package com.ailk.o2p.portal.utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ailk.o2p.portal.service.login.ILoginService;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebConstants;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: TenantInterceptor
 * @Description: 租户 spring mvc拦截器
 * @author zhengpeng
 * @date 2016-4-25 下午2:23:46
 *
 */
public class TenantInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger log = Logger.getLog(TenantInterceptor.class);
	public static final String PORTAL_TENANT_ISINTERCEPTOR = "portal_tenant_isInterceptor";
	public static final String TENANT_CODE = "tenantCode";
	public static final String TENANT_ID = "tenantId";
	public static final String SESSION_OPERATE_CODE = "sessionOperateCode";
	public static final String OPERATE_CODE = "operateCode";
	
	@Resource
	private ILoginService loginService;
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		String tenantCode = "defalut";
		Integer tenantId = O2pWebCommonUtil.getDefalutTenantId(); 
		log.debug("TenantInterceptor userRoleInfo:"+userRoleInfo);
		if(userRoleInfo == null){
			if("true".equals(WebPropertyUtil.getPropertyValue(PORTAL_TENANT_ISINTERCEPTOR))){
				log.debug("TenantInterceptor url:{0}||tid:{1}",request.getRequestURL(),request.getParameter("tid"));
				String tenantIdStr = this.getTenantIdByUrl(request);
				if(!StringUtil.isEmpty(tenantIdStr)){
					tenantId = Integer.valueOf(tenantIdStr);
					request.setAttribute("tid", tenantId);
				}else{
					tenantCode = this.getTenantCodeByUrl(request);
					tenantIdStr = loginService.queryTenantIdByCode(tenantCode);
					if(!StringUtil.isEmpty(tenantIdStr)){
						tenantId = Integer.valueOf(tenantIdStr); 
					}
				}
			}
			userRoleInfo = new UserRoleInfo();
			userRoleInfo.setTenantCode(tenantCode);
			userRoleInfo.setTenantId(tenantId); 
		}else{
			Integer _tenantId = userRoleInfo.getTenantId();
			if(_tenantId == null){
				if("true".equals(WebPropertyUtil.getPropertyValue(PORTAL_TENANT_ISINTERCEPTOR))){
					String tenantIdStr = this.getTenantIdByUrl(request);
					if(!StringUtil.isEmpty(tenantIdStr)){
						tenantId = Integer.valueOf(tenantIdStr);
					}else{
						tenantCode = this.getTenantCodeByUrl(request);
						tenantId = Integer.valueOf(loginService.queryTenantIdByCode(tenantCode));
					}
				}
				userRoleInfo.setTenantCode(tenantCode);
				log.debug("TenantInterceptor two setTid tid:{0}", tenantId);
				userRoleInfo.setTenantId(tenantId); 
				request.setAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY, userRoleInfo);
			}
		}
		OperateActContext.setUserInfo(userRoleInfo);
		return true;
	}
	
	/**
	 * cloud 判断是否需要过滤运营商的数据
	 * @param request
	 */
//	private void isOperateFilter(HttpServletRequest request){
//		Object operateCode = request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE);
//		if(null != operateCode){
//			log.debug("##############+++++++++++++++++++++++++++++############ sessionTenantId" + operateCode);
//			OperateActContext.setOperateCode(String.valueOf(operateCode));
//		}
//	}
	
	private String getTenantIdByUrl(HttpServletRequest request){
		return (String) request.getParameter("tid");
	}
	
	private String getTenantCodeByUrl(HttpServletRequest request){
    	StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
		int startIndex = 0;
		String indexStr = "://";
		if(contextUrl.indexOf(indexStr) > 0){
			startIndex = contextUrl.indexOf(indexStr)+indexStr.length();
		}
		if(contextUrl.indexOf(".") > -1){
			contextUrl = contextUrl.substring(startIndex, contextUrl.indexOf("."));
		}else{
			contextUrl = contextUrl.substring(startIndex, contextUrl.lastIndexOf(":"));  
		}
		return contextUrl;
    }
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView){
		OperateActContext.removeUserInfo();
	}

}
