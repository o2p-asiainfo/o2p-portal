<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/css/rs-style.css" media="screen">
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/css/settings.css" media="screen">
<link href="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<%@include file="header.jsp" %>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed" style="padding-top:75px !important;overflow-x:hidden;overflow-y:hidden; ">
<!-- BEGIN HEADER -->
<%@include file="menu.jsp" %>
<!-- END HEADER --> 

<!-- BEGIN PAGE CONTAINER -->
<iframe width="100%" frameborder="0" align="top"   id="mianframe" name="mianframe" height="100%" src="${APP_PATH}/index.shtml?tid=${tid}"></iframe>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<div class="copyright">
  <div class="container">
    <div class="row">
      <div class="col-md-12 text-center">
        <p> <span class="margin-right-10">Copyright &copy; ${tenantName} </span>  All Rights Reserved</p>
      </div>
    </div>
  </div>
</div>
<!-- END COPYRIGHT --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="footer.jsp" %>
<!-- END COPYRIGHT -->
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 

<script type="text/javascript">
 jQuery(document).ready(function() {
  function getViewPort() {
    var e = window,
      a = 'inner';
    if (!('innerWidth' in window)) {
      a = 'client';
      e = document.documentElement || document.body;
    }
    return {
      width: e[a + 'Width'],
      height: e[a + 'Height']
    }
  }
  function setIframeHeight() {
    $('#mianframe').attr('height', getViewPort().height - $('.header').outerHeight(true) - 37);
    $('.copyright:hidden').show();
  }
  setIframeHeight()
  window.onresize = setIframeHeight;
	
 });
 

 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>