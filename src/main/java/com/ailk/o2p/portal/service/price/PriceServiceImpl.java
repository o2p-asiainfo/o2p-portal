package com.ailk.o2p.portal.service.price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRelPricing;
import com.ailk.eaap.op2.bo.PriceItem;
import com.ailk.o2p.portal.bo.PricingPlan;
import com.ailk.eaap.op2.bo.PricingTarget;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.ScopeApplication;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.price.PriceDao;
@Service
@Transactional
public class PriceServiceImpl implements PriceService {
	@Autowired
	private PriceDao priceDao ;
	@Autowired
    private MainDataDao mainDataSqlDAO ;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	
	public PriceDao getPriceDao() {
		return priceDao;
	}
	public void setPriceDao(PriceDao priceDao) {
		this.priceDao = priceDao;
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
	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData) ;
	}
	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		// TODO Auto-generated method stub
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType);
	}
//--------------------------------------------------------
	//定价计划
	public List<PricingPlan> queryPricingPlan(PricingPlan pricingPlan){
		return priceDao.queryPricingPlan(pricingPlan);
	}
	public Integer countpricingPlan(PricingPlan pricingPlan){
		return priceDao.countpricingPlan(pricingPlan);
	}
	public Integer addPricingPlan(PricingPlan pricingPlan){
		return priceDao.addPricingPlan(pricingPlan);
	}
	public Integer updatePricingPlan(PricingPlan pricingPlan){
		return priceDao.updatePricingPlan(pricingPlan);
	}
	public List<PricingPlan> getPricingPlan(Map map){
		return priceDao.getPricingPlan(map);
	}
	//定价计划关联
	public List<ProdOfferPricing> queryProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return priceDao.queryProdOfferPricing(prodOfferPricing);
	}
	public Integer updateProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return priceDao.updateProdOfferPricing(prodOfferPricing);
	}
	public Integer addProdOfferPricing(ProdOfferPricing prodOfferPricing){
		return priceDao.addProdOfferPricing(prodOfferPricing);
	}
	//定价对象
	public Integer addPricingTarget(PricingTarget pricingTarget){
		return priceDao.addPricingTarget(pricingTarget);
	}
	public Integer updatePricingTarget(PricingTarget pricingTarget){
		return priceDao.updatePricingTarget(pricingTarget);
	}
	
	public List<PricingTarget> queryPricingTarget(PricingTarget pricingTarget){
		return priceDao.queryPricingTarget(pricingTarget);
	}
	//定价-销售品关联
	public Integer addProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return priceDao.addProdRelPricing(offerProdRelPricing);
	}
	public Integer updateProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return priceDao.updateProdRelPricing(offerProdRelPricing);
	}
	public List<OfferProdRelPricing> queryOfferProdRelPricing(OfferProdRelPricing offerProdRelPricing){
		return priceDao.queryOfferProdRelPricing(offerProdRelPricing);
	}
	//科目
	public List<PriceItem> queryPriceItem(PriceItem priceItem){
		return priceDao.queryPriceItem(priceItem);
	}
	public List<PriceItem> queryPriceItemList(Map map){
		return priceDao.queryPriceItemList(map);
	}
	public Integer countPriceItemList(Map map){
		return priceDao.countPriceItemList(map);
	}
	//适用范围
	public List<ScopeApplication> selectScopeApplication(ScopeApplication scopeApplication){
		return priceDao.selectScopeApplication(scopeApplication);
	}

	//
	public List<Map<String,Object>> selectProductNameById(Map<String,Object> map){
		return priceDao.selectProductNameById(map);
	}
	
	
	public void deletePardOffertPricePlan(String priceInfoId ,String prodOfferId,String pricingTargetId)throws Exception {
		PricingPlan pricingPlan=new PricingPlan();
		pricingPlan.setPricingInfoId(Integer.parseInt(priceInfoId));
		pricingPlan.setStatusCd("11");
		this.updatePricingPlan(pricingPlan);
		
		ProdOfferPricing prodOfferPricing=new ProdOfferPricing();
		prodOfferPricing.setPricingInfoId(Integer.parseInt(priceInfoId));
		prodOfferPricing.setProdOfferId(BigDecimal.valueOf(Long.valueOf(prodOfferId)));
		prodOfferPricing = this.queryProdOfferPricing(prodOfferPricing).get(0);
		prodOfferPricing.setStatusCd("11");
		this.updateProdOfferPricing(prodOfferPricing);
		
		PricingTarget pricingTarget=new PricingTarget();
		pricingTarget.setPricingTargetId(Integer.parseInt(pricingTargetId));
		pricingTarget.setStatusCd("11");
		this.updatePricingTarget(pricingTarget);
		
		OfferProdRelPricing offerProdRelPricing=new OfferProdRelPricing();
		offerProdRelPricing.setPricingInfoId(Integer.parseInt(pricingTargetId));
		List<OfferProdRelPricing> offerProdRelPricingList = this.queryOfferProdRelPricing(offerProdRelPricing);
		for(OfferProdRelPricing o : offerProdRelPricingList){
			o.setStatusCd("11");
			this.updateProdRelPricing(o);
		}
	}
	public List getSystemStatus(Map map){
		return priceDao.getSystemStatus(map);
	}
}
