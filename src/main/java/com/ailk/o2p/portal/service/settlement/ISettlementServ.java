package com.ailk.o2p.portal.service.settlement;

import java.math.BigDecimal;
 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.asiainfo.integration.o2p.web.bo.Org;
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

public interface ISettlementServ {
	public List<MainDataType> selectMainDataType(MainDataType mainDataType);
	public List<MainData> selectMainData(MainData mainData);
	public List<Map<String,Object>> selectOfferProdRel(Map<String,String> map);
	public List<Map<String,String>> getBasicTree(Map<String,Object> map);
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication);
	public List<Map<String,Object>> getSettle(Map<String,Object> map);
	public List<Map<String,Object>> allowUpdateCycleByRC(Map<String,Object> map);
	/**
	 * 
	 * @param partnerCode 第三方编码
	 * @param busiCode 对应销售品
	 * @param servCode 对应产品
	 * @param oldServCode 要复制的产品
	 */
	public void copySettleRuleList(String partnerCode, String busiCode, String servCode, String oldServCode);
	
	//账期定义表
	public BigDecimal addSettleCycleDef(SettleCycleDef settleCycleDef);
	public Integer updateSettleCycleDef(SettleCycleDef settleCycleDef);
	public SettleCycleDef querySettleCycleDef(SettleCycleDef settleCycleDef);
	
	//结算对象信息表
	public BigDecimal addSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);
	public Integer updateSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);
	public List<SettleSpBusiDef> querySettleSpBusiDef(SettleSpBusiDef settleSpBusiDef);

	//结算对象规则表
	public BigDecimal addSettleRule(SettleRule settleRule);
	public Integer updateSettleRule(SettleRule settleRule);
	public Integer delSettleRule(SettleRule settleRule);
	public List<SettleRule> querySettleRule(SettleRule settleRule);
	public List<SettleRule> querySettleRuleForGrid(Map<String,Object> map);
	public Integer cotSettleRuleForGrid(Map<String,Object> map);
	
	//结算规则关联运营商标
	public void addSettleRuleOrgRel(SettleRuleOrgRel settleRuleOrgRel) ;
	public void updateSettleRuleOrgRel(SettleRuleOrgRel settleRuleOrgRel) ;
	
	//结算对象规则明细-一次性费用
	public BigDecimal addSettleRuleOTC(SettleRuleOTC settleRuleOTC);
	public Integer updateSettleRuleOTC(SettleRuleOTC settleRuleOTC);
	public List<SettleRuleOTC> querySettleRuleOTC(SettleRuleOTC settleRuleOTC);
	
	//结算对象规则明细-周期性费用
	public BigDecimal addSettleRuleRC(SettleRuleRC settleRuleRC);
	public Integer updateSettleRuleRC(SettleRuleRC settleRuleRC);
	public List<SettleRuleRC> querySettleRuleRC(SettleRuleRC settleRuleRC);
	
	//结算对象规则明细-总量批价
	public BigDecimal addSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
	public Integer updateSettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
	public List<SettleRuleAggregation> querySettleRuleAggregation(SettleRuleAggregation settleRuleAggregation);
		
	//结算规则条件表
	public BigDecimal addSettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public Integer updateSettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public void deleteSettleRuleCondition(SettleRuleCondition settleRuleCondition);
	public List<SettleRuleCondition> querySettleRuleCondition(SettleRuleCondition settleRuleCondition);

	public void deletePardOfferSettlementDetail(String ruleId ,String ruleType)throws Exception;
	public JSONObject saveOrUpdateSettle(final HttpServletRequest request,
			final HttpServletResponse response,String formSubStr,Org orgBean)throws Exception;
	public JSONObject saveOrUpdateOTC(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception;
	public JSONObject saveOrUpdateRC(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception;
	public JSONObject saveOrUpdateSettleRuleAggregation(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception;
	//api
	public BigDecimal addSettleRuleCLC(SettleRuleCLC settleRuleCLC);
	public Integer updateSettleRuleCLC(SettleRuleCLC settleRuleCLC);
	public List<SettleRuleCLC> querySettleRuleCLC(SettleRuleCLC settleRuleCLC);
	
	public List<Map<String, Object>> queryOperatorId(Map<String, Object> map) ;
}