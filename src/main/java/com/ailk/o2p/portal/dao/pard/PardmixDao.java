package com.ailk.o2p.portal.dao.pard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdAttrValue;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannelType;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.bo.ProductAttrGroup;
import com.ailk.eaap.op2.bo.ProductAttrGroupRela;

public interface PardmixDao {
	public List<Map> selectProdOfferList(Map map) ;
	public List<Map> selectProdOffer(Map map) ;
	public Integer insertProdOffer(ProdOffer prodOffer) ;
	public List<ProdOffer> selectProdOffer(ProdOffer prodOffer);
	public Integer insertOfferProdRel(OfferProdRel offerProdRel);
	public Integer insertProdOfferChannelType(ProdOfferChannelType prodOfferChannelType);
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel) ;
	public List<Product>  selectProduct(Product product) ;
	public List<Map>  selectProductAttr(ProductAttr productAttr) ;
	public List<Map>  selectProductAttrInGroup(ProductAttr productAttr) ;
	public Integer insertProduct(Product product) ;
	public Integer insertProductAttr(ProductAttr productAttr) ;
	public Integer updateProdOffer(ProdOffer prodOffer) ;
	public List<ProdOfferChannelType>  selectProdOfferChannelType(ProdOfferChannelType prodOfferChannelType) ;
	public List<Map> selectPricingListByOfferId(ProdOffer prodOffer) ;
	public List<Map> selectAllAttrValueByOfferId(Product product) ;
	public Integer insertProdAttrValue(ProdAttrValue prodAttrValue) ;
	public Integer updateProductAttr(ProductAttr productAttr) ;
	public Integer deleteProdOfferChannelType(ProdOfferChannelType prodOfferChannelType) ;
	public List<Map> selectGroupInfoByProductId(Map map) ;
	
	public List<ProductAttrGroup>  selectProductAttrGroup(ProductAttrGroup productAttrGroup);
	public List<Map> selectPricingClassifyByPid(Map map);
	public Integer insertProductAttrGroup(ProductAttrGroup productAttrGroup);
	public void updateProductAttrStatueByGroupId(ProductAttrGroup productAttrGroup);
	public Integer insertProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela);
	public List<ProductAttrGroupRela>  selectProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela);
	public Integer updateProductAttrGroup(ProductAttrGroup productAttrGroup);
	//////////////////////////////////
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel);
	public Integer insertProdOfferRel(ProdOfferRel prodOfferRel);
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel);
	public void delProdOfferRel(ProdOfferRel prodOfferRel);
}
