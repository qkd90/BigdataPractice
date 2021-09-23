<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/9/7
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>酒店房态管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <style>
        .opt a.ena {color: #0000cc;text-decoration: underline; }.insurance_dialog {padding: 0 10px;}
        .fl {float: left;}.mt8 {margin-top: 8px;}.ml5 {margin-left: 5px;}
        .detail_item {border-bottom: dashed 1px #ff9900; padding: 5px;}
        .room_status li{ list-style: none}
        .checkin_info {height: 100px;max-height: 200px; border: dashed #706f6e 1px;  margin-top: 10px; overflow-y: auto}
        .c_dialog{padding: 0 10px; background: #FFFFCC;} input{color: #000}
        .c_dialog input{width: 150px;font-size: 13px;}.c_dialog li{margin-top: 8px;}
        .c_dialog label{font-size: 13px;}.c_dialog .disa{background: none;border: none;}
        .c_dialog .fl{float: left;}.c_dialog .ml8{margin-left: 8px;}
        .d_status{border: solid 1px transparent; border-radius: 5px; color: #fff; padding: 2px;}
    </style>
</head>
<body>
<div title="酒店房态管理" data-options="fit:true,border:false"
     style="width:100%;height:100%;" class="easyui-layout" id="content">
    <div id="hotel-tool" style="padding:3px">
        <label>请选择一个酒店：</label>
        <input id="hotelId_combogrid" name="hotelId" class="easyui-combogrid" style="width: 600px;">
        <label>请选择时间</label>
        <select id="hotel-day-range">
            <option value="7" selected>最近7天</option>
            <option value="30">最近30天</option>
            <option value="60">最近60天</option>
            <option value="90">最近90天</option>
        </select>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
           onclick="HotelRoomStatusMgr.doSearch()">查询</a>
        <div>
            <a href="#" class="easyui-linkbutton" style="width: 120px;"
               onclick="HotelRoomStatusMgr.openVerifyCheckin()">入住信息验证</a>
            <a href="#" class="easyui-linkbutton" style="width: 120px;"
               onclick="HotelRoomStatusMgr.getHotelRoomDataTable()">导出数据</a>
        </div>
    </div>
    <div data-options="region:'center',border:false">
        <table id="hotelRoomDg"></table>
    </div>
    <%-- 酒店下拉框搜索工具栏 --%>
    <div id="hotel-searcher" style="padding:3px">
        <span>
            <select id="hotel-search-type">
                <option value="hotel.name" selected>酒店名称</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <input id="hotel-search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
           onclick="HotelRoomStatusMgr.doHotelSearch()">查询</a>
    </div>
    <%--入住信息验证框--%>
    <div class="easyui-dialog verify_checkin_dialog c_dialog" id="verify_checkin_panel" closed="true" style="width:500px;top: 80px;">
        <form id="verify_checkin_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li style="width: 100%">
                    <label>酒店名称：</label>
                    <input class="disa" name="hotel.name" id="hotelName" disabled style="width: 80%;">
                    <input type="hidden" class="disa" name="hotel.id" style="width: 60px;">
                </li>
                <li class="fl">
                    <label>客户姓名：</label>
                    <input name="customerName">
                </li>
                <li class="fl ml8">
                    <label>证件号码：</label>
                    <input name="idNumber">
                </li>
                <li class="fl">
                    <label>联系电话：</label>
                    <input name="tel">
                </li>
                <li class="fl ml8">
                    <label>时间范围：</label>
                    <select id="hotel-check-range" name="days" style="width: 150px;">
                        <option value="3" selected>最近3天</option>
                        <option value="7">最近7天</option>
                        <option value="30">最近30天</option>
                        <option value="60">最近60天</option>
                        <option value="90">最近90天</option>
                    </select>
                </li>
                <li class="fl" style="width: 100%; text-align: center;">
                    <label style="font-size: 16px; font-weight: bold; color: #00008B;">验证结果</label>
                    <div class="checkin_info" id="verify_checkin_info"></div>
                </li>
                <%--<li>--%>
                    <%--<label>入住日期：</label>--%>
                    <%--<input class="disa" name="playDate" value="等待验证">--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<label>入住房型：</label>--%>
                    <%--<input class="disa" name="roomName" value="等待验证">--%>
                <%--</li>--%>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="HotelRoomStatusMgr.doVerifyCheckin('verify_checkin_form', 'verify_checkin_panel')">验证</a>
            </div>
        </form>
    </div>
    <%--办理入住确认框--%>
    <div class="easyui-dialog c_dialog" id="confirm_checkin_panel" closed="true" style="width:500px;top: 80px;">
        <form id="confirm_checkin_form" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="orderDetailId">
            <ul>
                <li style="text-align: center;">
                   <span style="color: #FF2F2F; font-weight: bold;
                        font-size: 24px;">请确认入住客户信息</span>
                </li>
                <li style="width: 100%">
                    <label>入住酒店：</label>
                    <input class="disa" name="hotel.name" disabled style="width: 80%;">
                    <input type="hidden" class="disa" disabled name="hotel.id" style="width: 60px;">
                </li>
                <li style="width: 100%">
                    <label>入住房型：</label>
                    <input class="disa" name="roomName" disabled
                            style="width: 80%; font-size: 16px; font-weight: bold">
                </li>
                <li class="fl">
                    <label>入住日期：</label>
                    <input class="disa" name="playDate" disabled>
                </li>
                <li class="fl ml8">
                    <label>预订数量：</label>
                    <input class="disa" name="num" disabled
                           style="color:#0000FF; width: 50px; font-size: 16px; font-weight: bold">
                </li>
                <li class="fl" style="width: 100%; text-align: center;">
                    <label style="font-size: 16px; font-weight: bold; color: #00008B;">入住客户信息</label>
                    <div class="checkin_info" id="confirm_checkin_info"></div>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="HotelRoomStatusMgr.doConfirmCheckin('confirm_checkin_form', 'confirm_checkin_panel')">确认办理入住</a>
            </div>
        </form>
    </div>
    <%--办理退房确认框--%>
    <div class="easyui-dialog c_dialog" id="confirm_checkout_panel" closed="true" style="width:500px;top: 80px;">
        <form id="confirm_checkout_form" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="orderDetailId">
            <ul>
                <li style="text-align: center;">
                   <span style="color: #FF2F2F; font-weight: bold;
                        font-size: 24px;">请确认客户退房信息</span>
                </li>
                <li style="width: 100%">
                    <label>入住酒店：</label>
                    <input class="disa" name="hotel.name" disabled style="width: 80%;">
                    <input type="hidden" class="disa" disabled name="hotel.id" style="width: 60px;">
                </li>
                <li style="width: 100%">
                    <label>入住房型：</label>
                    <input class="disa" name="roomName" disabled
                           style="width: 80%; font-size: 16px; font-weight: bold">
                </li>
                <li class="">
                    <label>入住日期：</label>
                    <input class="disa" name="playDate" disabled
                           style="font-size: 16px; color: #117700; font-weight: bold;">
                </li>
                <li class="fl">
                    <label>预定退房日期：</label>
                    <input class="disa" name="leaveDate" disabled
                           style="width: 120px; font-size: 16px; color: #e200fd; font-weight: bold;">
                </li>
                <li class="fl">
                    <label>实际退房日期：</label>
                    <input class="disa" name="nowDate" disabled
                           style="width: 120px; font-size: 16px; color: #e200fd; font-weight: bold;">
                </li>
                <li class="fl ml8">
                    <label>预订数量：</label>
                    <input class="disa" name="num" disabled
                           style="text-align: center; color:#0000FF; width: 30px; font-size: 20px; font-weight: bold">
                    <span>间</span>
                </li>
                <li class="fl ml8">
                    <label>预订天数：</label>
                    <input class="disa" name="booking_nights" disabled
                           style="text-align: center; color:#0000FF; width: 30px; font-size: 20px; font-weight: bold">
                    <span>晚</span>
                </li>
                <li class="fl ml8">
                    <label>入住天数：</label>
                    <input class="disa" name="checkin_nights" disabled
                           style="text-align: center; color:#0000FF; width: 30px; font-size: 20px; font-weight: bold">
                    <span>晚</span>
                </li>
                <li class="fl" style="width: 100%; text-align: center;">
                    <label style="font-size: 16px; font-weight: bold; color: #00008B;">退房客户信息</label>
                    <div class="checkin_info" id="confirm_checkout_info"></div>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="HotelRoomStatusMgr.doConfirmCheckout('confirm_checkout_form', 'confirm_checkout_panel')">确认办理退房</a>
            </div>
        </form>
    </div>
</div>
<script src="/js/outOrder/outOrder/hotelroom.js" type="text/javascript"></script>
</body>
</html>
