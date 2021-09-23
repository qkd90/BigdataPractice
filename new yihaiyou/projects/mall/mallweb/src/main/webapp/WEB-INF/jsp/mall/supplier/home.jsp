<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page import="com.data.data.hmly.service.entity.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<% User CURRENT_USER = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>供应商主页</title>
  <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/gys-home.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<!--供应商头部开始-->
<jx:include fileAttr="${SUPPLIER_HOME}"></jx:include>
<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js"></script>

<script type="text/javascript">
  <% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>

  function refreshTable() {
    $(".iagent").remove();
  }

  <%} else {%>
  function refreshTable() {
    $(".iagent").show();
  }
  <%}%>

</script>

<script
        src="${mallConfig.resourcePath}/js/mall/supplier/home.js?v=${mallConfig.resourceVersion}"
        type="">
</script>

</body>
</html>
