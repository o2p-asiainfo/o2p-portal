<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
  <h4 class="modal-title">Price Plan</h4>
</div>
<div class="modal-body form-horizontal"> 
  <!-- BEGIN SECTION 1 -->
  <div class="section clearfix">
    <h3 class="form-section mt5">Base Information</h3>
    <div class="form-body">
      <div class="form-group">
        <label class="col-md-4 control-label"> <font color="FF0000">*</font>Price Name:</label>
        <div class="col-md-8">
          <input type="text" name="priceName" placeholder="" class="form-control input-medium">
        </div>
      </div>
      <div class="form-group">
        <label class="col-md-4 control-label"><i title="Pricing plan Effect type,The pricing plan is effective immediately or in the specified period of time after the entry into force of the offset." data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>Offset Type:</label>
        <div class="col-md-8 form-inline">
          <select class="form-control offsetType">
            <option value="1">Immediately</option>
            <option value="2">Offset</option>
          </select>
          <input type="text" class="form-control input-xsmall offset" placeholder="1">
          <select class="form-control offset">
            <option value="3">By Billing cycle</option>
            <option value="2">By Month</option>
            <option value="1">By Day</option>
            <option value="4">By Hour</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-md-4 control-label"><i title="Effective length effective pricing plans, if choose the type of Fixed expiry date, it said that in this period of time." data-placement="top" data-toggle="tooltip" class="fa fa-question-circle"></i> <font color="FF0000">*</font>Effective Time:</label>
        <div class="col-md-8 form-inline" id="effectiveTime">
          <input type="text" class="form-control input-medium effectiveTime1" placeholder="1">
          <div class="input-group input-medium date date-picker effectiveTime2" data-date-format="dd-mm-yyyy" data-date-start-date="+0d" style="display:none;">
            <input type="text" class="form-control" readonly>
            <span class="input-group-btn">
            <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
            </span> </div>
          <select class="form-control unit">
            <option value="1">Month(s)</option>
            <option value="2">Week(s)</option>
            <option value="3">Billing Cycle</option>
            <option value="4">Calendar Week</option>
            <option value="5">Calendar Day</option>
            <option value="6">Hour</option>
            <option value="7">Year(s)</option>
            <option value="8">Calendar Year</option>
            <option value="9">Calendar Month</option>
            <option value="10">Fixed Expiry Date</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-md-4 control-label"> <font color="FF0000">*</font>Price Object Product:</label>
        <div class="col-md-8 form-inline">
          <div class="input-icon right input-large"> <i class="glyphicon glyphicon-tree-deciduous"></i>
            <input type="text" class="form-control input-large" data-hover="dropdown" >
            <div class="dropdown-menu jstree" role="menu">
              <div class="jstree"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-md-4 control-label"> <font color="FF0000">*</font>Priority :</label>
        <div class="col-md-8 form-inline">
          <input type="text" class="form-control input-medium" placeholder="500">
        </div>
      </div>
    </div>
  </div>
   <p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="reqNodeListSubmit">Save</a></p>
  <!-- END SECTION 1 --> 
  <!-- BEGIN SECTION 2 -->
  <div class="portlet">
    <div style="margin:30px 0 25px!important;" class="portlet-title">
      <div style="font-size: 24px; font-weight: 300 ! important;" class="caption"> Charge Information </div>
      <div class="actions dropdown"> <a class="btn default btn-select" href="javascript:;"><i class="fa fa-check-square-o"></i> Select </a> <a class="btn default btn-delete" href="javascript:;"><i class="fa fa-trash-o"></i> Delete </a> <a class="btn default" data-toggle="dropdown" href="javascript:;"> Add <i class="fa fa-angle-down"></i></a>
        <ul class="dropdown-menu pull-right tpl" role="menu" data-set="addTarget">
          <li> <a data-url="../provider/basicTariff.shtml" href="javascript:;"  data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="1"> Basic Tariff </a> </li>
          <li> <a data-url="../provider/billingDiscount.shtml" href="javascript:;" data-action="addTabMenu" data-container=".tabsArea" data-draw="0" data-type="2">Billing Discount</a> </li>
        </ul>
      </div>
    </div>
    <div class="portlet-body tabsArea" data-addtpl="false">
      <div class="well">您还未填写'Charge Information'，请点击右上方的添加按钮</div>
    </div>
  </div>
  <!-- END SECTION 2 --> 
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
  <button type="button" class="btn theme-btn">OK</button>
</div>


