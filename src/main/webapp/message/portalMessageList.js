var pagination;
function setbootpag(){
	pagination = $('.msgPagination').bootpag({total: 1
		   }).on("page", function(event, num){
			   $("#pageNum").val(num);
			   ajaxMsgData();
    }).find('.pagination');   
}

function settooptip(){
	$('[data-toggle="tooltip"]').tooltip()
}

function ajaxMsgData(){
	var str = $('#msgListForm').serialize();
	$.ajax({
		type: "POST",
		async:true,
	    url: "../messagePortal/showMessageList.shtml",
	    dataType:'json',
	    data:str,
		success:function(msgJson){
			 var msgTableHTML ;
			 if("LOOKED"==$("#lookFalg").val()){
				 msgTableHTML = getMsgListLookedHtml(msgJson.data);
				 $("#msgHistory").html(msgTableHTML); 
			 }else{
				 msgTableHTML = getMsgListHtml(msgJson.data);
				 $("#msgList").html(msgTableHTML); 
			 }
			 $('.msgPagination').bootpag({total: msgJson.total, maxVisible: msgJson.maxVisible});
      }
 });
}

function getMsgListHtml(data){
	var html = "";
	if(!data||!data[0]){
		return "<div style='text-align:center;'>None</div>";
	}
	 $.each(  
	    data,  
	    function(key) {  
	        if (data[key]["msgId"]) {  
	        	var title = data[key]["msgTitle"];
	        	if(title.indexOf("[Organization ID")>=0){
	        		title = "[Organization]" + title.substring(title.search(new RegExp('Name=')));
	        	}
	        	html += "<div class=\"panel panel-default\"><div class=\"panel-heading\"><h4 class=\"panel-title\"><a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#msgList\" href=\"#msgContent_"+data[key]["msgId"]+"\">";
	        	html +=	"<span class=\"fa fa-plus\">"+ title + "&nbsp;&nbsp;&nbsp;"+ data[key]["msgSubtitle"]+"</span></a>";
	        	html += "<div class=\"pull-right\">"+modalMessage(data[key]["msgId"]); 
	        	if("3"!=data[key]["msgType"]){
	        		html += "&nbsp;<button type=\"button\" class=\"btn btn-info btn-xs theme-btn\" id=\"btn_ignoreMsg\" onclick=\"ignoreMsg("+data[key]["msgId"]+");\">"+getLang('message_operation_ignore')+"</button>";
	        	}else if("3"==data[key]["msgType"]){
	        		html += "&nbsp;<button type=\"button\" class=\"btn btn-info btn-xs theme-btn\" id=\"btn_dealMsg\" onclick=\"dealMsg("+data[key]["msgId"]+",'"+data[key]["msgHandleAddress"]+"');\">"+getLang('message_operation_deal')+"</button>";
	        	}
	        	html += "</div></h4></div><div class=\"panel-collapse collapse\" id=\"msgContent_"+data[key]["msgId"]+"\"><div class=\"panel-body\"> "+data[key]["msgContent"]+"</div></div></div>";
	        }  
	    });
	return html;
}

function getMsgListLookedHtml(data){
	if(!data||!data[0]){
		return "<div style='text-align:center;'>None</div>";
	}
	var html = "";
	 $.each(  
	    data,  
	    function(key) {  
	        if (data[key]["msgId"]) {  
	        	var title = data[key]["msgTitle"];
	        	if(title.indexOf("[Organization ID")>=0){
	        		title = "[Organization]" + title.substring(title.search(new RegExp('Name=')));
	        	}
	        	html += "<div class=\"panel panel-default\"><div class=\"panel-heading\"><h4 class=\"panel-title\"><a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#msgList\" href=\"#msgContent_"+data[key]["msgId"]+"\">";
	        	html +=	"<span class=\"fa fa-plus\">"+ title + "</span><small> "+ data[key]["msgSubtitle"]+"</small></a>";
	        	html += "<div class=\"pull-right\">"+modalMessage(data[key]["msgId"]); 
	        	html += "</div></h4></div><div class=\"panel-collapse collapse\" id=\"msgContent_"+data[key]["msgId"]+"\"><div class=\"panel-body\"> "+data[key]["msgContent"]+"</div></div></div>";
	        }  
	    });
	return html;
}

function lookMsg(msgId){
	var url = "../messagePortal/lookMsgById.shtml?msgId="+msgId;
	$modal =  $('#showMessageModal');
    $modal.load(url, '', function() {
        $modal.modal('show');
    });
}

function modalMessage(msgId){
	var modalMsg = "<button class=\"btn btn-info btn-xs theme-btn showMessageModalByMsgId\" onclick=\"showMessageModalByMsgId(this);\" href=\"#\" data-height=\"750\" data-width=\"500\" "+
		"data-href=\"../messagePortal/lookMsgById.shtml?msgId=" + msgId + "\" data-toggle=\"modal\"> <span class=\"glyphicon glyphicon-search\"></span>" + getLang('message_operation_look') + " </button>";
	return modalMsg;
}

function showMessageModalByMsgId(obj) {
	var xsize,
    ysize,
    href,
    $modal,
    img,
    realWidth,
    realHeight,
    $this = $(obj),
    size = $this.size();
	 xsize = $this.attr('data-width')+'px';
	 ysize = $this.attr('data-height')+'px';
	 href = $this.attr('data-href');
	 //点击查询消息
	 $modal =  $('#showMessageModalByMsgId');
     $modal.css({'width':xsize,'height':ysize});
     $modal.load(href, '', function() {
         $modal.modal('show');
     });
}

function ignoreMsg(msgId){
	var form = document.messageForm;
	$("#msgId").val(msgId);
	form.action = APP_PATH + "/messagePortal/ignoreMessage.shtml";
	form.submit();
}

function dealMsg(msgId,dealURL){
	window.location.href = APP_PATH + dealURL;
}

$(document).ready(function(){
	 
	 $("#messageTab").click(function(){
			$("#lookFalg").val('');
			$("#msgType").val($("#msgTypeSelect").val());
			ajaxMsgData();
		});
		
	$("#historyTab").click(function(){
		$("#lookFalg").val('LOOKED');
		$("#msgType").val($("#msgTypeSelect").val());
		ajaxMsgData();
	});
	
	$("#msgListFormSearch").click(function(){
		$("#msgType").val($("#msgTypeSelect").val());
		ajaxMsgData();
	});
	 
	showMessageModalByMsgId();
});