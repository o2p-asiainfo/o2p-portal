/** 
 * Project Name:o2p-portal-pro 
 * File Name:ProcessDao.java 
 * Package Name:com.ailk.o2p.portal.dao.process 
 * Date:2015年8月19日下午6:10:47 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.dao.process;  

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.bo.WorkTaskContion;
import com.linkage.rainbow.dao.SqlMapDAO;

/** 
 * ClassName:ProcessDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年8月19日 下午6:10:47 <br/> 
 * @author   m 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
@Repository
public class ProcessDao implements IProcessDao{
	@Autowired
	private SqlMapDAO sqlMapDao;
	/**
	 * 获取新环节模板序列
	 * @return
	 */	
	public String getActivityModelSequence(){
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-process.getActivityModelSequence", null);
	}
	public List getPathDetConditionList(Map paramMap){	
		return sqlMapDao.queryForList("eaap-op2-portal-process.getPathDetConditionList", paramMap);
	}
	
	public List getPathDetConditionValueList(Map map){	
		return sqlMapDao.queryForList("eaap-op2-portal-process.getPathDetConditionValueList", map);
	}
	public String saveFlowMid(Map<String, Object> map){
		return (String)sqlMapDao.insert("eaap-op2-portal-process.saveFlowMid", map);
    }
	public String deleteFlowMid(Map<String, Object> map){
		sqlMapDao.delete("eaap-op2-portal-process.deleteFlowMid", map);
		return "";
		}
	public List getFlowMid(Map<String, Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-process.getFlowMid", map);
    }
	
	public String  getProvisioningProcessState(Map<String, Object> map){
		return (String) sqlMapDao.queryForObject("eaap-op2-portal-process.getFlowStatus", map);
	}
	
	public String  getProvisioningProcessName(Map<String, Object> map){
		return (String) sqlMapDao.queryForObject("eaap-op2-portal-process.getFlowName", map);
	}
	
	public String insertWorkTaskConf(WorkTaskConf workTaskConf){
		return  (String)sqlMapDao.insert("eaap-op2-portal-process.insertWorkTaskConf", workTaskConf);
	}
	public String insertWorkTaskContion(WorkTaskContion workTaskContion){
		return  (String)sqlMapDao.insert("eaap-op2-portal-process.insertWorkTaskContion", workTaskContion);
	}
	public List queryWorkTaskConf(WorkTaskConf workTaskConf){
		return sqlMapDao.queryForList("eaap-op2-portal-process.queryWorkTaskConf", workTaskConf);
	}
	public List queryWorkTaskContion(WorkTaskContion workTaskContion){
		return sqlMapDao.queryForList("eaap-op2-portal-process.queryWorkTaskContion", workTaskContion);
	}
	public List getWorkTaskContionList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-process.getWorkTaskContionList", map);
		
	}
	public String selectService(Map map){
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-process.selectService", map);
		
	}
	public void deleteWorkTaskContion(Map paramMap){
		sqlMapDao.delete("eaap-op2-portal-process.deleteWorkTaskContion", paramMap);
	}
	public Integer checkSysOrg(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-process.checkSysOrg",map);

	}
	
	public void deleteWorkTaskConf(Map paramMap){
		sqlMapDao.delete("eaap-op2-portal-process.deleteWorkTaskConf", paramMap);

	}
	public void updateWorkTaskConf(WorkTaskConf workTaskConf){
		sqlMapDao.update("eaap-op2-portal-process.updateWorkTaskConf", workTaskConf);
	}
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	public List getSystemProduct(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-process.getSystemProduct", map);
		
	}
	public List getSystemStatus(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-process.getSystemStatus", map);
	}

	public List getWorkflowNoticeTaskConfList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-process.getWorkflowNoticeTaskConfList", map);
	}
	public void insertWorkflowNoticeTaskConf(Map map){
		sqlMapDao.insert("eaap-op2-portal-process.insertWorkflowNoticeTaskConf", map);
	}
	public void updateWorkflowNoticeTaskConf(Map map){
		sqlMapDao.update("eaap-op2-portal-process.updateWorkflowNoticeTaskConf", map);
	}
	
	public String getProcessKeyByComponentId(Map map){
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-process.getProcessKeyByComponentId", map); 
	}
	
}
