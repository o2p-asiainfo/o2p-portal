package com.ailk.o2p.portal.controller.pard;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.BaseTariffItemRel;
import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.FreeResourceSeg;
import com.ailk.eaap.op2.bo.FreeResource;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.CharSpec;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.OneTimeCharge;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.PriceItem;
import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.RatingDiscount;
import com.ailk.eaap.op2.bo.RsSysCellDef;
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.price.IAttrSpecServ;
import com.ailk.o2p.portal.service.price.PricePlanDetailServiceImpl;
import com.ailk.o2p.portal.service.price.PriceService;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;
import com.ailk.o2p.portal.service.pardSpec.IPardSpecServ;
import com.ailk.o2p.portal.service.pardoffer.IPardOfferServ;
import com.ailk.o2p.portal.utils.SavePermission;
import com.ailk.o2p.portal.utils.TenantInterceptor;

@Controller
@Transactional
@RequestMapping(value = "/pardOfferPricePlan")
public class PardOfferPricePlanController extends BaseController {

	private static Logger log = Logger.getLog(PardOfferPricePlanController.class);

	@Autowired
	private PriceService priceServ;
	@Autowired
	private IAttrSpecServ attrSpecService;
	@Autowired
	private PricePlanDetailServiceImpl pricePlanDetailServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	public List<Map> pricePlanSpecList = new ArrayList<Map>();
	@Autowired
	private IPardSpecServ pardSpecServ;
	@Autowired
	private IPardOfferServ pardOfferServ;
		
	@RequestMapping(value = "/toPricePlan")
	public ModelAndView toPardOffertPricePlan(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");//home
			messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
			messages.add("eaap.op2.portal.pardOffer.offer");//offer
			messages.add("eaap.op2.portal.pardProd.prodDetail.location");//detail
			//二级导航
			messages.add("eaap.op2.portal.pardOffer.localtion1");//Basic Info
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eeaap.op2.portal.pardOffer.account");//Settlement
			//基础信息
			messages.add("eaap.op2.portal.price.priceName");//Price Name
			messages.add("eaap.op2.portal.price.delayUnit.detail");//Offset label
			messages.add("eaap.op2.portal.price.delayUnit");//Offset
			messages.add("eaap.op2.portal.price.validUnit.detail");//Validity label
			messages.add("eaap.op2.portal.price.validUnit");//Validity
			messages.add("eaap.op2.portal.pardProd.product.priority");//priority label
			messages.add("eaap.op2.portal.price.priority");//priority 
			messages.add("eaap.op2.portal.price.pricePlan");
			messages.add("eaap.op2.portal.price.pricePlanSpec");//Price Plan Attribute
			messages.add("eaap.op2.portal.price.priceDesc");//Description
			//Price Object Product
			messages.add("eaap.op2.portal.price.priceObject-prod");//Price Object Product
			// Charge Information
			messages.add("eaap.op2.portal.price.feeInfo");// Charge Information
			messages.add("eaap.op2.portal.price.noChargeInformation");
			//审核状态
			//按钮
			messages.add("eaap.op2.portal.doc.submit");//SELECT PRODUCT
			
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.price.timeSpec.longitude");
			messages.add("eaap.op2.portal.price.timeSpec.latitude");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.price.timeSpec.radius");
			messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.attrSpec.selMap");
			messages.add("eaap.op2.portal.doc.cancel");

			messages.add("eaap.op2.portal.price.baseInfo");
			messages.add("eaap.op2.portal.price.feeInfo");
			
			messages.add("eaap.op2.portal.price.message.saveSuccess");
			messages.add("eaap.op2.portal.price.message.saveFail");
			messages.add("eaap.op2.portal.pardOffer.noData");
			messages.add("eaap.op2.portal.isRequired");

			messages.add("eaap.op2.portal.price.priceObject");
			messages.add("eaap.op2.portal.price.priceUnit1");
			messages.add("eaap.op2.portal.price.fee");
			messages.add("eaap.op2.portal.price.priceSubject");
			messages.add("eaap.op2.portal.price.ratingDiscount.timeRange");
			messages.add("eaap.op2.portal.price.serviceType");

			messages.add("eaap.op2.portal.price.reference");
			messages.add("eaap.op2.portal.price.contrast");
			messages.add("eaap.op2.portal.price.maxDiscount");
			
			messages.add("eaap.op2.portal.price.freeResourceItem");
			messages.add("eaap.op2.portal.price.subscriberStatusEligibility");
			messages.add("eaap.op2.portal.price.forwardCycle");
			
			addTranslateMessage(mv, messages);
			mv.addObject("activationOffsetTypeList",mainDataServ.getMainInfo(EAAPConstants.ACTIVATION_OFFSET_TYPE));
			mv.addObject("activationOffsetList",mainDataServ.getMainInfo(EAAPConstants.ACTIVATION_OFFSET));
			mv.addObject("pricePlanValidityPeriodList",mainDataServ.getMainInfo(EAAPConstants.PRICE_PLAN_VALIDITY_PERIOD));
			mv.addObject("priceTypeList",mainDataServ.getMainInfo(EAAPConstants.COMPONENTPRICE_PRICETYPE));
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));
			mv.addObject("serviceLevelAgreementList",mainDataServ.getMainInfo(EAAPConstants.SLA_RANGE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));
					
			String actionType = getRequestValue(request, "actionType");
			
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
//					prodOfferPricing.setPricingInfoId(pricingPlan.getPricingInfoId());
//					getTreeParams += ",pricingTargetId:"+pricingTarget.getPricingTargetId();
//					getTreeParams += ",idvalue:" + this.getSelectedTreeIdByTarget(pricingTarget.getPricingTargetId());//全部的
				String prodIds = getRequestValue(request, "prodIds");
				String pricingInfoId = getRequestValue(request, "pricingInfoId");
				String pricingTargetId = getRequestValue(request, "pricingTargetId");
				String applicType = getRequestValue(request, "applicType");


				mv.addObject("prodOfferId",prodIds);
				mv.addObject("pricingInfoId",pricingInfoId);
				mv.addObject("pricingTargetId",pricingTargetId);
		
