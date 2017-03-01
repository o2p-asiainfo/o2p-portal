package com.ailk.o2p.portal.service.settlement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.ProdOffer;
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
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.eaap.op2.settleRuleOrgRel.dao.SettleRuleOrgRelDao;
import com.ailk.o2p.portal.dao.pardoffer.PardOfferDao;
import com.ailk.o2p.portal.dao.settlement.ISettlementDao;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;

@Service
@Transactional
public class SettlementServ implements ISettlementServ {
	@Autowired
	private MainDataDao mainDataSqlDAO;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	@Autowired
	private ISettlementDao settlementDao;
	@Autowired
	private SettleRuleOrgRelDao settleRuleOrgRelDao;
	@Autowired
	private PardOfferDao pardOfferDao;

	public ISettlementDao getSettlementDao() {
		return settlementDao;
	}

	public void setSettlementDao(ISettlementDao settlementDao) {
		this.settlementDao = settlementDao;
	}

	
	public SettleRuleOrgRelDao getSettleRuleOrgRelDao() {
		return settleRuleOrgRelDao;
	}

	public void setSettleRuleOrgRelDao(SettleRuleOrgRelDao settleRuleOrgRelDao) {
		this.settleRuleOrgRelDao = settleRuleOrgRelDao;
	}

	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}

	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}

	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}

	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}

	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType);
	}

	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData);
	}

	public List<Map<String, Object>> selectOfferProdRel(Map<String, String> map) {
		return settlementDao.selectOfferProdRel(map);
	}

	public List<Map<String, String>> getBasicTree(Map<String, Object> map) {
		return settlementDao.getBasicTree(map);
	}

	public List<ScopeApplication> selectScopeApplication(
			ScopeApplication scopeApplication) {
		return settlementDao.selectScopeApplication(scopeApplication);
	}

	public List<Map<String, Object>> getSettle(Map<String, Object> map) {
		return settlementDao.getSettle(map);
	}
	
	public List<Map<String, Object>> queryOperatorId(Map<String, Object> map) {
		return settlementDao.queryOperatorId(map);
	}
	
	public List<SettleRuleOrgRel> settleRuleOrgRelChangeOperator(Map<String, Object> map) {
		return settlementDao.settleRuleOrgRelChangeOperator(map);
	}

	public List<Map<String, Object>> allowUpdateCycleByRC(
			Map<String, Object> map) {
		return settlementDao.allowUpdateCycleByRC(map);
	}

	public void copySettleRuleList(String partnerCode, String busiCode,
			String servCode, String oldServCode) {
		SettleRule settleRule = new SettleRule();
		settleRule.setPartnerCode(partnerCode);
		settleRule.setBusiCode(busiCode);
		settleRule.setServCode(oldServCode);
		settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
		List<SettleRule> settleRuleList = settlementDao
				.querySettleRule(settleRule);
		if (null != settleRuleList && settleRuleList.size() > 0) {
			BigDecimal srId = null;
			BigDecimal srDetailId = null;
			BigDecimal settleRuleId = null;
			BigDecimal settleRuleDetailId = null;
			Map<String, BigDecimal> bMap = new HashMap<String, BigDecimal>();
			for (SettleRule sr : settleRuleList) {
				srId = sr.getRuleId();
				sr.setServCode(servCode);
				settleRuleId = settlementDao.insertSettleRule(sr);

				if ("1".equals(sr.getRuleType().toString())) {
					SettleRuleRC settleRuleRCQury = new SettleRuleRC();
					settleRuleRCQury.setRuleId(srId);

					SettleRuleRC settleRuleRC = settlementDao
							.querySettleRuleRC(settleRuleRCQury).get(0);
					srDetailId = settleRuleRC.getRuleDetailId();

					settleRuleRC.setRuleId(settleRuleId);
					settleRuleDetailId = settlementDao
							.insertSettleRuleRC(settleRuleRC);

					bMap.put(srId.toString(), settleRuleId);
				} else if ("2".equals(sr.getRuleType().toString())) {
					SettleRuleOTC settleRuleOTCQury = new SettleRuleOTC();
					settleRuleOTCQury.setRuleId(srId);
					SettleRuleOTC settleRuleOTC = settlementDao
							.querySettleRuleOTC(settleRuleOTCQury).get(0);
					srDetailId = settleRuleOTC.getRuleDetailId();

					settleRuleOTC.setRuleId(settleRuleId);
					settleRuleDetailId = settlementDao
							.insertSettleRuleOTC(settleRuleOTC);

					bMap.put(srId.toString(), settleRuleId);
//				} else if ("3".equals(sr.getRuleType().toString())) {

				}
				if ("4".equals(sr.getRuleType().toString())) {
					SettleRuleAggregation settleRuleAggregationQury = new SettleRuleAggregation();
					settleRuleAggregationQury.setRuleId(srId);

					SettleRuleAggregation settleRuleAggregation = settlementDao
							.querySettleRuleAggregation(
									settleRuleAggregationQury).get(0);
					srDetailId = settleRuleAggregation.getRuleDetailId();

					StringBuffer sb = new StringBuffer();
					for (Map.Entry<String, BigDecimal> entry : bMap.entrySet()) {
						if (settleRuleAggregation.getRuleDetailInfo().indexOf(
								entry.getKey()) >= 0) {
							sb.append(entry.getValue()).append("/");
						}
					}

					settleRuleAggregation.setRuleId(settleRuleId);
					settleRuleAggregation.setRuleDetailInfo(sb.toString()
							.substring(0, sb.toString().length() - 1));
					settleRuleDetailId = settlementDao
							.insertSettleRuleAggregation(settleRuleAggregation);

				}

				SettleRuleCondition settleRuleCondition = new SettleRuleCondition();
				settleRuleCondition.setRuleDetailId(srDetailId);
				settleRuleCondition
						.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);

				List<SettleRuleCondition> settleRuleConditionList = settlementDao
						.querySettleRuleCondition(settleRuleCondition);
				if (null != settleRuleConditionList
						&& settleRuleConditionList.size() > 0) {
					for (SettleRuleCondition src : settleRuleConditionList) {
						src.setRuleDetailId(settleRuleDetailId);
						settlementDao.insertSettleRuleCondition(src);
					}
				}
			}
		}
	}

	/** 方法 **/
	// 账期定义表
	public BigDecimal addSettleCycleDef(SettleCycleDef settleCycleDef) {
		SettleCycleDef cycleDef = this.querySettleCycleDef(settleCycleDef);
		if(null==cycleDef){
			return settlementDao.insertSettleCycleDef(settleCycleDef);
		}else{
			cycleDef.setCycleCount(settleCycleDef.getCycleCount());
			cycleDef.setCycleType(settleCycleDef.getCycleType());
			cycleDef.setPriority(settleCycleDef.getPriority());
			cycleDef.setDescription(settleCycleDef.getDescription());
			cycleDef.setSyncFlag(3);
			
			settlementDao.updateSettleCycleDef(cycleDef);
			return cycleDef.getCycleDefId();
		}
		
	}

	public Integer updateSettleCycleDef(SettleCycleDef settleCycleDef) {
		return settlementDao.updateSettleCycleDef(settleCycleDef);
	}

	public SettleCycleDef querySettleCycleDef(
			SettleCycleDef settleCycleDef) {
		List<SettleCycleDef> settleCycleDefList = settlementDao.querySettleCycleDef(settleCycleDef);
		if(null==settleCycleDefList||settleCycleDefList.size()==0){
			return null;
		}
//		if(settleCycleDefList.size()>1){
//			//throw 
//		}
		return settleCycleDefList.get(0);
	}

	// 结算对象信息表
	public BigDecimal addSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef) {
		return settlementDao.insertSettleSpBusiDef(settleSpBusiDef);
	}

	public Integer updateSettleSpBusiDef(SettleSpBusiDef settleSpBusiDef) {
		return settlementDao.updateSettleSpBusiDef(settleSpBusiDef);
	}

	public List<SettleSpBusiDef> querySettleSpBusiDef(
			SettleSpBusiDef settleSpBusiDef) {
		return settlementDao.querySettleSpBusiDef(settleSpBusiDef);
	}

	// 结算对象规则表
	public BigDecimal addSettleRule(SettleRule settleRule) {
		return settlementDao.insertSettleRule(settleRule);
	}

	public Integer updateSettleRule(SettleRule settleRule) {
		return settlementDao.updateSettleRule(settleRule);
	}

	public Integer delSettleRule(SettleRule settleRule) {
		return settlementDao.delSettleRule(settleRule);
	}

	public List<SettleRule> querySettleRule(SettleRule settleRule) {
		return settlementDao.querySettleRule(settleRule);
	}

	public List<SettleRule> querySettleRuleForGrid(Map<String, Object> map) {
		return settlementDao.querySettleRuleForGrid(map);
	}

	public Integer cotSettleRuleForGrid(Map<String, Object> map) {
		return settlementDao.cotSettleRuleForGrid(map);
	}

	// 结算对象规则明细-一次性费用
	public BigDecimal addSettleRuleOTC(SettleRuleOTC settleRuleOTC) {
		return settlementDao.insertSettleRuleOTC(settleRuleOTC);
	}

	public Integer updateSettleRuleOTC(SettleRuleOTC settleRuleOTC) {
		return settlementDao.updateSettleRuleOTC(settleRuleOTC);
	}

	public List<SettleRuleOTC> querySettleRuleOTC(SettleRuleOTC settleRuleOTC) {
		return settlementDao.querySettleRuleOTC(settleRuleOTC);
	}

	// 结算对象规则明细-周期性费用
	public BigDecimal addSettleRuleRC(SettleRuleRC settleRuleRC) {
		return settlementDao.insertSettleRuleRC(settleRuleRC);
	}

	public Integer updateSettleRuleRC(SettleRuleRC settleRuleRC) {
		return settlementDao.updateSettleRuleRC(settleRuleRC);
	}

	public List<SettleRuleRC> querySettleRuleRC(SettleRuleRC settleRuleRC) {
		return settlementDao.querySettleRuleRC(settleRuleRC);
	}

	// 结算对象规则明细-总量批价
	public BigDecimal addSettleRuleAggregation(
			SettleRuleAggregation settleRuleAggregation) {
		return settlementDao.insertSettleRuleAggregation(settleRuleAggregation);
	}

	public Integer updateSettleRuleAggregation(
			SettleRuleAggregation settleRuleAggregation) {
		return settlementDao.updateSettleRuleAggregation(settleRuleAggregation);
	}

	public List<SettleRuleAggregation> querySettleRuleAggregation(
			SettleRuleAggregation settleRuleAggregation) {
		return settlementDao.querySettleRuleAggregation(settleRuleAggregation);
	}

	// 结算规则条件表
	public BigDecimal addSettleRuleCondition(
			SettleRuleCondition settleRuleCondition) {
		return settlementDao.insertSettleRuleCondition(settleRuleCondition);
	}

	public Integer updateSettleRuleCondition(
			SettleRuleCondition settleRuleCondition) {
		return settlementDao.updateSettleRuleCondition(settleRuleCondition);
	}

	
	public void addSettleRuleOrgRel(
			SettleRuleOrgRel settleRuleOrgRel) {
		 settleRuleOrgRelDao.insert(settleRuleOrgRel);
	}
	
	
	public void updateSettleRuleOrgRel(
			SettleRuleOrgRel settleRuleOrgRel) {
		 settleRuleOrgRelDao.updateSettleRuleOrgRelInfo(settleRuleOrgRel);
	}
	
	public void deleteSettleRuleOrgRel(
			SettleRuleOrgRel settleRuleOrgRel) {
		 settleRuleOrgRelDao.deleteSettleRuleOrgRelInfo(settleRuleOrgRel);
	}
	
	public List<SettleRuleCondition> querySettleRuleCondition(
			SettleRuleCondition settleRuleCondition) {
		return settlementDao.querySettleRuleCondition(settleRuleCondition);
	}
	
	public void deletePardOfferSettlementDetail(String ruleId ,String ruleType)throws Exception {
		SettleRule settleRule=new SettleRule();
		settleRule.setRuleId(new BigDecimal(ruleId));
		//settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_TEST);
		settleRule = this.querySettleRule(settleRule).get(0);
		SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
		if(allownDelSettleRuleDetail(settleRule.getRuleId(),settleRule.getPartnerCode(),settleRule.getBusiCode(),settleRule.getServCode())){
			throw new Exception("not allown delete "+ruleId+" settle rule");
		}
		if(StringUtils.isNotEmpty(ruleId)&&StringUtils.isNotEmpty(ruleType)){
			if("1".equals(ruleType)){
				SettleRuleRC settleRuleRC=new SettleRuleRC();
				settleRuleRC.setRuleId(new BigDecimal(ruleId));
				settleRuleRC = this.querySettleRuleRC(settleRuleRC).get(0);
				settleRuleCondition.setStatusCd("1300");
				settleRuleCondition.setRuleDetailId(settleRuleRC.getRuleDetailId());
				this.updateSettleRuleCondition(settleRuleCondition);
				
			}else if("2".equals(ruleType)){
				SettleRuleOTC settleRuleOTC=new SettleRuleOTC();
				settleRuleOTC.setRuleId(new BigDecimal(ruleId));
				settleRuleOTC = this.querySettleRuleOTC(settleRuleOTC).get(0);
				settleRuleCondition.setStatusCd("1300");
				settleRuleCondition.setRuleDetailId(settleRuleOTC.getRuleDetailId());
				this.updateSettleRuleCondition(settleRuleCondition);
				
//			}else if("3".equals(ruleType)){
				
			}else if("4".equals(ruleType)){
				SettleRuleAggregation settleRuleAggregation=new SettleRuleAggregation();
				settleRuleAggregation.setRuleId(new BigDecimal(ruleId));
				settleRuleAggregation = this.querySettleRuleAggregation(settleRuleAggregation).get(0);
				settleRuleCondition.setStatusCd("1300");
				settleRuleCondition.setRuleDetailId(settleRuleAggregation.getRuleDetailId());
				this.updateSettleRuleCondition(settleRuleCondition);
				
			}
			settleRule.setStatusCd("1300");
			settleRule.setSyncFlag(3);
			this.updateSettleRule(settleRule);
			
			SettleRuleOrgRel  settleRuleOrgRel=new SettleRuleOrgRel();
			settleRuleOrgRel.setRuleId(new BigDecimal(ruleId));
			this.deleteSettleRuleOrgRel(settleRuleOrgRel);
		}
	}
	public boolean allownDelSettleRuleDetail(BigDecimal ruleId,String partnerCode,String busiCode,String servCode){
		SettleRule settleRule = new SettleRule();
		settleRule.setPartnerCode(partnerCode);
		settleRule.setBusiCode(busiCode);
		settleRule.setServCode(servCode);
		settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
		settleRule.setRuleType(4);
		List<SettleRule> list = this.querySettleRule(settleRule);
		if(null==list||list.size()==0){
			return Boolean.FALSE;
		}
		
		settleRule = list.get(0);
		
		SettleRuleAggregation settleRuleAggregation = new SettleRuleAggregation();
		settleRuleAggregation.setRuleId(settleRule.getRuleId());
		settleRuleAggregation = this.querySettleRuleAggregation(settleRuleAggregation).get(0);
		return settleRuleAggregation.getRuleDetailInfo().indexOf(ruleId.toString())>=0;
	}
	
	
	public JSONObject saveOrUpdateSettle(final HttpServletRequest request,
			final HttpServletResponse response,String formSubStr,Org orgBean)throws Exception {
		//String[] formSubAry=formSubStr.split(",");//1_1,1_2
		//String num=formSubAry[0].split("_")[0]+"_";
		
		
		String cycleCount = getRequestValue(request, "settleCycleDef.cycleCount");
		String cycleType = getRequestValue(request, "settleCycleDef.cycleType");
		String priority = getRequestValue(request, "settleCycleDef.priority");
		String description = getRequestValue(request, "settleCycleDef.description");
		
		String servCode = getRequestValue(request, "settleSpBusiDef.servCode");
		String busiId = getRequestValue(request, "settleSpBusiDef.busiId");
		String busiCode = getRequestValue(request, "settleCycleDef.busiCode");
		String cycleDefId = getRequestValue(request, "settleCycleDef.cycleDefId");
		String forEffDate =getRequestValue(request,"settleSpBusiDef.forEffDate");
		String forExpDate =getRequestValue(request,"settleSpBusiDef.forExpDate");
		
		SettleCycleDef settleCycleDef=new SettleCycleDef();
		settleCycleDef.setCycleCount(Integer.parseInt(cycleCount));
		settleCycleDef.setCycleType(Integer.parseInt(cycleType));
		settleCycleDef.setPriority(Integer.parseInt(priority));
		settleCycleDef.setDescription(description);
		settleCycleDef.setBusiCode(busiCode);
		if(!StringUtil.isEmpty(cycleDefId)){
			settleCycleDef.setCycleDefId(new BigDecimal(cycleDefId));
		}
		
		SettleSpBusiDef settleSpBusiDef=new SettleSpBusiDef();
		String busiName = getRequestValue(request, "settleSpBusiDef.busiName");
		String busiType = getRequestValue(request, "settleSpBusiDef.busiType");
		String settleSpBusiDef_description = getRequestValue(request, "settleSpBusiDef.description");
		String billEndDate = getRequestValue(request, "settleCycleDef.billEndDate");
		settleSpBusiDef.setBusiName(busiName);
		settleSpBusiDef.setBusiType(Integer.parseInt(busiType));
		settleSpBusiDef.setDescription(settleSpBusiDef_description);
		settleSpBusiDef.setPartnerCode(orgBean.getOrgId().toString());
		settleSpBusiDef.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
		settleSpBusiDef.setForEffDate(forEffDate);
		settleSpBusiDef.setForExpDate(forExpDate);
		settleSpBusiDef.setEffDate("".equals(StringUtil.valueOf(settleSpBusiDef.getForEffDate()))?null:DateUtil.stringToDate(settleSpBusiDef.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
		settleSpBusiDef.setExpDate("".equals(StringUtil.valueOf(settleSpBusiDef.getForExpDate()))?null:DateUtil.stringToDate(settleSpBusiDef.getForExpDate().replace("-","/"), "yyyy/mm/dd")) ;
		settleSpBusiDef.setBusiCode(settleCycleDef.getBusiCode());
		if(!StringUtil.isEmpty(busiId)){
			settleSpBusiDef.setBusiId(new BigDecimal(busiId));
		}
		if(!StringUtil.isEmpty(servCode)){
			settleSpBusiDef.setServCode(servCode);
		}
		
		settleCycleDef.setBillEndDate(billEndDate.replace("-","").replace("/",""));
		
		String actionType = getRequestValue(request, "actionType");
		JSONObject json = new JSONObject();
		if("add".equals(actionType)){
			settleCycleDef.setAcctType(40);
			settleCycleDef.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleCycleDef.setSyncFlag(1);
			
			settleCycleDef.setPartnerCode(orgBean.getOrgId().toString());
			settleCycleDef.setEffDate(settleSpBusiDef.getEffDate());
			settleCycleDef.setExpDate(settleSpBusiDef.getExpDate());
			
			cycleDefId = this.addSettleCycleDef(settleCycleDef).toString();
			settleSpBusiDef.setSyncFlag(1);
			busiId = this.addSettleSpBusiDef(settleSpBusiDef).toString();
			json.put("cycleDefId", cycleDefId);
			json.put("busiId", busiId);
			json.put("settleCycleDefSyncFlag", 1);
			json.put("settleSpBusiDefSyncFlag", 1);
			//定价计划规则的子规则
//			String type=null;
//			for(int i=0;i<formSubAry.length;i++){
//				type=formSubAry[i].split("_")[1];//1_1
//				if("1".equals(type))saveOrUpdateRC(request,response,formSubAry[i],orgBean);//rc
//				else if("2".equals(type))saveOrUpdateOTC(request,response,formSubAry[i],orgBean);//otc
//				else if("4".equals(type))saveOrUpdateSettleRuleAggregation(request,response,formSubAry[i],orgBean);//agg
//			}
		}else{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("partnerCode", orgBean.getOrgId());
			map.put("busiCode", settleSpBusiDef.getBusiCode());
			map.put("servCode", settleSpBusiDef.getServCode());
			map.put("statusCd", EAAPConstants.STATUS_CD_FOR_ADD);
			List<Map<String,Object>> allowUpdateCycleByRCList = this.allowUpdateCycleByRC(map);
//			if(null==allowUpdateCycleByRCList||allowUpdateCycleByRCList.size()==0){
////				throw new Exception("allowUpdateCycleByRCList not exit data!!!!!!!!!allow to update!!!!!!!");
//				
//			}else{
			 if(null!=allowUpdateCycleByRCList&&allowUpdateCycleByRCList.size()>0){
					 
				Map<String,Object> tempM = allowUpdateCycleByRCList.get(0);
				if(3!=settleCycleDef.getCycleType()&&null!=tempM&&null!=tempM.get("ACCUMDTAETYPE")&&"1".equals(tempM.get("ACCUMDTAETYPE").toString())){
					//log.info("not allow update this data!!!");
//					retMap.put("code", "1");
//					json.putAll(retMap);
//					DataInteractiveUtil.actionResponsePage(getResponse(), json);
//					return NONE;
					throw new Exception("not allow update this data!");
//				}else{
					//log.info("allowUpdateCycleByRCList exit date!!but the accumDtaeOffset is null!!!!");
				}
			}
			int newSyncFlag = getSyncFlag(getRequestValue(request, "settleCycleDef.syncFlag"));
			settleCycleDef.setSyncFlag(newSyncFlag);
			this.updateSettleCycleDef(settleCycleDef);
			
			int newSettleSpBusiDefSyncFlag = getSyncFlag(getRequestValue(request, "settleSpBusiDef.syncFlag"));
			settleSpBusiDef.setSyncFlag(newSettleSpBusiDefSyncFlag);
			this.updateSettleSpBusiDef(settleSpBusiDef);
			json.put("cycleDefId", cycleDefId);
			json.put("busiId", busiId);
			json.put("settleCycleDefSyncFlag", newSyncFlag);
			json.put("settleSpBusiDefSyncFlag", newSettleSpBusiDefSyncFlag);
//			if(null!=settleSpBusiDef.getBusiId()&&!"".equals(settleSpBusiDef.getBusiId())){
//				settleSpBusiDef.setSyncFlag(3);
//				this.updateSettleSpBusiDef(settleSpBusiDef);
//				busiId = settleSpBusiDef.getBusiId().toString();
//			}else{
//				settleSpBusiDef.setSyncFlag(1);
//				busiId = this.addSettleSpBusiDef(settleSpBusiDef).toString();
//			}
		}
		return json;
	}
	private int getSyncFlag(String oldSyncFlag){
		if("2".equals(oldSyncFlag)){
			return 3;
		}
		else if("1".equals(oldSyncFlag)){
			return 1;
		}
		else{
			return 3;
		}
	}
	public JSONObject saveOrUpdateOTC(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception {
//		key=key+"_";
		JSONObject json = new JSONObject();
		String ruleName =getRequestValue(request,"settleRule.ruleName");
		String chargeDir =getRequestValue(request,"settleRule.chargeDir");
		String ruleId1 =getRequestValue(request,"settleRule.ruleId");
		String busiCode =getRequestValue(request,"settleRule.busiCode");
		String servCode =getRequestValue(request,"settleRule.servCode");
		String moneyUnit =getRequestValue(request,"settleRule.moneyUnit");
		String forEffDate =getRequestValue(request,"settleRule.forEffDate");
		String forExpDate =getRequestValue(request,"settleRule.forExpDate");
		String description = getRequestValue(request, "settleRule.description");
		String operatorOrgId=getRequestValue(request, "operatorOrgId");
		String operatorForSettlement=getRequestValue(request, "operatorForSettlement");
		String accumType =getRequestValue(request,"settleRuleOTC.accumType");
		String attrSpecId =getRequestValue(request,"settleRuleOTC.attrSpecId");
		SettleRule settleRule=new SettleRule();
		SettleRuleOrgRel settleRuleOrgRel=new SettleRuleOrgRel();
		if(!StringUtil.isEmpty(ruleId1)){
			settleRule.setRuleId(new BigDecimal(ruleId1));
			settleRuleOrgRel.setRuleId(new BigDecimal(ruleId1));
		}
		if(!StringUtil.isEmpty(operatorOrgId)){
				settleRuleOrgRel.setOrgId(Integer.parseInt(operatorOrgId));
		}
		settleRule.setRuleName(ruleName);
		settleRule.setChargeDir(Integer.parseInt(chargeDir));
		settleRule.setBusiCode(busiCode);
		settleRule.setServCode(servCode);
		settleRule.setMoneyUnit(Integer.parseInt(moneyUnit));
		settleRule.setForEffDate(forEffDate);
		settleRule.setForExpDate(forExpDate);
		settleRule.setDescription(description);
		SettleRuleOTC settleRuleOTC=new SettleRuleOTC();
		settleRuleOTC.setAccumType(Integer.parseInt(accumType));
		settleRuleOTC.setAttrSpecId(attrSpecId);
		String ruleDetailId1 = getRequestValue(request,"settleRuleRC.ruleDetailId");
		if(!StringUtil.isEmpty(ruleDetailId1)){
			settleRuleOTC.setRuleDetailId(new BigDecimal(ruleDetailId1));
		}
		String baseValue = getRequestValue(request,"settleRuleCondition.baseValue");
		SettleRuleCondition settleRuleCondition=new SettleRuleCondition();
		settleRuleCondition.setBaseValue(baseValue);
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			settleRule.setPartnerCode(orgBean.getOrgId().toString());
			settleRule.setRuleType(2);
			settleRule.setSyncFlag(1);
			settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleRule.setPriority(Integer.parseInt(EAAPConstants.SETTLE_PRIORITY_NUM));
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setRaiseFlag(0);
			BigDecimal ruleId = this.addSettleRule(settleRule);
			
			//添加ruleId标识
			settleRuleOrgRel.setRuleId(ruleId);
			  //针对新增只添加总规则，修改后新增子规则的场景
			if(settleRuleOrgRel.getOrgId()==null){
				if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			   }
			}
			
			settleRuleOTC.setRuleId(ruleId);
			settleRuleOTC.setUserTime(settleRule.getEffDate());
			BigDecimal ruleDetailId = this.addSettleRuleOTC(settleRuleOTC);
			
			
			settleRuleCondition.setRuleDetailId(ruleDetailId);
			settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			this.addSettleRuleCondition(settleRuleCondition);
			this.addSettleRuleOrgRel(settleRuleOrgRel);
			json.put("ruleId", ruleId);
			json.put("ruleDetailId", ruleDetailId);
			json.put("syncFlag", 1);
		}else{
			int newSyncFlag =getSyncFlag(getRequestValue(request, "settleRule.syncFlag"));
			settleRule.setSyncFlag(newSyncFlag);
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			this.updateSettleRule(settleRule);
			if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			}
			this.updateSettleRuleOrgRel(settleRuleOrgRel);
			
			settleRuleOTC.setRuleId(settleRule.getRuleId());
			settleRuleOTC.setUserTime(settleRule.getEffDate());
			this.updateSettleRuleOTC(settleRuleOTC);
			
			settleRuleOTC = this.querySettleRuleOTC(settleRuleOTC).get(0);
			
			SettleRuleCondition settleRuleConditionQuery = new SettleRuleCondition();
			settleRuleConditionQuery.setRuleDetailId(settleRuleOTC.getRuleDetailId());
			List<SettleRuleCondition> settleRuleConditionList = this.querySettleRuleCondition(settleRuleConditionQuery);
			if(null!=settleRuleConditionList && settleRuleConditionList.size()==1){
				settleRuleCondition.setRuleConditionId(settleRuleConditionList.get(0).getRuleConditionId());
				this.updateSettleRuleCondition(settleRuleCondition);
			}else{
				throw new BusinessException(ExceptionCommon.WebExceptionCode, "one time Settle's rule condition is error!", null);
			}
			
			json.put("ruleId", ruleId1);
			json.put("ruleDetailId", ruleDetailId1);
			json.put("syncFlag", newSyncFlag);
		}
		return json;
	}
	public JSONObject saveOrUpdateRC(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception {
//		key=key+"_";
		JSONObject json = new JSONObject();
		String ruleId1 =getRequestValue(request,"settleRule.ruleId");
		String busiCode =getRequestValue(request,"settleRule.busiCode");
		String servCode =getRequestValue(request,"settleRule.servCode");
		String ruleName =getRequestValue(request,"settleRule.ruleName");
		String chargeDir =getRequestValue(request,"settleRule.chargeDir");
		String moneyUnit =getRequestValue(request,"settleRule.moneyUnit");
		String forEffDate =getRequestValue(request,"settleRule.forEffDate");
		String forExpDate =getRequestValue(request,"settleRule.forExpDate");
		String description = getRequestValue(request, "settleRule.description");
		String operatorOrgId=getRequestValue(request, "operatorOrgId");
		String operatorForSettlement=getRequestValue(request, "operatorForSettlement");
		
		SettleRule settleRule=new SettleRule();
		SettleRuleOrgRel settleRuleOrgRel=new SettleRuleOrgRel();
		if(!StringUtil.isEmpty(ruleId1)){
			settleRule.setRuleId(new BigDecimal(ruleId1));
			settleRuleOrgRel.setRuleId(new BigDecimal(ruleId1));
		}
		if(!StringUtil.isEmpty(operatorOrgId)){
				settleRuleOrgRel.setOrgId(Integer.parseInt(operatorOrgId));
		}
		settleRule.setRuleName(ruleName);
		settleRule.setChargeDir(Integer.parseInt(chargeDir));
		settleRule.setBusiCode(busiCode);
		settleRule.setServCode(servCode);
		settleRule.setMoneyUnit(Integer.parseInt(moneyUnit));
		settleRule.setForEffDate(forEffDate);
		settleRule.setForExpDate(forExpDate);
		settleRule.setDescription(description);
		
		
		
		String ruleDetailId1 =getRequestValue(request,"settleRuleRC.ruleDetailId");
		String accumType =getRequestValue(request,"settleRuleRC.accumType");
		String accumDateType =getRequestValue(request,"settleRuleRC.accumDateType");
		String accumDateOffset =getRequestValue(request,"settleRuleRC.accumDateOffset");
		String reduceRes =getRequestValue(request,"settleRuleRC.reduceRes");
		String attrSpecId =getRequestValue(request,"settleRuleRC.attrSpecId");
		SettleRuleRC settleRuleRC=new SettleRuleRC();
		if(!StringUtil.isEmpty(ruleDetailId1)){
			settleRuleRC.setRuleDetailId(new BigDecimal(ruleDetailId1));
		}
		settleRuleRC.setAccumType(Integer.parseInt(accumType));
		settleRuleRC.setAttrSpecId(attrSpecId);
		settleRuleRC.setAccumDateType(Integer.parseInt(accumDateType));
		settleRuleRC.setAccumDateOffset(Integer.parseInt(accumDateOffset));
		settleRuleRC.setReduceRes(Integer.parseInt(reduceRes));
		String ruleConditionId=getRequestValue(request,"ruleConditionId");
		String upValue=getRequestValue(request,"upValue");
		String downValue=getRequestValue(request,"downValue");
		String ratioNumerator=getRequestValue(request,"ratioNumerator");
		String baseValue=getRequestValue(request,"baseValue");
		String[] ruleConditionIds =!StringUtil.isEmpty(ruleConditionId)?ruleConditionId.split(";"):new String[] {};
		String[] upValues = !StringUtil.isEmpty(upValue)?upValue.split(";"):new String[] {};
		String[] downValues = !StringUtil.isEmpty(downValue)?downValue.split(";"):new String[] {};
		String[] ratioNumerators = !StringUtil.isEmpty(ratioNumerator)?ratioNumerator.split(";"):new String[] {};
		String[] baseValues =!StringUtil.isEmpty(baseValue)?baseValue.split(";"):new String[] {};
		SettleRuleCondition settleRuleCondition=null;
		
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			settleRule.setRuleType(1);
			settleRule.setSyncFlag(1);
			settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleRule.setPartnerCode(orgBean.getOrgId().toString());
			settleRule.setPriority(Integer.parseInt(EAAPConstants.SETTLE_PRIORITY_NUM));
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setRaiseFlag(0);
			BigDecimal ruleId = this.addSettleRule(settleRule);
			//添加ruleId标识
			settleRuleOrgRel.setRuleId(ruleId);
		    //针对新增只添加总规则，修改后新增子规则的场景
			if(settleRuleOrgRel.getOrgId()==null){
				if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			   }
			}
			settleRuleRC.setRuleId(ruleId);
			settleRuleRC.setStartUserTime(settleRule.getEffDate());
			BigDecimal ruleDetailId = this.addSettleRuleRC(settleRuleRC);
			
			if(upValues.length>0){
				for(int i=0;i<upValues.length;i++){
					settleRuleCondition = new SettleRuleCondition();
					settleRuleCondition.setRuleDetailId(ruleDetailId);
					settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
					settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
					settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
					settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
					settleRuleCondition.setRatioDemominator(1);
					settleRuleCondition.setBaseValue(baseValues[i]);
					this.addSettleRuleCondition(settleRuleCondition);
				}
			}
			this.addSettleRuleOrgRel(settleRuleOrgRel);
			json.put("ruleId", ruleId);
			json.put("ruleDetailId", ruleDetailId);
			json.put("syncFlag", 1);
		}else{
			int newSyncFlag =getSyncFlag(getRequestValue(request, "settleRule.syncFlag"));
			settleRule.setSyncFlag(newSyncFlag);
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			this.updateSettleRule(settleRule);
			if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			}
			this.updateSettleRuleOrgRel(settleRuleOrgRel);
			settleRuleRC.setRuleId(settleRule.getRuleId());
			this.updateSettleRuleRC(settleRuleRC);
			json.put("ruleId", ruleId1);
			json.put("ruleDetailId", ruleDetailId1);
			json.put("syncFlag", newSyncFlag);
			
			//更新的时候先删除全部的区间，再重新添加
			SettleRuleCondition settleRuleConditionQuery= new SettleRuleCondition();
			settleRuleConditionQuery.setRuleDetailId(settleRuleRC.getRuleDetailId());
			List<SettleRuleCondition> settleRuleConditionList = this.querySettleRuleCondition(settleRuleConditionQuery);
			
			for(SettleRuleCondition r : settleRuleConditionList){
				this.deleteSettleRuleCondition(r);
			}
			
			for(int i=0;i<upValues.length;i++){
				settleRuleCondition = new SettleRuleCondition();
				settleRuleCondition.setRuleDetailId(settleRuleRC.getRuleDetailId());
				settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
				settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
				settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
				settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
				settleRuleCondition.setRatioDemominator(1);
				settleRuleCondition.setBaseValue(baseValues[i]);
				
			    this.addSettleRuleCondition(settleRuleCondition);
			}
		}
		return json;
	}
	public JSONObject saveOrUpdateSettleRuleAggregation(final HttpServletRequest request,
			final HttpServletResponse response,String key,Org orgBean)throws Exception {
//		key=key+"_";
		JSONObject json = new JSONObject();
		String ruleId1 =getRequestValue(request,"settleRule.ruleId");
		String busiCode =getRequestValue(request,"settleRule.busiCode");
		String servCode =getRequestValue(request,"settleRule.servCode");
		String ruleName =getRequestValue(request,"settleRule.ruleName");
		String chargeDir =getRequestValue(request,"settleRule.chargeDir");
		String moneyUnit =getRequestValue(request,"settleRule.moneyUnit");
		String priority =getRequestValue(request,"settleRule.priority");
		String forEffDate =getRequestValue(request,"settleRule.forEffDate");
		String forExpDate =getRequestValue(request,"settleRule.forExpDate");
		String description = getRequestValue(request, "settleRule.description");
		String operatorOrgId=getRequestValue(request, "operatorOrgId");
		String operatorForSettlement=getRequestValue(request, "operatorForSettlement");
		
		//查询销售品信息
		ProdOffer prodOfferQuery=new ProdOffer();
		prodOfferQuery.setProdOfferId(new BigDecimal(servCode));
		ProdOffer prodOffer=pardOfferDao.selectProdOffer(prodOfferQuery);
		String prodOfferType=prodOffer.getOfferType();
		
		//换算优先级
		int priorityForSettlement=calculatePriority(Integer.parseInt(priority),prodOfferType);
		
		SettleRule settleRule=new SettleRule();
		SettleRuleOrgRel settleRuleOrgRel=new SettleRuleOrgRel();
		if(!StringUtil.isEmpty(ruleId1)){
			settleRule.setRuleId(new BigDecimal(ruleId1));
			settleRuleOrgRel.setRuleId(new BigDecimal(ruleId1));
		}
		if(!StringUtil.isEmpty(operatorOrgId)){
				settleRuleOrgRel.setOrgId(Integer.parseInt(operatorOrgId));
		}
		settleRule.setRuleName(ruleName);
		settleRule.setChargeDir(Integer.parseInt(chargeDir));
		settleRule.setBusiCode(busiCode);
		settleRule.setServCode(servCode);
		settleRule.setMoneyUnit(Integer.parseInt(moneyUnit));
		settleRule.setForEffDate(forEffDate);
		settleRule.setForExpDate(forExpDate);
		settleRule.setDescription(description);
		
		
		
		settleRule.setPriority(priorityForSettlement);
		
		String ruleConditionId=getRequestValue(request,"ruleConditionId");
		String upValue=getRequestValue(request,"upValue");
		String downValue=getRequestValue(request,"downValue");
		String ratioNumerator=getRequestValue(request,"ratioNumerator");
		String ratioDemominator=getRequestValue(request,"ratioDemominator");
		String baseValue=getRequestValue(request,"baseValue");
		String[] ruleConditionIds =!StringUtil.isEmpty(ruleConditionId)?ruleConditionId.split(";"):new String[] {};
		String[] upValues = !StringUtil.isEmpty(upValue)?upValue.split(";"):new String[] {};
		String[] downValues = !StringUtil.isEmpty(downValue)?downValue.split(";"):new String[] {};
		String[] ratioNumerators = !StringUtil.isEmpty(ratioNumerator)?ratioNumerator.split(";"):new String[] {};
		String[] ratioDemominators = !StringUtil.isEmpty(ratioDemominator)?ratioDemominator.split(";"):new String[] {};
		String[] baseValues =!StringUtil.isEmpty(baseValue)?baseValue.split(";"):new String[] {};
		
		String ruleDetailId1 =getRequestValue(request,"settleRuleAggregation.ruleDetailId");
		String ruleDetailInfo =getRequestValue(request,"settleRuleAggregation.ruleDetailInfo");
		String aggregationItem =getRequestValue(request,"settleRuleAggregation.aggregationItem");
		SettleRuleAggregation settleRuleAggregation=new SettleRuleAggregation();
		if(!StringUtil.isEmpty(ruleDetailId1)){
			settleRuleAggregation.setRuleDetailId(new BigDecimal(ruleDetailId1));
		}
		settleRuleAggregation.setRuleDetailInfo(ruleDetailInfo);
		settleRuleAggregation.setAggregationItem(Integer.parseInt(aggregationItem));
		SettleRuleCondition settleRuleCondition=null;
		String actionType = getRequestValue(request, "actionType");
		if("add".equals(actionType)){
			settleRule.setRuleType(4);
			settleRule.setSyncFlag(1);
			settleRule.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			settleRule.setPartnerCode(orgBean.getOrgId().toString());
			settleRule.setPriority(priorityForSettlement);
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setRaiseFlag(0);
			BigDecimal ruleId = this.addSettleRule(settleRule);
			//添加ruleId标识
			settleRuleOrgRel.setRuleId(ruleId);
			  //针对新增只添加总规则，修改后新增子规则的场景
			if(settleRuleOrgRel.getOrgId()==null){
				if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			   }
			}
			settleRuleAggregation.setRuleId(ruleId);
			BigDecimal ruleDetailId = this.addSettleRuleAggregation(settleRuleAggregation);
			
			if(upValues.length>0){
				for(int i=0;i<upValues.length;i++){
					settleRuleCondition = new SettleRuleCondition();
					settleRuleCondition.setRuleDetailId(ruleDetailId);
					settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
					settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
					settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
					settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
					settleRuleCondition.setRatioDemominator(Integer.parseInt(ratioDemominators[i]));
					settleRuleCondition.setBaseValue(baseValues[i]);
					this.addSettleRuleCondition(settleRuleCondition);
				}
			}
			this.addSettleRuleOrgRel(settleRuleOrgRel);
			json.put("ruleId", ruleId);
			json.put("ruleDetailId", ruleDetailId);
			json.put("syncFlag", 1);
		}else{
			int newSyncFlag =getSyncFlag(getRequestValue(request, "settleRule.syncFlag"));
			settleRule.setSyncFlag(newSyncFlag);
			settleRule.setEffDate("".equals(StringUtil.valueOf(settleRule.getForEffDate()))?null:DateUtil.stringToDate(settleRule.getForEffDate().replace("-","/"), "yyyy/MM/dd")) ;
			settleRule.setExpDate("".equals(StringUtil.valueOf(settleRule.getForExpDate()))?null:DateUtil.stringToDate(settleRule.getForExpDate().replace("-","/"), "yyyy/MM/dd")) ;
			this.updateSettleRule(settleRule);
			if(!StringUtil.isEmpty(operatorForSettlement)){
					settleRuleOrgRel.setOrgId(Integer.parseInt(operatorForSettlement));
			}
			this.updateSettleRuleOrgRel(settleRuleOrgRel);
			settleRuleAggregation.setRuleId(settleRule.getRuleId());
			this.updateSettleRuleAggregation(settleRuleAggregation);
			json.put("ruleId", ruleId1);
			json.put("ruleDetailId", ruleDetailId1);
			json.put("syncFlag", newSyncFlag);
			
			//更新的时候先删除全部的区间，再重新添加
			SettleRuleCondition settleRuleConditionQuery= new SettleRuleCondition();
			settleRuleConditionQuery.setRuleDetailId(settleRuleAggregation.getRuleDetailId());
			List<SettleRuleCondition> settleRuleConditionList = this.querySettleRuleCondition(settleRuleConditionQuery);
			
			for(SettleRuleCondition r : settleRuleConditionList){
				this.deleteSettleRuleCondition(r);
			}
			
			for(int i=0;i<upValues.length;i++){
				settleRuleCondition = new SettleRuleCondition();
				settleRuleCondition.setRuleDetailId(settleRuleAggregation.getRuleDetailId());
				settleRuleCondition.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
				settleRuleCondition.setUpValue(Integer.parseInt(upValues[i]));
				settleRuleCondition.setDownValue(Integer.parseInt(downValues[i]));
				settleRuleCondition.setRatioNumerator(ratioNumerators[i]);
				settleRuleCondition.setRatioDemominator(Integer.parseInt(ratioDemominators[i]));
				settleRuleCondition.setBaseValue(baseValues[i]);
				
				this.addSettleRuleCondition(settleRuleCondition);
			}
		}
		return json;
	}
	protected String getRequestValue(final HttpServletRequest request,
			String paramName, boolean escape) {
		String paramValue = request.getParameter(paramName);
		if (null != paramValue) {
			paramValue = StringUtils.trim(paramValue);
			if (escape) {
				paramValue = StringEscapeUtils.escapeHtml(paramValue);
				paramValue = StringEscapeUtils.escapeSql(paramValue);
			}
			return paramValue;
		} else {
			return StringUtils.EMPTY;
		}
	}
	protected String getRequestValue(final HttpServletRequest request,
			String paramName) {
		return getRequestValue(request, paramName, true);
	}
//	protected String getI18nMessage(String key,PropertiesLoader i18nLoader) {
//		return i18nLoader.getProperty(key);
//	}
	
	
	   private  int calculatePriority(int billingPriority, String prodOfferType) {
			int result = 0;
			if("11".equals(prodOfferType)){//Main Offer
				result=600 - 10*billingPriority;
			}else if("12".equals(prodOfferType)){//Add on offer
				result=1000 - 10*billingPriority;
			}
			return result;
			
		}
	//API结算周期
	public BigDecimal addSettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return settlementDao.insertSettleRuleCLC(settleRuleCLC);
	}
	public Integer updateSettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return settlementDao.updateSettleRuleCLC(settleRuleCLC);
	}
	public List<SettleRuleCLC> querySettleRuleCLC(SettleRuleCLC settleRuleCLC){
		return settlementDao.querySettleRuleCLC(settleRuleCLC);
	}

	@Override
	public void deleteSettleRuleCondition(
			SettleRuleCondition settleRuleCondition) {
		// TODO Auto-generated method stub
		 settlementDao.deleteSettleRuleCondition(settleRuleCondition);
	}
}
