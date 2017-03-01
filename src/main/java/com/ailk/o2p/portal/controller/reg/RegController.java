package com.ailk.o2p.portal.controller.reg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.o2p.common.security.SecurityUtil;
import com.ailk.eaap.o2p.common.util.date.UTCTimeUtil;
import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.bo.OrgRole;
import com.ailk.eaap.op2.bo.hub.HubUrlParam;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.controller.busiDataDeal.BusiDataInfoDeal;
import com.ailk.o2p.portal.service.busiDataInfo.IBusiDataInfoServ;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.reg.IRegService;
import com.ailk.o2p.portal.utils.DictDataUtil;
import com.ailk.o2p.portal.utils.SHA256;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.AuthenticationUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: RegController
 * @Description: 
 * @author zhengpeng
 * @date 2015-6-8 上午10:47:51
 *
 */
@Controller
public class RegController extends BaseController{
	
	private static Logger log = Logger.getLog(RegController.class);
	@Autowired
	private IRegService regService;
	@Autowired
	private IMessageServ messageServ;
	@Autowired
	private IBusiDataInfoServ busiDataInfoServ;
	@Autowired
	private MainDataServ mainDataServ;
	
	public static final String APP_TOKEN_INVALID_CODE=WebPropertyUtil.getPropertyValue("app_token_invalid_code");
	public static final String IS_NEED_APP_HUB=WebPropertyUtil.getPropertyValue(EAAPConstants.IS_NEED_APP_HUB);
	
	@PostConstruct
	public void init(){
		List<Map<String,Object>> provinceList = this.getProvinceList();
		DictDataUtil.setProvinceList(provinceList);
		Map<String,List<Map<String,Object>>> cityList = this.getCityList();
		DictDataUtil.setCityList(cityList);
	}
	
	
	private Map<String,List<Map<String,Object>>> getCityList(){
		Org orgBean = new Org();
		List<Map<String,Object>> cityList = regService.queryCityForReg(orgBean);
		Map<String,List<Map<String,Object>>> provCityMap = new HashMap<String,List<Map<String,Object>>>();
		for(Map<String,Object> cityMap : cityList){
			String provinceId = String.valueOf(cityMap.get("PROVINCEID")); 
			if(provCityMap.containsKey(provinceId.trim())){
				List<Map<String,Object>> _cityList = provCityMap.get(provinceId);
				_cityList.add(cityMap);
			}else{
				List<Map<String,Object>> _cityList = new ArrayList<Map<String,Object>>();
				_cityList.add(cityMap);
				provCityMap.put(provinceId, _cityList);
			}
		}
		return provCityMap;
	}
	
	private List<Map<String,Object>> getProvinceList(){
		Org orgBean = new Org();
		return regService.queryProvinceForReg(orgBean);
		 
	}
	
	
	@RequestMapping(value = "/org/getRegInitData", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String getRegInitData(HttpServletRequest request){
		String orgType = getRequestValue(request, "orgType");
		String provinceId = getRequestValue(request, "provinceId");
		List<Map<String,String>> certTypeList = null;
		JSONObject json = new JSONObject();
		if("1".equals(orgType)){//1个人，2企业
			certTypeList = mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG_CERT_TYPE);
		}
		Map<String,List<Map<String,Object>>> allCityMap = DictDataUtil.getCityList();
		List<Map<String,Object>> cityList = allCityMap.get(provinceId);
		json.put("certTypeList", certTypeList);
		json.put("cityList", cityList);
		return json.toString();
	}
	
