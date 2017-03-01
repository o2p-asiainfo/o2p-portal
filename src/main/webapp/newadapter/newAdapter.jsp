<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
	<link rel="stylesheet" type="text/css"  href="../resources/bootstrap/css/GooFlow.css">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="../resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-datepicker/datepicker.css" />
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link rel="stylesheet" type="text/css" href="../resources/plugins/select2/select2.min.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="../resources/bootstrap/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="../resources/bootstrap/css/style.css" rel="stylesheet" type="text/css" />
<link href="../resources/bootstrap/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="../resources/bootstrap/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../resources/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript"  src="../resources/bootstrap/scripts/GooFlow.js"></script>
    <script src="../resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
    <script >
    var title = [];
	title[0]='${local["eaap.op2.conf.adapter.sourceNode"]}';
	title[1]='${local["eaap.op2.conf.adapter.targetNode"]}';
	title[2]='${local["eaap.op2.conf.adapter.operation"]}';
	title[3]='${local["eaap.op2.conf.adapter.operationValue"]}';
	title[4]='${local["eaap.op2.conf.adapter.stillNode"]}';
	title[5]='${local["eaap.op2.conf.adapter.sureDelete"]}';
	title[6]='${local["eaap.op2.conf.adapter.moveIsExit"]}';
	title[7]='${local["eaap.op2.conf.adapter.updateTomove"]}';
	title[8]='${local["eaap.op2.conf.adapter.updateIsExit"]}';
	title[9]='${local["eaap.op2.conf.adapter.sureUpdate"]}';
	title[10]='${local["eaap.op2.conf.adapter.otherIsExit"]}';
	title[11]='${local["eaap.op2.conf.adapter.updateToOther"]}';
	title[12]='${local["eaap.op2.conf.adapter.assignIsExit"]}';
	title[13]='${local["eaap.op2.conf.adapter.updateToAssign"]}';
	title[14]='${local["eaap.op2.conf.adapter.rowToColumnIsExit"]}';
	title[15]='${local["eaap.op2.conf.adapter.rowToColumnSure"]}';
	title[16]='${local["eaap.op2.conf.adapter.columnToRowIsExit"]}';
	title[17]='${local["eaap.op2.conf.adapter.columnToRowSure"]}';
	
	var dataError = '${local["eaap.op2.conf.protocol.error.dataDoerror"]}';
	var risExist = '${local["eaap.op2.conf.protocol.RisExit"]}';
	var intoProExist = '${local["eaap.op2.conf.protocol.IntoisExit"]}';
	var notDelSrc = '${local["eaap.op2.conf.protocol.error.notDel"]}';
	var nodeUsed = '${local["eaap.op2.conf.protocol.error.nodeused"]}';
	var srcDel = '${local["eaap.op2.conf.protocol.error.srcDel"]}';
	var chooseRecords = '${local["eaap.op2.conf.protocol.error.chooseRecords"]}';
	var notchooseTar = '${local["eaap.op2.conf.protocol.error.canotSelectTar"]}';
	var addFirst = '${local["eaap.op2.conf.protocol.error.addfirst"]}';
	var keyExpnotnull = '${local["eaap.op2.conf.protocol.error.keyexpnotnull"]}';
	var canotbeempty = '${local["eaap.op2.conf.protocol.error.canotbeempty"]}';
	var toolong = '${local["eaap.op2.conf.protocol.error.toolong"]}';
	var nodeBeUsed='${local["eaap.op2.conf.protocol.error.nodeBeused"]}';
	var notchooseSrc = '${local["eaap.op2.conf.protocol.error.canotSelectSrc"]}';
    </script>
</head>
<body>
<!-- 隐藏域区间 -->
<!-- 是选源还是选目标节点的动作类型 -->
<input type="hidden" id="selectActioin">
<!-- 协议转化ID -->
<input  type="hidden" name="pageContractAdapterId" id="pageContractAdapterId" value="${pageContractAdapterId}"/>
<!-- 源协议ID -->
<input  type="hidden" name="pageSrcTcpCtrFId" id="pageSrcTcpCtrFId" value="${pageSrcTcpCtrFId}"/>
<!-- 目标协议ID -->
<input  type="hidden" name="pageTarTcpCtrFId" id="pageTarTcpCtrFId" value="${pageTarTcpCtrFId}"/>
<!-- 端点ID -->
<input  type="hidden" name="pageEndpointId" id="pageEndpointId" value="${pageEndpointId}"/>
<!-- 源节点ID -->
<input  type="hidden" name="pageSrcNodeDescId" id="pageSrcNodeDescId"/>
<!-- 目标节点ID -->
<input  type="hidden" name="pageTarNodeDescId" id="pageTarNodeDescId"/>
<!-- 节点取值要求ID -->
<input  type="hidden" name="pageNodeReqId" id="pageNodeReqId"/>
<!-- 常量映射类型编码 -->
<input  type="hidden" name="pageConsMapCDFinal" id="pageConsMapCDFinal"/>
<!-- 变量映射表ID-->
<input  type="hidden" name="pageVarMappingId" id="pageVarMappingId"/>
<!-- 添加或者修改标记位-->
<input  type="hidden" name="saveOrUpdateFlag" id="saveOrUpdateFlag"/>
<!-- 消息流参数 -->
<input  type="hidden" name="pageState" id="pageState" value="${pageState}"/>
<input  type="hidden" name="attrName" id="attrName" value="${attrName}"/>
<input  type="hidden" name="objectId" id="objectId" value="${objectId}"/>
<input  type="hidden" name="endpoint_Spec_Attr_Id" id="endpoint_Spec_Attr_Id" value="${endpoint_Spec_Attr_Id}"/>
<input type="hidden" name="newState" id="newState" value="${newState}">
<input type="hidden" name="attrSpecCode" id="attrSpecCode" value="${attrSpecCode}">
<input type="hidden" name="intoOrOut" id="intoOrOut" value="${intoOrOut}"/>
<!--body start -->
<div class="Content">
<ul id="myTab" class="nav nav-tabs">
   <li class="active">
      <a href="#commonAdapter" onclick="javascript:onclickTab(1);" data-toggle="tab">  
         ${local["eaap.op2.conf.adapter.universalAdapter"]}
      </a>
   </li>
   <li><a href="#scriptAdapter" onclick="javascript:onclickTab(2);" data-toggle="tab">
   ${local["eaap.op2.conf.adapter.scriptAdapter"]}
   </a></li>
