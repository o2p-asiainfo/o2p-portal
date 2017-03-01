package com.ailk.o2p.portal.service.doc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.o2p.portal.dao.doc.IDocumentDao;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.asiainfo.integration.o2p.basedao.util.DateUtil;
import com.asiainfo.integration.o2p.web.bo.Org;

@Service
public class DocumentService implements IDocumentService {

	@Autowired
	private IDocumentDao documentDao;
	@Autowired
	private IOrgDao orgDao; 
	public IDocumentDao getdocumentDao() {
		return documentDao;
	}

	public void setdocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	
	public Map<String,Object> qryApiByService(String serviceId){
		Map paramMap=new HashMap(); 
		paramMap.put("serviceId", serviceId);
		return documentDao.qryApiByService(paramMap);
	}

	public List<Map<String, String>> queryApiDocument(String type, String id) {
		List<Map<String, String>> queryApiDocument = null;
		Map paramMap=new HashMap(); 
		paramMap.put("type", type);
		paramMap.put("id", id);
		queryApiDocument = documentDao.queryApiDocument(paramMap);
		return queryApiDocument;
	}

	public List<Map<String, String>> showApiDirectory() {
		List<Map<String, String>> apiDirectoryList = null;
		Map paramMap=new HashMap(); 
		apiDirectoryList = documentDao.showApiDirectory(paramMap);
		return apiDirectoryList;
	}
	
	public Integer queryAllApiCount(Map map){
		return documentDao.queryAllApiCount(map);
	}
	
	public List<Map<String, String>> queryAllApiDocument(Map map){
		return  documentDao.queryAllApiDocument(map);
		 
	}

	public String findAbilityIntro(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findAbilityIntro(paramMap);
		 
	}

	public String findApiUserGetType(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findApiUserGetType(paramMap);
		 
	}

	public List<Map<String, Object>> findAppResult(String id) {
		List<Map<String, Object>> appResult = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		appResult = documentDao.findAppResult(paramMap);
		return appResult;
	}

	public List<Map<String, String>> findApplyReqList(String id) {
		List<Map<String, String>> applyReqList = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		applyReqList = documentDao.findApplyReqList(paramMap);
		return applyReqList;
	}

	public List<Map<String, String>> findErrorCode(String id) {
		List<Map<String, String>> errorCode = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		errorCode = documentDao.findErrorCode(paramMap);
		return errorCode;
	}

	public String findHttpQeq(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findHttpQeq(paramMap);
		 
	}

	public String findNotice(String id) {
		return documentDao.findNotice(id);
		 
	}

