

var Scemanage = {
		
		init:function(){
//			Scemanage.initKindEditor();
//			Scemanage.initProCommbox();
//			Scemanage.initCommbox();
			Scemanage.doSearch();
			Scemanage.initDgList();
			Scemanage.initCom();
		},
		
		// 打开新增窗口
		openAddForm : function() {
			
			Scemanage.doAdd();
		},
		
		initCom:function(){
			$("#city_name").combobox({
				onSelect : function(param) {
					var id = param.id;
					var name = param.name;
//					$("#sitename_id").val(name);
					$("#hidden_city").val(id);
					
				},
				onChange:function(){
//					alert("aa");
					var cityname = $("#city_name").combobox("getValue");
					if(cityname.length<1){
						$("#hidden_city").val("");
					}
				}
			});
			
			
			$("#sce_name")
			.combobox(
					{
						onSelect : function(param) {
							var id = param.id;
							var name = param.name;
							
//							$("#sitename_id").val(name);
							$("#hidden_scename").val(id);
							
						},
						onChange:function(){
//							alert("aa");
							var scename = $("#sce_name").combobox("getValue");
							if(scename.length<1){
								$("#hidden_scename").val("");
							}
						}
					});
			
		},
		
		// 添加
		doAdd: function() {
			var t = Math.random(); 	// 保证页面刷新
			var url = "/scemanager/scemanager/addScemanage.jhtml";
			var ifr = $("#edit_panel").children()[0];
			$(ifr).attr("src", url);
			$("#edit_panel").dialog({
				title : '新增景点帐号',
				modal : true,
				top : "50",
				left : "100",
				shadow:false
					
			});
			$("#edit_panel").dialog("open");
		},
		
		doSearch : function() {
			$("#search").click(function() {
				var isUsed = "";
				
				$.each($("input[name='status']"),function(i,perValue){
					if($(perValue).attr("checked")){
						$("#hidden_used").val($(perValue).val());
						return false;
					}
				});
				
				
				$("#dg").datagrid('load', {
					'sceId' : $("#hidden_scename").val(),
					'cityId' : $("#hidden_city").val(),
					'status':$("#hidden_used").val()
//					'remark' : $("#role_remark").val(),
				});
			});
		},

		lockStatus:function(){
			
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length < 1) {
				show_msg("请选择记录");
				return ;
			}
			
			var idsArray = [];
	    	for (var i = 0; i < rows.length; i++){
	        	var row = rows[i];
	        	if(row.status != "activity") {
					show_msg("请选择已发布的记录");
					return ;
	        	}
	        	idsArray.push(row.id);                     
	        } 
	        var ids = idsArray.join(',');
	        $.post('/scemanager/scemanager/lockStatus.jhtml', {
	        	'ids' : ids
	        }, function(result) {
	        	if (result.success) {
	        		$("#dg").datagrid("reload");
	        		show_msg("帐号冻结成功");
	        	} else {
	        		show_msg(result.errorMsg);
	        	}
	        });
		},
		
		editStatus:function(){
//			var rows = $('#'+tbId).datagrid('getSelections');
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length < 1) {
				show_msg("请选择记录");
				return ;
			}
			
			var idsArray = [];
	    	for (var i = 0; i < rows.length; i++){
	        	var row = rows[i];
	        	if(row.status != "lock") {
					show_msg("请选择未发布的记录");
					return ;
	        	}
	        	idsArray.push(row.id);                     
	        } 
	        var ids = idsArray.join(',');
	        $.post('/scemanager/scemanager/editStatus.jhtml', {
	        	'ids' : ids
			}, function(result) {
				if (result.success) {
					$("#dg").datagrid("reload");
					show_msg("帐号发布成功");
				} else {
					show_msg(result.errorMsg);
				}
			});	
		},
		
		editPassword:function(){
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length < 1) {
				show_msg("请选择要重置密码的景点帐号!");
				return ;
			}
			
	    	for (var i = 0; i < rows.length; i++){
	    		var row = rows[i];
				$.post('/scemanager/scemanager/editPassword.jhtml', {
					'userId' : row.id
				}, function(result) {
					if (result.success) {
						$("#dg").datagrid("reload");
						show_msg("重置密码成功");
					} else {
						show_msg(result.errorMsg);
					}
				});
			}
			
		},
		
		clearForm:function(){
			$('#searchform').form('clear');
			$("#dg").datagrid('load', {});
		},
		// 初始化表格数据
		initDgList : function() {
			$("#dg").datagrid({
				title : '景点帐号列表',
//				height : 422,
				url : '/scemanager/scemanager/dataList.jhtml',
				pagination : true,
				pageList : [ 10, 20, 30 ],
//				rownumbers : true,
				fitColumns : true,
				fit : true,
				singleSelect : false,
				striped : true,// 斑马线
				ctrlSelect : false,// 组合键选取多条数据：ctrl+鼠标左键
				columns : [ [ 
				             { 
				    field:'ck',
				    checkbox:true 
				    },
				              {
					field : 'sceName',
					title : '景点名称',
					width : 350,
					sortable : true
				}, {
					field : 'account',
					title : '帐号',
					width : 350,
					sortable : true
				}, {
					field : 'status',
					title : '是否发布',
					width : 350,
					sortable : true,
					codeType: 'userStatus', 
					formatter: ValidateUtil.codeFmt
				}, {
					field : 'city',
					title : '城市',
					width : 350,
					sortable : true
				}, { 
					field: 'createdTime', 
					title: '创建时间', 
					width: 350, 
					sortable: true
				}] ],
				toolbar : "#toolbar"
			});
		},
		
		
		
		
		
		cityLoader:function(param, success, error) {
			var q = param.q || '';
			if (q.length <= 1) {
				return false
			}
			$.ajax({
				url : '/scemanager/scemanager/getCityLoader.jhtml',
				dataType : 'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				method : 'POST',
				data : {
					featureClass : "P",
					style : "full",
					maxRows : 20,
					name_startsWith : q
				},
				success : function(data) {

					
					var items = $.map(data, function(item) {
						return {
							id : item.id,
							name : item.name
							
							
						};
					});
					success(items);
				},
				error : function() {
					error.apply(this, arguments);
				}
			});
		},
		
		searchSceLoader:function(param, success, error) {
			var q = param.q || '';
			if (q.length <= 1) {
				return false
			}
			$.ajax({
				url : '/scemanager/scemanager/getScenicList.jhtml',
				dataType : 'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				method : 'POST',
				data : {
					featureClass : "P",
					style : "full",
					maxRows : 20,
					name_startsWith : q
				},
				success : function(data) {

					
					var items = $.map(data, function(item) {
						return {
							id : item.id,
							name : item.name
							
						};
					});
					success(items);
				},
				error : function() {
					error.apply(this, arguments);
				}
			});
		},
		
		
		
		
		
		
		
		
		
		
};



$(function() {
//	SysSite.init();
	Scemanage.init();
	// 编辑框关闭时清除表单
	$("#edit_panel").dialog({
		onClose : function() {
			$("#ff").form("clear");
			$("#dg").datagrid("reload");
		}
	});
	
});

