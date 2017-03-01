<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceTypeName 		= "${local['eaap.op2.portal.price.priceType9']}";
var _prodDateStartEnd 	= "${local['eaap.op2.portal.pardProd.prodDateStartEnd']}";
</script>
<form id="ratingDiscountForm" name="ratingDiscountForm" action="${APP_PATH}/pardOfferPricePlan/saveOrUpdateRatingDiscount.shtml" method="post">
<div style="display:none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="numberator" id="numberator" value="${ratingDiscount.numberator }"/>
	 <input type="text" name="demominator" id="demominator" value="${ratingDiscount.demominator }"/>
	 <input type="text" name="currencyUnitType" id="currencyUnitType" value="${ratingDiscount.currencyUnitType}"/>
	 <input type="text" name="maxiMum" id="maxiMum" value="${ratingDiscount.maxiMum}"/>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.priceName']}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-xlarge" placeholder="" id="priceName" name="priceName"  size="44" value="${componentPrice.priceName }"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="" data-original-title=" ${local['eaap.op2.portal.pardProd.product.timeRange']}"></i>
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.ratingDiscount.timeRange']}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-xlarge" placeholder=""  id="segName"   name="segName"  value="${ratingDiscount.segName }"  readonly="readonly" />
  <span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
  		<button data-target="#attributeModal" data-toggle="modal" class="btn theme-btn" type="button"  id="timeModal"><i class="fa fa-plus"></i></button> 
  </span>
  <input type="text" id="segId" name="segId" value="${ratingDiscount.segId }" style="position:absolute; top:-100000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.priceTime"]}:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2012-10-11" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
      <input type="text"  id="formatEffDate" name="formatEffDate"  class="form-control od"   style="width:120px !important;"  value="${componentPrice.formatEffDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
	   <span class="input-group-addon"> To </span>
	   <input type="text"  id="formatExpDate" name="formatExpDate"  class="form-control od"   style="width:120px !important;"  value="${componentPrice.formatExpDate }" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
  </div>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.objectType']}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge baseItemType"  id="baseItemType"  name="baseItemType"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${baseItemTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==ratingDiscount.baseItemType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.priceObject"]}:</label>
 <div class="col-md-8 form-inline"  id="poJstreeDiv" style="width:auto;">
	<div class="input-icon right input-large"  style="width:485px !important;">
		<i class="glyphicon glyphicon-tree-deciduous"></i>
	   <input type="text" class="form-control input-xlarge  dropdown-toggle2"  id="itemNames" name="itemNames"   style="width:485px !important;" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
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
	 <a data-content="${local['eaap.op2.portal.pardProd.product.ratingType']}"  class="fa fa-question-circle"  title="" data-toggle="popover" data-placement="top" href="javascript:;" data-original-title="Popover title"></a>
	 <font color="FF0000">*</font>${local['eaap.op2.portal.price.ratingDiscount.ratingType']}:</label>
	 <div class="col-md-8 form-inline">
		 <select class="form-control input-xlarge"  id="discountType" name="discountType"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
			<c:forEach var="obj" items="${ratingTypeList}" varStatus="status">
				<option value="${obj.cepCode}"   <c:if test="${obj.cepCode==ratingDiscount.discountType}">selected</c:if>>${obj.cepName} </option>
			</c:forEach>
		</select>
	 </div>
</div>

