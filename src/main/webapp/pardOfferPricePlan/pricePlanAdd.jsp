<!-- BEGIN SECTION 1 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${APP_PATH}/pardOfferPricePlan/pricePlanAttr.js" type="text/javascript"></script> 

<script type="text/javascript">
var  _pricePlan 		= "${local['eaap.op2.portal.price.pricePlan']}";
var  _saveSuccess 	= "${local['eaap.op2.portal.price.message.saveSuccess']}";
var  _saveFail			= "${local['eaap.op2.portal.price.message.saveFail']}";
var  _priceName		= "${local['eaap.op2.portal.price.priceName']}";
var  _priceObjectprod = "${local['eaap.op2.portal.price.priceObject-prod']}";  
var  _validUnit		= "${local['eaap.op2.portal.price.validUnit']}";
var  _priority			= "${local['eaap.op2.portal.price.priority']}"; 
var  _isRequired		= " ${local['eaap.op2.portal.isRequired']}";
var  _noChargeInformation		= "${local['eaap.op2.portal.price.noChargeInformation']}";

var _priceObject		= "${local['eaap.op2.portal.price.priceObject']}";
var _priceUnit1 		= "${local['eaap.op2.portal.price.priceUnit1']}";
var _fee 					= "${local['eaap.op2.portal.price.fee']}";
var _priceSubject	= "${local['eaap.op2.portal.price.priceSubject']}";
var _timeRange		= "${local['eaap.op2.portal.price.ratingDiscount.timeRange']}";
var _serviceType		= "${local['eaap.op2.portal.price.serviceType']}";
var _reference 		= "${local['eaap.op2.portal.price.reference']}";
var _contrast 			= "${local['eaap.op2.portal.price.contrast']}";
var _maxDiscount	= "${local['eaap.op2.portal.price.maxDiscount']}";

var _freeresItem	= "${local['eaap.op2.portal.price.freeResourceItem']}";
var _userStateNames	= "${local['eaap.op2.portal.price.subscriberStatusEligibility']}";
var _forwardCycle	= "${local['eaap.op2.portal.price.forwardCycle']}";

