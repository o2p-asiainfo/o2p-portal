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
<script type="text/javascript">
var _enterpriseName ='${local["eaap.op2.portal.reg.enterpriseName"]}';
var _personalName ='${local["eaap.op2.portal.reg.infoName"]}';
</script>
<body class="page-header-fixed">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.reg.infoUserInfo"]}</h1> 
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li>${local["eaap.op2.portal.reg.infoUserInfo"]}</li>
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
      <button class="btn btn-block theme-btn"  id="submitToCheckBut">${local["eaap.op2.portal.devmgr.submitToCheck"]}</button>
      <button class="btn btn-block theme-btn"  id="historyBut">${local["eaap.op2.portal.portalMessage.history"] }</button>
      <button class="btn btn-block theme-btn"  id="infoChangPwd">${local["eaap.op2.portal.reg.infoChangPwd"]}</button>
      <c:if test="${switchUserRole == true}">
      	 <button class="btn btn-block theme-btn"  id="switchUserBut">${local["eaap.op2.portal.reg.switchUser"]}</button>
      </c:if>
    </div>
    
    <!-- BEGIN -->    
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">${local["eaap.op2.portal.reg.infoUserInfo"]}</h3>  
          </div>
          <div class="panel-body">
            <form class="form-horizontal form-bordered form-row-stripped" role="form">
             <input type="hidden"  id="o2pCloudFlag"  name="o2pCloudFlag"  value="${o2pCloudFlag}"/>
              <div class="form-body" id="userInfoDiv">
                <div style="display: none;">
                  <p id="objId"></p>
                  <p id="objIdType"></p>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.infoUserName"]}:</label>
                  <div class="col-md-6"><p class="form-control-static"  id="userName"></p></div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.regEmail"]}:</label>
                  <div class="col-md-6"> <a href="#" data-name="email" id="email"></a> </div>
                </div>
                 <div class="form-group">
                  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.reg.regSubEmail"]}:</label>
                  <div class="col-md-6"> <a href="#" data-name="subEmail" id="subEmail"></a> </div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> Country:</label>
                  <div class="col-md-6">
              		<input type="hidden"   id="city" value=""/>
					<a id="cityName" class="editable editable-click editable-open"   onclick="$('#citySelDiv').show();"   href="#" ></a>
					<div id="citySelDiv" class="popover editable-container editable-popup fade top in"   style="top: -140px; left:-100px; display:none;">
						<div class="arrow"></div>
						<h3 class="popover-title">Select a new  Country</h3>
						<div class="popover-content">
							<div>
							<div class="editableform-loading" style="display: none;"></div>
							<form class="form-inline editableform" style="">
								<div class="control-group form-group">
									<div>
										<div class="editable-input">
											<div class="editable-address">
												<label>
													<select   id="country-list" class="select2 form-control">
									                  <c:forEach var="obj" items="${provinceList}">
										          		<option value="${obj.PROVINCEID}">${obj.PROVINCENAME}</option>
										          	  </c:forEach>
									                </select>
												</label>
											</div>
											 
											<div class="editable-address">
											<!-- 
												<label>
									                <select  id="city-list" class="select2 form-control"> 
									                </select>
												</label>-->
											</div>
										</div>
										<div class="editable-buttons">
											<button class="btn btn-primary btn-sm editable-submit" type="button"  onclick="changeCity()">
												<i class="glyphicon glyphicon-ok"></i>
											</button>
											<button class="btn btn-default btn-sm editable-cancel" type="button"  onclick="$('#citySelDiv').hide();">
												<i class="glyphicon glyphicon-remove"></i>
											</button>
										</div>
									</div>
									<div class="editable-error-block help-block" style="display: none;"></div>
								</div>
							</form>
							</div>
						</div>
					</div>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-4 control-label" >${local["eaap.op2.portal.reg.address"]}:</label>
                  <div class="col-md-6"> <a href="#" data-name="address" id="address"></a> </div>
                </div>

              	<input type="hidden"   id="orgTypeCode" value="${orgTypeCode }"/>
              	<input type="hidden"  name="orgTypeList"  id="orgTypeList"  value = "[{value:1,text:'${local["eaap.op2.portal.reg.regPersonal"]}'},{value:2,text:'${local["eaap.op2.portal.reg.regcompany"]}'}]">
                <div class="form-group">
                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.userType"]}:</label>
                  <div class="col-md-6"> <a href="#" data-name="orgType"  id="orgType"></a></div>
                </div>
                 <c:if test="${o2pCloudFlag=='cloud'}">
					<input type="hidden"   id="partnerType" value=""/>
	  			    <div class="form-group">
	                  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.reg.partnerType"]}:</label>
	                  <div class="col-md-6"> <p class="form-control-static"  id="partnerTypeName"></p></div>
	                </div>
                </c:if>
                <div class="form-group">
                  <label class="col-md-4 control-label"  id="nameType"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.infoName"]}:</label>
                  <div class="col-md-6"> <a href="#" data-name="name" id="name"></a> </div>
                </div>
              	<input type="hidden"   id="certTypeCode" value=""/>
              	<input type="hidden"  name="certTypeList"  id="certTypeList"  value = "">
      			<c:if test="${o2pCloudFlag!='cloud'}">
      					<div class="form-group"   id="compDiv">
		                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.legalName"]}:</label> 
		                  <div class="col-md-6"> <a href="#" data-name="legalName"  id="legalName"></a></div>
	                   </div>
	                 <div id="individualDiv">
                       <div class="form-group"  >
		                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.regPapersType"]}:</label> 
		                  <div class="col-md-6"  id="certTypeContent"> 
		                          <a href="#" data-name="certType"  id="certType"  class="editable editable-click editable-open"   onclick="$('#certTypeSelDiv').show();" ></a>
				                 <div id="certTypeSelDiv" class="popover editable-container editable-popup fade top in"   style="top: -100px; left:-100px; display:none;">
									<div class="arrow"></div>
									<h3 class="popover-title">Select an Identification Type</h3>
									<div class="popover-content">
										<div>
											<form class="form-inline editableform" style="">
												<div class="control-group form-group">
													<div>
														<div class="editable-input">
															<div class="editable-address">
																<label>
																	<select   id="certType-list" class="select2 form-control">
													                  <c:forEach var="obj" items="${certTypeList}">
														          		<option value="${obj.cepCode}">${obj.cepName}</option>
														          	  </c:forEach>
													                </select>
																</label>
															</div>
														</div>
														<div class="editable-buttons">
															<button class="btn btn-primary btn-sm editable-submit" type="button"  onclick="changeCertTypeValue()">
																<i class="glyphicon glyphicon-ok"></i>
															</button>
															<button class="btn btn-default btn-sm editable-cancel" type="button"  onclick="$('#certTypeSelDiv').hide();">
																<i class="glyphicon glyphicon-remove"></i>
															</button>
														</div>
													</div>
												</div>
											</form>
										</div>
									</div>
							</div>
					</div>
					<div class="col-md-6"  id="certTypeContentBak"> 
					   <a href="#" data-name="certType"  id="certType" ></a>
					</div>
	           </div>
                
		                <div class="form-group"  >
		                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.regPapersNUm"]}:</label>
		                  <div class="col-md-6"><a href="#" data-name="certNum" id="certNum"></a></div>
		                </div>
		                
		                <div class="form-group"  >
		                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.indenValidityDate"]}:</label> 
		                  <div class="col-md-6 "> 
		                     <div data-date-format="yyyy-mm-dd"  class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
		                       <input type="text"  name="indenEffDate"  id="indenEffDate"   class="editable editable-click editable-open" ></a>
		                         <span class="input-group-addon"  style="width:3px"> To </span>
		                        <input type="text"  name="indenExpDate"  id="indenExpDate"  class="editable editable-click editable-open" ></a>
		                     </div>
		                  </div>
	                   </div>
                
		                <div class="form-group"  >
		                  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.reg.indenAddress"]}:</label>
		                  <div class="col-md-6"><a href="#" data-name="indenAddress" id="indenAddress"></a></div>
		                </div>
		              </div>
                </c:if>
                <div class="form-group">
                  <label class="col-md-4 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.phone"]}:</label>
                  <div class="col-md-6"><a href="#" data-name="mobilePhone" id="phone"></a></div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label"> ${local["eaap.op2.portal.reg.subPhone"]}:</label>
                  <div class="col-md-6"><a href="#" data-name="subPhone" id="subPhone"></a></div>
                </div>
                
           
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.introduction"]}:</label>
                  <div class="col-md-6"><a href="#" data-name="introduction" id="introduction"></a></div>
                </div>
                <div class="form-group">
                  <label class="col-md-4 control-label">${local["eaap.op2.portal.reg.state"]}:</label>
                  <div class="col-md-6" ><p class="form-control-static"  id="stateName"></p></div>
                </div>
                <c:if test="${userBean.state=='C' }">
                	<div class="form-group">
		              <label class="col-md-4 control-label"> Feedback Message:</label>
		              <div class="col-md-6" style="line-height:35px;color:blue;">
		              	  <p class="form-control-static">${checkMsg }</p>	
		              </div>
		            </div>
                </c:if>
              </div>
              <div class="form-actions fluid">
                <div class="col-md-offset-4">
                  <input type="button" class="btn theme-btn" id="updateBtn" value='Save'></input> 
                  <input type="button" class="btn default" id="cancelBtn" value='${local["eaap.op2.portal.reg.cancel"]}'></input>
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
<!-- END CONTAINER -->
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<!-- END COPYRIGHT --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 

