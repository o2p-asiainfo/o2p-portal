package com.ailk.o2p.portal.dao.price;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.BaseTariffItemRel;
import com.ailk.eaap.op2.bo.BasicTariff;
import com.ailk.eaap.op2.bo.FreeResource;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.OneTimeCharge;
import com.ailk.eaap.op2.bo.PecurringFee;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.FreeResourceSeg;
import com.ailk.eaap.op2.bo.RatingDiscount;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class PricePlanDetailImpl implements PricePlanDetailDao {
	@Autowired
	private SqlMapDAO sqlMapDao;
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	public List<ComponentPrice> queryComponentPrice(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryComponentPrice", map);
	}

	public Integer countComponentPrice(Map map) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.countComponentPrice", map);
	}

	public Integer addComponentPrice(ComponentPrice componentPrice) {
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addComponentPrice", componentPrice);
	}

	public Integer updateComponentPrice(ComponentPrice componentPrice) {
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateComponentPrice", componentPrice);
	}

	public BasicTariff queryBasicTariff(BasicTariff basicTariff) {
		return (BasicTariff) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryBasicTariff", basicTariff);
	}

	public Integer updateBasicTariff(BasicTariff basicTariff) {
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateBasicTariff", basicTariff);
	}

	public Integer addBasicTariff(BasicTariff basicTariff) {
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addBasicTariff", basicTariff);
	}

	public Integer addRecurringFee(PecurringFee pecurringFee) {
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addRecurringFee", pecurringFee);
	}

	public Integer updateRecurringFee(PecurringFee pecurringFee) {
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateRecurringFee", pecurringFee);
	}

	public PecurringFee queryRecurringFee(PecurringFee pecurringFee) {
		return (PecurringFee) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryRecurringFee", pecurringFee);
	}

	public Integer addRatingDiscount(RatingDiscount ratingDiscount){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addRatingDiscount", ratingDiscount);
	}
	public Integer updateRatingDiscount(RatingDiscount ratingDiscount){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.updateRatingDiscount", ratingDiscount);
	}
	public RatingDiscount queryRatingDiscount(RatingDiscount ratingDiscount){
		return (RatingDiscount) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryRatingDiscount", ratingDiscount);
	}
	
	public Integer addBillingDiscount(BillingDiscount billingDiscount){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addBillingDiscount", billingDiscount);
	}
	public Integer updateBillingDiscount(BillingDiscount billingDiscount){
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateBillingDiscount", billingDiscount);
	}
	public BillingDiscount queryBillingDiscount(BillingDiscount billingDiscount){
		return (BillingDiscount) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryBillingDiscount", billingDiscount);
	}
	
	
	public List<RatingCurverDetail> queryRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryRatingCurveDetail", ratingCurverDetail);
	}
	public Integer addRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addRatingCurveDetail", ratingCurverDetail);
	}
	public Integer updateRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateRatingCurveDetail", ratingCurverDetail);
	}
	public void delRatingCurveDetail(RatingCurverDetail ratingCurverDetail){
		sqlMapDao.delete("eaap-op2-portal-componentPrice.delRatingCurveDetail", ratingCurverDetail);
	}
	
	public Integer addOneTimeCharge(OneTimeCharge oneTimeCharge){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addOneTimeCharge", oneTimeCharge);
	}
	public Integer updateOneTimeCharge(OneTimeCharge oneTimeCharge){
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateOneTimeCharge", oneTimeCharge);
	}
	public OneTimeCharge queryOneTimeCharge(OneTimeCharge oneTimeCharge){
		return (OneTimeCharge) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryOneTimeCharge", oneTimeCharge);
	}
	
	public List<BaseTariffItemRel> getBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.getBaseTariffItemRel", baseTariffItemRel);
	}
	public Integer addBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addBaseTariffItemRel", baseTariffItemRel);
	}
	public void delBaseTariffItemRel(BaseTariffItemRel baseTariffItemRel){
		sqlMapDao.delete("eaap-op2-portal-componentPrice.delBaseTariffItemRel", baseTariffItemRel);
	}


	public FreeResource queryFreeResource(FreeResource freeResource) {
		return (FreeResource) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryFreeResource", freeResource);
	}
	public Integer updateFreeResource(FreeResource freeResource) {
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateFreeResource", freeResource);
	}
	public Integer addFreeResource(FreeResource freeResource) {
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addFreeResource", freeResource);
	}

	public Integer queryFreeResourceItemListCount(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-componentPrice.queryFreeResourceItemListCount", map);
	}
	public List queryFreeResourceItemList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryFreeResourceItemList", map);
	}
	

	public List<FreeResourceSeg> queryFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return sqlMapDao.queryForList("eaap-op2-portal-componentPrice.queryFreeResourceSeg", freeResourceSeg);
	}
	public Integer addFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-componentPrice.addFreeResourceSeg", freeResourceSeg);
	}
	public Integer updateFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		return sqlMapDao.update("eaap-op2-portal-componentPrice.updateFreeResourceSeg", freeResourceSeg);
	}
	public void delFreeResourceSeg(FreeResourceSeg freeResourceSeg){
		sqlMapDao.delete("eaap-op2-portal-componentPrice.delFreeResourceSeg", freeResourceSeg);
	}
}
