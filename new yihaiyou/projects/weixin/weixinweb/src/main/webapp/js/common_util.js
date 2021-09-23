/**
 * 获取url参数
 * @param name 参数名
 * @return
 */
function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null; //返回参数值
}
/**
 * 验证是否是数字
 * @param strNumber
 * @return
 */
function isNumber(strNumber) {
    var pattern = /^\d*(?:\.\d{0,8})?$/;
    var m = strNumber.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}
/**
 * 验证是否为身份证
 */
function isCardNo(strCard)  
{  
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   //var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
   var pattern = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
   var m = strCard.match(pattern);
   if (m == null) {
       return false;
   }
   return true;
}  
/**
 * 验证是否是手机号码
 * @param strPhone
 * @return
 */
function isMobile(strPhone) {
    var pattern = /^1[3,5,8][0-9]{9}$/;
    var m = strPhone.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}

/**
 * 验证是否为中文
 */
function isChn(str)
{ 
	var reg = /^[\u4E00-\u9FA5]+$/; 
	if(!reg.test(str))
	{ 
		return false; 
	} 
	return true; 
} 

/**
 * 验证是否HH:mm:ss格式的时间
 * @param strDate
 * @return
 */
function isTime(strDate) {
    var pattern = /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var m = strDate.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}
/**
 * 验证是否yyyy-MM-dd格式的日期
 * @param strDate
 * @return
 */
function isDate(strDate) {
    var pattern = /^(\d{4})-(\d{2})-(\d{2})$/;
    var m = strDate.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}
/**
 * 验证是否yyyy-MM-dd HH:mm:ss格式的日期
 * @param strDate
 * @return
 */
function isDateTime(strDate) {
    var pattern = /^(\d{4})-(\d{2})-(\d{2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var m = strDate.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}
/**
 * 验证是否EMAIL
 * @param strDate
 * @return
 */
function isEmail(email) {
    var pattern = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    var m = email.match(pattern);
    if (m == null) {
        return false;
    }
    return true;
}
/**
 * 验证是否为空
 * @param strDate
 * @return
 */
function isNull(str) {
    if (null == str || "" == str || str == undefined || "null" == str) {
        return true;
    }
    
    if (typeof str == "string")
    {
    	str = str.replace(/(^\s*)|(\s*$)/g, "");
    	return str.length == 0;
    }
    
    return false;
}

function isNullEx(selector)
{
	return isNull($(selector).val());
}
/**
 * 验证是否为合法用户名
 * @param strDate
 * @return
 */
function isUsername(username) {
    if (/^\d.*$/.test(username)) {
        return 1;
    }
    if (!/^.{5,20}$/.test(username)) {
        return 2;
    }
    if (!/^[\w_]*$/.test(username)) {
        return 3;
    }
    return 0;
}

/**
 * 表单验证
 * @param strDate
 * @return
 */
function vaildateForm(formId) {
    // 获取FORM下的所有INPUT元素
	var verified = false;
    var elements = $("#" + formId).find("input, textarea");
    
    for (var i = 0; i < elements.size(); i++)
    {
    	var element = elements.eq(i);
    	verified = element.validate();
    	if (!verified){}
    	{
    		break;
    	}
    }
    
    return verified;
}

/**
 * 验证并提交
 * @return
 */
function submitForm(formId)
{
	if (vaildateForm(formId))
	{
		$('#' + formId).submit();
	}
}


/**
 * 压缩图片
 * @param jqImg		需要压缩的图片对应的jquery对象
 * @param displayHeight	压缩后高度
 * @param displayWidth		压缩后宽度
 */
function resizeImg(jqImg,displayHeight,displayWidth){
	var width = 0;				//图片实际宽度
	var height = 0;				//图片实际高度
	var ratio = 0;				//缩放比例
	jqImg.each(function() {
		imgLoad(this,function(){
			width = $(this).width(); // 图片实际宽度
			height = $(this).height(); // 图片实际高度
			ratio = width < height ? displayWidth/width : displayHeight/height;
			
			$(this).css("height", height * ratio); // 设定等比例缩放后的高度
			$(this).css("width", width * ratio); // 设定等比例缩放后的宽度
		});
	});
}

/**
 * 图片加载完成后事件
 * @param img
 * @param callback
 */
function imgLoad(img,callback){
	if(img.complete 
		|| img.readyState == 'loading' 
		|| img.readyState == 'complete'){
		
		callback();
	}
	else{
		img.onload = callback;
	}
}   

function hasLogin()
{
    return $("#J_userInfo").val() == "1";
}

/**
 * 获取cookie
 * @param name cookie名称
 * @return {*}
 */
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return arr[2].replace(/\"/g, "");
    else
        return null;
}

function setCookie(name, value)
{
	// 默认设置30天
	var days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days * 24 * 3600000);
	document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + ";path=/";
}

function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if(cval!=null) 
    document.cookie= name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/";
}


