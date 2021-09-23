<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${product.name}</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/xianlu-info.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--日期控件css-->
    <link href="${mallConfig.resourcePath}/css/date/default.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.print.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!--头部开始-->
<%@include file="/WEB-INF/jsp/mall/common/header.jsp"%>
<!--头部结束-->

<jx:include fileAttr="${LINE_DETAIL}" targetObject="lineBuilder" targetMethod="buildDetailWeb" objs="${param.id}"></jx:include>

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script type="text/javascript">
<% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>

    function refreshTable() {
        $(".commission-hide").remove();
    }

<%} else {%>
    function refreshTable() {
        $(".commission-hide").show();
    }
<%}%>

</script>
<script id="typePriceItem" type="text/x-jsrender">
    <tr>
        <td>成人价</td>
        <td>{{:price.discountPrice}}</td>
        <td class="commission-hide">{{:price.rebate}}</td>
    </tr>
    <tr>
        <td>儿童价</td>
        <td>{{:price.childPrice}}</td>
        <td class="commission-hide">{{:price.childRebate}}</td>
    </tr>
</script>

<%--<script src="${mallConfig.resourcePath}/js/custom.js?v=${mallConfig.resourceVersion}"></script>--%>
<script src="${mallConfig.resourcePath}/js/fullcalendar/lib/moment.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/fullcalendar/lang-all.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/zebra_datepicker.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jsrender.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/mall/line/detail.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>