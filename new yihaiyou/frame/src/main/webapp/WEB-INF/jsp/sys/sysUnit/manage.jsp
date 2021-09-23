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
<title>组织管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysUnit/sysUnit.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysUnit/manage.css">
    <style type="text/css">
        #searchform label {
            margin-right: 10px;
        }
    </style>
</head>
<body style="background-color: white;">
<div class="easyui-layout" style="width: 100%;height: 100%;">
    <div style="height: 100%;width: 100%;">
        <!-- 左边组织架构树 -->
        <div style="width: 19%;height: 100%; float: left;">
            <%--<div id="reloadBtn">--%>
                <%--<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="$('#tt').treegrid('reload');">刷新</a>--%>
            <%--</div>--%>
            <div id="tt"></div>
        </div>
        <!-- 主面板 -->
        <div style="width: 81%;height: 100%; float:right;">
            <!-- 数据表格 按纽组 始 -->
            <div id="toolbar" >
                <div>
                    <form action="" id="searchform">
                        <label>组织名:</label>
                        <label><input  type="text" class="easyui-textbox" id="role_name" style="width: 100px" /></label>
                        <!--
                        <td>组织编号:</td>
                        <td><input  type="text" id="u_unitNo" ></input></td>
                         -->
                        <label>描述:</label>
                        <label><input  type="text" class="easyui-textbox" id="role_remark" style="width: 100px" /></label>
                        <label>组织状态:</label>
                        <label>
                            <input class="easyui-combobox" id="role_status" style="width: 100px"
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
                    }]" />
                        </label>
                        <label>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.doSearch()">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.doClearSearch()">重置</a>
                        </label>
                    </form>
                </div>
                <c:if test="${userType == 'SiteManage' || userType == 'AllSiteManage'}">
                <div>
                    <a id="addbtn" href="#" onclick="SysUnit.openAddForm();"  class="easyui-linkbutton" >添加组织</a>
                    <a id="editbtn" href="#" onclick="SysUnit.openEditForm();" class="easyui-linkbutton" >编辑组织</a>
                    <a id="showbtn" href="#" onclick="SysUnit.showDetail();" class="easyui-linkbutton" >查看组织</a>
                    <a id="forzenbtn" href="#" onclick="SysUnit.forzenUnit();" class="easyui-linkbutton" >冻结组织</a>
                    <a id="unforzenbtn" href="#" onclick="SysUnit.unForzenUnit();" class="easyui-linkbutton" >激活组织</a>
                    <a id="delbtn" href="#" onclick="SysUnit.deleteUnit();" class="easyui-linkbutton" >删除组织</a>
                </div>
                </c:if>
            </div>
            <!-- 数据表格 按纽组 终 -->
            <!-- 数据表格 始 -->
            <table id="dg"></table>
        </div>
    </div>

	<!-- 数据表格 终-->

	
	<!-- 编辑框  始-->
	<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysUnit.clearForm()" style="width:300px;top: 80px;">
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>上级组织:</td>
                    <td id="parent_name"><!-- <input class="easyui-textbox" type="text" name="unitNo" data-options="required:true" ></input>
                    <input type="hidden" name="parentId"/>
                    <a href="javascript:void(0)"  class="easyui-linkbutton" onClick="SysUnit.openSetRight()" >选择</a>
                     --></td>
                </tr>
                <tr>
                    <td>组织名称:</td>
                    <td>
                    <input type="hidden" name="id"/>
                    <input type="hidden" id="parent_id" name="parentUnit.id"/>
                    <input type="hidden" id="companyUnitId" name="companyUnit.id"/>
                    <input type="hidden" name="delFlag"/>
                    <input type="hidden" name="status"/>
                    <input type="hidden" name="sysSite.id" id="site_id"/>
                    <input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
                </tr>
                <!-- 
                <tr>
                    <td>组织编码:</td>
                    <td><input class="easyui-textbox" type="text" name="unitNo" id="unitNo" readonly="readonly" disabled="disabled"></input></td>
                </tr>
                 -->
                <tr>
                    <td>所属站点:</td>
                    <td id="site_name"></td>
                </tr>
                <tr>
                    <td>序号:</td>
                    <td><input class="easyui-numberbox" type="text" name="seq" ></input></td>
                </tr>
                <tr>
                    <td>组织描述:</td>
                    <td><input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
               
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.clearForm()">重置</a>
        </div>
    </div>
	<!-- 编辑框 终 -->
	
	<!-- 详情框  始-->
	<div class="easyui-dialog" id="show_panel" closed="true"  onClose="SysUnit.clearForm()" style="width:300px;top: 80px;">
        <form id="showform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>上级组织:</td>
                    <td id="s_parent_name"></td>
                </tr>
                <tr>
                    <td>组织名称:</td>
                    <td>
                    <input class="easyui-textbox" type="text" name="name" readonly="readonly"></input></td>
                </tr>
                <!-- 
                <tr>
                    <td>组织编码:</td>
                    <td><input class="easyui-textbox" type="text" name="unitNo" id="unitNo" readonly="readonly" ></input></td>
                </tr>
                 -->
                <tr>
                    <td>所属站点:</td>
                    <td id="s_site_name"></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td ><s:select list="#{0:'启用',1:'冻结' }" name="status" disabled="true"></s:select></td>
                </tr>
                <tr>
                    <td>序号:</td>
                    <td><input class="easyui-numberbox" type="text" name="seq" readonly="readonly" ></input></td>
                </tr>
                <tr>
                    <td>组织描述:</td>
                    <td><input class="easyui-textbox" name="remark" readonly="readonly" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysUnit.clearForm()">重置</a>
        </div>
    </div>
	<!-- 详情框 终 -->
</div>
</body>
</html>