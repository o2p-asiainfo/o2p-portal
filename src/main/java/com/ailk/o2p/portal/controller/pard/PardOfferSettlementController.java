package com.ailk.o2p.portal.controller.pard;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.bo.SettleCycleDef;
import com.ailk.eaap.op2.bo.SettleRule;
import com.ailk.eaap.op2.bo.SettleRuleAggregation;
import com.ailk.eaap.op2.bo.SettleRuleCondition;
import com.ailk.eaap.op2.bo.SettleRuleOTC;
import com.ailk.eaap.op2.bo.SettleRuleRC;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.pardoffer.PardOfferServ;
import com.ailk.o2p.portal.service.pardproduct.PardProdServ;
import com.ailk.o2p.portal.service.settlement.ISettlementServ;
import com.ailk.o2p.portal.utils.SavePermission;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

@Controller
@Transactional
@RequestMapping(value = "/pardOfferSettlement")
public class PardOfferSettlementController extends BaseController {

	private static Logger log = Logger.getLog(PardOfferSettlementController.class);
	@Autowired
	private ISettlementServ settleServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private PardOfferServ pardOfferServ;
	@Autowired
	private PardProdServ pardProdService;

	//chooseSettleType settlement rule list
	@RequestMapping(value = "/getRuleList", method = RequestMethod.POST)
	@ResponseBody
	public String  getSettlementRuleList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			Org orgBean = getOrg(request);
			String busiCode = getRequestValue(request, "busiCode");
			HashMap<String,Object> itemMap = new HashMap<String,Object>();
			itemMap.put("partnerCode", orgBean.getOrgId());
			itemMap.put("busiCode", busiCode);
			itemMap.put("pro_mysql", 0);
			itemMap.put("page_record", 99);
			itemMap.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			List<Map<String, String>> basicTreeList = settleServ.getBasicTree(itemMap);
			JSONArray retList=new JSONArray();

