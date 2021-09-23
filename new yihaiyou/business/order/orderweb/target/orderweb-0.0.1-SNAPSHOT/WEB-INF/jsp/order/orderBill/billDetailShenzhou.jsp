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
    <title>账单明细</title>
    <%@ include file="../../common/common141.jsp" %>
    <style type="text/css">
        .billSummaryInfo {margin-top:10px;margin-left:10px;margin-bottom:10px;}
        .billSummaryInfo tr {line-height: 28px;}
        .billSummaryInfo td {padding-left:6px;}
        .billSummaryInfo td.lbl { text-align: right;font-weight: bold; padding-left:10px;}
    </style>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
    <input type="hidden" id="search-billSummaryId" value="${orderBillSummary.id}">
    <input type="hidden" id="billDays" value="${orderBillSummary.billDays}">
    <table class="billSummaryInfo">
        <tr>
            <td class="lbl">对账单号:</td>
            <td>${orderBillSummary.billNo}</td>
            <td class="lbl">账单日期:</td>
            <td><fmt:formatDate value="${orderBillSummary.billSummaryDate}" pattern="yyyy-MM-dd"/></td>
            <td class="lbl">结算方式:</td>
            <td id="billType">${orderBillSummary.billType}</td>
        </tr>
        <tr>
            <td class="lbl">当期结算额:</td>
            <td>${orderBillSummary.totalBillPrice}</td>
            <td class="lbl">实际收款:</td>
            <td>${orderBillSummary.totalBillPrice-orderBillSummary.refundPrice}</td>
            <td class="lbl">结算状态:</td>
            <td id="status">${orderBillSummary.status}</td>
        </tr>
    </table>

    <table id="orderDetailGrid" style="width:100%; height:430px;"></table>
</body>
<script type="text/javascript" src="/js/order/order/billDetailShenzhou.js"></script>
</body>
</html>
