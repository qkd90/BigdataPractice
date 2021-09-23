var SysSite = {
	init : function() {
		SysSite.initDgList();
		SysSite.doSearch();
	},
	doSearch : function() {
		$("#search").click(function() {
			$("#dg").datagrid('load', {
				'name' : $("#role_name").textbox("getValue"),
				'status' : $("#role_status").combobox("getValue"),
				'remark' : $("#role_remark").textbox("getValue")
			});
		});
	},
	
	// 初始化表格数据
	initDgList : function() {
		$("#dg").datagrid({
			title : '分站列表',
			height : 400,
			fit:true,
			url : '/sys/sysSite/searchSite.jhtml',
			pagination : true,
			pageList : [ 20, 30, 50 ],
			rownumbers : true,
			fitColumns : true,
			singleSelect : false,
			striped : true,// 斑马线
			ctrlSelect : true,// 组合键选取多条数据：ctrl+鼠标左键
			columns : [ [ {
				field : 'sitename',
				title : '站点名称',
				width : 350,
				sortable : true
			}, {
				field : 'siteurl',
				title : '链接',
				width : 350,
				sortable : true
			}, {
				field : 'area.name',
				title : '城市',
				width : 350,
				sortable : true
			}, {
				field : 'status',
				title : '状态',
				width : 350,
				sortable : true,
				formatter : function(value, row, index) {
					if (row.status == 'DEL') {
						return "<font style='color:red'>删除</font>";
					} else {
						return "<font style='color:green'>正常</font>";
					}
				}
			}, {
				field : 'seq',
				title : '排序号',
				width : 350,
				sortable : true
			} ] ],
			toolbar : "#toolbar"
		});
	},
	// 打开新增窗口
	openAddForm : function() {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/sys/sysSite/editSite.jhtml?t="+t+'#loctop';
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		$("#editPanel").dialog("open");
	},
	// 打开编辑窗口
	openEditForm : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要编辑的站点!");
		} else {
			
			var t = Math.random(); 	// 保证页面刷新
			var url = "/sys/sysSite/editSite.jhtml?t="+t+'&siteId='+row.id+'#loctop';
			var ifr = $("#editPanel").children()[0];
			$(ifr).attr("src", url);
			$("#editPanel").dialog("open");
			
			
			/*
			$.post('/sys/SysSite/getEditSite.jhtml', {
				'sid' : row.id
			}, function(result) {
				console.log(result);
				if (result.success) {
					
					if(result.sysSite){
						if(result.sysSite.id){
							$('#hidden_siteid').val(result.sysSite.id);
						}	
						if(result.sysSite.sitename){
							$('#text_site_name').textbox('setValue',result.sysSite.sitename);
						}
						if(result.sysSite.siteurl){
							$('#text_siteurl').textbox('setValue',result.sysSite.siteurl);
						}
					}
					
					if(result.unit){
						if(result.unit.id){
							
							$('#hidden_unitid').val(result.unit.id);
						}
						if(result.unit.name){
							$('#text_name').textbox('setValue',result.unit.name);
						}
						if(result.unit.address){
							$('#text_address').textbox('setValue',result.unit.address);
						}
					}
					
					if(result.cityCode){
						var cityCode = result.cityCode;
						cityCode = cityCode.toString();
						
						cityCode = cityCode.substr(0, 2);
						$('#edit_province').val(cityCode+"0000");
						
						$.post('/sys/area/selectProvince.jhtml', {
							'provinceId' : $('#edit_province').val()
						}, function(data) {
							if (data.success == undefined || data.success) {
								var html = '';
								for (var i = 0; i < data.length; i++) {
									if(result.cityCode==data[i].id){
										console.log("aaa="+data[i].id);
										html += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
									}
									
								}
								$("#edit_city").html(html);
							} else {
								show_msg(data.errorMsg);
							}
						});
						
					}
						
					
					
					
					
					if(result.unitDetail){
						
						if((result.unitDetail.supplierType)){
							$.each($("input[name='supplierType']"),function(i,perValue){
								if(perValue.value == result.unitDetail.supplierType){
									perValue.checked = "checked";
									return false;
								}
							});
						}
						
							
						if((result.unitDetail.businessScope)){
							$.each($("input[name='businessScope']"),function(i,perValue){
								if(perValue.value == result.unitDetail.businessScope){
									perValue.checked = "checked";
									return false;
								}
							});
						}
						if((result.unitDetail.businessType)){
							$.each($("input[name='businessType']"),function(i,perValue){
								if(perValue.value == result.unitDetail.businessType){
									perValue.checked = "checked";
									return false;
								}
							});
						}
						if((result.unitDetail.businessModel)){
							$.each($("input[name='businessModel']"),function(i,perValue){
								if(perValue.value == result.unitDetail.businessModel){
									perValue.checked = "checked";
									return false;
								}
							});
						}
						
						if((result.unitDetail.telphone).length>0){
							$('#text_udtelphone').textbox('setValue',result.unitDetail.telphone);
						}
						if((result.unitDetail.fax).length>0){
							$('#text_udfax').textbox('setValue',result.unitDetail.fax);
						}
						if((result.unitDetail.mainBody).length>0){
							$('#text_udmainBody').textbox('setValue',result.unitDetail.mainBody);
						}
						
						if((result.unitDetail.partnerChannel).length>0){
							$('#text_udpartnerChannel').textbox('setValue',result.unitDetail.partnerChannel);
						}
						
						if((result.unitDetail.partnerUrl).length>0){
							$('#text_udpartnerUrl').textbox('setValue',result.unitDetail.partnerUrl);
						}
						if((result.unitDetail.partnerAdvantage).length>0){
							$('#text_udpartnerAdvantage').textbox('setValue',result.unitDetail.partnerAdvantage);
						}
					}
					
					
					
					$("#edit_dialog").dialog({
						title : '编辑站点',
						modal : true,
						top : "20",
						left : "100",
					});
//					$("#edit_dialog").dialog("open");
					
//					$("#dg").datagrid("reload");
//					show_msg("站点冻结成功");
				} else {
					show_msg(result.errorMsg);
				}
				
			});
			
			
//			$("#ff").form('load', '/sys/sysSite/editSite.jhtml', function(json) {
//				alert(json)
//			});
//			
			return ;
			
			*/
		} 
		
	},
	// 关闭编辑窗口
	closeEditPanel: function(isRefresh) {
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", '');
		$("#editPanel").dialog("close");
		if (isRefresh) {
			$('#dg').datagrid('reload'); 
		}
	},
	// 清除表单
	clearForm : function() {
		$("#ff").form("clear");
		alert("aa1");
	},
	// 显示详情
	showDetail : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要编辑的行!");
		} else {
			$("#showform").form('load', row);
			$("#show_panel").dialog({
				title : '查看站点信息',
				modal : true,
				top : "20",
				left : "100",
			});
			$("#show_panel").dialog("open");
		}
	},
	// 冻结站点
	forzenRole : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要冻结的站点!");
		} else {
			$.post('/sys/SysSite/forzenRole.jhtml', {
				'id' : row.id
			}, function(result) {
				if (result.success) {
					$("#dg").datagrid("reload");
					show_msg("站点冻结成功");
				} else {
					show_msg(result.errorMsg);
				}
			});
		}
	},
	// 解冻站点
	unForzenRole : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要解冻的站点!");
		} else {
			$.post('/sys/SysSite/unForzenRole.jhtml', {
				'id' : row.id
			}, function(result) {
				if (result.success) {
					$("#dg").datagrid("reload");
					show_msg("站点解冻成功");
				} else {
					show_msg(result.errorMsg);
				}
			});
		}
	},
	// 删除站点
	deleteRole : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要删除的站点!");
		} else {
			$.messager.confirm("", "删除后的站点不能复原!是否删除?", function(r) {
				if (r) {
					$.post('/sys/SysSite/deleteRole.jhtml', {
						'id' : row.id
					}, function(result) {
						if (result.success) {
							$("#dg").datagrid("reload");
							show_msg("站点删除成功");
						} else {
							show_msg(result.errorMsg);
						}
					});
				}
			});
		}
	},
	
	// 提交编辑表单
	submitEditForm : function() {
		$.messager.progress({
			title : '温馨提示',
			text : '数据处理中,请耐心等待...'
		});	
		$('#editForm').form('submit', {
			url : "/sys/site/editSite.jhtml",
			onSubmit : function() {
				if ($(this).form('validate') == false) {
					$.messager.progress('close');
				}
				return $(this).form('validate');
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
//				alert(result.success);
				if (result.success == true) {
					
					$("#edit_dialog").dialog("close");
					SysSite.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
				} else {
					show_msg(result.errorMsg);
				}
			}
		});
	},
	
	
	// 提交新增表单
	submitForm : function() {
		$.messager.progress({
			title : '温馨提示',
			text : '数据处理中,请耐心等待...'
		});
		$('#ff').form('submit', {
			url : "/sys/site/addNewSite.jhtml",
			onSubmit : function() {
				if ($(this).form('validate') == false) {
					$.messager.progress('close');
				}
				return $(this).form('validate');
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if (result.success == true) {
					$("#edit_panel").dialog("close");
					SysSite.clearForm();
					show_msg("保存成功!");
					$("#dg").datagrid("reload");
				} else {
					show_msg(result.errorMsg);
				}
			}
		});
	},
	// 打开站点授权
	openSetRight : function() {
		var row = $("#dg").datagrid("getSelected");
		if (row == null) {
			show_msg("请选择要授权的站点!");
		} else {
			setRightDialog = sy.dialog({
				title : '【' + row.name + '】站点授权(<font style="color:red">注:</font><font style="color:green">绿色为公开权限,不可取消</font>)',
				href : '/sys/sysRight/manage.jhtml?roleId=' + row.id,
				width : 1100,
				height : 500,
				modal : true,
				onLoad : function() {
					SysRight.initMenuTree();
				}
			});
		}
	}
};
$(function() {
	SysSite.init();
	// 编辑框关闭时清除表单
	$("#edit_panel").dialog({
		onClose : function() {
			$("#ff").form("clear");
			$(this).dialog('destroy');
		}
	});
});

var sy = $.extend({}, sy);// 定义全局对象，类似于命名空间或包的作用
var setRightDialog;
/** ******自定义dialog************* */
sy.dialog = function(options) {
	var opts = $.extend({
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	return $('<div/>').dialog(opts);
};