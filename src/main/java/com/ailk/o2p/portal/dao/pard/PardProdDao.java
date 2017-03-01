package com.ailk.o2p.portal.dao.pard;


import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.AttrSpec;
import com.ailk.eaap.op2.bo.AttrValue;
import com.ailk.eaap.op2.bo.Component;
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

public interface PardProdDao {

	public List<Map> showProdOfferList(Map map);
	public List<Map> queryProdOfferCount(Map map);
	public ProdOffer queryProdOffer(ProdOffer prodOffer);
	public List<ProdOfferChannelType> queryProdOfferChannelList(
			ProdOfferChannelType prodChannel);
	public ValueAddedProdInfo queryValueAddedProdInfo(
			ValueAddedProdInfo valueProd);
	public List<Map> queryProdDealSvcList(Map map);
	
	
	public String queryProductSeq();

	public String queryProdOfferSeq();
	public String queryOfferProdRelSeq();
	public String queryProdChannelTypeSeq();
	public String queryPricingPlanSeq();
	public String queryPricingPlan2ProcModeSeq();
	public String queryProcModeInstSeq();
	public String queryProdOfferPricingSeq();
	public String queryAttrSpecSeq();
	
	public Component queryComponent(Component component);
	public List<PricingClassify> queryPricingClass();
	public int queryProcModeNum(Map map);
	public List<ProcMode> queryProcModeList(Map map);
	public void insertProdOffer(ProdOffer prodOffer);
	public void insertProduct(Product product);
	public void insertOfferProdRel(OfferProdRel offerProdRel);
	public void insertValueAddedProd(ValueAddedProdInfo valueAddedProd);
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel);
	public void updateProdOffer(ProdOffer prodOffer);
	public void updateProduct(Product product);
	public void updateValueAddedProdInfo(ValueAddedProdInfo valueAddedProd);
	public List<Map> queryAbilityAttr(Map map);
	public List<Map> queryAttrValue(Map map);
	public void insertProdOfferPricing(ProdOfferPricing prodOfferPricing);
	public List<PricingClassify> queryPricingClassifyByProdOfferId(
			Map mapPricing);
	public void insertProdChannelType(ProdOfferChannelType prodChannelType);
	public  Integer insertProcModeInst(ProcModeInst procModeInst);
	public Integer insertProcModeInstAttr(ProcModeInstAttr procModeInstAttr);
	public void insertPricePlan2ProcModeInst(PricingPlan2ProcModeInst bean);
	public void insertPricingPlan(PricingPlan pricingPlan);
	public List<Map> queryProcModeDesc(Map map);
	public List queryPardProdInfo(Map map);
	public List queryPardSellInfo(Map map);
	public void deleteProdChannelType(ProdOffer prodOffer);
	public String queryOfferProdRelPricingSeq();


	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel);



	/**
	 * 获得属性值规则序列
	 * 
	 * @return
	 */
	public void insertAttrSpec(AttrSpec attrSpec);





	/**
	 * 删除产品属性规格
	 * 
	 * @param attrSpec
	 */
	public void deleteAttrSpec(AttrSpec attrSpec);

	/**
	 * 查询定价计划表
	 */
	public PricingPlan queryPricingPlan(PricingPlan pricingPlan);

	/**
	 * 查询费用类别
	 * 
	 * @param prodOfferPricing
	 * @return
	 */
	public List<Map> queryPricingClassify(ProdOfferPricing prodOfferPricing);

	/**
	 * 更新定价计划表
	 * 
	 * @param pricingPlan
	 */
	public void updatePrincingPlan(PricingPlan pricingPlan);

	/**
	 * 删除定价计划表
	 */
	public void deletePrincingPlan(PricingPlan pricingPlan);
	/**
	 * 删除销售品定价计划表
	 * @param prodOfferPricing
	 */
	public void deleteProdOfferPricing(ProdOfferPricing prodOfferPricing);
	/**
     *删除配置能力实例
     */
	public void deleteProcModeInst(ProcModeInst procModeInst);
	/**
	 * 删除配置能力关联
	 * @param pricingPlan2ProcModeInst
	 */
    public void deletePricingPlan2ProcModeInst(PricingPlan2ProcModeInst pricingPlan2ProcModeInst);
	 /**
     * 更新属性
     * @param productAttr
     */
    public void updateProductAttr(ProductAttr productAttr);
    
    
    /**
     * 查询产品选取属性ID
     * @return
     */
    public String queryProductAttrSeq();
    /**
     * 查询产品属性分组ID
     * @return
     */
    public String queryProductAttrGroupSeq();
    /**
     * 查询产品属性归属分组ID
     * @return
     */
    public String queryProductAttrGroupRelaSeq();
    /**
     * 查询产品属性选用值ID
     * @return
     */
    public String queryProdAttrValueSeq();
    /**
     * 查询属性值规格2表ID
     * @return
     */
    public String queryAttrValueSeq();

    /**
     * 产品属性分组表增删查
     */
    public void insertProductAttrGroup(ProductAttrGroup productAttrGroup);
    public List<ProductAttrGroup> queryProductAttrGroup(ProductAttrGroup productAttrGroup);
    public void deleteProductAttrGroup(ProductAttrGroup productAttrGroup);
    /**
     * 产品属性归属分组表增删查
     */
    public void insertProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela);
    public List<ProductAttrGroupRela> queryProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela);
    public void deleteProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela);
    
    /**
     * 产品选取属性表增删查
     * @param productAttr
     */
    public void insertProductAttr(ProductAttr productAttr);
    public List<ProductAttr> queryProductAttr(ProductAttr productAttr);
    public void deleteProductAttr(ProductAttr productAttr);
    
    /**
     * 产品属性选用值表增删查
     * @param prodAttrValue
     */
    public void insertProdAttrVaule(ProdAttrValue prodAttrValue);
    public List<ProdAttrValue> queryProdAttrVaule(ProdAttrValue prodAttrValue);
    public void deleteProdAttrVaule(ProdAttrValue prodAttrValue);
    
    /**
     * 属性值规格2表增删查
     * @param attrValue
     */
    public void insertAttrValue(AttrValue attrValue);
    public List<AttrValue> queryAttrValue(AttrValue attrValue);
    public void deleteAttrValue(AttrValue attrValue);
   
    /**
     * 查询属性规格1表
     * @param attrSpec
     * @return
     */
    public AttrSpec queryAttrSpec(AttrSpec attrSpec);
    public ProductAttr queryProductAttrGroupRelaAndProductAttr(Map hashMap);
    public List<AttrValue> queryProductAttrValueAndAttrValue(ProductAttr productAttr);
    public List<ProdOffer> compareProdOffer(ProdOffer prodOffer);
    public String queryPricingPlanClassifyRelSeq();
    public void insertPricingPlanClassifyRel(PricingPlanClassifyRel pricingPlanClassifyRel);
    public void deleteProcModeInstAttr(ProcModeInstAttr procModeInstAttr);
	public void insertOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing);
	public Map<String,Object> queryProduct(Map<String, Object> hashMap);
	public List<Map> queryPricingPlan(OfferProdRel offerProdRel);
	public void deleteOfferProdRelPricing(ProdOfferPricing prodOfferPricing);
	public String queryPricingPlanClassfiyRelSeq();
	public void deletePricingPlanClassifyRel(PricingPlanClassifyRel pricingPlanClassifyRel);
	
	public List<Map> selectRelPricingByProdofferId(ProdOfferPricing prodOfferPricing);
}
