/** 
 * Project Name:o2p-portal-pro 
 * File Name:ProcessSrv.java 
 * Package Name:com.ailk.o2p.portal.service.process 
 * Date:2015年8月19日下午6:08:00 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.service.process;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.bo.WorkTaskContion;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.ailk.o2p.portal.dao.process.IProcessDao;
import com.ailk.o2p.portal.utils.RestClient;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/** 
 * ClassName:ProcessSrv <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年8月19日 下午6:08:00 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
@Service
@Transactional
public class ProcessSrv implements IProcessSrv{
	/**
	 * 流程下一环节路径的确定方法信息
	 * @param map
	 */
	@Autowired
 	private IProcessDao processDao;
	RestTemplate simpleRestTemplate;
	@Autowired
	private IOrgDao orgSqlDAO;
	
	private static Log log = LogFactory.getLog(ProcessSrv.class);
	
	public String getActivityModelSequence(){
		 
		return processDao.getActivityModelSequence();
	}
	
	public String saveProcessSrv(Map map){
		int check =0 ;
		check = checkSysOrg(map);
		if(check<=0){
			return "";
		}  
		// 将xml 和systemId 传给workflow 工程 ，由workflow 返回流程IDmodelID\
		
		
		String rest = saveProcess(map);
		// 将流程Id系统ID和orgID存表。
		JSONObject object = JSONObject.fromObject(rest);
		String code = (String) object.getString("code");
		String processId = "";
		if ("0000".equals(code)) {
			processId = object.getString("desc");
		} else {
			return "";
		}

		 //保存system org flow 的关系
		String system_id =StringUtil.valueOf(map.get("systemId"));
		if (null != processId && !"".equals(processId)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("flow_id", processId);
			paraMap.put("country_id", map.get("orgId"));
			paraMap.put("system_id", system_id);
			paraMap.put("flow_state", map.get("flowStatus"));
			saveFlowMid(paraMap);
		}
		// 保存路由以及节点信息
		// 循环所有节点，找出操作节点，
		// 循环线找出路由线插入
		WorkTaskConf workTaskConf = null;
		String finalStr = StringUtil.valueOf(map.get("finalStr"));
		JSONObject finalStrJson = JSONObject.fromObject(finalStr);
		JSONObject activityModel = finalStrJson.getJSONObject("nodes");
		JSONObject transtionModel = finalStrJson.getJSONObject("lines");
		List activities = getJsontoList(activityModel);
		List transtions = getJsontoList(transtionModel);
		// insertWorkTaskConf
		// insertWorkTaskContion
		//删除节点 delete
		
		deleteWorkTaskContion(processId);
		deleteWorkTaskConf(processId);
		StringBuilder froms = null;
		for (int index = 0; index < activities.size(); index++) { // 保存各个环节信息
			froms=new StringBuilder();
			JSONObject activity_Node = (JSONObject) activities.get(index);
			String act_Type = activity_Node.getString("act_type");
			if ("exclusiveGateway".equals(act_Type)
					|| "inclusiveGateway".equals(act_Type)) {
				workTaskConf = new WorkTaskConf();
				String id = activity_Node.getString("id");
				
				workTaskConf.setTacheId(id);
				workTaskConf.setTacheName(activity_Node.getString("name"));
				workTaskConf.setTaskType(act_Type);
				// workTaskConf.setDescription(activity_Node.getString("remark"));
				workTaskConf.setBusinessId(system_id);
				workTaskConf.setModelId(processId);
				workTaskConf.setIsSchedule(Integer
						.parseInt(StringUtil.valueOf(activity_Node.getString("isSchedule"))));
				workTaskConf.setScheduleCount(Integer
						.parseInt(StringUtil.valueOf(activity_Node.getString("scheduleCount"))));
				// 插入 workTaskConf 表 返回 conf_id
				String workTaskConfId = insertWorkTaskConf(workTaskConf);
				
				for (int j = 0; j < transtions.size(); j++) {
					JSONObject transtion_Node = (JSONObject) transtions.get(j);
					String fromId = transtion_Node.getString("from");
					String to = transtion_Node.getString("to");
					if(id.equals(to)){
						froms.append(fromId).append(",");
					}
					if (id.equals(fromId)) {

						if (!StringUtil.isEmpty(transtion_Node.getString("condition"))) {
							WorkTaskContion workTaskContion = null;
							String transtionId = StringUtil.valueOf(transtion_Node.getString("id"));
 
							String conditions = StringUtil.valueOf(transtion_Node.getString("condition"));
							String conditionValues = StringUtil.valueOf(transtion_Node.getString("conditionValue"));
							conditions = conditions.substring(1,conditions.length() - 1);
							conditions = conditions.replace("\"", "");
							conditionValues = conditionValues.substring(1,conditionValues.length() - 1);
							conditionValues = conditionValues.replace("\"","");
							String conditionArr[] = conditions.split(",");
							String conditionValueArr[] = conditionValues.split(",");

							if (conditionArr.length > 0) {
								for (int k = 0; k < conditionArr.length; k++) {
									workTaskContion = new WorkTaskContion();
									workTaskContion.setContionKey(conditionArr[k]);
									workTaskContion.setContionValue(conditionValueArr[k]);
									workTaskContion.setConfId(StringUtil.valueOf(workTaskConfId));
									workTaskContion.setTranstionId(transtionId);
									workTaskContion.setFromId(transtion_Node.getString("from"));
									workTaskContion.setToId(transtion_Node.getString("to"));
									insertWorkTaskContion(workTaskContion);
								}
							}

						}
					}
				}
				workTaskConf.setFromTacheId(froms.toString());
				 updateWorkTaskConf(workTaskConf);
			} else if ("receiveTask".equals(act_Type)
					|| "userTask".equals(act_Type)
					|| "serviceTask".equals(act_Type)||"begin".equals(act_Type)||"end".equals(act_Type)||"noticeTask".equals(act_Type)) {
				
				workTaskConf = new WorkTaskConf();
				String id = activity_Node.getString("id");
				workTaskConf.setTacheId(id);
				workTaskConf.setTacheName(activity_Node.getString("name"));
				workTaskConf.setTaskType(act_Type);
				workTaskConf.setDescription(StringUtil
						.valueOf(activity_Node.getString("remark")));
				workTaskConf.setBusinessId(system_id);
				workTaskConf.setModelId(processId);
				workTaskConf.setApiParamMode(Integer.parseInt(activity_Node
						.getString("auto_exec_method")));
				workTaskConf.setInvokeApi(StringUtil.valueOf(activity_Node
						.getString("invoke_api")));
				workTaskConf.setApiParamInId(StringUtil
						.valueOf(activity_Node.getString("inParam")));
				workTaskConf.setApiParamOutId(StringUtil
						.valueOf(activity_Node.getString("outParam")));
				workTaskConf
						.setSerInvokeInsId(StringUtil.valueOf(activity_Node
								.getString("serInvokeInsId")));
				workTaskConf.setMessageFlowId(StringUtil
						.valueOf(activity_Node.getString("messageFlowId")));
				for (int j = 0; j < transtions.size(); j++) {
					JSONObject transtion_Node = (JSONObject) transtions.get(j);
					String fromId = transtion_Node.getString("from");
					String to = transtion_Node.getString("to");
					if(id.equals(to)){
						froms.append(fromId).append(",");
					}
				}
				workTaskConf.setFromTacheId(froms.toString());
				String isSchedule = StringUtil.valueOf(activity_Node.getString("isSchedule"));
				if(null!=isSchedule&&!"".equals(isSchedule)){
					workTaskConf.setIsSchedule(Integer
							.parseInt(StringUtil.valueOf(activity_Node.getString("isSchedule"))));
				}
				String scheduleCount = activity_Node.getString("scheduleCount");		
				if(null!=scheduleCount&&!"".equals(scheduleCount)){
					workTaskConf.setScheduleCount(Integer
							.parseInt(StringUtil.valueOf(activity_Node.getString("scheduleCount"))));
				}
				
				String workTaskConfId = insertWorkTaskConf(workTaskConf);
			}
		}
		return processId;
	}
	public String getpathDetJson(Map map){
		JSONArray pathDetJsonArray = new JSONArray(); 
		try{
			//条件：
			Map paramMap=new HashMap();
			List pathDetConditionList = processDao.getPathDetConditionList(paramMap);
			for(int i=0;i<pathDetConditionList.size();i++){
		        JSONObject conditionJson = new JSONObject();
				Map conditionMap = (Map)pathDetConditionList.get(i); 
				String conditionId			= conditionMap.get("V_DEF_ID").toString();
				String conditionName	= conditionMap.get("V_NAME").toString();
				conditionJson.put("CONDITION_ID", conditionId);
				conditionJson.put("CONDITION_NAME", conditionName);
				
				//值：
				JSONArray valueJsonArray = new JSONArray();
				HashMap vMap = new HashMap() ;
				vMap.put("conditionId", conditionId) ;
				vMap.put("systemId", map.get("systemId"));
				List pathDetConditionValueList=null;
				if("10004".equals(conditionId)){
					pathDetConditionValueList = processDao.getSystemProduct(vMap);
				}else{
					pathDetConditionValueList = processDao.getPathDetConditionValueList(vMap);
				}
				if(pathDetConditionValueList.size()>0){
					for(int n=0;n<pathDetConditionValueList.size();n++){
		                JSONObject valueJson = new JSONObject ();
						Map valueMap	= (Map)pathDetConditionValueList.get(n);
						String valueId		= valueMap.get("VALUE_ID").toString();
						String valueText	= valueMap.get("VALUE_TEXT").toString();
						valueJson.put("VALUE_ID",valueId);
						valueJson.put("VALUE_TEXT",valueText); 
						valueJsonArray.add(valueJson);
					}
					conditionJson.put("CONDITION_VALUE", valueJsonArray);
					pathDetJsonArray.add(conditionJson);
				}
			}
		}
		catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),e));
