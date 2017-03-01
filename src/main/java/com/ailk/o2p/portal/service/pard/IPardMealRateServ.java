package com.ailk.o2p.portal.service.pard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;

public interface IPardMealRateServ {

	/**
	 * 查询语音通话
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getVoiceList(Map<String, String> map);

	/**
	 * 查询数据业务
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getDataList(Map<String, String> map);

	/**
	 * 查询短信服务
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getMsgList(Map<String, String> map);

	/**
	 * 销售品对应产品
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getProductList(Map<String, String> map);


	/**
	 * 插入各表
	 * @param prodOffer
	 * @param prodOfferAttrList
	 * @param offerProdRelList
	 * @param prodOfferPricing 
	 */
	void insertAll(ProdOffer prodOffer, List<ProdOfferAttr> prodOfferAttrList,
			List<OfferProdRel> offerProdRelList, ProdOfferPricing prodOfferPricing);



	/**
	 * 获取ProdOfferPricing序列
	 * @return
	 */
	String getProdOfferPricingId();

	/**
	 * 
	 * @param mainDataType
	 * @return
	 */
	List<MainDataType> selectMainDataType(MainDataType mainDataType) ;

	/**
	 * 
	 * @param mainData
	 * @return
	 */
	List<MainData> selectMainData(MainData mainData);

	public List<HashMap> showProdOfferList (Map map);

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
	 * 修改全部
	 * @param prodOffer
	 * @param prodOfferAttrList
	 * @param offerProdRelList
	 */
	void updateAll(ProdOffer prodOffer, List<ProdOfferAttr> prodOfferAttrList,
			List<OfferProdRel> offerProdRelList);

	/**
	 * 删除操作
	 * @param prodOffer
	 */
	void deletePardMealRate(ProdOffer prodOffer);

	/**
	 * 更改销售品状态
	 * @param prodOffer
	 */
	void updateProdOffer(ProdOffer prodOffer);

	/**
	 * 通过orgId查询EXT_PROD_OFFER_ID是否有多个
	 * @param map
	 * @return
	 */
	int queryMoreExtByOrgId(Map<String, String> map);

}