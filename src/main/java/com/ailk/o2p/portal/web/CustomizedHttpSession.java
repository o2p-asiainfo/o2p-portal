package com.ailk.o2p.portal.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.ailk.o2p.portal.cache.CacheFactory;
import com.ailk.o2p.portal.cache.ICache;
import com.ailk.o2p.portal.listener.i18nListener;
import com.ailk.o2p.portal.utils.IdGenerator;
import com.asiainfo.foundation.log.Logger;

public class CustomizedHttpSession implements HttpSession {
	protected ServletContext servletContext;
	protected HttpServletRequest currentRequest;
	protected HttpSession session;
	private Map<String, Object> map;
	private ICache cache;
	private String sid;
	
	private static final Logger log = Logger.getLog(CustomizedHttpSession.class);
	
	public CustomizedHttpSession(String sid, HttpSession session,
			HttpServletRequest currentRequest, ServletContext servletContext) {
		this.session = session;
		this.currentRequest = currentRequest;
		this.servletContext = servletContext;
		this.sid = sid;
		// 初始化缓存对象
		cache = CacheFactory.newCacheInstance("REDIS");
		map = (Map<String, Object>) cache.get(sid);
		if (null == map) {
			map = new HashMap<String, Object>();
			cache.set(sid, map);
		}
	}

	public Object getAttribute(String key) {
		Object obj = map.get(key);
		log.info("--------------[会话id=" + getId() + ",sid=" + sid
				+ "]这里改为从缓存取" + key + "=" + obj);
		return obj;
	}

	public Enumeration getAttributeNames() {
		return this.session.getAttributeNames();
	}

	public long getCreationTime() {
		return session.getCreationTime();
	}

	public String getId() {
		return session.getId();
	}

	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	public Object getValue(String arg0) {
		return session.getValue(arg0);
	}

	public String[] getValueNames() {
		return session.getValueNames();
	}

	public void invalidate() {
		this.session.invalidate();
	}

	public boolean isNew() {
		return session.isNew();
	}

	public void putValue(String arg0, Object arg1) {
		session.putValue(arg0, arg1);
	}

	public void removeAttribute(String arg0) {
		this.session.removeAttribute(arg0);
	}

	public void removeValue(String arg0) {
		session.removeValue(arg0);
	}

	public void setAttribute(String key, Object value) {
		log.info("--------------[会话id=" + getId() + ",sid=" + sid
				+ "]这里改为将会话存入缓存" + key);
		// this.session.setAttribute(arg0, arg1);
		map.put(key, value);
		cache.set(sid, map);
	}

	public void setMaxInactiveInterval(int arg0) {
		session.setMaxInactiveInterval(arg0);
	}

}
