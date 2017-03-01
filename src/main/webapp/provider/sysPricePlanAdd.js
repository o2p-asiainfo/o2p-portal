var PricePlanAdd = function() {

    var handleSelectZoneModal = function() {}
    	
	 var handleJstree = function(selector) {
		 //alert('pps');
		 var prodOfferId = $("#hidProdOfferId").val();
		 $.ajax({
				async : false,
				type : "POST",
				url :  APP_PATH+"/pardOffer/getPardOffertProdTree.shtml",
				data : {
					prodIds:prodOfferId,
					idvalue:$("#relIds").val()
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
							 var prodOfferId = $("#hidProdOfferId").val();
							 prodOfferId = "A"+prodOfferId;
							 var checkTextAry=[];
							 var checkIdAry=[];
							 for(i = 0, j = data.selected.length; i < j; i++) {
								 if(data.instance.get_node(data.selected[i]).id != prodOfferId){
									 checkTextAry.push(data.instance.get_node(data.selected[i]).text);
									 checkIdAry.push(data.instance.get_node(data.selected[i]).id);
								 }
							 }
							 $("#jstreeTextString").val(checkTextAry.join(","));
							 $("#relIds").val(checkIdAry.join(","));
							 
						  });
					} else {
						alert(data.desc);
					}
				},
				dataType : "json",
	            error: function(xhr, textStatus, errorThrown) {
	            }
			});
	    }

	 var handlePOJstree = function(selector,formName) {
		 //alert(1);
		 var thisForm = $(selector).closest("form[id="+formName+"]");
		 var baseItemTypeObj = $(thisForm).find("select[id=baseItemType]");
	     var baseItemType = baseItemTypeObj.val();
		 var itemIdObj = $(thisForm).find("input[id=itemId]");
		 var itemIds =itemIdObj.val();	
		 var itemNamesObj = $(thisForm).find("input[id=itemNames]");
		 var itemIdObj = $(thisForm).find("input[id=itemId]");
		 $(thisForm).find("div[id=poJstreeDiv]").hide();
		 $(thisForm).find("div[id=loadingImg_tree]").show();
		 itemNamesObj.val('');
		 itemIdObj.val('');
		 $.ajax({
				async : true,
				type : "POST",
				url :  APP_PATH+"/pardOfferPricePlan/getPricingSubjectTree.shtml",
				data : {
					baseItemType:baseItemType,
					itemIds:itemIds
				},
				success : function(data) {
					 $(thisForm).find("div[id=poJstreeDiv]").show();
					 $(thisForm).find("div[id=loadingImg_tree]").hide();
					if (data.code == "0000") {
						 var tree = $(selector).jstree({
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
							 itemNamesObj.val(checkTextAry.join(","));
							 itemIdObj.val(checkIdAry.join(","));
						  });
					} else {
						alert(data.desc);
					}
				},
				dataType : "json",
	            error: function(xhr, textStatus, errorThrown) {
	            }
			});
	    }
	 

	    var handleRatingUnitType = function(selector) {
	    	//alert(baseItemTypeText);
			 var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
			 var baseItemTypeText = $(baseItemTypeObj).find("option:selected").text();
			 var ratingUnitTypeObj = $(selector).find("select[id=ratingUnitType]");
			 $(selector).find("select[id=ratingUnitType]").hide();
			 $(selector).find("div[id=loadingImg_unit]").show();
			 $.ajax({
					async : true,
					type : "POST",
					url :  APP_PATH+"/pardOfferPricePlan/showRatingUnitTypeList.shtml",
					data : {
						baseItemType:baseItemTypeText
					},
					success : function(data) {
						$(selector).find("div[id=loadingImg_unit]").hide();
						 $(selector).find("select[id=ratingUnitType]").show();
						if (data.code == "0000") {
							ratingUnitTypeObj.find("option").remove();
							ratingUnitTypeObj.append("<option selected value='"+109+"'>"+"Piece"+"</option>");
							 $(selector + ' .unitArea').html($(ratingUnitTypeObj).find('option:selected').text());
							// rat
							//							 var selList = data.desc
//							 if(selList!=null && selList.length>0){
//								 for(var i=0; i<selList.length; i++){
//									 var _key    = selList[i].key;
//									 var _value = selList[i].value;
//									 ratingUnitTypeObj.append("<option value='"+_key+"'>"+_value+"</option>"); 
//								 }
//							 }
						} else {
							alert(data.desc);
						}
					},
					dataType : "json"
			});
	    }
	
	 
    //控制offset tyep 选择为offset时 显示输入框
    var handleOffsetType = function(selector) { 
        var $offsetType = $('#modal4').find('.offsetType');
        $(selector).on('change', '.offsetType', function() {
            if ($offsetType.val() == 1) {
                $('.offset').show();
            } else {
                $('.offset').hide();
            }
        })
        if ($offsetType.val() == 1) {
            $('.offset').show();
        } else{
            $('.offset').hide();
        }
    }
    
    //控制 EFFECTIVE TIME 当单位选择为fixed expiry date时 input变为日期控件
    var handleEffectiveTime = function(selector) {
        var $unit = $(selector).find('.unit');
        var $effectiveTime1 = $(selector).find('.effectiveTime1');
        var $effectiveTime2 = $(selector).find('.effectiveTime2');

        $(selector).on('change', '.unit', function() {
            if ($unit.val() == 11) {
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
    

    

    var handleSelectTimeRangeModal = function() {
    	var timeRangeDT = $('#timeRangeDT').dataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "ajax": "../pardOfferPricePlan/getTimeRangeList.shtml",
            "order": [],
            "autoWidth": false,
            "columns": [
                        {"data": "INDEX"},
                        {"data": "SEG_NAME"},
                        {"data": "DATE_MODE"},
                        {"data": "TIME_MODE"},
                        {"data": "DESCRIPTION"}
                    ],
              "columnDefs": [{
                  "orderable": false,
                  "targets":  [0,1,2,3,4],
                  "width": "25px"
              },{
                  "visible": true,
                  "targets": [0,1,2,3,4]
              }],
            "initComplete": function() {
                App.ajaxInit();
                $('#timeRangeDT tbody').find('input[type="checkbox"]:checked').each(function() {
                    $(this).closest('tr').addClass('active');
                })
            }
        });
        jQuery('#timeRangeDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#timeRangeDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
   
         //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $('.timeOK').bind('click', function() {
        	$("#segId").val("1234");																	//////////////////////////?
        	$("#segName").val("Weekend night 19~22");						//////////////////////////?
        	$('#selectTimeRangeModal').modal('hide');
        });
        
        $(".timeCancel").click(function(){
        	$('#selectTimeRangeModal').find(".close").click();
        });
        
        $("#timeModal").click(function(){
        	$("#selectTimeRangeModal").modal('show');
        });
        
        $("#timeQuery").click(function(){
            var url = "../pardOfferPricePlan/getTimeRangeList.shtml?idOrName="+$("#idOrName").val()+"&dateMode="+$("#dateMode").val() + "&timeMode="+$("#timeMode").val();
            timeRangeDT.api().ajax.url(url).load();
        });
    }
    
    var setDefPriceNameValue = function(selector, pSelector){
    	var pricingName = $(pSelector).find("input[id=pricingName]").val();
    	var defVal = pricingName+ "_"+_priceType1+"_111";
    	$(selector).find("input[id=priceName]").val(defVal);
    }
    
    /////////////////////////////////  
    var addTabMenu = function(selector) {

        var tpl = '<ul class="nav nav-tabs fix tabs-content-ajax"></ul> <div class="tab-content"></div>';

        $(selector).on('click', 'a[data-action="addTabMenu"]', function() {
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
                var container = $this.data('container');
                var $container = $(selector + ' ' + container);
                //有设置改属性的，表明只能添加一次
                if ($this.attr('data-unique') == 'true' && draw == '1') {
                    toastr.error("This property has already been added.");		///这个属性已经添加过了
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
                        if (type == 0) {
                            //针对basic tariff           
                           // handlePOJstree(id + ' .poShu', "basicTariffForm");
                            handleChargeTable(id);
                            handleChargeTableAdd(id);
                            handleDatePickers(id + ' .date-picker');
                            handleRatingUnitType(id);
                            //basicTariff_InitDefValue(id);
                        }else if(type==8){
                            //针对Rating Discount    
                        	handleSelectTimeRangeModal(id);
                        	ratingDiscount_InitDefValue(id);
                            handlePOJstree(id + ' .poShu',"ratingDiscountForm");
                            handleDatePickers(id + ' .date-picker');
                        }
                        setDefPriceNameValue(id, selector);
                    	$('.dropdown-toggle2').dropdownHover();
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
            var total = $this.closest('.portlet').find('.nav-tabs input').size();

            if (total == selected && selected != 0) {
                $this.closest('.portlet').find('.portlet-body').html('<div class="well">您还未填写Charge Information,请点击右上方的添加按钮</div>');
                $this.closest('.dropdown').find('.dropdown-menu li a').each(function() {
                    $(this).attr('data-draw', '0');
                })
                $this.closest('.portlet').children('.portlet-body').attr('data-addtpl', 'false');
            } else if (selected != 0) {
                $selected.each(function() {
                    var $$this = $(this);
                    var target = $$this.val();
                    var length = target.length;
                    var index = target.charAt(length - 3);
                    var $a = $this.closest('.dropdown').find('.dropdown-menu li').eq(index).children('a');
                    $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
                    $(target).remove();
                    $$this.closest('li').remove();
                });
                $this.closest('.portlet').find('.nav-tabs li:first a').click();
            }
        });
    }

    var basicTariff_InitDefValue = function(selector) {
		var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
        baseItemTypeObj.change(function(){	
			 $(selector + ' .poShu').jstree('destroy');	//先清空树
			 handlePOJstree(selector + ' .poShu', 'basicTariffForm');	//再重新加载树
			// alert(1);
			 handleRatingUnitType(selector);				
       });
    }
    
    var ratingDiscount_InitDefValue = function(selector) {
		var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
        baseItemTypeObj.change(function(){
			 $(selector + ' .poShu').jstree('destroy');	//先清空树
			 handlePOJstree(selector + ' .poShu', 'ratingDiscountForm');	//再重新加载树
			 //alert(2);
			 handleRatingUnitType(selector);				
        });
		var discountTypeObj = $(selector).find("select[id=discountType]");
		discountTypeObj.change(function(){
				if($(this).val()=="0"){
					$("#fixedTab").hide();
					$("#percentyTab").show();
					$("#feeTr").show();
				}else if($(this).val()=="1"){
					$("#fixedTab").show();
					$("#percentyTab").hide();
					$("#feeTr").show();
				}else if($(this).val()=="2"){
					$("#fixedTab").hide();
					$("#percentyTab").hide();
					$("#feeTr").hide();
				}
       });
		if(discountTypeObj.val()=="0"){
			$("#fixedTab").hide();
			$("#percentyTab").show();
			$("#feeTr").show();
		}else if(discountTypeObj.val()=="1"){
			$("#fixedTab").show();
			$("#percentyTab").hide();
			$("#feeTr").show();
		}else if(discountTypeObj.val()=="2"){
			$("#fixedTab").hide();
			$("#percentyTab").hide();
			$("#feeTr").hide();
		}
    }
    
    //控制 Charge Information中basic tariff面板内的charge表格的联动效果
    var handleChargeTable = function(selector) {
        $(selector).on('change', '#ratingUnitType', function() {
            var unit = $(this).find('option:selected').text();
            
            $(selector + ' .unitArea').html(unit);
        })
        $(selector + ' input[name="chargingUnitValue"]').focusout(function() {
            var chargingValue = $(this).val();
            $(selector + ' .numArea').html(chargingValue);
        })
        $(selector).on('click', 'input[name="rateType"]', function() {
            var chargeType = $(this).val();
            if (chargeType == 1) {
                $(selector + ' .simple').show();
                $(selector + ' .ladder').hide();
				inputV = "1";
            } else if (chargeType == 2) {
                $(selector + ' .simple').hide();
                $(selector + ' .ladder').show();
				inputV = "2";
            }
        })
        $(selector + ' input[name="ratingUnitVal"]').focusout(function() {
		            var chargingValue = $(this).val();
		            $(selector + ' .numArea').html(chargingValue);
		 })
        
        $(selector).on('change', '#currencyUnitType', function() { 
            var currency = $(this).find('option:selected').text();
            $('.currencyArea').html(currency);
        }) 
        //初始化 
        //console.log($(selector + ' .currency'));
        $(selector + ' .unitArea').html($(selector + ' .chargingUnit').find('option:selected').text());
        $(selector + ' .currencyArea').html($(selector + ' .currency').find('option:selected').text());
        $(selector + ' .numArea').html($(selector + ' input[name="ratingUnitVal"]').val() ? $(selector + ' input[name="ratingUnitVal"]').val() : 1);
        var chargeType = $(selector + ' input[name="rateType"]:checked').val();
        if (chargeType == 1) {
            $(selector + ' .simple').show();
            $(selector + ' .ladder').hide();
        } else if (chargeType == 2) {
            $(selector + ' .simple').hide();
            $(selector + ' .ladder').show();
        }
    }
    

    var handleChargeTableAdd = function(selector) {
		var tableObj = $(selector).find("table[id=ladderFeeTab]");
		$(selector).find(".addBut").bind('click', function() {
			var len = $(selector).find("table[id=ladderFeeTab]>tbody>tr").length;
			var val = $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len-1).find("input[id=endVal]").val();
			if(val=="-1"||val==""){
				toastr.error(_endVal);
				return;
			}
			var num1 = parseInt($(selector).find("table[id=ladderFeeTab]>tbody>tr:eq("+(len-1)+")>td:eq(0)>input:eq(0)").val());
			var num2 = parseInt($(selector).find("table[id=ladderFeeTab]>tbody>tr:eq("+(len-1)+")>td:eq(0)>input:eq(1)").val());
			if(num1>=num2){
				toastr.error(_greater);
				return;
			}
			var val = $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len-1).find("input[id=endVal]").val();
			if(val==undefined){
				val = 0;
			}
			newTr = "<tr><td>"+len+"</td>" 
				+"<td>" 
				+"	 <div class='input-group input-xs'>" 
				+"		  <input type='text' value='"+val+"'  id='startVal' class='form-control' onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"/>" 
				+"		  <span class='input-group-addon'> To </span>" 
				+"		  <input type='text' value='-1'  id='endVal' class='form-control'  onkeyup=\"this.value=this.value.match('^-?[0-9]*$')\" onafterpaste=\"this.value=this.value.match('^-?[0-9]*$')\">" 
				+" 	</div>" 
				+" </td>" 
				+" <td>" 
				+"  		<input type='text' value='0' id='rateVal' placeholder='' class='form-control input-xs' onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\" >" 
				+" </td>" 
				+" <td>" 
				+"	<input type='text'  value='0' id='baseVal' placeholder='' class='form-control input-xs'  onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\">" 
				+" </td>" 
				+"<td>" 
				+"	<a class='btn default btn-sm black btn-del' href='#' onclick=\"$(this).closest('tr').remove()\"><i class='fa fa-trash-o' ></i>Delete </a>" 
			  	+"</td>" 
			  	+"</tr>";
			tableObj.append(newTr);   
       });
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
	 
	 var showZoneMap = function() {
		    var xsize,
		        ysize,
		        href,
		        $modal,
		        img,
		        realWidth,
		        realHeight,
		        $this = $('.showZone'),
		        size = $this.size();
		    xsize = $this.attr('data-width')+'px';
		    ysize = $this.attr('data-height')+'px';
		    href = $this.attr('data-href');
		    //点击查询消息
		    $('.showZone').find('.fa-plus').bind('click', function() {
		        $modal =  $('#selectZoneModal2');
		        $modal.css({'width':xsize,'height':ysize});
		        $modal.load(href, '', function() {
		            $modal.modal('show');
		        });
		    });		    
		}


	 var initDefValue = function(selector){
	 	var prodOfferName = $("#prodOfferName").val();
	 	var pricingNameObj = $(selector).find("input[id=pricingName]");
	 	if(pricingNameObj.val() ==""){
			 var tabNum = selector.substring(selector.indexOf("_")+1,selector.length);
	 		prodOfferName += "_"+_pricePlan+"_" + tabNum;
	 		pricingNameObj.val(prodOfferName);
	 	}
	 }
	 
	return {
        init: function(id,num,priceType) {
        	//是否展示chargeInformation 如果已有priceplanId就展示
        	var priceflag = $('#pricingInfoId').val(); 
        	if(priceflag==null||priceflag==""){ 
        		$('.portlet-title').hide();
        	}
        	
            initDefValue(id);
            handleJstree('#jstreemjf');
//            addTabMenu(id);
//            handleJstree(id + ' .ppJstree');
            if(num==0){
            addTabMenu(id);
            }
            handleOffsetType(id) //控制offset type显示和隐藏
            handleEffectiveTime(id);
            handleDatePickers(id + ' .date-picker'); 
            App.ajaxInit(); 
        	if (priceType == 0) {
                //basic tariff :
               // handlePOJstree(id + ' .poShu', "basicTariffForm");
                handleChargeTable(id);
                handleChargeTableAdd(id);
                
                handleDatePickers(id + ' .date-picker');
                handleRatingUnitType(id);
                
                basicTariff_InitDefValue(id);
            }else if(priceType==7){
                //Recurring charge :
            }else if(priceType==5){
                //Rating Discount :
            	handleSelectTimeRangeModal(id);
            	ratingDiscount_InitDefValue(id);
                handlePOJstree(id + ' .poShu',"ratingDiscountForm");
                handleDatePickers(id + ' .date-picker');
            }else if(priceType==9){
                //One-time charge :
            }else if(priceType==8){
                //Billing discount :
            }
           // setDefValue(id, selector,draw);
        	$('.dropdown-toggle2').dropdownHover();
            App.ajaxInit();
        }
	}

}();
    



