/** 常量 */
var LineConstants = {
	QINIU_DOMAIN : QINIU_BUCKET_URL,
	// 线路描述图片目录
	lineDescImg:'line/lineDesc/',
	// 线路状态
	lineStatus : [{'id':'','text':'线路状态'},{'id':'show','text':'上架'},{'id':'hide','text':'下架'},{'id':'del','text':'删除','filter':true},{'id':'outday','text':'过期'},{'id':'checking','text':'待审核'}],
	// 线路类型
	lineType : [{'id':'','text':'线路类型','filter':true},{'id':'city','text':'厦门游'},{'id':'around','text':'周边游'},{'id':'china','text':'国内游'}],
	// 产品性质
	productAttr : [{'id':'','text':'产品性质','filter':true},{'id':'gentuan','text':'跟团游'},{'id':'ziyou','text':'自助游'},{'id':'zijia','text':'自驾游'},
		{'id':'custommade','text':'精品定制'},{'id':'localplay','text':'当地参团'}],
	// 自定义分类(不限)
	customType : [{'id':'','text':'自定义分类(不限)','filter':true},{'id':'1','text':'厦门本地游'}, {'id':'2','text':'厦门周边及省内游'}],
	// 购物与自费
	buypay : [{'id':'','text':'购物与自费','filter':true},{'id':'noBuyNoPay','text':'无购物无自费'}, {'id':'noBuyPay','text':'无购物有自费'}, {'id':'buyNoPay','text':'有购物无自费'}, {'id':'buyPay','text':'有购物有自费'}],
	// 智能筛选
	aiFilter : [{'id':'','text':'智能筛选','filter':true},{'id':'scoreExchangeParticipation','text':'积分兑换线路'},{'id':'paySetNoClose','text':'开通支付'},{'id':'clickNumLt10','text':'人气少于10'},{'id':'orderNumEq0','text':'无订单'}],
	// 线路类型  CTRIP','JUHE','ELONG','QUNAR PLATFORM
	source : [{'id':'','text':'全部类型','filter':true},{'id':'LXB','text':'本平台'},{'id':'CTRIP','text':'携程'},{'id':'JUHE','text':'聚合'},{'id':'ELONG','text':'艺龙'},{'id':'QUNAR','text':'去哪儿'}],
	combineType : [{'id':'','text':'全部类型','filter':true},{'id':'single','text':'单一型'},{'id':'combine','text':'组合型'}],


	getConstants : function(code, filter) {
		var resultArray = [];
		var codeArray = LineConstants[code];
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
var LineUtil = {
	// 根据代码类型和代码取对应中文名称
	getCodeName : function(codeType, code) {
		if (codeType) {
			var codeArray = LineConstants[codeType];
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
			return LineUtil.getCodeName(this.codeType, value);
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
		return LineUtil.dateToString(date, this.datePattern);
	},
	// 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
	dateNumToString : function(dateNum, pattern) {
		if (!dateNum) {
            return '';
        }
		var date = new Date(dateNum);
		return LineUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
	},
	// 时间字符串转为日期对象，pattern为“yyyy-MM-dd”
    stringToDate : function(dateStr) {
		var dateArray = dateStr.split('-');
        var year = parseInt(dateArray[0]);
        var month = parseInt(dateArray[1]) - 1;
        var day = parseInt(dateArray[2]);
		var date = new Date(year, month, day);
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
			return o.y+'-'+LineUtil.lpad(o.M)+'-'+LineUtil.lpad(o.d)+' '+LineUtil.lpad(o.H)+':'+LineUtil.lpad(o.m)+':'+LineUtil.lpad(o.s);
		}
		if ('HH:mm:ss' == pattern) {
			return LineUtil.lpad(o.H)+':'+LineUtil.lpad(o.m)+':'+LineUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd' == pattern) {
			return LineUtil.lpad(o.y)+'-'+LineUtil.lpad(o.M)+'-'+LineUtil.lpad(o.d);
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
	// build线路，重新生成静态页面
	buildLine : function(id) {
		if (FG_DOMAIN) {	// 有配置则执行
			$.post(FG_DOMAIN+'/build/lxb/buildLineDetail.jhtml', {lineId:id},
				function(data){
				}
			);
		}
	}
};


