package com.ailk.o2p.portal.service.pardoffer;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.ailk.o2p.portal.utils.PropertiesLoader;

public interface IPardOfferServ {
	public ProdOffer selectProdOffer(ProdOffer prodOffer);
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel);
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel);
	public void delOfferProdRel(OfferProdRel offerProdRel);
	public Integer insertOfferProdRel(OfferProdRel offerProdRel);
	public Integer updateOfferProdRel(OfferProdRel offerProdRel);
	
	public List<Map> queryProductList(Map map);
	public Integer countProductList(Map map);

	public Integer updateProdOffer(ProdOffer prodOffer) ;
	public void deleteprodOfferRel(ProdOfferRel prodOfferRel);
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel);
	public Integer compOfferCode(ProdOffer prodOffer);
	public String insertProdOfferRel(ProdOfferRel prodOfferRel);
	public void delProdOfferRel(ProdOfferRel prodOfferRel);
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel) ;
	public ProdOfferRel queryProdOfferRel(ProdOfferRel prodOfferRel);
	public BigDecimal addProdOffer(final HttpServletRequest request,
			final HttpServletResponse response,ProdOffer prodOffer,String offerProdStr,String offerStr,String offerMutualExclusionStr,String offerChannelStr,PropertiesLoader i18nLoader,Org orgBean)throws Exception;
	public void updateProdOffer(ProdOffer prodOffer,String itemIds,String offerProdStr,String offerStr, String offerMutualExclusionStr)throws Exception;
	public void deleteProdOffer(ProdOffer prodOffer,OfferProdRel offerProdRel)throws Exception;
	public void doPardOffertSubmitCheck(ProdOffer prodOffer,String orgId,Object switchUserRole)throws Exception;
	public List<Map> selectProdOfferList(Map map) ;
	public BigDecimal insertProdOffer(ProdOffer prodOffer);
	public List<Channel> getChannelBasicTree(Channel channel);
	public List<Channel> getProdOfferChannel(ProdOfferChannel poChannel);
	public Integer insertProdOfferChannel(ProdOfferChannel poChannel);
	public void deleteProdOfferChannel(ProdOfferChannel poChannel);
	public String queryActivityId(String prodOfferId);
	
	public void lookMsgById(Integer userId,String titleQuery);
	
	 public  Org queryOrg(Org paramOrg);
	 public Integer checkOfferCode(ProdOffer prodOffer);
	 public List<Map<String,Object>> queryOrgCountry(Map map);
	 public List<Map<String, Object>> queryOperatorUnderCountry(Org orgBean);
	 public Integer querySettleRuleInfo(SettleSpBusiDef settleSpBusiDef);
	 public Integer queryPrcingPlanInfo(OfferProdRel offerProdRel);
	 
	 public Org getOrgInfoByComponentId(String componentId);
}