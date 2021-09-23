<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/11
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${errMsg == ''}">
    ${payForm}
</c:if>

<c:if test="${errMsg != ''}">
    <p>${errMsg}</p>
</c:if>

</body>
</html>
