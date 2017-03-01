package com.ailk.o2p.portal.dao.pard;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface PardSellDao {

	public List<Map> queryProdSellList(Map map);
	
	public List<Map> queryProdSellNum(Map map);
	
	public List<Map> queryToAddProdList(Map map);
	
	public List<Map> queryToAddProdNum(Map map);
	
	public List<Map> queryApplyInfo(Map map);
	
	public List<Map> queryChannelId(Map map);
	
	public Integer saveChannelInfo(Map map);
	
	public void saveOfferChannel(Map map);
	
	public void cancelProduct(Map map);
}
