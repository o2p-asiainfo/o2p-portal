package com.ailk.o2p.portal.service.pageCnt;

import com.ailk.eaap.op2.bo.ItemCnt;


/**
 * 页面统计访问信息统计
 * @author zjl
 *
 */
public interface IPageViewCntService {
	
	/**
	 * 获取访问数
	 * @param itemType（1：Api；2、Product;3、Cooperation Partner）
	 * @param itemCode 对应ID
	 * @return 
	 */
	public Long getPageView(ItemCnt itemCnt);
	
	/**
	 * 访问量+1
	 * @param itemType（1：Api；2、Product;3、Cooperation Partner）
	 * @param itemCode 对应ID
	 */
	public void addPageView(ItemCnt itemCnt);
	
	/**
	 * 修改数据库访问量
	 * @param itemCnt
	 */
	public void updateDBPageView();
}
