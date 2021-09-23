<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/5/30
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp" %>
    <title></title>
  <script type="text/javascript" src="/js/order/order/shenZhouManage.js"></script>
</head>
<body style="background-color: #ffffff">
<div id="cc" class="easyui-layout" fit:true style="width:100%;height:100%;">
  <div data-options="region:'center', fit:true, border:false">
      <div id="searchForm" style="padding: 10px;">

          <div style="margin-top: 5px;margin-bottom: 5px;">
              <input type="text" id="sea-orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-serviceId" class="easyui-combobox" data-options="prompt:'服务类型',
                  valueField: 'id',
                  textField: 'text',
                  panelHeight: 'auto',
                  data: ShenZhouOrder.serviceId"  style="width: 100px;margin-right: 10px;">

              <input type="text" id="sea-status" class="easyui-combobox" data-options="prompt:'状态',
                  valueField: 'id',
                  textField: 'text',
                  panelHeight: 'auto',
                  data:ShenZhouOrder.status"  style="width: 110px;margin-right: 10px;">


              <input type="text" id="sea-paymentStatus" class="easyui-combobox" data-options="prompt:'订单状态',
              valueField: 'id',
              textField: 'text',
              panelHeight: 'auto',
              data:ShenZhouOrder.paymentStatus"  style="width: 100px;margin-right: 10px;">

              <input type="text" id="sea-passengerName" class="easyui-textbox" data-options="prompt:'乘客姓名'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-passengerMobile" class="easyui-textbox" data-options="prompt:'乘客手机号'" style="width: 170px;margin-right: 10px;">
              <div></div>
              <input type="text" id="sea-account" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-vehicleNo" class="easyui-textbox" data-options="prompt:'车牌号'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-rStartTime" class="easyui-datetimebox" data-options="prompt:'下单时间始'" style="width: 170px;">-
              <input type="text" id="sea-rEndTime"  class="easyui-datetimebox" data-options="prompt:'下单时间末'" style="width: 170px;margin-right: 15px;">
              <a href="#" class="easyui-linkbutton" onclick="ShenZhouOrder.doSearch()" >查询</a>
              <a href="#" class="easyui-linkbutton" onclick="ShenZhouOrder.doClear()" >重置</a>
          </div>
      </div>
      <div id="dg"></div>
  </div>
</div>
<!-- 编辑窗口 -->
<div id="editPanel" class="easyui-dialog" title=""
     data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
    <iframe name="editIframe" id="editIframe" scrolling="yes" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
</div>


</body>
</html>