</ul>
<div id="myTabContent" class="tab-content">
   <div class="tab-pane fade in active" id="commonAdapter">
	<div class="choose">
    	<span>${local["eaap.op2.conf.adapter.srcContract"]}</span>
    	<input  type="text" name="srcContractName" id="srcContractName"/>
    	<input  type="text" name="srcContractVersionName" id="srcContractVersionName"/>
    	<input  type="text" name="srcContractType" id="srcContractType"/>
    	<input type="button"  value='${local["eaap.op2.conf.remoteCallInfo.choose"]}'  id="chooseLeft" class="submit"  data-toggle="modal">
    	<span style="margin-left:100px">${local["eaap.op2.conf.adapter.tarContract"]}</span>
    	<input  type="text" name="tarContractName" id="tarContractName" />
    	<input  type="text" name="tarContractVersionName" id="tarContractVersionName"/>
    	<input  type="text" name="tarContractType" id="tarContractType"/>
    	<input type="button" value='${local["eaap.op2.conf.remoteCallInfo.choose"]}'  id="chooseRight" class="submit"  data-toggle="modal"/>
    </div>
	<div id="Adapter">
	    <!-- 拉线操作区 -->
		<div id="UniversalAdapterDemo" class="GooFlow">
		<!-- 色彩说明区 
		<div class='colorillustrate'>
			<span class='sp1'></span><s:property value="%{getText('eaap.op2.conf.protocol.intoprotocol')}" />
			<span class='sp2'></span><s:property value="%{getText('eaap.op2.conf.protocol.TAction')}" />
			<span class='sp3'></span><s:property value="%{getText('eaap.op2.conf.protocol.RAction')}" />
		</div>
		-->
		</div>	 
		<!-- 表格展示区 -->
		<div id="FlowTable"></div>  
	</div>
	<!-- 适配提交按钮 -->
   <p class="text-center">
   <input type="button" class="btn green" value='${local["eaap.op2.conf.prov.define"]}' id="adapterButton">
   <input type="button" class="btn green margin-left-10" value='${local["eaap.op2.conf.prov.close"]}' id="contractAdapterClose" onclick="javascript:closeAdapterWindow();">
   </p>
   </div>
   <!-- 脚本转化配置区 -->
      <div class="tab-pane fade" id="scriptAdapter">
   		<div  class="adaptip">
   		    ${local["eaap.op2.conf.adapter.contractMaping"]}
    	</div>
        <div>
        	<table width="100%"  class="table table-bordered">
  				<tr>
    				<td width="20%">${local["eaap.op2.conf.adapter.scriptType"]}</td>
    				<td width="80%"><div class="input-group" style="padding:0; margin:0">
 
          <select class="js-example-placeholder-single form-control" id="adapterType" placeholder="name" style="width:150px">
            <c:forEach var="obj" items="${scriptL}" varStatus="scriptList">
			    <option value="${obj.id}" <c:if test="${obj.id == pageAdapterType}">selected</c:if>>${obj.val}</option>
			</c:forEach>
          </select>
        </div></td>
  				</tr>
  				<tr>
    				<td>${local["eaap.op2.conf.adapter.scriptUpload"]}</td>
    				<td>
    				<form method="POST" id="reqFile" action="../provider/uploadFile.shtml"  enctype="multipart/form-data">
    				<input type="file" name="file" id="scriptFileUpload" size="30" onchange="javascript:uploadImg();" multiple class="sys-text-area" />
    				</form>
    				</td>
  				</tr>
  				<tr>
    				<td>${local["eaap.op2.conf.adapter.contractScript"]}</td>
    				<td><textarea class="box2" onmouseover="this.className='box2'" onmouseout="this.className='box1'" type="text" style="WIDTH: 850px; HEIGHT:280px;" id="scriptFileText" name="scriptFileText">${pageScriptFileText}</textarea></td>
  				</tr>
                
			</table>

        </div>
        <!-- 脚本提交按钮 -->
        <p class="text-center">
        <input type="button" class="btn green" value='${local["eaap.op2.conf.prov.define"]}' id="scritpButton">
        <input type="button" class="btn green margin-left-10" value='${local["eaap.op2.conf.prov.close"]}' id="scriptAdapterClose" onclick="javascript:closeAdapterWindow();">
        </p>
   </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:1000px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
            ${local["eaap.op2.conf.protocol.chooseProtocol"]}
            </h4>
         </div>
         <div class="contionfilter clearfix">
         
         	<div class="input-group2 mr20">
          <label for=""><span class="input-group-text">${local["eaap.op2.conf.adapter.contractName"]}:</span> </label>
          <input type="text" name="pageProtocolName" id="pageProtocolName"/>
          
        </div>
        <div class="input-group2 mr20">
           <label for=""><span class="input-group-text">${local["eaap.op2.conf.adapter.contractVersion"]}:</span> </label>
           <input type="text" name="pageContractVersion" id="pageContractVersion"/>
        </div>
        <div class="input-group mr20">
          <label for=""><span class="input-group-text">${local["eaap.op2.conf.adapter.contractType"]}:</span></label>
          <select name="pageProtocolType" id="pageProtocolType" class="js-example-placeholder-single form-control mr20" placeholder="name" style="width:150px">
            <option value="">ALL</option>
            <c:forEach var="obj" items="${contractTypeList}" varStatus="listcontractTypeList">
			    <option value="${obj.key}">${obj.value}</option>
			</c:forEach>
          </select>
        </div>
        <div class="input-group mr20">
          <label for=""><span class="input-group-text">${local["eaap.op2.conf.adapter.httpType"]}:</span></label>
          <select name="pageReqRsp" id="pageReqRsp" class="js-example-placeholder-single form-control mr20" placeholder="name" style="width:150px">
            <option value="">ALL</option>
            <c:forEach var="obj" items="${httpTypeList}" varStatus="listhttpTypeList">
			    <option value="${obj.key}">${obj.value}</option>
			</c:forEach>
          </select>
        </div>
        <input type="button" class="btn btn-primary query" id="queryContract" value='${local["eaap.op2.conf.logaudit.typequery"]}'>
         </div>
         <div class="modal-body" style="padding-bottom:0;">
            <div class="container" style="width:100%; padding-bottom:0">
    <div class="portlet box s-protlet-theme">
      <div class="portlet-title">
        <div class="caption">${local["eaap.op2.conf.protocol.contractMessage"]}</div>
        
      </div>
      <div class="portlet-body">
        <table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="dt">
          <thead>
            <tr>
              <th>
              </th>
              <th> ${local["eaap.op2.conf.adapter.contractName"]} </th>
              <th> ${local["eaap.op2.conf.adapter.contractVersion"]}</th>
              <th> ${local["eaap.op2.conf.adapter.contractType"]}</th>
              <th> ${local["eaap.op2.conf.adapter.httpType"]}</th>
              <th> ${local["eaap.op2.conf.protocol.TCP_CTR_F_ID"]}</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
  </div>
         </div>
         <div class="modal-footer" style="margin-top:10px;">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">
               ${local["eaap.op2.conf.prov.close"]}
            </button>
            <button type="button" id="chooseContract" class="btn btn-primary">
              ${local["eaap.op2.conf.prov.define"]}
            </button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 条件值配置区 -->
