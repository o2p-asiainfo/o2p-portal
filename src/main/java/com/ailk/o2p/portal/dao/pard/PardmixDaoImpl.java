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
import com.linkage.rainbow.dao.SqlMapDAO;

public class PardmixDaoImpl implements PardmixDao {
	private SqlMapDAO sqlMapDao;
	
	
	public List<Map> selectProdOfferList(Map map){
		if("chooseyys".equals(String.valueOf(map.get("queryFlag")))){
			if("ALLNUM".equals(String.valueOf(map.get("queryType")))){
				return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferCountToyys", map) ;
			}else{
				return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferListToyys", map) ;
			}
		}else{
			if("ALLNUM".equals(String.valueOf(map.get("queryType")))){
				if(map.get("cooperationType")!=null&&!"".equals(map.get("cooperationType").toString()))
					map.put("cooperationType", map.get("cooperationType").toString().split(","));
				return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferCount", map) ;
			}else{
				if("choosepard".equals(String.valueOf(map.get("queryFlag")))){
					return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferListToPard", map) ;
				}else{
					if(map.get("cooperationType")!=null&&!"".equals(map.get("cooperationType").toString()))
						map.put("cooperationType", map.get("cooperationType").toString().split(","));
					return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferList", map) ;
				}
				
			}
		}
		
		
		  
	}
	public Integer deleteProdOfferChannelType(ProdOfferChannelType prodOfferChannelType){
		return (Integer)sqlMapDao.update("eaap-op2-portal-pardMix.deleteProdOfferChannelType", prodOfferChannelType);
	}
	public Integer updateProductAttr(ProductAttr productAttr){
		return (Integer)sqlMapDao.update("eaap-op2-portal-pardMix.updateProductAttr", productAttr) ;
	}
	public List<Map> selectPricingListByOfferId(ProdOffer prodOffer){
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectPricingListByOfferId", prodOffer) ;
    }
	public List<Map> selectAllAttrValueByOfferId(Product product){
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectAllAttrValueByOfferId", product) ;
    }
	
	public List<Map> selectGroupInfoByProductId(Map map){
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectGroupInfoByProductId", map) ;
    }
	
	public List<Map> selectPricingClassifyByPid(Map map){
		if(map.get("pids")!=null&&!"".equals(map.get("pids").toString()))
			map.put("pids", map.get("pids").toString().split(","));
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectPricingClassifyByPid", map) ;
    }
	
	public List<Map> selectProdOffer(Map map){
		 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOffer", map) ;
	 }
	
	public List<ProdOffer> selectProdOffer(ProdOffer prodOffer){
	 	return (ArrayList<ProdOffer>)sqlMapDao.queryForList("eaap-op2-portal-prodOffer.selectProdOffer", prodOffer) ;
    }
	
	public Integer insertProdOffer(ProdOffer prodOffer){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-prodOffer.insertProdOffer", prodOffer) ;
    }
	
	public Integer insertProdOfferChannelType(ProdOfferChannelType prodOfferChannelType){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProdOfferChannelType", prodOfferChannelType) ;
    }
	
	public Integer insertProdAttrValue(ProdAttrValue prodAttrValue){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProdAttrValue", prodAttrValue) ;
    }
	
	
	public Integer insertOfferProdRel(OfferProdRel offerProdRel){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-offerProdRel.insertOfferProdRel", offerProdRel) ;
    }
	public Integer insertProduct(Product product){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProduct", product) ;
    }
	
	public Integer insertProductAttr(ProductAttr productAttr){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProductAttr", productAttr) ;
    }
	
	public Integer updateProdOffer(ProdOffer prodOffer){
		return (Integer)sqlMapDao.update("eaap-op2-portal-pardMix.updateProdOffer", prodOffer) ;
    }
	
	
	
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel){
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-offerProdRel.selectOfferProdRel", offerProdRel) ;
    }
	
	public List<Product>  selectProduct(Product product){
		return (ArrayList<Product>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProduct", product) ;
		
	}
	
	public List<ProdOfferChannelType>  selectProdOfferChannelType(ProdOfferChannelType prodOfferChannelType){
		return (ArrayList<ProdOfferChannelType>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferChannelType", prodOfferChannelType) ;
		
	}
	
	
	
	public List<Map>  selectProductAttr(ProductAttr productAttr){
		return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProductAttrNotInGroup", productAttr) ;
		
	}
	
	public List<Map>  selectProductAttrInGroup(ProductAttr productAttr){
		return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProductAttrInGroup", productAttr) ;
		
	}
	
	
	public List<ProductAttrGroup>  selectProductAttrGroup(ProductAttrGroup productAttrGroup){
		return (ArrayList<ProductAttrGroup>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProductAttrGroup", productAttrGroup) ;
		
	}
	
	public List<ProductAttrGroupRela>  selectProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela){
		return (ArrayList<ProductAttrGroupRela>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProductAttrGroupRela", productAttrGroupRela) ;
		
	}
	
	public Integer insertProductAttrGroup(ProductAttrGroup productAttrGroup){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProductAttrGroup", productAttrGroup) ;
    }
	public Integer updateProductAttrGroup(ProductAttrGroup productAttrGroup){
		return (Integer)sqlMapDao.update("eaap-op2-portal-pardMix.updateProductAttrGroup", productAttrGroup) ;
	}
	
	public void updateProductAttrStatueByGroupId(ProductAttrGroup productAttrGroup){
		 sqlMapDao.update("eaap-op2-portal-pardMix.updateProductAttrStatueByGroupId", productAttrGroup) ;
	}
	
	public Integer insertProductAttrGroupRela(ProductAttrGroupRela productAttrGroupRela){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-pardMix.insertProductAttrGroupRela", productAttrGroupRela) ;
    }
	
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}


	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	/////////////////////////////////////////////////////////////////
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel){
		return sqlMapDao.queryForList("eaap-op2-portal-prodOfferRel.selectProdOfferRel", prodOfferRel);
	}
	public Integer insertProdOfferRel(ProdOfferRel prodOfferRel){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-prodOfferRel.insertProdOfferRel", prodOfferRel);
	}
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel){
		return (Integer) sqlMapDao.update("eaap-op2-portal-prodOfferRel.updateprodOfferRel", prodOfferRel);
	}
	public void delProdOfferRel(ProdOfferRel prodOfferRel){
		sqlMapDao.delete("eaap-op2-portal-prodOfferRel.deleteprodOfferRel", prodOfferRel);
	}
}
