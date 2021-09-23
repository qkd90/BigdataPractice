<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/2
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户提现列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/member.list.css">
</head>
<body>

<div title="客户管理" data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <!-- 表格工具条 始 -->
    <div id="member-searcher" style="padding:3px">
        <input id="search-orderNo" class="easyui-textbox" data-options="prompt:'输入编号'"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">

        <input id="search-userName" class="easyui-textbox" data-options="prompt:'输入用户名'"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">

        <input id="search-priceStart" class="easyui-numberbox"  data-options="prompt:'查询金额始', min:0, precision:2"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px; width: 100px;">-
        <input id="search-priceEnd" class="easyui-numberbox" data-options="prompt:'查询金额末', min:0, precision:2"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px; width: 100px;">
             <span>
                 <input id="search-status" type="text" class="easyui-combobox" style="width: 120px;"
                        data-options="
                        valueField: 'id',
                        textField: 'text',
                        panelHeight: 'auto',
                        prompt:'状态',
                        data:[
                        {id:'WAIT', text:'待审核'},
                        {id:'FAILED', text:'提现失败'},
                        {id:'CLOSED', text:'已关闭'},
                        {id:'SUCCESS', text:'已提现'}
                        ]
                ">
            </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="CustomerBalance.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="CustomerBalance.clearSearch()">重置</a>
    </div>

    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="memberDg"></table>
    </div>
    <!-- 数据表格 终-->
</div>
<script type="text/javascript" src="/js/customerBalance/customerBalance/list.js"></script>
</body>
</html>
