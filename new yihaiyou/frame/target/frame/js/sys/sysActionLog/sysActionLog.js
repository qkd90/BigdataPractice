
var SysActionLog={
		init:function(){
			SysActionLog.initDgList();
		},
		doSearch:function(){
			$("#dg").datagrid('load',{
				'opAccount':$("#log_account").val(),
				'opName':$("#log_name").val(),
				'actionTime':$("#log_time").val(),
			});
		},
		//初始化表格数据
		initDgList:function(){
			$("#dg").datagrid({
				title:'日志列表',
				height:580,
				url:'/sys/sysActionLog/search.jhtml',
				pagination:true,
				pageList:[20,30,50],
				rownumbers:true,
				fitColumns:true,
				singleSelect:true,
				striped:true,//斑马线
				columns: [[
		                   { field: 'opAccount', title: '操作者账号', width: 350, sortable: true },
		                   { field: 'opName', title: '操作者姓名', width: 350, sortable: true },
		                   { field: 'target', title: '操作对象', width: 350, sortable: true },
		                   { field: 'actionType', title: '操作类型', width: 350, sortable: true },
		                   { field: 'actionTime', title: '操作时间', width: 350, sortable: true },
		                   { field: 'actionContent', title: '操作内容', width: 350, sortable: true }	            ]],
			});
		},
	};
