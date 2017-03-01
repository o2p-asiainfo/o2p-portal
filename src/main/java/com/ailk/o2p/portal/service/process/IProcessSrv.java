/** 
 * Project Name:o2p-portal-pro 
 * File Name:IProcessSrv.java 
 * Package Name:com.ailk.o2p.portal.service.process 
 * Date:2015年8月19日下午6:07:12 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.service.process;  

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.bo.WorkTaskContion;

import net.sf.json.JSONObject;

/** 
 * ClassName:IProcessSrv <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年8月19日 下午6:07:12 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public interface IProcessSrv {
	public String saveProcessSrv(Map map);
	public String getActivityModelSequence();
	public String getpathDetJson(Map map);
	public String saveProcess(Map map);
	public void saveFlowMid(Map<String, Object> paraMap); 
	public Map getProcess(Map map);
	public String getProvisioningProcessState(Map map);
	public String getProvisioningProcessName(Map map);
	public String insertWorkTaskConf(WorkTaskConf workTaskConf);
	public String insertWorkTaskContion(WorkTaskContion workTaskContion);
	public List getJsontoList(JSONObject json);
	public JSONObject getProcessJson(Map map);
	public String deleteFlowMid(Map<String, Object> map);
	public String selectService(Map map);
	public void deleteWorkTaskContion(String confId);
	public void deleteWorkTaskConf(String modelId);
	public Integer checkSysOrg(Map map);
	public void updateWorkTaskConf(WorkTaskConf workTaskConf);
	public List getSystemStatus(Map map);
	public List getWorkflowNoticeTaskConfList(Map map);
	public void insertWorkflowNoticeTaskConf(Map map);
	public void updateWorkflowNoticeTaskConf(Map map);
	public String getProcessKeyByComponentId(Map map);
	
	public Tenant queryTenant(Tenant tenant);
	
}
