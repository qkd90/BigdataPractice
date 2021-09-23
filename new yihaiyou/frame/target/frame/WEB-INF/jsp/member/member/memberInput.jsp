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

<title>供应商管理</title>
<%@ include file="../../common/common141.jsp"%>
<link href="<%=basePath%>/jQuery-uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/jQuery-uploadify/swfobject.js"></script>
<script type="text/javascript" src="<%=basePath%>/jQuery-uploadify/jquery.uploadify.min.js"></script>

</head>
<body>
<style type="text/css">
.text{
	font-size: 13px; 
	color: rgb(2, 48, 97);
}
#searchBtn:hover {
	background:#fff;
	color: blue;
    font-family: "Comic Sans MS";
    font-weight: bold;
    text-shadow: 2px 2px 1px #999;
    text-transform: uppercase;
   }
.legend {
    color: #f00;
    font-size:15px;
    font-family: "Comic Sans MS";
    font-weight: bold;
    text-shadow: 2px 2px 1px #999;
    text-transform: uppercase;
}
</style>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true">
		<!--表单区域开始-->
		<form id="memberInputForm" name="memberInputForm" method="post" action="/member/member/addEditMember.jhtml?member.id=${member.id}">
			<input id="memberId" type="hidden" name="member.id" value="${member.id}" />
			<input id="attachmentId" type="hidden" name="member.attachmentId" value="${member.attachmentId}" />
			<input id="createdTime" type="hidden" name="member.createdTime" value="${member.createdTime}" />
			<input id="updateTime" type="hidden" name="member.updateTime" value="${member.updateTime}" />
			<input id="shopId" type="hidden" name="member.shopId" value="${member.shopId}" />
			<input id="userId" type="hidden" name="member.userId" value="${member.userId}" />
			<table border="0" cellpadding="10" cellspacing="0"
				style="margin-top: 5px; width: 750px; height: 350px;">
				<tr align="center" bgcolor="#FFFFFF">
					<td colspan="4" align="left" valign="top"
						style="padding-top: 10px; padding-middle: 0px;">
				<div class="easyui-tabs easyui-panel" style="margin-top: 10px;" id="tt">
						<div title="主要资料"  style="padding:10px">
							<table width="100%" border="0" cellpadding="0" cellspacing="1" class="blue_kuang">
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员昵称：</span>
									</td>
									<td>
										<input id="member_nickName" name="member.nickName" class="easyui-validatebox"
										 missingMessage="请输入会员昵称" value="${member.nickName}" size="35" maxlength="100"/>
									</td>
									<td width="35%" colspan="2" rowspan="7">
										<a id="picFile1As" href="<%=path %>/${member.picture}" target="_blank">
										<img id="picture-goods" border="0" width="160px" height="180px" src="<%=path %>/${member.picture}"> 
										</a>
										<br/>
										<input type="file" name="urlfile" id="urlfile" size="2" accept= "image/jpg,image/jpeg,image/gif,image/png" /> 
										<input type="hidden" id="member_picture" value="${member.picture}" name="picture" type="text" style="width:180px"/>
										<br/>
										<input id="searchBtn" class="legend" type="button" value="上传会员照片" onclick="ajaxFileUpload()" />
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员姓名：</span>
									</td>
									<td colspan="3">
										<input id="member_realName" name="member.realName" class="easyui-validatebox" required="required" 
										 missingMessage="请输入会员姓名" placeholder="请输入会员姓名" value="${member.realName}" size="35" maxlength="100"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员卡号：</span>
									</td>
									<td colspan="3">
										<input id="member_cardNo" name="member.cardNo" class="easyui-validatebox" 
											required="required" missingMessage="请输入会员卡号" placeholder="请输入会员卡号"
										 	value="${member.cardNo}" size="35" maxlength="100"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">会员等级：</span>
									</td>
									<td colspan="3" bgcolor="#FFFFFF" width="30%">
										<select id="member_memberType"  name="member.memberType.levelName" style="width: 200px;line-height:22px;border:1px solid #95B8E7" onchange="changeLevelDis();">
											<option value="">...请选择...</option>
											<c:forEach items="${typeList}" var="type">
											<option value="${type.levelName}" 
											<c:if test="${member.memberType.levelName==type.levelName}">selected="selected"</c:if>>${type.levelName}</option>
											</c:forEach>  
										</select>
										<span style="color: red;">*必填</span>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">折扣：</span>
									</td>
									<td colspan="3" bgcolor="#FFFFFF" width="30%">
										<span class="member_discount">${member.discount}</span>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">手机号码：</span>
									</td>
									<td colspan="3" bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.phone" id="member_phone" placeholder="请输入手机号码"
											data-options="required:true,missingMessage:'请输入手机号码',validType:'mobile'"
											size="35" maxlength="11" class="easyui-validatebox" value="${member.phone}"  />
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">出生年月：</span>
									</td>
									<td colspan="3" bgcolor="#FFFFFF" width="30%">
										<%-- <input type="text" name="member.birthDate" id="member_birthDate" onclick="WdatePicker();"
											style="line-height:20px;border:1px solid #ccc" value="${member.birthDate}"/> --%>
										<input name="member.birthDate" id="member_birthDate" value="${member.birthDate}" size="20" 
											 required="required" class="easyui-datebox" data-options="showSeconds:true,formatter:myformatter,missingMessage:'请选择出生年月'"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="20%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">使用状态：</span>
									</td>
									<td colspan="3" class="selState">
										<input id="one-opens" type="radio" name="member.state" value="启用" <c:if test="${member.state=='启用'}">checked="checked"</c:if> 
											<c:if test="${member.state!='启用'&&member.state!='停用'}">checked="checked"</c:if>/><label for="one-opens">启用</label>
										<input id="two-opens" type="radio" name="member.state" value="停用"
											<c:if test="${member.state=='停用'}">checked="checked"</c:if>/><label for="two-opens">停用</label>
									</td>
								</tr>
							</table>
						</div>
						<div title="扩展资料"  style="padding:10px">
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">性别：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%" class="selGender">
										<input id="one-sexs" type="radio" name="member.gender" value="女" <c:if test="${member.gender=='女'}">checked="checked"</c:if> 
											<c:if test="${member.gender!='女'&&member.gender!='男'}">checked="checked"</c:if>/><label for="one-sexs">女</label>
										<input id="two-sexs" type="radio" name="member.gender" value="男"
											<c:if test="${member.gender=='男'}">checked="checked"</c:if>/><label for="two-sexs">男</label>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">身份证：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.identify" id="member_identify"
											size="50" maxlength="18" class="easyui-validatebox" value="${member.identify}"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">电子邮箱：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.memberEmail" id="member_memberEmail"
											size="50" maxlength="50" class="easyui-validatebox" value="${member.memberEmail}" />
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">QQ号码：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.qq" id="member_qq"
											size="50" maxlength="20" class="easyui-validatebox" value="${member.qq}" />
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">微信号：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.weixin" id="member_weixin"
											size="50" maxlength="50" class="easyui-validatebox" value="${member.weixin}" />
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">地址：</span>
									</td>
									<td bgcolor="#FFFFFF" width="30%">
										<input type="text" name="member.address" id="member_address"
											size="50" maxlength="100" class="easyui-validatebox" value="${member.address}"/>
									</td>
								</tr>
								<tr align="left" valign="middle">
									<td width="15%" height="28" align="right" valign="middle" bgcolor="#ececec">
										<span class="text">备注：</span>
									</td>
									<td>
										<textarea id="member_remark" name="member.remark" class="easyui-validatebox" rows="8" cols="80">${member.remark}</textarea>
									</td>
								</tr>
							</table>
						</div>
				</div>
							
					</td>
				</tr>

			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="submitmemberInputForm()">提交</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="clearmemberInputForm()">重置</a>
		<a class="easyui-linkbutton"
			href="javascript:void(0)" onclick="closememberInputForm()">关闭</a>
	</div>


