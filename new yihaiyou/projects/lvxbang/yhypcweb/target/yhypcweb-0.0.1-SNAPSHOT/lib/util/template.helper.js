template.helper("briefText", function (data, num) {
    if (isNull(data)) {
        return "";
    }
    if (data.length <= num) {
        return data;
    }
    return data.substr(0, num - 1) + "...";
});
template.helper("imgSrc", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return CFG.SRV_ADDR_IMAGE + data;
    }
});
template.helper('imageResize', function(data, width, height) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        if (data.indexOf(QINIU_BUCKET_URL) >= 0) {
            return data + "?imageView2/2/w/" + width + "/h/" + height;
        } else {
            return data;
        }
    }
    else {
        return QINIU_BUCKET_URL + data + "?imageView2/2/w/" + width + "/h/" + height;
    }
});

template.helper("recommendPlanTripImg", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + data + "?imageView2/1/w/214/h/256";
    }
});
template.helper("commentImg", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + data + "?imageView2/1/w/48/h/48/q/75";
    }
});

template.helper("recommendPlanDetailImg", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + data + "?imageView2/2/w/680";
    }
});
template.helper("recommendPlanUserCenterImg", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + data + "?imageView2/1/w/366/h/211/";
    }
});
template.helper("recommendPlanTripListImg", function (data) {
    if (isNull(data)) {
        return "";
    }
    else if (data.substring(0, 4) == "http") {
        return data;
    }
    else {
        return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + data + "?imageView2/1/w/300/h/218/q/90";
    }
});

template.helper('ldf', function (ld, format, tips) {
    if (!ld) {
        return tips;
    }

    var date = new Date(parseInt(ld));

    return formatDate(date, format);
});

template.helper('dateFormat', function (date, format) {
    if (isNull(date)) {
        return "暂无日期信息";
    }

    date = date.split(" ")[0];
    date = new Date(parseInt(date));

    return formatDate(date, format);
});

template.helper('time', function (date, format) {
    if (isNull(date)) {
        return;
    }

    var dates = date.split(":");
    var map =
    {
        "h": dates[0], //小时
        "m": dates[1], //分
        "s": dates[2] //秒
    };

    format = format.replace(/([hms])+/g, function (all, t) {
        var v = map[t];

        if (v !== undefined) {
            if (all.length > 1) {
                v = '0' + v;
                v = v.substr(v.length - 2);
            }
            return v;
        }

        return all;
    });

    return format;

});

function formatDate(date, format) {
    var map =
    {
        //"y": date.getYear(),
        "M": date.getMonth() + 1, //月份
        "d": date.getDate(), //日
        "w": date.getDay() == 0 ? "日" : Arabia_to_Chinese(date.getDay() + ""), //星期
        "h": date.getHours(), //小时
        "m": date.getMinutes(), //分
        "s": date.getSeconds(), //秒
        "q": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };

    format = format.replace(/([yMdwhmsqS])+/g, function (all, t) {
        var v = map[t];

        if (v !== undefined) {
            if (all.length > 1) {
                v = '0' + v;
                v = v.substr(v.length - 2);
            }
            return v;
        }
        else if (t === 'y') {
            return (date.getFullYear() + '').substr(4 - all.length);
        }
        return all;
    });

    return format;
}

template.helper('chineseNumber', function (number) {
    return display2Chinese(number);
});

