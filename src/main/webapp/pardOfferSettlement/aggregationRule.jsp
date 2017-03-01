<!-- BEGIN SECTION 1 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String formNum=String.valueOf(request.getAttribute("formNum"));
String ruleType=String.valueOf(request.getAttribute("ruleType"));
request.setAttribute("key", "settle"+formNum+"_"+ruleType+"_");
request.setAttribute("pKey", "settle"+formNum+"_");
String actionType=String.valueOf(request.getAttribute("actionType"));
boolean isDetail=true;
if("detail".equals(actionType)){
	isDetail=true;
	request.setAttribute("disabled", "disabled='disabled'");
}else{
	isDetail=false;
	request.setAttribute("disabled", "");
}
%>
<script>
$(document).ready(function(){
	var _key = '${key}';
	var _pKey = '${pKey}';
    $('#'+_key+'submit_form').find("#settleRuleMoneyUnit").change(function(){
    	$(this).closest('form').find('span[class=currencyArea]').text($(this).find('option:selected').text());
    });
    
    //init
    $('#'+_key+'submit_form').find("#settleRuleMoneyUnit").change();

	var defaultName = '${settleRule.ruleName}';
	var ruleId = $('#'+_key+'submit_form').find('input[name="settleRule.ruleId"]').val();
	if(ruleId==""){
		var busiName = $('#'+_pKey+'submit_form').find('input[id="settleSpBusiDef.busiName"]').val();
		$('#'+_key+'submit_form').find('input[id="settleRule.ruleName"]').val(busiName+defaultName);
	}
	
});
var regValue = function (obj){
	var val = $(obj).val();
	if(/^\d*(\d|(\.{0,1}[0-9]{1,2}))$/.test(val)){
		$(obj).val(val);
	}else{
		$(obj).val('');
	}
}

var _endWith1 				= "${local['eaap.op2.portal.price.basic.endWith-1']}";
var _greater 					= "${local['eaap.op2.portal.price.basic.greater']}";
var _equal1 					= "${local['eaap.op2.portal.price.basic.equal1']}";
var _equal2 					= "${local['eaap.op2.portal.price.basic.equal2']}";
var _notNullBase 			= "${local['eaap.op2.portal.price.basic.notNullBase']}";
var _nNotNull 				= "${local['eaap.op2.portal.price.billingDiscount.nNotNull']}";
var _dNotNull 				= "${local['eaap.op2.portal.price.billingDiscount.dNotNull']}";
var _nLessThand 			= "${local['eaap.op2.portal.price.billingDiscount.nLessThand']}";
var _endWith0d 				= "${local['eaap.op2.portal.price.billingDiscount.endWith0d']}";
</script>
<form id="${key}submit_form" class="form-horizontal" action="#" novalidate>
<input type="hidden"  id="ruleType" name="ruleType" value="${ruleType}"/>
<input type="hidden"  id="actionType" name="actionType" value="${actionType}"/>
<input type="hidden"  id="settleRule.ruleId" name="settleRule.ruleId" value="${settleRule.ruleId}"/>
<input type="hidden"  id="busiCode" name="settleRule.busiCode" value="${settleRule.busiCode}"/>
<input type="hidden"  id="servCode" name="settleRule.servCode" value="${settleRule.servCode}"/>
<input type="hidden"  id="settleRuleRC.ruleDetailId" name="settleRuleRC.ruleDetailId" value="${settleRuleRC.ruleDetailId}"/>
<input type="hidden"  id="settleRule.syncFlag" name="settleRule.syncFlag" value="${settleRule.syncFlag}"/>
<input type="hidden"  id="settleRuleAggregation.ruleDetailId" name="settleRuleAggregation.ruleDetailId" value="${settleRuleAggregation.ruleDetailId}"/>
<input type="hidden"  id="settleRuleAggregation.ruleDetailInfo" name="settleRuleAggregation.ruleDetailInfo" value="${settleRuleAggregation.ruleDetailInfo}"/>
<input type="hidden"  id="ruleConditionId" name="ruleConditionId" value="${ruleConditionId}"/>
<input type="hidden"  name="upValue" value="${upValue}"/>
<input type="hidden"  name="downValue" value="${downValue}"/>
<input type="hidden"  name="ratioNumerator" value="${ratioNumerator}"/>
<input type="hidden"  name="ratioDemominator" value="${ratioDemominator}"/>
<input type="hidden"  name="baseValue" value="${baseValue}"/>
<input type="hidden"  id="operatorOrgId" name="operatorOrgId" value="${operatorOrgId}"/>
<input type="hidden"  id="operatorForSettlement" name="operatorForSettlement"/>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.ruleName"]}:</label>
  <div class="col-md-8">
    <input ${disabled}  type="text" class="form-control input-xlarge" placeholder="" name="settleRule.ruleName" id="settleRule.ruleName" value="${settleRule.ruleName}">
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.aggregationItem.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.aggregationItem"]}:</label>
  <div class="col-md-8 form-inline">
  	<select ${disabled}  class="form-control input-medium" name="settleRuleAggregation.aggregationItem" id="settleRuleAggregation.aggregationItem">
		<c:forEach var="obj" items="${aggregationItemList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRuleAggregation.aggregationItem?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
