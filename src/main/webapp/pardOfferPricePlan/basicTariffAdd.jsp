<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
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

var regValue = function (obj){
	var val = $(obj).val();
	if(/^\d*(\d|(\.{0,1}[0-9]{1,2}))$/.test(val)){
		$(obj).val(val);
	}else{
		$(obj).val('');
	}
}
</script>
<form id="basicTariffForm" name="basicTariffForm" action="${APP_PATH}/pardOfferPricePlan/saveOrUpdateBasicTariff.shtml" method="post">
<div style="display: none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="other" id="other" value=""/>
	 <input type="text" name="details" id="details" value=""/>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-large" placeholder=""  id="priceName" name="priceName"  value="${componentPrice.priceName }"  ${actionType=='detail'?'disabled':''}  maxlength="100">
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-large"   id="taxIncluded"  name="taxIncluded"   ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${taxTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==basicTariff.taxIncluded}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2012-10-11" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
   <input type="text"  id="formatEffDate" name="formatEffDate"  class="form-control od"   style="width:140px !important;"  value="${componentPrice.formatEffDate }"  ${actionType=='detail'?'disabled':''}>
   <span class="input-group-addon"> To </span>
   <input type="text"  id="formatExpDate" name="formatExpDate"  class="form-control od"   style="width:140px !important;"  value="${componentPrice.formatExpDate }"  ${actionType=='detail'?'disabled':''}>
  </div>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.objectType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-large baseItemType"  id="baseItemType"  name="baseItemType"    ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${baseItemTypeList}" varStatus="status">
			<option value="${obj.cepCode}"   <c:if test="${obj.cepCode==basicTariff.baseItemType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group" id="qosDiv" style="<c:if test="${empty basicTariff.baseQosType}">display:none;</c:if>">
 <label class="col-md-4 control-label">
            QOS:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-large"  id="baseQosType"  name="baseQosType"    ${actionType=='detail'?'disabled':''}>	 
			<option value="1"  <c:if test="${baseQosType=='1'}">selected</c:if>>Gold</option>
			<option value="2" <c:if test="${baseQosType=='2'}">selected</c:if>>Silver</option>
			<option value="3"  <c:if test="${baseQosType=='3'}">selected</c:if>>Copper</option>               	 
  </select>
 <input type="hidden" id="baseQosTypeVal"  name="baseQosTypeVal"/>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.priceObject"]}:</label>
 <div class="col-md-8 form-inline"  id="poJstreeDiv" >
		<div class="input-icon right input-large"  style="width:485px !important;">
			<i class="glyphicon glyphicon-tree-deciduous"></i>
		   <input type="text" class="form-control input-large  dropdown-toggle2"  id="itemNames" name="itemNames"    style="width:485px !important;"  ${actionType=='detail'?'disabled':''}/>
		   <input type="hidden"  id="itemIds" name="itemIds"  value="${itemIds}"/>
		   <div class="dropdown-menu jstree" role="menu"  style="width:485px !important;">
		    	<div class="poShu"></div>
		   </div>
  		</div>
 </div>
 <div class="col-md-8 form-inline"  id="loadingImg_tree"  style="width:30px; padding:9px 0 0 0; display:none;"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceUnit1"]}:</label>
 <div class="col-md-8 form-inline"   style="width:auto;">
  <input type="text" class="input-medium form-control"  id="ratingUnitVal" name="ratingUnitVal" value="${basicTariff.ratingUnitVal }"  maxlength="9" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"    ${actionType=='detail'?'disabled':''}/>
  <select class="form-control input-medium chargingUnit"   id="ratingUnitType"  name="ratingUnitType"  def-value="${basicTariff.ratingUnitType}"   ${actionType=='detail'?'disabled':''}></select>
 </div>
 <div class="col-md-8 form-inline"  id="loadingImg_unit"  style="width:30px; padding:9px 0 0 0; display:none;"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceUnit2"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge currency"   id="currencyUnitType"  name="currencyUnitType"   ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status" >
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==basicTariff.currencyUnitType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
	 <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.feeType"]}:</label>
	 <div class="col-md-8 form-inline" >
		  <div class="radio-list" data-error-container="#here2">
			   <label class="radio-inline">
				    <input type="radio"  name="rateType" id="rateType1" value="1" <c:if test="${basicTariff.rateType==1 || empty basicTariff.rateType}">checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/> 
				    ${local["eaap.op2.portal.price.simple"]} 
			    </label>
			   <label class="radio-inline" >
				    <input type="radio"  name="rateType" id="rateType2" value="2" <c:if test="${basicTariff.rateType==2}">checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/> 
				    ${local["eaap.op2.portal.price.ladder"]} 
			    </label>
		  </div>
	 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
 <div class="col-md-8 form-inline">
 
  <div id="simpleFeeTab">
	    <c:choose>
   			<c:when test="${basicTariff.rateType==1}">
   			<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
			  <div class="simple" >
				   <div class="input-group">
					    <input type="text" id="rateVal"  name="rateVal" class="form-control"  value="${d.rateVal }"  maxlength="9"  style="width:160px;"  onblur="regValue(this);"   ${actionType=='detail'?'disabled':''}/>
					    <span class="input-group-addon"> <span class="currencyArea"></span>/<span class="numArea"></span>*<span class="unitArea"></span> </span>
				   </div>
				    <label class="control-label" style="margin-left:15px;margin-top:-7px">Base Charge:</label>
				   <div class="input-group">
					    <input type="text" id="baseVal"  name="baseVal"  class="form-control" placeholder="Fixed Amount"  value="${d.baseVal }"  maxlength="9"  style="width:60px;"  onblur="regValue(this);"  ${actionType=='detail'?'disabled':''}/>
					    <span class="input-group-addon"> <span class="currencyArea"></span> </span>
				   </div>
			  </div>
			</c:forEach>
			</c:when>
			<c:otherwise>
			  <div class="simple" >
				   <div class="input-group">
					    <input type="text" id="rateVal"  name="rateVal" class="form-control"  value="0"  maxlength="9"   style="width:115px;"  onblur="regValue(this);"   ${actionType=='detail'?'disabled':''}/>
					    <span class="input-group-addon"> <span class="currencyArea"></span>/<span class="numArea"></span>*<span class="unitArea"></span> </span>
				   </div>
				    <label class="control-label" style="margin-left:15px;margin-top:-7px">Base Charge:</label>
				   <div class="input-group">
					    <input type="text" id="baseVal"  name="baseVal"  class="form-control"  value="0"  maxlength="9"   style="width:115px;"  onblur="regValue(this);"  ${actionType=='detail'?'disabled':''}/>
					    <span class="input-group-addon"> <span class="currencyArea"></span> </span>
				   </div>
			  </div>
			</c:otherwise>
		</c:choose>
  </div>
  
  <table class="table table-bordered table-condensed table-advance ladder"  style="display:none;"  id="ladderFeeTab" >
   <thead>
    <tr>
     <th></th>
     <th>${local["eaap.op2.portal.price.arrange"]}<span class="label label-yellow"><span class="numArea"></span>*<span class="unitArea"></span></span></th>
     <th>${local["eaap.op2.portal.price.fee"]}<span class="label label-yellow"><span class="currencyArea"></span>/<span class="numArea"></span>*<span class="unitArea"></span></span></th>
     <th>${local["eaap.op2.portal.price.priceType2"]}<span class="label label-yellow"><span class="currencyArea"></span></span></th>
     <th style="<c:if test="${actionType=='detail'}">display:none;</c:if>"><i class="fa fa-plus addBut" ></i></th>
    </tr>
   </thead>
   <tbody>
			<c:choose>
				<c:when test="${basicTariff.rateType==2}">
					<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
					<tr id="${d.segMentId }"><td>${vs.count}</td>
						<td>
							 <div class='input-group input-xs'>
								  <input type='text'  id='startval'  name='startval'  class='form-control'  value="${d.startval }" maxlength="9"   onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   ${actionType=='detail'?'disabled':''}/>
								  <span class='input-group-addon'>To</span>
								  <input type='text'  id='endVal'  name='endVal'  class='form-control'  value="${d.endVal }"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"   ${actionType=='detail'?'disabled':''}/>
						 	</div>
						 </td>
						 <td>
						 		<input type='text'  id='rateVal'  name='rateVal'  value="${d.rateVal }" class='form-control input-xs'  maxlength="9" onblur="regValue(this);"  ${actionType=='detail'?'disabled':''}/>
						 </td>
						 <td>
								<input type='text'  id='baseVal'  name='baseVal'  value="${d.baseVal }"  class='form-control input-xs'  maxlength="9" onblur="regValue(this);"  ${actionType=='detail'?'disabled':''}/>
						 </td>
						<td  style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
							<c:if test="${vs.count >1}">
							<a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a>
							</c:if>
					  	</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>		    
				<tr id="1"><td>1</td>
					<td>
						 <div class='input-group input-xs'>
							  <input type='text'  id='startval'  name='startval'  class='form-control' value="0"  maxlength="9"  onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"   ${actionType=='detail'?'disabled':''}/>
							  <span class='input-group-addon'>To</span>
							  <input type='text'  id='endVal'  name='endVal'  class='form-control'  value="-1"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"   ${actionType=='detail'?'disabled':''}/>
					 	</div>
					 </td>
					 <td>
					  		<input type='text' value='0' id='rateVal'  name='rateVal'  placeholder='' class='form-control input-xs'  maxlength="9" onblur="regValue(this);"   ${actionType=='detail'?'disabled':''}/>
					 </td>
					 <td>
						<input type='text'  value='0' id='baseVal'  name='baseVal'  placeholder='' class='form-control input-xs'  maxlength="9" onblur="regValue(this);"  ${actionType=='detail'?'disabled':''}/>
					 </td>
					<td style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
						<!-- a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a -->
				  	</td>
				</tr>
				</c:otherwise>
			</c:choose>
   </tbody>
  </table>
    
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]} :</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"   id="description" name="description"   ${actionType=='detail'?'disabled':''}>${componentPrice.description }</textarea>
 </div>
</div>

<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4 control-labcel"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateBasicTariff(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>