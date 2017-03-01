package com.ailk.o2p.portal.service.pard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferAttr;
import com.ailk.eaap.op2.bo.ProdOfferPricing;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.pard.IPardMealRateDAO;

public class PardMealRateServImpl implements IPardMealRateServ {

	private IPardMealRateDAO pardMealRateDAO;
	private MainDataDao mainDataSqlDAO;
	private MainDataTypeDao mainDataTypeSqlDAO;

	public void setPardMealRateDAO(IPardMealRateDAO pardMealRateDAO) {
		this.pardMealRateDAO = pardMealRateDAO;
	}

	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}

	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}

	public List<Map<String, Object>> getVoiceList(Map<String, String> map) {
		return pardMealRateDAO.getVoiceList(map);
	}

	public List<Map<String, Object>> getDataList(Map<String, String> map) {
		return pardMealRateDAO.getDataList(map);
	}

	public List<Map<String, Object>> getMsgList(Map<String, String> map) {
		return pardMealRateDAO.getMsgList(map);
	}

	public List<Map<String, Object>> getProductList(Map<String, String> map) {
		return pardMealRateDAO.getProductList(map);
	}

	public void insertAll(ProdOffer prodOffer,
			List<ProdOfferAttr> prodOfferAttrList,
			List<OfferProdRel> offerProdRelList,
			ProdOfferPricing prodOfferPricing) {

		BigDecimal prodOfferId = pardMealRateDAO.getProdOfferId();
		prodOffer.setProdOfferId(prodOfferId);

		pardMealRateDAO.insertProdOffer(prodOffer);
		for (ProdOfferAttr prodOfferAttr : prodOfferAttrList) {
			Integer prodOfferAttrId = pardMealRateDAO.getProdOfferAttrId();
			prodOfferAttr.setProdOfferAttrId(prodOfferAttrId);
			prodOfferAttr.setProdOfferId(prodOfferId);
		}

		for (OfferProdRel offerProdRel : offerProdRelList) {
			Integer offerProdRelaId = pardMealRateDAO.getOfferProdRelId();
			offerProdRel.setOfferProdRelaId(offerProdRelaId);
			offerProdRel.setProdOfferId(prodOfferId);
		}
		pardMealRateDAO.insertProdOfferAttr(prodOfferAttrList);
		pardMealRateDAO.insetOfferProdRel(offerProdRelList);

	}

	public String getProdOfferPricingId() {
		return pardMealRateDAO.getProdOfferPricingId();
	}

	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType);
	}

	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData);
	}

	public List<HashMap> showProdOfferList(Map map) {
		if ("ALLNUM".equals(map.get("queryType"))) {
			return pardMealRateDAO.queryProdOfferCount(map);
		} else {
			return pardMealRateDAO.showProdOfferList(map);
		}
	}

	public ProdOffer getProdOffer(ProdOffer prodOffer) {
		return pardMealRateDAO.getProdOffer(prodOffer);
	}

	public List<HashMap> selectPricingListByOfferId(ProdOffer prodOffer) {
		return pardMealRateDAO.selectPricingListByOfferId(prodOffer);
	}

	public void updateAll(ProdOffer prodOffer,
			List<ProdOfferAttr> prodOfferAttrList,
			List<OfferProdRel> offerProdRelList) {

		pardMealRateDAO.updateProdOffer(prodOffer);

		for (ProdOfferAttr poa : prodOfferAttrList) {
			pardMealRateDAO.updateProdOfferAttr(poa);
		}

		for (OfferProdRel opr : offerProdRelList) {
			pardMealRateDAO.updateOfferProdRel(opr);
		}

	}

	public void deletePardMealRate(ProdOffer prodOffer) {
		pardMealRateDAO.deletePardMealRate(prodOffer);
	}

	public void updateProdOffer(ProdOffer prodOffer) {
		pardMealRateDAO.updateProdOffer(prodOffer);
	}

	public int queryMoreExtByOrgId(Map<String, String> map) {
		return pardMealRateDAO.queryMoreExtByOrgId(map);
	}

}