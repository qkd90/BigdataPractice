<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page import="com.zuipin.util.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>
<link rel="stylesheet" href="${mallConfig.resourcePath}/css/mall/supplier/header.css?v=${mallConfig.resourceVersion}"/>
<div class="container-fluid" id="top">
  <div class="container">
    <div class="row">
      <div class="col-xs-5">旅行帮-旅游同业采购分销第一平台</div>
      <div class="col-xs-7">
        <% if (CURRENT_USER == null) { %>
        <div class="toplinks pull-right"><a href="/user/register/personal.jhtml">免费注册</a><a href="/">登录</a><a href="#" class="top-wx"></a><a
                href="#" class="top-wb"></a><a href="#" class="top-qq"></a></div>
        <% } else { %>
        <div class="toplinks pull-right">
          <a href="#">欢迎，<%=StringUtils.isBlank(CURRENT_USER.getUserName())?CURRENT_USER.getAccount():CURRENT_USER.getUserName()%></a>
          <a href="/user/user/exitLogin.jhtml">退出登录</a>
        </div>
        <% } %>
      </div>
    </div>
  </div>
</div>
<div class="container" id="gys-header">
  <div class="row">
    <div class="col-xs-12">
      <h1><a href="">${unit.name} </a></h1>
    </div>
  </div>
</div>
<div class="container-fluid" id="topnav">
  <div class="container">
    <div class="mainnav"> <a href="/mall/supplier/home.jhtml?id=${unit.id}" class="curr">企业首页</a> <a class="supplier-line" href="/mall/supplier/lines.jhtml?id=${unit.id}">旅游线路</a> <a class="supplier-ticket" href="/mall/ticket/gysTicket.jhtml?supplierId=${unit.id}">景点门票</a> <a class="supplier-about" href="/mall/supplier/about.jhtml?id=${unit.id}">关于我们</a> <span href="#" class="pull-right cart"></span> </div>
  </div>
</div>
<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
