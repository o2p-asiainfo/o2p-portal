/** 
 * Project Name:o2p-portal-pro 
 * File Name:ProcessController.java 
 * Package Name:com.ailk.o2p.portal.controller.process 
 * Date:2015年8月19日下午6:03:32 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
 */

package com.ailk.o2p.portal.controller.process;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.o2p.common.spring.config.CustomPropertyConfigurer;
import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.process.IProcessSrv;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.WebConstants;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * ClassName:ProcessController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月19日 下午6:03:32 <br/>
 * 
 * @author m
 * @version
 * @since JDK 1.6
 * @see
 */
@Controller
public class ProcessController extends BaseController {
	private static Logger log = Logger.getLog(ProcessController.class);

	/**
	 * 流程下一环节路径的确定方法信息
	 * 
	 * @param map
	 */
	@Autowired
	private IProcessSrv processSrv;

	/**
	 * 添加流程&打开流程 openingProcess:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author m
	 */
	@RequestMapping(value = "/process/openingProcess")
	@ResponseBody
	public ModelAndView openingProcess(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String finalStr = "";
		List<String> messages = getUTC();
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取orgId
		Org orgBean = getOrg(request);
		String orgId = StringUtil.valueOf(orgBean.getOrgId());
//		String optType = getRequestValue(request, "optType");
		String processId = getRequestValue(request, "processId");
		String systemId = request.getParameter("systemId");
		map.put("orgId", orgId);
		map.put("systemId", systemId);
		map.put("country_id", orgId);
		map.put("system_id", systemId);
		map.put("processId", processId);// processId flow_id
		// 添加
		
		String xmlContent = "";
		String flag = "0";
		String processName = "";
		String status = "";
		String statusUp="";
		String flowStatus="";
		//系统状态
		//status = processSrv.getSystemStatus(map);
		List statusL = processSrv.getSystemStatus(map); 
		flowStatus=processSrv.getProvisioningProcessState(map);
		if(statusL.size()>0){
			Map statusM = (Map)statusL.get(0);
			status = StringUtil.valueOf(statusM.get("STATE"));
			statusUp = StringUtil.valueOf(statusM.get("UP_STATE"));
		}
		if (processId!=null&&(!"".equals(processId))) {
			// 查看或者修改流程
			Map msg = processSrv.getProcess(map);
			processName =  StringUtil.valueOf(msg.get("processName"));
			JSONObject object = JSONObject.fromObject(msg.get("message"));
			String code = (String) object.getString("code");
			if ("0000".equals(code)) {
				xmlContent = (String) object.get("desc");
				xmlContent = URLDecoder.decode(xmlContent, "UTF-8");

				flag = "1";
			}

//		} else {
			// 添加流程
//			xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//					+ "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\">"
//					+ "<process id=\"myProcess\" name=\"My process\" isExecutable=\"true\"/>"
//					+ "<bpmndi:BPMNDiagram id=\"BPMNDiagram_myProcess\">"
//					+ "<bpmndi:BPMNPlane bpmnElement=\"myProcess\" id=\"BPMNPlane_myProcess\"/>"
//					+ "</bpmndi:BPMNDiagram>" + "</definitions>";
		}

		// addTranslateMessage(mv, messages);
		String tenantCode = null;
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		if(userRoleInfo !=null&&userRoleInfo.getTenantId()!=null){
			String tenantId= userRoleInfo.getTenantId().toString();
			Tenant  tenant = new Tenant();
			tenant.setTenantId(Integer.valueOf(tenantId));
			tenant = processSrv.queryTenant(tenant);
			tenantCode=  tenant.getCode(); 
		}
		String regex="\\u0024\\u002EtenantCode";
		String messageFlowUrl = WebPropertyUtil.getPropertyValue("message_flow_pro_url");
	    messageFlowUrl = messageFlowUrl.replaceAll(regex,tenantCode);
		mv.addObject("orgId", orgId);
		mv.addObject("systemId", systemId);
		mv.addObject("processId", processId);
		mv.addObject("processName", processName);
		mv.addObject("finalStr", finalStr);
		mv.addObject("status", status);
		mv.addObject("statusUp", statusUp);
		mv.addObject("flag", flag);
		mv.addObject("xmlContent", xmlContent);
		mv.addObject("messageFlowUrl", messageFlowUrl);
		mv.addObject("flowStatus", flowStatus);
		String noticeServiceId = WebPropertyUtil.getPropertyValue("NOTICE_SERVICE_ID");  //从公共配置文件eaap_common.properties中读取通用的通知服务ID
		String messageFlowId = WebPropertyUtil.getPropertyValue("NOTICE_MESSAGE_FLOW_ID");  //从公共配置文件eaap_common.properties中读取公用通知消息流ID
		mv.addObject("noticeServiceId", noticeServiceId);
		mv.addObject("noticeMessageFlowId", messageFlowId);
		mv.setViewName("../process/openingProcess.jsp");
		return mv;
		// mv.setViewName("../provider/settleAdd.jsp");
	}

