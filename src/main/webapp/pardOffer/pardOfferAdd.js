var PardOfferAdd = function() {
	var productCurrIndex = 0;
	var offerCurrIndex = 0;
	var exclusionOfferCurrIndex = 0;
	
	var tableTrObjectId = function(tTableName){
		var arr = [];
		if($("#"+tTableName).find("tr").length==0){
			return "";
		}
		$("#"+tTableName).find("tr").each(function(){
			arr.push($(this).prop("id"));
		});
		return arr.join(",");
	}
	
	 var handleProductModal = function() {
        //弹窗展现前从后台加载表格数据
        $('#productModal').on('show.bs.modal', function(e) {
            var productModalDT = $('#productDt').dataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "ajax": APP_PATH+"/pardProduct/winList.shtml?cooperationType="+$("#operatorValue").val(),
                "order": [],
                "autoWidth": false,
                "columnDefs": [{
                    "orderable": false,
                    "targets": 0,
                    "width": "36px"
                }],
                "initComplete": function() {
                    App.ajaxInit();
                }
            });
            jQuery('#productDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
            jQuery('#productDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            
          //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
            $('.btn.theme-btn.productModal.ok').bind('click', function() {
            	$('#productModal').modal('hide');
                  $("#productModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
                	  	var objTr=$(this).closest('tr');
            		  	if(productCurrIndex==0){
          	  				$("#productTB tr").eq(0).remove();
          	  				addProductTr(objTr,objCk.id);
              	  		}else{
              	  			//查重
              	  			var _pass=true;
              	  			$('#productTB tr').each(function(j,objPTr) {
              	  				if(objPTr.id==objCk.id){
              	  					_pass=false;
              	  					return;
              	  				}
                		  	});
                		  	if(_pass){
          	  					addProductTr(objTr,objCk.id);
          	  				}
              	  		}
                  });
          		 productOrOfferIsHas();
            });
            //关闭产品选择模块
            $('.btn.btn-default.productModal.cancel').bind('click', function() {
            	$('#productModal').modal('hide');
            });
            //查询
            $(".queryProdcutModalBtn").click(function(){
            	var url = APP_PATH+"/pardProduct/winList.shtml?";
            	url += "prod_name="+$("#productModal_prod_name").val() + "&prod_code=" + $("#productModal_prod_code").val() 
            	    + "&provider=" +$("#operatorValue").val() + "&cooperationType=" + $("#productModal_cooperationType").val(); 
            	productModalDT.api().ajax.url(url).load();
            });
            
            var addProductTr = function(objTr,pId){
            	productCurrIndex++;
    	  		var tr=[];
    	  		tr.push("<td>"+productCurrIndex+"</td>");
    	  		tr.push("<td>"+$(objTr).children('td').eq(1).html()+"</td>");
    	  		tr.push("<td>"+$(objTr).children('td').eq(2).html()+"</td>");
    	  		tr.push("<td><a onclick='productDetailModal("+pId+")'>"+$(objTr).children('td').eq(3).html()+"</a</td>");
    	  		tr.push("<td><input type='text' class='form-control' onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" value='1'/></td>");
    	  		tr.push("<td><input type='text' class='form-control' onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" value='1'/></td>");
    	  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
    	  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
        		$("#productTB").append(trStr);
            }
        });
        
      //打开产品选择模块
	    $(".productModalPlus").bind('click', function() {
	    	if($("#operatorValue").val()==''){
	    		 toastr.error("You must select an operater frist!");
	    	}else{
	    		$('#productModal').modal('show');
	    	}
	    });
	    $('#productTB').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            if (productCurrIndex >0) {
            	productCurrIndex--;
                $tbody = $(this).closest('tbody');
                $(this).closest('tr').remove();
                $tbody.find('tr>td:first-child').each(function(i) {
                    $(this).html(i + 1);
                });
            }
            if (productCurrIndex ==0) {
            	$("#productTB").append('<tr> <td colspan="7">None</td> </tr>');
            }
     		 productOrOfferIsHas();
        });
	    
	 }
	 var handleOfferModal = function() {
	        //弹窗展现前从后台加载表格数据
	        $('#offerModal').on('show.bs.modal', function(e) {
	            var offerModalDT = $('#offerDt').dataTable({
	                "processing": true,
	                "serverSide": true,
	                "searching": false,
	                "ajax": APP_PATH+"/pardOffer/winList.shtml?cooperationType="+$("#operatorValue").val(),
	                "order": [],
	                "autoWidth": false,
	                "columnDefs": [{
	                    "orderable": false,
	                    "targets": 0,
	                    "width": "36px"
	                }],
	                "initComplete": function() {
	                    App.ajaxInit();
	                }
	            });
	            jQuery('#offerDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
	            jQuery('#offerDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
	            
	            $(".queryofferModalBtn").click(function(){
	            	var url = APP_PATH+"/pardOffer/winList.shtml?";
	            	url += "off_name="+$("#offerModal_off_name").val() + "&off_code=" + $("#offerModal_off_code").val() 
	            	    + "&provider=" + $("#operatorValue").val() + "&queryParamsData="+tableTrObjectId("exclusionOfferTB") + "&cooperationType="+$("#offerModal_cooperationType").val();; 
	            	offerModalDT.api().ajax.url(url).load();
	            });
	        });
	      //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
	        $('.btn.theme-btn.offerModal.ok').bind('click', function() {
	        	$('#offerModal').modal("hide");
	              $("#offerModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
	            	  	var objTr=$(this).closest('tr');
	        		  	if(offerCurrIndex==0){
	      	  				$("#offerTB tr").eq(0).remove();
	      	  				addOfferModalTr(objTr,objCk.id);
	          	  		}else{
	          	  			//查重
	          	  			var _pass=true;
	          	  			$('#offerTB tr').each(function(j,objPTr) {
	          	  				if(objPTr.id==objCk.id){
	          	  					_pass=false;
	          	  					return;
	          	  				}
	            		  	});
	            		  	if(_pass){
	            		  		addOfferModalTr(objTr,objCk.id);
	      	  				}
	          	  		}
	              });
	      		productOrOfferIsHas();
	        });
	        //取消融合offer模块
	        $('.btn.btn-default.offerModal.cancel').bind('click', function() {
	        	$('#offerModal').modal("hide");
	        });
	        //打开融合offer模块
	        $('.offerModalPlus').bind('click', function() {
	        	if($("#operatorValue").val()==''){
		    		 toastr.error("You must select an operater please!");
		    	}else{
		    		$('#offerModal').modal("show");
		    	}
	        });
	        var addOfferModalTr=function(objTr,pId){
	        	offerCurrIndex++;
		  		var tr=[];
		  		tr.push("<td>"+offerCurrIndex+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(1).html()+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(2).html()+"</td>");
		  		tr.push("<td><a onclick='offerDetailModal("+pId+")'>"+$(objTr).children('td').eq(3).html()+"</a</td>");
		  		tr.push("<td><input type='text' class='form-control' onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" value='0'/></td>");
		  		tr.push("<td><input type='text' class='form-control' onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" value='1'/></td>");
		  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
		  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
	    		$("#offerTB").append(trStr);
	        }
	        $('#offerTB').on('click', 'td .btn-del', function(e) {
	            e.preventDefault();
	            if (offerCurrIndex >0) {
	            	offerCurrIndex--;
	                $tbody = $(this).closest('tbody');
	                $(this).closest('tr').remove();
	                $tbody.find('tr>td:first-child').each(function(i) {
	                    $(this).html(i + 1);
	                });
	            }
	            if (offerCurrIndex ==0) {
	            	$("#offerTB").append('<tr> <td colspan="7">None</td> </tr>');
	            }
	      		productOrOfferIsHas();
	        });
	        
		 }
	 var handleExclusionOfferModal = function() {
		 $('#exclusionOfferModal').on('show.bs.modal', function(e) {
	        //弹窗展现前从后台加载表格数据
			var exclusionOfferModalDt = $('#exclusionOfferDt').dataTable({
	             "processing": true,
	             "serverSide": true,
	             "searching": false,
	             "ajax": APP_PATH+"/pardOffer/winList.shtml?cooperationType="+$("#operatorValue").val(),
	             "order": [],
	             "autoWidth": false,
	             "columnDefs": [{
	                 "orderable": false,
	                 "targets": 0,
	                 "width": "36px"
	             }],
	             "initComplete": function() {
	                App.ajaxInit();
	             }
	         });
	         jQuery('#exclusionOfferDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
	         jQuery('#exclusionOfferDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
	         
	         $(".queryExclusionOfferModalBtn").click(function(){
	            	var url = APP_PATH+"/pardOffer/winList.shtml?";
	            	url += "off_name="+$("#exclusionOfferModal_off_name").val() + "&off_code=" + $("#exclusionOfferModal_off_code").val() 
	            	    + "&provider=" + $("#operatorValue").val() + "&queryParamsData="+tableTrObjectId("offerTB") + "&cooperationType="+$("#exclusionOfferModal_cooperationType").val();; 
	            	exclusionOfferModalDt.api().ajax.url(url).load();
	         });
		 });
		 //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
	        $('.btn.theme-btn.exclusionOfferModal.ok').bind('click', function() {
	              $("#exclusionOfferModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
	            	  	var objTr=$(this).closest('tr');
	        		  	if(exclusionOfferCurrIndex==0){
	      	  				$("#exclusionOfferTB tr").eq(0).remove();
	      	  				addExclusionOfferTr(objTr,objCk.id);
	          	  		}else{
	          	  			//查重
	          	  			var _pass=true;
	          	  			$('#exclusionOfferTB tr').each(function(j,objPTr) {
	          	  				if(objPTr.id==objCk.id){
	          	  					_pass=false;
	          	  					return;
	          	  				}
	            		  	});
	            		  	if(_pass){
	            		  		addExclusionOfferTr(objTr,objCk.id);
	      	  				}
	          	  		}
	              });
	        });
	        //打开互斥offer模块
	        $('.exclusionOfferModal').bind('click', function() {
	        	if($("#operatorValue").val()==''){
		    		 toastr.error("You must select an operater please!");
		    	}else{
		    		$('#exclusionOfferModal').modal("show");
		    	}
	        });
	        var addExclusionOfferTr=function(objTr,pId){
	        	exclusionOfferCurrIndex++;
		  		var tr=[];
		  		tr.push("<td>"+exclusionOfferCurrIndex+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(1).html()+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(2).html()+"</td>");
		  		tr.push("<td><a onclick='offerDetailModal("+pId+")'>"+$(objTr).children('td').eq(3).html()+"</a</td>");
		  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
		  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
	    		$("#exclusionOfferTB").append(trStr);
	        }
	        $('#exclusionOfferTB').on('click', 'td .btn-del', function(e) {
	            e.preventDefault();
	            if (exclusionOfferCurrIndex >0) {
	            	exclusionOfferCurrIndex--;
	                $tbody = $(this).closest('tbody');
	                $(this).closest('tr').remove();
	                $tbody.find('tr>td:first-child').each(function(i) {
	                    $(this).html(i + 1);
	                });
	            }
	            if (exclusionOfferCurrIndex ==0) {
	            	$("#exclusionOfferTB").append('<tr> <td colspan="5">None</td> </tr>');
	            }
	        });
	        
		 }
	 
	 //用于保存时验证必需选择一个产品或一个销售品：
	 var productOrOfferIsHas = function() {
		var productLen = $("#productTB>tr").length;
		if(productLen==1){
			var inp = $("#productTB>tr input").length;
			if(inp==0){
				productLen = 0;
			}
		}
		var offerLen	   = $("#offerTB>tr").length;
		if(offerLen==1){
			var inp = $("#offerTB>tr input").length;
			if(inp==0){
				offerLen = 0;
			}
		}
		var len = productLen+offerLen;
		if(len==0){
			$("#productOrOfferTableHasRow").val("");
		}else{
			$("#productOrOfferTableHasRow").val(productLen+offerLen);
		}
	 }
	 
	 var handleDatePickers = function(selector, model) {
	        var options = {};
	        if (model == 'daily') {
	            options = {
	                autoclose: true,
	                minViewMode: 'year',
	                format: 'yyyy-mm-dd'
	            }
	        } else if (model == 'monthly') {
	            options = {
	                autoclose: true,
	                minViewMode: 'months',
	                format: 'yyyy-mm'
	            }
	        } else {
	            options = {
	                autoclose: true,
	                minViewMode: 'year',
	                format: 'yyyy-mm-dd'
	            }
	        }
	        if (jQuery().datepicker) {
	            $(selector).datepicker(options);
	        }
	    } 
	 
	 var handleJstree = function(selector) {
		 $.ajax({
				async : false,
				type : "POST",
				url :  APP_PATH+"/pardOffer/getPardChanneTree.shtml",
				data : {
					
				},
				success : function(data) {
					if (data.code == "0000") {
						 $(selector).jstree({
				            'plugins': ["checkbox", "types"],
				            'core': {
				                'data': data.desc
				            },
				            "types": {
				                "default": {
				                    "icon": "fa fa-folder icon-warning icon-lg"
				                },
				                "file": {
				                    "icon": "fa fa-file icon-warning icon-lg"
				                }
				            }
				        });
						 
						 $(selector).on('changed.jstree', function (e, data) {
							 var checkTextAry=[];
							 var checkIdAry=[];
							 for(i = 0, j = data.selected.length; i < j; i++) {
								 checkTextAry.push(data.instance.get_node(data.selected[i]).text);
								 checkIdAry.push(data.instance.get_node(data.selected[i]).id);
							 }
							 $("#jstreeText").val(checkTextAry.join(","));
							 $("#offerChannelStr").val(checkIdAry.join(","));
							 /*
							 var nodes=$(selector).jstree("get_checked"); //使用get_checked方法
				           	 $.each(nodes, function(i, n) { alert($(n).attr("id"));
				           		checkStr.push($(selector).jstree("get_json",n,"id")); 
				           	 });*/
						  });
					} else {
						toastr.error(data.desc);
					}
				},
				dataType : "json",
	            error: function(xhr, textStatus, errorThrown) {
	            	//called when there is an error
	            }
			});
	        /*$(selector).jstree({
	        	 "plugins": ["themes", "json_data", "ui"],
	        	 'core': {
	        		    'data': {
	        		        'url': APP_PATH+"/pardOffer/getPardChanneTree.shtml",
	        		        'data': function(node) {
	        		            return {
	        		                'id': node.id,
	        		                'text': node.text
	        		            };
	        		        }
	        		    },
	        		}
	        });*/
	    };
	 var onSubmit=function(){
         if ($('#submit_form').valid() == false) {
             return false;
         }
		 if(productCurrIndex>0){
     		 var offerProdStr=[];
    		 $('#productTB tr').each(function(j,objPTr) {
    			 offerProdStr.push([objPTr.id,$(objPTr).children('td').eq(4).find('input').val(),$(objPTr).children('td').eq(5).find('input').val()].join(","));
    	  	 });
    		 $("#offerProdStr").val(offerProdStr.join(";"));
     	}
     	if(offerCurrIndex>0){ 	
     		 var offerStr=[];
    		 $('#offerTB tr').each(function(j,objPTr) {
    			 offerStr.push([objPTr.id,$(objPTr).children('td').eq(4).find('input').val(),$(objPTr).children('td').eq(5).find('input').val()].join(","));
    	  	 });
    		 $("#offerStr").val(offerStr.join(";"));	
     	}
     	if(exclusionOfferCurrIndex>0){ 	
     		 var offerMutualExclusionStr=[];
    		 $('#exclusionOfferTB tr').each(function(j,objPTr) {
    			 offerMutualExclusionStr.push([objPTr.id].join(","));
    	  	 });
    		 $("#offerMutualExclusionStr").val(offerMutualExclusionStr.join(";"));	
     	}
     	 var chk_value=[];
		 $('input[name="channel"]:checked').each(function(){    
			   chk_value.push($(this).val());    
			  });  
		 $("#offerChannelStr").val(chk_value.join(","));
		 if($("#offerChannelStr"))
		 $.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/pardOffer/add.shtml",
			data : $('#submit_form').serialize(),
			beforeSend:function(){
				
			},success : function(data) {
				if (data.code == "0000") {
					var offerId=data.desc;	 
					toastr.success('Save Success');
					$("#prodOfferId").val(offerId);
					showProdForm.action=APP_PATH+"/pardOffer/toUpdate.shtml";
					showProdForm.submit();
					$("#submit_form").find("ul>li").eq(3).attr("href","${APP_PATH }/pardOffer/toDetail.shtml?prodOffer.prodOfferId="+offerId);
				} else {
					toastr.error(data.desc);
				}
			},
			dataType : "json",
            error: function(xhr, textStatus, errorThrown) {
            	 toastr.error("Save Fail");
            }
		});
	 };
    return {
        //main function to initiate the module
        init: function() {
        	if (!jQuery().bootstrapWizard) {
                return;
            }
        	var form = $('#submit_form');
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);
            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                	"prodOffer.prodOfferName": {
                        required: true,
                        minlength: 4
                    },
                    "prodOffer.extProdOfferId": {
                        required: true,
                        remote: {
                        	type:"POST",
                        	 url: APP_PATH+"/pardOffer/checkOfferCode.shtml",
                 	 	    dataType:'json',
                 	 	    data:{
              	 	    	 offerCode:
	              	 	    	 function() {
	                                return $("#productCode").val();   
	                            }
              	 	    	}
                        },
                        minlength: 4
                    },
                    "prodOffer.formatEffDate": {
                        required: true
                    },
                    "prodOffer.formatExpDate": {
                        required: true
                    },
                    productOrOfferTableHasRow: {
                        required: true
                    }
                },
                messages: {
                	"prodOffer.prodOfferName": {
                        required: "Offer Name is required.",
                        minlength: jQuery.format("Please select  at least {0} types of prodOfferName")
                    },
                    "prodOffer.extProdOfferId": {
                        required: "Offer Code is required.",
                        minlength: jQuery.format("Please select  at least {0} types of extProdOfferId"),
                        remote: "offer code already exists"
                    },
                    "prodOffer.formatEffDate": {
                        required: "Start time is required."
                    },
                    "prodOffer.formatExpDate": {
                        required: "End time is required."
                    },
                    productOrOfferTableHasRow: {
                        required: "Select at least one Product or an Product Offer."
                    }
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
                    if (element.parents('.input-group').size() > 0) {
                        error.appendTo(element.parents('.input-group').attr("data-error-container"));
                    } else if (element.parents('.checkbox-list').size() > 0) {
                        error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },
                submitHandler: function(form) {
                    Fun(); // form validation success, call ajax form submit
                }
            });

            var handleTitle = function(tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title').text('Step ' + (index + 1) + ' of ' + total);
                // set done steps
                jQuery('li', $('#submit_form')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#submit_form').find('.button-previous').hide();
                } else {
                    $('#submit_form').find('.button-previous').hide();
                }

                if (current >= total) {
                    $('#submit_form').find('.button-next').hide();
                    $('#submit_form').find('.button-submit').hide();
                    //displayConfirm();
                } else {
                    $('#submit_form').find('.button-next').hide();
                    $('#submit_form').find('.button-submit').hide();
                }
                App.scrollTo($('.page-title'));
            }

            // default form wizard
            $('#submit_form').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                onTabClick: function(tab, navigation, index, clickedIndex) {
                    success.hide();
                    error.hide();
                    if (form.valid() == false) {
                        return false;
                    }
                    handleTitle(tab, navigation, clickedIndex);
                },
                onNext: function(tab, navigation, index) {
                    success.hide();
                    error.hide();

                    if (form.valid() == false) {
                        return false;
                    }
                   // handleTitle(tab, navigation, index);
                },
                onPrevious: function(tab, navigation, index) {
                    success.hide();
                    error.hide();

                    handleTitle(tab, navigation, index);
                },
                onTabShow: function(tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#submit_form').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                    if (index == 1 && tab.attr('data-visit') == 'false') { //step2
                        var tpl = '<ul class="nav nav-tabs "> </ul> <div class="tab-content tab2-content "> </div>';

                        $('#tab2_addTab').bind('click', function() {
                            var $this = $(this);
                            var addTpl = $this.attr('data-addtpl');
                            if (addTpl == 'false') {
                                $('.createArea').html(tpl);
                                $this.attr('data-addtpl', 'true');
                            }
                            var target = $this.data('target');
                            var draw = $this.attr('data-draw'); //数字表示重复添加的次数，如果是undefined表示不允许重复添加同一个           
                            var url = $this.data('url');
                            var container = $this.data('container');

                            $(target).find('.createArea > .nav li').removeClass('active');
                            target = target + '_' + draw;
                            var a = $('<a data-toggle="tab">').attr({
                                'data-url': url,
                                'href': target
                            }).html('Form' + (parseInt(draw) + 1));
                            var li = $('<li class="active">').append(a); //创建一个li标签 
                            li.append('<input type="checkbox" value="' + target + '" name="select">');
                            //根据data-url上的值加载html
                            a.one('click', function(event) {
                                var $this = $(this);
                                var requestURL = $(this).data('url');
                                if (!requestURL) {
                                    return
                                }
                                var id = $(this).attr('href');
                                $(id).load(requestURL, function() { //加载完配置单后 执行的渲染动作
                                	
                                	PricePlanAdd.init("tab2",id,"");

                                    $(id + ' ul[data-set="addTarget"]').find('a').each(function(index) {
                                        $(this).attr('data-target', id + '_' + index);
                                    })
                                })
                            });

                            $this.closest('.tab-pane').find('.createArea > .nav').append(li);
                            //添加一个tab-content
                            $(container + '>.tab-pane').removeClass('active');
                            var div = $('<div class="tab-pane active price-plan-tab">').attr('id', target.substring(1)).html('<img src="'+APP_PATH+'/img/input-spinner.gif" alt="">')
                            $(container).append(div);
                            a.click();

                            draw = parseInt(draw) + 1;
                            $this.attr({
                                'data-draw': draw
                            });
                        });
                        //step2 控制选择按钮
                        $('#tab2-tools').on('click', '.btn-select', function() {
                                $('#tab2 .createArea').children('.nav-tabs').toggleClass('select');
                            })
                            //step2 控制删除按钮
                        $('#tab2-tools').on('click', '.btn-delete', function() {
                            var $this = $(this);
                            var $selected = $('#tab2 .createArea').children('.nav-tabs').find('input[type="checkbox"]:checked');
                            var selected = $selected.size();
                            var total = $('#tab2 .createArea').children('.nav-tabs').find('input').size();
                            if (selected != 0 && selected == total) {
                                $('#tab2 .createArea').html('<div class="well">您还未填写Charge Information,请点击右上方的添加按钮</div>');
                                $('#tab2-tools .btn-add').attr({
                                    'data-addtpl': 'false',
                                    'data-draw': '0'
                                });

                            } else if (selected != 0) {
                                $selected.each(function() {
                                    var $$this = $(this);
                                    var target = $$this.val();
                                    var $a = $('#tab2-tools').find('.btn-add');
                                    $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);

                                    $(target).remove();
                                    $$this.closest('li').remove();
                                });
                                $('#tab2 .createArea').children('.nav-tabs').find('li:first a').click();
                            }

                        })
                    } else if (index == 2 && tab.attr('data-visit') == 'false') { //step3
                        //控制copy 显示
                        $('body').on('click', 'input[name="type"]', function() {
                            var type = $(this).val();
                            if (type == 1) {
                                $('#settlementRule').addClass('display-none');
                            } else if (type == 2) {
                                $('#settlementRule').removeClass('display-none');
                            }
                        })
                        var type = $('input[name="type"]:checked').val(); //选择的模板类型                        
                        if (type == 1) {
                            $('#settlementRule').addClass('display-none');
                        } else if (type == 2) {
                            $('#settlementRule').removeClass('display-none');

                        }
                        //
                        var tpl = '<ul class="nav nav-tabs "></ul><div class="tab-content tab3-content"></div>';
                        $('#addSettlement').bind('click', function() {
                            var $this = $(this);
                            var addTpl = $this.attr('data-addtpl');
                            var type = $('input[name="type"]:checked').val();
                            var obj = $('[name="settlementObject"] option:selected').val(); //选择的对象   
                            var rule = $('#settlementRule option:selected').val(); //选择的对象   
                            var valid = false;
                            //添加模板                         
                            if (addTpl == 'false' && type == 1 && obj != '') {
                                $('.createArea2').html(tpl);
                                $this.attr('data-addtpl', 'true');
                                addTpl = 'true';
                            } else if (addTpl == 'false' && type == 2 && obj != '' && rule != '') {
                                $('.createArea2').html(tpl);
                                $this.attr('data-addtpl', 'true');
                                addTpl = 'true'
                            }
                            if (type == 1 && obj != '') {
                                valid = true;
                            } else if (type == 2 && obj != '' && rule != '') {
                                valid = true;
                            } else {
                                toastr.error("请选择正确的属性");
                            }
                            //模板创建完毕
                            if (addTpl == 'true' && valid == true) {
                                var target = $this.data('target');
                                var draw = $this.attr('data-draw'); //数字表示重复添加的次数，如果是undefined表示不允许重复添加同一个
                                var url;
                                switch (type) {
                                    case 1:
                                        url = 'ajax/settlementNew.html';
                                        break;
                                    default:
                                        url = 'ajax/settlementNew.html';
                                        break;
                                }
                                var container = $this.data('container');
                                var $container = $(container);

                                $('.createArea2 > .nav').find('li').removeClass('active');
                                target = target + '_' + draw;
                                var a = $('<a data-toggle="tab">').attr({
                                    'data-url': url,
                                    'href': target
                                }).html('Form' + (parseInt(draw) + 1));
                                var li = $('<li class="active">').append(a); //创建一个li标签 
                                li.append('<input type="checkbox" value="' + target + '" name="select">');
                                //根据data-url上的值加载html
                                a.one('click', function(event) {
                                    var $this = $(this);
                                    var requestURL = $(this).data('url');
                                    if (!requestURL) {
                                        return
                                    }
                                    var id = $(this).attr('href');
                                    $(id).load(requestURL, function() { //加载完配置单后 执行的渲染动作                                        
                                        addTabMenu(id);
                                        handleDatePickers(id + ' .date-picker');
                                        App.ajaxInit();
                                        //分配 dropdown-menu里面data-target的值
                                        $(id + ' ul[data-set="addTarget"]').find('a').each(function(index) {
                                            $(this).attr('data-target', id + '_' + index);
                                        })
                                    })
                                });

                                $('.createArea2 > .nav').append(li);
                                //添加一个tab-content
                                $(container + '>.tab-pane').removeClass('active');
                                var div = $('<div class="tab-pane active">').attr('id', target.substring(1)).html('<img src="'+APP_PATH+'/img/input-spinner.gif" alt="">')
                                $(container).append(div);
                                a.click();

                                draw = parseInt(draw) + 1;
                                $this.attr({
                                    'data-draw': draw
                                });
                            }
                        })
                        //step3 控制选择按钮
                        $('#tab3-tools').on('click', '.btn-select', function() {
                                $('#tab3 .createArea2').children('.nav-tabs').toggleClass('select');
                            })
                        //step3 控制删除按钮
                        $('#tab3-tools').on('click', '.btn-delete', function() {
                            var $this = $(this);
                            var $selected = $('#tab3 .createArea2').children('.nav-tabs').find('input[type="checkbox"]:checked');
                            var selected = $selected.size();
                            var total = $('#tab3 .createArea2').children('.nav-tabs').find('input').size();                            
                           if(selected !=0 && selected == total){
                            $('#tab3 .createArea2').html('<div class="well">您还未填写Charge Information,请点击右上方的添加按钮</div>');
                            $('#tab3-tools .btn-add').attr({'data-addtpl':'false','data-draw':'0'});
                            
                           }
                           else if (selected != 0) {
                                $selected.each(function() {
                                    var $$this = $(this);
                                    var target = $$this.val();                                    
                                    var $a = $('#tab2-tools').find('.btn-add');
                                    $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
                                    
                                    $(target).remove();
                                    $$this.closest('li').remove();
                                });
                                $('#tab3 .createArea2').children('.nav-tabs').find('li:first a').click();
                            }

                        })
                    }
                    tab.attr('data-visit', true);
                }
            });
            $('#submit_form').find('.button-previous').hide();
            $('#submit_form').find('.button-next').hide();
            $('#submit_form .button-submit').click(function() {}).hide();
            $('#saveBtn').click(onSubmit);
            $('#tab2_link').click(onSubmit);
            $('#tab2_link').click(onSubmit);
            
        	handleProductModal();
        	handleOfferModal();
        	handleExclusionOfferModal();
        	handleDatePickers('#tab1 .date-picker');
        	handleJstree('#tab1 .jstree');
        	
        }
    };
}();

