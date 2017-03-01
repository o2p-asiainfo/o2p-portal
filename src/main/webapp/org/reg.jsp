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
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs" style="background: #0275b3;">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1 style="color: #fff;">${local["eaap.op2.portal.index.signUp"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml?tid=${tid}" style="color: #fff;">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li class="active" style="color: #fff;">${local["eaap.op2.portal.index.signUp"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 mt30">
    <div class="row">
      <div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 login-signup-page">
        <form class="login-form" method="post">
        <input type="hidden"  id="isNeedAppHub"  name="isNeedAppHub"  value="${isNeedAppHub}"/>
        <input type="hidden"  id="o2pCloudFlag"  name="o2pCloudFlag"  value="${o2pCloudFlag}"/>
          <h2>${local["eaap.op2.portal.index.signUp"]}</h2>
          <div class="step"> 
            <p> ${local["eaap.op2.portal.reg.regEnterInfo"]}: </p>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.userName"]}' name="username" id="username"  maxlength="30">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input type="password" class="form-control" placeholder='${local["eaap.op2.portal.reg.regPassword"]}' name="password" id="password" autocomplete="off"  maxlength="16"/>
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-repeat"></i></span>
                <input type="password" class="form-control" placeholder='${local["eaap.op2.portal.reg.reTypePwd"]}' name="rePassword" id="rePassword" autocomplete="off" maxlength="16"/>
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.regEmail"]}' name="email"  id="email" maxlength="255">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.regSubEmail"]}' name="subEmail"  id="subEmail" maxlength="255">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
                <input type="text" name="country" style="opacity:0; position:absolute; top:-100000px;" id="country" value="">
                 <select id="country-list" name="country" class="js-example-basic-single" data-size="6" style="width:400px">
		            <c:forEach var="obj" items="${provinceList}">
	          		   <option value="${obj.PROVINCEID}">${obj.PROVINCENAME}</option>
	          	    </c:forEach>
                 </select>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.address"]}' name="address" id="address">
              </div>
            </div> 
            <p> ${local["eaap.op2.portal.reg.regEnterAuthInfo"]}: </p> 
            <div class="form-group">
              <div class="radio-list">
                <label class="radio-inline">
                  <input type="radio" name="type2" value="personal" checked="">
                  ${local["eaap.op2.portal.reg.regPersonal"]} </label>
                <label class="radio-inline">
                  <input type="radio" name="type2" value="enterprise" checked="">
                  ${local["eaap.op2.portal.reg.regcompany"]} </label>
              </div>
            </div>
      
            <div id="personalType" style="display:none">
              <div class="form-group"> 
                <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-font"></i></span>
                  <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.regFirstName"]}' name="firstname" id="firstName"  maxlength="30">
                </div>
              </div>
              <div class="form-group"> 
                <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-font"></i></span>
                  <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.regLastName"]}' name="lastname" id="lastName"  maxlength="30">
                </div>
              </div>
              <c:if test="${o2pCloudFlag!='cloud'}">
                  <div class="form-group">
		              <div class="input-group"  > <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-check-square-o"></i></span>
		                <select name="card" id="cardType" class="form-control">
		                </select>
		              </div>
		           </div>
		            <div class="form-group"  >
		              <div class="input-group"><font color="FF0000" class="fix">*</font><span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
		                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.regPapersNUm"]}' name="idNumber" id="idNumber">
		              </div>
		            </div>
		            
		           <div class="form-group">
						  <div data-date-format="yyyy-mm-dd"  class="input-group input-medium date-picker input-daterange" data-error-container="#error-container"><font color="FF0000" class="fix">*</font><span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						   <input type="text"  id="indenEffDate" name="indenEffDate"  placeholder='${local["eaap.op2.portal.reg.indenEffDate"]}'  class="form-control od"   style="width:177px !important;"  value="" />
						   <span class="input-group-addon"> To </span>
						   <input type="text"  id="indenExpDate" name="indenExpDate"   placeholder='${local["eaap.op2.portal.reg.indenExpDate"]}'  class="form-control od"   style="width:177px !important;" value="" />
						 </div>
			        </div>
			        <div class="form-group"  >
		              <div class="input-group"><span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
		                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.indenAddress"]}' name="indenAddress" id="indenAddress">
		              </div>
		            </div>
			        
              </c:if>
           
          </div>
            <div id="enterpriseType" style="display:none">
               <c:if test="${o2pCloudFlag=='cloud'}">
                  <div class="form-group">
		              <div class="radio-list">
		                <label class="radio-inline">
		                  <input type="radio" name="partnerType" value="2" checked="">
		                  ${local["eaap.op2.portal.reg.partnerTypeForOperator"]} </label>
		                <label class="radio-inline" style="margin-left:18px">
		                  <input type="radio" name="partnerType" value="1" checked="">
		                  ${local["eaap.op2.portal.reg.partnerTypeForNormalPartner"]} </label>
		              </div>
           		   </div>
           		</c:if>
           		
              <div class="form-group"> 
                <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-font"></i></span>
                  <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.enterpriseName"]}' name="enterprisename" id="enterpriseName"  maxlength="60">
                </div>
              </div>
            
             <c:if test="${o2pCloudFlag!='cloud'}">
	            <div class="form-group"> 
	                <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-font"></i></span>
	                  <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.legalName"]}' name="legalName" id="legalName"  maxlength="60">
	                </div>
	             </div>
             </c:if>
            </div>
          
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font> <span class="input-group-addon"><i class="fa fa-mobile-phone"></i></span>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.phone"]}' name="phone" id="phone"   maxlength="20">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <span class="input-group-addon"><i class="fa fa-mobile-phone"></i></span>
                <input type="text" class="form-control" placeholder='${local["eaap.op2.portal.reg.subPhone"]}' name="subPhone" id="subPhone"   maxlength="20">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"><span class="input-group-addon"><i class="fa fa-pencil-square-o"></i></span>
                <textarea id="introduction" rows="3" class="form-control" value="Introduction" name="introduction" placeholder='${local["eaap.op2.portal.reg.introduction"]}' id="introduction"  maxlength="500"></textarea>
              </div>
            </div>
            <div class="form-group">
              <div class="input-group"> <font color="FF0000" class="fix">*</font><span class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
                <input type="text" class="form-control input-medium" placeholder="Verification" name="verification" id="verifyCode"  value=""  maxlength="4">
                <a href="#" onClick="refreshimg()" ><img  src="${APP_PATH}/authImg" id="authImg" width="90" height="32"/></a>
               </div>
            </div>
            <div class="form-group">
              <input type="checkbox" id="agreement" name="agreement"> 
              ${local["eaap.op2.portal.reg.regReadOne"]} <a href="javascript:;" data-target="#myModal" data-toggle="modal">${local["eaap.op2.portal.reg.regReadTwo"]}</a> </div>
            <div class="row">
              <div class="col-md-6 col-sm-6">
              </div>
              <div class="col-md-6 col-sm-6">
                <button type="submit" class="btn theme-btn pull-right" id="signUp"  onclick="saveOrg()">Sign Up <i class="m-icon-swapright m-icon-white"></i></button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- END CONTAINER --> 
