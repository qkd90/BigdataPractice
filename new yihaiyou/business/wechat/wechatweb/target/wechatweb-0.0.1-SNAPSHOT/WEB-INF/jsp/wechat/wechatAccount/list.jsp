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
  <script type="text/javascript" src="/js/wechat/wechat/list.js"></script>
    <link href="/css/wechat/qrcode.css" rel="stylesheet">
    <link href="/css/wechat/menu.css" rel="stylesheet">
  <title>公众号管理</title>
    <style type="text/css">
        #add-account-form table td {padding:10px;}
    </style>
</head>
<body style="background-color:white;">
<div>

  <div id="searchparam" class="searcharea">
      <div style="padding: 5px;">
          <label>公众号：</label>
          <input id="search-account" class="easyui-textbox" name="account" type="text">
          <a id="searchbtn" href="#"  class="easyui-linkbutton"  onclick="WechatAccount.doSearch()">查询</a>
          <a id="reloadbtn" href="#"  class="easyui-linkbutton"  onclick="WechatAccount.doClear()">重置</a>
      </div>
      <div style="padding: 5px;">
          <a id="addbtn" href="#"  class="easyui-linkbutton"  onclick="WechatAccount.addAccount()">新增</a>
          <a id="editbtn" href="#"  class="easyui-linkbutton"  onclick="WechatAccount.editInfo()">编辑</a>
          <a  href="#"  class="easyui-linkbutton"  onclick="WechatAccount.menuDialog()">菜单编辑</a>
          <a  href="#"  class="easyui-linkbutton"  onclick="WechatAccount.syncMenu()">菜单同步</a>
      </div>
  </div>


  <div style="width: 100%; height: 100%;">
    <table id="accountDg"></table>
  </div>

  <div class="easyui-dialog" id="add-account-dialog" closed="true" style="width:380px; padding: 10px">
        <form id="add-account-form" action="" method="post">
                <table>
                    <tr>
                    	<input type="hidden" id="id" name="id">
                        <td class="label">帐号名称:</td>
                        <td>
                            <input id="account" class="easyui-textbox" name="account" style="width:200px;" class="valuezone" maxlength="10" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">APPID:</td>
                        <td>
                            <input id="appId" class="easyui-textbox" name="appId" style="width:200px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">APPSECRET:</td>
                        <td>
                            <input id="appSecret" class="easyui-textbox" name="appSecret" style="width:200px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">ORIGINALID:</td>
                        <td>
                            <input id="originalId" class="easyui-textbox" name="originalId" style="width:200px;" class="valuezone" data-options="required:true,validType:'origId'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">MCHKEY:</td>
                        <td>
                            <input id="mchKey" class="easyui-textbox" name="mchKey" style="width:200px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                     <tr>
                        <td class="label">MCHID:</td>
                        <td>
                            <input id="mchId" class="easyui-textbox" name="mchId" style="width:200px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                     <tr>
                        <td class="label">DEFAULTTPLID:</td>
                        <td>
                            <input id="defaultTplId" class="easyui-textbox" name="defaultTplId" style="width:200px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <a class="easyui-linkbutton" id="submitbutton" onClick="WechatAccount.submitData()">提交</a>
                        </td>
                    </tr>
                </table>
        </form>

  </div>


    <div id="qrcode-dialog">
        <iframe name="step1Iframe" id="step1Iframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
    </div>
    <div id="menu-dialog">

    </div>



</div>

<script id="qrcodeTmpl" type="text/x-jsrender">
    <div class="qrcode-div float-left">
        <div class="cf">
            <div class="qrcode-name float-left">{{:name}}</div>
            <div class="deal-div float-right">
                <a href="#" onclick="WechatQrcode.editForm({{:id}})" class="qrcode-deal">修改</a>
                <a href="/wechat/wechatQrcode/delWechatQrcode.jhtml?id={{:id}}&&accountId={{:wechatAccount.id}}" class="qrcode-deal">删除</a>
                <a href="/wechat/wechatQrcode/download.jhtml?id={{:id}}" class="qrcode-deal">下载</a>
            </div>
        </div>
        <div>
            <img class="qrcode-img" src="{{:path}}">
        </div>
    </div>

</script>
<script type="text/javascript" src="/js/wechat/wechat/qrcode.js"></script>
<script type="text/javascript" src="/js/wechat/wechat/menu_manage.js"></script>



</body>
</html>
