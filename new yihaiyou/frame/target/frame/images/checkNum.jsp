<%@page import="com.data.data.hmly.util.CheckNum"%>
<%@ page contentType="image/jpeg" pageEncoding="UTF-8"%>
<jsp:useBean id="image" scope="page" class="com.data.data.hmly.util.CheckNum"/>
<%		
	response.reset();
	response.setContentType("image/jpeg");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", 0);
	
	String str=image.getCertPic(90,54,response.getOutputStream());
	session.setAttribute("checkNum",str);					
	// 避免OutputStream has been called错误
	out.clear();
	out = pageContext.pushBody();
%>
