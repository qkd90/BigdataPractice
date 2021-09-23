var sy = $.extend({}, sy);// 定义全局对象，类似于命名空间或包的作用 
var selectMenuDialog;
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

var SysResource={
	init:function(){
		//初始化模块树
		SysResource.initMenuTree();
		//初始化表格数据
		SysResource.initDgList();
		//选择框点击事件
//		$("#menuname").click(function(){
//			SysResource.openSelectMenu();
//		});
	},
	doSearch:function(){
		$("#dg").datagrid('load',{
			'name':$("#r_name").val(),
			'resourceUrl':$("#r_resourceUrl").textbox("getValue"),
			'resourceNo':$("#r_resourceNo").textbox("getValue"),
			'status':$("#r_status").combobox("getValue"),
			'isPublic':$("#r_isPublic").combobox("getValue")
		});
	},
	//打开模块选择框
	openSelectMenu:function(){
		selectMenuDialog=sy.dialog({
			title:'模块选择',
			href : '/sys/sysResource/menuSelect.jhtml',
			width : 1000,
			height : 550,
			top : 10,
			modal:true,
			onLoad:function(){
				//初始化模块树
				SysMenu.initMenuTree();
				//初始化表格数据
				SysMenu.initDgList();
			}}
		);
	},
	//初始化模块树
	initMenuTree:function(){
		$.post('/sys/sysMenu/loadMenuTree.jhtml',{},function(data){
			$('#tt').tree({
				data : data.menus,
				method : 'post',
				animate : true,
				onClick : function(node) {
					$("#eastPanel").panel({
						'title':node.text
					});
					$('#dg').datagrid('load', {
						'sysMenu.menuid' : node.id
					});
				}
			});
		});
	}
	,
	//初始化表格数据
	initDgList:function(){
		$("#dg").datagrid({
			height:600,
			fit:true,
			url:'/sys/sysResource/searchResource.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'name', title: '资源名称', width: 350, sortable: true },
	                   { field: 'sysMenu', title: '所属模块', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.sysMenu!=null){
	                		   return row.sysMenu.menuname;
	                	   }
	                   } },
	                   { field: 'resourceNo', title: '资源编码', width: 350, sortable: true },
	                   { field: 'resourceUrl', title: '资源链接', width: 350, sortable: true },
	                   //{ field: 'menuImg', title: '模块大图标', width: 350, sortable: true },
	                   { field: 'isPublic', title: '是否公开', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.isPublic!=null&&row.isPublic==1){
	                		   return "<font style='color:blue'>公共</font>";
	                	   }else{
	                		   return "<font style='color:brown'>私有</font>";
	                	   }
	                   } },
	                   { field: 'status', title: '状态', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.status!=null&&row.status==1){
	                		   return "<font style='color:red'>冻结</font>";
	                	   }else{
	                		   return "<font style='color:green'>激活</font>";
	                	   }
	                   } },
	                   { field: 'seq', title: '排序号', width: 350, sortable: true },
	                   { field: 'remark', title: '模块描述', width: 350, sortable: true },
            ]],
	        toolbar : "#toolbar"
		});
	},
	//打开新增窗口
	openAddForm:function(){
		var parentMenu=$("#tt").tree("getSelected");
		if(parentMenu==null){
			parentMenu=$("#tt").tree("find",0);
		}
		$("#parent_name").html(parentMenu.text);
		$("#parentId").val(parentMenu.id);
		$("#edit_panel").dialog({
			title:'新增模块',
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
			show_msg("请选择要编辑的模块!");
		}else{
			$("#menuid").val(row.sysMenu.menuid);
			$("#menuname").textbox("setValue",row.sysMenu.menuname);
			$("#ff").form('load',row);
			$("#edit_panel").dialog({
				title:'编辑模块',
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
			var parentMenu=$("#tt").tree("find",row.parentId);
			$("#show_parent_name").html(parentMenu.text);
			$("#showform").form('load',row);
			$("#show_panel").dialog({
				title:'查看模块信息',
				modal:true,
				top:"20",
				left:"100",
			});
			$("#show_panel").dialog("open");
		}
	},
	//冻结模块
	forzenResource:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要冻结的模块!");
		}else{
			$.post('/sys/sysResource/forzenResource.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("模块冻结成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//解冻模块
	unForzenResource:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要解冻的模块!");
		}else{
			$.post('/sys/sysResource/unForzenResource.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("模块解冻成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//公开模块
	publicResource:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要公开的模块!");
		}else{
			$.post('/sys/sysResource/publicResource.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("模块公开成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//私有模块
	privateResource:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要私有的模块!");
		}else{
			$.post('/sys/sysResource/privateResource.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("模块私有成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//删除模块
	deleteResource:function(){
		//var row=$("#dg").datagrid("getSelections");//用于批量处理,待扩展
		var row=$("#dg").datagrid("getSelected");
		if(row==null||row.length==0){
			show_msg("请选择要删除的模块!");
		}else{
			//批量删除,待扩展
//			var Resourceids="";
//			for(var i=0;i<row.length;i++){
//				
//			}
			$.messager.confirm("", "删除后的模块不能复原!是否删除?", function(r){
				if(r){
					$.post('/sys/sysResource/deleteResource.jhtml',{'id':row.id},function(result){
						if(result.success){
							$("#dg").datagrid("reload");
							show_msg("模块删除成功");
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
			url : "/sys/sysResource/saveResource.jhtml",
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
					SysResource.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
				}else{
					show_msg(result.errorMsg);
				}
			}
		});
	}
};
$(function(){
	SysResource.init();
	//编辑框关闭时清除表单
	$("#edit_panel").dialog({
		onClose:function(){
			$("#ff").form("clear");
		}
	});
});