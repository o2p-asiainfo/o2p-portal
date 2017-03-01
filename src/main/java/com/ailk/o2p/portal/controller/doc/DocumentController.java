package com.ailk.o2p.portal.controller.doc;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.o2p.common.spring.config.CustomPropertyConfigurer;
import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.op2.bo.Org;
import com.ailk.eaap.op2.bo.Org;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.apiStore.IApiStoreService;
import com.ailk.o2p.portal.service.doc.IDocumentService;
import com.ailk.o2p.portal.utils.LogUtil;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

@Controller
@RequestMapping(value = "/document")
public class DocumentController extends BaseController {

	private static final Logger log = Logger.getLog(DocumentController.class);
	@Autowired
	private IDocumentService documentService;
	
	@Autowired
	private IApiStoreService apiStoreService;

	
	@UnPermission
	@RequestMapping(value = "api/apiList", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView productList(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		String prodOfferId = getRequestValue(request, "prodOfferId");
		ModelAndView mv = new ModelAndView();
		List<Map<String, String>> apiList = documentService.qryApiListByOffer(prodOfferId);
		mv.addObject("apiList", apiList);
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		mv.addObject("TENANTID", tenantId);
		mv.addObject("TEMPLATE",template);
		mv.setViewName(this.getTenantId() + "/" + template + "/productDetail.htm");
		return mv;
	}
	
	@UnPermission
	@RequestMapping(value = "/productDetail")
	public ModelAndView productDetailList(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		addTranslateMessage(mv, messages);
		String prodOfferId = getRequestValue(request, "prodOfferId");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (!StringUtil.isEmpty(prodOfferId)) {
			Map<String, String> prodOfferMap = documentService.qryProductOffer(prodOfferId);
			mv.addObject("prodOffer", prodOfferMap);
			Org org = new Org();
			org.setOrgId(Integer.valueOf(prodOfferMap.get("OFFER_PROVIDER_ID")));
			String providerName = apiStoreService.quryApiOfferProviderName(org);
			prodOfferMap.put("BRAND_ID", providerName);
			List<Map<String, Object>> articleList = documentService.queryArticleList(prodOfferId);
			mv.addObject("articleList", articleList);
			List<Map<String, String>> prodList = documentService.qryProdListByOffer(prodOfferId);
			for (Map<String, String> prodMap : prodList) {
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.putAll(prodMap);
				String productId = String.valueOf(prodMap.get("PRODUCT_ID"));
				String reqInstance = documentService.findProdReqInstance(productId);
				resultMap.put("reqInstance", reqInstance);
				String returnInstance = documentService.findProdReturnInstance(productId);
				resultMap.put("returnInstance", returnInstance);
				resultList.add(resultMap);
			}
			mv.addObject("prodList", resultList);
			List<Map<String, String>> apiList = documentService.qryApiListByOffer(prodOfferId);
			if(apiList!=null&&apiList.size()>0){
				Map<String, String> apiMap = apiList.get(0);
				String apiId = String.valueOf(apiMap.get("APIID"));
				String apiName = String.valueOf(apiMap.get("APINAME"));
				mv.addObject("apiId", apiId);
				mv.addObject("apiName", apiName);
				String reqUrl = WebPropertyUtil.getPropertyValue("serviceAgentUrl");
				// HTTP Request
				String httpQeq = documentService.findHttpQeq(apiId);
				reqUrl = reqUrl+"/http/"+apiName;
				mv.addObject("reqUrl", reqUrl);
				mv.addObject("httpQeq", httpQeq);
				// Response Support Format
				String rspSupport = documentService.findRspSupport(apiId);
				mv.addObject("rspSupport", rspSupport);
				// System Level Request Parameter
				List<Map<String, String>> sysQeqList = documentService.findSysQeqList(apiId);
				mv.addObject("sysQeqList", sysQeqList);
				// Application Level Request Parameter
				List<Map<String, String>> applyReqList = documentService.findApplyReqList(apiId);
				mv.addObject("applyReqList", applyReqList);
				// System Level Return Result
				List<Map<String, Object>> sysResult = documentService.findSysResult(apiId);
				mv.addObject("sysResult", sysResult);
				// Application Level Return Result
				List<Map<String, Object>> appResult = documentService.findAppResult(apiId);
				mv.addObject("appResult", appResult);
				// Error Code
				List<Map<String, String>> errorCode = documentService.findErrorCode(apiId);
				mv.addObject("errorCode", errorCode);
			}
		}
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		mv.addObject("TENANTID", tenantId);
		mv.addObject("TEMPLATE",template);
		mv.setViewName(tenantId + "/" + template + "/productStore/productDetail.htm");
		return mv;
	}
	
	
	@UnPermission
	@RequestMapping(value = "/apiStoreDetail")
	public ModelAndView apiStoreDetailList(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		addTranslateMessage(mv, messages);
		String prodOfferId = getRequestValue(request, "prodOfferId");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if (!StringUtil.isEmpty(prodOfferId)) {
			Map<String, String> prodOfferMap = documentService.qryApiOffer(prodOfferId);
			Org org = new Org();
			org.setOrgId(Integer.valueOf(prodOfferMap.get("OFFER_PROVIDER_ID")));
			String providerName = apiStoreService.quryApiOfferProviderName(org);
			prodOfferMap.put("BRAND_ID", providerName);
			mv.addObject("prodOffer", prodOfferMap);
			mv.addObject("pageView", prodOfferMap.get("PAGE_VIEW"));
			List<Map<String, Object>> articleList = documentService.queryArticleList(prodOfferId);
			mv.addObject("articleList", articleList);
			List<Map<String, String>> apiList = documentService.qryApiListByOffer(prodOfferId);
			for (Map<String, String> apiMap : apiList) {
				String apiId = String.valueOf(apiMap.get("APIID"));
				String apiName = String.valueOf(apiMap.get("APINAME"));
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("apiId", apiId);
				resultMap.put("apiName", apiName);
				try {
					// Request URL Address
					//对外提供的公共地址
					//String reqUrl = documentService.findReqUrl(apiId);
					/* 
						类似:http://10.1.236.72:8280/serviceAgent/http/EAAS.OTT.Wimp.createUserAndSubscription
					*/
					//String reqUrl = propertiesLoader.getProperty("serviceAgentUrl");
					String reqUrl = WebPropertyUtil.getPropertyValue("serviceAgentUrl");
					// HTTP Request
					String httpQeq = documentService.findHttpQeq(apiId);
				
					resultMap.put("httpQeq", httpQeq);
					// Response Support Format
					String rspSupport = documentService.findRspSupport(apiId);
					resultMap.put("rspSupport", rspSupport);
					// System Level Request Parameter
					List<Map<String, String>> sysQeqList = documentService.findSysQeqList(apiId);
					resultMap.put("sysQeqList", sysQeqList);
					com.ailk.eaap.op2.bo.Api api = new Api();
					api.setApiId(Integer.valueOf(apiId));
					String serviceCode = apiStoreService.querySeriveCode(api);
					reqUrl = reqUrl+"/http/"+serviceCode;
					resultMap.put("reqUrl", reqUrl);
					// Application Level Request Parameter
					List<Map<String, String>> applyReqList = documentService.findApplyReqList(apiId);
					resultMap.put("applyReqList", applyReqList);
					// Request Example
					String reqInstance = documentService.findReqInstance(apiId);
					resultMap.put("reqInstance", reqInstance);
					// System Level Return Result
					List<Map<String, Object>> sysResult = documentService.findSysResult(apiId);
					resultMap.put("sysResult", sysResult);
					// Application Level Return Result
					List<Map<String, Object>> appResult = documentService.findAppResult(apiId);
					resultMap.put("appResult", appResult);
					// Response Example
					String returnInstance = documentService.findReturnInstance(apiId);
					resultMap.put("returnInstance", returnInstance);
					// Error Code
					List<Map<String, String>> errorCode = documentService.findErrorCode(apiId);
					resultMap.put("errorCode", errorCode);
					resultList.add(resultMap);
				} catch (Exception e) {
					LogUtil.log(log, e, "apiDetailList Exception:");
				}
			}
		}
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		mv.addObject("apiList", resultList);
		mv.addObject("TENANTID", tenantId);
		mv.addObject("TEMPLATE",template);
		mv.addObject("prodOfferId",prodOfferId);
		mv.setViewName(tenantId + "/" + template + "/apiStore/apiStoreDetail.htm");
		return mv;
	}
	
	@RequestMapping(value = "/getMostPopular",method = RequestMethod.GET,produces="application/json; charset=utf-8")
	@UnPermission
	@ResponseBody
	public String getMostPopular(HttpServletRequest request){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("cooperationType", "13");
		List<Map> mostPopularApiList = documentService.qryMostPopularOffer(paraMap);
		try {
			for (Map map:mostPopularApiList) {
				Map map2 = new HashMap();
				map2.put("prodOfferId",map.get("PROD_OFFER_ID"));
				//map.put("callCnt", documentService.qryCallCntByProdOffer(map2));
				map.put("callCnt", "0");
			}
		} catch (Exception e) {
			LogUtil.log(log, e, "getMostPopular Exception:");
		}

		paraMap.put("cooperationType", "11");
		List<Map> mostPopularProductList = documentService.qryMostPopularOffer(paraMap);
		try {
			for (Map map:mostPopularProductList) {
				Map map2 = new HashMap();
				map2.put("prodOfferId",map.get("PROD_OFFER_ID"));
				//map.put("callCnt", documentService.qryCallCntByProdOffer(map2));
				map.put("callCnt", "0");
			}			
		} catch (Exception e) {
			LogUtil.log(log, e, "getMostPopular Exception:");
		}

		List<Map> partnerList = documentService.qryMostPopularPartner(paraMap);
		Integer tenantId = this.getTenantId();
		String template = TemplateUtil.getTenantTemplate(tenantId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mostPopularApiList", mostPopularApiList);
		jsonObject.put("mostPopularProductList", mostPopularProductList);
		jsonObject.put("partnerList", partnerList);
		return jsonObject.toString();
	}
}
