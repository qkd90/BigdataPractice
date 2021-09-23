<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/26
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
<!--     <link rel="stylesheet" type="text/css" href="/css/ad/addAds.css">-->
<!--     <script type="text/javascript" src="/js/ad/adsUtil.js"></script>  -->
    <title></title>
    <style type="text/css">
    	body {
    		background-color:white;
    		padding:0px;
    	} 
    	label {
    		margin-right:10px;
    	}
    </style>
</head>
<body>
	<div title="贴标签" class="easyui-layout" style="width:650px;height:409px;" id="content">
<!-- 		<div data-options="region:'north',border:'false'" style="height:1%"> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-top: 5px;margin-left: 5px;"  onclick="SelectLabel.saveItems()">保存</a> -->
<!-- 		</div> -->
        <div id="selectLabel-searcher" style="padding:3px">
            <input id="search-content" placeholder="输入标签名称"
                   style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
            <a href="#"  class="easyui-linkbutton" style="width: 80px;" onclick="SelectLabel.doSearch()">查询</a>
        </div>
		<div data-options="region:'center',border:'false'">
			<input type="hidden" id="hi_targetId" value="${targetId}">
			<input type="hidden" id="hi_type" value="${typeStr}">
			<table id="dialog_tree"></table>
		</div>
        <div id="dlg" class="easyui-dialog" title="设置排序" style="width:200px;height:90px;padding:10px;">
            <form id="sortForm">
                <input type="hidden" id="hid_label" value=""/>
                <input class="easyui-textbox" id="sortId" data-options="validType:'number'" style="width:100px;">
                <a href="#" class="easyui-linkbutton" onclick="SelectLabel.subSort()" style="">确定</a>
            </form>
        </div>
	</div>
</body>
<script type="text/javascript" src="/js/labels/selectLabels.js"></script>
</html>
