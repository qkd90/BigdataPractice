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
    <script type="text/javascript" src="/js/payLogs.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/payLogs.css"/>
    <title>支付日志查询</title>
</head>
<body style="background-color: #ffffff">
    <div>
        <div id="searchparam" class="searcharea">
            <div class="margintop">
                <label class="keyzone"> 订单号：</label>
                <input id="orderId" class="easyui-textbox" name="orderId" type="text">
                <label class="keyzone needmargin"> 姓名：</label>
                <input id="name" name="name" class="easyui-textbox" type="text">
                <label class="keyzone needmargin"> 电话：</label>
                <input id="mobile" name="mobile" class="easyui-textbox" type="text">
            </div>
            <div class="margintop">
                <label class="keyzone"> 开始日期：</label>
                <input id="st" name="sTime" class="easyui-datebox">
                <label class="keyzone needmargin"> 结束日期：</label>
                <input id="et" name="eTime" class="easyui-datebox">

                <a id="searchbtn" href="#" class="easyui-linkbutton needmargin"  onclick="payLogs.doSearch()">查询</a>
            </div>
        </div>

        <div class="margintop" style="width: 100%;height: 100%;">
            <table id="logsDg"></table>
        </div>

    </div>

</body>
</html>
