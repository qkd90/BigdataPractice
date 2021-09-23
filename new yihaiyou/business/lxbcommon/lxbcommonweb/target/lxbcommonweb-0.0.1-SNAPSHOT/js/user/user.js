// 添加和修改操作
function openuserWindow(title, url, type,userId,roleId) {
	if(type == 'add') {
		$('#userWindow').window({
			title : title
		});
		$('#userWindow').window('open');
		$('#userWindow').window('refresh', url);
		$('#userTable').datagrid('clearSelections');
	}else if (type == 'edit') {
		var row = $('#userTable').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
			return false;
		} else {
			if(row.id!=userId&&row.pid!=userId&&roleId!=1&&roleId!=4){
				$.messager.alert('温馨提示', '您没有权限修改其他人的用户信息！', 'warning');
				$('#userTable').datagrid('clearSelections');
				return false;
			}else{
				$('#userWindow').window({
					title : title
				});
				$('#userWindow').window('open');
				$('#userWindow').window('refresh', url + '?user.id=' + row.id);
				$('#userTable').datagrid('clearSelections');
			}
		}
	}else if(type == 'addManager'){
		// 添加店长
		$('#addshopManagerWindow').window({
			title : title
		});
		$('#addshopManagerWindow').window('open');
		$('#addshopManagerWindow').window('refresh', url);
		$('#userTable').datagrid('clearSelections');
	}
}

// 刷新
function reloadUsersTable(){
	$("#userTable").datagrid("reload");
	$('#userTable').datagrid('clearSelections');
}

// 查询条件
function userQuery() {
	$('#userTable').datagrid("load", {
		'user.account' : $('#search_account').val(),
		'user.userName' : $('#search_userName').val(),
		'user.phone' : $('#search_phone').val(),
		'user.isUse' : $("select[id=search_isUse]").find("option:selected").val(),
	});
}

// 修改用户的激活状态
function changeState(val,row){
	if(val=='已激活'){
		return "<a href='javascript:void(0);' onclick=changeUserState("+row.id+",'"+val+"') style='color:green;'>【已激活】</a>";
	}else{
		return "<a href='javascript:void(0);' onclick=changeUserState("+row.id+",'"+val+"') style='color:red;'>【已冻结】</a>";
	}
}
// 异步改变状态
function changeUserState(id,state){
	var param = {'id':id,'state':state};
	if(state=='已激活'){
		$.messager.confirm("温馨提示","您确定要冻结该用户信息吗？",function(r){
			if(r){
				$.post('/user/user/changeState.jhtml',param,function(result){
					if(result.success){
						$.messager.show({
							title:"温馨提示",
							msg:result.errorMsg
						});
						$("#userTable").datagrid("reload");
					}
				});
			}
		});
	}else{
		$.messager.confirm("温馨提示","您确定要激活该用户信息吗？",function(r){
			if(r){
				$.post('/user/user/changeState.jhtml',param,function(result){
					if(result.success){
						$.messager.show({
							title:"温馨提示",
							msg:result.errorMsg
						});
						$("#userTable").datagrid("reload");
					}
				});
			}
		});
	}
}

// 初始化密码
function initPass(){
	var rows = $('#userTable').datagrid('getChecked');
	if (rows == null || rows == '') {
		$.messager.alert('温馨提示', '请选择要初始化的用户信息！', 'warning');
	} else {
		var ids = '';
		for ( var i = 0; i < rows.length; i++) {
			if (ids != '')
				ids += ',';
			ids += rows[i].id;
		}
		$.messager.confirm('温馨提示', '确定要初始化选中的用户密码吗？', function(r) {
			if (r) {
				$.get('/user/user/initPassword.jhtml', {
					'ids' : ids
				}, function(result) {
					if (result.success) {
						$.messager.show({ 
							title : '温馨提示',
							msg : '成功初始化用户密码为：123456，请注意及时更改！'
						});
						$('#userTable').datagrid("reload");
						$('#userTable').datagrid('clearSelections');
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

//个人中心初始化密码
function initCenterPass(userId){
	if(userId!=null){
		$.messager.confirm('温馨提示', '确定要初始化用户密码吗？', function(r) {
			if (r) {
				$.post("/user/user/initPassword.jhtml",{"user.id":userId},function(result){
					if (result.success) {
						$.messager.show({ 
							title : '温馨提示',
							msg : '成功初始化用户密码为：123456，请注意及时更改！'
						});
					} else {
						$.messager.show({ 
							title : '温馨提示',
							msg : result.errorMsg
						});
					}
				});
			}
		});
	}else{
		$.messager.alert('温馨提示', '用户信息已过期，请重新登陆！', 'warning');
		return false;
	}
}

// 初始化职位名称
function initUserRole(val,row){
	if(row.roleid=='1'){
		return "系统管理员";
	}else if(row.roleid=='2'){
		return "店长";
	}else if(row.roleid=='3'){
		return "店员";
	}else if(row.roleid=='4'){
		return "店铺管理员";
	}
}