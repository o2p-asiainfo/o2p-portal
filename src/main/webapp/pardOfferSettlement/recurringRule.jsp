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
	//初始化名称
	var defaultName = '${settleRule.ruleName}';
	var ruleId = $('#'+_key+'submit_form').find('input[name="settleRule.ruleId"]').val();
	if(ruleId==""){
		var busiName = $('#'+_pKey+'submit_form').find('input[id="settleSpBusiDef.busiName"]').val();
		$('#'+_key+'submit_form').find('input[id="settleRule.ruleName"]').val(busiName+defaultName);
	}
	
    $('#'+_key+'submit_form').find("#settleRuleRCAccumDateType").change(function(){ 
		if("1"==$(this).val()){
			$('#'+_key+'submit_form').find("#accumDateOffsetDiv").show();
		}else{
			$('#'+_key+'submit_form').find("#accumDateOffsetDiv").hide();
		}
	});
    
    $('#'+_key+'submit_form').find("#settleRuleRCAccumType").change(function(){ 
		if("3"==$(this).val()){
			$('#'+_key+'submit_form').find("#accumTypeAttributeValueDiv").show();
		}else{
			$('#'+_key+'submit_form').find("#accumTypeAttributeValueDiv").hide();
		}
	});
    
    $('#'+_key+'submit_form').find("#settleRuleMoneyUnit").change(function(){
    	$(this).closest('form').find('span[class=currencyArea]').text($(this).find('option:selected').text());
    });
    
    //init
    $('#'+_key+'submit_form').find("#settleRuleRCAccumDateType").change();
    $('#'+_key+'submit_form').find("#settleRuleRCAccumType").change();
    $('#'+_key+'submit_form').find("#settleRuleMoneyUnit").change();
    
});
var regValue = function (obj){
	var val = $(obj).val();
	if(/^\d*(\d|(\.{0,1}[0-9]{1,2}))$/.test(val)){
		$(obj).val(val);
	}else{
		$(obj).val('');
	}
}
</script>
<form id="${key}submit_form" class="form-horizontal" action="#" novalidate>
<input type="hidden"  id="ruleType" name="ruleType" value="${ruleType}"/>
<input type="hidden"  id="actionType" name="actionType" value="${actionType}"/>
<input type="hidden"  id="settleRule.ruleId" name="settleRule.ruleId" value="${settleRule.ruleId}"/>
<input type="hidden"  id="settleRule.busiCode" name="settleRule.busiCode" value="${settleRule.busiCode}"/>
<input type="hidden"  id="settleRule.servCode" name="settleRule.servCode" value="${settleRule.servCode}"/>
<input type="hidden"  id="settleRuleRC.ruleDetailId" name="settleRuleRC.ruleDetailId" value="${settleRuleRC.ruleDetailId}"/>
<input type="hidden"  id="settleRule.syncFlag" name="settleRule.syncFlag" value="${settleRule.syncFlag}"/>
<input type="hidden"  id="ruleConditionId" name="ruleConditionId" value="${ruleConditionId}"/>
<input type="hidden"  id="upValue" name="upValue" value="${upValue}"/>
<input type="hidden"  id="downValue" name="downValue" value="${downValue}"/>
<input type="hidden"  id="ratioNumerator" name="ratioNumerator" value="${ratioNumerator}"/>
<input type="hidden"  id="baseValue" name="baseValue" value="${baseValue}"/>
<input type="hidden"  id="operatorOrgId" name="operatorOrgId" value="${operatorOrgId}"/>
<input type="hidden"  id="operatorForSettlement" name="operatorForSettlement"/>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.ruleName"]}:</label>
  <div class="col-md-8">
    <input  ${disabled} type="text" class="form-control input-xlarge" placeholder="" name="settleRule.ruleName" id="settleRule.ruleName" value="${settleRule.ruleName}">
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.orderMethod.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.orderMethod"]}:</label>
  <div class="col-md-3 form-inline">
  	<select ${disabled} class="form-control input-medium" name="settleRuleRC.accumType" id="settleRuleRCAccumType">
		<c:forEach var="obj" items="${settleAccumTypeList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRuleRC.accumType?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
  <div id="accumTypeAttributeValueDiv">
  	<c:choose>
  		<c:when test="${actionType=='detail'}">
        	<label class="col-md-1 control-label"   style="margin-left:54px;margin-top:-9px"><font color="FF0000">*</font>${local["eaap.op2.portal.pardProd.feeCfg.attr"]}:</label>
  	 	</c:when>
    	<c:otherwise>
	  		<label class="col-md-1 control-label"  ><font color="FF0000">*</font>${local["eaap.op2.portal.pardProd.feeCfg.attr"]}:</label>
		</c:otherwise>
	</c:choose>
 	<div class="col-md-1 form-inline">
	  	<select ${disabled} class="form-control" name="settleRuleRC.attrSpecId" id="settleRuleRC.attrSpecId">
			<c:forEach var="obj" items="${productAttrInfoList}" step="1" varStatus="status"> 
				<option value="${obj.ATTR_SPEC_ID}" ${obj.ATTR_SPEC_ID==settleRuleRC.attrSpecId?"selected":""}>${obj.CHAR_SPEC_NAME}</option> 
	 		</c:forEach>
	 	</select>
  	</div>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.orderUserMethod.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.orderUserMethod"]}:</label>
  <div class="col-md-3 form-inline">
  	<select ${disabled} class="form-control input-medium" name="settleRuleRC.accumDateType" id="settleRuleRCAccumDateType">
		<c:forEach var="obj" items="${settleAccumDateTypeList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRuleRC.accumDateType?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
  <div id="accumDateOffsetDiv">
  <c:choose>
    <c:when test="${actionType=='detail'}">
        <label class="col-md-1 control-label"   style="margin-left:54px;margin-top:-9px"><font color="FF0000">*</font>${local["eaap.op2.portal.settlement.day"]}:</label>
  	<div class="col-md-1 form-inline">
  	<select ${disabled} class="form-control" name="settleRuleRC.accumDateOffset" id="settleRuleRC.accumDateOffset">
		<c:forEach var="obj" items="${monthDayList}" step="1" varStatus="status"> 
			<option value="${obj.key}" ${obj.key==settleRuleRC.accumDateOffset?"selected":""}>${obj.value}</option> 
 		</c:forEach>
 	</select>
  	</div>
  	
    </c:when>
    <c:otherwise>
  	<label class="col-md-1 control-label"  ><font color="FF0000">*</font>${local["eaap.op2.portal.settlement.day"]}:</label>
	 <div class="col-md-1 form-inline">
	  	<select ${disabled} class="form-control" name="settleRuleRC.accumDateOffset" id="settleRuleRC.accumDateOffset">
			<c:forEach var="obj" items="${monthDayList}" step="1" varStatus="status"> 
				<option value="${obj.key}" ${obj.key==settleRuleRC.accumDateOffset?"selected":""}>${obj.value}</option> 
	 		</c:forEach>
	 	</select>
	  </div>
    </c:otherwise>
  </c:choose>
  
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.reduce.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.reduce"]}:</label>
  <div class="col-md-8 form-inline">
    <input ${disabled} type="text" class="form-control input-medium" placeholder="" name="settleRuleRC.reduceRes" value="${settleRuleRC.reduceRes}">
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.payDir.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.payDir"]}:</label>
  <div class="col-md-8 form-inline">
    <select ${disabled} class="form-control input-medium" name="settleRule.chargeDir" id="settleRule.chargeDir">
		<c:forEach var="obj" items="${settleChargeDirList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRule.chargeDir?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.priceType"]}:</label>
  <div class="col-md-8 form-inline">
    <select ${disabled} class="form-control input-medium" name="settleRule.moneyUnit" id="settleRuleMoneyUnit">
		<c:forEach var="obj" items="${settleMoneyUnitList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRule.moneyUnit?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.settlement.RCCharge.detail"]}' class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.rcCharge"]}:</label>
  <div class="col-md-8 form-inline">
    <table class="table table-bordered table-condensed table-advance table-function table-rc"  id="recurrinngRuleTab">
      <thead>
        <tr>
          <th><c:if test="${actionType!='detail'}"><i class="fa fa-plus"  onclick="handleRcTableAdd(this);"></i></c:if></th>
          <th>${local["eaap.op2.portal.settlement.orderArrange"]}</th>
          <th>${local["eaap.op2.portal.settlement.unitPrice"]}&nbsp; (<span class="currencyArea">DKK</span>)</th>
          <th>${local["eaap.op2.portal.settlement.baseCharge"]}&nbsp; (<span class="currencyArea">DKK</span>)</th>
          <th ${actionType=="detail"?"style='display:none '":""}>${local["eaap.op2.portal.pardSpec.operation"]}</th>
        </tr>
      </thead>
      <tbody>
	    <c:if test="${fn:length(ruleConditionList)>=1}">
	      <c:forEach var="settleRuleCondition" items="${ruleConditionList}" step="1" varStatus="status"> 
			<tr id="${settleRuleCondition.ruleConditionId}">
	          <td>${status.count}</td>
	          <td><div class="input-group input-xs">
	              <input ${disabled} type="text"  id="downInputValue"  class="form-control" value="${settleRuleCondition.downValue}">
	              <span class="input-group-addon">${local["eaap.op2.portal.price.to1"]}</span>
	              <input ${disabled} type="text"   id="upInputValue"  class="form-control" value="${settleRuleCondition.upValue}">
	            </div>
	          <td><input ${disabled} type="text" placeholder="" size="15" class="form-control input-xs" value="${settleRuleCondition.ratioNumerator}" onblur="regValue(this);"></td>
	          <td><input ${disabled} type="text"  id="baseRcValue"  placeholder="" size="15" class="form-control input-xs" value="${settleRuleCondition.baseValue}" onblur="regValue(this);"></td>
	          <td ${actionType=="detail"?"style='display:none '":""} align="center">
	          	<a class="btn default btn-sm black btn-del"  onclick="handleRcTableDel(this);">Delete </a>
	          </td>
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
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.effAndExpDate"]}:</label>
  <div class="col-md-8 form-inline">
    <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#here6">
      <input ${disabled} type="text" name="settleRule.forEffDate" value="${settleRule.forEffDate}" class="form-control od"   style="width:120px !important;"/>
      <span class="input-group-addon"> ${local["eaap.op2.portal.price.to"]}</span>
      <input ${disabled} type="text" name="settleRule.forExpDate" value="${settleRule.forExpDate}" class="form-control od" style="width:120px !important;"/>
    </div>
    <div id="here6"></div>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]} :</label>
  <div class="col-md-8">
    <textarea ${disabled} rows="3" class="form-control" name="settleRule.description">${settleRule.description}</textarea>
  </div>
</div>
 <c:if test="${actionType!='detail'}">
	<div class="form-actions fluid">
   	<div class="col-md-offset-4"> 
   		<a class="btn theme-btn button-save" href="javascript:;" onclick="onSettlementRcSave('${key}')"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
   	</div>
 	</div>
 </c:if>
</form>
