<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/4/28
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>行程规划列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/plan.list.css">
</head>
<body>
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div id="toolbar" style="padding:10px 0 5px 10px">
        <table id="qryCondition">
            <tr>
                <td width="80" align="right">城市:</td>
                <td>
                    <input type="hidden" id="qry_cityCode">
                    <input type="hidden" id="qry_isChina">
                    <input id="qryCity" class="easyui-textbox"
                           data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                           style="width:200px" data-country="" data-province="" data-city="">
                    <%--<select class="easyui-combobox" name="province" id="qry_province" style="width:120px">--%>
                    <%--</select>--%>
                    <%--<select class="easyui-combobox" name="city" id="qry_city" style="width:120px">--%>
                    <%--</select>--%>
                </td>
                <td width="40" align="right">筛选:</td>
                <td>
                    <select class="easyui-combobox" name="condition" id="qry_condition" style="width: 80px;"
                            data-options="panelHeight: 'auto'">
                        <option value="0" selected="selected">全部</option>
                        <option value="nickName">昵称</option>
                        <option value="userName">用户名</option>
                        <option value="planName">行程名</option>
                        <%--<option value=""></option>--%>
                    </select>
                </td>
                <td width="80" align="right">查询内容:</td>
                <td><input class="easyui-textbox" id="qry_content" name="name"
                           data-options="prompt:'请输入查询内容'" style="width:150px;"></td>
                <td>
                <td width="40" align="right">排序:</td>
                <td>
                    <select class="easyui-combobox" name="condition" id="qry_orderProperty" style="width: 90px;"
                            data-options="panelHeight: 'auto'">
                        <option value="startTime" selected="selected">出发时间</option>
                        <option value="createTime" selected="selected">创建时间</option>
                        <option value="planDays">行程天数</option>
                    </select>
                    <select class="easyui-combobox" name="condition" id="qry_orderType" style="width: 60px;"
                            data-options="panelHeight: 'auto'">
                        <option value="desc" selected="selected">降序</option>
                        <option value="asc">升序</option>
                    </select>
                </td>
                <td>
                    <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton"
                       style="width:80px; margin-left: 10px;"
                       onClick="PlanMgr.doSearch();">查询</a>
                    <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px"
                       onClick="PlanMgr.reset();">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',border:false">
        <table id="planDataDg"></table>
    </div>
    <div class="easyui-dialog plan_dialog" id="plan_panel" closed="true" style="width:500px;top: 80px;">
        <form id="plan_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程&nbsp;&nbsp;&nbsp;&nbsp;ID：</label>
                    <input class="disa" name="plan.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label>行&nbsp;&nbsp;程&nbsp;&nbsp;名&nbsp;&nbsp;称：</label>
                    <input type="text" name="plan.name" style="width: 350px;">
                </li>
                <li>
                    <label>副&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
                    <input type="text" name="plan.subTitle" style="width: 350px;">
                </li>
                <li>
                    <label>专家推荐理由：</label>
                    <textarea class="vam" name="plan.recReason" style="width: 350px; height: 80px;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="PlanMgr.commitForm('plan_form', 'plan_panel')">保存修改</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
<script type="text/javascript" src="/js/area.js"></script>
<script type="text/javascript" src="/js/plan/list.js"></script>
</body>
</html>
