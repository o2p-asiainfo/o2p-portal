package com.ailk.o2p.portal.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.o2p.portal.dao.support.ISupportDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

@Service
public class SupportService implements ISupportService {

	private static final Logger log = Logger.getLog(SupportService.class);
	
	@Autowired
	private ISupportDao supportDao;

	public List<Map<String, String>> searchProblem(String query) {
		List<Map<String, String>> queryProblem = null;
		Map paramMap=new HashMap();
		paramMap.put("value",query);
		queryProblem = supportDao.searchProblem(paramMap);
		return queryProblem;
	}

	public ISupportDao getSupportDao() {
		return supportDao;
	}

	public void setSupportDao(ISupportDao supportDao) {
		this.supportDao = supportDao;
	}

	public List<Map<String, String>> searchDirList(String dirId) {
		List<Map<String, String>> searchDirList = null;
		Map paramMap=new HashMap(); 
		searchDirList = supportDao.searchDirList(dirId, paramMap);
		return searchDirList;
	}

	public List<Map<String, String>> searchDirNexList(String dirId) {
		List<Map<String, String>> searchDirNexList = null;
		searchDirNexList = supportDao.searchDirNexList(dirId);
		return searchDirNexList;
	}

	public List<Map<String, String>> showProblemList(String dirId) {
		List<Map<String, String>> showProblemList = null;
		Map paramMap=new HashMap();
		showProblemList = supportDao.showProblemList(dirId,paramMap);
		return showProblemList;
	}

	public String showDirName(String dirId) {
		String dirName = null;
		try {
			Map paramMap=new HashMap();
			paramMap.put("dirId", dirId);
			dirName = supportDao.showDirName(paramMap);
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return dirName;
	}

}
