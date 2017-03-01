var PardSerivceSpecList = function() {
    var PardSerivceSpecListFun = function() {
    	$.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/pardServiceSpec/list.shtml",
			data : {
				serviceSpecName : $("#serviceSpecName").val()
			},
			complete: function(xhr, textStatus) {                
                var online = $('.category_1').size();
                var pending = $('.category_2').size();
                var offline = $('.category_4').size();
                var all = online + pending + offline;   
                
                $("#myApp").html(all); 
                $('#spanAll').remove();
                $('#spanOnline').remove();
                $('#spanPending').remove();
                $('#spanOffline').remove();
                $('li[data-filter="all"]').append('<sapn id="spanAll">(' + all + ')</span>');
                $('li[data-filter=".category_1"]').append('<sapn id="spanOnline">(' + online + ')</span>');
                $('li[data-filter=".category_2"]').append('<sapn id="spanPending">(' + pending + ')</span>');
                $('li[data-filter=".category_4"]').append('<sapn id="spanOffline">(' + offline + ')</span>');
            },
			success : function(data) {
				if (data.code == "0000") {
					$('.mix-grid').html(data.desc).mixItUp({
	                    callbacks: {
	                        onMixEnd: function(state) {
	                            if (state.fail == true) {
	                                $('.s-warning').show();
	                            } else {
	                                $('.s-warning').hide();
	                            }
	                        }
	                    }
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
    }
    return {
        init: function() {
        	PardSerivceSpecListFun();
            $(".input-group-btn").click(PardSerivceSpecListFun);
        }
    };
}();

var showServiceSpecDetail=function(serviceSpecId){
	$("#serviceSpecId").val(serviceSpecId);
	document.serviceSpecForm.action = APP_PATH+"/pardServiceSpec/serviceSpecView.shtml";
	document.serviceSpecForm.submit();
}
