<!DOCTYPE html">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<title></title>

<!-- BEGIN HEAD -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- BEGIN GLOBAL MANDATORY STYLES -->
 <link href="${APP_PATH}/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
 <link href="${APP_PATH}/resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/css/rs-style.css" media="screen">
<link rel="stylesheet" href="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/css/settings.css" media="screen">
<link href="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${APP_PATH}/resources/css/style-template.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/themes/orange.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${APP_PATH}/resources/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${APP_PATH}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<script type="text/javascript">
  var APP_PATH = '/portal';  
  var RESULT_OK = "0000";
  var RESULT_ERR = "0001"; 
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">

<!-- BEGIN PAGE CONTAINER -->
<div class="page-container"> 
  <!-- BEGIN REVOLUTION SLIDER -->
  <div class="fullwidthbanner-container slider-main" style="height:446px;">
    <div class="fullwidthabnner">
      <ul id="revolutionul" style="display:none;">
        <!-- THE FIRST SLIDE -->
        <li data-transition="fade" data-slotamount="8" data-masterspeed="700" data-delay="9400" data-thumb="${APP_PATH}/assets/img/sliders/revolution/thumbs/thumb2.jpg"> 
          <!-- THE MAIN IMAGE IN THE FIRST SLIDE --> 
          <img src="${APP_PATH}/resources/img/sliders/revolution/bg1.jpg" alt="">
          <div class="caption lft slide_title slide_item_left" data-x="0" data-y="105" data-speed="400" data-start="1000" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.iWantToDev"]} </div>
          <div class="caption lft slide_subtitle slide_item_left" data-x="0" data-y="180" data-speed="400" data-start="1500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.registerDeveDesc1New"]} </div>
          <div class="caption lft slide_desc slide_item_left" data-x="0" data-y="220" data-speed="400" data-start="2000" data-easing="easeOutExpo"> 
            ${local["eaap.op2.portal.index.registerDeveDesc3New"]} <br>
            ${local["eaap.op2.portal.index.registerDeveDesc4New"]} </div>
          <a class="caption lft btn blue slide_btn slide_item_left" href="javascript:;" data-x="0" data-y="295" data-speed="400" data-start="2500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.readyGo"]} </a>
          <div class="caption lfb" data-x="640" data-y="25" data-speed="700" data-start="100" data-easing="easeOutExpo"> <img src="${APP_PATH}/resources/img/sliders/revolution/1.png" alt="Image 1"> </div>
        </li>
        
        <!-- THE SECOND SLIDE -->
        <li data-transition="fade" data-slotamount="7" data-masterspeed="300" data-delay="9400" data-thumb="${APP_PATH}/assets/img/sliders/revolution/thumbs/thumb2.jpg"> <img src="${APP_PATH}/resources/img/sliders/revolution/bg2.jpg" alt="">
          <div class="caption lft slide_title slide_item_left" data-x="0" data-y="125" data-speed="400" data-start="500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.iWantProv"]} </div>
          <div class="caption lft slide_subtitle slide_item_left" data-x="0" data-y="200" data-speed="400" data-start="1000" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.registerProvDesc1New"]} </div>
          <div class="caption lft slide_desc slide_item_left" data-x="0" data-y="245" data-speed="400" data-start="1500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.registerProvDesc2New"]} <br>
            ${local["eaap.op2.portal.index.registerProvDesc3New"]} </div>
          <a class="caption lft btn blue slide_btn slide_item_left" href="javascript:;" data-x="0" data-y="310" data-speed="400" data-start="2000" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.readyGo"]} </a>       
          <div class="caption lfr slide_item_right" data-x="520" data-y="0" data-speed="1200" data-start="2500" data-easing="easeOutExpo"> <img src="${APP_PATH}/resources/img/sliders/revolution/3.png" alt="Image 1"> </div>
        </li>
        <!-- THE FORTH SLIDE -->
        <li data-transition="fade" data-slotamount="8" data-masterspeed="700" data-delay="9400" data-thumb="${APP_PATH}/assets/img/sliders/revolution/thumbs/thumb2.jpg"> 
          <!-- THE MAIN IMAGE IN THE FIRST SLIDE --> 
          <img src="${APP_PATH}/resources/img/sliders/revolution/bg1.jpg" alt="">
          <div class="caption lft slide_title" data-x="0" data-y="105" data-speed="400" data-start="1500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.iWantPart"]} </div> 
          <div class="caption lft slide_subtitle" data-x="0" data-y="180" data-speed="400" data-start="2000" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.registerPartDesc1New"]} </div>
          <div class="caption lft slide_desc" data-x="0" data-y="225" data-speed="400" data-start="2500" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.registerPartDesc2New"]} <br>
           </div>
          <a class="caption lft btn blue slide_btn slide_item_left" href="javascript:;" data-x="0" data-y="290" data-speed="400" data-start="3000" data-easing="easeOutExpo"> ${local["eaap.op2.portal.index.readyGo"]} </a>
          <div class="caption lfr start" data-x="570" data-y="0" data-speed="300" data-start="1500" data-easing="easeOutBack"> <img src="${APP_PATH}/resources/img/sliders/revolution/2.png" alt="Image 2"> </div>
        </li>
      </ul>
      <!-- <div class="tp-bannertimer tp-bottom"></div> -->
    </div>
  </div>
  <!-- END REVOLUTION SLIDER --> 
  
  <!-- BEGIN CONTAINER -->
  <div class="container"> 
    <!-- BEGIN FEATURES BOX -->
    <div class="row features clearfix">
      
      <div class="text-center smalltitle"></div>
      <div class="carousel_nav text-center clearfix" style="width:192px;"> <h1 class="small text-center mb30 bold mt0 uppercase">Capability</h1> </div>
      <div class="col-md-3 text-center animated fadeInLeftNow notransition fadeInLeft">
        <div class="icon-box-top"> <i class="fa medium circle-white center fa fa-user"></i>
          <h2>${local["eaap.op2.portal.index.OperatorAsSalesChannel"]}</h2>
          <p>${local["eaap.op2.portal.index.OperatorAsSalesChannelConten"]}</p>
        </div>
      </div>
      <div class="col-md-3 text-center animated fadeInLeftNow notransition fadeInLeft">
        <div class="icon-box-top"> <i class="fa medium circle-white center fa-group "></i>
          <h2>${local["eaap.op2.portal.index.PartnerAsSalesChannel"]}</h2>
          <p>${local["eaap.op2.portal.index.PartnerAsSalesChannelConten"]}</p>
        </div>
      </div>
      <div class="col-md-3 text-center animated fadeInRightNow notransition fadeInRight">
        <div class="icon-box-top active"> <i class="fa medium circle-white center fa-search"></i>
          <h2>${local["eaap.op2.portal.index.CustomerInsight"]}</h2>
          <p>${local["eaap.op2.portal.index.CustomerInsightContent"]}</p>
        </div>
      </div>
      <div class="col-md-3 text-center animated fadeInRightNow notransition fadeInRight">
        <div class="icon-box-top"> <i class="fa medium circle-white center fa-shopping-cart"></i>
          <h2>${local["eaap.op2.portal.index.RealTimeCampaign"]}</h2>
          <p>${local["eaap.op2.portal.index.RealTimeCampaignConten"]}</p>
        </div>
      </div>
      <!-- END FEATURES BOX --> 
    </div>
    <!-- END CONTAINER-->
 <!--    <div class="clearfix"></div> -->
    <!-- BEGIN RECENT WORKS -->
