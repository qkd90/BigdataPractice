<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/24
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信菜单资源管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/wechat/wechat/res_manage.js"></script>
    <style type="text/css">
        #resource-form table td {padding:10px;}
    </style>
</head>
<body>

<div title="资源管理">
    <div id="content" title="" class="easyui-layout" data-options="fit:true,border:false"
         style="width:100%;height:100%;">
        <!--
        <div class="easyui-panel" title="查询条件" style="padding: 10px;height:120px" data-options="region:'north',border:true">
        </div>
        -->

        <!-- 表格工具条 始 -->
        <div id="tb">
            <div style="padding:2px 5px;">
                <form id="searchform">
                    <label>资源名称：</label>
                    <input id="resName" style="width:120px;"/>
                    <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="WechatResource.doSearch()">
                        查询
                    </a>
                </form>
            </div>
            <div style="padding:2px 5px;">
                <a href="javascript:void(0)" onclick="WechatResource.addForm()" class="easyui-linkbutton"
                   >新增</a>
                <a href="javascript:void(0)" onclick="WechatResource.doValid('dg');" class="easyui-linkbutton"
                   >有效</a>
                <a href="javascript:void(0)" onclick="WechatResource.doInvalid('dg');" class="easyui-linkbutton"
                   >无效</a>
            </div>
        </div>
        <!-- 表格工具条 终 -->

        <!-- 数据表格 始 -->
        <div data-options="region:'center',border:false">
            <table id="dg"></table>
        </div>
        <!-- 数据表格 终-->

        <div class="easyui-dialog" id="resource-panel" closed="true" style="width:350px;top: 80px;">
            <form id="resource-form" action="/wechat/wechatResource/saveWechatResource.jhtml" method="post" enctype="multipart/form-data">
                <input type="hidden" id="res_id" name="id">
                <table>
                    <tr>
                        <td class="label">资源名称:</td>
                        <td>
                            <input id="res-name" name="resName" style="width:120px;" class="valuezone" maxlength="10" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">资源类型:</td>
                        <td>
                            <select id="res-type" class="easyui-combobox" name="resType" style="width:200px;">
                                <option value="click">click</option>
                                <option value="view">view</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">内容:</td>
                        <td>
                            <input id="res-content" name="content" style="width:120px;" class="valuezone" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">资源对象参数:</td>
                        <td>
                            <input id="res-ObjectParam" name="resObjectParam" style="width:120px;" class="valuezone" >
                        </td>
                    </tr>
                    <tr>
                        <td class="label">是否有效:</td>
                        <td>
                            <select id="res-valid" class="easyui-combobox" name="validFlag" style="width:200px;">
                                <option value="true">有效</option>
                                <option value="false">无效</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input id="submitbutton" type="submit" value="提交">
                        </td>
                    </tr>
                </table>
            </form>

        </div>

    </div>
</div>

</body>
</html>