<script src="${APP_PATH}/resources/scripts/userInfo.js"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
  UserInfo.init();
  myInit();
});

var myInit = function(){
	
	var orgTypeCode=$("#orgTypeCode").val();
	if(orgTypeCode=='2'){
		$("#compDiv").show();
		$("#individualDiv").hide();
	}else{
		$("#individualDiv").show();
		$("#compDiv").hide();
	}
	$("#updateBtn").click(function() {
		saveUserInfo(); 
	});
	$("#cancelBtn").click(function() {
		var url = "${APP_PATH}/index.shtml";
		window.location.href = url;
	});
	
	$("#country-list").change(function(){
		//changeProvince($(this).val());
	});
		
	$("#submitToCheckBut").click(function() {
		submitToCheck(); 
	});
	$("#historyBut").click(function() {
		var url = "${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=ORGTODO&lookFalg=&query=${userBean.orgId}";
		window.location.href = url;
	});
	$("#infoChangPwd").click(function() {
		var url ="${APP_PATH}/org/changePwd.shtml";
		window.location.href = url;
	});
	$("#switchUserBut").click(function() {
		var url ="${APP_PATH}/org/toSwitchUserView.shtml";
		window.location.href = url;
	});
		
	$.ajax({
		  async : true,
		  type : "POST",
		  url :  "${APP_PATH}/org/qryUserInfo.shtml",
		  data : {},
		  dataType : "json",
		  success : function(data) {
			 if(data && data.code == RESULT_OK){
				 setUserInfo(data);
			 }
		  }
	});
};

