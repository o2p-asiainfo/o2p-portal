package com.ailk.o2p.portal.service.doc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.o2p.portal.dao.doc.IApiDocumentDao;

@Service
public class ApiDocumentService implements IApiDocumentService {

	@Autowired
	private IApiDocumentDao apiDocumentDao;

	public IApiDocumentDao getApiDocumentDao() {
		return apiDocumentDao;
	}

	public void setApiDocumentDao(IApiDocumentDao apiDocumentDao) {
		this.apiDocumentDao = apiDocumentDao;
	}
	
	public Map<String,Object> qryApiByService(String serviceId){
		Map paramMap=new HashMap(); 
		paramMap.put("serviceId", serviceId);
		return apiDocumentDao.qryApiByService(paramMap);
	}

	public List<Map<String, String>> queryApiDocument(String type, String id) {
		List<Map<String, String>> queryApiDocument = null;
		Map paramMap=new HashMap(); 
		paramMap.put("type", type);
		paramMap.put("id", id);
		queryApiDocument = apiDocumentDao.queryApiDocument(paramMap);
		return queryApiDocument;
	}

	public List<Map<String, String>> showApiDirectory() {
		List<Map<String, String>> apiDirectoryList = null;
		Map paramMap=new HashMap(); 
		apiDirectoryList = apiDocumentDao.showApiDirectory(paramMap);
		return apiDirectoryList;
	}
	
	public Integer queryAllApiCount(Map map){
		return apiDocumentDao.queryAllApiCount(map);
	}
	
	public List<Map<String, String>> queryAllApiDocument(Map map){
		return  apiDocumentDao.queryAllApiDocument(map);
		 
	}

	public String findAbilityIntro(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findAbilityIntro(paramMap);
		 
	}

	public String findApiUserGetType(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findApiUserGetType(paramMap);
		 
	}

	public List<Map<String, Object>> findAppResult(String id) {
		List<Map<String, Object>> appResult = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		appResult = apiDocumentDao.findAppResult(paramMap);
		for (Map<String, Object> item : appResult) {
			item.put("FANONENAME", cutSuperNode(item.get("FANONENAME")
					.toString()));
		}
		return appResult;
	}

	public List<Map<String, String>> findApplyReqList(String id) {
		List<Map<String, String>> applyReqList = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		applyReqList = apiDocumentDao.findApplyReqList(paramMap);
		return applyReqList;
	}

	public List<Map<String, String>> findErrorCode(String id) {
		List<Map<String, String>> errorCode = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		errorCode = apiDocumentDao.findErrorCode(paramMap);
		return errorCode;
	}

	public String findHttpQeq(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findHttpQeq(paramMap);
		 
	}

	public String findNotice(String id) {
		return apiDocumentDao.findNotice(id);
		 
	}

	public String findReqInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findReqInstance(paramMap);
		 
	}

	public String findReqUrl(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findReqUrl(paramMap);
		   
	}

	public String findReturnInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findReturnInstance(paramMap);
	 
	}

	public String findRspSupport(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.findRspSupport(paramMap);
		 
	}

	public List<Map<String, String>> findSysQeqList(String id) {
		List<Map<String, String>> sysQeqList = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		sysQeqList = apiDocumentDao.findSysQeqList(paramMap);
		return sysQeqList;
	}

	public List<Map<String, Object>> findSysResult(String id) {
		List<Map<String, Object>> sysResult = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		sysResult = apiDocumentDao.findSysResult(paramMap);
		for (Map<String, Object> item : sysResult) {
			item.put("FANONENAME", cutSuperNode(item.get("FANONENAME")
					.toString()));
		}
		return sysResult;
	}

	public List<Map<String, String>> findChannel(String id) {
		List<Map<String, String>> channel = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		channel = apiDocumentDao.findChannel(paramMap);
		return channel;
	}

	public List<Map<String, String>> searchDirList(String dirId) {
		List<Map<String, String>> searchDirList = null;
		Map paramMap=new HashMap();
		searchDirList = apiDocumentDao.searchDirList(dirId,paramMap);
		return searchDirList;
	}

	public List<Map<String, String>> searchDirNexList(String dirId) {
		List<Map<String, String>> searchDirNexList = null;
		Map paramMap=new HashMap();
		searchDirNexList = apiDocumentDao.searchDirNexList(dirId,paramMap);
		return searchDirNexList;
	}
	
	public List<Map<String, String>> searchDirApiList(){
		List<Map<String, String>> searchDirApiList = null;
		Map paramMap=new HashMap();
		searchDirApiList = apiDocumentDao.searchDirApiList(paramMap);
		return searchDirApiList;
	}
	
	public List<Map<String, String>> searchDirThirdList(String dirId){
		List<Map<String, String>> searchDirThirdList = null;
		Map paramMap=new HashMap();
		searchDirThirdList = apiDocumentDao.searchDirThirdList(dirId,paramMap);
		return searchDirThirdList; 
	}

	public List<Map<String, String>> showPlatformList(String dirId) {
		List<Map<String, String>> showPlatformList = null;
		Map paramMap=new HashMap();
		showPlatformList = apiDocumentDao.showPlatformList(dirId,paramMap);
		return showPlatformList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getApiDirList(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return apiDocumentDao.getApiDirList(paramMap);
		 
	}

	public String findNexDirList(String dirId) {
		Map paramMap=new HashMap();
		paramMap.put("dirId", dirId);
		return apiDocumentDao.findNexDirList(paramMap);
		 
	}

	private String cutSuperNode(String node) {
		String str = node;
		String s1 = str.replace("\\", "/");
		String d[] = s1.split("/");
		if (d.length > 0) {
			int lastIndex_1 = s1.lastIndexOf('/');
			if (lastIndex_1 == -1) {
				;

			} else if (lastIndex_1 > -1 && lastIndex_1 <= 2) {
				int i = s1.indexOf('/');
				str = s1.substring(0, i) + s1.substring(i + 1);
			} else {
				String s2 = s1.substring(0, lastIndex_1);
				int lastIndex_2 = s2.lastIndexOf('/');
				str = s1.substring(lastIndex_2 + 1, lastIndex_1);
			}
		}
		return str;
	}

	public List<Map> successCase(String isIndex) {
		Map paramMap=new HashMap(); 
		paramMap.put("isIndex", isIndex);
		return apiDocumentDao.successCase(paramMap);
	}

	public List<Map> successCaseDetails(String orgId) {
		Map paramMap=new HashMap(); 
		paramMap.put("orgId", orgId);
		return apiDocumentDao.successCaseDetails(paramMap);
	}

	public Org getOrg(Org org) {
		return apiDocumentDao.getOrg(org);
	}

	public List<Map> showSuccessCaseList(Map hashMap) {
		return apiDocumentDao.showSuccessCaseList(hashMap);
	}
	
	public List<Map> querySuccessCaseIndexList(){
		Map paramMap=new HashMap(); 
		return apiDocumentDao.querySuccessCaseIndexList(paramMap);
	}
}
