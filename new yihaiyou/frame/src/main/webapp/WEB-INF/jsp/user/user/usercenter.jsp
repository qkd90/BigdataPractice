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

<title>用户个人中心</title>
<%@ include file="../../common/common141.jsp"%>
<link href="/css/detailMember.css" rel="stylesheet" type="text/css" />
<link href="/css/tbase.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/user/user.js"></script>
<style type="text/css">
.legend {
    color: blue;
    font-size:14px;
    font-family: "Comic Sans MS";
    font-weight: bold;
    text-shadow: 1px 1px 1px #999;
    text-transform: uppercase;
}
</style>
</head>
<body style="background-color: #fff">
<div class="main">
<div class="headerNav"><h2 class="fl ml5 ff_yh lh40">用户个人中心</h2><span class="ml10"> </span></div>
	<div class="help_right">
		<div class="member_center_c">
				<p class="member_title">您好，欢迎回来<span class="tc8" style="font-size:17px;">【<s:property value="user.userName" />】</span></p>
			<div class="member_left_c">
				<p class="mb20"><span style="width:75px;display: inline-block;">所属店铺：</span><span class="ml20 t99" id="memberLev">【<span class="legend"><s:property value="user.shopName" /></span>】</span>
				</p>
				<p class="mb20" ><span style="width:75px;display: inline-block;">身份：</span>
				<label class="ml20 t99" ><s:if test='user.roleid=="1"'>
					【<span class="legend">管理员</span>】
				</s:if>
				<s:elseif test='user.roleid=="2"'>
					【<span class="legend">店长</span>】
				</s:elseif>
				<s:elseif test='user.roleid=="4"'>
					【<span class="legend">店铺管理员</span>】
				</s:elseif>
				<s:else>
					【<span class="legend">店员</span>】
				</s:else>
				</label>
				</p>
			</div>
			<div class="cl"></div>
		</div>
		<div class="hot_pro">
			<div class="hot_pro_t" style="margin-bottom: 0px;">
				<label class="w200 fs14 fl fb">个人信息</label>
				<div class="cl"></div>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">用户名：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%" class="selAccount">
						<s:property value="user.account"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">真实姓名：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%">
						<s:property value="user.userName"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">手机号码：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%">
						<s:property value="user.phone"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">电子邮箱：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%">
						<s:property value="user.email"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">性别：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%" class="selGender">
						<s:property value="user.gender"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">联系地址：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%">
						<s:property value="user.address"/>
					</td>
				</tr>
				<tr align="left" valign="middle">
					<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
						<span class="text">是否激活：</span>
					</td>
					<td bgcolor="#FFFFFF" width="30%" class="selState">
						<s:property value="user.isUse"/>
					</td>
				</tr>
			</table>
			<div style="text-align: center;margin-top: 10px;">
				<a href="javascript:void(0)"
					onclick="openuserWindow('修改用户信息','/user/user/usercenteredit.jhtml','edit','<s:property value="user.id" />')"
					class="easyui-linkbutton"  >修改个人信息</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="initCenterPass(<s:property value='user.id' />)" >初始化密码</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//添加和修改操作
function openuserWindow(title, url, type,userid) {
	if (type == 'edit') {
		$('#userWindow').window({
			title : title
		});
		$('#userWindow').window('open');
		$('#userWindow').window('refresh', url + '?user.id=' + userid);
		$('#userTable').datagrid('clearSelections');
	}
}
</script>
<!-- 添加和修改数据页面框 -->
<div id="userWindow" class="easyui-window"
	style="width: 480px; height: 300px; top: 10px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
</body>
</html>