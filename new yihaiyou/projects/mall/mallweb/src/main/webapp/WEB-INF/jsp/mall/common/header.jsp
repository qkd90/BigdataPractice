<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page import="com.data.data.hmly.service.entity.Member" %>
<%@ page import="com.zuipin.util.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>
<link rel="stylesheet" href="${mallConfig.resourcePath}/css/mall/common/header.css?v=${mallConfig.resourceVersion}"/>
<div class="container-fluid" id="top">
    <div class="container">
        <div class="row">
            <div class="col-xs-5">旅行帮-旅游同业采购分销第一平台</div>
            <div class="col-xs-7">
                <% if (CURRENT_USER == null) { %>
                <div class="toplinks pull-right"><a href="/user/register/personal.jhtml">免费注册</a>
                    <a href="#" data-toggle="modal" data-target="#loginModal">登录</a><a href="#" class="top-wx"></a><a
                        href="#" class="top-wb"></a><a href="#" class="top-qq"></a></div>
                <% } else { %>
                <div class="toplinks pull-right">
                    <a href="/user/home/order.jhtml">欢迎，<%=StringUtils.isBlank(CURRENT_USER.getUserName())?CURRENT_USER.getAccount():CURRENT_USER.getUserName()%></a>
                    <a href="/user/user/exitLogin.jhtml">退出登录</a>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</div>
<div class="container" id="header">
    <div class="row">
        <div class="col-xs-4">
            <div class="logo pull-left"><a href="/"><img src="${mallConfig.resourcePath}${mallConfig.logoPath}"></a>
            </div>
            <div class="city pull-left">
                <a href="#" class="curr" id="selectcity">厦门站</a>
                <!--城市弹出选择开始-->
                <jx:include fileAttr="${GLOBAL_HEADER}" targetObject="commonBuilder" targetMethod="build"></jx:include>
                <!--城市弹出选择结束-->
            </div>
        </div>
        <div class="col-xs-8">
            <dl class="topsearch">
                <dt><a href="#">线路</a> | <a href="#">门票</a> | <a href="#">供应商</a></dt>
                <dd>
                    <input name="" type="text" class="search-kw" placeholder="请输入目的地或者线路名称关键字">
                    <input name="" type="button" class="search-bt" value="搜索">
                </dd>
            </dl>
        </div>



    </div>
</div>
<div class="container-fluid" id="topnav">
    <div class="container">
        <div class="mainnav"><a href="/" class="curr" id="navIndex">首页</a> <a href="/mall/line/list.jhtml" id="navLine">旅游线路</a> <a href="/mall/ticket/list.jhtml" id="navTicket">景点门票</a> <a
                href="/mall/supplier/list.jhtml" id="navSupplier">供应商</a> <span href="#" class="pull-right cart"></span></div>
    </div>
</div>
<% if (CURRENT_USER==null) { %>
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                <%--<h4 class="modal-title" id="myModalLabel">Modal title</h4>--%>
            <%--</div>--%>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/jsp/user/user/login.jsp"></jsp:include>
            </div>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

<% } %>


<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/mall/common/header.js?v=${mallConfig.resourceVersion}"></script>
<!--首页登录框-->
<script src="${mallConfig.resourcePath}/js/jquery.validation.min.js"></script>
<script src="${mallConfig.resourcePath}/js/user/login/login.js?v=${mallConfig.resourceVersion}"></script>