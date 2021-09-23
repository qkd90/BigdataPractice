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

<title>店铺管理</title>
<%@ include file="../../common/common141.jsp"%>
</head>
<body>
<style type="text/css">
.legend {
    color: #f00;
    font-size:15px;
    font-family: "Comic Sans MS";
    font-weight: bold;
    text-shadow: 2px 2px 1px #999;
    text-transform: uppercase;
}
.childLegend {
    color: #0066CC;
    font-size:11px;
    font-family: "Comic Sans MS";
    font-weight: bold;
    text-shadow: 1px 1px 1px #999;
    text-transform: uppercase;
}
.text{
	font-size: 13px; 
	color: rgb(2, 48, 97);
}
</style>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true">
		<table style="width: 600px; height: 250px;" border="0" align="left"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<!--表单区域开始-->
					<form id="UsershopManagerForm" name="UsershopManagerForm" method="post" action="/shop/shop/addShopManager.jhtml">
						<table border="0" cellpadding="10" cellspacing="0"
							style="margin-top: 10px; width: 600px; height: 250px;">

							<tr align="center" bgcolor="#FFFFFF">
								<td colspan="2" align="left" valign="top" style="padding-top: 0px; padding-middle: 0px;">
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">所属店铺：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="text" name="shop.shopAllName" id="user_shopAllName"
														maxlength="50" size="35" value="${shopManagetName}" 
														style="padding-left:5px;border: 0px;" readonly="readonly"/>
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">用户名：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="text" name="shop.loginAccount" id="user_loginAccount" onblur="checkLoginName();" 
														size="35" maxlength="16" class="easyui-validatebox" required="required" placeholder="请输入由5-16位字母，数字组成的账号"
														missingMessage="请输入用户名" value="${shop.loginAccount}"/>
														<span id="reg_msg_show"></span>
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">密码：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="password" name="shop.password" id="user_password" placeholder="请输入由6-20位字母，数字组成的密码"
														size="35" maxlength="20" class="easyui-validatebox" required="required"  onblur="checkPassword();"
														missingMessage="请输入登陆密码" value="${shop.password}" />
														<span id="reg_msg_show2"></span>
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">真实姓名：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="text" name="shop.legalName" id="user_legalName" placeholder="请输入真实姓名"
														size="35" maxlength="50" class="easyui-validatebox" required="required" 
														missingMessage="请输入真实姓名" value="${shop.legalName}" />
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">手机号码：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="text" name="shop.legalPhone" id="user_legalPhone" placeholder="请输入手机号码"
														size="35" maxlength="11" class="easyui-validatebox" value="${shop.legalPhone}" 
														data-options="required:true,missingMessage:'请输入手机号码',validType:'mobile'"/>
												</td>
											</tr>
											<tr>
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">电子邮箱：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input type="text" name="shop.email" id="user_email" placeholder="请输入电子邮箱"
														size="35" maxlength="20" class="easyui-validatebox" value="${shop.email}" />
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">性别：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<input id="one-sexs" type="radio" name="sex" value="2" <c:if test="${shop.sex==2}">checked="checked"</c:if> 
														<c:if test="${shop.sex!=2&&shop.sex!=1}">checked="checked"</c:if>/><label for="one-sexs">女</label>
													<input id="two-sexs" type="radio" name="sex" value="1"
														<c:if test="${shop.sex==1}">checked="checked"</c:if>/><label for="two-sexs">男</label>
												</td>
											</tr>
											<tr align="left" valign="middle">
												<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
													<span class="text">联系地址：</span>
												</td>
												<td bgcolor="#FFFFFF" width="30%">
													<div id="provincesCity" onchange="changeAddress();"></div>
													<input type="text" name="defaultAddress" id="user_defaultAddress" placeholder="请输入联系地址"
														size="68" maxlength="100" class="easyui-validatebox" required="required" 
														missingMessage="请输入联系地址" value="${shop.defaultAddress}"  />
												</td>
											</tr>
										</table>
								</td>
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="submitUsershopManagerForm()">提交</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="clearUsershopManagerForm()">重置</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="closeUsershopManagerForm()">关闭</a>
	</div>
	
	<!-- 省市县级联 -->
