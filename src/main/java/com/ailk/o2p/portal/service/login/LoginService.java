package com.ailk.o2p.portal.service.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.Tenant;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgRole;
import com.ailk.o2p.portal.dao.org.IOrgDao;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IOrgDao orgSqlDAO;

	public List<Org> selectOrg(Org org) {
		return orgSqlDAO.selectOrg(org);
	}
	
	public List<Map<String,String>> queryOrgIdAndName(){
		return orgSqlDAO.queryOrgIdAndName();
	}

	public Map loginOrg(Org org) {
		return orgSqlDAO.loginOrg(org);
	}

	public List<OrgRole> selectOrgRole(OrgRole orgRoleBean) {
		return orgSqlDAO.selectOrgRole(orgRoleBean);
	}

	public IOrgDao getOrgSqlDAO() {
		return orgSqlDAO;
	}

	public void setOrgSqlDAO(IOrgDao orgSqlDAO) {
		this.orgSqlDAO = orgSqlDAO;
	}

	public Org selectOrgOne(Org org) {

		return orgSqlDAO.selectOrgOne(org);
	}

	public Component selectComponentOne(Component component) {
		return orgSqlDAO.selectComponentOne(component);
	}
	
	public String queryCityById(String areaId){
		Map paramMap=new HashMap(); 
		paramMap.put("areaId", areaId);
		return orgSqlDAO.queryCityById(paramMap); 
	}
	
	public String queryTenantIdByCode(String tenantCode){
		return orgSqlDAO.queryTenantIdByCode(tenantCode);
	}
	public Tenant queryTenant(Tenant tenant){
		return orgSqlDAO.queryTenant(tenant);
	}
	
	

}
