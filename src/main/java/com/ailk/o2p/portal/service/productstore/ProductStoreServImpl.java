package com.ailk.o2p.portal.service.productstore;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;

import com.ailk.eaap.op2.bo.Directory;
import com.ailk.o2p.portal.dao.productstore.IProductStoreDao;
import com.ailk.o2p.portal.utils.DirectoryByJson;

@Service
public class ProductStoreServImpl implements IProductStoreServ {
	@Autowired
	private IProductStoreDao  productStoreDao;

	@Override
	public JSONArray queryProductCategory(Map map) {
		List<Directory> resultList = productStoreDao.queryProductCategoryList(map);
		JSONArray json = DirectoryByJson.createTreeJson(resultList); 
		return json;
	}

	@Override
	public List<Map> queryProductList(Map map) {
		return productStoreDao.queryProductList(map);
	}

	@Override
	public List<Map> queryNewProductList(Map map) {
		return productStoreDao.queryNewProductList(map);
	}

	@Override
	public List<Map> queryCRProductList(Map map) {
		return productStoreDao.queryCRProductList(map);
	}

	@Override
	public Integer queryProductOfferCnt(Map<String, Object> paraMap) {
		return productStoreDao.queryProductOfferCnt(paraMap);
	}
}
