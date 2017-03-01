<!DOCTYPE html>
<html lang="en">
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%@include file="../header.jsp" %>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/bootstrap-datepicker/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
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
        <h1>${local["eaap.op2.portal.pardSpec.attrSpec"] }</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a> </li>
          <li><a href="${APP_PATH }/partnerGuide.jsp">${local["eaap.op2.portal.index.pradIndex"] }</a> </li>
          <li class="active">${local["eaap.op2.portal.pardSpec.attrSpec"] }</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <div class="container">
  <div class="panel" style="margin-top:15px;">
	<form class="form-horizontal" role="form">
      <div class="form-body">
       <div class="form-group">
        <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.code"] }:</label>
        <div class="col-md-4" ><input type="text" class="form-control" placeholder="Attribute Code" id="featureCode"  name="code"></input></div>
         <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.name"] }:</label>
         <div class="col-md-4">
           <div>
           	<input type="text" class="form-control" placeholder="Attribute Name" id="featureName" name="name"></input>
          </div>
        </div>
       </div>
       <div class="form-group">
        <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.type"] }:</label>
        <div class="col-md-4">
         <div><select class="form-control" name="type" id="featureType">
            <optgroup><option value=""> </option>
            <c:forEach var="specType" items="${pardSpecTypeList }">
            	 <option value="${specType.cepCode }">${specType.cepName }</option>
            </c:forEach>
            </optgroup>
            </select>
          </div>
         </div>
         <label class="col-md-2 control-label">${local["eaap.op2.portal.pardSpec.customer"] }:</label>
            <div class="col-md-4">
               <select class="form-control" style="width:100%" name="isCustomized" id="isCustomized"><optgroup>
               		<option value=""> </option>
                    <c:forEach var="specCust" items="${pardSpecCustList }">
            	        <option value="${specCust.cepCode }">${specCust.cepName }</option>
                     </c:forEach>
                </optgroup></select>
         </div>
       </div>
      <div class="form-group">
        <label class="col-md-2 control-label"> ${local["eaap.op2.portal.pardSpec.maintenancec"] }:</label> 
        <div class="col-md-4">
        	<div class="input-icon"> <select class="form-control" style="width:100%" name="maintenancec" id="maintenancec">
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
	<button class="btn theme-btn" style="float:right;" id="bntPardSearch"> ${local["eaap.op2.portal.devmgr.query"] }</button>
</div></div>
  <!-- BEGIN CONTAINER -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="portlet box s-protlet-theme">
      <div class="portlet-title">
        <div class="caption"> ${local["eaap.op2.portal.pardSpec.attrSpec"] } </div>
        <div class="actions">
          <button class="btn default" id="btn-add"><i class="fa fa-plus"></i> Add </button>
          <button class="btn default" id="btn-edit"><i class="fa fa-edit"></i> Edit </button>
          <button class="btn default" id="btn-del"><i class="fa fa-trash-o"></i> Del </button>
        </div>
      </div>
      <div class="portlet-body">
        <table class="table table-sl table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="dt">
          <thead>
            <tr>
              <th> <input type="checkbox" class="group-checkable" data-set=".group-check .checkboxes"></th>
              <th> ${local["eaap.op2.portal.pardSpec.id"] } </th>
              <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
              <th> ${local["eaap.op2.portal.pardSpec.name"] }</th>
              <th> ${local["eaap.op2.portal.pardSpec.code"] }</th>
              <th> ${local["eaap.op2.portal.pardSpec.type"] }</th>
              <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <!-- END CONTENT --> 