</div>
</div>
<!-- END CONTAINER -->
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.extend.js" type="text/javascript"></script>
<script src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js" type="text/javascript"></script> 

<c:if test="${language=='zh'}">
<script type="text/javascript" src="${home}/resources/plugins/jquery-validation/jquery.validate.zh.js" ></script>
</c:if>
<!-- END COPYRIGHT --> 
<script src="${APP_PATH}/resources/scripts/reg.js"></script> 
<script src="${APP_PATH}/resources/scripts/sha256.js" type="text/javascript"></script>
<script src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  Reg.init();
  myInit();
});

var myInit = function(){
	$(".js-example-basic-single").select2();
	myInitThread();
	$("input:radio[name=type2]").change(function() {
		changeCertType($(this).val()); 
	}); 
};

var myInitThread = function(){
	var orgTypeName = $('input:radio[name=type2]:checked').val();
	var orgType = '1';
	if(orgTypeName == 'personal'){
		orgType = '1';
	}else if(orgTypeName == 'enterprise'){
		orgType = '2';
	}
	var provinceId = $("#country-list").val();
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/org/getRegInitData.shtml?tid=${tid}",
		  data : {
			  orgType : orgType,
			  provinceId : provinceId
		  },
		  dataType : "json",
		  success : function(data) {
			  if(data){
				  var certTypeList = data.certTypeList;
				  if(certTypeList!=null){
					  $("#cardType").empty();
					  for(var i = 0; i < certTypeList.length; i++){
						  var option = $("<option>").val(certTypeList[i].certCode).text(certTypeList[i].certName);
						  $("#cardType").append(option);
					  }
				  }
			  }
		  }
	 });
};
 
