var AddPardServiceSpec = function() {

    var handleForm = function() {

        $('#serviceSpecInfoForm').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
            	serviceSpecName: {
                    required: true,
                    minlength: 4,
                    maxlength: 50
                },
                serviceSpecCode: {
                    required: true,
                    minlength: 1,
                    maxlength: 32
                },
            },
            messages: {
            	serviceSpecName: {
                    required: "Service Specification's name is required and max length less than 50."
                },
                serviceSpecCode: {
                    required: "Service Specification's code is required and max length less than 50."
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
            	var customized,specType,attrFrom,attrTo,defaultValue,retFlag=0,isRet=true;
            	$("#serviceSpecTab>tbody>tr").each(function(){
            		customized =  $.trim($(this).find("td:eq(1)").text());
            		specType = $(this).find("input[id=specType]").val();
            		defaultValue = $(this).find("input[name=defaultValue]").val();
            		
            		if("3"==specType){
            			attrFrom = $(this).find("input[name=attrFrom]").val();
            			attrTo = $(this).find("input[name=attrTo]").val();
            			defaultValue = $(this).find("input[id=defaultValueEum]").val();
            			 if(""==attrFrom||"null"==attrFrom||""==attrTo||"null"==attrTo){
            				toastr.error("systom error!!Please contact the administrator.");
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
            	

        		//效验Service Specification Code是否唯一:
            	if(retFlag==0){
            		var serviceSpecCode = $("#serviceSpecCode").val();
            		var serviceSpecId =  $("#serviceSpecId").val();
	            	$.ajax({
	       				async : false,
	       				type : "POST",
	       				url :  APP_PATH+"/pardServiceSpec/serviceSpecificationCodeCheck.shtml",
	       				dataType : "json",
	       				data : {
	       					serviceSpecId:serviceSpecId,
	       					serviceSpecCode:serviceSpecCode
	       				},
	       				success : function(data) {
	       					if (data.code == "0000") {
	       						if(data.isHas != false){
	             			    	retFlag = 1;       			
	             			    	toastr.error("Service Specification Code you entered already exists, <br>Please enter a different code.");				
	       						}
	       					} else {
             			    	retFlag = 1;
	       						toastr.error(data.desc);
	       					}
	       				}
	            	});
            	}
            	
            	if(retFlag==0){
                	var attrSpecParams = []; 
                	var specCodes="##";
                	$("#serviceSpecTab>tbody>tr").each(function(){
                		//效验Attribute列表中的Code是否唯一:
                		var chooseAttrSpecCode = $(this).find("input[id=chooseAttrSpecCode]").val();
                		if(chooseAttrSpecCode !=""){
                			if(specCodes.indexOf("##"+chooseAttrSpecCode+"##") > -1){
             			    	toastr.error("Attribute code of the service cannot be the same!");
             			    	retFlag = 1;
             			    	return;
                			}else{
                				specCodes += chooseAttrSpecCode+"##";
                			}
                		}

                		var attrSpecParam = [];
                		attrSpecParam.push($(this).find("input[id=attrSpecId]").val());
                		attrSpecParam.push($(this).find("input[id=specType]").val());
                		attrSpecParam.push($(this).find("input[name=defaultValue]").val());
                		attrSpecParam.push($(this).find("input[id=chooseAttrSpecCode]").val());
                		attrSpecParam.push($(this).find("input[id=specMaintainType]").val());
                		attrSpecParams.push(attrSpecParam.join(","));
                	});
                	$("#params").val(attrSpecParams.join(";"));
            	}
            	
            	if(retFlag==0){
                	form.submit(); 
            	}
            }
        });
    }
    
    var handleServiceSpecAttributeModal = function() {
    	var oTable = "";
    	var servSpecTabCurrIndex = $('#serviceSpecTab>tbody>tr').length;
    	var tempArr = ['<a class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a>'];
    	var url="../pardSpec/list.shtml";
    	if($("#crmVersion").val()!='7.0'){
    		url+="?isCustomized=Y";
    	}
        //弹窗展现前从后台加载表格数据
        $('#serviceSpecAttributeModal').on('show.bs.modal', function(e) {
          oTable = $('#dt1').dataTable({
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                   // "ajax": "../pardSpec/list.shtml",
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
                              {"data": "SPEC_MAINTAIN_TYPE_FORMAT"},
                              {"data": "ATTR_SPEC_DESC"},
                              {"data": "PAGE_IN_TYPE"},
                              {"data": "IS_CUSTOMIZED"},
                              {"data": "DEFAULT_VALUE"},
                              {"data": "SPEC_MAINTAIN_TYPE"}
                          ],
                    "columnDefs": [{
                        "orderable": false,
                        "targets":  [0,1,2,3,4,5,6,7,8,9,10,11],
                        "width": "36px"
                    },{
                        "visible": false,
                        "targets": [8,9,10,11]
                    }],
                    "initComplete": function() {
                        App.initUniform();
                    }
                });
                jQuery('#dt1_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
                jQuery('#dt1_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            })
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $('.okServiceSpecBtn').bind('click', function() {
        	var obs,chooseAttrSpecId,chooseAttrSpecName,chooseAttrSpecCode,chooseAttrSpecType,chooseAttrSpecDesc,specMaintainType,specMaintainTypeText;
        	var chooseIsCustomized,chooseIsCustomizedText,changeType,chooseDefaultValue,chooseFrom,flag=false;
        	$("#dt1 tbody input[type='checkbox']:checked").each(function(i, o) {
        		obs = oTable.fnGetData(oTable.$('tr.active').get(i));
        		chooseAttrSpecId = obs.ATTR_SPEC_ID;
        		flag=false;
        		
        		$("#serviceSpecTab>tbody>tr").each(function(){
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
            		specMaintainType = obs.SPEC_MAINTAIN_TYPE;
            		specMaintainTypeText = obs.SPEC_MAINTAIN_TYPE_FORMAT;
            		
            		var tr = "";
            		servSpecTabCurrIndex ++;
            		if(chooseAttrSpecType=="1"||"2"==chooseAttrSpecType||"5"==chooseAttrSpecType||"6"==chooseAttrSpecType||"7"==chooseAttrSpecType){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+servSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td>"+specMaintainTypeText+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    	 	 	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='specMaintainType' value='"+specMaintainType+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
    	 	 	    		+"<input type='text' class='form-control input-xs' name='defaultValue' id='defaultValueText' placeholder='' value='"+chooseDefaultValue+"'>"
    	 	 	  		    +"<td>"+tempArr[0]+"</td></tr>";
    	 	 	    }else if(chooseAttrSpecType=="3"){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+servSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='white-space: normal;'>"+chooseAttrSpecName+"</td><td style='white-space: normal;word-break: break-word'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td>"+specMaintainTypeText+"</td><td style='white-space:pre-wrap; overflow:hidden;text-overflow:ellipsis;width: 100px' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
     	 	  		 	    +"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='specMaintainType' value='"+specMaintainType+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"'/><input type='hidden' name='attrFrom' value='"+chooseFrom+"'/><input type='hidden' name='attrTo' value='"+chooseTo+"'/><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' />"
     	 	  		        +"<input type='text' class='form-control input-xs' name='defaultValue' id='defaultValueNumber' placeholder='' value='"+chooseDefaultValue+"' onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\">"
     	 	  		 	    +"<td>"+tempArr[0]+"</td></tr>";
    	 	 	    }else if(chooseAttrSpecType=="4"){
    	 	 	    	tr ="<tr id='tr_"+chooseAttrSpecId+"'><td>"+servSpecTabCurrIndex+"</td><td>"+chooseIsCustomizedText+"</td><td style='TEXT-ALIGN:left;white-space: normal;'>"+chooseAttrSpecName+"</td><td style='TEXT-ALIGN:left;white-space: normal'>"+chooseAttrSpecCode+"</td><td>"+changeType+"</td><td>"+specMaintainTypeText+"</td><td style='TEXT-ALIGN:left;white-space:pre-wrap;' title='"+chooseAttrSpecDesc+"'>"+chooseAttrSpecDesc+"</td>"
    	 	   	  		 	+"<td><input type='hidden' id='chooseAttrSpecCode' value='"+chooseAttrSpecCode+"' /><input type='hidden' id='specMaintainType' value='"+specMaintainType+"' /><input type='hidden' id='attrSpecId' value='"+chooseAttrSpecId+"' /><input type='hidden' id='specType' value='"+chooseAttrSpecType+"' /><input name='defaultValue' id='defaultEum' value='"+chooseDefaultValue+"' type='hidden'/> "
    	 	  	  		    +"<a class='button-base button-small' title=\"select\"><span class='button-text' onclick=\"javascript:handleSelect("+chooseAttrSpecId+",'');\"> select</span></a></td>"
    	 	   	  		 	+"</td><td>"+tempArr[0]+"</td></tr>";
    	 	  	    }
            		
            		$("#serviceSpecTab>tbody").append(tr);
        		}
            });
        	$("#serviceSpecAttributeModal").modal("hide");
        });
        
        $('.cancelServiceSpecBtn').bind('click', function() {
        	 $("#serviceSpecAttributeModal").modal("hide");
        });
        
        $('#serviceSpecTab').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            if ($(this).closest('tbody').find('tr').size() >= 1) {            	
            	servSpecTabCurrIndex--;
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
        	    + "&type=" + $("#featureType").val();
        	if($("#crmVersion").val()!='7.0'){
        		url+="&isCustomized=Y"//+ $("#isCustomized").val() ;
        	}
        	url+="&maintenancec=" + $("#maintenancec").val(); 
        	$('#dt1').dataTable().api().ajax.url(url).load();
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
      //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
      $('.okAttrValueBtn').bind('click', function() {
    	  var attValur = [];
      	  $("#serviceSpecAttributeValueDT tbody input[name=attrSpecValueId]:checked").each(function(i, o) {
    		attValur.push($(this).val());
    	 });
      	$("#serviceSpecTab>tbody").find("tr[id="+attrValueTr+"]").find("input[id=defaultEum]").val(attValur.join("/"));
      	$("#serviceSpecAttributeValueModal").modal("hide");
      });
      
    }
   
    return {
        init: function() {
        	var attrValueTr = "";
        	handleForm();
        	handleServiceSpecAttributeModal();
        	serviceSpecAttributeValueFunction();
        	
        	$("#servSpecAttributeModalTarget").click(function(){
        		$("#serviceSpecAttributeModal").modal('show');
        	});
        	
        	$("#serviceSpecCancle").click(function(e){
        		e.preventDefault();
            	window.location.href = APP_PATH+"/pardServiceSpec/serviceSpecIndex.shtml";
        	});
        }
    }
}()
