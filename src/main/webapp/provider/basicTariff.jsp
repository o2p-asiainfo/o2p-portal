<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Price Name:</label>
 <div class="col-md-8">
  <div class="form-control-static">Defualt Name</div>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Tax Type:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium">
   <option value="2">VAT inclusive</option>
   <option value="1">VAT exclusive</option>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Effective Time:</label>
 <div class="col-md-8 form-inline">
  <div data-date-format="mm/dd/yyyy" data-date-start-date="+0d" data-date="10/11/2012" class="input-group input-medium date-picker input-daterange" data-error-container="#error-container">
   <input type="text" name="from2" class="form-control od">
   <span class="input-group-addon"> To </span>
   <input type="text" name="to2" class="form-control od">
  </div>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Subject Type:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium">
   <option value="50014">MMS</option>
   <option value="50015">WLAN</option>
   <option value="50005">SMS</option>
   <option value="50006">ISMP</option>
   <option value="50001">VOICE</option>
   <option value="53001">GPRS</option>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Charging Unit:</label>
 <div class="col-md-8 form-inline">
  <input type="text" class="input-medium form-control" name="chargingUnitValue" value="1">
  <select class="form-control input-medium chargingUnit">
   <option value="109">Piece</option>
   <option value="108">Piece2</option>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Currency:</label>
 <div class="col-md-8 form-inline">
  <select class="form-control input-medium currency">
   <option value="10302">Euro</option>
   <option value="11403">DKK</option>
  </select>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Charge Type:</label>
 <div class="col-md-8 form-inline">
  <div class="radio-list" data-error-container="#here2">
   <label class="radio-inline">
    <input type="radio" value="1" checked="" name="ChargeType"> Simple </label>
   <label class="radio-inline">
    <input type="radio" value="2" name="ChargeType"> Ladder </label>
  </div>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label">
  <font color="FF0000">*</font>Charge:</label>
 <div class="col-md-8 form-inline">
  <div class="simple">
   <div class="input-group">
    <input type="text" name="" class="form-control">
    <span class="input-group-addon"> <span class="currencyArea">DKK</span>/<span class="numArea">3</span>*<span class="unitArea">Piece</span> </span>
   </div>
   <div class="input-group">
    <input type="text" name="" class="form-control" placeholder="Fixed Amount">
    <span class="input-group-addon"> <span class="currencyArea">DKK</span> </span>
   </div>
  </div>
  <table class="table table-bordered table-condensed table-advance ladder">
   <thead>
    <tr>
     <th> <i class="fa fa-plus"></i> </th>
     <th> Charge Arrange <span class="label label-yellow"> <span class="numArea">3</span>*<span class="unitArea">Piece</span> </span>
     </th>
     <th> Charge <span class="label label-yellow"> <span class="currencyArea">DKK</span>/<span class="numArea">3</span>*<span class="unitArea">Piece</span> </span>
     </th>
     <th> Recurring Charge <span class="label label-yellow"> <span class="currencyArea">DKK</span></span>
     </th>
     <th> Opertion </th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td>1</td>
     <td>
      <div class="input-group input-xs">
       <input type="text" name="from3" class="form-control">
       <span class="input-group-addon"> To </span>
       <input type="text" name="to3" class="form-control">
      </div>
      <td>
       <input type="text" name="" placeholder="" class="form-control input-xs">
      </td>
      <td>
       <input type="text" name="" placeholder="" class="form-control input-xs">
      </td>
      <td>
       <a class="btn default btn-sm black btn-del" href="#"> <i class="fa fa-trash-o"></i> Delete </a>
      </td>
    </tr>
    <tr>
   </tbody>
  </table>
 </div>
</div>
<div class="form-group">
 <label class="col-md-4 control-label"> Description :</label>
 <div class="col-md-8">
  <textarea rows="3" class="form-control"></textarea>
 </div>
</div>
  <p  class="text-center margin-top-10"><a class="btn theme-btn button-submit" href="javascript:;" id="reqNodeListSubmit">Save</a></p>
