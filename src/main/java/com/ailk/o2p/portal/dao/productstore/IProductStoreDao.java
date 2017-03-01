package com.ailk.o2p.portal.dao.productstore;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Directory;
@SuppressWarnings("all")
public interface IProductStoreDao {

	public List<Directory> queryProductCategoryList(Map map);
	public List<Map> queryProductList(Map map);
	public List<Map> queryNewProductList(Map map);
	public List<Map> queryCRProductList(Map map);
	public Integer queryProductOfferCnt(Map<String, Object> paraMap);
}
