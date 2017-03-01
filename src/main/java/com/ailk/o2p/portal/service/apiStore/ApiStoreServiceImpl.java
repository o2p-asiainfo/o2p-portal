package com.ailk.o2p.portal.service.apiStore;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.op2.bo.Org;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.o2p.portal.dao.apiStore.IApiStoreDao;
import com.ailk.o2p.portal.utils.DirectoryByJson;

/**
 * @author yangbl
 * @since 2010/8/16
 */
@SuppressWarnings("rawtypes")
@Service("apiStoreService")
public class ApiStoreServiceImpl implements IApiStoreService {
	
	@Autowired
	private IApiStoreDao apiStoreDao;
	
	
	@Override
	public int quryApiOfferDealCount(Map<String, String> param) {
		return apiStoreDao.quryApiOfferDealCount(param);
	}

	@Override
	public List<Map> quryApiOfferMsg(Map<String, String> param) {
		return apiStoreDao.quryApiOfferMsg(param);
	}

	@Override
	public List<Map> quryApiOfferList(Map<String, String> param) {
		return apiStoreDao.quryApiOfferList(param);
	}

	@Override
	public String quryApiOfferMenuType(com.ailk.eaap.op2.bo.ProdOffer prodOffer){
		return apiStoreDao.quryApiOfferMenuType(prodOffer);
	}

	@Override
	public String quryApiOfferProviderName(Org org) {
		return apiStoreDao.quryApiOfferProviderName(org);
	}

	@Override
	public int insertApiOfferVisitorVolume(ProdOffer prodOffer) {
		return apiStoreDao.insertApiOfferVisitorVolume(prodOffer);
	}

	@Override
	public String quryApiOfferType(ProdOffer prodOffer) {
		return apiStoreDao.quryApiOfferType(prodOffer);
	}

	@Override
	public List<Map> queryNewApiList(Map map) {
		return apiStoreDao.queryNewApiList(map);
	}

	@Override
	public String quryApiCataName(ProdOffer prodOffer) {
		return apiStoreDao.quryApiCataName(prodOffer);
	}

	@Override
	public JSONArray queryApiCategory(Map map) {
		List<Directory> resultList = apiStoreDao.queryApiCategoryList(map);
		JSONArray json = DirectoryByJson.createTreeJson(resultList); 
		return json;
	}

	@Override
	public Integer updateVisitor(ItemCnt itemCnt) {
		return apiStoreDao.updateVisitor(itemCnt);
	}

	@Override
	public List<Map> quryApiOfferListByCategory(Map<String, String> param) {
		return apiStoreDao.quryApiOfferListByCategory(param);
	}

	@Override
	public List<Map> quryAllApiOfferList(Map<String, String> param) {
		return apiStoreDao.quryAllApiOfferList(param);
	}

	@Override
	public List<Map> quryAllLimitApiOfferList(Map<String, String> param) {
		return apiStoreDao.quryAllLimitApiOfferList(param);
	}

	@Override
	public List<Map> queryNewLimitApiList(Map<String, String> param) {
		return apiStoreDao.queryNewLimitApiList(param);
	}

	@Override
	public List<Map> quryApiOfferLimitList(Map<String, String> param) {
		return apiStoreDao.quryApiOfferLimitList(param);
	}

	@Override
	public Integer queryApiOfferCnt(Map<String, Object> paraMap) {
		return apiStoreDao.queryApiOfferCnt(paraMap);
	}

	@Override
	public String querySeriveCode(Api api) {
		return apiStoreDao.querySeriveCode(api);
	}

//	@Override
//	public List<Directory> queryDirList(Directory directory) {
//		return apiStoreDao.queryDirList(directory);
//	}
//
//	@Override
//	public Integer queryFaDirId(Directory directory) {
//		return apiStoreDao.queryFaDirId(directory);
//	}

	

}
