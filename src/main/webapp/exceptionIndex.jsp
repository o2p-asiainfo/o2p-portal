<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
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
        <h1>Exception Center</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active">Exception Center</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
   
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40">
  <div class="row">
    <div class="col-md-12 page-error">
      <div class="number"><i class="fa fa-frown-o"></i></div>
      <div class="details">
        <h3>Sorry!  Something went wrong.</h3>
        <p> We are fixing it!&nbsp;&nbsp;&nbsp;<span id="msgDetail">detail</span></p>
        <br>
        <div style="display: none;" id="msgError"><h4>${retMsg }</h4></div>
        <br>  Please try again. 
        <button type="button" class="btn default" id="backBtn">Back</button>
        <br>
      </div>
    </div>
  </div>
</div>
  <!-- END CONTAINER --> 
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script>
$(document).ready(function(){
	$("#msgDetail").click(function(){
		$("#msgError").toggle();
	});
	
	$("#backBtn").click(function(){
		history.go(-1);
	});
});
</script>
</html>
