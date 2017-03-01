<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="../header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
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
        <h1>${local["eaap.op2.portal.index.signUp"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/default.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.index.signUp"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40">
  <div class="row">
    <div class="col-md-12 page-error">
      <div class="number"><i style="color:#11ad45;" class="fa fa-check-circle success"></i></div>
      <div style="" class="details">
              <h3>${local["eaap.op2.portal.reg.successful"]}</h3>
              <p>
                  ${local["eaap.op2.portal.reg.registrationInfoOne"]}<br> ${local["eaap.op2.portal.reg.registrationInfoTwo"]}<br>
                  <a id="gotoLogin" href="${APP_PATH}/org/tologin.shtml">${local["eaap.op2.portal.reg.gotoLogin"]}</a> or <a id="gotoHome"  href="${APP_PATH}/default.shtml">${local["eaap.op2.portal.reg.gotoHome"]}</a>.
              </p>
      </div>
    </div>
  </div>
</div>
  <!-- END CONTAINER --> 
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script type="text/javascript">

</script>
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY --> 
</html>