function display2Chinese(Num) {
    return Arabia_to_Chinese(Num);
}
function Arabia_to_Chinese(Num) {
    if (isNaN(Num)) { //验证输入的字符是否为数字
        return "";
    }
    //---字符处理完毕，开始转换，转换采用前后两部分分别转换---//
    part = String(Num).split(".");
    newchar = "";
    //小数点前进行转化
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) {
            //alert("位数过大，无法计算");
            promptWarn("位数过大，无法计算");
            return "";
        }//若数量超过拾亿单位，提示
        tmpnewchar = "";
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0":
                tmpnewchar = "零" + tmpnewchar;
                break;
            case "1":
                tmpnewchar = "一" + tmpnewchar;
                break;
            case "2":
                tmpnewchar = "二" + tmpnewchar;
                break;
            case "3":
                tmpnewchar = "三" + tmpnewchar;
                break;
            case "4":
                tmpnewchar = "四" + tmpnewchar;
                break;
            case "5":
                tmpnewchar = "五" + tmpnewchar;
                break;
            case "6":
                tmpnewchar = "六" + tmpnewchar;
                break;
            case "7":
                tmpnewchar = "七" + tmpnewchar;
                break;
            case "8":
                tmpnewchar = "八" + tmpnewchar;
                break;
            case "9":
                tmpnewchar = "九" + tmpnewchar;
                break;
        }
        switch (part[0].length - i - 1) {
            case 0:
                break;
            case 1:
                if (perchar != 0)tmpnewchar = tmpnewchar + "十";
                break;
            case 2:
                if (perchar != 0)tmpnewchar = tmpnewchar + "百";
                break;
            case 3:
                if (perchar != 0)tmpnewchar = tmpnewchar + "千";
                break;
            case 4:
                tmpnewchar = tmpnewchar + "万";
                break;
            case 5:
                if (perchar != 0)tmpnewchar = tmpnewchar + "十";
                break;
            case 6:
                if (perchar != 0)tmpnewchar = tmpnewchar + "百";
                break;
            case 7:
                if (perchar != 0)tmpnewchar = tmpnewchar + "千";
                break;
            case 8:
                tmpnewchar = tmpnewchar + "亿";
                break;
            case 9:
                tmpnewchar = tmpnewchar + "十";
                break;
        }
        newchar = tmpnewchar + newchar;
    }//for
    //小数点之后进行转化
    if (Num.indexOf(".") != -1) {
        if (part[1].length > 2) {
            promptWarn("小数点之后只能保留两位,系统将自动截段");
            //alert("小数点之后只能保留两位,系统将自动截段");
            part[1] = part[1].substr(0, 2)
        }
        for (i = 0; i < part[1].length; i++) {//for2
            tmpnewchar = "";
            perchar = part[1].charAt(i);
            switch (perchar) {
                case "0":
                    tmpnewchar = "零" + tmpnewchar;
                    break;
                case "1":
                    tmpnewchar = "一" + tmpnewchar;
                    break;
                case "2":
                    tmpnewchar = "二" + tmpnewchar;
                    break;
                case "3":
                    tmpnewchar = "三" + tmpnewchar;
                    break;
                case "4":
                    tmpnewchar = "四" + tmpnewchar;
                    break;
                case "5":
                    tmpnewchar = "五" + tmpnewchar;
                    break;
                case "6":
                    tmpnewchar = "六" + tmpnewchar;
                    break;
                case "7":
                    tmpnewchar = "七" + tmpnewchar;
                    break;
                case "8":
                    tmpnewchar = "八" + tmpnewchar;
                    break;
                case "9":
                    tmpnewchar = "九" + tmpnewchar;
                    break;
            }
            newchar = newchar + tmpnewchar;
        }//for2
    }
    //替换所有无用汉字
    while (newchar.search("零零") != -1)
        newchar = newchar.replace("零零", "零");
    newchar = newchar.replace("亿零万", "亿");
    newchar = newchar.replace("零亿", "亿");
    newchar = newchar.replace("亿万", "亿");
    newchar = newchar.replace("零万", "万");
    newchar = newchar.replace("零", "");
    return newchar;
}
//景点、游记列表点评
template.helper("formatComment", function (data, size) {
    if (typeof(data) == "undefined" || data == null) {
        data = "";
    }
    console.info(data);
    if (data.length > size) {
        return data.substring(0, size - 3) + "<span class='ellipsis'>…<br/></span>" + data.substring(size - 3);
    }
    else {
        return data;
    }
});
//头部搜索结果页游记、酒店列表点评
template.helper("formatFavoritesCom", function (data, size) {
    if (typeof(data) == "undefined" || data == null) {
        data = "";
    }
    if (data.length > size) {
        return data.substring(0, size - 3) + "<span class='ellipsis'>…<img src='/images/left2.png' align='absbottom' class='ml5' /><br/></span>" + data.substring(size - 3);
    }
    else {
        return data + "<img src='/images/left2.png' align='absbottom' class='ml5' />";
    }
});
//头部搜索结果页美食列表简介
template.helper("formatDelicacyIntro", function (data, size) {
    if (typeof(data) == "undefined" || data == null) {
        data = "";
    }
    if (data.length > size) {
        var length = size - 3;
        if (data.indexOf("<br") >= 0 && data.indexOf("<br") < size / 2) {
            var str1 = data.substring(0, data.indexOf("<br") + 5);
            var str2 = data.substring(data.indexOf("<br") + 5);
            length = size / 2 - 3;
            if (str2.indexOf("<br") >= 0 && str2.indexOf("<br") < size / 2) {
                length = str2.indexOf("<br");
            }
            return str1 + str2.substring(0, length) + "<span class='ellipsis'>…<br/></span>" + str2.substring(length);
        }
        if (data.indexOf("<br") >= size / 2 && data.indexOf("<br") < size) {
            length = data.indexOf("<br");
        }
        return data.substring(0, length) + "<span class='ellipsis'>…<br/></span>" + data.substring(length);
    }
    else {
        return data;
    }
});

