<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<head>
<%@include file="/header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<form id="showProdForm" name="showProdForm" method="post" id="showPordOfferForm" action="">
	 <input type="hidden"  id="prodOfferId" name="prodOffer.prodOfferId" value="${prodOffer.prodOfferId}"/>
	 <input type="hidden"   name="prodOffer.prodOfferName" value="${prodOffer.prodOfferName}"/>
	 <input type="hidden"   name="prodOffer.statusCd" value="${prodOffer.statusCd}"/>
	 <input type="hidden"   name="activity_Id" value="${activity_Id}"/>
	 <input type="hidden"   name="message.msgId" value="${message.msgId}"/>
</form>

<!-- END HEADER --> 
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>${local["eaap.op2.portal.price.baseInfo"]}</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml">${local["eaap.op2.portal.index.index"]}</a> </li>
          <li><a href="${APP_PATH}/pardOffer/toIndex.shtml">${local["eaap.op2.portal.pardOffer.offer"]}</a> </li>
          <li class="active">${local["eaap.op2.portal.pardProd.prodDetail.location"]}</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTENT -->
  <div class="container margin-bottom-40 margin-top-20">
    <div class="s-sidebar"> 
	    <c:choose>
	      <c:when test="${not empty prodOffer.logoFileId}">
	         <img id="offerImg" src="${APP_PATH}/fileShare/readImg.shtml?sFileId=${prodOffer.logoFileId}" alt="" style="width:250px;height:166px;"/>
	      </c:when>
	      <c:otherwise>
	         <img id="offerImg" src="${APP_PATH}/resources/img/pics/250x250.jpg" width="250" height="166" alt="">
	      </c:otherwise>
	    </c:choose> 
     <a href="#"   data-height="166" data-width="250" data-href="${APP_PATH}/ajax/imgupload.shtml" data-toggle="modal" class="s-pic-edit changeImg"> edit</a>
		<c:choose>
		     <c:when test="${prodOffer.statusCd =='1200'}">
		     	<button class="btn theme-btn btn-block" id="updateInfo">${local["eaap.op2.portal.devmgr.updateBasicInfo"]}</button>
				<button class="btn theme-btn btn-block" id="submitToCheck">${local["eaap.op2.portal.devmgr.submitToCheck"]}</button>
				<button class="btn theme-btn btn-block" id="linkDel">Delete</button>
		     </c:when>
			 <c:when test="${prodOffer.statusCd =='1000'}">
			 	<a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=OFFERTODO&lookFalg=&query=${prodOffer.prodOfferId}" target="_blank">Message History</a>
				<button class="btn theme-btn btn-block" id="offShelf">Off Shelf</button>
    		</c:when>
    		<c:when test="${prodOffer.statusCd =='1600'}">
    			<a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=OFFERTODO&lookFalg=&query=${prodOffer.prodOfferId}" target="_blank">Message History</a>
				<button class="btn theme-btn btn-block" id="onShelf">On Shelf</button>
    		</c:when>
    		<c:when test="${ prodOffer.statusCd =='1299' or prodOffer.statusCd =='1289' or prodOffer.statusCd =='1279' or prodOffer.statusCd =='1500'}">
    			<a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=OFFERTODO&lookFalg=&query=${prodOffer.prodOfferId}" target="_blank">Message History</a>
    		</c:when>
    		<c:when test="${prodOffer.statusCd =='1298' or prodOffer.statusCd =='1288' }">
    			<a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=OFFERTODO&lookFalg=&query=${prodOffer.prodOfferId}" target="_blank">Message History</a>
    			<button class="btn theme-btn btn-block" id="updateInfo">${local["eaap.op2.portal.devmgr.updateBasicInfo"]}</button>
    			<button class="btn theme-btn btn-block" id="submitToCheck">${local["eaap.op2.portal.devmgr.submitToCheck"]}</button>
    			<button class="btn theme-btn btn-block" id="linkDel">Delete</button>
    		</c:when>
    		<c:when test="${prodOffer.statusCd =='1278'}">
    			<a class="btn theme-btn btn-block showMessage" href="${APP_PATH }/messagePortal/goMessageList.shtml?msgType=3&msgQuertType=OFFERTODO&lookFalg=&query=${prodOffer.prodOfferId}" target="_blank">Message History</a>
    			<button class="btn theme-btn btn-block" id="submitToCheck">${local["eaap.op2.portal.devmgr.submitToCheck"]}</button>
    			<button class="btn theme-btn btn-block" id="updateInfo">${local["eaap.op2.portal.devmgr.updateBasicInfo"]}</button>
    		</c:when>
    		<c:otherwise></c:otherwise>
		</c:choose>
    </div>
    <!-- BEGIN -->    
    <div class="s-content-wrapper">
      <div class="s-content">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">${local["eaap.op2.portal.price.baseInfo"]}</h3>
          </div>
          <div class="panel-body">
            <form class="form-horizontal form-bordered form-row-stripped" role="form">
              <div class="form-body">
                <ol class="bwizard-steps clearfix mb20" role="tablist">
	            	<c:choose>
			        <c:when test="${prodOffer.statusCd =='1000'}">
			        	<li style="z-index: 4;" class="active"><span class="label">1</span>${local["eaap.op2.portal.pardOffer.addOffer"]}</li>
			        	<li style="z-index: 3;" class="active"><span class="label">2</span>${local["eaap.op2.portal.devmgr.developTest"]}</li>
			        	<li style="z-index: 2;" class="active"><span class="label">3</span>${local["eaap.op2.portal.devmgr.submitToCheck"]}</li>
			        	<li style="z-index: 1;" class="active"><span class="label">4</span>${local["eaap.op2.portal.pardOffer.onlineOffer"]}</li>
			         </c:when>
			        <c:when test="${prodOffer.statusCd =='1200' or prodOffer.statusCd =='1298' or prodOffer.statusCd =='1288' or prodOffer.statusCd =='1278'}">
			        	<li style="z-index: 4;" class="active"><span class="label">1</span>${local["eaap.op2.portal.pardOffer.addOffer"]}</li>
			        	<li style="z-index: 3;" class="active"><span class="label">2</span>${local["eaap.op2.portal.devmgr.developTest"]}</li>
			        	<li style="z-index: 2;"><span class="label">3</span>${local["eaap.op2.portal.devmgr.submitToCheck"]}</li>
			        	<li style="z-index: 1;"><span class="label">4</span>${local["eaap.op2.portal.pardOffer.onlineOffer"]}</li>
			         </c:when>
			          <c:otherwise>
			          	<li style="z-index: 4;" class="active"><span class="label">1</span>${local["eaap.op2.portal.pardOffer.addOffer"]}</li>
			        	<li style="z-index: 3;" class="active"><span class="label">2</span>${local["eaap.op2.portal.devmgr.developTest"]}</li>
			        	<li style="z-index: 2;" class="active"><span class="label">3</span>${local["eaap.op2.portal.devmgr.submitToCheck"]}</li>
			        	<li style="z-index: 1;"><span class="label">4</span>${local["eaap.op2.portal.pardOffer.onlineOffer"]}</li>
			          </c:otherwise>
			 		</c:choose>
          		</ol>
                <!-- BEGIN CONTAINER -->
	      <form id="submit_form" class="form-horizontal" action="#" novalidate>
	      <div class="form-wizard">
            <div class="form-body">
              <ul class="nav nav-pills nav-justified steps">
                <li onclick="$('#bar').find('.progress-bar').css('width','33.3333%')" data-visit='false' class='${isAdd==1?"":"active"}'> <a class="step" data-toggle="tab" href="#tab1"> <span class="number"> 1 </span> <span class="desc"> ${local["eaap.op2.portal.price.baseInfo"]} </span> </a> </li>
                <li onclick="$('#bar').find('.progress-bar').css('width','66.6666%')" data-visit='false' class='${isAdd==1?"active":""}'> <a class="step" data-toggle="tab" href="#tab2"> <span class="number"> 2 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.pricePlan"]} </span> </a> </li>
                <li onclick="$('#bar').find('.progress-bar').css('width','100%')" data-visit="false"> <a class="step active" data-toggle="tab" href="#tab3"> <span class="number"> 3 </span> <span class="desc"> ${local["eaap.op2.portal.pardOffer.account"]}</span> </a> </li>
              </ul>
              <div role="progressbar" class="progress progress-striped" id="bar">
                <div class="progress-bar progress-bar-success" style="width: 25%;"> </div>
              </div>
              <div class="tab-content">
                <div class="alert alert-danger display-none">
                  <button data-dismiss="alert" class="close"></button>
                  You have some form errors. Please check below. </div>
                <div class="alert alert-success display-none">
                  <button data-dismiss="alert" class="close"></button>
                  Your form validation is successful! </div>
                <div id="tab1" class='tab-pane ${isAdd==1?"":"active"} form-horizontal '>
                  <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerName"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="prodOfferName" name="prodOffer.prodOfferName" value="${prodOffer.prodOfferName}" disabled="disabled"/> </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label"><font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerCode"]}:</label>
	                  <div class="col-md-9"> <input type="text" class="form-control input-xlarge" placeholder="" id="productCode" name="prodOffer.extProdOfferId" value="${prodOffer.extProdOfferId}" disabled="disabled"/> </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.offerType"]} :</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <c:if test="${prodOffer.offerType==11}">
								<input type="radio" value="11" name="prodOffer.offerType" checked="checked" disabled="disabled"/>
								 <label class="checkbox-inline">${local["eaap.op2.portal.pardOffer.offerType_main"]} </label>
							</c:if> 
	                        <c:if test="${prodOffer.offerType==12}">
								<input type="radio" value="12" name="prodOffer.offerType" checked="checked" disabled="disabled"/>
								<label class="checkbox-inline">${local["eaap.op2.portal.pardOffer.offerType_AddOn"]} </label>
							</c:if> 
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div>
	                <c:choose>
				      <c:when test="${o2pCloudFlag=='cloud'}">
		                <div class="form-group">
		                	<label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.operate"]}:</label>
		                	<div class="col-md-6" class="input-group">
		                		<input type="text" data-hover="dropdown" name="operator"  id="operator"  placeholder="Select an Operator" data-id="opr" class="form-control" value="${operatorName}" />
								<input type="hidden"  id="operatorValue" name="prodOffer.operateCode" value="${prodOffer.operateCode}" />
		                		<div role="menu" class="dropdown-menu multiple-select-box"  id="operatorDropDown">
							      	<c:forEach var="countryObj" items="${countryAndOperatorList}">
									    <div class="multiple-select-group"> 
									    <span class="multiple-select-group-letter">${countryObj.country}</span> 
									    <c:forEach var="operatorObj" items="${countryObj.operatorMapList}">
									    	<a sdata="${operatorObj.ORGID}" onclick="changeOperatorValue(this)" href="javascript:;">${operatorObj.ORGUSERNAME}</a> 
									    </c:forEach>
									    </div>
							     	</c:forEach>
							  	 </div>
		                	</div>
						</div>
					  </c:when>
					</c:choose>
	                <div class="form-group">
                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardSpec.product"]}:</label>
		              <div class="col-md-9">
		                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
		                  <thead>
		                    <tr>
		                      <th> &nbsp; </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.provider"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
		                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
		                    </tr>
		                  </thead>
		                  <tbody id='productTB'>
		                  	<c:if test="${fn:length(offerProdRelList)>=1}">
    							<c:forEach var="prod" items="${offerProdRelList}" step="1" varStatus="status"> 
				             	<tr id="${prod.productId}">
				             		<td>${status.count}</td>
				             		<td>${prod.productProviderName}</td>
		                      		<td>${prod.extProdId}</td>
		                      		<td><a onclick="productDetailModal(${prod.productId})"> ${prod.productName} </a></td>
		                      		<td>${prod.minCount}</td>
		                      		<td>${prod.maxCount}</td>
		                    	</tr>
	                     		</c:forEach>
                			</c:if>	
                			<c:if test="${fn:length(offerProdRelList)<1}">
    							<tr>
			                      <td colspan="6">None</td>
			                    </tr>
                			</c:if>	
		                  </tbody>
		                </table>
				  	   </div>
	                </div>
	                <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.bundleOffer"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th> &nbsp; </th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.minimum"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.maximum"]} </th>
			                    </tr>
			                  </thead>
			                 <tbody id="offerTB">
			                  	<c:if test="${fn:length(prodOfferRelList1)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList1}" step="1" varStatus="status"> 
					             	<tr id="${offer.offerZId}">
					             		<td>${status.count}</td>
					             		<td>${offer.offerProvider}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td><a onclick="offerDetailModal(${offer.offerZId})"> ${offer.prodOfferName} </a></td>
			                      		<td>${offer.minCount}</td>
			                      		<td>${offer.maxCount}</td>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList1)<1}">
	    							<tr>
				                      <td colspan="6">None</td>
				                    </tr>
	                			</c:if>	
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                 <div class="form-group">
	                  	  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardOffer.exclusion"]}:</label>
			              <div class="col-md-9">
			                <table class="table table-bordered table-condensed table-advance table-function" data-error-container="#form_2_services_error">
			                  <thead>
			                    <tr>
			                      <th>&nbsp;</th>
		                      	  <th> ${local["eaap.op2.portal.pardSpec.offerProvider"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.code"]} </th>
			                      <th> ${local["eaap.op2.portal.pardSpec.name"]} </th>
			                    </tr>
			                  </thead>
			                  <tbody id="exclusionOfferTB">
			                  	<c:if test="${fn:length(prodOfferRelList2)>=1}">
	    							<c:forEach var="offer" items="${prodOfferRelList2}" step="1" varStatus="status"> 
					             	<tr id="${offer.offerZId}">
					             		<td>${status.count}</td>
					             		<td>${offer.offerProvider}</td>
					             		<td>${offer.extProdOfferId}</td>
			                      		<td><a onclick="offerDetailModal(${offer.offerZId})"> ${offer.prodOfferName} </a></td>
			                    	</tr>
		                     		</c:forEach>
	                			</c:if>	
	                			<c:if test="${fn:length(prodOfferRelList2)<1}">
	    							<tr>
				                      <td colspan="4">None</td>
				                    </tr>
	                			</c:if>	
			                  </tbody>
			                </table>
					  	   </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.prodDesc"]}:</label>
	                  <div class="col-md-9">  
						<textarea id="prodOfferDesc" name="prodOffer.prodOfferDesc" class="form-control" disabled="disabled">${prodOffer.prodOfferDesc}</textarea> 
					  </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-md-3 control-label">
	                  <i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title='${local["eaap.op2.portal.pardOffer.timeToOrderRemark"]}'></i>
	                  <font color='FF0000'>*</font>${local["eaap.op2.portal.pardOffer.timeToOrder"]}:</label>
	                  <div class="col-md-9"> 
	            		 <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
	                        <input type="text" style="width:120px" name="prodOffer.formatEffDate" class="form-control od" disabled="disabled" value="${prodOffer.formatEffDate}" />
	                        <span class="input-group-addon"> To </span>
	                        <input type="text"  style="width:120px"name="prodOffer.formatExpDate" class="form-control od" disabled="disabled" value="${prodOffer.formatExpDate}"/>
	                      </div>
	                      <div id="error-container"></div>
					  </div>
	                </div>
	              <!--   <div class="form-group">
	                    <label class="col-md-3 control-label"> <font color='FF0000'>*</font>${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                    <div class="col-md-9">
	                      <div class="checkbox-list" data-error-container="#here">
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,1)}">
								<input type="checkbox" value="1" name="channel" checked="checked" disabled="disabled"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,1))}">
	                          <input type="checkbox" value="1" name="channel" disabled="disabled"/>
	                        </c:if> 
	                          Third Party Partners </label>
	                        <label class="checkbox-inline">
	                        <c:if test="${fn:contains(FuncSeletedTreeMap.funcId,2)}">
								<input type="checkbox" value="2" name="channel" checked="checked" disabled="disabled"/>
							</c:if> 
							<c:if test="${!(fn:contains(FuncSeletedTreeMap.funcId,2))}">
	                          <input type="checkbox" value="2" name="channel" disabled="disabled"/>
	                        </c:if> 
	                          Operator </label>
	                      </div>
	                      <div id="here"></div>
	                    </div>
	                </div> -->
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.status"]}:</label>
	                  <div class="col-md-9"  style="line-height:30px;">
							<c:if test="${prodOffer.statusCd==1200}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1200"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1299}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1299"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1289}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1289"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1288}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1288"]}
		                 	</c:if>
		                 	 <c:if test="${prodOffer.statusCd==1287}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1287"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1279}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1279"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1277}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1277"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1500}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1500"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1278}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1278"]}
		                 	</c:if>
		                 	<c:if test="${prodOffer.statusCd==1298}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1298"]}
		                 	</c:if>
							<c:if test="${prodOffer.statusCd==1000}">
		                 		${local["eaap.op2.portal.pardProdOffer.status1000"]}
		                 	</c:if>
					  </div>
	                </div>	                            
	                <!-- 
	                <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.portal.pardProd.prodDetail.saleArea"]}:</label>
	                  <div class="col-md-9"> 
	                  	<div class="input-icon right input-large" > <i class="glyphicon glyphicon-tree-deciduous"></i>
				          <input type="text" class="form-control input-large" id="jstreeText" name="offerChannelStr" value="${FuncSeletedTreeMap.funcValue}" data-hover="dropdown" disabled="disabled">
				          <div class="dropdown-menu jstree" role="menu">
				            <div class="jstree"></div>
				          </div>
				        </div>
					  </div>
	                </div> -->
                </div>
                <div id="tab2" class="tab-pane ${isAdd==1?"active":""}">
                  <div class="form-horizontal tabbable-custom createArea">
	                  	<c:if test="${fn:length(pricingPlanList)>=1}">
	                  		<ul class="nav nav-tabs tab2_TabUL">
		  						<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status"> 
									<li class="${1==status.count?'active':''}">
										<a data-toggle="tab" onclick="javascript:loadEx_tab2('#tab2_${status.count-1}','${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?actionType=detail&prodIds=${prodOffer.prodOfferId}&pricingInfoId=${pricePlan.pricingInfoId}&pricingTargetId=${pricePlan.pricingTargetId}&applicType=1&formNum=${status.count-1}')" 
											 data-url="${APP_PATH}/pardOfferPricePlan/toPricePlan.shtml?actionType=update&prodIds=${prodOffer.prodOfferId}&pricingInfoId=${pricePlan.pricingInfoId}&pricingTargetId=${pricePlan.pricingTargetId}&applicType=1&formNum=${status.count-1}"
											 data-key="${status.count-1}" href="#tab2_${status.count-1}">${pricePlan.pricingName}</a>
										<input type="checkbox" name="select" value="#tab2_${status.count-1}"/>
									</li>
		                  		</c:forEach>
	                  		</ul>
	                  		<div class="tab-content tab2-content">
	                  			<c:forEach var="pricePlan" items="${pricingPlanList}" step="1" varStatus="status">
											<div id="tab2_${status.count-1}" class="tab-pane ${1==status.count?'active':''}" isLoad="0"><img src="${APP_PATH}/resources/img/input-spinner.gif" alt=""></div>
		                  		</c:forEach>
							</div>
	              		</c:if>	
	              		<c:if test="${fn:length(pricingPlanList)<1}">
	  							<div class="well">${local["eaap.op2.portal.pardOffer.noData"]}</div>
	              		</c:if>
                  </div>
                </div>
                <div id="tab3" class="tab-pane">
                  <input type="hidden" id="addSettlement" data-addTpl="${fn:length(settleList)>=1?true:false}" data-container=".tab3-content" data-draw="${fn:length(settleList)}" data-target="#tab3"/>
                  <div class="createArea2 form-horizontal tabbable-custom">
                  	<c:if test="${fn:length(settleList)>=1}">
                  		<ul class="nav nav-tabs fix tabs-content-ajax  tab3_TabUL">
	  						<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<li class="${fn:length(settleList)==status.count?'active':''}">
									<a data-toggle="tab" href="#tab3_${status.count-1}" 
									onclick="javascript:loadEx('#tab3_${status.count-1}','${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=detail&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}')"
									data-url="${APP_PATH}/pardOfferSettlement/toSettlement.shtml?actionType=detail&settleCycleDef.busiCode=${settle.BUSICODE}&settleSpBusiDef.servCode=${settle.SERVCODE}&settleSpBusiDef.busiId=${settle.BUSIID}&formNum=${formNum}_${status.count}">${settle.BUSINAME}</a>
									<input type="checkbox" name="select" value="#tab3_${status.count}"/>
								</li>
	                  		</c:forEach>
                  		</ul>
                  		<div class="tab-content tab3-content">
                  			<c:forEach var="settle" items="${settleList}" step="1" varStatus="status"> 
								<div id="tab3_${status.count-1}" class="tab-pane ${1==status.count?'active':''}" isLoad="${fn:length(settleList)==1?1:0}">
									<img src="${APP_PATH}/resources/img/input-spinner.gif" alt="">
								</div>
	                  		</c:forEach>
						</div>
              		</c:if>	
              		<c:if test="${fn:length(settleList)<1}">
  							<div class="well">${local["eaap.op2.portal.pardOffer.noData"]}</div>
              		</c:if>	
                  </div>
                </div>
              </div>
              <div class="form-actions fluid">
                <div class="col-md-offset-4"> 
                	<a class="btn default button-previous" href="${APP_PATH}/pardOffer/toIndex.shtml" id="backBtn"> <i class="m-icon-swapleft"></i> Back </a> 
                </div>
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
<!-- END CONTAINER -->
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<jsp:include page="/footer.jsp"/>
<!-- END COPYRIGHT --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="${APP_PATH}/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jstree/jstree.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bootstrap-editable/bootstrap-editable.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/scripts/app.js"></script> 
<script src="${APP_PATH}/pardOffer/pardOfferDetail.js"></script> 
<script src="${APP_PATH}/pardOfferPricePlan/pricePlanAdd.js"></script>
<script src="${APP_PATH}/pardOfferSettlement/pardOfferSettlement.js"></script> 
<script type="text/javascript">
 	jQuery(document).ready(function() {
  		App.init();
  		PardOfferDetail.init();
  		
  		$("#operatorDropDown>div").find("a").each(function(){
  			if($(this).attr("sdata") == $("#operatorValue").val()){
  				$(this).click();
  			}
  		});
 	});
 	
 	function productDetailModal(prodId){
		var url = APP_PATH + "/pardProduct/toDetail_noHead.shtml?product.productId="+prodId;
		$modal =  $('#productDetailModal');
	    $modal.load(url, '', function() {
	        $modal.modal('show');
	    });
	}
 	
 	function offerDetailModal(offerId){
		var url = APP_PATH + "/pardOffer/toDetail_noHead.shtml?prodOffer.prodOfferId="+offerId;
		$modal =  $('#offerDetailModal');
	    $modal.load(url, '', function() {
	        $modal.modal('show');
	    });
	}
 	
 	function uploadCallback(data){
 		 var imgId = data;
 		 if(imgId){
 			 $.ajax({
 			 	type: "POST",
 			 	async: false,
 			 	url: "${APP_PATH}/pardOffer/update.shtml", 
 			 	dataType:'json',
 			 	data:{
 			 		'prodOffer.prodOfferId':'${prodOffer.prodOfferId}',
 			 		logoFileId:imgId
 			 	},
 			 	success:function(data){ 
 			 		if (data.code == RESULT_OK) {
 			 			$('#offerImg').attr('src',APP_PATH+'/fileShare/readImg.shtml?sFileId='+imgId);
 			 		}else{
 			 			toastr.warning(); 
 			 		}
 			    }
 			});
 		} 
 	}
 	
 	function changeOperatorValue(obj){
		$(obj).toggleClass('select');
		if($(obj).hasClass('select')){
			$("#operator").val($(obj).html());
			$("#operatorValue").val($(obj).attr("sdata"));
		}else{
			$("#operator").val("");
			$("#operatorValue").val("");
		}
		
		$("#operatorDropDown a").each(function(index,element){
		    if($(element).text()!=$(obj).html()){
		    	$(element).removeClass("select");
		    }
	   })
	}
</script> 
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
<!-- product detail modal -->
<div id="productDetailModal" class="modal container fade" tabindex="-1" aria-hidden="true">
</div>
<!-- offer detail modal -->
<div id="offerDetailModal" class="modal container fade" tabindex="-1" aria-hidden="true">
</div>
</html>

