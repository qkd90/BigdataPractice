var Jbpm=$.fn.extend({},{
	//查询我的任务
	doSearch : function() {
		$('#dg2').datagrid('load', {
			'jbpmTask.executionId' : $('#executionId').val(),
			'jbpmTask.name' : $('#taskname').val(),
			'jbpmTask.activityName' : $('#activityName').val(),
			'jbpmTask.startTime' : $('#startTime').combobox("getValue"),
			'jbpmTask.endTime' : $('#endTime').combobox("getValue")
		});
	},
	//查询我的历史任务
	doHisSearch : function() {
		$('#historydg').datagrid('load', {
			'jbpmTask.executionId' : $('#h_executionId').val(),
			'jbpmTask.name' : $('#h_taskname').val(),
			'jbpmTask.activityName' : $('#h_activityName').val(),
			'jbpmTask.startTime' : $('#h_startTime').combobox("getValue"),
			'jbpmTask.endTime' : $('#h_endTime').combobox("getValue")
		});
	},
	pageInit:function(){
		//tab变换时显示查询结果
		$('#tt').tabs({
		    border:false,
		    onSelect:function(title){
		    	
		    }
		});
	}
});


