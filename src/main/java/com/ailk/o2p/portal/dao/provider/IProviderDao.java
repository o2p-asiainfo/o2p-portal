package com.ailk.o2p.portal.dao.provider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.Contract;
import com.ailk.eaap.op2.bo.ContractFormat;
import com.ailk.eaap.op2.bo.ContractVersion;
import com.ailk.eaap.op2.bo.DirSerList;
import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.NodeDesc;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.ServiceAtt;
import com.ailk.eaap.op2.bo.ServiceProductRela;
import com.ailk.eaap.op2.bo.TechImpAtt;
import com.ailk.eaap.op2.bo.TechImpl;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.bo.ServiceOwner;
import com.ailk.o2p.portal.bo.UserDefined;

public interface IProviderDao{

	public List<Map<String, Object>> findApply(Map<String, String> map);

	public List<Map<String, Object>> callSystem();

	public List<Map<String, Object>> selectFileShare(Map paramMap);

	public Map<String, String> queryProvSystem(Map paramMap);

	public Integer saveFileShare(FileShare file);

	public boolean isExitCode(Map paramMap);

	public String saveComponent(Component component);

	public String updateComponent(Component component);

	public List<Map<String, String>> provAbility(Map map);

	public List<Map<String, Object>> userDefinedList(Map paramMap);

	public Integer addTechImpAtt(Map<String, String> map,Map paramMap);

	public List<Map<String, Object>> selectCommProtocal();

	public List<Map<String, Object>> selectDirectory(Map paramMap);

	public List<Map<String, Object>> selectConType(Map paramMap);

	public boolean checkCode(Map paramMap);

	public boolean checkVersion(Map paramMap);

	public Contract addContract(Contract contract);

	public ContractVersion addContractVersion(ContractVersion contractVersion);

	public ServiceOwner addService(ServiceOwner service);

	public Integer addApi(Api api);

	public Integer addDirServiceList(DirSerList dirSerList);

	public Integer addServiceAtt(ServiceAtt serviceAtt);

	public Integer addUserDefineTechImpAtt(Map map, Map<String, String> mapParam,UserDefined userDefined);

	public void editContract(Contract contract);

	public void editDirSerList(DirSerList dirSerList);

	public Integer editService(ServiceOwner service);

	public void editTechImpl(TechImpl techImpl);

	public Integer editAbility(TechImpAtt tech,Map paramMap);

	public Integer addContractFormat(ContractFormat cf);

	public Integer editNodeDesc(NodeDesc nodeDesc);

	public String getNodeIdByPath(Map paramMap);

	public Integer addNodeDesc(NodeDesc nodeDesc);

	public List<Map<String, Object>> queryNodeDesc(Map map);

	public Integer editContractFormat(ContractFormat cf);

	public List<Map<String, Object>> getNodeDesc(Map paramMap);

	public void deleteNodeDesc(String string,Map paramMap);

	public List<Map<String, Object>> queryUserFine(Map paramMap);

	public List<Map<String, Object>> queryContractFormat(Map paramMap);

	public void operatorSerTechImpl(Map paramMap);
	
	public WorkTaskConf queryWorkTaskConf(Map paramMap);
	
	public ServiceProductRela queryServiceProductRela(Map paramMap);

	public String getComponentSeq();

	public int countProvAbility(Map map);

	public List<Map<String, Object>> showAbility(Map map);

	public int countShowAbility(Map map);
 
	
	public List<Map<String, Object>> showAbilityByName(Map map);
	/**
	 * insertProduct:插入产品表. <br/>  
	 */
	public BigDecimal insertProduct(Product pro);
	/**
	 * insertProdOffer:插入销售品表. <br/>  
	 */
	public BigDecimal insertProdOffer(ProdOffer prodOffer);
	/**
	 * insertOfferProdRel:插入销售品产品关系表. <br/>  
	 */
	public Integer insertOfferProdRel(OfferProdRel offerProdRel);
	/**
	 * showPackage:展示包. <br/>  
	 */
	public List<Map<String, Object>> showPackage(String componentId,Map paramMap);
	/**
	 * 根据服务对象ID查询需要绑定的能力
	 */
	public List<Map<String, String>> queryService(String service,Map paramMap) throws EAAPException;
	
	public BigDecimal getProductbyCap(Map map);
	/**
	 * insertServiceProRel:插入服务产品关系表. <br/> 
	 */
	public Integer insertServiceProRel(Map map);
 

	public String editProvSystem(Component component);

	public List<Map<String, Object>> showAbility(String componentId);
	public Integer updateProdOffer(ProdOffer prodOffer) ;

	public List<MainData> selectMainData(MainData mainData);

	public List<MainDataType> selectMainDataType(MainDataType mainDataType);

	public Integer addComponentPrice(ComponentPrice componentPrice);

	public Integer addBillingDiscount(BillingDiscount billingDiscount);

	public Integer addRatingCurveDetail(RatingCurverDetail ratingCurverDetail);

	public Integer updateComponentPrice(ComponentPrice componentPrice);

	public Integer updateBillingDiscount(BillingDiscount billingDiscount);

	public List<RatingCurverDetail> queryRatingCurveDetail(
			RatingCurverDetail ratingCurverDetail);

	public void delRatingCurveDetail(RatingCurverDetail r);

	public List<ComponentPrice> queryComponentPrice(Map<String, Object> map);

	public BillingDiscount queryBillingDiscount(BillingDiscount billingDiscount);

	public Component queryProv(Component component);

	public void updateProdOfferById(String componentId);
	
	public List<Map<String, Object>> getProcessList(Map<String, Object> map);

	public List<Map<String, Object>> queryTechImplAttrSpec(
			Map<String, String> hashMap);

	public void editTechImplAtt(TechImpAtt tech);

	public boolean getIsExit(TechImpAtt tech);

	public void addTechImplAtt(TechImpAtt tech);

	public void updateSerTechImpl(String techImpId);
	
	public List<Map<String, Object>> queryComponentState(Map map);
	
	public void deleteDirSerList(Map paramMap);
	
	public List<Map<String, Object>> queryDirSerList(Map paramMap);
	
}