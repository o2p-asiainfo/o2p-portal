var PardOfferSettlement = function() {
	
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
	
    return {
        init: function(id) {
        	addTabMenu_(id);
        	
        }
    };
}();

var dataURL = function(busiCode,servCode,cepCode,formNum,obj){
	return APP_PATH+"/pardOfferSettlement/toRule.shtml?actionType=add&"
	      +"settleCycleDef.busiCode="+busiCode+"&settleSpBusiDef.servCode="+servCode
	      +"&ruleType="+cepCode+"&formNum="+formNum+"&defaultName="+$(obj).closest("input[id=settleSpBusiDef.busiName]").val();
}
var onSettlementSave=function(_key){
	$.ajax({
		async : false,
		type : "POST",
		url :  APP_PATH+"/pardOfferSettlement/saveSettle.shtml",
		data : $('#'+_key+'submit_form').serialize(),
		beforeSend:function(){
			
		},success : function(data) {
			if (data.code == "0000") {
				toastr.success('Save Success');
				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
					$('#'+_key+'submit_form input[id="actionType"]').val("update");
					$('#'+_key+'submit_form input[id="settleCycleDef.cycleDefId"]').val(data.desc.cycleDefId);
					$('#'+_key+'submit_form input[id="settleSpBusiDef.busiId"]').val(data.desc.busiId);
					$('#'+_key+'submit_form input[id="settleCycleDef.syncFlag"]').val(data.desc.settleCycleDefSyncFlag);
					$('#'+_key+'submit_form input[id="settleSpBusiDef.syncFlag"]').val(data.desc.settleSpBusiDefSyncFlag);
				}
				$('#'+_key+'portlet').show();
			} else {
				toastr.error(data.desc);
			}
		},
		dataType : "json",
        error: function(xhr, textStatus, errorThrown) {
        	toastr.error("Save Fail");
        }
	});
}
var onSettlementRcSave=function(_key){
	var len = $('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr").length;
	var val =$('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr").eq(len-1).find("input[id=upInputValue]").val();
	if(val!="-1"){
		toastr.error(_endWith1);
		$('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr").eq(len-1).find("input[id=upInputValue]").focus();
		return;
	}
	for(var i=0;i<len;i++){
		if((i!=len-1) && parseInt($('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+i+")").find("input[id=downInputValue]").val())>=parseInt($('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+i+")").find("input[id=upInputValue]").val())){
			toastr.error("Row"+(i+1)+":"+_greater);
			$('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+i+")").find("input[id=upInputValue]").focus();
			return;
		}
		if( i+1<len && $('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+i+")").find("input[id=upInputValue]").val() != $('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+(i+1)+")").find("input[id=downInputValue]").val()){
			toastr.error(_equal1+(i+2)+" "+_equal2+(i+1));
			$('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr:eq("+(i+1)+")").find("input[id=downInputValue]").focus();
			return;
		}
		if($('#'+_key+'submit_form').find("table[id=recurrinngRuleTab]>tbody>tr").eq(i).find("input[id=baseRcValue]").val()==""){
			toastr.error(_notNullBase);
			return;
		}
	}
	
	var upValue=[];
	var downValue=[];
	var ratioNumerator=[];
	var baseValue=[];
	var ruleConditionId=[];
	$('#'+_key+'submit_form').find(".table.table-bordered.table-condensed.table-advance.table-function.table-rc").find("tbody>tr").each(function(i,obj){
		var _input=$(this).find("input");
		downValue.push(_input.eq(0).val());
		upValue.push(_input.eq(1).val());
		ratioNumerator.push(_input.eq(2).val());
		baseValue.push(_input.eq(3).val());
		ruleConditionId.push(_input.eq(4).val());
	})
	$('#'+_key+'submit_form input[id="upValue"]').val(upValue.join(";"));
	$('#'+_key+'submit_form input[id="downValue"]').val(downValue.join(";"));
	$('#'+_key+'submit_form input[id="ratioNumerator"]').val(ratioNumerator.join(";"));
	$('#'+_key+'submit_form input[id="baseValue"]').val(baseValue.join(";"));
	$('#'+_key+'submit_form input[id="ruleConditionId"]').val(ruleConditionId.join(";"));
	$('#'+_key+'submit_form input[id="operatorForSettlement"]').val($("#operatorValue").val());
	var o2pCloudFlag=$("#o2pCloudFlag").val();
	var operatorOrgId=$("#operatorOrgId").val();
	var operatorValue=$("#operatorValue").val();
	if(o2pCloudFlag=='cloud'){
		if(operatorOrgId==''&&operatorValue==''){
			toastr.error("please select an operator!");
	   		return ;
		}else{
			saveRc(_key);
		}
	}else{
		saveRc(_key);
	}
	
}
var onSettlementOtcSave=function(_key){
	$('#'+_key+'submit_form input[id="operatorForSettlement"]').val($("#operatorValue").val());
	
	var o2pCloudFlag=$("#o2pCloudFlag").val();
	var operatorOrgId=$("#operatorOrgId").val();
	var operatorValue=$("#operatorValue").val();
	if(o2pCloudFlag=='cloud'){
		if(operatorOrgId==''&&operatorValue==''){
			toastr.error("please select an operator!");
	   		return ;
		}else{
			saveOtc(_key);
		}
	}else{
		saveOtc(_key);
	}
}
var onSettlementAggregationSave=function(_key){
	var len = $('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr").length;
	var val =$('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr").eq(len-1).find("input[id=upValue]").val();
	if(val!="-1"){
		toastr.error(_endWith1);
		$('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr").eq(len-1).find("input[id=upValue]").focus();
		return;
	}
	for(var i=0;i<len;i++){
		if((i!=len-1) && parseInt($('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=downValue]").val())>=parseInt($('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=upValue]").val())){
			toastr.error("Row"+(i+1)+":"+_greater);
			$('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=upValue]").focus();
			return;
		}
		if( i+1<len && $('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=upValue]").val() != $('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+(i+1)+")").find("input[id=downValue]").val()){
			toastr.error(_equal1+(i+2)+" "+_equal2+(i+1));
			$('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+(i+1)+")").find("input[id=downValue]").focus();
			return;
		}
		if($('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr").eq(i).find("input[id=baseValue]").val()==""){
			toastr.error(_notNullBase);
			return;
		}
		var n = $('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=ratioNumerator]").val();
		var d = $('#'+_key+'submit_form').find("table[id=aggregationTab]>tbody>tr:eq("+i+")").find("input[id=ratioDemominator]").val();
		if(n==""){
			toastr.error(_nNotNull);
			return;
		}
		if(d==""){
			toastr.error(_dNotNull);
			return;
		}
		if(parseInt(n)>parseInt(d)){
			toastr.error(_nLessThand);
			return;
		}if(d=="0"){
			toastr.error(_endWith0d);
			return;
		}
	}
	
	var upValue=[];
	var downValue=[];
	var ratioNumerator=[];
	var ratioDemominator=[];
	var baseValue=[];
	var ruleConditionId=[];
	$('#'+_key+'submit_form').find(".table.table-bordered.table-condensed.table-advance.table-function.table-agg").find("tbody>tr").each(function(i,obj){
		var _input=$(this).find("input");
		downValue.push(_input.eq(0).val());
		upValue.push(_input.eq(1).val());
		ratioNumerator.push(_input.eq(2).val());
		ratioDemominator.push(_input.eq(3).val());
		baseValue.push(_input.eq(4).val());
		ruleConditionId.push(_input.eq(5).val());
	})
	$('#'+_key+'submit_form input[name="upValue"]').val(upValue.join(";"));
	$('#'+_key+'submit_form input[name="downValue"]').val(downValue.join(";"));
	$('#'+_key+'submit_form input[name="ratioNumerator"]').val(ratioNumerator.join(";"));
	$('#'+_key+'submit_form input[name="ratioDemominator"]').val(ratioDemominator.join(";"));
	$('#'+_key+'submit_form input[name="baseValue"]').val(baseValue.join(";"));
	$('#'+_key+'submit_form input[id="ruleConditionId"]').val(ruleConditionId.join(";"));
	$('#'+_key+'submit_form input[id="operatorForSettlement"]').val($("#operatorValue").val());
	
	var o2pCloudFlag=$("#o2pCloudFlag").val();
	var operatorOrgId=$("#operatorOrgId").val();
	var operatorValue=$("#operatorValue").val();
	if(o2pCloudFlag=='cloud'){
		if(operatorOrgId==''&&operatorValue==''){
			toastr.error("please select an operator!");
	   		return ;
		}else{
			saveAggRule(_key);
		}
	}else{
		saveAggRule(_key);
	}
}

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
            }],
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
        jQuery('#'+_key+'submit_form').find('#ruleDetailInfoDt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#'+_key+'submit_form').find('#ruleDetailInfoDt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
    
    });
    
  //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
	$('#'+_key+'submit_form').find('.okRuleDetailInfoModal').bind('click', function() {
		var $ruleDetailInfoModal = $(this).closest("#ruleDetailInfoModal");
		
		$ruleDetailInfoModal.modal('hide');
		
		$ruleDetailInfoModal.find("tbody input[type='checkbox']:checked").each(function(i, objCk) {
			var objTr=ruleDetailInfoDt.fnGetData(ruleDetailInfoDt.$('tr.active').get(i));
			if(ruleDetailCurrIndex==0){
		  		$('#'+_key+"submit_form").find("#ruleDetailInfoTB tr").eq(0).remove();
	  				addTr(objTr,objCk.id);
  	  		}else{
  	  			//查重
  	  			var _pass=true;
  	  			$('#'+_key+"submit_form").find('#ruleDetailInfoTB tr').each(function(j,objPTr) {
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
	//取消事件
	$('#'+_key+'submit_form').find('.cancelRuleDetailInfoModal').bind('click', function() {
		$(this).closest("#ruleDetailInfoModal").modal('hide');
	});
	
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
    
    $('#'+_key+"submit_form").find('#ruleDetailInfoTB').on('click', 'td .btn-del', function(e) {
        e.preventDefault();
        if (ruleDetailCurrIndex >0) {
        	ruleDetailCurrIndex--;
            $tbody = $(this).closest('tbody');
            $(this).closest('tr').remove();
            $tbody.find('tr>td:first-child').each(function(i) {
                $(this).html(i + 1);
            })
        }
        if (ruleDetailCurrIndex ==0) {
        	$('#'+_key+"submit_form").find("#ruleDetailInfoTB").append('<tr> <td colspan="3">None</td> </tr>');
        }
    })
 }



var saveRc=function(_key){
	$.ajax({
		async : false,
		type : "POST",
		url :  APP_PATH+"/pardOfferSettlement/saveRC.shtml",
		data : $('#'+_key+'submit_form').serialize(),
		beforeSend:function(){
			
		},success : function(data) {
			if (data.code == "0000") {
				toastr.success('Save Success');
				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
					$('#'+_key+'submit_form input[id="actionType"]').val("update");
					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
				}
				$("#operator").attr("disabled","disabled");
				$("#operatorDropDown").hide();
			} else {
				toastr.error(data.desc);
			}
		},
		dataType : "json",
        error: function(xhr, textStatus, errorThrown) {
        	toastr.error("Save Fail");
        }
	});
}


var saveOtc=function(_key){
	$.ajax({
		async : false,
		type : "POST",
		url :  APP_PATH+"/pardOfferSettlement/saveOTC.shtml",
		data : $('#'+_key+'submit_form').serialize(),
		beforeSend:function(){
		
		},success : function(data) {
			if (data.code == "0000") {
				toastr.success('Save Success');
				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
					$('#'+_key+'submit_form input[id="actionType"]').val("update");
					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
				}
				$("#operator").attr("disabled","disabled");
				$("#operatorDropDown").hide();
			} else {
				toastr.error(data.desc);
			}
		},
		dataType : "json",
        error: function(xhr, textStatus, errorThrown) {
        	toastr.error("Save Fail");
        }
	});
}


var saveAggRule=function(_key){
	$.ajax({
		async : false,
		type : "POST",
		url :  APP_PATH+"/pardOfferSettlement/saveAggregation.shtml",
		data : $('#'+_key+'submit_form').serialize(),
		beforeSend:function(){
		
		},
		success : function(data) {
			if (data.code == "0000") {
				toastr.success('Save Success');
				if($('#'+_key+'submit_form input[id="actionType"]').val()=='add'){
					$('#'+_key+'submit_form input[id="actionType"]').val("update");
					$('#'+_key+'submit_form input[id="settleRule.ruleId"]').val(data.desc.ruleId);
					$('#'+_key+'submit_form input[id="settleRuleRC.ruleDetailId"]').val(data.desc.ruleDetailId);
					$('#'+_key+'submit_form input[id="settleRule.syncFlag"]').val(data.desc.syncFlag);
				}
				$("#operator").attr("disabled","disabled");
				$("#operatorDropDown").hide();
			} else {
				toastr.error(data.desc);
			}
		},
		dataType : "json",
        error: function(xhr, textStatus, errorThrown) {
        	toastr.error("Save Fail");
        }
	});
}