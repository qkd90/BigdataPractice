/**
 * 登陆验证组件
 */

function isIE6()
{
	return !$.support.leadingWhitespace;
}

// 判断是否属于IE8或者IE9
function isIE89()
{
	var browser = navigator.appName;
	var b_version = navigator.appVersion;
	var version = b_version.split(";");
	if (version.length <= 1)
	{
		return false;
	}
	else
	{
		var trim_Version = version[1].replace(/[ ]/g,""); 
		if((browser == "Microsoft Internet Explorer" && trim_Version == "MSIE8.0") || browser == "Microsoft Internet Explorer" && trim_Version == "MSIE9.0") 
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
}

function safeSubstr(str, start, len)
{
	if (isNull(str))
	{
		return "";
	}
	return str.substr(start, len);
}

function safeRun(func)
{
	try
	{
		func();
	}
	catch (e){}
}


/**
 * 数值格式化
 * @param num 需要格式化的数值
 * @param paramValue 保留的小数位数
 * @return
 */
function num_fmt(num, len)
{
	return Math.abs(num).toFixed(len);
}

/**
 * 在当前的URL中添加参数，如果参数名已经存在的情况下则替换
 * @param paramName 参数名
 * @param paramValue 参数值
 * @return
 */
function addUrlParam(paramName, paramValue, srcUrl) {
    return paraUrlParam(paramName, paramValue, true, srcUrl);
}

/**
 * 删除URL中的指定参数
 * @param paramName 参数名
 * @param paramValue 参数值
 * @return
 */
function removeUrlParam(paramName, srcUrl) {
    return paraUrlParam(paramName, "", false, srcUrl);
}

/**
 * 处理URL参数的通用方法
 * @param paramName 参数名
 * @param paramValue 参数值
 * @param operType 操作类型，ture表示添加参数，false表示删除参数
 * @return
 */
function paraUrlParam(paramName, paramValue, operType, srcUrl) {

	if (isNull(srcUrl))
	{
		var srcUrl = window.top.location.href;
	}
	
    var urlArr = srcUrl.split("?");
    var tarUrl = urlArr[0].replaceAll("#", "", true);
    if (urlArr.length > 1) {
        var newParamStr = "";
        var paramStr = urlArr[1];
        var paramArr = urlArr[1].split("&");
        var i = 0;
        for (; i < paramArr.length; i++) {
            if (isNull(paramArr[i]))
            {
                continue;
            }
            if (paramName == paramArr[i].substring(0, paramArr[i].indexOf("="))) {
                continue;
            }

            // 去除参数值中的#
            var kv = paramArr[i].replaceAll("#", "", true);
            newParamStr = newParamStr + kv + "&";
        }
        // 如果需要添加参数
        if (operType)
        {
            newParamStr = newParamStr + paramName + "=" + paramValue + "&";
        }
        if (newParamStr.length > 0)
        {
            newParamStr = newParamStr.substr(0, newParamStr.length - 1);
        }

        if (!isNull(newParamStr))
        {
            tarUrl = tarUrl + "?" + newParamStr;
        }

    } else {
        // 如果需要添加参数
        if (operType) {
            tarUrl = tarUrl + "?" + paramName + "=" + paramValue;
        }
    }
    return tarUrl;
}

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

        return decodeURIComponent(arr[2].replace(/\"/g, ""));
    else
        return null;
}

function setCookie(name, value)
{
	// 默认设置30天
	var days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days * 24 * 3600000);
	document.cookie = name + "="+ encodeURIComponent(value) + ";expires=" + exp.toGMTString() + ";path=/" + ";domain=.lvxbang.com";
}

function getUnCodedCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return arr[2];
    else
        return null;
}

function setUnCodedCookie(name, value)
{
	// 默认设置30天
	var days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days * 24 * 3600000);
	document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + ";path=/"  + ";domain=.lvxbang.com";
}

function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if(cval!=null) 
    document.cookie= name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/" + ";domain=.lvxbang.com";
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

