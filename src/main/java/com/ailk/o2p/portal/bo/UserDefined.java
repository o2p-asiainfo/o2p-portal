package com.ailk.o2p.portal.bo;

import java.util.Arrays;

import javax.validation.constraints.Size;

public class UserDefined {
	//业务名称
	private String bizName;	
	//服务名称
	@Size(min = 4, max = 50, message = "eaap.op2.portal.must4To50Char")
	private String serviceName;	
	//目录Id
	private String directoryId;
	private String publicDirectoryId;
	private String isPublic;
	//请求地址
	@Size(min =1, max = 250, message = "eaap.op2.portal.must1To250Char")
	private String attrSpecValue;
	//协议请求格式
	private String conType;
	//通讯协议
	private String commProcd;
	//协议结构
	private String xsdFormat;
	//协议样例
	private String xsdDemo;		
	//介绍
	@Size(min = 0, max = 1000, message = "eaap.op2.portal.notExceed1000Char")
	private String descriptor;	
	//组件ID
	private String componentId;
	
	private String techImpAttId;
	
	private String techImpId;
	
	private String tcpCtrFid;
	//协议ID
	private String contractId;	
	//协议版本ID
	private String contractVersionId;
	
	private String dirSerlistId;
	//服务ID
	private String serviceId;
	//协议编码
	@Size(min = 4, max = 50, message = "eaap.op2.portal.must4To50Char")
	private String contractCode;
	//协议版本编码
	@Size(min = 4, max = 50, message = "eaap.op2.portal.must4To50Char")
	private String contractVersionCode;
	//请求或者响应
	private String reqRsp;
	
	private String fileShareId; 
	
	private String isNeedCheck[];
	
	private String nodeLength[];
	
	private String nodeNumber[];
	
	private String nevlConsType[];
	
	private String nelConsValue[];
	
	private String nevlConsDesc[];
	
	private String nodeDescId[];	

	private String nodeTypeCons[];

	private String javaField[];
	
	private String nodeType[];
	
	private String[] dyAttrSpecIds;
	private String[] dyAttrSpecValues;
	//协议头结构
	private String xsdHeadFormat;
	
