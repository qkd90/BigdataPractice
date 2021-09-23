$.fn.ajaxsubmit = function(opt)
{
	var defaults = 
	{
		autoDisable : true,
		autoEnable : true,
		getdata : function(){},
		url : "",
		disableClass : "disable",
		beforesave : function(){ return true; },
		onsuccess : function(){}
	};
	
	var opt = $.extend(defaults, opt);
	
	var that = $(this);
	
	that.click(function()
	{
		submitForm();
	});
	
	function submitForm()
	{
		if (!opt.beforesave())
		{
			return;
		}
		
		if (opt.autoDisable)
		{
			that.unbind("click");
			that.addClass(opt.disableClass);
		}
		
		$.post(opt.url, opt.getdata(), function(result)
		{
			if (opt.autoEnable)
			{
				that.click(function(){ submitForm(); });
				that.removeClass(opt.disableClass);
			}
			
			opt.onsuccess(result);
		});
	}
	
}