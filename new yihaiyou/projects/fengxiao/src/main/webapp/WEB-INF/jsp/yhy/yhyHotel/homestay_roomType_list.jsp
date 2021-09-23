<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_index.css">
    <link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
    <link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet'>
    <link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print'>
    <title>民宿房型-一海游商户平台</title>
</head>
<body class="homestayIndex includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<!-- 民宿信息/基础信息 -->
<div class="secnav">
    <div class="secnav_list">
        <ul class="clearfix">
            <li data-href="/yhy/yhyMain/toHomestay.jhtml">我的民宿</li>
            <li data-href="/yhy/yhyMain/toHomeStayPriceList.jhtml" class="HSsec_active">房型设置</li>
        </ul>
    </div>
</div>
<!-- /民宿信息/房型设置 -->
<div class="roomset roomPrice roomType" style="display: block;">
    <div class="selectBar">
        <div class="selectBar_1">
            <span class="roomemess">状态</span>
            <div class="dropdown status-dropdown">
                <select id="hotelPriceStatusSel" name="hotelPrice.status" data-btn-class="btcombo btn-default status-sel">
                    <option value="">全部</option>
                    <option value="UP">已上架</option>
                    <option value="DOWN">已下架</option>
                    <option value="UP_CHECKING">上架中</option>
                    <option value="REFUSE">被拒绝</option>
                </select>
            </div>
            <span class="roomemess">房型名称</span>
            <div class="input-group">
                <input type="text" class="form-control" id="searchRoomName">
            </div>
            <div class="btn-group">
                <button class="btn btn-default search-btn" id="priceSearchBtn">查询</button>
            </div>
        </div>
        <div class="btn-group addmore" id="addMoreDiv" style="display:block!important">
            <a class="btn btn-default" id="addMorePriceBtn">新增</a>
        </div>
    </div>
    <div class="messageList_header">
        <table id="yhyHotelPriceList" class="table table-striped yhy-common-table">
            <thead>
            <tr>
                <th class="roomName">房型名称</th>
                <th class="roomDescribe">描述</th>
                <th class="roomAmount">房间数量</th>
                <th class="roomNum">房号</th>
                <th class="roomLive">可住</th>
                <th class="roomState">状态</th>
                <th class="roomOperate">操作</th>
                <th class="roomSort">排序<span class="up"></span><span class="down"><input type="hidden" id="table-sort" value=""></span></th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="price_calendar_modal" class="modal" role="dialog" style="display: none;" data-backdrop="static">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title" id="price_calendar_title">民宿价格日历</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="priceCalendarForm" action="" method="post" enctype="multipart/form-data">
                    <div class="price-calendar-info">
                        <div class="outDiv clearfix">
                            <div class="form-group yhy-form-group">
                                <label for="startDate" class="leftTitle"><span class="starColor">*</span>指定时间段：</label>
                                <input type="text" id="startDate" class="form-control yhy-form-ctrl" placeholder="开始日期">
                                <label class="leftTitle" style="margin: 0 10px">至</label>
                                <input type="text" id="endDate" class="form-control yhy-form-ctrl" placeholder="结束日期">
                                <label for="startDate" class="leftTitle" style="margin-left: 5px"><span class="starColor">*</span>计价模式：</label>
                                <input type="text" id="valuationModels" readonly="readonly" class="form-control yhy-form-ctrl readonly" style="width: 200px" placeholder="-">
                            </div>
                        </div>
                        <div class="outDiv clearfix">
                            <div class="form-group yhy-form-group">
                                <label class="leftTitle"><span class="starColor">*</span>每周：</label>
                                <div class="week-area">
                                    <label class="checkbox-inline"><input id="allWeekCheck" class="form-control yhy-form-ctrl" type="checkbox" value="" name="weekday">整周</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="1" name="weekday">周一</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="2" name="weekday">周二</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="3" name="weekday">周三</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="4" name="weekday">周四</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="5" name="weekday">周五</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="6" name="weekday">周六</label>
                                    <label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="0" name="weekday">周日</label>
                                </div>
                            </div>
                        </div>
                        <div class="outDiv clearfix">
                            <div class="form-group yhy-form-group">
                                <input type="hidden" id="hotelPriceId">
                                <label for="startDate" class="leftTitle"><span class="starColor">*</span>价格设置：</label>
                                <input type="text" id="memberPrice" style="width: 85px; margin-right: 10px" class="form-control yhy-form-ctrl" placeholder="销售价">
                                <input type="text" id="costPrice" style="width: 85px" class="form-control yhy-form-ctrl" placeholder="结算价">
                                <label for="startDate" class="leftTitle" style="margin-left: 5px"><span class="starColor">*</span>设置库存：</label>
                                <input type="text" id="inventory" style="width: 85px" class="form-control yhy-form-ctrl" placeholder="库存数量">
                                <button type="button" class="btn btn-default add-price-btn" id="addPriceInfoBtn">添加</button>
                                <button type="button" class="btn btn-danger clear-price-btn" id="clearPriceInfoBtn">清除</button>
                            </div>
                        </div>
                    </div>
                </form>
                <div id="price_calendar"></div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                <button class="btn btn-primary" id="savePriceBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhyHotel/homestay_roomtype_list.js"></script>
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src='/fullcalendar-2.4.0/lang-all.js'></script>
</html>
