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
<script type="text/javascript" src="/js/sys/sysUser/editSysUser.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysUnit/manage.css"></link>
<style type="text/css">
	#class_id td{
		padding:4px;
	}
	
</style>



</head>
<body style="background-color: white;" >
	<div id="class_id">
	<!-- 编辑框  始-->
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>登录帐号:</td>
                    <td>
                    <input type="hidden" name="delFlag" value="${user.delFlag}"/>
                    <input type="hidden" name="status" value="${user.status}"/>
                    <input type="hidden" id="hidden_userid" name="id" value="${user.id}"/>
<%--                     <input type="hidden" name="createdTime" value="${user.createdTime}"/>  --%>
<%--                     <input type="hidden" name="updateTime" value="${user.updateTime }"/>  --%>
                    <input type="hidden" name="password" value="${user.password }"/>
                    <input type="hidden" name="isUse" value="${user.isUse }"/>
                    <input type="hidden" name="delFlag" value="${user.delFlag }"/>
                    <input type="hidden" name="loginNum" value="${user.loginNum }"/>
                    <input class="easyui-textbox" type="text" id="account" name="account" data-options="required:true,validType:'letOrNum'" value="${user.account}"></input></td>
                    <td>姓名:</td>
                    <td>
                    <input class="easyui-textbox" type="text" name="userName" data-options="required:true" value="${user.userName}"></input></td>
                </tr>
                <tr>
                    <td>联系方式:</td>
                    <td><input class="easyui-textbox" type="text" name="mobile" value="${user.mobile }" ></input></td>
                    <td>邮箱:</td>
                    <td><input class="easyui-textbox" type="text" name="email" value="${user.email }" ></input></td>
                </tr>
                <tr>
                    <td>组织架构:</td>
                    <td colspan="3">
                    	<input type="hidden" id="hidden_uintid" value="${user.sysUnit.id}"></input>
                    	<input class="easyui-combotree" id="unit_id" type="text" name="sysUnit.id" data-options="required:true,width:330" ></input>
                    </td>
                </tr>
                <tr>
                    <td>角色:</td>
                    <td colspan="3">
                    	<input type="hidden" id="hidden_roleId" value="${user.roles}"></input>
                    	<select class="easyui-combobox" id="roleIds" name="roleIds"  data-options="multiple:true,multiline:true" style="width:330px;height:50px">
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td colspan="3"><s:radio list="#{'男':'男','女':'女' }" name="gender" value="user.gender"></s:radio></td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td colspan="3"><input class="easyui-textbox" name="address" data-options="multiline:true" style="width:330px;height:60px" value="${user.address }"></input></td>
                </tr>
               
            </table>
        </form>
        <div data-options="region:'west',title:'组织架构',split:true" style="width:200px;display:none;">
	    	<div id="reloadBtn">
	    		<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="$('#tt').treegrid('reload');">刷新</a>
	    	</div>
	    	<div id="tt"></div>
	    </div>
        
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="EditSysUser.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="EditSysUser.clearForm()">重置</a>
        </div>
       </div>
	<!-- 编辑框 终 -->
	<!-- 搜索角色工具栏 -->
</body>
</html>