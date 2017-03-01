package com.ailk.o2p.portal.controller.pageCnt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.Directory;
import com.ailk.eaap.op2.bo.ItemCnt;
import com.ailk.o2p.portal.bo.PageBean;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.pageCnt.PageViewCntService;
import com.ailk.o2p.portal.utils.DirectoryByJson;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;

@Controller
public class PageCntController extends BaseController{
	@Autowired
	PageViewCntService pageViewCntService;
	
	@RequestMapping(value = "/page/pageviewcnt",method = RequestMethod.GET)
	@UnPermission
	@ResponseBody
	public String partnerList(HttpServletRequest request){
		Integer tenantId = this.getTenantId();
		String itemCode = getRequestValue(request, "itemCode");
		String itemType = getRequestValue(request, "itemType");
		ItemCnt itemCnt = new ItemCnt();
		itemCnt.setItemCode(itemCode);
		itemCnt.setItemType(itemType);
		itemCnt.setTenantId(tenantId);
		pageViewCntService.addPageView(itemCnt);
		return "success";
	}
	
}
