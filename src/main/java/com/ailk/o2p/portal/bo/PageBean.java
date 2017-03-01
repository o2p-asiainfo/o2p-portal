package com.ailk.o2p.portal.bo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 分页相关
 * @author zjl
 *
 */
public class PageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageSize = 10;
	private Integer currentPage = 1;
	private Integer actualRowCnt = 0; //实际总纪录数
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public void setCurrentPage(String currentPage) {
		if (StringUtils.isEmpty(currentPage)) {
			this.currentPage = 1;
		} else {
			try {
				this.currentPage = Integer.valueOf(currentPage);
			} catch (Exception e) {
				this.currentPage = 1;
			}
			
		}
		
	}
	
	public Integer getActualRowCnt() {
		return actualRowCnt;
	}
	public void setActualRowCnt(Integer actualRowCnt) {
		this.actualRowCnt = actualRowCnt;
	}
	/**
	 * 获取起始记录，从0开始
	 * @return
	 */
	public Integer getStartRow() {
		if (currentPage == null) {
			return 0;
		}
		return this.pageSize*(this.currentPage-1);
	}
	
	/**
	 * 判断请求是否到最后
	 * @return
	 */
	public boolean isTheLast() {
		if (this.actualRowCnt <= this.currentPage*this.pageSize) {
			return true;
		}
		return false;
	}
}
