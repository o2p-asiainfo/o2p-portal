var SettlementAdd = function() {
	var handleFunction = function() {
	    var currIndex = 1;
	        // add tr
	    $('.table-agg').on('click', 'th .fa-plus', function() {
	            
	            //判断最后一行是否为'-1',如果是-1则不可以添加
//	            $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(len-1).find("input[id=endVal]").focus();
//				toastr.error(_endWith1);
	            currIndex = $(this).closest('table').find('tbody>tr').length;
	            var endVal = $(this).closest('table').find('tbody>tr').eq(currIndex-1).find("input[id=engVal]").val();
	            var nextBeginVal = endVal;
	            if(endVal == '-1'){
	            	toastr.error(_endWith1);
	            	return;
	            }
	            currIndex++;
	            var content='<tr><td>' + currIndex + '</td>';
	            content+='<td><div class="input-group input-xs">';
	            content+='<input type="text" name="from3" id="beginVal" class="form-control" value="'+nextBeginVal+'">';
	            content+='<span class="input-group-addon">~</span>';
	            content+='<input type="text" name="to3" id="engVal" class="form-control" value="-1">';
	            content+='</div>';
	            content+='<td><div class="input-group input-xs"><input type="text" name="" placeholder="" class="form-control" value="0">';
	            content+='<span class="input-group-addon">/</span><input type="text" name="to3" class="form-control" value="1"></td>';
	            content+='<td><input type="text" name="" placeholder="" class="form-control input-xs" value="0"></td>';
	            content+='<td>';
	            content+='<a class="btn default btn-sm black btn-del" href="#"> <i class="fa fa-trash-o"></i> Delete </a>';
	            content+='</td>';
	            content+='<input type="hidden" value=""/></tr>';
	            $(this).closest('table').find('tbody').append(content);

	        })
	        // remove tr
	    $('.table-agg').on('click', 'td .btn-del', function(e) {

	    	e.preventDefault();
	    	  var conid = $(this).closest('a')[0].id;
	    	  
	    	  if(conid!=null&&conid!=""){
	    		  
	    	 
	          $.ajax({
	         		type: "POST",
	         		async:true,
	         	    url: APP_PATH+'/provider/delCondition.shtml?ruleId='+$('#ruleId').val()+'&conditionId='+conid,
	         	    dataType:'json',
	         	   // data:thisForm.serialize(),
	         	    success:function(msg){
	         	    	//{"priceId":"100000010","code":"0000","desc":"Success"}
	         	    	if(msg.code!=null && msg.code=="0000" ){
	         		    	 //删除
	         	    		toastr.success('Success');
	         	    	}else{
	         				toastr.error('Save Fail.');
	         				return;
	         	    	}
	         	    }
	         	});
	    	  }
	        if ($(this).closest('tbody').find('tr').size() > 1) {
	            currIndex--;
	            $tbody = $(this).closest('tbody');
	            $(this).closest('tr').remove();
	            $tbody.find('tr>td:first-child').each(function(i) {
	                $(this).html(i + 1);
	            })
	        }
	    })
	    
	    $('.currencyArea').html($('#moneyUnit').find('option:selected').text());
	    
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
	 //初始化值
	var default_val = function(){
		$('#cycleCount').val(1);
		
	}

	$(document).on('click','#saveSettleApi',function(){
		 
		 
//		var ruleId = $('#ruleId').val();
//		var servCode = $('#servCode').val();
		var ruleName = $('#ruleName').val();
		var cycleCount = $('#cycleCount').val();
//		var cycleType = $('#cycleType').val();
//		var moneyUnit = $('#moneyUnit').val();
//		var chargeDir = $('#chargeDir').val();
//		var description = $('#description').val();
//		var forEffDate = $('#forEffDate').val();
//		var forExpDate = $('#forExpDate').val();
		 if(ruleName==""||ruleName==null){
			 toastr.error(_ruleNameNull);
			 return;
		 }
		 if(cycleCount==""||cycleCount==null){
			 toastr.error("Please enter the cycle value.");
			 return;
		 }
		
		var upValue=[];
		var downValue=[];
		var ratioNumerator=[];
		var ratioDemominator=[];
		var baseValue=[];
		var ruleConditionId=[];
		var rowEnd = "";
		var rowStart = "";
		var rowBeforeStart = "";
		var rowBeforeEnd = "";
		var flag = 1;
		$('#settle_submit_form').find(".table.table-bordered.table-condensed.table-advance.table-function.table-agg").find("tbody>tr").each(function(i,obj){
			var _input=$(this).find("input");
			rowBeforeStart = rowStart;
			rowBeforeEnd = rowEnd;
			rowStart = _input.eq(0).val();
			rowEnd = _input.eq(1).val();
			if(rowEnd!='-1'){
				if(rowStart>rowEnd){
					flag = 0;
					toastr.error(_greater);
					return false;
				}
			}
			if(rowBeforeStart!=""&&rowBeforeEnd!=""){
				if(rowBeforeEnd!=rowStart){
					flag = 0;
					toastr.error(_equal1+_equal2);
					return false;
				}
			}
			
			downValue.push(_input.eq(0).val());
			upValue.push(_input.eq(1).val());
			ratioNumerator.push(_input.eq(2).val());
			ratioDemominator.push(_input.eq(3).val());
			baseValue.push(_input.eq(4).val());
			ruleConditionId.push(_input.eq(5).val());
		});
		if(flag == 0){
			return;
		}
		if(rowEnd!=""&&rowEnd!='-1'){
			flag = 0;
			toastr.error(_endWith1);
			return;
		}
		
		$('#settle_submit_form input[id="upValue"]').val(upValue.join(";"));
		$('#settle_submit_form input[id="downValue"]').val(downValue.join(";"));
		$('#settle_submit_form input[id="ratioNumerator"]').val(ratioNumerator.join(";"));
		$('#settle_submit_form input[id="ratioDemominator"]').val(ratioDemominator.join(";"));
		$('#settle_submit_form input[id="baseValue"]').val(baseValue.join(";"));
		$('#settle_submit_form input[id="ruleConditionId"]').val(ruleConditionId.join(";"));
		
//		var upValueStr = upValue.join(";");
//		var downValueStr = downValue.join(";");
//		var ratioNumeratorStr = ratioNumerator.join(";");
//		var ratioDemominatorStr = ratioDemominator.join(";");
//		var baseValueStr = baseValue.join(";");
//		var ruleConditionIdStr = ruleConditionId.join(";");
		
		
//		var dataVals = 
//		saveOrUpdateAPI
		$.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/provider/saveOrUpdateAPI.shtml",
//			data : {
//				ruleName:ruleName,
//				cycleCount:cycleCount,
//				cycleType:cycleType,
//				moneyUnit:moneyUnit,
//				chargeDir:chargeDir,
//				upValue:upValueStr,
//				downValue:downValueStr,
//				ratioNumerator:ratioNumeratorStr,
//				ratioDemominator:ratioDemominatorStr,
//				baseValue:baseValueStr,
//				ruleConditionId:ruleConditionIdStr,
//				servCode:servCode,
//				description:description,
//				forEffDate:forEffDate,
//				forExpDate:forExpDate
//				 
//			},
			data: $('#settle_submit_form').serialize(),
			beforeSend:function(){
				
			},success : function(data) {
				if (data.code == "0000") { 
					toastr.success('Save Success');
					if($('#settle_submit_form input[id="actionType"]').val()=='add'){
						$('#settle_submit_form input[id="actionType"]').val("update");
					}
					var   prodOfferId =  $("#hidProdOfferId").val();//closest('tr').children('td').eq(1).html()
					
					//var pricePlanTdval =  $('#'+prodOfferId).closest('tr').children('td').eq(3).html();
					var strvala = "<a href='javascript:;' class='editSettle' id="+data.ruleId+"> "+ruleName+"</a>" 
					             +"<a href='javascript:;' class='delSettle'  id='0minus0"+data.ruleId+"'><i class='fa fa-times-circle'></i></a> ";
					//<a href="javascript:;" class="editPricePlan" name ="${pricePlan.PRICINGTARGETID}" id="${pricePlan.PRICINGINFOID}"> ${pricePlan.PRICINGNAME }</a> 
				       
					$('#'+prodOfferId).closest('tr').children('td').eq(4).html(strvala);
					$('#modal6').modal('hide');
					//$('#settle_submit_form div[id="portlet"]').show();
				} else {
					toastr.error(data.desc);
				}
			},
			dataType : "json",
	        error: function(xhr, textStatus, errorThrown) {
	        	toastr.error("Save Fail");
	        }
		});
		
		
		
	})
	
    return {
        //main function to initiate the module
        init: function() { 
        	handleDatePickers('#modal6' + ' .date-picker');
        	handleFunction();
        }
    };
}();
//var onSettlementSave=function(_key){
//	$.ajax({
//		async : false,
//		type : "POST",
//		url :  APP_PATH+"/pardOfferSettlement/saveSettle.shtml",
//		data : $('#'+_key+'submit_form').serialize(),
//		beforeSend:function(){
//			
//		},success : function(data) {
//			if (data.code == "0000") {
//				toastr.success('Save Success');
//				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
//					$('#'+_key+'submit_form input[id="actionType"]').val("update");
//					$('#'+_key+'submit_form input[id="settleCycleDef.cycleDefId"]').val(data.desc.cycleDefId);
//					$('#'+_key+'submit_form input[id="settleSpBusiDef.busiId"]').val(data.desc.busiId);
//					$('#'+_key+'submit_form input[id="settleCycleDef.syncFlag"]').val(data.desc.settleCycleDefSyncFlag);
//					$('#'+_key+'submit_form input[id="settleSpBusiDef.syncFlag"]').val(data.desc.settleSpBusiDefSyncFlag);
//				}
//				$('#'+_key+'submit_form div[id="portlet"]').show();
//			} else {
//				toastr.error(data.desc);
//			}
//		},
//		dataType : "json",
//        error: function(xhr, textStatus, errorThrown) {
//        	toastr.error("Save Fail");
//        }
//	});
//}

 


