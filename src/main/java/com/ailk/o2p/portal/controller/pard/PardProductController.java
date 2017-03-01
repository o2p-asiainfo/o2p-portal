package com.ailk.o2p.portal.controller.pard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.ailk.eaap.op2.bo.CharSpecValue;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.bo.ProductServiceRel;
import com.ailk.eaap.op2.bo.ServiceSpec;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.pardService.IPardServiceSpecServ;
import com.ailk.o2p.portal.service.pardSpec.IPardSpecServ;
import com.ailk.o2p.portal.service.pardproduct.IPardProdServ;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;
@Controller
@RequestMapping(value = "/pardProduct")
public class PardProductController extends BaseController {

	private static final Logger log = Logger.getLog(PardProductController.class);

	@Autowired
	private IPardProdServ pardProdServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private IPardServiceSpecServ pardServiceSpecServ;
	@Autowired
	private IPardSpecServ pardSpecServ;
	

	@Permission(center="pard",module="pardProd", privilege="pard")
	@RequestMapping(value = "/toIndex")
	public ModelAndView toPardProductIndex() {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		messages.add("eaap.op2.portal.index.index");
		messages.add("eaap.op2.portal.index.pradIndex");
		messages.add("eaap.op2.portal.pardProduct.product");
				
		messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("productStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
		mv.setViewName("../pardProduct/pardProductList.jsp");
		if(switchEnvironment()){
			mv.addObject("switchEnvironmentFlag",true);
		}else{
			mv.addObject("switchEnvironmentFlag",false);
		}
		return mv; 
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardProductList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String productName = getRequestValue(request, "productName");
			String path = request.getContextPath();
			Map queryMap = new HashMap();
			queryMap.put("queryType", "");
			if(productName!=null&&!"".equals(productName)){
				queryMap.put("productName", productName);
			}
			Org orgBean = getOrg(request);
			queryMap.put("orgId", orgBean.getOrgId());
			
			List<Map> resultList = pardProdServ.showProdList(queryMap);
			StringBuilder returnDesc = new StringBuilder();
	        String statusCd = null;
	        String statusClass = null;
			for(Map resultMap:resultList){
				statusCd=resultMap.get("STATUS_CD").toString();
				if("1000".equals(statusCd)){statusClass="category_1";}
				else if("1200".equals(statusCd)){statusClass="category_2";}
				else if("1299".equals(statusCd)){statusClass="category_3";}
				else if("1300".equals(statusCd)){statusClass="category_4";}
				else {statusClass="category_2";}
				returnDesc.append("<div class=\"col-md-3 col-sm-4 mix ").append(statusClass).append("\" style=\"display:inline-block;\">");
				returnDesc.append("<div class=\"mix-inner\"> <img class=\"img-responsive\" src=\"").append(path);
				if(resultMap.get("S_FILE_ID")==null){
					returnDesc.append("/resources/img/default.jpg");
				}else{
					returnDesc.append("/fileShare/readImg.shtml?sFileId=").append(resultMap.get("S_FILE_ID"));
				}
				returnDesc.append("\" alt=\"\"> <a href=\"javascript:showProdDetail('").append(resultMap.get("PRODUCT_ID")).append("');\" class=\"s-mix-link\">VIEW MORE</a>");
				returnDesc.append("<div class=\"mix-title\"> <strong>").append(resultMap.get("PRODUCT_NAME")).append("</strong> <b>").append(resultMap.get("CREATE_DATE")).append("</b> </div>");
				returnDesc.append("</div></div>");
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,returnDesc.toString());
		}catch(Exception e){
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/toDetail_noHead")
	public ModelAndView toPardProductDetail_NoHead(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardProduct.product");
			messages.add("eaap.op2.portal.pardProd.detail");
			//基础信息
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");//Product Name 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");//	Product Code 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardporduct.componentId");//System Name
			//-- Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessId");//Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessCode");
			messages.add("eaap.op2.portal.pardporduct.subBusinessName");
			//-- Product Attribute
			messages.add("eaap.op2.portal.pardProd.prodAdd.define");//Product Attribute
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			
			messages.add("eaap.op2.portal.pardProdServAttr.checkMsg");
			messages.add("eaap.op2.portal.pardProdServAttr.servModule");
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
			//审核状态
			messages.add("eaap.op2.portal.pardpord.addPardPord");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardpord.online");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			//按钮
			messages.add("eaap.op2.portal.pardProd.prodDetail.updateInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardProd.prodAdd.linkDel");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.modify");
			messages.add("eaap.op2.portal.pardProdServAttr.close");
			messages.add("eaap.op2.portal.portalMessage.history");

			messages.add("eaap.op2.portal.pardProd.status");
			messages.add("eaap.op2.portal.pardProd.status-new");
			messages.add("eaap.op2.portal.pardProd.status-pending");
			messages.add("eaap.op2.portal.pardProd.status-passed");
			messages.add("eaap.op2.portal.pardProd.status-notPassed");
			messages.add("eaap.op2.portal.pardProdServAttr.dependentType");

			addTranslateMessage(mv, messages);
			
			//  获取产品基本信息
			String productId = getRequestValue(request, "product.productId");
			Product product = new Product();
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product = pardProdServ.queryProduct(product);
			String btime = product.getEffDate() == null ? product.getNowDate(0) : DateUtil
					.convDateToString(product.getEffDate(), "yyyy-MM-dd");
			String etime = product.getExpDate() == null ? "2037-12-31" : DateUtil
					.convDateToString(product.getExpDate(), "yyyy-MM-dd");
			product.setFormatEffDate(btime);
			product.setFormatExpDate(etime);
			//获取销售品 产品受理服务
			// 1 销售品与产品关联，取到productid（OFFER_PROD_REL）
			//产品属性
			ProductAttr productAttr = new ProductAttr();
//			AttrSpec attrSpec = new AttrSpec();
			productAttr.setProductId(product.getProductId());
			productAttr.setDisplayType("21");
			List<Map> productAtrrAll= pardProdServ.queryProductAttrInfo(productAttr);
			//根据规格功能拆分
			Map tempMap=null;
			List<HashMap> subBusinessCodes = new ArrayList<HashMap>();
			List<Map> prodAttrList = new ArrayList<Map>();

			//获取组件信息
			Map orgMap = new HashMap();
			Org orgBean = getOrg(request);
			orgMap.put("orgId", orgBean.getOrgId());
			List<Map> componentList = pardProdServ.queryComponentList(orgMap);
			Map UpcMap = new HashMap();
			UpcMap.put("COMPONENT_ID", "1205");
			UpcMap.put("NAME", "Veris UPC");
			componentList.add(UpcMap);
			
			for(int i=0;i<productAtrrAll.size();i++){
				tempMap=productAtrrAll.get(i);
				if(EAAPConstants.SUB_BUSINESS.equals(tempMap.get("ATTR_SPEC_ID").toString())){//子业务编码
					String defaultValue = (String)tempMap.get("DEFAULT_VALUE");
					String[] defaultValues = defaultValue.split(","); 
					if(defaultValues.length == 2){
						HashMap<String,String> subBusinessMap = new HashMap<String,String>();
						subBusinessMap.put("SUB_BUSINESS_CODE", defaultValues[0]);
						subBusinessMap.put("SUB_BUSINESS_NAME", defaultValues[1]);
						subBusinessCodes.add(subBusinessMap);
					}
				}else if(EAAPConstants.SERVICE_PROVIDER.equals(tempMap.get("ATTR_SPEC_ID").toString())){//第三方系统编码 即组件
					mv.addObject("componentId",tempMap.get("DEFAULT_VALUE"));
				}else if(EAAPConstants.DEPENDENT_TYPE.equals(tempMap.get("ATTR_SPEC_ID").toString())){//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
					mv.addObject("dependentType",tempMap.get("DEFAULT_VALUE"));
				}else{
					prodAttrList.add(tempMap);
				}
			}
				
			mv.addObject("product",product);
			mv.addObject("componentList",componentList);
			mv.addObject("subBusinessCodes",subBusinessCodes);
			mv.addObject("prodAttrList",prodAttrList);
			//加载服务列表
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			mv.addObject("serviceList",pardServiceSpecServ.qryServiceSpeclist(serviceSpec));
			ProductServiceRel productServiceRel = pardServiceSpecServ.getProductServiceRel(productId);
			mv.addObject("serviceId",null==productServiceRel?null:productServiceRel.getServiceId());
			
			//Dependent Type：
			List<CharSpecValue> dependentTypeList=null;
			CharSpecValue charSpecValueTemp=null;
			charSpecValueTemp=new CharSpecValue();
			charSpecValueTemp.setCharSpecId(EAAPConstants.DEPENDENT_TYPE);		//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
			dependentTypeList=pardSpecServ.qryCharSpecValue(charSpecValueTemp);
			mv.addObject("dependentTypeList",dependentTypeList);
			String isShowDependentType = WebPropertyUtil.getPropertyValue("IS_SHOW_DEPENDENT_TYPE");  //从公共配置文件eaap_common.properties中读取配置判断是否显示依赖关系
			mv.addObject("isShowDependentType", isShowDependentType);
			
			//枚举字段转名字
			mv.addObject("attrSpecIsCustomizedMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED,mainDataServ.MAININFO_MAP));
			mv.addObject("attrSpecPageInTypeMap",mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP));
			mv.addObject("productStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.setViewName("../pardProduct/pardProductView_noHead.jsp");
			
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}

	@RequestMapping(value = "/toDetail")
	public ModelAndView toPardProductDetail(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardProduct.product");
			messages.add("eaap.op2.portal.pardProd.detail");
			//基础信息
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");//Product Name 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");//	Product Code 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardporduct.componentId");//System Name
			//-- Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessId");//Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessCode");
			messages.add("eaap.op2.portal.pardporduct.subBusinessName");
			//-- Product Attribute
			messages.add("eaap.op2.portal.pardProd.prodAdd.define");//Product Attribute
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			
			messages.add("eaap.op2.portal.pardProdServAttr.checkMsg");
			messages.add("eaap.op2.portal.pardProdServAttr.servModule");
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
			//审核状态
			messages.add("eaap.op2.portal.pardpord.addPardPord");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardpord.online");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			//按钮
			messages.add("eaap.op2.portal.pardProd.prodDetail.updateInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardProd.prodAdd.linkDel");
			messages.add("eaap.op2.portal.devmgr.developTest");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.price.modify");
			messages.add("eaap.op2.portal.pardProdServAttr.close");
			messages.add("eaap.op2.portal.portalMessage.history");

			messages.add("eaap.op2.portal.pardProd.status");
			messages.add("eaap.op2.portal.pardProd.status-new");
			messages.add("eaap.op2.portal.pardProd.status-pending");
			messages.add("eaap.op2.portal.pardProd.status-passed");
			messages.add("eaap.op2.portal.pardProd.status-notPassed");
			messages.add("eaap.op2.portal.pardProdServAttr.dependentType");

			addTranslateMessage(mv, messages);
			
			//  获取产品基本信息
			String productId = getRequestValue(request, "product.productId");
			Product product = new Product();
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product = pardProdServ.queryProduct(product);

			String btime = product.getEffDate() == null ? product.getNowDate(0) : DateUtil
					.convDateToString(product.getEffDate(), "yyyy-MM-dd");
			String etime = product.getExpDate() == null ? "2037-12-31" : DateUtil
					.convDateToString(product.getExpDate(), "yyyy-MM-dd");
			product.setFormatEffDate(btime);
			product.setFormatExpDate(etime);
			//获取销售品 产品受理服务
			// 1 销售品与产品关联，取到productid（OFFER_PROD_REL）
			//产品属性
			ProductAttr productAttr = new ProductAttr();
//			AttrSpec attrSpec = new AttrSpec();
			productAttr.setProductId(product.getProductId());
			productAttr.setDisplayType("21");
			List<Map> productAtrrAll= pardProdServ.queryProductAttrInfo(productAttr);
			//根据规格功能拆分
			Map tempMap=null;
			List<HashMap> subBusinessCodes = new ArrayList<HashMap>();
			List<Map> prodAttrList = new ArrayList<Map>();

			//获取组件信息
			Map orgMap = new HashMap();
			Org orgBean = getOrg(request);
			orgMap.put("orgId", orgBean.getOrgId());
			List<Map> componentList = pardProdServ.queryComponentList(orgMap);
			Map UpcMap = new HashMap();
			UpcMap.put("COMPONENT_ID", "1205");
			UpcMap.put("NAME", "Veris UPC");
			componentList.add(UpcMap);
			
			for(int i=0;i<productAtrrAll.size();i++){
				tempMap=productAtrrAll.get(i);
				if(EAAPConstants.SUB_BUSINESS.equals(tempMap.get("ATTR_SPEC_ID").toString())){//子业务编码
					String defaultValue = (String)tempMap.get("DEFAULT_VALUE");
					String[] defaultValues = defaultValue.split(","); 
					if(defaultValues.length == 2){
						HashMap<String,String> subBusinessMap = new HashMap<String,String>();
						subBusinessMap.put("SUB_BUSINESS_CODE", defaultValues[0]);
						subBusinessMap.put("SUB_BUSINESS_NAME", defaultValues[1]);
						subBusinessCodes.add(subBusinessMap);
					}
				}else if(EAAPConstants.SERVICE_PROVIDER.equals(tempMap.get("ATTR_SPEC_ID").toString())){//第三方系统编码 即组件
					mv.addObject("componentId",tempMap.get("DEFAULT_VALUE"));
				}else if(EAAPConstants.DEPENDENT_TYPE.equals(tempMap.get("ATTR_SPEC_ID").toString())){//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
					mv.addObject("dependentType",tempMap.get("DEFAULT_VALUE"));
				}else{
					prodAttrList.add(tempMap);
				}
			}
				
			mv.addObject("product",product);
			mv.addObject("componentList",componentList);
			mv.addObject("subBusinessCodes",subBusinessCodes);
			mv.addObject("prodAttrList",prodAttrList);
			//加载服务列表
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			mv.addObject("serviceList",pardServiceSpecServ.qryServiceSpeclist(serviceSpec));
			ProductServiceRel productServiceRel = pardServiceSpecServ.getProductServiceRel(productId);
			mv.addObject("serviceId",null==productServiceRel?null:productServiceRel.getServiceId());
			mv.addObject("checkMsg",pardProdServ.getCheckMsgByObjectId(productId));
			
			//Dependent Type：
			List<CharSpecValue> dependentTypeList=null;
			CharSpecValue charSpecValueTemp=null;
			charSpecValueTemp=new CharSpecValue();
			charSpecValueTemp.setCharSpecId(EAAPConstants.DEPENDENT_TYPE);		//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
			dependentTypeList=pardSpecServ.qryCharSpecValue(charSpecValueTemp);
			mv.addObject("dependentTypeList",dependentTypeList);
			String isShowDependentType = WebPropertyUtil.getPropertyValue("IS_SHOW_DEPENDENT_TYPE");  //从公共配置文件eaap_common.properties中读取配置判断是否显示依赖关系
			mv.addObject("isShowDependentType", isShowDependentType);
			
			//枚举字段转名字
			mv.addObject("attrSpecIsCustomizedMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED,mainDataServ.MAININFO_MAP));
			mv.addObject("attrSpecPageInTypeMap",mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP));
			mv.addObject("productStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.setViewName("../pardProduct/pardProductView.jsp");
			
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@Permission(center="pard",module="pardprod", privilege="audit")
	@RequestMapping(value = "/toAdd")
	public ModelAndView toPardProductAdd(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardProduct.product");
			messages.add("eaap.op2.portal.pardProd.detail");
			messages.add("eaap.op2.portal.pardProd.prodAdd.location");
			//基础信息
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");//Product Name 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");//	Product Code 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardporduct.componentId");//System Name
			messages.add("eaap.op2.portal.pardProd.component");//System Name Label
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodSystemSelect");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodSystemSelectExisted");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodSystemSelectNew");
			//-- Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessId");//Sub Business
			messages.add("eaap.op2.portal.pardProd.sub.bussiness");//Sub Business Label
			messages.add("eaap.op2.portal.pardporduct.subBusinessCode");
			messages.add("eaap.op2.portal.pardporduct.subBusinessName");
			
			messages.add("eaap.op2.portal.pardProdServAttr.checkMsg");
			messages.add("eaap.op2.portal.pardProdServAttr.servModule");
			messages.add("eaap.op2.portal.pardProdServAttr.servAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.prodAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.servAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.prodAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.servModule");
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
			//-- Product Attribute
			messages.add("eaap.op2.portal.pardProd.prodAdd.define");//Product Attribute
			messages.add("eaap.op2.portal.pardProd.product.attrubute");//Product Attribute Label
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			messages.add("eaap.op2.portal.pardSpec.maintenancec");//Name
			//审核状态
			
			//按钮
			messages.add("eaap.op2.portal.pardProd.prodDetail.updateInfo");
			messages.add("eaap.op2.portal.devmgr.submitToCheck");
			messages.add("eaap.op2.portal.pardProd.prodAdd.linkDel");
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			messages.add("eaap.op2.portal.devmgr.query");
			messages.add("eaap.op2.portal.pardProdServAttr.close");
			messages.add("eaap.op2.portal.pardProdServAttr.dependentType");
			
			addTranslateMessage(mv, messages);
			
			//获取组件下拉框值
			Org orgBean = getOrg(request);
			Map orgMap = new HashMap();
			orgMap.put("orgId", orgBean.getOrgId());

			List<Map> componentList = pardProdServ.queryComponentList(orgMap);
			mv.addObject("componentList",componentList);//System Name List

			//Dependent Type：
			List<CharSpecValue> dependentTypeList=null;
			CharSpecValue charSpecValue=null;
			charSpecValue=new CharSpecValue();
			charSpecValue.setCharSpecId(EAAPConstants.DEPENDENT_TYPE);		//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
			dependentTypeList=pardSpecServ.qryCharSpecValue(charSpecValue);
			mv.addObject("dependentTypeList",dependentTypeList);
			String isShowDependentType = WebPropertyUtil.getPropertyValue("IS_SHOW_DEPENDENT_TYPE");  //从公共配置文件eaap_common.properties中读取配置判断是否显示依赖关系
			mv.addObject("isShowDependentType", isShowDependentType);
			
			mv.addObject("pageFlag","add");
			//加载服务列表
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			mv.addObject("serviceList",pardServiceSpecServ.qryServiceSpeclist(serviceSpec));
			//枚举字段转名字
			mv.addObject("productStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.addObject("pardSpecTypeList",mainDataServ.getMainInfo("ATTR_SPEC_TYPE"));//Specification Value Type SELECT
			mv.addObject("pardSpecCustList",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED));//Customized SELECT
			mv.addObject("maintenancecTypeList",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE));//Maintenance Type SELECT
			mv.addObject("crmVersion", WebPropertyUtil.getPropertyValue("crm_version"));
			mv.setViewName("../pardProduct/pardProduct.jsp");
			
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/serviceSpecAttrList",produces="application/json; charset=utf-8")
	@ResponseBody
	public String serviceSpecList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
		    String serviceId = getRequestValue(request, "serviceId");
		    
		    if(null==serviceId){
		    	throw new BusinessException(ExceptionCommon.WebExceptionCode,"serviceSpecAttrList --> serviceId is empty! init the table.", null);
		    }
		    
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("statusCD", EAAPConstants.PRODUCT_ATTR_ONLINE);
			params.put("serviceId", serviceId);
			List<Map<String, Object>> serviceSpecAttrList = pardServiceSpecServ.qryServiceSpecAttr(params);
			Map pardSpecTypeMap = (Map) mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP);
			JSONArray dataList = new JSONArray();
			JSONObject subData = null;
			for(Map<String,Object> erviceSpecAttrMap:serviceSpecAttrList){
				subData=new JSONObject();
				subData.put("SERV_ATTR_VALUE_ID",erviceSpecAttrMap.get("SERVICESPECATTRID"));
				subData.put("ATTR_SPEC_ID",erviceSpecAttrMap.get("CHAR_SPEC_ID"));
				subData.put("ATTR_SPEC_CODE",erviceSpecAttrMap.get("CODE"));
				subData.put("ATTR_SPEC_NAME",erviceSpecAttrMap.get("CHAR_SPEC_NAME"));
				subData.put("ATTR_SPEC_DESC",null==erviceSpecAttrMap.get("DESCRIPTION")?"":erviceSpecAttrMap.get("DESCRIPTION"));
				subData.put("PAGE_IN_TYPE",erviceSpecAttrMap.get("VALUE_TYPE"));
				subData.put("PAGE_IN_TYPE_FORMAT",pardSpecTypeMap.get(String.valueOf(erviceSpecAttrMap.get("VALUE_TYPE"))));
				subData.put("IS_CUSTOMIZED",null==erviceSpecAttrMap.get("IS_CUSTOMIZED")?"":erviceSpecAttrMap.get("IS_CUSTOMIZED"));
				subData.put("VALUE_FROM",null==erviceSpecAttrMap.get("VALUE_FROM")?"":erviceSpecAttrMap.get("VALUE_FROM"));
				subData.put("VALUE_TO",null==erviceSpecAttrMap.get("VALUE_TO")?"":erviceSpecAttrMap.get("VALUE_TO"));
				subData.put("IS_CUSTOMIZED_FORMAT",getIsCustomized(String.valueOf(erviceSpecAttrMap.get("IS_CUSTOMIZED"))));
				subData.put("DEFAULT_VALUE",null==erviceSpecAttrMap.get("DEFAULT_VALUE")?"":erviceSpecAttrMap.get("DEFAULT_VALUE"));
				subData.put("MIN_CARDINALITY", null==erviceSpecAttrMap.get("MIN_CARDINALITY")?"":erviceSpecAttrMap.get("MIN_CARDINALITY"));
				subData.put("MAX_CARDINALITY", null==erviceSpecAttrMap.get("MAX_CARDINALITY")?"":erviceSpecAttrMap.get("MAX_CARDINALITY"));
				
				dataList.add(subData);
			}
			
