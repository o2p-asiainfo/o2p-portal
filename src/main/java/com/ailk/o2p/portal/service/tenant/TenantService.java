package com.ailk.o2p.portal.service.tenant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.o2p.portal.dao.tenant.ITenantDao;

/**
 * @ClassName: TenantService
 * @Description: 
 * @author zhengpeng
 * @date 2016-5-13 下午5:38:24
 *
 */
@Service
public class TenantService implements ITenantService{
	
	@Autowired
	private ITenantDao tenantDao;
	
	public String qryAdminIdByTenantId(Integer tenantId){
		return tenantDao.qryAdminIdByTenantId(tenantId);
	}
	
	public List<Map<String,Object>> qryTenantTheme(){
		return tenantDao.qryTenantTheme();
	}

}
