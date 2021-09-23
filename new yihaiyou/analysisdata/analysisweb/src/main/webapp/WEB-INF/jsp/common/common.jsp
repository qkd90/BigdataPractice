<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.3.6/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.3.6/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.3.6/themes/color.css">
<script type="text/javascript" src="/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/jquery.jdirk.js"></script>
<script type="text/javascript" src="/js/jquery.hotkeys.js"></script>
<link rel="stylesheet" type="text/css" href="/css/common.css">
<!-- 导入easyui 扩展js,及其它通用 -->
<!-- 当前页面统计信息插件 -->
<script type="text/javascript" src="/jquery-easyui-1.3.6/easyui.plugin.js">
</script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	var admin='<s:property value="#session.loginuser" />';
	if(admin==null||admin==''||admin=="null"){
		window.location.href="/sys/login/login.jhtml";
	}
	function checkPerm(resourceUrl){
		var temp; 
		$.ajax({ 
			async: false, 
			type : "POST", 
			url : '/manager/system/checkPermission.jhtml?resourceUrl='+resourceUrl, 
			dataType : 'json', 
			success : function(result) { 
				if(result!=null&&result.success){
					temp= true;
	  			}else{
	  				temp= false;
	  			}
			} 
		}); 
		return temp;
	}
	
	//h: 额外的高度
	function initHeight(topId, dataId, h) {
		if (h != null && h != '' && h != undefined) {
			// g   /global   代表全局搜索  , 搜索这个字符串
			// i   /ignore   代表忽略大小写 
			h = h.replace(/[^0-9]/ig,""); 
		} else {
			h = 0;
		}
		//可见区域高度
		var cWidth = document.body.clientHeight;
		//查询条件部分高度
		var topWidth = document.getElementById(topId).scrollHeight;
		//重设表格数据高度
		$("#"+dataId)[0].style.height = cWidth - topWidth * 2  - h ;
	}
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>