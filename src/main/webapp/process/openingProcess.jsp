<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
 
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
      + request.getServerName() + ":" + request.getServerPort()
      + path + "/";
  String manager = request.getParameter("manager");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="../header.jsp" %>
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />

<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->

<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/processResource/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/processResource/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/processResource/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/processResource/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${APP_PATH}/resources/processResource/css/custom.css" rel="stylesheet" type="text/css"/>
<link href="${APP_PATH}/resources/processResource/css/process.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-sidebar-fixed">
<!-- BEGIN HEADER --> 
<!-- END HEADER --> 
<!-- BEGIN CONTAINER -->

<div class="page-container" style="margin-top:0;"  id="cc"> 
  <!-- BEGIN SIDEBAR -->
  <div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse"> 
      <!-- add "navbar-no-scroll" class to disable the scrolling of the sidebar menu --> 
      <!-- BEGIN SIDEBAR MENU -->
      <ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">       
        <li class="start" id="begin"><i class="fa fa-circle"></i> <span class="title"> Begin </span></li>
        <li id="end"><i class="fa fa-square"></i> <span class="title"> End </span></li>
        <li id="receiveTask"> <i class="fa fa-tasks"></i> <span class="title"> ReceiveTask </span></li>
        <li id="userTask"> <i class="fa fa-user"></i> <span class="title"> UserTask </span></li>
        <li id="serviceTask"> <i class="fa fa-cog"></i> <span class="title"> ServiceTask </span></li>
        <li id="noticeTask"> <i class="fa fa-envelope"></i> <span class="title"> NoticeTask </span></li>
        <!--<li id="decision"> <i class="fa fa-square-o"></i> <span class="title"> Decision </span></li>-->
        <!--<li id="exclusiveGateway"><i class="fa fa-times"></i> <span class="title"> ExclusiveGateway </span></li>-->
        <li id="parallelGateway"> <i class="fa fa-plus"></i> <span class="title"> ParallelGateway </span></li>
        <li id="inclusiveGateway"> <i class="fa fa-circle-o"></i> <span class="title"> InclusiveGateway </span></li>
        <li id="path"><i class="fa fa-long-arrow-right"></i> <span class="title"> Line </span></li>
        <!--<li id="save"><i class="fa fa-save"></i> <span class="title"> Save </span></li>-->
        <li id="move"><i class="fa fa-arrows"></i> <span class="title"> Move </span></li>
        <li id="delete" class="last"> <i class="fa fa-trash-o"></i> <span class="title"> Delete </span></li>
      </ul>
      <!-- END SIDEBAR MENU --> 
    </div>
  </div>
  <!-- END SIDEBAR --> 
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <div class="page-content">
      <div class="row toolbar mb20">
      <input type="hidden" id='flag' value = "${flag }" class="form-control input-inline disabled">
          
        <div class="col-md-12 form-inline">
          <input type="hidden" id='systemId' value = "${systemId }" class="form-control input-inline">&nbsp;&nbsp;
          <div class="form-group">
          <label for="">${local["eaap.op2.portal.process.FLOW_NAME"]}:</label>
          <input type="text" id='processName' value = "${processName }" class="form-control">&nbsp;&nbsp;
          </div>
          <input type="hidden" id='proSystemId' value = "${systemId }" class="form-control input-inline disabled"> 
          <input type="hidden" id='processId' value = "${processId }" class="form-control input-inline disabled">
          <input type="hidden" id='messageFlowUrl' value = "${messageFlowUrl }" class="form-control input-inline disabled">
          <input type="hidden" id='status' value = "${status }" class="form-control input-inline disabled">
          <input type="hidden" id='statusUp' value = "${statusUp }" class="form-control input-inline disabled">
           <input type="hidden" id='flowStatus' value = "${flowStatus }" class="form-control input-inline disabled">
          <!--<input name="" type="button" id="submit" value="Save">-->
          <input name="" type="button" class="btn theme-btn step2" id="tempSaveProecss" value="${local['eaap.op2.portal.process.SAVE_PROCESS']}">
          <input name="" type="button" class="btn theme-btn step2" id="saveProecss" value="${local['eaap.op2.portal.process.SUBMIT_PROCESS']}">
           <textarea name="" cols="140" rows="5" id="XMLtextShow" style="display:none"  >${xmlContent }</textarea>    
        </div>
        <div class="col-md-12">
           <table style="margin-top: 5px;">
             <tr>
              <td style="padding-left: 20px;"><input name="" type="button" class="btn theme-btn step2" id="exportProecss" value="export" style="display: none;"></td>
              <td style="padding-left: 20px;">
                <table>
                   <tr><td>
                      <input name="" type="button" class="btn theme-btn step2" id="importProecss" value="import" style="display: none;">
                    </td><td>
                    <form method="post" id="reqFile" enctype="multipart/form-data">
                  <input type="file" id="file" style="display: none;" name="file" multiple>
                </form> 
                     </td>
                  </tr>
                </table>
          </td>
             <tr>
           </table>
        </div>
      </div>     
      <div id="group1"></div>
    </div>
  </div>
  <!-- END CONTENT --> 
