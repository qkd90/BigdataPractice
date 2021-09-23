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

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
<script type="text/javascript" src="/js/hotel/hotelUtil.js"></script>
<script type="text/javascript" src="/js/hotel/addStep1.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<form id="editForm" method="post">
			<input id="productId" name="productId" type="hidden"/>
			<table style="float:left;">
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>名称:</td>
					<td>
						<input class="easyui-textbox" name="hotel.name" style="width:200px;" data-options="required:true, validType:['maxLength[50]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>民宿封面图:</td>
					<td>
						<div id="imgView" style="display: none;">
							<img alt="" src="" width="240" height="160" style="border-radius: 5px;">
							<i class="iconfont" onclick="addStep1.delDescPic()" style="cursor:pointer; color: rgb(252, 109, 83);position: relative;top: -145px;left: -22px;">&#xe60d;</i>
						</div>
						<div class="btn_class">
							<input id="filePath" name="hotel.cover" type="hidden"/>
							<input type="button" class="J_add_pic" id="add_descpic" onclick="" value="添加描述图">
						</div>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>所属区域:</td>
					<td>
						<input class="easyui-combobox" name="hotel.provienceId" style="width: 100px;" id="com_province" data-options="required:true, prompt:'请选择省', editable:false"/>
						<input class="easyui-combobox" name="hotel.cityId" style="width: 100px;" id="com_city" data-options="required:true, prompt:'请选择市', editable:false"/>
						<%--<input class="easyui-combobox" style="width: 100px;" id="com_region" data-options="required:true, prompt:'请选择区/县', editable:false"/>--%>
						<input class="easyui-textbox" style="width: 250px;" id="text_region_detail" name="hotel.extend.address" data-options="validType:['maxLength[30]']"/>
						<%--<input type="hidden" id="hotel_address" name="hotel.extend.address">--%>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>星级:</td>
					<td>
						<input class="easyui-combobox" name="hotel.star" value="${hotel.star}" style="width: 150px;" id="com_start"
							   data-options="
							   prompt:'星级',
							   editable:false,
							   valueField: 'id',
							   textField: 'value',
							   data: [{
											id: '0',
											value: '未评定星级',
											selected:true
										},{
											id: '1',
											value: '一星'
										},{
											id: '2',
											value: '二星'
										},{
											id: '3',
											value: '三星'
										},{
											id: '4',
											value: '四星'
										},{
											id: '5',
											value: '五星'
										}]" />

					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>民宿相册:</td>
					<td>
						<div id="demo" style="width: 650px; /*background: #CF9; min-height: 200px;*/">
							<div id="as">
							</div>
						</div>
						<input type="hidden" name="folder" value="/hotel/hotelDesc">
						<input type="hidden" id="imageUrls" name="imageUrls">
					</td>
				</tr>
				<tr>
					<td class="label">民宿主题:</td>
					<td>
						<input class="easyui-combobox" name="hotel.theme" style="width: 300px;" id="com_hotelTheme" data-options=" multiple:true, prompt:'民宿主题', editable:false"/>
					</td>
				</tr>
				<tr>
					<td class="label">民宿休闲:</td>
					<td>
						<input class="easyui-combobox" name="hotel.recreationAmenities" style="width: 300px;" id="com_recreationAmenities" data-options="multiple:true, prompt:'民宿休闲', editable:false"/>
					</td>
				</tr>
				<tr>
					<td class="label">民宿服务:</td>
					<td>
						<input class="easyui-combobox" name="hotel.serviceAmenities" style="width: 300px;" id="com_serviceAmenities" data-options="multiple:true, prompt:'民宿服务', editable:false"/>
					</td>
				</tr>
				<tr>
					<td class="label">民宿设施:</td>
					<td>
						<input class="easyui-combobox" name="hotel.generalAmenities" style="width: 300px;" id="com_generalAmenities" data-options="multiple:true, prompt:'民宿设施', editable:false"/>
					</td>
				</tr>
				<tr>
					<td class="label">备注:</td>
					<td>
						<input class="easyui-textbox" name="hotel.shortDesc" style="width: 700px; height: 120px;" id="com_hotelShortDesc" data-options="prompt:'民宿备注', multiline:true, validType:['maxLength[300]']"/>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>交通指南:</td>
					<td>
						<div id="baiduMap" style="border-radius: 5px; width: 700px; height: 300px; background-color: #9ad2d2"></div>
						<div style="position: relative; top: -285px; left: 582px; width: 150px;">
							<div style="margin:5px;">
								<input id="map_lng" class="easyui-textbox" name="hotel.extend.longitude" style="width: 100px;"  data-options="required:true, prompt:'经度'"/>
							</div>
							<div style="margin:5px;">
								<input id="map_lat" class="easyui-textbox" name="hotel.extend.latitude" style="width: 100px;"  data-options="required:true, prompt:'纬度'"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>民宿简介:</td>
					<td>
						<textarea id="hotelInfoDesc" name="hotel.extend.description"
								  style="width: 700px; height: 300px; visibility: hidden;"></textarea>
						<span>还可以输入<span id="text_count" style="font-weight: 600;">2000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>是否需要确认:</td>
					<td>
						<label style="margin-right: 12px;">
							<input type="radio" name="hotel.needConfirm" value="true"/>
							需要确认
						</label>
						<label>
							<input type="radio" checked="checked" name="hotel.needConfirm" value="false"/>
							无需确认
						</label>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>排序:</td>
					<td>
						<input class="easyui-numberspinner" name="hotel.ranking" style="width: 100px;" id="com_hotelRanking" data-options="required:true, min:0"/>
					</td>
				</tr>
			</table>
	    </form>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:left;padding:5px;height:30px;margin-left: 77px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep1.nextGuide()">保存，下一步</a>
		</div>  
	</div>
</div>
</body>
</html>
