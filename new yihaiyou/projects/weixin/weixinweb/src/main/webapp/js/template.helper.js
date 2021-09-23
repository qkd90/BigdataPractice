template.helper("imgSrc", function(data)
{
	if (isNull(data)) 
	{
		return "";
	}
	else if (data.substring(0, 4) == "http")
    {
    	return data;
    }
    else
    {
    	return CFG.SRV_ADDR_IMAGE + data;
    }
});

template.helper('ldf', function(ld, format, tips)
{
	if (!ld)
	{
		return tips;
	}
	
	var date = new Date(parseInt(ld));
	
	return formatDate(date, format);
});

template.helper('dateFormat', function (date, format)
{
	if (isNull(date))
	{
		return "暂无日期信息";
	}
	
    date = date.split(" ")[0];
    date = new Date(parseInt(date));
    
	return formatDate(date, format);
});

template.helper('time', function (date, format)
{
	if (isNull(date))
	{
		return;
	}
	
	var dates = date.split(":");
	var map =
	{
		"h": dates[0], //小时
		"m": dates[1], //分
		"s": dates[2] //秒
	};
	
    format = format.replace(/([hms])+/g, function(all, t)
    {
		var v = map[t];
		
		if(v !== undefined)
		{
			if (all.length > 1)
			{
               v = '0' + v;
               v = v.substr(v.length-2);
		    }
			return v;
		}
		
			return all;
	});
    
	return format;
	
});

function formatDate(date, format)
{
	var map =
	{
		"M": date.getMonth() + 1, //月份
		"d": date.getDate(), //日
		"w": date.getDay()==0?"日":date.getDay(), //星期
		"h": date.getHours(), //小时
		"m": date.getMinutes(), //分
		"s": date.getSeconds(), //秒
		"q": Math.floor((date.getMonth() + 3) / 3), //季度
		"S": date.getMilliseconds() //毫秒
	};
	
    format = format.replace(/([yMdwhmsqS])+/g, function(all, t)
    {
		var v = map[t];
		
		if(v !== undefined)
		{
			if (all.length > 1)
			{
               v = '0' + v;
               v = v.substr(v.length-2);
		    }
			return v;
		}
		else if(t === 'y')
		{
			return (date.getFullYear() + '').substr(4 - all.length);
		}
			return all;
	});
    
	return format;
}

template.helper('tDifference', function(data)
{
	console.log(data)
	
	var time;
	
	time = date + "分钟前";
	console.log("时间："+time);
	
	
	if (date < 3600)
	{
		time = date + "分钟前";
		console.log("时间："+time);
		return time;
	}else if(86400 > date >= 3600){
		time = date%3600 + "小时前";
		console.log("时间："+time);
		return time;
	}else if( 432000> date >= 86400){
		time = date%86400 + "天前";
		console.log("时间："+time);
		return time;
	}else{
		console.log("时间："+time);
		return "";
	}
});











