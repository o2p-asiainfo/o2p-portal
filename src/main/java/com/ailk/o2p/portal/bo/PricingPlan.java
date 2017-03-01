package com.ailk.o2p.portal.bo;
import java.util.Date;
import java.io.Serializable;

import javax.validation.constraints.Size;

import com.ailk.eaap.op2.bo.utc.DateConvertBeanImpl;
import com.ailk.eaap.op2.bo.utc.DateConvertField;

public class PricingPlan implements Serializable,DateConvertBeanImpl{
private static final long serialVersionUID = 1L;
	private Integer pricingInfoId;
	private String pricingClassify;
	private Integer pricingType;
	private String pricingDesc;
	private String beyondDesc;
	private String priceSign;
	private String priceCha;
	private Date createDate;
	private String statusCd;
	private Date statusDate;
	@DateConvertField
	private Date effDate;
	@DateConvertField
	private Date expDate;
	private String formatEffDate = DefaultVlueUtils.getNowDate(0);
	private String formatExpDate = DefaultVlueUtils.DEFAULT_VALUE_FOR_EXP;//getNowDate(1);
	//=====新曾属性
	@Size(min = 2, max = 256, message = "eaap.op2.portal.doc.notexceed20char")
	private String pricingName;
	private Integer licenseNbr;
	private String applicType;
	private Integer cycletype;
	private Integer cycleUnit;
	private String isMain;
	private Integer billingPriority;
	//================
	private Integer pricingTargetId;
	private Integer tenantId;

	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public void setPricingInfoId(Integer pricingInfoId){
		this.pricingInfoId=pricingInfoId;
	}
	public Integer getPricingInfoId(){
		return this.pricingInfoId;
	}
	public void setPricingClassify(String pricingClassify){
		this.pricingClassify=pricingClassify;
	}
	public String getPricingClassify(){
		return this.pricingClassify;
	}
	public void setPricingType(Integer pricingType){
		this.pricingType=pricingType;
	}
	public Integer getPricingType(){
		return this.pricingType;
	}
	public void setPricingDesc(String pricingDesc){
		this.pricingDesc=pricingDesc;
	}
	public String getPricingDesc(){
		return this.pricingDesc;
	}
	public void setBeyondDesc(String beyondDesc){
		this.beyondDesc=beyondDesc;
	}
	public String getBeyondDesc(){
		return this.beyondDesc;
	}
	public void setPriceSign(String priceSign){
		this.priceSign=priceSign;
	}
	public String getPriceSign(){
		return this.priceSign;
	}
	public void setPriceCha(String priceCha){
		this.priceCha=priceCha;
	}
	public String getPriceCha(){
		return this.priceCha;
	}
	public void setCreateDate(Date createDate){
		this.createDate=(Date) createDate.clone();
	}
	public Date getCreateDate(){
		return this.createDate;
	}
	public void setStatusCd(String statusCd){
		this.statusCd=statusCd;
	}
	public String getStatusCd(){
		return this.statusCd;
	}
	public void setStatusDate(Date statusDate){
		this.statusDate=(Date) statusDate.clone();
	}
	public Date getStatusDate(){
		return this.statusDate;
	}
	public void setEffDate(Date effDate){
		this.effDate= effDate;
	}
	public Date getEffDate(){
		return this.effDate;
	}
	public void setExpDate(Date expDate){
		this.expDate= expDate;
	}
	public Date getExpDate(){
		return this.expDate;
	}
	public String getApplicType() {
		return applicType;
	}
	public void setApplicType(String applicType) {
		this.applicType = applicType;
	}
	public String getIsMain() {
		return isMain;
	}
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	public String getFormatEffDate() {
		return formatEffDate;
	}
	public void setFormatEffDate(String formatEffDate) {
		this.formatEffDate = formatEffDate;
	}
	public String getFormatExpDate() {
		return formatExpDate;
	}
	public void setFormatExpDate(String formatExpDate) {
		this.formatExpDate = formatExpDate;
	}
	public String getPricingName() {
		return pricingName;
	}
	public void setPricingName(String pricingName) {
		this.pricingName = pricingName;
	}
	public Integer getLicenseNbr() {
		return licenseNbr;
	}
	public void setLicenseNbr(Integer licenseNbr) {
		this.licenseNbr = licenseNbr;
	}
	public Integer getCycletype() {
		return cycletype;
	}
	public void setCycletype(Integer cycletype) {
		this.cycletype = cycletype;
	}
	public Integer getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(Integer cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public Integer getPricingTargetId() {
		return pricingTargetId;
	}
	public void setPricingTargetId(Integer pricingTargetId) {
		this.pricingTargetId = pricingTargetId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getBillingPriority() {
		return billingPriority;
	}
	public void setBillingPriority(Integer billingPriority) {
		this.billingPriority = billingPriority;
	}
}
