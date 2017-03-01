package com.ailk.o2p.portal.bo;
import java.util.Date;
import java.io.Serializable;

import com.ailk.eaap.op2.bo.ContractVersion;
public class ServiceOwner implements Serializable{
private static final long serialVersionUID = 1L;
	private int serviceId;
	private String contractVersionId;
	private String serviceCnName;
	private String serviceEnName;
	private String serviceCode;
	private String serviceType;
	private String serviceVersion;
	private Date createDate;
	private String state;
	private String defaultMsgFlow;                              
	private Date lastestDate;
	private String serviceDesc;
	private String isPublished;
	private String servicePriority;
	private Integer serviceTimeout;
	private ContractVersion contractVersion;
	private String busCode;
	private String serviceRelaType;
	private ServiceOwner relaService;
	private Integer tenantId;

	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getDefaultMsgFlow() {
		return defaultMsgFlow;
	}
	public void setDefaultMsgFlow(String defaultMsgFlow) {
		this.defaultMsgFlow = defaultMsgFlow;
	}
	public String getServiceRelaType() {
		return serviceRelaType;
	}
	public void setServiceRelaType(String serviceRelaType) {
		this.serviceRelaType = serviceRelaType;
	}
	public ServiceOwner getRelaService() {
		return relaService;
	}
	public void setRelaService(ServiceOwner relaService) {
		this.relaService = relaService;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public ContractVersion getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(ContractVersion contractVersion) {
		this.contractVersion = contractVersion;
	}

	public void setContractVersionId(String contractVersionId){
		this.contractVersionId=contractVersionId;
	}
	public String getContractVersionId(){
		return this.contractVersionId;
	}
	public void setServiceCnName(String serviceCnName){
		this.serviceCnName=serviceCnName;
	}
	public String getServiceCnName(){
		return this.serviceCnName;
	}
	public void setServiceEnName(String serviceEnName){
		this.serviceEnName=serviceEnName;
	}
	public String getServiceEnName(){
		return this.serviceEnName;
	}
	public void setServiceCode(String serviceCode){
		this.serviceCode=serviceCode;
	}
	public String getServiceCode(){
		return this.serviceCode;
	}
	public void setServiceType(String serviceType){
		this.serviceType=serviceType;
	}
	public String getServiceType(){
		return this.serviceType;
	}
	public void setServiceVersion(String serviceVersion){
		this.serviceVersion=serviceVersion;
	}
	public String getServiceVersion(){
		return this.serviceVersion;
	}
	public void setCreateDate(Date createDate){
		this.createDate=(Date) createDate.clone();
	}
	public Date getCreateDate(){
		return this.createDate;
	}
	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return this.state;
	}
	public void setLastestDate(Date lastestDate){
		this.lastestDate=(Date) lastestDate.clone();
	}
	public Date getLastestDate(){
		return this.lastestDate;
	}
	public void setServiceDesc(String serviceDesc){
		this.serviceDesc=serviceDesc;
	}
	public String getServiceDesc(){
		return this.serviceDesc;
	}
	public void setIsPublished(String isPublished){
		this.isPublished=isPublished;
	}
	public String getIsPublished(){
		return this.isPublished;
	}
	public void setServicePriority(String servicePriority){
		this.servicePriority=servicePriority;
	}
	public String getServicePriority(){
		return this.servicePriority;
	}
	public void setServiceTimeout(Integer serviceTimeout){
		this.serviceTimeout=serviceTimeout;
	}
	public Integer getServiceTimeout(){
		return this.serviceTimeout;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
}