<!--     <div class="row recent-work margin-bottom-40 mt0">
      <h1 class="small text-center mb30 bold mt0 uppercase"><a href="#" style="color:#333">${local["eaap.op2.portal.index.successcase"]}</a> </h1>      
      <div class="text-center smalltitle"></div>
      <div class="carousel_nav text-center mb30 clearfix"> <span href="#" id="car_prev"></span> <span href="#" id="car_next"></span> </div>
      <div class="col-md-12" id="detailCase">
        <ul class="bxslider">
        </ul>
      </div>
    </div> -->
    <!-- END RECENT WORKS --> 
  </div>
    <div class="c2">
    <div class="container">
      <div class="clearfix"></div>
      <!-- BEGIN RECENT WORKS -->
      <div class="row recent-work margin-bottom-40">
        <h1 class="small text-center bold mb30 uppercase"> PARTNER SUCCESSFUL CASES </h1>

        <div class="text-center smalltitle"></div>
        <div class="carousel_nav text-center mb30 clearfix"> <span href="#" id="car_prev"></span> <span href="#" id="car_next"></span> </div>
        <div class="col-md-12">
          <ul class="bxslider opr-items">
            <li>
              <a href="#">
                <figure>
                  <img src="resources/img/clients/aws.jpg" />                 
                   <h1 class="title clear"><div class="pull-left"><i class="fa fa-users"></i> <b>1 million</b></div><div class="pull-right"><i class="fa fa-dollar"></i> <b>342,129</b></div></h1>
                  <div class="description">
                    <p>Amazon Web Services (AWS), is a collection of cloud computing services, also called web services, that make up a cloud-computing platform offered by Amazon.com. </p>
                  </div>
                </figure>
              </a>
            </li>
            <li>
              <a href="#">
                <figure>
                  <img src="resources/img/clients/baidu.jpg" />
                  <h1 class="title clear">
                  <div class="pull-left"><i class="fa fa-users"></i> <b>2 million</b></div><div class="pull-right"><i class="fa fa-dollar"></i> <b>551,129</b></div>
                  </h1>
                  <div class="description">
                    <p>Baidu, Inc. is a Chinese web services company. Baidu offers many services, including a Chinese language-search engine for websites, audio files and images.</p>

                  </div>
                </figure>
              </a>
            </li>
            <li>
              <a href="#">
                <figure>
                  <img src="resources/img/clients/evernote.jpg" />
                   <h1 class="title clear"><div class="pull-left"><i class="fa fa-users"></i> <b>2129</b></div><div class="pull-right"><i class="fa fa-dollar"></i> <b>221,129</b></div></h1>
                  <div class="description">
                    <p>Evernote Corporation provides a cross-platform, freemium app designed for note taking, organizing, and archiving. It is a private company headquartered in Redwood City, California.</p>
                  </div>
                </figure>
              </a>
            </li>
            <li>
              <a href="#">
                <figure>
                  <img src="resources/img/clients/airbnb.jpg" />
                   <h1 class="title clear"><div class="pull-left"><i class="fa fa-users"></i> <b>1129</b></div><div class="pull-right"><i class="fa fa-dollar"></i> <b>321,129</b></div></h1>
                  <div class="description">
                    <p>Airbnb is a trusted community marketplace for people to list, discover, and book unique accommodations around the world — online or from a mobile phone or tablet.</p>
                  </div>
                </figure>
              </a>
            </li>
            <li>
              <a href="#">
                <figure>
                  <img src="resources/img/clients/uber.jpg" />
                   <h1 class="title clear"><div class="pull-left"><i class="fa fa-users"></i> <b>1129</b></div><div class="pull-right"><i class="fa fa-dollar"></i> <b>381,129</b></div></h1>
                  <div class="description">
                    <p>Uber Technologies Inc. is an American international transportation network company. The company develops, markets and operates the Uber mobile app, which allows consumers with smartphones to submit a trip request which is then routed to Uber drivers who use their own cars.</p>

                  </div>
                </figure>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <!-- END RECENT WORKS -->
    </div>
  </div>
  <div class="o2p-main-partner c2">
    <div class="container mt40 mb30">
      <div class="text-center smalltitle"></div>
      <div class="carousel_nav text-center clearfix" style="width:432px;">
        <h1 class="small text-center mb30 bold mt0 uppercase">OPERATOR SUCCESSFUL CASES</h1> </div>
      <div class="y-row">
        <div class="o2p-main-partner-box ">
          <div class="o2p-main-partner-img y-clear">
            <div class="active">
              <a href="javascript:void(0)" data-content="<span class='themeColor'>Telenor</span> is one of the world's largest mobile telecommunication companies with operations in Scandinava, Eastern Europe and Asia. It has more than 10 key partners including：Global compact, UNICEF, Red Cross, GSMA."><img src="resources/img/clients/telenor.png"> </a>
            </div>
            <div>
              <a href="javascript:void(0)" data-content="<span class='themeColor'>Reliance Industries Limited (RIL)</span> is an Indianconglomerate holding company. Reliance owns businesses across India engaged in energy, petrochemicals, textiles, natural resources, retail and telecommunications."><img src="resources/img/clients/reliance.png"> </a>
            </div>
            <div>
              <a href="javascript:void(0)" data-content="<span class='themeColor'>China Telecommunications Corporation</span> is a Chinese state-owned telecommunication company. It is the largest fixed-line service and the third largest mobile telecommunication provider in the People's Republic of China."><img src="resources/img/clients/China_Telecom.png"> </a>
            </div>
            <div>
              <a href="javascript:void(0)" data-content="<span class='themeColor'>Advanced Info Services Plc. (AIS)</span> is the leading mobile operator in Thailand. Its core businesses include domestic mobile services, international direct dialling (IDD) services, and international roaming services."><img src="resources/img/clients/ais.png"> </a>
            </div>
            <div>
              <a href="javascript:void(0)" data-content="<span class='themeColor'>China United Network Communications Group Co.</span>, Ltd. is a Chinese state-owned telecommunications operator in the People's Republic of China. China Unicom is the world's fourth largest mobile service provider."><img src="resources/img/clients/china_unicom.png"> </a>
            </div>
            <div>
              <a href="javascript:void(0)" data-content="<span class='themeColor'>China Mobile Communications Corporation</span> is a Chinese state-owned telecommunication company that provides mobile voice and multimedia services. The company is the largest mobile telecommunications company and the world's largest mobile phone operator."><img src="resources/img/clients/china_mobile.png"> </a>
            </div>
          </div>
          <div class="o2p-main-partner-tip">
            <p id="opr-intro"><span class="themeColor">Telenor</span> is one of the world's largest mobile telecommunication companies with operations in Scandinava, Eastern Europe and Asia. It has more than 10 key partners including：Global compact, UNICEF, Red Cross, GSMA.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END CONTAINER --> 
