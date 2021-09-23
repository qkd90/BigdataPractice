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
  <script type="text/javascript" src="/js/wechat/wechat/replyText.js"></script>
  <title>公众号管理</title>
</head>
<body style="background:white;">
	
	<div id="text" title="文本素材">
		<div id="userTool" style="padding: 5px; height: auto">
		
			<input type="hidden" id="indexId" value="${index}">
			
			<input class="easyui-textbox" id="text_keword" data-options="prompt:'标题'" style="width:200px;line-height:20px;border:1px solid #ccc">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				  onclick="ReplyText.doSearch();">查询</a>
				
			
			<div id="addTool" style="margin-right:20px;float: right;" >
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="ReplyText.addText()">添加文本素材</a>
			</div>
		</div>
		
			<!-- 数据表格 始 -->
			<div data-options="region:'center',border:false">
				<table id="text_dg"></table>
			</div>
			<!-- 数据表格 终-->
			
	
	</div>
	
	<div id="editText" class="easyui-dialog" title="新增文本素材"
        data-options="fit:true,resizable:true,modal:true,closed:true"> 
        <iframe name="editTextIframe" id="editTextIframe" scrolling="no" frameborder="0"  style="width:100%;height:480px;"></iframe>  
	</div> 
	<div id="editNews" class="easyui-dialog"  title="图文素材"
        data-options="fit:true,resizable:true,modal:true,closed:true"> 
        <iframe name="editNewsIframe" id="editNewsIframe"  frameborder="0"  style="width:100%;height:700px;"></iframe>  
	</div> 

</body>
</html>
