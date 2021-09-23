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
  <script type="text/javascript" src="/js/wechat/wechat/autoReply.js"></script>
  <title>公众号管理</title>
</head>
<body style="background:white;width:100%;height:100%;">
	
	
	<div class="easyui-layout" data-options="border:false" style="width:100%;height:100%;">
		<div data-options="region:'north',border:false" style="height:10%;">
			<div style="margin-bottom: 10px;margin-top: 10px;width:100%;">
				<div style="width:25%;float:left;margin-left: 10px;">
					<a href="#" class="easyui-linkbutton" onclick="AutoReply.addReplyAcc()" >增加自动回复</a>
				</div>
				<div style="width:65%;text-align: right;margin-left: 345px	;">
					<input id="search_ruleName" class="easyui-textbox" data-options="prompt:'请输入规则名'">
					<select class="easyui-combobox" style="width: 193px" id="search_account" data-options="prompt:'请选择公众号'" name="category" ></select>
					<a href="#" class="easyui-linkbutton" onclick="AutoReply.searchReplys()">查询</a>
					<a href="#" class="easyui-linkbutton" onclick="AutoReply.clearReplys()" >重置</a>
				</div>
			</div>
		</div>
		<div data-options="region:'south'" style="height:90%;">
			<div id="Js_ruleList" style="height:100%;">
				<input type="hidden" id="rulesId" value="${rulesId}">
 				<div class="easyui-accordion" id="accordionId" data-options="border:false" style="width:100%;height:0px;"> 
 				</div> 
				<table id="rule_dg">
				</table>
			</div>
		</div>
	</div>

	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="" width="100%" height="100%"
        data-options="modal:true,closed:true,shadow:false"> 
        <iframe name="editIframe" id="editIframe" scrolling="yes" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>  
	</div> 
	
	<div id="editNews" class="easyui-dialog" title="" width="100%" height="100%"
        data-options="modal:true,closed:true,shadow:false"> 
        <iframe name="editIframe" id="editIframeNews" scrolling="yes" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>  
	</div> 
	

</body>
</html>
