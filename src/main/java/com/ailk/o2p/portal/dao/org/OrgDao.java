package com.ailk.o2p.portal.dao.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Area;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Tenant;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgRole;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class OrgDao implements IOrgDao {

	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	public Integer addOrg(Org orgBean, OrgRole orgRoleBean) {
		sqlMapDao.insert("org.insertOrg", orgBean);
		Integer orgId=orgBean.getOrgId();
		orgRoleBean.setOrgId(orgId);
		sqlMapDao.insert("org_role.insertOrgRole", orgRoleBean);
		return orgId;
	}

	public Integer addOrgRole(OrgRole orgRoleBean) {
		return (Integer) sqlMapDao
				.insert("org_role.insertOrgRole", orgRoleBean);
	}

	public List<Org> selectOrg(Org orgBean) {
		List<Org> orgList = (ArrayList<Org>) sqlMapDao.queryForList(
				"org.selectOrg", orgBean);
		return orgList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> queryOrgIdAndName(){
		List<Map<String,String>> orgList = (List<Map<String,String>>) sqlMapDao.queryForList("org.queryOrgIdAndName",
				null);
		return orgList;
	}

	public Org queryOrg(Org orgBean) {
		return (Org) sqlMapDao.queryForObject("org.selectOrg", orgBean);
	}

	public Map loginOrg(Org orgBean) {
		Map orgList = (HashMap) sqlMapDao.queryForObject("org.selectOrg",
				orgBean);
		return orgList;
	}

	public List<OrgRole> selectOrgRole(OrgRole orgRoleBean) {
		List<OrgRole> orgRoleList = (ArrayList<OrgRole>) sqlMapDao
				.queryForList("org_role.selectOrgRole", orgRoleBean);
		return orgRoleList;
	}

	public Integer updateOrgInfo(Org orgBean) {
		return (Integer) sqlMapDao.update("org.updateOrg", orgBean);
	}
	
	public String queryCityById(Map paramMap){
		return (String) sqlMapDao.queryForObject("org.queryCityById", paramMap);
	}

	public List<Map<String,Object>> queryCityForReg(Org orgBean) {
		return (List<Map<String,Object>>) sqlMapDao.queryForList("org.queryCityForReg",
				orgBean);
	}

	public List<Map<String,Object>> queryProvinceForReg(Org orgBean) {
		return (List<Map<String,Object>>) sqlMapDao.queryForList("org.queryProvinceForReg",
				orgBean);
	}

	public List<Map<String,Object>> queryOrgCountry(Map map) {
		return (List<Map<String,Object>>) sqlMapDao.queryForList("org.queryOrgCountry",map);
	}
	
	
	@Override
	public List<Map<String, Object>> queryOperatorUnderCountry(Org orgBean) {
		// TODO Auto-generated method stub
		return (List<Map<String,Object>>) sqlMapDao.queryForList("org.queryOperatorUnderCountry",orgBean);
	}
	
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	public Org selectOrgOne(Org org) {
		return (Org) sqlMapDao.queryForObject("org.selectOrg", org);
	}

	public Component selectComponentOne(Component component) {
		return (Component) sqlMapDao.queryForObject("component.selectComponent", component);
	}

	public List<Area> loadCityAreaList(HashMap paraMap) {
		return (List<Area>) sqlMapDao.queryForList("org.selectCity", paraMap);
	}

	public Integer queryOrgIdSeq(){
		return  (Integer) sqlMapDao.queryForObject("org.queryOrgIdSeq", null);
	}
	
	public String queryTenantIdByCode(String tenantCode){
		return  (String) sqlMapDao.queryForObject("org.queryTenantIdByCode", tenantCode);
	}
	public Tenant queryTenant(Tenant tenant){
		return  (Tenant) sqlMapDao.queryForObject("org.queryTenant", tenant);
	}

	public List<Map<String,Object>> queryPartnerList(Map<String,Object> paraMap) {
		return (List<Map<String,Object>>) sqlMapDao.queryForList("partner.queryPartnerList", paraMap);
	}

	
	@Override
	public List<Map<String, Object>> queryPartnerInfo(
			Org org) {
		return (List<Map<String,Object>>) sqlMapDao.queryForList("partner.queryPartnerInfo", org);
	}

	
	@Override
	public List<ProdOffer> queryMyAPIOffer(Org org) {
		// TODO Auto-generated method stub
		return (List<ProdOffer>) sqlMapDao.queryForList("partner.queryMyApiOffer", org);
	}

	@Override
	public List<ProdOffer> queryMyProdOffer(Org org) {
		// TODO Auto-generated method stub
		return (List<ProdOffer>) sqlMapDao.queryForList("partner.queryMyProdOffer", org);
	}

	@Override
	public List<ProdOffer> queryConsumerApiOffer(Org org) {
		// TODO Auto-generated method stub
		return (List<ProdOffer>) sqlMapDao.queryForList("partner.queryConsumerApiOffer", org);
	}

	@Override
	public List<ProdOffer> queryResaleProdOffer(Org org) {
		// TODO Auto-generated method stub
		return (List<ProdOffer>) sqlMapDao.queryForList("partner.queryResaleProdOffer", org);
	}

	@Override
	public List<Map<String, Object>> queryOrgCategory(
			Directory dir) {
		return (List<Map<String, Object>>) sqlMapDao.queryForList("partner.queryOrgCategory", dir);
	}

	@Override
	public List<String> queryDirById(Map<String, Object> paraMap) {
		return (List<String>) sqlMapDao.queryForList("partner.queryDirById", paraMap);
	}

	@Override
	public List<String> queryCategoryByOrgId(Org org) {
		return (List<String>) sqlMapDao.queryForList("partner.queryCategoryByOrgId", org);
	}

	@Override
	public List<Directory> queryAllOrgCategory(Directory dir) {
		return (List<Directory>) sqlMapDao.queryForList("partner.queryAllOrgCategory", dir);
	}

	@Override
	public List<Map<String, Object>> queryArticleList(
			HashMap<String, Object> paraMap) {
		return (List<Map<String, Object>>) sqlMapDao.queryForList("partner.queryArticleList", paraMap);
	}

	@Override
	public Integer queryPartnerCnt(Map<String, Object> paraMap) {
		return (Integer) sqlMapDao.queryForObject("partner.queryPartnerCnt", paraMap);
	}

	@Override
	public ItemCnt queryItemCnt(ItemCnt itemCnt) {
		// TODO Auto-generated method stub
		return (ItemCnt) sqlMapDao.queryForObject("partner.queryItemCnt", itemCnt);
	}

	@Override
	public void updateItemCnt(ItemCnt itemCnt) {
		sqlMapDao.update("partner.updateItemCnt", itemCnt);
		
	}

	@Override
	public void insertItemCnt(ItemCnt itemCnt) {
		sqlMapDao.insert("partner.insertItemCnt", itemCnt);
	}


}
