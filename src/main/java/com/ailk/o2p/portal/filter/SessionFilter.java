package com.ailk.o2p.portal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ailk.o2p.portal.utils.CookieUtils;
import com.ailk.o2p.portal.utils.I18nAspectForSpring;
import com.ailk.o2p.portal.utils.IdGenerator;
import com.ailk.o2p.portal.utils.PortalConstants;
import com.ailk.o2p.portal.utils.SystemKeyWords;
import com.ailk.o2p.portal.web.CustomizedHttpServletRequest;
import com.linkage.rainbow.util.StringUtil;

public class SessionFilter implements Filter {

	private FilterConfig filterConfig;

	public void destroy() {
	}

	public void init(FilterConfig fc) throws ServletException {
		SystemKeyWords.APP_PATH = fc.getServletContext().getContextPath();
		fc.getServletContext()
				.setAttribute("APP_PATH", SystemKeyWords.APP_PATH);
	}

	@SuppressWarnings("unused")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String path = httpRequest.getRequestURI();
		if(path.indexOf(".jsp") > -1){
			httpRequest.getRequestDispatcher("index.shtml").forward(httpRequest,httpResponse);
		}else{
			if(httpRequest.getSession().getAttribute(PortalConstants.LANGUAGE) == null){
				httpRequest.getSession().setAttribute(PortalConstants.LANGUAGE, I18nAspectForSpring.getLanguage());
			}
			if (1 == SystemKeyWords.SESSION_STORE_REDIS) {
				String sid = CookieUtils.getCookie(httpRequest,
						SystemKeyWords.SESSIONID_COOKIE);
				if (null == sid || StringUtils.EMPTY.equals(sid)) {
					CookieUtils.setCookie(httpResponse,
							SystemKeyWords.SESSIONID_COOKIE, IdGenerator.uuid());
				}
				ServletRequest custRequest = new CustomizedHttpServletRequest(sid,
						httpRequest, httpRequest.getSession().getServletContext());
				filterChain.doFilter(custRequest, response);
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
