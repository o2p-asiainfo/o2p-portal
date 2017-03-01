<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" type="text/css" href="${APP_PATH}/resources/plugins/select2/select2.min.css" />
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
  <h4 class="modal-title">${doOperation}</h4>
</div>
<div class="modal-body">
<div id="part1">
<form action="#" method="post" id="serviceForm">
  <!-- BEGIN SECTION 1 -->
  <h4 class="form-section mt5">Part 1</h4>
<div class="row">
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.applyabilityName"]}</label>
      <input type="text" class="form-control" id="pageServiceName" name="pageServiceName" value="${userDefined.serviceName}" maxlength="50">
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.crontactCode"]}</label>
      <input type="text" class="form-control" id="pageProCode" name="pageProCode" value="${userDefined.contractCode}" maxlength="50">
    </div>
  </div>
</div>
<div class="row">
<div class="col-md-6">
  <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.crontactVersionCode"]}</label>
      <input type="text" class="form-control" id="pageProVersionCode" name="pageProVersionCode" value="${userDefined.contractVersionCode}"  maxlength="50">
    </div>
  </div>
  <div class="col-md-6">
	  <div class="form-group">
	      <label class="control-label"> <font color="FF0000">*</font>${local["eaap.op2.portal.doc.isPublic"]}</label>
	      <select class="form-control" id="isPublic" onchange="javascript:changePublic(this.value);">
	      	<option value="1">Yes</option>
	      	<option value="2">No</option>
	      </select>
	    </div>
	</div>
  </div>
<div class="row">
	<div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.selectDirectory"]}</label>
      <div id="publicPageSelectedDirDiv"> 
	      <select id="publicPageSelectedDir" class="js-example-basic-multiple" multiple="multiple" style="width: 100%">
	      	  <c:forEach var="obj" items="${publicDirectoryList}">
		          	<option value="${obj.DIRID}">${obj.DIRNAME}</option>
		      </c:forEach>
	      </select>
      </div>
      <select class="form-control" id="pageSelectedDir" name="pageSelectedDir" style="display: none;">
       <c:forEach var="lwm" items="${directoryList}" varStatus="vsw">
		    <c:choose>
				<c:when test="${userDefined.dirSerlistId == lwm.DIRID}">
					<option value="${lwm.DIRID}" selected>${lwm.DIRNAME}</option>
				</c:when>
				<c:otherwise>
					<option value="${lwm.DIRID}">${lwm.DIRNAME}</option>
				</c:otherwise>
			</c:choose>				                
		</c:forEach>
      </select>
    </div>
  </div>
</div>
<div class="row">
<div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.selectCommProcd"]}</label>
      <select class="form-control" id="pageCommPro" name="pageCommPro" onchange="javascript:changeCommPro(this.value);">
      <c:forEach var="lwm" items="${commProtocalList}" varStatus="vsw">
		    <c:choose>
				<c:when test="${userDefined.commProcd == lwm.COMMPROCD}">
					<option value="${lwm.COMMPROCD}" selected>${lwm.COMMPRONAME}</option>
				</c:when>
				<c:otherwise>
					<option value="${lwm.COMMPROCD}">${lwm.COMMPRONAME}</option>
				</c:otherwise>
			</c:choose>						                
		</c:forEach>
      </select>
    </div>
  </div>
  <%--
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> ${local["eaap.op2.portal.doc.serviceProvAddress"]}</label>
      <input type="text" class="form-control" id="pageServiceAdd" value='${userDefined.attrSpecValue}'>
    </div>
  </div>--%>
</div>
<%--动态属性展示 --%>
<div id="dyProperty">
<%-- 
<div class="row">
  <div class="col-md-12">
    <div class="form-group">
      <label class="control-label"> ${local["eaap.op2.portal.doc.serviceProvAddress"]}</label>
      <input type="text" class="form-control" id="pageServiceAdd" value='${userDefined.attrSpecValue}'>
    </div>
  </div>
</div>--%>
</div>
<%--/////动态属性展示 --%>
<div class="row">
  <div class="col-md-12">
    <div class="form-group">
      <label class="control-label"> ${local["eaap.op2.portal.doc.descr"]}</label>
      <textarea rows="3" class="form-control" id="pageDescription" name="pageDescription"  maxlength="1000">${userDefined.descriptor}</textarea>
    </div>
  </div>
