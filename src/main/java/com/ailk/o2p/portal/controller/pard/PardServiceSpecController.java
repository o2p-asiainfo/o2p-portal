package com.ailk.o2p.portal.controller.pard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.ServiceSpec;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.pardService.IPardServiceSpecServ;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.ailk.o2p.portal.utils.StringUtils;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.DateUtil;
@Controller
@RequestMapping(value = "/pardServiceSpec")
public class PardServiceSpecController extends BaseController{
	private static final Logger log = Logger.getLog(PardServiceSpecController.class);
	
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private IPardServiceSpecServ pardServiceSpecServ;
	
	@Permission(center="pard",module="pardServiceSpec", privilege="pard")
	@RequestMapping(value = "/serviceSpecIndex")
	public ModelAndView toServiceSpecIndex(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			messages.add("eaap.op2.portal.index.SysName");
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardServiceSpec.service");
			addTranslateMessage(mv, messages);
			
			mv.setViewName("../pardServiceSpec/pardServiceSpecList.jsp");
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/list", produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardProductList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String serviceSpecName = getRequestValue(request, "serviceSpecName");
			String path = request.getContextPath();
			Org orgBean = getOrg(request);
			
			ServiceSpec serviceSpec = new ServiceSpec();
			if(!StringUtils.isEmpty(serviceSpecName)){
				serviceSpec.setServiceName(serviceSpecName);
			}
			
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			serviceSpec.setCooperationType(EAAPConstants.SERVICE_SPEC_TYPE_PRIVATE);
			
			List<ServiceSpec> serviceSpecList = pardServiceSpecServ.qryServiceSpeclistByPer(serviceSpec);
			StringBuilder returnDesc=new StringBuilder();
			String statusCd = null;
			String statusClass = null;
			
			StringBuilder imgStr=null;
			for(ServiceSpec s : serviceSpecList){
				statusCd = s.getStatusCd();
				if("1000".equals(statusCd)){statusClass="category_1";}
				else if("1200".equals(statusCd)){statusClass="category_2";}
				else if("1300".equals(statusCd)){statusClass="category_4";}
				else {statusClass="category_2";}
				imgStr=new StringBuilder();
				if(s.getsFileId()!=null&&!"".equals(s.getsFileId())){
					imgStr.append(path).append("/provider/readImg.shtml?sFileId=").append(s.getsFileId());
				}else{
					imgStr.append(path).append("/resources/img/default.jpg");
				}
				returnDesc.append("<div class=\"col-md-3 col-sm-4 mix ").append(statusClass).append("\" style=\"display:inline-block;\">");
				returnDesc.append("<div class=\"mix-inner\"> <img class=\"img-responsive\" src=\"").append(imgStr.toString()).append("\" alt=\"\"> <a href=\"javascript:showServiceSpecDetail('").append(s.getServiceId()).append("');\" class=\"s-mix-link\">VIEW MORE</a>");
				returnDesc.append("<div class=\"mix-title\"> <strong>").append(s.getServiceName()).append("</strong> <b>").append(DateUtil.convDateToString(s.getCreateDate(), "yyyy-MM-dd")).append("</b> </div>");
				returnDesc.append("</div></div>");
			}
			
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,returnDesc.toString());
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + CommonUtil.getErrMsg(e),null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/serviceSpecView")
	@SavePermission(center="pard",module="serviceSpec",parameterKey="serviceSpecId",privilege="")
	public ModelAndView toServiceSpecView(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			messages.add("eaap.op2.portal.index.SysName");
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardServiceSpec.service");
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardServiceSpec.servName");
			messages.add("eaap.op2.portal.pardServiceSpec.servCode");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			
			//按钮
			messages.add("eaap.op2.portal.pardProd.prodDetail.updateInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardProd.prodAdd.linkDel");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.modify");
			
			messages.add("eaap.op2.portal.pardProdServAttr.servAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.prodAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.selProdAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.viewProdAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.selServAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.viewServAttrVal");
			messages.add("eaap.op2.portal.pardSpec.attrVlaId");
			messages.add("eaap.op2.portal.pardSpec.attrId");
			messages.add("eaap.op2.portal.pardSpec.key");
			messages.add("eaap.op2.portal.pardSpec.showValue");
			messages.add("eaap.op2.portal.pardSpec.attrVlaDesc");
			messages.add("eaap.op2.portal.pardSpec.seqId");
			messages.add("eaap.op2.portal.pardSpec.attrIsDef");
			messages.add("eaap.op2.portal.pardSpec.maintenancec");
			
			messages.add("eaap.op2.portal.pardProd.status");
			messages.add("eaap.op2.portal.pardProd.status-new");
			messages.add("eaap.op2.portal.pardProd.status-pending");
			messages.add("eaap.op2.portal.pardProd.status-passed");
			messages.add("eaap.op2.portal.pardProd.status-notPassed");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			messages.add("eaap.op2.portal.doc.message.edit");
			messages.add("eaap.op2.portal.doc.message.success");
			messages.add("eaap.op2.portal.manager.detail");
			addTranslateMessage(mv, messages);
			
			String serviceSpecId = getRequestValue(request, "serviceSpecId");
			if(null == serviceSpecId || "".equals(serviceSpecId.trim())){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"service--> serviceId is empty!", null);
			}
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceId(new BigDecimal(serviceSpecId));
			serviceSpec = pardServiceSpecServ.qryServiceSpeclistById(serviceSpec);
			
