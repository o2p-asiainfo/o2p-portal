package com.ailk.o2p.portal.service.pardService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.ProductServiceRel;
import com.ailk.eaap.op2.bo.ServiceSpec;
import com.ailk.eaap.op2.bo.ServiceSpecAttr;
import com.ailk.eaap.op2.bo.ServiceSpecAttrValue;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.ProductServiceDAO;
import com.ailk.eaap.op2.dao.ServiceSpecDao;
import com.ailk.o2p.portal.dao.pardSpec.IPardSpecDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;

@Service
@Transactional
public class PardServiceSpecServ implements IPardServiceSpecServ {
	@Autowired
	private ServiceSpecDao serviceSpecDao;
	@Autowired
	private ProductServiceDAO productServiceDao;
	@Autowired
	private IPardSpecDao pardSpecDao ;
	
	public String getServiceSpecSeq(){
		return serviceSpecDao.getServiceSpecSeq();
	}
	public String getServiceSpecRelSeq(){
		return serviceSpecDao.getServiceSpecAttrRelSeq();
	}
	public String getselectSeqServiceSpeceValueRelSeq(){
		return serviceSpecDao.getselectSeqServiceSpeceValueRelSeq();
	}
	
	public List<ServiceSpec> qryServiceSpeclist(ServiceSpec serviceSpec) {
		serviceSpec.setCooperationType(EAAPConstants.SERVICE_SPEC_TYPE_PRIVATE);
		return serviceSpecDao.qryServiceSpeclist(serviceSpec);
	}
	
	public List<ServiceSpec> serviceSpecificationCodeCheck(ServiceSpec serviceSpec) {
		return serviceSpecDao.serviceSpecificationCodeCheck(serviceSpec);
	}
	
	public List<ServiceSpec> qryServiceSpeclistByPer(ServiceSpec serviceSpec){
		return serviceSpecDao.selectServiceSpecByPer(serviceSpec);
	}

	public String cntServiceSpeclist(ServiceSpec serviceSpec) {
		return serviceSpecDao.cntServiceSpeclist(serviceSpec);
	}
	
	public ServiceSpec qryServiceSpeclistById(ServiceSpec serviceSpec){
		return serviceSpecDao.qryServiceSpeclistById(serviceSpec);
	}
	
