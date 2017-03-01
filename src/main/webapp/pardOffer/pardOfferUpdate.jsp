<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<%@include file="/header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/jstree/css/style.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-wizard/bootstrap-wizard.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<script type="text/javascript">
var  _addPricePlanInfo 		= "${local['eaap.op2.portal.pardOffer.addPricePlanInfo']}";
var  _addSettlementInfo 	= "${local['eaap.op2.portal.pardOffer.addSettlementInfo']}";
var  _noSettlementInfo		= "${local['eaap.op2.portal.pardOffer.noSettlementInfo']}";
var  _deleteConfirm			= "${local['eaap.op2.portal.price.deleteConfirm']}";
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<form name="showProdForm" method="post" id="showPordOfferForm" action="">
	 <input type="hidden"  id="prodOfferId" name="prodOffer.prodOfferId" value="${prodOffer.prodOfferId}"/>
	 <input type="hidden"  id="o2pCloudFlag" name="o2pCloudFlag" value="${o2pCloudFlag}"/>
</form>
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
      		<c:if test="${isAdd!=1}">
	    		<h1>${local["eaap.op2.portal.pardOffer.offerUpdate.location"]}</h1>					 
  			</c:if>	
  			<c:if test="${isAdd==1}">
				<h1>${local["eaap.op2.portal.pardOffer.localtion"]}</h1>
  			</c:if>	
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li><a href="${APP_PATH}/pardOffer/toIndex.shtml">${local["eaap.op2.portal.pardOffer.offer"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.pardOffer.offerUpdate.location"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
 <!-- BEGIN CONTAINER -->
  <div class="container">
    <div class="panel panel-default margin-bottom-40 margin-top-20">
      <div class="panel-heading">
        <h3 class="panel-title" id="title">Fill in the information - <span class="step-title"> Step 1 of 3 </span></h3>
      </div>
      <div class="panel-body">
          <div class="form-wizard">
            <div class="form-body">
             <form id="submit_form" class="form-horizontal" action="#" novalidate>
              <ul class="nav nav-pills nav-justified steps">
                <li data-visit='false' class='${isAdd==1?"":"active"}'> <a class="step" data-toggle="tab" href="#tab1"> <span class="number"> 1 </span> <span class="desc"> ${local["eaap.op2.portal.price.baseInfo"]} </span> </a> </li>
                <li data-visit='false' class='${isAdd==1?"active":""}'> <a class="step" data-toggle="tab" href="#tab2"   id="tab2_link"> <span class="number"> 2 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.pricePlan"]} </span> </a> </li>
                <li data-visit="false"> <a class="step active" data-toggle="tab" href="#tab3"   id="tab3_link"> <span class="number"> 3 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.account"]}</span> </a> </li>
               	<li data-visit="false"> <a id="tab4_link" class="step active" href="${APP_PATH }/pardOffer/toDetail.shtml?prodOffer.prodOfferId=${prodOffer.prodOfferId}"> <span class="number"> 4 </span> <span class="desc"> Offer Detail</span> </a> </li>
              </ul>
             <div role="progressbar" class="progress progress-striped" id="bar">
                <div class="progress-bar progress-bar-success" style="width: 25%;"> </div>
              </div>
              </form>
              <div class="tab-content">
                <div class="alert alert-danger display-none">
                  <button data-dismiss="alert" class="close"></button>
                  You have some form errors. Please check below. </div>
                <div class="alert alert-success display-none">
                  <button data-dismiss="alert" class="close"></button>
                  Your form validation is successful! </div>
                <div id="tab1" class='tab-pane ${isAdd==1?"":"active"} form-horizontal '>
                 <form id="tab1_submit_form" class="form-horizontal" action="" novalidate>
		         	<input type="hidden"  id="prodOfferId" name="prodOffer.prodOfferId" value="${prodOffer.prodOfferId}"/>
		        	<input type="hidden" id="offerProdStr" name="offerProdStr"/>
		        	<input type="hidden" id="offerStr" name="offerStr"/>
		        	<input type="hidden" id="extProdOfferId" name="prodOffer.extProdOfferId" value="${prodOffer.extProdOfferId}"/>
		        	<input type="hidden" id="offerMutualExclusionStr" name="offerMutualExclusionStr"/>
		        	<input type="hidden" id="offerChannelStr" name="offerChannelStr" value="${FuncSeletedTreeMap.funcId}"/>
                  <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerName"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="prodOfferName" name="prodOffer.prodOfferName" value="${prodOffer.prodOfferName}"/> </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerCode"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="productCode" name="prodOffer.extProdOfferId" value="${prodOffer.extProdOfferId}" disabled="disabled"/> </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerType"]} :</label>
	                    <div class="col-md-9">
	                      <div class="radio-list" data-error-container="#here">
		                     <c:if test="${prodOffer.offerType==11}">
		                     	<label class="radio-inline">
									<input type="radio" value="11" name="prodOffer.offerType" checked="checked" />
								${local["eaap.op2.portal.pardOffer.offerType_main"]} </label>
							</c:if> 
							<c:if test="${prodOffer.offerType!=11}">
		                     	<label class="radio-inline">
									<input type="radio" value="11" name="prodOffer.offerType" />
								${local["eaap.op2.portal.pardOffer.offerType_main"]} </label>
							</c:if> 
	                        <c:if test="${prodOffer.offerType==12}">
	                        	<label class="radio-inline">
									<input type="radio" value="12" name="prodOffer.offerType" checked="checked" />
								${local["eaap.op2.portal.pardOffer.offerType_AddOn"]} </label>
							</c:if> 
							<c:if test="${prodOffer.offerType!=12}">
	                        	<label class="radio-inline">
									<input type="radio" value="12" name="prodOffer.offerType"/>
								${local["eaap.op2.portal.pardOffer.offerType_AddOn"]} </label>
							</c:if> 
	                      </div>
                      </div>
                      <div id="here"></div>
	                </div>
	                <c:choose>
				      <c:when test="${o2pCloudFlag=='cloud'}">
		                <div class="form-group">
		                	<label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.operate"]}:</label>
		                	<div class="col-md-6" class="input-group">
		                		<input type="text" data-hover="dropdown" name="operator"  id="operator"  placeholder="Select an Operator" data-id="opr" class="form-control" value="${operatorName}" />
								<input type="hidden"  id="operatorValue" name="prodOffer.operateCode" value="${prodOffer.operateCode}" />
		                		<div role="menu" class="dropdown-menu multiple-select-box"  id="operatorDropDown">
							      	<c:forEach var="countryObj" items="${countryAndOperatorList}">
									    <div class="multiple-select-group"> 
									    <span class="multiple-select-group-letter">${countryObj.country}</span> 
									    <c:forEach var="operatorObj" items="${countryObj.operatorMapList}">
									    	<a sdata="${operatorObj.ORGID}" onclick="changeOperatorValue(this)" href="javascript:;">${operatorObj.ORGUSERNAME}</a> 
									    </c:forEach>
									    </div>
							     	</c:forEach>
							  	 </div>
		                	</div>
						</div>
					  </c:when>
					</c:choose>
	                <div class="form-group">
                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardSpec.product"]}:</label>
		              <div class="col-md-9">
		                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
		                  <thead>
		                    <tr>
		                      <th> 
		                         <c:choose>
								    <c:when test="${prodOffer.statusCd==1200}"><i class="fa fa-plus productModalPlus"></i></c:when>
								    <c:otherwise>&nbsp; </c:otherwise>
								</c:choose>
		                      </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.provider"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
		                      <c:if test="${prodOffer.statusCd==1200}"><th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th></c:if>
		                    </tr>
		                  </thead>
		                  <tbody id='productTB'>
		                  	<c:if test="${fn:length(offerProdRelList)>=1}">
    							<c:forEach var="prod" items="${offerProdRelList}" step="1" varStatus="status"> 
				             	<tr id="${prod.productId}">
				             		<td>${status.count}</td>
				             		<td>${prod.productProviderName}</td>
		                      		<td>${prod.extProdId}</td>
		                      		<td><a onclick="productDetailModal(${prod.productId})">${prod.productName}</a></td>
		                      		<td><input type='text' class='form-control '  value='${prod.minCount}'/></td>
		                      		<td><input type='text' class='form-control '  value='${prod.maxCount}'/></td>
		                      		 <c:if test="${prodOffer.statusCd==1200}"><td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td></c:if>
		                    	</tr>
	                     		</c:forEach>
                			</c:if>	
                			<c:if test="${fn:length(offerProdRelList)<1}">
    							<tr>
    							<c:choose>
								    <c:when test="${prodOffer.statusCd==1200}"><td colspan="7">None</td></c:when>
								    <c:otherwise><td colspan="6">None</td></c:otherwise>
								</c:choose>        
			                    </tr>
                			</c:if>	
		                  </tbody>
		                </table>
				  	   </div>
	                </div>
	                <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.bundleOffer"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th>
				                      <c:choose>
			                            <c:when test="${prodOffer.statusCd==1200}"><i class="fa fa-plus offerModalPlus"></i></c:when>
			                            <c:otherwise>&nbsp; </c:otherwise>
			                         </c:choose>
		                         </th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
			                       <c:if test="${prodOffer.statusCd==1200}"><th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th></c:if>
			                    </tr>
			                  </thead>
			                 <tbody id="offerTB">
			                  	<c:if test="${fn:length(prodOfferRelList1)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList1}" step="1" varStatus="status"> 
					             	<tr id="${offer.offerZId}">
					             		<td>${status.count}</td>
					             		<td>${offer.offerProvider}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td><a onclick="offerDetailModal(${offer.offerZId})"> ${offer.prodOfferName} </a></td>
			                      		<td><input type='text' class='form-control '  value='${offer.minCount}'/></td>
			                      		<td><input type='text' class='form-control '  value='${offer.maxCount}'/></td>
			                      		<c:if test="${prodOffer.statusCd==1200}"><td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td></c:if>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList1)<1}">
	    							<tr>
	    								<c:choose>
								    		<c:when test="${prodOffer.statusCd==1200}"><td colspan="7">None</td></c:when>
								    		<c:otherwise><td colspan="6">None</td></c:otherwise>
										</c:choose> 
				                    </tr>
	                			</c:if>	
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                 <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.exclusion"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th>
			                      <c:choose>
		                            <c:when test="${prodOffer.statusCd==1200}"><i class="fa fa-plus exclusionOfferModal"></i></c:when>
		                            <c:otherwise>&nbsp;</c:otherwise>
		                         </c:choose>
			                      </th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                       <c:if test="${prodOffer.statusCd==1200}"><th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th></c:if>
			                    </tr>
			                  </thead>
			                  <tbody id="exclusionOfferTB">
			                  	<c:if test="${fn:length(prodOfferRelList2)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList2}" step="1" varStatus="status"> 
					             	<tr id="${offer.offerZId}">
					             		<td>${status.count}</td>
					             		<td>${offer.offerProvider}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td><a onclick="offerDetailModal(${offer.offerZId})"> ${offer.prodOfferName} </a></td>
			                      		<c:if test="${prodOffer.statusCd==1200}"><td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td></c:if>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList2)<1}">
	    							<tr>
	    								<c:choose>
								          <c:when test="${prodOffer.statusCd==1200}"><td colspan="5">None</td></c:when>
								          <c:otherwise><td colspan="4">None</td></c:otherwise>
								        </c:choose>  
				                    </tr>
	                			</c:if>	
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"]}:</label>
	                  <div class="col-md-9">  
						<textarea id="prodOfferDesc" name="prodOffer.prodOfferDesc" class="form-control" >${prodOffer.prodOfferDesc}</textarea> 
					  </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">
	                  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title='${local["eaap.op2.portal.pardOffer.timeToOrderRemark"]}'></i>
	                  <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.timeToOrder"]}:</label>
	                  <div class="col-md-9"> 
	            		 <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
	                        <input type="text"  style="width:120px" name="prodOffer.formatEffDate" class="form-control od" value="${prodOffer.formatEffDate}"/>
	                        <span class="input-group-addon"> To </span>
	                        <input type="text"  style="width:120px" name="prodOffer.formatExpDate" class="form-control od" value="${prodOffer.formatExpDate}"/>
	                      </div>
	                      <div id="error-container"></div>
					  </div>
	                </div>
	                 <!--  <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,1)}">
								<input type="checkbox" value="1" name="channel" id="channel" checked="checked"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,1))}">
	                          <input type="checkbox" value="1" name="channel" id="channel"/>
	                        </c:if> 
	                          Third Party Partners </label>
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,2)}">
								<input type="checkbox" value="2" name="channel" id="channel" checked="checked"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,2))}">
	                          <input type="checkbox" value="2" name="channel" id="channel"/>
	                        </c:if> 
	                          Operator </label>
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div>-->
	                <!-- 
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                  <div class="col-md-9"> 
	                  	<div class="input-icon right input-large" > <i class="glyphicon glyphicon-tree-deciduous"></i>
				          <input type="text" class="form-control input-large" id="jstreeText" name="jstreeText" value="${FuncSeletedTreeMap.funcValue}" data-hover="dropdown">
				          <div class="dropdown-menu jstree" role="menu">
				            <div class="jstree"></div>
				          </div>
				        </div>
					  </div>
	                </div> -->
	                <div class="form-actions fluid">
				        <div class="col-md-offset-4"> 
				        	<a class="btn theme-btn button-save" href="javascript:;" id="saveBtnForUpdate"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
				        </div>
				      </div>
				      </form>
                </div>
                
                <div id="tab2" class="tab-pane ${isAdd==1?"active":""}">
                  <div class="form-group row">
                    <div class="col-md-12 text-right" id="tab2-tools"> 
                    	<a href="javascript:;" class="btn default btn-select"><i class="fa fa-check-square-o"></i> Select </a> 
                    	<a href="javascript:;" class="btn default btn-delete"><i class="fa fa-trash-o"></i> Delete </a> 
                    	<a href="javascript:;" data-target="#tab2" data-draw="${fn:length(pricingPlanList)}" id="tab2_addTab" data-container=".tab2-content"  title="Add a Price Plan"  data-url='${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?prodIds=${prodOffer.prodOfferId}'  class="btn default btn-add addTab"  data-addTpl="${fn:length(pricingPlanList)>=1?true:false}" ><i class="fa fa-plus"></i> ADD</a>
                    </div>
                  </div>
                  <div class="form-horizontal tabbable-custom createArea">

	                  	<c:if test="${fn:length(pricingPlanList)>=1}">
	                  		<ul class="nav nav-tabs tab2_TabUL">
		  						<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status"> 
									<li class="${1==status.count?'active':''}"    pricingTargetId="${pricePlan.pricingTargetId}"  pricingInfoId="${pricePlan.pricingInfoId}" >
										<a data-toggle="tab"   onclick="javascript:loadEx_tab2('#tab2_${status.count-1}','${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?actionType=update&prodIds=${prodOffer.prodOfferId}&pricingInfoId=${pricePlan.pricingInfoId}&pricingTargetId=${pricePlan.pricingTargetId}&applicType=1&formNum=${status.count-1}')" 
											 data-key="${status.count-1}" href="#tab2_${status.count-1}">${pricePlan.pricingName}</a>
										<input type="checkbox" name="select" value="#tab2_${status.count-1}"/>
									</li>
		                  		</c:forEach>
	                  		</ul>
	                  		<div class="tab-content tab2-content">
	                  			<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status">
											<div id="tab2_${status.count-1}" class="tab-pane ${1==status.count?'active':''}" isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""/></div>
		                  		</c:forEach>
							</div>
	              		</c:if>	
	              		<c:if test="${fn:length(pricingPlanList)<1}">
	  							<div class="well">${local["eaap.op2.portal.pardOffer.addPricePlanInfo"]}</div>
	              		</c:if>

                  </div>
                </div>
                
                <div id="tab3" class="tab-pane">
                 <div class="form-group row">
                   <c:choose>
				      <c:when test="${o2pCloudFlag=='cloud'}">
					
                    <label class="col-md-3 control-label">
                    	<select class="form-control" name="settlementObject" title="Settlement Object choose!"  id="settlementObject">
	                    	<c:forEach var="offer" items="${productList}" step="1" varStatus="status"> 
								<option value="${offer.PRODUCTID}">${offer.PRODUCTNAME}</option> 
		           			</c:forEach>
                       </select>
                        <input type="hidden"  id="productSettleCount"  value="false"/>
                    </label>
                    <div class="col-md-3">
                    	<a href="javascript:;" class="btn default btn-select"><i class="fa fa-check-square-o"></i> Select </a> 
                    	<a href="javascript:;" class="btn default btn-delete"><i class="fa fa-trash-o"></i> Delete </a>
                    	<a href="javascript:;" class="btn default btn-add" id="addSettlement" data-addTpl="${fn:length(settleList)>=1?true:false}" data-container=".tab3-content" data-draw="${fn:length(settleList)}" data-target="#tab3"><i class="fa fa-plus"></i> ADD</a>
					</div>
					</c:when>
					<c:otherwise>
						   <div class="col-md-6 pull-right" id="tab3-tools"  style="margin-right:-50px">
			                    <label class="col-md-5 control-label">
			                    	<select class="form-control" name="settlementObject" title="Settlement Object choose!" >
				                    	<c:forEach var="offer" items="${productList}" step="1" varStatus="status"> 
											<option value="${offer.PRODUCTID}">${offer.PRODUCTNAME}</option> 
					           			</c:forEach>
			                       </select>
			                    </label>
			                    <div class="col-md-7">
			                    	<a href="javascript:;" class="btn default btn-select"><i class="fa fa-check-square-o"></i> Select </a> 
			                    	<a href="javascript:;" class="btn default btn-delete"><i class="fa fa-trash-o"></i> Delete </a>
									<a href="javascript:;" class="btn default btn-add" id="addSettlement" data-addTpl="${fn:length(settleList)>=1?true:false}" data-container=".tab3-content" data-draw="${fn:length(settleList)}" data-target="#tab3"><i class="fa fa-plus"></i> ADD</a>
								</div>
	                  	</div>
					</c:otherwise>
				   </c:choose>
                  </div>
                  <div class="createArea2 form-horizontal tabbable-custom">
                  	<c:if test="${fn:length(settleList)>=1}">
                  		<ul class="nav nav-tabs fix tabs-content-ajax  tab3_TabUL">
	  						<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<li class="${ fn:length(settleList)==status.count?'active':''}"  busiId="${settle.BUSIID}"  busiCode="${settle.BUSICODE}"  servCode="${settle.SERVCODE}" >
									<a data-toggle="tab" 
									onclick="javascript:loadEx('#tab3_${status.count-1}','${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=update&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}')"
									data-url="${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=update&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}"
									href="#tab3_${status.count-1}">${settle.BUSINAME}</a>
									<input type="checkbox" name="select" value="#tab3_${status.count-1}"/>
								</li>
	                  		</c:forEach>
                  		</ul>
                  		<div class="tab-content tab3-content">
                  			<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<div id="tab3_${status.count-1}" class="tab-pane ${fn:length(settleList)==status.count?'active':''}"  isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""/></div>
	                  		</c:forEach>
						</div>
              		</c:if>	
              		<c:if test="${fn:length(settleList)<1}">
  							<div class="well">${local["eaap.op2.portal.pardOffer.addSettlementInfo"]}</div>
              		</c:if>	
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>
  <!-- END CONTENT --> 
