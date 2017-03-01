package com.ailk.o2p.portal.dao.doc;

import java.util.List;
import java.util.Map;

import com.asiainfo.integration.o2p.web.bo.Org;

public interface IDocumentDao {
	
	public Map<String,Object> qryApiByService(Map paramMap);

	public Map<String,Object> qryApiByProduct(Map paramMap);
	
	public List<Map<String, String>> qryApiListByOffer(Map paramMap);
	
	public List<Map<String, String>> qryProdListByOffer(Map paramMap);
	
	public List<Map<String, String>> queryApiDocument(Map paramMap);

	public List<Map<String, String>> showApiDirectory(Map paramMap);
	
	public Map<String,String> qryProductOffer(Map paramMap);
	
	public Map<String,String> qryApiOffer(Map paramMap);
	
	public Integer queryAllApiCount(Map map);
	
	public List<Map<String, String>> queryAllApiDocument(Map map);

	public String findAbilityIntro(Map paramMap);

	public String findApiUserGetType(Map paramMap);

	public String findReqUrl(Map paramMap);

	public String findHttpQeq(Map paramMap);

	public String findRspSupport(Map paramMap);

	public String findNotice(String id);

	public List<Map<String, String>> findSysQeqList(Map paramMap);

	public List<Map<String, String>> findApplyReqList(Map paramMap);

	public String findReqInstance(Map paramMap);
	
	public String findProdReqInstance(Map paramMap);

	public List<Map<String, Object>> findSysResult(Map paramMap);

	public List<Map<String, Object>> findAppResult(Map paramMap);

	public String findReturnInstance(Map paramMap);
	
	public String findProdReturnInstance(Map paramMap);

	public List<Map<String, String>> findErrorCode(Map paramMap);

	public List<Map<String, String>> findChannel(Map paramMap);

	public List<Map<String, String>> searchDirList(String dirId,Map paramMap);

	public List<Map<String, String>> searchDirNexList(String dirId,Map paramMap);
	
	public List<Map<String, String>> searchDirApiList(Map paramMap);
	
	public List<Map<String, String>> searchDirThirdList(String dirId,Map paramMap);

	public List<Map<String, String>> showPlatformList(String dirId,Map paramMap);

	public Map<String, String> getApiDirList(Map paramMap);

	public String findNexDirList(Map paramMap);

	public List<Map> successCase(Map paramMap);

	public List<Map> successCaseDetails(Map paramMap);

	public Org getOrg(Org org);

	public List<Map> querySuccessCaseCount(Map map);

	public List<Map> showSuccessCaseList(Map map);
	
	public List<Map> querySuccessCaseIndexList(Map paramMap);
	
	public List<Map> qryMostPopularOffer(Map paramMap);
	
	public List<Map> qryMostPopularPartner(Map paramMap);
	
	public Integer qryCallCntByProdOffer(Map paramMap);
	
}