</div>
</form>
<p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="PartSubmit">${local["eaap.op2.portal.doc.submit"]} </a></p>
</div>
<form action="#" method="post" id="reqForm">
<div id="part2">
<!-- END SECTION 1 --> 
  <!-- BEGIN SECTION 2 -->
  <h4 class="form-section mt5">Part 2 - ${local["eaap.op2.portal.doc.selectProcdFormat"]}</h4>
  <div class="" id="son">
    <ul class="nav nav-tabs fix">
      <li class="active"><a data-toggle="tab" href="#tab22">${local["eaap.op2.portal.doc.request"]}</a> </li>
      <li><a data-toggle="tab" href="#tab33">${local["eaap.op2.portal.doc.response"]}</a> </li>
    </ul>
    <div class="tab-content tab2-content ">
      <div id="tab22" class="tab-pane active">
        <div class="row" style="padding-left:0;">
          <div class="col-md-6">
            <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.selectProcdFormat"]}</label>
            <select class="form-control" id="pageReqProFormat" name="pageReqProFormat" onchange="javascript:changeReqValue(this.value);"> 
              <c:forEach var="lwm" items="${conTypeList}" varStatus="vsw">
		       <c:choose>
				<c:when test="${reqConType == lwm.CEPVALUES}">
					<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
				</c:when>
			    <c:otherwise>
				<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
			    </c:otherwise>
			   </c:choose>				                
		      </c:forEach>
            </select>
          </div>
          </div>
          <div class="col-md-6">
          <div class="form-group">
            <div>
            <label class="control-label">${local["eaap.op2.portal.doc.contractDocument"]}</label>
            </div>
            <iframe src="${APP_PATH}/provider/jumpfileUpload.shtml?type=req" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="230px" height="30px"/>
          </div>
        </div>
        </div>
        <div class="row" style="padding-left:0;">
         <div class="col-md-5">
            <div class="form-group">
             <label class="control-label"> ${local["eaap.op2.portal.doc.headFormat"]}</label>
              <textarea class="form-control" rows="3" id="pageReqHeadFormat" name="pageReqHeadFormat">${reqHeadXsdFormat}</textarea>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.procdBodyFormat"]} </label>
              <textarea class="form-control" rows="3" id="pageReqBodyFormat" name="pageReqBodyFormat">${reqXsdFormat}</textarea>
            </div>
          </div>
          <div class="col-md-1">
          <div class="form-group">
            <input type="button" value='${local["eaap.op2.portal.doc.message.import"]}'  id="pageReqImport" class="btn btn-default margin-top-50"/>
          </div>
        </div>
        </div>
        <div class="row" style="padding-left:0;">
         <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.procdDemo"]}</label>
              <textarea class="form-control" rows="3" id="pageReqSample" name="pageReqSample">${reqXsdSample}</textarea>
            </div>
          </div>

          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.descr"]} </label>
              <textarea class="form-control" rows="3" id="pageReqDescription" name="pageReqDescription">${reqDescript}</textarea>
            </div>
          </div>
        </div>
        <%-- 
        <p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="reqNodeListSubmit">Save All Node Information </a></p>
        --%>
        <h4 class="form-section"  style="margin:0 0 5px 0;">
              <div class="pull-right" style="margin-top:-5px;"><a class="btn default btn-sm"  onclick="javascript:reqRsp='REQ';reSetVal(); $('#javaFieldReqDiv').show();$('#javaFieldRspDiv').hide()"  data-toggle="modal" data-target="#nodeDescModal"><i class="fa fa-plus"></i> ${local["eaap.op2.portal.doc.add"]} </a> </div>
              <label class="control-label">Request Node Description</label>
        </h4>
        <div style="overflow-x:auto; width:1120px;">
        <table class="table table-bordered" id="pageReqTable">
          <thead>
            <tr>
            <th> ${local["eaap.op2.portal.doc.nodeName"]}</th>
            <th> ${local["eaap.op2.portal.doc.nodePath"]} </th>
            <th> ${local["eaap.op2.portal.doc.nodeType"]} </th>
            <th> ${local["eaap.op2.portal.doc.nodeLengthCons"]} </th>
            <th> ${local["eaap.op2.portal.doc.nodeTypeCons"]} </th>
            <th> ${local["eaap.op2.portal.doc.nodeNumberCons"]} </th> 
            <th> ${local["eaap.op2.portal.doc.nevlConsType"]} </th>
            <th> ${local["eaap.op2.portal.doc.isNeedCheck"]} </th>
            <th> ${local["eaap.op2.portal.doc.nevlConsValue"]} </th>
            <th> Java Field </th>
            <th> ${local["eaap.op2.portal.doc.operator"]}</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${reqNodeList}" var="obj" varStatus="status">
			   <tr>
			    <td>
			    ${obj.NODENAME}
			    <input type="hidden" name="nodeDescId" value="${obj.NODEDESCID}">
			    </td>
			    <td><c:out value="${obj.NODEPATH}"  escapeXml="true"/></td>
			    <td><select name='nodeType' id='nodeType'  class='form-control'>
			        <c:forEach var="lwm" items="${nodeTypeList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODETYPE == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><input type='text' class='form-control'  id='nodeLength'  name='nodeLength' value='${obj.NODELENGTHCONS}'></td>
			    <td><select name='nodeTypeCons' id='nodeTypeCons' class='form-control'>
			        <c:forEach var="lwm" items="${nodeTypeConsList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODETYPECONS == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='nodeNumber' id='nodeNumber' class='form-control'>
			        <c:forEach var="lwm" items="${nodeNumberConsList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODENUMBERCONS == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='nevlConsType' id='nevlConsType' class='form-control'>
						<option value=""></option>
			         <c:forEach var="lwm" items="${nevlConsTypeList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NEVLCONSTYPE == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='isNeedCheck' id='isNeedCheck' class='form-control'>
			        <c:forEach var="lwm" items="${selectStateList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.ISNEEDCHECK == lwm.CODE}">
							<option value="${lwm.CODE}" selected>${lwm.NAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CODE}">${lwm.NAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><input type='text' name='nelConsValue' class='form-control' value='${obj.NEVLCONSVALUE}'></td>
			    <td><select name='javaField' id='javaField' class='form-control'>
					  <option value=""></option>
			         <c:forEach var="lwm" items="${javaFieldReqList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.JAVAFIELD == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td  style="text-align:center; vertical-align: middle;">
				    <a href="#" id="reqDelete"  delId="${obj.NODEDESCID}" class="btn default btn-xs">${local["eaap.op2.portal.price.delete"]}</a>
			    </td>
			   </tr>
			   </c:forEach>
          </tbody>
        </table>
        </div>
      </div>
      <div id="tab33" class="tab-pane">
        <div class="row" style="padding-left:0;">
          <div class="col-md-6">
            <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.selectProcdFormat"]}</label>
            <select class="form-control" id="pageRspProFormat" name="pageRspProFormat" onchange="javascript:changeRspValue(this.value);">
              <c:forEach var="lwm" items="${conTypeList}" varStatus="vsw">
		         <c:choose>
				<c:when test="${rspConType == lwm.CEPVALUES}">
					<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
				</c:when>
			    <c:otherwise>
				<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
			    </c:otherwise>
			   </c:choose>					                
		      </c:forEach>
            </select>
          </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <div>
              <label class="control-label"> ${local["eaap.op2.portal.doc.contractDocument"]}</label>
              </div>
              <iframe src="${APP_PATH}/provider/jumpfileUpload.shtml?type=rsp" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="230px" height="30px"/>
            </div>
          </div>
        </div>
        <div class="row" style="padding-left:0;">
          <div class="col-md-5">
            <div class="form-group">
             <label class="control-label"> ${local["eaap.op2.portal.doc.headFormat"]}</label>
              <textarea class="form-control" rows="3" id="pageRspHeadFormat" name="pageRspHeadFormat">${rspHeadXsdFormat}</textarea>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.procdBodyFormat"]} </label>
              <textarea class="form-control" rows="3" id="pageRspBodyFormat" name="pageRspBodyFormat">${rspXsdFormat}</textarea>
            </div>
          </div>
          <div class="col-md-1">
          <div class="form-group">
            <input type="button" value='${local["eaap.op2.portal.doc.message.import"]}'  id="pageRspImport" class="btn btn-default margin-top-50"/>
          </div>
        </div>
        </div>
        <div class="row" style="padding-left:0;">
          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.procdDemo"]}</label>
              <textarea class="form-control" rows="3"  id="pageRspSample" name="pageRspSample">${rspXsdSample}</textarea>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> ${local["eaap.op2.portal.doc.descr"]} </label>
              <textarea class="form-control" rows="3" id="pageRspDescription" name="pageRspDescription">${rspDescript}</textarea>
            </div>
          </div>
        </div>
        <%-- 
        <p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="rspNodeListSubmit">Save All Node Information </a></p>
        --%>
        <h4 class="form-section"  style="margin:0 0 5px 0;">
              <div class="pull-right" style="margin-top:-5px;"><a class="btn default btn-sm"  onclick="javascript:reqRsp='RSP';reSetVal(); $('#javaFieldReqDiv').hide();$('#javaFieldRspDiv').show();" data-toggle="modal" data-target="#nodeDescModal"><i class="fa fa-plus"></i> ${local["eaap.op2.portal.doc.add"]} </a> </div>
              <label class="control-label">Response Node Description</label>
        </h4>
        <div style="overflow-x:auto; width:1120px;">
        <table class="table table-bordered" id="pageRspTable">
          <thead>
            <tr>
                <th> ${local["eaap.op2.portal.doc.nodeName"]}</th>
	            <th> ${local["eaap.op2.portal.doc.nodePath"]} </th>
	            <th> ${local["eaap.op2.portal.doc.nodeType"]} </th>
	            <th> ${local["eaap.op2.portal.doc.nodeLengthCons"]} </th>
	            <th> ${local["eaap.op2.portal.doc.nodeTypeCons"]} </th>
	            <th> ${local["eaap.op2.portal.doc.nodeNumberCons"]} </th> 
	            <th> ${local["eaap.op2.portal.doc.nevlConsType"]} </th>
	            <th> ${local["eaap.op2.portal.doc.isNeedCheck"]} </th>
	            <th> ${local["eaap.op2.portal.doc.nevlConsValue"]} </th>
            	<th> Java Field </th>
	            <th> ${local["eaap.op2.portal.doc.operator"]}</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${rspNodeList}" var="obj" varStatus="status">
			   <tr>
			    <td>
			    ${obj.NODENAME}
			    <input type="hidden" name="rspNodeDescId" value="${obj.NODEDESCID}">
			    </td>
			    <td><c:out value="${obj.NODEPATH}"  escapeXml="true"/></td>
			    <td><select name='rspNodeType' id='rspNodeType' class='form-control'>
			        <c:forEach var="lwm" items="${nodeTypeList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODETYPE == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><input type='text' class='form-control'  id='rspNodeLength'  name='rspNodeLength' value='${obj.NODELENGTHCONS}'></td>
			    <td><select name='rspNodeTypeCons' id='rspNodeTypeCons' class='form-control'>
			        <c:forEach var="lwm" items="${nodeTypeConsList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODETYPECONS == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='rspNodeNumber' id='rspNodeNumber' class='form-control'>
			        <c:forEach var="lwm" items="${nodeNumberConsList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NODENUMBERCONS == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='rspNevlConsType' id='rspNevlConsType' class='form-control'>
						<option value=""></option>
			         <c:forEach var="lwm" items="${nevlConsTypeList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.NEVLCONSTYPE == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><select name='rspIsNeedCheck' id='rspIsNeedCheck' class='form-control'>
			        <c:forEach var="lwm" items="${selectStateList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.ISNEEDCHECK == lwm.CODE}">
							<option value="${lwm.CODE}" selected>${lwm.NAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CODE}">${lwm.NAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td><input type='text' name='rspNelConsValue' class='form-control' value='${obj.NEVLCONSVALUE}'></td>
			    <td><select name='rspJavaField' id='rspJavaField' class='form-control'>
					  <option value=""></option>
			         <c:forEach var="lwm" items="${javaFieldRspList}" varStatus="vsw">
				       <c:choose>
						<c:when test="${obj.JAVAFIELD == lwm.CEPVALUES}">
							<option value="${lwm.CEPVALUES}" selected>${lwm.CEPNAME}</option>
						</c:when>
					    <c:otherwise>
						<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>
					    </c:otherwise>
					   </c:choose>				                
				      </c:forEach>
			        </select>
			    </td>
			    <td  style="text-align:center; vertical-align: middle;">
				    <a href="#" id="rspDelete"  delId="${obj.NODEDESCID}" class="btn default btn-xs">${local["eaap.op2.portal.price.delete"]}</a>
			    </td>
			   </tr>
			   </c:forEach>
          </tbody>
        </table>
        </div>
      </div>
    </div>
  </div>
  </div>
  <!-- END SECTION 2 --> 
