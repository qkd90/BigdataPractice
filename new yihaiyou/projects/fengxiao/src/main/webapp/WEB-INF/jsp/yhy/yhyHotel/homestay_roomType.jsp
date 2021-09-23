<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
    <title>民宿房型-一海游商户平台</title>
</head>
<body class="homestayIndex includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<div class="roomBaseMess roomType">
    <form role="form" id="hotelRoomTypeForm" action="" method="post" enctype="multipart/form-data">
        <input type="hidden" id="hotelPriceId" name="hotelPrice.id" value="${editPriceId}">
        <input type="hidden" id="originId" name="hotelPrice.originId">
        <input type="hidden" id="hotelId" name="hotel.id">
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label for="roomName" class="leftTitle"><span class="starColor">*</span>名称：</label>
                <input type="text" id="roomName" name="hotelPrice.roomName" class="form-control yhy-form-ctrl homestayName" placeholder="房型名称">
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group" style="position: relative">
                <label id="roomDescriptionLab" class="leftTitle"><span class="starColor">*</span>描述：</label>
                <textarea id="roomDescription" name="hotelPrice.roomDescription" class="form-control yhy-form-ctrl hotel-room-des" placeholder="房型描述"></textarea>
                <span class="writLimit">0/30</span>
            </div>
            <div class="remind_describ">房型描述</div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label class="leftTitle"><span class="starColor">*</span>房号：</label>
                <div class="room-number-group">
                    <div class="room-number-item">
                        <input type="text" name="roomNum" class="form-control yhy-form-ctrl room-number">
                        <span class="delRoom">－</span>
                    </div>
                </div>
                <div class="addRommBT" id="addRoomDiv">
                    <span class="jia">＋</span>
                    <span class="fangjian">房间</span>
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
        <div class="outDiv clearfix" id="roomtype_photo_area">
            <label class="leftTitle" style="margin-left:49px"><span class="starColor">*</span>相册：</label>
            <div class="row photo" style="padding: 0;border: none;">
                <div class="col-md-12" style="padding-left: 0;padding-right: 0;">
                    <input id="hotelRoomImgs" name="resource" type="file" multiple class="file-loading">
                </div>
                <div class="advice">为了展示效果，建议上传图片的规格为320×240。</div>
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group">
                <label class="leftTitle" style="margin-left:35px!important">是否含早：</label>
                <input type="hidden" id="hasHreakfast" name="hotelPrice.breakfast" value="true">
                <div class="cludBreak" id="breakfastSel">
                    <span data-has-breakfast="true" class="selected">含早</span>
                    <span data-has-breakfast="false">不含早</span>
                </div>
            </div>
        </div>
        <div class="outDiv clearfix">
            <div class="form-group yhy-form-group" style="position: relative">
                <label id="changeRuleLab" class="leftTitle" style="margin-left:35px!important">预定须知：</label>
                <textarea id="changeRule" class="form-control yhy-form-ctrl hotel-change-rule" name="hotelPrice.changeRule"></textarea>
                <span class="writLimit">0/500</span>
            </div>
            <div class="remind_beKnow">预订须知</div>
        </div>
        <div class="outDiv clearfix">
            <div class="mineBtn">
                <a class="btn btn-default btn1" href="/yhy/yhyMain/toHomeStayPriceList.jhtml">取消</a>
                <button class="btn btn-default btn2" id="savePriceInfoBtn" data-loading-text="保存中...">提交</button>
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
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay_roomtype.js"></script>
</html>