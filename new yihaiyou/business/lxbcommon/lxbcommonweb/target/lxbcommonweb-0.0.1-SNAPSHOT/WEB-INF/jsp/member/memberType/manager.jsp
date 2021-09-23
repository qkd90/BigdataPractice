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

<title>会员等级管理</title>
<%@ include file="../../common/common141.jsp"%>


</head>
<body>
<table id="memberTypeTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'/member/memberType/searchList.jhtml',toolbar:'#memberTypeTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'levelName'" width="100" align="center">等级名称</th>
			<th data-options="field:'discount',formatter:function(value){if(value!=null){return value+'折';}}" width="100" align="center">折扣</th>
			<c:if test="${session.loginuser.roleid=='1'}">
				<th data-options="field:'shopSimpleName'" width="80" align="center">所属店铺</th>
			</c:if>
			<th data-options="field:'isOrder'" width="100" align="center">排序值</th>
		</tr>
	</thead>
</table>
<div id="memberTypeTool" style="padding: 5px; height: auto">
	等级名称：<input type="text" id="search_levelName" style="width: 180px;line-height:20px;border:1px solid #ccc"></input> 
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="memberTypeQuery();">查询</a>
	<br/>
	<a href="javascript:void(0)"
		onclick="openmemberTypeWindow('添加会员等级信息','/member/memberType/memberTypeInput.jhtml','add')"
		class="easyui-linkbutton"  >添加</a>
 	<a href="javascript:void(0)"
		onclick="openmemberTypeWindow('修改会员等级信息','/member/memberType/memberTypeInput.jhtml','edit')"
		class="easyui-linkbutton"  >修改</a>
	<a href="javascript:void(0)" 
		onclick="deletememberTypes()" class="easyui-linkbutton"  >删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" 
		 onclick="reloadMemberTypesTable();">刷新</a>
	
</div>


<script type="text/javascript">
	// 添加和修改操作
	function openmemberTypeWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#memberTypeTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择会员等级信息', 'warning');
			} else {
				$('#memberTypeWindow').window({
					title : title
				});
				$('#memberTypeWindow').window('open');
				$('#memberTypeWindow').window('refresh', url + '?memberType.id=' + rows.id);
				$('#memberTypeTable').datagrid('clearSelections');
			}
		}  else if (type == 'add') {
			$('#memberTypeWindow').window({
				title : title
			});
			$('#memberTypeWindow').window('open');
			$('#memberTypeWindow').window('refresh', url);
			$('#memberTypeTable').datagrid('clearSelections');
		}
	}
	
	// 批量删除
	function deletememberTypes() {
		var rows = $('#memberTypeTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的会员等级信息！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager.confirm('温馨提示', '确定要删除选中的会员等级信息吗？', function(r) {
				if (r) {
					$.get('/member/memberType/deleteMemberTypes.jhtml', {
						'ids' : ids
					}, function(result) {
						if (result.success) {
							$.messager.show({ 
								title : '温馨提示',
								msg : '成功删除会员等级信息！'
							});
							$('#memberTypeTable').datagrid("reload");
							$('#memberTypeTable').datagrid('clearSelections');
						} else {
							$.messager.show({ 
								title : '温馨提示',
								msg : result.errorMsg
							});
						}
					}, 'json');
				}
			});
		}
	}
	
	// 刷新
	function reloadMemberTypesTable(){
		$('#memberTypeTable').datagrid("reload");
		$('#memberTypeTable').datagrid('clearSelections');
	}
	

	// 查询条件
	function memberTypeQuery() {
		$('#memberTypeTable').datagrid("load", {
			'memberType.levelName' : $('#search_levelName').val()
		});
	}
	

</script>

<!-- 添加和修改数据页面框 -->
<div id="memberTypeWindow" class="easyui-window"
	style="width: 400px; height: 250px; top: 10px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
</body>
</html>