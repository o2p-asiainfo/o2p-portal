package com.ailk.o2p.portal.service.productstore;

import java.util.List;

import net.sf.json.JSONArray;

import java.util.Map;

public interface IProductStoreServ {
	public JSONArray queryProductCategory(Map map);
	public List<Map> queryProductList(Map map);
	public List<Map> queryNewProductList(Map map);
	public List<Map> queryCRProductList(Map map);
	public Integer queryProductOfferCnt(Map<String, Object> paraMap);
}
