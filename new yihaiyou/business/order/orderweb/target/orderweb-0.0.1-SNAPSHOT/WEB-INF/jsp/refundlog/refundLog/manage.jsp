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
  <script type="text/javascript" src="/js/order/refundlog/refundManage.js"></script>
</head>
<body style="background-color: #ffffff">
<div id="cc" class="easyui-layout" fit:true style="width:100%;height:100%;">
  <div data-options="region:'center', fit:true, border:false">
      <div id="searchForm" style="padding: 10px;">

          <div style="margin-top: 5px;margin-bottom: 5px;">
              <input type="text" id="sea-orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-orderName" class="easyui-textbox" data-options="prompt:'订单名称'" style="width: 170px;margin-right: 10px;">
              <input type="text" id="sea-channel" class="easyui-combobox" data-options="prompt:'退款渠道',
                  valueField: 'id',
                  textField: 'value',
                  panelHeight: 50,
                  data: [{
                      id: 'weixin',
                      value: '微信'
                    },{
                        id: 'taobao',
                        value: '支付宝'
                    }]"  style="width: 150px;margin-right: 10px;">
          </div>
          <div style="margin-top: 5px;margin-bottom: 5px;">
              <input type="text" id="sea-status" class="easyui-combobox" data-options="prompt:'退款状态',
                  valueField: 'id',
                  textField: 'value',
                  panelHeight: 80,
                  data: [{
                      id: 'SUCCESS',
                      value: '成功'
                    },{
                        id: 'FAIL',
                        value: '失败'
                    },{
                        id: 'WAITING',
                        value: '待审核'
                    }]"  style="width: 150px;margin-right: 10px;">
              <input type="text" id="sea-rStartTime" class="easyui-datetimebox" data-options="prompt:'申请退款时间始'" style="width: 170px;">-
              <input type="text" id="sea-rEndTime"  class="easyui-datetimebox" data-options="prompt:'申请退款时间末'" style="width: 170px;margin-right: 15px;">
              <a href="#" class="easyui-linkbutton" onclick="RefundManage.doSearch()" >查询</a>
              <a href="#" class="easyui-linkbutton" onclick="RefundManage.doClear()" >重置</a>
          </div>
          <div>
              <a href="#" class="easyui-linkbutton" onclick="RefundManage.doWxRefund()" >微信退款</a>
              <a href="#" class="easyui-linkbutton" onclick="RefundManage.doRefund()" >支付宝批量退款</a>
          </div>
      </div>
      <div id="dg"></div>
  </div>
</div>
</body>
</html>
