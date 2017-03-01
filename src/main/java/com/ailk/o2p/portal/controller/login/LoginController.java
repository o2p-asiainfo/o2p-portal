package com.ailk.o2p.portal.controller.login;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.o2p.common.security.SecurityUtil;
import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.bo.OrgRole;
import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.eaap.op2.bo.hub.HubUrlParam;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.controller.busiDataDeal.BusiDataInfoDeal;
import com.ailk.o2p.portal.service.login.ILoginService;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.reg.IRegService;
import com.ailk.o2p.portal.utils.DictDataUtil;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.foundation.util.MessageUtils;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.AuthenticationUtil;
import com.asiainfo.integration.o2p.web.util.WebConstants;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

@Controller
public class LoginController extends BaseController {
	//private static Logger log = Logger.getLog(LoginController.class);

	@Autowired
	private ILoginService loginService;
	@Autowired
	private IRegService regService;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private IMessageServ messageServ;
	private static final Logger log = Logger.getLog(LoginController.class);

	
	public static final String APP_TOKEN_INVALID_CODE=WebPropertyUtil.getPropertyValue("app_token_invalid_code");
	public static final String IS_NEED_APP_HUB=WebPropertyUtil.getPropertyValue(EAAPConstants.IS_NEED_APP_HUB);

	@RequestMapping(value = "/org/login", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	@UnPermission
	public String orgLogin(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String orgUserName = getRequestValue(request, "orgUserName");
		String sha256UserPwd = getRequestValue(request, "orgPwd");
		String rand = getRequestValue(request, "rand");
		String timeOffset = getRequestValue(request, "timeOffset");
		String vCode = (String)session.getAttribute("rand");
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
		
		String desc = "";
		String code = RESULT_ERR;
		if(!StringUtil.isEmpty(rand) && (rand.equals(vCode) || "O2P".equals(rand.toUpperCase()))){
			Org org = new Org();
			org.setOrgUsername(orgUserName);
			if("false".equals(isNeedAppHub)){
				org.setOrgPwd(sha256UserPwd); 
			}
			org = this.getUserInfo(org);
			if (org != null) {
				//审核通过D、待审核B、审核未通过C：（待审核及审核未通过用户登录后只允许修改用户基本信息）
				if(org.getState().equals(EAAPConstants.COMM_STATE_ONLINE) || org.getState().equals(EAAPConstants.COMM_STATE_WAITAUDI) || org.getState().equals(EAAPConstants.COMM_STATE_NOPASSAUDI)){
					session.invalidate();
					if(org.getSFileId() != null){
						org.setFileIdString(String.valueOf(org.getSFileId())); 
					}
					String  userId = String.valueOf(org.getOrgId());
					OrgRole orgRole = new OrgRole();
					orgRole.setOrgId(Integer.valueOf(userId));
					List<OrgRole> orgRoleList = loginService.selectOrgRole(orgRole);
					
					Set<String> roleCodeSet = this.getRoleCode(orgRoleList);
					request.getSession().setAttribute("userBean", org);
					request.getSession().setAttribute("userRole", orgRoleList);
					//创建UserInfo
					UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
					if(userRoleInfo==null){
						userRoleInfo = new UserRoleInfo();
					}
					userRoleInfo.setId(String.valueOf(org.getOrgId()));
					userRoleInfo.setName(org.getName());
					userRoleInfo.setTenantId(org.getTenantId());
					if(request.getAttribute("tenantCode") !=null){
						userRoleInfo.setTenantCode((String) request.getAttribute("tenantCode")); 
					}
					request.getSession().setAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY, userRoleInfo);
					//将时区放入session
					request.getSession().setAttribute(EAAPConstants.TIME_OFFSET, timeOffset);
					//将租户ID放入session
					request.getSession().setAttribute(WebConstants.O2P_USER_TENANT_ID_KEY, org.getTenantId());
						
					if(roleCodeSet.contains("4")){
						request.getSession().setAttribute("switchUserRole", true); 
					}
					request.getSession().setMaxInactiveInterval(this.getMaxInactiveInterval()); 
					if("false".equals(isNeedAppHub)){//原始方式登录校验
						code = RESULT_OK;
						desc = getI18nMessage("eaap.op2.portal.login.successLogin");
						desc = MessageUtils.resolve(desc, new String[] { orgUserName });
					}else{//认证方式登录
						org.setOrgPwd(sha256UserPwd); //直接从页面的明文密码传给hub认证
						HubUrlParam hubUrlParam=partyAuthInputParamByOrg(appTokenStr,org,rand,apphubTenantId.toString());
						Map<String,String> resultMap=getPartyAuthResult(hubUrlParam);
						Map<String,String> returnMap=partyResultDealByOrg(resultMap, code, desc,org,rand,request);
						code=returnMap.get("code");
						desc=returnMap.get("desc");
					}
				}
			}else{
				if("false".equals(isNeedAppHub)){
					desc = getI18nMessage("eaap.op2.portal.login.nameOrPasswordError");
				}else{
					HubUrlParam hubUrlParam=partyAuthInputParam(appTokenStr,rand,orgUserName,sha256UserPwd,apphubTenantId.toString());
					Map<String,String> resultMap=getPartyAuthResult(hubUrlParam);
					Map<String,String> returnMap=partyResultDeal(resultMap, code, desc,rand,orgUserName,sha256UserPwd,request);
					if("1".equals(resultMap.get("hub_code"))){//针对o2p不存在，但是apphub中存在的账号，认证成功后，也是不能登录的场景
						json.put("hubcode", returnMap.get("code"));
						desc=getI18nMessage("eaap.op2.portal.login.nameOrPasswordError");
					}else{
						code=returnMap.get("code");
						desc=returnMap.get("desc");
					}
				}
			}
		}else{
			desc = getI18nMessage("eaap.op2.portal.login.authcodeerror");
		}
		json.put(RETURN_CODE, code); 
		json.put(RETURN_DESC, desc);
		return json.toString();
	}
	
