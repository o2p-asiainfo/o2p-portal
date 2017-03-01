package com.ailk.o2p.portal.dao.messageflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.SerInvokeIns;

public interface IMessageFlowDao {

	public String getMessageFlowSeq();

	public void deleteEndpointAttrValues(Map paramMap);

	public void deleteEndpoints(Map paramMap);

	public void deleteRoutePolicy(Map paramMap);

	public void deleteService_Route_Configs(Map paramMap);

	public void deleteMessage(Map paramMap);

	public String getEndpointSequence();

	public void saveEndpoint(HashMap<String, String> endpointMap);

	public void saveEndpointAttrValue(
			HashMap<String, String> endpointAttrValueMap);

	public String getRulePolicySeq();

	public String getServiceRouteConfigSeq();

	public void addRoutePolicy(HashMap<String, String> paramRoutePolicyMap);

	public void saveService_Route_Config(
			HashMap<String, String> serviceRouteConfigMap);

	public void saveMessageFlow(HashMap<String, String> messageMap);

	public void updateSerInvokeIns(SerInvokeIns serInvokeIns);

	public Integer insertSerInvokeIns(SerInvokeIns serInvokeIns);

	public String getSerTechImplId(Map paramMap);

	public int queryServiceSupplierRegisterCount(Map map);

	public List<Map> queryServiceSupplierRegister(Map map);

	public List<Map> queryCommProtocalListList();

	public int getAllTechImplRecordsById(Map paramMap);

	public List<Map> getTechImplList(Map map);

	public List<HashMap> getTechImplMessageById(Map paramMap);

	public List<HashMap> getAttrSpecList(String serTechImplId, String commCd);

	public List<Map<String, Object>> getSerInvokeInsList(Map<String, String> map);
	
	public List<Map<String, Object>> getSerInvokeInsListForNoticeTask(Map<String, String> map);

}
