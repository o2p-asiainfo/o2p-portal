package com.ailk.o2p.portal.dao.pardproduct;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class PardProdDaoImpl implements PardProdDao {
	//private static final int List = 0;
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	@SuppressWarnings("unchecked")
	public List<Map> queryComponentList(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProduct.queryComponentList", map);
	}
	@SuppressWarnings("unchecked")
	public List<Map> showProdList(Map map) {
		List<Map> returnList = sqlMapDao.queryForList(
				"eaap-op2-portal-pardProduct.showProdList", map);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> queryProdCount(Map map) {
		return (List<Map>) sqlMapDao.queryForList(
				"eaap-op2-portal-pardProduct.queryProdCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getOperatorProduct(Map map){
		return sqlMapDao.queryForList("product.getOperatorProduct", map);
	}
	public String getOperatorProductCount(Map map){
		return (String) sqlMapDao.queryForObject("product.getOperatorProductCount", map);
	}

	public Product queryProduct(Product product) {
		return (Product) sqlMapDao.queryForObject("eaap-op2-portal-pardProduct.selectProduct", product);
	}
	public String queryProductSeq() {
		return (String) sqlMapDao.queryForObject("product.selectProductSeq",
				null);
	}
	public String queryServiceSeq() {
		return (String) sqlMapDao.queryForObject("product.selectServiceSeq",
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



	public void insertProduct(Product product) {
		sqlMapDao.insert("product.insertProduct", product);
	}


	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) {
		return (OfferProdRel) sqlMapDao.queryForObject(
				"offerProdRel.selectOfferProdRel", offerProdRel);
	}
	public void updateProdOffer(ProdOffer prodOffer) {
		sqlMapDao.update("prodOffer.updateProdOffer", prodOffer);
	}
	public void updateProduct(Product product) {
		sqlMapDao.update("product.updateProduct", product);
	}
	public void deleteProductAttrValue(Product product){
		sqlMapDao.delete("eaap-op2-portal-pardProduct.deleteProductAttrValue", product);
	}
	public void deleteProductAttr(Product product){
		sqlMapDao.delete("eaap-op2-portal-pardProduct.deleteProductAttr", product);
	}
	public Integer insertProdAttrValue(Map map) {
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardProduct.insertProdAttrValue", map);
	}
    public String queryProductAttrSeq(){
		return (String) sqlMapDao.queryForObject(
				"productAtrr.selectProductAtrrSeq", null);
    }
    public void insertProductAttr(ProductAttr productAttr){
    	sqlMapDao.insert("productAtrr.insertProdAttr", productAttr);
    }
    public List<Map> queryProductAttrInfo(ProductAttr productAttr){
    	return sqlMapDao.queryForList("eaap-op2-portal-pardProduct.queryProductAttrInfo", productAttr);
    }
    public List<Map> queryProdAttrValue(Map map){
       	return sqlMapDao.queryForList("eaap-op2-portal-pardProduct.queryProdAttrValue", map);
    }
    public List<Product> compareProd(Product product){
    	return (List<Product>)sqlMapDao.queryForList("eaap-op2-portal-pardProduct.compareProdId", product);
    }
	    
    public List<Map> queryProductList(Map map){
    	return sqlMapDao.queryForList("eaap-op2-portal-pardProduct.queryProductList", map);

	}
	public Integer countProductList(Map map){
    	return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardProduct.countProductList", map);
	}
	public Product selectProduct(Product product){
		return (Product) sqlMapDao.queryForObject("eaap-op2-portal-pardProduct.selectProduct", product);
	}
	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel) {
		return  sqlMapDao.queryForList("offerProdRel.selectOfferProdRel", offerProdRel);
	}
	public Integer updateOfferProdRel(OfferProdRel offerProdRel){
		return sqlMapDao.update("eaap-op2-portal-offerProdRel.updateOfferProdRel", offerProdRel);
	}

	public Integer offShelfProduct(Map map) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-pardProduct.offShelfProduct", map);
	}
}
