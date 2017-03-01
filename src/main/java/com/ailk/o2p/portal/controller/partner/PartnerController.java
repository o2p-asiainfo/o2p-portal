package com.ailk.o2p.portal.controller.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.ailk.o2p.portal.service.pageCnt.IPageViewCntService;
import com.ailk.o2p.portal.service.partner.IPartnerService;
import com.ailk.o2p.portal.utils.DirectoryByJson;
import com.ailk.o2p.portal.utils.TemplateUtil;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;

@Controller
public class PartnerController extends BaseController{
	@Autowired
	private IPartnerService partnerService;
	
	@Autowired
	private IPageViewCntService pageViewCntService;
	
	HttpServletRequest request;
	@RequestMapping(value = "/org/partnerlist")
	@UnPermission
	public ModelAndView partnerList(HttpServletRequest request){
		
/*		ItemCnt itemCnt = new ItemCnt();
		itemCnt.setItemType("3");
		itemCnt.setItemCode("100000082");
		System.out.println(pageViewCntService.getPageView(itemCnt));*/
		
		String dirId = getRequestValue(request, "dirId");
		if (StringUtils.isEmpty(dirId)) {
			dirId = null;
		}
		
		PageBean pageBean = new PageBean();
		
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("dirId", dirId);
		paraMap.put("sortType", "1");
		paraMap.put("startRow", pageBean.getStartRow());
		paraMap.put("pageSize", pageBean.getPageSize());
		List<Map<String, Object>> partnerList1 = partnerService.queryPartnerList(paraMap);
		paraMap.put("sortType", "2");
		List<Map<String, Object>> partnerList2 = partnerService.queryPartnerList(paraMap);
		Directory dir = new Directory();
		if (StringUtils.isNotEmpty(dirId)) {
			dir.setDirId(Integer.valueOf(dirId));
		}
		List<Directory> orgCategoryList = partnerService.queryAllOrgCategory(dir);
		JSONArray treeMenu = DirectoryByJson.createTreeJson(orgCategoryList);
		pageBean.setActualRowCnt(partnerService.queryPartnerCnt(paraMap));
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("treeMenu",treeMenu);
			mv.addObject("partnerList1",partnerList1);
			mv.addObject("partnerList2",partnerList2);
			mv.addObject("currentPage",pageBean.getCurrentPage()+1);
			mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
			mv.setViewName(tenantId + "/" + template +"/partner/partnerlist.htm"); 
		}else{
			mv.setViewName("/partnerlist.jsp"); 
		}
		return mv;
	}

	
	@RequestMapping(value = "/org/queryPartnerListByCategory",method = RequestMethod.GET)
	@UnPermission
	@ResponseBody
	public ModelAndView queryPartnerListByCategory(HttpServletRequest request){
		String currentPage = getRequestValue(request, "currentPage");
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(currentPage);
		ModelAndView mv = new ModelAndView();
		String dirId = getRequestValue(request, "dirId");
		if (StringUtils.isEmpty(dirId)) {
			dirId = null;
		}
		JSONObject jsonObject = new JSONObject();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("dirId", dirId);
		pageBean.setActualRowCnt(partnerService.queryPartnerCnt(paraMap));
		paraMap.put("sortType", "1");
		paraMap.put("startRow", pageBean.getStartRow());
		paraMap.put("pageSize", pageBean.getPageSize());
		List<Map<String, Object>> partnerList1 = partnerService.queryPartnerList(paraMap);
		paraMap.put("sortType", "2");
		List<Map<String, Object>> partnerList2 = partnerService.queryPartnerList(paraMap);
		jsonObject.put("partnerList1", partnerList1);
		jsonObject.put("partnerList2", partnerList2);
		
		
		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("partnerList1",partnerList1);
			mv.addObject("partnerList2",partnerList2);
			mv.addObject("currentPage",pageBean.getCurrentPage()+1);
			mv.addObject("isTheLast",String.valueOf(pageBean.isTheLast()));
			mv.setViewName(tenantId + "/" + template +"/partner/partnerCategory.htm"); 
		}else{
			mv.setViewName("/partnerlist.jsp"); 
		}
		

		return mv;
		//return jsonObject.toString();
	}
	
