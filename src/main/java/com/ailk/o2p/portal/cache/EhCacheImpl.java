package com.ailk.o2p.portal.cache;

import org.springframework.stereotype.Component;


@Component
public class EhCacheImpl implements ICache {

	@Override
	public Object get(String key) {
		return EhCacheUtils.get(key);
	}

	@Override
	public void set(String key, Object value) {
		EhCacheUtils.put(key, value);
	}

	@Override
	public void remove(String key) {
		EhCacheUtils.remove(key);
	}



}
