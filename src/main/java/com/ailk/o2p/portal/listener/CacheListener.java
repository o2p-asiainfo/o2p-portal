package com.ailk.o2p.portal.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.dao.BusiDataInfoDao;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.cache.CacheFactory;
import com.ailk.o2p.portal.cache.ICache;
import com.asiainfo.foundation.log.Logger;



public class CacheListener implements ServletContextListener{

	private static final Logger log = Logger.getLog(CacheListener.class);
	
	@Autowired
	private BusiDataInfoDao  busiDataInfoDao;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ICache cache = CacheFactory
				.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
		Map paramMap=new HashMap();
		List<BusiDataInfo> busiDataInfoList = this.getBusiDataInfoDao().loadBusiDataInfo(paramMap);
		for(BusiDataInfo b:busiDataInfoList){
			cache.set(b.getBusinessName(), b.getBusinessValue());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	
	public BusiDataInfoDao getBusiDataInfoDao() {
		if(busiDataInfoDao == null){
			this.busiDataInfoDao = (BusiDataInfoDao) SpringContextUtil.getBean("busiDataInfoDao");
		}
		return busiDataInfoDao;
	}

	public void setBusiDataInfoDao(BusiDataInfoDao busiDataInfoDao) {
		this.busiDataInfoDao = busiDataInfoDao;
	}
	
	
}
