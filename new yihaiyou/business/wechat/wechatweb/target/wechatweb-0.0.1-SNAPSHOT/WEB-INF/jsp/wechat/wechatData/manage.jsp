<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/20
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/wechat/wechat/data_manage.js"></script>
    <title>公众号管理</title>
</head>
<body>
<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div id="tabs" class="easyui-tabs" fit="true">
        <div id="text" title="文本素材">
            <div id="text-tool" style="padding: 5px;">

                <input class="easyui-textbox" id="text_keword" data-options="prompt:'请输入文本标题'"
                       style="width:200px;line-height:20px;border:1px solid #ccc">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                    onclick="WechatData.doSearch('text_dg');">查询</a>
                <div style="margin-right:20px;float: right;">
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       onclick="WechatData.addText()">添加文本素材</a>
                </div>
            </div>

            <!-- 数据表格 始 -->
            <div data-options="region:'center',border:false" style="height: 100%">
                <table id="text_dg"></table>
            </div>
            <!-- 数据表格 终-->
        </div>

        <div id="news" title="图文素材">
            <div id="newsTool" style="padding: 5px; height: auto">
                <input class="easyui-textbox" id="news_keword" data-options="prompt:'请输入图文标题'"
                       style="width:200px;line-height:20px;border:1px solid #ccc">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                    onclick="WechatData.doSearch('news_dg');">查询</a>

                <div style="margin-right:20px;float: right;">
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       onclick="WechatData.addNews()">添加图文素材</a>
                </div>
            </div>
            <!-- 数据表格 始 -->
            <div data-options="region:'center',border:false" style="height: 100%">
                <table id="news_dg"></table>
            </div>
            <!-- 数据表格 终-->
        </div>
    </div>

    <div id="editText" class="easyui-dialog" title="新增文本素材" data-options="modal:true,closed:true">
        <iframe name="editTextIframe" id="editTextIframe" scrolling="no" frameborder="0"
                style="width: 480px;height:420px;"></iframe>
    </div>
    <div id="editNews" class="easyui-dialog" title="图文素材"
         data-options="fit:true,resizable:true,modal:true,closed:true">
        <iframe name="editNewsIframe" id="editNewsIframe" frameborder="0" style="width:100%; height:700px;"></iframe>
    </div>
</div>
</body>
</html>