// 阻止冒泡兼容火狐
function stopEventFun()
{
	try
	{
		stopEvent(event);
	}
	catch (e)
	{
		if($.browser.mozilla)
		{
			var $E = function()
			{
				var c = $E.caller; while(c.caller)c=c.caller; return c.arguments[0];
			};
			__defineGetter__("event", $E);
			event.stopPropagation();
		};
	}
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

/**
 * 计算日期
 * @param day
 * @returns {String}
 */
function getDayDate(day)
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

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt)
{ //author: meizz
    var o = {
        "y+" : this.getFullYear,
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "H+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

// 清空保存在cookie中的每个城市中的行程线路
function delCityPlan()
{
	var citystr = getCookie("citys");
	if (citystr)
	{
		var cityArr = citystr.split("%");
		for (var i = 0; i < cityArr.length; i++)
		{
			delCookie("city_" + cityArr[i]);
		}
	}
	
	delCookie("citys");
}

// 头部搜索框
function headerSearch() {
	var keyword = $('#txtSearch').val();
	if (!keyword || $.trim(keyword).length <= 0) {
		promptWarn("搜索关键字不能为空");
		return ;
	}
	keyword = encodeURI(encodeURI(keyword));
	window.location.href = $(".header_nav .menu_panel .menu_list li").find("a").eq(0).attr("href") + "/search_" + keyword + "_all.html";
}

!function (a) {
    a.fly = function (b, c) {
        var d = {
                version: "1.0.0",
                autoPlay: !0,
                vertex_Rtop: 20,
                speed: 1.2,
                start: {},
                end: {},
                onEnd: a.noop
            },
            e = this,
            f = a(b);
        e.init = function (a) {
            this.setOptions(a), !!this.settings.autoPlay && this.play()
        }, e.setOptions = function (b) {
            this.settings = a.extend(!0, {}, d, b);
            var c = this.settings,
                e = c.start,
                g = c.end;
            f.css({
                marginTop: "0px",
                marginLeft: "0px",
                position: "fixed"
            }).appendTo("body"), null != g.width && null != g.height && a.extend(!0, e, {
                width: f.width(),
                height: f.height()
            });
            var h = Math.min(e.top, g.top) - Math.abs(e.left - g.left) / 3;
            h < c.vertex_Rtop && (h = Math.min(c.vertex_Rtop, Math.min(e.top, g.top)));
            var i = Math.sqrt(Math.pow(e.top - g.top, 2) + Math.pow(e.left - g.left, 2)),
                j = Math.ceil(Math.min(Math.max(Math.log(i) / .05 - 75, 30), 100) / c.speed),
                k = e.top == h ? 0 : -Math.sqrt((g.top - h) / (e.top - h)),
                l = (k * e.left - g.left) / (k - 1),
                m = g.left == l ? 0 : (g.top - h) / Math.pow(g.left - l, 2);
            a.extend(!0, c, {
                count: -1,
                steps: j,
                vertex_left: l,
                vertex_top: h,
                curvature: m
            })
        }, e.play = function () {
            this.move()
        }, e.move = function () {
            var b = this.settings,
                c = b.start,
                d = b.count,
                e = b.steps,
                g = b.end,
                h = c.left + (g.left - c.left) * d / e,
                i = 0 == b.curvature ? c.top + (g.top - c.top) * d / e : b.curvature * Math.pow(h - b.vertex_left, 2) + b.vertex_top;
            if (null != g.width && null != g.height) {
                var j = e / 2,
                    k = g.width - (g.width - c.width) * Math.cos(j > d ? 0 : (d - j) / (e - j) * Math.PI / 2),
                    l = g.height - (g.height - c.height) * Math.cos(j > d ? 0 : (d - j) / (e - j) * Math.PI / 2);
                f.css({
                    width: k + "px",
                    height: l + "px",
                    "font-size": Math.min(k, l) + "px"
                })
            }
            f.css({
                left: h + "px",
                top: i + "px"
            }), b.count++;
            var m = window.requestAnimationFrame(a.proxy(this.move, this));
            d == e && (window.cancelAnimationFrame(m), b.onEnd.apply(this))
        }, e.destory = function () {
            f.remove()
        }, e.init(c)
    }, a.fn.fly = function (b) {
        return this.each(function () {
            void 0 == a(this).data("fly") && a(this).data("fly", new a.fly(this, b))
        })
    }
}(jQuery);
