package com.ailk.o2p.portal.dao.settlement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.ScopeApplication;
import com.ailk.eaap.op2.bo.SettleCycleDef;
import com.ailk.eaap.op2.bo.SettleRule;
import com.ailk.eaap.op2.bo.SettleRuleAggregation;
import com.ailk.eaap.op2.bo.SettleRuleCLC;
import com.ailk.eaap.op2.bo.SettleRuleCondition;
import com.ailk.eaap.op2.bo.SettleRuleOTC;
import com.ailk.eaap.op2.bo.SettleRuleOrgRel;
import com.ailk.eaap.op2.bo.SettleRuleRC;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class SettlementDao implements ISettlementDao {
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	/****/
	public List<Map<String,Object>> selectOfferProdRel(Map<String,String> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.selectProduct", map);
	}
	/****/
	public BigDecimal insertSettleCycleDef(SettleCycleDef settleCycleDef){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleCycleDef", settleCycleDef);
	}
	public Integer updateSettleCycleDef(SettleCycleDef settleCycleDef){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleCycleDef", settleCycleDef);
	}
	public List<SettleCycleDef> querySettleCycleDef(SettleCycleDef settleCycleDef){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleCycleDef", settleCycleDef);
	}
	public List<Map<String,String>> getBasicTree(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.getBasicTree", map);
	}
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication){
		return sqlMapDao.queryForList("eaap-op2-portal-price.selectScopeApplication",scopeApplication);
	}
	public List<Map<String,Object>> getSettle(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.getSettle",map);
	}
	
	public List<Map<String,Object>> queryOperatorId(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.queryOperatorId",map);
	}
	
	public List<SettleRuleOrgRel> settleRuleOrgRelChangeOperator(Map<String, Object> map) {
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.settleRuleOrgRelChangeOperator",map);
	}
	
	public List<Map<String,Object>> allowUpdateCycleByRC(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.allowUpdateCycleByRC",map);
	}
	//结算对象信息表
	public BigDecimal insertSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleSPBusiDef", settleSpBusiDef);
	}
	public Integer updateSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleSPBusiDef", settleSpBusiDef);
	}
	public List<SettleSpBusiDef> querySettleSpBusiDef(SettleSpBusiDef settleSpBusiDef){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleSPBusiDef", settleSpBusiDef);
	}

	//结算对象规则表
	public BigDecimal insertSettleRule(SettleRule settleRule){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRule", settleRule);
	}
	public Integer updateSettleRule(SettleRule settleRule){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRule", settleRule);
	}
	public Integer delSettleRule(SettleRule settleRule){
		return sqlMapDao.update("eaap-op2-portal-settlement.delSettleRule", settleRule);
	}
	public List<SettleRule> querySettleRule(SettleRule settleRule){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRule", settleRule);
	}
	public List<SettleRule> querySettleRuleForGrid(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleForGrid", map);
	}
	public Integer cotSettleRuleForGrid(Map<String,Object> map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-settlement.cotSettleRuleForGrid", map);
	}
	
	//结算对象规则明细-一次性费用
	public BigDecimal insertSettleRuleOTC(SettleRuleOTC settleRuleOTC){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRuleOTC", settleRuleOTC);
	}
	public Integer updateSettleRuleOTC(SettleRuleOTC settleRuleOTC){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRuleOTC", settleRuleOTC);
	}
	public List<SettleRuleOTC> querySettleRuleOTC(SettleRuleOTC settleRuleOTC){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleOTC", settleRuleOTC);
	}
	
	//结算对象规则明细-周期性费用
	public BigDecimal insertSettleRuleRC(SettleRuleRC settleRuleRC){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRuleRC", settleRuleRC);
	}
	public Integer updateSettleRuleRC(SettleRuleRC settleRuleRC){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRuleRC", settleRuleRC);
	}
	public List<SettleRuleRC> querySettleRuleRC(SettleRuleRC settleRuleRC){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleRC", settleRuleRC);
	}
	
	//结算对象规则明细-总量批价
	public BigDecimal insertSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRuleAggregation", settleRuleAggregation);
	}
	public Integer updateSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRuleAggregation", settleRuleAggregation);
	}
	public List<SettleRuleAggregation> querySettleRuleAggregation(SettleRuleAggregation settleRuleAggregation){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleAggregation", settleRuleAggregation);
	}

	//结算规则条件表
	public BigDecimal insertSettleRuleCondition(SettleRuleCondition settleRuleCondition){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRuleCondition", settleRuleCondition);
	}
	public Integer updateSettleRuleCondition(SettleRuleCondition settleRuleCondition){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRuleCondition", settleRuleCondition);
	}
	public List<SettleRuleCondition> querySettleRuleCondition(SettleRuleCondition settleRuleCondition){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleCondition", settleRuleCondition);
	}
	
	//API结算周期
	public BigDecimal insertSettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return (BigDecimal) sqlMapDao.insert("eaap-op2-portal-settlement.addSettleRuleCLC", settleRuleCLC);
	}
	public Integer updateSettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return sqlMapDao.update("eaap-op2-portal-settlement.updateSettleRuleCLC", settleRuleCLC);
	}
	public List<SettleRuleCLC> querySettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return sqlMapDao.queryForList("eaap-op2-portal-settlement.querySettleRuleCLC", settleRuleCLC);
	}
	@Override
	public void deleteSettleRuleCondition(
			SettleRuleCondition settleRuleCondition) {
		// TODO Auto-generated method stub
		sqlMapDao.delete("eaap-op2-portal-settlement.deleteSettleRuleCondition", settleRuleCondition);
	}
}
