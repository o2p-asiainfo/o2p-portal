<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="header.jsp" %>
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed modifyBg">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs" style="background: #0275b3;">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1 style="color: #fff;">${local["eaap.op2.portal.login.toLogin"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="../default.shtml"  target="_parent" style="color: #fff;">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active" style="color: #fff;">${local["eaap.op2.portal.login.toLogin"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 mt30">
    <div class="row">
      <div class="col-md-7 loginLeftBgLocal"></div>
      <div class="col-md-5 ">
        <form class="login-form" method="post">
           <input type="hidden"  id="isNeedAppHub"  name="isNeedAppHub"  value="${isNeedAppHub}"/>
          <h2>${local["eaap.op2.portal.login.loginToYourAccount"]}</h2>
          <div class="form-group">
            <div class="input-group"> <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
              <input id="userName" type="text" class="form-control" placeholder='${local["eaap.op2.portal.login.loginUserName"]}' name="username"  maxlength="30"  value="">
            </div>
          </div>
          <div class="form-group">
            <div class="input-group"> <span class="input-group-addon"><i class="fa fa-lock"></i></span>
              <input id="userPwd" type="password" class="form-control" placeholder='${local["eaap.op2.portal.login.loginPassword"]}' name="password"  autocomplete="off"  maxlength="16"  value=""/>
            </div>
          </div>
          <div class="form-group">
            <div class="input-group"> <span class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
              <input id="rand" type="text" class="form-control "  style="width:300px;" placeholder='${local["eaap.op2.portal.login.verification"]}' name="verification"  maxlength="4"  value="">
                 <a href="#" onClick="refreshimg()" ><img  src="${APP_PATH}/authImg" id="authImg" width="90" height="32"/></a>
               </div>
          </div>   
          <div class="form-group">
              <input type="checkbox" id="agreement" name="agreement"> 
              ${local["eaap.op2.portal.reg.regReadOne"]} <a href="javascript:;" data-target="#myModal" data-toggle="modal">${local["eaap.op2.portal.reg.regReadTwo"]}</a>
          </div>
          <div class="row">
            <div class="col-md-11 col-sm-11">
              <button type="submit" class="btn theme-btn pull-left" id="btnlogin" style="width:150px;">${local["eaap.op2.portal.login.toLogin"]}</button>
            </div>
          </div>
          <hr style="margin:15px 0 0 !important;">
          <div class="forget-password">
            <h4>${local["eaap.op2.portal.login.forgotPassword"]}</h4>
            <p> ${local["eaap.op2.portal.login.forgotPwdOne"]} <a id="forget-password" href="${APP_PATH}/forgotPwd/forgot.shtml?tid=${tid}"> ${local["eaap.op2.portal.login.forgotPwdTwo"]} </a> ${local["eaap.op2.portal.login.forgotPwdThree"]} </p>
          </div>
          <div class="create-account">
            <p> ${local["eaap.op2.portal.login.donotHaveAccount"]}&nbsp; <a id="register-btn" href="${APP_PATH}/org/reg.shtml"> ${local["eaap.op2.portal.login.createAnAccount"]} </a> </p>
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
<%@include file="footer.jsp" %>
<!-- END COPYRIGHT --> 

<!-- MYModal -->
<c:choose>
	<c:when test="${language==null || language=='en'}">
		<%@include file="./org/treaty_en.jsp" %>
	</c:when>
	<c:otherwise>
		<%@include file="./org/treaty_zh.jsp" %>
	</c:otherwise>
</c:choose>
<!-- /MYModal -->
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" ></script>
<c:if test="${language=='zh'}">
<script type="text/javascript" src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.zh.js" ></script>
</c:if>
<script src="${APP_PATH}/resources/scripts/login.js"></script>
<script src="${APP_PATH}/resources/scripts/sha256.js" type="text/javascript"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  Login.init();
});
 

var refreshimg = function(){
	$("#authImg").attr("src",'${APP_PATH}/authImg?now=' + new Date()); 
};


	var doLogin = function() {
		var orgUserName = $("#userName").val();
		var orgPwd = $("#userPwd").val();
		var rand = $("#rand").val();
		var date = new Date();
		
		var passwordValue;
		var isNeedAppHub=$("#isNeedAppHub").val();
		if(isNeedAppHub=='true'){
			passwordValue=$("#userPwd").val();
		}else{
			passwordValue=hex_sha256(orgPwd);
		}
		
		var offect = date.getTimezoneOffset();
		$.ajax({
			async : true,
			type : "POST",
			url : "${APP_PATH}/org/login.shtml?tid=${tid}",
			data : {
				orgUserName : orgUserName,
				orgPwd : passwordValue,
				rand : rand,
				timeOffset : offect
			},
			dataType : "json",
			beforeSend : function() {
				$("#btnlogin").attr("disabled", true);
			},
			complete : function() {
				$("#btnlogin").attr("disabled", false);
			},
			success : function(data) {
				if (data) {
					if (data.code == "0000") {
						toastr.success(data.desc);
						var url = "${APP_PATH}/default.shtml";
						window.parent.location.href = url;
					} else {
						toastr.warning(data.desc);
						refreshimg();
					}
				}
			}
		});
	};
</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
