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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="/css/payLogs.css"/>
    <title>支付日志查询</title>
</head>
<body>
    <div>

        <div id="searchparam" class="searcharea">
            <div class="margintop">
                <label class="keyzone"> 订单号：</label>
                <input id="orderId" name="orderId" type="text">
                <label class="keyzone needmargin"> 姓名：</label>
                <input id="name" name="name" type="text">
                <label class="keyzone needmargin"> 电话：</label>
                <input id="mobile" name="mobile" type="text">
            </div>
            <div class="margintop">
                <label class="keyzone"> 开始日期：</label>
                <input id="st" name="sTime" class="easyui-datebox">
                <label class="keyzone needmargin"> 结束日期：</label>
                <input id="et" name="eTime" class="easyui-datebox">

                <a id="searchbtn" href="#" class="easyui-linkbutton needmargin"  onclick="payLogs.doSearch()">查询</a>
            </div>
        </div>


        <div class="margintop">
            <table id="logsDg"></table>
        </div>

    </div>

</body>
</html>
