<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/jstree/css/style.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-wizard/bootstrap-wizard.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- END THEME STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
<%@include file="../header.jsp" %>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
 <input type="hidden" id="hidComponentId" />
<input type="hidden" id="hidComponentName" />
<input type="hidden" id="hidContractId" />
<input type="hidden" id="hidIsPublic" />
<input type="hidden" id="hidDirSerlistId" />
<input type="hidden" id="hidServiceId" />
<input type="hidden" id="hidTechImpAttId" />
<input type="hidden" id="hidTechimplId" />
<input type="hidden" id="hidContractVersionId" />
<input type="hidden" id="hidTcpCtrFId" /><!-- 请求的协议格式ID -->
<input type="hidden" id="hidRspTcpCtrFId" /><!-- 响应的协议格式ID -->
<input type="hidden" id="hidProdOfferName" />
<input type="hidden" id="hidProdOfferId" />
<input type="hidden" id="sysProcessId" value="${processId }"/>

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.doc.createSys"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.home"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.doc.createSys"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container">
    <div class="panel panel-default margin-bottom-40 margin-top-20">
      <div class="panel-heading">
        <h3 class="panel-title" id="titleCreate">${local["eaap.op2.portal.doc.message.fillInfo"]} - <span class="step-title"> ${local["eaap.op2.portal.doc.message.step1to3"]} </span></h3>
      </div>
      <div class="panel-body">
        <form id="submit_form" action="#" novalidate>
          <div class="form-wizard">
            <div class="form-body">
              <ul class="nav nav-pills nav-justified steps">
                <li class="active"> <a class="step" data-toggle="tab" href="#tab1"> <span class="number"> 1 </span> <span class="desc"> ${local["eaap.op2.portal.doc.baseInfo"]} </span> </a> </li>
                <li data-visit="false"> <a class="step" data-toggle="tab" href="#tab2"> <span class="number"> 2 </span> <span class="desc"> ${local["eaap.op2.portal.doc.applyability"]} </span> </a> </li>
                <li data-visit="false"> <a class="step active" data-toggle="tab" href="#tab3"> <span class="number"> 3 </span> <span class="desc">API ${local["eaap.op2.portal.doc.package"]} </span> </a> </li>
                <li data-visit="false"> <a class="step active" data-toggle="tab" href="#tab4"> <span class="number"> 4 </span> <span class="desc">Provisioning Process </span> </a> </li>
              
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
                  <div class="form-body">
                    <div class="form-group">
                      <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.doc.sysName"]}:</label>
                      <div class="col-md-8">
                        <input type="text" class="form-control" placeholder="Enter text" id="systemName"  name="systemName">
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.portal.doc.systemDetail"]}:</label>
                      <div class="col-md-8">
                        <textarea class="form-control" rows="3" id="systemDetails"  name="systemDetails"></textarea>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.portal.doc.imgInfo"]}:</label>
                      <input type="hidden"  id="sFileId"  name="sFileId">
                      <div class="col-md-8"> 
                      <div id="showImg" style="display:none;">
                      <img id="sysImg" src="" width="250" height="166" alt="">
                      </div>
                      <br>
                       <a class="btn theme-btn btn-sm changeImg" href="#" data-height="250" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal">${local["eaap.op2.portal.devmgr.imageUpload"]} <i class="fa fa-upload"></i></a>                      
                        <p class="help-block">${local["eaap.op2.portal.devmgr.appImageDesc"]}. (<1MB)</p>
                      </div>
                    </div>
                  </div>
                </div>
                <div id="tab2" class="tab-pane ">
   <h4 class="form-section">
                    <div class="btn-group pull-right" style="margin-top:-15px;"><a class="btn theme-btn btn-sm" data-toggle="dropdown" href="javascript:;"> ${local["eaap.op2.portal.doc.add"]} <i class="fa fa-angle-down"></i></a>
                      <ul class="dropdown-menu tpl" role="menu" data-set="addTarget">
                        <li> <a href="javascript:;" class="add-general"> ${local["eaap.op2.portal.doc.generalService"]} </a> </li>
                        <li> <a href="javascript:;" class="add-customized"> ${local["eaap.op2.portal.doc.oneselfService"]} </a> </li>
                      </ul>
                    </div>
                    * ${local["eaap.op2.portal.doc.applyability"]}</h4>
                  <table class="table table-bordered" id="topDt" >
                    <thead>
                      <tr>
                        <th> ${local["eaap.op2.portal.doc.fenType"]} </th>
		                <th> ${local["eaap.op2.portal.doc.applyabilityName"]} </th>
		                <th> ${local["eaap.op2.portal.doc.developValue"]} </th>
		                <th> ${local["eaap.op2.portal.doc.operator"]} </th>  
                      </tr>
                    </thead>
                    <tbody>
                    </tbody>
                  </table>
                </div>
                <!-- 定价 -->
                  <div id="tab3" class="tab-pane">
                   <h4 class="form-section">
                    <div class="pull-right" style="margin-top:-15px;"><a class="btn theme-btn btn-sm" href="javascript:;" data-toggle="modal" data-target="#modal5"  id="apiOfferAddBtn"><i class="fa fa-plus"></i> ${local["eaap.op2.portal.doc.add"]} </a> </div>
                    * API ${local["eaap.op2.portal.doc.package"]}</h4>
                 <div class="table-responsive">    
                 <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th>API ${local["eaap.op2.portal.doc.packageName"]} </th>
                        <th>  ${local["eaap.op2.portal.doc.applyability"]} </th>
                        <th>  ${local["eaap.op2.portal.doc.pricePlan"]} </th>
                        <th>  ${local["eaap.op2.portal.settlement.settle"]} </th>
                        <th> ${local["eaap.op2.portal.doc.operator"]} </th>
                      </tr>
                    </thead>
                    <tbody id="offerSysModalTB">
                    </tbody>
                  </table>     
                  </div>           
                </div>
                
                <!-- 开通流程创建 -->
                  <div id="tab4" class="tab-pane">
                   <h4 class="form-section">
                    <div class="pull-right" style="margin-top:-15px;"><a class="add-process btn theme-btn btn-sm " href="javascript:;" > Edit </a> </div>
                    * Process</h4>
                 <div class="table-responsive" hidden>    
                 <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th> Process Name </th>
                        <th> Process Description </th>
                        <th>  ${local["eaap.op2.portal.doc.pricePlan"]} </th>
                        <th>  ${local["eaap.op2.portal.settlement.settle"]} </th>
                        <th> ${local["eaap.op2.portal.doc.operator"]} </th>
                      </tr>
                    </thead>
                    <tbody id="proccesSysModalTB">
                    </tbody>
                  </table>     
                  </div>           
                </div>
                
              </div>
              <div class="form-actions fluid">	
              	<label class="col-md-4 control-label"></label>
                <div class="col-md-8"> <a class="btn default button-previous" href="javascript:;"> <i class="m-icon-swapleft"></i> ${local["eaap.op2.portal.doc.message.back"]} </a> <a class="btn theme-btn button-next" href="javascript:;"> ${local["eaap.op2.portal.doc.message.continue"]} <i class="m-icon-swapright m-icon-white"></i> </a> <a class="btn theme-btn button-submit" href="javascript:;"> ${local["eaap.op2.portal.devmgr.addApp"]} <i class="m-icon-swapright m-icon-white"></i> </a> </div>
              </div>
            </div>
          </div>
        </form>
        <form id = "finish_form"  name = "finish_form" method="post"></form>
      </div>
    </div>
  </div>
  <!-- END CONTENT --> 
