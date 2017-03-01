package com.ailk.o2p.portal.controller.orderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.OrgDao;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.WorkRestUtils;
import com.ailk.o2p.portal.service.orderManager.IOrderManagerService;

@Controller
@RequestMapping(value = "/orderManager")
public class OrderManagerController extends BaseController { 
	private Logger log = Logger.getLog(this.getClass());
	
//	private String startAcceptDate = DateUtil.convDateToString(DateUtil.getDateBefore(new Date(), -1),"yyyy-MM-dd");
//	private String endAcceptDate = DateUtil.convDateToString(new Date(), "yyyy-MM-dd");
	
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private IOrderManagerService orderManagerService;
	@Autowired
	private OrgDao baseDaoOrgDao;
	//@Autowired
	//private List<Map> selectOrderTypeList = new ArrayList<Map>();

	private static final String CRM_ORDER_TYPE = "CRM_ORDER_TYPE";
	private static final String CRMORDER_TRADE_TYPE_CODE = "CRMORDER_TRADE_TYPE_CODE";
	private static final String CRMORDER_CUST_TYPE_ID = "CRMORDER_CUST_TYPE_ID";
	private static final String CRMORDER_USER_TYPE_ID = "CRMORDER_USER_TYPE_ID";
	private static final String CRMORDER_PRODUCT_ACT_TYPE = "CRMORDER_PRODUCT_ACT_TYPE";
	private static final String CRM_ORDER_STATUS = "CRM_ORDER_STATUS";
	
	@RequestMapping(value = "/toIndex")
	public ModelAndView goOrderIndex(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.portal.orderMgr.acceptDate");
		messages.add("eaap.op2.portal.orderMgr.to");
		messages.add("eaap.op2.portal.orderMgr.orderType");
		messages.add("eaap.op2.portal.orderMgr.orderNumber");
		messages.add("eaap.op2.portal.orderMgr.query");
		messages.add("eaap.op2.portal.orderMgr.orderId");
		messages.add("eaap.op2.portal.orderMgr.crmOrderId");
		messages.add("eaap.op2.portal.orderMgr.orderNumber");
		messages.add("eaap.op2.portal.orderMgr.acceptDate");
		messages.add("eaap.op2.portal.orderMgr.countryCode");
		messages.add("eaap.op2.portal.orderMgr.locality");
		messages.add("eaap.op2.portal.orderMgr.mainServiceId");
		messages.add("eaap.op2.portal.orderMgr.tradeTypeCode");
		messages.add("eaap.op2.portal.orderMgr.originalOrderNumber");
		messages.add("eaap.op2.portal.orderMgr.bPMProcessId");
		messages.add("eaap.op2.portal.orderMgr.orderState");
		messages.add("eaap.op2.portal.orderMgr.flow");

		messages.add("eaap.op2.portal.orderMgr.orderDetail");
		messages.add("eaap.op2.portal.orderMgr.detail");
		messages.add("eaap.op2.portal.orderMgr.user");
		messages.add("eaap.op2.portal.orderMgr.address");
		messages.add("eaap.op2.portal.orderMgr.customer");
		messages.add("eaap.op2.portal.orderMgr.product");
				
		messages.add("eaap.op2.portal.orderMgr.userId");
		messages.add("eaap.op2.portal.orderMgr.userType");
		messages.add("eaap.op2.portal.orderMgr.companyName");
		messages.add("eaap.op2.portal.orderMgr.firstName");
		messages.add("eaap.op2.portal.orderMgr.middleName");
		messages.add("eaap.op2.portal.orderMgr.lastName");
		messages.add("eaap.op2.portal.orderMgr.phone");
		messages.add("eaap.op2.portal.orderMgr.userId");
		messages.add("eaap.op2.portal.orderMgr.city");
		messages.add("eaap.op2.portal.orderMgr.zipCode");
		messages.add("eaap.op2.portal.orderMgr.street");
		messages.add("eaap.op2.portal.orderMgr.building");
		messages.add("eaap.op2.portal.orderMgr.stairway");
		messages.add("eaap.op2.portal.orderMgr.floor");
		messages.add("eaap.op2.portal.orderMgr.door");
		messages.add("eaap.op2.portal.orderMgr.customerId");
		messages.add("eaap.op2.portal.orderMgr.companyName");
		messages.add("eaap.op2.portal.orderMgr.firstName");
		messages.add("eaap.op2.portal.orderMgr.middleName");
		messages.add("eaap.op2.portal.orderMgr.lastName");
		messages.add("eaap.op2.portal.orderMgr.state");
		messages.add("eaap.op2.portal.orderMgr.customerType");
		messages.add("eaap.op2.portal.orderMgr.country");
		messages.add("eaap.op2.portal.orderMgr.mainFlag");
		messages.add("eaap.op2.portal.orderMgr.productId");
		messages.add("eaap.op2.portal.orderMgr.productName");
		messages.add("eaap.op2.portal.orderMgr.actionType");

		messages.add("eaap.op2.portal.devmgr.query");
		messages.add("eaap.op2.portal.index.orderManager");
		messages.add("eaap.op2.portal.orderMgr.orderList");
		messages.add("eaap.op2.portal.orderMgr.cancel");
		addTranslateMessage(mv, messages);
		
		mv.addObject("selectOrderTypeList",mainDataServ.getMainInfo(EAAPConstants.CRM_ORDER_TYPE));	//Order Type

		mv.setViewName("../orderManager/orderManager.jsp");
		return mv;
	}
	
