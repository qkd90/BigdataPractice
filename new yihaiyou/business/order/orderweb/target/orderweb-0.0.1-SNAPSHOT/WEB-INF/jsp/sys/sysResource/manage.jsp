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
<title>资源管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysResource/menuSelect.js"></script>
<script type="text/javascript" src="/js/sys/sysResource/sysResource.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysResource/manage.css">
	<style type="text/css">
		#searchform label {
			margin-right: 10px;
		}
	</style>
</head>
<body  >
	<div class="easyui-layout" style="width: 100%;height: 100%;">
    <!--查询区域 始 页面上方-->
    <div data-options="region:'north',title:'查询条件 ',split:true" id="tb" style="height:25%; width:100%; padding: 10px;">
		<form action="" id="searchform">
			<label>资源名:
				<input  type="text" id="r_name" class="easyui-textbox" style="width: 100px;"/>
			</label>
			<label>链接:
				<input  type="text" id="r_resourceUrl" class="easyui-textbox" style="width: 100px;"/>
			</label>
			<label>资源编号:
				<input  type="text" id="r_resourceNo" class="easyui-textbox" style="width: 100px;"/>
			</label>
			<label>资源状态:
				<input  type="text" id="r_status" class="easyui-combobox" style="width: 100px;"
						data-options="
                                    valueField:'label',
                                    textField:'value',
                                    panelHeight:50,
                                    data: [{
                                        label: '0',
                                        value: '启用'
                                    },{
                                        label: '1',
                                        value: '冻结'
                                    }]
                                    "
						/>
			</label>
			<label>是否为公共资源:
				<input  type="text" id="r_isPublic" class="easyui-combobox" style="width: 100px;"
						data-options="
                                    valueField:'label',
                                    textField:'value',
                                    panelHeight:50,
                                    data: [{
                                        label: '0',
                                        value: '私有资源'
                                    },{
                                        label: '1',
                                        value: '公共资源'
                                    }]
                                    "
						/>
			</label>
			<label>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysResource.doSearch()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
			</label>
		</form>
    </div>
	<!--查询区域 终  -->
	<!-- 左边资源树 -->
    <div data-options="region:'west',title:'模块树',split:true" style="width:20%; height: 80%;" >
    	<div id="tt"></div>
    	<div id="reloadBtn">
    		<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="SysResource.initMenuTree()">刷新</a>
    	</div>
    </div>   
    <!-- 主面板 -->
    <div id="eastPanel" data-options="region:'center',title:'资源列表'" style="width:80%; height: 80%;padding:5px;background:#eee;">
	    <!-- 数据表格 始 -->
		<table id="dg"></table>
		<!-- 数据表格 终-->
		<!-- 数据表格 按纽组 始 -->
		<div id="toolbar" >
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_add')">
				<a id="addbtn" href="#" onclick="SysResource.openAddForm();"  class="easyui-linkbutton" >添加模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_edit')">
				<a id="editbtn" href="#" onclick="SysResource.openEditForm();" class="easyui-linkbutton" >编辑模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_show')">
				<a id="showbtn" href="#" onclick="SysResource.showDetail();" class="easyui-linkbutton" >查看模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_frozen')">
				<a id="forzenbtn" href="#" onclick="SysResource.forzenResource();" class="easyui-linkbutton" >冻结模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_unfrozen')">
				<a id="unforzenbtn" href="#" onclick="SysResource.unForzenResource();" class="easyui-linkbutton" >激活模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_del')">
				<a id="delbtn" href="#" onclick="SysResource.deleteResource();" class="easyui-linkbutton" >删除模块</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_public')">
				<a id="publicbtn" href="#" onclick="SysResource.publicResource();" class="easyui-linkbutton" >公开</a>
			</s:if>
			<s:if test="@com.data.data.hmly.action.sys.LoginAction@hasNo('resource_private')">
				<a id="privatebtn" href="#" onclick="SysResource.privateResource();" class="easyui-linkbutton" >私有</a>
			</s:if>
		</div>
		<!-- 数据表格 按纽组 终 -->
    </div>   
    </div>
    <!-- 编辑框  始-->
		<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysResource.clearForm()" style="width:500px;top: 80px;">
	        <form id="ff" method="post">
	            <table cellpadding="5">
	                <tr>
	                    <td>所属模块:</td>
	                    <td>
	                    	<input type="hidden" id="menuid" name="sysMenu.menuid"/>
	                    	<input class="easyui-textbox" data-options="required:true"  type="text" name="menuname" id="menuname" readonly="readonly"/>
	                    	<!-- //,buttonText:'选择模块',onClickButton:SysResource.openSelectMenu -->
	                    	<a href="javascript:void(0)"  class="easyui-linkbutton" onClick="SysResource.openSelectMenu()" >选择模块</a>
	                    </td>
	                </tr>
	                <tr>
	                    <td>资源名:</td>
	                    <td>
	                    <input type="hidden" name="id"/>
	                    <input type="hidden" name="updater"/>
	                    <input type="hidden" name="delFlag"/>
	                    <input type="hidden" name="status"/>
	                    <input type="hidden" name="isPublic"/>
	                    <input class="easyui-textbox" type="text" name="name" data-options="required:true"/></td>
	                </tr>
	                <tr>
	                    <td>资源编号:</td>
	                    <td><input class="easyui-textbox" type="text" name="resourceNo"  /></td>
	                </tr>
	                <tr>
	                    <td>链接:</td>
	                    <td><input class="easyui-textbox" type="text" name="resourceUrl"  /></td>
	                </tr>
	                <tr>
	                    <td>序号:</td>
	                    <td><input class="easyui-numberbox" type="text" name="seq" /></td>
	                </tr>
	               <%--  <tr>
	                    <td>状态:</td>
	                    <td>
	                    	<s:select list="#{0:'启用',1:'冻结' }" name="status"></s:select>
	                        <select class="easyui-combobox" name="status">
	                        	<option value="0">启用</option>
	                        	<option value="1">冻结</option>
	                        </select>
	                    </td>
	                </tr> --%>
	                <tr>
	                    <td>资源描述:</td>
	                    <td><input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px"/></td>
	                </tr>
	               
	            </table>
	        </form>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysResource.submitForm()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysResource.clearForm()">重置</a>
	        </div>
	    </div>
		<!-- 编辑框 终 -->
		
		<!-- 详情框  始-->
		<div class="easyui-dialog" id="show_panel" closed="true"  onClose="SysResource.clearForm()" style="width:500px;top: 80px;">
	           <form action="" id="showform">
	            <table cellpadding="5">
	                <tr>
	                    <td>上级资源:</td>
	                    <td id="show_parent_name"></td>
	                </tr>
	                <tr>
	                    <td>资源名:</td>
	                    <td>
	                    <input class="easyui-textbox" type="text" name="menuname"  disabled="disabled"/></td>
	                </tr>
	                <tr>
	                    <td>链接:</td>
	                    <td><input class="easyui-textbox" type="text" name="url"  disabled="disabled"/></td>
	                </tr>
	                <tr>
	                    <td>图标:</td>
	                    <td><input class="easyui-textbox" type="text" name="icon"   disabled="disabled"/></td>
	                </tr>
	                <tr>
	                    <td>等级:</td>
	                    <td><input class="easyui-numberbox" type="text" name="menulevel"  disabled="disabled"/></td>
	                </tr>
	                <tr>
	                    <td>序号:</td>
	                    <td><input class="easyui-numberbox" type="text" name="seq"  disabled="disabled"/></td>
	                </tr>
	               <tr>
	                    <td>状态:</td>
	                    <td>
	                    	<s:select list="#{0:'启用',1:'冻结' }" name="status" disabled="true"></s:select>
	                    </td>
	                </tr> 
	                <tr>
	                    <td>资源描述:</td>
	                    <td><input class="easyui-textbox" name="remark"  disabled="disabled" data-options="multiline:true" style="height:60px"/></td>
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