	/**
	 * 获取流程节点信息
	 */
	@RequestMapping(value = "/process/getFlowProcessJson", method = RequestMethod.POST)
	@ResponseBody
	public String getFlowProcessJson(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			// 获取节点和线 finalStr
			Map<String, Object> map = new HashMap<String, Object>();
			String processId = request.getParameter("processId");
			map.put("processId", processId);
			JSONObject jsonObject = processSrv.getProcessJson(map);
			String finalStr = "";
			finalStr = jsonObject.toString();
			json.put("resTEXTData", finalStr);
			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}

	/**
	 * 提存流程 saveOpeningProcess:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author m
	 */
	@RequestMapping(value = "/process/saveOpeningProcess", method = RequestMethod.POST)
	@ResponseBody
	public String saveOpeningProcess(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			Org orgBean = getOrg(request);
			String orgId = StringUtil.valueOf(orgBean.getOrgId());
			String finalStr = request.getParameter("finalStr");
//			String pathAutoExecute = request
//					.getParameter("pathAutoExecuteMethodJson");
//			String pathDet = request.getParameter("pathDetJson");
			String processName = request.getParameter("processName");
			JSONObject finalStrJson = JSONObject.fromObject(finalStr);
			JSONObject processModel = finalStrJson.getJSONObject("process");
			// 获取xml
			String xml = request.getParameter("xml"); 
			// 获取SystemId
			String systemId = request.getParameter("system_id");
			String flowStatus=request.getParameter("flowStatus");
			//验证系统ID 是否为 ORG下的系统
			Map map = new HashMap();
			map.put("systemId", systemId);
			map.put("orgId", orgId);
			map.put("xmlContent", xml);
			map.put("modelName", processName);
			map.put("modelId", processModel.get("processId"));
			map.put("finalStr", finalStr);
			if(!StringUtils.isEmpty(flowStatus)){
				if("T".equals(flowStatus)){
					map.put("flowStatus", "D");
				}else{
					map.put("flowStatus", flowStatus);
				}
			}else{
				map.put("flowStatus", "D");
			}
			
			//判断是否需要保存
			List statusL = processSrv.getSystemStatus(map);
			String processId=null;
			if(statusL.size()>0){
				Map statusM = (Map)statusL.get(0);
				String status = StringUtil.valueOf(statusM.get("STATE"));
				String statusUp = StringUtil.valueOf(statusM.get("UP_STATE"));
				if("A".equals(status)||"C".equals(status)||"G".equals(status)){
					processId = processSrv.saveProcessSrv(map);
				}else if("D".equals(status)){
					if("E".equals(statusUp)||"F".equals(statusUp)){
						processId = processSrv.saveProcessSrv(map);
					}
				}
			}
			if(processId!=null){
				json.put("resTEXTData", processId);
				json.put(RETURN_CODE, RESULT_OK);
			}else{
				json.put("resTEXTData", "");
				json.put(RETURN_CODE, RESULT_ERR);
			}

		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}

	
	/**
	 * 暂存流程 tempSaveOpeningProcess:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author m
	 */
	@RequestMapping(value = "/process/tempSaveOpeningProcess", method = RequestMethod.POST)
	@ResponseBody
	public String tempSaveOpeningProcess(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			Org orgBean = getOrg(request);
			String orgId = StringUtil.valueOf(orgBean.getOrgId());
			String finalStr = request.getParameter("finalStr");
//			String pathAutoExecute = request
//					.getParameter("pathAutoExecuteMethodJson");
//			String pathDet = request.getParameter("pathDetJson");
			String processName = request.getParameter("processName");
			JSONObject finalStrJson = JSONObject.fromObject(finalStr);
			JSONObject processModel = finalStrJson.getJSONObject("process");
			// 获取xml
			String xml = request.getParameter("xml"); 
			// 获取SystemId
			String systemId = request.getParameter("system_id");
			String flowStatus=request.getParameter("flowStatus");
			//验证系统ID 是否为 ORG下的系统
			Map map = new HashMap();
			map.put("systemId", systemId);
			map.put("orgId", orgId);
			map.put("xmlContent", xml);
			map.put("modelName", processName);
			map.put("modelId", processModel.get("processId"));
			map.put("finalStr", finalStr);
			if(!StringUtils.isEmpty(flowStatus)){
				if("D".equals(flowStatus)){
					map.put("flowStatus", "T");
				}else{
					map.put("flowStatus", flowStatus);
				}
			
			}else{
				map.put("flowStatus", "T");
			}
		
			//判断是否需要保存
			List statusL = processSrv.getSystemStatus(map);
			String processId=null;
			if(statusL.size()>0){
				Map statusM = (Map)statusL.get(0);
				String status = StringUtil.valueOf(statusM.get("STATE"));
				String statusUp = StringUtil.valueOf(statusM.get("UP_STATE"));
				if("A".equals(status)||"C".equals(status)||"G".equals(status)){
					processId = processSrv.saveProcessSrv(map);
				}else if("D".equals(status)){
					if("E".equals(statusUp)||"F".equals(statusUp)){
						processId = processSrv.saveProcessSrv(map);
					}
				}
			}
			if(processId!=null){
				json.put("resTEXTData", processId);
				json.put(RETURN_CODE, RESULT_OK);
			}else{
				json.put("resTEXTData", "");
				json.put(RETURN_CODE, RESULT_ERR);
			}

		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}
	
