package com.ailk.o2p.portal.service.support;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.common.EAAPException;


public interface ISupportService{
	
	/**
	 * 搜索和展现问题文档
	 * @param type
	 * @param api
	 * @return
	 */
	public List<Map<String, String>> searchProblem(String query);

	/**
	 * 加载目录树结构
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> searchDirList(String dirId);
	
	/**
	 * 加载二级目录树结构
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> searchDirNexList(String dirId);
	
	/**
	 * 展示问题内容
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> showProblemList(String dirId);
	
	/**
	 * 根据目录名返回目录中文
	 * @param dirID
	 * @return dirName
	 */
	public String showDirName(String dirId);
	
}
