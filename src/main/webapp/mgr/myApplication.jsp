<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css"
	rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/pages/portfolio.css"
	rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css"
	rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css"
	rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->


<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<jsp:include page="../header.jsp">
		<jsp:param value="MyCenter" name="navBarPageId" />
	</jsp:include>
	<!-- END HEADER -->

	<!-- BEGIN PAGE CONTAINER -->
	<div class="page-container">
		<!-- BEGIN BREADCRUMBS -->
		<div class="row breadcrumbs">
			<div class="container">
				<div class="col-md-4 col-sm-4">
					<h1>My Application</h1>
				</div>
				<div class="col-md-8 col-sm-8">
					<ul class="pull-right breadcrumb">
						<li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a></li>
						<li class="active">My Application</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- END BREADCRUMBS -->
		<!-- BEGIN CONTAINER -->
		<div class="container margin-bottom-40 margin-top-20">
			<div class="s-border clearfix margin-bottom-40">
				<!-- BEGIN USER AVATAR -->

				<!-- END USER AVATAR -->
				<!-- BEGIN USER INFO -->
					<%@include file="../userAvatar.jsp"%>
				<!-- END USER INFO -->
				<!-- BEGIN ADD PRODUCT -->
				<div class="s-add-product pull-right">
					<a class="btn s-btn-add"
						href="${APP_PATH}/mgr/manageDevMgrAdd.shtml?"><i
						class="fa fa-plus"></i></a>
				</div>
				<!-- END ADD PRODUCT -->
			</div>
			<!-- BEGIN FILTER -->
			<div class="filter-v1">
				<ul class="mix-filter">
					<li class="filter" data-filter="all" id="all">All</li>
					<li class="filter" data-filter=".category_1">Online</li>
					<li class="filter" data-filter=".category_2">Pending</li>
					<li class="filter" data-filter=".category_4">Offline</li>
					<div class="input-group input-large pull-right">
						<input type="text" class="form-control"
							placeholder="My Application Name" id="appName"> <span
							class="input-group-btn">
							<button type="submit" class="btn theme-btn" id="btn-app-search">
								<i class="fa fa-search search-btn show-search-icon"></i>
							</button>
						</span>
					</div>
				</ul>
				<form id="showAppForm" action="" method="post" name="showAppForm">
					<input id="appId" name="appId" value="" type="hidden" />
				</form>
				<div class="row mix-grid thumbnails">
					<div class="col-md-12">
						<img src="${APP_PATH}/resources/img/input-spinner.gif" alt="">
					</div>
				</div>
				<p class="text-center s-warning" style="display: none">
					<i class="fa fa-info-circle"></i> None
				</p>
			</div>
			<!-- END FILTER -->
		</div>
		<!-- END CONTENT -->

		<!-- END CONTAINER -->
	</div>
	<!-- END PAGE CONTAINER -->
	<!-- BEGIN COPYRIGHT -->
	<jsp:include page="/footer.jsp" />
	<!-- END COPYRIGHT -->
	<!-- Load javascripts at bottom, this will reduce page load time -->
	<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) -->
	<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]-->
	<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>
	<script
		src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/back-to-top.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js"
		type="text/javascript"></script>
	<script
		src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js"
		type="text/javascript"></script>
	<script
		src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
	<script
		src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
	<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/jquery.mixitup.min.js"></script>
	<script src="${APP_PATH}/resources/scripts/app.js"></script>
	<script src="${APP_PATH}/resources/scripts/myApp.js"></script>
	<script type="text/javascript">
var APP_PATH = "${APP_PATH}";
		jQuery(document).ready(function() {
			App.init();
			$("#myCount").html('My Application');
			MyApp.init();
		});
	</script>
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
