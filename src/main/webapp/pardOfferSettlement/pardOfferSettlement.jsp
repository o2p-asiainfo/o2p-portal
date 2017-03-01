<!-- BEGIN SECTION 1 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	var defaultName = $("#settleSpBusiDef.busiName").val();

	var _priceTypeName      = "${local['eaap.op2.portal.price.priceType1']}";
	var _operationTips         = "${local['eaap.op2.portal.price.priceTime']}";
	var _endVal 					= "${local['eaap.op2.portal.price.basic.endVal']}";
	var _greater 					= "${local['eaap.op2.portal.price.basic.greater']}";
	var _prodDateStartEnd 	= "${local['eaap.op2.portal.pardProd.prodDateStartEnd']}";
	var _simpleNotNull 		= "${local['eaap.op2.portal.price.basic.simpleNotNull']}";
	var _endWith1 				= "${local['eaap.op2.portal.price.basic.endWith-1']}";
	var _equal1 					= "${local['eaap.op2.portal.price.basic.equal1']}";
	var _equal2 					= "${local['eaap.op2.portal.price.basic.equal2']}";
	var _notNullBase 			= "${local['eaap.op2.portal.price.basic.notNullBase']}";
	var _notNullRateVal 		= "${local['eaap.op2.portal.price.basic.notNullRateVal']}";
</script>
<%
String formNum=String.valueOf(request.getAttribute("formNum"));
request.setAttribute("key", "settle"+formNum+"_");
request.setAttribute("formNum", formNum);
request.setAttribute("defaultName", "");
String actionType=String.valueOf(request.getAttribute("actionType"));
if("detail".equals(actionType)){
	request.setAttribute("disabled", "disabled='disabled'");
	request.setAttribute("detailFlag", "false");
}else{
	request.setAttribute("disabled", "");
	request.setAttribute("detailFlag", "true");
}
%>
<form id="${key}submit_form" class="form-horizontal" action="#" novalidate>
 <input type="hidden"  id="settleCycleDef.busiCode" name="settleCycleDef.busiCode" value="${settleCycleDef.busiCode}"/>
 <input type="hidden"  id="settleSpBusiDef.servCode" name="settleSpBusiDef.servCode" value="${settleSpBusiDef.servCode}"/>
 <input type="hidden"  id="settleCycleDef.cycleDefId" name="settleCycleDef.cycleDefId" value="${settleCycleDef.cycleDefId}"/>
 <input type="hidden"  id="actionType" name="actionType" value="${actionType}"/>
 <input type="hidden"  id="settleCycleDef.priority" name="settleCycleDef.priority" value="500"/>
 <input type="hidden"  id="settleSpBusiDef.busiId" name="settleSpBusiDef.busiId" value="${settleSpBusiDef.busiId}"/>
 <input type="hidden"  id="settleCycleDef.syncFlag" name="settleCycleDef.syncFlag" value="${settleCycleDef.syncFlag}"/>
 <input type="hidden"  id="settleSpBusiDef.syncFlag" name="settleSpBusiDef.syncFlag" value="${settleSpBusiDef.syncFlag}"/>
 <input type="hidden"  id="operatorOrgId" name="operatorOrgId" value="${operatorOrgId}"/>
 <input type="hidden"  id="o2pCloudFlag" name="o2pCloudFlag" value="${o2pCloudFlag}"/>
