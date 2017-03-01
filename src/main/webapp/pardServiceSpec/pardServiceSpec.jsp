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
        <h1>${local["eaap.op2.portal.pardServiceSpec.service"] }</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a> </li>
          <li><a href="${APP_PATH }/pardServiceSpec/serviceSpecIndex.shtml">${local["eaap.op2.portal.pardServiceSpec.service"] } </a></li>              
          <c:if test="${empty serviceSpec.serviceId}">
          	<li class="active">${local["eaap.op2.portal.manager.add"] }</li>
          </c:if>
          <c:if test="${not empty serviceSpec.serviceId}">
          	<li class="active">${local["eaap.op2.portal.manager.modify"] }</li>
          </c:if>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
   <div class="container">
    <div class="panel panel-default margin-bottom-40 margin-top-20">
      <div class="panel-heading">
        <h3 class="panel-title"></h3>
      </div>
      <div class="panel-body">
        <form class="form-horizontal form-bordered form-row-stripped" method="POST" role="serviceSpecInfoForm" id="serviceSpecInfoForm" name="serviceSpecInfoForm" action="${APP_PATH }/pardServiceSpec/serviceSpecAddOrUpdate.shtml">
          <div class="form-body">
          	<div class="form-group">
          		<div style="display:none;">
          			<input type="text" id="crmVersion" name="crmVersion" value="${crmVersion}"/>
			      	<input type="text" id="serviceSpecId" name="serviceId" value="${serviceSpec.serviceId }"/>
			      	<input type="text" id="params" name="params" value=""/>
   	        	</div>
   	        </div>
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardServiceSpec.servName"] }</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="ServiceSpec Name" name="serviceSpecName" id="serviceSpecName" value="${serviceSpec.serviceName }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardServiceSpec.servCode"] }</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="ServiceSpec Code" name="serviceSpecCode" id="serviceSpecCode" value="${serviceSpec.serviceCode }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"] }</label>
              <div class="col-md-8">
                <textarea class="form-control" rows="3" name="serviceSpecDesc">${serviceSpec.serviceDesc }</textarea>
              </div>
            </div>
          </div>
             <h4 class="form-section">
             	<i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> Attribute:</h4>
              <div class="col-md-12">
                <table class="table table-bordered table-condensed table-advance" id="serviceSpecTab">
                  <thead>
                    <tr>
                      <th> <i class="fa fa-plus" id="servSpecAttributeModalTarget" ></i></th>
                      <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.name"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.code"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.type"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.maintenancec"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
                      <th> Operation </th>
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
				                <input type='hidden' id='chooseAttrSpecCode' value='${servAttr.CODE}' />
				          		<input type='hidden' id='specMaintainType' value='${servAttr.SPEC_MAINTAIN_TYPE}' />
			                 </td>
			                 <td>${servAttr.CODE }</td>
			                 <td>${attrSpecPageInTypeMap[servAttr.VALUE_TYPE]}</td>
			                 <td>${specMaintainTypeMap[servAttr.SPEC_MAINTAIN_TYPE]}</td>
			                 <td>${servAttr.DESCRIPTION }</td>
			                 <c:if test="${servAttr.VALUE_TYPE =='4' }">
			                 	<td><a class="button-base button-small" 
				                 		onclick="javascript:handleSelect('${servAttr.CHAR_SPEC_ID}','${servAttr.SERVICESPECATTRID}');" title="select">
				                 	<span class="button-text"> Select</span></a>
				                 	<input name="defaultValue" id="defaultEum" value="${servAttr.DEFAULT_VALUE }" type="hidden"/>
				                </td>
			                 </c:if>
			                 <c:if test="${servAttr.VALUE_TYPE != '4'}">
			                 	<td><input name="defaultValue" id="defaultValueText" class="form-control input-xs" value="${servAttr.DEFAULT_VALUE }" type="text" /></td>
			                 </c:if>
			                 <td><a class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.pardProd.prodAdd.linkDel"] } </a></td>
			             </tr> 
                  		</c:forEach>
                  	</c:if>
                  </tbody>
                </table>
              </div>
              <div class="form-actions fluid">
	            <div class="col-md-offset-4">
	              <button type="button" class="btn default" id="serviceSpecCancle">${local["eaap.op2.portal.devmgr.cancel"] }</button>
	              <button type="submit" class="btn theme-btn">${local["eaap.op2.portal.manager.auth.save"] }</button>
	            </div>
	          </div>
        </form>
      </div></div>
   </div></div>

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
<script src="${APP_PATH }/pardServiceSpec/pardServiceSpec.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
	  App.init();  
	  AddPardServiceSpec.init();  
 });
 
 function handleSelect(attrSpecId,serivAttrValId){
	 attrValueTr='tr_'+attrSpecId;
	 $('#serviceSpecAttributeValueModal').modal('show');
	 $('#serviceSpecAttributeValueDT').dataTable().api().ajax.
		url('../pardSpec/attrSpecValuelist.shtml?objType=SERVICE&objId='+serivAttrValId+'&attrSpecId='+attrSpecId).load();
}
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- serviceSpecAttributeModal -->
<div id="serviceSpecAttributeModal" class="modal container fade" tabindex="" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardProdServAttr.servAttr"] }</h4>
    <hr/>
  <div class="panel-body">
    <form class="form-horizontal" role="form">
	     <div class="form-body">
	      <div class="form-group">
	       <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }</label>
	  <div class="col-md-3" ><input type="text" class="form-control" placeholder="Attribute Code" id="featureCode"  name="code"></input></div>
	   <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }</label>
	   <div class="col-md-3">
	     <div>
	     	<input type="text" class="form-control" placeholder="Attribute Name" id="featureName" name="name"></input>
	    </div>
	  </div>
	 </div>
	 <div class="form-group">
	  <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.type"] }</label>
	  <div class="col-md-3">
	   <div><select class="form-control" name="type" id="featureType" >
	      <optgroup><option value=""> </option>
	      <c:forEach var="specType" items="${pardSpecTypeList }">
	      	 <option value="${specType.cepCode }">${specType.cepName }</option>
	      </c:forEach>
	      </optgroup>
	      </select>
	    </div>
	   </div>
	   <label class="col-md-2 control-label"> ${local["eaap.op2.portal.pardSpec.maintenancec"] }</label> 
	  <div class="col-md-3">
	  	<div class="input-icon"> <select class="form-control" name="maintenancec" id="maintenancec">
	      <optgroup>
	      	<option value=""> </option>
	          <c:forEach var="mainType" items="${maintenancecTypeList }">
	    	        <option value="${mainType.cepCode }">${mainType.cepName }</option>
	           </c:forEach>
	      </optgroup>
	      </select>
	     </div>
        </div>
	 </div>
	</div>
	</form>	
    	<button type="button" style="float:right;" class="btn theme-btn queryAttrBtn">${local["eaap.op2.portal.devmgr.query"] }</button>
  </div></div>
  <div class="modal-body">
    <table class="table table-sl table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="dt1">
      <thead>
        <tr>
          <th><input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"></th>
          <th>${local["eaap.op2.portal.pardSpec.id"] }</th>
          <th>${local["eaap.op2.portal.pardSpec.customer"] }</th>
          <th>${local["eaap.op2.portal.pardSpec.name"] }</th>
          <th>${local["eaap.op2.portal.pardSpec.code"] }</th>
          <th> ${local["eaap.op2.portal.pardSpec.type"] }</th>
          <th> ${local["eaap.op2.portal.pardSpec.maintenancec"] }</th>
          <th>${local["eaap.op2.portal.pardSpec.note"] }</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default cancelServiceSpecBtn">${local["eaap.op2.portal.devmgr.cancel"] }</button>
    <button type="submit" class="btn theme-btn okServiceSpecBtn" data-dismiss="modal">${local["eaap.op2.portal.pardProd.prodAdd.submit"] }</button>
  </div>
</div>
<!-- /serviceSpecAttributeValueModal -->
<div id="serviceSpecAttributeValueModal" class="modal container fade" tabindex="" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardProdServAttr.viewServAttrVal"] }</h4>
  </div>
  <div class="modal-body">
    <table class="table table-sl table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="serviceSpecAttributeValueDT">
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
    <button type="submit" class="btn theme-btn okAttrValueBtn">${local["eaap.op2.portal.pardProd.prodAdd.submit"] }</button>
  </div>
</div>
</html>
