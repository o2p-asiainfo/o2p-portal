package com.ailk.o2p.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.ailk.o2p.portal.utils.BeanValidators;
import com.ailk.o2p.portal.utils.PropLoaderFactory;
import com.ailk.o2p.portal.utils.PropertiesLoader;
import com.ailk.o2p.portal.utils.StringUtils;
import com.ailk.o2p.portal.utils.SystemKeyWords;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.StringUtil;

/**
 * 
 * @ClassName: BaseController
 * 
 * @Description: 控制器基类
 * 
 * @author 庄敬飞
 * 
 * @date 2015-5-12 上午11:36:10
 * 
 * 
 */
public class BaseController {

	protected static final String RESULT_OK = "0000";
	protected static final String RESULT_ERR = "0001";
	protected static final String RESULT_Exit = "0002";
	protected static final String RETURN_CODE = "code";
	protected static final String RETURN_DESC = "desc";
	private static final Logger log = Logger.getLog(BaseController.class);

	protected static PropertiesLoader i18nLoader = null;

//	protected static Map<String, String> configurations = null;
	
	@Autowired
	protected Validator validator;

	static {
		log.info("BaseController");
		i18nLoader = PropLoaderFactory
				.getPropertiesLoader(SystemKeyWords.I18N_LOADER_NAME);
//		configurations = CommonConfigurations.getConfigurations();
		log.info("i18nLoader:{0}", i18nLoader);
//		log.info("configurations:{0}", configurations);
		
	}

