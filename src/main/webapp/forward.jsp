<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="0">
<%@ page language="java" import="com.asiainfo.integration.o2p.web.util.WebPropertyUtil"  pageEncoding="UTF-8"%>
<%  
   String frontEndUrl=WebPropertyUtil.getPropertyValue("frontEnd_url");
   response.sendRedirect(frontEndUrl+"/portal/360.jsp");      
%> 