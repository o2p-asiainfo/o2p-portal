package com.ailk.o2p.portal.dao.price;

 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.PriceItem;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.ScopeApplication;
import com.ailk.o2p.portal.dao.pardproduct.PardProdDao;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class PriceDaoImpl implements PriceDao {
	@Autowired
	private SqlMapDAO sqlMapDao;
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	
	//定价计划
	public List<PricingPlan> queryPricingPlan(PricingPlan pricingPlan){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryPricingPlan", pricingPlan);
	}
	public Integer countpricingPlan(PricingPlan pricingPlan){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-price.countpricingPlan", pricingPlan);
	}
	public Integer addPricingPlan(PricingPlan pricingPlan){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-price.addPricingPlan", pricingPlan);
	}
	public Integer updatePricingPlan(PricingPlan pricingPlan){
		return sqlMapDao.update("eaap-op2-portal-price.updatePricingPlan", pricingPlan);
	}
	public List<PricingPlan> getPricingPlan(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-price.getPricingPlan", map);
	}
	//定价计划关联
	public List<ProdOfferPricing> queryProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryProdOfferPricing", prodOfferPricing);
	}
	public Integer updateProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return sqlMapDao.update("eaap-op2-portal-price.updateProdOfferPricing", prodOfferPricing);
	}
	public Integer addProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-price.addProdOfferPricing", prodOfferPricing);
	}
	//定价对象
	public Integer addPricingTarget(PricingTarget pricingTarget){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-price.addPricingTarget", pricingTarget);
	}
	public Integer updatePricingTarget(PricingTarget pricingTarget){
		return sqlMapDao.update("eaap-op2-portal-price.updatePricingTarget", pricingTarget);
	}
	public List<PricingTarget> queryPricingTarget(PricingTarget pricingTarget){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryPricingTarget", pricingTarget);
	}
	//定价-销售品关联
	public Integer addProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-price.addProdRelPricing", offerProdRelPricing);
	}
	public Integer updateProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return sqlMapDao.update("eaap-op2-portal-price.updateProdRelPricing", offerProdRelPricing);
	}
	public List<OfferProdRelPricing> queryOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryOfferProdRelPricing", offerProdRelPricing);
	}
	//科目
	public List<PriceItem> queryPriceItem(PriceItem priceItem){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryPriceItem", priceItem);
	}
	public List<PriceItem> queryPriceItemList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-price.queryPriceItemList", map);

	}
	public Integer countPriceItemList(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-price.countPriceItemList", map);
	}
	//适用范围
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication){
		return sqlMapDao.queryForList("eaap-op2-portal-price.selectScopeApplication", scopeApplication);
	}
	//
	public List<Map<String,Object>> selectProductNameById(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-price.selectProductNameById", map);
	}
	public List getSystemStatus(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-price.getSystemStatus", map);
    }
}