</div>
<!-- END CONTAINER --> 
<!-- BEGIN FOOTER --> 

<!-- END FOOTER --> 
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) --> 
<!-- BEGIN CORE PLUGINS --> 
<!--[if lt IE 9]>
<script src="${APP_PATH}/resources/processResource/plugins/respond.min.js"></script>
<script src="${APP_PATH}/resources/processResource/plugins/excanvas.min.js"></script> 
<![endif]--> 

<script src="${APP_PATH}/resources/processResource/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/jquery.cokie.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/jquery.i18n.properties-min-1.0.9.js" type="text/javascript" ></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript" ></script>
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-multiselect/bootstrap-multiselect.js"></script>

<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL PLUGINS --> 

<!-- END PAGE LEVEL PLUGINS --> 
<!-- BEGIN PAGE LEVEL SCRIPTS --> 
<script src="${APP_PATH}/resources/scripts/app.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/processResource/scripts/custom/messagesport.1.0.js" type="text/javascript"></script>
<script type="text/javascript" src="${APP_PATH}/resources/processResource/scripts/custom/tacheInfo.js"></script>

<script src="${APP_PATH}/resources/processResource/scripts/custom/workFlow.js" type="text/javascript"></script> 
 <!-- END PAGE LEVEL SCRIPTS --> 


<link href="${APP_PATH}/resources/bootstrap/css/GooFlow.css" rel="stylesheet" type="text/css" />
<script src="${APP_PATH}/process/noticeTaskConf.js" type="text/javascript"></script> 

<script>
var manager = "<%=manager%>";
var noticeServiceId = "${noticeServiceId}";
var noticeMessageFlowId = "${noticeMessageFlowId}";
$(function() {
   //????
   if(manager == 'admin'){
     managerButton();
   } 
   // App.init(); // initlayout and core plugins
   WF.init(); 
   $("#cc").messagesport();
   $('#chooseCancel').live('click',function(){
     $('#chooseService').modal('hide');
   });
   
   $('#chooseOK').live('click',function(){
     var oSelected = $("#chooseSerTable>tbody input[type='radio']:checked");
       if (oSelected.size() == 1) {
         var name = $("#chooseSerTable>tbody input[type='radio']:checked").attr('trName');
         
         $('#INVOKE_API_NAME').val(name);
         $('#tache_name').val(name);
         $('#INVOKE_API').val($("#chooseSerTable>tbody input[type='radio']:checked").val());
         $('#chooseService').modal('hide');
         $('#msgFlowConfigBut').attr('disabled',false);
         //$('#inParamBut').attr('disabled',false);
          // $('#outParamBut').attr('disabled',false);
       }else{
         alert("Choose one record");
       }
   });
});

$(function () {
  var LANGUAGE = '${language}'; 
  jQuery.i18n.properties({
    name : 'eaap-op2-portal-messages',        
    path : APP_PATH+'/resources/i18n/',
    language: LANGUAGE, 
    mode : 'map'
  });
}); 
var chooseSerTable;
function loadChooseServiceTable(){
  //?????? 
        chooseSerTable = $('#chooseSerTable').dataTable({
    'ordering':false,
    "ajax": "${APP_PATH}/provider/chooseService.shtml?componentId="+$('#proSystemId').val(),
    "paging":false,
    "info":false,
    "searching": false
    });
}
//????????
function invokeApiChoose() { 
   var url="${APP_PATH}/provider/chooseService.shtml?componentId="+$('#proSystemId').val();
   chooseSerTable.api().ajax.url(url).load();
         $('#chooseService').modal('show');
    }
    
