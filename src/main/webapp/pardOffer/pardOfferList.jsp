<!DOCTYPE html>
<html lang="en">
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link
	href="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.css"
	rel="stylesheet" type="text/css" />
<link
	href="${APP_PATH}/resources/plugins/revolution_slider/css/rs-style.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/css/settings.css"
	media="screen" />
<link href="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.css"
	rel="stylesheet" />
<link href="${APP_PATH}/resources/css/pages/portfolio.css"
	rel="stylesheet" />

<!-- END PAGE LEVEL PLUGIN STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<form name="showPordOfferForm" method="post" id="showPordOfferForm"
		action="">
		<input type="hidden" id="prodOfferId" name="prodOffer.prodOfferId" />
	</form>
	<!-- BEGIN PAGE CONTAINER -->
	<div class="page-container">
		<!-- BEGIN BREADCRUMBS -->
		<div class="row breadcrumbs">
			<div class="container">
				<div class="col-md-4 col-sm-4">
					<h1>${local["eaap.op2.portal.pardOffer.offer"]}</h1>
				</div>
				<div class="col-md-8 col-sm-8">
					<ul class="pull-right breadcrumb">
						<li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a>
						</li>
						<li class="active">${local["eaap.op2.portal.pardOffer.offer"]}
						</li>
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
					<a class="btn s-btn-add" href="${APP_PATH}/pardOffer/toAdd.shtml"><i
						class="fa fa-plus"></i></a>
				</div>
				<!-- END ADD PRODUCT -->
			</div>
			<!-- BEGIN FILTER -->
			<div class="tab-style-3 margin-bottom-40 margin-top-20">
				<ul class="nav nav-tabs ">
					<li class="active"><a href="#tab_5_1" data-toggle="tab">
							${local["eaap.op2.portal.pardOffer.offer"]} </a></li>
				</ul>
				<div
					class="tab-content form-horizontal form-bordered form-row-stripped">
					<div class="tab-pane active clearfix" id="tab_5_1">
						<div class="filter-v1">
							<ul class="mix-filter">
								<li class="filter" data-filter="all">All</li>
								<li class="filter" data-filter=".category_1">${prodOfferStateMap['1000']}</li>
								<li class="filter" data-filter=".category_2">${prodOfferStateMap['1200']}</li>
								<li class="filter" data-filter=".category_3">${prodOfferStateMap['1299']}</li>
								<li class="filter" data-filter=".category_4">${prodOfferStateMap['1300']}</li>
								<div class="input-group input-large pull-right">
									<input type="text" class="form-control"
										placeholder='${local["eaap.op2.portal.pardOffer.offerName"]}'
										id="prodOfferName"> <span class="input-group-btn">
										<button type="submit" class="btn theme-btn">
											<i class="fa fa-search search-btn show-search-icon"></i>
										</button>
									</span>
								</div>
							</ul>
							<div class="row mix-grid thumbnails">
								<div class="col-md-12">
									<img src="${APP_PATH}/resources/img/input-spinner.gif" alt="">
								</div>
								<p class="text-center s-warning" style="display: none">
									<i class="fa fa-info-circle"></i> None
								</p>
							</div>
						</div>
					</div>
				</div>

				<!-- END FILTER -->
			</div>
			<!-- END CONTENT -->
		</div>
	</div>
	<!-- BEGIN COPYRIGHT -->
	<jsp:include page="/footer.jsp" />
	<!-- END COPYRIGHT -->
	<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
	<script type="text/javascript"
		src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
	<script src="${APP_PATH}/resources/plugins/jquery.mixitup.min.js"></script>
	<script src="${APP_PATH}/resources/scripts/app.js"></script>
	<script src="${APP_PATH}/pardOffer/pardOfferList.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
			$("#myCount").html('${local["eaap.op2.portal.pardOffer.offer"]}');
			PardOfferList.init();
		});
	</script>
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>

