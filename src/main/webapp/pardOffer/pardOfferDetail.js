var PardOfferDetail = function() {
	var updateInfoFun=function(){
		showProdForm.action=APP_PATH+"/pardOffer/toUpdate.shtml";
		showProdForm.submit();
	}
	var delInfoFun=function(){
		bootbox.confirm("Are you sure?", function(result) {
			 if(result==true){
				 $.ajax({
					async : false,
					type : "POST",
					url :  APP_PATH+"/pardOffer/delete.shtml",
					data : {
						offerId:$("#prodOfferId").val()
					},
					beforeSend:function(){
						
					},success : function(data) {
						if (data.code == "0000") {
							toastr.success('Save Success');
							showProdForm.action=APP_PATH+"/pardOffer/toIndex.shtml";
							showProdForm.submit();
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
			 }
		});
	}
	var submitCheckFun=function(){
		$.ajax({
			type: "POST",
			async:true,
		    url: APP_PATH+"/pardOffer/checkProductOrOfferState.shtml",
		    dataType:'json',
		    data:$('#showProdForm').serialize(),
			success:function(msgJson){
				if(msgJson.code=='0000'){
					showProdForm.action=APP_PATH+"/pardOffer/submitCheck.shtml";
					showProdForm.submit();
				}else{
					toastr.error(msgJson.desc);
				}
			},
			error: function(xhr, textStatus, errorThrown) {
	        	toastr.error("System error!");
	        }
		});
	}
	var offShelfFun=function(){
		bootbox.confirm("Are you sure?", function(result) {
			 if(result){
				 showProdForm.action=APP_PATH+"/pardOffer/offShelf.shtml";
				 showProdForm.submit();
			 }
		});
	}
	var onShelfFun = function(){
		bootbox.confirm("Are you sure?", function(result) {
			 if(result){
				 showProdForm.action=APP_PATH+"/pardOffer/onShelf.shtml";
				 showProdForm.submit();
			 }
		});
	}
	
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
	            var datakey = $this.attr('data-key');
	            var container = $this.data('container');
	            var flag = $this.data('flag');
	            var $container = $(selector + ' ' + container);
	            //有设置改属性的，表明只能添加一次
	            if ($this.attr('data-unique') == 'true' && draw == '1') {
	                toastr.error("这个属性已经添加过了");
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
	                        }else   if (type == 2) {//otc
	                        	handleDatePickers(id + ' .date-picker');
	                        }else if(type==4){
	                        	handleDatePickers(id + ' .date-picker');
	                        	setTimeout(handleRuleDetailInfoModal(datakey),1000);
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

	    })

	}


    return {
        //main function to initiate the module
        init: function() {
            //编辑页面初始化显示第一页：
        	/*var tab3TabList = $(".tab3_TabUL").find("a");
        	if(tab3TabList.length>0){
        		tab3TabList[0].click();
        	}*/
        	$("#updateInfo").click(updateInfoFun);
        	$("#linkDel").click(delInfoFun);
        	$("#submitToCheck").click(submitCheckFun);
        	$("#offShelf").click(offShelfFun);
        	$("#onShelf").click(onShelfFun);
        	
        	//LOAD SETTLE
        	var target =null;
            var draw = $('#addSettlement').attr('data-draw');
            var _url=null;
            if(parseInt(draw)>0){ 
            	 for(var i=0;i<1;i++){
                 	target = $('#addSettlement').data('target') + '_' + i;
                 	_url=$('#tab3 .createArea2').children('.nav-tabs').find('li:eq(0):first a').attr("data-url");
                 	if(_url.indexOf("&formNum=")<0){
                 		_url += "&formNum="+i;
                 	}
                 	$(target).load(_url, function() { //加载完配置单后 执行的渲染动作
                 		addTabMenu(target);
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
             		        	_url1=_url1+"&formNum="+i+ '_' + j;
             		        	$(_target1).load(_url1, function() {
             		        		//addTabMenu(_target1);
             		        		handleDatePickers(_target1 + ' .date-picker');
                     		        App.ajaxInit();
             		        	});
         		        	}
         		        }

         		        //编辑页面初始化显示第一页：
         		    	var tab3Chils = $(target).find(".tab3Chil_TabUL").find("a");
         		    	if(tab3Chils.length>0){
         		    		tab3Chils[0].click();
         		    	}
         		    })
                 }
            }
			
            //编辑页面初始化显示第一页：
        	var tab2TabList = $(".tab2_TabUL").find("a");
        	if(tab2TabList.length>0){
        		tab2TabList[0].click();
        	}
        }
    };
}();
var loadEx=function(_target,_url){
	var target=$(_target);
	var isLoad=target.attr("isLoad");
	_url=encodeURI(_url);
	_url=encodeURI(_url);
	if(isLoad=='0'){
		target.load(_url, function() {
	 		//addTabMenu(_target1);
			target.attr({"isLoad":"1"});
	 		handleDatePickers(_target + ' .date-picker');
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