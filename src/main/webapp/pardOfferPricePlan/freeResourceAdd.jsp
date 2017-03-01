<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
var _priceTypeName 		= "${local['eaap.op2.portal.price.priceType5']}";
var _endVal 					= "${local['eaap.op2.portal.price.basic.endVal']}";
var _endValNotNUll 		="${local['eaap.op2.portal.price.basic.endValNotNUll']}";
var _endValTip 				="${local['eaap.op2.portal.price.basic.endValTip']}";
var _greater 					= "${local['eaap.op2.portal.price.basic.greater']}";
var _prodDateStartEnd 	= "${local['eaap.op2.portal.pardProd.prodDateStartEnd']}";
var _simpleNotNull 		= "${local['eaap.op2.portal.price.basic.simpleNotNull']}";
var _endWith1 				= "${local['eaap.op2.portal.price.basic.endWith-1']}";
var _equal1 					= "${local['eaap.op2.portal.price.basic.equal1']}";
var _equal2 					= "${local['eaap.op2.portal.price.basic.equal2']}";
var _notNullBase 			= "${local['eaap.op2.portal.price.basic.notNullBase']}";
var _notNullRateVal 		= "${local['eaap.op2.portal.price.basic.notNullRateVal']}";
var _prodDateStartEnd 	= "${local['eaap.op2.portal.pardProd.prodDateStartEnd']}";
</script>
<form id="freeResourceForm" name="freeResourceForm" action="${APP_PATH}/pardOfferPricePlan/saveOrUpdateFreeResource.shtml" method="post">
<div style="display:none;">
	 <input type="text" name="actionType"  id="actionType"  value="${actionType}"/>
	 <input type="text" name="priPricingInfoId" id="priPricingInfoId" value="${componentPrice.priPricingInfoId}"/>
	 <input type="text" name="priceId" id="priceId" value="${componentPrice.priceId }"/>
	 <input type="text" name="numberator" id="numberator" value="${ratingDiscount.numberator }"/>
	 <input type="text" name="details" id="details" value=""/>
	 
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
  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="" data-original-title=" ${local['eaap.op2.portal.price.freeResourceItem']}"></i>
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.freeResourceItem']}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-xlarge" placeholder=""  id="freeresItemName"   name="freeresItemName"  value="${freeResource.freeresItemName }"  readonly="readonly" />
  <span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
  		<button data-target="#attributeModal" data-toggle="modal" class="btn theme-btn" type="button"  id="timeModal"><i class="fa fa-plus"></i></button> 
  </span>
  <input type="text" id="freeresItem" name="freeresItem" value="${freeResource.freeresItem}" style="position:absolute; top:-1000000px;"/>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.freeResourceCycle']}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge cycleRefMode"  id="cycleRefMode"  name="cycleRefMode"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${freeresourcecyclelist}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==freeResource.cycleRefMode}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group"  id="freeResourcePeriodDiv"  <c:if test="${freeResource.cycleRefMode==0 || freeResource.cycleRefMode==1 || empty freeResource.cycleRefMode}"> style="display:none;"</c:if>>
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.freeResourcePeriod"]}:</label>
 <div class="col-md-8 form-inline"   style="width:auto;">
  <input type="text" class="input-medium form-control"  id="cycleUnit" name="cycleUnit" value="${freeResource.cycleUnit}"  maxlength="9" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"    ${actionType=='detail'?'disabled':''}/>
  <select class="form-control input-medium"  id="cycleType"  name="cycleType"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${freeResourcePeriodUnitList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==freeResource.cycleType}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.expiryType']}:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-xlarge invalidProdUseFlag"  id="invalidProdUseFlag"  name="invalidProdUseFlag"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		<c:forEach var="obj" items="${expiryTypeList}" varStatus="status">
			<option value="${obj.cepCode}"  <c:if test="${obj.cepCode==freeResource.invalidProdUseFlag}">selected</c:if>>${obj.cepName}</option>
		</c:forEach>
  </select>
 </div>