<div class="section clearfix">
  <h3 class="form-section mt5">${local["eaap.op2.portal.price.baseInfo"]}</h3>
  <div class="form-body">
  <c:if test="${not empty operatorName&& actionType=='detail'}">
    <div class="form-group">
      <label class="col-md-4 control-label"> ${local["eaap.op2.portal.settlement.OperatorName"]}:</label>
      <div class="col-md-8">
        <input ${disabled} type="text"   value="${operatorName}" class="form-control input-medium"/>
      </div>
    </div>
  </c:if>   
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.SBName"]}:</label>
      <div class="col-md-8">
        <input ${disabled} type="text" name="settleSpBusiDef.busiName" id="settleSpBusiDef.busiName" placeholder="Name" value="${settleSpBusiDef.busiName}" class="form-control input-medium" >
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-4 control-label"><i title='${local["eaap.op2.portal.settlement.billEndDate.detail"]}' data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.billEndDate"]} :</label>
      <div class="col-md-8">
        <div class="input-group input-medium date date-picker" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
          <input ${disabled} type="text" class="form-control" name="settleCycleDef.billEndDate"  id="settleCycleDef.billEndDate" value="${settleCycleDef.billEndDate}" readonly>
          <span class="input-group-btn">
          <button ${disabled} class="btn default" type="button"><i class="fa fa-calendar"></i></button>
          </span> </div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-4 control-label"><i title='${local["eaap.op2.portal.settlement.cycleType.detail"]}' data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.cycleType"]}:</label>
      <div class="col-md-8 form-inline">
        <input ${disabled} type="text" class="form-control input-medium" placeholder="" id="settleCycleDef.cycleCount" value="${settleCycleDef.cycleCount}" name="settleCycleDef.cycleCount"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
        <select ${disabled} class="form-control input-medium" name="settleCycleDef.cycleType" id="settleCycleDef.cycleType">
 			<c:forEach var="obj" items="${settleCycleTypeList}" step="1" varStatus="status"> 
				<option value="${obj.cepCode}" ${obj.cepCode==settleCycleDef.cycleType?"selected":""}>${obj.cepName}</option> 
      		</c:forEach>
        </select>
      </div>
    </div>
    <div class="form-group" style="display:none">
      <label class="col-md-4 control-label"><i title='${local["eaap.op2.portal.settlement.busiType.detail"]}' data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.busiType"]}:</label>
      <div class="col-md-8 form-inline" >
      	<select ${disabled} class="form-control input-medium" id="settleSpBusiDef.busiType" name="settleSpBusiDef.busiType">
 			<c:forEach var="obj" items="${settleDirectoryTypeList}" step="1" varStatus="status"> 
				<option value="${obj.cepCode}" ${obj.cepCode==settleSpBusiDef.busiType?"selected":""}>${obj.cepName}</option> 
      		</c:forEach>
        </select>
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.settlement.effAndExpDate"]}:</label>
      <div class="col-md-8 form-inline">
        <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#here5">
          <input ${disabled} type="text" name="settleSpBusiDef.forEffDate" value="${settleSpBusiDef.forEffDate}" class="form-control od"  style="width:120px !important;"/>
          <span class="input-group-addon"> To </span>
          <input ${disabled} type="text" name="settleSpBusiDef.forExpDate" value="${settleSpBusiDef.forExpDate}" class="form-control od"  style="width:120px !important;"/>
        </div>
        <div id="here5"></div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-4 control-label">${local["eaap.op2.portal.settlement.description"]}:</label>
      <div class="col-md-8">
        <textarea ${disabled} rows="3" class="form-control" id="settleSpBusiDef.description" name="settleSpBusiDef.description">${settleSpBusiDef.description}</textarea>
      </div>
    </div>
    <c:if test="${detailFlag}">
		<div class="form-actions fluid">
	        <div class="col-md-offset-4"> 
	        	<a class="btn theme-btn button-save" href="javascript:;" onclick="onSettlementSave('${key}')"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
	        </div>
      	</div>
    </c:if>
  </div>
