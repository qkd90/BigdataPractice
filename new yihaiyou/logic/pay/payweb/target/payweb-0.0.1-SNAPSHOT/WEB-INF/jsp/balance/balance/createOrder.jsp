<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/22
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp" %>
    <title>支付日志查询</title>
    <style type="text/css">
        .row_hd {
            height: 30px;
            width: 100%;
            line-height: 30px;
            border-bottom: 1px solid #ddd;
            background: #f4f4f4;
            /*margin: 5px 15px 10px 15px;*/
            /*padding-left: 10px;*/
            font-weight: 900;
            font-family: "Consolas", "Monaco", "Bitstream Vera Sans Mono", "Courier New", Courier, monospace;
            color: #666;
        }
        .form_div {
            padding: 10px 20px;
        }
        .form_label {

        }
        .btn_pre {
            padding: 20px 80px;
        }
        .btn_pre a{
            margin-right: 20px;
        }

    </style>
    <script src="/js/balance/createOrder.js" type="text/javascript"></script>
</head>
<body style="background-color: white">

<div class="row_hd">
    <div style="width: 10%;float: left;color: #0092DC"><span>提交申请>></span></div>
    <div style="width: 10%;float: left;"><span>确认充值>></span></div>
    <div style="width: 10%;float: left;"><span>充值>></span></div>
    <div style="width: 10%;float: left;"><span>充值成功</span></div>
</div>

<div style="padding: 20px">
    <h2 style="font-size: x-large;">充值中心</h2>
</div>
<form id="perRechargeForm" method="post">
    <div class="form_div">
        <label class="form_label" style="width: 100px;text-align: right">充值金额：</label>
        <input type="hidden" id="hipt_preAccountId" name="accountId" value="${order.id}"/>
        <input type="hidden" id="hipt_rechargeMoney" name="" value="${money}"/>
        <%--<input type="hidden"  name="productId" value="${product.id}"/>--%>
        <input id="ipt_money" name="money" class="easyui-numberbox" value="${order.price}" data-options="min:0, prompt:'0',precision:2, required:true"/>
    </div>
    <div class="form_div">
        <label class="form_label" style="width: 100px;text-align: right;margin-left: 24px;">备注：</label>
        <input id="ipt_description" name="remark" type="text" class="easyui-textbox" value="${order.remark}" data-options="multiline:true,width:200,height:100"/>
    </div>
</form>

<div class="btn_pre">
    <a id="btn_preRechargeConfig" href="#" class="easyui-linkbutton" onclick="CreateOrder.createOrder()" >提交</a>
    <a id="btn_cancelPreRecharge" href="#" class="easyui-linkbutton" onclick="CreateOrder.toManage()" >取消</a>
</div>


</body>
</html>
