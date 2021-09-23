<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/16
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <script type="text/javascript" src="/js/wechat/wechat/easyui.plugin.js"></script>
    <script type="text/javascript" src="/js/wechat/wechat/statisticsManage.js"></script>
    <title></title>
    <style>
        .calender_class {
            border-radius: 5px 5px 5px 5px;
        }
    </style>
</head>
<body style="background-color: white">

<div id="activity_tab" class="easyui-tabs" fit="true">
    <div id="tab_ticket" title="二维码关注度统计">
        <div id="search_ticket_tool" style="padding: 5px;">
            <form id="sale_ticket_searchForm">
                <div style="padding: 5px;">
                    <label>日期：</label>
                    <label style="margin-right: 25px;">
                        <input type="radio" name="check_ticket_date" checked="checked" value="7">近7天
                    </label>
                    <label style="margin-right: 25px;">
                        <input type="radio" name="check_ticket_date" value="30">近30天
                    </label>
                    <label style="margin-right: 50px;">
                        <input type="radio" name="check_ticket_date" value="3">前三个月
                    </label>

                    <label>公众号：</label>
                    <label>
                        <input id="sea_ticket_productName" type="text" class="easyui-combobox">
                    </label>

                </div>
                <div style="padding: 5px;">

                    <label style="margin-left: 40px;margin-right: 25px;">
                        <input id="start_ticket_date" class="Wdate calender_class" style="width: 100px;">
                        --
                        <input id="end_ticket_date" class="Wdate calender_class" style="width: 100px;">
                    </label>

                    <label>二维码名称：</label>
                    <label>
                        <input id="sea_ticket_buyUnit"  type="text" class="easyui-combobox" name="date">
                    </label>

                    <label style="margin-left: 25px;">
                        <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="StatisticsManage.doTicketSearch()">查询</a>
                    </label>
                    <label style="margin-left: 10px;">
                        <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="StatisticsManage.doTicketClear()">重置</a>
                    </label>
                    <%--<label style="margin-left: 10px;">--%>
                        <%--<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="StatisticsManage.doLoadTicketExcel()">导出数据</a>--%>
                    <%--</label>--%>
                </div>
            </form>
        </div>
        <table id="sales_ticket"></table>
        </div>
    </div>
</div>

</body>
</html>
