var PardOfferUpdate = function() {
	var productCurrIndex;
	var offerCurrIndex;
	var exclusionOfferCurrIndex ;
	
	var isHasPricingOrSettle=false;
	if($("#productTB tr:first-child>td").eq(0).html()=='None'){
		productCurrIndex=0;
	}else{
		productCurrIndex = $("#productTB tr").length;
	}
	
	if($("#offerTB tr:first-child>td").eq(0).html()=='None'){
		offerCurrIndex=0;
	}else{
		offerCurrIndex = $("#offerTB tr").length;
	}
	
	if($("#exclusionOfferTB tr:first-child>td").eq(0).html()=='None'){
		exclusionOfferCurrIndex=0;
	}else{
		exclusionOfferCurrIndex = $("#exclusionOfferTB tr").length;
	}
	
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
              $("#productModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
            	  	var objTr=$(this).closest('tr');
        		  	if(productCurrIndex==0){
      	  				$("#productTB tr").eq(0).remove();
      	  				addTr(objTr,objCk.id);
          	  		}else{
          	  			//查重
          	  			var _pass=true;
          	  			$('#productTB tr').each(function(j,objPTr) {
          	  				if(objPTr.id==objCk.id){
          	  					_pass=false;
          	  					return;
          	  				}
            		  	})
            		  	if(_pass){
      	  					addTr(objTr,objCk.id);
      	  				}
          	  		}
              });
        });
        
        //关闭产品选择模块
        $('.btn.btn-default.productModal.cancel').bind('click', function() {
        	$('#productModal').modal('hide');
        });
        //查询
        $(".queryProdcutModalBtn").click(function(){
        	var url = APP_PATH+"/pardProduct/winList.shtml?";
        	url += "prod_name="+$("#productModal_prod_name").val() + "&prod_code=" + $("#productModal_prod_code").val() 
        	    + "&provider=" + $("#operatorValue").val() + "&cooperationType=" + $("#productModal_cooperationType").val(); ; 
        	productModalDT.api().ajax.url(url).load();
        });
        
        var addTr = function(objTr,pId){
        	productCurrIndex++;
	  		var tr=[];
	  		tr.push("<td>"+productCurrIndex+"</td>");
	  		tr.push("<td>"+$(objTr).children('td').eq(1).html()+"</td>");
	  		tr.push("<td>"+$(objTr).children('td').eq(2).html()+"</td>");
	  		tr.push("<td><a onclick='productDetailModal("+pId+")'>"+$(objTr).children('td').eq(3).html()+"</a</td>");
	  		tr.push("<td><input type='text' class='form-control '  value='1'/></td>");
	  		tr.push("<td><input type='text' class='form-control '  value='1'/></td>");
	  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
	  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
    		$("#productTB").append(trStr);
        }
      });   
        
        //打开产品选择模块
	    $(".productModalPlus").bind('click', function() {
	    	$('#productModal').modal('show');
	    });
        $('#productTB').on('click', 'td .btn-del', function(e) {
            e.preventDefault();
            $tbody = $(this).closest('tbody');
            var objProductTr=  $(this).closest('tr');
            
           var prodOfferId= $("#prodOfferId").val();
           var productId=objProductTr.attr("id");
           isHasPricingPlanOrSettlementRule(prodOfferId,productId);
          if(isHasPricingOrSettle=="true"){
        	  toastr.error("the product has pricingPlan or settlementRule,you can not delete it");
        	  return;
          }else{
        	  $.ajax({
					async : false,
					type : "POST",
					url :  APP_PATH+"/pardOffer/deleteOfferProductRel.shtml",
					dataType : "json",
					data:{
       	 	    	 offerId:prodOfferId,
       	 	    	 productId: productId
        	 	    	},
					beforeSend:function(){
						
					},success : function(data) {
						if (data.code == "0000") {
							 if (productCurrIndex >0) {
					            	productCurrIndex--;
					                objProductTr.remove();
					                $tbody.find('tr>td:first-child').each(function(i) {
					                    $(this).html(i + 1);
					                })
					            }
					            if (productCurrIndex ==0) {
					            	$("#productTB").append('<tr> <td colspan="7">None</td> </tr>');
					            }
					            toastr.success('Delete Success');
						} else {
								toastr.error(data.desc);
						}
					},
			       error: function(xhr, textStatus, errorThrown) {
			    	   toastr.error("Delete Fail");
			       }
				});
          }
	       	
        })
	 }
	 var handleOfferModal = function() {
	        //弹窗展现前从后台加载表格数据
	        $('#offerModal').on('show.bs.modal', function(e) {
	        	 var offerModalDT =  $('#offerDt').dataTable({
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
	            	url += "off_name="+$("#offerModal_off_name").val() + "&off_code=" + $("#offerModal_off_code").val() + "&cooperationType=" + $("#offerModal_cooperationType").val() 
	            	    + "&provider=" + $("#operatorValue").val() + "&queryParamsData="+tableTrObjectId("exclusionOfferTB"); 
	            	offerModalDT.api().ajax.url(url).load();
	            });
	        });
	      //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
	        $('.btn.theme-btn.offerModal.ok').bind('click', function() {
	              $("#offerModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
	            	  	var objTr=$(this).closest('tr');
	        		  	if(offerCurrIndex==0){
	      	  				$("#offerTB tr").eq(0).remove();
	      	  				addTr(objTr,objCk.id);
	          	  		}else{
	          	  			//查重
	          	  			var _pass=true;
	          	  			$('#offerTB tr').each(function(j,objPTr) {
	          	  				if(objPTr.id==objCk.id){
	          	  					_pass=false;
	          	  					return;
	          	  				}
	            		  	})
	            		  	if(_pass){
	      	  					addTr(objTr,objCk.id);
	      	  				}
	          	  		}
	              });
	        });
	        
	        //取消融合offer模块
	        $('.btn.btn-default.offerModal.cancel').bind('click', function() {
	        	$('#offerModal').modal("hide");
	        });
	        //打开融合offer模块
	        $('.offerModalPlus').bind('click', function() {
	        	$('#offerModal').modal("show");
	        });
	        
	        var addTr=function(objTr,pId){
	        	offerCurrIndex++;
		  		var tr=[];
		  		tr.push("<td>"+offerCurrIndex+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(1).html()+"</td>");
		  		tr.push("<td>"+$(objTr).children('td').eq(2).html()+"</td>");
		  		tr.push("<td><a onclick='offerDetailModal("+pId+")'>"+$(objTr).children('td').eq(3).html()+"</a</td>");
		  		tr.push("<td><input type='text' class='form-control '  value='0'/></td>");
		  		tr.push("<td><input type='text' class='form-control '  value='1'/></td>");
		  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
		  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
	    		$("#offerTB").append(trStr);
	        }
	        $('#offerTB').on('click', 'td .btn-del', function(e) {
	            e.preventDefault();
	            $tbody = $(this).closest('tbody');
	            var objBuddleOfferTr=  $(this).closest('tr');
		       	 $.ajax({
						async : false,
						type : "POST",
						url :  APP_PATH+"/pardOffer/deleteBundleOrExclusionOffer.shtml",
						dataType : "json",
						data:{
	         	 	    	 offerId:
	              	 	    	 function() {
	                                return $("#prodOfferId").val();   
	                            },
	                            bundleOfferId: 
	                            	function() {
	                                return objBuddleOfferTr.attr("id");   
	                            }
	          	 	    	},
						beforeSend:function(){
							
						},success : function(data) {
							if (data.code == "0000") {
								 if (offerCurrIndex >0) {
						            	offerCurrIndex--;
						            	objBuddleOfferTr.remove();
						                $tbody.find('tr>td:first-child').each(function(i) {
						                    $(this).html(i + 1);
						                })
						            }
						            if (offerCurrIndex ==0) {
						            	$("#offerTB").append('<tr> <td colspan="7">None</td> </tr>');
						            }  
								toastr.success('Delete Success');
							} else {
								toastr.error(data.desc);
							}
						},
				       error: function(xhr, textStatus, errorThrown) {
				    	   toastr.error("Delete Fail");
				       }
					});
	        })
		 }
	 var handleExclusionOfferModal = function() {
	        //弹窗展现前从后台加载表格数据
	        $('#exclusionOfferModal').on('show.bs.modal', function(e) {
	        	var exclusionOfferModalDt =  $('#exclusionOfferDt').dataTable({
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
	            	url += "off_name="+$("#exclusionOfferModal_off_name").val() + "&off_code=" + $("#exclusionOfferModal_off_code").val() + "&cooperationType=" + $("#exclusionOfferModal_cooperationType").val()
	            	    + "&provider=" + $("#operatorValue").val() + "&queryParamsData="+tableTrObjectId("offerTB"); 
	            	exclusionOfferModalDt.api().ajax.url(url).load();
	         });
	      });
	        
	      //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
	        $('.btn.theme-btn.exclusionOfferModal.ok').bind('click', function() {
	              $("#exclusionOfferModal tbody tr input[type='checkbox']:checked").each(function(i,objCk) {
	            	  	var objTr=$(this).closest('tr');
	        		  	if(exclusionOfferCurrIndex==0){
	      	  				$("#exclusionOfferTB tr").eq(0).remove();
	      	  				addTr(objTr,objCk.id);
	          	  		}else{
	          	  			//查重
	          	  			var _pass=true;
	          	  			$('#exclusionOfferTB tr').each(function(j,objPTr) {
	          	  				if(objPTr.id==objCk.id){
	          	  					_pass=false;
	          	  					return;
	          	  				}
	            		  	})
	            		  	if(_pass){
	      	  					addTr(objTr,objCk.id);
	      	  				}
	          	  		}
	              });
	        });
	        
	        //打开互斥offer模块
	        $('.exclusionOfferModal').bind('click', function() {
	        	$('#exclusionOfferModal').modal("show");
	        });
	        var addTr=function(objTr,pId){
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
	            $tbody = $(this).closest('tbody');
	            var objExclusionOfferTr=  $(this).closest('tr');
		       	 $.ajax({
						async : false,
						type : "POST",
						url :  APP_PATH+"/pardOffer/deleteBundleOrExclusionOffer.shtml",
						dataType : "json",
						data:{
	         	 	    	 offerId:
	              	 	    	 function() {
	                                return $("#prodOfferId").val();   
	                            },
	                            bundleOfferId: 
	                            	function() {
	                                return objExclusionOfferTr.attr("id");
	                            }
	          	 	    	},
						beforeSend:function(){
							
						},success : function(data) {
							if (data.code == "0000") {
								  if (exclusionOfferCurrIndex >0) {
						            	exclusionOfferCurrIndex--;
						            	objExclusionOfferTr.remove();
						                $tbody.find('tr>td:first-child').each(function(i) {
						                    $(this).html(i + 1);
						                })
						            }
						            if (exclusionOfferCurrIndex ==0) {
						            	$("#exclusionOfferTB").append('<tr> <td colspan="7">None</td> </tr>');
						            }
						            toastr.success('Delete Success');
							} else {
									toastr.error(data.desc);
							}
						},
				       error: function(xhr, textStatus, errorThrown) {
				    	   toastr.error("Delete Fail");
				       }
					});
	        
	        })
		 }
	 
	 
	//判断产品是否包含定价或者结算
	var  isHasPricingPlanOrSettlementRule=function(prodOfferId,productId){
      	 $.ajax({
				async : false,
				type : "POST",
				url :  APP_PATH+"/pardOffer/checkHasPricingOrSettle.shtml",
				dataType : "json",
				data:{
  	 	    	 offerId:prodOfferId,
                 productId:productId
   	 	    	},
				beforeSend:function(){
					
				},success : function(data) {
					if (data.code == "0000") {
						isHasPricingOrSettle= data.result;
					} 
				}
			});
	};
	
	 var onTab1Submit=function(){
			var chk_value=[];
			 $('input[name="channel"]:checked').each(function(){    
				   chk_value.push($(this).val());    
				  });  
//			 if(chk_value.length==0){
//				 toastr.error("offer's channel is not empty!");
//				 return;
//			 }
			 $("#offerChannelStr").val(chk_value.join(","));
			 
			 
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
		 	
			 $.ajax({
				async : false,
				type : "POST",
				url :  APP_PATH+"/pardOffer/update.shtml",
				data : $('#tab1_submit_form').serialize(),
				beforeSend:function(){
					
				},success : function(data) {
					if (data.code == "0000") {
//						var offerId=data.desc;	  
						toastr.success('Save Success');
					} else {
						toastr.error(data.desc);
					}
				},
				dataType : "json",
		       error: function(xhr, textStatus, errorThrown) {
		    	   toastr.error("Save Fail");
		       	//called when there is an error
		       }
			});
		};

		var addTabMenu_ = function(selector) {
		    var tpl = '<ul class="nav nav-tabs fix tabs-content-ajax"></ul> <div class="tab-content"></div>';

		    $(selector).on('click', 'a[data-action="addTabMenu_"]', function() {
		            //防止多次添加模板
		            var addTpl = $(selector + ' .tabsArea').attr('data-addtpl');
		            if (addTpl == 'false') {
		                $(selector + ' .tabsArea').html(tpl).attr('data-addtpl', 'true');
		            }
		            var $this = $(this);
		            var target = $this.data('target');
		            var type = $this.data('type');
		            var draw = $this.attr('data-draw'); //数字表示重复添加的次数，如果是undefined表示不允许重复添加同一个
		            var title = $this.text();
		            var url = $this.data('url');
		            var datakey = $this.attr('data-key');
		            var container = $this.data('container');
		            var flag = $this.data('flag');
		            var $container = $(selector + ' ' + container);
		            //有设置改属性的，表明只能添加一次
		            if ($this.attr('data-unique') == 'true' && draw == '1') {
		                toastr.error("This attribute is exit!");
		                return
		            }
		            //添加一个tabMenu
		            $container.find('.nav li').removeClass('active');
		            //创建一个a标签,先判断是否允许重复添加 
		            if (draw != undefined) {
		                target = target + '_' + draw;
		                draw = parseInt(draw) + 1;
		                $this.attr({
		                    'data-draw': draw
		                });
		            }
		            var a = $('<a data-toggle="tab">').attr({
		                'data-url': url,
		                'data-key': datakey,
		                'href': target
		            }).html(title);
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
		                $(id).load(requestURL, function() {
		                	if(flag=='settle'){
		                		if (type == 1) {//rc
		                            handleJstree(id + ' .jstree');
		                            handleChargeTable(id);
		                            handleDatePickers(id + ' .date-picker');
		                            handleRcTableFunction();
		                        }else   if (type == 2) {//otc
		                        	handleDatePickers(id + ' .date-picker');
		                        }else if(type==4){
		                        	handleDatePickers(id + ' .date-picker');
		                        	setTimeout(handleRuleDetailInfoModal(datakey),1500);
		                        	handleAggTableFunction();
		                        }
		                	}
		                    App.ajaxInit();
		                })
		            });
		            $container.find('.nav').append(li);
		            //添加一个tab-content
		            $container.find('.tab-pane').removeClass('active');
		            var div = $('<div class="tab-pane active">').attr('id', target.substring(1)).html('<img src="'+APP_PATH+'/resources/img/input-spinner.gif" alt="">')
		            $container.find('.tab-content').append(div);
		            a.click();
		    })
		        //控制选择按钮
		    $(selector).on('click', '.btn-select', function() {
		            var $this = $(this);
		            $this.closest('.portlet').find('.nav-tabs').toggleClass('select');
		   })
		        //控制删除按钮
		    $(selector).on('click', '.btn-delete', function() {
		        var $this = $(this);
		        var $selected = $this.closest('.portlet').find('.nav-tabs input[type="checkbox"]:checked');
		        var selected = $selected.size();
		        if (selected != 0) {
	            	bootbox.confirm(_deleteConfirm, function(result) {
	    	       	if(result==true){
			            $selected.each(function() {
			                var $$this = $(this);
			                var target = $$this.val();
		                    var ruleIdV = $$this.closest('li').attr("ruleId");
		                    var ruleTypeV = $$this.closest('li').attr("ruleType");
		                    if(ruleIdV==undefined){
		                    	ruleIdV = $(target).find("input[id='settleRule.ruleId']").val();
		                    }
		                    if(ruleTypeV==undefined){
		                    	ruleTypeV = $(target).find("input[id=ruleType]").val();
		                    }
		                    //数据库中删除：
		                    if(ruleIdV!=undefined && ruleIdV!="" && ruleTypeV!=undefined && ruleTypeV!=""){
		                   			 $.ajax({
		             					async : false,
		             					type : "POST",
		             					url :  APP_PATH+"/pardOfferSettlement/detailDelete.shtml",
		             					data : {ruleId:ruleIdV,ruleType:ruleTypeV},
		             					success : function(data) {
		             						if (data.code == "0000") {
		             							//删除成功
		        		                    	delTab($$this,target);  //界面上删除
		             						} else {
		             							//删除失败
		             							alert(data.desc);
		             						}
		             					},
		             					dataType : "json",
		             		            error: function(xhr, textStatus, errorThrown) {
		         							//删除失败
		             		            }
		             				});
		                    }else{
		                    	delTab($$this,target);
		                    }
			            });
	    	       	}
	            	});
		        }
		        
		        //界面上删除:
		        delTab = function(obj,target){
	                var length = target.length;
	                var index = target.charAt(length - 3);
	                var $a = $this.closest('.dropdown').find('.dropdown-menu li').eq(index).children('a');
	                $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
	                $(target).remove();
	                obj.closest('li').remove();
                    //全删了：
                    var tabNum = $this.closest('.portlet').find('.nav-tabs input').size();
                    if (tabNum == 0) {
    		            $this.closest('.portlet').find('.portlet-body').html('<div class="well">'+_noSettlementInfo+'</div>');
    		            $this.closest('.dropdown').find('.dropdown-menu li a').each(function() {
    		                $(this).attr('data-draw', '0');
    		            })
    		            $this.closest('.portlet').children('.portlet-body').attr('data-addtpl', 'false');
                    } 
		            $this.closest('.portlet').find('.nav-tabs li:first a').click();
		        }
		    })

	        //编辑页面初始化显示第一页：
	    	var tab3Chils = $(selector).find(".tab3Chil_TabUL").find("a");
	    	if(tab3Chils.length>0){
	    		tab3Chils[0].click();
	    	}
		}


		 var handleJstree = function(selector) {
			 $.ajax({
					async : false,
					type : "POST",
					url :  APP_PATH+"/pardOffer/getPardChanneTree.shtml",
					data : {
						idvalue:$("#offerChannelStr").val()
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
							alert(data.desc);
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
		  //控制offset tyep 选择为offset时 显示输入框
		    var handleOffsetType = function(selector) {
		            var $offsetType = $(selector).find('.offsetType');
		            $(selector).on('change', '.offsetType', function() {
		                if ($offsetType.val() == 2) {
		                    $('.offset').show();
		                } else if ($offsetType.val() == 1) {
		                    $('.offset').hide();
		                }
		            })
		            if ($offsetType.val() == 2) {
		                $('.offset').show();
		            } else if ($offsetType.val() == 1) {
		                $('.offset').hide();
		            }
		        }
		        //控制 EFFECTIVE TIME 当单位选择为fixed expiry date时 input变为日期控件
		    var handleEffectiveTime = function(selector) {
		            var $unit = $(selector).find('.unit');
		            var $effectiveTime1 = $(selector).find('.effectiveTime1');
		            var $effectiveTime2 = $(selector).find('.effectiveTime2');

		            $(selector).on('change', '.unit', function() {
		                if ($unit.val() == 10) {
		                    $effectiveTime1.hide();
		                    $effectiveTime2.show();
		                } else {
		                    $effectiveTime1.show();
		                    $effectiveTime2.hide();
		                }
		            })
		            if ($unit.val() == 10) {
		                $effectiveTime1.hide();
		                $effectiveTime2.show();
		            } else {
		                $effectiveTime1.show();
		                $effectiveTime2.hide();
		            }
		        }
		        //控制 Charge Information中basic tariff面板内的charge表格的联动效果
		    var handleChargeTable = function(selector) {
		        $(selector).on('change', '.chargingUnit', function() {
		            var unit = $(this).find('option:selected').text();
		            $(selector + ' .unitArea').html(unit);
		        })
		        $(selector + ' input[name="chargingUnitValue"]').focusout(function() {
		            var chargingValue = $(this).val();
		            $(selector + ' .numArea').html(chargingValue);
		        })
		        $(selector).on('click', 'input[name="ChargeType"]', function() {
		            var chargeType = $(this).val();
		            if (chargeType == 1) {
		                $(selector + ' .simple').show();
		                $(selector + ' .ladder').hide();
		            } else if (chargeType == 2) {
		                $(selector + ' .simple').hide();
		                $(selector + ' .ladder').show();
		            }
		        })
		        $(selector).on('change', '#settleRuleMoneyUnit', function() {
		            var currency = $(this).find('option:selected').text();
		            $('.currencyArea').html(currency);
		        })

		        //初始化
		        $(selector + ' .unitArea').html($(selector + ' .chargingUnit').find('option:selected').text());
		        $(selector + ' .currencyArea').html($(selector + ' #settleRuleMoneyUnit').find('option:selected').text());
		        $(selector + ' .numArea').html($(selector + ' input[name="chargingUnitValue"]').val() ? $(selector + ' input[name="chargingUnitValue"]').val() : 1);
		        var chargeType = $(selector + ' input[name="ChargeType"]:checked').val();

		        if (chargeType == 1) {
		            $(selector + ' .simple').show();
		            $(selector + ' .ladder').hide();
		        } else if (chargeType == 2) {
		            $(selector + ' .simple').hide();
		            $(selector + ' .ladder').show();
		        }
		    }

		    ///////////////////////////////// 
		    
		    var loadSettlement=function(){
		    	var target =null;
	            var draw = $('#addSettlement').attr('data-draw');
	            var _url=null;
	            if(parseInt(draw)>0){
                 	target = $('#addSettlement').data('target') + '_' + (parseInt(draw)-1);
                 	_url=$('#tab3 .createArea2').children('.nav-tabs').find('li:eq('+(parseInt(draw)-1)+'):first a').attr("data-url");
                 	if(_url.indexOf("&formNum=")<0){
                 		_url += "&formNum="+i;
                 	}
                     $(target).load(_url, function() { //加载完配置单后 执行的渲染动作                 
         		        addTabMenu_(target);
         		        handleDatePickers(target + ' .date-picker');
         		        App.ajaxInit();
         		        //分配 dropdown-menu里面data-target的值
         		        $(target + ' ul[data-set="addTarget"]').find('a').each(function(index) {
         		            $(this).attr('data-target', target + '_' + index);
         		        });
         		        var draw1=$('#tab3 .createArea2').children('.tab-content.tab3-content').find(target).find('.portlet-body.tabsArea').attr("data-draw");
         		        if(parseInt(draw1)>0){
         		        	for(var j=1;j<=1;j++){
         		        		var _target1 = target + '_' + j;
             		        	var _url1=$('#tab3 .createArea2').children('.tab-content.tab3-content').find(target).find('.portlet-body.tabsArea').find('li:eq(0):first a').attr("data-url");
             		        	$(_target1).load(_url1, function() {
             		        		//addTabMenu(_target1);
             		        		$(_target1).attr({"isLoad":"1"});
             		        		handleDatePickers(_target1 + ' .date-picker');
             		        		handleRcTableFunction();
             		        		handleAggTableFunction();
                     		        App.ajaxInit();
             		        	});
         		        	}
         		        }
         		    });
                 }
		    }

	 
    return {
        //main function to initiate the module
        init: function() {
        	
        	$("body").on("keypress",function(e){
            	if (e.which == 13) {
            		e.preventDefault();		//阻止回车事件
            	}
        	});
        	
        	if (!jQuery().bootstrapWizard) {
                return;
            }
        	$('#detailLink').on('click', function() {
        		showProdForm.action=APP_PATH+"/pardOffer/toDetail.shtml";
        		showProdForm.submit();
            })
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
                        minlength: 4
                    },
                    "prodOffer.formatEffDate": {
                        required: true
                    },
                    "prodOffer.formatExpDate": {
                        required: true
                    },
                    "channel": {
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
                        minlength: jQuery.format("Please select  at least {0} types of extProdOfferId")
                    },
                    "prodOffer.formatEffDate": {
                        required: "Start time is required."
                    },
                    "prodOffer.formatExpDate": {
                        required: "End time is required."
                    },
                    "channel": {
                        required: "Please select sale channel at least 1 types."
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
                    handleTitle(tab, navigation, index);
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
                    if (index == 1 && tab.attr('data-visit') == 'false') {
                    	//step2:
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
                            var div = $('<div class="tab-pane active price-plan-tab">').attr('id', target.substring(1)).html('<img src="'+APP_PATH+'/resources/img/input-spinner.gif" alt="">')
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
                        
                        //step2 控制删除按钮（删除定价计划）
                        $('#tab2-tools').on('click', '.btn-delete', function() {
	                            var $this = $(this);
	                            var $selected = $('#tab2 .createArea').children('.nav-tabs').find('input[type="checkbox"]:checked');
	                            var selected = $selected.size();
	                            if (selected != 0) {
	                                bootbox.confirm(_deleteConfirm, function(result) {
	   	           	       			if(result==true){
		                            	var prodOfferId=$("#prodOfferId").val();
		                                $selected.each(function() {
		                                    var $$this = $(this);
		                                    var target = $$this.val();
		                                    var pricingInfoId = $$this.closest('li').attr("pricingInfoId");
		                                    var pricingTargetId = $$this.closest('li').attr("pricingTargetId");
		                                    if(pricingInfoId==undefined){
		                                    	pricingInfoId = $(target).find("input[id=pricingInfoId]").val();
		                                    }
		                                    if(pricingTargetId==undefined){
		                                    	pricingTargetId = $(target).find("input[id=pricingTargetId]").val();
		                                    }
		                                    //数据库中删除：
		                                    if(prodOfferId!=undefined && prodOfferId!="" 
		                                    	&& pricingInfoId!=undefined && pricingInfoId!="" 
		                                    		&& pricingTargetId!=undefined && pricingTargetId!=""){
				                           			 $.ajax({
				                     					async : false,
				                     					type : "POST",
				                     					url :  APP_PATH+"/pardOfferPricePlan/deletePricePlan.shtml",
				                     					data : {prodOfferId:prodOfferId,priceInfoId:pricingInfoId,pricingTargetId:pricingTargetId},
				                     					success : function(data) {
				                     						if (data.code == "0000") {
				                     							//删除成功
						                                    	delTab($$this,target);	                            
				                     						} else {
				                     							//删除失败
				                     							alert(data.desc);
				                     						}
				                     					},
				                     					dataType : "json",
				                     		            error: function(xhr, textStatus, errorThrown) {
			                     							//删除失败
				                     		            }
				                     				});
		                                    }else{
		                                    	delTab($$this,target);
		                                    }
		                                });
			           	       		}
		                            });
		                       }

                		       //界面上删除:
                		       delTab = function(obj,target){
                                    var $a = $('#tab2-tools').find('.btn-add');
                                    $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
                                    //界面上删除：
                                    $(target).remove();
                                    obj.closest('li').remove();
                                    //全删了：
    	                            var tabNum = $('#tab2 .createArea').children('.nav-tabs').find('input').size();
    	                            if(tabNum==0){
    	                            	 $('#tab2 .createArea').html('<div class="well">'+_addPricePlanInfo+'</div>');
    	                                 $('#tab2-tools .btn-add').attr({
    	                                     'data-addtpl': 'false',
    	                                     'data-draw': '0'
    	                                 });
    	                            }		
	                                $('#tab2 .createArea').children('.nav-tabs').find('li:first a').click();
                		       }
                        })
                        
                        //编辑页面初始化显示第一页：
                    	var tab2TabList = $(".tab2_TabUL").find("a");
                    	if(tab2TabList.length>0){
                    		tab2TabList[0].click();
                    	}
                    } else if (index == 2 && tab.attr('data-visit') == 'false') {
                    	//step3:
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
                        	var o2pCloudFlag=$("#o2pCloudFlag").val();
                        	 var operatorOrgId="";
                        	if(o2pCloudFlag=='cloud'){
                        		operatorOrgId=$("#operatorValue").val();
                        		if(operatorOrgId==''){
                        			toastr.error("please select an operator!");
                            		return ;
                        		}
                        	}
                        	
                        	var $this = $(this);
                            var addTpl = $this.attr('data-addtpl');
                            //var type = $('input[name="type"]:checked').val();
                            var obj = $('[name="settlementObject"] option:selected').val(); //选择的对象   
                            //产品id
                        	selectChangeCtr(obj);
                        	//针对同一产品添加完一个总结算规则，没保存，再添加另一个的情况
                        	var countFormForSameProduct="";
                        	_url=$('#tab3 .createArea2').children('.nav-tabs').find('li>a').attr("data-url");
                        	if(_url!=undefined){
                        		if(_url.indexOf(obj)>0){
                             		countFormForSameProduct="true";
                             	}
                        	}
                         
                            //var rule = $('#settlementRule option:selected').val(); //选择的对象   
                            var type = 1;
                            var rule =null;
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
                                toastr.error("please select a settle object!");
                            }
                            //模板创建完毕
                            if (addTpl == 'true' && valid == true) {
                            	var  productSettleCountFlag=$("#productSettleCount").val();
                                var target = $this.data('target');
                                var draw = $this.attr('data-draw'); //数字表示重复添加的次数，如果是undefined表示不允许重复添加同一个
                                if(productSettleCountFlag=="true"||countFormForSameProduct=="true"){
                                	toastr.error("can not add other settlement rule for the same product");
                            		return ;
                                }
                                var url;
                                switch (type) {
                                    case 1:
                                        url = APP_PATH+'/pardOfferSettlement/toSettlement.shtml?actionType=add&settleCycleDef.busiCode='+obj+'&settleSpBusiDef.servCode='+$("#prodOfferId").val()+'&operatorOrgId='+operatorOrgId+'&defaultName='+$("#prodOfferName").val().replace(/\s+/g,"%20");
                                        url=encodeURI(url);
                                        url=encodeURI(url);
                                        break;
                                    default:
                                        url = APP_PATH+'/pardOfferSettlement/toSettlement.shtml?actionType=add&settleCycleDef.busiCode='+obj+'&settleSpBusiDef.servCode='+$("#prodOfferId").val()+'&operatorOrgId='+operatorOrgId+'&defaultName='+$("#prodOfferName").val().replace(/\s+/g,"%20");
                                        url=encodeURI(url);
                                        url=encodeURI(url);
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
                    		        $(id).load(requestURL+"&formNum="+(parseInt(draw) + 1), function() { //加载完配置单后 执行的渲染动作            
                    		        	addTabMenu_(id);
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
                    		    var div = $('<div class="tab-pane active">').attr('id', target.substring(1)).html('<img src="'+APP_PATH+'/resources/img/input-spinner.gif" alt="">')
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
                            if (selected != 0) {
                               bootbox.confirm(_deleteConfirm, function(result) {
  	           	       			if(result==true){
	                                $selected.each(function() {
	                                    var $$this = $(this);
	                                    var target = $$this.val();       
		                                var busiIdV			= $$this.closest('li').attr("busiId");
	                                    var busiCodeV	= $$this.closest('li').attr("busiCode");
	                                    var servCodeV 	= $$this.closest('li').attr("servCode");
	                                    if(busiIdV==undefined){
	                                    	busiIdV = $(target).find("input[id='settleSpBusiDef.busiId']").val();
	                                    }
	                                    if(busiCodeV==undefined){
	                                    	busiCodeV = $(target).find("input[id='settleCycleDef.busiCode']").val();
	                                    }
	                                    if(servCodeV==undefined){
	                                    	servCodeV = $(target).find("input[id='settleSpBusiDef.servCode']").val();
	                                    }
		                                $.ajax({
		                 					type: "POST",
		                 					async:false,
	                     					url :  APP_PATH+"/pardOfferSettlement/deleteSettlement.shtml",
		                 				    dataType:'json',
		                 				    data:{busiCode:busiCodeV,servCode:servCodeV,busiId:busiIdV},
		                 					success:function(data){
		                 						if (data.code == "0000") {
	                     							//删除成功                             
		    	                                    var $a = $('#tab2-tools').find('.btn-add');
		    	                                    $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
		    	                                    $(target).remove();
		    	                                    $$this.closest('li').remove();
		    	                                    
		    	                                    var tabNum = $('#tab3 .createArea2').children('.nav-tabs').find('input').size();
		    	                                   if(tabNum == 0){
			    	                                    $('#tab3 .createArea2').html('<div class="well">'+_addSettlementInfo+'</div>');
			    	                                    $('#tab3-tools .btn-add').attr({'data-addtpl':'false','data-draw':'0'});
		    	                                   }
		    	                                    
		    		                                $('#tab3 .createArea2').children('.nav-tabs').find('li:first a').click();
	                     						} else {
	                     							//删除失败
	                     							alert(data.desc);
	                     						}
		                 					}
		                                });
	                                	
	                                });
		           	       		}
	                            });
                            }
                        })

                        //编辑页面初始化显示第一页：
                    	/*var tab3TabList = $(".tab3_TabUL").find("a");
                    	if(tab3TabList.length>0){
                    		tab3TabList[0].click();
                    	}*/
                    }
                    tab.attr('data-visit', true);
                }
            });
            $('#submit_form').find('.button-previous').hide();
            $('#submit_form').find('.button-next').hide();
            $('#submit_form .button-submit').click(function() {}).hide();
        	handleProductModal();
        	handleOfferModal();
        	handleExclusionOfferModal();
        	$('#saveBtnForUpdate').click(onTab1Submit);
        	handleDatePickers('#tab1 .date-picker');
        	handleJstree('#tab1 .jstree');
        	//LOAD SETTLE
        	loadSettlement();
        	
        }
    };
}();

$('#tab2_link').click(function(){
	handlePardOffertProdTreeJstree('#tab2');
});


$('#tab3_link').click(function(){
	changeProductListForSettlement();
});

var changeProductListForSettlement=function(){
	$.ajax({
		async : false,
		type : "POST",
		url :  APP_PATH+"/pardOffer/changeProductListForSettlement.shtml",
		dataType:'json',
	 	    data:{
 	    	 offerId:
	 	    	 function() {
 	    		 	return $("#prodOfferId").val();   
 	    	 	}
	 	    },success : function(data) {
				if (data.code == "0000") {
					var productListStr=data.productListForSettlement;
					$("#settlementObject").empty();
					for(i = 0, j = productListStr.length; i < j; i++){
						$("#settlementObject").append("<option value="+productListStr[i].PRODUCTID+">"+productListStr[i].PRODUCTNAME+"</option>");
						}
					}
			}
	});

};

var loadEx=function(_target,_url){
	var target=$(_target);
	var isLoad=target.attr("isLoad");
	if(isLoad=='0'){
		var o2pCloudFlag=$("#o2pCloudFlag").val();
   	    var operatorOrgId="";
      	if(o2pCloudFlag=='cloud'){
      		operatorOrgId=$("#operatorValue").val();
      	}
      	_url += '&operatorOrgId='+operatorOrgId+'&defaultName='+$("#prodOfferName").val().replace(/\s+/g,"%20");
      	_url=encodeURI(_url);
      	_url=encodeURI(_url);
		target.load(_url, function() {  
	 		//addTabMenu(_target);
			PardOfferSettlement.init(_target);
			target.attr({"isLoad":"1"});
	 		handleDatePickers(_target + ' .date-picker');
	 		handleRcTableFunction();
	 		handleAggTableFunction();
	 		App.ajaxInit();
	 		
	        //编辑页面初始化显示第一页：
	    	var tab3Chils = $(_target).find(".tab3Chil_TabUL").find("a");
	    	if(tab3Chils.length>0){
	    		tab3Chils[0].click();
	    	}
	 	});
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
var handleRcTableFunction = function() {
	   var currIndex ;
		if($("#recurrinngRuleTab>tbody").find("tr:first-child>td").eq(0).html()=='None'){
			currIndex=0;
		}else{
			currIndex =   $("#recurrinngRuleTab>tbody").find("tr").length;
		}
		
		return currIndex;
}


var handleRcTableAdd = function(obj) {
	 // add tr
	    var currIndex=handleRcTableFunction();
		var thisTable = $(obj).closest("table");
	  	if(currIndex==0){
	  		$("#recurrinngRuleTab>tbody").find("tr").eq(0).remove();
	  		addRcTableTr(currIndex,0,thisTable);
	  	}else{
	  	    var upValue="";
			var num1="";
			var num2="";
	    	var lastTr = $("#recurrinngRuleTab>tbody").find("tr").eq(currIndex-1);
	    	var inputs=$(lastTr).find("input");
	    	upValue = inputs.eq(1).val();
	   		num1 = inputs.eq(0).val();
	   		num2 = inputs.eq(1).val();
		    if(upValue=="-1"){
				toastr.error("end value is -1,you can not add other");
				$("#recurrinngRuleTab>tbody").find("tr").eq(currIndex - 1).find("input[id=upInputValue]").focus();
				return;
			}
			if((upValue!="-1")&& parseInt(num1)>=parseInt(num2)){
				toastr.error("Row"+currIndex+":"+_greater);
				$("#recurrinngRuleTab>tbody").find("tr").eq(currIndex - 1).find("input[id=upInputValue]").focus();
				return;
			}   
			addRcTableTr(currIndex,upValue,thisTable);
	  	}
}

var addRcTableTr=function(currIndex,rowStartValue,thisTable){
	currIndex++;
    var content='<tr><td>' + currIndex+ '</td>';
    content+='<td><div class="input-group input-xs">';
    content+='<input type="text"  id="downInputValue" name="from3" class="form-control" value="'+rowStartValue+'">';
    content+='<span class="input-group-addon">~</span>';
    content+='<input type="text"  id="upInputValue"  name="to3" class="form-control" value="-1">';
    content+='</div>';
    content+='<td><input type="text" name="" placeholder="" size="15" class="form-control input-xs" value="0"></td>';
    content+='<td><input type="text" id="baseRcValue" name="" placeholder="" size="15" class="form-control input-xs" value="0"></td>';
    content+='<td align="center">';
    content+='<a class="btn default btn-sm black btn-del"  onclick="handleRcTableDel(this);">Delete </a>';
    content+='</td>';
    content+='<input type="hidden" value=""  id="ruleConditionIdForDel"/></tr>';
    thisTable.find('tbody').append(content);
}

var handleRcTableDel = function(obj) {
        // remove tr
        var currIndex=handleRcTableFunction();
        var $tbody = $(obj).closest('tbody');
        var recurrinngRuleTr=  $(obj).closest('tr');
        var recurrinngRuleTrId=recurrinngRuleTr.attr("id");
        var ruleConditionId='';
        if(recurrinngRuleTrId!=' '&&recurrinngRuleTrId!=undefined){
        	ruleConditionId=recurrinngRuleTrId;
        }else{
        	ruleConditionId=recurrinngRuleTr.find("input[id=ruleConditionIdForDel]").val();
        }
        if(ruleConditionId!=''&&ruleConditionId!=undefined){
        	 if (currIndex> 1) {
            	 $.ajax({
     				async : false,
     				type : "POST",
     				url :  APP_PATH+"/pardOfferSettlement/deleteSettleRuleCondition.shtml",
     				dataType : "json",
     				data:{
                             ruleConditionId: ruleConditionId
           	 	    },
     				beforeSend:function(){
     					
     				},success : function(data) {
     					if (data.code == "0000") {
     						       currIndex--;
     				               recurrinngRuleTr.remove();
     				                $tbody.find('tr>td:first-child').each(function(i) {
     				                	$(this).html(i + 1);
     				                })
     						toastr.success('Delete Success');
     					} else {
     						toastr.error(data.desc);
     					}
     				},
     		       error: function(xhr, textStatus, errorThrown) {
     		    	   toastr.error("Delete Fail");
     		       }
     			});
            }else{
            	var finalValue=$tbody.find("tr").eq(0).find("input[id=upInputValue]").val();
            	if(finalValue=="-1"){
            		 toastr.error("end value is -1 of last row, you can not delete it");
                     return;
            	}
            }
        }else{
        	  currIndex--;
              recurrinngRuleTr.remove();
               $tbody.find('tr>td:first-child').each(function(i) {
               	$(this).html(i + 1);
               })
        }
}


var handleAggTableFunction = function() {
	 var currIndex ;
	if($("#aggregationTab>tbody").find("tr:first-child>td").eq(0).html()=='None'){
		currIndex=0;
	}else{
		currIndex =   $("#aggregationTab>tbody").find("tr").length;
	}
	
	return currIndex;
}


var addAggregationTableTr=function(currIndex,rowStartValue,thisTable){
	 currIndex++;
     var content='<tr><td>' + currIndex + '</td>';
     content+='<td><div class="input-group input-xs">';
     content+='<input type="text" id="downValue" class="form-control" value='+rowStartValue+'>';
     content+='<span class="input-group-addon">~</span>';
     content+='<input type="text" id="upValue" class="form-control" value="-1">';
     content+='</div>';
     content+='<td><div class="input-group input-xs"><input type="text" id="ratioNumerator" placeholder="" class="form-control" value="100">';
     content+='<span class="input-group-addon">/</span><input type="text" id="ratioDemominator" class="form-control" value="100"></td>';
     content+='<td><input id="baseValue" type="text" placeholder="" class="form-control input-xs" value="0"></td>';
     content+='<td>';
     content+='<a class="btn default btn-sm black btn-del" href="javascript:;" onclick="handleAggTableDel(this);"> <i class="fa fa-trash-o"></i> Delete </a>';
     content+='</td>';
     content+='<input type="hidden" value=""  id="ruleConditionIdForDel"/></tr>';
     thisTable.find('tbody').append(content);
}

var handleAggTableAdd = function(obj) {
	// add tr
		var currIndex=handleAggTableFunction();
		var thisTable = $(obj).closest("table");
	  	if(currIndex==0){
	  		$("#aggregationTab>tbody").find("tr").eq(0).remove();
	  		addAggregationTableTr(currIndex,0,thisTable);
	  	}else{
	        var upValue="";
			var num1="";
			var num2="";
	    	var lastTr =  $(thisTable).find("tbody>tr").eq(currIndex-1);
	    	var inputs=$(lastTr).find("input");
	    	upValue = inputs.eq(1).val();
	   		num1 = inputs.eq(0).val();
	   		num2 = inputs.eq(1).val();
		    if(upValue=="-1"){
				toastr.error("end value is -1,you can not add other");
				 $(thisTable).find("tbody>tr").eq(currIndex - 1).find("input[id=upValue]").focus();
				return;
			}
			if((upValue!="-1")&& parseInt(num1)>=parseInt(num2)){
				toastr.error("Row"+currIndex+":"+_greater);
				$(thisTable).find("tbody>tr").eq(currIndex - 1).find("input[id=upValue]").focus();
				return;
			}   
			addAggregationTableTr(currIndex,upValue,thisTable);
	  	}

}

var handleAggTableDel = function(obj) {
    // remove tr
        var currIndex=handleAggTableFunction();
        var $tbody = $(obj).closest('tbody');
        var aggTr=  $(obj).closest('tr');
        var aggTrId=aggTr.attr("id");
        var ruleConditionId='';
        if(aggTrId!=''&&aggTrId!=undefined){
        	ruleConditionId=aggTrId;
        }else{
        	ruleConditionId=aggTr.find("input[id=ruleConditionIdForDel]").val();
        }
        if(ruleConditionId!=''&&ruleConditionId!=undefined){
        	  if (currIndex> 1) {
             	 $.ajax({
      				async : false,
      				type : "POST",
      				url :  APP_PATH+"/pardOfferSettlement/deleteSettleRuleCondition.shtml",
      				dataType : "json",
      				data:{
                              ruleConditionId: ruleConditionId
            	 	    },
      				beforeSend:function(){
      					
      				},success : function(data) {
      					if (data.code == "0000") {
      				                currIndex--;
      				                aggTr.remove();
      				                $tbody.find('tr>td:first-child').each(function(i) {
      				                	$(this).html(i + 1);
      				                })
      						toastr.success('Delete Success');
      					} else {
      						toastr.error(data.desc);
      					}
      				},
      		       error: function(xhr, textStatus, errorThrown) {
      		    	   toastr.error("Delete Fail");
      		       }
      			});
             }else{
             	var finalValue=$tbody.find("tr").eq(0).find("input[id=upValue]").val();
             	if(finalValue=="-1"){
             		 toastr.error("end value is -1 of last row, you can not delete it");
                      return;
             	}
             }
        }else{
        	  currIndex--;
              aggTr.remove();
              $tbody.find('tr>td:first-child').each(function(i) {
              	$(this).html(i + 1);
              })
       }
}
    
    
var handlePardOffertProdTreeJstree = function(selector) {
    var prodOfferId = $("#prodOfferId").val();
    var relIds = $(selector).find("#relIds").val();
    $.ajax({
        async: false,
        type: "POST",
        url: APP_PATH + "/pardOffer/getPardOffertProdTree.shtml",
        dataType: "json",
        data: {
            prodIds: prodOfferId,
            idvalue: relIds
        },
        success: function(data) {
            if (data.code == "0000") {
            	if($(selector + ' .pp').data('jstree')){
                	$(selector + ' .pp').data('jstree').destroy();
                }
                $(selector + ' .pp').jstree({
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
                $(selector + ' .pp').on('changed.jstree', function(e, data) {
                    var prodOfferId = $("#prodOfferId").val();
                    prodOfferId = "A" + prodOfferId;
                    var checkTextAry = [];
                    var checkIdAry = [];
                    for (i = 0, j = data.selected.length; i < j; i++) {
                        if (data.instance.get_node(data.selected[i]).id != prodOfferId) {
                            checkTextAry.push(data.instance.get_node(data.selected[i]).text);
                            checkIdAry.push(data.instance.get_node(data.selected[i]).id);
                        }
                    }
                    $(selector).find("#jstreeTextString").val(checkTextAry.join(","));
                    $(selector).find("#relIds").val(checkIdAry.join(","));
                });
            } else {
                toastr.error(data.desc);
            }
        }
    });
}


var changeOperatorValue=function(obj){
	$(obj).toggleClass('select');
	if($(obj).hasClass('select')){
		$("#operator").val($(obj).html());
		$("#operatorValue").val($(obj).attr("sdata"));
	}else{
		$("#operator").val("");
		$("#operatorValue").val("");
	}
	
	$("#operatorDropDown a").each(function(index,element){
	    if($(element).text()!=$(obj).html()){
	    	$(element).removeClass("select");
	    }
   })
} 
   var selectChangeCtr=function(productId){
		$.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/pardOffer/queryOfferAndProductSettlement.shtml",
			dataType:'json',
 	 	    data:{
	 	    	 offerId:
  	 	    	 function() {
                    return $("#prodOfferId").val();   
                },
                productId:
                	productId
 	 	    },success : function(data) {
 				if (data.code == "0000") {
 				 $("#productSettleCount").val(data.result);
 				} 
 			}
		});
	}
   
   
   var settleRuleOrgRelOperChange=function(){
		$.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/pardOffer/updateSettleRuleOrgRelOperator.shtml",
			dataType:'json',
 	 	    data:{
	 	    	 offerId:
  	 	    	 function() {
                    return $("#prodOfferId").val();   
                },
                productId:
                	function(){
                	 return $('[name="settlementObject"] option:selected').val();
                },
                operatorId:
                	function(){
                	return $("#operatorValue").val();
                }
 	 	    },success : function(data) {
 				if (data.code == "0000") {
 					toastr.success('set product[] settlement rule to the new operator!');
 				} 
 			}
		});
   }
