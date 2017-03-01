package com.ailk.o2p.portal.service.tenant;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ITenantService
 * @Description: 
 * @author zhengpeng
 * @date 2016-5-13 下午5:37:58
 *
 */
public interface ITenantService {
	
	public String qryAdminIdByTenantId(Integer tenantId);
	
	public List<Map<String,Object>> qryTenantTheme();

}
