package com.ailk.o2p.portal.service.pard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.AttrSpec;
import com.ailk.eaap.op2.bo.AttrValue;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.PricingClassify;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingPlan2ProcModeInst;
import com.ailk.eaap.op2.bo.PricingPlanClassifyRel;
import com.ailk.eaap.op2.bo.ProcMode;
import com.ailk.eaap.op2.bo.ProcModeInst;
import com.ailk.eaap.op2.bo.ProcModeInstAttr;
import com.ailk.eaap.op2.bo.ProdAttrValue;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannelType;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.bo.ProductAttrGroup;
import com.ailk.eaap.op2.bo.ProductAttrGroupRela;
import com.ailk.eaap.op2.bo.ValueAddedProdInfo;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.pard.PardProdDao;

public class PardProdServ implements IPardProdServ {
	private PardProdDao pardProdDao;
	private MainDataDao mainDataSqlDAO;
	private MainDataTypeDao mainDataTypeSqlDAO;

	@SuppressWarnings("unchecked")
	public List<Map> showProdOfferList(Map map) {
		if ("ALLNUM".equals(map.get("queryType"))) {
			return pardProdDao.queryProdOfferCount(map);
		} else {
			return pardProdDao.showProdOfferList(map);
		}
	}

	public List<ProdOfferChannelType> queryProdOfferChannelList(
			ProdOfferChannelType prodChannel) {
		return pardProdDao.queryProdOfferChannelList(prodChannel);
	}

	public ProdOffer queryProdOffer(ProdOffer prodOffer) {
		return pardProdDao.queryProdOffer(prodOffer);
	}

	public ValueAddedProdInfo queryValueAddedProdInfo(
			ValueAddedProdInfo valueProd) {
		return pardProdDao.queryValueAddedProdInfo(valueProd);
	}

	public List<Map> queryProdDealSvcList(Map map) {
		return pardProdDao.queryProdDealSvcList(map);
	}

	public String queryProductSeq() {
		return pardProdDao.queryProductSeq();
	}

	public List<PricingClassify> queryPricingClass() {
		return pardProdDao.queryPricingClass();
	}

	public String queryProdOfferSeq() {
		return pardProdDao.queryProdOfferSeq();
	}

	public List<Map> queryProcModeDesc(Map map) {
		return pardProdDao.queryProcModeDesc(map);
	}

	public Map<String, Object> queryProcModeInfo(Map map, int startPage,
			int pageRecord, boolean flag) {
		int total = this.queryProcModeNum(map);
		List<ProcMode> procModeList = this.queryProcModeList(map, startPage,
				pageRecord);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", total);
		resultMap.put("dataList", procModeList);
		return resultMap;
	}

	public List<ProcMode> queryProcModeList(Map map, int startPage,
			int pageRecord) {
		int skipRow = 0;
		int maxResult = 0;
		if (startPage <= 1) {
			skipRow = 0;
			maxResult = pageRecord;
		} else {
			skipRow = (startPage - 1) * pageRecord;
			maxResult = startPage * pageRecord;
		}
		map.put("skipRow", skipRow);
		map.put("maxResult", maxResult);
		return (List<ProcMode>) pardProdDao.queryProcModeList(map);
	}

	public int queryProcModeNum(Map map) {
		return pardProdDao.queryProcModeNum(map);
	}

	public String queryOfferProdRelSeq() {
		return pardProdDao.queryOfferProdRelSeq();
	}

	public String queryProdChannelTypeSeq() {
		return pardProdDao.queryProdChannelTypeSeq();
	}

