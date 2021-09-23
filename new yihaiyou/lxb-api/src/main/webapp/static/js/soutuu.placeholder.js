/**
 * input提示框信息组件
 */

$.fn.placeholder = function (opt)
{
	// 针对不识别placeholder属性的浏览器
	if (!("placeholder" in document.createElement("input"))) 
	{ 
		var defaults = { tip_class : "" };
		
		var options = $.extend(defaults, opt);

		var $element = $(this);
		
		var placeholder = $element.attr("placeholder");

		if (placeholder) 
		{
		    // 获取文本框Id
		    var elementId = $element.attr("id");
		    
		    // 若文本框没有Id，随机分配一个Id名
		    if (!elementId) 
		    {
		        var now = new Date();
		        elementId = "lbl_placeholder" + now.getSeconds() + now.getMilliseconds();
		        $element.attr("id", elementId);
		    } 
		    
		    // 添加label标签，用于显示placeholder的值
		    var $label = $("<label>", 
		    {
		        html: $element.val() ? "" : placeholder,
		        "for": elementId,
		        "class": options.tip_class
		    }).insertAfter($element); 
		    
		    var _resetPlaceholder = function () 
		    {
		        if ($element.val()) 
		        { 
		        	$label.html(null); 
		        }
		        else 
		        {
		            $label.html(placeholder);
		        }
		    };
		    
		    // 绑定事件
		    $element.on("focus blur input keyup propertychange resetplaceholder", _resetPlaceholder); 
		  }
	}
};