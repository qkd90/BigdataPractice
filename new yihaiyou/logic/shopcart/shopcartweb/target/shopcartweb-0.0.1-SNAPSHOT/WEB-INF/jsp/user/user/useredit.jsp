<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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


</head>
<body>
<style type="text/css">
.text{
	font-size: 13px; 
	color: rgb(2, 48, 97);
}
</style>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true">
		<!--表单区域开始-->
		<form id="userInputForm" name="userInputForm" method="post" action="">
			<input id="userId" type="hidden" name="user.id" value="${user.id}" />
			<input id="password" type="hidden" name="user.password" value="${user.password}" />
			<input id="createdTime" type="hidden" name="user.createdTime" value="${user.createdTime}" />
			<input id="updateTime" type="hidden" name="user.updateTime" value="${user.updateTime}" />
			<input id="loginNum" type="hidden" name="user.loginNum" value="${user.loginNum}" />
			<input id="shopSimpleName" type="hidden" name="user.shopSimpleName" value="${user.shopSimpleName}" />
			<input id="shopName" type="hidden" name="user.shopName" value="${user.shopName}" />
			<input id="shopType" type="hidden" name="user.shopType" value="${user.shopType}" />
			<input id="shopId" type="hidden" name="user.shopId" value="${user.shopId}" />
			<input id="isUse" type="hidden" name="user.isUse" value="${user.isUse}" />
			<input id="pid" type="hidden" name="user.pid" value="${user.pid}" />
			<input id="roleid" type="hidden" name="user.roleid" value="${user.roleid}" />
			<table border="0" cellpadding="5" cellspacing="0"
				style="margin-top: 5px; width: 430px; height: 200px;">
				<tr align="center" bgcolor="#FFFFFF">
					<td align="left" valign="top" style="padding-top: 5px; padding-middle: 0px;">

						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">用户名：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%" class="selAccount">
									<input id="userinput_account" name="account" 
										style="border:0px;" value="${user.account}" readonly="readonly"/>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">真实姓名：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="userName" id="userinput_userName" placeholder="请输入真实姓名"
										size="38" maxlength="50" class="easyui-validatebox" required="required" 
										missingMessage="请输入真实姓名" value="${user.userName}" />
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">手机号码：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="phone" id="userinput_phone" placeholder="请输入手机号码"
										size="38" maxlength="11" class="easyui-validatebox" value="${user.phone}" 
										data-options="required:true,missingMessage:'请输入手机号码',validType:'mobile'"/>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">电子邮箱：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="email" id="userinput_email" placeholder="请输入电子邮箱"
										size="38" maxlength="50" class="easyui-validatebox" value="${user.email}" 
										data-options="missingMessage:'请输入电子邮箱'"/>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">性别：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%" class="selGender">
									<input id="one-sexs" type="radio" name="gender" value="女" <c:if test="${user.gender=='女'}">checked="checked"</c:if> 
										<c:if test="${user.gender!='女'&&user.gender!='男'}">checked="checked"</c:if>/><label for="one-sexs">女</label>
									<input id="two-sexs" type="radio" name="gender" value="男"
										<c:if test="${user.gender=='男'}">checked="checked"</c:if>/><label for="two-sexs">男</label>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">联系地址：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="address" id="userinput_address" placeholder="请输入联系地址"
										size="38" maxlength="100" class="easyui-validatebox" value="${user.address}" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="submituserInputForm()">提交</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="clearUserInputForm()">重置</a>
	</div>
	

	<script type="text/javascript">
	//提交表单
	function submituserInputForm() {
		var userId = $("#userId").val();
		var url = "/user/user/editUser.jhtml?user.id="+userId;
		var param = {
				"user.shopSimpleName":$("#shopSimpleName").val(),
				"user.shopName":$("#shopName").val(),
				"user.shopType":$("#shopType").val(),
				"user.shopId":$("#shopId").val(),
				"user.pid":$("#pid").val(),
				"user.roleid":$("#roleid").val(),
				"user.password":$("#password").val(),
				"user.createdTime":$("#createdTime").val(),
				"user.updateTime":$("#updateTime").val(),
				"user.loginNum":$("#loginNum").val(),
				"user.account":$("#userinput_account").val(),
				"user.userName":$("#userinput_userName").val(),
				"user.phone":$("#userinput_phone").val(),
				"user.email":$("#userinput_email").val(),
				"user.gender":$(".selGender input:radio:checked").val(),
				"user.address":$("#userinput_address").val(),
				"user.isUse":$(".selState input:radio:checked").val()
		};
		$.post(url,param,function(json){
			if(json.errorMsg){
				$.messager.show({
					title:"温馨提示",
					msg:json.errorMsg
				});
			}else{
				$.messager.show({
					title:"温馨提示",
					msg:'修改用户信息成功'
				});
				$('#userWindow').window('close');
				$('#userTable').datagrid("reload");
			}
		});
	}

	// 重置填入的数据
	function clearUserInputForm() {
		$('#userInputForm').form('reset');
		
	}
	
	// 关闭窗口
	function closeuserInputForm(){
		$('#userWindow').window('close');
	}
	
	</script>
</div>
</body>
</html>