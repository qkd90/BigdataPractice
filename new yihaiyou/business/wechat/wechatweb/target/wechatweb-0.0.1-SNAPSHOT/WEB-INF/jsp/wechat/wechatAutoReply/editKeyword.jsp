<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/20
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp" %>
  <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
  <script type="text/javascript" src="/js/wechat/wechat/editkeyword.js"></script>
  <title>公众号管理</title>
</head>
<body style="background:white;">
	
	<div style="margin:10px;">
		<input type="hidden" id="index_id" value="${index}">
		<input type="hidden" id="key_index" value="${key_index}">
<%-- 		<input type="hidden" id="keyword" value="${keyword}"> --%>
		<textarea id="editkeyword" name="content" value=""
			style="width: 563px; height: 300px; visibility: hidden;" >${keyword}</textarea>
			<span style="color:gray;">还可以输入<span id="changeword">30</span>字，每个关键字少于30个字符</span>
	</div>
	<div style="margin:10px;">
		<input type="hidden" id="hidden_keyword" value="">
	</div>
	
	<div style="    margin: 10px;margin-top: 110px; margin-left: 220;">
		<a href="#" class="easyui-linkbutton" style="margin-right:15px;" onclick="EditKeyword.addKeyword()" >确定</a>
		<a href="#" class="easyui-linkbutton" onclick="EditKeyword.cancel()" >取消</a>
	</div>
					

</body>
</html>
