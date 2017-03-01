package com.ailk.o2p.portal.service.pard;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface IPardSellServ {

	public List<Map> queryProdSellList (Map map);
	
	public List<Map> queryApplyProdInfo (Map map);
	
	public List<Map> queryAddProList (Map map);
	
	public void addProductInfo(Map map);
	
	public void cancelProduct(Map map);
	
}