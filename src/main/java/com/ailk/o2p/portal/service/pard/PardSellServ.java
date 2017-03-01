package com.ailk.o2p.portal.service.pard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ailk.o2p.portal.dao.pard.PardSellDao;

@SuppressWarnings("unchecked")
public class PardSellServ implements IPardSellServ {

	private PardSellDao pardSellDao;
	//private Log log = LogFactory.getLog(PardSellServ.class);

	public List<Map> queryProdSellList(Map map) {
		if ("ALLNUM".equals(map.get("queryType"))) {
			return pardSellDao.queryProdSellNum(map);
		}
		return pardSellDao.queryProdSellList(map);
	}

	public List<Map> queryAddProList(Map map) {
		if ("ALLNUM".equals(map.get("queryType"))) {
			return pardSellDao.queryToAddProdNum(map);
		}
		return pardSellDao.queryToAddProdList(map);
	}

	public List<Map> queryApplyProdInfo(Map map) {
		return pardSellDao.queryApplyInfo(map);
	}

	public void addProductInfo(Map map) {

		Map paramMap = new HashMap();
		paramMap.put("ORG_ID", map.get("ORG_ID"));
		List queryList = pardSellDao.queryChannelId(paramMap);
		if (queryList == null || queryList.size() == 0) {
			paramMap.clear();
			paramMap.put("ORG_ID", map.get("ORG_ID"));
			paramMap.put("CHANNEL_ID", pardSellDao.saveChannelInfo(paramMap)
					.toString());
		} else {// 该合作伙伴无对应的渠道信息，则新增渠道信息
			String channelId = ((HashMap) queryList.get(0)).get("CHANNEL_ID")
					.toString();
			paramMap.put("CHANNEL_ID", channelId);
		}

		String[] applyInfoList = (String[]) map.get("APPLY_PROD_LIST");
		Map prodMap = new HashMap();
		for (String prodOfferId : applyInfoList) {
			prodMap.clear();
			prodMap.put("CHANNEL_ID", paramMap.get("CHANNEL_ID"));
			prodMap.put("PROD_OFFER_ID", prodOfferId);
			prodMap.put("AUDIT_FLOW_ID", map.get("AUDIT_FLOW_ID"));

			prodMap.put("PROD_OFFER_ID", prodOfferId);
			prodMap.put("OFFER_PROVIDER_ID", map.get("OFFER_PROVIDER_ID"));
			prodMap.put("ORG_ID", map.get("ORG_ID"));
			pardSellDao.saveOfferChannel(prodMap);
		}
	}

	public void cancelProduct(Map map) {
		pardSellDao.cancelProduct(map);
	}

	public PardSellDao getPardSellDao() {
		return pardSellDao;
	}

	public void setPardSellDao(PardSellDao pardSellDao) {
		this.pardSellDao = pardSellDao;
	}
}