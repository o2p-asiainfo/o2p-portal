package com.ailk.o2p.portal.service.adapter;

import java.util.List;
import java.util.Map;

public interface IAdapterService {

	public Map queryContractAdapter(int transformerRuleId);

	public String getSelectedLeftFormat(String pageContractAdapterId);

	public int getCountContractListByMap(Map map);

	public List<Map> getContractListByMap(Map map);

	public int countVarMapType(Map map);

	public List<Map> queryVarMapType(Map map);

	public int getCountValableMapByMap(Map map);

	public List<Map> getValableMapByMap(Map map);

	public Map getContractAdapterById(String pageContractAdapterId);

	public List<Map> getConAdaEndListById(String pageContractAdapterId);

	public String getTcpCtrFIdByMap(Map<String, String> map);

	public List<Map> getNodeMapperListById(String pageContractAdapterId);

	public List<Map> queryNodeByContractId(Map<String, String> map);

	public String getContractNameById(String contractFormateId);

	public void updateContractAdapter(Map<String, String> map);

	public Integer addContractAdapter(Map paramMap);

	public String getConAdaEndId();

	public void addConAdaEnd(Map contractEndpoint);

	public void updateContractRecords(Map<String, String> param);

	public void delConAdaEndByMap(Map<String, String> param);

	public String isExitOperator(String operator, String pageContractAdapterId);

	public String isExitLine(Map<String, String> param);

	public String getActionValue(Map<String, String> param);

	public String changeToAction(Map<String, String> param);

	public List<Map> getNodeValAdaReq(Map<String, String> param);

	public String addNodeValAdapterRes(Map<String, String> param);

	public int isVarMapTypeExit(Map<String, String> param);

	public Integer saveVarMapType(Map<String, String> param);

	public String getVarMapTypeName(Map<String, String> param);

	public Integer saveVariableMap(Map<String, String> param);

	public void updateVariableMap(Map<String, String> param);

	public List<Map> getContractFormat(Map<String, String> param);

	public void updateResult(Map<String, String> param);

	public void deleteVarMapType(Map<String, String> param);

	public void delNodeMaper(Map<String, String> param);

	public void delAdapterReq(Map<String, String> param);

	public void delAdapterEndpoint(String pageContractAdapterId);

	public Integer insertContractAdapter(Map<String, String> param);

	public void delVariableMap(int pageVarMappingId);

	public void updateTransScript(Map map);

	public boolean isNodeMapDecExit(Map paramMap);

	public Integer addNodeDescMap(Map paramMap);

	public void updateNodeDescMap(Map paramMap);

	public void delNodeDescMap(Map map);

	public void delNodeValAdapterRea(Map map);

	public List<Map> getRToCLinesDataById(Map<String, String> param);
	
	public Integer getCountVarMay(Map<String, String> param);
	
	public Integer getCountVarMayByCode(Map<String, String> param);
}
