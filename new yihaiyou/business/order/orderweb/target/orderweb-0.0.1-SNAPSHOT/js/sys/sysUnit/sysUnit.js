/**组织管理*/
var SysUnit={
	init:function(){
		SysUnit.initDgList();
		SysUnit.initUnitTree();
	},
	doSearch:function(){
		$("#dg").datagrid('load',{
			'name':$("#role_name").textbox("getValue"),
			'status':$("#role_status").combobox("getValue"),
			'remark':$("#role_remark").textbox("getValue")/*,
			'unitNo':$("#u_unitNo").val()*/
		});
	},
	doClearSearch:function() {
		$('#searchform').form('clear');
		$("#dg").datagrid('load',{
			'name':$("#role_name").textbox("getValue"),
			'status':$("#role_status").combobox("getValue"),
			'remark':$("#role_remark").textbox("getValue")/*,
			 'unitNo':$("#u_unitNo").val()*/
		});
		$('#tt').treegrid('reload');

	},
	//初始化表格数据
	initDgList:function(){
		$("#dg").datagrid({
			title:"组织列表",
			fit:true,
			height:600,
			url:'/sys/SysUnit/searchUnit.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'name', title: '组织名称', width: '250px', sortable: true },
	                   //{ field: 'unitNo', title: '组织编码', width: 350, sortable: true },
	                   { field: 'parentName', title: '上级组织', width: '250px', sortable: true
	                	   /*,
	                	   formatter:function(value,row,index){
	                		   if(row.parentUnit!=null){
	                			   return row.parentUnit.name;
	                		   }
	                	   }*/
	                   },
	                   { field: 'siteName', title: '所属站点', width: '250px', sortable: true
	                	   /*,
	                	   formatter:function(value,row,index){
	                		   if(row.sysSite!=null){
	                			   return row.sysSite.sitename;
	                		   }
	                	   }*/
	                   },
	                   { field: 'status', title: '状态', width: '250px', sortable: true,
	                	   formatter:function(value,row,index){
	                	   if(row.status!=null&&row.status==1){
	                		   return "<font style='color:red'>冻结</font>";
	                	   }else{
	                		   return "<font style='color:green'>启用</font>";
	                	   }
	                   } },
	                   { field: 'seq', title: '排序号', width: '250px', sortable: true },
	                   { field: 'remark', title: '组织描述', width: '250px', sortable: true },
            ]],
	        toolbar : "#toolbar"
		});
	},
	//初始组织架构
	initUnitTree:function(){
		$('#tt').treegrid({
			title:'组织架构',
		    url:'/sys/sysUnit/loadUnitTree.jhtml',    
		    idField:'id',    
		    treeField:'name',
		    checkbox:true,
		    showHeader:false,
		    fit:true,
			tools: [{
				iconCls:'icon-reload',
				handler:function(){
					$('#tt').treegrid('reload');
				}
			}] ,
		    columns:[[
		        {title:'组织名称',field:'name',width:220},  
		    ]],
		    onLoadSuccess:function(){
		    	//TODO
		    },
		    onClickRow:function(row){
		    	$("#dg").datagrid('load',{
		    		'parentUnit.id':row.id
		    	});
		    }
		}); 
	},
	//打开新增窗口
	openAddForm:function(){
		var row=$("#tt").treegrid("getSelected");
		if(row==null||row.length==0){
			show_msg("请选择上级组织!");
		}else{
			/*$.post('/sys/sysUnit/toAddUnit.jhtml',{'id':row.id},function(result){
				$("#unitNo").textbox('setValue',result.maxno);
			});*/
			$("#parent_name").html(row.name);
			$("#parent_id").val(row.id);
			$("#companyUnitId").val(row.companyUnit.id);
			if(row.sysSite!=null){
				$("#site_id").val(row.sysSite.id);
				$("#site_name").html(row.sysSite.sitename);
			}
			$("#edit_panel").dialog({
				title:'新增组织',
				modal:true,
				top:"20",
				left:"100"
			});
			$("#edit_panel").dialog("open");
		}
	},
	//打开编辑窗口
	openEditForm:function(){
		var row=$("#dg").datagrid("getSelected");
		var rowTree =$("#tt").treegrid("getSelected");
		
		$.post('/sys/sysUnit/getUnitByIdStr.jhtml',{'idStr':row.id},function(result){
			if(result.success){
//				$("#dg").datagrid("reload");
//				show_msg("组织冻结成功");
				if(row==null){
					show_msg("请选择要编辑的组织!");
				}else{
//					$("#ff").form('load',row);
					
					if(result.parUnit!=null){
						$("#parent_name").html(result.parUnit.name);
						$("#parent_id").val(result.parUnit.id);
					}
					if(result.site!=null){
						$("#site_name").html(result.site.sitename);
						$("#site_id").val(result.site.id);
					}
					if(result.comUnit!=null){
						$("#companyUnitId").val(result.comUnit.id);
					}
					
//					IF(RESULT.COMUNIT!=NULL){
//						$("#COMPANYUNITID").VAL(RESULT.COMPANYUNITID);
//					}
					$("#ff").form('load',result.unit);
					$("#edit_panel").dialog({
						title:'编辑组织',
						modal:true,
						top:"20",
						left:"100",
					});
					$("#edit_panel").dialog("open");
				}
				
				
				
			}else{
				show_msg(result.errorMsg);
			}
		});
		
		
		
		
	},
	//清除表单
	clearForm:function(){
		$("#ff").form("clear");
	},
	//显示详情
	showDetail:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要查看的组织!");
		}else{
			if(row.parentUnit!=null){
				$("#s_parent_name").html(row.parentUnit.name);
			}
			if(row.sysSite!=null){
				$("#s_site_name").html(row.sysSite.sitename);
			}
			$("#showform").form('load',row);
			$("#show_panel").dialog({
				title:'查看组织信息',
				modal:true,
				top:"20",
				left:"100",
			});
			$("#show_panel").dialog("open");
		}
	},
	//冻结组织
	forzenUnit:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要冻结的组织!");
		}else{
			$.post('/sys/sysUnit/forzenUnit.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("组织冻结成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//解冻组织
	unForzenUnit:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要解冻的组织!");
		}else{
			$.post('/sys/sysUnit/unForzenUnit.jhtml',{'id':row.id},function(result){
				if(result.success){
					$("#dg").datagrid("reload");
					show_msg("组织解冻成功");
				}else{
					show_msg(result.errorMsg);
				}
			});
		}
	},
	//删除组织
	deleteUnit:function(){
		var row=$("#dg").datagrid("getSelected");
		if(row==null){
			show_msg("请选择要删除的组织!");
		}else{
			$.messager.confirm("", "删除后的组织不能复原!是否删除?", function(r){
				if(r){
					$.post('/sys/sysUnit/deleteUnit.jhtml',{'id':row.id},function(result){
						if(result.success){
							$("#dg").datagrid("reload");
							show_msg("组织删除成功");
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
			url : "/sys/sysUnit/saveUnit.jhtml",
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
					SysUnit.clearForm();
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
	SysUnit.init();
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