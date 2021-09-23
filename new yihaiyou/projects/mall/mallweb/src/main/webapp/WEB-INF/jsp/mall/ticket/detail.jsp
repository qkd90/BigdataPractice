<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/4
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>景点详情</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/jingdian-info.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--日期控件css-->
    <link href="${mallConfig.resourcePath}/css/date/default.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!--头部开始-->
<%@include file="/WEB-INF/jsp/mall/common/header.jsp"%>
<!--头部结束-->

<jx:include fileAttr="${TICKET_DETAIL}"></jx:include>


<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script type="text/javascript">
    <% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>

    function refreshTable() {
        $(".commission-hide").remove();
        $(".distribute").remove();
    }

    <%} else {%>
    function refreshTable() {
        $(".commission-hide").show();
        $(".distribute").show();
    }
    <%}%>

</script>
<script id="typePriceItem" type="text/x-jsrender">
    <tr>
        <td rowspan="2" class="border-right"></td>
        <td>大金湖漂流周末票</td>
        <td align="center">¥100</td>
        <td align="center">¥90</td>
        <td align="center">景区取票</td>
        <td align="center">支付方式</td>
        <td align="center">
            <input id="datepicker1" type="text" data-zdp_direction="1" value="2015-10-24"></td>
        <td align="center"><input type="submit" class="btn btn-warning" id="button" value="立即预定">
            <input type="submit" class="btn btn-danger" id="button2" value="我要分销"></td>
    </tr>
</script>

<script src="${mallConfig.resourcePath}/js/zebra_datepicker.js?v=${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
<script src="${mallConfig.resourcePath}/js/mall/ticket/detail.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
