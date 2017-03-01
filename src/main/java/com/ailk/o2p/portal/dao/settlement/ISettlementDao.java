package com.ailk.o2p.portal.dao.settlement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.RsSysCellDef;
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
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.bo.TimeSegDtl;

@SuppressWarnings("all")
public interface ISettlementDao {
	/**其余方法**/
	public List<Map<String,Object>> selectOfferProdRel(Map<String,String> map);
	public List<Map<String,String>> getBasicTree(Map<String,Object> map);
	public List<Map<String,Object>> getSettle(Map<String,Object> map);
	public List<Map<String,Object>> allowUpdateCycleByRC(Map<String,Object> map);
	//适用范围
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication);
	
	//账期定义表
	public BigDecimal insertSettleCycleDef(SettleCycleDef settleCycleDef);
	public Integer updateSettleCycleDef(SettleCycleDef settleCycleDef);
	public List<SettleCycleDef> querySettleCycleDef(SettleCycleDef settleCycleDef);
	
	//结算对象信息表
	public BigDecimal insertSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);
	public Integer updateSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);
	public List<SettleSpBusiDef> querySettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);

	//结算对象规则表
	public BigDecimal insertSettleRule(SettleRule settleRule);
	public Integer updateSettleRule(SettleRule settleRule);
	public Integer delSettleRule(SettleRule settleRule);
	public List<SettleRule> querySettleRule(SettleRule settleRule);
	public List<SettleRule> querySettleRuleForGrid(Map<String,Object> map);
	public Integer cotSettleRuleForGrid(Map<String,Object> map);
	
	//结算对象规则明细-一次性费用
	public BigDecimal insertSettleRuleOTC(SettleRuleOTC settleRuleOTC);
	public Integer updateSettleRuleOTC(SettleRuleOTC settleRuleOTC);
	public List<SettleRuleOTC> querySettleRuleOTC(SettleRuleOTC settleRuleOTC);
	
	//结算对象规则明细-周期性费用
	public BigDecimal insertSettleRuleRC(SettleRuleRC settleRuleRC);
	public Integer updateSettleRuleRC(SettleRuleRC settleRuleRC);
	public List<SettleRuleRC> querySettleRuleRC(SettleRuleRC settleRuleRC);
	
	//结算对象规则明细-总量批价
	public BigDecimal insertSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
	public Integer updateSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
	public List<SettleRuleAggregation> querySettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
	
	//结算规则条件表
	public BigDecimal insertSettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public Integer updateSettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public List<SettleRuleCondition> querySettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public void deleteSettleRuleCondition(SettleRuleCondition settleRuleCondition);

	public BigDecimal insertSettleRuleCLC(SettleRuleCLC settleRuleCLC);
	public Integer updateSettleRuleCLC(SettleRuleCLC settleRuleCLC);
	public List<SettleRuleCLC> querySettleRuleCLC(SettleRuleCLC settleRuleCLC);
	
	public List<Map<String,Object>> queryOperatorId(Map<String,Object> map);
	
	public List<SettleRuleOrgRel> settleRuleOrgRelChangeOperator(Map<String, Object> map) ;
}
