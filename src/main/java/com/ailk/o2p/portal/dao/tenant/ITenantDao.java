package com.ailk.o2p.portal.dao.tenant;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ITenantDao
 * @Description: 
 * @author zhengpeng
 * @date 2016-5-13 下午5:41:27
 *
 */
public interface ITenantDao {
	
	public String qryAdminIdByTenantId(Integer tenantId);
	
	public List<Map<String,Object>> qryTenantTheme();

}