//				if(!"".equals(prodIds)&&null!=prodIds){
//					pricingTarget.setProdOfferId(Integer.parseInt(prodIds));
//					getTreeParams = "prodIds:"+prodIds;
//				}
				
				PricingPlan pricingPlan=new PricingPlan();
				pricingPlan.setPricingInfoId(Integer.parseInt(pricingInfoId));
				pricingPlan.setApplicType(applicType);
				pricingPlan = priceServ.queryPricingPlan(pricingPlan).get(0);
				 
				pricingPlan.setFormatEffDate("".equals(StringUtil.valueOf(pricingPlan.getEffDate()))?null:DateUtil.convDateToString(pricingPlan.getEffDate(), "yyyy-MM-dd")) ;
				pricingPlan.setFormatExpDate("".equals(StringUtil.valueOf(pricingPlan.getEffDate()))?null:DateUtil.convDateToString(pricingPlan.getExpDate(), "yyyy-MM-dd")) ;
				
				PricngPlanAttr pricngPlanAttr = new PricngPlanAttr();
				pricngPlanAttr.setPricingInfoId(new BigDecimal(pricingPlan.getPricingInfoId()));
				List<PricngPlanAttr> list = attrSpecService.queryPricngPlanAttr(pricngPlanAttr);
				if(list.size()>0){
					List pricePlanAttrList=new ArrayList();
					for(PricngPlanAttr p :list){
						PricngPlanAttr newP = null;
						newP = new PricngPlanAttr();
						newP.setAttrSpecId(p.getAttrSpecId());
						newP.setDefaultValue(p.getDefaultValue()==null?"":p.getDefaultValue());
						newP.setDefaultValueName(null==p.getDefaultValueName()?"":p.getDefaultValueName());
						newP.setPageInType(p.getPageInType());
						newP.setAttrSpecName(p.getAttrSpecName());
						newP.setChooseURL(p.getChooseURL()==null?"":p.getChooseURL());
						newP.setAttrSpecDesc(p.getAttrSpecDesc()==null?"":p.getAttrSpecDesc());
						pricePlanAttrList.add(newP);
					}
					mv.addObject("pricePlanAttrList",pricePlanAttrList);
				}
				PricePlanLifeCycle pricePlanLifeCycle = new PricePlanLifeCycle();
				pricePlanLifeCycle.setPricePlanId(Long.valueOf(pricingPlan.getPricingInfoId()));
				pricePlanLifeCycle = attrSpecService.queryPricePlanLifeCycle(pricePlanLifeCycle);
				if(null == pricePlanLifeCycle){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"exception: The PricePlanLifeCycle is null.",null);
				}
				pricePlanLifeCycle.setfFixedExpireDate("".equals(StringUtil.valueOf(pricePlanLifeCycle.getFixedExpireDate()))?null:DateUtil.convDateToString(pricePlanLifeCycle.getFixedExpireDate(), "yyyy-MM-dd"));
				
				mv.addObject("pricingPlan",pricingPlan);
				//System.out.println("========================"+pricingPlan.getBillingPriority());
				
				//查询销售品信息
				ProdOffer prodOfferQuery=new ProdOffer();
				prodOfferQuery.setProdOfferId(new BigDecimal(prodIds));
				ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
				String prodOfferType=prodOffer.getOfferType();
                int billingPriorityValue=calculateTransferFromDb(pricingPlan.getBillingPriority(),prodOfferType);
                mv.addObject("billingPriorityValue",billingPriorityValue);
				mv.addObject("pricePlanLifeCycle",pricePlanLifeCycle);
				
				String relIds = "";

				OfferProdRelPricing offerProdRelPricing = new OfferProdRelPricing();
				offerProdRelPricing.setStatusCd("10");
				if(null != pricingTargetId && !"".equals(pricingTargetId)){
					offerProdRelPricing.setPricingTargetId(Integer.parseInt(pricingTargetId));
				}
				List<OfferProdRelPricing> offerProdRelPricingList = priceServ.queryOfferProdRelPricing(offerProdRelPricing);
				for(OfferProdRelPricing o : offerProdRelPricingList){
					if("".equals(relIds)){
						relIds = o.getOfferProdRelaId().toString();
					}else{
						relIds += ","+o.getOfferProdRelaId().toString();
					}
				}		
				mv.addObject("relIds", relIds);

				HashMap paramMap = new HashMap();
				int rows = 999;
				int pageNum = 1;
				int startRow = (pageNum - 1) * rows;
				paramMap.put("pro_mysql", startRow);
				paramMap.put("page_record", rows);
				paramMap.put("priPricingInfoId", pricingInfoId);
				List<ComponentPrice> componentPriceList = pricePlanDetailServ.queryComponentPrice(paramMap);
				mv.addObject("componentPriceList",componentPriceList);

				String formNum = getRequestValue(request, "formNum");
				mv.addObject("formNum",formNum);
				
				mv.setViewName("../pardOfferPricePlan/pricePlanAdd.jsp");
			}else{
				PricingPlan pricingPlan = new PricingPlan();
				mv.addObject("pricingPlan",pricingPlan);
				
				CharSpec charSpec = new CharSpec();
				charSpec.setCharSpecType(EAAPConstants.CHAR_SPEC_TYPE_PRICE);
				charSpec.setStatusCd(EAAPConstants.COMM_STATE_ONLINE);
				List<CharSpec> charSpeclist = pardSpecServ.qryCharSpec(charSpec);
				if(charSpeclist.size()>0){
					List<PricngPlanAttr> pricePlanAttrList=new ArrayList<PricngPlanAttr>();
					for(CharSpec p :charSpeclist){
						PricngPlanAttr newP = null;
						newP = new PricngPlanAttr();
						newP.setAttrSpecId(BigDecimal.valueOf(Long.valueOf(p.getCharSpecId())));
						newP.setDefaultValue(p.getDefaultValue());
						newP.setPageInType(p.getValueType());
						newP.setAttrSpecName(p.getCharSpecName());
						newP.setChooseURL(p.getUrl());
						newP.setAttrSpecDesc(p.getDescription());
						pricePlanAttrList.add(newP);
					}
					mv.addObject("pricePlanAttrList",pricePlanAttrList);
				}
				mv.setViewName("../pardOfferPricePlan/pricePlanAdd.jsp");

				String prodOfferId = getRequestValue(request, "prodIds");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("prodOfferId", prodOfferId);
				List<Map<String,Object>> mapList = priceServ.selectProductNameById(map);
				if(mapList.size()==1){
					Map<String,Object> maps = mapList.get(0);
					String relIds = maps.get("OFFERPRODRELAID").toString();
					mv.addObject("relIds", relIds);
				}
			}
		}catch (BusinessException e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getCause()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}

	@RequestMapping(value = "/savrOrUpdatePricePlan", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlan",parameterKey="pricingInfoId",privilege="")
	@ResponseBody
	public String savrOrUpdatePricePlan(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		PricingPlan pricingPlan = new PricingPlan();
		PricePlanLifeCycle pricePlanLifeCycle = new PricePlanLifeCycle();
		OfferProdRelPricing offerProdRelPricing = new OfferProdRelPricing();
		PricngPlanAttr pricngPlanAttr = new PricngPlanAttr();
		PricingTarget pricingTarget = new PricingTarget();
		
		HashMap<String,String> retMap = new HashMap<String,String>();
		String prodOfferId = getRequestValue(request, "prodOfferId");
		//查询销售品信息
		ProdOffer prodOfferQuery=new ProdOffer();
		prodOfferQuery.setProdOfferId(new BigDecimal(prodOfferId));
		ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
		String prodOfferType=prodOffer.getOfferType();
		String targetId = getRequestValue(request, "pricingTargetId");
		pricingTarget.setProdOfferId(!"".equals(prodOfferId)?BigDecimal.valueOf(Long.valueOf(prodOfferId)):null);
		pricingTarget.setPricingTargetId(!"".equals(targetId)?Integer.valueOf(targetId):null);
		
		String priceId = getRequestValue(request, "pricingInfoId");
		String applicType = getRequestValue(request, "applicType");
		String pricingName = getRequestValue(request, "pricingName");
		String licenseNbr = getRequestValue(request, "licenseNbr");		//?
		String billingPriorit = getRequestValue(request, "billingPriority");
		//换算优先级
		int priority=calculatePriority(Integer.parseInt(billingPriorit),prodOfferType);
		
		String desc = getRequestValue(request, "pricingDesc");					//?
		String feffd = getRequestValue(request, "formatEffDate");			//?
		String fexpd = getRequestValue(request, "formatExpDate");			//?
		pricingPlan.setPricingInfoId(!"".equals(priceId)?Integer.valueOf(priceId):null);
		pricingPlan.setApplicType(applicType);
		pricingPlan.setPricingName(pricingName);
		pricingPlan.setLicenseNbr(!"".equals(licenseNbr)?Integer.valueOf(licenseNbr):null);
		pricingPlan.setBillingPriority(priority);		// int <2147483647
		pricingPlan.setPricingDesc(desc);
		
		String pricePlanSpecIds		= getRequestValue(request, "pricePlanSpecIds");
		String defaultVals 				= getRequestValue(request, "defaultVals");
		String defaultValNames		= getRequestValue(request, "defaultValNames",true,false);
		
		String fFixedExpireDate = getRequestValue(request, "fixedExpireDate");
		String relIds = getRequestValue(request, "relIds");

		String subEffectMode = getRequestValue(request, "subEffectMode");
		String subDelayUnit = getRequestValue(request, "subDelayUnit");
		String subDelayCycle = getRequestValue(request, "subDelayCycle");
		String validUnit = getRequestValue(request, "validUnit");
		String vaildType = getRequestValue(request, "vaildType");
		pricePlanLifeCycle.setSubEffectMode(!"".equals(subEffectMode)?Integer.valueOf(subEffectMode):null);
		pricePlanLifeCycle.setSubDelayCycle(subDelayCycle);
		pricePlanLifeCycle.setSubDelayUnit(!"".equals(subDelayUnit)?Integer.valueOf(subDelayUnit):null);
		pricePlanLifeCycle.setValidUnit(!"".equals(validUnit)?Integer.valueOf(validUnit):null);
		pricePlanLifeCycle.setVaildType(vaildType);
		try {
			pricingPlan.setEffDate("".equals(StringUtil.valueOf(feffd))?null:DateUtil.stringToDate(feffd.replace("-","/"), "yyyy/MM/dd")) ;
			pricingPlan.setExpDate("".equals(StringUtil.valueOf(fexpd))?null:DateUtil.stringToDate(fexpd.replace("-","/"), "yyyy/MM/dd")) ;
			pricePlanLifeCycle.setFixedExpireDate("".equals(StringUtil.valueOf(fFixedExpireDate))?null:DateUtil.stringToDate(fFixedExpireDate.replace("-","/"), "yyyy/MM/dd"));

			String[] pricePlanSpecIdList = null;
			if(!"".equals(pricePlanSpecIds)){
				pricePlanSpecIdList = pricePlanSpecIds.split(",");
			}
			String[] defaultValsList = null;
			if(!"".equals(defaultVals)){
				defaultValsList = defaultVals.split(",");
			}
			String[] defaultValNamesList = null;
			if(!"".equals(defaultValNames)){
				defaultValNamesList = defaultValNames.split(",");
			}

			pricingPlan.setPricingName(pricingName);
			if(!"".equals(licenseNbr)&&licenseNbr!=null){
				pricingPlan.setLicenseNbr(Integer.parseInt(licenseNbr));
			}
			if(!"".equals(priority)){
				pricingPlan.setBillingPriority(priority);
			}
			if(!"".equals(applicType)&&applicType!=null){
				pricingPlan.setApplicType(applicType);
			}else{
				pricingPlan.setApplicType("1");
			}
			pricingPlan.setPricingDesc(desc);

			json=validateWithException(pricingPlan);
			String code = String.valueOf(json.get("code"));
			if(!BaseController.RESULT_OK.equals(code)){//说明验证通过
				return json.toString();
			}
			if(null!=priceId&&!"".equals(priceId) && null!=targetId&&!"".equals(targetId)){
				//update:

				
				pricingPlan.setPricingInfoId(Integer.parseInt(priceId));
				priceServ.updatePricingPlan(pricingPlan);
				pricingPlan.setPricingInfoId(Integer.parseInt(priceId));
				retMap.put("priceInfoId", priceId+"");
				
				offerProdRelPricing.setStatusCd("10");
				if(null != targetId && !"".equals(targetId)){
					offerProdRelPricing.setPricingTargetId(Integer.parseInt(targetId));
				}
				List<OfferProdRelPricing> offerProdRelPricingList = priceServ.queryOfferProdRelPricing(offerProdRelPricing);
				for(OfferProdRelPricing o : offerProdRelPricingList){
					o.setStatusCd("11");
					priceServ.updateProdRelPricing(o);
				}

				pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
				pricngPlanAttr.setStatusCd("10");
				List<PricngPlanAttr> list = attrSpecService.queryPricngPlanAttr(pricngPlanAttr);
				if(list.size()==0){
					if(null!=pricePlanSpecIdList&&pricePlanSpecIdList.length>0){
						for(int i=0;i<pricePlanSpecIdList.length;i++){
							String pricePlanSpecId =pricePlanSpecIdList[i];									/////////////?
							String defaultValName = "";
							if(null!=defaultValNamesList && defaultValNamesList.length>0){
								defaultValName = defaultValNamesList[i];
							}
							String defaultVal ="";
							if(null!=defaultValsList && defaultValsList.length>0){
								defaultVal = defaultValsList[i];
							}
							pricngPlanAttr = new PricngPlanAttr();
							pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
							pricngPlanAttr.setStatusCd("10");
							pricngPlanAttr.setAttrSpecId(BigDecimal.valueOf(Long.valueOf(String.valueOf(pricePlanSpecId))));
							pricngPlanAttr.setSeqId(1);
							if(defaultValName.indexOf(',')>=0){
								pricngPlanAttr.setDefaultValue(defaultVal.replaceAll(",", "/"));
								pricngPlanAttr.setDefaultValueName(defaultValName.replaceAll(",", "/"));
							}else{
								pricngPlanAttr.setDefaultValue(defaultVal);
								pricngPlanAttr.setDefaultValueName(defaultValName);
							}
							attrSpecService.addPricngPlanAttr(pricngPlanAttr);
						}
					}
				}else{
					for(PricngPlanAttr p:list){
						p.setStatusCd("11");
						attrSpecService.updatePricngPlanAttr(p);
					}
					if(null!=pricePlanSpecIdList){
						for(int i=0;i<pricePlanSpecIdList.length;i++){
							String pricePlanSpecId = pricePlanSpecIdList[i];
							String defaultValName ="";
							if(null!=defaultValNamesList && defaultValNamesList.length>0){
								defaultValName = defaultValNamesList[i];
							}
							String defaultVal ="";
							if(null!=defaultValsList && defaultValsList.length>0){
								defaultVal = defaultValsList[i];
							}
							pricngPlanAttr = new PricngPlanAttr();
							pricngPlanAttr.setPricingInfoId(new BigDecimal(priceId));
							pricngPlanAttr.setStatusCd("10");
							pricngPlanAttr.setSeqId(1);
							pricngPlanAttr.setAttrSpecId(new BigDecimal(pricePlanSpecId));
							if("".equals(defaultValName) || "null".equals(defaultValName)){
								defaultValName = defaultVal;
							}
							if(defaultValName.indexOf(',')>=0){
								pricngPlanAttr.setDefaultValue(defaultVal.replaceAll(",", "/"));
								pricngPlanAttr.setDefaultValueName(defaultValName.replaceAll(",", "/"));
							}else{
								pricngPlanAttr.setDefaultValue(defaultVal);
								pricngPlanAttr.setDefaultValueName(defaultValName);
							}
							attrSpecService.addPricngPlanAttr(pricngPlanAttr);
						}
					}
				}
				retMap.put("pricingTargetId", targetId);

				relIds=relIds.replaceAll("A","");
				if(!"".equals(relIds)){
					for(String id : relIds.split(",")){
						offerProdRelPricing.setOfferProdRelaId(Integer.parseInt(id));
						offerProdRelPricing.setPricingInfoId(Integer.parseInt(priceId));
						offerProdRelPricing.setPricingTargetId(Integer.parseInt(targetId));
						offerProdRelPricing.setStatusCd("10");
						priceServ.addProdRelPricing(offerProdRelPricing);
					}
				}
				
				pricePlanLifeCycle.setPricePlanId(new Long(priceId));
				pricePlanLifeCycle.setStatusCd("10");
				attrSpecService.updatePricePlanLifeCycle(pricePlanLifeCycle);
				
			}else {
				//Add:
				
				Integer priceInfoId = priceServ.addPricingPlan(pricingPlan);
				pricingPlan.setPricingInfoId(priceInfoId);
				retMap.put("priceInfoId", priceInfoId+"");
				
				pricingTarget.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
				pricingTarget.setPricingTargetName(pricingName);				//
				pricingTarget.setPricingTargetType("1");
				pricingTarget.setStatusCd("10");
				Integer pricingTargetId = priceServ.addPricingTarget(pricingTarget);
				retMap.put("pricingTargetId", pricingTargetId+"");
				
				relIds=relIds.replaceAll("A","");
				if(!"".equals(relIds)){
					for(String id : relIds.split(",")){
						if(!"".equals(id)){
							offerProdRelPricing.setOfferProdRelaId(Integer.parseInt(id));
							offerProdRelPricing.setPricingInfoId(priceInfoId);
							offerProdRelPricing.setPricingTargetId(pricingTargetId);
							offerProdRelPricing.setStatusCd("10");
							priceServ.addProdRelPricing(offerProdRelPricing);
						}
					}
				}
				if(null!=pricePlanSpecIdList&&pricePlanSpecIdList.length>0){
					for(int i=0;i<pricePlanSpecIdList.length;i++){
						String pricePlanSpecId = pricePlanSpecIdList[i];
						String defaultValName ="";
						if(null!=defaultValNamesList && defaultValNamesList.length>0){
							defaultValName = defaultValNamesList[i];
						}
						String defaultVal ="";
						if(null!=defaultValsList && defaultValsList.length>0){
							defaultVal = defaultValsList[i];
						}
						pricngPlanAttr = new PricngPlanAttr();
						pricngPlanAttr.setPricingInfoId(new BigDecimal(priceInfoId));
						pricngPlanAttr.setStatusCd("10");
						pricngPlanAttr.setAttrSpecId(new BigDecimal(pricePlanSpecId));
						pricngPlanAttr.setSeqId(1);
						if("".equals(defaultValName) || "null".equals(defaultValName)){
							defaultValName = defaultVal;
						}
						if(defaultValName.indexOf(',')>=0){
							pricngPlanAttr.setDefaultValue(defaultVal.replaceAll(",", "/"));
							pricngPlanAttr.setDefaultValueName(defaultValName.replaceAll(",", "/"));
						}else{
							pricngPlanAttr.setDefaultValue(defaultVal);
							pricngPlanAttr.setDefaultValueName(defaultValName);
						}
						attrSpecService.addPricngPlanAttr(pricngPlanAttr);
					}
				}
				ProdOfferPricing prodOfferPricing = new ProdOfferPricing();
				prodOfferPricing.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
				prodOfferPricing.setPricingInfoId(priceInfoId);
				prodOfferPricing.setPricingTargetId(pricingTargetId);
				priceServ.addProdOfferPricing(prodOfferPricing);
				
				pricePlanLifeCycle.setPricePlanId(Long.valueOf(priceInfoId));
				pricePlanLifeCycle.setStatusCd("10");
				attrSpecService.addPricePlanLifeCycle(pricePlanLifeCycle);
			}
			json.putAll(retMap);
			//DataInteractiveUtil.actionResponsePage(response, json);
		}catch (ParseException e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getCause(),null));
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getCause(),null));
		}
		return json.toString();
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
	
	
	private int calculateTransferFromDb(Integer billingPriority,
			String prodOfferType) {
		int result = 0;
		if("11".equals(prodOfferType)){//Main Offer
			result=(600 - billingPriority)/10;
		}else if("12".equals(prodOfferType)){//Add on offer
			result=(1000 - billingPriority)/10;
		}
		return result;
		
	}

	@RequestMapping(value = "/toBasicTariff")
	public ModelAndView toPardOfferPricePlanBasicTariff(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.price.priceType");//Tax Type
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Subject Type
		messages.add("eaap.op2.portal.price.objectType");//Subject Type
		messages.add("eaap.op2.portal.price.priceObject");//Pricing Subject
		messages.add("eaap.op2.portal.price.priceUnit1");//Charging Unit
		messages.add("eaap.op2.portal.price.priceUnit2");//Currency
		messages.add("eaap.op2.portal.price.feeType");//Charge Type
		messages.add("eaap.op2.portal.price.simple");
		messages.add("eaap.op2.portal.price.ladder");
		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.fixedAmount");//Fixed Amount
		messages.add("eaap.op2.portal.price.arrange");//Charge Arrange
		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.priceType2");//Recurring Charge
		messages.add("eaap.op2.portal.price.priceDesc");//Description

		messages.add("eaap.op2.portal.attrSpec.operationTips");
		messages.add("eaap.op2.portal.price.basic.endVal");
		messages.add("eaap.op2.portal.price.basic.greater");

		messages.add("eaap.op2.portal.pardProd.prodDateStartEnd");
		messages.add("eaap.op2.portal.price.basic.simpleNotNull");
		messages.add("eaap.op2.portal.price.Opertion");		
		
		messages.add("eaap.op2.portal.price.basic.equal1");		
		messages.add("eaap.op2.portal.price.basic.equal2");
		messages.add("eaap.op2.portal.price.basic.notNullBase");		
		messages.add("eaap.op2.portal.price.basic.notNullRateVal");		
		messages.add("eaap.op2.portal.price.priceType1");		
		messages.add("eaap.op2.portal.price.basic.endWith-1");

		ModelAndView mv = new ModelAndView();
		try {
			//按钮
			addTranslateMessage(mv, messages);
			mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
			mv.addObject("baseItemTypeList",mainDataServ.getMainInfo(EAAPConstants.BASE_ITEM_TYPE));//Tax Type SELECT
			mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));//Tax Type SELECT
			
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				componentPrice.setPriceId(Integer.parseInt(priceId));
				PricingPlan pricingPlan=new PricingPlan();
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				mv.addObject("componentPrice",componentPrice);//
				BasicTariff basicTariff=new BasicTariff();
				basicTariff.setPriceId(Integer.parseInt(priceId));
				basicTariff = pricePlanDetailServ.queryBasicTariff(basicTariff);
				mv.addObject("basicTariff",basicTariff);//
				mv.addObject("baseQosType",basicTariff.getBaseQosType());
				BaseTariffItemRel baseTariffItemRel=new BaseTariffItemRel();
				baseTariffItemRel.setPriceId(Integer.parseInt(priceId));
				List<BaseTariffItemRel> baseTariffItemRelist = pricePlanDetailServ.getBaseTariffItemRel(baseTariffItemRel);
				StringBuilder sb1 = new StringBuilder();

				for(BaseTariffItemRel b : baseTariffItemRelist){
					sb1.append(b.getItemId()).append(",");
				}
				if(sb1.toString().length()>0){
					String itemIds = sb1.toString().substring(0,sb1.toString().length()-1);
					mv.addObject("itemIds",itemIds);//
				}
				RatingCurverDetail ratingCurverDetail=new RatingCurverDetail();
				ratingCurverDetail.setPriceId(Integer.parseInt(priceId));
				List ratingCurverDetailList = pricePlanDetailServ.queryRatingCurveDetail(ratingCurverDetail);
				mv.addObject("ratingCurverDetailList",ratingCurverDetailList);//
				mv.setViewName("../pardOfferPricePlan/basicTariffAdd.jsp");
			}else{
				mv.addObject("basicTariff",new BasicTariff());//
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/basicTariffAdd.jsp");
			}
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	//Recurring Charge
	@RequestMapping(value = "/toRecurringCharge")
	public ModelAndView toPricePlanRecurringCharge(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.price.priceType");//Tax Type
		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.priceSubject");//Subject
		messages.add("eaap.op2.portal.price.priority");//Priority
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Effective Date to
		messages.add("eaap.op2.portal.price.priceDesc");//Description
		//Subject 表格
		messages.add("eaap.op2.portal.price.itemIdOrName");//Item Id Or Name
		messages.add("eaap.op2.portal.price.itemType");//Item Type
		messages.add("eaap.op2.portal.price.itemId");//Item Id 
		messages.add("eaap.op2.portal.price.itemName");//Item Name 
		messages.add("eaap.op2.portal.price.itemType");//Item Type 
		messages.add("eaap.op2.portal.price.description");//Description
		//button
		messages.add("eaap.op2.portal.doc.submit");//ok
		messages.add("eaap.op2.portal.doc.cancel");//cancel
		messages.add("eaap.op2.portal.devmgr.query");//query
		
		messages.add("eaap.op2.portal.price.priceType2");
		messages.add("eaap.op2.portal.price.selectPriceItem");
		
		//按钮
		ModelAndView mv = new ModelAndView();
		try {
			addTranslateMessage(mv, messages);
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));//Tax Type SELECT
			mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
			mv.addObject("itemTypeList",mainDataServ.getMainInfo(EAAPConstants.PRICEITEM_ITEMTYPE));//Item Type SELECT
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				String  prodIds = getRequestValue(request, "prodOfferId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				PricingPlan pricingPlan=new PricingPlan();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				
				PecurringFee pecurringFee=new PecurringFee();
				pecurringFee.setPriceId(Integer.parseInt(priceId));
				pecurringFee = pricePlanDetailServ.queryRecurringFee(pecurringFee);
				
				pecurringFee.setItemName(this.getPriceItemName(pecurringFee.getItemId(),request));
				
				//查询销售品信息
				ProdOffer prodOfferQuery=new ProdOffer();
				prodOfferQuery.setProdOfferId(new BigDecimal(prodIds));
				ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
				String prodOfferType=prodOffer.getOfferType();
                int billingPriorityValue=calculateTransferFromDb(pecurringFee.getBillingPriority(),prodOfferType);
                mv.addObject("billingPriorityValue",billingPriorityValue);
					
				mv.addObject("componentPrice",componentPrice);//
				mv.addObject("pecurringFee",pecurringFee);//
				mv.setViewName("../pardOfferPricePlan/recurringChargeAdd.jsp");
			}else{
				mv.addObject("pecurringFee",new PecurringFee());//
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/recurringChargeAdd.jsp");
			}
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	public String getPriceItemName(Long itemId,HttpServletRequest request){
		if(null == itemId || "".equals(itemId)){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"COMPONENT PRICE ITEMID IS NULL !", null);
		}
		
		PriceItem priceItem=new PriceItem();
		priceItem.setItemId(itemId);
		Object operateCode=request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE);
		if(operateCode!=null){
			priceItem.setOperateCode(String.valueOf(operateCode));
		}
		priceItem = priceServ.queryPriceItem(priceItem).get(0);
		
		return priceItem.getItemName();
	}
	
	//Rating Discount
	@RequestMapping(value = "/toRatingDiscount")
	public ModelAndView toPricePlanRatingDiscount(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.pardProd.product.timeRange");//Time Range Label
		messages.add("eaap.op2.portal.price.ratingDiscount.timeRange");//Time Range
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Effective Date to
		messages.add("eaap.op2.portal.price.objectType");//Subject Type
		messages.add("eaap.op2.portal.price.priceObject");//Pricing Subject
		messages.add("eaap.op2.portal.pardProd.product.ratingType");//Rating Type  Label
		messages.add("eaap.op2.portal.price.ratingDiscount.ratingType");//Rating Type 
		messages.add("eaap.op2.portal.price.priceDesc");//Description
		//Time Range 表格
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode");//Name/ID
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type1");//The Date Pattern 
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type2");//Time Pattern

		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.ratingDiscount.rating");//Charge
		messages.add("eaap.op2.portal.price.ratingDiscount.maxRatingMoney");//Charge
		
		messages.add("eaap.op2.portal.pardSpec.id");//id
		messages.add("eaap.op2.portal.pardSpec.name");//Name 
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type1");//The Date Pattern 
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type2");//Time Pattern 
		messages.add("eaap.op2.portal.pardSpec.note");//Remarks

		messages.add("eaap.op2.portal.price.ratingDiscount.ratingMoney");
		
		messages.add("eaap.op2.portal.price.priceType9");

		messages.add("eaap.op2.portal.pardProd.select.timerang");
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode");
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type1");
		messages.add("eaap.op2.portal.price.ratingDiscount.timeSpec.type2");
		messages.add("eaap.op2.portal.price.priceType9");
		
		//button
		messages.add("eaap.op2.portal.doc.submit");//ok
		messages.add("eaap.op2.portal.doc.cancel");//cancel
		messages.add("eaap.op2.portal.devmgr.query");//query   
		messages.add("eaap.op2.portal.price.selectPriceItem");
		//按钮
		ModelAndView mv = new ModelAndView();
		try {
			addTranslateMessage(mv, messages);
			mv.addObject("baseItemTypeList",mainDataServ.getMainInfo(EAAPConstants.BASE_ITEM_TYPE));//Subject Type SELECT
			mv.addObject("ratingTypeList",mainDataServ.getMainInfo(EAAPConstants.RATING_TYPE));//Rating Type SELECT
			//Time Range win
			mv.addObject("timePatternList",mainDataServ.getMainInfo(EAAPConstants.TIME_SPEC_TIME_PATTERN));//Subject Type SELECT
			mv.addObject("datePatternList",mainDataServ.getMainInfo(EAAPConstants.TIME_SPEC_DATE_PATTERN));//Rating Type SELECT
			
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));//Max Discount Money
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				PricingPlan pricingPlan=new PricingPlan();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				
				RatingDiscount ratingDiscount = new RatingDiscount();
				ratingDiscount.setPriceId(Integer.parseInt(priceId));
				ratingDiscount.setOperateCode((String)request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
				ratingDiscount = pricePlanDetailServ.queryRatingDiscount(ratingDiscount);
				
				BaseTariffItemRel baseTariffItemRel = new BaseTariffItemRel();
				baseTariffItemRel.setPriceId(Integer.parseInt(priceId));
				List<BaseTariffItemRel> baseTariffItemRelist = pricePlanDetailServ.getBaseTariffItemRel(baseTariffItemRel);
				StringBuilder sb1 = new StringBuilder();
				for(BaseTariffItemRel b : baseTariffItemRelist){
					sb1.append(b.getItemId()).append(",");
				}
				if(sb1.toString().length()>0){
					String itemIds = sb1.toString().substring(0,sb1.toString().length()-1);
					mv.addObject("itemIds",itemIds);//
				}
				mv.addObject("componentPrice",componentPrice);//
				mv.addObject("ratingDiscount",ratingDiscount);//
				mv.addObject("baseTariffItemRelist",baseTariffItemRelist);//
				mv.setViewName("../pardOfferPricePlan/ratingDiscountAdd.jsp");
			}else{
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/ratingDiscountAdd.jsp");
			}
		} catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	//One-time Charge
	@RequestMapping(value = "/toOnetimeCharge")
	public ModelAndView toPricePlanOnetimeCharge(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.price.priceType");//Tax Type
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Effective Date to
		messages.add("eaap.op2.portal.price.serviceType");//Service Type
		messages.add("eaap.op2.portal.price.priceSubject");//Subject
		messages.add("eaap.op2.portal.price.costType");//Cost Type
		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.priority");//Priority
		messages.add("eaap.op2.portal.price.priceDesc");//Description
		//Service Type 	和 Subject 表格
		messages.add("eaap.op2.portal.price.itemIdOrName");//Item Id Or Name
		messages.add("eaap.op2.portal.price.itemType");//Item Type
		
		messages.add("eaap.op2.portal.price.itemId");//Item Id 
		messages.add("eaap.op2.portal.price.itemName");//Item Name 
		messages.add("eaap.op2.portal.price.itemType");//Item Type 
		messages.add("eaap.op2.portal.price.description");//Description
		//button
		messages.add("eaap.op2.portal.doc.submit");//ok
		messages.add("eaap.op2.portal.doc.cancel");//cancel
		messages.add("eaap.op2.portal.devmgr.query");//query
		
		messages.add("eaap.op2.portal.price.priceType3");
		messages.add("eaap.op2.portal.price.selectPriceItem");
		//按钮
		ModelAndView mv = new ModelAndView();
		try{
			addTranslateMessage(mv, messages);
			//mv.addObject("baseItemTypeList",mainDataServ.getMainInfo(EAAPConstants.BASE_ITEM_TYPE));//Subject Type SELECT
			//mv.addObject("ratingTypeList",mainDataServ.getMainInfo(EAAPConstants.RATING_TYPE));//Rating Type SELECT
			//Subject win
			mv.addObject("itemTypeList",mainDataServ.getMainInfo(EAAPConstants.PRICEITEM_ITEMTYPE));//Item Type SELECT
			
			mv.addObject("changeTypeList",mainDataServ.getMainInfo(EAAPConstants.CHARGE_TYPE));// Cost Type
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));//currency Unit Type
			mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type
			
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				String  prodIds = getRequestValue(request, "prodOfferId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				PricingPlan pricingPlan=new PricingPlan();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				
				OneTimeCharge oneTimeCharge=new OneTimeCharge();
				oneTimeCharge.setPriceId(Integer.parseInt(priceId));
				oneTimeCharge = pricePlanDetailServ.queryOneTimeCharge(oneTimeCharge);

				oneTimeCharge.setBusinessItemName(this.getPriceItemName(oneTimeCharge.getBusinessItem(), request));
				
				//查询销售品信息
				ProdOffer prodOfferQuery=new ProdOffer();
				prodOfferQuery.setProdOfferId(new BigDecimal(prodIds));
				ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
				String prodOfferType=prodOffer.getOfferType();
                int billingPriorityValue=calculateTransferFromDb(oneTimeCharge.getBillingPriority(),prodOfferType);
                mv.addObject("billingPriorityValue",billingPriorityValue);
                
				oneTimeCharge.setOneTimeItemName(this.getPriceItemName(oneTimeCharge.getOneTimeItem(), request));
				mv.addObject("componentPrice",componentPrice);//
				mv.addObject("oneTimeCharge",oneTimeCharge);//
				mv.setViewName("../pardOfferPricePlan/onetimeChargeAdd.jsp");
			}else{
				mv.addObject("oneTimeCharge",new OneTimeCharge());//
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/onetimeChargeAdd.jsp");
			}
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	//Billing Discount
	@RequestMapping(value = "/toBillingDiscount")
	public ModelAndView toPricePlanBillingDiscount(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.price.reference");//Reference Subject
		messages.add("eaap.op2.portal.price.contrast");//Contrast Subject
		messages.add("eaap.op2.portal.price.priceType");//Tax Type 
		messages.add("eaap.op2.portal.price.priority");//Priority
		messages.add("eaap.op2.portal.price.promType");//Discount Type
		messages.add("eaap.op2.portal.price.maxDiscount");//Max Discount Money
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Effective Date to
		messages.add("eaap.op2.portal.price.calcType");//Calc Type
		
		messages.add("eaap.op2.portal.price.Section");//Calc Type 
		messages.add("eaap.op2.portal.price.finalSection");//Calc Type 
		messages.add("eaap.op2.portal.price.fee");//Charge
		messages.add("eaap.op2.portal.price.arrange");//Charge Arrange
		messages.add("eaap.op2.portal.price.discount");//Recurring Charge
		messages.add("eaap.op2.portal.price.priceDesc");//Description
		//Reference Subject和 Contrast Subject 	 表格
		messages.add("eaap.op2.portal.price.itemIdOrName");//Item Id Or Name
		messages.add("eaap.op2.portal.price.itemType");//Item Type
		
		messages.add("eaap.op2.portal.price.itemId");//Item Id 
		messages.add("eaap.op2.portal.price.itemName");//Item Name 
		messages.add("eaap.op2.portal.price.itemType");//Item Type 
		messages.add("eaap.op2.portal.price.description");//Description
		//button
		messages.add("eaap.op2.portal.doc.submit");//ok 
		messages.add("eaap.op2.portal.doc.cancel");//cancel
		messages.add("eaap.op2.portal.devmgr.query");//query 

		messages.add("eaap.op2.portal.price.priceType4");
		messages.add("eaap.op2.portal.manager.auth.add");
		messages.add("eaap.op2.portal.price.priceType2");
		messages.add("eaap.op2.portal.price.basic.endVal");
		messages.add("eaap.op2.portal.price.basic.greater");
		messages.add("eaap.op2.portal.pardProd.prodDateStartEnd");
		messages.add("eaap.op2.portal.price.basic.equal1");		
		messages.add("eaap.op2.portal.price.basic.equal2");

		messages.add("eaap.op2.portal.price.billingDiscount.endWith0d");
		messages.add("eaap.op2.portal.price.billingDiscount.nLessThand");
		messages.add("eaap.op2.portal.price.billingDiscount.dNotNull");
		messages.add("eaap.op2.portal.price.billingDiscount.nNotNull");
		messages.add("eaap.op2.portal.price.equal");
		messages.add("eaap.op2.portal.price.basic.endWith-1");
		messages.add("eaap.op2.portal.price.selectPriceItem");
		
		//按钮
		ModelAndView mv = new ModelAndView();
		try{
			addTranslateMessage(mv, messages);
			mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
			mv.addObject("promTypeList",mainDataServ.getMainInfo("BillingDiscount_PromType"));//Discount Type
			mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE,request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE)));//Max Discount Money
			//Subject win
			mv.addObject("itemTypeList",mainDataServ.getMainInfo(EAAPConstants.PRICEITEM_ITEMTYPE));//Item Type SELECT
			
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				String  prodIds = getRequestValue(request, "prodOfferId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				PricingPlan pricingPlan=new PricingPlan();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				
				BillingDiscount billingDiscount=new BillingDiscount();
				billingDiscount.setPriceId(Integer.parseInt(priceId));
				billingDiscount = pricePlanDetailServ.queryBillingDiscount(billingDiscount);
				
				billingDiscount.setEligibleItemName(this.getPriceItemName(billingDiscount.getEligibleItem(), request));
				
				billingDiscount.setTargetItemName(this.getPriceItemName(billingDiscount.getTargetItem(), request));
				
				//查询销售品信息
				ProdOffer prodOfferQuery=new ProdOffer();
				prodOfferQuery.setProdOfferId(new BigDecimal(prodIds));
				ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
				String prodOfferType=prodOffer.getOfferType();
                int billingPriorityValue=calculateTransferFromDb(billingDiscount.getBillingPriority(),prodOfferType);
                mv.addObject("billingPriorityValue",billingPriorityValue);
                
				RatingCurverDetail ratingCurverDetail=new RatingCurverDetail();
				ratingCurverDetail.setPriceId(Integer.parseInt(priceId));
				List ratingCurverDetailList = pricePlanDetailServ.queryRatingCurveDetail(ratingCurverDetail);
				mv.addObject("componentPrice",componentPrice);//
				mv.addObject("billingDiscount",billingDiscount);//
				mv.addObject("ratingCurverDetailList",ratingCurverDetailList);//
				mv.setViewName("../pardOfferPricePlan/billingDiscountAdd.jsp");
			}else{
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/billingDiscountAdd.jsp");
			}
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	//Free Resource 免费资源
	@RequestMapping(value = "/toFreeResource")
	public ModelAndView toPricePlanFreeResource(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//基础信息
		messages.add("eaap.op2.portal.price.priceType5");//Free Resource
		messages.add("eaap.op2.portal.price.priceName");//Price Name
		messages.add("eaap.op2.portal.price.freeResourceItem"); //Free Resource Item
		messages.add("eaap.op2.portal.price.freeResourceCycle");//Free Resource Cycle
		messages.add("eaap.op2.portal.price.freeResourcePeriod");//Free Resource Period
		messages.add("eaap.op2.portal.price.expiryType");//Expiry Type
		messages.add("eaap.op2.portal.price.subscriberStatusEligibility");//Subscriber Status Eligibility
		messages.add("eaap.op2.portal.price.notificationTemplate");//Notification Template
		messages.add("eaap.op2.portal.price.forwardCycle");//Forward Cycle
		messages.add("eaap.op2.portal.price.amountDistributionType");//Amount Distribution Type
		messages.add("eaap.op2.portal.price.even");//Even
		messages.add("eaap.op2.portal.price.designatedAmount");//Designated Amount
		messages.add("eaap.op2.portal.price.freeSourceAmount");//Free Source Amount
		messages.add("eaap.op2.portal.price.specificAmount");//Specific Amount  
		messages.add("eaap.op2.portal.price.unlimited");//Unlimited
		messages.add("eaap.op2.portal.price.amount");//Amount
		messages.add("eaap.op2.portal.price.priceTime");//Effective Date
		messages.add("eaap.op2.portal.price.to");//Effective Date to
		messages.add("eaap.op2.portal.price.priceDesc");//Description
		messages.add("eaap.op2.portal.price.cycleRange");//Cycle Range

		messages.add("eaap.op2.portal.price.priceType4");
		messages.add("eaap.op2.portal.manager.auth.add");
		messages.add("eaap.op2.portal.price.priceType2");
		messages.add("eaap.op2.portal.price.basic.endVal");
		messages.add("eaap.op2.portal.price.basic.endValNotNUll");
		messages.add("eaap.op2.portal.price.basic.endValTip");
		messages.add("eaap.op2.portal.price.basic.greater");
		messages.add("eaap.op2.portal.pardProd.prodDateStartEnd");
		messages.add("eaap.op2.portal.price.basic.equal1");		
		messages.add("eaap.op2.portal.price.basic.equal2");
		messages.add("eaap.op2.portal.price.basic.notNullBase");		
		messages.add("eaap.op2.portal.price.basic.notNullRateVal");		
		messages.add("eaap.op2.portal.price.priceType1");		
		messages.add("eaap.op2.portal.price.basic.endWith-1");
		
		//button
		messages.add("eaap.op2.portal.doc.submit");//ok
		messages.add("eaap.op2.portal.doc.cancel");//cancel
		messages.add("eaap.op2.portal.devmgr.query");//query   

		ModelAndView mv = new ModelAndView();
		try {
			addTranslateMessage(mv, messages);
			mv.addObject("freeresourcecyclelist",mainDataServ.getMainInfo(EAAPConstants.PRICE_PLAN_FREE_RESOURCE_CYCLE));					//Free Resource Cycle SELECT
			mv.addObject("freeResourcePeriodUnitList",mainDataServ.getMainInfo(EAAPConstants.PRICE_PLAN_FREE_RESOURCE_PERIOD));	//Free Resource Period SELECT
			mv.addObject("expiryTypeList",mainDataServ.getMainInfo(EAAPConstants.PRICE_PLAN_EXPIRY_TYPE));												//Expiry Type SELECT
			mv.addObject("trafficUnitList",mainDataServ.getMainInfo(EAAPConstants.PRICEPLAN_TRAFFIC_UNIT));												//TRAFFIC_UNIT SELECT
			//mv.addObject("subscriberStatusList",mainDataServ.getMainInfo(EAAPConstants.PRICEPLAN_SUBSCRIBER_STATUS));					//Subscriber Status Eligibility
			
			String actionType = getRequestValue(request, "actionType");
			mv.addObject("actionType",actionType);
			if("update".equals(actionType)||"detail".equals(actionType)){
				String priceInfoId = getRequestValue(request, "priceInfoId");
				String priceId = getRequestValue(request, "priceId");
				Map map = new HashMap();
				ComponentPrice componentPrice=new ComponentPrice();
				PricingPlan pricingPlan=new PricingPlan();
				componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
				pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
				map.put("priceId", priceId);
				map.put("pro_mysql", 0);
				map.put("page_record", 1);
				componentPrice = pricePlanDetailServ.queryComponentPrice(map).get(0);
				componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
				componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
				
				FreeResource freeResource = new FreeResource();
				freeResource.setPriceId(Long.parseLong(priceId));
				freeResource = pricePlanDetailServ.queryFreeResource(freeResource);
				
				FreeResourceSeg freeResourceSeg=new FreeResourceSeg();
				freeResourceSeg.setPriceId(Long.parseLong(priceId));
				List<FreeResourceSeg> freeResourceSegList = pricePlanDetailServ.queryFreeResourceSeg(freeResourceSeg);
				mv.addObject("freeResourceSegList",freeResourceSegList);//
				String amount="";
				String measureId="";
				if(freeResource.getAllowanceType().equals("1")){
					if(freeResourceSegList!=null  &&  freeResourceSegList.size()>0){
						FreeResourceSeg frs = freeResourceSegList.get(0);
						amount 		= frs.getAmount()==null?"":frs.getAmount().toString();
						measureId 	= frs.getMeasureId()==null?"":frs.getMeasureId().toString();
					}
				}
				mv.addObject("amount",amount);
				mv.addObject("measureId",measureId);
				
				mv.addObject("componentPrice",componentPrice);//
				mv.addObject("freeResource",freeResource);//
				mv.setViewName("../pardOfferPricePlan/freeResourceAdd.jsp");
			}else{
				mv.addObject("componentPrice",new ComponentPrice());//
				mv.setViewName("../pardOfferPricePlan/freeResourceAdd.jsp");
			}
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getStackTrace()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getStackTrace().toString(), null));
		}
		return mv;
	}

	// Free Resource --- Subscriber Status Eligibility
	@RequestMapping(value = "/getUserStateListTree")
	@ResponseBody
	public String getUserStateListTree(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String itemIds = getRequestValue(request, "itemIds");
			List<MainData> userStateList = mainDataServ.getMainInfo(EAAPConstants.PRICEPLAN_SUBSCRIBER_STATUS);
			JSONArray retList=new JSONArray();
			if(userStateList.size()>0){
				JSONObject mapL =null;
				JSONObject stateMap = null;
				for(MainData p : userStateList){
					mapL = new JSONObject();
					mapL.put("id",p.getCepCode());
					mapL.put("text", p.getCepName());
					stateMap = new JSONObject();
					stateMap.put("selected", false);
					stateMap.put("opened", false);
					if(!"".equals(itemIds)&&null!=itemIds&&itemIds.contains(p.getCepCode()+"")){
						stateMap.put("selected", true);
					}
					mapL.put("state", stateMap);
					retList.add(mapL);
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,retList);
		}catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	//Partner Product - Price Plan(Basic Charge) -Charging Unit
	@RequestMapping(value = "/getRatingUnitType", method = RequestMethod.POST)
	@ResponseBody
	public String getPricePlanRatingUnitType(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		String applicType = getRequestValue(request, "applicType");
		String value = getRequestValue(request, "baseItemType");
		JSONArray ratingUnitTypeList = new JSONArray();

		if("2".equals(applicType)){
			value = "API";
		}
		Map<String,String> ratingUnitType = (Map)mainDataServ.getMainInfo(EAAPConstants.RATING_UNIT_TYPR_JL+value,mainDataServ.MAININFO_MAP) ;//EAAPConstants.RATING_UNIT_TYPR_JL
		Iterator<Entry<String, String>> iter = ratingUnitType.entrySet().iterator();   
		JSONObject mapTmp = null;
		Entry<String,String> entry =null;
		while(iter.hasNext()) {
			mapTmp = new JSONObject() ;
			entry = (Entry<String,String>)iter.next();
			mapTmp.put("key", entry.getKey().toString()) ;
			mapTmp.put("value", entry.getValue().toString()) ;
			ratingUnitTypeList.add(mapTmp) ;
		}
		JSONObject json = new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		json.put(RETURN_DESC,ratingUnitTypeList);
		return json.toString();
	}
	
	// Partner Product - Price Plan(Basic Charge) -Pricing Subject
	@RequestMapping(value = "/getPricingSubjectTree")
	@ResponseBody
	public String pricePlanPricingSubjectTree(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String baseItemType = getRequestValue(request, "baseItemType");
			String itemIds = getRequestValue(request, "itemIds");
			
			HashMap itemMap = new HashMap();
			if(baseItemType!=null && !"".equals(baseItemType)){
				itemMap.put("baseItemType", baseItemType);
				itemMap.put("parentItemId", baseItemType);
			}
			itemMap.put("itemType", "1");
			itemMap.put("pro_mysql", 0);
			itemMap.put("page_record", 999);
			
			itemMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,getPricePlanPricingSubjectTree(itemMap,itemIds));
		}catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	public JSONArray getPricePlanPricingSubjectTree(HashMap itemMap,String itemIds) {
		List<PriceItem> priceItemList = priceServ.queryPriceItemList(itemMap);
		JSONArray childrenList=new JSONArray();
		if(priceItemList.size()>0){
			JSONObject mapL =null;
			JSONObject stateMap = null;
			JSONArray reList=null;
			for(PriceItem p : priceItemList){
				reList=new JSONArray();
				mapL = new JSONObject();
				mapL.put("id",p.getItemId());
				mapL.put("text", p.getItemName());
				stateMap = new JSONObject();
				stateMap.put("selected", false);
				stateMap.put("opened", false);
				if(!"".equals(itemIds)&&null!=itemIds&&itemIds.contains(p.getItemId()+"")){
					stateMap.put("selected", true);
				}
				mapL.put("state", stateMap);
				itemMap.put("parentItemId", p.getItemId());
				if(0==p.getIsLeaf()){
					reList=getPricePlanPricingSubjectTree(itemMap,itemIds);
				}
				if(reList!=null&&reList.size()>0){
					mapL.put("children",reList);
				}
				childrenList.add(mapL);
			}
		}
		return childrenList;
	}

	@RequestMapping(value = "/showRatingUnitTypeList")
	@ResponseBody
	public String showRatingUnitTypeList(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		String applicType	= getRequestValue(request, "applicType");
		String value			= getRequestValue(request, "baseItemType");

		 if("2".equals(applicType)){
			 value = "API";
		 }
		 Map<String,String> ratingUnitType = getMainInfo(EAAPConstants.RATING_UNIT_TYPR_JL+value) ;//EAAPConstants.RATING_UNIT_TYPR_JL
		 Iterator<Entry<String, String>> iter = ratingUnitType.entrySet().iterator();   
		 JSONArray reList=new JSONArray();
		 while(iter.hasNext()) {

		 	 Entry<String,String> entry = (Entry<String,String>)iter.next();			 
			JSONObject mapR = new JSONObject();
			mapR.put("key", entry.getKey().toString());
			mapR.put("value", entry.getValue().toString());
			reList.add(mapR);
		 }
		JSONObject json = new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		json.put(RETURN_DESC,reList);
		return json.toString();
	}

	private Map<String,String> getMainInfo(String mdtSign){
	  	  MainDataType mainDataType = new MainDataType();
	  	  MainData mainData = new MainData();
	  	  Map<String,String> stateMap = new LinkedHashMap<String,String>() ;
	  	  mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
	  	  mainDataType.setMdtSign(mdtSign) ;
		  mainDataType = priceServ.selectMainDataType(mainDataType).get(0) ;
		  mainData.setMdtCd(mainDataType.getMdtCd()) ;
		  mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
		  List<MainData> mainDataList =priceServ.selectMainData(mainData) ;
		 
		  if(mainDataList!=null && mainDataList.size()>0){
			  for(int i=0;i<mainDataList.size();i++){
				  stateMap.put(mainDataList.get(i).getCepCode(),
						          mainDataList.get(i).getCepName()) ;
			  }
		  }
	  	return stateMap ;
	  }
	
	
	//Partner Product -Price Plan(Recurring Charge) -Subject
	@RequestMapping(value = "/getSubjectList", method = RequestMethod.POST)
	@ResponseBody
	public String getPricePlanSubjectList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			//获取请求次数
		    String draw = "0";
		    draw = getRequestValue(request, "draw");
		    //数据起始位置
		    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			HashMap paramMap = new HashMap();
			//总记录数
		    int recordsTotal = priceServ.countPriceItemList(paramMap);
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			String itemIdOrName = getRequestValue(request, "itemIdOrName");
			String itemType = getRequestValue(request, "itemType");
			String queryParamsData = getRequestValue(request, "queryParamsData");
