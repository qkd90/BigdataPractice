/** 常量 */
var FxConstants = {
	// 线路描述图片目录
	lineDescImg:'/line/lineDesc/',
	// 状态
	status : [{'id':'','text':'状态','filter':true},{'id':'-1','text':'待审核'},{'id':'0','text':'已审核'},{'id':'1','text':'已冻结'}],
	// 供应商类型
	supplierType : [{'id':'other','text':'其他'}, {'id':'hotel','text':'酒店民宿'}, {'id':'sailboat','text':'海上休闲'}, {'id':'cruiseship','text':'邮轮旅游'}, {'id':'scenic','text':'景点门票'}],
	
	getConstants : function(code, filter) {
		var resultArray = [];
		var codeArray = FxConstants[code];
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
var FxUtil = {
	// 根据代码类型和代码取对应中文名称
	getCodeName : function(codeType, code) {
		if (codeType) {
			var codeArray = FxConstants[codeType];
			for (var i = 0; i < codeArray.length; i++) {
				if (codeArray[i].id === code.toString()) {	// 转为字符串进行比较，避免整数0比较错误
					return codeArray[i].text ;
				}
			}
		}
		return '';
	},
	// 表格代码转换
	codeFmt : function(value, rowData, rowIndex) {
		if ((value||value==0) && this.codeType) {
			return FxUtil.getCodeName(this.codeType, value);
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
		return FxUtil.dateToString(date, this.datePattern);
	},
	// 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
	dateNumToString : function(dateNum, pattern) {
		if (!dateNum) {
            return '';
        }
		var date = new Date(dateNum);
		return FxUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
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
			return o.y+'-'+FxUtil.lpad(o.M)+'-'+FxUtil.lpad(o.d)+' '+FxUtil.lpad(o.H)+':'+FxUtil.lpad(o.m)+':'+FxUtil.lpad(o.s);
		}
		if ('HH:mm:ss' == pattern) {
			return FxUtil.lpad(o.H)+':'+FxUtil.lpad(o.m)+':'+FxUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd' == pattern) {
			return FxUtil.lpad(o.y)+'-'+FxUtil.lpad(o.M)+'-'+FxUtil.lpad(o.d);
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
	}, 
	// build供应商，重新生成静态页面
	buildSupplier : function(id) {
		$.post(FG_DOMAIN+'/mall/build/buildOneSupplier.jhtml', {id:id},
			function(data){
			}
		);
	}
};