<label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.ruleDetailInfo"]}:</label>
           <div class="col-md-8">
             <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
               <thead>
                 <tr>
                   <th> <i class="fa fa-plus" data-toggle="modal" data-target="#ruleDetailInfoModal"></i>  </th>
                   <th> ${local["eaap.op2.portal.settlement.ruleType"]} </th>
                   <th> ${local["eaap.op2.portal.settlement.SBName"]} </th>
                   <th> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
                 </tr>
               </thead>
               <tbody id='ruleDetailInfoTB'>
               	<c:if test="${fn:length(settleRuleList)>=1}">
				<c:forEach var="settleRule" items="${settleRuleList}" step="1" varStatus="status"> 
            		<tr id="${settleRule.ruleId}">
            			<td>${status.count}</td>
            			<td>${settleRule.ruleType}</td>
                   		<td>${settleRule.ruleName}</td>
                   		<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>
                 	</tr>
         		</c:forEach>
          		</c:if>	
      			<c:if test="${fn:length(settleRuleList)<1}">
					<tr>
                    	<td colspan="6">None</td>
                  	</tr>
           		</c:if>	
               </tbody>
             </table>
 	   </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.payDir.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.payDir"]}:</label>
  <div class="col-md-8 form-inline">
    <select ${disabled}  class="form-control input-medium" name="settleRule.chargeDir" id="settleRule.chargeDir">
		<c:forEach var="obj" items="${settleChargeDirList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRule.chargeDir?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.priceType"]}:</label>
  <div class="col-md-8 form-inline">
    <select ${disabled}  class="form-control input-medium" name="settleRule.moneyUnit" id="settleRuleMoneyUnit">
		<c:forEach var="obj" items="${settleMoneyUnitList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRule.moneyUnit?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.chargeCondition.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.chargeCondition"]}:</label>
  <div class="col-md-8 form-inline">
    <table class="table table-bordered table-condensed table-advance table-function table-agg"  id="aggregationTab">
      <thead>
        <tr>
          <th><c:if test="${actionType!='detail'}"><i class="fa fa-plus"  onclick="handleAggTableAdd(this);"></i></c:if></th>
          <th> ${local["eaap.op2.portal.settlement.arrange"]} <span class="label ">  (<span class="currencyArea">DKK</span>) </span> </th>
          <th> ${local["eaap.op2.portal.settlement.ChargePercent"]}  </th>
          <th> ${local["eaap.op2.portal.settlement.baseCharge"]} <span class="label "> (<span class="currencyArea">DKK</span>)</span> </th>
          <c:choose>
             <c:when test="${actionType=='detail'}">
             </c:when>
             <c:otherwise>
                <th> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
             </c:otherwise>
          </c:choose>
          
        </tr>
      </thead>
      <tbody>
        <c:if test="${fn:length(ruleConditionList)>=1}">
	      <c:forEach var="settleRuleCondition" items="${ruleConditionList}" step="1" varStatus="status"> 
			<tr id="${settleRuleCondition.ruleConditionId}">
	          <td>${status.count}</td>
	          <td>
	          	<div class="input-group input-xs">
	              <input ${disabled} id="downValue"  type="text" class="form-control" value="${settleRuleCondition.downValue}" />
	              <span class="input-group-addon">~</span>
	              <input ${disabled} id="upValue" type="text" class="form-control" value="${settleRuleCondition.upValue}" />
	            </div>
	           </td>
	          <td>
	          	<div class="input-group input-xs">
	              <input  ${disabled} id="ratioNumerator"  type="text" class="form-control" value="${settleRuleCondition.ratioNumerator}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	              <span class="input-group-addon">/</span>
	              <input ${disabled} id="ratioDemominator" type="text" class="form-control" value="${settleRuleCondition.ratioDemominator}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	            </div>
	          </td>
	          <td><input ${disabled}  id="baseValue"  type="text" placeholder="" class="form-control input-xs" value="${settleRuleCondition.baseValue}" onblur="regValue(this);"></td>
	          <c:choose>
             <c:when test="${actionType=='detail'}">
             </c:when>
             <c:otherwise>
                <td>
	          	<a class="btn default btn-sm black btn-del" href="javascript:;"  onclick="handleAggTableDel(this);"> <i class="fa fa-trash-o"></i> Delete </a>
	          </td>
             </c:otherwise>
          </c:choose>
	          <input type="hidden" value="${settleRuleCondition.ruleConditionId}"  id="ruleConditionIdForDel"/>
	        </tr>
 		  </c:forEach>
	    </c:if>
      	<c:if test="${fn:length(ruleConditionList)<1}">
      	
      	<c:choose>
             <c:when test="${actionType=='detail'}">
	             <tr>
		        	<td colspan="4">None</td>
		        </tr>
             </c:when>
             <c:otherwise>
                <tr>
		        	<td colspan="5">None</td>
		        </tr>
             </c:otherwise>
          </c:choose>
	        
	     </c:if>
      </tbody>
    </table>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> <i title="Indicates the relative priority across all items of the same type where a value of 1 is the highest priority and a value of 10 would be the lowest priority item"
         data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i>${local["eaap.op2.portal.settlement.priority"]}:</label>
  <div class="col-md-1">
