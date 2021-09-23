<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysUser/sysUser.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysUnit/manage.css">
	<style type="text/css">
		#searchform label {
			margin-right: 10px;
		}
	</style>
</head>
<body >
<div class="easyui-layout" style="width: 100%;height: 100%;">
	<!-- 左边用户架构树 -->
    <div data-options="region:'west',title:'组织架构',split:true" style="width:20%;height: 82%;">
    	<div id="reloadBtn">
    		<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="$('#tt').treegrid('reload');">刷新</a>
    	</div>
    	<div id="tt"></div>
    </div>
    <!-- 主面板 -->
    <div id="eastPanel" data-options="region:'center',title:'用户列表'" style="width:80%; height: 82%; spadding:5px;background:#eee;">
	<!-- 数据表格 始 -->
	<table id="dg"></table>
	</div>
	<!-- 数据表格 终-->
	<!-- 数据表格 按纽组 始 -->
	<div id="toolbar" >

		<div>
			<form action="" id="searchform">
				<label>帐号:</label>
				<label><input  type="text" id="user_account" class="easyui-textbox" style="width: 100px;"/></label>
				<label>姓名:</label>
				<label><input  type="text" id="user_userName" class="easyui-textbox" style="width: 100px;"/></label>
				<label>电话:</label>
				<label><input  type="text" id="user_phone" class="easyui-textbox" style="width: 100px;"/></label>
				<label>邮箱:</label>
				<label><input  type="text" id="user_email" class="easyui-textbox" style="width: 100px;"/></label>
				<br/>
				<label>地址:</label>
				<label><input  type="text" id="user_address" class="easyui-textbox" style="width: 100px;"/></label>
				<label>性别:</label>
				<label>
					<input  type="text" id="role_status" class="easyui-combobox" style="width: 100px;"
							data-options="
								valueField:'label',
								textField:'value',
								panelHeight:50,
								data: [{
									label: '男',
									value: '男'
								},{
									label: '女',
									value: '女'
								}]
								"
							/>
				</label>
				<input type="hidden" id="hidden_searchUnitId" />
				<label><a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUser.doSearch()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a></label>
			</form>
		</div>
		<c:if test="${userType == 'SiteManage' || userType == 'AllSiteManage'}">
		<div>
			<a id="addbtn" href="#" onclick="SysUser.openAddForm();"  class="easyui-linkbutton" >添加用户</a>
			<a id="editbtn" href="#" onclick="SysUser.openEditForm();" class="easyui-linkbutton" >编辑用户</a>
			<a id="showbtn" href="#" onclick="SysUser.showDetail();" class="easyui-linkbutton" >查看用户</a>
			<a id="forzenbtn" href="#" onclick="SysUser.forzenUser();" class="easyui-linkbutton" >冻结用户</a>
			<a id="unforzenbtn" href="#" onclick="SysUser.unForzenUser();" class="easyui-linkbutton" >激活用户</a>
			<a id="delbtn" href="#" onclick="SysUser.deleteUser();" class="easyui-linkbutton" >删除用户</a>
			<a id="" href="#" onclick="SysUser.resetPassword();" class="easyui-linkbutton" >重置密码</a>
		</div>
		</c:if>

	</div>
	<!-- 数据表格 按纽组 终 -->
	
	<!-- 编辑框  始-->
	<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysUser.clearForm()" style="width:580px;top: 80px;">
		<iframe name="editIframe" id="editIframe" scrolling="yes" frameborder="0"  style="width:100%;height:400px;"></iframe>
    </div>
	<!-- 编辑框 终 -->
	
	<!-- 详情框  始-->
	<div class="easyui-dialog" id="show_panel" closed="true"  onClose="SysUser.clearForm()" style="width:580px;height:420px;top: 80px;">
        <form id="showform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>登录帐号:</td>
                    <td>
                    <input class="easyui-textbox" type="text" name="account" readonly="readonly" /></td>
                    <td>姓名:</td>
                    <td>
                    <input class="easyui-textbox" type="text" name="userName" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>联系方式:</td>
                    <td><input class="easyui-textbox" type="text" name="mobile" readonly="readonly" /></td>
                    <td>邮箱:</td>
                    <td><input class="easyui-textbox" type="text" name="email" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>组织架构:</td>
                    <td colspan="3" id="sysUnit_name">
                    </td>
                </tr>
                <tr>
                    <td>角色:</td>
                    <td colspan="3">
                    	<select class="easyui-combobox" readonly="readonly" id="s_roleIds" name="roleIds"  data-options="multiple:true,multiline:true" style="width:330px;height:80px">
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td colspan="3"><s:radio list="#{'男':'男','女':'女' }" disabled="true" name="gender"></s:radio></td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td colspan="3"><input class="easyui-textbox" readonly="readonly" name="address" data-options="multiline:true" style="width:330px;height:60px"></input></td>
                </tr>
               <tr>
                    <td>状态:</td>
                    <td>
                    	<s:select list="#{0:'冻结',1:'激活' }" disabled="true" name="isUse"></s:select>
                    </td>
                    <td>登录次数:</td>
                    <td>
                    <input class="easyui-textbox" readonly="readonly" type="text" name="loginNum" ></input></td>
                </tr>
                <tr>
                    <td>创建时间:</td>
                    <td>
                    	<input class="easyui-textbox" readonly="readonly" type="text" name="createdTime" ></input>
                    </td>
                    <td>更新时间:</td>
                    <td>
                    <input class="easyui-textbox" readonly="readonly" type="text" name="updateTime" ></input></td>
                </tr>
            </table>
        </form> 
    </div>
	<!-- 详情框 终 -->
	<!-- 搜索角色工具栏 -->
</div>
</body>
</html>