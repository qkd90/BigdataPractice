<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>第二步</title>
	<%@ include file="../../common/common141.jsp"%>
	<link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseShip/addWizard.css">
    <script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/editStep2.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
		<input id="productId" name="productId" type="hidden" value="<s:property value="productId"/>"/>
        <div class="row_hd">甲板信息</div>
        <!-- 甲板表格工具条 始 -->
        <div id="deckGridtb">
            <div style="padding:2px 5px;">
                <a href="javascript:void(0)" onclick="editStep2.searchDeck()" class="easyui-linkbutton" >刷新</a>
                <a href="javascript:void(0)" onclick="editStep2.openEditDeckDg()" class="easyui-linkbutton" >新增</a>
                <a href="javascript:void(0)" onclick="editStep2.doDelDeck()" class="easyui-linkbutton" >删除</a>
            </div>
        </div>
        <!-- 甲板表格工具条 终 -->
        <!-- 甲板数据表格 始 -->
        <div style="margin-left:10px;">
            <table id="deckGrid" style="width:800px; height:400px;"></table>
        </div>
        <!-- 甲板数据表格 终-->

		<div class="row_hd">房型信息</div>
		<!-- 房型表格工具条 始 -->
		<div id="roomGridtb">
			<div style="padding:2px 5px;">
				<a href="javascript:void(0)" onclick="editStep2.searchRoom()" class="easyui-linkbutton" >刷新</a>
				<a href="javascript:void(0)" onclick="editStep2.openEditRoomDg()" class="easyui-linkbutton" >新增</a>
				<a href="javascript:void(0)" onclick="editStep2.doDelRoom()" class="easyui-linkbutton" >删除</a>
			</div>
		</div>
		<!-- 房型表格工具条 终 -->
		<!-- 房型数据表格 始 -->
        <div style="margin-left:10px;">
		    <table id="roomGrid" style="width:800px; height:400px;"></table>
        </div>
		<!-- 房型数据表格 终-->
		<div style="text-align:left;margin:20px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep2.nextGuide(3)">保存，下一步</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep2.nextGuide(5)">编辑完成</a>
		</div>
	</div>
</div>
<%--甲板信息编辑窗口--%>
<div id="editDeckDg" title="甲板信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:600px;height:400px;padding:6px">
    <form id="deckForm" method="post">
        <input type="hidden" id="deckId" name="cruiseShipDeck.id" value="">
        <input type="hidden" name="cruiseShipDeck.cruiseShip.id" value="">
        <table>
            <tr>
                <td align="right">甲板楼层：</td>
                <td>
                    <input class="easyui-numberbox" name="cruiseShipDeck.level" data-options="required:true" style="width:80px;">
                </td>
            </tr>
            <tr>
                <td align="right">甲板描述：</td>
                <td>
                    <textarea id="levelDesc" name="cruiseShipDeck.levelDesc" style="width:400px; height:120px;" value=""></textarea>
                </td>
            </tr>
            <tr>
                <td align="right">甲板设施：</td>
                <td>
                    <textarea id="deckFacility" name="cruiseShipDeck.deckFacility" style="width:400px; height:120px;" value=""></textarea>
                </td>
            </tr>
            <tr>
                <td align="right">甲板图片：</td>
                <td>
                    <div id="deck_imageBox" style="width: 400px;">
                        <div id="deck_imagePanel"></div>
                    </div>
                    <div id="deck_imageContent"></div>
                    <input type="hidden" id="childFolder" name="deckChildFolder" value="cruiseship/deck/">
                    <label style="color: red">(上传新的甲板图片可以替换旧的图片)</label>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <a id="submit_deck" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="editStep2.saveDeck()">保存</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 房型编辑窗口 -->
<div id="editRoomDg" title="房型信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:700px;height:420px;padding:6px">
    <form id="roomForm" method="post">
        <input type="hidden" id="roomId" name="id" value="" >
        <input type="hidden" name="cruiseShipId" value=""/>
        <table>
            <tr>
                <td align="right">名称：</td>
                <td>
                    <input class="easyui-textbox" name="name" data-options="required:true,validType:'maxLength[50]'" style="width:240px;">
                </td>
                <td align="left">类型：</td>
                <td colspan="5">
                    <input id="roomType" name="roomType" style="width:80px;">
                </td>
                <td align="left">必须住满：</td>
                <td>
                    <input id="forceFlag" name="forceFlag" style="width:80px;"/>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td align="left">楼层：</td>
                <td>
                    <input class="easyui-textbox" name="deck" data-options="required:true,min:1,max:50" style="width:100px;">
                </td>
                <td align="left">可住人数：</td>
                <td>
                    <input class="easyui-numberbox" name="peopleNum" data-options="required:true,min:1,max:10" style="width:100px;">
                </td>
                <td align="left">面积：</td>
                <td>
                    <input class="easyui-textbox" name="area" data-options="required:true,min:1,max:9999" style="width:100px;">
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td align="right">设施：</td>
                <td>
                    <textarea id="facilities" name="facilities" style="width:400px; height:120px;" value=""></textarea>
                    <%--<input class="easyui-textbox" name="facilities" data-options="validType:'maxLength[100]'" style="width:600px;">--%>
                </td>
                <td align="right">房型封面:</td>
                <td>
                    <div style="width:180px; float: right">
                        <div id="coverParent">
                            <div id="coverBox"></div>
                        </div>
                        <input type="hidden" id="coverPath" name="coverImage">
                        <input type="hidden" id="coverImgId" name="coverImgId">
                    </div>
                </td>
            </tr>
        </table>
        <div style="width: 540px;">
            <label>房型图片:</label>
            <div id="room_imageBox">
                <div id="room_imagePanel"></div>
            </div>
            <div id="room_imageContent"></div>
            <input type="hidden" id="roomChildFolder" name="roomChildFolder" value="cruiseship/room/">
            <span style="color: rgba(128, 128, 128, 0.55);top: 47px; position: relative;">为了展示效果，建议上传图片的规格为300×230.</span>
        </div>
        <div style="margin-top: 50px; padding-top: 3px; text-align: center; border-top: dashed #706f6e 1px;">
            <a id="submit_room" href="javascript:void(0)" class="easyui-linkbutton"
               data-options="" onclick="editStep2.saveRoom()">保存</a>
        </div>
    </form>
</div>
</body>
</html>
