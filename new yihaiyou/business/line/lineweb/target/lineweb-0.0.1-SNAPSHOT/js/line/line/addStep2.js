var addStep2 = {
	limitNum: 6000,	// 字数限制仅文本
	maxLimitNum: 10000,	// 字数限制包含html标签
	//quoteDescK: null,
	//quoteNoDescK: null,
	dateStart: null,    // 价格日历开始时间
		
	init:function(){
		addStep2.dateStart = $('#dateStart').val();
		addStep2.initComp();
		addStep2.initListener();
		addStep2.initStatus();
	},	
	// 初始化控件
	initComp:function(){
		//费用包含说明
		KindEditor.ready(function(K) {
			var quoteDescK = K.create('#quoteContainDescK', {
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
					if (hasNum > addStep2.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,addStep2.limitNum);
						this.text(strValue);  
						show_msg("字数过长已被截取，请简化"); 
						//计算剩余字数
						$('textarea[name="quoteContainDesc"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="quoteContainDesc"]').next().children('.green-bold').html(addStep2.limitNum-hasNum);
					}
				}, 
				afterBlur: function() { 
					this.sync(); 
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});

		//费用不包含说明
		KindEditor.ready(function(K) {
			var quoteNoDescK = K.create('#quoteNoContainDescK', {
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
					if (hasNum > addStep2.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,addStep2.limitNum);
						this.text(strValue);
						show_msg("字数过长已被截取，请简化");
						//计算剩余字数
						$('textarea[name="quoteNoContainDesc"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="quoteNoContainDesc"]').next().children('.green-bold').html(addStep2.limitNum-hasNum);
					}
				},
				afterBlur: function() {
					this.sync();
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});

		// 自费项目
		KindEditor.ready(function(K) {
			var quoteOwnK = K.create('#quoteOwnK', {
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
					if (hasNum > addStep2.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,addStep2.limitNum);
						this.text(strValue);
						show_msg("字数过长已被截取，请简化");
						//计算剩余字数
						$('textarea[name="quoteOwn"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="quoteOwn"]').next().children('.green-bold').html(addStep2.limitNum-hasNum);
					}
				},
				afterBlur: function() {
					this.sync();
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});

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
			//selectable: true,
			eventLimit: false, // allow "more" link when too many events
			dayClick: function(date, jsEvent, view) {
				var dateStr = date.format() + "";
				if (dateStr < addStep2.dateStart) {    // 选择的时间在开始时间之前
					return ;
				} else {
					$('#dayPriceEditDg').dialog('setTitle', dateStr + '报价编辑');
					$('#iptDay').val(dateStr);
					$('#dayPriceEditDg').dialog('open');
				}
			},
			eventClick: function(calEvent, jsEvent, view) {
				var dateStr = calEvent.start._i;
				if (dateStr < addStep2.dateStart) {    // 选择的时间在开始时间之前
					return ;
				} else {
					$('#dayPriceEditDg').dialog('setTitle', dateStr + '报价编辑');
					$('#iptDay').val(dateStr);
					$('#dayPriceEditDg').dialog('open');
				}
			}
			//events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1'
		});


		var url = '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1';

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






		//// 日历
		//$('#calendar2').fullCalendar({
		//	theme: true,
		//	header: {
		//		left: '',
		//		center: 'title',
		//		right: ''
		//	},
		//	defaultDate: $('#dateEnd').val(),
		//	lang: 'zh-cn',
		//	buttonIcons: false, // show the prev/next text
		//	weekNumbers: false,
		//	fixedWeekCount: false,
		//	editable: true,
		//	//selectable: true,
		//	eventLimit: true // allow "more" link when too many events
		//	//events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateEnd='+dateEnd+'&linetypepriceId='+priceId+'&cIndex=2'
		//});
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
		//$('#quoteName').textbox('setValue',LineUtil.getCodeName('productAttr',lineInfo.productAttr));
	},
	// 添加 - 报价时间
	doAddPriceDate : function() {
		var discountPrice = $('#discountPrice').val();
		if (!discountPrice) {
			show_msg("请填写成人价信息");
			$('#discountPrice').focus();
			return ;
		}
		var marketPrice = $('#marketPrice').val();
		if (!marketPrice) {
			show_msg("请填写市场价信息");
			$('#marketPrice').focus();
			return ;
		}

		var inventory = $('#inventory').val();
		if (!inventory) {
			show_msg("请填写库存信息");
			$('#inventory').focus();
			return ;
		}

		var oasiaHotel = $("#oasiaHotel").val();	//单房差
		var rebate = $('#rebate').val();
		//if (!rebate) {
		//	show_msg("请填写成人价信息");
		//	$('#rebate').focus();
		//	return ;
		//}
		var childPrice = $('#childPrice').val();
		var childMarketPrice = $('#childMarketPrice').val();
		var childRebate = $('#childRebate').val();
		//if (!childRebate) {	// 默认佣金0
		//	childRebate = 0;
		//}
		//$('#calendar1').fullCalendar('removeEvents');
		var startDateStr = $('#dateStart').val();
		var endDateStr = $('#dateEnd').val();
		var weeks = $(':checked[name=weekday]');
		var weekStr = "";
		for (var i = 0; i < weeks.length; i++) {
			weekStr = weekStr + weeks[i].value + ",";
		}
		addStep2.setEventSource(startDateStr,endDateStr,weekStr,discountPrice,marketPrice,rebate,childPrice,childMarketPrice, childRebate, oasiaHotel, inventory);
	},
	// 清除所有报价
	doClearPriceDate: function() {
		//$('#calendar1').fullCalendar('removeEvents');
		$("#dateSourceId").val("");
		$('#calendar').fullCalendar('removeEvents');
	},
	// 获取日历安排事项
	setEventSource: function(startDateStr,endDateStr,weekStr,discountPrice, marketPrice, rebate, childPrice, childMarketPrice, childRebate, oasiaHotel, inventory) {
		var startDate = LineUtil.stringToDate(startDateStr);
		var endDate = LineUtil.stringToDate(endDateStr);
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
					var dateStr = LineUtil.dateToString(date,'yyyy-MM-dd');
					if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内
						var discountPriceData = addStep2.buildDiscountPrice(date, discountPrice);
						if (discountPriceData) {	// 成人价
							data.push(discountPriceData);
						}
						var marketPriceData = addStep2.buildMarketPrice(date, marketPrice);
						if (marketPriceData) {	// 成人市场价
							data.push(marketPriceData);
						}
						var cpriceData = addStep2.buildCprice(date, rebate);
						if (cpriceData) {	// C端加价
							data.push(cpriceData);
						}
						var childPriceData = addStep2.buildChildPrice(date, childPrice, childRebate);
						if (childPriceData) {	// 儿童价
							data.push(childPriceData);
						}
						var childMarketPriceData = addStep2.buildChildMarketPrice(date, childMarketPrice);
						if (childMarketPriceData) {	// 儿童市场价
							data.push(childMarketPriceData);
						}
						var childCpriceData = addStep2.buildChildCprice(date, childRebate);
						if (childCpriceData) {	// 儿童C价
							data.push(childCpriceData);
						}
						var oasiaHotelData = addStep2.buildOasiaHotel(date, oasiaHotel);
						if (oasiaHotelData) {	// 单房差
							data.push(oasiaHotelData);
						}
						var inventoryData = addStep2.buildInventory(date, inventory);
						if (inventoryData) {	// 库存
							data.push(inventoryData);
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
	// 设置线路报价时间
	setTypePriceDate: function() {
		var htmlStr = "";
		var data = [];
		var res1 = $('#calendar').fullCalendar('clientEvents');
		for (var i = 0; i < res1.length; i++) {
			var m = res1[i].start;
			var obj = LineUtil.getByKey(data, 'start', m.format());
			if (obj) {	// 已经包含该元素
				if (res1[i].discountPrice) {
					obj.discountPrice = res1[i].discountPrice;
					obj.rebate = res1[i].rebate;
				}
				if (res1[i].childPrice) {
					obj.childPrice = res1[i].childPrice;
					obj.childRebate = res1[i].childRebate;
				}
			} else {
				obj = {};
				obj.start = m.format();
				if (res1[i].discountPrice) {
					obj.discountPrice = res1[i].discountPrice;
					obj.rebate = res1[i].rebate;
				}
				if (res1[i].childPrice) {
					obj.childPrice = res1[i].childPrice;
					obj.childRebate = res1[i].childRebate;
				}
				data.push(obj);
			}
		}
		//var res2 = $('#calendar2').fullCalendar('clientEvents');
		//for (var i = 0; i < res2.length; i++) {
		//	var m = res2[i].start;
		//	var obj = LineUtil.getByKey(data, 'start', m.format());
		//	if (obj) {	// 已经包含该元素
		//		if (res2[i].discountPrice) {
		//			obj.discountPrice = res2[i].discountPrice;
		//			obj.rebate = res2[i].rebate;
		//		}
		//		if (res2[i].childPrice) {
		//			obj.childPrice = res2[i].childPrice;
		//			obj.childRebate = res2[i].childRebate;
		//		}
		//	} else {
		//		obj = {};
		//		obj.start = m.format();
		//		if (res2[i].discountPrice) {
		//			obj.discountPrice = res2[i].discountPrice;
		//			obj.rebate = res2[i].rebate;
		//		}
		//		if (res2[i].childPrice) {
		//			obj.childPrice = res2[i].childPrice;
		//			obj.childRebate = res2[i].childRebate;
		//		}
		//		data.push(obj);
		//	}
		//}
		if (data.length <= 0) {
			return false;
		}
		// 设置隐藏域
		for (var i = 0; i < data.length; i++) {
			htmlStr = htmlStr+'<input name="typePriceDate['+i+'].start" value="'+data[i].start+' "type="hidden"/>';
			if (data[i].discountPrice) {
				htmlStr = htmlStr+'<input name="typePriceDate['+i+'].discountPrice" value="'+data[i].discountPrice+'" type="hidden"/>';
				htmlStr = htmlStr+'<input name="typePriceDate['+i+'].rebate" value="'+data[i].rebate+'" type="hidden"/>';
			}
			if (data[i].childPrice) {
				htmlStr = htmlStr+'<input name="typePriceDate['+i+'].childPrice" value="'+data[i].childPrice+'" type="hidden"/>';
				htmlStr = htmlStr+'<input name="typePriceDate['+i+'].childRebate" value="'+data[i].childRebate+'" type="hidden"/>';
			}
		}
		$('#typePriceDate').html(htmlStr);
	},
	// 下一步
	nextGuide:function(){

		// 保存表单
		$('#editForm').form('submit', {
			url : "/line/linetypeprice/saveLinePrice.jhtml",
			onSubmit : function() {
				// 费用说明 字段验证 
				var quoteContainDesc = $('textarea[name="quoteContainDesc"]').val();
				if (!quoteContainDesc) {
					show_msg("费用包含说明不能为空");
					return false;
				} else if (quoteContainDesc.length > addStep2.maxLimitNum) {
					show_msg("费用包含说明html标签过多，请简化");
					return false;
				}

				// 费用说明 字段验证
				var quoteNoContainDesc = $('textarea[name="quoteNoContainDesc"]').val();
				if (!quoteNoContainDesc) {
					//show_msg("费用不包含说明不能为空");
					//return false;
				} else if (quoteNoContainDesc.length > addStep2.maxLimitNum) {
					show_msg("费用不包含说明html标签过多，请简化");
					return false;
				}

				// 自费项目 字段验证
				var quoteOwn = $('textarea[name="quoteOwn"]').val();
				if (!quoteOwn) {
					//show_msg("费用不包含说明不能为空");
					//return false;
				} else if (quoteOwn.length > addStep2.maxLimitNum) {
					show_msg("自费项目说明html标签过多，请简化");
					return false;
				}

				var flag = addStep2.getDateSource();

				if (!flag) {
					show_msg("请完善价格日历信息。");
					return false;
				}

				var isValid = $(this).form('validate');
				if(isValid){
					//isValid = addStep2.setTypePriceDate();
					//if (!isValid) {
					//	show_msg("请填写报价");
					//	return isValid;
					//}
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
					$('#priceId').val(result.id);
					var productId = $('#productId').val();
					var url = "/line/line/addStep21.jhtml?productId="+productId+"#loctop";
					window.location.href = url;
				}else{
					show_msg("保存线路失败");
				}
			}
		});
	},
	// 更新单天价格日历
	updateDayPrice : function() {
		var dateStr = $('#iptDay').val();
		var discountPrice = $('#iptDiscountPrice').val();
		var marketPrice = $('#iptMarketPrice').val();
		var rebate = $('#iptRebate').val();
		var childPrice = $('#iptChildPrice').val();
		var childMarketPrice = $('#iptChildMarketPrice').val();
		var childRebate = $('#iptChildRebate').val();
		var inventory = $('#iptInventory').val();
		var oasiaHotel = $('#iptOasiaHotel').val();
		if (!discountPrice) {
			show_msg("请填写成人价信息");
			$('#iptDiscountPrice').focus();
			return ;
		}
		if (!marketPrice) {
			show_msg("请填写市场价信息");
			$('#iptMarketPrice').focus();
			return ;
		}
		if (!inventory) {
			show_msg("请填写库存信息");
			$('#iptInventory').focus();
			return ;
		}
		if (dateStr) {
			var data = [];
			var date = LineUtil.stringToDate(dateStr);
			var discountPriceData = editStep2.buildDiscountPrice(date, discountPrice);
			if (discountPriceData) {	// 成人价
				data.push(discountPriceData);
			}
			var marketPriceData = editStep2.buildMarketPrice(date, marketPrice);
			if (marketPriceData) {	// 市场价
				data.push(marketPriceData);
			}
			var cpriceData = editStep2.buildCprice(date, rebate);
			if (cpriceData) {	// 市场价
				data.push(cpriceData);
			}
			var childPriceData = editStep2.buildChildPrice(date, childPrice, childRebate);
			if (childPriceData) {	// 儿童价
				data.push(childPriceData);
			}
			var childMarketPriceData = editStep2.buildChildMarketPrice(date, childMarketPrice);
			if (childMarketPriceData) {	// 市场价
				data.push(childMarketPriceData);
			}
			var childCpriceData = editStep2.buildChildCprice(date, childRebate);
			if (childCpriceData) {	// 市场价
				data.push(childCpriceData);
			}
			var oasiaHotelData = addStep2.buildOasiaHotel(date, oasiaHotel);
			if (oasiaHotelData) {	// 单房差
				data.push(oasiaHotelData);
			}
			var inventoryData = addStep2.buildInventory(date, inventory);
			if (inventoryData) {	// 库存
				data.push(inventoryData);
			}
			var filter = function(event) {
				return event.start._i == dateStr;
			};
			$('#calendar').fullCalendar('removeEvents', filter);
			$('#calendar').fullCalendar('addEventSource', data);
			$('#dayPriceEditDg').dialog('close');
			// 清空页面值
			$('#iptDiscountPrice').numberbox('setValue', '');
			$('#iptMarketPrice').numberbox('setValue', '');
			$('#iptRebate').numberbox('setValue', '');
			$('#iptChildPrice').numberbox('setValue', '');
			$('#iptChildMarketPrice').numberbox('setValue', '');
			$('#iptChildRebate').numberbox('setValue', '');
			$('#iptOasiaHotel').numberbox('setValue', '');
			$('#iptInventory').numberbox('setValue', '');
		}
	},
	// 更新单天价格日历
	clearDayPrice : function() {
		var dateStr = $('#iptDay').val();
		if (dateStr) {
			var filter = function(event) {
				return event.start._i == dateStr;
			};
			$('#calendar').fullCalendar('removeEvents', filter);
			$('#dayPriceEditDg').dialog('close');
			// 清空页面值
			$('#iptDiscountPrice').numberbox('setValue', '');
			$('#iptMarketPrice').numberbox('setValue', '');
			$('#iptRebate').numberbox('setValue', '');
			$('#iptChildPrice').numberbox('setValue', '');
			$('#iptChildMarketPrice').numberbox('setValue', '');
			$('#iptChildRebate').numberbox('setValue', '');
			$('#iptOasiaHotel').numberbox('setValue', '');
			$('#iptInventory').numberbox('setValue', '');
		}
	},
	// 成人价
	buildDiscountPrice : function(date, discountPrice, rebate) {
		var data = null;
		if (discountPrice) {	// 成人价
			var vid = "1" + date.getTime();
			var title = 'a.成人' + discountPrice;
			//if (rebate) {
			//	title = title + "(" + rebate + ")";
			//} else {
			//	rebate = null;
			//}
			data = {
				id: vid,
				discountPrice: discountPrice,
				rebate: rebate,
				title: title,
				start: LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 成人价
	buildMarketPrice : function(date, marketPrice) {
		var data = null;
		if (marketPrice) {	// 成人价
			var vid = "2" + date.getTime();
			var title = 'b.成人市价' + marketPrice;
			data = {
				id: vid,
				marketPrice: marketPrice,
				title: title,
				start: LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	buildCprice : function(date, rebate) {
		var data = null;
		if (rebate) {	// 成人价
			var vid = "2" + date.getTime();
			var title = 'b.成人C价' + rebate;
			data = {
				id: vid,
				rebate: rebate,
				title: title,
				start: LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 儿童价
	buildChildPrice : function(date, childPrice) {
		var data = null;
		if (childPrice) {	// 儿童价
			var vid = "2"+date.getTime();
			var title = 'b.儿童' + childPrice;
			//if (childRebate) {
			//	title = title + "(" + childRebate + ")";
			//} else {
			//	childRebate = null;
			//}
			data = {
				id:vid,
				childPrice:childPrice,
				title:title,
				start:LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 儿童市场价
	buildChildMarketPrice : function(date, childMarketPrice) {
		var data = null;
		if (childMarketPrice) {	// 成人价
			var vid = "5" + date.getTime();
			var title = 'e.儿童市价' + childMarketPrice;
			data = {
				id: vid,
				childMarketPrice: childMarketPrice,
				title: title,
				start: LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	buildChildCprice : function(date, childRebate) {
		var data = null;
		if (childRebate) {	// 成人价
			var vid = "6" + date.getTime();
			var title = 'f.儿童C价' + childRebate;
			data = {
				id: vid,
				childRebate: childRebate,
				title: title,
				start: LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 单房差
	buildOasiaHotel : function(date, oasiaHotel) {
		var data = null;
		if (oasiaHotel) {	// 单房差
			var vid = "3"+date.getTime();
			data = {
				id:vid,
				oasiaHotel:oasiaHotel,
				title:'c.单房差'+oasiaHotel,
				start:LineUtil.dateToString(date,'yyyy-MM-dd')
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
				title:'d.库存'+inventory,
				start:LineUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},

	getDateSource: function() {

		var htmlStr = "";
		var data = [];
		var res1 = $('#calendar').fullCalendar('clientEvents');
		for (var i = 0; i < res1.length; i++) {
			var m = res1[i].start;
			var obj = LineUtil.getByKey(data, 'start', m.format());
			if (obj) {	// 已经包含该元素
				if (res1[i].discountPrice) {
					obj.discountPrice = res1[i].discountPrice;
				}
				if (res1[i].rebate) {
					obj.rebate = res1[i].rebate;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].childPrice) {
					obj.childPrice = res1[i].childPrice;
				}
				if (res1[i].childMarketPrice) {
					obj.childMarketPrice = res1[i].childMarketPrice;
				}
				if (res1[i].childRebate) {
					obj.childRebate = res1[i].childRebate;
				}
				if (res1[i].oasiaHotel) {
					obj.oasiaHotel = res1[i].oasiaHotel;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
			} else {
				obj = {};
				obj.start = m.format();
				if (res1[i].discountPrice) {
					obj.discountPrice = res1[i].discountPrice;
				}
				if (res1[i].rebate) {
					obj.rebate = res1[i].rebate;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
				if (res1[i].childPrice) {
					obj.childPrice = res1[i].childPrice;
				}
				if (res1[i].childMarketPrice) {
					obj.childMarketPrice = res1[i].childMarketPrice;
				}
				if (res1[i].childRebate) {
					obj.childRebate = res1[i].childRebate;
				}
				if (res1[i].oasiaHotel) {
					obj.oasiaHotel = res1[i].oasiaHotel;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				data.push(obj);
			}
		}
        //
		//return data;
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
		var url = "/line/line/addStep21.jhtml?productId="+productId+"#loctop";
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