//			log.error(e.getStackTrace());
//			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode," get Path to the decision function anomaly ",null));
		}
		return pathDetJsonArray.toString();
	}

	public String saveProcess(Map map){
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<String, Object>();
		requestBody.add("modelId",map.get("modelId"));
		requestBody.add("modelName", map.get("modelName"));
		requestBody.add("xmlContent", map.get("xmlContent"));
		//String message = rest.postForObject("http://localhost:8080/workflow/deploy/saveModel?modelId={modelId}&modelName={modelName}&xmlContent={xmlContent}",null, String.class,urlVariables); 
		
		//RestTemplate restTemplate = new RestTemplate(); 
		//String message = restTemplate.postForObject(CommonConfigurations.getProperty("work_flow_pro_url") + "/deploy/saveModel?modelId={modelId}&modelName={modelName}&xmlContent={xmlContent}", null, String.class,urlVariables);
		RestTemplate restTemplate =  (RestTemplate) SpringContextUtil.getBean("getRestTemplate");
		return restTemplate.postForObject(WebPropertyUtil.getPropertyValue("work_flow_pro_url")+"/deploy/saveModel",requestBody, String.class); 
		 
	}
	
	/**
	 * 保存中间表数据
	 * @param paraMap
	 */
	@Override
	public void saveFlowMid(Map<String, Object> paraMap) {
		processDao.deleteFlowMid(paraMap);
		processDao.saveFlowMid(paraMap);
	}
    public Map getProcess(Map map){
    	//获取流程Id
    	Map returnMap=new HashMap();
    	List list=processDao.getFlowMid(map);
    	String processId ="";
    	String processName ="";
    	if(list.size()>0){
    		Map processInfo = (HashMap)list.get(0);
    		processId = processInfo.get("PROCESSID").toString();
    		processName = StringUtil.valueOf(processInfo.get("PROCESSNAME"));
    	}else{
    		return null;
    	}
    	RestTemplate rest = new RestTemplate(); 
    	
    		//String message = rest.getForObject("http://localhost:8080/workflow/deploy/getModel/{modelId}", String.class,processId); 

    	String message = rest.getForObject(WebPropertyUtil.getPropertyValue("work_flow_pro_url")+"/deploy/getModel/{modelId}", String.class,processId); 
    	returnMap.put("message", message);
    	returnMap.put("processName", processName);
    	//String message = rest.getForObject("http://localhost:8080/workflow/task/taskListByProcessId/{processId}", String.class,processId); 
        return returnMap;
    }
    
    public String getProvisioningProcessState(Map map){
    	String flowStatus=processDao.getProvisioningProcessState(map);
		return flowStatus;
    }
    
	public String  getProvisioningProcessName(Map map){
		String flowName=processDao.getProvisioningProcessName(map);
		return flowName;
	}
    public JSONObject getProcessJson(Map map){
    	String processId = (String)map.get("processId");
    	WorkTaskConf workTaskConf = new WorkTaskConf();
//    	WorkTaskContion workTaskContion = new WorkTaskContion();
    	List<Map<String,Object>> workTaskContionList = null;
    	JSONObject processwork = new JSONObject();
		JSONObject process = new JSONObject();
		Map contionmap = new HashMap();
    	workTaskConf.setModelId(processId);
    	process.put("processId", processId);
    	List<WorkTaskConf> workTaskConfList = processDao.queryWorkTaskConf(workTaskConf);
    	String confId="" ;
    	processwork.put("process",process );
    	
    	JSONObject nodes = new JSONObject();
    	for(int i=0;i<workTaskConfList.size();i++){
    		JSONObject node = new JSONObject();
    		workTaskConf= workTaskConfList.get(i);
    		node.put("id",workTaskConf.getTacheId());
    		node.put("name",workTaskConf.getTacheName());
    		node.put("act_type",workTaskConf.getTaskType());
    		node.put("act_mod",workTaskConf.getTacheId());
    		node.put("act_name",workTaskConf.getTacheName());
    		 
    		node.put("remark", workTaskConf.getDescription());
    		node.put("auto_exec_method", workTaskConf.getApiParamMode());
    		node.put("invoke_api", workTaskConf.getInvokeApi());
    		node.put("inParam", workTaskConf.getApiParamInId());
    		node.put("outParam", workTaskConf.getApiParamOutId());
    		node.put("messageFlowId", workTaskConf.getMessageFlowId());
    		node.put("serInvokeInsId", workTaskConf.getSerInvokeInsId());
    		node.put("isSchedule", workTaskConf.getIsSchedule());
    		node.put("expressId", workTaskConf.getExpressId());
    		node.put("scheduleCount", workTaskConf.getScheduleCount());
    		Map mapservice = new HashMap();
    		mapservice.put("SERVICE_ID", workTaskConf.getInvokeApi());
    		String a = processDao.selectService(mapservice);
    		node.put("invoke_api_name", a);
    		
    		 if(i==0){
    			 confId=workTaskConf.getId();
    		 }else{
    			 confId+=","+workTaskConf.getId();
    		 }
    		 nodes.put(workTaskConf.getTacheId(), node);
    	}
    	processwork.put("nodes",nodes);
    	String []confIds =confId.split(",") ;
    	contionmap.put("confIds", confIds);
    	workTaskContionList = processDao.getWorkTaskContionList(contionmap);
    	JSONObject lines = new JSONObject();
    	Map<String,Object> workTaskContionMap = null;
    	if(null!=workTaskContionList){
    		for(int s =0;s<workTaskContionList.size();s++){
    			workTaskContionMap = workTaskContionList.get(s);
    			JSONObject line = new JSONObject();
//    			"actMod": fid,
//                "nextActMod": tid,
    			line.put("id", workTaskContionMap.get("TRANSTION_ID"));
    			line.put("from", workTaskContionMap.get("FROM_ID"));
    			line.put("to", workTaskContionMap.get("TO_ID"));
    			line.put("actMod", workTaskContionMap.get("FROM_ID"));
    			line.put("nextActMod", workTaskContionMap.get("TO_ID"));
    			if(null!=workTaskContionMap.get("CONTION_KEYS")&& !"".equals(workTaskContionMap.get("CONTION_KEYS"))){
    				line.put("condition", "["+workTaskContionMap.get("CONTION_KEYS")+"]");
        			line.put("conditionValue", "["+workTaskContionMap.get("CONTION_VALUES")+"]");
    			}else{
    				line.put("condition", workTaskContionMap.get("CONTION_KEYS"));
        			line.put("conditionValue", workTaskContionMap.get("CONTION_VALUES"));
    			}
    			
    			lines.put(workTaskContionMap.get("TRANSTION_ID"), line);
    		}
    	}
    	processwork.put("lines",lines); 
    	return processwork;
    }
    
	public String insertWorkTaskConf(WorkTaskConf workTaskConf){
		
		return processDao.insertWorkTaskConf(workTaskConf);
	}
	public String insertWorkTaskContion(WorkTaskContion workTaskContion){
		return processDao.insertWorkTaskContion(workTaskContion);
	}
	public void deleteWorkTaskContion(String confId){
		Map paramMap=new HashMap();
		paramMap.put("modelId", confId);
		 processDao.deleteWorkTaskContion(paramMap);
	}
	
	public String deleteFlowMid(Map<String, Object> map){
		return processDao.deleteFlowMid(map);
	}
	public void deleteWorkTaskConf(String modelId){
		Map paramMap=new HashMap();
		paramMap.put("modelId", modelId);
		processDao.deleteWorkTaskConf(paramMap);
	}
	public String selectService(Map map){
		return processDao.selectService(map);
	}
	public Integer checkSysOrg(Map map){
		return processDao.checkSysOrg(map);
	}
	public void updateWorkTaskConf(WorkTaskConf workTaskConf){
		processDao.updateWorkTaskConf(workTaskConf);
	}
	
	public List getJsontoList(JSONObject json){
		List list = new ArrayList();
		//JSONObject j ;
		for(Object k : json.keySet()){  
          Object v = json.get(k);  
            
         	list.add(v); 
		}
		return list;
	}
	public List getSystemStatus(Map map){
		return processDao.getSystemStatus(map);
	}


	public List getWorkflowNoticeTaskConfList(Map map){
		return processDao.getWorkflowNoticeTaskConfList(map);
	}
	public void insertWorkflowNoticeTaskConf(Map map){
		processDao.insertWorkflowNoticeTaskConf(map);
	}
	public void updateWorkflowNoticeTaskConf(Map map){
		processDao.updateWorkflowNoticeTaskConf(map);
	}
	
	public String getProcessKeyByComponentId(Map map){
		return processDao.getProcessKeyByComponentId(map);
	}
	public Tenant queryTenant(Tenant tenant){
		return orgSqlDAO.queryTenant(tenant);
	}
	
	public IProcessDao getProcessDao() {
		return processDao;
	}
	public void setProcessDao(IProcessDao processDao) {
		this.processDao = processDao;
	}

	public IOrgDao getOrgSqlDAO() {
		return orgSqlDAO;
	}

	public void setOrgSqlDAO(IOrgDao orgSqlDAO) {
		this.orgSqlDAO = orgSqlDAO;
	}
	
	
}
