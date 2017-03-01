package com.ailk.o2p.portal.service.price;

 import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.PriceItem;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.ScopeApplication;

public interface PriceService {

	public List<MainData> selectMainData(MainData mainData);
	public List<MainDataType> selectMainDataType(MainDataType mainDataType);
	
	//定价计划
	public List<PricingPlan> queryPricingPlan(PricingPlan pricingPlan);
	public Integer countpricingPlan(PricingPlan pricingPlan);
	public Integer addPricingPlan(PricingPlan pricingPlan);
	public Integer updatePricingPlan(PricingPlan pricingPlan);
	public List<PricingPlan> getPricingPlan(Map map);
	//定价计划关联
	public List<ProdOfferPricing> queryProdOfferPricing(ProdOfferPricing prodOfferPricing);
	public Integer updateProdOfferPricing(ProdOfferPricing prodOfferPricing);
	public Integer addProdOfferPricing(ProdOfferPricing prodOfferPricing);
	//定价对象
	public Integer addPricingTarget(PricingTarget pricingTarget);
	public Integer updatePricingTarget(PricingTarget pricingTarget);
	public List<PricingTarget> queryPricingTarget(PricingTarget pricingTarget);
	//定价-销售品关联
	public Integer addProdRelPricing(OfferProdRelPricing offerProdRelPricing);
	public Integer updateProdRelPricing(OfferProdRelPricing offerProdRelPricing);
	public List<OfferProdRelPricing> queryOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing);
	//科目
	public List<PriceItem> queryPriceItem(PriceItem priceItem);
	public List<PriceItem> queryPriceItemList(Map map);
	public Integer countPriceItemList(Map map);
	//适用范围
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication);
	//
	public List<Map<String,Object>> selectProductNameById(Map<String,Object> map);
	public void deletePardOffertPricePlan(String priceInfoId ,String prodOfferId,String pricingTargetId)throws Exception;
	public List getSystemStatus(Map map);
}
