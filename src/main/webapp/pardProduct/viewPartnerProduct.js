var PardProductView = function() {
	
	var viewPardProduct = function(){
		$("#productInfoForm").find("*").attr("disabled","disabled");
		
		var form = document.productForm;
		//提交审核事件
		$("#submitToCheckProductBTN").click(function(e){
			form.action = APP_PATH+"/pardProduct/submitCheck.shtml";
			form.submit();
		});
		//修改事件
		$("#modifyProductBTN").click(function(e){
			form.action = APP_PATH+"/pardProduct/toUpdate.shtml";
			form.submit();
		});
		//删除事件
		$("#delProductBTN").click(function(e){
			form.action = APP_PATH+"/pardProduct/delete.shtml";
			form.submit();
		});
		
	}
	
	var productAttributeValueFunction = function(){
   	 $('#productAttributeValueModal').on('show.bs.modal', function(e) {
	    	var productAttributeValueTable = $('#productAttributeValueDT').dataTable({
	            "processing": true,
	            "serverSide": true,
	            "searching": false,
	            "ajax": "../pardSpec/attrSpecValuelist.shtml",
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
	    	jQuery('#productAttributeValueDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
	        jQuery('#productAttributeValueDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
   	 });
   	$('.cancelAttrValueBtn').bind('click', function() {
		$("#productAttributeValueModal").modal("hide");
     });
   	
   }
	
	var handleServiceAttributeModal = function(serviceId){
		$.ajax({
			type: "POST",
			async:false,
		    url: APP_PATH+"/pardProduct/serviceSpecAttrList.shtml",
		    dataType:'json',
		    data:{serviceId:serviceId},
			success:function(retData){
				if(retData.code=="0000"){
					serviceSpecTabAddAll(retData.desc);
				}else{
					toastr.error(retData.desc);
				}
	        }
	   });
    	
    	function serviceSpecTabAddAll(data){
    		$('#serviceSpecTab>tbody>tr').remove();
    		if(!data||!data[0]){
    			var tr = "<tr style='text-align:center;'>None</tr>";
    			$("#serviceSpecTab>tbody").append(tr);
    		}else{
    			var serviceSpecTabCurrIndex = $('#serviceSpecTab>tbody>tr').length;
        		$.each(  
    			    data,  
    			    function(key) {
    			    	var obs,chooseAttrSpecId,chooseAttrSpecName,chooseAttrSpecCode,chooseAttrSpecType,chooseAttrSpecDesc;
    		        	var chooseIsCustomized,chooseIsCustomizedText,changeType,chooseDefaultValue,chooseFrom,chooseTo,chooseServAttrValueId;
    		    		
    		        	chooseServAttrValueId = data[key]["SERV_ATTR_VALUE_ID"];
    		        	chooseAttrSpecId = data[key]["ATTR_SPEC_ID"];
    		        	chooseAttrSpecName = data[key]["ATTR_SPEC_NAME"];
    		    		chooseAttrSpecCode = data[key]["ATTR_SPEC_CODE"];
    		    		chooseAttrSpecType = data[key]["PAGE_IN_TYPE"];
    		    		chooseAttrSpecDesc = data[key]["ATTR_SPEC_DESC"];
    		    		chooseIsCustomized = data[key]["IS_CUSTOMIZED"];
    		    		chooseIsCustomizedText = data[key]["IS_CUSTOMIZED_FORMAT"];
    		    		chooseDefaultValue = data[key]["DEFAULT_VALUE"];
    		    		changeType = data[key]["PAGE_IN_TYPE_FORMAT"];
    		    		
    		    		var tr = "";
    		    		serviceSpecTabCurrIndex ++;
    		    		if(chooseAttrSpecType=="1"||chooseAttrSpecType=="2"||chooseAttrSpecType=="5"||chooseAttrSpecType=="6"||chooseAttrSpecType=="7"){
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    		 	 	  		 	+"<td><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    		 	 	    		+chooseDefaultValue
    		 	 	  		    +"</tr>";
    		 	 	    }else if(chooseAttrSpecType=="3"){
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    			 	  		 	    +"<td><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"'/><input type='hidden' name='attrFrom' value='"+chooseFrom+"'/><input type='hidden' name='attrTo' value='"+chooseTo+"'/><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    			 	  		        +chooseDefaultValue
    			 	  		 	    +"</tr>";
    		 	 	    }else if(chooseAttrSpecType=="4"){
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='TEXT-ALIGN:left;white-space: normal;'>"+chooseAttrSpecName+"</td><td style='TEXT-ALIGN:left;white-space: normal'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='TEXT-ALIGN:left;white-space:pre-wrap;' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    		 	   	  		 	+"<td><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' /><input name='defaultValue' id='defaultEum' value='' type='hidden'/> "
    		 	   	  		+"<a class='button-base button-small' onclick=\"javascript:handleSelectServ('"+chooseAttrSpecId+"','"+chooseServAttrValueId+"');\" title='view'><span class=\"button-text\"> View</span></a>"
    		 	   	  		 	+"</td></tr>";
    		 	  	    }
    			    	$("#serviceSpecTab>tbody").append(tr);
    		    });
    		}
    		
    	}
    }
	
	var showmessage = function() {
        var xsize,
            ysize,
            href,
            $modal,
            img,
            realWidth,
            realHeight,
            $this = $('.showMessage'),
            size = $this.size();
        //存在修改头像的，先创建弹窗模板   
//        $('body').append('<div id="showMessageModal" class="modal container fade showMessageModal" tabindex="-1"></div>');
        xsize = $this.attr('data-width')+'px';
        ysize = $this.attr('data-height')+'px';
        href = $this.attr('data-href');
        //点击查询消息
        $('.showMessage').bind('click', function() {
            $modal =  $('#showMessageModal');
            $modal.css({'width':xsize,'height':ysize});
            $modal.load(href, '', function() {
                $modal.modal('show');
            });
        });
        
	}
	
	var serviceSpecAttributeValueFunction = function(){
	   	 $('#serviceSpecAttributeValueModal').on('show.bs.modal', function(e) {
		    	var serviceSpecAttributeValueTable = $('#serviceSpecAttributeValueDT').dataTable({
		            "processing": true,
		            "serverSide": true,
		            "searching": false,
		            "ajax": "../pardSpec/attrSpecValuelist.shtml",
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
        	viewPardProduct();
        	handleServiceAttributeModal(serviceId);
        	productAttributeValueFunction();
        	showmessage();
        	serviceSpecAttributeValueFunction();
        }
    };
}();