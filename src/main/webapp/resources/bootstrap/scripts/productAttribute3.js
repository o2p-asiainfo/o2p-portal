var ProductAttribute3 = function() {

    var handleDataTable = function() {
        //初始化表格数据
        var oTable = $("#dt3").dataTable({
			
            "processing": true,
            "serverSide": true,
            "searching": false,
            //"lengthMenu": [5, 10, 15, "All"],
            "ajax": "../newadapter/getVarMapTypeList.shtml",
            "ordering": false,
            "autoWidth": false,
            "columns": [{
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="row-details-btn row-details-close"></span>'
            }, {
                "data": "id"
            }, {
                "data": "varMapType"
            }, {
                "data": "name"
            }],
            "columnDefs": [{
                "width": "36px",
                "targets": 0,
                "render": function(data, type, full, meta) {
                    var html = "<input type='radio' value='"+full.id+"' class='radio-fix' name='radiobox' />";
                	return html;
                }
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        jQuery('#dt3_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt3_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
        

        $('#pageWinQuery').click(function(){
        	var pageConsMapCD = $('#pageWinVarType').val();
        	var pageConsMapName = $('#pageWinName').val();
        	var url = "../newadapter/getVarMapTypeList.shtml?pageConsMapCD="+pageConsMapCD+"&pageConsMapName="+pageConsMapName;
	        oTable.api().ajax.url(url).load();
        });
        $('#pageWinDel').click(function(){
        	//判断是否有选择
            var oSelected = $("#dt3>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	var trselected = $("#dt3>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	$.ajax({
        			type: "POST",
        			async:false,
        		    url: "../newadapter/deleteVarMapType.shtml",
        		    dataType:'json',
        		    data:{pageVarMapTypeId:selected.id},
        			success:function(msg){
        				if(msg && msg.result == 'success'){
        					oTable.api().ajax.reload();
        				}else{
        					toastr.error(msg.desc);
        				}
        	         }
        	     });
            }else{
            	toastr.error(chooseRecords);
            }
        });
        $('#pageWinSelect').click(function(){
        	resetFrom();
        	var url = "../newadapter/getVarMapTypeList.shtml";
	        oTable.api().ajax.url(url).load();
        });
        $('#pageWinOK').click(function(){
        	//判断是否有选择
            var oSelected = $("#dt3>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	var trselected = $("#dt3>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	$('#pageConsMapCDFinal').val(selected.varMapType);
            	$('#pageConsMapCD').val(selected.varMapType);
            	$('#pageConsMapName').val(selected.name);
            	oTable2.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+selected.varMapType).load();
            	$('#MetaData').modal('hide');
            }else{
            	toastr.error(chooseRecords);
            }
        	
        });
        //重置按钮
        $('#reSetButton').click(function(){
        	$('#pageConsMapCDFinal').val('');
        	$('#pageConsMapCD').val('');
        	$('#pageConsMapName').val('');
        	oTable2.api().ajax.url('../newadapter/getValableMap.shtml').load();
        });
        /**
         * 重置表单
         */
        function resetFrom() {
            $('form').each(function(index) {
                $('form')[index].reset();
                $('form').find('.help-block').remove();
                $('form').find('.form-group').removeClass('has-error');
            });
        }

    };

    return {
        init: function() {
            handleDataTable();
        },

    };
}();
