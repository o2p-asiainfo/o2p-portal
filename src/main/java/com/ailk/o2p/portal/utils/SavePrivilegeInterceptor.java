package com.ailk.o2p.portal.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.o2p.portal.dao.savePrivilege.ISavePrivilegeDao;

public class SavePrivilegeInterceptor extends HandlerInterceptorAdapter {

//	private static final Logger log = Logger.getLog(SavePrivilegeInterceptor.class);
	@Autowired
	private ISavePrivilegeDao savePrivilegeDao;
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
//		log.debug("this method name:==============>"+method.getName());
		
		if (method != null && method.isAnnotationPresent(SavePermission.class)) {
			//首先先判断是否已经登录，未登录返回登陆页，已登录继续 
			HttpSession session = request.getSession();
			Org orgBean = (Org) session.getAttribute("userBean");
			if ( orgBean == null) { 
				throw new AuthorizationException();
			}else{
				SavePermission saveP = method.getAnnotation(SavePermission.class);
				String objectId = request.getParameter(saveP.parameterKey());
				if(null==objectId || "".equals(objectId)){
					return true;
				}
				if(checkPrivilege(saveP.center(),saveP.module(),objectId,String.valueOf(orgBean.getOrgId()))){
					return true;
				}else{
					throw new SavePrivilegeException();
				}
			}
		}else{
			return true;
		}
	}

	private Boolean checkPrivilege(String center,String module,String objectId,String userId) 
			throws SavePrivilegeException{
		Integer cntObject = null;
		Map<String,String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("objectId", objectId);
		if("pard".equals(center)){
			if("serviceSpec".equals(module)){
				cntObject = savePrivilegeDao.cntServiceSpec(map);
			}else if("product".equals(module)){
				cntObject = savePrivilegeDao.cntProduct(map);
			}else if("offer".equals(module)){
				cntObject = savePrivilegeDao.cntOffer(map);
			}else if("attrSpec".equals(module)){
				cntObject = savePrivilegeDao.cntAttrSpec(map);
			}else if("pricePlan".equals(module)){
				cntObject = savePrivilegeDao.cntPricePlan(map);
			}else if("pricePlanRule".equals(module)){
				cntObject = savePrivilegeDao.cntPricePlanRule(map);
			}else if("settlement".equals(module)){
				cntObject = savePrivilegeDao.cntSettlement(map);
			}else if("settlementRule".equals(module)){
				cntObject = savePrivilegeDao.cntSettlementRule(map);
			}else{
				throw new SavePrivilegeException();
			}
		}else if("dev".equals(center)){
			if("app".equals(module)){
				cntObject = savePrivilegeDao.cntApp(map);
			}else{
				throw new SavePrivilegeException();
			}
		}else if("prov".equals(center)){
			if("pricePlanRule".equals(module)){
				cntObject = savePrivilegeDao.cntSysPricePlan(map);
			}else if("component".equals(module)){
				cntObject = savePrivilegeDao.cntSys(map);
			}else{
				throw new SavePrivilegeException();
			}
		}else{
			throw new SavePrivilegeException();
		}
		return cntObject==1;
	}
	
}