			json.put(RETURN_CODE,RESULT_OK);
			json.put(RETURN_DESC,dataList.toJSONString());
		}catch(Exception e){
			json.put(RETURN_CODE,RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	private String getPageInType(String value){
		String reValue =null;
		if("1".equals(value)){
			reValue="eaap.op2.portal.pardSpec.type1";
	    }else if("2".equals(value)){
			reValue="eaap.op2.portal.pardSpec.type2";
	    }else if("3".equals(value)){
			reValue="eaap.op2.portal.pardSpec.type3";
	    }else if("4".equals(value)){
			reValue="eaap.op2.portal.pardSpec.type4";
	    }
		if(reValue!=null) return getI18nMessage(reValue);
		else return value;
	}
	
	private String getIsCustomized(String value){
		String reValue =null;
		if("Y".equals(value)){
			reValue="eaap.op2.portal.pardSpec.yes";
	    }else if("N".equals(value)){
			reValue="eaap.op2.portal.pardSpec.no";
	    }
		if(reValue!=null) return getI18nMessage(reValue);
		else return value;
	}
	
	@SavePermission(center="pard",module="product",parameterKey="productId",privilege="")
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toPardProductUpdate(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		try{
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.index.SysName");
			//导航
			messages.add("eaap.op2.portal.index.index");
			messages.add("eaap.op2.portal.index.pradIndex");
			messages.add("eaap.op2.portal.pardProduct.product");
			messages.add("eaap.op2.portal.pardProd.detail");
			messages.add("eaap.op2.portal.pardProd.prodAdd.location");
			//基础信息
			messages.add("eaap.op2.portal.pardProd.prodDetail.basicInfo");
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");//Product Name 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodCode");//	Product Code 
			messages.add("eaap.op2.portal.pardProd.prodDetail.prodDesc");//Description
			messages.add("eaap.op2.portal.pardporduct.componentId");//System Name
			messages.add("eaap.op2.portal.pardProd.component");//System Name Label
			//-- Sub Business
			messages.add("eaap.op2.portal.pardporduct.subBusinessId");//Sub Business
			messages.add("eaap.op2.portal.pardProd.sub.bussiness");//Sub Business Label
			messages.add("eaap.op2.portal.pardporduct.subBusinessCode");
			messages.add("eaap.op2.portal.pardporduct.subBusinessName");
			//-- Product Attribute
			messages.add("eaap.op2.portal.pardProd.prodAdd.define");//Product Attribute
			messages.add("eaap.op2.portal.pardProd.product.attrubute");//Product Attribute Label
			messages.add("eaap.op2.portal.pardSpec.customer");
			messages.add("eaap.op2.portal.pardSpec.id");
			messages.add("eaap.op2.portal.pardSpec.code");
			messages.add("eaap.op2.portal.pardSpec.name");
			messages.add("eaap.op2.portal.pardSpec.note");
			messages.add("eaap.op2.portal.pardSpec.type");
			messages.add("eaap.op2.portal.pardSpec.default");
			messages.add("eaap.op2.portal.pardSpec.maintenancec");//Name
			//审核状态
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
			messages.add("eaap.op2.portal.devmgr.cancel");
			messages.add("eaap.op2.portal.pardProd.prodAdd.submit");
			messages.add("eaap.op2.portal.devmgr.query");
			
			messages.add("eaap.op2.portal.pardProdServAttr.servAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.prodAttr");
			messages.add("eaap.op2.portal.pardProdServAttr.servModule");
			messages.add("eaap.op2.portal.pardProdServAttr.dependentType");
			
			addTranslateMessage(mv, messages);
			
			//获取组件下拉框值
			Org orgBean = getOrg(request);
			HashMap orgMap = new HashMap();
			orgMap.put("orgId", orgBean.getOrgId());
			List<Map>  componentList = pardProdServ.queryComponentList(orgMap);
			
			//  获取产品基本信息
			String productId = getRequestValue(request, "productId");
			String msgId = getRequestValue(request, "message.msgId");
			Product product=new Product();
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product = pardProdServ.queryProduct(product);
			String btime = product.getEffDate() == null ? "" : DateUtil
					.convDateToString(product.getEffDate(), "yyyy-MM-dd");
			String etime = product.getExpDate() == null ? "" : DateUtil
					.convDateToString(product.getExpDate(), "yyyy-MM-dd");
			product.setFormatEffDate(btime);
			product.setFormatExpDate(etime);

			//产品属性
			ProductAttr productAttr = new ProductAttr();
			productAttr.setProductId(product.getProductId());
			productAttr.setDisplayType("21");
			List<Map> productAtrrAll = pardProdServ.queryProductAttrInfo(productAttr);
			//枚举字段转名字
			//根据规格功能拆分
			Map<String,Object> tempMap = null;
			List<Map> subBusinessCodes = new ArrayList<Map>();
			List<Map> prodAttrList = new ArrayList<Map>();
			CharSpecValue charSpecValue=null;
			List<CharSpecValue> charSpecValueList=null;
			for(int i=0;i<productAtrrAll.size();i++){
				tempMap = productAtrrAll.get(i);
				if(EAAPConstants.SUB_BUSINESS.equals(tempMap.get("ATTR_SPEC_ID").toString())){//子业务编码
					String defaultValue = (String)tempMap.get("DEFAULT_VALUE");
					String[] defaultValues = defaultValue.split(","); 
					Map<String,String> subBusinessMap = new HashMap<String,String>();
					subBusinessMap.put("SUB_BUSINESS_CODE", defaultValues[0]);
					subBusinessMap.put("SUB_BUSINESS_NAME", defaultValues[1]);
					subBusinessCodes.add(subBusinessMap);		
				}else if(EAAPConstants.SERVICE_PROVIDER.equals(tempMap.get("ATTR_SPEC_ID").toString())){//第三方系统编码 即组件
					mv.addObject("componentId",tempMap.get("DEFAULT_VALUE"));
				}else if(EAAPConstants.DEPENDENT_TYPE.equals(tempMap.get("ATTR_SPEC_ID").toString())){//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
					mv.addObject("dependentType",tempMap.get("DEFAULT_VALUE"));
				}else{
					//回填枚举的可选参数
					if(tempMap.get("VALUE_TYPE")!=null && "4".equals(tempMap.get("VALUE_TYPE").toString())){
						String pardAttrValue = pardProdServ.queryProdAttrValue(tempMap);
						if(pardAttrValue!=null && !"".equals(pardAttrValue)){
							tempMap.put("DEFAULT_VALUE", pardAttrValue);
						}
					}else if(tempMap.get("VALUE_TYPE")!=null && "3".equals(tempMap.get("VALUE_TYPE").toString())){
						charSpecValue=new CharSpecValue();
						charSpecValue.setCharSpecId(tempMap.get("ATTR_SPEC_ID").toString());
						charSpecValueList=pardSpecServ.qryCharSpecValue(charSpecValue);
						if(charSpecValueList!=null&&charSpecValueList.size()>0){
							tempMap.put("VALUE_FROM", charSpecValueList.get(0).getValueFrom());
							tempMap.put("VALUE_TO", charSpecValueList.get(0).getValueTo());
						}
					}
					prodAttrList.add(tempMap);
				}
			}
			mv.addObject("pageFlag","update");	
			mv.addObject("product",product);
			mv.addObject("message.msgId",msgId);
			mv.addObject("componentList",componentList);//System Name List
			mv.addObject("subBusinessCodes",subBusinessCodes);//Sub Business List
			mv.addObject("prodAttrList",prodAttrList);
			//加载服务列表
			ServiceSpec serviceSpec = new ServiceSpec();
			serviceSpec.setServiceProviderId(orgBean.getOrgId());
			mv.addObject("serviceList",pardServiceSpecServ.qryServiceSpeclist(serviceSpec));
			mv.addObject("serviceId",pardServiceSpecServ.getProductServiceRel(productId).getServiceId());
			
			//消息已读
			if("1298".equals(product.getStatusCd())||"1278".equals(product.getStatusCd())){
				pardProdServ.lookMsgById(orgBean.getOrgId(),EAAPConstants.WORK_FLOW_MESSAGE_QUERY_PRODUCT.replace("#id", String.valueOf(product.getProductId())));
			}

			//Dependent Type：
			List<CharSpecValue> dependentTypeList=null;
			CharSpecValue charSpecValueTemp=null;
			charSpecValueTemp=new CharSpecValue();
			charSpecValueTemp.setCharSpecId(EAAPConstants.DEPENDENT_TYPE);		//用于标识是“基础1”还是“依赖0”产品，空代表普通产品
			dependentTypeList=pardSpecServ.qryCharSpecValue(charSpecValueTemp);
			mv.addObject("dependentTypeList",dependentTypeList);
			String isShowDependentType = WebPropertyUtil.getPropertyValue("IS_SHOW_DEPENDENT_TYPE");  //从公共配置文件eaap_common.properties中读取配置判断是否显示依赖关系
			mv.addObject("isShowDependentType", isShowDependentType);
			
			//枚举字段转名字
			mv.addObject("attrSpecIsCustomizedMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED,mainDataServ.MAININFO_MAP));
			mv.addObject("attrSpecPageInTypeMap",mainDataServ.getMainInfo("ATTR_SPEC_TYPE",mainDataServ.MAININFO_MAP));
			mv.addObject("productStateMap",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_PRODSTATE,mainDataServ.MAININFO_MAP));
			mv.addObject("pardSpecTypeList",mainDataServ.getMainInfo("ATTR_SPEC_TYPE"));//Specification Value Type SELECT
			mv.addObject("pardSpecCustList",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED));//Customized SELECT
			mv.addObject("maintenancecTypeList",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE));//Maintenance Type SELECT
			mv.addObject("crmVersion", WebPropertyUtil.getPropertyValue("crm_version"));
			mv.setViewName("../pardProduct/pardProduct.jsp");
			
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/updateImg", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateImg(HttpServletRequest request,HttpServletResponse response){
		return pardProductUpdate(request).toString();
	}
	
	@SavePermission(center="pard",module="product",parameterKey="productId",privilege="")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
	public ModelAndView pardProductAddOrUpdate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try{
			JSONObject json = null;
			String productId = getRequestValue(request, "productId");
			
			if(null==productId||"".equals(productId)){
				json = pardProductAdd(request);
				productId = json.getString(RETURN_DESC);
			}else{
				json = pardProductUpdate(request);
			}
			
			if(null!=json&&RESULT_OK.equals(json.get(RETURN_CODE))){
				mv.setViewName("redirect:./toDetail.shtml?product.productId="+productId);
			}else{
				mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+json.getString(RETURN_DESC)+"&callBack="+request.getRequestURL().toString());
			}
		}catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	private JSONObject pardProductAdd(final HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {

			String productName = getRequestValue(request, "productName");
			String extProdId = getRequestValue(request, "extProdId");
			String productDesc = getRequestValue(request, "productDesc");
			String componentId = getRequestValue(request, "componentId");
			String dependentType = getRequestValue(request, "dependentType");
			String subBusinessCode = getRequestValue(request, "subBusinessCode");
			String subBusinessName = getRequestValue(request, "subBusinessName");
			String attrSpecId = getRequestValue(request, "attrSpecId");
			String specType = getRequestValue(request, "specType");
			String defaultValues = getRequestValue(request, "defaultValues");
			String serviceId = getRequestValue(request, "serviceId");
			String systemSelect = getRequestValue(request, "systemSelect");
			String componentIdText= getRequestValue(request, "componentIdText");
			String chooseAttrSpecCodeInput= getRequestValue(request, "chooseAttrSpecCodeInput");
			
			Org orgBean = getOrg(request);
			// 1新增 产品
			Product product = new Product();
			product = checkProduct(product,productName,extProdId,productDesc);

			product.setProductType("10");
			product.setProductProviderId(orgBean.getOrgId());//第三方用户
			product.setStatusCd("1200");// 待审核
			product.setProdFuncType("103");
			product.setValueAddedFlag("0");
			product.setCooperationType("11");// 合作伙伴
			product.setEffDate("".equals(StringUtil.valueOf(product.getFormatEffDate())) ? null : DateUtil.stringToDate(product.getFormatEffDate().replace("-", "/"),"yyyy/MM/dd"));
			product.setExpDate("".equals(StringUtil.valueOf(product.getFormatExpDate())) ? null : DateUtil.stringToDate(product.getFormatExpDate().replace("-", "/"),"yyyy/MM/dd"));
//			JSONObject valJson = validateWithException(product);
//			if(!RESULT_OK.equals(valJson.getString(RETURN_CODE))){
//				return valJson;
//			}
		    String productId = pardProdServ.addPardProduct(product,subBusinessCode,subBusinessName,componentId,dependentType,attrSpecId,specType,
		    		defaultValues,serviceId,systemSelect,componentIdText,getOrg(request).getOrgId(),chooseAttrSpecCodeInput);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,productId);
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json;
	}
	private Product checkProduct(Product product,String productName,String extProdId,String productDesc){
		if(null==productName){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"product's name attribute length is not null.", null);
		}else if(productName.length()>50){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"product's name attribute length between 1 and 50.", null);
		}else{
			product.setProductName(productName);
		}
		if(null==extProdId){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"product's code attribute length is not null.", null);
		}else if(extProdId.length()>32){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"product's code attribute length between 1 and 32.", null);
		}else{
			product.setExtProdId(extProdId);
		}
		if(null!=productDesc&&productDesc.length()>250){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"product's description attribute length is not null or length between 0 and 250.", null);
		}else{
			product.setProductDesc(productDesc);
		}
		
		return product;
	}
	private JSONObject pardProductUpdate(final HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			String sFileId = getRequestValue(request, "sFileId");
			String productId = getRequestValue(request, "productId");
			String productName = getRequestValue(request, "productName");
			String extProdId = getRequestValue(request, "extProdId");
			String productDesc = getRequestValue(request, "productDesc");
			String componentId = getRequestValue(request, "componentId");
			String dependentType = getRequestValue(request, "dependentType");
			String subBusinessCode = getRequestValue(request, "subBusinessCode");
			String subBusinessName = getRequestValue(request, "subBusinessName");
			String attrSpecId = getRequestValue(request, "attrSpecId");
			String specType = getRequestValue(request, "specType");
			String defaultValues = getRequestValue(request, "defaultValues");
			String serviceId = getRequestValue(request, "serviceId");
			String chooseAttrSpecCodeInput= getRequestValue(request, "chooseAttrSpecCodeInput");
			
			Product product = new Product();
			if(!StringUtils.isEmpty(sFileId)){
				product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
				product.setSFileId(Integer.parseInt(sFileId));
				product.setFormatEffDate(null);
				product.setFormatExpDate(null);
				pardProdServ.updateProduct(product);
			}else{
				product = checkProduct(product,productName,extProdId,productDesc);
				product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
				product.setEffDate("".equals(StringUtil.valueOf(product.getFormatEffDate())) ? null : DateUtil.stringToDate(product.getFormatEffDate().replace("-", "/"),"yyyy/MM/dd"));
				product.setExpDate("".equals(StringUtil.valueOf(product.getFormatExpDate())) ? null : DateUtil.stringToDate(product.getFormatExpDate().replace("-", "/"),"yyyy/MM/dd"));
				pardProdServ.updatePardProduct(product,subBusinessCode,subBusinessName,componentId,dependentType,attrSpecId,specType,defaultValues,serviceId,chooseAttrSpecCodeInput);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json;
	}
	
	@SavePermission(center="pard",module="product",parameterKey="productId",privilege="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView pardProductDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		JSONObject json = new JSONObject();
		try {
			
			String productId = getRequestValue(request, "productId");
			Product product=new Product();
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product.setStatusCd("1300");
			JSONObject valJson=validateWithException(product);
			if(!RESULT_OK.equals(valJson.getString(RETURN_CODE))){
				return mv;
			}
			pardProdServ.updateProduct(product);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
			
			mv.setViewName("redirect:./toIndex.shtml");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return mv;
		
	}
	@SavePermission(center="pard",module="product",parameterKey="productId",privilege="")
	@RequestMapping(value = "/offShelf", method = RequestMethod.POST)
	public ModelAndView pardProductOffShelf(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		ModelAndView mv = new ModelAndView();
		try {
			String productId = getRequestValue(request, "productId");
			Product product=new Product();
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product.setStatusCd("1600");
			JSONObject valJson = validateWithException(product);
			if(!RESULT_OK.equals(valJson.getString(RETURN_CODE))){
				return mv;
			}
			pardProdServ.updateProduct(product);
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
		} catch (Exception e) {
			json.put(RETURN_CODE, RESULT_ERR);
			json.put(RETURN_DESC,e.getMessage());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		mv.setViewName("redirect:./toIndex.shtml");
		return mv;
	}
	@SavePermission(center="pard",module="product",parameterKey="productId",privilege="")
	@RequestMapping(value = "/submitCheck", method = RequestMethod.POST)
	public ModelAndView pardProductSubmitCheck(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			Org org = (Org)request.getSession().getAttribute("userBean");
			Object switchUserRole = request.getSession().getAttribute("switchUserRole");
			
			String productId = getRequestValue(request, "productId");
			String productName = getRequestValue(request, "productName");
			String statusCd = getRequestValue(request, "statusCd");
//			String msgIdStr=getRequestValue(request, "message.msgId");
			Product product=new Product();
			product.setProductName(productName);
			product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			product.setStatusCd(statusCd);
			pardProdServ.doPardProductSubmitCheck(product,String.valueOf(org.getOrgId()),switchUserRole);
			
			mv.setViewName("redirect:./toDetail.shtml?product.productId="+productId);
		} catch(BusinessException e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode="+e.getResult().getCode()+"&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+CommonUtil.getErrMsg(e)+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/winList" , produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardProductWinList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			Org orgBean = getOrg(request);
			//获取请求次数
		    String draw = getRequestValue(request, "draw");
		    
		    //数据起始位置
		    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //过滤后记录数
		    String recordsFiltered = "";
			
			Map paramMap = new HashMap();
			
			paramMap.put("pro_mysql", startRow);
			paramMap.put("page_record", rows);
			
			String prodName = getRequestValue(request, "prod_name");  
			String prodCode = getRequestValue(request, "prod_code"); 
			String cooperationType = getRequestValue(request, "cooperationType"); 
			if(!"".equals(prodName)&&null!=prodName){
				paramMap.put("prodName", prodName);
			}
			if(!"".equals(prodCode)&&null!=prodCode){
				paramMap.put("prodCode", prodCode);
			}
			if(!"".equals(cooperationType)&&null!=cooperationType){
				paramMap.put("cooperationType", cooperationType);
			}
			
			if(EAAPConstants.isCloud()){
				String provider = getRequestValue(request, "provider") + "," + getOrg(request).getOrgId();
				paramMap.put("project", EAAPConstants.O2P_WEB_DOMAIN_CLOUD);
				if(!"".equals(provider)&&null!=provider){
					paramMap.put("providerId", provider.split(","));
				}
			}else{
				paramMap.put("project", EAAPConstants.O2P_WEB_DOMAIN_LOCAL);
				paramMap.put("providerId", getOrg(request).getOrgId());
			}
			
			 //获取用户过滤框里的字符
//		    String searchValue = request.getParameter("search[value]");
		    //总记录数
		    int recordsTotal = pardProdServ.countProductList(paramMap);
		    if(0 == recordsTotal){
		    	throw new BusinessException(ExceptionCommon.WebExceptionCode,
						"query product list error... " ,null); 
		    }
			List<Map> pardSpecList = pardProdServ.queryProductList(paramMap);
			JSONArray dataList=new JSONArray();
			JSONArray subDataList=null;
			for(Map pardSpecMap:pardSpecList){
				subDataList=new JSONArray();
				subDataList.add("<input type='checkbox' value='' class='checkboxes' id='"+pardSpecMap.get("PRODUCT_ID")+"' name='ck'/>");
				subDataList.add(pardSpecMap.get("NAME"));
				subDataList.add(pardSpecMap.get("EXT_PROD_ID"));
				subDataList.add(pardSpecMap.get("PRODUCT_NAME"));
				subDataList.add(pardSpecMap.get("PRODUCT_DESC"));
				subDataList.add(pardSpecMap.get("COOPERATION_TYPE"));
				subDataList.add(pardSpecMap.get("MANAGE_GRADE"));
				subDataList.add(pardSpecMap.get("PRODUCT_ID"));
				dataList.add(subDataList);
			}
			
			json.put("draw", draw);
			json.put("recordsTotal",recordsTotal);
			json.put("recordsFiltered", recordsTotal);
			json.put("data",dataList.toJSONString());
			
		}catch(Exception e){
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
		}
		return json.toString();
	}
	/**
	 * 从request获取数据，拼装product
	 * @param request
	 * @return 
	 * @throws ParseException
	 */
//	private JSONObject productValidateWithException(final HttpServletRequest request) throws ParseException{
//		String productId = getRequestValue(request, "productId");
//		String productName = getRequestValue(request, "productName");
//		String extProdId = getRequestValue(request, "extProdId");
//		String productDesc = getRequestValue(request, "productDesc");
////		String componentId = getRequestValue(request, "componentId");
////		String subBusinessCode = getRequestValue(request, "subBusinessCode");
////		String subBusinessName = getRequestValue(request, "subBusinessName");
////		String attrSpecId = getRequestValue(request, "attrSpecId");
////		String specType = getRequestValue(request, "specType");
////		String defaultValues = getRequestValue(request, "defaultValues");
//		
//		Product product=new Product();
//		product.setProductName(productName);
//		product.setExtProdId(extProdId);
//		product.setProductDesc(productDesc);
//		product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
//		product.setEffDate("".equals(StringUtil.valueOf(product.getFormatEffDate())) ? null : DateUtil.stringToDate(product.getFormatEffDate().replace("-", "/"),"yyyy/MM/dd"));
//		product.setExpDate("".equals(StringUtil.valueOf(product.getFormatExpDate())) ? null : DateUtil.stringToDate(product.getFormatExpDate().replace("-", "/"),"yyyy/MM/dd"));
//		return validateWithException(product);
//	}
	
	public boolean switchEnvironment(){
		String switchEnvironmentFlag= WebPropertyUtil.getPropertyValue("switchEnvironmentFlag");
		if("true".equals(switchEnvironmentFlag)){
			return true;
		}
		return false;
		
	}
}
