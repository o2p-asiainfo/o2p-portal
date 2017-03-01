<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="header.jsp" %>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.index.proGuide"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml?tid=${tid}">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.index.proGuide"]}</li>
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
          <h2>${local["eaap.op2.portal.doc.createProvNum"]}</h2> 
          <p>${local["eaap.op2.portal.doc.provRegistApprove"]}.</p> 
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-two">
          <h2>${local["eaap.op2.portal.doc.loginSys"]}</h2> 
          <p>${local["eaap.op2.portal.doc.getSysCode"]}.</p>  
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-three">
          <h2>${local["eaap.op2.portal.doc.abilityProvNew"]}</h2> 
          <p>${local["eaap.op2.portal.doc.searchAPIList"]}.</p> 
        </div>
      </div>
      <div class="col-md-3 col-sm-3">
        <div class="front-steps front-step-four">
          <h2>${local["eaap.op2.portal.doc.manageControl"]}</h2>  
          <p>${local["eaap.op2.portal.doc.abilityProvManager"]}.</p> 
        </div>
      </div>
    </div>
    <!-- END STEP --> 
    <!-- BEGIN QUICK LINKS-->
    <div class="row s-introduction margin-bottom-40">
      <div class="col-md-6">
        <h2>${local["eaap.op2.portal.doc.abilityProv"]}</h2>  
        <p>${local["eaap.op2.portal.doc.intro"]}</p>
        <br>
        <div class="row front-lists-v2 margin-bottom-15">
          <div class="col-md-6">
            <ul class="list-unstyled">  
              <li><i class="glyphicon glyphicon-send"></i> <a href="#">${local["eaap.op2.portal.doc.APIregister"]}</a></li> 
              <li><i class="fa fa-key"></i> <a href="#">${local["eaap.op2.portal.doc.APIManagemant"]} </a></li>
            </ul>
          </div>
          <div class="col-md-6">
            <ul class="list-unstyled">  
              <li><i class="glyphicon glyphicon-ok-circle"></i> <a href="#">${local["eaap.op2.portal.doc.grounding"]}</a></li>
              <li><i class="fa fa-globe"></i> <a href="#">${local["eaap.op2.portal.doc.APIPricingPlan"]}</a></li> 
            </ul>
          </div>
        </div>
         </div>
      <div class="col-md-6"> 
        <h2>${local["eaap.op2.portal.doc.provAdd"]} <a class="btn theme-btn btn-xs" href="${APP_PATH}/org/reg.shtml"><i class="glyphicon glyphicon-plus"></i>${local["eaap.op2.portal.devmgr.joinUs"]}</a></h2>   
        <p>${local["eaap.op2.portal.doc.provIntro"]}</p>
        <br> 
        <div class="row front-lists-v2 margin-bottom-15">
          <div class="col-md-6">
            <ul class="list-unstyled">
              <li><i class="glyphicon glyphicon-pencil"></i> <a href="#" style="font-size:15px!important;">${local["eaap.op2.portal.doc.serviceProvRegister"]}</a></li> 
            </ul>
          </div>
          <div class="col-md-6">
            <ul class="list-unstyled">
              <li><i class="fa fa-globe"></i> <a href="#">${local["eaap.op2.portal.doc.check"]}</a></li>
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
<script type="text/javascript" src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.min.js"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
