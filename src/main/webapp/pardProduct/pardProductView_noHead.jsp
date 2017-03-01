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

<body class="container" id="infoModalList" class="modal container fade" aria-hidden="true">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h4 class="modal-title">${local["eaap.op2.portal.pardProd.prodDetail.basicInfo"] }</h4>
  </div>
  <div class="modal-body">
	<div class="panel panel-default ">
	    	<div class="panel-heading">
		      <div class="panel-body">
		        <form class="form-horizontal form-bordered form-row-stripped" method="post" role="form" id="productInfoForm" name="productInfoForm">
		          <div class="form-body">
		            <div class="form-group">
		              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.prodName"] }:</label>
		              <div class="col-md-8">
		                <input type="text" class="form-control input-xlarge" placeholder="Product Name" name="" id="productName" value="${product.productName }">
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-md-4 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.prodCode"] }:</label>
		              <div class="col-md-8">
		                <input type="text" class="form-control input-xlarge" placeholder="Product Code" name="extProdId" value="${product.extProdId }">
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"] }:</label>
		              <div class="col-md-8">
		                <textarea class="form-control" rows="3" name="productDesc">${product.productDesc }</textarea>
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-md-4 control-label"> <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you frist ,you need go to Provider Centers My System to establish Componets."></i> <font color="FF0000">*</font>${local["eaap.op2.portal.pardporduct.componentId"] }:</label>
		              <div class="col-md-6">
		                <select id="componentId" name="componentId" class="form-control input-large">
			                <c:if test="${not empty componentList }">
			                	<c:forEach var="componentVar" items="${componentList }">
			                		<option value="${componentVar.COMPONENT_ID }">${componentVar.NAME }</option>
			                	</c:forEach>
			                </c:if>
		                </select>
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-md-4 control-label"> <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="the service."></i> <font color="FF0000">*</font>${local["eaap.op2.portal.pardProdServAttr.servModule"] }:</label>
		              <div class="col-md-8">
		                <select id="serviceId" name="serviceId" class="form-control input-large">
		                	<option value=""></option>
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
		                <select id="dependentType" name="dependentType" class="form-control input-large">
			                <c:if test="${not empty dependentTypeList}">
			                	<c:forEach var="dependentType"  items="${dependentTypeList}">
			                		<option value="${dependentType.charSpecValueId}">${dependentType.displayValue}</option>
			                	</c:forEach>
			                </c:if>
		                </select>
		              </div>
		            </div>
	        		</c:if>
		            
		            <div class="form-group">
		              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProd.status"] }:</label>
		              <div class="col-md-8"  style="line-height:35px;">
		              		${productStateMap[product.statusCd] } 
		              </div>
		            </div>
		            <c:if test="${product.statusCd =='1298' || product.statusCd =='1278'}">
		            	<div class="form-group">
			              <label class="col-md-4 control-label">${local["eaap.op2.portal.pardProdServAttr.checkMsg"] }:</label>
			              <div class="col-md-8"  style="line-height:35px;color:blue;">
			              		${checkMsg }
			              </div>
			            </div>
		            </c:if>
		          </div>
		         <div class="form-group">
		        	<h4 class="form-section"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> ${local["eaap.op2.portal.pardProdServAttr.servAttr"] }</h4>
		              <div class="col-md-12">
		                <table class="table table-bordered table-condensed table-advance" id="serviceSpecTab">
		                  <thead>
		                    <tr>
		                      <th> <i class="" id="serviceAttributeModalTarget" ></i> </th>
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
		           </div>
		          <div class="form-group">
		             <h4 class="form-section"><i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="If you cannot find the attribute in the existing attribute list, you add a new one."></i> ${local["eaap.op2.portal.pardProdServAttr.prodAttr"] }</h4>
		              <div class="col-md-12">
		                <table class="table table-bordered table-condensed table-advance" id="productSpecTab">
		                  <thead>
		                    <tr>
		                      <th> </th>
		                     <th> ${local["eaap.op2.portal.pardSpec.customer"] } </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.name"] } </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.code"] } </th>
		                       <th> ${local["eaap.op2.portal.pardSpec.type"] } </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.note"] } </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.default"] } </th>
		                    </tr>
		                  </thead>
		                  <tbody>
		                  	<c:if test="${not empty prodAttrList }">
		                  		<c:forEach var="prodAttr" items="${prodAttrList }" varStatus="vs">
		                  			<tr id="tr_${prodAttr.ATTR_SPEC_ID}">
		                  			 <td>${vs.count }</td>
					                 <td>${attrSpecIsCustomizedMap[prodAttr.IS_CUSTOMIZED]}  </td>
					                 <td style="white-space: normal;">${prodAttr.CHAR_SPEC_NAME }
					                 	<input type='hidden' id='attrSpecId' value='${prodAttr.ATTR_SPEC_ID}' />
					                 	<input type='hidden' id='specType' value='${prodAttr.VALUE_TYPE}' />
					                 	 <c:if test="${prodAttr.PAGE_IN_TYPE==3}">
						                 	<input type='hidden' name='attrFrom' value='${prodAttr.VALUE_FROM}' />
						                 	<input type='hidden' name='attrTo' value='${prodAttr.VALUE_TO}' />
						                 </c:if>
					                 </td>
					                 <td style="white-space: pre-wrap;">${prodAttr.CODE }</td>
					                 <td>${attrSpecPageInTypeMap[prodAttr.VALUE_TYPE]}</td>
					                 <td>${prodAttr.DESCRIPTION }</td>
					                 <c:if test="${prodAttr.VALUE_TYPE =='4' }">
					                 	<td><a class="button-base button-small" 
						                 		onclick="javascript:handleSelect('${prodAttr.ATTR_SPEC_ID}','${prodAttr.PRODUCT_ATTR_ID}');" title="view">
						                 	<span class="button-text"> View</span></a></td>
					                 </c:if>
					                 <c:if test="${prodAttr.VALUE_TYPE==1 || prodAttr.VALUE_TYPE==2 || prodAttr.VALUE_TYPE==5 || prodAttr.VALUE_TYPE==6}">
					                 	<td>${prodAttr.DEFAULT_VALUE }</td>
					                 </c:if>
					             </tr> 
		                  		</c:forEach>
		                  	</c:if>
		                  </tbody>
		                </table>
		              </div>
		           </div>
		        </form>
		        <form class="form-horizontal" role="form" method="post" id="productForm" name="productForm">
			     	<div class="form-body"><div class="form-group"><div style="display:none;">
			      	<input type="text" id="productId" name="productId" value="${product.productId }"/>
			      	<input type="text" id="productName" name="productName" value="${product.productName }"/>
			      	<input type="text" id="statusCd" name="statusCd" value="${product.statusCd }"/>
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
		      </div>
	    	</div>
	</div>
  </div>
  
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
<script src="${APP_PATH }/pardProduct/viewPartnerProduct.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
	  App.init();  
	  var componentId = '${componentId}';
	  $('#componentId').val(componentId);
	  var serviceId = '${serviceId}';
	  $('#serviceId').val(serviceId);
	  $('#serviceId').change();
	  PardProductView.init(serviceId);  
	  var dependentType = '${dependentType}';
	  $('#dependentType').val(dependentType);      
 });
 
 function handleSelect(attrSpecId,prodAttrValId){
		attrValueTr='tr_'+attrSpecId;
		$('#productAttributeValueModal').modal('show');
		$('#productAttributeValueDT').dataTable().api().ajax.
			url('../pardSpec/attrSpecValuelist.shtml?objType=PRODUCT&objId='+prodAttrValId+'&attrSpecId='+attrSpecId).load();
	}
 
 function handleSelectServ(attrSpecId,servAttrValId){
	 $('#serviceSpecAttributeValueModal').modal('show');
	 $('#serviceSpecAttributeValueDT').dataTable().api().ajax.
		url('../pardSpec/attrSpecValuelist.shtml?objType=SERVICE&objId='+servAttrValId+'&attrSpecId='+attrSpecId).load();
}
 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
