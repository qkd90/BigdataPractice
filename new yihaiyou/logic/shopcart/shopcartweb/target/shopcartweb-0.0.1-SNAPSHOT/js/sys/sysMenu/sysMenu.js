
var SysMenu={
	init:function(){
		//初始化模块树
		SysMenu.initMenuTree();
		//初始化表格数据
		SysMenu.initDgList();
	},
	doSearch:function(){
		$("#dg").datagrid('load',{
			'menuname':$("#m_menuname").textbox("getValue"),
			'status':$("#m_status").combobox("getValue"),
			'url':$("#m_url").textbox("getValue")
		});
		$("#tt").datagrid("clearSelections");
	},
	//初始化模块树
	initMenuTree:function(){
		$.ajax({  
	         type : "post",  
	          url : "/sys/sysMenu/loadMenuTree.jhtml",  
	          async : false,  
	          success : function(data){  
	        	  $('#tt').tree({
		  				data : data.menus,
		  				method : 'post',
		  				animate : true,
		  				onClick : function(node) {
		  					$("#eastPanel").panel({
		  						'title':node.text
		  					});
		  					$('#dg').datagrid('load', {
		  						'parentId' : node.id
		  					});
		  				}
		  			}); 
	          }  
	     }); 
	},
	//初始化表格数据
	initDgList:function(){
		$("#dg").datagrid({
			height:600,
			url:'/sys/SysMenu/searchMenu.jhtml',
			fit:true,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'menuname', title: '模块名称', width: 350, sortable: true },
	                   { field: 'parentId', title: '上级模块', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.parentId!=null){
	                		   var parentMenu=$("#tt").tree("find",row.parentId);
	                		   if(parentMenu!=null){
	                			   return parentMenu.text;
	                		   }
	                	   }
	                   } },
	                   { field: 'url', title: '链接', width: 350, sortable: true },
	                   //{ field: 'menuImg', title: '模块大图标', width: 350, sortable: true },
	                   { field: 'icon', title: '模块图标', width: 350, sortable: true },
	                   { field: 'menulevel', title: '模块等级', width: 350, sortable: true },
	                   { field: 'isPublic', title: '公共模块', width: 350, sortable: true,formatter:function(value,row,index){
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
	                		   return "<font style='color:green'>启用</font>";
	                	   }
	                   } },
	                   { field: 'seq', title: '排序号', width: 350, sortable: true },
                        { field: 'subSystemFlag', title: '子系统菜单', width: 350, sortable: true,formatter:function(value,row,index){
                            if (row.subSystemFlag) {
                                return "是";
                            }else{
                                return "否";
                            }
                        } }
	                   //{ field: 'remark', title: '模块描述', width: 350, sortable: true },
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
        $("input[name='subSystemFlag']:last").attr("checked", "checked");
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
			var parentMenu=$("#tt").tree("find",row.parentId);
			$("#parent_name").html(parentMenu.text);
			$("#parentId").val(parentMenu.id);
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
	forzenMenu:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要冻结的模块!");
		}else{
			$.post('/sys/sysMenu/forzenMenu.jhtml',{'menuid':row.menuid},function(result){
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
	unForzenMenu:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要解冻的模块!");
		}else{
			$.post('/sys/sysMenu/unForzenMenu.jhtml',{'menuid':row.menuid},function(result){
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
	publicMenu:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要公开的模块!");
		}else{
			$.post('/sys/sysMenu/publicMenu.jhtml',{'menuid':row.menuid},function(result){
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
	privateMenu:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要私有的模块!");
		}else{
			$.post('/sys/sysMenu/privateMenu.jhtml',{'menuid':row.menuid},function(result){
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
	deleteMenu:function(){
		//var row=$("#dg").datagrid("getSelections");//用于批量处理,待扩展
		var row=$("#dg").datagrid("getSelected");
		if(row==null||row.length==0){
			show_msg("请选择要删除的模块!");
		}else{
			//批量删除,待扩展
//			var menuids="";
//			for(var i=0;i<row.length;i++){
//				
//			}
			$.messager.confirm("", "删除后的模块不能复原!是否删除?", function(r){
				if(r){
					$.post('/sys/sysMenu/deleteMenu.jhtml',{'menuid':row.menuid},function(result){
						if(result.success){
							$("#dg").datagrid("reload");
							//初始化模块树
							//SysMenu.initMenuTree();
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
			url : "/sys/sysMenu/saveMenu.jhtml",
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
					SysMenu.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
					//初始化模块树
					//SysMenu.initMenuTree();
				}else{
					show_msg(result.errorMsg);
				}
			}
		});
	}
};
$(function(){
	SysMenu.init();
	//编辑框关闭时清除表单
	$("#edit_panel").dialog({
		onClose:function(){
			$("#ff").form("clear");
		}
	});
});