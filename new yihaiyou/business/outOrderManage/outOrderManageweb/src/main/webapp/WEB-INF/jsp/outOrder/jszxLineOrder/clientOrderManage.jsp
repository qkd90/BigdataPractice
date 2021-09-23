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
    <script type="text/javascript" src="/js/outOrder/lineOrder/clientOrderManage.js"></script>
    <title></title>
    <style>
        .labelClass {
            float: left;
            margin-right: 5px;
            width: 60px;
            text-align: right;
        }
        .row_hd {
            height: 27px;
            width: 100%;
            line-height: 27px;
            border-bottom: 1px solid #ddd;
            background: #f4f4f4;
            margin: 0px 15px 10px 5px;
            padding-left: 5px;
            padding-right: 5px;
            font-weight: 700;
            color: #666;
        }
        .calender_class {
            border: #999 1px solid;
            width: 117px;
            margin-left: -4px;
            height: 22px;
            background: url("/jquery-easyui-1.4.1/themes/icons/date.png") no-repeat scroll right center transparent;
            border-radius: 5px 5px 5px 5px;
            border: 1px solid #95B8E7;
        }
    </style>
</head>
<body style="background-color: white">
<div id="tabsTools">
    <label>账户余额：<span style="color:red; font-weight:bold;font-size:20px;">${sysUser.balanceStr}</span>元</label>
