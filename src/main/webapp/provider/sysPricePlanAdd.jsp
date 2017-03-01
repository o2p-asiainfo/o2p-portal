<!-- BEGIN SECTION 1 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${APP_PATH}/provider/sysPricePlanAttr.js" type="text/javascript"></script> 
<script src="${APP_PATH}/provider/sysPricePlanAdd.js" type="text/javascript"></script> 

<script type="text/javascript">
var  _pricePlan = "${local['eaap.op2.portal.price.pricePlan']}";
$(document).ready(function(){
	pricePlanAttribute.init();

});
</script>
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
  <h4 class="modal-title">Price Plan</h4>
</div>
<div class="modal-body form-horizontal">
  <h3 class="form-section mt5">Base Information</h3>
  <div class="form-body">
  	<form id="pricePlanForm" name="pricePlanForm" action="${APP_PATH}/pardOfferPricePlan/savrOrUpdatePricePlan.shtml" method="post">
  	<input type="hidden" name="pricingTargetId" id="pricingTargetId" value="${pricingTargetId}"/>
  	<input type="hidden" name="pricingPlan.pricingInfoId"  id="pricingInfoId" value="${pricingPlan.pricingInfoId}"/>
  	<input type="hidden" name="pricingPlan.applicType"  id="applicType" value="1"/>
  	<input type="hidden" name="pricePlanSpecIds" id="pricePlanSpecIds" />
  	<input type="hidden" name="defaultVals" id="defaultVals" />
  	<input type="hidden" name="defaultValNames" id="defaultValNames" />
  	<input type="hidden" name="actionType"  id="actionType" value="${actionType}"/>
    <input type="hidden" name="relIds"  id="relIds" value = "${relIds}"/>
	<input type="hidden" name="state"  id="state" value = "${state}"/>
							 
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
      <div class="col-md-8">
        <input type="text" ${readonly} name="pricingPlan.pricingName" id="pricingName" value="${pricingPlan.pricingName }"   placeholder=""  class="form-control input-medium" />
      </div>
    </div>
    
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="${local['eaap.op2.portal.price.delayUnit.detail']}" data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.price.delayUnit"]}:</label>
      <div class="col-md-8 form-inline">
      	
        <select class="form-control offsetType" ${readonly}  id="subEffectMode" name="pricePlanLifeCycle.subEffectMode"   value="${pricePlanLifeCycle.subEffectMode}">
            <c:forEach var="offsetType" items="${activationOffsetTypeList}" varStatus="status">
				<option value="${offsetType.cepCode}" <c:if test="${offsetType.cepCode==pricePlanLifeCycle.subEffectMode}">selected</c:if>>${offsetType.cepName} </option>
       		
       		</c:forEach>
        </select>
        <label class="form-control  offset control-label" style="border: 0px;">By</label> 
        <input type="text" ${readonly} class="form-control input-xsmall offset" placeholder="" 
			id="subDelayUnit" name="pricePlanLifeCycle.subDelayUnit"  size="3"  type="text"
			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${empty pricePlanLifeCycle.subDelayUnit }">value="1"</c:if>
			<c:if test="${not empty pricePlanLifeCycle.subDelayUnit }">value="${pricePlanLifeCycle.subDelayUnit }"</c:if>
		/>
        <select class="form-control offset" ${readonly}   id="subDelayCycle"  name="pricePlanLifeCycle.subDelayCycle"  >
            <c:forEach var="offset" items="${activationOffsetList}" varStatus="status">
				<option value="${offset.cepCode}"  <c:if test="${offset.cepCode==pricePlanLifeCycle.subDelayCycle}">selected</c:if> >${offset.cepName} </option>
       		</c:forEach>
        </select>
      </div>
    </div>
    
    <div class="form-group">
      <label class="col-md-4 control-label"><i title="Effective length effective pricing plans, if choose the type of Fixed expiry date, it said that in this period of time." data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.price.validUnit"]}:</label>
      <div class="col-md-8 form-inline">
        <input type="text" ${readonly} class="form-control input-medium effectiveTime1" placeholder=""
			id="validUnit"  name="pricePlanLifeCycle.validUnit"  size="22"
			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${empty pricePlanLifeCycle.validUnit }">value="36"</c:if>
			<c:if test="${not empty pricePlanLifeCycle.validUnit }">value="${pricePlanLifeCycle.validUnit }"</c:if>	/>
        <div ${readonly} class="input-group input-medium date date-picker effectiveTime2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d" style="display:none;">
	          <input type="text" class="form-control" readonly  id="fixedExpireDate">
	          <span class="input-group-btn">
	          <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
	          </span>
		</div>
        <select class="form-control unit" ${readonly}  id="vaildType" name="pricePlanLifeCycle.vaildType"  value="${pricePlanLifeCycle.vaildType }" >
            <c:forEach var="validityPeriod" items="${pricePlanValidityPeriodList}" varStatus="status">
            <c:if test="${empty pricePlanLifeCycle.vaildType }"> 
				<c:if test="${validityPeriod.cepCode==3}">
				<option value="${validityPeriod.cepCode}" selected >${validityPeriod.cepName} </option>
				</c:if>
				<c:if test="${validityPeriod.cepCode!=3}">
				<option value="${validityPeriod.cepCode}" >${validityPeriod.cepName} </option>
				</c:if> 
            </c:if>
       		<c:if test="${not empty pricePlanLifeCycle.vaildType }">
       		<option value="${validityPeriod.cepCode}"  <c:if test="${validityPeriod.cepCode==pricePlanLifeCycle.vaildType}">selected</c:if>>${validityPeriod.cepName} </option>
       		</c:if>
       		</c:forEach>
        </select>
      </div>
    </div>
   <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceObject-prod"]}:</label>
      <div class="col-md-8 form-inline">
        <div class="input-icon right input-large dropdown"><i class="glyphicon glyphicon-tree-deciduous"></i>
          	<input type="text" ${readonly} class="form-control input-large dropdown-toggle2"  id="jstreeTextString" name="jstreeTextString">
          	<div class="dropdown-menu jstree" role="menu" id="jstreemjf" >
            	<div class="ppJstree"></div>
          	</div>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.priority"]}: </label>
      <div class="col-md-8 form-inline">
		<input id="billingPriority" ${readonly} name="pricingPlan.billingPriority" class="form-control input-medium" size="22" type="text"
			onkeyup="this.value=this.value.replace(/\D/g,'')"
			onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${not empty pricingPlan.billingPriority }">value="${pricingPlan.billingPriority }"</c:if>
			<c:if test="${empty pricingPlan.billingPriority }">value="500"</c:if> 	/>
      </div>
    </div>
    </form>
  </div>
