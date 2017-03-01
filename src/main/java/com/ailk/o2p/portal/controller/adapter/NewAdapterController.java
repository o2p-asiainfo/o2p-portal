package com.ailk.o2p.portal.controller.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.adapter.IAdapterService;
import com.ailk.o2p.portal.service.provider.IProviderService;
import com.asiainfo.foundation.log.Logger;
@Controller
public class NewAdapterController extends BaseController{

	private static final Logger log = Logger.getLog(NewAdapterController.class);
	@Autowired
	private IAdapterService adapterService;
	@Autowired
	private IProviderService providerService;
	//分页参数
	private int start;
	private int length;
	private int total;
	private int draw;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/showNewAdapter")
	public ModelAndView newAdapterPage(final HttpServletRequest request,
			final HttpServletResponse response) throws SQLException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String objectId = getRequestValue(request, "objectId");
		String endpointSpecAttrId = this.getRequestValue(request, "endpoint_Spec_Attr_Id");
		String attrSpecCode = this.getRequestValue(request, "attrSpecCode");
		map.put("objectId", objectId);
		map.put("endpoint_Spec_Attr_Id", endpointSpecAttrId);
		map.put("attrSpecCode", attrSpecCode);
		if (objectId != null && !"".equals(objectId)) {
			String[] arrayEndpoint = objectId.split("_");
			if(arrayEndpoint.length>0){
				map.put("pageEndpointId", objectId.split("_")[arrayEndpoint.length-1]);//从消息流传进来的端点ID
			}
		}else{
			map.put("pageEndpointId", 12);//从消息流传进来的端点ID
		}
		String intoOrOut = getRequestValue(request, "intoOrOut");
		map.put("intoOrOut", intoOrOut);
		String moreSource = getRequestValue(request, "moreSource");
		if(null != moreSource && !"".equals(moreSource)){
			String[] source = moreSource.split(":");
			List<Map<String,Object>> moreSourceList = new ArrayList<Map<String,Object>>();
			if(source.length>0){
				for(String finalvalue : source){
					String[] key = finalvalue.split(",");
					if(key.length>0){
						Map<String,Object> mapParam = new HashMap<String,Object>();
						mapParam.put("id",key[0]);
						mapParam.put("val","ID:"+key[0]+" Name:"+key[1]);
						moreSourceList.add(mapParam);
					}
				}
			}
			map.put("moreSourceList", moreSourceList);
		}
		Map contractTypeMap = getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_CONTRACTCON);
		map.put("contractTypeMap", contractTypeMap);
		List<Map> contractTypeList = new ArrayList<Map>() ;
		Iterator iter = contractTypeMap.entrySet().iterator();   
		  while(iter.hasNext()) {
			  Map mapTmp = new HashMap() ;
			 Entry entry = (Entry)iter.next();
			 mapTmp.put("key", entry.getKey()) ;
			 mapTmp.put("value", entry.getValue()) ;
			 contractTypeList.add(mapTmp) ;
		 }
		 map.put("contractTypeList", contractTypeList);
		 List<Map> httpTypeList = new ArrayList<Map>() ;
		 Map map1 = new HashMap(); 
		 map1.put("key", "REQ");
		 map1.put("value", "REQ");
		 Map map2 = new HashMap(); 
		 map2.put("key", "RSP");
		 map2.put("value", "RSP");
		 httpTypeList.add(map1);
		 httpTypeList.add(map2);
		 map.put("httpTypeList", httpTypeList);
		 
		 List<Map<String,Object>> metaDataMachingL = new ArrayList<Map<String,Object>>();
		 Map map3 = new HashMap();
	   	 map3.put("id","1");
	   	 map3.put("val",getI18nMessage("eaap.op2.conf.adapter.fromDb"));
	   	 metaDataMachingL.add(map3);
	   	 Map map4 = new HashMap();
	   	 map4.put("id","2");
	   	 map4.put("val",getI18nMessage("eaap.op2.conf.adapter.fromHost"));
	   	 metaDataMachingL.add(map4);
	   	 map.put("metaDataMachingL", metaDataMachingL);
	   	 
	 	//脚本配置初使化
	   	List<Map<String,Object>> scriptL = new ArrayList<Map<String,Object>>();
	   		Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("id", "2");
			m2.put("val", "js scriptExecutor");
			scriptL.add(m2);
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("id", "3");
			m3.put("val", "bsh scriptExecutor");
			scriptL.add(m3);
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("id", "4");
			m4.put("val", "Python scriptExecutor");
			scriptL.add(m4);
			Map<String, Object> m5 = new HashMap<String, Object>();
			m5.put("id", "5");
			m5.put("val", "xsl");
			scriptL.add(m5);
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("id", "6");
			m6.put("val", "xml2Json");
			scriptL.add(m6);
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("id", "7");
			m7.put("val", "json2Xml");
			scriptL.add(m7);

			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("id", "12");
			m12.put("val", "byte2String");
			scriptL.add(m12);

			Map<String, Object> m14 = new HashMap<String, Object>();
			m14.put("id", "14");
			m14.put("val", "file2String");
			scriptL.add(m14);
			
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("id", "15");
			m15.put("val", "message2File");
			scriptL.add(m15);
			map.put("scriptL", scriptL);
			String transformerRuleId = getRequestValue(request, "transformerRuleId");
			if(null != transformerRuleId && !"".equals(transformerRuleId)){//说明是查看配置
				map.put("pageContractAdapterId", transformerRuleId);
				Map contractAdapterMap = adapterService.queryContractAdapter(Integer.parseInt(transformerRuleId));//协议转化对象
				if(null != contractAdapterMap){
					if(contractAdapterMap.get("SCRIPT_SRC")!=null){
						Object scriptSrc = contractAdapterMap.get("SCRIPT_SRC");
						if (scriptSrc instanceof CLOB) {
							Reader is = ((CLOB) scriptSrc).getCharacterStream();
							BufferedReader br = new BufferedReader(is);
							String s = "";
							StringBuffer sb = new StringBuffer();
							while ((s = br.readLine())!= null) {
								sb.append(s).append("\n");
							}
							map.put("pageScriptFileText", sb.toString());
							is.close();
							br.close();
						} else {
							map.put("pageScriptFileText", new String((byte[]) contractAdapterMap.get("SCRIPT_SRC"),"UTF-8"));
						}
					}
					map.put("pageAdapterType", String.valueOf(contractAdapterMap.get("ADAPTER_TYPE")));
					if(null != contractAdapterMap.get("SRC_CTR_F_ID")){
						map.put("pageSrcTcpCtrFId", String.valueOf(contractAdapterMap.get("SRC_CTR_F_ID")));
					}
					if(null != contractAdapterMap.get("TAR_CTR_F_ID")){
						map.put("pageTarTcpCtrFId", String.valueOf(contractAdapterMap.get("TAR_CTR_F_ID")));
					}
				}
			}
		// 添加页面上需要国际化的消息
		List<String> messages = new ArrayList<String>();
		messages.add("eaap.op2.conf.adapter.sourceNode");
		messages.add("eaap.op2.conf.adapter.targetNode");
		messages.add("eaap.op2.conf.adapter.operation");
		messages.add("eaap.op2.conf.adapter.operationValue");
		messages.add("eaap.op2.conf.adapter.stillNode");
		messages.add("eaap.op2.conf.adapter.sureDelete");
		messages.add("eaap.op2.conf.adapter.moveIsExit");
		messages.add("eaap.op2.conf.adapter.updateTomove");
		messages.add("eaap.op2.conf.adapter.updateIsExit");
		messages.add("eaap.op2.conf.adapter.sureUpdate");
		messages.add("eaap.op2.conf.adapter.otherIsExit");
		messages.add("eaap.op2.conf.adapter.updateToOther");
		messages.add("eaap.op2.conf.adapter.assignIsExit");
		messages.add("eaap.op2.conf.adapter.updateToAssign");
		messages.add("eaap.op2.conf.protocol.error.dataDoerror");
		messages.add("eaap.op2.conf.protocol.RisExit");
		messages.add("eaap.op2.conf.protocol.IntoisExit");
		messages.add("eaap.op2.conf.protocol.error.notDel");
		messages.add("eaap.op2.conf.protocol.error.nodeused");
		messages.add("eaap.op2.conf.protocol.error.srcDel");
		messages.add("eaap.op2.conf.protocol.error.chooseRecords");
		messages.add("eaap.op2.conf.protocol.error.canotSelectTar");
		messages.add("eaap.op2.conf.protocol.error.addfirst");
		messages.add("eaap.op2.conf.protocol.error.keyexpnotnull");
		messages.add("eaap.op2.conf.protocol.error.canotbeempty");
		messages.add("eaap.op2.conf.protocol.error.toolong");
		messages.add("eaap.op2.conf.protocol.error.nodeBeused");
		messages.add("eaap.op2.conf.adapter.universalAdapter");
		messages.add("eaap.op2.conf.adapter.scriptAdapter");
		messages.add("eaap.op2.conf.adapter.srcContract");
		messages.add("eaap.op2.conf.remoteCallInfo.choose");
		messages.add("eaap.op2.conf.adapter.tarContract");
		messages.add("eaap.op2.conf.prov.define");
		messages.add("eaap.op2.conf.prov.close");
		messages.add("eaap.op2.conf.adapter.contractMaping");
		messages.add("eaap.op2.conf.adapter.scriptType");
		messages.add("eaap.op2.conf.adapter.scriptUpload");
		messages.add("eaap.op2.conf.adapter.contractScript");
		messages.add("eaap.op2.conf.protocol.chooseProtocol");
		messages.add("eaap.op2.conf.adapter.contractName");
		messages.add("eaap.op2.conf.adapter.contractVersion");
		messages.add("eaap.op2.conf.adapter.contractType");
		messages.add("eaap.op2.conf.adapter.httpType");
		messages.add("eaap.op2.conf.logaudit.typequery");
		messages.add("eaap.op2.conf.protocol.contractMessage");
		messages.add("eaap.op2.conf.protocol.TCP_CTR_F_ID");
		messages.add("eaap.op2.conf.protocol.msg.chooseTemplate");
		messages.add("eaap.op2.conf.adapter.srcNode");
		messages.add("eaap.op2.conf.adapter.nodeName");
		messages.add("eaap.op2.conf.adapter.nodePath");
		messages.add("eaap.op2.conf.adapter.assignmentCondition");
		messages.add("eaap.op2.conf.adapter.fixAssign");
		messages.add("eaap.op2.conf.adapter.javaAssign");
		messages.add("eaap.op2.conf.adapter.metaDataMatch");
		messages.add("eaap.op2.conf.adapter.nodeValue");
		messages.add("eaap.op2.conf.adapter.codeFrag");
		messages.add("eaap.op2.conf.adapter.varMapType");
		messages.add("eaap.op2.conf.adapter.name");
		messages.add("eaap.op2.conf.prov.submit");
		messages.add("eaap.op2.conf.prov.reSet");
		messages.add("eaap.op2.conf.protocol.variables");
		messages.add("eaap.op2.conf.protocol.Add");
		messages.add("eaap.op2.conf.protocol.Edit");
		messages.add("eaap.op2.conf.protocol.Del");
		messages.add("eaap.op2.conf.adapter.varMapingId");
		messages.add("eaap.op2.conf.adapter.dataSource");
		messages.add("eaap.op2.conf.adapter.keyExpress");
		messages.add("eaap.op2.conf.adapter.valExpress");
		messages.add("eaap.op2.conf.adapter.state");
		messages.add("eaap.op2.conf.protocol.error.Relatedendpoint");
		messages.add("eaap.op2.conf.protocol.error.endpointId");
		messages.add("eaap.op2.conf.protocol.error.dataerror");
		messages.add("eaap.op2.conf.adapter.notEmpty");
		messages.add("eaap.op2.conf.adapter.fileError");
		messages.add("eaap.op2.conf.adapter.contractScriptNotNull");
		messages.add("eaap.op2.conf.adapter.saveSuccess");
		messages.add("eaap.op2.conf.adapter.updateSuccess");
		messages.add("eaap.op2.conf.protocol.error.notsave");
		messages.add("eaap.op2.conf.protocol.error.messageFlowId");
		messages.add("eaap.op2.conf.protocol.error.mustnum");
		messages.add("eaap.op2.conf.protocol.sureToModidy");
		messages.add("eaap.op2.conf.protocol.error.canotSelectSrc");
		messages.add("eaap.op2.conf.adapter.selectOne");
		messages.add("eaap.op2.conf.adapter.cToRConversionConfiguration");
		messages.add("eaap.op2.conf.adapter.chooseValueNode");
		messages.add("eaap.op2.conf.adapter.columnToRowIsExit");
		messages.add("eaap.op2.conf.adapter.columnToRowSure");
		messages.add("eaap.op2.conf.adapter.rToCConversionConfiguration");
		messages.add("eaap.op2.conf.adapter.rowToColumnIsExit");
		messages.add("eaap.op2.conf.adapter.rowToColumnSure");
		ModelAndView mv = new ModelAndView("newAdapter.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	public Map<String,String> getMainInfo(String mdtSign){
	  	  MainDataType mainDataType = new MainDataType();
	  	  MainData mainData = new MainData();
	  	  Map<String,String> stateMap = new LinkedHashMap<String,String>() ;
	  	  mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
	  	  mainDataType.setMdtSign(mdtSign) ;
		  mainDataType = providerService.selectMainDataType(mainDataType).get(0) ;
		  mainData.setMdtCd(mainDataType.getMdtCd()) ;
		  mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE) ;
		  List<MainData> mainDataList = providerService.selectMainData(mainData) ;
		 
		  if(mainDataList!=null && mainDataList.size()>0){
			  for(int i=0;i<mainDataList.size();i++){
				  stateMap.put(mainDataList.get(i).getCepCode(),
						          mainDataList.get(i).getCepName()) ;
			  }
		  }
	  	return stateMap ;
	  }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/getContractList")
	@ResponseBody
	public String getContractList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageProtocolName = getRequestValue(request, "pageProtocolName");
		String pageContractVersion = getRequestValue(request, "pageContractVersion");
		String pageProtocolType = getRequestValue(request, "pageProtocolType");
		String pageReqRsp = getRequestValue(request, "pageReqRsp");
		String pageContractAdapterId = getRequestValue(request, "pageContractAdapterId");
		Map map = new HashMap() ;  
		if(null != pageProtocolName && !"".equals(pageProtocolName)){
			map.put("contractName", pageProtocolName);
		}
		if(null != pageContractVersion && !"".equals(pageContractVersion)){
			map.put("contractVersion", pageContractVersion);
		}
		if(null != pageProtocolType && !"".equals(pageProtocolType)){
			map.put("contractType", pageProtocolType);
		}
		if(null != pageReqRsp && !"".equals(pageReqRsp)){
			map.put("httpType", pageReqRsp);
		}
		if(null != pageContractAdapterId && !"".equals(pageContractAdapterId)){
			String pageTcpCtrFIdList =  adapterService.getSelectedLeftFormat(pageContractAdapterId);
			if(null != pageTcpCtrFIdList && !"".equals(pageTcpCtrFIdList)){
				map.put("tcpCtrFId", pageTcpCtrFIdList);
			}
		}
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		total = adapterService.getCountContractListByMap(map);
		List<Map> tmpList = adapterService.getContractListByMap(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("name", String.valueOf(itData.get("NAME")));
				jsondata.put("version", String.valueOf(itData.get("VERSION")));
				jsondata.put("contype", getShuitValue(String.valueOf(itData.get("CONTYPE"))));
				jsondata.put("contypeNum", String.valueOf(itData.get("CONTYPE")));
				jsondata.put("req_rsp", String.valueOf(itData.get("REQ_RSP")));
				jsondata.put("tcpcrtfid", String.valueOf(itData.get("TCPCTRFID")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}
	private String getShuitValue(String type){
		if(null != type && !"".equals(type) && !"null".equals(type)){
			if("1".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType1");
			}else if("2".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType2");
			}else if("3".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType3");
			}else if("4".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType4");
			}else if("5".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType5");
			}else if("6".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType6");
			}else if("10".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.contractType10");
			}
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/getVarMapTypeList")
	@ResponseBody
	public String getVarMapTypeList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapCD = getRequestValue(request, "pageConsMapCD");
		String pageConsMapName = getRequestValue(request, "pageConsMapName");

		Map map = new HashMap() ;  
		if(null != pageConsMapCD && !"".equals(pageConsMapCD)){
			map.put("consMapCD", pageConsMapCD);
		}
		if(null != pageConsMapName && !"".equals(pageConsMapName)){
			map.put("consMapName", pageConsMapName);
		}
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		total = adapterService.countVarMapType(map);
		List<Map> tmpList = adapterService.queryVarMapType(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("id", String.valueOf(itData.get("VAR_MAP_TYPE_ID")));
				jsondata.put("varMapType", String.valueOf(itData.get("CONS_MAP_CD")));
				jsondata.put("name", String.valueOf(itData.get("NAME")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/getValableMap")
	@ResponseBody
	public String getValableMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapCD = getRequestValue(request, "pageConsMapCD");
		if(null != getRequestValue(request, "length") && !"".equals(getRequestValue(request, "length"))){
			length = Integer.parseInt(getRequestValue(request, "length"));
		}
		if(null != getRequestValue(request, "draw") && !"".equals(getRequestValue(request, "draw"))){
			draw = Integer.parseInt(getRequestValue(request, "draw"));
		}
		if(null != getRequestValue(request, "start") && !"".equals(getRequestValue(request, "start"))){
			start = Integer.parseInt(getRequestValue(request, "start"));
		}

		Map map = new HashMap() ;  
		if(null != pageConsMapCD && !"".equals(pageConsMapCD)){
			map.put("consMapCD", pageConsMapCD);
		}else{
			map.put("consMapCD", "0");
		}
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		total = adapterService.getCountValableMapByMap(map);
		List<Map> tmpList = adapterService.getValableMapByMap(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("varMappingId", String.valueOf(itData.get("VAR_MAPING_ID")));
				jsondata.put("consMapCd", String.valueOf(itData.get("CONS_MAP_CD")));
				jsondata.put("dataSource", getShuitDataValue(String.valueOf(itData.get("DATA_SOURCE"))));
				jsondata.put("dataSourceNum", String.valueOf(itData.get("DATA_SOURCE")));
				jsondata.put("keyExp", String.valueOf(itData.get("KEY_EXPRESS")));
				jsondata.put("valExp", String.valueOf(itData.get("VAL_EXPRESS")));
				jsondata.put("state", String.valueOf(itData.get("STATE")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}
	private Object getShuitDataValue(String type) {
		if(null != type && !"".equals(type) && !"null".equals(type)){
			if("1".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.fromDb");
			}else if("2".equals(type)){
				return this.getI18nMessage("eaap.op2.conf.adapter.fromHost");
			}
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/loadAllData")
	@ResponseBody
	public String loadAllData(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = getRequestValue(request, "pageContractAdapterId");
		//协议转化表对象
				Map contractAdapter = adapterService.getContractAdapterById(pageContractAdapterId);
				//协议端点列表
				List<Map>  listConAdaEnd = adapterService.getConAdaEndListById(pageContractAdapterId);
				Map<String,String> mapBaseList = new HashMap<String,String>();//存储协议及基类协议ID
				JSONObject allRecords = new JSONObject();
				JSONObject nodes = new JSONObject();
				JSONObject leftOrRight = new JSONObject();
				JSONObject contract = new JSONObject();
				JSONObject table = new JSONObject();
				if(null != contractAdapter){
					String srcCtrFId = String.valueOf(contractAdapter.get("SRCCTRFID"));
					String srcType = String.valueOf(contractAdapter.get("SRCTYPE"));
					if(null != srcCtrFId && !"".equals(srcCtrFId) && !"null".equals(srcCtrFId)){//说明有值
						JSONObject leftJson = getLeftJson(srcCtrFId,srcType,"XX");
						contract.put(srcCtrFId, leftJson);
						JSONObject tableJson = getTableJson(srcCtrFId,"L");
						table.put(srcCtrFId+"L", tableJson);
						//
						Map<String,String> map = new HashMap<String,String>() ;  
						map.put("tcpCtrFId", srcCtrFId);
				       //得到基类协议的协议格式ID
						String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
						mapBaseList.put(srcCtrFId, baseContractAdaperId);
					}
					String tarCtrFId = String.valueOf(contractAdapter.get("TARCTRID"));
					String tarType = String.valueOf(contractAdapter.get("TARTYPE"));
		            if(null != tarCtrFId && !"".equals(tarCtrFId) && !"null".equals(tarCtrFId)){//说明有值
		            	JSONObject rightJson = getRightJson(tarCtrFId,tarType);
		            	leftOrRight.put("RightNode", rightJson);
		            	JSONObject tableJson = getTableJson(tarCtrFId,"R");
		            	table.put(tarCtrFId+"r", tableJson);
		            	//
						Map<String,String> map = new HashMap<String,String>() ;  
						map.put("tcpCtrFId", tarCtrFId);
				       //得到基类协议的协议格式ID
						String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
						mapBaseList.put(tarCtrFId, baseContractAdaperId);
					}
				}
				//协议端点里的协议
				if(null != listConAdaEnd && listConAdaEnd.size()>0){
					for(Map valueMap : listConAdaEnd){
						String contractFormateId = String.valueOf(valueMap.get("CONTRACT_FORMATE_ID"));
						String conType = String.valueOf(valueMap.get("CON_TYPE"));
						String actionType = String.valueOf(valueMap.get("ACTION_TYPE"));
						if(null != contractFormateId && !"".equals(contractFormateId) && !"null".equals(contractFormateId)){//说明有值
							JSONObject leftJson = getLeftJson(contractFormateId,conType,actionType);
							contract.put(contractFormateId, leftJson);
							JSONObject tableJson = getTableJson(contractFormateId,"L");
							table.put(contractFormateId+"L", tableJson);
							//
							Map<String,String> map = new HashMap<String,String>() ;  
							map.put("tcpCtrFId", contractFormateId);
					       //得到基类协议的协议格式ID
							String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
							mapBaseList.put(contractFormateId, baseContractAdaperId);
						}
					}
				}
				leftOrRight.put("LeftNode", contract);
				nodes.put("nodes", leftOrRight);
				//线操作
				List<Map> nodeMapperList = adapterService.getNodeMapperListById(pageContractAdapterId);
				if(null != nodeMapperList && nodeMapperList.size() > 0){
					JSONObject lineJson = getLineJson(nodeMapperList,mapBaseList);
					nodes.put("lines", lineJson);
				}
				nodes.put("tables", table);
				allRecords.put("all", nodes);
				log.info("Mawl Node begin:{0}", "start");
				log.info("Mawl Node log:{0}", allRecords.toString());
		return allRecords.toString();
	}
	
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/loadCToRLinesData")
	@ResponseBody
	public String loadCToRLinesData(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
		JSONObject allRecords = new JSONObject();
		String pageContractAdapterId = getRequestValue(request, "pageContractAdapterId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("tarNodeDescId", pageTarNodeDescId);
		List<Map> nodeMapperList = adapterService.getRToCLinesDataById(param);
		allRecords.put("all", nodeMapperList);
		return allRecords.toString();
	}
	
	
	/**
	 * 得到左边协议的树级关系
	 * @param contractFormateId 协议格式ID
	 * @param conType 协议格式类型
	 * @param actionType 动作类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JSONObject getLeftJson(String contractFormateId, String conType,
			String actionType) {
		Map<String,String> map = new HashMap<String,String>() ;  
		map.put("tcpCtrFId", contractFormateId);
		String pathStr = "";
		String contractFlag = "/";
		String parentNodeDescIdPath = "";
		if ("2".equals(conType)) {
			contractFlag = ".";
		    pathStr = "$";
		}
		int i = 0;
		//得到基类协议的协议格式ID
		String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
		if(null != baseContractAdaperId && !"".equals(baseContractAdaperId)){
			map.put("baseTcpFId", baseContractAdaperId);
		}

		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>() ; 
		List<Map> tmpList =adapterService.queryNodeByContractId(map) ;
//	  做递归生成node_path		
		for (Map data : tmpList) {
			if (data.get("PARENT_NODE_ID") == null )  {
				dataList = getNodeDescLevel(data, tmpList, dataList, pathStr, i, contractFlag,parentNodeDescIdPath);
			}
		}
		String contractName = adapterService.getContractNameById(contractFormateId);
		JSONObject contractLevel = new JSONObject(); 
		contractLevel.put("versionName", contractName);
		if("XX".equals(actionType)){
			contractLevel.put("flags", "false");
		}else{
			contractLevel.put("flags", actionType);
		}
		contractLevel.put("checkbox", this.getI18nMessage("eaap.op2.conf.protocol.intoprotocol"));
		contractLevel.put("actionR", this.getI18nMessage("eaap.op2.conf.protocol.RAction"));
		contractLevel.put("actionT", this.getI18nMessage("eaap.op2.conf.protocol.TAction"));
		contractLevel.put("inputId", this.getI18nMessage("eaap.op2.conf.protocol.updateMessageFlowId"));
		JSONObject nodeKey = new JSONObject(); 
		for (Map<String,String> dataMap : dataList) {
			if(!dataMap.isEmpty())
			{
				JSONObject nodeValue = new JSONObject(); //json叶子子节点
			     String name = (String)dataMap.get("name");
			     String path = (String)dataMap.get("path");
			     String nodeDescIdPath = (String)dataMap.get("nodeDescIdPath");
			     String childCount = (String)dataMap.get("childCount");
			     String dept = (String)dataMap.get("dept");
			     String nodeDescId = (String)dataMap.get("nodeDescId");
			     String nodeTypeCons = (String)dataMap.get("nodeTypeCons");
			     String nodeNumberCons = (String)dataMap.get("nodeNumberCons");
			     nodeValue.put("name", name);
			     nodeValue.put("nodeType", nodeTypeCons);
			     nodeValue.put("nodeNum", nodeNumberCons);
			     nodeValue.put("dept", dept);
			     nodeValue.put("path", path);
			     nodeValue.put("nodeDescIdPath", nodeDescIdPath);
			     nodeValue.put("childCount", childCount);
			     nodeValue.put("nodeDescId", nodeDescId);
			     nodeKey.put(contractFormateId+"L" + nodeDescId, nodeValue);
			}
		}
		contractLevel.put("nodeList", nodeKey);
		return contractLevel;
	}
	/**
	 * 得到表格数据
	 * @param srcCtrFId
	 * @param string
	 * @return
	 */
	private JSONObject getTableJson(String srcCtrFId, String action) {
		JSONObject tableJson = new JSONObject();
		String contractName = adapterService.getContractNameById(srcCtrFId);
		tableJson.put("id", srcCtrFId);
		tableJson.put("name", contractName);
		tableJson.put("action", action);
		return tableJson;
	}
	/**
	 * 得到右边协议的树级关系
	 * @param tarCtrFId  协议格式ID
	 * @param tarType协议格式类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JSONObject getRightJson(String tarCtrFId, String tarType) {
		Map<String,String> map = new HashMap<String,String>() ;  
		map.put("tcpCtrFId", tarCtrFId);
		String pathStr = "";
		String contractFlag = "/";
		String parentNodeDescIdPath = "";
		if ("2".equals(tarType)) {
			contractFlag = ".";
		    pathStr = "$";
		}
		int i = 0;
		//得到基类协议的协议格式ID
		String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
		if(null != baseContractAdaperId && !"".equals(baseContractAdaperId)){
			map.put("baseTcpFId", baseContractAdaperId);
		}

		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>() ; 
		List<Map> tmpList =adapterService.queryNodeByContractId(map) ;
//	  做递归生成node_path		
		for (Map data : tmpList) {
			if (data.get("PARENT_NODE_ID") == null )  {
				dataList = getNodeDescLevel(data, tmpList, dataList, pathStr, i, contractFlag,parentNodeDescIdPath);
			}
		}
		JSONObject rNum = new JSONObject();
		for (Map<String,String> dataMap : dataList) {
			if(!dataMap.isEmpty())
			{
				JSONObject nodeValue = new JSONObject(); //json叶子子节点
			     String name = (String)dataMap.get("name");
			     String path = (String)dataMap.get("path");
			     String nodeDescIdPath = (String)dataMap.get("nodeDescIdPath");
			     String childCount = (String)dataMap.get("childCount");
			     String dept = (String)dataMap.get("dept");
			     String nodeDescId = (String)dataMap.get("nodeDescId");
			     String nodeTypeCons = (String)dataMap.get("nodeTypeCons");
			     String nodeNumberCons = (String)dataMap.get("nodeNumberCons");
			     nodeValue.put("name", name);
			     nodeValue.put("nodeType", nodeTypeCons);
			     nodeValue.put("nodeNum", nodeNumberCons);
			     nodeValue.put("dept", dept);
			     nodeValue.put("path", path);
			     nodeValue.put("nodeDescIdPath", nodeDescIdPath);
			     nodeValue.put("childCount", childCount);
			     nodeValue.put("nodeDescId", nodeDescId);
			     rNum.put(tarCtrFId+"R" + nodeDescId, nodeValue);
			}
		}
		return rNum;
	}
	@SuppressWarnings("rawtypes")
	private List<Map<String,String>> getNodeDescLevel(Map data, List<Map> tmpList, 
			List<Map<String,String>> dataMapList, String parentPath, int i, String contractFlag, String parentNodeDescIdPath){
		String[] nodeTypeCons = {"","String","Number","Object","DATE","","","JsonArray"};
		String[] nodeNumCons = {"","[1-1]","[1-N]","[0-1]","[0-N]"};
		Integer typeCons = 0;
		Integer numCons = 0;
		Map<String, String> dataMap = new HashMap<String, String>(); 
		String path = parentPath + contractFlag + (String)data.get("NODE_NAME");
		String nodeDescIdPath = parentNodeDescIdPath + "/" + String.valueOf(data.get("NODE_DESC_ID"));
		int dept = i + 1;
		dataMap.put("name", (String)data.get("NODE_NAME"));
		dataMap.put("path", path);
		dataMap.put("nodeDescIdPath", nodeDescIdPath);
		dataMap.put("childCount", String.valueOf(data.get("CHILD_COUNT")));
		dataMap.put("dept", String.valueOf(dept));
		dataMap.put("nodeDescId", String.valueOf(data.get("NODE_DESC_ID")));
		//新增
		if( !"".equals(data.get("NODE_TYPE_CONS")) && null!=data.get("NODE_TYPE_CONS")){
			typeCons = Integer.parseInt((String) data.get("NODE_TYPE_CONS"));
		}
		if(!"".equals(data.get("NODE_NUMBER_CONS")) && null!= data.get("NODE_NUMBER_CONS")){
			numCons = Integer.parseInt((String) data.get("NODE_NUMBER_CONS"));
		}
		dataMap.put("nodeTypeCons", nodeTypeCons[typeCons]);
		dataMap.put("nodeNumberCons", nodeNumCons[numCons]);
		dataMap.put("description", (String)data.get("DESCRIPTION"));
		dataMapList.add(dataMap);
		for (Map tempMap : tmpList) {
			if (null != tempMap.get("PARENT_NODE_ID")  && !"null".equals(String.valueOf(tempMap.get("PARENT_NODE_ID")))
					&& tempMap.get("PARENT_NODE_ID").equals(data.get("NODE_DESC_ID")) 
					&& !(String.valueOf(data.get("NODE_TYPE"))).equals("8")) {
				getNodeDescLevel(tempMap,  tmpList, dataMapList, path, dept, contractFlag,nodeDescIdPath);
			}
		}
		return dataMapList;
	}
	/**
	 * 得到线数据
	 * @param srcNodeDescId源节点ID
	 * @param srcTcpId 源协议ID
	 * @param actionType 动作类型
	 * @param tarNodeDescId 目标节点ID
	 * @param tarTcpId 目标协议ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JSONObject getLineJson(List<Map> nodeMapperList,Map<String,String> mapBaseList) {
		JSONObject line = new JSONObject();
		for(Map lineMap : nodeMapperList){
			JSONObject content = new JSONObject();
			String srcNodeDescId = String.valueOf(lineMap.get("SRC_NODE_DESC_ID"));
			String srcTcpId = String.valueOf(lineMap.get("SRCTCPID"));
			String actionType = String.valueOf(lineMap.get("ACTION_TYPE_CD"));
			String tarNodeDescId = String.valueOf(lineMap.get("TAR_NODE_DESC_ID"));
			String tarTcpId = String.valueOf(lineMap.get("TARTCPID"));
			String key = "";
			if(null != srcTcpId && !"".equals(srcTcpId) && !"null".equals(srcTcpId)){
				for(Map.Entry<String, String> entry : mapBaseList.entrySet()){
					String keyCon = entry.getKey();
					String valueCon = entry.getValue();
					if(null != valueCon && !"".equals(valueCon)){
						if(valueCon.equals(srcTcpId)){
							srcTcpId = keyCon;
						}
					}
				}
			}
			if(null != tarTcpId && !"".equals(tarTcpId) && !"null".equals(tarTcpId)){
				for(Map.Entry<String, String> entry : mapBaseList.entrySet()){
					String keyCon = entry.getKey();
					String valueCon = entry.getValue();
					if(null != valueCon && !"".equals(valueCon)){
						if(valueCon.equals(tarTcpId)){
							tarTcpId = keyCon;
						}
					}
				}
			}
			content.put("type", "s1");
			if("M".equals(actionType)){
				content.put("from", srcTcpId+"L"+srcNodeDescId);
				content.put("operate", "Move");
				key=tarTcpId+"R"+tarNodeDescId+srcTcpId+"L"+srcNodeDescId;
			}else if("U".equals(actionType)){
				content.put("from", srcTcpId+"L"+srcNodeDescId);
				content.put("operate", "Update");
				key=tarTcpId+"R"+tarNodeDescId+srcTcpId+"L"+srcNodeDescId;
			}else if("A".equals(actionType)){
				content.put("from", "");
				content.put("operate", "Assign");
				key=tarTcpId+"R"+tarNodeDescId;
			}else if("R".equals(actionType)){
				content.put("from", "");
				content.put("operate", "Other");
				key=tarTcpId+"R"+tarNodeDescId;
			}else if("Z".equals(actionType)){		//纵转横
				content.put("from", srcTcpId+"L"+srcNodeDescId);
				content.put("operate", "R to C");
				key=tarTcpId+"R"+tarNodeDescId+srcTcpId+"L"+srcNodeDescId;
			}else if("H".equals(actionType)){		//横转纵
				content.put("from", srcTcpId+"L"+srcNodeDescId);
				content.put("operate", "C to R");
				key=tarTcpId+"R"+tarNodeDescId+srcTcpId+"L"+srcNodeDescId;
			}
			content.put("to", tarTcpId+"R"+tarNodeDescId);
			content.put("alt",true);
			line.put(key, content);
		}
		return line;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/getNodeDesc")
	@ResponseBody
	public String getNodeDesc(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageTcpCtrFId = this.getRequestValue(request, "pageTcpCtrFId");
		String pageContractType = this.getRequestValue(request, "pageContractType");
		String pageLoadSideFlg = this.getRequestValue(request, "pageLoadSideFlg");
		String pageContractName = this.getRequestValue(request, "pageContractName");
		String pageContractNum = this.getRequestValue(request, "pageContractNum");
		Map<String,String> map = new HashMap<String,String>() ;  
		map.put("tcpCtrFId", pageTcpCtrFId);
		String pathStr = "";
		String contractFlag = "/";
		String parentNodeDescIdPath = "";
		if ("2".equals(pageContractType)) {
			contractFlag = ".";
		    pathStr = "$";
		}
		int i = 0;
		//得到基类协议的协议格式ID
		String baseContractAdaperId  = adapterService.getTcpCtrFIdByMap(map);
		if(null != baseContractAdaperId && !"".equals(baseContractAdaperId)){
			map.put("baseTcpFId", baseContractAdaperId);
		}

		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>() ; 
		List<Map> tmpList =adapterService.queryNodeByContractId(map) ;
//	  做递归生成node_path		
		for (Map data : tmpList) {
			if (data.get("PARENT_NODE_ID") == null )  {
				dataList = getNodeDescLevel(data, tmpList, dataList, pathStr, i, contractFlag,parentNodeDescIdPath);
			}
		}
		JSONObject nodes = new JSONObject(); //json根节点：nodes
		JSONObject lRAction = new JSONObject(); //左右节点层
		JSONObject rNum = new JSONObject(); 
		JSONObject contractLevel = new JSONObject(); //协议层
		if("L".equals(pageLoadSideFlg)){
			contractLevel.put("versionName", pageContractName);
			if("0".equals(pageContractNum)){
				contractLevel.put("flags", "false");
			}else{
				contractLevel.put("flags", "T");
			}
			contractLevel.put("checkbox", this.getI18nMessage("eaap.op2.conf.protocol.intoprotocol"));
			contractLevel.put("actionR", this.getI18nMessage("eaap.op2.conf.protocol.RAction"));
			contractLevel.put("actionT", this.getI18nMessage("eaap.op2.conf.protocol.TAction"));
			contractLevel.put("inputId", this.getI18nMessage("eaap.op2.conf.protocol.updateMessageFlowId"));
			JSONObject nodeKey = new JSONObject(); 
			for (Map<String,String> dataMap : dataList) {
				if(!dataMap.isEmpty())
				{
					JSONObject nodeValue = new JSONObject(); //json叶子子节点
				     String name = (String)dataMap.get("name");
				     String path = (String)dataMap.get("path");
				     String nodeDescIdPath = (String)dataMap.get("nodeDescIdPath");
				     String childCount = (String)dataMap.get("childCount");
				     String dept = (String)dataMap.get("dept");
				     String nodeDescId = (String)dataMap.get("nodeDescId");
				     String nodeTypeCons = (String)dataMap.get("nodeTypeCons");
				     String nodeNumberCons = (String)dataMap.get("nodeNumberCons");
				     String description = (String)dataMap.get("description");
				     nodeValue.put("name", name);
				     nodeValue.put("nodeType", nodeTypeCons);
				     nodeValue.put("nodeNum", nodeNumberCons);
				     nodeValue.put("dept", dept);
				     nodeValue.put("path", path);
				     nodeValue.put("nodeDescIdPath", nodeDescIdPath);
				     nodeValue.put("childCount", childCount);
				     nodeValue.put("nodeDescId", nodeDescId);
				     nodeValue.put("description", description);
				     nodeKey.put(pageTcpCtrFId+pageLoadSideFlg + nodeDescId, nodeValue);
				}
			}
			contractLevel.put("nodeList", nodeKey);
			rNum.put(pageTcpCtrFId, contractLevel);
			lRAction.put("LeftNode", rNum);
		}else{//右节点
			for (Map<String,String> dataMap : dataList) {
				if(!dataMap.isEmpty())
				{
					JSONObject nodeValue = new JSONObject(); //json叶子子节点
				     String name = (String)dataMap.get("name");
				     String path = (String)dataMap.get("path");
				     String nodeDescIdPath = (String)dataMap.get("nodeDescIdPath");
				     String childCount = (String)dataMap.get("childCount");
				     String dept = (String)dataMap.get("dept");
				     String nodeDescId = (String)dataMap.get("nodeDescId");
				     String nodeTypeCons = (String)dataMap.get("nodeTypeCons");
				     String nodeNumberCons = (String)dataMap.get("nodeNumberCons");
				     String description = (String)dataMap.get("description");
				     nodeValue.put("name", name);
				     nodeValue.put("nodeType", nodeTypeCons);
				     nodeValue.put("nodeNum", nodeNumberCons);
				     nodeValue.put("dept", dept);
				     nodeValue.put("path", path);
				     nodeValue.put("nodeDescIdPath", nodeDescIdPath);
				     nodeValue.put("childCount", childCount);
				     nodeValue.put("nodeDescId", nodeDescId);
				     nodeValue.put("description", description);
				     rNum.put(pageTcpCtrFId+pageLoadSideFlg + nodeDescId, nodeValue);
				}
			}
			lRAction.put("RightNode", rNum);
		}
		nodes.put("nodes", lRAction);
		log.info("Mawl Node begin:{0}", "start");
		log.info("Mawl Node log:{0}", nodes.toString());
		return nodes.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/saveAdapterConfig")
	@ResponseBody
	public String saveAdapterConfig(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String tvname = this.getRequestValue(request, "tvname");
		String pageTarTcpCtrFId = this.getRequestValue(request, "pageTarTcpCtrFId");
		String type = this.getRequestValue(request, "type");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageSrcTcpCtrFId = this.getRequestValue(request, "pageSrcTcpCtrFId");
//		String pageEndpointId = this.getRequestValue(request, "pageEndpointId");
		Map paramMap = new HashMap();
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		String adapter_name = getI18nMessage("eaap.op2.conf.adapter.contractAdapter")+tvname;
		if(null != pageTarTcpCtrFId && !"".equals(pageTarTcpCtrFId)){
			paramMap.put("tid", pageTarTcpCtrFId);
		}
//		if(null != pageSrcTcpCtrFId && !"".equals(pageSrcTcpCtrFId)){
//			paramMap.put("sid", pageSrcTcpCtrFId);
//		}
		paramMap.put("type", type);
		paramMap.put("adapter_name", adapter_name);
		paramMap.put("state", "S");
		if(null != pageContractAdapterId && !"".equals(pageContractAdapterId)){//说明有适配ID存在
			Map<String,String> map = new HashMap<String,String>();
			if(null != pageTarTcpCtrFId && !"".equals(pageTarTcpCtrFId)){
				map.put("tarTcpCtrFId", pageTarTcpCtrFId);
			}
			if(null != pageSrcTcpCtrFId && !"".equals(pageSrcTcpCtrFId)){
				map.put("srcTcpCtrFId", pageSrcTcpCtrFId);
			}
			map.put("adapterName", adapter_name);
			map.put("contractAdapterId", pageContractAdapterId+"");
			adapterService.updateContractAdapter(map);//修改协议转化规则
		}else{
			pageContractAdapterId = adapterService.addContractAdapter(paramMap)+"";//添加协议转化规则
		}
		//添加协议端点记录
		if(null != pageSrcTcpCtrFId && !"".equals(pageSrcTcpCtrFId)){
			String conAdaEndId = adapterService.getConAdaEndId();//得到协议端点表ID
			Map contractEndpoint = new HashMap();
			contractEndpoint.put("conAdaEndId", conAdaEndId);
			contractEndpoint.put("contractAdapterId", pageContractAdapterId);
			contractEndpoint.put("endPointId", "1");
			contractEndpoint.put("srcTcpCtrFId", pageSrcTcpCtrFId);
			contractEndpoint.put("actionType", "T");
			adapterService.addConAdaEnd(contractEndpoint);
		}
		retMap.put("pageContractAdapterId",pageContractAdapterId);
		json.putAll(retMap);
		return json.toString();
	}
	@RequestMapping(value = "/newadapter/delContractRecords")
	@ResponseBody
	public String operatorApility(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageTarTcpCtrFId = this.getRequestValue(request, "pageTarTcpCtrFId");
		String pageEndpointId = this.getRequestValue(request, "pageEndpointId");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("tcpCrtFId", pageTarTcpCtrFId);
		param.put("endpointId", pageEndpointId);
		adapterService.updateContractRecords(param);//去掉协议转换表中的源节点记录(如果存在的话)
		adapterService.delConAdaEndByMap(param);//去掉协议端点里面的记录(如果存在的话)
		JSONObject json = new JSONObject();
		return json.toString();
	}


	@RequestMapping(value = "/newadapter/isExitOperator")
	@ResponseBody
	public String isExitOperator(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String operator = this.getRequestValue(request, "operator");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		String result = adapterService.isExitOperator(operator,pageContractAdapterId);
		retMap.put("result",result);
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/isExitLine")
	@ResponseBody
	public String isExitLine(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String operator = this.getRequestValue(request, "operator");
		String pageSrcNodeDescId = this.getRequestValue(request, "pageSrcNodeDescId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("operator", operator);
		if(null != pageSrcNodeDescId && !"".equals(pageSrcNodeDescId)){
			param.put("srcNodeDescId", pageSrcNodeDescId);
		}
		param.put("tarNodeDescId", pageTarNodeDescId);
		String result = adapterService.isExitLine(param);
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		retMap.put("result",result);
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/getActionValue")
	@ResponseBody
	public String getActionValue(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageSrcTcpCtrFId = this.getRequestValue(request, "pageSrcTcpCtrFId");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("contractFormatId", pageSrcTcpCtrFId);
		String result = adapterService.getActionValue(param);
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		retMap.put("result",result);
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/changeToAction")
	@ResponseBody
	public String changeToAction(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String operator = this.getRequestValue(request, "operator");
		String pageSrcTcpCtrFId = this.getRequestValue(request, "pageSrcTcpCtrFId");
		String pageEndpointId = this.getRequestValue(request, "pageEndpointId");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("operator", operator);
		param.put("srcTcpCtrFId", pageSrcTcpCtrFId);
		param.put("endpointId", pageEndpointId);
		String result = adapterService.changeToAction(param);
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		retMap.put("result",result);
		json.putAll(retMap);
		return json.toString();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/getNodeValAdaReq")
	@ResponseBody
	public String getNodeValAdaReq(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId",pageContractAdapterId);
		param.put("tarNodeDescId", pageTarNodeDescId);
		List<Map> resultList = adapterService.getNodeValAdaReq(param);
		if(null != resultList && resultList.size() > 0){
			retMap.put("result", "HAVE_DATA");
			retMap.put("nodeReqId", String.valueOf(resultList.get(0).get("NODE_VAL_ADAPTER_REQ_ID")));
			retMap.put("valueWay",String.valueOf(resultList.get(0).get("NODE_VALUE_SOURCE_CD")));
			retMap.put("valueExp",String.valueOf(resultList.get(0).get("VALUE_EXPRESS")));
			retMap.put("valueScript",String.valueOf(resultList.get(0).get("SCRIPT")));
			retMap.put("triggerExp",String.valueOf(resultList.get(0).get("TRIGGER_EXPRESS")));
		}else{
			retMap.put("result", "NO_DATA");
		}
		JSONObject json = new JSONObject();
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/addNodeValAdapterRes")
	@ResponseBody
	public String addNodeValAdapterRes(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		String type = this.getRequestValue(request, "type");
		String pageNodeValue = this.getRequestValue(request, "pageNodeValue");
		String pageScript = this.getRequestValue(request, "pageScript");
		String pageCondition = this.getRequestValue(request, "pageCondition");
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractAdapterId", pageContractAdapterId);
		param.put("nodeDescId", pageTarNodeDescId);
		param.put("nodeValueSourceCd",type);
		if(null != pageNodeValue && !"".equals(pageNodeValue)){
			param.put("valueExpress", pageNodeValue);
		}
		if(null != pageScript && !"".equals(pageScript)){
			param.put("script", pageScript);
		}
		param.put("triggerExpress", pageCondition);
		String result = adapterService.addNodeValAdapterRes(param);
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		retMap.put("result",result);
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/addVarMapType")
	@ResponseBody
	public String addVarMapType(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapName = this.getRequestValue(request, "pageConsMapName");
		String pageConsMapCD = this.getRequestValue(request, "pageConsMapCD");
		Org org = (Org) request.getSession().getAttribute("userBean");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("name", pageConsMapName);
		param.put("consMapCD", pageConsMapCD);
		param.put("orgId", String.valueOf(org.getOrgId())); 
		int cnt = adapterService.isVarMapTypeExit(param);
		if(0==cnt){
			Integer id = adapterService.saveVarMapType(param);
			retMap.put("result", "success");
			retMap.put("errMsg", id+"");
		}else{
			retMap.put("result", "error");
			retMap.put("errMsg", getI18nMessage("eaap.op2.conf.adapter.varMapTypeExist"));
		}
		JSONObject json = new JSONObject();
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/getVarMapTypeName")
	@ResponseBody
	public String getVarMapTypeName(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapCD = this.getRequestValue(request, "pageConsMapCD");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("consMapCD", pageConsMapCD);
		String result = adapterService.getVarMapTypeName(param);
		JSONObject json = new JSONObject();
		retMap.put("result", result);
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/addVariableMap")
	@ResponseBody
	public String addVariableMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapCD = this.getRequestValue(request, "pageConsMapCD");
		String pageDataSource = this.getRequestValue(request, "pageDataSource");
		String pageKeyExp = this.getRequestValue(request, "pageKeyExp");
		String pageValExp = this.getRequestValue(request, "pageValExp");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Org org = (Org) request.getSession().getAttribute("userBean");
		
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("consMapCD", pageConsMapCD);
		param.put("dataSource", pageDataSource);
		param.put("keyExpress", pageKeyExp);
		param.put("valExpress", pageValExp);
		param.put("contractAdapterId",pageContractAdapterId);
		param.put("orgId", String.valueOf(org.getOrgId())); 
		Integer count = adapterService.getCountVarMayByCode(param);
		JSONObject json = new JSONObject();
		if(count != null && count > 0){
			adapterService.saveVariableMap(param);
			retMap.put("result", "success");
			json.putAll(retMap);
		}else{
			retMap.put("result", "fail");
			retMap.put("desc", getI18nMessage("eaap.op2.conf.adapter.addPermission"));
			json.putAll(retMap);
		}
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/updateVariableMap")
	@ResponseBody
	public String updateVariableMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageConsMapCD = this.getRequestValue(request, "pageConsMapCD");
		String pageVarMappingId = this.getRequestValue(request, "pageVarMappingId");
		String pageDataSource = this.getRequestValue(request, "pageDataSource");
		String pageKeyExp = this.getRequestValue(request, "pageKeyExp");
		String pageValExp = this.getRequestValue(request, "pageValExp");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Org org = (Org) request.getSession().getAttribute("userBean");
		
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("varMapingId", pageVarMappingId);
		param.put("consMapCD", pageConsMapCD);
		param.put("dataSource", pageDataSource);
		param.put("keyExpress", pageKeyExp);
		param.put("valExpress", pageValExp);
		param.put("contractAdapterId",pageContractAdapterId);
		param.put("orgId", String.valueOf(org.getOrgId())); 
		
		Integer count = adapterService.getCountVarMayByCode(param);
		JSONObject json = new JSONObject();
		if(count != null && count > 0){
			adapterService.updateVariableMap(param);
			retMap.put("result", "success");
			json.putAll(retMap);
		}else{
			retMap.put("result", "fail");
			retMap.put("desc", getI18nMessage("eaap.op2.conf.adapter.updataPermission"));
			json.putAll(retMap);
		}
		return json.toString();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/showMessageFlowId")
	@ResponseBody
	public String showMessageFlowId(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageSrcTcpCtrFId = this.getRequestValue(request, "pageSrcTcpCtrFId");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractFormatId", pageSrcTcpCtrFId);
		param.put("contractAdapterId",pageContractAdapterId);
		List<Map> list = adapterService.getContractFormat(param);
		String messageFlowId =  "";
		if(null !=  list && list.size()>0){
			if(null != list.get(0).get("ENDPOINT_ID")){
				messageFlowId = String.valueOf(list.get(0).get("ENDPOINT_ID"));
			}
		}
		JSONObject json = new JSONObject();
		retMap.put("messageFlowId", messageFlowId);
		json.putAll(retMap);
		return json.toString();
	}
	@RequestMapping(value = "/newadapter/updateResult")
	@ResponseBody
	public String updateResult(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageEndpointId = this.getRequestValue(request, "pageEndpointId");
		String pageSrcTcpCtrFId = this.getRequestValue(request, "pageSrcTcpCtrFId");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("endPointId", pageEndpointId);
		param.put("contractFormatId", pageSrcTcpCtrFId);
		param.put("contractAdapterId",pageContractAdapterId);
		adapterService.updateResult(param);
		JSONObject json = new JSONObject();
		retMap.put("result", "success");
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/deleteVarMapType")
	@ResponseBody
	public String deleteVarMapType(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Org org = (Org) request.getSession().getAttribute("userBean");
		String pageVarMapTypeId = this.getRequestValue(request, "pageVarMapTypeId");
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("varMapTypeId", pageVarMapTypeId);
		param.put("orgId", String.valueOf(org.getOrgId()));  
		Integer count = adapterService.getCountVarMay(param);
		JSONObject json = new JSONObject();
		if(count != null && count > 0){
			adapterService.deleteVarMapType(param);
			retMap.put("result", "success");
			json.putAll(retMap);
		}else{
			retMap.put("result", "fail");
			retMap.put("desc", getI18nMessage("eaap.op2.conf.adapter.deletePermission"));
			json.putAll(retMap);
		}
		return json.toString();
	}
	//
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/newadapter/isOrNotSubmit")
	@ResponseBody
	public String isOrNotSubmit(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map<String,String> retMap = new HashMap<String,String>();
		JSONObject json = new JSONObject();
		boolean flag = false;
		Map contractAdapter = adapterService.getContractAdapterById(pageContractAdapterId);
		if(null != contractAdapter){
			String srcCtrFId = String.valueOf(contractAdapter.get("SRCCTRFID"));
			String tarCtrFId = String.valueOf(contractAdapter.get("TARCTRID"));
			if(null !=srcCtrFId && !"".equals(srcCtrFId) &&!"null".equals(srcCtrFId) && !"0".equals(srcCtrFId)
					&& null !=tarCtrFId && !"".equals(tarCtrFId) &&!"null".equals(tarCtrFId) && !"0".equals(tarCtrFId)){
				flag = true;
			}
		}
		if(flag){
			retMap.put("result", "success");
		}else{
			retMap.put("result", "fail");
		}
		json.putAll(retMap);
		return json.toString();
	}

	@RequestMapping(value = "/newadapter/saveScriptAdapter")
	@ResponseBody
	public String saveScriptAdapter(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageAdapterType = this.getRequestValue(request, "pageAdapterType");
		String pageScriptFileText = this.getRequestValue(request, "pageScriptFileText");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		JSONObject json = new JSONObject();
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		param.put("adapterType", pageAdapterType);
		param.put("scriptSrc", pageScriptFileText);
		param.put("state", "A");
		if (pageContractAdapterId!=null && !"".equals(pageContractAdapterId)) {
			param.put("srcTcpCtrFId", "0");
			param.put("tarTcpCtrFId", "0");
			param.put("contractAdapterId", pageContractAdapterId);
			adapterService.updateContractAdapter(param);
			adapterService.delNodeMaper(param);//删除节点映射信息
			adapterService.delAdapterReq(param);//删除节点取值要求信息
			adapterService.delAdapterEndpoint(pageContractAdapterId);//删除协议端点信息
		    retMap.put("msg", "update");
		    retMap.put("contractAdapterId", pageContractAdapterId);
		   } else {
			   pageContractAdapterId = adapterService.insertContractAdapter(param)+"";
			   retMap.put("msg", "add");
			   retMap.put("contractAdapterId", pageContractAdapterId);
		   }
		json.putAll(retMap);
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/delVariableMap")
	@ResponseBody
	public String delVariableMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageVarMappingId = this.getRequestValue(request, "pageVarMappingId");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		String pageConsMapCD = this.getRequestValue(request, "pageConsMapCD");
		Org org = (Org) request.getSession().getAttribute("userBean");
		Map map = new HashMap();
		map.put("contractAdapterId",pageContractAdapterId);
		map.put("varMapingId", pageVarMappingId);
		map.put("consMapCD", pageConsMapCD);
		map.put("orgId", String.valueOf(org.getOrgId()));  
		Integer count = adapterService.getCountVarMay(map);
		JSONObject json = new JSONObject();
		if(count != null && count > 0){
			adapterService.delVariableMap(Integer.parseInt(pageVarMappingId));
			adapterService.updateTransScript(map);
			json.put("result", "success");
		}else{
			json.put("result", "fail");
			json.put("desc", getI18nMessage("eaap.op2.conf.adapter.deletePermission"));
		}
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/saveNodeDescMap")
	@ResponseBody
	public String saveNodeDescMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageSrcNodeDescId = this.getRequestValue(request, "pageSrcNodeDescId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		String operator = this.getRequestValue(request, "operator");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map paramMap = new HashMap();
		if(null!=pageSrcNodeDescId&& !"".equals(pageSrcNodeDescId)){
			paramMap.put("src_node_desc_id", pageSrcNodeDescId);
		}
			paramMap.put("tar_node_desc_id", pageTarNodeDescId);
			paramMap.put("action_type_cd", operator);
			paramMap.put("contract_adapter_id", pageContractAdapterId);
		if(null!=pageContractAdapterId){
			if(adapterService.isNodeMapDecExit(paramMap)){
				adapterService.addNodeDescMap(paramMap);
			}else{
				adapterService.updateNodeDescMap(paramMap);
			}
		}
		JSONObject json = new JSONObject();
		json.put("result", "success");
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/delNodeDescMap")
	@ResponseBody
	public String delNodeDescMap(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pageSrcNodeDescId = this.getRequestValue(request, "pageSrcNodeDescId");
		String pageTarNodeDescId = this.getRequestValue(request, "pageTarNodeDescId");
		String pageContractAdapterId = this.getRequestValue(request, "pageContractAdapterId");
		Map map = new HashMap();
		if(!"".equals(pageSrcNodeDescId)&&null!=pageSrcNodeDescId){
			map.put("sid", pageSrcNodeDescId);
		}
		map.put("nodeDescId", pageTarNodeDescId);
		map.put("contractAdapterId", pageContractAdapterId);
		adapterService.delNodeDescMap(map);
		adapterService.delNodeValAdapterRea(map);//删除节点取值要求
		JSONObject json = new JSONObject();
		json.put("result", "success");
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/newadapter/saveUniversalAdapter")
	@ResponseBody
	public String saveUniversalAdapter(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String transformerRuleId = this.getRequestValue(request, "transformerRuleId");
		if (null != transformerRuleId && !"".equals(transformerRuleId)) {
			   Map map = new HashMap();
			   map.put("state", "A");
			   map.put("contractAdapterId", transformerRuleId);
			   adapterService.updateContractAdapter(map);
		   } 
		JSONObject json = new JSONObject();
		json.put("msg", "save");
		return json.toString();
	}
}
