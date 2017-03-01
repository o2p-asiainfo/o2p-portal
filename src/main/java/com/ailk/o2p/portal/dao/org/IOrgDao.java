package com.ailk.o2p.portal.dao.org;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Area;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.eaap.op2.bo.OrgRole;
import com.asiainfo.integration.o2p.web.bo.Org;
 

public interface IOrgDao {
	
	public Integer addOrg(Org orgBean,OrgRole orgRoleBean);
	
	public Integer addOrgRole(OrgRole orgRoleBean);
	
	public List<Org> selectOrg(Org orgBean);
	
	public Org queryOrg(Org orgBean);
	
	public List<Map<String,String>> queryOrgIdAndName();
	
	public Map loginOrg(Org orgBean);
	
	public List<OrgRole> selectOrgRole(OrgRole orgRoleBean);
	
	public Integer updateOrgInfo(Org orgBean);
	
	public List<Map<String,Object>> queryProvinceForReg(Org orgBean);
	
	public List<Map<String,Object>> queryOrgCountry(Map map);
	
	public List<Map<String,Object>> queryOperatorUnderCountry(Org orgBean);
	
	public List<Map<String,Object>> queryCityForReg(Org orgBean);
	
	public Org selectOrgOne(Org org);
	
	public Component selectComponentOne(Component component);
	
	public List<Area> loadCityAreaList(HashMap paraMap);
	
	public String queryCityById(Map paramMap);
	
	public Integer queryOrgIdSeq();
	
	public String queryTenantIdByCode(String tenantCode);
	
	public Tenant queryTenant(Tenant tenant);
	
	public List<Map<String,Object>> queryPartnerList(Map<String,Object> paraMap);
	
	public List<Map<String,Object>> queryPartnerInfo(Org org); 
	
	public List<Map<String,Object>> queryOrgCategory(Directory dir);
	
	public List<Directory> queryAllOrgCategory(Directory dir);
	public List<String> queryDirById(Map<String, Object> paraMap);
	public List<String> queryCategoryByOrgId(Org org);
	
	public Integer queryPartnerCnt(Map<String, Object> paraMap);
	
	/**
	 * 获取文章列表
	 * @param paraMap
	 * columnID 所属栏目（不可空）
	 * itemCode 所属物品编码（可空）
	 * @return
	 */
	public List<Map<String, Object>> queryArticleList(HashMap<String, Object> paraMap);
	
	/**
	 * 提供的APIoffer
	 * @param prodOffer
	 * @return
	 */
	public List<ProdOffer> queryMyAPIOffer(Org org);
	/**
	 * 提供的product offer
	 * @param prodOffer
	 * @return
	 */
	public List<ProdOffer> queryMyProdOffer(Org org);
	/**
	 * 消费的api offer
	 * @param prodOffer
	 * @return
	 */
	public List<ProdOffer> queryConsumerApiOffer(Org org);
	/**
	 * 转售的api offer
	 * @param prodOffer
	 * @return
	 */
	public List<ProdOffer> queryResaleProdOffer(Org org);
	
	/**
	 * 查询访问量
	 * @param itemCnt
	 * @return
	 */
	public ItemCnt queryItemCnt(ItemCnt itemCnt);
	

	public void updateItemCnt(ItemCnt itemCnt);
	public void insertItemCnt(ItemCnt itemCnt);
}
