<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceType1 				= "${local['eaap.op2.portal.price.priceType1']}";
var _operationTips 		= "${local['eaap.op2.portal.price.priceTime']}";
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
  <input type="text" ${readonly} class="form-control input-medium" placeholder=""  id="priceName" name="priceName"  size="44"  value="${componentPrice.priceName }">
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium" ${readonly}  id="taxIncluded"  name="taxIncluded" >
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
   <input type="text" ${readonly}  id="formatEffDate"  style="width:120px" name="formatEffDate"  value="${componentPrice.formatEffDate }"  class="form-control od">
   <span class="input-group-addon"> To </span>
   <input type="text" ${readonly}  id="formatExpDate"  style="width:120px" name="formatExpDate"  value="${componentPrice.formatExpDate }" class="form-control od">
  </div>
 </div>
</div>

<div class="form-group" style="display:none">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.objectType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium baseItemType" ${readonly} id="baseItemType"  name="baseItemType"  >
		<c:forEach var="obj" items="${baseItemTypeList}" varStatus="status">
			<option value="${obj.cepCode}"    <c:if test="${obj.cepCode==50014}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group" style="display:none">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceObject"]}:</label>
 <div class="col-md-8 form-inline"  id="poJstreeDiv" style="width:auto;">
	  <div class="input-icon right input-large">
		<i class="glyphicon glyphicon-tree-deciduous"></i>
	   <input type="text" class="form-control input-large  dropdown-toggle2"  id="itemNames" name="itemNames">
	   <input type="hidden"  id="itemIds" name="itemIds"/>
	   <div class="dropdown-menu jstree" role="menu">
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
  <input type="text" ${readonly} class="input-medium form-control"  id="ratingUnitVal" name="ratingUnitVal" size="15" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
	   <c:if test="${not empty basicTariff.ratingUnitVal }">value="${basicTariff.ratingUnitVal }"</c:if>
	   <c:if test="${empty basicTariff.ratingUnitVal }">value="1"</c:if>
    >
  <select ${readonly} class="form-control input-medium chargingUnit"   id="ratingUnitType"  name="ratingUnitType" ></select>
 </div>
 <div class="col-md-8 form-inline"  id="loadingImg_unit"  style="width:30px; padding:9px 0 0 0; display:none;"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceUnit2"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium currency"  ${readonly} id="currencyUnitType"  name="currencyUnitType" >
		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status" >
                <option value="${obj.cepCode}"  <c:if test="${obj.cepCode==basicTariff.currencyUnitType}">selected</c:if>>${obj.cepName}</option>
				</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
	 <label class="col-md-4 control-label">
	  <font color="FF0000">*</font>${local["eaap.op2.portal.price.feeType"]}:</label>
	 <div class="col-md-8 form-inline">
		  <div class="radio-list" data-error-container="#here2">
			   <label class="radio-inline">
			    <input type="radio" ${readonly}  name="rateType" id="rateType1" value="1" <c:if test="${basicTariff.rateType==1 || empty basicTariff.rateType}">checked="checked"</c:if>> ${local["eaap.op2.portal.price.simple"]} </label>
			   <label class="radio-inline">
			    <input type="radio" ${readonly}  name="rateType" id="rateType2" value="2" <c:if test="${basicTariff.rateType==2}">checked="checked"</c:if>> ${local["eaap.op2.portal.price.ladder"]} </label>
		  </div>
	 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
 <div class="col-md-8 form-inline">
 <table class="table table-bordered table-condensed table-advance ladder"  id="simpleFeeTab" style="display: block;" >
 <c:choose>
  <c:when test="${basicTariff.rateType==1}">
  <c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
  <div class="simple">
	   <div class="input-group">
		    <input type="text" id="rateVal" ${readonly} name="rateVal"  value="${d.rateVal }" class="form-control">
		    <span class="input-group-addon"> <span class="currencyArea"></span>/<span class="numArea">3</span>*<span class="unitArea"></span> </span>
	   </div>
	     <label class="control-label" style="margin-left:15px;margin-top:-7px">Base Charge:</label>
	   <div class="input-group">
		    <input type="text" id="baseVal" ${readonly} name="baseVal"  class="form-control" value="${d.baseVal }" placeholder="Fixed Amount">
		    <span class="input-group-addon"> <span class="currencyArea"></span> </span>
	   </div>
  </div>
  </c:forEach>
  </c:when>
  <c:otherwise>
  <div class="simple">
	   <div class="input-group">
		    <input type="text" id="rateVal" ${readonly} name="rateVal" value=0  class="form-control">
		    <span class="input-group-addon"> <span class="currencyArea"></span>/<span class="numArea">3</span>*<span class="unitArea"></span> </span>
	   </div>
	     <label class="control-label" style="margin-left:15px">Base Charge:</label>
	   <div class="input-group">
		    <input type="text" id="baseVal" ${readonly} name="baseVal" value=0  class="form-control" placeholder="Fixed Amount">
		    <span class="input-group-addon"> <span class="currencyArea"></span> </span>
	   </div>
  </div>
  </c:otherwise>
  </c:choose>
  </table>
  
  <table class="table table-bordered table-condensed table-advance ladder"  style="display:none;"  id="ladderFeeTab" >
   <thead>
    <tr>
     <th><i class="fa fa-plus addBut" ></i></th>
     <th>${local["eaap.op2.portal.price.arrange"]}<span class="label label-yellow"><span class="numArea">3</span>*<span class="unitArea"></span></span></th>
     <th>${local["eaap.op2.portal.price.fee"]}<span class="label label-yellow"><span class="currencyArea"></span>/<span class="numArea">3</span>*<span class="unitArea"></span></span></th>
     <th>${local["eaap.op2.portal.price.priceType2"]}<span class="label label-yellow"><span class="currencyArea"></span></span></th>
     <th>${local["eaap.op2.portal.price.Opertion"]}</th>
    </tr>
   </thead>
   <tbody>
        <c:choose>
			<c:when test="${basicTariff.rateType==2}">
				<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
				<tr id="${d.segMentId }" >
					<td >1</td>
					<td>
						 <div class='input-group input-xs'>
							  <input type='text'  id='startVal'  name='startVal'  class='form-control' value='${d.startval }' onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"/>
							  <span class='input-group-addon'>To</span>
							  <input type='text'  id='endVal'  name='endVal'  class='form-control'   value="${d.endVal }"  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')">
					 	</div>
					 </td>
					 <td>
					  		<input type='text' id='rateVal'  name='rateVal' value="${d.rateVal }"  placeholder='' class='form-control input-xs' onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')" >
					 </td>
					 <td>
						<input type='text'   id='baseVal'  name='baseVal' value="${d.baseVal }"  placeholder='' class='form-control input-xs'  onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')">
					 </td>
					<td>
						<a class='btn default btn-sm black btn-del' href='#' onclick="$(this).closest('tr').remove()"><i class='fa fa-trash-o' ></i>Delete</a>
				  	</td>
			    </tr> 
				</c:forEach>
			</c:when>
		
		<c:otherwise>
			<tr><td>1</td>
				<td>
					 <div class='input-group input-xs'>
						  <input type='text'  id='startVal'  name='startVal'  class='form-control' value="0" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"/>
						  <span class='input-group-addon'>To</span>
						  <input type='text'  id='endVal'  name='endVal'  class='form-control'  value="-1"  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')">
				 	</div>
				 </td>
				 <td>
				  		<input type='text' value='0' id='rateVal'  name='rateVal'  placeholder='' class='form-control input-xs' onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')" >
				 </td>
				 <td>
					<input type='text'  value='0' id='baseVal'  name='baseVal'  placeholder='' class='form-control input-xs'  onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')">
				 </td>
				<td>
					<a class='btn default btn-sm black btn-del' href='#' onclick="$(this).closest('tr').remove()"><i class='fa fa-trash-o' ></i>Delete</a>
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
  <textarea rows="3" class="form-control" ${readonly}  id="description" name="description">${componentPrice.description }</textarea>
 </div>
</div>

<div style="text-align:center;">
        <c:if test="${readonly!='readonly'}">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateBasicTariff(this)"> ${local['eaap.op2.portal.doc.submit']} <i class="m-icon-swapright m-icon-white"></i></button> 		
		</c:if>
		<button type="button" class="btn theme-btn button-finish"  onclick="pricePlanFinish()"> Finish <i class="m-icon-swapright m-icon-white"></i></button>
		
</div>
 
</form>