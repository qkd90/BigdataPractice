<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/18
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="/js/jquery.qrcode.min.js"></script>
  <script type="text/javascript" src="/js/wxpay.js"></script>
  <script type="text/javascript" src="/js/qrcode.js"></script>

    <title></title>
</head>
<body>

  <input id="code-url" type="hidden" value="${payForm}">
  <div id="code"></div>

</body>
</html>
