<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="../none_header.jsp" %>

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

<body class="page-header-fixed">

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1></h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">Home</a> </li>
          <li class="active">Statistics</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="search-form-default">    
      <div class="row">
          <div class="col-md-3 col-sm-4">            
              <select name="" class="form-control" id="">
                <option>Choose a Month</option>
                <option value="">Jan</option>
                <option value="">Feb</option>
                <option value="">Mar</option>
                <option value="">Apr</option>
                <option value="">May</option>
                <option value="">Jun</option>
                <option value="">Jul</option>
                <option value="">Aug</option>
                <option value="">Sep</option>
                <option value="">Oct</option>
                <option value="">Nov</option>
                <option value="">Dec</option>
              </select>      
          </div>
        </div> 
    </div>
  <div class="row">
        <div class="col-md-6">
          <!-- BEGIN Portlet PORTLET-->
          <div class="portlet box s-protlet-theme">
            <div class="portlet-title">
              <div class="caption">
                Consumption Overview
              </div>
              <div class="tools">
                <a class="collapse" href="#">
                </a>
                <a class="config" data-toggle="modal" href="#portlet-config">
                </a>
                <a class="reload" data-url="portlet_ajax_content_error.html" data-error-display="notific8" href="#">
                </a>
                <a class="remove" href="#">
                </a>
              </div>
            </div>
            <div class="portlet-body portlet-empty">
               <div id="pie" style="height:300px;"></div>
            </div>
          </div>
          <!-- END Portlet PORTLET-->
        </div>
        <div class="col-md-6">
          <!-- BEGIN Portlet PORTLET-->
          <div class="portlet box s-protlet-theme">
            <div class="portlet-title">
              <div class="caption">
                Recent Consumer Trends
              </div>              
            </div>
            <div class="portlet-body portlet-empty">
               <div id="line" style="height:300px;"></div>
            </div>
          </div>
          <!-- END Portlet PORTLET-->
        </div>
      </div>
    <div class="margin-bottom-40 margin-top-20">
    <div class="portlet box s-protlet-theme">
      <div class="portlet-title">
        <div class="caption"></div>
        <div class="actions">
          <button data-target="#myModal" data-toggle="modal" id="btn-add" class="btn default"><i class="fa fa-print"></i>Print</button>         
        </div>
      </div>
      <div class="portlet-body">
      <table class="table table-bordered table-striped table-hover text-center nowrap-ingore" id="dt">
      <thead>
        <tr> 
		  <th> Developer </th>
          <th> Provider </th>
          <th> Product Offer </th>
          <th> ProviderAPP </th>
          <th> Free API Number </th>
          <th> Billing API Number </th>
          <th> Original Price (Yuan)</th>
          <th> Discount Amount (Yuan)</th>
          <th> Account Period (NATURAL month)</th>
          <th> Real Deduction Amount (Yuan)</th>
        </tr>
      </thead>
      <tbody>
      <tr><td>Dev</td><td>SuperMusic</td><td>SuperMusic</td><td>My Music</td><td>2000</td><td>200</td><td>20</td><td>4</td><td>2015/01</td><td>16</td></tr>
      <tr><td>Dev</td><td>Operator</td><td>SMS</td><td>SMS</td><td>3000</td><td>400</td><td>40</td><td>8</td><td>2015/02</td><td>32</td></tr>
      <tr><td>Dev</td><td>Operator</td><td>MMS</td><td>MMS</td><td>3000</td><td>500</td><td>50</td><td>10</td><td>2015/03</td><td>40</td></tr>
      <tr><td>Dev</td><td>Asiainfo</td><td>CSAutomaton</td><td>CSAutomaton</td><td>4000</td><td>900</td><td>90</td><td>18</td><td>2015/04</td><td>72</td></tr>
       </tbody>
      </table>
      </div>
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
<%@include file="../none_footer.jsp" %>
<!-- END COPYRIGHT --> 
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 

<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/echarts-2.2.6/echarts.js"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/resources/scripts/bill.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
  Bill.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
