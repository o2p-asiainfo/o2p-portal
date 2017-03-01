package com.ailk.o2p.portal.dao.savePrivilege;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class SavePrivilegeDao implements ISavePrivilegeDao{

	@Autowired
	private SqlMapDAO sqlMapDao;
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	public Integer cntAttrSpec(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countSpec", map);
	}
	public Integer cntServiceSpec(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countServiceSpec", map);
	}
	public Integer cntProduct(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countProduct", map);
	}
	public Integer cntOffer(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countOffer", map);
	}
	
	public Integer cntPricePlan(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countPricePlan", map);
	}
	
	public Integer cntPricePlanRule(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countPricePlanRule", map);
	}
	
	public Integer cntSettlement(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countSettlement", map);
	}
	public Integer cntSettlementRule(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countSettlementRule", map);
	}
	
	public Integer cntApp(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countApp", map);
	}
	
	public Integer cntSys(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countSys", map);
	}
	
	public Integer cntSysPricePlan(Map<String,String> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-savePrivilege.countSysPricePlan", map);
	}
}
