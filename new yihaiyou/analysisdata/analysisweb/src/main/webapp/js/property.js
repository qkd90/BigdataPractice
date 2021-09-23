/**
 * 商品属性
 */
var datagrid;
var editRow = undefined; // 定义全局变量：当前编辑的行


$(function(){
	//初始化表格高度
	initHeight("top_id", "dg");
	ProductPropertyJS.initData();
});

var ProductPropertyJS=$.fn.extend({
	
	
	/**
	 * 初始化数据
	 */
	initData : function() {
		datagrid=$("#dg").datagrid({
			title:'商品属性管理',
			url:'/product/property/findPropertyList.jhtml',
			idField: 'id',
			pagination:true,
			rownumbers:true,
			fitColumns:false,
			frozenColumns: [[
			                 { field: 'ck', checkbox: true },
			                 { field: 'id', title: 'id', width: 80, sortable: true ,hidden:true}
			                 ]],
			columns: [[
	                   { field: 'nameAdmin', title: '属性名称(后台)', width: 150, sortable: true },
	                   { field: 'name', title: '属性名称(前端)', width: 200, sortable: true },
	                   { field: 'statusStr', title: '状态', width: 100, sortable: true },
	                   { field: 'createTime', title: '创建时间', width: 150, sortable: true },
	                   { field: 'updateTime', title: '修改时间', width: 150, sortable: true },
	                   { field: 'remark', title: '备注', width: 200}
	               ]]
		});
	},
	
	/**
	 * 查询
	 */
	doSearch:function(){
		var propertyName = $("#propertyName").val();
		
		$("#dg").datagrid('load',{
			'nameAdmin':propertyName
		});
	},
	
	/**
	 * 删除
	 */
	doDelete:function(){
		var rows = $("#dg").datagrid('getSelections');
		if (!rows || rows.length <= 0) {
			$.messager.alert("温馨提示： ", "请先选择要删除的数据!");
			return false;
		}
		var paramVal = [];
		for (var i=0; i<rows.length; i++) {
			paramVal[i] = rows[i].id;
		}
		$.messager.progress({
		     title:'正在操作中,请稍候...'
		});
		$.post("/product/property/deleteProperty.jhtml?ids="+paramVal,function(result){
			if(result!=null){
				$.messager.progress("close");
				if(!result.success){
					$.messager.show({
						title : '温馨提示',
						msg : result.msg
					});
				}else{
					$.messager.show({
						title : '温馨提示',
						msg : '删除成功!'
					});
					// 重新加载
					datagrid.datagrid('reload');
					// 取消当前页中所有选中的行
					datagrid.datagrid('unselectAll');
				}
			}
		});
	},
	
	/**
	 * 添加
	 */
	doAdd:function(){
		$("#addId").dialog({
			title:'添加商品属性信息',
			fit:true,
			modal:true, 
			width: 600,    
		    height: 300,
			onLoad:function(){
			}
		});
		$("#addId").dialog('open').dialog('refresh','/product/property/create.jhtml');
	}, 
	
	/**
	 * 编辑
	 */
	doEdit:function() {
		var row = $("#dg").datagrid("getSelections");
		if (!row || row.length <= 0) {
			$.messager.alert("温馨提示： ", "请先选择要编辑的数据!");
			return false;
		}
		if (row.length > 1) {
			$.messager.alert("温馨提示： ", "只能编辑 1 条数据!");
			return false;
		}
		
		var id = row[0].id;
		
		$("#addId").dialog({
			title:'编辑商品属性信息',
			fit:true,
			modal:true, 
			width: 600,    
		    height: 300,
			onLoad:function(){
			}
		});
		$("#addId").dialog('open').dialog('refresh','/product/property/update.jhtml?id='+id);
	}

});
