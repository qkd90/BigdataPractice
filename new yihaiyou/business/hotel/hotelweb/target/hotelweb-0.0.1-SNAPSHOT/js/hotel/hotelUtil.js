/**
 * Created by dy on 2016/6/3.
 */
/** 常量 */
var HotelConstants = {
    QINIU_DOMAIN : QINIU_BUCKET_URL,
    // 线路描述图片目录
    hotelDescImg:'hotel/hotelDesc/',
    // 票型
    type : [{'id':'','text':'票型','filter':true},{'id':'adult','text':'成人票'},{'id':'student','text':'学生票'},{'id':'child','text':'儿童票'},{'id':'oldman','text':'老年票'},{'id':'taopiao','text':'套票'},{'id':'other','text':'其他票型'},{'id':'team','text':'团体票'}],

    // 状态
    status : [{'id':'','text':'状态','filter':true},{'id':'0','text':'无库存'},{'id':'1','text':'正常'}],
    // 状态
    ticketStatus : [{'id':'','text':'门票状态','filter':true},{'id':'UP','text':'上架'},{'id':'DOWN','text':'下架'}],

    getConstants : function(code, filter) {
        var resultArray = [];
        var codeArray = HotelConstants[code];
        for (var i = 0; i < codeArray.length; i++) {
            var item = codeArray[i];
            if (filter && item.filter) {	// 过滤
                continue ;
            }
            resultArray.push(item);
        }
        return resultArray;
    }
};

/** 工具方法 */
var HotelUtil = {
    // 根据代码类型和代码取对应中文名称
    getCodeName : function(codeType, code) {
        if (codeType) {
            var codeArray = HotelConstants[codeType];
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
            return HotelUtil.getCodeName(this.codeType, value);
        }
        return '';
    },
    // 表格代码转换-是否
    sfFmt : function(value, rowData, rowIndex) {
        if (!value) {
            return '';
        }
        if ('T' == value) {
            return '是';
        } else if ('F' == value) {
            return '否';
        } else {
            return '';
        }
    },
    // 表格代码转换-日期时间
    dateTimeFmt : function(value, rowData, rowIndex) {
        if (!value || !this.datePattern) {
            return '';
        }
        var date = new Date(value);
        return HotelUtil.dateToString(date, this.datePattern);
    },
    // 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
    dateNumToString : function(dateNum, pattern) {
        if (!dateNum) {
            return '';
        }
        var date = new Date(dateNum);
        return HotelUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
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
            return o.y+'-'+HotelUtil.lpad(o.M)+'-'+HotelUtil.lpad(o.d)+' '+HotelUtil.lpad(o.H)+':'+HotelUtil.lpad(o.m)+':'+HotelUtil.lpad(o.s);
        }
        if ('HH:mm:ss' == pattern) {
            return HotelUtil.lpad(o.H)+':'+HotelUtil.lpad(o.m)+':'+HotelUtil.lpad(o.s);
        }
        if ('yyyy-MM-dd' == pattern) {
            return HotelUtil.lpad(o.y)+'-'+HotelUtil.lpad(o.M)+'-'+HotelUtil.lpad(o.d);
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
    // 字符串转为日期，格式为：yyyy-MM-dd HH:mm:ss，yyyy-MM-dd
    stringToDate : function(dateStr) {
        var date = new Date(dateStr.replace(/-/g, "/"));
        return date;
    },
    // 数组是否包含某个值
    contains : function(array, element) {
        for (var i = 0; i < array.length; i++) {
            if (array[i] === element) {
                return true;
            }
        }
        return false;
    },
    // 数组获取对应key值的对象
    getByKey : function(array, keyName, keyValue) {
        for (var i = 0; i < array.length; i++) {
            if (array[i][keyName] === keyValue) {
                return array[i];
            }
        }
        return null;
    },
    // 异步回调函数，表格、表单、异步；successfn成功后回调函数；errorFn失败后回调函数
    backCall : function(data, successfn, errorFn) {
        var obj = null;
        if (data instanceof String || typeof data == 'string') {
            obj = eval('(' + data+ ')');
        } else {
            obj = data;
        }
        if (obj && obj.errorCode === 0) {
            if(typeof successfn == "function") {
                successfn();
            }
            return true;
        } else {
            if (obj && obj.errorCode) {
                $.messager.alert('提示', obj.errorCode+'-'+obj.errorMsg, 'info', function() {
                    if(typeof errorFn == "function") {
                        errorFn();
                    }
                });
            } else {
                $.messager.alert('提示', '操作失败！', 'info', function() {
                    if(typeof errorFn == "function") {
                        errorFn();
                    }
                });
            }
            return false;
        }
    }
};


