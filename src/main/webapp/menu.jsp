<!-- BEGIN HEADER -->
<div class="header navbar navbar-default navbar-static-top">
  <div class="container">
    <!-- BEGIN TOP NAVIGATION MENU -->
    <c:choose>
    	<c:when test="${userBean.name==null || userBean.name==''}">
    		<div class="navbar-header"> 
      			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
      			<button class="navbar-toggle btn navbar-btn" data-toggle="collapse" data-target=".navbar-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      			<!-- END RESPONSIVE MENU TOGGLER --> 
      			<!-- BEGIN LOGO (you can use logo image instead of text)--> 
      			<a class="navbar-brand logo-v1" href="${APP_PATH}/index.shtml?tid=${tid}"  target="mianframe">
		      		<c:choose>
		      		    <c:when test="${not empty tenantLogo }">
							<img src="${APP_PATH}/resources/tenant/${tenantLogo}" id="logoimg" alt="" height="50">
						</c:when>
						 <c:otherwise>
							<img src="${APP_PATH}/resources/img/${contextStyleSpecial }/logo${localeName }_super.png" id="logoimg" alt="" height="50">
						</c:otherwise>
					</c:choose>
				</a> 
      			<!-- END LOGO --> 
    		</div>
		    <div class="navbar-collapse collapse">
		      <ul class="nav navbar-nav">
		        <li <c:if test="${navBarPageId=='Home'}"> class="active" </c:if>> <a href="${APP_PATH}/index.shtml?tid=${tid}" target="mianframe"> ${local["eaap.op2.portal.index.index"]} </a> </li>
		        <li class="dropdown <c:if test="${navBarPageId=='Guide'}"> active </c:if>"> <a href="#" data-close-others="false" data-delay="0" data-hover="dropdown" data-toggle="dropdown" class="dropdown-toggle"> ${local["eaap.op2.portal.index.guide"]} <i class="fa fa-angle-down"></i> </a>
		          <ul class="dropdown-menu">
		            <li><a href="${APP_PATH}/developerGuide.shtml?tid=${tid}"  target="mianframe">${local["eaap.op2.portal.index.devGuide"]}</a></li>
		            <li><a href="${APP_PATH}/providerGuide.shtml?tid=${tid}"  target="mianframe">${local["eaap.op2.portal.index.proGuide"]}</a></li>
		            <li><a href="${APP_PATH}/partnerGuide.shtml?tid=${tid}" target="mianframe">${local["eaap.op2.portal.index.parGuide"]}</a></li>
		          </ul>
		        </li>
		        <li <c:if test="${navBarPageId=='Doc'}"> class="active" </c:if>><a href="${APP_PATH}/api/documention.shtml?tid=${tid}"  target="mianframe">${local["eaap.op2.portal.index.docIndex"]}</a></li> 
		        <li <c:if test="${navBarPageId=='Support'}"> class="active" </c:if>> <a href="${APP_PATH}/api/support.shtml?tid=${tid}"  target="mianframe"> ${local["eaap.op2.portal.index.supportIndex"]} </a> </li>         
		        <li class="s-login-link">
		        <span class="sep"></span>
		          <a href="${APP_PATH}/org/tologin.shtml?tid=${tid}"  target="mianframe">${local["eaap.op2.portal.index.login"]}</a> | <a href="${APP_PATH}/org/reg.shtml?tid=${tid}"  target="mianframe">${local["eaap.op2.portal.index.signUp"]}</a>
		        </li>
		      </ul>
		    </div>
    	</c:when>
    	<c:otherwise>
	    	<!-- BEGIN HEADER -->
		
			    <div class="navbar-header"> 
			      <!-- BEGIN RESPONSIVE MENU TOGGLER -->
			      <button class="navbar-toggle btn navbar-btn" data-toggle="collapse" data-target=".navbar-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
			      <!-- END RESPONSIVE MENU TOGGLER --> 
			      <!-- BEGIN LOGO (you can use logo image instead of text)--> 
			      <a class="navbar-brand logo-v1" href="${APP_PATH}/index.shtml"  target="mianframe">
			      	<c:choose>
		      		    <c:when test="${not empty tenantLogo }">
							<img src="${APP_PATH}/resources/tenant/${tenantLogo}" id="logoimg" alt="" height="50">
						</c:when>
						 <c:otherwise>
							  <img src="${APP_PATH}/resources/img/${contextStyleSpecial }/logo${localeName }_super.png" id="logoimg" alt="" height="50">
						</c:otherwise>
					</c:choose>
				 </a> 
			      <!-- END LOGO --> 
			    </div>
			    <!-- BEGIN TOP NAVIGATION MENU -->
			    <div class="navbar-collapse collapse">
			      <ul class="nav navbar-nav">
			        <li <c:if test="${navBarPageId=='Home'}"> class="active" </c:if>><a href="${APP_PATH}/index.shtml"  target="mianframe"> ${local["eaap.op2.portal.index.index"]} </a></li>
			        <li class="dropdown  <c:if test="${navBarPageId=='MyCenter'}"> active </c:if>"> <a href="#"> ${local["eaap.op2.portal.index.manageCenter"]} <i class="fa fa-angle-down"></i></a>
			          <ul class="dropdown-menu" aria-labelledby="mega-menu" >
			            <li>
			              <div class="nav-content"> 
			                <!-- BEGIN DROPDOWN MENU - COLUMN -->
			                <div class="nav-content-col">
			                  <h3>${local["eaap.op2.portal.index.devIndex"]}</h3>
			                  <ul>
			                    <li><a href="${APP_PATH}/mgr/developerCenter.shtml"  target="mianframe"> ${local["eaap.op2.portal.index.myApp"]} </a> </li>
			                    <li><a href="${APP_PATH}/mgr/manageDevMgrAdd.shtml"  target="mianframe"> ${local["eaap.op2.portal.index.createApp"]} </a> </li>
			                    <!-- li><a href="${APP_PATH}/mgr/statistics.shtml"  target="mianframe"> ${local["eaap.op2.portal.index.statistics"]} </a> </li -->

			                  </ul>
			                </div>
			                <!-- END DROPDOWN MENU - COLUMN --> 
			                <!-- BEGIN DROPDOWN MENU - COLUMN -->
			                <div class="nav-content-col">
			                  <h3>${local["eaap.op2.portal.index.provIndex"]}</h3>
			                  <ul>
			                    <li><a href="${APP_PATH}/provider/mySystem.shtml"  target="mianframe">${local["eaap.op2.portal.index.mySystem"]}</a></li>
			                    <li><a href="${APP_PATH}/provider/createSystem.shtml"  target="mianframe">${local["eaap.op2.portal.index.createSystem"]}</a></li>
			                  </ul>
			                </div>
			                <!-- END DROPDOWN MENU - COLUMN --> 
			                <!-- BEGIN DROPDOWN MENU - COLUMN -->
			                <div class="nav-content-col">
			                  <h3>${local["eaap.op2.portal.index.pradIndex"]}</h3>
			                  <ul>
			                  	<li><a href="${APP_PATH}/pardSpec/toWinIndex.shtml"  target="mianframe"> ${local["eaap.op2.portal.pardSpec.attrSpec"] }</a></li>
			                  	<li><a href="${APP_PATH}/pardServiceSpec/serviceSpecIndex.shtml" target="mianframe"> ${local["eaap.op2.portal.index.serviceSpec"]} </a> </li>
			                  	<!-- 
			                  	<li><a href="${APP_PATH}/pardServiceSpec/toServiceSpecAddOrUpdate.shtml" target="mianframe"> ${local["eaap.op2.portal.index.addServiceSpec"]}</a> </li>
			                     -->
			                    <li><a href="${APP_PATH}/pardProduct/toIndex.shtml"  target="mianframe">${local["eaap.op2.portal.index.product"]}</a></li>
			                    <!-- 
			                    <li><a href="${APP_PATH}/pardProduct/toAdd.shtml"  target="mianframe">${local["eaap.op2.portal.index.addProduct"]}</a></li>
			                     -->
			                    <li><a href="${APP_PATH}/pardOffer/toIndex.shtml"  target="mianframe">${local["eaap.op2.portal.index.productOffer"]}</a></li>
			                    <!-- 
			                    <li><a href="${APP_PATH}/pardOffer/toAdd.shtml"  target="mianframe">${local["eaap.op2.portal.index.addProductOffer"]}</a></li>
			                     -->
			                    <li><a href="${APP_PATH}/orderManager/toIndex.shtml"  target="mianframe">${local["eaap.op2.portal.index.orderManager"]}</a></li>
			                      <li><a href="forward.jsp"  target="mianframe">${local["eaap.op2.portal.index.360view"]}</a></li>
