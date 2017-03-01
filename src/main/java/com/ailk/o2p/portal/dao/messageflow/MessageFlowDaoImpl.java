package com.ailk.o2p.portal.dao.messageflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.SerInvokeIns;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class MessageFlowDaoImpl implements IMessageFlowDao {
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	@Override
	public String getMessageFlowSeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getMessageFlowSequence", null);
	}

	@Override
	public void deleteEndpointAttrValues(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteEndpointAttrValues", paramMap);	
	}

	@Override
	public void deleteEndpoints(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteEndpoints", paramMap);
	}

	@Override
	public void deleteRoutePolicy(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteRoute_Policy", paramMap);
	}

	@Override
	public void deleteService_Route_Configs(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteService_Route_Configs", paramMap);
	}

	@Override
	public void deleteMessage(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteMessage", paramMap);
	}

	@Override
	public String getEndpointSequence() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getEndpointSequence", null); 
	}

	@Override
	public void saveEndpoint(HashMap<String, String> endpointMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.saveEndpoint", endpointMap);
	}

	@Override
	public void saveEndpointAttrValue(
			HashMap<String, String> endpointAttrValueMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.saveEndpointAttrValue", endpointAttrValueMap);
	}

	@Override
	public String getRulePolicySeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getRulePolicySequence", null); 
	}

	@Override
	public String getServiceRouteConfigSeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getServiceRouteConfigSequence", null); 
	}

	@Override
	public void addRoutePolicy(HashMap<String, String> paramRoutePolicyMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.addRoutePolicy", paramRoutePolicyMap);
	}

	@Override
	public void saveService_Route_Config(
			HashMap<String, String> serviceRouteConfigMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.saveService_Route_Config", serviceRouteConfigMap);
	}

	@Override
	public void saveMessageFlow(HashMap<String, String> messageMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.saveMessageFlow", messageMap);
	}

	@Override
	public void updateSerInvokeIns(SerInvokeIns serInvokeIns) {
		sqlMapDao.update("eaap-op2-messagearrange-messageflow.updateSerInvokeIns", serInvokeIns);
	}

	@Override
	public Integer insertSerInvokeIns(SerInvokeIns serInvokeIns) {
		return (Integer)sqlMapDao.insert("eaap-op2-messagearrange-messageflow.insertSerInvokeIns", serInvokeIns);
	}

	@Override
	public String getSerTechImplId(Map paramMap) {
	
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getSerTechImplId", paramMap); 
	}

	@Override
	public int queryServiceSupplierRegisterCount(Map map) {
		return Integer.valueOf((String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.queryServiceSupplierRegisterCount", map));
	}

	@Override
	public List<Map> queryServiceSupplierRegister(Map map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.queryServiceSupplierRegister", map);
	}

	@Override
	public List<Map> queryCommProtocalListList() {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.queryCommProtocalListList", null);
	}

	@Override
	public int getAllTechImplRecordsById(Map paramMap) {
		int num = 0;
		num = (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getAllTechImplRecordsById", paramMap);
		return num;
	}

	@Override
	public List<Map> getTechImplList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getTechImplList", map);
	}

	@Override
	public List<HashMap> getTechImplMessageById(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getTechImplMessageById", paramMap);
	}

	@Override
	public List<HashMap> getAttrSpecList(String serTechImplId, String commCd) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("serTechImplId", serTechImplId);
		map.put("commProCd", commCd);
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getAttrSpecList", map);
	}

	@Override
	public List<Map<String, Object>> getSerInvokeInsList(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getSerInvokeInsList", map);
	}
	@Override
	public List<Map<String, Object>> getSerInvokeInsListForNoticeTask(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getSerInvokeInsListForNoticeTask", map);
	}
}
