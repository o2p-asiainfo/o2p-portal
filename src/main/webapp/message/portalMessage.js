function setPopover(){
	$('#example').popover({
			html:true,
			trigger:'click'
			}
	);
}

function getMsgList(){
	$.ajax({
		type: "POST",
		async:true,
	    url: "../messagePortal/showMessageSimple.shtml",
	    dataType:'json',
	    data:{},
		success:function(msgJson){
			var data = "";
			if(!msgJson){
				data = emptyMsgListToTable();
			}else{
				$(".badge").text(msgJson.cntMsg);
				data = chanegToTabel(msgJson.data);
			}
			$('#example').popover({
				trigger:'click',
				html: true,
				content:data,
			});
      }
 });
}

function openWind(msgType){
	openWindows_lock('../messagePortal/goMessageList.shtml?msgType='+msgType,"operation",1000,550,true);
}

function messagesContent(msgId){
	openWindows_lock('../messagePortal/lookMsgById.shtml?fristLook=0&message.MsgId='+msgId,"operation",1000,550,true);
}

function chanegToTabel(data){
	var html1 = "<dl><dt>"+getLang('message_type_1')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(1);'>"+getLang('message_operation_more')+"</button></li></a></dt>";  
	var html2 = "<dl><dt>"+getLang('message_type_2')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(2);'>"+getLang('message_operation_more')+"</button></li></a></dt>";  
	var html3 = "<dl><dt>"+getLang('message_type_3')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(3);'>"+getLang('message_operation_more')+"</button></li></a></dt>";  
	var cnt1 = 0;
	var cnt2 = 0;
	var cnt3 = 0;
    $.each(  
        data,  
        function(key) {  
            if (data[key]["msgId"]) {  
            	if("1"==data[key]["msgType"]&&cnt1<3){
            		 html1 += "<dd style='padding-top:3px;'><a onclick='messagesContent("+data[key]["msgId"]+")' title='"+data[key]["msgTitle"]+"'\">"+formatTitle(data[key]["msgTitle"],13)
                        + "<small class='pull-right'>"+data[key]["formatBeginDate"]+"</small></a></dd>";
            		 cnt1++;
            	}else if("2"==data[key]["msgType"]&&cnt2<3){
            		 html2 += "<dd style='padding-top:3px;'><a onclick='messagesContent("+data[key]["msgId"]+")' title='"+data[key]["msgTitle"]+"'\">"+formatTitle(data[key]["msgTitle"],13)
                        + "<small class='pull-right'>"+data[key]["formatBeginDate"]+"</small></a></dd>";
            		 cnt2++;
            	}else if("3"==data[key]["msgType"]&&cnt3<3){
            		 html3 += "<dd style='padding-top:3px;'><a onclick='messagesContent("+data[key]["msgId"]+")' title='"+data[key]["msgTitle"]+"'\">"+formatTitle(data[key]["msgTitle"],13)
                        + "<small class='pull-right'>"+data[key]["formatBeginDate"]+"</small></a></dd>";
            		 cnt3++;
            	}
            }  
        });  
    return html1+"</dl>"+html2+"</dl>"+html3+"</dl>";
}

function emptyMsgListToTable(){
	var html1 = "<dl><dt>"+getLang('message_type_1')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(1);'>"+getLang('message_operation_more')+"</button></li></a></dt><dd>"+getLang('message_type_1_no_data')+"</dd>";  
	var html2 = "<dl><dt>"+getLang('message_type_2')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(2);'>"+getLang('message_operation_more')+"</button></li></a></dt><dd>"+getLang('message_type_2_no_data')+"</dd>";  
	var html3 = "<dl><dt>"+getLang('message_type_3')+"<a><button class='btn btn-warning btn-xs pull-right' onclick='openWind(3);'>"+getLang('message_operation_more')+"</button></li></a></dt><dd>"+getLang('message_type_3_no_data')+"</dd>";  
	return html1+"</dl>"+html2+"</dl>"+html3+"</dl>";
}

function formatTitle(title,length){
	if(!title){
		return "";
	}
	if(title.length<=length){
		return title;
	}
	title = title.substring(0,length);
	return title+"...";
}