var _orgTypeCode="";
var orgPartnerType="";
var setUserInfo = function(data){
	var userInfo = data.org;
	$("#objId").val(userInfo.orgId);
	$("#objIdType").val("orgfilSFileId"); 
	$("#userName").html(userInfo.orgUsername);
	$("#email").html(userInfo.email!=""?userInfo.email:"&nbsp;&nbsp;&nbsp;");
	$("#subEmail").html(userInfo.subEmail!=""?userInfo.subEmail:"&nbsp;&nbsp;&nbsp;");
	$("#certNum").html(userInfo.certNumber!=""?userInfo.certNumber:"&nbsp;&nbsp;&nbsp;");
	$("#city").val(userInfo.areaId); 
	$("#cityName").html(data.cityName!=""?data.cityName:"&nbsp;&nbsp;&nbsp;");
	$("#address").html(isEmpty(userInfo.address) == false ? userInfo.address:"&nbsp;&nbsp;&nbsp;");
	$("#name").html(userInfo.name!=""?userInfo.name:"&nbsp;&nbsp;&nbsp;");
	$("#phone").html(userInfo.telephone!=""?userInfo.telephone:"&nbsp;&nbsp;&nbsp;");
	$("#subPhone").html(userInfo.subTelephone!=""?userInfo.subTelephone:"&nbsp;&nbsp;&nbsp;");
	$("#introduction").html(userInfo.descriptor!=""?userInfo.descriptor:"&nbsp;&nbsp;&nbsp;"); 
	
	$("#legalName").html(userInfo.legalName!=""?userInfo.legalName:"&nbsp;&nbsp;&nbsp;"); 
	$("#indenEffDate").val(userInfo.indenEffDate!=""?userInfo.indenEffDate:""); 
	$("#indenExpDate").val(userInfo.indenExpDate!=""?userInfo.indenExpDate:""); 
	$("#indenAddress").html(userInfo.indenAddress!=""?userInfo.indenAddress:"&nbsp;&nbsp;&nbsp;"); 
	
	$("#orgTypeCode").val(userInfo.orgTypeCode); 
	$("#partnerType").val(userInfo.partnerType);
	_orgTypeCode =userInfo.orgTypeCode;
	orgPartnerType=userInfo.partnerType;
	if(userInfo.orgTypeCode==2){
		$("#orgType").html('${local["eaap.op2.portal.reg.regcompany"]}'); 
		$("#nameType").html('<font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.enterpriseName"]}'); 
	}else{
		$("#orgType").html('${local["eaap.op2.portal.reg.regPersonal"]}'); 
		$("#nameType").html('<font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.reg.infoName"]}'); 
	}
	
	if(orgPartnerType==1){
		$("#partnerTypeName").html('${local["eaap.op2.portal.reg.partnerTypeForNormalPartner"]}'); 
	}else if(orgPartnerType==2){
		$("#partnerTypeName").html('${local["eaap.op2.portal.reg.partnerTypeForOperator"]}'); 
	}

	$("#certTypeCode").val(userInfo.certTypeCode); 
	if(userInfo.orgTypeCode==1){
		changeCertType();
	}
	privilegeControl(userInfo.state);
	setOrgState(userInfo.state,data.orgState);
};

