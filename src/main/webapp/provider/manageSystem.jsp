<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/jstree/css/style.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-wizard/bootstrap-wizard.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->


<link rel="shortcut icon" href="favicon.ico" />

<%@include file="../header.jsp" %>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<input type="hidden" id="hidComponentId" value="${componentId}"/>
<input type="hidden" id="hidComponentName" value="${systemMap.NAME}"/>
<input type="hidden" id="hidContractId" />
<input type="hidden" id="hidIsPublic" />
<input type="hidden" id="hidDirSerlistId" />
<input type="hidden" id="hidServiceId" />
<input type="hidden" id="hidTechImpAttId" />
<input type="hidden" id="hidTechimplId" />
<input type="hidden" id="hidContractVersionId" />
<input type="hidden" id="hidTcpCtrFId" /><!-- 请求的协议格式ID -->
<input type="hidden" id="hidRspTcpCtrFId" /><!-- 响应的协议格式ID -->
<input type="hidden" id="hidProdOfferId" name ="hidProdOfferId"/>
<input type="hidden" id="hidProdOfferName" name ="hidProdOfferName"/>
<input type="hidden" id="state" value="${state }"/>
<input type="hidden" id="sysProcessId" value="${processId }"/>
<input type="hidden" id="upState" value="${upState }"/>
<input type="hidden" id="flowStatus"  value="${flowStatus}"/>
<input type="hidden" id="flowName"/>