	/**
	 * 获取新环节模板序列
	 * 
	 * @return
	 */
	@RequestMapping(value = "/process/getActivityModelSequence", method = RequestMethod.POST)
	@ResponseBody
	public String getActivityModelSequence(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		JSONObject json = new JSONObject();
		try {
			String activityModelSequence = processSrv
					.getActivityModelSequence();
			json.put("resTEXTData", activityModelSequence);
			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}

	@RequestMapping(value = "/process/setBpmPathDetInfoJson", method = RequestMethod.POST)
	@ResponseBody
	public String setBpmPathDetInfoJson(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String nextTacheMod = request.getParameter("nextTacheMod");
			String procMod = request.getParameter("procMod");
			String actMod = request.getParameter("actMod");
			String systemId = request.getParameter("systemId");

			HashMap map = new HashMap();
			map.put("procMod", procMod);
			map.put("actMod", actMod);
			map.put("systemId", systemId);
			String pathDetJson = processSrv.getpathDetJson(map);
			String rst = pathDetJson + "|&" + nextTacheMod;
			json.put("resTEXTData", rst);
			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();

	}

	@RequestMapping(value = "/process/delProcess", method = RequestMethod.POST)
	@ResponseBody
	public String delProcess(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {

			String processId = getRequestValue(request, "processId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", processId);
			processSrv.deleteFlowMid(map);

			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();

	}

	// 加载国际化信息
	public List<String> getUTC() {
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.portal.process.COUNTRY");
		messages.add("eaap.op2.portal.process.TACHEINFO");
		messages.add("eaap.op2.portal.process.FROM_TACHE");
		messages.add("eaap.op2.portal.process.TO_TACHE");
		messages.add("eaap.op2.portal.process.ROUTING_CONDITIONS");
		messages.add("eaap.op2.portal.process.CONDITIONS");
		messages.add("eaap.op2.portal.process.VALUE");
		messages.add("eaap.op2.portal.process.OK");
		messages.add("eaap.op2.portal.process.CANCEL");
		messages.add("eaap.op2.portal.process.TACHE_ID");
		messages.add("eaap.op2.portal.process.TACHE_NAME");
		messages.add("eaap.op2.portal.process.TACHE_TYPE");
		messages.add("eaap.op2.portal.process.API_BY_PARAMETERS");
		messages.add("eaap.op2.portal.process.THE_ORIGINAL_MESSAGE");
		messages.add("eaap.op2.portal.process.A_LINK_TO_RETURN_A_MESSAGE");
		messages.add("eaap.op2.portal.process.INVOKE_API");
		messages.add("eaap.op2.portal.process.DESCR");
		messages.add("eaap.op2.portal.process.IN_PARAM_CONVERT");
		messages.add("eaap.op2.portal.process.OUT_PARAM_CONVERT");
		messages.add("eaap.op2.portal.process.FLOW_NAME");
		messages.add("eaap.op2.portal.process.SAVE_PROCESS");
		messages.add("eaap.op2.portal.process.SUBMIT_PROCESS");
		messages.add("eaap.op2.portal.process.LINE_ID");
		messages.add("eaap.op2.portal.process.OUT_PARAMS");
		messages.add("eaap.op2.portal.process.IN_PARAMS");
		messages.add("eaap.op2.portal.process.LINE_ID");
		messages.add("eaap.op2.portal.process.CLASSIFICATION");
		messages.add("eaap.op2.portal.process.CAPABILITY_NAME");
		messages.add("eaap.op2.portal.process.CAPABILITY_PARAMETER");
		messages.add("eaap.op2.portal.process.CAPABILITY");
		return messages;
	}
	

	/**
	 * 获取已配的工作流通知任务节点配置信息 
	 * @author lxh
	 */
	@RequestMapping(value = "/process/loadNoticeTaskConf", method = RequestMethod.POST)
	@ResponseBody
	public String loadNoticeTaskConf(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String taskId = request.getParameter("taskId");
			Map map = new HashMap();
			map.put("taskId", taskId);
			JSONObject confJson = new JSONObject();
			List confList = processSrv.getWorkflowNoticeTaskConfList(map);
			if(confList.size()>0){
				Map cMap = (Map)confList.get(0);
				String emailAddress 	= StringUtil.valueOf(cMap.get("EMAILADDRESS"));
				String emailTitle 	= StringUtil.valueOf(cMap.get("EMAILTITLE"));
				String emailContent 	= StringUtil.valueOf(cMap.get("EMAILCONTENT"));
				String smsAddress 		= StringUtil.valueOf(cMap.get("SMSADDRESS"));
				String smsContent 		= StringUtil.valueOf(cMap.get("SMSCONTENT"));
				confJson.put("EMAIL_ADDRESS", emailAddress);
				confJson.put("EMAIL_TITLE", emailTitle);
				confJson.put("EMAIL_CONTENT", emailContent);
				confJson.put("SMS_ADDRESS", smsAddress);
				confJson.put("SMS_CONTENT", smsContent);
				json.put("confInfo", confJson);
			}
			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}
	

	/**
	 * 保存工作流通知任务节点配置信息 
	 * @author lxh
	 */
	@RequestMapping(value = "/process/saveNoticeTaskConf", method = RequestMethod.POST)
	@ResponseBody
	public String saveNoticeTaskConf(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String taskId = request.getParameter("taskId");
			String msAddress = request.getParameter("msAddress");
			String emailTitle = request.getParameter("emailTitle");
			String msContent = request.getParameter("msContent");
			String msType = request.getParameter("msType");

			Map map = new HashMap();
			map.put("taskId", taskId);
			if("EMAIL".equals(msType)){
				map.put("emailAddress", msAddress);
				map.put("emailTitle", emailTitle);
				map.put("emailContent", msContent);
			}else if("SMS".equals(msType)){
				map.put("smsAddress", msAddress);
				map.put("smsContent", msContent);
			}
			
			//判断是否存在
			List confList = processSrv.getWorkflowNoticeTaskConfList(map);
			if(confList.size()>0){
				processSrv.updateWorkflowNoticeTaskConf(map);
			}else{
				processSrv.insertWorkflowNoticeTaskConf(map);
			}
			json.put(RETURN_CODE, RESULT_OK);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC, e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), e));
		}
		return json.toString();
	}
	
	
	
	
