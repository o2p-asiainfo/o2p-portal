package com.ailk.o2p.portal.controller.apiStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.o2p.portal.bo.PageBean;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.controller.adapter.NewAdapterController;
import com.ailk.o2p.portal.service.apiStore.IApiStoreService;
import com.ailk.o2p.portal.service.productstore.IProductStoreServ;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;

/**
 * 
 * @author yangbl
 * @since 2016/08/16
 */
@Controller
@RequestMapping(value = "/apiStore")
public class ApiStoreController extends BaseController{
	
	private static final Logger log = Logger.getLog(NewAdapterController.class);
	@Autowired 
	private IApiStoreService apiStoreService;
	@Autowired
	private IProductStoreServ productStoreServ;
	@UnPermission
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
		
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			Map paraMap = new HashMap();
			paraMap.put("tenantId", this.getTenantId());
			PageBean pageBean = new PageBean();
			pageBean.setActualRowCnt(apiStoreService.queryApiOfferCnt(paraMap));
			paraMap.put("startRow", pageBean.getStartRow());
			paraMap.put("pageSize", pageBean.getPageSize());
			JSONArray treeMenu = apiStoreService.queryApiCategory(paraMap);
			List<Map> apiNewList = apiStoreService.queryNewApiList(paraMap);
			List<Map> apiList = apiStoreService.quryAllApiOfferList(paraMap);
			mv.addObject("apiNewList",apiNewList);
			mv.addObject("apiList",apiList);
			mv.addObject("treeMenu",treeMenu);
			mv.addObject("currentPage",pageBean.getCurrentPage()+1);
			mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
			mv.setViewName(tenantId +"/"+ template+"/apiStore/apiStore.htm");
		}else{
			mv.setViewName("../apiStore/apiStore.jsp");
		}
		return mv;
	}
	
	@UnPermission
	@RequestMapping(value = "/productCategory", method = RequestMethod.GET)
	@ResponseBody
	public String productCategory(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Map paraMap = new HashMap();
		paraMap.put("tenantId", this.getTenantId());
		JSONArray treeMenu = apiStoreService.queryApiCategory(paraMap);
		return treeMenu.toString();
		
	}
	
	
	@UnPermission
	@RequestMapping(value = "/apiList.shtml")
	public ModelAndView apiList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		String dirId = request.getParameter("dirId");
		String currentPage = getRequestValue(request, "currentPage");
		ModelAndView mv = new ModelAndView();
		if(this.isCms()){
			if (StringUtils.isEmpty(dirId)) {
				dirId = null;
			}
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			Map paraMap = new HashMap();
			paraMap.put("tenantId", tenantId);
			paraMap.put("dirId", dirId);
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			pageBean.setActualRowCnt(apiStoreService.queryApiOfferCnt(paraMap));
			paraMap.put("startRow", pageBean.getStartRow());
			paraMap.put("pageSize", pageBean.getPageSize());
			List<Map> apiList = apiStoreService.quryAllApiOfferList(paraMap);
			List<Map> apiNewList = apiStoreService.queryNewApiList(paraMap);
			mv.addObject("apiNewList",apiNewList);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("apiList", apiList);
			mv.addObject("currentPage",pageBean.getCurrentPage()+1);
			mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
			mv.setViewName(this.getTenantId() +"/"+ template+"/apiStore/apiList.htm");
		}else{
			mv.setViewName("../apiStore/apiList.jsp");
		}
		return mv;
	}
	
	
	@UnPermission
	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
	}
	
	
	@UnPermission
	@RequestMapping(value = "/apiStoreDetail")
	public ModelAndView apiStoreDetail(final HttpServletRequest request,
			final HttpServletResponse response){
		String prodOfferId = request.getParameter("prodOfferId");
		com.ailk.eaap.op2.bo.ProdOffer prodOffer = new ProdOffer();
		prodOffer.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
		String apiCataName = apiStoreService.quryApiCataName(prodOffer);
		String prodOfferName = request.getParameter("prodOfferName");
		String prodOfferDesc = request.getParameter("prodOfferDesc");
		String collectCnt = request.getParameter("collectCnt");
		String feeDesc = request.getParameter("feeDesc");
		String sFileId = request.getParameter("sFileId");
		String pageView = request.getParameter("pageView");
		String statusDate = request.getParameter("statusDate");
		
		
		ModelAndView mv = new ModelAndView();
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("prodOfferName",prodOfferName);
			mv.addObject("prodOfferDesc",prodOfferDesc);
			mv.addObject("collectCnt",collectCnt);
			mv.addObject("feeDesc",feeDesc);
			mv.addObject("apiCataName",apiCataName);
			mv.addObject("sFileId",sFileId);
			mv.addObject("prodOfferId",prodOfferId);
			mv.addObject("pageView",pageView);
			mv.addObject("statusDate",statusDate);
			mv.setViewName(tenantId +"/"+ template+"/apiStore/apiStoreDetail.htm");
		}
		return mv;
	}
	
	
	@UnPermission
	@RequestMapping(value = "/updateVisitor", method = RequestMethod.POST)
	@ResponseBody
	public void updateVisitor(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		String prodOfferId = request.getParameter("prodOfferId");
		String pageView = request.getParameter("pageView");
		if(this.isCms()){
			com.ailk.eaap.op2.bo.ItemCnt itemCnt = new  com.ailk.eaap.op2.bo.ItemCnt();
			itemCnt.setItemCode(prodOfferId);
			itemCnt.setPageView(Integer.valueOf(pageView)+1);
			apiStoreService.updateVisitor(itemCnt);
		}
	}
	
	
	@UnPermission
	@RequestMapping(value = "/quryApiOfferListByCategory.shtml", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView quryApiOfferListByCategory(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String dirId = request.getParameter("dirId");
		ModelAndView mv = new ModelAndView();
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			Map paraMap = new HashMap();
			paraMap.put("tenantId", tenantId);
			paraMap.put("dirId", dirId);
			List<Map> apiList = apiStoreService.quryApiOfferListByCategory(paraMap);
			List<Map> apiNewList = apiStoreService.queryNewLimitApiList(paraMap);
			mv.addObject("apiNewList",apiNewList);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("apiList", apiList);
			mv.setViewName(this.getTenantId() +"/"+ template+"/apiStore/apiList.htm");
		}else{
			mv.setViewName("../apiStore/apiList.jsp");
		}
		return mv;
	}
	
	
	@UnPermission
	@RequestMapping(value = "/quryApiOfferListMore", method = RequestMethod.GET,produces="application/json; charset=utf-8")
	@ResponseBody
	public String quryApiOfferListMore(final HttpServletRequest request,
		final HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		Map paraMap = new HashMap();
		paraMap.put("tenantId", tenantId);
		List<Map> apiList = apiStoreService.quryAllApiOfferList(paraMap);
		List<Map> apiNewList = apiStoreService.queryNewApiList(paraMap);
		jsonObject.put("apiList", apiList);
		jsonObject.put("apiNewList", apiNewList);
		return jsonObject.toString();
	}

}