<div class="modal fade" id="chooseTemplate" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:750px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel2">
            ${local["eaap.op2.conf.protocol.msg.chooseTemplate"]}
            </h4>
         </div>
         <div class="modal-body">
         	<div class="chooseFilter clearfix">
            	<div class="sourceNode">
         	<h5>${local["eaap.op2.conf.adapter.srcNode"]}</h5>
                    <p><span>${local["eaap.op2.conf.adapter.nodeName"]}:</span><input type="text" name="showNodeName" id="showNodeName" readonly></p>
                    <p><span>${local["eaap.op2.conf.adapter.nodePath"]}:</span><input type="text" name="showNodePath" id="showNodePath" readonly></p>       
                </div>
                <div class="assignmentCondition">
                	<h5>${local["eaap.op2.conf.adapter.assignmentCondition"]}</h5>
                	  <textarea name="textarea" id="pageAssignmentCondition" style="width:100%; HEIGHT:90px;"></textarea>
                </div>
            </div>
            <ul id="chooseTab2" class="nav nav-tabs">
   				<li class="active"><a href="#fixedA" data-toggle="tab">${local["eaap.op2.conf.adapter.fixAssign"]}</a> </li>
   				<li><a href="#JavaBeanA" data-toggle="tab">${local["eaap.op2.conf.adapter.javaAssign"]}</a></li>
                <li><a href="#MetaDM" data-toggle="tab">${local["eaap.op2.conf.adapter.metaDataMatch"]}</a></li>
			</ul>
            <div class="tab-content">
            	<div id="fixedA" class="tab-pane fade in active tab-content-m">
                	<p><span>${local["eaap.op2.conf.adapter.nodePath"]}:</span><input type="text" name="pageNodePath" id="pageNodePath" readonly></p>
                    <p><span>${local["eaap.op2.conf.adapter.nodeValue"]}:</span><input type="text"name="pageNodeValue" id="pageNodeValue"></p>
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="pageTypeOneSubmit">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
                <div id="JavaBeanA" class="tab-pane fade  tab-content-m">
                	<p>${local["eaap.op2.conf.adapter.codeFrag"]}</p>
                    <textarea name="pageJavaBean" id="pageJavaBean" style="width:100%; HEIGHT:90px;"></textarea>
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="pageTypeTwoSubmit">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
            	<div id="MetaDM" class="tab-pane fade">
                	<p class="tab-pane-p">
                	${local["eaap.op2.conf.adapter.varMapType"]}<input type="text" id="pageConsMapCD" name="pageConsMapCD">
                	${local["eaap.op2.conf.adapter.name"]}<input type="text" id="pageConsMapName" name="pageConsMapName">
                	<input type="button" value='${local["eaap.op2.conf.remoteCallInfo.choose"]}'  id="pageWinSelect" data-toggle="modal" data-target="#MetaData">
                	<input type="button" id="ConsMapButton" value='${local["eaap.op2.conf.prov.submit"]}'>
                	<input type="button" id="reSetButton" value='${local["eaap.op2.conf.prov.reSet"]}'></p>
                    <div class="container  margin-top-10" style="width:100%; ">
    					<div class="portlet box s-protlet-theme">
      						<div class="portlet-title">

        						<div class="caption"> ${local["eaap.op2.conf.protocol.variables"]} </div>
        							<div class="actions">
         						 	<button class="btn green" id="btn-add2" data-toggle="modal">
         						 	<i class="fa fa-plus"></i> ${local["eaap.op2.conf.protocol.Add"]} </button>
          							<button class="btn blue" id="btn-edit2"><i class="fa fa-edit"></i> ${local["eaap.op2.conf.protocol.Edit"]} </button>
          							<button class="btn red" id="btn-del2"><i class="fa fa-trash-o"></i> ${local["eaap.op2.conf.protocol.Del"]} </button>
        							</div>
      							</div>
      							<div class="portlet-body">
        							<table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="dt2">
          							<thead>
            						<tr>
              							<th></th>
              							<th> ${local["eaap.op2.conf.adapter.varMapingId"]} </th>
              							<%--
              							<th> ${local["eaap.op2.conf.adapter.dataSource"]}</th> --%>
              							<th> ${local["eaap.op2.conf.adapter.keyExpress"]}</th>
              							<th> ${local["eaap.op2.conf.adapter.valExpress"]}</th>
              							<%--
             							<th> ${local["eaap.op2.conf.adapter.state"]} </th>--%>
            						</tr>
         							 </thead>
         							<tbody>
          							</tbody>
       								</table>
      							</div>
    						</div>
  						</div>
         			
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="pageTypeThreeSubmit">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                    </div>
                   
                </div>
            </div>
            	
      			</div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="dataMatch" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:400px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
            ${local["eaap.op2.conf.adapter.metaDataMatch"]}
            </h4>
         </div>
         <div class="modal-body" style="padding-bottom:0">
           <div class="container " style="width:100%; padding-bottom:0 ">
       <table  class="table  table-bordered">
         <tr style="display: none;">
           <td class="text-right line-h34">${local["eaap.op2.conf.adapter.dataSource"]}</td>
           <td>
          <div class="input-group" style="padding-bottom:0">
          <select class="js-example-placeholder-single form-control mr20" id="pageDataSource" placeholder="name" style="width:150px">
            <c:forEach var="obj" items="${metaDataMachingL}" varStatus="metaDataMachingList">
			    <option value="${obj.id}">${obj.val}</option>
			</c:forEach>
          </select>
        </div></td>
         </tr>
         <tr>
           <td class="text-right line-h34">${local["eaap.op2.conf.adapter.keyExpress"]}</td>
           <td><div class="input-group3"><input type="text" id="pageKeyExp"></div></td>
         </tr>
         <tr>
           <td class="text-right line-h34">${local["eaap.op2.conf.adapter.valExpress"]}</td>
           <td><div class="input-group3"><input type="text" id="pageValExp"></div></td>
         </tr>
       </table>
           </div>
         <div class="modal-footer" style="margin-top:10px">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}
            </button>
            <button type="button" class="btn btn-primary"  id="ProtocolMChoose">
               ${local["eaap.op2.conf.prov.define"]}
            </button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="dataidienty" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
  <div class="modal-dialog"  style="width:360px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
                ${local["eaap.op2.conf.protocol.error.Relatedendpoint"]}
            </h4>
         </div>
         <div class="modal-body" style="padding-bottom:0">
           <div class="container " style="width:100%; padding-bottom:0 ">
       <table  class="table  table-bordered">
         <tr>
           <td class="text-right line-h34">${local["eaap.op2.conf.protocol.error.endpointId"]}</td>
           <td>
           <!-- 
           <div class="input-group3"><input type="text" id="ProtocolId"></div> -->
           <div class="input-group" style="padding-bottom:0">
          <select class="js-example-placeholder-single form-control mr20" id="ProtocolId" placeholder="name" style="width:150px">
            <c:forEach var="obj" items="${moreSourceList}" varStatus="metaDataMachingList">
			    <option value="${obj.id}">${obj.val}</option>
			</c:forEach>
          </select>
        </div>
           </td>
         </tr>
       </table>
           </div>
         <div class="modal-footer" style="margin-top:10px">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}
            </button>
            <button type="button" class="btn btn-primary"  id="pageMessageButton">
               ${local["eaap.op2.conf.prov.define"]}
            </button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="MetaData" tabindex="-1" role="dialog" 
  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:660px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel2">
            ${local["eaap.op2.conf.protocol.msg.chooseTemplate"]}
            </h4>
         </div>
         <div class="modal-body">
            <div class="tab-content">
            	<div id="MetaDM">
                	<p class="tab-pane-p">
                	${local["eaap.op2.conf.adapter.varMapType"]}<input type="text" id="pageWinVarType">
                	${local["eaap.op2.conf.adapter.name"]}<input type="text" id="pageWinName">
                	<input type="button" id="pageWinQuery" value='${local["eaap.op2.conf.logaudit.typequery"]}'>
                	</p>
                    <div class="container  margin-top-10" style="width:100%; ">
    					<div class="portlet box s-protlet-theme">
      						<div class="portlet-title">

        						<div class="caption"> ${local["eaap.op2.conf.adapter.varMapType"]} </div>
        							<div class="actions" style="padding-top:5px;">
          							<button class="btn red" id="pageWinDel"><i class="fa fa-trash-o"></i> ${local["eaap.op2.conf.protocol.Del"]} </button>
        							</div>
      							</div>
      							<div class="portlet-body">
        							<table class="table table-bordered table-striped table-hover text-center nowrap-ingore group-check" id="dt3">
          							<thead>
            						<tr>
              							<th></th>
              							<th> ID </th>
              							<th> ${local["eaap.op2.conf.adapter.varMapType"]}</th>
              							<th> ${local["eaap.op2.conf.adapter.name"]}</th>
            						</tr>
         							 </thead>
         							<tbody>
          							</tbody>
       								</table>
      							</div>
    						</div>
  						</div>
         			
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="pageWinOK">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                    </div>
                   
                </div>
            </div>
            	
      			</div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="ExtractModal" tabindex="-1" role="dialog" 
  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:660px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel2">
               模态框（Modal）标题
            </h4>
         </div>
         <div class="modal-body">
            <div class="tab-content">
           	  <div id="MetaDM">
              		<div class="ddd" style="width:200px">
                    <p class="extractradio"><label><input type="radio" checked="checked" name="ct">String类型截取</label></p>
                    <p class="extractradio"><label><input type="radio" name="ct">数组获取</label></p>
                    </div>
                	<div class="extractRemarks">
                    	<div class="RemarksDetail">
                        	<dl><dt>配置说明：</dt><dd>'nodeDescId'.substring(start, end)。nodeDescId源节点ID，start字符开始位置，end字符结束位置。</dd></dl>
                            <dl><dt>配置效果：</dt><dd>假设源节点值为“中国福建福州”，源节点ID为500100。那么表达式'500100'.substring(0, 2)的输出结果为“中国”。</dd></dl>
                            <dl class="espld"><dt>参考配置：</dt><dd><span>'500100'.substring(<font color="red">start</font>, <font color="red">end</font>)</span>，其中字体红色为需要修改的地方。<input type="button" value="使用表达式"/></dd></dl>
                        </div>
                        <div class="RemarksDetail" style="display:none">
                        	<dl><dt>配置说明：</dt><dd>{nodeDescId}[index]，nodeDescId源节点ID,index是数组的索引值。</dd></dl>
                            <dl><dt>配置效果：</dt><dd>假设原节点值为“500105,500106,500107”，（注：节点的值信息必须是英文半角逗号分隔）源节点ID值为500100。那么表达式{500100}[0]的输出结果为“500105”</dd></dl>
                            <dl  class="espld"><dt>参考配置：</dt><dd><span>{500100}[<font color="red">index</font>]</span>，其中字体红色为需要修改的地方。<input type="button" value="使用表达式"/></dd></dl>
                        </div>
                    </div>
                    <div class="tab-pane-p" id="extractList"></div>
                    <div class="margin-top-10" style="width:100%; ">
         			<textarea style="width:100%; height:80px;border:1px solid #dfdfdf"></textarea>
                    <p class="text-right"><input value="clear" type="button" class="margin-top-10 extractClear"></p>
                    <div class="modal-footer">
       				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            		<button type="button" class="btn btn-primary">提交更改</button>
         			</div>
                    </div>
                   
                </div>
            </div>
            	
      			</div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="JoinModal" tabindex="-1" role="dialog" 
  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:660px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel2">
               模态框（Modal）标题
            </h4>
         </div>
         <div class="modal-body">
            <div class="tab-content">
           	  <div id="MetaJM">
                    <div class="JionRemarks clearfix">
                        	<dl><dt>配置说明：</dt><dd>[nodeDescId] + '分隔符(可选)' + [nodeDescId]。nodeDescId源节点ID。</dd></dl>
                            <dl><dt>配置效果：</dt><dd>假设源节点值分别为“中国”、“福建”、“福州”。ID值为别为“500100”、“500101”、“500102”。那么表达式[500100] + ',' + [500101] + '.' + [500102]的输出结果为“中国,福建.福州”。</dd></dl>
                            <dl class="joinexample"><dt>参考配置：</dt><dd><span></span>，其中字体红色为色为需要修改的地方。<input type="button" value="使用表达式"/></dd></dl>
                        </div>
                    <div class="tab-pane-p" id="joinList"></div>
                    <div class="margin-top-10" style="width:100%; ">
         			<textarea style="width:100%; height:80px;border:1px solid #dfdfdf"></textarea>
                    <p class="text-right"><input value="clear" type="button" class="margin-top-10 extractClear"></p>
                    <div class="modal-footer">
       				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            		<button type="button" class="btn btn-primary">提交更改</button>
         			</div>
                    </div>
                   
                </div>
            </div>
            	
      			</div><!-- /.modal-content -->
