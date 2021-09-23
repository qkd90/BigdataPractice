<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2016/2/22
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<script type="text/javascript">

    // 如果是小窗口弹出登陆则调用起保存事件
    try
    {
        if (opener && opener.logincallback)
        {
            opener.logincallback();
            opener = null;
            window.close();
        }
    }
    catch (e){}


</script>
</body>
</html>
