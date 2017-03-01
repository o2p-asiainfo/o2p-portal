<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
  <h4 class="modal-title">${doOperation}</h4>
</div>
<div class="modal-body">
<div id="part1">
  <!-- BEGIN SECTION 1 -->
  <h4 class="form-section mt5">Part 1</h4>
<div class="row">
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.applyabilityName"]}</label>
      <input type="text" class="form-control" id="pageServiceName" value="${userDefined.serviceName}" maxlength="50">
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.crontactCode"]}</label>
      <input type="text" class="form-control" id="pageProCode" value="${userDefined.contractCode}" maxlength="50">
    </div>
  </div>
</div>
<div class="row">
<div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.crontactVersionCode"]}</label>
      <input type="text" class="form-control" id="pageProVersionCode" value="${userDefined.contractVersionCode}" maxlength="50">
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.selectDirectory"]}</label>
      <select class="form-control" id="pageSelectedDir" disabled>
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
      <select class="form-control" id="pageCommPro" disabled>
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

</div>
<div class="row">
  <div class="col-md-12">
    <div class="form-group">
      <label class="control-label"> ${local["eaap.op2.portal.doc.descr"]}</label>
      <textarea rows="3" class="form-control" id="pageDescription"  maxlength="1000">${userDefined.descriptor}</textarea>
    </div>
  </div>
</div>
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
            <select class="form-control" id="pageReqProFormat" name="pageReqProFormat"> 
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
            <label class="control-label">${local["eaap.op2.portal.doc.contractDocument"]}</label>
            <input type="file" class="control">
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
			    <td><select name='nodeTypeCons' id='' class='form-control'>
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
            <select class="form-control" id="pageRspProFormat" name="pageRspProFormat">
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
              <label class="control-label"> ${local["eaap.op2.portal.doc.contractDocument"]}</label>
              <input type="file">
            </div>
          </div>
        </div>
        <div class="row" style="padding-left:0;">
        <div class="col-md-5">
            <div class="form-group">
             <label class="control-label"> ${local["eaap.op2.portal.doc.headFormat"]}</label>
              <textarea class="form-control" rows="3" id="pageReqHeadFormat" name="pageReqHeadFormat">${rspHeadXsdFormat}</textarea>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="control-label"> <font color="FF0000">*</font> ${local["eaap.op2.portal.doc.procdBodyFormat"]} </label>
              <textarea class="form-control" rows="3" id="pageRspBodyFormat" name="pageRspBodyFormat">${rspXsdFormat}</textarea>
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
			    <td><select name='rspNodeType' id='rspNodeType'  class='form-control'>
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
<div class="modal-footer" id="cumOperator">
  <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.portal.doc.message.close"]}</button>
</div>
<script>
$(function(){
	showProperty($('#pageCommPro').val());
	});
	function showProperty(value){
		var pageTechimplId = '${userDefined.techImpId}';
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
</script>