</div><!-- /.modal -->


<div class="modal fade" id="rToCConfigModal"  tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:750px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel2">
            	${local["eaap.op2.conf.adapter.rToCConversionConfiguration"]}
            </h4>
         </div>
         <div class="modal-body">
      			<div class="panel-body">
      				<div style="height:100px;">
	      			<div class="modal-body GooFlow ctrlist "  style="width:690px;height:70px;margin:0;padding:0;overflow-y:auto;border:2px solid #bbb; padding:20px 8px; margin:0 0 20px 0; ">
			            <ul>
	           	    			<li class="child checked act" style="-moz-user-select: text  !important; -khtml-user-select: text  !important; -ms-user-select:text !important;-webkit-user-select:text !important;">
	            	    			<span class="d-t"><em style="margin-left: 5px;line-height:20px;" id="leftNodeName">CCCC</em></span>
	           	    			</li>
	           	    			<li style="background-color:#FFFFFF; border-top:1px solid #FFFFFF; ">
	           	    					<div>
	           	    						<div class="right" style="width:215px; border-top: 2px solid #ffd202; position: absolute; margin-top:9px;"></div>
		           	    					<div style="border: 1px solid #f99c00;height: 20px; line-height:20px; text-align:center;position: absolute;width: 72px; margin-left:80px; background-color:#FFFFFF;">
		           	    							R to C
		           	    					</div>
	           	    					</div>
	           	    			</li>
	           	    			<li class="child checked act"  style="-moz-user-select: text  !important; -khtml-user-select: text  !important; -ms-user-select:text !important;-webkit-user-select:text !important;">
		           	    			<span class="d-t" ><em style="margin-left: 5px;line-height:20px;" id="rightNodeName" >AAAAA</em></span>
	           	    			</li>
			            </ul>
		            </div>
           	    	</div>
            	    <div class="form-group">
	                  <label class="col-md-3 control-label">${local["eaap.op2.conf.adapter.assignmentCondition"]}:</label>
	                  <div class="col-md-8">
	                  		<div class="form-control"  onclick="showCon()" style="width:400px; height:34px; border:1px solid #E5E5E5; padding:0; background-color:#F8F8F8; line-height:30px; color:#AAAAAA;">
	                  			<table id="conTable" cellspacing="0" cellpadding="0"  style="display:none;width:auto; border:0; margin:0;padding:0;">
		                  			<tr>
			                  			<td style="padding-left:10px; font-family: 'Open Sans', Arial, sans-serif!important;">src_node_val=="</td>
			                  			<td>
			                  				<span id="conAssignmentConditionText" name="conAssignmentConditionText" style="width:auto !important; line-height:24px; font-family: 'Open Sans', Arial, sans-serif!important;"></span>
			                  				<input type="text" onblur="conOnblur(this)" onkeyup="$('#conAssignmentConditionText').text($(this).val())" id="conAssignmentCondition" name="conAssignmentCondition" style="width:200px; height:26px; line-height:24px;  margin:3px 0; border:0; background-color:#FFFFFF; padding-left:2px; font-family: 'Open Sans', Arial, sans-serif!important;"   value=""/>
			                  			</td>
			                  			<td style="font-family: 'Open Sans', Arial, sans-serif!important;">"</td>
		                  			</tr>
	                  			</table>
							</div>
					  </div>
	                </div>
	                <div class="form-group"   style="padding-top:25px;">
	                  <label class="col-md-3 control-label"><font style="color:#FF0000;">*</font> ${local["eaap.op2.conf.adapter.nodeValue"]}:</label>
	                  <div class="col-md-8 form-inline">
	                  		<input type="text" class="form-control"  style="width:400px; padding-left:10px; font-family: 'Open Sans', Arial, sans-serif!important;" id="conNodeValue" name="conNodeValue" value=""  disabled="disabled" />
							<button class="btn theme-btn" type="button"  onclick="chooseSrcBrothersNodePathNode()" title="Choose"><i class="fa fa-plus"></i></button> 
					  </div>
	                </div>
         		</div>
            	<div class="tab-pane fade in active tab-content-m">
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="pageCRSubmit">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
         </div>
      	</div>
	</div>
