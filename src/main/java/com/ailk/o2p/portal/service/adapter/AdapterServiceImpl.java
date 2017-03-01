package com.ailk.o2p.portal.service.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.o2p.portal.controller.adapter.NodeDescMapper;
import com.ailk.o2p.portal.dao.adapter.IAdapterDao;
import com.asiainfo.foundation.log.Logger;

@Service
public class AdapterServiceImpl implements IAdapterService {

	//private Logger log = Logger.getLog(this.getClass());
	@Autowired
	private IAdapterDao adapterDao;
	@Override
	public Map queryContractAdapter(int transformerRuleId) {
		Map paramMap=new HashMap(); 
		paramMap.put("transformerRuleId", transformerRuleId);
		return adapterDao.queryContractAdapter(paramMap);
	}
	@Override
	public String getSelectedLeftFormat(String pageContractAdapterId) {
		String result = "";
		List<String> resultList = new ArrayList<String>();
		Map paramMap=new HashMap(); 
		paramMap.put("contractAdapterId", paramMap);
		String srcAdapterId = adapterDao.querySrcById(paramMap);
		List<Map> listEndpointSrcId = adapterDao.getEndPointSrcList(paramMap);
		if(null != srcAdapterId && !"".equals(srcAdapterId)){
			resultList.add(srcAdapterId);
		}
		if(null != listEndpointSrcId && listEndpointSrcId.size() > 0){
			for(Map value : listEndpointSrcId){
				String id = String.valueOf(value.get("CONTRACT_FORMATE_ID"));
				resultList.add(id);
			}
		}
		if(resultList.size()  > 0){
			for(String value : resultList){
				if("".equals(result)){
					result = value;
				}else{
					result = result+","+value;
				}
			}
		}
		return result;
	}
	@Override
	public int getCountContractListByMap(Map map) {
		return adapterDao.getCountContractListByMap(map);
	}
	@Override
	public List<Map> getContractListByMap(Map map) {
		return adapterDao.getContractListByMap(map);
	}
	@Override
	public int countVarMapType(Map map) {
		return adapterDao.countVarMapType(map);
	}
	@Override
	public List<Map> queryVarMapType(Map map) {
		return adapterDao.queryVarMapType(map);
	}
	@Override
	public int getCountValableMapByMap(Map map) {
		return adapterDao.getCountValableMapByMap(map);
	}
	@Override
	public List<Map> getValableMapByMap(Map map) {
		return adapterDao.getValableMapByMap(map);
	}
	@Override
	public Map getContractAdapterById(String pageContractAdapterId) {
		Map paramMap=new HashMap(); 
		paramMap.put("pageContractAdapterId", pageContractAdapterId);
		return adapterDao.getContractAdapterById(paramMap);
	}
	@Override
	public List<Map> getConAdaEndListById(String pageContractAdapterId) {
		Map paramMap=new HashMap(); 
		paramMap.put("pageContractAdapterId", pageContractAdapterId);
		return adapterDao.getConAdaEndListById(paramMap);
	}
	@Override
	public String getTcpCtrFIdByMap(Map<String, String> map) {
		return adapterDao.getTcpCtrFIdByMap(map);
	}
	@Override
	public List<Map> getNodeMapperListById(String pageContractAdapterId) {
		Map paramMap=new HashMap(); 
		paramMap.put("pageContractAdapterId", pageContractAdapterId);
		return adapterDao.getNodeMapperListById(paramMap);
	}
	@Override
	public List<Map> queryNodeByContractId(Map<String, String> map) {
		return adapterDao.queryNodeByContractId(map);
	}
	@Override
	public String getContractNameById(String contractFormateId) {
		Map paramMap=new HashMap(); 
		paramMap.put("contractFormateId", contractFormateId);
		return adapterDao.getContractNameById(paramMap);
	}
	@Override
	public void updateContractAdapter(Map<String, String> map) {
		adapterDao.updateContractAdapter(map);
	}
	@Override
	public Integer addContractAdapter(Map paramMap) {
		Integer id = adapterDao.queryContractAdapterID();
		paramMap.put("contract_adapter_id", id);
		adapterDao.saveContractAdapter(paramMap);
		return id;
	}
	@Override
	public String getConAdaEndId() {
		return adapterDao.getConAdaEndId();
	}
	@Override
	public void addConAdaEnd(Map contractEndpoint) {
		adapterDao.addConAdaEnd(contractEndpoint);
	}
	@Override
	public void updateContractRecords(Map<String, String> param) {
		adapterDao.updateContractRecords(param);
	}
	@Override
	public void delConAdaEndByMap(Map<String, String> param) {
		adapterDao.delConAdaEndByMap(param);
	}
	@Override
	public String isExitOperator(String operator, String pageContractAdapterId) {
		String result = "";
		if("XX".equals(operator)){
			Map paramMap=new HashMap(); 
			paramMap.put("contractAdapterId", paramMap);
			String value = adapterDao.querySrcById(paramMap);
			if(null != value && !"".equals(value) && !"0".equals(value)){
				result = "EXIT";
			}else{
				result = "NO_DATA";
			}
		}else if("R".equals(operator)){
			Map paramMap=new HashMap(); 
			paramMap.put("pageContractAdapterId", pageContractAdapterId);
			int value = adapterDao.queryActionById(paramMap);
			if(value >0){
				result = "EXIT";
			}else{
				result = "NO_DATA";
			}
		}else{
			result = "NO_DATA";
		}
		return result;
	}
	@Override
	public String isExitLine(Map<String, String> param) {
		String result = "";//返回结果
		String operator = param.get("operator");
		String contractAdapterId = param.get("contractAdapterId");
		String srcNodeDescId = param.get("srcNodeDescId");
//		String tarNodeDescId = param.get("tarNodeDescId");
		boolean isExitOper = adapterDao.isExitOper(param);
		if(isExitOper){
			result = "EXIT";
		}else{
			if("M".equals(operator) || "U".equals(operator) || "Z".equals(operator) || "H".equals(operator)){
				boolean isInFormatList = isInFormatList(srcNodeDescId,contractAdapterId);
				if(isInFormatList){//在列表中,可以往下操作
					result  =  getFinalResult(param);
				}else{//不在列表中,
					result = "NOT_IN_LIST";
				}
			}else{
				result  =  getFinalResult(param);
			}
		}
		return result;
	}
	/**
	 * 判断是否在当前的协议列表中
	 * @param srcNodeDescId
	 * @param contractAdapterId
	 * @return
	 */
	private boolean isInFormatList(String srcNodeDescId,
			String contractAdapterId) {
		List<String> resultList = new ArrayList<String>();
		Map paramMap=new HashMap(); 
		paramMap.put("contractAdapterId", paramMap);
		String srcAdapterId = adapterDao.querySrcById(paramMap);
		List<Map> listEndpointSrcId = adapterDao.getEndPointSrcList(paramMap);
		if(null != srcAdapterId && !"".equals(srcAdapterId)){
			resultList.add(srcAdapterId);
		}
		if(null != listEndpointSrcId && listEndpointSrcId.size() > 0){
			for(Map value : listEndpointSrcId){
				String id = String.valueOf(value.get("CONTRACT_FORMATE_ID"));
				resultList.add(id);
			}
		}
		List<String> finalResult = new ArrayList<String>();
		for(String finalValue: resultList){
			finalResult.add(finalValue);
			Map map = new HashMap();
			map.put("tcpCtrFId", finalValue);
			finalResult.add(adapterDao.getTcpCtrFIdByMap(map));
		}
		Map paramFormatMap=new HashMap(); 
		paramFormatMap.put("srcNodeDescId", srcNodeDescId);
		String srcFomatId = adapterDao.getFormatId(paramFormatMap);
		if(finalResult.contains(srcFomatId)){
			return true;
		}
		return false;
	}
	/**
	 * 得到最终的判断结果
	 * @param param
	 * @return
	 */
	private String getFinalResult(Map<String, String> param) {
		String result = "";
		String contractAdapterId = param.get("contractAdapterId");
		String tarNodeDescId = param.get("tarNodeDescId");
		String srcNodeDescId = param.get("srcNodeDescId");
		String operator = param.get("operator");
		Map<String,String> map = new HashMap<String,String>();
		map.put("contractAdapterId", contractAdapterId);
		map.put("tarNodeDescId", tarNodeDescId);
		List<Map> list =null;
		if("H".equals(operator)){
			map.put("srcNodeDescId", srcNodeDescId);
			list = adapterDao.getNodeMapper(map);
		}else{
			list = adapterDao.getNodeMapper(map);
		}
		if(null != list && list.size()  > 0){//说明存在
			NodeDescMapper mapper = new NodeDescMapper();
			mapper.setNodeDescMapperId(String.valueOf(list.get(0).get("NODE_DESC_MAPER_ID")));
			mapper.setTarNodeDescId(String.valueOf(list.get(0).get("TAR_NODE_DESC_ID")));
			mapper.setSrcNodeDescId(String.valueOf(list.get(0).get("SRC_NODE_DESC_ID")));
			mapper.setActionTypeCd(String.valueOf(list.get(0).get("ACTION_TYPE_CD")));
			mapper.setContractAdapterId(String.valueOf(list.get(0).get("CONTRACT_ADAPTER_ID")));
			if("M".equals(operator) ||  "U".equals(operator) ||  "Z".equals(operator) ||  "H".equals(operator)){
				String innerOper = mapper.getActionTypeCd();
				if("R".equals(innerOper) || "A".equals(innerOper)){
					result = "USED";//已经被使用
				}else{
					if(srcNodeDescId.equals(mapper.getSrcNodeDescId())){
						result = "UPDATE";//更新操作
					}else{
						result = "USED";//已经被使用
					}
				}
			}else{
				String innerOper = mapper.getActionTypeCd();
				if("M".equals(innerOper) || "U".equals(innerOper) || "Z".equals(innerOper) || "H".equals(innerOper)){
					result = "USED";//已经被使用
				}else{
					result = "UPDATE";//更新操作
				}
			}
		}else{//说明不存在
			result = "NO_DATA";
		}
		return result;
	}
	@Override
	public String getActionValue(Map<String, String> param) {
		return adapterDao.getActionValue(param);
	}
	@Override
	public String changeToAction(Map<String, String> param) {
		String result = "";
		String contractAdapterId = param.get("contractAdapterId");
		String operator = param.get("operator");
		String srcTcpCtrFId = param.get("srcTcpCtrFId");
		if("XX".equals(operator)){
			boolean isExit = adapterDao.isExitSrcTcpCtrFId(param);//判断在协议端点表里是否存在
			if(isExit){
				Map<String,String> map = new HashMap<String,String>();
				map.put("contractAdapterId", contractAdapterId);
				map.put("endpointId", param.get("endpointId"));
				map.put("tcpCrtFId", srcTcpCtrFId);
				adapterDao.delConAdaEndByMap(map);//删除协议端点表里的记录
				adapterDao.updateContractAdapter(param);//同时修改协议转化表的源协议格式ID
				result = "success";
			}else{
				result = "DATA_EXCEPTION";
			}
		}else if("R".equals(operator)){
			boolean isExit = adapterDao.isExitSrcTcpCtrFId(param);//判断在协议端点表里是否存在
			if(isExit){
				param.put("actionType", "R");
				adapterDao.updateConAdaEndpoint(param);//存在的话，则改变它的动作为R。
				result = "success";
			}else{
				result = "DATA_EXCEPTION";
			}
		}else{
			boolean isExit = adapterDao.isExitInAdapter(param);//判断在协议转化表里是否存在
			if(isExit){
				Map<String,String> mapParam = new HashMap<String,String>();
				mapParam.put("contractAdapterId", contractAdapterId);
				mapParam.put("tcpCrtFId", srcTcpCtrFId);
				adapterDao.updateContractRecords(mapParam);//如果存在，则将协议转化表的源协议格式ID清空
				String id = adapterDao.getConAdaEndId();
				Map<String,String> bean = new HashMap<String,String>();
				bean.put("conAdaEndId", id);
				bean.put("contractAdapterId", contractAdapterId);
				bean.put("endPointId", param.get("endpointId"));
				bean.put("srcTcpCtrFId", srcTcpCtrFId);
				bean.put("actionType", "T");
				adapterDao.addConAdaEnd(bean);//同时协议端点表里添加一条记录
				result = "success";
			}else{//如果不存在，
				boolean isExitOther = adapterDao.isExitSrcTcpCtrFId(param);//则判断协议端点表里是否存在，
				if(isExitOther){
					param.put("actionType", "T");
					adapterDao.updateConAdaEndpoint(param);//如果存在，则改变它的动作为T。
					result = "success";
				}else{
					result = "DATA_EXCEPTION";
				}
			}
		}
		return result;
	}
	@Override
	public List<Map> getNodeValAdaReq(Map<String, String> param) {
		return adapterDao.getNodeValAdaReq(param);
	}
	@Override
	public String addNodeValAdapterRes(Map<String, String> param) {
		String result = "";
		boolean isExit = adapterDao.isExitNodeValReq(param);
		if(isExit){//存在,说明是修改动作
			adapterDao.updateNodeValAdapterRes(param);
			result = "success";
		}else{//添加动作
			String id = adapterDao.queryNodeValAdapterResID()+"";
			param.put("nodeId", id);
			adapterDao.saveNodeValAdapterRes(param);
			result = "success";
		}
		return result;
	}
	@Override
	public int isVarMapTypeExit(Map<String, String> param) {
		return adapterDao.isVarMapTypeExit(param);
		 
	}
	@Override
	public Integer saveVarMapType(Map<String, String> param) {
		Integer id = adapterDao.queryVarMapTypeID();
		param.put("varMapTypeID", id+"");
		adapterDao.saveVarMapType(param);
		return id;
	}
	@Override
	public String getVarMapTypeName(Map<String, String> param) {
		return adapterDao.getVarMapTypeName(param);
	}
	@Override
	public Integer saveVariableMap(Map<String, String> param) {
		Integer id = adapterDao.queryVariableMapID();
		param.put("varMapingID", id+"");
		adapterDao.saveVariableMap(param);
		adapterDao.saveTransScript(param);
		return id;
	}
	@Override
	public void updateVariableMap(Map<String, String> param) {
		adapterDao.updateVariableMap(param);
	}
	@Override
	public List<Map> getContractFormat(Map<String, String> param) {
		return adapterDao.getContractFormat(param);
	}
	@Override
	public void updateResult(Map<String, String> param) {
		adapterDao.updateResult(param);
	}
	@Override
	public void deleteVarMapType(Map<String, String> param) {
		adapterDao.deleteVarMapType(param);
	}
	@Override
	public void delNodeMaper(Map<String, String> param) {
		adapterDao.delNodeMaper(param);
	}
	@Override
	public void delAdapterReq(Map<String, String> param) {
		adapterDao.delAdapterReq(param);
	}
	@Override
	public void delAdapterEndpoint(String pageContractAdapterId) {
		Map paramMap=new HashMap(); 
		paramMap.put("pageContractAdapterId", pageContractAdapterId);
		adapterDao.delAdapterEndpoint(paramMap);
	}
	@Override
	public Integer insertContractAdapter(Map<String, String> param) {
		return adapterDao.insertContractAdapter(param);
	}
	@Override
	public void delVariableMap(int pageVarMappingId) {
		Map paramMap=new HashMap(); 
		paramMap.put("varMapingID", pageVarMappingId);
		adapterDao.delVariableMap(paramMap);
	}
	@Override
	public void updateTransScript(Map map) {
		adapterDao.updateTransScript(map);
	}
	@Override
	public boolean isNodeMapDecExit(Map paramMap) {
		Integer num = adapterDao.isNodeMapDecExit(paramMap);
		return num==0?true:false;
	}
	@Override
	public Integer addNodeDescMap(Map paramMap) {
		Integer id = adapterDao.queryNodeDecMapID();
		paramMap.put("node_desc_id", id);
		adapterDao.saveNodeDecMap(paramMap);
		return id;
	}
	@Override
	public void updateNodeDescMap(Map paramMap) {
		adapterDao.updateNodeDescMap(paramMap);
	}
	@Override
	public void delNodeDescMap(Map map) {
		map.put("state", "D");
		if(!"".equals(map.get("mapingId")) && null!= map.get("mapingId")){
			Map valAdapterResMap=new HashMap(); 
			valAdapterResMap.put("nodeValAdapterReqId", Integer.parseInt((String) map.get("mapingId")));
			List<Map> l = adapterDao.queryValAdapterResByMapingID(valAdapterResMap);
			Map ml = l.get(0);
			if("3".equals(ml.get("NODE_VALUE_SOURCE_CD"))){
				map.put("consMapCD", ml.get("VALUE_EXPRESS"));
				adapterDao.updateVarMapType(map);
				Map paramMap=new HashMap(); 
				paramMap.put("consMapCD", (String) map.get("consMapCD"));
				List<Map> list = adapterDao.queryVariableMap(paramMap);
				for(Map m:list){
					map.put("varMapingId", m.get("VAR_MAPING_ID"));
					adapterDao.updateTransScript(map);
					adapterDao.updateVariableMap(map);
				}
//			}else if("4".equals(ml.get("NODE_VALUE_SOURCE_CD"))){
				
			}
			adapterDao.updateNodeValAdapterRes(map);
		}
		adapterDao.delNodeDecMap(map);
	}
	@Override
	public void delNodeValAdapterRea(Map map) {
		adapterDao.delNodeValAdapterRea(map);
	}
	
	@Override
	public List<Map> getRToCLinesDataById(Map<String, String> param) {
		return adapterDao.getRToCLinesDataById(param);
	}
	
	@Override
	public Integer getCountVarMay(Map<String, String> param){
		return adapterDao.getCountVarMay(param);
	}
	
	@Override
	public Integer getCountVarMayByCode(Map<String, String> param){
		return adapterDao.getCountVarMayByCode(param); 
	}
}
