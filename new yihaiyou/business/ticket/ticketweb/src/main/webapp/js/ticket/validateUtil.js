/** 常量 */
var ValidateContants = {
	// 票型
	usedFlag : [{'id':'','text':'是否验证','filter':true},{'id':false,'text':'未验证'},{'id':true,'text':'已验证'}],
	userStatus: [{'id':'','text':'是否发布','filter':true},{'id':'activity','text':'已发布'},{'id':'lock','text':'未发布'},{'id':'del','text':'已删除'}],
	
	getConstants : function(code, filter) {
		var resultArray = [];
		var codeArray = ValidateContants[code];
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
var ValidateUtil = {
	// 根据代码类型和代码取对应中文名称
	getCodeName : function(codeType, code) {
		if (codeType) {
			var codeArray = ValidateContants[codeType];
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
			return ValidateUtil.getCodeName(this.codeType, value);
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
		return ValidateUtil.dateToString(date, this.datePattern);
	},
	// 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
	dateNumToString : function(dateNum, pattern) {
		if (!dateNum) {
            return '';
        }
		var date = new Date(dateNum);
		return ValidateUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
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
			return o.y+'-'+ValidateUtil.lpad(o.M)+'-'+ValidateUtil.lpad(o.d)+' '+ValidateUtil.lpad(o.H)+':'+ValidateUtil.lpad(o.m)+':'+ValidateUtil.lpad(o.s);
		}
		if ('HH:mm:ss' == pattern) {
			return ValidateUtil.lpad(o.H)+':'+ValidateUtil.lpad(o.m)+':'+ValidateUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd' == pattern) {
			return ValidateUtil.lpad(o.y)+'-'+ValidateUtil.lpad(o.M)+'-'+ValidateUtil.lpad(o.d);
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


