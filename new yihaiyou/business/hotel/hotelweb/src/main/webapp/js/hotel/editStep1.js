var editStep1 = {
	init:function(){
		var name = "";
		editStep1.initMap(name);
		editStep1.initXiangqing();
		editStep1.initAddCover();
		editStep1.initAreaComp();
		editStep1.initCom();
		editStep1.initPrc();
	},

	initPrc: function() {
		//extraParams : {oldFilePath:$('#filePath').val(), folder:HotelConstants.hotelDescImg},
		$('#as').diyUpload({
			url:'/hotel/hotel/uploadPics.jhtml?folder='+HotelConstants.hotelDescImg,
			success:function( result, $fileBox) {
				//showImage($('#as'), data.url, 0);
				if (result.success) {
					//uploadSuccess($('#as'), result.url, result.imgIndex, this);
					$("#name_"+ result.imgIndex +"").val(result.url);
					var address = result.url;
					$fileBox.remove();
					showImage($('#as'), address, result.imgIndex);
					$('#fileBox_' + result.imgIndex).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
					$('#fileBox_' + result.imgIndex).find('.diyCover').click(function () {//增加事件
						$('#coverParent').html('<div id="coverBox"></div>');
						$('#coverPath').prop('value', result.url);
						$('#imageBox').find('.coverSuccess').hide();
						$('#fileBox_' + result.imgIndex).find('.coverSuccess').show();
						showImageWithoutCancel($('#coverBox'), address,  result.imgIndex);
					});
					$('#fileBox_' + result.imgIndex).find('.diyCancel').click(function () {
						if (result.url == $('#coverPath').val()) {
							showMsgPlus('提示', '封面已经被删除!!', '3000');
							$('#coverBox').next('div.parentFileBox').remove();
							$('#coverPath').val(null);
							$('#coverImgId').val(null);
						}
						$("#input_" + result.imgIndex).remove();
					});

				}
			},
			error:function( err ) {
				console.info( err );
			},
			buttonText : '添加图片',
			chunked:true,
			// 分片大小
			chunkSize:512 * 1024,
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit:30,
			fileSizeLimit:500000 * 1024,
			fileSingleSizeLimit:50000 * 1024,
			accept: {}
		});
		var temp_images = $("#temp_images").val();
		if (temp_images.length > 0) {
			var images = temp_images.split(",");
			if (images.length > 0) {
				$.each(images, function(i, perValue) {
					showImage($('#as'), perValue, i)
				});
			}
		}
	},

	initMap: function(name) {

		var map = new BMap.Map("baiduMap");

		map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

		var lng = $("#map_lng").textbox("getValue");
		var lat = $("#map_lat").textbox("getValue");

		if (name.length > 0 && name != null) {
			//创建小点
			map.centerAndZoom(name, 12);
			$("#map_lng").textbox("setValue", "");
			$("#map_lat").textbox("setValue", "");
		} else {
			var pt = new BMap.Point(lng, lat);
			map.centerAndZoom(pt, 11);
			var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
			var marker = new BMap.Marker(pt,{icon:myIcon});
			map.addOverlay(marker);    //增加点

		}

		//单击获取点击的经纬度
		map.addEventListener("click",function(e){
			map.clearOverlays();
			$("#map_lng").textbox("setValue", e.point.lng);
			$("#map_lat").textbox("setValue", e.point.lat);
			lng = e.point.lng;
			lat = e.point.lat;
			//创建小狐狸
			var pt = new BMap.Point(lng, lat);
			var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
			var marker = new BMap.Marker(pt,{icon:myIcon});
			map.addOverlay(marker);    //增加点
		});

	},

	initCom: function() {

/*
		//民宿类型
		$("#com_hotelType").combobox({
			valueField: 'id',
			textField: 'name',
			panelHeight:'auto',
			panelMaxHeight:100,
			onShowPanel: function () {
				var url = "/hotel/hotelAmenities/getListByFatherName.jhtml?hotelAmenities.id=678";
				$.post(url,
					function(result){
						if (result) {
							var resultData = result.rows;
							var loadData = [];
							if (resultData.length >0) {
								$.each(resultData, function(i, perValue) {
									var data = {
										id:perValue.id,
										name:perValue.name
									};
									loadData.push(data);
								});
							}
							$("#com_hotelType").combobox('loadData', loadData);
						}
					}
				);
			}
		});*/

		//民宿主题
		$("#com_hotelTheme").combobox({
			url:"/hotel/hotelAmenities/getListByFatherName.jhtml?hotelAmenities.id=96",
			valueField: 'id',
			textField: 'name',
			panelHeight:200
		});

		//民宿服务
		$("#com_serviceAmenities").combobox({
			url:"/hotel/hotelAmenities/getListByFatherName.jhtml?hotelAmenities.id=7",
			valueField: 'id',
			textField: 'name',
			panelHeight:200,
			panelMaxHeight:100
		});

		//民宿休闲
		$("#com_recreationAmenities").combobox({
			url:"/hotel/hotelAmenities/getListByFatherName.jhtml?hotelAmenities.id=9",
			valueField: 'id',
			textField: 'name',
			panelHeight:200,
			panelMaxHeight:100

		});

		//民宿服务
		$("#com_generalAmenities").combobox({
			url : "/hotel/hotelAmenities/getGeneralAmenities.jhtml?ids=8,199,195,202",
			valueField: 'id',
			textField: 'name',
			panelHeight:200,
			panelMaxHeight:100,
			checkbox:false
		});
	},

	initXiangqing:function(){
		//富文本产品详情
		var editorXiangqing;
		KindEditor.ready(function(K) {
			editorXiangqing = K.create('#hotelInfoDesc', {
				resizeType : 1,
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
					var textCount = 2000 - this.count('text');
					if (textCount >= 500){
						K("#text_count").html(textCount);
						K("#text_count").css("color", "green");
					} else if (textCount <= 500 && textCount > 0) {
						K("#text_count").html(textCount);
						K("#text_count").css("color", "red");
					} else {
						show_msg("民宿详情内容过长，请重新编辑！");
					}
				},
				afterBlur: function() {
					this.sync();
				}
			});
		});


	},


	/**
	 * 新增描述图片
	 */
	initAddCover: function() {

		var filePath = $("#filePath").val();

		if (filePath) {
			$('#imgView img').attr('src', filePath);
			$('#imgView').show();
			$("#btn_class").hide();
		}

		// 图片上传
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#add_descpic')[0],
				fieldName : 'resource',
				url : '/hotel/hotel/uploadImg.jhtml',
				extraParams : {oldFilePath:$('#filePath').val(), folder:HotelConstants.hotelDescImg},
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						var url = K.formatUrl(result.url, 'absolute');
						$('#filePath').val(url);
						$('#imgView img').attr('src', url);
						$('#imgView').show();
						$("#btn_class").hide();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
			uploadbutton.fileBox.change(function(e) {
				var filePath = uploadbutton.fileBox[0].value;
				if (!filePath) {
					show_msg("图片格式不正确");
					return ;
				}
				var suffix = filePath.substr(filePath.lastIndexOf("."));
				suffix = suffix.toLowerCase();
				if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
					show_msg("图片格式不正确");
					return ;
				}
				$.messager.progress({
					title:'温馨提示',
					text:'图片上传中,请耐心等待...'
				});
				uploadbutton.submit();
			});
		});



	},
	/**
	 * 删除图片
	 */
	delDescPic: function() {
		var filePath = $("#filePath").val();
		if (filePath) {
			$("#filePath").val("");
			$('#imgView').hide();
			$('#imgView img').attr('src', "");
			$("#btn_class").show();
		} else {
			return;
		}


	},

	/**
	 * 初始化地区选择
	 */
	initAreaComp : function() {
		$("#com_province").combobox({
			valueField: 'id',
			textField: 'name',
			url:"/hotel/hotel/getAreaList.jhtml?area.level=1&area.id=100000",
			panelMinHeight:0,
			panelMaxHeight:100,
			onSelect: function() {
				$("#com_city").combobox("clear");
				var url="/hotel/hotel/getAreaList.jhtml?area.level=2&area.id="+$("#com_province").combobox("getValue");
				$.ajax({
					type: "POST",
					url: url,
					cache: false,
					dataType : "json",
					success: function(data){
						$("#com_city").combobox("loadData",data);
					}
				});
			},
			onLoadSuccess : function() {
				var city = $("#com_city").combobox("getValue");
				if (city) {
					var pro = city.substr(0,2)+'0000';
					$("#com_province").combobox('setValue', pro);
				}

				var url="/hotel/hotel/getAreaList.jhtml?area.level=2&area.id="+$("#com_province").combobox("getValue");
				$.ajax({
					type: "POST",
					url: url,
					cache: false,
					dataType : "json",
					success: function(data){
						$("#com_city").combobox("loadData",data);
					}
				});
			}
		});

		$("#com_city").combobox({
			valueField: 'id',
			textField: 'name',
			url:"/hotel/hotel/getAreaList.jhtml?area.level=2&area.id="+$("#com_province").combobox("getValue"),
			panelMinHeight:0,
			panelMaxHeight:100,
			onSelect: function(record) {
				editStep1.initMap(record.fullPath);
				var proName = $("#text_region_detail").textbox("getValue");
				$("#text_region_detail").textbox("setValue", record.fullPath);

			},
			onLoadSuccess: function() {
				var city = $("#com_city").combobox("getValue");
				if (city.length > 0) {
					var city = city.substr(0,4)+'00';
					$("#com_city").combobox('setValue', city);
				}
			}

		});
	},
	// 初始状态
	initStatus : function() {
		$('#childFolder').val(LineConstants.lineDescImg);
	},
	setImageData: function() {
		var inputArr = $(".fileBoxId");
		if (inputArr.length > 0) {
			var imageUrl = [];
			$.each(inputArr, function(i, perValue) {
				var value = perValue.value;
				if (value.length > 0 && value != null && value != 'undefind') {
					imageUrl.push(perValue.value);
				}
			});
			if (imageUrl.length > 0) {
				$("#imageUrls").val(imageUrl.join(","));
				return true;
			} else {
				show_msg("请上传民宿相册！");
				return false;
			}
		} else {
			show_msg("请完善民宿相册！");
			return false;
		}

	},

	// 下一步
	nextGuide:function(){
		//parent.window.showGuide(2, true);


		editStep1.setImageData();
		var imageUrls = $("#imageUrls").val();
		if (imageUrls) {
			var filePath = $("#filePath").val();
			if (filePath) {
				var hotelInfoDesc = $("#hotelInfoDesc").val();
				if (hotelInfoDesc) {
					editStep1.saveHotel();
				} else {
					show_msg("请完善民宿详情！");
				}
			} else {
				show_msg("请完善民宿封面图！");
			}
		}



		//parent.window.showGuide(2, true);
	},

	saveHotel: function() {
		// 保存表单
		$('#editForm').form('submit', {
			url : "/hotel/hotel/saveHotel.jhtml",
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if($(this).form('validate')){
					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
				} else {
					show_msg("请完善当前页面数据");
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					$('#productId').val(result.id);
					parent.window.showGuide(2, true, "/hotel/hotel/eddStep21.jhtml?productId="+result.id);
				}else{
					show_msg("保存民宿失败");
				}
			}
		});
	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};
// 返回本页面数据
/*unction getIfrData(){
	var data = {};
	data.productId = $('#productId').val();
	data.productName = $('#name').textbox('getValue');
	data.productAttr = $(':checked[name="productAttr"]').val();
	return data;
}*/
$(function(){
	editStep1.init();
});