</div>
<!-- END SECTION 1 --> 

<div style="text-align:center;padding-bottom:20px;">
     <c:if test="${readonly!='readonly' }">
		<button type="button"  class="btn theme-btn button-next"  onclick="savePricePlanBaseInfo()"> Save <i class="m-icon-swapright m-icon-white"></i></button>
     </c:if>
</div>
<div id="chargeInformation" class="portlet"  <c:if test="${pricingTargetId==null}">style="display:none;"</c:if>>
	<c:if test="${readonly!='readonly'}">
	  <div style="margin:30px 0 25px!important;" class="portlet-title">
		    <div style="font-size: 24px; font-weight: 300 ! important;" class="caption"> Charge Information </div>
		    <div class="actions dropdown"   > <a class="btn default btn-select" href="javascript:;"><i class="fa fa-check-square-o"></i> Select </a> <a class="btn default btn-delete" href="javascript:;"><i class="fa fa-trash-o"></i> Delete </a> <a class="btn default" data-toggle="dropdown" href="javascript:;"> Add <i class="fa fa-angle-down"></i></a>
		      <ul class="dropdown-menu pull-right tpl" role="menu" data-set="addTarget">
		        <li> <a data-url="${APP_PATH }/provider/toBasicTariff.shtml?actionType=add&priceType=0" href="javascript:;"  data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="0"> Basic Tariff </a> </li>
		        <li> <a data-url="${APP_PATH }/provider/billingDiscount.shtml?actionType=add&priceType=8" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="8"> Billing Discount </a> </li>
		       </ul>
		    </div>
	  </div>
	  </c:if>
	   
	         <c:if test="${fn:length(componentPriceList)>=1}">
		         <div class="portlet-body tabsArea" data-addtpl="true">
					<ul class="nav nav-tabs fix tabs-content-ajax  tab2Chil_TabUL">
						<c:forEach var="componentPrice" items="${componentPriceList}" varStatus="status"> 
						<c:choose>
							<c:when test="${componentPrice.priceType == '8'}">
							<li class="${1==status.count?'active':''}">
								<a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/provider/billingDiscount.shtml?priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}')" 
											data-toggle="tab"  
											data-key="${formNum}_${componentPrice.priceType}_${status.count}" 
											href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									     </a>
								<input type="checkbox" name="select" value="#tab2_${formNum}_${componentPrice.priceType}_${status.count}"/>
							</li>
							</c:when>
							<c:otherwise>
							<li class="${1==status.count?'active':''}">
							             <a onclick="javascript:loadEx_tab2Chil('#tab2_${formNum}_${componentPrice.priceType}_${status.count}',${componentPrice.priceType},'${APP_PATH}/provider/toBasicTariff.shtml?actionType=${actionType}&type=0&priceInfoId=${componentPrice.priPricingInfoId}&priceId=${componentPrice.priceId}&prodOfferId=${prodOfferId}&pricingTargetId=${pricingTarget.pricingTargetId}&applicType=update&readonly=${readonly}')" 
											data-toggle="tab"  
											data-key="${formNum}_${componentPrice.priceType}_${status.count}" 
											href="#tab2_${formNum}_${componentPrice.priceType}_${status.count}">
											${componentPrice.priceName}
									     </a>
									    <input type="checkbox" name="select" value="#tab2_${formNum}_${componentPrice.priceType}_${status.count}"/>
									     
									     </li>
							</c:otherwise>
							
							</c:choose>
			     		</c:forEach>
			    	</ul>
			 		<div class="tab-content">
				  		<c:forEach var="componentPrice" items="${componentPriceList}" varStatus="status"> 
							<div id="tab2_${formNum}_${componentPrice.priceType}_${status.count}" class="tab-pane ${1==status.count?'active':''}" isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
						</c:forEach>
					</div>
				</div>
			</c:if>
            <c:if test="${fn:length(componentPriceList)<1}">
                <div class="portlet-body tabsArea" data-addtpl="false">
  							<div class="well"> ${local["eaap.op2.portal.price.noChargeInformation"]}</div>
  				</div>			
            </c:if>
              		
              		
              		
	  

