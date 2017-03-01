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
				 $(".msgHistory").html(msgTableHTML); 
			 }else{
				 msgTableHTML = getMsgListHtml(msgJson.data);
				 $(".msgContent").html(msgTableHTML); 
			 }
			 $('.msgPagination').bootpag({total: msgJson.total, maxVisible: msgJson.maxVisible});
      }
 });
}

function getMsgListHtml(data){
	var html = "<div class=\"\" id=\"msgList\"><div class=\"panel panel-default\">";
	if(!data||!data[0]){
		return "";
	}
	 $.each(  
	    data,  
	    function(key) {  
	        if (data[key]["msgId"]) {  
	        	html += "<div class=\"panel-heading\"><h4 class=\"panel-title\"><a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#msgList\" href=\"#msgContent_"+data[key]["msgId"]+"\">";
	        	html +=	"<span class=\"fa fa-hand-o-right\">"+ data[key]["msgTitle"] + "</span><small> "+ data[key]["msgSubtitle"]+"</small></a>";
	        	html += "<div class=\"pull-right\">"+modalMessage(data[key]["msgId"]); 
	        	if("3"!=data[key]["msgType"]){
	        		html += "<a type=\"button\" class=\"btn btn-default btn-xs\" id=\"btn_ignoreMsg\" onclick=\"ignoreMsg("+data[key]["msgId"]+");\">"+getLang('message_operation_ignore')+"</a>";
	        	}else if("3"==data[key]["msgType"]){
	        		html += "<a type=\"button\" class=\"btn btn-default btn-xs\" id=\"btn_dealMsg\" onclick=\"dealMsg("+data[key]["msgId"]+",'"+data[key]["msgHandleAddress"]+"');\">"+getLang('message_operation_deal')+"</a>";
	        	}
	        	html += "</div></h4></div><div class=\"panel-collapse collapse\" id=\"msgContent_"+data[key]["msgId"]+"\"><div class=\"panel-body\"> "+data[key]["msgContent"]+"</div></div>";
	        }  
	    });
	return html += "</div></div>";
}

function getMsgListLookedHtml(data){
	if(!data||!data[0]){
		return "";
	}
	var html = "<div class=\"\" id=\"msgList\"><div class=\"panel panel-default\">";
	 $.each(  
	    data,  
	    function(key) {  
	        if (data[key]["msgId"]) {  
	        	html += "<div class=\"panel-heading\"><h4 class=\"panel-title\"><a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#msgList\" href=\"#msgContent_"+data[key]["msgId"]+"\">";
	        	html +=	"<span class=\"fa fa-hand-o-right\">"+ data[key]["msgTitle"] + "</span><small> "+ data[key]["msgSubtitle"]+"</small></a>";
	        	html += "<div class=\"pull-right\"><a class=\"btn btn-default btn-xs\" id=\"btn_lookMsg\" onclick=\"lookMsg("+data[key]["msgId"]+");\">"+getLang('message_operation_look')+"</a>"; 
	        	html += "</div></h4></div><div class=\"panel-collapse collapse\" id=\"msgContent_"+data[key]["msgId"]+"\"><div class=\"panel-body\"> "+data[key]["msgContent"]+"</div></div>";
	        }  
	    });
	return html += "</div></div>";
}

function lookMsg(msgId){
	var url = "../messagePortal/lookMsgById.shtml?msgId="+msgId;
	$modal =  $('#showMessageModal');
    $modal.load(url, '', function() {
        $modal.modal('show');
    });
}

function modalMessage(msgId){
	var modalMsg = "<a class=\"btn theme-btn btn-block showMessageModalByMsgId\" onclick=\"showMessageModalByMsgId();\" href=\"#\" data-height=\"750\" data-width=\"500\" "+
		"data-href=\"../messagePortal/lookMsgById.shtml?msgId=" + msgId + "\" data-toggle=\"modal\"> " + getLang('message_operation_look') + " </a>";
	return modalMsg;
}

function showMessageModalByMsgId() {
	var xsize,
    ysize,
    href,
    $modal,
    img,
    realWidth,
    realHeight,
    $this = $('.showMessageModalByMsgId'),
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
	form.action = "../messagePortal/ignoreMessage.shtml";
	form.submit();
}

function dealMsg(msgId,dealURL){
	window.location.href="/.."+dealURL;
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