package com.ailk.o2p.portal.bo;
import java.util.Date;
import java.io.Serializable;

import javax.validation.constraints.Size;

import com.asiainfo.integration.o2p.web.bo.Org;
public class Component implements Serializable{
private static final long serialVersionUID = 1L;
	private String componentId;
	private Integer orgId;
	private String code;
	@Size(min = 2, max = 40, message = "eaap.op2.portal.doc.notexceed20char")
	private String name;
	private Integer componentTypeId;
	private Date regTime;
	private String state;
	private Date stateTime;
	private String password;
	private String descriptor;
	private Integer sfileId ;
	private Org org;
	private String auditFlowId;
	
	private String orgCode;
	private String orgState;
	
	private String upState;
	
	private Integer tenantId;

	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public Component(){
		super() ;
	}
	
	public Component(Integer orgId ){
		this.orgId = orgId ; 
	}
	
	public void setComponentId(String componentId){
		this.componentId=componentId;
	}
	public String getComponentId(){
		return this.componentId;
	}
	public void setOrgId(Integer orgId){
		this.orgId=orgId;
	}
	public Integer getOrgId(){
		return this.orgId;
	}
	public void setCode(String code){
		this.code=code;
	}
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCode(){
		return this.code;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setComponentTypeId(Integer componentTypeId){
		this.componentTypeId=componentTypeId;
	}
	public Integer getComponentTypeId(){
		return this.componentTypeId;
	}
	public void setRegTime(Date regTime){
		this.regTime=(Date) regTime.clone();
	}
	public Date getRegTime(){
		return this.regTime;
	}
	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return this.state;
	}
	public void setStateTime(Date stateTime){
		this.stateTime=(Date) stateTime.clone();
	}
	public Date getStateTime(){
		return this.stateTime;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setDescriptor(String descriptor){
		this.descriptor=descriptor;
	}
	public String getDescriptor(){
		return this.descriptor;
	}
	public Integer getSfileId() {
		return sfileId;
	}
	public void setSfileId(Integer sfileId) {
		this.sfileId = sfileId;
	}
	public Org getOrg() {
		return org;
	}
	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}
	public String getAuditFlowId() {
		return auditFlowId;
	}
	public void setAuditFlowId(String auditFlowId) {
		this.auditFlowId = auditFlowId;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getUpState() {
		return upState;
	}

	public void setUpState(String upState) {
		this.upState = upState;
	}
}