	public String findReqInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findReqInstance(paramMap);
		 
	}

	public String findReqUrl(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findReqUrl(paramMap);
		   
	}

	public String findReturnInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findReturnInstance(paramMap);
	 
	}

	public String findRspSupport(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findRspSupport(paramMap);
		 
	}

	public List<Map<String, String>> findSysQeqList(String id) {
		List<Map<String, String>> sysQeqList = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		sysQeqList = documentDao.findSysQeqList(paramMap);
		return sysQeqList;
	}

	public List<Map<String, Object>> findSysResult(String id) {
		List<Map<String, Object>> sysResult = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		sysResult = documentDao.findSysResult(paramMap);
//		for (Map<String, Object> item : sysResult) {
//			item.put("FANONENAME", cutSuperNode(item.get("FANONENAME")
//					.toString()));
//		}
		return sysResult;
	}

	public List<Map<String, String>> findChannel(String id) {
		List<Map<String, String>> channel = null;
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		channel = documentDao.findChannel(paramMap);
		return channel;
	}

	public List<Map<String, String>> searchDirList(String dirId) {
		List<Map<String, String>> searchDirList = null;
		Map paramMap=new HashMap();
		searchDirList = documentDao.searchDirList(dirId,paramMap);
		return searchDirList;
	}

	public List<Map<String, String>> searchDirNexList(String dirId) {
		List<Map<String, String>> searchDirNexList = null;
		Map paramMap=new HashMap();
		searchDirNexList = documentDao.searchDirNexList(dirId,paramMap);
		return searchDirNexList;
	}
	
	public List<Map<String, String>> searchDirApiList(){
		List<Map<String, String>> searchDirApiList = null;
		Map paramMap=new HashMap();
		searchDirApiList = documentDao.searchDirApiList(paramMap);
		return searchDirApiList;
	}
	
	public List<Map<String, String>> searchDirThirdList(String dirId){
		List<Map<String, String>> searchDirThirdList = null;
		Map paramMap=new HashMap();
		searchDirThirdList = documentDao.searchDirThirdList(dirId,paramMap);
		return searchDirThirdList; 
	}

	public List<Map<String, String>> showPlatformList(String dirId) {
		List<Map<String, String>> showPlatformList = null;
		Map paramMap=new HashMap();
		showPlatformList = documentDao.showPlatformList(dirId,paramMap);
		return showPlatformList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getApiDirList(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.getApiDirList(paramMap);
		 
	}

	public String findNexDirList(String dirId) {
		Map paramMap=new HashMap();
		paramMap.put("dirId", dirId);
		return documentDao.findNexDirList(paramMap);
		 
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
		return documentDao.successCase(paramMap);
	}

	public List<Map> successCaseDetails(String orgId) {
		Map paramMap=new HashMap(); 
		paramMap.put("orgId", orgId);
		return documentDao.successCaseDetails(paramMap);
	}

	public Org getOrg(Org org) {
		return documentDao.getOrg(org);
	}

	public List<Map> showSuccessCaseList(Map hashMap) {
		return documentDao.showSuccessCaseList(hashMap);
	}
	
	public List<Map> querySuccessCaseIndexList(){
		Map paramMap=new HashMap(); 
		return documentDao.querySuccessCaseIndexList(paramMap);
	}

	@Override
	public Map<String, Object> qryApiByProduct(String productId) {
		Map paramMap=new HashMap(); 
		paramMap.put("productId", productId);
		return documentDao.qryApiByProduct(paramMap);
	}

	@Override
	public List<Map<String, String>> qryApiListByOffer(String prodOfferId) {
		Map paramMap=new HashMap(); 
		paramMap.put("prodOfferId", prodOfferId);
		List<Map<String, String>> apiList = documentDao.qryApiListByOffer(paramMap);
		return apiList;
	}

	@Override
	public Map<String, String> qryProductOffer(String prodOfferId) {
		Map paramMap=new HashMap(); 
		paramMap.put("prodOfferId", prodOfferId);
		return documentDao.qryProductOffer(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryArticleList(String prodOfferId) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("itemCode", prodOfferId);
		paraMap.put("columnId", "1001");
		return orgDao.queryArticleList(paraMap);
	}

	@Override
	public Map<String, String> qryApiOffer(String prodOfferId) {
		Map paramMap=new HashMap(); 
		paramMap.put("prodOfferId", prodOfferId);
		return documentDao.qryApiOffer(paramMap);
	}

	@Override
	public List<Map<String, String>> qryProdListByOffer(String prodOfferId) {
		Map paramMap=new HashMap(); 
		paramMap.put("prodOfferId", prodOfferId);
		List<Map<String, String>> productList = documentDao.qryProdListByOffer(paramMap);
		return productList;
	}

	@Override
	public String findProdReqInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findProdReqInstance(paramMap);
	}

	@Override
	public String findProdReturnInstance(String id) {
		Map paramMap=new HashMap();
		paramMap.put("value", id);
		return documentDao.findProdReturnInstance(paramMap);
	}

	@Override
	public List<Map> qryMostPopularOffer(Map paramMap) {
		return documentDao.qryMostPopularOffer(paramMap);
	}

	@Override
	public List<Map> qryMostPopularPartner(Map paramMap) {
		return documentDao.qryMostPopularPartner(paramMap);
	}

	@Override
	public Integer qryCallCntByProdOffer(Map paramMap) {
		//获取当前年份和月份
		SimpleDateFormat formatter = new SimpleDateFormat("yyMM"); 
		paramMap.put("tabSuffix", formatter.format(new Date()));
		
		return documentDao.qryCallCntByProdOffer(paramMap);
	}
	
	
}
