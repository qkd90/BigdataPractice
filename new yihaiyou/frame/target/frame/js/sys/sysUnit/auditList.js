var auditList={
	init:function(){
		auditList.initComp();
		auditList.initStatus();
	},
	// 初始状态
	initStatus : function() {
		
	},
	// 表格查询
	doSearch:function(){
		$('#dg').datagrid('load', {});
	},
	// 查看
	doView:function(unitId){
		//parent.window.open(FG_DOMAIN, '_blank');
		var t = Math.random(); 	// 保证页面刷新
		var url = "/sys/sysUnit/viewSupplier.jhtml?t="+t+'&unitId='+unitId+'#loctop';
		var ifr = $("#viewPanel").children()[0];
		$(ifr).attr("src", url);
		$("#viewPanel").dialog("open");
	},
	// 添加
	doAdd: function() {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/sys/sysUnit/editSupplier.jhtml?t="+t+'#loctop';
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		$("#editPanel").dialog("open");
	},
	// 编辑
	doEdit: function(unitId) {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/sys/sysUnit/editSupplier.jhtml?t="+t+'&unitId='+unitId+'#loctop';
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		$("#editPanel").dialog("open");
	},
	// 关闭编辑窗口
	closeEditPanel: function(isRefresh) {
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", '');
		$("#editPanel").dialog("close");
		if (isRefresh) {
			auditList.doSearch();
		}
	},
	// 关闭编辑窗口
	closeViewPanel: function(isRefresh) {
		var ifr = $("#viewPanel").children()[0];
		$(ifr).attr("src", '');
		$("#viewPanel").dialog("close");
	},
	// 批量审核
	doBatchAudit: function() {
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	if (row.status != -1) {		// 状态：-1待审核；0通过；1不通过；
    			show_msg("请选择状态为【待审核】的记录");
    			return ;
        	}
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
        auditList.doAudit(ids);
	},
	// 审核
	doAudit: function(ids) {
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/sys/sysUnit/doBatchAudit.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("审核成功");
					auditList.doSearch();
				}else{
					show_msg("审核失败");
				}
    		}
		);
	},
	// 打开冻结窗口
	doOpenReasonDg: function(unitId, reason) {
		if (unitId) {
			$("#unitId").val(unitId);
			$("#reason").textbox("setValue", reason);
		} else {
			var rows = $('#dg').datagrid('getSelections');
			if (rows.length < 1) {
				show_msg("请选择记录");
				return ;
			} 
	    	for (var i = 0; i < rows.length; i++){
	        	var row = rows[i];
	        	if (row.status === -1) {		// 状态：-1待审核；0通过；1不通过；
	    			show_msg("请选择状态为【待审核】的记录");
	    			return ;
	        	}
	        } 
	    	
			$("#unitId").val('');
			$("#reason").textbox("setValue", '');
		}
		$('#reasonDg').dialog('open');
	},

	doBatchActivate: function() {
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		}
		var idsArray = [];
		for (var i = 0; i < rows.length; i++){
			var row = rows[i];
			if (row.status === -1 || row.status === 0) {		// 状态：-1待审核；0通过；1不通过；
				show_msg("请选择状态为【已冻结状态】的记录");
				return ;
			}
			idsArray.push(row.id);
		}
		var ids = idsArray.join(',');
		auditList.doActivate(ids);
	},

	// 冻结
	doActivate: function(ids) {
		var reason = $("#reason").textbox("getValue");
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/sys/sysUnit/doBatchActivate.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				$('#reasonDg').dialog('close');
				$("#unitId").val("setValue", '');
				if(result.success==true){
					show_msg("操作成功");
					auditList.doSearch();
				}else{
					show_msg("操作失败");
				}
			}
		);
	},

	// 批量冻结
	doBatchFreeze: function() {

		var rows = $('#dg').datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		}
		var idsArray = [];
		for (var i = 0; i < rows.length; i++){
			var row = rows[i];
			if (row.status === 1) {		// 状态：-1待审核；0通过；1不通过；
				show_msg("请选择状态为【非冻结状态】的记录");
				return ;
			}
			idsArray.push(row.id);
		}
		var ids = idsArray.join(',');
		auditList.doFreeze(ids);
		//}
	},



	// 冻结
	doFreeze: function(ids) {
		var reason = $("#reason").textbox("getValue");
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/sys/sysUnit/doBatchFreeze.jhtml",
			{ids : ids, reason : reason},
			function(result) {
				$.messager.progress("close");
				$('#reasonDg').dialog('close');
				$("#unitId").val("setValue", '');
				if(result.success==true){
					show_msg("操作成功");
					auditList.doSearch();
				}else{
					show_msg("操作失败");
				}
    		}
		);
	},
	// 批量删除
	doBatchDel: function() {
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	/*if (row.status !== 1) {	// 1已冻结
    			show_msg("请选择状态为【已冻结】的记录");
    			return ;
        	}*/
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
        auditList.doDels(ids);
	},



	// 批量删除
	doDels: function(ids) {
		$.messager.confirm('温馨提示', '您确认删除选中记录？', function(r){
			if (r) {
				$.messager.progress({
					title:'温馨提示',
					text:'数据处理中,请耐心等待...'
				});
				$.post("/sys/sysUnit/doBatchDel.jhtml",
					{ids : ids},
					function(result) {
						$.messager.progress("close");
						if(result.success==true){
							show_msg("删除成功");
							auditList.doSearch();
						}else{
							show_msg("删除失败");
						}
		    		}
				);
			}
		});
	},

	//重置密码
	resetPassword: function() {
		var rows = $("#dg").datagrid("getSelections");
		if (rows.length < 1) {
			show_msg("请选择要重置密码的公司帐号!");
			return ;
		}
		for (var i = 0; i < rows.length; i++){
			var row = rows[i];
			$.post('/sys/sysUnit/resetPassword.jhtml', {
				'unitId' : row.id,
				'type':'unit'
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

	// 初始化控件
	initComp:function(){
		$('#qry_status').combobox({ 
		  	data:FxConstants.status, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		//$('#qry_status').combobox('setValue', 1);	// 默认选中：1-已冻结
		$('#qry_supplierType').combobox({ 
		  	data:FxConstants.supplierType,
			prompt:'供应商类型',
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:'/sys/sysUnit/searchAuditList.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
			   { field: 'ck', checkbox: true },
               { field: 'name', title: '商户名称', width: 260, sortable: true },
               { field: 'status', title: '状态', width: 130, sortable: true, codeType: 'status', formatter: FxUtil.codeFmt},
               { field: 'sysUnitDetail.supplierType', title: '商户类型', width: 140, sortable: true, codeType: 'supplierType', formatter: FxUtil.codeFmt},
				{ field: 'sysUnitDetail.legalPerson', title: '企业法人', width: 140, sortable: true },
				{ field: 'sysUnitDetail.mobile', title: '联系电话', width: 120, sortable: true },
               //{ field: 'sysUnitDetail.brandName', title: '品牌名', width: 250, sortable: true},
               { field: 'createTime', title: '入驻时间', width: 200, sortable: true},
               //{ field: 'sysUnitDetail.inivitorName', title: '邀请人', width: 250, sortable: true},
               { field: 'reason', title: '审核原因', width: 180, sortable: true},
               { field: 'opt', title: '操作', width: 180, align: 'center',
				formatter : function(value, rowData, rowIndex) {
					var btn = "";
					var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doView("+rowData.id+")'>查看</a>";
					var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doEdit("+rowData.id+")'>修改</a>";
					var btnAudit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doAudit("+rowData.id+")'>通过</a>";
					var btnFreeze = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doOpenReasonDg("+rowData.id+",\""+rowData.reason+"\")'>不通过</a>";
					var btnDel = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doDels("+rowData.id+")'>删除</a>";
					btn = btnView+"&nbsp;"+btnEdit+"&nbsp;"+btnDel;
					if (rowData.status == -1) {	// 状态：-1待审核；0通过；1不通过；
						btn = btnAudit+"&nbsp;"+btnFreeze+"&nbsp;"+btn;
					}
					return btn;
				}}
            ]],
            toolbar: '#tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.status = $("#qry_status").combobox("getValue");
		        data.supplierType = $("#qry_supplierType").combobox("getValue");
		        data.keyword = $("#qry_keyword").textbox("getValue");
			}
		});
	}
};

$(function(){
	auditList.init();
});