//			queryParamsData = null==queryParamsData?null==prar.get("itemTypes")?itemTypes:prar.get("itemTypes").toString():queryParamsData;

			if(!"".equals(itemIdOrName) && null!=itemIdOrName){
				paramMap.put("itemIdOrName", itemIdOrName);
			}
			if(!"".equals(itemType) && null!=itemType){
				paramMap.put("itemType", itemType);
				
			}else if(null!=queryParamsData&&!"".equals(queryParamsData)){
				paramMap.put("itemTypes", queryParamsData.split(","));
			}
			
			paramMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			List<PriceItem> pardSpecList = priceServ.queryPriceItemList(paramMap);
			JSONArray dataList=new JSONArray();
			JSONArray subDataList=null;
			for(PriceItem priceItem:pardSpecList){
				subDataList=new JSONArray();
				subDataList.add("<input type='checkbox' value='' class='checkboxes' name='' />");
				subDataList.add(priceItem.getItemId());
				subDataList.add(priceItem.getItemName());
				subDataList.add(priceItem.getItemType());
				subDataList.add(getItemType(String.valueOf(priceItem.getItemType())));
				subDataList.add(priceItem.getDescription());
				dataList.add(subDataList);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",recordsTotal);
			json.put("recordsFiltered", priceServ.countPriceItemList(paramMap));
			json.put("data",dataList.toJSONString());
		}catch (Exception e) {
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	private String getItemType(String value){
		String reValue =null;
		if("1".equals(value)){
			reValue="eaap.op2.portal.price.itemType1";
	    }else if("2".equals(value)){
			reValue="eaap.op2.portal.price.itemType2";
	    }else if("3".equals(value)){
			reValue="eaap.op2.portal.price.itemType3";
	    }else if("4".equals(value)){
			reValue="eaap.op2.portal.price.itemType4";
	    }
		if(reValue!=null){
			return getI18nMessage(reValue);
		}
		else{
			return value;
		}
	}
	
	@RequestMapping(value = "/getTimeRangeList", method = RequestMethod.GET)
	@ResponseBody
	public String getTimeRangeList(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		try{
			Map paramMap = new HashMap();
			//获取请求次数
		    String draw = "".equals(getRequestValue(request, "draw"))?"1":getRequestValue(request, "draw");;
		    //数据起始位置
		    int startRow = "".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			
			String idOrName =getRequestValue(request, "idOrName"); 
			String dateMode =getRequestValue(request, "dateMode"); 
			String timeMode =getRequestValue(request, "timeMode");
			if(!"".equals(idOrName)&&null!=idOrName){
				paramMap.put("segNameOrId", idOrName);
			}
			if(!"".equals(dateMode)&&null!=dateMode){
				paramMap.put("dateMode", dateMode);
			}
			if(!"".equals(timeMode)&&null!=timeMode){
				paramMap.put("timeMode", timeMode);
			}

			paramMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			List<TimeSegDef> timeSegDefList = attrSpecService.queryTimeSeqDef(paramMap);
			JSONArray dataList=new JSONArray();
			JSONObject subData = null;
			for(TimeSegDef timeSegDef:timeSegDefList){
				subData=new JSONObject();
				subData.put("INDEX","<input type='radio' value='"+timeSegDef.getSegId()+"' class='checkboxes' name='selSegId' />");
				subData.put("SEG_ID",timeSegDef.getSegId());
				subData.put("SEG_NAME",timeSegDef.getSegName());
				subData.put("DATE_MODE",null==timeSegDef.getDateMode()?"":timeSegDef.getDateMode());
				subData.put("TIME_MODE",null==timeSegDef.getTimeMode()?"":timeSegDef.getTimeMode());
				subData.put("DESCRIPTION",null==timeSegDef.getDescription()?"":timeSegDef.getDescription());
				dataList.add(subData);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",timeSegDefList.size());
			json.put("recordsFiltered", timeSegDefList.size());
			json.put("data",dataList.toJSONString());
		}catch (Exception e) {
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	

	@RequestMapping(value = "/getPriceItemList", method = RequestMethod.GET)
	@ResponseBody
	public String getPriceItemList(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		try{
			Map paramMap = new HashMap();
			//获取请求次数
		    String draw = "".equals(getRequestValue(request, "draw"))?"1":getRequestValue(request, "draw");;
		    //数据起始位置
		    int startRow = "".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			
			String itemIdOrName	= getRequestValue(request, "itemIdOrName"); 
			String itemType 	= getRequestValue(request, "itemType"); 
			String itemTypes	= getRequestValue(request, "itemTypes");

			if(!"".equals(itemIdOrName) && null!=itemIdOrName){
				paramMap.put("itemIdOrName", itemIdOrName);
			}
			if(!"".equals(itemType) && null!=itemType){
				paramMap.put("itemType", itemType);
			}else if(null!=itemTypes&&!"".equals(itemTypes)){
				paramMap.put("itemTypes", itemTypes.split(","));
			}

			paramMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			Integer cnt = priceServ.countPriceItemList(paramMap);
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			List<PriceItem> priceItemList = priceServ.queryPriceItemList(paramMap);
			JSONArray dataList=new JSONArray();
			JSONObject subData = null;
			for(PriceItem item:priceItemList){
				subData=new JSONObject();
				subData.put("INDEX","<input type='radio' value='"+item.getItemId()+"' class='checkboxes' name='selItemId' />");
				subData.put("ITEM_ID",item.getItemId());
				subData.put("ITEM_NAME",item.getItemName());
				subData.put("ITEM_TYPE",null==item.getItemType()?"":item.getItemType());
				subData.put("ITEM_TYPE_NAME",null==item.getItemTypeName()?"":item.getItemTypeName());
				subData.put("DESCRIPTION",null==item.getDescription()?"":item.getDescription());
				dataList.add(subData);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",cnt);
			json.put("recordsFiltered", cnt);
			json.put("data",dataList.toJSONString());
		}catch (Exception e) {
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","{}");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	

	@RequestMapping(value = "/getFreeResourceItemList", method = RequestMethod.GET)
	@ResponseBody
	public String getFreeResourceItemList(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		try{
			Map paramMap = new HashMap();
			//获取请求次数
		    String draw = "".equals(getRequestValue(request, "draw"))?"1":getRequestValue(request, "draw");;
		    //数据起始位置
		    int startRow = "".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			String idOrName =getRequestValue(request, "idOrName"); 
			if(!"".equals(idOrName)&&null!=idOrName){
				paramMap.put("idOrName", idOrName);
			}
			paramMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			Integer cnt = pricePlanDetailServ.queryFreeResourceItemListCount(paramMap);
			List<Map> itemList = pricePlanDetailServ.queryFreeResourceItemList(paramMap);
			JSONArray dataList=new JSONArray();
			JSONObject subData = null;
			for(int i=0; i<itemList.size(); i++){
				Map map = itemList.get(i);
				subData=new JSONObject();
				subData.put("INDEX","<input type='radio' value='"+map.get("ITEM_ID")+"' class='checkboxes' name='itemId' />");
				subData.put("ITEM_ID",map.get("ITEM_ID"));
				subData.put("ITEM_NAME",map.get("ITEM_NAME"));
				subData.put("UNIT",map.get("UNIT"));
				subData.put("MEASURE_ID",map.get("MEASURE_ID"));
				dataList.add(subData);
			}
			json.put("draw", draw);
			json.put("recordsTotal",cnt);
			json.put("recordsFiltered",cnt);
			json.put("data",dataList.toJSONString());
		}catch (Exception e) {
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
//	private String getDateMode(String value){
//		String reValue =null;
//		if("1".equals(value)){
//			reValue="Week";
//	    }else if("4".equals(value)){
//			reValue="DD";
//	    }
//		if(reValue!=null){
//			return value;
//		}
//		else{
//			return value;
//		}
//	}
//	private String getTimeMode(String value){
//		String reValue =null;
//		if("0".equals(value)){
//			reValue="Everyday";
//	    }else if("1".equals(value)){
//			reValue="Time to time";
//	    }
//		if(reValue!=null){
//			return value;
//		}
//		else{
//			return value;
//		}
//	}
	
	@RequestMapping(value = "/deletePricePlan", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlan",parameterKey="pricingInfoId",privilege="")
	@ResponseBody
	public String pardOffertPricePlanDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String priceInfoId = getRequestValue(request, "priceInfoId");
			String prodOfferId = getRequestValue(request, "prodOfferId");
			String pricingTargetId = getRequestValue(request, "pricingTargetId");
			if(!"".equals(priceInfoId)&&null!=priceInfoId&&!"".equals(prodOfferId)&&null!=prodOfferId&&!"".equals(pricingTargetId)&&null!=pricingTargetId){
				priceServ.deletePardOffertPricePlan(priceInfoId,prodOfferId,pricingTargetId);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/deletePrice", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String pardOffertPricePlanDetailDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String priceId = getRequestValue(request, "priceId");

			if(!"".equals(priceId)&&null!=priceId){
				ComponentPrice componentPrice=new ComponentPrice();
				componentPrice.setPriceId(Integer.parseInt(priceId));
				componentPrice.setStatusCd("11");
				pricePlanDetailServ.updateComponentPrice(componentPrice);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/saveOrUpdatePricePlan", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlan",parameterKey="pricingInfoId",privilege="")
	@ResponseBody
	public String saveOrUpdatePricePlan(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			json = pricePlanDetailServ.saveOrUpdatePricePlan(request,response,actionKey,i18nLoader);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/saveOrUpdateBasicTariff", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateBasicTariff(final HttpServletRequest request,final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateBasicTariff(request,response,actionKey,i18nLoader);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/saveOrUpdateRecurringFee", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateRecurringFee(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateRecurringFee(request,response,actionKey,i18nLoader);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/saveOrUpdateRatingDiscount", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateRatingDiscount(final HttpServletRequest request, final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateRatingDiscount(request,response,actionKey);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/saveOrUpdateFreeResource", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateFreeResource(final HttpServletRequest request, final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateFreeResource(request,response,actionKey);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/saveOrUpdateOneTimeFee", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateOneTimeFee(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateOneTimeFee(request,response,actionKey,i18nLoader);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/saveOrUpdateBillingDiscount", method = RequestMethod.POST)
	@SavePermission(center="pard",module="pricePlanRule",parameterKey="priceId",privilege="")
	@ResponseBody
	public String saveOrUpdateBillingDiscount(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			String priceId = pricePlanDetailServ.saveOrUpdateBillingDiscount(request,response,actionKey);
			json.put("priceId", priceId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/goToSelZoneMap", method = RequestMethod.GET)
	public ModelAndView goToSelZoneMap(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("../common/googlemap.jsp");
		return mv;
	}
	@RequestMapping(value = "/getZoneSpecList", method = RequestMethod.GET)
	@ResponseBody
	public String getZoneSpecList(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		try{
			Map paramMap = new HashMap();
			//获取请求次数
		    String draw = "".equals(getRequestValue(request, "draw"))?"1":getRequestValue(request, "draw");;
		    //数据起始位置
		    int startRow = "".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
		    
		    
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			
			String lat = getRequestValue(request, "lat");
			String lng = getRequestValue(request, "lng");
			String nameOrId = getRequestValue(request, "nameOrId");
			String radius = getRequestValue(request, "radius");
			if(!"".equals(lat)&&null!=lat){
				paramMap.put("lat", lat);
			}
			if(!"".equals(lng)&&null!=lng){
				paramMap.put("lng", lng);
			}
			if(!"".equals(nameOrId)&&null!=nameOrId){
				paramMap.put("nameOrId", nameOrId);
			}
			if(!"".equals(radius)&&null!=radius){
				paramMap.put("radius", radius);
			}
			
			paramMap.put(TenantInterceptor.OPERATE_CODE, request.getSession().getAttribute(TenantInterceptor.SESSION_OPERATE_CODE));
			List<RsSysCellDef> rsSysCellDefSegList = attrSpecService.queryRsSysCellDef(paramMap);
			JSONArray dataList=new JSONArray();
			JSONObject subData = null;
			for( RsSysCellDef rsSysCellDefSeg:rsSysCellDefSegList){
				subData=new JSONObject();
				subData.put("INDEX","<input type='checkbox' value='"+rsSysCellDefSeg.getOpId()+"' class='checkboxes' name='selOpId' />");
				subData.put("OP_ID",rsSysCellDefSeg.getOpId());
				subData.put("OP_NAME",rsSysCellDefSeg.getOpName());
				subData.put("OP_DESC",null==rsSysCellDefSeg.getDescription()?"":rsSysCellDefSeg.getDescription());
				subData.put("LONGITUDE",null==rsSysCellDefSeg.getLongitude()?"":rsSysCellDefSeg.getLongitude());
				subData.put("LATITUDE",null==rsSysCellDefSeg.getLatitude()?"":rsSysCellDefSeg.getLatitude());
				
				dataList.add(subData);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",rsSysCellDefSegList.size());
			json.put("recordsFiltered", rsSysCellDefSegList.size());
			json.put("data",dataList.toJSONString());
		}catch (Exception e) {
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}

}
