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

<title>会员等级管理</title>
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
		<form id="memberTypeInputForm" name="memberTypeInputForm" method="post" action="">
			<input id="memberTypeId" type="hidden" name="memberType.id" value="${memberType.id}" />
			<input id="createdTime" type="hidden" name="memberType.createdTime" value="${memberType.createdTime}" />
			<input id="updateTime" type="hidden" name="memberType.updateTime" value="${memberType.updateTime}" />
			<input id="shopId" type="hidden" name="memberType.shopId" value="${memberType.shopId}" />
			<input id="userId" type="hidden" name="memberType.userId" value="${memberType.userId}" />
			<table border="0" cellpadding="10" cellspacing="0"
				style="margin-top: 15px; width: 350px; height: 150px;">
				<tr align="center" bgcolor="#FFFFFF">
					<td align="left" valign="top" style="padding-top: 10px; padding-middle: 0px;">

						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">等级名称：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="levelName" id="memberType_levelName"
										maxlength="50" size="25" value="${memberType.levelName}" placeholder="请输入等级名称" 
										class="easyui-validatebox" required="required" missingMessage="请输入等级名称"/>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">折扣：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="discount" id="memberType_discount" 
										validType="length[1,5]" placeholder="可保留小数点后两位"
										data-options="precision:2,decimalSeparator:'.'"
										min="0" max="10" size="25" maxlength="5" class="easyui-numberspinner" value="${memberType.discount}"  />
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
									<span class="text">排序值：</span>
								</td>
								<td bgcolor="#FFFFFF" width="30%">
									<input type="text" name="isOrder" id="memberType_isOrder" 
										validType="length[1,9]" placeholder="请输入数值类型的排序值"
										min="0" size="25" maxlength="9" class="easyui-numberspinner" value="${memberType.isOrder}"  />
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
			href="javascript:void(0)" onclick="submitmemberTypeInputForm()">提交</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="clearmemberTypeInputForm()">重置</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="closememberTypeInputForm()">关闭</a>
	</div>
	

	<script type="text/javascript">
	//提交表单
	function submitmemberTypeInputForm() {
		var typeId = $("#memberTypeId").val();
		var url = "/member/memberType/addEditMemberType.jhtml?memberType.id="+typeId;
		var param = {
				"memberType.shopId":$("#shopId").val(),
				"memberType.userId":$("#userId").val(),
				"memberType.createdTime":$("#createdTime").val(),
				"memberType.updateTime":$("#updateTime").val(),
				"memberType.levelName":$("#memberType_levelName").val(),
				"memberType.discount":$("#memberType_discount").val(),
				"memberType.isOrder":$("#memberType_isOrder").val()
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
					msg:'提交会员等级信息成功'
				});
				$('#memberTypeWindow').window('close');
				$('#memberTypeTable').datagrid("reload");
			}
		});
	}

	// 清空填入的数据
	function clearmemberTypeInputForm() {
		$('#memberTypeInputForm').form('reset');
		
	}
	
	// 关闭窗口
	function closememberTypeInputForm(){
		$('#memberTypeWindow').window('close');
	}
	
	</script>
</div>
</body>
</html>