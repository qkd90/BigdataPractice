<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>
    <script type="text/javascript" src="/js/outOrder/lineOrder/checkLineDetail.js"></script>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
  .labelName {
      font-size: 14px;
  }
  .labelValue {
      font-size: 14px;
  }
</style>
<body>
  <input id="ipt_id" type="hidden" name="id" value="${jszxOrder.id}">


  <div style="width: 100%; height: 100%">

      <div style="width: 100%;height: 5%;padding: 15px;">

          <div style="width: 50%;float: left">
              <label class="labelName">
                  订单：
              </label>
              <label class="labelValue">
                  ${jszxOrder.orderNo}
              </label>
              </div>
              <div style="width: 50%;float: right;">
              <input type="hidden" id="btn_status" value="${jszxOrder.btnStatus}">
              <input type="hidden" id="btn_refund_status" value="${jszxOrder.showRefund}">
              <input type="hidden" id="hipt_isconfim" value="${isConfim}">
              <div style="width: 45%; float: left;text-align: right;">
                  <a id="btn_confimOrder" onclick="CheckLineDetail.confimOrder()" class="easyui-linkbutton" >确认订单</a>
                  <a id="btn_applyCancel" onclick="CheckLineDetail.applyCancelDialog()" class="easyui-linkbutton" >申请取消</a>
              </div>
              <div style="width: 55%; float: right">
                  <a class="easyui-linkbutton" style="margin-left: 18px;" id="btn_msgCount" onclick="CheckLineDetail.sendMsgAgain()" >短信重发(<span id="msgCount">${jszxOrder.msgCount}</span>)</a>
                  <a class="easyui-linkbutton" style="margin-left: 18px;" onclick="CheckLineDetail.backParent()" >后退</a>
              </div>

          </div>

      </div>
      <div style="width: 100%;height: 55%;">

          <div style="width: 100%;height: 11%;padding: 15px 15px;background-color: #f7f7f7">

              <div style="width: 25%;height: 100%;float: left">

                  <div style="font-size: 12px;padding: 0px 0px 10px;">
                    ${jszxOrder.orderNo}
                  </div>
                  <div style="font-size: 16px;font-weight: bolder;">
                    ${jszxOrder.product.name}
                  </div>

              </div>
              <div style="width: 25%;height: 100%;float: left">

                  <div class="showByStatus" style="font-size: 12px;padding: 0px 0px 0px;">
                    创建日期：
                      <label id="payDateId" data-value="${jszxOrder.updateTime}"></label>
                  </div>
                  <div  style="font-size: 12px;margin-top: 2px;">
                    供应商：
                      <label>${jszxOrder.supplierUnit.name}</label>
                  </div>
                  <div  style="font-size: 12px;margin-top: 2px;">
                      供应商电话：
                      <label>${jszxOrder.supplierMobile}</label>
                  </div>

              </div>
              <div style="width: 25%;height: 100%;float: left" >

                  <div id="desc_source" class="showByStatus" style="font-size: 12px;padding: 0px 0px 10px;">
                      <div style="float: left; height: 100%;">备注：</div>
                      <div id="la_descId" style="height: 100%; float: right; width: 250px;" data-value="${jszxOrder.source}"></div>
                  </div>
              </div>
              <div style="width: 20%;height: 100%;float: right">

                  <div>
                      <label style="font-size: 14px;">订单状态：</label>
                        <input type="hidden" id="ipt_status" value="${jszxOrder.status}">
                        <label id="l_status" style="font-size: 16px;">
                      </label>
                  </div>

              </div>

          </div>

          <div style="width: 100%;height: 15%;padding: 15px 15px;">

              <div style="width: 70%; float: left">
                  <div style="padding: 10px 0px;">
                      <label style="font-size: 14px;font-weight: 700">联系人：</label>
                      <label style="font-size: 14px;font-weight: 700">${jszxOrder.contact}</label>
                  </div>
                  <div>
                      <label style="font-size: 14px;">电话：</label>
                      <label style="font-size: 14px;">${jszxOrder.phone}</label>
                  </div>
              </div>
              <div style="width: 30%;float:right">
                  <div style="padding: 10px 0px;">
                      <label style="font-size: 14px;font-weight: 700">总价：</label>
                      <label style="font-size: 14px;font-weight: 700">${jszxOrder.totalPrice}</label>
                  </div>
                  <div style="">
                      <label style="font-size: 14px;font-weight: 700">实际支出：</label>
                      <label style="font-size: 14px;font-weight: 700">${jszxOrder.actualPayPrice}</label>
                  </div>
              </div>
          </div>
          <div style="width: 100%;height: 50%;padding: 5px 15px;">

              <div style="width: 100%;height: 100%;">
                <table id="dg_ticketType"></table>
              </div>
          </div>
      </div>
      <div id="refund_layout" style="width: 100%;height: 30%;padding: 15px 15px;">

          <div style="width: 100%;height: 100%;">
            <table id="dg_ticketStatus"></table>
          </div>
      </div>
  </div>
  <div id="dialog_cancelOrder" class="easyui-dialog" title="取消订单" style="width:400px;height:200px;"
       data-options="resizable:false,modal:true,shadow:true">
      <div style="margin: 10px;height: 10px;" >
          订单:
          <label style="font-weight: 700">
              ${jszxOrder.orderNo}
          </label>
          <label style="font-weight: 700; margin-left: 20px; color: #cfcfc0">
              请选择需要取消的门票类型
          </label>
      </div>
      <div style="height: 200px;width: 100%;">
          <table id="dg_cancelOrder"></table>
      </div>
      <div style="width: 100%;margin-top: 3px;">
          <input id="ipt_desc" class="easyui-textbox"  data-options="multiline:true,prompt:'退票原因'" style="width:100%;height: 90px"/>
      </div>

  </div>


  <div id="confim_order" class="easyui-dialog" title="确认订单"
       data-options="closed:true">
      <iframe name="editIframe1" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:350px;"></iframe>
  </div>
</body>
</html>
