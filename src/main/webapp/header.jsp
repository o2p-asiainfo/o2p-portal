<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
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
<title>${APP_WEB_TITLE}</title>
<script type="text/javascript">
	var APP_PATH = '${APP_PATH}';	 
	var RESULT_OK = "0000";
	var RESULT_ERR = "0001"; 
</script>
<!-- BEGIN HEAD -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- BEGIN GLOBAL MANDATORY STYLES -->
 <link href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

<link rel="shortcut icon" href="favicon.ico" />
