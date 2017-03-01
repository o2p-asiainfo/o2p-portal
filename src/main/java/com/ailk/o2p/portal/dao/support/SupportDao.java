package com.ailk.o2p.portal.dao.support;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.common.EAAPErrorCodeDef;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.eaap.op2.common.EAAPTags;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class SupportDao implements ISupportDao {
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchProblem(Map paramMap) {
		List<Map<String, String>> queryProblem = null;
		queryProblem = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.searchProblem", paramMap);
		return queryProblem;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchDirList(String dirId,Map paramMap) {
		Map map = new HashMap();
		Integer tenantId=Integer.parseInt(paramMap.get("tenantId").toString());
		String arrayDirId = String.valueOf(dirId);
		if (null != dirId && !"".equals(arrayDirId)) {
			arrayDirId = arrayDirId.replace("'", "");
			String[] dstCode = arrayDirId.split(",");
			map.put("DirId", dstCode);
		}
		map.put("tenantId",tenantId);
		List<Map<String, String>> searchDirList = null;
		searchDirList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectDirList", map);
		return searchDirList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchDirNexList(String dirId) {
		Map map = new HashMap();
		String arrayDirId = String.valueOf(dirId);
		if (null != dirId && !"".equals(arrayDirId)) {
			arrayDirId = arrayDirId.replace("'", "");
			String[] dstCode = arrayDirId.split(",");
			map.put("DirId", dstCode);
		}
		List<Map<String, String>> searchDirNexList = null;
		searchDirNexList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.selectDirNexList", map);
		return searchDirNexList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> showProblemList(String dirId,Map paramMap) {
		List<Map<String, String>> showProblemList = null;
		if (null == dirId || dirId.equals("")) {
			dirId = EAAPConstants.SUPPORTPROBLEM;
		}
		paramMap.put("dirId", dirId);
		showProblemList = sqlMapDao.queryForList(
				"eaap-op2-portal-doc.showPlatform", paramMap);
		return showProblemList;
	}

	public String showDirName(Map paramMap) throws EAAPException {
		String dirName = null;
		try {
			dirName = (String) sqlMapDao.queryForObject(
					"eaap-op2-portal-doc.showSupportName", paramMap);
		} catch (Exception e) {
			throw new EAAPException(EAAPTags.SEG_PROVAPP_SIGN,
					EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012, new Timestamp(
							System.currentTimeMillis())
							+ "  Get database to list Errors", e);
		}
		return dirName;
	}

}