function formatString(data) {
    if (typeof(data) == "undefined" || data == null) {
        return "";
    }
    return $.trim(data).replace(/\s/g, "").replace(/&ldquo;/g, "“").replace(/&rdquo;/g, "”").replace(/&middot;/g, "·");
}

//float.js里面用来截取城市id前四位数
template.helper("subCityId", function (data) {
    if (parseInt(data) > 1000000) {
        return data;
    } else {
        return data.substring(0, 4);
    }

});
//建议游玩时间从分钟转化为小时处理
template.helper("calTime", function (data) {
    var hour = Math.floor(data / 60);
    var remainder = Number((data / 60).toFixed(1).split(".")[1]);
    if (0 < remainder && remainder <= 5) {
        remainder = 0.5;
    } else if (remainder == 0) {
        remainder = 0;
    } else {
        remainder = 1;
    }
    return hour + remainder;
});
//计算机票价格
template.helper("calPlanPrice", function (price, addi, airp) {
    return (Number(price) + Number(addi) + Number(airp));
});

template.helper("planMapCover", function (cover) {
    if (isNull(cover)) {
        return "";
    }
    if (cover.substring(0, 4) == "http") {
        return cover;
    }
    return 'http://7u2inn.com2.z0.glb.qiniucdn.com/' + cover + "?imageView2/1/w/230/h/130";
});

template.helper("substring", function (string, length) {
    if (isNull(string) || string.length < length) {
        return string;
    }
    return string.substring(0, length);
});
template.helper("subRepacket", function (data) {
    if (data.length > 10) {
        return data.substring(0, 10) + "...";
    } else {
        return data;
    }
});

template.helper("numToWord", function (num) {
    switch (num) {
        case "1":
            return "一";
        case "2":
            return "二";
        case "3":
            return "三";
        case "4":
            return "四";
        case "5":
            return "五";
        default :
            return "";
    }
});

template.helper("formatPrice", function (price) {
    return parseInt(price * 100) / 100;
});

template.helper("formatDouble", function (data) {
    var num = Math.round(data * 100) / 100;
    if (num < 0.1) {
        return num.toFixed(2);
    } else if (num > 0.1 && num < 1) {
        return num.toFixed(2);
    } else if (num > 1) {
        return num;
    }
});

template.helper("floatScenicCover", function (cover) {
    if (isNull(cover)) {
        return "";
    }
    if (cover.indexOf("http") == 0) {
        return cover;
    } else {
        return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover + "?imageView2/1/w/50/h/50/q/75";
    }
});

template.helper("imgPath", function (path, width, height, quality) {
    if (isNull(path)) {
        return "";
    }
    if (path.indexOf("http") == 0) {
        return path;
    } else {
        if (quality != null || isNaN(quality)) {
            quality = 75;
        }
        return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + path + "?imageView2/1/w/" + width + "/h/" + height + "/q/" + quality;
    }
});

template.helper("roundNumber", function (number, length) {
    return Math.round(number * Math.pow(10, length)) / Math.pow(10, length);
});