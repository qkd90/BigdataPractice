<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html;charset=Big5" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>找不到这个页面</title>
    <style type="text/css">
        .wrap {
            position: absolute;
            top: -160px;
            bottom: 0;
            left: 0;
            right: 0;
            width: 1000px;
            height: 623px;
            margin: auto;
        }

        .pic {
        }

        .return {
            display: block;
            padding-top: 100px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            color: #ff6000;
        }
    </style>
</head>
<body>
<div class="wrap">
    <img class="pic" src="/images/404.png"/>

    <a href="${INDEX_PATH}" class="return">回首页</a>
    <%=exception%>
    <%
        exception.printStackTrace(response.getWriter());
    %>

    <%--<div style="display: none;"><s:fielderror/></div>--%>
</div>
<script type="text/javascript">
</script>
</body>
</html>