</div>
</div>
</div>
<!-- END CONTAINER -->
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<!-- END COPYRIGHT --> 
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]--> 

<!-- END CORE PLUGINS --> 
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
<script src="${APP_PATH}/resources/plugins/jquery.i18n.properties-min-1.0.9.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/scripts/createSystem.js"></script> 
<script src="${APP_PATH}/provider/settleAdd.js"></script> 
<script type="text/javascript">
var oneRecords = '${local["eaap.op2.portal.doc.message.oneRecords"]}';
var success = '${local["eaap.op2.portal.doc.message.success"]}';
var toolong = '${local["eaap.op2.portal.doc.notexceed20char"]}';
 jQuery(document).ready(function() {
   var LANGUAGE = '${language}'; 
   jQuery.i18n.properties({
		name : 'eaap-op2-portal-messages',        
		path : APP_PATH+'/resources/i18n/',
		language: LANGUAGE, 
		mode : 'map'
  });
  App.init();
  CreateSystem.init();
 });
 //图片上传回调函数
 function uploadCallback(data){
	 if('' != data){
		 $('#sFileId').val(data);
		 $('#showImg').find('img').attr('src','../provider/readImg.shtml?sFileId='+data);
		 $('#showImg').show();
	 }
 }
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- Modal -->
<div id="addModal1" class="modal container fade" tabindex="-1" style="display: none;"></div>
<div id="addModal2" class="modal container fade" tabindex="-1" style="display: none;"></div>
<div id="editModal" class="modal container fade" tabindex="-1" style="display: none;"></div>
<div id="serviceAddress" class="modal fade" tabindex="-1" style="display: none;">
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
  <h4 class="modal-title">${local["eaap.op2.portal.doc.message.edit"]}</h4>
