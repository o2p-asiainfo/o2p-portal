package com.ailk.o2p.portal.dao.pardoffer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class PardOfferDao implements IPardOfferDao {
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	public ProdOffer selectProdOffer(ProdOffer prodOffer){
		return (ProdOffer) sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.selectProdOffer", prodOffer);
	}
	
	public List<Map> queryOfferList(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-prodOffer.queryOfferList", map);
	}
	public Integer countOfferList(Map map) {
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.countOfferList", map);
	}
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel){
	 	return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-offerProdRel.selectOfferProdRel", offerProdRel) ;
    }
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) {
		return (OfferProdRel) sqlMapDao.queryForObject("eaap-op2-portal-offerProdRel.queryOfferProdRel", offerProdRel);
	}
	
	public Integer updateOfferProdRel(OfferProdRel offerProdRel){
		return sqlMapDao.update("eaap-op2-portal-offerProdRel.updateOfferProdRel", offerProdRel);
	}
	
	public void delOfferProdRel(OfferProdRel offerProdRel){
		sqlMapDao.delete("eaap-op2-portal-offerProdRel.deleteOfferProdRel", offerProdRel);
	}
	public Integer updateProdOffer(ProdOffer prodOffer){
		return sqlMapDao.update("eaap-op2-portal-prodOffer.updateProdOffer", prodOffer);
	}
	public void deleteprodOfferRel(ProdOfferRel prodOfferRel){
		sqlMapDao.delete("eaap-op2-portal-prodOfferRel.deleteprodOfferRel", prodOfferRel);
	}
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel){
		return sqlMapDao.update("eaap-op2-portal-prodOfferRel.updateProdOfferRel", prodOfferRel);
	}
	public Integer compOfferCode(ProdOffer prodOffer){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.compareProdOfferId", prodOffer);
	}
	///
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel){
		return sqlMapDao.queryForList("eaap-op2-portal-prodOfferRel.selectProdOfferRel", prodOfferRel);
	}
	
	@Override
	public ProdOfferRel queryProdOfferRel(ProdOfferRel prodOfferRel) {
		// TODO Auto-generated method stub
		return (ProdOfferRel)sqlMapDao.queryForObject("eaap-op2-portal-prodOfferRel.queryProdOfferRel", prodOfferRel);
	}
	
	
	public Integer insertProdOfferRel(ProdOfferRel prodOfferRel){
		return (Integer) sqlMapDao.insert("eaap-op2-portal-prodOfferRel.insertProdOfferRel", prodOfferRel);
	}
	public void delProdOfferRel(ProdOfferRel prodOfferRel){
		sqlMapDao.delete("eaap-op2-portal-prodOfferRel.deleteprodOfferRel", prodOfferRel);
	}
	public List<Map> selectProdOfferList(Map map){
		if(map.get("cooperationType")!=null&&!"".equals(map.get("cooperationType").toString()))
			map.put("cooperationType", String.valueOf(map.get("cooperationType")).split(","));
		
		if("ALLNUM".equals(String.valueOf(map.get("queryType")))){
			return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferCount", map) ;
		}else{
			return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-pardMix.selectProdOfferList", map) ;
		}

	}
	public BigDecimal insertProdOffer(ProdOffer prodOffer){
		return (BigDecimal)sqlMapDao.insert("eaap-op2-portal-prodOffer.insertProdOffer", prodOffer) ;
    }
	public Integer insertOfferProdRel(OfferProdRel offerProdRel){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-offerProdRel.insertOfferProdRel", offerProdRel) ;
    }

	
	public List<Channel> getChannelBasicTree(Channel channel){
		return sqlMapDao.queryForList("prodOfferChannelType.selectChannel", channel);
	}
	public List<Channel> getProdOfferChannel(ProdOfferChannel poChannel){
		return sqlMapDao.queryForList("prodOfferChannelType.selectProdOfferChannel", poChannel);
	}
	public Integer insertProdOfferChannel(ProdOfferChannel poChannel){
		return (Integer) sqlMapDao.insert("prodOfferChannelType.insertProdOfferChannel", poChannel);
	}
	public void deleteProdOfferChannel(ProdOfferChannel poChannel){
		sqlMapDao.delete("prodOfferChannelType.deleteProdOfferChannel", poChannel);
	}
	public String queryActivityId(String productOfferId){
		return String.valueOf(sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.queryActivityId", productOfferId));
	}
	@Override
	public Integer checkOfferCode(ProdOffer prodOffer) {
		// TODO Auto-generated method stub
		return (Integer)sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.checkOfferCode", prodOffer);
	}
	@Override
	public Integer querySettleRuleInfo(SettleSpBusiDef settleSpBusiDef) {
		// TODO Auto-generated method stub
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.settleRuleInfo", settleSpBusiDef);
	}
	@Override
	public Integer queryPrcingPlanInfo(OfferProdRel offerProdRel) {
		// TODO Auto-generated method stub
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prodOffer.queryPrcingPlanInfo", offerProdRel);
	}

}