//????
function managerButton(){
   $("#exportProecss").css("display","block");
   $("#importProecss").css("display","block");  
   $("#file").css("display","block"); 
   $("#exportProecss").bind('click',function(){
     var systemId = $('#systemId').val(); 
     var processId = $('#processId').val();
     if(!processId){
       toastr.error('Please save the process!');
       return;
     }
     document.location.href = "${APP_PATH}/process/exportProcess.shtml?systemId="+systemId+"&processId="+processId;
   });
   $("#importProecss").bind('click',function(){
     uploadProcess();
   });
}

function uploadProcess(){
  var formData = new FormData(document.getElementById("reqFile"));
  var modelName = $('#processName').val(); 
  $.ajax({
        url: "${APP_PATH}/process/importProcess.shtml?modelName="+modelName,
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,  
        contentType: false 
      }).done(function(data) {
        if(data){
          if(data.code = '0000'){
            alert("processId:" + processId); 
          }else{
            alert("Import failure!");  
          }
          
        }
      });
}


function checkboxCtr(obj){
  var selectOptionConList =$("select[name='selectOptionCon']");
  if(obj.checked){
    $("input[name='selectCon']").attr("checked",true); 
       for(var j = 0; selectOptionConList.length > j; j++){
           selectOptionConList[j].removeAttribute("disabled"); 
            $("#selectButton").removeAttr("disabled"); 
            $("#selectButton").css("background","#ffffff");
            $("#selectButton").removeClass("disabled");
       }
  }else{
    $("input[name='selectCon']").attr("checked",false); 
     for(var j = 0; selectOptionConList.length > j; j++){
           selectOptionConList[j].setAttribute("disabled", "disabled");
            $("#selectButton").attr("disabled", "disabled");
            $("#selectButton").css("background","#eeeeee");
       }
  }
}
</script> 
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- ???????  -->
<div class="modal container panel" id="tachInfoRoute" style="display:none;"  >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >&times;</button>
    <h4>${local["eaap.op2.portal.process.TACHEINFO"]}
    </h4>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-condensed" id ="activityTabGeneralTable" style="margin:10px auto;">
      <tr>
        <td style="text-align:right;">${local["eaap.op2.portal.process.LINE_ID"]}
          :</td>
        <td><input class="form-control" type="text" id = "line_id" placeholder="Text input" readonly/></td>
         
        <td style="text-align:right;">${local["eaap.op2.portal.process.FROM_TACHE"]}
          :</td>
        <td><input class="form-control" type="text" id = "from_tache" placeholder="Text input" readonly/>
        <input class='form-control'type="hidden" id ="from_tache_id"  placeholder="Text input"/>
        </td>
        <td style="text-align:right;">${local["eaap.op2.portal.process.TO_TACHE"]}
          :</td>
        <td><input class='form-control' type="text" id ="to_tache"  placeholder="Text input" readonly/>
        <input class='form-control' type="hidden" id ="to_tache_id"  placeholder="Text input"/>
        </td>
        
      </tr> 
      <tr>
        <td style="text-align:right;">${local["eaap.op2.portal.process.ROUTING_CONDITIONS"]}
          :</td>
        <td colspan=5>
        <table class="table table-bordered table-condensed text-center" style="margin:20px auto;" id="pathDetListDt">
          <thead style="background:#CCC">
              <th style="width:36px;"><input name=""   type="checkbox" value=""  id="checkboxAll"  onchange='checkboxCtr(this)' ></th>
                <th>${local["eaap.op2.portal.process.CONDITIONS"]}</th>
                <th>${local["eaap.op2.portal.process.VALUE"]}</th>
            </thead>
            <tbody id = "pathDetList">
                           
            </tbody>
            
        </table>
        </td>
      </tr>
    </table>
  </div>
  <div class="modal-footer" style="text-align:center">
    <button name="OK" type="button"  class="btn theme-btn" onclick="onClickOK();">
    ${local["eaap.op2.portal.process.OK"]}
    </button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick = " ">
    ${local["eaap.op2.portal.process.CANCEL"]}
    </button>
  </div>
