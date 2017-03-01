package com.ailk.o2p.portal.controller.productstore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.o2p.portal.bo.PageBean;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.productstore.IProductStoreServ;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;

@Controller
@RequestMapping(value = "/productStore")
public class ProductStoreController extends BaseController {
	private static final Logger log = Logger.getLog(ProductStoreController.class);
	@Autowired
	private IProductStoreServ productStoreServ;

	@UnPermission
	@RequestMapping(value = "/toIndex")
	public ModelAndView goProductIndex(final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		if (this.isCms()) {
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE", template);
			Map paraMap = new HashMap();
			paraMap.put("tenantId", this.getTenantId());
			JSONArray treeMenu = productStoreServ.queryProductCategory(paraMap);
			
			PageBean pageBean = new PageBean();
			pageBean.setActualRowCnt(productStoreServ.queryProductOfferCnt(paraMap));
			paraMap.put("startRow", pageBean.getStartRow());
			paraMap.put("pageSize", pageBean.getPageSize());
			
			List<Map> productList = productStoreServ.queryCRProductList(paraMap);
			List<Map> newproductList = productStoreServ.queryNewProductList(paraMap);
			mv.addObject("productList", productList);
			mv.addObject("newproductList", newproductList);
			mv.addObject("currentPage",pageBean.getCurrentPage()+1);
			mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
			mv.addObject("treeMenu", treeMenu);
			mv.setViewName(tenantId + "/" + template + "/productStore/productStore.htm");
		} else {
			mv.setViewName("../productStore/productStore.jsp");
		}
		return mv;
	}

	@UnPermission
	@RequestMapping(value = "/productCategory", method = RequestMethod.GET)
	@ResponseBody
	public String productCategory(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		Map paraMap = new HashMap();
		paraMap.put("tenantId", this.getTenantId());
		JSONArray treeMenu = productStoreServ.queryProductCategory(paraMap);
		return treeMenu.toString();

	}

	@UnPermission
	@RequestMapping(value = "/productList")
	public ModelAndView productList(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		String dirId = getRequestValue(request,"dirId");
		String currentPage = getRequestValue(request, "currentPage");
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isEmpty(dirId)) {
			dirId = null;
		}
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		Map paraMap = new HashMap();
		paraMap.put("tenantId", tenantId);
		paraMap.put("dirId", dirId);
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(currentPage);
		pageBean.setActualRowCnt(productStoreServ.queryProductOfferCnt(paraMap));
		paraMap.put("startRow", pageBean.getStartRow());
		paraMap.put("pageSize", pageBean.getPageSize());
		List<Map> productList = productStoreServ.queryCRProductList(paraMap);
		List<Map> newproductList = productStoreServ.queryNewProductList(paraMap);
		mv.addObject("productList", productList);
		mv.addObject("newproductList", newproductList);
		mv.addObject("TENANTID", tenantId);
		mv.addObject("TEMPLATE", template);
		mv.addObject("currentPage",pageBean.getCurrentPage()+1);
		mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
		mv.setViewName(this.getTenantId() + "/" + template + "/productStore/productList.htm");
		return mv;
	}

	@UnPermission
	@RequestMapping(value = "/productDetail")
	public ModelAndView goProductDetail(final HttpServletRequest request, final HttpServletResponse response) {
		String prodOfferId = request.getParameter("prodOfferId");
		
		
		ModelAndView mv = new ModelAndView();
		if (this.isCms()) {
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE", template);
			mv.setViewName(tenantId + "/" + template + "/productStore/productDetail_" + prodOfferId + ".htm");
		}
		return mv;
	}

	@UnPermission
	public boolean switchEnvironment() {
		String switchEnvironmentFlag = WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if ("true".equals(switchEnvironmentFlag)) {
			return true;
		}
		return false;
	}

}