</div>

<!-- END PAGE CONTAINER --> 
<!-- BEGIN COPYRIGHT -->
<%@include file="footer.jsp" %>
<!-- END COPYRIGHT -->
<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) --> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/fancybox/source/jquery.fancybox.pack.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/bxslider/jquery.bxslider.min.js"></script> 
<script type="text/javascript" src="${APP_PATH}/resources/plugins/jquery-validation/jquery.validate.min.js" ></script>
<script src="${APP_PATH}/resources/scripts/index.js"></script> 
<script type="text/javascript">
 jQuery(document).ready(function() {
	  App.init();
	  App.initBxSlider();
    Index.init();
    handCaseInitialise();
function handCaseInitialise() {
    $("#detailCase ul").empty();
    $.ajax({
        async: false,
        type: "POST",
        url: "${APP_PATH}/doc/querySucessCaseIndexList.shtml",
        data: {},
        dataType: "json",
        success: function(data) {
            $.each(data, function(key, val) {
                var newli = '<li>' + '<em>' + '<img src="${APP_PATH}/fileShare/readImg.shtml?sFileId=' + val.FIL_S_FILE_ID + '" alt=""/>' + '</em>' + '<a class="bxslider-block" href="javascript:;">' + '<strong>' + val.ORG_USERNAME + '</strong>' + '<b>' + val.CAPABILITY + '</b> ' + '</a>' + '</li>';
                $("#detailCase ul").append(newli);
            });
        }
    });
};

 });

 </script> 
<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
