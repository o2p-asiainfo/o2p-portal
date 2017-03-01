package com.ailk.o2p.portal.controller.pard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.CharSpec;
import com.ailk.eaap.op2.bo.CharSpecValue;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.ailk.o2p.portal.service.pardService.IPardServiceSpecServ;
import com.ailk.o2p.portal.service.pardSpec.IPardSpecServ;
import com.ailk.o2p.portal.service.pardproduct.IPardProdServ;
import com.ailk.o2p.portal.utils.Permission;
import com.ailk.o2p.portal.utils.SavePermission;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
@Controller
@RequestMapping(value = "/pardSpec")
public class PardSpecController extends BaseController {

	private static final Logger log = Logger.getLog(PardSpecController.class);

	@Autowired
	private IPardSpecServ pardSpecServ;
	@Autowired
	private MainDataServ mainDataServ;
	@Autowired
	private IPardProdServ pardProdServ;
	@Autowired
	private IPardServiceSpecServ pardServiceSpecServ;

	@RequestMapping(value = "/toIndex")
	public ModelAndView toPardSpecIndex(final HttpServletRequest request) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//导航
		messages.add("eaap.op2.portal.index.index");
		messages.add("eaap.op2.portal.index.pradIndex");
		messages.add("eaap.op2.portal.pardProd.index.provide");
		messages.add("eaap.op2.portal.pardSpec.spec");
		//查询条件
		messages.add("eaap.op2.portal.pardSpec.code");//Code
		messages.add("eaap.op2.portal.pardSpec.name");//Name
		messages.add("eaap.op2.portal.pardSpec.type");//Specification Value Type
		messages.add("eaap.op2.portal.pardSpec.customer");//Customized
		messages.add("eaap.op2.portal.pardSpec.maintenancec");//Maintenance Type
		//列头
		messages.add("eaap.op2.portal.pardSpec.id");//Code
		messages.add("eaap.op2.portal.pardSpec.code");//Code
		messages.add("eaap.op2.portal.pardSpec.name");//Name
		messages.add("eaap.op2.portal.pardSpec.type");//Specification Value Type
		messages.add("eaap.op2.portal.pardSpec.note");//Customized
		messages.add("eaap.op2.portal.pardSpec.customer");//Maintenance Type
		messages.add("eaap.op2.portal.pardSpec.default");//Code
		messages.add("eaap.op2.portal.pardSpec.maintainType");//Name
		//按钮
		messages.add("eaap.op2.portal.devmgr.query");
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.doc.cancel");
				
		messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");
		
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("pardSpecTypeList",mainDataServ.getMainInfo("ATTR_SPEC_TYPE"));//Specification Value Type SELECT
		mv.addObject("pardSpecCustList",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED));//Customized SELECT
		mv.addObject("maintenancecTypeList",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE));//Maintenance Type SELECT
		mv.setViewName("../pardSpec/productAttribute.jsp");
		return mv;
	}
	@RequestMapping(value = "/toWinIndex")
	public ModelAndView toPardSpecWinIndex(final HttpServletRequest request) {
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.index.SysName");
		//导航
		messages.add("eaap.op2.portal.index.index");
		messages.add("eaap.op2.portal.index.pradIndex");
		messages.add("eaap.op2.portal.pardProd.index.provide");
		messages.add("eaap.op2.portal.pardSpec.spec");
		//查询条件
		messages.add("eaap.op2.portal.pardSpec.code");//Code
		messages.add("eaap.op2.portal.pardSpec.name");//Name
		messages.add("eaap.op2.portal.pardSpec.type");//Specification Value Type
		messages.add("eaap.op2.portal.pardSpec.customer");//Customized
		messages.add("eaap.op2.portal.pardSpec.maintenancec");//Maintenance Type
		//列头
		messages.add("eaap.op2.portal.pardSpec.id");//Code
		messages.add("eaap.op2.portal.pardSpec.code");//Code
		messages.add("eaap.op2.portal.pardSpec.name");//Name
		messages.add("eaap.op2.portal.pardSpec.type");//Specification Value Type
		messages.add("eaap.op2.portal.pardSpec.note");//Customized
		messages.add("eaap.op2.portal.pardSpec.customer");//Maintenance Type
		messages.add("eaap.op2.portal.pardSpec.default");//Code
		messages.add("eaap.op2.portal.pardSpec.maintainType");//Name
		
		messages.add("eaap.op2.portal.pardSpec.key");
		messages.add("eaap.op2.portal.pardSpec.showValue");
		messages.add("eaap.op2.portal.pardSpec.operation");
		messages.add("eaap.op2.portal.pardSpec.minimum");
		messages.add("eaap.op2.portal.pardSpec.maximum");
		messages.add("eaap.op2.portal.pardSpec.defaultValue");
		messages.add("eaap.op2.portal.pardSpec.minCharacterister");
		messages.add("eaap.op2.portal.pardSpec.maxCharacterister");
		
		messages.add("eaap.op2.portal.pardSpec.attrIsMulti");
		messages.add("eaap.op2.portal.pardSpec.attrVal");
		//按钮
		messages.add("eaap.op2.portal.devmgr.query");
		messages.add("eaap.op2.portal.manager.auth.save");
		messages.add("eaap.op2.portal.doc.submit");
		messages.add("eaap.op2.portal.doc.cancel");
		messages.add("eaap.op2.portal.manager.auth.update");
				
		messages.add("eaap.op2.portal.pardProd.prodDetail.prodName");
		ModelAndView mv = new ModelAndView();
		addTranslateMessage(mv, messages);
		mv.addObject("pardSpecTypeList",mainDataServ.getMainInfo("ATTR_SPEC_TYPE"));//Specification Value Type SELECT
		mv.addObject("pardSpecCustList",mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPECISCUSTOMIZED));//Customized SELECT
		mv.addObject("maintenancecTypeList",mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE));//Maintenance Type SELECT
		mv.setViewName("../pardSpec/productAttribute.jsp");
		return mv;
	}
	@RequestMapping(value = "/list", produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardSpecList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			//获取请求次数
		    String draw = getRequestValue(request, "draw");
		    //数据起始位置
		    int startRow ="".equals(getRequestValue(request, "start"))?1:Integer.parseInt(getRequestValue(request, "start"));
		    //数据长度
		    int rows = "".equals(getRequestValue(request, "length"))?10:Integer.parseInt(getRequestValue(request, "length"));
		    //总记录数
		    String recordsTotal = "";
			
		    CharSpec charSpec = new CharSpec();
		    charSpec.setOrgId(getOrgId(request));
		    charSpec.setBegin(startRow);
		    charSpec.setEnd(rows);
		    
			String code = getRequestValue(request, "code");  
			String name = getRequestValue(request, "name");  
			String isCustomized = getRequestValue(request, "isCustomized"); 
			String type = getRequestValue(request, "type"); 
			String mainType = getRequestValue(request, "maintenancec"); 
			
			if(!"".equals(code)&&null!=code){
				charSpec.setCode(code);
			}
			if(!"".equals(name)&&null!=name){
				charSpec.setCharSpecName(name);
			}
			if(!"".equals(isCustomized)&&null!=isCustomized){
				charSpec.setIsCustomized(isCustomized);
			}
			if(!"".equals(type)&&null!=type){
				charSpec.setValueType(type);
			}
			if(!"".equals(mainType)&&null!=mainType&&mainType.equals("3")){
				charSpec.setCharSpecMaintainType(mainType);
				charSpec.setOrgId(null);
			}else if(!"".equals(mainType)&&null!=mainType&&mainType.equals("4")){
				charSpec.setCharSpecMaintainType(mainType);
				charSpec.setOrgId(null);
			}else if(!"".equals(mainType)&&null!=mainType){
				charSpec.setCharSpecMaintainType(mainType);
			}
			charSpec.setStatusCd(EAAPConstants.COMM_STATE_ONLINE);
			
			recordsTotal = pardSpecServ.cntCharSpec(charSpec);
			List<CharSpec> charSpecList = null;
			if("0".equals(recordsTotal)){
				charSpecList = new ArrayList<CharSpec>();
			}else{
				charSpecList = pardSpecServ.qryCharSpec(charSpec);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject subData = null;
			Map attrSpecTypeMap = (Map)mainDataServ.getMainInfo("ATTR_SPEC_TYPE",MainDataServ.MAININFO_MAP);
			Map specMaintainTypeMap = (Map)mainDataServ.getMainInfo(EAAPConstants.SPEC_MAINTAIN_TYPE,MainDataServ.MAININFO_MAP);
			for(CharSpec charSpecTemp : charSpecList){
				subData = new JSONObject();
				subData.put("ATTR_SPEC_INDEX","<input type='checkbox' value='"+charSpecTemp.getCharSpecId()+"' class='checkboxes' name='attrSpecId' />");
				subData.put("ATTR_SPEC_ID",charSpecTemp.getCharSpecId());
				subData.put("ATTR_SPEC_CODE",null==charSpecTemp.getCode()?"":charSpecTemp.getCode());
				subData.put("ATTR_SPEC_NAME",charSpecTemp.getCharSpecName());
				subData.put("ATTR_SPEC_DESC",null==charSpecTemp.getDescription()?"":charSpecTemp.getDescription());
				subData.put("PAGE_IN_TYPE",charSpecTemp.getValueType());
				subData.put("PAGE_IN_TYPE_FORMAT",attrSpecTypeMap.get(charSpecTemp.getValueType()));
				subData.put("IS_CUSTOMIZED",charSpecTemp.getIsCustomized());
				subData.put("IS_CUSTOMIZED_FORMAT",getIsCustomized(charSpecTemp.getIsCustomized()));
				subData.put("IS_CUSTOMIZED_FORMAT",getIsCustomized(charSpecTemp.getIsCustomized()));
				subData.put("DEFAULT_VALUE",null==charSpecTemp.getDefaultValue()?"":charSpecTemp.getDefaultValue());
				subData.put("MIN_CARDINALITY", null==charSpecTemp.getMinCardinality()?"":charSpecTemp.getMinCardinality());
				subData.put("MAX_CARDINALITY", null==charSpecTemp.getMaxCardinality()?"":charSpecTemp.getMaxCardinality());
				subData.put("IS_MULTI", null==charSpecTemp.getIsMulti()?"":charSpecTemp.getIsMulti());
				subData.put("SPEC_MAINTAIN_TYPE", charSpecTemp.getCharSpecMaintainType());
				subData.put("SPEC_MAINTAIN_TYPE_FORMAT",specMaintainTypeMap.get(charSpecTemp.getCharSpecMaintainType()));
				dataList.add(subData);
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
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	@RequestMapping(value = "/attrSpecValuelist", produces="application/json; charset=utf-8")
	@ResponseBody
	public String pardSpecValueList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			//获取请求次数
		    String draw = getRequestValue(request, "draw");
		    
		    JSONArray dataList = new JSONArray();
		    String attrSpecId = getRequestValue(request, "attrSpecId");
		    String objId = getRequestValue(request, "objId");
		    String objType = getRequestValue(request, "objType");
		    if(null==attrSpecId || "".equals(attrSpecId)){
		    	json.put("recordsTotal","0");
		    	json.put("recordsFiltered", "0");
		    	json.put("draw", draw);
		    	json.put("data","");
		    }else{
				if(null==objId||"".equals(objId.trim())){
					dataList = getJSONString(dataList,"0",attrSpecId,null);
				}else{
					if("PRODUCT".equals(objType.trim())){
						Map map = new HashMap();
						map.put("PRODUCT_ATTR_ID", objId);
						dataList = getJSONString(dataList,"1",attrSpecId,pardProdServ.queryProdAttrValue(map));
					}else if("SERVICE".equals(objType.trim())){
						dataList = getJSONString(dataList,"2",attrSpecId,pardServiceSpecServ.qryServiceSpecAttrValue(objId));
					}
				}
				
		    	json.put("recordsTotal",dataList.size());
		    	json.put("recordsFiltered", dataList.size());
		    	json.put("draw", draw);
		    	json.put("data",dataList.toJSONString());
		    }
		}catch(Exception e){
			json.put("draw", "0");
			json.put("recordsTotal","0");
			json.put("recordsFiltered", "0");
			json.put("data","");
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return json.toString();
	}
	
	private JSONArray getJSONString(JSONArray dataList, String type, String charSpecId,String objValueId){
		JSONObject subData = null;
		
		CharSpec charSpec = pardSpecServ.qryCharSpecById(charSpecId);
		String isMulti = charSpec.getIsMulti();
		CharSpecValue charSpecValue = new CharSpecValue();
		charSpecValue.setCharSpecId(charSpecId);
    	List<CharSpecValue> charSpecValueList = pardSpecServ.qryCharSpecValue(charSpecValue);
		for(CharSpecValue charSpecValueTemp : charSpecValueList){
			subData = new JSONObject();
			
			if("0".equals(type)){
				subData.put("INDEX", this.getIndexAttrSpec(isMulti, charSpecValueTemp.getIsDefault(), charSpecValueTemp.getCharSpecValueId()));
			}else if("1".equals(type)){
				subData.put("INDEX", this.getIndexProduct(isMulti, objValueId, charSpecValueTemp.getCharSpecValueId()));
			}else if("2".equals(type)){
				subData.put("INDEX", this.getIndexServiceSpec(isMulti, objValueId, charSpecValueTemp.getCharSpecValueId()));
			}
			
			subData.put("ATTR_VALUE_ID",charSpecValueTemp.getCharSpecValueId());
			subData.put("ATTR_SPEC_ID",charSpecValueTemp.getCharSpecId());
			subData.put("ATTR_VALUE_NAME",charSpecValueTemp.getDisplayValue());
			subData.put("ATTR_VALUE",charSpecValueTemp.getValue());
			subData.put("ATTR_DESC",null==charSpecValueTemp.getDescription()?"":charSpecValueTemp.getDescription());
			subData.put("SEQ_ID",charSpecValueTemp.getSeqId());
			subData.put("IS_DEFAULT",charSpecValueTemp.getIsDefault());
			
			dataList.add(subData);
		}
		
		return dataList;
	}
	
	private String getIndexAttrSpec(String isMulti, String isDefault, String attrValueId){
		if("0".equals(isMulti)){
			if("0".equals(isDefault)){
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}else{
			if("0".equals(isDefault)){
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}
	}
	
	private String getIndexProduct(String isMulti, String productValueId, String attrValueId){
		if("0".equals(isMulti)){
			if(productValueId.indexOf(attrValueId)>=0){
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}else{
			if(productValueId.indexOf(attrValueId)>=0){
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}
	}
	
	private String getIndexServiceSpec(String isMulti, String serviceSpecValueId, String attrValueId){
		if("0".equals(isMulti)){
			if(serviceSpecValueId.indexOf(attrValueId)>=0){
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='checkbox' value='"+attrValueId+"' class='checkboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}else{
			if(serviceSpecValueId.indexOf(attrValueId)>=0){
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' checked='checked'/>";
			}else{
				return "<input type='radio' value='"+attrValueId+"' class='radioboxes' name='attrSpecValueId' id='attrSpecValueId' />";
			}
		}
	}
	
	@Permission(center="pard",module="pardspec", privilege="audit")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
	@SavePermission(center="pard",module="attrSpec",parameterKey="attrSpecId",privilege="")
	@SuppressWarnings("all")
	public ModelAndView PardSpecAddOrUpdate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try{
			String charSpecId = getRequestValue(request,"attrSpecId");
			String attrSpecCode = getRequestValue(request,"code");
			String attrSpecName = getRequestValue(request,"name");
			String minCharacterister = getRequestValue(request,"min");
			String maxCharacterister = getRequestValue(request,"max");
			String isCustomized = getRequestValue(request,"customized");
			String pageInType = getRequestValue(request,"type");
			String attrSpecDesc = getRequestValue(request,"featureNote");
			
			CharSpec charSpec = new CharSpec();
			List<CharSpecValue> charSpecValueList = new ArrayList<CharSpecValue>();
			
			charSpec.setCharSpecMaintainType(EAAPConstants.CHAR_SPEC_MAINTAIN_TYPE_PARTNER_SELF);
			charSpec.setOrgId(this.getOrgId(request));
			if(null==attrSpecCode){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's code is not null!", null);
			}else if(attrSpecCode.trim().length()>50){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's max length must be less than 50!", null);
			}else{
//				Map para = new HashMap();
//				para.put("attrSpecCodeEqual", attrSpecCode.trim());
//				if(null!=attrSpecId&&!"".equals(attrSpecId)){
//					para.put("attrSpecIdNotEqual", attrSpecId);
//				}
//				para.put("specMaintainType", -1);
//				int attrSpecCodeCount= pardSpecServ.countFeatureSpec(para);
//				if(attrSpecCodeCount>0){
//					throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's code is already exists!", null);
//				}
				charSpec.setCode(attrSpecCode.trim());
			}
			
			if(null==attrSpecName){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's name is not null!", null);
			}else if(attrSpecName.trim().length()>50){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's name length must be less than 50!", null);
			}else{
				charSpec.setCharSpecName(attrSpecName.trim());
			}
			
			if(null==minCharacterister){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's minCharacterister is not null!", null);
			}else if(null!=minCharacterister){
				charSpec.setMinCardinality(Integer.parseInt(minCharacterister));
			}
			
			if(null==maxCharacterister){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's maxCharacterister is not null!", null);
			}else if(null!=maxCharacterister){
				charSpec.setMaxCardinality(Integer.parseInt(maxCharacterister));
			}
			
			if(null==isCustomized){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's isCustomized is not null!", null);
			}else{
				charSpec.setIsCustomized(isCustomized);
			}
			
			if(null==pageInType){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's pageInType is not null!", null);
			}else{
				charSpec.setValueType(pageInType);
			}
			
			if(null!=attrSpecDesc&&attrSpecDesc.length()>250){
				throw new BusinessException(ExceptionCommon.WebExceptionCode," attribute 's attrSpecDesc max length must be less than 250!", null);
			}else{
				charSpec.setDescription(attrSpecDesc);
			}
			
			if(!"".equals(pageInType)&&null!=pageInType){
				if(pageInType.equals("1")||pageInType.equals("2")||pageInType.equals("5")||pageInType.equals("6")||pageInType.equals("7")){
					String defaultValue = getRequestValue(request,"textDefaultValue");
					if(defaultValue.length()>200){
						throw new BusinessException(ExceptionCommon.WebExceptionCode,"If type is Text,than attribute 's textValue's max length must be less than 200!", null);
					}
					
					charSpec.setDefaultValue(defaultValue);
				}else if(pageInType.equals("3")){
					String valueFrom = getRequestValue(request,"featureMinimum");
					String valueTo = getRequestValue(request,"featureMaximum");
					String defaultValue = getRequestValue(request,"num_featureDefault");
					if(null==valueFrom || "".equals(valueFrom) || null==valueTo || "".equals(valueTo)){
						throw new BusinessException(ExceptionCommon.WebExceptionCode,"If type is Number,than  attribute 's valueFrom or valueTo is not null!", null);
					}else{
						Integer.parseInt(valueFrom);
						Integer.parseInt(valueTo);
					}
					if(null!=defaultValue && !"".equals(defaultValue)){
						Integer.parseInt(defaultValue);
					}
					
					charSpec.setDefaultValue(defaultValue);
					CharSpecValue charSpecValue = new CharSpecValue();
					charSpecValue.setValueFrom(valueFrom);
					charSpecValue.setValueTo(valueTo);
					
					charSpecValueList.add(charSpecValue);
				}else if(pageInType.equals("4")){
					String defaultValue = getRequestValue(request,"e_featureDefault");
					String isMulti = getRequestValue(request,"isMulti");
					
					if(null==defaultValue){
						throw new BusinessException(ExceptionCommon.WebExceptionCode,"If type is Eum,than  attribute 's value is not null!", null);
					}
					
					if("0".equals(isMulti.trim())||"1".equals(isMulti.trim())){
						charSpec.setIsMulti(isMulti);
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,"product attribute 's isMulti 's value is not fllow!", null);
					}
					
					String[] attrValueArr = defaultValue.split(";");
					String[] attrValueDet = null;
					Integer i = 0;
					
					String oldValKey = null;
					String valKey = null;
					String valName = null;	
					
					CharSpecValue charSpecValue = null;
					if(null!=attrValueArr && attrValueArr.length>0){
						for(; i<attrValueArr.length; ){
							for(int j=i+1; j<attrValueArr.length; j++){
								if(attrValueArr[i].equals(attrValueArr[j])){
									throw new BusinessException(ExceptionCommon.WebExceptionCode,"If type is Eum,than  attribute vlaue's key not repeat!", null);
								}
							}
							
							charSpecValue = new CharSpecValue();
							attrValueDet = attrValueArr[i].split(",");
							
							charSpecValue.setSeqId(++i);
							charSpecValue.setDisplayValue(attrValueDet[1]);
							charSpecValue.setValue(attrValueDet[0]);
							charSpecValue.setDescription(attrValueDet[1]);
							charSpecValue.setIsDefault(attrValueDet[2]);
							
							charSpecValueList.add(charSpecValue);
						}
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,"If type is Eum,than  attribute vlaue is not empty!", null);
					}
				}
			}
			
			if(null!=charSpecId&&!"".equals(charSpecId)){
				charSpec.setCharSpecId(charSpecId);
				pardSpecServ.updateCharSpec(charSpec, charSpecValueList);
			}else{
				pardSpecServ.addCharSpec(charSpec, charSpecValueList);
			}
			mv.setViewName("redirect:./toWinIndex.shtml");
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
		}
		return mv;
	}
	
 
	@RequestMapping(value = "/toUpdate", produces="application/json; charset=utf-8")
	@ResponseBody
	public String toPardSpecUpdate(final HttpServletRequest request,
			final HttpServletResponse response) {
		try{
			JSONObject json = new JSONObject();
			String charSpecId = getRequestValue(request,"attrSpecId");
			if(!"".equals(charSpecId)&&null!=charSpecId){
				CharSpec charSpec = pardSpecServ.qryCharSpecById(charSpecId);
				json.put("charSpec", charSpec);
				
				CharSpecValue charSpecValue = new CharSpecValue();
				charSpecValue.setCharSpecId(charSpecId);
				json.put("charSpecValueList", pardSpecServ.qryCharSpecValue(charSpecValue));
				
			}
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}
	
	@RequestMapping(value = "/handleEumCustomizedTypeList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String handleEumCustomizedTypeList(final HttpServletRequest request,
			final HttpServletResponse response){
		try{
			JSONObject json = new JSONObject();
			String customizedType = getRequestValue(request,"customizedType");
			Map eumCustomizedTypeMap = (Map)mainDataServ.getMainInfo(EAAPConstants.MAINDATATYPE_SIGN_ATTRSPEC_TYPE);
			Iterator iter = eumCustomizedTypeMap.entrySet().iterator();
			List<HashMap<String, String>> eumCustomizedTypeList = new ArrayList<HashMap<String,String>>();
			
			if(!"".equals(customizedType)&&null!=customizedType){
				json.put("code", RESULT_OK);
				if("Y".equals(customizedType)){
					while(iter.hasNext()) {
						 HashMap<String,String> mapTmp = new HashMap<String,String>() ;
					 	 Entry<String,String> entry = (Entry<String,String>)iter.next();
						 mapTmp.put("key", entry.getKey().toString()) ;
						 mapTmp.put("value", entry.getValue().toString()) ;
						 eumCustomizedTypeList.add(mapTmp) ;
					}
				}else if("N".equals(customizedType)){
					while(iter.hasNext()) {
						HashMap<String,String> mapTmp = new HashMap<String,String>() ;
					 	 Entry<String,String> entry = (Entry<String,String>)iter.next();
					     if("1".equals(entry.getKey().toString())){
					    	 mapTmp.put("key", entry.getKey().toString()) ;
					    	 mapTmp.put("value", entry.getValue().toString()) ;
					    	 eumCustomizedTypeList.add(mapTmp) ;
					    	 break;
					     }
					 }
				}
				json.put("desc", eumCustomizedTypeList);
			}else{
				json.put("code", RESULT_ERR);
				json.put("desc", "");
			}
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}
	
	@Permission(center="pard",module="pardspec", privilege="audit")
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces="application/json; charset=utf-8")
	@SavePermission(center="pard",module="attrSpec",parameterKey="attrSpecId",privilege="")
	@ResponseBody
	public String pardSpecDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		try{
			JSONObject json = new JSONObject();
			String charSpecId = getRequestValue(request,"attrSpecId");
			if(!"".equals(charSpecId)&&null!=charSpecId){
				pardSpecServ.delCharSpec(charSpecId);
			}
			json.put(RETURN_CODE, RESULT_OK);
			json.put(RETURN_DESC,"Success");
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}
	
	@RequestMapping(value = "/isUD", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String isUD(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		try{
			JSONObject json = new JSONObject();
			String charSpecId = getRequestValue(request,"attrSpecId");
			if(!"".equals(charSpecId)&&null!=charSpecId){
				int cnt = pardSpecServ.isUpdateOrDelete(charSpecId);
				if(cnt>=1){
					json.put(RETURN_CODE, "1");
					json.put(RETURN_DESC, "");
				}else{
					json.put(RETURN_CODE, "0");
					json.put(RETURN_DESC, "");
				}
			}
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
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
	
	@RequestMapping(value = "/checkPageInTypeCount", produces="application/json; charset=utf-8")
	@ResponseBody
	public String checkPageInTypeCount(final HttpServletRequest request,
			final HttpServletResponse response) {
		try{
			JSONObject json = new JSONObject();
			String pageInType = getRequestValue(request,"pageInType");
			Org orgBean = getOrg(request);
			Integer orgId = orgBean.getOrgId();
			int result = pardSpecServ.checkPageInTypeCount(pageInType,orgId);
			json.put("resultMap", result);
			return json.toString();
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, e.getMessage(), null));
		}
		return null;
	}
	
}
