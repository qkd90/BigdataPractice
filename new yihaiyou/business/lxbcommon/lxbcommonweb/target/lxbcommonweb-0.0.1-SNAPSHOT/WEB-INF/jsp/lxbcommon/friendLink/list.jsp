<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2017/3/3
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>友情链接列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .friendLink_dialog {padding: 5px 10px}
        .friendLink_dialog input{  width: 150px;  padding: 2px;  font-size: 13px;}
        .friendLink_dialog li{  margin-top: 8px;}
        .opt a.ena{  color: #0000cc;  text-decoration: underline;}
        .opt a.disa{  color: #8F8F8F;  text-decoration: none;  cursor: not-allowed;}
    </style>
</head>
<body>
<div title="友情链接管理" data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <div id="friendLink-searcher" style="padding:3px">
        <span>
            <input id="search-content" class="easyui-textbox" data-options="prompt:'链接名称'" style="width:200px;">
        </span>
        <span>
            <input id="search-status" class="easyui-combobox" editable="false"
                   data-options="prompt:'状态',
											valueField:'id',
											textField:'text',
											panelHeight: 'auto',
											data:[
												{text:'全部'},
												{id:'SHOW',text:'展示'},
												{id:'HIDE',text:'隐藏'},
												{id:'EXPIRED',text:'已过期'}
											]
						" style="width:120px; visibility: hidden;">
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="FriendLink.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="FriendLink.doClear()">重置</a>
        <a href="#" class="easyui-linkbutton" style="width: 90px; float: right; margin: 5px 10px 0" onclick="FriendLink.addFriendLink()">添加友情链接</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="friendLinkDg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog friendLink_dialog" id="friendLink_panel" closed="true" style="width:500px;top: 80px;">
        <form id="friendLink_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>链接名称：</label>
                    <input class="easyui-textbox" name="friendLink.linkName" data-options="required:true">
                    <input type="hidden" name="friendLink.id">
                </li>
                <li>
                    <label>链接地址：</label>
                    <input class="easyui-textbox" name="friendLink.url" style="width: 260px;" data-options="required:true">
                </li>
                <li>
                    <label>链接logo：</label>
                    <input id="logoPath" name="friendLink.linkLogo" type="hidden">
                    <input type="button" class="J_add_pic" id="add_logopic" value="添加链接logo" style="width:100px;">
                </li>
                <li>
                    <label>开始日期：</label>
                    <input name="startTimeStr" id="validStart" class="Wdate" data-options="required:true"
                           style="width: 150px;padding: 2px; font-size: 13px;"
                           onclick="WdatePicker({readOnly:true,isShowClear:false,
                               doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss',errDealMode:1,
                               maxDate: '#F{$dp.$D(\'validEnd\')}',
                               minDate: '%y-%M-%d {%H+1}:%m:%s',
                               })">
                </li>
                <li>
                    <label class="vam">结束日期：</label>
                    <input name="endTimeStr" id="validEnd" class="Wdate" data-options="required:true"
                           style="width: 150px;padding: 2px; font-size: 13px;"
                           onclick="WdatePicker({readOnly:true,isShowClear:false,
                               doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss',errDealMode:1,
                               minDate: '#F{$dp.$D(\'validStart\')||\'%y-%M-%d {%H+1}:%m:%s\'}'
                               })">
                </li>
                <li>
                    <label>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
                    <input name="friendLink.status" class="easyui-combobox" editable="false"
                           data-options="required:true, prompt:'状态',
											valueField:'id',
											textField:'text',
											panelHeight: 'auto',
											data:[
												{id:'SHOW',text:'展示'},
												{id:'HIDE',text:'隐藏'},
												{id:'EXPIRED',text:'已过期'}
											]
						" style="width:120px; visibility: hidden;">
                </li>
            </ul>
            <div id="imgView" style="display:none; position: absolute;bottom: 66px;left: 256px;">
                <img src="" width="200" height="120" style="border-radius: 5px;">
                <a href="javascript:;" id="remove_img_id" class="easyui-linkbutton line-btn" onclick="FriendLink.deleteImg()" style="margin-left: -48px;margin-top: -23px;">删除</a>
            </div>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="FriendLink.doSaveFriendLink()">保存</a>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/lxbcommon/friendLink/list.js"></script>
</html>
