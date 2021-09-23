$.fn.validate = function(param)
{
	var defaults = 
	{
		callback : function(msg){ alert(msg); },
		focus : true,
		error : true
	};
	var options = $.extend(defaults, param);
	
	var msgHead = $(this).attr("cn");
	if (isNull(msgHead))
	{
		msgHead = "";
	}
	
	// 非空验证
	if ("need" == $(this).attr("need"))
	{
		if (isNull($(this).val()))
		{
			options.callback(msgHead + "不能为空");
			focusMe($(this));
			return false;
		}
	}
	
	// 格式验证
	var fmt = $(this).attr("fmt");
	var iptVal = $(this).val();
	// 没有格式验证需求或者没有输入任何值
	if (isNull(fmt) || isNull(iptVal))
	{
		return true;
	}
	if ("mobile" == fmt)
	{
		if (!isMobile(iptVal))
		{
			options.callback("请输入正确的手机格式");
			focusMe($(this));
			return false;
		}
	}
	else if ("number" == fmt)
	{
		if (!isNumber(iptVal))
		{
			options.callback(msgHead + "只能为数字");
			focusMe($(this));
			return false;
		}
	}
	else if ("email" == fmt)
	{
		if (!isEmail(iptVal))
		{
			options.callback("请输入正确的邮箱地址");
			focusMe($(this));
			return false;
		}
	}
	else if ("date" == fmt)
	{
		if (!isDate(iptVal))
		{
			options.callback("请输入正确的日期");
			focusMe($(this));
			return false;
		}
	}
	else if ("datetime" == fmt)
	{
		if (!isDateTime(iptVal))
		{
			options.callback("请输入正确的日期");
			focusMe($(this));
			return false;
		}
	}
	else if ("time" == fmt)
	{
		if (!isTime(iptVal))
		{
			options.callback("请输入正确的时间");
			focusMe($(this));
			return false;
		}
	}
	
	function focusMe(me)
	{
		if (options.focus)
		{
			me.focus();
		}
		if (options.error)
		{
			throw new Error("validate failed!");
		}
	}
};

