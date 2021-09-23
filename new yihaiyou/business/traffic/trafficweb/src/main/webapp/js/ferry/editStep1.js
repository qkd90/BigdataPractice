var editStep1 = {
	init:function(){
		editStep1.initAreaTextbox();
		editStep1.initCom();
	},

	initCom: function() {
		$("#leavePort").combobox({
			onSelect: function(record) {
				$("#hid_leavePortName").val(record.selName);
			}
		});
		$("#arrivePort").combobox({
			onSelect: function(record) {
				$("#hid_endPortName").val(record.selName);
			}
		});


		$("#leaveTimeId").timespinner({
			onChange: function(newVlaue, oldVlaue) {
				var days = $("#daysId").numberspinner("getValue");
				var arriveTime = $("#arriveTimeId").timespinner("getValue");
				editStep1.sumTime(days, newVlaue, arriveTime);
			}
		});
		$("#daysId").numberspinner({
			onChange: function(newVlaue, oldVlaue) {
				var leaveTime = $("#leaveTimeId").timespinner("getValue");
				var arriveTime = $("#arriveTimeId").timespinner("getValue");
				editStep1.sumTime(newVlaue, leaveTime, arriveTime);
			}
		});
		$("#arriveTimeId").timespinner({
			onChange: function(newVlaue, oldVlaue) {
				var days = $("#daysId").numberspinner("getValue");
				var leaveTime = $("#leaveTimeId").timespinner("getValue");
				editStep1.sumTime(days, leaveTime, newVlaue);
			}
		});

	},

	sumTime: function(days, leaveTime, arriveTime) {
		var totalLeaveTime = Number(leaveTime.substr(0, leaveTime.indexOf(":")))*60 + Number(leaveTime.substr(leaveTime.indexOf(":") + 1, leaveTime.length-1));
		var day = Number(days);
		var totaArriveTime = Number(arriveTime.substr(0, arriveTime.indexOf(":")))*60 + Number(arriveTime.substr(arriveTime.indexOf(":") + 1, arriveTime.length-1));
		var totalTime = 0;
		if (day > 1) {
			totalTime = (day - 1) * 1440 - totalLeaveTime + totaArriveTime;
		} else {
			totalTime = totaArriveTime - totalLeaveTime;
		}
		$("#flightTime").textbox("setValue", totalTime);
	},

	transportLoader: function(param, success, error) {
		var q = param.q || '';
		if (q.length <= 1) {
			return false
		}

		var trafficType = 4;
		//if ($(":input[name='traffic.trafficType']:checked").val() == "TRAIN") {
		//	trafficType = 1;
		//} else if ($(":input[name='traffic.trafficType']:checked").val() == "AIRPLANE") {
		//	trafficType = 2;
		//} else if ($(":input[name='traffic.trafficType']:checked").val() == "SHIP") {
		//	trafficType = 4;
		//} else {
		//	trafficType = 3;
		//}

		var type = $(this).attr("data-type");
		var data = {};
		if (type == "leaveProt") {
			data = {
				featureClass : "P",
				style : "full",
				pageSize : 20,
				'transportation.searchName' : q,
				'transportation.cityCode' : $("#hidden_startCityId").val(),
				'transportation.type' : trafficType
			};
		} else {
			data = {
				featureClass : "P",
				style : "full",
				pageSize : 20,
				'transportation.searchName' : q,
				'transportation.cityCode' : $("#hidden_arriveCityId").val(),
				'transportation.type' : trafficType
			};
		}

		$.ajax({
			url : '/transportation/transportation/list.jhtml',
			dataType : 'json',
			//contentType: "application/x-www-form-urlencoded; charset=utf-8",
			type : 'POST',
			data : data,
			success : function(data) {
				var items = $.map(data.rows, function(item) {
					return {
						id:item.id,
						name:item.searchName,
						selName: item.name
					};
				});
				//console.log(items);
				success(items);
			},
			error : function() {
				error.apply(this, arguments);
			}
		});
	},

	resetProt: function() {
		$("#leavePort").combobox("setValue", "");
		$("#hid_leavePortName").val("");
		$("#arrivePort").combobox("setValue", "");
		$("#hid_endPortName").val("");
	},

	resetLeavePort: function() {
		$("#leavePort").combobox("setValue", "");
		$("#hid_leavePortName").val("");
	},
	resetArrivePort: function() {
		$("#arrivePort").combobox("setValue", "");
		$("#hid_endPortName").val("");
	},

	initAreaTextbox: function() {
		var hid_startCityId = $("#hidden_startCityId").val();
		if (hid_startCityId) {
			var url = "/sys/area/getAreaInfo.jhtml";
			$.post(url,
				{cityId: hid_startCityId},
				function(result) {
					if (result.level == 2) {
						var fullName = result.countryName + "/" + result.provinceName + "/" + result.cityName;
						$('#startCityId').textbox('setValue', fullName);
						$('#startCityId').attr('data-country', result.countryId);
						$('#startCityId').attr('data-province', result.provinceId);
						$('#startCityId').attr('data-city', result.cityId);
					} else if (result.level == 1) {
						var fullName = result.countryName + "/" + result.provinceName ;
						$('#startCityId').textbox('setValue', fullName);
						$('#startCityId').attr('data-country', result.countryId);
						$('#startCityId').attr('data-province', result.provinceId);
					} else if (result.level == 0) {
						var fullName = result.countryName;
						$('#startCityId').textbox('setValue', fullName);
						$('#startCityId').attr('data-country', result.countryId);
					}

				}
			);

		}

		var hid_arriveCityId = $("#hidden_arriveCityId").val();
		if (hid_arriveCityId) {
			var url = "/sys/area/getAreaInfo.jhtml";
			$.post(url,
				{cityId: hid_arriveCityId},
				function(result) {
					if (result.level == 2) {
						var fullName = result.countryName + "/" + result.provinceName + "/" + result.cityName;
						$('#arriveCityId').textbox('setValue', fullName);
						$('#arriveCityId').attr('data-country', result.countryId);
						$('#arriveCityId').attr('data-province', result.provinceId);
						$('#arriveCityId').attr('data-city', result.cityId);
					} else if (result.level == 1) {
						var fullName = result.countryName + "/" + result.provinceName ;
						$('#arriveCityId').textbox('setValue', fullName);
						$('#arriveCityId').attr('data-country', result.countryId);
						$('#arriveCityId').attr('data-province', result.provinceId);
					} else if (result.level == 0) {
						var fullName = result.countryName;
						$('#arriveCityId').textbox('setValue', fullName);
						$('#arriveCityId').attr('data-country', result.countryId);
					}

				}
			);

		}


		// 出发城市查询条件
		$('#startCityId').textbox({
			onClickButton:function() {
				$('#startCityId').textbox('setValue', '');
				$('#startCityId').attr('data-country', '');
				$('#startCityId').attr('data-province', '');
				$('#startCityId').attr('data-city', '');
				// 特殊处理，为了结合原来代码
				$('#hidden_startCityId').val('');

				editStep1.resetLeavePort();
			}
		});
		$("#startCityId").next('span').children('input').focus(function() {

			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#startCityId').attr('data-country');
			var province = $('#startCityId').attr('data-province');
			var city = $('#startCityId').attr('data-city');
			editStep1.resetLeavePort();
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
				editStep1.resetArrivePort();
			}
		});
		$("#arriveCityId").next('span').children('input').focus(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#arriveCityId').attr('data-country');
			var province = $('#arriveCityId').attr('data-province');
			var city = $('#arriveCityId').attr('data-city');
			editStep1.resetArrivePort();
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

	// 下一步
	nextGuide:function(){
		// 保存表单
		$('#editForm').form('submit', {
			url : "/traffic/ferry/saveTraffic.jhtml",
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
					parent.window.showGuide(2, true, "/traffic/ferry/addStep2.jhtml?productId="+result.id);
				}else{
					show_msg("保存酒店失败");
				}
			}
		});

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
					$(".diyStart").click();
					parent.window.showGuide(2, true, "/hotel/hotel/addStep2.jhtml?productId="+result.id);
				}else{
					show_msg("保存酒店失败");
				}
			}
		});

	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};

$(function(){
	editStep1.init();
});