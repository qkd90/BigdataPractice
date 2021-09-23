/**组织管理*/
var EditSysUser={
	init:function(){
//		SysUser.initDgList();
		EditSysUser.initUserTree();
		EditSysUser.initRoleSearch();
		EditSysUser.initCom();
	},
	
	initCom:function(){
		
		var userId = $("#hidden_userid").val();
		if(userId){
			$("#account").textbox('readonly',true);
		}
		$.post('/sys/sysRole/searchAllRole.jhtml',
				{'userId':userId},
				function(result){
					$('#roleIds').combogrid({ 
						value:result.roles,  
					    data:result.rows,   
					}
			);
		});
		
//		var unitId = $("#hidden_uintid").val();
//		if(unitId){
////			alert(unitId);
//			$('#unit_id').combotree('setValue', unitId);
//		}
	},
	//初始化角色选择
	initRoleSearch:function(){
		$('#roleIds').combogrid({    
		    panelWidth:330,    
		    value:'',    
		    idField:'id',    
		    textField:'name',    
		    panelHeight:150,
		    editable:false,
		    //url:'/sys/sysRole/searchAllRole.jhtml',    
		    columns:[[    
		        {field:'name',title:'角色名',width:100},    
		        {field:'remark',title:'角色描述',width:200},    
		    ]],
		});  
	},
	//初始组织架构
	initUserTree:function(){
		$.post('/sys/sysUnit/loadUnitTree.jhtml',{},function(result){
			//左边组织架构
			$('#tt').treegrid({    
			    data:result.rows,    
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
					
					var unitId = $("#hidden_uintid").val();
					if(unitId){
						$('#unit_id').combotree('setValue', [unitId]);
					}
			    }
			    /*onClickRow:function(row){
			    	$("#dg").datagrid('load',{
			    		'sysUnit.id':row.id
			    	});
			    }*/
			});
		});
	},
	//清除表单
	clearForm:function(){
		$("#ff").form("clear");
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
//					window.parent.$("#dg").datagrid("reload");
//					window.parent.$("#edit_panel").dialog("close");
					
					show_msg("保存成功!");
					window.parent.$("#dg").datagrid("reload");
					EditSysUser.clearForm();
					window.parent.$("#edit_panel").dialog("close");
					
				}else{
					show_msg(result.errorMsg);
				}
			}
		});
	},
};
$(function(){
	EditSysUser.init();
	//编辑框关闭时清除表单
	/*$("#edit_panel").dialog({
		onClose:function(){
			$("#ff").form("clear");
		}
	});*/
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