package com.ailk.o2p.portal.service.provider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.NodeDesc;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.ServiceProductRela;
import com.ailk.eaap.op2.bo.TechImpAtt;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.bo.UserDefined;

public interface IProviderService {

	public List<Map<String, Object>> findApply(Map<String, String> map);

	public List<Map<String, Object>> selectFileShare(String requestValue);

	public Map<String, String> queryProvSystem(String componentId);

	public Integer saveFileShare(FileShare file);

	public boolean isExitCode(String code);

	public String saveOrUpdate(String pageActionType, Component component);

	public List<Map<String, String>> provAbility(Map map);

	public List<Map<String, Object>> userDefinedList(String componentId);
	
	public List<Map<String, Object>> showAbilityByName(Map map);

	public void addTechImpAtt(Map<String, String> map);

	public List<Map<String, Object>> selectCommProtocal();

	public List<Map<String, Object>> selectDirectory(String fadiridapi);

	public List<Map<String, Object>> selectConType(String maindatacontype);

	public boolean checkCode(String pageProCode);

	public boolean checkVersion(String pageProVersionCode);

	public Map<String, String> addUserDefined(UserDefined userDefined);

	public void updateUserDefined(UserDefined userDefined,JSONObject json);

	public Integer addContractFormat(UserDefined userDefined);

	public List<Map<String, Object>> queryNodeDesc(String string);

	public Integer updateContractFormat(UserDefined userDefined);

	public void deleteNodeDesc(String pageNodeDescId);

	public List<Map<String, Object>> queryUserFine(String editvalue);

	public List<Map<String, Object>> queryContractFormat(String techImpAttId);

	public void editAbility(TechImpAtt techImpAtt);

	public void operatorSerTechImpl(String techimpAttId, String state,String componentId);
	
	public WorkTaskConf queryWorkTaskConf(String componentId,String  serviceId);
	
	public ServiceProductRela queryServiceProductRela(String componentId,String  serviceId);
	
	public String getComponentSeq();

	public int countProvAbility(Map map);

	public List<Map<String, Object>> showAbility(Map map);

	public int countShowAbility(Map map);
 
	public String editProvSystem(Component component2);

	public void updateNodeDesc(UserDefined uf);
 
	
	/**
	 * insertProdOffer:插入销售品表. <br/>  `````````````
	 */
	public BigDecimal insertProdOffer(ProdOffer prodOffer);
	/**
	 * 根据服务对象ID查询需要绑定的能力````````````````````
	 */
	public List<Map<String, String>> queryService(String service);
	/**
	 * ````````````````````````
	 */
	public BigDecimal getProductbyCap(Map map);
	/**
	 * insertProduct:插入产品表. <br/>  ````````````````````
	 */
	public BigDecimal insertProduct(Product pro);
	/**
	 * insertServiceProRel:插入服务产品关系表. <br/>  `````````````````
	 */
	public Integer insertServiceProRel(Map map);
	/**
	 * insertOfferProdRel:插入销售品产品关系表. <br/>  `````````````
	 */
	public Integer insertOfferProdRel(OfferProdRel offerProdRel);
	 
	/**
	 * showPackage:展示包. <br/> 
	 */
	public List<Map<String, Object>> showPackage(String componentId,Map paramMap);
	/**
	 * 删除销售品
	 */
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

	public void updateContractFormat2(UserDefined userDefined);

	public void addContractFormat2(UserDefined userDefined);

	public Component queryProv(Component component1);

	public void updateComponentById(Component component);

	public void updateProdOfferById(String componentId);
	
	public List<Map<String, Object>> getProcessList(Map<String, Object> map);

	public List<Map<String, Object>> queryTechImplAttrSpec(
			Map<String, String> hashMap);
	
	public List<Map<String, Object>> queryComponentState(Map map);
	
	public Integer addNodeDesc(NodeDesc nodeDesc);
	
	public List<Map<String, Object>> queryDirSerList(String editvalue);
	
	
}
