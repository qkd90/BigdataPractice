<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/12/19
  Time: 15:19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>消息模板管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/coupon.list.css">
</head>
<body>
<div title="消息模板管理" data-options="fit:true,border:false"
     style="width:100%;height:100%;" class="easyui-layout" id="content">
    <div id="msgTemplate-searcher" style="padding:3px">
        <input id="search-content" placeholder="输入模板标题/内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <label>按</label>
            <select id="search-sort-property">
                <option value="id">ID</option>
                <option value="createTime" selected>创建时间</option>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>降序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
           onclick="MsgTemplateList.doSearch()">查询</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="msgTemplateDg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog coupon_dialog" id="msg_template_panel" closed="true" style="width:550px; top: 80px;">
        <form id="msg_template_form" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="msgTemplate.id">
            <ul>
                <li>
                    <label>模板标题：</label>
                    <input type="text" name="msgTemplate.title" class="easyui-validatebox" style="width: 370px;" data-options="required:true">
                </li>
                <li>
                    <label>模板内容：</label>
                    <textarea class="easyui-validatebox vam" name="msgTemplate.content" data-options="required:true" style="width: 370px; height: 80px; background: #FFFFFF;"></textarea>
                </li>
                <li>
                    <label>使用帮助：</label>
                    <textarea class="vam" name="msgTemplate.description" style="width: 370px; height: 80px; background: #FFFFFF;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="MsgTemplateList.commitForm('msg_template_form', 'msg_template_panel')">保存</a>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="/js/lxbcommon/msgTemplate/list.js"></script>
</html>
