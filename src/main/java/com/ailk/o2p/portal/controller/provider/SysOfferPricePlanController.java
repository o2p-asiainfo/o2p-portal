/** 
 * Project Name:o2p-portal-pro 
 * File Name:SysOfferPricePlanController.java 
 * Package Name:com.ailk.o2p.portal.controller.provider 
 * Date:2015年7月18日下午2:41:44 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.controller.provider;  

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.BaseTariffItemRel;
import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.CharSpec;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.PriceItem;
import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.RsSysCellDef;
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.pardSpec.IPardSpecServ;
import com.ailk.o2p.portal.service.price.AttrSpecServ;
import com.ailk.o2p.portal.service.price.PricePlanDetailServiceImpl;
import com.ailk.o2p.portal.service.price.PriceServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

/** 
 * ClassName:SysOfferPricePlanController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年7月18日 下午2:41:44 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
@Controller
public class SysOfferPricePlanController extends BaseController{

	private static Logger log = Logger.getLog(SysOfferPricePlanController.class);

	@Autowired
	private PriceServiceImpl priceServ;
//	@Autowired
//	private MessageServ messageServ;
	@Autowired
	private AttrSpecServ attrSpecService;
	@Autowired
	private PricePlanDetailServiceImpl pricePlanDetailServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	public List<Map> pricePlanSpecList = new ArrayList<Map>();
	@Autowired
	private IPardSpecServ iPardSpecServ;
		
	
	
	
	@RequestMapping(value = "/provider/toPricePlan")
	public ModelAndView toPardOffertPricePlan(final HttpServletRequest request,
			final HttpServletResponse response) {
	
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
		
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("activationOffsetTypeList",mainDataServ.getMainInfo(EAAPConstants.ACTIVATION_OFFSET_TYPE));
		mv.addObject("activationOffsetList",mainDataServ.getMainInfo(EAAPConstants.ACTIVATION_OFFSET));
		mv.addObject("pricePlanValidityPeriodList",mainDataServ.getMainInfo(EAAPConstants.PRICE_PLAN_VALIDITY_PERIOD));
		mv.addObject("priceTypeList",mainDataServ.getMainInfo(EAAPConstants.COMPONENTPRICE_PRICETYPE));
		mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));
		Map map = new HashMap();
		String componentId = getRequestValue(request, "componentId");
		Org org = (Org)request.getSession().getAttribute("userBean");
		String orgId = "";
		if(org!=null&&!Strings.isEmpty(org.getOrgId()+"")){
			 orgId = org.getOrgId()+"";
		}
		map.put("systemId", componentId);
		map.put("orgId", orgId);
		String state="";
		String stateUp="";
		//获取系统状态
		List stateL = priceServ.getSystemStatus(map);
		if(stateL.size()>0){
			Map stateM = (Map)stateL.get(0);
			state = StringUtil.valueOf(stateM.get("STATE"));
			stateUp =StringUtil.valueOf(stateM.get("UP_STATE"));
			if("B".equals(state)){
				mv.addObject("readonly","readonly");
				mv.addObject("state",state);
				mv.addObject("stateUp",stateUp);
				
			}else if("D".equals(state)){
				if("E".equals(stateUp)||"F".equals(stateUp)){
					mv.addObject("readonly","");
					mv.addObject("state",state);
					mv.addObject("stateUp",stateUp);
				}else{
					mv.addObject("readonly","readonly");
					mv.addObject("state",state);
					mv.addObject("stateUp",stateUp);
				}
			}else if("A".equals(state)||"C".equals(state)||"G".equals(state)){
				mv.addObject("readonly","");
				mv.addObject("state",state);
				mv.addObject("stateUp",stateUp);
			}else{
				mv.addObject("readonly","readonly");
				mv.addObject("state",state);
				mv.addObject("stateUp",stateUp);
			}
		}
		 

		String defaultName=getRequestValue(request,"defaultName");
		String actionType = getRequestValue(request, "actionType");
		String prodIds = getRequestValue(request, "prodOfferId");
//		if(!"".equals(prodIds)&&null!=prodIds){
//			pricingTarget.setProdOfferId(Integer.parseInt(prodIds));
//			getTreeParams = "prodIds:"+prodIds;
//		}
		mv.addObject("actionType",actionType);
		mv.addObject("defaultName", defaultName);
		if("update".equals(actionType)||"detail".equals(actionType)){
//			prodOfferPricing.setPricingInfoId(pricingPlan.getPricingInfoId());
//			getTreeParams += ",pricingTargetId:"+pricingTarget.getPricingTargetId();
//			getTreeParams += ",idvalue:" + this.getSelectedTreeIdByTarget(pricingTarget.getPricingTargetId());//全部的
			String pricingInfoId = getRequestValue(request, "pricingInfoId");
			String pricingTargetId = getRequestValue(request, "pricingTargetId");
			String applicTyp = getRequestValue(request, "pricingPlan.applicTyp");
	//		PricingTarget pricingTarget=new PricingTarget();
			
	//		if(!"".equals(prodIds)&&null!=prodIds){
	//			pricingTarget.setProdOfferId(Integer.parseInt(prodIds));
	//			getTreeParams = "prodIds:"+prodIds;
	//		}
			
			PricingPlan pricingPlan=new PricingPlan();
			pricingPlan.setPricingInfoId(Integer.parseInt(pricingInfoId));
			pricingPlan.setApplicType(applicTyp);
			pricingPlan = priceServ.queryPricingPlan(pricingPlan).get(0);
			 
			pricingPlan.setFormatEffDate("".equals(StringUtil.valueOf(pricingPlan.getEffDate()))?null:DateUtil.convDateToString(pricingPlan.getEffDate(), "yyyy-MM-dd")) ;
			pricingPlan.setFormatExpDate("".equals(StringUtil.valueOf(pricingPlan.getEffDate()))?null:DateUtil.convDateToString(pricingPlan.getExpDate(), "yyyy-MM-dd")) ;
			
			PricngPlanAttr pricngPlanAttr = new PricngPlanAttr();
			pricngPlanAttr.setPricingInfoId(new BigDecimal(pricingPlan.getPricingInfoId()));
			List<PricngPlanAttr> list = attrSpecService.queryPricngPlanAttr(pricngPlanAttr);
			if(list.size()>0){
				PricngPlanAttr newP = null;
				List pricePlanAttrList=new ArrayList();
				for(PricngPlanAttr p :list){
					newP = new PricngPlanAttr();
					newP.setAttrSpecId(p.getAttrSpecId());
					newP.setDefaultValue(p.getDefaultValue());
					newP.setDefaultValueName(null==p.getDefaultValueName()?"":p.getDefaultValueName());
					newP.setPageInType(p.getPageInType());
					newP.setAttrSpecName(p.getAttrSpecName());
					newP.setChooseURL(p.getChooseURL());
					newP.setAttrSpecDesc(p.getAttrSpecDesc());
					pricePlanAttrList.add(newP);
				}
				mv.addObject("pricePlanAttrList",pricePlanAttrList);
			}
			PricePlanLifeCycle pricePlanLifeCycle=new PricePlanLifeCycle();
			pricePlanLifeCycle.setPricePlanId(Long.valueOf(pricingPlan.getPricingInfoId()));
			pricePlanLifeCycle = attrSpecService.queryPricePlanLifeCycle(pricePlanLifeCycle);
			pricePlanLifeCycle.setfFixedExpireDate("".equals(StringUtil.valueOf(pricePlanLifeCycle.getFixedExpireDate()))?null:DateUtil.convDateToString(pricePlanLifeCycle.getFixedExpireDate(), "yyyy-MM-dd"));
			
			mv.addObject("pricingPlan",pricingPlan);
			mv.addObject("pricePlanLifeCycle",pricePlanLifeCycle);
			
			mv.addObject("prodOfferId",prodIds);
			mv.addObject("pricingInfoId",pricingInfoId);
			mv.addObject("pricingTargetId",pricingTargetId);
			
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
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			int rows =999;
			int pageNum =1;
			int startRow = (pageNum - 1) * rows;
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			paramMap.put("priPricingInfoId", pricingInfoId);
			List<ComponentPrice> componentPriceList = pricePlanDetailServ.queryComponentPrice(paramMap);
			mv.addObject("componentPriceList",componentPriceList);

			String formNum = getRequestValue(request, "formNum");
			mv.addObject("formNum",formNum);
			
			mv.setViewName("../provider/sysPricePlanAdd.jsp");
		}else{
			PricingPlan pricingPlan = new PricingPlan();
			mv.addObject("pricingPlan",pricingPlan);
			
//			PricingTarget pricingTarget = new PricingTarget();
//			mv.addObject("pricingTarget",pricingTarget);
			
			CharSpec charSpec = new CharSpec();
			charSpec.setCharSpecType(EAAPConstants.CHAR_SPEC_TYPE_PRICE);
			charSpec.setStatusCd(EAAPConstants.COMM_STATE_ONLINE);
			List<CharSpec> charSpeclist = iPardSpecServ.qryCharSpec(charSpec);
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
			mv.addObject("prodOfferId",prodIds);
			mv.setViewName("../provider/sysPricePlanAdd.jsp");
		}
		return mv;
	}


	@RequestMapping(value = "/provider/savrOrUpdatePricePlan", method = RequestMethod.POST)
	@ResponseBody
	public String savrOrUpdatePricePlan(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		PricingPlan pricingPlan = new PricingPlan();
		PricePlanLifeCycle pricePlanLifeCycle = new PricePlanLifeCycle();
		OfferProdRelPricing offerProdRelPricing = new OfferProdRelPricing();
		PricngPlanAttr pricngPlanAttr = new PricngPlanAttr();
		PricingTarget pricingTarget = new PricingTarget();
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		String prodOfferId = getRequestValue(request, "prodOfferId");
		String targetId = getRequestValue(request, "pricingTargetId");
		pricingTarget.setProdOfferId(!"".equals(prodOfferId)?BigDecimal.valueOf(Long.valueOf(prodOfferId)):null);
		pricingTarget.setPricingTargetId(!"".equals(targetId)?Integer.valueOf(targetId):null);
		
		String priceId = getRequestValue(request, "pricingInfoId");
		String applicType = getRequestValue(request, "applicType");
		String pricingName = getRequestValue(request, "pricingName");
		String licenseNbr = getRequestValue(request, "licenseNbr");		//?
		String billingPriorit = getRequestValue(request, "billingPriority");
		String desc = getRequestValue(request, "pricingDesc");					//?
		String feffd = getRequestValue(request, "formatEffDate");			//?
		String fexpd = getRequestValue(request, "formatExpDate");			//?
		pricingPlan.setPricingInfoId(!"".equals(priceId)?Integer.valueOf(priceId):null);
		pricingPlan.setApplicType(applicType);
		pricingPlan.setPricingName(pricingName);
		pricingPlan.setLicenseNbr(!"".equals(licenseNbr)?Integer.valueOf(licenseNbr):null);
		pricingPlan.setBillingPriority(!"".equals(billingPriorit)?Integer.valueOf(billingPriorit):null);
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
			if(!"".equals(billingPriorit)&&billingPriorit!=null){
				pricingPlan.setBillingPriority(Integer.parseInt(billingPriorit));
			}
			if(!"".equals(applicType)&&applicType!=null){
				pricingPlan.setApplicType(applicType);
			}else{
				pricingPlan.setApplicType("1");
			}
			pricingPlan.setPricingDesc(desc);
			
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
				for(String id : relIds.split(",")){
					offerProdRelPricing.setOfferProdRelaId(Integer.parseInt(id));
					offerProdRelPricing.setPricingInfoId(Integer.parseInt(priceId));
					offerProdRelPricing.setPricingTargetId(Integer.parseInt(targetId));
					offerProdRelPricing.setStatusCd("10");
					priceServ.addProdRelPricing(offerProdRelPricing);
				}
				
				pricePlanLifeCycle.setPricePlanId(Long.valueOf(priceId));
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
						if("".equals(defaultValName)){
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
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getCause(),null));
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getCause(),null));
		}
		return json.toString();
	}

	
	
	@RequestMapping(value = "/provider/toBasicTariff")
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
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.devmgr.finish");
		
		//按钮
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
		mv.addObject("baseItemTypeList",mainDataServ.getMainInfo(EAAPConstants.BASE_ITEM_TYPE));//Tax Type SELECT
		mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
		mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));//Tax Type SELECT
		String state = getRequestValue(request, "state");
		String readonly = getRequestValue(request, "readonly");
		 
			mv.addObject("readonly",readonly);
			mv.addObject("state",state);
		 
		String actionType = getRequestValue(request, "actionType");
		mv.addObject("actionType",actionType);
		if("update".equals(actionType)||"detail".equals(actionType)){
			String priceInfoId = getRequestValue(request, "priceInfoId");
			String priceId = getRequestValue(request, "priceId");
			Map<String,Object> map = new HashMap<String,Object>();
			ComponentPrice componentPrice=new ComponentPrice();
			componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
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
			BaseTariffItemRel baseTariffItemRel=new BaseTariffItemRel();
			baseTariffItemRel.setPriceId(Integer.parseInt(priceId));
			List<BaseTariffItemRel> baseTariffItemRelist = pricePlanDetailServ.getBaseTariffItemRel(baseTariffItemRel);
			StringBuilder sb1 = new StringBuilder();

			for(BaseTariffItemRel b : baseTariffItemRelist){
				sb1.append(b.getItemId()).append("/");
			}
			if(sb1.toString().length()>0){
				String itemIds = sb1.toString().substring(0,sb1.toString().length()-1);
				mv.addObject("itemIds",itemIds);//
			}
			RatingCurverDetail ratingCurverDetail=new RatingCurverDetail();
			ratingCurverDetail.setPriceId(Integer.parseInt(priceId));
			List ratingCurverDetailList = pricePlanDetailServ.queryRatingCurveDetail(ratingCurverDetail);
			mv.addObject("ratingCurverDetailList",ratingCurverDetailList);//
			mv.setViewName("../provider/sysBasicTariffAdd.jsp");
		}else{
			ComponentPrice componentPrice=new ComponentPrice();
			//map.put("start", DateUtil.convDateToString(new Date(), "yyyy-MM-dd"));
			String  start = DateUtil.convDateToString(new Date(), "yyyy-MM-dd");
			String end ="2037-12-31";
			componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?start:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
			componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?end:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
			mv.addObject("componentPrice",componentPrice);
			mv.setViewName("../provider/sysBasicTariffAdd.jsp");
		}
		return mv;
	}
	//Recurring Charge
	@RequestMapping(value = "/provider/toRecurringCharge")
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
		//按钮
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("currencyUnitTypeList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));//Tax Type SELECT
		mv.addObject("taxTypeList",mainDataServ.getMainInfo(EAAPConstants.TAXINCLUDED));//Tax Type SELECT
		mv.addObject("itemTypeList",mainDataServ.getMainInfo(EAAPConstants.PRICEITEM_ITEMTYPE));//Item Type SELECT
		String actionType = getRequestValue(request, "actionType");
		mv.addObject("actionType",actionType);
		if("update".equals(actionType)||"detail".equals(actionType)){
			String priceInfoId = getRequestValue(request, "priceInfoId");
			String priceId = getRequestValue(request, "priceId");
			Map<String,Object> map = new HashMap<String,Object>();
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
			pecurringFee =pricePlanDetailServ.queryRecurringFee(pecurringFee);
				
			mv.addObject("componentPrice",componentPrice);//
			mv.addObject("pecurringFee",pecurringFee);//
			mv.setViewName("../pardOfferPricePlan/recurringChargeDetail.jsp");
		}else{
			mv.setViewName("../provider/sysRecurringChargeAdd.jsp");
		}
		return mv;
	}
//	//Rating Discount
//	@RequestMapping(value = "/toRatingDiscount")
//	public ModelAndView toPricePlanRatingDiscount(final HttpServletRequest request,
//			final HttpServletResponse response) {}
//	//One-time Charge
//	@RequestMapping(value = "/toOnetimeCharge")
//	public ModelAndView toPricePlanOnetimeCharge(final HttpServletRequest request,
//			final HttpServletResponse response) {}
//	//Billing Discount
//	@RequestMapping(value = "/toBillingDiscount")
//	public ModelAndView toPricePlanBillingDiscount(final HttpServletRequest request,
//			final HttpServletResponse response) {}
//	//Partner Product - Price Plan(Basic Charge) -Charging Unit
//	@RequestMapping(value = "/getRatingUnitType", method = RequestMethod.POST)
//	@ResponseBody
//	public String getPricePlanRatingUnitType(final HttpServletRequest request,
//			final HttpServletResponse response) throws Exception {}
	
	// Partner Product - Price Plan(Basic Charge) -Pricing Subject
	@RequestMapping(value = "/getPricingSubjectTree")
	@ResponseBody
	public String pricePlanPricingSubjectTree(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pid = getRequestValue(request, "baseItemType");
		String itemIds =getRequestValue(request, "itemIds");
		Map itemMap = new HashMap();
		if(pid!=null && !"".equals(pid)){
			itemMap.put("baseItemType", pid);
		}
		itemMap.put("itemType", "1");
		itemMap.put("pro_mysql", 0);
		itemMap.put("page_record", 999);
		itemMap.put("parentItemId", 0);
		JSONObject json = new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		json.put(RETURN_DESC,getPricePlanPricingSubjectTree(itemMap,itemIds));
		return json.toString();
	}
	public JSONArray getPricePlanPricingSubjectTree(Map itemMap,String itemIds) {
		List<PriceItem> priceItemList = priceServ.queryPriceItemList(itemMap);
		JSONArray childrenList=new JSONArray();
		if(priceItemList.size()>0){
			JSONObject mapL =null;
			JSONObject stateMap = null;
			JSONArray reList=null;
			for(PriceItem p : priceItemList){
				mapL = new JSONObject();
				mapL.put("id",p.getItemId());
				mapL.put("text", p.getItemName());
				stateMap = new JSONObject();
				stateMap.put("selected", false);
				stateMap.put("opened", true);
				if(!"".equals(itemIds)&&null!=itemIds&&itemIds.contains(p.getItemId()+"")){
					stateMap.put("selected", true);
				}
				mapL.put("state", stateMap);
				itemMap.put("parentItemId", p.getItemId());
				reList=getPricePlanPricingSubjectTree(itemMap,itemIds);
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

	public Map<String,String> getMainInfo(String mdtSign){
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
		
		//获取请求次数
	    String draw = "0";
	    draw = getRequestValue(request, "draw");
	    //数据起始位置
	    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
	    //数据长度
	    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
	    //过滤后记录数
	    String recordsFiltered = "";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//总记录数
	    int recordsTotal = priceServ.countPriceItemList(paramMap);
		paramMap.put("pro_mysql", startRow);
		paramMap.put("page_record", rows);
		String itemIdOrName = getRequestValue(request, "itemIdOrName");
		String itemType = getRequestValue(request, "itemType");
		String queryParamsData = getRequestValue(request, "queryParamsData");
//		queryParamsData = null==queryParamsData?null==prar.get("itemTypes")?itemTypes:prar.get("itemTypes").toString():queryParamsData;

		if(!"".equals(itemIdOrName) && null!=itemIdOrName){
			paramMap.put("itemIdOrName", itemIdOrName);
		}
		if(!"".equals(itemType) && null!=itemType){
			paramMap.put("itemType", itemType);
			
		}else if(null!=queryParamsData&&!"".equals(queryParamsData)){
			paramMap.put("itemTypes", queryParamsData.split(","));
		}
		
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
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal",recordsTotal);
		json.put("recordsFiltered", priceServ.countPriceItemList(paramMap));
		json.put("data",dataList.toJSONString());
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

		List<TimeSegDef> timeSegDefList = attrSpecService.queryTimeSeqDef(paramMap);
		JSONArray dataList=new JSONArray();
		JSONObject subData = null;
		for(TimeSegDef timeSegDef:timeSegDefList){
			subData=new JSONObject();
			subData.put("INDEX","<input type='checkbox' value='"+timeSegDef.getSegId()+"' class='checkboxes' name='attrSpecId' />");
			subData.put("SEG_ID",timeSegDef.getSegId());
			subData.put("SEG_NAME",timeSegDef.getSegName());
			subData.put("DATE_MODE",null==timeSegDef.getDateMode()?"":timeSegDef.getDateMode());
			subData.put("TIME_MODE",null==timeSegDef.getTimeMode()?"":timeSegDef.getTimeMode());
			subData.put("DESCRIPTION",null==timeSegDef.getDescription()?"":timeSegDef.getDescription());
			dataList.add(subData);
		}
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal",timeSegDefList.size());
		json.put("recordsFiltered", timeSegDefList.size());
		json.put("data",dataList.toJSONString());
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
	
	@RequestMapping(value = "/provider/delete", method = RequestMethod.POST)
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
	@RequestMapping(value = "/detailDelete", method = RequestMethod.POST)
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
	@RequestMapping(value = "/provider/saveOrUpdateBasicTariff", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdateBasicTariff(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
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
	@ResponseBody
	public String saveOrUpdateRecurringFee(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			pricePlanDetailServ.saveOrUpdateRecurringFee(request,response,actionKey,i18nLoader);
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
	@ResponseBody
	public String saveOrUpdateRatingDiscount(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			pricePlanDetailServ.saveOrUpdateRatingDiscount(request,response,actionKey);
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
	@ResponseBody
	public String saveOrUpdateOneTimeFee(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			pricePlanDetailServ.saveOrUpdateOneTimeFee(request,response,actionKey,i18nLoader);
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
	@ResponseBody
	public String saveOrUpdateBillingDiscount(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			pricePlanDetailServ.saveOrUpdateBillingDiscount(request,response,actionKey);
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
		
		mv.setViewName("../common/map.jsp");
		return mv;
	}
	@RequestMapping(value = "/getZoneSpecList", method = RequestMethod.GET)
	@ResponseBody
	public String getZoneSpecList(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception{
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
		String idOrName = getRequestValue(request, "idOrName");
		String radius = getRequestValue(request, "radius");
		
		if(!"".equals(idOrName)&&null!=idOrName){
			paramMap.put("segNameOrId", idOrName);
		}
		if(!"".equals(radius)&&null!=radius){
			paramMap.put("radius", radius);
		}
		
		List<RsSysCellDef> rsSysCellDefSegList = attrSpecService.queryRsSysCellDef(paramMap);
		JSONArray dataList=new JSONArray();
		JSONObject subData = null;
		for( RsSysCellDef rsSysCellDefSeg:rsSysCellDefSegList){
			subData=new JSONObject();
			subData.put("INDEX","<input type='checkbox' value='"+rsSysCellDefSeg.getOpId()+"' class='checkboxes' name='attrSpecId' />");
			subData.put("OP_ID",rsSysCellDefSeg.getOpId());
			subData.put("OP_NAME",rsSysCellDefSeg.getOpName());
			subData.put("OP_DESC",null==rsSysCellDefSeg.getDescription()?"":rsSysCellDefSeg.getDescription());
			subData.put("LONGITUDE",null==rsSysCellDefSeg.getLongitude()?"":rsSysCellDefSeg.getLongitude());
			subData.put("LATITUDE",null==rsSysCellDefSeg.getLatitude()?"":rsSysCellDefSeg.getLatitude());
			
			dataList.add(subData);
		}
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal",rsSysCellDefSegList.size());
		json.put("recordsFiltered", rsSysCellDefSegList.size());
		json.put("data",dataList.toJSONString());
		return json.toString();
		
	}
	

}
