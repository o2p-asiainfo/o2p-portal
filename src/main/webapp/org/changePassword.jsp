<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="../header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
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
        <h1>${local["eaap.op2.portal.reg.infoChangPwd"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a></li>
          <li class="active"><a href="${APP_PATH}/org/userInfo.shtml">${local["eaap.op2.portal.reg.infoUserInfo"]}</a></li>
          <li class="active">${local["eaap.op2.portal.reg.infoChangPwd"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTENT -->
  <div class="container margin-bottom-40 margin-top-20">    
    <div class="s-sidebar"> 
	    <c:choose>
	      <c:when test="${not empty userBean.fileIdString}">
	         <img id="userImg" src="${APP_PATH}/fileShare/readImg.shtml?sFileId=${userBean.fileIdString}" alt="" style="width:250px;height:166px;"/>
	      </c:when>
	      <c:otherwise>
	         <img id="userImg" src="${APP_PATH}/resources/img/pics/250x250.jpg" width="250" height="166" alt="">
	      </c:otherwise>
	    </c:choose> 
      <a href="#"   data-height="166" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal" class="s-pic-edit changeImg">${local["eaap.op2.portal.reg.infoEdit"]}</a>
      <button class="btn btn-block theme-btn"  id="basicInfoBut"> ${local["eaap.op2.portal.reg.infoBasicInfo"]}</button>
    </div>
    <!-- BEGIN -->
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">${local["eaap.op2.portal.reg.infoChangPwd"]}</h3>
          </div>
          <div class="panel-body" style="min-height:500px;">
            <form class="form-horizontal form-bordered form-row-stripped" role="form" id="form1">
              <input type="hidden"  id="isNeedAppHub"  name="isNeedAppHub"  value="${isNeedAppHub}"/>
              <div class="form-body">
                <div style="display: none;">
                   <p id="objId"></p>
                   <p id="objIdType"></p>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.oldPwd"]}:</label> 
                  <div class="col-md-6">
                    <div class="input-icon"> <i class="fa fa-lock"></i>
                      <input type="password" name="oldPassword" id="oldPassword" placeholder="" class="form-control" autocomplete="off" maxlength="16"/>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.newPwd"]}:</label>
                  <div class="col-md-6">
                    <div class="input-icon"> <i class="fa fa-unlock"></i>
                      <input type="password" name="newPassword" id="newPassword" placeholder="" class="form-control" autocomplete="off" maxlength="16"/>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.repeat"]}: </label>
                  <div class="col-md-6">
                    <div class="input-icon"> <i class="fa fa-repeat"></i>
                      <input type="password" name="repeatPassword" id="rePassword" placeholder="" class="form-control" autocomplete="off" maxlength="16"/>
                    </div>
                  </div>
                </div>
                <div class="form-actions fluid">
                <div class="col-md-offset-4">
                  <button type="submit" class="btn theme-btn" id="updateBtn" value=''>${local["eaap.op2.portal.reg.submit"]}</button> 
                  <button type="button" class="btn default" id="cancelBtn" value=''>${local["eaap.op2.portal.reg.cancel"]}</button>
                </div>
              </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- END --> 
  </div>
  <!-- END CONTENT --> 
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
<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/sha256.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/changePassword.js"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  ChangePassword.init();
  myInit();
});

var myInit = function(){
	$("#objId").val('${userBean.orgId}');  
	$("#objIdType").val("orgfilSFileId"); 
	$("#cancelBtn").click(function() {
		var url = "${APP_PATH}/index.shtml";
		window.location.href = url;
	});
	
	$("#basicInfoBut").click(function() {
		var url = "${APP_PATH}/org/userInfo.shtml";
		window.location.href = url;
	});
};

var doChangePwd = function(){
	var oldPwd;
	var newPwd;
	
	var isNeedAppHub=$("#isNeedAppHub").val();
	if(isNeedAppHub=='true'){
		oldPwd= $("#oldPassword").val();
		newPwd= $("#newPassword").val();
	}else{
		oldPwd= $("#oldPassword").val();
		newPwd= $("#newPassword").val();
		oldPwd= hex_sha256(oldPwd);
		newPwd= hex_sha256(newPwd);
	}
	
	if(oldPwd != newPwd){
		$.ajax({
			  async : false,
			  type : "POST",
			  url :  "${APP_PATH}/org/doChangePwd.shtml",  
			  data : {
				  oldPwd : oldPwd,
				  newPwd : newPwd,
			  },
			  dataType : "json",
			  success : function(data) {
				 if(data){
					 if (data.code ==RESULT_OK&& data.HUB_RETURN_CODE==RESULT_OK) {
						 toastr.success(data.HUB_RETURN_DESC);
						 var url = "${APP_PATH}/org/logout.shtml";
						 window.parent.location.href = url;
					}else if(data.HUB_RETURN_CODE=="9999"){
						toastr.warning(data.HUB_RETURN_DESC); 
					}else  if (data.code == RESULT_OK) {
						toastr.success(data.desc);
						var url = "${APP_PATH}/org/logout.shtml";
						window.parent.location.href = url;
					}else if(data.code == "9999"){
						toastr.warning(data.desc); 
					}
				 } 
			  }
		});
	}else{
		toastr.warning($.i18n.prop('eaap.op2.portal.message.pwdUnsame'));
	}
};

function uploadCallback(data){
	 var imgId = data;
	 if(imgId){
		 $.ajax({
		 	type: "POST",
		 	async: false,
		 	url: "${APP_PATH}/org/updateOrg.shtml", 
		 	dataType:'json',
		 	data:{
		 		sFileId:imgId
		 	},
		 	success:function(data){ 
		 		if (data.code == RESULT_OK) {
		 			$('#userImg').attr('src',APP_PATH+'/fileShare/readImg.shtml?sFileId='+imgId);
		 		}else{
		 			toastr.warning(); 
		 		}
		    }
		});
	}
}


</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
