package com.ailk.o2p.portal.service.pard;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdAttrValue;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannelType;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.bo.ProductAttrGroup;
import com.ailk.eaap.op2.bo.ProductAttrGroupRela;
import com.ailk.o2p.portal.dao.pard.PardProdDao;
import com.ailk.o2p.portal.dao.pard.PardmixDao;

public class PardmixServ implements IPardmixServ {
	private PardmixDao pardMixSqlDAO;
	private PardProdDao pardProdDao;

	public List<Map> queryProdDealSvcList(Map map) {
		return pardProdDao.queryProdDealSvcList(map);
	}

	public Integer deleteProdOfferChannelType(
			ProdOfferChannelType prodOfferChannelType) {
		return pardMixSqlDAO.deleteProdOfferChannelType(prodOfferChannelType);
	}

	public List<Map> selectProdOfferList(Map map) {
		return pardMixSqlDAO.selectProdOfferList(map);
	}

	public List<Map> selectPricingListByOfferId(ProdOffer prodOffer) {
		return pardMixSqlDAO.selectPricingListByOfferId(prodOffer);
	}

	public List<Map> selectAllAttrValueByOfferId(Product product) {
		return pardMixSqlDAO.selectAllAttrValueByOfferId(product);
	}

	public Integer insertProdAttrValue(ProdAttrValue prodAttrValue) {
		return pardMixSqlDAO.insertProdAttrValue(prodAttrValue);
	}

	public String queryProdOfferPricingSeq() {
		return pardProdDao.queryProdOfferPricingSeq();
	}

	public void insertProdOfferPricing(ProdOfferPricing prodOfferPricing) {
		pardProdDao.insertProdOfferPricing(prodOfferPricing);
	}

	public List<Map> selectProdOffer(Map map) {
		return pardMixSqlDAO.selectProdOffer(map);
	}

	public Integer updateProductAttr(ProductAttr productAttr) {
		return pardMixSqlDAO.updateProductAttr(productAttr);
	}

	public List<ProdOffer> selectProdOffer(ProdOffer prodOffer) {
		return pardMixSqlDAO.selectProdOffer(prodOffer);
	}

	public List<Product> selectProduct(Product product) {
		return pardMixSqlDAO.selectProduct(product);
	}

	public Integer insertProduct(Product product) {
		return pardMixSqlDAO.insertProduct(product);
	}

	public Integer insertProductAttr(ProductAttr productAttr) {
		return pardMixSqlDAO.insertProductAttr(productAttr);
	}

	public Integer insertProdOffer(ProdOffer prodOffer) {
		return pardMixSqlDAO.insertProdOffer(prodOffer);
	}

	public List<Map> selectProductAttrInGroup(ProductAttr productAttr) {
		return pardMixSqlDAO.selectProductAttrInGroup(productAttr);
	}

	public List<Map> selectProductAttr(ProductAttr productAttr) {
		return pardMixSqlDAO.selectProductAttr(productAttr);
	}

	public List<Map> selectGroupInfoByProductId(Map map) {
		return pardMixSqlDAO.selectGroupInfoByProductId(map);

	}

	public List<ProductAttrGroup> selectProductAttrGroup(
			ProductAttrGroup productAttrGroup) {
		return pardMixSqlDAO.selectProductAttrGroup(productAttrGroup);

	}

	public Integer insertProductAttrGroup(ProductAttrGroup productAttrGroup) {
		return pardMixSqlDAO.insertProductAttrGroup(productAttrGroup);
	}

	public Integer insertProductAttrGroupRela(
			ProductAttrGroupRela productAttrGroupRela) {
		return pardMixSqlDAO.insertProductAttrGroupRela(productAttrGroupRela);
	}

	public List<Map> selectPricingClassifyByPid(Map map) {
		return pardMixSqlDAO.selectPricingClassifyByPid(map);
	}

	public Integer insertOfferProdRel(OfferProdRel offerProdRel) {
		return pardMixSqlDAO.insertOfferProdRel(offerProdRel);
	}

	public Integer insertProdOfferChannelType(
			ProdOfferChannelType prodOfferChannelType) {
		return pardMixSqlDAO.insertProdOfferChannelType(prodOfferChannelType);
	}

	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel) {
		return pardMixSqlDAO.selectOfferProdRel(offerProdRel);
	}

	public Integer updateProdOffer(ProdOffer prodOffer) {
		return pardMixSqlDAO.updateProdOffer(prodOffer);
	}

	public List<ProdOfferChannelType> selectProdOfferChannelType(
			ProdOfferChannelType prodOfferChannelType) {
		return pardMixSqlDAO.selectProdOfferChannelType(prodOfferChannelType);
	}

	public Integer updateProductAttrGroup(ProductAttrGroup productAttrGroup) {
		return pardMixSqlDAO.updateProductAttrGroup(productAttrGroup);
	}

	public List<ProductAttrGroupRela> selectProductAttrGroupRela(
			ProductAttrGroupRela productAttrGroupRela) {
		return pardMixSqlDAO.selectProductAttrGroupRela(productAttrGroupRela);
	}

	public void updateProductAttrStatueByGroupId(
			ProductAttrGroup productAttrGroup) {
		pardMixSqlDAO.updateProductAttrStatueByGroupId(productAttrGroup);
	}

	public PardmixDao getPardMixSqlDAO() {
		return pardMixSqlDAO;
	}

	public void setPardMixSqlDAO(PardmixDao pardMixSqlDAO) {
		this.pardMixSqlDAO = pardMixSqlDAO;
	}

	public PardProdDao getPardProdDao() {
		return pardProdDao;
	}

	public void setPardProdDao(PardProdDao pardProdDao) {
		this.pardProdDao = pardProdDao;
	}

	// ////////////////////////////////////////////
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel) {
		return pardMixSqlDAO.selectProdOfferList(prodOfferRel);
	}

	public Integer insertProdOfferRel(ProdOfferRel prodOfferRel) {
		return pardMixSqlDAO.insertProdOfferRel(prodOfferRel);
	}

	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel) {
		return pardMixSqlDAO.updateProdOfferRel(prodOfferRel);
	}

	public void delProdOfferRel(ProdOfferRel prodOfferRel) {
		pardMixSqlDAO.delProdOfferRel(prodOfferRel);
	}
}