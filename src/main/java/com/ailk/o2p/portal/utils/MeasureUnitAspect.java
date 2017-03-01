package com.ailk.o2p.portal.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.OneTimeCharge;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.RatingDiscount;
import com.ailk.eaap.op2.bo.SettleRuleCondition;
import com.ailk.eaap.op2.dao.MeasureUnitDao;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.cache.CacheFactory;
import com.ailk.o2p.portal.cache.ICache;
import com.ailk.o2p.portal.dao.price.PricePlanDetailDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.Logger;

public class MeasureUnitAspect {
	
	private static final Logger log = Logger.getLog(MeasureUnitAspect.class);
	@Autowired
	private MeasureUnitDao measureUnitDao;
	@Autowired
    private PricePlanDetailDao pricePlanDetailDao;

	@SuppressWarnings("unchecked")
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try{
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			log.debug("############## MeasureUnitAspect className:" + joinPoint.getTarget().getClass().getName() + " || method：" + method.getName());
			
			ICache cache = CacheFactory
					.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
			Map<String,String> cacheMap =  (Map<String, String>) cache.get("cacheMap");
			
			
			if(method.getName().startsWith("query")){
				result = joinPoint.proceed();
				
				if(result instanceof PecurringFee){
					
					result = this.exchangePecurringFee(result, cacheMap,Boolean.TRUE);;
				}else if(result instanceof List){
					for(Object o : ((List<?>) result)){
						if(o instanceof RatingCurverDetail){
							this.changeRatingCurverDetail(o,cacheMap,Boolean.TRUE);
						}
					}
					
				}else if(result instanceof OneTimeCharge){
					
					result = this.exchangeOneTimeCharge(result, cacheMap, Boolean.TRUE);
				}else if(result instanceof BillingDiscount){
					
					result = this.exchangeBillingDiscount(result, cacheMap, Boolean.TRUE);
				}else if(result instanceof RatingDiscount){
					
					result = this.exchangeRatingDiscount(result, cacheMap, Boolean.TRUE);
				}
				
			}else{
				Object[] args = joinPoint.getArgs();
				if(args != null && args.length > 0){
					if(args[0] instanceof PecurringFee){
						
						this.exchangePecurringFee(args[0], cacheMap,Boolean.FALSE);
					}else if(args[0] instanceof RatingCurverDetail){
						
						this.changeRatingCurverDetail(args[0],cacheMap,Boolean.FALSE);
					}else if(args[0] instanceof OneTimeCharge){
						
						this.exchangeOneTimeCharge(args[0], cacheMap, Boolean.FALSE);
					}else if(args[0] instanceof BillingDiscount){
						
						this.exchangeBillingDiscount(args[0], cacheMap, Boolean.FALSE);
					}else if(args[0] instanceof RatingDiscount){
						
						this.exchangeRatingDiscount(args[0], cacheMap, Boolean.FALSE);
					}
					
				}
				
				result = joinPoint.proceed();
			}
			
		}catch(Exception e){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"**************** MeasureUnitAspect :" + e.getMessage()  , null);
		}
		
		return result;
	}
	
	private SettleRuleCondition exchangeSettleRuleCondition(Object o,Map<String,String> cacheMap,Boolean flag){
		String measureType = null;
		String destMeasureId = null;
		
		SettleRuleCondition settleRuleCondition = (SettleRuleCondition) o;
//		measureType = String.valueOf(billingDiscount.getCurrencyUnitType()).substring(0, 3);
//		destMeasureId = cacheMap.get(measureType + "_0");
//		log.debug("cache ==============================", destMeasureId);
		
		return settleRuleCondition;
	}
	
	private BillingDiscount exchangeBillingDiscount(Object o,Map<String,String> cacheMap,Boolean flag){
		String measureType = null;
		String destMeasureId = null;
		
		BillingDiscount billingDiscount = (BillingDiscount) o;
		measureType = String.valueOf(billingDiscount.getCurrencyUnitType()).substring(0, 3);
		destMeasureId = cacheMap.get(measureType + "_0");
		log.debug("cache ==============================", destMeasureId);
		
		if(flag){
			billingDiscount.setMaxiMum(this.getMeasureUnitDao().isMeasureUnitExchange(billingDiscount.getMaxiMum(), destMeasureId,
					String.valueOf(billingDiscount.getCurrencyUnitType())));
		}else{
			billingDiscount.setMaxiMum(this.getMeasureUnitDao().isMeasureUnitExchange(billingDiscount.getMaxiMum(),
					String.valueOf(billingDiscount.getCurrencyUnitType()), destMeasureId));
		}
		
		return billingDiscount;
	}
	
	private RatingDiscount exchangeRatingDiscount(Object o,Map<String,String> cacheMap,Boolean flag){
		String measureType = null;
		String destMeasureId = null;
		
		RatingDiscount ratingDiscount = (RatingDiscount) o;
		measureType = String.valueOf(ratingDiscount.getCurrencyUnitType()).substring(0, 3);
		destMeasureId = cacheMap.get(measureType + "_0");
		log.debug("cache ==============================", destMeasureId);
		
		if(flag){
			ratingDiscount.setMaxiMum(this.getMeasureUnitDao().isMeasureUnitExchange(ratingDiscount.getMaxiMum(), destMeasureId,
					String.valueOf(ratingDiscount.getCurrencyUnitType())));
		}else{
			ratingDiscount.setMaxiMum(this.getMeasureUnitDao().isMeasureUnitExchange(ratingDiscount.getMaxiMum(),
					String.valueOf(ratingDiscount.getCurrencyUnitType()), destMeasureId));
		}
		
		return ratingDiscount;
	}
	
	private OneTimeCharge exchangeOneTimeCharge(Object o,Map<String,String> cacheMap,Boolean flag){
		String measureType = null;
		String destMeasureId = null;
		
		OneTimeCharge oneTimeCharge = (OneTimeCharge) o;
		measureType = String.valueOf(oneTimeCharge.getCurrencyUnitType()).substring(0, 3);
		destMeasureId = cacheMap.get(measureType + "_0");
		log.debug("cache ==============================", destMeasureId);
		
		if(flag){
			oneTimeCharge.setCurrencyUnitVal(this.getMeasureUnitDao().isMeasureUnitExchange(oneTimeCharge.getCurrencyUnitVal(), destMeasureId,
					String.valueOf(oneTimeCharge.getCurrencyUnitType())));
		}else{
			oneTimeCharge.setCurrencyUnitVal(this.getMeasureUnitDao().isMeasureUnitExchange(oneTimeCharge.getCurrencyUnitVal(),
					String.valueOf(oneTimeCharge.getCurrencyUnitType()), destMeasureId));
		}
		
		return oneTimeCharge;
	}
	
	private PecurringFee exchangePecurringFee(Object o,Map<String,String> cacheMap,Boolean flag) {
		String measureType = null;
		String destMeasureId = null;
		
		PecurringFee recurringFee = (PecurringFee) o;
		measureType = String.valueOf(recurringFee.getCurrencyUnitType()).substring(0, 3);
		destMeasureId = cacheMap.get(measureType + "_0");
		log.debug("cache ==============================", destMeasureId);
		
		if(flag){
			recurringFee.setCurrencyUnitVal(this.getMeasureUnitDao().isMeasureUnitExchange(recurringFee.getCurrencyUnitVal(), destMeasureId,
					String.valueOf(recurringFee.getCurrencyUnitType())));
		}else{
			recurringFee.setCurrencyUnitVal(this.getMeasureUnitDao().isMeasureUnitExchange(recurringFee.getCurrencyUnitVal(),
					String.valueOf(recurringFee.getCurrencyUnitType()), destMeasureId));
		}
		
		return recurringFee;
	}
	
	private RatingCurverDetail changeRatingCurverDetail(Object o,Map<String,String> cacheMap,Boolean flag){
		String measureType = null;
		String destMeasureId = null;
		Integer currencyUnitType = null;
		
		RatingCurverDetail ratingCurverDetail = (RatingCurverDetail) o;
		
		BasicTariff basicTariff = new BasicTariff();
		basicTariff.setPriceId(ratingCurverDetail.getPriceId());
		basicTariff = this.getPricePlanDetailDao().queryBasicTariff(basicTariff);
		if(null == basicTariff){
			BillingDiscount billingDiscount = new BillingDiscount();
			billingDiscount.setPriceId(ratingCurverDetail.getPriceId());
			billingDiscount = this.getPricePlanDetailDao().queryBillingDiscount(billingDiscount);
			currencyUnitType = billingDiscount.getCurrencyUnitType();
		}else{
			currencyUnitType = basicTariff.getCurrencyUnitType();
		}
		
		measureType = String.valueOf(currencyUnitType).substring(0, 3);
		destMeasureId = cacheMap.get(measureType + "_0");
		log.debug("cache ==============================", destMeasureId);
		
		if(flag){
			if(null != ratingCurverDetail.getRateVal()){
				ratingCurverDetail.setRateVal(this.getMeasureUnitDao().isMeasureUnitExchange(ratingCurverDetail.getRateVal(), destMeasureId,
						String.valueOf(currencyUnitType)));
			}
			if(null != ratingCurverDetail.getBaseVal()){
				ratingCurverDetail.setBaseVal(this.getMeasureUnitDao().isMeasureUnitExchange(ratingCurverDetail.getBaseVal(), destMeasureId,
						String.valueOf(currencyUnitType)));
			}
			
		}else{
			if(null != ratingCurverDetail.getRateVal()){
				ratingCurverDetail.setRateVal(this.getMeasureUnitDao().isMeasureUnitExchange(ratingCurverDetail.getRateVal(),
						String.valueOf(currencyUnitType), destMeasureId));
			}
			if(null != ratingCurverDetail.getBaseVal()){
				ratingCurverDetail.setBaseVal(this.getMeasureUnitDao().isMeasureUnitExchange(ratingCurverDetail.getBaseVal(),
						String.valueOf(currencyUnitType), destMeasureId));
			}
			
		}
		
		return ratingCurverDetail;
	}
	
	
	
	public MeasureUnitDao getMeasureUnitDao() {
		if(measureUnitDao == null ){
			measureUnitDao = (MeasureUnitDao) SpringContextUtil.getBean("measureUnitDao"); 
		}
		return measureUnitDao;
	}
	public void setMeasureUnitDao(MeasureUnitDao measureUnitDao) {
		this.measureUnitDao = measureUnitDao;
	}
	public PricePlanDetailDao getPricePlanDetailDao() {
		if(pricePlanDetailDao == null){
			pricePlanDetailDao = (PricePlanDetailDao) SpringContextUtil.getBean("pricePlanDetailDao");
		}
		return pricePlanDetailDao;
	}
	public void setPricePlanDetailDao(PricePlanDetailDao pricePlanDetailDao) {
		this.pricePlanDetailDao = pricePlanDetailDao;
	}
	
}
