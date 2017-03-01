<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="../header.jsp" %>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
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
<style>
#productSpecTab td{
word-break:break-all; 
word-wrap:break-word;
}
</style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<div class="page-container"> 
<!-- BEGIN PAGE CONTAINER -->
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.pardProd.prodDetail.basicInfo"] }</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a></li>
          <li><a href="${APP_PATH }/pardServiceSpec/serviceSpecIndex.shtml">${local["eaap.op2.portal.pardServiceSpec.service"] } </a></li>           
          <li class="active">${local["eaap.op2.portal.manager.detail"] }</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
    <div class="container margin-bottom-40 margin-top-20">
    <div class="s-sidebar">
    	<c:choose>
	      <c:when test="${not empty serviceSpec.sFileId}">
	         <img id="sysImg" src="${APP_PATH}/provider/readImg.shtml?sFileId=${serviceSpec.sFileId}" width="250"  alt="">
	      </c:when>
	      <c:otherwise>
	         <img id="sysImg" src="${APP_PATH}/resources/img/pics/250x250.jpg" width="250" alt="">
	      </c:otherwise>
	    </c:choose>
	    <c:if test="${userAble =='Yes'}">
	    	<a href="#" data-height="166" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal" class="s-pic-edit changeImg"> ${local["eaap.op2.portal.doc.message.edit"]} </a>
	    </c:if>
		<c:if test="${serviceSpec.statusCd =='1200'}">
			<button type="submit" class="btn theme-btn btn-block" id="modifyServiceSpecBTN">Update Basic Information</button>
		</c:if>
		<c:if test="${serviceSpec.statusCd =='1200'}">
			<button type="submit" class="btn theme-btn btn-block" id="delServiceSpecBTN">${local["eaap.op2.portal.pardProd.prodAdd.linkDel"] }</button>
		</c:if>
    </div>
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">${local["eaap.op2.portal.pardProd.prodDetail.basicInfo"] }</h3>
      </div>
      <div class="panel-body">
        <form class="form-horizontal form-bordered form-row-stripped" method="post" role="form" id="serviceSpecInfoForm" name="serviceSpecInfoForm">
          <div class="form-body">
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardServiceSpec.servName"] }:</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="ServiceSpec Name" name="" id="serviceSpecName" value="${serviceSpec.serviceName }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardServiceSpec.servCode"] }:</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="ServiceSpec Code" name="serviceSpecCode" value="${serviceSpec.serviceCode }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"] }:</label>
              <div class="col-md-8">
                <textarea class="form-control" rows="3" name="serviceSpecDesc">${serviceSpec.serviceDesc }</textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.status"] }:</label>
              <div class="col-md-8"  style="line-height:35px;">
              		${serviceStateMap[serviceSpec.statusCd] } 
              </div>
            </div>
          </div>
        </form>
            <h4 class="form-section"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> Service Attribute:</h4>
              <div class="col-md-12">
                <table class="table table-bordered table-condensed table-advance" id="serviceSpecTab">
                  <thead>
                    <tr>
                      <th> </th>
                      <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.name"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.code"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.type"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.maintenancec"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:if test="${not empty serviceSpecAttrList }">
                  		<c:forEach var="servAttr" items="${serviceSpecAttrList }" varStatus="vs">
                  			<tr id="tr_${servAttr.CHAR_SPEC_ID}">
                  			 <td>${vs.count }</td>
			                 <td>${attrSpecIsCustomizedMap[servAttr.IS_CUSTOMIZED]}  </td>
			                 <td>${servAttr.CHAR_SPEC_NAME }
			                 	<input type='hidden' id='attrSpecId' value='${servAttr.CHAR_SPEC_ID}' />
			                 	<input type='hidden' id='specType' value='${servAttr.VALUE_TYPE}' />
			                 </td>
			                 <td>${servAttr.CODE }</td>
			                 <td>${attrSpecPageInTypeMap[servAttr.VALUE_TYPE]}</td>
			                 <td>${specMaintainTypeMap[servAttr.SPEC_MAINTAIN_TYPE]}</td>
			                 <td>${servAttr.DESCRIPTION }</td>
			                 <c:if test="${servAttr.VALUE_TYPE =='4' }">
			                 	<td><a class="button-base button-small" 
				                 		onclick="javascript:handleSelect('${servAttr.CHAR_SPEC_ID}','${servAttr.SERVICESPECATTRID}');" title="view">
				                 	<span class="button-text"> View</span></a></td>
			                 </c:if>
			                 <c:if test="${servAttr.VALUE_TYPE !='4' }">
			                 	<td>${servAttr.DEFAULT_VALUE }</td>
			                 </c:if>
			             </tr> 
                  		</c:forEach>
                  	</c:if>
                  </tbody>
                </table>
              </div>
        <form class="form-horizontal" role="form" method="post" id="serviceSpecForm" name="serviceSpecForm">
	     	<div class="form-body"><div class="form-group"><div style="display:none;">
	      	<input type="text" id="serviceSpecId" name="serviceId" value="${serviceSpec.serviceId }"/>
	      	<input type="text" id="serviceSpecName" name="serviceSpecName" value="${serviceSpec.serviceName }"/>
	      	<input type="text" id="statusCd" name="statusCd" value="${serviceSpec.statusCd }"/>
	      	<input type="hidden" name="activity_Id"  value="${activity_Id}"/>
   	        <input type="hidden" name="process_Id"  value="${process_Id}"/>
   	        <input type="hidden" name="message.msgId"  value="${message.msgId}"/>
   	        </div></div>
	      </div>
	   </form>
	   <div class="form-actions fluid">
	         <div class="col-md-offset-4">
				
	         </div>
       </div>
      </div></div></div></div>
    </div>
  <!-- END CONTENT --> 
