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
    <script type="text/javascript" src="/js/outOrder/outOrder/outOrderManage.js"></script>
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
        .search {
            margin: 9px 10px 0px 10px;
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
            <div style="width:100%;height:100%;">
                <div id="ticket-tool">
                    <form id="searchForm_activity">
                        <div style="padding: 5px 5px 3px 5px;">
                            <input id="ipt_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                            <input id="ipt_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;"/>
                            <input id="ticket_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                            <input id="com_status" class="easyui-combobox" style="width:100px;" data-options="
                                valueField: 'id',
                                textField: 'value',
                                prompt:'订单状态',
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
                                    }]"
                                />
                            <input id="com_useStatus" class="easyui-combobox " style="width:100px;" data-options="
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
                            <input id="start_ticket_createTime" class="easyui-datebox " data-options="prompt:'订单日期始'" style="width:100px;"/>
                            -
                            <input id="end_ticket_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>
                        </div>
                        <div style="padding:0px 5px 5px 5px;">
                            <input id="ipt_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;"/>
                            <input id="ipt_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;"/>
                            <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton"  onclick="OutOrderManage.doTicketSearch()">查询</a>
                            <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="OutOrderManage.clearTicketSearchForm()">清空</a>
                            <a style=" margin-right: 10px;" href="javascript:void(0)" class="easyui-linkbutton "  onclick="OutOrderManage.downloadTicketExcel()">导出订单</a>
                            <a id="addbtn2" href="#" onclick="OutOrderManage.loadTicketValidateExcel();"   class="easyui-linkbutton" >门票验证记录导出</a>
                        </div>
                    </form>
                </div>
                <div style="width: 100%; height: 100%;">
                    <table id="dg_outOrder"></table>
                </div>
            </div>
        </div>
        <div id="line" title="线路订单">
            <div style="width:100%;height:100%;">
                <div id="line-tool">
                    <form id="searchForm_line">
                        <div style="padding:5px 5px 3px 5px;">
                            <input id="ipt_line_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:150px;"/>
                            <input id="ipt_line_proName" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:150px;"/>
                            <input id="line_createby" class="easyui-textbox" data-options="prompt:'下单帐号'" style="width:100px;"/>
                            <input id="com_line_status" class="easyui-combobox" style="width:100px;" data-options="
                                valueField: 'id',
                                prompt:'订单状态',
                                textField: 'value',
                                 data: [{
                                 <%--CANCELED("已取消"),UNPAY("待付款"),PAYED("已付款"),UNCANCEL("待取消"); WAITING--%>
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
                                    }]"
                                            />
                            <input id="com_line_useStatus" class="easyui-combobox" style="width:100px;" data-options="
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
                            <input id="ipt_line_contact" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;"/>
                            <input id="ipt_line_phone" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;"/>
                            <input id="start_line_useTime" class="easyui-datebox" data-options="prompt:'出发日期始'" style="width:100px;"/>
                            -
                            <input id="end_line_useTime" class="easyui-datebox" data-options="prompt:'出发日期止'" style="width:100px;"/>
                            <a style=" margin-right: 10px;" href="javascript:void(0)"  class="easyui-linkbutton"  onclick="OutOrderManage.doLineSearch()">查询</a>
                            <a style=" margin-right: 10px;" href="javascript:void(0)"  class="easyui-linkbutton"  onclick="OutOrderManage.clearLineSearchForm()">清空</a>
                            <a style=" margin-right: 10px;" href="javascript:void(0)"  class="easyui-linkbutton"  onclick="OutOrderManage.downloadLineExcel()">导出订单</a>
                        </div>
                    </form>
                </div>
                <div style="width: 100%; height: 100%;">
                    <%--<div data-options="region:'center',border:false" style="width: 92%">--%>
                    <table id="dg_line"></table>
                </div>
            </div>
        </div>
    </div>

<!-- 编辑窗口 -->
<div id="editPanel" class="easyui-dialog" title="录入订单信息"
     data-options="fit:true,resizable:false,modal:true,closed:true">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:800px;"></iframe>
</div>

<!-- 编辑窗口 -->
<div id="editPanel1" class="easyui-dialog" title="录入订单信息"
     data-options="fit:true,resizable:false,modal:true,closed:true">
    <iframe name="editIframe1" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:800px;"></iframe>
</div>

    <div id="editPanel2" class="easyui-dialog" title="门票验证详情"
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
            <div><input class="calender_class" id="startTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endTime\',{M:-3})}',maxDate:'#F{$dp.$D(\'endTime\')}'})"></div>
        </div>
        <div style="padding: 5px;">
            <label style="float: left; margin-right: 10px;">截至时间：</label>
            <div><input class="calender_class" id="endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}', maxDate:'%y-%M-%d'})"></div>
        </div>
    </div>
</body>
</html>
