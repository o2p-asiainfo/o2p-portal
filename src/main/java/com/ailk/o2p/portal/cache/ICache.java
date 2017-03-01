package com.ailk.o2p.portal.cache;

public interface ICache {

	public Object get(String key);

	public void set(String key, Object value);

	public void remove(String key);
}