<!-- END SECTION 2 -->
<!-- selectZoneModal -->
<div id="selectZoneModal2" class="modal container fade" tabindex="-1" style="display: none;">
</div>
<!-- selectZoneModal -->
<div id="selectZoneModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">Select Zone</h4>
    <hr/>
    <div class="search-form-default">
      <form action="#" class="form-inline">
      <div class="input-group input-medium mb5" > 
        <!-- <input type="text" name="Longitude" placeholder="Longitude" class="form-control" value=""> -->
        <input type="text" name="nameOrId" id="nameOrId" placeholder="Name/ID" class="form-control" >
        <input type="text" name="latitudeAndLongitude" id="latitudeAndLongitude" placeholder="latitude And Longitude" class="form-control" value="">
        <span class="input-group-btn">
        	<a class="btn theme-btn btn-block showZone" href="#" data-height="700" data-width="400" title="Zone Map"
			   data-href="${APP_PATH }/pardOfferPricePlan/goToSelZoneMap.shtml" 
			   ><i class="fa fa-plus"></i></a>
        </span>
        </div>
        <input type="text" name="radius" id="radius" placeholder="Radius (km)" class="form-control" value="0.6(km)">
        <button class="btn default" type="button" id="zoneQuery">Query</button>
      </form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="zonesDT">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"></th>
          <th>ID</th>
          <th>Name</th>
          <th>Longitude</th>
          <th>Latitude</th>
          <th>Remarks</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default zoneCancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn zoneOK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>