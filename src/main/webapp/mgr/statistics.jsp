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
<title>${local["eaap.op2.portal.index.SysName"]}</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link href="resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
 <link href="resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
 <link href="resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="resources/plugins/bootstrap-datepicker/datepicker.css" />
<link rel="stylesheet" type="text/css" href="resources/plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link rel="stylesheet" type="text/css" href="resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

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
        <h1>Statistics</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="index.jsp">Home</a> </li>
          <li class="active">Statistics</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="search-form-default">
      <form action="#" class="form-inline">
        <input type="checkbox" checked name="my-checkbox" class="toggle" />
        <div class="input-group mr20 ml20">
          <label for="">Date range: </label>
          <div class="input-group input-large date-picker input-daterange from">
            <input type="text" class="form-control" name="from" id="from" placeHolder="mm/dd/yyyy">
            <span class="input-group-addon"> To </span>
            <input type="text" class="form-control" name="to" placeHolder="mm/dd/yyyy">
          </div>
        </div>
        <div class="input-group mr20">
          <label for="">Application: </label>
          <select class="js-example-placeholder-single form-control mr20" placeholder="name" style="width:150px">
            <optgroup label="Pacific Time Zone">
            <option value="AL">--ALL--</option>
            <option value="CA">California</option>
            <option value="NV">Nevada</option>
            <option value="OR">Oregon</option>
            <option value="WA">Washington</option>
            </optgroup>
            <optgroup label="Mountain Time Zone">
            <option value="AZ">Arizona</option>
            <option value="CO">Colorado</option>
            <option value="ID">Idaho</option>
            <option value="MT">Montana</option>
            <option value="NE">Nebraska</option>
            <option value="NM">New Mexico</option>
            <option value="ND">North Dakota</option>
            <option value="UT">Utah</option>
            <option value="WY">Wyoming</option>
            </optgroup>
          </select>
        </div>
        <button class="btn theme-btn" type="button">Query</button>
      </form>
    </div>
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore" id="dt">
      <thead>
        <tr>
          <th> Daily </th>
          <th> Application Name</th>
          <th> Total Business Volume</th>
          <th> Successful Transaction Quantity</th>
          <th> Business Exception Quantity</th>
          <th> System Exception Quantity</th>
          <th> Average Response Time</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
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
    <script src="resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<script src="resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 
<script src="resources/plugins/bootstrap-switch/bootstrap-switch.min.js" type="text/javascript"></script> 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script type="text/javascript" src="resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script> 
<script type="text/javascript" src="resources/plugins/select2/select2.min.js"></script> 
<script src="resources/scripts/app.js"></script> 
<script src="resources/scripts/statistics.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
  Statistics.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
