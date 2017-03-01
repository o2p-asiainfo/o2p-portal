<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="/header.jsp" %>
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
<link href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/jstree/css/style.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-wizard/bootstrap-wizard.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<form name="showProdForm" method="post" id="showProdForm" action="">
	<input type="hidden"  id="isAdd" name="isAdd" value="1"/>
	<input type="hidden"  id="prodOfferId" name="prodOffer.prodOfferId" value="${prodOffer.prodOfferId}"/>
	<input type="hidden"  id="o2pCloudFlag" name="o2pCloudFlag" value="${o2pCloudFlag}"/>
</form>

<!-- END HEADER --> 
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.pardOffer.localtion"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li><a href="${APP_PATH}/pardOffer/toIndex.shtml">${local["eaap.op2.portal.pardOffer.offer"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.pardOffer.localtion"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
 <!-- BEGIN CONTAINER -->
  <div class="container">
    <div class="panel panel-default margin-bottom-40 margin-top-20">
      <div class="panel-heading">
        <h3 class="panel-title" id="title">Fill in the information - <span class="step-title"> Step 1 of 4 </span></h3>
      </div>
      <div class="panel-body">
        <form id="submit_form" class="form-horizontal" action="#" novalidate>
        	<input type="hidden" id="offerProdStr" name="offerProdStr"/>
        	<input type="hidden" id="offerStr" name="offerStr"/>
        	<input type="hidden" id="offerMutualExclusionStr" name="offerMutualExclusionStr"/>
        	<input type="hidden" id="offerChannelStr" name="offerChannelStr"/>
          <div class="form-wizard">
            <div class="form-body">
              <ul class="nav nav-pills nav-justified steps">
                <li class="active"> <a class="step" data-toggle="tab" href="#tab1"> <span class="number"> 1 </span> <span class="desc"> ${local["eaap.op2.portal.price.baseInfo"]} </span> </a> </li>
                <li data-visit="false"> <a id="tab2_link" class="step" data-toggle="tab" href="#tab2"> <span class="number"> 2 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.pricePlan"]} </span> </a> </li>
                <li data-visit="false"> <a id="tab3_link" class="step active" data-toggle="tab" href="#tab3"> <span class="number"> 3 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.account"]}</span> </a> </li>
                <li data-visit="false"> <a id="tab4_link" class="step active" data-toggle="tab" href=""> <span class="number"> 4 </span> <span class="desc"> Offer Detail</span> </a> </li>
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
                <div id="tab1" class="tab-pane active form-horizontal ">
                  <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerName"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="Offer Name" id="prodOfferName" name="prodOffer.prodOfferName"/> </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerCode"]}:</label>
	                  <div class="col-md-9">   <input type="text" class="form-control input-xlarge  placeholder="Offer Code" id="productCode" name="prodOffer.extProdOfferId"/> </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerType"]} :</label>
	                    <div class="col-md-9">
	                      <div class="radio-list" data-error-container="#here">
	                          <label class="radio-inline"> 
	                          	<input type="radio" value="11" name="prodOffer.offerType" checked="checked"></input>
	                          	${local["eaap.op2.portal.pardOffer.offerType_main"]} </label>
	                          <label class="radio-inline"> 
	                          	<input type="radio" value="12" name="prodOffer.offerType" ></input>
	                          	${local["eaap.op2.portal.pardOffer.offerType_AddOn"]}</label>
	                      </div>
	                      <div id="here"></div>
	                    </div>
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
	                </div>
	                <div class="form-group">
                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardSpec.product"]}:</label>
		              <div class="col-md-9">
		                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
		                  <thead>
		                    <tr>
		                      <th> <i class="fa fa-plus productModalPlus"></i> </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.provider"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
		                      <th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
		                    </tr>
		                  </thead>
		                  <tbody id='productTB'>
		                  	<tr>
		                      <td colspan="7">None</td>
		                    </tr>
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
			                      <th> <i class="fa fa-plus offerModalPlus"></i> </th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
			                      <th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
			                    </tr>
			                  </thead>
			                 <tbody id="offerTB">
			                  	<tr>
			                      <td colspan="7">None</td>
			                    </tr>
			                  </tbody>
			                </table>
		                	<input type="text" class="form-control input-medium"  name="productOrOfferTableHasRow"  id="productOrOfferTableHasRow"  style="opacity:0; position:absolute; top:-100000px;"/>
					  	</div>
	                </div>
	                 <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.exclusion"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th><i class="fa fa-plus exclusionOfferModal"></i> </th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                      <th width="90"> ${local["eaap.op2.portal.pardSpec.operation"]} </th>
			                    </tr>
			                  </thead>
			                  <tbody id="exclusionOfferTB">
			                  	<tr>
			                      <td colspan="5">None</td>
			                    </tr>
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"]}:</label>
	                  <div class="col-md-9">  
						<textarea id="prodOfferDesc" name="prodOffer.prodOfferDesc" class="form-control" ></textarea> 
					  </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">
	                  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title='${local["eaap.op2.portal.pardOffer.timeToOrderRemark"]}'></i>
	                  <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.timeToOrder"]}:</label>
	                  <div class="col-md-9"> 
	            		 <div data-date-format="yyyy-mm-dd" data-date-start-date="+0d" data-date="2015-10-01" class="input-group input-large date-picker input-daterange" data-error-container="#error-container">
	                        <input type="text" name="prodOffer.formatEffDate" class="form-control od "value="${prodOffer.formatEffDate}"/>
	                        <span class="input-group-addon"> To </span>
	                        <input type="text" name="prodOffer.formatExpDate" class="form-control od " value="${prodOffer.formatExpDate}"/>
	                      </div>
	                      <div id="error-container"></div>
					  </div>
	                </div>
	               <!--  <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <label class="checkbox-inline">
	                          <input type="checkbox" value="1" name="channel" id="channel" checked="checked"></input>
	                          111Third Party Partners </label>
	                        <label class="checkbox-inline">
	                          <input type="checkbox" value="2" name="channel" id="channel" checked="checked"></input>
	                          Operator </label>
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div>-->
	                <!-- 
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                  <div class="col-md-9"> 
	                  	<div class="input-icon right input-large"  > <i class="glyphicon glyphicon-tree-deciduous"></i>
				          <input type="text" class="form-control input-large" id="jstreeText" name="jstreeText" data-hover="dropdown"/>
				          <div class="dropdown-menu jstree" role="menu">
				            <div class="jstree"></div>
				          </div>
				        </div>
					  </div>
	                </div> -->
	                <div class="form-actions fluid">
	                    <label class="col-md-3 control-label"></label>
				        <div class="col-md-9"> 
				        	<a class="btn theme-btn button-save" href="javascript:;" id="saveBtn"> Save <i class="m-icon-swapright m-icon-white"></i> </a> 
				        </div>
				      </div>
                </div>
                <div id="tab2" class="tab-pane ">
                  <div class="form-group row"></div>
                <div id="tab3" class="tab-pane">
                  <div class="form-group row form-inline fix-radio" id="tab3-tools">
                  </div>
                </div>
              </div>
              <div class="form-actions fluid">
              	<label class="col-md-3 control-label"></label>
                <div class="col-md-9"> 
                	<a class="btn default button-previous" href="javascript:;"> <i class="m-icon-swapleft"></i> Back </a> 
                	<a class="btn theme-btn button-next" href="javascript:;"> Continue <i class="m-icon-swapright m-icon-white"></i> </a> 
                	<a class="btn theme-btn button-submit" href="javascript:;"> Submit <i class="m-icon-swapright m-icon-white"></i> </a> 
                </div>
              </div>
            </div>
          </div>
          </div>
        </form>
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
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 

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
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/pardOffer/pardOfferAdd.js"></script> 
<script src="${APP_PATH}/pardOfferPricePlan/pricePlanAdd.js"></script> 
<script type="text/javascript">
 	jQuery(document).ready(function() {
  		App.init();
  		PardOfferAdd.init();
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

<!-- productModal -->
<div id="productModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-titel" style="margin-top:15px;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardOffer.selProd"]}</h4>
  <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }:</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Product Name" id="productModal_prod_name"  name="prod_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }:</label>
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
  </div></div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="productDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
          <th>${local["eaap.op2.portal.pardSpec.provider"]}</th>
          <th>Product Code</th>
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
    <button type="submit" class="btn theme-btn productModal ok">${local["eaap.op2.portal.doc.submit"]}</button>
  </div>
</div>
<!-- /productModal -->
<!-- offerModal -->
<div id="offerModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-title" style="margin-top:15px;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardOffer.selOffer"]}</h4>
    <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }:</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Offer Name" id="offerModal_off_name"  name="off_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }:</label>
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
  </div></div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="offerDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
		  <th>${local["eaap.op2.portal.pardSpec.offerProvider"]}</th>
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
    <button type="submit" class="btn theme-btn offerModal ok">${local["eaap.op2.portal.doc.submit"]}</button>
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
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }:</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Offer Name" id="exclusionOfferModal_off_name"  name="off_name"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }:</label>
	   <div class="col-md-3">
	     <div>
	     	<input type="text" class="form-control" placeholder="Offer Code" id="exclusionOfferModal_off_code" name="off_code"></input>
	    </div>
	  </div>
	 </div>
	 <div class="form-group">
	  <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.provider"] }:</label>
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
  </div></div>
  <div class="modal-body">
    <table class="table table-bordered  table-hover text-center group-check" id="exclusionOfferDt">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"/></th>
		  <th>${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
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
    <button type="button" class="btn btn-default exclusionOfferModal cancel1" data-dismiss="modal">${local["eaap.op2.portal.doc.cancel"]}</button>
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

