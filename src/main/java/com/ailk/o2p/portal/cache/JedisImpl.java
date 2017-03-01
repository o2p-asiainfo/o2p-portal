package com.ailk.o2p.portal.cache;

import org.springframework.stereotype.Component;

@Component
public class JedisImpl implements ICache {

	//private static final String SYS_CACHE = "sysCache";

	@Override
	public Object get(String key) {
		if (key == null) {
			return null;
		}

		return JedisUtils.getObject(key);
	}

	@Override
	public void set(String key, Object value) {
		JedisUtils.setObject(key, value, 0);
	}

	@Override
	public void remove(String key) {
		JedisUtils.del(key);
	}

}
