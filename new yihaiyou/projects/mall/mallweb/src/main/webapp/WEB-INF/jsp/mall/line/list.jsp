<%@ page import="com.data.data.hmly.service.entity.UserType" %>
<%@page language="java" pageEncoding="UTF-8"%>
<%--<%@include file="/common/include.inc.jsp" %>--%>
<%@taglib prefix="jx" uri="/includeTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%--<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="${mallKey.keyWords}" name="Keywords" />
<meta content="${mallKey.remark}" name="Description" />
<title>${mallConfig.title} - 线路列表</title>
<%--<jsp:include page="/common/common.jsp"/>--%>
<link
	href="${mallConfig.resourcePath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}"
	rel="stylesheet" type="text/css" />
<link
	href="${mallConfig.resourcePath}/css/xianlu.css?v=${mallConfig.resourceVersion}"
	rel="stylesheet" type="text/css" />
<%--<jsp:include page="style.jsp"/>--%>
<!--[if lte IE 6]>
    <script src="${mallConfig.resourcePath}/js/DD_belatedPNG.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.hover.htc a,span,li,ul,img,.attr_b,.item_hd s,.area,.area a,.button_1,.Search_bt,.Cart_span,.Cart_a,.Cart_xq_bt,.slideBox_span_on,.slideBox_span,.prev,.prev2,.next,.next2');
    </script>
    <![endif]-->
</head>
<body>
	<%@ include file="/WEB-INF/jsp/mall/common/header.jsp"%>
	<%-- <div class="main" name="top">
    <div class="searcher-panel">
        <jx:include fileAttr="${LINE_SEARCHER}"></jx:include>
    </div>
    <div class="list-panel">
        这里是列表内容了
    </div>
</div> --%>

	<input id="line-type" type="hidden" value="lines">
	<div class="clearfix container-bg">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<!--筛选类型-->
					<jx:include fileAttr="${LINE_SEARCHER}" targetObject="lineBuilder" targetMethod="buildSearcher"></jx:include>
					<!--筛选类型-->
					<!--排序-->
					<div class="sortby clearfix">
						<a href="#" class="curr top">热门</a><a href="#" class="top">最新</a>
						<a href="#" class="price top"><span class="qj" id="priceqj">价格区间<span
								class="caret"></span></span></a>
						<div class="price-sub" style="display: none;">
							<a href="#" class="first">从低到高<span class="caret"></span></a> <a
								href="#" class="dropup">从高到低<span class="caret"></span></a> <span
								class="jiagequjian pull-left">指定价格区间: <input name=""
								type="text" class="text" /> - <input name="" type="text"
								class="text" /> <input class="button" value="确认" type="button" />
							</span>
						</div>
					</div>
					<!--排序-->
					<div class="listtop text-center">
						<span class="t1 col-xs-3">供应商</span> <span class="t2 col-xs-6">线路</span>
						<span class="t3 col-xs-1">销售价</span>
						<% if (CURRENT_USER != null && !(CURRENT_USER instanceof Member)) { %>
							<span class="t4 col-xs-1">佣金</span>
						<% } else { %>
							<span class="t4 col-xs-1"></span>
						<% } %>

						<span class="t5 col-xs-1">操作</span>
					</div>
					<!--线路列表-->
					<div class="box">
						<div id="linesPanel">
							<s:if test="#request.lines.size() == 0">
							找不到对象
						</s:if>
							<s:else>
								<c:forEach items="${lines }" var="line">
									<div class="list clearfix">
										<div class="col-xs-3 text-center">
											<a href="/mall/supplier/home.jhtml?id=${line.supplierId}">
												<img src="${mallConfig.imguriPreffix}${line.supplierLogo }" alt="" style="width: 220px; height: 120px"/>
												<h5>${line.supplierName }</h5> </a>
										</div>
										<div class="col-xs-6">
											<dl>
												<dt>
													<a href="/mall/line/detail.jhtml?id=${line.id}">${line.name }</a>
												</dt>
												<dd>${line.linePoint }</dd>
												<!-- <dd>
												编号：BL02 团期：10/11 、 10/12 、 10/13 <a href="#">[更多]</a>
											</dd> -->
												<!-- <dd>
												<img src="images/hot.jpg" alt="" />
											</dd> -->
											</dl>
										</div>
										<div class="col-xs-1 text-center">¥${line.disCountPrice }
										</div>
										<% if (CURRENT_USER != null && !(CURRENT_USER instanceof Member)) { %>
											<div class="col-xs-1 text-center">¥${line.yjPrice }</div>
										<% } else { %>
											<div class="col-xs-1 text-center"></div>
										<% } %>

										<div class="col-xs-1 text-center action">
											<a href="/mall/line/detail.jhtml?id=${line.id}" class="btn btn-warning">立即预定</a>
											<% if (CURRENT_USER != null && !(CURRENT_USER instanceof Member)) { %>
												<a href="/mall/line/agent.jhtml?productId=${line.id}" class="btn btn-danger">我要分销</a>
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
							</ul>
						</div>
						<!--分页-->
					</div>
					<!--线路列表-->
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
	<div style="display: none;" id="pageIndex">${pageIndex }</div>
	<div style="display: none;" id="totalCount">${totalCount }</div>
	<script src="${mallConfig.resourcePath}/js/jsrender.js" type=""></script>
	<script src="${mallConfig.resourcePath}/js/plugs/jquery.pagination.js"
		type="">
	</script>
	<script
			src="${mallConfig.resourcePath}/js/mall/line/line.js?v=${mallConfig.resourceVersion}"
			type="">
	</script>
	<script
		src="${mallConfig.resourcePath}/js/mall/line/list.js?v=${mallConfig.resourceVersion}"
		type=""></script>
	<script id="lineTmpl" type="text/x-jsrender">
    							<div class="list clearfix">
									<div class="col-xs-3 text-center">
										<a href="/mall/supplier/home.jhtml?id={{:supplierId}}">
											<img src="{{:supplierLogo }}" alt="" style="width: 220px; height: 120px"/>
											<h5>{{:supplierName }}</h5>
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
									<% if (CURRENT_USER != null && !(CURRENT_USER instanceof Member)) { %>
										<div class="col-xs-1 text-center">¥{{:yjPrice }}</div>
									<% } else { %>
										<div class="col-xs-1 text-center"></div>
									<% } %>

									<div class="col-xs-1 text-center action">
										<a href="/mall/line/detail.jhtml?id={{:id}}" class="btn btn-warning">立即预定</a>
										<% if (CURRENT_USER != null && !(CURRENT_USER instanceof Member)) { %>
											<a href="/mall/line/agent.jhtml?productId={{:id}}" class="btn btn-danger">我要分销${CURRENT_USER}</a>
										<% } else { %>
										<% } %>
									</div>
								</div>
    </script>

</body>
</html>