<!-- BEGIN HEADER -->
<!-- END HEADER --> 

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.doc.systemDetail"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a></li>
          <li class="active">${systemMap.NAME}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTENT -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="s-sidebar">
    <c:choose>
      <c:when test="${not empty systemMap.SFILEID }">
         <img id="sysImg" src="${APP_PATH}/provider/readImg.shtml?sFileId=${systemMap.SFILEID}" width="250" height="166" alt="">
      </c:when>
      <c:otherwise>
         <img id="sysImg" src="${APP_PATH}/resources/img/pics/250x250.jpg" width="250" height="166" alt="">
      </c:otherwise>
    </c:choose>
     <a href="#" <c:if test="${userAble == 'NO'}">disabled</c:if>  data-height="166" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal" class="s-pic-edit changeImg"> ${local["eaap.op2.portal.doc.message.edit"]} </a>
      <c:if test="${state == 'A'|| state == 'C'|| state == 'G'|| upState == 'E'|| upState == 'F'}"> 
         <a class="btn theme-btn btn-block" id="onlineOperation"> ${local["eaap.op2.portal.devmgr.submitToCheck"]}</a>
         <a class="btn default btn-block disabled" id="upgradeStateA">${local["eaap.op2.portal.doc.systemUpGrade"]}</a>
         <a class="btn default btn-block disabled" id="offline">${local["eaap.op2.portal.doc.systemDown"]}</a>
      </c:if>
      <c:if test="${state == 'B' || (state == 'D' && upState == 'H')}"> 
         <a class="btn default btn-block disabled" id="online"> ${local["eaap.op2.portal.devmgr.submitToCheck"]}</a>
         <a class="btn default btn-block disabled" id="upgrade">${local["eaap.op2.portal.doc.systemUpGrade"]}</a>
         <a class="btn default btn-block disabled" id="offline">${local["eaap.op2.portal.doc.systemDown"]}</a>
      </c:if>
      <c:if test="${state == 'D' && upState == ''}"> 
         <a class="btn default btn-block disabled" id="online"> ${local["eaap.op2.portal.devmgr.submitToCheck"]}</a>
         <a class="btn theme-btn btn-block" id="upgradeStateD">${local["eaap.op2.portal.doc.systemUpGrade"]}</a>
         <a class="btn default btn-block" id="offlineStateD">${local["eaap.op2.portal.doc.systemDown"]}</a>
      </c:if>
      <%-- 
      <c:if test="${state == 'E'}"> 
         <a class="btn default btn-block disabled" id="online"> ${local["eaap.op2.portal.devmgr.submitToCheck"]}</a>
         <a class="btn default btn-block disabled" id="upgrade">${local["eaap.op2.portal.doc.systemUpGrade"]}</a>
         <a class="btn theme-btn btn-block" id="offlineStateE">${local["eaap.op2.portal.doc.systemDown"]}</a>
      </c:if>--%>
      <c:if test="${state == 'A'|| state == 'C'|| state == 'G'|| (state == 'D' && upState == 'F')}"> 
      <a class="btn theme-btn btn-block" id="deleteStateAll">${local["eaap.op2.portal.doc.systemDel"]}</a>
      </c:if>
      <c:if test="${state == 'B'|| (state == 'D' && (upState == 'E'|| upState == 'H'))}"> 
      <a class="btn default btn-block disabled">${local["eaap.op2.portal.doc.systemDel"]}</a>
      </c:if>
      <c:if test="${state != 'A'}">
      <a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=COMTODO&lookFalg=&query=${componentId}" target="_blank">${local["eaap.op2.portal.portalMessage.history"] }</a>
      </c:if>
      <c:if test="${state == 'A'}">
      <a class="btn default btn-block disabled" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=COMTODO&lookFalg=&query=${componentId}" target="_blank">${local["eaap.op2.portal.portalMessage.history"] }</a>
      </c:if>
    </div>
    <!-- BEGIN -->
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">${local["eaap.op2.portal.doc.systemDetail"]}</h3>
          </div>
          <div class="panel-body">
            <form class="" role="form">
            <div class="form-body">
            <ol class="bwizard-steps clearfix mb20" role="tablist">
              <li style="z-index: 4;" id ="li1" class="active"><span class="label">1</span>${local["eaap.op2.portal.doc.createSys"]} </li>
              <li style="z-index: 3;" id ="li2"><span class="label">2</span>${local["eaap.op2.portal.devmgr.developTest"]} </li>
              <li style="z-index: 2;" id ="li3"><span class="label">3</span>${local["eaap.op2.portal.devmgr.submitToCheck"]} </li>
              <li style="z-index: 1;" id ="li4"><span class="label">4</span>${local["eaap.op2.portal.doc.systemUp"]} </li>
            </ol>
            <form novalidate action="#" id="submit_form">
              <div class="">
                <div class="form-body">
                  <h4 class="form-section mt5">* ${local["eaap.op2.portal.doc.baseInfo"]}</h4>
                  <div class="form-horizontal form-bordered form-row-stripped">
                    <div class="form-group">
                      <label class="col-md-4 control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.doc.sysName"]}:</label>
                      <div class="col-md-8">
                        <a  href="#" style="color:#999;" disabled class="editable editable-click">${systemMap.NAME} </a>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.portal.doc.systemNo"]}:</label>
                      <div class="col-md-8">
                        <a  href="#" style="color:#999;" disabled class="editable editable-click">${systemMap.CODE}</a> </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.portal.doc.createTime"]}:</label>
                      <div class="col-md-8">
                        <a  href="#" disabled style="color:#999;" class="editable editable-click">${systemMap.REGTIME}</a> </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.portal.doc.systemDetail"]}:</label>
                      <div class="col-md-8">
                      <a  <c:if test="${userAble == 'Yes'}">data-name="systemDetail"</c:if> href="#"  <c:if test="${userAble == 'NO'}">disabled style="color:#999;"</c:if> class="editable editable-click">${systemMap.DESCRIPTOR}</a> </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-4 control-label">${local["eaap.op2.conf.adapter.state"]}:</label>
                      <div class="col-md-8">
                          <c:if test="${state == 'A'}"><%--新增 --%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.newadd"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'B'}"><%--待审核--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.pendingAudit"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'C'}"><%--审核失败--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.auditFailure"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'D' && upState == ''}"><%--上线--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.online"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'G'}"><%--下线--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.offline"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'D' && upState == 'E'}"><%--升级中--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.upgrading"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'D' && upState == 'H'}"><%--等待升级审核--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.upgradeAudit"]}</a> </div>
                          </c:if>
                          <c:if test="${state == 'D' && upState == 'F'}"><%--升级失败--%>
                            <a  href="#" style="color:#999;" disabled class="editable editable-click">${local["eaap.op2.portal.system.state.upgradeFailed"]}</a> </div>
                          </c:if>
                      </div>
                      <c:if test="${state=='C' || state=='F'}">
                      	<div class="form-group">
			              <label class="col-md-4 control-label">Feedback Message:</label>
			              <div class="col-md-8" style="line-height:35px;color:blue;">
			              		${checkMsg }
			              </div>
			           </div>
                      </c:if>
                    </div>
                  </div>
                    
                  <h4 class="form-section"><div  class="btn-group pull-right" style="margin-top:-5px;">
		                <c:if test="${userAble == 'Yes'}">
		                  <a class="btn default btn-sm" data-toggle="dropdown" href="javascript:;"> ${local["eaap.op2.portal.doc.add"]} <i class="fa fa-angle-down"></i></a>
                        <ul class="dropdown-menu tpl" role="menu" data-set="addTarget">
                          <li> <a href="javascript:;" class="add-general"> ${local["eaap.op2.portal.doc.generalService"]} </a> </li>
                          <li> <a href="javascript:;" class="add-customized"> ${local["eaap.op2.portal.doc.oneselfService"]} </a> </li>
                        </ul>
                        </c:if>
                      </div>* ${local["eaap.op2.portal.doc.applyability"]}</h4>               
                   
                        <table class="table table-bordered" id="showTopTable">
                            <thead>
                              <tr>
                                <th> ${local["eaap.op2.portal.doc.fenType"]} </th>
		                        <th> ${local["eaap.op2.portal.doc.applyabilityName"]} </th>
		                        <th> ${local["eaap.op2.portal.doc.developValue"]} </th>
		                        <th> ${local["eaap.op2.portal.doc.operator"]} </th>                            
                              </tr>
                            </thead>
                            <tbody>
                            </tbody>
                          </table>
                   
                  <h4 class="form-section">
                  <c:if test="${userAble == 'Yes'}">
                    <div class="pull-right" style="margin-top:-5px;"><a class="btn default btn-sm" href="javascript:;" data-toggle="modal" data-target="#modal5"  id="apiOfferAddBtn">${local["eaap.op2.portal.doc.add"]} </a> </div>
                   </c:if> 
                    * API ${local["eaap.op2.portal.doc.package"]}</h4>
                   
                   <div class="table-responsive"> 
                  <table class="table table-bordered"  id="APIOfferTable">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th>API ${local["eaap.op2.portal.doc.packageName"]} </th>
                        <th>  ${local["eaap.op2.portal.doc.applyability"]} </th>
                        <th>  ${local["eaap.op2.portal.doc.pricePlan"]} </th>
                        <th>  ${local["eaap.op2.portal.settlement.settle"]} </th>
                        <th> ${local["eaap.op2.portal.doc.operator"]} </th>
                      </tr>
                    </thead>
                    <tbody id="offerSysModalTB">
                       <c:forEach var="offerInfo" items="${showPackageList}" varStatus="vs"> 
					     <tr id=${offerInfo.PROD_OFFER_ID }>
					       <td colspan="1">${vs.count }</td>
					       <td colspan="1">${offerInfo.PACKAGENAME}</td>
					       <td colspan="1">
						   <c:forEach var="api" items="${offerInfo.APIs}" varStatus="v1"> 
					         <a href="${APP_PATH }/api/apiDoc.shtml?serviceId=${api.API_ID }"  target="_Blank">${api.API_NAME }</a> 
					       </c:forEach>
                           </td>
					       <td colspan="1">
					       <c:if test="${userAble == 'Yes'}">
					         <a href="javascript:;" class="addPricePlan" ><i class="fa fa-plus"></i></a>
					       </c:if>
					       <c:forEach var="pricePlan" items="${offerInfo.PricePlan}" varStatus="vv"> 
					       <li id= "li${pricePlan.PRICINGTARGETID}" >
					          <a href="javascript:;" class="editPricePlan" name ="${pricePlan.PRICINGTARGETID}" id="${pricePlan.PRICINGINFOID}"> ${pricePlan.PRICINGNAME }</a> 
					          <c:if test="${state == 'A'|| state == 'C'|| state == 'G'|| upState == 'E'|| upState == 'F'}">
					          <a href="javascript:;" class="" name ="0minus0${pricePlan.PRICINGTARGETID}" id="0minus0${pricePlan.PRICINGINFOID}" ><i class="fa fa-times-circle delPricePlan"></i></a> 
					          </c:if>
					          </li>
					       </c:forEach>
					         
					       </td>
					       <td colspan="1">
					       <c:if test="${userAble == 'Yes'}">
					        <c:if test="${fn:length(offerInfo.settleList)<1}">
					         <a href="javascript:;" class="addSettle" ><i class="fa fa-plus"></i></a>
					        </c:if>
					        </c:if> 
					          <c:forEach var="settleList" items="${offerInfo.settleList}" varStatus="vv"> 
					          <a href="javascript:;" class="editSettle"  id="${settleList.RULE_ID}"> ${settleList.RULE_NAME }</a> 
					         <c:if test="${state == 'A'|| state == 'C'|| state == 'G'|| upState == 'E'|| upState == 'F'}">
					          <a href="javascript:;" class="delSettle"  id="0minus0${settleList.RULE_ID}"><i class="fa fa-times-circle"></i></a> 
					          </c:if>
					       </c:forEach>
					       </td>
					       <td><a href="javascript:;" <c:if test="${userAble == 'NO'}">disabled</c:if> class="delOffer btn default btn-sm">${local["eaap.op2.portal.pardDistribution.delete"]}</a></td>
					     </tr>
					   </c:forEach>
                      
                      
                    </tbody>
                  </table>  
                  </div>
                  
                  
                  <h4 class="form-section">
                   
                  <c:choose>  
                    <c:when test="${fn:length(processList)>=1}">
                      <div  id="addprocessbtn" class="pull-right" style="margin-top:-5px;"    ><a class="btn default btn-sm editProcess"  href="javascript:;"   >Edit </a> </div>
                   </c:when>
                    <c:otherwise >
                      <div  id="addprocessbtn2" class="pull-right" style="margin-top:-5px;"   ><a class="btn  default  btn-sm add-process" href="javascript:;"   > Edit </a> </div>
                   </c:otherwise>
                  </c:choose>
                   
                    * Provisioning Process</h4>
                     <c:if test="${not empty processId }">
                        <div class="table-responsive">
							<img src="${processDefImg}"/>
						</div>
                   </c:if>
                   <div class="table-responsive" hidden> 
                  <table class="table table-bordered" id= "processTable">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th> Process Name </th>
                        <th> Process Description </th>
                        <th>    </th>
                        <th>    </th>
                        <th> ${local["eaap.op2.portal.doc.operator"]} </th>
                      </tr>
                    </thead>
                    <tbody id="proccesSysModalTB">
                       <c:forEach var="processInfo" items="${processList}" varStatus="vs"> 
					     <tr id='TR${processInfo.PROCESS_ID }'>
					       <td colspan="1">${vs.count }</td>
					       <td colspan="1"><a href="javascript:;" class="editProcess"  id="${processInfo.PROCESS_ID}"> ${processInfo.PROCESS_NAME }</a></td>
					       <td colspan="1">
						    
                           </td>
					       <td colspan="1">
					        
					       </td>
					       <td colspan="1">
					        
					       </td>
					       <td><a href="javascript:;" <c:if test="${userAble == 'NO'}">disabled</c:if> class="delProcess btn default btn-sm">${local["eaap.op2.portal.pardDistribution.delete"]}</a></td>
					     </tr>
					   </c:forEach>
                      
                      
                    </tbody>
                  </table>  
                  </div>
                  
                </div>
              </div>
            </form>
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
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jstree/jstree.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/hover-dropdown.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js" type="text/javascript"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>  
<script src="${APP_PATH}/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/resources/scripts/manageSystem.js"></script> 
<script src="${APP_PATH}/provider/sysPricePlanAdd.js"></script> 
<script src="${APP_PATH}/provider/settleAdd.js"></script> 
<script type="text/javascript">
var oneRecords = '${local["eaap.op2.portal.doc.message.oneRecords"]}';
var success = '${local["eaap.op2.portal.doc.message.success"]}';
var sysNameShow = '${local["eaap.op2.portal.doc.message.newSysName"]}';
 jQuery(document).ready(function() {
	// alert("${state}");
  var _IS_OP = "${state}";
  App.init();
  ManageSystem.init(_IS_OP);
   
  $('#upgradeStateA').click(function(){
	  var componentId = $('#hidComponentId').val();
	  $(this).attr('href',"../provider/provOperator.shtml?componentId="+componentId+'&state=B');
  });
  $('#upgradeStateD').click(function(){
	  var componentId = $('#hidComponentId').val();
	  $(this).attr('href',"../provider/provOperator.shtml?componentId="+componentId+'&state=E');
  });
  $('#offlineStateD').click(function(){
	  var componentId = $('#hidComponentId').val();
	  $(this).attr('href',"../provider/provOperator.shtml?componentId="+componentId+'&state=G');
  });
  $('#offlineStateE').click(function(){
	  var componentId = $('#hidComponentId').val();
	  $(this).attr('href',"../provider/provOperator.shtml?componentId="+componentId+'&state=G');
  });
  $('#deleteStateAll').click(function(){
	  //var componentId = $('#hidComponentId').val();
	  //$(this).attr('href',"../provider/provOperator.shtml?componentId="+componentId+'&state=X');
	  bootbox.confirm("Are you sure to delete the system ?", function(result) {
	      	if(result){
	      		var componentId = $('#hidComponentId').val();
	      		window.location.href="../provider/provOperator.shtml?componentId="+componentId+'&state=X';
				
	      	      }
	      		});
  });
  $('#onlineOperation').click(function(){
	  //检查api offer是否绑定能力
	  var offerNames = new Array();
      $(".offerTrClass").each(function(index){
       var offerTr = $(this);
       var offerId = offerTr.attr("id");
       if(!offerId){
        var offerTds = offerTr.find('td');
        var offername = $(offerTds[1]).attr("offername");
        offerNames.push(offername);
       }
      });
      if(offerNames.length > 0){
       var offerNameStr = offerNames.join(",");
       toastr.error($.i18n.prop('eaap.op2.portal.message.apiOfferCapabilityIsNotNull',offerNameStr));
      return false;
      }
      
    	  
      //点击提交页面请求跳转
	  var state = $('#state').val();
	  var componentId = $('#hidComponentId').val();
	  var processId=$('#sysProcessId').val();
	  var activity_Id  = '';
	  
	  
      //检查有配置开通流程的流程状态是否为提交状态
      getFlowStatusAndName(componentId,processId);
      var flowStatus=$("#flowStatus").val();
      var flowName=$("#flowName").val();
      if(flowStatus=="T"){
    	  toastr.error($.i18n.prop('eaap.op2.portal.message.provisioningProcess.state',flowName));
      }else if(flowStatus==""||flowStatus=="D"){
    	  window.location.href="../provider/provAudiDev.shtml?componentId="+componentId;
      }
  });
 });
 //图片上传回调函数
 function uploadCallback(data){
	 if('' != data){
		 var componentId = $('#hidComponentId').val();
	     $('#sysImg').attr('src','../provider/readImg.shtml?sFileId='+data);
	     $.ajax({
	 		type: "POST",
	 		async:false,
	 	    url: "../provider/updateComponent.shtml",
	 	    dataType:'json',
	 	    data:{componentId:componentId,sFileId:data},
	 		success:function(data){ 
	 			if (data.code == "0000") {
	 				toastr.success(success);
	 			}else{
	 				toastr.success('Edit Fail');
	 			}
	           }
	       });
	 }
 }
 
 function getFlowStatusAndName(componentId,processId){
     $.ajax({
	 		type: "POST",
	 		async:false,
	 	    url: "../provider/getFlowStatusAndName.shtml?",
	 	    dataType:'json',
	 	    data:{
	 	    	processId:processId,
	 	    	systemId:componentId
	 	    	},
	 		success:function(data){ 
	 			if (data.code == "0000") {
						$("#flowStatus").attr('value',data.flowStatus);
						$("#flowName").attr('value',data.flowName);
	 			}else{
	 					toastr.error('get open flow fail');
	 			}
	           }
	       });
 }
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- Modal -->
<div id="addModal1" class="modal container fade" tabindex="-1" style="display: none;">
</div>
<div id="addModal2" class="modal container fade" tabindex="-1" style="display: none;">
</div>
<div id="editModal" class="modal container fade" tabindex="-1" style="display: none;">
</div>
<div id="serviceAddress" class="modal container fade" tabindex="-1" style="display: none;">
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
  <h4 class="modal-title">${local["eaap.op2.portal.doc.message.edit"]}</h4>
