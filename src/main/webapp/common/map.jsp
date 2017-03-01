
<script type="">
$(document).ready(function(){
	$("#buttonadddept").click(function(){
    	 $("#latitudeAndLongitude").val("55.42N,12.36E");

		  $("#zoneMapModalList").find(".close").click();
     });
      
      $("#buttonCancel").click(function(){
    	  $("#zoneMapModalList").find(".close").click();
      });
});
</script>

<div class="container" id="zoneMapModalList" class="modal container fade" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X—</button>
    <h4 class="modal-title">Zone Map</h4>
  </div>
	<div class="modal-body">
	 <div id="test">
	 	<img src="${APP_PATH}/common/dk.png" alt="" />
	 </div>
	 <table class="mgr-table" id="confirm">
		   <tr>
		     <td colspan="4"  align="center">
		     	
		      </td>	
	     </tr>
	  </table>
	</div>
	<div class="modal-footer">
	<button type="button" class="btn btn-default" id="buttonCancel">Cancel</button>
    <button type="submit" class="btn theme-btn" id="buttonadddept" data-dismiss="modal">Submit</button>
  </div>
</div>
