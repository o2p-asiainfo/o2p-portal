<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

 <!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
 <link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
 <!-- END PAGE LEVEL PLUGIN STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<!-- BEGIN HEADER -->
  <jsp:include page="../header.jsp" >
	<jsp:param value="MyCenter" name="navBarPageId" /> 
  </jsp:include>
<!-- END HEADER --> 

 <!-- BEGIN PAGE CONTAINER -->
 <div class="page-container">
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
   <div class="container">
    <div class="col-md-4 col-sm-4">
     <h1>${local["eaap.op2.portal.devmgr.createApplication"]}</h1>
    </div>
    <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="index.jsp">${local["eaap.op2.portal.index.home"]}</a> </li> 
          <li class="active">${local["eaap.op2.portal.devmgr.createApplication"]}</li>
        </ul>
    </div>
   </div>
  </div>
  <!-- END BREADCRUMBS -->
  <!-- BEGIN CONTAINER -->
  <div class="container">
   <div class="panel panel-default margin-bottom-40 margin-top-20">
    <div class="panel-heading">
     <h3 class="panel-title">${local["eaap.op2.portal.devmgr.basicInfo"]}</h3>
    </div>
    <div class="panel-body">
     <form id="form2" name = "form2">
     </form>
     <form class="form-horizontal form-bordered form-row-stripped" role="form" id="form1" name = "form1">
      <input type="hidden" class="form-control" placeholder="Enter text" name="bindOfferIds" id="bindOfferIds">
     
      <div class="form-body">
       <div class="form-group">
        <label class="col-md-4 control-label">
         <font color='FF0000'>*</font>${local["eaap.op2.portal.devmgr.appName"]}:</label>
        <div class="col-md-6">
         <input type="text" class="form-control" placeholder="Enter text" name="appName" id="appName">
         <input type="hidden" class="form-control" placeholder="Enter text" value ="${appId }" id="appId" name="appId">
<!--          <span class="help-block">at least 4 characters</span> -->
       </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">
         <font color='FF0000'>*</font>${local["eaap.op2.portal.devmgr.appType"]}:</label>
        <div class="col-md-6">
         <select class="form-control"  name="appType">
          <optgroup>
	          <c:forEach var="obj" items="${appTypelist}" varStatus="listSystem">
	          <option value="${obj.appTypeCd}">${obj.appTypeName}</option>
	          </c:forEach>
              </optgroup>
         </select>
        </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">Bind Capability:</label>
       <div class="col-md-8">
         <table class="table table-bordered table-condensed table-advance">
		 <thead>
		  <tr>
		   <th> <i data-target="#addOfferModal" data-toggle="modal" class="fa fa-plus"></i> </th>
		   <th>${local["eaap.op2.portal.devmgr.packageName"]}</th>
	       <th>${local["eaap.op2.portal.devmgr.APIName"]} </th>
	       <th>${local["eaap.op2.portal.price.pricePlan"]} </th> 
	       <th>${local["eaap.op2.portal.devmgr.packageDesc"]}  </th> 
	       <th>${local["eaap.op2.portal.devmgr.todosomething"]} </th> 
		  </tr>
		 </thead>
		 <tbody id="appOfferTB">
		  <tr>
		   <td colspan="6">None</td>
		  </tr>
		 </tbody>
		</table>
       </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appAddress"]}:</label>
        <div class="col-md-6">
         <div class="input-icon"> <i class="fa fa-link"></i>
          <input type="url" class="form-control" placeholder="Link" name="appUrl">
         </div>
        </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appCallBackUrl"]}:</label>
        <div class="col-md-6">
         <div class="input-icon"> <i class="fa fa-link"></i>
          <input type="url" class="form-control" placeholder="Link" name="appCallbackUrl">
         </div>
        </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appIntro"]}:</label>
        <div class="col-md-6">
         <input type="text" class="form-control" placeholder="Link" name="appSumma">
        </div>
       </div>
       <div class="form-group">
        <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appDes"]}:</label>
        <div class="col-md-6">
         <textarea class="form-control" rows="3"  placeholder="Link" name="appDesc"></textarea>
        </div>
       </div>
       
        <div class="form-group">
          <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appImage"]}:</label>
               <input type="hidden"  id="sFileId"  name="sFileId">
               <div class="col-md-8"> 
					<div id="showImg" style="display:none;">
						<img id="sysImg" src="" width="250" height="166" >
					</div>
					<a class="btn theme-btn btn-sm changeImg" href="#" data-height="250" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal">${local["eaap.op2.portal.devmgr.imageUpload"]} <i class="fa fa-upload"></i></a>                      
	             	<p class="help-block">${local["eaap.op2.portal.devmgr.appImageDesc"]}. (<1MB)</p>
				</div>
        </div>
                    
      </div>
      <div class="form-group fluid">
       <div class="col-md-offset-4">
        <button type="button" class="btn theme-btn" onclick="submitAdd();">${local["eaap.op2.portal.devmgr.addApp"]}</button>
        <button id="btn_new_app_cancel" type="button" class="btn default">${local["eaap.op2.portal.devmgr.cancel"]}</button>
       </div>
      </div>
     </form>
     <form  id="appDetail" name = "appDetail" method="post">
     <input type="hidden" class="form-control" placeholder="Enter text" name="appIdtoDetail" id="appIdtoDetail">
     
     </form>
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
<jsp:include page="/footer.jsp"/>
<!-- END COPYRIGHT --> 
 <!-- Load javascripts at bottom, this will reduce page load time -->
 <!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) -->
 <!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]-->

 <!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
 <script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
 <script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script>
 <script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
 <script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
 <script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
 <script type="text/javascript" src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script>
 <script src="${APP_PATH}/resources/scripts/app.js"></script>
 <script src="${APP_PATH}/resources/scripts/createApp.js"></script>
 <script type="text/javascript">
 var _APP_PATH = "${APP_PATH}";
 jQuery(document).ready(function() {
  App.init();  
  CreateApp.init();
 });
 
 //图片上传回调函数
 function uploadCallback(data){
	 if('' != data){
		 $('#sFileId').val(data);
		 $('#showImg').find('img').attr('src',_APP_PATH+'/provider/readImg.shtml?sFileId='+data);
		 $('#showImg').show();
	 }
 }
 </script>
 <!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- MYModal -->