	public Component queryComponent(Component component) {
		return pardProdDao.queryComponent(component);
	}

	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) {
		return pardProdDao.queryOfferProdRel(offerProdRel);
	}

	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel) {
		return pardProdDao.queryOfferProdRelList(offerProdRel);
	}

	public List<Map> queryAbilityAttr(Map map) {
		return pardProdDao.queryAbilityAttr(map);
	}

	public List<Map> queryAttrValue(Map map) {
		List<Map> list = new ArrayList<Map>();
		return pardProdDao.queryAttrValue(map) == null ? list : pardProdDao
				.queryAttrValue(map);
	}

	public String queryPricingPlan2ProcModeSeq() {
		return pardProdDao.queryPricingPlan2ProcModeSeq();
	}

	public String queryPricingPlanSeq() {
		return pardProdDao.queryPricingPlanSeq();
	}

	public String queryProcModeInstSeq() {
		return pardProdDao.queryProcModeInstSeq();
	}

	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData);
	}

	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType);
	}

	public void addPardProductInfo(ProdOffer prodOffer, Product product,
			OfferProdRel offerProdRel, ValueAddedProdInfo valueAddedProd,
			List<ProdOfferChannelType> channelTypeList) {
		pardProdDao.insertProdOffer(prodOffer);
		pardProdDao.insertProduct(product);
		pardProdDao.insertOfferProdRel(offerProdRel);
		pardProdDao.insertValueAddedProd(valueAddedProd);
		this.addChannelTypeList(channelTypeList);
	}

	public void addPricePlanAndAbilityInfo(ProcModeInst procModeInst,
			PricingPlan2ProcModeInst pricePlan2Pmi, PricingPlan pricingPlan,
			String procModeId, String[] procModeAttrIds,
			String[] abilityAttrVals) {

		if (pricingPlan.getStatusCd() == null) {
			pardProdDao.insertProcModeInst(procModeInst);
			pardProdDao.insertPricePlan2ProcModeInst(pricePlan2Pmi);
		} else {
			pardProdDao.insertProcModeInst(procModeInst);
			pardProdDao.insertPricingPlan(pricingPlan);
			pardProdDao.insertPricePlan2ProcModeInst(pricePlan2Pmi);
		}

		int i = 0;
		for (String procModeAttrId : procModeAttrIds) {
			ProcModeInstAttr procModeInstAttr = new ProcModeInstAttr();
			procModeInstAttr.setProcModeAttrId(Integer.valueOf(procModeAttrId));
			procModeInstAttr
					.setProcModeInstId(procModeInst.getProcModeInstId());
			if (abilityAttrVals[i].startsWith("isSelectType")) {
				procModeInstAttr.setProcModeValueId(Integer
						.valueOf(abilityAttrVals[i].split("/~~/")[3]));
			} else {
				procModeInstAttr.setProcModeInstAttrValue(abilityAttrVals[i]);
			}
			procModeInstAttr.setStatusCd("10");
			i++;
			pardProdDao.insertProcModeInstAttr(procModeInstAttr);

		}

	}

	/**
	 * 更新合作伙伴信息保存
	 */
	public void updatePardProdInfo(ProdOffer prodOffer,
			ValueAddedProdInfo valueAddedProd,
			List<ProdOfferChannelType> channelTypeList) {
		pardProdDao.updateProdOffer(prodOffer);
		if (valueAddedProd.getServiceId() != null) {
			pardProdDao.updateValueAddedProdInfo(valueAddedProd);
		}
		pardProdDao.deleteProdChannelType(prodOffer);
		this.addChannelTypeList(channelTypeList);
	}

	public void deletePardProdInfo(ProdOffer prodOffer, Product product) {
		pardProdDao.updateProdOffer(prodOffer);
		pardProdDao.updateProduct(product);
	}

	public void addChannelTypeList(List<ProdOfferChannelType> channelTypeList) {
		if (channelTypeList != null && channelTypeList.size() > 0) {
			java.util.Iterator it = channelTypeList.iterator();
			while (it.hasNext()) {
				ProdOfferChannelType prodChannelType = (ProdOfferChannelType) it
						.next();
				pardProdDao.insertProdChannelType(prodChannelType);
			}
		}
	}

	public PardProdDao getPardProdDao() {
		return pardProdDao;
	}

	public void setPardProdDao(PardProdDao pardProdDao) {
		this.pardProdDao = pardProdDao;
	}

	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}

	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}

	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}

	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}

	public String queryProdOfferPricingSeq() {
		return pardProdDao.queryProdOfferPricingSeq();
	}

	public List<PricingClassify> queryPricingClassifyByProdOfferId(
			Map mapPricing) {
		return pardProdDao.queryPricingClassifyByProdOfferId(mapPricing);
	}

	/**
	 * 插入产品属性
	 */
	public void insertProdAttr(ProductAttr productAttr) {
		pardProdDao.insertProductAttr(productAttr);
	}

	/**
	 * 查询产品属性
	 */
	public List<ProductAttr> queryProductAtrr(ProductAttr productAttr) {
		return pardProdDao.queryProductAttr(productAttr);
	}

	/**
	 * 获得产品属性规格序列
	 */
	public String getAttrSpecSeq() {
		return pardProdDao.queryAttrSpecSeq();
	}

	/**
	 * 插入产品属性规格
	 */
	public void insertAttrSpec(AttrSpec attrSpec) {
		pardProdDao.insertAttrSpec(attrSpec);
	}

	/**
	 * 更新产品属性
	 * 
	 * @param productAttr
	 * @param attrSpec
	 */
	public void updateProductAttrSpec(ProductAttr productAttr, AttrSpec attrSpec) {
		pardProdDao.deleteProductAttr(productAttr);
		pardProdDao.deleteAttrSpec(attrSpec);
	}

	/**
	 * 查询定价计划表
	 */
	public PricingPlan queryPricingPlan(PricingPlan pricingPlan) {
		return pardProdDao.queryPricingPlan(pricingPlan);
	}

	/**
	 * 查询费用类别
	 * 
	 * @param prodOfferPricing
	 * @return
	 */
	public List<Map> queryPricingClassify(ProdOfferPricing prodOfferPricing) {
		return pardProdDao.queryPricingClassify(prodOfferPricing);
	}

	/**
	 * 插入销售品定价计划表
	 */
	public void insertPrincingPlanId(ProdOfferPricing prodOfferPricing) {
		pardProdDao.insertProdOfferPricing(prodOfferPricing);
	}

	/**
	 * 更新定价计划表
	 * 
	 * @param pricingPlan
	 */
	public void updatePrincingPlan(PricingPlan pricingPlan,
			PricingPlanClassifyRel pricingPlanClassifyRel) {
		pardProdDao.updatePrincingPlan(pricingPlan);
		String pricingPlanClassifyRelId = pardProdDao
				.queryPricingPlanClassifyRelSeq();
		pricingPlanClassifyRel
				.setPricingPlanClassifyRelId(pricingPlanClassifyRelId);
		pricingPlanClassifyRel.setPricingInfoId(pricingPlan.getPricingInfoId()
				.toString());

		pardProdDao.insertPricingPlanClassifyRel(pricingPlanClassifyRel);//
	}

	/**
	 * 删除定价计划表
	 */
	public void deleteProdOfferPrincing(ProdOfferPricing prodOfferPricing) {
		pardProdDao.deleteProdOfferPricing(prodOfferPricing);
	}

	public void updateProdOffer(ProdOffer prodOffer) {
		pardProdDao.updateProdOffer(prodOffer);
	}

	/**
	 * 更新属性
	 * 
	 * @param productAttr
	 */
	public void updateProductAttr(ProductAttr productAttr) {
		pardProdDao.updateProductAttr(productAttr);
	}

	/**
	 * 删除配置能力
	 */
	public void deletePricingPlan2ProcModeInst(ProcModeInst procModeInst,
			PricingPlan2ProcModeInst pricingPlan2ProcModeInst,
			ProcModeInstAttr procModeInstAttr) {
		pardProdDao.deletePricingPlan2ProcModeInst(pricingPlan2ProcModeInst);
		pardProdDao.deleteProcModeInstAttr(procModeInstAttr);
		pardProdDao.deleteProcModeInst(procModeInst);
	}

	@SuppressWarnings("unchecked")
	public Map queryPardProdInfo(Map map) {
		Map retMap = new HashMap();
		map.put("COOPERATION_TYPE", "11");
		List pardProdList = pardProdDao.queryPardProdInfo(map);
		retMap.put("pardProdList", pardProdList);

		map.put("COOPERATION_TYPE", "12");
		List pardMixList = pardProdDao.queryPardProdInfo(map);
		retMap.put("pardMixList", pardMixList);

		map.put("COOPERATION_TYPE", "10");
		List mealRateList = pardProdDao.queryPardProdInfo(map);
		retMap.put("mealRateList", mealRateList);

		List prodOfferList = pardProdDao.queryPardSellInfo(map);
		retMap.put("prodOfferList", prodOfferList);
		return retMap;
	}

	/**
	 * 查询产品属性分组ID
	 * 
	 * @return
	 */
	public String queryProductAttrGroupSeq() {
		return pardProdDao.queryProductAttrGroupSeq();
	}

	/**
	 * 查询产品属性归属分组ID
	 * 
	 * @return
	 */
	public String queryProductAttrGroupRelaSeq() {
		return pardProdDao.queryProductAttrGroupRelaSeq();
	}

	/**
	 * 查询产品属性表ID
	 */
	public String queryProductAttrSeq() {
		return pardProdDao.queryProductAttrSeq();
	}

	public String queryProdAttrValueSeq() {
		return pardProdDao.queryProdAttrValueSeq();
	}

	/**
	 * 查询属性值规格2表ID
	 * 
	 * @return
	 */
	public String queryAttrValueSeq() {
		return pardProdDao.queryAttrValueSeq();
	}

	/**
	 * 产品属性分组表增删查
	 */
	public void insertProductAttrGroup(ProductAttrGroup productAttrGroup) {
		pardProdDao.insertProductAttrGroup(productAttrGroup);
	}

	public List<ProductAttrGroup> queryProductAttrGroup(
			ProductAttrGroup productAttrGroup) {
		return pardProdDao.queryProductAttrGroup(productAttrGroup);
	}

	public void deleteProductAttrGroup(ProductAttrGroup productAttrGroup) {
		pardProdDao.deleteProductAttrGroup(productAttrGroup);
	}

	/**
	 * 产品属性归属分组表增删查
	 */
	public void insertProductAttrGroupRela(
			ProductAttrGroupRela productAttrGroupRela) {
		pardProdDao.insertProductAttrGroupRela(productAttrGroupRela);
	}

	public List<ProductAttrGroupRela> queryProductAttrGroupRela(
			ProductAttrGroupRela productAttrGroupRela) {
		return pardProdDao.queryProductAttrGroupRela(productAttrGroupRela);
	}

	public void deleteProductAttrGroupRela(
			ProductAttrGroupRela productAttrGroupRela) {
		pardProdDao.deleteProductAttrGroupRela(productAttrGroupRela);
	}

	/**
	 * 产品选取属性表增删查
	 * 
	 * @param productAttr
	 */
	public void insertProductAttr(ProductAttr productAttr) {
		pardProdDao.insertProductAttr(productAttr);
	}

	public List<ProductAttr> queryProductAttr(ProductAttr productAttr) {
		return pardProdDao.queryProductAttr(productAttr);
	}

	public void deleteProductAttr(ProductAttr productAttr) {
		pardProdDao.deleteProductAttr(productAttr);
	}

	/**
	 * 产品属性选用值表增删查
	 * 
	 * @param prodAttrValue
	 */
	public void insertProdAttrVaule(ProdAttrValue prodAttrValue) {
		pardProdDao.insertProdAttrVaule(prodAttrValue);
	}

	public List<ProdAttrValue> queryProdAttrVaule(ProdAttrValue prodAttrValue) {
		return pardProdDao.queryProdAttrVaule(prodAttrValue);
	}

	public void deleteProdAttrVaule(ProdAttrValue prodAttrValue) {
		pardProdDao.deleteProdAttrVaule(prodAttrValue);
	}

	/**
	 * 属性值规格2表增删查
	 * 
	 * @param attrValue
	 */
	public void insertAttrValue(AttrValue attrValue) {
		pardProdDao.insertAttrValue(attrValue);
	}

	public List<AttrValue> queryAttrValue(AttrValue attrValue) {
		return pardProdDao.queryAttrValue(attrValue);
	}

	public void deleteAttrValue(AttrValue attrValue) {
		pardProdDao.deleteAttrValue(attrValue);
	}

	/**
	 * 查询属性规格1表
	 */
	public AttrSpec queryAttrSpec(AttrSpec attrSpec) {
		return pardProdDao.queryAttrSpec(attrSpec);
	}

	public ProductAttr queryProductAttrGroupRelaAndProductAttr(Map hashMap) {
		return pardProdDao.queryProductAttrGroupRelaAndProductAttr(hashMap);
	}

	public List<AttrValue> queryProductAttrValueAndAttrValue(
			ProductAttr productAttr) {
		return pardProdDao.queryProductAttrValueAndAttrValue(productAttr);
	}

	public List<ProdOffer> compareProdOffer(ProdOffer prodOffer) {
		return pardProdDao.compareProdOffer(prodOffer);
	}

	public String queryOfferProdRelPricingSeq() {
		return (String) pardProdDao.queryOfferProdRelPricingSeq();
	}

	public void insertOfferProdRelPricing(
			OfferProdRelPricing offerProdRelPricing) {
		pardProdDao.insertOfferProdRelPricing(offerProdRelPricing);
	}

	public Map<String, Object> queryProduct(Map<String, Object> hashMap) {
		return pardProdDao.queryProduct(hashMap);
	}

	public List<Map> queryPricingPlan(OfferProdRel offerProdRel) {
		return pardProdDao.queryPricingPlan(offerProdRel);
	}

	public void deleteOfferProdRelPricing(ProdOfferPricing prodOfferPricing,
			PricingPlanClassifyRel pricingPlanClassifyRel) {
		pardProdDao.deleteOfferProdRelPricing(prodOfferPricing);
		pardProdDao.deletePricingPlanClassifyRel(pricingPlanClassifyRel);
	}

	public List<Map> selectRelPricingByProdofferId(
			ProdOfferPricing prodOfferPricing) {
		return pardProdDao.selectRelPricingByProdofferId(prodOfferPricing);
	}
}