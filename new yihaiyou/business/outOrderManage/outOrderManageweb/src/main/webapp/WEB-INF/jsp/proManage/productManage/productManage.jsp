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
    <script type="text/javascript" src="/js/proManage/productManage.js"></script>
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
    </style>
</head>
<body style="background-color: white">
<div id="tabsTools">
    <label>账户余额：<span style="color:red; font-weight:bold;font-size:20px;">${user.balanceStr}</span>元</label>
</div>
<div id="tabs" class="easyui-tabs" fit="true" tools="#tabsTools">
    <div id="ticket" title="门票商品" data-type="recharge">
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="ticket_tool">
                <div style="padding:2px 5px;">
                    <form action="" id="ticket_form">
                        <div style="float: left;margin: 3px 20px 3px 20px;">
                            门票名称:	<input class="easyui-textbox"  id="sear_ticketName" style="width:200px;">
                        </div>
                        <div style="float: left;margin: 3px 20px 3px 20px;">
                            门票类别：<input id="com_type" class="easyui-combobox" style="width:200px;" data-options="
                            valueField: 'id',
                            textField: 'value',
                            data: [{
                                    id: 'scenic',
                                    value: '景点门票'
                                },{
                                    id: 'boat',
                                    value: '船票'
                                }
                                ,{
                                    id: 'shows',
                                    value: '演唱会门票'
                                }]" />
                        </div >
                        <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ProductManage.doSearchTicket()">查询</a>
                        <a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ProductManage.clearTicket()">清空</a>
                    </form>
                </div>
                <%--<div style="padding:2px 5px;">--%>
                    <%--<a href="javascript:void(0)" onclick="Balance.applyRecharge()" class="easyui-linkbutton" >申请充值</a>--%>
                <%--</div>--%>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="ticket_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>
    <div id="line" title="线路商品" data-type="consume" >
        <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <!-- 表格工具条 始 -->
            <div id="line_tool">
                <div style="padding:2px 5px;">
                    <form action="" id="consume_form">
                        <input id="sconsume_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词'" style="width:200px;">
                        <a href="javascript:void(0)" id="consume_search" class="easyui-linkbutton"  onclick="ProductManage.doSearchLine()">查询</a>
                    </form>
                </div>
                <div style="padding:2px 5px;">
                    <%--<a href="javascript:void(0)" onclick="" class="easyui-linkbutton" >快速发布</a>--%>
                </div>
            </div>
            <!-- 表格工具条 终 -->
            <!-- 表格 始 -->
            <div data-options="region:'center',border:false">
                <table id="line_dg"></table>
            </div>
            <!-- 表格 终 -->
        </div>
    </div>

</div>





<!-- 编辑窗口 -->
<div id="editPanel" class="easyui-dialog" title="录入订单信息"
     data-options="fit:true,resizable:false,modal:true,closed:true">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:650px;"></iframe>
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
