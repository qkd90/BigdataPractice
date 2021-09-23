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

<title>会员管理</title>
<%@ include file="../../common/common141.jsp"%>


</head>
<body>
<table id="memberTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'/member/member/searchList.jhtml',toolbar:'#memberTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'nickName'" width="100" align="left">会员昵称</th>
			<th data-options="field:'realName'" width="100" align="center">会员姓名</th>
			<th data-options="field:'cardNo'" width="150" align="center">会员卡号</th>
			<th data-options="field:'picture',formatter:viewMember" width="100" align="center">会员图片</th>
			<th data-options="field:'phone'" width="100" align="center">手机号码</th>
		<!-- 	<th data-options="field:'mobile'" width="100" align="center">座机号码</th> -->
			<th data-options="field:'point',formatter:formatPoint" width="100" align="center">会员积分</th>
			<th data-options="field:'memberType.levelName',formatter:function(value,row){if(row.memberType!=null){return row.memberType.levelName+'('+row.memberType.discount+'折)';}else{return value;}}" width="120" align="center">会员等级</th>
			<th data-options="field:'state',formatter:function(value){
				if(value=='启用'){
					return '<font style=color:green>启用</font>';
				}else{
					return '<font style=color:red>'+value+'</font>';
				}
			}" width="100" align="center">使用状态</th>
			<c:if test="${session.loginuser.roleid=='1'}">
				<th data-options="field:'shopSimpleName'" width="80" align="center">所属店铺</th>
			</c:if>
			<th data-options="field:'operate',formatter:openSaleDetail" width="100" align="center">操作</th>
		</tr>
	</thead>
</table>
<div id="memberTool" style="padding: 5px; height: auto">
	会员昵称：<input type="text" id="search_nickName" style="width: 150px;line-height:20px;border:1px solid #ccc"></input> 
	会员姓名：<input type="text" id="search_realName" style="width: 150px;line-height:20px;border:1px solid #ccc"></input>
	会员卡号：<input type="text" id="search_cardNo" style="width: 180px;line-height:20px;border:1px solid #ccc"></input>
	手机号码：<input type="text" id="search_phone" style="width: 150px;line-height:20px;border:1px solid #ccc"></input>
	使用状态：<select id="search_state" name="state">
			<option value="">请选择</option>
			<option value="启用">启用</option>
			<option value="停用">停用</option>
		   </select>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="memberQuery();">查询</a>
	<br/>
	<a href="javascript:void(0)"
		onclick="openmemberWindow('添加会员信息','/member/member/memberInput.jhtml','add')"
		class="easyui-linkbutton"  >添加</a>
 	<a href="javascript:void(0)"
		onclick="openmemberWindow('修改会员信息','/member/member/memberInput.jhtml','edit')"
		class="easyui-linkbutton"  >修改</a>
	<a href="javascript:void(0)" 
		onclick="deletemembers()"  >删除</a>
	<a href="javascript:void(0)"
		onclick="openmemberWindow('查看会员详情','/member/member/memberInfo.jhtml','looking')"
		class="easyui-linkbutton"  >查看</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" 
		  onclick="reloadMembersTable();">刷新</a>
	
</div>


<script type="text/javascript">
	// 添加和修改操作
	function openmemberWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#memberTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择会员信息', 'warning');
			} else {
				$('#memberWindow').window({
					title : title
				});
				$('#memberWindow').window('open');
				$('#memberWindow').window('refresh', url + '?member.id=' + rows.id);
				$('#memberTable').datagrid('clearSelections');
			}
		}  else if (type == 'add') {
			$('#memberWindow').window({
				title : title
			});
			$('#memberWindow').window('open');
			$('#memberWindow').window('refresh', url);
			$('#memberTable').datagrid('clearSelections');
		}  else if(type == 'looking'){
			var rows = $('#memberTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择会员信息', 'warning');
			} else {
				$('#memberInfoWindow').window({
					title : title
				});
				$('#memberInfoWindow').window('open');
				$('#memberInfoWindow').window('refresh', url + '?member.id=' + rows.id);
				$('#memberTable').datagrid('clearSelections');
			}
		}
	}
	
	
	// 批量删除
	function deletemembers() {
		var rows = $('#memberTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的会员信息！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager.confirm('温馨提示', '确定要删除选中的会员信息吗？', function(r) {
				if (r) {
					$.get('/member/member/deleteMembers.jhtml', {
						'ids' : ids
					}, function(result) {
						if (result.success) {
							$.messager.show({ 
								title : '温馨提示',
								msg : '成功删除会员信息！'
							});
							$('#memberTable').datagrid("reload");
							$('#memberTable').datagrid('clearSelections');
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
	function reloadMembersTable(){
		$('#memberTable').datagrid("reload");
		$('#memberTable').datagrid('clearSelections');
	}

	// 查询条件
	function memberQuery() {
		$('#memberTable').datagrid("load", {
			'member.nickName' : $('#search_nickName').val(),
			'member.realName' : $('#search_realName').val(),
			'member.cardNo' : $('#search_cardNo').val(),
			'member.phone' : $('#search_phone').val(),
			'member.state' : $("select[id=search_state]").find("option:selected").val()
		});
	}
	
	//执行操作，查看消费记录
	function openSaleDetail(val,row){
		return "<a href='javascript:void(0)' onclick='openSaleDetailWindow("+row.id+")' style='color:green'>【消费记录】</a>";
	}
	// 打开会员消费详情的窗口
	function openSaleDetailWindow(id){
		$('#memberRecordInfoWindow').window({
			title : '查看会员消费记录'
		});
		$('#memberRecordInfoWindow').window('open');
		$('#memberRecordInfoWindow').window('refresh', '/member/member/memberRecordList.jhtml?member.id=' + id);
		$('#memberTable').datagrid('clearSelections');
	}
	
	//显示图片
	function viewMember(val, row) {
		if(val==""){
			return "<span style='color:red;'>无</span>";
		}else{
		return "<a href=\"<%=basePath%>/"+val+"\" target=\"_blank\"><img width='70px' height='60px' border='0' src=\"<%=basePath%>/"+val+"\"></a>";
		}
	}
	
	//打开会员记录
	function formatPoint(value,row){
		if(row.point!=0){
			return  row.point+'<a href="javascript:void(0)" class="easyui-linkbutton" style="color:green"   onclick="openMemberPointHistory('+row.id+');"> [积分记录]</a>';
		}
	}
	
	function openMemberPointHistory(memberId){
		$('#memberPointHistoryWindow').window('open');
		$('#memberPointHistoryWindow').window('refresh', '/member/member/memberPointHistory.jhtml?member.id=' + memberId);
		$('#memberTable').datagrid('clearSelections');
	}
</script>

<!-- 添加和修改数据页面框 -->
<div id="memberWindow" class="easyui-window"
	style="width: 800px; height: 450px; top: 30px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 查看详情页面框 -->
<div id="memberInfoWindow" class="easyui-window"
	style="width: 800px; height: 420px; top: 30px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 查看会员消费记录详情的页面框 -->
<div id="memberRecordInfoWindow" class="easyui-window"
	style="width: 800px; height: 420px; top: 30px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>

<!-- 查看会员积分记录详情的页面框 -->
<div id="memberPointHistoryWindow" class="easyui-window" title="会员积分记录"
	style="width: 800px; height: 420px; top: 30px;"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>

</body>
</html>