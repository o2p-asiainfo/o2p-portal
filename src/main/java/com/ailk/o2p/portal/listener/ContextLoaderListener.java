package com.ailk.o2p.portal.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ailk.o2p.portal.utils.LogUtil;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;

public class ContextLoaderListener implements ServletContextListener {
	private static final Logger log = Logger.getLog(ContextLoaderListener.class);
	public static final String O2P_CONFIGLOCATION = "o2pConfigLocation";
	public static final String APPLICATION_STYLE_THEME = "contextStyleTheme";
	public static final String APPLICATION_STYLE_SPECIAL = "contextStyleSpecial";
	public static final String APPLICATION_MENU_BELONGTO = "contextMenuBelongto";
	
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String contextStyleTheme = null;
		String contextStyleSpecial = null;
		String contextMenuBelongto = null;
		try {
			if(null != WebPropertyUtil.getPropertyValue(APPLICATION_STYLE_THEME)){
				contextStyleTheme = WebPropertyUtil.getPropertyValue(APPLICATION_STYLE_THEME);
			}
			if(null != WebPropertyUtil.getPropertyValue(APPLICATION_STYLE_SPECIAL)){
				contextStyleSpecial = WebPropertyUtil.getPropertyValue(APPLICATION_STYLE_SPECIAL);
			}
			if(null != WebPropertyUtil.getPropertyValue(APPLICATION_MENU_BELONGTO)){
				contextMenuBelongto = WebPropertyUtil.getPropertyValue(APPLICATION_MENU_BELONGTO);
			}
		} catch (Exception e) {
			LogUtil.log(log,e,"contextInitialized expection:");
		} 
		servletContext.setAttribute(APPLICATION_STYLE_THEME, contextStyleTheme);
		servletContext.setAttribute(APPLICATION_STYLE_SPECIAL, contextStyleSpecial);
		servletContext.setAttribute(APPLICATION_MENU_BELONGTO, contextMenuBelongto);
	}

	public void contextDestroyed(ServletContextEvent event) {

	}
}
