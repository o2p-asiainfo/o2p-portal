package com.ailk.o2p.portal.controller.pard;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.SettleRuleOrgRel;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.login.ILoginService;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.pardoffer.IPardOfferServ;
import com.ailk.o2p.portal.service.pardproduct.PardProdServ;
import com.ailk.o2p.portal.service.price.PriceServiceImpl;
import com.ailk.o2p.portal.service.settlement.SettlementServ;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.ailk.o2p.portal.utils.TenantInterceptor;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

@Controller
@RequestMapping(value = "/pardOffer")
@Transactional
public class PardOfferController extends BaseController {
	private static Logger log = Logger.getLog(PardOfferController.class);

	@Autowired
	private IPardOfferServ pardOfferServ;
	@Autowired
	private PardProdServ pardProdService;
	@Autowired
	private PriceServiceImpl priceServ;
	@Autowired
	private SettlementServ settleServ;
	@Autowired
	private IMessageServ messageServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private ILoginService loginServ;

	@Permission(center="pard",module="pardoffer", privilege="pard")
	@RequestMapping(value = "/toIndex")
	public ModelAndView toPardOffertIndex() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		messages.add("eaap.op2.portal.index.index");//home
		messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
		messages.add("eaap.op2.portal.pardOffer.offer");//offer
		
		messages.add("eaap.op2.portal.pardOffer.offerName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("prodOfferStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODGOODSSTATE,mainDataServ.MAININFO_MAP));
		mv.setViewName("../pardOffer/pardOfferList.jsp");
		if(switchEnvironment()){
			mv.addObject("switchEnvironmentFlag",true);
		}else{
			mv.addObject("switchEnvironmentFlag",false);
		}
		return mv;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public String PardOffertList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		Org orgBean = getOrg(request);
		String prodOfferName = getRequestValue(request, "prodOfferName");
		String path=request.getContextPath();
		JSONObject json = new JSONObject();
		Map queryMap=new HashMap();
		queryMap.put("queryType", "");
		if(prodOfferName!=null&&!"".equals(prodOfferName))
			queryMap.put("prodOfferName", prodOfferName);
		queryMap.put("offerProviderId", orgBean.getOrgId().toString()) ;
		queryMap.put("cooperationType", "11") ;
		List<Map> resultList = pardOfferServ.selectProdOfferList(queryMap);
		
