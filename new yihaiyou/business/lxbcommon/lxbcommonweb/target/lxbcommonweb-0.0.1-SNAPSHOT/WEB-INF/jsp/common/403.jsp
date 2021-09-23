<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>403 - 没有可访问资源</title>
</head>

<body>
<div>
	<div>
		<h1>没有可访问资源！</h1>
		<h1>退出后，请确认登录地址是否正确或者联系管理员。</h1>
	</div>
	<div><a href="javascript:loginOut();">退出</a></div>
</div>
</body>
<!-- jquery组件 -->
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    // 退出
    function loginOut() {
        var url = "/sys/login/loginOut.jhtml";
        var param = {};
        $.post(url, param, function(r) {
            if (r.success) {
                location.href="/sys/login/login.jhtml";
            }
        });
    }
</script>
</html>