</div>

<div class="modal fade" id="lChooseBrothersNodePathModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:750px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel2">
            	${local["eaap.op2.conf.adapter.chooseValueNode"]}
            </h4>
         </div>
         <div class="modal-body">
      			<div class="modal-body GooFlow stolist stolistL"  style="height:330px !important;margin:0;padding:0;">
      				<div style="height:325px;margin:0;padding:0;overflow-y:auto !important;">
		            	<ul id="sTolistL"></ul>
		            </div>
	            </div>
            	<div class="tab-pane fade in active tab-content-m"  style="margin:0; padding:0;">
                    <div class="modal-footer"   style="margin-top:10px; padding-top:10px;">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="getLNodePathBut">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
         </div>
      	</div>
	</div>
</div>

<div class="modal fade" id="cToRConfigModal"  tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:1050px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel2">
            	${local["eaap.op2.conf.adapter.cToRConversionConfiguration"]}
            </h4>
         </div>
         <div class="modal-body">
      			<div class="panel-body">
						<table class="table table-bordered table-hover"  id="cToRLinesTable">
							<thead>
								<tr class="thead"><th colspan="7"  style="text-align:left;"><span class="d-slide"></span><span style="float:left;">Configuration  List</span></th></tr>
								<tr><th width="5%"> # </th><th width="15%">Source Node </th><th width="20%">Source Node Path </th><th width="15%">Target Node </th><th width="20%">Target Node Path </th><th width="20%" style="border-right-width:0;"><font style="color:#FF0000">*</font> Node Value </th><th width="5%"  style="border-left-width:0;"></th></tr>
							</thead>
							<tbody>
								<!-- tr><td>1</td><td>open:systemId</td><td></td><td>open:cvr</td><td></td><td style="border-right-width:0;">11</td><td  style="border-left-width:0;"><button type="button"  onclick="chooseTarBrothersNodePathNode()" class="btn btn-primary"  style="height:22px; line-height:22px; padding:0 6px;">Select</button></td></tr-->
							</tbody>
						</table>
         		</div>
            	<div class="tab-pane fade in active tab-content-m">
                    <div class="modal-footer">
           				<button type="button" class="btn btn-default"  data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary"  data-dismiss="modal">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
         </div>
      	</div>
	</div>
