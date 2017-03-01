<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="../header.jsp" %>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${APP_PATH }/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH }/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/pages/portfolio.css" rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH }/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH }/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
</head>

<body class="page-header-fixed">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.pardServiceSpec.service"] }</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a> </li>           
          <li class="active"> ${local["eaap.op2.portal.pardServiceSpec.service"] }</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="s-border clearfix margin-bottom-40"> 
      <%@include file="../userAvatar.jsp" %>
      <!-- END USER INFO --> 
      <!-- BEGIN ADD PRODUCT -->
      <div class="s-add-product pull-right"> <a class="btn s-btn-add" href="${APP_PATH }/pardServiceSpec/toServiceSpecAddOrUpdate.shtml"><i class="fa fa-plus"></i></a></div>
      <!-- END ADD PRODUCT --> 
    </div>
    <!-- BEGIN FILTER -->
        <div class="tab-style-3 margin-bottom-40 margin-top-20">
      <ul class="nav nav-tabs ">
        <li class="active"> <a href="#tab_5_1" data-toggle="tab"> ${local["eaap.op2.portal.pardServiceSpec.service"] } </a> </li>
      </ul>
      <div class="tab-content form-horizontal form-bordered form-row-stripped">
        <div class="tab-pane active clearfix" id="tab_5_1">
         <div class="filter-v1">
      <ul class="mix-filter">
        <li class="filter" data-filter="all">All</li>
        <li class="filter" data-filter=".category_1">Online</li>
        <li class="filter" data-filter=".category_2">Pending</li>
        <li class="filter" data-filter=".category_4">Offline</li>
        <div class="input-group input-large pull-right">
          <input type="text" class="form-control" placeholder="ServiceSpec Name" id="serviceSpecName">
          <span class="input-group-btn">
          <button type="submit" class="btn theme-btn"><i class="fa fa-search search-btn show-search-icon"></i></button>
          </span> 
        </div>
      </ul>
      <div class="row mix-grid thumbnails">
        <div class="col-md-12"><img src="${APP_PATH }/resources/img/input-spinner.gif" alt=""></div>
      </div>
      <p class="text-center s-warning" style="display:none"><i class="fa fa-info-circle"></i> None</p>
    </div>
        </div>
        
      </div>
    </div>
   <form class="form-horizontal form-bordered form-row-stripped" method="post" role="serviceSpecForm" name="serviceSpecForm" id="serviceSpecForm">
     <div style="display:none;">
      	<input id="serviceSpecId" name="serviceSpecId" value=""/>
      </div>
   </form>
    <!-- END FILTER --> 
  </div>
  <!-- END CONTENT --> 
</div>
<!-- END PAGE CONTAINER --> 
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH }/resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<script src="${APP_PATH }/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH }/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH }/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH }/resources/plugins/back-to-top.js" type="text/javascript" ></script> 
<script src="${APP_PATH }/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="${APP_PATH }/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="${APP_PATH }/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" ></script> 
<script src="${APP_PATH }/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script> 
<script src="${APP_PATH }/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script> 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH }/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
<script src="${APP_PATH }/resources/plugins/jquery.mixitup.min.js"></script> 
<script src="${APP_PATH }/resources/scripts/app.js"></script> 
<script src="${APP_PATH }/pardServiceSpec/pardServiceSpecList.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
  $("#myCount").html('${local["eaap.op2.portal.pardServiceSpec.service"]}');
  PardSerivceSpecList.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