<script type="text/javascript" src="/js/fileupload/ajaxfileupload.js" charset="utf-8"></script>
	<script type="text/javascript">
	
	// 上传会员照片
	function ajaxFileUpload() {
		$.ajaxFileUpload({
			url : '/member/member/fileUpload.jhtml',
			secureuri : false,
			fileElementId : 'urlfile',
			dataType : 'json',
			success : function(data, status) {
				if(data.urlId!=null){
					$("#attachmentId").val(data.urlId);
					$("#picture-goods").attr("src", "<%=path%>/" + data.address);
					$("#picFile1As").attr("href", "<%=path%>/" + data.address);
					$("#member_picture").val(data.address);
				}else{
					$.messager.show({
						title:"温馨提示",
						msg:data.errorMsg
					});
				}
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
		return false;
	}
	
	// 异步获取 该会员等级的折扣率
	function changeLevelDis(){
		var levelName = $("select[id=member_memberType]").find("option:selected").val();
		if(levelName!=""){
			$.post("/member/member/changeDiscount.jhtml",{"memberType.levelName":levelName},function(result){
				if(result!=null){
					$(".member_discount").html(result.type.discount+"折");
				}
			});
		}
	}
	
	
	//提交表单
	function submitmemberInputForm() {
		var memberId = $("#memberId").val();
		var url = "/member/member/addEditMember.jhtml?member.id="+memberId;
		var param = {
				"member.attachmentId":$("#attachmentId").val(),
				"member.shopId":$("#shopId").val(),
				"member.userId":$("#userId").val(),
				"member.createdTime":$("#createdTime").val(),
				"member.updateTime":$("#updateTime").val(),
				"member.nickName":$("#member_nickName").val(),
				"member.realName":$("#member_realName").val(),
				"member.cardNo":$("#member_cardNo").val(),
				"member.memberType.levelName":$("select[id=member_memberType]").find("option:selected").val(),
				"member.phone":$("#member_phone").val(),
				"member.birthDate":$('#member_birthDate').datebox('getValue'),
				"member.state":$(".selState input:radio:checked").val(),
				"member.gender":$(".selGender input:radio:checked").val(),
				"member.identify":$("#member_identify").val(),
				"member.memberEmail":$("#member_memberEmail").val(),
				"member.qq":$("#member_qq").val(),
				"member.weixin":$("#member_weixin").val(),
				"member.address":$("#member_address").val(),
				"member.remark":$("#member_remark").val(),
				"member.picture":$("#member_picture").val()
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
					msg:'提交会员信息成功'
				});
				$('#memberWindow').window('close');
				$('#memberTable').datagrid("reload");
			}
		}); 
		
		//提交表单
		$('#memberInputForm').form("submit", {
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
						msg:'提交会员信息成功'
					});
					$('#memberWindow').window('close');
					$('#memberTable').datagrid("reload");
				}
				
			}
		});
	}

	// 清空填入的数据
	function clearmemberInputForm() {
		$('#memberInputForm').form('reset');
		
	}
	
	// 关闭窗口
	function closememberInputForm(){
		$('#memberWindow').window('close');
	}
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours(); //hour  
		var n = date.getMinutes(); //minute  
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
	
	</script>
</div>
</body>
</html>