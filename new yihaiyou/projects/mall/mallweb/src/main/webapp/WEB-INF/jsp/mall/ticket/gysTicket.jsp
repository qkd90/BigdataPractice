<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page import="com.data.data.hmly.service.entity.UserType" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/8
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>供应商 景点门票</title>
  <link href="${mallConfig.resourcePath}/css/proInfo.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/gys-xianlu.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js?v=${mallConfig.resourceVersion}"></script>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/respond.js/1.4.2/respond.min.js?v=${mallConfig.resourceVersion}"></script>
  <![endif]-->
</head>
<body>

<input id="init-city" type="hidden" value="${city}">
<input id="init-level" type="hidden" value="${level}">
<input id="init-cost" type="hidden" value="${cost}">
<input id="init-min" type="hidden" value="${mincost}">
<input id="init-max" type="hidden" value="${maxcost}">
<input id="init-name" type="hidden" value="${ticketName}">
<input id="page" type="hidden" value="${page}">
<input id="total-count" type="hidden" value="${total}">

<!--供应商头部开始-->
<jsp:include page="../supplier/header.jsp"></jsp:include>
<!--头部结束-->
<input id="supplierInfo" type="hidden" value="${unit.id}">
<div class="container-bg">
  <div class="container">
    <div class="col-xs-12">
      <form class="form-inline pull-right" id="xl-search">
        <input type="text" class="form-control" id="kw" placeholder="请输入关键词">
        <input class="btn btn-warning" type="button" value="搜索" id="search-btn">
      </form>
    </div>
    <!--筛选类型-->
    <div class="box typelist col-xs-12">
      <dl class="clearfix" id="city">
        <dt><span class="title">所在地：</span><span id="nonecity" class="curr" href="#">不限</span></dt>
        <dd>  </dd>
      </dl>
      <dl class="clearfix" id="scenic-level">
        <dt><span class="title">景点星级：</span><span id="nonelevel" class="curr" href="#">不限</span></dt>
        <dd>  </dd>
      </dl>
      <dl class="clearfix last" id="ticket-cost">
        <dt><span class="title">价格区间：</span><span class="curr" id="nonecost" href="#">不限</span></dt>
        <dd> <a id="c-0-30" href="#">30元以下</a><a id="c-30-50" href="#">30-50元</a><a id="c-50-100" href="#">50-100元</a><a id="c-100-200" href="#">100-200元</a><a id="c-200-500" href="#">200-500元</a><a id="c-500" href="#">500元以上</a>
      </dl>
      <!--筛选类型-->
    </div>
    <div class="listtop text-center col-xs-12"> <span class="t1 col-xs-3">门票封面</span> <span class="t2 col-xs-6">名称/编号/发团日期</span> <span class="t3 col-xs-1">销售价</span>
        <% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
            <span class="t4 col-xs-1">分销价</span>
        <% } else { %>
            <span class="t4 col-xs-1"></span>
        <% } %>

        <span class="t5 col-xs-1">操作</span> </div>
    <!--线路列表-->
    <div class="box col-xs-12">
      <div id="ticket-list">
          <s:if test="#request.tickets.size() == 0">
              找不到对象
          </s:if>
          <s:else>
              <c:forEach items="${tickets }" var="ticket">

                  <div class="list clearfix">
                      <div class="col-xs-3 text-center">
                          <a href="/mall/ticket/detail.jhtml?id=${ticket.id}">
                              <img src="<c:if test="ticket.productImg != null">{mallConfig.imguriPreffix}${ticket.productImg}</c:if>" style="width: 220px; height: 120px">
                              <%--<h5>${ticket.supplierName }</h5>--%>
                          </a>
                      </div>
                      <div class="col-xs-6">
                          <dl>
                              <dt>
                                  <a href="/mall/ticket/detail.jhtml?id=${ticket.id}">
                                      ${ticket.name}
                                  </a>
                              </dt>
                              <dd class="proInfo">${ticket.productAttr}</dd>
                              <dd> <a href="/mall/ticket/detail.jhtml?id=${ticket.id}">[更多]</a></dd>
                          </dl>
                      </div>
                      <div class="col-xs-1 text-center">¥${ticket.disCountPrice }</div>
                      <% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
                            <div class="col-xs-1 text-center">¥${ticket.yjPrice }</div>
                      <% } else { %>
                            <div class="col-xs-1 text-center"></div>
                      <% } %>

                      <div class="col-xs-1 text-center action">
                          <a href="/mall/ticket/detail.jhtml?id=${ticket.id}" class="btn btn-warning">立即预定</a>
                          <% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
                             <%--<a href="#" class="btn btn-primary">立即上架</a>--%>
                          <% } else { %>
                          <% } %>

                      </div>
                  </div>
              </c:forEach>
          </s:else>
      </div>


      <!--分页-->

        <div class="clearfix text-right">
            <ul class="pagination" id="page-list">
                <%-- <li class="disabled"><span>共有 8 条记录</span></li> --%>
            </ul>
        </div>


      <!--分页-->
    </div>
    <!--线路列表-->

  </div>
  <!--底部-->
  <jsp:include page="../supplier/footer.jsp"></jsp:include>
  <!--底部-->


    <script id="ticketTmpl" type="text/x-jsrender">
            <div class="list clearfix">
                <div class="col-xs-3 text-center">
                    <a href="/mall/supplier/home.jhtml?id={{:supplierId}}">
                        <img src="{{:productImg }}" style="width: 220px; height: 120px"/>
                        <%--<h5>{{:supplierName }}</h5>--%>
                    </a>
                </div>
                <div class="col-xs-6">
                    <dl>
                        <dt>
                            <a href="/mall/ticket/detail.jhtml?id={{:id}}">
                                {{:name }}
                            </a>
                        </dt>
                        <dd class="proInfo">{{:productAttr}}</dd>
                        <dd><a href="/mall/ticket/detail.jhtml?id={{:id}}">[更多]</a></dd>
                    </dl>
                </div>
                <div class="col-xs-1 text-center">¥{{:disCountPrice}}</div>
                <div class="col-xs-1 text-center">¥{{:yjPrice }}</div>
                <div class="col-xs-1 text-center action">
                <a href="/mall/ticket/detail.jhtml?id={{:id}}" class="btn btn-warning">立即预定</a>
                <% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
                    <%--<a href="#" class="btn btn-primary">立即上架</a>--%>
                <% } else { %>
                <% } %>
            </div>

    </script>

    <script src="${mallConfig.resourcePath}/js/jquery.min.js"></script>
    <script src="${mallConfig.resourcePath}/js/bootstrap.min.js"></script>
    <script src="${mallConfig.resourcePath}/js/jsrender.js" type=""></script>
    <script src="${mallConfig.resourcePath}/js/plugs/jquery.pagination.js" type=""></script>
  <%--<script src="${mallConfig.resourcePath}/js/custom.js?v=${mallConfig.resourceVersion}"></script>--%>

  <script src="${mallConfig.resourcePath}/js/mall/ticket/gysTicket.js?v=${mallConfig.resourceVersion}"></script>
</div>
</body>
</html>
