package com.ailk.o2p.portal.service.messageflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface IMessageFlowService {

	public Map<String,String> saveOrUpdateMessageFlow(JSONObject finalJson);

	public Map<String,String> saveOrUpdateSerInvokeIns(JSONObject finalJson);

	public int queryServiceSupplierRegisterCount(Map map);

	public List<Map> queryServiceSupplierRegister(Map map);

	public List<Map> queryCommProtocalListList();

	public int getAllTechImplRecordsById(String serTechImplId);

	public List<Map> getTechImplList(Map map);

	public List<HashMap> getTechImplMessageById(String serTechImplId);

	public List<HashMap> getAttrSpecList(String serTechImplId, String commCd);

	public Map<String, String> getSerInvokeInsMessage(JSONObject finalJson);
	
	public Map<String, String> getSerInvokeInsMessageForNoticeTask(JSONObject finalJson);

}
