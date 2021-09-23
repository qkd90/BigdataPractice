var editStep2 = {
	limitNum: 3000,	// 字数限制仅文本
	maxLimitNum: 4000,	// 字数限制包含html标签
	quoteDescK: null,
		
	init:function(){
		editStep2.initCalendar();
		editStep2.initCom();
		editStep2.initListener();
		editStep2.initStatus();
	},
	initCom: function() {
	},


	initCheckContract: function() {
		var url = "/contract/contract/isHasContract.jhtml";
		var flag = false;
		$.post(url, function(data) {
			if (data.isHas) {
				flag = true;
			} else {
				flag = false;
			}
		});
		return flag;
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


		var url = '/hotel/hotelRoomType/findTypePriceDate.jhtml?dateStart='+dateStart+'&typeRriceId='+priceId+'&cIndex=1';

		if (priceId) {
			$.messager.progress({
				title:'温馨提示',
				text:'数据处理中,请耐心等待...'
			});
			$.post(url, function(result) {
				$('#calendar').fullCalendar('addEventSource', result);
				$.messager.progress("close");
			});
		};


		//富文本房型描述
		var editorRoomDesc;
		KindEditor.ready(function(K) {
			editorRoomDesc = K.create('#roomDescription', {
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
						show_msg("民宿房型内容过长，请重新编辑！");
					}
				},
				afterBlur: function() {
					this.sync();
				}
			});
		});

		//富文本房型退改规则
		var editorRoomDesc;
		KindEditor.ready(function(K) {
			editorRoomDesc = K.create('#roomChangeRule', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowFileManager : true,
				filePostName: 'resource',
				items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline'],
				afterChange: function() {
					this.sync();
					//var textCount = 120 - this.count('text');
					//if (textCount >= 50){
					//	K("#text_count1").html(textCount);
					//	K("#text_count1").css("color", "green");
					//} else if (textCount <= 50 && textCount > 0) {
					//	K("#text_count1").html(textCount);
					//	K("#text_count1").css("color", "red");
					//} else {
					//	show_msg("民宿房型退改规则内容过长，请重新编辑！");
					//}
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
		//var lineInfo = parent.window.getIfrData('step1');
		//$('#productId').val(lineInfo.productId);
	},
	// 添加 - 报价时间
	doAddPriceDate : function() {

		var url = "/contract/contract/isHasContract.jhtml";
		$.post(url, {proId: $("#productId").val()}, function(data) {
			if (data.isHas) {
				var member = $('#discountPrice').val();		//销售价
				var cost = $('#cPrice').val();				//结算价
				var marketPrice = $("#marketPrice").val();
				var inventory = $("#inventory").val();
				if (!member) {
					show_msg("请填写销售价");
					$('#discountPrice').focus();
					return ;
				}
				if (!marketPrice) {
					show_msg("请填写市场价");
					$('#marketPrice').focus();
					return ;
				}
				if (!cost) {
					show_msg("请填写结算价");
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
				editStep2.setEventSource(startDateStr,endDateStr,weekStr, member, marketPrice, cost, inventory);
			} else {
				show_msg(data.reMsg);
				return ;
			}
		});

	},
	// 清除所有报价
	doClearPriceDate: function() {
		//$('#calendar1').fullCalendar('removeEvents');
		$("#dateSourceId").val("");
		$('#calendar').fullCalendar('removeEvents');
	},
	// 获取日历安排事项
	setEventSource: function(startDateStr, endDateStr, weekStr, member, marketPrice, cost, inventory) {
		var startDate = HotelUtil.stringToDate(startDateStr);
		var endDate = HotelUtil.stringToDate(endDateStr);
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
					var dateStr = HotelUtil.dateToString(date,'yyyy-MM-dd');
					if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内

						if (member) {	// 成人价
							data.push(editStep2.buildPrice(date, member));
						}
						if (marketPrice) {	// 成人价
							data.push(editStep2.buildMarketPrice(date, marketPrice));
						}
						if (cost){
							data.push(editStep2.buildCprice(date, cost));
						}
						if (inventory){
							data.push(editStep2.buildInventory(date, inventory));
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
	// 设置销售价
	buildPrice : function(date, member) {
		var data = null;
		if (member) {	// 单房差
			var vid = "1"+date.getTime();
			data = {
				id:vid,
				member:member,
				title:'a.销售：'+member,
				start:HotelUtil.dateToString(date,'yyyy-MM-dd')
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
				start:HotelUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 设置结算价
	buildCprice : function(date, cost) {
		var data = null;
		if (cost) {	// 单房差
			var vid = "3"+date.getTime();
			data = {
				id:vid,
				cost:cost,
				title:'c.结算：'+cost,
				start:HotelUtil.dateToString(date,'yyyy-MM-dd')
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
				start:HotelUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 下一步
	nextGuide:function(){




		// 保存表单
		$('#editForm').form('submit', {
			url : "/hotel/hotelRoomType/saveRoomPrice.jhtml",
			onSubmit : function() {

				var flag = true;
				editStep2.getDateSource();
				if (editStep2.initCheckContract()) {
					if ($("#dateSourceId").val().length <= 0) {
						flag = false;
					}
				}

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
					var url = "/hotel/hotel/editStep21.jhtml?productId="+result.productId+"#loctop";
					window.location.href = url;
				}else{
					show_msg("保存民宿失败");
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
			var obj = HotelUtil.getByKey(data, 'start', m.format());
			if (obj) {	// 已经包含该元素
				if (res1[i].member) {
					obj.member = res1[i].member;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].cost) {
					obj.cost = res1[i].cost;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
			} else {
				obj = {};
				obj.start = m.format();
				if (res1[i].member) {
					obj.member = res1[i].member;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].cost) {
					obj.cost = res1[i].cost;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				data.push(obj);
			}
		}
		if (data.length > 0) {
			var dataJson = JSON.stringify(data);
			$("#dateSourceId").val(dataJson);
		} else {
			$("#dateSourceId").val("");
		}

	},

	// 返回价格列表
	doBackList : function() {
		var productId = $('#productId').val();
		var url = "/hotel/hotel/editStep21.jhtml?productId="+productId+"#loctop";
		window.location.href = url;
	}
};

//// 返回本页面数据
//function getIfrData(){
//	var data = {};
//	return data;
//}

$(function(){
	editStep2.init();
});