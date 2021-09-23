$.fn.tab = function(options)
{
	var defaults = 
	{
		afterHandler : function(){},
		handleClick : false,
		switchTo : function(dict, idx, time)
		{
			myscroll.scrollTo(-idx * cntWidth, time);
	        me.find(".ui-tab-nav li").removeClass("active");
	        me.find(".ui-tab-nav li").eq(idx).addClass("active");
	        if (time)
	        {
	        	window.setTimeout(function(){ options.afterHandler(idx, dict); }, time);
	        }
	        else
	        {
	        	options.afterHandler(idx, dict);
	        }
		}
	};
	
	options = $.extend(defaults, options);
	
    var me = $(this);
    var cntCounts = me.find(".tab-cnt").size();
    me.find(".tab-cnt-panel").width( cntCounts * 100 + '%' );
    me.find(".tab-cnt").width( Math.floor(100000000 / cntCounts) / 1000000 + '%');
    var cntWidth = me.find(".tab-cnt").width();


    var scrollCont = me.find(".tab-viewport");
    var cntPanel = me.find(".tab-cnt-panel");
    me.find(".ui-tab-nav li").on("click", function(e) {
    	options.switchTo($(this).index(), 400);
    });
    me.find(".ui-tab-nav li").eq(0).addClass("active");
   
    var myscroll = scrollCont.hScroll({onTouchEnd : function(touch)
    {
    	var newpos = Math.abs(cntPanel.position().left);
    	var tabIdx = parseInt(newpos / cntWidth);
    	var tabFix = (newpos % cntWidth) > (cntWidth / 2) ? 1 : 0;
    	var touchTime = touch.endTime - touch.startTime;
    	var moveX = Math.abs( touch.lastX - touch.startX);
    	
    	if (touchTime < 300 && 15 < moveX)
    	{
    		if (touch.startX >= touch.lastX)
    		{
    			tabFix = 1;
    		}
    		else
    		{
    			tabFix = 0;
    		}
    	}
    	var tgtIdx = tabIdx + tabFix;
    	
    	if (newpos % cntWidth != 0)
    	{
    		options.switchTo(tabFix, tgtIdx, 400);
    	}
    	
    }});
    
    return options;
    

//    var myscroll = new iScroll(scrollCont.get(0), {useTransition: true, handleClick: options.handleClick, hScrollbar : false, bounce : false, momentum : false, onScrollEnd : function ()
//    {
//        var newpos = Math.abs(cntPanel.position().left);
//        var tabIdx = parseInt(newpos / cntWidth);
//        var tabFix = (newpos % cntWidth) > (cntWidth / 2) ? 1 : 0;
//
//        var tgtIdx = tabIdx + tabFix;
//        if (newpos % cntWidth != 0)
//        {
//            myscroll.scrollTo(-tgtIdx * cntWidth, 0, 400);
//        }
//        // 滚动已经结束
//        else
//        {
//        	me.find(".ui-tab-nav li").removeClass("active");
//            me.find(".ui-tab-nav li").eq(tgtIdx).addClass("active");
//        	options.afterHandler(tgtIdx);
//        }
//    }});
};