</div>

<div class="modal fade" id="rChooseBrothersNodePathModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true"  style="width:100%; z-index:9999">
   <div class="modal-dialog"  style="width:750px">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel2">
            	${local["eaap.op2.conf.adapter.chooseValueNode"]}
            </h4>
         </div>
         <div class="modal-body">
      			<div class="modal-body GooFlow stolist stolistR"  style="height:330px !important;margin:0;padding:0;">
      				<div style="height:325px;margin:0;padding:0;overflow-y:auto !important;">
		            	<ul id="sTolistR"></ul>
		            </div>
	            </div>
            	<div class="tab-pane fade in active tab-content-m"  style="margin:0; padding:0;">
                    <div class="modal-footer"   style="margin-top:10px; padding-top:10px;">
           				<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.prov.close"]}</button>
            			<button type="button" class="btn btn-primary" id="setNodeValueBut">${local["eaap.op2.conf.prov.define"]}</button>
         			</div>
                </div>
         </div>
      	</div>
	</div>
</div>
</body>
<script src="../resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/back-to-top.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap-switch/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="../resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 
 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script src="../resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script type="text/javascript" src="../resources/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script> 
<script type="text/javascript" src="../resources/plugins/select2/select2.min.js"></script> 
<script src="../resources/bootstrap/scripts/app.js"></script> 
<script src="../resources/bootstrap/scripts/productAttribute.js"></script>
<script src="../resources/bootstrap/scripts/productAttribute3.js"></script> 
<script src="../resources/bootstrap/scripts/variableMap.js"></script>
<script src="${APP_PATH}/resources/plugins/jquery.i18n.properties-min-1.0.9.js" type="text/javascript" ></script>

