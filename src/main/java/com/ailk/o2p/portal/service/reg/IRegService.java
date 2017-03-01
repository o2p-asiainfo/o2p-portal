package com.ailk.o2p.portal.service.reg;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ailk.eaap.op2.bo.Area;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgRole;

public interface IRegService{
	
	public void saveOrg(Org org,OrgRole orgRoleBean) throws Exception;
	
	public Integer addOrgRole(OrgRole orgRoleBean);
	
	public List<Org> selectOrg(Org org);
	
	public Integer updateOrgInfo(Org orgBean);
	
	public List<Map<String,Object>> queryProvinceForReg(Org orgBean);
	
	public List<Map<String,Object>> queryCityForReg(Org orgBean);
	
	public List<Area> loadCityAreaList(HashMap paraMap);
	
	public String queryCityById(String areaId);
	
	public Integer queryOrgIdSeq();
}
