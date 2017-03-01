<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/css/rs-style.css" media="screen">
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/css/settings.css" media="screen">
<link href="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
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
        <h1>${local["eaap.op2.portal.index.devGuide"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml?tid=${tid}">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.index.devGuide"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container"> 
    <!-- BEGIN STEP -->
    <div class="row no-space-steps margin-bottom-40 margin-top-20">
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-one">
          <h2>${local["eaap.op2.portal.devmgr.createdevuser"]}</h2>
          <p>${local["eaap.op2.portal.devmgr.devRegistApprove"]}.</p> 
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-two">
          <h2>${local["eaap.op2.portal.devmgr.addApp"]}</h2>  
          <p>${local["eaap.op2.portal.devmgr.getappkeyAPI"]}.</p>
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-three">
          <h2>${local["eaap.op2.portal.devmgr.submitToCheck"]}</h2> 
          <p>${local["eaap.op2.portal.devmgr.appForApprove"]}.</p>  
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-four">
          <h2>${local["eaap.op2.portal.devmgr.publishsuccess"]}</h2> 
          <p>${local["eaap.op2.portal.devmgr.appManager"]}.</p>  
        </div>
      </div>
    </div>
    <!-- END STEP --> 
    <!-- BEGIN QUICK LINKS-->
    <div class="row s-introduction margin-bottom-40">
      <div class="col-md-6">
        <h2>${local["eaap.op2.portal.devmgr.apppublish"]}</h2>    
        <p>${local["eaap.op2.portal.devmgr.apppublishDesc"]}</p>
        <br>
        <div class="row front-lists-v2 margin-bottom-15">
          <div class="col-md-6">
            <ul class="list-unstyled">  
              <li><i class="fa fa-key"></i> <a href="#">${local["eaap.op2.portal.devmgr.getappkey"]}</a></li>
              <li><i class="glyphicon glyphicon-send"></i> <a href="#">${local["eaap.op2.portal.devmgr.askforpublish"]}</a> </li>
            </ul>
          </div>
          <div class="col-md-6">
            <ul class="list-unstyled">
              <li><i class="glyphicon glyphicon-certificate"></i> <a href="#">${local["eaap.op2.portal.devmgr.appManager"]}</a></li> 
            </ul>
          </div>
        </div>
         </div>
      <div class="col-md-6"> 
        <h2>${local["eaap.op2.portal.devmgr.devaddin"]} <a class="btn theme-btn btn-xs" href="${APP_PATH}/org/reg.shtml"><i class="glyphicon glyphicon-plus"></i> ${local["eaap.op2.portal.devmgr.joinUs"]}</a></h2>
        <p>${local["eaap.op2.portal.devmgr.devaddinDesc"]}</p> 
        <br> 
        <div class="row front-lists-v2 margin-bottom-15">
          <div class="col-md-6">
            <ul class="list-unstyled"> 
              <li><i class="glyphicon glyphicon-pencil"></i> <a href="#">${local["eaap.op2.portal.devmgr.devcreate"]}</a></li>
            </ul>
          </div>
          <div class="col-md-6">
            <ul class="list-unstyled"> 
              <li><i class="fa fa-globe"></i> <a href="#">${local["eaap.op2.portal.devmgr.checkstate"]}</a></li>
            </ul>
          </div>
        </div>
         </div>
    </div>
    <!-- END QUICK LINKS --> 
    <!-- END CONTAINER--> 
    
  </div>
  <!-- END CONTAINER --> 
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="footer.jsp" %>
<!-- END COPYRIGHT -->
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script type="text/javascript" src="resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
<script type="text/javascript" src="resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script> 
<script type="text/javascript" src="resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script> 
<script type="text/javascript" src="resources/plugins/bxslider/jquery.bxslider.min.js"></script> 
<script src="resources/scripts/app.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
