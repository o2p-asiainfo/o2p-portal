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
<link href="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<form id="showProdForm" name="showProdForm" method="post" id="showPordOfferForm" action="">
	 <input type="hidden"  id="prodOfferId" name="prodOffer.prodOfferId" value="${prodOffer.prodOfferId}"/>
	 <input type="hidden"   name="prodOffer.prodOfferName" value="${prodOffer.prodOfferName}"/>
	 <input type="hidden"   name="prodOffer.statusCd" value="${prodOffer.statusCd}"/>
	 <input type="hidden"   name="activity_Id" value="${activity_Id}"/>
	 <input type="hidden"   name="message.msgId" value="${message.msgId}"/>
</form>

<!-- END HEADER --> 
<!-- BEGIN PAGE CONTAINER -->
<div class="container" id="infoModalList" class="modal container fade" aria-hidden="true"> 
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.price.baseInfo"] }</h4>
  </div>
  <div class="modal-body">
	<div class="panel panel-default ">
	    	<div class="panel-heading">
		      <div class="panel-body">
		      	<form class="form-horizontal form-bordered form-row-stripped" role="form">
              <div class="form-body">
                <!-- BEGIN CONTAINER -->
	      <form id="submit_form" class="form-horizontal" action="#" novalidate>
	      <div class="form-wizard">
            <div class="form-body">
              <ul class="nav nav-pills nav-justified steps">
                <li data-visit='false' class='${isAdd==1?"":"active"}'> <a class="step" data-toggle="tab" href="#tab7"> <span class="number"> 1 </span> <span class="desc"> ${local["eaap.op2.portal.price.baseInfo"]} </span> </a> </li>
                <li data-visit='false' class='${isAdd==1?"active":""}'> <a class="step" data-toggle="tab" href="#tab8"> <span class="number"> 2 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.pricePlan"]} </span> </a> </li>
                <li data-visit="false"> <a class="step active" data-toggle="tab" href="#tab9"> <span class="number"> 3 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.account"]}</span> </a> </li>
              </ul>
              <div role="progressbar" class="progress progress-striped" id="bar">
                <div class="progress-bar progress-bar-success" style="width: 25%;"> </div>
              </div>
              <div class="tab-content">
                <div class="alert alert-danger display-none">
                  <button data-dismiss="alert" class="close"></button>
                  You have some form errors. Please check below. </div>
                <div class="alert alert-success display-none">
                  <button data-dismiss="alert" class="close"></button>
                  Your form validation is successful! </div>
                <div id="tab7" class='tab-pane ${isAdd==1?"":"active"} form-horizontal '>
                  <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerName"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="prodOfferName" name="prodOffer.prodOfferName" value="${prodOffer.prodOfferName}" disabled="disabled"/> </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerCode"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="productCode" name="prodOffer.extProdOfferId" value="${prodOffer.extProdOfferId}" disabled="disabled"/> </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>Offer Type :</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <c:if test="${prodOffer.offerType==11}">
								<input type="radio" value="11" name="prodOffer.offerType" checked="checked" disabled="disabled"/>
								 <label class="checkbox-inline">Main Offer </label>
							</c:if> 
	                        <c:if test="${prodOffer.offerType==12}">
								<input type="radio" value="12" name="prodOffer.offerType" checked="checked" disabled="disabled"/>
								<label class="checkbox-inline">Add On Offer </label>
							</c:if> 
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div>
	                <div class="form-group">
                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardSpec.product"]}:</label>
		              <div class="col-md-9">
		                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
		                  <thead>
		                    <tr>
		                      <th> &nbsp; </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.provider"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
		                    </tr>
		                  </thead>
		                  <tbody id='productTB'>
		                  	<c:if test="${fn:length(offerProdRelList)>=1}">
    							<c:forEach var="prod" items="${offerProdRelList}" step="1" varStatus="status"> 
				             	<tr id="${prod.productId}">
				             		<td>${status.count}</td>
				             		<td>${prod.productProviderName}</td>
		                      		<td>${prod.extProdId}</td>
		                      		<td>${prod.productName}</td>
		                      		<td>${prod.minCount}</td>
		                      		<td>${prod.maxCount}</td>
		                    	</tr>
	                     		</c:forEach>
                			</c:if>	
                			<c:if test="${fn:length(offerProdRelList)<1}">
    							<tr>
			                      <td colspan="6">None</td>
			                    </tr>
                			</c:if>	
		                  </tbody>
		                </table>
				  	   </div>
	                </div>
	                <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.offer"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th> &nbsp; </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
			                    </tr>
			                  </thead>
			                 <tbody id="offerTB">
			                  	<c:if test="${fn:length(prodOfferRelList1)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList1}" step="1" varStatus="status"> 
					             	<tr>
					             		<td>${status.count}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td>${offer.prodOfferName}</td>
			                      		<td>${offer.minCount}</td>
			                      		<td>${offer.maxCount}</td>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList1)<1}">
	    							<tr>
				                      <td colspan="5">None</td>
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
			                      <th>&nbsp;</th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                    </tr>
			                  </thead>
			                  <tbody id="exclusionOfferTB">
			                  	<c:if test="${fn:length(prodOfferRelList2)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList2}" step="1" varStatus="status"> 
					             	<tr>
					             		<td>${status.count}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td>${offer.prodOfferName}</td>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList2)<1}">
	    							<tr>
				                      <td colspan="3">None</td>
				                    </tr>
	                			</c:if>	
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"]}:</label>
	                  <div class="col-md-9">  
						<textarea id="prodOfferDesc" name="prodOffer.prodOfferDesc" class="form-control" disabled="disabled">${prodOffer.prodOfferDesc}</textarea> 
					  </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">
	                  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title='${local["eaap.op2.portal.pardOffer.timeToOrderRemark"]}'></i>
	                  <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.timeToOrder"]}:</label>
	                  <div class="col-md-9"> 
	            		 <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
	                        <input type="text" name="prodOffer.formatEffDate" class="form-control od" disabled="disabled" value="${prodOffer.formatEffDate}"/>
	                        <span class="input-group-addon"> To </span>
	                        <input type="text" name="prodOffer.formatExpDate" class="form-control od" disabled="disabled" value="${prodOffer.formatExpDate}"/>
	                      </div>
	                      <div id="error-container"></div>
					  </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,1)}">
								<input type="checkbox" value="1" name="channel" checked="checked" disabled="disabled"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,1))}">
	                          <input type="checkbox" value="1" name="channel" disabled="disabled"/>
	                        </c:if> 
	                          Third Party Partners </label>
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,2)}">
								<input type="checkbox" value="2" name="channel" checked="checked" disabled="disabled"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,2))}">
	                          <input type="checkbox" value="2" name="channel" disabled="disabled"/>
	                        </c:if> 
	                          Operator </label>
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.status"]}:</label>
	                  <div class="col-md-9"  style="line-height:30px;">
							<c:if test="${prodOffer.statusCd==1200}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1200"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1299}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1299"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1289}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1289"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1288}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1288"]}
		                 	</c:if>
		                 	 <c:if test="${prodOffer.statusCd==1287}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1287"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1279}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1279"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1277}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1277"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1500}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1500"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1278}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1278"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1298}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1298"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1000}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1000"]}
		                 	</c:if>
					  </div>
	                </div>	                            
	                <!-- 
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                  <div class="col-md-9"> 
	                  	<div class="input-icon right input-large" > <i class="glyphicon glyphicon-tree-deciduous"></i>
				          <input type="text" class="form-control input-large" id="jstreeText" name="offerChannelStr" value="${FuncSeletedTreeMap.funcValue}" data-hover="dropdown" disabled="disabled">
				          <div class="dropdown-menu jstree" role="menu">
				            <div class="jstree"></div>
				          </div>
				        </div>
					  </div>
	                </div> -->
                </div>
                <div id="tab8" class="tab-pane ${isAdd==1?"active":""}">
                  <div class="form-horizontal tabbable-custom createArea">
	                  	<c:if test="${fn:length(pricingPlanList)>=1}">
	                  		<ul class="nav nav-tabs tab2_TabUL">
		  						<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status"> 
									<li class="${1==status.count?'active':''}">
										<a data-toggle="tab" onclick="javascript:loadEx_tab2('#tab2_${status.count-1}','${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?actionType=detail&prodIds=${prodOffer.prodOfferId}&pricingInfoId=${pricePlan.pricingInfoId}&pricingTargetId=${pricePlan.pricingTargetId}&applicType=1&formNum=${status.count-1}')" 
											 data-url="${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?actionType=update&prodIds=${prodOffer.prodOfferId}&pricingInfoId=${pricePlan.pricingInfoId}&pricingTargetId=${pricePlan.pricingTargetId}&applicType=1&formNum=${status.count-1}"
											 data-key="${status.count-1}" href="#tab2_${status.count-1}">${pricePlan.pricingName}</a>
										<input type="checkbox" name="select" value="#tab2_${status.count-1}"/>
									</li>
		                  		</c:forEach>
	                  		</ul>
	                  		<div class="tab-content tab2-content">
	                  			<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status">
											<div id="tab2_${status.count-1}" class="tab-pane ${1==status.count?'active':''}" isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
		                  		</c:forEach>
							</div>
	              		</c:if>	
	              		<c:if test="${fn:length(pricingPlanList)<1}">
	  							<div class="well">${local["eaap.op2.portal.pardOffer.noData"]}</div>
	              		</c:if>
                  </div>
                </div>
                <div id="tab9" class="tab-pane">
                  <input type="hidden" id="addSettlement" data-addTpl="${fn:length(settleList)>=1?true:false}" data-container=".tab3-content" data-draw="${fn:length(settleList)}" data-target="#tab3"/>
                  <div class="createArea2 form-horizontal tabbable-custom">
                  	<c:if test="${fn:length(settleList)>=1}">
                  		<ul class="nav nav-tabs fix tabs-content-ajax  tab3_TabUL">
	  						<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<li class="${fn:length(settleList)==status.count?'active':''}">
									<a data-toggle="tab" href="#tab3_${status.count-1}" 
									onclick="javascript:loadEx('#tab3_${status.count-1}','${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=detail&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}')"
									data-url="${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=detail&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}">${settle.BUSINAME}</a>
									<input type="checkbox" name="select" value="#tab3_${status.count}"/>
								</li>
	                  		</c:forEach>
                  		</ul>
                  		<div class="tab-content tab3-content">
                  			<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<div id="tab3_${status.count-1}" class="tab-pane ${1==status.count?'active':''}" isLoad="${fn:length(settleList)==1?1:0}">
									<img src="${APP_PATH}/resources/img/input-spinner.gif" alt="">
								</div>
	                  		</c:forEach>
						</div>
              		</c:if>	
              		<c:if test="${fn:length(settleList)<1}">
  							<div class="well">${local["eaap.op2.portal.pardOffer.noData"]}</div>
              		</c:if>	
                  </div>
                </div>
              </div>
              <div class="form-actions fluid">
                <div class="col-md-offset-4"> 
                	<a class="btn default button-previous" href="${APP_PATH}/pardOffer/toIndex.shtml" id="backBtn"> <i class="m-icon-swapleft"></i> Back </a> 
                </div>
              </div>
			</div>
      	</div>
		</form>
			      
			</div>
			</form>
		      </div>
	</div>
 </div>

<!-- END COPYRIGHT --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
	<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jstree/jstree.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.min.js"></script> 
	<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
	<script src="${APP_PATH}/resources/scripts/app.js"></script> 
	<script src="${APP_PATH}/pardOffer/pardOfferDetail.js"></script> 
	<script src="${APP_PATH}/pardOfferPricePlan/pricePlanAdd.js"></script>
	<script src="${APP_PATH}/pardOfferSettlement/pardOfferSettlement.js"></script> 
	<script type="text/javascript">
	 	jQuery(document).ready(function() {
	  		App.init();
	  		PardOfferDetail.init();
	 	});
	</script> 
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>

