<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceTypeName 		= "${local['eaap.op2.portal.price.priceType4']}";
var _endVal 					= "${local['eaap.op2.portal.price.basic.endVal']}";
var _greater 					= "${local['eaap.op2.portal.price.basic.greater']}";
var _prodDateStartEnd 	= "${local['eaap.op2.portal.pardProd.prodDateStartEnd']}";
var _endWith1 				= "${local['eaap.op2.portal.price.basic.endWith-1']}";
var _equal1 					= "${local['eaap.op2.portal.price.basic.equal1']}";
var _equal2 					= "${local['eaap.op2.portal.price.basic.equal2']}";
var _notNull1 					= "${local['eaap.op2.portal.price.basic.notNull1']}";
var _endWith0d 				= "${local['eaap.op2.portal.price.billingDiscount.endWith0d']}";
var _nLessThand 			= "${local['eaap.op2.portal.price.billingDiscount.nLessThand']}";
var _dNotNull 				= "${local['eaap.op2.portal.price.billingDiscount.dNotNull']}";
var _nNotNull 				= "${local['eaap.op2.portal.price.billingDiscount.nNotNull']}";

var regValue = function (obj){
	var val = $(obj).val();
	if(/^\d*(\d|(\.{0,1}[0-9]{1,2}))$/.test(val)){
		$(obj).val(val);
	}else{
		$(obj).val('');
	}
}
</script>
<form id="billingDiscountForm"  name="billingDiscountForm"  method="post">
<div style="display:none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="details" id="details" value=""/>
	 <input type="text" name="other" id="other" value=""/>
	 <input type="text" name="offerId" id="offerId" value=""/>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-xlarge" placeholder="" name="priceName" id="priceName"  value="${componentPrice.priceName}"   ${actionType=='detail'?'disabled':''}/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.reference"]}:</label>
 <div class="col-md-8 form-inline">
 	<input type="text" class="form-control input-xlarge"  name="eligibleItemName"  id="eligibleItemName"  value="${billingDiscount.eligibleItemName}"  readonly="readonly" />
 	<span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
   		<button data-toggle="modal" class="btn theme-btn  opener_odrPrice23" type="button"  id="opener_odrPrice23"  setName="eligibleItemName"  setId="eligibleItem"><i class="fa fa-plus"></i></button> 
	</span>
	<input type="text" id="eligibleItem" name="eligibleItem" value="${billingDiscount.eligibleItem }"  style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.contrast"]}:</label>
 <div class="col-md-8 form-inline">
 	<input type="text" class="form-control input-xlarge"  name="targetItemName"  id="targetItemName"  value="${billingDiscount.targetItemName }"  readonly="readonly" />
 	<span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
   		<button data-toggle="modal" class="btn theme-btn  opener_odrPrice23" type="button"  id="opener_odrPrice23"  setName="targetItemName"  setId="targetItem"><i class="fa fa-plus"></i></button> 
	</span>
	<input type="text" id="targetItem" name="targetItem" value="${billingDiscount.targetItem }"  style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
 <select class="form-control input-xlarge" id="taxIncluded"  name="taxIncluded"   ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${taxTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==billingDiscount.taxIncluded}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label"><i title="Indicates the relative priority across all items of the same type where a value of 1 is the highest priority and a value of 10 would be the lowest priority item"
         data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i>${local["eaap.op2.portal.price.priority"]}:</label>
  <div class="col-md-8 form-inline">
