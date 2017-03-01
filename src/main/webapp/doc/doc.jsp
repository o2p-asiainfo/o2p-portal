<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<%@include file="../header.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/css/rs-style.css" media="screen">
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/css/settings.css" media="screen">
<link href="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
<link href="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
</head>
<style type="text/css"> 
 .table_td{
    font-size:13px;
    overflow:hidden;
 	text-overflow:ellipsis;
 	white-space:nowrap;
 }
 .table_css{
 	table-layout:fixed;
 }
</style>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">
<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>Documentation</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml?tid=${tid}">Home</a> </li>
          <li class="active">Documentation</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container">
    <div class="tab-style-1 mt40 mb30">
      <ul class="nav nav-tabs" id="dirUl">
        <!-- add dir li -->
      </ul>
      <div class="tab-content active">
        <div class="tab-pane active">
          <div class="clearfix">
            <div class="col-md-3 pdl0">
              <ul id="secondDirUl" class="ver-inline-menu tabbable margin-bottom-10">
               	 <!-- dir li -->
              </ul>
            </div>
            <div class="col-md-9 pdr0"> 
              <!-- BEGIN TAB CONTENT -->
              <div class="tab-content pd0"> 
                <!--  
                <div class="input-group input-large pull-right"  id="seachApi" style="display:none;">
          			<input type="text" class="form-control" placeholder="API Name" id="apiName">  			
          			<span class="input-group-btn">
          				<button type="submit" class="btn theme-btn"><i class="fa fa-search search-btn show-search-icon"></i></button>
          			</span> 
        		</div>-->
              	<div class="tab-pane active" id="dir_content">
              	    <!-- dir content -->
              	</div>
              </div>
              <!-- END TAB CONTENT --> 
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END CONTAINER --> 
</div>
<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="../footer.jsp" %>
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.min.js"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="${APP_PATH}/resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script> 
<script type="text/javascript">
jQuery(document).ready(function() {
   App.init();
   myInit();
});

var myInit = function(){
	$.ajax({
		 async : false,
		 type : "POST",
		 url :  "${APP_PATH}/doc/qryDirList.shtml",
		 data : {},
		 dataType : "json",
		 success : function(data) {
			 if(data && data.dirList){ 
				 var dirList = data.dirList;
				 appendDirHtml(dirList); 
			 }
		 }
	});
};

var appendDirHtml = function(dirList){
	$.each(dirList, function(key,val){
		var newli = '<li id="li_'+val.DIRID+'" dirId="'+val.DIRID+'">'+
		              '<a id='+val.DIRID+' class="dir_a" data-toggle="tab" href="#tab_"'+val.FADIRID+'_'+val.DIRID+'>'+val.DIRNAME+'</a>'+
		            '</li>';
		$("#dirUl").append(newli);
	});
	$("#dirUl li:nth-child(1)").addClass("active");
	var initDirId = $("#dirUl li:nth-child(1)").attr("dirId");; 
	addSecondDir(initDirId); 
	$('.dir_a').click(function(){
		if('103' == this.id){
			addApiDir();
		}else{
			addSecondDir(this.id); 
		}
    });
};

var appendApiDirHtml = function(apiDirList,thirdDirList){
	$("#secondDirUl").html('');
	$.each(apiDirList, function(key,val){
		var newli = '<li dirId="'+val.DIRID+'" type="'+val.DIRTYPE+'">'+
		              '<a data-toggle="tab" class="sdir_a" id="'+val.DIRID+'">'+ val.DIRNAME + '</a>'+
		              '<span class="after"></span>' + 
		            '</li>'; 
		$("#secondDirUl").append(newli); 
	});
	$.each(thirdDirList, function(key,val){
		var newli = '<li dirId="'+val.DIRID+'" type="'+val.DIRTYPE+'">'+
		              '<a data-toggle="tab" class="sdir_a" id="'+val.DIRID+'" type="cApi">&nbsp;&nbsp;&nbsp;'+ val.DIRNAME + '</a>'+
		              '<span class="after"></span>' + 
		            '</li>'; 
		$("#secondDirUl").append(newli); 
	});
	$("#secondDirUl li:nth-child(1)").addClass("active");
	var initSDirId = $("#secondDirUl li:nth-child(1)").attr("dirId");
	var initSDirType = $("#secondDirUl li:nth-child(1)").attr("type");
	addApiTable(initSDirId,initSDirType); 
	$('.sdir_a').click(function(){
		var type = this.type;
		var dirId = this.id;
		addApiTable(dirId,type);
    });
};

var appendSecondDirHtml = function(secondDirList){
	$("#secondDirUl").html('');
	$.each(secondDirList, function(key,val){
		var newli = '<li dirId="'+val.DIRID+'" type="'+val.DIRTYPE+'">'+
		              '<a data-toggle="tab" class="sdir_a" id="'+val.DIRID+'" type="'+val.DIRTYPE+'">'+ val.DIRNAME + '</a>'+
		              '<span class="after"></span>' + 
		            '</li>'; 
		$("#secondDirUl").append(newli); 
	});
	$("#secondDirUl li:nth-child(1)").addClass("active");
	var initSDirId = $("#secondDirUl li:nth-child(1)").attr("dirId");
	addContentDir(initSDirId); 
	$('.sdir_a').click(function(){
		addContentDir(this.id); 
    });
};

