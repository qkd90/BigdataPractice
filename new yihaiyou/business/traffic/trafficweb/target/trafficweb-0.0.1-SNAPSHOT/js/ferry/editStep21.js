var editStep21 = {
	init:function(){
		//editStep21.initStatus();
		//editStep21.initMinDiscountPrice();
		//editStep21.initMinRatePrice();
		editStep21.initDatagrid();
	},

	initDatagrid: function() {
		$("#room_dg").datagrid({
			fit:true,
			url:'/traffic/trafficPrice/listPrice.jhtml?productId=' + $("#productId").val(),
			data: [],
			//pagination:true,
			//pageList:[10,20,50],
			fit:true,
			rownumbers:true,
			singleSelect:false,
			striped:true, //斑马线
			ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'seatType', title: '座位类型', width: 100},
				{ field: 'seatName', title: '座位名称', width: 100},
				{ field: 'price', title: '分销价', width: 100},
				{ field: 'marketPrice', title: '市场价', width: 100},
				{ field: 'cprice', title: 'C端加价', width: 100},
				{ field: 'opt', title: '操作', width: 100, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep21.doEditPrice(" + rowData.id+")'>详情</a>";
						var btnDel = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep21.delPrice("+rowData.id+")'>删除</a>";
						return btnView + "&nbsp;&nbsp;&nbsp;" + btnDel;
					}}
			]],
			toolbar: '#room_tool'
		});
	},

	delPrice: function(priceId) {
		var url = "/traffic/trafficPrice/delTrafficPrice.jhtml";
		$.post(url, { typePriceId: priceId},
			function(result){
				$("#room_dg").datagrid("load");
			}
		);
	},

	// 编辑类别报价
	doEditPrice : function(priceId) {
		var url = "/traffic/ferry/editStep2.jhtml?typePriceId=" + priceId + "&productId=" + $("#productId").val();
		window.location.href = url;	
	},
	// 上一步
	prevGuide:function(){
		var url = "/traffic/ferry/editStep2.jhtml?productId=" + $("#productId").val();
		window.location.href = url;	
	},	
	// 下一步
	nextGuide:function(){
		parent.window.showGuide(3, true, "/traffic/ferry/editStep3.jhtml?productId=" + $("#productId").val());
	}
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
	editStep21.init();
});