</div>

<div class="form-group" style="display:none;">
 <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.subscriberStatusEligibility"]}:</label>
 <div class="col-md-8 form-inline"  id="userStateJstreeDiv" >
		<div class="input-icon right input-large"  style="width:485px !important;">
			<i class="glyphicon glyphicon-tree-deciduous"></i>
		   <input type="text" class="form-control input-large  dropdown-toggle2"  id="userStateNames" name="userStateNames"    style="width:485px !important;"  ${actionType=='detail'?'disabled':''}/>
		   <input type="hidden"  id="userStateIds" name="userStateIds"  value="${freeResource.userState}"/>
		   <div class="dropdown-menu jstree" role="menu"  style="width:485px !important;">
		    	<div class="poShu"></div>
		   </div>
  		</div>
 </div>
 <div class="col-md-8 form-inline"  id="loadingImg_tree"  style="width:30px; padding:9px 0 0 0; display:none;"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
</div>

<div class="form-group"   style="display:none;">
 <label class="col-md-4 control-label">
  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="" data-original-title=" ${local['eaap.op2.portal.price.notificationTemplate']}"></i>
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.notificationTemplate']}:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="form-control input-xlarge" placeholder=""  id="notificationTemplate"   name="notificationTemplate"  value=""  readonly="readonly" />
  <span class="input-group-btn"  style="display: ${actionType=='detail'?'none':'inline-block'};">
  		<button data-target="" data-toggle="" class="btn theme-btn" type="button"  id=""><i class="fa fa-plus"></i></button> 
  </span>
  <input type="text" id="notificationTemplateId" name="notificationTemplateId" value="" style="position:absolute; top:-100000px;"/>
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
  <font color="FF0000">*</font>${local['eaap.op2.portal.price.forwardCycle']}:</label>
 <div class="col-md-8">
  <input type="text" class="form-control input-xlarge" placeholder="" id="forwardCycle" name="forwardCycle"  maxlength="4"  value="${freeResource.forwardCycle }"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>/>
 </div>
</div>

<div class="form-group">
	 <label class="col-md-4 control-label"><font color="FF0000">*</font>${local["eaap.op2.portal.price.amountDistributionType"]}: </label>
	 <div class="col-md-8 form-inline" >
		  <div class="radio-list" data-error-container="#here2"   id="allowanceTypeDiv">
			   <label class="radio-inline">
				    <input type="radio"  name="allowanceType"  value="1" <c:if test="${freeResource.allowanceType==1 || empty freeResource.allowanceType}">checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/> 
				    ${local["eaap.op2.portal.price.specificAmount"]} 
			    </label>
			   <label class="radio-inline" >
				    <input type="radio"  name="allowanceType"  value="2" <c:if test="${freeResource.allowanceType==2}">checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/> 
				    ${local["eaap.op2.portal.price.unlimited"]} 
			    </label>
			   <label class="radio-inline" >
				    <input type="radio"  name="allowanceType"  value="3" <c:if test="${freeResource.allowanceType==3}">checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/> 
				    ${local["eaap.op2.portal.price.designatedAmount"]}
			    </label>
		  </div>
	 </div>
</div>

<div class="form-group"  id="freeSourceAmountDiv"  <c:if test="${freeResource.allowanceType==3}"> style="display:none;"</c:if>>
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.freeSourceAmount"]}:</label>
 <div class="col-md-8 form-inline"   style="width:auto;">
	  <input type="text" class="input-medium form-control"  id="amount" name="amount" value="${amount}"  maxlength="9" 
	  <c:if test="${freeResource.allowanceType==2 || freeResource.allowanceType==3}"> style="display:none;"</c:if>
	  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"    ${actionType=='detail'?'disabled':''}/>
      <select class="form-control input-medium"  id="measureId"  name="measureId" 
       <c:if test="${freeResource.allowanceType==2 || freeResource.allowanceType==3}"> style="display:none;"</c:if>
       <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>
		      	<option value="" title="--Please choose--">--Please choose--</option>
				<c:forEach var="obj" items="${trafficUnitList}" varStatus="status">
					<option value="${obj.cepCode}"  <c:if test="${measureId==obj.cepCode}">selected</c:if>>${obj.cepName}</option>
				</c:forEach>
      </select>
	  <select class="form-control input-medium"  id="unlimitamount"  name="unlimitamount"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>
	  	<c:if test="${freeResource.allowanceType==1 || freeResource.allowanceType==3 || empty freeResource.allowanceType}"> style="display:none;"</c:if> >
				<option value="999999999999999999"  title="unlimited"   selected>unlimited</option>
	  </select>  
 </div>
