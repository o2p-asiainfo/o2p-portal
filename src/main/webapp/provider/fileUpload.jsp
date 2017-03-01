<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String type = request.getParameter("type");
%>
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<form method="POST" id="reqFile" action="${APP_PATH}/provider/uploadFile.shtml"  enctype="multipart/form-data">
   <input type="file" id="file" name="file" onchange="javascript:uploadImg();" multiple>
</form>
<script type="text/javascript">
var type = '${type}';
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
    		  if('req' == type){
    			  window.parent.document.getElementById('pageReqBodyFormat').value = data;
    		  }else{
    			  window.parent.document.getElementById('pageRspBodyFormat').value = data;
    		  }
    		  
    		  //$('#pageReqBodyFormat').val(data);
    	  }
      });
}
</script>


