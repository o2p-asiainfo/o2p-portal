package com.ailk.o2p.portal.service.reg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.Area;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.ailk.eaap.op2.bo.OrgRole;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.ailk.o2p.portal.dao.tenant.ITenantDao;
import com.ailk.o2p.portal.utils.SpringContextHolder;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.asiainfo.integration.o2p.web.util.WorkRestUtils;
import com.workflow.remote.WorkflowRemoteInterface;

@Service
public class RegService implements IRegService {
	
	@Autowired
	private IOrgDao orgDAO;
	@Autowired
	private ITenantDao tenantDao;
	
	public WorkflowRemoteInterface getWorkflowRemote(){
		return (WorkflowRemoteInterface)SpringContextHolder.getBean("workflowRemote");
		 
	}

	@Transactional(propagation=Propagation.REQUIRED) 
	public void saveOrg(Org org, OrgRole orgRoleBean) throws Exception{
		com.alibaba.fastjson.JSONObject retJson = null;
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		
		org.setState(EAAPConstants.COMM_STATE_WAITAUDI);
		Integer orgId = orgDAO.addOrg(org, orgRoleBean);
		
		jsonObject.put("contentId", String.valueOf(orgId));
		
		UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
		String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
		Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
		if(userRoleInfo != null){
			userId = tenantDao.qryAdminIdByTenantId(userRoleInfo.getTenantId());
			tenantId = userRoleInfo.getTenantId();
		}
		
		String o2pWebDomain = WebPropertyUtil.getPropertyValue("o2p_web_domin");
		if(null==o2pWebDomain || "".equals(o2pWebDomain.trim())){
			o2pWebDomain = EAAPConstants.O2P_WEB_DOMAIN_LOCAL;
		}
		
		
		if(EAAPConstants.O2P_WEB_DOMAIN_LOCAL.equals(o2pWebDomain)){
    		jsonObject.put("isLocal", Boolean.TRUE);
    	}else{
    		jsonObject.put("isLocal", Boolean.FALSE);
    	}
		
		retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_ORGREG, "Org Process Audit Name:"+org.getOrgUsername(),
				userId, tenantId, jsonObject.toJSONString());
		String returnCode = retJson.getString("code");
		
		if ("0000".equals(returnCode)) {
			Org orgTemp = new Org();
			orgTemp.setOrgId(orgId);
			orgTemp.setIndenEffDate(org.getIndenEffDate());
			orgTemp.setIndenExpDate(org.getIndenExpDate());
			orgTemp.setAuditFlowId(retJson.getString("desc"));
			orgDAO.updateOrgInfo(orgTemp);
		}
	}

	public Integer addOrgRole(OrgRole orgRoleBean) {
		return orgDAO.addOrgRole(orgRoleBean);
	}

	public List<Org> selectOrg(Org org) {
		return orgDAO.selectOrg(org);
	}

	public Integer updateOrgInfo(Org orgBean) {
		return orgDAO.updateOrgInfo(orgBean);
	}

	public List<Map<String,Object>> queryProvinceForReg(Org orgBean) {
		return orgDAO.queryProvinceForReg(orgBean);
	}

	public List<Map<String,Object>> queryCityForReg(Org orgBean) {
		return orgDAO.queryCityForReg(orgBean);
	}
	
	public String queryCityById(String areaId){
		Map paramMap=new HashMap(); 
		paramMap.put("areaId", areaId);
		return orgDAO.queryCityById(paramMap);
	}

	public Integer queryOrgIdSeq(){
		return orgDAO.queryOrgIdSeq();
	}
	public IOrgDao getOrgDAO() {
		return orgDAO;
	}

	public void setOrgDAO(IOrgDao orgDAO) {
		this.orgDAO = orgDAO;
	}

	public List<Area> loadCityAreaList(HashMap paraMap) {
		return orgDAO.loadCityAreaList(paraMap);
	}
}
