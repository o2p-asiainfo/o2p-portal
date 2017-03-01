<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<table class="table table-bordered" id="userDefineTable">
 <thead>
  <tr>
   <th> <i class="fa fa-plus insert btn btn-xs theme-btn"></i> </th>
   <th> Class </th>
   <th> Capability Name </th>
   <th> Capability Description </th>
  </tr>
 </thead>
 <tbody>
  <c:forEach items="${userDefinedList}" var="obj" varStatus="status">
   <tr>
    <td>
    ${obj.DIRNAME}
    </td>
    <td>${obj.CNNAME}</td>
    <td>${obj.SERVICEDESC}</td>
    <td><a data-target="#myModal" data-toggle="modal" class="btn default btn-sm edit" id='editUserDefine' editvalue='${obj.TECHIMPATTID}'>Edit</a> <a class="btn default btn-sm" href="javascript:;">Delete</a></td>
   </tr>
   </c:forEach>
 </tbody>
</table>


