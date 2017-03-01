<div class="modal-header">
 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
 </button>
 <h4 class="modal-title" id="myModalLabel"><font id="title">Upload Image</font></h4> 
</div>
<div class="modal-body" style="overflow:hidden;">
 <img id="ImgPr" src="${APP_PATH}/resources/img/avatar.jpg" onclick="document.getElementById('file').click();" title="Choose an Image File..." style="cursor:pointer;" onmouseover="this.style.border='1px solid #FF8503';" onmouseout="this.style.borderWidth='0';"/>
  <form method="post" id="crop_form" enctype="multipart/form-data">
  	<input type="file" id="file" class="display-none" name="file" multiple>
  </form> 
</div>
<div class="modal-footer">
 <input class="btn btn-default" data-dismiss="modal" type="button" id="canBtn" value=''></input>
 <input class="btn theme-btn ok" type="button" id="upImageBtn" value=''></input>
</div>
<script src="${APP_PATH}/resources/scripts/previewImage.js"></script>  
<script src="${APP_PATH}/resources/plugins/jquery.i18n.properties-min-1.0.9.js" type="text/javascript" ></script>
<script>
$(function () {
	var LANGUAGE = '${language}'; 
	jQuery.i18n.properties({
		name : 'eaap-op2-portal-messages',        
		path : APP_PATH+'/resources/i18n/',
		language: LANGUAGE, 
		mode : 'map'
	});
	$("#title").html($.i18n.prop('eaap.op2.portal.message.changeYourImage'));
	$("#canBtn").val($.i18n.prop('eaap.op2.portal.message.cancel'));
	$("#upImageBtn").val($.i18n.prop('eaap.op2.portal.message.sumbit'));
	$("#file").uploadPreview({ Img: "ImgPr", Width: 250, Height: 250 });
	$("#upImageBtn").click(function(){
		if(!validator()){
			return;
		}
		var formData = new FormData(document.getElementById("crop_form"));
		$.ajax({
            url: APP_PATH + "/fileShare/uploadImage.shtml",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  
            contentType: false 
          }).done(function(data) {
        	  if(data){
        		  uploadCallback(data);
        		  $("#canBtn").click();
        	  }
          });
	});
});	

function validator(){
	var filePath = jQuery("#file").val();
	if (!filePath && jQuery.trim(filePath)=="") {
		toastr.warning($.i18n.prop('eaap.op2.portal.messaes.imageSelectError')); 
		return false;  
	}
	
	var filter = "jpg,png,gif,bmp";
	var tst = new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(filePath);
	if (!tst) {
		toastr.warning($.i18n.prop('eaap.op2.portal.messaes.imageTypeError'));
		return false;
	}
	return true;
}
</script>