//var onSettlementRcSave=function(_key){
//	var upValue=[];
//	var downValue=[];
//	var ratioNumerator=[];
//	var baseValue=[];
//	var ruleConditionId=[];
//	$('#'+_key+'submit_form').find(".table.table-bordered.table-condensed.table-advance.table-function.table-rc").find("tbody>tr").each(function(i,obj){
//		var _input=$(this).find("input");
//		downValue.push(_input.eq(0).val());
//		upValue.push(_input.eq(1).val());
//		ratioNumerator.push(_input.eq(2).val());
//		baseValue.push(_input.eq(3).val());
//		ruleConditionId.push(_input.eq(4).val());
//	})
//	$('#'+_key+'submit_form input[id="upValue"]').val(upValue.join(";"));
//	$('#'+_key+'submit_form input[id="downValue"]').val(downValue.join(";"));
//	$('#'+_key+'submit_form input[id="ratioNumerator"]').val(ratioNumerator.join(";"));
//	$('#'+_key+'submit_form input[id="baseValue"]').val(baseValue.join(";"));
//	$('#'+_key+'submit_form input[id="ruleConditionId"]').val(ruleConditionId.join(";"));
//	$.ajax({
//		async : false,
//		type : "POST",
//		url :  APP_PATH+"/pardOfferSettlement/saveRC.shtml",
//		data : $('#'+_key+'submit_form').serialize(),
//		beforeSend:function(){
//			
//		},success : function(data) {
//			if (data.code == "0000") {
//				toastr.success('Save Success');
//				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
//					$('#'+_key+'submit_form input[id="actionType"]').val("update");
//					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
//					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
//					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
//				}
//			} else {
//				toastr.error(data.desc);
//			}
//		},
//		dataType : "json",
//        error: function(xhr, textStatus, errorThrown) {
//        	toastr.error("Save Fail");
//        }
//	});
//}
//var onSettlementOtcSave=function(_key){
//	$.ajax({
//		async : false,
//		type : "POST",
//		url :  APP_PATH+"/pardOfferSettlement/saveOTC.shtml",
//		data : $('#'+_key+'submit_form').serialize(),
//		beforeSend:function(){
//			
//		},success : function(data) {
//			if (data.code == "0000") {
//				toastr.success('Save Success');
//				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
//					$('#'+_key+'submit_form input[id="actionType"]').val("update");
//					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
//					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
//					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
//				}
//			} else {
//				toastr.error(data.desc);
//			}
//		},
//		dataType : "json",
//        error: function(xhr, textStatus, errorThrown) {
//        	toastr.error("Save Fail");
//        }
//	});
//}
//var onSettlementAggregationSave=function(_key){
//	var upValue=[];
//	var downValue=[];
//	var ratioNumerator=[];
//	var ratioDemominator=[];
//	var baseValue=[];
//	var ruleConditionId=[];
//	$('#'+_key+'submit_form').find(".table.table-bordered.table-condensed.table-advance.table-function.table-agg").find("tbody>tr").each(function(i,obj){
//		var _input=$(this).find("input");
//		downValue.push(_input.eq(0).val());
//		upValue.push(_input.eq(1).val());
//		ratioNumerator.push(_input.eq(2).val());
//		ratioDemominator.push(_input.eq(3).val());
//		baseValue.push(_input.eq(4).val());
//		ruleConditionId.push(_input.eq(5).val());
//	})
//	$('#'+_key+'submit_form input[id="upValue"]').val(upValue.join(";"));
//	$('#'+_key+'submit_form input[id="downValue"]').val(downValue.join(";"));
//	$('#'+_key+'submit_form input[id="ratioNumerator"]').val(ratioNumerator.join(";"));
//	$('#'+_key+'submit_form input[id="ratioDemominator"]').val(ratioDemominator.join(";"));
//	$('#'+_key+'submit_form input[id="baseValue"]').val(baseValue.join(";"));
//	$('#'+_key+'submit_form input[id="ruleConditionId"]').val(ruleConditionId.join(";"));
//	$.ajax({
//		async : false,
//		type : "POST",
//		url :  APP_PATH+"/pardOfferSettlement/saveAggregation.shtml",
//		data : $('#'+_key+'submit_form').serialize(),
//		beforeSend:function(){
//			
//		},
//		success : function(data) {
//			if (data.code == "0000") {
//				toastr.success('Save Success');
//				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
//					$('#'+_key+'submit_form input[id="actionType"]').val("update");
//					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
//					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
//					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
//				}
//			} else {
//				toastr.error(data.desc);
//			}
//		},
//		dataType : "json",
//        error: function(xhr, textStatus, errorThrown) {
//        	toastr.error("Save Fail");
//        }
//	});
//}

