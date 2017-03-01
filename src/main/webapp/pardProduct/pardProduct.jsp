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
<link rel="stylesheet" type="text/css" href="${APP_PATH }/resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH }/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH }/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH }/resources/css/custom.css" rel="stylesheet" type="text/css" />
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
        <h1>${local["eaap.op2.portal.pardProd.prodAdd.location"] }</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH }/index.shtml">${local["eaap.op2.portal.index.index"] }</a> </li>
          <li><a href="${APP_PATH }/partnerGuide.jsp">${local["eaap.op2.portal.index.pradIndex"] }</a> </li>
          <li class="active">${local["eaap.op2.portal.pardProd.prodAdd.location"] } </li>
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
        <form class="form-horizontal form-bordered form-row-stripped" method="post" role="productForm" id="productForm" action="${APP_PATH }/pardProduct/addOrUpdate.shtml">
          <div style="display:none;">
	      	<input id="productId" name="productId" value="${product.productId }"/>
	      	<input id="subBusinessCodeInput" name="subBusinessCode" value=""/>
	      	<input id="subBusinessNameInput" name="subBusinessName" value=""/>
	      	<input id="attrSpecIdInput" name="attrSpecId" value=""/>
	      	<input id="specTypeInput" name="specType" value=""/>
	      	<input id="defaultValuesInput" name="defaultValues" value=""/>
	      	<input id="chooseAttrSpecCodeInput" name="chooseAttrSpecCodeInput" value=""/>
	      	<input id="crmVersion" name="crmVersion" value="${crmVersion}"/>
	      </div>
          <div class="form-body">
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.prodName"] }:</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="Product Name" name="productName" id="productName" value="${product.productName }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.prodCode"] }:</label>
              <div class="col-md-8">
                <input type="text" class="form-control input-xlarge" placeholder="Product Code" name="extProdId" id="extProdId" value="${product.extProdId }">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"] }:</label>
              <div class="col-md-8">
                <textarea class="form-control" rows="3" name="productDesc" >${product.productDesc }</textarea>
              </div>
            </div>
            
            <c:if test="${pageFlag=='add'}">
	            <div class="form-group">
	               <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.prodSystemSelect"]}:</label>
	               <div class="col-md-8">
	                 <div class="radio-list" data-error-container="#here">
	                     <label class="radio-inline"> 
	                     	<input type="radio" value="11" name="systemSelect" checked="checked" onchange="javascript:$('#componentIdDiv').show();$('#componentIdTextDiv').hide();"></input>
	                     	${local["eaap.op2.portal.pardProd.prodDetail.prodSystemSelectExisted"]} </label>
	                     <label class="radio-inline"> 
	                     	<input type="radio" value="12" name="systemSelect" onchange="javascript:$('#componentIdDiv').hide();$('#componentIdTextDiv').show();"></input>
	                     	${local["eaap.op2.portal.pardProd.prodDetail.prodSystemSelectNew"]} </label>
	                 </div>
	                 <div id="here"></div>
	               </div>
	            </div>
	        </c:if>
            <div class="form-group">
              <label class="col-md-4 control-label"> <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you frist ,you need go to Provider Centers My System to establish Componets."></i> <font color="FF0000">*</font>${local["eaap.op2.portal.pardporduct.componentId"] }:</label>
              <div class="col-md-8" id="componentIdDiv">
                <select id="componentId" name="componentId" class="js-example-basic-single" data-size="6" style="width:200px">
	                <c:if test="${not empty componentList }">
	                	<c:forEach var="componentVar" items="${componentList }">
	                		<option value="${componentVar.COMPONENT_ID }">${componentVar.NAME }</option>
	                	</c:forEach>
	                </c:if>
                </select>
              </div>
              <c:if test="${pageFlag=='add'}">
               	<div class="col-md-8" id="componentIdTextDiv" style="display: none">
                	<input type="text" class="form-control input-xlarge" placeholder='${local["eaap.op2.portal.pardporduct.componentId"]}' name="componentIdText" id="componentIdText" value="${product.productName }">
              	</div>
              </c:if>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label"> <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="the service."></i> ${local["eaap.op2.portal.pardProdServAttr.servModule"] }:</label>
              <div class="col-md-8">
                <select id="serviceId" name="serviceId" class="js-example-basic-single" data-size="6" style="width:200px">
	                <c:if test="${not empty serviceList }">
	                	<c:forEach var="service" items="${serviceList }">
	                		<option value="${service.serviceId }">${service.serviceName }</option>
	                	</c:forEach>
	                </c:if>
                </select>
              </div>
            </div>
            
            <c:if test="${isShowDependentType=='true' }">
            <div class="form-group">
              <label class="col-md-4 control-label"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="Dependent Type"></i> ${local["eaap.op2.portal.pardProdServAttr.dependentType"] }:</label>
              <div class="col-md-8">
                <select id="dependentType" name="dependentType" class="js-example-basic-single" data-size="6" style="width:200px">
	                <c:if test="${not empty dependentTypeList }">
	                	<c:forEach var="dependentType" items="${dependentTypeList }">
	                		<option value="${dependentType.charSpecValueId }"   isDefault="${dependentType.isDefault}">${dependentType.displayValue }</option>
	                	</c:forEach>
	                </c:if>
                </select>
              </div>
            </div>
	        </c:if>
            
            <!--  
            <div class="form-group">
              <label class="col-md-4 control-label">App Image:</label>
              <div class="col-md-6"> <a class="btn theme-btn btn-sm changeImg" href="#" data-height="166" data-width="250" data-href="${APP_PATH }/ajax/changeImg.html" data-toggle="modal">Upload <i class="fa fa-upload"></i></a> </div>
            </div>
            <div class="form-group">
              <label class="col-md-4 control-label"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you want to provide some channel regarding the content, you need to specify the different music in different channels."></i> ${local["eaap.op2.portal.pardporduct.subBusinessId"] }:</label>
              <div class="col-md-8">
                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error" id="subBusinessTable">
                  <thead>
                    <tr>
                      <th> <i class="fa fa-plus" id="subBusinessAddBTN"></i> </th>
                      <th> ${local["eaap.op2.portal.pardporduct.subBusinessCode"] } </th>
                      <th> ${local["eaap.op2.portal.pardporduct.subBusinessName"] } </th>
                      <th> Operation </th>
                    </tr>
                  </thead>
                  <tbody>
	                  <c:choose>
	                  	<c:when test="${fn:length(subBusinessCodes)>=1}">
	                  		<c:forEach var="subBusiness" items="${subBusinessCodes}" step="1" varStatus="xh"> 
				                 <tr>			
					                 <td>${xh.count}</td>
				                     <td><input type="text" class="form-control input-xs" placeholder="" id="subBusinessCode" value="${subBusiness.SUB_BUSINESS_CODE}" ></td>
				                     <td><input type="text" class="form-control input-xs" placeholder="" id="subBusinessName" value="${subBusiness.SUB_BUSINESS_NAME}" ></td>
				                     <td><a id="subBusinessCodeDelBTN" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>
					             </tr>  
		                    </c:forEach>
	                  	</c:when>
                      </c:choose>		
                  </tbody>
                </table>
                <div id="form_2_services_error"></div>
              </div>
            </div>
            -->
          <div class="form-group">
           <h4 class="form-section"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> ${local["eaap.op2.portal.pardProdServAttr.servAttr"] }</h4>      
                <table class="table table-bordered table-condensed table-advance" id="serviceSpecTab">
                  <thead>
                    <tr>
                      <th></th>
                      <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.name"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.code"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.type"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
                    </tr>
                  </thead>
                  <tbody>
                  </tbody>
                </table>
            </div>
            
            <div class="form-group">
            <h4 class="form-section"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> ${local["eaap.op2.portal.pardProdServAttr.prodAttr"] }</h4>
                <table class="table table-bordered table-condensed table-advance" id="productSpecTab" >
                  <thead>
                    <tr>
                      <th> <i class="fa fa-plus" id="productAttributeModalTarget" ></i> </th>
                      <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.name"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.code"] } </th>
                       <th> ${local["eaap.op2.portal.pardSpec.type"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
                      <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
                      <th> Operation </th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:if test="${not empty prodAttrList }">
                  		<c:forEach var="prodAttr" items="${prodAttrList }" varStatus="vs">
                  			<tr id="tr_${prodAttr.ATTR_SPEC_ID}">
                  			 <td>${vs.count }</td>
			                 <td>${attrSpecIsCustomizedMap[prodAttr.IS_CUSTOMIZED]}  </td>
			                 <td style="white-space: pre-wrap;">${prodAttr.CHAR_SPEC_NAME }</td>
			                 <td style="white-space: pre-wrap;">${prodAttr.CODE }<span style="display:none;">
			                 	<input type='hidden' id='attrSpecId' value='${prodAttr.ATTR_SPEC_ID}' />
			                 	<input type='hidden' id='specType' value='${prodAttr.VALUE_TYPE}' />
			                 	 <c:if test="${prodAttr.VALUE_TYPE==3}">
				                 	<input type='hidden' name='attrFrom' value='${prodAttr.VALUE_FROM}' />
				                 	<input type='hidden' name='attrTo' value='${prodAttr.VALUE_TO}' />
				                 </c:if>
				                 <input type='hidden' id='chooseAttrSpecCode' value='${prodAttr.CODE}' />
				                 </span></td>
			                 <td>${attrSpecPageInTypeMap[prodAttr.VALUE_TYPE]}</td>
			                 <td style="white-space: pre-wrap;">${prodAttr.DESCRIPTION }</td>
			                 <td>
				                 <c:if test="${prodAttr.VALUE_TYPE==4}">
				                 	<a class="button-base button-small" 
				                 		onclick="javascript:handleSelectByUpdate('${prodAttr.ATTR_SPEC_ID}','${prodAttr.PRODUCT_ATTR_ID}');" title="select">
				                 	<span class="button-text"> select</span></a>
				                 	<input name="defaultValue" id="defaultEum" value="${prodAttr.DEFAULT_VALUE }" type="hidden"/>
				                 </c:if>
				                  <c:if test="${prodAttr.VALUE_TYPE==3}">
				                 	<input name="defaultValue" id="defaultValueNumber" class="form-control input-xs" value="${prodAttr.DEFAULT_VALUE }" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				                 </c:if>
				                 <c:if test="${prodAttr.VALUE_TYPE==1 || prodAttr.VALUE_TYPE==2 || prodAttr.VALUE_TYPE==5 || prodAttr.VALUE_TYPE==6}">
				                 	<input name="defaultValue" id="defaultValueText" class="form-control input-xs" value="${prodAttr.DEFAULT_VALUE }" type="text" /> 
				                 </c:if>
			                 </td>
			                 <td><a class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> ${local["eaap.op2.portal.pardProd.prodAdd.linkDel"] } </a></td>
			             </tr> 
                  		</c:forEach>
                  	</c:if>
                  </tbody>
                </table>
             </div>
          <div class="form-actions text-center" style="padding-top:15px;">
              <button type="button" class="btn default product cancle">${local["eaap.op2.portal.devmgr.cancel"] }</button>
              <button type="submit" class="btn theme-btn">Save</button>
          </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- END CONTENT --> 
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
<script src="${APP_PATH }/resources/plugins/select2/select2.min.js"></script>
<script src="${APP_PATH }/resources/scripts/app.js"></script> 
<script src="${APP_PATH }/pardProduct/partnerProduct.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
	 $(".js-example-basic-single").select2();
	 var componentId = '${componentId}';
	 var temp = $("#componentId").find("option").eq(0).attr("value");
	 if(!componentId){
		 componentId = temp;
	 }
	 $('#componentId').select2().val(componentId).trigger("change");
	 
	 var serviceId = '${serviceId}';
	 var servTemp = $("#serviceId").find("option").eq(0).attr("value");
	 if(!serviceId){
		 serviceId = servTemp;
	 }
	 $('#serviceId').select2().val(serviceId).trigger("change");

	 var dependentType = '${dependentType}';
	 if(!dependentType){
		 var optionList =$("#dependentType").find("option");
		 for(var i=0; i<optionList.length; i++){
			 var opObj=$("#dependentType").find("option").eq(i);
			 if(opObj.attr("isDefault")=="1"){
				 dependentType=opObj.attr("value");
			 }
		 }
	 }
	 $('#dependentType').select2().val(dependentType).trigger("change");
	 	 
	 App.init();  
	 AddPartnerProduct.init();  
 });
 
