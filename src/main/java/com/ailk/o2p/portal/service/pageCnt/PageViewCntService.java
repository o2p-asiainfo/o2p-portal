package com.ailk.o2p.portal.service.pageCnt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ailk.eaap.o2p.common.cache.Redis;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;

@Component
public class PageViewCntService implements IPageViewCntService {
	private static final Logger log = Logger.getLog(PageViewCntService.class);
	
	//key前缀
	private final static String PAGE_VIEW_PREFIX = "pageView:";
	//key值的Set集合
	private final static String PAGE_VIEW_KEY_SET = "pageViewKeySet";
	
	
	
	@Autowired
	Redis<String,Object> redis;
	
/*	@Autowired
	RedisService<String,Object> redisService;*/
	
	@Autowired
	private IOrgDao orgSqlDAO;
	
	public Long getPageView(ItemCnt itemCnt) {
		ItemCnt localItemCnt = getItemCnt(itemCnt);
		Long pageView = 0l;
		if (localItemCnt == null || localItemCnt.getPageView() == null) {
			pageView = 0l;
		} else {
			pageView = Long.valueOf(localItemCnt.getPageView());
		}
		return pageView;
	}
	
	public ItemCnt getItemCnt(ItemCnt itemCnt) {
		ItemCnt localItemCnt = (ItemCnt) redis.get(PAGE_VIEW_PREFIX+itemCnt.getItemType()+"-"+itemCnt.getItemCode());
		if (localItemCnt == null) {//缓存取为空读数据库
			localItemCnt = orgSqlDAO.queryItemCnt(itemCnt);
		}
		return localItemCnt;
	}

	public void addPageView(ItemCnt itemCnt) {
		ItemCnt localItemCnt = getItemCnt(itemCnt);
		if (localItemCnt == null) {
			localItemCnt = itemCnt;
		} 
		if (localItemCnt.getTenantId()==null) {
			localItemCnt.setTenantId(O2pWebCommonUtil.getDefalutTenantId());
		}
		if (localItemCnt.getPageView()==null) {
			localItemCnt.setPageView(0);
		}
		localItemCnt.setPageView(localItemCnt.getPageView()+1);
		redis.put(PAGE_VIEW_PREFIX+itemCnt.getItemType()+"-"+itemCnt.getItemCode(), localItemCnt);
		//得到key set集合
		@SuppressWarnings("unchecked")
		Set<String> set = (Set<String>) redis.get(PAGE_VIEW_KEY_SET);
		if (set == null) {
			set = new HashSet<String>();
		}
		set.add(PAGE_VIEW_PREFIX+itemCnt.getItemType()+"-"+itemCnt.getItemCode());
		redis.put(PAGE_VIEW_KEY_SET, set);
		
		//redisService.getRedisTemplate().
	}

	public void updateDBPageView(ItemCnt itemCnt) {
		//itemCnt.setTenantId(O2pWebCommonUtil.getDefalutTenantId());	
		ItemCnt itemCnt2 = orgSqlDAO.queryItemCnt(itemCnt); 
		//判断数据库是否已存在记录
		if (itemCnt2==null) {
			orgSqlDAO.insertItemCnt(itemCnt);
		} else {
			orgSqlDAO.updateItemCnt(itemCnt);
		}
	}
	
	@SuppressWarnings("unchecked")
	//@Scheduled(cron="0 0 */1 * * ? ")
	@Scheduled(cron="0 */1 * * * ? ")
	public void updateDBPageView(){
		log.debug("update pageview to DB every 1 hours");
		//取出需要更新数据库的keys集合
		Set<String> keys = (Set<String>) redis.get(PAGE_VIEW_KEY_SET);
		if (keys != null) {
			redis.remove(PAGE_VIEW_KEY_SET);
			for (String key:keys) {
				if (log.isDebugEnabled()) {
					log.debug("visit Page key:"+key);
				}
				ItemCnt itemCnt = (ItemCnt) redis.get(key);
				updateDBPageView(itemCnt);
			}			
		}

	}

}
