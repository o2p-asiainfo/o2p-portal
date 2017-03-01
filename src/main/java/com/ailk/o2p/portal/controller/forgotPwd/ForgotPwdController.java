package com.ailk.o2p.portal.controller.forgotPwd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.o2p.common.security.SecurityUtil;
import com.ailk.eaap.o2p.common.util.date.UTCTimeUtil;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.AuthenticationUtil;
import com.asiainfo.integration.o2p.web.util.PushCInvokeUtils;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.bo.OrgAuthCode;
import com.ailk.eaap.op2.bo.hub.HubUrlParam;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.controller.busiDataDeal.BusiDataInfoDeal;
import com.ailk.o2p.portal.service.busiDataInfo.IBusiDataInfoServ;
import com.ailk.o2p.portal.service.forgotpwd.IForgotPwdService;
import com.ailk.o2p.portal.service.reg.IRegService;
import com.ailk.o2p.portal.utils.LogUtil;
import com.ailk.o2p.portal.utils.PortalConstants;
import com.ailk.o2p.portal.utils.SendMailUtil;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: ForgotPwdController
 * @Description:
 * @author zhengpeng
 * @date 2015-8-6 上午7:32:00
 * 
 */
@Controller
public class ForgotPwdController extends BaseController {

	private static final Logger log = Logger.getLog(ForgotPwdController.class);

	@Autowired
	private IForgotPwdService forgotPwdService;
	@Autowired
	private IRegService regService;
	@Autowired
	private IBusiDataInfoServ busiDataInfoServ;
	
	public static final String APP_TOKEN_INVALID_CODE=WebPropertyUtil.getPropertyValue("app_token_invalid_code");
	public static final String IS_NEED_APP_HUB=WebPropertyUtil.getPropertyValue(EAAPConstants.IS_NEED_APP_HUB);

