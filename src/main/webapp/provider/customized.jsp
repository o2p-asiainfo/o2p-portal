<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!-- BEGIN SECTION 1 -->
<div id="part1">
<h4 class="form-section mt5">Part 1</h4>
<div class="row">
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> Service Name</label>
      <input type="text" class="form-control" id="pageServiceName">
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> Protocol Code</label>
      <input type="text" class="form-control" id="pageProCode">
    </div>
  </div>
</div>
<div class="row">
<div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> Protocol Version Code</label>
      <input type="text" class="form-control" id="pageProVersionCode">
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> Select Directory</label>
      <select class="form-control" id="pageSelectedDir">
       <c:forEach var="lwm" items="${directoryList}" varStatus="vsw">
		    <option value="${lwm.DIRID}">${lwm.DIRNAME}</option>				                
		</c:forEach>
      </select>
    </div>
  </div>
</div>
<div class="row">
<div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> <font color="FF0000">*</font> Communication Protocol</label>
      <select class="form-control" id="pageCommPro">
      <c:forEach var="lwm" items="${commProtocalList}" varStatus="vsw">
		    <option value="${lwm.COMMPROCD}">${lwm.COMMPRONAME}</option>				                
		</c:forEach>
      </select>
    </div>
  </div>
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> Service Address</label>
      <input type="text" class="form-control" id="pageServiceAdd">
    </div>
  </div>
</div>
<div class="row">
  <div class="col-md-6">
    <div class="form-group">
      <label class="control-label"> Description</label>
      <textarea rows="3" class="form-control" id="pageDescription"></textarea>
    </div>
  </div>