</div>
<div class="modal-body"> 
<div class="form-group">
<label class="control-label" id="showAddressName">
<font color="FF0000">*</font>mjftestG:</label>
<input type="hidden" id="showAttrId">
<input type="text" id="showAttrSpecValue"  placeholder="Service Address"  maxlength="200" class="form-control" name="attrspecValue">
</div>
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>
  <button type="button" class="btn theme-btn" data-dismiss="modal" id="addressOK">${local["eaap.op2.portal.doc.message.ok"]}</button>
</div>
</div>
<!-- /Modal -->
<!-- Modal(offer) -->
<div id="offerSysModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.portal.doc.applyability"]}</h4>
  </div>
  <div class="modal-body">
      <form id="offerSysForm" name = "offerSysForm">
            <input type="hidden" id="capabilityCheckId" name ="capabilityCheckId"/>
            <input type="hidden" id="capabilityCheckName" name ="capabilityCheckName"  />
            <input type="hidden" id="offerId" name ="offerId"  />
            <input type="hidden" id="offerName" name ="offerName"  />
            <input type="hidden" id="componentIdforOffer" name ="componentIdforOffer" value="${componentId}" />
      </form>
<table class="table table-bordered table-striped table-hover text-center group-check3" id="offerSysDt">
      <thead>
        <tr>
          <th style="width:24px"><input type="checkbox" data-set=".group-check3 .checkboxes" class="group-checkable"  onchange='checkboxCtr(this)'></th>
          <th>${local["eaap.op2.portal.doc.fenType"]}</th>
          <th>${local["eaap.op2.portal.doc.applyabilityName"]}</th>
          <th>${local["eaap.op2.portal.doc.developMemo"]}</th>
        </tr>
      </thead>
      <tbody id="offerSysDtBody">
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button  type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>
    <button id = "btn-offerSys-Ok" type="button" class="btn theme-btn">${local["eaap.op2.portal.doc.message.ok"]}</button>
  </div>