</div>
<!-- ?????  -->
<div class="modal container panel" id="tachInfo" style="display:none;top:300px; "  >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"  >X</button>
    <h4>
      ${local["eaap.op2.portal.process.TACHEINFO"]}
    </h4>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-condensed" id ="activityTabGeneralTable" style="margin:10px auto;">
      <tr>
          
        <td style="text-align:right;">${local["eaap.op2.portal.process.TACHE_ID"]}
          :</td>
        <td><input class="form-control" type="text" id = "tache_id" placeholder="Text input" readonly/> 
        </td>
        <td style="text-align:right;">${local["eaap.op2.portal.process.TACHE_NAME"]}
          :</td>
        <td> 
        <input class='form-control'type="text" id ="tache_name"  placeholder="Text input"/>
        </td>
         
        <td style="text-align:right;">${local["eaap.op2.portal.process.TACHE_TYPE"]}
          :</td>
        <td><input class='form-control'type="text" id ="act_type"  placeholder="Text input" readonly/>
         </td>
      </tr> 
       <tr id="apiTr">
        <td class="td-fix">${local["eaap.op2.portal.process.API_BY_PARAMETERS"]}
          :</td>
        <td colspan=5 ><select class='form-control' id ="API_PARAMTER_COMBO">
            <option value = "1" selected="selected">
            ${local["eaap.op2.portal.process.THE_ORIGINAL_MESSAGE"]}
            </option>
            <option value = "2">
            ${local["eaap.op2.portal.process.A_LINK_TO_RETURN_A_MESSAGE"]}
            </option>
          </select></td>
      </tr>
      <tr id="autoTr" style="display:none">
        <td class="td-fix">AUTO_EXECUTE_METHOD
          :</td>
        <td colspan=5 ><select class="form-control" id ="AUTO_EXEC_METHOD" name="select" onchange="receiveAutoChange(this.value)">
            <option value="1">
            INVOKE_API
            </option>
            <option value="2">
            INVOKE_SCRIPT
            </option>
          </select></td>
      </tr>
      
      
      <tr id="emailNotification" style="display:none">
        <td class="td-fix">Email notification:</td>
         <td colspan=5>
           <button  class="btn disable btn-sm" id="emailNotificationBut" onclick="emailSmsNoticeConf('email')">Protocol Configuration</button>
        </td>
      </tr>
      <tr id="smsNotification" style="display:none">
        <td class="td-fix">SMS notification:</td>
         <td colspan=5>
           <button  class="btn disable btn-sm" id="smsNotificationBut" onclick="emailSmsNoticeConf('sms')">Protocol Configuration</button>
        </td>
      </tr>
      
      <tr id="invokeApiTr" >
        <td class="td-fix">${local["eaap.op2.portal.process.INVOKE_API"]} :</td>
        <td colspan=1><input type="hidden" id="INVOKE_API" name="INVOKE_API"/>
          <input class='form-control'  placeholder="Text Input" type="text" id="INVOKE_API_NAME" name="INVOKE_API_NAME" readonly  />
        <input class='form-control'type="hidden" id ="inParam"  placeholder="Text input"/>
        <input class='form-control'type="hidden" id ="outParam"  placeholder="Text input"/>
        <input class='form-control'type="hidden" id ="messageFlowId"  placeholder="Text input"/>
        <input class='form-control'type="hidden" id ="serInvokeInsId"  placeholder="Text input"/>
        <!-- choose?????    <input type="button" id=\"invokeApiBut\" value=\"Choose\" onclick=\"invokeApiChoose()\" class=\"sys-btn\">"
                 --></td>
        <td colspan=4><button class="savebtn btn newblue btn-sm " id="invokeApiBut" onclick="invokeApiChoose()">Choose</button>
        <button  class="btn disable btn-sm" id="msgFlowConfigBut" onclick="msgFlowConfig()">Message Flow Configuration</button>
       <!--  <button   class="btn btn-warning btn-sm" id="inParamBut" onclick="inParamChoose()">${local["eaap.op2.portal.process.IN_PARAM_CONVERT"]}</button>
        <button   class="btn btn-warning btn-sm" id="outParamBut" onclick="outParamChoose()">${local["eaap.op2.portal.process.OUT_PARAM_CONVERT"]}</button>
      -->
      </td>
      </tr>
      <tr id="invokeScriptTr" style="display:none">
        <td class="td-fix">INVOKE_SCRIPT
          : </td>
        <td colspan="5"><textarea class="textarea" rows="5" style="width:100%" id="exec_proc" name="exec_proc" ></textarea></td>
      </tr>
      <tr id= "PollingTd" >
        <td class="td-fix">Invoke Method
          :</td>
        <td colspan=5>
        <label>
            <input type='radio' name="Polling" value='1' />
            Polling
          </label>
          <label>
            <input type='radio' name="Polling" value='0' checked/>
            Callback
          </label>
        </td>
      </tr>
      <tr>
        <td class="td-fix">${local["eaap.op2.portal.process.DESCR"]}
          :</td>
        <td colspan=5><textarea class="textarea form-control" rows="5" style="width:100%" id="remark"></textarea></td>
      </tr>
    </table>
  </div>
  <div class="modal-footer" style="text-align:center">
    <button name="OK" type="button"  class="btn theme-btn savebtn" onclick="pathAutoExecuteMethodJsonUpdate();">
    ${local["eaap.op2.portal.process.OK"]}
    </button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick = " ">
     ${local["eaap.op2.portal.process.CANCEL"]}
    </button>
  </div>
