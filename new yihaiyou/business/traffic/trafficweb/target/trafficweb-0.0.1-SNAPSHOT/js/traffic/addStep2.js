var addStep2 = {
	limitNum: 3000,	// 字数限制仅文本
	maxLimitNum: 4000,	// 字数限制包含html标签
	quoteDescK: null,
		
	init:function(){
		addStep2.initCalendar();
		addStep2.initCom();
		addStep2.initListener();
		addStep2.initStatus();
	},
	initCom: function() {

	},
	// 初始化控件
	initCalendar:function(){
		var priceId = $('#priceId').val();
		var dateStart = $('#dateStart').val();
		var dateEnd = $('#dateEnd').val();
		// 日历
		$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev',
				center: 'title',
				right: 'next'
			},
			defaultDate: $('#dateStart').val(),
			lang: 'zh-cn',
			buttonIcons: false, // show the prev/next text
			weekNumbers: false,
			fixedWeekCount: false,
			editable: false,
			eventLimit: false // allow "more" link when too many events
		});


		var url = '/traffic/trafficPrice/findCalenderPrice.jhtml?dateStart='+dateStart+'&typeRriceId='+priceId+'&cIndex=1';

		if (priceId) {
			$.messager.progress({
				title:'温馨提示',
				text:'数据处理中,请耐心等待...'
			});
			$.post(url, function(result) {
				$('#calendar').fullCalendar('addEventSource', result);
				$.messager.progress("close");
			});
		}

		//富文本房型描述
		var changePolicy;
		KindEditor.ready(function(K) {
			changePolicy = K.create('#changePolicy', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowFileManager : true,
				filePostName: 'resource',
				items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline'],
				afterChange: function() {
					this.sync();
					var textCount = 120 - this.count('text');
					if (textCount >= 50){
						K("#text_count0").html(textCount);
						K("#text_count0").css("color", "green");
					} else if (textCount <= 50 && textCount > 0) {
						K("#text_count0").html(textCount);
						K("#text_count0").css("color", "red");
					} else {
						show_msg("改签政策内容过长，请重新编辑！");
					}
				},
				afterBlur: function() {
					this.sync();
				}
			});
		});

		//富文本房型退改规则
		var backPolicy;
		KindEditor.ready(function(K) {
			backPolicy = K.create('#backPolicy', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowFileManager : true,
				filePostName: 'resource',
				items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline'],
				afterChange: function() {
					this.sync();
					var textCount = 120 - this.count('text');
					if (textCount >= 50){
						K("#text_count1").html(textCount);
						K("#text_count1").css("color", "green");
					} else if (textCount <= 50 && textCount > 0) {
						K("#text_count1").html(textCount);
						K("#text_count1").css("color", "red");
					} else {
						show_msg("退票政策退改规则内容过长，请重新编辑！");
					}
				},
				afterBlur: function() {
					this.sync();
				}
			});
		});


	},
	// 初始化事件
	initListener : function() {
		// 选团周期
		$(':checkbox[name=weekday]').each(function(index, element) {
			$(this).change(function() {
				if (index === 0) {	// 如果是“天天发团”监听全选和取消全选
					var checked = $(this).attr('checked');
					if (checked) {
						$(':checkbox[name=weekday]').attr('checked', 'checked');
					} else {
						$(':checkbox[name=weekday]').removeAttr('checked');
					}
				} else {	// 否则，设置对应“天天发团”状态
					var checked = $(this).attr('checked'); 
					if (!checked) {
						$(':checkbox[name=weekday]').first().removeAttr('checked');
					}
				}
			});
		});
	},
	// 初始状态
	initStatus : function() {
		$(':checkbox[name=weekday]').attr('checked', 'checked');
		var lineInfo = parent.window.getIfrData('step1');
		$('#productId').val(lineInfo.productId);
	},
	// 添加 - 报价时间
	doAddPriceDate : function() {
		var price = $('#price').val();
		var marketPrice = $('#marketPrice').val();
		var cPrice = $("#cPrice").val();
		var inventory = $("#inventory").val();
		var flightTime = $("#flightTime").val();
		var leaveTimeStr = $("#leaveTime").val();
		var arriveTimeStr = $("#arriveTime").val();
		if (!price) {
			show_msg("请填写分销价");
			$('#price').focus();
			return ;
		}
		if (!marketPrice) {
			show_msg("请填写市场价");
			$('#marketPrice').focus();
			return ;
		}
		if (!cPrice) {
			show_msg("请填写C端加价");
			$('#cPrice').focus();
			return ;
		}
		if (!inventory) {
			show_msg("请填写库存");
			$('#inventory').focus();
			return ;
		}
		var startDateStr = $('#dateStart').val();
		var endDateStr = $('#dateEnd').val();
		var weeks = $(':checked[name=weekday]');
		var weekStr = "";
		for (var i = 0; i < weeks.length; i++) {
			weekStr = weekStr + weeks[i].value + ",";
		}
		addStep2.setEventSource(startDateStr,endDateStr,weekStr,price, marketPrice, cPrice, inventory, flightTime, leaveTimeStr, arriveTimeStr);
	},
	// 清除所有报价
	doClearPriceDate: function() {
		$("#dateSourceId").val("");
		$('#calendar').fullCalendar('removeEvents');
	},
	// 获取日历安排事项
	setEventSource: function(startDateStr, endDateStr, weekStr, price, marketPrice, cPrice, inventory, flightTime, leaveTimeStr, arriveTimeStr) {
		var startDate = TrafficUtil.stringToDate(startDateStr);
		var endDate = TrafficUtil.stringToDate(endDateStr);
		var startDateMon = startDate.getMonth();
		var endDateMon = endDate.getMonth();
		var offset = (endDateMon-startDateMon+12)%12+1;
		var date = startDate;
		var dateMon = startDateMon;
		for (var i = 1; i <= offset; i++) {
            var data = [];
            var tempId = [];
			while (date <= endDate) {
				var tempDateMon = date.getMonth();
				if (tempDateMon != dateMon) {	// 超过当前月，跳出循环后继续遍历
					dateMon = tempDateMon;
					break;
				} else {
					var dateDay = date.getDay();	// 星期几
					var dateStr = TrafficUtil.dateToString(date,'yyyy-MM-dd');
					if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内

						var leaveDateStr = TrafficUtil.dateToString(date,'yyyy-MM-dd') + " " + leaveTimeStr;
						var leaveDate = TrafficUtil.stringToDate(leaveDateStr);
						if (leaveTimeStr) {
							data.push(addStep2.buildLeaveTime(date, leaveDate));
						}
						var flightTimeMils = flightTime*60*1000;
						var leaveDateMils = leaveDate.getTime();
						var arriveDate = new Date(flightTimeMils + leaveDateMils);
						//var arriveDate = TrafficUtil.dateToString(TrafficUtil.dateToString(newDate, 'yyyy-MM-dd HH:mm:ss'),'MM-dd') + " " + leaveTimeStr;
						if (arriveTimeStr) {
							data.push(addStep2.buildArriveTime(date, arriveDate));
						}
						if (price) {	// 成人价
							data.push(addStep2.buildPrice(date, price));
						}
						if (marketPrice) {
							data.push(addStep2.buildMarketPrice(date, marketPrice));
						}
						if (cPrice) {
							data.push(addStep2.buildCprice(date, cPrice));
						}
						if (inventory) {
							data.push(addStep2.buildInventory(date, inventory));
						}
						if (tempId.indexOf(dateStr) <= -1) {
							tempId.push(dateStr);
						}
					}
					// 变量增加
					var dateTime = date.getTime() + 24*60*60*1000;	// 往前+1天
					date = new Date(dateTime);
				}
			}
            var filter = function(event) {
				return tempId.indexOf(event.start._i) > -1;
            };
            $('#calendar').fullCalendar('removeEvents', filter);
            $('#calendar').fullCalendar('addEventSource', data);
		}
	},
	// 设置开始时间
	buildLeaveTime : function(date, leaveDate) {
		var data = null;
		if (leaveDate) {	// 单房差
			var vid = "5"+date.getTime();
			data = {
				id:vid,
				leaveTime:TrafficUtil.dateToString(leaveDate, 'yyyy-MM-dd HH:mm:ss'),
				title:'始'+TrafficUtil.dateToString(leaveDate,'MM-dd HH:mm'),
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},

	// 设置开始时间
	buildArriveTime : function(date, arriveDate) {
		var data = null;
		if (arriveDate) {	// 单房差
			var vid = "6"+date.getTime();
			data = {
				id:vid,
				arriveTime:TrafficUtil.dateToString(arriveDate, 'yyyy-MM-dd HH:mm:ss'),
				title:'终'+TrafficUtil.dateToString(arriveDate,'MM-dd HH:mm'),
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},


	// 设置分销价
	buildPrice : function(date, price) {
		var data = null;
		if (price) {	// 单房差
			var vid = "1"+date.getTime();
			data = {
				id:vid,
				price:price,
				title:'a.价格：'+price,
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},

	// 设置市场价
	buildMarketPrice : function(date, marketPrice) {
		var data = null;
		if (marketPrice) {	// 单房差
			var vid = "2"+date.getTime();
			data = {
				id:vid,
				marketPrice:marketPrice,
				title:'b.市价：'+marketPrice,
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 设置C端加价
	buildCprice : function(date, cPrice) {
		var data = null;
		if (price) {	// 单房差
			var vid = "3"+date.getTime();
			data = {
				id:vid,
				cPrice:cPrice,
				title:'c.C端：'+cPrice,
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},

	// 库存
	buildInventory : function(date, inventory) {
		var data = null;
		if (inventory) {	// 单房差
			var vid = "4"+date.getTime();
			data = {
				id:vid,
				inventory:inventory,
				title:'d.库存：'+inventory,
				start:TrafficUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},


	// 下一步
	nextGuide:function(){


		// 保存表单
		$('#editForm').form('submit', {
			url : "/traffic/trafficPrice/saveTrafficPrice.jhtml",
			onSubmit : function() {

				var flag = addStep2.getDateSource();

				if (!flag) {
					show_msg("请完善价格日历信息。");
					return false;
				}

				var isValid = $(this).form('validate');
				if(isValid){
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
					var url = "/traffic/traffic/addStep21.jhtml?productId="+result.productId+"#loctop";
					window.location.href = url;
				}else{
					show_msg("保存酒店失败");
				}
			}
		});
	},

	getDateSource: function() {

		var htmlStr = "";
		var data = [];
		var res1 = $('#calendar').fullCalendar('clientEvents');
		for (var i = 0; i < res1.length; i++) {
			var m = res1[i].start;
			var obj = TrafficUtil.getByKey(data, 'start', m.format());
			if (obj) {	// 已经包含该元素
				if (res1[i].price) {
					obj.price = res1[i].price;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].cPrice) {
					obj.cPrice = res1[i].cPrice;
				}
				if (res1[i].leaveTime) {
					obj.leaveTime = res1[i].leaveTime;
				}
				if (res1[i].arriveTime) {
					obj.arriveTime = res1[i].arriveTime;
				}
			} else {
				obj = {};
				obj.start = m.format();
				if (res1[i].price) {
					obj.price = res1[i].price;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].cPrice) {
					obj.cPrice = res1[i].cPrice;
				}
				if (res1[i].leaveTime) {
					obj.leaveTime = res1[i].leaveTime;
				}
				if (res1[i].arriveTime) {
					obj.arriveTime = res1[i].arriveTime;
				}
				data.push(obj);
			}
		}
		if (data.length > 0) {
			var dataJson = JSON.stringify(data);
			$("#dateSourceId").val(dataJson);
			return true;
		} else {
			show_msg("请先完善价格日历数据！");
			$("#dateSourceId").val("");
			return false;
		}
	},

	// 返回价格列表
	doBackList : function() {
		var productId = $('#productId').val();
		var url = "/traffic/traffic/addStep21.jhtml?productId="+productId+"#loctop";
		window.location.href = url;
	}
};

// 返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
	addStep2.init();
});