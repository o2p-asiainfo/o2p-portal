<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<%@ include file="../taglibs.jsp"%>
<head>
   
	<link href="${APP_PATH }/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
	<!-- END GLOBAL MANDATORY STYLES -->
	
	<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
	<link href="${APP_PATH }/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/bootstrap-datepicker/datepicker.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/plugins/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
	<!-- END PAGE LEVEL PLUGIN STYLES -->
	
	<!-- BEGIN THEME STYLES -->
	<link href="${APP_PATH }/resources/css/style-template.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
	<link href="${APP_PATH }/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
	<link href="${APP_PATH }/resources/css/custom.css" rel="stylesheet" type="text/css" />
	<!-- END THEME STYLES -->
	
	<link rel="shortcut icon" href="favicon.ico" />
	<script src="${APP_PATH }/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/back-to-top.js" type="text/javascript" ></script> 
	<script src="${APP_PATH }/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" ></script> 
	<script src="${APP_PATH }/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script> 
	<script src="${APP_PATH }/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script> 
	
	<!-- END CORE PLUGINS --> 
	<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
	<script src="${APP_PATH }/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/plugins/Jcrop/jquery.Jcrop.min.js" type="text/javascript"></script> 
	<script src="${APP_PATH }/resources/scripts/app.js"></script> 
	<script src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script>
	
	<script src="${APP_PATH}/message/portalMessageCont.js" type="text/javascript"></script>
	
</head>
<body>
<div class="container" id="infoModalList" class="modal container fade" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.portal.portalMessage.message"] }</h4>
  </div>
	<div class="modal-body">
	<div class="panel panel-default ">
	    	<div class="panel-heading" align="center">
	        	<h2 class="panel-title">${message.msgTitle} </h2>
	        	<h4 class="panel-title">&nbsp;&nbsp;&nbsp;&nbsp;${message.msgSubtitle }</h4>
	        	<hr/>
	        	<div>
		        	<div class="pull-center">
		        		<span class="h5">Message Type:</span>  
		        		<span class="h4">
		        			<c:if test="${empty message.msgType and message.msgType==1}">${local["eaap.op2.portal.portalMessage.webMsg"] }</c:if>
		        			<c:if test="${message.msgType==2 }">${local["eaap.op2.portal.portalMessage.flowMsg"] }</c:if>
		        			<c:if test="${message.msgType==3 }">${local["eaap.op2.portal.portalMessage.todoMsg"] }</c:if>
		        		</span>
		        		&nbsp;&nbsp;&nbsp;
		        		<span class="h5">Message Originator:</span>  
		        		<span class="h4">${message.msgOriginator }</span>
		        		&nbsp;&nbsp;&nbsp;
		        		<span class="h5">Message Date:</span>  
		        		<span class="h4">${message.formatBeginDate }</span>
		        	</div>
	        	</div>
	        	
	         </div>
	         <div class="panel-body">
	         	<c:if test="${msgRec.statusCd==1700 and not empty message.msgHandleAddress}">
	         		<div class="text-right"><button class="btn btn-sm btn-warning" id="msgHandleBTN" onclick="msgHandleFunction('${message.msgHandleAddress}');"><span class="fa fa-chevron-circle-right"></span> Please handle the message </button></div>
	            </c:if>
	            <div><h3>${message.msgContent }<h3></div>
	           
	            <form class="form-horizontal margin-top-20" role="form" method="post" name="decisionForm" id="decisionForm" action="../messagePortal/submitMsgDecision.shtml">
			        <div class="form-body">
			          <div class="form-group">
			              <div class="input-icon" style="display: none;">
			                <input name="msgRecId" value="${msgRec.msgRecId }"></input>
			              </div>
			            <div class="">
			              <div class="input-icon">
			                <textarea name="msgDecision" cols="6" class="form-control" style="width:98%;" rows="5" title="Message Decision" >
			                	<c:out value="${msgRec.msgDecision }" escapeXml="false"></c:out>
			                </textarea>
			              </div>
			         </div>
			        </div>
			        <div class="form-group" align="center">
			        	<div class="">
			                  <button type="submit" class="btn green" id="decisionFormSubmitBtn">${local["eaap.op2.portal.portalMessage.btn.submit"] }</button>
			                  <button type="button" class="btn default" id="decisionFormCancelBtn">${local["eaap.op2.portal.portalMessage.btn.cancle"] }</button>
			           </div>
			        </div>
                   </div>
			    </form>	
	         </div> 
	  </div>
</div>
</div>
</body>