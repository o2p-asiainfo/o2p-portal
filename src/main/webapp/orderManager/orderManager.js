var OrderManager = function() {

	//初始化查询条件中的日期
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
   var oTable;
    var handleDataTable = function() {
        //初始化表格数据
    	oTable = $('#orderListTable').dataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            //"lengthMenu": [5, 10, 15, "All"],
            "ajax": APP_PATH+"/orderManager/orderList.shtml",
            "order": [],
            "autoWidth": false,
            "columns": [
                      {"data": "ORDER_INDEX"},
                      {"data": "ORDERID"},
                      {"data": "ORDERNBR"},
                      {"data": "SONBR"},
                      {"data": "CEPNAME"},
                      {"data": "ACCEPTDATE"},
                      {"data": "COUNTRYCODE"},
                      {"data": "LOCALITY"},
                      {"data": "MAINSERVERID"},
                      {"data": "TRADETYPECODE"},
                      {"data": "ORDERSTATUSNAME"},
                      {"data": "SUBPROCID"}
                  ],
            "columnDefs": [{
                "orderable": false,
                "targets":  [0,1,2,3,4,5,6,7,8,9,10],
                "width": "36px"
            },{
                "visible": false,
                "targets": [7,10]
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        jQuery('#orderListTable_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#orderListTable_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown 

        //点击按钮查看详情
        $("#btn-detail").click(function() {
            resetFrom();		//清空原表单
            //判断是否有选择
            var oSelected = $("tbody input[type='radio']:checked");
            if (oSelected.size() > 1) {
                toastr.error(getLang('message_one_record_operation'));
            } else if (oSelected.size() == 1) {
                loadOrderInfo();

            	var tab2Chils = $(".detail_tab").find("a");
            	if(tab2Chils.length>0){
            		tab2Chils[0].click();
            	}
                $('#myModal').modal('show');
            } else {
                toastr.error(getLang('message_least_one_operation'));
            }
        });
    };

        /**
         * 表格查询操作
         */
        $('#searchBut').click(function(e){
        	//e.stopPropagation();
        	
        	var soNbr = $('#soNbr').val();
        	var orderNbr = $('#orderNbr').val();
        	var soType = $('#soType').val();
        	var startAcceptDate = $('#startAcceptDate').val();
        	var endAcceptDate = $('#endAcceptDate').val();
        	
        	var url = APP_PATH+'/orderManager/orderList.shtml?soNbr='+soNbr
        	        + '&soType='+soType
        	        + '&orderNbr='+orderNbr
        	        + '&startAcceptDate='+startAcceptDate
        	        + '&endAcceptDate='+endAcceptDate;
        	oTable.api().ajax.url(url).load();
        });
        
        /**
         * 查看详情加载数据
         */
        function loadOrderInfo() {
            var oSelected = $("tbody input[type='radio']:checked");
            $.ajax({
                url: APP_PATH+"/orderManager/getOrderInfoById.shtml",
                data: {
                    "orderId": oSelected.val()
                },
                type: "GET",
                cache: false,
                dataType:'json',
                success: function(data) {
                    if (data) {
    			        showInfo(data);
                    } 
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }
        

        function showInfo(data){
        	if(data){
//            	console.info(showObj(data));
            	var orderInfo = data['orderInfo'];
//            	console.info(showObj(orderInfo));
            	
            	showOrderInfo(orderInfo);
        		var orderUserList = data['orderUserList'];
        		showUserInfo(orderUserList);
        		
        		var orderCustomerList = data['orderCustomerList'];
        		showCustomerInfo(orderCustomerList);
        		
        		var orderProductList = data['orderProductList'];
        		showProductInfo(orderProductList); 
        		
        		var addressList = data['userAddressList'];
        		showAddressInfo(addressList); 
        	}
        }

        function showAddressInfo(addressList){
        	$("#addressBody").html('');
        	if(addressList){
        		var html = '';
        		for(var i = 0; i < addressList.length; i++){
        			console.info(showObj(addressList[i]));
        			html += "<tr>";
        			html += "<td>"+addressList[i].USERID+"</td>";
        			html += "<td>"+getText(addressList[i].CITY)+"</td>";
        			html += "<td>"+getText(addressList[i].ZIPCODE)+"</td>";
        			html += "<td>"+getText(addressList[i].STREET)+"</td>";
        			html += "<td>"+getText(addressList[i].BUILDING)+"</td>";
        			html += "<td>"+getText(addressList[i].STAIRWAY)+"</td>";
        			html += "<td>"+getText(addressList[i].FLOOR)+"</td>";
        			html += "<td>"+getText(addressList[i].DOOR)+"</td>";
        			html += "</tr>";
        		}
        		$("#addressBody").html(html);
        	}
        }

        function showProductInfo(orderProductList){
        	$("#productBody").html('');
        	if(orderProductList){
        		var html = '';
        		for(var i = 0; i < orderProductList.length; i++){
//        			console.info(showObj(orderProductList[i]));
        			html += "<tr>";
        			html += "<td>"+orderProductList[i].SONBR+"</td>"; 
        			html += "<td>"+getText(orderProductList[i].MAINFLAG)+"</td>";
        			html += "<td>"+getText(orderProductList[i].PRODID)+"</td>";
        			html += "<td>"+getText(orderProductList[i].PRODNAME)+"</td>";
        			html += "<td>"+getText(orderProductList[i].ACTTYPE)+"</td>";
        			html += "</tr>";
        		}
        		$("#productBody").html(html);
        	}
        }

        function showCustomerInfo(orderCustomerList){
        	$("#customerBody").html('');
        	if(orderCustomerList){
        		var html = '';
        	    for(var i = 0; i < orderCustomerList.length; i++){
//        			console.info(showObj(orderCustomerList[i]));
        			html += "<tr>";
        			html += "<td>"+orderCustomerList[i].SONBER+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].CUSTID)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].COMPANYNAME)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].FIRSTNAME)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].MIDNAME)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].LASTNAME)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].STATE)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].USERTYPE)+"</td>";
        			html += "<td>"+getText(orderCustomerList[i].COUNTRY)+"</td>";
        			html += "</tr>";
        		}
        		$("#customerBody").html(html);
        	}
        }

        function showUserInfo(orderUserList){
        	$("#userInfoBody").html('');
        	if(orderUserList){
        		var html = '';
        		for(var i = 0; i < orderUserList.length; i++){
//        			console.info(showObj(orderUserList[i]));
        			html += "<tr>";
        			html += "<td>"+orderUserList[i].SONBR+"</td>";
        			html += "<td>"+orderUserList[i].USERID+"</td>";
        			html += "<td>"+getText(orderUserList[i].USERTYPE)+"</td>";
        			html += "<td>"+getText(orderUserList[i].COMPANYNAME)+"</td>";
        			html += "<td>"+getText(orderUserList[i].FIRSTNAME)+"</td>";
        			html += "<td>"+getText(orderUserList[i].MIDDLENAME)+"</td>";
        			html += "<td>"+getText(orderUserList[i].LASTNAME)+"</td>";
        			html += "<td>"+getText(orderUserList[i].ADDRESSINFO)+"</td>";
        			html += "<td>"+getText(orderUserList[i].PHONE)+"</td>";
        			html += "</tr>";
        		}
        		$("#userInfoBody").html(html); 
        	}
        }

        function getText(value){
        	return value ? value : ""; 
        }

        function showOrderInfo(orderInfo){
        	$("#orderId").val(orderInfo.ORDERID);
        	$("#orderNumber").val(orderInfo.SONBR);
        	$("#orderType").val(orderInfo.CEPNAME);
        	$("#acceptDate").val(orderInfo.ACCEPTDATE);
        	$("#countryCode").val(orderInfo.COUNTRYCODE);
        	$("#locality").val(orderInfo.LOCALITY);
        	$("#mainServiceId").val(orderInfo.MAINSERVERID);
        	$("#orderState").val(orderInfo.ORDERSTATUS);
        	$("#tradeTypeCode").val(orderInfo.TRADETYPECODE);
        	$("#originalOrderNumber").val(orderInfo.ORIGNALSONBR);
        	$("#bPMProcessId").val(orderInfo.PROCID);
        }

        function showObj(obj){
        	var objStr="";
        	for(items in obj){
        		var str="objStr+=items+'='+obj."+items+"+'\\n';";
        		eval(str);
        	}
        	return objStr;
        }


        /**
         * 重置表单
         */
        function resetFrom() {
        	$("#orderId").val("");
        	$("#orderNumber").val("");
        	$("#orderType").val("");
        	$("#acceptDate").val("");
        	$("#countryCode").val("");
        	$("#locality").val("");
        	$("#mainServiceId").val("");
        	$("#orderState").val("");
        	$("#tradeTypeCode").val("");
        	$("#originalOrderNumber").val("");
        	$("#bPMProcessId").val("");
            $("#userInfoBody>tr").remove();
            $("#addressBody>tr").remove();
            $("#customerBody>tr").remove();
            $("#productBody>tr").remove();
        }

    
    
    

    return {
        init: function() {
            var currIndex = 1;
            //方法
            handleDataTable();
            handleDatePickers('.date-picker');
        }
    }
}()