//		queryMap.put("offerProviderId", "800000") ;
//		queryMap.put("cooperationType", "10") ;
//		resultList.addAll(pardOfferServ.selectProdOfferList(queryMap));
		
		StringBuilder returnDesc=new StringBuilder();
        String statusCd=null;
        String statusClass=null;
		for(Map resultMap:resultList){
			statusCd=resultMap.get("STATUS_CD").toString();
			if ("1000".equals(statusCd)) {
				statusClass = "category_1";
			} else if ("1200".equals(statusCd)) {
				statusClass = "category_2";
			} else if ("1299".equals(statusCd)) {
				statusClass = "category_3";
			} else if ("1300".equals(statusCd)) {
				statusClass = "category_4";
			} else {
				statusClass = "category_2";
			}
			Date createDate=DateUtil.stringToDate(resultMap.get("CREATE_DT").toString(), "yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String tim = sdf.format(createDate); 
			returnDesc.append("<div class=\"col-md-3 col-sm-4 mix ").append(statusClass).append("\" style=\"display:inline-block;\">");
			returnDesc.append("<div class=\"mix-inner\"> <img class=\"img-responsive\" src=\"").append(path);
			if(resultMap.get("LOGO_FILE_ID")==null){
				returnDesc.append("/resources/img/default.jpg");
			}else{
				returnDesc.append("/fileShare/readImg.shtml?sFileId=").append(resultMap.get("LOGO_FILE_ID"));
			}
			returnDesc.append("\" alt=\"\"> <a href=\"javascript:showProdOfferDetail('").append(resultMap.get("PROD_OFFER_ID")).append("');\" class=\"s-mix-link\">VIEW MORE</a>");
			returnDesc.append("<div class=\"mix-title\"> <strong>").append(resultMap.get("PROD_OFFER_NAME")).append("</strong> <b>").append(tim).append("</b> </div>");
			returnDesc.append("</div></div>");
		}
		json.put(RETURN_CODE, RESULT_OK);
		json.put(RETURN_DESC,returnDesc.toString());
		return json.toString();
	}
	
	@RequestMapping(value = "/toDetail")
	public ModelAndView toPardOffertDetail(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			Org orgBean = getOrg(request);
			
			Org org=new Org();
		
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");//home
			messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
			messages.add("eaap.op2.portal.pardOffer.offer");//offer
			messages.add("eaap.op2.portal.pardProd.prodDetail.location");//detail
			//基础信息
			messages.add("eaap.op2.portal.price.baseInfo");
			messages.add("eaap.op2.portal.pardOffer.offerName");//offerName
			messages.add("eaap.op2.portal.pardOffer.offerCode");//Offer Code
			messages.add("eaap.op2.portal.pardOffer.operate");
			messages.add("eaap.op2.portal.pardOffer.offerType");
			messages.add("eaap.op2.portal.pardOffer.offerType_main");
			messages.add("eaap.op2.portal.pardOffer.offerType_AddOn");
			//-- Product
			messages.add("eaap.op2.portal.pardSpec.product");//Product
			messages.add("eaap.op2.portal.pardSpec.provider");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.minimum");
			messages.add("eaap.op2.portal.pardSpec.maximum");
			//
			messages.add("eaap.op2.portal.pardOffer.offer");//Product Offer
			//Mutual Exclusion
			messages.add("eaap.op2.portal.pardOffer.exclusion");//Mutual Exclusion
			messages.add("eaap.op2.portal.pardSpec.code"); 
			messages.add("eaap.op2.portal.pardSpec.name"); 
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc"); //Description
			messages.add("eaap.op2.portal.pardOffer.timeToOrderRemark"); //Order Time
			messages.add("eaap.op2.portal.pardOffer.timeToOrder"); //Order Time
			//--Price Plan
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			//Settlement
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			messages.add("eaap.op2.portal.settlement.SBName");
			messages.add("eaap.op2.portal.settlement.settleObject");
			messages.add("eaap.op2.portal.settlement.effAndExpDate");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.saleArea");
			//审核状态
			messages.add("eaap.op2.portal.pardOffer.addOffer");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardOffer.onlineOffer");
			//按钮
			messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardmix.delete");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.detail");
			
			messages.add("eaap.op2.portal.pardOffer.noData");
			
			messages.add("eaap.op2.portal.pardProd.status");
			messages.add("eaap.op2.portal.pardProd.status-new");
			messages.add("eaap.op2.portal.pardProd.status-pending");
			messages.add("eaap.op2.portal.pardProd.status-passed");
			messages.add("eaap.op2.portal.pardProd.status-notPassed");
			//销售品对应的各状态
			messages.add("eaap.op2.portal.pardProdOffer.status1200");
			messages.add("eaap.op2.portal.pardProdOffer.status1299");
			messages.add("eaap.op2.portal.pardProdOffer.status1289");
			messages.add("eaap.op2.portal.pardProdOffer.status1288");
			messages.add("eaap.op2.portal.pardProdOffer.status1287");
			messages.add("eaap.op2.portal.pardProdOffer.status1279");
			messages.add("eaap.op2.portal.pardProdOffer.status1277");
			messages.add("eaap.op2.portal.pardProdOffer.status1500");
			messages.add("eaap.op2.portal.pardProdOffer.status1278");
			messages.add("eaap.op2.portal.pardProdOffer.status1298");
			messages.add("eaap.op2.portal.pardProdOffer.status1000");
			messages.add("eaap.op2.portal.pardSpec.offerProvider");
			messages.add("eaap.op2.portal.pardOffer.bundleOffer");
			ProdOffer prodOffer = new ProdOffer();
			addTranslateMessage(mv, messages);
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			//  获取销售品基本信息
			prodOffer.setStatusCd("1300");
			prodOffer = pardOfferServ.selectProdOffer(prodOffer);
			prodOffer.setFormatEffDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getEffDate(), "yyyy-MM-dd")) ;
			prodOffer.setFormatExpDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getExpDate(), "yyyy-MM-dd")) ;
			
			OfferProdRel offerProdRel = new OfferProdRel();
			offerProdRel.setProdOfferId(prodOffer.getProdOfferId());
			List<OfferProdRel> offerProdRelList = pardProdService.queryOfferProdRelList(offerProdRel);
			Product tp = new Product();
			for(int i=0;i<offerProdRelList.size();i++){
				tp.setProductId(offerProdRelList.get(i).getProductId());
				Product p = pardProdService.selectProduct(tp);
				offerProdRelList.get(i).setProductName(p.getProductName());
				offerProdRelList.get(i).setExtProdId(p.getExtProdId());
				org=new Org();
				org.setOrgId(p.getProductProviderId());
				org=pardOfferServ.queryOrg(org);
				
				offerProdRelList.get(i).setProductProviderName(org.getName());
			}
			ProdOfferRel prodOfferRel = new ProdOfferRel();
			prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
			prodOfferRel.setRelTypeCd("9999");
			List<ProdOfferRel> prodOfferRelList1 = pardOfferServ.selectProdOfferList(prodOfferRel);//依赖
			ProdOffer prodOfferTemp = null;
			for(int i=0;i<prodOfferRelList1.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList1.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList1.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList1.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());				
				Org org2=new Org();
				org2.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org2=pardOfferServ.queryOrg(org2);
				prodOfferRelList1.get(i).setOfferProvider(org2.getName());
			}
			prodOfferRel.setRelTypeCd("200000");
			List<ProdOfferRel> prodOfferRelList2 = pardOfferServ.selectProdOfferList(prodOfferRel);//互斥
			for(int i=0;i<prodOfferRelList2.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList2.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList2.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList2.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());
				Org org3=new Org();
				org3.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org3=pardOfferServ.queryOrg(org3);
				prodOfferRelList2.get(i).setOfferProvider(org3.getName());
			}
			//定价计划
			Map map = new HashMap();
			map.put("prodOfferId", prodOffer.getProdOfferId());
			List<PricingPlan> pricingPlanList = priceServ.getPricingPlan(map);
			
			//结算
			map.put("partnerCode", orgBean.getOrgId());
			map.put("servCode", prodOffer.getProdOfferId());
			map.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			List<Map<String, Object>> settleList = settleServ.getSettle(map);
			
			List<Map<String, Object>> settleListNew=new ArrayList<Map<String, Object>>();
			for(Map<String, Object> settleRuleTransfer:settleList){
				settleRuleTransfer.put("BUSINAME",settleRuleTransfer.get("BUSINAME").toString().replaceAll("%20"," "));
				settleListNew.add(settleRuleTransfer);
			}
			
			mv.addObject("settleList",settleListNew);
			
			//渠道
			ProdOfferChannel poChannel=new ProdOfferChannel();
			poChannel.setProdOfferId(prodOffer.getProdOfferId());
			List<Channel> pcList = pardOfferServ.getProdOfferChannel(poChannel);
			StringBuilder funcId = new StringBuilder();
			StringBuilder funcValue = new StringBuilder();
			for(Channel pc:pcList){
				funcId.append(pc.getChannelId()).append(",");
				funcValue.append(pc.getChannelName()).append(",");
			}
			Map FuncSeletedTreeMap = new HashMap();
			FuncSeletedTreeMap.put("funcId", funcId.length()>0?funcId.substring(0, funcId.length()-1):funcId.toString()); 
			FuncSeletedTreeMap.put("funcValue",  funcValue.length()>0?funcValue.substring(0, funcValue.length()-1):funcValue.toString()); 
			FuncSeletedTreeMap.put("funcPath",funcValue.length()>0?funcValue.substring(0, funcValue.length()-1):funcValue.toString()); 
			
			if("1298".equals(prodOffer.getStatusCd())||"1288".equals(prodOffer.getStatusCd())||"1278".equals(prodOffer.getStatusCd())){
				Map<String,Object> msgMap=new HashMap<String,Object>();
				msgMap.put("orgId", prodOffer.getOfferProviderId());
				String query=EAAPConstants.WORK_FLOW_MESSAGE_QUERY.replace("#id", String.valueOf(prodOffer.getProdOfferId()));
				msgMap.put("msgType", "3");
				msgMap.put("query", query);
				msgMap.put("begin", 0);
				msgMap.put("end", 1);
				List<Message> msgList = messageServ.showMessageList(msgMap);
				if(msgList!=null&&msgList.size()>0){
					Message msg=(Message)msgList.get(0);
					String url=msg.getMsgHandleAddress();
					String surl=url.substring(url.indexOf('?')+1,url.length());
					String[] urlAry=surl.split("&");
					String[] urlObjAry=null;
					for(String urlObj:urlAry){
						urlObjAry=urlObj.split("=");
						if("activity_Id".equals(urlObjAry[0])){
							mv.addObject("activity_Id",urlObjAry[1]);  
						}else if("process_Id".equals(urlObjAry[0])){
							mv.addObject("process_Id",urlObjAry[1]);
						}else if("message.msgId".equals(urlObjAry[0])){
							mv.addObject("message_msgId",urlObjAry[1]);
						}
					}
				}
			}
			
			List<Map<String,Object>>  countryHasOperatorList = this.getOrgCountryList();
			List<Map<String,Object>>  countryAndOperatorList = new ArrayList<Map<String,Object>>();
			Map<String,Object> countryAndOperatorMap;
			List<Map<String,Object>>operatorUnderCountryList;
			if(countryHasOperatorList!=null&&countryHasOperatorList.size()>0){
				for(Map<String,Object> countryMap:countryHasOperatorList){
					operatorUnderCountryList=new ArrayList<Map<String,Object>>();
					countryAndOperatorMap=new HashMap<String, Object>();
					operatorUnderCountryList=this.getCountryOperatorList(countryMap.get("AREAID").toString());
					countryAndOperatorMap.put("areaId",countryMap.get("AREAID").toString());
					countryAndOperatorMap.put("country", countryMap.get("COUNTRY").toString());
					countryAndOperatorMap.put("operatorMapList",operatorUnderCountryList);
					countryAndOperatorList.add(countryAndOperatorMap);
				}
				
				mv.addObject("countryAndOperatorList", countryAndOperatorList);
			}
			
			prodOffer = this.changeOperateCode(request, prodOffer, null);
			
			String  o2pCloudFlag = WebPropertyUtil.getPropertyValue("o2p_web_domin");
			mv.addObject("o2pCloudFlag",o2pCloudFlag);
			mv.addObject("prodOffer",prodOffer);
			mv.addObject("offerProdRelList",offerProdRelList);
			mv.addObject("prodOfferRelList1",prodOfferRelList1);
			mv.addObject("prodOfferRelList2",prodOfferRelList2);
			mv.addObject("pricingPlanList",pricingPlanList);
			mv.addObject("FuncSeletedTreeMap",FuncSeletedTreeMap);
			mv.addObject("checkMsg",messageServ.getCheckMsgByObjectId(EAAPConstants.WORK_FLOW_MESSAGE_QUERY,prodOfferId));
			mv.addObject("prodOfferStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODGOODSSTATE,mainDataServ.MAININFO_MAP));
			mv.setViewName("../pardOffer/pardOfferDetail.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/toDetail_noHead")
	public ModelAndView toPardOffertDetail_noHead(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			Org orgBean = getOrg(request);
			
			Org org=new Org();
		
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");//home
			messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
			messages.add("eaap.op2.portal.pardOffer.offer");//offer
			messages.add("eaap.op2.portal.pardProd.prodDetail.location");//detail
			//基础信息
			messages.add("eaap.op2.portal.price.baseInfo");
			messages.add("eaap.op2.portal.pardOffer.offerName");//offerName
			messages.add("eaap.op2.portal.pardOffer.offerCode");//Offer Code
			messages.add("eaap.op2.portal.pardOffer.operate");
			messages.add("eaap.op2.portal.pardOffer.offerType");
			messages.add("eaap.op2.portal.pardOffer.offerType_main");
			messages.add("eaap.op2.portal.pardOffer.offerType_AddOn");
			//-- Product
			messages.add("eaap.op2.portal.pardSpec.product");//Product
			messages.add("eaap.op2.portal.pardSpec.provider");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.minimum");
			messages.add("eaap.op2.portal.pardSpec.maximum");
			//
			messages.add("eaap.op2.portal.pardOffer.offer");//Product Offer
			//Mutual Exclusion
			messages.add("eaap.op2.portal.pardOffer.exclusion");//Mutual Exclusion
			messages.add("eaap.op2.portal.pardSpec.code"); 
			messages.add("eaap.op2.portal.pardSpec.name"); 
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc"); //Description
			messages.add("eaap.op2.portal.pardOffer.timeToOrderRemark"); //Order Time
			messages.add("eaap.op2.portal.pardOffer.timeToOrder"); //Order Time
			//--Price Plan
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			//Settlement
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			messages.add("eaap.op2.portal.settlement.SBName");
			messages.add("eaap.op2.portal.settlement.settleObject");
			messages.add("eaap.op2.portal.settlement.effAndExpDate");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.saleArea");
			//审核状态
			messages.add("eaap.op2.portal.pardOffer.addOffer");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardOffer.onlineOffer");
			//按钮
			messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardmix.delete");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.detail");
			
			messages.add("eaap.op2.portal.pardOffer.noData");
			
			messages.add("eaap.op2.portal.pardProd.status");
			messages.add("eaap.op2.portal.pardProd.status-new");
			messages.add("eaap.op2.portal.pardProd.status-pending");
			messages.add("eaap.op2.portal.pardProd.status-passed");
			messages.add("eaap.op2.portal.pardProd.status-notPassed");
			//销售品对应的各状态
			messages.add("eaap.op2.portal.pardProdOffer.status1200");
			messages.add("eaap.op2.portal.pardProdOffer.status1299");
			messages.add("eaap.op2.portal.pardProdOffer.status1289");
			messages.add("eaap.op2.portal.pardProdOffer.status1288");
			messages.add("eaap.op2.portal.pardProdOffer.status1287");
			messages.add("eaap.op2.portal.pardProdOffer.status1279");
			messages.add("eaap.op2.portal.pardProdOffer.status1277");
			messages.add("eaap.op2.portal.pardProdOffer.status1500");
			messages.add("eaap.op2.portal.pardProdOffer.status1278");
			messages.add("eaap.op2.portal.pardProdOffer.status1298");
			messages.add("eaap.op2.portal.pardProdOffer.status1000");
			messages.add("eaap.op2.portal.pardOffer.bundleOffer");
			ProdOffer prodOffer = new ProdOffer();
			addTranslateMessage(mv, messages);
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			//  获取销售品基本信息
			prodOffer.setStatusCd("1300");
			prodOffer = pardOfferServ.selectProdOffer(prodOffer);
			prodOffer.setFormatEffDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getEffDate(), "yyyy-MM-dd")) ;
			prodOffer.setFormatExpDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getExpDate(), "yyyy-MM-dd")) ;
			
			OfferProdRel offerProdRel = new OfferProdRel();
			offerProdRel.setProdOfferId(prodOffer.getProdOfferId());
			List<OfferProdRel> offerProdRelList = pardProdService.queryOfferProdRelList(offerProdRel);
			Product tp = new Product();
			for(int i=0;i<offerProdRelList.size();i++){
				tp.setProductId(offerProdRelList.get(i).getProductId());
				Product p = pardProdService.selectProduct(tp);
				offerProdRelList.get(i).setProductName(p.getProductName());
				offerProdRelList.get(i).setExtProdId(p.getExtProdId());
				org=new Org();
				org.setOrgId(p.getProductProviderId());
				org=pardOfferServ.queryOrg(org);
				
				offerProdRelList.get(i).setProductProviderName(org.getName());
			}
			ProdOfferRel prodOfferRel = new ProdOfferRel();
			prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
			prodOfferRel.setRelTypeCd("9999");
			List<ProdOfferRel> prodOfferRelList1 = pardOfferServ.selectProdOfferList(prodOfferRel);//依赖
			ProdOffer prodOfferTemp = null;
			for(int i=0;i<prodOfferRelList1.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList1.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList1.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList1.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());
				Org org2=new Org();
				org2.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org2=pardOfferServ.queryOrg(org2);
				prodOfferRelList1.get(i).setOfferProvider(org2.getName());
			}
			prodOfferRel.setRelTypeCd("200000");
			List<ProdOfferRel> prodOfferRelList2 = pardOfferServ.selectProdOfferList(prodOfferRel);//互斥
			for(int i=0;i<prodOfferRelList2.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList2.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList2.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList2.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());
				Org org3=new Org();
				org3.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org3=pardOfferServ.queryOrg(org3);
				prodOfferRelList2.get(i).setOfferProvider(org3.getName());
			}
			//定价计划
			Map map = new HashMap();
			map.put("prodOfferId", prodOffer.getProdOfferId());
			List<PricingPlan> pricingPlanList = priceServ.getPricingPlan(map);
			
			//结算
			map.put("partnerCode", orgBean.getOrgCode());
			map.put("servCode", prodOffer.getProdOfferId());
			map.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			List<Map<String, Object>> settleList = settleServ.getSettle(map);
			
			//渠道
			ProdOfferChannel poChannel=new ProdOfferChannel();
			poChannel.setProdOfferId(prodOffer.getProdOfferId());
			List<Channel> pcList = pardOfferServ.getProdOfferChannel(poChannel);
			StringBuilder funcId = new StringBuilder();
			StringBuilder funcValue = new StringBuilder();
			for(Channel pc:pcList){
				funcId.append(pc.getChannelId()).append(",");
				funcValue.append(pc.getChannelName()).append(",");
			}
			Map FuncSeletedTreeMap = new HashMap();
			FuncSeletedTreeMap.put("funcId", funcId.length()>0?funcId.substring(0, funcId.length()-1):funcId.toString()); 
			FuncSeletedTreeMap.put("funcValue",  funcValue.length()>0?funcValue.substring(0, funcValue.length()-1):funcValue.toString()); 
			FuncSeletedTreeMap.put("funcPath",funcValue.length()>0?funcValue.substring(0, funcValue.length()-1):funcValue.toString()); 
			
			mv.addObject("prodOffer",prodOffer);
			mv.addObject("offerProdRelList",offerProdRelList);
			mv.addObject("prodOfferRelList1",prodOfferRelList1);
			mv.addObject("prodOfferRelList2",prodOfferRelList2);
			mv.addObject("pricingPlanList",pricingPlanList);
			mv.addObject("settleList",settleList);
			mv.addObject("FuncSeletedTreeMap",FuncSeletedTreeMap);
			mv.addObject("prodOfferStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODGOODSSTATE,mainDataServ.MAININFO_MAP));
			mv.setViewName("../pardOffer/pardOfferDetail_noHead.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@SavePermission(center="pard",module="offer",parameterKey="prodOffer.prodOfferId",privilege="")
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toPardOffertUpdate(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			Org orgBean = getOrg(request);
			Org org=new Org();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");//home
			messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
			messages.add("eaap.op2.portal.pardOffer.offer");//offer
			messages.add("eaap.op2.portal.pardProd.prodDetail.location");//detail
			//基础信息
			messages.add("eaap.op2.portal.price.baseInfo");//Basic Info
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			//基础信息
			messages.add("eaap.op2.portal.pardOffer.offerUpdate.location");//Basic Info
			messages.add("eaap.op2.portal.pardOffer.localtion");
			messages.add("eaap.op2.portal.pardOffer.offerName");//offerName
			messages.add("eaap.op2.portal.pardOffer.offerCode");//Offer Code
			messages.add("eaap.op2.portal.pardOffer.operate");
			messages.add("eaap.op2.portal.pardOffer.offerType");
			messages.add("eaap.op2.portal.pardOffer.offerType_main");
			messages.add("eaap.op2.portal.pardOffer.offerType_AddOn");
			//-- Product
			messages.add("eaap.op2.portal.pardSpec.product");//Product
			messages.add("eaap.op2.portal.pardSpec.provider");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.minimum");
			messages.add("eaap.op2.portal.pardSpec.maximum");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//
			messages.add("eaap.op2.portal.pardOffer.offer");//Product Offer
			//Mutual Exclusion
			messages.add("eaap.op2.portal.pardOffer.exclusion");//Mutual Exclusion
			messages.add("eaap.op2.portal.pardSpec.code"); 
			messages.add("eaap.op2.portal.pardSpec.name"); 
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc"); //Description
			messages.add("eaap.op2.portal.pardOffer.timeToOrderRemark"); //Order Time
			messages.add("eaap.op2.portal.pardOffer.timeToOrder");
			//--Price Plan
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			//Settlement
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			messages.add("eaap.op2.portal.settlement.SBName");
			messages.add("eaap.op2.portal.settlement.settleObject");
			messages.add("eaap.op2.portal.settlement.effAndExpDate");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//settlement page
			messages.add("eaap.op2.portal.settlement.settleObject");//Settlement
			messages.add("eaap.op2.portal.settlement.newSettleWay");
			messages.add("eaap.op2.portal.settlement.copyRule");
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.saleArea");
			//审核状态
			
			//弹出
			messages.add("eaap.op2.portal.pardOffer.selProd");//SELECT PRODUCT
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardOffer.selOffer");//SELECT PRODUCT OFFER
			//--Price Plan
			messages.add("eaap.op2.portal.pardProd.prodDetail.saleArea");
			//审核状态
			messages.add("eaap.op2.portal.pardOffer.addOffer");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardOffer.onlineOffer");
			//按钮
			messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardmix.delete");
			messages.add("eaap.op2.portal.devmgr.cancel");

			messages.add("eaap.op2.portal.pardOffer.addPricePlanInfo");
			messages.add("eaap.op2.portal.pardOffer.addSettlementInfo");
			messages.add("eaap.op2.portal.pardOffer.noSettlementInfo");
			messages.add("eaap.op2.portal.price.deleteConfirm");
			messages.add("eaap.op2.portal.pardSpec.offerProvider");
			
			
			messages.add("eaap.op2.portal.doc.cancel");
			messages.add("eaap.op2.portal.doc.submit");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.pardOffer.bundleOffer");
			
			ProdOffer prodOffer = new ProdOffer();
			addTranslateMessage(mv, messages);
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			String isAdd=getRequestValue(request, "isAdd");
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			//  获取销售品基本信息
			prodOffer.setStatusCd("1300");
			prodOffer = pardOfferServ.selectProdOffer(prodOffer);
			prodOffer.setFormatEffDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getEffDate(), "yyyy-MM-dd")) ;
			prodOffer.setFormatExpDate("".equals(StringUtil.valueOf(prodOffer.getEffDate()))?null:DateUtil.convDateToString(prodOffer.getExpDate(), "yyyy-MM-dd")) ;
			
			OfferProdRel offerProdRel = new OfferProdRel();
			offerProdRel.setProdOfferId(prodOffer.getProdOfferId());
			List<OfferProdRel> offerProdRelList = pardProdService.queryOfferProdRelList(offerProdRel);
			Product tp = new Product();
			for(int i=0;i<offerProdRelList.size();i++){
				tp.setProductId(offerProdRelList.get(i).getProductId());
				Product p = pardProdService.selectProduct(tp);
				offerProdRelList.get(i).setProductName(p.getProductName());
				offerProdRelList.get(i).setExtProdId(p.getExtProdId());
				org=new Org();
				org.setOrgId(p.getProductProviderId());
				org=pardOfferServ.queryOrg(org);
				offerProdRelList.get(i).setProductProviderName(org.getName());
			}
			ProdOfferRel prodOfferRel = new ProdOfferRel();
			prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
			prodOfferRel.setRelTypeCd("9999");
			List<ProdOfferRel> prodOfferRelList1 = pardOfferServ.selectProdOfferList(prodOfferRel);//依赖
			ProdOffer prodOfferTemp = null;
			for(int i=0;i<prodOfferRelList1.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList1.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList1.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList1.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());	
				Org org2=new Org();
				org2.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org2=pardOfferServ.queryOrg(org2);
				prodOfferRelList1.get(i).setOfferProvider(org2.getName());
			}
			prodOfferRel.setRelTypeCd("200000");
			List<ProdOfferRel> prodOfferRelList2 = pardOfferServ.selectProdOfferList(prodOfferRel);//互斥
			for(int i=0;i<prodOfferRelList2.size();i++){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRelList2.get(i).getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				prodOfferRelList2.get(i).setProdOfferName(prodOfferTemp.getProdOfferName());
				prodOfferRelList2.get(i).setExtProdOfferId(prodOfferTemp.getExtProdOfferId());	
				Org org3=new Org();
				org3.setOrgId(Integer.valueOf(prodOfferTemp.getOfferProviderId()));
				org3=pardOfferServ.queryOrg(org3);
				prodOfferRelList2.get(i).setOfferProvider(org3.getName());
			}
			//定价计划
			HashMap map = new HashMap();
			map.put("prodOfferId", prodOffer.getProdOfferId());
			List<PricingPlan> pricingPlanList = priceServ.getPricingPlan(map);
			
			//结算
			map.put("partnerCode", orgBean.getOrgCode());
			map.put("servCode", prodOffer.getProdOfferId());
			map.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			List<Map<String, Object>> settleList = settleServ.getSettle(map);
			
			List<Map<String, Object>> settleListNew=new ArrayList<Map<String, Object>>();
			for(Map<String, Object> settleRuleTransfer:settleList){
				settleRuleTransfer.put("BUSINAME",settleRuleTransfer.get("BUSINAME").toString().replaceAll("%20"," "));
				settleListNew.add(settleRuleTransfer);
			}
			
			mv.addObject("settleList",settleListNew);
			
			//针对一个销售品下面的一个产品只有一个结算总规则
			if(settleList!=null&&settleList.size()>0){
				Map<String, Object> settleMap=settleList.get(0);
				Map<String,Object> queryOperatorMap=new HashMap<String, Object>();
				String servCodeFlag=settleMap.get("SERVCODE").toString();
				String busiCodeFlag=settleMap.get("BUSICODE").toString();
				queryOperatorMap.put("busiCode", busiCodeFlag);
				queryOperatorMap.put("servCode",servCodeFlag);
				String operatorName = null;
				String operatorOrgId=null;
				List<Map<String, Object>> operatorMapList = settleServ.queryOperatorId(queryOperatorMap);
				if(operatorMapList!=null&&operatorMapList.size()>0){
					if(operatorMapList.size()==1){
						Map<String, Object> operatorMap=operatorMapList.get(0);
						Integer operatorId=(Integer) operatorMap.get("OPERATORID");
						//查找运营商name
						Org orgForOperator=new Org();
						orgForOperator.setOrgId(operatorId);
						Org  orgForOperatorNew=pardOfferServ.queryOrg(orgForOperator);
						operatorName=orgForOperatorNew.getOrgUsername();
						operatorOrgId=orgForOperatorNew.getOrgId().toString();
						mv.addObject("operatorName", operatorName);
						mv.addObject("operatorOrgId", operatorOrgId);
					}
				}
			}
			
			
			//渠道
			ProdOfferChannel poChannel=new ProdOfferChannel();
			poChannel.setProdOfferId(prodOffer.getProdOfferId());
			List<Channel> pcList= pardOfferServ.getProdOfferChannel(poChannel);
			StringBuilder funcId=new StringBuilder();
			StringBuilder funcValue=new StringBuilder();
			for(Channel pc:pcList){
				funcId.append(pc.getChannelId()).append(",");
				funcValue.append(pc.getChannelName()).append(",");
			}
			Map FuncSeletedTreeMap = new HashMap();
			FuncSeletedTreeMap.put("funcId", funcId.length()>0?funcId.substring(0, funcId.length()-1):funcId.toString()); 
			FuncSeletedTreeMap.put("funcValue",  funcValue.length()>0?funcValue.substring(0, funcValue.length()-1):funcValue.toString()); 
			
			//消息已读
			String msgId = getRequestValue(request, "message.msgId");
			if("1298".equals(prodOffer.getStatusCd())||"1288".equals(prodOffer.getStatusCd())||"1278".equals(prodOffer.getStatusCd())){
				if(msgId!=null&&!"".equals(msgId)){
					messageServ.lookMsgById(String.valueOf(orgBean.getOrgId()),msgId);
				}else{
					pardOfferServ.lookMsgById(orgBean.getOrgId(), EAAPConstants.WORK_FLOW_MESSAGE_QUERY.replace("#id", prodOfferId));
				}
			}
			//settlement page
			Map<String, String> praMap = new HashMap<String, String>();
			praMap.put("prodOfferId", prodOfferId);