	private int getMaxInactiveInterval(){
		int maxInactive = 60 * 15;
		if(!StringUtil.isEmpty(WebPropertyUtil.getPropertyValue("o2p_session_max_inactive"))){
			maxInactive = Integer.valueOf(WebPropertyUtil.getPropertyValue("o2p_session_max_inactive").trim());
		}
		return maxInactive;
	}
	
	private Set<String> getRoleCode(List<OrgRole> orgRoleList){
		Set<String> roleCodeSet = new HashSet<String>();
		for(OrgRole orgRole : orgRoleList){
			roleCodeSet.add(orgRole.getRoleCode()); 
		}
		return roleCodeSet;
	}
	
	@RequestMapping(value = "/org/toSwitchUserView")
	public ModelAndView toSwitchUserView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.reg.switchUser");
		messages.add("eaap.op2.portal.reg.submit");
		messages.add("eaap.op2.portal.reg.cancel");
		messages.add("eaap.op2.portal.reg.infoUserInfo");
		messages.add("eaap.op2.portal.reg.switchUser");
		messages.add("eaap.op2.portal.reg.infoBasicInfo");
		messages.add("eaap.op2.portal.reg.infoEdit");
		
		
		List<Map<String,String>> orgList = loginService.queryOrgIdAndName();
		addTranslateMessage(mv, messages);
		mv.addObject("orgList", orgList);
		mv.setViewName("../org/switchUser.jsp");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/org/switchUserInfo",method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String switchUserInfo(HttpServletRequest request){
		JSONObject json = new JSONObject();
		List<OrgRole> orgRoleList = (List<OrgRole>) request.getSession().getAttribute("userRole");
		Set<String> roleCodeSet = this.getRoleCode(orgRoleList);
		String code = RESULT_ERR;
		String desc = "";
		//如果用户为超级用户
		if(roleCodeSet.contains("4")){
			String orgId = getRequestValue(request, "orgId");
			if(!StringUtils.isEmpty(orgId)){
				Org org = new Org();
				org.setOrgId(Integer.valueOf(orgId)); 
				org = this.getUserInfo(org);
				if(org != null){
					request.getSession().setAttribute("userBean", org);
				}
				code = RESULT_OK;
				desc = getI18nMessage("eaap.op2.portal.reg.switchUserSuccess");
			}else{
				desc = getI18nMessage("eaap.op2.portal.reg.switchUserFail");
			}
		}else{
			desc = getI18nMessage("eaap.op2.portal.reg.switchUserNoPermission");
		}
		json.put(RETURN_CODE, code); 
		json.put(RETURN_DESC, desc);
		return json.toString();
	}
	
	
	@RequestMapping(value = "/org/toSavePrivilege")
	@UnPermission
	public ModelAndView toSavePrivilege(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.index.SysName");
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//导航
		messages.add("eaap.op2.portal.index.index");
		messages.add("eaap.op2.portal.index.pradIndex");
		messages.add("eaap.op2.portal.pardProduct.product");
		messages.add("eaap.op2.portal.pardProd.detail");
		//基础信息
		
		addTranslateMessage(mv, messages);
		mv.setViewName("../savePrivilege.jsp");
		return mv;
	}

	@RequestMapping(value = "/org/tologin")
	@UnPermission
	public ModelAndView toLogin(HttpServletRequest request) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//登录
		messages.add("eaap.op2.portal.login.toLogin");
		//登录到您的帐户
		messages.add("eaap.op2.portal.login.loginToYourAccount");
		//帐号
		messages.add("eaap.op2.portal.login.loginUserName");
		//密码
		messages.add("eaap.op2.portal.login.loginPassword");
		//验证码
		messages.add("eaap.op2.portal.login.verification");
		//忘记密码
		messages.add("eaap.op2.portal.login.forgotPassword");
		//没有一个帐户吗?
		messages.add("eaap.op2.portal.login.donotHaveAccount");
		//创建一个帐户
		messages.add("eaap.op2.portal.login.createAnAccount");
		messages.add("eaap.op2.portal.login.forgotPwdOne");
		messages.add("eaap.op2.portal.login.forgotPwdTwo");
		messages.add("eaap.op2.portal.login.forgotPwdThree");
		messages.add("eaap.op2.portal.reg.regReadOne");
		messages.add("eaap.op2.portal.reg.regReadTwo");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		
		//是否是apphub接入标识
		String isNeedAppHub=IS_NEED_APP_HUB;
		mv.addObject("isNeedAppHub",isNeedAppHub);
		
		if(switchEnvironment()){
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
				mv.setViewName(tenantId +"/"+ template+"/o2pCloudLogin.htm");
			}else{
				mv.setViewName("/o2pCloudLogin.jsp");
			}
		}else{
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
				mv.setViewName(tenantId +"/"+ template+"/o2pCloudLogin.htm");
			}else{
				mv.setViewName("../login.jsp");
			}
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/index")
	@UnPermission
	public ModelAndView toIndex(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//手机余额支付
		messages.add("eaap.op2.portal.index.balance");
		//现金充值(话费)
		messages.add("eaap.op2.portal.index.topupapi");
		//短信
		messages.add("eaap.op2.portal.index.sms");
		//销售品订单
		messages.add("eaap.op2.portal.index.selllist");
		//成功案例
		messages.add("eaap.op2.portal.index.successcase");
		//使用本能力应用数
		messages.add("eaap.op2.portal.index.useapinumdes1");
		//调用数
		messages.add("eaap.op2.portal.index.useapinumdes2");
		messages.add("eaap.op2.portal.index.balanceDes");
		messages.add("eaap.op2.portal.index.topupapidesc");
		messages.add("eaap.op2.portal.index.smsDes");
		messages.add("eaap.op2.portal.index.selllistdesc");
		//Become a Developer
		messages.add("eaap.op2.portal.index.iWantToDev");
		messages.add("eaap.op2.portal.index.registerDeveDesc1");
		messages.add("eaap.op2.portal.index.registerDeveDesc2");
		messages.add("eaap.op2.portal.index.registerDeveDesc3");
		messages.add("eaap.op2.portal.index.registerDeveDesc4");
		messages.add("eaap.op2.portal.index.readyGo");
		
		messages.add("eaap.op2.portal.index.iWantProv");
		messages.add("eaap.op2.portal.index.registerProvDesc1");
		messages.add("eaap.op2.portal.index.registerProvDesc2");
		messages.add("eaap.op2.portal.index.registerProvDesc3");
		
		messages.add("eaap.op2.portal.index.iWantPart");
		messages.add("eaap.op2.portal.index.registerPartDesc1");
		messages.add("eaap.op2.portal.index.registerPartDesc2");
		messages.add("eaap.op2.portal.index.registerPartDesc3");
		if(switchEnvironment()){
			messages.add("eaap.op2.portal.index.OperatorAsSalesChannel");
			messages.add("eaap.op2.portal.index.OperatorAsSalesChannelConten");
			messages.add("eaap.op2.portal.index.PartnerAsSalesChannel");
			messages.add("eaap.op2.portal.index.PartnerAsSalesChannelConten");
			messages.add("eaap.op2.portal.index.CustomerInsight");
			messages.add("eaap.op2.portal.index.CustomerInsightContent");
			messages.add("eaap.op2.portal.index.RealTimeCampaign");
			messages.add("eaap.op2.portal.index.RealTimeCampaignConten");
		}
		
		//新国际化，首页跳动图片的文字
		messages.add("eaap.op2.portal.index.registerDeveDesc1New");
		messages.add("eaap.op2.portal.index.registerDeveDesc3New");
		messages.add("eaap.op2.portal.index.registerDeveDesc4New");
		messages.add("eaap.op2.portal.index.registerProvDesc1New");
		messages.add("eaap.op2.portal.index.registerProvDesc2New");
		messages.add("eaap.op2.portal.index.registerProvDesc3New");
		messages.add("eaap.op2.portal.index.registerPartDesc1New");
		messages.add("eaap.op2.portal.index.registerPartDesc2New");
				
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Home"); 
		if(switchEnvironment()){
			mv.setViewName("/o2pCloudIndex.jsp"); 
		}else{
			mv.setViewName("/index.jsp"); 
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/home")
	@UnPermission
	public ModelAndView toHome(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		//手机余额支付
		messages.add("eaap.op2.portal.index.balance");
		//现金充值(话费)
		messages.add("eaap.op2.portal.index.topupapi");
		//短信
		messages.add("eaap.op2.portal.index.sms");
		//销售品订单
		messages.add("eaap.op2.portal.index.selllist");
		//成功案例
		messages.add("eaap.op2.portal.index.successcase");
		//使用本能力应用数
		messages.add("eaap.op2.portal.index.useapinumdes1");
		//调用数
		messages.add("eaap.op2.portal.index.useapinumdes2");
		messages.add("eaap.op2.portal.index.balanceDes");
		messages.add("eaap.op2.portal.index.topupapidesc");
		messages.add("eaap.op2.portal.index.smsDes");
		messages.add("eaap.op2.portal.index.selllistdesc");
		//Become a Developer
		messages.add("eaap.op2.portal.index.iWantToDev");
		messages.add("eaap.op2.portal.index.registerDeveDesc1");
		messages.add("eaap.op2.portal.index.registerDeveDesc2");
		messages.add("eaap.op2.portal.index.registerDeveDesc3");
		messages.add("eaap.op2.portal.index.registerDeveDesc4");
		messages.add("eaap.op2.portal.index.readyGo");
		
		messages.add("eaap.op2.portal.index.iWantProv");
		messages.add("eaap.op2.portal.index.registerProvDesc1");
		messages.add("eaap.op2.portal.index.registerProvDesc2");
		messages.add("eaap.op2.portal.index.registerProvDesc3");
		
		messages.add("eaap.op2.portal.index.iWantPart");
		messages.add("eaap.op2.portal.index.registerPartDesc1");
		messages.add("eaap.op2.portal.index.registerPartDesc2");
		messages.add("eaap.op2.portal.index.registerPartDesc3");
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannel");
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannel");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.CustomerInsight");
		messages.add("eaap.op2.portal.index.CustomerInsightContent");
		messages.add("eaap.op2.portal.index.RealTimeCampaign");
		messages.add("eaap.op2.portal.index.RealTimeCampaignConten");
		
		//新国际化，首页跳动图片的文字
		messages.add("eaap.op2.portal.index.registerDeveDesc1New");
		messages.add("eaap.op2.portal.index.registerDeveDesc3New");
		messages.add("eaap.op2.portal.index.registerDeveDesc4New");
		messages.add("eaap.op2.portal.index.registerProvDesc1New");
		messages.add("eaap.op2.portal.index.registerProvDesc2New");
		messages.add("eaap.op2.portal.index.registerProvDesc3New");
		messages.add("eaap.op2.portal.index.registerPartDesc1New");
		messages.add("eaap.op2.portal.index.registerPartDesc2New");
				
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Home"); 
		mv.setViewName("/o2pCloudIndex.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/view")
	@UnPermission
	public ModelAndView toView(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//手机余额支付
		messages.add("eaap.op2.portal.index.balance");
		//现金充值(话费)
		messages.add("eaap.op2.portal.index.topupapi");
		//短信
		messages.add("eaap.op2.portal.index.sms");
		//销售品订单
		messages.add("eaap.op2.portal.index.selllist");
		//成功案例
		messages.add("eaap.op2.portal.index.successcase");
		//使用本能力应用数
		messages.add("eaap.op2.portal.index.useapinumdes1");
		//调用数
		messages.add("eaap.op2.portal.index.useapinumdes2");
		messages.add("eaap.op2.portal.index.balanceDes");
		messages.add("eaap.op2.portal.index.topupapidesc");
		messages.add("eaap.op2.portal.index.smsDes");
		messages.add("eaap.op2.portal.index.selllistdesc");
		//Become a Developer
		messages.add("eaap.op2.portal.index.iWantToDev");
		messages.add("eaap.op2.portal.index.registerDeveDesc1");
		messages.add("eaap.op2.portal.index.registerDeveDesc2");
		messages.add("eaap.op2.portal.index.registerDeveDesc3");
		messages.add("eaap.op2.portal.index.registerDeveDesc4");
		messages.add("eaap.op2.portal.index.readyGo");
		
		messages.add("eaap.op2.portal.index.iWantProv");
		messages.add("eaap.op2.portal.index.registerProvDesc1");
		messages.add("eaap.op2.portal.index.registerProvDesc2");
		messages.add("eaap.op2.portal.index.registerProvDesc3");
		
		messages.add("eaap.op2.portal.index.iWantPart");
		messages.add("eaap.op2.portal.index.registerPartDesc1");
		messages.add("eaap.op2.portal.index.registerPartDesc2");
		messages.add("eaap.op2.portal.index.registerPartDesc3");
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannel");
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannel");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.CustomerInsight");
		messages.add("eaap.op2.portal.index.CustomerInsightContent");
		messages.add("eaap.op2.portal.index.RealTimeCampaign");
		messages.add("eaap.op2.portal.index.RealTimeCampaignConten");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Home"); 
		mv.setViewName("/viewFrame.jsp"); 
		
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		if (userRoleInfo == null) {
			mv.setViewName("redirect:/default");
		}
		return mv;
	}
	@RequestMapping(value = "/viewIndex")
	@UnPermission
	public ModelAndView toViewIndex(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//手机余额支付
		messages.add("eaap.op2.portal.index.balance");
		//现金充值(话费)
		messages.add("eaap.op2.portal.index.topupapi");
		//短信
		messages.add("eaap.op2.portal.index.sms");
		//销售品订单
		messages.add("eaap.op2.portal.index.selllist");
		//成功案例
		messages.add("eaap.op2.portal.index.successcase");
		//使用本能力应用数
		messages.add("eaap.op2.portal.index.useapinumdes1");
		//调用数
		messages.add("eaap.op2.portal.index.useapinumdes2");
		messages.add("eaap.op2.portal.index.balanceDes");
		messages.add("eaap.op2.portal.index.topupapidesc");
		messages.add("eaap.op2.portal.index.smsDes");
		messages.add("eaap.op2.portal.index.selllistdesc");
		//Become a Developer
		messages.add("eaap.op2.portal.index.iWantToDev");
		messages.add("eaap.op2.portal.index.registerDeveDesc1");
		messages.add("eaap.op2.portal.index.registerDeveDesc2");
		messages.add("eaap.op2.portal.index.registerDeveDesc3");
		messages.add("eaap.op2.portal.index.registerDeveDesc4");
		messages.add("eaap.op2.portal.index.readyGo");
		
		messages.add("eaap.op2.portal.index.iWantProv");
		messages.add("eaap.op2.portal.index.registerProvDesc1");
		messages.add("eaap.op2.portal.index.registerProvDesc2");
		messages.add("eaap.op2.portal.index.registerProvDesc3");
		
		messages.add("eaap.op2.portal.index.iWantPart");
		messages.add("eaap.op2.portal.index.registerPartDesc1");
		messages.add("eaap.op2.portal.index.registerPartDesc2");
		messages.add("eaap.op2.portal.index.registerPartDesc3");
		
		//新国际化，首页跳动图片的文字
		messages.add("eaap.op2.portal.index.registerDeveDesc1New");
		messages.add("eaap.op2.portal.index.registerDeveDesc3New");
		messages.add("eaap.op2.portal.index.registerDeveDesc4New");
		messages.add("eaap.op2.portal.index.registerProvDesc1New");
		messages.add("eaap.op2.portal.index.registerProvDesc2New");
		messages.add("eaap.op2.portal.index.registerProvDesc3New");
		messages.add("eaap.op2.portal.index.registerPartDesc1New");
		messages.add("eaap.op2.portal.index.registerPartDesc2New");
		
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannel");
		messages.add("eaap.op2.portal.index.OperatorAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannel");
		messages.add("eaap.op2.portal.index.PartnerAsSalesChannelConten");
		messages.add("eaap.op2.portal.index.CustomerInsight");
		messages.add("eaap.op2.portal.index.CustomerInsightContent");
		messages.add("eaap.op2.portal.index.RealTimeCampaign");
		messages.add("eaap.op2.portal.index.RealTimeCampaignConten");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		if(userRoleInfo!=null && userRoleInfo.getId()!=""){
			mv.setViewName("/forward.jsp"); 
		}else{
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
			//	mv.setViewName(tenantId+"/"+template+"/o2pCloudIndex.htm"); 
				mv.setViewName("/o2pCloudIndex.jsp"); 
			}else{
				mv.setViewName("/o2pCloudIndex.jsp"); 
			}
		}
		return mv;
	}



	@RequestMapping(value = "/default")
	@UnPermission
	public ModelAndView toframe(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//手机余额支付
		messages.add("eaap.op2.portal.index.balance");
		//现金充值(话费)
		messages.add("eaap.op2.portal.index.topupapi");
		//短信
		messages.add("eaap.op2.portal.index.sms");
		//销售品订单
		messages.add("eaap.op2.portal.index.selllist");
		//成功案例
		messages.add("eaap.op2.portal.index.successcase");
		//使用本能力应用数
		messages.add("eaap.op2.portal.index.useapinumdes1");
		//调用数
		messages.add("eaap.op2.portal.index.useapinumdes2");
		messages.add("eaap.op2.portal.index.balanceDes");
		messages.add("eaap.op2.portal.index.topupapidesc");
		messages.add("eaap.op2.portal.index.smsDes");
		messages.add("eaap.op2.portal.index.selllistdesc");
		//Become a Developer
		messages.add("eaap.op2.portal.index.iWantToDev");
		messages.add("eaap.op2.portal.index.registerDeveDesc1");
		messages.add("eaap.op2.portal.index.registerDeveDesc2");
		messages.add("eaap.op2.portal.index.registerDeveDesc3");
		messages.add("eaap.op2.portal.index.registerDeveDesc4");
		messages.add("eaap.op2.portal.index.readyGo");
		
		messages.add("eaap.op2.portal.index.iWantProv");
		messages.add("eaap.op2.portal.index.registerProvDesc1");
		messages.add("eaap.op2.portal.index.registerProvDesc2");
		messages.add("eaap.op2.portal.index.registerProvDesc3");
		
		messages.add("eaap.op2.portal.index.iWantPart");
		messages.add("eaap.op2.portal.index.registerPartDesc1");
		messages.add("eaap.op2.portal.index.registerPartDesc2");
		messages.add("eaap.op2.portal.index.registerPartDesc3");
		if(switchEnvironment()){
			messages.add("eaap.op2.portal.index.OperatorAsSalesChannel");
			messages.add("eaap.op2.portal.index.OperatorAsSalesChannelConten");
			messages.add("eaap.op2.portal.index.PartnerAsSalesChannel");
			messages.add("eaap.op2.portal.index.PartnerAsSalesChannelConten");
			messages.add("eaap.op2.portal.index.CustomerInsight");
			messages.add("eaap.op2.portal.index.CustomerInsightContent");
			messages.add("eaap.op2.portal.index.RealTimeCampaign");
			messages.add("eaap.op2.portal.index.RealTimeCampaignConten");
		}

		ModelAndView mv = new ModelAndView();
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		if(userRoleInfo!=null && userRoleInfo.getTenantId()!=null){
			String tenantId= userRoleInfo.getTenantId().toString();
			Tenant  tenant = new Tenant();
			tenant.setTenantId(Integer.valueOf(tenantId));
			tenant = loginService.queryTenant(tenant);
			String tenantName= tenant.getName();
			String tenantLogo= tenant.getLogo();
			mv.addObject("tenantName", tenantName);
			mv.addObject("tenantLogo", tenantLogo);
		}
		
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Home"); 
		
		if(switchEnvironment()){
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
				mv.setViewName(tenantId + "/" + template +"/viewFrame.htm"); 
			}else{
				mv.setViewName("/viewFrame.jsp"); 
			}
		}else{
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
				mv.setViewName(tenantId + "/" + template +"/viewFrame.htm"); 
			}else{
				mv.setViewName("/frame.jsp");
			}
			
		}
		

		
		return mv;
	}
	
	
	
	
	@RequestMapping(value = "/org/logout")
	public ModelAndView logOut(HttpServletRequest request){
		request.getSession(false).invalidate();
		List<String> messages = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Home"); 
		mv.setViewName("/logout.jsp"); 
		return mv;
	}
	
	
	@RequestMapping(value = "/org/userInfo")
	public ModelAndView userInfo(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.reg.infoUserName");
		messages.add("eaap.op2.portal.reg.regEmail");
		messages.add("eaap.op2.portal.reg.regSubEmail");
		messages.add("eaap.op2.portal.reg.regPapersType");
		messages.add("eaap.op2.portal.reg.regPapersNUm");
		messages.add("eaap.op2.portal.reg.infoCity");
		messages.add("eaap.op2.portal.reg.infoName"); 
		messages.add("eaap.op2.portal.reg.phone"); 
		messages.add("eaap.op2.portal.reg.subPhone");
		messages.add("eaap.op2.portal.reg.introduction"); 
		messages.add("eaap.op2.portal.reg.infoBasicInfo"); 
		messages.add("eaap.op2.portal.reg.infoChangPwd"); 
		messages.add("eaap.op2.portal.reg.infoUserInfo");
		messages.add("eaap.op2.portal.reg.submit");
		messages.add("eaap.op2.portal.reg.cancel");
		messages.add("eaap.op2.portal.reg.infoEdit");
		messages.add("eaap.op2.portal.portalMessage.history");
		messages.add("eaap.op2.portal.reg.userType");
		messages.add("eaap.op2.portal.reg.regPersonal");
		messages.add("eaap.op2.portal.reg.regcompany");
		messages.add("eaap.op2.portal.devmgr.submitToCheck");
		messages.add("eaap.op2.portal.reg.switchUser");
		messages.add("eaap.op2.portal.reg.state"); 
		messages.add("eaap.op2.portal.reg.regFirstName");
		messages.add("eaap.op2.portal.reg.regLastName");
		messages.add("eaap.op2.portal.reg.enterpriseName");
		messages.add("eaap.op2.portal.reg.isRequired");
		messages.add("eaap.op2.portal.reg.country");
		messages.add("eaap.op2.portal.reg.address");
		messages.add("eaap.op2.portal.reg.partnerType");
		messages.add("eaap.op2.portal.reg.partnerTypeForNormalPartner");
		messages.add("eaap.op2.portal.reg.partnerTypeForOperator");
		messages.add("eaap.op2.portal.reg.indenAddress");
		messages.add("eaap.op2.portal.reg.indenValidityDate");
		messages.add("eaap.op2.portal.reg.legalName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		List<Map<String,Object>> provinceList = null;
		provinceList = this.getProvinceList();
		mv.addObject("provinceList", provinceList);
		//是否是cloud的标识
		String  o2pCloudFlag=WebPropertyUtil.getPropertyValue("o2p_web_domin");
		mv.addObject("o2pCloudFlag",o2pCloudFlag);
		Org org = (Org) request.getSession().getAttribute("userBean");
		List<Map<String,String>> certTypeList=null;
		String orgType= org.getOrgTypeCode();
		if("1".equals(orgType)){//1个人，2企业
			certTypeList = mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG_CERT_TYPE);
		}
		mv.addObject("certTypeList", certTypeList);
		mv.addObject("orgTypeCode",orgType);
		mv.addObject("checkMsg", messageServ.getCheckMsgByObjectId(EAAPConstants.WORK_FLOW_MESSAGE_QUERY_ORG, String.valueOf(org.getOrgId())));
		mv.setViewName("../org/userInfo.jsp");
		return mv;
	}

	private List<Map<String,Object>> getProvinceList(){
		Org orgBean = new Org();
		return regService.queryProvinceForReg(orgBean);
		 
	}
	
	@RequestMapping(value = "/org/qryUserInfo", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String qryUserInfo(HttpServletRequest request){
		JSONObject json = new JSONObject();
		Org org = (Org) request.getSession().getAttribute("userBean");
		Org _org = new Org();
		_org.setOrgId(org.getOrgId());
		_org = this.getUserInfo(_org);
		String code = RESULT_ERR;
		if(_org != null){
			String cityName = loginService.queryCityById(org.getAreaId());
			code = RESULT_OK;
			//模糊化处理手机号和邮箱
			String mainTelephone=_org.getTelephone();
			_org.setTelephone(this.getFuzzyPhone(mainTelephone));
			String subTelephone=_org.getSubTelephone();
			if(!StringUtils.isEmpty(subTelephone)&&!subTelephone.contains("&nbsp;")){
				_org.setSubTelephone(this.getFuzzyPhone(subTelephone));
			}
			String mainEmail=_org.getEmail();
			_org.setEmail(this.getFuzzyEmail(mainEmail));
			String subEmail=_org.getSubEmail();
			if(!StringUtils.isEmpty(subEmail)&&!subEmail.contains("&nbsp;")){
				_org.setSubEmail(this.getFuzzyEmail(subEmail));
			}
			
			json.put("org", _org);
			json.put("cityName", cityName); 
			json.put("orgState",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ORG));
		}
		json.put(RETURN_CODE, code); 
		return json.toString();
	}
	
	
	private String getDecryMsg(String msg){
		String decryMsg = SecurityUtil.getInstance().decryMsg(msg);
		if(decryMsg == null){
			decryMsg = msg;
		}
		return decryMsg;
	}
	
	private Org getUserInfo(Org org) {
		Org _org = null;
		List<Org> userList = loginService.selectOrg(org);
		if (userList != null && !userList.isEmpty()) {
			_org = (Org) userList.get(0);
			String email = _org.getEmail();
			if (!StringUtils.isEmpty(email)) {
				String decryEmail = this.getDecryMsg(email);
				_org.setEmail(decryEmail);
			}
			String subEmail= _org.getSubEmail();
			if (!StringUtils.isEmpty(subEmail)) {
				String decrySubEmail = this.getDecryMsg(subEmail);
				_org.setSubEmail(decrySubEmail);
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
			String subTelephone=_org.getSubTelephone();
			if (!StringUtils.isEmpty(subTelephone)) {
				String decrySubTelephone = this.getDecryMsg(subTelephone);
				_org.setSubTelephone(decrySubTelephone);
			}
			try {
			    String indenEffDate=_org.getIndenEffDate();
			    if (!StringUtils.isEmpty(indenEffDate)) {
			    	Date indenEffDateVar = DateUtil.stringToDate(indenEffDate,"yyyy-MM-dd");
			    	String indenEffDateFormat=DateUtil.convDateToString(indenEffDateVar, "yyyy-MM-dd");
			    	_org.setIndenEffDate(indenEffDateFormat);
			    }
			    String indenExpDate=_org.getIndenExpDate();
			    if (!StringUtils.isEmpty(indenExpDate)) {
			    	Date indenExpDateVar = DateUtil.stringToDate(indenExpDate,"yyyy-MM-dd");
			    	String indenExpDateFormat=DateUtil.convDateToString(indenExpDateVar, "yyyy-MM-dd");
			    	_org.setIndenExpDate(indenExpDateFormat);
			    }
			} catch (ParseException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), e));
			} 
		}
		return _org;
	}
	
	
	@RequestMapping(value = "developerGuide")
	@UnPermission
	public ModelAndView toDeveloperGuide(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//Create Developer Account
		messages.add("eaap.op2.portal.devmgr.createdevuser");
		//Developer Registration Approve
		messages.add("eaap.op2.portal.devmgr.devRegistApprove");
		//Submit
		messages.add("eaap.op2.portal.devmgr.addApp");
		//Get AppKey
		messages.add("eaap.op2.portal.devmgr.getappkey");
		//Get AppKey and bind API
		messages.add("eaap.op2.portal.devmgr.getappkeyAPI");
		//Submit for Approval
		messages.add("eaap.op2.portal.devmgr.submitToCheck");
		//Apply for Publish Approve
		messages.add("eaap.op2.portal.devmgr.appForApprove");
		//Published successfully
		messages.add("eaap.op2.portal.devmgr.publishsuccess");
		//Application Management
		messages.add("eaap.op2.portal.devmgr.appManager");
		//Publish Application
		messages.add("eaap.op2.portal.devmgr.apppublish");
		messages.add("eaap.op2.portal.devmgr.apppublishDesc");
		//Approve
		messages.add("eaap.op2.portal.devmgr.checkstate");
		//Apply for Publish
		messages.add("eaap.op2.portal.devmgr.askforpublish");
		//Application Management
		messages.add("eaap.op2.portal.devmgr.appManager");
		//Add New Developer
		messages.add("eaap.op2.portal.devmgr.devaddin");
		//jion us
		messages.add("eaap.op2.portal.devmgr.joinUs");
		messages.add("eaap.op2.portal.devmgr.devaddinDesc");
		//Developer Registration
		messages.add("eaap.op2.portal.devmgr.devcreate");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Guide"); 
		mv.setViewName("/developerGuide.jsp"); 
		return mv;
	}
	
	@RequestMapping(value = "providerGuide")
	@UnPermission
	public ModelAndView toProviderGuide(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//Create Provider Account
		messages.add("eaap.op2.portal.doc.createProvNum");
		//Provider Registraion Approve
		messages.add("eaap.op2.portal.doc.provRegistApprove");
		//Register System
		messages.add("eaap.op2.portal.doc.loginSys");
		//Get System Code
		messages.add("eaap.op2.portal.doc.getSysCode");
		//Capability to Provide
		messages.add("eaap.op2.portal.doc.abilityProv");
		//Register API
		messages.add("eaap.op2.portal.doc.abilityProvNew");
		//Retrieve API Directory
		messages.add("eaap.op2.portal.doc.searchAPIList");
		//Management and Control
		messages.add("eaap.op2.portal.doc.manageControl");
		//Capability Management
		messages.add("eaap.op2.portal.doc.abilityProvManager");
		messages.add("eaap.op2.portal.doc.intro");
		//Approve
		messages.add("eaap.op2.portal.doc.check");
		//API Register
		messages.add("eaap.op2.portal.doc.APIregister");
		//API OnLine
		messages.add("eaap.op2.portal.doc.grounding");
		//Add Provider
		messages.add("eaap.op2.portal.doc.provAdd");
		//join us
		messages.add("eaap.op2.portal.doc.joinUs");
		messages.add("eaap.op2.portal.doc.provIntro");
		//Service Provider Registration
		messages.add("eaap.op2.portal.doc.serviceProvRegister");
		//jion us
		messages.add("eaap.op2.portal.devmgr.joinUs");
		//API Managemant
		messages.add("eaap.op2.portal.doc.APIManagemant");
		//API Pricing Plan
		messages.add("eaap.op2.portal.doc.APIPricingPlan");
		
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Guide"); 
		mv.setViewName("/providerGuide.jsp"); 
		return mv;
	}
	@RequestMapping(value = "/org/toAuditPrivilege")
	@UnPermission
	public ModelAndView toAuditPrivilege(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.login.userAuditing");
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//导航
		messages.add("eaap.op2.portal.index.index");
		messages.add("eaap.op2.portal.index.pradIndex");
		messages.add("eaap.op2.portal.pardProduct.product");
		messages.add("eaap.op2.portal.pardProd.detail");
		//基础信息
		
		addTranslateMessage(mv, messages);
		mv.setViewName("../auditPrivilege.jsp");
		return mv;
	}
	@RequestMapping(value = "partnerGuide")
	@UnPermission
	public ModelAndView toPartnerGuide(HttpServletRequest request){
		List<String> messages = new ArrayList<String>();
		//Create Partner Account
		messages.add("eaap.op2.portal.pardIndex.createParUser");
		//Partner Registration Approve
		messages.add("eaap.op2.portal.pardIndex.partnerApprove");
		//Create Converged Product
		messages.add("eaap.op2.portal.pardIndex.proFusion");
		//Bundle Product Submitted for Approval
		messages.add("eaap.op2.portal.pardIndex.bundleProduct");
		//Take Product Online
		messages.add("eaap.op2.portal.pardIndex.proShelves");
		//Management and Control
		messages.add("eaap.op2.portal.pardIndex.managementControl");
		//Converged Product Management
		messages.add("eaap.op2.portal.pardIndex.proFusionControl");
		//Publish Application
		messages.add("eaap.op2.portal.pardIndex.publishApplication");
		messages.add("eaap.op2.portal.pardIndex.proFusionDec");
		//Bundle Product
		messages.add("eaap.op2.portal.pardIndex.proBind");
		//Submitted for Approval
		messages.add("eaap.op2.portal.pardIndex.submittedForAppr");
		//Add New Partner 
		messages.add("eaap.op2.portal.pardIndex.parJoin");
		//join Us
		messages.add("eaap.op2.portal.pardIndex.joinUs");
		messages.add("eaap.op2.portal.pardIndex.parJoinDec");
		
		//Partner Registration
		messages.add("eaap.op2.portal.pardIndex.parJoinDec");
		//Approve
		messages.add("eaap.op2.portal.pardIndex.check");
		//Take Product Online
		messages.add("eaap.op2.portal.pardIndex.shelves");

		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Guide"); 
		mv.setViewName("/partnerGuide.jsp"); 
		return mv;
	}

	@RequestMapping(value = "/view/view1")
	@UnPermission
	public ModelAndView view1(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/view1.jsp"); 
		return mv;
	}
	@RequestMapping(value = "/view/view2")
	@UnPermission
	public ModelAndView view2(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/view2.jsp"); 
		return mv;
	}
	@RequestMapping(value = "/view/view3")
	@UnPermission
	public ModelAndView view3(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/view3.jsp"); 
		return mv;
	}
	@RequestMapping(value = "/view/view4")
	@UnPermission
	public ModelAndView view4(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/view4.jsp"); 
		return mv;
	}

	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
	}
	
	public Map<String,String> getPartyAuthResult(HubUrlParam hubUrlParam){
		com.alibaba.fastjson.JSONObject getJson = AuthenticationUtil.partyAuthenticate(hubUrlParam);
		String partyAuthResult= getJson.get("hub_code").toString();
		com.alibaba.fastjson.JSONObject hubValueJson = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		if("1".equals(partyAuthResult)){
			returnMap.put("hub_code", partyAuthResult);
			return returnMap;
		}else{
			JSONArray array = getJson.getJSONArray("hub_value");
			hubValueJson = (com.alibaba.fastjson.JSONObject) (array.get(0));
			String authErrorCode=hubValueJson.get("code").toString();
			String authErrorTip=hubValueJson.get("value").toString();
			returnMap.put("hub_code", partyAuthResult);
			returnMap.put("code",authErrorCode);
			returnMap.put("value",authErrorTip);
			return returnMap;
		}
	}
	
	
	public HubUrlParam partyAuthInputParamByOrg(String appTokenStr,Org org,String rand,String tenantId){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		hubUrlParam.setTenant(tenantId);
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		webHubParamsJsonObj.put("userName", org.getOrgUsername());
		webHubParamsJsonObj.put("pwd", org.getOrgPwd());
		webHubParamsJsonObj.put("loginType","1");
		webHubParamsJsonObj.put("credentialType", "password");
		webHubParamsJsonObj.put("vertifyCode", rand);
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		return hubUrlParam;
	}
	
	public Map<String,String> partyResultDealByOrg(Map<String,String> resultMap,String code,String desc,Org org,String rand,HttpServletRequest request){
		Map<String, String> returnMap= new HashMap<String, String>();
		if("1".equals(resultMap.get("hub_code"))){
			code = RESULT_OK;
			desc =getI18nMessage("eaap.op2.portal.login.authSuccess");
			returnMap.put("code",code);
			returnMap.put("desc",desc);
		}else{
			//账号锁定编码AIAUTH1007
			if("AIAUTH1007".equals(resultMap.get("code"))){
				desc =getI18nMessage("eaap.op2.portal.login.authFail")+","+getI18nMessage("eaap.op2.portal.login.authAccountTip");
				returnMap.put("code",RESULT_ERR);
				returnMap.put("desc",desc);
			}else if(!StringUtils.isEmpty(APP_TOKEN_INVALID_CODE)&&APP_TOKEN_INVALID_CODE.equals(resultMap.get("code"))){//appToken失效
				BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
				BusiDataInfo busiDataInfo=busiDataInfoDeal.updateBusiDataInfo(request.getSession().getServletContext());
				Integer apphubTenantId=busiDataInfoDeal.getAppHubTenantId();
				
				HubUrlParam hubUrlParam=partyAuthInputParamByOrg(busiDataInfo.getBusinessValue(),org,rand,apphubTenantId.toString());
				Map<String,String> resultMapNew=getPartyAuthResult(hubUrlParam);
				partyResultDealByOrg(resultMapNew, code, desc,org,rand,request);
			}else{
				desc =getI18nMessage("eaap.op2.portal.login.authFail")+","+getI18nMessage("eaap.op2.portal.login.nameOrPasswordError");
				returnMap.put("code",RESULT_ERR);
				returnMap.put("desc",desc);
			}
		}
		return returnMap;
	}
	
	public HubUrlParam partyAuthInputParam(String appTokenStr,String rand,String orgUserName,String md5UserPwd,String tenantId){
		HubUrlParam hubUrlParam=new HubUrlParam();
		hubUrlParam.setRoleType(EAAPConstants.HUB_CUSTOMER);
		hubUrlParam.setTenant(tenantId);
		if(!StringUtils.isEmpty(appTokenStr)){
			hubUrlParam.setAppToken(appTokenStr);
		}
		com.alibaba.fastjson.JSONObject webHubParamsJsonObj = new com.alibaba.fastjson.JSONObject();
		webHubParamsJsonObj.put("userName", orgUserName);
		webHubParamsJsonObj.put("pwd", md5UserPwd);
		webHubParamsJsonObj.put("loginType","1");
		webHubParamsJsonObj.put("credentialType", "password");
		webHubParamsJsonObj.put("vertifyCode", rand);
		hubUrlParam.setWebHubParamsJson(webHubParamsJsonObj);
		return hubUrlParam;
	}
	
	public Map<String, String> partyResultDeal(Map<String,String> resultMap,String code,String desc,String rand,String orgUserName,String md5UserPwd,HttpServletRequest request){
		Map<String, String> returnMap= new HashMap<String, String>();
		if("1".equals(resultMap.get("hub_code"))){
			code = RESULT_OK;
			desc =getI18nMessage("eaap.op2.portal.login.authSuccess");
			returnMap.put("code",code);
			returnMap.put("desc",desc);
		}else{
			//账号锁定编码AIAUTH1007
			if("AIAUTH1007".equals(resultMap.get("code"))){
				desc =getI18nMessage("eaap.op2.portal.login.authFail")+","+getI18nMessage("eaap.op2.portal.login.authAccountTip");
				returnMap.put("code",RESULT_ERR);
				returnMap.put("desc",desc);
			}else if(!StringUtils.isEmpty(APP_TOKEN_INVALID_CODE)&&APP_TOKEN_INVALID_CODE.equals(resultMap.get("code"))){
				
				BusiDataInfoDeal busiDataInfoDeal = (BusiDataInfoDeal) SpringContextUtil.getBean("busiDataInfoDeal");
				BusiDataInfo busiDataInfo=busiDataInfoDeal.updateBusiDataInfo(request.getSession().getServletContext());
				Integer apphubTenantId=busiDataInfoDeal.getAppHubTenantId();
				
				HubUrlParam hubUrlParam=partyAuthInputParam(busiDataInfo.getBusinessValue(),rand,orgUserName,md5UserPwd,apphubTenantId.toString());
				Map<String,String> resultMapNew=getPartyAuthResult(hubUrlParam);
				partyResultDeal(resultMapNew, code, desc,rand,orgUserName,md5UserPwd,request);
			}else{
				desc =getI18nMessage("eaap.op2.portal.login.authFail")+","+getI18nMessage("eaap.op2.portal.login.nameOrPasswordError");
				returnMap.put("code",RESULT_ERR);
				returnMap.put("desc",desc);
			}
		}
		return returnMap;
	}
	
	
	/**
	 * 获取模糊的手机号码
	 * @param phone
	 * @return
	 */
	public  String  getFuzzyPhone(String phone){
		int phoneLength=phone.length();
		if(phoneLength>7){
			return   phone.substring(0,3) + "****" + phone.substring(7, phone.length());
		}else{
			String str = "";
			int temp=phoneLength-1;
			if(temp>0){
				for(int i=0;i<temp;i++){
					str+="*";
				}
				return   phone.substring(0,1) + str ;
			}else{
				return   "*";
			}
			
		}
	}
	
	/**
	 * 获取模糊的邮箱
	 * @param email
	 * @return
	 */
	public  String  getFuzzyEmail(String email){
		String emailname=email.substring(0, email.indexOf("@"));
		String emailAddress=email.substring(email.indexOf("@"),email.length());    //后缀名字
		if(emailname.length()>4){
			return emailname.substring(0, emailname.length()-4) +"****"+emailAddress;
		}else{
			  return  "****"+emailAddress;
		}
		
	}
}
