package com.ailk.o2p.portal.service.messageflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.SerInvokeIns;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.o2p.portal.controller.reg.RegController;
import com.ailk.o2p.portal.dao.messageflow.IMessageFlowDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

@Service
public class MessageFlowServiceImpl implements IMessageFlowService {
	
	private static Logger log = Logger.getLog(MessageFlowServiceImpl.class);
	@Autowired
	private IMessageFlowDao messageFlowDao;

	@Override
	public Map<String,String> saveOrUpdateMessageFlow(JSONObject finalJson) {
		Map<String,String> returnResult = new HashMap<String,String>();
		String result = "success";//返回结果
		try{
			String messageFlowId = finalJson.getString("messageFlowId");
			String transformerOne = finalJson.getString("transformerOne");
			String serviceId = finalJson.getString("serviceId");
			String componentId = finalJson.getString("componentId");
			String transformerTwo = finalJson.getString("transformerTwo");
			String type = finalJson.getString("type");
			String name = finalJson.getString("name");
			if(null != messageFlowId && !"".equals(messageFlowId)){//说明是修改
				//先清空之前的数据
				Map paramMap=new HashMap();
				paramMap.put("messageFlowId", messageFlowId);
				messageFlowDao.deleteEndpointAttrValues(paramMap);
				messageFlowDao.deleteEndpoints(paramMap);
				messageFlowDao.deleteRoutePolicy(paramMap);
				messageFlowDao.deleteService_Route_Configs(paramMap);
				messageFlowDao.deleteMessage(paramMap);
			}else{//说明是添加
				messageFlowId = messageFlowDao.getMessageFlowSeq();
			}
			//添加开始端点
			String beginEndpointId = addBeginEndpoint(messageFlowId,type);
			//添加转换端点
			String transformerOneId  = "";
			if(!StringUtils.isEmpty(transformerOne)){
				transformerOneId = addTransformerEndpoint(messageFlowId,transformerOne,"1",type);
			}
			//添加调用端点
			Map paramMap=new HashMap();
			paramMap.put("serviceId", serviceId);
			paramMap.put("componentId", componentId);
			String call = messageFlowDao.getSerTechImplId(paramMap);
			String callEndpointId = addCallEndpoint(messageFlowId,call,type);
			//添加转换端点
			String transformerTwoId  = "";
			if(!StringUtils.isEmpty(transformerTwo)){ 
				transformerTwoId = addTransformerEndpoint(messageFlowId,transformerTwo,"2",type);
			}
			//添加结束端点
			String endEndpointId = addEndEndpoint(messageFlowId,type);
			if(!"".equals(transformerOneId) && !"".equals(transformerTwoId)){//5个端点全部存在
				String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+transformerOneId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,beginEndpointId,transformerOneId,mapCodeVml_1,type);
				String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+transformerOneId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 307,120.5,444,117.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,transformerOneId,callEndpointId,mapCodeVml_2,type);
				String mapCodeVml_3 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+transformerTwoId+"\" title=\"line_1\" points=\" 479,117.5,661,119.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,callEndpointId,transformerTwoId,mapCodeVml_3,type);
				String mapCodeVml_4 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+transformerTwoId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 696,119.5,889,128.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,transformerTwoId,endEndpointId,mapCodeVml_4,type);
			}else if("".equals(transformerOneId) && !"".equals(transformerTwoId)){
				String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,beginEndpointId,callEndpointId,mapCodeVml_1,type);
				String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+transformerTwoId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,callEndpointId,transformerTwoId,mapCodeVml_2,type);
				String mapCodeVml_3 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+transformerTwoId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,transformerTwoId,endEndpointId,mapCodeVml_3,type);
			}else if(!"".equals(transformerOneId) && "".equals(transformerTwoId)){
				String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+transformerOneId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,beginEndpointId,transformerOneId,mapCodeVml_1,type);
				String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+transformerOneId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,transformerOneId,callEndpointId,mapCodeVml_2,type);
				String mapCodeVml_3 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,callEndpointId,endEndpointId,mapCodeVml_3,type);
			}else if("".equals(transformerOneId) && "".equals(transformerTwoId)){
				String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,beginEndpointId,callEndpointId,mapCodeVml_1,type);
				String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 119,119.5,272,120.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
				saveRouteConfig(messageFlowId,callEndpointId,endEndpointId,mapCodeVml_2,type);
			}
			createMessageFlow(messageFlowId,beginEndpointId,name);
			returnResult.put("id", messageFlowId);
			returnResult.put("call", call);
		}catch(Exception e){
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"saveOrUpdateMessageFlow:" + errorMsg,e));
			result = "fail";
		}
		returnResult.put("result", result);
		return returnResult;
	}

	public void createMessageFlow(String messageFlowId,String fromEndpointId,String name){
		//添加消息流数据
		 HashMap<String,String> messageMap=new HashMap<String,String>();
		 messageMap.put("message_Flow_Id",messageFlowId);
		 messageMap.put("message_Flow_Name",name);
		 messageMap.put("first_Endpoint_Id",fromEndpointId);
		 messageMap.put("lastest_Time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 messageMap.put("state","A");
		 messageMap.put("descriptor", "");
		 messageFlowDao.saveMessageFlow(messageMap);
	}
	private void saveRouteConfig(String messageFlowId,String from,String to,String mapCode,String type){
		String routePolicyId = messageFlowDao.getRulePolicySeq();
		String routeId = messageFlowDao.getServiceRouteConfigSeq();
		 //添加路由规则数据
		 HashMap<String,String>  paramRoutePolicyMap =  new HashMap<String,String>();
		 paramRoutePolicyMap.put("route_Policy_Id", routePolicyId);
		 paramRoutePolicyMap.put("rule_Strategy_Id", "1");
		 paramRoutePolicyMap.put("route_Cond_Id", "");
		 messageFlowDao.addRoutePolicy(paramRoutePolicyMap);
		 //添加路由配置数据
		 HashMap<String,String> serviceRouteConfigMap=new HashMap<String,String>();
		 serviceRouteConfigMap.put("route_Id",routeId);
		 serviceRouteConfigMap.put("route_Policy_Id",routePolicyId);
		 serviceRouteConfigMap.put("message_Flow_Id",messageFlowId);
		 serviceRouteConfigMap.put("from_Endpoint_Id", from);
		 serviceRouteConfigMap.put("to_Endpoint_Id",to);
		 serviceRouteConfigMap.put("syn_Asyn","SYN");
		 serviceRouteConfigMap.put("state","A");
		 serviceRouteConfigMap.put("lastest_Date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 if("old".equals(type)){
			 serviceRouteConfigMap.put("map_Code", mapCode);
		 }else{
			 serviceRouteConfigMap.put("map_Code", "");
		 }
		 messageFlowDao.saveService_Route_Config(serviceRouteConfigMap);
	}
	private String addCallEndpoint(String messageFlowId,String call,String type) {
		String endpointId = messageFlowDao.getEndpointSequence();
		String mapCode = "";
		if("old".equals(type)){
			mapCode =  "<span id=\"E"+endpointId+"\" style=\"background: url(&quot;../resource/common/images/messageArray/endpointSpec/webservice.png&quot;) no-repeat center; left: 444px; top: 100px; width: 35px; height: 35px; position: absolute; z-index: 1; cursor: hand;\"/><div id=\"T"+endpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Call</div>";
		}else{
			mapCode = "429px;247px";
		}
		createEndpoint(endpointId,messageFlowId,"6","Call",mapCode);
		createEndpointAttrValue(endpointId,call,"22");
		return endpointId;
	}

	private String addTransformerEndpoint(String messageFlowId,String transformer,String order,String type) {
		String endpointId = messageFlowDao.getEndpointSequence();
		String mapCode = "";
		if("1".equals(order)){
			if("old".equals(type)){
				mapCode =  "<span id=\"E"+endpointId+"\" style=\"background: url(&quot;../resource/common/images/messageArray/endpointSpec/transform.png&quot;) no-repeat center; left: 272px; top: 103px; width: 35px; height: 35px; position: absolute; z-index: 1; cursor: hand;\"/><div id=\"T"+endpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Transformer</div>";
			}else{
				mapCode ="173px;248px";
			}
		}else{
			if("old".equals(type)){
				mapCode =  "<span id=\"E"+endpointId+"\" style=\"background: url(&quot;../resource/common/images/messageArray/endpointSpec/transform.png&quot;) no-repeat center; left: 661px; top: 102px; width: 35px; height: 35px; position: absolute; z-index: 1; cursor: hand;\"/><div id=\"T"+endpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Transformer</div>";
			}else{
				mapCode = "657px;252px";
			}
		}
		createEndpoint(endpointId,messageFlowId,"35","Transformer",mapCode);
		createEndpointAttrValue(endpointId,transformer,"28");
		return endpointId;
	}

	private String addEndEndpoint(String messageFlowId,String type) {
		String endpointId = messageFlowDao.getEndpointSequence();
		String mapCode = "";
		if("old".equals(type)){
			mapCode =  "<span id=\"E"+endpointId+"\" style=\"background: url(&quot;../resource/common/images/messageArray/endpointSpec/end.png&quot;) no-repeat center; left: 889px; top: 111px; width: 35px; height: 35px; position: absolute; z-index: 1; cursor: hand;\"/><div id=\"T"+endpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">End</div>";
		}else{
			mapCode = "912px;250px";
		}
		return createEndpoint(endpointId,messageFlowId,"11","End",mapCode);
	}

	private String addBeginEndpoint(String messageFlowId,String type) {
		String endpointId = messageFlowDao.getEndpointSequence();
		String mapCode = "";
		if("old".equals(type)){
			mapCode =  "<span id=\"E"+endpointId+"\" style=\"background: url(&quot;../resource/common/images/messageArray/endpointSpec/start.png&quot;) no-repeat center; left: 84px; top: 102px; width: 35px; height: 35px; position: absolute; z-index: 1; cursor: hand;\"/><div id=\"T"+endpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Begin</div>";
		}else{
			mapCode = "7px;248px";
		}
		return createEndpoint(endpointId,messageFlowId,"7","Begin",mapCode);
	}
	private String createEndpoint(String endpointId,String messageFlowId,String endpointSpecId,String endpointName,String mapCode){
		HashMap<String,String> endpointMap=new HashMap<String,String>();
		endpointMap.put("endpoint_Id", endpointId);
		endpointMap.put("message_Flow_Id", messageFlowId);
		endpointMap.put("endpoint_Spec_Id", endpointSpecId);
		endpointMap.put("in_Data_Type_Id", "1");
		endpointMap.put("out_Data_Type_Id", "1");
		endpointMap.put("endpoint_Name", endpointName);
		endpointMap.put("endpoint_Code", endpointId);
		endpointMap.put("enable_In_Trace", "Y");
		endpointMap.put("enable_Out_Trace", "Y");
		endpointMap.put("enable_In_Log", "Y");
		endpointMap.put("enable_Out_Log", "Y");
		endpointMap.put("state", "A");
		endpointMap.put("lastest_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		endpointMap.put("endpoint_Desc", "");
		endpointMap.put("map_Code", mapCode);
		messageFlowDao.saveEndpoint(endpointMap);
		return endpointId;
	}
	private void createEndpointAttrValue(String endpointId,String attrValue,String endpointSpecAttrId){
		HashMap<String,String> endpointAttrValueMap=new HashMap<String,String>();
		endpointAttrValueMap.put("endpoint_Id", endpointId);
		endpointAttrValueMap.put("attr_Value", attrValue);
		endpointAttrValueMap.put("endpoint_Spec_Attr_Id", endpointSpecAttrId);
		endpointAttrValueMap.put("long_Attr_Value", "");
		messageFlowDao.saveEndpointAttrValue(endpointAttrValueMap);
	}

	@Override
	public Map<String,String> saveOrUpdateSerInvokeIns(JSONObject finalJson) {
		Map<String,String> returnResult = new HashMap<String,String>();
		String result = "success";//返回结果
		try{
			String messageFlowId = finalJson.getString("messageFlowId");
			String serInvokeInsId = finalJson.getString("serInvokeInsId");
			String componentId = finalJson.getString("componentId");
			String serviceId = finalJson.getString("serviceId");
			String name = finalJson.getString("name");
			SerInvokeIns serInvokeIns = new SerInvokeIns();
			serInvokeIns.setComponentId(componentId);
			serInvokeIns.setServiceId(Integer.valueOf(serviceId));
			serInvokeIns.setSerInvokeInsName(name);
			serInvokeIns.setState("A");
			serInvokeIns.setMessageFlowId(Integer.valueOf(messageFlowId));
			if(null !=  serInvokeInsId && !"".equals(serInvokeInsId)){//修改
				serInvokeIns.setSerInvokeInsId(Integer.valueOf(serInvokeInsId));
				messageFlowDao.updateSerInvokeIns(serInvokeIns);
			}else{
				serInvokeInsId = messageFlowDao.insertSerInvokeIns(serInvokeIns) +"";
			}
			returnResult.put("id", serInvokeInsId);
		}catch(Exception e){
			result = "fail";
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"saveOrUpdateSerInvokeIns:" + errorMsg,e));
		}
		returnResult.put("result", result);
		return returnResult;
	}

	@Override
	public int queryServiceSupplierRegisterCount(Map map) {
		return messageFlowDao.queryServiceSupplierRegisterCount(map);
	}

	@Override
	public List<Map> queryServiceSupplierRegister(Map map) {
		return messageFlowDao.queryServiceSupplierRegister(map);
	}

	@Override
	public List<Map> queryCommProtocalListList() {
		return messageFlowDao.queryCommProtocalListList();
	}

	@Override
	public int getAllTechImplRecordsById(String serTechImplId) {
		Map paramMap=new HashMap(); 
		paramMap.put("serTechImplId", serTechImplId);
		return messageFlowDao.getAllTechImplRecordsById(paramMap);
	}

	@Override
	public List<Map> getTechImplList(Map map) {
		return messageFlowDao.getTechImplList(map);
	}

	@Override
	public List<HashMap> getTechImplMessageById(String serTechImplId) {
		Map paramMap=new HashMap(); 
		paramMap.put("serTechImplId", serTechImplId);
		return messageFlowDao.getTechImplMessageById(paramMap);
	}

	@Override
	public List<HashMap> getAttrSpecList(String serTechImplId, String commCd) {
		return messageFlowDao.getAttrSpecList(serTechImplId,commCd);
	}

	@Override
	public Map<String, String> getSerInvokeInsMessage(JSONObject finalJson) {
		String serviceId = finalJson.getString("serviceId");
		String componentId = finalJson.getString("componentId");
		Map<String,String> result = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("serviceId", serviceId);
		map.put("componentId", componentId);
		List<Map<String,Object>> serInvokeInsList = messageFlowDao.getSerInvokeInsList(map);
		if(null != serInvokeInsList && serInvokeInsList.size()>0){
			if(null != serInvokeInsList.get(0).get("MESSAGE_FLOW_ID")){
				result.put("id", serInvokeInsList.get(0).get("MESSAGE_FLOW_ID")+"");
			}else{
				result.put("id", "");
			}
			if(null != serInvokeInsList.get(0).get("SER_INVOKE_INS_ID")){
				result.put("serInvoId", serInvokeInsList.get(0).get("SER_INVOKE_INS_ID")+"");
			}else{
				result.put("serInvoId", "");
			}
		}
		return result;
	}
	
	@Override
	public Map<String, String> getSerInvokeInsMessageForNoticeTask(JSONObject finalJson) {
		String serviceId = finalJson.getString("serviceId");
		String componentId = finalJson.getString("componentId");
		String messageFlowId = finalJson.getString("messageFlowId");
		Map<String,String> result = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("serviceId", serviceId);
		map.put("componentId", componentId);
		map.put("messageFlowId", messageFlowId);
		List<Map<String,Object>> serInvokeInsList = messageFlowDao.getSerInvokeInsListForNoticeTask(map);
		if(null != serInvokeInsList && serInvokeInsList.size()>0){
			if(null != serInvokeInsList.get(0).get("MESSAGE_FLOW_ID")){
				result.put("id", serInvokeInsList.get(0).get("MESSAGE_FLOW_ID")+"");
			}else{
				result.put("id", "");
			}
			if(null != serInvokeInsList.get(0).get("SER_INVOKE_INS_ID")){
				result.put("serInvoId", serInvokeInsList.get(0).get("SER_INVOKE_INS_ID")+"");
			}else{
				result.put("serInvoId", "");
			}
		}
		return result;
	}
	
}