//Save Price Plan Base Information:
function savePricePlanBaseInfo(){
    if($("#vaildType").val()=="11"){
    	$("#validUnit").val('');
    }else{
    	$("#etime").val('');
    }
	
	var pricePlanSpecIdList =	$('[id=pricePlanSpecId]');
	var defaultValList 			=	$('[id=defaultVal]');
	var defaultValNameList =	$('[id=defaultValName]');
	if(pricePlanSpecIdList !=null  &&pricePlanSpecIdList !=undefined && pricePlanSpecIdList.length>0){
		var ids="";
		var vals="";
		var names="";
		var j=0;
		for(var i=0; i<pricePlanSpecIdList.length; i++){
			var obj = pricePlanSpecIdList[i];
			var _val ="";
			var _name="";
			if($(obj).prop("checked")){
				if(ids==""){
					ids += $(obj).val();
				}else{
					ids += ","+$(obj).val();
				}

				var vObj = defaultValList[i];
				if($(vObj) !=null  && $(vObj) !=undefined){
					_val = $(vObj).val();
				}
				if(j==0){
					vals += _val
				}else{
					vals += ","+_val
				}

				var nObj = defaultValNameList[i];
				if($(nObj) !=null  && $(nObj) !=undefined){
					_name = $(nObj).val()+" ";
				}
				if(j==0){
					names += _name
				}else{
					names += ","+_name
				}
				j++;
			}
		}
		$("#pricePlanSpecIds").val(ids);
		$("#defaultVals").val(vals);
		$("#defaultValNames").val(names);
		
	}
	
	//pricingTarget:
	var prodOfferId = $("#hidProdOfferId").val();
	var pricingTargetId = $("#pricingTargetId").val();
	 
	//pricingPlan:
	var pricingInfoId = $("#pricingInfoId").val();
	var applicType = $("#applicType").val();
	var pricingName = $("#pricingName").val();
	var billingPriority = $("#billingPriority").val();
	
	//pricePlanLifeCycle:
	var subEffectMode = $("#subEffectMode").val();
	var subDelayUnit = $("#subDelayUnit").val();
	var subDelayCycle = $("#subDelayCycle").val();
	var validUnit = $("#validUnit").val();
	var vaildType = $("#vaildType").val();
	var fixedExpireDate = $("#fixedExpireDate").val();
	
	//var pricingTargetId =$("#pricingTargetId").val()
	//Price Plan Attribute:
	var pricePlanSpecIds = $("#pricePlanSpecIds").val();
	var defaultVals = $("#defaultVals").val();
	var defaultValNames = $("#defaultValNames").val();
	//Price Object-Product:
	var relIds = $("#relIds").val();
	var actionType=$('#actionType').val();
	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/provider/savrOrUpdatePricePlan.shtml",
	    dataType:'json',
	    //data:$("#pricePlanForm").serialize(),
	    data:{
	    	prodOfferId:prodOfferId,
	    	pricingTargetId:pricingTargetId,
	    	pricingInfoId:pricingInfoId,
	    	applicType:applicType,

	    	pricingName:pricingName,
	    	billingPriority:billingPriority,
	    	
	    	subEffectMode:subEffectMode,
	    	subDelayUnit:subDelayUnit,
	    	subDelayCycle:subDelayCycle,
	    	validUnit:validUnit,
	    	vaildType:vaildType,
	    	fixedExpireDate:fixedExpireDate,
	    	
	    	pricePlanSpecIds:pricePlanSpecIds,
	    	defaultVals:defaultVals,
	    	defaultValNames:defaultValNames,
	    	
	    	relIds:relIds,
	    	actionType:actionType
	    },
	    success:function(msg){
	    	//{"pricingTargetId":"100000051","priceInfoId":"100000080"}
	    	if(msg.priceInfoId!=null && msg.priceInfoId!="" && msg.pricingTargetId!=null && msg.pricingTargetId!=""){
				$("#pricingInfoId").val(msg.priceInfoId);
				$("#pricingTargetId").val(msg.pricingTargetId);
				$("#chargeInformation").show();
				//回写到页面
				var id = pricingInfoId;//closest('tr').children('td').eq(1).html()
				
				if(id!=null&&id!=''){
					
					toastr.success('Edit Success.');
					return;
				}
				var pricePlanTdval =  $('#'+prodOfferId).closest('tr').children('td').eq(3).html();
				var strvala = "<p id= 'li"+msg.priceInfoId+"' ><a href='javascript:;' class='editPricePlan' name ="+msg.pricingTargetId+" id="+msg.priceInfoId+"> "+pricingName+"</a>" +
				              "<a href='javascript:;' class='' name ='0minus0"+msg.pricingTargetId+"' id='0minus0"+msg.priceInfoId+"' ><i class='fa fa-times-circle delPricePlan'></i></a></p>";
				//<a href="javascript:;" class="editPricePlan" name ="${pricePlan.PRICINGTARGETID}" id="${pricePlan.PRICINGINFOID}"> ${pricePlan.PRICINGNAME }</a> 
			       
				$('#'+prodOfferId).closest('tr').children('td').eq(3).html(pricePlanTdval+strvala);
				
				$('.portlet-title').show();
				toastr.success('Save Success.');
				$("#chargeInformation").show();
	    	}else{
				toastr.error('Save Fail.');
	    	}
	    }
	});
