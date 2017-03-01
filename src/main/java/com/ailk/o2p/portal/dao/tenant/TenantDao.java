package com.ailk.o2p.portal.dao.tenant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.linkage.rainbow.dao.SqlMapDAO;

/**
 * @ClassName: TenantDao
 * @Description: 
 * @author zhengpeng
 * @date 2016-5-13 下午5:41:45
 *
 */
@Repository
public class TenantDao implements ITenantDao{
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	
	public String qryAdminIdByTenantId(Integer tenantId){
		return String.valueOf(sqlMapDao.queryForObject("org.qryAdminIdByTenantId", tenantId)); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> qryTenantTheme(){
		return sqlMapDao.queryForList("cms.qryTenantTheme",null);
	}

}
