<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/26
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript">
        var FG_DOMAIN = '<s:property value="fgDomain"/>';
    </script>
    <!--     <link rel="stylesheet" type="text/css" href="/css/ad/addAds.css">-->
    <script type="text/javascript" src="/js/labels/labels.js"></script>
<!--     <script type="text/javascript" src="/js/ad/adsUtil.js"></script>  -->
    <title></title>
    <style type="text/css">
    	body {
    		background-color:white;
    	}
    	td {
    		padding:4px;
    	}
    	label{
    		margin-right:30px;
    		
    	}
        .textbox-text-readonly {color:gray;}
    </style>
</head>
<body>
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
		<div id="labelListId" style="padding:5px 0 0 5px;">
			<input id="qryKeyword" class="easyui-textbox" data-options="prompt:'请输入标签名称'" style="width:250px;">
			<!--<label>状态：
                <select id="sea_status" class="easyui-combobox" data-options=""  style="width:100px;">
                    <option value="">请选择状态</option>
                    <option value="USE">正常</option>
                    <option value="IDLE">隐藏</option>
                </select>
            </label>-->
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.doSearch()">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.addNodes()">添加</a>
			<!--<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.editNodes()">编辑</a>-->
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.delNodes()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.openMoveNodeDg()">移动</a>
		</div>
			<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false" >
			<table id="tg"></table>
		</div>
	</div>

	<!-- 标签编辑窗口 -->
	<div id="editNodeDg" title="标签编辑" class="easyui-dialog" data-options="closed:true,modal:true" style="width:400px;height:350px;padding:10px">
		<form id="nodeForm" method="post">
            <input type="hidden" id="parentId" name="parentId" value="" >
            <input type="hidden" id="labelId" name="id" value="" >
			<table>
				<tr>
					<td width="80" align="right">父级标签：</td>
					<td>
						<input name="parentName" class="easyui-textbox" data-options="readonly:true,prompt:'顶级标签'" style="width:240px;">
					</td>
				</tr>
				<tr>
					<td align="right">标签名称：</td>
					<td>
						<input class="easyui-textbox" name="name" data-options="required:true,validType:'maxLength[100]'" style="width:240px;">
					</td>
				</tr>
                <tr>
                    <td align="right">标签别名：</td>
                    <td>
                        <input class="easyui-textbox" name="alias" data-options="required:true,validType:'maxLength[100]'" style="width:240px;">
                    </td>
                </tr>
				<tr>
					<td align="right">标签状态：</td>
					<td>
						<select class="easyui-combobox" name="status" style="width:120px;">
						    <option value="USE">正常</option>   
						    <option value="IDLE">隐藏</option>   
						</select>
					</td>
				</tr>
                <tr>
                    <td align="right">标签排序：</td>
                    <td>
                        <input class="easyui-numberbox" name="sort" data-options="min:1,max:999" style="width:120px;">
                    </td>
                </tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="Labels.saveNodes()">保存</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

    <!-- 产品标签窗口 -->
    <div id="productLabelDg" class="easyui-dialog" title="产品标签" style="width:680px;height:460px;"
         data-options="resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
        <!-- 表格工具条 始 -->
        <div id="productLabelTb">
            <div style="padding:2px 5px;">
                <input id="qryTargetType" style="width:120px;"/>
				<%--<input id="qryCity" class="easyui-textbox" data-options="buttonText:'×',editable:false,prompt:'点击选择城市'" style="width:200px" data-country="" data-province="" data-city="">--%>
                <%--<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.searchProduct()">查询</a>--%>
				<!--<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Labels.closeProductLabelDg()">关闭</a>-->
				<label style="color: red;line-height: 24px;float: right;">(排序时请确保只有类型查询条件)</label>
            </div>
        </div>
        <!-- 表格工具条 终 -->
        <!-- 数据表格 始 -->
        <table id="productLabelGrid"></table>
        <!-- 数据表格 终-->
    </div>

	<!-- 标签编辑窗口 -->
	<div id="moveNodeDg" title="标签移动" class="easyui-dialog" data-options="closed:true,modal:true,onClose:function(){$('#moveLabel').combobox('setValue', '');}" style="width:380px;height:160px;padding:10px">
		<form id="moveNodeForm">
			<table>
				<tr>
					<td colspan="2" style="color:red;">
						标签为空时默认移动到顶层标签，选中标签不可移动到该标签或者该标签子标签下。
					</td>
				</tr>
				<tr>
					<td width="80" align="right">移动到标签：</td>
					<td>
                        <input id="moveLabel" style="width:240px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="Labels.moveNodes()">确认移动</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