</div>
<!-- END CONTAINER -->
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<jsp:include page="/footer.jsp"/>
<!-- END COPYRIGHT --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jstree/jstree.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/hover-dropdown.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-wizard/bootstrap-wizard.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/pardOffer/pardOfferUpdate.js"></script> 
<script src="${APP_PATH}/pardOfferPricePlan/pricePlanAdd.js"></script>
<script src="${APP_PATH}/pardOfferSettlement/pardOfferSettlement.js"></script> 
<script type="text/javascript" src="http://ditu.google.cn/maps/api/js?key=AIzaSyA9-e46hdffPB8IeOMcErv_7qfFjwlzQXA&language=en"></script>

<script type="text/javascript">
 	jQuery(document).ready(function() {
  		App.init();
  		PardOfferUpdate.init();
  		
  		$("#operatorDropDown>div").find("a").each(function(){
  			if($(this).attr("sdata") == $("#operatorValue").val()){
  				$(this).click();
  			}
  		});
 	});
 	function productDetailModal(prodId){
		var url = APP_PATH + "/pardProduct/toDetail_noHead.shtml?product.productId="+prodId;
		$modal =  $('#productDetailModal');
	    $modal.load(url, '', function() {
	        $modal.modal('show');
	    });
	}
 	function offerDetailModal(offerId){
		var url = APP_PATH + "/pardOffer/toDetail_noHead.shtml?prodOffer.prodOfferId="+offerId;
		$modal =  $('#offerDetailModal');
	    $modal.load(url, '', function() {
	        $modal.modal('show');
	    });
	}
 	function changeOperatorValue(obj){
		$(obj).toggleClass('select');
		if($(obj).hasClass('select')){
			$("#operator").val($(obj).html());
			$("#operatorValue").val($(obj).attr("sdata"));
		}else{
			$("#operator").val("");
			$("#operatorValue").val("");
		}
		
		$("#operatorDropDown a").each(function(index,element){
		    if($(element).text()!=$(obj).html()){
		    	$(element).removeClass("select");
		    }
	   })
	} 
