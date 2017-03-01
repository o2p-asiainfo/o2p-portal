package com.ailk.o2p.portal.service.pard;

import java.util.List;
import java.util.Map;

import javax.management.Query;

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

public interface IPardProdServ {
	public List<Map> showProdOfferList (Map map);
	public ProdOffer queryProdOffer(ProdOffer prodOffer) ;
	public List<ProdOfferChannelType> queryProdOfferChannelList(ProdOfferChannelType prodChannel);
    public ValueAddedProdInfo queryValueAddedProdInfo(ValueAddedProdInfo valueProd);
    public List<Map> queryProdDealSvcList(Map map);
	public String queryProductSeq();
	
	public String queryProdOfferSeq();
	public String queryOfferProdRelSeq();
	public String queryProdChannelTypeSeq();
	public String queryPricingPlanSeq();
    public String queryPricingPlan2ProcModeSeq();
	public String queryProcModeInstSeq();
	
	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel);
	
	public Component queryComponent(Component component);
    public List<PricingClassify> queryPricingClass();
    public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) ;
    public int queryProcModeNum(Map map);
    public List<ProcMode> queryProcModeList(Map map,int startPage,int pageRecord);
	public Map<String,Object> queryProcModeInfo(Map map,int startPage,int pageRecord,boolean flag);
 	public List<MainData> selectMainData(MainData mainData) ;
	public List<MainDataType> selectMainDataType(MainDataType mainDataType) ;
	public void deletePardProdInfo(ProdOffer prodOffer,Product product);
	
	public List<Map> queryAbilityAttr(Map map);
	public List<Map>  queryAttrValue(Map map);
	public List<Map> queryProcModeDesc(Map map);
	public void addPricePlanAndAbilityInfo(ProcModeInst procModeInst,PricingPlan2ProcModeInst pricePlan2Pmi,PricingPlan pricingPlan,String procModeId,String [] procModeAttrIds,String [] abilityAttrVals);
	public void addPardProductInfo(ProdOffer prodOffer,Product product,OfferProdRel offerProdRel,
			ValueAddedProdInfo valueAddedProd,List<ProdOfferChannelType> channelTypeList);
	public void updatePardProdInfo(ProdOffer prodOffer,ValueAddedProdInfo valueAddedProd, List<ProdOfferChannelType> channelTypeList);
	public String queryProdOfferPricingSeq();
	public List<PricingClassify> queryPricingClassifyByProdOfferId(Map mapPricing);
	public void insertProdAttr(ProductAttr productAttr);

	public List<ProductAttr> queryProductAtrr(ProductAttr productAttr);
	public String getAttrSpecSeq();
	public void insertAttrSpec(AttrSpec attrSpec);
	
    public void updateProductAttrSpec(ProductAttr productAttr,AttrSpec attrSpec);
    public PricingPlan queryPricingPlan(PricingPlan pricingPlan);
    public List<Map> queryPricingClassify(ProdOfferPricing prodOfferPricing);
    public void insertPrincingPlanId(ProdOfferPricing prodOfferPricing);
    public void updatePrincingPlan(PricingPlan pricingPlan,PricingPlanClassifyRel pricingPlanClassifyRel);
    public void deleteProdOfferPrincing(ProdOfferPricing prodOfferPricing);
	public void updateProdOffer(ProdOffer prodOffer);
    public void deletePricingPlan2ProcModeInst(ProcModeInst procModeInst,PricingPlan2ProcModeInst pricingPlan2ProcModeInst,ProcModeInstAttr procModeInstAttr);
	
    public Map queryPardProdInfo(Map map);
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
     * 查询产品选取属性ID
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
    public String queryOfferProdRelPricingSeq();
    public void insertOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing);
	public Map<String,Object> queryProduct(Map<String, Object> hashMap);
	public List<Map> queryPricingPlan(OfferProdRel offerProdRel);
	public void deleteOfferProdRelPricing(ProdOfferPricing prodOfferPricing,PricingPlanClassifyRel pricingPlanClassifyRel);
	
	
	public List<Map>  selectRelPricingByProdofferId(ProdOfferPricing prodOfferPricing);
}