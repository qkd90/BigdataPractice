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
<title>角色管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysRole/sysRole.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysRole/manage.css">
<script type="text/javascript" src="/js/sys/sysRight/sysRight.js"></script>
    <style type="text/css">
        #searchform label {
            margin-right: 10px;
        }
    </style>
</head>
<body >
	<!-- 数据表格 始 -->
	<table id="dg"></table>
	<!-- 数据表格 终-->
	<!-- 数据表格 按纽组 始 -->
	<div id="toolbar" >
        <div style="margin: 10px 0px 10px 10px;">
            <form action="" id="searchform">
                <label>角色名:</label>
                <label><input  type="text" id="role_name" class="easyui-textbox" style="width: 100px;" /></label>
                <label>描述:</label>
                <label><input  type="text" id="role_remark" class="easyui-textbox" style="width: 100px;" /></label>
                <label>角色状态:</label>
                <label>
                    <input  type="text" id="role_status" class="easyui-combobox" style="width: 100px;"
                            data-options="
								valueField:'label',
								textField:'value',
								panelHeight:50,
								data: [{
									label: '0',
									value: '激活'
								},{
									label: '1',
									value: '冻结'
								}]
								"
                            />
                </label>
                <label>
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysRole.doSearch()">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
                </label>
            </form>
        </div>
        <c:if test="${userType == 'SiteManage' || userType == 'AllSiteManage'}">
        <div style="margin: 10px 0px 5px 10px;">
            <a id="addbtn" href="#" onclick="SysRole.openAddForm();"  class="easyui-linkbutton" >添加角色</a>
            <a id="editbtn" href="#" onclick="SysRole.openEditForm();" class="easyui-linkbutton" >编辑角色</a>
            <a id="showbtn" href="#" onclick="SysRole.showDetail();" class="easyui-linkbutton" >查看角色</a>
            <a id="forzenbtn" href="#" onclick="SysRole.forzenRole();" class="easyui-linkbutton" >冻结角色</a>
            <a id="unforzenbtn" href="#" onclick="SysRole.unForzenRole();" class="easyui-linkbutton" >激活角色</a>
            <a id="delbtn" href="#" onclick="SysRole.deleteRole();" class="easyui-linkbutton" >删除角色</a>
            <a id="delbtn" href="#" onclick="SysRole.openSetRight();" class="easyui-linkbutton" >角色授权</a>
        </div>
        </c:if>
	</div>
	<!-- 数据表格 按纽组 终 -->
	
	<!-- 编辑框  始-->
	<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysRole.clearForm()" style="width:300px;top: 80px;">
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>角色名:</td>
                    <td>
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="delFlag"/>
                    <input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>序号:</td>
                    <td><input class="easyui-numberbox" type="text" name="seq" ></input></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td>
                    	<%-- <s:select list="#{0:'激活',1:'冻结' }" name="status"></s:select> --%>
                        <select class="easyui-combobox" name="status">
                        	<option value="0">激活</option>
                        	<option value="1">冻结</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>角色描述:</td>
                    <td><input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
               
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysRole.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysRole.clearForm()">重置</a>
        </div>
    </div>
	<!-- 编辑框 终 -->
	
	<!-- 详情框  始-->
	<div class="easyui-dialog" id="show_panel" closed="true"  onClose="SysRole.clearForm()" style="width:300px;top: 80px;">
           <form action="" id="showform">
            <table cellpadding="5">
                <tr>
                    <td>角色名:</td>
                    <td>
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="delFlag"/>
                    <input disabled="disabled"  class="easyui-textbox"  type="text" name="name" ></input></td>
                </tr>
                <tr>
                    <td>序号:</td>
                    <td><input disabled="disabled" class="easyui-numberbox" type="text" name="seq" ></input></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td>
                        <select  disabled="disabled" class="easyui-combobox" name="status">
                        	<option value="0">激活</option>
                        	<option value="1">冻结</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>角色描述:</td>
                    <td><input  disabled="disabled" class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
               
            </table>
            </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#show_panel').dialog('close')">关闭</a>
        </div>
    </div>
	<!-- 详情框 终 -->
</body>
</html>