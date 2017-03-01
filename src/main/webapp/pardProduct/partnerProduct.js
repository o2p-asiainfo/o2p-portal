var AddPartnerProduct = function() {

    var handleForm = function() {

        $('#productForm').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
            	productName: {
                    required: true,
                    minlength: 4,
                    maxlength: 50
                },
                extProdId: {
                    required: true,
                    minlength: 1,
                    maxlength: 32
                },
            },
            messages: {
            	productName: {
                    required: "Product name is required and max length less than 50."
                },
                extProdId: {
                    required: "Product code is required and max length less than 50."
                },
            },

            invalidHandler: function(event, validator) { //display error alert on form submit                       
            	App.scrollTop();
            },

            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group 
            },

            success: function(label) {        
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                // error.insertAfter(element.closest('.input-icon'));
                if (element.parents('.table').size() > 0) {
                    error.appendTo(element.closest('.table').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            },

            submitHandler: function(form){
            	//校验数据
            	//if($("#componentId").val()=="" || null==$("#componentId").val()){
            	//	toastr.error("System is required.You must go to Provider Center frist!");
            	//	return;
            	//}
            	var customized,specType,attrFrom,attrTo,defaultValue,retFlag=0,isRet=true;
            	$("#productSpecTab>tbody>tr").each(function(){
            		customized =  $.trim($(this).find("td:eq(1)").text());
            		specType = $(this).find("input[id=specType]").val();
            		defaultValue = $(this).find("input[name=defaultValue]").val();
            		
//            		if("no"==(customized.toLowerCase())){
//            			if(!defaultValue){
//            				toastr.error("The product attribute custmized type is NO,Please fill in the default values!");
//            				retFlag = 1 ;
//            				return;
//        				}
//            		}
            		
            		if("3"==specType){
            			attrFrom = $(this).find("input[name=attrFrom]").val();
            			attrTo = $(this).find("input[name=attrTo]").val();
            			defaultValue = $(this).find("input[id=defaultValueEum]").val();
            			 if(""==attrFrom||"null"==attrFrom||""==attrTo||"null"==attrTo){
            				toastr.error("system error!!Please contact the administrator.");
            				retFlag = 1 ;
            				return;
         			    }else if(parseInt(attrFrom)>parseInt(defaultValue)||parseInt(defaultValue)>parseInt(attrTo)){
         			    	toastr.error("the value must be in ["+attrFrom+","+attrTo+"]");
         			    	$(this).find(input[id=defaultValueEum]).focus();
         			    	retFlag = 1 ;
         			    	return;
         			    }
            		}
            	 });
            	
            	if(retFlag==0){
            		var subBusinessCodeArr = [];
                	var subBusinessNameArr = [];
                	$("#subBusinessTable>tbody>tr").each(function(){
                		subBusinessCodeArr.push($(this).find("input[id=subBusinessCode]").val());
                		subBusinessNameArr.push($(this).find("input[id=subBusinessName]").val());
                	});
                	$("#subBusinessCodeInput").val(subBusinessCodeArr.join(","));
                	$("#subBusinessNameInput").val(subBusinessNameArr.join(","));
                	
                	var attrSpecIdArr = [];
                	var specTypeArr = [];
                	var defaultValuesArr = [];
                	var chooseAttrSpecCodeArr=[];
                	$("#productSpecTab>tbody>tr").each(function(){
                		attrSpecIdArr.push($(this).find("input[id=attrSpecId]").val());
                		specTypeArr.push($(this).find("input[id=specType]").val());
                		defaultValuesArr.push($(this).find("input[name=defaultValue]").val());
                		chooseAttrSpecCodeArr.push($(this).find("input[id=chooseAttrSpecCode]").val());
                	});
                	$("#attrSpecIdInput").val(attrSpecIdArr.join(","));
                	$("#specTypeInput").val(specTypeArr.join(","));
                	$("#defaultValuesInput").val(defaultValuesArr.join(";"));
                	$("#chooseAttrSpecCodeInput").val(chooseAttrSpecCodeArr.join(";"));
                	form.submit();
            	}
            	
            }
        });
    }
    
    var handleServiceAttributeModal = function(){
    	$("#serviceId").change(function(){
    		var serviceId = $(this).val();
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
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td>" +
    		 	 	    			"<td>"+chooseIsCustomizedText+"</td>" +
    		 	 	    					"<td style='white-space: normal;'>"+chooseAttrSpecName+"</td>" +
    		 	 	    							"<td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    		 	 	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    		 	 	    		+chooseDefaultValue
    		 	 	  		    +"</tr>";
    		 	 	    }else if(chooseAttrSpecType=="3"){
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    			 	  		 	    +"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"'/><input type='hidden' name='attrFrom' value='"+chooseFrom+"'/><input type='hidden' name='attrTo' value='"+chooseTo+"'/><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    			 	  		        +chooseDefaultValue
    			 	  		 	    +"</tr>";
    		 	 	    }else if(chooseAttrSpecType=="4"){
    		 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+serviceSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='TEXT-ALIGN:left;white-space: normal;'>"+chooseAttrSpecName+"</td><td style='TEXT-ALIGN:left;white-space: normal'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='TEXT-ALIGN:left;white-space:pre-wrap;' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    		 	   	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' /><input name='defaultValue' id='defaultEum' value='' type='hidden'/>"
    		 	   	  		 	+"<a class='button-base button-small' onclick=\"javascript:handleSelectServ('"+chooseAttrSpecId+"','"+chooseServAttrValueId+"');\" title='view'><span class=\"button-text\"> View</span></a>"
    		 	   	  		 	+"</tr>";
    		 	  	    }
    			    	$("#serviceSpecTab>tbody").append(tr);
    		    });
    		}
    		
    	}
    }

    var handleProductAttributeModal = function() {
    	var oTable = "";
    	var productSpecTabCurrIndex = $('#productSpecTab>tbody>tr').length;
    	var tempArr = ['<a class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a>'];
    	var url="../pardSpec/list.shtml";
    	if($("#crmVersion").val()!='7.0'){
    		url+="?isCustomized=N";
    	}
        //弹窗展现前从后台加载表格数据
        $('#productAttributeModal').on('show.bs.modal', function(e) {
          oTable = $('#dt1').dataTable({
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": url,
                    "order": [],
                    "autoWidth": false,
                    "columns": [
                              {"data": "ATTR_SPEC_INDEX"},
                              {"data": "ATTR_SPEC_ID"},
                              {"data": "IS_CUSTOMIZED_FORMAT"},
                              {"data": "ATTR_SPEC_NAME"},
                              {"data": "ATTR_SPEC_CODE"},
                              {"data": "PAGE_IN_TYPE_FORMAT"},
                              {"data": "ATTR_SPEC_DESC"},
                              {"data": "PAGE_IN_TYPE"},
                              {"data": "IS_CUSTOMIZED"},
                              {"data": "DEFAULT_VALUE"}
                          ],
                    "columnDefs": [{
                        "orderable": false,
                        "targets":  [0,1,2,3,4,5,6,7,8,9],
                        "width": "36px"
                    },{
                        "visible": false,
                        "targets": [7,8,9]
                    }],
                    "initComplete": function() {
                        App.initUniform();
                    }
                });
                jQuery('#dt1_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
                jQuery('#dt1_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            })
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $('.okProductBtn').bind('click', function() {
        	
        	var obs,chooseAttrSpecId,chooseAttrSpecName,chooseAttrSpecCode,chooseAttrSpecType,chooseAttrSpecDesc;
        	var chooseIsCustomized,chooseIsCustomizedText,changeType,chooseDefaultValue,chooseFrom,flag=false;
        	$("#dt1 tbody input[type='checkbox']:checked").each(function(i, o) {
        		obs = oTable.fnGetData(oTable.$('tr.active').get(i));
        		chooseAttrSpecId = obs.ATTR_SPEC_ID;
        		flag=false;
        		
        		$("#productSpecTab>tbody>tr").each(function(){
        			if($(this).attr("id")==("tr_"+chooseAttrSpecId)){
        				flag = true;
        				return;
        			}
        		});
        		if(flag){
        			
        		}else{
        			chooseAttrSpecName = obs.ATTR_SPEC_NAME;
            		chooseAttrSpecCode = obs.ATTR_SPEC_CODE;
            		chooseAttrSpecType = obs.PAGE_IN_TYPE;
            		chooseAttrSpecDesc = obs.ATTR_SPEC_DESC;
            		chooseIsCustomized = obs.IS_CUSTOMIZED;
            		chooseIsCustomizedText = obs.IS_CUSTOMIZED_FORMAT;
            		chooseDefaultValue = obs.DEFAULT_VALUE;
            		chooseFrom = obs.VALUE_FROM;
            		chooseTo = obs.VALUE_TO;
            		changeType = obs.PAGE_IN_TYPE_FORMAT;
            		
            		var tr = "";
            		productSpecTabCurrIndex ++;
            		if(chooseAttrSpecType=="1"||"2"==chooseAttrSpecType||"5"==chooseAttrSpecType||"6"==chooseAttrSpecType){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+productSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    	 	 	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    	 	 	    		+"<input type='text' class='form-control input-xs' name='defaultValue' id='defaultValueText' placeholder='' value='"+chooseDefaultValue+"'>"
    	 	 	  		    +"<td>"+tempArr[0]+"</td></tr>";
    	 	 	    }else if(chooseAttrSpecType=="3"){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+productSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
     	 	  		 	    +"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"'/><input type='hidden' name='attrFrom' value='"+chooseFrom+"'/><input type='hidden' name='attrTo' value='"+chooseTo+"'/><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
     	 	  		        +"<input type='text' class='form-control input-xs' name='defaultValue' id='defaultValueNumber' placeholder='' value='"+chooseDefaultValue+"' onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\">"
     	 	  		 	    +"<td>"+tempArr[0]+"</td></tr>";
    	 	 	    }else if(chooseAttrSpecType=="4"){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+productSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='TEXT-ALIGN:left;white-space: normal;'>"+chooseAttrSpecName+"</td><td style='TEXT-ALIGN:left;white-space: normal'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td style='TEXT-ALIGN:left;white-space:pre-wrap;' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    	 	   	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' /><input name='defaultValue' id='defaultEum' value='"+chooseDefaultValue+"' type='hidden'/> "
    	 	  	  		    +"<a class='button-base button-small' title=\"select\"><span class='button-text' onclick=\"javascript:handleSelect("+chooseAttrSpecId+");\"> select</span></a></td>"
    	 	   	  		 	+"</td><td>"+tempArr[0]+"</td></tr>";
    	 	  	    }
            		
            		$("#productSpecTab>tbody").append(tr);
        		}
            });
        	$("#productAttributeModal").modal("hide");
        });
        
        $("#productAttributeModalTarget").click(function(){
        	$("#productAttributeModal").modal("show");
        });
        
        $('.cancelProductBtn').bind('click', function() {
        	 $("#productAttributeModal").modal("hide");
        });
        
        $('#productSpecTab').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            if ($(this).closest('tbody').find('tr').size() >= 1) {            	
            	productSpecTabCurrIndex--;
                $tbody = $(this).closest('tbody');
                $(this).closest('tr').remove();
                $tbody.find('tr>td:first-child').each(function(i) {
                    $(this).html(i + 1);
                });
            }
        });
        
        $(".queryAttrBtn").click(function(){
        	var url = "../pardSpec/list.shtml?";
        	url += "code="+$("#featureCode").val() + "&name=" + $("#featureName").val() 
        	    + "&type=" + $("#featureType").val() 
        	    + "&maintenancec=" + $("#maintenancec").val(); 
        	if($("#crmVersion").val()!='7.0'){
        		url+="&isCustomized=N"//+ $("#isCustomized").val() ;
        	}
        	$('#dt1').dataTable().api().ajax.url(url).load();
        });
        
    }

    var handleTableFunction = function() {
        var currIndex = $('#subBusinessTable>tbody>tr').length;
        var tmpl = ['<input type="text" class="form-control input-xs" name="" id="subBusinessCode" placeholder="">', '<input type="text" class="form-control input-xs" name="" id="subBusinessName" placeholder="">', '<a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a>'];
            // add tr
        $('#subBusinessTable').on('click', 'th .fa-plus', function() {
                currIndex++;
                $(this).closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>' + tmpl[2] + '</td></tr');

        });
            // remove tr
        $('#subBusinessTable').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            if ($(this).closest('tbody').find('tr').size() >= 1) {
                currIndex--;
                $tbody = $(this).closest('tbody');
                $(this).closest('tr').remove();
                $tbody.find('tr>td:first-child').each(function(i) {
                    $(this).html(i + 1);
                });
            }
        });
        
        $(".product.cancle").click(function(e){
        	e.preventDefault();
        	window.location.href = "../pardProduct/toIndex.shtml";
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
//	                selectAllDataTable();
	            }
	        });
	    	jQuery('#productAttributeValueDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
	        jQuery('#productAttributeValueDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
    	 });
    	
    	$('.cancelAttrValueBtn').bind('click', function() {
    		$("#productAttributeValueModal").modal("hide");
         });
      //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
      $('.okAttrValueBtn').bind('click', function() {
    	  var attValur = [];
      	  $("#productAttributeValueDT tbody input[name=attrSpecValueId]:checked").each(function(i, o) {
    		attValur.push($(this).val());
    	 });
      	$("#productSpecTab>tbody").find("tr[id="+attrValueTr+"]").find("input[id=defaultEum]").val(attValur.join("/"));
      	$("#productAttributeValueModal").modal("hide");
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
        init: function() {
        	var attrValueTr = "";
        	
            handleForm();
            handleTableFunction();
            handleProductAttributeModal();
            productAttributeValueFunction();
            handleServiceAttributeModal();
            serviceSpecAttributeValueFunction();
            
            $("#serviceId").change();
        }
    }
}()
