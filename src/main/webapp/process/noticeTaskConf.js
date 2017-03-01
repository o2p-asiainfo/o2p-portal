
function noticeTaskInitialize(){
   	if(noticeServiceId !="" && noticeMessageFlowId !=""){
	   	$('#INVOKE_API').val(noticeServiceId);						//通用的通知服务ID,配在公共配置文件eaap_common.properties中（NOTICE_SERVICE_ID）
	   	$('#INVOKE_API_NAME').val("noticeService");
	   	$('#messageFlowId').val(noticeMessageFlowId);		//公用的通知消息流ID,配在公共配置文件eaap_common.properties中（NOTICE_MESSAGE_FLOW_ID）

		//创建服务调用实例：
		var jsonIns={
			'messageFlowId':$('#messageFlowId').val(),
			'serInvokeInsId':$('#serInvokeInsId').val(),
			'componentId':$('#systemId').val(),
			'serviceId':$('#INVOKE_API').val(),
			'name':'NoticeSerInvokeIns'+$('#INVOKE_API').val()	//服务调用实例名称
		};
		$.ajax({
		        url: APP_PATH+"/messageFlow/saveSerInvokeInsForNoticeTask.shtml?jsonObject="+JSON.stringify(jsonIns),
		        data: "",
		        type: "post",
		        dataType:"json",
		        async : false, 
		        success: function(backdata) {
		            if (backdata.result == 'success') {
		            	serInvokeInsId= backdata.id; 
			           	$('#serInvokeInsId').val(serInvokeInsId);
		            } else  {
		            	 toastr.error($.i18n.prop('eaap.op2.portal.mgr.message.failure'));
		            }
		        },
		        error: function(error) {
		        	 toastr.error($.i18n.prop('eaap.op2.portal.mgr.message.failure'));
		        }
		});
   	}
}

//email、sms配制
var msType="EMAIL";
function emailSmsNoticeConf(es) {
    if(es=="email"){
    	msType="EMAIL";
        $("#emailSmsNoticeConf").find(".modal-title").html("Email Notification Protocol Configuration");
        $("#emailSmsNoticeConf").find("#esAddTitle").html("Email address:");
    	$("#emailTitleDiv").show();
    	$("#msContent").css("height","230px");
    }else{
    	msType="SMS";
        $("#emailSmsNoticeConf").find(".modal-title").html("SMS Notification Protocol Configuration");
        $("#emailSmsNoticeConf").find("#esAddTitle").html("SMS reception number:");
    	$("#emailTitleDiv").hide();
    	$("#msContent").css("height","318px");
    }
	$("#msAddress").val("");
	$("#emailTitle").val("");
	$("#msContent").val("");
	$("#orderCompletedProtocol").html("<img src='"+APP_PATH+"/resources/img/input-spinner.gif' style='padding:10;'/>");
	$("#productAttribute").html("<img src='"+APP_PATH+"/resources/img/input-spinner.gif' style='padding:10;'/>");
    $('#emailSmsNoticeConf').modal('show');
    loadNoticeTaskConf();
	getOrderCompletedProtocol();
	getProdAttributeList();
	variableid=undefined;
 }
 

$("#emailSmsNoticeConfCancel").live("click",function(){
	   $("#emailSmsNoticeConf").modal("hide");
});

//文本框点位位置获取
var variablestart,variableend,variableid;
$("textarea[type='data-area'],input[type='data-area']").live('mouseup',function(e){
	variableid=$(this).attr("id");
	variablestart=document.getElementById(variableid).selectionStart;
	variableend=document.getElementById(variableid).selectionEnd;
})	

$("textarea[type='data-area']").live('change',function(e){
	var thisid=$(this).attr("id");
	variablestart=document.getElementById(thisid).selectionStart;
	variableend=document.getElementById(thisid).selectionEnd;
})