//			praMap.put("statusCd", EAAPConstants.STATUS_CD_FOR_TEST);
			List productList = settleServ.selectOfferProdRel(praMap);
			
			List<Map<String,Object>>  countryHasOperatorList = this.getOrgCountryList();
			
			List<Map<String,Object>>  countryAndOperatorList = new ArrayList<Map<String,Object>>();
			Map<String,Object> countryAndOperatorMap;
			List<Map<String,Object>>operatorUnderCountryList;
			if(countryHasOperatorList!=null&&countryHasOperatorList.size()>0){
				for(Map<String,Object> countryMap:countryHasOperatorList){
					operatorUnderCountryList=new ArrayList<Map<String,Object>>();
					countryAndOperatorMap=new HashMap<String, Object>();
					operatorUnderCountryList=this.getCountryOperatorList(countryMap.get("AREAID").toString());
					countryAndOperatorMap.put("areaId",countryMap.get("AREAID").toString());
					countryAndOperatorMap.put("country", countryMap.get("COUNTRY").toString());
					countryAndOperatorMap.put("operatorMapList",operatorUnderCountryList);
					countryAndOperatorList.add(countryAndOperatorMap);
				}
				
				mv.addObject("countryAndOperatorList", countryAndOperatorList);
			}
			
			String  o2pCloudFlag = WebPropertyUtil.getPropertyValue("o2p_web_domin");
			prodOffer = this.changeOperateCode(request, prodOffer, null);
			
			mv.addObject("providerList",mainDataServ.getMainInfo(EAAPConstants.DATE_TYPE));
			mv.addObject("o2pCloudFlag",o2pCloudFlag);
			mv.addObject("isAdd",isAdd);
			mv.addObject("productList",productList);
			mv.addObject("prodOffer",prodOffer);
			mv.addObject("offerProdRelList",offerProdRelList);
			mv.addObject("prodOfferRelList1",prodOfferRelList1);
			mv.addObject("prodOfferRelList2",prodOfferRelList2);
			mv.addObject("pricingPlanList",pricingPlanList);
			mv.addObject("FuncSeletedTreeMap",FuncSeletedTreeMap);
			mv.setViewName("../pardOffer/pardOfferUpdate.jsp");
			
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	@Permission(center="pard",module="pardoffer", privilege="audit")
	@RequestMapping(value = "/toAdd")
	public ModelAndView toPardOffertAdd(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");//home
			messages.add("eaap.op2.portal.index.pradIndex");//Partner Center
			messages.add("eaap.op2.portal.pardOffer.offer");//offer
			messages.add("eaap.op2.portal.pardOffer.bundleOffer");
			messages.add("eaap.op2.portal.pardProd.prodDetail.location");//detail
			//二级导航
			messages.add("eaap.op2.portal.price.baseInfo");//Basic Info
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			//基础信息
			messages.add("eaap.op2.portal.pardOffer.localtion");
			messages.add("eaap.op2.portal.pardOffer.offerName");//offerName
			messages.add("eaap.op2.portal.pardOffer.offerCode");//Offer Code
			messages.add("eaap.op2.portal.pardOffer.operate");
			messages.add("eaap.op2.portal.pardOffer.offerType");
			messages.add("eaap.op2.portal.pardOffer.offerType_main");
			messages.add("eaap.op2.portal.pardOffer.offerType_AddOn");
			//-- Product
			messages.add("eaap.op2.portal.pardSpec.product");//Product
			messages.add("eaap.op2.portal.pardSpec.provider");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.minimum");
			messages.add("eaap.op2.portal.pardSpec.maximum");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//
			messages.add("eaap.op2.portal.pardOffer.offer");//Product Offer
			//Mutual Exclusion
			messages.add("eaap.op2.portal.pardOffer.exclusion");//Mutual Exclusion
			messages.add("eaap.op2.portal.pardSpec.code"); 
			messages.add("eaap.op2.portal.pardSpec.name"); 
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc"); //Description
			messages.add("eaap.op2.portal.pardOffer.timeToOrderRemark"); //Order Time
			messages.add("eaap.op2.portal.pardOffer.timeToOrder");
			//--Price Plan
			messages.add("eaap.op2.portal.pardOffer.pricePlan");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			//Settlement
			messages.add("eaap.op2.portal.pardOffer.account");//Settlement
			messages.add("eaap.op2.portal.settlement.SBName");
			messages.add("eaap.op2.portal.settlement.settleObject");
			messages.add("eaap.op2.portal.settlement.effAndExpDate");
			messages.add("eaap.op2.portal.pardSpec.operation");
			//
			messages.add("eaap.op2.portal.pardProd.prodDetail.saleArea");
			//审核状态
			
			//弹出
			messages.add("eaap.op2.portal.pardOffer.selProd");//SELECT PRODUCT
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardOffer.selOffer");//SELECT PRODUCT OFFER
			//按钮
			messages.add("eaap.op2.portal.devmgr.updateBasicInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardmix.delete");
			messages.add("eaap.op2.portal.doc.cancel");
			messages.add("eaap.op2.portal.doc.submit");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.pardSpec.offerProvider");
			
			addTranslateMessage(mv, messages);
			
			List providerList = mainDataServ.getMainInfo(EAAPConstants.DATE_TYPE);
			
			//获取组件下拉框值
//			List<Object> list = new ArrayList<Object>();
//			List<Channel> channelList = pardOfferServ.getChannelBasicTree(new Channel());
//			for(Channel p : channelList){
//				HashMap mapL = new HashMap();
//				mapL.put("qweId", p.getChannelId());
//				mapL.put("qwepId", "0");
//				mapL.put("isOpen", true);
//				mapL.put("qweIsParent", false);
//				mapL.put("onlyLeafCheck", false);
//				mapL.put("attrChecked", false);
//				mapL.put("qwepName", p.getChannelName());
//				list.add(mapL);
//			}
//			mv.addObject("channelList",list);//channelList
			
			List<Map<String,Object>>  countryHasOperatorList = this.getOrgCountryList();
			List<Map<String,Object>>  countryAndOperatorList = new ArrayList<Map<String,Object>>();
			Map<String,Object> countryAndOperatorMap;
			List<Map<String,Object>>operatorUnderCountryList;
			if(countryHasOperatorList!=null&&countryHasOperatorList.size()>0){
				for(Map<String,Object> countryMap:countryHasOperatorList){
					operatorUnderCountryList=new ArrayList<Map<String,Object>>();
					countryAndOperatorMap=new HashMap<String, Object>();
					String areaId=countryMap.get("AREAID").toString();
					String country=countryMap.get("COUNTRY").toString();
					operatorUnderCountryList=this.getCountryOperatorList(areaId);
					countryAndOperatorMap.put("areaId",areaId);
					countryAndOperatorMap.put("country", country);
					countryAndOperatorMap.put("operatorMapList",operatorUnderCountryList);
					countryAndOperatorList.add(countryAndOperatorMap);
				}
				
				mv.addObject("countryAndOperatorList", countryAndOperatorList);
			}
			
			mv.addObject("prodOffer",new ProdOffer());
			mv.addObject("providerList",providerList);
			String  o2pCloudFlag = WebPropertyUtil.getPropertyValue("o2p_web_domin");
			mv.addObject("o2pCloudFlag",o2pCloudFlag);
			mv.setViewName("../pardOffer/pardOfferAdd.jsp");
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	@RequestMapping(value = "/getPardChanneTree")
	@ResponseBody
	public String PardChanneTree(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			List<Channel> channelList = pardOfferServ.getChannelBasicTree(new Channel());
			String idvalue = getRequestValue(request, "idvalue");
			JSONArray reList=new JSONArray();
			JSONObject stateMap = new JSONObject();
			stateMap.put("opened", true);
			stateMap.put("disabled", true);
			JSONObject mapR = new JSONObject();
			mapR.put("id", "A0");
			mapR.put("state", stateMap);
			mapR.put("text", "ALL");
			JSONArray childrenList=new JSONArray();
			JSONObject mapL =null;
			for(Channel p : channelList){
				mapL = new JSONObject();
				mapL.put("id", p.getChannelId());
				mapL.put("text", p.getChannelName());
				stateMap = new JSONObject();
				stateMap.put("selected", false);
				stateMap.put("opened", true);
				if(null!=idvalue&&!"".equals(idvalue)&&((","+idvalue+",").indexOf(","+p.getChannelId()+",")>=0)){
					stateMap.put("selected", true);
				}
				mapL.put("state", stateMap);
				childrenList.add(mapL);
			}
			mapR.put("children", childrenList);
			reList.add(mapR);
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,reList);
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/checkOfferCode",method = RequestMethod.POST)
	@ResponseBody
	public String checkOfferCode(final HttpServletRequest request,
			final HttpServletResponse response){
		String result = "true";
		String offerCode=getRequestValue(request, "offerCode");
		Org org = (Org)request.getSession().getAttribute("userBean");
		String orgId=org.getOrgId().toString();
		ProdOffer prodOffer=new ProdOffer();
		prodOffer.setExtProdOfferId(offerCode);
		prodOffer.setOfferProviderId(orgId);
		int codeCount=pardOfferServ.checkOfferCode(prodOffer);
		if(codeCount>=1){
			result = "false";
		}
		return result;
		
	}
	
	@RequestMapping(value = "/changeProductListForSettlement",method = RequestMethod.POST)
	@ResponseBody
	public String changeProductListForSettlement(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			String prodOfferId=getRequestValue(request, "offerId");
			Map<String, String> praMap = new HashMap<String, String>();
			praMap.put("prodOfferId", prodOfferId);
			List productList = settleServ.selectOfferProdRel(praMap);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
			json.put("productListForSettlement", productList);
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
		
		
	}

	
	@RequestMapping(value = "/deleteBundleOrExclusionOffer",method = RequestMethod.POST)
	@ResponseBody
	public String deleteBundleOrExclusionOffer(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			String offerId=getRequestValue(request, "offerId");
			String bundleOfferId=getRequestValue(request,"bundleOfferId");
			ProdOfferRel prodOfferRel=new ProdOfferRel();
			prodOfferRel.setOfferAId(new BigDecimal(offerId));
			prodOfferRel.setOfferZId(new BigDecimal(bundleOfferId));
			pardOfferServ.deleteprodOfferRel(prodOfferRel);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
		
	}
	
	
	
	@RequestMapping(value = "/deleteOfferProductRel",method = RequestMethod.POST)
	@ResponseBody
	public String deleteOfferProductRel(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			String offerId=getRequestValue(request, "offerId");
			String productId=getRequestValue(request,"productId");
			OfferProdRel offerProdRel=new OfferProdRel();
			offerProdRel.setProdOfferId(new BigDecimal(offerId));
			offerProdRel.setProductId(new BigDecimal(productId));
			pardOfferServ.delOfferProdRel(offerProdRel);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
		
	}
	

	@RequestMapping(value = "/checkHasPricingOrSettle",method = RequestMethod.POST)
	@ResponseBody
	public String checkHasPricingOrSettle(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		String result="false";
		try{
			String offerId=getRequestValue(request, "offerId");
			String productId=getRequestValue(request,"productId");
			OfferProdRel offerProdRel=new OfferProdRel();
			offerProdRel.setProdOfferId(new BigDecimal(offerId));
			offerProdRel.setProductId(new BigDecimal(productId));
			Integer pricingCount=pardOfferServ.queryPrcingPlanInfo(offerProdRel);
			SettleSpBusiDef settleSpBusiDef=new SettleSpBusiDef();
			settleSpBusiDef.setBusiCode(productId);
			settleSpBusiDef.setServCode(offerId);
			Integer settleRuleCount=pardOfferServ.querySettleRuleInfo(settleSpBusiDef);
			if(pricingCount>=1||settleRuleCount>=1){
				result = "true";
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put("result",result);
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/getPardOffertProdTree")
	@ResponseBody
	public String PardOffertProdTree(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String prodOfferId = getRequestValue(request, "prodIds");
			String idvalue = getRequestValue(request, "idvalue");
			ProdOffer prodOffer = new ProdOffer();
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			List<Integer> ids = new ArrayList<Integer>();//this.getSelectedTreeId();//全部的
			List<Integer> aids = new ArrayList<Integer>();
			prodOffer = pardOfferServ.selectProdOffer(prodOffer);
			JSONArray list = getPardOffertProdTree(prodOffer,"0",ids,aids,idvalue);
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,list);
			
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	
	private JSONArray getPardOffertProdTree(ProdOffer prodOffer,Object pid,List<Integer> ids,List<Integer> aids,String idvalue){
		JSONArray reList=new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prodOfferId", prodOffer.getProdOfferId());
		List<Map<String,Object>> mapList = priceServ.selectProductNameById(map);
		JSONObject stateMap = new JSONObject();
		stateMap.put("opened", true);
		if(null!=idvalue&&!"".equals(idvalue)&&idvalue.indexOf(prodOffer.getProdOfferId().toString())>-1){
			stateMap.put("selected", true);
		}
		JSONObject mapR = new JSONObject();
		mapR.put("id", "A"+prodOffer.getProdOfferId());
		mapR.put("state", stateMap);
		mapR.put("text", prodOffer.getProdOfferName());
		JSONArray childrenList=new JSONArray();
		JSONObject mapL =null;
		for(Map<String,Object> m : mapList){
			mapL = new JSONObject();
			mapL.put("id", m.get("OFFERPRODRELAID"));
			mapL.put("text", m.get("PRODNAME"));
			stateMap = new JSONObject();
			stateMap.put("opened", true);
			if(null!=idvalue&&!"".equals(idvalue)&&idvalue.indexOf(m.get("OFFERPRODRELAID").toString())>-1){		//?
				stateMap.put("selected", true);
			}else{
				stateMap.put("selected", false);
			}
			mapL.put("state", stateMap);
			childrenList.add(mapL);
		}
		mapR.put("children", childrenList);
		reList.add(mapR);
		return reList;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@SavePermission(center="pard",module="offer",parameterKey="prodOffer.prodOfferId",privilege="")
	@ResponseBody
	public String PardOfferUpdate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String offerProdStr = getRequestValue(request, "offerProdStr"); //val1
			String offerStr = getRequestValue(request, "offerStr"); //val2
			String offerMutualExclusionStr = getRequestValue(request, "offerMutualExclusionStr"); //val3
			
			String logoFileId= getRequestValue(request, "logoFileId");
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			String prodOfferName = getRequestValue(request, "prodOffer.prodOfferName"); 
			String extProdOfferId = getRequestValue(request, "prodOffer.extProdOfferId"); 
			String prodOfferDesc = getRequestValue(request, "prodOffer.prodOfferDesc"); 
			String formatEffDate = getRequestValue(request, "prodOffer.formatEffDate"); 
			String formatExpDate = getRequestValue(request, "prodOffer.formatExpDate"); 
			String offerType = getRequestValue(request, "prodOffer.offerType");
			String operateCode = getRequestValue(request, "prodOffer.operateCode");

			ProdOffer prodOffer = new ProdOffer();
			if(!StringUtils.isEmpty(logoFileId)){
				prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
				prodOffer.setLogoFileId(Integer.parseInt(logoFileId));
				pardOfferServ.updateProdOffer(prodOffer);
			}else{
				prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
				prodOffer.setProdOfferName(prodOfferName);
				prodOffer.setExtProdOfferId(extProdOfferId);
				prodOffer.setFormatEffDate(formatEffDate);
				prodOffer.setFormatExpDate(formatExpDate);
				prodOffer.setProdOfferDesc(prodOfferDesc);
				prodOffer.setOfferType(offerType);
				prodOffer.setEffDate("".equals(StringUtil.valueOf(prodOffer.getFormatEffDate()))?null:DateUtil.stringToDate(prodOffer.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
				prodOffer.setExpDate("".equals(StringUtil.valueOf(prodOffer.getFormatExpDate()))?null:DateUtil.stringToDate(prodOffer.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
				String offerChannelStr = getRequestValue(request, "offerChannelStr");
				pardOfferServ.updateProdOffer(this.changeOperateCode(request, prodOffer, operateCode),offerChannelStr,offerProdStr,offerStr,offerMutualExclusionStr);
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
	
	private ProdOffer changeOperateCode(HttpServletRequest request,ProdOffer prodOffer,String operateCode){
		if(EAAPConstants.isCloud()){
			if(null != operateCode){
				Component component = new Component();
				component.setOrgId(Integer.parseInt(operateCode));
				component.setIsMain("Y");
				component = loginServ.selectComponentOne(component);
				if(component != null){
					prodOffer.setOperateCode(component.getComponentId());
					request.getSession().removeAttribute(TenantInterceptor.SESSION_OPERATE_CODE);
					request.getSession().setAttribute(TenantInterceptor.SESSION_OPERATE_CODE, operateCode);
				}else{
					log.error(LogModel.EVENT_APP_EXCPT, 
							new BusinessException(ExceptionCommon.WebExceptionCode,"cloud set operate code .component is null",null));
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"cloud set operate code .component is null.",null);
				}
			}else{
				Component component = new Component();
				component.setComponentId(prodOffer.getOperateCode());
				component.setIsMain("Y");
				component = loginServ.selectComponentOne(component);
				
				if(component != null){
					request.getSession().removeAttribute(TenantInterceptor.SESSION_OPERATE_CODE);
					request.getSession().setAttribute(TenantInterceptor.SESSION_OPERATE_CODE, String.valueOf(component.getOrgId()));
				}else{
					log.error(LogModel.EVENT_APP_EXCPT, 
							new BusinessException(ExceptionCommon.WebExceptionCode,"cloud get operate code .component is null",null));
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"cloud get operate code .component is null.",null);
				}
				
				prodOffer.setOperateCode(String.valueOf(component.getOrgId()));
			}
		}
		
		return prodOffer;
	}

	@SavePermission(center="pard",module="offer",parameterKey="offerId",privilege="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String PardOffertDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String offerId = getRequestValue(request, "offerId");
			if(!"".equals(offerId)&&null!=offerId){
				ProdOffer prodOffer = new ProdOffer();
				prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(offerId)));
				JSONObject valJson = validateWithException(prodOffer);
				if(!RESULT_OK.equals(valJson.getString(RETURN_CODE))){return valJson.toString();}
				OfferProdRel offerProdRel = new OfferProdRel();
				offerProdRel.setProdOfferId(prodOffer.getProdOfferId());
				pardOfferServ.deleteProdOffer(prodOffer,offerProdRel);
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
	
	@SavePermission(center="pard",module="offer",parameterKey="prodOffer.prodOfferId",privilege="")
	@RequestMapping(value = "/offShelf", method = RequestMethod.POST)
	public ModelAndView offShelf(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		try {
			
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			ProdOffer prodOffer = new ProdOffer();
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			prodOffer.setStatusCd(EAAPConstants.STATUS_CD_FOR_PRODUCTOFFER_OFFSHEFL);
			pardOfferServ.updateProdOffer(prodOffer);
			
			mv.setViewName("redirect:./toDetail.shtml?prodOffer.prodOfferId="+prodOfferId);
		} catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	@SavePermission(center="pard",module="offer",parameterKey="prodOffer.prodOfferId",privilege="")
	@RequestMapping(value = "/onShelf", method = RequestMethod.POST)
	public ModelAndView onShelf(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		try {
			
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			ProdOffer prodOffer = new ProdOffer();
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			prodOffer.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
			pardOfferServ.updateProdOffer(prodOffer);
			
			mv.setViewName("redirect:./toDetail.shtml?prodOffer.prodOfferId="+prodOfferId);
		} catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	@SavePermission(center="pard",module="offer",parameterKey="prodOffer.prodOfferId",privilege="")
	@RequestMapping(value = "/submitCheck", method = RequestMethod.POST)
	public ModelAndView PardOffertSubmitCheck(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			Org org = (Org)request.getSession().getAttribute("userBean");
			String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
			String prodOfferName = getRequestValue(request, "prodOffer.prodOfferName");
			String statusCd = getRequestValue(request, "prodOffer.statusCd");
			ProdOffer prodOffer = new ProdOffer();
			prodOffer.setProdOfferName(prodOfferName);
			prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
			prodOffer.setStatusCd(statusCd);
			
			Object switchUserRole = request.getSession().getAttribute("switchUserRole");
			pardOfferServ.doPardOffertSubmitCheck(prodOffer,String.valueOf(org.getOrgId()),switchUserRole);
			mv.setViewName("redirect:./toDetail.shtml?prodOffer.prodOfferId="+prodOfferId);
		} catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/checkProductOrOfferState", method = RequestMethod.POST)
	@ResponseBody
	public String checkProductOrOfferState(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject json = new JSONObject();
		String prodOfferId = getRequestValue(request, "prodOffer.prodOfferId");
		try{
			if(prodOfferId == null){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"PardOffertSubmitCheck offer'id is null! ", null);
			}
			
			OfferProdRel offerProdRel = new OfferProdRel();
			offerProdRel.setProdOfferId(new BigDecimal(prodOfferId));
			List<OfferProdRel> offerProdRelList = pardProdService.queryOfferProdRelList(offerProdRel);
			Product tp = null;
			for(OfferProdRel offerProdRelT: offerProdRelList){
				tp = new Product();
				tp.setProductId(offerProdRelT.getProductId());
				tp = pardProdService.selectProduct(tp);
				if(!EAAPConstants.STATUS_CD_FOR_TEST.equals(tp.getStatusCd())){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"PardOffertSubmitCheck Product [ ID="+tp.getProductId()+", Name="+tp.getProductName()+" ] status is not online! ", null);
				}
			}
			ProdOfferRel prodOfferRel = new ProdOfferRel();
			prodOfferRel.setOfferAId(new BigDecimal(prodOfferId));
			prodOfferRel.setRelTypeCd("9999");
			List<ProdOfferRel> prodOfferRelList1 = pardOfferServ.selectProdOfferList(prodOfferRel);//依赖
			ProdOffer prodOfferTemp = null;
			for(ProdOfferRel prodOfferRel1 : prodOfferRelList1){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRel1.getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				if(!EAAPConstants.STATUS_CD_FOR_TEST.equals(prodOfferTemp.getStatusCd())){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"PardOffertSubmitCheck Product Offer [ ID="+prodOfferTemp.getProdOfferId()+", Name="+prodOfferTemp.getProdOfferName()+" ] status is not online! ", null);
				}
			}
			prodOfferRel.setRelTypeCd("200000");
			List<ProdOfferRel> prodOfferRelList2 = pardOfferServ.selectProdOfferList(prodOfferRel);//互斥
			for(ProdOfferRel prodOfferRel2: prodOfferRelList2){
				prodOfferTemp = new ProdOffer();
				prodOfferTemp.setProdOfferId(prodOfferRel2.getOfferZId());
				prodOfferTemp = pardOfferServ.selectProdOffer(prodOfferTemp);
				if(!EAAPConstants.STATUS_CD_FOR_TEST.equals(prodOfferTemp.getStatusCd())){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"PardOffertSubmitCheck Mutual Exclusion Product Offer [ ID="+prodOfferTemp.getProdOfferId()+", Name="+prodOfferTemp.getProdOfferName()+" ] status is not online! ", null);
				}
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/winList", produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardOfferWinList(final HttpServletRequest request,
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
			//获取当前用户
			
			Map paramMap = new HashMap();
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			String offName = getRequestValue(request, "off_name");
			String offCode = getRequestValue(request, "off_code");
			String cooperationType = getRequestValue(request, "cooperationType"); 
			if(!"".equals(offName)&&null!=offName){
				paramMap.put("offerName", offName);
			}
			if(!"".equals(offCode)&&null!=offCode){
				paramMap.put("offerCode", offCode);
			}
			if(!"".equals(cooperationType)&&null!=cooperationType){
				paramMap.put("cooperationType", cooperationType);
			}
			
			if(EAAPConstants.isCloud()){
				String provider = getRequestValue(request, "provider") + "," + getOrg(request).getOrgId();
				paramMap.put("project", EAAPConstants.O2P_WEB_DOMAIN_CLOUD);
				if(!"".equals(provider)&&null!=provider){
					paramMap.put("providerId", provider.split(","));
				}
			}else{
				paramMap.put("project", EAAPConstants.O2P_WEB_DOMAIN_LOCAL);
				paramMap.put("providerId", getOrg(request).getOrgId());
			}
			
			String extValue =getRequestValue(request,"queryParamsData");
			if(!"".equals(extValue)&&null!=extValue){
				paramMap.put("extVal", extValue.split(","));
			}
			
			Integer recordsTotal = pardOfferServ.countProductList(paramMap);
			if(0 == recordsTotal){
		    	throw new BusinessException(ExceptionCommon.WebExceptionCode,
						"query offer list error... " ,null); 
		    }
			List<Map> pardSpecList = pardOfferServ.queryProductList(paramMap);
			JSONArray dataList = new JSONArray();
			JSONArray subDataList = null;
			for(Map pardSpecMap:pardSpecList){
				subDataList=new JSONArray();
				subDataList.add("<input type='checkbox' value='' class='checkboxes' id='"+pardSpecMap.get("PROD_OFFER_ID")+"' name='' />");
//				subDataList.add(pardSpecMap.get("PROD_OFFER_ID"));
//				subDataList.add(pardSpecMap.get("OFFER_PROVIDER_ID"));
				subDataList.add(pardSpecMap.get("NAME"));
				subDataList.add(pardSpecMap.get("EXT_PROD_OFFER_ID"));
				subDataList.add(pardSpecMap.get("PROD_OFFER_NAME"));
				subDataList.add(pardSpecMap.get("PROD_OFFER_DESC"));
				subDataList.add(pardSpecMap.get("COOPERATION_TYPE"));
				dataList.add(subDataList);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",recordsTotal);
			json.put("recordsFiltered", recordsTotal);
			json.put("data",dataList.toJSONString());
		}catch(Exception e){
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String PardOfferAdd(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			Org orgBean = getOrg(request);
			String offerProdStr = getRequestValue(request, "offerProdStr"); //val1
			String offerStr = getRequestValue(request, "offerStr"); //val2
			String offerMutualExclusionStr = getRequestValue(request, "offerMutualExclusionStr"); //val3
			String offerChannelStr = getRequestValue(request, "offerChannelStr"); //items
			String prodOfferName = getRequestValue(request, "prodOffer.prodOfferName"); 
			String extProdOfferId = getRequestValue(request, "prodOffer.extProdOfferId"); 
			String prodOfferDesc = getRequestValue(request, "prodOffer.prodOfferDesc"); 
			String formatEffDate = getRequestValue(request, "prodOffer.formatEffDate"); 
			String formatExpDate = getRequestValue(request, "prodOffer.formatExpDate"); 
			String offerType = getRequestValue(request, "prodOffer.offerType");
			String operateCode = getRequestValue(request, "prodOffer.operateCode");
			
			ProdOffer prodOffer=new ProdOffer();
			prodOffer.setExtProdOfferId(extProdOfferId);
			prodOffer.setProdOfferName(prodOfferName);
			prodOffer.setProdOfferDesc(prodOfferDesc);
			prodOffer.setFormatEffDate(formatEffDate);
			prodOffer.setFormatExpDate(formatExpDate);
			prodOffer.setOfferType(offerType) ;
			prodOffer.setOperateCode(operateCode);
			prodOffer.setStatusCd("1200") ;
			prodOffer.setOfferProviderId(String.valueOf(orgBean.getOrgId()));
			prodOffer.setCooperationType("11"); 
			prodOffer.setEffDate("".equals(StringUtil.valueOf(prodOffer.getFormatEffDate()))?null:DateUtil.stringToDate(prodOffer.getFormatEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			prodOffer.setExpDate("".equals(StringUtil.valueOf(prodOffer.getFormatExpDate()))?null:DateUtil.stringToDate(prodOffer.getFormatExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			
			BigDecimal offerId = pardOfferServ.addProdOffer(request,response,this.changeOperateCode(request, prodOffer, operateCode),
					offerProdStr,offerStr,offerMutualExclusionStr,offerChannelStr,i18nLoader,orgBean);
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,offerId);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
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
	
	@RequestMapping(value = "/queryOfferAndProductSettlement", method = RequestMethod.POST)
	@ResponseBody
	public String queryOfferAndProductSettlement(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String result;
		String offerId=getRequestValue(request, "offerId");
		String productId=getRequestValue(request, "productId");
		SettleSpBusiDef settleSpBusiDef=new SettleSpBusiDef();
		settleSpBusiDef.setBusiCode(productId);
		settleSpBusiDef.setServCode(offerId);
		Integer settleRuleCount=pardOfferServ.querySettleRuleInfo(settleSpBusiDef);
		if(settleRuleCount>=1){
			result= "true";
		}else{
			result= "false";
		}
		json.put(RETURN_CODE, RESULT_OK);
		json.put("result",result);
		return json.toString();
	}
	
	
	@RequestMapping(value = "/updateSettleRuleOrgRelOperator", method = RequestMethod.POST)
	@ResponseBody
	public String updateSettleRuleOrgRelOperator(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String result;
		String offerId=getRequestValue(request, "offerId");
		String productId=getRequestValue(request, "productId");
		String operatorId=getRequestValue(request, "operatorId");
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("busiCode", productId);
		queryMap.put("servCode",offerId);
		List<SettleRuleOrgRel> settleRuleOrgRelList=settleServ.settleRuleOrgRelChangeOperator(queryMap);
		
		if(settleRuleOrgRelList!=null&&settleRuleOrgRelList.size()>0){
			for(SettleRuleOrgRel settleRuleOrgRel:settleRuleOrgRelList){
				settleRuleOrgRel.setOrgId(Integer.parseInt(operatorId));
				settleServ.updateSettleRuleOrgRel(settleRuleOrgRel);
			}
			json.put(RETURN_CODE, RESULT_OK);
		}else{
			json.put(RETURN_CODE, RESULT_ERR);
		}
		return json.toString();
	}
	
	private List<Map<String,Object>> getOrgCountryList(){
		Map map = new HashMap();
		return pardOfferServ.queryOrgCountry(map);
	}
	
	
	private List<Map<String,Object>> getCountryOperatorList(String areaId){
		Org orgBean=new Org();
		orgBean.setAreaId(areaId);
		return pardOfferServ.queryOperatorUnderCountry(orgBean);
	}
}