			for(Map<String, String> map:basicTreeList){
				JSONObject retMap = new JSONObject();
				retMap.put("key", map.get("LISTKEY"));
				retMap.put("value", map.get("LISTVALUE"));
				retList.add(retMap);
			}
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,retList.toString());
			
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/toSettlement")
	public ModelAndView toPardOffertSettlement(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
	
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
		messages.add("eaap.op2.portal.price.baseInfo");//Basic Info
		//运营商名字
		messages.add("eaap.op2.portal.settlement.OperatorName");//Operator Name
		//基础信息
		messages.add("eaap.op2.portal.settlement.SBName");//Name
		messages.add("eaap.op2.portal.settlement.period.detail");//Product Cycle label
		messages.add("eaap.op2.portal.settlement.period");//Product Cycle 
		messages.add("eaap.op2.portal.settlement.billEndDate.detail");//Start Time label
		messages.add("eaap.op2.portal.settlement.billEndDate");//Start Time 
		messages.add("eaap.op2.portal.settlement.cycleType.detail");//Cycle Type label
		messages.add("eaap.op2.portal.settlement.cycleType");//Start Time 
		messages.add("eaap.op2.portal.settlement.busiType");//Price Plan Attribute
		messages.add("eaap.op2.portal.settlement.busiType.detail");//Busi Type 	
		messages.add("eaap.op2.portal.settlement.effAndExpDate");//Period of Validity
		messages.add("eaap.op2.portal.settlement.description");//Description
		// Settlement Information
		messages.add("eaap.op2.portal.settlement.feeInfo");// Settlement Information
		//审核状态
		//按钮
		messages.add("eaap.op2.portal.doc.submit");//SELECT PRODUCT
		messages.add("eaap.op2.portal.pardOffer.noData");
		messages.add("eaap.op2.portal.pardOffer.noSettlementInfo");

		messages.add("eaap.op2.portal.attrSpec.operationTips");
		messages.add("eaap.op2.portal.price.basic.endVal");
		messages.add("eaap.op2.portal.price.basic.greater");
		messages.add("eaap.op2.portal.price.basic.equal1");		
		messages.add("eaap.op2.portal.price.basic.equal2");
		messages.add("eaap.op2.portal.price.basic.notNullBase");		
		messages.add("eaap.op2.portal.price.basic.notNullRateVal");		
		messages.add("eaap.op2.portal.price.priceType1");		
		messages.add("eaap.op2.portal.price.basic.endWith-1");
		
		ModelAndView mv = new ModelAndView();
		try{
			addTranslateMessage(mv, messages);
			mv.addObject("settleCycleTypeList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_CYCLE_TYPE));//Cycle Type SELECT
			mv.addObject("settleDirectoryTypeList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_DIRECTORY_TYPE));//Busi Type SELECT
			mv.addObject("settleRuleTypeList", mainDataServ.getMainInfo(EAAPConstants.SETTLE_RULE_TYPE));//SETTLE_RULE_TYPE SELECT
			Org orgBean = getOrg(request);
			String actionType = getRequestValue(request, "actionType");
			String settleType = getRequestValue(request, "settleType");
			String formNum = getRequestValue(request, "formNum");
			String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
			String defaultName = getRequestValue(request, "defaultName");
			defaultName=URLDecoder.decode(defaultName,"utf8"); 
			defaultName=defaultName.replaceAll("%20"," ");
			String operatorOrgId=getRequestValue(request,"operatorOrgId");
			
			mv.addObject("actionType",actionType);
			mv.addObject("settleType",settleType);
			mv.addObject("formNum",formNum);
			mv.addObject("operatorOrgId",operatorOrgId);
			
			SettleSpBusiDef settleSpBusiDef=new SettleSpBusiDef();
			String servCode = getRequestValue(request, "settleSpBusiDef.servCode");
			String busiId = getRequestValue(request, "settleSpBusiDef.busiId");
			settleSpBusiDef.setServCode(servCode);
			if(null!=busiId&&!"".equals(busiId)){
				settleSpBusiDef.setBusiId(new BigDecimal(busiId));
			}
			
			SettleCycleDef settleCycleDef=new SettleCycleDef();
			settleCycleDef.setBusiCode(busiCode);
			settleCycleDef.setPartnerCode(orgBean.getOrgId().toString());
			settleCycleDef.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleCycleDef = settleServ.querySettleCycleDef(settleCycleDef);
			if(null!=settleCycleDef){
				settleCycleDef.setBillEndDate(this.changeDateFormat(settleCycleDef.getBillEndDate(), "yyyyMMdd", "yyyy-MM-dd"));
			}else{
				settleCycleDef = new SettleCycleDef();
				settleCycleDef.setCycleCount(1);
				settleCycleDef.setSyncFlag(1);
				settleCycleDef.setBusiCode(busiCode);
				
		        Date serDate = new Date();
		        java.text.DateFormat formatDT = new java.text.SimpleDateFormat("yyyy-MM-dd");
		        String sDate = formatDT.format(serDate);
				settleCycleDef.setBillEndDate(sDate);
			}
			
			String  o2pCloudFlag=WebPropertyUtil.getPropertyValue("o2p_web_domin");
			String operatorName=null;
			
			if("update".equals(actionType)||"detail".equals(actionType)||("add".equals(actionType)&&"2".equals(settleType))){
//				String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
				settleSpBusiDef.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
				settleSpBusiDef.setPartnerCode(orgBean.getOrgId().toString());
				settleSpBusiDef.setBusiCode(settleCycleDef.getBusiCode());
				settleSpBusiDef = settleServ.querySettleSpBusiDef(settleSpBusiDef).get(0);
				settleSpBusiDef.setBusiName(settleSpBusiDef.getBusiName().replaceAll("%20"," "));
				settleSpBusiDef.setServCode(servCode);
				if("2".equals(settleType)){
					settleSpBusiDef.setBusiId(null);
					/**保存复制的结算信息**/
					BigDecimal settleSpBusiDefId = settleServ.addSettleSpBusiDef(settleSpBusiDef);
					settleSpBusiDef.setBusiId(settleSpBusiDefId);
					settleServ.copySettleRuleList(settleSpBusiDef.getPartnerCode(),settleSpBusiDef.getBusiCode(),settleSpBusiDef.getServCode(),servCode);
				}
				settleSpBusiDef.setForEffDate("".equals(StringUtil.valueOf(settleSpBusiDef.getEffDate()))?null:DateUtil.convDateToString(settleSpBusiDef.getEffDate(), "yyyy-MM-dd")) ;
				settleSpBusiDef.setForExpDate("".equals(StringUtil.valueOf(settleSpBusiDef.getEffDate()))?null:DateUtil.convDateToString(settleSpBusiDef.getExpDate(), "yyyy-MM-dd")) ;
				mv.addObject("settleCycleDef",settleCycleDef);//
				mv.addObject("settleSpBusiDef",settleSpBusiDef);//
				//rule list
				SettleRule settleRule = new SettleRule();
				settleRule.setPartnerCode(orgBean.getOrgId().toString());
				settleRule.setBusiCode(busiCode);
				settleRule.setServCode(servCode);
				settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
				List<SettleRule> settleRulelist = settleServ.querySettleRule(settleRule);
				List<SettleRule> settleRulelistNew=new ArrayList<SettleRule>();
				for(SettleRule settleRuleTransfer:settleRulelist){
					settleRuleTransfer.setRuleName(settleRuleTransfer.getRuleName().replaceAll("%20"," "));
					settleRulelistNew.add(settleRuleTransfer);
				}
				mv.addObject("settleRulelist",settleRulelistNew);//
				Map settleRuleMap=new HashMap();
				for(SettleRule sr:settleRulelist){
					settleRuleMap.put(sr.getRuleType()+"_", 1);
				}
				mv.addObject("settleRuleMap",settleRuleMap);//
		            
			}else{
				settleSpBusiDef.setSyncFlag(1);
				mv.addObject("settleCycleDef",settleCycleDef);//
				defaultName.replaceAll(" ","%20");
				settleSpBusiDef.setBusiName(defaultName+"_"+getI18nMessage("eaap.op2.portal.pardOffer.account")+"_"+formNum);
				mv.addObject("settleSpBusiDef",settleSpBusiDef);//
				mv.addObject("defaultName",defaultName);//
			}
			mv.addObject("o2pCloudFlag",o2pCloudFlag);
			mv.addObject("operatorName", operatorName);
			mv.setViewName("../pardOfferSettlement/pardOfferSettlement.jsp");
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	private String changeDateFormat(String dateStr,String format1,String format2) throws ParseException{
		if(StringUtil.isBlank(dateStr)){
			return "";
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		return sdf2.format(sdf1.parse(dateStr));
	}
	@RequestMapping(value = "/toRule")
	public ModelAndView toRule(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String ruleType = getRequestValue(request, "ruleType");
		if("1".equals(ruleType)){
			return toSettlementRecurringRule(request,response);
		}else if("2".equals(ruleType)){
			return toSettlementOneTimeCharge(request,response);
		}else if("4".equals(ruleType)){
			return toSettlementAggregationRule(request,response);
		}
		return null;
	}
	//Recurring Rule
	@RequestMapping(value = "/toRecurringRule")
	public ModelAndView toSettlementRecurringRule(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//基础信息
			messages.add("eaap.op2.portal.settlement.ruleName");//Rule Name 	
			messages.add("eaap.op2.portal.settlement.orderMethod.detail");//Order Statistics Method label
			messages.add("eaap.op2.portal.settlement.orderMethod");//Order Statistics Method
			messages.add("eaap.op2.portal.settlement.orderUserMethod.detail");//Order Use Method label
			messages.add("eaap.op2.portal.settlement.orderUserMethod");//Order Use Method
			messages.add("eaap.op2.portal.settlement.day");//Day
			messages.add("eaap.op2.portal.settlement.reduce.detail");//Reduce label
			messages.add("eaap.op2.portal.settlement.reduce");//Reduce
			messages.add("eaap.op2.portal.settlement.payDir.detail");//Pay for direction label
			messages.add("eaap.op2.portal.settlement.payDir");//Pay for direction
			messages.add("eaap.op2.portal.settlement.priceType");//Money Unit
			messages.add("eaap.op2.portal.settlement.RCCharge.detail");//Charge label
			messages.add("eaap.op2.portal.settlement.rcCharge");//Charge
			messages.add("eaap.op2.portal.settlement.orderArrange");//Order Arrange
			messages.add("eaap.op2.portal.settlement.unitPrice");//Charge
			messages.add("eaap.op2.portal.settlement.baseCharge");//Base Charge
			messages.add("eaap.op2.portal.pardSpec.operation");
			messages.add("eaap.op2.portal.price.to1");// 
			messages.add("eaap.op2.portal.manager.auth.add");// 
			messages.add("eaap.op2.portal.settlement.effAndExpDate");//Period of Validity
			messages.add("eaap.op2.portal.price.to");//Period of Validity to
			messages.add("eaap.op2.portal.price.priceDesc");//Description
			messages.add("eaap.op2.portal.pardProd.feeCfg.attr");//Product Attribute
			//button
			messages.add("eaap.op2.portal.doc.submit");//ok
			messages.add("eaap.op2.portal.doc.cancel");//cancel
			//按钮
			
			addTranslateMessage(mv, messages);
			
			Org orgBean = getOrg(request);
			String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
			String servCode = getRequestValue(request, "settleSpBusiDef.servCode");
			
			SettleCycleDef settleCycleDef = new SettleCycleDef();
			settleCycleDef.setPartnerCode(orgBean.getOrgId().toString());
			settleCycleDef.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleCycleDef.setBusiCode(busiCode);
			settleCycleDef = settleServ.querySettleCycleDef(settleCycleDef);
			
			mv.addObject("settleAccumTypeList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_ACCUM_TYPE));//Order Statistics Method SELECT
			mv.addObject("monthDayList",getMonthDayListForSelect());//Day SELECT
			mv.addObject("settleChargeDirList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_CHARGE_DIR));//Pay for direction
			mv.addObject("settleMoneyUnitList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));//Money Unit
			
			List settleAccumDateTypeList = mainDataServ.getMainInfo(EAAPConstants.SETTLE_ACCUM_DATE_TYPE);//Order Use Method
			if(null!=settleCycleDef&&3==settleCycleDef.getCycleType()){
				mv.addObject("settleAccumDateTypeList",settleAccumDateTypeList);
			}else{
				for(Object mainData:settleAccumDateTypeList){
					if(mainData instanceof MainData){
						if(((MainData) mainData).getCepCode().equals(EAAPConstants.SETTLE_ACCUM_DATE_TYPE_MONTH)){
							settleAccumDateTypeList.remove(mainData);
							break;
						}
					}
				}
				mv.addObject("settleAccumDateTypeList",settleAccumDateTypeList);//Order Use Method
			}
			
			ProductAttr productAttr=new ProductAttr();
			productAttr.setProductId(new BigDecimal(busiCode));
			List productAttrInfoList=pardProdService.queryProductAttrInfo(productAttr);
			mv.addObject("productAttrInfoList",productAttrInfoList);//Order Use Method
			
			String actionType = getRequestValue(request, "actionType");
			String formNum = getRequestValue(request, "formNum");
			String ruleType = getRequestValue(request, "ruleType");
			String defaultName = getRequestValue(request, "defaultName");
			String operatorOrgId=getRequestValue(request, "operatorOrgId");
			mv.addObject("formNum",formNum);
			mv.addObject("ruleType",ruleType);
			mv.addObject("actionType",actionType);
			mv.addObject("operatorOrgId",operatorOrgId);
			SettleRule settleRule=new SettleRule();
			SettleRuleRC settleRuleRC=new SettleRuleRC();
			if("update".equals(actionType)||"detail".equals(actionType)){
				String ruleId = getRequestValue(request, "ruleId");
				SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
				settleRule.setRuleId(new BigDecimal(ruleId));
				settleRule = settleServ.querySettleRule(settleRule).get(0);
				settleRule.setForEffDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getEffDate(), "yyyy-MM-dd")) ;
				settleRule.setForExpDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getExpDate(), "yyyy-MM-dd")) ;
				settleRule.setRuleName(settleRule.getRuleName().replaceAll("%20"," "));
				
				settleRuleRC.setRuleId(settleRule.getRuleId());
				settleRuleRC = settleServ.querySettleRuleRC(settleRuleRC).get(0);
				
				settleRuleCondition.setRuleDetailId(settleRuleRC.getRuleDetailId());
				//settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
				List ruleConditionList = settleServ.querySettleRuleCondition(settleRuleCondition);
				
				mv.addObject("ruleConditionList",ruleConditionList);//
				mv.addObject("settleRule",settleRule);//
				mv.addObject("settleRuleRC",settleRuleRC);//
			}else{
				
				settleRule.setServCode(servCode);
				settleRule.setBusiCode(busiCode);
				settleRuleRC.setReduceRes(0);
				settleRule.setRuleName(defaultName+"_"+getI18nMessage("eaap.op2.portal.settlement.RCRule")+"_"+formNum);
				mv.addObject("settleRule",settleRule);//
				mv.addObject("settleRuleRC",settleRuleRC);//
			}
			mv.setViewName("../pardOfferSettlement/recurringRule.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	//One Time Charge
	@RequestMapping(value = "/toOneTimeCharge")
	public ModelAndView toSettlementOneTimeCharge(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//基础信息
			messages.add("eaap.op2.portal.settlement.ruleName");//Rule Name 	
			messages.add("eaap.op2.portal.settlement.payDir.detail");//Pay for direction label
			messages.add("eaap.op2.portal.settlement.payDir");//Pay for direction
			messages.add("eaap.op2.portal.settlement.OtcCharge");//Charge
			messages.add("eaap.op2.portal.settlement.effAndExpDate");//Period of Validity
			messages.add("eaap.op2.portal.price.to");//Period of Validity to
			messages.add("eaap.op2.portal.price.priceDesc");//Description
			messages.add("eaap.op2.portal.pardProd.feeCfg.attr");//Product Attribute
			//button
			messages.add("eaap.op2.portal.doc.submit");//ok
			messages.add("eaap.op2.portal.doc.cancel");//cancel
			//按钮
			
			addTranslateMessage(mv, messages);
			
			mv.addObject("settleChargeDirList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_CHARGE_DIR));//Pay for direction
			mv.addObject("settleMoneyUnitList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));//Money Unit 	
			String actionType = getRequestValue(request, "actionType");
			String formNum = getRequestValue(request, "formNum");
			String ruleType = getRequestValue(request, "ruleType");
			String defaultName = getRequestValue(request, "defaultName");
			String operatorOrgId=getRequestValue(request, "operatorOrgId");
			mv.addObject("formNum",formNum);
			mv.addObject("ruleType",ruleType);
			mv.addObject("actionType",actionType);
			mv.addObject("operatorOrgId",operatorOrgId);
			String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
			String servCode = getRequestValue(request, "settleSpBusiDef.servCode");
			ProductAttr productAttr=new ProductAttr();
			productAttr.setProductId(new BigDecimal(busiCode));
			List productAttrInfoList=pardProdService.queryProductAttrInfo(productAttr);
			mv.addObject("productAttrInfoList",productAttrInfoList);//Order Use Method
			
			SettleRule settleRule=new SettleRule();
			if("update".equals(actionType)||"detail".equals(actionType)){
				String ruleId = getRequestValue(request, "ruleId");
				SettleRuleOTC settleRuleOTC=new SettleRuleOTC();
				SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
				settleRule.setRuleId(new BigDecimal(ruleId));
				settleRule = settleServ.querySettleRule(settleRule).get(0);
				settleRule.setForEffDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getEffDate(), "yyyy-MM-dd")) ;
				settleRule.setForExpDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getExpDate(), "yyyy-MM-dd")) ;
				settleRule.setRuleName(settleRule.getRuleName().replaceAll("%20"," "));
				
				settleRuleOTC.setRuleId(settleRule.getRuleId());
				settleRuleOTC = settleServ.querySettleRuleOTC(settleRuleOTC).get(0);
				
				settleRuleCondition.setRuleDetailId(settleRuleOTC.getRuleDetailId());
				//settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
				settleRuleCondition = settleServ.querySettleRuleCondition(settleRuleCondition).get(0);
				
				mv.addObject("settleRuleCondition",settleRuleCondition);//
				mv.addObject("settleRule",settleRule);//
				mv.addObject("settleRuleOTC",settleRuleOTC);//
			}else{
				settleRule.setRuleName(defaultName+"_"+getI18nMessage("eaap.op2.portal.settlement.OTCRule")+"_"+formNum);
				settleRule.setServCode(servCode);
				settleRule.setBusiCode(busiCode);
				mv.addObject("settleRule",settleRule);//
				
				SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
				settleRuleCondition.setBaseValue("0");
				mv.addObject("settleRuleCondition",settleRuleCondition);//
			}
			mv.setViewName("../pardOfferSettlement/oneTimeCharge.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	//Aggregation Rule
	@RequestMapping(value = "/toAggregationRule")
	@SuppressWarnings("all")
	public ModelAndView toSettlementAggregationRule(final HttpServletRequest request,
			final HttpServletResponse response)  throws Exception {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//基础信息
			messages.add("eaap.op2.portal.settlement.ruleName");//Rule Name 	
			messages.add("eaap.op2.portal.settlement.aggregationItem.detail");//Order Statistics Method label
			messages.add("eaap.op2.portal.settlement.aggregationItem");//Order Statistics Method
			messages.add("eaap.op2.portal.settlement.ruleDetailInfo");//Order Use Method label
			messages.add("eaap.op2.portal.settlement.payDir.detail");//Order Use Method
			messages.add("eaap.op2.portal.settlement.payDir");//Day
			messages.add("eaap.op2.portal.settlement.priceType");//Reduce label
			messages.add("eaap.op2.portal.settlement.chargeCondition.detail");//Reduce
			messages.add("eaap.op2.portal.settlement.chargeCondition");//Pay for direction label
			messages.add("eaap.op2.portal.settlement.arrange");//Pay for direction
			messages.add("eaap.op2.portal.settlement.ChargePercent");//Money Unit
			messages.add("eaap.op2.portal.settlement.baseCharge");//Charge label
			messages.add("eaap.op2.portal.settlement.priority");//Charge
			messages.add("eaap.op2.portal.settlement.effAndExpDate");//Period of Validity
			messages.add("eaap.op2.portal.price.to");//Period of Validity to
			messages.add("eaap.op2.portal.price.priceDesc");//Description
			//rule table
			messages.add("eaap.op2.portal.price.id");//
			messages.add("eaap.op2.portal.settlement.ruleType");//
			messages.add("eaap.op2.portal.settlement.SBName");//
			messages.add("eaap.op2.portal.settlement.description");//
			messages.add("eaap.op2.portal.settlement.ruleType");//
			messages.add("eaap.op2.portal.pardSpec.operation");//
			//button
			messages.add("eaap.op2.portal.doc.submit");//ok
			messages.add("eaap.op2.portal.doc.cancel");//cancel
			
			messages.add("eaap.op2.portal.price.basic.endWith-1");
			messages.add("eaap.op2.portal.price.basic.greater");
			messages.add("eaap.op2.portal.price.basic.equal1");
			messages.add("eaap.op2.portal.price.basic.equal2");
			messages.add("eaap.op2.portal.price.basic.notNullBase");
			messages.add("eaap.op2.portal.price.billingDiscount.nNotNull");
			messages.add("eaap.op2.portal.price.billingDiscount.dNotNull");
			messages.add("eaap.op2.portal.price.billingDiscount.nLessThand");
			messages.add("eaap.op2.portal.price.billingDiscount.endWith0d");
			
			//按钮
			
			addTranslateMessage(mv, messages);
			mv.addObject("aggregationItemList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_AGGREGATION_ITEM));//item SELECT
			mv.addObject("settleChargeDirList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_CHARGE_DIR));//Pay for direction
			mv.addObject("settleMoneyUnitList",mainDataServ.getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE));//Money Unit 	
			mv.addObject("settleAccumDateTypeList",mainDataServ.getMainInfo(EAAPConstants.SETTLE_ACCUM_DATE_TYPE));//Order Use Method
			
			Org orgBean = getOrg(request);
			String actionType = getRequestValue(request, "actionType");
			String formNum = getRequestValue(request, "formNum");
			String ruleType = getRequestValue(request, "ruleType");
			String defaultName = getRequestValue(request, "defaultName");
			String operatorOrgId=getRequestValue(request, "operatorOrgId");
			String prodIds=getRequestValue(request, "settleSpBusiDef.servCode");
			mv.addObject("formNum",formNum);
			mv.addObject("ruleType",ruleType);
			mv.addObject("actionType",actionType);
			mv.addObject("operatorOrgId",operatorOrgId);
			SettleRule settleRule=new SettleRule();
			if("update".equals(actionType)||"detail".equals(actionType)){
				String ruleId = getRequestValue(request, "ruleId");
				SettleRuleAggregation settleRuleAggregation=new SettleRuleAggregation();
				SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
				settleRule.setRuleId(new BigDecimal(ruleId));
				settleRule = settleServ.querySettleRule(settleRule).get(0);
				settleRule.setForEffDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getEffDate(), "yyyy-MM-dd")) ;
				settleRule.setForExpDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getExpDate(), "yyyy-MM-dd")) ;
				settleRule.setRuleName(settleRule.getRuleName().replaceAll("%20"," "));
				
				settleRuleAggregation.setRuleId(settleRule.getRuleId());
				settleRuleAggregation = settleServ.querySettleRuleAggregation(settleRuleAggregation).get(0);
				
				settleRuleCondition.setRuleDetailId(settleRuleAggregation.getRuleDetailId());
				//settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
				List ruleConditionList = settleServ.querySettleRuleCondition(settleRuleCondition);
				mv.addObject("ruleConditionList",ruleConditionList);
				
				mv.addObject("settleRuleCondition",settleRuleCondition);//
				mv.addObject("settleRule",settleRule);//
				mv.addObject("settleRuleAggregation",settleRuleAggregation);//
				
				//查询销售品信息
				ProdOffer prodOfferQuery=new ProdOffer();
				prodOfferQuery.setProdOfferId(new BigDecimal(prodIds));
				ProdOffer prodOffer=pardOfferServ.selectProdOffer(prodOfferQuery);
				String prodOfferType=prodOffer.getOfferType();
                int billingPriorityValue=calculateTransferFromDb(settleRule.getPriority(),prodOfferType);
                mv.addObject("billingPriorityValue",billingPriorityValue);
				
				Map paramMap = new HashMap();
				paramMap.put("partnerCode", orgBean.getOrgId());
				paramMap.put("busiCode", settleRule.getBusiCode());
				paramMap.put("servCode", settleRule.getServCode());
				paramMap.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
				paramMap.put("settleType", 4);
				paramMap.put("pro_mysql", 0);
				paramMap.put("page_record", 9999);
				paramMap.put("raiseFlag", 0);
				List settleRuleList = settleServ.querySettleRuleForGrid(paramMap);	
				mv.addObject("settleRuleList",settleRuleList);
			}else{
				String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
				String servCode = getRequestValue(request, "settleSpBusiDef.servCode");
				settleRule.setRuleName(defaultName+"_"+getI18nMessage("eaap.op2.portal.settlement.aggregationRule")+"_"+formNum);
				settleRule.setServCode(servCode);
				settleRule.setBusiCode(busiCode);
				mv.addObject("settleRule",settleRule);//
			}
			mv.setViewName("../pardOfferSettlement/aggregationRule.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	private List getMonthDayListForSelect(){
		List monthDayList = new ArrayList();
		String[] monthDayArr = EAAPConstants.MONTH_DAY_TO_STRING.split(",");
		for(String str:monthDayArr){
			HashMap<String,String> mdMap = new HashMap<String, String>();
			mdMap.put("key", str);
			mdMap.put("value", str);
			monthDayList.add(mdMap);
		}
		return monthDayList;
	}
	public List<Map<String,String>> getAccumDateTypeList(Map para){
		String applicType =(String) para.get("cycleType");
		List<Map<String, String>> settleAccumDateTypeList=mainDataServ.getMainInfo(EAAPConstants.SETTLE_ACCUM_DATE_TYPE);
		if(!"3".equals(applicType)){
			for(Map<String, String> map:settleAccumDateTypeList){
				if(map.get("key").equals(EAAPConstants.SETTLE_ACCUM_DATE_TYPE_MONTH)){
					settleAccumDateTypeList.remove(map);
					break;
				}
			}
		}
		return settleAccumDateTypeList;
	}
	
	@RequestMapping(value = "/deleteSettlement", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlement",parameterKey="busiId",privilege="")
	@ResponseBody
	public String pardOfferSettlementDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String busiCode = getRequestValue(request, "busiCode");
			String servCode = getRequestValue(request, "servCode");
			String busiId = getRequestValue(request, "busiId");
			Org orgBean = (Org) getOrg(request);
			if(!StringUtil.isEmpty(busiId) && !StringUtil.isEmpty(busiCode) && !StringUtil.isEmpty(servCode)){
				this.deleteSettleSpBusiDef(busiId);
				this.deleteSettleRule(orgBean.getOrgId().toString(),busiCode, servCode);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/detailDelete", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlementRule",parameterKey="ruleId",privilege="")
	@ResponseBody
	public String pardOfferSettlementDetailDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String ruleId = getRequestValue(request, "ruleId");
			String ruleType = getRequestValue(request, "ruleType");
			if(!StringUtil.isEmpty(ruleId) && !StringUtil.isEmpty(ruleType)){
				settleServ.deletePardOfferSettlementDetail(ruleId,ruleType);
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
	
	private void deleteSettleSpBusiDef(String busiId){
		try{
			SettleSpBusiDef settleSpBusiDef=new SettleSpBusiDef();
			settleSpBusiDef.setBusiId(new BigDecimal(busiId));
			settleSpBusiDef.setStatusCd("1300");
			settleSpBusiDef.setSyncFlag(3);
			settleServ.updateSettleSpBusiDef(settleSpBusiDef);
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
	}
	
	private void deleteSettleRule(String partnerCode,String busiCode,String servCode){
		try{
			SettleRule settleRule=new SettleRule();
			settleRule.setPartnerCode(partnerCode);
			settleRule.setBusiCode(busiCode);
			settleRule.setServCode(servCode);
			settleRule.setStatusCd("1300");
			settleRule.setSyncFlag(3);
			settleServ.delSettleRule(settleRule);
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
	}
	@RequestMapping(value = "/saveSettle", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlement",parameterKey="settleSpBusiDef.busiId",privilege="")
	@ResponseBody
	public String saveSettle(final HttpServletRequest request,
			final HttpServletResponse response,String formSubStr)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String actionKey = getRequestValue(request, "actionKey");
			JSONObject reJson=settleServ.saveOrUpdateSettle(request,response,actionKey,getOrg(request));
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,reJson);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/saveOTC", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlementRule",parameterKey="settleRule.ruleId",privilege="")
	@ResponseBody
	public String saveOTC(final HttpServletRequest request,
			final HttpServletResponse response,String key)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			JSONObject reJson=settleServ.saveOrUpdateOTC(request,response,actionKey,getOrg(request));
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,reJson);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/saveRC", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlementRule",parameterKey="settleRule.ruleId",privilege="")
	@ResponseBody
	public String saveRC(final HttpServletRequest request,
			final HttpServletResponse response,String key)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			JSONObject reJson=settleServ.saveOrUpdateRC(request,response,actionKey,getOrg(request));
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,reJson);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/saveAggregation", method = RequestMethod.POST)
	@SavePermission(center="pard",module="settlementRule",parameterKey="settleRule.ruleId",privilege="")
	@ResponseBody
	public String saveAggregation(final HttpServletRequest request,
			final HttpServletResponse response,String key)throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			String actionKey = getRequestValue(request, "actionKey");
			JSONObject reJson=settleServ.saveOrUpdateSettleRuleAggregation(request,response,actionKey,getOrg(request));
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,reJson);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/getAggregationRuleInfo")
	@ResponseBody
	public String aggregationRuleInfoList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			Org orgBean = getOrg(request);
			//获取请求次数
		    String draw = "0";
		    draw = getRequestValue(request, "draw");
		    //数据起始位置
		    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			//获取当前用户
			String busiCode = getRequestValue(request, "busiCode");
			String servCode = getRequestValue(request, "servCode");
			String settleRuleName = getRequestValue(request, "settleRuleName");
			Map paramMap = new HashMap();
			paramMap.put("partnerCode", orgBean.getOrgId());
			paramMap.put("busiCode", busiCode);
			paramMap.put("servCode", servCode);
			paramMap.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			paramMap.put("settleType", 4);//排除总量批价
			if(null!=settleRuleName&&!"".equals(settleRuleName)){
				paramMap.put("settleRuleName", settleRuleName);
			}
			//总记录数
		    int recordsTotal = settleServ.cotSettleRuleForGrid(paramMap);
//			paramMap.put("orgId", orgId);
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			paramMap.put("raiseFlag", 0);
			List<SettleRule> pardSpecList = settleServ.querySettleRuleForGrid(paramMap);
			JSONArray dataList=new JSONArray();
			JSONArray subDataList=null;
			for(SettleRule settleRule:pardSpecList){
				subDataList=new JSONArray();
				subDataList.add("<input type='checkbox' value='' class='checkboxes' id='"+settleRule.getRuleId()+"' name='' />");
				subDataList.add(getRuleType(String.valueOf(settleRule.getRuleType())));
				subDataList.add(settleRule.getRuleName());
				dataList.add(subDataList);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",recordsTotal);
			json.put("recordsFiltered", recordsTotal);
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
	private String getRuleType(String value){
		String reValue =null;
		if("1".equals(value)){
			reValue="eaap.op2.portal.settlement.RCRule";
	    }else if("2".equals(value)){
			reValue="eaap.op2.portal.settlement.OTCRule";
	    }else if("4".equals(value)){
			reValue="eaap.op2.portal.settlement.aggregationRule";
	    }
		if(reValue!=null) {
			return getI18nMessage(reValue);
			}
		else {
			return value;
			}
	}
	
	
	@RequestMapping(value = "/deleteSettleRuleCondition",method = RequestMethod.POST)
	@ResponseBody
	public String deleteSettleRuleCondition(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			String ruleConditionId=getRequestValue(request, "ruleConditionId");
			SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
			settleRuleCondition.setRuleConditionId(new BigDecimal(ruleConditionId));
			settleServ.deleteSettleRuleCondition(settleRuleCondition);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
		
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
}