</div>
</form>
<!-- END SECTION 1 --> 
<!-- BEGIN SECTION 2 -->
<div class="portlet" style="display: none;" id="${key}portlet">
  <div style="margin:30px 0 25px!important;" class="portlet-title">
    <div style="font-size: 24px; font-weight: 300 ! important;" class="caption"> ${local["eaap.op2.portal.settlement.feeInfo"]} </div>
    <div class="actions dropdown">
    <c:if test="${detailFlag}">
      <a class="btn default btn-select" href="javascript:;"><i class="fa fa-check-square-o"></i> Select </a>      
      <a class="btn default btn-delete" href="javascript:;"><i class="fa fa-trash-o"></i> Delete </a>      
      <a class="btn default" data-toggle="dropdown" href="javascript:;"> Add <i class="fa fa-angle-down"></i> </a>
      <ul class="dropdown-menu pull-right tpl" role="menu" data-set="addTarget">
      	<c:forEach var="obj" items="${settleRuleTypeList}" step="1" varStatus="status"> 
      		<c:set var="ruleType_" value="${obj.cepCode}_" />
			<li> 
				<a data-unique="true" data-type="${obj.cepCode}" data-container=".tabsArea" data-action="addTabMenu_" data-flag="settle" href="javascript:;" data-draw="${settleRuleMap[ruleType_]==1?1:0}" 
				data-url="${APP_PATH}/pardOfferSettlement/toRule.shtml?actionType=add&settleCycleDef.busiCode=${settleCycleDef.busiCode}&settleSpBusiDef.servCode=${settleSpBusiDef.servCode}&ruleType=${obj.cepCode}&formNum=${formNum}&defaultName=${defaultName}&operatorOrgId=${operatorOrgId}"
				name='addSettlementRule' data-key="${formNum}_${obj.cepCode}" data-target="#tab3_${formNum}_${status.count}"> ${obj.cepName}</a>
			</li>
  		</c:forEach>
      </ul>
	</c:if>
    </div>
  </div>
  <div class="portlet-body tabsArea" data-addTpl="${fn:length(settleRulelist)==0?false:true}" data-draw="${fn:length(settleRulelist)}">
  	<c:if test="${fn:length(settleRulelist)>=1}">
		<ul class="nav nav-tabs fix tabs-content-ajax tab3Chil_TabUL">
			<c:forEach var="settleRule" items="${settleRulelist}" step="1" varStatus="status"> 
				<li class=""  ruleId="${settleRule.ruleId}"  ruleType="${settleRule.ruleType}">
					<a onclick="javascript:loadEx('#tab3_${formNum}_${status.count}','${APP_PATH}/pardOfferSettlement/toRule.shtml?actionType=${actionType}&ruleId=${settleRule.ruleId}&ruleType=${settleRule.ruleType}&formNum=${formNum}_${status.count}&settleCycleDef.busiCode=${settleCycleDef.busiCode}&settleSpBusiDef.servCode=${settleSpBusiDef.servCode}')" 
					data-toggle="tab" 
					data-url="${APP_PATH}/pardOfferSettlement/toRule.shtml?actionType=${actionType}&ruleId=${settleRule.ruleId}&ruleType=${settleRule.ruleType}&formNum=${formNum}_${status.count}" data-key="${formNum}_${status.count}" href="#tab3_${formNum}_${status.count}">${settleRule.ruleName}</a>
					<input type="checkbox" name="select" value="#tab3_${formNum}_${status.count}"/>
				</li>
     		</c:forEach>
    	</ul>
 		<div class="tab-content">
	  		<c:forEach var="settle" items="${settleRulelist}" step="1" varStatus="status"> 
				<div id="tab3_${formNum}_${status.count}" class="tab-pane ${1==status.count?'active':''}" isLoad="0" ><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
			</c:forEach>
		</div>
	</c:if>	
	<c:if test="${fn:length(settleRulelist)<1}">
		<div class="well">
			<c:if test="${actionType!='detail'}">
				${local["eaap.op2.portal.pardOffer.noSettlementInfo"]}
			</c:if>
			<c:if test="${actionType=='detail'}">
				${local["eaap.op2.portal.pardOffer.noData"]}
			</c:if>
		</div>
	</c:if>	
  </div>
</div>
<!-- END SECTION 2 --> 

<script type="text/javascript">
 	jQuery(document).ready(function() {
 		var keyV = '${key}';
 		var busiId = $('#'+keyV+'submit_form input[id="settleSpBusiDef.busiId"]').val();
 		if(!!busiId){
 			$('#'+keyV+'portlet').show();
 		}
 		
 	});
</script> 
