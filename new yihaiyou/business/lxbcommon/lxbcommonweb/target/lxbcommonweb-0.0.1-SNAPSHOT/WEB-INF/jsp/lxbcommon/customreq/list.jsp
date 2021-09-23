<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/6/15
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>私人定制表单列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/customreq.list.css">
</head>
<body>
<div title="私人定制表单管理" data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <div id="customReq-searcher" style="padding:3px">
        <label>出发地:</label>
        <span>
            <input type="hidden" id="qry_cityCode">
                        <input type="hidden" id="qry_isChina">
                        <input id="qryCity" class="easyui-textbox"
                               data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                               style="width:200px" data-country="" data-province="" data-city="">
            <%--<select class="easyui-combobox" name="province" id="qry_province" style="width:80px">--%>
            <%--</select>--%>
            <%--<select class="easyui-combobox" name="city" id="qry_city" style="width:80px">--%>
            <%--</select>--%>
        </span>
        <span>
            <lable>查询条件:</lable>
            <select id="search-type">
                <option value="customRequire.contactor" selected>联系人</option>
                <option value="customRequire.contactPhone">联系手机</option>
                <option value="customRequire.contactEmail">联系邮箱</option>
                <option value="customRequire.handler.userName">处理人</option>
            </select>
        </span>
        <input id="search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <lable>状态:</lable>
            <select id="search-status">
                <option value="" selected>所有状态</option>
                <option value="handling">处理中</option>
                <option value="handled">已处理</option>
                <option value="cancel">已取消</option>
            </select>
        </span>
        <span>
            <label>定制类型:</label>
            <select id="search-customType">
                <option value="" selected>全部</option>
                <option value="company">公司</option>
                <option value="home">家庭</option>
                <option value="other">其他</option>
            </select>
        </span>
        <span>
            <label>行程安排:</label>
            <select id="search-arrange">
                <option value="" selected>全部</option>
                <option value="compact">紧凑</option>
                <option value="moderate">中等</option>
                <option value="loose">宽松</option>
                <option value="unsure">不确定</option>
            </select>
        </span>
        <span>
            <lable>排序:</lable>
            <select id="search-sort-property">
                <option value="createTime" selected>创建时间</option>
                <option value="startDate">出发时间</option>
                <option value="handleTime">处理时间</option>
                <option value="day">游玩天数</option>
                <option value="adult">成人数量</option>
                <option value="child">儿童数量</option>
                <option value="minPrice">最低预算</option>
                <option value="maxPrice">最高预算</option>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>倒序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <a href="#" class="easyui-linkbutton"
           style="width: 60px;" onclick="CustomReqMgr.doSearch()">查询</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="customReqDg"></table>
    </div>
    <!-- 数据表格 终-->
    <%-- detail start --%>
    <div class="easyui-dialog customreq_dialog" id="detail_panel" closed="true" style="width:500px;top: 80px;">
        <form id="detail_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>定制ID：</label>
                    <input class="disa" name="customRequire.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label class="vam">备&nbsp;&nbsp;&nbsp;注：</label>
                    <textarea class="vam" name="customRequire.remark" style="width: 350px; height: 80px;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="CustomReqMgr.commitForm('detail_form', 'detail_panel')">保存</a>
            </div>
        </form>
    </div>
    <%-- detail end --%>
</div>
<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
<script type="text/javascript" src="/js/area.js"></script>
<script src="/js/lxbcommon/customreq/list.js" type="text/javascript"></script>
</body>
</html>