</div>
<div id="modal4" class="modal container fade form-horizontal" tabindex="-1" style="display: none;"></div>
<div id="modal5" class="modal fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.doc.addPackage"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
      <div class="form-group">
        <label class="control-label col-md-3"><font color="FF0000">*</font>${local["eaap.op2.portal.doc.packageName"]}:</label>
        <div class="col-md-9">
          <input type="text" class="form-control" name="offerNameNew" id="offerNameNew">
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn default" type="button" data-dismiss="modal">${local["eaap.op2.portal.devmgr.cancel"]}</button>
    <button id="btn-add-offer" data-dismiss="modal" class="btn theme-btn" type="button">${local["eaap.op2.portal.doc.message.ok"]}</button>
  </div>
</div>
<!-- /Modal(offer) -->

<!-- /Modal(settle) -->
<div id="modal6" class="modal container fade form-horizontal" tabindex="-1" style="display: none;"></div>
<div id="modal7" class="modal container  form-horizontal" tabindex="-1" style="display: none;"></div>

<!-- /Modal(procces) -->
<div id="addProcess" class="modal  container" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">AddProcess</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
      <iframe name="addProcessIframe" id="addProcessIframe" src="" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 520px;" allowtransparency="true"></iframe>
      <input type="hidden" class="btn btn-default" id="closeButton" data-dismiss="modal"/>
    </div>
  </div>
</div>

</html>