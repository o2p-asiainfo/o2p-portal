package com.ailk.o2p.portal.dao.pardSpec;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class PardSpecDao implements IPardSpecDao {
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	public BigDecimal getFeatureSpecId(){
		return (BigDecimal) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.getFeatureSpecId", null);
	}
	public List<Map> getFeatureSpecList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.queryFeatureSpec", map);
	}
	public void updateFeatureSpec(Map map) {
		sqlMapDao.update("eaap-op2-portal-pardSpec.updateFeatureSpec", map);
	}
	public void addFeatureSpec(Map map) {
		sqlMapDao.insert("eaap-op2-portal-pardSpec.addFeatureSpec", map);
	}
	public Integer countFeatureSpec(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.countFeatureSpec", map);
	}
	public Map getFeatureSpec(String attrSpecId){
		return (Map) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.getFeatureSpec", attrSpecId);
	}
	public int isUD(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.isUD", map);
	}
	public void delFeatureSpecTrue(String attrSpecId){
		sqlMapDao.delete("eaap-op2-portal-pardSpec.delFeatureSpecTrue", attrSpecId);
	}
	
//-----------------------------------------------------------
	public String addFeatureSpecValue(Map map){
		return sqlMapDao.insert("eaap-op2-portal-pardSpec.addFeatureVal", map)+"";
	}
	public void updateFeatureSpecValue(Map map){
		sqlMapDao.update("eaap-op2-portal-pardSpec.updateFeatureVal", map);
	}
	public List<Map> getFeatureSpecValueList(String attrSpecId){
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.queryFeatureSpecValList", attrSpecId);
	}
	public Map getFeatureSpecValue(Integer attrValId){
		return (Map) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.queryFeatureSpecVal", attrValId);
	}
	public void delFeatureSpecValue(String attrSpecId){
		sqlMapDao.delete("eaap-op2-portal-pardSpec.delFeatureSpecValue", attrSpecId);
	}
	//--------------------------------------------------------------------
	public void addProdAttrVal(Map map){
		sqlMapDao.insert("eaap-op2-portal-pardSpec.addProdAttrVal", map);
	}
	//-----------------------------------------------
	public List<Map> queryProductList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.queryProductList", map);
	}
	public Integer countProductList(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.countProductList", map);
	}
	public List<Map> queryOfferList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.queryOfferList", map);
	}
	public Integer countOfferList(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.countOfferList", map); 
	}
	//---------------------------------------------------------
	public Integer getProductId(){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.getProductId", null);
	}
	public void insertProduct(Map map){
		sqlMapDao.insert("eaap-op2-portal-pardSpec.insertProduct", map);
	}
	public List<Map> getPageInTypeIds(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.getPageInTypeIds", map);
	}
	public List<Integer> getMappingIdList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-pardSpec.getMappingIdList", map);
	}
	public Integer isExitAttrByCode(Map<String,String> params){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardSpec.isExitAttrByCode", params);
	}
}