			if(null == serviceSpec){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"service--> quert service by serviceId is empty!", null);
			}
			//初始时允许修改头像
			if("1200".equals(serviceSpec.getStatusCd())){
				mv.addObject("userAble", "Yes");
			}else{
				mv.addObject("userAble", "NO");
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("statusCD", EAAPConstants.PRODUCT_ATTR_ONLINE);
			params.put("serviceId", serviceSpecId);
			
			mv.addObject("serviceSpec", serviceSpec);
			mv.addObject("serviceSpecAttrList", pardServiceSpecServ.qryServiceSpecAttr(params));
			mv.addObject("attrSpecIsCustomizedMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED,mainDataServ.MAININFO_MAP));
			mv.addObject("attrSpecPageInTypeMap",mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP));
			mv.addObject("serviceStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.addObject("specMaintainTypeMap",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE,mainDataServ.MAININFO_MAP));//Maintenance Type SELECT
			mv.setViewName("../pardServiceSpec/pardServiceSpecView.jsp");
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/toServiceSpecAddOrUpdate")
	public ModelAndView toServiceSpecAddOrUpdate(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			messages.add("eaap.op2.portal.index.SysName");
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardServiceSpec.service");
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardServiceSpec.servName");
			messages.add("eaap.op2.portal.pardServiceSpec.servCode");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			messages.add("eaap.op2.portal.pardSpec.maintenancec");
			
			messages.add("eaap.op2.portal.pardProdServAttr.servAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.prodAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.selProdAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.viewProdAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.selServAttrVal");
			messages.add("eaap.op2.portal.pardProdServAttr.viewServAttrVal");
			messages.add("eaap.op2.portal.pardSpec.attrVlaId");
			messages.add("eaap.op2.portal.pardSpec.attrId");
			messages.add("eaap.op2.portal.pardSpec.key");
			messages.add("eaap.op2.portal.pardSpec.showValue");
			messages.add("eaap.op2.portal.pardSpec.attrVlaDesc");
			messages.add("eaap.op2.portal.pardSpec.seqId");
			messages.add("eaap.op2.portal.pardSpec.attrIsDef");
			//按钮
			messages.add("eaap.op2.portal.pardProd.prodDetail.updateInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardProd.prodAdd.linkDel");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.modify");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.manager.auth.save");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			messages.add("eaap.op2.portal.manager.add");
			messages.add("eaap.op2.portal.manager.modify");
			addTranslateMessage(mv, messages);
			
			String serviceSpecId = getRequestValue(request, "serviceSpecId");
			if(null != serviceSpecId && !"".equals(serviceSpecId.trim())){
				ServiceSpec serviceSpec = new ServiceSpec();
				serviceSpec.setServiceId(new BigDecimal(serviceSpecId));
				serviceSpec = pardServiceSpecServ.qryServiceSpeclistById(serviceSpec);
				
				if(null == serviceSpec){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"service--> quert service by serviceId is empty!", null);
				}
				
				mv.addObject("serviceSpec", serviceSpec);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("statusCD", EAAPConstants.PRODUCT_ATTR_ONLINE);
				params.put("serviceId", serviceSpecId);
				mv.addObject("serviceSpecAttrList", pardServiceSpecServ.qryServiceSpecAttr(params));
				mv.addObject("specMaintainTypeMap",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE,mainDataServ.MAININFO_MAP));//Maintenance Type SELECT
			}
			
			mv.addObject("attrSpecIsCustomizedMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED,mainDataServ.MAININFO_MAP));
			mv.addObject("attrSpecPageInTypeMap",mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP));
			mv.addObject("serviceStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.addObject("pardSpecTypeList",mainDataServ.getMainInfo("ATTR_SPEC_TYPE"));//Specification Value Type SELECT
			mv.addObject("pardSpecCustList",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED));//Customized SELECT
			mv.addObject("maintenancecTypeList",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE));//Maintenance Type SELECT
			mv.addObject("crmVersion", WebPropertyUtil.getPropertyValue("crm_version"));
			mv.setViewName("../pardServiceSpec/pardServiceSpec.jsp");
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/serviceSpecificationCodeCheck")
	@ResponseBody
	public String serviceSpecificationCodeCheck(final HttpServletRequest request,
			final HttpServletResponse response)throws Exception {
		JSONObject json = new JSONObject();
		try {
			String serviceSpecId = getRequestValue(request, "serviceSpecId");
			String serviceSpecCode = getRequestValue(request, "serviceSpecCode");
			Org orgBean = getOrg(request);
			ServiceSpec serviceSpec = new ServiceSpec();
			if(!serviceSpecId.equals("")){
				serviceSpec.setServiceId(new BigDecimal(serviceSpecId));
			}
			serviceSpec.setServiceCode(serviceSpecCode);
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			List<ServiceSpec> serviceSpecList = pardServiceSpecServ.serviceSpecificationCodeCheck(serviceSpec);
			if(serviceSpecList.size()>0){
				json.put("isHas", true);
			}else{
				json.put("isHas", false);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	@SavePermission(center="pard",module="serviceSpec",parameterKey="serviceSpecId",privilege="")
	@RequestMapping(value = "/serviceSpecAddOrUpdate", method = RequestMethod.POST)
	public ModelAndView serviceSpecAddOrUpdate(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			addTranslateMessage(mv, messages);
			
			String serviceId = getRequestValue(request, "serviceId");
			String serviceSpecName = getRequestValue(request, "serviceSpecName");
			String serviceSpecCode = getRequestValue(request, "serviceSpecCode");
			String serviceSpecDesc = getRequestValue(request, "serviceSpecDesc");
			String params = getRequestValue(request, "params");
			
			Org orgBean = getOrg(request);
			
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec = checkServiceSpec(serviceSpec,serviceSpecName,serviceSpecCode,serviceSpecDesc);
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			serviceSpec.setStatusCd(EAAPConstants.STATUS_CD_FOR_ADD);
			serviceSpec.setCooperationType(EAAPConstants.SERVICE_SPEC_TYPE_PRIVATE);
			serviceId = pardServiceSpecServ.addOrUpdateServiceSpec(serviceId, serviceSpec, params);
			
			mv.setViewName("redirect:/pardServiceSpec/serviceSpecView.shtml?serviceSpecId="+serviceId);
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@SavePermission(center="pard",module="serviceSpec",parameterKey="serviceId",privilege="")
	@RequestMapping(value = "/delServiceSpec", method = RequestMethod.POST)
	public ModelAndView delServiceSpec(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			String serviceSpecId = getRequestValue(request, "serviceId");
			if(null == serviceSpecId || "".equals(serviceSpecId.trim())){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"service--> serviceId is empty!", null);
			}
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceId(new BigDecimal(serviceSpecId));
			serviceSpec.setStatusCd(EAAPConstants.PRODUCT_ATTR_GIVEUP);
			pardServiceSpecServ.updateServiceSpec(serviceSpec);
			
			mv.setViewName("redirect:/pardServiceSpec/serviceSpecIndex.shtml");
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + CommonUtil.getErrMsg(e),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/updatePic")
	@ResponseBody
	public String updatePic(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String serviceSpecId = getRequestValue(request, "serviceSpecId");
			String sFileId = getRequestValue(request, "sFileId");
			if(null != sFileId && !"".equals(sFileId)){
				ServiceSpec serviceSpec = new ServiceSpec();
				serviceSpec.setServiceId(new BigDecimal(serviceSpecId));
				serviceSpec.setsFileId(sFileId);
				pardServiceSpecServ.updateServiceSpec(serviceSpec);
			}else{
				json.put(RETURN_CODE, RESULT_ERR);
				json.put(RETURN_DESC,"IMG is null");
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	
	private ServiceSpec checkServiceSpec(ServiceSpec serviceSpec,String serviceSpecName,String serviceSpecCode,String serviceSpecDesc){
		if(null==serviceSpecName){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpec's name attribute length is not null.", null);
		}else if(serviceSpecName.length()>50){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpec's name attribute length between 1 and 50.", null);
		}else{
			serviceSpec.setServiceName(serviceSpecName);
		}
		
		if(null==serviceSpecCode){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpec's code attribute length is not null.", null);
		}else if(serviceSpecCode.length()>32){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpec's code attribute length between 1 and 32.", null);
		}else{
			serviceSpec.setServiceCode(serviceSpecCode);
		}
		
		if(serviceSpecDesc.length()>250){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpec's description attribute length between 0 and 250.", null);
		}else{
			serviceSpec.setServiceDesc(serviceSpecDesc);
		}
		return serviceSpec;
	}
}
