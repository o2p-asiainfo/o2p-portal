<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceTypeName 	= "${local['eaap.op2.portal.price.priceType3']}";
</script>
<form id="oneTimeChargeForm" name="oneTimeChargeForm" action="${APP_PATH}/pardOfferPricePlan/saveOrUpdateRatingDiscount.shtml" method="post">
<div style="display:none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="other" id="other" value=""/>
	  <input type="text" name="offerId" id="offerId" value=""/>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-xlarge" placeholder="" name="priceName" id="priceName"  value="${componentPrice.priceName}"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font> ${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge"   id="taxIncluded"  name="taxIncluded"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${taxTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==oneTimeCharge.taxIncluded}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2012-10-11" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
   <input type="text"  id="formatEffDate" name="formatEffDate"  class="form-control od"  style="width:120px !important;"  value="${componentPrice.formatEffDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
   <span class="input-group-addon"> To </span>
   <input type="text"  id="formatExpDate" name="formatExpDate"  class="form-control od"   style="width:120px !important;" value="${componentPrice.formatExpDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
  </div>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.serviceType"]}:</label>
 <div class="col-md-8 form-inline">
 	<input type="text" class="form-control input-xlarge"  name="businessItemName"  id="businessItemName"  value="${oneTimeCharge.businessItemName }"  readonly="readonly" />
 	<span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
   		<button data-toggle="modal" class="btn theme-btn" type="button"  id="opener_odrPrice4"><i class="fa fa-plus"></i></button> 
	</span>
	<input type="text" id="businessItem" name="businessItem" value="${oneTimeCharge.businessItem }"  style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceSubject"]}:</label>
 <div class="col-md-8 form-inline">
    <input type="text" class="form-control  input-xlarge" name="oneTimeItemName"   id="oneTimeItemName"  value="${oneTimeCharge.oneTimeItemName }"  readonly="readonly" />
   <span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
   		<button data-toggle="modal" class="btn theme-btn" type="button"  id="opener_odrPrice3"><i class="fa fa-plus"></i></button> 
	</span>
	<input type="text" id="oneTimeItem" name="oneTimeItem"  value="${oneTimeCharge.oneTimeItem }"  style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.costType"]}:</label>
 <div class="col-md-8 form-inline">
 <select class="form-control input-xlarge"  id="chargeType"  name="chargeType"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${changeTypeList}" varStatus="status" >
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==oneTimeCharge.chargeType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font> ${local["eaap.op2.portal.price.fee"]}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-medium"  id="currencyUnitVal"  name="currencyUnitVal"  value="${oneTimeCharge.currencyUnitVal }"  onblur="regValue(this);"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
  <select class="form-control input-medium"   id="currencyUnitType"  name="currencyUnitType"   <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status" >
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==oneTimeCharge.currencyUnitType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label"><i title="Indicates the relative priority across all items of the same type where a value of 1 is the highest priority and a value of 10 would be the lowest priority item"
         data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> ${local["eaap.op2.portal.price.priority"]}:</label>
  <div class="col-md-8 form-inline">
<!--    <input type="text" class="form-control input-medium"  id="billingPriority" name="billingPriority"  maxlength="22"  -->
<!-- 	onkeyup="this.value=this.value.replace(/\D/g,'')"	onafterpaste="this.value=this.value.replace(/\D/g,'')"  -->
<%--    	<c:if test="${not empty oneTimeCharge.billingPriority }">value="${oneTimeCharge.billingPriority }"</c:if> --%>
<%-- 	<c:if test="${empty oneTimeCharge.billingPriority }">value="500"</c:if>  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/> --%>
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
  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.price.priceDesc"]}:</label>
  <div class="col-md-8">
  		<textarea rows="3" class="form-control"   id="description" name="description"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>${componentPrice.description }</textarea>
   </div>
</div>

<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateOneTimeFee(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>


<!-- SelectPriceItem4Modal Start:  -->
<div id="selectPriceItem4Modal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header"  style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local["eaap.op2.portal.price.selectPriceItem"] }</h4>
    <hr/>
    <div class="search-form-default" >
      <form action="#" class="form-inline" >
			<input id="itemTypes" name="itemTypes" type="hidden" value="4" />
	      	<label class="control-label">${local["eaap.op2.portal.price.itemIdOrName"]}:</label>
	        <input type="text" id="itemIdOrName"  name="itemIdOrName" placeholder="" class="form-control input-medium" >
			<label class="control-label">${local["eaap.op2.portal.price.itemType"]}:</label>
	        <select  name="itemType"  id="itemType"  class="form-control input-medium"   onchange="">
		   		<c:forEach var="obj" items="${itemTypeList}" varStatus="status">
		   			<c:if test="${obj.cepCode==4}">
					<option value="${obj.cepCode}"  selected>${obj.cepName} </option>
					</c:if>
		    	</c:forEach>
			</select>
	        <button class="btn default" type="button" id="priceItem4Query" >${local['eaap.op2.portal.devmgr.query']}</button></form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="priceItem4DT">
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
    <button type="button" class="btn btn-default priceItem4Cancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn priceItem4OK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>
<!-- SelectpriceItem4Modal  End-->


<!-- SelectPriceItem3Modal Start:  -->
<div id="selectPriceItem3Modal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header"  style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local["eaap.op2.portal.price.selectPriceItem"] }</h4>
    <hr/>
    <div class="search-form-default">
      <form action="#" class="form-inline">
			<input id="itemTypes" name="itemTypes" type="hidden" value="3" />
	      	<label class="control-label">${local["eaap.op2.portal.price.itemIdOrName"]}:</label>
	        <input type="text" id="itemIdOrName"  name="itemIdOrName" placeholder="" class="form-control input-medium" >
			<label class="control-label">${local["eaap.op2.portal.price.itemType"]}:</label>
	        <select  name="itemType"  id="itemType"  class="form-control input-medium"   onchange="">
		   		<c:forEach var="obj" items="${itemTypeList}" varStatus="status">
		   			<c:if test="${obj.cepCode==3}">
					<option value="${obj.cepCode}"  selected>${obj.cepName} </option>
					</c:if>
		    	</c:forEach>
			</select>
	        <button class="btn default" type="button" id="priceItem3Query" >${local['eaap.op2.portal.devmgr.query']}</button></form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="priceItem3DT">
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
    <button type="button" class="btn btn-default priceItem3Cancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn priceItem3OK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>
<!-- SelectpriceItem3Modal  End-->