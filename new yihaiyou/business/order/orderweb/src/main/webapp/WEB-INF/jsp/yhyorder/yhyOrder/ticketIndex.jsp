<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/10/8
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <script type="text/javascript" src="/js/yhyorder/index.js"></script>
    <title>门票订单管理</title>
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

    <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
        <input type="hidden" id="orderType" value="ticket">
        <!-- 表格工具条 始 -->
        <div id="client_tool">
            <form action="" id=client_form">
                <div style="padding:5px 5px 3px 5px;">
                    <input id="client_orderNo" class="easyui-textbox" data-options="prompt:'订单编号'" style="width:200px;"/>
                    <input id="client_recName" class="easyui-textbox" data-options="prompt:'联系人'" style="width:150px;">
                    <input id="client_mobile" class="easyui-textbox" data-options="prompt:'联系电话'" style="width:150px;">
                    <input id="client_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                        valueField: 'id',
                        textField: 'value',
                        panelHeight: 'auto',
                         data:　ClientOrderManage.orderStatus
                        " style="width:150px;">
                    <input id="start_createTime" class="easyui-datebox" data-options="prompt:'订单日期始'" style="width:100px;"/>
                    -
                    <input id="end_createTime" class="easyui-datebox" data-options="prompt:'订单日期止'" style="width:100px;"/>

                    <a href="javascript:void(0)" id="client_ship_search" class="easyui-linkbutton"  onclick="ClientOrderManage.doSearch()">查询</a>
                    <a href="javascript:void(0)" id="client_ship_clear" class="easyui-linkbutton"  onclick="ClientOrderManage.clearForm()">重置</a>
                </div>

            </form>
        </div>
        <!-- 表格工具条 终 -->
        <!-- 表格 始 -->
        <div data-options="region:'center',border:false">
            <table id="client_dg"></table>
        </div>
        <!-- 表格 终 -->
    </div>

<div id="editPanel" class="easyui-dialog" title="录入订单信息"
     data-options="fit:true,resizable:false,modal:true,closed:true">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
</div>


</body>
</html>
