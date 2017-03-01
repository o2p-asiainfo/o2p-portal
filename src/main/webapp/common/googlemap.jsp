<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
		html, body, #modal-body,#map-canvas { 
			height: 250px; margin: 0; padding: 0;
		}
    </style>
    <script type="text/javascript">
  		var map;
	    var marker;
	    var infowindow;
	    var geocoder;
	    var markersArray = [];
	    var addressMap={};
      	function initialize_1() {
      		var lng="12.590829100000064";
      		var lat="55.6743937";
      		if($("#selectZoneModal #lng").val()!="")lng=$("#selectZoneModal #lng").val();
      		if($("#selectZoneModal #lat").val()!="")lat=$("#selectZoneModal #lat").val();
			var mapOptions = {
	          center:new google.maps.LatLng( lat,lng ),
	          zoom: 8
	        };
	        map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
			geocoder = new google.maps.Geocoder();
	        //监听点击地图事件
	        google.maps.event.addListener(map, 'click', function (event) {
	            placeMarker(event.latLng);
	        });
		}
      	//添加标记
      	function placeMarker(location) {
	        clearOverlays(infowindow);//清除地图中的标记
	        marker = new google.maps.Marker({
	            position: location,
	            map: map
	        });
	        markersArray.push(marker);
	        //根据经纬度获取地址
	        if (geocoder) {
	            geocoder.geocode({ 'location': location }, function (results, status) {
	                if (status == google.maps.GeocoderStatus.OK) {
	                    if (results[0]) {
	                        attachSecretMessage(marker, results[0].geometry.location, results[0].formatted_address);
	                    }
	                } else {
	                	toastr.error("Geocoder failed due to: " + status);
	                }
	            });
	        }
	    }
	    //在地图上显示经纬度地址
    	function attachSecretMessage(marker, piont, address) {
	  		var message = "<b>LAT/LNG:</b> " + piont.lat() + " , " + piont.lng() + "<br/>" + "<b>Address:</b> " + address;
	        var infowindow = new google.maps.InfoWindow({
                content: message,
                size: new google.maps.Size(50, 50)
            });
	        infowindow.open(map, marker);
	        addressMap={"lng":piont.lng(), "lat":piont.lat(), "address":address};
	 	}
    	//删除所有标记阵列中消除对它们的引用
    	function clearOverlays(infowindow) {
	        if (markersArray && markersArray.length > 0) {
	            for (var i = 0; i < markersArray.length; i++) {
	                markersArray[i].setMap(null);
	            }
	            markersArray.length = 0;
	        }
	        if (infowindow) {
	            infowindow.close();
	        }
	    }
    
    	//搜索框查询
		function searchmap(){ 
	       //先从输入框中取出要搜的地名  
	       var address=document.getElementById("address").value;  
	       if(address==null||address==""){  
	    	   	toastr.error("Please Enter Address!");
	            return false;  
	       }else{  
	       // geocoder = new google.maps.Geocoder(); //注意:还有一个全局的 var geocoder 对象  
	        if(geocoder){  
	            geocoder.geocode({'address': address }, function(results, status) {  
	                if (status == google.maps.GeocoderStatus.OK) { 
	                	placeMarker(results[0].geometry.location); return;
	                    } else {  
	                    	toastr.error("No Result:" + status);  
	                    }  
	                });  
	            }  
	       }  
	    }      
		
		jQuery(document).ready(function() {
			$("#selectZoneModal2").find(".mapCancel").click(function(){
				$(this).closest("#selectZoneModal2").modal('hide');
	        });
			$("#selectZoneModal2").find(".mapOK").click(function(){
				if(addressMap["address"]==null){
					toastr.error("Please Select Zone!");
				}else{
					$("#selectZoneModal #latitudeAndLongitude").val(addressMap["address"]);
					$("#selectZoneModal #lng").val(addressMap["lng"]);
					$("#selectZoneModal #lat").val(addressMap["lat"]);
					$(this).closest("#selectZoneModal2").modal('hide');
				}
	        });
	 	});
    </script>
  </head>
  
  <body>
	  <div class="modal-header" style="padding-bottom:0;">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
	    <h4 class="modal-title">Latitude And Longitude</h4>
	    <hr/>
    	<div class="search-form-default" style="bottom: 0">
	      <form action="#" class="form-inline">
	      	<input type="text" name="address" id="address" placeholder="Address" class="form-control" value=""/>
	  	 	<button class="btn default" type="button" onclick="javascript:searchmap()" id="addQuery">Query</button>
	      </form>
	    </div>
	  </div>
	  <div id="modal-body" class="modal-body" style="width: 100%">
	  	<div id="map-canvas">loading...</div>
	  </div>
	  <div class="modal-footer">
	    <button type="button" class="btn btn-default mapCancel">cancel</button>
	    <button type="submit" class="btn theme-btn mapOK"  onclick="javascript:submitFun()">submit</button>
	  </div>
  </body>
</html>