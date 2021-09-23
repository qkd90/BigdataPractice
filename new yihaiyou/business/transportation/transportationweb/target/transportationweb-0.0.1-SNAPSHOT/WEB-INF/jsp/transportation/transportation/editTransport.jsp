<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
	<%@ include file="../../common/common141.jsp"%>
<%--<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>--%>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css"/>
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/hotel/addWizard.css"/>
	<link rel="stylesheet" type="text/css" href="/css/hotel/iconfont/iconfont.css"/>

	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
	<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
	<script type="text/javascript" src="/js/extends/jquery.radio.js"></script>
	<script type="text/javascript" src="/js/transportation/trasportUtil.js"></script>
	<script type="text/javascript" src="/js/transportation/editTransport.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<form id="editForm" method="post">
			<input name="transportation.id" value="${transportation.id}" type="hidden"/>
			<table style="float:left;">
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>车站名称:</td>
					<td>
						<input class="easyui-textbox" name="transportation.name" value="${transportation.name}" style="width:200px;" data-options="validType:['maxLength[40]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>站点类型:</td>
					<td>
						<input id="transportType" name="transportation.type" value="${transportation.type}" class="easyui-combobox"
							   data-options="prompt:'站点类型',
										   		valueField:'id',
										   		textField:'text',
										   		editable:false,
										   		required: true,
										   		data:[
													{id:'1',text:'火车站'},
													{id:'2',text:'机场'},
													{id:'3',text:'汽车站'},
													{id:'4',text:'码头'}
										   		]
							" style="width:120px;">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>封面图:</td>
					<td>
						<div id="imgView" style="display: none;">
							<img alt="" src="${transportation.coverImg}" width="240" height="160" style="border-radius: 5px;">
							<i class="iconfont" onclick="EditTransport.delDescPic()" style="cursor:pointer; color: rgb(252, 109, 83);position: relative;top: -145px;left: -22px;">&#xe60d;</i>
						</div>
						<div class="btn_class">
							<input id="filePath" name="transportation.coverImg" value="${transportation.coverImg}" type="hidden"/>
							<input type="button" class="J_add_pic" id="add_descpic" onclick="" value="添加封面图">
						</div>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>联系电话:</td>
					<td>
						<input class="easyui-textbox" name="transportation.telephone" value="${transportation.telephone}" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>所属城市:</td>
					<td>
						<input type="hidden" id="hidden_startcityName" name="transportation.cityName" value="${transportation.cityName}">
						<input type="hidden" id="hidden_startcityCode" name="transportation.cityCode" value="${transportation.cityCode}">
						<input type="hidden" id="hidden_startCityId" name="transportation.cityId" value="${transportation.cityId}">
						<input id="startCityId" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="" data-province="" data-city="">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>区域名称:</td>
					<td>
						<input class="easyui-textbox" name="transportation.regionName" value="${transportation.regionName}" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>详细地址:</td>
					<td>
						<input id="ipt_address" class="easyui-textbox" name="transportation.address" value="${transportation.address}" style="width:250px;" data-options="buttonText:'x', required: true, prompt:'如：福建省厦门市思明区软件园二期'">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>经纬度:</td>
					<td>
						<div id="baiduMap" style="border-radius: 5px; width: 700px; height: 300px; background-color: #9ad2d2"></div>
						<div style="position: relative; top: -285px; left: 582px; width: 150px;">
							<div style="margin:5px;">
								<input id="map_lng" class="easyui-textbox" name="transportation.lng" value="${transportation.lng}" style="width: 100px;"  data-options="required:true, prompt:'经度'"/>
							</div>
							<div style="margin:5px;">
								<input id="map_lat" class="easyui-textbox" name="transportation.lat" value="${transportation.lat}" style="width: 100px;"  data-options="required:true, prompt:'纬度'"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>设置状态:</td>
					<td>
						<input name="transportation.status" value="${transportation.status}" class="easyui-combobox"
							   data-options="prompt:'状态',
										   		valueField:'id',
										   		textField:'text',
										   		editable:false,
										   		required: true,
										   		data:[
													{id:'-1',text:'失效/无用'},
													{id:'0',text:'可用但旅行帮无交通接口'},
													{id:'1',text:'可用且有交通接口'},
										   		]
							" style="width:120px;">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>代码:</td>
					<td>
						<input class="easyui-textbox" name="transportation.code" value="${transportation.code}" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>查询关键词:</td>
					<td>
						<input class="easyui-textbox" name="transportation.searchName" value="${transportation.searchName}" style="width:200px;" data-options="prompt:'如：厦门市 厦门火车站', required:true, validType:['maxLength[20]']">
					</td>
				</tr>
			</table>
	    </form>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:right;padding:5px;height:30px;margin-right: 15px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="EditTransport.nextGuide()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="EditTransport.cancelEdit()">取消</a>
		</div>  
	</div>
</div>
</body>
</html>
