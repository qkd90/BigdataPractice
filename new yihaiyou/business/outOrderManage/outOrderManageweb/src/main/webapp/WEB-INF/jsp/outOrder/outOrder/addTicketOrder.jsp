<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>


    <%--<script language="javascript" type="text/javascript" src="/js/outOrder/Time/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="/js/outOrder/outOrder/addTicketOrder.js"></script>
    <%--<script type="text/javascript" src="/js/outOrder/outOrder/ticket.js"></script>--%>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
  .row_hd {
    height: 27px;
    width: 100%;
    line-height: 27px;
    border-bottom: 1px solid #ddd;
    background: #f4f4f4;
    margin: 5px 15px 10px 15px;
    padding-left: 10px;
    font-weight: 700;
    color: #666;
  }
  .label_class {
    width: 80px;
    float: left;
    text-align: right;
    margin-right: 15px;
  }
  .coupon_class {
    margin-top: 5px;
    margin-left: 20px;
  }
  .botton_class {
    margin-top: 22px;
    margin-left: 122px;
  }
  .botton_class a{
    margin-right: 20px;
  }
    .dialog {
        margin-left: 5px;
    }

  .calender_class {
      /*padding-right: 20px;*/
      border: #999 1px solid;
      width: 117px;
      display: none;
      margin-left: -4px;
      height: 24px;
      background: url("/jquery-easyui-1.4.1/themes/icons/date.png") no-repeat scroll right center transparent;
  }
  .count_class {
      width: 105px;
      display: none;
      height: 22px;
  }
</style>
<body>
<div class="row_hd">
    <div style="width: 80%;float: left;"><span>订单基本信息</span></div>
    <div style="width: 20%;float: left;text-align: center;">
        余额￥：
        <span style="color: crimson;font-weight: 800">

            <c:choose>
                <c:when test="${sysUser.balance == null || sysUser.balance == 0}">
                    0
                </c:when>
                <c:otherwise>
                    ${sysUser.balance}
                </c:otherwise>
            </c:choose>



        </span>
    </div>
</div>
<form id="editForm" method="post">
      <input id="hipt_orderId" type="hidden">
    <input id="ipt_ticket" type="hidden" value="${ticket.id}">
      <input type="hidden" id="hipt_ticketType" value="${ticket.ticketType}">
    <input type="hidden" id="hipt_ticketConfim" value="${line.orderConfirm}">
      <input type="hidden" id="hipt_proType" value="${ticket.proType}">
      <input type="hidden" id="hipt_validDay" value="${ticket.validOrderDay}">
  <div class="coupon_class">
    <label class="label_class">订单类型:</label>
    <label id="la_ticketType">
    </label>
  </div>
  <div class="coupon_class">
    <label class="label_class">产品名称:</label>
    <label>${ticket.name}</label>
  </div>
    <div class="coupon_class">
        <label class="label_class">联系人:</label>
        <input id="ipt_contact" class="easyui-textbox" name="contact" data-options="" required="true" style="width:200px"/>
    </div>
    <div class="coupon_class">
        <label class="label_class">联系电话:</label>
        <input id="ipt_phone" class="easyui-textbox" name="phone" data-options="validType:'mobile'" required="true" style="width:200px"/>
    </div>
    <div class="coupon_class">
        <label class="label_class">备注:</label>
        <input id="ipt_source" class="easyui-textbox" name="source" data-options="multiline:true, validType:'maxLength[500]'" style="width:620px; height:80px;"/>
    </div>
  <div id="ticketNo_list" style="margin-left: 115px;padding-top: 5px;width:620px;height:300px;">
        <%--<div id="p" class="easyui-panel" title="票号列表"--%>
             <%--style="width:600px;height:300px;background:#fafafa;">--%>
            <table id="dg_ticketList"></table>
        <%--</div>--%>
  </div>
    <div class="coupon_class">
        <label class="label_class">总价:</label>
        <input id="ipt_totalPrice" class="easyui-numberbox" name="totalPrice" data-options="readonly:true, precision:2" required="true" style="width:200px"/>
    </div>
</form>
<div class="botton_class">
  <a class="easyui-linkbutton" onclick="AddTicketOrder.saveOutOrder()" >提交</a>
  <a class="easyui-linkbutton" onclick="AddTicketOrder.cancelOutOrder()" >取消</a>
</div>

<div id="sel_startTime" class="easyui-dialog" title="价格日历" style="width:350px;height:450px;"
     data-options="resizable:true,modal:true,closed:true">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:330px;"></iframe>
    <input type="hidden" id="hipt_startTime_index"/>
</div>
<div id="confim_order" class="easyui-dialog" title="确认订单"
     data-options="closed:true">
    <iframe name="editIframe1" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:350px;"></iframe>
</div>

<div id="editPanel" class="easyui-dialog"
     data-options="closed:true">
    <iframe name="editIframe1" id="recharge_iframe" scrolling="no" frameborder="0"  style="width:100%;height:350px;"></iframe>
</div>



</body>
</html>
