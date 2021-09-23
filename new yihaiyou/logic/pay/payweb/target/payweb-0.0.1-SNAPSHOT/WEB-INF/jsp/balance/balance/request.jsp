<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/22
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .div_content {
            font-size: 16px;
            padding: 5px 20px;
            font-weight: 700;
        }
        .div_label {
            color: rgba(26, 32, 39, 0.93)
        }
        .div_config_btn {
            padding: 5px 20px;
        }
        .div_config_btn a{
            margin-right: 20px;
        }
        .class_weipay {
            font-size: 16px;
            font-weight: 700;
        }
        .introduction {
            margin: 20px 0px;
        }
        .fl {
            font-size: 14px;
        }
    </style>
    <script src="/js/balance/request.js" type="text/javascript"></script>
</head>
<body style="background-color: white">

    <div class="row_hd">
        <div style="width: 10%;float: left;"><span>提交申请>></span></div>
        <div style="width: 10%;float: left;color: #0092DC"><span>确认充值>></span></div>
        <div style="width: 10%;float: left;"><span>充值>></span></div>
        <div style="width: 10%;float: left;"><span>充值成功</span></div>
    </div>

    <div style="width: 100%;height: 500px;padding:10px;">
        <div style="float: left; width:50%;height: 100%;">
            <div style="padding: 20px">
                <h1 style="font-size: x-large;">确定充值</h1>
            </div>
            <div class="div_content">

                <label>充值编号：</label><label class="div_label" >${order.orderNo}</label>
            </div>
            <div class="div_content">
                <label>充值金额：</label><label class="div_label">${order.price}</label>
            </div>
            <div class="div_content">
                <label>备注：</label>
                <c:if test="${order.remark != null}">
                    <label class="div_label">${order.remark}</label>
                </c:if>
                <c:if test="${order.remark == null}">
                    <label class="div_label">无</label>
                </c:if>
            </div>
            <div class="div_config_btn">
                <input type="hidden" id="hipt_orderId" value="${order.id}"/>
                <%--<input type="hidden" id="hipt_productId" value="${product.id}">--%>
                <a id="btn1" href="#" class="easyui-linkbutton" onclick="Request.toWechatPay()" >微信支付</a>
                <!-- <a id="btnZhaoh" href="#" class="easyui-linkbutton" onclick="Request.toZhaohPay()" >招行支付</a> -->
                <a id="btn2" href="#" class="easyui-linkbutton" onclick="Request.toManage()" >取消</a>
            </div>
        </div>
        <div style="float: right; width:50%;height: 100%;">
        </div>
    </div>

    <!-- 支付表单 开始 -->
    <form id="forwardZhFm" action="" method="post" target="_blank">
        <input id="zhaoh_BranchID" name="BranchID" type="hidden" value="0571"><!--分行号-->
        <input id="zhaoh_CoNo" name="CoNo" type="hidden" value="001139"><!--商户号-->
        <input id="zhaoh_Date" name="Date" type="hidden" value="20140903"><!--日期（必须填写为今天）-->
        <input id="zhaoh_BillNo" name="BillNo" type="hidden" value="0000000001"><!--定单号（10位数字）-->
        <input id="zhaoh_Amount" name="Amount" type="hidden" value="${order.price}"><!--金额-->
        <input id="zhaoh_MerchantPara" name="MerchantPara" type="hidden" value="abc"><!--商户自定义参数-->
        <input id="zhaoh_MerchantURL" name="MerchantURL" type="hidden" value="http://merchant.com/recv.aspx"><!--返回URL（URL必须能通过公网能够访问，不能带有端口号，页面中不能含有跳转）-->
    </form>
    <!-- 支付表单 结束 -->

</body>
</html>