var changeProvince = function(provinceId){
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/org/getCityList.shtml",
		  data : {
			  provinceId : provinceId
		  },
		  dataType : "json",
		  success : function(data) {
			  if(data){
				  var cityList = data.cityList;
				  $("#city-list").empty();
				  for(var i = 0; i < cityList.length; i++){
					  var option = $("<option>").val(cityList[i].CITYID).text(cityList[i].CITYNAME);
					  $("#city-list").append(option); 
				  }
			  }
		  }
	 });
};

var changeCertType = function(orgTypeName){
	var orgType = '1';
	if(orgTypeName == 'personal'){
		orgType = '1';
	}else if(orgTypeName == 'enterprise'){
		orgType = '2';
	}
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/org/getCertTypeList.shtml",
		  data : {
			  orgType : orgType
		  },
		  dataType : "json",
		  success : function(data) {
			  if(data){
				  var certTypeList = data.certTypeList;
				  if(certTypeList!=null){
					  $("#cardType").empty();
					  for(var i = 0; i < certTypeList.length; i++){
						  var option = $("<option>").val(certTypeList[i].cepCode).text(certTypeList[i].cepName);
						  $("#cardType").append(option);
					  }
				  }
			  }
		  }
	 });
};
 
var refreshimg = function(){
	$("#authImg").attr("src",'${APP_PATH}/authImg?now=' + new Date()); 
};

var saveOrg = function(){
	if(verifyOrg()){
		var password = $("#password").val();
		var userName = $("#username").val();
		var email = $("#email").val();
		var subEmail=$("#subEmail").val();
		var cityId = $("#country-list").val();
		var address = $("#address").val();
		var orgTypeName = $('input:radio[name=type2]:checked').val();
		var orgType = '1';
		//普通合作伙伴和运营商
		var partnerType="";
		var partnerTypeValue="";
		if(orgTypeName == 'personal'){
			orgType = '1';//org_type_code 
		}else if(orgTypeName == 'enterprise'){
			orgType = '2';
			partnerTypeValue=$('input:radio[name=partnerType]:checked').val();
			if(partnerTypeValue == '1'){
				partnerType = '1';//普通合作伙伴
			}else if(partnerTypeValue == '2'){
				partnerType = '2';//运营商
			}
		}
		
		var passwordValue;
		var isNeedAppHub=$("#isNeedAppHub").val();
		if(isNeedAppHub=='true'){
			passwordValue=$("#password").val();
		}else{
			passwordValue=hex_sha256(password);
		}
		
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val(); 
		var enterpriseName = $("#enterpriseName").val();
		var certType = $("#cardType").find("option:selected").val(); 
		var idNumber = $("#idNumber").val();
		var indenEffDate=$("#indenEffDate").val();
		var indenExpDate=$("#indenExpDate").val();
		var indenAddress=$("#indenAddress").val();
		var legalName=$("#legalName").val();
		var telephone = $("#phone").val();
		var subTelephone= $("#subPhone").val();
		var introduction = $("#introduction").val();
		var verifyCode = $("#verifyCode").val();

		$.ajax({
			  async : true,
			  type : "POST",
			  url :  "${APP_PATH}/org/saveOrg.shtml?tid=${tid}",  
			  data : {
				  password :passwordValue ,
				  userName : userName,
				  email : email,
				  subEmail:subEmail,
				  cityId : cityId,
				  address : address,
				  orgType : orgType,
				  partnerType:partnerType,
				  firstName : firstName,
				  lastName : lastName,
				  enterpriseName : enterpriseName,
				  idNumber : idNumber,
				  certType : certType,
				  indenEffDate:indenEffDate,
				  indenExpDate:indenExpDate,
				  indenAddress:indenAddress,
				  legalName:legalName,
				  telephone : telephone,
				  subTelephone:subTelephone,
				  introduction : introduction,
				  verifyCode : verifyCode
			  },
			  dataType : "json",
			  beforeSend: function(){
				  $("#signUp").attr("disabled",true); 
			  },
			  success : function(data) {
				 if(data){
					 if (data.code == "0000"&& data.HUB_RETURN_CODE=="0000") {
						 toastr.success(data.desc+" and "+data.HUB_RETURN_DESC);
						 var url = "${APP_PATH}/org/toLoginSuccess.shtml";
						 window.location.href = url;
					}else if(data.HUB_RETURN_CODE=="9999"){
						toastr.warning(data.HUB_RETURN_DESC); 
						refreshimg();
					}else if(data.code == "0000"){
						toastr.success(data.desc);
						var url = "${APP_PATH}/org/toLoginSuccess.shtml";
						 window.location.href = url;
					}else if(data.code == "9999"){
						toastr.warning(data.desc); 
						refreshimg();
					}else {
						//显示验证错误信息
						if(undefined != data.desc.orgUsername){
							toastr.error('${local["eaap.op2.portal.reg.userName"]} '+data.desc.orgUsername);
						}
						if(undefined != data.desc.name){
							if(orgType == '1'){
								toastr.error('${local["eaap.op2.portal.reg.enterpriseName"]} '+data.desc.name);
							}else{
								toastr.error('${local["eaap.op2.portal.reg.regFirstName"]} , ${local["eaap.op2.portal.reg.regFirstName"]} '+data.desc.name);
							}
						}
						if(undefined != data.desc.email){
							toastr.error('${local["eaap.op2.portal.reg.regEmail"]} '+data.desc.email);
						}
						
					}
				 } 
			  },
			  complete : function(){
				  $("#signUp").attr("disabled",false);  
			  }
		});
	}else {
		toastr.error('There are some information still need to fill out.');
	}

};

