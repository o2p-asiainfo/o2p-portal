<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>${local["eaap.op2.portal.index.SysName"]}</title>
<head>
	<%@include file="/header.jsp" %>
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
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container" style="margin-top:0!important;"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>Real-time Bill</h1>
      </div>
      <div class="col-md-8 col-sm-8 display-none">
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
      <form action="#">
      <div class="row">
          <div class="col-md-3 col-sm-4">
           
              <div class="input-group date-picker input-daterange" data-date="10/11/2012" data-date-format="mm/dd/yyyy">
                <input type="text" placeholder="Begin Date" class="form-control" name="from">
                <span class="input-group-addon"> To </span>
                <input type="text" placeholder="End Date" class="form-control" name="to">
          
            </div>
          </div>
          <div class="col-md-3 col-sm-4">
            
              <input type="text" placeholder="APP" class="form-control">
      
          </div>
          <div class="col-md-3 col-sm-4">
            
              <input type="text" placeholder="Product Offer" class="form-control">
        
          </div>
          <div class="col-md-3 col-sm-4">
            
              <button class="btn default">Search</button>
         
          </div>
        </div>
      </form>
    </div>
    <div class="margin-bottom-40 margin-top-20">
    <div class="portlet box s-protlet-theme">
      <div class="portlet-title">
        <div class="caption"> The current account balance:12.38Yuan </div>
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
      <tr><td>YouKu</td><td>Wimp</td><td>Music</td><td>My Music</td><td>2000</td><td>200</td><td>20</td><td>4</td><td>2015/01</td><td>16</td></tr>
      <tr><td>YouKu</td><td>Wimp</td><td>Music</td><td>My Music</td><td>3000</td><td>400</td><td>40</td><td>8</td><td>2015/02</td><td>32</td></tr>
      <tr><td>YouKu</td><td>Wimp</td><td>Music</td><td>My Music</td><td>3000</td><td>500</td><td>50</td><td>10</td><td>2015/03</td><td>40</td></tr>
      <tr><td>YouKu</td><td>Wimp</td><td>Music</td><td>My Music</td><td>4000</td><td>900</td><td>90</td><td>18</td><td>2015/04</td><td>72</td></tr>
      <tr><td>YouKu</td><td>Wimp</td><td>Music</td><td>My Music</td><td>5000</td><td>2000</td><td>200</td><td>50</td><td>2015/05</td><td>150</td></tr>
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
<!-- BEGIN COPYRIGHT -->
<jsp:include page="/footer.jsp"/>
<!-- END COPYRIGHT --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
	<script type="text/javascript" src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
	<script src="${APP_PATH}/resources/plugins/jquery.mixitup.min.js"></script> 
	<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
	<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script> 
	<script src="${APP_PATH}/resources/scripts/app.js"></script> 
	<script src="${APP_PATH}/mgr/realTimeBill.js"></script> 
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

