<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta content="" name="Keywords"/>
  <meta content="" name="Description"/>
  <title>验票登录</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="/css/vticket/checkTicket/clogin.css" rel="stylesheet">

</head>
<body>
<div class="container">
  <form class="form-signin" method="post" action="/vticket/checkTicket/doClogin.jhtml">
    <c:if test="${not empty errMsg}">
        <h3 class="form-signin-heading" style="color:red;">${errMsg}</h3>
    </c:if>
    <c:if test="${empty errMsg}">
        <h3 class="form-signin-heading">请用景点账号登录</h3>
    </c:if>
    <label for="inputEmail" class="sr-only">用户名</label>
    <input type="text" id="inputEmail" name="account" class="form-control" placeholder="景点账号" required autofocus>
    <label for="inputPassword" class="sr-only">密码</label>
    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="密码" required>
    <div class="checkbox">

    </div>
    <button class="btn btn-lg btn-info btn-block" type="submit">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
  </form>
</div>
</body>
</html>
