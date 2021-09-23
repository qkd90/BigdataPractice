///////////////////会话管理/////////////////////////////////////
var SessionDatagrid;//会话列表
var Session={
	init:function(){
		var day = new Date().Format("yyyy-MM-dd");
		$("#startTime").datetimebox('setValue', day + ' 00:00:00');
		$("#endTime").datetimebox('setValue', day + ' 23:59:59');
		this.initDatagrid();
		$("#search").click(this.querySession);
	},
	initDatagrid:function(){//初始化datagrid
		SessionDatagrid=$("#Session").datagrid({
			url:'/session/session/search.jhtml',
			pagination : true,
			pagePosition : 'bottom',
			title:'会话列表',
			singleSelect:true,
			fit : true,
			rownumbers:true,
			fitColumns : false,
			nowrap : false,
			border : false,
			checkOnSelect:true,
			selectOnCheck:true,
			queryParams: {
				uid: $("#uid").val(),
				startPv: $("#startPv").val(),
				endPv: $("#endPv").val(),
				startTime: $("#startTime").datetimebox('getValue'),
				endTime: $("#endTime").datetimebox('getValue'),
				referer: $("#referer").val(),
				startCost: $("#startCost").val(),
				endCost: $("#endCost").val()
			},
			frozenColumns:[[{
								align:'center',
								title : '会话ID',
								field : 'SESSIONID',
								width : 150,
								sortable:true
							}
			             ]],    
			columns : [[{
				align:'center',
				title : '用户ID',
				field : 'USERID',
				width : 60
			},{
				align:'center',
				title : 'PV',
				field : 'PV',
				width : 60
			},{
				align:'center',
				title : '耗时',
				field : 'COSTTIME',
				width : 60
			},{
				align:'center',
				title : '访问时间',
				field : 'ACCESSTIME',
				width : 150
			},{
				align:'center',
				title : '退出时间',
				field : 'OUTTIME',
				width : 150
			},{
				align:'center',
				title : '耗时',
				field : 'COSTTIME',
				width : 60
			},{
				align:'center',
				title : '来源域名',
				field : 'REFERERDOMAIN',
				width : 150
			},{
				align:'center',
				title : '来源',
				field : 'REFERRER',
				width : 250
			},{
				align:'center',
				title : '浏览器',
				field : 'BROWSER',
				width : 60
			},{
				align:'center',
				title : '系统',
				field : 'SYSTEM',
				width : 80
			}]],
			onLoadSuccess:function(data){
				
			},
			onDblClickCell: function(index,field,value){
				var win = $("#sessionWindow");
				win.dialog({
					title:'动画窗口',
					fit:true,
					//top : $(document).scrollTop(),
					modal:true
				});
				win.dialog('open').dialog('refresh','/session/session/sessionwindow.jhtml');
			}
		});
	},
	//查询会话
	querySession:function(){
		SessionDatagrid.datagrid('loadData', { total: 0, rows: [] });
		SessionDatagrid.datagrid('load', {
			uid: $("#uid").val(),
			startPv: $("#startPv").val(),
			endPv: $("#endPv").val(),
			startTime: $("#startTime").datetimebox('getValue'),
			endTime: $("#endTime").datetimebox('getValue'),
			referer: $("#referer").val(),
			startCost: $("#startCost").val(),
			endCost: $("#endCost").val()
		});
	}
};

$(function(){
	Session.init();
});