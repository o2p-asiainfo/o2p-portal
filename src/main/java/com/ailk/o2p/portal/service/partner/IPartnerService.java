package com.ailk.o2p.portal.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Directory;
import com.asiainfo.integration.o2p.web.bo.Org;

/**
 * O2P CLOUD合作伙伴
 * @author Administrator
 *
 */
public interface IPartnerService {
	public List<Map<String, Object>> queryPartnerList(Map<String, Object> paraMap);
	
	public Map<String, Object> queryPartnerDetail(Org org);
	
	public List<Directory> queryAllOrgCategory(Directory dir);
	public List<Map<String,Object>> queryOrgCategory(Directory dir);
	public Integer queryPartnerCnt(Map<String, Object> paraMap);
}
