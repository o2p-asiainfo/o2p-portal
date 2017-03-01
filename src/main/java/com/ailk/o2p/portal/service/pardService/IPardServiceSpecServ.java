package com.ailk.o2p.portal.service.pardService;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.ProductServiceRel;
import com.ailk.eaap.op2.bo.ServiceSpec;
import com.ailk.eaap.op2.bo.ServiceSpecAttrValue;

public interface IPardServiceSpecServ {
	
	public String getServiceSpecSeq();
	public String getServiceSpecRelSeq();
	public String getselectSeqServiceSpeceValueRelSeq();
	
	public ServiceSpec qryServiceSpeclistById(ServiceSpec serviceSpec);
	public List<ServiceSpec> qryServiceSpeclist(ServiceSpec serviceSpec);
	public List<ServiceSpec> serviceSpecificationCodeCheck(ServiceSpec serviceSpec);
	public List<ServiceSpec> qryServiceSpeclistByPer(ServiceSpec serviceSpec);
	public String cntServiceSpeclist(ServiceSpec serviceSpec);
	public void updateServiceSpec(ServiceSpec serviceSpec);
	public void addOrUpdateProductService(String productId,String serviceId);
	public ProductServiceRel getProductServiceRel(String productId);
	public List<Map<String,Object>> qryServiceSpecAttr(Map<String,Object> params);
	
	public void addServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue);
	public void delServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue);
	public List<ServiceSpecAttrValue> selectServiceSpecAttrValue(ServiceSpecAttrValue serviceSpecAttrValue);
	public String qryServiceSpecAttrValue(String serviceSpecAttrValueId);
	
	public String addOrUpdateServiceSpec(String serviceId, ServiceSpec serviceSpec,String params)throws Exception;
	
}