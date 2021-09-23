<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<td>Key</td>
				<td>Site</td>
				<td>Total</td>
				<td>ShopCart</td>
				<td>cashaccount</td>
				<td>order</td>
				<td>time</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${totalVisitors }" var="item">
				<tr>
					<td>${item.row }</td>
					<td>${item.site }</td>
					<td>${item.total }</td>
					<td>${item.shopcarlist }</td>
					<td>${item.cashaccount }</td>
					<td>${item.order }</td>
					<td>${item.visiDay }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>