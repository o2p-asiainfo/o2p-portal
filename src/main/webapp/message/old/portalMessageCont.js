function settooptip(){
	$('[data-toggle="tooltip"]').tooltip()
}

function msgHandleFunction(address){
	var vOpener=window.art.dialog.opener;
	vOpener.window.location.href="/.."+address;
	art.dialog.close();
}

function submitForm(){
	$("#decisionForm").submit();
}

$(document).ready(function(){
	$("#decisionFormSubmitBtn").click(function(){
		document.decisionForm.submit();
	});
	
	$("#decisionFormCancelBtn").click(function(){
		$('#showMessageModalByMsgId').modal('hide');
	});
});