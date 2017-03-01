package com.ailk.o2p.portal.dao.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NData;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NDatas;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.linkage.rainbow.dao.SqlMapDAO;
import com.linkage.rainbow.util.StringUtil;

@Repository
public class ApiDocumentDao implements IApiDocumentDao {

	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	public Map<String,Object> qryApiByService(Map paramMap){
		List<Map<String, Object>> apiMapList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.qryApiByService", paramMap);
		Map<String, Object> apiMap = null;
		if(apiMapList != null && !apiMapList.isEmpty()){
			apiMap = apiMapList.get(0);
		}
		return apiMap;
	}
	
	public Integer queryAllApiCount(Map map) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-doc.findAllApiListCount", map);
	}
	
	public List<Map<String, String>> queryAllApiDocument(Map map){
		List<Map<String, String>> queryApiDocument = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.findAllApiList", map);
		return queryApiDocument;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> queryApiDocument(Map paramMap) {
		List<Map<String, String>> queryApiDocument = null;
		String type=paramMap.get("type").toString();
		String id=paramMap.get("id").toString();
		if (id != null && !id.equals("")) {
			if ("Search API Document".equals(id)) {
				queryApiDocument = sqlMapDao.queryForList(
						"eaap-op2-portal-doc.findAllApiList", null);
				return queryApiDocument;
			}
			if ("dir".equals(type)) {
				queryApiDocument = sqlMapDao.queryForList(
						"eaap-op2-portal-doc.selectDirApiList", paramMap);
			}
			if ("api".equals(type)) {
				queryApiDocument = sqlMapDao.queryForList(
						"eaap-op2-portal-doc.selectApiList", paramMap);
			}
		} else {
			queryApiDocument = sqlMapDao.queryForList(
					"eaap-op2-portal-doc.findAllApiList", null);
		}
		return queryApiDocument;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> showApiDirectory(Map paramMap) {
		List<Map<String, String>> apiDirectoryList = null;
		apiDirectoryList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectDirectoryList", paramMap);
		return apiDirectoryList;
	}

	public String findAbilityIntro(Map paramMap) {
		String abilityIntro = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectAbilityIntro", paramMap);
		return abilityIntro;
	}

	public String findApiUserGetType(Map paramMap) {
		String apiUserGetType = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectAPIUserGetType", paramMap);
		return apiUserGetType;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "NODETYPE") })
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findAppResult(Map paramMap) {
		List<Map<String, Object>> AppResult = null;
		AppResult = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectAppResult", paramMap);
		return AppResult;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "NODETYPE") })
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findApplyReqList(Map paramMap) {
		List<Map<String, String>> ApplyReqList = null;
		ApplyReqList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectApplyReqList", paramMap);
		return ApplyReqList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findErrorCode(Map paramMap) {
		List<Map<String, String>> errorCode = null;
		errorCode = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectErrorCode", paramMap);
		return errorCode;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "comm_protocal", columnNames = "comm_pro_name", idName = "COMMPROCD", propertyNames = "COMMPRONAME") })
	public String findHttpQeq(Map paramMap) {
		String httpqeq = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectHTTPQeq", paramMap);
		return httpqeq;
	}

	public String findNotice(String id) {
		return null;
	}

	public String findReqInstance(Map paramMap) {
		String reqIntance = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectReqInstance", paramMap);
		return reqIntance;
	}

	public String findReqUrl(Map paramMap) {
		String reqUrl = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectReqURL", paramMap);
		return reqUrl;
	}

	public String findReturnInstance(Map paramMap) {
		String returnInstance = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectReturnInstance", paramMap);
		return returnInstance;
	}

	public String findRspSupport(Map paramMap) {
		String rspSupport = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectRspSupport", paramMap);
		return rspSupport;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "NODETYPE") })
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSysQeqList(Map paramMap) {
		List<Map<String, String>> sysQeqList = null;
		sysQeqList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectSysQeqList", paramMap);
		return sysQeqList;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "NODETYPE") })
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findSysResult(Map paramMap) {
		List<Map<String, Object>> sysResult = null;
		sysResult = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectSysResult", paramMap);
		return sysResult;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "MAINDID", propertyNames = "CEPNAME") })
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findChannel(Map paramMap) {
		List<Map<String, String>> channelList = null;
		channelList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectChannelList", paramMap);
		return channelList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchDirList(String dirId,Map paramMap) {
		Map map = new HashMap();
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		String arrayDirId = String.valueOf(dirId);
		if (null != dirId && !"".equals(arrayDirId)) {
			arrayDirId = arrayDirId.replace("'", "");
			String[] dstCode = arrayDirId.split(",");
			map.put("DirId", dstCode);
		}
		map.put("tenantId",tenantId);
		List<Map<String, String>> searchDirList = null;
		searchDirList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectDirList", map);
		return searchDirList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchDirNexList(String dirId,Map paramMap) {
		Map map = new HashMap();
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		String arrayDirId = String.valueOf(dirId);
		if (null != dirId && !"".equals(arrayDirId)) {
			arrayDirId = arrayDirId.replace("'", "");
			String[] dstCode = arrayDirId.split(",");
			map.put("DirId", dstCode);
		}
		map.put("tenantId",tenantId);
		List<Map<String, String>> searchDirNexList = null;
		searchDirNexList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectDirNexList", map);
		return searchDirNexList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchDirApiList(Map paramMap) {
		return sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectApiDirList", paramMap);
	}
	
	@SuppressWarnings("unchecked") 
	public List<Map<String, String>> searchDirThirdList(String dirId,Map paramMap) {
		Map map = new HashMap();
		List<Map<String, String>> searchDirNexList = new ArrayList<Map<String, String>>();
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		if (!StringUtil.isEmpty(dirId)) {
			String[] dstCode = dirId.split(",");
			map.put("DirId", dstCode);
			map.put("tenantId", tenantId);
			searchDirNexList = sqlMapDao.queryForList(
					"eaap-op2-portal-doc.selectDirThirdList", map);
		}
		return searchDirNexList;
	}

	@ProvideI18NDatas(values = { @ProvideI18NData(tableName = "ques_help", columnNames = "QUE_TITLE,QUE_ASK_CONTENT", idName = "QUE_HELP_ID", propertyNames = "QUETITLE,QUEASKCONTENT") })
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> showPlatformList(String dirId,Map paramMap) {
		List<Map<String, String>> showPlatformList = null;
		if (null == dirId || dirId.equals("")) {
			dirId = EAAPConstants.DOCPROBLEM;
		}
		paramMap.put("dirId", dirId);
		showPlatformList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.showPlatform",paramMap);
		return showPlatformList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getApiDirList(Map paramMap) {
		Map map =  (Map<String, String>) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.selectApiAndDirList", paramMap);
		return map;
	}

	public String findNexDirList(Map paramMap) {
		String nextDirTitle = null;
		nextDirTitle = (String) sqlMapDao.queryForObject(
				"eaap-op2-portal-doc.nexDirTitle", paramMap);
		return nextDirTitle;
	}

	public List<Map> successCase(Map paramMap) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectSuccessCase", paramMap);
	}

	public List<Map> successCaseDetails(Map paramMap) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-doc.successCaseDetails", paramMap);
	}

	public Org getOrg(Org org) {
		return (Org) sqlMapDao.queryForObject("org.selectOrg", org);
	}

	public List<Map> querySuccessCaseCount(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-doc.successCaseCount", map);
	}

	public List<Map> showSuccessCaseList(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-doc.successCaseList", map);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> querySuccessCaseIndexList(Map paramMap){
		return (List<Map>) sqlMapDao.queryForList("eaap-op2-portal-doc.querySuccessCaseIndexList",paramMap);
	}
	
}
