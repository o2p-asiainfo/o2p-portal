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
 <h4 class="modal-title">${local["eaap.op2.portal.doc.generalService"]}</h4>
</div>
<div class="modal-body">
 <div class="step step1">
  <table class="table table-bordered table-striped table-hover text-center group-check2" id="dt">
   <thead>
    <tr>
     <th style="width:24px">
     </th>
     <th> ${local["eaap.op2.portal.doc.developName"]} </th>
     <th> ${local["eaap.op2.portal.doc.developMemo"]} </th>
    </tr>
   </thead>
   <tbody>
   </tbody>
  </table>
 </div>
 <div class="step step2 row" id="serviceAttrAdd">
 <%-- 
  <div class="col-md-6">
   <div class="form-group">
    <label class="control-label">
     <font color="FF0000">*</font>testSer527:</label>
  <input type="text" id="s1" name="attrspecvalue" class="form-control" placeholder="Service Address" techimpattid="100000085">
   </div>
  </div>
  --%>
 </div>
</div>
<div class="modal-footer">
 <button type="button" class="btn btn-default step2" id="back">${local["eaap.op2.portal.doc.message.back"]}</button>
 <button type="button" class="btn theme-btn step1" id="next">${local["eaap.op2.portal.doc.message.next"]}</button>
 <button type="button" class="btn theme-btn step2" id="ok">${local["eaap.op2.portal.doc.message.ok"]}</button>
</div>