<script type="text/javascript">
var APP_PATH = '${APP_PATH}';
 $(function() {
  var LANGUAGE = '${language}'; 
  jQuery.i18n.properties({
	 name : 'eaap-op2-portal-messages',        
	 path : APP_PATH+'/resources/i18n/',
	 language: LANGUAGE, 
	 mode : 'map'
  });
  //拉线图形初使化操作
  var scriptDemo = "UniversalAdapterDemo";
  function newGooflow(){ demo=new GooFlow($("#" + scriptDemo),{width:1060,height:300,haveTool:true}); }
  newGooflow();
  //表格属性初使化操作
  App.init();
  ProductAttribute.init();
  VariableMap.init();
  ProductAttribute3.init();
 
  //表格显示隐藏操作
  $(".table-hover tbody tr").live('click',function(){
			$(".table-hover tbody tr.cur").removeClass("cur");$(this).addClass("cur");
			});
  $(".table-hover tr.thead").live('click',function(){
		if($(this).next().is(":visible")){
			$(this).find(".d-slide").addClass("d-withdraw");
			$(this).next().slideUp(200);
			$(this).parent().next().find("td").slideUp(200);
			}
		else{
			$(this).find(".d-slide").removeClass("d-withdraw");
			$(this).next().slideDown(200);
			$(this).parent().next().find("td").slideDown(200);
			}
		});
		$(".tablist li").live('click',function(){
			var ind=$(this).index();
			$(this).addClass("cur").siblings().removeClass("cur");
			$(".tabContent").hide();
			$(".tabContent:eq("+ind+")").show();
			});
		$("ul.l .head .title , ul.l .head .check , ul.l .head label,ul.l .basicid").live('mouseover',function(e){
			var tipleft=e.pageX-$("#UniversalAdapterDemo").offset().left;
			var tiptop=e.pageY-$("#UniversalAdapterDemo").offset().top+10;
			$tip=$("<div class='gooFlow-tip' id='tip'></div>");
			$tip.css({"top":tiptop+"px","left":tipleft+"px"}).html($(this).attr("tip"));
			$("#UniversalAdapterDemo").append($tip);
			})
		$("ul.l .head .title , ul.l .head .check , ul.l .head label,ul.l .basicid").live('mouseout',function(e){
			$("#tip").remove();
		});
		//新添加部分
		$("#ExtractModal span.nodespan,#joinList span.nodespan").live("mouseover",(function(e){
		$("#tip").remove();
		var tipleft=e.pageX-$("#UniversalAdapterDemo").offset().left;
		var tiptop=e.pageY-$("#UniversalAdapterDemo").offset().top+10;
		$tip=$("<div class='gooFlow-tip' id='tip'></div>");
		$tip.css({"top":tiptop+"px","left":tipleft+"px"}).html($(this).attr("objectid")+"<br>"+$(this).attr("name"));
		$("#UniversalAdapterDemo").append($tip);
		}));
	$("#ExtractModal span.nodespan,#joinList span.nodespan").live("mouseout",(function(e){
		$("#tip").remove();
	}));
	$("#MetaDM .extractradio").click(function(){
		if($(this).index()==0){
			
			$(".extractRemarks .RemarksDetail:first").find(".espld span").html("'"+$("#extractList span").attr("objectid")+"'"+"."+"substring(<span class='red'>start</span> , <span class='red'>end</span>)")
		}
		else if($(this).index()==1){
			$(".extractRemarks .RemarksDetail:last").find(".espld span").html("{"+$("#extractList span").attr("objectid")+"}"+"[<span class='red'>"+"index"+"</span>]")
			}
		$(".extractRemarks .RemarksDetail").hide();
		$(".extractRemarks .RemarksDetail:eq('"+$(this).index()+"')").show();
		});
	$(".extractRemarks .RemarksDetail:first input").click(function(){
		$("#extractList").next().find("textarea ").val("'"+$("#extractList span").attr("objectid")+"'"+"."+"substring(start, end)");
		});
	$(".extractRemarks .RemarksDetail:last input").click(function(){
		$("#extractList").next().find("textarea ").val("{"+$("#extractList span").attr("objectid")+"}"+"["+"index"+"]");
		});
	$(".extractClear").click(function(){
		$(this).parent().prev().val("");
		});
		//新添加部分
		//初使化树数据
		  var adapterType = '${pageAdapterType}';
			if ($('#pageContractAdapterId').val() > 0) {
				if(adapterType > 1){//说明是脚本配置 
					$('#myTab').find('li').eq(1).find('a').click();//设置Tab选中
				}else{
					if('' == pageSrcTcpCtrFId || ''== pageTarTcpCtrFId || '0' == pageSrcTcpCtrFId || '0' == pageTarTcpCtrFId){
						alert('${local["eaap.op2.conf.protocol.error.dataerror"]}');
					}else{
						loadAllData();//加载所有数据
					}
				}
			}
			
  $('#pageTypeTwoSubmit').click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var pageTarNodeDescId = $('#pageTarNodeDescId').val();//源节点ID
	  var condition = $('#pageAssignmentCondition').val();
	  var pageScript = $('#pageJavaBean').val();
	  if(!pageScript){
  		alert('${local["eaap.op2.conf.adapter.notEmpty"]}');
  		return;
  	  }else{
  		$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/addNodeValAdapterRes.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId,type:'4',pageTarNodeDescId:pageTarNodeDescId,pageCondition:condition,pageScript:pageScript},
			success:function(msg){
				 if('success' == msg.result){
					 $('#chooseTemplate').modal('hide');//隐藏窗口
				 }else{
					 alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
				 }
            }
       });
  	  }
  });
  
  $('#pageTypeOneSubmit').click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var pageTarNodeDescId = $('#pageTarNodeDescId').val();//源节点ID
	  var condition = $('#pageAssignmentCondition').val();
	  var nodeValue = $('#pageNodeValue').val();
	  if(!nodeValue){
  		alert('${local["eaap.op2.conf.adapter.notEmpty"]}');
  		return;
  	  }else{
  		$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/addNodeValAdapterRes.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId,type:'2',pageTarNodeDescId:pageTarNodeDescId,pageCondition:condition,pageNodeValue:nodeValue},
			success:function(msg){
				 if('success' == msg.result){
					 $('#chooseTemplate').modal('hide');//隐藏窗口
				 }else{
					 alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
				 }
            }
       });
  	  }
  });
  
  $('#pageTypeThreeSubmit').click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var pageTarNodeDescId = $('#pageTarNodeDescId').val();//源节点ID
	  var condition = $('#pageAssignmentCondition').val();
	  var nodeValue = $('#pageConsMapCDFinal').val();
	  if(!nodeValue){
  		alert('${local["eaap.op2.conf.adapter.notEmpty"]}');
  		return;
  	  }else{
  		$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/addNodeValAdapterRes.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId,type:'3',pageTarNodeDescId:pageTarNodeDescId,pageCondition:condition,pageNodeValue:nodeValue},
			success:function(msg){
				 if('success' == msg.result){
					 $('#chooseTemplate').modal('hide');//隐藏窗口
				 }else{
					 alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
				 }
            }
       });
  	  }
  });

  //脚本提交操作
  $('#scritpButton').click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var type = $('#adapterType').val();
	  if(12 != type &&  14 != type){
		  if ($('#scriptFileText').val() == "") {
			  alert('${local["eaap.op2.conf.adapter.contractScriptNotNull"]}');
	          return false;		  
		  }
	  }
	  	$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/saveScriptAdapter.shtml",
		    dataType:'json',
		    data:{pageScriptFileText:$('#scriptFileText').val(),pageAdapterType:type,pageContractAdapterId:pageContractAdapterId},
			success:function(msg){ 
				var o = msg;
				if (o.msg == 'save'){
					alert('${local["eaap.op2.conf.adapter.saveSuccess"]}');
				}else {
					alert('${local["eaap.op2.conf.adapter.updateSuccess"]}');
				}
				window.parent.editorPropertyValue(document.getElementById("objectId").value,
		 				document.getElementById("endpoint_Spec_Attr_Id").value,
		 				document.getElementById("attrSpecCode").value,o.contractAdapterId);
				$(window.parent.document).find('#closeButton').click();
          }
     });
  });
  //协议适配提交操作
  $('#adapterButton').click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  if(isOrNotSubmit(pageContractAdapterId)){
		  $.ajax({
				type: "POST",
				async:true,
			    url: "../newadapter/saveUniversalAdapter.shtml",
			    dataType:'json',
			    data:{transformerRuleId:pageContractAdapterId},
				success:function(msg){ 
					var o = msg;
					if (o.msg == 'save'){
						alert('${local["eaap.op2.conf.adapter.saveSuccess"]}');
					}else{
						alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
					}
					window.parent.editorPropertyValue(document.getElementById("objectId").value,
			 				document.getElementById("endpoint_Spec_Attr_Id").value,
			 				document.getElementById("attrSpecCode").value,pageContractAdapterId);
					$(window.parent.document).find('#closeButton').click();
	        }
	   });
	  }else{
		  alert('${local["eaap.op2.conf.protocol.error.notsave"]}');
	  }
  });
  //消息流Id修改提交
  $('#pageMessageButton').click(function(){
	 var messageFlowId = $('#ProtocolId').val();
	 if("" == messageFlowId){
		 alert('${local["eaap.op2.conf.protocol.error.messageFlowId"]}');
		 return;
	 }else{
		 var r = /^\+?[1-9][0-9]*$/;//正整数 
	     if(!r.test(messageFlowId)){
	    	 alert('${local["eaap.op2.conf.protocol.error.mustnum"]}');
	    	 return ;
	     }else{
	    	 var contractId = $('#ProtocolId').attr('releId');
	    	 var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	    	 $.ajax({
	 			type: "POST",
	 			async:false,
	 		    url: "../newadapter/updateResult.shtml",
	 		    dataType:'json',
	 		    data:{pageContractAdapterId:pageContractAdapterId,pageSrcTcpCtrFId:contractId,pageEndpointId:messageFlowId},
	 			success:function(msg){ 
	 				if (msg.result == 'success'){
	 					$('#dataidienty').modal('hide');
	 				}else{
	 					alert('data error');
	 				}
	             }
	           });
	     }
	 }
  });
 });
 //判断是否可以提交
 function isOrNotSubmit(pageContractAdapterId){
	 var flag = false;
	 $.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/isOrNotSubmit.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId},
			success:function(msg){ 
				if (msg.result == 'success'){
					flag = true;
				}
            }
          });
	 return flag;
 }
 function closeAdapterWindow(){
	 $(window.parent.document).find('#closeButton').click();
 }
 //**初使化加载所有数据
 function loadAllData(){
	 var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	 $.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/loadAllData.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId},
			success:function(msg){ 
				loadData(msg.all);
				$.each(msg.all.tables,function(key,val) {
					addTable(val.name,val.id,val.action);
				});//加载表格
				loadLine(msg.all);//加载线功能
				$.each(msg.all.lines,function(key,val) {
					addTableTr(val.from,val.to,val.operate);
				});//加载表格记录
				reHeight();
           }
      });
 }
 //响应tab的点击事件
 function onclickTab(num){
	  var type = '${pageAdapterType}';
	  if($('#pageContractAdapterId').val() > 0){
		  if(1 == num){//第一个TAB
			  if(type > 1){//说明在做变换配置
				  if(!confirm('${local["eaap.op2.conf.protocol.sureToModidy"]}')){
					  $('#myTab').find('li').eq(1).find('a').click();//设置Tab选中
				  }
			  }
		  }else if(2 == num){//第二个TAB
             if(type <=1){//说明在做变换配置
           	  if(!confirm('${local["eaap.op2.conf.protocol.sureToModidy"]}')){
					  $('#myTab').find('li').eq(0).find('a').click();//设置Tab选中
				  }
			  }
		  }
	  }
 }
 //处理文件上传
