package com.ailk.o2p.portal.dao.pard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;

public interface IPardMealRateDAO {

	/**
	 * 套餐议价语音通话
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getVoiceList(Map<String, String> map);

	/**
	 * 套餐议价数据业务
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getDataList(Map<String, String> map);

	/**
	 * 套餐议价短信服务
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getMsgList(Map<String, String> map);

	/**
	 * 销售品对应产品列表
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getProductList(Map<String, String> map);

	/**
	 * 
	 * @param prodOffer
	 */
	Integer insertProdOffer(ProdOffer prodOffer);

	/**
	 * 
	 * @param prodOfferAttrList
	 */
	void insertProdOfferAttr(List<ProdOfferAttr> prodOfferAttrList);

	/**
	 * 
	 * @param offerProdRelList
	 */
	void insetOfferProdRel(List<OfferProdRel> offerProdRelList);


	public BigDecimal getProdOfferId();
	
	public Integer getProdOfferAttrId();
	
	public Integer getOfferProdRelId();
	
	/**
	 * 
	 * @return
	 */
	String getProdOfferPricingId();
	
	/**
	 * 
	 * @param prodOfferPricing
	 */
	void insertProdOfferPricing(ProdOfferPricing prodOfferPricing);

	List<HashMap> queryProdOfferCount(Map map);

	List<HashMap> showProdOfferList(Map map);

	/**
	 * 
	 * @param prodOffer
	 * @return
	 */
	ProdOffer getProdOffer(ProdOffer prodOffer);

	/**
	 * 获取定价信息
	 * @param prodOffer
	 * @return
	 */
	List<HashMap> selectPricingListByOfferId(ProdOffer prodOffer);

	/**
	 * 
	 * @param prodOffer
	 */
	void updateProdOffer(ProdOffer prodOffer);

	/**
	 * 
	 * @param poa
	 */
	void updateProdOfferAttr(ProdOfferAttr poa);

	/**
	 * 
	 * @param opr
	 */
	void updateOfferProdRel(OfferProdRel opr);

	/**
	 * 删除
	 * @param prodOffer
	 */
	void deletePardMealRate(ProdOffer prodOffer);

	/**
	 * 
	 * @param map
	 * @return
	 */
	int queryMoreExtByOrgId(Map<String, String> map);

}