	/**
	 * @author 庄敬飞
	 * @Title: getClientIp
	 * @Description: 取客户端IP
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getClientIp(HttpServletRequest request) {
		return StringUtils.getRemoteAddr(request);
	}

	/**
	 * @author 庄敬飞
	 * @Title: addTranslateMessage
	 * @Description: 增加需要国际化的消息并推送到页面
	 * @param @param mv
	 * @param @param messages 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	protected void addTranslateMessage(ModelAndView mv, List<String> messages) {
		messages.addAll(this.getMenuMessages());
		Map<String, String> map = new HashMap<String, String>();
		for (String message : messages) {
			String value = getI18nMessage(message);
			map.put(message, value);
		}
		mv.addObject(SystemKeyWords.LOCAL_ATTRIBUTE_NAME, map);
	}

	protected String getI18nMessage(String key) {
		return i18nLoader.getProperty(key);
	}

	protected String getRequestValue(final HttpServletRequest request,
			String paramName, boolean escape) {
		String paramValue = request.getParameter(paramName);
		if (null != paramValue) {
			paramValue = StringUtils.trim(paramValue);
//			if (escape) {
//				paramValue = StringEscapeUtils.escapeHtml(paramValue);
//				paramValue = StringEscapeUtils.escapeSql(paramValue);
//			}
			return paramValue;
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	protected String getRequestValue(final HttpServletRequest request,
			String paramName, boolean escape, boolean trim) {
		String paramValue = request.getParameter(paramName);
		if (null != paramValue) {
			if(trim){
				paramValue = StringUtils.trim(paramValue);
			}
			
			if (escape) {
//				paramValue = StringEscapeUtils.escapeHtml(paramValue);
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
	
	protected String[] getRequestValues(final HttpServletRequest request,
			String paramName) {
		return getRequestValues(request, paramName, true);
	}
	
	protected String[] getRequestValues(final HttpServletRequest request,
			String paramName, boolean escape) {
		String[] paramValues = request.getParameterValues(paramName);
		if (null != paramValues) {
			for(String paramValue:paramValues){
				paramValue = StringUtils.trim(paramValue);
				if (escape) {
					paramValue = StringEscapeUtils.escapeHtml(paramValue);
					paramValue = StringEscapeUtils.escapeSql(paramValue);
				}
			}
			return paramValues;
		} else {
			return null;
		}
	}
	/**
	* @author 庄敬飞
	* @Title: validateWithException
	* @Description: 值对象验证
	* @param @param object
	* @param @return    设定文件
	* @return JSONObject    返回类型
	* @throws
	*/ 
	protected <T> JSONObject validateWithException(T object) {
		JSONObject json = new JSONObject();
		try {
			BeanValidators.validateWithException(validator, object);
			json.put(RETURN_CODE, RESULT_OK);
		} catch (ConstraintViolationException cve) {
			Map<String, String> map = BeanValidators
					.extractPropertyAndMessage(cve);
			Set<String> keys = map.keySet();
			for (String key : keys) {
				String messageTemplate = MapUtils.getString(map, key);
				map.put(key, i18nLoader.getProperty(messageTemplate));
			}
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,
			JSONObject.fromObject(map).toString());
		}
		return json;
	}
	
	protected Org getOrg(HttpServletRequest request){
		return (Org) request.getSession().getAttribute("userBean");
	}
	
	protected String getOrgId(HttpServletRequest request){
		return String.valueOf(this.getOrg(request).getOrgId());
	}
	
	protected List<String> getMenuMessages(){
		List<String> messages = new ArrayList<String>();
		//主页
		messages.add("eaap.op2.portal.index.index");
		//登录  
		messages.add("eaap.op2.portal.index.login");
		//开发者中心
		messages.add("eaap.op2.portal.index.devIndex");
		//供应者中心
		messages.add("eaap.op2.portal.index.provIndex");
		//合作伙伴中心
		messages.add("eaap.op2.portal.index.pradIndex");
		//文档
		messages.add("eaap.op2.portal.index.docIndex");
		//支持
		messages.add("eaap.op2.portal.index.supportIndex");
		//向导
		messages.add("eaap.op2.portal.index.guide");
		//开发者向导
		messages.add("eaap.op2.portal.index.devGuide");
		//供应者向导
		messages.add("eaap.op2.portal.index.proGuide");
		//合作伙伴向导
		messages.add("eaap.op2.portal.index.parGuide");
		//管理中心
		messages.add("eaap.op2.portal.index.manageCenter");
		//我的应用
		messages.add("eaap.op2.portal.index.myApp");
		//新建应用
		messages.add("eaap.op2.portal.index.createApp");
		//统计
		messages.add("eaap.op2.portal.index.statistics");
		//我的系统
		messages.add("eaap.op2.portal.index.mySystem");
		//新建系统
		messages.add("eaap.op2.portal.index.createSystem");
		//服务规格
		messages.add("eaap.op2.portal.index.serviceSpec");
		//新增服务规格
		messages.add("eaap.op2.portal.index.addServiceSpec");
		//产品
		messages.add("eaap.op2.portal.index.product");
		//新增产品
		messages.add("eaap.op2.portal.index.addProduct");
		//产品定价
		messages.add("eaap.op2.portal.index.productOffer");
		//新增产品定价
		messages.add("eaap.op2.portal.index.addProductOffer");
		//产品属性
		messages.add("eaap.op2.portal.pardSpec.attrSpec");
		//产品交付流程
		messages.add("eaap.op2.portal.index.productDeliverFlow");
		//用户信息
		messages.add("eaap.op2.portal.index.userInfo");
		//注销
		messages.add("eaap.op2.portal.index.signOut");
		//注册
		messages.add("eaap.op2.portal.index.signUp");
		//定单管理
		messages.add("eaap.op2.portal.index.orderManager");
		messages.add("eaap.op2.portal.index.360view");
		messages.add("eaap.op2.portal.index.rtiView");
		return messages;
	}
	
	public boolean isCms(){
		Object isCmsObj = WebPropertyUtil.getPropertyValue("o2p_portal_cms");
		if(isCmsObj == null || StringUtil.isEmpty(String.valueOf(isCmsObj))){
			return false;
		}else{
			return Boolean.valueOf(String.valueOf(isCmsObj));
		}
	}
	
	public Integer getTenantId(){
		Integer tenantId = O2pWebCommonUtil.getDefalutTenantId(); 
		UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
		if(userRoleInfo != null){
			tenantId = userRoleInfo.getTenantId();
		}
		return tenantId;
	}
	
}
