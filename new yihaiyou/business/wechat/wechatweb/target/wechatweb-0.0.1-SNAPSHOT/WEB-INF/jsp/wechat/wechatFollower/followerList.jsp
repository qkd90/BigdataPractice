
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp" %>
  <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
  <link href="/css/wechatAccountList.css" rel="stylesheet" type="text/css">
  <title>公众号管理</title>
  <style type="text/css">
  	
  	.inform_table td{
  		padding:5px;
  	}
  </style>
</head>
<body style="background-color: white;padding-left:10px;padding-right:10px;">
<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">  
		<%--<div data-options="region:'north',border:false" style="height:10%;">--%>
			 <%----%>


		<%--</div>--%>
	<%----%>
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false" style="height:100%;">
			<div id="addTool" style="margin-right:20px;margin-bottom: 0px;margin-left: 5px;margin-top: 2px;background-color:white;" >
				<div>
					<table style="width: 100%">
						<tr>
							<td style="width: 30%">
								<input class="easyui-textbox" data-options="icons: [{
									iconWidth: 22,
									handler: FollowerList.searchBynickname
								}],prompt:'搜索用户名称'" id="search_nickname" style="width:300px;">
							</td>
							<td>
								<input id="isKefu" class="easyui-combobox" name="" style="width:200px;"
									   data-options="
									   prompt:'客服查询',
									   valueField:'id',
									   textField:'text',
									   data:[
									   {id:'true',text:'已设客服'},
									   {id:'false',text:'非客服'}
									   ]
									   " />
								<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="FollowerList.doSearch()" style="">查询</a>
								<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="FollowerList.clearSearch()" style="">重置</a>
							</td>
							<td>
								<label>请选择公众号：</label>
								<input type="hidden" id="hidden_accountId" value="${accountId}">
								<input id="select_accountid" class="easyui-combobox" name="accountId"
									   data-options="" />
								<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="FollowerList.synFollower()" style="">同步用户列表</a>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="FollowerList.openInformDialog()">发送通知</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="FollowerList.openMsgDialog()">群发消息</a>
				</div>
			</div>
			<table id="follower_dg"></table>
		</div>
			

	<div id="inform_dialog" class="easyui-dialog" title="编辑通知"
        data-options="resizable:true,modal:true,closed:true"> 
<!--         <iframe name="editTextIframe" id="editTextIframe" scrolling="no" frameborder="0"  style="width:100%;height:480px;"></iframe>   -->
		<form id="inform_form" action="">
			<table class="inform_table" style="margin:10px;">
				<tr>
					<td><label>通知类型<span style="color:red;">*</span>：</label></td>
					<td>
						<input type="hidden" id="inform_fansIds" value="" name="fansIds">
						<input type="hidden" id="inform_accountId" value="" name="accountId">
						<select id="sel_inform_type" class="easyui-combobox" data-options="prompt:'请选择通知类型'" name="informType" style="width:200px;">   
						    <option value="deliver">发货通知</option>   
						    <option value="order">订单通知</option>   
						    <option value="pay">支付通知</option>   
						    <option value="notice">旅游提示</option>   
						</select>
					</td>
				</tr>
				<tr>
					<td><label>通知内容<span style="color:red;">*</span>：</label></td>
					<td>
						<input id="inform_content" class="easyui-textbox" name="infromContent" data-options="multiline:true,prompt:'请输入通知内容，字数不能超过200字。',validType:'maxLength[200]'"  style="width:320px;height:100px">
					</td>
				</tr>
				<tr>
					<td><label>跳转链接：</label></td>
					<td>
						<input id="jump_url" name="url" class="easyui-textbox" data-options="prompt:'请输入需要跳转的URL,如：http://www.baidu.com',validType:['url','maxLength[100]']" style="width:300px"> 
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="FollowerList.sendInform()">确定发送</a>
					</td>
				</tr>
			</table>
		</form>
		
	</div> 
	<div id="msg_dialog" class="easyui-dialog"  title="编辑消息"
        data-options="resizable:true,modal:true,closed:true"> 
<!--         <iframe name="editNewsIframe" id="editNewsIframe"  frameborder="0"  style="width:100%;height:700px;"></iframe>   -->

		<form id="msg_form" action="">
		<input type="hidden" id="msg_accountId" value="" name="accountId">	
			<div style="padding:10px;">
				<div>
					<textarea id="msg_content" name="content" value=""
								style="width: 450px; height: 200px; visibility: hidden;">请输入消息内容</textarea>
					<span style="color:gray;margin-left: 360;">还可以输入<span id="changeFontCount">600</span>字</span>
				</div>
							
				<div style="margin:10 170;">
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="FollowerList.sendMsg()">确定发送</a>
				</div>
			</div>
			
		</form>
		

	</div> 
    </div>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/wechat/wechat/followerList.js"></script>
</body>
</html>
