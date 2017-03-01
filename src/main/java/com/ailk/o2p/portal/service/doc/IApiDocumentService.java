package com.ailk.o2p.portal.service.doc;

import java.util.List;
import java.util.Map;

import com.asiainfo.integration.o2p.web.bo.Org;

public interface IApiDocumentService {
	
	public Map<String,Object> qryApiByService(String serviceId);

	public List<Map<String, String>> queryApiDocument(String type, String id);

	public List<Map<String, String>> showApiDirectory();
	
	public Integer queryAllApiCount(Map map);
	
	public List<Map<String, String>> queryAllApiDocument(Map map);

	public String findAbilityIntro(String id);

	public String findApiUserGetType(String id);

	public String findReqUrl(String id);

	public String findHttpQeq(String id);

	public String findRspSupport(String id);

	public String findNotice(String id);

	public List<Map<String, String>> findSysQeqList(String id);

	public List<Map<String, String>> findApplyReqList(String id);

	public String findReqInstance(String id);

	public List<Map<String, Object>> findSysResult(String id);

	public List<Map<String, Object>> findAppResult(String id);

	public String findReturnInstance(String id);

	public List<Map<String, String>> findErrorCode(String id);

	public List<Map<String, String>> findChannel(String id);

	public List<Map<String, String>> searchDirList(String dirId);

	public List<Map<String, String>> searchDirNexList(String dirId);
	
	public List<Map<String, String>> searchDirThirdList(String dirId);
	
	public List<Map<String, String>> searchDirApiList();

	public List<Map<String, String>> showPlatformList(String dirId);

	public Map<String, String> getApiDirList(String id);

	public String findNexDirList(String dirId);

	public List<Map> successCase(String isIndex);

	public List<Map> successCaseDetails(String orgId);

	public Org getOrg(Org org);

	public List<Map> showSuccessCaseList(Map hashMap);
	
	public List<Map> querySuccessCaseIndexList();
}