$(document).ready(function(){
	pricePlanAttribute.init();
});
</script>
<div class="section clearfix">
  <h3 class="form-section mt5">${local["eaap.op2.portal.price.baseInfo"]}</h3>
  <div class="form-body">
  	<form id="pricePlanForm" name="pricePlanForm" action="${APP_PATH}/pardOfferPricePlan/savrOrUpdatePricePlan.shtml" method="post">
  	<input type="hidden" name="pricingTargetId" id="pricingTargetId" value="${pricingTargetId}"/>
  	<input type="hidden" name="pricingInfoId"  id="pricingInfoId" value="${pricingInfoId}"/>
  	<input type="hidden" name="pricingPlan.applicType"  id="applicType" value="1"/>
  	<input type="hidden" name="pricePlanSpecIds" id="pricePlanSpecIds" />
  	<input type="hidden" name="defaultVals" id="defaultVals" />
  	<input type="hidden" name="defaultValNames" id="defaultValNames" />
    <input type="hidden" name="actionType"  id="actionType" value="${actionType}"/>
							
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
      <div class="col-md-8">
        <input type="text"  name="pricingName" id="pricingName" value="${pricingPlan.pricingName }"   placeholder=""  class="form-control input-large"    ${actionType=='detail'?'disabled':''}  maxlength="100"/>
      </div>
    </div>
    
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceObject-prod"]}:</label>
      <div class="col-md-8 form-inline">
        <div class="input-icon right input-large dropdown"><i class="glyphicon glyphicon-tree-deciduous"></i>
          	<input type="text" class="form-control input-large dropdown-toggle2"  id="jstreeTextString" name="jstreeTextString"   ${actionType=='detail'?'disabled':''}>
			<input type="hidden" name="relIds"  id="relIds" value="${relIds}"/>
          	<div class="dropdown-menu jstree" role="menu">
            	<div class="pp"></div>
          	</div>
        </div>
      </div>
    </div>
    
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="${local['eaap.op2.portal.price.delayUnit.detail']}" data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.price.delayUnit"]}:</label>
      <div class="col-md-8 form-inline">      	
        <select class="form-control offsetType input-large"  id="subEffectMode" name="pricePlanLifeCycle.subEffectMode"     ${actionType=='detail'?'disabled':''}>
            <c:forEach var="offsetType" items="${activationOffsetTypeList}" varStatus="status">
				<option value="${offsetType.cepCode}" <c:if test="${offsetType.cepCode==pricePlanLifeCycle.subEffectMode}">selected</c:if>>${offsetType.cepName} </option>
       		</c:forEach>
        </select>       
        
        <label class="form-control  offset " style="border: 0px;">By</label> 
        <input type="text" class="form-control  offset"  placeholder=""  style="width:110px;" maxlength="9" 
			id="subDelayUnit" name="pricePlanLifeCycle.subDelayUnit"  ${actionType=='detail'?'disabled':''}   type="text"
			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${empty pricePlanLifeCycle.subDelayUnit }">value="1"</c:if>
			<c:if test="${not empty pricePlanLifeCycle.subDelayUnit }">value="${pricePlanLifeCycle.subDelayUnit }"</c:if>
		/>
        <select class="form-control offset"   id="subDelayCycle"  name="pricePlanLifeCycle.subDelayCycle"    ${actionType=='detail'?'disabled':''}>
            <c:forEach var="offset" items="${activationOffsetList}" varStatus="status">
				<option value="${offset.cepCode}"  <c:if test="${offset.cepCode==pricePlanLifeCycle.subDelayCycle}">selected</c:if> >${offset.cepName} </option>
       		</c:forEach>
        </select>
      </div>
    </div>
    
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="Effective length effective pricing plans, if choose the type of Fixed expiry date, it said that in this period of time." data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.price.validUnit"]}:</label>
      <div class="col-md-8 form-inline">
        <input type="text" class="form-control input-large effectiveTime1" style="width:160px;" placeholder=""
			id="validUnit"  name="pricePlanLifeCycle.validUnit"    ${actionType=='detail'?'disabled':''}   maxlength="9"
			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${empty pricePlanLifeCycle.validUnit }">value="36"</c:if>
			<c:if test="${not empty pricePlanLifeCycle.validUnit }">value="${pricePlanLifeCycle.validUnit }"</c:if>	/>
        <div class="input-group input-medium date date-picker effectiveTime2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d" style="display:none;">
	          <input type="text" class="form-control" readonly  id="fixedExpireDate">
	          <span class="input-group-btn">
	          <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
	          </span>
		</div>
        <select class="form-control unit"  id="vaildType" name="pricePlanLifeCycle.vaildType"    ${actionType=='detail'?'disabled':''}>
            <c:forEach var="validityPeriod" items="${pricePlanValidityPeriodList}" varStatus="status">
				<option value="${validityPeriod.cepCode}"  <c:if test="${validityPeriod.cepCode==pricePlanLifeCycle.vaildType}">selected</c:if>>${validityPeriod.cepName} </option>
       		</c:forEach>
        </select>
      </div>
    </div>
    <!-- 
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="Effective length effective pricing plans, if choose the type of Fixed expiry date, it said that in this period of time." data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> ${local["eaap.op2.portal.price.pricePlanSpec"]}:</label>
      <div class="col-md-8 form-inline"   id="pricePlanAttrListDiv">
				<c:if test="${not empty pricePlanAttrList }">
					<c:forEach var="p" items="${pricePlanAttrList }">
							<c:choose>
								<c:when test="${p.pageInType == 1 }">
							        <div class="input-group input-medium" id="" title="${p.attrSpecDesc }">
								        <span class="input-group-addon" ><input type="checkbox" id="pricePlanSpecId" class="" name="pricePlanSpecId"  value="${p.attrSpecId }"   <c:if test="${actionType=='update' || actionType=='detail'}">checked</c:if>   ${actionType=='detail'?'disabled':''}/></span>
							          	<input type="text" class="form-control" placeholder="${p.attrSpecName }"  id="defaultVal"  name="defaultVal"   value="${p.defaultValue }"  maxlength="22" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"    ${actionType=='detail'?'disabled':''}/>
							          	<input type="hidden" id="defaultValName" name="defaultValName" value="${p.defaultValueName }" />
							        </div>
								</c:when>
								<c:when test="${p.pageInType == 2 }">-->
									<!-- Rating Zone : -->
							      <!--  <div class="input-group input-medium" id="zoneDiv" title="${p.attrSpecDesc }">
								          <span class="input-group-addon" ><input type="checkbox" id="pricePlanSpecId" class="zoneFun" name="pricePlanSpecId"  value="${p.attrSpecId }"    <c:if test="${actionType=='update' || actionType=='detail'}">checked</c:if>     ${actionType=='detail'?'disabled':''}></span>
								          <input type="text" class="form-control" placeholder="${p.attrSpecName }"   readonly="true"  id="defaultValName" name="defaultValName"  value="${p.defaultValueName}"     ${actionType=='detail'?'disabled':''}/>
								          <input type="hidden"  id="defaultVal"  name="defaultVal"   value="${p.defaultValue }" />
								          <span class="input-group-btn"   style="display:<c:if test="${actionType=='detail'}">none</c:if>">
								          	<button type="button" class="btn theme-btn" id="zoneModal"><i class="fa fa-plus"></i></button>
								          </span>
							        </div>
								</c:when>
								<c:when test="${p.pageInType == 3 }"> -->
									<!-- Number of license: -->
							   <!--      <div class="input-group input-medium" id="numOfLicenseDiv" title="${p.attrSpecDesc }">
								        <span class="input-group-addon" ><input type="checkbox" id="pricePlanSpecId" class="numOfLicenseFun" name="pricePlanSpecId"  value="${p.attrSpecId }"   <c:if test="${actionType=='update' || actionType=='detail'}">checked</c:if>    ${actionType=='detail'?'disabled':''}/></span>
							          	<input type="text" class="form-control" placeholder="${p.attrSpecName }"   id="defaultVal"  name="defaultVal"   value="${p.defaultValue}"  maxlength="22" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"     ${actionType=='detail'?'disabled':''}/>
							          	<input type="hidden" id="defaultValName" name="defaultValName" value="${p.defaultValueName }" />
							        </div>
								</c:when>
								<c:when test="${p.pageInType == 4}">-->
										<!-- SLA: -->
								        <!-- <div class="input-group input-medium" id="SLADiv"  title="${p.attrSpecDesc }">
								        	<span class="input-group-addon" ><input type="checkbox" id="pricePlanSpecId" class="SLAFun" name="pricePlanSpecId"  value="${p.attrSpecId }"    <c:if test="${actionType=='update' || actionType=='detail'}">checked</c:if>   ${actionType=='detail'?'disabled':''}/></span>
								          	<select class="form-control"  id="defaultVal" name="defaultVal"    ${actionType=='detail'?'disabled':''}>
								          		<option value="" style="color:#cccccc;">SLA</option>
								          		<c:forEach var="sla" items="${serviceLevelAgreementList}" varStatus="status">
								          			<option value="${sla.cepCode}"  <c:if test="${sla.cepCode==p.defaultValue}">selected</c:if>>${sla.cepName}</option>
								          		</c:forEach>
								          	</select>
								          	<input type="hidden" id="defaultValName" name="defaultValName" value="${p.defaultValueName }" />
								        </div>
								</c:when>
							</c:choose>
					</c:forEach>
				</c:if>
      </div>
    </div>
    -->
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="Indicates the relative priority across all items of the same type where a value of 1 is the highest priority and a value of 10 would be the lowest priority item"
         data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priority"]}: </label>
      <div class="col-md-8 form-inline">