$("#productAttribute li").live("click",function(){
	if(variableid==undefined){
		return;
	}
	if(variableid=="msAddress"){
		$("#msAddress").val("");
	}
	//$("#mailContent").val($(this).attr("name"));
	$("#productAttribute li.act").removeClass("act");
	$(this).addClass("act");
	var attrCode=$(this).attr("code");
	//用#{...}包装
	varipath="#{/*[local-name()='Envelope']/*[local-name()='Body']/*[local-name()='SoReceiveReq']/*[local-name()='OrderCont']/ServiceOrders/ServiceOrder/ServiceInfo/ProdInfo/MainProdInfos/MainProdInfo/ProdPrptyInfo[PrptyName='"+attrCode+"']/PrptyValue}";
	if($("#"+variableid).val()==""){
		$("#"+variableid).val(varipath);
	}else{
		var text=$("#"+variableid).val();
		if(typeof(variablestart) == "undefined"){
			variablestart=$("#"+variableid).val().length;
			variableend=$("#"+variableid).val().length;
		}
		if(variablestart==variableend){
			var ec1=text.substring(0,variablestart)
			var ec2=text.substring(variablestart)
			text=ec1+varipath+ec2;
		}else{
			text=text.substring(variablestart,variableend)
			text=$("#"+variableid).val().replace(text,varipath);
		}
		$("#"+variableid).val(text);
	}
})		

$("#orderCompletedProtocol li").live("click",function(){
	if(variableid==undefined){
		return;
	}
	if(variableid=="msAddress"){
		$("#msAddress").val("");
	}
	//$("#mailContent").val($(this).attr("name"));
	$("#orderCompletedProtocol li.act").removeClass("act");
	$(this).addClass("act");
	varipath="${"+$(this).attr("path")+"}";					//用${...}包装
	if($("#"+variableid).val()==""){
		$("#"+variableid).val(varipath);
	}else{
		var text=$("#"+variableid).val();
		if(typeof(variablestart) == "undefined"){
			variablestart=$("#"+variableid).val().length;
			variableend=$("#"+variableid).val().length;
		}
		if(variablestart==variableend){
			var ec1=text.substring(0,variablestart)
			var ec2=text.substring(variablestart)
			text=ec1+varipath+ec2;
		}else{
			text=text.substring(variablestart,variableend)
			text=$("#"+variableid).val().replace(text,varipath);
		}
		$("#"+variableid).val(text);
	}
})		

function saveNoticeTaskConf(){
    var taskId = $("#tache_id").val();
	var jsonStr = {
	    	'processId':$("#processId").val(),
	    	'taskId':$("#tache_id").val(),
	    	'msAddress':$("#msAddress").val(),
	    	'emailTitle':$("#emailTitle").val(),
	    	'msContent':$("#msContent").val(),
	    	'msType':msType
	};
	$.ajax({
		type: "POST",
		async:true,
        url: APP_PATH+"/process/saveNoticeTaskConf.shtml",
	    dataType:'json',
        data: jsonStr,
	    success:function(msg){
	    	if(msg.code!=null && msg.code=="0000"){
	    		$("#emailSmsNoticeConf").modal("hide");
				toastr.success("Save success");
	    	}else{
				toastr.error(msg.desc);
	    	}
	    }
	});
}

//加载已配的工作流通知任务节点配置信息
function loadNoticeTaskConf(){
    var taskId = $("#tache_id").val();
	var jsonStr = {
	    	'processId':$("#processId").val(),
	    	'taskId':$("#tache_id").val()
	};
	$.ajax({
		type: "POST",
		async:true,
        url: APP_PATH+"/process/loadNoticeTaskConf.shtml",
	    dataType:'json',
        data: jsonStr,
	    success:function(msg){
	    	if(msg.code!=null && msg.code=="0000"){
	    		if(msg.confInfo!=null){
	    			var confInfo = msg.confInfo;
	    	    	if(msType=="EMAIL"){
	    	    		$("#msAddress").val(confInfo.EMAIL_ADDRESS);
	    	    		$("#emailTitle").val(confInfo.EMAIL_TITLE);
	    	    		$("#msContent").val(confInfo.EMAIL_CONTENT);
	    	    	}else if(msType=="SMS"){
	    	    		$("#msAddress").val(confInfo.SMS_ADDRESS);
	    	    		$("#msContent").val(confInfo.SMS_CONTENT);
	    	    	}
	    		}
	    	}else{
				toastr.error(msg.desc);
	    	}
	    }
	});
}


