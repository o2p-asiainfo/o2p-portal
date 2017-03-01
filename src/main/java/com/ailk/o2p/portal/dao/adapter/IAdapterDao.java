package com.ailk.o2p.portal.dao.adapter;

import java.util.List;
import java.util.Map;

public interface IAdapterDao {

	public Map queryContractAdapter(Map paramMap);

	public String querySrcById(Map paramMap);

	public List<Map> getEndPointSrcList(Map paramMap);

	public int getCountContractListByMap(Map map);

	public List<Map> getContractListByMap(Map map);

	public int countVarMapType(Map map);

	public List<Map> queryVarMapType(Map map);

	public int getCountValableMapByMap(Map map);

	public List<Map> getValableMapByMap(Map map);

	public Map getContractAdapterById(Map paramMap);

	public List<Map> getConAdaEndListById(Map paramMap);

	public String getTcpCtrFIdByMap(Map<String, String> map);

	public List<Map> getNodeMapperListById(Map paramMap);

	public List<Map> queryNodeByContractId(Map<String, String> map);

	public String getContractNameById(Map paramMap);

	public void updateContractAdapter(Map<String, String> map);

	public Integer queryContractAdapterID();

	public void saveContractAdapter(Map paramMap);

	public String getConAdaEndId();

	public void addConAdaEnd(Map contractEndpoint);

	public void updateContractRecords(Map<String, String> param);

	public void delConAdaEndByMap(Map<String, String> param);

	public int queryActionById(Map paramMap);

	public boolean isExitOper(Map<String, String> param);

	public String getFormatId(Map paramMap);

	public List<Map> getNodeMapper(Map<String, String> param);

	public String getActionValue(Map<String, String> param);

	public boolean isExitSrcTcpCtrFId(Map<String, String> param);

	public void updateConAdaEndpoint(Map<String, String> param);

	public boolean isExitInAdapter(Map<String, String> param);

	public List<Map> getNodeValAdaReq(Map<String, String> param);

	public boolean isExitNodeValReq(Map<String, String> param);

	public void updateNodeValAdapterRes(Map<String, String> param);

	public Integer queryNodeValAdapterResID();

	public void saveNodeValAdapterRes(Map<String, String> param);

	public Integer isVarMapTypeExit(Map<String, String> param);

	public Integer queryVarMapTypeID();

	public void saveVarMapType(Map<String, String> param);

	public String getVarMapTypeName(Map<String, String> param);

	public Integer queryVariableMapID();

	public void saveVariableMap(Map<String, String> param);

	public void saveTransScript(Map<String, String> param);

	public void updateVariableMap(Map<String, String> param);

	public List<Map> getContractFormat(Map<String, String> param);

	public void updateResult(Map<String, String> param);

	public void deleteVarMapType(Map<String, String> param);

	public void delNodeMaper(Map<String, String> param);

	public void delAdapterReq(Map<String, String> param);

	public void delAdapterEndpoint(Map paramMap);

	public Integer insertContractAdapter(Map<String, String> param);

	public void delVariableMap(Map paramMap);

	public void updateTransScript(Map map);

	public Integer isNodeMapDecExit(Map paramMap);

	public Integer queryNodeDecMapID();

	public void saveNodeDecMap(Map paramMap);

	public void updateNodeDescMap(Map paramMap);

	public List<Map> queryValAdapterResByMapingID(Map valAdapterResMap);

	public void updateVarMapType(Map map);

	public List<Map> queryVariableMap(Map paramMap);

	public void delNodeDecMap(Map map);

	public void delNodeValAdapterRea(Map map);

	public List<Map> getRToCLinesDataById(Map<String, String> param);
	
	public Integer getCountVarMay(Map<String, String> param);
	
	public Integer getCountVarMayByCode(Map<String, String> param);
}
