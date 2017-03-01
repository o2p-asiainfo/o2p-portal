package com.ailk.o2p.portal.utils;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asiainfo.integration.o2p.web.bo.Org;

/**
 * 方法权限拦截器.
 * @author NIEZH
 *
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if (method != null && method.isAnnotationPresent(UnPermission.class)) {
			return true;
		}else{
			//首先先判断是否已经登录，未登录返回登陆页，已登录继续 
			HttpSession session = request.getSession();
			if (session.getAttribute("userBean")  == null) { 
				throw new AuthorizationException();
			}else{
				if (method != null && method.isAnnotationPresent(Permission.class)) {
					Permission permission = method.getAnnotation(Permission.class);
					String privilege=permission.privilege();
					if("audit".equals(privilege)){//需要已审核权限，则判断用户是否已通过审核
						Org userBean=(Org)session.getAttribute("userBean");
						if(!"D".equals(userBean.getState())){
							throw new AuditException();
						}
					}
				}
				return true;
			}
		}
	}
	
}