</div>
<p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="PartSubmit">Submit<i class="m-icon-swapright m-icon-white"></i> </a></p>
</div>
<!-- END SECTION 1 --> 
<!-- BEGIN SECTION 2 -->
<div id="part2" style="display:none">
<P id="PartSlide" class="text-center  margin-top-10"><a href="javascript:;" class="btn default button-previous" style="display: inline-block;"> <i class="m-icon-swapleft"></i> Back </a></P>
<h4 class="form-section mt5">Part 2 - Protocol Format</h4>
<div class="" id="son">
  <ul class="nav nav-tabs fix">
    <li class="active"><a data-toggle="tab" href="#tab22">Request</a> </li>
    <li><a data-toggle="tab" href="#tab33">Response</a> </li>
  </ul>
  <div class="tab-content tab2-content ">
    <div id="tab22" class="tab-pane active">
      <div class="row" style="padding-left:0;">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Protocol Format</label>
            <select class="form-control" id="pageReqProFormat"> 
              <c:forEach var="lwm" items="${conTypeList}" varStatus="vsw">
		         <option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>				                
		      </c:forEach>
            </select>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Protocol Documentation</label>
            <input type="file">
          </div>
        </div>
      </div>
      <div class="row" style="padding-left:0;">
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Body Format </label>
            <textarea class="form-control" rows="3" id="pageReqBodyFormat"></textarea>
          </div>
        </div>
        <div class="col-md-1">
          <div class="form-group">
            <input type="button" value="Import" id="pageReqImport" class="btn btn-default margin-top-50"/>
          </div>
        </div>
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> Sample</label>
            <textarea class="form-control" rows="3" id="pageReqSample"></textarea>
          </div>
        </div>
      </div>
      <div class="row" style="padding-left:0;">
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> Description </label>
            <textarea class="form-control" rows="3" id="pageReqDescription"></textarea>
          </div>
        </div>
      </div>
      <input type="button" class="form-control" id="reqNodeListSubmit" value="submit">
      <form action="" method="post" id="reqForm">
      <table class="table table-bordered" id="pageReqTable">
        <thead>
          <tr>
            <th> Name</th>
            <th> Node Path </th>
            <th> Length Constraint </th>
            <th> Type Constraint </th>
            <th> Quantity Constraint </th> 
            <th> Value Constraint </th>
            <th> Validate </th>
            <th> 值约束表达式 </th>
            <th> Operation</th>
          </tr>
        </thead>
        <tbody>
        <!-- 
          <tr>
            <td>root</td>
            <td>/root</td>
            <td><input type="text" class="form-control"></td>
            <td><select name="" id="" class="form-control">
                <option value="1">String </option>
                <option value="2">Number </option>
             </select>
             </td>
            <td><select name="" id="" class="form-control">
                <option value="1">1</option>
                <option value="2">1-N</option>
                <option value="3">0-1</option>
                <option value="4">0-N</option>
              </select></td>
            <td><select name="" id="" class="form-control">
                <option value="1">Fixed </option>
                <option value="3">Main data </option>
                <option value="4">Exception Code </option>
              </select></td>
            <td><select name="" id="" class="form-control">
                <option value="Y">Yes</option>
                <option selected="" value="N">No</option>
              </select></td>
            <td><input type="text" class="form-control"></td>
          </tr>
           -->
        </tbody>
      </table>
      </form>
    </div>
    <div id="tab33" class="tab-pane">
      <div class="row" style="padding-left:0;">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Protocol Format</label>
            <select class="form-control" id="pageRspProFormat">
              <c:forEach var="lwm" items="${conTypeList}" varStatus="vsw">
		         <option value="${lwm.CEPVALUES}">${lwm.CEPNAME}</option>				                
		      </c:forEach>
            </select>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Protocol Documentation</label>
            <input type="file">
          </div>
        </div>
      </div>
      <div class="row" style="padding-left:0;">
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> <font color="FF0000">*</font> Body Format </label>
            <textarea class="form-control" rows="3" id="pageRspBodyFormat"></textarea>
          </div>
        </div>
        <div class="col-md-1">
          <div class="form-group">
            <input type="button" value="Import"  id="pageRspImport" class="btn btn-default margin-top-50"/>
          </div>
        </div>
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> Sample</label>
            <textarea class="form-control" rows="3"  id="pageRspSample"></textarea>
          </div>
        </div>
      </div>
      <div class="row" style="padding-left:0;">
        <div class="col-md-5">
          <div class="form-group">
            <label class="control-label"> Description </label>
            <textarea class="form-control" rows="3" id="pageRspDescription"></textarea>
          </div>
        </div>
      </div>
      <input type="button" class="form-control" id="rspNodeListSubmit" value="submit">
      <table class="table table-bordered" id="pageRspTable">
        <thead>
          <tr>
            <th> Name</th>
            <th> Node Path </th>
            <th> Length Constraint </th>
            <th> Type Constraint </th>
            <th> Quantity Constraint </th>
            <th> Value Constraint </th>
            <th> Validate </th>
            <th> 值约束表达式 </th>
            <th> Operation</th>
          </tr>
        </thead>
        <tbody>
        <!-- 
          <tr>
            <td>root</td>
            <td>/root</td>
            <td><input type="text" class="form-control"></td>
            <td><select name="" id="" class="form-control">
                <option value="1">String </option>
                <option value="2">Number </option>
              </select></td>
            <td><select name="" id="" class="form-control">
                <option value="1">1</option>
                <option value="2">1-N</option>
                <option value="3">0-1</option>
                <option value="4">0-N</option>
              </select></td>
            <td><select name="" id="" class="form-control">
                <option value="1">Fixed </option>
                <option value="3">Main data </option>
                <option value="4">Exception Code </option>
              </select></td>
            <td><select name="" id="" class="form-control">
                <option value="Y">Yes</option>
                <option selected="" value="N">No</option>
              </select></td>
            <td><input type="text" class="form-control"></td>
            <td><input type="button" class="form-control" value="delete"></td>
          </tr>
           -->
        </tbody>
      </table>
    </div>
  </div>