</div>
<div class="modal-body"> 
<div class="form-group">
<label class="control-label" id="showAddressName">
<font color="FF0000">*</font>mjftestG:</label>
<input type="text" id="showAttrSpecValue"  placeholder="Service Address" class="form-control" name="attrspecValue">
</div>
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>
  <button type="button" class="btn theme-btn" data-dismiss="modal" id="addressOK">${local["eaap.op2.portal.doc.message.ok"]}</button>
</div>
</div>
<!-- /Modal -->
<!-- Modal(offer) -->
<div id="offerSysModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.portal.doc.applyability"]}</h4>
  </div>
  <div class="modal-body">
      <form id="offerSysForm" name = "offerSysForm">
            <input type="hidden" id="capabilityCheckId" name ="capabilityCheckId"/>
            <input type="hidden" id="capabilityCheckName" name ="capabilityCheckId"  />
            <input type="hidden" id="offerId" name ="offerId"  />
            <input type="hidden" id="offerName" name ="offerName"  />
            <input type="hidden" id="componentIdforOffer" name ="componentIdforOffer" value="${componentId}" />
      </form>
<table class="table table-bordered table-striped table-hover text-center group-check3" id="offerSysDt">
      <thead>
        <tr>
          <th style="width:24px"><input type="checkbox" data-set=".group-check3 .checkboxes" class="group-checkable" onchange='checkboxCtr(this)'></th>
          <th>${local["eaap.op2.portal.doc.fenType"]}</th>
          <th>${local["eaap.op2.portal.doc.applyabilityName"]}</th>
          <th>${local["eaap.op2.portal.doc.developMemo"]}</th>
        </tr>
      </thead>
      <tbody id="offerSysDtBody">
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button  type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>
    <button id = "btn-offerSys-Ok" type="button" class="btn theme-btn">${local["eaap.op2.portal.doc.message.ok"]}</button>
  </div>
</div>
<!-- Modal(process) -->
<div id="processSysModal" class="modal container fade" tabindex="-1" style="display: none;">
  
</div>
<div id="modal4" class="modal container fade form-horizontal" tabindex="-1" style="display: none;"></div>
<div id="modal5" class="modal fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
    <h4 class="modal-title">${local["eaap.op2.portal.doc.addPackage"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
      <div class="form-group">
        <label class="control-label col-md-3"><font color="FF0000">*</font>${local["eaap.op2.portal.doc.packageName"]}:</label>
        <div class="col-md-9">
          <input type="text" class="form-control" name="offerNameNew" id="offerNameNew">
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn default" type="button" data-dismiss="modal">${local["eaap.op2.portal.devmgr.cancel"]}</button>
    <button id="btn-add-offer" data-dismiss="modal" class="btn theme-btn" type="button">${local["eaap.op2.portal.doc.message.ok"]}</button>
  </div>
</div>
<!-- /Modal(offer) -->
<!-- /Modal(settle) -->
<div id="modal6" class="modal container fade form-horizontal" tabindex="-1" style="display: none;"></div>
<div id="modal7" class="modal container form-horizontal" tabindex="-1" style="display: none;"></div>

</html>