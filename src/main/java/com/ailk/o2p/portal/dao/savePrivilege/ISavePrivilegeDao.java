package com.ailk.o2p.portal.dao.savePrivilege;

import java.util.Map;

public interface ISavePrivilegeDao {

	public Integer cntAttrSpec(Map<String,String> map);
	public Integer cntServiceSpec(Map<String,String> map);
	public Integer cntProduct(Map<String,String> map);
	public Integer cntOffer(Map<String,String> map);
	public Integer cntPricePlan(Map<String,String> map);
	public Integer cntPricePlanRule(Map<String,String> map);
	public Integer cntSettlement(Map<String,String> map);
	public Integer cntSettlementRule(Map<String,String> map);
	
	public Integer cntApp(Map<String,String> map);
	
	public Integer cntSys(Map<String,String> map);
	public Integer cntSysPricePlan(Map<String,String> map);
}
