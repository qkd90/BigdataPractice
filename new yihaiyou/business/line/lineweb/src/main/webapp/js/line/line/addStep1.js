var addStep1 = {
	limitNum: 6000,	// 字数限制仅文本
	maxLimitNum: 10000,	// 字数限制包含html标签
	init:function(){
		addStep1.initComp();
		addStep1.initAreaTextbox();
		addStep1.initStatus();
		PhotoJs.initSearch("/line/line/getProductImagesList.jhtml");
		PhotoJs.initTarget("addStep1");
		PhotoJs.initParams("/line/lineImg/upload.jhtml", LineConstants.lineDescImg);
        // 产品性质为自助游、自驾游，出发方式、交通方式为可选
		// “跟团游”发团地点为“目的地成团”，出发方式、交通方式为可选，且出发方式隐藏
        $('input[name=productAttr]').click(function() {
            var productAttr = $(this).val();
			var tourPlaceType =  $('input[name=tourPlaceType]:checked').val();
            if (productAttr == 'ziyou' || productAttr == 'zijia' || (productAttr == 'gentuan' && tourPlaceType == 'dest')) {
                //$('#country').combobox({required: false});
                //$('#province').combobox({required: false});
				$('#startCityId').textbox({required: false});  // 防止重复加载
				$("#startCityId").textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				$("#hidden_startCityId").val("");
				addStep1.initAreaTextbox();
				$('#goWay').combobox({required: false});
				$('#backWay').combobox({required: false});
				$('.requiredLable').hide();
                if (productAttr == 'gentuan') {
                    $('.tourPlaceType').show();
                    if (tourPlaceType == 'dest') {
                        $('.startPlace').hide();
                    } else {
                        $('.startPlace').show();
                    }
                } else {
                    $('.tourPlaceType').hide();
                    $('.startPlace').show();
                }
            } else {
                //$('#country').combobox({required: true});
                //$('#province').combobox({required: true});
                $('#startCityId').textbox({required: true});  // 防止重复加载
                $("#startCityId").textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				$("#hidden_startCityId").val("");
				addStep1.initAreaTextbox();
                $('#goWay').combobox({required: true});
                $('#backWay').combobox({required: true});
                $('.requiredLable').show();
                $('.startPlace').show();
                if (productAttr == 'gentuan') {
                    $('.tourPlaceType').show();
                } else {
                    $('.tourPlaceType').hide();
                }
            }
        });
        // “跟团游”发团地点为“目的地成团”，出发方式、交通方式为可选，且出发方式隐藏
        $('input[name=tourPlaceType]').click(function() {
            var val = $(this).val();
            if (val == 'dest') {
                //$('#country').combobox({required: false});
                //$('#province').combobox({required: false});
                $('#startCityId').textbox({required: false});  // 防止重复加载
                $("#startCityId").textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				$("#hidden_startCityId").val("");
				addStep1.initAreaTextbox();
                $('#goWay').combobox({required: false});
                $('#backWay').combobox({required: false});
                $('.requiredLable').hide();
                $('.startPlace').hide();
            } else {
                //$('#country').combobox({required: true});
                //$('#province').combobox({required: true});
                $('#startCityId').textbox({required: true});  // 防止重复加载
                $("#startCityId").textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				$("#hidden_startCityId").val("");
				addStep1.initAreaTextbox();
                $('#goWay').combobox({required: true});
                $('#backWay').combobox({required: true});
                $('.requiredLable').show();
                $('.startPlace').show();
            }
        });
	},


	initAreaTextbox: function() {
		// 出发城市查询条件
		$('#startCityId').textbox({
			onClickButton:function() {
				$('#startCityId').textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				// 特殊处理，为了结合原来代码
				$('#hidden_startCityId').val('');
			}
		});
		$("#startCityId").next('span').children('input').focus(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#startCityId').attr('data-country');
			var province = $('#startCityId').attr('data-province');
			var city = $('#startCityId').attr('data-city');
			AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
				$('#startCityId').textbox('setValue', fullName);
				if (countryId) {
					$('#startCityId').attr('data-country', countryId);
				} else {
					$('#startCityId').attr('data-country', '');
				}
				if (provinceId) {
					$('#startCityId').attr('data-province', provinceId);
				} else {
					$('#startCityId').attr('data-province', '');
				}
				if (cityId) {
					$('#startCityId').attr('data-city', cityId);
				} else {
					$('#startCityId').attr('data-city', '');
				}
				// 特殊处理，为了结合原来代码
				if (cityId) {
					$('#hidden_startCityId').val(cityId);
				} else if (provinceId) {
					$('#hidden_startCityId').val(provinceId);
				} else if (countryId) {
					$('#hidden_startCityId').val(countryId);
				}
				//LabelManage.searchLabelItem();
			});
		});


		// 到达城市查询条件
		$('#arriveCityId').textbox({
			onClickButton:function() {
				$('#arriveCityId').textbox('setValue', '');
				$('#arriveCityId').attr('data-country', '');
				$('#arriveCityId').attr('data-province', '');
				$('#arriveCityId').attr('data-city', '');
				// 特殊处理，为了结合原来代码
				$('#hidden_arriveCityId').val('');
			}
		});
		$("#arriveCityId").next('span').children('input').focus(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#arriveCityId').attr('data-country');
			var province = $('#arriveCityId').attr('data-province');
			var city = $('#arriveCityId').attr('data-city');
			AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
				$('#arriveCityId').textbox('setValue', fullName);
				if (countryId) {
					$('#arriveCityId').attr('data-country', countryId);
				} else {
					$('#arriveCityId').attr('data-country', '');
				}
				if (provinceId) {
					$('#arriveCityId').attr('data-province', provinceId);
				} else {
					$('#arriveCityId').attr('data-province', '');
				}
				if (cityId) {
					$('#arriveCityId').attr('data-city', cityId);
				} else {
					$('#arriveCityId').attr('data-city', '');
				}
				// 特殊处理，为了结合原来代码
				if (cityId) {
					$('#hidden_arriveCityId').val(cityId);
				} else if (provinceId) {
					$('#hidden_arriveCityId').val(provinceId);
				} else if (countryId) {
					$('#hidden_arriveCityId').val(countryId);
				}
				//LabelManage.searchLabelItem();
			});
		});

	},

	// 初始控件
	initComp : function() {


/*
        // 国家数据初始加载
        var countryData = [];
        $.ajax({
            async: false,
            dataType: 'json',
            type: 'POST',
            url: '/sys/area/listAreaNew.jhtml',
            data: {},
            success: function(data) {
                countryData = data;
            },
            error: function(){
                //error.apply(this, arguments);
            }
        });

		// 出发城市-一级联动控件
		$('#country').combobox({
            data : countryData,
			loader: function(param, success, error){
				if (!param.q) {return false;}
                $.ajax({
                    dataType: 'json',
                    type: 'POST',
					url: '/sys/area/listAreaNew.jhtml',
					data: {name: param.q},
					success: function(data) {
						success(data);
					},
					error: function(){
						//error.apply(this, arguments);
					}
				});
			},
            prompt:'请选择国家',
            valueField:"id",
            textField:"name",
			editable:false,
			mode: 'remote',
            onSelect: function(record){
                $("#province").combobox("clear");
                if (record && record.id) {
                    $("#province").combobox("reload");
                }
			}
		});
		// 出发城市-二级联动控件
		$("#province").combobox({
            loader: function(param, success, error){
                //var q = param.q || '';
                //if (q.length < 2) {return false;}
                var fatherId = $('#country').combobox("getValue");
                if (fatherId) {
                    $.ajax({
                        dataType: 'json',
                        type: 'POST',
                        url: '/sys/area/listAreaNew.jhtml',
                        data: {name: param.q, fatherId : fatherId},
                        success: function(data){
                            success(data);
                        },
                        error: function(){
                            //error.apply(this, arguments);
                        }
                    });
                }
            },
            prompt:'请选择',
            valueField:"id",
            textField:"name",
			editable:false,
            mode: 'remote',
            onSelect : function(record) {
				$("#startCityId").combobox("clear");
                if (record && record.id) {
					$("#startCityId").combobox("reload");
				}
			} 
		});
		// 出发城市-三级控件
		$("#startCityId").combobox({
            loader: function(param, success, error){
                //var q = param.q || '';
                //if (q.length < 2) {return false;}
                var fatherId = $('#province').combobox("getValue");
                if (fatherId) {
                    $.ajax({
                        dataType: 'json',
                        type: 'POST',
                        url: '/sys/area/listAreaNew.jhtml',
                        data: {name: param.q, fatherId: fatherId},
                        success: function (data) {
                            success(data);
                        },
                        error: function () {
                            //error.apply(this, arguments);
                        }
                    });
                }
            },
            prompt:'请选择',
            valueField:"id",
            textField:"name",
			editable:false,
            mode: 'remote'
		});

        // 到达城市-一级联动控件
        $('#arriveCountry').combobox({
            data : countryData,
            loader: function(param, success, error){
                if (!param.q) {return false;}
                $.ajax({
                    dataType: 'json',
                    type: 'POST',
                    url: '/sys/area/listAreaNew.jhtml',
                    data: {name: param.q},
                    success: function(data) {
                        success(data);
                    },
                    error: function(){
                        //error.apply(this, arguments);
                    }
                });
            },
            prompt:'请选择国家',
            valueField:"id",
            textField:"name",
            mode: 'remote',
			editable: false,
            onSelect: function(record){
                $("#arriveProvince").combobox("clear");
                if (record && record.id) {
                    $("#arriveProvince").combobox("reload");
                }
            }
        });
        // 到达城市-二级联动控件
        $("#arriveProvince").combobox({
            loader: function(param, success, error){
                //var q = param.q || '';
                //if (q.length < 2) {return false;}
                var fatherId = $('#arriveCountry').combobox("getValue");
                if (fatherId) {
                    $.ajax({
                        dataType: 'json',
                        type: 'POST',
                        url: '/sys/area/listAreaNew.jhtml',
                        data: {name: param.q, fatherId : fatherId},
                        success: function(data){
                            success(data);
                        },
                        error: function(){
                            //error.apply(this, arguments);
                        }
                    });
                }
            },
            prompt:'请选择',
            valueField:"id",
            textField:"name",
            mode: 'remote',
			editable:false,
            onSelect : function(record) {
                $("#arriveCityId").combobox("clear");
                if (record && record.id) {
                    $("#arriveCityId").combobox("reload");
                }
            }
        });
        // 到达城市-三级控件
        $("#arriveCityId").combobox({
            loader: function(param, success, error){
                //var q = param.q || '';
                //if (q.length < 2) {return false;}
                var fatherId = $('#arriveProvince').combobox("getValue");
                if (fatherId) {
                    $.ajax({
                        dataType: 'json',
                        type: 'POST',
                        url: '/sys/area/listAreaNew.jhtml',
                        data: {name: param.q, fatherId: fatherId},
                        success: function (data) {
                            success(data);
                        },
                        error: function () {
                            //error.apply(this, arguments);
                        }
                    });
                }
            },
            prompt:'请选择',
            valueField:"id",
            textField:"name",
			editable:false,
            mode: 'remote'

        });*/

		//富文本 产品经理推荐
		KindEditor.ready(function(K) {
			lineLightPointK = K.create('#line-shortDesc', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
				fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
				allowImageUpload : true,
				allowFileManager : true,
				filePostName: 'resource',
				afterChange: function() {
					this.sync();
					var hasNum = this.count('text');
					if (hasNum > addStep1.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,addStep1.limitNum);
						this.text(strValue);
						show_msg("字数过长已被截取，请简化");
						//计算剩余字数
						$('textarea[name="shortDesc"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="shortDesc"]').next().children('.green-bold').html(addStep1.limitNum-hasNum);
					}
				},
				afterBlur: function() {
					this.sync();
				},
				items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
			});
		});