	private String[] getComponentCode(Integer orgId){
		List<String> s = new ArrayList<String>();
		Component component = new Component();
		component.setOrgId(orgId);
		component.setState(EAAPConstants.COMM_STATE_ONLINE);
		List<Component> components = this.baseDaoOrgDao.qryComponent(component);
		for(Component c : components){
			s.add(c.getCode());
		}
		
		return s.toArray(new String[s.size()]);
	}

	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	@ResponseBody
	public String orderList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		try{
			//获取请求次数
		    String draw = getRequestValue(request, "draw");
		    //数据起始位置
		    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
		    
			Map<String,Object> map = new HashMap<String,Object>(); 
			map.put("soType", "".equals(getRequestValue(request, "soType"))?null:getRequestValue(request, "soType"));
			map.put("soNbr", "".equals(getRequestValue(request,"soNbr"))?null:getRequestValue(request,"soNbr"));
			map.put("orderNbr", "".equals(getRequestValue(request,"orderNbr"))?null:getRequestValue(request,"orderNbr"));
			if(getRequestValue(request,"startAcceptDate")== null || "".equals(getRequestValue(request,"startAcceptDate"))){
				map.put("startAcceptDate", null);
			}else{
				map.put("startAcceptDate", getRequestValue(request,"startAcceptDate"));
			}
			if(getRequestValue(request,"endAcceptDate")== null || "".equals(getRequestValue(request,"endAcceptDate"))){
				map.put("endAcceptDate", null);
			}else{
				map.put("endAcceptDate", getRequestValue(request,"endAcceptDate"));
			}
			map.put("queryType", "ALLNUM");
			
			map.put("mainServiceId", this.getComponentCode(getOrg(request).getOrgId()));
			
			List<Map> tmpOrderList = orderManagerService.queryOrderInfoList(map);
			recordsFiltered = String.valueOf(tmpOrderList.get(0).get("ALLNUM"));
			
			map.put("pro", startRow);
	        map.put("end", startRow+rows-1);
			map.put("pro_mysql", startRow);
		    map.put("page_record", rows);
		    map.put("queryType", null);
		    tmpOrderList = orderManagerService.queryOrderInfoList(map);
		    
			JSONArray dataList=new JSONArray();
			JSONObject subData = null;
			StringBuffer sb = null;
			for(Map pardSpecMap:tmpOrderList){
				subData=new JSONObject();
				sb = new StringBuffer();
				subData.put("ORDER_INDEX","<input type='radio' value='"+pardSpecMap.get("ORDERID")+"' class='checkboxes' name='orderid' />");
				subData.put("ORDERID",pardSpecMap.get("ORDERID"));
				subData.put("ORDERNBR",null==pardSpecMap.get("ORDERNBR")?"":pardSpecMap.get("ORDERNBR"));
				subData.put("SONBR",null==pardSpecMap.get("SONBR")?"":pardSpecMap.get("SONBR"));
				subData.put("CEPNAME",null==pardSpecMap.get("CEPNAME")?"":pardSpecMap.get("CEPNAME"));
				subData.put("ACCEPTDATE",null==pardSpecMap.get("ACCEPTDATE")?"":pardSpecMap.get("ACCEPTDATE"));
				subData.put("COUNTRYCODE",null==pardSpecMap.get("COUNTRYCODE")?"":pardSpecMap.get("COUNTRYCODE"));
				subData.put("LOCALITY",null==pardSpecMap.get("LOCALITY")?"":pardSpecMap.get("LOCALITY"));
				subData.put("MAINSERVERID",null==pardSpecMap.get("MAINSERVERID")?"":pardSpecMap.get("MAINSERVERID"));
				subData.put("TRADETYPECODE",null==pardSpecMap.get("TRADETYPECODE")?"":pardSpecMap.get("TRADETYPECODE"));
				subData.put("ORDERSTATUSNAME",null==pardSpecMap.get("ORDERSTATUSNAME")?"":pardSpecMap.get("ORDERSTATUSNAME"));
				if(null==pardSpecMap.get("PROCID")){
					subData.put("SUBPROCID","");	
				}else{
					subData.put("SUBPROCID",sb.append("<a target=\"_blank\" href=\"").append(WorkRestUtils.WORK_FLOW).append("/historyService/toHistoryProcess.shtml?processInstanceId=")
							.append( pardSpecMap.get("PROCID")).append("\">View Process Image</a>").toString());	
				}
				
				
				dataList.add(subData);
			}
			JSONObject json = new JSONObject();
			json.put("draw", draw);
			json.put("recordsTotal",recordsFiltered);
			json.put("recordsFiltered", recordsFiltered);
			json.put("data",dataList.toJSONString());
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}
	