<div class="form-group"  id="feeTr">
  <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:</label>
  <div class="col-md-8 form-inline">
  
     <table  class="table table-bordered" style="width:90%"  id="fixedTab" style="display: none;">
  		<tr>
    		<td width="25%" class="text-right"><font color="FF0000">*</font>${local['eaap.op2.portal.price.ratingDiscount.ratingMoney']}:</td>
    		<td width="75%">
               		<div style="float:left">
               		<c:choose>
               			<c:when test="${ratingDiscount.discountType==1 }">
               				<input id="fixedTabMaxiMum"  class="form-control" size="15"  type="text" value="${ratingDiscount.maxiMum }"  onblur="regValue(this);"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
               			</c:when>
               			<c:otherwise>
               				<input id="fixedTabMaxiMum"  class="form-control" size="15"  type="text" onblur="regValue(this);" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
               			</c:otherwise>
               		</c:choose>
               		</div>
	                <div id="dosubmit2" style="display:block;float:left;">
		 				<select class="form-control input-medium"   id="fixedTabCurrencyUnitType"   <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
					      	<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status" >
								<option value="${obj.cepCode}"   <c:if test="${obj.cepCode==ratingDiscount.currencyUnitType}">selected</c:if>>${obj.cepName} </option>
							</c:forEach>
						</select>
			       </div>
			</td>
  		</tr>
	</table>
  
     <table  class="table table-bordered" style="width:90%" id="percentyTab" style="display: none; ">
  		<tr>
    		<td width="25%" class="text-right"><font color="FF0000">*</font>${local['eaap.op2.portal.price.ratingDiscount.rating']}:</td>
    		<td width="75%">
	    		<input type="text" class="form-control" placeholder=""  style="width:150px;" id="percentyTabNumberator"  name="numberator"  value="${ratingDiscount.numberator }"    onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
	    		<span> / </span>
	    		<input type="text" class="form-control" placeholder=""  style="width:150px;" id="percentyTabDemominator"  name="demominator" value="${ratingDiscount.demominator }"    onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
    		</td>
  		</tr>
		<tr>
    		<td  class="text-right"><font color="FF0000">*</font>${local['eaap.op2.portal.price.ratingDiscount.maxRatingMoney']}:</td>
    		<td>
						<div style="float:left">
                			<c:choose>
	                			<c:when test="${ratingDiscount.discountType==0 }">
	                				<input id="percentyTabMaxiMum"  class="form-control" style="width:150px;" size="15"  type="text" value="${ratingDiscount.maxiMum }"  onblur="regValue(this);" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
	                			</c:when>
	                			<c:otherwise>
	                				<input id="percentyTabMaxiMum"  class="form-control" style="width:150px;" size="15"  type="text" value="" onblur="regValue(this);" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
	                			</c:otherwise>
                			</c:choose>
                		</div>
		                <div id="dosubmit2" style="float:left;">
			 				<select class="form-control input-medium"   id="percentyTabCurrencyUnitType"  style="width:162px !important;"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
						      	<c:forEach var="obj" items="${currencyUnitTypeList}" varStatus="status" >
									<option value="${obj.cepCode}" <c:if test="${obj.cepCode==ratingDiscount.currencyUnitType}">selected</c:if>>${obj.cepName}</option>
								</c:forEach>
							</select>
				       </div>
            </td>
  		</tr>
	</table>
	
	
 </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label">${local['eaap.op2.portal.price.priceDesc']}:</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"  id="description"  name="description" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>${componentPrice.description }</textarea>
 </div>
</div>

<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateRatingDiscount(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>




<!-- SelectTimeRangeModal Start:  -->
<div id="selectTimeRangeModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header"  style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">${local['eaap.op2.portal.pardProd.select.timerang']}</h4>
    <hr/>
    <div class="search-form-default">
      <form action="#" class="form-inline">
      	 <label class="control-label">${local['eaap.op2.portal.price.ratingDiscount.timeSpec.idOrCode']}:</label>
        <input type="text" id="idOrName"  name="idOrName" placeholder="Name/ID" class="form-control input-medium" >
      	 <label class="control-label">${local['eaap.op2.portal.price.ratingDiscount.timeSpec.type1']}:</label>
        <select  name="dateMode"  id="dateMode"  class="form-control input-medium"   onchange="">
           	<option value=""></option>
	      	<c:forEach var="obj" items="${datePatternList}" varStatus="status" >
				<option value="${obj.cepCode}" >${obj.cepName} </option>
			</c:forEach>
		</select>
      	 <label class="control-label">${local['eaap.op2.portal.price.ratingDiscount.timeSpec.type2']}:</label>
        <select  name="timeMode"  id="timeMode"  class="form-control input-medium"  onchange="">
           	<option value=""> </option>
	      	<c:forEach var="obj" items="${timePatternList}" varStatus="status" >
				<option value="${obj.cepCode}" >${obj.cepName} </option>
			</c:forEach>
		</select>
        <button class="btn default" type="button" id="timeQuery" >${local['eaap.op2.portal.devmgr.query']}</button></form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="timeRangeDT">
      <thead>
        <tr>
          <th></th>
          <th>${local['eaap.op2.portal.pardSpec.name']}</th>
          <th>${local['eaap.op2.portal.price.ratingDiscount.timeSpec.type1']}</th>
          <th>${local['eaap.op2.portal.price.ratingDiscount.timeSpec.type2']}</th>
          <th>${local['eaap.op2.portal.pardSpec.note']}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default timeCancel">${local['eaap.op2.portal.doc.cancel']}</button>
    <button type="submit" class="btn theme-btn timeOK" data-dismiss="modal">${local['eaap.op2.portal.doc.submit']}</button>
  </div>
</div>
<!-- SelectTimeRangeModal  End-->