var PardServiceSpecView = function() {
	
	var viewServiceSpec = function(){
		$("#serviceSpecInfoForm").find("*").attr("disabled","disabled");
		
		//修改事件
		$("#modifyServiceSpecBTN").click(function(e){
			document.serviceSpecInfoForm.action = APP_PATH+"/pardServiceSpec/toServiceSpecAddOrUpdate.shtml?serviceSpecId="+$("#serviceSpecId").val();
			document.serviceSpecInfoForm.submit();
		});
		
		//删除事件
		$("#delServiceSpecBTN").click(function(e){
			document.serviceSpecForm.action = APP_PATH+"/pardServiceSpec/delServiceSpec.shtml";
			document.serviceSpecForm.submit();
		});
		
	}
	
	var serviceSpecAttributeValueFunction = function(){
   	 $('#serviceSpecAttributeValueModal').on('show.bs.modal', function(e) {
	    	var serviceSpecAttributeValueTable = $('#serviceSpecAttributeValueDT').dataTable({
	            "processing": true,
	            "serverSide": true,
	            "searching": false,
	            "ajax": APP_PATH + "/pardSpec/attrSpecValuelist.shtml",
	            "order": [],
	            "autoWidth": false,
	            "columns": [
	                      {"data": "INDEX"},
	                      {"data": "ATTR_VALUE_ID"},
	                      {"data": "ATTR_SPEC_ID"},
	                      {"data": "ATTR_VALUE_NAME"},
	                      {"data": "ATTR_VALUE"},
	                      {"data": "ATTR_DESC"},
	                      {"data": "SEQ_ID"},
	                      {"data": "IS_DEFAULT"}
	                  ],
	            "columnDefs": [{
	                "orderable": false,
	                "targets":  [0,1,2,3,4,5,6],
	                "width": "25px"
	            },{
	                "visible": false,
	                "targets": [1,2,5,6,7]
	            }],
	            "initComplete": function() {
	                App.initUniform();
	            }
	        });
	    	jQuery('#serviceSpecAttributeValueDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
	        jQuery('#serviceSpecAttributeValueDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
   	 });
   	$('.cancelAttrValueBtn').bind('click', function() {
		$("#serviceSpecAttributeValueModal").modal("hide");
     });
   	
   }
	
	return {
        init: function(serviceId) {
        	viewServiceSpec();
        	serviceSpecAttributeValueFunction();
        }
    };
}();