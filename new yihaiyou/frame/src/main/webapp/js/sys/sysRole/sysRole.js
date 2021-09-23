
var SysRole={
	init:function(){
		SysRole.initDgList();
	},
	doSearch:function(){
		$("#dg").datagrid('load',{
			'name':$("#role_name").textbox("getValue"),
			'status':$("#role_status").combobox("getValue"),
			'remark':$("#role_remark").textbox("getValue")
		});
	},
	//初始化表格数据
	initDgList:function(){
		$("#dg").datagrid({
			title:'角色列表',
			fit:true,
			height:400,
			url:'/sys/SysRole/searchRole.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'name', title: '角色名称', width: 350, sortable: true },
	                   { field: 'status', title: '状态', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.status!=null&&row.status==1){
	                		   return "<font style='color:red'>冻结</font>";
	                	   }else{
	                		   return "<font style='color:green'>启用</font>";
	                	   }
	                   } },
	                   { field: 'seq', title: '排序号', width: 350, sortable: true },
	                   { field: 'remark', title: '角色描述', width: 350, sortable: true },
            ]],
	        toolbar : "#toolbar"
		});
	},
	//打开新增窗口
	openAddForm:function(){
		$("#edit_panel").dialog({
			title:'新增角色',
			modal:true,
			top:"20",
			left:"100"
		});
		$("#edit_panel").dialog("open");
	},
	//打开编辑窗口
	openEditForm:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要编辑的角色!");
		}else{
			$("#ff").form('load',row);
			$("#edit_panel").dialog({
				title:'编辑角色',
				modal:true,
				top:"20",
				left:"100",
			});
			$("#edit_panel").dialog("open");
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
			show_msg("请选择要编辑的行!");
		}else{
			$("#showform").form('load',row);
			$("#show_panel").dialog({
				title:'查看角色信息',
				modal:true,
				top:"20",
				left:"100",
			});
			$("#show_panel").dialog("open");
		}
	},
	//冻结角色
	forzenRole:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要冻结的角色!");
		}else{
			$.post('/sys/sysRole/forzenRole.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("角色冻结成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//解冻角色
	unForzenRole:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要解冻的角色!");
		}else{
			$.post('/sys/sysRole/unForzenRole.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("角色解冻成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//删除角色
	deleteRole:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要删除的角色!");
		}else{
			$.messager.confirm("", "删除后的角色不能复原!是否删除?", function(r){
				if(r){
					$.post('/sys/sysRole/deleteRole.jhtml',{'id':row.id},function(result){
						if(result.success){
							$("#dg").datagrid("reload");
							show_msg("角色删除成功");
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
			url : "/sys/sysRole/saveRole.jhtml",
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
					SysRole.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
				}else{
					show_msg(result.errorMsg);
				}
			}
		});
	},
	//打开角色授权
	openSetRight:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要授权的角色!");
		}else{
			setRightDialog=sy.dialog({
				title:'【'+row.name+'】角色授权(<font style="color:red">注:</font><font style="color:green">绿色为公开权限,不可取消</font>)',
				href : '/sys/sysRight/manage.jhtml?roleId='+row.id,
				width : 1100,
				height : 500,
				modal:true,
				onLoad:function(){
					SysRight.initMenuTree();
				}}
			);
		}
	}
};
$(function(){
	SysRole.init();
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