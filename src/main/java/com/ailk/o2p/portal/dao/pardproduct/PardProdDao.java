package com.ailk.o2p.portal.dao.pardproduct;


import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;

public interface PardProdDao {
	
	public List<Map> queryComponentList(Map map);
	
	public List<Map> showProdList(Map map);
	public List<Map> queryProdCount(Map map);
	public List<Map> getOperatorProduct(Map map);
	public String getOperatorProductCount(Map map);
	public Product queryProduct(Product product);
	public String queryServiceSeq();
	public String queryProductSeq();
	public String queryProdOfferSeq();
	public String queryOfferProdRelSeq();

	public void insertProduct(Product product);

	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel);
	public void updateProdOffer(ProdOffer prodOffer);
	public void updateProduct(Product product);
	public void deleteProductAttrValue(Product product);
	public void deleteProductAttr(Product product);
	public Integer insertProdAttrValue(Map map);
    public String queryProductAttrSeq();
    public void insertProductAttr(ProductAttr productAttr);
    public List<Map> queryProductAttrInfo(ProductAttr productAttr);
    public List<Map> queryProdAttrValue(Map map);
    public List<Product> compareProd(Product product);

	public List<Map> queryProductList(Map map);
	public Integer countProductList(Map map);
	public Product selectProduct(Product product);
	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel);
	public Integer updateOfferProdRel(OfferProdRel offerProdRel);
	
	public Integer offShelfProduct(Map map);
}