	/**
	 * 租户ID
	 */
	private Integer tenantId;

	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getPublicDirectoryId() {
		return publicDirectoryId;
	}
	public void setPublicDirectoryId(String publicDirectoryId) {
		this.publicDirectoryId = publicDirectoryId;
	}
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public String getXsdHeadFormat() {
		return xsdHeadFormat;
	}
	public void setXsdHeadFormat(String xsdHeadFormat) {
		this.xsdHeadFormat = xsdHeadFormat;
	}
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getAttrSpecValue() {
		return attrSpecValue;
	}
	public void setAttrSpecValue(String attrSpecValue) {
		this.attrSpecValue = attrSpecValue;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getCommProcd() {
		return commProcd;
	}
	public void setCommProcd(String commProcd) {
		this.commProcd = commProcd;
	}
	public String getXsdFormat() {
		return xsdFormat;
	}
	public void setXsdFormat(String xsdFormat) {
		this.xsdFormat = xsdFormat;
	}
	public String getXsdDemo() {
		return xsdDemo;
	}
	public void setXsdDemo(String xsdDemo) {
		this.xsdDemo = xsdDemo;
	}
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String[] getNodeLength() {
		if(null != nodeLength){
			return Arrays.copyOf(nodeLength,nodeLength.length);
		}else{
			return null;
		}
	}
	public void setNodeLength(String[] nodeLength) {
		if(null != nodeLength){
			this.nodeLength=Arrays.copyOf(nodeLength,nodeLength.length); 
		}
	}
	public String[] getNodeNumber() {
		if(null != nodeNumber){
			return Arrays.copyOf(nodeNumber,nodeNumber.length);
		}else{
			return null;
		}
	}
	public void setNodeNumber(String[] nodeNumber) {
		if(null !=nodeNumber){
			this.nodeNumber=Arrays.copyOf(nodeNumber,nodeNumber.length); 
		}
	}
	public String[] getNevlConsType() {
		if(null != nevlConsType){
			return Arrays.copyOf(nevlConsType,nevlConsType.length);
		}else{
			return null;
		}
	}
	public void setNevlConsType(String[] nevlConsType) {
		if(null !=  nevlConsType){
			this.nevlConsType=Arrays.copyOf(nevlConsType,nevlConsType.length); 
		}
	}
	public String[] getNelConsValue() {
		if(null != nelConsValue){
			return Arrays.copyOf(nelConsValue,nelConsValue.length);  
		}else{
			return null;
		}
	}
	public void setNelConsValue(String[] nelConsValue) {
		if(null !=  nelConsValue){
			this.nelConsValue=Arrays.copyOf(nelConsValue,nelConsValue.length); 
		}
	}
	public String[] getNevlConsDesc() {
		if(null != nevlConsDesc){
			return Arrays.copyOf(nevlConsDesc,nevlConsDesc.length); 
		}else{
			return null;
		}
	}
	public void setNevlConsDesc(String[] nevlConsDesc) {
		if(null !=  nevlConsDesc){
			this.nevlConsDesc=Arrays.copyOf(nevlConsDesc,nevlConsDesc.length); 
		}
	}
	public String[] getNodeDescId() {
		if(null != nodeDescId){
			return Arrays.copyOf(nodeDescId,nodeDescId.length); 
		}else{
			return null;
		}
	}
	public void setNodeDescId(String[] nodeDescId) {
		if(null !=  nodeDescId){
			this.nodeDescId=Arrays.copyOf(nodeDescId,nodeDescId.length); 
		}
	}
	public String[] getJavaField() {
		if(null != javaField){
			return Arrays.copyOf(javaField,javaField.length); 
		}else{
			return null;
		}
	}
	public void setJavaField(String[] javaField) {
		if(null !=  javaField){
			this.javaField=Arrays.copyOf(javaField,javaField.length); 
		}
	}
	public String[] getNodeType() {
		if(null != nodeType){
			return Arrays.copyOf(nodeType,nodeType.length); 
		}else{
			return null;
		}
	}
	public void setNodeType(String[] nodeType) {
		if(null !=  nodeType){
			this.nodeType=Arrays.copyOf(nodeType,nodeType.length); 
		}
	}
	
	public String getTechImpAttId() {
		return techImpAttId;
	}
	public void setTechImpAttId(String techImpAttId) {
		this.techImpAttId = techImpAttId;
	}
	public String getTcpCtrFid() {
		return tcpCtrFid;
	}
	public void setTcpCtrFid(String tcpCtrFid) {
		this.tcpCtrFid = tcpCtrFid;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getDirSerlistId() {
		return dirSerlistId;
	}
	public void setDirSerlistId(String dirSerlistId) {
		this.dirSerlistId = dirSerlistId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String[] getIsNeedCheck() {
		return Arrays.copyOf(isNeedCheck,isNeedCheck.length);
	}
	public void setIsNeedCheck(String[] isNeedCheck) {
		this.isNeedCheck=Arrays.copyOf(isNeedCheck,isNeedCheck.length);
	}
	public String[] getNodeTypeCons() {
		return Arrays.copyOf(nodeTypeCons,nodeTypeCons.length);
	}
	public void setNodeTypeCons(String[] nodeTypeCons) {
		this.nodeTypeCons=Arrays.copyOf(nodeTypeCons,nodeTypeCons.length);
	}
	public String[] getDyAttrSpecIds() {
		if(null != dyAttrSpecIds){
			return Arrays.copyOf(dyAttrSpecIds,dyAttrSpecIds.length); 
		}else{
			return null;
		}
	}
	public void setDyAttrSpecIds(String[] dyAttrSpecIds) {
		if(null !=  dyAttrSpecIds){
			this.dyAttrSpecIds=Arrays.copyOf(dyAttrSpecIds,dyAttrSpecIds.length); 
		}
	}
	public String[] getDyAttrSpecValues() {
		if(null != dyAttrSpecValues){
			return Arrays.copyOf(dyAttrSpecValues,dyAttrSpecValues.length); 
		}else{
			return null;
		}
	}
	public void setDyAttrSpecValues(String[] dyAttrSpecValues) {
		if(null !=  dyAttrSpecValues){
			this.dyAttrSpecValues=Arrays.copyOf(dyAttrSpecValues,dyAttrSpecValues.length); 
		}
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getReqRsp() {
		return reqRsp;
	}
	public void setReqRsp(String reqRsp) {
		this.reqRsp = reqRsp;
	}
	public String getContractVersionId() {
		return contractVersionId;
	}
	public void setContractVersionId(String contractVersionId) {
		this.contractVersionId = contractVersionId;
	}
	public String getTechImpId() {
		return techImpId;
	}
	public void setTechImpId(String techImpId) {
		this.techImpId = techImpId;
	}
	public String getContractVersionCode() {
		return contractVersionCode;
	}
	public void setContractVersionCode(String contractVersionCode) {
		this.contractVersionCode = contractVersionCode;
	}
	public String getFileShareId() {
		return fileShareId;
	}
	public void setFileShareId(String fileShareId) {
		this.fileShareId = fileShareId;
	}
	
}
