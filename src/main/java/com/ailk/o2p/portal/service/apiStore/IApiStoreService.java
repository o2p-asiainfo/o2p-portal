package com.ailk.o2p.portal.service.apiStore;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Directory;

import net.sf.json.JSONArray;

/**
 * 
 * @author yangbl
 * @since 2010/8/16
 */
public interface IApiStoreService {
	
	public int quryApiOfferDealCount(Map<String, String> param);
	
	public List<Map> quryApiOfferMsg(Map<String, String> param);
	
	public List<Map> quryApiOfferList(Map<String, String> param);
	
	public List<Map> quryAllApiOfferList(Map<String, String> param);
	
	public List<Map> quryApiOfferListByCategory(Map<String, String> param);
	
	public String  quryApiOfferMenuType(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	
	public String  quryApiOfferProviderName(com.ailk.eaap.op2.bo.Org org);
	
	public int insertApiOfferVisitorVolume(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	
	public String quryApiOfferType(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	
	public List<Map> queryNewApiList(Map map);
	
	public String quryApiCataName(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	
	public JSONArray queryApiCategory(Map map);
	
	public Integer updateVisitor(com.ailk.eaap.op2.bo.ItemCnt itemCnt);
	
	public List<Map> quryAllLimitApiOfferList(Map<String, String> param);
	
	public List<Map> queryNewLimitApiList(Map<String, String> param);
	
	public List<Map> quryApiOfferLimitList(Map<String, String> param);
	
	public Integer queryApiOfferCnt(Map<String, Object> paraMap);
	
	public String querySeriveCode(com.ailk.eaap.op2.bo.Api api);
	
	//public List<Directory> queryDirList(com.ailk.eaap.op2.bo.Directory directory);
	
	//public Integer queryFaDirId(com.ailk.eaap.op2.bo.Directory directory);
	
}
