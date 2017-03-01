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
<link href="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
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

<body class="page-header-fixed">

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.devmgr.appDes"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a></li>
          <li class="active">${appInfo.appName}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTENT -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="s-sidebar"> 
	    <c:choose>
	      <c:when test="${not empty app.sfileId}">
	         <img id="sysImg" src="${APP_PATH}/provider/readImg.shtml?sFileId=${app.sfileId}" width="250" height="166" alt="">
	      </c:when>
	      <c:otherwise>
	         <img id="sysImg" src="${APP_PATH}/resources/img/pics/250x250.jpg" width="250" height="166" alt="">
	      </c:otherwise>
	    </c:choose> 
     <a href="#"  <c:if test="${app.appState == 'D'|| app.appState == 'C' || app.appState == 'B'|| app.appState == 'E'|| app.appState == 'H'}">disabled</c:if> data-height="166" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal" class="s-pic-edit changeImg"> ${local["eaap.op2.portal.doc.message.edit"]} </a>
      <button class="btn default btn-block disabled" id="online"> ${local["eaap.op2.portal.devmgr.submitToCheck"]}</button>
      <button class="btn default btn-block disabled" id="upgrade">${local["eaap.op2.portal.devmgr.appupgrade"]}</button>
      <button class="btn default btn-block disabled" id="offline">${local["eaap.op2.portal.devmgr.appcancel"]}</button>
      <c:if test="${app.appState == 'A'|| app.appState == 'C'|| app.appState == 'G'|| app.appState == 'F'}"> 
      	<button class="btn theme-btn btn-block" id="delete">${local["eaap.op2.portal.devmgr.appdelete"]}</button>
      </c:if>
      <c:if test="${app.appState == 'B'|| app.appState == 'D'|| app.appState == 'E'|| app.appState == 'H'}">
      	<button class="btn default btn-block disabled">${local["eaap.op2.portal.devmgr.appdelete"]}</button>
      </c:if>
      <c:if test="${app.appState != 'A'}">
      	<a class="btn theme-btn btn-block showMessage" href="${APP_PATH}/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=APPTODO&lookFalg=&query=${app.appId}" target="_blank">${local["eaap.op2.portal.portalMessage.history"] }</a>
      </c:if>
      <c:if test="${app.appState == 'A'}">
      	<a class="btn default btn-block disabled" href="${APP_PATH}/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=APPTODO&lookFalg=&query=${app.appId}" target="_blank">${local["eaap.op2.portal.portalMessage.history"] }</a>
      </c:if>
    </div>
    <!-- BEGIN -->    
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">${local["eaap.op2.portal.devmgr.appDes"]}</h3>
          </div>
          <div class="panel-body"> 
            <form id = "form2" name = "form2"></form>
            <form class="form-horizontal form-bordered form-row-stripped" role="form" id = "form1" name = "form1"  method="post" >
            
              <input type="hidden" class="form-control" placeholder="Enter text" name="donameid" id="donameid" value=""/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appstate" id="appstate" value=""/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appId" id="appId" value="${app.appId}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appName" id="appName" value="${appInfo.appName}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appType" id="appType" value="${appType.appTypeCd}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appUrl" id="appUrl" value="${app.appUrl}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name = "appDesc" id = "appDesc" value="${app.appDesc}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="appCallbackUrl" id="appCallbackUrl" value="${app.appCallbackUrl}"/>
              <input type="hidden" class="form-control" placeholder="Enter text" name="bindOfferIds" id="bindOfferIds">
              <input type="hidden" class="form-control" placeholder="Enter text" name="applistString" id="applistString" value = "${applistString }">
              <input type="hidden" class="form-control" placeholder="Enter text" name="delOfferId" id="delOfferId" value = "">
              <input type="hidden" class="form-control" placeholder="Enter text" name="apiInfoListNum" id="apiInfoListNum" value = "${apiInfoListNum }">
              <input type="hidden" class="form-control" placeholder="Enter text" name="sFileId" id="sFileId" value="${app.sfileId}"/>
              
              
              
              <div class="form-body">
                <ol class="bwizard-steps clearfix mb20" role="tablist" id ="tablist">
                  <li style="z-index: 4;" id ="li1" class="active"><span class="label">1</span>${local["eaap.op2.portal.devmgr.addAppAbr"]} </li>
                  <li style="z-index: 3;" id ="li2"><span class="label">2</span>${local["eaap.op2.portal.devmgr.developTest"]} </li>
                  <li style="z-index: 2;" id ="li3"><span class="label">3</span>${local["eaap.op2.portal.devmgr.submitToCheck"]} </li>
                  <li style="z-index: 1;" id ="li4"><span class="label">4</span>${local["eaap.op2.portal.devmgr.appOnline"]}</li>
                </ol>
                <h4 class="form-section mt5">* ${local["eaap.op2.portal.devmgr.basicInfo"]}</h4>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appName"]}:</label>
                  <div class="col-md-6"> ${appInfo.appName}</div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appType"]}:</label>
                  <div class="col-md-6"> <a href="javascript:;" data-name="appType">${appType.appTypeName}</a> </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appAddress"]}:</label>
                  <div class="col-md-6"> 
                     <c:choose>
                        <c:when test="${ not empty app.appUrl}">
                           <a href="javascript:;" data-name="address">${app.appUrl}</a>
                        </c:when>
                        <c:otherwise>
                            <p class="form-control-static" style="color:#bbb">None</p>
                        </c:otherwise>
                     </c:choose>
                   </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appCallBackUrl"]}:</label>
                  <div class="col-md-6"> 
                     <c:choose>
                        <c:when test="${ not empty app.appCallbackUrl}">
                           <a href="javascript:;" data-name="callback">${app.appCallbackUrl}</a> 
                        </c:when>
                        <c:otherwise>
                            <p class="form-control-static" style="color:#bbb">None</p>
                        </c:otherwise>
                     </c:choose>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.updateAppKey"]}:</label>
                  <div class="col-md-6">
                    <p class="form-control-static">${app.appkey}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appSecret"]}: </label>
                  <div class="col-md-6">
                    <p class="form-control-static">${app.appsecure}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.comid"]}: </label>
                  <div class="col-md-6">
                    <p class="form-control-static">${component.componentId}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.createtime"]}: </label>
                  <div class="col-md-6">
                    <p class="form-control-static">${dateString}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.devmgr.appDes"]}:</label>
                  <div class="col-md-6">
                      <c:choose>
                        <c:when test="${ not empty app.appDesc}">
                            <a href="#" data-name="details" >${app.appDesc}</a>  
                        </c:when>
                        <c:otherwise>
                            <p class="form-control-static" style="color:#bbb">None</p>
                        </c:otherwise>
                     </c:choose>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">Status:</label>
                  <div class="col-md-8">
                     <p class="form-control-static">
                  		${stateMap[app.appState] }
                  	</p>
                  </div>
               </div>
                <c:if test="${app.appState=='C' || app.appState=='F'}">
                 <div class="form-group">
	              <label class="col-md-4 control-label">Feedback Message:</label>
	              <div class="col-md-8"  style="line-height:35px;color:blue;">
	              		${checkMsg }
	              </div>
	             </div>
                </c:if>
                 <h4 class="form-section"> <div class="btn-group pull-right" style="margin-top:-7px;"><a id="plus" class="btn default btn-sm" data-target="#addOfferModal" data-toggle="modal" href="javascript:;"><i class="fa fa-plus"></i> ADD </a> </div>* ${local["eaap.op2.portal.devmgr.useApp"]}</h4>
                   <div class="table-responsive">
			         <table class="table table-bordered table-condensed table-advance ">
					 <thead>
					  <tr>
					   <th>  #</th>
					   <th>${local["eaap.op2.portal.devmgr.packageName"]}</th>
				       <th>${local["eaap.op2.portal.devmgr.APIName"]} </th>
				       <th>${local["eaap.op2.portal.price.pricePlan"]} </th>
				       <th>Eff Time-Exp Time</th>
				       <th>${local["eaap.op2.portal.devmgr.packageDesc"]}  </th> 
					   <c:if test="${app.appState!='D' &&app.appState!='B' &&app.appState!='E'}">
				       <th>${local["eaap.op2.portal.devmgr.todosomething"]} </th> 
				       </c:if>
					  </tr>
					 </thead>
					 <tbody id="appOfferTB">
					   
					   <c:forEach var="apiInfo" items="${apiInfoList}" varStatus="vs"> 
					     <tr id=${apiInfo.PROD_OFFER_ID }>
					       <td colspan="1">${vs.count }</td>
					       <td colspan="1">${apiInfo.TR_PACKAGENAME}</td>
					       <td colspan="1">
					       <c:forEach var="api" items="${apiInfo.APIs}" varStatus="v1"> 
					         <a href="${APP_PATH }/api/apiDoc.shtml?serviceId=${api.API_ID }"  target="_Blank" >${api.API_NAME }</a> 
					       </c:forEach>
					       
					       </td>
					       <td colspan="1">${apiInfo.TR_PRICING_NAME_S}</td>
					       <td colspan="1">${apiInfo.TR_EFF_EXP}</td>
					       <td colspan="1">${apiInfo.TR_PROD_OFFER_DESC}</td>
					       <c:if test="${app.appState!='D' &&app.appState!='B' &&app.appState!='E'}">
					       <td colspan="1"><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>
					       </c:if>
					     </tr>
					   </c:forEach>
				
					 </tbody>
					</table>
			      </div>  
              </div>
              <div class="form-actions fluid">
                <div class="col-md-offset-4">
                  <button id="btn_app_update_a" type="button" class="btn theme-btn">${local["eaap.op2.portal.devmgr.addApp"]}</button>
                  <button id="btn_app_cancel" type="button" class="btn default">${local["eaap.op2.portal.devmgr.cancel"]}</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    
    <!-- END --> 
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
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script>  
<script src="${APP_PATH}/resources/scripts/manageApp.js"></script> 
<script type="text/javascript">
var _APP_PATH = "${APP_PATH}";
var APP_PATH = "${APP_PATH}";
var _IS_OP = "${app.appState}";

 jQuery(document).ready(function() {
  App.init();
  ManageApp.init();
 });
 
 //图片上传回调函数
 function uploadCallback(data){
	 if('' != data){
		 $('#sFileId').val(data);
		 $('#sysImg').attr('src',_APP_PATH+'/provider/readImg.shtml?sFileId='+data);
	 }
 }
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- MYModal -->
<div id="addOfferModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
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
      <tr id="offerCheckBox">
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
    <button type="button" class="btn theme-btn" data-dismiss="modal" id="btn-offer-Ok">${local["eaap.op2.portal.devmgr.ok"]}</button>
  </div>
</div>
<!-- /MYModal -->
<!-- apiModal -->
<div id="apiModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
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
    <button id="btn-api-Ok" type="button" data-dismiss="modal" class="btn theme-btn">${local["eaap.op2.portal.devmgr.ok"]}</button>
  </div>
</div>
<!-- /apiModal -->

</html>
