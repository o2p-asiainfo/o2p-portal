/** 
 * Project Name:o2p-portal-pro 
 * File Name:DevMgrAction.java 
 * Package Name:com.ailk.o2p.portal.controller.mgr 
 * Date:2015年7月2日下午7:47:14 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.controller.mgr;  

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.App;
import com.ailk.eaap.op2.bo.AppApiList;
import com.ailk.eaap.op2.bo.AppType;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.mgr.IDevMgrServ;
import com.ailk.o2p.portal.service.tenant.ITenantService;
import com.ailk.o2p.portal.utils.OWASPUtil;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

/** 
 * ClassName:DevMgrAction <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年7月2日 下午7:47:14 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
@Controller
public class DevMgrController  extends BaseController{
	private static Logger log = Logger.getLog(DevMgrController.class);
 
	@Autowired
	private IDevMgrServ devMgrService;
	@Autowired
	private IMessageServ messageServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private ITenantService tenantService;
	
	@RequestMapping(value = "/mgr/manageDevMgr")
	@ResponseBody
	public ModelAndView manageDevMgr (final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		// 添加页面上需要国际化的消息
		//公共
		List<String> messages = getUTC();
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		
		//   
		AppApiList appApiBean = new AppApiList() ;
		App app=new App();
		String appId = getRequestValue(request, "appId");
		appApiBean.setAppId(Integer.parseInt(appId));
		app.setAppId(Integer.parseInt(appId));
		
		List<AppApiList> myAppApiList = devMgrService.selectAppApiList(appApiBean) ;
		List<Map> apiInfoList = devMgrService.selectApiInfo(app) ;
		List<App>  tmpappList = devMgrService.selectAPP(app) ;
	    App appInfo = null;
		if(tmpappList.size()>0){ 
			appInfo = tmpappList.get(0);
		}
		//JSONArray checkOffer=new JSONArray();
		List<Map> father = new ArrayList<Map>();
		if(apiInfoList.size()>0){
			Map temp = null;
			Map subDataList=null;
			for(int m=0;m<apiInfoList.size();m++){
				List<Map> son = null;
				List<Map> charge = null;
				temp = apiInfoList.get(m);
				temp.put("offerId", temp.get("PROD_OFFER_ID"));
				temp.put("componentId", appInfo.getComponentId());
				son = devMgrService.queryApiIds(temp);
				charge =devMgrService.queryChargeInfo(temp);
				String chargeString="";
				if(charge.size()>0){
					chargeString = devMgrService.pricePlanToString(charge);
				}
				
				temp.put("APIs", son);
				temp.put("Charges", charge);
				if(temp.get("PRICING_NAME_S")==null||"".equals(temp.get("PRICING_NAME_S"))){
					temp.put("PRICING_NAME_S", "");
				}
				 
			 
				temp.put("TR_1","<input type='checkbox' value='' class='checkboxes' onChange='offerCheck(this)' id='"+temp.get("PROD_OFFER_ID")+"' name='' />");
                temp.put("TR_PACKAGENAME",temp.get("PACKAGENAME"));
				temp.put("TR_API_NAME_S",temp.get("API_NAME_S"));
				//temp.put("TR_PRICING_NAME_S","<a data-content='&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Popover title'>"+temp.get("PRICING_NAME_S")+"</a>");
				temp.put("TR_PRICING_NAME_S","<a data-content='"+chargeString+"' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Price Plan'>"+temp.get("PRICING_NAME_S")+"</a>");
			    temp.put("TR_EFF_EXP",temp.get("EFF_TIME")+"~"+temp.get("EXP_TIME"));
				temp.put("TR_PROD_OFFER_DESC",temp.get("PROD_OFFER_DESC"));
				father.add(temp);
			}
		}
		apiInfoList = father;
		app = tmpappList.get(0);
	    String dateString = "".equals(StringUtil.valueOf(app.getAppCreateTime()))?null:DateUtil.convDateToString(app.getAppCreateTime() , "yyyy-MM-dd");
	    AppType appType = new AppType() ;
	    List<AppType> appTypelist = new ArrayList<AppType>() ;
	    appTypelist = devMgrService.selectAppType(appType) ;
	    String applistString ="";
	    if(appTypelist.size()>0){
	    	for(int i=0;i<appTypelist.size();i++){
	    		AppType apptype =appTypelist.get(i);
	    		if(i==0){
	    			applistString="[{value:"+apptype.getAppTypeCd()+",text:'"+apptype.getAppTypeName()+"',}";
	    		}else{
	    			applistString+=",{value:"+apptype.getAppTypeCd()+",text:'"+apptype.getAppTypeName()+"',}";
	    		}
	    		
	    	}
	    	applistString+="]";
	    }
	    
		appType.setAppTypeCd(app.getAppType());
		appType = devMgrService.selectAppType(appType).get(0) ;
		Component component = new Component();
		component.setComponentId(app.getComponentId()) ;
		component = devMgrService.selectCom(component).get(0) ;
//		Map apiStateMap = getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_API) ;
		//Map appApiListStateMap =  getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_APPAPILIST) ;
		 
		List<Map> category = new ArrayList<Map>() ;
		Map map = new HashMap();
		category = devMgrService.getCategory(map);
		mv.addObject("category",category);
		mv.addObject("applistString",applistString);
		mv.addObject("appInfo",appInfo);
		mv.addObject("app",app);
		mv.addObject("appType",appType);
		mv.addObject("dateString",dateString);
		mv.addObject("component",component);
		mv.addObject("myAppApiList",myAppApiList);
		mv.addObject("apiInfoList",apiInfoList);
		mv.addObject("apiInfoListNum",apiInfoList.size());
		mv.addObject("stateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_COMAPP,mainDataServ.MAININFO_MAP));
		mv.addObject("checkMsg", messageServ.getCheckMsgByObjectId(EAAPConstants.WORK_FLOW_MESSAGE_QUERY_APP, appId));
		mv.setViewName("../mgr/manageApp.jsp");
		return mv;  
 
	}
	
	@Permission(center="dev",module="application", privilege="audit")
	@RequestMapping(value = "/mgr/manageDevMgrAdd", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView manageDevMgrAdd(){
		// 添加页面上需要国际化的消息
		//公共
		List<String> messages = getUTC();
 
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		AppType appType = new AppType() ;
		HashMap temp = new HashMap();

		List<AppType> appTypelist = devMgrService.selectAppType(appType) ;
		Map orgStateMap = getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG) ;
		
		
		List<Map> category = devMgrService.getCategory(temp);
		mv.addObject("category",category);
		//mv.addObject("appId",returnAppId);
		mv.addObject("appTypelist",appTypelist);
		mv.addObject("orgStateMap",orgStateMap);
		mv.setViewName("../mgr/createApp.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/mgr/manageDevMgrSave", method = RequestMethod.POST)
	@SavePermission(center="dev",module="app",parameterKey="appId",privilege="")
	@ResponseBody
	public String  manageDevMgrSave (final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		App app=new App();
	try{
		Org orgBean = getOrg(request); 
		String returnAppId = devMgrService.selectSeqApp() ; 
		app.setAppDeve(Integer.valueOf(orgBean.getOrgId().toString())) ;
		String appName = getRequestValue(request, "appName");
		app.setAppName(appName); 
		String appId = returnAppId;
		app.setAppId(Integer.parseInt(appId)) ;
		String appUrl = getRequestValue(request, "appUrl");
		app.setAppUrl(appUrl);
		String appCallbackUrl = getRequestValue(request, "appCallbackUrl");
		app.setAppCallbackUrl(appCallbackUrl);
		String appType = getRequestValue(request, "appType");
		app.setAppType(appType);
		String appSumma = getRequestValue(request, "appSumma");
		app.setAppSumma(appSumma);
		String appDesc = getRequestValue(request, "appDesc");
		app.setAppDesc(appDesc);
		String offerId = getRequestValue(request, "bindOfferIds");
		String sFileId = getRequestValue(request, "sFileId");
		if(!"".equals(sFileId)){
			app.setSfileId(Integer.valueOf(sFileId));
		}
		
		app.setComponentId(saveComponent(app.getAppName(),Integer.valueOf(orgBean.getOrgId().toString()))) ; 
		String time = String.valueOf(new Date().getTime()) ;
		app.setAppkey(StringUtil.Md5("APPKEY"+appId+time+makeAwardNo(9999)));
		app.setAppsecure(StringUtil.Md5("APPSECRET"+appId+time+makeAwardNo(9999)));
		devMgrService.saveApp(app) ;
		if(!"".equals(offerId)){
			devMgrService.saveAppOrderApi(offerId,app);
		}
		json.put("appId", appId);
		json.put(RETURN_CODE, RESULT_OK);
		json.put(RETURN_DESC,"Success");
	} catch (Exception e) {
		json.put(RETURN_CODE, RESULT_ERR);
		json.put(RETURN_DESC,e.getMessage());
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
	}
	 
		return json.toString(); 
	}
	
	@SavePermission(center="dev",module="app",parameterKey="appId",privilege="")
	@RequestMapping(value = "/mgr/manageDevMgrUpdate")
	@ResponseBody
	public String updateApp (final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		App app=new App();
		try{
			Org orgBean = getOrg(request); 
			
			app.setAppDeve(Integer.valueOf(orgBean.getOrgId().toString())) ;
			String appName = getRequestValue(request, "appName");
			app.setAppName(appName); 
			String appId = getRequestValue(request, "appId");
			app.setAppId(Integer.parseInt(appId)) ;
			String appUrl = getRequestValue(request, "appUrl");
			app.setAppUrl(appUrl);
			String appCallbackUrl = getRequestValue(request, "appCallbackUrl");
			app.setAppCallbackUrl(appCallbackUrl);
			String appType = getRequestValue(request, "appType");
			app.setAppType(appType);
			String offerId = getRequestValue(request, "bindOfferIds");
			String sFileId = getRequestValue(request, "sFileId");
			if(!"".equals(sFileId)){
				app.setSfileId(Integer.valueOf(sFileId));
			}
			
			String appDesc = getRequestValue(request, "appDesc");
			app.setAppDesc(appDesc);
			devMgrService.updateApp(app) ;
			if(!"".equals(offerId)){
				devMgrService.saveAppOrderApi(offerId,app);
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
	
	@RequestMapping(value = "/mgr/developerCenter", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView developerCenter(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
 
			ModelAndView mv = new ModelAndView();
	       // mv.addObject("appInfo",1);
	        List<String> messages = new ArrayList<String>();
	        addTranslateMessage(mv, messages);
			mv.setViewName("../mgr/myApplication.jsp");
			if(switchEnvironment()){
				mv.addObject("switchEnvironmentFlag",true);
			}else{
				mv.addObject("switchEnvironmentFlag",false);
			}
			return mv; 	 
	}
	
	@RequestMapping(value = "/mgr/statistics", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView statistics(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
	        ModelAndView mv = new ModelAndView();
	       // mv.addObject("appInfo",1);

			mv.setViewName("../statistics.jsp");
			return mv;
	}
 
	@RequestMapping(value = "/mgr/applicationList", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String applicationList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		  Map map = new HashMap();
		  Org orgBean = getOrg(request);
		  String path=request.getContextPath();
		  JSONObject json = new JSONObject();
		  String appName = getRequestValue(request, "appName");
			if(appName!=null&&!"".equals(appName))
			map.put("appName", appName);
		    map.put("appDeve", orgBean.getOrgId().toString()) ;
            map.put("pro_mysql", 0);
            map.put("page_record", 2000);
		    List<Map> appApiNumList = devMgrService.queryAppApiList(map);
			StringBuilder returnDesc=new StringBuilder();
		    String statusCd=null;
		    String statusClass=null;
		    
			for(Map resultMap:appApiNumList){
				statusCd=resultMap.get("APP_STATE").toString();
				String amount = "0.00";
				if(resultMap.get("AMOUNT") != null){
					amount = String.valueOf(resultMap.get("AMOUNT"));
				}
				//A = new ,B = approval, D= on line, X =DELETE
				if("D".equals(statusCd))statusClass="category_1";
				else if("A".equals(statusCd))statusClass="category_2";
				else if("B".equals(statusCd))statusClass="category_3";
				else if("X".equals(statusCd))statusClass="category_4";
				else statusClass="category_2";
				returnDesc.append("<div class=\"col-md-3 col-sm-4 mix ").append(statusClass).append("\" style=\"display:inline-block;\">");
				returnDesc.append("<div class=\"mix-inner\"> ");
				if(resultMap.get("S_FILE_ID") != null && !"".equals(resultMap.get("S_FILE_ID"))){
					returnDesc.append("<img class=\"img-responsive\" src=\"").append(path).append("/provider/readImg.shtml?sFileId=").append(resultMap.get("S_FILE_ID")).append("\" alt=\"\"> ");
				}else{
					returnDesc.append("<img class=\"img-responsive\" src=\"").append(path).append("/resources/img/default.jpg\" alt=\"\"> ");
				}
				returnDesc.append("<a href=\"javascript:showAppDetail('").append(resultMap.get("APP_ID")).append("');\" class=\"s-mix-link\">VIEW MORE</a>");
				returnDesc.append("<div class=\"mix-title\"> <strong>").append(resultMap.get("APP_NAME")).append("</strong> <b> Amount of consumption:<a href=\"mgr/bill.jsp\">").append(amount).append("</a></b> </div>");
				returnDesc.append("</div></div>");
			}
		    
		    //Map appStateMap = getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_COMAPP) ;
		    //Map orgStateMap = getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG) ;
 
			//request.setAttribute("appApiNumList", appApiNumList);
			//request.setAttribute("appStateMap", appStateMap);
			//request.setAttribute("appStateMap", orgStateMap);

			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,returnDesc.toString());
			return json.toString();
	}
	
 /**
  * apiOfferInsert:(保存Offer订购). <br/> 
  * @author m 
  */
	@RequestMapping(value = "/mgr/apiOfferInsert", method = RequestMethod.POST)
	@ResponseBody
	public String apiOfferInsert(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		 JSONObject json = new JSONObject();
		 App app=new App();
		 Map temp = new HashMap();
		 String apiId= getRequestValue(request, "apiIds");
		 String offerId="";
		 try{
			 if(null!=app.getAppDeve()&&null!=app.getAppId()){
		  		if(offerId!=null){ 
		  			devMgrService.apiOfferInsertSrv(temp,offerId,apiId); 
		  		}
	    }
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString() ;
	}
	
	/**
	 * 获取Offer列表
	 * @param prar
	 * @return
	 */
	@RequestMapping(value = "/mgr/apiOfferList")
	@ResponseBody
	public String apiOfferList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		JSONArray dataList=new JSONArray();
		
		Map temp = new HashMap(); 
		Map paramMap = new HashMap();
		
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
	    //总记录数-未带参数查询
	    int recordsTotal = 0;
		temp.put("pro_mysql", startRow);
		temp.put("page_record", rows);
		
		
		String apiIds = getRequestValue(request, "checkApiIds");
		String offerName = getRequestValue(request, "OfferName");
		if(apiIds.length()>0){
			temp.put("API_IDS", apiIds);
		}
		if(offerName.length()>0){
			temp.put("offerName", offerName);
		}
		List<Map> list = null ;
		
		list =devMgrService.getOffersIds(temp);
		if(list.size()>0){
	    recordsTotal =list.size();
	    String orgNames = "";
		String offersIds ="";
		for(int i=0; i<list.size();i++){
			Map tempx = list.get(i);
			if(i==0) { 
				if(!"".equals(StringUtil.valueOf(tempx.get("ORG_ID")))&&null!=StringUtil.valueOf(tempx.get("ORG_ID"))){
					orgNames = "'"+tempx.get("ORG_ID").toString()+"'";
					//org.add(temp);
				} 
				
				offersIds = tempx.get("PROD_OFFER_ID").toString();
			 }else{
				 if(!orgNames.contains("'"+tempx.get("ORG_ID")+"'")){
					 orgNames =orgNames+ "'"+tempx.get("ORG_ID").toString()+"'"; 
					// org.add(temp);
				 }
				offersIds =offersIds+","+tempx.get("PROD_OFFER_ID");
			 }
		}
		temp.put("PROD_OFFER_ID", offersIds);

		paramMap =temp;
		dataList = devMgrService.apiOfferListSrv(temp);
 
		}
		json.put(RETURN_CODE, RESULT_OK);
		json.put("draw", draw);
		json.put("recordsTotal",recordsTotal);
		json.put("recordsFiltered", devMgrService.queryOffersInfoCount(paramMap));
		json.put("data",dataList.toJSONString());
	} catch (Exception e) {
		json.put(RETURN_CODE, RESULT_ERR);
		json.put(RETURN_DESC,e.getMessage());
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
	}
	return json.toString() ;
		 
	}
	
	/**
	 * 获取Api列表
	 * @param prar
	 * @return
	 */
	@RequestMapping(value = "/mgr/selectApiInfoList")
	@ResponseBody
	public String selectApiInfoList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		HashMap paramMap = new HashMap();
		//获取请求次数
	    String draw = "0";
	    draw = getRequestValue(request, "draw");
	    //数据起始位置
	    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
	    //数据长度
	    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
	    //过滤后记录数
	    //String recordsFiltered = "";
		//获取当前用户
	    //总记录数-未带参数查询
	    int recordsTotal = devMgrService.selectApiInfoNewCount(paramMap);
		paramMap.put("pro_mysql", startRow);
		paramMap.put("page_record", rows);
		String dirId = getRequestValue(request, "dirId");
  
		String tmpAipName = getRequestValue(request, "tmpAipName");
		String packageName =getRequestValue(request, "packageName");
		String orgName = getRequestValue(request, "orgName");
		String orgTypeCodes = getRequestValue(request, "orgTypeCodes");
			if(!"".equals(tmpAipName)&&null!=tmpAipName){
				paramMap.put("apiNames", tmpAipName);
			}
			if(!"".equals(packageName)&&null!=packageName){
				paramMap.put("packageName", packageName);
			}
			if(!"".equals(orgName)&&null!=orgName){
				paramMap.put("orgName", orgName);
			}
			if(!"".equals(dirId)&&null!=dirId){
				paramMap.put("DIR_ID", dirId);
			}if(!"".equals(orgTypeCodes)&&null!=orgTypeCodes){
				paramMap.put("ORG_TYPE_CODES", orgTypeCodes);
			}
			
			
			List<Map> apiInfoList = devMgrService.selectApiInfoNew(paramMap);	 
			JSONArray dataList=new JSONArray();
			JSONArray subDataList=null;
			String name ="";
			for(Map pardSpecMap:apiInfoList){
				subDataList=new JSONArray();
				//name=(String) pardSpecMap.get("API_NAME");
				name ="apiCheck(this,'"+pardSpecMap.get("API_NAME")+"')";
				subDataList.add("<input type='checkbox' value='' class='checkboxes' onChange="+name+" id='"+pardSpecMap.get("API_ID")+"' name='' />");
//				subDataList.add(pardSpecMap.get("PROD_OFFER_ID"));
//				subDataList.add(pardSpecMap.get("OFFER_PROVIDER_ID"));
//				subDataList.add(pardSpecMap.get("API_NAME"));
				subDataList.add("<a href='"+request.getContextPath()+"/api/apiDoc.shtml?serviceId="+pardSpecMap.get("API_ID")+"'  target='Blank' >"+pardSpecMap.get("API_NAME")+"</a>");
				subDataList.add(pardSpecMap.get("ORG_NAME"));
				subDataList.add(pardSpecMap.get("API_DESC"));
				dataList.add(subDataList);
			}
			JSONObject json = new JSONObject();
			json.put(RETURN_CODE, RESULT_OK);
			json.put("draw", draw);
			json.put("recordsTotal",recordsTotal);
			json.put("recordsFiltered", devMgrService.selectApiInfoNewCount(paramMap));
			json.put("data",dataList.toJSONString());
			return json.toString();
	 
	}
 
	/**
	 * 
	 * updateAppStateByAppId:(通过appId改变app状态，上线待审核，更新状态，下线，删除). <br/>   
	 * 
	 * @author m  
	 */
	@SavePermission(center="dev",module="app",parameterKey="appId",privilege="")
	@RequestMapping(value = "/mgr/updateAppStateByAppId")
	@ResponseBody
	public ModelAndView updateAppStateByAppId(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		JSONObject json = new JSONObject();
		String message = "";
		String appId= getRequestValue(request, "appId");
		String appName= getRequestValue(request, "appName");
		String appstate= getRequestValue(request, "appstate");
		com.alibaba.fastjson.JSONObject retJson = new com.alibaba.fastjson.JSONObject();
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		Org org = (Org)request.getSession().getAttribute("userBean");
		App app = new App();
		try{
			app.setAppId(Integer.parseInt(appId));
			List<App> appList = devMgrService.selectAPP(app);
			if(null!=appList&&appList.size()==1){
				App appTmp = appList.get(0);
				if("X".equals(appstate)||"G".equals(appstate)||"E".equals(appstate)){
					app.setAppState(appstate);
					devMgrService.updateApp(app) ;
				}else if (EAAPConstants.COMM_STATE_NEW.equals(appTmp.getAppState())||"E".equals(appTmp.getAppState())) {
					jsonObject.put("contentId", appId);
					String processName = null;
					if("A".equals(appTmp.getAppState())){
						processName = "APP Process Audit Name:"+appName;
					}else if("E".equals(appTmp.getAppState())){
						processName = "APP Process Upgrade Name:"+appName;
					}
					UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
					String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
					Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
					if(userRoleInfo != null){
						userId = tenantService.qryAdminIdByTenantId(userRoleInfo.getTenantId());
						tenantId = userRoleInfo.getTenantId();
					}
					retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_APPONLINE, processName,
							userId, tenantId, jsonObject.toJSONString());
					String returnCode = retJson.getString("code");
					if ("0000".equals(returnCode)) {
						app.setAppState(EAAPConstants.COMM_STATE_WAITAUDI);
						app.setAudutFlowId(retJson.getString("desc"));
						devMgrService.updateApp(app) ;
					}
				} else if(EAAPConstants.COMM_STATE_NOPASSAUDI.equals(appTmp.getAppState())||EAAPConstants.COMM_STATE_NOUPGRADE.equals(appTmp.getAppState())){
					message = WorkRestUtils.taskListByProcessId(appTmp.getAudutFlowId());
					if(!StringUtils.isEmpty(message)){
						com.alibaba.fastjson.JSONArray jsons = JSON.parseArray(message);
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
											EAAPConstants.WORK_FLOW_MESSAGE_QUERY_APP.replace("#id", appId));
									messageServ.lookMsgById(String.valueOf(org.getOrgId()),String.valueOf(msgId));
									messageServ.updateMsgForWorkFlow(msgId);
									
									app.setAppState(EAAPConstants.COMM_STATE_WAITAUDI);
									devMgrService.updateApp(app) ;
								}
							}
						}
					}
				}
			}
			mv.setViewName("redirect:/mgr/manageDevMgr.shtml?appId="+appId);
		}catch (Exception e) {
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return mv ;
	}
	/**
	 * delete package
	 */
	@SavePermission(center="dev",module="app",parameterKey="appId",privilege="")
	@RequestMapping(value = "/mgr/delOfferofApp")
	@ResponseBody
	public String delOfferofApp(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		try{
			Org orgBean = getOrg(request); 
			
			//app.setAppDeve(Integer.valueOf(orgBean.getOrgId().toString())) ;
			String pkgId = request.getParameter("delOfferId");
			String appId = request.getParameter("appId");
			String orgId = orgBean.getOrgId().toString();
			HashMap temp = new HashMap(); 
			if(!"".equals(pkgId)&&null!=pkgId&&!"".equals(appId)&&null!=appId&&!"".equals(orgId)&&null!=orgId){
				temp.put("org", orgId);
				temp.put("prodOfferId", pkgId);
				temp.put("appId", appId); 
				//获取offer 下api 然后 状态置为D
				temp.put("offerId", pkgId);
				String a = devMgrService.delOfferofApp(temp);
				if("1".equals(a)){
					json.put(RETURN_CODE, RESULT_OK);
				}else{
					json.put(RETURN_CODE, RESULT_ERR);
				}
				
			}
			
			json.put(RETURN_DESC,"Success"); 
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
	return json.toString() ;

	}
	
    public Map getMainInfo(String mainTypeSign){
  	  MainDataType mainDataType = new MainDataType();
  	  MainData mainData = new MainData();
  	  Map stateMap = new HashMap() ;
  	  mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ; 
  	  mainDataType.setMdtSign(mainTypeSign) ;
		  mainDataType = devMgrService.selectMainDataType(mainDataType).get(0) ;
		  mainData.setMdtCd(mainDataType.getMdtCd()) ;
		  mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
		  List<MainData> mainDataList = devMgrService.selectMainData(mainData) ;
		 
		  if(mainDataList!=null && mainDataList.size()>0){
			  for(int i=0;i<mainDataList.size();i++){
				  stateMap.put(mainDataList.get(i).getCepCode(),
						          mainDataList.get(i).getCepName()) ;
			  }
		  }
  	
  	return  stateMap ;
  }
	public static String makeAwardNo(long lngMax){
		return String.valueOf(Math.abs(OWASPUtil.randomLong() % lngMax));
	}
	public String saveComponent(String appName,int userId){
	     
	    Component component = new Component();
		component.setOrgId(userId);
		component.setName(appName) ;
		component.setComponentTypeId(2) ; 
		return devMgrService.saveCom(component) ; 
		 
	 }
	


	 //定价信息转化为文字

	 
    // 加载国际化信息
	 public List<String>  getUTC(){
		 List<String> messages = new ArrayList<String>();
		    messages.add("eaap.op2.portal.devmgr.appDes");
			messages.add("eaap.op2.portal.devmgr.appName");
			messages.add("eaap.op2.portal.devmgr.appType");
			messages.add("eaap.op2.portal.devmgr.appAddress");
			messages.add("eaap.op2.portal.devmgr.appCallBackUrl");
			messages.add("eaap.op2.portal.devmgr.useApp");
			messages.add("eaap.op2.portal.devmgr.updateAppKey");
			messages.add("eaap.op2.portal.devmgr.addAppAbr");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.devmgr.appOnline");
			messages.add("eaap.op2.portal.devmgr.packageName");
			messages.add("eaap.op2.portal.devmgr.APIName");
			messages.add("eaap.op2.portal.price.pricePlan");
			messages.add("eaap.op2.portal.devmgr.packageDesc");
			messages.add("eaap.op2.portal.devmgr.todosomething");
			messages.add("eaap.op2.portal.devmgr.appSecret");
			messages.add("eaap.op2.portal.devmgr.comid");
			messages.add("eaap.op2.portal.devmgr.createtime");
			messages.add("eaap.op2.portal.devmgr.addApp");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.devmgr.orgName");
			messages.add("eaap.op2.portal.devmgr.apidesc");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.devmgr.ok");
			messages.add("eaap.op2.portal.devmgr.apitype");
			messages.add("eaap.op2.portal.devmgr.appOnline");
			messages.add("eaap.op2.portal.devmgr.appupgrade");
			messages.add("eaap.op2.portal.devmgr.appcancel");
			messages.add("eaap.op2.portal.devmgr.appdelete");
			messages.add("eaap.op2.portal.devmgr.basicInfo");
			
			messages.add("eaap.op2.portal.devmgr.createApplication");
			messages.add("eaap.op2.portal.index.home");
			messages.add("eaap.op2.portal.devmgr.devCentro");  
			messages.add("eaap.op2.portal.devmgr.updateAppKey"); 
			messages.add("eaap.op2.portal.devmgr.appIntro"); 
			messages.add("eaap.op2.portal.devmgr.appImage");
			messages.add("eaap.op2.portal.devmgr.appImageDesc");
			messages.add("eaap.op2.portal.index.SysName");
			messages.add("eaap.op2.portal.devmgr.imageUpload");
			messages.add("eaap.op2.portal.doc.message.edit");
			messages.add("eaap.op2.portal.portalMessage.history");
			return messages;
	 }
	 
	 @RequestMapping(value = "/mgr/realTimeBill")
		@ResponseBody
		public ModelAndView realTimeBill (final HttpServletRequest request,
				final HttpServletResponse response) throws Exception {
			// 添加页面上需要国际化的消息
			//公共
			List<String> messages = getUTC();
			ModelAndView mv = new ModelAndView();
			addTranslateMessage(mv, messages);
			
			mv.setViewName("../mgr/realTimeBill.jsp");
			return mv;  
		}
	 
	 public boolean switchEnvironment(){
			String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
			if("true".equals(switchEnvironmentFlag)){
				return true;
			}
			return false;
			
		}
}