	@RequestMapping(value = "/getOrderInfoById", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderInfoById(final HttpServletRequest request,
			final HttpServletResponse response) {
		try{
			JSONObject json = new JSONObject();
			String orderId = getRequestValue(request,"orderId");

			Map paraMap = new HashMap();
			paraMap.put("orderId", orderId);
			Map returnMap = new HashMap();
			Map orderInfo = this.getOrderInfo(paraMap);
			if(orderInfo != null){
				returnMap.put("orderInfo", orderInfo);
				
				List<Map> orderUserList = this.getOrderUserList(paraMap);
				if(orderUserList != null){
					String userIds = this.getUserIds(orderUserList);
					returnMap.put("uIds", userIds);
					//获取地址
					List<Map> userAddressList = this.getUserAddressList(paraMap);
					
					returnMap.put("orderUserList", orderUserList);
					returnMap.put("userAddressList", userAddressList);
				}
				
				List<Map> orderCustomerList = this.getOrderCustomerList(paraMap);
				if(orderCustomerList != null){
					returnMap.put("orderCustomerList", orderCustomerList);
				}
				
				List<Map> orderProductList = this.getOrderProductList(paraMap);
				if(orderProductList != null){
					returnMap.put("orderProductList", orderProductList);
				}
				
				json = JSONObject.fromObject(returnMap);
			}
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}

	private List<Map> getUserAddressList(Map paraMap){
		//地址属性
		List<Map> attrSpecMapList = orderManagerService.queryAttrSpec();
		List<Map> userAddressMapList = orderManagerService.queryCrmUserAddressById(paraMap);
		Map<String,String> allAttrMap = this.getAttrMap(attrSpecMapList);
		
		List<Map> resultMapList = new ArrayList<Map>();
		Map<String,Map> addressMap = new HashMap<String,Map>();
		
		for(Map userAddressMap : userAddressMapList){
			String userId = (String)userAddressMap.get("USERID");
			if(addressMap.containsKey(userId)){
				Map map = addressMap.get(userId);
				String attrID = String.valueOf(userAddressMap.get("ATTRID"));
				//获取属性名
				String attrName = allAttrMap.get(attrID); 
				map.put(attrName.toUpperCase(), userAddressMap.get("ATTRVALUE"));
			}else{
				Map map = new HashMap();
				addressMap.put(userId, map);
				map.put("USERID", userId);
				String attrID = String.valueOf(userAddressMap.get("ATTRID"));
				//获取属性名
				String attrName = allAttrMap.get(attrID); 
				map.put(attrName.toUpperCase(), userAddressMap.get("ATTRVALUE"));
				resultMapList.add(map);
			}
		}
		return resultMapList;
	}
	
	private Map<String,String> getAttrMap(List<Map> userAttrMapList){
		Map<String,String> allAttrMap = new HashMap<String,String>();
		try{
			for(Map attrMap : userAttrMapList){
				String attrId = attrMap.get("ATTRID").toString();
				String attrName = attrMap.get("ATTRNAME") == null?"":attrMap.get("ATTRNAME").toString();
				allAttrMap.put(attrId, attrName);
			}
		}catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return allAttrMap;
	}
	
	private String getUserIds(List<Map> orderCustomerList){
		StringBuffer userIds = new StringBuffer();
		int i = 0;
		for(Map map : orderCustomerList){
			i++;
			userIds.append(map.get("UID"));
			if(i < orderCustomerList.size()-1){
				userIds.append(",");
			}
		}
		return userIds.toString();
	}
	
	
	private List<Map> getOrderProductList(Map paraMap){
		String productActType = this.getMainDateTypeCd(CRMORDER_PRODUCT_ACT_TYPE);
		paraMap.put(CRMORDER_PRODUCT_ACT_TYPE, productActType);
		return orderManagerService.queryCrmOrderProductById(paraMap); 
	}
	
	private List<Map> getOrderCustomerList(Map paraMap){
		String custTypeId = this.getMainDateTypeCd(CRMORDER_CUST_TYPE_ID);
		paraMap.put(CRMORDER_CUST_TYPE_ID, custTypeId);
		return orderManagerService.queryCrmOrderCustomerById(paraMap);
	}
	
	private List<Map> getOrderUserList(Map paraMap){
		String userTypeId = this.getMainDateTypeCd(CRMORDER_USER_TYPE_ID);
		paraMap.put(CRMORDER_USER_TYPE_ID, userTypeId);
		return orderManagerService.queryCrmOrderUserById(paraMap);
	}
	
	private Map getOrderInfo(Map paraMap){
		String crmOrderTypeCd = this.getMainDateTypeCd(CRM_ORDER_TYPE);
		String tradeTypeCode = this.getMainDateTypeCd(CRMORDER_TRADE_TYPE_CODE);
		String crmOrderStatus = this.getMainDateTypeCd(CRM_ORDER_STATUS);
		paraMap.put(CRM_ORDER_TYPE, crmOrderTypeCd);
		paraMap.put(CRMORDER_TRADE_TYPE_CODE, tradeTypeCode);
		paraMap.put(CRM_ORDER_STATUS, crmOrderStatus);
		List<Map> orderList = orderManagerService.queryCrmOrderById(paraMap);
		Map orderInfo = null;
		if(orderList.size() > 0){
			orderInfo = orderList.get(0);
		}
		return orderInfo;
	}
	
	private String getMainDateTypeCd(String mdtName){
		MainDataType mainDateType = new MainDataType();
		mainDateType.setMdtName(mdtName);
		List<MainDataType> mainDateTypeList = orderManagerService.selectMainDataType(mainDateType);
		return mainDateTypeList.get(0).getMdtCd();
	}

	
	
}