	public void addOrUpdateProductService(String productId,String serviceId){
		if(null!=productId && null!=serviceId){
			ProductServiceRel productServiceRel = new ProductServiceRel();
			productServiceRel.setProductId(productId);
			productServiceRel.setStatus(EAAPConstants.COMM_STATE_VALID);
			
			ProductServiceRel productServiceRelTemp = productServiceDao.queryProductService(productServiceRel);
			productServiceRel.setServiceId(serviceId);
			if(null != productServiceRelTemp){
				productServiceDao.updateProductService(productServiceRel);
			}else{
				productServiceDao.insertProductService(productServiceRel);
			}
			
		}else{
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"O2P service --> productId and serviceId is not empty!", null);
		}
	}
	
	public ProductServiceRel getProductServiceRel(String productId){
		ProductServiceRel productServiceRel = new ProductServiceRel();
		if(null!=productId){
			productServiceRel.setProductId(productId);
			productServiceRel.setStatus(EAAPConstants.COMM_STATE_VALID);
			productServiceRel = productServiceDao.queryProductService(productServiceRel);
		}
		return productServiceRel;
	}
	
	public List<Map<String,Object>> qryServiceSpecAttr(Map<String,Object> params){
		return serviceSpecDao.qryServiceSpecAttr(params);
	}
	
	public void addServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue){
		serviceSpecDao.addServiceSpecAttrValue(serviceSpecAttrValue);
	}
	
	public void delServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue){
		serviceSpecDao.delServiceSpecAttrValue(serviceSpecAttrValue);
	}
	
	public List<ServiceSpecAttrValue> selectServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue){
		return serviceSpecDao.selectServiceSpecAttrValue(serviceSpecAttrValue);
	}
	
	public String qryServiceSpecAttrValue(String serviceSpecAttrValueId){
		ServiceSpecAttrValue serviceSpecAttrValue = new ServiceSpecAttrValue();
		serviceSpecAttrValue.setServiceSpecAttrId(Integer.parseInt(serviceSpecAttrValueId));
		List<ServiceSpecAttrValue> serviceSpecAttrValueList = this.selectServiceSpecAttrValue(serviceSpecAttrValue);
		
		StringBuffer sb = new StringBuffer();
		for(ServiceSpecAttrValue servSpecAttrValue : serviceSpecAttrValueList){
			sb.append(servSpecAttrValue.getAttrValueId()).append(",");
		}
		return sb.toString();
	}
	
	public void delServiceSpecAttr(String serviceId){
		ServiceSpecAttr serviceSpecAttr = new ServiceSpecAttr();
		serviceSpecAttr.setServiceId(new BigDecimal(serviceId));
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("statusCD", EAAPConstants.PRODUCT_ATTR_ONLINE);
		params.put("serviceId", serviceId);
		List<Map<String, Object>> serviceSpecAttrList = this.qryServiceSpecAttr(params);
		ServiceSpecAttrValue serviceSpecAttrValue = null;
		for(Map<String, Object> map : serviceSpecAttrList){
			serviceSpecAttrValue = new ServiceSpecAttrValue();
			serviceSpecAttrValue.setServiceSpecAttrId((Integer) map.get("serviceSpecAttrId".toUpperCase()));
			
			serviceSpecDao.delServiceSpecAttrValue(serviceSpecAttrValue);
		}
		
		serviceSpecDao.delServiceSpecAttr(serviceSpecAttr);
	}
	
	public void updateServiceSpec(ServiceSpec serviceSpec){
		serviceSpecDao.updateServiceSpec(serviceSpec);
	}
	
	public String addOrUpdateServiceSpec(String serviceId, ServiceSpec serviceSpec,String params)throws Exception{
		//update
		if(StringUtils.isNotEmpty(serviceId)){
			this.delServiceSpecAttr(serviceId);
			
			serviceSpec.setServiceId(new BigDecimal(serviceId));
			serviceSpecDao.updateServiceSpec(serviceSpec);
			
			this.addServiceSpecAttr(serviceId,params);
		}else{
			serviceId = serviceSpecDao.getServiceSpecSeq();
			this.addServiceSpecAttr(serviceId,params);
			
			serviceSpec.setServiceId(new BigDecimal(serviceId));
			serviceSpecDao.addServiceSpec(serviceSpec);
		}
		return serviceId;
	}
	
	private void addServiceSpecAttr(String serviceId,String params)throws Exception{
		if (StringUtils.isNotEmpty(params)){
			String serviceSpecAttrRelSeq = null;
			
			String[] paramArr = params.split(";");
			String[] p = null;
			ServiceSpecAttr serviceSpecAttr = null;		
    		String chooseAttrSpecCode=null;
    		String specMaintainType=null;
    		String specType=null;
    		String attrSpecId=null;
    		String defaultValue=null;
    		String mappingId=null;
    		Map checkCountMap=new HashMap();
    		Map specTypeIdsMap=new HashMap();
    		int count=0;
    		Map paraMap=null;
    		Map checkCntMap=new HashMap();
    		String needCheckCodeNum=WebPropertyUtil.getPropertyValue("need_check_code_num");
			for(String param : paramArr){
				p = param.split(",",-1);
				if(p.length!=5){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"O2P service --> system error!", null);
				}
				chooseAttrSpecCode=p[3];
	    		specMaintainType=p[4];
	    		specType=p[1];
	    		//效验CODE是否唯一
	    		if(needCheckCodeNum!=null&&"1".equals(needCheckCodeNum)){
	    			if(checkCntMap.get(chooseAttrSpecCode)==null){
	        			checkCntMap.put(chooseAttrSpecCode, chooseAttrSpecCode);
	        		}else{
	        			throw new BusinessException(ExceptionCommon.WebExceptionCode,"Attribute code of the service cannot be the same!", null);
	        		}
	    		}
	    		
	    		if("2".equals(specMaintainType)){//合作伙伴自定义的属性
	    			if(checkCountMap.get(specType)==null){
	    				checkCountMap.put(specType, 1);
	    				String pageInTypeName=getPageInTypeName(specType);
	    				paraMap=new HashMap();
	    				paraMap.put("pageInTypeName", pageInTypeName);
	    				specTypeIdsMap.put(specType, pardSpecDao.getPageInTypeIds(paraMap).get(0));//查询ID段定义信息
	    			}else{
	    				count=Integer.parseInt(checkCountMap.get(specType).toString());
	    				checkCountMap.put(specType, ++count);
	    			}
	    		}else{
	    			continue;
	    		}
			}
			//效验数量
			Iterator<Map.Entry<String, String>> it = checkCountMap.entrySet().iterator();
			Map.Entry<String, String> entry = null;
			Map pageInTypeMap=null;
			int max=0;
			int length=0;
			int next=0;
			while (it.hasNext()) {
			   entry = it.next();
			   count=Integer.parseInt(String.valueOf(entry.getValue()));
			   pageInTypeMap=(Map)specTypeIdsMap.get(entry.getKey());
			   length=Integer.parseInt(String.valueOf(pageInTypeMap.get("LENGTH")));
			   if(count>length){
				   throw new BusinessException(ExceptionCommon.WebExceptionCode,"Above Proof!", null);
			   }
			}
			checkCountMap.clear();
			for(String param : paramArr){
				p = param.split(",",-1);
				serviceSpecAttrRelSeq = serviceSpecDao.getServiceSpecAttrRelSeq();
				serviceSpecAttr = new ServiceSpecAttr();
				if(p.length==5){
					serviceSpecAttr.setDefaultValue(p[2]);
				}else if(p.length>5){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,"O2P service --> system error!", null);
				}else{
					serviceSpecAttr.setDefaultValue(null);
				}
				
	    		specType=p[1];
	    		attrSpecId=p[0];
	    		defaultValue=p[2];
	    		chooseAttrSpecCode=p[3];
	    		specMaintainType=p[4];
	    		if("2".equals(specMaintainType)){//合作伙伴自定义的属性 mappingid为预分配的值 nzh
	    			if(checkCountMap.get(specType)==null){
	    				pageInTypeMap=(Map)specTypeIdsMap.get(specType);
	    				checkCountMap.put(specType, pageInTypeMap.get("MAX"));
	    			}else{
	    				count=Integer.parseInt(checkCountMap.get(specType).toString());
	    				checkCountMap.put(specType, ++count);
	    			}
	    			mappingId=String.valueOf(checkCountMap.get(specType));
	    		}else if("3".equals(specMaintainType)){//公用属性 mappingid为属性id nzh
	    			String pageInTypeName="SEQ_ATTR_SPEC_"+chooseAttrSpecCode.toUpperCase();
    				paraMap=new HashMap();
    				paraMap.put("pageInTypeName", pageInTypeName);
	    			mappingId=((Map)pardSpecDao.getPageInTypeIds(paraMap).get(0)).get("MAX").toString();
	    		}else{
	    			mappingId=chooseAttrSpecCode;
	    		}
	    		
				serviceSpecAttr.setServiceSpecAttrRelId(Integer.parseInt(serviceSpecAttrRelSeq));
				serviceSpecAttr.setAttrSpecId(new BigDecimal(p[0]));
				serviceSpecAttr.setStatusCd(EAAPConstants.PRODUCT_ATTR_ONLINE);
				serviceSpecAttr.setServiceId(new BigDecimal(serviceId));
				serviceSpecAttr.setMappingId(mappingId);
					
				if("4".equals(p[1])){
					if(p.length==5){
						String serviceAttrValStr = p[2];
						if(serviceAttrValStr!=null&&!"".equals(serviceAttrValStr.trim())){
							String[] serviceAttrValArr = serviceAttrValStr.split("/");
							
							ServiceSpecAttrValue serviceSpecAttrValue = null;
							for(String serviceAttrVal : serviceAttrValArr){
								serviceSpecAttrValue = new ServiceSpecAttrValue();
								serviceSpecAttrValue.setServiceSpecAttrValueId(serviceSpecDao.getselectSeqServiceSpeceValueRelSeq());
								serviceSpecAttrValue.setServiceSpecAttrId(Integer.parseInt(serviceSpecAttrRelSeq));
								serviceSpecAttrValue.setAttrValueId(Integer.parseInt(serviceAttrVal));
								serviceSpecDao.addServiceSpecAttrValue(serviceSpecAttrValue);
							}
						}
					}
				}
				serviceSpecDao.addServiceSpecAttr(serviceSpecAttr);
			}
		}
	}
	private String getPageInTypeName(String pageInType){
		String pageInTypeName=null;
		switch(Integer.parseInt(pageInType)){
			case 1:pageInTypeName="SEQ_ATTR_SPEC_TEXT";break;
			case 2:pageInTypeName="SEQ_ATTR_SPEC_PASSWORD";break;
			case 3:pageInTypeName="SEQ_ATTR_SPEC_NUMBER";break;
			case 4:pageInTypeName="SEQ_ATTR_SPEC_COMBOX";break;
			case 5:pageInTypeName="SEQ_ATTR_SPEC_DATE";break;
			case 6:pageInTypeName="SEQ_ATTR_SPEC_EMAIL";break;
			case 7:pageInTypeName="SEQ_ATTR_SPEC_READONLY_TEXT";break;
		}
		return pageInTypeName;
	}
}

