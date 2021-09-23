var SysMsgHis={
	init:function(){
		SysMsgHis.initHgList();
	},
	doSearch:function(){
		$("#hg").datagrid('load',{
			'id':$("#id").val(),
			'sedntime':$("sendtime").val(),
			'receivenum':$("#receivenum").val(),
			'context':$("#context").val(),
		});
	},
	
	//初始化发送表数据
	initHgList:function(){
		$("#hg").datagrid({
			title:'短信列表',
			height:800,
			url:'/msghis/msghis/searchMsgHis.jhtml',
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
};


//$(function(){
//	SysMsgHis.init();
//	//编辑框关闭时清除表单
//	$("#edit_panel").dialog({
//		onClose:function(){
//			$("#tt").form("clear");
//		}
//	});
//});

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




$(function(){
	SysMsgHis.init();
//	//编辑框关闭时清除表单
//	$("#edit_panel").dialog({
//		onClose:function(){
//			$("#ff").form("clear");
//		}
//	});
});