/** 
 * Project Name:o2p-web-common-tags 
 * File Name:BusiDataInfoListener.java 
 * Package Name:com.ailk.eaap.o2p.common.listener 
 * Date:2016年3月13日下午7:29:38 
 * Copyright (c) 2016, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.controller.busiDataDeal;  

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;





import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.service.busiDataInfo.IBusiDataInfoServ;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.AuthenticationUtil;

/** 
 * ClassName:BusiDataInfoListener <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年3月13日 下午7:29:38 <br/> 
 * @author   wushuzhen 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
public class BusiDataInfoDeal {
	private static final Logger log = Logger.getLog(BusiDataInfoDeal.class);
	@Autowired
	private IBusiDataInfoServ busiDataInfoServ;
	
	public String getAppTokenStr(ServletContext servletContext){
		String appTokenStr=(String)servletContext.getAttribute(EAAPConstants.HUB_APP_TOKEN);
		if(StringUtils.isEmpty(appTokenStr)){
			contextInitialized(servletContext);
			appTokenStr=(String)servletContext.getAttribute(EAAPConstants.HUB_APP_TOKEN);
		}
		return appTokenStr;
	}
	

	public void contextInitialized(ServletContext servletContext) {
		// TODO Auto-generated method stub
		ApplicationContext context 		= WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		SpringContextUtil springUtil=new SpringContextUtil();
		springUtil.setApplicationContext(context);
		busiDataInfoServ = (IBusiDataInfoServ) SpringContextUtil.getBean("busiDataInfoServ");
		Map map =new HashMap();
		map.put("businessName", EAAPConstants.HUB_APP_TOKEN);
		BusiDataInfo busiDataInfo=busiDataInfoServ.qryBusiDataInfoByName(map);
		String appToken=null;
		Integer apphubTenantId = null;
		if(busiDataInfo==null){
			apphubTenantId=getAppHubTenantId();
			appToken=AuthenticationUtil.generatedAppToken(apphubTenantId);
			BusiDataInfo busiDataInfoNew=new BusiDataInfo();
			busiDataInfoNew.setBusinessName(EAAPConstants.HUB_APP_TOKEN);
			busiDataInfoNew.setBusinessValue(appToken);
			busiDataInfoNew.setBusinessModule("appHub");
			busiDataInfoNew.setBusinessDesc("appHub appToken flag");
			busiDataInfoServ.insertDataToTable(busiDataInfoNew);
		}else{
			appToken=busiDataInfo.getBusinessValue();
		}
		servletContext.setAttribute(EAAPConstants.HUB_APP_TOKEN, appToken);
	
		log.debug("servlet appToken attribute: {0}", servletContext.getAttribute(EAAPConstants.HUB_APP_TOKEN));
	}

   public BusiDataInfo updateBusiDataInfo(ServletContext servletContext){
	   Integer apphubTenantId=getAppHubTenantId();
		String appToken=AuthenticationUtil.generatedAppToken(apphubTenantId);
		Map map =new HashMap();
		map.put("businessName", EAAPConstants.HUB_APP_TOKEN);
		BusiDataInfo busiDataInfo=busiDataInfoServ.qryBusiDataInfoByName(map);
		busiDataInfo.setBusinessValue(appToken);
		busiDataInfoServ.updateBusiDataInfo(busiDataInfo);
		servletContext.setAttribute(EAAPConstants.HUB_APP_TOKEN, appToken);
		return busiDataInfo;
   }

   public Integer getAppHubTenantId(){
	   UserRoleInfo userRoleInfo=OperateActContext.getUserInfo();
	   Integer apphubTenantId=userRoleInfo.getTenantId();
	   return apphubTenantId;
   }
	
	public IBusiDataInfoServ getBusiDataInfoServ() {
		return busiDataInfoServ;
	}

	public void setBusiDataInfoServ(IBusiDataInfoServ busiDataInfoServ) {
		this.busiDataInfoServ = busiDataInfoServ;
	}

	
	
}
