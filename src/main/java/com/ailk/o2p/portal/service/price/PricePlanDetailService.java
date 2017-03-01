package com.ailk.o2p.portal.service.price;


import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.BaseTariffItemRel;
import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.FreeResource;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.OneTimeCharge;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.RatingDiscount;

public interface PricePlanDetailService {

	public List<ComponentPrice> queryComponentPrice(Map map);
	public Integer countComponentPrice(Map map);
	public Integer addComponentPrice(ComponentPrice componentPrice);
	public Integer updateComponentPrice(ComponentPrice componentPrice);
	
	public BasicTariff queryBasicTariff(BasicTariff basicTariff);
	public Integer updateBasicTariff(BasicTariff basicTariff);
	public Integer addBasicTariff(BasicTariff basicTariff);
	
	public Integer addRecurringFee(PecurringFee pecurringFee);
	public Integer updateRecurringFee(PecurringFee pecurringFee);
	public PecurringFee queryRecurringFee(PecurringFee pecurringFee);
	
	public Integer addBillingDiscount(BillingDiscount billingDiscount);
	public Integer updateBillingDiscount(BillingDiscount billingDiscount);
	public BillingDiscount queryBillingDiscount(BillingDiscount billingDiscount);
	
	public Integer addOneTimeCharge(OneTimeCharge oneTimeCharge);
	public Integer updateOneTimeCharge(OneTimeCharge oneTimeCharge);
	public OneTimeCharge queryOneTimeCharge(OneTimeCharge oneTimeCharge);
	
	public Integer addRatingDiscount(RatingDiscount ratingDiscount);
	public Integer updateRatingDiscount(RatingDiscount ratingDiscount);
	public RatingDiscount queryRatingDiscount(RatingDiscount ratingDiscount);
	
	public List<RatingCurverDetail> queryRatingCurveDetail(RatingCurverDetail ratingCurverDetail);
	public Integer addRatingCurveDetail(RatingCurverDetail ratingCurverDetail);
	public Integer updateRatingCurveDetail(RatingCurverDetail ratingCurverDetail);
	public void delRatingCurveDetail(RatingCurverDetail ratingCurverDetail);
	
	public List<BaseTariffItemRel> getBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel);
	public Integer addBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel);
	public void delBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel); 

	public FreeResource queryFreeResource(FreeResource freeResource);
	public Integer updateFreeResource(FreeResource freeResource);
	public Integer addFreeResource(FreeResource freeResource);
}
