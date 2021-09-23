<%@page language="java" pageEncoding="UTF-8"%>
<%--<%@include file="/common/include.inc.jsp" %>--%>
<%@taglib prefix="jx" uri="/includeTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="${mallKey.keyWords}" name="Keywords" />
<meta content="${mallKey.remark}" name="Description" />
<title>${mallConfig.title}-供应商列表</title>
<%--<jsp:include page="/common/common.jsp"/>--%>
<link href="${mallConfig.resourcePath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}"
	rel="stylesheet" type="text/css" />
<link
	href="${mallConfig.resourcePath}/css/jingdian.css?v=${mallConfig.resourceVersion}"
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


	<div class="clearfix container-bg">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<!--筛选类型-->
					<jx:include fileAttr="${SUPPLIER_SEARCH}"></jx:include>
					<!--筛选类型-->
					<!--排序-->
					<div class="sortby clearfix">
						<a href="#" class="curr top">所有</a><a href="#" class="top">人气</a>
					</div>
					<!--排序-->
					<!--线路列表-->
					<div class="box">
						<div id="suppliersPanel">
							<s:if test="#request.units.size() == 0">
							找不到对象
						</s:if>
							<s:else>
								<c:forEach items="${units }" var="unit">
									<div class="list clearfix">
										<div class="col-xs-3 text-center">
											<a href="/mall/supplier/home.jhtml?id=${unit.id}"><img width="220px" height="120px" src="${mallConfig.imguriPreffix}${unit.sysUnitDetail.logoImgPath }"
												alt="${unit.name}" /></a>
										</div>
										<div class="col-xs-7">
											<dl>
												<dt>
													<a href="/mall/supplier/home.jhtml?id=${unit.id}">${unit.name} [${unit.area.father.name}
														${unit.area.name}]</a>
												</dt>
												<dd>主营业务：${unit.sysUnitDetail.mainBusiness }</dd>
											</dl>
										</div>
										<div class="col-xs-2 text-center action">
											<% if (CURRENT_USER == null || CURRENT_USER instanceof Member) { %>
											<% } else { %>
												<a href="/mall/supplier/home.jhtml?id=${unit.id}" class="btn btn-danger btn-lg">我要分销</a>
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
			</div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
	<div style="display: none;" id="pageIndex">${pageIndex }</div>
	<div style="display: none;" id="totalCount">${totalCount }</div>
	<script src="${mallConfig.resourcePath}/js/jsrender.js" type=""></script>
	<script src="${mallConfig.resourcePath}/js/plugs/jquery.pagination.js"
		type=""></script>
	<script
		src="${mallConfig.resourcePath}/js/mall/supplier/list.js?v=${mallConfig.resourceVersion}"
		type=""></script>
	<script id="supplierTmpl" type="text/x-jsrender">
							<div class="list clearfix">
										<div class="col-xs-3 text-center">
											<a href="/mall/supplier/home.jhtml?id={{:id}}"><img width="220px" height="120px" src="${mallConfig.imguriPreffix}{{:sysUnitDetail.logoImgPath }}"
												alt="{{:name}}" /></a>
										</div>
										<div class="col-xs-7">
											<dl>
												<dt>
													<a href="/mall/supplier/home.jhtml?id={{:id}}">{{:name}} [{{:area.father.name}}
														{{:area.name}}]</a>
												</dt>
												<dd>主营业务：{{:sysUnitDetail.mainBusiness }}</dd>
											</dl>
										</div>
										<div class="col-xs-2 text-center action">
											<% if (CURRENT_USER == null || CURRENT_USER instanceof Member) { %>
											<% } else { %>
												<a href="/mall/supplier/home.jhtml?id=${unit.id}" class="btn btn-danger btn-lg">我要分销</a>
											<% } %>
										</div>
									</div>
</script>
</body>
</html>