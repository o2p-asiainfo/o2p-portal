<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
var _endWith1 				= "${local['eaap.op2.portal.price.basic.endWith-1']}";
var _greater 					= "${local['eaap.op2.portal.price.basic.greater']}";
var _equal1 					= "${local['eaap.op2.portal.price.basic.equal1']}";
var _equal2 					= "${local['eaap.op2.portal.price.basic.equal2']}";
var _ruleNameNull 				= "${local['eaap.op2.portal.settlement.ruleNameNull']}";
var _notEmpty 					= "${local['eaap.op2.portal.settlement.notEmpty']}";


 function nullError(obj){
	 if(obj.value.replace(/^ +| +$/g,'')==''){
		 toastr.error(_notEmpty);
		 obj.focus();
		 }
 }

 jQuery(document).ready(function() { 
    SettlementAdd.init();
 })
 
 function changeMonetTypeDir(){
	 $("#moneyUnit").change(function(){
		 var currency = $(this).find('option:selected').text();
	     $('.currencyArea').html(currency);
	});
 }

        
  </script> 
  <form id="settle_submit_form" class="form-horizontal" action="#" novalidate>
  <input name="settleRule.ruleId" id="ruleId"   value="${settleRule.ruleId }" type="hidden">
  <input name="settleRule.servCode" id="servCode"  value="${servCode}" type="hidden" >
  <input name="actionType" id="actionType"  value="${actionType}" type="hidden" >
  <input name="upValue" id="upValue"  type="hidden" >
  <input name="downValue" id="downValue"   type="hidden" >
  <input name="ratioNumerator" id="ratioNumerator"   type="hidden" >
  <input name="ratioDemominator" id="ratioDemominator" type="hidden" >
  <input name="baseValue" id="baseValue"   type="hidden" >
  <input name="ruleConditionId" id="ruleConditionId"  type="hidden" >
        					
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
  <h4 class="modal-title">${local["eaap.op2.portal.settlement.addSettle"]}</h4>
