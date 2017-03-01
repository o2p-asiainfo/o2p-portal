<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script>
var radomformId = '${billingDiscountId}';
	$(function() {
		var handleDatePickers = function(selector) {
			var options = {
				autoclose : true,
				minViewMode : 'year',
				format : 'yyyy-mm-dd'
			}
			if (jQuery().datepicker) {
				$(selector).datepicker(options);
			}
		}
		handleDatePickers('#moneyTime .date-picker');
		var pricingInfoId = $('#pricingInfoId').val();
		var pricingName = $('#pricingName').val();
		var value = $('#'+radomformId).find('#priceName').val();
		if('' == value){
			$('#'+radomformId).find('#priceName').val(pricingName+'billingDiscount');
		}
		var startTime = $('#'+radomformId).find('#startTime').val();
		var endTime = $('#'+radomformId).find('#endTime').val();
		if('' == startTime){
			$('#'+radomformId).find('#startTime').val('${start}');
		}
		if('' == endTime){
			$('#'+radomformId).find('#endTime').val('${end}');
		}
		$('#'+radomformId).find('#billingPriPricingInfoId').val(pricingInfoId);
		changeMoney('',radomformId);
	});
	function billingDiscountSubmit(formId){
		$.ajax({
			async : false,
			type : "POST",
			url : "../provider/saveOrUpdateBillingDiscount.shtml",
			data : $('#'+formId).serialize(),
			success : function(data) {
				if (data.code == "0000") {
					$('#'+formId).find('#billingPriceId').val(data.result);
					toastr.success('${local["eaap.op2.portal.price.message.saveSuccess"]}');
				} else {
					toastr.error(data.desc);
				}
			},
			dataType : "json",
			error : function(xhr, textStatus, errorThrown) {
				toastr.error('${local["eaap.op2.portal.price.message.saveFail"]}');
			}
		});
	}
	function addRow(formId) {
		var selectValue = $('#'+formId).find('#selectPercent').val();
		if (1 == selectValue) {
			var size = $('#'+formId).find('#billingDiscountTable2>tbody>tr').size() + 1;
			var value2 = 1;
			if(size-1 > 0){
				var value1 = $('#'+formId).find('#billingDiscountTable2>tbody>tr').eq(size-2).find('input').eq(0).val();
				value2 = $('#'+formId).find('#billingDiscountTable2>tbody>tr').eq(size-2).find('input').eq(1).val();
				var value3 = $('#'+formId).find('#billingDiscountTable2>tbody>tr').eq(size-2).find('input').eq(2).val();
				var value4 = $('#'+formId).find('#billingDiscountTable2>tbody>tr').eq(size-2).find('input').eq(3).val();
				if(isNaN(value1) || isNaN(value2) || isNaN(value3) || isNaN(value4)){
					toastr.error('${local["eaap.op2.portal.price.message.mustNum"]}');
					return;
				}
				if(value1 >= value2){
					toastr.error('${local["eaap.op2.portal.price.basic.greater"]}'); 
					return;
				}
				if('' == value1 || '' == value2 || '' == value3 || '' == value4){
					toastr.error('${local["eaap.op2.portal.price.message.notEmpty"]}');
					return;
				}
			}
			var html = '<tr>'
					+ '<td>'
					+ size
					+ '</td>'
					+ '<td><div class="input-group input-xs">'
					+ '<input type="text" name="startPercent" class="form-control" value="'+value2+'" onchange=\"javascript:checkNumValue(this);\">'
					+ '<span class="input-group-addon"> To </span>'
					+ '<input type="text" name="endPercent" class="form-control" onchange=\"javascript:checkNumValue(this);\"></div></td>'
					+ '<td><input type="text" name="sonPercent" onchange=\"javascript:checkNumValue(this);\" size="5" placeholder="" class="form-control input-xs">&nbsp;/&nbsp;<input type="text" name="matherPercent" onchange=\"javascript:checkNumValue(this);\" size="5" placeholder="" class="form-control input-xs"></td>'
					+ '<td><a class="btn default btn-sm black btn-del"  onclick=\"javascript:delRow($(this).closest(\'tr\'),\''+formId+'\');\"> <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.price.delete"]} </a></td>'
					+ '</tr>';
			$('#'+formId).find('#billingDiscountTable2>tbody').append(html);
		} else {
			var size = $('#'+formId).find('#billingDiscountTable>tbody>tr').size() + 1;
			var value2 = 1;
			if(size-1 > 0){
				var value1 = $('#'+formId).find('#billingDiscountTable>tbody>tr').eq(size-2).find('input').eq(0).val();
				value2 = $('#'+formId).find('#billingDiscountTable>tbody>tr').eq(size-2).find('input').eq(1).val();
				var value3 = $('#'+formId).find('#billingDiscountTable>tbody>tr').eq(size-2).find('input').eq(2).val();
				if(isNaN(value1) || isNaN(value2) || isNaN(value3)){
					toastr.error('${local["eaap.op2.portal.price.message.mustNum"]}');
					return;
				}
				if(value1 >= value2){
					toastr.error('${local["eaap.op2.portal.price.basic.greater"]}'); 
					return;
				}
				if('' == value1 || '' == value2 || '' == value3){
					toastr.error('${local["eaap.op2.portal.price.message.notEmpty"]}');
					return;
				}
			}
			var html = '<tr>'
					+ '<td>'
					+ size
					+ '</td>'
					+ '<td><div class="input-group input-xs">'
					+ '<input type="text" name="startOther"  onchange=\"javascript:checkNumValue(this);\" class="form-control" value="'+value2+'">'
					+ '<span class="input-group-addon"> To </span>'
					+ '<input type="text" name="endOther" onchange=\"javascript:checkNumValue(this);\" class="form-control"></div></td>'
					+ '<td><input type="text" onchange=\"javascript:checkNumValue(this);\" name="otherValue" placeholder="" class="form-control input-xs"></td>'
					+ '<td><a class="btn default btn-sm black btn-del"  onclick=\"javascript:delRow($(this).closest(\'tr\'),\''+formId+'\');\"> <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.price.delete"]} </a></td>'
					+ '</tr>';
					$('#'+formId).find('#billingDiscountTable>tbody').append(html);
		}
	}
	function checkNumValue(obj){
		if(isNaN(obj.value)){
			toastr.error('${local["eaap.op2.portal.price.message.mustNum"]}');
			obj.value = '';
		}
	}
	function delRow(row,formId) {
		var selectValue = $('#'+formId).find('#selectPercent').val();
		$(row).detach();
		var num = 1;
		if (1 == selectValue) {
			$('#'+formId).find('#billingDiscountTable2>tbody>tr').each(function() {
				$(this).children('td').eq(0).html(num++);
			});
		} else {
			$('#'+formId).find('#billingDiscountTable>tbody>tr').each(function() {
				$(this).children('td').eq(0).html(num++);
			});
		}

	}
	function changeTab(obj,formId) {
		if (1 == obj) {
			$('#'+formId).find('#billingDiscountTable').hide();
			$('#'+formId).find('#billingDiscountTable2').show();
		} else {
			$('#'+formId).find('#billingDiscountTable').show();
			$('#'+formId).find('#billingDiscountTable2').hide();
		}
	}
	function changeMoney(obj,formId) {
		var obj = $('#'+formId).find('#selectMonney')[0];
		var text = obj.options[obj.selectedIndex].text;//获取文本
		$('#'+formId).find('#unitAreaTable1').html(text);
		$('#'+formId).find('#currencyAreaTable1').html(text);
		$('#'+formId).find('#unitAreaTable2').html(text);
	}
