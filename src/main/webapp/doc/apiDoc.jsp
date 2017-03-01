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
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type='text/css'>
.divP{word-wrap: break-word;}
</style>	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->

<body class="page-header-fixed">

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN BREADCRUMBS -->
  <div class="row breadcrumbs">
    <div class="container">
      <div class="col-md-4 col-sm-4">
        <h1>API Document</h1>
      </div>
      <div class="col-md-8 col-sm-8">
        <ul class="pull-right breadcrumb">
          <li><a href="${APP_PATH}/index.shtml?tid=${tid}">Home</a> </li>
          <li class="active">API Document</li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END BREADCRUMBS --> 
  <!-- BEGIN CONTAINER -->
  <div class="container">
    <div class="row margin-bottom-40 margin-top-20">
      <div class="col-md-3">
        <ul class="ver-inline-menu tabbable margin-bottom-10">
          <li class="active"> <a data-toggle="tab" href="#tab_1">${apiName}</a></li>
        </ul>
      </div>
      <div class="col-md-9"> 
        <!-- BEGIN TAB CONTENT -->
        <div class="tab-content"> 
          <!-- START TAB 1 -->
          <div id="tab_1" class="tab-pane active">
            <div id="accordion1" class="panel-group">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_1"> Capability Description </a> </h4>
                </div>
                <div id="accordion2_1" class="panel-collapse collapse  in">
                  <div class="panel-body divP">
                    <p class="divP">${abilityIntro}</p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_2"> API User Authorization </a> </h4>
                </div>
                <div id="accordion2_2" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>  
	                    <c:if test="${apiUserGetType == 'Y'}">
							Required
						</c:if>
						<c:if test="${apiUserGetType == 'N'}">
							Not Required
						</c:if>	
                    </p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_3"> Request URL Address </a> </h4>
                </div>
                <div id="accordion2_3" class="panel-collapse collapse">
                  <div class="panel-body divP">
                    <p class="divP">${reqUrl}</p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_4"> HTTP Request Method </a> </h4>
                </div>
                <div id="accordion2_4" class="panel-collapse collapse">
                  <div class="panel-body divP">
                    <p class="divP">${httpQeq}</p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_5"> Response Support Format </a> </h4>
                </div>
                <div id="accordion2_5" class="panel-collapse collapse">
                  <div class="panel-body divP">
                    <p class="divP">${rspSupport}</p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_6"> System Level Request Parameter </a> </h4>
                </div>
                <div id="accordion2_6" class="panel-collapse collapse">
                  <div class="panel-body">
                    <table class="table table-sl table-bordered table-hover">
					  <thead>
						  <tr>			    
						    <th class="middle">Name</th>
						    <th class="middle">Type</th>
						    <th class="middle">Required Flag</th>
						    <th class="left">Example Value</th>
						    <th class="left">Description</th>
						  </tr>
					  </thead>
					  <tbody>
							<c:forEach items="${sysQeqList}" var="obj" varStatus="n">
								<tr class="odd">					
									<td class="middle">${obj.NONENAME}</td>
									<td class="middle">${obj.NODETYPE}</td>
								    <td class="left">${obj.ISNOT}</td>
								    <td class="left">${obj.NEVLCONSVALUE}</td>
								    <td class="left">${obj.DESCRIPTION}</td>
								</tr>
							</c:forEach>
					   </tbody>
			 		</table>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_7"> Application Level Request Parameter </a> </h4>
                </div>
                <div id="accordion2_7" class="panel-collapse collapse">
                  <div class="panel-body">
                    <table class="table table-sl table-bordered table-hover">
					  <thead>
						  <tr>
						    <th class="middle">Name</th>
						    <th class="middle">Type</th>
						    <th class="middle">Parent Node</th>
						    <th class="middle">Node path</th>
						    <th class="middle">Required Flag</th>
						    <th class="left">Example Value</th>
						    <th class="left">Description</th>
						  </tr>
					  </thead>
					  <tbody>					
						<c:forEach items="${applyReqList}" var="obj" varStatus="n">
							<tr class="odd">					
								<td class="middle" title="${obj.NONENAME}">${obj.NONENAME}</td>
								<td class="middle">${obj.NODETYPE}</td>
								<td class="middle" title="${obj.FNONENAME}">${obj.FNONENAME}</td>
								<td class="middle" title="${obj.NODEPATH}">${obj.NODEPATH}</td> 
							    <td class="left">${obj.ISNOT}</td>
							    <td class="left">${obj.NEVLCONSVALUE}</td>
							    <td class="left">${obj.DESCRIPTION}</td>
							</tr>
						</c:forEach>					
					</tbody>
			 		</table>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_8"> Request Example </a> </h4>
                </div>
                <div id="accordion2_8" class="panel-collapse collapse">
                  <div class="panel-body">
                     <p class="divP"><textarea style="width: 100%;height: 250px;">${reqInstance}</textarea></p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_9"> Application Level Return Result </a> </h4>
                </div>
                <div id="accordion2_9" class="panel-collapse collapse">
                  <div class="panel-body">
                    <table class="table table-sl table-bordered table-hover">
					  <thead>
						  <tr>
						    <th class="middle">Upper Level Name</th>
						    <th class="middle">Name</th>
						    <th class="middle">Type</th>
						    <th class="middle">Required Flag</th>
						    <th class="left">Example Value</th>
						    <th class="left">Description</th>		    
						  </tr>
					  </thead>
					  <tbody>	
						<c:forEach items="${appResult}" var="obj" varStatus="n">
							<tr class="odd">				
								<td class="middle">${obj.FANONENAME}</td>	
								<td class="middle">${obj.NONENAME}</td>
								<td class="middle">${obj.NODETYPE}</td>
							    <td class="left">${obj.ISNOT}</td>
							    <td class="left">${obj.NEVLCONSVALUE}</td>
							    <td class="left">${obj.DESCRIPTION}</td>
							</tr>
						</c:forEach>
					  </tbody>
	  			    </table>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_10"> Response Example </a> </h4>
                </div>
                <div id="accordion2_10" class="panel-collapse collapse">
                  <div class="panel-body divP">
                    <p class="divP"><textarea style="width: 100%;height: 250px;">${returnInstance}</textarea></p>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_11"> Error Code </a> </h4>
                </div>
                <div id="accordion2_11" class="panel-collapse collapse">
                  <div class="panel-body">
                    <table class="table table-sl table-bordered table-hover">
					  <thead>
						  <tr>
						  	<th class="middle">Error Type Code</th> 				  	
						    <th class="middle">Error Code</th>
						    <th class="middle">Error Type Description</th>
						    <th class="middle">Error Description</th>
						  </tr>
					  </thead>
					  <tbody>
						<c:forEach items="${errorCode}" var="obj" varStatus="n">
							<tr class="odd">				
								<td class="middle">${obj.TYPEID}</td>	
								<td class="middle">${obj.CODE}</td>
								<td class="middle">${obj.NAME}</td>
							    <td class="left">${obj.DESCRIPTOR}</td>	
							</tr>
						</c:forEach>
					 </tbody>
					</table>
                  </div>
                </div>
              </div>
              
            </div>
          </div>
          <!-- END TAB 1 --> 
        </div>
        <!-- END TAB CONTENT --> 
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
<!-- END COPYRIGHT --> 
<script type="text/javascript">
jQuery(document).ready(function() {
  App.init();
});

</script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
