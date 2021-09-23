/**组织管理*/
var SysUser={
	init:function(){
		SysUser.initDgList();
		SysUser.initUserTree();
		SysUser.initRoleSearch();
	},
	doSearch:function(){
		$("#dg").datagrid('load',{
			'account':$("#user_account").textbox("getValue"),
			'userName':$("#user_userName").textbox("getValue"),
			'mobile':$("#user_phone").textbox("getValue"),
			'email':$("#user_email").textbox("getValue"),
			'address':$("#user_address").textbox("getValue"),
			'gender':$("#role_status").combobox("getValue"),
			'sysUnit.id':$("#hidden_searchUnitId").val()
		});
	},
	//初始化角色选择
	initRoleSearch:function(){
		$('#roleIds').combogrid({    
		    panelWidth:330,    
		    value:'',    
		    idField:'id',    
		    textField:'name',    
		    editable:false,
		    //url:'/sys/sysRole/searchAllRole.jhtml',    
		    columns:[[    
		        {field:'name',title:'角色名',width:100},    
		        {field:'remark',title:'角色描述',width:200},    
		    ]]
		});  
	},
	//初始化表格数据
	initDgList:function(){
		$("#dg").datagrid({
			url:'/sys/sysUser/searchUser.jhtml',
			fit:true,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:false,
			singleSelect:true,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'account', title: '帐号', width: 150, sortable: true },
	                   { field: 'userName', title: '姓名', width: 150, sortable: true },
	                   { field: 'roles', title: '角色', width: 200, sortable: true },
	                   { field: 'sysUnit', title: '组织部门', width: 150, sortable: true ,formatter:function(value,row,index){
	 			          if(row.sysUnit!=null){
	 			        	  	return row.sysUnit.name;
							}
				      }},
	                   { field: 'gender', title: '性别', width: 100, sortable: true },
	                   { field: 'loginNum', title: '登录次数', width: 100, sortable: true },
	                   { field: 'isUse', title: '冻结或启用', width: 100, sortable: true,resizable:true,formatter:function(value,row,index){
	 			          if(value == "1"){
	 			        	  	return "<font color = 'red'>已激活</font>";
							}else if(value == "0"||value==null){
								return "<font color = 'green'>未激活</font>";
							}
				       }},
	                   { field: 'mobile', title: '手机', width: 150, sortable: true },
	                   { field: 'email', title: '邮箱', width: 200, sortable: true },
	                   { field: 'address', title: '地址', width: 300, sortable: true },
            ]],
	        toolbar : '#toolbar'
		});
	},
	//初始组织架构
	initUserTree:function(){
		$.post('/sys/sysUnit/loadUnitTree.jhtml',{},function(result){
			//左边组织架构
			$('#tt').treegrid({    
			    data:result.rows,
				fit:true,
			    idField:'id',    
			    treeField:'name',
			    checkbox:true,
			    showHeader:false,
			    fit:true,
			    toolbar:'#rightBar',
			    columns:[[
			        {title:'组织名称',field:'name',width:220},  
			    ]],
			    onLoadSuccess:function(){
			    	//TODO
			    	//初始化组织选择
					$('#unit_id').combotree({    
						data:result.rows,    
						required: true,
						editable:false
					});  
			    },
			    onClickRow:function(row){
			    	$("#dg").datagrid('load',{
			    		'sysUnit.id':row.id
			    	});
			    	$("#hidden_searchUnitId").val(row.id);
			    }
			});
		});
	},
	//打开新增窗口
	openAddForm:function(){
		
		var t = Math.random(); 	//保证页面刷新
		var url = "/sys/sysUser/editSysUser.jhtml?Uid=#loctop";
		var ifr = $("#edit_panel").children()[0];
		$(ifr).attr("src", url);
		$("#edit_panel").dialog({
			title:'新增用户,新用户默认密码为【123456】',
			modal:true,
			top:"20",
			height:"420",
			left:"100"
		});
		$("#edit_panel").dialog("open");
		
		
		
		
	},
	//打开编辑窗口
	openEditForm:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要编辑的用户!");
		}else{
			$("#account").textbox({"readonly":true});
			$("#ff").form('load',row);
			var t = Math.random(); 	//保证页面刷新
			var url = "/sys/sysUser/editSysUser.jhtml?Uid="+row.id+'#loctop';
			var ifr = $("#edit_panel").children()[0];
			$(ifr).attr("src", url);
			$("#edit_panel").dialog({
				title:'编辑用户',
				modal:true,
				height:"420",
				top:"20",
				left:"100"
			});
			$("#edit_panel").dialog("open");
		}
	},

	//重置密码
	resetPassword: function() {
		var rows = $("#dg").datagrid("getSelections");
		if (rows.length < 1) {
			show_msg("请选择要重置密码的公司帐号!");
			return ;
		}
		for (var i = 0; i < rows.length; i++){
			var row = rows[i];
			$.post('/sys/sysUnit/resetPassword.jhtml', {
				'userId' : row.id,
				'type':'user'
			}, function(result) {
				if (result.success) {
					$("#dg").datagrid("reload");
					show_msg("重置密码成功");
				} else {
					show_msg(result.errorMsg);
				}
			});
		}

	},

	//清除表单
	clearForm:function(){
		$("#ff").form("clear");
	},
	//显示详情
	showDetail:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要查看的用户!");
		}else{
			$("#showform").form('load',row);
			$.post('/sys/sysRole/searchAllRole.jhtml',{'userId':row.id},function(result){
				$('#s_roleIds').combogrid({ 
					panelWidth:330,    
				    value:'',    
				    idField:'id',    
				    textField:'name',    
				    editable:false,
				    value:result.roles,  
				    data:result.rows,     
				    columns:[[    
				        {field:'name',title:'角色名',width:100},    
				        {field:'remark',title:'角色描述',width:200},    
				    ]],
				});
			});
			if(row.sysUnit!=null){
				$('#sysUnit_name').html(row.sysUnit.name);
			}
			$("#show_panel").dialog({
				title:'查看用户信息',
				modal:true,
				top:"20",
				height:"420",
				left:"100"
			});
			$("#show_panel").dialog("open");
		}
	},
	//冻结用户
	forzenUser:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要冻结的用户!");
		}else{
			$.post('/sys/sysUser/forzenUser.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("用户冻结成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//解冻用户
	unForzenUser:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要解冻的用户!");
		}else{
			$.post('/sys/sysUser/unForzenUser.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("用户解冻成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//删除用户
	deleteUser:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要删除的用户!");
		}else{
			$.messager.confirm("", "删除后的用户不能复原!是否删除?", function(r){
				if(r){
					$.post('/sys/sysUser/deleteUser.jhtml',{'id':row.id},function(result){
						if(result.success){
							$("#dg").datagrid("reload");
							show_msg("用户删除成功");
						}else{
							show_msg(result.errorMsg);
						}
					});
				}
			});
		}
	},
	//提交表单
	submitForm:function(){
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$('#ff').form('submit', {
			url : "/sys/sysUser/saveUser.jhtml",
			onSubmit : function() {
				if($(this).form('validate')==false){
					$.messager.progress('close');
				}
				return $(this).form('validate');
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					$("#edit_panel").dialog("close");
					SysUser.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
				}else{
					show_msg(result.errorMsg);
				}
			}
		});
	},
//	
//	openSetRight:function(){
//		var row=$("#dg").datagrid("getSelected");
//		if(row==null){
//			show_msg("请选择要授权的组织!");
//		}else{
//			setRightDialog=sy.dialog({
//				title:'【'+row.name+'】组织授权(<font style="color:red">注:</font><font style="color:green">绿色为公开权限,不可取消</font>)',
//				href : '/sys/sysRight/manage.jhtml?roleId='+row.id,
//				width : 1000,
//				height : 500,
//				modal:true,
//				onLoad:function(){
//					SysRight.initMenuTree();
//				}}
//			);
//		}
//	}
};
$(function(){
	SysUser.init();
	//编辑框关闭时清除表单
	$("#edit_panel").dialog({
		onClose:function(){
			$("#ff").form("clear");
		}
	});
});

var sy = $.extend({}, sy);// 定义全局对象，类似于命名空间或包的作用 
var setRightDialog;
/********自定义dialog**************/
sy.dialog = function(options) {
	var opts = $.extend({
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	return $('<div/>').dialog(opts);
};