<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>


<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css"
	rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/pages/portfolio.css"
	rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->


<link rel="shortcut icon" href="favicon.ico" />
<%@include file="../header.jsp"%>
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
					<h1>${local["eaap.op2.portal.doc.mySystem"]}</h1>
				</div>
				<div class="col-md-8 col-sm-8">
					<ul class="pull-right breadcrumb">
						<li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a></li>
						<li class="active">${local["eaap.op2.portal.doc.mySystem"]}</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- END BREADCRUMBS -->
		<!-- BEGIN CONTAINER -->
		<div class="container margin-bottom-40 margin-top-20">
			<div class="s-border clearfix margin-bottom-40">
					<%@include file="../userAvatar.jsp"%>
				<!-- END USER INFO -->
				<!-- BEGIN ADD PRODUCT -->
				<div class="s-add-product pull-right">
					<a class="btn s-btn-add" href="../provider/createSystem.shtml"><i
						class="fa fa-plus"></i></a>
				</div>
				<!-- END ADD PRODUCT -->
			</div>
			<!-- BEGIN FILTER -->
			<div class="filter-v1">
				<ul class="mix-filter">
					<li class="filter" data-filter="all" id="all">All</li>
					<div class="input-group input-large pull-right">
						<input type="text" class="form-control" id="seachValue"
							placeholder="My System Name"> <span
							class="input-group-btn">
							<button type="submit" class="btn theme-btn" id="providerSeach">
								<i class="fa fa-search search-btn show-search-icon"></i>
							</button>
						</span>
					</div>
				</ul>
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
	</div>
	</div>
	</div>
	<!-- END CONTAINER -->
	</div>
	<!-- END PAGE CONTAINER -->
	<!-- BEGIN COPYRIGHT -->
	<%@include file="../footer.jsp"%>
	<!-- END COPYRIGHT -->
	<!-- Load javascripts at bottom, this will reduce page load time -->
	<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) -->
	<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]-->
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
	<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js"
		type="text/javascript"></script>
	<script src="${APP_PATH}/resources/plugins/jquery.mixitup.min.js"></script>
	<script src="${APP_PATH}/resources/scripts/app.js"></script>
	<script src="${APP_PATH}/resources/scripts/mySystem.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
			$("#myCount").html('${local["eaap.op2.portal.doc.mySystem"]}');
			MyApp.init();
		});
	</script>
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>

