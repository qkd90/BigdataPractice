<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page import="com.data.data.hmly.service.entity.UserType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="jx" uri="/includeTag"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>供应商 旅游线路</title>
<link href="${mallConfig.resourcePath}/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}"
	rel="stylesheet" />
<link
	href="${mallConfig.resourcePath}/css/gys-xianlu.css?v=${mallConfig.resourceVersion}"
	rel="stylesheet" />
<!--[if lt IE 9]>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js?v=${mallConfig.resourceVersion}"></script>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/respond.js/1.4.2/respond.min.js?v=${mallConfig.resourceVersion}"></script>
  <![endif]-->
</head>
<body>

	<!--供应商头部开始-->

	<jsp:include page="header.jsp"></jsp:include>
	<!--头部结束-->
	<input id="line-type" type="hidden" value="supplier-lines">
	<input id="supplierId" type="hidden" value="${unit.id}">
	<div class="container-bg">
		<div class="container">
			<div class="col-xs-12">
				<form class="form-inline pull-right" id="xl-search">
					<input type="text" class="form-control" id="kw"
						placeholder="请输入关键词">
					<input class="btn btn-warning"
						type="button" value="搜索" id="search-btn">
				</form>
			</div>
			<!--筛选类型-->
			<jx:include fileAttr="${LINE_SEARCHER}"></jx:include>
			<!--筛选类型-->
			<!--线路列表-->
			<div class="listtop text-center col-xs-12">
				<span class="t1 col-xs-3">线路封面</span> <span class="t2 col-xs-4">名称/编号/发团日期</span><span
					class="t2 col-xs-2">行程天数</span> <span class="t3 col-xs-1">销售价</span>
				<% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
					<span class="t4 col-xs-1">分销价</span>
				<% } else { %>
					<span class="t4 col-xs-1"></span>
				<% } %>

				<span class="t5 col-xs-1">操作</span>
			</div>
			<!--线路列表-->
			<div class="box col-xs-12">
				<div id="linesPanel">
				<s:if test="#request.lines.size() == 0">
					找不到对象
				</s:if>
				<s:else>
					<c:forEach items="${lines }" var="line">
						<div class="list clearfix">
							<div class="col-xs-3 text-center">
								<a href="/mall/line/detail.jhtml?id=${line.id }">
									<img src="${mallConfig.imguriPreffix}${line.productImg }" style="width: 220px; height: 120px">
									<%--<h5>${line.supplierName }</h5> </a>--%>
							</div>
							<div class="col-xs-4">
								<dl>
									<dt>
										<a href="/mall/line/detail.jhtml?id=${line.id }"> ${line.name }</a>
									</dt>
									<dd>${line.linePoint }</dd>
									<dd>
										编号：BL02 团期：10/11 、 10/12 、 10/13 <a href="#">[更多]</a>
									</dd>
								</dl>
							</div>
							<div class="col-xs-2 text-center">2天</div>
							<div class="col-xs-1 text-center">¥${line.disCountPrice }</div>
							<% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
								<div class="col-xs-1 text-center">¥${line.yjPrice }</div>
							<% } else { %>
								<div class="col-xs-1 text-center"></div>
							<% } %>

							<div class="col-xs-1 text-center action">
								<a href="/mall/line/detail.jhtml?id=${line.id }" class="btn btn-warning">立即预定</a>
								<% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
									<%--<a href="#" class="btn btn-primary">立即上架</a>--%>
								<% } %>

							</div>
						</div>
					</c:forEach>
				</s:else>
				</div>

				<!--分页-->
				<div class="clearfix text-right">
					<ul class="pagination" id="page-list">
					</ul>
				</div>


				<!--分页-->
			</div>
			<!--线路列表-->

		</div>
		<!--底部-->
		<jsp:include page="footer.jsp"></jsp:include>
		<!--底部-->

		<div style="display: none;" id="pageIndex">${pageIndex }</div>
		<div style="display: none;" id="totalCount">${totalCount }</div>


		<script id="lineTmpl" type="text/x-jsrender">
    							<div class="list clearfix">
									<div class="col-xs-3 text-center">
										<a href="/mall/line/detail.jhtml?id={{:id}}">
											<img src="{{:productImg }}" alt="" style="width: 220px; height: 120px"/>
											<%--<h5>{{:supplierName }}</h5> --%>
										</a>
									</div>
									<div class="col-xs-6">
										<dl>
											<dt>
												<a href="/mall/line/detail.jhtml?id={{:id}}">{{:name }}</a>
											</dt>
											<dd>{{:linePoint }}</dd>
											<!-- <dd>
												编号：BL02 团期：10/11 、 10/12 、 10/13 <a href="#">[更多]</a>
											</dd> -->
											<!-- <dd>
												<img src="images/hot.jpg" alt="" />
											</dd> -->
										</dl>
									</div>
									<div class="col-xs-1 text-center">¥{{:disCountPrice }}
									</div>
									<% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
										<div class="col-xs-1 text-center">¥{{:yjPrice }}</div>
									<% } else { %>
										<div class="col-xs-1 text-center"></div>
									<% } %>

									<div class="col-xs-1 text-center action">
										<a href="/mall/line/detail.jhtml?id={{:id}}" class="btn btn-warning">立即预定</a>
										<% if (CURRENT_USER != null && CURRENT_USER.getUserType() != UserType.USER) { %>
											<%--<a href="#" class="btn btn-primary">立即上架</a>--%>
										<% } else { %>
										<% } %>
									</div>
								</div>
    </script>

		<script src="${mallConfig.resourcePath}/js/jquery.min.js"></script>
		<script src="${mallConfig.resourcePath}/js/bootstrap.min.js"></script>
		<script src="${mallConfig.resourcePath}/js/jsrender.js" type=""></script>
		<script src="${mallConfig.resourcePath}/js/plugs/jquery.pagination.js"
			type=""></script>
		<%--<script src="${mallConfig.resourcePath}/js/custom.js?v=${mallConfig.resourceVersion}"></script>--%>
		<script
				src="${mallConfig.resourcePath}/js/mall/line/supplierLine.js?v=${mallConfig.resourceVersion}">
		</script>
		<script
				src="${mallConfig.resourcePath}/js/mall/line/line.js?v=${mallConfig.resourceVersion}">
		</script>
	</div>
</body>
</html>
