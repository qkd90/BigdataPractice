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
    <script type="text/javascript" src="/js/statistics/salesStatisManage.js"></script>
    <title></title>

    <style>
    </style>
</head>
<body style="background-color: white">

    <div id="activity_tab" class="easyui-tabs" fit="true">
        <div id="tab_ticket" title="门票销售统计">
            <div id="search_ticket_tool" style="padding: 5px;">
                <form id="sale_ticket_searchForm">
                    <div style="padding: 5px;">
                        <label>业务日期：</label>
                        <label style="margin-right: 25px;">
                            <input type="radio" name="check_ticket_date" checked="checked" value="7" style="height:13px">近7天
                        </label>
                        <label style="margin-right: 25px;">
                            <input type="radio" name="check_ticket_date" value="30" style="height:13px">近30天
                        </label>
                        <label style="margin-right: 28px;">
                            <input type="radio" name="check_ticket_date" value="3" style="height:13px">前三个月
                        </label>

                        <label>产品名称：</label>
                        <label>
                            <input id="sea_ticket_productName" type="text" class="easyui-textbox">
                        </label>

                    </div>
                    <div style="padding: 5px;">

                        <label style="margin-left: 92px;margin-right: 25px;">
                            <input id="start_ticket_date" class="Wdate calender_class" style="width: 100px;">
                            --
                            <input id="end_ticket_date" class="Wdate calender_class" style="width: 100px;">
                        </label>

                        <label>采购公司：</label>
                        <label>
                            <input id="sea_ticket_buyUnit"  type="text" class="easyui-textbox" name="date">
                        </label>

                        <label style="margin-left: 25px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doTicketSearch()">查询</a>
                        </label>
                        <label style="margin-left: 10px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doTicketClear()">清空</a>
                        </label>
                        <label style="margin-left: 10px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doLoadTicketExcel()">导出数据</a>
                        </label>
                    </div>
                </form>
            </div>
            <div id="sales_ticket" style="width: 100%; height: 100%;">
            </div>
        </div>
        <div id="tab_line" title="线路销售统计">
            <div id="search_line_tool" style="padding: 5px;">
                <form id="sale_line_searchForm">
                    <div style="padding: 5px;">
                        <label>业务日期：</label>
                        <label style="margin-right: 25px;">
                            <input type="radio" checked="checked" name="check_line_date" value="7">近7天
                        </label>
                        <label style="margin-right: 25px;">
                            <input type="radio" name="check_line_date" value="30">近30天
                        </label>
                        <label style="margin-right: 28px;">
                            <input type="radio" name="check_line_date" value="3">前三个月
                        </label>

                        <label>产品名称：</label>
                        <label>
                            <input id="sea_line_productName" type="text" class="easyui-textbox" name="date">
                        </label>

                    </div>
                    <div style="padding: 5px;">

                        <label style="margin-left: 65px;margin-right: 25px;">
                            <input id="start_line_date" class="Wdate calender_class" style="width: 100px;">
                            --
                            <input id="end_line_date" class="Wdate calender_class" style="width: 100px;">
                        </label>

                        <label>采购公司：</label>
                        <label>
                            <input id="sea_line_buyUnit" type="text" class="easyui-textbox" name="date">
                        </label>

                        <label style="margin-left: 25px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doLineSearch()">查询</a>
                        </label>
                        <label style="margin-left: 10px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doLineClear()">清空</a>
                        </label>
                        <label style="margin-left: 10px;">
                            <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SalesStatisManage.doLoadLineExcel()">导出数据</a>
                        </label>
                    </div>
                </form>
            </div>
            <div id="sales_line" style="width: 100%; height: 100%;">
            </div>
        </div>
    </div>

</body>
</html>