</form>
</div>
<div class="modal-footer" style="display:none;" id="cumOperator">
<%-- 
  <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>--%>
  <button type="button" class="btn theme-btn" id="PartBace">${local["eaap.op2.portal.doc.message.back"]}</button>
  <button type="button" class="btn theme-btn" data-dismiss="modal" id="formOK">${local["eaap.op2.portal.doc.message.ok"]}</button>
</div>

<!-- Modal(Node Description Info): -->
<div id="nodeDescModal" class="modal container fade form-horizontal w700 "  tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">X</button>
    <h4 class="modal-title">Add Node Description</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
      <div class="form-group">
        <label class="control-label col-md-3"><font style="color:#FF0000;">*</font> ${local["eaap.op2.portal.doc.nodeName"]}:</label>
        <div class="col-md-9">
          <input type="text" class="form-control" name="nodeName" id="nodeName">
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nodePath"]}:</label>
        <div class="col-md-9">
          <input type="text" class="form-control" name="nodePath" id="nodePath">
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nodeType"]}:</label>
        <div class="col-md-9">
   			<select name='nodeType' id='nodeType' class='form-control'>
	        	<c:forEach var="lwm" items="${nodeTypeList}" varStatus="vsw">
				<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
		      </c:forEach>
	        </select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nodeLengthCons"]}:</label>
        <div class="col-md-9">
          		<input type="text" class="form-control" name="nodeLengthCons" id="nodeLengthCons">
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nodeTypeCons"]}:</label>
        <div class="col-md-9">
   			<select name='nodeTypeCons' id='nodeTypeCons' class='form-control'>
	        	<c:forEach var="lwm" items="${nodeTypeConsList}" varStatus="vsw">
				<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
		      </c:forEach>
	        </select>
        </div>
      </div>
      
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nodeNumberCons"]}:</label>
        <div class="col-md-9">
       		<select name='nodeNumber' id='nodeNumber' class='form-control'>
	        	<c:forEach var="lwm" items="${nodeNumberConsList}" varStatus="vsw">
					<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
		      	</c:forEach>
	        </select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nevlConsType"]}:</label>
        <div class="col-md-9">
			<select name='nevlConsType' id='nevlConsType' class='form-control'>
				<option value=""></option>
				<c:forEach var="lwm" items="${nevlConsTypeList}" varStatus="vsw">
					<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
			    </c:forEach>
			</select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.isNeedCheck"]}:</label>
        <div class="col-md-9">
			<select name='isNeedCheck' id='isNeedCheck' class='form-control'>
				<c:forEach var="lwm" items="${selectStateList}" varStatus="vsw">
						<option value="${lwm.CODE}">${lwm.NAME}</option>	                
				</c:forEach>
			 </select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-md-3">${local["eaap.op2.portal.doc.nevlConsValue"]}:</label>
        <div class="col-md-9">
          <input type="text" class="form-control" name="nevlConsValue" id="nevlConsValue">
        </div>
      </div>
      
      <div class="form-group"  id="javaFieldReqDiv"  style="display:none;">
        <label class="control-label col-md-3">Java Field:</label>
        <div class="col-md-9">
			<select name='javaFieldReq' id='javaFieldReq' class='form-control'>
				<option value=""></option>
				<c:forEach var="lwm" items="${javaFieldReqList}" varStatus="vsw">
					<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
			    </c:forEach>
			</select>
        </div>
      </div>
      <div class="form-group"  id="javaFieldRspDiv"  style="display:none;">
        <label class="control-label col-md-3">Java Field:</label>
        <div class="col-md-9">
			<select name='javaFieldRsp' id='javaFieldRsp' class='form-control'>
				<option value=""></option>
				<c:forEach var="lwm" items="${javaFieldRspList}" varStatus="vsw">
					<option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>			                
			    </c:forEach>
			</select>
        </div>
      </div>
      
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn default" type="button" data-dismiss="modal">${local["eaap.op2.portal.devmgr.cancel"]}</button>
    <button id="btn-add-nodeDesc"  onclick="addNodeDesc()"  class="btn theme-btn" type="button">${local["eaap.op2.portal.doc.message.ok"]}</button>
  </div>
