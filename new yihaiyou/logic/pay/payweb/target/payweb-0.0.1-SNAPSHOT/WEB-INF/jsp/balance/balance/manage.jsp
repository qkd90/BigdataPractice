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
    <script type="text/javascript" src="/js/balance/manage.js"></script>
    <title>支付日志查询</title>
</head>
<body style="background-color: white">
<div id="tabsTools">
    <label>账户余额：<span style="color:red; font-weight:bold;font-size:20px;">${user.balanceStr}</span>元</label>
</div>
    <div id="tabs" class="easyui-tabs" fit="true" tools="#tabsTools">
        <div id="recharge" title="充值记录" data-type="recharge">
            <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                <!-- 表格工具条 始 -->
                <div id="recharge_tool">
                    <%--<div style="padding:2px 5px;">--%>
                        <%--<input type="hidden" id="hipt_userBalance" value="${user.balance}">--%>
                        <%--<form action="" id="recharge_form">--%>
                            <%--<input id="show_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">--%>
                            <%--<a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="Line.doSearch('show_dg')">查询</a>--%>
                        <%--</form>--%>
                    <%--</div>--%>
                    <div style="padding:2px 5px;">
                        <a href="javascript:void(0)" onclick="Balance.applyRecharge()" class="easyui-linkbutton" >充值</a>
                    </div>
                </div>
                <!-- 表格工具条 终 -->
                <!-- 表格 始 -->
                <div data-options="region:'center',border:false">
                    <table id="recharge_dg"></table>
                </div>
                <!-- 表格 终 -->
            </div>
        </div>
        <div id="withdraw" title="提现记录" data-type="recharge">
            <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                <!-- 表格工具条 始 -->
                <div id="withdraw_tool">
                    <div style="padding:2px 5px;">
                        <a href="javascript:void(0)" onclick="Balance.doOpenApplyWithdrawDg()" class="easyui-linkbutton" >申请提现</a>
                    </div>
                </div>
                <!-- 表格工具条 终 -->
                <!-- 表格 始 -->
                <div data-options="region:'center',border:false">
                    <table id="withdraw_dg"></table>
                </div>
                <!-- 表格 终 -->
            </div>
        </div>
        <div id="consume" title="消费记录" data-type="consume" >
            <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                <!-- 表格工具条 始 -->
                <div id="consume_tool">
                    <%--<div style="padding:2px 5px;">--%>
                        <%--<form action="" id="consume_form">--%>
                            <%--<input id="sconsume_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">--%>
                            <%--<a href="javascript:void(0)" id="consume_search" class="easyui-linkbutton"  onclick="Line.doSearch('show_dg')">查询</a>--%>
                        <%--</form>--%>
                    <%--</div>--%>
                    <%--<div style="padding:2px 5px;">--%>
                        <%--<a href="javascript:void(0)" onclick="" class="easyui-linkbutton">快速发布</a>--%>
                    <%--</div>--%>
                </div>
                <!-- 表格工具条 终 -->
                <!-- 表格 始 -->
                <div data-options="region:'center',border:false">
                    <table id="consume_dg"></table>
                </div>
                <!-- 表格 终 -->
            </div>
        </div>
        <div id="balance_result" title="余额流水" data-type="consume" >
            <div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                <!-- 表格工具条 始 -->
                <div id="balance_result_tool">
                    <%--<div style="padding:2px 5px;">--%>
                        <%--<form action="" id="balance_result_form">--%>
                            <%--<input id="balance_result_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">--%>
                            <%--<a href="javascript:void(0)" id="balance_result_search" class="easyui-linkbutton" data-options=" onclick="Line.doSearch('show_dg')">查询</a>--%>
                        <%--</form>--%>
                    <%--</div>--%>
                    <%--<div style="padding:2px 5px;">--%>
                        <%--<a href="javascript:void(0)" onclick="" class="easyui-linkbutton" >快速发布</a>--%>
                    <%--</div>--%>
                </div>
                <!-- 表格工具条 终 -->
                <!-- 表格 始 -->
                <div data-options="region:'center',border:false">
                    <table id="balance_result_dg"></table>
                </div>
                <!-- 表格 终 -->
            </div>
        </div>
    </div>

    <!-- 编辑窗口 -->
    <div id="editPanel" class="easyui-dialog" title=""
         data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
    </div>

    <!-- 资金操作窗口 始 -->
    <div id="applyWithdrawDg" title="提现操作" class="easyui-dialog" style="width:340px;height:150px;padding:10px 5px 5px 5px"
         closed="true" modal="true" >
        <form id="applyWithdrawForm" method="post">
            <table>
                <tr>
                    <td width="100" align="right" style="font-weight:bold;line-height:30px;">余额：</td>
                    <td>
                        <span id="frm_balance" style="color:red;font-weight:bold;"></span>
                    </td>
                </tr>
                <tr>
                    <td align="right" style="font-weight:bold;line-height:30px;">金额：</td>
                    <td>
                        <input id="frm_amount" type="text" class="easyui-numberbox" data-options="required:true,min:1,precision:2"/>
                    </td>
                <tr>
                </tr>
                    <td align="center" colspan="2" style="font-weight:bold;line-height:50px;">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onClick="Balance.doApplyWithdraw()" style="margin-top: 5px;">确认</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 资金操作窗口 终 -->

</body>
</html>
