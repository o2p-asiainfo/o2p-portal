package com.ailk.o2p.portal.dao.pardoffer;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;

public interface IPardOfferDao {
	public ProdOffer selectProdOffer(ProdOffer prodOffer);
	public List<Map> queryOfferList(Map map);
	public Integer countOfferList(Map map);
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel);
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel);
	public void delOfferProdRel(OfferProdRel offerProdRel);
	public Integer updateOfferProdRel(OfferProdRel offerProdRel);
	public Integer updateProdOffer(ProdOffer prodOffer) ;
	public void deleteprodOfferRel(ProdOfferRel prodOfferRel) ;
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel);
	public Integer compOfferCode(ProdOffer prodOffer);
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel);
	public ProdOfferRel queryProdOfferRel(ProdOfferRel prodOfferRel);
	
	public Integer insertProdOfferRel(ProdOfferRel prodOfferRel);
	public void delProdOfferRel(ProdOfferRel prodOfferRel);
	
	public List<Map> selectProdOfferList(Map map) ;
	public BigDecimal insertProdOffer(ProdOffer prodOffer) ;	
	public Integer insertOfferProdRel(OfferProdRel offerProdRel);
	public List<Channel> getChannelBasicTree(Channel channel);
	public List<Channel> getProdOfferChannel(ProdOfferChannel poChannel);
	public Integer insertProdOfferChannel(ProdOfferChannel poChannel);
	public void deleteProdOfferChannel(ProdOfferChannel poChannel);
	public String queryActivityId(String productOfferId);
	
	public Integer checkOfferCode(ProdOffer prodOffer);
	public Integer querySettleRuleInfo(SettleSpBusiDef settleSpBusiDef);
	public Integer queryPrcingPlanInfo(OfferProdRel offerProdRel);
	
}