var isEmpty = function(value){
	if(value == null || $.trim(value) == ''){
		return true;
	}
	return false;
};

var setOrgState = function(_state,orgStateList){
	if(orgStateList!=null, orgStateList.length>0){
		  for(var i = 0; i<orgStateList.length; i++){
			  if(orgStateList[i].cepCode==_state){
					$("#stateName").html(orgStateList[i].cepName);
					break;
			  }
		  }
	}
};

function privilegeControl(state){
	if(state=="C"){
		//C:审核不通过
		$("#submitToCheckBut").removeClass("disabled");
		$("#submitToCheckBut").addClass("theme-btn");
	}else{
		//B:待审核,D:在用
			$("#submitToCheckBut").removeClass("theme-btn");
			$("#submitToCheckBut").addClass("disabled");
		 
			$("#orgType").prop("outerHTML","<p class='form-control-static' id='orgType'>"+$("#orgType").text()+"</p>");
			$("#name").prop("outerHTML","<p class='form-control-static' id='name'>"+$("#name").text()+"</p>");
			$("#certType").prop("outerHTML","<p class='form-control-static' id='certType'>"+$("#certType").text()+"</p>");
			$("#certNum").prop("outerHTML","<p class='form-control-static' id='certNum'>"+$("#certNum").text()+"</p>");
			$("#cityName").prop("outerHTML","<p class='form-control-static' id='cityName'>"+$("#cityName").text()+"</p>");
			
			$("#legalName").prop("outerHTML","<p class='form-control-static' id='legalName'>"+$("#legalName").text()+"</p>");
			$("#indenEffDate").prop("outerHTML","<p class='form-control-static' id='indenEffDate'>"+$("#indenEffDate").val()+"</p>");
			$("#indenExpDate").prop("outerHTML","<p class='form-control-static' id='indenExpDate'>"+$("#indenExpDate").val()+"</p>");
			$("#indenAddress").prop("outerHTML","<p class='form-control-static' id='indenAddress'>"+$("#indenAddress").text()+"</p>");
		 
		
	}
}

