<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_index.css">
    <title>我的民宿-一海游商户平台</title>
</head>
<body class="homestayIndex includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<!-- 民宿信息/基础信息 -->
<div class="secnav">
    <div class="secnav_list">
        <ul class="clearfix">
            <li data-href="/yhy/yhyMain/toHomestay.jhtml" class="HSsec_active">我的民宿</li>
            <li data-href="/yhy/yhyMain/toHomeStayPriceList.jhtml">房型设置</li>
        </ul>
    </div>
</div>
<div class="roomset roomPrice roomMine" style="display:block">
    <div class="selectBar">
        <div class="selectBar_1">
            <span class="roomemess">状态</span>
            <div class="dropdown status-dropdown">
                <select id="hotelStatusSel" name="hotel.status" data-btn-class="btcombo btn-default status-sel">
                    <option value="">全部</option>
                    <option value="UP">已上架</option>
                    <option value="DOWN">已下架</option>
                    <option value="UP_CHECKING">上架中</option>
                    <option value="DOWN_CHECKING">下架中</option>
                    <option value="REFUSE">被拒绝</option>
                </select>
            </div>
            <span class="roomemess">产品名称</span>
            <div class="input-group">
                <input type="text" class="form-control" id="searchProductName">
            </div>
            <div class="btn-group">
                <button class="btn btn-default search-btn" id="hotelSearchBtn">查询</button>
            </div>
        </div>
        <div class="btn-group addmore">
            <a class="btn btn-default" href="/yhy/yhyHotelInfo/toHotelInfo.jhtml">新增</a>
        </div>
    </div>
    <div class="messageList_header">
        <table id="yhyHotelList" class="table table-striped yhy-common-table">
            <thead>
            <tr>
                <th class="roomName">民宿名称</th>
                <th class="roomDescribe">民宿地址</th>
                <th class="roomContact">联系人</th>
                <th class="roomPhone">联系电话</th>
                <th class="roomState">状态</th>
                <th class="roomOperate">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/lib/jquery-1.11.1/js/jquery.sortable.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhyHotel/homestay_list.js"></script>
</html>
