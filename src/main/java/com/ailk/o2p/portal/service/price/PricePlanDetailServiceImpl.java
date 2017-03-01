package com.ailk.o2p.portal.service.price;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.BaseTariffItemRel;
import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.FreeResource;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.OneTimeCharge;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.FreeResourceSeg;
import com.ailk.eaap.op2.bo.RatingDiscount;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.price.PricePlanDetailDao;
import com.ailk.o2p.portal.service.pardoffer.IPardOfferServ;
import com.ailk.o2p.portal.utils.PropertiesLoader;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;
@Service
public class PricePlanDetailServiceImpl implements PricePlanDetailService {
	@Autowired
    private MainDataDao mainDataSqlDAO ;
	@Autowired
    private PricePlanDetailDao pricePlanDetailDao;
    @Autowired
    private MainDataTypeDao mainDataTypeSqlDAO;
    @Autowired
	private PriceServiceImpl priceServ;
    @Autowired
	private AttrSpecServ attrSpecService;
	@Autowired
	private IPardOfferServ pardOfferServ;

	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}
	public PricePlanDetailDao getPricePlanDetailDao() {
		return pricePlanDetailDao;
	}
	public void setPricePlanDetailDao(PricePlanDetailDao pricePlanDetailDao) {
		this.pricePlanDetailDao = pricePlanDetailDao;
	}

	@Override
	public List<ComponentPrice> queryComponentPrice(Map map) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.queryComponentPrice(map);
	}

	@Override
	public Integer countComponentPrice(Map map) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.countComponentPrice(map);
	}

	@Override
	public Integer addComponentPrice(ComponentPrice componentPrice) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.addComponentPrice(componentPrice);
	}

	@Override
	public Integer updateComponentPrice(ComponentPrice componentPrice) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.updateComponentPrice(componentPrice);
	}

	@Override
	public BasicTariff queryBasicTariff(BasicTariff basicTariff) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.queryBasicTariff(basicTariff);
	}

	@Override
	public Integer updateBasicTariff(BasicTariff basicTariff) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.updateBasicTariff(basicTariff);
	}

	@Override
	public Integer addBasicTariff(BasicTariff basicTariff) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.addBasicTariff(basicTariff);
	}


	@Override
	public FreeResource queryFreeResource(FreeResource freeResource) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.queryFreeResource(freeResource);
	}
	@Override
	public Integer updateFreeResource(FreeResource freeResource) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.updateFreeResource(freeResource);
	}
	@Override
	public Integer addFreeResource(FreeResource freeResource) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.addFreeResource(freeResource);
	}
	
	@Override
	public Integer addRecurringFee(PecurringFee pecurringFee) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.addRecurringFee(pecurringFee);
	}

	@Override
	public Integer updateRecurringFee(PecurringFee pecurringFee) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.updateRecurringFee(pecurringFee);
	}

	@Override
	public PecurringFee queryRecurringFee(PecurringFee pecurringFee) {
		// TODO Auto-generated method stub
		return pricePlanDetailDao.queryRecurringFee(pecurringFee);
	}

	public Integer addBillingDiscount(BillingDiscount billingDiscount){
		return pricePlanDetailDao.addBillingDiscount(billingDiscount);
	}
	public Integer updateBillingDiscount(BillingDiscount billingDiscount){
		return pricePlanDetailDao.updateBillingDiscount(billingDiscount);
	}
	public BillingDiscount queryBillingDiscount(BillingDiscount billingDiscount){
		return pricePlanDetailDao.queryBillingDiscount(billingDiscount);
	}
	
	
	public List<RatingCurverDetail> queryRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return pricePlanDetailDao.queryRatingCurveDetail(ratingCurverDetail);
	}
	public Integer addRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return pricePlanDetailDao.addRatingCurveDetail(ratingCurverDetail);
	}
	public Integer updateRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return pricePlanDetailDao.updateRatingCurveDetail(ratingCurverDetail);
	}
	public void delRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		pricePlanDetailDao.delRatingCurveDetail(ratingCurverDetail);
	}
	
	public Integer addOneTimeCharge(OneTimeCharge oneTimeCharge){
		return pricePlanDetailDao.addOneTimeCharge(oneTimeCharge);
	}
	public Integer updateOneTimeCharge(OneTimeCharge oneTimeCharge){
		return pricePlanDetailDao.updateOneTimeCharge(oneTimeCharge);
	}
	public OneTimeCharge queryOneTimeCharge(OneTimeCharge oneTimeCharge){
		return pricePlanDetailDao.queryOneTimeCharge(oneTimeCharge);
	}
	
	public Integer addRatingDiscount(RatingDiscount ratingDiscount){
		return pricePlanDetailDao.addRatingDiscount(ratingDiscount);
	}
	public Integer updateRatingDiscount(RatingDiscount ratingDiscount){
		return pricePlanDetailDao.updateRatingDiscount(ratingDiscount);
	}
	public RatingDiscount queryRatingDiscount(RatingDiscount ratingDiscount){
		return pricePlanDetailDao.queryRatingDiscount(ratingDiscount);
	}
	
	////
	public List<BaseTariffItemRel> getBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		return pricePlanDetailDao.getBaseTariffItemRel(baseTariffItemRel);
	}
	public Integer addBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		return pricePlanDetailDao.addBaseTariffItemRel(baseTariffItemRel);
	}
	public void delBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		pricePlanDetailDao.delBaseTariffItemRel(baseTariffItemRel);
	}
	public JSONObject saveOrUpdatePricePlan(final HttpServletRequest request,
			final HttpServletResponse response,String formSubStr,PropertiesLoader i18nLoader)throws Exception {
		JSONObject retJson = new JSONObject();
		String[] formSubAry=formSubStr.split(",");//1_1,1_2
		String num="";
		String prodOfferId = getRequestValue(request, num+"prodOfferId");
		String priceId = getRequestValue(request, num+"pricingInfoId");
		String applicType = getRequestValue(request, num+"applicType");
		String targetId = getRequestValue(request, num+"pricingTargetId");
		String pricingName = getRequestValue(request, "pricingName");
		String licenseNbr = getRequestValue(request, num+"licenseNbr");
		String billingPriorit = getRequestValue(request, num+"billingPriorit");
		String desc = getRequestValue(request, num+"pricingDesc");
		String feffd = getRequestValue(request, num+"formatEffDate");
		String fexpd = getRequestValue(request, num+"formatExpDate");
		
		String[] pricePlanSpecIds = getRequestValue(request, num+"pricePlanSpecIds").split(";"); 
		String[] defaultValNames = getRequestValue(request, num+"defaultValNames").split(";"); 
		String[] defaultVals =getRequestValue(request, num+"defaultVals").split(";"); 
		
		String fFixedExpireDate = getRequestValue(request, num+"fFixedExpireDate");
		PricingPlan pricingPlan=new PricingPlan();
		PricePlanLifeCycle pricePlanLifeCycle=new PricePlanLifeCycle();
		OfferProdRelPricing offerProdRelPricing=new OfferProdRelPricing();
		PricingTarget pricingTarget=new PricingTarget();
		ProdOfferPricing prodOfferPricing=new ProdOfferPricing();
			
		pricingPlan.setEffDate("".equals(StringUtil.valueOf(feffd))?null:DateUtil.stringToDate(feffd.replace("-","/"), "yyyy/MM/dd")) ;
		pricingPlan.setExpDate("".equals(StringUtil.valueOf(fexpd))?null:DateUtil.stringToDate(fexpd.replace("-","/"), "yyyy/MM/dd")) ;
		pricePlanLifeCycle.setFixedExpireDate("".equals(StringUtil.valueOf(fFixedExpireDate))?null:DateUtil.stringToDate(fFixedExpireDate.replace("-","/"), "yyyy/MM/dd"));
	
		String relIds = getRequestValue(request, num+"relIds");
		String prodNames = getRequestValue(request, num+"prodNames");

		pricingPlan.setPricingName(pricingName);
		if(!"".equals(licenseNbr)&&licenseNbr!=null){
			pricingPlan.setLicenseNbr(Integer.parseInt(licenseNbr));
		}
		if(!"".equals(billingPriorit)&&billingPriorit!=null){
			pricingPlan.setBillingPriority(Integer.parseInt(billingPriorit));
		}
		if(!"".equals(applicType)&&applicType!=null){
			pricingPlan.setApplicType(applicType);
		}else{
			pricingPlan.setApplicType("1");
		}
		pricingPlan.setPricingDesc(desc);
		
		String actionType = getRequestValue(request, "actionType");
		if("update".equals(actionType)){
			pricingPlan.setPricingInfoId(Integer.parseInt(priceId));
			priceServ.updatePricingPlan(pricingPlan);
			pricingPlan.setPricingInfoId(Integer.parseInt(priceId));
			retJson.put("priceInfoId", priceId+"");
			
			offerProdRelPricing.setStatusCd("10");
			offerProdRelPricing.setPricingInfoId(Integer.parseInt(targetId));
			List<OfferProdRelPricing> offerProdRelPricingList = priceServ.queryOfferProdRelPricing(offerProdRelPricing);
			for(OfferProdRelPricing o : offerProdRelPricingList){
				o.setStatusCd("11");
				priceServ.updateProdRelPricing(o);
			}
			
			PricngPlanAttr pricngPlanAttr = new PricngPlanAttr();
			pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
			pricngPlanAttr.setStatusCd("10");
			List<PricngPlanAttr> list = attrSpecService.queryPricngPlanAttr(pricngPlanAttr);
			if(list.size()==0){
				if(null!=pricePlanSpecIds&&pricePlanSpecIds.length>0){
					for(int i=0;i<pricePlanSpecIds.length;i++){
						pricngPlanAttr = new PricngPlanAttr();
						pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
						pricngPlanAttr.setStatusCd("10");
						pricngPlanAttr.setAttrSpecId(new BigDecimal(pricePlanSpecIds[i]));
						pricngPlanAttr.setSeqId(1);
						if(defaultValNames[i].indexOf(',')>=0){
							pricngPlanAttr.setDefaultValue(defaultVals[i].replaceAll(",", "/"));
							pricngPlanAttr.setDefaultValueName(defaultValNames[i].replaceAll(",", "/"));
						}else{
							pricngPlanAttr.setDefaultValue(defaultVals[i]);
							pricngPlanAttr.setDefaultValueName(defaultValNames[i]);
						}
						attrSpecService.addPricngPlanAttr(pricngPlanAttr);
					}
				}
			}else{
				for(PricngPlanAttr p:list){
					p.setStatusCd("11");
					attrSpecService.updatePricngPlanAttr(p);
				}
				if(null!=pricePlanSpecIds){
					for(int i=0;i<pricePlanSpecIds.length;i++){
						pricngPlanAttr = new PricngPlanAttr();
						pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
						pricngPlanAttr.setStatusCd("10");
						pricngPlanAttr.setSeqId(1);
						pricngPlanAttr.setAttrSpecId(new BigDecimal(pricePlanSpecIds[i]));
						if(defaultValNames[i].indexOf(',')>=0){
							pricngPlanAttr.setDefaultValue(defaultVals[i].replaceAll(",", "/"));
							pricngPlanAttr.setDefaultValueName(defaultValNames[i].replaceAll(",", "/"));
						}else{
							pricngPlanAttr.setDefaultValue(defaultVals[i]);
							pricngPlanAttr.setDefaultValueName(defaultValNames[i]);
						}
						attrSpecService.addPricngPlanAttr(pricngPlanAttr);
					}
				}
			}
			retJson.put("pricingTargetId", targetId);
			
			for(String id : relIds.split(",")){
				offerProdRelPricing.setOfferProdRelaId(Integer.parseInt(id));
				offerProdRelPricing.setPricingInfoId(Integer.parseInt(targetId));
				offerProdRelPricing.setStatusCd("10");
				priceServ.addProdRelPricing(offerProdRelPricing);
			}
			
			pricePlanLifeCycle.setPricePlanId(new Long(priceId));
			pricePlanLifeCycle.setStatusCd("10");
			attrSpecService.updatePricePlanLifeCycle(pricePlanLifeCycle);
		}else {
			Integer priceInfoId = priceServ.addPricingPlan(pricingPlan);
			pricingPlan.setPricingInfoId(priceInfoId);
			retJson.put("priceInfoId", priceInfoId+"");
			
			pricingTarget.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			pricingTarget.setPricingTargetName(prodNames);
			pricingTarget.setPricingTargetType("1");
			pricingTarget.setStatusCd("10");
			Integer pricingTargetId = priceServ.addPricingTarget(pricingTarget);
			retJson.put("pricingTargetId", pricingTargetId+"");

			for(String id : relIds.split(",")){
				offerProdRelPricing.setOfferProdRelaId(Integer.parseInt(id));
				offerProdRelPricing.setPricingInfoId(pricingTargetId);
				offerProdRelPricing.setStatusCd("10");
				priceServ.addProdRelPricing(offerProdRelPricing);
			}
			if(null!=pricePlanSpecIds&&pricePlanSpecIds.length>0){
				PricngPlanAttr pricngPlanAttr=null;
				for(int i=0;i<pricePlanSpecIds.length;i++){
					pricngPlanAttr = new PricngPlanAttr();
					pricngPlanAttr.setPricingInfoId(new BigDecimal(priceInfoId));
					pricngPlanAttr.setStatusCd("10");
					pricngPlanAttr.setAttrSpecId(new BigDecimal(pricePlanSpecIds[i]));
					pricngPlanAttr.setSeqId(1);
					if("".equals(defaultValNames[i])){
						defaultValNames[i] = defaultVals[i];
					}
					if(defaultValNames[i].indexOf(',')>=0){
						pricngPlanAttr.setDefaultValue(defaultVals[i].replaceAll(",", "/"));
						pricngPlanAttr.setDefaultValueName(defaultValNames[i].replaceAll(",", "/"));
					}else{
						pricngPlanAttr.setDefaultValue(defaultVals[i]);
						pricngPlanAttr.setDefaultValueName(defaultValNames[i]);
					}
					attrSpecService.addPricngPlanAttr(pricngPlanAttr);
				}
			}
			
			prodOfferPricing.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			prodOfferPricing.setPricingInfoId(priceInfoId);
			prodOfferPricing.setPricingTargetId(pricingTargetId);
			priceServ.addProdOfferPricing(prodOfferPricing);
			
			pricePlanLifeCycle.setPricePlanId(Long.valueOf(priceInfoId));
			pricePlanLifeCycle.setStatusCd("10");
			attrSpecService.addPricePlanLifeCycle(pricePlanLifeCycle);
			
			//定价计划规则的子规则
			String type=null;
			for(int i=0;i<formSubAry.length;i++){
				type=formSubAry[i].split("_")[1];//1_1
				if("1".equals(type))saveOrUpdateBasicTariff(request,response,formSubAry[i],i18nLoader);//BasicTariff
				else if("2".equals(type))saveOrUpdateRecurringFee(request,response,formSubAry[i],i18nLoader);//
				else if("3".equals(type))saveOrUpdateRatingDiscount(request,response,formSubAry[i]);//
				else if("4".equals(type))saveOrUpdateOneTimeFee(request,response,formSubAry[i],i18nLoader);//
				else if("5".equals(type))saveOrUpdateBillingDiscount(request,response,formSubAry[i]);//
			}
		}
		return retJson;
	}
	
	public String saveOrUpdateBasicTariff(final HttpServletRequest request,
			final HttpServletResponse response,String key,PropertiesLoader i18nLoader )throws Exception {
		String other =getRequestValue(request,"other");
		String details =getRequestValue(request,"details");
		String priceName =getRequestValue(request,"priceName");
		String description =getRequestValue(request,"description");
		String priceId =getRequestValue(request,"priceId");
		String priPricingInfoId =getRequestValue(request,"priPricingInfoId");
		String formatEffDate =getRequestValue(request,"formatEffDate");
		String formatExpDate =getRequestValue(request,"formatExpDate");
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setPriPricingInfoId(Integer.parseInt(priPricingInfoId));
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setPriceName(priceName);
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
	
		componentPrice.setPriceType("0");
		componentPrice.setStatusCd("10");
		
		String taxIncluded =getRequestValue(request,"taxIncluded");
		String baseItemType =getRequestValue(request,"baseItemType");
		String ratingUnitType =getRequestValue(request,"ratingUnitType");
		String ratingUnitVal =getRequestValue(request,"ratingUnitVal");
		String currencyUnitType =getRequestValue(request,"currencyUnitType");
		String rateType =getRequestValue(request,"rateType");
		BasicTariff basicTariff=new BasicTariff();
		basicTariff.setTaxIncluded(taxIncluded);
		basicTariff.setBaseItemType(Integer.parseInt(baseItemType));
		basicTariff.setRatingUnitType(Integer.parseInt(ratingUnitType));
		basicTariff.setRatingUnitVal(Integer.parseInt(ratingUnitVal));
		basicTariff.setCurrencyUnitType(Integer.parseInt(currencyUnitType));
		basicTariff.setRateType(Integer.parseInt(rateType));
		
		basicTariff.setPriceId(componentPrice.getPriceId());
		basicTariff.setPriceName(componentPrice.getPriceName());
		String baseQosType=getRequestValue(request,"baseQosTypeVal");
		if(!StringUtils.isEmpty(baseQosType)){
			basicTariff.setBaseQosType(baseQosType);
		}
		
		
		StringBuffer sb = null;
		String[] arr = null;
		if(null!=other&&4 == other.split(",").length){
			arr = other.split(",");
			sb = new StringBuffer();
			sb.append(getI18nMessage("eaap.op2.portal.price.priceObject",i18nLoader)).append("[").append(arr[0])
			  .append("],").append(getI18nMessage("eaap.op2.portal.price.priceUnit1",i18nLoader)).append("[").append(basicTariff.getRatingUnitVal()).append(arr[1])
			  .append("],").append(getI18nMessage("eaap.op2.portal.price.priceUnit2",i18nLoader)).append("[").append(arr[2])
			  .append("],").append(getI18nMessage("eaap.op2.portal.price.feeType",i18nLoader)).append("[").append("1".equals(arr[3])?getI18nMessage("eaap.op2.portal.price.simple",i18nLoader):getI18nMessage("eaap.op2.portal.price.ladder",i18nLoader))
			  .append("],").append(getI18nMessage("eaap.op2.portal.price.priceUnit2",i18nLoader)).append("[").append(arr[2]).append("]");
			componentPrice.setPriceDesc(sb.toString());
		}
		BaseTariffItemRel baseTariffItemRel = new BaseTariffItemRel();
		RatingCurverDetail ratingCurverDetail=new RatingCurverDetail();
		String itemIds =getRequestValue(request,"itemIds");
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			//ADD:
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			basicTariff.setPriceId(priceId1);
			this.addBasicTariff(basicTariff);
			if(StringUtils.isNotEmpty(itemIds)){
				baseTariffItemRel.setPriceId(priceId1);
				baseTariffItemRel.setItemType(basicTariff.getBaseItemType());
				for(String id : itemIds.split(",")){
					baseTariffItemRel.setItemId(Long.parseLong(id));
					this.addBaseTariffItemRel(baseTariffItemRel);
				}
			}
			
			if(null!=details && !"".equals(details)){
				if(details.indexOf(';')>=0){
					for(String str : details.split(";")){
						String[] a = str.split(",");
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(Integer.parseInt(a[0]));
						ratingCurverDetail.setStartval(Integer.parseInt(a[1]));
						ratingCurverDetail.setEndVal(Integer.parseInt(a[2]));
						ratingCurverDetail.setRateVal(a[3]);
						ratingCurverDetail.setBaseVal(a[4]);
						this.addRatingCurveDetail(ratingCurverDetail);
					}
				}else{
					ratingCurverDetail.setPriceId(componentPrice.getPriceId());
					ratingCurverDetail.setSegMentId(1);
					ratingCurverDetail.setStartval(0);
					ratingCurverDetail.setEndVal(-1);
					ratingCurverDetail.setRateVal(details.split(",")[0]);
					ratingCurverDetail.setBaseVal(details.split(",")[1]);
					this.addRatingCurveDetail(ratingCurverDetail);
				}
				
			}
		}else{
			//Update:
			this.updateComponentPrice(componentPrice);
			this.updateBasicTariff(basicTariff);
			
			baseTariffItemRel.setPriceId(componentPrice.getPriceId());
			this.delBaseTariffItemRel(baseTariffItemRel);
			if(StringUtils.isNotEmpty(itemIds)){
				baseTariffItemRel.setPriceId(componentPrice.getPriceId());
				baseTariffItemRel.setItemType(basicTariff.getBaseItemType());
				for(String id : itemIds.split(",")){
					baseTariffItemRel.setItemId(Long.parseLong(id));
					this.addBaseTariffItemRel(baseTariffItemRel);
				}
			}
			
			ratingCurverDetail.setPriceId(componentPrice.getPriceId());
			List<RatingCurverDetail> ratingCurverDetailList = this.queryRatingCurveDetail(ratingCurverDetail);
			for(RatingCurverDetail r : ratingCurverDetailList){
				this.delRatingCurveDetail(r);
			}
			if(null!=details && !"".equals(details)){
				if(details.indexOf(';')>=0){
					for(String str : details.split(";")){
						String[] a = str.split(",");
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(Integer.parseInt(a[0]));
						ratingCurverDetail.setStartval(Integer.parseInt(a[1]));
						ratingCurverDetail.setEndVal(Integer.parseInt(a[2]));
						ratingCurverDetail.setRateVal(a[3]);
						ratingCurverDetail.setBaseVal(a[4]);
						this.addRatingCurveDetail(ratingCurverDetail);
					}
				}else{
					ratingCurverDetail.setPriceId(componentPrice.getPriceId());
					ratingCurverDetail.setSegMentId(1);
					ratingCurverDetail.setStartval(0);
					ratingCurverDetail.setEndVal(-1);
					ratingCurverDetail.setRateVal(details.split(",")[0]);
					ratingCurverDetail.setBaseVal(details.split(",")[1]);
					this.addRatingCurveDetail(ratingCurverDetail);
				}
			}
		}
		return priceId;
	}
	
	public String saveOrUpdateRecurringFee(final HttpServletRequest request,
			final HttpServletResponse response,String key,PropertiesLoader i18nLoader )throws Exception {
		String priceId =getRequestValue(request,"priceId");
		String priPricingInfoId =getRequestValue(request,"priPricingInfoId");
		String priceName =getRequestValue(request,"priceName");
		String formatEffDate =getRequestValue(request,"formatEffDate");
		String formatExpDate =getRequestValue(request,"formatExpDate");
		String description =getRequestValue(request,"description");
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setPriPricingInfoId(Integer.parseInt(priPricingInfoId));
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setPriceType("7");
		componentPrice.setStatusCd("10");

		String taxIncluded 			=getRequestValue(request,"taxIncluded");
		String itemName 			=getRequestValue(request,"itemName");
		String itemId 					=getRequestValue(request,"itemId");
		String billingPriority 		=getRequestValue(request,"billingPriority");
		String currencyUnitVal 	=getRequestValue(request,"currencyUnitVal");
		String currencyUnitType=getRequestValue(request,"currencyUnitType");
	
		String prodOfferId = getRequestValue(request, "offerId");
		//查询销售品信息
		ProdOffer prodOfferQuery=new ProdOffer();
		prodOfferQuery.setProdOfferId(new BigDecimal(prodOfferId));
		ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
		String prodOfferType=prodOffer.getOfferType();
		
		//换算优先级
		int priority=calculatePriority(Integer.parseInt(billingPriority),prodOfferType);
		
		PecurringFee pecurringFee=new PecurringFee();
		pecurringFee.setPriceId(componentPrice.getPriceId());
		pecurringFee.setPriceName(componentPrice.getPriceName());
		pecurringFee.setCurrencyUnitVal(currencyUnitVal);
		pecurringFee.setCurrencyUnitType("".equals(currencyUnitType)?null:Integer.parseInt(currencyUnitType));
		pecurringFee.setTaxIncluded(taxIncluded);
		pecurringFee.setItemId("".equals(itemId)?null:Long.parseLong(itemId));
		pecurringFee.setItemName(itemName);
		pecurringFee.setBillingPriority(priority);
		
		String other = getRequestValue(request,"other");
		if(null!=other&&!"".equals(other)){
			StringBuffer sb = new StringBuffer();
			sb.append(getI18nMessage("eaap.op2.portal.price.priceUnit2",i18nLoader)).append("[").append(pecurringFee.getCurrencyUnitVal()).append(other).append("]");
			componentPrice.setPriceDesc(sb.toString());
		}
		
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			pecurringFee.setPriceId(priceId1);
			this.addRecurringFee(pecurringFee);
		}else{
			this.updateComponentPrice(componentPrice);
			this.updateRecurringFee(pecurringFee);
		}
		return priceId;
	}
	
   private  int calculatePriority(int billingPriority, String prodOfferType) {
		int result = 0;
		if("11".equals(prodOfferType)){//Main Offer
			result=600 - 10*billingPriority;
		}else if("12".equals(prodOfferType)){//Add on offer
			result=1000 - 10*billingPriority;
		}
		return result;
		
	}
   
	public String saveOrUpdateRatingDiscount(final HttpServletRequest request,
			final HttpServletResponse response,String key)throws Exception {
		String priceId =getRequestValue(request,"priceId");
		String priceName =getRequestValue(request,"priceName");
		String formatEffDate =getRequestValue(request,"formatEffDate");
		String formatExpDate =getRequestValue(request,"formatExpDate");
		String description =getRequestValue(request,"description");
		String priPricingInfoId = getRequestValue(request,"priPricingInfoId");
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
	
		componentPrice.setPriceType("5");
		componentPrice.setStatusCd("10");
		componentPrice.setPriPricingInfoId(Integer.valueOf(priPricingInfoId));
		
		RatingDiscount ratingDiscount=new RatingDiscount();
		ratingDiscount.setSegName(getRequestValue(request,"segName"));
		ratingDiscount.setSegId(new BigDecimal(getRequestValue(request,"segId")));
		String baseItemType = getRequestValue(request,"baseItemType");
		ratingDiscount.setBaseItemType("".equals(baseItemType)?0: Integer.parseInt(baseItemType));
		String discountType = getRequestValue(request,"discountType");
		ratingDiscount.setDiscountType("".equals(discountType)?0: Integer.parseInt(discountType));
		String maxiMum = getRequestValue(request,"maxiMum");
		ratingDiscount.setMaxiMum("".equals(maxiMum)?"0": maxiMum); 
		String numberator = getRequestValue(request,"numberator");
		ratingDiscount.setNumberator("".equals(numberator)?0: Integer.parseInt(numberator)); 
		String demominator = getRequestValue(request,"demominator");
		ratingDiscount.setDemominator("".equals(demominator)?0: Integer.parseInt(demominator)); 
		ratingDiscount.setPriceId(componentPrice.getPriceId());
		ratingDiscount.setPriceName(componentPrice.getPriceName());
		String currencyUnitType = getRequestValue(request,"currencyUnitType");
		ratingDiscount.setCurrencyUnitType("".equals(demominator)?0: Integer.parseInt(currencyUnitType));
		BaseTariffItemRel baseTariffItemRel=new BaseTariffItemRel();
		String itemIds =getRequestValue(request,"itemIds");
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			ratingDiscount.setPriceId(priceId1);
			this.addRatingDiscount(ratingDiscount);
			if(StringUtils.isNotEmpty(itemIds)){
				baseTariffItemRel.setPriceId(priceId1);
				baseTariffItemRel.setItemType(ratingDiscount.getBaseItemType());
				for(String id : itemIds.split(",")){
					baseTariffItemRel.setItemId(Long.parseLong(id));
					this.addBaseTariffItemRel(baseTariffItemRel);
				}
			}
			
		}else{
			this.updateComponentPrice(componentPrice);
			this.updateRatingDiscount(ratingDiscount);
			
			baseTariffItemRel.setPriceId(componentPrice.getPriceId());
			this.delBaseTariffItemRel(baseTariffItemRel);
			if(StringUtils.isNotEmpty(itemIds)){
				baseTariffItemRel.setPriceId(componentPrice.getPriceId());
				baseTariffItemRel.setItemType(ratingDiscount.getBaseItemType());
				for(String id : itemIds.split(",")){
					baseTariffItemRel.setItemId(Long.parseLong(id));
					this.addBaseTariffItemRel(baseTariffItemRel);
				}
			}
		}
		return priceId;
	}
	public String saveOrUpdateOneTimeFee(final HttpServletRequest request,
			final HttpServletResponse response,String key,PropertiesLoader i18nLoader )throws Exception {
		String priceId =getRequestValue(request,"priceId");
		String priceName =getRequestValue(request,"priceName");
		String formatEffDate =getRequestValue(request,"formatEffDate");
		String formatExpDate =getRequestValue(request,"formatExpDate");
		String description =getRequestValue(request,"description");
		String priPricingInfoId = getRequestValue(request,"priPricingInfoId");
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setPriPricingInfoId(Integer.valueOf(priPricingInfoId));
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
	
		String taxIncluded =getRequestValue(request,"taxIncluded");
		String businessItemName =getRequestValue(request,"businessItemName");
		String businessItem =getRequestValue(request,"businessItem");
		String oneTimeItemName =getRequestValue(request,"oneTimeItemName");
		String oneTimeItem =getRequestValue(request,"oneTimeItem");
		String chargeType =getRequestValue(request,"chargeType");
		String currencyUnitVal =getRequestValue(request,"currencyUnitVal");
		String currencyUnitType =getRequestValue(request,"currencyUnitType");
		String billingPriority =getRequestValue(request,"billingPriority");
		
		String prodOfferId = getRequestValue(request, "offerId");
		//查询销售品信息
		ProdOffer prodOfferQuery=new ProdOffer();
		prodOfferQuery.setProdOfferId(new BigDecimal(prodOfferId));
		ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
		String prodOfferType=prodOffer.getOfferType();
		
		//换算优先级
		int priority=calculatePriority(Integer.parseInt(billingPriority),prodOfferType);
		
		
		OneTimeCharge oneTimeCharge=new OneTimeCharge();
		oneTimeCharge.setTaxIncluded(taxIncluded);
		oneTimeCharge.setBusinessItemName(businessItemName);
		oneTimeCharge.setBusinessItem(Long.parseLong(businessItem));
		oneTimeCharge.setOneTimeItem(Long.parseLong(oneTimeItem));
		oneTimeCharge.setOneTimeItemName(oneTimeItemName);
		oneTimeCharge.setChargeType(chargeType);
		oneTimeCharge.setCurrencyUnitType(Integer.parseInt(currencyUnitType));
		oneTimeCharge.setCurrencyUnitVal(currencyUnitVal);
		oneTimeCharge.setBillingPriority(priority);
		oneTimeCharge.setPriceId(componentPrice.getPriceId());
		oneTimeCharge.setPriceName(componentPrice.getPriceName());
		String other = getRequestValue(request,"other");
		if(null!=other&&!"".equals(other)){
			StringBuffer sb = new StringBuffer();
			sb.append(getI18nMessage("eaap.op2.portal.price.priceUnit2",i18nLoader)).append("[").append(oneTimeCharge.getCurrencyUnitVal()).append(other).append("]");
			componentPrice.setPriceDesc(sb.toString());
		}
		componentPrice.setPriceType("9");
		componentPrice.setStatusCd("10");
		
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			oneTimeCharge.setPriceId(priceId1);
			this.addOneTimeCharge(oneTimeCharge);
		}else{
			this.updateComponentPrice(componentPrice);
			this.updateOneTimeCharge(oneTimeCharge);
		}
		return priceId;
	}
	
	public String saveOrUpdateBillingDiscount(final HttpServletRequest request,final HttpServletResponse response,String key)throws Exception {
		String priceId =getRequestValue(request,"priceId");
		String priceName =getRequestValue(request,"priceName");
		String formatEffDate =getRequestValue(request,"formatEffDate");
		String formatExpDate =getRequestValue(request,"formatExpDate");
		String description =getRequestValue(request,"description");
		String priPricingInfoId = getRequestValue(request,"priPricingInfoId");
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setPriPricingInfoId(Integer.valueOf(priPricingInfoId));
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
	
		componentPrice.setPriceType("8");
		componentPrice.setStatusCd("10");
		
		String eligibleItemName =getRequestValue(request,"eligibleItemName");
		String eligibleItem =getRequestValue(request,"eligibleItem");
		String targetItemName =getRequestValue(request,"targetItemName");
		String targetItem =getRequestValue(request,"targetItem");
		String taxIncluded =getRequestValue(request,"taxIncluded");
		String billingPriority =getRequestValue(request,"billingPriority");
		
		String prodOfferId = getRequestValue(request, "offerId");
		//查询销售品信息
		ProdOffer prodOfferQuery=new ProdOffer();
		prodOfferQuery.setProdOfferId(new BigDecimal(prodOfferId));
		ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
		String prodOfferType=prodOffer.getOfferType();
		
		//换算优先级
		int priority=calculatePriority(Integer.parseInt(billingPriority),prodOfferType);
		
		String promType =getRequestValue(request,"promType");
		String maxiMum =getRequestValue(request,"maxiMum");
		String currencyUnitType =getRequestValue(request,"currencyUnitType");
		String calcType =getRequestValue(request,"calcType");
		BillingDiscount billingDiscount=new BillingDiscount();
		billingDiscount.setPriceId(componentPrice.getPriceId());
		billingDiscount.setPriceName(componentPrice.getPriceName());
		billingDiscount.setEligibleItem(Long.parseLong(eligibleItem));
		billingDiscount.setEligibleItemName(eligibleItemName);
		billingDiscount.setTargetItem(Long.parseLong(targetItem));
		billingDiscount.setTargetItemName(targetItemName);
		billingDiscount.setTaxIncluded(taxIncluded);
		billingDiscount.setBillingPriority(priority);
		billingDiscount.setPromType(promType);
		billingDiscount.setMaxiMum(maxiMum);
		billingDiscount.setCurrencyUnitType(Integer.parseInt(currencyUnitType));
		billingDiscount.setCalcType(calcType);
		RatingCurverDetail ratingCurverDetail=new RatingCurverDetail();
		String details = getRequestValue(request,"details");
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			billingDiscount.setPriceId(priceId1);
			this.addBillingDiscount(billingDiscount);
			
			if(null!=details && !"".equals(details)){
				
				if("1".equals(billingDiscount.getPromType())){
					for(String str : details.split(";")){
						String[] a = str.split(",");
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(Integer.parseInt(a[0]));
						ratingCurverDetail.setStartval(Integer.parseInt(a[1]));
						ratingCurverDetail.setEndVal(Integer.parseInt(a[2]));
						ratingCurverDetail.setNumerator(Integer.parseInt(a[3]));
						ratingCurverDetail.setDenominator(Integer.parseInt(a[4]));
						this.addRatingCurveDetail(ratingCurverDetail);
					}
				}else{
					for(String str : details.split(";")){
						String[] a = str.split(",");
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(Integer.parseInt(a[0]));
						ratingCurverDetail.setStartval(Integer.parseInt(a[1]));
						ratingCurverDetail.setEndVal(Integer.parseInt(a[2]));
						ratingCurverDetail.setBaseVal(a[3]);
						this.addRatingCurveDetail(ratingCurverDetail);
					}
				}
			}
		}else{
			this.updateComponentPrice(componentPrice);
			this.updateBillingDiscount(billingDiscount);
			
			ratingCurverDetail.setPriceId(componentPrice.getPriceId());
			List<RatingCurverDetail> ratingCurverDetailList = this.queryRatingCurveDetail(ratingCurverDetail);
			for(RatingCurverDetail r : ratingCurverDetailList){
				this.delRatingCurveDetail(r);
			}
			if(null!=details && !"".equals(details)){
				for(String str : details.split(";")){
					String[] a = str.split(",");
					ratingCurverDetail.setPriceId(componentPrice.getPriceId());
					ratingCurverDetail.setSegMentId(Integer.parseInt(a[0]));
					ratingCurverDetail.setStartval(Integer.parseInt(a[1]));
					ratingCurverDetail.setEndVal(Integer.parseInt(a[2]));
					if("1".equals(billingDiscount.getPromType())){
						ratingCurverDetail.setNumerator(Integer.parseInt(a[3]));
						ratingCurverDetail.setDenominator(Integer.parseInt(a[4]));
					}else{
						ratingCurverDetail.setBaseVal(a[3]);
					}
					this.addRatingCurveDetail(ratingCurverDetail);
				}
			}
		}
		return priceId;
	}
	
	
	public String saveOrUpdateFreeResource(final HttpServletRequest request,final HttpServletResponse response,String key)throws Exception {
		String details =getRequestValue(request,"details");
		String priceName =getRequestValue(request,"priceName");	//
		String description =getRequestValue(request,"description");//
		String priceId =getRequestValue(request,"priceId");//
		String priPricingInfoId =getRequestValue(request,"priPricingInfoId");//
		String formatEffDate =getRequestValue(request,"formatEffDate");//
		String formatExpDate =getRequestValue(request,"formatExpDate");//
		ComponentPrice componentPrice=new ComponentPrice();
		componentPrice.setPriceId("".equals(priceId)?null:Integer.parseInt(priceId));
		componentPrice.setPriceName(priceName);
		componentPrice.setPriPricingInfoId(Integer.parseInt(priPricingInfoId));
		componentPrice.setDescription(description);
		componentPrice.setFormatEffDate(formatEffDate);
		componentPrice.setFormatExpDate(formatExpDate);
		componentPrice.setPriceName(priceName);
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setPriceType("3");
		componentPrice.setStatusCd("10");
		
		String freeresItem =getRequestValue(request,"freeresItem");
		String cycleRefMode =getRequestValue(request,"cycleRefMode");
		String cycleUnit =getRequestValue(request,"cycleUnit");
		String cycleType =getRequestValue(request,"cycleType");
		String invalidProdUseFlag =getRequestValue(request,"invalidProdUseFlag");
		String userStateIds =getRequestValue(request,"userStateIds");		
		String forwardCycle =getRequestValue(request,"forwardCycle");
		String allowanceType =getRequestValue(request,"allowanceType");
		String amountType =getRequestValue(request,"amountType");

		FreeResource freeResource=new FreeResource();
		freeResource.setFreeresItem(Long.parseLong(freeresItem));
		freeResource.setCycleRefMode(cycleRefMode);
		freeResource.setCycleUnit("".equals(cycleUnit)?null:Integer.parseInt(cycleUnit));
		freeResource.setCycleType("".equals(cycleType)?null:Integer.parseInt(cycleType));
		freeResource.setInvalidProdUseFlag(invalidProdUseFlag);
		freeResource.setUserState(userStateIds);
		freeResource.setAllowanceType(allowanceType);
		freeResource.setAmountType(amountType);
		freeResource.setForwardCycle("".equals(forwardCycle)?null:Integer.parseInt(forwardCycle));
		
		FreeResourceSeg freeResourceSeg=new FreeResourceSeg();
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			//ADD:
			Integer priceId1 = this.addComponentPrice(componentPrice);
			priceId = priceId1.toString();
			freeResource.setPriceId(Long.parseLong(priceId1.toString()));
			this.addFreeResource(freeResource);
			
			if(null!=details && !"".equals(details)){
				if(details.indexOf(';')>=0){
					for(String str : details.split(";")){
						String[] a = str.split(",");
						if(a.length>0){
							freeResourceSeg.setPriceId(Long.parseLong(priceId));
							freeResourceSeg.setStartValue(Integer.parseInt(a[0]));
							freeResourceSeg.setEndValue(Integer.parseInt(a[1]));
							freeResourceSeg.setAmount(Long.parseLong(a[2]));
							freeResourceSeg.setMeasureId(Integer.parseInt(a[3]));
							this.addFreeResourceSeg(freeResourceSeg);
						}
					}
				}else{
					String[] a = details.split(",");
					if(a.length>0){
						freeResourceSeg.setPriceId(Long.parseLong(priceId));
						freeResourceSeg.setStartValue(Integer.parseInt(a[0]));
						freeResourceSeg.setEndValue(Integer.parseInt(a[1]));
						freeResourceSeg.setAmount(Long.parseLong(a[2]));
						freeResourceSeg.setMeasureId(Integer.parseInt(a[3]));
						this.addFreeResourceSeg(freeResourceSeg);
					}
				}
			}
			
		}else{
			//Update:
			this.updateComponentPrice(componentPrice);
			freeResource.setPriceId(Long.parseLong(componentPrice.getPriceId().toString()));
			this.updateFreeResource(freeResource);

			freeResourceSeg.setPriceId(Long.parseLong(componentPrice.getPriceId().toString()));
			List<FreeResourceSeg> freeResourceSegList = this.queryFreeResourceSeg(freeResourceSeg);
			for(FreeResourceSeg r : freeResourceSegList){
				this.delFreeResourceSeg(r);
			}
			if(null!=details && !"".equals(details)){
				if(details.indexOf(';')>=0){
					for(String str : details.split(";")){
						String[] a = str.split(",");
						if(a.length>0){
							freeResourceSeg.setPriceId(Long.parseLong(priceId));
							freeResourceSeg.setStartValue(Integer.parseInt(a[0]));
							freeResourceSeg.setEndValue(Integer.parseInt(a[1]));
							freeResourceSeg.setAmount(Long.parseLong(a[2]));
							freeResourceSeg.setMeasureId(Integer.parseInt(a[3]));
							this.addFreeResourceSeg(freeResourceSeg);
						}
					}
				}else{
					String[] a = details.split(",");
					if(a.length>0){
						freeResourceSeg.setPriceId(Long.parseLong(priceId));
						freeResourceSeg.setStartValue(Integer.parseInt(a[0]));
						freeResourceSeg.setEndValue(Integer.parseInt(a[1]));
						freeResourceSeg.setAmount(Long.parseLong(a[2]));
						freeResourceSeg.setMeasureId(Integer.parseInt(a[3]));
						this.addFreeResourceSeg(freeResourceSeg);
					}
				}
			}
		}
		return priceId;
	}
	
	
	protected String getRequestValue(final HttpServletRequest request,
			String paramName, boolean escape) {
		String paramValue = request.getParameter(paramName);
		if (null != paramValue) {
			paramValue = StringUtils.trim(paramValue);
			if (escape) {
				paramValue = StringEscapeUtils.escapeHtml(paramValue);
				paramValue = StringEscapeUtils.escapeSql(paramValue);
			}
			return paramValue;
		} else {
			return StringUtils.EMPTY;
		}
	}
	protected String getRequestValue(final HttpServletRequest request,
			String paramName) {
		return getRequestValue(request, paramName, true);
	}
	protected String getI18nMessage(String key,PropertiesLoader i18nLoader) {
		return i18nLoader.getProperty(key);
	}

	public Integer queryFreeResourceItemListCount(Map map){
		return pricePlanDetailDao.queryFreeResourceItemListCount(map);
	}
	public List queryFreeResourceItemList(Map map){
		return pricePlanDetailDao.queryFreeResourceItemList(map);
	}
	

	public List<FreeResourceSeg> queryFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return pricePlanDetailDao.queryFreeResourceSeg(freeResourceSeg);
	}
	public Integer addFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return pricePlanDetailDao.addFreeResourceSeg(freeResourceSeg);
	}
	public Integer updateFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return pricePlanDetailDao.updateFreeResourceSeg(freeResourceSeg);
	}
	public void delFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		pricePlanDetailDao.delFreeResourceSeg(freeResourceSeg);
	}
	
}