<!-- 		<input id="billingPriority" name="pricingPlan.billingPriority" class="form-control input-large"  type="text"  maxlength="9" -->
<!-- 			onkeyup="this.value=this.value.replace(/\D/g,'')" -->
<!-- 			onafterpaste="this.value=this.value.replace(/\D/g,'')" -->
<%-- 			<c:if test="${not empty pricingPlan.billingPriority }">value="${pricingPlan.billingPriority }"</c:if> --%>
<%-- 			<c:if test="${empty pricingPlan.billingPriority }">value="500"</c:if>  ${actionType=='detail'?'disabled':''}	/> --%>

 		<select class="form-control unit"  id="billingPriority" name="pricingPlan.billingPriority"    ${actionType=='detail'?'disabled':''}>
<%--             <c:forEach var="validityPeriod" items="${pricePlanValidityPeriodList}" varStatus="status"> --%>
<%-- 				<option value="${validityPeriod.cepCode}"  <c:if test="${validityPeriod.cepCode==pricePlanLifeCycle.vaildType}">selected</c:if>>${validityPeriod.cepName} </option> --%>
<%--        		</c:forEach> --%>
               <option value="1"  <c:if test="${billingPriorityValue==1}">selected</c:if>>1</option>
                <option value="2" <c:if test="${billingPriorityValue==2}">selected</c:if>>2</option>
                <option value="3"  <c:if test="${billingPriorityValue==3}">selected</c:if>>3</option>
                <option value="4"  <c:if test="${billingPriorityValue==4}">selected</c:if>>4</option>
                <option value="5"  <c:if test="${billingPriorityValue==5}">selected</c:if>>5</option>
                <option value="6"  <c:if test="${billingPriorityValue==6}">selected</c:if>>6</option>
                <option value="7"  <c:if test="${billingPriorityValue==7}">selected</c:if>>7</option>
                <option value="8"  <c:if test="${billingPriorityValue==8}">selected</c:if>>8</option>
                <option value="9" <c:if test="${billingPriorityValue==9}">selected</c:if>>9</option>
                <option value="10" <c:if test="${billingPriorityValue==10}">selected</c:if>>10</option>
        </select>
      </div>
    </div>
    
	<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-8">
			<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdatePricePlan(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
			</div>
	</div>
    </form>
  </div>
</div>
<!-- END SECTION 1 --> 

<!-- BEGIN SECTION 2 -->
<div id="chargeInformation" class="portlet"  style="display:${(actionType=='update'||actionType=='detail')?'block':'none'};">
	  <div style="margin:30px 0 25px!important;"  class="portlet-title" >
		    <div style="font-size: 24px; font-weight: 300 ! important;" class="caption"> ${local["eaap.op2.portal.price.feeInfo"]}</div>
		    <div class="actions dropdown"  style="display: ${actionType=='detail'?'none':'block'};">
		    	<a class="btn default btn-select" href="javascript:;"><i class="fa fa-check-square-o"></i> Select </a> 
		    	<a class="btn default btn-delete" href="javascript:;"><i class="fa fa-trash-o"></i> Del </a> 
		    	<a class="btn default" data-toggle="dropdown" href="javascript:;"> Add <i class="fa fa-angle-down"></i></a>
			      <ul class="dropdown-menu pull-right tpl" role="menu" data-set="addTarget">
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toBasicTariff.shtml?actionType=add&priceType=0" href="javascript:;"  data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="0"> Basic Tariff </a> </li>
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toRecurringCharge.shtml?actionType=add&priceType=7" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="7">Recurring Charge</a> </li>
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toRatingDiscount.shtml?actionType=add&priceType=5" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="5">Rating Discount</a> </li>
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toOnetimeCharge.shtml?actionType=add&priceType=9" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="9">One-Time Charge</a> </li>
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toBillingDiscount.shtml?actionType=add&priceType=8" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="8">Billing Discount</a> </li>
			        <li> <a data-url="${APP_PATH }/pardOfferPricePlan/toFreeResource.shtml?actionType=add&priceType=3" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="3">Free Resource</a> </li>
			      </ul>
		    </div>
	  </div>
	  <c:if test="${fn:length(componentPriceList)>=1}">
		  <div class="portlet-body tabsArea"  data-addtpl="true">
					<ul class="nav nav-tabs fix tabs-content-ajax  tab2Chil_TabUL">
						<c:forEach var="componentPrice" items="${componentPriceList}" varStatus="status"> 
							<li class="${1==status.count?'active':''}"   priceId="${componentPrice.priceId}">
								<c:if test="${componentPrice.priceType==0}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toBasicTariff.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<c:if test="${componentPrice.priceType==7}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toRecurringCharge.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<c:if test="${componentPrice.priceType==5}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toRatingDiscount.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<c:if test="${componentPrice.priceType==9}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toOnetimeCharge.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<c:if test="${componentPrice.priceType==8}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toBillingDiscount.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<c:if test="${componentPrice.priceType==3}">
									<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/pardOfferPricePlan/toFreeResource.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=1')" 
										data-toggle="tab"   data-key="${formNum}_${componentPrice.priceType}_${status.count}"  href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									</a>
								</c:if>
								<input type="checkbox" name="select" value="#tab2_${formNum}_${componentPrice.priceType}_${status.count}"/>
							</li>
			     		</c:forEach>
			    	</ul>
			 		<div class="tab-content">
				  		<c:forEach var="componentPrice" items="${componentPriceList}" varStatus="status"> 
							<div id="tab2_${formNum}_${componentPrice.priceType}_${status.count}" class="tab-pane  ${1==status.count?'active':''}" isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
						</c:forEach>
					</div>
		  </div>
	  </c:if>
	  <c:if test="${fn:length(componentPriceList)<1}">
		  <div class="portlet-body  tabsArea"  data-addtpl="false">
				<div class="well">
					<c:if test="${actionType!='detail'}">
						${local["eaap.op2.portal.price.noChargeInformation"]}
					</c:if>
					<c:if test="${actionType=='detail'}">
						${local["eaap.op2.portal.pardOffer.noData"]}
					</c:if>
				</div>
		  </div>
	  </c:if>
</div>
<!-- END SECTION 2 -->

<!-- selectZoneModal -->
<div id="selectZoneModal2" class="modal container fade" tabindex="-1" style="display: none;">
</div>
<!-- selectZoneModal -->
<div id="selectZoneModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header" style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local["eaap.op2.portal.attrSpec.selMap"]}</h4>
    <hr/>
    <div class="search-form-default">
      <form action="#" class="form-inline">
      	<div class="input-group input-medium mb5" > 
	        <input type="text" name="latitudeAndLongitude" id="latitudeAndLongitude" placeholder="Latitude And Longitude" class="form-control" value=""/>
	        <input type="hidden" id="lng" name="lng" value=""/>
	        <input type="hidden" id="lat" name="lat" value=""/>
	        <span class="input-group-btn">
	        	<a class="btn theme-btn btn-block showZone" href="#" data-height="500" data-width="700" title="Zone Map"
				   data-href="${APP_PATH }/pardOfferPricePlan/goToSelZoneMap.shtml" 
				   >&nbsp;<i class="fa fa-plus"></i>&nbsp;</a>
	        </span>
        </div>
        <input type="text" name="radius" id="radius" value="" placeholder="${local["eaap.op2.portal.price.timeSpec.radius"]} (km)" class="form-control"  title="${local["eaap.op2.portal.price.timeSpec.radius"]} (km)"/>
    	<input type="text" name="nameOrId" id="nameOrId" value="" placeholder="${local["eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode"]}" class="form-control"  title="${local["eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode"]}"/>
        <button class="btn default" type="button" id="zoneQuery">${local["eaap.op2.portal.devmgr.query"]}</button>
      </form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check"  id="zonesDT">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"></th>
          <th>${local["eaap.op2.portal.pardSpec.id"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.name"]}</th>
          <th>${local["eaap.op2.portal.price.timeSpec.longitude"]}</th>
          <th>${local["eaap.op2.portal.price.timeSpec.latitude"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.note"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default zoneCancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn zoneOK" >${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>