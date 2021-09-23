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
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
    <script type="text/javascript" src="/js/common/labelMgrDg.js"></script>
    <script type="text/javascript" src="/js/labels/labelsManage.js"></script>
    <title></title>
    <style type="text/css">
    	body {
    		background-color:white;
    		padding:2px;
    	}
    	label {
    		margin-right:10px;
    	}
        .opt a{
            color: #0000cc;
            text-decoration: underline;
        }
    </style>
</head>
<body>

	<div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north',border:false" style="height:10%;">
			<div style="margin:10px;">
				<label>
					<select id="sea_type" class="easyui-combobox" data-options="prompt:'请选择产品类别'" style="width:200px;">
<!-- 						<option value="CITY">城市</option> -->
					</select>
				</label>
<!-- 				<label><input id="sea_name" class="easyui-combobox" data-options="prompt:'请输入标签名称'" style="width:200px;"></label> -->
				<%--<label>
					<input type="hidden" id="hidden_areaId">
					<input id="qryCity" class="easyui-textbox" data-options="buttonText:'×',editable:false,prompt:'点击选择城市'" style="width:200px" data-country="" data-province="" data-city="">
				</label>--%>
				<label><input id="sea_name" class="easyui-textbox" data-options="prompt:'请输入产品名称'" style="width:200px;"></label>
				<label><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LabelManage.searchLabelItem()">查询</a></label>
				<label><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LabelManage.clearSearch()">重置</a></label>
			</div>
		</div>
		<div data-options="region:'west', border:true, split:true, collapsible:false" title="标签" style="width:20%;">
			<lu id="left_tree"></lu>
		</div>
		<div data-options="region:'center', border:true, collapsible:false" title="产品列表">
			<input type="hidden" id="hidden_targetId" value="">
			<table id="tgrid"></table>
			
		</div>

		<!--
		<div id="dlg" class="easyui-dialog" title="选择标签"  style="width:650px;height:445px;">
		
			 <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
				
		</div>
		<div id="sort_dlg" class="easyui-dialog" title="设置排序" style="width:200px;height:90px;padding:10px;">
			<form id="sortForm">
				<input type="hidden" id="hid_labId" value=""/>
				<input type="hidden" id="hid_targetId" value=""/>
				<input class="easyui-textbox" id="sortId" data-options="validType:'number'" style="width:100px;">
				<a href="#" class="easyui-linkbutton" onclick="LabelManage.subSort()"  style="">确定</a>
			</form>
		</div>
		-->
	</div>

</body>
</html>
