<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="../header.jsp" %>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<style>
	.form-group2{margin:15px 0; padding:15px 0px;}
</style>
<body class="page-header-fixed">

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.index.orderManager"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li><a href="${APP_PATH }/partnerGuide.jsp">${local["eaap.op2.portal.index.pradIndex"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.index.orderManager"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <div class="container">
  <div class="panel" style="padding-top:15px;">
	<form class="form-horizontal" role="form">
      <div class="form-body">
      
       <div class="form-group">
         <label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderNumber"]}</label>
         <div class="col-md-4">
           	<input type="text" class="form-control" placeholder=""  id="soNbr" name="soNbr"></input>
        </div>
        <label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderType"]}</label>
        <div class="col-md-4">
         	<select class="form-control" name="soType" id="soType">
	            <optgroup><option value=""> </option>
	            <c:forEach var="oType" items="${selectOrderTypeList }">
	            	 <option value="${oType.cepCode }">${oType.cepName }</option>
	            </c:forEach>
	            </optgroup>
            </select>
         </div>
       </div>

      <div class="form-group">
        <label class="col-md-2 control-label"> ${local["eaap.op2.portal.orderMgr.acceptDate"]}</label> 
        <div class="col-md-4">
			  <div data-date-format="yyyy-mm-dd"  class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
			   <input type="text"  id="startAcceptDate" name="startAcceptDate"  class="form-control od"   style="width:130px !important;"  value="" />
			   <span class="input-group-addon"> To </span>
			   <input type="text"  id="endAcceptDate" name="endAcceptDate"  class="form-control od"   style="width:130px !important;" value="" />
			 </div>
        </div>
        
        <label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.crmOrderId"]}</label>
         <div class="col-md-4">
           	<input type="text" class="form-control" placeholder=""  id="orderNbr" name="orderNbr"></input>
        </div>
     </div>
	<input type="button" class="btn theme-btn" style="float:right;" id="searchBut" value='${local["eaap.op2.portal.devmgr.query"]}' /> 
</div>
</form>	
</div></div>
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="portlet box s-protlet-theme">
      <div class="portlet-title">
        <div class="caption"> ${local["eaap.op2.portal.orderMgr.orderList"]} </div>
        <div class="actions">
          <button class="btn default" id="btn-detail"><i class="fa fa-edit"></i> View </button>
        </div>
      </div>
      <div class="portlet-body">
        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore " id="orderListTable">
          <thead>
            <tr>
              <th></th>
              <th> ${local["eaap.op2.portal.orderMgr.orderId"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.crmOrderId"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.orderNumber"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.orderType"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.acceptDate"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.countryCode"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.locality"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.mainServiceId"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.tradeTypeCode"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.orderState"]} </th>
              <th> ${local["eaap.op2.portal.orderMgr.flow"]} </th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
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
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/orderManager/orderManager.js"></script> 
<script src="${APP_PATH}/orderManager/orderManager_en_US.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
  OrderManager.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- MYModal -->
<div id="myModal" class="modal modal-lg fade" tabindex="-1" style="display: none;">
  <form id="form1" role="form" action="" method="POST">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
      <h4 class="modal-title">${local["eaap.op2.portal.orderMgr.orderDetail"]}</h4>
    </div>
    <div class="modal-body">
			<div data-addtpl="true" class="portlet-body tabsArea">
					<ul class="nav nav-tabs fix tabs-content-ajax  detail_tab">
							<li class="active"><a href="#tab_1"  data-toggle="tab" >${local["eaap.op2.portal.orderMgr.detail"]}</a></li>
							<li class=""><a href="#tab_2"  data-toggle="tab" >${local["eaap.op2.portal.orderMgr.user"]}</a></li>
							<li class=""><a href="#tab_3"  data-toggle="tab" >${local["eaap.op2.portal.orderMgr.address"]}</a></li>
							<li class=""><a href="#tab_4"  data-toggle="tab" >${local["eaap.op2.portal.orderMgr.customer"]}</a></li>
							<li class=""><a href="#tab_5"  data-toggle="tab" >${local["eaap.op2.portal.orderMgr.product"]}</a></li>
			    	</ul>
			 		<div class="tab-content"  style="height:300px;">
							<div isload="1" class="tab-pane active" id="tab_1">
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderId"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="orderId" value=""/>
									</div>
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderNumber"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="orderNumber" value=""/>
									</div>
								</div>
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderType"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="orderType" value=""/>
									</div>
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.acceptDate"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="acceptDate" value=""/>
									</div>
								</div>
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.countryCode"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="countryCode" value=""/>
									</div>
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.locality"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="locality" value=""/>
									</div>
								</div>
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.mainServiceId"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="mainServiceId" value=""/>
									</div>
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.orderState"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="orderState" value=""/>
									</div>
								</div>
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.tradeTypeCode"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="tradeTypeCode" value=""/>
									</div>
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.bPMProcessId"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="bPMProcessId" value=""/>
									</div>
								</div>
       							<div class="form-group2">
									<label class="col-md-2 control-label">${local["eaap.op2.portal.orderMgr.originalOrderNumber"]}</label>
									<div class="col-md-4">
										<input type="text" class="form-control"  id="originalOrderNumber" value=""/>
									</div>
								</div>
            				</div>
            				
							<div isload="1" class="tab-pane" id="tab_2">
							      <div class="portlet-body">
							        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" >
							          <thead>
							            <tr>
											<th> ${local["eaap.op2.portal.orderMgr.orderNumber"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.userId"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.userType"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.companyName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.firstName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.middleName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.lastName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.address"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.phone"]} </th>
							            </tr>
							          </thead>
									  <tbody id="userInfoBody">
							          </tbody>
							        </table>
							      </div>
							</div>
							
							<div isload="1" class="tab-pane" id="tab_3">
							      <div class="portlet-body">
							        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" >
							          <thead>
							            <tr>
											<th> ${local["eaap.op2.portal.orderMgr.userId"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.city"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.zipCode"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.street"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.building"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.stairway"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.floor"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.door"]} </th>
							            </tr>
							          </thead>
									  <tbody id="addressBody">
							          </tbody>
							        </table>
							      </div>
							</div>
							
							<div isload="1" class="tab-pane" id="tab_4">
							      <div class="portlet-body">
							        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" >
							          <thead>
							            <tr>
											<th> ${local["eaap.op2.portal.orderMgr.orderNumber"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.customerId"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.companyName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.firstName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.middleName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.lastName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.state"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.customerType"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.country"]} </th>
							            </tr>
							          </thead>
									  <tbody id="customerBody">
							          </tbody>
							        </table>
							      </div>
							</div>
							
							<div isload="1" class="tab-pane" id="tab_5">
							      <div class="portlet-body">
							        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" >
							          <thead>
							            <tr>
											<th> ${local["eaap.op2.portal.orderMgr.orderNumber"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.mainFlag"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.productId"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.productName"]} </th>
											<th> ${local["eaap.op2.portal.orderMgr.actionType"]} </th>
							            </tr>
							          </thead>
									  <tbody id="productBody">
							          </tbody>
							        </table>
							      </div>
							</div>
					</div>
		  </div>
		  
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.orderMgr.cancel"]}</button>
    </div>
  </form>
</div>
<!-- /MYModal -->
</html>
