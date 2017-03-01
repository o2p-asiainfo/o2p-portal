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
	
	$('#'+_key+'submit_form').find("#settleRuleOTCAccumType").click(function(){
		var ischeck=0;
		$('input[id="settleRuleOTCAccumType"]:checked').each(function(){    
			$('#'+_key+'submit_form').find("#accumTypeAttributeValueDiv").show();
			ischeck=1;
			$('#'+_key+'submit_form').find("#accumType").val('3');
		});  
		if(ischeck==0){
			$('#'+_key+'submit_form').find("#accumTypeAttributeValueDiv").hide();
			$('#'+_key+'submit_form').find("#accumType").val('2');
		}
	});
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
<input type="hidden"  id="operatorOrgId" name="operatorOrgId" value="${operatorOrgId}"/>
<input type="hidden"  id="operatorForSettlement" name="operatorForSettlement"/>
<input type="hidden"  id="accumType" name="settleRuleOTC.accumType" value="${settleRuleOTC.accumType}"/>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.ruleName"]}:</label>
  <div class="col-md-8">
    <input ${disabled}  type="text" class="form-control input-xlarge" placeholder="" name="settleRule.ruleName" id="settleRule.ruleName" value="${settleRule.ruleName}">
  </div>
</div>

<c:choose>  
	<c:when test="${fn:contains(actionType,'detail')}"> 
		<c:if test="${fn:contains(settleRuleOTC.accumType,3)}">
			<div class="form-group">
				<label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.pardProd.feeCfg.attr"]}' class="fa fa-question-circle"></i> ${local["eaap.op2.portal.pardProd.feeCfg.attr"]}:</label>
			  	<div class="col-md-3 form-inline">
			  	 	<div class="checkbox-list" data-error-container="#here">
						<label class="checkbox-inline">
			             	<select ${disabled} class="form-control" name="settleRuleOTC.attrSpecId" id="accumTypeAttributeValueDiv" style="display: ${fn:contains(settleRuleOTC.accumType,3)?'':'none'}">
								<c:forEach var="obj" items="${productAttrInfoList}" step="1" varStatus="status"> 
									<option value="${obj.ATTR_SPEC_ID}" ${obj.ATTR_SPEC_ID==settleRuleOTC.attrSpecId?"selected":""}>${obj.CHAR_SPEC_NAME}</option> 
					 			</c:forEach>
					 		</select>
			        	</label>
			  		</div>
			  	</div>
			</div>
		</c:if> 
	</c:when>  
   	<c:otherwise>
   		<div class="form-group">
			<label class="col-md-4 control-label"><i data-toggle="tooltip" data-placement="top" data-original-title='${local["eaap.op2.portal.pardProd.feeCfg.attr"]}' class="fa fa-question-circle"></i> ${local["eaap.op2.portal.pardProd.feeCfg.attr"]}:</label>
		  	<div class="col-md-3 form-inline">
		  	 	<div class="checkbox-list" data-error-container="#here">
					<label class="checkbox-inline">
						<c:if test="${fn:contains(settleRuleOTC.accumType,3)}">
							<input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes" name="settleRuleOTCAccumTypeCK" id="settleRuleOTCAccumType" checked="checked"/>
						</c:if> 
						<c:if test="${!(fn:contains(settleRuleOTC.accumType,3))}">
		                  <input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes" name="settleRuleOTCAccumTypeCK" id="settleRuleOTCAccumType"/>
		                </c:if> 
		             	<select ${disabled} class="form-control" name="settleRuleOTC.attrSpecId" id="accumTypeAttributeValueDiv" style="display: ${fn:contains(settleRuleOTC.accumType,3)?'':'none'}">
							<c:forEach var="obj" items="${productAttrInfoList}" step="1" varStatus="status"> 
								<option value="${obj.ATTR_SPEC_ID}" ${obj.ATTR_SPEC_ID==settleRuleOTC.attrSpecId?"selected":""}>${obj.CHAR_SPEC_NAME}</option> 
				 			</c:forEach>
				 		</select>
		        	</label>
		  		</div>
		  	</div>
		</div>
   	</c:otherwise>  
</c:choose> 

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
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.OtcCharge"]}:</label>
  <div class="col-md-8 form-inline">
  	<input ${disabled}  type="text" class="form-control input-medium" placeholder="" name="settleRuleCondition.baseValue" id="settleRuleCondition.baseValue" value="${settleRuleCondition.baseValue}" onblur="regValue(this);" />
    <select ${disabled}  class="form-control input-medium" name="settleRule.moneyUnit" id="settleRule.moneyUnit">
		<c:forEach var="obj" items="${settleMoneyUnitList}" step="1" varStatus="status"> 
			<option value="${obj.cepCode}" ${obj.cepCode==settleRule.moneyUnit?"selected":""}>${obj.cepName}</option> 
 		</c:forEach>
 	</select>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.effAndExpDate"]}:</label>
  <div class="col-md-8 form-inline">
    <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#here6">
      <input ${disabled}  type="text" name="settleRule.forEffDate" value="${settleRule.forEffDate}" class="form-control od"  style="width:120px !important;"/>
      <span class="input-group-addon"> ${local["eaap.op2.portal.price.to"]}</span>
      <input ${disabled}  type="text" name="settleRule.forExpDate" value="${settleRule.forExpDate}" class="form-control od"  style="width:120px !important;"/>
    </div>
    <div id="here6"></div>
  </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]} :</label>
  <div class="col-md-8">
    <textarea ${disabled}  rows="3" class="form-control" name="settleRule.description">${settleRule.description}</textarea>
  </div>
</div>
 <c:if test="${actionType!='detail'}">
	<div class="form-actions fluid">
	   <div class="col-md-offset-4"> 
	   	<a class="btn theme-btn button-save" href="javascript:;" onclick="onSettlementOtcSave('${key}')"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
	   </div>
	 </div>
</c:if>
</form>
