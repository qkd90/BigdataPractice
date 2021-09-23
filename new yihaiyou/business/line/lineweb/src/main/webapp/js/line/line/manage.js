var Line={
	activeTabId: 'all',	// 默认第一个标签ID
	init:function(){
		//Line.initCompShow();
		//Line.initCompOutday();
		//Line.initCompHide();
		//Line.initCompCheckIng();
		//Line.initCompAll();
		//Line.initCompAgent();
		Line.initStatus();
        Line.initListener();
	},

	// 监听事件
	initListener : function() {
		$('#tabs').tabs({
		    onSelect: function(title, index){
		    	var tab = $('#tabs').tabs('getTab', index);
		    	Line.activeTabId = tab[0].id+'_dg';

				var url = '/line/line/search.jhtml';
				if (Line.activeTabId == "all_dg") {
					Line.initCompAll(url);
					//$('#'+tbId).datagrid("load");
				} else if (Line.activeTabId == "show_dg") {
					Line.initCompShow(url);
					//$('#'+tbId).datagrid("load");
				} else if (Line.activeTabId == "outday_dg") {
					Line.initCompOutday(url);
				} else if (Line.activeTabId == "agent_dg") {
					Line.initCompAgent(url);
				} else if (Line.activeTabId == "checkIng_dg") {
					Line.initCompCheckIng(url);
				} else if (Line.activeTabId == "hide_dg") {
					Line.initCompHide(url);
				}
		    }
		});
	},
	// 初始状态
	initStatus : function() {
		$('#tabs').tabs({selected:4});
		var url = '/line/line/search.jhtml';
		Line.initCompAll(url);
    	//Line.doSearch('all_dg');	// 初始化对应表格数据
	},
	// 表格分类名称显示
	fmtTbCategoryName : function(tbId, rowIndex, categoryId) {
		$.post("/line/line/findCategoryName.jhtml", {categoryId: categoryId},
			function(data){
				$('#'+tbId).datagrid('updateRow', {index:rowIndex, row:{categoryName:data.categoryName}});
			}
		);
	},
	// 打开编辑窗口
	openEditPanel: function(url) {
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		$("#editPanel").dialog("open");
	},
	// 关闭编辑窗口
	closeEditPanel: function(isRefresh) {
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", '');
		$("#editPanel").dialog("close");
		if (isRefresh && Line.activeTabId) {
			Line.doSearch(Line.activeTabId+'_dg');
		}
	},
	// 表格查询，第一次查询两次！！
	doSearch:function(tbId){
		$("#"+tbId+"").datagrid("load");
//		$('#'+tbId).datagrid({url:'/line/line/search.jhtml',pageNumber:1});
	},
	// 快速发布
	doQuickPub:function(){
		var url = FG_DOMAIN+"/mall/line/list.jhtml";
		window.open(url, '_blank');
	},
	// 添加
	doAdd:function(){
		var url = "/line/line/addWizard.jhtml";
//		window.location.href = url;
		Line.openEditPanel(url);
	},
	// 删除
	doDel: function(tbId) {
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
		$.messager.confirm('温馨提示', '您确认删除选中记录？', function(r){
			if (r) {
				$.messager.progress({
					title:'温馨提示',
					text:'数据处理中,请耐心等待...'
				});
				$.post("/line/line/delBatch.jhtml",
					{ids : ids},
					function(result) {
						$.messager.progress("close");
						if(result.success==true){
							show_msg("线路删除成功");
							Line.doSearch(tbId);
						}else{
							show_msg("线路删除失败");
						}
		    		}
				);
			}
		});
	},
	// 更新排序
	doUpdateOrder:function(tbId){
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idValueArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
    		var rowIndex = $('#'+tbId).datagrid('getRowIndex', row);
    		$('#'+tbId).datagrid('endEdit', rowIndex);
        	if (row.showOrder) {
        		idValueArray.push(row.id+':'+row.showOrder); 
        	} else {	// 避免0的情况
        		idValueArray.push(row.id+':'); 
        	}
        } 
        var idValues = idValueArray.join(',');
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/updateOrderBatch.jhtml",
			{idValues : idValues},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("更新排序成功");
					Line.doSearch(tbId);
				}else{
					show_msg("更新排序失败");
				}
    		}
		);
	},

	// 设置隐藏
	doSetHide:function(tbId){
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/hideBatch.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("线路隐藏成功");
					for (var j = 0; j < idsArray.length; j++) {
						LineUtil.buildLine(idsArray[j]);
					}
					Line.doSearch(tbId);

				}else{
					show_msg("线路隐藏失败");
				}
    		}
		);
	},
	// 设置显示  
	doSetShow:function(tbId){
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/showBatch.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("线路设置成功");
					Line.doSearch(tbId);
				}else{
					show_msg(result.errorMsg);
				}
    		}
		);
	},
	// 修改价格
	doUpdatePrice:function(productId){
		var url = "/line/line/editWizard.jhtml?productId="+productId;
//		window.location.href = url;
		Line.openEditPanel(url);
	},
	// 重发
	doRePub:function(tbId){
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/rePubBatch.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("线路重发成功");
					Line.doSearch(tbId);
				}else{
					show_msg("线路重发失败");
				}
    		}
		);
	},
	// 有购物有自费
	doShopCost:function(tbId){
		var rows = $('#'+tbId).datagrid('getSelections');
		if (rows.length < 1) {
			show_msg("请选择记录");
			return ;
		} 
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	if (row.agentFlag) {
        		show_msg("存在代理的线路记录，请选择非代理线路进行修改");
    			return ;
			}
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/buyPayBatch.jhtml",
			{ids : ids},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("线路设置成功");
					Line.doSearch(tbId);
				}else{
					show_msg("线路设置失败");
				}
    		}
		);
	},
	// 价格
	doPrice:function(productId){
		var url = "/line/line/editWizard.jhtml?productId="+productId+"&winIndex=2";
		//window.location.href = url;
		Line.openEditPanel(url);
	},
	// 修改
	doEdit:function(productId){
		var url = "/line/line/editWizard.jhtml?productId="+productId;
//		window.location.href = url;
		Line.openEditPanel(url);
	},
    // 组合产品
    doRelate:function(productId){
        var url = '/line/line/lineRelate.jhtml?productId=' + productId;
//		window.location.href = url;
        Line.openEditPanel(url);
    },
	// 标签
    doLabel:function(productId){
        LabelMgrDg.open('LINE', productId, Line.doGener);
        //$("#frmLabelDg").dialog("open");
	},
	// 推广
	doGener:function(){
		//alert('doGener');
	},
	// 复制
	doCopy:function(productId, tbId){
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/copyLine.jhtml",
			{productId : productId},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("线路复制成功");
					Line.doSearch(tbId);
				}else{
					show_msg("线路复制失败");
				}
    		}
		);
	},
	// 查看线路详情
	doView: function (productId, name, check) {
		var url = FG_DOMAIN + "/line_detail_" + productId + ".html";
		window.open(url, '_blank');
		//window.parent.addTab(name, '/line/line/lineView.jhtml?productId=' + productId + '&isCheck=' + check, '');//增加tab
	},

	/**
	 * 提交审核
	 * @param id
	 */
	doSubCheck: function(id, tbId) {
		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/subCheckLine.jhtml",
			{productId : id},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("提交审核成功");
					Line.doSearch("hide_dg");
				}else{
					show_msg("线路提交审核失败");
				}
			}
		);
	},

	doCheckLine: function(id) {

		$.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
		$.post("/line/line/checkLine.jhtml",
			{productId : id},
			function(result) {
				$.messager.progress("close");
				if(result.success==true){
					show_msg("审核成功");
					Line.doSearch("hide_dg");
				}else{
					show_msg("线路审核失败");
				}
			}
		);

	},

	// 初始化控件（正常线路）
	initCompShow:function(url){
		$('#show_qry_lineType').combobox({ 
		  	data:LineConstants.lineType, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#show_qry_productAttr').combobox({ 
		  	data:LineConstants.productAttr, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#show_qry_buypay').combobox({ 
		  	data:LineConstants.buypay, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#show_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			data: [],
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'ck', checkbox: true },
				{ field: 'productNo', title: '产品编号', width: '160px'},
				{ field: 'name', title: '线路名称', width: 320,
					formatter : function(value, rowData, rowIndex) {
						if (rowData.agentFlag) {
							return value+"[代理]";
						}
						return value;
					}},
				{ field: 'commentSum', title: '人气', width: 140},
				{ field: 'orderSum', title: '订单', width: 140 },
				{ field: 'categoryName', title: '分类', width: 180},
				{ field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
				{ field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
				{ field: 'showOrder', title: '排序', width: 140, editor:{type:'numberbox',options:{min:1,max:999}}, formatter:function(value,row){
					if (row.showOrder) {
						return row.showOrder;
					}
					return '';
				}},
				{ field: 'updateTime', title: '最后更新', width: 200},
				{ field: 'opt', title: '操作', width: 160, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
						//var btnPrice = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doPrice("+rowData.id+")'>价格</a>";
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doEdit("+rowData.id+")'>修改</a>";
						//var btnGener = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doGener("+rowData.id+")'>推广</a>";
						//var btnCopy = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doCopy("+rowData.id+",\"show_dg\")'>复制</a>";
						var btnLabel = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doLabel("+rowData.id+")'>标签</a>";
						var btnCombine = '';
						if (rowData.combineType == 'combine') {
							btnCombine = "&nbsp;&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doRelate("+rowData.id+")'>组合产品</a>";
						}
						var btn = btnView+"&nbsp;"+btnEdit+"&nbsp;"+btnLabel+btnCombine;
						return btn;
					}}
            ]],
            toolbar: '#show_tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.lineType = $("#show_qry_lineType").combobox("getValue");
		        data.productAttr = $("#show_qry_productAttr").combobox("getValue");
		        data.category = $("#show_qry_customType").combobox("getValue");
		        data.buypay = $("#show_qry_buypay").combobox("getValue");
		        data.keyword = $("#show_qry_keyword").textbox("getValue");
		        data.lineStatus = 'show';
			},
			onSelect : function(rowIndex, rowData) {
				//$("#show_dg").datagrid('beginEdit', rowIndex);
			},
			onUnselect : function(rowIndex, rowData) {
				//$("#show_dg").datagrid('endEdit', rowIndex);
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('show_dg', i, rows[i].category);
					}
				}
			}
		});
	},
	// 初始化控件（过期线路）
	initCompOutday:function(url){
		$('#outday_qry_lineType').combobox({ 
		  	data:LineConstants.lineType, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#outday_qry_productAttr').combobox({
		  	data:LineConstants.productAttr, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#outday_qry_buypay').combobox({ 
		  	data:LineConstants.buypay, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#outday_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'ck', checkbox: true },
				{ field: 'productNo', title: '产品编号', width: 160},
				{ field: 'name', title: '线路名称', width: 320,
					formatter : function(value, rowData, rowIndex) {
						if (rowData.agentFlag) {
							return value+"[代理]";
						}
						return value;
					}},
				{ field: 'commentSum', title: '人气', width: 140},
				{ field: 'orderSum', title: '订单', width: 140 },
				{ field: 'categoryName', title: '分类', width: 180 },
				{ field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
				{ field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
				{ field: 'showOrder', title: '排序', width: 140},
				{ field: 'updateTime', title: '最后更新', width: 200},
				{ field: 'opt', title: '操作', width: 180, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						//var btnPrice = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doPrice("+rowData.id+")'>价格</a>";
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doEdit("+rowData.id+")'>修改</a>";
						return btnView + "&nbsp;&nbsp;" +btnEdit;
					}}
			]],
            toolbar: '#outday_tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.lineType = $("#outday_qry_lineType").combobox("getValue");
		        data.productAttr = $("#outday_qry_productAttr").combobox("getValue");
		        data.category = $("#outday_qry_customType").combobox("getValue");
		        data.buypay = $("#outday_qry_buypay").combobox("getValue");
		        data.keyword = $("#outday_qry_keyword").textbox("getValue");
		        data.lineStatus = 'outday';
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('outday_dg', i, rows[i].category);
					}
				}
			}
		});
	},
	// 初始化控件（隐藏线路）
	initCompHide:function(url){
		$('#hide_qry_lineType').combobox({ 
		  	data:LineConstants.lineType, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#hide_qry_productAttr').combobox({
		  	data:LineConstants.productAttr, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#hide_qry_buypay').combobox({ 
		  	data:LineConstants.buypay, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#hide_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'ck', checkbox: true },
				{ field: 'productNo', title: '产品编号', width: 160},
				{ field: 'name', title: '线路名称', width: 320,
					formatter : function(value, rowData, rowIndex) {
						if (rowData.agentFlag) {
							return value+"[代理]";
						}
						return value;
					}},
				{ field: 'commentSum', title: '人气', width: 140},
				{ field: 'orderSum', title: '订单', width: 140 },
				{ field: 'categoryName', title: '分类', width: 180 },
				{ field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
				{ field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
				{ field: 'showOrder', title: '排序', width: 140},
				{ field: 'updateTime', title: '最后更新', width: 200},
				{ field: 'auditReason', title: '审核原因', width: 140},
				{ field: 'opt', title: '操作', width: 190, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doEdit("+rowData.id+")'>修改</a>";
						var btnSubCheck = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doSubCheck("+rowData.id+")'>提交审核</a>";
						return btnView + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" +btnSubCheck;
					}}
			]],
            toolbar: '#hide_tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.lineType = $("#hide_qry_lineType").combobox("getValue");
		        data.productAttr = $("#hide_qry_productAttr").combobox("getValue");
		        data.category = $("#hide_qry_customType").combobox("getValue");
		        data.buypay = $("#hide_qry_buypay").combobox("getValue");
		        data.keyword = $("#hide_qry_keyword").textbox("getValue");
		        data.lineStatus = 'hide';
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('hide_dg', i, rows[i].category);
					}
				}
			}
		});
	},


	// 初始化控件（隐藏线路）
	initCompCheckIng:function(url){
		$('#checkIng_qry_lineType').combobox({
			data:LineConstants.lineType,
			valueField:'id',
			textField:'text'
		});
		$('#checkIng_qry_productAttr').combobox({
			data:LineConstants.productAttr,
			valueField:'id',
			textField:'text'
		});
		$('#checkIng_qry_buypay').combobox({
			data:LineConstants.buypay,
			valueField:'id',
			textField:'text'
		});
		// 初始化表格数据
		$("#checkIng_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'ck', checkbox: true },
                { field: 'productNo', title: '产品编号', width: 160},
				{ field: 'name', title: '线路名称', width: 320,
					formatter : function(value, rowData, rowIndex) {
						if (rowData.agentFlag) {
							return value+"[代理]";
						}
						return value;
					}},
				{ field: 'commentSum', title: '人气', width: 140},
				{ field: 'orderSum', title: '订单', width: 140 },
				{ field: 'categoryName', title: '分类', width: 180 },
				{ field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
				{ field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
				{ field: 'showOrder', title: '排序', width: 140},
				{ field: 'updateTime', title: '最后更新', width: 200},
				{ field: 'opt', title: '操作', width: 120, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
						return btnView;
					}}
			]],
			toolbar: '#checkIng_tb',
			onBeforeLoad : function(data) {   // 查询参数
				data.lineType = $("#checkIng_qry_lineType").combobox("getValue");
				data.productAttr = $("#checkIng_qry_productAttr").combobox("getValue");
				data.category = $("#checkIng_qry_customType").combobox("getValue");
				data.buypay = $("#checkIng_qry_buypay").combobox("getValue");
				data.keyword = $("#checkIng_qry_keyword").textbox("getValue");
				data.lineStatus = 'checking';
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('checkIng_dg', i, rows[i].category);
					}
				}
			}
		});
	},

	// 初始化控件（线路仓库）
	initCompAll:function(url){
		$('#all_qry_lineType').combobox({ 
		  	data:LineConstants.lineType, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#all_qry_productAttr').combobox({
		  	data:LineConstants.productAttr, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#all_qry_buypay').combobox({ 
		  	data:LineConstants.buypay, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#all_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'productNo', title: '产品编号', width: 160},
                { field: 'name', title: '线路名称', width: 320,
                    formatter : function(value, rowData, rowIndex) {
                        if (rowData.agentFlag) {
                            return value+"[代理]";
                        }
                        return value;
                    }},
                { field: 'commentSum', title: '人气', width: 140},
                { field: 'orderSum', title: '订单', width: 140 },
                { field: 'categoryName', title: '分类', width: 140 },
                { field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
                { field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
                { field: 'showOrder', title: '排序', width: 140},
                { field: 'updateTime', title: '最后更新', width: 200},
                { field: 'lineStatus', title: '状态', width: 120, align: 'center', codeType: 'lineStatus', formatter: LineUtil.codeFmt},
                { field: 'auditReason', title: '审核原因', width: 140},
                { field: 'opt', title: '操作', width: 180, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        //var btnPrice = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doPrice("+rowData.id+")'>价格</a>";
                        var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doEdit("+rowData.id+")'>修改</a>";
                        var btnCombine = '';
                        if (rowData.combineType == 'combine') {
                            btnCombine = "&nbsp;&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doRelate("+rowData.id+")'>组合产品</a>";
                        }
                        return btnView + "&nbsp;&nbsp;" + btnEdit + btnCombine;
                    }}
            ]],
            toolbar: '#all_tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.lineType = $("#all_qry_lineType").combobox("getValue");
		        data.productAttr = $("#all_qry_productAttr").combobox("getValue");
		        data.category = $("#all_qry_customType").combobox("getValue");
		        data.buypay = $("#all_qry_buypay").combobox("getValue");
		        data.keyword = $("#all_qry_keyword").textbox("getValue");
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('all_dg', i, rows[i].category);
					}
				}
			}
		});
	},
	// 初始化控件（代理线路）
	initCompAgent:function(url){
		$('#agent_qry_lineType').combobox({ 
		  	data:LineConstants.lineType, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#agent_qry_productAttr').combobox({
		  	data:LineConstants.productAttr, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#agent_qry_buypay').combobox({ 
		  	data:LineConstants.buypay, 
		  	valueField:'id', 
		  	textField:'text' 
		});
		$('#agent_qry_lineStatus').combobox({ 
		  	data:LineConstants.getConstants('lineStatus',true), 
		  	valueField:'id', 
		  	textField:'text' 
		});
		// 初始化表格数据
		$("#agent_dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:url,
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'ck', checkbox: true },
				{ field: 'productNo', title: '产品编号', width: 160},
				{ field: 'name', title: '线路名称', width: 300},
				{ field: 'companyUnitName', title: '代理商', width: 240},
				{ field: 'commentSum', title: '人气', width: 140},
				{ field: 'orderSum', title: '订单', width: 140 },
				{ field: 'productAttr', title: '产品性质', width: 160, codeType: 'productAttr', formatter: LineUtil.codeFmt},
				{ field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
				{ field: 'showOrder', title: '排序', width: 140},
				{ field: 'updateTime', title: '最后更新', width: 200},
				{ field: 'lineStatus', title: '状态', width: 120, align: 'center', codeType: 'lineStatus', formatter: LineUtil.codeFmt},
				{ field: 'opt', title: '操作', width: 120, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btn = "";
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Line.doView("+rowData.id+",\""+rowData.siteurl+"\")'>详情</a>";
						if (rowData.lineStatus == 'show') {
							btn = btnView;
						}
						return btn;
					}}
			]],
            toolbar: '#agent_tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.lineType = $("#agent_qry_lineType").combobox("getValue");
		        data.productAttr = $("#agent_qry_productAttr").combobox("getValue");
		        data.lineStatus = $("#agent_qry_lineStatus").combobox("getValue");
		        data.buypay = $("#agent_qry_buypay").combobox("getValue");
		        data.keyword = $("#agent_qry_keyword").textbox("getValue");
		        data.agentLine = 'agent';	// 代理线路
			},
			onLoadSuccess: function(data) {
				if (data.rows) {
					var rows = data.rows;
					for (var i = 0; i < rows.length; i++) {
						Line.fmtTbCategoryName('agent_dg', i, rows[i].category);
					}
				}
			}
		});
	}
};

$(function(){
	Line.init();
	Qchanpin();
});

function Qchanpin(){
	$('.tabs-wrap .tabs').css({'height':'36px'});
	$('.tabs-tool').css({'height':'36px','line-height':'36px'});
}