</div>
<!-- END CONTAINER -->
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<jsp:include page="/footer.jsp"/>
<!-- END COPYRIGHT --> 
<!-- Load javascripts at bottom, this will reduce page load time --> 
<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) --> 
<!--[if lt IE 9]>
    <script src="${APP_PATH}/resources/plugins/respond.min.js"></script>  
    <![endif]--> 
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/pardSpec/productAttribute.js"></script> 
<script src="${APP_PATH}/pardSpec/productAttribute${localeName }.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
  App.init();
  ProductAttribute.init();
 });
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- MYModal -->
<div id="myModal" class="modal modal-lg fade" tabindex="-1" style="display: none;">
  <form id="form1" role="form" action="${APP_PATH}/pardSpec/addOrUpdate.shtml" method="POST">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
      <h4 class="modal-title"></h4>
    </div>
    <div style="display:none;">
     	<input type="hidden" id="attrSpecId" name="attrSpecId" value=""/>
		<input type="hidden" name="e_featureDefault" id="e_featureDefault"/>
		<input type="hidden" name="specMaintainType" id="specMaintainType" />
		<input type="hidden" name="mappingId" id="mappingId" />
		<input type="hidden" name="pageInType" id="pageInType" />
     </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.code"] }</label>
            <input type="text" class="form-control" name="code" id="code" placeholder="Attribute Code" required>
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label>
            <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="buttom" title='Please enter a property name variable that is consistent with the name of the hump.'></i>
            <font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.name"] }</label>
            <input type="text" class="form-control" name="name" id="name" placeholder="Attribute Name" required>
          </div>
        </div>
        <!--/span-->
      </div>
      <div class="row"  style="display:none;">
        <div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.minCharacterister"] }</label>
            <input type="text" class="form-control" name="min" id="min" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.maxCharacterister"] }</label>
            <input type="text" class="form-control" name="max" id="max" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
          </div>
        </div>
        <!--/span-->
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.customer"] }</label>
            <select class="form-control" name="customized" id="customized">
            	<c:forEach var="specCust" items="${pardSpecCustList }">
            		<option value="${specCust.cepCode }">${specCust.cepName }</option>
            	</c:forEach>
            </select>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.type"] }</label>
            <select class="form-control" name="type" id="type">
            	<c:forEach var="specType" items="${pardSpecTypeList }">
            		<option value="${specType.cepCode }">${specType.cepName }</option>
            	</c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="row" id="textDefaultValueDiv">
      	<div class="col-md-6">
          <div class="form-group">
            <label>${local["eaap.op2.portal.pardSpec.defaultValue"] }</label>
            <input type="text" class="form-control" placeholder="" name="textDefaultValue" id="textDefaultValue">
          </div>
        </div>
      </div>
      <div id="numberDefaultValueDiv">
      	<div class="row">
	      	<div class="col-md-6">
	          <div class="form-group">
	            <label><font color='FF0000'>*</font> ${local["eaap.op2.portal.pardSpec.minimum"] }</label>
	            <input type="text" class="form-control" name="featureMinimum" id="featureMinimum" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	          </div>
	        </div>
	        <div class="col-md-6">
	          <div class="form-group">
	            <label><font color='FF0000'>*</font> ${local["eaap.op2.portal.pardSpec.maximum"] }</label>
	            <input type="text" class="form-control" name="featureMaximum" id="featureMaximum" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	          </div>
	        </div>
      	</div>
      	<div class="row">
	      	<div class="col-md-6">
	          <div class="form-group">
	            <label>${local["eaap.op2.portal.pardSpec.defaultValue"] }</label>
	            <input type="text" class="form-control" name="num_featureDefault" id="num_featureDefault" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	          </div>
	        </div>
	    </div>
      </div>
      <div class="row" id="enmDefaultValueDiv">
      	<div class="col-md-6">
          <div class="form-group">
            <label><font color='FF0000'>*</font>${local["eaap.op2.portal.pardSpec.attrIsMulti"] }</label>
            <select class="form-control" name="isMulti" id="isMulti">
            	<option value="0">Yes</option>
            	<option value="1">No</option>
            </select>
          </div>
        </div>
      	<div class="col-md-12">
          <div class="form-group">
            <label>${local["eaap.op2.portal.pardSpec.attrVal"] }</label>
            <div id="enmDefaultValueDiv" class="panel-body">
            	<div class="form-group">
		         <div class="col-md-12">
		           <table class="table table-bordered table-striped table-condensed table-advance table-function" data-error-container="#form_2_services_error" id="eumTable">
		             <thead>
		               <tr>
		                 <th> <i class="fa fa-plus" id="specAddBTN"></i> </th>
		                 <th> ${local["eaap.op2.portal.pardSpec.showValue"] } </th>
		                 <th> ${local["eaap.op2.portal.pardSpec.key"] } </th>
		                 <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
		                 <th> ${local["eaap.op2.portal.pardSpec.operation"] } </th>
		               </tr>
		             </thead>
		             <tbody>
		             </tbody>
		           </table>
		       </div>
            </div>
          </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label>${local["eaap.op2.portal.pardSpec.note"] }</label>
            <textarea name="featureNote" id="featureNote" rows="3" class="form-control" id="remarks"></textarea>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.cancel"] }</button>
      <button type="submit" class="btn theme-btn" id="btn-add-ok">${local["eaap.op2.portal.manager.auth.save"] }</button>
      <button type="submit" class="btn theme-btn" id="btn-save">${local["eaap.op2.portal.manager.auth.update"] }</button>
    </div>
  </form>
</div>
<!-- /MYModal -->
</html>
