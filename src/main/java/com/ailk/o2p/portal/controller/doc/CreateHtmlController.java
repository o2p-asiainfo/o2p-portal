package com.ailk.o2p.portal.controller.doc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.foundation.log.Logger;

@Controller
public class CreateHtmlController extends BaseController {

	private static final Logger log = Logger.getLog(CreateHtmlController.class);

	@UnPermission
	@RequestMapping(value = "api/getHtml")
	public ModelAndView getHtml(final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		// 项目绝对地址
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		// 项目物理路径
		String realPath = request.getSession().getServletContext().getRealPath("/");
		// 相对动态地址（动态页面地址）
		String dynamicPath = request.getParameter("dPath");
		// 相对静态地址（静态页面地址）
		String staticsPath = "";
		if (dynamicPath.indexOf(".") > 0) {
			staticsPath = dynamicPath.substring(0, dynamicPath.indexOf(".")).replace("/", "_");
		}
		// 拼接参数字符串
		String paraStr = "?";
		// 获取所有参数
		Enumeration paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paramName = (String) paraNames.nextElement();
			if ("dPath".equals(paramName)) {
				continue;
			} else {
				paraStr = paraStr + paramName + "=" + request.getParameter(paramName) + "&";
				staticsPath = staticsPath + request.getParameter(paramName);
			}
		}
		if (!"?".equals(paraStr)) {
			paraStr = paraStr.substring(0, paraStr.lastIndexOf("&"));
			dynamicPath = dynamicPath + paraStr;
		}
		staticsPath = staticsPath + ".html";
		ServletContext context = request.getServletContext();
		Object obj = context.getAttribute("staticsHtmlMap");
		Map<String, Object> staticHtmlMap = null;
		if (obj != null) {
			staticHtmlMap = (Map<String, Object>) obj;
			Object statichtmlObj = staticHtmlMap.get(staticsPath);
			if (statichtmlObj == null) {
				HtmlGenerator h = new HtmlGenerator("");
				boolean flag = h.createHtmlPage(basePath + "/" + dynamicPath, realPath + staticsPath);
				if (flag == true) {
					staticHtmlMap.put(staticsPath, "1");
				}
			}
		} else {
			HtmlGenerator h = new HtmlGenerator("");
			boolean flag = h.createHtmlPage(basePath + "/" + dynamicPath, realPath + staticsPath);
			if (flag == true) {
				staticHtmlMap = new HashMap<String, Object>();
				staticHtmlMap.put(staticsPath, "1");
				context.setAttribute("staticsHtmlMap", staticHtmlMap);
			}
		}
		mv.setViewName("/" + staticsPath);
		return mv;
	}

	@UnPermission
	@RequestMapping(value = "api/reflushHtml")
	public void reflushHtml(final HttpServletRequest request, final HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		context.removeAttribute("staticsHtmlMap");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("reflush html success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