</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

<!-- productModal -->
<div id="productModal" class="modal container fade" tabindex="-1" style="display: none;">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardOffer.selProd"]}</h4>
  <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Product Name" id="productModal_prod_name"  name="prod_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }</label>
	   <div class="col-md-3">
	     <div>
	     	<input type="text" class="form-control" placeholder="Product Code" id="productModal_prod_code" name="prod_code"></input>
	    </div>
	  </div>
	 </div>
	 <div class="form-group">
	  <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.provider"] }</label>
	  <div class="col-md-3">
	   <div><select class="form-control" name="cooperationType" id="productModal_cooperationType" >
	      <optgroup><option value=""> </option>
	      <c:forEach var="provider" items="${providerList }">
	      	 <option value="${provider.cepCode }">${provider.cepName }</option>
	      </c:forEach>
	      </optgroup>
	      </select>
	    </div>
	   </div>
	 </div>
	</div>
	</form>	
    	<button type="button" style="float:right;" class="btn theme-btn queryProdcutModalBtn">${local["eaap.op2.portal.devmgr.query"] }</button>
   </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="productDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
          <th>${local["eaap.op2.portal.pardSpec.provider"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.code"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.name"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.note"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default productModal cancel">${local["eaap.op2.portal.doc.cancel"]}</button>
    <button type="submit" class="btn theme-btn productModal ok" data-dismiss="modal">${local["eaap.op2.portal.doc.submit"]}</button>
  </div>
</div>
<!-- /productModal -->
<!-- offerModal -->
<div id="offerModal" class="modal container fade" tabindex="-1" style="display: none;">
 <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardOffer.selOffer"]}</h4>
    <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Offer Name" id="offerModal_off_name"  name="off_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }</label>
	   <div class="col-md-3">
	     <div>
	     	<input type="text" class="form-control" placeholder="Offer Code" id="offerModal_off_code" name="off_code"></input>
	    </div>
	  </div>
	 </div>
	 <div class="form-group">
	  <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.provider"] }</label>
	  <div class="col-md-3">
	   <div><select class="form-control" name="cooperationType" id="offerModal_cooperationType" >
	      <optgroup><option value=""> </option>
	      <c:forEach var="provider" items="${providerList }">
	      	 <option value="${provider.cepCode }">${provider.cepName }</option>
	      </c:forEach>
	      </optgroup>
	      </select>
	    </div>
	   </div>
	 </div>
	</div>
	</form>	
    	<button type="button" style="float:right;" class="btn theme-btn queryofferModalBtn">${local["eaap.op2.portal.devmgr.query"] }</button>
  </div>
  </div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="offerDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
          <th>${local["eaap.op2.portal.pardSpec.provider"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.code"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.name"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.note"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default offerModal cancel">${local["eaap.op2.portal.doc.cancel"]}</button>
    <button type="submit" class="btn theme-btn offerModal ok" data-dismiss="modal">${local["eaap.op2.portal.doc.submit"]}</button>
  </div>
</div>
<!-- /offerModal -->
<!-- exclusionOfferModal -->
<div id="exclusionOfferModal" class="modal container fade" tabindex="-1" style="display: none;">
 <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardOffer.selOffer"]}</h4>
  <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Offer Name" id="exclusionOfferModal_off_name"  name="off_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }</label>
	   <div class="col-md-3">
	     <div>
	     	<input type="text" class="form-control" placeholder="Offer Code" id="exclusionOfferModal_off_code" name="off_code"></input>
	    </div>
	  </div>
	 </div>
	 <div class="form-group">
	  <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.provider"] }</label>
	  <div class="col-md-3">
	   <div><select class="form-control" name="cooperationType" id="exclusionOfferModal_cooperationType" >
	      <optgroup><option value=""> </option>
	      <c:forEach var="provider" items="${providerList }">
	      	 <option value="${provider.cepCode }">${provider.cepName }</option>
	      </c:forEach>
	      </optgroup>
	      </select>
	    </div>
	   </div>
	 </div>
	</div>
	</form>	
    	<button type="button" style="float:right;" class="btn theme-btn queryExclusionOfferModalBtn">${local["eaap.op2.portal.devmgr.query"] }</button>
  </div>
 </div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="exclusionOfferDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
          <th>${local["eaap.op2.portal.pardSpec.provider"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.code"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.name"]}</th>
          <th>${local["eaap.op2.portal.pardSpec.note"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default exclusionOfferModal cancel" data-dismiss="modal">${local["eaap.op2.portal.doc.cancel"]}</button>
    <button type="submit" class="btn theme-btn exclusionOfferModal ok" data-dismiss="modal">${local["eaap.op2.portal.doc.submit"]}</button>
  </div>
</div>
<!-- /exclusionOfferModal -->
<!-- product detail modal -->
<div id="productDetailModal" class="modal container fade" tabindex="-1" aria-hidden="true">
</div>
<!-- offer detail modal -->
<div id="offerDetailModal" class="modal container fade" tabindex="-1" aria-hidden="true">
</div>
</html>