<div id="addOfferModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.devmgr.useApp"]}</h4>
  </div>
  <div class="modal-body">
  <div class="search-form-default">
      <form class="form-inline" action="#">
        <input type="text" class="form-control mr20" placeholder="Offer Name" id="OfferName" name="OfferName">
        <div class="input-group input-medium mr20">
          <input type="hidden" placeholder="ApiId" class="form-control" name="checkApiIds" id="checkApiIds">
          <input type="text" placeholder="Api" class="form-control" readonly name="checkApiNames" id="checkApiNames">
          <span class="input-group-btn">
          <button data-target="#apiModal" data-toggle="modal" class="btn default" type="button"><i class="fa fa-plus"></i></button>
          </span></div>
        <button id= "btn-offer-search" type="button" class="btn theme-btn">${local["eaap.op2.portal.devmgr.query"]}</button>
      </form>
    </div>
    <table class="table table-bordered table-striped table-hover text-center group-check" id="appOfferDt">
     <thead>
      <tr>
       <th style="width:24px"> <input type="checkbox" data-set=".group-check .checkboxes" class="group-checkable"></th>
       <th>${local["eaap.op2.portal.devmgr.packageName"]}</th>
       <th>${local["eaap.op2.portal.devmgr.APIName"]} </th>
       <th>${local["eaap.op2.portal.price.pricePlan"]} </th> 
       <th>${local["eaap.op2.portal.devmgr.packageDesc"]}  </th>      
      </tr>
     </thead>
     <tbody>
     </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <input type="hidden" class="form-control mr20" placeholder="Check Offer" id="CheckOffer" name ="CheckOffer">
    
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.devmgr.cancel"]}</button>
    <button type="button" class="btn theme-btn" id="btn-offer-Ok">${local["eaap.op2.portal.devmgr.ok"]}</button>
  </div>
</div>
<!-- /MYModal -->
<!-- apiModal -->
<div id="apiModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">Api</h4>
  </div>
  <div class="modal-body">
      <div class="search-form-default">
      <form class="form-inline" action="#" id="apiForm">
        <div class="input-group mr20">
          <label for="">${local["eaap.op2.portal.devmgr.apitype"]}:</label>
          <select class="select2 form-control mr20" placeholder="name" style="width:150px" name = "dirId" id="dirId">           
	             <optgroup>
	             <option value=""></option>
		          <c:forEach var="obj" items="${category}" varStatus="listSystem">
		          <option value="${obj.DIR_ID}">${obj.DIR_NAME}</option>
		          </c:forEach>
	              </optgroup>        
         	 </select>
        </div>
        <input type="text" class="form-control mr20" placeholder="Api Name" name ="tmpAipName" id="tmpAipName">
        <input type="text" class="form-control mr20" placeholder="Provider" id="Provider" name="Provider">
        <button id="btn-api-search" type="button" class="btn theme-btn"  >${local["eaap.op2.portal.devmgr.query"]}</button>
      </form>
    </div>
    <table class="table table-bordered table-striped table-hover text-center group-check2" id="apiDt">
      <thead>
        <tr>
          <th style="width:24px"><input type="checkbox" data-set=".group-check2 .checkboxes" class="group-checkable"></th>
          <th>${local["eaap.op2.portal.devmgr.APIName"]} </th>
          <th>${local["eaap.op2.portal.devmgr.orgName"]} </th>
          <th>${local["eaap.op2.portal.devmgr.apidesc"]}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <input type="hidden" class="form-control mr20" placeholder="Check Api" id="CheckApi" name ="CheckApi">
    <input type="hidden" class="form-control mr20" placeholder="Check Api Name" id="CheckName" name ="CheckName">
        
        
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.devmgr.cancel"]}</button>
    <button id="btn-api-Ok" type="button" class="btn theme-btn">${local["eaap.op2.portal.devmgr.ok"]}</button>
  </div>
</div>
<!-- /apiModal -->
</html>
