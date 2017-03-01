package com.ailk.o2p.portal.dao.price;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.RsSysCellDef;
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.bo.TimeSegDtl;
import com.ailk.o2p.portal.dao.pardproduct.PardProdDao;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class AttrSpecDao implements IAttrSpecDao {
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	public List<TimeSegDef> queryTimeSeqDef(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-attrSpec.queryTimeSegDef", map);
	}
	public Integer countTimeSeqDef(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-attrSpec.countTimeSegDef", map);
	}
	public Integer addTimeSeqDef(TimeSegDef timeSeqDef){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-attrSpec.addTimesegDef", timeSeqDef);
	}
	public Integer updateTimeSeqDef(TimeSegDef timeSeqDef){
		return (Integer) sqlMapDao.update("eaap-op2-portal-attrSpec.updateTimeSegDef", timeSeqDef);
	}
	
	
	public List<TimeSegDtl> queryTimeSeqDtl(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-attrSpec.queryTimeSegDtl", map);
	}
	public Integer addTimeSeqDtl(TimeSegDtl timeSeqDtl){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-attrSpec.addTimeSegDtl", timeSeqDtl);
	}
	public Integer updateTimeSeqDtl(TimeSegDtl timeSeqDtl){
		return (Integer) sqlMapDao.update("eaap-op2-portal-attrSpec.updateTimeSegDtl", timeSeqDtl);
	}
	
	public List<RsSysCellDef> queryRsSysCellDef(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-attrSpec.queryRsSysCellDef", map);
	}
	public Integer countRsSysCellDef(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-attrSpec.countRsSysCellDef", map);
	}
	
	public Integer addPricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-attrSpec.addPricngPlanAttr", pricngPlanAttr);
	}
	public Integer updatePricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return (Integer) sqlMapDao.update("eaap-op2-portal-attrSpec.updatePricngPlanAttr", pricngPlanAttr);
	}
	public List<PricngPlanAttr> queryPricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return sqlMapDao.queryForList("eaap-op2-portal-attrSpec.queryPricngPlanAttr", pricngPlanAttr);
	}
	public List<RsSysCellDef> selRsSysCellDef(Map<String, Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-attrSpec.selRsSysCellDef", map);
	}
	/**
	 * 删除时间副表数据
	 * @param timeSegDef
	 */
	@Override
	public void deleteTimeSeqDtl(TimeSegDef timeSegDef) {
		sqlMapDao.delete("eaap-op2-portal-attrSpec.deleteTimeSeqDtl", timeSegDef);
	}
	/**
	 * 检查时间数据有没有被使用
	 * @param segId
	 * @return
	 */
	@Override
	public Integer countDiscountPrice(Map paramMap) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-attrSpec.countDiscountPrice", paramMap);
	}
	/**
	 * 删除时间数据
	 * @param segId
	 */
	@Override
	public void deleteTimeSegDef(Map paramMap) {
		sqlMapDao.delete("eaap-op2-portal-attrSpec.deleteTimeSegDef", paramMap);
	}
	/**
	 * 定价计划
	 */
	public Integer addPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-attrSpec.addPricePlanLifeCycle", pricePlanLifeCycle);
	}
	public Integer updatePricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return (Integer) sqlMapDao.update("eaap-op2-portal-attrSpec.updatePricePlanLifeCycle", pricePlanLifeCycle);
	}
	public PricePlanLifeCycle queryPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return (PricePlanLifeCycle) sqlMapDao.queryForObject("eaap-op2-portal-attrSpec.queryPricePlanLifeCycle", pricePlanLifeCycle);
	}
}
