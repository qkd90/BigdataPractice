<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 15/11/7
  Time: 下午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>供应商-关于我们</title>
  <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/gys-about.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<jx:include fileAttr="${SUPPLIER_ABOUT}"></jx:include>

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
