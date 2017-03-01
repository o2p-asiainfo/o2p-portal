package com.ailk.o2p.portal.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ailk.o2p.portal.utils.I18nAspectForSpring;
import com.ailk.o2p.portal.utils.PortalConstants;
import com.ailk.o2p.portal.utils.SystemKeyWords;
import com.asiainfo.integration.o2p.session.web.http.O2pSessionRepositoryFilter;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: PortalSessionRepositoryFilter
 * @Description: 
 * @author zhengpeng
 * @date 2015-9-7 上午11:44:20
 *
 */
public class PortalSessionRepositoryFilter extends O2pSessionRepositoryFilter{
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		String path = request.getRequestURI();
//		if(path.indexOf(".jsp") > -1){
//			request.getRequestDispatcher("index.shtml").forward(request,response);
//		}else{
//			super.doFilterInternal(request, response, filterChain);
//		}
		super.doFilterInternal(request, response, filterChain);

	 }
	    
	 public void init(FilterConfig config) {
		 if(isCms()){
			 SystemKeyWords.APP_PATH = WebPropertyUtil.getPropertyValue("o2p_portal_path"); 
		 }else{
			 SystemKeyWords.APP_PATH = config.getServletContext().getContextPath();
		 }
		 SystemKeyWords.WEB_PATH = WebPropertyUtil.getPropertyValue("o2p_web_front_path");
		 config.getServletContext().setAttribute("APP_PATH", SystemKeyWords.APP_PATH);
		 config.getServletContext().setAttribute("WEB_PATH", SystemKeyWords.WEB_PATH); 
		 
		 config.getServletContext().setAttribute(PortalConstants.LANGUAGE, I18nAspectForSpring.getLanguage());
		 config.getServletContext().setAttribute(SystemKeyWords.APP_WEB_TITLE, 
				 WebPropertyUtil.getPropertyValue(SystemKeyWords.APP_WEB_TITLE));
		 config.getServletContext().setAttribute(SystemKeyWords.APP_WEB_FOOT, 
				 WebPropertyUtil.getPropertyValue(SystemKeyWords.APP_WEB_FOOT));
		 config.getServletContext().setAttribute(SystemKeyWords.APP_WEB_LOGO_STYLE, 
				 WebPropertyUtil.getPropertyValue(SystemKeyWords.APP_WEB_LOGO_STYLE));
		 config.getServletContext().setAttribute(SystemKeyWords.LOCAL_NAME, I18nAspectForSpring.getLocalName());
		 super.init(config);
	 }
	 
	 private boolean isCms(){
			Object isCmsObj = WebPropertyUtil.getPropertyValue("o2p_portal_cms");
			if(isCmsObj == null || StringUtil.isEmpty(String.valueOf(isCmsObj))){
				return false;
			}else{
				return Boolean.valueOf(String.valueOf(isCmsObj));
			}
		}

}