</script>
<form action="#" id="${billingDiscountId}">
	<input type="hidden" name="billingPriPricingInfoId"
		id="billingPriPricingInfoId"
		value="${componentPrice.priPricingInfoId}" /> <input type="hidden"
		name="billingPriceId" id="billingPriceId"
		value="${componentPrice.priceId}" />
	<input type="hidden" id="billingTabId">
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
		${local["eaap.op2.portal.price.priceName"]}:
		</label>
		<div class="col-md-8">
			<input type="text" class="form-control input-medium" name="priceName" id="priceName"
				value="${componentPrice.priceName}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
		${local["eaap.op2.portal.price.priceType"]}:
		</label>
		<div class="col-md-8 form-inline">
			<select class="form-control input-medium" name="taxType">
				<c:forEach var="lwm" items="${taxTypeList}" varStatus="vsw">
					<c:choose>
						<c:when test="${billingDiscount.taxIncluded == lwm.key}">
							<option value="${lwm.key}" selected>${lwm.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${lwm.key}">${lwm.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
			${local["eaap.op2.portal.price.priority"]}:
		</label>
		<div class="col-md-8 form-inline">
			<input type="text" class="form-control input-medium" name="priority"
			<c:if test="${not empty billingDiscount.billingPriority }">value="${billingDiscount.billingPriority }"</c:if>
  			<c:if test="${empty billingDiscount.billingPriority }">value="500"</c:if>
			/>
		</div>
	</div>
	<input type="hidden" name="promType" value="1" id="selectPercent">
	<%-- 
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
		${local["eaap.op2.portal.price.promType"]}:
		</label>
		<div class="col-md-8 form-inline">
			<select class="form-control input-medium" id="selectPercent"
				name="promType" onchange="javascript:changeTab(this.value,'${billingDiscountId}');">
				<c:forEach var="lwm" items="${promTypeList}" varStatus="vsw">
					<c:choose>
						<c:when test="${billingDiscount.promType == lwm.key}">
							<option value="${lwm.key}" selected>${lwm.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${lwm.key}">${lwm.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>--%>
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
		${local["eaap.op2.portal.price.maxDiscount"]}:
		</label>
		<div class="col-md-8 form-inline">
			<input type="text" class="input-medium form-control" name="maxMonney"
			<c:if test="${not empty billingDiscount.maxiMum }">value="${billingDiscount.maxiMum }"</c:if>
  			<c:if test="${empty billingDiscount.maxiMum }">value="1"</c:if>
			/> 
				<select
				class="form-control input-medium chargingUnit" id="selectMonney"
				name="currencyUnitType"
				onchange="javascript:changeMoney(this.value,'${billingDiscountId}');">
				<c:forEach var="lwm" items="${currencyUnitTypeList}" varStatus="vsw">
					<c:choose>
						<c:when test="${billingDiscount.currencyUnitType == lwm.key}">
							<option value="${lwm.key}" selected>${lwm.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${lwm.key}">${lwm.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-4 control-label"> <font color="FF0000">*</font>
		${local["eaap.op2.portal.price.priceTime"]}:
		</label>
		<div class="col-md-8 form-inline" id="moneyTime">
			<div data-date-format="mm/dd/yyyy" data-date-start-date="+0d"
				data-date="10/11/2012"
				class="input-group input-medium date-picker input-daterange"
				data-error-container="#error-container">
				<input type="text" name="startTime" class="form-control od" id="startTime"  style="width:120px"
					value="${componentPrice.formatEffDate}"> <span
					class="input-group-addon"> To </span> <input type="text"
					name="endTime" class="form-control od" id="endTime"  style="width:120px"
					value="${componentPrice.formatExpDate}">
			</div>
		</div>
		<input type="hidden" name="calcType" value="1">
		<%-- 
		<div class="form-group">
			<label class="col-md-4 control-label"> <font color="FF0000">*</font>
			${local["eaap.op2.portal.price.calcType"]}:
			</label>
			<div class="col-md-8 form-inline">
				<div class="radio-list" data-error-container="#here2">
					<label class="radio-inline"> <input type="radio" value="0"
						name="calcType"
						<c:if test="${billingDiscount.calcType==0 ||empty billingDiscount.calcType }">checked="checked"</c:if> />
						${local["eaap.op2.portal.price.Section"]}
					</label> <label class="radio-inline"> <input type="radio" value="1"
						name="calcType"
						<c:if test="${billingDiscount.calcType==1}">checked="checked"</c:if> />
						${local["eaap.op2.portal.price.finalSection"]}
					</label>
				</div>
			</div>
		</div>--%>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.price.fee"]}:
			</label>
			<div class="col-md-8 form-inline">
				<c:choose>
					<c:when test="${null == billingDiscount}">
						<!-- 其它表格 -->
						<table
							class="table-bordered table-condensed table-advance ladder"
							id="billingDiscountTable" style="display: none;" width="98%">
							<thead>
								<tr>
									<th><i class="fa fa-plus" onclick="javascript:addRow('${billingDiscountId}');"></i>
									</th>
									<th>${local["eaap.op2.portal.price.arrange"]} <span class="label label-yellow"><span
											class="unitArea" id="unitAreaTable1">DKK</span> </span>
									</th>
									<th>${local["eaap.op2.portal.price.priceType2"]} <span class="label label-yellow">
											<span class="currencyArea" id="currencyAreaTable1">DKK</span>
									</span>
									</th>
									<th>${local["eaap.op2.portal.price.Opertion"]}</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<!-- 百分比表格 -->
						<table
							class="table-bordered table-condensed table-advance ladder"
							id="billingDiscountTable2"  width="98%">
							<thead>
								<tr>
									<th><i class="fa fa-plus" onclick="javascript:addRow('${billingDiscountId}');"></i>
									</th>
									<th>${local["eaap.op2.portal.price.arrange"]} <span class="label label-yellow"><span
											class="unitArea" id="unitAreaTable2">DKK</span> </span>
									</th>
									<th>${local["eaap.op2.portal.price.discount"]}</th>
									<th>${local["eaap.op2.portal.price.Opertion"]}</th>
								</tr>
							</thead>
							<tbody>
							<tr>
										  <td>1</td>
										  <td>
											  <div class="input-group input-xs">
											  <input type="text" name="startPercent" onchange="javascript:checkNumValue(this);" class="form-control" value="0">
											  <span class="input-group-addon"> To </span>
											  <input type="text" name="endPercent" onchange="javascript:checkNumValue(this);" class="form-control" value="-1">
										      </div>
										  </td>
										  <td>
											  <input type="text" name="sonPercent" onchange="javascript:checkNumValue(this);" size="5" placeholder="" class="form-control input-xs"  value="100">
											  &nbsp;/&nbsp;
											  <input type="text" name="matherPercent" onchange="javascript:checkNumValue(this);" size="5" placeholder="" class="form-control input-xs" value="100">
										  </td>
										  <td>
											  <a class="btn default btn-sm black btn-del"  onclick="javascript:delRow($(this).closest('tr'),'${billingDiscountId}');">
											  <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.price.delete"]} </a>
										  </td>
										</tr>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<!-- 查看,修改的操作 -->
						<table
							class="table-bordered table-condensed table-advance ladder"
							id="billingDiscountTable"  width="98%"
							<c:if test="${billingDiscount.promType == 1 }">style="display:none;"</c:if>>
							<thead>
								<tr>
									<th><i class="fa fa-plus" onclick="javascript:addRow('${billingDiscountId}');"></i>
									</th>
									<th>${local["eaap.op2.portal.price.arrange"]} <span class="label label-yellow"><span
											class="unitArea" id="unitAreaTable1">DKK</span> </span>
									</th>
									<th>${local["eaap.op2.portal.price.priceType2"]} <span class="label label-yellow">
											<span class="currencyArea" id="currencyAreaTable1">DKK</span>
									</span>
									</th>
									<th>${local["eaap.op2.portal.price.Opertion"]}</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(ratingCurverDetailList)>0 && billingDiscount.promType!=1}">
									<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
										<tr>
										  <td>${vs.index+1}</td>
										  <td>
											  <div class="input-group input-xs">
											  <input type="text" name="startOther" onchange="javascript:checkNumValue(this);" class="form-control" value="${d.startval }">
											  <span class="input-group-addon"> To </span>
											  <input type="text" name="endOther" onchange="javascript:checkNumValue(this);" class="form-control" value="${d.endVal }">
										      </div>
										  </td>
										  <td><input type="text" name="otherValue" placeholder="" onchange="javascript:checkNumValue(this);" class="form-control input-xs" value="${d.baseVal }"></td>
										  <td>
											  <a class="btn default btn-sm black btn-del"  onclick="javascript:delRow($(this).closest('tr'),'${billingDiscountId}');">
											  <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.price.delete"]} </a>
										  </td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<!-- 百分比表格 -->
						<table
							class="table-bordered table-condensed table-advance ladder"
							id="billingDiscountTable2" width="98%"
							<c:if test="${billingDiscount.promType != 1 }">style="display:none;"</c:if>>
							<thead>
								<tr>
									<th><i class="fa fa-plus" onclick="javascript:addRow('${billingDiscountId}');"></i>
									</th>
									<th>${local["eaap.op2.portal.price.arrange"]} <span class="label label-yellow"><span
											class="unitArea" id="unitAreaTable2">DKK</span> </span>
									</th>
									<th>${local["eaap.op2.portal.price.discount"]}</th>
									<th>${local["eaap.op2.portal.price.Opertion"]}</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${fn:length(ratingCurverDetailList)>0 && billingDiscount.promType ==1}">
									<c:forEach items="${ratingCurverDetailList }" var="d" varStatus="vs">
										<tr>
										  <td>${vs.index+1}</td>
										  <td>
											  <div class="input-group input-xs">
											  <input type="text" name="startPercent" onchange="javascript:checkNumValue(this);" class="form-control" value="${d.startval}">
											  <span class="input-group-addon"> To </span>
											  <input type="text" name="endPercent" onchange="javascript:checkNumValue(this);" class="form-control" value="${d.endVal }">
										      </div>
										  </td>
										  <td>
											  <input type="text" name="sonPercent" onchange="javascript:checkNumValue(this);" size="5" placeholder="" class="form-control input-xs"  value="${d.numerator }">
											  &nbsp;/&nbsp;
											  <input type="text" name="matherPercent" onchange="javascript:checkNumValue(this);" size="5" placeholder="" class="form-control input-xs" value="${d.denominator }">
										  </td>
										  <td>
											  <a class="btn default btn-sm black btn-del"  onclick="javascript:delRow($(this).closest('tr'),'${billingDiscountId}');">
											  <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.price.delete"]} </a>
										  </td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-md-4 control-label">${local["eaap.op2.portal.price.priceDesc"]}:</label>
			<div class="col-md-8">
				<textarea rows="3" class="form-control" name="description">${componentPrice.description}</textarea>
			</div>
		</div>
		
		<p class="text-center margin-top-10">
			<a class="btn theme-btn button-submit" href="javascript:billingDiscountSubmit('${billingDiscountId}');"
				id="billingDiscountSubmit">${local["eaap.op2.portal.price.submit"]}</a>
				<button type="button" class="btn theme-btn button-finish"  onclick="$('#modal4').modal('hide');"> Finish <i class="m-icon-swapright m-icon-white"></i></button>
		</p>
</form>