function getFriendlyTime(seconds)
{
	// TODO:验证出现问题
	if (isNull(seconds))
	{
		return "";
	}
	var hour = Math.floor(seconds / 3600);
	var min = Math.floor((seconds - (3600 * hour)) % 60);
	if (hour > 0)
	{
		hour = hour;
	}
	else
	{
		hour = 0;
	}
	
	if (min >= 45)
	{
		min = 1;
	}
	else
	{
		if (min >= 15)
		{
			min = 0.5;
		}
		else
		{
			min = 0;
		}
	}
	
	return hour + min + "小时";
}

function getEvtTgt()
{
	var evt;
	try
	{
		evt = event;
	}
	catch (e)
	{
		return FFSearchEventTarget();
	}
	
	var target = $(evt.target);
	if (target.size() == 0)
	{
		target = $(evt.srcElement);
	}
	
	return target;
}

function stopEvent(event)
{
	if (event.stopPropagation != undefined)
	{
		event.stopPropagation();
	}
	else
	{
		event.cancelBubble = true;
	}
}

function FFSearchEventTarget()
{
	func = FFSearchEventTarget.caller;
	
	while(func != null)
	{
		var arg0 = func.arguments[0];
		if(arg0)
		{
			if (arg0.target)
			{
				return $(arg0.target);
			}
		}
		func = func.caller;
	}
	
	return null;
}

//自动调整高度
function adjustHeight()
{
	var height1 = $(window).height() - 44;
	$(".auto-height-panel").height(height1);
	$(".auto-height-panel1").height(height1 - 42);
	$(".auto-height-panel2").height(height1);
	$(".auto-panel").height(height1-40);
}

// 判断微信浏览器方法
function isWeiXin()
{ 
	var ua = window.navigator.userAgent.toLowerCase(); 
	if(ua.match(/MicroMessenger/i) == 'micromessenger')
	{ 
		return true; 
	}
	else
	{ 
		return false; 
	} 
};

/**
 * 计算日期
 * @param day
 * @returns {String}
 */
function getDay(day)
{  
    var today = new Date();  
    var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;          
    today.setTime(targetday_milliseconds);  
    
    var tYear = today.getFullYear();  
    var tMonth = today.getMonth();  
    var tDate = today.getDate();  
    tMonth = doHandleMonth(tMonth + 1);  
    tDate = doHandleMonth(tDate);  
    
    return tYear + "-" + tMonth + "-" + tDate;  
} 

function doHandleMonth(month)
{  
    var m = month;  
    if(month.toString().length == 1)
    {  
       m = "0" + month;  
    }  
    
    return m;  
} 

// 阻止页面返回事件
function initHash(callback)
{
	$(window).bind("hashchange", function()
	{
		if (!window.location.hash)
		{
			callback();
		}
	});
}

// 仿浏览器后退事件
function closePanel()
{
	window.history.back();
}