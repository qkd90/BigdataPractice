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
  <script type="text/javascript" src="/js/wechat/wechat/replyNews.js"></script>
  <title>公众号管理</title>
</head>
<body style="background:white;">
	
	<input type="hidden" id="indexId" value="${index}">

	<div id="news" title="图文素材">
		<div id="userTool" style="padding: 5px; height: auto">
			<input class="easyui-textbox" id="news_keword" data-options="prompt:'标题'" style="width:200px;line-height:20px;border:1px solid #ccc">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				  onclick="ReplyNews.doSearch();">查询</a>
			<div id="addTool" style="margin-right:20px;float: right;" >
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ReplyNews.addNews()">添加图文素材</a>
			</div>
		</div>
			<!-- 数据表格 始 -->
			<div data-options="region:'center',border:false">
				<table id="news_dg"></table>
			</div>
			<!-- 数据表格 终-->
	</div>
	<div id="editNews" class="easyui-dialog"  title="图文素材" width="100%" height="100%"
        data-options="fit:'true',modal:true,closed:true"> 
         <iframe name="editNewsIframe" id="editNewsIframe"  frameborder="0" scrolling="yes" style="width:100%;height:100%;"></iframe> 
	</div> 
</body>
</html>
