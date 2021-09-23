<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/5/11
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户优惠券管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/coupon.list.css">
</head>
<body>
<div title="用户优惠券管理" data-options="fit:true,border:false"
     style="width:100%;height:100%;" class="easyui-layout" id="content">
    <%-- 用户优惠券搜索工具栏--%>
    <div id="user-coupon-searcher" style="padding:3px">
        <span>
            <select id="search-type">
                <option value="userCoupon.coupon.name" selected>优惠券名称</option>
                <option value="userCoupon.member.userName" selected>用户名</option>
                <option value="userCoupon.member.nickName" selected>用户昵称</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <input id="search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <select id="search-status">
                <option value="" selected>所有状态</option>
                <option value="unused">未使用</option>
                <option value="used">已使用</option>
                <option value="expired">已失效</option>
                <option value="unavailable">不可用</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <span>
            <select id="search-sort-property">
                <option value="validEnd" selected>有效期</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>倒序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="UserCouponMgr.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 90px;"
           onclick="UserCouponMgr.openSendCoupon()">发放优惠券</a>
    </div>
    <%-- 用户搜索工具栏 --%>
    <div id="member-searcher" style="padding:3px">
        <span>
            <select id="member-search-type">
                <option value="member.account" selected>帐户</option>
                <option value="member.userName">用户名</option>
                <option value="member.nickName">用户昵称</option>
                <option value="member.mobile">手机号码</option>
                <option value="member.telephone">联系电话</option>
                <option value="member.email">邮箱地址</option>
            </select>
        </span>
        <input id="member-search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="UserCouponMgr.doMemberSearch()">查询</a>
    </div>
    <%-- 优惠券搜索工具栏 --%>
    <div id="coupon-searcher" style="padding:3px">
        <span>
            <select id="coupon-search-type">
                <option value="coupon.name" selected>优惠券名称</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <input id="coupon-search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="UserCouponMgr.doCouponSearch()">查询</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="userCouponDg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog coupon_dialog" id="user_coupon_panel" closed="true" style="width:750px;">
        <form id="user_coupon_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>优惠券：</label>
                    <input class="easyui-combogrid" style="width: 500px;"
                           name="couponIds" id="couponIds_combogrid">
                    <label><em>*</em>请选择要发放的优惠券</label>
                </li>
                <li>
                    <label>用&nbsp;&nbsp;&nbsp;户：</label>
                    <input class="easyui-combogrid" style="width: 500px;;"
                           name="memberIds" id="memberIds_combogrid">
                    <label><em>*</em>请选择要发放用户</label>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="UserCouponMgr.commitForm('user_coupon_form', 'user_coupon_panel')">发放优惠券</a>
            </div>
        </form>
    </div>
</div>
<script src="/js/lxbcommon/coupon/user_coupon.js" type="text/javascript"></script>
</body>
</html>
