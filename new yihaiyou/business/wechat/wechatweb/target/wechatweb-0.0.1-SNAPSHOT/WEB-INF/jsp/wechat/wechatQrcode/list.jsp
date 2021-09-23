<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/27
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>二维码管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/wechat/wechat/qrcode.js"></script>
    <link href="/css/wechat/qrcode.css" rel="stylesheet">
</head>
<body style="background-color: white; padding: 0px; margin: 0px;">

<div id="total" style="width: 100%; height: 100%;">
        <input id="accountId" type="hidden" value="${accountId}">
    <div id="toolbar" style="padding: 10px;">
        <a id="btn" href="#" class="easyui-linkbutton" onclick="WechatQrcode.addQrcode()" >新建二维码</a>
    </div>

    <div id="dg_qrcode" style="width: 100%; height: 100%;">
    </div>

    <%--<div id="qrcode-panel">--%>

    <%--</div>--%>

    <%--编辑菜单--%>
    <div class="easyui-dialog" id="edit-panel" closed="true">
        <form id="edit-form" method="post">
            <input id="qrcodeId" name="id" type="hidden">
            <div style="padding:10px;">
                <div style="float: left; width: 67px; text-align: right;">参数代码:</div>
                <label style="margin-left: 5px;"><input id="sceneStr" name="sceneStr" class="easyui-textbox" data-options="validType:['validateSceneStrExist', 'maxLength[60]']"
                              required="true"/></label>
            </div>

            <div style="padding:10px;">
                <div style="float: left; width: 67px; text-align: right;">二维码名称:</div>
                <label style="margin-left: 5px;"><input id="name" name="name" class="easyui-textbox" maxlength="10"
                              required="true"/></label>
            </div>
            <div id="btn-div" style="text-align: right;">
                <a href="#" class="easyui-linkbutton" onclick="WechatQrcode.closeEdit()"  >取消</a>
                <a href="#" class="easyui-linkbutton" onclick="WechatQrcode.makeQrcode()"  >确认</a>
            </div>
        </form>
    </div>
</div>


<%--<script id="qrcodeTmpl" type="text/x-jsrender">--%>
    <%--<div class="qrcode-div float-left">--%>
        <%--<div class="cf">--%>
            <%--<div class="qrcode-name float-left">{{:name}}</div>--%>
            <%--<div class="deal-div float-right">--%>
                <%--<a href="#" onclick="WechatQrcode.editForm({{:id}})" class="qrcode-deal">修改</a>--%>
                <%--<a href="/wechat/wechatQrcode/delWechatQrcode.jhtml?id={{:id}}&&accountId={{:wechatAccount.id}}" class="qrcode-deal">删除</a>--%>
                <%--<a href="/wechat/wechatQrcode/download.jhtml?id={{:id}}" class="qrcode-deal">下载</a>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div>--%>
            <%--<img class="qrcode-img" src="{{:path}}">--%>
        <%--</div>--%>
    <%--</div>--%>

<%--</script>--%>

<%--<script>--%>
    <%--WechatQrcode.initList(${accountId});--%>
<%--</script>--%>

</body>
</html>