</div>
 <div id="apiParamModal" class="modal  container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.process.IN_PARAMS"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
       <iframe name="inParamiframe" id="inParamiframe" src="" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 520px;" allowtransparency="true"></iframe>
      <input type="hidden" class="btn btn-default" id="incloseButton" data-dismiss="modal"/>
    </div>
  </div>
</div>

 <div id="apiParamModalout" class="modal  container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.process.OUT_PARAMS"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
       <iframe name="outParamiframe" id="outParamiframe" src="" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 520px;" allowtransparency="true"></iframe>
      <input type="hidden" class="btn btn-default" id="outcloseButton" data-dismiss="modal"/>
    </div>
  </div>
</div>
<div id="chooseService" class="modal  container fade" tabindex="-1" style="display: none;">
<div class="modal-header">
 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
 <h4 class="modal-title">${local["eaap.op2.portal.process.CAPABILITY"]}</h4>
</div>
<div class="modal-body">
 <div class="step step1">
  <table class="table table-bordered table-striped table-hover text-center group-check2" id="chooseSerTable">
   <thead>
    <tr>
     <th style="width:100px;">
      
     </th>
     <th style="width:100px;"> ${local["eaap.op2.portal.process.CLASSIFICATION"]} </th>
     <th style="width:360px;"> ${local["eaap.op2.portal.process.CAPABILITY_NAME"]}</th>
     <th style="width:560px;"> ${local["eaap.op2.portal.process.CAPABILITY_PARAMETER"]}</th>
    </tr>
   </thead>
   <tbody>
   </tbody>
  </table>
 </div>
</div>
<div class="modal-footer">
 <button type="button" class="btn btn-default step2" id="chooseCancel">${local["eaap.op2.portal.process.CANCEL"]}</button>
 <button type="button" class="btn theme-btn step2 savebtn" id="chooseOK">${local["eaap.op2.portal.process.OK"]}</button>
</div>
</div>


 
<div id="emailSmsNoticeConf" class="modal  container fade"   style="display:;">
<div class="modal-header">
 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
 <h4 class="modal-title">Protocol Configuration</h4>
</div>
<div class="container">
<div class="col-md-12 margin-top-10" >
  <div class="col-md-8">
    <div>
      <p style="margin-bottom:2px"><label id="esAddTitle">address:</label></p>
      <textarea id="msAddress" style="border:1px solid #dfdfdf; height:68px; padding:5px; width:100%; resize:none;" type="data-area"></textarea>
    </div>
    <div id="emailTitleDiv">
      <p style="margin-bottom:2px"><labe>Title:</label></p>
      <textarea id="emailTitle" style="border:1px solid #dfdfdf; height:68px; padding:5px; width:100%; resize:none;" type="data-area"></textarea>
    </div>
    <div>
      <p style="margin-bottom:2px"><label>Content:</label></p>
      <textarea id="msContent" style="border:1px solid #dfdfdf; height:230px; padding:5px; width:100%; resize:none;" type="data-area"></textarea>
    </div>
  </div>
    <div class="col-md-4">
      <div class="tab-content" style="height:440px;width:365px; overflow-y:auto; border:1px solid #dfdfdf; padding:1px 0 0 1px;">
        <div  class="modal-body GooFlow stolist"  style="padding:0; border:0px; " id="protocolList">
          <div class="pathList" style=" border:0px ;">
            <div id="productAttribute"  style="width:340px;"></div>
            <div id="orderCompletedProtocol"  style="width:340px;"></div>
          </div>
        </div>
    </div>
  </div>
</div>
</div>
<div class="modal-footer" style="margin-top:5;">
 <button type="button" class="btn btn-default step2" id="emailSmsNoticeConfCancel">${local["eaap.op2.portal.process.CANCEL"]}</button>
 <button type="button" class="btn theme-btn step2 savebtn" id="emailSmsNoticeConfOK"  onclick="saveNoticeTaskConf()">${local["eaap.op2.portal.process.OK"]}</button>
</div>
</div>

</html>