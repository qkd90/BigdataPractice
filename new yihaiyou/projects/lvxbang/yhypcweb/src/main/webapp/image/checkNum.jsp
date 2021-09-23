<%@ page contentType="image/jpeg" pageEncoding="UTF-8"%>
<jsp:useBean id="image" scope="page" class="com.data.data.hmly.util.CheckNum"/>
<%		
	response.reset();
	response.setContentType("image/jpeg");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", 0);
	
	String str=image.getCertPic(0,0,response.getOutputStream());
	session.setAttribute("orderCheckNum",str);
	// 避免OutputStream has been called错误
	out.clear();
	out = pageContext.pushBody();
%>
