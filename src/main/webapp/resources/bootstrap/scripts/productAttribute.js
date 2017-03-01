var ProductAttribute = function() {
	var handleSelect2 = function() {
        $(".js-example-placeholder-single").select2();
    };
    var handleDataTable = function() {
        //初始化表格数据
        var oTable = $('#dt').dataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            //"lengthMenu": [5, 10, 15, "All"],
            "ajax": "../newadapter/getContractList.shtml",
            "ordering": false,
            "autoWidth": false,
            "columns": [{
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="row-details-btn row-details-close"></span>'
            }, {
                "data": "name"
            }, {
                "data": "version"
            }, {
                "data": "contype"
            }, {
                "data": "req_rsp"
            }, {
                "data": "tcpcrtfid"
            }],
            "columnDefs": [{
                "width": "36px",
                "targets": 0,
                "render": function(data, type, full, meta) {
                    var html = "<input type='radio' value='"+full.tcpcrtfid+"' class='radio-fix' name='radiobox' />";
                	return html;
                }
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        jQuery('#dt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
        
        $('#chooseRight').click(function(){
        	if(changeRigthContract()>0){
        		alert(notchooseTar);
    		}else{
    			$('#selectActioin').val("R");
        		var url = '../newadapter/getContractList.shtml';
    	        oTable.api().ajax.url(url).load();
    	        $('#myModal').modal('show');
    		}
        });
        $('#chooseLeft').click(function(){
        	//if(changeRigthContract()>0){
        		//alert(notchooseSrc);
    		//}else{
    			$('#selectActioin').val("L");
            	var pageContractAdapterId = $("#pageContractAdapterId").val();//协议转化ID
        		var url = "../newadapter/getContractList.shtml?pageContractAdapterId="+pageContractAdapterId;
    	        oTable.api().ajax.url(url).load();
    	        $('#myModal').modal('show');
    		//}
        	
        });
        /**
         * 表格查询操作
         */
        $('#queryContract').click(function(){
        	var pageProtocolName = $('#pageProtocolName').val();
        	var pageContractVersion = $('#pageContractVersion').val();
        	var pageProtocolType = $('#pageProtocolType').val();
        	var pageReqRsp = $('#pageReqRsp').val();
        	var url = '../newadapter/getContractList.shtml?pageProtocolName='+pageProtocolName
        	        + '&pageContractVersion='+pageContractVersion
        	        + '&pageProtocolType='+pageProtocolType
        	        + '&pageReqRsp='+pageReqRsp;
        	oTable.api().ajax.url(url).load();
        });
        $("#chooseContract").click(function(){
        	//判断是否有选择
            var oSelected = $("#dt>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	$('#myModal').modal('hide');
            	var trselected = $("#dt>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	var action = $('#selectActioin').val();
            	if("L" == action){
            		$('#srcContractName').val(selected.name);
                	$('#srcContractVersionName').val(selected.version);
                	$('#srcContractType').val(selected.contype);
                	//设置源节点选中ID
                	$('#pageSrcTcpCtrFId').val(selected.tcpcrtfid);
                	//$('ul.l').remove();//清空之前节点
                	//加载左节点
                	getNodesc(selected.tcpcrtfid,"L",selected.contypeNum,selected.name,getLeftContractNum());
                	//清空多余表格
                	/*$('#FlowTable>table').each(function(){
                		var id = $(this).attr('id');
                		if(id.indexOf('Rightable')>0){//说明是右边表格
                			
                		}else{//说明是左边表格
                			$(this).remove();
                		}
                	});*/
                	//添加新表格
                	addTable(selected.name,selected.tcpcrtfid,'L');
            	}else if("R" == action){
            		$('ul.r').remove();//清空
            		//清除之前表格记录
        			$('#'+$('#pageTarTcpCtrFId').val()+'Rightable').remove();
            		$('#tarContractName').val(selected.name);
                	$('#tarContractVersionName').val(selected.version);
                	$('#tarContractType').val(selected.contype);
                	//设置目标节点选中ID
                	$('#pageTarTcpCtrFId').val(selected.tcpcrtfid);
                	//清空源节点选中ID
                 	$('#pageSrcTcpCtrFId').val('');
                	//加载右节点
                	getNodesc(selected.tcpcrtfid,"R",selected.contypeNum,selected.name,0);
                	addTable(selected.name,selected.tcpcrtfid,'R');
            	}
            	
            } else {
                alert(chooseRecords);
            }
        });
    };

    return {
        init: function() {
            handleDataTable();
            handleSelect2();
        },
    };
}();
/**
 * 加载节点方法
 * @param tcpcrtfid
 * @param action
 * @param conType
 */
function getNodesc(tcpcrtfid,action,conType,contractName,contractNum){
	//加载节点
	$.ajax({
		type: "POST",
		async:false,
	    url: "../newadapter/getNodeDesc.shtml",
	    dataType:'json',
	    data:{pageTcpCtrFId:tcpcrtfid,pageLoadSideFlg:action,pageContractType:conType,pageContractName:contractName,pageContractNum:contractNum},
		success:function(msg){ 
			      loadData(msg);
				  saveContractAdapter();
          }
     });
}
/**
 * 保存协议转化数据
 */
function saveContractAdapter(){
	var pageTarTcpCtrFId = $('#pageTarTcpCtrFId').val();//目标节点
	var pageSrcTcpCtrFId = $('#pageSrcTcpCtrFId').val();//源节点
		$.ajax({
			type: "POST",
			async:true,
		    url: "../newadapter/saveAdapterConfig.shtml",
		    dataType:'json',
		    data:{pageSrcTcpCtrFId:pageSrcTcpCtrFId,pageTarTcpCtrFId:pageTarTcpCtrFId,type:'1',svname:$('#srcContractVersionName').val(),tvname:$('#tarContractVersionName').val(),pageContractAdapterId:$('#pageContractAdapterId').val(),pageEndpointId:$('#pageEndpointId').val()},
			success:function(msg){
				 $("#pageContractAdapterId").val(msg.pageContractAdapterId);
          }
     });
}
/**
 * 判断拉线是否存在
 * @returns {Number}
 */
function changeRigthContract(){
	var num = 0;
	$.each($('#FlowTable>table>tbody>tr'), function () {
		num++;
	});
	return num;
}

function getLeftContractNum(){
	var num = 0;
	$.each($('#UniversalAdapterDemo .GooFlow_work .baseversion>ul'), function () {
		num++;
	});
	return num;
}
function addTable(name,id,action){
	if('L' == action){
		id = id + 'table';
	}else{
		id = id + 'Rightable';
	}
  var tableValue = '<table class="table table-bordered table-hover adapertable" id='+id+'>'
		         + '<thead>'
		         + '<tr class="thead"><th colspan="5"> <span class="d-slide"></span> '+name+' </th></tr>'
		         + '<tr>'
		         + '<th width="5%"> # </th>'
		         + '<th width="30%">Source Node Path </th>'
		         + '<th width="30%">Target Node Path </th>'
		         + '<th width="15%">Operation </th>'
		         + '<th width="20%">Value of Operation </th>'
		         + '</tr>'
		         + '</thead>'
		         + '<tbody></tbody></table>';
  $('#FlowTable').append(tableValue);
}