</div>
<!-- /Modal(Node Description Info)-->
</html>
<script src="${APP_PATH}/resources/plugins/select2/select2.min.js"></script>
<script>
var datadeleteSuccess = '${local["eaap.op2.portal.doc.message.deletesuccess"]}';
$(function(){
	$("#part1").show();
	$("#part2").hide();
	$(".js-example-basic-multiple").select2();
	var flag ='${updateFlag}';
	if('update' == flag){  
		$('#pageProCode').attr('readonly',true);
		$('#pageProVersionCode').attr('readonly',true);
		$('#hidContractId').val('${userDefined.contractId}');
		$('#hidDirSerlistId').val('${userDefined.dirSerlistId}');
		$('#hidServiceId').val('${userDefined.serviceId}');
		$('#hidTechImpAttId').val('${userDefined.techImpAttId}');
		$('#hidTechimplId').val('${userDefined.techImpId}');
		$('#hidContractVersionId').val('${userDefined.contractVersionId}');
		$('#hidTcpCtrFId').val('${reqTcpCrtFid}');
		$('#hidRspTcpCtrFId').val('${rspTcpCrtFid}');
		$('#isPublic').val('${userDefined.isPublic}');
		changePublic('${userDefined.isPublic}'); 
		if('${userDefined.isPublic}' == '1'){
			var directoryIdArray = new Array();
			var directoryIdStr = '${userDefined.directoryId}';
			directoryIdArray = directoryIdStr.split(",");
			$(".js-example-basic-multiple").val(directoryIdArray).trigger("change");
		}
	}else{
		$('#hidContractId').val('');
		$('#hidDirSerlistId').val('');
		$('#hidServiceId').val('');
		$('#hidTechImpAttId').val('');
		$('#hidTechimplId').val('');
		$('#hidContractVersionId').val('');
		$('#hidTcpCtrFId').val('');
		$('#hidRspTcpCtrFId').val('');
	}
	showProperty($('#pageCommPro').val());
	$("#PartSubmit").click(function(){
		var componentId = $('#hidComponentId').val();
		var publicPageDirSerlistId = $('#publicPageSelectedDir').val(); 
		var pageSelectedDir = $('#pageSelectedDir').val();
		var isPublic = $('#isPublic').val();
		if(isPublic == '1'){
			if(!publicPageDirSerlistId){
				toastr.error('${local["eaap.op2.portal.doc.directoryIsMust"]}');
				return;
			}
		}
		//服务地址
		var serviceAddress=$("#serviceAddressValue").val();
		if(serviceAddress==""){
			toastr.error('${local["eaap.op2.portal.doc.serviceAddressIsMust"]}');
			return;
		}
		/*var pageServiceName = $('#pageServiceName').val();
		var pageProCode = $('#pageProCode').val();
		var pageProVersionCode = $('#pageProVersionCode').val();
		var pageCommPro = $('#pageCommPro').val();
		var pageServiceAdd = $('#pageServiceAdd').val();
		var pageDescription = $('#pageDescription').val();*/
		//修改操作参数
		var pageContractId = $('#hidContractId').val();
		var pageDirSerlistId = $('#hidDirSerlistId').val();
		var pageServiceId = $('#hidServiceId').val();
		var pageTechImpAttId = $('#hidTechImpAttId').val();
		var pageTechimplId = $('#hidTechimplId').val();
		var pageContractVersionId = $('#hidContractVersionId').val();
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/addProvSev.shtml?componentId="
		    		+componentId+"&pageContractId="+pageContractId+"&pageDirSerlistId="+pageDirSerlistId+"&pageServiceId="
		    		+pageServiceId+"&pageTechImpAttId="+pageTechImpAttId+"&pageTechimplId="+pageTechimplId
		    		+"&pageContractVersionId="+pageContractVersionId+"&pageSelectedDir="+pageSelectedDir+"&publicPageSelectedDir="+publicPageDirSerlistId+"&isPublic="+isPublic+"&pageServiceAdd="+serviceAddress,
		    dataType:'json',
		    data:$('#serviceForm').serialize(),
			success:function(data){ 
				if (data.code == "0000") {
					$('#hidContractId').val(data.contractId);
					$('#hidDirSerlistId').val(data.dirSerlistId);
					$('#hidServiceId').val(data.serviceId);
					$('#hidTechImpAttId').val(data.techImpAttId);
					$('#hidTechimplId').val(data.techimplId);
					$('#hidContractVersionId').val(data.contractVersionId);

					$('#hidTcpCtrFId').val(data.reqTcpCtrFId);
					$('#hidRspTcpCtrFId').val(data.rspTcpCtrFId);
					
					$('#pageProCode').attr('readonly','true');
					$('#pageProVersionCode').attr('readonly','true');
					toastr.success('${local["eaap.op2.portal.doc.message.success"]}');
					$('#cumOperator').show();
					$("#part1").hide();
					$("#part2").show();
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}else {
					if(undefined != data.desc.serviceName){
						toastr.error('${local["eaap.op2.portal.doc.serverName"]} '+data.desc.serviceName);
					}
					if(undefined != data.desc.contractCode){
						toastr.error('${local["eaap.op2.portal.doc.crontactCode"]} '+data.desc.contractCode);
					}
					if(undefined != data.desc.contractVersionCode){
						toastr.error('${local["eaap.op2.portal.doc.crontactVersionCode"]} '+data.desc.contractVersionCode);
					}
					if(undefined != data.desc.attrSpecValue){
						toastr.error('${local["eaap.op2.portal.doc.serviceProvAddress"]} '+data.desc.attrSpecValue);
					}
					if(undefined != data.desc.descriptor){
						toastr.error('${local["eaap.op2.portal.doc.descr"]} '+data.desc.descriptor);
					}
				}
	          }
	      });
		});
	$("#PartBace").click(function(){
		$("#part2").hide();
		$("#part1").show();
		$('#cumOperator').hide();
		});
	$('#pageReqImport').click(function(){
		var pageReqHeadFormat = $('#pageReqHeadFormat').val();
		var pageReqBodyFormat = $('#pageReqBodyFormat').val();
		if('' == pageReqHeadFormat&&'' == pageReqBodyFormat){
			toastr.error('${local["eaap.op2.portal.doc.procdBodyFormat"]}  '+$.i18n.prop('eaap.op2.portal.doc.message.notEmpty'));
			return false;
		}
		var pageContractVersionId = $('#hidContractVersionId').val();
		var pageReqProFormat = $('#pageReqProFormat').val();
		var pageReqSample = $('#pageReqSample').val();
		var pageReqDescription = $('#pageReqDescription').val();
		var pageReqRsp = 'REQ';
		var pageTcpCtrFId = $('#hidTcpCtrFId').val();
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/addConFormat.shtml",
		    dataType:'json',
		    data:{
		    	pageReqHeadFormat:pageReqHeadFormat,
		    	pageContractVersionId:pageContractVersionId,
		    	pageReqProFormat:pageReqProFormat,
		    	pageReqBodyFormat:pageReqBodyFormat,
		    	pageReqSample:pageReqSample,
		    	pageReqDescription:pageReqDescription,
		    	pageReqRsp:pageReqRsp,
		    	pageTcpCtrFId:pageTcpCtrFId
		    	},
			success:function(data){ 
				if (data.code == "0000") {
					$('#hidTcpCtrFId').val(data.hidTcpCtrFId);
					$('#pageReqTable>tbody').empty();
					$('#pageReqTable>tbody').append(data.result);
					toastr.success('${local["eaap.op2.portal.doc.message.importsuccess"]}');
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	
	$('#pageRspImport').click(function(){
		var pageRspHeadFormat = $('#pageRspHeadFormat').val();
		var pageContractVersionId = $('#hidContractVersionId').val();
		var pageRspProFormat = $('#pageRspProFormat').val();
		var pageRspBodyFormat = $('#pageRspBodyFormat').val();
		var pageRspSample = $('#pageRspSample').val();
		var pageRspDescription = $('#pageRspDescription').val();
		var pageReqRsp = 'RSP';
		var pageTcpCtrFId = $('#hidRspTcpCtrFId').val();
		if('' == pageRspHeadFormat&&'' == pageRspBodyFormat){
			toastr.error('${local["eaap.op2.portal.doc.procdBodyFormat"]}'+$.i18n.prop('eaap.op2.portal.doc.message.notEmpty'));
			return false;
		}
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/addRspConFormat.shtml",
		    dataType:'json',
		    data:{
		    	pageRspHeadFormat:pageRspHeadFormat,
		    	pageContractVersionId:pageContractVersionId,
		    	pageRspProFormat:pageRspProFormat,
		    	pageRspBodyFormat:pageRspBodyFormat,
		    	pageRspSample:pageRspSample,
		    	pageRspDescription:pageRspDescription,
		    	pageReqRsp:pageReqRsp,
		    	pageTcpCtrFId:pageTcpCtrFId
		    	},
			success:function(data){ 
				if (data.code == "0000") {
					$('#hidRspTcpCtrFId').val(data.hidTcpCtrFId);
					$('#pageRspTable>tbody').empty();
					$('#pageRspTable>tbody').append(data.result);
					toastr.success('${local["eaap.op2.portal.doc.message.importsuccess"]}');
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	
	
	});
	
	function changePublic(value){
		$('.js-example-basic-multiple').val(null).trigger("change");
		if(value == 1){
			$('#publicPageSelectedDirDiv').show();
			$('#pageSelectedDir').hide();
		}else if(value == 2){
			$('#pageSelectedDir').show();
			$('#publicPageSelectedDirDiv').hide();
		}
	}
	function changeReqValue(value){
		if(1 == value||2 == value||3 == value||10 == value){
			$('#pageReqImport').show();
		}else{
			$('#pageReqImport').hide();
		}
	}
	function changeRspValue(value){
		if(1 == value||2 == value||3 == value||10 == value){
			$('#pageRspImport').show();
		}else{
			$('#pageRspImport').hide();
		}
	}

	function changeCommPro(value){
		showProperty(value);
	}
	function showProperty(value){
		var pageTechimplId = $('#hidTechimplId').val();
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/showProperty.shtml",
		    dataType:'json',
		    data:{pageTechimplId:pageTechimplId,
		    	  pageCommPro:value
		    	},
			success:function(data){ 
				$('#dyProperty').empty();
				$('#dyProperty').append(data.property);
	          }
	      });
	}

		var nodeTypeList ="${nodeTypeList}";
		var nodeTypeConsList ="${nodeTypeConsList}";
		var nodeNumberConsList ="${nodeNumberConsList}";
		var nevlConsTypeList ="${nevlConsTypeList}";
		var selectStateList ="${selectStateList}";
		var javaFieldReqList 	="${javaFieldReqList}";
		var javaFieldRspList	="${javaFieldRspList}";
		var reqRsp = "REQ";
	    function addNodeDesc(){
	    	var nodeName 			= $("#nodeDescModal").find("#nodeName").val();
	    	var nodePath 			= $("#nodeDescModal").find("#nodePath").val();
	    	var nodeType 			= $("#nodeDescModal").find("#nodeType").val();
	    	var nodeLengthCons	= $("#nodeDescModal").find("#nodeLengthCons").val();
	    	var nodeTypeCons		= $("#nodeDescModal").find("#nodeTypeCons").val();
	    	var nodeNumber 		= $("#nodeDescModal").find("#nodeNumber").val();
	    	var nevlConsType 		= $("#nodeDescModal").find("#nevlConsType").val();
	    	var isNeedCheck 		= $("#nodeDescModal").find("#isNeedCheck").val();
	    	var nevlConsValue 	= $("#nodeDescModal").find("#nevlConsValue").val();
	    	var javaFieldReq		 	= $("#nodeDescModal").find("#javaFieldReq").val();
	    	var javaFieldRsp		 	= $("#nodeDescModal").find("#javaFieldRsp").val();
	    	var tcpCtrFId ="";
	    	var javaField="";
	    	var javaFieldList="";
	    	if(nodeName==""){
				toastr.error('${local["eaap.op2.portal.doc.nodeName"]} ' +'${local["eaap.op2.portal.required"]}');
	    		return;
	    	}
	  		if(reqRsp=="REQ"){
				tcpCtrFId = $("#hidTcpCtrFId").val();
				javaField=javaFieldReq;
				javaFieldList=javaFieldReqList;
	  		}else if(reqRsp=="RSP"){
	  			tcpCtrFId = $("#hidRspTcpCtrFId").val();
				javaField=javaFieldRsp;
				javaFieldList=javaFieldRspList;
	  		}
	    	//保存入库：
	    	var nodeDescId="";
			var isSave=false;
			$.ajax({
				type: "POST",
				async:false,
			    url: "../provider/addNodeDesc.shtml",
			    dataType:'json',
			    data:{
			    	nodeName:nodeName,
			    	nodePath:nodePath,
			    	nodeType:nodeType,
			    	nodeLengthCons:nodeLengthCons,
			    	nodeTypeCons:nodeTypeCons,
			    	nodeNumber:nodeNumber,
			    	nevlConsType:nevlConsType,
			    	isNeedCheck:isNeedCheck,
			    	nevlConsValue:nevlConsValue,
			    	javaField:javaField,
			    	tcpCtrFId:tcpCtrFId},
				success:function(data){
					if (data.code == "0000") {
						nodeDescId = data.nodeDescId;
						toastr.success("Success");
						isSave = true;
					}else if(data.code == "0002"){
						toastr.error("Failure");
						isSave = false;
					}
					$("#nodeDescModal").modal('hide');
		          }
		    });
			//列表加载：
			if(isSave){
		  		var tr=[];
		  		tr.push("<td>"+nodeName+"<input type=\"hidden\" name=\"nodeDescId\" value=\""+nodeDescId+"\"></td>");
		  		tr.push("<td>"+nodePath+"</td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml("nodeType",nodeType,nodeTypeList));
		  		tr.push("</td>");
		  		tr.push("<td><input type='text' class='form-control'  id='nodeLength'  name='nodeLength' value='"+nodeLengthCons+"'></td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml("nodeTypeCons",nodeTypeCons,nodeTypeConsList));
		  		tr.push("</td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml("nodeNumber",nodeNumber,nodeNumberConsList));
		  		tr.push("</td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml("nevlConsType",nevlConsType,nevlConsTypeList));
		  		tr.push("</td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml2("isNeedCheck",isNeedCheck,selectStateList));
		  		tr.push("</td>");
		  		tr.push("<td><input type='text' name='nelConsValue' class='form-control' value='"+nevlConsValue+"'></td>");
		  		tr.push("<td>");
		  		tr.push(getSelectHtml("javaField",javaField,javaFieldList));
		  		tr.push("</td>");
		  		if(reqRsp=="REQ"){
			  		tr.push("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"reqDelete\"  delId=\""+nodeDescId+"\" class=\"btn default btn-xs\">${local["eaap.op2.portal.price.delete"]}</a></td></td>");
		  		}else if(reqRsp=="RSP"){
			  		tr.push("<td  style=\"text-align:center; vertical-align: middle;\"><a href=\"#\" id=\"rspDelete\"  delId=\""+nodeDescId+"\" class=\"btn default btn-xs\">${local["eaap.op2.portal.price.delete"]}</a></td></td>");
		  		}		  		
		  		var trStr="<tr id=''>"+tr.join("")+"</tr>";
		  		if(reqRsp=="REQ"){
					$("#pageReqTable tbody").append(trStr);
		  		}else if(reqRsp=="RSP"){
					$("#pageRspTable tbody").append(trStr);
		  		}
			}
    	}

	    function getSelectHtml(idName,defVal,listStr){
	    	var selJson = toJson(listStr);
	    	var selHtml = "<select name='"+idName+"' id='"+idName+"' class='form-control'>";
	    	if(selJson!=null && selJson.length>0){
	    		if(idName=="nevlConsType" || idName=="javaField"){
	    	    	selHtml += "<option value=''></option>";
	    		}
	    		for(var i=0; selJson.length>i; i++){
	    			var isSelected = (selJson[i].CEPVALUES==defVal?"selected":"");
	    	    	selHtml += "<option value='"+selJson[i].CEPVALUES+"'  "+isSelected+">"+selJson[i].CEPNAME+"</option>";
	    		}
	    	}
	    	selHtml += "</select>";
	    	return selHtml;
	    }
	    
	    function getSelectHtml2(idName,defVal,listStr){
	    	var selJson = toJson(listStr);
	    	var selHtml = "<select name='"+idName+"' id='"+idName+"' class='form-control'>";
	    	if(selJson!=null && selJson.length>0){
	    		for(var i=0; selJson.length>i; i++){
	    			var isSelected = (selJson[i].CODE==defVal?"selected":"");
	    	    	selHtml += "<option value='"+selJson[i].CODE+"'  "+isSelected+">"+selJson[i].NAME+"</option>";
	    		}
	    	}
	    	selHtml += "</select>";
	    	return selHtml;
	    }
	    
	    function toJson(listStr){
	    	listStr = listStr.replace("[{", "[{\"");
	    	listStr = listStr.replace(/}, {/g, "\"},{\"");
	    	listStr = listStr.replace("}]", "\"}]");
	    	listStr = listStr.replace(/=/g, "\":\"");
	    	listStr = listStr.replace(/, /g, "\",\"");
	    	return eval(listStr);
	    }
	    
	    function reSetVal(){
	    	$("#nodeDescModal").find("#nodeName").val("");
	    	$("#nodeDescModal").find("#nodePath").val("");
	    	$("#nodeDescModal").find("#nodeType").val(1);
	    	$("#nodeDescModal").find("#nodeLengthCons").val("");
	    	$("#nodeDescModal").find("#nodeTypeCons").val(1);
	    	$("#nodeDescModal").find("#nodeNumber").val(1);
	    	$("#nodeDescModal").find("#nevlConsType").val("");
	    	$("#nodeDescModal").find("#isNeedCheck").val("N");
	    	$("#nodeDescModal").find("#nevlConsValue").val("");
	    	$("#nodeDescModal").find("#javaFieldReq").val("");
			$("#nodeDescModal").find("#javaFieldRsp").val("");
	    }
</script>

