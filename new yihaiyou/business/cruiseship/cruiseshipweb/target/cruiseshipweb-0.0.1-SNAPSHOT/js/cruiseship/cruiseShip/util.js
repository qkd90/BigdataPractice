/** 常量 */
var CruiseShipConstants = {
	// 基本信息图片
	infoImg:'cruiseship/info/',
    // 甲板信息图片
    deckImg: 'cruiseship/deck/',
	// 房间信息图片
	roomImg:'cruiseship/room/',
	//项目信息图片
	projectImg:'cruiseship/project/',
	// 是否标志
	booleanFlag : [{'id':'','text':'全部', filter: true},{'id':true,'text':'是'},{'id':false,'text':'否'}],
	productStatus: [{id: 'UP', text: '已上架'}, {id: 'REFUSE', text: '被拒绝'}, {id: 'DOWN', text: '已下架'}, {id: 'CHECKING', text: '待审核'}],
	// 状态
	status : [{'id':'','text':'邮轮状态'},{'id':'UP','text':'上架'},{'id':'DOWN','text':'下架'}, {'id': 'DEL', 'text': '删除'}, {'id': 'CHECKING', 'text': '待审核'}, {'id': 'FAIL', 'text': '审核未通过'}],
	qry_status : [{'id':'','text':'邮轮状态'},{'id':'UP','text':'上架'},{'id':'DOWN','text':'下架'}],
	show_status:[{'id':'','text':'项目名称'},{'id':'1','text':'服务'},{'id':'2','text':'美食'},{'id':'3','text':'娱乐'}],
	// 房间类型
	roomType : [{'id':'','text':'房间类型', filter: true},{'id':'inside','text':'内舱房'},{'id':'seascape','text':'海景房'},{'id':'balcony','text':'阳台房'},{'id':'suite','text':'套房'}],
	projectType:[{'id':'','text':'项目类型', filter: true},{'id':'1','text':'服务'},{'id':'2','text':'美食'},{'id':'3','text':'娱乐'}],
	getConstants : function(code, filter) {
		var resultArray = [];
		var codeArray = CruiseShipConstants[code];
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
var CruiseShipUtil = {
	// 根据代码类型和代码取对应中文名称
	getCodeName : function(codeType, code) {
		if (codeType) {
			var codeArray = CruiseShipConstants[codeType];
			for (var i = 0; i < codeArray.length; i++) {
				if (codeArray[i].id === code) {
					return codeArray[i].text ;
				}
			}
		}
		return '';
	},
	// 表格代码转换
	codeFmt : function(value, rowData, rowIndex) {
		if ((value || value === false) && this.codeType) {
			return CruiseShipUtil.getCodeName(this.codeType, value);
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
		return CruiseShipUtil.dateToString(date, this.datePattern);
	},
	// 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
	dateNumToString : function(dateNum, pattern) {
		if (!dateNum) {
            return '';
        }
		var date = new Date(dateNum);
		return CruiseShipUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
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
			return o.y+'-'+CruiseShipUtil.lpad(o.M)+'-'+CruiseShipUtil.lpad(o.d)+' '+CruiseShipUtil.lpad(o.H)+':'+CruiseShipUtil.lpad(o.m)+':'+CruiseShipUtil.lpad(o.s);
		}
		if ('HH:mm:ss' == pattern) {
			return CruiseShipUtil.lpad(o.H)+':'+CruiseShipUtil.lpad(o.m)+':'+CruiseShipUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd' == pattern) {
			return CruiseShipUtil.lpad(o.y)+'-'+CruiseShipUtil.lpad(o.M)+'-'+CruiseShipUtil.lpad(o.d);
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
	// build游艇帆船，重新生成静态页面
	buildCruiseship : function(id) {
		if (FG_DOMAIN) {	// 有配置则执行
			$.post(FG_DOMAIN + '/build/yhy/buildOneCruiseship.jhtml', {id: id},
				function (data) {
				}
			);
		}
	},
	buildAllCruiseship: function(id) {
		if (FG_DOMAIN) {	// 有配置则执行
			$.post(FG_DOMAIN + '/build/yhy/buildAllCruiseship.jhtml', {id: id},
				function (data) {
				}
			);
		}
	}
};


