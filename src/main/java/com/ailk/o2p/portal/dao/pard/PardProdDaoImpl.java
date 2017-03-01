package com.ailk.o2p.portal.dao.pard;

import java.util.ArrayList;
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
import com.ailk.eaap.op2.bo.i18n.ProvideI18NData;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NDatas;
import com.linkage.rainbow.dao.SqlMapDAO;

public class PardProdDaoImpl implements PardProdDao {
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map> showProdOfferList(Map map) {
		List<Map> returnList = sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.showProdOfferList", map);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List<Map> queryProdOfferCount(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.queryProdOfferCount", map);
	}

	public ProdOffer queryProdOffer(ProdOffer prodOffer) {
		return (ProdOffer) sqlMapDao.queryForObject(
				"prodOffer.selectProdOffer", prodOffer);
	}

	@SuppressWarnings("unchecked")
	public List<ProdOfferChannelType> queryProdOfferChannelList(
			ProdOfferChannelType prodChannel) {
		return (List<ProdOfferChannelType>) sqlMapDao.queryForList(
				"prodOfferChannelType.selectProdOfferChannelType", prodChannel);
	}

	public ValueAddedProdInfo queryValueAddedProdInfo(
			ValueAddedProdInfo valueProd) {
		return (ValueAddedProdInfo) sqlMapDao.queryForObject(
				"valueAddedProdInfo.selectValueAddedProdInfo", valueProd);
	}

	@SuppressWarnings("unchecked")
	public List<Map> queryProdDealSvcList(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.showProdDealSvcList", map);
	}

	public String queryProductSeq() {
		return (String) sqlMapDao.queryForObject("product.selectProductSeq",
				null);
	}

	public String queryProdOfferSeq() {
		return (String) sqlMapDao.queryForObject(
				"prodOffer.selectProdOfferSeq", null);
	}

	public String queryOfferProdRelSeq() {
		return (String) sqlMapDao.queryForObject(
				"offerProdRel.selectOfferProdRelSeq", null);
	}

	public String queryProdChannelTypeSeq() {
		return (String) sqlMapDao.queryForObject(
				"prodOfferChannelType.selectProdOfferChannelTypeSeq", null);
	}

	public String queryPricingPlanSeq() {
		return (String) sqlMapDao.queryForObject(
				"pricing.selectPricingPlanSeq", null);
	}

	public String queryPricingPlan2ProcModeSeq() {
		return (String) sqlMapDao.queryForObject(
				"pricing.selectPricingPlan2ProcModeSeq", null);
	}

	public String queryProcModeInstSeq() {
		return (String) sqlMapDao.queryForObject(
				"pricing.selectProcModeInstSeq", null);
	}

	public Component queryComponent(Component component) {
		return (Component) sqlMapDao.queryForObject(
				"component.selectComponent", component);
	}

	public List<PricingClassify> queryPricingClass() {
		return (List<PricingClassify>) sqlMapDao.queryForList(
				"pricingClassify.selectPricingClassify", null);
	}

	public int queryProcModeNum(Map map) {
		return (Integer) sqlMapDao.queryForObject("procMode.selectProcModeNum",
				map);
	}

	public List<Map> queryProcModeDesc(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.queryProcModeDesc", map);
	}

	public List<ProcMode> queryProcModeList(Map map) {
		return (List<ProcMode>) sqlMapDao.queryForList(
				"procMode.selectProcModeInfo", map);
	}

	public void insertProdOffer(ProdOffer prodOffer) {
		sqlMapDao.insert("prodOffer.insertProdOffer", prodOffer);
	}
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) {
		return (OfferProdRel) sqlMapDao.queryForObject(
				"offerProdRel.selectOfferProdRel", offerProdRel);
	}

	@SuppressWarnings("unchecked")
	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel) {
		return  sqlMapDao.queryForList("offerProdRel.selectOfferProdRel", offerProdRel);
	}
	
	public void insertOfferProdRel(OfferProdRel offerProdRel) {
		sqlMapDao.insert("offerProdRel.insertOfferProdRel", offerProdRel);
	}

	public void insertProdChannelType(ProdOfferChannelType prodChannelType) {
		sqlMapDao.insert("prodOfferChannelType.insertProdOfferChannelType",
				prodChannelType);
	}

	public void deleteProdChannelType(ProdOffer prodOffer) {
		sqlMapDao.delete("prodOfferChannelType.deleteProdOfferChannelType",
				prodOffer);
	}

	public void insertProduct(Product product) {
		sqlMapDao.insert("product.insertProduct", product);
	}

	public void insertValueAddedProd(ValueAddedProdInfo valueAddedProd) {
		sqlMapDao.insert("valueAddedProdInfo.insertValueAddedProdInfo",
				valueAddedProd);
	}

	public void updateProdOffer(ProdOffer prodOffer) {
		sqlMapDao.update("prodOffer.updateProdOffer", prodOffer);
	}

	public void updateValueAddedProdInfo(ValueAddedProdInfo valueAddedProd) {
		sqlMapDao.update("valueAddedProdInfo.updateValueAddedProdInfo",
				valueAddedProd);
	}

	public void updateProduct(Product product) {
		sqlMapDao.update("product.updateProduct", product);
	}

	public List<Map> queryAbilityAttr(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.queryAbilityAttr", map);
	}

	public List<Map> queryAttrValue(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.queryAttrValue", map);
	}

	public Integer insertProcModeInst(ProcModeInst procModeInst) {
		return (Integer)sqlMapDao.insert("pricing.insertProcModeInst", procModeInst);
	}
	
	public Integer insertProcModeInstAttr(ProcModeInstAttr procModeInstAttr) {
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardProd.insertProcModeInstAttr", procModeInstAttr);
	}
	

	public void insertPricePlan2ProcModeInst(PricingPlan2ProcModeInst bean) {
		sqlMapDao.insert("pricing.insertPricingPlan2ProcModeInst", bean);
	}

	public void insertPricingPlan(PricingPlan pricingPlan) {
		sqlMapDao.insert("pricing.insertPricingPlan", pricingPlan);
	}

	public String queryProdOfferPricingSeq() {
		return (String) sqlMapDao.queryForObject(
				"pricing.selectProdOfferPricinSeq", null);
	}

	public void insertProdOfferPricing(ProdOfferPricing prodOfferPricing) {
		sqlMapDao.insert("pricing.insertProdOfferPricing", prodOfferPricing);
	}

	public List<PricingClassify> queryPricingClassifyByProdOfferId(
			Map mapPricing) {
		return (List<PricingClassify>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProd.queryPricingClassify", mapPricing);
	}

	/**
	 * 查询产品属性
	 */
	public String queryProductAtrrSeq() {
		return (String) sqlMapDao.queryForObject(
				"productAtrr.selectProductAtrrSeq", null);
	}

	/**
	 * 获得属性值规则序列
	 */
	public String queryAttrSpecSeq() {
		return (String) sqlMapDao.queryForObject("atrrSpec.selectatrrSpecSeq",
				null);
	};

	/**
	 * 插入产品属性规格
	 */
	public void insertAttrSpec(AttrSpec attrSpec) {
		sqlMapDao.insert("atrrSpec.insertAttrSpec", attrSpec);
	}


	/**
	 * 删除产品属性规格
	 * 
	 * @param attrSpec
	 */
	public void deleteAttrSpec(AttrSpec attrSpec) {
		sqlMapDao.delete("atrrSpec.deleteAttrSpec", attrSpec);
	}

	/**
	 * 查询定价计划表
	 */
	public PricingPlan queryPricingPlan(PricingPlan pricingPlan) {
		return (PricingPlan)sqlMapDao.queryForObject("pricing.selectPricingPlan", pricingPlan);
		
	}
    /**
     * 查询费用类别
     * @param prodOfferPricing
     * @return
     */
     public  List<Map> queryPricingClassify(ProdOfferPricing prodOfferPricing){
    	return (ArrayList<Map>)sqlMapDao.queryForList("pricing.selectClassifyNameByProdofferId", prodOfferPricing);
    }
     /**
      * 更新定价计划表
      * @param pricingPlan
      */
     public void updatePrincingPlan(PricingPlan pricingPlan){
        sqlMapDao.update("pricing.updatePricingPlan", pricingPlan);
     }
     /**
      * 删除定价计划表 
      */
     public void deletePrincingPlan(PricingPlan pricingPlan){
    	 sqlMapDao.delete("pricing.deletePricingPlan", pricingPlan);
     }
     /**
 	 * 删除销售品定价计划表
 	 * @param prodOfferPricing
 	 */
 	 public void deleteProdOfferPricing(ProdOfferPricing prodOfferPricing){
 		sqlMapDao.delete("eaap-op2-portal-pardMix.deleteProdOfferPricing", prodOfferPricing);
 	}
 	    public List queryPardProdInfo(Map map){
		    return sqlMapDao.queryForList("eaap-op2-portal-pardProd.queryPardProdInfo", map);
	  }
	
	  public List queryPardSellInfo(Map map){
		  return sqlMapDao.queryForList("eaap-op2-portal-pardProd.queryPardSellInfo", map);
	  }
		/**
	     *删除配置能力实例
	     */
	 public void deleteProcModeInst(ProcModeInst procModeInst){
			sqlMapDao.delete("procModeInst.deleteProcModeInst", procModeInst);
	 }
		/**
		 * 删除配置能力关联
		 * @param pricingPlan2ProcModeInst
		 */
	 public void deletePricingPlan2ProcModeInst(PricingPlan2ProcModeInst pricingPlan2ProcModeInst){
		 sqlMapDao.delete("pricingPlan2ProcModeInst.deletePricingPlan2ProcModeInst", pricingPlan2ProcModeInst);
	 }
	 /**
	     * 更新属性
	     * @param productAttr
	     */
	 public void updateProductAttr(ProductAttr productAttr){
	    	sqlMapDao.update("productAtrr.updateProductAttr", productAttr);
	 }
	 




	    /**
	     * 产品属性分组表增删查
	     */
	    public String queryProductAttrGroupSeq(){
	    	return (String)sqlMapDao.queryForObject("productAttrGroup.queryProductAttrGroupSeq", null);
	    }
	    public void insertProductAttrGroup(ProductAttrGroup productAttrGroup){
	    	sqlMapDao.insert("productAttrGroup.insertProductAttrGroup", productAttrGroup);
	    }
	    public List<ProductAttrGroup> queryProductAttrGroup(ProductAttrGroup productAttrGroup){
	    	return (List<ProductAttrGroup>)sqlMapDao.queryForList("productAttrGroup.queryProductAttrGroup", productAttrGroup);
	    }
	    public void deleteProductAttrGroup(ProductAttrGroup productAttrGroup){
	    	sqlMapDao.delete("productAttrGroup.deleteProductAttrGroup", productAttrGroup);
	    }
	    /**
	     * 产品属性归属分组表增删查
	     */
	    public String queryProductAttrGroupRelaSeq(){
	    	return (String)sqlMapDao.queryForObject("productAttrGroupRela.queryProductAttrGroupRelaSeq", null);
	    }
	    public void insertProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela){
	    	sqlMapDao.insert("productAttrGroupRela.insertProductAttrGroupRela", productAttrGroupRela);
	    }
	    public List<ProductAttrGroupRela> queryProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela){
	    	return (List<ProductAttrGroupRela>)sqlMapDao.queryForList("productAttrGroupRela.queryProductAttrGroupRela", productAttrGroupRela);
	    }
	    public void deleteProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela){
	    	sqlMapDao.delete("productAttrGroupRela.deleteProductAttrGroupRela", productAttrGroupRela);
	    }
	    
	    /**
	     * 产品选取属性表增删查
	     * @param productAttr
	     */
	    public String queryProductAttrSeq(){
			return (String) sqlMapDao.queryForObject(
					"productAtrr.selectProductAtrrSeq", null);
	    }
	    public void insertProductAttr(ProductAttr productAttr){
	    	sqlMapDao.insert("productAtrr.insertProdAttr", productAttr);
	    }
	    public List<ProductAttr> queryProductAttr(ProductAttr productAttr){
	    	return (List<ProductAttr>) sqlMapDao.queryForList(
					"productAtrr.selectProductAtrr", productAttr);
	    }
	    public void deleteProductAttr(ProductAttr productAttr){
	    	sqlMapDao.delete("productAtrr.deleteProductAttr", productAttr);
	    }

	    /**
	     * 产品属性选用值表增删查
	     * @param prodAttrValue
	     */
	    public String queryProdAttrValueSeq(){
	    	return (String)sqlMapDao.queryForObject("prodAttrValue.queryProdAttrValueSeq", null);
	    }
	    public void insertProdAttrVaule(ProdAttrValue prodAttrValue){
	    	sqlMapDao.insert("prodAttrValue.insertProdAttrVaule", prodAttrValue);
	    }
	    public List<ProdAttrValue> queryProdAttrVaule(ProdAttrValue prodAttrValue){
	    	return (List<ProdAttrValue>)sqlMapDao.queryForList("prodAttrValue.queryProdAttrVaule", prodAttrValue);
	    }
	    public void deleteProdAttrVaule(ProdAttrValue prodAttrValue){
	    	sqlMapDao.delete("prodAttrValue.deleteProdAttrVaule", prodAttrValue);
	    }
	    
	    /**
	     * 属性值规格2表增删查
	     * @param attrValue
	     */
	    public String queryAttrValueSeq(){
	    	return (String)sqlMapDao.queryForObject("attrValue.queryAttrValueSeq", null);
	    }
	    public void insertAttrValue(AttrValue attrValue){
	    	sqlMapDao.insert("attrValue.insertAttrValue", attrValue);
	    }
	    public List<AttrValue> queryAttrValue(AttrValue attrValue){
	    	return (List<AttrValue>)sqlMapDao.queryForList("attrValue.queryAttrValue", attrValue);
	    }
	    public void deleteAttrValue(AttrValue attrValue){
	    	sqlMapDao.delete("attrValue.deleteAttrValue", attrValue);
	    }
	   
	    /**
	     * 查询属性规格1表
	     * @param attrSpec
	     * @return
	     */
	    @ProvideI18NDatas(values = { 
				@ProvideI18NData(tableName = "attr_spec", columnNames = "ATTR_SPEC_NAME", idName = "attrSpecId", propertyNames = "attrSpecName") 
			}
		)
	    public AttrSpec queryAttrSpec(AttrSpec attrSpec){
			return (AttrSpec) sqlMapDao.queryForObject("atrrSpec.selectAtrrSpec",
					attrSpec);
	    }
	    public ProductAttr queryProductAttrGroupRelaAndProductAttr(Map hashMap){
	    	return (ProductAttr)sqlMapDao.queryForObject("eaap-op2-portal-pardProd.queryProductAttrGroupRelaAndProductAttr", hashMap);
	    }
	    public List<AttrValue> queryProductAttrValueAndAttrValue(ProductAttr productAttr){
	    	return (List<AttrValue>)sqlMapDao.queryForList("eaap-op2-portal-pardProd.queryProductAttrValueAndAttrValue", productAttr);
	    }
	    public List<ProdOffer> compareProdOffer(ProdOffer prodOffer){
	    	return (List<ProdOffer>)sqlMapDao.queryForList("eaap-op2-portal-pardProd.compareProdOfferId", prodOffer);
	    }
	    public void insertPricingPlanClassifyRel(PricingPlanClassifyRel planClassifyRel){
	    	sqlMapDao.insert("pricing.insertPricingPlanClassifyRel", planClassifyRel);
	    }
		public String queryPricingPlanClassifyRelSeq() {
			return (String) sqlMapDao.queryForObject("pricing.selectPricingPlanClassifyRelSeq",
					null);
		};
		public String queryOfferProdRelPricingSeq(){
			return (String)sqlMapDao.queryForObject("pricing.selectOfferProdRelPricingSeq", null);
		}
	    public void deleteProcModeInstAttr(ProcModeInstAttr procModeInstAttr){
	    	sqlMapDao.delete("pricing.deleteProcModeInstAttrByProcModeInstId", procModeInstAttr);
	    }	    
		public void insertOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing){
	    	sqlMapDao.insert("pricing.insertOfferProdRelPricing", offerProdRelPricing);
	    }
		public Map<String,Object> queryProduct(Map<String, Object> hashMap){
			return (Map<String,Object>)sqlMapDao.queryForObject("eaap-op2-portal-pardProd.queryProduct", hashMap);
		}
		public List<Map> queryPricingPlan(OfferProdRel offerProdRel){
			return sqlMapDao.queryForList("eaap-op2-portal-pardProd.queryPricingPlan", offerProdRel);
		}
		public void deleteOfferProdRelPricing(ProdOfferPricing prodOfferPricing){
			sqlMapDao.delete("eaap-op2-portal-pardProd.deleteOfferProdRelPricing", prodOfferPricing);
		}
		public String queryPricingPlanClassfiyRelSeq(){
			return (String)sqlMapDao.queryForObject("eaap-op2-portal-pardProd.queryPricingPlanClassfiyRelSeq", null);
		}
		public void deletePricingPlanClassifyRel(PricingPlanClassifyRel pricingPlanClassifyRel){
			sqlMapDao.delete("eaap-op2-portal-pardProd.deletePricingPlanClassifyRel", pricingPlanClassifyRel);
		}
		
	    public  List<Map> selectRelPricingByProdofferId(ProdOfferPricing prodOfferPricing){
	     	return (ArrayList<Map>)sqlMapDao.queryForList("pricing.selectRelPricingByProdofferId", prodOfferPricing);
	    }
}
