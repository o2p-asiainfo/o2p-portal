package com.ailk.o2p.portal.controller.provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Blob;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.NodeDesc;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.ServiceProductRela;
import com.ailk.eaap.op2.bo.SettleRule;
import com.ailk.eaap.op2.bo.SettleRuleCLC;
import com.ailk.eaap.op2.bo.SettleRuleCondition;
import com.ailk.eaap.op2.bo.TechImpAtt;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.o2p.portal.bo.UserDefined;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.process.IProcessSrv;
import com.ailk.o2p.portal.service.provider.IProviderService;
import com.ailk.o2p.portal.service.settlement.ISettlementServ;
import com.ailk.o2p.portal.service.tenant.ITenantService;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.alibaba.fastjson.JSON;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.asiainfo.integration.o2p.web.util.WorkRestUtils;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

@Controller
@Transactional
public class MySystemController extends BaseController{

	private static Logger log = Logger.getLog(MySystemController.class);
	@Autowired
	private IProviderService providerService;
	@Autowired
	private ISettlementServ settleServ;
	@Autowired
	private IMessageServ messageServ;
	@Autowired
	private IProcessSrv processSrv;
	@Autowired
	private ITenantService tenantService;
	
	private static final String F_DIR_ID = "800000001";
	private static final String P_F_DIR_ID = "401";
	//分页参数
	private int start;
	private int length;
	private int total;
	private int draw;
	@RequestMapping(value = "/provider/mySystem")
	public ModelAndView mySystem() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.mySystem");
		messages.add("eaap.op2.portal.index.home");
		messages.add("eaap.op2.portal.doc.provCentre");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		if(switchEnvironment()){
			mv.addObject("switchEnvironmentFlag",true);
		}else{
			mv.addObject("switchEnvironmentFlag",false);
		}
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.setViewName(tenantId+"/"+template+"/provider/mySystem.htm"); 
		}else{
			mv.setViewName("mySystem.jsp");
		}
		return mv;
	}

	@RequestMapping(value = "/provider/pricePlan2")
	public ModelAndView pricePlan2() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.sso.function.functionName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("pricePlan2.jsp");
		return mv;
	}

	@RequestMapping(value = "/provider/basicTariff")
	public ModelAndView basicTariff() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.sso.function.functionName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("basicTariff.jsp");
		return mv;
	}
	@RequestMapping(value = "/provider/jumpfileUpload")
	public ModelAndView jumpfileUpload(final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		String type = getRequestValue(request, "type");
		map.put("type", type);
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.sso.function.functionName");
		ModelAndView mv = new ModelAndView("fileUpload.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	@RequestMapping(value = "/ajax/imgupload")
	public ModelAndView imgupload(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.sso.function.functionName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("../ajax/changeImg.jsp");
		return mv;
	}
	@RequestMapping(value = "/provider/billingDiscount")
	public ModelAndView billingDiscount(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<HashMap<String,String>> taxTypeList = showTaxTypeList();
		List<HashMap<String,String>> currencyUnitTypeList = showCurrencyUnitTypeList();
		List<HashMap<String,String>> promTypeList = showPromTypeList();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxTypeList", taxTypeList);
		map.put("currencyUnitTypeList", currencyUnitTypeList);
		map.put("promTypeList", promTypeList);
		SecureRandom rand = new SecureRandom(); 
		map.put("billingDiscountId", "tab_"+rand.nextInt(1000000));
		map.put("start", DateUtil.convDateToString(new Date(), "yyyy-MM-dd"));
		map.put("end", "2037-12-31");
		String priceInfoId = getRequestValue(request, "priceInfoId");
		String priceId = getRequestValue(request, "priceId");
		if(null != priceId && !"".equals(priceId) && null != priceInfoId && !"".equals(priceInfoId)){//查看或者修改
			ComponentPrice componentPrice = new ComponentPrice();
			PricingPlan pricingPlan = new PricingPlan();
			BillingDiscount billingDiscount = new BillingDiscount();
			RatingCurverDetail ratingCurverDetail = new RatingCurverDetail();
			componentPrice.setPriPricingInfoId(Integer.parseInt(priceInfoId));
			pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("priceId", priceId);
			parammap.put("pro_mysql", 0);
			parammap.put("page_record", 1);
			componentPrice = providerService.queryComponentPrice(parammap).get(0);
			componentPrice.setFormatEffDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getEffDate(), "yyyy-MM-dd")) ;
			componentPrice.setFormatExpDate("".equals(StringUtil.valueOf(componentPrice.getEffDate()))?null:DateUtil.convDateToString(componentPrice.getExpDate(), "yyyy-MM-dd")) ;
			billingDiscount.setPriceId(Integer.parseInt(priceId));
			billingDiscount = providerService.queryBillingDiscount(billingDiscount);
			ratingCurverDetail.setPriceId(Integer.parseInt(priceId));
			List<RatingCurverDetail> ratingCurverDetailList = providerService.queryRatingCurveDetail(ratingCurverDetail);
			map.put("componentPrice", componentPrice);
			map.put("pricingPlan", pricingPlan);
			map.put("billingDiscount", billingDiscount);
			map.put("ratingCurverDetailList", ratingCurverDetailList);
		}
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.price.priceName");
		messages.add("eaap.op2.portal.price.priceType");
		messages.add("eaap.op2.portal.price.priority");
		messages.add("eaap.op2.portal.price.promType");
		messages.add("eaap.op2.portal.price.maxDiscount");
		messages.add("eaap.op2.portal.price.priceTime");
		messages.add("eaap.op2.portal.price.calcType");
		messages.add("eaap.op2.portal.price.fee");
		messages.add("eaap.op2.portal.price.arrange");
		messages.add("eaap.op2.portal.price.discount");
		messages.add("eaap.op2.portal.price.priceType2");
		messages.add("eaap.op2.portal.price.priceDesc");
		messages.add("eaap.op2.portal.price.Section");
		messages.add("eaap.op2.portal.price.finalSection");
		messages.add("eaap.op2.portal.price.delete");
		messages.add("eaap.op2.portal.price.submit");
		messages.add("eaap.op2.portal.price.Opertion");
		messages.add("eaap.op2.portal.price.message.saveSuccess");
		messages.add("eaap.op2.portal.price.message.saveFail");
		messages.add("eaap.op2.portal.price.basic.greater");
		messages.add("eaap.op2.portal.price.message.mustNum");
		messages.add("eaap.op2.portal.price.message.notEmpty");
		//
		ModelAndView mv = new ModelAndView("billingDiscount.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SavePermission(center="prov",module="pricePlanRule",parameterKey="billingPriPricingInfoId",privilege="")
	@RequestMapping(value = "/provider/saveOrUpdateBillingDiscount")
	@ResponseBody
	public String saveOrUpdateBillingDiscount(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String billingPriPricingInfoId = getRequestValue(request, "billingPriPricingInfoId");
		String billingPriceId = getRequestValue(request, "billingPriceId");
		String priceName = getRequestValue(request, "priceName");
		String taxType = getRequestValue(request, "taxType");
		String priority = getRequestValue(request, "priority");
		String promType = getRequestValue(request, "promType");
		String maxMonney = getRequestValue(request, "maxMonney");
		String currencyUnitType = getRequestValue(request, "currencyUnitType");
		String startTime = getRequestValue(request, "startTime");
		String endTime = getRequestValue(request, "endTime");
		String calcType = getRequestValue(request, "calcType");
		String description = getRequestValue(request, "description");
		ComponentPrice componentPrice = new ComponentPrice();
		if(null != billingPriceId && !"".equals(billingPriceId)){
			componentPrice.setPriceId(Integer.parseInt(billingPriceId));
		}
		if(null != billingPriPricingInfoId && !"".equals(billingPriPricingInfoId)){
			componentPrice.setPriPricingInfoId(Integer.parseInt(billingPriPricingInfoId));
		}
		componentPrice.setPriceName(priceName);
		componentPrice.setFormatEffDate(startTime);
		componentPrice.setFormatExpDate(endTime);
		componentPrice.setEffDate("".equals(StringUtil.valueOf(componentPrice.getFormatEffDate()))?null:DateUtil.stringToDate(componentPrice.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setExpDate("".equals(StringUtil.valueOf(componentPrice.getFormatExpDate()))?null:DateUtil.stringToDate(componentPrice.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
		componentPrice.setPriceType("8");
		componentPrice.setStatusCd("10");
		componentPrice.setDescription(description);
		
		BillingDiscount billingDiscount = new BillingDiscount();
		billingDiscount.setPriceId(componentPrice.getPriceId());
		billingDiscount.setPriceName(componentPrice.getPriceName());
		billingDiscount.setTaxIncluded(taxType);
		if(null != priority && !"".equals(priority)){ 
			billingDiscount.setBillingPriority(Integer.parseInt(priority));
		}
		billingDiscount.setPromType(promType);
		if(null != maxMonney && !"".equals(maxMonney)){
			billingDiscount.setMaxiMum(maxMonney);
		}
		billingDiscount.setCurrencyUnitType(Integer.parseInt(currencyUnitType));
		billingDiscount.setCalcType(calcType);
		
		RatingCurverDetail ratingCurverDetail = new RatingCurverDetail();
		String[] startPercent = request.getParameterValues("startPercent");
		String[] endPercent = request.getParameterValues("endPercent");
		String[] sonPercent = request.getParameterValues("sonPercent");
		String[] matherPercent = request.getParameterValues("matherPercent");
		
		String[] startOther = request.getParameterValues("startOther");
		String[] endOther = request.getParameterValues("endOther");
		String[] otherValue = request.getParameterValues("otherValue");
		
		if(null==componentPrice.getPriceId()){
			Integer priceId = providerService.addComponentPrice(componentPrice);
			billingDiscount.setPriceId(priceId);
			providerService.addBillingDiscount(billingDiscount);
			 
			if("1".equals(billingDiscount.getPromType())){
				if(null != startPercent && null !=endPercent && null != sonPercent && null != matherPercent){
					for(int i=0;i<startPercent.length;i++){
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(i+1);
						ratingCurverDetail.setStartval(Integer.parseInt(startPercent[i]));
						ratingCurverDetail.setEndVal(Integer.parseInt(endPercent[i]));
						ratingCurverDetail.setNumerator(Integer.parseInt(sonPercent[i]));
						ratingCurverDetail.setDenominator(Integer.parseInt(matherPercent[i]));
						providerService.addRatingCurveDetail(ratingCurverDetail);
					}
				}
			}else{
				if(null != startOther && null !=endOther && null != otherValue){
					for(int i=0;i<startOther.length;i++){
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(i+1);
						ratingCurverDetail.setStartval(Integer.parseInt(startOther[i]));
						ratingCurverDetail.setEndVal(Integer.parseInt(endOther[i]));
						ratingCurverDetail.setBaseVal(otherValue[i]);
						providerService.addRatingCurveDetail(ratingCurverDetail);
					}
				}
			}
		}else{
			providerService.updateComponentPrice(componentPrice);
			providerService.updateBillingDiscount(billingDiscount);
			
			ratingCurverDetail.setPriceId(componentPrice.getPriceId());
			List<RatingCurverDetail> ratingCurverDetailList = providerService.queryRatingCurveDetail(ratingCurverDetail);
			for(RatingCurverDetail r : ratingCurverDetailList){
				providerService.delRatingCurveDetail(r);
			}
			if("1".equals(billingDiscount.getPromType())){
				if(null != startPercent && null !=endPercent && null != sonPercent && null != matherPercent){
					for(int i=0;i<startPercent.length;i++){
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(i+1);
						ratingCurverDetail.setStartval(Integer.parseInt(startPercent[i]));
						ratingCurverDetail.setEndVal(Integer.parseInt(endPercent[i]));
						ratingCurverDetail.setNumerator(Integer.parseInt(sonPercent[i]));
						ratingCurverDetail.setDenominator(Integer.parseInt(matherPercent[i]));
						providerService.addRatingCurveDetail(ratingCurverDetail);
					}
				}
			}else{
				if(null != startOther && null !=endOther && null != otherValue){
					for(int i=0;i<startOther.length;i++){
						ratingCurverDetail.setPriceId(componentPrice.getPriceId());
						ratingCurverDetail.setSegMentId(i+1);
						ratingCurverDetail.setStartval(Integer.parseInt(startOther[i]));
						ratingCurverDetail.setEndVal(Integer.parseInt(endOther[i]));
						ratingCurverDetail.setBaseVal(otherValue[i]);
						providerService.addRatingCurveDetail(ratingCurverDetail);
					}
				}
			}
		}
		JSONObject json=new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		json.put("result", billingDiscount.getPriceId());
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	@RequestMapping(value = "/provider/updateComponent")
	@ResponseBody
	public String updateComponent(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String componentId = getRequestValue(request, "componentId");
		String sFileId = getRequestValue(request, "sFileId");
		JSONObject json=new JSONObject();
		if(null != sFileId && !"".equals(sFileId)){
			Component component = new Component();
			component.setComponentId(componentId);
			component.setSfileId(Integer.valueOf(sFileId));
			providerService.updateComponentById(component);
			json.put(RETURN_CODE, RESULT_OK);
		}else{
			json.put(RETURN_CODE, RESULT_ERR);
		}
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/updateComponentContent")
	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	@ResponseBody
	public String updateComponentContent(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String componentId = getRequestValue(request, "componentId");
		String sysName = getRequestValue(request, "sysName");
		String sysDetail = getRequestValue(request, "sysDetail");
		JSONObject json=new JSONObject();
		Component component = new Component();
		component.setComponentId(componentId);
		if(null != sysName && !"".equals(sysName)){
			component.setName(sysName);
		}
		if(null != sysDetail && !"".equals(sysDetail)){
			component.setDescriptor(sysDetail);
		}
		providerService.updateComponentById(component);
		return json.toString();
	}
	public List<HashMap<String,String>> showPromTypeList(){
		List<HashMap<String,String>> promTypeList = new ArrayList<HashMap<String,String>>();
		Map<String,String> priceTypeMap = getMainInfo("BillingDiscount_PromType");//EAAPConstants.PROMTYPE
		Iterator<Entry<String, String>> iter = priceTypeMap.entrySet().iterator();   
		while(iter.hasNext()) {
			HashMap<String,String> mapTmp = new HashMap<String,String>() ;
			Entry<String,String> entry = (Entry<String,String>)iter.next();
			mapTmp.put("key", entry.getKey().toString()) ;
			mapTmp.put("value", entry.getValue().toString()) ;
			promTypeList.add(mapTmp) ;
		}
		return promTypeList;
	}
	public List<HashMap<String,String>> showCurrencyUnitTypeList(){
		List<HashMap<String,String>> currencyUnitTypeList = new ArrayList<HashMap<String,String>>();
		Map<String,String> currencyUnitType = getMainInfo(EAAPConstants.CURRENCY_UNIT_TYPE);
		Iterator<Entry<String, String>> iter = currencyUnitType.entrySet().iterator();   
		while(iter.hasNext()) {
			HashMap<String,String> mapTmp = new HashMap<String,String>() ;
			Entry<String,String> entry = (Entry<String,String>)iter.next();
			mapTmp.put("key", entry.getKey().toString()) ;
			mapTmp.put("value", entry.getValue().toString()) ;
			currencyUnitTypeList.add(mapTmp) ;
		}
		return currencyUnitTypeList;
	}
	public List<HashMap<String,String>> showTaxTypeList(){
		List<HashMap<String,String>> taxTypeList = new ArrayList<HashMap<String,String>>();
		Map<String,String> tax = getMainInfo(EAAPConstants.TAXINCLUDED);
		Iterator<Entry<String, String>> iter = tax.entrySet().iterator();   
		while(iter.hasNext()) {
			HashMap<String,String> mapTmp = new HashMap<String,String>() ;
			Entry<String,String> entry = (Entry<String,String>)iter.next();
			mapTmp.put("key", entry.getKey().toString()) ;
			mapTmp.put("value", entry.getValue().toString()) ;
			taxTypeList.add(mapTmp) ;
		}
		return taxTypeList;
	}
	
	public Map<String,String> getMainInfo(String mdtSign){
	  	  MainDataType mainDataType = new MainDataType();
	  	  MainData mainData = new MainData();
	  	  Map<String,String> stateMap = new LinkedHashMap<String,String>() ;
	  	  mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
	  	  mainDataType.setMdtSign(mdtSign) ;
		  mainDataType = providerService.selectMainDataType(mainDataType).get(0) ;
		  mainData.setMdtCd(mainDataType.getMdtCd()) ;
		  mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
		  List<MainData> mainDataList = providerService.selectMainData(mainData) ;
		 
		  if(mainDataList!=null && mainDataList.size()>0){
			  for(int i=0;i<mainDataList.size();i++){
				  stateMap.put(mainDataList.get(i).getCepCode(),
						          mainDataList.get(i).getCepName()) ;
			  }
		  }
	  	return stateMap ;
	  }
	@RequestMapping(value = "/provider/provOperator")
	public ModelAndView provOperator(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		String componentId = getRequestValue(request, "componentId");
		String state = getRequestValue(request, "state");
		Component component2 = new Component();
		component2.setComponentId(componentId);
		if(EAAPConstants.COMM_STATE_UPGRADE.equals(state)){//升级中
			component2.setUpState(state);
		}else{
			component2.setState(state);
		}
		providerService.editProvSystem(component2);
		if("X".equals(state) || "G".equals(state)){
			providerService.updateProdOfferById(componentId);
		}
		if("X".equals(state)){
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			messages.add("eaap.op2.portal.doc.mySystem");
			messages.add("eaap.op2.portal.index.home");
			messages.add("eaap.op2.portal.doc.provCentre");
			addTranslateMessage(mv, messages);
			mv.setViewName("mySystem.jsp");
			return mv;
		}else{
			Map<String, String> systemMap = providerService.queryProvSystem(componentId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("componentId", componentId);
			if(null != systemMap.get("UP_STATE")){
				map.put("upState", String.valueOf(systemMap.get("UP_STATE")));
			}else{
				map.put("upState", "");
			}
			map.put("state", state);
			map.put("systemMap", systemMap);
			if(EAAPConstants.COMM_STATE_WAITAUDI.equals(state) 
					|| (EAAPConstants.COMM_STATE_ONLINE.equals(state) && EAAPConstants.COMM_STATE_WAITUPGRADE.equals(String.valueOf(systemMap.get("UP_STATE"))))
					|| (EAAPConstants.COMM_STATE_ONLINE.equals(state) && ("null".equals(String.valueOf(systemMap.get("UP_STATE"))) || "".equals(String.valueOf(systemMap.get("UP_STATE")))))){
				map.put("userAble", "NO");
			}else{
				map.put("userAble", "Yes");
			}
			
//			List<String> messages = new ArrayList<String>();
//			// 添加页面上需要国际化的消息
//			messages.add("eaap.op2.portal.doc.sysName");
//			messages.add("eaap.op2.portal.doc.systemNo");
//			messages.add("eaap.op2.portal.doc.createTime");
//			messages.add("eaap.op2.portal.doc.systemDetail");
//			messages.add("eaap.op2.portal.doc.imgInfo");
//			messages.add("eaap.op2.portal.doc.notUpload");
//			messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
//			
//			messages.add("eaap.op2.portal.index.home");
//			messages.add("eaap.op2.portal.doc.provCentre");
//			messages.add("eaap.op2.portal.devmgr.developTest");
//			messages.add("eaap.op2.portal.devmgr.submitToCheck");
//			messages.add("eaap.op2.portal.doc.createSys");
//			messages.add("eaap.op2.portal.doc.systemUp");
//			messages.add("eaap.op2.portal.doc.systemUpGrade");
//			messages.add("eaap.op2.portal.doc.systemDown");
//			messages.add("eaap.op2.portal.doc.systemDel");
//			messages.add("eaap.op2.portal.doc.systemDetail");
//			messages.add("eaap.op2.portal.doc.baseInfo");
//			messages.add("eaap.op2.portal.doc.add");
//			messages.add("eaap.op2.portal.doc.generalService");
//			messages.add("eaap.op2.portal.doc.oneselfService");
//			messages.add("eaap.op2.portal.doc.applyability");
//			messages.add("eaap.op2.portal.doc.fenType");
//			messages.add("eaap.op2.portal.doc.serviceProvWay");
//			messages.add("eaap.op2.portal.doc.applyabilityName");
//			messages.add("eaap.op2.portal.doc.developValue");
//			messages.add("eaap.op2.portal.doc.operator");
//			messages.add("eaap.op2.portal.doc.package");
//			messages.add("eaap.op2.portal.doc.packageName");
//			messages.add("eaap.op2.portal.doc.pricePlan");
//			messages.add("eaap.op2.portal.pardDistribution.delete");
//			messages.add("eaap.op2.portal.doc.message.oneRecords");
//			messages.add("eaap.op2.portal.doc.message.success");
//			messages.add("eaap.op2.portal.doc.message.edit");
//			messages.add("eaap.op2.portal.doc.message.close");
//			messages.add("eaap.op2.portal.doc.message.ok");
//			messages.add("eaap.op2.portal.doc.developMemo");
//			messages.add("eaap.op2.portal.devmgr.cancel");
//			messages.add("eaap.op2.portal.doc.addPackage");
//			messages.add("eaap.op2.portal.doc.message.newSysName");
//			messages.add("eaap.op2.portal.portalMessage.history");
//			//
//			messages.add("eaap.op2.conf.adapter.state");
//			messages.add("eaap.op2.portal.system.state.newadd");
//			messages.add("eaap.op2.portal.system.state.pendingAudit");
//			messages.add("eaap.op2.portal.system.state.auditFailure");
//			messages.add("eaap.op2.portal.system.state.online");
//			messages.add("eaap.op2.portal.system.state.offline");
//			messages.add("eaap.op2.portal.system.state.upgrading");
//			messages.add("eaap.op2.portal.system.state.upgradeAudit");
//			messages.add("eaap.op2.portal.system.state.upgradeFailed");
//			//
//			ModelAndView mv = new ModelAndView("manageSystem.jsp",map);
//			addTranslateMessage(mv, messages);
			
			mv.setViewName("redirect:/provider/manageSystem.shtml?STATE=E&componentId="+componentId);
			return mv;
		}
	}
	
	@RequestMapping(value = "/provider/provAudiDev")
	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	public ModelAndView provAudiDev(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		try {
			String componentId = getRequestValue(request, "componentId");
			Org org = (Org)request.getSession().getAttribute("userBean");
			com.alibaba.fastjson.JSONObject retJson = new com.alibaba.fastjson.JSONObject();
			com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
			Component component1 = new Component();
			component1.setComponentId(componentId);
			Component cp = providerService.queryProv(component1);

			String state = cp.getState();
			String message = "";
			//在新增、下线、升级的状态下提交审核，会走新建工作流的实例流程
			if (EAAPConstants.COMM_STATE_NEW.equals(cp.getState()) || EAAPConstants.COMM_STATE_DOWNLINE.equals(cp.getState()) || EAAPConstants.COMM_STATE_UPGRADE.equals(cp.getUpState())) {
				String processName = null;
				jsonObject.put("contentId", String.valueOf(cp.getComponentId()));
				if (state.equals(EAAPConstants.COMM_STATE_NEW) || state.equals(EAAPConstants.COMM_STATE_DOWNLINE)) {//新增,下线提交审核
					component1.setState(EAAPConstants.COMM_STATE_WAITAUDI);//等待审核
					processName = "System Process Audit Name:"+cp.getName();
				}else {
					component1.setUpState(EAAPConstants.COMM_STATE_WAITUPGRADE);//等待升级审核
					processName = "System Process Upgrade Name:"+cp.getName();
				}
				UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
				String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
				Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
				if(userRoleInfo != null){
					userId = tenantService.qryAdminIdByTenantId(userRoleInfo.getTenantId());
					tenantId = userRoleInfo.getTenantId();
				}
				retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_SYSONLINE, processName,
						userId, tenantId, jsonObject.toJSONString());
				String returnCode = retJson.getString("code");
				if ("0000".equals(returnCode)) {
					component1.setAuditFlowId(retJson.getString("desc"));
					componentId = providerService.editProvSystem(component1);
				}else{
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"Audit System eErroe --> "+retJson.getString("desc"), null);
				}
			}else if(EAAPConstants.COMM_STATE_NOPASSAUDI.equals(cp.getState()) || EAAPConstants.COMM_STATE_NOUPGRADE.equals(cp.getUpState())){//审核失败跟升级失败的操作
				com.alibaba.fastjson.JSONArray jsons = new com.alibaba.fastjson.JSONArray();
				message = WorkRestUtils.taskListByProcessId(cp.getAuditFlowId());
				
				if(!StringUtils.isEmpty(message)){
					jsons = JSON.parseArray(message);
					if(null!=jsons&&jsons.size()==1){
						retJson = jsons.getJSONObject(0);
						String taskId = retJson.getString("taskId");
						String ret = WorkRestUtils.completeTask(taskId,jsonObject.toJSONString());
						
						if(!StringUtils.isEmpty(ret)){
							retJson = JSON.parseObject(ret);
							String returnCode = retJson.getString("code");
							if ("0000".equals(returnCode)) {
								//置待办消息的状态
								BigDecimal msgId = messageServ.getMsgIdByQueryTitle(
										EAAPConstants.WORK_FLOW_MESSAGE_QUERY_COM.replace("#id", componentId));
								messageServ.lookMsgById(String.valueOf(org.getOrgId()),String.valueOf(msgId));
								messageServ.updateMsgForWorkFlow(msgId);
								if(EAAPConstants.COMM_STATE_NOPASSAUDI.equals(cp.getState())){
									component1.setState(EAAPConstants.COMM_STATE_WAITAUDI);//等待审核
								}else{
									component1.setUpState(EAAPConstants.COMM_STATE_WAITUPGRADE);//等待升级审核
								}
								componentId = providerService.editProvSystem(component1);
							}else{
								throw new BusinessException(ExceptionCommon.WebExceptionCode,"Audit System eErroe --> "+retJson.getString("desc"), null);
							}
						}
					}
				}
			}
			mv.setViewName("redirect:/provider/manageSystem.shtml?STATE=B&componentId="+componentId);
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/provider/manageSystem")
	public ModelAndView manageSystem(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String componentId = getRequestValue(request, "componentId");
		//String state = getRequestValue(request, "state");
		Map<String, String> systemMap = providerService.queryProvSystem(componentId);
		//获取系统状态
		String state = systemMap.get("STATE");
		 
		//获取offerList
		Map paramMap=new HashMap();
		List<Map<String, Object>> showPackageList = providerService.showPackage(componentId,paramMap);
		//获取processList
		Map<String, Object> processMap = new HashMap();
		processMap.put("SYSTEMID", componentId);
		List<Map<String, Object>> processList = providerService.getProcessList(processMap);
		String processId ="";
		if(processList.size()>0){
			Map maptempdd = processList.get(0);
			processId = (String)maptempdd.get("PROCESS_ID");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("componentId", componentId);
		if(null != systemMap.get("UP_STATE")){
			map.put("upState", String.valueOf(systemMap.get("UP_STATE")));
		}else{
			map.put("upState", "");
		}
		map.put("state", state);
		map.put("systemMap", systemMap);
		map.put("processId", processId);
		map.put("showPackageList", showPackageList);
		map.put("processList", processList);
		map.put("checkMsg", messageServ.getCheckMsgByObjectId(EAAPConstants.WORK_FLOW_MESSAGE_QUERY_COM, componentId));
		if(EAAPConstants.COMM_STATE_WAITAUDI.equals(state) 
				|| (EAAPConstants.COMM_STATE_ONLINE.equals(state) && EAAPConstants.COMM_STATE_WAITUPGRADE.equals(String.valueOf(systemMap.get("UP_STATE"))))
				|| (EAAPConstants.COMM_STATE_ONLINE.equals(state) && ("null".equals(String.valueOf(systemMap.get("UP_STATE"))) || "".equals(String.valueOf(systemMap.get("UP_STATE")))))){
			map.put("userAble", "NO");
		}else{
			map.put("userAble", "Yes");
		}
		
		//获取流程图片
		Map pMap = new HashMap();
		pMap.put("componentId", componentId);
		String precessKey = this.processSrv.getProcessKeyByComponentId(pMap);
		if(StringUtil.isEmpty(precessKey)){
			precessKey = componentId;
		}
		String processDefImg = this.getProcessDefImg("ott_" + precessKey); 
		map.put("processDefImg", processDefImg);
		
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.sysName");
		messages.add("eaap.op2.portal.doc.systemNo");
		messages.add("eaap.op2.portal.doc.createTime");
		messages.add("eaap.op2.portal.doc.systemDetail");
		messages.add("eaap.op2.portal.doc.imgInfo");
		messages.add("eaap.op2.portal.doc.notUpload");
		messages.add("eaap.op2.portal.doc.provCentre");
		messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
		
		messages.add("eaap.op2.portal.index.home");
		messages.add("eaap.op2.portal.index.devIndex");
		messages.add("eaap.op2.portal.devmgr.developTest");
		messages.add("eaap.op2.portal.devmgr.submitToCheck");
		messages.add("eaap.op2.portal.doc.createSys");
		messages.add("eaap.op2.portal.doc.systemUp");
		messages.add("eaap.op2.portal.doc.systemUpGrade");
		messages.add("eaap.op2.portal.doc.systemDown");
		messages.add("eaap.op2.portal.doc.systemDel");
		messages.add("eaap.op2.portal.doc.systemDetail");
		messages.add("eaap.op2.portal.doc.baseInfo");
		messages.add("eaap.op2.portal.doc.add");
		messages.add("eaap.op2.portal.doc.generalService");
		messages.add("eaap.op2.portal.doc.oneselfService");
		messages.add("eaap.op2.portal.doc.applyability");
		messages.add("eaap.op2.portal.doc.fenType");
		messages.add("eaap.op2.portal.doc.serviceProvWay");
		messages.add("eaap.op2.portal.doc.applyabilityName");
		messages.add("eaap.op2.portal.doc.developValue");
		messages.add("eaap.op2.portal.doc.operator");
		messages.add("eaap.op2.portal.doc.package");
		messages.add("eaap.op2.portal.doc.packageName");
		messages.add("eaap.op2.portal.doc.pricePlan");
		messages.add("eaap.op2.portal.pardDistribution.delete");
		messages.add("eaap.op2.portal.doc.message.oneRecords");
		messages.add("eaap.op2.portal.doc.message.success");
		messages.add("eaap.op2.portal.doc.message.edit");
		messages.add("eaap.op2.portal.doc.message.close");
		messages.add("eaap.op2.portal.doc.message.ok");
		messages.add("eaap.op2.portal.doc.developMemo");
		messages.add("eaap.op2.portal.devmgr.cancel");
		messages.add("eaap.op2.portal.doc.addPackage");
		messages.add("eaap.op2.portal.doc.message.newSysName");
		messages.add("eaap.op2.portal.settlement.settle");
		messages.add("eaap.op2.portal.portalMessage.history");
		//
		messages.add("eaap.op2.conf.adapter.state");
		messages.add("eaap.op2.portal.system.state.newadd");
		messages.add("eaap.op2.portal.system.state.pendingAudit");
		messages.add("eaap.op2.portal.system.state.auditFailure");
		messages.add("eaap.op2.portal.system.state.online");
		messages.add("eaap.op2.portal.system.state.offline");
		messages.add("eaap.op2.portal.system.state.upgrading");
		messages.add("eaap.op2.portal.system.state.upgradeAudit");
		messages.add("eaap.op2.portal.system.state.upgradeFailed");
		ModelAndView mv = new ModelAndView("manageSystem.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	@Permission(center="prov",module="system", privilege="audit")
	@RequestMapping(value = "/provider/createSystem")
	public ModelAndView createSystem() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.createSys");
		messages.add("eaap.op2.portal.index.home");
		messages.add("eaap.op2.portal.doc.provCentre");
		messages.add("eaap.op2.portal.doc.message.fillInfo");
		messages.add("eaap.op2.portal.doc.message.step1to3");
		messages.add("eaap.op2.portal.doc.baseInfo");
		messages.add("eaap.op2.portal.doc.applyability");
		messages.add("eaap.op2.portal.doc.package");
		messages.add("eaap.op2.portal.doc.sysName");
		messages.add("eaap.op2.portal.doc.systemDetail");
		messages.add("eaap.op2.portal.doc.imgInfo");
		messages.add("eaap.op2.portal.devmgr.imageUpload");
		messages.add("eaap.op2.portal.devmgr.appImageDesc");
		messages.add("eaap.op2.portal.doc.add");
		messages.add("eaap.op2.portal.doc.generalService");
		messages.add("eaap.op2.portal.doc.oneselfService");
		messages.add("eaap.op2.portal.doc.applyability");
		messages.add("eaap.op2.portal.doc.fenType");
		messages.add("eaap.op2.portal.doc.serviceProvWay");
		messages.add("eaap.op2.portal.doc.applyabilityName");
		messages.add("eaap.op2.portal.doc.developValue");
		messages.add("eaap.op2.portal.doc.operator");
		messages.add("eaap.op2.portal.doc.packageName");
		messages.add("eaap.op2.portal.doc.pricePlan");
		messages.add("eaap.op2.portal.doc.message.back");
		messages.add("eaap.op2.portal.doc.message.continue");
		messages.add("eaap.op2.portal.devmgr.addApp");
		messages.add("eaap.op2.portal.doc.message.edit");
		messages.add("eaap.op2.portal.doc.message.close");
		messages.add("eaap.op2.portal.doc.message.ok");
		messages.add("eaap.op2.portal.doc.developMemo");
		messages.add("eaap.op2.portal.devmgr.cancel");
		messages.add("eaap.op2.portal.doc.addPackage");
		messages.add("eaap.op2.portal.doc.message.oneRecords");
		messages.add("eaap.op2.portal.doc.message.success");
		messages.add("eaap.op2.portal.doc.notexceed20char");
		messages.add("eaap.op2.portal.settlement.settle");
		//
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("createSystem.jsp");
		return mv;
	}
	@RequestMapping(value = "/provider/general")
	public ModelAndView general(final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.fenType");
		messages.add("eaap.op2.portal.doc.generalService");
		messages.add("eaap.op2.portal.doc.developName");
		messages.add("eaap.op2.portal.doc.developMemo");
		messages.add("eaap.op2.portal.doc.message.back");
		messages.add("eaap.op2.portal.doc.message.next");
		messages.add("eaap.op2.portal.doc.message.ok");
		ModelAndView mv = new ModelAndView("general.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/showAbility")
	@ResponseBody
	public String showAbility(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}
		String componentId = getRequestValue(request, "componentId");
		Map map = new HashMap() ;  
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		JSONObject json = new JSONObject();
		if(null != componentId && !"".equals(componentId)){
			map.put("componentId", componentId);
			total = providerService.countShowAbility(map);
			List<Map<String, Object>> tmpList = providerService.showAbility(map);
			json.put("draw", draw);
			json.put("recordsTotal", length);
			json.put("recordsFiltered", total);
			JSONArray jsonArray = new JSONArray();
			if(null !=  tmpList && tmpList.size() > 0){
				for(Map itData : tmpList){
					JSONArray jsonDate = new JSONArray();
					if(String.valueOf(itData.get("SERSPECVA")).equals("Y")){
						jsonDate.add("Proprietary");
					}else if(String.valueOf(itData.get("SERSPECVA")).equals("N")){
						jsonDate.add("General");
					}
					
					jsonDate.add("<a href='../api/apiDoc.shtml?serviceId="+String.valueOf(itData.get("SERVICEID"))+"'  target='_Blank' >"+String.valueOf(itData.get("CNNAME"))+"</a>");
					if(null != itData.get("ATTRSPECVALUE")){
						if(String.valueOf(itData.get("ATTRSPECVALUE")).length()>30){
							jsonDate.add(String.valueOf(itData.get("ATTRSPECVALUE")).substring(0, 30)+"...");
						}else{
							jsonDate.add(String.valueOf(itData.get("ATTRSPECVALUE")));
						}
					}
					if(String.valueOf(itData.get("SERSPECVA")).equals("Y")){
						if(String.valueOf(itData.get("COMSTATE")).equals("B") ||
								(String.valueOf(itData.get("COMSTATE")).equals("D") && String.valueOf(itData.get("UPSTATE")).equals("H"))
								|| (String.valueOf(itData.get("COMSTATE")).equals("D") && (String.valueOf(itData.get("UPSTATE")).equals("") || String.valueOf(itData.get("UPSTATE")).equals("null")))){
							jsonDate.add("<a editvalue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' disabled state='"+String.valueOf(itData.get("COMSTATE"))+"' upState='"+String.valueOf(itData.get("UPSTATE"))+"' id='editUserDefine' class='btn default btn-xs edit' data-toggle='modal' data-target='#myModal'>"+getI18nMessage("eaap.op2.portal.doc.message.edit")+"</a>&nbsp;<a href='#' id='cabDel' disabled serviceIdValue='"+String.valueOf(itData.get("SERVICEID"))+"'  delValue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' class='btn default btn-xs'>"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"</a>");
						}else{
							jsonDate.add("<a editvalue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' state='"+String.valueOf(itData.get("COMSTATE"))+"' upState='"+String.valueOf(itData.get("UPSTATE"))+"' id='editUserDefine' class='btn default btn-xs edit' data-toggle='modal' data-target='#myModal'>"+getI18nMessage("eaap.op2.portal.doc.message.edit")+"</a>&nbsp;<a href='#' id='cabDel' serviceIdValue='"+String.valueOf(itData.get("SERVICEID"))+"'  delValue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' class='btn default btn-xs'>"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"</a>");
						}
					}else if(String.valueOf(itData.get("SERSPECVA")).equals("N")){
						if(String.valueOf(itData.get("COMSTATE")).equals("B") || 
								(String.valueOf(itData.get("COMSTATE")).equals("D") && String.valueOf(itData.get("UPSTATE")).equals("H"))
								|| (String.valueOf(itData.get("COMSTATE")).equals("D") && (String.valueOf(itData.get("UPSTATE")).equals("") ||  String.valueOf(itData.get("UPSTATE")).equals("null")))){
							jsonDate.add("<a editvalue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' disabled cnname='"+String.valueOf(itData.get("CNNAME"))+"' specValue='"+String.valueOf(itData.get("ATTRSPECVALUE"))+"' id='editGeneral' class='btn default btn-xs' data-toggle='modal' data-target='#serviceAddress'>"+getI18nMessage("eaap.op2.portal.doc.message.edit")+"</a>&nbsp;<a href='#' id='cabDel' disabled serviceIdValue='"+String.valueOf(itData.get("SERVICEID"))+"'  delValue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' class='btn default btn-xs'>"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"</a>");
						}else{
							jsonDate.add("<a editvalue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' cnname='"+String.valueOf(itData.get("CNNAME"))+"' specValue='"+String.valueOf(itData.get("ATTRSPECVALUE"))+"' id='editGeneral' class='btn default btn-xs' data-toggle='modal' data-target='#serviceAddress'>"+getI18nMessage("eaap.op2.portal.doc.message.edit")+"</a>&nbsp;<a href='#' id='cabDel' serviceIdValue='"+String.valueOf(itData.get("SERVICEID"))+"'  delValue='"+String.valueOf(itData.get("TECHIMPATTID"))+"' class='btn default btn-xs'>"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"</a>");
						}
					
					}
					
					jsonArray.add(jsonDate);
				}
				json.put("data", jsonArray);
			}else{
				json.put("data", jsonArray);
			}
		}else{
			json.put("draw", draw);
			json.put("recordsTotal", length);
			json.put("recordsFiltered", total);
			JSONArray jsonArray = new JSONArray();
			json.put("data", jsonArray);
		}
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/chooseService")
	@ResponseBody
	public String chooseService(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}
		String componentId = getRequestValue(request, "componentId");
		Map map = new HashMap() ;  
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		JSONObject json = new JSONObject();
		if(null != componentId && !"".equals(componentId)){
			map.put("componentId", componentId);
			total = providerService.countShowAbility(map);
			List<Map<String, Object>> tmpList = providerService.showAbility(map);
			json.put("draw", draw);
			json.put("recordsTotal", length);
			json.put("recordsFiltered", total);
			JSONArray jsonArray = new JSONArray();
			if(null !=  tmpList && tmpList.size() > 0){
				for(Map itData : tmpList){
					JSONArray jsonDate = new JSONArray();
					jsonDate.add("<input type='radio' value='"+String.valueOf(itData.get("SERVICEID"))+"' trName='"+String.valueOf(itData.get("CNNAME"))+"' class='checkboxes' name='SERVICEID' />");
					if(String.valueOf(itData.get("SERSPECVA")).equals("Y")){
						jsonDate.add("Customized");
					}else if(String.valueOf(itData.get("SERSPECVA")).equals("N")){
						jsonDate.add("General");
					}
					jsonDate.add("<a href='api/apiDoc.shtml?serviceId="+String.valueOf(itData.get("SERVICEID"))+"'>"+String.valueOf(itData.get("CNNAME"))+"</a>");
					if(null != itData.get("ATTRSPECVALUE")){
						if(String.valueOf(itData.get("ATTRSPECVALUE")).length()>30){
							jsonDate.add(String.valueOf(itData.get("ATTRSPECVALUE")).substring(0, 30)+"...");
						}else{
							jsonDate.add(String.valueOf(itData.get("ATTRSPECVALUE")));
						}
					}
					jsonArray.add(jsonDate);
				}
				json.put("data", jsonArray);
			}else{
				json.put("data", jsonArray);
			}
		}else{
			json.put("draw", draw);
			json.put("recordsTotal", length);
			json.put("recordsFiltered", total);
			JSONArray jsonArray = new JSONArray();
			json.put("data", jsonArray);
		}
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/queryGeneralRecords")
//	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	@ResponseBody
	public String queryGeneralRecords(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		// 获取请求次数
		draw = Integer.parseInt(getRequestValue(request, "draw"));
		String componentId = getRequestValue(request, "componentId");
		Map map = new HashMap() ;  
		// 数据起始位置
		start = "".equals(getRequestValue(request, "start")) ? 1 : Integer.parseInt(getRequestValue(request, "start"));
		// 数据长度
		length = "".equals(getRequestValue(request, "length")) ? 10 : Integer.parseInt(getRequestValue(request, "length"));
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		JSONObject json = new JSONObject();
		if(null != componentId && !"".equals(componentId)){
			map.put("componentId", componentId);
			total = providerService.countProvAbility(map);
			List<Map<String, String>>  tmpList = providerService.provAbility(map);
			json.put("draw", draw);
			json.put("recordsTotal", total);
			json.put("recordsFiltered", total);
			JSONArray jsonArray = new JSONArray();
			if(null !=  tmpList && tmpList.size() > 0){
				for(Map itData : tmpList){
					JSONArray jsonDate = new JSONArray();
					String trId = String.valueOf(itData.get("NEWSERVICEID"));
					if(String.valueOf(itData.get("NEWSERVICEID")).equals(String.valueOf(itData.get("OLDSERVICEID")))){
						jsonDate.add("<input type='checkbox' value='"+String.valueOf(itData.get("NEWSERVICEID"))+"' class='checkboxes' checked disabled />");
					}else{
						jsonDate.add("<input type='checkbox' value='"+String.valueOf(itData.get("NEWSERVICEID"))+"' trId='"+trId+"' trName='"+String.valueOf(itData.get("CNNAME"))+"' class='checkboxes' name='SERVICEID' />");
					}
					jsonDate.add("<a target='blank' href='../api/apiDoc.shtml?serviceId="+String.valueOf(itData.get("NEWSERVICEID"))+"'>"+String.valueOf(itData.get("CNNAME"))+"</a>");
					jsonDate.add(String.valueOf(itData.get("SERVICEDESC")));
					jsonArray.add(jsonDate);
				}
				json.put("data", jsonArray);
			}else{
				json.put("data", "");
			}
		}
		return json.toString();
	}
	@RequestMapping(value = "/provider/customized")
	public ModelAndView customized(final HttpServletRequest request,
			final HttpServletResponse response) {
		String componentId = getRequestValue(request, "componentId");
		List<Map<String, Object>> userDefinedList = providerService.userDefinedList(componentId);
		List<Map<String, Object>> commProtocalList = providerService.selectCommProtocal();
		List<Map<String, Object>> directoryList = providerService.selectDirectory(F_DIR_ID);
		List<Map<String, Object>> conTypeList = providerService.selectConType(EAAPConstants.MAINDATACONTYPE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("commProtocalList", commProtocalList);
		map.put("directoryList", directoryList);
		map.put("conTypeList", conTypeList);
		map.put("userDefinedList", userDefinedList);
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.serverName");
		messages.add("eaap.op2.portal.doc.crontactCode");
		messages.add("eaap.op2.portal.doc.crontactVersionCode");
		messages.add("eaap.op2.portal.doc.selectDirectory");
		messages.add("eaap.op2.portal.doc.selectCommProcd");
		messages.add("eaap.op2.portal.doc.serviceProvAddress");
		messages.add("eaap.op2.portal.doc.descr");
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.doc.request");
		messages.add("eaap.op2.portal.doc.response");
		messages.add("eaap.op2.portal.doc.selectProcdFormat");
		messages.add("eaap.op2.portal.doc.contractDocument");
		messages.add("eaap.op2.portal.doc.procdBodyFormat");
		messages.add("eaap.op2.portal.doc.message.import");
		messages.add("eaap.op2.portal.doc.procdDemo");
		messages.add("eaap.op2.portal.doc.nodeName");
		messages.add("eaap.op2.portal.doc.nodePath");
		messages.add("eaap.op2.portal.doc.nodeLengthCons");
		messages.add("eaap.op2.portal.doc.nodeTypeCons");
		messages.add("eaap.op2.portal.doc.nodeNumberCons");
		messages.add("eaap.op2.portal.doc.nevlConsType");
		messages.add("eaap.op2.portal.doc.isNeedCheck");
		messages.add("eaap.op2.portal.doc.operator");
		messages.add("eaap.op2.portal.doc.nevlConsValue");
		messages.add("eaap.op2.portal.price.delete");
		messages.add("eaap.op2.portal.doc.message.close");
		messages.add("eaap.op2.portal.doc.message.back");
		messages.add("eaap.op2.portal.doc.message.ok");
		messages.add("eaap.op2.portal.doc.message.success");
		messages.add("eaap.op2.portal.doc.message.importsuccess");
		messages.add("eaap.op2.portal.doc.message.deletesuccess");
		ModelAndView mv = new ModelAndView("customizedTable.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}

	@RequestMapping(value = "/provider/customizedForm")
	public ModelAndView customizedForm(final HttpServletRequest request,
			final HttpServletResponse response) {
		String editvalue = getRequestValue(request, "editvalue");
		String state  = getRequestValue(request, "state");
		String upState  = getRequestValue(request, "upState");
		UserDefined userDefined = null;
		if(null != editvalue && !"".equals(editvalue)){
			List<Map<String, Object>> userDefineList = providerService.queryUserFine(editvalue);
			userDefined = new UserDefined();
			if(null != userDefineList && userDefineList.size()>0){
				userDefined
						.setServiceName(StringUtil.valueOf(userDefineList.get(0).get("NAME")));
				userDefined.setDescriptor(StringUtil.valueOf(userDefineList.get(0)
						.get("DESCRIPTOR")));
				userDefined.setCommProcd(StringUtil.valueOf(userDefineList.get(0)
						.get("COMMPROCD")));
				userDefined.setComponentId(StringUtil.valueOf(userDefineList.get(0)
						.get("COMMPONENTID")));
				userDefined.setAttrSpecValue(StringUtil.valueOf(userDefineList.get(0)
						.get("SPECVALUE")));
				userDefined.setTechImpId(StringUtil.valueOf(userDefineList.get(0)
						.get("TECHIMPLID")));
//				userDefined.setDirectoryId(StringUtil.valueOf(userDefineList.get(0)
//						.get("DIRID")));
				userDefined.setTechImpAttId(StringUtil.valueOf(userDefineList.get(0)
						.get("TECHIMPATTID")));
				userDefined.setContractId(StringUtil.valueOf(userDefineList.get(0)
						.get("CONTRACTID")));
//				userDefined.setDirSerlistId(StringUtil.valueOf(userDefineList.get(0)
//						.get("DIRSERLISTID")));
				userDefined.setComponentId(StringUtil.valueOf(userDefineList.get(0)
						.get("COMMPONENTID")));
				userDefined.setServiceId(StringUtil.valueOf(userDefineList.get(0)
						.get("SERVICEID")));
				userDefined.setContractCode(StringUtil.valueOf(userDefineList.get(0)
						.get("CODE")));
				userDefined.setContractVersionCode(StringUtil.valueOf(userDefineList.get(0)
						.get("VERSION")));
				userDefined.setContractVersionId( StringUtil.valueOf(userDefineList.get(0).get("CONTRACTVERSIONID")));
				List<Map<String, Object>> dirSerList = providerService.queryDirSerList(userDefined.getServiceId());
				if(dirSerList != null && !dirSerList.isEmpty()){
					List<String> dirIdList = new ArrayList<String>();
					List<String> dirSerListIdList = new ArrayList<String>();
					for(Map<String, Object> dirSerMap : dirSerList){
						dirIdList.add(String.valueOf(dirSerMap.get("DIRID"))); 
						dirSerListIdList.add(String.valueOf(dirSerMap.get("DIRSERLISTID")));
					}
					String dirIdStr = StringUtils.collectionToDelimitedString(dirIdList, ",");
					String dirSerListIdStr = StringUtils.collectionToDelimitedString(dirSerListIdList, ",");
					String isPublic = "1";
					if("800000002".equals(dirIdStr)){ 
						isPublic = "2";
					}

					userDefined.setDirectoryId(dirIdStr);
					userDefined.setDirSerlistId(dirSerListIdStr); 
					userDefined.setIsPublic(isPublic); 
				}
				
			}
		}
		
		List<Map<String, Object>> commProtocalList = providerService.selectCommProtocal();
		List<Map<String, Object>> directoryList = providerService.selectDirectory(F_DIR_ID);
		List<Map<String, Object>> publicDirectoryList = providerService.selectDirectory(P_F_DIR_ID);

		List<Map<String, Object>> conTypeList = providerService.selectConType(EAAPConstants.MAINDATACONTYPE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("commProtocalList", commProtocalList);
		map.put("directoryList", directoryList);
		map.put("publicDirectoryList", publicDirectoryList); 
		map.put("conTypeList", conTypeList);
		if(null !=userDefined){
			map.put("updateFlag", "update");
			map.put("doOperation", "Edit");
			map.put("userDefined", userDefined);
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(	EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldReqList = providerService.selectConType(EAAPConstants.JAVA_FIELD_REQ);
			List<Map<String, Object>> javaFieldRspList = providerService.selectConType(EAAPConstants.JAVA_FIELD_RSP);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			
			//因Exception Code、Master Data还未实现，所以先屏蔽：
			for(int i=nevlConsTypeList.size()-1; -1<i; i--){ 
				Map<String, Object> nct = nevlConsTypeList.get(i);
				String cepName= nct.get("CEPNAME").toString();
				if(cepName.equals("Exception Code") || cepName.equals("Master Data")){
					nevlConsTypeList.remove(nct); 
				}
			}
			
			map.put("nevlConsTypeList", nevlConsTypeList);
			map.put("nodeNumberConsList", nodeNumberConsList);
			map.put("nodeTypeConsList", nodeTypeConsList);
			map.put("selectStateList", selectStateList);
			map.put("nodeTypeList", nodeTypeList);
			map.put("javaFieldReqList", javaFieldReqList);
			map.put("javaFieldRspList", javaFieldRspList);
			if(!"".equals(userDefined.getTechImpAttId())){
				List<Map<String,Object>> findContractFormat = providerService.queryContractFormat(userDefined.getTechImpAttId());
				if(null != findContractFormat && findContractFormat.size()>0){
					for(Map<String,Object> mapValue: findContractFormat){
						 if("REQ".equals(mapValue.get("REQRSP").toString())){
							 map.put("reqConType", mapValue.get("CONTYPE").toString());
							 map.put("reqHeadXsdFormat", mapValue.get("XSDHEADERFOR").toString());
							 map.put("reqXsdFormat", mapValue.get("XSDFORMAT").toString());
							 map.put("reqXsdSample", String.valueOf(mapValue.get("XSDDEMO").toString()));
							 map.put("reqDescript", String.valueOf(mapValue.get("DESCRIPTOR")));
							 map.put("reqTcpCrtFid", String.valueOf(mapValue.get("TCPCTRFID")));
							 List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
									 mapValue.get("TCPCTRFID").toString());
							 /*for(Map<String, Object> nodeDesc:nodeDescList){
								 if("1".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "HEAD");
								 }else if("2".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "BODY");
								 }else if("4".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "URL");
								 }
							 }*/
							 map.put("reqNodeList", nodeDescList);
						 }else{
							 map.put("rspConType", mapValue.get("CONTYPE").toString());
							 map.put("rspHeadXsdFormat", mapValue.get("XSDHEADERFOR").toString());
							 map.put("rspXsdFormat", mapValue.get("XSDFORMAT").toString());
							 map.put("rspXsdSample", String.valueOf(mapValue.get("XSDDEMO").toString()));
							 map.put("rspDescript", String.valueOf(mapValue.get("DESCRIPTOR")));
							 map.put("rspTcpCrtFid", String.valueOf(mapValue.get("TCPCTRFID")));
							 List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
									 mapValue.get("TCPCTRFID").toString());
							 /*for(Map<String, Object> nodeDesc:nodeDescList){
								 if("1".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "HEAD");
								 }else if("2".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "BODY");
								 }else if("4".equals(nodeDesc.get("NODETYPE").toString())){
									 nodeDesc.put("NODETYPE", "URL");
								 }
							 }*/
							 map.put("rspNodeList", nodeDescList);
						 }
					}
				}
			}
		}else{
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(	EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldReqList = providerService.selectConType(EAAPConstants.JAVA_FIELD_REQ);
			List<Map<String, Object>> javaFieldRspList = providerService.selectConType(EAAPConstants.JAVA_FIELD_RSP);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			
			//因Exception Code、Master Data还未实现，所以先屏蔽：
			for(int i=nevlConsTypeList.size()-1; -1<i; i--){ 
				Map<String, Object> nct = nevlConsTypeList.get(i);
				String cepName= nct.get("CEPNAME").toString();
				if(cepName.equals("Exception Code") || cepName.equals("Master Data")){
					nevlConsTypeList.remove(nct); 
				}
			}
			
			map.put("nevlConsTypeList", nevlConsTypeList);
			map.put("nodeNumberConsList", nodeNumberConsList);
			map.put("nodeTypeConsList", nodeTypeConsList);
			map.put("selectStateList", selectStateList);
			map.put("nodeTypeList", nodeTypeList);
			map.put("javaFieldReqList", javaFieldReqList);
			map.put("javaFieldRspList", javaFieldRspList);
			map.put("doOperation", "Add");
		}
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.doc.serverName");
		messages.add("eaap.op2.portal.doc.crontactCode");
		messages.add("eaap.op2.portal.doc.crontactVersionCode");
		messages.add("eaap.op2.portal.doc.selectDirectory");
		messages.add("eaap.op2.portal.doc.selectCommProcd");
		messages.add("eaap.op2.portal.doc.serviceProvAddress");
		messages.add("eaap.op2.portal.doc.descr");
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.doc.request");
		messages.add("eaap.op2.portal.doc.response");
		messages.add("eaap.op2.portal.doc.selectProcdFormat");
		messages.add("eaap.op2.portal.doc.contractDocument");
		messages.add("eaap.op2.portal.doc.procdBodyFormat");
		messages.add("eaap.op2.portal.doc.message.import");
		messages.add("eaap.op2.portal.doc.procdDemo");
		messages.add("eaap.op2.portal.doc.nodeName");
		messages.add("eaap.op2.portal.doc.nodePath");
		messages.add("eaap.op2.portal.doc.nodeLengthCons");
		messages.add("eaap.op2.portal.doc.nodeTypeCons");
		messages.add("eaap.op2.portal.doc.nodeNumberCons");
		messages.add("eaap.op2.portal.doc.nevlConsType");
		messages.add("eaap.op2.portal.doc.isNeedCheck");
		messages.add("eaap.op2.portal.doc.operator");
		messages.add("eaap.op2.portal.doc.nevlConsValue");
		messages.add("eaap.op2.portal.price.delete");
		messages.add("eaap.op2.portal.doc.message.close");
		messages.add("eaap.op2.portal.doc.message.back");
		messages.add("eaap.op2.portal.doc.message.ok");
		messages.add("eaap.op2.portal.doc.message.success");
		messages.add("eaap.op2.portal.doc.message.importsuccess");
		messages.add("eaap.op2.portal.doc.message.deletesuccess");
		messages.add("eaap.op2.portal.doc.headFormat");
		messages.add("eaap.op2.portal.doc.nodeType");
		messages.add("eaap.op2.portal.doc.add");
		messages.add("eaap.op2.portal.doc.isPublic");
		messages.add("eaap.op2.portal.devmgr.cancel");
		messages.add("eaap.op2.portal.doc.directoryIsMust"); 
		messages.add("eaap.op2.portal.required"); 
		messages.add("eaap.op2.portal.doc.serviceAddressIsMust");
		messages.add("eaap.op2.portal.doc.applyabilityName");
		ModelAndView mv = new ModelAndView("customizedForm.jsp",map);
		if(null !=  state && !"".equals(state)){
			if("D".equals(state) ||  "B".equals(state) ||  "E".equals(state)||  "H".equals(state)){
				mv = new ModelAndView("customizedShowForm.jsp",map);
			}
			if(null !=  upState && "E".equals(upState) && "D".equals(state)){
				mv = new ModelAndView("customizedForm.jsp",map);		//升级中: state = 'D', upState = 'E'
			}
		}
		addTranslateMessage(mv, messages);
		return mv;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/mixItUpData", produces="application/json; charset=utf-8")
	@ResponseBody
	public String mixItUpData(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String componentName = getRequestValue(request, "componentName");
		Org org = (Org)request.getSession().getAttribute("userBean");
		String userId = org.getOrgId()+"";
		Map<String,String> map = new HashMap<String,String>() ; 
		if(null != componentName && !"".equals(componentName)){
			map.put("componentName", componentName);
		}
		map.put("userId", userId);
		List<Map<String, Object>> findApplyList = providerService.findApply(map);
		JSONObject json = new JSONObject();
		StringBuffer value =  new StringBuffer("");
		if(null != findApplyList && findApplyList.size()>0){
			for(Map<String,Object> mapValue : findApplyList){
				String sFileId = "";
				if(null != mapValue.get("SFILEID")){
					sFileId = mapValue.get("SFILEID").toString();
				}
				if(null != sFileId && !"".equals(sFileId)){
					value.append("<div class='col-md-3 col-sm-4 mix category_1' style='display: inline-block;'>  <div class='mix-inner'> <img class='img-responsive' src='../provider/readImg.shtml?sFileId="+sFileId+"' width='256' height='176' alt=''> <a href='../provider/manageSystem.shtml?componentId="+mapValue.get("COMPONENTID").toString()+"&state="+mapValue.get("STATE").toString()+"' class='s-mix-link'>VIEW MORE</a> <div class='mix-title'> <strong>"+mapValue.get("NANE").toString()+"</strong> <b>"+mapValue.get("STATE_TIME").toString()+"</b> </div></div></div>");
				}else{
					value.append("<div class='col-md-3 col-sm-4 mix category_1' style='display: inline-block;'>  <div class='mix-inner'> <img class='img-responsive' src='../resources/img/default.jpg' alt=''> <a href='../provider/manageSystem.shtml?componentId="+mapValue.get("COMPONENTID").toString()+"&state="+mapValue.get("STATE").toString()+"' class='s-mix-link'>VIEW MORE</a> <div class='mix-title'> <strong>"+mapValue.get("NANE").toString()+"</strong> <b>"+mapValue.get("STATE_TIME").toString()+"</b> </div></div></div>");
				}
			}
		}
	
		json.put("data", value.toString());
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/formOKSubmit")
	@ResponseBody
	public String formOKSubmit(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String contractVersionId = getRequestValue(request, "pageContractVersionId");
		String reqTcpCtrFId = getRequestValue(request, "pageReqTcpCtrFId");
		String rspTcpCtrFId = getRequestValue(request, "pageRspTcpCtrFId");
		//请求的相关操作
		String pageReqProFormat = getRequestValue(request, "pageReqProFormat");
		String pageReqBodyFormat = getRequestValue(request, "pageReqBodyFormat");
		String pageReqHeadFormat = getRequestValue(request, "pageReqHeadFormat");
		String pageReqSample = getRequestValue(request, "pageReqSample");
		String pageReqDescription = getRequestValue(request, "pageReqDescription");
		UserDefined userDefined = new UserDefined();
		userDefined.setContractVersionId(contractVersionId);
		userDefined.setConType(pageReqProFormat);
		userDefined.setXsdFormat(pageReqBodyFormat);
		userDefined.setXsdDemo(pageReqSample);
		userDefined.setDescriptor(pageReqDescription);
		userDefined.setReqRsp("REQ");
		userDefined.setXsdHeadFormat(pageReqHeadFormat);
		if(null != reqTcpCtrFId && !"".equals(reqTcpCtrFId)){
			userDefined.setTcpCtrFid(reqTcpCtrFId);
			providerService.updateContractFormat2(userDefined);
		}else{
			providerService.addContractFormat2(userDefined);
		}
		
		String[] nodeDescId = request.getParameterValues("nodeDescId");
		String[] nodeLength = request.getParameterValues("nodeLength");
		String[] nodeTypeCons = request.getParameterValues("nodeTypeCons");
		String[] nodeNumber = request.getParameterValues("nodeNumber");
		String[] nevlConsType = request.getParameterValues("nevlConsType");
		String[] isNeedCheck = request.getParameterValues("isNeedCheck");
		String[] nelConsValue = request.getParameterValues("nelConsValue");
		String[] javaField = request.getParameterValues("javaField");
		String[] nodeType = request.getParameterValues("nodeType");
		
		UserDefined uf = new UserDefined();
		if(null != nelConsValue && nelConsValue.length>0){
			uf.setNelConsValue(nelConsValue);
		}
		if(null != nevlConsType && nevlConsType.length>0){
			uf.setNevlConsType(nevlConsType);
		}
		if(null != nodeLength && nodeLength.length>0){
			uf.setNodeLength(nodeLength);
		}
		if(null != nodeNumber && nodeNumber.length>0){
			uf.setNodeNumber(nodeNumber);
		}
		if(null != nodeDescId && nodeDescId.length>0){
			uf.setNodeDescId(nodeDescId);
		}
		if(null !=nodeTypeCons && nodeTypeCons.length>0){
			uf.setNodeTypeCons(nodeTypeCons);
		}
		if(null !=  isNeedCheck && isNeedCheck.length>0){
			uf.setIsNeedCheck(isNeedCheck);
		}
		if(null !=  javaField && javaField.length>0){
			uf.setJavaField(javaField);
		}
		if(null !=  nodeType && nodeType.length>0){
			uf.setNodeType(nodeType);
		}
		providerService.updateNodeDesc(uf);
		//响应的相关操作
		String pageRspProFormat = getRequestValue(request, "pageRspProFormat");
		String pageRspBodyFormat = getRequestValue(request, "pageRspBodyFormat");
		String pageRspSample = getRequestValue(request, "pageRspSample");
		String pageRspDescription = getRequestValue(request, "pageRspDescription");
		UserDefined userDefined2 = new UserDefined();
		userDefined2.setContractVersionId(contractVersionId);
		userDefined2.setConType(pageRspProFormat);
		userDefined2.setXsdFormat(pageRspBodyFormat);
		userDefined2.setXsdDemo(pageRspSample);
		userDefined2.setDescriptor(pageRspDescription);
		userDefined2.setReqRsp("RSP");
		if(null != rspTcpCtrFId && !"".equals(rspTcpCtrFId)){
			userDefined2.setTcpCtrFid(rspTcpCtrFId);
			providerService.updateContractFormat2(userDefined2);
		}else{
			providerService.addContractFormat2(userDefined2);
		}
		
		String[] nodeDescIdRsp = request.getParameterValues("rspNodeDescId");
		String[] nodeLengthRsp = request.getParameterValues("rspNodeLength");
		String[] nodeTypeConsRsp = request.getParameterValues("rspNodeTypeCons");
		String[] nodeNumberRsp = request.getParameterValues("rspNodeNumber");
		String[] nevlConsTypeRsp = request.getParameterValues("rspNevlConsType");
		String[] isNeedCheckRsp = request.getParameterValues("rspIsNeedCheck");
		String[] nelConsValueRsp = request.getParameterValues("rspNelConsValue");
		String[] javaFieldRsp = request.getParameterValues("rspJavaField");
		String[] nodeTypeRsp = request.getParameterValues("rspNodeType");
		
		UserDefined ufRsp = new UserDefined();
		if(null != nelConsValueRsp && nelConsValueRsp.length>0){ 
			ufRsp.setNelConsValue(nelConsValueRsp);
		}
		if(null != nevlConsTypeRsp && nevlConsTypeRsp.length>0){ 
			ufRsp.setNevlConsType(nevlConsTypeRsp);
		}
		if(null != nodeLengthRsp && nodeLengthRsp.length>0){ 
			ufRsp.setNodeLength(nodeLengthRsp);
		}
		if(null != nodeNumberRsp && nodeNumberRsp.length>0){ 
			ufRsp.setNodeNumber(nodeNumberRsp);
		}
		if(null != nodeDescIdRsp && nodeDescIdRsp.length>0){ 
			ufRsp.setNodeDescId(nodeDescIdRsp);
		}
		if(null != nodeTypeConsRsp && nodeTypeConsRsp.length>0){ 
			ufRsp.setNodeTypeCons(nodeTypeConsRsp);
		}
		if(null != isNeedCheckRsp && isNeedCheckRsp.length>0){ 
			ufRsp.setIsNeedCheck(isNeedCheckRsp);
		}
		if(null !=  javaFieldRsp && javaFieldRsp.length>0){
			ufRsp.setJavaField(javaFieldRsp);
		}
		if(null !=  nodeTypeRsp && nodeTypeRsp.length>0){
			ufRsp.setNodeType(nodeTypeRsp);
		}
		providerService.updateNodeDesc(ufRsp);
		
		JSONObject json = new JSONObject();
		StringBuffer value =  new StringBuffer("");
		json.put("data", value.toString());
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/operatorApility")
	@ResponseBody
	public String operatorApility(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String operator = getRequestValue(request, "operator");
		String techimpAttId = getRequestValue(request, "techimpAttId");
		String componentId = getRequestValue(request, "componentId");
		String serviceId = getRequestValue(request, "serviceId");
		String  attrSpecValue = getRequestValue(request, "attrSpecValue");
		String state = getRequestValue(request, "state");
		JSONObject json = new JSONObject();
		if("Update".equals(operator)){
			TechImpAtt techImpAtt = new TechImpAtt();
			techImpAtt.setTechImpAttId(Integer.valueOf(techimpAttId));
			techImpAtt.setAttrSpecValue(attrSpecValue);
			providerService.editAbility(techImpAtt);
			json.put(RETURN_CODE, RESULT_OK); 
			json.put("result", "Update Success");
		}else{
			WorkTaskConf workTaskConf=providerService.queryWorkTaskConf(componentId,serviceId);
			ServiceProductRela serviceProductRela=providerService.queryServiceProductRela(componentId,serviceId);
			if(workTaskConf!=null || serviceProductRela!=null){
				json.put(RETURN_CODE, RESULT_ERR); 
				json.put("result", "the service has been used,you can not delete it ");
			}else{
				providerService.operatorSerTechImpl(techimpAttId,state,componentId);
				json.put(RETURN_CODE, RESULT_OK); 
				json.put("result", "Delete Success");
			}
		}
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/readImg")
	@ResponseBody
	public String readImg(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		try {
			java.sql.Blob blob = null;
			byte[] byteArr = null;
			List<Map<String, Object>> fileShareList = providerService.selectFileShare(getRequestValue(request, "sFileId"));
			if (fileShareList != null) {
				for (Map<String, Object> item : fileShareList) {
					if (item.get("SFILECONTENT").getClass().getName()
							.equals("oracle.sql.BLOB")) {
						blob = (Blob) item.get("SFILECONTENT");
						int len = (int) blob.length();
						byteArr = blob.getBytes(1, len);
					}
					if (item.get("SFILECONTENT").getClass().getName()
							.equals("[B")) {
						byteArr = (byte[]) item.get("SFILECONTENT");
					}
					response.setContentType("image/jpeg");
					OutputStream ops = response.getOutputStream();
					if(byteArr!=null){
						for (int i = 0; i < byteArr.length; i++) {
							ops.write(byteArr[i]);
						}
					}
					ops.flush();
					ops.close();
				}
			}
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/addSystem")
	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	@ResponseBody
	public String addSystem(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageActionType = getRequestValue(request, "pageActionType");
		String systemName = getRequestValue(request, "systemName");
		String systemDetails = getRequestValue(request, "systemDetails");
		String sFileId = getRequestValue(request, "sFileId");
		Org org = (Org)request.getSession().getAttribute("userBean");
		String componentId = "";
		Component component = new Component();
		component.setName(systemName);
		component.setDescriptor(systemDetails);
		component.setOrgId(org.getOrgId());
		if(null !=sFileId && !"".equals(sFileId)){
			component.setSfileId(Integer.valueOf(sFileId));
		}
		if(null != getRequestValue(request, "componentId") && !"".equals(getRequestValue(request, "componentId"))){
			componentId = getRequestValue(request, "componentId");
			component.setComponentId(componentId);
		}
		JSONObject json=validateWithException(component);
		String code = String.valueOf(json.get("code"));
		if(BaseController.RESULT_OK.equals(code)){//说明验证通过
			if("add".equals(pageActionType)){
				String sysCode = providerService.getComponentSeq();
				component.setCode(sysCode);
				componentId = providerService.saveOrUpdate(pageActionType,component);
				json.put("componentId", componentId);
			}else{
				componentId = providerService.saveOrUpdate(pageActionType,component);
				json.put("componentId", componentId);
			}
		}
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/addProvSev")
//	@SavePermission(center="prov",module="component",parameterKey="componentId",privilege="")
	@ResponseBody
	public String addProvSev(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String componentId = getRequestValue(request, "componentId");
		String pageServiceName = getRequestValue(request, "pageServiceName");
		String pageProCode = getRequestValue(request, "pageProCode");
		String pageProVersionCode = getRequestValue(request, "pageProVersionCode");
		String pageSelectedDir = getRequestValue(request, "pageSelectedDir");
		String publicPageDirSerlistId = getRequestValue(request, "publicPageSelectedDir");
		String isPublic = getRequestValue(request, "isPublic");
		
		String pageCommPro = getRequestValue(request, "pageCommPro");
		String pageServiceAdd = getRequestValue(request, "pageServiceAdd");
		String pageDescription = getRequestValue(request, "pageDescription");
		String[] dyAttrSpecIds = getRequestValues(request, "dyAttrSpecId");
		String[] dyAttrSpecValues = getRequestValues(request, "dyAttrSpecValue");
		//修改操作参数
		String pageContractId = getRequestValue(request, "pageContractId");

		String pageDirSerlistId = getRequestValue(request, "pageDirSerlistId");
		String pageServiceId = getRequestValue(request, "pageServiceId");
		String pageTechImpAttId = getRequestValue(request, "pageTechImpAttId");
		String pageTechimplId = getRequestValue(request, "pageTechimplId");
		String pageContractVersionId = getRequestValue(request, "pageContractVersionId");
    	
		UserDefined userDefined = new UserDefined();
		userDefined.setComponentId(componentId);
		userDefined.setServiceName(pageServiceName);
		userDefined.setContractCode(pageProCode);
		userDefined.setContractVersionCode(pageProVersionCode);
		userDefined.setDirectoryId(pageSelectedDir);
		userDefined.setPublicDirectoryId(publicPageDirSerlistId);
		userDefined.setIsPublic(isPublic);
		userDefined.setCommProcd(pageCommPro);
		userDefined.setAttrSpecValue(pageServiceAdd);
		userDefined.setDescriptor(pageDescription);
		userDefined.setDyAttrSpecIds(dyAttrSpecIds);
		userDefined.setDyAttrSpecValues(dyAttrSpecValues);
		//修改操作参数
		userDefined.setContractId(pageContractId);
		userDefined.setDirSerlistId(pageDirSerlistId);
		userDefined.setServiceId(pageServiceId);
		userDefined.setTechImpAttId(pageTechImpAttId);
		userDefined.setTechImpId(pageTechimplId);
		userDefined.setContractVersionId(pageContractVersionId);
		
		JSONObject json=validateWithException(userDefined);	//验证输入是否符合规范
		String code = String.valueOf(json.get("code"));
		if(BaseController.RESULT_OK.equals(code)){//说明验证通过
			if(null != pageContractId && !"".equals(pageContractId)){//修改操作
				providerService.updateUserDefined(userDefined,json);
				json.put("contractId", pageContractId);
				//json.put("dirSerlistId", pageDirSerlistId);
				json.put("serviceId", pageServiceId);
				json.put("techImpAttId", pageTechImpAttId);
				json.put("techimplId", pageTechimplId);
				json.put("contractVersionId",pageContractVersionId);
				
				List<Map<String,Object>> findContractFormat = providerService.queryContractFormat(pageTechImpAttId);
				if(null != findContractFormat && findContractFormat.size()>0){
					for(Map<String,Object> mapValue: findContractFormat){
						 if("REQ".equals(mapValue.get("REQRSP").toString())){
							json.put("reqTcpCtrFId", String.valueOf(mapValue.get("TCPCTRFID")));
						 }else if("RSP".equals(mapValue.get("REQRSP").toString())){
							json.put("rspTcpCtrFId", String.valueOf(mapValue.get("TCPCTRFID")));
						 }
					}
				}
			}else{//添加操作
				boolean flagCode = providerService.checkCode(pageProCode);
				if(!flagCode){
					boolean flagVersion = providerService.checkVersion(pageProVersionCode);
					if(!flagVersion){
						Map<String,String> mapResult = providerService.addUserDefined(userDefined);
						json.put("contractId", mapResult.get("contractId"));
						json.put("dirSerlistId", mapResult.get("dirSerlistId"));
						json.put("serviceId", mapResult.get("serviceId"));
						json.put("techImpAttId", mapResult.get("techImpAttId"));
						json.put("techimplId", mapResult.get("techimplId"));
						json.put("contractVersionId", mapResult.get("contractVersionId"));
						
						userDefined.setContractVersionId(mapResult.get("contractVersionId"));
						userDefined.setConType("1");
						userDefined.setReqRsp("REQ");
						Integer reqTcpCtrFId = providerService.addContractFormat(userDefined);
						json.put("reqTcpCtrFId", reqTcpCtrFId);
						userDefined.setReqRsp("RSP");
						Integer rspTcpCtrFId = providerService.addContractFormat(userDefined);
						json.put("rspTcpCtrFId", rspTcpCtrFId);
					}else{
						json.put(RETURN_CODE, RESULT_Exit);
						json.put("result", "ContractVersion Code is Exits");
					}
				}else{
					json.put(RETURN_CODE, RESULT_Exit);
					json.put("result", "Contract Code is Exits");
				}
			}
		}
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/addConFormat")
	@ResponseBody
	public String addConFormat(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json=new JSONObject();
		Integer tcpCtrFId = 0;
		String pageContractVersionId = getRequestValue(request, "pageContractVersionId");
		String pageReqProFormat = getRequestValue(request, "pageReqProFormat");
		String pageReqBodyFormat = getRequestValue(request, "pageReqBodyFormat");//
		String pageReqHeadFormat = getRequestValue(request, "pageReqHeadFormat");
		String pageReqSample = getRequestValue(request, "pageReqSample");
		String pageReqDescription = getRequestValue(request, "pageReqDescription");
		String pageReqRsp = getRequestValue(request, "pageReqRsp");
		String pageTcpCtrFId = getRequestValue(request, "pageTcpCtrFId");
		UserDefined userDefined = new UserDefined();
		userDefined.setContractVersionId(pageContractVersionId);
		userDefined.setConType(pageReqProFormat);
		userDefined.setXsdFormat(pageReqBodyFormat);
		userDefined.setXsdHeadFormat(pageReqHeadFormat);
		userDefined.setXsdDemo(pageReqSample);
		userDefined.setDescriptor(pageReqDescription);
		userDefined.setReqRsp(pageReqRsp);
		if(null != pageTcpCtrFId && !"".equals(pageTcpCtrFId)){
			userDefined.setTcpCtrFid(pageTcpCtrFId);
			tcpCtrFId = providerService.updateContractFormat(userDefined);
		}else{
			tcpCtrFId = providerService.addContractFormat(userDefined);
		}
		StringBuffer sb = new StringBuffer("");
		if (tcpCtrFId != null) {
			List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
					tcpCtrFId.toString());
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldReqList = providerService.selectConType(EAAPConstants.JAVA_FIELD_REQ);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			if(null != nodeDescList && nodeDescList.size()>0){
				for(Map<String, Object> mapValue : nodeDescList){
					sb.append("<tr>");
					sb.append("<td>"+mapValue.get("NODENAME").toString()+"<input id='nodeDescId' name='nodeDescId' type='hidden' value='"+mapValue.get("NODEDESCID").toString()+"'/></td>");
					sb.append("<td>"+mapValue.get("NODEPATH").toString()+"</td>");
					
					/*
					if("1".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>HEAD</td>");
					}else if("2".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>BODY</td>");
					}else if("4".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>URL</td>");
					}
					*/
					sb.append("<td><select name='nodeType' id='nodeType' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeList){
						if(null !=  mapValue.get("NODETYPE") && !"".equals(mapValue.get("NODETYPE").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPE").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");

					String nodeLength = (mapValue.get("NODELENGTHCONS")==null?"":mapValue.get("NODELENGTHCONS").toString());
					sb.append("<td><input type='text' class='form-control'  id='nodeLength'  name='nodeLength'  value='"+nodeLength+"'></td>");
					sb.append("<td><select name='nodeTypeCons' id='nodeTypeCons' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeConsList){
						if(null !=  mapValue.get("NODETYPECONS") && !"".equals(mapValue.get("NODETYPECONS").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPECONS").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='nodeNumber' id='nodeNumber' class='form-control'>");
					for(Map<String, Object> mapNum :nodeNumberConsList ){
						if(null != mapValue.get("NODENUMBERCONS") && !"".equals(mapValue.get("NODENUMBERCONS").toString())){
							if(mapNum.get("CEPVALUES").toString().equals(mapValue.get("NODENUMBERCONS").toString())){
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"' selected>"+mapNum.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='nevlConsType' id='nevlConsType' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapCons : nevlConsTypeList){
						if(null !=  mapValue.get("NEVLCONSTYPE") && !"".equals(mapValue.get("NEVLCONSTYPE").toString())){
							if(mapCons.get("CEPVALUES").toString().equals(mapValue.get("NEVLCONSTYPE").toString())){
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"' selected>"+mapCons.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='isNeedCheck' id='isNeedCheck' class='form-control'>");
					for(Map<String,String> mapNY : selectStateList){
						if(null != mapValue.get("ISNEEDCHECK") && !"".equals(mapValue.get("ISNEEDCHECK").toString())){
							if(mapNY.get("CODE").equals(mapValue.get("ISNEEDCHECK").toString())){
								sb.append("<option value='"+mapNY.get("CODE")+"' selected>"+mapNY.get("NAME")+"</option>");
							}else{
								sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
						}
					}
					sb.append("</select></td>");
					
					String nelConsValue = (mapValue.get("NEVLCONSVALUE")==null?"":mapValue.get("NEVLCONSVALUE").toString());
					sb.append("<td><input type='text' name='nelConsValue' class='form-control'  value='"+nelConsValue+"'></td>");
					
					sb.append("<td><select name='javaField' id='javaField' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapType : javaFieldReqList){
						if(null !=  mapValue.get("JAVAFIELD") && !"".equals(mapValue.get("JAVAFIELD").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("JAVAFIELD").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					
					//sb.append("<td><input type='button' id='reqDelete' delId='"+mapValue.get("NODEDESCID").toString()+"' value='"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"' class='form-control'></td>");  
					sb.append("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"reqDelete\"  delId=\""+mapValue.get("NODEDESCID").toString()+"\" class=\"btn default btn-xs\">"+getI18nMessage("eaap.op2.portal.price.delete")+"</a></td></td>"); 
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put("hidTcpCtrFId", tcpCtrFId.toString());
			json.put("result", sb.toString());
		}
		return json.toString();
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/addNodeDesc")
	@ResponseBody
	public String addNodeDesc(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String nodeName = getRequestValue(request, "nodeName");
		String nodePath = getRequestValue(request, "nodePath");
		String nodeType = getRequestValue(request, "nodeType");
		String nodeLengthCons = getRequestValue(request, "nodeLengthCons");
		String nodeTypeCons = getRequestValue(request, "nodeTypeCons");	//
		String nodeNumber = getRequestValue(request, "nodeNumber");
		String nevlConsType = getRequestValue(request, "nevlConsType");
		String isNeedCheck = getRequestValue(request, "isNeedCheck");
		String nevlConsValue = getRequestValue(request, "nevlConsValue");
		String javaField = getRequestValue(request, "javaField");
		String tcpCtrFId = getRequestValue(request, "tcpCtrFId");
		
		NodeDesc nodeDesc = new NodeDesc();
		nodeDesc.setNodeName(nodeName);
		nodeDesc.setNodeCode(nodeName);
		nodeDesc.setNodePath(nodePath);
		nodeDesc.setNodeType(nodeType);
		nodeDesc.setNodeLengthCons(nodeLengthCons);
		nodeDesc.setNodeTypeCons(nodeTypeCons);
		nodeDesc.setNodeNumberCons(nodeNumber);
		nodeDesc.setNevlConsType(nevlConsType);
		nodeDesc.setIsNeedCheck(isNeedCheck);
		nodeDesc.setNevlConsValue(nevlConsValue);
		nodeDesc.setJavaField(javaField);
		nodeDesc.setTcpCtrFId(Integer.valueOf(tcpCtrFId));
		nodeDesc.setIsNeedSign("N");
		nodeDesc.setState("A");
		Integer nodeDescId = providerService.addNodeDesc(nodeDesc);
		
		JSONObject json=new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		json.put("nodeDescId", nodeDescId.toString());
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/addRspConFormat")
	@ResponseBody
	public String addRspConFormat(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json=new JSONObject();
		Integer tcpCtrFId = 0;
		String pageContractVersionId = getRequestValue(request, "pageContractVersionId");
		String pageRspProFormat = getRequestValue(request, "pageRspProFormat");
		String pageRspBodyFormat = getRequestValue(request, "pageRspBodyFormat");
		String pageRspHeadFormat = getRequestValue(request, "pageRspHeadFormat");
		String pageRspSample = getRequestValue(request, "pageRspSample");
		String pageRspDescription = getRequestValue(request, "pageRspDescription");
		String pageReqRsp = getRequestValue(request, "pageReqRsp");
		String pageTcpCtrFId = getRequestValue(request, "pageTcpCtrFId");
		UserDefined userDefined = new UserDefined();
		userDefined.setContractVersionId(pageContractVersionId);
		userDefined.setConType(pageRspProFormat);
		userDefined.setXsdFormat(pageRspBodyFormat);
		userDefined.setXsdDemo(pageRspSample);
		userDefined.setDescriptor(pageRspDescription);
		userDefined.setReqRsp(pageReqRsp);
		userDefined.setXsdHeadFormat(pageRspHeadFormat);
		if(null != pageTcpCtrFId && !"".equals(pageTcpCtrFId)){
			userDefined.setTcpCtrFid(pageTcpCtrFId);
			tcpCtrFId = providerService.updateContractFormat(userDefined);
		}else{
			tcpCtrFId = providerService.addContractFormat(userDefined);
		}
		StringBuffer sb = new StringBuffer("");
		if (tcpCtrFId != null) {
			List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
					tcpCtrFId.toString());
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(
					EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(
					EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(
					EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldRspList = providerService.selectConType(EAAPConstants.JAVA_FIELD_RSP);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			if(null != nodeDescList && nodeDescList.size()>0){
				for(Map<String, Object> mapValue : nodeDescList){
					sb.append("<tr>");
					sb.append("<td>"+mapValue.get("NODENAME").toString()+"<input id='rspNodeDescId' name='rspNodeDescId' type='hidden' value='"+mapValue.get("NODEDESCID").toString()+"'/></td>");
					sb.append("<td>"+mapValue.get("NODEPATH").toString()+"</td>");
					
					/*
					if("1".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>HEAD</td>");
					}else if("2".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>BODY</td>");
					}else if("4".equals(mapValue.get("NODETYPE").toString())){
						sb.append("<td>URL</td>");
					}
					*/
					sb.append("<td><select name='rspNodeType' id='rspNodeType' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeList){
						if(null !=  mapValue.get("NODETYPE") && !"".equals(mapValue.get("NODETYPE").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPE").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");

					String nodeLength = (mapValue.get("NODELENGTHCONS")==null?"":mapValue.get("NODELENGTHCONS").toString());
					sb.append("<td><input type='text' class='form-control'  id='rspNodeLength'  name='rspNodeLength'  value='"+nodeLength+"'></td>");
					sb.append("<td><select name='rspNodeTypeCons' id='rspNodeTypeCons' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeConsList){
						if(null !=  mapValue.get("NODETYPECONS") && !"".equals(mapValue.get("NODETYPECONS").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPECONS").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspNodeNumber' id='rspNodeNumber' class='form-control'>");
					for(Map<String, Object> mapNum :nodeNumberConsList ){
						if(null != mapValue.get("NODENUMBERCONS") && !"".equals(mapValue.get("NODENUMBERCONS").toString())){
							if(mapNum.get("CEPVALUES").toString().equals(mapValue.get("NODENUMBERCONS").toString())){
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"' selected>"+mapNum.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspNevlConsType' id='rspNevlConsType' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapCons : nevlConsTypeList){
						if(null !=  mapValue.get("NEVLCONSTYPE") && !"".equals(mapValue.get("NEVLCONSTYPE").toString())){
							if(mapCons.get("CEPVALUES").toString().equals(mapValue.get("NEVLCONSTYPE").toString())){
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"' selected>"+mapCons.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspIsNeedCheck' id='rspIsNeedCheck' class='form-control'>");
					for(Map<String,String> mapNY : selectStateList){
						if(null != mapValue.get("ISNEEDCHECK") && !"".equals(mapValue.get("ISNEEDCHECK").toString())){
							if(mapNY.get("CODE").equals(mapValue.get("ISNEEDCHECK").toString())){
								sb.append("<option value='"+mapNY.get("CODE")+"' selected>"+mapNY.get("NAME")+"</option>");
							}else{
								sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
						}
					}
					sb.append("</select></td>");
					String nelConsValue = (mapValue.get("NEVLCONSVALUE")==null?"":mapValue.get("NEVLCONSVALUE").toString());
					sb.append("<td><input type='text' name='rspNelConsValue' class='form-control'  value='"+nelConsValue+"'></td>");
					
					sb.append("<td><select name='rspJavaField' id='rspJavaField' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapType : javaFieldRspList){
						if(null !=  mapValue.get("JAVAFIELD") && !"".equals(mapValue.get("JAVAFIELD").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("JAVAFIELD").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					
					//sb.append("<td><input type='button' id='rspDelete' delId='"+mapValue.get("NODEDESCID").toString()+"' value='"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"' class='form-control'></td>");
					sb.append("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"rspDelete\"  delId=\""+mapValue.get("NODEDESCID").toString()+"\" class=\"btn default btn-xs\">"+getI18nMessage("eaap.op2.portal.price.delete")+"</a></td></td>"); 
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put("hidTcpCtrFId", tcpCtrFId.toString());
			json.put("result", sb.toString());
		}
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/deleteDescNode")
	@ResponseBody
	public String deleteDescNode(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String tcpCtrFId = getRequestValue(request, "pageTcpCtrFId");
		String pageNodeDescId = getRequestValue(request, "pageNodeDescId");
		providerService.deleteNodeDesc(pageNodeDescId);
		StringBuffer sb = new StringBuffer("");
		JSONObject json=new JSONObject();
		if (tcpCtrFId != null) {
			List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
					tcpCtrFId);
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(
					EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(
					EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(
					EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldReqList = providerService.selectConType(EAAPConstants.JAVA_FIELD_REQ);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			if(null != nodeDescList && nodeDescList.size()>0){
				for(Map<String, Object> mapValue : nodeDescList){
					sb.append("<tr>");
					sb.append("<td>"+mapValue.get("NODENAME").toString()+"<input id='nodeDescId' name='nodeDescId' type='hidden' value='"+mapValue.get("NODEDESCID").toString()+"'/></td>");
					sb.append("<td>"+mapValue.get("NODEPATH").toString()+"</td>");

					sb.append("<td><select name='nodeType' id='nodeType' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeList){
						if(null !=  mapValue.get("NODETYPE") && !"".equals(mapValue.get("NODETYPE").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPE").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");

					String nodeLength = (mapValue.get("NODELENGTHCONS")==null?"":mapValue.get("NODELENGTHCONS").toString());
					sb.append("<td><input type='text' class='form-control'  id='nodeLength'  name='nodeLength'  value='"+nodeLength+"'></td>");
					sb.append("<td><select name='nodeTypeCons' id='' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeConsList){
						if(null !=  mapValue.get("NODETYPECONS") && !"".equals(mapValue.get("NODETYPECONS").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPECONS").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='nodeNumber' id='nodeNumber' class='form-control'>");
					for(Map<String, Object> mapNum :nodeNumberConsList ){
						if(null != mapValue.get("NODENUMBERCONS") && !"".equals(mapValue.get("NODENUMBERCONS").toString())){
							if(mapNum.get("CEPVALUES").toString().equals(mapValue.get("NODENUMBERCONS").toString())){
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"' selected>"+mapNum.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='nevlConsType' id='nevlConsType' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapCons : nevlConsTypeList){
						if(null !=  mapValue.get("NEVLCONSTYPE") && !"".equals(mapValue.get("NEVLCONSTYPE").toString())){
							if(mapCons.get("CEPVALUES").toString().equals(mapValue.get("NEVLCONSTYPE").toString())){
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"' selected>"+mapCons.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='isNeedCheck' id='isNeedCheck' class='form-control'>");
					for(Map<String,String> mapNY : selectStateList){
						if(null != mapValue.get("ISNEEDCHECK") && !"".equals(mapValue.get("ISNEEDCHECK").toString())){
							if(mapNY.get("CODE").equals(mapValue.get("ISNEEDCHECK").toString())){
								sb.append("<option value='"+mapNY.get("CODE")+"' selected>"+mapNY.get("NAME")+"</option>");
							}else{
								sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
						}
					}
					sb.append("</select></td>");
					String nelConsValue = (mapValue.get("NEVLCONSVALUE")==null?"":mapValue.get("NEVLCONSVALUE").toString());
					sb.append("<td><input type='text' name='nelConsValue' class='form-control'  value='"+nelConsValue+"'></td>");  
					
					sb.append("<td><select name='javaField' id='javaField' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapType : javaFieldReqList){
						if(null !=  mapValue.get("JAVAFIELD") && !"".equals(mapValue.get("JAVAFIELD").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("JAVAFIELD").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					
					//sb.append("<td><input type='button' id='reqDelete' delId='"+mapValue.get("NODEDESCID").toString()+"' value='"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"' class='form-control'></td>");
					sb.append("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"reqDelete\"  delId=\""+mapValue.get("NODEDESCID").toString()+"\" class=\"btn default btn-xs\">"+getI18nMessage("eaap.op2.portal.price.delete")+"</a></td></td>"); 
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put("result", sb.toString());
		}
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/deleteRspDescNode")
	@ResponseBody
	public String deleteRspDescNode(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String tcpCtrFId = getRequestValue(request, "pageTcpCtrFId");
		String pageNodeDescId = getRequestValue(request, "pageNodeDescId");
		providerService.deleteNodeDesc(pageNodeDescId);
		StringBuffer sb = new StringBuffer("");
		JSONObject json=new JSONObject();
		if (tcpCtrFId != null) {
			List<Map<String, Object>> nodeDescList = providerService.queryNodeDesc(
					tcpCtrFId);
			List<Map<String, Object>> nevlConsTypeList = providerService.selectConType(
					EAAPConstants.NEVLCONSTYPE);
			List<Map<String, Object>> nodeNumberConsList = providerService.selectConType(
					EAAPConstants.NODENUMBERCONS);
			List<Map<String, Object>> nodeTypeConsList = providerService.selectConType(
					EAAPConstants.NODETYPECONS);
			List<Map<String, Object>> nodeTypeList = providerService.selectConType(EAAPConstants.NODETYPE);
			List<Map<String, Object>> javaFieldRspList = providerService.selectConType(EAAPConstants.JAVA_FIELD_RSP);
			HashMap<String, String> serInvokeInsStateMap1 = new HashMap<String, String>();
			HashMap<String, String> serInvokeInsStateMap2 = new HashMap<String, String>();
			List<HashMap<String,String>> selectStateList = new ArrayList<HashMap<String,String>>();
			serInvokeInsStateMap1.put("NAME","Yes");
			serInvokeInsStateMap1.put("CODE", "Y");
			serInvokeInsStateMap2.put("NAME", "No");
			serInvokeInsStateMap2.put("CODE", "N");
			selectStateList.add(serInvokeInsStateMap1);
			selectStateList.add(serInvokeInsStateMap2);
			if(null != nodeDescList && nodeDescList.size()>0){
				for(Map<String, Object> mapValue : nodeDescList){
					sb.append("<tr>");
					sb.append("<td>"+mapValue.get("NODENAME").toString()+"<input id='rspNodeDescId' name='rspNodeDescId' type='hidden' value='"+mapValue.get("NODEDESCID").toString()+"'/></td>");
					sb.append("<td>"+mapValue.get("NODEPATH").toString()+"</td>");

					sb.append("<td><select name='rspNodeType' id='rspNodeType' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeList){
						if(null !=  mapValue.get("NODETYPE") && !"".equals(mapValue.get("NODETYPE").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPE").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");

					String nodeLength = (mapValue.get("NODELENGTHCONS")==null?"":mapValue.get("NODELENGTHCONS").toString());
					sb.append("<td><input type='text' class='form-control'  id='rspNodeLength'  name='rspNodeLength'  value='"+nodeLength+"'></td>");
					sb.append("<td><select name='rspNodeTypeCons' id='rspNodeTypeCons' class='form-control'>");
					for(Map<String, Object> mapType : nodeTypeConsList){
						if(null !=  mapValue.get("NODETYPECONS") && !"".equals(mapValue.get("NODETYPECONS").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("NODETYPECONS").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspNodeNumber' id='rspNodeNumber' class='form-control'>");
					for(Map<String, Object> mapNum :nodeNumberConsList ){
						if(null != mapValue.get("NODENUMBERCONS") && !"".equals(mapValue.get("NODENUMBERCONS").toString())){
							if(mapNum.get("CEPVALUES").toString().equals(mapValue.get("NODENUMBERCONS").toString())){
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"' selected>"+mapNum.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNum.get("CEPVALUES").toString()+"'>"+mapNum.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspNevlConsType' id='rspNevlConsType' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapCons : nevlConsTypeList){
						if(null !=  mapValue.get("NEVLCONSTYPE") && !"".equals(mapValue.get("NEVLCONSTYPE").toString())){
							if(mapCons.get("CEPVALUES").toString().equals(mapValue.get("NEVLCONSTYPE").toString())){
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"' selected>"+mapCons.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapCons.get("CEPVALUES").toString()+"'>"+mapCons.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					sb.append("<td><select name='rspIsNeedCheck' id='rspIsNeedCheck' class='form-control'>");
					for(Map<String,String> mapNY : selectStateList){
						if(null != mapValue.get("ISNEEDCHECK") && !"".equals(mapValue.get("ISNEEDCHECK").toString())){
							if(mapNY.get("CODE").equals(mapValue.get("ISNEEDCHECK").toString())){
								sb.append("<option value='"+mapNY.get("CODE")+"' selected>"+mapNY.get("NAME")+"</option>");
							}else{
								sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
							}
						}else{
							sb.append("<option value='"+mapNY.get("CODE")+"'>"+mapNY.get("NAME")+"</option>");
						}
					}
					sb.append("</select></td>");
					String nelConsValue = (mapValue.get("NEVLCONSVALUE")==null?"":mapValue.get("NEVLCONSVALUE").toString());
					sb.append("<td><input type='text' name='rspNelConsValue' class='form-control'  value='"+nelConsValue+"'></td>");
					
					sb.append("<td><select name='rspJavaField' id='rspJavaField' class='form-control'>");
					sb.append("<option value=''></option>");
					for(Map<String, Object> mapType : javaFieldRspList){
						if(null !=  mapValue.get("JAVAFIELD") && !"".equals(mapValue.get("JAVAFIELD").toString())){
							if(mapType.get("CEPVALUES").toString().equals(mapValue.get("JAVAFIELD").toString())){
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"' selected>"+mapType.get("CEPNAME").toString()+"</option>");
							}else{
								sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
							}
						}else{
							sb.append("<option value='"+mapType.get("CEPVALUES").toString()+"'>"+mapType.get("CEPNAME").toString()+"</option>");
						}
					}
					sb.append("</select></td>");
					
					//sb.append("<td><input type='button' id='rspDelete' delId='"+mapValue.get("NODEDESCID").toString()+"' value='"+getI18nMessage("eaap.op2.portal.pardDistribution.delete")+"' class='form-control'></td>");
					sb.append("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"rspDelete\"  delId=\""+mapValue.get("NODEDESCID").toString()+"\" class=\"btn default btn-xs\">"+getI18nMessage("eaap.op2.portal.price.delete")+"</a></td></td>"); 
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put("result", sb.toString());
		}
		return json.toString();
	}
	@RequestMapping(value = "/provider/showProperty")
	@ResponseBody
	public String showProperty(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageTechimplId = getRequestValue(request, "pageTechimplId");
		String pageCommPro = getRequestValue(request, "pageCommPro");
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("techImplId", pageTechimplId);
	    hashMap.put("commProCd", pageCommPro);
	    List<Map<String,Object>> list = providerService.queryTechImplAttrSpec(hashMap);
		StringBuffer sb = new StringBuffer("");
		JSONObject json=new JSONObject();
		if(null != list && list.size()>0){
			for(Map<String,Object>  paramValue : list){
				 String attrSpecName = (String)paramValue.get("ATTR_SPEC_NAME");
				 String attrSpecId = String.valueOf(paramValue.get("ATTR_SPEC_ID"));
				 String attrSpecValue = "";
				 if(null != paramValue.get("ATTR_SPEC_VALUE")){
					 attrSpecValue = (String)paramValue.get("ATTR_SPEC_VALUE");
				 }
				 sb.append("<div class='row'>");
				 sb.append("<div class='col-md-12'>");
				 sb.append("<div class='form-group'>");
				 if("1".equals(attrSpecId)){
					 sb.append("<font color='FF0000'>*</font><label class='control-label'>"+attrSpecName+"</label>");
				 }else{
					 sb.append("<label class='control-label'>"+attrSpecName+"</label>");
				 }
				 sb.append("<input type='hidden' class='form-control' name='dyAttrSpecId' value='"+attrSpecId+"'>");
				 if("1".equals(attrSpecId)){
					 sb.append("<input type='text' class='form-control' name='dyAttrSpecValue' id='serviceAddressValue' value='"+attrSpecValue+"'>");
				 }else{
					 sb.append("<input type='text' class='form-control' name='dyAttrSpecValue' value='"+attrSpecValue+"'>");
				 }
				 sb.append("</div></div></div>");
			}
		}
		json.put("property", sb.toString());
		return json.toString();
	}
	@RequestMapping(value = "/provider/updateNodeList")
	@ResponseBody
	public void updateNodeList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String[] nodeDescId = request.getParameterValues("nodeDescId");
		String[] nodeLength = request.getParameterValues("nodeLength");
		String[] nodeTypeCons = request.getParameterValues("nodeTypeCons");
		String[] nodeNumber = request.getParameterValues("nodeNumber");
		String[] nevlConsType = request.getParameterValues("nevlConsType");
		String[] isNeedCheck = request.getParameterValues("isNeedCheck");
		String[] nelConsValue = request.getParameterValues("nelConsValue");
		
		UserDefined uf = new UserDefined();
		uf.setNelConsValue(nelConsValue);
//		uf.setNevlConsDesc(nevlConsDesc);
		uf.setNevlConsType(nevlConsType);
		uf.setNodeLength(nodeLength);
		uf.setNodeNumber(nodeNumber);
		uf.setNodeDescId(nodeDescId);
		uf.setNodeTypeCons(nodeTypeCons);
		uf.setIsNeedCheck(isNeedCheck);
		providerService.updateNodeDesc(uf);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("<script>parent.showAlertResult();</script>") ;
			pw.flush();
		} catch (IOException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}finally{
			if(null != pw){
				pw.close() ;
			}
		}
	}

	@RequestMapping(value = "/provider/updateRspNodeList")
	@ResponseBody
	public void updateRspNodeList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String[] nodeDescId = request.getParameterValues("rspNodeDescId");
		String[] nodeLength = request.getParameterValues("rspNodeLength");
		String[] nodeTypeCons = request.getParameterValues("rspNodeTypeCons");
		String[] nodeNumber = request.getParameterValues("rspNodeNumber");
		String[] nevlConsType = request.getParameterValues("rspNevlConsType");
		String[] isNeedCheck = request.getParameterValues("rspIsNeedCheck");
		String[] nelConsValue = request.getParameterValues("rspNelConsValue");
		
		UserDefined uf = new UserDefined();
		uf.setNelConsValue(nelConsValue);
//		uf.setNevlConsDesc(nevlConsDesc);
		uf.setNevlConsType(nevlConsType);
		uf.setNodeLength(nodeLength);
		uf.setNodeNumber(nodeNumber);
		uf.setNodeDescId(nodeDescId);
		uf.setNodeTypeCons(nodeTypeCons);
		uf.setIsNeedCheck(isNeedCheck);
		providerService.updateNodeDesc(uf);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("<script>parent.showAlertResult();</script>") ;
			pw.flush();
		} catch (IOException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}finally{
			if(null != pw){
				pw.close() ;
			}
		}
	}
	
	  @RequestMapping(value = "/provider/uploadFile", method = RequestMethod.POST)  
		@ResponseBody
		public String uploadFile(@RequestParam(value = "file", required = false)MultipartFile file,HttpServletRequest request) {
		  StringBuilder sb = new StringBuilder("");    
		  String line = null;   
		  BufferedReader reader =null;
	        try {  
	        	reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8")); 
	        	while ((line = reader.readLine()) != null) {
	                sb.append(line+"\n");
	            } 
	        } catch (Exception e) { 
	        	String errorMsg = CommonUtil.getErrMsg(e);
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"upload exception:" + errorMsg,e));
	        } finally{
	        	if(reader!=null)
					try {
						reader.close();
					} catch (IOException e) {
						log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"close exception:" + CommonUtil.getErrMsg(e),e));
					}
	        }
			return sb.toString();
		}  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/provAddAbility")
	@ResponseBody
	public String provAddAbility(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String componentId = getRequestValue(request, "componentId");
		String serviceID = getRequestValue(request, "serviceID");
		String attrValue = getRequestValue(request, "attrID");
		Map<String,String> map = new HashMap<String,String>();
		map.put("service", serviceID);
		map.put("attrspecvalue", attrValue);
		map.put("componentId", componentId);
		map.put("commProCd", "1");
		providerService.addTechImpAtt(map);
		JSONObject json=new JSONObject();
		json.put(RETURN_CODE, RESULT_OK);
		return json.toString();
	}
	@RequestMapping(value = "/provider/isExit")
	@ResponseBody
	public String isExit(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String systemCode = getRequestValue(request, "systemCode");
		if(null != systemCode && !"".equals(systemCode)){
			boolean flag = providerService.isExitCode(systemCode);
			if(flag){
				return "false";
			}else{
				return "true";
			}
		}else{
			return "false";
		}
	}
	
	@RequestMapping(value = "/provider/provPackage")
	@ResponseBody
	public String provPackage(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray subDataList=null;
		JSONArray dataList=new JSONArray();
		JSONObject json = new JSONObject();
		String componentId=getRequestValue(request, "componentId");
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}
		try{
			map.put("componentId", componentId);
			//map.put("applyabilityName", applyabilityName);
			List<Map<String, Object>> showAbilityList = providerService.showAbilityByName(map);
			String name = "";
			if(null != showAbilityList && showAbilityList.size() >0 ){
				for(Map pardSpecMap:showAbilityList){
					subDataList=new JSONArray();
					String cnName=MapUtils.getString(pardSpecMap, "CNNAME");
					cnName=StringUtils.replace(cnName, "'", "");
					cnName=StringUtils.replace(cnName, "\"", "");
					//cnName=StringUtils.replace(cnName, " ", "");
					name ="capabilityCheck(this,\'"+cnName+"\')";
					subDataList.add("<input type='checkbox' value='' class='checkboxes' onChange=\""+name+"\" id='"+pardSpecMap.get("SERVICEID")+"' name=''  apiName='"+cnName+"'/>");
		
					//			subDataList.add(pardSpecMap.get("PROD_OFFER_ID"));
					subDataList.add(pardSpecMap.get("DIRNAME"));
					subDataList.add(pardSpecMap.get("CNNAME"));
					subDataList.add(pardSpecMap.get("SERVICEDESC"));
					//subDataList.add(pardSpecMap.get("PRICING_NAME_S"));
					//subDataList.add("<a data-content='&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Popover title'>"+pardSpecMap.get("PRICING_NAME_S")+"</a>");
					//subDataList.add("<a data-content='"+pardSpecMap.get("TRstring")+"' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Popover title'>"+pardSpecMap.get("CNNAME")+"</a>");
					
					//subDataList.add(pardSpecMap.get("CNNAME"));
					dataList.add(subDataList);
				}
//				json.put(RETURN_CODE, RESULT_OK);
				json.put("draw", draw);
				json.put("recordsTotal",length);
				json.put("recordsFiltered", showAbilityList.size());
				json.put("data",dataList.toString());
			}else{
				json.put("draw", draw);
				json.put("recordsTotal",length);
				json.put("recordsFiltered", 0);
				json.put("data","");
			}
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString() ;
	}
	/*
     *创建 打包销售品
	 * 1.turn to Product 2.add Package 3.add PRICING_CASE 定价实例 get PricePlan and
	 * Calculation actual time by relative time
	 */
	@RequestMapping(value = "/provider/addOffer")
	@ResponseBody
	public String addOffer(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ProdOffer prodOffer = new ProdOffer();
		JSONObject json = new JSONObject();
		Org orgBean = getOrg(request);
		try{
		//Map session = ActionContext.getContext().getSession();
		//Org userBean = (Org) session.get("userBean");
		String componentId=getRequestValue(request, "componentIdforOffer");
		String packageName=getRequestValue(request, "offerName");
		String service = getRequestValue(request, "capabilityCheckId");
		prodOffer.setProdOfferName(packageName);
		prodOffer.setProdOfferPublisher(componentId);
		prodOffer.setStatusCd("1200");
		prodOffer.setCooperationType("13");
		// effDate,expDate
		Date efftime = new Date();
		String exptime = "2099-01-01";
		try {
			prodOffer.setEffDate(efftime);
			prodOffer.setExpDate("".equals(StringUtil.valueOf(prodOffer
					.getFormatExpDate())) ? null : DateUtil.stringToDate(
					prodOffer.getFormatExpDate().replace("-", "/"),
					"yyyy/MM/dd"));
		} catch (ParseException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		prodOffer.setOfferProviderId(StringUtil.valueOf(orgBean.getOrgId()));
		BigDecimal offId = providerService.insertProdOffer(prodOffer);
		prodOffer.setProdOfferId(offId);
		List services = providerService.queryService(service);

		String userId = StringUtil.valueOf(orgBean.getOrgId());
		if (services.size() > 0) {
			BigDecimal con;
			for (int i = 0; i < services.size(); i++) {
				Product pro = new Product();
				Map<String, Object> temp = (HashMap<String, Object>) services.get(i);
				/* 查看这个服务是否已经是产品 */
				con = providerService.getProductbyCap(temp);

				pro.setProductName(StringUtil.valueOf(temp.get("CNNAME")));
				pro.setProductNbr(StringUtil.valueOf(temp.get("SERVICEID")));
				pro.setProductProviderId(Integer.parseInt(userId));
				pro.setStatusCd("1200");
				pro.setCooperationType("13");
				BigDecimal newProuctId;
				if (null != con) {
					newProuctId = con;
				} else {

					newProuctId = providerService.insertProduct(pro);
					// 插入服务产品关系表SERVICE_PRODUCT_REAL
					temp.put("PRODUCT_ID", newProuctId);
					providerService.insertServiceProRel(temp);
				}
				OfferProdRel offerProdRel = new OfferProdRel();
				offerProdRel.setProductId(newProuctId);
				offerProdRel.setProdOfferId(offId);
				int relaId = providerService.insertOfferProdRel(
						offerProdRel);

			}
		}
		 // List<Map<String, Object>> showAbilityList; 
		 // List<Map<String, Object>> showPackageList;
		 // Map<String, Object> map = new HashMap<String, Object>();
		 // map.put("componentId", componentId);
		//showAbilityList = providerService.showAbility(map);
		//showPackageList = providerService.showPackage(componentId);
		json.put(RETURN_CODE, RESULT_OK);
		json.put("id", offId);
	} catch (Exception e) {
		json.put(RETURN_CODE, RESULT_ERR);
		json.put(RETURN_DESC,e.getMessage());
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
	}
	return json.toString() ;
	}
	/**
	 * delProPackage:删除包. <br/>
	 */
	@RequestMapping(value = "/provider/delOffer")
	@ResponseBody
	public String delOffer(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ProdOffer prodOffer = new ProdOffer();
		JSONObject json = new JSONObject();
		String offerId=getRequestValue(request, "offerId");
		
		try{
		prodOffer.setStatusCd("1300");
		prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(offerId)));
		providerService.updateProdOffer(prodOffer);
		
		json.put(RETURN_CODE, RESULT_OK);
		//json.put(key, value)
		//json.put("draw", draw);
		//json.put("recordsTotal",recordsTotal);
		//json.put("recordsFiltered", devMgrService.queryOffersInfoCount(paramMap));
		//json.put("data",dataList.toString());
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString() ;
	}
	
	
	/**
	 * API结算功能。
	 */
	@RequestMapping(value = "/provider/toSettle")
	public ModelAndView addSettle(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		messages.add("eaap.op2.portal.settlement.cycleType.detail");
		messages.add("eaap.op2.portal.settlement.priceType");
		messages.add("eaap.op2.portal.settlement.payDir.detail");
		messages.add("eaap.op2.portal.settlement.payDir");
		messages.add("eaap.op2.portal.settlement.chargeCondition.detail");
		messages.add("eaap.op2.portal.settlement.chargeCondition");
		messages.add("eaap.op2.portal.settlement.arrange");
		messages.add("eaap.op2.portal.settlement.ChargePercent");
		messages.add("eaap.op2.portal.settlement.baseCharge");
		messages.add("eaap.op2.portal.pardSpec.operation");
		messages.add("eaap.op2.portal.settlement.effAndExpDate");
		messages.add("eaap.op2.portal.settlement.cycleType");
		messages.add("eaap.op2.portal.settlement.description");
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.settlement.ruleName");
		messages.add("eaap.op2.portal.settlement.addSettle");
		messages.add("eaap.op2.portal.price.basic.endWith-1");
		messages.add("eaap.op2.portal.price.basic.greater");
		messages.add("eaap.op2.portal.price.basic.equal1");
		messages.add("eaap.op2.portal.price.basic.equal2");
		messages.add("eaap.op2.portal.settlement.ruleNameNull");
		messages.add("eaap.op2.portal.settlement.notEmpty");
		
		 
//		messages.add("");
//		messages.add("");
		addTranslateMessage(mv, messages);
		List<HashMap<String,String>> settleCycleTypeList = new ArrayList<HashMap<String,String>>();
		List<HashMap<String,String>> settleMoneyUnitList = new ArrayList<HashMap<String,String>>();
		List<HashMap<String,String>> settleChargeDirList = new ArrayList<HashMap<String,String>>();
		
		showSelectList(settleCycleTypeList, EAAPConstants.SETTLE_CYCLE_TYPE);
		showSelectList(settleMoneyUnitList, EAAPConstants.CURRENCY_UNIT_TYPE);
		showSelectList(settleChargeDirList, EAAPConstants.SETTLE_CHARGE_DIR);
		mv.addObject("settleCycleTypeList",settleCycleTypeList);
		mv.addObject("settleMoneyUnitList",settleMoneyUnitList);
		mv.addObject("settleChargeDirList",settleChargeDirList);
		String actionType = getRequestValue(request, "actionType");
		String servCode = getRequestValue(request, "prodOfferId");
		String ruleId = getRequestValue(request, "ruleId");
		String componentId = getRequestValue(request, "componentId");
		Map map = new HashMap();
		map.put("componentId", componentId);
		map.put("prodOfferId", servCode);
		List compL = providerService.queryComponentState(map);
		if(compL.size()>0){
			Map compM = (Map)compL.get(0);
			String state = (String)compM.get("STATE");
			if("B".equals(state)||"D".equals(state)){
				String UpState = (String)compM.get("UP_STATE");
				if("H".equals(UpState)){
					mv.addObject("state",state);
					mv.addObject("disabled","disabled");
				}else if("E".equals(UpState)||"F".equals(UpState)){
					mv.addObject("state",state);
					mv.addObject("disabled","");
				}else{
					mv.addObject("state",state);
					mv.addObject("disabled","disabled");
				}
			}else if("A".equals(state)||"C".equals(state)||"E".equals(state)||"F".equals(state)){
				mv.addObject("state",state);
				mv.addObject("disabled","");
			}
		}else{
			mv.addObject("state","");
			mv.addObject("disabled","disabled");
		}
		SettleRule settleRule = new SettleRule();
		//获取系统状态
		
		SettleRuleCLC settleRuleCLC =new SettleRuleCLC();
		SettleRuleCondition settleRuleCondition = new SettleRuleCondition();		
		List<SettleRuleCondition> ruleConditionList = new ArrayList<SettleRuleCondition>();

		if("update".equals(actionType)||"detail".equals(actionType)){
			
			settleRule.setRuleId(new BigDecimal(ruleId));
			settleRule = settleServ.querySettleRule(settleRule).get(0);

			settleRule.setForEffDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getEffDate(), "yyyy-MM-dd")) ;
			settleRule.setForExpDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getExpDate(), "yyyy-MM-dd")) ;
			settleRuleCondition.setRuleId(settleRule.getRuleId()); 
			settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			
			ruleConditionList = settleServ.querySettleRuleCondition(settleRuleCondition);
			settleRuleCLC.setRuleId(settleRule.getRuleId());
			settleRuleCLC = settleServ.querySettleRuleCLC(settleRuleCLC).get(0);
		 
		} 
		mv.addObject("settleRuleCondition", settleRuleCondition);
		mv.addObject("ruleConditionList", ruleConditionList);
		mv.addObject("settleRule",settleRule);
		mv.addObject("settleRuleCLC",settleRuleCLC);
		mv.addObject("servCode",servCode);
		mv.addObject("actionType",actionType);
		
		
		mv.setViewName("../provider/settleAdd.jsp");
		return mv; 
		
	}
	public void showSelectList(List<HashMap<String,String>> list ,String mdtSign){
		Map<String,String> map = getMainInfo(mdtSign);
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();   
		while(iter.hasNext()) {
			HashMap<String,String> mapTmp = new HashMap<String,String>() ;
			Entry<String,String> entry = (Entry<String,String>)iter.next();
			mapTmp.put("key", entry.getKey().toString());
			mapTmp.put("value", entry.getValue().toString()) ;
			list.add(mapTmp);
		}
	}
	
	
//	public String addAPIsettle(){
//		try{ 
//		String pro = prodOfferId;
//		String servN = prodOfferName;
//		String busiN = busiName;
//		settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
//		settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
//		
//		showSelectList(settleCycleTypeList, EAAPConstants.SETTLE_CYCLE_TYPE);
//		showSelectList(settleMoneyUnitList, EAAPConstants.CURRENCY_UNIT_TYPE);
//		showSelectList(settleChargeDirList, EAAPConstants.SETTLE_CHARGE_DIR);
//		System.out.println("------------------------------------------------------------------");
//		}catch(Exception e){
//			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
//		}
//		return SUCCESS;
//		
//		
//	}
	
//	public String goSettleAPI(){
//		try{ 
//			showSelectList(settleCycleTypeList, EAAPConstants.SETTLE_CYCLE_TYPE);
//			showSelectList(settleMoneyUnitList, EAAPConstants.CURRENCY_UNIT_TYPE);
//			showSelectList(settleChargeDirList, EAAPConstants.SETTLE_CHARGE_DIR);
//		 
//			//settleRule.setRuleId(new BigDecimal(ruleId));
//			settleRule = this.getSettlementServ().querySettleRule(settleRule).get(0);
////			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
////			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
//			settleRule.setForEffDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getEffDate(), "yyyy-MM-dd")) ;
//			settleRule.setForExpDate("".equals(StringUtil.valueOf(settleRule.getEffDate()))?null:DateUtil.convDateToString(settleRule.getExpDate(), "yyyy-MM-dd")) ;
//					
//			settleRuleCondition.setRuleId(settleRule.getRuleId()); 
//			settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
//			
//			ruleConditionList = this.getSettlementServ().querySettleRuleCondition(settleRuleCondition);
//			settleRuleCLC.setRuleId(settleRule.getRuleId());
//			settleRuleCLC = this.getSettlementServ().querySettleRuleCLC(settleRuleCLC).get(0);
//		 
//		}catch(Exception e){
//			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
//		}
//		return SUCCESS;
//		
//	}
	@RequestMapping(value = "/provider/saveOrUpdateAPI",method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdateAPI(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		BigDecimal ruleIdJson;
		try{
			String ruleConditionId = getRequestValue(request, "ruleConditionId"); 
			String[] ruleConditionIds = ruleConditionId.split(";");
			String upValue = getRequestValue(request, "upValue"); 
			String[] upValues = upValue.split(";");
			String downValue = getRequestValue(request, "downValue"); 
			String[] downValues=downValue.split(";");
			String ratioNumerator = getRequestValue(request, "ratioNumerator"); 
			String[] ratioNumerators =ratioNumerator.split(";");
			String ratioDemominator = getRequestValue(request, "ratioDemominator"); 
			String[] ratioDemominators=ratioDemominator.split(";");
			String baseValue = getRequestValue(request, "baseValue"); 
			String[] baseValues=baseValue.split(";");
			//Org orgBean = (Org) session.get("userBean");
			Org orgBean = getOrg(request); 
			
			SettleRule settleRule = new SettleRule();
			SettleRuleCLC settleRuleCLC =new SettleRuleCLC();
			SettleRuleCondition settleRuleCondition;
			String ruleIdx= getRequestValue(request, "settleRule.ruleId");
			String ruleName= getRequestValue(request, "settleRule.ruleName");
			String moneyUnit= getRequestValue(request, "settleRule.moneyUnit");
			String chargeDir= getRequestValue(request, "settleRule.chargeDir");
			String servCode= getRequestValue(request, "settleRule.servCode");
			String cycleType = getRequestValue(request, "settleRuleCLC.cycleType");
			String cycleCount = getRequestValue(request, "settleRuleCLC.cycleCount");
			String forEffDate = getRequestValue(request, "settleRule.forEffDate");
			String forExpDate = getRequestValue(request, "settleRule.forExpDate");
			String description = getRequestValue(request, "settleRule.description");
			//String cycleCount = getRequestValue(request, "cycleCount");

			settleRule.setRuleName(ruleName);
			settleRule.setMoneyUnit(Integer.parseInt(moneyUnit));
			settleRule.setChargeDir(Integer.parseInt(chargeDir));
			settleRule.setServCode(servCode);
			settleRule.setDescription(description);
			settleRule.setForEffDate(forEffDate);
			settleRule.setForExpDate(forExpDate);
			
			if(null==ruleIdx||"".equals(ruleIdx)){
				settleRule.setPartnerCode(orgBean.getOrgId().toString());
				settleRule.setRuleType(4);
				settleRule.setSyncFlag(1);
				settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
				settleRule.setPriority(Integer.parseInt(EAAPConstants.SETTLE_PRIORITY_NUM));
				settleRule.setEffDate("".equals(StringUtil.valueOf(forEffDate))?null:DateUtil.stringToDate(forEffDate.replace("-","/"), "yyyy/MM/dd")) ;
				settleRule.setExpDate("".equals(StringUtil.valueOf(forExpDate))?null:DateUtil.stringToDate(forExpDate.replace("-","/"), "yyyy/MM/dd")) ;
				BigDecimal ruleIdNew = settleServ.addSettleRule(settleRule);
				ruleIdJson = ruleIdNew;
				settleRuleCLC.setRuleId(ruleIdNew);
				settleRuleCLC.setCycleType(Integer.parseInt(cycleType));
				settleRuleCLC.setCycleCount(Integer.parseInt(cycleCount));
				//settleRuleOTC.setRuleId(ruleId);
				//settleRuleOTC.setUserTime(settleRule.getEffDate());
				BigDecimal ruleDetailId = settleServ.addSettleRuleCLC(settleRuleCLC);
				
				if(upValues.length>0){
					for(int i=0;i<upValues.length;i++){
						settleRuleCondition = new SettleRuleCondition();
						settleRuleCondition.setRuleDetailId(ruleDetailId);
						settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
						settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
						settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
						settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
						settleRuleCondition.setRatioDemominator(Integer.parseInt(ratioDemominators[i]));
						settleRuleCondition.setBaseValue(baseValues[i]);
						settleRuleCondition.setRuleId(ruleIdNew);
						settleServ.addSettleRuleCondition(settleRuleCondition);
					}
				}
			 
				
			}else{
				settleRule.setRuleId(new BigDecimal(ruleIdx));
				ruleIdJson =settleRule.getRuleId();
				settleRule.setSyncFlag(3);
				settleRule.setEffDate("".equals(StringUtil.valueOf(forEffDate))?null:DateUtil.stringToDate(forEffDate.replace("-","/"), "yyyy/MM/dd")) ;
				settleRule.setExpDate("".equals(StringUtil.valueOf(forExpDate))?null:DateUtil.stringToDate(forExpDate.replace("-","/"), "yyyy/MM/dd")) ;
				settleServ.updateSettleRule(settleRule);
				
				settleRuleCLC.setRuleId(settleRule.getRuleId());
				//settleRuleOTC.setUserTime(settleRule.getEffDate());
//				settleServ.updateSettleRuleCLC(settleRuleCLC);
				
				if(ruleConditionIds.length>0){
					for(int i=0;i<upValues.length;i++){
						settleRuleCondition = new SettleRuleCondition();
						settleRuleCondition.setRuleDetailId(new BigDecimal(123) );
						settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
						settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
						settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
						settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
						settleRuleCondition.setRatioDemominator(Integer.parseInt(ratioDemominators[i]));
						settleRuleCondition.setBaseValue(baseValues[i]);
						settleRuleCondition.setRuleId(settleRule.getRuleId());
						if(i>=ruleConditionIds.length||null==ruleConditionIds[i]||"".equals(ruleConditionIds[i])){
							settleServ.addSettleRuleCondition(settleRuleCondition);
						}else{
							settleRuleCondition.setRuleConditionId(new BigDecimal(ruleConditionIds[i]));
							settleServ.updateSettleRuleCondition(settleRuleCondition);
						}
					}
				}
			}
			
			//retMap.put("busiId", busiId);
		//	retMap.put("settleCycleDef", cycleDefId);
			json.put(RETURN_CODE, RESULT_OK);
			json.put("ruleId", ruleIdJson);
		//	json.put("id", offId);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString() ;
	}
	@RequestMapping(value = "/provider/delSettleAPI",method = RequestMethod.POST)
	@ResponseBody
	public String delSettleAPI(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		 
		try{
			 String servCode = getRequestValue(request, "servCode");
			 String sruleId =getRequestValue(request, "ruleId"); 
			 BigDecimal ruleId=new BigDecimal(sruleId);
			 Org orgBean = getOrg(request); 
				
			    SettleRule settleRule = new SettleRule();
			    settleRule.setPartnerCode(orgBean.getOrgId().toString());
				settleRule.setRuleId(ruleId);
				settleRule.setServCode(servCode);
				settleRule.setStatusCd("1300");
				settleRule.setSyncFlag(3);
				settleServ.delSettleRule(settleRule);
			 
				json.put(RETURN_CODE, RESULT_OK);
				 
			} catch (Exception e) {
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC,e.getMessage());
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
			}
			return json.toString() ;
	}
	@RequestMapping(value = "/provider/delCondition",method = RequestMethod.POST)
	@ResponseBody
	public String delCondition(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		 
		try{
			 String conditionId = getRequestValue(request, "conditionId");
			 String sruleId =getRequestValue(request, "ruleId"); 
			 BigDecimal ruleId=new BigDecimal(sruleId);
//			 Org orgBean = getOrg(request); 
			 SettleRuleCondition settleRuleCondition = new SettleRuleCondition();
			 settleRuleCondition.setRuleId(ruleId);	
			 settleRuleCondition.setRuleConditionId(new BigDecimal(conditionId));
			 settleRuleCondition.setStatusCd("1300");
		     settleServ.updateSettleRuleCondition(settleRuleCondition);
			 
				json.put(RETURN_CODE, RESULT_OK);
				 
			} catch (Exception e) {
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC,e.getMessage());
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
			}
			return json.toString() ;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/getFlowStatusAndName")
	@ResponseBody
	public String getFlowStatusAndName(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String flowStatus = null;
		String flowName=null;
		JSONObject json=new JSONObject();
		try {
			 	Map<String, Object> map = new HashMap<String, Object>();
				// 获取orgId
				Org orgBean = getOrg(request);
				String orgId = StringUtil.valueOf(orgBean.getOrgId());
				String processId = getRequestValue(request, "processId");
				String systemId = request.getParameter("systemId");
				map.put("country_id", orgId);
				map.put("system_id", systemId);
				map.put("processId", processId);// processId flow_id
			    flowStatus=processSrv.getProvisioningProcessState(map);
			    flowName=processSrv.getProvisioningProcessName(map);
				json.put(RETURN_CODE, RESULT_OK);
				json.put("flowStatus", flowStatus);
				json.put("flowName", flowName);
		} catch (Exception e) {
			 json.put(RETURN_CODE, RESULT_ERR);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return json.toString();
	}
	
	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
		
	}
	
	
	public static String getProcessDefImg(String processKey){
		StringBuffer messageFlowURL = new StringBuffer();
		messageFlowURL.append(WebPropertyUtil.getPropertyValue("work_flow_pro_url")).append("/processDef/img/").append(processKey);
		return messageFlowURL.toString();
	}

}
