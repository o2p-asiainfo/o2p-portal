package com.ailk.o2p.portal.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.util.SpringContextUtil;
import com.ailk.o2p.portal.cache.CacheFactory;
import com.ailk.o2p.portal.cache.ICache;
import com.ailk.o2p.portal.service.busiDataInfo.IBusiDataInfoServ;
import com.ailk.o2p.portal.utils.PropertiesLoader;
import com.ailk.o2p.portal.utils.SystemKeyWords;
import com.asiainfo.foundation.log.Logger;

public class i18nListener implements ServletContextListener {

	private static final Logger log = Logger.getLog(i18nListener.class);

	private static PropertiesLoader i18nLoader = null;
	@Autowired
	private IBusiDataInfoServ busiDataInfoServ;

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent sc) {
		i18nLoader = new PropertiesLoader("local/local.properties");
		String localResource = i18nLoader.getProperty("spring.i18n.resources");
		String locale = i18nLoader.getProperty("spring.locale");
		String[] localResourceAry = StringUtils.split(localResource, ",");
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		List<String> resourcesPathList = new ArrayList<String>();
		for (String localFolder : localResourceAry) {
			try {
				String pathToUse = resourceLoader.getResource(localFolder)
						.getURL().getPath();
				File dir = new File(pathToUse);
				File[] files = dir.listFiles();
				for (File f : files) {
					if (!f.isDirectory()) {
						String fName = f.getName();
						String location = localFolder + "/" + fName;
						if (location.endsWith("_" + locale + ".properties")) {
							resourcesPathList.add(location);
							log.info(location);
						}
					}
				}
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}
		String[] resourcesPaths = new String[resourcesPathList.size()];
		resourcesPathList.toArray(resourcesPaths);
		i18nLoader = new PropertiesLoader(resourcesPaths);
		ICache cache = CacheFactory
				.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
		cache.set(SystemKeyWords.I18N_LOADER_NAME, i18nLoader);
		i18nLoader = null;
		
//		Map<String,String> cacheMap = new HashMap<String, String>();
//		cache.set("cacheMap", this.cachemapInit(cacheMap));
	}

	
//	private Map<String,String> cachemapInit(Map<String,String> cacheMap){
//		
//		List<BusiDataInfo> busiDataInfoList = this.getBusiDataInfoServ().loadBusiDataInfo();
//		for(BusiDataInfo b:busiDataInfoList){
//			cacheMap.put(b.getBusinessName(), b.getBusinessValue());
//		}
//		return cacheMap;
//	}
	
	public IBusiDataInfoServ getBusiDataInfoServ() {
		if(null == busiDataInfoServ){
			busiDataInfoServ = (IBusiDataInfoServ) SpringContextUtil.getBean("busiDataInfoServ");
		}
		return busiDataInfoServ;
	}

	public void setBusiDataInfoServ(IBusiDataInfoServ busiDataInfoServ) {
		this.busiDataInfoServ = busiDataInfoServ;
	}
}
