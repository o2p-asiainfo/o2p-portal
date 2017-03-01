package com.ailk.o2p.portal.dao.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class AdapterDaoImpl implements IAdapterDao {

	//private static Log log = LogFactory.getLog(AdapterDaoImpl.class);
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	@Override
	public Map queryContractAdapter(Map paramMap) {
		return (Map) sqlMapDao.queryForObject("eaap-op2-portal-adapter.queryContractAdapter", paramMap);
	}
	@Override
	public String querySrcById(Map paramMap) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.querySrcById", paramMap);
	}
	@Override
	public List<Map> getEndPointSrcList(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getEndPointSrcList", paramMap);
	}
	@Override
	public int getCountContractListByMap(Map map) {
		String array = String.valueOf(map.get("tcpCtrFId"));
		if(null != map.get("tcpCtrFId") && !"".equals(array)){
			array = array.replace("'", "");
			String[] inArray = array.split(",");
			map.put("inArray", inArray);
		}
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getCountContractListByMap", map);
	}
	@Override
	public List<Map> getContractListByMap(Map map) {
		String array = String.valueOf(map.get("tcpCtrFId"));
		if(null != map.get("tcpCtrFId") && !"".equals(array)){
			array = array.replace("'", "");
			String[] inArray = array.split(",");
			map.put("inArray", inArray);
		}
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getContractListByMap", map);
	}
	@Override
	public int countVarMapType(Map map) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.countVarMapType", map);
	}
	@Override
	public List<Map> queryVarMapType(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.queryVarMapType", map);
	}
	@Override
	public int getCountValableMapByMap(Map map) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getCountValableMapByMap", map);
	}
	@Override
	public List<Map> getValableMapByMap(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getValableMapByMap", map);
	}
	@Override
	public Map getContractAdapterById(Map paramMap) {
		return (Map)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getContractAdapterById",paramMap);
	}
	@Override
	public List<Map> getConAdaEndListById(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getConAdaEndListById", paramMap);
	}
	@Override
	public String getTcpCtrFIdByMap(Map<String, String> map) {
		List<Map> paraMap = (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-adapter.getTcpCtrFIdByMap", map);
		if(null != paraMap && paraMap.size()>0){ 
			return paraMap.get(0).get("TCP_CTR_F_ID").toString();
		}
		return null;
	}
	@Override
	public List<Map> getNodeMapperListById(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getNodeMapperListById", paramMap);
	}
	@Override
	public List<Map> queryNodeByContractId(Map<String, String> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.queryNodeByContractId", map);
	}
	@Override
	public String getContractNameById(Map paramMap) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getContractNameById", paramMap);
	}
	@Override
	public void updateContractAdapter(Map<String, String> map) {
		sqlMapDao.update("eaap-op2-portal-adapter.updateContractAdapter", map);
	}
	@Override
	public Integer queryContractAdapterID() {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.queryContractAdapterId", null);
	}
	@Override
	public void saveContractAdapter(Map paramMap) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.insertContractAdapter", paramMap);
	}
	@Override
	public String getConAdaEndId() {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getConAdaEndId", null);
	}
	@Override
	public void addConAdaEnd(Map contractEndpoint) {
		sqlMapDao.insert("eaap-op2-portal-adapter.addConAdaEnd", contractEndpoint);
	}
	@Override
	public void updateContractRecords(Map<String, String> param) {
		sqlMapDao.update("eaap-op2-portal-adapter.updateContractRecords", param);
	}
	@Override
	public void delConAdaEndByMap(Map<String, String> param) {
		sqlMapDao.delete("eaap-op2-portal-adapter.delConAdaEndByMap", param);
	}
	@Override
	public int queryActionById(Map paramMap) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.queryActionById", paramMap);
	}
	@Override
	public boolean isExitOper(Map<String, String> param) {
		int num = (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.isExitOper", param);
		if(num > 0){
			return true;
		}
		return false;
	}
	@Override
	public String getFormatId(Map paramMap) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getFormatId", paramMap);
	}
	@Override
	public List<Map> getNodeMapper(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getNodeMapper", param);
	}
	@Override
	public String getActionValue(Map<String, String> param) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getActionValue", param);
	}
	@Override
	public boolean isExitSrcTcpCtrFId(Map<String, String> param) {
		Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.isExitSrcTcpCtrFId", param);
		if(num > 0){
			return true;
		}
		return false;
	}
	@Override
	public void updateConAdaEndpoint(Map<String, String> param) {
		sqlMapDao.update("eaap-op2-portal-adapter.updateConAdaEndpoint", param);
	}
	@Override
	public boolean isExitInAdapter(Map<String, String> param) {
		String value = (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.isExitInAdapter", param);
		if(null != value && !"".equals(value) && !"0".equals(value)){
			return true;
		}
		return false;
	}
	@Override
	public List<Map> getNodeValAdaReq(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getNodeValAdaReq", param);
	}
	@Override
	public boolean isExitNodeValReq(Map<String, String> param) {
		Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-portal-adapter.isExitNodeValReq", param);
		if(num > 0){
			return true;
		}
		return false;
	}
	@Override
	public void updateNodeValAdapterRes(Map<String, String> param) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.updateNodeValAdapterRes", param);
	}
	@Override
	public Integer queryNodeValAdapterResID() {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.nodeValAdapterResID",null);
	}
	@Override
	public void saveNodeValAdapterRes(Map<String, String> param) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.saveNodeValAdapterRes", param);
	}
	@Override
	public Integer isVarMapTypeExit(Map<String, String> param) {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.isVarMapTypeExit",param);
	}
	@Override
	public Integer queryVarMapTypeID() {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.queryVarMapTypeID",null);
	}
	@Override
	public void saveVarMapType(Map<String, String> param) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.saveVarMapType", param);
	}
	@Override
	public String getVarMapTypeName(Map<String, String> param) {
		return (String)sqlMapDao.queryForObject("eaap-op2-portal-adapter.getVarMapTypeName", param);
	}
	@Override
	public Integer queryVariableMapID() {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.varVariableMapID",null);
	}
	@Override
	public void saveVariableMap(Map<String, String> param) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.saveVariableMap", param);
	}
	@Override
	public void saveTransScript(Map<String, String> param) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.saveTransScript", param);
	}
	@Override
	public void updateVariableMap(Map<String, String> param) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.updateVariableMap", param);
	}
	@Override
	public List<Map> getContractFormat(Map<String, String> param) {
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getContractFormat", param);
	}
	@Override
	public void updateResult(Map<String, String> param) {
		sqlMapDao.update("eaap-op2-portal-adapter.updateResult", param);
	}
	@Override
	public void deleteVarMapType(Map<String, String> param) {
		sqlMapDao.update("eaap-op2-portal-adapter.deleteVarMapType", param);
	}
	@Override
	public void delNodeMaper(Map<String, String> param) {
		this.sqlMapDao.delete("eaap-op2-portal-adapter.delNodeMaper", param);
	}
	@Override
	public void delAdapterReq(Map<String, String> param) {
		this.sqlMapDao.delete("eaap-op2-portal-adapter.delAdapterReq", param);
	}
	@Override
	public void delAdapterEndpoint(Map paramMap) {
		sqlMapDao.delete("eaap-op2-portal-adapter.delAdapterEndpoint", paramMap);
	}
	@Override
	public Integer insertContractAdapter(Map<String, String> param) {
		return (Integer)sqlMapDao.insert("eaap-op2-portal-adapter.insertContractAdapterScript", param) ;
	     
	}
	@Override
	public void delVariableMap(Map paramMap) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.delVariableMap", paramMap);
	}
	@Override
	public void updateTransScript(Map map) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.updateTransScript", map);
	}
	@Override
	public Integer isNodeMapDecExit(Map paramMap) {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.isNodeMapDecExit", paramMap);
	}
	@Override
	public Integer queryNodeDecMapID() {
		return (Integer)this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.queryNodeDecMapID", null);
	}
	@Override
	public void saveNodeDecMap(Map paramMap) {
		this.sqlMapDao.insert("eaap-op2-portal-adapter.saveAdapterConfig", paramMap);
	}
	@Override
	public void updateNodeDescMap(Map paramMap) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.updateNodeDecMap", paramMap);
	}
	@Override
	public List<Map> queryValAdapterResByMapingID(Map valAdapterResMap) {
		return this.sqlMapDao.queryForList("eaap-op2-portal-adapter.queryValAdapterResByMapingID", valAdapterResMap);
	}
	@Override
	public void updateVarMapType(Map map) {
		this.sqlMapDao.update("eaap-op2-portal-adapter.updateVarMapType", map);
	}
	@Override
	public List<Map> queryVariableMap(Map paramMap) {
		return this.sqlMapDao.queryForList("eaap-op2-portal-adapter.queryVariableMap", paramMap);
	}
	@Override
	public void delNodeDecMap(Map map) {
		this.sqlMapDao.delete("eaap-op2-portal-adapter.delNodeDecMap", map);
	}
	@Override
	public void delNodeValAdapterRea(Map map) {
		this.sqlMapDao.delete("eaap-op2-portal-adapter.delNodeValAdapterRea", map);
	}
	@Override
	public List<Map> getRToCLinesDataById(Map<String, String> param){
		return sqlMapDao.queryForList("eaap-op2-portal-adapter.getRToCLinesDataById", param);
	}
	@Override
	public Integer getCountVarMay(Map<String, String> param) {
		return (Integer) this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.getCountVarMay", param);
	}
	@Override
	public Integer getCountVarMayByCode(Map<String, String> param) {
		return (Integer) this.sqlMapDao.queryForObject("eaap-op2-portal-adapter.getCountVarMayByCode", param);
	}
}
