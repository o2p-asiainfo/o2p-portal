<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<%@include file="../header.jsp" %>
<head>
<link href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"></link>
   <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
   <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
   <link href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
    <!-- BEGIN THEME STYLES -->
    <link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/bootstrap-switch/bootstrap-switch.min.css" />
    <!-- BEGIN THEME STYLES -->
    <link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
    <link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
    <link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
    <link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
    <!-- END THEME STYLES -->
    <!-- END THEME STYLES -->
   <script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
   <script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
   <script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
   <script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
   <script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
   <script src="${APP_PATH}/resources/plugins/jquery.bootpag.js"></script>
   <script src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script>

<script src="${APP_PATH}/message/portalMessageList.js"></script>
<script src="${APP_PATH}/message/message_en_US.js"></script> 
   
</head>
<body class="page-header-fixed">
<div class="container" id="infoModalList" class="modal container fade" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X—</button>
    <h4 class="modal-title">${local["eaap.op2.portal.portalMessage.message"] }</h4>
  </div>
	<div class="modal-body">
	<!--BING SEACH-->
    <div class="search-form-default margin-top-10">
	      <form class="form-inline" action="#" id="msgListForm" name="msgListForm">
	      	<div style="display:none;">
	      		<input name="msgType" value="${msgType}" id="msgType"/>
	      		<input name="msgQuertType" value="${msgQuertType}" id="msgQuertType"/>
	      		<input name="lookFalg" value="${lookFalg}" id="lookFalg"/>
	      		<input name="pageNum" value="1" id="pageNum"/>
	      	</div>
	        <div class="col-md-3">
	        	<input class="form-control " type="text" placeholder="Enter text" name="query" value="${query}"/>
	        </div>
			<div class="col-md-3">
				<select class="js-example-basic-single" id="msgTypeSelect" style="width:100%;">
				<option value="">=ALL=</option>
				<option value="1">${local["eaap.op2.portal.portalMessage.webMsg"] }</option>
				<option value="2">${local["eaap.op2.portal.portalMessage.flowMsg"] }</option>
				<option value="3">${local["eaap.op2.portal.portalMessage.todoMsg"] }</option>
				</select>
			</div>
	            <button type="button" class="btn green" id="msgListFormSearch"><i class="fa fa-search"></i> ${local["eaap.op2.portal.portalMessage.btn.search"] }</button>  
	      </form>
	</div>
    <!--END SEACH-->
	<div class=" row">
		<div class="panel-heading ">
	    	<ul class="nav nav-tabs">
		        <li class="active"> <a data-toggle="tab" id="messageTab" href="#tab_5_1">${local["eaap.op2.portal.portalMessage.message"] }</a></li>
		        <li> <a data-toggle="tab" href="#tab_5_2" id="historyTab">${local["eaap.op2.portal.portalMessage.history"] }</a> </li>
		    </ul>
	    </div> 
		<div class="tab-content panel-body" id="messageList">
			<div class="tab-pane active" id="tab_5_1">
		    	<div class="panel panel-default">
		             <div class="msgContent"></div>
		        </div>         
		    </div>
		    <div class="tab-pane" id="tab_5_2">
	          <div class="panel panel-default">
		         <div class="msgHistory"></div>
		        </div> 
	        </div>
		</div>
	</div>
	<div class="msgPagination pull-right"></div>
   </div>
</div>
<form class="form-horizontal" role="form" method="post" id="messageForm" name="messageForm">
  <div class="form-body"><div class="form-group"><div style="display:none;">
	<input type="text" id="msgId" name="msgId" value=""/>
        </div></div>
   </div>
</form>
<!-- modal -->
<div id="showMessageModalByMsgId" class="modal container fade" tabindex="" aria-hidden="true">
</div>
</body>
<script type="text/javascript">
 $(document).ready(function() {
		setbootpag();
		settooptip();
		ajaxMsgData();
		$(".js-example-basic-single").select2();
		$('#msgTypeSelect').select2().val($("#msgType").val()).trigger("change");
		
		if("LOOKED"==$("#lookFalg").val()){
			$("#historyTab").click();
		}
		
 });
 
</script>



