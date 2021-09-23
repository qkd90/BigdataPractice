$.fn.hScroll = function(options)
{
	var defaults = 
	{
		transitionMove: "-webkit-transform 0ms cubic-bezier(0.33, 0.66, 0.66, 1)",
		transitionAni : "-webkit-transform #timems ease",
		onTouchEnd : null,
		calconce : true,
		scrollTo : function(targetX, time)
		{
			if (time)
			{
				container.css("-webkit-transition", options.transitionAni.replace("#time", time));
			}
			else
			{
				container.css("-webkit-transition", options.transitionMove);
			}
			
			container.css("-webkit-transform", "translate3d(" + targetX + "px, 0, 0)");
		}
	};
	options = $.extend(defaults, options);
	
	var touch = {};
	var me = $(this);
	var container = me.children().eq(0);
	var cw = container.width() - $(window).width();
	container.css("-webkit-transition", options.transitionMove);
	container.css("-webkit-transform", "translate3d(0, 0, 0)");
	
	me.unbind("touchstart").bind("touchstart", function(e)
	{
		e = e.originalEvent;
		touch = {};
		touch.startY = e.targetTouches[0].pageY;
		touch.startX = e.targetTouches[0].pageX;
		touch.lastX = e.targetTouches[0].pageX;
		touch.startTime = new Date().getTime();
		container.css("-webkit-transition", options.transitionMove);
	});
	me.unbind("touchmove").bind("touchmove", function(e)
	{
		e = e.originalEvent;
		var moveY = Math.abs(e.targetTouches[0].pageY - touch.startY);
		var moveX = Math.abs(e.targetTouches[0].pageX - touch.startX);
		// 确定滚动方向, 1代表纵向, 2代表横向
		if (!touch.direction)
		{
			if (moveY >= moveX)
			{
				touch.direction = 1;
			}
			else
			{
				touch.direction = 2;
			}
		}
		
		// 如果方向是横向，使控件横向调整位置，并阻止默认的事件处理
		if (touch.direction == 2)
		{
//			$("#header .pageTitle").html("touch.direction == 2");
			var targetX = container.position().left + e.targetTouches[0].pageX - touch.lastX;
//			$("#header .pageTitle").html(e.targetTouches[0].pageX - touch.lastX);
//			console.log(e.targetTouches[0].pageX - touch.lastX);
			// 限制滚动范围
			if (!options.calconce)
			{
				cw = container.width() - $(window).width();
			}
			if (targetX <= 0 && targetX >= -cw)
			{
				options.scrollTo(targetX);
			}
//			options.scrollTo(targetX);
//			console.log(targetX);
			e.preventDefault();
		}
		// 如果方向是纵向，则不处理
		else
		{
			// 纵向是还要考虑先纵向在转横向的场景，这种情况需要阻止横向滚动，但是如果使用3D变换而不是使用overflow，则可以避免这个事件的处理
		}
		touch.lastX = e.targetTouches[0].pageX;
		
	});
	
	me.unbind("touchend").bind("touchend", function()
	{
		touch.endTime = new Date().getTime();
		if (options.onTouchEnd)
		{
			options.onTouchEnd(touch);
		}
	});
	
	return options;
};