</div>


<div class="form-group"  id="cycleRangeDiv"  <c:if test="${freeResource.allowanceType==1 || freeResource.allowanceType==2 || empty freeResource.allowanceType}"> style="display:none;"</c:if>>
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>${local["eaap.op2.portal.price.cycleRange"]}:</label>
 <div class="col-md-8 form-inline">
	  <table class="table table-bordered table-condensed table-advance ladder"  id="cycleRangeTable" >
	   <thead>
	    <tr>
	     <th></th>
	     <th>Cycle</th>
	     <th>Unlimited</th>
	     <th>Quantity</th>
	     <th style="<c:if test="${actionType=='detail'}">display:none;</c:if>"><i class="fa fa-plus addCycleRangeBut" ></i></th>
	    </tr>
	   </thead>
	   <tbody>
				<c:choose>
					<c:when test="${freeResourceSegList !=null && fn:length(freeResourceSegList) > 0 && freeResource.allowanceType==3}">
						<c:forEach items="${freeResourceSegList }" var="d" varStatus="vs">
						<tr>
							<td>${vs.count}</td>
							<td>
								 <div class='input-group input-xs'>
									  <input type='text'  id='startval'  name='startval'  class='form-control'  maxlength="9"   value="${d.startValue}" 
									  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   ${actionType=='detail'?'disabled':''}    style="width:110px;"/>
									  <span class='input-group-addon'>To</span>
									  <input type='text'  id='endVal'  name='endVal'  class='form-control'  maxlength="9"  onclick="select()"  value="${d.endValue}" 
									  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"   ${actionType=='detail'?'disabled':''}    style="width:110px;"/>
							 	</div>
							 </td>
							 <td align="center">
								 <input type="checkbox"  name="unlimited"  id="unlimited"  onclick="unlimitedIsSelect(this)"  <c:if test="${d.amount=='999999999999999999'}"> checked="checked"</c:if>  ${actionType=='detail'?'disabled':''}/>
							 </td>
							 <td>
						  		<input type='text'  class='form-control input-xs '  id="quantity_amount"  name='quantity_amount'  placeholder=''  maxlength="9"    value="${d.amount}" 
						  		onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  ${actionType=='detail'?'disabled':''} 
						  		<c:if test="${d.amount !='999999999999999999' &&actionType!='detail'}"> style="width:100px;"</c:if>
						       	<c:if test="${d.amount =='999999999999999999' &&actionType!='detail'}"> style="display:none;width:100px;"</c:if> 
						       	<c:if test="${d.amount !='999999999999999999' &&actionType=='detail'}"> style="width:50px;"</c:if>
						       	<c:if test="${d.amount =='999999999999999999' &&actionType=='detail'}"> style="display:none;width:50px;"</c:if> />
						  		<select class="form-control  input-xs"  id="quantity_measureId"  name="quantity_measureId"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>
						  		<c:if test="${d.amount !='999999999999999999' &&actionType!='detail'}"> style="width:100px;"</c:if>
						       	<c:if test="${d.amount =='999999999999999999' &&actionType!='detail'}"> style="display:none;width:100px;"</c:if>
						       	<c:if test="${d.amount !='999999999999999999' &&actionType=='detail'}"> style="width:70px;"</c:if>
						       	<c:if test="${d.amount =='999999999999999999' &&actionType=='detail'}"> style="display:none;width:70px;"</c:if>>
									<c:forEach var="obj" items="${trafficUnitList}" varStatus="status">
										<option value="${obj.cepCode}"  <c:if test="${d.measureId==obj.cepCode}">selected</c:if>>${obj.cepName}</option>
									</c:forEach>
						        </select>
						       	<select class="form-control input-xs "  id="quantity_unlimitamount"  name="quantity_unlimitamount"  disabled="disabled"  
						       	<c:if test="${d.amount !='999999999999999999'}"> style="display:none;width:204px;"</c:if>
						       	<c:if test="${d.amount =='999999999999999999'}"> style="width:204px;"</c:if>>
									<option value="999999999999999999"  title="unlimited">unlimited</option>
								 </select>
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
					<tr id="1">
						<td>1</td>
						<td>
							 <div class='input-group input-xs'>
								  <input type='text'  id='startval'  name='startval'  class='form-control' value="1"  maxlength="9"  
								  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   ${actionType=='detail'?'disabled':''}    style="width:110px;"/>
								  <span class='input-group-addon'>To</span>
								  <input type='text'  id='endVal'  name='endVal'  class='form-control'  value="-1"  maxlength="9"  onclick="select()" 
								  onkeyup="this.value=this.value.match('^-?[0-9]*$')" onafterpaste="this.value=this.value.match('^-?[0-9]*$')"   ${actionType=='detail'?'disabled':''}    style="width:110px;"/>
						 	</div>
						 </td>
						 <td align="center">
							 <input type="checkbox"  name="unlimited"  id="unlimited"  onclick="unlimitedIsSelect(this)"/>
						 </td>
						 <td>
						  		<input type='text'  value=''  class='form-control input-xs '   id="quantity_amount"   name='quantity_amount'  placeholder=''  maxlength="9"  
						  		onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   ${actionType=='detail'?'disabled':''}    style="width:100px;"/>
						  		<select class="form-control  input-xs"  id="quantity_measureId"  name="quantity_measureId"  <c:if test="${actionType=='detail'}">disabled="disabled"</c:if> style="width:100px;">
									<c:forEach var="obj" items="${trafficUnitList}" varStatus="status">
										<option value="${obj.cepCode}"  >${obj.cepName}</option>
									</c:forEach>
						        </select>
						       	<select class="form-control input-xs"   id="quantity_unlimitamount"  name="quantity_unlimitamount"  style="display:none; width:204px;"  disabled="disabled" >
									<option value="999999999999999999"  title="unlimited">unlimited</option>
								 </select>
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
  <label class="col-md-4 control-label">${local['eaap.op2.portal.price.priceDesc']}:</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"  id="description"  name="description" <c:if test="${actionType=='detail'}">disabled="disabled"</c:if>>${componentPrice.description }</textarea>
 </div>
</div>






<div class="form-group" style="display: ${actionType=='detail'?'none':'block'};">
		<label class="col-md-4"></label>
		<div class="col-md-8">
		<button type="button" class="btn theme-btn button-next"  onclick="saveOrUpdateFreeResource(this)"> Save <i class="m-icon-swapright m-icon-white"></i></button>
		</div>
</div>
</form>




<!-- SelectFreeResourceItemModal Start:  -->
<div id="selectFreeResourceItemModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header"  style="padding-bottom:0;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">Select Free Resource Item</h4>
    <hr/>
    <div class="search-form-default">
    <form action="#" class="form-inline">
      	<label class="control-label">Name or ID:</label>
        <input type="text" id="idOrName"  name="idOrName" placeholder="Name/ID" class="form-control input-medium" >
        <button class="btn default" type="button" id="timeQuery" >${local['eaap.op2.portal.devmgr.query']}</button>
     </form>
    </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="freeResourceItemDT">
      <thead>
        <tr>
          <th></th>
          <th>ID</th>
          <th>Name</th>
          <th>Unit of Measurement</th>
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
<!-- SelectFreeResourceItemModal  End-->