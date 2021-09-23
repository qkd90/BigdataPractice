/*TicketEditOffer*/
var TicketEditOffer = {
		initTicketEditOffer:function(){
			TicketEditOffer.initYuding();
			//TicketEditOffer.initComp();
			TicketEditOffer.initConf();
			TicketEditOffer.initAgent();
			TicketEditOffer.initCalender();
		},


	initCheckContract: function() {
		var url = "/contract/contract/isHasContract.jhtml";
		var flag = false;
		$.post(url, {proId: $("#ticketId").val()}, function(data) {
			if (data.isHas) {
				flag = true;
			} else {
				flag = false;
			}
		});
		return flag;
	},

		initAgent:function(){
			var flag = $("#isAgent").val();
			if(flag == "true"){
//				alert("aa");
				$("#jd_address").textbox("readonly",true);
				$.each($("input[name='type']"),function(i,perValue){
					perValue.disabled = true;
				});
				$.each($("input[name='weekday']"),function(i,perValue){
					perValue.disabled = true;
				});


				$("#sel_getTicket").combobox("readonly", true);
				$("#commissionPrice").numberbox("readonly", true);
				$("#discountPrice").numberbox("readonly", true);
				//$("#dateStart").css("readonly", "true");
				//$("#dateEnd").css("readonly", "true");

				$("#add").linkbutton('disable');
				$("#clear").linkbutton('disable');
				
				$("#add_pic_toEditor").hide();


				
			}
			
			
			
		},
		
		
		
		
		initConf:function(){
			var inputType = $("input[name='type']");
			var hidden_type = $("#hidden_type").val();
			$.each(inputType,function(i,perValue){
				if(perValue.value == hidden_type){
					perValue.checked = 'checked';
				}
			});
			
			
			var getTicket = $("#hidden_getTicket").val();
            if (getTicket && getTicket != "") {
                $("#sel_getTicket").combobox('setValue',getTicket);
            }

		},
		
		preGuide:function(){
			
			window.parent.$("#qryResult").datagrid("load", {});
			window.parent.$('#dd').dialog('close'); 
		},
		
		
		saveBefore:function(){
			var flag = true;
			var jd_address = $("#jd_address").textbox('getValue');
			var type = $("input[name='type']:checked").val();
			var sel_getTicket = $("#sel_getTicket").combobox("getValue");
			var kindYuding = $("#kindYuding").val();
			//var discountPrice = $("#discountPrice").numberbox("getValue");



			//if(discountPrice.length > 0){
			//	TicketEditOffer.doAddPriceDate();
			//}

			if(jd_address.length<=0){
				flag = false;
				show_msg("请填写门票类型名称！");
				return flag;
			} else if(type.length<=0){
				flag = false;
				show_msg("请选择票型！");
				return flag;
			} else if(sel_getTicket.length<=0){
				flag = false;
				show_msg("请选择取票方式！");
				return flag;
			} else if(kindYuding.length<=0){
				flag = false;
				show_msg("请完善预定须知！");
				return flag;
			} else {
				return flag;
			}
			
		},
		
		// 下一步
		nextGuide:function() {

			var isValid = TicketEditOffer.saveBefore();
			var flag = TicketEditOffer.getDataSource();

			var postData = {
				'ticketPrice.id':$("#ticketPriceId").val(),
				'ticketPrice.ticket.id' : $("#ticketId").val(),
				'ticketPrice.name':$("#jd_address").val(),
				'ticketPrice.type':$(':checked[name=type]').val(),
				'ticketPrice.orderKnow':$("#kindYuding").val(),
				'ticketPrice.discountPrice':$("#discountPrice").val(),
				'ticketPrice.commission':$("#commissionPrice").val(),
				'ticketPrice.getTicket':$("#sel_getTicket").combobox("getValue"),
				dataJson : $("#dateSourceId").val()

			};

			var url = "/ticket/ticketPrice/edit.jhtml";

			if (isValid) {
				flag = true;
				if (flag) {

					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
					$.post(
						url,
						postData,
						function(result) {
							if (result.success) {
								$.messager.progress("close");
								TicketUtil.buildTicket(result.ticketId);
								window.parent.$("#qryResult").datagrid("load", {});
								window.parent.$('#dd').dialog('close');
							} else {
								$.messager.progress("close");
								show_msg("添加失败！")
							}
						}
					);

				} else {
					$.messager.progress("close");
					show_msg("请完善价格日历数据！");
				}

			} else {
				$.messager.progress("close");
				show_msg("请完善价格类型基础信息。");
			}
		},


	getDataSource: function() {

		var data = [];
		var res1 = $('#calendar').fullCalendar('clientEvents');
		for (var i = 0; i < res1.length; i++) {
			var m = res1[i].start;
			var obj = TicketUtil.getByKey(data, 'huiDate', m.format());
			if (obj) {	// 已经包含该元素
				if (res1[i].priPrice) {
					obj.priPrice = res1[i].priPrice;
					obj.huiDate = m.format()
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				if (res1[i].price) {
					obj.price = res1[i].price;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
				}
			} else {
				obj = {};
				obj.huiDate = m.format();
				if (res1[i].priPrice) {
					obj.priPrice = res1[i].priPrice;
					obj.price = res1[i].price;
				}
				if (res1[i].inventory) {
					obj.inventory = res1[i].inventory;
				}
				if (res1[i].price) {
					obj.price = res1[i].price;
				}
				if (res1[i].marketPrice) {
					obj.marketPrice = res1[i].marketPrice;
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

	initCalender: function() {
		var ticketpriceId = $('#ticketPriceId').val();
		var dateStart = $('#dateStart').val();
		var dateEnd = $('#dateEnd').val();

		// 初始状态
		$(':checkbox[name=weekday]').attr('checked', 'checked');
		$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev',
				center: 'title',
				right: 'next'
			},
			defaultDate: $('#dateStart').val(),
			lang: 'zh-cn',
			editable: false,
			eventLimit: false, // allow "more" link when too many events
			weekNumbers: false,
			fixedWeekCount: false,
			droppable: false
		});

		if (ticketpriceId) {
			TicketEditOffer.setDateSource();
		}
	},

	setDateSource: function() { //设置价格日历表
		var url = "/ticket/ticketDateprice/findPriceDate.jhtml";
		var dateStart = $('#dateStart').val();
		var dateEnd = $('#dateEnd').val();
		var postData = {
			ticketpriceId : $('#ticketPriceId').val(),
			dateEnd : dateEnd,
			dateStart : dateStart
		};

		if ($('#ticketPriceId').val()) {
			$.messager.progress({
				title:'温馨提示',
				text:'加载数据中,请耐心等待...'
			});
			$.post(url,
				postData,
				function (result) {
					$('#calendar').fullCalendar('removeEvents');
					$('#calendar').fullCalendar('addEventSource', result);
					$.messager.progress("close");
				}
			);
		}
	},

	doAddPriceDate : function() {

		var url = "/contract/contract/isHasContract.jhtml";
		$.post(url, {proId: $("#ticketId").val()}, function(data) {
			if (!data.isHas) {
				show_msg(data.reMsg);
				return ;
			} else {
				var flag = $("#isAgent").val();
				if(flag != "true"){
					var priPrice = $('#discountPrice').val();
					var price = $('#commissionPrice').val();
					var marketPrice = $('#marketPrice').val();
					var inventory = $('#inventory').val();

					if (!priPrice) {
						show_msg("请填写销售价");
						$('#discountPrice').focus();
						return ;
					}

					if (!price) {
						show_msg("请填写结算价");
						$('#commissionPrice').focus();
						return ;
					}

					if (!marketPrice) {
						show_msg("请填写市场价");
						$('#marketPrice').focus();
						return ;
					}
					if (!inventory) {
						show_msg("请填写库存");
						$('#inventory').focus();
						return ;
					}

					var startDateStr = $('#dateStart').val();
					var dateEndStr = $('#dateEnd').val();
					var weeks = $(':checked[name=weekday]');
					var weekStr = "";
					for (var i = 0; i < weeks.length; i++) {
						weekStr = weekStr + weeks[i].value + ",";
					}
					if(weeks.length>0){
						TicketEditOffer.saveEventSource(startDateStr, dateEndStr, weekStr, priPrice, marketPrice, price, inventory);
					}else{
						show_msg("请选择要添加的周数！");
					}
				}else{
					show_msg("该门票价格属于代理类型，不可更改！");
				}
			}
		});

		/*if (!TicketEditOffer.initCheckContract()) {
			show_msg("请完善合同信息，才可维护价格信息！");
			return ;
		}*/



	},

	saveEventSource: function(startDateStr, dateEndStr, weekStr, priPrice, marketPrice, price, inventory) {
		var startDate = TicketUtil.stringToDate(startDateStr);
		var endDate = TicketUtil.stringToDate(dateEndStr);
		var startDateMon = startDate.getMonth();
		var endDateMon = endDate.getMonth();
		var offset = (endDateMon-startDateMon+12)%12+1;
		var date = startDate;
		var dateMon = startDateMon;
		var data = [];

		for (var i = 1; i <= offset; i++) {
			var dataSource = [];
			var tempId = [];
			while (date <= endDate) {
				var tempDateMon = date.getMonth();
				if (tempDateMon != dateMon) {	// 超过当前月，跳出循环后继续遍历
					dateMon = tempDateMon;
					break;
				} else {
					var dateDay = date.getDay();	// 星期几
					var dateStr = TicketUtil.dateToString(date,'yyyy-MM-dd');
					if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内
						if (priPrice) {	// 销售价
							dataSource.push(TicketEditOffer.buildDiscountPrice(date, priPrice));
						}
						if (marketPrice) {
							dataSource.push(TicketEditOffer.buildMarketPrice(date, marketPrice));
						}
						if (price) {	// 结算价
							dataSource.push(TicketEditOffer.buildRebate(date, price));
						}
						if (inventory) {
							dataSource.push(TicketEditOffer.buildInventory(date, inventory));
						}
						if (tempId.indexOf(dateStr) <= -1) {
							tempId.push(dateStr);
						}
					}
					// 变量增加
					var dateTime = date.getTime() + 24 * 60 * 60 * 1000;	// 往前+1天
					date = new Date(dateTime);
				}
			}
			var filter = function(event) {
				return tempId.indexOf(event.start._i) > -1;
			};
			$('#calendar').fullCalendar('removeEvents', filter);
			$('#calendar').fullCalendar('addEventSource', dataSource);

		}

	},

	// 市场价
	buildMarketPrice : function(date, marketPrice) {
		var data = null;
		if (marketPrice) {	// 市场价
			var vid = "3"+date.getTime();
			data = {
				id:vid,
				marketPrice:marketPrice,
				title:'c.市价:'+marketPrice,
				start:TicketUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},

	// 库存
	buildInventory : function(date, inventory) {
		var data = null;
		if (inventory) {	// 库存
			var vid = "4"+date.getTime();
			data = {
				id:vid,
				inventory:inventory,
				title:'d.库存:'+inventory,
				start:TicketUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 结算价
	buildRebate : function(date, price) {
		var data = null;
		if (price) {	// 结算价
			var vid = "2"+date.getTime();
			data = {
				id:vid,
				price:price,
				title:'b.结算 :'+price,
				start:TicketUtil.dateToString(date,'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 分销价
	buildDiscountPrice : function(date, priPrice) {
		var data = null;
		if (priPrice) {	// 销售价
			var vid = "1"+date.getTime();
			data = {
				id:vid,
				priPrice: priPrice,
				date: TicketUtil.dateToString(date, 'yyyy-MM-dd'),
				title: 'a.销售:' + priPrice,
				start: TicketUtil.dateToString(date, 'yyyy-MM-dd')
			};
		}
		return data;
	},
	// 清除所有报价
	doClearPriceDate: function() {

		var flag = $("#isAgent").val();
		if(flag != "true"){
			$('#calendar').fullCalendar('removeEvents');
			$("#dayPriceId").val("");
		}else{
			show_msg("该门票价格属于代理类型，不可清除！");
		}


	},
		/*// 获取日历安排事项
		setEventSource: function(startDateStr, endDateStr, weekStr, discountPrice, rebate) {
			
			var startDate = TicketUtil.stringToDate(startDateStr);
			var endDate = TicketUtil.stringToDate(endDateStr);
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
						if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内

							if (discountPrice) {	// 成人价
								var vid =  date.getTime();
								data.push({id:vid,discountPrice:discountPrice,rebate:rebate,title:'分销:'+discountPrice+"("+rebate+")",start:TicketUtil.dateToString(date,'yyyy-MM-dd')});
	                            tempId.push(vid);
							}
//							if (childPrice) {	// 儿童价
//								var vid = "2"+date.getTime();
//								data.push({id:vid,childPrice:childPrice,childRebate:childRebate,title:'儿童'+childPrice+"("+childRebate+")",start:TicketUtil.dateToString(date,'yyyy-MM-dd')});
//	                            tempId.push(vid);
//							}
						}
						// 变量增加
						var dateTime = date.getTime() + 24*60*60*1000;	// 往前+1天
						date = new Date(dateTime);
					}
				}
	            var filter = function(event) {
	                return tempId.indexOf(event._id) > -1;
	            };
	            $('#calendar').fullCalendar('removeEvents', filter);
	            $('#calendar').fullCalendar('addEventSource', data);
			}
		},*/
		initYuding:function(){

			var flag = $("#isAgent").val();

			if (flag == "true") {
				flag = true;
			} else {
				flag = false;
			}

			//富文本预订说明
			var editorYuding;
			KindEditor.ready(function(K) {
				editorYuding = K.create('#kindYuding', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					readonlyMode : flag,
					items : [ 'fontsize', 'forecolor', 'bold'],
					afterChange: function() { 
						this.sync(); 
						if(this.count('text')<=1000){
							K('#em_tuigai').html(1000-this.count('text'));
						}else{
							show_msg("字数超过限制，请重新编辑！");
						}
					}, 
					afterBlur: function() { 
						this.sync(); 
					}
				});
				K('#useTemplate').click(function(){
					var html = '<p>预定时间：比如“如需预订，您最晚要在游玩前1天23:30前下单，请尽早预订。”&nbsp;</p>';
					html += '<p>预定说明：比如“有效日期：2014年2月22日－3月31日（不含3月8、9日） 对象：女士”</p>';
					editorYuding.insertHtml(html);
				});
			});
		},
		
		//整周全选
		selectAll:function(){
			
			var allweek = $("#allweekId");
			var weekdays = $("input[name='weekday']");
			if(!allweek.prop("checked")){
//				alert(allweek.prop("checked"));
				$.each(weekdays,function(i,perValue){
					perValue.checked = false;
				});
			}else{
//				alert(allweek.prop("checked"));
				$.each(weekdays,function(i,perValue){
					perValue.checked = true;
				});
			}
		}
		
};

$(function(){
	TicketEditOffer.initTicketEditOffer();
//	TicketEditOffer.initStatus();
	
	$.extend($.fn.validatebox.defaults.rules, {    
	    maxPrice: {    
	        validator: function(value, param){    
	            return value.length >= param[0];    
	        },    
	        message: 'Please enter at least {0} characters.'   
	    }    
	});  

	$("#discountPrice").numberbox({
		
//		max:$("#jd_address").numberbox("getValue")
//		onChange:function(newValue,oldValue){
//			var jd_address = $("#marketPrice").numberbox("getValue");
//			alert("newValue="+newValue+",jd_address="+jd_address);
//			if(newValue>jd_address){
//				show_msg("分销价不能超过市场价，请重新填写！");
//			}
//			alert("newValue="+newValue+",oldValue="+oldValue);
//		}
	});
	
//	
//	$("#discountPrice").onChange(function() {
//		alert("aaa");
//	});
//	

	
});

