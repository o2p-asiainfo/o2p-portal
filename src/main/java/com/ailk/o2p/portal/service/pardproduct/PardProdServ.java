package com.ailk.o2p.portal.service.pardproduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.ProductAttr;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.ServiceSpecDao;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.dao.pardproduct.PardProdDao;
import com.ailk.o2p.portal.dao.provider.IProviderDao;
import com.ailk.o2p.portal.dao.tenant.ITenantDao;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.ailk.o2p.portal.service.pardService.IPardServiceSpecServ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.asiainfo.integration.o2p.web.util.WorkRestUtils;

@Service
@Transactional
public class PardProdServ implements IPardProdServ{
	@Autowired
	private PardProdDao pardProdDao;
	@Autowired
	private IMessageServ messageServ;
	@Autowired
	private IPardServiceSpecServ pardServiceSpecServ;
	@Autowired
	private IProviderDao providerDao;
	@Autowired
	private ServiceSpecDao serviceSpecDao;
	@Autowired
	private ITenantDao tenantDao;
	
    public List<Map> queryComponentList(Map map){
    	return pardProdDao.queryComponentList(map);
    }
 
    private void checkAttributeCode(String serviceId,String chooseAttrSpecCodeInput)throws Exception{
    	String needCheckCodeNum=WebPropertyUtil.getPropertyValue("need_check_code_num");
    	if(needCheckCodeNum!=null&&"1".equals(needCheckCodeNum)){
    		Map qryMap=new HashMap();
        	qryMap.put("serviceId", serviceId);
        	List<Map<String,Object>> serviceSpecAttrList=serviceSpecDao.qryServiceSpecAttr(qryMap);
        	Object attrSpecCode=null;
        	Map checkCntMap=new HashMap();
        	for(Map<String,Object> serviceSpecAttrMap:serviceSpecAttrList){
        		attrSpecCode=serviceSpecAttrMap.get("code".toUpperCase());
        		if(checkCntMap.get(attrSpecCode)==null){
        			checkCntMap.put(attrSpecCode, attrSpecCode);
        		}else{
        			throw new BusinessException(ExceptionCommon.WebExceptionCode,"Attribute code of the product cannot be the same!", null);
        		}
        	}
        	if(chooseAttrSpecCodeInput!=null&&!"".equals(chooseAttrSpecCodeInput)){
        		String[] chooseAttrSpecCodeAry=chooseAttrSpecCodeInput.split(";");
            	for(String chooseAttrSpecCode:chooseAttrSpecCodeAry){
            		if(checkCntMap.get(chooseAttrSpecCode)==null){
            			checkCntMap.put(chooseAttrSpecCode, chooseAttrSpecCode);
            		}else{
            			throw new BusinessException(ExceptionCommon.WebExceptionCode,"Attribute code of the product cannot be the same!", null);
            		}
            	}
        	}
    	}
    }
    public String addPardProduct(Product product,String subBusinessCode,String subBusinessName,String componentId,String dependentType,String attrSpecId,
    		String specType,String defaultValues,String serviceId,String systemSelect,String componentIdText,int orgId,String chooseAttrSpecCodeInput)throws Exception{
    	//效验code
    	checkAttributeCode(serviceId,chooseAttrSpecCodeInput);
    	
    	String productId = this.queryProductSeq();
    	product.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
		//产品属性列表
		List<ProductAttr> productAttrList = new ArrayList<ProductAttr>();
		List<Map> prodAttrValueList = new ArrayList<Map>();
		ProductAttr productAttr=null;
		Map prodAttrValue=null;
		//组件-是否是新增
		if("12".equals(systemSelect)){
			Component component=new Component();
			component.setName(componentIdText);
			component.setDescriptor("");
			component.setOrgId(orgId);
			component.setSfileId(null);
			component.setComponentId("");
			component.setCode(providerDao.getComponentSeq());
			component.setComponentTypeId(Integer.valueOf(EAAPConstants.COMM_STATE_TYPE_ID));
			component.setState(EAAPConstants.COMM_STATE_NEW);
			component.setPassword(EAAPConstants.COMM_STATE_PASSWEORD);
			componentId= providerDao.saveComponent(component);
		}
		// 2新增产品属性——子系统编码
		if (StringUtils.isNotEmpty(subBusinessCode) && StringUtils.isNotEmpty(subBusinessName)){
			String[] subBusinessCodes = subBusinessCode.trim().split(",");
			String[] subBusinessNames = subBusinessName.trim().split(",");
			String productAttrSeq=null;
			for (int i = 0; i < subBusinessCodes.length; i++) {
				productAttr=new ProductAttr();
				productAttrSeq = this.queryProductAttrSeq();
				productAttr.setStatusCd("1000");
				productAttr.setDefaultValue(subBusinessCodes[i].trim() + "," + subBusinessNames[i].trim()); 
				productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
				productAttr.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
				productAttr.setAttrSpecId(EAAPConstants.SUB_BUSINESS);//子业务编码，如在mobile TV中，会区分不同的频道。
				productAttr.setDisplayType("21");
				productAttr.setMappingId(subBusinessCodes[i].trim());
				String subProductId = pardProdDao.queryServiceSeq();
				productAttr.setProductAttrDesc(subProductId);  
				productAttrList.add(productAttr);//添加产品属性
			}	
		}
		//3组件
		if(componentId!=null && StringUtils.isNotEmpty(componentId) ){
			productAttr=new ProductAttr();
			productAttr.setStatusCd("1000");
			String productAttrSeq = this.queryProductAttrSeq();
			productAttr.setDefaultValue(componentId);
			productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
			productAttr.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			productAttr.setAttrSpecId(EAAPConstants.SERVICE_PROVIDER);//第三方系统编码 即组件
			productAttr.setDisplayType("21");
			productAttr.setMappingId("Service Provider");
			productAttrList.add(productAttr);//添加产品属性
		}
		// 4新增产品属性——用户定制
		if (StringUtils.isNotEmpty(attrSpecId)){
			String[] attrSpecIds = attrSpecId.trim().split(",");
			String[] specTypes = specType.trim().split(",");
			String[] defaultValuess = defaultValues.split(";");
			String[] chooseAttrSpecCodeAry=chooseAttrSpecCodeInput.split(";");
			String productAttrSeq=null;
			String defaultValue=""; 
			for (int i = 0; i < attrSpecIds.length; i++) {
				productAttr=new ProductAttr();
				productAttrSeq = this.queryProductAttrSeq();

				if(i<defaultValuess.length){  
					defaultValue=defaultValuess[i].trim();
				}else{
					defaultValue="";
				} 
				
				//可选值部份
				if("4".equals(specTypes[i].trim())){//1.文本2.参照3.数字4.枚举
					prodAttrValue =new HashMap();
					//添加产品属性可选值
					prodAttrValue.put("PRODUCT_ATTR_ID", productAttrSeq);
					prodAttrValue.put("ATTR_SPEC_ID", attrSpecIds[i].trim());
					if(!"".equals(defaultValue)){
						String[] valueArr = defaultValue.split("/");
						for(String value:valueArr){
							prodAttrValue.put("ATTR_VALUE_ID", value);
						}
					}
					prodAttrValueList.add(prodAttrValue);
				}
				productAttr.setDefaultValue(defaultValue);
				
				productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
				productAttr.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
				productAttr.setAttrSpecId(attrSpecIds[i].trim().toString());
				productAttr.setStatusCd("1000");
				productAttr.setDisplayType("21");
				productAttr.setMappingId(chooseAttrSpecCodeAry[i]);
				//保存产品属性
				productAttrList.add(productAttr);//添加产品属性
			}
		}
		//5 Dependent Type 标识是“基础”还是“依赖”产品
		if(dependentType!=null && StringUtils.isNotEmpty(dependentType) ){
			productAttr=new ProductAttr();
			String productAttrSeq = this.queryProductAttrSeq();
			productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
			productAttr.setProductId(BigDecimal.valueOf(Long.valueOf(productId)));
			productAttr.setAttrSpecId(EAAPConstants.DEPENDENT_TYPE);		//基础产品、依赖产品、空代表普通产品
			productAttr.setDefaultValue(dependentType);
			productAttr.setStatusCd("1000");
			productAttr.setDisplayType("21");	
			productAttr.setMappingId("Dependent Type");
			productAttrList.add(productAttr);//添加产品属性
		}
		this.addPardProductInfo(product,productAttrList,prodAttrValueList);//添加数据到
		pardServiceSpecServ.addOrUpdateProductService(productId, serviceId);
		return productId;
	}
    public void updatePardProduct(Product product,String subBusinessCode,String subBusinessName,String componentId,String dependentType,String attrSpecId,
    		String specType,String defaultValues,String serviceId,String chooseAttrSpecCodeInput)throws Exception{
    	//效验code
    	checkAttributeCode(serviceId,chooseAttrSpecCodeInput);
    	//产品属性列表
		List<ProductAttr> productAttrList = new ArrayList<ProductAttr>();
		List<Map> prodAttrValueList = new ArrayList<Map>();
		ProductAttr productAttr=null;
		Map prodAttrValue=null;
		// 9新增产品属性——子系统编码
		if (StringUtils.isNotEmpty(subBusinessCode) && StringUtils.isNotEmpty(subBusinessName)){
			String[] subBusinessCodes = subBusinessCode.trim().split(",");
			String[] subBusinessNames = subBusinessName.trim().split(",");
			String productAttrSeq=null;
			for (int i = 0; i < subBusinessCodes.length; i++) {
				productAttr = new ProductAttr();
				productAttrSeq = this.queryProductAttrSeq();
				productAttr.setDefaultValue(subBusinessCodes[i].trim() + "," + subBusinessNames[i].trim()); 
				productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
				productAttr.setProductId(product.getProductId());
				productAttr.setAttrSpecId(EAAPConstants.SUB_BUSINESS);//子业务编码，如在mobile TV中，会区分不同的频道。
				productAttr.setDisplayType("21");
				productAttr.setMappingId(subBusinessCodes[i].trim());
				String subProductId = pardProdDao.queryServiceSeq();
				productAttr.setProductAttrDesc(subProductId); 
				productAttrList.add(productAttr);//添加产品属性
			}	
		}
		//组件
		if(componentId!=null && StringUtils.isNotEmpty(componentId) ){
			productAttr = new ProductAttr();
			String productAttrSeq = this.queryProductAttrSeq();
			productAttr.setDefaultValue(componentId);
			productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
			productAttr.setProductId(product.getProductId());
			productAttr.setAttrSpecId(EAAPConstants.SERVICE_PROVIDER);//第三方系统编码 即组件
			productAttr.setDisplayType("21");
			productAttr.setMappingId("Service Provider");
			productAttrList.add(productAttr);//添加产品属性
		}
		
		//Dependent Type 标识是“基础”还是“依赖”产品
		if(dependentType!=null && StringUtils.isNotEmpty(dependentType) ){
			productAttr=new ProductAttr();
			String productAttrSeq = this.queryProductAttrSeq();
			productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
			productAttr.setProductId(product.getProductId());
			productAttr.setAttrSpecId(EAAPConstants.DEPENDENT_TYPE);		//基础产品、依赖产品、空代表普通产品
			productAttr.setDefaultValue(dependentType);
			productAttr.setStatusCd("1000");
			productAttr.setDisplayType("21");	
			productAttr.setMappingId("Dependent Type");
			productAttrList.add(productAttr);//添加产品属性
		}
		
		// 10新增产品属性——用户定制
		if (StringUtils.isNotEmpty(attrSpecId)){
			String[] attrSpecIds = attrSpecId.trim().split(",");
			String[] specTypes = specType.trim().split(",");
			String[] defaultValuess = defaultValues.trim().split(";");
			String[] chooseAttrSpecCodeAry=chooseAttrSpecCodeInput.split(";");
			String productAttrSeq=null;
			String defaultValue="";
			for (int i = 0; i < attrSpecIds.length; i++) {
				productAttr=new ProductAttr();
				productAttrSeq = this.queryProductAttrSeq();
				
				if(i<defaultValuess.length){
					defaultValue=defaultValuess[i].trim();
				}else{
					defaultValue="";
				}
				if("4".equals(specTypes[i].trim())){//1.文本2.参照3.数字4.枚举
					prodAttrValue = new HashMap();
					//添加产品属性可选值
					prodAttrValue.put("PRODUCT_ATTR_ID", productAttrSeq);
					prodAttrValue.put("ATTR_SPEC_ID", attrSpecIds[i].trim());
					if(!"".equals(defaultValue)){
						String[] valueArr = defaultValue.split("/");
						for(String value:valueArr){
							prodAttrValue.put("ATTR_VALUE_ID", value);
						}
					}
					prodAttrValueList.add(prodAttrValue); 
				}
				
				productAttr.setDefaultValue(defaultValue);
				productAttr.setProductAttrId(Integer.parseInt(productAttrSeq));
				productAttr.setProductId(product.getProductId());
				productAttr.setAttrSpecId(attrSpecIds[i].trim().toString());
				productAttr.setStatusCd(EAAPConstants.PRODUCT_ATTR_ONLINE);
				productAttr.setDisplayType("21");  
				productAttr.setMappingId(chooseAttrSpecCodeAry[i]);
				//保存产品属性
				productAttrList.add(productAttr);//添加产品属性
				//可选值部份
			}
		}
		this.updatePardProductInfo(product,productAttrList,prodAttrValueList);
		pardServiceSpecServ.addOrUpdateProductService(String.valueOf(product.getProductId()), serviceId);
	}
    public void doPardProductSubmitCheck(Product product,String orgId,Object switchUserRole)throws Exception{
    	BigDecimal productId=product.getProductId();
    	JSONObject jsonObject = new JSONObject();
    	JSONObject retJson = new JSONObject();
    	
    	String o2pWebDomain = WebPropertyUtil.getPropertyValue("o2p_web_domin");
		if(null==o2pWebDomain || "".equals(o2pWebDomain.trim())){
			o2pWebDomain = EAAPConstants.O2P_WEB_DOMAIN_LOCAL;
		}
		if(EAAPConstants.O2P_WEB_DOMAIN_LOCAL.equals(o2pWebDomain)){
			// 审批流程
			if(productId!=null){
				if("1200".equals(product.getStatusCd())){
					jsonObject.put("contentId", String.valueOf(product.getProductId()));
					UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
					String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
					Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
					if(userRoleInfo != null){
						userId = tenantDao.qryAdminIdByTenantId(userRoleInfo.getTenantId());
						tenantId = userRoleInfo.getTenantId();
					}
					retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_PRODUCT, "Local Process Product Audit Name:"+product.getProductName(),
							userId, tenantId, isNeedO2pAudit(jsonObject,switchUserRole ,product ,orgId,o2pWebDomain).toJSONString());
					String returnCode = retJson.getString("code");
					
					if ("0000".equals(returnCode)) {
						// 申请审批成功
						product.setStatusCd("1299");
						product.setAuditFlowId(retJson.getString("desc"));
						this.updateProduct(product);
					}
				}else if("1298".equals(product.getStatusCd()) || "1278".equals(product.getStatusCd())){
					JSONArray jsons = null;
					//获取任务节点
					product = this.selectProduct(product);
					
					String message = WorkRestUtils.taskListByProcessId(product.getAuditFlowId());
					if(!StringUtils.isEmpty(message)){
						jsons = JSON.parseArray(message);
						if(null!=jsons&&jsons.size()==1){
							retJson = jsons.getJSONObject(0);
							String taskId = retJson.getString("taskId");
							
							String ret = WorkRestUtils.completeTask(taskId,
									isNeedO2pAudit(jsonObject,switchUserRole ,product , orgId,o2pWebDomain).toJSONString());
							
							if(!StringUtils.isEmpty(ret)){
								retJson = JSON.parseObject(ret);
								String returnCode = retJson.getString("code");
								if ("0000".equals(returnCode)) {
									//置待办消息的状态
									BigDecimal msgId = messageServ.getMsgIdByQueryTitle(
											EAAPConstants.WORK_FLOW_MESSAGE_QUERY_PRODUCT.replace("#id", String.valueOf(product.getProductId())));
									messageServ.lookMsgById(orgId,String.valueOf(msgId));
									messageServ.updateMsgForWorkFlow(msgId);
									
									product.setStatusCd("1299");
									this.updateProduct(product);
								}
							}
						}
					}
				}
			}
    	}else{
    		if(switchUserRole != null && (Boolean) switchUserRole){
    			product.setStatusCd("1000");
				this.updateProduct(product);
    		}else{
    			jsonObject.put("contentId", String.valueOf(product.getProductId()));
    			UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
				String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
				Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
				if(userRoleInfo != null){
					userId = tenantDao.qryAdminIdByTenantId(userRoleInfo.getTenantId());
					tenantId = userRoleInfo.getTenantId();
				}
				retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_PRODUCT, "Cloud Process Product Audit Name:"+product.getProductName(),
						userId, tenantId, isNeedO2pAudit(jsonObject,switchUserRole ,product ,orgId,o2pWebDomain).toJSONString());
				String returnCode = retJson.getString("code");
				
				if ("0000".equals(returnCode)) {
					// 申请审批成功
					product.setStatusCd("1299");
					product.setAuditFlowId(retJson.getString("desc"));
					this.updateProduct(product);
				}
    		}
    	}
	}
    /**
     * 判断是否需要O2P审核 
     * 判断是否服务已经同步
     * 判断是否是O2P local或者O2P cloud 审核流程
     * @param jsonObject
     * @param switchUserRole 超级用户权限，不用O2P审核
     * @return
     */
    private JSONObject isNeedO2pAudit(JSONObject jsonObject,Object switchUserRole ,Product product ,String orgId,String o2pWebDomain){
    	if(EAAPConstants.O2P_WEB_DOMAIN_LOCAL.equals(o2pWebDomain)){
    		jsonObject.put("isLocal", Boolean.TRUE);
    	}else{
    		jsonObject.put("isLocal", Boolean.FALSE);
    	}
    	
    	if(switchUserRole != null && (Boolean) switchUserRole){
    		jsonObject.put("O2P_examime", Boolean.FALSE);
			jsonObject.put("O2P_audit_result", Boolean.TRUE);
			product.setProductPublisher(orgId);
    	}else{
    		String o2pAudit = WebPropertyUtil.getPropertyValue("process_product_audit_o2p_audit");
    		if("1".equals(o2pAudit)){
    			jsonObject.put("O2P_examime", Boolean.TRUE);
    		}else{
    			jsonObject.put("O2P_examime", Boolean.FALSE);
    			jsonObject.put("O2P_audit_result", Boolean.TRUE);
    		}
    	}
		return jsonObject;
    }
    
    public void lookMsgById(Integer userId,String titleQuery){
    	messageServ.lookMsgById(String.valueOf(userId), String.valueOf(messageServ.getMsgIdByQueryTitle(titleQuery)));
    }
	public void addPardProductInfo(Product product,List<ProductAttr> productAttrList,List<Map> prodAttrValueList) {
 		pardProdDao.insertProduct(product);
 		if (productAttrList !=null && productAttrList.size() > 0) {
			java.util.Iterator it = productAttrList.iterator();
			while (it.hasNext()){
				ProductAttr productAttr = (ProductAttr)it.next();
				pardProdDao.insertProductAttr(productAttr);
			}
		}
		if (prodAttrValueList !=null && prodAttrValueList.size() > 0) {
			java.util.Iterator it = prodAttrValueList.iterator();
			while (it.hasNext()){
				Map prodAttrValue = (Map)it.next();
				pardProdDao.insertProdAttrValue(prodAttrValue);
			}
		}
	}

 

	public List<OfferProdRel> queryOfferProdRelList(OfferProdRel offerProdRel){
		return pardProdDao.queryOfferProdRelList(offerProdRel);
	}
	public void updatePardProductInfo(Product product,List<ProductAttr> productAttrList,List<Map> prodAttrValueList) {
 		pardProdDao.updateProduct(product);
		pardProdDao.deleteProductAttrValue(product);
		pardProdDao.deleteProductAttr(product);
		
		if (productAttrList !=null && productAttrList.size() > 0) {
			Iterator<ProductAttr> it = productAttrList.iterator();
			while (it.hasNext()){
				ProductAttr productAttr = (ProductAttr)it.next();
				pardProdDao.insertProductAttr(productAttr);
			}
		}
		if (prodAttrValueList !=null && prodAttrValueList.size() > 0) {
			Iterator<Map> it = prodAttrValueList.iterator();
			while (it.hasNext()){
				Map prodAttrValue = (Map)it.next();
				pardProdDao.insertProdAttrValue(prodAttrValue);
			}
		}
	}
	public Integer updateOfferProdRel(OfferProdRel offerProdRel){
		return pardProdDao.updateOfferProdRel(offerProdRel);
	}
	
	public String getCheckMsgByObjectId(String objId){
		return messageServ.getCheckMsgByObjectId(EAAPConstants.WORK_FLOW_MESSAGE_QUERY_PRODUCT,objId);
	}
	
    @SuppressWarnings("unchecked")
    public List<Map> showProdList(Map map) {
    	if ("ALLNUM".equals(map.get("queryType"))) { 
    		return pardProdDao.queryProdCount(map);
    	}else{ 
    		List<Map> showProdList = pardProdDao.showProdList(map);
//    		List<Map> operatorProductList = pardProdDao.getOperatorProduct(map);
//    		if(null!=operatorProductList&&operatorProductList.size()>0){
//    			if(showProdList.addAll(operatorProductList)){
//        			return showProdList;
//        		}else{
//        			throw new BusinessException(ExceptionCommon.WebExceptionCode,"system error!", null);
//        		}
//    		}
    		return showProdList;
    	}
	} 
    public Product queryProduct(Product product) {
		return pardProdDao.queryProduct(product);
	}
	public String queryProductSeq(){
		return pardProdDao.queryProductSeq();
	}
	public String queryProdOfferSeq() {
		return pardProdDao.queryProdOfferSeq();
	}
	public String queryOfferProdRelSeq(){
		return pardProdDao.queryOfferProdRelSeq();
	}
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel){
		return pardProdDao.queryOfferProdRel(offerProdRel);
	}
	public void deletePardProdInfo(ProdOffer prodOffer,Product product){
		pardProdDao.updateProdOffer(prodOffer);
		pardProdDao.updateProduct(product);
	}
	public void updateProduct(Product product) {
		pardProdDao.updateProduct(product) ;
	}
	public List<Map> queryProductAttrInfo(ProductAttr productAttr){
		return pardProdDao.queryProductAttrInfo(productAttr);
	}
	public String queryProdAttrValue(Map map){
		StringBuffer attrValue = new StringBuffer();
		List<Map> attrValueList = pardProdDao.queryProdAttrValue(map);
		Map tmpMap=null;
		for (int i=0;i<attrValueList.size();i++){
			tmpMap=attrValueList.get(i);
			if(tmpMap.get("ATTR_VALUE_ID")!=null && !"".equals(tmpMap.get("ATTR_VALUE_ID").toString())){
				if("".equals(attrValue.toString())){
					attrValue.append(tmpMap.get("ATTR_VALUE_ID").toString());
				}else{
					attrValue.append(",").append(tmpMap.get("ATTR_VALUE_ID").toString());
				}
			}
		}
		return attrValue.toString();
		
	}
	public String queryProductAttrSeq(){
		return pardProdDao.queryProductAttrSeq();
	}
	public List<Product> compareProd(Product product){
		return pardProdDao.compareProd(product);
	}
	public PardProdDao getPardProdDao() {
		return pardProdDao;
	}
	
	public void setPardProdDao(PardProdDao pardProdDao) {
		this.pardProdDao = pardProdDao;
	}
    
    public List<Map> queryProductList(Map map){
		return pardProdDao.queryProductList(map);
	}
	public Integer countProductList(Map map){
		return pardProdDao.countProductList(map);
	}
	public Product selectProduct(Product product){
		return pardProdDao.selectProduct(product);
	}
	
	public Integer offShelfProduct(String productId){
		Map map=new HashMap();
		map.put("productId", productId);
		return pardProdDao.offShelfProduct(map);
	}
}