</div>
</div>
<script>
$(function(){
	$("#PartSubmit").click(function(){
		var componentId = $('#hidComponentId').val();
		var pageServiceName = $('#pageServiceName').val();
		var pageProCode = $('#pageProCode').val();
		var pageProVersionCode = $('#pageProVersionCode').val();
		var pageSelectedDir = $('#pageSelectedDir').val();
		var pageCommPro = $('#pageCommPro').val();
		var pageServiceAdd = $('#pageServiceAdd').val();
		var pageDescription = $('#pageDescription').val();
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
		    url: "../provider/addProvSev.shtml",
		    dataType:'json',
		    data:{componentId:componentId,
		    	pageServiceName:pageServiceName,
		    	pageProCode:pageProCode,
		    	pageProVersionCode:pageProVersionCode,
		    	pageSelectedDir:pageSelectedDir,
		    	pageCommPro:pageCommPro,
		    	pageServiceAdd:pageServiceAdd,
		    	pageDescription:pageDescription,
		    	pageContractId:pageContractId,
		    	pageDirSerlistId:pageDirSerlistId,
		    	pageServiceId:pageServiceId,
		    	pageTechImpAttId:pageTechImpAttId,
		    	pageTechimplId:pageTechimplId,
		    	pageContractVersionId:pageContractVersionId
		    	},
			success:function(data){ 
				if (data.code == "0000") {
					//alert(data.contractId);
					$('#hidContractId').val(data.contractId);
					//alert(data.dirSerlistId);
					$('#hidDirSerlistId').val(data.dirSerlistId);
					//alert(data.serviceId);
					$('#hidServiceId').val(data.serviceId);
					//alert(data.techImpAttId);
					$('#hidTechImpAttId').val(data.techImpAttId);
					//alert(data.techimplId);
					$('#hidTechimplId').val(data.techimplId);
					$('#hidContractVersionId').val(data.contractVersionId);
					$('#pageProCode').attr('readonly','true');
					$('#pageProVersionCode').attr('readonly','true');
					$("#part1").hide();
					$("#part2").show();
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}else {
					if(undefined != data.desc.serviceName){
						toastr.error(data.desc.serviceName);
					}
					if(undefined != data.desc.attrSpecValue){
						toastr.error(data.desc.attrSpecValue);
					}
					if(undefined != data.desc.descriptor){
						toastr.error(data.desc.descriptor);
					}
					if(undefined != data.desc.contractCode){
						toastr.error(data.desc.contractCode);
					}
					if(undefined != data.desc.contractVersionCode){
						toastr.error(data.desc.contractVersionCode);
					}
				}
	          }
	      });
		});
	$("#PartSlide").click(function(){
		$("#part2").hide();
		$("#part1").show();
		});
	$('#pageReqImport').click(function(){
		//alert('pageReqImport');
		var pageContractVersionId = $('#hidContractVersionId').val();
		var pageReqProFormat = $('#pageReqProFormat').val();
		var pageReqBodyFormat = $('#pageReqBodyFormat').val();
		var pageReqSample = $('#pageReqSample').val();
		var pageReqDescription = $('#pageReqDescription').val();
		var pageReqRsp = 'REQ';
		var pageTcpCtrFId = $('#hidTcpCtrFId').val();
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/addConFormat.shtml",
		    dataType:'json',
		    data:{pageContractVersionId:pageContractVersionId,
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
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	$('#pageRspImport').click(function(){
		var pageContractVersionId = $('#hidContractVersionId').val();
		var pageRspProFormat = $('#pageRspProFormat').val();
		var pageRspBodyFormat = $('#pageRspBodyFormat').val();
		var pageRspSample = $('#pageRspSample').val();
		var pageRspDescription = $('#pageRspDescription').val();
		var pageReqRsp = 'RSP';
		var pageTcpCtrFId = $('#hidRspTcpCtrFId').val();
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/addRspConFormat.shtml",
		    dataType:'json',
		    data:{pageContractVersionId:pageContractVersionId,
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
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	$('#reqDelete').live('click',function(){
		var pageTcpCtrFId = $('#hidTcpCtrFId').val();
		var pageNodeDescId = $(this).attr('delId');
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/deleteDescNode.shtml",
		    dataType:'json',
		    data:{
		    	pageNodeDescId:pageNodeDescId,
		    	pageTcpCtrFId:pageTcpCtrFId
		    	},
			success:function(data){ 
				if (data.code == "0000") {
					$('#pageReqTable>tbody').empty();
					$('#pageReqTable>tbody').append(data.result);
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	$('#rspDelete').live('click',function(){
		var pageTcpCtrFId = $('#hidRspTcpCtrFId').val();
		var pageNodeDescId = $(this).attr('delId');
		$.ajax({
			type: "POST",
			async:false,
		    url: "../provider/deleteRspDescNode.shtml",
		    dataType:'json',
		    data:{
		    	pageNodeDescId:pageNodeDescId,
		    	pageTcpCtrFId:pageTcpCtrFId
		    	},
			success:function(data){ 
				if (data.code == "0000") {
					$('#pageRspTable>tbody').empty();
					$('#pageRspTable>tbody').append(data.result);
				}else if(data.code == "0002"){
					toastr.error(data.result);
				}
	          }
	      });
	});
	$('#reqNodeListSubmit').click(function(){
		alert(111);
		$('#reqForm').attr('action','../provider/updateNodeList.shtml');
		$('#reqForm').submit();
	});
	});
</script>
<!-- END SECTION 2 -->
