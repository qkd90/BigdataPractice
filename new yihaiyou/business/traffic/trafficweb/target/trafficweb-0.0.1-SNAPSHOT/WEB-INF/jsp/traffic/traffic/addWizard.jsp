<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑向导框</title>
<%@ include file="../../common/common141.jsp"%>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div id="ifrSet" title="新增交通" data-options="region:'center',border:true, tools:'#dsTools'">
		<div id="step1" title="" class="easyui-panel" 
			data-options="fit:true, closed:true, minimizable:false, maximizable:false, collapsible:false,border:false">
			<iframe name="step1Iframe" id="step1Iframe" scrolling="no" frameborder="0"  style="width:100%;height:670px;"></iframe>
		</div>
		<div id="step2" title="" class="easyui-panel" 
			data-options="fit:true, closed:true, minimizable:false, maximizable:false, collapsible:false,border:false">
			<iframe name="step2Iframe" id="step2Iframe" scrolling="no" frameborder="0"  style="width:100%;height:670px;"></iframe>
		</div>
		<div id="step3" title="" class="easyui-panel" 
			data-options="fit:true, closed:true, minimizable:false, maximizable:false, collapsible:false,border:false">
			<iframe name="step3Iframe" id="step3Iframe" scrolling="no" frameborder="0"  style="width:100%;height:99%;"></iframe>
		</div>
		<div id="step4" title="" class="easyui-panel" 
			data-options="fit:true, closed:true, minimizable:false, maximizable:false, collapsible:false,border:false">
			<iframe name="step4Iframe" id="step4Iframe" scrolling="no" frameborder="0"  style="width:100%;height:99%;"></iframe>
		</div>
		<div id="dsTools">
			<a href="javascript:void(0)" class="" style="width:28px;" onClick="closeChildPanel()">返回</a>
		</div>
	</div>	
</div>
</body>
</html>
<script type="text/javascript">	
	// 页面流
	var pages = [{winIndex:1, winId:"step1", title:"第一步：添加交通基本信息"},
         {winIndex:2, winId:"step2", title:"第二步：交通坐席管理"},
         {winIndex:3, winId:"step3", title:"第三步：交通发布完成"}];
//         {winIndex:4, winId:"step4", title:"第四步：线路发布成功", url:"/line/line/addStep4.jhtml"}];

	// 获取iframe的数据
	function getIfrData(winId) {	
		var ifr = $("#"+winId).children()[0];
		return ifr.contentWindow.getIfrData();
	}
	
	// 关闭
	function closeChildPanel() {
		//var url = '/line/line/manage.jhtml';
		//window.location.href = url;
//		alert("aaa");
		parent.window.TrafficManage.closeEditPanel(true);
	}
	
	// 显示页面
	function showGuide(winIndex, isRefresh, url) {
		// 显示当前窗口，隐藏其他窗口
		var title = "";
		for (var i = 0; i < pages.length; i++) {
			if (winIndex == pages[i].winIndex) {
//				var url = pages[i].url;
				var ifr = $("#"+pages[i].winId).children()[0];
				var src = $(ifr).attr("src");
				if (!src || src.length <= 0) {
					$(ifr).attr("src", url);
				} else {	// 否则调用iframe的初始方法（相关iframe要提供该方法）
					//ifr.contentWindow.initData();
					if (isRefresh) {	// 是否刷新页面
						ifr.contentWindow.location.reload();
					}
				}
				$("#"+pages[i].winId).panel("open");
				title = title + "<font color='#FF9900'>" + pages[i].title + "</font> 》 ";
			} else {
				$("#"+pages[i].winId).panel("close");
				title = title + "<font color='#CCCCCC'>" + pages[i].title + "</font> 》 ";
			}
		}
		// 设置标题
		title = title.substring(0, title.length-3);
		title = "<font color='#CCCCCC'>" + title + "</font>";
		$("#ifrSet").panel("setTitle", title);
	}

	// 初始页面设置
	function initPage() {
		showGuide(1, true, "/traffic/traffic/addStep1.jhtml");
	}
	
	// 页面加载后相关设置项
	$(function(){
		
		initPage();
		
	});		
	
</script>	