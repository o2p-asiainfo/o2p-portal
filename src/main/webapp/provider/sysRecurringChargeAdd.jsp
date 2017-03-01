<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form id="recurringChargeForm" name="recurringChargeForm" action="${contextPath}/pardOfferPricePlan/saveOrUpdateRecurringFee.shtml" method="post">
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceName"]}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-medium" placeholder=""  id="priceName" name="componentPrice.priceName"  size="44" type="text" value="${componentPrice.priceName }"/>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceType"]}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium"   id="taxIncluded"  name="pecurringFee.taxIncluded" >
   		<c:forEach var="obj" items="${taxTypeList}" varStatus="status">
			<option value="${obj.cepCode}">${obj.cepName} </option>
    	</c:forEach>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-medium"   id="currencyUnitVal" name="pecurringFee.currencyUnitVal" value="${pecurringFee.currencyUnitVal }" >
  <select class="form-control input-medium"   id="currencyUnitType"  name="pecurringFee.currencyUnitType" >
   		<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status">
			<option value="${obj.cepCode}">${obj.cepName} </option>
    	</c:forEach>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceSubject"]}:</label>
 <label class="col-md-4 control-label"></label>
 <div class="col-md-8 form-inline">
   <input type="text" class="form-control input-medium"  name="pecurringFee.itemName" id="itemName"  value="${pecurringFee.itemName }" textSize="25" disabled="true">
   <input type="hidden" id="itemId" name="pecurringFee.itemId" value="${pecurringFee.itemId }"/>
   <span class="input-group-btn" style="display:inline-block">
   <button data-target="#attributeModal" data-toggle="modal" class="btn theme-btn" type="button"><i class="fa fa-plus"></i></button> 
   </span>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">${local["eaap.op2.portal.price.priority"]}:</label>
 <div class="col-md-8 form-inline">
   <input type="text" class="form-control input-medium"   id="billingPriority" name="pecurringFee.billingPriority" class="input-text" size="22"  type="text" 
   	onkeypress="javascript:InputIntNumberCheck();"
	<c:if test="${not empty pecurringFee.billingPriority }">value="${pecurringFee.billingPriority }"</c:if>
	<c:if test="${empty pecurringFee.billingPriority }">value="500"</c:if>/>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
   <input type="text"  class="form-control od"  id="btime" name="componentPrice.formatEffDate" value="${componentPrice.formatEffDate}">
   <span class="input-group-addon"> To </span>
   <input type="text"  class="form-control od"  id="etime" name="componentPrice.formatExpDate" value="${componentPrice.formatExpDate}">
  </div>
 </div>
</div>
<div class="form-group">
  <label class="col-md-4 control-label">${local["eaap.op2.portal.price.priceDesc"]}:</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"  id="description" name="componentPrice.description">${componentPrice.description }</textarea>
 </div>
</div>

<div style="text-align:center;">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateRecurringFee()"> Save <i class="m-icon-swapright m-icon-white"></i></button>
</div>
</form>