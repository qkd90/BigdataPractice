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
    <title>客户列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/member.list.css">
</head>
<body>

<div title="客户管理" data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <!-- 表格工具条 始 -->
    <div id="member-searcher" style="padding:3px">
            <span>
                <select class="easyui-combobox" id="search-type">
                    <option value="member.userName">用户名</option>
                    <option value="member.nickName" selected>用户昵称</option>
                    <option value="member.mobile">手机号码</option>
                    <option value="member.telephone">联系电话</option>
                    <option value="member.email">邮箱地址</option>
                </select>
            </span>
        <input id="search-content" class="easyui-textbox" data-options="prompt:'输入查询内容'"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <%--<span>按用户性别搜索：</span>--%>
             <span>
                <select class="easyui-combobox" id="search-gender">
                    <option value="" selected>所有性别</option>
                    <option value="male">男</option>
                    <option value="female">女</option>
                    <option value="secret">保密</option>
                </select>
            </span>
             <span>
                <select class="easyui-combobox" id="search-status">
                    <option value="" selected>所有状态</option>
                    <option value="activity">激活</option>
                    <option value="lock">锁定</option>
                    <option value="del">删除</option>
                    <option value="blacklist">黑名单</option>
                </select>
            </span>
            <%--<span>--%>
                <%--<select id="search-sort-property">--%>
                    <%--<option value="createdTime" selected>注册时间</option>--%>
                <%--</select>--%>
            <%--</span>--%>
            <%--<span>--%>
                <%--<select id="search-sort-type">--%>
                    <%--<option value="desc" selected>倒序</option>--%>
                    <%--<option value="asc">升序</option>--%>
                <%--</select>--%>
            <%--</span>--%>
            <%--<span>--%>
                <%--<label>积分范围：</label>--%>
                <%--<input  style="line-height:22px;border:1px solid #ccc; padding: 0 3px;"--%>
                       <%--id="minJifen" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最少积分">--%>
                <%--<label>-</label>--%>
                <%--<input  style="line-height:22px;border:1px solid #ccc; padding: 0 3px;"--%>
                       <%--id="maxJifen" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最多积分">--%>
            <%--</span>--%>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="Customer.doSearch()">查询</a>
    </div>
    <%--<div id="tb">--%>
    <%--<div style="padding:2px 5px;">--%>
    <%--<form id="searchform">--%>
    <%--<label>账户：</label>--%>
    <%--<input id="account" type="text" name="account" style="width:120px;"/>--%>
    <%--<label>电话：</label>--%>
    <%--<input id="mobile" type="text" name="mobile" style="width:120px;"/>--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
    <%-- onclick="Customer.doSearch()">查询</a>--%>
    <%--</form>--%>
    <%--</div>--%>
    <%--<div style="padding:2px 5px;">--%>
    <%--<a href="javascript:void(0)" onclick="Customer.doTest('customerDg')" class="easyui-linkbutton"--%>
    <%-->test1</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <!-- 表格工具条 终 -->

    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="memberDg"></table>
    </div>
    <!-- 数据表格 终-->

    <div class="easyui-dialog" id="recharge_panel" closed="true" style="width:300px; height:200px; top: 80px;">
        <div style="padding: 10px;">
            <label>账户余额：</label>
            <span id="balance"></span>
        </div>
        <div style="padding: 10px;">
            <label>充值金额：</label>
            <input type="text" id="recharge-balance" class="easyui-numberbox" value="0" data-options="min:0,precision:2" style="width: 180px;"/>
        </div>
    </div>

    <!-- 数据表格 终-->
    <div class="easyui-dialog" id="accountLog_panel" closed="true" style="width:700px; height:400px; top: 80px;">
        <div style="padding: 10px;width: 100%; height: 100%;" id="accoungLogDg">

        </div>
    </div>

    <!--edit-->
    <div class="easyui-dialog" id="edit_panel" closed="true" style="width:500px;top: 80px;">
        <form id="edit_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>用&nbsp;&nbsp;户&nbsp;&nbsp;ID：</label>
                    <input class="disa" name="member.id" disabled style="width: 60px;">
                    <input type="hidden" name="id">
                </li>
                <li>
                    <label>帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</label>
                    <input class="disa" name="member.account" disabled style="width: 240px">
                </li>
                <li>
                    <label>用&nbsp;&nbsp;户&nbsp;&nbsp;名：</label>
                    <input name="member.userName">
                </li>
                <li class="ml8">
                    <label>昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
                    <input name="member.nickName">
                </li>
                <li>
                    <label>注册时间：</label>
                    <input class="disa" name="member.createdTime" disabled style="width: 100px;">
                </li>
                <li>
                    <label>积&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：</label>
                    <input class="disa" name="member.jifen" disabled style="width: 80px;">
                </li>
                <li>
                    <label>余&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</label>
                    <input class="disa" name="member.balance" disabled style="width: 80px;">
                </li>
                <li>
                    <label>头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;像：</label>
                    <input type="file" name="avatar" style="width: 35%">
                    <label>(如需修改,请上传新图片后保存)</label>
                </li>
                <li>
                    <label>生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日：</label>
                    <input name="birthday" id="edit_member_birthday">
                </li>
                <li class="ml8">
                    <label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
                    <input id="edit_user_gender" name="member.gender">
                </li>
                <li>
                    <label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：</label>
                    <input name="member.telephone">
                </li>
                <li class="ml8">
                    <label>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</label>
                    <input name="member.mobile">
                </li>
                <li>
                    <label>电子邮件：</label>
                    <input name="member.email">
                </li>
                <li class="ml8">
                    <label>QQ&nbsp;号码：</label>
                    <input name="member.qqNo">
                </li>
                <li class="ml8">
                    <label>身份证号：</label>
                    <input name="member.idNumber">
                </li>
                <li>
                    <label>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</label>
                    <input name="member.address">
                </li>
                <li class="ml8">
                    <label>帐户状态：</label>
                    <input id="edit_user_status" name="member.status">
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="Customer.commitForm('edit_form', 'edit_panel')">保存</a>
            </div>
            <%--<div style="padding-top: 30px">--%>
                <%--<div>订单列表</div>--%>
                <%--<input id="userId" type="hidden" name="id">--%>
                <%--<input id="orderNum" type="hidden" name="orderNum">--%>

                <%--<div>--%>
                    <%--<table id="orderDg"></table>--%>
                <%--</div>--%>
            <%--</div>--%>
        </form>
    </div>
</div>
<script type="text/javascript" src="/js/customer/customer/list.js"></script>
<script type="text/javascript" src="/js/customer/customer/detail.js"></script>
</body>
</html>