var handleRuleDetailInfoModal = function(_key) {
	var ruleDetailCurrIndex=0;
	_key="settle"+_key+"_";
	var busiCode=$('#'+_key+'submit_form').find("#busiCode").val();
	var servCode=$('#'+_key+'submit_form').find("#servCode").val();
	var ruleDetailInfoDt;
    //弹窗展现前从后台加载表格数据
	$('#'+_key+'submit_form div[id="ruleDetailInfoModal"]').on('show.bs.modal', function(e) {
		ruleDetailInfoDt = $('#'+_key+'submit_form').find("#ruleDetailInfoDt").dataTable({
            "processing": true,
            "serverSide": true,
            "searching":  true,
            "ajax": APP_PATH+"/pardOfferSettlement/getAggregationRuleInfo.shtml?busiCode="+busiCode+"&servCode="+servCode,
            "order": [],
            "autoWidth": false,
            "columnDefs": [{
                "orderable": false,
                "targets": 0,
                "width": "36px"
            }/*, {
            	 "targets": 5,
                 "bVisible": false
            }, {
               	 "targets": 6,
                 "bVisible": false
            }, {
           	 "targets": 7,
           	 "bSearchable": true,
           	 "bVisible":true
            }*/],
            "initComplete": function() {
                App.init();
                App.initUniform();
                /*$('#ruleDetailInfoModal tbody').find('input[type="checkbox"]:checked').each(function(i,objCk) {
                	$('#ruleDetailInfoTB tr').each(function(j,objPTr) {
      	  				if(objPTr.id==objCk.id){
      	  					$(this).closest('tr').addClass('active');
      	  					return;
      	  				}
        		  	})
                })*/
            }
        });
//		jQuery('#ruleDetailInfoDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
//      jQuery('#ruleDetailInfoDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 
        
        jQuery('#'+_key+'submit_form').find('#ruleDetailInfoDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#'+_key+'submit_form').find('#ruleDetailInfoDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
    
    });
    
  //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
//	$('#'+_key+'submit_form').find('.okRuleDetailInfoModal').bind('click', function() {
//		var $ruleDetailInfoModal = $(this).closest("#ruleDetailInfoModal");
//		
//		$ruleDetailInfoModal.modal('hide');
//		
//		$ruleDetailInfoModal.find("tbody input[type='checkbox']:checked").each(function(i, objCk) {
//			var objTr=ruleDetailInfoDt.fnGetData(ruleDetailInfoDt.$('tr.active').get(i));
//			if(ruleDetailCurrIndex==0){
//		  		$('#'+_key+"submit_form").find("#ruleDetailInfoTB tr").eq(0).remove();
//	  				addTr(objTr,objCk.id);
//  	  		}else{
//  	  			//查重
//  	  			var _pass=true;
//  	  			$('#'+_key+"submit_form").find('#ruleDetailInfoTB tr').each(function(j,objPTr) {
//  	  				if(objPTr.id==objCk.id){
//  	  					_pass=false;
//  	  					return;
//  	  				}
//    		  	})
//    		  	if(_pass){
//	  					addTr(objTr,objCk.id);
//	  				}
//  	  		}
//        });
//    });
	//取消事件
//	$('#'+_key+'submit_form').find('.cancelRuleDetailInfoModal').bind('click', function() {
//		$(this).closest("#ruleDetailInfoModal").modal('hide');
//	});
	
    var addTr=function(objTr,pId){
    	ruleDetailCurrIndex++;
  		var tr=[];
  		tr.push("<td>"+ruleDetailCurrIndex+"</td>");
  		tr.push("<td>"+objTr[1]+"</td>");
  		tr.push("<td>"+objTr[2]+"</td>");
  		tr.push('<td><a href="#" class="btn default btn-sm black btn-del"> <i class="fa fa-trash-o"></i> Delete </a></td>');
  		var trStr="<tr id='"+pId+"'>"+tr.join("")+"</tr>";
  		$('#'+_key+"submit_form").find("#ruleDetailInfoTB").append(trStr);
    }
    
//    $('#'+_key+"submit_form").find('#ruleDetailInfoTB').on('click', 'td .btn-del', function(e) {
//        e.preventDefault();
//        if (ruleDetailCurrIndex >0) {
//        	ruleDetailCurrIndex--;
//            $tbody = $(this).closest('tbody');
//            $(this).closest('tr').remove();
//            $tbody.find('tr>td:first-child').each(function(i) {
//                $(this).html(i + 1);
//            })
//        }
//        if (ruleDetailCurrIndex ==0) {
//        	$('#'+_key+"submit_form").find("#ruleDetailInfoTB").append('<tr> <td colspan="3">None</td> </tr>');
//        }
//    })
 }
