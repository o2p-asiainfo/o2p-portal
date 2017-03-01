package com.ailk.o2p.portal.dao.support;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.common.EAAPException;


public interface ISupportDao{
	
	/**
	 * 搜索和展现问题文档
	 * @param type
	 * @param api
	 * @return
	 */
	public List<Map<String, String>> searchProblem(Map paramMap);
	
	/**
	 * 加载目录树结构
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> searchDirList(String dirId,Map paramMap);
	
	/**
	 * 加载二级目录树结构
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> searchDirNexList(String dirId);
	/**
	 * 展示内容
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> showProblemList(String dirId,Map paramMap);
	
	/**
	 * 根据目录名返回目录中文
	 * @param dirID
	 * @return dirName
	 */
	public String showDirName(Map paramMap) throws EAAPException;
	
}
