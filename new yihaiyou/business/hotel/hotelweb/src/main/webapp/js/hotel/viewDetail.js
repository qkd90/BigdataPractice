var ViewDetail = {
	init:function(){
		ViewDetail.initMap();
		ViewDetail.initXiangqing();
		ViewDetail.initRoomTypeList();
	},

	addPiceMore:function() {
		$(".moreImage").show();
		$("#fileBox2").show();
		$("#fileBox1").hide();
	},

	hidePiceMore: function() {
		$(".moreImage").hide();
		$("#fileBox2").hide();
		$("#fileBox1").show();
	},


	initMap: function() {

		var map = new BMap.Map("baiduMap");

		map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用


		var lng = $("#lon").val();
		var lat = $("#lat").val();

		//创建小狐狸
		var pt = new BMap.Point(lng, lat);
		map.centerAndZoom(pt, 12);
		var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
		var marker = new BMap.Marker(pt,{icon:myIcon});
		map.addOverlay(marker);    //增加点

	},

	initXiangqing:function(){
		//富文本产品详情
		var editorXiangqing;
		KindEditor.ready(function(K) {
			editorXiangqing = K.create('#hotelInfoDesc', {
				resizeType : 1,
				readonlyMode : true,
				allowPreviewEmoticons : false,
				uploadJson : '/hotel/hotel/uploadImg.jhtml?folder=' + HotelConstants.hotelDescImg,
				fileManagerJson :  '/hotel/hotel/imgsView.jhtml?folder=' + HotelConstants.hotelDescImg,
				allowImageUpload : true,
				allowFileManager : true,
				filePostName: 'resource',
				items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline', 'table',
					'removeformat', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', 'image', 'link' ],
				afterChange: function() {
					this.sync();
				},
				afterBlur: function() {
					this.sync();
				}
			});
		});


	},


	/**
	 * 房型列表
	 */
	initRoomTypeList: function() {

		$("#roomTypeList").datagrid({
			fit:true,
			url:'/hotel/hotelRoomType/getShowHotelList.jhtml?productId=' + $("#productId").val(),
			data: [],
			fit:true,
			rownumbers:false,
			singleSelect:false,
			striped:true, //斑马线
			columns: [[
				{ field: 'roomName', title: '房型名称', width: 200},
				{ field: 'breakfast', title: '早餐', width: 100,
					formatter: function(value, row, index) {
						if (value) {
							return "含早餐";
						}else {
							return "无早餐";
						}
					}
				},
				{ field: 'status', title: '状态', width: 100,
					formatter: function(value, row, index) {
						if (value == "UP") {
							return "上架";
						}else if (value == "DOWN") {
							return "下架";
						}
					}
				},
				{ field: 'opt', title: '操作', width: 100, align: 'center',
					formatter : function(value, rowData, rowIndex) {
						var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ViewDetail.checkDatePrice(" + rowData.id+")'>查看价格</a>";
						return btnView;
					}}
			]]
		});

	},

	checkDatePrice: function(id) {
		var ifr = $("#sel_startTime").children()[0];
		var url = "/hotel/hotel/selectDatePrice.jhtml?typePriceId=" + id;
		$(ifr).attr("src", url);
		$("#sel_startTime").dialog({
			title: '选择日历价格',
			width: 350,
			height: 400,
			closed: false,
			cache: false,
			modal: true
		});
		$("#sel_startTime").dialog("open");

	}


};

$(function(){
	ViewDetail.init();
});