/*	@RequestMapping(value = "/org/queryPartnerListByMore",method = RequestMethod.GET,produces="application/json; charset=utf-8")
	@UnPermission
	@ResponseBody
	public String queryPartnerListByMore(HttpServletRequest request){
		String currentPage = getRequestValue(request, "currentPage");
		String dirId = getRequestValue(request, "dirId");
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(currentPage);
		JSONObject jsonObject = new JSONObject();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("dirId", dirId);
		pageBean.setActualRowCnt(partnerService.queryPartnerCnt(paraMap));
		paraMap.put("sortType", "1");
		paraMap.put("startRow", pageBean.getStartRow());
		paraMap.put("pageSize", pageBean.getPageSize());
		List<Map<String, Object>> partnerList1 = partnerService.queryPartnerList(paraMap);
		paraMap.put("sortType", "2");
		List<Map<String, Object>> partnerList2 = partnerService.queryPartnerList(paraMap);
		jsonObject.put("partnerList1", partnerList1);
		jsonObject.put("partnerList2", partnerList2);
		jsonObject.put("currentPage", pageBean.getCurrentPage()+1);
		jsonObject.put("isTheLast", pageBean.isTheLast());
		return jsonObject.toString();
		if(switchEnvironment()){
			if(this.isCms()){
				Integer tenantId = this.getTenantId();
				String template = TemplateUtil.getTenantTemplate(tenantId);
				mv.addObject("TENANTID", tenantId);
				mv.addObject("TEMPLATE",template);
				mv.addObject("partnerList1",partnerList1);
				mv.addObject("currentPage",pageBean.getCurrentPage()+1);
				mv.addObject("isTheLast",pageBean.isTheLast());
				mv.setViewName(tenantId + "/" + template +"/partner/partnerCategory_01.htm"); 
			}else{
				mv.setViewName("/partnerlist.jsp"); 
			}
		}else{
			mv.setViewName("/partnerlist.jsp"); 
		}
		return mv;
	}*/
	
	@RequestMapping(value = "/org/partnerdetail")
	@UnPermission
	public ModelAndView partnerDetail(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String orgId = getRequestValue(request, "orgId");
		Org org = new Org();
		org.setOrgId(Integer.valueOf(orgId));
/*		ItemCnt itemCnt = new ItemCnt();
		itemCnt.setItemType("3");
		itemCnt.setItemCode(orgId);
		pageViewCntService.addPageView(itemCnt);*/
		Map<String, Object> partnerInfo = partnerService.queryPartnerDetail(org);

		if(this.isCms()){
			Integer tenantId = this.getTenantId();
			String template = TemplateUtil.getTenantTemplate(tenantId);
			mv.addObject("TENANTID", tenantId);
			mv.addObject("TEMPLATE",template);
			mv.addObject("partnerInfo",partnerInfo);
		//	mv.addObject("provideApiList",partnerInfo.get("provideApiList"));
			mv.setViewName(tenantId + "/" + template +"/partner/partnerDetail.htm"); 
		}else{
			mv.setViewName("/partnerDetail.jsp"); 
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/org/queryOrgCategory",method = RequestMethod.GET,produces="application/json; charset=utf-8")
	@UnPermission
	@ResponseBody
	public String queryOrgCategory(HttpServletRequest request){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String dirId = getRequestValue(request, "dirId");
		Directory directory = new Directory();
		directory.setDirId(Integer.valueOf(dirId));
		List<Map<String,Object>> list = partnerService.queryOrgCategory(directory);
		return JSONArray.fromObject(list).toString();
	}
	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
	}
}
