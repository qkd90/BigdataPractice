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

<title>会员详情</title>

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
		<form id="memberInfoForm" name="memberInfoForm" method="post" >
			<table border="0" cellpadding="10" cellspacing="0"
				style="margin-top: 5px; width: 750px; height: 350px;">
				<tr align="center" bgcolor="#FFFFFF">
					<td colspan="4" align="left" valign="top"
						style="padding-top: 10px; padding-middle: 0px;">

							<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员昵称：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.nickName}
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员姓名：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.realName}
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员卡号：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.cardNo}
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员等级：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.memberType.levelName}
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">手机号码：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.phone}
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">出生年月：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										<fmt:formatDate value="${member.birthDate}" type="date"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">使用状态：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										<c:if test="${member.state=='启用'}">启用</c:if>
										<c:if test="${member.state=='停用'}">停用</c:if>
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">性别：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										<c:if test="${member.gender=='男'}">男</c:if>
										<c:if test="${member.gender=='女'}">女</c:if>
									</td>
								</tr>
							</table>
					</td>
				</tr>

				<tr align="center" bgcolor="#FFFFFF">
					<td colspan="4" align="left" valign="top" style="padding-top: 0px; padding-middle: 0px;">
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">身份证：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.identify}
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">电子邮箱：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.memberEmail}
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">QQ号码：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.qq}
									</td>
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">微信号：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.weixin}
									</td>
								</tr>
								<tr align="left" valign="middle">
									
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员地址：</span>
									</td>
									<td colspan="3" bgcolor="#FFFFFF" width="30%" style="color:red;">
										${member.address}
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">备注：</span>
									</td>
									<td colspan="3">
										<textarea style="border: 0px;color:red;" 
										 readonly="readonly" rows="8" cols="80">${member.remark}</textarea>
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
			href="javascript:void(0)" onclick="closememberInfoForm()">关闭窗口</a>
	</div>
	<script type="text/javascript">

	
	// 关闭窗口
	function closememberInfoForm(){
		$('#memberInfoWindow').window('close');
	}
	

	
	</script>
</div>
</body>
</html>