var oTable2;
var VariableMap = function() {
    var handleDataTable = function() {
        //初始化表格数据
        var oTable = $('#dt2').dataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            //"lengthMenu": [5, 10, 15, "All"],
            "ajax": "../newadapter/getValableMap.shtml",
            "ordering": false,
            "autoWidth": false,
            "columns": [{
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="row-details-btn row-details-close"></span>'
            }, {
                "data": "varMappingId"
            }, {
                "data": "keyExp"
            }, {
                "data": "valExp"
            }],
            "columnDefs": [{
                "width": "36px",
                "targets": 0,
                "render": function(data, type, full, meta) {
                    var html = "<input type='radio' value='"+full.varMappingId+"' class='radio-fix' name='radiobox' />";
                	return html;
                }
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        jQuery('#dt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
        
        
       
        $('#btn-add2').click(function(){
        	var pageConsMapCD = $('#pageConsMapCDFinal').val();
        	if('' == pageConsMapCD){
        		alert(addFirst);
        		return;
        	}
        	$('#saveOrUpdateFlag').val('add');
        	$('#dataMatch').modal('show');
        });
        $('#btn-edit2').click(function(){
        	var oSelected = $("#dt2>tbody input[type='radio']:checked");
        	if (oSelected.size() == 1) {
        		var trselected = $("#dt2>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	var varMappingId = selected.varMappingId;
            	var dataSource = selected.dataSourceNum;
            	var keyExp = selected.keyExp;
            	var valExp = selected.valExp;
            	$('#saveOrUpdateFlag').val('update');
            	$('#pageVarMappingId').val(varMappingId);
//            	$('#pageDataSource').val(dataSource);
            	$("#pageDataSource").select2("val", dataSource);//设置值选中
            	$('#pageKeyExp').val(keyExp);
            	$('#pageValExp').val(valExp);
            	$('#dataMatch').modal('show');
        	}else{
        		alert(chooseRecords);
        	}
        });
        $('#btn-del2').click(function(){
        	var oSelected = $("#dt2>tbody input[type='radio']:checked");
        	if (oSelected.size() == 1) {
        		var trselected = $("#dt2>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	var varMappingId = selected.varMappingId;
            	var pageConsMapCD = $('#pageConsMapCDFinal').val();
            	var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
            	$.ajax({
    				type: "POST",
    				async:false,
    			    url: "../newadapter/delVariableMap.shtml",
    			    dataType:'json',
    			    data:{pageContractAdapterId:pageContractAdapterId,pageVarMappingId:varMappingId,pageConsMapCD:pageConsMapCD},
    				success:function(msg){
    					if(msg && msg.result == 'success'){
    						$('#dataMatch').modal('hide');
        					oTable.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+pageConsMapCD).load();
    					}else{
    						toastr.error(msg.desc);
    					}
    	            }
    	        });
        	}else{
        		alert(chooseRecords);
        	}
        });
        $('#ProtocolMChoose').click(function(){
        	var pageDataSource = $('#pageDataSource').val();
        	var pageKeyExp = $('#pageKeyExp').val();
        	if('' == pageKeyExp){
        		alert(keyExpnotnull);
        		return;
        	}
        	var pageValExp = $('#pageValExp').val();
        	var pageConsMapCD = $('#pageConsMapCDFinal').val();
        	var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
        	var flag = $('#saveOrUpdateFlag').val();
        	if('add' == flag){
        		$.ajax({
    				type: "POST",
    				async:false,
    			    url: "../newadapter/addVariableMap.shtml",
    			    dataType:'json',
    			    data:{pageDataSource:pageDataSource,pageKeyExp:pageKeyExp,pageContractAdapterId:pageContractAdapterId,pageValExp:pageValExp,pageConsMapCD:pageConsMapCD},
    				success:function(msg){
    					if(msg && msg.result == 'success'){
    						$('#dataMatch').modal('hide');
        					oTable.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+pageConsMapCD).load();
    					}else{
    						toastr.error(msg.desc);
    					}
    	            }
    	        });
        	}else{
        		var pageVarMappingId = $('#pageVarMappingId').val();
        		$.ajax({
    				type: "POST",
    				async:false,
    			    url: "../newadapter/updateVariableMap.shtml",
    			    dataType:'json',
    			    data:{pageDataSource:pageDataSource,pageKeyExp:pageKeyExp,pageContractAdapterId:pageContractAdapterId,pageValExp:pageValExp,pageConsMapCD:pageConsMapCD,pageVarMappingId:pageVarMappingId},
    				success:function(msg){
    					if(msg && msg.result == 'success'){
    						$('#dataMatch').modal('hide');
        					oTable.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+pageConsMapCD).load();
    					}else{
    						toastr.error(msg.desc);
    					}
    	            }
    	        });
        	}
        });
        $('#ConsMapButton').click(function(){
        	var pageConsMapCD = $('#pageConsMapCD').val();
        	var pageConsMapName = $('#pageConsMapName').val();
        	if(pageConsMapCD == ""){
        		 alert($.i18n.prop('eaap.op2.portal.message.varMapTypeIsNotNull'));
         		 return;
        	}
        	if(pageConsMapName == ""){
        		alert($.i18n.prop('eaap.op2.portal.message.nameIsNotNull'));
        		 return;
        	}
        	
	      	 if(pageConsMapCD.length>16 || pageConsMapName.length>150){
	      		  alert(toolong);
	      		  return;
	      	 }
      	$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/addVarMapType.shtml",
		    dataType:'json',
		    data:{pageConsMapCD:pageConsMapCD,pageConsMapName:pageConsMapName},
			success:function(msg){ 
				if("success"==msg.result){
					alert('success');
					$('#pageConsMapCDFinal').val(pageConsMapCD);
					oTable.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+pageConsMapCD).load();
				}else{
					alert(msg.errMsg);
				}
           }
        });
        });
        oTable2 = oTable;
    };

    return {
        init: function() {
            handleDataTable();
        },
    };
}();