</div>
<div id="activity_tab" class="easyui-tabs" fit="true" tools="#tabsTools">
    <div id="ticket" title="门票订单">
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="client_ticket_tool">
                <form action="" id=client_ticket_form">
                    <div style="padding:5px 5px 3px 5px;">
                        <input id="client_ticket_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                        <input id="client_ticket_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;">
                        <input id="client_ticket_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                        <input id="client_ticket_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                            valueField: 'id',
                            textField: 'value',
                             data: [{
                                    id: 'CANCELED',
                                    value: '已取消'
                                    },{
                                    id: 'UNPAY',
                                    value: '待付款'
                                    },{
                                    id: 'PAYED',
                                    value: '已付款'
                                    },{
                                    id: 'UNCANCEL',
                                    value: '待取消'
                                    },{
                                    id: 'WAITING',
                                    value: '待确认'
                                    }]
                            " style="width:100px;">
                        <input id="client_ticket_useStatus" class="easyui-combobox" style="width:100px;" data-options="
                            valueField: 'id',
                            textField: 'value',
                            prompt:'使用状态',
                             data: [{
                                id: 'UNUSED',
                                value: '未验票'
                                },{
                                id: 'USED',
                                value: '已验票'
                                },{
                                id: 'CANCEL',
                                value: '已取消'
                            }]"
                                />
                        <input id="start_ticket_createTime" class="easyui-datebox" data-options="prompt:'订单日期始'" style="width:100px;"/>
                         -
                        <input id="end_ticket_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>
                    </div>
                    <div style="padding:0px 5px 5px 5px;">
                        <input id="client_ticket_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;">
                        <input id="client_ticket_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;">
                        <a href="javascript:void(0)" id="client_ticket_search" class="easyui-linkbutton"  onclick="ClientOrderManage.doTicketSearch('show_dg')">查询</a>
                        <a href="javascript:void(0)" id="client_ticket_clear" class="easyui-linkbutton"  onclick="ClientOrderManage.clearTicketForm()">重置</a>
                        <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="ClientOrderManage.downloadTicketSuplierExcel()">导出订单</a>
                        <a id="addbtn2" href="#" onclick="ClientOrderManage.loadTicketValidateSuplierExcel();"  class="easyui-linkbutton" >门票验证记录导出</a>
                    </div>
                </form>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="client_ticket_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>
    <div id="line" title="线路订单">
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="client_line_tool">
                <form action="" id=client_line_form">
                    <div style="padding:5px 5px 3px 5px;">
                        <input id="client_line_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                        <input id="client_line_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;">
                        <input id="client_line_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                        <input id="client_line_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                            valueField: 'id',
                            textField: 'value',
                             data: [{
                                    id: 'CANCELED',
                                    value: '已取消'
                                    },{
                                    id: 'UNPAY',
                                    value: '待付款'
                                    },{
                                    id: 'PAYED',
                                    value: '已付款'
                                    },{
                                    id: 'UNCANCEL',
                                    value: '待取消'
                                    },{
                                    id: 'WAITING',
                                    value: '待确认'
                                    }]
                            " style="width:100px;">
                        <input id="client_line_useStatus" class="easyui-combobox" style="width:100px;" data-options="
                            valueField: 'id',
                            textField: 'value',
                            prompt:'使用状态',
                             data: [{
                                    id: 'UNUSED',
                                    value: '未出游'
                                    },{
                                    id: 'REFUNDING',
                                    value: '退款中'
                                    },{
                                    id: 'USED',
                                    value: '已出游'
                                    },{
                                    id: 'CANCEL',
                                    value: '已取消'
                                }]"
                                />
                        <input id="start_line_createTime" class="easyui-datebox" data-options="prompt:'订单日期始'" style="width:100px;"/>
                        -
                        <input id="end_line_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>
                    </div>
                    <div style="padding:0px 5px 5px 5px;">
                        <input id="client_line_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;">
                        <input id="client_line_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;">
                        <input id="start_line_useTime" class="easyui-datebox" data-options="prompt:'出发日期始'" style="width:100px;"/>
                        -
                        <input id="end_line_useTime" class="easyui-datebox" data-options="prompt:'出发日期止'" style="width:100px;"/>
                        <a href="javascript:void(0)" id="client_line_search" class="easyui-linkbutton"  onclick="ClientOrderManage.doLineSearch('show_dg')">查询</a>
                        <a href="javascript:void(0)" id="client_line_clear" class="easyui-linkbutton"  onclick="ClientOrderManage.clearLineForm()">重置</a>
                        <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="ClientOrderManage.downloadLineSuplierExcel()">导出订单</a>
                    </div>
                </form>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="client_line_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>
    <div id="hotel" title="酒店订单">
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="client_hotel_tool">
                <form action="" id=client_hotel_form">
                    <div style="padding:5px 5px 3px 5px;">
                        <input id="client_hotel_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                        <input id="client_hotel_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;">
                        <input id="client_hotel_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                        <input id="client_hotel_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                            valueField: 'id',
                            textField: 'value',
                             data: [{
                                    id: 'CANCELED',
                                    value: '已取消'
                                    },{
                                    id: 'UNPAY',
                                    value: '待付款'
                                    },{
                                    id: 'PAYED',
                                    value: '已付款'
                                    },{
                                    id: 'UNCANCEL',
                                    value: '待取消'
                                    },{
                                    id: 'WAITING',
                                    value: '待确认'
                                    }]
                            " style="width:100px;">
                        <%--<input id="client_hotel_useStatus" class="easyui-combobox" style="width:100px;" data-options="--%>
                            <%--valueField: 'id',--%>
                            <%--textField: 'value',--%>
                            <%--prompt:'使用状态',--%>
                             <%--data: [{--%>
                                    <%--id: 'UNUSED',--%>
                                    <%--value: '未入住'--%>
                                    <%--},{--%>
                                    <%--id: 'REFUNDING',--%>
                                    <%--value: '退款中'--%>
                                    <%--},{--%>
                                    <%--id: 'USED',--%>
                                    <%--value: '已入住'--%>
                                    <%--},{--%>
                                    <%--id: 'CANCEL',--%>
                                    <%--value: '已取消'--%>
                                <%--}]"--%>
                                <%--/>--%>
                        <input id="start_hotel_createTime" class="easyui-datebox" data-options="prompt:'订单日期始'" style="width:100px;"/>
                        -
                        <input id="end_hotel_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>
                    </div>
                    <div style="padding:0px 5px 5px 5px;">
                        <input id="client_hotel_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;">
                        <input id="client_hotel_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;">
                        <input id="start_hotel_checkinTime" class="easyui-datebox"
                               data-options="prompt:'入住日期始'" style="width:100px;"/>&nbsp;-
                        <input id="end_hotel_checkinTime" class="easyui-datebox"
                               data-options="prompt:'入住日期止'" style="width:100px;"/>
                        <input id="start_hotel_checkoutTime" class="easyui-datebox"
                               data-options="prompt:'退房日期始'" style="width:100px;"/>&nbsp;-
                        <input id="end_hotel_checkoutTime" class="easyui-datebox"
                               data-options="prompt:'退房日期止'" style="width:100px;"/>
                        <a href="javascript:void(0)" id="client_hotel_search" class="easyui-linkbutton"  onclick="ClientOrderManage.doHotelSearch()">查询</a>
                        <a href="javascript:void(0)" id="client_Hotel_clear" class="easyui-linkbutton"  onclick="ClientOrderManage.clearHotelForm()">重置</a>
                        <%--<a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="ClientOrderManage.downloadHotelSuplierExcel()">导出订单</a>--%>
                    </div>
                </form>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="client_hotel_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>
    <div id="ship" title="船票(交通)">
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="client_ship_tool">
                <form action="" id=client_ship_form">
                    <div style="padding:5px 5px 3px 5px;">
                        <input id="client_ship_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                        <input id="client_ship_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;">
                        <input id="client_ship_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                        <input id="client_ship_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                            valueField: 'id',
                            textField: 'value',
                             data: [{
                                    id: 'CANCELED',
                                    value: '已取消'
                                    },{
                                    id: 'UNPAY',
                                    value: '待付款'
                                    },{
                                    id: 'PAYED',
                                    value: '已付款'
                                    },{
                                    id: 'UNCANCEL',
                                    value: '待取消'
                                    },{
                                    id: 'WAITING',
                                    value: '待确认'
                                    }]
                            " style="width:100px;">
                        <%--<input id="client_ship_useStatus" class="easyui-combobox" style="width:100px;" data-options="--%>
                            <%--valueField: 'id',--%>
                            <%--textField: 'value',--%>
                            <%--prompt:'使用状态',--%>
                             <%--data: [{--%>
                                    <%--id: 'UNUSED',--%>
                                    <%--value: '未出游'--%>
                                    <%--},{--%>
                                    <%--id: 'REFUNDING',--%>
                                    <%--value: '退款中'--%>
                                    <%--},{--%>
                                    <%--id: 'USED',--%>
                                    <%--value: '已出游'--%>
                                    <%--},{--%>
                                    <%--id: 'CANCEL',--%>
                                    <%--value: '已取消'--%>
                                <%--}]"--%>
                                <%--/>--%>
                        <input id="start_ship_createTime" class="easyui-datebox" data-options="prompt:'订单日期始'" style="width:100px;"/>
                        -
                        <input id="end_ship_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>
                    </div>
                    <div style="padding:0px 5px 5px 5px;">
                        <input id="client_ship_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;">
                        <input id="client_ship_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;">
                        <input id="start_ship_useTime" class="easyui-datebox"
                               data-options="prompt:'出发日期始'" style="width:100px;"/>&nbsp;-
                        <input id="end_ship_useTime" class="easyui-datebox"
                               data-options="prompt:'出发日期止'" style="width:100px;"/>
                        <a href="javascript:void(0)" id="client_ship_search" class="easyui-linkbutton"  onclick="ClientOrderManage.doShipSearch('show_dg')">查询</a>
                        <a href="javascript:void(0)" id="client_ship_clear" class="easyui-linkbutton"  onclick="ClientOrderManage.clearShipForm()">重置</a>
                        <%--<a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="ClientOrderManage.downloadShipSuplierExcel()">导出订单</a>--%>
                    </div>
                </form>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="client_ship_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>
