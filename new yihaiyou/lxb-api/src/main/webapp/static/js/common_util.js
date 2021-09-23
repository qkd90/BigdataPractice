function isIE6()
{
	return !$.support.leadingWhitespace;
}


function safeSubstr(str, start, len)
{
	if (isNull(str))
	{
		return "";
	}
	return str.substr(start, len);
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
    if (null == str || "" == str || undefined == str || "null" == str) {
        return true;
    }
    str = str.replace(/(^\s*)|(\s*$)/g, "");
    return str.length == 0;
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
	var result = false;
    try
    {
        // 读取登录状态
        var userSession = $("#_has_login").val();
        result = "true" == userSession;
    } catch (e)
    {
        result = false;
    }
    
    return result;
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