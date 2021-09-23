<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/10/31
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>结算管理</title>
    <%@ include file="../../common/common141.jsp" %>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <!-- 表格工具条 始 -->
    <div id="tb">
        <div style="padding:2px 5px;">
            <form action="" id="searchform">
                <span>
                    <label>对账单号: </label>
                    <input id="search-billNo" class="easyui-textbox" style="width:130px;">
                </span>
                <span>
                    <label>账单日期: </label>
                    <input id="search-billSummaryDate" class="easyui-datebox" style="width:130px;">
                </span>
                <span>
                    <label>结算状态: </label>
                    <select id="search-bill-status" class="easyui-combobox" style="width:80px;">
                        <option value="" selected>全部</option>
                        <option value="0">未结算</option>
                        <option value="1">已结算</option>
                        <%--<option value="2">部分结算</option>--%>
                    </select>
                </span>
                <%--<span>--%>
                <%--<label>确认状态: </label>--%>
                <%--<select id="search-confirm-status" class="easyui-combobox" style="width:80px;">--%>
                <%--<option value="" selected>全部</option>--%>
                <%--<option value="0">未确认</option>--%>
                <%--<option value="1">已确认</option>--%>
                <%--&lt;%&ndash;<option value="2">部分结算</option>&ndash;%&gt;--%>
                <%--</select>--%>
                <%--</span>--%>
                <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="OrderBill.doSearch()">查询</a>
                <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="OrderBill.doOpenGenBillDg()">生成账单</a>
            </form>
        </div>
    </div>
    <!-- 表格工具条 终 -->
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="orderBillTable"></table>
    </div>
    <!-- 数据表格 终-->
</div>

<!-- 生成账单操作窗口 始 -->
<div id="genBillDg" title="生成账单" class="easyui-dialog" style="width:470px;height:150px;padding:10px 5px 5px 5px"
     closed="true" modal="true" >
    <form id="genBillForm" method="post">
        <table>
            <tr>
                <td align="center" colspan="3" style="line-height:30px;color:gray;">
                    <span>温馨提示:生成过的账单如有问题，请用账单列表的重新生成。</span>
                </td>
            </tr>
            <tr>
                <td width="100" align="right" style="font-weight:bold;line-height:30px;">账单日期：</td>
                <td>
                    <input id="frm_billSummaryDate" class="easyui-datebox" style="width:130px;" data-options="required:true">
                </td>
                <td width="160" align="center" style="font-weight:bold;">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onClick="OrderBill.doGenBill()" style="width: 80px;">生成</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 生成账单操作窗口 终 -->

<!-- 账单明细窗口 始 -->
<div id="orderDetailDg" class="easyui-dialog" title="账单明细"
     data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
    <iframe name="orderDetailIframe" id="orderDetailIframe" scrolling="no" frameborder="0"  style="width:100%;height:1000px;border-width:0;"></iframe>
</div>
<!-- 账单明细窗口 终 -->

<!-- 账单结算操作窗口 始 -->
<div id="cfmBillDg" title="账单结算" class="easyui-dialog" style="width:360px;height:280px;padding:10px 5px 5px 5px"
     closed="true" modal="true" >
    <input type="hidden" id="search-cfm-billSummaryId">
    <input type="hidden" id="query_data_result">
    <table>
        <tr style="line-height:30px; font-weight: bold;">
            <td align="center" width="100">项目</td>
            <td align="center" width="100">平台账单数据</td>
            <td align="center" width="100">轮渡统计数据</td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">当期结算额</td>
            <td align="center"><span id="lbl_totalBillPrice"></span></td>
            <td align="center"><span id="lbl_saleAmount"></span></td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">当期订单数</td>
            <td align="center"><span id="lbl_totalOrderCount"></span></td>
            <td align="center"><span id="lbl_saleCount"></span></td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">当期退款额</td>
            <td align="center"><span id="lbl_refundPrice"></span></td>
            <td align="center"><span id="lbl_returnAmount"></span></td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">当期退款数</td>
            <td align="center"><span id="lbl_refundCount"></span></td>
            <td align="center"><span id="lbl_returnCount"></span></td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">退款手续费</td>
            <td align="center"><span id="lbl_refundFee"></span></td>
            <td align="center"><span id="lbl_poundageAmount"></span></td>
        </tr>
        <tr>
            <td align="center" style="font-weight: bold;">实际应收</td>
            <td align="center" style="font-weight: bold;"><span id="lbl_billAmount"></span></td>
            <td align="center" style="font-weight: bold;"><span id="lbl_billAmountFerry"></span></td>
        </tr>
        <tr>
            <td align="center" colspan="3" style="font-weight:bold;line-height:30px;padding-top: 10px;">
                <a href="javascript:void(0)" class="easyui-linkbutton" onClick="OrderBill.doCfmBill()" style="margin-top: 5px;width: 80px;">结算</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onClick="OrderBill.doCloseCfmBillDg()" style="margin-top: 5px;width: 80px;">取消</a>
            </td>
        </tr>
    </table>
</div>
<!-- 账单结算操作窗口 终 -->

</body>
<script type="text/javascript" src="/js/order/order/ferryOrderbill.js"></script>
</body>
</html>