//	@RequestMapping(value = "/process/exportProcess", method = RequestMethod.GET)
//	@ResponseBody
//	public String exportProcess(final HttpServletRequest request,
//			final HttpServletResponse response){
//		XMLWriter xmlWriter = null;
//		InputStream inputStream = null;
//		OutputStream os = null;
//		File file = null;
//		try{
//			//获取流程模板文件
//			String modelId = getRequestValue(request, "processId");
//			String systemId = getRequestValue(request, "systemId");
//			String folder = System.getProperty("java.io.tmpdir", "/tmp/");
//			folder = folder+ File.separator + systemId;
//			File folderFile = new File(folder); 
//			if (folderFile.exists()){
//				folderFile.delete(); 
//			}else{
//				folderFile.mkdirs();
//			}
//			System.out.println("############# folder:" + folder);
//			
//			this.createExportXls(folder, modelId);
//			RestTemplate rest = new RestTemplate(); 
//    	    String jsonStr = rest.getForObject(CommonConfigurations.getProperty("work_flow_pro_url")+"/deploy/getModelXml/{modelId}", String.class,modelId); 
//			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
//			String processName = jsonObject.getString("processName");
//			String xmlContent = jsonObject.getString("xmlContent"); 
//			if(!StringUtils.isEmpty(xmlContent)){
//				xmlContent = URLDecoder.decode(xmlContent,"UTF-8");
//				
//				Document document = DocumentHelper.parseText(xmlContent);
//		        OutputFormat format = new OutputFormat("",true);  
//		        format.setEncoding("UTF-8");
//		        String filePath = folder +File.separator+ processName;
//		        xmlWriter = new XMLWriter(new FileOutputStream(filePath),format);  
//		        xmlWriter.write(document);  
//		        
//		        response.setCharacterEncoding("utf-8");
//		        response.setContentType("multipart/form-data");
//		        response.setHeader("Content-Disposition", "attachment;fileName="+ processName);
//		        
//		        file = new File(filePath);
//	            inputStream = new FileInputStream(new File(filePath));
//	 
//	            os = response.getOutputStream();
//	            byte[] b = new byte[2048];
//	            int length;
//	            while ((length = inputStream.read(b)) > 0) {
//	                os.write(b, 0, length);
//	            }
//			}
//		}catch (Exception e) {
//			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
//					ExceptionCommon.WebExceptionCode, CommonUtil.getErrMsg(e), e)); 
//		}finally{
//			try{
//				if(xmlWriter != null){
//					xmlWriter.close();
//				}
//				if(os != null){
//					os.close();
//				}
//				if(inputStream != null){
//					inputStream.close();
//				}
//				if(file != null){
//					file.deleteOnExit();
//				}
//			}catch (Exception e) {
//				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
//						ExceptionCommon.WebExceptionCode, CommonUtil.getErrMsg(e), e));
//			}
//		}
//		
//		return null;
//	}
	

	
//	public String saveProcess(String modelName,String xmlContent) throws Exception{
//		xmlContent = URLEncoder.encode(xmlContent, "UTF-8"); 
//		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<String, Object>();
//		requestBody.add("modelId", ""); 
//		requestBody.add("modelName", modelName);
//		requestBody.add("xmlContent", xmlContent);
//		
//		RestTemplate restTemplate =  (RestTemplate) SpringContextUtil.getBean("getRestTemplate");
//		String restPath = CommonConfigurations.getProperty("work_flow_pro_url")+"/deploy/saveModel";
//		System.out.println("################## restPath:" + restPath);
//		return restTemplate.postForObject(restPath,requestBody, String.class); 
//		 
//	}
//	
//	private void createExportXls(String folder,String modelId) throws Exception{
//		WritableWorkbook workBook = null;
//		try{
//			String excelName = folder + File.separator + "workTaskConf.xls";
//			System.out.println("######## excelName:" + excelName);
//			File excelFile = new File(excelName);
//			workBook = Workbook.createWorkbook(excelFile);    
//			WritableSheet taskConfSheet = workBook.createSheet("work_task_conf", 0);
//			this.createWorkTaskHeader(taskConfSheet);
//			
//			//获取WorkTaskConf
//			List<WorkTaskConf> workTaskConfList = processSrv.getWorkTaskConfList(modelId);
//			this.createWorkTaskXls(taskConfSheet, workTaskConfList);
//			String confId = "";
//			int i = 0;
//			for (WorkTaskConf workTaskConf : workTaskConfList) {
//				if(i == 0){
//	    			 confId = workTaskConf.getId();
//	    		}else{
//	    			 confId += ","+workTaskConf.getId();
//	    		}
//				i++;
//			}
//			List<WorkTaskContion> workTaskContionList = processSrv.queryWorkTaskContionObjList(confId);
//			WritableSheet contionSheet = workBook.createSheet("work_task_contion", 1);
//			this.createWorkConditionHeader(contionSheet);
//			this.createWorkConditionXls(contionSheet, workTaskContionList); 
//			
//		}finally{
//	        if(workBook != null){
//	        	workBook.write();   
//	        	workBook.close();
//	        }
//		}
//		
//	}
//	
//	
//	private void createWorkTaskXls(WritableSheet sheet,List<WorkTaskConf> workTaskConfList) throws Exception{
//		int row = 1;
//		for (WorkTaskConf workTaskConf : workTaskConfList) {
//			int currentRow = row++;
//			int curentColunn = 0;
//			
//			Label label = new Label(curentColunn++, currentRow, workTaskConf.getId());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getBusinessId());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getTacheId());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getTacheName());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getFromTacheId());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getTaskType());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getInvokeApi());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskConf.getApiParamMode()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getApiParamInId());
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getApiParamOutId());
//			sheet.addCell(label);
//			
//			label = new Label( curentColunn++, currentRow, ""); 
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, workTaskConf.getSerInvokeInsId());  
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskConf.getIsSchedule()));  
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskConf.getExpressId()));   
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskConf.getScheduleCount()));   
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskConf.getDescription()));   
//			sheet.addCell(label);
//		}
//	}
//	
//	private void createWorkConditionXls(WritableSheet sheet,List<WorkTaskContion> workConditionList) throws Exception{
//		int row = 1;
//		for(WorkTaskContion workTaskContion : workConditionList){
//			int currentRow = row++;
//			int curentColunn = 0;
//			Label label = new Label(curentColunn++, currentRow,  getObjectStr(workTaskContion.getId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getConfId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getContionKey()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getContionValue()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getTranstionId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getTranstionId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getTranstionId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getFromId()));
//			sheet.addCell(label);
//			
//			label = new Label(curentColunn++, currentRow, getObjectStr(workTaskContion.getToId())); 
//			sheet.addCell(label);
//		}
//	}
//	
//	
//	private String getObjectStr(Object obj){
//		return obj == null ? "" : String.valueOf(obj);
//	}
//	
//	private void createWorkTaskHeader(WritableSheet sheet) throws Exception{
//		int column = 0;
//		
//		Label label = new Label(column++, 0,  "ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0,  "BUSINESS_KEY");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "TACHE_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "TACHE_NAME");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "FROM_TACHE_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "TASK_TYPE");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "INVOKE_API");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "API_PARAM_MODE");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "API_PARAM_IN_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "API_PARAM_OUT_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "MESSAGE_FLOW_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "SER_INVOKE_INS_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "IS_SCHEDULES");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "EXPRESS_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "SCHEDULE_COUNT");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "DESCRIPTION");
//		sheet.addCell(label);
//	}
//	
//	public void createWorkConditionHeader(WritableSheet sheet) throws Exception{
//		int column = 0;
//		Label label = new Label(column++, 0,  "ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "CONF_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "CONTION_KEY");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "CONTION_VALUE");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0, "TRANSTION_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0,  "FROM_ID");
//		sheet.addCell(label);
//		
//		label = new Label(column++, 0,  "TO_ID");
//		sheet.addCell(label);
//	}
	
