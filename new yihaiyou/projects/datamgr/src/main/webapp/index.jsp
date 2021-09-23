<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
<script type="text/javascript">
	var loginuser='<s:property value="#session.loginuser" />';
	if(loginuser!=null&&loginuser!=""){
		location.href="/main/index/index.jhtml";
	}else{
		location.href="/sys/login/login.jhtml";
	}
</script>
</head>
<body>

</body>
</html>