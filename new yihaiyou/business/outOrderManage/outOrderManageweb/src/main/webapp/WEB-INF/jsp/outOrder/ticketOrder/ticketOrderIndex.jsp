<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/10/8
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>邮轮订单管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/outOrder/ticket/ticketOrderIndex.js"></script>
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

    <div title="门票订单" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
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
                        <a href="javascript:void(0)" id="client_ticket_search" class="easyui-linkbutton"  onclick="TicketOrderIndex.doTicketSearch('show_dg')">查询</a>
                        <a href="javascript:void(0)" id="client_ticket_clear" class="easyui-linkbutton"  onclick="TicketOrderIndex.clearTicketForm()">清空</a>
                        <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="TicketOrderIndex.downloadTicketSuplierExcel()">导出订单</a>
                        <a id="addbtn2" href="#" onclick="TicketOrderIndex.loadTicketValidateSuplierExcel();"  class="easyui-linkbutton" >门票验证记录导出</a>
                    </div>
                </form>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div title="门票订单" data-options="region:'center',border:false">
                <table id="client_ticket_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
        <!-- 表格 终 -->
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
</body>
</html>
