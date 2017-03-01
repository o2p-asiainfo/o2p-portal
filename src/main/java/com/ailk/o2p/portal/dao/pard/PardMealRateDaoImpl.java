package com.ailk.o2p.portal.dao.pard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NData;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NDatas;
import com.linkage.rainbow.dao.SqlMapDAO;

public class PardMealRateDaoImpl implements IPardMealRateDAO {

	private SqlMapDAO sqlMapDao;

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	public BigDecimal getProdOfferId() {
		return new BigDecimal(sqlMapDao.queryForObject("prodOffer.selectProdOfferSeq", null).toString());
	}
	
	public Integer getProdOfferAttrId() {
		return Integer.valueOf(sqlMapDao.queryForObject("prodOfferAttr.selectProdOfferAttrSeq", null).toString());
	}
	
	public Integer getOfferProdRelId() {
		return Integer.valueOf(sqlMapDao.queryForObject("offerProdRel.selectOfferProdRelSeq", null).toString());
	}
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "CEP_NAME") 
		}
	)
	public List<Map<String, Object>> getVoiceList(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.getVoiceList", map);  
	}
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "CEP_NAME") 
		}
	)
	public List<Map<String, Object>> getDataList(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.getDataList", map);
	}

	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "CEP_NAME") 
		}
	)
	public List<Map<String, Object>> getMsgList(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.getMsgList", map);
	}

	public List<Map<String, Object>> getProductList(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.getProductList", map);
	}

	public Integer insertProdOffer(ProdOffer prodOffer) {
		return (Integer)sqlMapDao.insert("prodOffer.insertProdOffer", prodOffer);
	}

	public void insertProdOfferAttr(List<ProdOfferAttr> prodOfferAttrList) {
		for(ProdOfferAttr prodOfferAttr : prodOfferAttrList){
			sqlMapDao.insert("prodOfferAttr.insertProdOfferAttr", prodOfferAttr) ;
		}
	}

	public void insetOfferProdRel(List<OfferProdRel> offerProdRelList) {
		for(OfferProdRel offerProdRel : offerProdRelList){
			sqlMapDao.insert("offerProdRel.insertOfferProdRel", offerProdRel) ;
		}
	}


	public String getProdOfferPricingId() {
		return (String)sqlMapDao.queryForObject("pricing.selectProdOfferPricinSeq", null);
	}

	public void insertProdOfferPricing(ProdOfferPricing prodOfferPricing) {
		sqlMapDao.insert("pricing.insertProdOfferPricing", prodOfferPricing);
	}

	public List<HashMap> queryProdOfferCount(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.queryProdOfferCount", map);
	}

	public List<HashMap> showProdOfferList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.showProdOfferList", map);
	}

	public ProdOffer getProdOffer(ProdOffer prodOffer) {
		return (ProdOffer)sqlMapDao.queryForObject("prodOffer.selectProdOffer", prodOffer);
	}

	public List<HashMap> selectPricingListByOfferId(ProdOffer prodOffer) {
		return (List<HashMap>)sqlMapDao.queryForList("eaap-op2-portal-pardMealRate.selectPricingListByOfferId", prodOffer) ;
	}

	public void updateOfferProdRel(OfferProdRel opr) {
		sqlMapDao.update("offerProdRel.updateOfferProdRel", opr) ;
	}

	public void updateProdOffer(ProdOffer prodOffer) {
		sqlMapDao.update("prodOffer.updateProdOffer", prodOffer) ;
	}

	public void updateProdOfferAttr(ProdOfferAttr poa) {
		sqlMapDao.update("prodOfferAttr.updateProdOfferAttr", poa) ;
	}

	public void deletePardMealRate(ProdOffer prodOffer) {
		sqlMapDao.update("prodOffer.updateProdOffer", prodOffer) ;
	}

	public int queryMoreExtByOrgId(Map<String, String> map) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-pardMealRate.queryMoreExtByOrgId", map);
	}

}