function uploadImg(){
	var formData = new FormData(document.getElementById("reqFile"));
	$.ajax({
        url: "../provider/uploadFile.shtml",
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,  
        contentType: false 
      }).done(function(data) {
    	  if(data){
    		  $('#scriptFileText').val(data);
    	  }
      });
}
 
$("#getLNodePathBut").click(function(){
	var nodeName="";
	var selNode = $(".stolistL .act");
	if(selNode.length>0){
		nodeName = selNode.attr("name")
	}else{
	  	 alert('${local["eaap.op2.conf.adapter.selectOne"]}');
		 return;
	}	
	$("#conNodeValue").val("//"+nodeName);
	$("#lChooseBrothersNodePathModal").modal("hide");
});

$("#setNodeValueBut").click(function(){
		var nodeName="";
		var selNode = $(".stolistR .act");
		if(selNode.length>0){
			nodeName = selNode.attr("name")
		}else{
		  	 alert('${local["eaap.op2.conf.adapter.selectOne"]}');
			 return;
		}	

	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var pageSrcNodeDescId = $('#pageSrcNodeDescId').val();	//源节点ID
      var nodeValue = "//"+nodeName;
	  var condition ="";
	  if(!nodeValue){
		alert('\"${local["eaap.op2.conf.adapter.nodeValue"]}\" '+'${local["eaap.op2.conf.adapter.notEmpty"]}');
		return;
	  }else{
			$.ajax({
				type: "POST",
				async:true,
			    url: "../newadapter/addNodeValAdapterRes.shtml",
			    dataType:'json',
			    data:{pageContractAdapterId:pageContractAdapterId,type:'1',pageTarNodeDescId:pageSrcNodeDescId,pageCondition:condition,pageNodeValue:nodeValue},
				success:function(msg){
					 if('success' == msg.result){
						$("#rChooseBrothersNodePathModal").modal("hide");
						 loadCToRLinesData();
					 }else{
						 alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
					 }
	          }
	     });
	 }	
});

$("#pageCRSubmit").click(function(){
	  var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
	  var pageTarNodeDescId = $('#pageTarNodeDescId').val();	//目标节点ID
	  var condition = $('#conAssignmentCondition').val();
	  if(condition!=""){
		  condition = "src_node_val==\""+condition+"\"";
	  }
	  var nodeValue = $('#conNodeValue').val();
	  if(!nodeValue){
  		alert('\"${local["eaap.op2.conf.adapter.nodeValue"]}\" '+'${local["eaap.op2.conf.adapter.notEmpty"]}');
  		return;
  	  }else{
  		$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/addNodeValAdapterRes.shtml",
		    dataType:'json',
		    data:{pageContractAdapterId:pageContractAdapterId,type:'1',pageTarNodeDescId:pageTarNodeDescId,pageCondition:condition,pageNodeValue:nodeValue},
			success:function(msg){
				 if('success' == msg.result){
					 $('#rToCConfigModal').modal('hide');//隐藏窗口
				 }else{
					 alert('${local["eaap.op2.conf.protocol.error.dataDoerror"]}');
				 }
            }
       });
  	  }
});

function showCon(){
	$("#conTable").show();
	$("#conAssignmentConditionText").hide();
	$("#conAssignmentCondition").show();
	$("#conAssignmentCondition").focus();
}

function conOnblur(cObj){
	$(cObj).val($(cObj).val().replace(/(^\s*)|(\s*$)/g,''));
	if(cObj.value==""){
		$("#conTable").hide()
	}else{
		$("#conAssignmentCondition").hide()
		$("#conAssignmentConditionText").show()
	}
}

 </script>
</html>