</div>
<!-- 编辑窗口 -->
<div id="editPanel" class="easyui-dialog" title="录入订单信息"
     data-options="fit:true,resizable:false,modal:true,closed:true">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:600px;"></iframe>
</div>
<div id="editPanel2" class="    easyui-dialog" title="门票验证详情"
     data-options="resizable:false,modal:true,closed:true" style="padding:10px;">
    <div style="margin-bottom: 5px;">
        <label>
            产品名称：
        </label>
        <label id="proName">
        </label>
    </div>
    <div id="dg_validateInfo" style="width: 99%;height: 90%"></div>
</div>
<div id="editPanel3" class="easyui-dialog" title="验证记录导出<span style='color:red;font-size:12px;font-weight: 100;'>（日期范围不能超过三个月）</span>"
     data-options="resizable:false,modal:true,closed:true" style="padding:10px;">
    <div style="padding: 5px;">
        <label style="float: left; margin-right: 10px;">起始时间：</label>
        <div><input class="calender_class" id="client_startTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'client_endTime\',{M:-3})}',maxDate:'#F{$dp.$D(\'client_endTime\')}'})"></div>
    </div>
    <div style="padding: 5px;">
        <label style="float: left; margin-right: 10px;">截至时间：</label>
        <div><input class="calender_class" id="client_endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'client_startTime\')}', maxDate:'%y-%M-%d'})"></div>
    </div>
</div>
<!-- 编辑窗口 -->
<%--<div id="editPanel1" class="easyui-dialog" title="录入订单信息"--%>
     <%--data-options="fit:true,resizable:false,modal:true,closed:true">--%>
    <%--<iframe name="editIframe1" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:1450px;"></iframe>--%>
<%--</div>--%>
<%--<div id="editPanel" class="easyui-dialog" title="录入订单信息" data-options="width:400,height:400,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">--%>
    <%--<iframe name="editIframe" id="editIframe" scrolling="yes" frameborder="0"  style="width:100%;height:100%;border-width:0;overflow: scroll; "></iframe>--%>
<%--</div>--%>
</body>
</html>
