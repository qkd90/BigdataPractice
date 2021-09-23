<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/user/user.js"></script>

</head>
<body>
<table id="userTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[15,30,50],rownumbers:true,singleSelect:false,url:'/user/user/searchList.jhtml',toolbar:'#userTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'account'" width="100" align="center">用户名</th>
			<th data-options="field:'userName'" width="80" align="center">真实姓名</th>
			<th data-options="field:'phone'" width="100" align="center">手机号码</th>
			<th data-options="field:'email'" width="100" align="center">电子邮箱</th>
			<th data-options="field:'gender'" width="50" align="center">性别</th>
			<th data-options="field:'address'" width="200" align="center">联系地址</th>
			<c:if test="${session.loginuser.roleid=='1'}">
				<th data-options="field:'shopSimpleName'" width="80" align="center">所属店铺</th>
			</c:if>
			<th data-options="field:'roleid',formatter:initUserRole" width="50" align="center">职位</th>
			<th data-options="field:'loginNum'" width="50" align="center">登陆次数</th>
			<th data-options="field:'isUse',formatter:changeState" width="50" align="center">是否激活</th>
		</tr>
	</thead>
</table>
<div id="userTool" style="padding: 5px; height: auto">
	用户名：<input type="text" id="search_account" style="width: 150px;line-height:20px;border:1px solid #ccc"></input>
	真实姓名：<input type="text" id="search_userName" style="width: 150px;line-height:20px;border:1px solid #ccc"></input>
	手机号码：<input type="text" id="search_phone" style="width: 150px;line-height:20px;border:1px solid #ccc"></input> 
	是否激活：<select id="search_isUse"  name="user.isUse" style="width: 120px;line-height:22px;border:1px solid #95B8E7">
				<option value="">请选择</option>
				<option value="已激活">已激活</option>
				<option value="已冻结">已冻结</option>
			</select>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="userQuery();">查询</a>
	<br/>
	<s:if test='#session.loginuser.roleid=="2"'>
		<a href="javascript:void(0)"
		onclick="openuserWindow('新增店员信息','/user/user/userInput.jhtml','add',null,null)"
		class="easyui-linkbutton" >新增店员</a>
	</s:if>
	<s:if test='#session.loginuser.roleid=="4"'>
		<a href="javascript:void(0)"
		onclick="openuserWindow('新增店员信息','/user/user/userInput.jhtml','add',null,null)"
		class="easyui-linkbutton" >新增店员</a>
		<a href="javascript:void(0)"
		onclick="openuserWindow('新增店员信息','/user/user/userInput.jhtml','add',null,null)"
		class="easyui-linkbutton" >新增店员</a>
	</s:if>
 	<a href="javascript:void(0)"
		onclick="openuserWindow('修改店员信息','/user/user/userInput.jhtml','edit',${session.loginuser.id},${session.loginuser.roleid})"
		class="easyui-linkbutton" >修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" 
		 onclick="reloadUsersTable();">刷新</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="initPass()">初始化密码</a>
</div>




<!-- 添加和修改数据页面框 -->
<div id="userWindow" class="easyui-window"
	style="width: 480px; height: 330px; top: 10px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>

<!-- 添加店长页面框 -->
<div id="addshopManagerWindow" class="easyui-window"
	style="width: 630px; height:350px; top: 30px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>

</body>
</html>