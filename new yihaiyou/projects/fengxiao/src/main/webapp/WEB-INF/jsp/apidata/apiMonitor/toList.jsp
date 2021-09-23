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
    <title>接口监控</title>
    <%@ include file="../../common/common141.jsp" %>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="dg"></table>
    </div>
    <!-- 数据表格 终-->
</div>
</body>
<script type="text/javascript" src="/js/apidata/apiMonitor/toList.js"></script>
</body>
</html>
