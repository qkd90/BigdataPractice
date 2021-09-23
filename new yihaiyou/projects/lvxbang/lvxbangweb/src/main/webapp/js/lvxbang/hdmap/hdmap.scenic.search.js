$.fn.search = function(options)
{
	var defaults = 
	{
		target : "#J_result_list>ul",
		target_wrap : "#J_result_list",
		tplid : "",
		key : "str",
		url : "/hand-draw/ajax/sceniclistname?radom=" + new Date().getTime() + "&cityCode=" + editCfg.cityCode() + "&zoomLevel=" + (parseInt($("#map-min-level").val())+1),
		active_class : "active",
		select : function (src)
		{
			ipt.val(src.text());
			_hide_all();
			options.after_select(src.attr("data-id"), src.attr("lng"), src.attr("lat"));
		},
		after_select : function (dataid, lng, lat)
		{
			showMap(dataid, lng, lat);
		}
	};
	
	var ipt = $(this);
	options = $.extend(defaults, options);
	
	// 处理点击
	ipt.bind("click", function()
	{
		ipt.select();
		return false;
	});
	
	// 处理失去焦点事件
	ipt.bind("focus", function()
	{
		iptFocus = true;
	});
	
	$(document).bind("click", function()
	{
		_hide_all();
	});
	
	ipt.bind("focus keyup click", function()
	{
		if (ipt.val() == "")
		{
			_show_recommend();
		}
		else 
		{
			_show_search_result();
		}
	});
	
	ipt.bind("keyup", function(event)
	{
		// 不处理方向键
		if (event.keyCode >= 37 && event.keyCode <= 40)
		{
			return;
		}
		// 不处理回车键
		if (event.keyCode == 13)
		{
			return;
		}
		// 没有任何输入，不进行查询
		if (ipt.val() == "")
		{
			return;
		}
		
		// TODO 调用旅游景点搜索功能
		$.post(options.url, {str : ipt.val()}, function(result)
		{
			_dwar_result_list(result);
		},"json");
		
	});
	
	ipt.bind("keydown", function(event)
	{
		// 如果没输入任何内容，则忽略
		if (ipt.val() == "")
		{
			return;
		}
		
		var target = options.target;
		var activeLi = $(target).find("." + options.active_class);
		var newIndex;
		
		// 向上键
		if (event.keyCode == 38)
		{
			// 有选中的话
			if (activeLi.size() > 0)
			{
				newIndex = activeLi.index() - 1;
				// 当前已经是第一个了，则选中最后一个
				if (newIndex < 0)
				{
					newIndex = $(target).children().size() - 1;
				}
			}
			// 当前没有选中，则选中最后一个
			else 
			{
				newIndex = $(target).children().size() - 1;
			}
			
			$(target).children().removeClass(options.active_class);
			$(target).children().eq(newIndex).addClass(options.active_class);
			ipt.val($(target).children().eq(newIndex).text());
		}
		
		// 向下键
		if (event.keyCode == 40)
		{
			// 有选中的话
			if (activeLi.size() > 0)
			{
				newIndex = activeLi.index() + 1;
				// 当前已经是最后一个了，则选中第一个
				if (newIndex >= $(target).children().size())
				{
					newIndex = 0;
				}
			}
			// 当前没有选中，则选中第一个
			else 
			{
				newIndex = 0;
			}
			
			$(target).children().removeClass(options.active_class);
			$(target).children().eq(newIndex).addClass(options.active_class);
			ipt.val($(target).children().eq(newIndex).text());
		}
		
		// 回车键
		if (event.keyCode == 13)
		{
			if (activeLi.size() > 0)
			{
				options.select(activeLi);
				ipt.blur();
			}
			// 当前没有选择内容，则查询输入框输入的关键字是否能对应的到景点
			else
			{
				if (ipt.val())
				{
					$.post(options.url, { str : ipt.val() }, function(result)
					{
						if (result.success && result.data.length == 1)
						{
							options.after_select(result.data[0].scenicId, result.data[0].lng, result.data[0].lat);
						}
					},"json");
				}
			}
		}
	});
	
	function _show_recommend()
	{
		$(options.target_wrap).hide();
		$("#J_popup_city").show();
	}
	
	function _show_search_result()
	{
		$("#J_popup_city").hide();
		$(options.target_wrap).show();
	}
	
	function _dwar_result_list(result)
	{
		if (result.success && result.data != null && result.data.length > 0)
		{
			_show_search_result();
			// 清除现有内容
			$(options.target).html("");
			for (var i = 0; i < result.data.length; i++)
			{
				$(options.target).append(template(options.tplid, result.data[i]));
			}

			// 绑定点击事件
			$(options.target).children().bind("click", function()
			{
				options.select($(this));
			});
		}
	}
	
	function _hide_all()
	{
		$("#J_popup_city").hide();
		$(options.target_wrap).hide();
	}
};