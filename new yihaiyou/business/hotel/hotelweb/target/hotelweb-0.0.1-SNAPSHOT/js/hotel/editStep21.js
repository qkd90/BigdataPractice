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
			url:'/hotel/hotelRoomType/getShowHotelList.jhtml?productId=' + $("#productId").val(),
			data: [],
			//pagination:true,
			//pageList:[10,20,50],
			fit:true,
			rownumbers:true,
			singleSelect:false,
			striped:true, //斑马线
			ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
			columns: [[
				{ field: 'roomName', title: '房型名称', width: "33%"},
				//{ field: 'areaName', title: '价格', width: 100},
				{ field: 'breakfast', title: '早餐', width: "33%",
					formatter: function(value, row, index) {
						if (value) {
							return "含早餐";
						}else {
							return "无早餐";
						}
					}
				},
				/*{ field: 'status', title: '状态', width: 100,
					formatter: function(value, row, index) {
						if (value == "UP") {
							return "上架";
						}else if (value == "DOWN") {
							return "下架";
						}
					}
				},*/
				{ field: 'opt', title: '操作', width: "33%", align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep21.doEditPrice(" + rowData.id+")'>编辑</a>";
						var btnDel = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep21.delPrice("+rowData.id+")'>删除</a>";
						return btnView + "&nbsp;&nbsp;&nbsp;" + btnDel;
					}}
			]],
			toolbar: '#room_tool'
		});
	},

	delPrice: function(priceId) {
		var url = "/hotel/hotelRoomType/delRoomType.jhtml";
		$.post(url, { typePriceId: priceId},
			function(result){
				$("#room_dg").datagrid("load");
			}
		);
	},

	// 编辑类别报价
	doEditPrice : function(priceId) {
		var url = "/hotel/hotel/editStep2.jhtml?typePriceId=" + priceId;
		window.location.href = url;	
	},

	prevGuide:function(){
		var url = "/hotel/hotel/addStep2.jhtml?productId=" + $("#productId").val();
		window.location.href = url;	
	},

	// 下一步
	nextGuide:function(){
		parent.window.showGuide(3, true, "/hotel/hotel/editStep3.jhtml?productId=" + $("#productId").val());
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