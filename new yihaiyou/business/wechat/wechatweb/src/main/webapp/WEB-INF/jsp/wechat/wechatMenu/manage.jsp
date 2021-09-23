<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/25
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信菜单管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/wechat/wechat/menu_manage.js"></script>
    <link href="/css/wechat/menu.css" rel="stylesheet">
</head>
<body>

<div>


    <div class="cf">
        <input id="accountId" type="hidden" value="${accountId}">
        <div id="menu-tree" class="float-left padding-left border-div">
            <p>菜单树</p>
            <ul id="tree"></ul>
        </div>

        <%--编辑菜单--%>
        <div id="menu-edit-form" class="float-left padding-left border-div">
            <form id="menu-form" method="post">
                <table>
                    <input id="id" type="hidden">
                    <tr id="parent-tr">
                        <td class="label">父菜单:</td>
                        <td>
                            <input id="parentId" class="easyui-combobox" name="parentId">
                        </td>
                    </tr>
                    <tr>
                        <td class="label">菜单名称:</td>
                        <td>
                            <input id="menuName" name="menuName" style="width:120px;" class="easyui-textbox valuezone" maxlength="10" required="true">
                            	
                            </input>
                        </td>
                    </tr>
                    <tr id="resource-tr" class="resource">
                        <td class="label">资源:</td>
                        <td>
                            <input id="resource" class="easyui-combobox"  style="width:80px;" name="resourceId">
<!--                             	<option value="" selected="selected"></option> -->
                            </input>
                        </td>
                    </tr>
                    <tr class="resource">
                        <td class="label">外部链接:</td>
                        <td>
                            <input id="exterminal-link" name="url" style="width:120px;" class="easyui-textbox valuezone" maxlength="10" data-options="validType:['url','maxLength[100]']"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div id="mm" class="easyui-menu" style="width: 120px;">
            <%--<div onclick="WechatMenu.up()" iconcls="icon-up">--%>
                <%--上移--%>
            <%--</div>--%>
            <%--<div onclick="WechatMenu.down()" iconcls="icon-down">--%>
                <%--下移--%>
            <%--</div>--%>
            <div onclick="WechatMenu.remove()" iconcls="icon-remove">
                删除
            </div>

        </div>
    </div>



    <div id="btn">
        <button onclick="WechatMenu.addMenu()">添加菜单</button>
        <button id="saveAll" onclick="WechatMenu.submitMenu()">提交</button>
    </div>
</div>

<script>
    WechatMenu.initTree(${accountId});
    WechatMenu.initCombox();
</script>

</body>
</html>