//	@RequestMapping(value = "/process/importProcess", method = RequestMethod.POST)  
//	@ResponseBody
//	public String importProcess(@RequestParam(value = "file", required = false)MultipartFile file,HttpServletRequest request) {
//		JSONObject json = new JSONObject();
//		StringBuilder sb = new StringBuilder("");  
//		String modelName = request.getParameter("modelName");
//		String line = null;   
//		BufferedReader reader =null;
//		String code = RESULT_ERR;
//        try {  
//        	reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8")); 
//        	while ((line = reader.readLine()) != null) {
//                sb.append(line+"\n");
//            } 
//        	
//        	String jsonStr = this.saveProcess(modelName, sb.toString());
//        	JSONObject object = JSONObject.fromObject(jsonStr);
//    		String result = (String) object.getString("code");
//    		String processId = "";
//    		if ("0000".equals(result)) {
//    			processId = object.getString("desc");
//    		} 
//    		code = RESULT_OK;
//    		json.put(RETURN_CODE, code);
//    		json.put("processId", processId);
//        } catch (Exception e) { 
//			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"importProcess exception:" + e.getMessage(),e));
//        } finally{
//        	if(reader!=null){
//				try {
//					reader.close();
//				} catch (IOException e) {
//					log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"importProcess exception:" + e.getMessage(),e));
//				}
//        	}
//        }
//		return json.toString();
//	}
//	

}
