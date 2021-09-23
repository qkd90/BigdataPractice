//////////////////库存///////////////
var Store=$.extend({},{
	//加载预帐库存订单详情
	loadLockNumOrder:function(proId,stockId,storeId){
		$.post('/repertory/store/searchLockNumOrder.jhtml', {
			'proId' : proId,
			'stockId': stockId,
			'storeCode.id': storeId
		}, function(result) {
			$("#lockId").datagrid({
				title:'库存预占详情',
				loadMsg:'加载中',
				pagination:true,
				rownumbers:true,
				fitColumns:true,
				singleSelect:true,
				data:result,
				columns: [[
				           {field:'billType',title:'预占单据类型',width:120,align:'center'},
				           {field:'billNo',title:'订单号',width:130,align:'center'},
				           {field:'lockOrderProName',title:'预帐订单商品',width:250,formatter:function(value,row){
				        	   return row.product.proName;
				           }},
				           {field:'lockQty',title:'预占数量',width:100,align:'center'},    
				          ]],
//				view: detailview,
//				detailFormatter: function (index, row) {
//                    return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
//                },
//				onExpandRow: function(index,row){  
//					
//				}
			});
		});
	}
});
