<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<%
	String navBarPageId = request.getParameter("navBarPageId");
	navBarPageId = StringEscapeUtils.escapeHtml(navBarPageId);
	request.setAttribute("navBarPageId",navBarPageId);
	request.setAttribute("home",request.getContextPath());
%>
<title>Super Operator Open Operational Platform</title>
<script type="text/javascript">
	var APP_PATH = '${APP_PATH}';	 
	var RESULT_OK = "0000";
	var RESULT_ERR = "0001"; 
</script>
<!-- BEGIN HEAD -->
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