var verifyOrg = function(){
	var o2pCloudFlag=$("#o2pCloudFlag").val();
	var userName = $("#username").val();
	if(!userName){
		return false;
	}
	var password = $("#password").val();
	if(!password){
		return false;
	}
	var email = $("#email").val();
	if(!email){
		return false;
	}
	var orgTypeName = $('input:radio[name=type2]:checked').val();
	if(orgTypeName == 'personal'){
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val(); 
	
		if(!firstName){
			return false;
		}
		if(!lastName){
			return false;
		}
		
		if(o2pCloudFlag!='cloud'){
			var idNumber = $("#idNumber").val();
			var certType = $("#cardType").find("option:selected").val(); 
			var indenEffDate=$("#indenEffDate").val();
			var indenExpDate=$("#indenExpDate").val();
			
			if(!idNumber){
				return false;
			}
			if(!indenEffDate){
				return false;
			}
			if(!indenExpDate){
				return false;
			}
			if(!certType){
				return false;
			}
		}
	
	}else if(orgTypeName == 'enterprise'){
		var enterpriseName = $("#enterpriseName").val();
		if(!enterpriseName){
			return false;
		}
		
		if(o2pCloudFlag!='cloud'){
			var legalName=$("#legalName").val();
			if(!legalName){
				return false;
			}
		}
		
	}
	
	var phone = $("#phone").val();
	if(!phone){
		return false;
	}
	var verifyCode = $("#verifyCode").val();
	if(!verifyCode){
		return false;
	}
	if($("#agreement").attr("checked")!='checked'){
		return false;
	}
	return true;
};

function emailValidate() {  
	var email = $("#email").val();
    var bValidate = RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(email);  
    if(!bValidate) {
		toastr.error('${local["eaap.op2.portal.emailValidate"]}');
		//$("#email").focus();
    }
	return bValidate;
}

 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- MYModal -->
<c:choose>
	<c:when test="${language==null || language=='en'}">
		<%@include file="treaty_en.jsp" %>
	</c:when>
	<c:otherwise>
		<%@include file="treaty_zh.jsp" %>
	</c:otherwise>
</c:choose>
<!-- /MYModal -->
</html>
