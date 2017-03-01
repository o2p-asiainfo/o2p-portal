/** 
 * Project Name:o2p-portal-pro 
 * File Name:IProcessDao.java 
 * Package Name:com.ailk.o2p.portal.dao.process 
 * Date:2015年8月19日下午6:10:03 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.dao.process;  

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.bo.WorkTaskContion;

/** 
 * ClassName:IProcessDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年8月19日 下午6:10:03 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public interface IProcessDao {
	public String getActivityModelSequence();
	public List getPathDetConditionList(Map paramMap);
	public List getPathDetConditionValueList(Map map);
	public String saveFlowMid(Map<String, Object> map);
	public String deleteFlowMid(Map<String, Object> map);
	public List getFlowMid(Map<String, Object> map);
	public String  getProvisioningProcessState(Map<String, Object> map);
	public String  getProvisioningProcessName(Map<String, Object> map);
	public String insertWorkTaskConf(WorkTaskConf workTaskConf);
	public String insertWorkTaskContion(WorkTaskContion workTaskContion);
	public List queryWorkTaskConf(WorkTaskConf workTaskConf);
	public List queryWorkTaskContion(WorkTaskContion workTaskContion);
	public List getWorkTaskContionList(Map map);
	public String selectService(Map map);
	public void deleteWorkTaskContion(Map paramMap);
	public void deleteWorkTaskConf(Map paramMap);
	public Integer checkSysOrg(Map map);
	public void updateWorkTaskConf(WorkTaskConf workTaskConf);
	public List getSystemProduct(Map map);
	public List getSystemStatus(Map map);
	public List getWorkflowNoticeTaskConfList(Map map);
	public void insertWorkflowNoticeTaskConf(Map map);
	public void updateWorkflowNoticeTaskConf(Map map);
	public String getProcessKeyByComponentId(Map map);
}
