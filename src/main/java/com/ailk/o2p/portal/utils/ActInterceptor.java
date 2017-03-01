package com.ailk.o2p.portal.utils;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ailk.eaap.o2p.sqllog.model.OperateActInfo;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.sqllog.util.SqlLogUtil;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebConstants;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: ActInterceptor
 * @Description: 
 * @author zhengpeng
 * @date 2015-2-5 上午10:53:22
 *
 */
public class ActInterceptor extends HandlerInterceptorAdapter{

	public static final String SQLLOG_ISINTERCEPTOR = "sqlLog.isInterceptor";
	private static final Logger log = Logger.getLog(ActInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		//需要拦截
		if(SqlLogUtil.getSqlLogIsInterceptor()){
			try{
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				String className = handlerMethod.getBean().getClass().getName();
				Method method = handlerMethod.getMethod();
				if(OperateActContext.getActInfo() == null){
					OperateActInfo operateActInfo = new OperateActInfo();
					String userName = getUserNameForSession(request.getSession());
					operateActInfo.setUserName(userName); 
					String userIp = this.getRemortIP(request);
					operateActInfo.setUserIp(userIp);
					operateActInfo.setTenantId(this.getTenantId(request.getSession())); 
					log.debug("################### tenantId:" + operateActInfo.getTenantId()); 
					operateActInfo.setExecClass(className);  
					operateActInfo.setExceMothod(method.getName()); 
					OperateActContext.setActInfo(operateActInfo);
				}
			}catch (Exception e) {
				LogUtil.log(log, e, "ActInterceptor preHandle: ");
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView){
		OperateActContext.removeActInfo();
	}
	
	/**
	 * 获取租户ID
	 */
	private Integer getTenantId(HttpSession session){
		UserRoleInfo userRoleInfo = (UserRoleInfo) session.getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
		if(userRoleInfo != null){
			tenantId = userRoleInfo.getTenantId();
		}
		return tenantId;
	}
	
	/**
	 * 获取到用户名
	 * @param session
	 * @return
	 */
	private String getUserNameForSession(HttpSession session){
		String userName = "";
		if(session != null){
			Object orgBean =  session.getAttribute("userBean");
			if(orgBean != null){
				userName = ((Org)orgBean).getName();
			}
		}
		return userName;
	}
	
	/**
	 * 从Cookie获取到用户名
	 * @param cookie
	 * @return
	 */
	public String getUserNameForCookie(Cookie cookie){
		String userName = "";
		if(!StringUtil.isEmpty(cookie.getValue())){
			userName = cookie.getValue();
		}
		return userName;
	}


	
	public String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
	}
	
	public static String base64Correct(String base64str){
		switch(base64str.length()%4) {  
	        case 3:  
	        	base64str+= "==="; break;   
	        case 2:  
	        	base64str+= "=="; break;  
	        case 1:  
	        	base64str+= "="; break;  
	        default:  
	    }
		return base64str;
	}
	

}
