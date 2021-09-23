$.confirm = function(options)
{
    var defaults = 
    {
    	ctn : '<div id="popup-wrap"><div class="pop-title green"></div><div class="pop-content"><p class="label-wrap ui-confirm-msg"></p></div><div class="pop-footer"><a class="ui-confirm-cancel" href="javascript:;">取消</a><a class="ui-confirm-ok" href="javascript:;">确定</a></div></div>',
    	title : "旅行帮",
    	msg : "",
    	initFn : null 
    };
    options = $.extend(defaults, options);
    	
    var _cfm = $(options.ctn);
    	
    _cfm.find(".pop-title").html(options.title);
    _cfm.find(".ui-confirm-msg").html(options.msg);
    _cfm.find(".ui-confirm-cancel").click(function()
    {
    	$("#__ui-dialog").remove();
    });
   
    _cfm.find(".ui-confirm-ok").click(function()
    {
    	if (typeof options.initFn == 'function')
    	{ 
    		 options.initFn.call(this);
    	}
    	$("#__ui-dialog").remove();
    });
    	
    $.dialog({ width : "260px", height : "auto", cnt : _cfm });
};

$.dialog = function(options)
{
	var defaults = 
	{
		width : "30em",
		height : "15em",
		wrap : "<div id='__ui-dialog'><div class='ui-dialog-mask'></div><div class='ui-dialog-panel'><div class='ui-dialog-wrap'></div></div></div>",
		cnt : "",
		removeDlg : function(){ $("#__ui-dialog").remove(); }
	};
	
	options = $.extend(defaults, options);
	options.removeDlg();
	var _dlg = $(options.wrap);
	var _dlgpanel = _dlg.find(".ui-dialog-panel");
	_dlgpanel.find(".ui-dialog-wrap").append(options.cnt);
	_dlgpanel.css("width", options.width).css("height", options.height);
	_dlg.find(".ui-dialog-mask").bind("click", function()
	{
		options.removeDlg();
	});
	
	$("body").append(_dlg);
	
	return options;
};

$.tips = function(msg)
{
	var tipdiv = $("#__ui-tips");
	if (tipdiv.size() > 0)
	{
		tipdiv.remove();
	}
	
	tipdiv = $("<div id='__ui-tips'></div>");
	tipdiv.append(msg);
	$("body").append(tipdiv);
	$("#__ui-tips").css("margin-left", 0 - $("#__ui-tips").width()/2);
	window.setTimeout(function()
	{
		$("#__ui-tips").remove();
	}, 2000);
};

$.showLoading = function()
{
	// 如果当前页面以及被阻塞，就不做任何处理
	if ($("#__ui-loading").size() > 0)
	{
		return;
	}
	
//	lddiv = $("<div id='__ui-loading'><div class='loading-mask'></div><div class='loading-bar'><div id='floatingCirclesG'><div class='f_circleG' id='frotateG_01'></div><div class='f_circleG' id='frotateG_02'></div><div class='f_circleG' id='frotateG_03'></div><div class='f_circleG' id='frotateG_04'></div><div class='f_circleG' id='frotateG_05'></div><div class='f_circleG' id='frotateG_06'></div><div class='f_circleG' id='frotateG_07'></div><div class='f_circleG' id='frotateG_08'></div></div></div></div>");
	lddiv = $("<div id='__ui-loading'><div class='loading-mask'></div><div class='loading-bar'></div></div>");
	
	$("body").append(lddiv);
	// 阻止滚动事件
	$("body").bind("touchmove", function(e)
	{
		e.preventDefault();
	});
};

$.hideLoading = function()
{
	$("#__ui-loading").remove();
	$("body").unbind("touchmove");
};

$.goBack = function()
{
	$.hideLoading();
    var p = _history.pop();
	var hash = window.location.hash;
	if (hash && hash != "#main")
	{
		
		window.history.go(-1);
	}
	else
	{
		
		if (!_willquit)
		{
			$.ui.tips("在按一次退出应用程序");
			_willquit = true;
		}
		else
		{
			// 退出APP
			navigator.app.exitApp();
		}
	}
};

$.loading = function(msg)
{
	var loaddiv = $("<div class='loading-ui'><i></i></div>");
	loaddiv.append("<p>" + msg + "</p>");
	$("body").append(loaddiv);
};
