package com.ailk.o2p.portal.controller.messageflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.messageflow.IMessageFlowService;
@Controller
public class SerTechImplController extends BaseController{

	@Autowired
	private IMessageFlowService messageFlowService;
	//分页参数
	private int start;
    private int length;
	private int total;
	private int draw;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/serviceManager/serviceSupRegister")
	public ModelAndView serviceSupRegister(final HttpServletRequest request,
			final HttpServletResponse response) {
		String objectId = this.getRequestValue(request, "objectId");
		String endpointSpecAttrId = this.getRequestValue(request, "endpoint_Spec_Attr_Id");
		String attrSpecCode = this.getRequestValue(request, "attrSpecCode");
		String orgId = this.getRequestValue(request, "orgId");
		List<Map<String,Object>> commProtocalResultList = new ArrayList<Map<String,Object>>();
		List<Map> commProtocalList = messageFlowService.queryCommProtocalListList();
		for (Map hashMap : commProtocalList) {
			Map mapTest = new HashMap(); 
			mapTest.put("value", hashMap.get("COMM_PRO_NAME"));
			mapTest.put("key", hashMap.get("COMM_PRO_CD"));
			commProtocalResultList.add(mapTest);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);//状态
		map.put("commProtocalList", commProtocalResultList);
		map.put("objectId", objectId);
		map.put("endpoint_Spec_Attr_Id", endpointSpecAttrId);
		map.put("attrSpecCode", attrSpecCode);
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.conf.server.supplier.CommunicationProtocol");
		messages.add("eaap.op2.conf.server.manager.serviceName");
		messages.add("eaap.op2.conf.server.manager.serviceCode");
		messages.add("eaap.op2.conf.server.supplier.componentCode");
		messages.add("eaap.op2.conf.server.supplier.componentName");
		messages.add("eaap.op2.conf.server.supplier.address");
		messages.add("eaap.op2.conf.server.supplier.techImplName");
		messages.add("eaap.op2.conf.server.manager.serviceSubmit");
		messages.add("eaap.op2.conf.server.manager.serviceCancel");
		messages.add("eaap.op2.conf.protocol.error.chooseRecords");
		messages.add("eaap.op2.conf.logaudit.typequery");
		ModelAndView mv = new ModelAndView("serviceSupplierResgister.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/serviceManager/showServiceSupRegisterGrid")
	@ResponseBody
	public String showServiceSupRegisterGrid(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		length = Integer.parseInt(getRequestValue(request, "length"));
		draw = Integer.parseInt(getRequestValue(request, "draw"));
		start = Integer.parseInt(getRequestValue(request, "start"));
		Map map = new HashMap() ;  
		String orgId = getRequestValue(request, "orgId");
		String serviceId = getRequestValue(request, "serviceId");
		String componentId = getRequestValue(request, "componentId");
		String commProCd = getRequestValue(request, "commProCd");
		String techImplId = getRequestValue(request, "techImplId");
		String state = getRequestValue(request, "state");
		if(null != orgId && !"".equals(orgId.trim())){
			map.put("orgId", orgId);
		}
		if(null != serviceId && !"".equals(serviceId.trim())){
			map.put("serviceId", serviceId);
		}
		if(null != componentId && !"".equals(componentId.trim())){
			map.put("componentId", componentId);
		}
		if(null != commProCd && !"".equals(commProCd.trim())){
			map.put("commProCd", commProCd);
		}
		if(null != techImplId && !"".equals(techImplId.trim())){
			map.put("techImplId", techImplId);
		}
		if(null != state && !"".equals(state.trim())){
			map.put("state", state);
		}
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		total = messageFlowService.queryServiceSupplierRegisterCount(map);
		List<Map> tmpList = messageFlowService.queryServiceSupplierRegister(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("service_cn_name", String.valueOf(itData.get("SERVICE_CN_NAME")));
				jsondata.put("service_code", String.valueOf(itData.get("SERVICE_CODE")));
				jsondata.put("code", String.valueOf(itData.get("CODE")));
				jsondata.put("name", String.valueOf(itData.get("NAME")));
				jsondata.put("state", String.valueOf(itData.get("STATE")));
				jsondata.put("create_time", String.valueOf(itData.get("CREATE_TIME")));
				jsondata.put("lastest_time", String.valueOf(itData.get("LASTEST_TIME")));
				String address = String.valueOf(itData.get("ATTR_SPEC_VALUE"));
				if(null != itData.get("ATTR_SPEC_VALUE") && !"".equals(address)){
					if(address.length()>30){
						address = address.substring(0, 29)+"...";
					}
				}
				jsondata.put("attr_spec_value", address);
				if(null !=  itData.get("COMM_PRO_CD") && !"".equals(String.valueOf(itData.get("COMM_PRO_CD")))){
					String sysStausId = String.valueOf(itData.get("COMM_PRO_CD"));
					if("1".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "http-get");
					}else if("2".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "http-post");
					}else if("3".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "http-get-post");
					}else if("4".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "sftp");
					}else if("5".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "ftp");
					}else if("6".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "webservice");
					}else if("7".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "socket");
					}else if("11".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "rest");
					}else if("12".equals(sysStausId)){
						jsondata.put("comm_pro_cd", "lkrest");
					}
				}else{
					jsondata.put("comm_pro_cd", "");
				}
				jsondata.put("tech_impl_name", String.valueOf(itData.get("TECH_IMPL_NAME")));
				jsondata.put("ser_tech_impl_id", String.valueOf(itData.get("SER_TECH_IMPL_ID")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/serviceManager/showTechnologyImpl")
	public ModelAndView showTechnologyImpl(final HttpServletRequest request,
			final HttpServletResponse response) {
		String serTechImplId = this.getRequestValue(request, "serTechImplId");
		String commCd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serTechImplId", serTechImplId);
		List<HashMap> techImplMessageModel = messageFlowService.getTechImplMessageById(serTechImplId);
		if(null != techImplMessageModel && techImplMessageModel.size()>0){
			if(null != techImplMessageModel.get(0).get("COMMPROCD")){
				commCd = techImplMessageModel.get(0).get("COMMPROCD")+"";
			}
			
			if(null != techImplMessageModel.get(0).get("ORGNAME")){
				map.put("orgName", techImplMessageModel.get(0).get("ORGNAME")+"");
			}else{
				map.put("orgName", "");
			}
			if(null != techImplMessageModel.get(0).get("COMNAME")){
				map.put("comName", techImplMessageModel.get(0).get("COMNAME")+"");
			}else{
				map.put("comName","");
			}
			if(null != techImplMessageModel.get(0).get("TECHIMPLNAME")){
				map.put("techImplName", techImplMessageModel.get(0).get("TECHIMPLNAME")+"");
			}else{
				map.put("techImplName","");
			}
			if(null != techImplMessageModel.get(0).get("COMMPRONAME")){
				map.put("commProName", techImplMessageModel.get(0).get("COMMPRONAME")+"");
			}else{
				map.put("commProName", "");
			}
			if(null != techImplMessageModel.get(0).get("SERVICE_CN_NAME")){
				map.put("serviceName", techImplMessageModel.get(0).get("SERVICE_CN_NAME")+"");
			}else{
				map.put("serviceName", "");
			}
			if(null != techImplMessageModel.get(0).get("NAME")){
				map.put("contractName", techImplMessageModel.get(0).get("NAME")+"");
			}else{
				map.put("contractName", "");
			}
		}
		if(null != commCd){
			List<HashMap> listAttrSpec = messageFlowService.getAttrSpecList(serTechImplId,commCd);
			map.put("listAttrSpec", listAttrSpec);
		}
		
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.conf.server.manager.serviceCancel");
		messages.add("eaap.op2.conf.server.supplier.subsidiaryOrgan");
		messages.add("eaap.op2.conf.server.supplier.subsidiaryComponent");
		messages.add("eaap.op2.conf.server.supplier.techcologicalRealization");
		messages.add("eaap.op2.conf.techimpl.protocol");
		messages.add("eaap.op2.conf.manager.auth.cc2cname");
		messages.add("eaap.op2.conf.manager.auth.cyclevalue");
		messages.add("eaap.op2.conf.manager.auth.cutmsvalue");
		messages.add("eaap.op2.conf.manager.auth.state");
		messages.add("eaap.op2.conf.manager.messageflow.contractName");
		messages.add("eaap.op2.conf.manager.messageflow.serviceName");
		ModelAndView mv = new ModelAndView("showTechnologyImpl.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/serviceManager/getCC2CList")
	@ResponseBody
	public String getCC2CList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		length = Integer.parseInt(getRequestValue(request, "length"));
		draw = Integer.parseInt(getRequestValue(request, "draw"));
		start = Integer.parseInt(getRequestValue(request, "start"));
		Map map = new HashMap() ;  
		String serTechImplId = getRequestValue(request, "serTechImplId");
		map.put("serTechImplId", serTechImplId);
		//mysql参数
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		total = messageFlowService.getAllTechImplRecordsById(serTechImplId);
		List<Map> tmpList = messageFlowService.getTechImplList(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("name", String.valueOf(itData.get("NAME")));
				jsondata.put("cyclevalue", String.valueOf(itData.get("CYCLEVALUE")));
				jsondata.put("cutmsvalue", String.valueOf(itData.get("CUTMSVALUE")));
				jsondata.put("effectstate", String.valueOf(itData.get("EFFECTSTATE")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}
}
