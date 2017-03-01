package com.ailk.o2p.portal.dao.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.Contract;
import com.ailk.eaap.op2.bo.ContractFormat;
import com.ailk.eaap.op2.bo.ContractVersion;
import com.ailk.eaap.op2.bo.DirSerList;
import com.ailk.eaap.op2.bo.Endpoint;
import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.NodeDesc;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.SerTechImpl;
import com.ailk.eaap.op2.bo.ServiceAtt;
import com.ailk.eaap.op2.bo.ServiceProductRela;
import com.ailk.eaap.op2.bo.TechImpAtt;
import com.ailk.eaap.op2.bo.TechImpl;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NData;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NDatas;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.common.EAAPErrorCodeDef;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.eaap.op2.common.EAAPTags;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.bo.ServiceOwner;
import com.ailk.o2p.portal.bo.UserDefined;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.dao.SqlMapDAO;
import com.linkage.rainbow.util.StringUtil;

@Repository
public class ProviderDao implements IProviderDao {
	
	private static Log log = LogFactory.getLog(ProviderDao.class);
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	@Override
	public List<Map<String, Object>> findApply(Map<String, String> map) {
		List<Map<String, Object>> findApplyList = null;
		findApplyList = sqlMapDao.queryForList("eaap-op2-portal-prov.showApply", map);
		if(findApplyList.size()< 1){
			findApplyList = new ArrayList<Map<String, Object>>();
		}
		return findApplyList;
	}
	@Override
	public List<Map<String, Object>> callSystem() {
		List<Map<String, Object>> call = new ArrayList();
		try {
			call = sqlMapDao.queryForList("eaap-op2-portal-prov.callSystem",null);
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return call;
	}
	@Override
	public List<Map<String, Object>> selectFileShare(Map paramMap) {
		List<Map<String, Object>> fileShareList = new ArrayList();
		try {
			fileShareList = sqlMapDao.queryForList("eaap-op2-portal-prov.selectFileShare",paramMap);
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return fileShareList;
	}
	@Override
	public Map<String, String> queryProvSystem(Map paramMap) {
		Map<String, String> findSystemMap = new HashMap<String,String>();
		try {
			findSystemMap = (Map<String, String>) sqlMapDao.queryForObject("eaap-op2-portal-prov.showComponent", paramMap);
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return findSystemMap;
	}
	@Override
	public Integer saveFileShare(FileShare file) {
		File fileContent = file.getSFileContent() ;
		file.setSFileContent(null) ;
		Integer fileShareId = (Integer)sqlMapDao.insert("eaap-op2-portal-prov.insertFileShare", file) ;
		 return  inImageContent(fileShareId,fileContent) ;
	} 
	
	private Integer inImageContent(Integer fileShareId,File fileContent){
		Connection con =null;
		PreparedStatement pstmt =null;
		FileInputStream stream =null;
        try {
			con = sqlMapDao.getSmcTemplate().getDataSource().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("update file_share set s_file_content = ? where s_file_id=?");
			stream = new FileInputStream(fileContent);
			pstmt.setInt(2, fileShareId);
			pstmt.setBinaryStream(1, stream, stream.available());
			pstmt.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}finally{
			try {
				if(stream!=null){
					stream.close();
				}
			} catch (IOException e) {
				log.error("IOException!", e);
			}
			try{
				if(pstmt!=null){
					pstmt.close();
				}
			} catch (SQLException e) {
				log.error("SQLException!", e);
			}
			try{
				if(con!=null){
					con.close();
				}
			}
			 catch (SQLException e) {
				log.error("Exception!", e);
			} 
		}
        return fileShareId ;
	}
	@Override
	public boolean isExitCode(Map paramMap) {
		Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prov.countComponentCode", paramMap) ;
		if(num > 0){
			return true;
		}
		return false;
	}
	@Override
	public String saveComponent(Component component) {
		String componentId = null;
		componentId = (String) sqlMapDao.insert("eaap-op2-portal-prov.insertComponent", component);	
		return componentId;
	}
	@Override
	public String updateComponent(Component component) {
		int num;
		String componentId = null;
		num = sqlMapDao.update("eaap-op2-portal-prov.updateComponent", component);
		if(num >= 1){
			componentId = component.getComponentId();
		}
		return componentId;
	}
	@Override
	public List<Map<String, String>> provAbility(Map map) {
		List<Map<String, String>> provAbility;
		try {
			provAbility = sqlMapDao.queryForList("eaap-op2-portal-prov.serviceList", map);
		} catch (Exception e) {
			throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
				      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
		}
		return provAbility;
	}
	@Override
	public List<Map<String, Object>> userDefinedList(Map paramMap) {
		List<Map<String, Object>> userDefinedList;
		try {
			userDefinedList = sqlMapDao.queryForList("eaap-op2-portal-prov.serviceListOut", paramMap);
		} catch (Exception e) {
			throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
				      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
		}
		return userDefinedList;
	}
	@Override
	public Integer addTechImpAtt(Map<String, String> map,Map paramMap) {
		Integer techImpAttId = null;
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		String[] attr =  map.get("attrspecvalue").toString().split(",");
		String[] svi = map.get("service").toString().split(",");
		Integer techimplId = null;
		Integer serTechImplId = null;
		for(int i=0;i<attr.length;i++){
			if(attr[i].equals("")){
				continue;
			}
			Component component = new Component();
			Component component2 = new Component();
			TechImpl techimpl = new TechImpl();
			techimpl.setCommProCd(map.get("commProCd").toString());
			techimpl.setTechImpConPoId(Integer.valueOf("1"));
			component2.setComponentId(map.get("componentId").toString());
			component2.setTenantId(tenantId);
			component = (Component) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectComponent", component2);
			techimpl.setTechImplName(component.getName());
			techimpl.setComponentId(map.get("componentId").toString());
			techimpl.setUsealbeState(EAAPConstants.COMM_STATE_VALID);
			techimpl.setTenantId(tenantId);
			techimplId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertTechImpl", techimpl);
			
			TechImpAtt att = new TechImpAtt();
			att.setAttrSpecId(Integer.valueOf(1));
			att.setTechImplId(techimplId);
			att.setAttrSpecValue(attr[i]);
			att.setState(EAAPConstants.COMM_STATE_VALID);
			att.setTenantId(tenantId);
			techImpAttId =  (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertTechImpAtt", att);
			
			SerTechImpl  sti = new SerTechImpl();
			sti.setTechImplId(techimplId);
			sti.setServiceId(Integer.valueOf(svi[i]));
			sti.setState(EAAPConstants.COMM_STATE_NEW);
			sti.setTenantId(tenantId);
			serTechImplId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertSerTechImpl", sti);
			
			String serviceId = svi[i];
			String noticeServiceId = WebPropertyUtil.getPropertyValue("NOTICE_SERVICE_ID");  //从公共配置文件eaap_common.properties中读取通用的通知服务ID
			if(serviceId!=null && serviceId!="" && serviceId.equals(noticeServiceId)){		
				//当添加通知能力时，不再创建新的透传消息流，使用共用的通知消息流，只在这共用消息流中添加一个调用端点call，并关联上服务技术实现：
				noticeTaskMessageFlowAddCall(serTechImplId.toString(),map);	//在通知透传消息流中增加一个调用端点
			}else{
				String techImplName = component.getName();
				createDirectMessageFlow(serTechImplId.toString(),techImplName,map);		//创建一个透传的消息流
			}
		}		
		return techImpAttId;
	}
	
	//为共用的通知消息流添加一个调用端点call，并关联上服务技术实现：
	public void noticeTaskMessageFlowAddCall(String serTechImplId,Map<String, String> paramMap){
		String messageFlowIds = WebPropertyUtil.getPropertyValue("NOTICE_MESSAGE_FLOW_ID");  //从公共配置文件eaap_common.properties中读取公用通知消息流ID
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		Integer messageFlowId 		= null;		    //公用通知消息流ID
		if(messageFlowIds!=null && !messageFlowIds.equals("")){
			messageFlowId = Integer.parseInt(messageFlowIds);
		}
		Integer beginEndpointId 	= null;				//开始端点ID
		Integer endEndpointId		= null;				//结束端点ID
		Integer callEndpointId 		= getSeq("SEQ_ENDPOINT");				//调用端点ID
		
		if(null !=messageFlowId && !"".equals(messageFlowId)){
			Map messageFlowMap=new HashMap();
			messageFlowMap.put("tenantId", tenantId);
			messageFlowMap.put("messageFlowId", messageFlowId.toString());
			List<Endpoint> beginAndEndEndpointList = (ArrayList<Endpoint>)sqlMapDao.queryForList("eaap-op2-portal-prov.getMessageFlowBeginAndEndEndpoint", messageFlowMap) ;
			if(null != beginAndEndEndpointList && beginAndEndEndpointList.size()>0){
				for(Endpoint endpointObj:beginAndEndEndpointList){
					Integer endpointSpecId = endpointObj.getEndpointSpecId();		//7：Begin 开始端点, 11：End 结束端点
					if(endpointSpecId==7){
						beginEndpointId = endpointObj.getEndpointId();
					}else if(endpointSpecId==11){
						endEndpointId = endpointObj.getEndpointId();
					}
				}
			}
		}
		
		if(null !=beginEndpointId && null !=endEndpointId && null !=callEndpointId){
			String cMapCodeVml		= "<span id=\"E"+callEndpointId+"\" style=\"CURSOR: hand; HEIGHT: 35px; WIDTH: 35px; BACKGROUND: url(../resource/common/images/messageArray/endpointSpec/webservice.png) no-repeat center 50%; POSITION: absolute; LEFT: 302px; Z-INDEX: 1; TOP: 63px\"/><div id=\"T"+callEndpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Call</div>";
			//创建端点：
			Map endPointMap=new HashMap();
			endPointMap.put("tenant_id", tenantId);
			saveEndpoint(callEndpointId, messageFlowId, 6, cMapCodeVml, "Call",endPointMap);				// 6：  Call 调用端点
						
			//端点属性值：
			Map endpointAttrValueMap=new HashMap();
			endpointAttrValueMap.put("endpoint_Id", callEndpointId);			//调用端点ID
			endpointAttrValueMap.put("endpoint_Spec_Attr_Id", 22);  		 	//22:服务技术实现
			endpointAttrValueMap.put("attr_Value", serTechImplId);				//服务技术实现ID
			endpointAttrValueMap.put("tenant_id", tenantId);
			sqlMapDao.insert("eaap-op2-portal-prov.saveEndpointAttrValue", endpointAttrValueMap);
			
			//创建路由：
			String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 146,62.5,286,63.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
			String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 321,63.5,455,62.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
			Map serviceRouteConfigMap=new HashMap();
			serviceRouteConfigMap.put("tenant_id", tenantId);
			saveServiceRouteConfig(messageFlowId, beginEndpointId, callEndpointId, mapCodeVml_1,serviceRouteConfigMap);	//路径1
			saveServiceRouteConfig(messageFlowId, callEndpointId, endEndpointId, mapCodeVml_2,serviceRouteConfigMap);		//路径2
					
			//对应服务技术实现的默认消息流更新为新创建的透传消息流:
			Map map=new HashMap();
			map.put("messageFlowId",messageFlowId);
			map.put("serTechImplId",serTechImplId);
			map.put("tenant_id", tenantId);
			sqlMapDao.update("eaap-op2-portal-prov.updateServiceDefaultMsgFlow", map);
		}
	}
	
	//创建一个透传的消息流：
		public void createDirectMessageFlow(String serTechImplId, String techImplName,Map paramMap){
			Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
			Integer messageFlowId 		= getSeq("SEQ_MESSAGE_FLOW");		//消息流ID
			Integer beginEndpointId 	= getSeq("SEQ_ENDPOINT");				//开始端点ID
			Integer callEndpointId 		= getSeq("SEQ_ENDPOINT");				//调用端点ID
			Integer endEndpointId		= getSeq("SEQ_ENDPOINT");				//结束端点ID

			String bMapCodeVml	= "<span id=\"E"+beginEndpointId+"\" style=\"CURSOR: hand; HEIGHT: 35px; WIDTH: 35px; BACKGROUND: url(../resource/common/images/messageArray/endpointSpec/start.png) no-repeat center 50%; POSITION: absolute; LEFT: 109px; Z-INDEX: 1; TOP: 46px\"/><div id=\"T"+beginEndpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Begin</div>";
			String cMapCodeVml		= "<span id=\"E"+callEndpointId+"\" style=\"CURSOR: hand; HEIGHT: 35px; WIDTH: 35px; BACKGROUND: url(../resource/common/images/messageArray/endpointSpec/webservice.png) no-repeat center 50%; POSITION: absolute; LEFT: 302px; Z-INDEX: 1; TOP: 63px\"/><div id=\"T"+callEndpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">Call</div>";
			String eMapCodeVml	= "<span id=\"E"+endEndpointId+"\" style=\"CURSOR: hand; HEIGHT: 35px; WIDTH: 35px; BACKGROUND: url(../resource/common/images/messageArray/endpointSpec/end.png) no-repeat center 50%; POSITION: absolute; LEFT: 458px; Z-INDEX: 1; TOP: 46px\"/><div id=\"T"+endEndpointId+"\" style=\"background-color:#CEDEF0;position:absolute;left:230;top:180;width:75;height35;textalign:center;fontsize:9pt;word-break:break-all;overflow:hidden;z-index:0\">End</div>";
			
			//创建端点：
			Map endPointMap=new HashMap();
			endPointMap.put("tenant_id", tenantId);
			saveEndpoint(beginEndpointId, messageFlowId, 7, bMapCodeVml, "Begin",endPointMap);		// 7：  Begin 开始端点
			saveEndpoint(callEndpointId, messageFlowId, 6, cMapCodeVml, "Call",endPointMap);				// 6：  Call 调用端点
			saveEndpoint(endEndpointId, messageFlowId, 11, eMapCodeVml, "End",endPointMap);			// 11：End 结束端点
						
			//端点属性值：
			Map endpointAttrValueMap=new HashMap();
			endpointAttrValueMap.put("endpoint_Id", callEndpointId);			//调用端点ID
			endpointAttrValueMap.put("endpoint_Spec_Attr_Id", 22);  		 	//22:服务技术实现
			endpointAttrValueMap.put("attr_Value", serTechImplId);				//服务技术实现ID
			endpointAttrValueMap.put("tenantId", tenantId);	
			sqlMapDao.insert("eaap-op2-portal-prov.saveEndpointAttrValue", endpointAttrValueMap);
			
			//创建路由：
			String mapCodeVml_1 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+beginEndpointId+"-E"+callEndpointId+"\" title=\"line_1\" points=\" 146,62.5,286,63.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
			String mapCodeVml_2 = "<v:polyline style=\"z-index:2;position:absolute;cursor:hand\" id=\"E"+callEndpointId+"-E"+endEndpointId+"\" title=\"line_1\" points=\" 321,63.5,455,62.5\" filled=\"f\" strokecolor=\"blue\" strokeweight=\"1pt\"><v:stroke endarrow=\"classic\"/></v:polyline>";
			Map serviceRouteConfigMap=new HashMap();
			serviceRouteConfigMap.put("tenant_id", tenantId);
			saveServiceRouteConfig(messageFlowId, beginEndpointId, callEndpointId, mapCodeVml_1,serviceRouteConfigMap);	//路径1
			saveServiceRouteConfig(messageFlowId, callEndpointId, endEndpointId, mapCodeVml_2,serviceRouteConfigMap);		//路径2
			
			//保存消息流：
			Map messageMap=new HashMap();
			messageMap.put("message_Flow_Id",messageFlowId);
			messageMap.put("message_Flow_Name",techImplName+"_MsgFlow");
			messageMap.put("first_Endpoint_Id",beginEndpointId);
			messageMap.put("state","A");
			messageMap.put("descriptor", "");
			messageMap.put("tenantId", tenantId);	
			sqlMapDao.insert("eaap-op2-portal-prov.saveMessageFlow", messageMap);
			
			//对应服务技术实现的默认消息流更新为新创建的透传消息流:
			Map map=new HashMap();
			map.put("messageFlowId",messageFlowId);
			map.put("serTechImplId",serTechImplId);
			map.put("tenantId", tenantId);	
			sqlMapDao.update("eaap-op2-portal-prov.updateServiceDefaultMsgFlow", map);
		}
		//创建端点
		public void saveEndpoint(Integer endpointId, Integer messageFlowId, Integer endpointSpecId, String bMapCodeVml, String endpointName,Map endPointParamMap){
			Map endpointMap=new HashMap();
			endpointMap.put("endpoint_Id", endpointId);
			endpointMap.put("message_Flow_Id", messageFlowId);
			endpointMap.put("endpoint_Spec_Id", endpointSpecId);  						//7: Begin 开始端点，6：Call 调用端点，11：End 结束端点
			endpointMap.put("in_Data_Type_Id", 1);
			endpointMap.put("out_Data_Type_Id", 1);
			endpointMap.put("endpoint_Name", endpointName);
			endpointMap.put("endpoint_Code", endpointId);
			endpointMap.put("enable_In_Trace", "Y");
			endpointMap.put("enable_Out_Trace", "Y");
			endpointMap.put("enable_In_Log", "Y");
			endpointMap.put("enable_Out_Log", "Y");
			endpointMap.put("state", "A");
			endpointMap.put("endpoint_Desc", "");
			endpointMap.put("map_Code", bMapCodeVml);
			endpointMap.put("tenant_id", Integer.parseInt(endPointParamMap.get("tenant_id").toString()));
			sqlMapDao.insert("eaap-op2-portal-prov.saveEndpoint", endpointMap);
		}
		//创建路由
		public void saveServiceRouteConfig(Integer messageFlowId, Integer fromEndpointId, Integer toEndpointId, String mapCodeVml,Map serviceRouteConfigParamMap){
			Integer routeId				= getSeq("SEQ_SERVICE_ROUTE_CONF");		//路由ID
			Integer routePolicyId		= getSeq("SEQ_ROUTE_POLI");						//路由规则ID
			Integer tenantId=Integer.parseInt(serviceRouteConfigParamMap.get("tenant_id").toString());
			//路由规则
			Map  paramRoutePolicyMap =  new HashMap();
			paramRoutePolicyMap.put("rule_Strategy_Id", 1);							//1：DIRECT 直接透传
			paramRoutePolicyMap.put("route_Policy_Id", routePolicyId);
			paramRoutePolicyMap.put("tenant_id", tenantId);
			sqlMapDao.insert("eaap-op2-portal-prov.addRoutePolicy", paramRoutePolicyMap);
			
			//路由配置
			Map serviceRouteConfigMap=new HashMap();
			serviceRouteConfigMap.put("route_Id",routeId);
			serviceRouteConfigMap.put("route_Policy_Id",routePolicyId);
			serviceRouteConfigMap.put("message_Flow_Id",messageFlowId);
			serviceRouteConfigMap.put("from_Endpoint_Id", fromEndpointId);
			serviceRouteConfigMap.put("to_Endpoint_Id",toEndpointId);
			serviceRouteConfigMap.put("syn_Asyn","SYN");
			serviceRouteConfigMap.put("state","A");
			serviceRouteConfigMap.put("map_Code", mapCodeVml);
			serviceRouteConfigMap.put("tenant_id", tenantId);
			sqlMapDao.insert("eaap-op2-portal-prov.saveService_Route_Config", serviceRouteConfigMap);
		}
		//取Sequence值:
		public Integer getSeq(String sqlName){
			Map map = new HashMap();
			map.put("sequenceName", sqlName);
			return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prov.getSeq",map); 
		}
		@Override
		public List<Map<String, Object>> selectCommProtocal() {
			List<Map<String, Object>> commProtocalList = new ArrayList();
			try {
				commProtocalList = sqlMapDao.queryForList("eaap-op2-portal-prov.selectCommProtocal",null);
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return commProtocalList;
		}
		@Override
		public List<Map<String, Object>> selectDirectory(Map paramMap) {
			List<Map<String, Object>> directoryList = new ArrayList();
			try {
				directoryList = sqlMapDao.queryForList("eaap-op2-portal-prov.selectDirectory",paramMap);
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return directoryList;
		}
		@Override
		public List<Map<String, Object>> selectConType(Map paramMap) {
			List<Map<String, Object>> conTypeList = new ArrayList();
			try {
				conTypeList = sqlMapDao.queryForList("eaap-op2-portal-prov.selectConType",paramMap);
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return conTypeList;
		}
		@Override
		public boolean checkCode(Map paramMap) {
			Integer num = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.countContractCode", paramMap);
			if(num > 0){
				return true;
			}
			return false;
		}
		@Override
		public boolean checkVersion(Map paramMap) {
			Integer num = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.countContractVersion", paramMap);
			if(num > 0){
				return true;
			}
			return false;
		}
		@Override
		public Contract addContract(Contract contract) {
			Integer contractId = null;
			Contract newContract = null;
			try {
				contractId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertContract",contract);
				if(contractId != null){
					newContract = new Contract();
					newContract.setContractId(contractId);
					newContract.setTenantId(contract.getTenantId());
					newContract = (Contract) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectContract",newContract);
				}
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return newContract;
		}
		@Override
		public ContractVersion addContractVersion(ContractVersion contractVersion) {
			Integer contractVersionId = null;
			ContractVersion newContractVersion = null;
			try {
				contractVersionId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertContractVersion",contractVersion);
				if(contractVersionId != null){
					newContractVersion = new ContractVersion();
					newContractVersion.setContractVersionId(contractVersionId);
					newContractVersion.setTenantId(contractVersion.getTenantId());
					newContractVersion = (ContractVersion) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectContractVersion",newContractVersion);
				}
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return newContractVersion;
		}
		@Override
		public ServiceOwner addService(ServiceOwner service) {
			Integer serviceId = null;
			ServiceOwner newService = null;
			try {
				serviceId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertService",service);	
				if(serviceId!= null){
					newService = new ServiceOwner();
					newService.setServiceId(serviceId);
					newService.setTenantId(service.getTenantId());
					newService = (ServiceOwner) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectService",newService);
				}
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return newService;
		}
		@Override
		public Integer addApi(Api api) {
			Integer apiId = null;
			try {
				apiId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertApi",api);	
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return apiId;
		}
		@Override
		public Integer addDirServiceList(DirSerList dirSerList) {
			Integer dirSerId = null;
			try {
				dirSerId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertDirSerList",dirSerList);			
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return dirSerId;
		}
		@Override
		public Integer addServiceAtt(ServiceAtt serviceAtt) {
			Integer serviceAttId = null;
			try {
				serviceAttId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertServiceAtt",serviceAtt);			
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return serviceAttId;	
		}
		@Override
		public Integer addUserDefineTechImpAtt(Map map,
				Map<String, String> mapParam,UserDefined userDefined) {
			Integer techImpAttId = null;
//			String attr =  map.get("attrspecvalue").toString();
			String svi = map.get("service").toString();
			Integer techimplId = null;
			Integer serTechImplId = null;
			Integer tenantId=Integer.parseInt(map.get("tenantId").toString());
			Component component = null;
			Component component2 = new Component();
			TechImpl techimpl = new TechImpl();
			techimpl.setCommProCd(map.get("commProCd").toString());
			techimpl.setTechImpConPoId(Integer.valueOf("1"));
			component2.setComponentId(map.get("componentId").toString());
			component2.setTenantId(tenantId);
			component = (Component) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectComponent", component2);
			techimpl.setTechImplName(component.getName());
			techimpl.setComponentId(map.get("componentId").toString());
			techimpl.setUsealbeState(EAAPConstants.COMM_STATE_VALID);
			techimpl.setTenantId(tenantId);
			techimplId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertTechImpl", techimpl);
			
			mapParam.put("techimplId", techimplId+"");//返加参数
			
			String[] dyAttrSpecId = userDefined.getDyAttrSpecIds();
			String[] dyAttrSpecValue = userDefined.getDyAttrSpecValues();
			if(null != dyAttrSpecId && dyAttrSpecId.length>0){
				for(int i=0;i<dyAttrSpecId.length;i++){
					String attrSpecValue=dyAttrSpecValue[i];
					if(!StringUtils.isEmpty(attrSpecValue)){
						TechImpAtt att = new TechImpAtt();
						att.setAttrSpecId(Integer.valueOf(dyAttrSpecId[i]));
						att.setTechImplId(techimplId);
						att.setAttrSpecValue(attrSpecValue);
						att.setState(EAAPConstants.COMM_STATE_VALID);
						att.setTenantId(tenantId);
						techImpAttId =  (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertTechImpAtt", att);
					}
				}
			}
			
			SerTechImpl  sti = new SerTechImpl();
			sti.setTechImplId(techimplId);
			sti.setServiceId(Integer.valueOf(svi));
			sti.setState(EAAPConstants.COMM_STATE_NEW);
			sti.setTenantId(tenantId);
			serTechImplId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertSerTechImpl", sti);
			
			String techImplName = component.getName();
			createDirectMessageFlow(serTechImplId.toString(),techImplName,map);		//创建一个透传的消息流		
			return techImpAttId;
		}
		@Override
		public void editContract(Contract contract) {
			sqlMapDao.update("eaap-op2-portal-prov.updateContract", contract);
		}
		@Override
		public void editDirSerList(DirSerList dirSerList) {
			sqlMapDao.update("eaap-op2-portal-prov.updateDirSerList", dirSerList);
		}
		@Override
		public void deleteDirSerList(Map paramMap){
			sqlMapDao.update("eaap-op2-portal-prov.deleteDirSerList", paramMap);
		}
		@Override
		public Integer editService(ServiceOwner service) {
			return sqlMapDao.update("eaap-op2-portal-prov.updateService", service);
		}
		@Override
		public void editTechImpl(TechImpl techImpl) {
			sqlMapDao.update("eaap-op2-portal-prov.updateTechImpl", techImpl);
		}
		@Override
		public Integer editAbility(TechImpAtt tech,Map paramMap) {
			Integer attId = null;
			Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
			Map getSevTechimplMap=new HashMap();
			getSevTechimplMap.put("value", tech.getTechImpAttId().toString());
			getSevTechimplMap.put("tenantId", tenantId);
			try {
				attId = (Integer)sqlMapDao.update("eaap-op2-portal-prov.updateTechImpAtt", tech);
				//改变服务技术实现的状态  mawl add
				Integer serTechImplId = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.getSevTechimplId", getSevTechimplMap);
				SerTechImpl serTechImpl = new SerTechImpl();
				serTechImpl.setSerTechImplId(serTechImplId);
				serTechImpl.setState(EAAPConstants.COMM_STATE_NEW);
				serTechImpl.setTenantId(tenantId);
				sqlMapDao.update("eaap-op2-portal-prov.updateSerTechImpl", serTechImpl);
			} catch (Exception e) {
				throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
					      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
			}	
			return attId;
		}
		@Override
		public Integer addContractFormat(ContractFormat cf) {
			Integer contractFormatId = null;
			try {
				contractFormatId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertContractFormat",cf);			
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return contractFormatId;	
		}
		@Override
		public Integer editNodeDesc(NodeDesc nodeDesc) {
			if(null != nodeDesc.getNodeDescId()){
				return sqlMapDao.update("eaap-op2-portal-prov.updateNodeDesc", nodeDesc);
			}
			return null;
		}
		@Override
		public String getNodeIdByPath(Map paramMap) {
			Map<String,String> map = new HashMap<String,String>();
			return (String)sqlMapDao.queryForObject("eaap-op2-portal-prov.getNodeIdByPath", paramMap);
		}
		@Override
		public Integer addNodeDesc(NodeDesc nodeDesc) {
			Integer nodeDescId = null;
			try {
				nodeDescId = (Integer) sqlMapDao.insert("eaap-op2-portal-prov.insertNodeDesc",nodeDesc);			
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return nodeDescId;
		}
		@Override
		public List<Map<String, Object>> queryNodeDesc(Map map) {
			List<Map<String, String>> nodeDescList = null;
			return sqlMapDao.queryForList("eaap-op2-portal-prov.selectNodeDesc", map);
		}
		@Override
		public Integer editContractFormat(ContractFormat cf) {
			return sqlMapDao.update("eaap-op2-portal-prov.updateContractFormat", cf);
		}
		@Override
		public List<Map<String, Object>> getNodeDesc(Map paramMap) {
			return sqlMapDao.queryForList("eaap-op2-portal-prov.getNodeDescId", paramMap);
		}
		@Override
		public void deleteNodeDesc(String nodeDescId,Map paramMap) {
			Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
			if(null != nodeDescId && !"".equals(nodeDescId)){
				NodeDesc nodeDesc = new NodeDesc();
				nodeDesc.setNodeDescId(Integer.valueOf(nodeDescId));
				nodeDesc.setState(EAAPConstants.COMM_STATE_FAIL);
				nodeDesc.setTenantId(tenantId);
				sqlMapDao.update("eaap-op2-portal-prov.updateNodeDesc", nodeDesc);
			}
		}
		@Override
		public List<Map<String, Object>> queryUserFine(Map paramMap) {
			return sqlMapDao.queryForList("eaap-op2-portal-prov.selectUserDefine", paramMap);
		}
		@Override
		public List<Map<String, Object>> queryDirSerList(Map paramMap) {
			return sqlMapDao.queryForList("eaap-op2-portal-prov.selectDirSerList", paramMap);
		}
		
		@Override
		public List<Map<String, Object>> queryContractFormat(Map paramMap) {
			return sqlMapDao.queryForList("eaap-op2-portal-prov.contractFormatList", paramMap);
		}
		@Override
		public void operatorSerTechImpl(Map paramMap) {
			Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
			String techImpAttId=paramMap.get("techimpAttId").toString();
			String state=paramMap.get("state").toString();
			Integer serTechImplId = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.getSevTechimplId", paramMap);
			SerTechImpl serTechImpl = new SerTechImpl();
			serTechImpl.setSerTechImplId(serTechImplId);
			serTechImpl.setState(state);
			serTechImpl.setTenantId(tenantId);
			sqlMapDao.update("eaap-op2-portal-prov.updateSerTechImpl", serTechImpl);
			
			Integer techImplId = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.getTechimplId", paramMap);
			TechImpl techImpl = new TechImpl();
			if(state.equals(EAAPConstants.COMM_STATE_DELETE)){
				techImpl.setUsealbeState(EAAPConstants.COMM_STATE_FAIL);
			}else{
				techImpl.setUsealbeState(EAAPConstants.COMM_STATE_VALID);
			}
			techImpl.setTechImplId(techImplId);
			techImpl.setTenantId(tenantId);
			sqlMapDao.update("eaap-op2-portal-prov.updateTechImpl", techImpl);
			
			TechImpAtt techimplAtt= new TechImpAtt();
			techimplAtt.setTechImpAttId(Integer.valueOf(techImpAttId));
			if(state.equals(EAAPConstants.COMM_STATE_DELETE)){
				techimplAtt.setState(EAAPConstants.COMM_STATE_FAIL);
			}else{
				techimplAtt.setState(EAAPConstants.COMM_STATE_VALID);
			}
			techimplAtt.setTenantId(tenantId);
			sqlMapDao.update("eaap-op2-portal-prov.updateTechImpAtt", techimplAtt);
			
			if(state.equals(EAAPConstants.COMM_STATE_DELETE)){
				List<Map<String, Object>> userDefineList = sqlMapDao.queryForList("eaap-op2-portal-prov.selectUserDefine", paramMap);
				if(null != userDefineList && userDefineList.size()>0){
					String contractId = StringUtil.valueOf(userDefineList.get(0).get("CONTRACTID"));
					Contract contract = new Contract();
					contract.setContractId(Integer.parseInt(contractId));
					contract.setTenantId(tenantId);
					sqlMapDao.delete("eaap-op2-portal-prov.deleteContract", contract);
					Integer contractVersionId=(Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectConVersion", paramMap);
					ContractVersion contractVersionQuery=new ContractVersion();
					contractVersionQuery.setContractVersionId(contractVersionId);
					contractVersionQuery.setTenantId(tenantId);
					ContractVersion  contractVersion= (ContractVersion) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectContractVersion", contractVersionQuery);
					sqlMapDao.delete("eaap-op2-portal-prov.deleteContractVersion", contractVersion);
					ServiceOwner serviceQuery=new ServiceOwner();
					serviceQuery.setContractVersionId(contractVersionId.toString());
					serviceQuery.setTenantId(tenantId);
					ServiceOwner service=(ServiceOwner) sqlMapDao.queryForObject("eaap-op2-portal-prov.queryServiceForDel", serviceQuery);
					sqlMapDao.delete("eaap-op2-portal-prov.deleteService", service);
				}
			}
			
		}
		
		public WorkTaskConf queryWorkTaskConf(Map paramMap){
			return (WorkTaskConf) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectWorkTaskConf", paramMap);
		}
		
		public ServiceProductRela queryServiceProductRela(Map paramMap){
			return (ServiceProductRela) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectServiceProductRela", paramMap);
		}
		
		@Override
		public String getComponentSeq() {
			return (String)sqlMapDao.queryForObject("eaap-op2-portal-prov.getComponentSeq", null);
		}
		@Override
		public int countProvAbility(Map map) {
			return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prov.countProvAbility", map);
		}
		@Override
		public List<Map<String, Object>> showAbility(Map map) {
			List<Map<String, Object>> findAbility;
			try {
				findAbility = sqlMapDao.queryForList("eaap-op2-portal-prov.showService", map);
				if(findAbility.size()< 1){
					findAbility = null;
				}
			} catch (Exception e) {
				throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
					      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
			}
			return findAbility;
		}
		@Override
		public int countShowAbility(Map map) {
			return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prov.countShowAbility", map);
		}
 
		@Override
		public List<Map<String, Object>> showAbilityByName(Map map)throws EAAPException {
			// TODO Auto-generated method stub
			List<Map<String, Object>> findAbility;
			try {
				findAbility = sqlMapDao.queryForList("eaap-op2-portal-prov.showServiceByCapName", map);
				if(findAbility.size()< 1){
					findAbility = null;
				}
			} catch (Exception e) {
				throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
					      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
			}
			return findAbility;
		}
		public BigDecimal insertProduct(Product pro){
			return (BigDecimal)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProduct", pro) ;
			
		}
		
		public BigDecimal insertProdOffer(ProdOffer prodOffer){
			return (BigDecimal)sqlMapDao.insert("eaap-op2-portal-prodOffer.insertProdOffer", prodOffer) ;
			
		}
		public Integer insertOfferProdRel(OfferProdRel offerProdRel){
			return (Integer)sqlMapDao.insert("eaap-op2-portal-offerProdRel.insertOfferProdRel", offerProdRel) ;
			
		}
		
		public List<Map<String, Object>> showPackage(String componentId,Map map){
			List<Map<String, Object>> showPackage = null;
			List<Map<String, Object>> resault =  new ArrayList<Map<String, Object>>() ;
			Integer tenantId=Integer.parseInt(map.get("tenantId").toString());
			Map paramMap=new HashMap();
			paramMap.put("tenantId", tenantId);
			paramMap.put("componentId", componentId);
			showPackage = sqlMapDao.queryForList("eaap-op2-portal-prov.selectPackageList", paramMap);
			List<Map<String, Object>> sonList = null;
			List<Map<String, Object>> sonList2 = null;
			List<Map<String, Object>> sonList3 = null; //API
			
			for(int i=0; i<showPackage.size();i++){
				Map father = (HashMap)showPackage.get(i);
				Map son = new HashMap();
				if(!"".equals(father.get("PROD_OFFER_ID"))){ 
					son.put("prodOfferId", father.get("PROD_OFFER_ID"));
					son.put("servCode", father.get("PROD_OFFER_ID"));
					son.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
					son.put("private", 1);
					son.put("offerId", son.get("prodOfferId").toString().split(","));
					son.put("tenantId", tenantId);
					sonList =sqlMapDao.queryForList("eaap-op2-portal-prov.getPricingPlan", son);
					sonList3 =sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryApiIds", son) ;
					sonList2 = sqlMapDao.queryForList("eaap-op2-portal-settlement.getSettleAPI",son);
					father.put("PricePlan", sonList);
					father.put("settleList", sonList2);//结算
					father.put("APIs", sonList3);//api
				}
				resault.add(father);
				
			}
//			HashMap settleMap = new HashMap();
//			settleMap.put("partnerCode", prodOffer.getOfferProviderId());
//			settleMap.put("servCode", prodOffer.getProdOfferId());
//			settleMap.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
//			settleList = getProvideService().getSettle(settleMap);
//			
			
			
			return resault;
		}
		@SuppressWarnings("unchecked")
		public List<Map<String, String>> queryService(String service,Map paramMap)
				throws EAAPException {
			// TODO Auto-generated method stub
			List<Map<String, String>> serviceList;
			Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
			try {
				Map map=new HashMap();
				map.put("service", service.split(","));
				map.put("tenantId", tenantId);
				serviceList = sqlMapDao.queryForList("eaap-op2-portal-prov.queryService",map );
			} catch (Exception e) {
				throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
					      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
			}
			return serviceList;
		}
		
		public BigDecimal getProductbyCap(Map map){
			return (BigDecimal)  sqlMapDao.queryForObject("eaap-op2-portal-prov.getProductbyCap", map) ;
	  

		}
		public Integer insertServiceProRel(Map map){
			return (Integer)sqlMapDao.insert("eaap-op2-portal-prov.insertServiceProRel", map) ;
			
		}
 
		@Override
		public String editProvSystem(Component component) {
			int num;
			String componentId = null;
			num = sqlMapDao.update("eaap-op2-portal-prov.updateComponent", component);
			if(num >= 1){
				componentId = component.getComponentId();
			}
			return componentId;
		}
		@Override
		public List<Map<String, Object>> showAbility(String componentId) {
			List<Map<String, Object>> findAbility;
			try {
				Map paramMap=new HashMap();
				paramMap.put("componentId", componentId);
				findAbility = sqlMapDao.queryForList("eaap-op2-portal-prov.showService", paramMap);
				if(findAbility.size()< 1){
					findAbility = null;
				}
			} catch (Exception e) {
				throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
					      new Timestamp(System.currentTimeMillis()) + "  Get ability apply to list Errors", e);
			}
			return findAbility;
		}
		@Override
		public Integer updateProdOffer(ProdOffer prodOffer){
			return sqlMapDao.update("eaap-op2-portal-prodOffer.updateProdOffer", prodOffer);
		}
		@ProvideI18NDatas(values = { 
				@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "maindId", propertyNames = "CEPNAME") 
			}
		)
		public List<MainData> selectMainData(MainData mainData) {
			return (ArrayList<MainData>)sqlMapDao.queryForList("eaap-op2-portal-prov.selectMainData", mainData) ;
			 
		}
		@Override
		public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
			return (ArrayList<MainDataType>)sqlMapDao.queryForList("eaap-op2-portal-prov.selectMainDataType", mainDataType) ;
		     
		}
		@Override
		public Integer addComponentPrice(ComponentPrice componentPrice) {
			return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addComponentPrice", componentPrice);
		}
		@Override
		public Integer addBillingDiscount(BillingDiscount billingDiscount) {
			return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addBillingDiscount", billingDiscount);
		}
		@Override
		public Integer addRatingCurveDetail(
				RatingCurverDetail ratingCurverDetail) {
			return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addRatingCurveDetail", ratingCurverDetail);
		}
		@Override
		public Integer updateComponentPrice(ComponentPrice componentPrice) {
			return sqlMapDao.update("eaap-op2-portal-componentPrice.updateComponentPrice", componentPrice);
		}
		@Override
		public Integer updateBillingDiscount(BillingDiscount billingDiscount) {
			return sqlMapDao.update("eaap-op2-portal-componentPrice.updateBillingDiscount", billingDiscount);
		}
		@Override
		public List<RatingCurverDetail> queryRatingCurveDetail(
				RatingCurverDetail ratingCurverDetail) {
			return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryRatingCurveDetail", ratingCurverDetail);
		}
		@Override
		public void delRatingCurveDetail(RatingCurverDetail r) {
			sqlMapDao.delete("eaap-op2-portal-componentPrice.delRatingCurveDetail", r);
		}
		@Override
		public List<ComponentPrice> queryComponentPrice(Map<String, Object> map) {
			return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryComponentPrice", map);
		}
		@Override
		public BillingDiscount queryBillingDiscount(
				BillingDiscount billingDiscount) {
			return (BillingDiscount) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryBillingDiscount", billingDiscount);
		}
		@Override
		public Component queryProv(Component component) {
			Component editComponent = new Component();
			try {
			editComponent = (Component) sqlMapDao.queryForObject("eaap-op2-portal-prov.selectComponent", component);
			} catch (Exception e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			return editComponent;
		}
		@Override
		public void updateProdOfferById(String componentId) {
			Map paramMap=new HashMap();
			paramMap.put("componentId", componentId);
			sqlMapDao.update("eaap-op2-portal-prov.updateProdOfferById", paramMap);
		}
		
		public List<Map<String, Object>> getProcessList(Map<String, Object> map){
			return sqlMapDao.queryForList("eaap-op2-portal-prov.getProcessList", map);
		}
		@Override
		public List<Map<String, Object>> queryTechImplAttrSpec(
				Map<String, String> hashMap) {
			return sqlMapDao.queryForList("eaap-op2-portal-prov.queryTechImplAttrSpec", hashMap);
		}
		@Override
		public void editTechImplAtt(TechImpAtt tech) {
			sqlMapDao.update("eaap-op2-portal-prov.editTechImplAtt", tech);
		}
		@Override
		public boolean getIsExit(TechImpAtt tech) {
			Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prov.exitTechImplAtt", tech);
			return num >  0;
		}
		@Override
		public void addTechImplAtt(TechImpAtt tech) {
			sqlMapDao.insert("eaap-op2-portal-prov.insertTechImpAtt", tech);
		}
 
		
		public List<Map<String, Object>> queryComponentState(Map map){
			return sqlMapDao.queryForList("eaap-op2-portal-prov.queryComponentState", map);
		}
 
		@Override
		public void updateSerTechImpl(String techImpId) {
			Map paramMap=new HashMap();
			paramMap.put("techImpId", techImpId);
			sqlMapDao.update("eaap-op2-portal-prov.updateSerTechImplState", techImpId);
		}
 
}