
var SysMsg={
	
		
	init:function(){
		$(SysMsg.initTgList());
	},
	doSearch:function(){
		$("#tg").datagrid('load',{
			'id':$("#id").val(),
			'sendtime':$("sendtime").val(),
			'receivernum':$("#receivernum").val(),
			'context':$("#context").val(),
		});
	},
	
	//初始化发送表数据
	initTgList:function(){
		$("#tg").datagrid({
			title:'短信列表',
			height:800,
			///msg/msg/manage.jhtml
			url:'/msghis/msghis/searchMsg.jhtml',
			pagination:true,
			pageList:[50,100,200],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
	                   { field: 'id', title: 'id', width: 200, sortable: true },
	                   { field: 'receivernum', title: '号码', width: 200, sortable: true },
	                   { field: 'sendtime', title: '发送时间',width: 200, sortable: true},
	                   { field: 'context', title: '文本内容', width: 800, sortable: true },
            ]],
	        toolbar : "#toolbar"
		});
	},
	
//	//初始化数据库号码信息
//	initdbList:function(){
//		$('#db').datagrid({
//			title:'号码列表',
//			height:200,
//			url:
//		});
//	},
	
	//打开编辑窗口
	openAddForm:function(){
		$("#edit_panel").dialog({
			title:'编辑信息',
			modal:true,
			top:"20",
			left:"100"
		});
		$("#edit_panel").dialog("open");
	},
	
//	//清除表单
//	clearForm:function(){
//		$("#tt").form("clear");
//	},
//	
//	//删除信息
//	deleteMsg:function(){
//		var row=$("#tg").datagrid("getSelected");
//		if(row==null){
//			show_msg("请选择要删除的信息!");
//		}else{
//			$.messager.confirm("", "删除后的信息不能复原!是否删除?", function(r){
//				if(r){
//					$.post('/msg/Msg/deleteMsg.jhtml',{'id':row.id},function(result){
//						if(result.success){
//							$("#dg").datagrid("reload");
//							show_msg("信息删除成功");
//						}else{
//							show_msg(result.errorMsg);
//						}
//					});
//				}
//			});
//		}
//	},
//	
//	//弹出数据库表----msgnum
//	upform:function(){
//		var row=$("#dg").datagrid("postForm");
//		if(row==null){
//			show_msg("请选择要发送的号码!");
//		}else{
//			$("#ff").form('load',row);
//			$("#up_panel").dialog({
//				title:'选择号码',
//				modal:true,
//				top:"30",
//				left:"80",
//			});
//			$("#up_panel").dialog("open");
//		}
//	},
//	
//	//提交表单
//	submitMsg:function(){
//		$.messager.progress({
//			title:'温馨提示',
//			text:'数据处理中,请耐心等待...'
//		});
//		$('#ff').form('submit', {
//			url : "/sys/sysRole/saveRole.jhtml",
//			onSubmit : function() {
//				if($(this).form('validate')==false){
//					$.messager.progress('close');
//				}
//				return $(this).form('validate');
//			},
//			success : function(result) {
//				$.messager.progress("close");
//				var result = eval('(' + result + ')');
//				if(result.success==true){
//					$("#edit_panel").dialog("close");
//					SysRole.clearForm();
//					show_msg("保存成功!");
//					$("#dg").datagrid("reload");
//				}else{
//					show_msg(result.errorMsg);
//				}
//			}
//		});
//	},
//	
//	//发送短信
//	sendingMsg:function(){
//		
//	},
};



$(function(){
	SysMsg.init();
//	//编辑框关闭时清除表单
//	$("#edit_panel").dialog({
//		onClose:function(){
//			$("#ff").form("clear");
//		}
//	});
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