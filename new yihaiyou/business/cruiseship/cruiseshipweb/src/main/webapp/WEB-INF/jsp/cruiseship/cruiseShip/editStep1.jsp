<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
	<%@ include file="../../common/common141.jsp"%>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseShip/addWizard.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
    <script type="text/javascript" src="/js/cruiseship/cruiseShip/editStep1.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
		<form id="editForm" method="post">
		<input id="productId" name="cruiseShip.id" type="hidden" value="<s:property value="cruiseShip.id"/>"/>
		<div class="row_hd">基本信息</div>
		<table>
        	<tr>
			   	<td class="label">产品编号:</td>
			   	<td>
			   		<input class="easyui-textbox" name="cruiseShip.productNo" value="<s:property value="cruiseShip.productNo"/>" style="width:180px;" readonly="readonly" data-options="prompt:'系统自动生成'">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><span style="color: red">*&nbsp;</span>邮轮名称:</td>
			   	<td>
			   		<input class="easyui-textbox" name="cruiseShip.name" value="<s:property value="cruiseShip.name"/>" style="width:360px;" required="true" data-options="validType:'maxLength[100]'">
			   		<span class="tip">最多<span class="green-bold">100</span>个字符</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><span style="color: red;">*&nbsp;</span>出发城市:</td>
				<td>
					<input type="hidden" id="startCityId" name="cruiseShip.startCityId" value="<s:property value="cruiseShip.startCityId"/>">
					<input id="startCity" name="cruiseShip.startCity" value="<s:property value="cruiseShip.startCity"/>"
                           class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="" data-province="" data-city="">
                </td>
			</tr>
			<tr>
				<td class="label"><span style="color: red">*&nbsp;</span>到达城市:</td>
				<td>
					<input type="hidden" id="arriveCityId" name="cruiseShip.arriveCityId" value="<s:property value="cruiseShip.arriveCityId"/>">
					<input id="arriveCity" name="cruiseShip.arriveCity" value="<s:property value="cruiseShip.arriveCity"/>" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false, prompt:'点击选择到达城市'" style="width:200px" data-country="" data-province="" data-city="">
				</td>
			</tr>
        	<tr>
			   	<td class="label"><span style="color: red">*&nbsp;</span>提前报名:</td>
			   	<td>
                    <span style="line-height: 18px;">请提前</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.attend" value="<s:property value="cruiseShip.attend"/>" required="required" data-options="min:1,max:100,editable:true">
                    <span style="line-height: 18px;">天报名，（如未办理护照，请提前</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.attendNoPassport" value="<s:property value="cruiseShip.attendNoPassport"/>" required="required" data-options="min:1,max:100,editable:true">
                    <span style="line-height: 18px;">天以上；如已办理护照但未办理签证，请提前</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.attendNoVisa" value="<s:property value="cruiseShip.attendNoVisa"/>" required="required" data-options="min:1,max:100,editable:true">
                    <span style="line-height: 18px;">天以上）</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label" valign="top"><span style="color: red">*&nbsp;</span>邮轮图片:</td>
			   	<td>
                    <div id="imageBox">
                        <div id="imagePanel"></div>
                    </div>
                    <div id="imageContent" style="margin-top: 50px;">
                        <span style="color: rgba(128, 128, 128, 0.55);">为了展示效果，建议上传图片的规格为300×150.</span>
                        <input type="hidden" id="childFolder" name="childFolder" value="cruiseship/info/">
                    </div>
                    <div style="width:180px; float: right">
                        封面：
                        <div id="coverParent">
                            <div id="coverBox"></div>
                        </div>
                        <input type="hidden" id="coverPath" name="cruiseShip.coverImage">
                        <input type="hidden" id="coverImgId" name="coverImgId">
                    </div>
			   	</td>
			</tr>
			<tr>
				<td class="label">邮轮品牌:</td>
				<td>
					<input class="easyui-combotree" id="brand" name="cruiseShip.brand.id" value="<s:property value="cruiseShip.brand.id"/>" style="width:360px;">
					<%--<span class="tip">最多<span class="green-bold">100</span>个字符</span>--%>
				</td>
			</tr>
			<tr>
				<td class="label">邮轮航线:</td>
				<td>
					<input class="easyui-combotree" id="route" name="cruiseShip.route.id" value="<s:property value="cruiseShip.route.id"/>" style="width:360px;">
					<%--<span class="tip">最多<span class="green-bold">100</span>个字符</span>--%>
				</td>
			</tr>
			<tr>
				<td class="label">特色服务:</td>
				<td>
					<input class="easyui-textbox" id="services" name="cruiseShip.services" value="<s:property value="cruiseShip.services"/>" style="width:360px;" data-options="validType:'maxLength[100]'">
					<span class="tip">最多<span class="green-bold">100</span>个字符</span>
				</td>
			</tr>
            <tr>
                <td class="label"></td>
                <td>
                    <span style="line-height: 18px;">满意度</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.satisfaction" value="<s:property value="cruiseShip.satisfaction"/>" data-options="min:1,max:100,editable:true">
                    <span style="line-height: 18px;">%，评论数</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.commentNum" value="<s:property value="cruiseShip.commentNum"/>" data-options="min:1,editable:true">
                    <span style="line-height: 18px;">条，收藏数</span>
                    <input class="easyui-numberbox" style="width:50px;" name="cruiseShip.collectionNum" value="<s:property value="cruiseShip.collectionNum"/>" data-options="min:1,editable:true">
                    <span style="line-height: 18px;">条。</span>
                </td>
            </tr>
			<tr>
				<td class="label">产品备注:</td>
				<td>
					<input class="easyui-textbox" name="cruiseShip.remark" value="<s:property value="cruiseShip.remark"/>" data-options="multiline:true,validType:'maxLength[300]'" style="height:80px;width:480px;"/>
					<div class="tip">最多<span class="green-bold">300</span>个字符</div>
				</td>
			</tr>
            <tr>
                <td class="label">产品确认:</td>
                <td>
                    <label style="margin-right: 12px;">
                        <input type="radio" name="cruiseShip.needConfirm" value="true" <s:if test="cruiseShip.needConfirm==true">checked</s:if> >
                        需要确认
                    </label>
                    <label>
                        <input type="radio" name="cruiseShip.needConfirm" value="false" <s:if test="cruiseShip.needConfirm == null || cruiseShip.needConfirm==false">checked</s:if>/>
                        无需确认
                    </label>
                </td>
            </tr>
		</table>
		<div class="row_hd">其他信息</div>
            <table>
                <tr>
                    <td class="label">签证信息:</td>
                    <td>
                        <textarea id="visaInfo" name="cruiseShipExtend.visaInfo" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.visaInfo"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">推荐理由:</td>
                    <td>
                        <textarea id="recommend" name="cruiseShip.recommend" style="width:600px; height:120px;" value=""><s:property value="cruiseShip.recommend"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">行程特色:</td>
                    <td>
                        <textarea id="lightPoint" name="cruiseShipExtend.lightPoint" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.lightPoint"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">费用不含:</td>
                    <td>
                        <textarea id="quoteNoContainDesc" name="cruiseShipExtend.quoteNoContainDesc" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.quoteNoContainDesc"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">费用包含:</td>
                    <td>
                        <textarea id="quoteContainDesc" name="cruiseShipExtend.quoteContainDesc" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.quoteContainDesc"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">预订须知:</td>
                    <td>
                        <textarea id="orderKnow" name="cruiseShipExtend.orderKnow" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.orderKnow"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">如何预订:</td>
                    <td>
                        <textarea id="howToOrder" name="cruiseShipExtend.howToOrder" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.howToOrder"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">签约方式:</td>
                    <td>
                        <textarea id="signWay" name="cruiseShipExtend.signWay" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.signWay"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">付款方式:</td>
                    <td>
                        <textarea id="payWay" name="cruiseShipExtend.payWay" style="width:600px; height:120px;" value=""><s:property value="cruiseShipExtend.payWay"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
            </table>
	    </form>
        <div style="text-align:left;margin:20px;height:30px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep1.nextGuide(2)">保存，下一步</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep1.nextGuide(5)">编辑完成</a>
        </div>
    </div>
</div>
</body>
</html>