<!-- 			                    <li><a href="saleablePorduct.jsp">Saleable Product</a></li> -->
<!-- 			                    <li><a href="transactionRecords.jsp">Transaction Records</a></li> -->
<!-- 			                    <li><a href="settlement.jsp">Settlement</a></li> -->
			                  </ul>
			                </div>
			                <!-- END DROPDOWN MENU - COLUMN --> 
			              </div>
			            </li>
			          </ul>
			        </li>
			        <li class="dropdown  <c:if test="${navBarPageId=='Guide'}"> active </c:if>" > <a href="#" data-close-others="false" data-delay="0" data-hover="dropdown" data-toggle="dropdown" class="dropdown-toggle"> ${local["eaap.op2.portal.index.guide"]} <i class="fa fa-angle-down"></i> </a>
			          <ul class="dropdown-menu">
			            <li><a href="${APP_PATH}/developerGuide.shtml"  target="mianframe">${local["eaap.op2.portal.index.devIndex"]}</a> </li>
			            <li><a href="${APP_PATH}/providerGuide.shtml"  target="mianframe">${local["eaap.op2.portal.index.provIndex"]}</a> </li>
			            <li><a href="${APP_PATH}/partnerGuide.shtml"  target="mianframe">${local["eaap.op2.portal.index.pradIndex"]}</a> </li>

			          </ul>
			        </li>
			        <li <c:if test="${navBarPageId=='Doc'}"> class="active" </c:if>><a href="${APP_PATH}/api/documention.shtml"  target="mianframe">${local["eaap.op2.portal.index.docIndex"]}</a></li>
			        <li <c:if test="${navBarPageId=='Support'}"> class="active" </c:if>> <a href="${APP_PATH}/api/support.shtml"  target="mianframe"> ${local["eaap.op2.portal.index.supportIndex"]} </a> </li>     
			        <li class=" dropdown <c:if test="${navBarPageId=='UserInfo'}">active </c:if>"> <span class="sep"></span> <a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false" href="#"> ${userBean.name} <i class="fa fa-sort-down"></i></a>
			          <ul class="dropdown-menu s-user-box">
			            <li><a href="${APP_PATH}/org/userInfo.shtml"  target="mianframe"><i class="fa fa-user"></i>${local["eaap.op2.portal.index.userInfo"]}</a> </li>
			            <li><a href="${APP_PATH}/org/logout.shtml"  target="mianframe" ><i class="fa fa-sign-out"></i>${local["eaap.op2.portal.index.signOut"]}</a> </li>
			          </ul>
			        </li>
			      </ul>
			    </div>
			    <!-- BEGIN TOP NAVIGATION MENU --> 
			<!-- END HEADER --> 
        </c:otherwise>
    </c:choose>
    <!-- BEGIN TOP NAVIGATION MENU --> 
  </div>
</div>
<!-- END HEADER --> 