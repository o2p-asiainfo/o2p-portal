<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceTypeName 		= "${local['eaap.op2.portal.price.priceType2']}";
</script>
<form id="recurringChargeForm" name="recurringChargeForm" action="${APP_PATH}/pardOfferPricePlan/saveOrUpdateRecurringFee.shtml" method="post">
<div style="display:none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="offerId" id="offerId" value=""/>
	 
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-xlarge" placeholder=""  id="priceName" name="priceName"  size="44" type="text" value="${componentPrice.priceName }"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge"   id="taxIncluded"  name="taxIncluded"   <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
   		<c:forEach var="obj" items="${taxTypeList}" varStatus="status">
			<option value="${obj.cepCode}"   <c:if test="${obj.cepCode==pecurringFee.taxIncluded}">selected</c:if>>${obj.cepName} </option>
    	</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-medium"   id="currencyUnitVal" name="currencyUnitVal" value="${pecurringFee.currencyUnitVal }"  maxlenght="9" onblur="regValue(this);"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
  <select class="form-control input-medium"   id="currencyUnitType"  name="currencyUnitType"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
   		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status">
			<option value="${obj.cepCode}"   <c:if test="${obj.cepCode==pecurringFee.currencyUnitType}">selected</c:if>>${obj.cepName} </option>
    	</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label" >
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceSubject"]}:</label>
 <div class="col-md-8 form-inline">
   <input type="text" class="form-control input-xlarge"  name="itemName" id="itemName"  value="${pecurringFee.itemName }"  readonly="readonly">
   <span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
   		<button data-target="#attributeModal" data-toggle="modal" class="btn theme-btn" type="button"  id="opener_odrPrice"><i class="fa fa-plus"></i></button> 
   </span>
   <input type="text" id="itemId" name="itemId" value="${pecurringFee.itemId }"  style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label"><i title="Indicates the relative priority across all items of the same type where a value of 1 is the highest priority and a value of 10 would be the lowest priority item"
         data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i>${local["eaap.op2.portal.price.priority"]}:</label>
 <div class="col-md-8 form-inline">
<!--    <input type="text" class="form-control input-medium"   id="billingPriority" name="billingPriority" class="input-text"  maxlenght="9"  type="text"  -->
<!-- 	onkeyup="this.value=this.value.replace(/\D/g,'')"	onafterpaste="this.value=this.value.replace(/\D/g,'')" -->
<%-- 	<c:if test="${not empty pecurringFee.billingPriority }">value="${pecurringFee.billingPriority }"</c:if> --%>
<%-- 	<c:if test="${empty pecurringFee.billingPriority }">value="500"</c:if>  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/> --%>
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
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2012-10-11" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
      <input type="text"  id="formatEffDate" name="formatEffDate"  class="form-control od"   style="width:120px !important;"  value="${componentPrice.formatEffDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
	   <span class="input-group-addon"> To </span>
	   <input type="text"  id="formatExpDate" name="formatExpDate"  class="form-control od"  style="width:120px !important;" value="${componentPrice.formatExpDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
  </div>
 </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label">${local["eaap.op2.portal.price.priceDesc"]}:</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"  id="description" name="description"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>${componentPrice.description }</textarea>
 </div>
</div>

<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateRecurringFee(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>


<!-- SelectPriceItemModal Start:  -->
<div id="selectPriceItemModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local["eaap.op2.portal.price.selectPriceItem"] }</h4>
  </div>
  <div class="modal-body">  
    <div class="search-form-default">
      <form action="#" class="form-inline">
      <input id="itemTypes" name="itemTypes" type="hidden" value="2" />
          <label class="control-label">${local["eaap.op2.portal.price.itemIdOrName"]}:</label>
          <input type="text" id="itemIdOrName"  name="itemIdOrName" placeholder="" class="form-control input-medium" >
      <label class="control-label">${local["eaap.op2.portal.price.itemType"]}:</label>
          <select  name="itemType"  id="itemType"  class="form-control input-medium"   onchange="">
          <c:forEach var="obj" items="${itemTypeList}" varStatus="status">
            <c:if test="${obj.cepCode==2}">
          <option value="${obj.cepCode}"  selected>${obj.cepName} </option>
          </c:if>
          </c:forEach>
      </select>
          <button class="btn default" type="button" id="priceItemQuery" >${local['eaap.op2.portal.devmgr.query']}</button>
      </form>
    </div>
    <table class="table table-bordered table-hover nowrap-ingore group-check" id="priceItemDT">
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
    <button type="button" class="btn btn-default priceItemCancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn priceItemOK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>
<!-- SelectPriceItemModal  End-->