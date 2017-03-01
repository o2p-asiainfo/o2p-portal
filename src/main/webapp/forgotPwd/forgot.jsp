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
        <form class="forgot-form" method="post">
          <h2>${local["eaap.op2.portal.forgotPwd.forgotPassword"]} ?</h2>
          <div class="form-group">
            <p>${local["eaap.op2.portal.forgotPwd.enterYourName"]}. </p>
            <div class="input-group"> <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
              <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.forgotPwd.userName"]}' name="userName" id="userName">
            </div>
          </div>        
          <div class="form-group">
            <div class="input-group"> <span class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
              <input type="text" class="form-control input-medium" placeholder='${local["eaap.op2.portal.forgotPwd.verification"]}' name="verification" id="rand">
              <a href="#" onClick="refreshimg()" ><img  src="${APP_PATH}/authImg" id="authImg" width="90" height="32"/></a>
            </div>
          </div>
          <div class="row" style="padding-top: 20px;">
            <div class="col-md-6 col-sm-6 col-md-offset-6">
              <button type="submit" id="btnSubmit" class="btn theme-btn pull-right">${local["eaap.op2.portal.forgotPwd.submit"]}</button>
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
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<!-- END COPYRIGHT -->
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/forgot.js"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  Forgot.init();
  myInit();
});

var myInit = function(){
	$("#btnSubmit").click(function() {
		doForgot();
	});
};

var doForgot = function(){
	var userName = $("#userName").val();
	var rand = $("#rand").val(); 
	if(userName && rand){
		$.ajax({
			  async : true,
			  type : "POST",
			  url :  "${APP_PATH}/forgotPwd/sentResetMail.shtml?tid=${tid}",  
			  data : {
				  userName : userName,
				  rand : rand
			  },
			  dataType : "json",
			  beforeSend: function(){
				  $("#btnSubmit").attr("disabled",true); 
			  },
			  complete : function(){
				  $("#btnSubmit").attr("disabled",false);  
			  },
			  success : function(data) {
				 if(data){
					 if (data.code == "0000") {
						var url = "${APP_PATH}/forgotPwd/sentMail.shtml";
						window.location.href = url;
					} else if(data.HUB_RETURN_CODE=="9999"){
						toastr.warning(data.HUB_RETURN_DESC);
						refreshimg();
					}else {
						toastr.warning(data.desc);
						refreshimg();
					}
				 } 
			  }
		});
	}
};

var refreshimg = function(){
	$("#authImg").attr("src",'${APP_PATH}/authImg?now=' + new Date()); 
};

</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>
