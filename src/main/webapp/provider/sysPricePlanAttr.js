var pricePlanAttribute = function() {
	var pricePlanAttributeInit = function(){
		var pricingInfoId = $("#pricingInfoId").val();
		var pricePlanAttrListDiv = $("div[id=pricePlanAttrListDiv]");
		if(pricingInfoId){
			
		}else{
			$("div[id=pricePlanAttrListDiv]").find("#zoneModal,#defaultVal").attr("disabled","true");
		}
		
		$("div[id=pricePlanAttrListDiv]").find(".numOfLicenseFun").click(function(){
			var isDisabled = $(this).attr("checked");
			if("checked"!=isDisabled){
				$("#numOfLicenseDiv").find("#defaultVal").val("");
				$("#numOfLicenseDiv").find("#defaultVal").attr("disabled","true");
			}else{
				$("#numOfLicenseDiv").find("#defaultVal").removeAttr("disabled");
			}
		});
		
		$("div[id=pricePlanAttrListDiv]").find(".zoneFun").click(function(){
			var isDisabled = $(this).attr("checked");
			if("checked"!=isDisabled){
				$("#zoneDiv").find("#defaultVal").val("");
				$("#zoneDiv").find("#zoneModal").attr("disabled","true");
			}else{
				$("#zoneDiv").find("#zoneModal").removeAttr("disabled");
			}
		});

		$("div[id=pricePlanAttrListDiv]").find(".SLAFun").click(function(){
			var isDisabled = $(this).attr("checked");
			if("checked"!=isDisabled){
				$("#SLADiv").find("#defaultVal").val("");
				$("#SLADiv").find("#defaultVal").attr("disabled","true");
			}else{
				$("#SLADiv").find("#defaultVal").removeAttr("disabled");
			}
		});
		
	}
	
	
	
	return {
        init: function(id) {
        	pricePlanAttributeInit();
        }
	}

}()