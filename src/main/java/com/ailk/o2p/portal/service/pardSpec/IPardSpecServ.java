package com.ailk.o2p.portal.service.pardSpec;

import java.util.List;

import com.ailk.eaap.op2.bo.CharSpec;
import com.ailk.eaap.op2.bo.CharSpecValue;

public interface IPardSpecServ {
	
	public CharSpec qryCharSpecById(String charSpecId);
	public List<CharSpec> qryCharSpec(CharSpec charSpec);
	public String cntCharSpec(CharSpec charSpec);
	public List<CharSpecValue> qryCharSpecValue(CharSpecValue charSpecValue);
	
	public String addCharSpec(CharSpec charSpec,List<CharSpecValue> charSpecValueList);
	public String updateCharSpec(CharSpec charSpec,List<CharSpecValue> charSpecValueList);
	
	public void delCharSpec(String charSpecId);
	public Integer isUpdateOrDelete(String charSpecId);
	
	public int checkPageInTypeCount(String pageInType,int orgId);
}