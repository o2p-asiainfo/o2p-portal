package com.ailk.o2p.portal.dao.apiStore;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.op2.bo.Org;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
@SuppressWarnings("unchecked")
public class ApiStoreDaoImpl implements IApiStoreDao {
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	
	@Override
	public int quryApiOfferDealCount(Map<String, String> param) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.quryApiOfferDealCount", param);
	}


	@Override
	public List<Map> quryApiOfferMsg(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryApiOfferMsg",param);
	}


	@Override
	public List<Map> quryApiOfferList(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryApiOfferList",param);
	}


	@Override
	public String quryApiOfferMenuType(com.ailk.eaap.op2.bo.ProdOffer prodOffer) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.quryApiOfferMenuType", prodOffer);
	}


	@Override
	public String quryApiOfferProviderName(Org org) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.quryApiOfferProviderName", org);
	}


	@Override
	public int insertApiOfferVisitorVolume(com.ailk.eaap.op2.bo.ProdOffer prodOffer) {
		return (Integer) sqlMapDao.insert("eaap-op2-portal-apiStroe.addProdRelPricing", prodOffer);
	}


	@Override
	public String quryApiOfferType(ProdOffer prodOffer) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.quryApiOfferTyp", prodOffer);
	}
	
	@Override
	public List<Map> queryNewApiList(Map map) {
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-apiStroe.queryNewApiList", map);
	}


	@Override
	public String quryApiCataName(ProdOffer prodOffer) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.quryApiCataName", prodOffer);
	}


	@Override
	public List<Directory> queryApiCategoryList(Map map) {
		return (List<Directory>)sqlMapDao.queryForList("eaap-op2-portal-apiStroe.queryApiCategory", map);
	}


	@Override
	public Integer updateVisitor(ItemCnt itemCnt) {
		return (Integer) sqlMapDao.update("eaap-op2-portal-apiStroe.updateVisitor", itemCnt);
	}


	@Override
	public List<Map> quryApiOfferListByCategory(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryApiOfferListByCategory",param);
	}


	@Override
	public List<Map> quryAllApiOfferList(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryAllApiOfferList",param);
	}


	@Override
	public List<Map> quryAllLimitApiOfferList(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryAllLimitApiOfferList",param);
	}


	@Override
	public List<Map> queryNewLimitApiList(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.queryNewLimitApiList",param);
	}


	@Override
	public List<Map> quryApiOfferLimitList(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.quryApiOfferLimitList",param);
	}


	@Override
	public Integer queryApiOfferCnt(Map<String, Object> paraMap) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.queryApiOfferCnt", paraMap);
	}


	@Override
	public String querySeriveCode(Api api) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.querySeriveCode", api);
	}


//	@Override
//	public List<Directory> queryDirList(Directory directory) {
//		return sqlMapDao.queryForList("eaap-op2-portal-apiStroe.queryDirList",directory);
//	}
//
//
//	@Override
//	public Integer queryFaDirId(Directory directory) {
//		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-apiStroe.queryFaDirId", directory);
//	}
	
}
