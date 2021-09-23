<html>
<head>
	<title>${title!"title的默认值"}</title>
</head>
<body>
	<#include "first.ftl"/>
	<lable>学号：</lable>${student.id}<br>
	<lable>姓名：</lable>${student.name}<br>
	<lable>住址：</lable>${student.address}<br>
	学生列表
	<table border="1">
	<#list students as s>
		<#if s_index % 2 == 0>
		<tr style="color:red">
		<#else>
		</#if>
			<td>${s_index}</td>
			<td>${s.id}</td>
			<td>${s.name}</td>
			<td>${s.address}</td>
		</tr>
	</#list>
	</table>
	<br>
	<#if curdate ??>
	当前日期:${curdate?string("yyyy/MM/dd HH:mm:ss")}
	<#else>
	curdate 属性为 null
	</#if>
</body>
</html>