function handleSelect(attrSpecId){
	attrValueTr='tr_'+attrSpecId;
	$('#productAttributeValueModal').modal('show');
	$('#productAttributeValueDT').dataTable().api().ajax.
		url('../pardSpec/attrSpecValuelist.shtml?objType=PRODUCT&objId=&attrSpecId='+attrSpecId).load();
}

function handleSelectServ(attrSpecId,servAttrValId){
	 $('#serviceSpecAttributeValueModal').modal('show');
	 $('#serviceSpecAttributeValueDT').dataTable().api().ajax.
		url('../pardSpec/attrSpecValuelist.shtml?objType=SERVICE&objId='+servAttrValId+'&attrSpecId='+attrSpecId).load();
}

function handleSelectByUpdate(attrSpecId,prodAttrValId){
	attrValueTr='tr_'+attrSpecId;
	$('#productAttributeValueModal').modal('show');
	$('#productAttributeValueDT').dataTable().api().ajax.
		url('../pardSpec/attrSpecValuelist.shtml?objType=PRODUCT&objId='+prodAttrValId+'&attrSpecId='+attrSpecId).load();
}
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- productModal -->
<div id="productAttributeModal" class="modal container fade" tabindex="" style="display: none;">
  <div class="modal-title" style="padding-top:15px;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">Select Product Attribute</h4>
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
          <th>${local["eaap.op2.portal.pardSpec.note"] }</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default cancelProductBtn">${local["eaap.op2.portal.devmgr.cancel"] }</button>
    <button type="submit" class="btn theme-btn okProductBtn" data-dismiss="modal">${local["eaap.op2.portal.pardProd.prodAdd.submit"] }</button>
  </div>
</div>
<!-- /productAttrValueModal -->
<div id="productAttributeValueModal" class="modal container fade" tabindex="" style="display: none;">
  <div class="modal-title" style="padding-top:15px;">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardProdServAttr.selProdAttrVal"] }</h4>
  </div>
  <div class="modal-body">
    <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="productAttributeValueDT">
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
<!-- /serviceSpecAttributeValueModal -->
<div id="serviceSpecAttributeValueModal" class="modal container fade" tabindex="" style="display: none;">
  <div class="modal-title"style="padding-top:15px;">
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
    <button type="button" class="btn btn-default cancelAttrValueBtn">${local["eaap.op2.portal.pardProdServAttr.close"] }</button>
  </div>
</div>
</html>
