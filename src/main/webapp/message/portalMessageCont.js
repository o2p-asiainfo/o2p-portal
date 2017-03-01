function settooptip(){
	$('[data-toggle="tooltip"]').tooltip()
}

function msgHandleFunction(address){
	window.location.href = APP_PATH + address;
}

function submitForm(){
	$("#decisionForm").submit();
}

$(document).ready(function(){
	$("#decisionFormSubmitBtn").click(function(){
		document.decisionForm.submit();
	});
	
	$("#decisionFormCancelBtn").click(function(){
		//$('#showMessageModalByMsgId').modal('hide');
	});
});