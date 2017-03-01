package com.ailk.o2p.portal.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ailk.o2p.portal.service.tenant.ITenantService;

/**
 * @ClassName: TemplateUtil
 * @Description: 
 * @author zhengpeng
 * @date 2016-7-26 上午10:09:29
 *
 */
@Component
public class TemplateUtil implements InitializingBean{
	
	public static Map<Integer,String> templateMap = new HashMap<Integer,String>();
	@Autowired
	private ITenantService tenantService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<Map<String,Object>> themeList = tenantService.qryTenantTheme();
		for(Map<String,Object> tenantTheme : themeList){
			if(tenantTheme.get("TENANTID") != null && tenantTheme.get("THEME") != null){
				templateMap.put(Integer.valueOf(tenantTheme.get("TENANTID").toString()),tenantTheme.get("THEME").toString());
			}
		}
	}
	
	public static String getTenantTemplate(Integer tenantId){
		return templateMap.get(tenantId); 
	}

	

}
