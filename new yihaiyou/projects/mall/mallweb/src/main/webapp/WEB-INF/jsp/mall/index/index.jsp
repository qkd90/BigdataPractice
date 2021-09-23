<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.Member" %>
<%@page language="java" pageEncoding="UTF-8" %>
<%--<%@include file="/common/include.inc.jsp" %>--%>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta content="${mallKey.keyWords}" name="Keywords"/>
    <meta content="${mallKey.remark}" name="Description"/>
    <title>${mallConfig.title}</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/index.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<%--<jx:include fileAttr="${GLOBAL_HEADER}"></jx:include>--%>
<%@ include file="/WEB-INF/jsp/mall/common/header.jsp" %>
<!--幻灯片开始-->
<jx:include fileAttr="${INDEX_TOP_BANNER}" targetObject="indexBuilder" targetMethod="build"></jx:include>
<!--幻灯片结束-->

<!--首页登录框-->
<% if (CURRENT_USER==null) { %>
<div class="container">
    <div class="row">

        <div id="homefloat">
            <jsp:include page="/WEB-INF/jsp/user/user/login.jsp"></jsp:include>
        </div>

    </div>
</div>

<% } %>
<!--首页登录框-->

<div class="container">
    <div class="row">
        <!--旅游线路开始-->
        <jx:include fileAttr="${INDEX_LINE}" targetObject="indexBuilder" targetMethod="build"></jx:include>
        <!--旅游线路结束-->
        <!--景点门票开始-->
        <jx:include fileAttr="${INDEX_TICKET}" targetObject="indexBuilder" targetMethod="build"></jx:include>
        <!--景点门票结束-->
        <!--供应商-->
        <jx:include fileAttr="${INDEX_SUPPLIER}" targetObject="indexBuilder" targetMethod="build"></jx:include>
        <!--供应商-->
    </div>
</div>

<%--<jx:include fileAttr="${GLOBAL_FOOTER}"></jx:include>--%>
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
</body>
</html>