</div>
<div class="modal-body form-horizontal">
   
  <div class="form-body">
		<div class="form-group">
		 <label class="col-md-4 control-label">
		  <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.ruleName"]}:</label>
		 <div class="col-md-8">
        <input type="text" ${disabled} name="settleRule.ruleName" id="ruleName" value="${settleRule.ruleName}" onblur="nullError(this);" placeholder=""  class="form-control input-medium" />
        
      </div>
		</div>
		<div class="form-group">
	      <label class="col-md-4 control-label"><i title='${local["eaap.op2.portal.settlement.cycleType.detail"]}' data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.cycleType"]}:</label>
	      <div class="col-md-8 form-inline">
	        <input ${disabled} type="text" class="form-control input-medium" placeholder="" id="cycleCount" onblur="nullError(this);"
	        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
			<c:if test="${empty settleRuleCLC.cycleCount }">value="1"</c:if>
			<c:if test="${not empty settleRuleCLC.cycleCount }">value="${settleRuleCLC.cycleCount }"</c:if>
	          name="settleRuleCLC.cycleCount" />
	        <select ${disabled} class="form-control input-medium" name="settleRuleCLC.cycleType" id="cycleType">
	 			<c:forEach var="obj" items="${settleCycleTypeList}" step="1" varStatus="status"> 
					<option value="${obj.key}" ${obj.key==settleRuleCLC.cycleType?"selected":""}>${obj.value}</option> 
	      		</c:forEach>
	        </select>
	      </div>
	    </div>
		
		<div class="form-group">
		  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.priceType"]}:</label>
		  <div class="col-md-8 form-inline">
		    <select ${disabled}  class="form-control input-medium" name="settleRule.moneyUnit" id="moneyUnit"  onclick="javascript:changeMonetTypeDir();">
				<c:forEach var="obj" items="${settleMoneyUnitList}" step="1" varStatus="status"> 
					<option value="${obj.key}" ${obj.key==settleRule.moneyUnit?"selected":""}>${obj.value}</option> 
		 		</c:forEach>
		 	</select>
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.payDir.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.payDir"]}:</label>
		  <div class="col-md-8 form-inline">
		    <select ${disabled}  class="form-control input-medium" name="settleRule.chargeDir" id="chargeDir">
				<c:forEach var="obj" items="${settleChargeDirList}" step="1" varStatus="status"> 
					<option value="${obj.key}" ${obj.key==settleRule.chargeDir?"selected":""}>${obj.value}</option> 
		 		</c:forEach>
		 	</select>
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.chargeCondition.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.chargeCondition"]}:</label>
		  <div class="col-md-8 form-inline">
		    <table class="table table-bordered table-condensed table-advance table-function table-agg">
		      <thead>
		        <tr>
		          <th><c:if test="${disabled !='disabled'}"> <i ${disabled} class="fa fa-plus addBut"></i></c:if> </th>
		          <th> ${local["eaap.op2.portal.settlement.arrange"]} <span class="label label-yellow">  <span class="currencyArea"></span> </span> </th>
		          <th> ${local["eaap.op2.portal.settlement.ChargePercent"]}  </th>
		          <th> ${local["eaap.op2.portal.settlement.baseCharge"]} <span class="label label-yellow"> <span class="currencyArea"></span></span> </th>
		          <th> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
		        </tr>
		      </thead>
		      <tbody>
		        <c:if test="${fn:length(ruleConditionList)>=1}">
			      <c:forEach var="settleRuleCondition" items="${ruleConditionList}" step="1" varStatus="status"> 
					<tr>
			          <td>${status.count}</td>
			          <td>
			          	<div class="input-group input-xs">
			              <input type="text" ${disabled} class="form-control" id ='beginVal'  value="${settleRuleCondition.downValue}"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			              <span class="input-group-addon">~</span>
			              <input type="text" ${disabled} class="form-control" id = 'engVal' value="${settleRuleCondition.upValue}"
			              onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')">
			            </div>
			          <td>
			          	<div class="input-group input-xs">
			              <input type="text" ${disabled} class="form-control" value="${settleRuleCondition.ratioNumerator}"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			              <span class="input-group-addon">/</span>
			              <input type="text" ${disabled} class="form-control" value="${settleRuleCondition.ratioDemominator}"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			            </div>
			          </td>
			          <td><input type="text" ${disabled} placeholder="" class="form-control input-xs" value="${settleRuleCondition.baseValue}"
			          onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
			          <td>
			          	<a id="${settleRuleCondition.ruleConditionId}" ${disabled} class="btn default btn-sm black btn-del" href="#"> <i class="fa fa-trash-o"></i> Delete </a>
			          </td>
			          <input type="hidden" value="${settleRuleCondition.ruleConditionId}"/>
			        </tr>
		 		  </c:forEach>
			    </c:if>
			    <c:if test="${state !='D'&& state !='B'&& state !='H'}">
		      	<c:if test="${fn:length(ruleConditionList)<1}">
			        <tr>
			          <td>1</td>
			          <td><div class="input-group input-xs">
			              <input type="text" name="from3" class="form-control" value="0"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			              <span class="input-group-addon">~</span>
			              <input type="text" name="to3" class="form-control" value="-1" onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')">
			            </div>
			          <td><div class="input-group input-xs">
			              <input type="text" name="from3" class="form-control" value="0"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			              <span class="input-group-addon">/</span>
			              <input type="text" name="to3" class="form-control" value="1"
			              onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			            </div></td>
			          <td><input type="text" name="" placeholder="" class="form-control" value="0"></td>
			          <td>
			          	<a class="btn default btn-sm black btn-del" href="#"> <i class="fa fa-trash-o"></i> Delete </a>
			          </td>
			          <input type="hidden" value=""/>
			        </tr>
			     </c:if>
			     </c:if>
		      </tbody>
		    </table>
		  </div>
		</div>
		<div class="form-group">
	      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.effAndExpDate"]}:</label>
	      <div class="col-md-8 form-inline">
	        <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#here5">
	          <input ${disabled} readonly type="text"   style="width:120px" name="settleRule.forEffDate" id ="forEffDate" value="${settleRule.forEffDate}" class="form-control od">
	          <span class="input-group-addon"> To </span>
	          <input ${disabled} type="text"  style="width:120px"  name="settleRule.forExpDate" id="forExpDate" value="${settleRule.forExpDate}" class="form-control od">
	        </div>
	        <div id="here5"></div>
	      </div>
	    </div>
		<div class="form-group">
		 <label class="col-md-4 control-label"> ${local["eaap.op2.portal.settlement.description"]} :</label>
		 <div class="col-md-8">
		  <textarea rows="3" ${disabled} class="form-control" id="description" name="settleRule.description" >${settleRule.description}</textarea>
		 </div>
		</div>
		 
		  <p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" ${disabled} id="saveSettleApi">${local['eaap.op2.portal.doc.submit']}</a></p>
         
</div>
</div>
</form>