<!--    <input type="text" class="form-control input-medium"   id="billingPriority" name="billingPriority"   -->
<!-- 		onkeyup="this.value=this.value.replace(/\D/g,'')"	onafterpaste="this.value=this.value.replace(/\D/g,'')"  -->
<%-- 		<c:if test="${not empty billingDiscount.billingPriority }">value="${billingDiscount.billingPriority }"</c:if> --%>
<%-- 		<c:if test="${empty billingDiscount.billingPriority }">value="500"</c:if>  --%>
<%-- 		onkeypress="javascript:InputIntNumberCheck();"   ${actionType=='detail'?'disabled':''}/> --%>
<select class="form-control unit"  id="billingPriority" name="billingPriority"    ${actionType=='detail'?'disabled':''}>
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
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.maxDiscount"]}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="input-medium form-control"   id="maxiMum" name="maxiMum"  maxlength="9"  value="${billingDiscount.maxiMum }"  onblur="regValue(this);" ${actionType=='detail'?'disabled':''}/>
  <select class="form-control input-medium chargingUnit"  id="currencyUnitType" name="currencyUnitType"   ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==billingDiscount.currencyUnitType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2012-10-11" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
   <input type="text"  id="formatEffDate" name="formatEffDate"  class="form-control od"   style="width:120px !important;"  value="${componentPrice.formatEffDate }"  ${actionType=='detail'?'disabled':''}/>
   <span class="input-group-addon"> To </span>
   <input type="text"  id="formatExpDate" name="formatExpDate"  class="form-control od"   style="width:120px !important;" value="${componentPrice.formatExpDate }"  ${actionType=='detail'?'disabled':''}/>
  </div>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.calcType"]}:</label>
 <div class="col-md-8 form-inline">
  <div class="radio-list" data-error-container="#here2">
   <label class="radio-inline">
	   <input type="radio" value="0" name="calcType"  id="calcType1"   <c:if test="${billingDiscount.calcType==0 ||empty billingDiscount.calcType }">checked="checked"</c:if>   ${actionType=='detail'?'disabled':''}/> 
	   ${local["eaap.op2.portal.price.Section"]}
   </label>
   <label class="radio-inline">
	   <input type="radio" value="1" name="calcType"   id="calcType2"  <c:if test="${billingDiscount.calcType==1}">checked="checked"</c:if>   ${actionType=='detail'?'disabled':''}/> 
	   ${local["eaap.op2.portal.price.finalSection"]}
   </label>
  </div>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.promType"]}:</label>
 <div class="col-md-8 form-inline">
 <select class="form-control input-xlarge"  id="promType" name="promType"   ${actionType=='detail'?'disabled':''}>
		<c:forEach var="obj" items="${promTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==billingDiscount.promType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
  <div class="col-md-8 form-inline">
    <table class="table table-bordered table-condensed table-advance ladder"  style="display:one; border-top-width:0px;"  id="detailTab" >
	   <thead>
	    <tr>
	     <th style="width:95px;"></th>
	     <th style="text-align:center;">${local["eaap.op2.portal.price.arrange"]} &nbsp;(<span class="currencyArea"  id="unit1" ></span>)</th>
	     <th style="text-align:center;">${local["eaap.op2.portal.price.discount"]}</th>
	     <th style="width:95px; <c:if test="${actionType=='detail'}">display:none;</c:if>"><i class="fa fa-plus addBut"  title="${local['eaap.op2.portal.manager.auth.add']} "></i></th>
	    </tr>
	   </thead>
	   <tbody>   
			<c:choose>
				<c:when test="${fn:length(ratingCurverDetailList)>0 && billingDiscount.promType==1}">
					<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
						<tr id="${d.segMentId }"><td>${vs.count}</td>
						<td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="startval" name="startval"  class='form-control'  value="${d.startval }"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>~</span>
								  <input type='text'  id="endVal" name="endVal"  class='form-control'  value="${d.endVal }"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"  style="width:100px" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						 </td>
						 <td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="numerator" name="numerator"  value="${d.numerator }"  class='form-control'  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>/</span>
								  <input type='text'  id="denominator" name="denominator"  value="${d.denominator }"  class='form-control'  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						</td>
						<td style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
							<c:if test="${vs.count >1}">
							<a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a>
							</c:if>
					  	</td>
			    		</tr>
					</c:forEach>
				</c:when>
				<c:when test="${billingDiscount.promType!=1 || empty billingDiscount.promType }">
						<tr id="1"><td>1</td>
						<td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="startval" name="startval"  class='form-control'  value="0"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>~</span>
								  <input type='text'  id="endVal" name="endVal"  class='form-control'  value="-1"  maxlength="9"  onclick="select()"  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						 </td>
						 <td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="numerator" name="numerator"  value="100"  class='form-control'  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>/</span>
								  <input type='text'  id="denominator" name="denominator"  value="100"  class='form-control'  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						</td>
						<td style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
							<!-- a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a -->
					  	</td>
			    		</tr>					    
				</c:when>
			</c:choose>
	   </tbody>
	</table>
  
    <table class="table table-bordered table-condensed table-advance ladder"  style="display:none; border-top-width:0px;"  id="detailTab1" >
	   <thead>
	    <tr>
	     <th></th>
	     <th style="text-align:center;">${local["eaap.op2.portal.price.arrange"]} &nbsp;(<span class="label currencyArea"  id="unit1"></span>)</th>
	     <th style="text-align:center;">${local["eaap.op2.portal.price.priceType2"]} &nbsp;(<span class="label currencyArea"  id="unit1"></span>)</th>
	     <th style="width:95px; <c:if test="${actionType=='detail'}">display:none;</c:if>"><i class="fa fa-plus addBut"  title="${local['eaap.op2.portal.manager.auth.add']} "></i></th>
	    </tr>
	   </thead>
	   <tbody>
			<c:choose>
				<c:when test="${fn:length(ratingCurverDetailList)>0 && billingDiscount.promType!=1}">
					<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
						<tr id="${d.segMentId }"><td>${vs.count}</td>
						<td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="startval" name="startval"  class='form-control'  value="${d.startval }"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>~</span>
								  <input type='text'  id="endVal" name="endVal"  class='form-control'  value="${d.endVal }"  maxlength="9"  onclick="select()"  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')" style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						 </td>
						 <td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="baseVal" name="baseVal"  value="${d.baseVal}"  class='form-control'  maxlength="9"  onclick="select()" onblur="regValue(this);"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						</td>
						<td style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
							<c:if test="${vs.count >1}">
							<a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a>
							</c:if>
					  	</td>
			    		</tr>
					</c:forEach>
				</c:when>
				<c:when test="${billingDiscount.promType!=1 || not empty billingDiscount.promType }">
						<tr id="1"><td>1</td>
						<td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="startval" name="startval"  class='form-control'  value="0"  maxlength="9"  onclick="select()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
								  <span class='input-group-addon'>~</span>
								  <input type='text'  id="endVal" name="endVal"  class='form-control'  value="-1"  maxlength="9"  onclick="select()"  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')" style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						 </td>
						 <td  style="text-align:center;">
							 <div class='input-group input-xs' >
								  <input type='text'  id="baseVal" name="baseVal"  value="0"  class='form-control'  maxlength="9"  onclick="select()" onblur="regValue(this);"  style="width:100px"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
						 	</div>
						</td>
						<td style="<c:if test="${actionType=='detail'}">display:none;</c:if>">
							<!-- a class='btn default btn-xs'  onclick="$(this).closest('tr').remove()">Delete</a -->
					  	</td>
			    	</tr>
				</c:when>
			</c:choose>
	   </tbody>
	  </table>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]}:</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"  id="description" name="description"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>${componentPrice.description }</textarea> 
 </div>