	@RequestMapping(value = "/org/getCityList", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String getCityList(HttpServletRequest request){
		String provinceId = getRequestValue(request, "provinceId");
		JSONObject json = new JSONObject();
		Map<String,List<Map<String,Object>>> allCityMap = DictDataUtil.getCityList();
		List<Map<String,Object>> cityList = allCityMap.get(provinceId);
		json.put("cityList", cityList);
		return json.toString();
	}
	
	@RequestMapping(value = "/org/getCertTypeList", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String getCertTypeList(HttpServletRequest request){
		String orgType = getRequestValue(request, "orgType");
		List<Map<String,String>> certTypeList = null;
		JSONObject json = new JSONObject();
	   if("1".equals(orgType)){//1个人，2企业
			certTypeList =mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG_CERT_TYPE);
		}
		json.put("certTypeList", certTypeList);
		return json.toString();
	}
	
	@RequestMapping(value = "/org/reg")
	@UnPermission
	public ModelAndView orgReg(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.reg.userName");
		messages.add("eaap.op2.portal.reg.regPassword");
		messages.add("eaap.op2.portal.reg.regPapersNUm");
		messages.add("eaap.op2.portal.reg.indenEffDate");
		messages.add("eaap.op2.portal.reg.indenExpDate");
		messages.add("eaap.op2.portal.reg.indenAddress");
		messages.add("eaap.op2.portal.reg.regEmail");
		messages.add("eaap.op2.portal.reg.regSubEmail");
		messages.add("eaap.op2.portal.reg.regVerifiaction");
		messages.add("eaap.op2.portal.reg.regPersonal");
		messages.add("eaap.op2.portal.reg.regcompany");
		messages.add("eaap.op2.portal.reg.regFirstName");
		messages.add("eaap.op2.portal.reg.regLastName");
		messages.add("eaap.op2.portal.reg.phone");
		messages.add("eaap.op2.portal.reg.subPhone");
		messages.add("eaap.op2.portal.reg.introduction");
		messages.add("eaap.op2.portal.reg.reTypePwd");
		messages.add("eaap.op2.portal.reg.enterpriseName");
		messages.add("eaap.op2.portal.reg.legalName");
		messages.add("eaap.op2.portal.reg.regReadOne");
		messages.add("eaap.op2.portal.reg.regReadTwo");
		messages.add("eaap.op2.portal.reg.regEnterInfo");
		messages.add("eaap.op2.portal.reg.regEnterAuthInfo");
		messages.add("eaap.op2.portal.reg.address");
		messages.add("eaap.op2.portal.emailValidate");
		
		messages.add("eaap.op2.portal.reg.partnerTypeForOperator");
		messages.add("eaap.op2.portal.reg.partnerTypeForNormalPartner");
		
		ModelAndView mv = new ModelAndView();
		//是否是cloud的标识
		String  o2pCloudFlag=WebPropertyUtil.getPropertyValue("o2p_web_domin");
		mv.addObject("o2pCloudFlag",o2pCloudFlag);
		//是否是apphub接入标识
		String isNeedAppHub=IS_NEED_APP_HUB;
		mv.addObject("isNeedAppHub",isNeedAppHub);
		
		List<Map<String,Object>> provinceList =null;
		provinceList = this.getProvinceList();
		mv.addObject("provinceList", provinceList);
		
		if(isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.setViewName(tenantId+"/"+template+"/org/reg.htm"); 
		}else{
			mv.setViewName("../org/reg.jsp");
		}
		addTranslateMessage(mv, messages);
		return mv;
	}
	
	private String encryMsg(String msg){
		String encryMsg = SecurityUtil.getInstance().encryMsg(msg);
		if(encryMsg == null){
			encryMsg = msg;
		}
		return encryMsg;
	}
	
	@RequestMapping(value = "/org/saveOrg", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String saveOrg(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String verifyCode = getRequestValue(request, "verifyCode"); 
		String password = getRequestValue(request, "password"); 
		String email = getRequestValue(request, "email");
		String subEmail=getRequestValue(request, "subEmail");
		String cityId = getRequestValue(request, "cityId"); 
		String address = getRequestValue(request, "address"); 
		String certNumber = getRequestValue(request, "idNumber");  
		String telephone = getRequestValue(request, "telephone");
		String subTelephone = getRequestValue(request, "subTelephone");
		String firstName = getRequestValue(request, "firstName");
		String lastName = getRequestValue(request, "lastName"); 
		String enterpriseName = getRequestValue(request, "enterpriseName"); 
		String certType = getRequestValue(request, "certType"); 
		String orgType = getRequestValue(request, "orgType"); 
		String partnerType=getRequestValue(request, "partnerType"); 
		String userName = getRequestValue(request, "userName"); 
		String introduction = getRequestValue(request, "introduction"); 
		String indenEffDate= getRequestValue(request, "indenEffDate"); 
		String indenExpDate=getRequestValue(request, "indenExpDate"); 
		String indenAddress=getRequestValue(request, "indenAddress");
		String legalName=getRequestValue(request, "legalName");
        String rand = (String)session.getAttribute("rand");
        Map<String,String> appHubMap= queryIsNeedApphub(request);
        String isNeedAppHub=appHubMap.get("isNeedAppHub");
		String appTokenStr = appHubMap.get("appTokenStr");
		String apphubTenantId= appHubMap.get(EAAPConstants.HUB_TENANT);
    	
    	if(!StringUtil.isEmpty(verifyCode) && (verifyCode.equals(rand) || "O2P".equals(verifyCode))){

			Org org = new Org();
			OrgRole orgRole = new OrgRole();
			org.setEmail(email);
			if(!StringUtil.isEmpty(subEmail)){
				org.setSubEmail(subEmail);
			}
			org.setCertNumber(certNumber);
			org.setTelephone(telephone); 
			if(!StringUtil.isEmpty(subTelephone)){
				org.setSubTelephone(subTelephone);
			}
			org.setOrgUsername(userName); 
			org.setOrgPwd(password); 
			if("1".equals(orgType)){		//1个人，2企业
				org.setName(firstName + " " + lastName);
			}else if("2".equals(orgType)){
				org.setName(enterpriseName); 
			}
			if (!"".equals(certType)) {
				org.setCertTypeCode(certType);
			}
			org.setOrgTypeCode(orgType);
			org.setPartnerType(partnerType);
			org.setAreaId(cityId); 
			org.setAddress(address);
			org.setDescriptor(introduction); 
			if(!Strings.isEmpty(indenAddress)){
				org.setIndenAddress(indenAddress);
			}
			if(!Strings.isEmpty(indenEffDate)){
				org.setIndenEffDate(indenEffDate);
			}
			if(!Strings.isEmpty(indenExpDate)){
				org.setIndenExpDate(indenExpDate);
			}
			if(!Strings.isEmpty(legalName)){
				org.setLegalName(legalName);
			}
			orgRole.setRoleCode(orgType); 
			
			json=validateWithException(org);		//验证输入是否符合规范
			String code = String.valueOf(json.get("code"));
			if(BaseController.RESULT_OK.equals(code)){			//说明验证通过
				//加密(证件号、电话、邮箱)：
				email = this.encryMsg(email);
				org.setEmail(email);
				if(!StringUtil.isEmpty(subEmail)){
					subEmail=this.encryMsg(subEmail);
					org.setSubEmail(subEmail);
				}
				certNumber = this.encryMsg(certNumber);
				org.setCertNumber(certNumber);
				telephone = this.encryMsg(telephone);
				org.setTelephone(telephone); 
				if(!StringUtil.isEmpty(subTelephone)){
					subTelephone=this.encryMsg(subTelephone);
					org.setSubTelephone(subTelephone);
				}
				//查询序列获取orgId
				Integer orgId=regService.queryOrgIdSeq();
				org.setOrgId(orgId);
				if("true".equals(isNeedAppHub)){//appHub注册认证
					//为了注册认证传入partyId
					HubUrlParam hubUrlParam=registAuthInputParam(appTokenStr,org,apphubTenantId);
					Map<String, String> resultMap=getAuthResult(hubUrlParam,"1");
					Map<String,String> returnMap=authResultDeal(resultMap,org,request,null,"1");
					json.put("HUB_RETURN_CODE", returnMap.get("HUB_RETURN_CODE"));
					json.put("HUB_RETURN_DESC",returnMap.get("HUB_RETURN_DESC"));
					if("0000".equals(returnMap.get("HUB_RETURN_CODE"))){
						try {
							//验证用户名是否存在
							Org qryOrg = new Org();
							qryOrg.setOrgUsername(userName);
							List orgList = regService.selectOrg(qryOrg);
							if(orgList!=null&&orgList.size()>0){
								json.put(RETURN_CODE,"9999");
								json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.registeredFail"));
							}else{
								String passwd=SHA256.encode(org.getOrgPwd());
								org.setOrgPwd(passwd);
								regService.saveOrg(org, orgRole);
								json.put(RETURN_CODE, RESULT_OK);
								json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.registeredSucc"));
							}
							
						} catch (Exception e) {
							json.put(RETURN_CODE,"9999");
							json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.registeredFail"));
							String errorMsg = CommonUtil.getErrMsg(e);
							log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"saveOrg exception:" + errorMsg,e));
						}
					
					}
				}else{
					//验证用户名是否存在
					Org qryOrg = new Org();
					qryOrg.setOrgUsername(userName);
					List orgList = regService.selectOrg(qryOrg);
					if(orgList.isEmpty()){
						try{
								regService.saveOrg(org, orgRole);
								json.put(RETURN_CODE, RESULT_OK);
								json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.registeredSucc"));
						}catch (Exception e) {
							json.put(RETURN_CODE,"9999");
							json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.registeredFail"));
							String errorMsg = CommonUtil.getErrMsg(e);
							log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"saveOrg exception:" + errorMsg,e));
						}
					//用户名已经存在
					}else{
						json.put(RETURN_CODE,"9999");
						json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.userNameAlready"));
					}
				}
			}
		 }else{
			json.put(RETURN_CODE, "9999");
			json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.login.authcodeerror"));
		 }
		 return json.toString();
	}
	
	@RequestMapping(value = "/org/changePwd")  
	public ModelAndView changePassword(){
		List<String> messages = new ArrayList<String>(); 
		messages.add("eaap.op2.portal.reg.infoBasicInfo"); 
		messages.add("eaap.op2.portal.reg.infoChangPwd"); 
		messages.add("eaap.op2.portal.reg.infoUserInfo");
		messages.add("eaap.op2.portal.reg.submit");
		messages.add("eaap.op2.portal.reg.cancel");
		messages.add("eaap.op2.portal.reg.infoEdit");
		messages.add("eaap.op2.portal.reg.oldPwd");
		messages.add("eaap.op2.portal.reg.newPwd");
		messages.add("eaap.op2.portal.reg.repeat");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		//是否是apphub接入标识
		String isNeedAppHub=IS_NEED_APP_HUB;
		mv.addObject("isNeedAppHub",isNeedAppHub);
		
		mv.setViewName("../org/changePassword.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/org/updateOrg", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateOrg(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		String name = getRequestValue(request, "name");
		String email = getRequestValue(request, "email");
		String subEmail=getRequestValue(request, "subEmail");
		String phone = getRequestValue(request, "phone");
		String subTelephone = getRequestValue(request, "subTelephone");
		String certNumber = getRequestValue(request, "certNumber");
		String orgTypeCode = getRequestValue(request,"orgTypeCode");
		String orgPartnerType=getRequestValue(request,"orgPartnerType");
		String introduction = getRequestValue(request, "introduction");
		String sFileId = getRequestValue(request, "sFileId");
		String areaId = getRequestValue(request, "areaId");
		String certTypeCode = getRequestValue(request, "certTypeCode");
		String address = getRequestValue(request, "address");
		String indenEffDate= getRequestValue(request, "indenEffDate"); 
		String indenExpDate=getRequestValue(request, "indenExpDate"); 
		String indenAddress=getRequestValue(request, "indenAddress");
		String legalName=getRequestValue(request, "legalName");
		try{
			Org orgBean = (Org) request.getSession().getAttribute("userBean");
			if(orgBean != null){
				if(!StringUtil.isEmpty(name)){
					orgBean.setName(name);
				}
				//加密(证件号、电话、邮箱)  *号的判断针对模糊化的处理
				if(!StringUtil.isEmpty(email)&&!email.contains("*")){
					email = this.encryMsg(email);
					orgBean.setEmail(email);
				}
				if(!StringUtil.isEmpty(subEmail)&&!subEmail.contains("&nbsp;")&&!subEmail.contains("*")){
					subEmail = this.encryMsg(subEmail);
					orgBean.setSubEmail(subEmail);
				}
				if(!StringUtil.isEmpty(phone)&&!phone.contains("*")){
					phone = this.encryMsg(phone);
					orgBean.setTelephone(phone);
				}
				if(!StringUtil.isEmpty(subTelephone)&&!subTelephone.contains("&nbsp;")&&!subTelephone.contains("*")){
					subTelephone = this.encryMsg(subTelephone);
					orgBean.setSubTelephone(subTelephone);
				}
				if(!StringUtil.isEmpty(certNumber)){
					certNumber = this.encryMsg(certNumber);
					orgBean.setCertNumber(certNumber);
				}else{
					orgBean.setCertNumber(certNumber);
				}
				if(!StringUtil.isEmpty(orgTypeCode)){
					orgBean.setOrgTypeCode(orgTypeCode); 
				}
				if(!StringUtil.isEmpty(orgPartnerType)){
					orgBean.setPartnerType(orgPartnerType);
				}
				if(!StringUtil.isEmpty(sFileId)){
					orgBean.setSFileId(Integer.valueOf(sFileId)); 
				}
				if(!StringUtil.isEmpty(areaId)){
					orgBean.setAreaId(areaId); 
				}
				orgBean.setCertTypeCode(certTypeCode); 
				orgBean.setIndenAddress(indenAddress);
				if(StringUtil.isEmpty(indenEffDate)){
					orgBean.setIndenEffDate(null);
				}else{
					orgBean.setIndenEffDate(indenEffDate);
				}
				if(StringUtil.isEmpty(indenExpDate)){
					orgBean.setIndenExpDate(null);
				}else{
					orgBean.setIndenExpDate(indenExpDate);
				}
				orgBean.setLegalName(legalName);
				orgBean.setDescriptor(introduction);
				orgBean.setAddress(address);
				orgBean.setState(null);
				regService.updateOrgInfo(orgBean);
				//消息已读
				String msgId = getRequestValue(request, "message.msgId");
				if("C".equals(orgBean.getState())){
					if(msgId!=null&&!"".equals(msgId)){
						messageServ.lookMsgById(String.valueOf(orgBean.getOrgId()),msgId);
					}else{
						messageServ.lookMsgById(String.valueOf(orgBean.getOrgId()), 
								String.valueOf(messageServ.getMsgIdByQueryTitle(
										EAAPConstants.WORK_FLOW_MESSAGE_QUERY_ORG.replace("#id", String.valueOf(orgBean.getOrgId())))));
					}
				}
				
				orgBean = this.getUserInfo(orgBean); 
				if(!StringUtil.isEmpty(sFileId)){
					orgBean.setFileIdString(sFileId);
					request.getSession().setAttribute("userBean",orgBean);
				}
				json.put(RETURN_CODE, RESULT_OK);
				json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.userUpdated"));
			}else{
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.userUpdatedFail"));
			} 
		}catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"updateOrg exception:" + errorMsg,e));
		}
		return json.toString();
	}
	

	@RequestMapping(value = "/org/submitToCheck", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String submitToCheck(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			Org orgBean = (Org) request.getSession().getAttribute("userBean");
			Org org = new Org();
			org.setOrgId(orgBean.getOrgId());
			org = this.getUserInfo(org);
			if(org != null){
				String message = "";
				RestTemplate rest = new RestTemplate();
				com.alibaba.fastjson.JSONObject retJson = null;
				com.alibaba.fastjson.JSONArray jsons =null;
				com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();

				Map<String, Object> taskVariables = new HashMap<String, Object>();
				String wordFlowUrl = WebPropertyUtil.getPropertyValue("work_flow_pro_url");
				message = rest.getForObject(wordFlowUrl+"/task/taskListByProcessId/{processId}",String.class,String.valueOf(org.getAuditFlowId()));
				if(!StringUtils.isEmpty(message)){
					jsons = JSON.parseArray(message);
					if(null!=jsons&&jsons.size()==1){
						retJson = jsons.getJSONObject(0);
						String taskId = retJson.getString("taskId");
						taskVariables.put("taskId", taskId);
						taskVariables.put("varJson", jsonObject.toJSONString());
						String ret = rest.postForObject(wordFlowUrl+"/task/completeTask?taskId={taskId}&varJson={varJson}",null, String.class,taskVariables);
						if(!StringUtils.isEmpty(ret)){
							retJson = JSON.parseObject(ret);
							String returnCode = retJson.getString("code");
							if ("0000".equals(returnCode)) {
								//置待办消息的状态
								BigDecimal msgId = messageServ.getMsgIdByQueryTitle(EAAPConstants.WORK_FLOW_MESSAGE_QUERY_ORG.replace("#id", org.getOrgId().toString()));
								messageServ.lookMsgById(String.valueOf(org.getOrgId()),String.valueOf(msgId));
								messageServ.updateMsgForWorkFlow(msgId);
								//将Org状态改为待审核B：
								org.setState(EAAPConstants.COMM_STATE_WAITAUDI);		
								regService.updateOrgInfo(org);
								//反回成功：
								json.put(RETURN_CODE, RESULT_OK);
								json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalSuccess"));
							}else{
								//反回失败：
								json.put(RETURN_CODE, RESULT_ERR);
								json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalFail"));
							} 
						}else{
							//反回失败：
							json.put(RETURN_CODE, RESULT_ERR);
							json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalFail"));
						} 
					}else{
						//反回失败：
						json.put(RETURN_CODE, RESULT_ERR);
						json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalFail"));
					} 
				}else{
					//反回失败：
					json.put(RETURN_CODE, RESULT_ERR);
					json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalFail"));
				}
			}else{
				//反回失败：
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.submitApprovalFail"));
			} 
		}catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"Submit for Approval Exception:" + errorMsg,e));
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/org/doChangePwd", method = RequestMethod.POST)
	@ResponseBody
	public String doChangePassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try{
			Org orgBean = (Org) request.getSession().getAttribute("userBean");
		    Map<String,String> appHubMap= queryIsNeedApphub(request);
	        String isNeedAppHub=appHubMap.get("isNeedAppHub");
			String appTokenStr = appHubMap.get("appTokenStr");
			String apphubTenantId= appHubMap.get(EAAPConstants.HUB_TENANT);
				
			if(orgBean != null){
				String oldPwd = getRequestValue(request, "oldPwd"); 
				String newPwd = getRequestValue(request, "newPwd"); 
				if("true".equals(isNeedAppHub)){//appHub注册认证
					orgBean.setOrgPwd(newPwd);
					HubUrlParam hubUrlParam=changePwdAuthInputParam(appTokenStr,orgBean,oldPwd,apphubTenantId);
					Map<String, String> resultMap=getAuthResult(hubUrlParam,"2");
					Map<String,String> returnMap=authResultDeal(resultMap,orgBean,request,oldPwd,"2");
					json.put("HUB_RETURN_CODE", returnMap.get("HUB_RETURN_CODE"));
					json.put("HUB_RETURN_DESC",returnMap.get("HUB_RETURN_DESC"));
					if("0000".equals(returnMap.get("HUB_RETURN_CODE"))){
						orgBean.setState(null);
						orgBean.setPwdUpdateDate(UTCTimeUtil.getUTCTime(new Date()));
						regService.updateOrgInfo(orgBean);
						json.put(RETURN_CODE, RESULT_OK);
						json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.pwdModiSucc"));
					}
				}else{
					if(oldPwd.equals(orgBean.getOrgPwd())){
						orgBean.setOrgPwd(newPwd);
						orgBean.setState(null);
						orgBean.setPwdUpdateDate(UTCTimeUtil.getUTCTime(new Date()));
						regService.updateOrgInfo(orgBean);
						json.put(RETURN_CODE, RESULT_OK);
						json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.pwdModiSucc"));
					}else{
						json.put(RETURN_CODE, RESULT_ERR);
						json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.oriPwdNotCorrect"));
					}
				}
			}else{
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.pwdModiFail"));
			} 
		}catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.reg.pwdModiFail"));
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"doChangePassword exception:" + errorMsg,e));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/org/verifyUserName", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String verifyUserName(HttpServletRequest request,HttpServletResponse response){		
		String result = "true";
		String userName = getRequestValue(request, "userName");
		Org org = new Org();
		if(!StringUtil.isEmpty(userName)){
		    org = new Org();
			org.setOrgUsername(userName);
		}
	    Map<String,String> appHubMap= queryIsNeedApphub(request);
        String isNeedAppHub=appHubMap.get("isNeedAppHub");
		String appTokenStr = appHubMap.get("appTokenStr");
		String apphubTenantId= appHubMap.get(EAAPConstants.HUB_TENANT);
		if("true".equals(isNeedAppHub)){//appHub校验用户是否存在
			result=appHubVerifyUserName(appTokenStr,org,request,apphubTenantId);
		}else{
				JSONObject json = new JSONObject();
				List orgList = regService.selectOrg(org);
				if(orgList != null && !orgList.isEmpty()){
					json.put("result", "false");
					json.put("code", RESULT_OK);
					json.put("desc", "");
					result=json.toString();
				}
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/org/verifyPassword", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String verifyPassword(HttpServletRequest request,HttpServletResponse response){		
		String result = "false";
		String password = getRequestValue(request, "passwordValue");
        String reg ="^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,16}$"; 
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(password);
		if(matcher.find()){
			result = "true";
		}
		return result;
	}
	
	private Org getUserInfo(Org org) {
		Org _org = null;
		List<Org> userList = regService.selectOrg(org);
		if (userList != null && !userList.isEmpty()) {
			_org = (Org) userList.get(0);
			String email = _org.getEmail();
			if (!StringUtils.isEmpty(email)) {
				String decryEmail = this.getDecryMsg(email);
				_org.setEmail(decryEmail);
			}
			String certNumber = _org.getCertNumber();
			if (!StringUtils.isEmpty(certNumber)) {
				String decryCertNumber = this.getDecryMsg(certNumber);
				_org.setCertNumber(decryCertNumber);
			}
			String telephone = _org.getTelephone();
			if (!StringUtils.isEmpty(telephone)) {
				String decryTelephone = this.getDecryMsg(telephone);
				_org.setTelephone(decryTelephone);
			}
		}
		return _org;
	}
	
	private String getDecryMsg(String msg){
		String decryMsg = SecurityUtil.getInstance().decryMsg(msg);
		if(decryMsg == null){
			decryMsg = msg;
		}
		return decryMsg;
	}
	
	@RequestMapping(value = "org/toLoginSuccess")
	@UnPermission
	public ModelAndView toLoginSuccess(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.login.toLogin");
		messages.add("eaap.op2.portal.index.index"); 
		messages.add("eaap.op2.portal.index.signUp");
		messages.add("eaap.op2.portal.reg.registrationInfoOne");
		messages.add("eaap.op2.portal.reg.registrationInfoTwo");
		messages.add("eaap.op2.portal.reg.gotoLogin");
		messages.add("eaap.op2.portal.reg.gotoHome");
		messages.add("eaap.op2.portal.reg.gotoHome");
		messages.add("eaap.op2.portal.reg.successful");
		
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.setViewName(tenantId +"/"+ template+"/org/regSuccess.htm");
		}else{
			mv.setViewName("../org/regSuccess.jsp");
		}		return mv;
	}

	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
		
	}
	
	public HubUrlParam registAuthInputParam(String appTokenStr,Org org,String tenantId){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setTenant(tenantId);
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		webHubParamsJsonObj.put("userName", org.getOrgUsername());
		webHubParamsJsonObj.put("pwd", org.getOrgPwd());
		webHubParamsJsonObj.put("loginType","1");
		webHubParamsJsonObj.put("partyId", org.getOrgId());
		webHubParamsJsonObj.put("credentialType", "password");
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		hubUrlParam.setToken("");
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		return hubUrlParam;
	}
	
	
	public HubUrlParam changePwdAuthInputParam(String appTokenStr,Org org,String oldPassword,String tenantId){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setTenant(tenantId);
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		webHubParamsJsonObj.put("userName", org.getOrgUsername());
		webHubParamsJsonObj.put("oldPassword", oldPassword);
		webHubParamsJsonObj.put("newPassword", org.getOrgPwd());
		webHubParamsJsonObj.put("loginType","1");
		webHubParamsJsonObj.put("credentialType", "password");
		webHubParamsJsonObj.put("vertifyCode", "");
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		hubUrlParam.setToken("");
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		return hubUrlParam;
	}
	
	
	public HubUrlParam verifyUserNameAuthInputParam(String appTokenStr,Org org,String tenantId){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setTenant(tenantId);
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		webHubParamsJsonObj.put("userName", org.getOrgUsername());
		webHubParamsJsonObj.put("loginType",Long.parseLong("1"));
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		hubUrlParam.setToken("");
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		return hubUrlParam;
	}
	
	
	
	public Map<String, String> getAuthResult(HubUrlParam hubUrlParam,String authType){
		com.alibaba.fastjson.JSONObject getJson = null;
		if("1".equals(authType)){//注册认证
			getJson = AuthenticationUtil.registAccountAuthenticate(hubUrlParam);
		}else if("2".equals(authType)){//修改密码
			getJson = AuthenticationUtil.changePasswordAuthenticate(hubUrlParam);
		}else if("3".equals(authType)){//校验用户是否存在
			getJson = AuthenticationUtil.verifyUserNameAuthenticate(hubUrlParam);
		}
		String authResultCode= getJson.get("hub_code").toString();
		com.alibaba.fastjson.JSONObject hubValueJson = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		if("1".equals(authResultCode)){
			returnMap.put("hub_code", authResultCode);
			if("3".equals(authType)){
				String data= getJson.get("data").toString();
				returnMap.put("data", data);
			}
		}else{
			JSONArray array = getJson.getJSONArray("hub_value");
			if("1".equals(authType)||"3".equals(authType)){//注册认证
				if(array!=null&&array.size()>0){
					hubValueJson = (com.alibaba.fastjson.JSONObject) (array.get(0));
					String authErrorCode=hubValueJson.get("code").toString();
					String authErrorTip=hubValueJson.get("value").toString();
					returnMap.put("hub_code", authResultCode);
					returnMap.put("code",authErrorCode);
					returnMap.put("value",authErrorTip);
				}else{
					if("1".equals(authType)){
						returnMap.put("hub_code", authResultCode);
						returnMap.put("value",getI18nMessage("eaap.op2.portal.reg.authFailTip"));
					}else if("3".equals(authType)){
						returnMap.put("hub_code", authResultCode);
						returnMap.put("value",getI18nMessage("eaap.op2.portal.reg.verifyUserFailed"));
					}
					
				}
			}else{
				hubValueJson = (com.alibaba.fastjson.JSONObject) (array.get(0));
				String authErrorCode=hubValueJson.get("code").toString();
				String authErrorTip=hubValueJson.get("value").toString();
				returnMap.put("hub_code", authResultCode);
				returnMap.put("code",authErrorCode);
				returnMap.put("value",authErrorTip);
			}
		}
		return returnMap;
	}
	
	
	
	public Map<String,String> authResultDeal(Map<String,String> resultMap,Org org,HttpServletRequest request,String oldPassword,String authType){
		Map<String, String> returnMap= new HashMap<String, String>();
		if("1".equals(resultMap.get("hub_code"))){
			returnMap.put("HUB_RETURN_CODE",RESULT_OK);
			if("1".equals(authType)){//注册认证
				returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.reg.authSuccess"));
			}else if("2".equals(authType)){//修改密码
				returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.reg.changPwdSuccess"));
			}else if("3".equals(authType)){//校验用户是否存在
				returnMap.put("HUB_RETURN_DESC","");
				String data= resultMap.get("data").toString();
				returnMap.put("data", data);
			}
		}else{
			 if(!StringUtils.isEmpty(APP_TOKEN_INVALID_CODE)&&APP_TOKEN_INVALID_CODE.equals(resultMap.get("code"))){//appToken失效
				BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
				BusiDataInfo busiDataInfo=busiDataInfoDeal.updateBusiDataInfo(request.getSession().getServletContext());
				Integer apphubTenantId=busiDataInfoDeal.getAppHubTenantId();
				HubUrlParam hubUrlParam = null;
				if("1".equals(authType)){//注册认证
					hubUrlParam=registAuthInputParam(busiDataInfo.getBusinessValue(),org,apphubTenantId.toString());
					Map<String,String> resultMapNew=getAuthResult(hubUrlParam,authType);
					authResultDeal(resultMapNew,org,request,null,authType);
				}else if("2".equals(authType)){//修改密码
					hubUrlParam=changePwdAuthInputParam(busiDataInfo.getBusinessValue(),org,oldPassword,apphubTenantId.toString());
					Map<String,String> resultMapNew=getAuthResult(hubUrlParam,authType);
					authResultDeal(resultMapNew,org,request,oldPassword,authType);
				}else if("3".equals(authType)){//校验用户是否存在
					hubUrlParam=verifyUserNameAuthInputParam(busiDataInfo.getBusinessValue(),org,apphubTenantId.toString());
					Map<String,String> resultMapNew=getAuthResult(hubUrlParam,authType);
					authResultDeal(resultMapNew,org,request,null,authType);
				}
			}else{
				returnMap.put("HUB_RETURN_CODE","9999");
				if("1".equals(authType)){//注册认证
					if("AIAUTH1012".equals(resultMap.get("code"))){
						returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.reg.authFail")+","+getI18nMessage("eaap.op2.portal.reg.userNameAlready"));
					}else{
						returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.reg.authFail")+","+resultMap.get("value"));
					}
				}else if("2".equals(authType)){//修改密码
					returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.reg.changPwdFail")+","+resultMap.get("value"));
				}else if("3".equals(authType)){//校验用户是否存在
					returnMap.put("HUB_RETURN_DESC",resultMap.get("value"));
				}
			}
		}
		return returnMap;
	}
	
	
	
 public Map<String,String> queryIsNeedApphub(HttpServletRequest request){
	    Map<String,String> appHubMap=new HashMap<String, String>();
	    String isNeedAppHub=IS_NEED_APP_HUB;
		String appTokenStr = null;
		Integer apphubTenantId=null;
		if("true".equals(isNeedAppHub)){
			isNeedAppHub="true";
			BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
			appTokenStr=busiDataInfoDeal.getAppTokenStr(request.getSession().getServletContext());
		    apphubTenantId=busiDataInfoDeal.getAppHubTenantId();
		}else{
			isNeedAppHub="false";
		}
		appHubMap.put("isNeedAppHub", isNeedAppHub);
		appHubMap.put("appTokenStr", appTokenStr);
		if(apphubTenantId!=null){
			appHubMap.put(EAAPConstants.HUB_TENANT, apphubTenantId.toString());
		}
		return appHubMap;		
 }
 
 
 public String appHubVerifyUserName(String appTokenStr,Org org,HttpServletRequest request,String apphubTenantId){
		String result = "true";
		String code="";
		String desc = null;
		JSONObject json = new JSONObject();
	 	HubUrlParam hubUrlParam=verifyUserNameAuthInputParam(appTokenStr,org,apphubTenantId);
		Map<String, String> resultMap=getAuthResult(hubUrlParam,"3");
		Map<String,String> returnMap=authResultDeal(resultMap,org,request,null,"3");
		if("0000".equals(returnMap.get("HUB_RETURN_CODE"))){
			String data=returnMap.get("data");
			code=RESULT_OK;
			if("true".equals(data)){//data是true说明用户名存在
				result = "false";
			}
		}else{
			code="9999";
			desc=returnMap.get("HUB_RETURN_DESC");
		}
		json.put("result", result);
		json.put("code", code);
		json.put("desc", desc);
		return json.toString();
 }
 
}
