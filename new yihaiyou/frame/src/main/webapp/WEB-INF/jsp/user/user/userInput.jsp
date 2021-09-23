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
			<input id="createdTime" type="hidden" name="user.createdTime" value="${user.createdTime}" />
			<input id="updateTime" type="hidden" name="user.updateTime" value="${user.updateTime}" />
			<input id="loginNum" type="hidden" name="user.loginNum" value="${user.loginNum}" />
			<input id="shopSimpleName" type="hidden" name="user.shopSimpleName" value="${user.shopSimpleName}" />
			<input id="shopName" type="hidden" name="user.shopName" value="${user.shopName}" />
			<input id="shopType" type="hidden" name="user.shopType" value="${user.shopType}" />
			<input id="shopId" type="hidden" name="user.shopId" value="${user.shopId}" />
			<input id="pid" type="hidden" name="user.pid" value="${user.pid}" />
			<input id="roleid" type="hidden" name="user.roleid" value="${user.roleid}" />
			<input id="province" type="hidden" name="user.province" value="${user.province}" />
			<input id="city" type="hidden" name="user.city" value="${user.city}" />
			<input id="district" type="hidden" name="user.district" value="${user.district}" />
			<table border="0" cellpadding="5" cellspacing="0"
				style="margin-top: 5px; width: 430px; height: 200px;">
				<tr align="center" bgcolor="#FFFFFF">
					<td align="left" valign="top" style="padding-top: 5px; padding-middle: 0px;">

						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
							<c:if test="${user.roleid=='2'||user.roleid=='3'}">
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">所属店铺：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" value="${shopManagetName}" size="50"
											style="padding-left:5px;border: 0px;" readonly="readonly"/>
									</td>
								</tr>
							</c:if>
							<c:if test="${user.roleid=='3'}">
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">所属店长：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" value="${belongShopManager}" size="38"
											style="padding-left:5px;border: 0px;" readonly="readonly"/>
									</td>
								</tr>
							</c:if>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">用户名：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%" class="selAccount">
									<input id="userinput_account" name="account" 
										 value="${user.account}" placeholder="请输入由5-16位字母，数字组成的账号"
										<c:if test="${user.id==null}">
										 size="38" maxlength="50" class="easyui-validatebox" required="required" 
										 missingMessage="请输入用户名" onchange="checkAccount(this.value);" </c:if>
										<c:if test="${user.id!=null}"> style="border:0px;" readonly="readonly"</c:if> />
								</td>
							</tr>
							<c:if test="${user.id==null}">
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">密码：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="password" name="password" id="userinput_password"
										size="38" maxlength="50" class="easyui-validatebox" required="required" 
										missingMessage="请输入登陆密码" value="${user.password}" placeholder="请输入由6-20位字母，数字组成的密码" />
								</td>
							</tr>
							</c:if>
							<c:if test="${user.id!=null}">
								<input id="userinput_password" type="hidden" name="user.password" value="${user.password}" />
							</c:if>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">真实姓名：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="userName" id="userinput_userName"
										size="38" maxlength="50" class="easyui-validatebox" required="required" 
										missingMessage="请输入真实姓名" value="${user.userName}" placeholder="请输入真实姓名" />
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
									<c:if test="${user.province==''||user.province==null}">
										<div id="userProvincesCity" onchange="changeUserAddress();"></div>
									</c:if>
									<input type="text" name="address" id="userinput_address" placeholder="请输入联系地址"
										size="50" maxlength="100" class="easyui-validatebox" value="${user.address}" 
										data-options="required:true,missingMessage:'请输入联系地址'"/>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">是否激活：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%" class="selState">
									<input id="one-opens" type="radio" name="isUse" value="已激活" <c:if test="${user.isUse=='已激活'}">checked="checked"</c:if> 
										<c:if test="${user.isUse!='已激活'&&user.isUse!='已冻结'}">checked="checked"</c:if>/><label for="one-opens">已激活</label>
									<input id="two-opens" type="radio" name="isUse" value="已冻结"
										<c:if test="${user.isUse=='已冻结'}">checked="checked"</c:if>/><label for="two-opens">已冻结</label>
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
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="closeUserInputForm()">关闭</a>
	</div>
	
	<!-- 省市县级联 -->
<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.provincesCity.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.6/regionUnit.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			//省市县级联菜单初始化
			$("#userProvincesCity").ProvinceCity({
				"provinceName" : "user.province",
				"cityName" : "user.city",
				"countyName" : "user.district"
			})
		});
	</script>

	<script type="text/javascript">
	function changeUserAddress(){
		var pro = $("#userProvincesCity").find("option:selected").eq(0).html();
		if(pro!='请选择'){
			$("#province").val($("#userProvincesCity").find("option:selected").eq(0).val());
			$("#userinput_address").val(pro);
			var city = $("#userProvincesCity").find("option:selected").eq(1).html();
			if(city!='请选择'){
				$("#city").val($("#userProvincesCity").find("option:selected").eq(1).val());
				$("#userinput_address").val(pro+city);
				var dist = $("#userProvincesCity").find("option:selected").eq(2).html();
				if(dist!='请选择'){
					$("#district").val($("#userProvincesCity").find("option:selected").eq(2).val());
					$("#userinput_address").val(pro+city+dist);
				}
			}
		}else{
			$("#userinput_address").val("");
		}
	}
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
				"user.province":$("#province").val(),
				"user.city":$("#city").val(),
				"user.district":$("#district").val(),
				"user.loginNum":$("#loginNum").val(),
				"user.password":$("#userinput_password").val(),
				"user.createdTime":$("#createdTime").val(),
				"user.updateTime":$("#updateTime").val(),
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
	function closeUserInputForm(){
		$('#userWindow').window('close');
	}
	
	//验证用户名唯一性
	function checkAccount(val){
		$.post("/user/user/checkAccount.jhtml",{"user.account":val},function(result){
			if(result!=null){
				if(result.success){
					$.messager.show({
						title : '温馨提示',
						msg : '用户名已被使用，请重新输入'
					});
					$("#userinput_account").val("");
					$("#userinput_account").focus();
				}
			}
		});
	}
	
	</script>
</div>
</body>
</html>