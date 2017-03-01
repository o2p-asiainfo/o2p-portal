package com.ailk.o2p.portal.dao.pardSpec;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
@SuppressWarnings("all")
public interface IPardSpecDao {
	
	public BigDecimal getFeatureSpecId();
	public Integer countFeatureSpec(Map map);
	public List<Map> getFeatureSpecList(Map map);
	public Map getFeatureSpec(String attrSpecId);
	public void updateFeatureSpec(Map map);
	public void addFeatureSpec(Map map);
	public int isUD(Map map);
	public void delFeatureSpecTrue(String attrSpecId);
	
	//--------------------------------------------------------------
	public String addFeatureSpecValue(Map map);
	public void updateFeatureSpecValue(Map map);
	public List<Map> getFeatureSpecValueList(String attrSpecId);
	public Map getFeatureSpecValue(Integer attrValId);
	public void delFeatureSpecValue(String attrSpecId);
	
	//----------------------------------------------------------------
	public void addProdAttrVal(Map map);
	//--------------------------------------------------
	public List<Map> queryProductList(Map map);
	public Integer countProductList(Map map);
	public List<Map> queryOfferList(Map map);
	public Integer countOfferList(Map map);
	//---------------------------------------------------
	public Integer getProductId();
	public void insertProduct(Map map);
	public List<Map> getPageInTypeIds(Map map);
	public List<Integer> getMappingIdList(Map map);
	public Integer isExitAttrByCode(Map<String,String> params);
}
