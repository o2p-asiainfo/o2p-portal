package com.ailk.o2p.portal.service.pardproduct;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;

public interface IPardProdServ {
	public List<Map> queryComponentList(Map map);

	public List<Map> showProdList (Map map);
	public Product queryProduct(Product product) ;
	public String queryProductSeq();
	public String queryProdOfferSeq();
	public String queryOfferProdRelSeq();
    public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) ;
	public void deletePardProdInfo(ProdOffer prodOffer,Product product);
	public void addPardProductInfo(Product product,List<ProductAttr> productAttrList,List<Map> prodAttrValueList);
	public void updatePardProductInfo(Product product,List<ProductAttr> productAttrList,List<Map> prodAttrValueList);
	public List<Map> queryProductAttrInfo(ProductAttr productAttr);
	public String queryProdAttrValue(Map map);
	public void updateProduct(Product product);
    public String queryProductAttrSeq();
    public List<Product> compareProd(Product product);
    
    public String addPardProduct(Product product,String subBusinessCode,String subBusinessName,String componentId,String dependentType,
    		String attrSpecId,String specType,String defaultValues,String serviceId,String systemSelect,String componentIdText,int orgId,String chooseAttrSpecCodeInput)throws Exception;
    public void updatePardProduct(Product product,String subBusinessCode,String subBusinessName,String componentId,String dependentType,
    		String attrSpecId,String specType,String defaultValues,String serviceId,String chooseAttrSpecCodeInput)throws Exception;
    public void doPardProductSubmitCheck(Product product,String orgId,Object switchUserRole)throws Exception;

	//------------------------------------------------------
	public List<Map> queryProductList(Map map);
	public Integer countProductList(Map map);
	public Product selectProduct(Product product);
	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel);
	public Integer updateOfferProdRel(OfferProdRel offerProdRel);
	
	public Integer offShelfProduct(String productId);

	public void lookMsgById(Integer userId,String titleQuery);
	
	public String getCheckMsgByObjectId(String objId);
	
}