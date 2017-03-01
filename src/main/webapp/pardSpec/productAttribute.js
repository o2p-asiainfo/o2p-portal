var ProductAttribute = function() {

	var tmpl = ['<input type="text" class="form-control input-xs" name="featureKey" id="featureKey" placeholder="Value" required>', 
                '<input type="text" class="form-control input-xs" name="featureShowValue" id="featureShowValue" placeholder="Text" required>', 
                '<input type="radio" name="featureDefault" id="featureDefault" checked=""></input>',
                '<a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a>',
                '<input type="checkBox" name="featureDefault" id="featureDefault" checked=""></input>'];
	
	 function showOrHide(){
 		var type = $("#type").val();
 		var pageInType = $("#pageInType").val();
 		if("1"==type||"2"==type||"5"==type||"6"==type||"7"==type){//text
 			$("div[id=textDefaultValueDiv]").show();
 			$("div[id=numberDefaultValueDiv]").hide();
 			$("div[id=enmDefaultValueDiv]").hide();
 		}else if("3"==type){//number
 			$("div[id=textDefaultValueDiv]").hide();
 			$("div[id=numberDefaultValueDiv]").show();
 			$("div[id=enmDefaultValueDiv]").hide();
 		}else if("4"==type){//combox
 			$("div[id=textDefaultValueDiv]").hide();
 			$("div[id=numberDefaultValueDiv]").hide();
 			$("div[id=enmDefaultValueDiv]").show();
 		}
 	}
	 
    var handleDataTable = function() {
        //初始化表格数据
        var oTable = $('#dt').dataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            //"lengthMenu": [5, 10, 15, "All"],
            "ajax": APP_PATH+"/pardSpec/list.shtml",
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
                      {"data": "DEFAULT_VALUE"},
                      {"data": "MIN_CARDINALITY"},
                      {"data": "MAX_CARDINALITY"}
                  ],
            "columnDefs": [{
                "orderable": false,
                "targets":  [0,1,2,3,4,5,6,7,8,9,10,11],
                "width": "36px"
            },{
                "visible": false,
                "targets": [7,8,9,10,11]
            }],
            "initComplete": function() {
                App.initUniform();
                $("#dt>tbody>tr").each(function(){
                	$(this).find("td").eq("3").attr("title",$(this).find("td").eq("3").text());
                	$(this).find("td").eq("4").attr("title",$(this).find("td").eq("4").text());
                	$(this).find("td").eq("6").attr("title",$(this).find("td").eq("6").text());
                });
            }
        });
        jQuery('#dt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
        //点击添加
        $("#btn-add").click(function() {
        	$('#myModal').modal('show');
            resetFrom();
            //handleEumCustomizedType();
            showOrHide();
            $('#btn-save').hide();
            $('.modal-title').html(getLang('attrSpec_add'));
            $('#btn-add-ok').show();
        });
        //点击修改
        $("#btn-edit").click(function() {
            resetFrom();
            $('#btn-save').show();
            $('.modal-title').html(getLang('attrSpec_edit'))
            $('#btn-add-ok').hide();
            
            //判断是否有选择
            var oSelected = $("#dt tbody input[type='checkbox']:checked");
            if (oSelected.size() > 1) {
                toastr.error(getLang('message_one_record_operation'));
            } else if (oSelected.size() == 1) {
                _editFun();
                $('#myModal').modal('show');
            } else {
                toastr.error(getLang('message_least_one_operation'));
            }
        });

        $('#btn-del').bind('click', _deleteList);
        //add弹窗，确定提交按钮
        $('#btn-add-ok').click(function() {
            handleForm(_addFun);
        });
        //edit弹窗，确定提交按钮
        $('#btn-save').click(function() {
            handleForm(_editFunAjax);
        });
        //type类型改动
        $("select[id=type]").change(function(){
        	showOrHide();
        });
        //isMulti类型改动
        $("#isMulti").change(function(){
        	$("#eumTable>tbody>tr").remove();
        	currIndex = 1;
            var isMulti = $("#isMulti").val();
            if("0"==isMulti){
            	$('#eumTable').closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[4]+'</td><td>' + tmpl[3] + '</td></tr>');
            }else{
                $('#eumTable').closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[2]+'</td><td>' + tmpl[3] + '</td></tr>');
            }
        });
        /**
         * 表格查询操作
         */
        $('#bntPardSearch').click(function(e){
        	e.stopPropagation();
        	
        	var featureName = $('#featureName').val();
        	var featureCode = $('#featureCode').val();
        	var featureType = $('#featureType').val();
        	var isCustomized = $('#isCustomized').val();
        	var maintenancec = $('#maintenancec').val();
        	
        	var url = APP_PATH+'/pardSpec/list.shtml?name='+featureName
        	        + '&code='+featureCode
        	        + '&type='+featureType
        	        + '&isCustomized='+isCustomized
        	        + '&maintenancec='+maintenancec;
        	$('#dt').dataTable().api().ajax.url(url).load();
        });
        
        /**
         * 表单验证
         * @param Fun 表单验证成功时执行的动作，（如：_addFun,_editFunAjax）
         */
        function handleForm(Fun) {
            $('#form1').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    code: {
                        required: true,
                        maxlength: 50
                    },
                    name: {
                        required: true,
                        maxlength: 50
                    },
                    type: {
                        required: true,
                    },
                    value: {
                        required: true,
                    },
                    min: {
                        required: true,
                        maxlength: 3
                    },
                    max: {
                        required: true,
                        maxlength: 3
                    },
                    customized: {
                        required: true,
                    }
                },
                messages: {
                    name: {
                        required: "filed name is required and max length less than 50."
                    },
                    code: {
                        required: "filed code is required and max length less than 50."
                    },
                    min: {
                        required: "filed min is required and max length less than 3."
                    },
                    max: {
                        required: "filed max is required and max length less than 3."
                    }
                },

                invalidHandler: function(event, validator) { //display error alert on form submit                       
                    App.scrollTop();
                },

                highlight: function(element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                success: function(label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function(error, element) {
                    // error.insertAfter(element.closest('.input-icon'));
                    if (element.parents('.checkbox-list').size() > 0) {
                        error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },

                submitHandler: function(form) {
                    // form validation success, call ajax form submit
                	var min = $("#min").val();
                	var max = $("#max").val();
                	if((parseInt(min)>parseInt(max))){
    					toastr.error("the product attribute's minCharacterister must be less than maxCharacterister!");
    					return;
    				}
                	
                	if(isUD($("#attrSpecId").val(),$("#specMaintainType").val())){
            			if("4"==$("#type").val()){
            				if(!isExit()){
            					return;
            				}
            				var totalVal=[],featureDefault;
            				$("#eumTable>tbody>tr").each(function(i){
            					var trVal = [];
            					trVal.push($(this).find("input[id=featureKey]").val());
            					trVal.push($(this).find("input[id=featureShowValue]").val());
            					featureDefault = $(this).find("input[id=featureDefault]").attr('checked');
            					if(featureDefault=="checked"){
            						trVal.push("0");
            					}else{
            						trVal.push("1");
            					}
            					totalVal.push(trVal.join(","));
            				});
            				$("#e_featureDefault").val(totalVal.join(";"));
            				
            			}
            			/**
            			 * else if("1"==$("#type").val()&&"N"==$("#customized").val()){
            				var textDefaultValue = $("#textDefaultValueDiv").find("#textDefaultValue").val();
            				if(!textDefaultValue){
            					toastr.error("If the product attribute's Customized is NO and Specification Value Type is Text ,than default value is not empty!");
            					return;
            				}
            			}
            			 */
            			else if("3"==$("#type").val()){
            				var featureMinimum = $("#numberDefaultValueDiv").find("#featureMinimum").val();
            				var featureMaximum = $("#numberDefaultValueDiv").find("#featureMaximum").val();
            				var featureDefault = $("#numberDefaultValueDiv").find("#num_featureDefault").val();
            				if(!featureMinimum||!featureMaximum){
            					toastr.error("If the product attribute's Specification Value Type is Number ,than min number, max number is not empty!");
            					return;
            				}
            				if(!(parseInt(featureMinimum)<=parseInt(featureMaximum))){
            					toastr.error("If the product attribute's Specification Value Type is Number ,than min number must be less than max number!");
            					return;
            				}
            			}
            			
            			var type = $("#type").val();
            	 		var pageInType = $("#pageInType").val();
            	 		if(type!=pageInType){
            	 			$("#mappingId").val("");
            	 		}
            			form.submit();
                	}
                }
            });

            function isExit(){
        		var len = $("#eumTable>tbody").find("tr").length;
        		var itrId = "";
        		var jtrId = "";
        		var itrV = "";
        		var jtrV ="";
        		for(var i=0;i<len;i++){
        			itrId = "#eumTable>tbody>tr:eq(" + i + ")>td>";
        			for(var j=0;j<len;j++){
        				jtrId = "#eumTable>tbody>tr:eq(" + j + ")>td>";
        				itrV = $(itrId + "#featureKey").val();
        				jtrV = $(jtrId + "#featureKey").val();
        				if(itrV==""){
        					toastr.error("Attribute's text is not null!");
        					return false;
        				}
        				if( $(jtrId + "#featureShowValue").val()==""){
        					toastr.error("Attribute's value is not null!");
        					return false;
        				}
        				
        				if(i!=j && itrV==jtrV){
        					toastr.error("Attribute's key is not repeat!");
        					return false;
        				} 
        			}
        		}
        		return true;
        	}
            function isUD(id,maintainId){
        		if(""==id){return true;}
        		  var flag;
        		  if(maintainId!="2"){
        			  toastr.error("Product Attribute Is Not User Maintenance.");
        			  return false;
        		  }
        		  $.ajax({
        				type: "POST",
        				async:false,
        			    url: APP_PATH+"/pardSpec/isUD.shtml",
        			    dataType:'json',
        			    data:{attrSpecId:id},
        				success:function(msg){
        					if(msg.code=="1"){
        						toastr.error("You Can Not Delete Or Modify The Referenced.");
        						flag = false;
        					}else{
        						flag = true;
        					}
        		        }
        		   });
        		  return flag;
        	  }
            
            $('#form1 input').keypress(function(e) {
                if (e.which == 13) {
                    if ($('#form1').validate().form()) {
                        //form validation success, call ajax form submit
                        Fun();
                    }
                    return false;
                }
            });
        }

        /**
         * 编辑数据带出值
         */
        function _editFun() {
            var selected = oTable.fnGetData(oTable.$('tr.active').get(0));
            $.ajax({
                url: APP_PATH+"/pardSpec/toUpdate.shtml",
                data: {
                    "attrSpecId": selected.ATTR_SPEC_ID
                },
                type: "GET",
                cache: false,
                dataType:'json',
                success: function(backdata) {
                    if (backdata) {
                    	$('#attrSpecId').val(backdata.charSpec.charSpecId);
                        $('#code').val(backdata.charSpec.code);
			            $('#name').val(backdata.charSpec.charSpecName);
			            $('#min').val(backdata.charSpec.minCardinality);
			            $('#max').val(backdata.charSpec.maxCardinality);
			            $('#customized').val(backdata.charSpec.isCustomized);
			            $('#type').val(backdata.charSpec.valueType);
			            $('#featureNote').text(backdata.charSpec.description);
			            $('#specMaintainType').val(backdata.charSpec.charSpecMaintainType);
			            $('#isMulti').val(backdata.charSpec.isMulti);
			            $('#pageInType').val(backdata.charSpec.valueType);
			            var PAGE_IN_TYPE=backdata.charSpec.valueType;
			            if("1"==PAGE_IN_TYPE||"2"==PAGE_IN_TYPE||"5"==PAGE_IN_TYPE||"6"==PAGE_IN_TYPE||"7"==PAGE_IN_TYPE){
			            	$('#textDefaultValue').val(backdata.charSpec.defaultValue);
			            }else if("3"==PAGE_IN_TYPE){
			            	 $.each(  
		            				 backdata.charSpecValueList,  
            				    function(key) {  
		            					$('#featureMinimum').val(backdata.charSpecValueList[key].valueFrom);
		     			            	$('#featureMaximum').val(backdata.charSpecValueList[key].valueTo);
		            				 });
			            	$('#num_featureDefault').val(backdata.charSpec.defaultValue);
			            }else if("4"==PAGE_IN_TYPE){
			            	var tr = "";
			            	if(backdata.charSpecValueList){
			            		$("#eumTable>tbody>tr").remove();
			            		 $.each(  
			            				 backdata.charSpecValueList,  
	            				    function(key) {  
			            				tr = '<tr><td>' + backdata.charSpecValueList[key].seqId + '<input type="hidden" name="attrValId" id="attrValId" value="'+ backdata.charSpecValueList[key].charSpecValueId +'"></td>'	;
			            				tr += '<td><input type="text" class="form-control input-xs" name="featureKey" id="featureKey" placeholder="" value="'+backdata.charSpecValueList[key].value+'" required></td>';
			            				tr += '<td><input type="text" class="form-control input-xs" name="featureShowValue" id="featureShowValue" placeholder="" value="'+backdata.charSpecValueList[key].displayValue+'" required></td>';
			            				if("0"==backdata.charSpec.isMulti){
			            					if("0"==backdata.charSpecValueList[key].isDefault){
			            						tr += '<td><input type="checkBox" name="featureDefault" id="featureDefault" checked="true"></input></td>';
			            					}else{
			            						tr += '<td><input type="checkBox" name="featureDefault" id="featureDefault"></input></td>';
			            					}
			            				}else{
			            					if("0"==backdata.charSpecValueList[key].isDefault){
			            						tr += '<td><input type="radio" name="featureDefault" id="featureDefault" checked="true"></input></td>';
			            					}else{
			            						tr += '<td><input type="radio" name="featureDefault" id="featureDefault"></input></td>';
			            					}
			            				}
			                            tr += '<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td></tr>';
			            				
			            			    $("#eumTable>tbody").append(tr);
	            				    });
			            	}
			            	currIndex =  $("#eumTable>tbody>tr").size();
			            }
                    } 
                    showOrHide();
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }

        /**
         * 添加数据
         * @private
         */
        function _addFun() {
            var str = $('form').serialize();
            $.ajax({
                url: "insert.jsp",
                data: str,
                type: "post",
                success: function(backdata) {
                    if (backdata == 1) {
                        $("#myModal").modal("hide");
                        resetFrom();
                        oTable.api().ajax.reload();
                        $('#dt_processing').hide();
                        toastr.success(getLang('message_operation_success'));
                    } else if (backdata == 0) {
                    	toastr.error(getLang('message_operation_fail'));
                    }
                },
                error: function(error) {
                	toastr.error(error);
                }
            });
        }

        /**
         * 编辑数据
         * @private
         */
        function _editFunAjax() {                
                var str = $('form').serialize();
                $.ajax({
                    type: 'POST',
                    url: 'edit.jsp',
                    data: str,
                    success: function(backdata) {
                        if (backdata == 1) {
                            $("#myModal").modal("hide");
                            resetFrom();
                            oTable.api().ajax.reload();
                            $('#dt_processing').hide();
                             toastr.success(getLang('message_operation_success'));
                        } else if (backdata == 0) {
                            toastr.error(getLang('message_operation_fail'));
                        }
                    },
                    error: function(error) {
                    	toastr.error(error);
                    }
                });
            }
            /**
             * 删除
             * @param id
             * @private
             */
        function _deleteFun(IDS) {
            $.ajax({
                url: APP_PATH+"/pardSpec/delete.shtml",
                data: {"attrSpecId": IDS},
                type: "GET",
                cache: false,
                dataType:'json',
                success: function(backdata) {
                    if (backdata.code=="0000") {
                        oTable.api().ajax.reload();
                        $('#dt_processing').hide();
                    } else {
                    	toastr.error(getLang('message_operation_fail'));
                    }
                },
                error: function(error) {
                	toastr.error("Delete Failed !");
                }
            });
        }

        /**
         * 批量删除
         * 未做
         * @private
         */
        function _deleteList() {
            var idArr=[] ,IDS;
            $("tbody input[type='checkbox']:checked").each(function(i, o) {
            	idArr.push(oTable.fnGetData(oTable.$('tr.active').get(i)).ATTR_SPEC_ID);
            });
            if (idArr.length > 0) {
                IDS = idArr.join(",");
                bootbox.confirm(getLang('message_delete_date_id') + IDS+"?", function(result) {
	       			 if(result==true){
	                    _deleteFun(IDS)
	       			 }
                });
            } else {
                toastr.error(getLang('message_least_one_operation'));
            }
        }

        /**
         * 重置表单
         */
        function resetFrom() {
            $('form').each(function(index) {
                $('form')[index].reset();
                $('form').find('.help-block').remove();
                $('form').find('.form-group').removeClass('has-error');
                $("#eumTable>tbody>tr").remove();
                currIndex = 1;
                var isMulti = $("#isMulti").val();
                if("0"==isMulti){
                	$('#eumTable').closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[4]+'</td><td>' + tmpl[3] + '</td></tr>');
                }else{
                	$('#eumTable').closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[2]+'</td><td>' + tmpl[3] + '</td></tr>');
                }
            });
            $("#form1").find("input[type=hidden]").val('');
            $("#form1").find("textarea").text('');
        }

    }
    
    var handleTableFunction = function() {
       // add tr
        $('#eumTable').on('click', 'th .fa-plus', function() {
            currIndex++;
            var isMulti = $("#isMulti").val();
            if("0"==isMulti){
            	$(this).closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[4]+'</td><td>' + tmpl[3] + '</td></tr>');
            }else{
            	$(this).closest('table').find('tbody').append('<tr><td>' + currIndex + '</td><td>' + tmpl[0] + '</td><td>' + tmpl[1] + '</td><td>'+tmpl[2]+'</td><td>' + tmpl[3] + '</td></tr>');
            }
        });
       // remove tr
        $('#eumTable').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            if ($(this).closest('tbody').find('tr').size() > 1) {
                currIndex--;
                $tbody = $(this).closest('tbody');
                $(this).closest('tr').remove();
                $tbody.find('tr>td:first-child').each(function(i) {
                    $(this).html(i + 1);
                });
            }else{
            	toastr.error(getLang('message_least_one_operation'));
            }
        });
     
    }
    
    var handleEumCustomizedType = function() {
		 var customized = $("#customized").val();
		 var type = $("#type");
		 $.ajax({
				async : true,
				type : "POST",
				url :  APP_PATH+"/pardSpec/handleEumCustomizedTypeList.shtml",
				data : {
					customizedType:customized
				},
				success : function(data) {
					if (data.code == RESULT_OK) {
						type.find("option").remove();
						 var selList = data.desc
						 if(selList!=null && selList.length>0){
							 for(var i=0; i<selList.length; i++){
								 var _key   = selList[i].key;
								 var _value = selList[i].value;
								 type.append("<option value='"+_key+"'>"+_value+"</option>"); 
							 }
							 showOrHide();
						 }
					} else {
						toastr.error(data.desc);
					}
				},
				dataType : "json"
		});
   }

    return {
        init: function() {
            var currIndex = 0;
            //方法
            handleDataTable();
            handleTableFunction();
        },

    }
}()