//	}
}

//Save Basic Tariff:
var inputV = "1";
var frist = "1";
function saveOrUpdateBasicTariff(savebut){
	var thisForm = $(savebut).closest("form[id=basicTariffForm]");
	var begin = $(thisForm).find("input[id=formatEffDate]").val();
	var over = $(thisForm).find("input[id=formatExpDate]").val();
	var ass,aD,aS;
	var bss,bD,bS;
	ass=begin.split("-");
	aD=new Date(ass[0],ass[1]-1,ass[2]);
	aS=aD.getTime(); 
	bss=over.split("-");
	bD=new Date(bss[0],bss[1]-1,bss[2]);
	bS=bD.getTime();
	if(aS>bS){
		toastr.error(_prodDateStartEnd);
		//art.dialog.alert("<s:property value="%{getText('eaap.op2.portal.attrSpec.operationTips')}" />","<s:property value="%{getText('eaap.op2.portal.pardProd.prodDateStartEnd')}" />");
		return false;
	}  
	
	var detail = "";
	if(inputV=="1"){
		if(!($(thisForm).find("input[id=rateVal]").val()!="" && $(thisForm).find("input[id=baseVal]").val()!="")){
			toastr.error(_simpleNotNull);
			//art.dialog.alert("<s:property value="%{getText('eaap.op2.portal.attrSpec.operationTips')}" />","<s:property value="%{getText('eaap.op2.portal.price.basic.simpleNotNull')}" />");
			return;
		}
		detail = $(thisForm).find("input[id=rateVal]").val() + "," +$(thisForm).find("input[id=baseVal]").val();
	}else if(inputV=="2"){
		var len = $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").length;
		var val = $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(len-1).find("input[id=endVal]").val();
		if(val!="-1"){
			$(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(len-1).find("input[id=endVal]").focus();
			toastr.error(_endWith1);
			return;
		}
		var ladderFee = "";
		for(var i=0;i<len;i++){
			if((i!=len-1)&&parseInt($(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")>td:eq(0)>input:eq(0)").val())>=parseInt($(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+(i)+")>td:eq(0)>input:eq(1)").val())){
				$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")>td:eq(0)>input:eq(1)").focus();
				toastr.error(_greater);
				return;
			}
			if( i+1<len&&$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")>td:eq(0)>input:eq(1)").val()!=$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+(i+1)+")>td:eq(0)>input:eq(0)").val()){
				toastr.error(_equal1+(i+2)+_equal2+(i+1));
				$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+(i+1)+")>td:eq(0)>input:eq(0)").focus();
				return;
			}
			if($(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(i).find("input[id=baseVal]").val()==""){
				toastr.error(_notNullBase);
				return;
			}else{
				ladderFee += (i+1) +"," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")").find("#startVal").val() + "," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")").find("#endVal").val() +"," +$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")").find("#rateVal").val() +","+$(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq("+i+")").find("#baseVal").val()+ ";";
			}
			if($(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(i).find("input[id=rateVal]").val()==""){
				toastr.error(_notNullRateVal);
				return;
			}
		}
		detail = ladderFee.substring(0,ladderFee.length);
	}

	 var baseItemTypeObj = $(thisForm).find("select[id=baseItemType]");
	 var baseItemTypeText = $(baseItemTypeObj).find("option:selected").text();
	 var ratingUnitTypeObj = $(thisForm).find("select[id=ratingUnitType]");
	 var ratingUnitTypeText = $(ratingUnitTypeObj).find("option:selected").text();
	 var currencyUnitTypeObj = $(thisForm).find("select[id=currencyUnitType]");
	 var currencyUnitTypeText = $(currencyUnitTypeObj).find("option:selected").text();
	 
	var other = baseItemTypeText + "," + ratingUnitTypeText + "," + currencyUnitTypeText + "," + inputV;
	$(thisForm).find("#other").val(other);
	$(thisForm).find("#details").val(detail);
	//alert($('#pricingInfoId').val());
	$(thisForm).find("#priPricingInfoId").val($('#pricingInfoId').val());

	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/provider/saveOrUpdateBasicTariff.shtml",
	    dataType:'json',
	    data:thisForm.serialize(),
	    success:function(msg){
	    	//{"priceId":"100000010","code":"0000","desc":"Success"}
	    	if(msg.code!=null && msg.code=="0000" && msg.priceId!=null && msg.priceId!=""){
		    	$(thisForm).find("input[id=priceId]").val(msg.priceId);
		    	$(thisForm).find("input[id=actionType]").val("update");
				toastr.success('Save Success.');
	    	}else{
				toastr.error('Save Fail.');
	    	}
	    }
	});

}

