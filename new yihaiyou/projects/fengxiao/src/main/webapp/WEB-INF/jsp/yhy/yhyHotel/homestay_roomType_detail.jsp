<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
    <title>民宿房型详情信息-一海游商户平台</title>
</head>
<body class="homestayIndex">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<div class="roomBaseMess roomType">
    <div class="detail_header clearfix" style="z-index:2;">
        <span class="return" onclick="history.back()">返回</span>
    </div>
    <form role="form" id="hotelRoomTypeForm" action="" method="post" enctype="multipart/form-data">
        <input type="hidden" id="hotelPriceId" name="hotelPrice.id" value="${hotelPriceId}">
        <input type="hidden" id="hotelId" name="hotel.id">
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label for="roomName" class="leftTitle"><span class="starColor">*</span>名称：</label>
                <input type="text" readonly="readonly" id="roomName" name="hotelPrice.roomName" class="form-control yhy-form-ctrl homestayName" placeholder="房型名称">
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group" style="position: relative">
                <label class="leftTitle"><span class="starColor">*</span>描述：</label>
                <textarea id="roomDescription" readonly="readonly" name="hotelPrice.roomDescription" class="form-control yhy-form-ctrl hotel-room-des" placeholder="民宿描述"></textarea>
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label class="leftTitle"><span class="starColor">*</span>房号：</label>
                <div class="room-number-group">
                    <div class="room-number-item readonly">
                        <input type="text" name="roomNum" readonly="readonly" class="form-control yhy-form-ctrl room-number">
                    </div>
                </div>
            </div>
        </div>
        <div class="outDiv clearfix" style="line-height:30px">
            <div class="form-group yhy-form-group">
                <label class="leftTitle"><span class="starColor">*</span>可住：</label>
                <select id="capacitySel" name="hotelPrice.capacity" data-btn-class="form-control yhy-form-ctrl homestayLevel btcombo btn-default">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <label class="rightTitle">人</label>
            </div>
        </div>
        <div class="outDiv clearfix">
            <label class="leftTitle" style="margin-left:49px"><span class="starColor">*</span>相册：</label>
            <div class="row photo" style="padding: 0;border: none;">
                <div class="col-md-12" style="padding-left: 0;padding-right: 0;">
                    <input id="hotelRoomImgs" name="resource" type="file" readonly class="file-loading">
                </div>
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label class="leftTitle" style="margin-left:31px!important"><span class="starColor_white">*</span>是否含早：</label>
                <input type="hidden" id="hasHreakfast" name="hotelPrice.breakfast" value="true">
                <div class="cludBreak" id="breakfastSel">
                    <span data-has-breakfast="true">含早</span>
                    <span data-has-breakfast="false">不含早</span>
                </div>
            </div>
        </div>
        <div class="outDiv clearfix" style="margin-bottom: 20px">
            <div class="form-group yhy-form-group" style="position: relative">
                <label class="leftTitle" style="margin-left:31px!important"><span class="starColor_white">*</span>预定须知：</label>
                <textarea id="changeRule" name="hotelPrice.changeRule" readonly="readonly" class="form-control yhy-form-ctrl hotel-change-rule"></textarea>
            </div>
        </div>
    </form>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/locales/zh.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput_util.js" type="text/javascript"></script>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay.js"></script>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay_roomtype_detail.js"></script>
</html>