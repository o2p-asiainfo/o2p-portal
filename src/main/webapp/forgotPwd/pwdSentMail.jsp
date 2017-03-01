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
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.forgotPwd.forgotPassword"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.forgotPwd.forgotPassword"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 mt30">
    <div class="row">
      <div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 login-signup-page">
        <form class="forgot-form" style="width: 600px;vertical-align: middle;" method="post">
          <div class="form-group">
             <div class="col-md-4 col-sm-4">
             	<img width="90" height="90" src="${APP_PATH}/resources/img/sentMail.jpg">
             </div>
             <div style="display: none;">
             	<input type="hidden" id="userName" value='${forgot_pwd_userName}'></input>
             	<input type="hidden" id="email" value='${forgot_pwd_email}'></input>
             </div>
             <div>
             	<p><font size="4">${local["eaap.op2.portal.forgotPwd.forgotMail"]} <font color="black">${forgot_pwd_email}</font> ${local["eaap.op2.portal.forgotPwd.sentSuccess"]} </font></p>
             	<p><font size="4" >${local["eaap.op2.portal.forgotPwd.note"]} </font></p>
             	<p>${local["eaap.op2.portal.forgotPwd.noteMail"]}</p>
             	<p>${local["eaap.op2.portal.forgotPwd.noMail"]}<a id="resent"  target="_blank" class="fontC36cS12">${local["eaap.op2.portal.forgotPwd.resent"]}</a></p>
             </div>
          </div>               
        </form>
      </div>
    </div>
  </div>
  <!-- END CONTAINER --> 
</div>
</div>
</div>
<!-- END CONTAINER -->
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<!-- END COPYRIGHT -->
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  myInit();
});

var myInit = function(){
	$("#resent").click(function() {
		var userName = $("#userName").val();
		if(userName){
			$.ajax({
				  async : true,
				  type : "POST",
				  url :  "${APP_PATH}/forgotPwd/reSentMail.shtml",  
				  data : {
					  userName : userName
				  },
				  dataType : "json",
				  success : function(data) {
					 if(data){
						if (data.code == "0000") {
							toastr.success(data.desc); 
						} else {
							toastr.warning(data.desc);
						}
					 } 
				  }
			});
		}
	});
};


</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