//Save Recurring Charge:
function saveOrUpdateRecurringFee(){
	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/pardOfferPricePlan/saveOrUpdateRecurringFee.shtml",
	    dataType:'json',
	    data:$("#recurringChargeForm").serialize(),
	    success:function(msg){
	    	//alert(msg);
	    }
	});
}
//Save Rating Discount:
function saveOrUpdateRatingDiscount(){
	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/pardOfferPricePlan/saveOrUpdateRatingDiscount.shtml",
	    dataType:'json',
	    data:$("#ratingDiscountForm").serialize(),
	    success:function(msg){
	    	//alert(msg);
	    }
	});
}

//Save     One-Time Charge:
function saveOrUpdateOneTimeFee(){
	$.ajax({
		type: "POST",
		async:false,
	    url: APP_PATH+"/pardOfferPricePlan/saveOrUpdateOneTimeFee.shtml",
	    dataType:'json',
	    data:$("#ratingDiscountForm").serialize(),
	    success:function(msg){
	    	//alert(msg);
	    }
	});
}

//Save Billing Discount:
function saveOrUpdateBillingDiscount(){
	$.ajax({
		type: "POST",
		async:false,
	    url: APP_PATH+"/pardOfferPricePlan/saveOrUpdateBillingDiscount.shtml",
	    dataType:'json',
	    data:$("#ratingDiscountForm").serialize(),
	    success:function(msg){
	    	//alert(msg);
	    }
	});
}

function pricePlanFinish(){ 
	$('#modal4').modal('hide');
	// location.reload();
//	basicTariffForm.action=APP_PATH+"/provider/manageSystem.shtml?componentId="+$("#hidComponentId").val()+"&state="+$("#state").val();
//	basicTariffForm.submit();
    
	
}
var tabNums = 0;
var loadEx_tab2Chil=function(id,priceType,_url){
//	console.log(id);
//	console.log(priceType);
//	console.log(_url);
	var target=$(id);
	var isLoad=target.attr("isLoad");
	if(isLoad=='0'){
		target.load(_url+"&state="+$('#state').val(), function() {
			target.attr({"isLoad":"1"});
        	PricePlanAdd.init(id,0,priceType);
            $(id + ' ul[data-set="addTarget"]').find('a').each(function(index) {
                $(this).attr('data-target', id + '_' + index);
            })
	 	});
	}
}
