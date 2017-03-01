package com.ailk.o2p.portal.service.login;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.Tenant;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgRole;

public interface ILoginService {

	public List<Org> selectOrg(Org org);
	
	public List<Map<String,String>> queryOrgIdAndName();

	public Map loginOrg(Org org);

	public List<OrgRole> selectOrgRole(OrgRole orgRoleBean);

	public Org selectOrgOne(Org org);

	public Component selectComponentOne(Component component);
	
	public String queryCityById(String areaId);
	
	public String queryTenantIdByCode(String tenantCode);
	
	public Tenant queryTenant(Tenant tenant);

}
