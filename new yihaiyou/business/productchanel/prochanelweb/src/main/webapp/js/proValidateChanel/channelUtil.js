/**
 * Created by dy on 2016/9/1.
 */
var ChannelConstants = {
    chanel: [
        {id:'LXB',text:'旅行帮'},
        {id:'CTRIP',text:'携程'},
        {id:'ZYB',text:'智游宝'}
    ],
    status: [
        {id:'UP',text:"有效"},
        {id:'FREEZE',text:"冻结"},
        {id:'INVALID',text:"无效"}
    ]
};
var ChannelUtil = {
    //month, halfmonth, week, day
    getCodeName : function(codeType, code) {
        if (codeType) {
            var codeArray = ChannelConstants[codeType];
            for (var i = 0; i < codeArray.length; i++) {
                if (codeArray[i].id == code) {
                    return codeArray[i].text ;
                }
            }
        }
        return '';
    },
    // 表格代码转换
    codeFmt : function(value, rowData, rowIndex) {
        if (value && this.codeType) {
            return ChannelUtil.getCodeName(this.codeType, value);
        }
        return '';
    },
    // 表格代码转换-日期时间
    dateTimeFmt : function(value, rowData, rowIndex) {
        if (!value || !this.datePattern) {
            return '';
        }
        var date = new Date(value);
        return ChannelUtil.dateToString(date, this.datePattern);
    },
    // 字符串转为日期，格式为：yyyy-MM-dd HH:mm:ss，yyyy-MM-dd
    stringToDate : function(dateStr) {
        var date = new Date(dateStr.replace(/-/g, "/"));
        return date;
    },
    // 时间转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
    dateToString : function(date, pattern) {
        var o = {
            y : date.getFullYear(),
            M : date.getMonth()+1,
            d : date.getDate(),
            H : date.getHours(),
            m : date.getMinutes(),
            s : date.getSeconds()
        };
        if ('yyyy-MM-dd HH:mm:ss' == pattern) {
            return o.y+'-'+ ChannelUtil.lpad(o.M)+'-'+ChannelUtil.lpad(o.d)+' '+ChannelUtil.lpad(o.H)+':'+ ChannelUtil.lpad(o.m)+':'+ChannelUtil.lpad(o.s);
        }
        if ('HH:mm:ss' == pattern) {
            return ChannelUtil.lpad(o.H)+':'+ChannelUtil.lpad(o.m)+':'+ChannelUtil.lpad(o.s);
        }
        if ('yyyy-MM-dd' == pattern) {
            return ChannelUtil.lpad(o.y)+'-'+ChannelUtil.lpad(o.M)+'-'+ChannelUtil.lpad(o.d);
        }
        if ('yyyyMMdd' == pattern) {
            return ChannelUtil.lpad(o.y)+''+ChannelUtil.lpad(o.M)+''+ChannelUtil.lpad(o.d);
        }
    },
    // 表格代码转换-日期时间
    dateTimeFmt : function(value, rowData, rowIndex) {
        if (!value || !this.datePattern) {
            return '';
        }
        var date = new Date(value);
        return ChannelUtil.dateToString(date, this.datePattern);
    },
    // 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
    dateNumToString : function(dateNum, pattern) {
        if (!dateNum) {
            return '';
        }
        var date = new Date(dateNum);
        return ChannelUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
    },

    dateStringCompare : function(dateStr1, dateStr2) {
        var date1 = ChannelUtil.stringToDate(dateStr1);
        var date2 = ChannelUtil.stringToDate(dateStr2);
        return ChannelUtil.dateCompare(date1, date2);
    },
    dateCompare : function(date1, date2) {
        var datetime1 = date1.getTime();
        var datetime2 = date2.getTime();
        var result = datetime1 - datetime2;
        if (result > 0) {
            return 1;
        } else if (result < 0 ) {
            return -1;
        } else {
            return 0;
        }
    },
    // 数字左侧补0
    lpad : function(num) {
        if (num < 10) {
            return '0' + num;
        } else {
            return '' + num;
        }
    },

    cutByLength: function(str, length) {
        if (str) {
            if (str.length > length) {
                return str.substr(0, length);
            } else {
                return str;
            }
        } else {
            return "";
        }
    }
}