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
  <script type="text/javascript" src="/js/wechat/wechat/addText.js"></script>
  <link rel='stylesheet' href='/css/wechat/wechat/dataStyle.css' />
  <title>文本素材</title>
</head>
<body style="background-color: white;">

	<div class="father_div">
		<form id="textFormId" method="post">
		
			<input id="textid" name="wechatDataText.id" type="hidden" value="${dataText.id}">
			<input id="hidden_reply" type="hidden" value="${reply}"> 
<%-- 			<input id="textid" name="" type="hidden" value="${dataText.id}">  --%>
            <div class="first_class">
                <div class="second_class1">
                    <label>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</label>
                </div>
                <div class="second_class2">
                    <input id="input_category" name="wechatDataText.category.id" value="${dataText.category.id}"
                           class="easyui-combotree" style="width: 280px">
                </div>
            </div>
			<div class="first_class">
                <div class="second_class1">
                    <label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
                </div>
                <div class="second_class2">
                    <input id="input_title" name="wechatDataText.title" value="${dataText.title}"
                           class="easyui-textbox" style="width: 280px" data-options="required:true">
                    <span style="color:#DCDCDC;">当前输入字数<span id="textLength" style="color:green;">0</span>/64</span>
                </div>
            </div>
			<div class="first_class">
				<div class="second_class1" >
					<label>文本内容：</label>
				</div>
				<div class="second_class2">
					<%-- <input class="easyui-textbox" id="text_content" name="content" data-options="multiline:true" style="width:700px;height:300px;" value="${dataText.content}"> --%> 
					<input type="hidden" id="hidden_content" name="wechatDataText.content" value='${dataText.content}'>
 					<textarea id="text_content"  value="" style="width: 700px; height: 250px; visibility: hidden;">
 					    ${dataText.content}</textarea>
					<span style="margin-left: 205px;color:#DCDCDC;">当前还可以输入<span id="textareaLength2" style="color:green;">600</span><span>(文本)字</span></span>
	<!-- 				<span style="margin-left:80px;color:#DCDCDC;">当前输入字数/600</span> -->
					  
				</div>
				
			</div>
		</form>
		<div class="first_class">
			<div class="second_class1" >
<!-- 				<label>文本内容：</label> -->
			</div>
			<div style="margin-left:80px;margin-top:5px;">
				<a id="btn_editSce" href="javascript:void(0)" class="easyui-linkbutton"
									 onclick="AddText.addText()">保存文本素材</a>
				  
			</div>
			
		</div>
		
	</div>
	

	
</body>
</html>
