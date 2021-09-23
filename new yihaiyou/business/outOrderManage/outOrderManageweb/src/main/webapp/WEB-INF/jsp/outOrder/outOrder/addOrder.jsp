<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>
    <%--<script language="javascript" type="text/javascript" src="/js/outOrder/Time/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="/js/outOrder/outOrder/addOrder.js"></script>
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
    .dialog_botton{
        margin-left: 100px;
        margin-top: 25px;
    }
</style>
<body>
<div class="row_hd">
    <div style="width: 70%;float: left;"><span>订单基本信息</span></div>
    <div style="width: 20%;float: left;text-align: center;">
        余额￥：
        <span style="color: crimson;font-weight: 800">

            <c:choose>
                <c:when test="${outOrder.user.balance == null || outOrder.user.balance == 0}">
                    0
                </c:when>
                <c:otherwise>
                    ${outOrder.user.balance}
                </c:otherwise>
            </c:choose>



        </span>
    </div>
    <div style="width: 10%;float: left;float: right;">
        <a class="easyui-linkbutton" style="margin-right: 18px;" onclick="AddOutOrder.backParent()" >后退</a>
    </div>
</div>
<form id="editForm" method="post">
  <input id="ipt_id" type="hidden" name="id" value="${outOrder.id}">
  <div class="coupon_class">
    <label class="label_class">订单类型:</label>
    <input id="com_orderType" class="easyui-combobox" name="proType" style="width:200px;" required="true" data-options="
          valueField: 'id',
          textField: 'value',
          data: [{
                  id: 'scenic',
                  value: '门票',
                  selected: true
              },{
                  id: 'line',
                  value: '线路'
              }]" />
  </div>
  <div class="coupon_class">
    <label class="label_class">产品名称:</label>
    <input class="easyui-combobox" style="width: 200px" required="true" name="product.id"
           id="cbb_productName"
           data-options="loader: AddOutOrder.productLoader,
									mode: 'remote',
									valueField: 'id',
									textField: 'name'">
  </div>

    <div id="ticketNo_list" style="margin-left: 115px;padding-top: 5px;">

        <div id="p" class="easyui-panel" title="票号列表"
             style="width:600px;height:300px;background:#fafafa;">
            <div id="tool_addTicketNo">
                <a class="easyui-linkbutton" onclick="AddOutOrder.openAddTicketNo()" >确定</a>
                <%--<a class="easyui-linkbutton"  onclick="AddOutOrder.openEditTicketNo()" >编辑</a>--%>
                <%--<a class="easyui-linkbutton"  onclick="AddOutOrder.openDelTicketNo()" >删除</a>--%>
            </div>
            <table id="dg_ticketList"></table>
        </div>
    </div>
    <div class="coupon_class">
        <label class="label_class">使用日期:</label>

        <input type="hidden" id="hipt_preOrderDay">
        <input type="hidden" id="hipt_validDay">
        <input type="text" value="" style="height: 23px;" id="ipt_beginDate" name="startTime" maxlength="20" required="true" placeholder="yyyy-mm-dd" >
        <span id="validSpan" style="margin-right: 20px;"></span>
        <%--<input id="ipt_beginDate" class="easyui-datetimebox" name="startTime" data-options="readonly:true" required="true" style="width:200px"/>--%>
        <%-------%>
        <%--<input id="ipt_endDate" class="easyui-datetimebox" name="endTime" data-options="readonly:true" required="true" style="width:200px"/>--%>
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
        <input id="ipt_source" class="easyui-textbox" name="source" data-options="multiline:true" required="true" style="width:200px; height:50px;"/>
    </div>
    <div class="coupon_class">
        <label class="label_class">总价:</label>
        <input id="ipt_totalPrice" class="easyui-textbox" name="totalPrice" data-options="readonly:true" required="true" style="width:200px"/>
    </div>
</form>
<div class="botton_class">
  <a class="easyui-linkbutton" onclick="AddOutOrder.saveOutOrder()" >提交</a>
  <a class="easyui-linkbutton" onclick="AddOutOrder.cancelOutOrder()" >取消</a>
</div>





</body>
</html>
