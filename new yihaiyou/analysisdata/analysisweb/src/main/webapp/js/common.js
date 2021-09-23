$.ajaxSetup({
	async : true
});
/**自定义表单验证*/
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
	mobile: {
	validator: function (value, param) {
	return /^((\(\d{2,3}\))|(\d{3}\-))?1[3|4|5|8][0-9]\d{4,8}$/.test(value);
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
	           return /^?[1-9]+\d*$/.test(value); 
	       }, 
	       message : '请输入整数' 
	   }, 
	positiveinteger : {// 验证正整数 
	       validator : function(value, param) { 
	           return /^[+]?[1-9]+\d*$/.test(value); 
	       }, 
	       message : '请输入正整数' 
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
	   }/*,
	  intlength : {
		   maxLength:0,
    	   validator : function(value, param) { 
    		   maxLength=param[1];
    		   if(value.length>param[1]){
    			   return false;
    		   }
	           return /^[+]?[1-9]+\d*$/.test(value); 
	       }, 
	       message : '请输入'+maxLength+'整数'
	   }*/

});

/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 *   在easyui.min.js之后导入本js
 *   代码案例:
 *		$("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *		$("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 * @author ____′↘夏悸
 */
$.extend($.fn.datagrid.methods, {
	tooltip : function (jq, fields) {
		return jq.each(function () {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function () {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function () {
				var content = $(this).text();
				$(this).tooltip({
					content : content,
					trackMouse : true,
					onHide : function () {
						$(this).tooltip('destroy');
					}
				}).tooltip('show');
			});
		}
	}
});


$.extend($.fn.datagrid.methods, {
    autoMergeCells : function (jq, fields) {
        return jq.each(function () {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var rows = target.datagrid("getRows");
            var i = 0,
            j = 0,
            temp = {};
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                for (j; j < fields.length; j++) {
                    var field = fields[j];
                    var tf = temp[field];
                    if (!tf) {
                        tf = temp[field] = {};
                        tf[row[field]] = [i];
                    } else {
                        var tfv = tf[row[field]];
                        if (tfv) {
                            tfv.push(i);
                        } else {
                            tfv = tf[row[field]] = [i];
                        }
                    }
                }
            }
            $.each(temp, function (field, colunm) {
                $.each(colunm, function () {
                    var group = this;
                    
                    if (group.length > 1) {
                        var before,
                        after,
                        megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                target.datagrid('mergeCells', {
                                    index : megerIndex,
                                    field : field,
                                    rowspan : rowspan
                                });
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    }
});

//重写日期格式
/*$.extend($.fn.datagrid.defaults.editors, {
    datetimebox: {// datetimebox就是你要自定义editor的名称
        init: function (container, options) {
            var input = $('<input class="easyuidatetimebox">').appendTo(container);
            return input.datetimebox({
                formatter: function (date) {
                    return new Date(date).format("yyyy-MM-dd hh:mm:ss");
                }
            });
        },
        getValue: function (target) {
            return $(target).parent().find('input.combo-value').val();
        },
        setValue: function (target, value) {
            $(target).datetimebox("setValue", value);
        },
        resize: function (target, width) {
            var input = $(target);
            if ($.boxModel == true) {
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    }
});
// 时间格式化
Date.prototype.format = function (format) {
    * eg:format="yyyy-MM-dd hh:mm:ss";
    
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};*/
/** 自定义提示按纽文字*/
$.extend($.messager.defaults,{  
    ok:"确定",  
    cancel:"取消"  
});
/**post提交*/
function doPost(table,url,param){
	$.messager.progress();
	$.post(url,param,
		function(result) {
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
			$('#'+table).datagrid('reload');
		}
	}, 'json');
}

//距离过期还剩一个月的商品弹框 
function validWarning(title,url){
	$('#validNumWindow').window({
		title : title
	});
	$('#validNumWindow').window('open');
	$('#validNumWindow').window('refresh', url);
}

	// 超过库存下限的商品弹框
function reperWarning(title,url){
	$('#reperNumWindow').window({
		title : title
	});
	$('#reperNumWindow').window('open');
	$('#reperNumWindow').window('refresh', url);
}

//采购或生产的到货通知
function purchaseNotice(title,url){
	$('#purNoticeNumWindow').window({
		title : title
	});
	$('#purNoticeNumWindow').window('open');
	$('#purNoticeNumWindow').window('refresh', url);
}
	
/**消息弹出框*/
function showMsg(t,m){
	$.messager.show({
		title:t,
		msg:m,
		timeout:5000,
		showType:'show'
//		style:{
//			right:'',
//			top:document.body.scrollTop+document.documentElement.scrollTop,
//			bottom:''
//		}
	});
}
///////////////////////////////////////数学运算 /////////////////////////////////////////////////////
//加法函数  
function accAdd(arg1, arg2) {  
	if(arg1==null||arg1==""){
	    arg1=0;
	}
	if(arg2==null||arg2==""){
	    arg2=0;
	}
    var r1, r2, m;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));  
    return (arg1 * m + arg2 * m) / m;  
}   
//给Number类型增加一个add方法，，使用时直接用 .add 即可完成计算。   
Number.prototype.add = function (arg) {  
    return accAdd(arg, this);  
};  

//减法函数  
function Subtr(arg1, arg2) {  
	if(arg1==null||arg1==""){
	    arg1=0;
	}
	if(arg2==null||arg2==""){
	    arg2=0;
	}
    var r1, r2, m, n;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));  
     //last modify by deeka  
     //动态控制精度长度  
    n = (r1 >= r2) ? r1 : r2;  
    return ((arg1 * m - arg2 * m) / m).toFixed(n);  
}  
  
//给Number类型增加一个add方法，，使用时直接用 .sub 即可完成计算。   
Number.prototype.sub = function (arg) {  
    return Subtr(this, arg);  
};  

/**
 * 该方法可用，但是不是很严谨， 必须保证传入的参数都是Number类型
 */
//乘法函数
function accMul(arg1, arg2, n) {
	if(arg1==null||arg1==""){
	    arg1=0;
	}
	if(arg2==null||arg2==""){
	    arg2=0;
	}
	if(n==null||n==""){
	    n=0;
	}
	var len = arguments.length; 
	if (len == 2) {
		return this.accMulWith2Params(arg1, arg2);
	}
	else if (len == 3) {
		return this.accMulWith3Params(arg1, arg2, n);
	}
	else {
		/**
		 * 
		 */
	}
}
 
//乘法函数  , 带两个参数
function accMulWith2Params(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();  
    try {  
        m += s1.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        m += s2.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m).toFixed(2);  
}

//乘法函数  ， 带三个参数
function accMulWith3Params(arg1, arg2,n) {  
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();  
    try {  
        m += s1.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        m += s2.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    return (Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)).toFixed(n);  
}   
//给Number类型增加一个mul方法，使用时直接用 .mul 即可完成计算。   
Number.prototype.mul = function (arg) {  
    return accMul(arg, this);  
};   
 

//除法函数  
function accDiv(arg1, arg2, n) {  
	if(arg1==null||arg1==""){
	    arg1=0;
	}
	if(arg2==null||arg2==""){
	    arg2=0;
	}
	if(n==null||n==""){
	    n=0;
	}
	var len = arguments.length; 
	if (len == 2) {
		return this.accDivWith2Params(arg1, arg2);
	}
	else if (len == 3) {
		return this.accDivWith3Params(arg1, arg2, n);
	}
	else {
		/**
		 * 
		 */
	}
}

//除法函数  , 带两个参数
function accDivWith2Params(arg1, arg2) {
    var t1 = 0, t2 = 0;  
    try {  
        t1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        t2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    with (Math) {  
        r1 = Number(arg1.toString().replace(".", ""));  
        r2 = Number(arg2.toString().replace(".", ""));  
        return (r1 / r2) * pow(10, t2 - t1);  
    }  
}
//除法函数  ， 带3个参数
function accDivWith3Params(arg1, arg2,n) {  
    var t1 = 0, t2 = 0;  
    try {  
        t1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        t2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    with (Math) {  
        r1 = Number(arg1.toString().replace(".", ""));  
        r2 = Number(arg2.toString().replace(".", ""));  
        return ((r1 / r2) * pow(10, t2 - t1)).toFixed(n);  
    }  
}   
//给Number类型增加一个div方法，，使用时直接用 .div 即可完成计算。   
Number.prototype.div = function (arg) {  
    return accDiv(this, arg);  
};   
/////////////////////////////////////字符处理/////////////////////////////////////////////////////
/**
 * 检查是否为空、空串、未定义
 * @param checkedVal 受检查的值
 * @returns 如果为空、空串或者未定义，则返回true， 否则为false
 */
function isEmpty(checkedVal) {
	if (checkedVal == '' || checkedVal == null || checkedVal == undefined) {
		return true;
	} else {
		return false;
	}
}

/**
 * 检查是否不为空、空串、未定义的
 * @param checkedVal 受检查的值
 * @returns 如果为空、空串或者未定义，则返回false， 否则为true
 */
function isNotEmpty(checkedVal) {
	return ! isEmpty(checkedVal);
}

/////////////////////////////////////打印/////////////////////////////////////////////////////
/**
 * 打印信息
 * @param datagrid 数据网格
 * @param url 请求地址
 * @param printBtn 打印按钮id
 */
function printInfo(datagrid, url, printBtn,billId){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length<1&&billId==undefined){
		$.messager.alert("温馨提示","请选择要打印的信息");
	}else{
		//批量打印
		batchPrint(rows, url, printBtn,billId);
	}
}
/**
 * @param rows 要打印的数据行
 * @param url 请求地址
 * @param printBtn 打印按钮id
 * @param billId 用于页面打开时打印的单据Id
 */
function batchPrint(rows, url, printBtn,billId){
	$("#"+printBtn).attr("disabled",true);
	var c = getPrinter();
	var arr = c.split(",");
	var index = arr[1];
	var LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
	if(LODOP. SET_PRINTER_INDEX(index)){
		$.messager.progress({
			title:'加载中',
			text:'打印中,请耐心等待...'
		});
		var billIds=[];
		if(rows.length>0){
			for(var i=0;i<rows.length;i++){
				billIds.push(rows[i].id);
			}
		}else{
			billIds.push(billId);
		}
		if(billIds.length>0){
			var reqUrl = url + "billIds="+billIds;
			$.post(reqUrl, function(data){
				if(data.result=="success"){
					$.messager.progress('close');
					eval(data.script);
					LODOP.PREVIEW();
				}else{
					$.messager.progress('close');
					$.messager.alert("温馨提示","打印错误！");
				}
			});
		}
	}
}
//获取打印机
function getPrinter(){ 
	var LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
	return LODOP.GET_FILE_TEXT("c:/printer.txt").replace('\r','').replace('\n','');
}

/**
 * 获取当前日期
 */
function getCurrentDate() {
	//默认查询时间
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	
	var d = date.getDate();
	d = d < 10 ? '0'+d : d;
	var currentDate = year + '-' + month + '-' + d;
	
	return currentDate;
}
/**
 * 动态获取高度，自适应
 * @param topId 头部div的id（如：查询条件）
 * @param tableId 数据部分table的id
 */
/*function initHeight(topId, tableId) {
	//可见区域高度
	var cWidth = document.body.clientHeight;
	//查询条件部分高度
	var topWidth = document.getElementById(topId).scrollHeight;
	//重设表格数据高度
	$("#"+tableId)[0].style.height = cWidth - topWidth ;
}*/
//替换隐藏列的值
function showPurchasePrice(price){
    var hasPermi=$("#show_purchase_price").val();
    if(hasPermi=="1"){
	return price;
    }else{
	return "隐藏";
    }
}
function hasPurPermi(){
    var hasPermi=$("#show_purchase_price").val();
    if(hasPermi=="1"){
	return true;
    }else{
	return false;
    }
}
//隐藏列
function hideColumn(tb,felids){
    if(felids!=null&&felids.length>0){
	for(var i=0;i<felids.length;i++){
	    tb.datagrid('hideColumn',felids[i]);
	}
    }
}

