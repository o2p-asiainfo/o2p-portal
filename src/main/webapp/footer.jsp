
<script src="${APP_PATH}/resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/back-to-top.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script> 
<script src="${APP_PATH}/resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/plugins/jquery.i18n.properties-min-1.0.9.js" type="text/javascript" ></script>
<script src="${APP_PATH}/resources/scripts/app.js"></script>  
<!-- END CORE PLUGINS -->
<script>
$(function () {
	var LANGUAGE = '${language}'; 
	jQuery.i18n.properties({
		name : 'eaap-op2-portal-messages',        
		path : APP_PATH+'/resources/i18n/',
		language: LANGUAGE, 
		mode : 'map'
	});
});	
</script>