<%--     <input ${disabled}  type="text" class="form-control input-medium" placeholder="" name="settleRule.priority" id="settleRule.priority" value="${settleRule.priority }"> --%>
  <select class="form-control unit"  id="settleRule.priority" name="settleRule.priority"    ${disabled} style="width:85px">
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
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.effAndExpDate"]}:</label>
  <div class="col-md-8 form-inline">
    <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#here6">
      <input ${disabled}  type="text" name="settleRule.forEffDate"  value="${settleRule.forEffDate}" class="form-control od" style="width:120px !important;"/>
      <span class="input-group-addon"> ${local["eaap.op2.portal.price.to"]}</span>
      <input ${disabled}  type="text" name="settleRule.forExpDate"  value="${settleRule.forExpDate}" class="form-control od" style="width:120px !important;"/>
    </div>
    <div id="here6"></div>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]} :</label>
  <div class="col-md-8">
    <textarea ${disabled}  rows="3" class="form-control" name="settleRule.description" >${settleRule.description}</textarea>
  </div>
</div>
<c:if test="${actionType!='detail'}">
<div class="form-actions fluid">
   <div class="col-md-offset-4"> 
   	<a class="btn theme-btn button-save" href="javascript:;" onclick="onSettlementAggregationSave('${key}')"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
   </div>
 </div>
</c:if>

<!-- ruleDetailInfoModal -->
<div id="ruleDetailInfoModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.settlement.ruleDetailInfo"]}</h4>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center group-check" id="ruleDetailInfoDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"></th>
          <th>${local["eaap.op2.portal.settlement.ruleType"]}</th>
          <th>${local["eaap.op2.portal.settlement.SBName"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default ruleDetailInfoModal cancelRuleDetailInfoModal">${local["eaap.op2.portal.doc.cancel"]}</button>
    <button type="submit" class="btn theme-btn ruleDetailInfoModal okRuleDetailInfoModal">${local["eaap.op2.portal.doc.submit"]}</button>
  </div>
</div>
<!-- /ruleDetailInfoModal -->
</form>