/**
 * 加载服务开通竣工报文协议
 */
function getOrderCompletedProtocol(){
	//加载节点
	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/newadapter/getNodeDesc.shtml",
	    dataType:'json',
	    data:{pageTcpCtrFId:'5006',pageLoadSideFlg:'L',pageContractType:'1',pageContractName:'Crm.CommonAdapter',pageContractNum:'0'},		//5006:竣工报文协议
		success:function(msg){
			$("#orderCompletedProtocol").html("");
			loadOrderCompletedProtocol(msg);
        }
     });
}


/**
 * 加载竣工报文协议节点数据
 */
function loadOrderCompletedProtocol(my_object){
	dadaobj=my_object;
	josnObj=my_object.nodes.LeftNode;
	if(josnObj!=null){
		$.each(josnObj,function(key,val){
			josnObjList=josnObj[key];
			var id=key;
			$("#orderCompletedProtocol").append("<ul class='l' id='"+id+"'></ul>");
			$("#"+id).append("<div class='head' style='width:338px;'><span class='title' tip='"+josnObjList.versionName+"'>"+josnObjList.versionName+"</span></div>");

			if(josnObj[key].flags=="false"){
				$("#"+id).find(".head").css("background","#FF8C00").find(".flag").css("visibility","hidden");
				$("#"+id).find("input:checkbox").attr("checked","checked");
			}else if(josnObj[key].flags=="T"){
				$("#"+id).find(".head").css("background","#ab5a03").find(".flag").find("input[value='t']").attr("checked","checked");
			}else if(josnObj[key].flags=="R"){
				$("#"+id).find(".head").css("background","#690e02").find(".flag").find("input[value='r']").attr("checked","checked");
			}
			
			var nodeList=josnObjList.nodeList;
			$.each(nodeList,function(key,val){
				var ns=val.childCount>0?"par":"child";
				var lli='<li id="'+key+'" class="'+ns+'" name="'+val.name+'" nodeType="'+val. nodeType+'" nodeNum="'+val.nodeNum+'"  dept="'+val.dept+'" path="'+val.path+'"  nodeDescIdPath="'+val.nodeDescIdPath+'" childCount="'+val.childCount+'" nodeDescId="'+val.nodeDescId+'"><span class="d-t"><em style="margin-left:'+val.dept*5+"px"+'" >'+val.name+'</em></span><span class="d-s"><b>'+val.nodeNum+'</b>'+val.nodeType+'</span><div class="tips">'+val.name+'<br>'+val.description+'</div></li>';
				$("#"+id).append(lli);
			});
		});
	}
}

//加载产品属性列表：
function getProdAttributeList(){
	$.ajax({
		type: "POST",
		async:true,
	    url: APP_PATH+"/pardSpec/list.shtml",
	    dataType:'json',
	    data:{start:'0',length:'100'},
		success:function(msg){
			$("#productAttribute").html("");
			loadProdAttributeData(msg);
        }
     });
}

function loadProdAttributeData(my_object){
	$("#productAttribute").append("<ul id='productAttributeUl' class='l'><div class='head' style='width:338px;'><span class='title' tip='Service/Product Attribute'>Service/Product Attribute</span></div></ul>");
	dadaobj=my_object;
	josnObj=my_object.data;
	if(josnObj!=null){
		$.each(josnObj,function(key,val){
			if(val. ATTR_SPEC_CODE!=""){
				var lli='<li id="'+val.ATTR_SPEC_ID+'" class="child" name="'+val.ATTR_SPEC_NAME+'" code="'+val. ATTR_SPEC_CODE+'" path="/soapenv:Envelope7"><span class="d-t"><em style="margin-left:5px">'+val. ATTR_SPEC_NAME+'</em></span><span class="d-s"><b>1-1</b>String</span><div class="tips">'+val.ATTR_SPEC_CODE+'<br></div></li>';
				$("#productAttribute").find("#productAttributeUl").append(lli);
			}
		});
	}
}


$(".stolist ul.l .head").live("click",function(){	
	$(this).parent().toggleClass("contract");
	$(this).find(".title").toggleClass("t");
});
