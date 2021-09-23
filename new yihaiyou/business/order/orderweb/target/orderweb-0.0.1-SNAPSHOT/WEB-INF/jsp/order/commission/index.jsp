<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/10/13
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>佣金页面</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/order.commission.index.css"/>
</head>
<body>

<div class="easyui-layout order-table main-wrap">
    <div class="easyui-layout table-panel" data-options="region:'center',title:'模块列表'">
        <table id="commission-table" class=" easyui-datagrid" data-options="fit:true">
        </table>
    </div>
</div>


<script type="text/javascript" src="/js/order/commission/commission.js"></script>
</body>
</html>
