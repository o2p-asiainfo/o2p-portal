package com.ailk.o2p.portal.controller.doc;

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

import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.WebConstants;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.doc.IApiDocumentService;
import com.ailk.o2p.portal.service.login.ILoginService;
import com.ailk.o2p.portal.service.support.ISupportService;
import com.ailk.o2p.portal.utils.LogUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: ApiDocumentController
 * @Description: 
 * @author zhengpeng
 * @date 2015-5-29 上午10:59:58
 *
 */
@Controller
public class ApiDocumentController extends BaseController{
	
	private static final Logger log = Logger.getLog(ApiDocumentController.class);
	@Autowired
	private IApiDocumentService apiDocumentService;
	@Autowired
	private ISupportService supportService;
	@Autowired
	private ILoginService loginService;
	
	@RequestMapping(value = "/doc/querySucessCaseIndexList", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String querySucessCaseIndexList(final HttpServletRequest request,
			final HttpServletResponse response){
		List<Map> successCaseList = apiDocumentService.querySuccessCaseIndexList();
		return JSON.toJSONString(successCaseList);
		 
	}
	
	@RequestMapping(value = "/doc/successCaseDetails", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String successCaseDetails(final HttpServletRequest request,
			final HttpServletResponse response){	
		String orgId = getRequestValue(request, "orgId");
		List<Map> successCaseList = apiDocumentService.successCaseDetails(orgId);
		Org org = new Org();
		org.setOrgId(Integer.parseInt(orgId));
		org = apiDocumentService.getOrg(org);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("successCaseList", successCaseList);
		result.put("org", org);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/doc/qryDirList", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String qryDirList(final HttpServletRequest request,
			final HttpServletResponse response){
		List<Map<String, String>> apiDocList = apiDocumentService.searchDirList(EAAPConstants.APIDOCDIRID);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("dirList", apiDocList);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/doc/qrySecondDirList", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String qrySecondDirList(final HttpServletRequest request,
			final HttpServletResponse response){
		String dirId = getRequestValue(request, "dirId");
		Map<String,Object> result = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(dirId)){
			List<Map<String, String>> apiDocNexList = apiDocumentService.searchDirNexList(dirId);
			result.put("secondDirList", apiDocNexList);
		}else{
			List<Map<String, String>> apiDocApiList = apiDocumentService.searchDirApiList();
			result.put("secondDirList", apiDocApiList);
			if(apiDocApiList.size() > 0){
				Map<String, String> apiDocMap = apiDocApiList.get(0);
				String nexDirID = String.valueOf(apiDocMap.get("DIRID")); 
				List<Map<String, String>> apiDocThirdList = apiDocumentService.searchDirThirdList(nexDirID);
				result.put("thirdDirList", apiDocThirdList); 
			}
		}
		return JSON.toJSONString(result);
	}
	
	
	
	@RequestMapping(value = "/doc/qryDirContentList", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String qryDirContentList(final HttpServletRequest request,
			final HttpServletResponse response){
		String dirId = getRequestValue(request, "dirId");
		Map<String,Object> result = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(dirId)){
			List<Map<String, String>> findPlatformView = apiDocumentService.showPlatformList(dirId);
			result.put("dirContentList", findPlatformView);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/doc/qryAllApiList")
	@ResponseBody
	@UnPermission
	public String qryAllApiList(final HttpServletRequest request,
			final HttpServletResponse response) {
		// 获取请求次数
		String draw = getRequestValue(request, "draw");
		// 数据起始位置
		int startRow = "".equals(getRequestValue(request, "start")) ? 1 : Integer.parseInt(getRequestValue(request, "start"));
		// 数据长度
		int rows = "".equals(getRequestValue(request, "length")) ? 10 : Integer.parseInt(getRequestValue(request, "length"));
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pro_mysql", startRow);
		paramMap.put("page_record", rows);
		
		String searchValue = getRequestValue(request, "search[value]");
		String dirId = getRequestValue(request, "dirId");
		String type = getRequestValue(request, "type");
		String[] dirIdArray = null;
		if(!"cApi".equals(type)){
			List<Map<String, String>> apiDocThirdList = apiDocumentService.searchDirThirdList(dirId);
			dirIdArray = new String[apiDocThirdList.size()];
			int i = 0;
			for(Map<String,String> apiDoc : apiDocThirdList){
				dirIdArray[i] = String.valueOf(apiDoc.get("DIRID")); 
				i++;
			}
		}else{
			dirIdArray = new String[1];
			dirIdArray[0] = dirId;
		}
		paramMap.put("DirId", dirIdArray);  
		
		if(!StringUtil.isEmpty(searchValue)){
			paramMap.put("searchValue", searchValue); 
		}
		int recordsTotal = 0;
		List<Map<String, String>> allApiList = new ArrayList<Map<String, String>>();
		if(dirIdArray.length > 0){
			//总记录数
		    recordsTotal = apiDocumentService.queryAllApiCount(paramMap);
	
			allApiList = apiDocumentService.queryAllApiDocument(paramMap);
		}
		
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal",recordsTotal);
		json.put("recordsFiltered", recordsTotal);
		json.put("data",JSON.toJSONString(allApiList,new ValueFilter() {
		    @Override
		    public Object process(Object obj, String s, Object v) {
			    if(v==null)
			        return "";
			    return v;
		    }
		}));
		return json.toString();
	}
	

	@RequestMapping(value = "/support/qrySupportList", method = RequestMethod.POST)
	@ResponseBody
	@UnPermission
	public String qrySupportList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String, String>> supportList = supportService.showProblemList(EAAPConstants.SUPPORTPROBLEM);
		result.put("supportList", supportList);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "api/apiDoc")
	@UnPermission
	public ModelAndView apiDetailList(HttpServletRequest request) {	
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		addTranslateMessage(mv, messages);
		String serviceId = getRequestValue(request, "serviceId");
		String apiId = getRequestValue(request, "apiId");
		String apiName = getRequestValue(request, "apiName");
		if(!StringUtil.isEmpty(serviceId)){
			Map<String,Object> apiMap = apiDocumentService.qryApiByService(serviceId);
			apiId = String.valueOf(apiMap.get("APIID"));
			apiName = String.valueOf(apiMap.get("APINAME")); 
		}
		mv.addObject("apiName", apiName); 
		
		//Request URL Address
		String url = WebPropertyUtil.getPropertyValue("serviceAgentUrl");
		String tenantId = null;
		UserRoleInfo userRoleInfo = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
		if(userRoleInfo != null && userRoleInfo.getTenantId() != null){
			tenantId= userRoleInfo.getTenantId().toString();
		}else{
			StringBuffer urlStr = request.getRequestURL();
			String contextUrl = urlStr.delete(urlStr.length() - request.getRequestURI().length(), urlStr.length()).toString();
			int startIndex = 0;
			String indexStr = "://";
			if(contextUrl.indexOf(indexStr) > 0){
				startIndex = contextUrl.indexOf(indexStr)+indexStr.length();
			}
			if(contextUrl.indexOf(".") > -1){
				contextUrl = contextUrl.substring(startIndex, contextUrl.indexOf("."));
			}else{
				contextUrl = contextUrl.substring(startIndex, contextUrl.lastIndexOf(":"));  
			}
			String tenantCode = contextUrl;
			tenantId=loginService.queryTenantIdByCode(tenantCode);
		}
		String reqUrl = url+"/"+tenantId+"/"+"http"+"/"+apiName;
		mv.addObject("reqUrl", reqUrl);
		
		try{
			//Capability Description
			String abilityIntro = apiDocumentService.findAbilityIntro(apiId);
			mv.addObject("abilityIntro", abilityIntro);
			//API User Authorization
			String apiUserGetType = apiDocumentService.findApiUserGetType(apiId);
			mv.addObject("apiUserGetType", apiUserGetType);
			//HTTP Request
			String httpQeq = apiDocumentService.findHttpQeq(apiId);
			mv.addObject("httpQeq", httpQeq);
			//Response Support Format
			String rspSupport = apiDocumentService.findRspSupport(apiId);
			mv.addObject("rspSupport", rspSupport); 
			//System Level Request Parameter
			List<Map<String, String>> sysQeqList = apiDocumentService.findSysQeqList(apiId);
			mv.addObject("sysQeqList", sysQeqList);  
			//Application Level Request Parameter
			List<Map<String, String>> applyReqList = apiDocumentService.findApplyReqList(apiId);
			mv.addObject("applyReqList", applyReqList);  
			//Request Example
			String reqInstance = apiDocumentService.findReqInstance(apiId);
			mv.addObject("reqInstance", reqInstance); 
			//System Level Return Result
			List<Map<String, Object>> sysResult = apiDocumentService.findSysResult(apiId);
			mv.addObject("sysResult", sysResult);
			//Application Level Return Result
			List<Map<String, Object>> appResult = apiDocumentService.findAppResult(apiId);
			mv.addObject("appResult", appResult); 
			//Response Example
			String returnInstance = apiDocumentService.findReturnInstance(apiId);
			mv.addObject("returnInstance", returnInstance);  
			//Error Code
			List<Map<String, String>> errorCode = apiDocumentService.findErrorCode(apiId);	
			mv.addObject("errorCode", errorCode);  
		}catch (Exception e) {
			LogUtil.log(log, e, "apiDetailList Exception:");
		}
		request.getSession().setAttribute("navBarPageId", "Doc");
		mv.setViewName("../doc/apiDoc.jsp");
		return mv;
	}
	
	@RequestMapping(value = "api/documention")
	@UnPermission
	public ModelAndView toDocumention(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Doc");
		mv.setViewName("../doc/doc.jsp");
		return mv;
	}
	
	@RequestMapping(value = "api/support")
	@UnPermission
	public ModelAndView toSupport(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		addTranslateMessage(mv, messages);
		request.getSession().setAttribute("navBarPageId", "Support");
		mv.setViewName("../support/support.jsp"); 
		return mv;
	}

}