<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.provincesCity.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.6/regionUnit.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			//省市县级联菜单初始化
			$("#provincesCity").ProvinceCity({
				"provinceName" : "shop.province",
				"cityName" : "shop.city",
				"countyName" : "shop.district"
			})
		});
	</script>

	<script type="text/javascript">
	function changeAddress(){
		var pro = $("#provincesCity").find("option:selected").eq(0).html();
		if(pro!='请选择'){
			$("#user_defaultAddress").val(pro);
			var city = $("#provincesCity").find("option:selected").eq(1).html();
			if(city!='请选择'){
				$("#user_defaultAddress").val(pro+city);
				var dist = $("#provincesCity").find("option:selected").eq(2).html();
				if(dist!='请选择'){
					$("#user_defaultAddress").val(pro+city+dist);
				}
			}
		}else{
			$("#user_defaultAddress").val("");
		}
	}
	//提交表单
	function submitUsershopManagerForm() {
		
		if($("#user_shopAllName").val()==null||$("#user_shopAllName").val()==""){
			$.messaget.alert("温馨提示","所属店铺不能为空，否则不能添加店长！");
			return false;
		}
		
		$('#reg_msg_show').html("");
		$('#reg_msg_show2').html("");
		//提交表单
		$('#UsershopManagerForm').form("submit", {
			success : function(data) {
				var result =JSON.parse(data);
				if(result.errorMsg){
					$.messager.show({
						title:"温馨提示",
						msg:result.errorMsg
					});
				}else{
					$.messager.show({
						title:"温馨提示",
						msg:'新增店长信息成功'
					});
					$('#addshopManagerWindow').window('close');
					$("#userTable").datagrid('reload');
				}
				
			}
		});
	}

	// 清空填入的数据
	function clearUsershopManagerForm() {
		$('#reg_msg_show').html("");
		$('#reg_msg_show2').html("");
		$('#UsershopManagerForm').form('reset');
		
	}
	
	// 关闭窗口
	function closeUsershopManagerForm(){
		$('#reg_msg_show').html("");
		$('#reg_msg_show2').html("");
		$('#addshopManagerWindow').window('close');
	}
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours(); //hour  
		var n = date.getMinutes(); //minute  
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
				+ ':' + (n < 10 ? ('0' + n) : n) + ':' + (s < 10 ? ('0' + s) : s);
	}
	
	// 验证登陆账号
	function checkLoginName(){
		var shopName = $("#user_shopAllName").val();
		var memberName=$('#user_loginAccount').val();
		var reg = /[\u4E00-\u9FA5`~%!@#^=''?~！()+\|$^@#￥……&——‘”“'？*，,。、]+/g;
		if(reg.test(memberName)){
			$('#reg_msg_show').html("<font style='color:red;'>登陆账号包含特殊符号或中文</font>");
			$('#reg_msg_show').show();
			return false;
		}else if(memberName.length<5||memberName.length>16){
			$('#reg_msg_show').html("<font style='color:red;'>请输入由5-16位字母，数字组成的账号</font>");
			$('#reg_msg_show').show();
			return false;
		}
		var param={'memberName':memberName,'shop.shopAllName':shopName};
		$.post('/shop/shop/checkName.jhtml', param, function(result) {
			if(result.success){
				if($("#user_password").val()=='请输入由6-20位字母，数字组成的密码'){
					$("#user_password").focus();
					$("#user_password").mouseenter();
				}
				$('#reg_msg_show').html("<font style='color:green'>"+result.errorMsg+"</font>");
				$('#reg_msg_show').show();
				 return true;
			}else{
				$('#reg_msg_show').html("<font style='color:red;'>"+result.errorMsg+"</font>");
				 $('#reg_msg_show').show();
				 return false;
			}
		});
	}
	
	//验证登陆密码
	function checkPassword(){
		var password = $("#user_password").val();
		var reg = /[\u4E00-\u9FA5]+/g;
		if(password.length<6||password.length>20){
			$('#reg_msg_show2').html("<font style='color:red;'>请输入由6-20位字母，数字组成的密码</font>");
			$('#reg_msg_show2').show();
			return false;
		}else if(reg.test(password)){
			$('#reg_msg_show2').html("<font style='color:red;'>请输入由6-20位字母，数字组成的密码</font>");
			$('#reg_msg_show2').show();
			return false;
		}else{
			$('#reg_msg_show2').html("");
			return true;
		}
	}
	
	</script>
</div>
</body>
</html>