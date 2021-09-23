<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>KindEditor JSP</title>
	<link rel="stylesheet" href="/js/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/js/kindeditor/plugins/code/prettify.css" />
	<style type="text/css">
	.ke-icon-imgsView {
		background-image: url(/js/kindeditor/themes/default/default.gif); 
		background-position: 0px -672px;
		width: 16px;
		height: 16px;
	}
	</style>
	<script charset="utf-8" src="/js/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="/js/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="/js/kindeditor/plugins/code/prettify.js"></script>
	<script>
	// 自定义插件 
	KindEditor.lang({
		imgsView : '图库浏览'
	});
	KindEditor.plugin('imgsView', function(K) {
		var self = this, name = 'imgsView';
		self.clickToolbar(name, function() {
			var editor = K.editor({
				fileManagerJson : '../php/file_manager_json.php'
			});
			editor.loadPlugin('filemanager', function() {
				editor.plugin.filemanagerDialog({
					viewType : 'VIEW',
					dirName : 'image',
					clickFn : function(url, title) {
						self.insertHtml(url);
						editor.hideDialog();
					}
				});
			});
		});
	});
	
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content1"]', {
				cssPath : '/js/kindeditor/plugins/code/prettify.css',
				uploadJson : '/js/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '/js/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', 'imgsView']
			});
			prettyPrint();
		});
	</script>
</head>
<body>
	<%=htmlData%>
	<form name="example" method="post" action="demo.jsp">
		<textarea name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</form>
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>