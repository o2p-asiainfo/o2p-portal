package com.ailk.o2p.portal.dao.productstore;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Directory;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository
public class ProductStoreDaoImpl implements IProductStoreDao {
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	
	@Override
	public List<Directory> queryProductCategoryList(Map map) {
		return (List<Directory>)sqlMapDao.queryForList("eaap-op2-portal-productsotre.queryProductCategory", map);
	}

	@Override
	public List<Map> queryProductList(Map map) {
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-productsotre.queryProductList", map);
	}

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	@Override
	public List<Map> queryNewProductList(Map map) {
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-productsotre.queryNewProductList", map);
	}

	@Override
	public List<Map> queryCRProductList(Map map) {
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-productsotre.queryCRProductList", map);
	}

	@Override
	public Integer queryProductOfferCnt(Map<String, Object> paraMap) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-productsotre.queryProductOfferCnt", paraMap);
	}
	
	
}