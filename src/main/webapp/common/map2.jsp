<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
      html, body, #map-canvas { height: 100%; margin: 0; padding: 0;}
    </style>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA9-e46hdffPB8IeOMcErv_7qfFjwlzQXA&language=en">
    </script>
    <script type="text/javascript">
  		var map;
	    var marker;
	    var infowindow;
	    var geocoder;
	    var markersArray = [];
      function initialize() {
				var mapOptions = {
          center:new google.maps.LatLng(55.6743937 , 12.590829100000064),
          zoom: 8
        };
        map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
 				geocoder = new google.maps.Geocoder();
        //监听点击地图事件
        google.maps.event.addListener(map, 'click', function (event) {
            placeMarker(event.latLng);
        });
        map.enableGoogleBar();
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
	                    alert("Geocoder failed due to: " + status);
	                }
	            });
	        }
	    }
	    //在地图上显示经纬度地址
    function attachSecretMessage(marker, piont, address) {
        var message = "<b>Latitude and longitude:</b>" + piont.lat() + " , " + piont.lng() + "<br />" + "<b>address:</b>" + address;
        var infowindow = new google.maps.InfoWindow(
            {
                content: message,
                size: new google.maps.Size(50, 50)
            });
        infowindow.open(map, marker);
        if (typeof (mapClick) == "function") mapClick(piont.lng(), piont.lat(), address);
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
            alert("请输入要定位的地名！");  
            return false;  
       }else{  
       // geocoder = new google.maps.Geocoder(); //注意:还有一个全局的 var geocoder 对象  
        if(geocoder){  
            geocoder.geocode({'address': address }, function(results, status) {  
                if (status == google.maps.GeocoderStatus.OK) { 
                	placeMarker(results[0].geometry.location); return;
                    /*var GeoCode = ((results[0].geometry.location).toString().replace(/[()]/g, '')).split(",",2);  
                    var lat = parseFloat(GeoCode[0]);//纬度  
                    var lng = parseFloat(GeoCode[1]);//经度  
                    var mylatlng = new google.maps.LatLng(lat, lng);      
                    map.setCenter(mylatlng);                                           
                    //对搜索到的这个点进行标注  
                  	var marker = new google.maps.Marker({  
                        map: map,  
                        position: mylatlng,  
                        title:address  
                    });      */    
                    } else {  
                    alert("Error Message:" + status);  
                    }  
                });  
            }  
       }  
    }      
      google.maps.event.addDomListener(window, 'load', initialize);//初始化入口
    </script>
  </head>
  
  <body>
  	Address：<input id="address" value="" />
  	<input type="submit" class="btn default" onclick="javascript:searchmap()" value="search" />
		<div id="map-canvas"></div>
  </body>
</html>