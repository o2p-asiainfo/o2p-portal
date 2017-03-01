package com.ailk.o2p.portal.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.Directory;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.ailk.o2p.portal.utils.DirectoryByJson;
import com.asiainfo.integration.o2p.web.bo.Org;
@Service
public class PartnerService implements IPartnerService {
	@Autowired
	private IOrgDao orgSqlDAO;
	
	@Override
	public Integer queryPartnerCnt(Map<String, Object> paraMap) {
		//paraMap.put("dirId", getDirId(paraMap));
		return orgSqlDAO.queryPartnerCnt(paraMap);
	}
	@Override
	public List<Map<String, Object>> queryPartnerList(Map<String, Object> paraMap) {
		List<Map<String, Object>> orgList = null;
		//paraMap.put("dirId", getDirId(paraMap));
		orgList = orgSqlDAO.queryPartnerList(paraMap);
		return orgList;
	}

	public String getDirId(Map<String, Object> paraMap) {
		String dirIds = "";
		if (paraMap.get("dirId")!=null && StringUtils.isNotEmpty((String)paraMap.get("dirId"))) {
			List<String> dirIdList = orgSqlDAO.queryDirById(paraMap);
			for (String dirId:dirIdList) {
				dirIds += dirId+",";
			}
		}
		if (StringUtils.isEmpty(dirIds)) {
			dirIds = null;
		} else {
			dirIds = dirIds.substring(0, dirIds.length()-1);
		}
		return dirIds;
	}
	


	@Override
	public Map<String, Object> queryPartnerDetail(Org org) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		List<Map<String, Object>> orgList = orgSqlDAO.queryPartnerInfo(org);
		List<String> orgCategoryList = orgSqlDAO.queryCategoryByOrgId(org);
		StringBuffer orgCategory = new StringBuffer("");
		for (String category:orgCategoryList) {
			if (category != null) {
				orgCategory.append(category+",");
			}
			
		}
		if (orgCategory.length()>0) {
			orgCategory.deleteCharAt(orgCategory.length()-1);
		}
		
		Map<String, Object> map = orgList.get(0);
		paraMap.put("itemCode", org.getOrgId());
		paraMap.put("columnId", "1002");
		map.put("provideApiList",  orgSqlDAO.queryMyAPIOffer(org));
		map.put("provideProdList", orgSqlDAO.queryMyProdOffer(org));
		map.put("consumerApiOfferList", orgSqlDAO.queryConsumerApiOffer(org));
		map.put("resaleProdOfferList", orgSqlDAO.queryResaleProdOffer(org));
		map.put("articleList", orgSqlDAO.queryArticleList(paraMap));
		map.put("orgCateGory", orgCategory.toString());
		return map;
	}

	@Override
	public List<Map<String, Object>> queryOrgCategory(Directory dir) {
		return orgSqlDAO.queryOrgCategory(dir);
	}

	@Override
	public List<Directory> queryAllOrgCategory(Directory dir) {
		List<Directory> resultList = orgSqlDAO.queryAllOrgCategory(dir);
		return resultList;
	}




}