var saveUserInfo = function(){
	var name = $("#name").html();
	var email = $("#email").html();
	var subEmail=$("#subEmail").html();
	var phone = $("#phone").html();
	var subTelephone= $("#subPhone").html();
	var certNumber = $("#certNum").html();
	var introduction = $("#introduction").html();
	var orgTypeCode = $("#orgTypeCode").val();
	var orgPartnerType=$("#partnerType").val();
	var areaId = $("#city").val();
	var certTypeCode = $("#certTypeCode").val();
	var indenEffDate=$("#indenEffDate").val();
	var indenExpDate=$("#indenExpDate").val();
	var indenAddress=$("#indenAddress").html();
	var legalName=$("#legalName").html();
	var address = $("#address").html();
	var o2pCloudFlag=$("#o2pCloudFlag").val();
	if(name==""){
		 toastr.warning('${local["eaap.op2.portal.reg.infoName"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
		 return;
	}
	if(email==""){
		 toastr.warning('${local["eaap.op2.portal.reg.regEmail"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
		 return;
	}
	if(phone==""){
		 toastr.warning('${local["eaap.op2.portal.reg.phone"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
		 return;
	}
	if(areaId==""){
		 toastr.warning('${local["eaap.op2.portal.reg.infoCity"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
		 return;
	}
	
	
	if(orgTypeCode == '1'){
		if(o2pCloudFlag!='cloud'){
			if(certNumber==""||certNumber=="&nbsp;&nbsp;&nbsp;"){
				 toastr.warning('${local["eaap.op2.portal.reg.regPapersNUm"]}' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
				 return;
			}
			if(indenEffDate==""){
				toastr.warning('${local["eaap.op2.portal.reg.indenValidityDate"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
				 return;
			}
			if(indenExpDate==""){
				toastr.warning('${local["eaap.op2.portal.reg.indenValidityDate"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
				 return;
			}
			if(certTypeCode==""){
				toastr.warning('${local["eaap.op2.portal.reg.regPapersType"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
				 return;
			}
		}
	
	}else if(orgTypeCode == '2'){
		if(o2pCloudFlag!='cloud'){
			if(legalName==""||legalName=="&nbsp;&nbsp;&nbsp;"){
				toastr.warning('${local["eaap.op2.portal.reg.legalName"]} ' +'${local["eaap.op2.portal.reg.isRequired"]}'); 
				 return;
			}
		}
		
	}
	
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/org/updateOrg.shtml", 
		  data : {
			  name : name,
			  email : email,
			  subEmail:subEmail,
			  phone : phone,
			  subTelephone:subTelephone,
			  areaId : areaId,
			  address : address,
			  certNumber : certNumber,
			  orgTypeCode : orgTypeCode,
			  orgPartnerType:orgPartnerType,
			  introduction : introduction,
			  certTypeCode : certTypeCode,
			  indenEffDate:indenEffDate,
			  indenExpDate:indenExpDate,
			  indenAddress:indenAddress,
			  legalName:legalName
		  },
		  dataType : "json",
		  success : function(data) {
			 if(data && data.code == RESULT_OK){
				 toastr.success(data.desc);
				 window.setTimeout("window.location.reload()",1000);
			 }else{
				 toastr.warning(data.desc); 
			 }
		  }
	});
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


function changeCertType(){
	var orgType = $("#orgTypeCode").val();
	var certTypeCode=$("#certTypeCode").val();
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "../org/getCertTypeList.shtml",
		  data : {
			  orgType : orgType
		  },
		  dataType : "json",
		  success : function(data) {
			  if(data){
				  var certTypeList = data.certTypeList;
				  for(var i = 0; i < certTypeList.length; i++){
					  if(certTypeCode==certTypeList[i].cepCode){
					  	$("#certType").html(certTypeList[i].cepName); 
					  	break;
					  }
				  }
			  }
		  }
	 });
};

var submitToCheck = function(){
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/org/submitToCheck.shtml", 
		  data : {},
		  dataType : "json",
		  success : function(data) {
			 if(data && data.code == RESULT_OK){
				 toastr.success(data.desc);
				 window.setTimeout("window.location.reload()",500);
			 }else{
				 toastr.warning(data.desc); 
			 }
		  }
	});
};


function changeCity(){
	var country = $("#country-list").val();
	var countryName = $("#country-list").find("option:selected").text();
	$("#city").val(country); 
	$("#cityName").html(countryName);  
	$("#citySelDiv").hide();
}

function changeCertTypeValue(){
	var certTypeCode = $("#certType-list").val();
	var certType = $("#certType-list").find("option:selected").text();
	$("#certTypeCode").val(certTypeCode); 
	$("#certType").html(certType);  
	$("#certTypeSelDiv").hide();
}


 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
