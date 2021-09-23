/**
 * 日历控件
 */

$.calendar = function(opt)
{
	var defaults = 
	{
		type : "common",
		content_class : ".calendar_div",
		prev_class : ".js_prev",
		next_class : ".js_next",
		selectday_id : "#J_play-date",
		ticket_type : null,
		param_value : null,
		callback : function(){}
	};
	
	var options = $.extend(defaults, opt);
	
	if ($(options.content_class).find(".calendar_wrap").size() > 0)
	{
		$(options.content_class).show();
		return;
	}
	
	// 初始化使用的当天年月
	var nowDate = new Date();  
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1;
	var today = nowDate.getDate();
	
	var now_year = year;
	var now_month = month;
	
	// 普通日历
	if (options.type == "common")
	{
		_common_init(year, month);
	}
	// 带价格日历（每天价格相同）
	else if (options.type == "price")
	{
		_price_init(year, month);
	}
	
	$(options.content_class).click(function()
	{
		$(options.content_class).hide();
	});
	
	// 通用日历初始化
	function _common_init(_year, _month, cutflag)
	{
		$.get("/calendar/common", {year : _year, month : _month, select_day : $(options.selectday_id).val()}, function(result)
		{
			$(options.content_class).append(result);
			if (cutflag)
			{
				$(options.content_class).find(".calendar_wrap").eq(0).remove();
			}
			_common_fuc();
		});
	};

	// 价格日历初始化
	function _price_init(_year, _month, cutflag)
	{
		if (options.ticket_type == "qunaer")
		{
			$.get("/calendar/price/qunaer", {ticketId : options.param_value, year : _year, month : _month, select_day : $(options.selectday_id).val()}, function(result)
			{
				$(options.content_class).append(result);
				if (cutflag)
				{
					$(options.content_class).find(".calendar-prices-wrap").eq(0).remove();
				}
				_price_fuc();
			});
		}
		else if (options.ticket_type == "single")
		{
			$.get("/calendar/price/single", {price : options.param_value, year : _year, month : _month, select_day : $(options.selectday_id).val()}, function(result)
			{
				$(options.content_class).append(result);
				if (cutflag)
				{
					$(options.content_class).find(".calendar-prices-wrap").eq(0).remove();
				}
				_price_fuc();
			});
		}
	};
	
	var _common_fuc = function()
	{
		// 上个月
		$(options.prev_class).click(function()
		{
			event.stopPropagation();
			
			var myTable = getEvtTgt().parents(".calendar_wrap:first");
			if (myTable.find(options.prev_class).hasClass("disable"))
			{
				return;
			}
			
			if (now_month == 1)
			{
				now_year = now_year - 1; 
				now_month = 13;
			}
			now_month = now_month - 1;
			_common_init(now_year, now_month, true);
		});
		
		// 下个月
		$(options.next_class).click(function()
		{
			event.stopPropagation();
			
			if (now_month == 12)
			{
				now_year = now_year + 1; 
				now_month = 0;
			}
			now_month = now_month + 1;
			_common_init(now_year, now_month, true);
		});
		
		// 选择日期
		$(options.content_class).find(".calendar_day").click(function()
		{
			event.stopPropagation();
			
			if ($(this).hasClass("disable"))
			{
				return;
			}
			
			$(options.content_class).find(".calendar_day").removeClass("select");
			$(this).addClass("select");
			
			var day = $(this).find("em").html();
			if (day == "今天")
			{
				day = today;
			}
			else if (day == "明天")
			{
				day = today + 1;
			}
			else if (day == "后天")
			{
				day = today + 2;
			}
			
			var strTime= now_year + "-" + now_month + "-" + day; 
			var date= new Date(Date.parse(strTime.replace(/-/g, "/")));
			
			var chooseDate = 
			{
				strTime : strTime,
				date : date
			};
			options.callback(chooseDate);
			$(options.content_class).hide();
		});
	};
	
	var _price_fuc = function()
	{
		var price_type = $("#J_calendar_type").val();
		// 每天价格相同日历需重新请求日历
		if (price_type == "one")
		{
			// 上个月
			$(options.prev_class).click(function()
			{
				event.stopPropagation();
				
				var myTable = getEvtTgt().parents(".calendar_wrap:first");
				if (myTable.find(options.prev_class).hasClass("disable"))
				{
					return;
				}
				
				if (now_month == 1)
				{
					now_year = now_year - 1; 
					now_month = 13;
				}
				now_month = now_month - 1;
				_price_init(now_year, now_month, true);
			});
			
			// 下个月
			$(options.next_class).click(function()
			{
				event.stopPropagation();
				
				if (now_month == 12)
				{
					now_year = now_year + 1; 
					now_month = 0;
				}
				now_month = now_month + 1;
				_price_init(now_year, now_month, true);
			});
		}
		// 每天不同价格则不需要重新请求
		else if (price_type == "more")
		{
			// 上个月
			$(options.prev_class).click(function()
			{
				event.stopPropagation();
				
				var myTable = getEvtTgt().parents(".calendar_wrap:first");
				if (myTable.find(options.prev_class).hasClass("disable"))
				{
					return;
				}
				
				myTable.find(options.next_class).removeClass("disable");
				var prevTable = myTable.prev();
				if (prevTable.size() == 1)
				{
					prevTable.find(options.prev_class).addClass("disable");
				}
				
				$(".calendar_wrap").hide();
				prevTable.show();
				
				if (now_month == 1)
				{
					now_year = now_year - 1; 
					now_month = 13;
				}
				
				now_month = now_month-1;
				$("#J_calendar_year").val(now_year);
				$("#J_calendar_month").val(now_month);
			});
			
			// 下个月
			$(options.next_class).click(function()
			{
				event.stopPropagation();
				
				var myTable = getEvtTgt().parents(".calendar_wrap:first");
				if (myTable.find(options.next_class).hasClass("disable"))
				{
					return;
				}
				
				myTable.find(options.prev_class).removeClass("disable");
				var nextTable = myTable.next();
				if (nextTable.size() == 1)
				{
					nextTable.find(options.next_class).addClass("disable");
				}
				
				$(".calendar_wrap").hide();
				nextTable.show();
				
				if (now_month == 12)
				{
					now_year = now_year + 1; 
					now_month = 0;
				}
				now_month = now_month + 1;
				$("#J_calendar_year").val(now_year);
				$("#J_calendar_month").val(now_month);
			});
		}
		
		// 选择日期
		$(options.content_class).find(".calendar_day").click(function()
		{
			event.stopPropagation();
			
			if ($(this).hasClass("disable"))
			{
				return;
			}
			
			$(options.content_class).find(".calendar_day").removeClass("select");
			$(this).addClass("select");
			
			var day = $(this).find("em").html();
			if (day == "今天")
			{
				day = today;
			}
			else if (day == "明天")
			{
				day = today + 1;
			}
			else if (day == "后天")
			{
				day = today + 2;
			}
			
			var price = $(this).find("i>.cs_price").html();
			
			var strTime= now_year + "-" + now_month + "-" + day; 
			var date= new Date(Date.parse(strTime.replace(/-/g, "/")));
			
			var chooseDate = 
			{
				strTime : strTime,
				date : date,
				price : price
			};
			options.callback(chooseDate);
			$(options.content_class).hide();
		});
	};
};

