$.extend($.fn.validatebox.defaults.rules, {
	CHS: {
		validator: function (value, param) {
			return /^[\u0391-\uFFE5]+$/.test(value);
		},
		message: '请输入汉字'
	},
	numSign: {
		validator: function (value, param) {
			return /^[A-Za-z0-9.-]+$/.test(value);
		},
		message: '请输入数字、字母或其他'
	},
	ZIP: {
		validator: function (value, param) {
			return /^[1-9]\d{5}$/.test(value);
		},
		message: '邮政编码不存在'
	},
	QQ: {
		validator: function (value, param) {
			return /^[1-9]\d{4,10}$/.test(value);
		},
		message: 'QQ号码不正确'
	},
	filePath: {
		validator: function(value, param) {
			return /^[a-zA-Z]:\\[a-zA-Z_0-9\\]*/;
		},
		message: '文件路径格式错误'
	},
	mobile: {
		validator: function (value, param) {
			return /^1[3|4|5|6|7|8|9]\d{9,9}$/.test(value);
		},
		message: '手机号码不正确'
	},
	tel: {
		validator: function (value, param) {
			return /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/.test(value);
		},
		message: '固定电话号码不正确'
	},
	loginName: {
		validator: function (value, param) {
			return /^[\u0391-\uFFE5\w]+$/.test(value);
		},
		message: '登录名称只允许汉字、英文字母、数字及下划线。'
	},
	crtacc: {
		validator: function (value, param) {
			return /^(\d{15,20})$/.test(value);
		},
		message: '银行帐号格式不正确'
	},
	loginAccount: {
		validator: function (value, param) {
			return /^(\w){3,15}$/.test(value);
			//return /^[a-zA-Z]{1}[0-9a-zA-Z_]{1,14}$/.test(value);
		},
		message: '登录帐号只能输入3-15个字母、数字、下划线。'
	},
	safepass: {
		validator: function (value, param) {
			return safePassword(value);
		},
		message: '密码由字母和数字组成，至少6位'
	},
	equalTo: {
		validator: function (value, param) {
			return value== $(param[0]).val();
		},
		message: '两次输入的字符不一至'
	},
	number: {
		validator: function (value, param) {
			return /^\d+$/.test(value);
		},
		message: '请输入数字'
	},
	//２０１４－１０－１１ insert from Teny_Lu
	idcard : {// 验证身份证 
	  	validator : function(value, param) { 
	     	return /^\d{15}(\d{2}[A-Za-z0-9])?$/.test(value); 
	  	}, 
	  	message : '身份证号码格式不正确' 
	},
	phone : {// 验证电话号码 
		  validator : function(value, param) { 
		     return /^((\(\d{2,4}\))|(\d{3,4}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/.test(value); 
		  }, 
		  message : '格式不正确,请使用下面格式:010-88888888' 
		},
	intOrFloat : {// 验证整数或小数 
	       validator : function(value, param) { 
	           return /^\d+(\.\d+)?$/.test(value); 
	       }, 
	       message : '请输入数字，并确保格式正确' 
	   },
	currency : {// 验证货币 
	       validator : function(value, param) { 
	           return /^\d+(\.\d+)?$/.test(value); 
	       }, 
	       message : '货币格式不正确' 
	   },
	qq : {// 验证QQ,从10000开始 
	       validator : function(value, param) { 
	           return /^[1-9]\d{4,9}$/.test(value); 
	       }, 
	       message : 'QQ号码格式不正确' 
	   }, 
	integer : {// 验证整数 
	       validator : function(value, param) { 
	           return /^[+]?[1-9]+\d*$/.test(value); 
	       }, 
	       message : '请输入整数' 
	   }, 
	age : {// 验证年龄
	       validator : function(value, param) { 
	           return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/.test(value); 
	       }, 
	       message : '年龄必须是0到120之间的整数' 
	   }, 
	date : {// 验证姓名，可以是中文或英文 
	       validator : function(value, param) { 
	        //格式yyyy-MM-dd或yyyy-M-d
	           return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/.test(value); 
	       },
	       message : '清输入合适的日期格式'
	   },
     letOrNum : {// 验证只能输入英文,下划线或数字 
	       validator : function(value, param) { 
	           return /^\w+$/.test(value); 
	       },
	       message : '只能输入英文,下划线或数字!'
	   }, 
	  maxLength : {// 最大长度
	       validator : function(value, param) { 
	           return value.length <= param[0]; 
	       }, 
	       message : '最多{0}字符' 
	   }

});

	function doPost(table, url, param) {
		$.messager.progress();
		$.post(url, param, function(result) {
			$.messager.progress("close");
			if (!result.success) {
				$.messager.show({
					title : 'Error',
					msg : result.errorMsg
				});
			} else {
				$.messager.show({
					title : '提示',
					msg : result.errorMsg
				});
				$('#' + table).datagrid('reload');
			}
		}, 'json');
	}
	
	function doSubmit(fromid, formaction, param) {
		$.messager.progress();	// 显示进度条  
		$('#'+fromid).form('submit', {  	
		   url: formaction,  	
		   onSubmit: function(){  		
		        var isValid = $(this).form('validate');  		
		        if (!isValid){  			
		            $.messager.progress('close');	//隐藏进度条虽然形式是无效的 		
		        }  		
		        return isValid;	// 返回false,将阻止表单提交 	
		   },  	
		   success: function(result){  		
		        $.messager.progress('close');	// 隐藏进度条同时提交成功	
		        var result = eval('(' + result + ')');
				var title=!result.success?'错误':'提示';
				$.messager.show({
					title : title,
					msg : result.message
				});
		   }  
		});
	}
	
	function selectSearchBox(){
		$(".searchdiv").find(":text").focus();
		$(".searchdiv").find(":text").mouseenter();
		$(".searchdiv").find(":text").focus(function(){
			  $(this).select();
		}).mouseenter(function(){
			$(this).select();
			$(this).focus();
			$(this).click();
		});
		$(".searchdiv").find("a").mouseenter(function(){
			$(this).focus();
		});
		foucsLinkBtn();
		
	}
	function foucsLinkBtn(){
		$(".easyui-linkbutton").mouseenter(function(){
			$(this).focus();
		}).mouseleave(function(){
			$(this).blur();
		});
		$(":button").mouseenter(function(){
			$(this).focus();
		}).mouseleave(function(){
			$(this).blur();
		});
		$("a").mouseenter(function(){
			$(this).focus();
		}).mouseleave(function(){
			$(this).blur();
		});
	}
	function showLoading()
    {
        document.getElementById("over").style.display = "block";
        document.getElementById("layout").style.display = "block";
    }
	function hideLoading()
    {
        document.getElementById("over").style.display = "none";
        document.getElementById("layout").style.display = "none";
    }
	function showimg(url,id,name){
		if(url!=""){
			$("#showimg").find("img").attr("src",url);
			$("#showimg").find("span").html(name);
			$("#showimg").show();
		}else if(id!=""){
			var param={'id':id};
			$.post('/product/product/searchProduct.jhtml', param, function(result) {
				if (!result.success) {
					$("#showimg").find("img").attr("src",'/Upload/product/nopic.tmp');
				} else {
					$("#showimg").find("img").attr("src",result.defaultImg);
					$("#showimg").find("span").html(result.proName);
				}
				$("#showimg").show();
			}, 'json');
		}
	}
	$(function() {
		selectSearchBox();
		$("#showimg").click(function(){
			$(this).hide();
		});
	});
	
	function show_msg(result){
		$.messager.show({
			title:'温馨提示',
			showType:'show',
			msg:result,
			timeout:2000,
			 style:{
	                right:'',
	                bottom:''
	            }

		});
	}
    function showMsgPlus(title, msg, timeout) {
        if(timeout == null || timeout <= 0 || timeout == "") {
            timeout = 2000;
        }
        $.messager.show({
            title: title,
            showType:'fade',
            msg:msg,
            timeout: timeout,
            style:{
                right:'',
                bottom:''
            }
        });
    }
/*源头来自:soutu_bgmgr项目:static/base.js*/
/** 常量 */
var BizConstants = {
	PAGESIZE : 15,
	PAGELIST : [15],
	QINIU_DOMAIN : QINIU_BUCKET_URL,
	COMMON_FLAG: [{'id':'','text':'请选择...','filter':true},{'id':'T','text':'是'},{'id':'F','text':'否'}],
	COMMON_YN: [{'id':'','text':'请选择...','filter':true},{'id':'1','text':'是'},{'id':'2','text':'否'}],
	SEX: [{'id':'','text':'请选择...','filter':true},{'id':'1','text':'男'}, {'id':'2','text':'女'}, {'id':'3','text':'保密'}],
	COMPANY_STATUS: [{'id':'1','text':'待审核'},{'id':'2','text':'经营中'},{'id':'3','text':'下架'},{'id':'3','text':'拒绝'}],
	COMPANY_CHECKTYPE: [{'id':'1','text':'申请商户'},{'id':'2','text':'资料修改'},{'id':'3','text':'重新上架'}],
	RECLOC : [{'id':'','text':'请选择...','filter':true},{'id':'1','text':'TOP1'}, {'id':'2','text':'TOP2'}, {'id':'3','text':'TOP3'}, {'id':'99','text':'无'}],
	RECPLAN_STATUS: [{'id':'','text':'请选择...','filter':true},{'id':'1','text':'草稿'},{'id':'2','text':'上架'},{'id':'3','text':'下架'}],
	TRIP_TYPE: [{'id':'','text':'请选择...','filter':true},{'id':'1','text':'景点'},{'id':'3','text':'酒店'},{'id':'2','text':'美食'},{'id':'4','text':'交通'}],

	getConstants : function(code, filter) {
		var resultArray = [];
		var codeArray = BizConstants[code];
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
var BgmgrUtil = {
	// 根据代码类型和代码取对应中文名称
	getCodeName : function(codeType, code) {
		if (codeType) {
			var codeArray = BizConstants[codeType];
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
			return BgmgrUtil.getCodeName(this.codeType, value);
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
        var date = null;
        if (typeof value != 'object') {
            date = new Date(value);
        } else {
            date = new Date(value.time);
        }
		return BgmgrUtil.dateToString(date, this.datePattern);
	},
	// 日期时间转换（非表格）
	dateTimeFmt2 : function(value, datePattern) {
		if (!value || !datePattern) {
			return '';
		}
		var date = null;
		if (typeof value != 'object') {
			date = new Date(value);
		} else {
			date = new Date(value.time);
		}
		return BgmgrUtil.dateToString(date, datePattern);
	},

	// 时间数值转为字符串，pattern为“yyyy-MM-dd HH:mm:ss”子串
	dateNumToString : function(dateNum, pattern) {
		if (!dateNum) {
			return '';
		}
		var date = new Date(dateNum);
		return BgmgrUtil.dateToString(date, 'yyyy-MM-dd HH:mm:ss');
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
			return o.y+'-'+BgmgrUtil.lpad(o.M)+'-'+BgmgrUtil.lpad(o.d)+' '+BgmgrUtil.lpad(o.H)+':'+BgmgrUtil.lpad(o.m)+':'+BgmgrUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd HH:mm' == pattern) {
			return o.y+'-'+BgmgrUtil.lpad(o.M)+'-'+BgmgrUtil.lpad(o.d)+' '+BgmgrUtil.lpad(o.H)+':'+BgmgrUtil.lpad(o.m);
		}
		if ('HH:mm:ss' == pattern) {
			return BgmgrUtil.lpad(o.H)+':'+BgmgrUtil.lpad(o.m)+':'+BgmgrUtil.lpad(o.s);
		}
		if ('yyyy-MM-dd' == pattern) {
			return BgmgrUtil.lpad(o.y)+'-'+BgmgrUtil.lpad(o.M)+'-'+BgmgrUtil.lpad(o.d);
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
	settlementFmt : function(billType, billDays) {
		if (billType == 'T0' || billType == 'T1' || billType == 'TN') {
			return 'T+' + billDays;
		} else if (billType == 'month') {
			return "每月" + billDays + "日";
		} else if (billType == 'week') {
			var weekday = '';
			switch(billDays) {
				case 1:
					weekday = '日';
					break;
				case 2:
					weekday = '一';
					break;
				case 3:
					weekday = '二';
					break;
				case 4:
					weekday = '三';
					break;
				case 5:
					weekday = '四';
					break;
				case 6:
					weekday = '五';
					break;
				case 7:
					weekday = '六';
					break;
				default:;
			}
			return "每周" + weekday;    // 星期日为1
		} else if (billType == 'D0' || billType == 'D1' || billType == 'DN') {
			return 'D+' + billDays;
		} else {
			return '';
		}
	}
};