/*
		// 图片上传
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#uploadButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#filePath').val(), folder:LineConstants.lineDescImg},
				url : '/line/lineImg/upload.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						var url = K.formatUrl(result.url, 'absolute');
						$('#filePath').val(url);	
						$('#imgView img').attr('src', '/static'+url);
						$('#imgView').show();
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
				
				$('input[name=oldFilePath]').val($('#filePath').val());	// 添加动态参数，隐藏标签是KindEditor自动生成的	
				uploadbutton.submit();
			});
		});*/
	},
	// 初始状态
	initStatus : function() {
		$('#childFolder').val(LineConstants.lineDescImg);
	},

	/**
	 * 新增图片组
	 * @param day
	 * @param time
	 */
	addImgBtn: function() {
		var dialogHtml = PhotoJs.createPhotDiv();
		$("body").append(dialogHtml);
		$("body").mask();
		$("#dingwei").css("top", "50%");
		$("#hid_daytime").attr("day", 0);
		$("#hid_daytime").attr("time", 0);
		PhotoJs.createUploadBtn();
	},


	addStepUlImg: function(url, title) {
		var ulImgArr = $("#imgUl").children();
		var index = $("#imgUl").children().length;
		index = index + 1;
		if (ulImgArr.length > 0) {
			$("#imgUl").children().last().after(addStep1.addLiImgDiv(index, url, title));
		} else {
			var htmlDiv = "";
			htmlDiv += '<div id="imgLiDiv" style="width: 700px; height: 185px;">';
			htmlDiv += '<input type="hidden" name="imgPaths" id="imgPath">';
			htmlDiv += '<ul id="imgUl">';
			htmlDiv += '</ul>';
			htmlDiv += '</div>';
			$("#addImgDivBtn").after(htmlDiv);
			$("#imgUl").append(addStep1.addLiImgDiv(index, url, title));
		}
	},

	addLiImgDiv: function(i, url, title) {
		var liImg = "";
		liImg += '<li id="li_'+ i +'" style="list-style:none; float: left; padding: 10px;">';
		liImg += '<div style="position:relative; width: 150px; height: 185px;">';
		liImg += '<img id="day_plan_img_1_0_0_1" src="'+ url +'" style="width: 150px; height: 160px;border: 1px solid #ccc;">';
		liImg += '<i class="iconfont iconfont-hover" style="color: red; position:absolute; top:0px; right:-2px; z-index:99;" onclick="addStep1.delLiImg('+ i +')"></i>';
		liImg += '<input type="hidden" class="hid_imgDetailUrl" title="'+ title +'" value="'+ url +'"/>';
		liImg += '</div>';
		liImg += '</li>';

		return liImg;
	},

	setImgPathData: function() {
		var hidImgUrlArr = $(".hid_imgDetailUrl");
		if (hidImgUrlArr) {
			var imgArr = [];
			$.each(hidImgUrlArr, function(i, perValue) {
				var data = {};
				data['imgUrl'] = $(perValue).val();
				data['title'] = $(perValue).attr("title");
				imgArr.push(data);
			});
			$("#imgPath").val(JSON.stringify(imgArr));
		}
	},

	delLiImg: function(i) {

		var ulImgArr = $("#imgUl").children();
		if (ulImgArr.length > 1) {
			$("#li_"+ i +"").remove();
		} else {
			$("#imgLiDiv").remove();
		}
	},

	/*// 删除图片
	delImg : function() {
		// 异步删除图片文件
		$.post("/line/lineImg/delFile.jhtml",
			{oldFilePath : $('#filePath').val()},
			function(result) {
				// TODO 暂时不做提示
    		}
		);
		
		$('#filePath').val('');	
		$('#imgView img').attr('src', '');
		$('#imgView').hide();
	},*/
	// 下一步
	nextGuide:function(){
        // 线路是否有图片校验
        var newImgLength = $("#imageContent").find('input[name = "lineImgPaths"]').length;
        var existImgLength = $("#imageBox").find('.viewThumb').children('img').length;
        if (newImgLength + existImgLength <= 0) {
            show_msg("请上传线路图片!");
            return;
        }
		addStep1.setImgPathData();
		// 保存表单
		$('#editForm').form('submit', {
			url : "/line/line/saveLine.jhtml",
			onSubmit : function() {
				// 产品经理推荐 字段验证
				var shortDesc = $('textarea[name$="shortDesc"]').val();
				if (shortDesc && shortDesc.length > addStep1.maxLimitNum) {
					show_msg("行程亮点html标签过多，请简化");
					return false;
				}

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
					parent.window.showGuide(2, true);	
				}else{
					show_msg("保存线路失败");
				}
			}
		});
		//parent.window.showGuide(2, true);	
	},
	// 清除出发地
	clearStartPlace : function() {
        //$('#country').combobox('setValue', '');
		//$('#province').combobox('setValue', '');
		$('#startCityId').attr('data-country', '');
		$('#startCityId').attr('data-province', '');
		$('#startCityId').attr('data-city', '');
		$('#startCityId').textbox('setValue', '');
		$("#hidden_startCityId").val("");
	},
	// 清除交通方式
	clearTrafficWay : function() {
		$('#goWay').combobox('setValue', '');
		$('#backWay').combobox('setValue', '');
		$('#wayDesc').textbox('setValue', '');
	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};
// 返回本页面数据
function getIfrData(){
	var data = {};
	data.productId = $('#productId').val();
	data.productName = $('#name').textbox('getValue');
	data.productAttr = $(':checked[name="productAttr"]').val();
	return data;
}
$(function(){
	addStep1.init();
});