var addApiDir = function(){
	$.ajax({
		 async : false,
		 type : "POST",
		 url :  "${APP_PATH}/doc/qrySecondDirList.shtml",
		 data : {
		 },
		 dataType : "json",
		 success : function(data) {
			 if(data){ 
				 var secondDirList = data.secondDirList;
				 var thirdDirList = data.thirdDirList;
				 appendApiDirHtml(secondDirList,thirdDirList);   
			 }
		 }
	});
};

var addSecondDir = function(dirId){
	$.ajax({
		 async : false,
		 type : "POST",
		 url :  "${APP_PATH}/doc/qrySecondDirList.shtml",
		 data : {
			 dirId : dirId
		 },
		 dataType : "json",
		 success : function(data) {
			 if(data && data.secondDirList){ 
				 var secondDirList = data.secondDirList;
				 appendSecondDirHtml(secondDirList);   
			 }
		 }
	});
};

var appendContentDirHtml = function(dirContentList){
	$("#dir_content").html('');
	$.each(dirContentList, function(key,val){
		var contentHtml = '<div class="panel-group" id="dir_content">'+
							'<div class="panel panel-default">'+
			                     '<div class="panel-heading">'+
			                        '<h4 class="panel-title"><a href="#content_dir_'+val.DIRID+'_'+val.HELPID+'" data-parent="#content_dir_'+val.DIRID+'" data-toggle="collapse" class="accordion-toggle">'+val.QUETITLE+'</a></h4>'+
			                      '</div>'+
			                      '<div class="panel-collapse collapse  in" id="content_dir_'+val.DIRID+'_'+val.HELPID+'">'+
			                         '<div class="panel-body">'+val.QUEASKCONTENT+'</div>'+
			                      '</div>'+
			                    '</div>'+
			                   '</div>';
		$("#dir_content").append(contentHtml); 
	});
};

var createApiTable = function(dirId,type){
	$("#dir_content").html('');
	var newTableHtml = 
	'<table class="table table-sl table-bordered table-striped table-hover text-center group-check" id="apiTable">'+
	    '<thead>'+
	       '<tr>'+
	          '<th class="table_td"> API Type </th>'+
	          '<th class="table_td"> API Name </th>'+
	          '<th class="table_td"> API Method Name </th>'+
	        '</tr>'+
	       '</thead>'+
	       '<tbody>'+
	       '</tbody>'+
           '</table>';
	$("#dir_content").append(newTableHtml); 
	$('#apiTable').dataTable({
        "processing": true,
        "serverSide": true,
        "ajax": APP_PATH+"/doc/qryAllApiList.shtml?dirId="+dirId+"&type="+type, 
        "ordering":  false,
        "autoWidth": false,
        "columns": [
                    { "data": "DIRNAME" },
                    { "data": "APINAME" },
                    { "data": "APIMETHOD" }
                ],
      "columnDefs": [{
                    "render": function(data, type, row) {
                	    var apiName = '<a href="${APP_PATH}/api/apiDoc.shtml?apiId='+row.APIID+'&apiName='+data+'">'+data+'</a>';
                        return apiName;
                    },
                    "targets": 1}],
        "initComplete": function() {
            App.ajaxInit();
        }
    });
    jQuery('#apiTable_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
    jQuery('#apiTable_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
};

var appendApiHtml = function(apiList){
	$("#dir_content").html('');
	var newTableHtml = '<table class="table table-sl table-bordered table-striped table-hover text-center group-check table_css">'+
	                         '<thead>'+
	                            '<tr>'+
	                               '<th class="table_td"> API Type </th>'+
	                               '<th class="table_td"> API Name </th>'+
	                               '<th class="table_td"> API Method Name </th>'+
	                             '</tr>'+
	                            '</thead>'+
	                            '<tbody>';
	$.each(apiList, function(key,val){
		var api_name = '<a class="view" data-original-title="API Name" href="${APP_PATH}/api/apiDoc.shtml?apiId='+val.APIID+'&apiName='+val.APINAME+'" data-placement="top" data-toggle="popover" title="" data-content="'+val.APINAME+'">'+val.APINAME+'</a>';
	    var api_method = '<a class="view" data-original-title="API Method Name" href="javascript:;" data-placement="top" data-toggle="popover" title="" data-content="'+val.APIMETHOD+'">'+val.APIMETHOD+'</a>';
	    newTableHtml += '<tr>'+
		                  '<td class="table_td"> '+val.DIRNAME+' </td>'+
		                  '<td class="table_td"> '+api_name+' </td>'+
		                  '<td class="table_td"> '+val.APIMETHOD+'</td>'+
		                 '</tr>';
	});
	newTableHtml += '</tbody>'+
	               '</table>';
	$("#dir_content").append(newTableHtml); 
};

var apiDir = function(apiId,apiName){
	$.ajax({
		  async : false,
		  type : "POST",
		  url :  "${APP_PATH}/api/apiDoc.shtml",  
		  data : {
			  apiId : apiId,
			  apiName : apiName
		  },
		  dataType : "json"
	});
};

var addApiTable = function(dirId,type){
	createApiTable(dirId,type);
};

var addContentDir = function(dirId){
	$.ajax({
		 async : false,
		 type : "POST",
		 url :  "${APP_PATH}/doc/qryDirContentList.shtml",
		 data : {
			 dirId : dirId
		 },
		 dataType : "json",
		 success : function(data) {
			 if(data && data.dirContentList){ 
				 var dirContentList = data.dirContentList;
				 appendContentDirHtml(dirContentList);   
			 }
		 }
	});
};


</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>