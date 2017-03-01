package com.ailk.o2p.portal.dao.pard;


import java.util.List;
import java.util.Map;

import com.linkage.rainbow.dao.SqlMapDAO;

@SuppressWarnings("unchecked")
public class PardSellDaoImpl implements PardSellDao{
	
	private SqlMapDAO sqlMapDao;

	public List<Map> queryProdSellList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryPardSell", map);
		
	}
	
	public List<Map> queryProdSellNum(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryPardSellNum", map);
		 
	}
	
	public List<Map> queryToAddProdList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryAddProd", map);
		 
	}

	public List<Map> queryToAddProdNum(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryAddProdNum", map);
		 
	}
	
	public List<Map> queryApplyInfo(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryApplyProdInfo", map);
		 
	}
	
	public List<Map> queryChannelId(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-pardSell.queryChannelId", map);
		 
	}
	
	public Integer saveChannelInfo(Map map) {
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardSell.saveChannelInfo", map);
	}
	
	public void saveOfferChannel(Map map) {
		sqlMapDao.insert("eaap-op2-portal-pardSell.saveOfferChannel", map);
	}
	
	public void cancelProduct(Map map){
		sqlMapDao.delete("eaap-op2-portal-pardSell.cancalProduct", map);
	}
	


	
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
}