	@RequestMapping(value = "forgotPwd/forgot")
	@UnPermission
	public ModelAndView toForgot(HttpServletRequest request) {
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.forgotPwd.forgotPassword");
		messages.add("eaap.op2.portal.forgotPwd.enterYourName");
		messages.add("eaap.op2.portal.forgotPwd.userName");
		messages.add("eaap.op2.portal.forgotPwd.verification");
		messages.add("eaap.op2.portal.forgotPwd.submit");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.setViewName(tenantId +"/"+ template+"/forgotPwd/forgot.htm");
		}else{
			mv.setViewName("../forgotPwd/forgot.jsp");
		}
		//mv.setViewName("../forgotPwd/forgot.jsp");
		return mv;
	}
	
	@RequestMapping(value = "forgotPwd/sentMail")
	@UnPermission
	public ModelAndView toSentMail(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.forgotPwd.forgotPassword");
		messages.add("eaap.op2.portal.forgotPwd.forgotMail");
		messages.add("eaap.op2.portal.forgotPwd.sentSuccess"); 
		messages.add("eaap.op2.portal.forgotPwd.note");
		messages.add("eaap.op2.portal.forgotPwd.noteMail");
		messages.add("eaap.op2.portal.forgotPwd.noMail");
		messages.add("eaap.op2.portal.forgotPwd.resent"); 
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.setViewName(tenantId +"/"+ template+"/forgotPwd/pwdSentMail.htm");
		}else{
			mv.setViewName("../forgotPwd/pwdSentMail.jsp");
		}
	//	mv.setViewName("../forgotPwd/pwdSentMail.jsp");
		return mv;
	}
	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
	}
	@RequestMapping(value = "/forgotPwd/reSentMail", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String resentMail(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String userName = getRequestValue(request, "userName");
		String desc = "";
		String code = RESULT_ERR;
		Org org = new Org();
		org.setOrgUsername(userName);
		List<Org> userList = forgotPwdService.selectOrg(org);
		if (userList == null || userList.isEmpty()) {
			desc = getI18nMessage("eaap.op2.portal.forgotPwd.noexist");
			code = RESULT_ERR;
		}else{
			try{
				org = (Org) userList.get(0);
				String email = org.getEmail();
				if (!StringUtils.isEmpty(email)) {
					String decryEmail = this.getDecryMsg(email);
					org.setEmail(decryEmail);
				}
				this.sendMail(org, request);
				code = RESULT_OK;
				desc = getI18nMessage("eaap.op2.portal.forgotPwd.resentSuccess");
				request.getSession().setAttribute(PortalConstants.FORGOT_PWD_USER_SESSION_KEY, org.getOrgUsername());
				request.getSession().setAttribute(PortalConstants.FORGOT_PWD_EMAIL_SESSION_KEY, org.getEmail());
			}catch (Exception e) {
				LogUtil.log(log, e, "ForgotPwdController sentResetMail:");
				code = RESULT_ERR;
				desc = getI18nMessage("eaap.op2.portal.forgotPwd.resentFailed");
			}
		} 
		json.put(RETURN_CODE, code); 
		json.put(RETURN_DESC, desc);
		return json.toString(); 
	}
 
	@RequestMapping(value = "/forgotPwd/sentResetMail", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String sentResetMail(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String rand = getRequestValue(request, "rand");
		String vCode = (String)session.getAttribute("rand");
		String userName = getRequestValue(request, "userName");
		String desc = "";
		String code = RESULT_ERR;
	    String isNeedAppHub=IS_NEED_APP_HUB;
		String appTokenStr = null;
		if("true".equals(isNeedAppHub)){
			isNeedAppHub="true";
			BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
			appTokenStr=busiDataInfoDeal.getAppTokenStr(request.getSession().getServletContext());
		}else{
			isNeedAppHub="false";
		}		
		if (vCode.equals(rand)) {
			Org org = new Org();
			org.setOrgUsername(userName);
			List<Org> userList = forgotPwdService.selectOrg(org);
			if (userList == null || userList.isEmpty()) {
				desc = getI18nMessage("eaap.op2.portal.forgotPwd.noexist");
				code = RESULT_ERR;
			}else{
				try{
					org = (Org) userList.get(0);
					if("true".equals(isNeedAppHub)){//appHub发送安全连接
						HubUrlParam hubUrlParam=sendSafeLinkAuthInputParam(appTokenStr,org);
						Map<String, String> resultMap=getSendSafeLinkResult(hubUrlParam);
						Map<String,String> returnMap=sendSafeLinkResultDeal(resultMap,org,request);
						json.put("HUB_RETURN_CODE", returnMap.get("HUB_RETURN_CODE"));
						json.put("HUB_RETURN_DESC",returnMap.get("HUB_RETURN_DESC"));
						if("0000".equals(returnMap.get("HUB_RETURN_CODE"))){
							json.put(RETURN_CODE, RESULT_OK);
							json.put(RETURN_DESC, getI18nMessage("eaap.op2.portal.forgetPwd.sendSafeLinkSucc"));
						}
					}else{
						String email = org.getEmail();
						if (!StringUtils.isEmpty(email)) {
							String decryEmail = this.getDecryMsg(email);
							org.setEmail(decryEmail);
						}
						this.sendMail(org, request);
						code = RESULT_OK;
						request.getSession().setAttribute(PortalConstants.FORGOT_PWD_USER_SESSION_KEY, org.getOrgUsername());
						request.getSession().setAttribute(PortalConstants.FORGOT_PWD_EMAIL_SESSION_KEY, org.getEmail());
					}
					
				}catch (Exception e) {
					LogUtil.log(log, e, "ForgotPwdController sentResetMail:");
					code = RESULT_ERR;
					desc = getI18nMessage("eaap.op2.portal.forgotPwd.resentFailed");
				}
			}
		} else {
			desc = getI18nMessage("eaap.op2.portal.forgotPwd.authcodeerror");
			code = RESULT_ERR;
		}
		json.put(RETURN_CODE, code); 
		json.put(RETURN_DESC, desc);
		return json.toString(); 
	}
	
	private String getDecryMsg(String msg){
		String decryMsg = SecurityUtil.getInstance().decryMsg(msg);
		if(decryMsg == null){
			decryMsg = msg;
		}
		return decryMsg;
	}
	
	@RequestMapping(value = "/forgotPwd/reSetPassword", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String reSetPwd(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String rand = getRequestValue(request, "rand");
		String vCode = (String)session.getAttribute("rand");
		String newPassword = getRequestValue(request, "newPassword");
		String userId = getRequestValue(request, "userId");
		String desc = "";
		String code = RESULT_ERR;
		String isNeedAppHub=IS_NEED_APP_HUB;
		String appTokenStr = null;
		if("true".equals(isNeedAppHub)){
			isNeedAppHub="true";
			BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
			appTokenStr=busiDataInfoDeal.getAppTokenStr(request.getSession().getServletContext());
		}else{
			isNeedAppHub="false";
		}		
		if (vCode.equals(rand)) {
			try{
				Org org = new Org();
				org.setOrgId(Integer.valueOf(userId));
				org.setOrgPwd(newPassword);
				if("true".equals(isNeedAppHub)){//appHub重置密码认证
					HubUrlParam hubUrlParam=resetPasswordAuthInputParam(appTokenStr,org);
					Map<String, String> resultMap=getResetPasswordResult(hubUrlParam);
					Map<String,String> returnMap=resetPasswordResultDeal(resultMap,org,request);
					json.put("HUB_RETURN_CODE", returnMap.get("HUB_RETURN_CODE"));
					json.put("HUB_RETURN_DESC",returnMap.get("HUB_RETURN_DESC"));
					if("0000".equals(returnMap.get("HUB_RETURN_CODE"))){
						org.setPwdUpdateDate(UTCTimeUtil.getUTCTime(new Date()));
						regService.updateOrgInfo(org);
						code = RESULT_OK;
						desc = getI18nMessage("eaap.op2.portal.forgotPwd.resetPwdSuccess");
					}
				}else{
					org.setPwdUpdateDate(UTCTimeUtil.getUTCTime(new Date()));
					regService.updateOrgInfo(org);
					code = RESULT_OK;
					desc = getI18nMessage("eaap.op2.portal.forgotPwd.resetPwdSuccess");
				}
			}catch (Exception e) {
				LogUtil.log(log, e, "ForgotPwdController reSetPwd:");
				code = RESULT_ERR;
				desc = getI18nMessage("eaap.op2.portal.forgotPwd.resetPwdFailed");
			}
		} else {
			desc = getI18nMessage("eaap.op2.portal.forgotPwd.authcodeerror");
			code = RESULT_ERR;
		}
		json.put(RETURN_CODE, code); 
		json.put(RETURN_DESC, desc);
		return json.toString(); 
	}
	
	private void sendMail(Org org,HttpServletRequest request){
		OrgAuthCode orgAuthCode = new OrgAuthCode();
		orgAuthCode.setOrgId(org.getOrgId());
		orgAuthCode.setAuthType("1");
		String authCode = StringUtil.Md5(org.getOrgId() + DateUtil.convDateToString(new Date(),
							"yyyy-MM-dd HH:mm:ss"));
		orgAuthCode.setAuthCode(authCode);
		forgotPwdService.insertOrgAuthCode(orgAuthCode);
		String email = org.getEmail();
		String orgUserName = org.getOrgUsername();
		String host = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
		String mailTitle = SendMailUtil.getValueByProCode("forgotPwdResetMail.mailTitle"); // 邮件主题（从mail-send.properties配置文件读取）
		String content = SendMailUtil.getValueByProCode("forgotPwdResetMail.mailContent"); // 邮件内容
		String url = host + "/forgotPwd/resetPassword.shtml?sid=" + authCode;
		String url2 = host + "/forgotPwd/forgot.shtml";
		String dt = DateUtil.convDateToString(new Date(), "yyyy-MM-dd");
		if (!StringUtil.isEmpty(email) && !StringUtil.isEmpty(content)) {
			content = StringUtils.replace(content, "${userName}",orgUserName);
			content = StringUtils.replace(content, "${url}", url);
			content = StringUtils.replace(content, "${url2}", url2); 
			content = StringUtils.replace(content, "${date}", dt);
			
			String pushc_url=WebPropertyUtil.getPropertyValue("pushc_url");
			if(!StringUtils.isEmpty(pushc_url)){
				PushCInvokeUtils.pushEmailByForgetPwdContentForHttp(org, mailTitle, "<![CDATA["+content+"]]>", org.getOrgId().toString());
			}else{
				SendMailUtil sendMail = new SendMailUtil();
				sendMail.send(email, content, mailTitle);
			}
		}
	}
	
	
	@RequestMapping(value = "/forgotPwd/resetPassword")
	@UnPermission
	public ModelAndView toResetPassword(HttpServletRequest request,HttpServletResponse response) {
		String sid = getRequestValue(request, "sid");
		OrgAuthCode orgAuthCode = new OrgAuthCode();
		orgAuthCode.setAuthCode(sid);
		List<OrgAuthCode> orgAuthCodeList = forgotPwdService.selectOrgAuthCode(orgAuthCode);
		ModelAndView mv = new ModelAndView();
		if (orgAuthCodeList != null && !orgAuthCodeList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date disableTime = orgAuthCodeList.get(0).getDisableTime();
			String disableTimeStr = sdf.format(disableTime);
			String nowStr = DateUtil.convDateToString(new Date(),"yyyy-MM-dd HH:mm:ss");

			if (disableTimeStr.compareTo(nowStr) >= 0) {
				Org org = new Org();
				org.setOrgId(orgAuthCodeList.get(0).getOrgId());
				List<Org> userList = forgotPwdService.selectOrg(org);
				if (userList != null && userList.size() > 0) {
					org = (Org) userList.get(0);
					org.setOrgUsername(org.getOrgUsername());
					org.setOrgId(org.getOrgId());
					mv = this.getResetView();
					mv.addObject(PortalConstants.RESET_USER_ID, String.valueOf(org.getOrgId()));
					forgotPwdService.updateOrgAuthDisableTime(sid); 
				}else{
					mv = this.getTimeOutView();
				}
			} else {
				mv = this.getTimeOutView();
			}
		} else {
			mv = this.getTimeOutView();
		}
		return mv;
	}
	
	private ModelAndView getResetView(){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.forgotPwd.forgotPassword");
		messages.add("eaap.op2.portal.forgotPwd.resetPassword");
		messages.add("eaap.op2.portal.forgotPwd.submit");
		messages.add("eaap.op2.portal.forgotPwd.newPwd");
		messages.add("eaap.op2.portal.forgotPwd.repeat");
		messages.add("eaap.op2.portal.forgotPwd.verification");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("../forgotPwd/resetPwd.jsp");
		return mv;
	}
	
	private ModelAndView getTimeOutView(){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.forgotPwd.forgotPassword");
		messages.add("eaap.op2.portal.forgotPwd.linkError");
		messages.add("eaap.op2.portal.forgotPwd.rePwd");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.setViewName("../forgotPwd/pwdTimeOut.jsp");
		return mv;
	}

	public Map<String, String> getSendSafeLinkResult(HubUrlParam hubUrlParam){
		com.alibaba.fastjson.JSONObject getJson = AuthenticationUtil.sendSafeLink(hubUrlParam);
		String registAuthResult= getJson.get("hub_code").toString();
		com.alibaba.fastjson.JSONObject hubValueJson = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		if("1".equals(registAuthResult)){
			returnMap.put("hub_code", registAuthResult);
		}else{
			JSONArray array = getJson.getJSONArray("hub_value");
			if(array!=null&&array.size()>0){
				hubValueJson = (com.alibaba.fastjson.JSONObject) (array.get(0));
				String authErrorCode=hubValueJson.get("code").toString();
				String authErrorTip=hubValueJson.get("value").toString();
				returnMap.put("hub_code", registAuthResult);
				returnMap.put("code",authErrorCode);
				returnMap.put("value",authErrorTip);
			}
		}
		return returnMap;
	}
	
	
	public HubUrlParam sendSafeLinkAuthInputParam(String appTokenStr,Org org){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setTenant(EAAPConstants.HUB_TENANT);
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		webHubParamsJsonObj.put("identificationName", org.getOrgUsername());
	 
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		hubUrlParam.setToken("");
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		return hubUrlParam;
	}
	
	public Map<String,String> sendSafeLinkResultDeal(Map<String,String> resultMap,Org org,HttpServletRequest request){
		Map<String, String> returnMap= new HashMap<String, String>();
		if("1".equals(resultMap.get("hub_code"))){
			returnMap.put("HUB_RETURN_CODE",RESULT_OK);
			returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.forgetPwd.sendSafeLinkSucc"));
		}else{
			 if(!StringUtils.isEmpty(APP_TOKEN_INVALID_CODE)&&APP_TOKEN_INVALID_CODE.equals(resultMap.get("code"))){//appToken失效
				BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
				BusiDataInfo busiDataInfo=busiDataInfoDeal.updateBusiDataInfo(request.getSession().getServletContext());
				
				HubUrlParam hubUrlParam=sendSafeLinkAuthInputParam(busiDataInfo.getBusinessValue(),org);
				Map<String,String> resultMapNew=getSendSafeLinkResult(hubUrlParam);
				sendSafeLinkResultDeal(resultMapNew,org,request);
			}else{
				returnMap.put("HUB_RETURN_CODE","9999");
				returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.forgetPwd.sendSafeLinkFail")+","+resultMap.get("value"));
			}
		}
		return returnMap;
	}
	
	
	
	public Map<String, String> getResetPasswordResult(HubUrlParam hubUrlParam){
		com.alibaba.fastjson.JSONObject getJson = AuthenticationUtil.resetPasswordAuthenticate(hubUrlParam);
		String registAuthResult= getJson.get("hub_code").toString();
		com.alibaba.fastjson.JSONObject hubValueJson = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		if("1".equals(registAuthResult)){
			returnMap.put("hub_code", registAuthResult);
		}else{
			JSONArray array = getJson.getJSONArray("hub_value");
			if(array!=null&&array.size()>0){
				hubValueJson = (com.alibaba.fastjson.JSONObject) (array.get(0));
				String authErrorCode=hubValueJson.get("code").toString();
				String authErrorTip=hubValueJson.get("value").toString();
				returnMap.put("hub_code", registAuthResult);
				returnMap.put("code",authErrorCode);
				returnMap.put("value",authErrorTip);
			}
		}
		return returnMap;
	}
	
	
	public HubUrlParam resetPasswordAuthInputParam(String appTokenStr,Org org){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setTenant(EAAPConstants.HUB_TENANT);
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		webHubParamsJsonObj.put("identificationName", org.getOrgUsername());
		webHubParamsJsonObj.put("newPasswd ", org.getOrgPwd());
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		hubUrlParam.setToken("");
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		return hubUrlParam;
	}
	
	public Map<String,String> resetPasswordResultDeal(Map<String,String> resultMap,Org org,HttpServletRequest request){
		Map<String, String> returnMap= new HashMap<String, String>();
		if("1".equals(resultMap.get("hub_code"))){
			returnMap.put("HUB_RETURN_CODE",RESULT_OK);
			returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.forgetPwd.resetPwdSuccessFromAppHub"));
		}else{
			 if(!StringUtils.isEmpty(APP_TOKEN_INVALID_CODE)&&APP_TOKEN_INVALID_CODE.equals(resultMap.get("code"))){//appToken失效
				BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
				BusiDataInfo busiDataInfo=busiDataInfoDeal.updateBusiDataInfo(request.getSession().getServletContext());
				
				HubUrlParam hubUrlParam=resetPasswordAuthInputParam(busiDataInfo.getBusinessValue(),org);
				Map<String,String> resultMapNew=getResetPasswordResult(hubUrlParam);
				resetPasswordResultDeal(resultMapNew,org,request);
			}else{
				returnMap.put("HUB_RETURN_CODE","9999");
				returnMap.put("HUB_RETURN_DESC",getI18nMessage("eaap.op2.portal.forgetPwd.resetPwdFailedFromAppHub")+","+resultMap.get("value"));
			}
		}
		return returnMap;
	}
}