</div>

<!-- modal -->
<div id="showMessageModal" class="modal container fade" tabindex="" aria-hidden="true">
</div>
<!-- END COPYRIGHT --> 
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH }/resources/plugins/respond.min.js"></script>  
    <![endif]--> 
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
<script src="${APP_PATH }/pardServiceSpec/pardServiceSpecView.js"></script> 
<script type="text/javascript">
	var _success='${local["eaap.op2.portal.doc.message.success"]}';
 jQuery(document).ready(function() {
	  App.init();  
	  PardServiceSpecView.init();  
 });
 function handleSelect(attrSpecId,serviceAttrValId){
		$('#serviceSpecAttributeValueModal').modal('show');
		$('#serviceSpecAttributeValueDT').dataTable().api().ajax.
			url('../pardSpec/attrSpecValuelist.shtml?objType=SERVICE&objId='+serviceAttrValId+'&attrSpecId='+attrSpecId).load();
	}
 function uploadCallback(data){
	 if('' != data){
		 var serviceSpecId = $('#serviceSpecId').val();
	     $('#sysImg').attr('src','../provider/readImg.shtml?sFileId='+data);
	     $.ajax({
	 		type: "POST",
	 		async:false,
	 	    url: "../pardServiceSpec/updatePic.shtml",
	 	    dataType:'json',
	 	    data:{serviceSpecId:serviceSpecId,sFileId:data},
	 		success:function(data){ 
	 			if (data.code == "0000") {
	 				toastr.success(_success);
	 			}else{
	 				toastr.success('Edit Fail');
	 			}
	           }
	       });
	 }
 }
</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<div id="serviceSpecAttributeValueModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardProdServAttr.viewServAttrVal"] }</h4>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="serviceSpecAttributeValueDT">
      <thead>
        <tr>
          <th></th>
         <th>${local["eaap.op2.portal.pardSpec.attrVlaId"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.attrId"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.showValue"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.key"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.attrVlaDesc"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.seqId"] } </th>
          <th>${local["eaap.op2.portal.pardSpec.attrIsDef"] } </th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default cancelAttrValueBtn">${local["eaap.op2.portal.devmgr.cancel"] }</button>
  </div>
</div>
</html>
