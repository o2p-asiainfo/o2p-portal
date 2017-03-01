package com.ailk.o2p.portal.utils;

import com.ailk.o2p.portal.cache.CacheFactory;
import com.ailk.o2p.portal.cache.ICache;

public final class PropLoaderFactory {

	private static PropertiesLoader loader;

	private PropLoaderFactory() {

	}

	public static PropertiesLoader getPropertiesLoader(String propName) {
		if (loader == null) {
			ICache cache = CacheFactory.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
			loader = (PropertiesLoader) cache.get(propName);
			cache.remove(propName);
		}
		return loader;
	}

}