</div>

<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateBillingDiscount(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>

<!-- SelectPriceItem23Modal Start:  -->
<div id="selectPriceItem23Modal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header" style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local["eaap.op2.portal.price.selectPriceItem"] }</h4>
    <hr/>
    <div class="search-form-default" >
      <form action="#" class="form-inline">
			<input id="itemTypes" name="itemTypes" type="hidden" value="2" />
	      	<label class="control-label">${local["eaap.op2.portal.price.itemIdOrName"]}:</label>
	        <input type="text" id="itemIdOrName"  name="itemIdOrName" placeholder="" class="form-control input-medium" >
			<label class="control-label">${local["eaap.op2.portal.price.itemType"]}:</label>
	        <select  name="itemType"  id="itemType"  class="form-control input-medium"   onchange="">
				<option value="" ></option>
		   		<c:forEach var="obj" items="${itemTypeList}" varStatus="status">
		   			<c:if test="${obj.cepCode==2 || obj.cepCode==3}">
					<option value="${obj.cepCode}" >${obj.cepName} </option>
					</c:if>
		    	</c:forEach>
			</select>
	        <button class="btn default" type="button" id="priceItem23Query" >${local['eaap.op2.portal.devmgr.query']}</button>
	    </form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="priceItem23DT">
      <thead>
        <tr>
          <th></th>
          <th>${local["eaap.op2.portal.price.itemId"]}</th>
          <th>${local["eaap.op2.portal.price.itemName"]}</th>
          <th>${local["eaap.op2.portal.price.itemType"]}</th>
          <th>${local["eaap.op2.portal.price.description"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default priceItem23Cancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn priceItem23OK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>
<!-- SelectpriceItem23Modal  End-->