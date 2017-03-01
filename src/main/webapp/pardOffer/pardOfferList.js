var PardOfferList = function() {
    var pardOfferListFun = function() {
    	$.ajax({
			async : false,
			type : "POST",
			url :  APP_PATH+"/pardOffer/list.shtml",
			data : {
				prodOfferName : $("#prodOfferName").val()
			},
			complete: function(xhr, textStatus) {                
                var online = $('.category_1').size();
                var newProduct = $('.category_2').size();
                var prending = $('.category_3').size();
                var offline = $('.category_4').size();
                var all = online + prending + offline + newProduct;   
                
                $("#myApp").html(all); 
                $('#spanAll').remove();
                $('#spanOnline').remove();
                $('#spanPrending').remove();
                $('#spanOffline').remove();
                $('li[data-filter="all"]').append('<sapn id="spanAll">(' + all + ')</span>');
                $('li[data-filter=".category_1"]').append('<sapn id="spanOnline">(' + online + ')</span>');
                $('li[data-filter=".category_2"]').append('<sapn id="spanPrending">(' + prending + ')</span>');
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
					alert(data.desc);
				}
			},
			dataType : "json",
            error: function(xhr, textStatus, errorThrown) {
            	//called when there is an error
            }
		});
    }
    return {
        //main function to initiate the module
        init: function() {
        	pardOfferListFun();
            $(".input-group-btn").click(pardOfferListFun);
        }
    };
}();

var showProdOfferDetail=function(productId){
	$("#prodOfferId").val(productId);
	showPordOfferForm.action=APP_PATH+"/pardOffer/toDetail.shtml";
	showPordOfferForm.submit();
}
