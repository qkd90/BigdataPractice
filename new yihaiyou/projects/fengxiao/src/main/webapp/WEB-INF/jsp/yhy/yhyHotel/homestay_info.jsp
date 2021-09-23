<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
    <title>民宿信息-一海游商户平台</title>
</head>
<body class="homestayIndex">
<div class="container">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
    <!-- 民宿信息/基础信息 -->
    <div class="roomset roomBaseMess" style="display:block">
        <form role="form" id="hotelInfoForm" action="" method="post" enctype="multipart/form-data">
            <input type="hidden" id="hotelId" name="hotel.id" value="${editHotelId}">
            <input type="hidden" id="originId" name="hotel.originId">
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group">
                    <label for="hotelName" class="leftTitle"><span class="starColor">*</span>名称：</label>
                    <input type="text" id="hotelName" name="hotel.name" class="form-control yhy-form-ctrl homestayName" placeholder="民宿名称">
                </div>
            </div>
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group">
                    <label for="hotelStar" class="leftTitle"><span class="starColor_white">*</span>星级：</label>
                    <select id="hotelStar" name="hotel.star" data-btn-class="form-control yhy-form-ctrl homestayLevel btcombo btn-default">
                        <option value="0">客栈/经济</option>
                        <option value="1">一星级</option>
                        <option value="2">二星级</option>
                        <option value="3">三星级</option>
                        <option value="4">四星级</option>
                        <option value="5">五星级</option>
                    </select>
                </div>
            </div>
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group">
                    <label for="hotelArea" class="leftTitle"><span class="starColor">*</span>区域：</label>
                    <%--<div class="homestayAddress">请选择省市区</div>--%>
                    <input type="text" readonly id="hotelArea" class="form-control yhy-form-ctrl homestayAddress" placeholder="请选择省市区">
                    <input type="hidden" name="hotel.cityId" id="cityId" value="350200">
                </div>
                <div class="addressSel" id="addressSelBox">
                    <div class="title">
                        <ul class="titleUl clearfix" id="addressTitleTab">
                            <li class="titleLi" id="provinceTitleTab" data-show-box="provinceSel">省份</li>
                            <li class="titleLi" id="cityTitleTab" data-show-box="citySel">城市</li>
                            <li class="titleLi active" id="regionTitleTab" data-show-box="regionSel">区/县</li>
                        </ul>
                    </div>
                    <div class="selbox province clearfix" id="provinceSel">
                        <ul class="clearfix provinceUl">
                            <li class="selected">福建</li>
                        </ul>
                    </div>
                    <div class="selbox city" id="citySel">
                        <ul class="clearfix cityUl">
                            <li class="selected">厦门</li>
                        </ul>
                    </div>
                    <div class="selbox area disb" id="regionSel">
                        <ul class="clearfix regionUl">
                            <li data-city-id="350203">思明</li>
                            <li data-city-id="350205">海沧</li>
                            <li data-city-id="350206">湖里</li>
                            <li data-city-id="350211">集美</li>
                            <li data-city-id="350212">同安</li>
                            <li data-city-id="350213">翔安</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group">
                    <label for="hotelAddress" class="leftTitle" style="margin-left:32px!important;"><span class="starColor">*</span>详细地址：</label>
                    <textarea id="hotelAddress" class="form-control yhy-form-ctrl addressDe"
                              placeholder="请输入详细地址" name="hotelExtend.address"></textarea>
                    <input type="hidden" id="hotelLng" name="hotelExtend.longitude">
                    <input type="hidden" id="hotelLat" name="hotelExtend.latitude">
                </div>
            </div>
            <%--<div class="outDiv clearfix">--%>
                <%--<div class="form-group yhy-form-group">--%>
                    <%--<label for="hotelPhone" class="leftTitle"><span class="starColor">*</span>电话：</label>--%>
                    <%--<input id="hotelPhone" type="text" class="form-control yhy-form-ctrl homestayName"--%>
                           <%--placeholder="请输入电话" name="hotelExtend.tel">--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group" style="position:relative">
                    <label for="hotelIntro" id="hotelIntroLab" class="leftTitle"><span class="starColor">*</span>简介：</label>
                    <textarea id="hotelIntro" class="form-control yhy-form-ctrl hotel-intro"
                              placeholder="请输入简介" name="hotel.shortDesc"></textarea>
                    <span class="writLimit">0/500</span>
                </div>
                <div class="remind_service">我的民宿简介</div>
            </div>
            <div class="outDiv clearfix" id="hotel_photo_area">
                <span class="leftTitle" style="margin-left:58px"><span class="starColor">*</span>相册：</span>
                <div class="row photo" style="padding: 0;border: none;">
                    <div class="col-md-12" style="padding-left: 0;padding-right: 0;">
                        <input id="hotelImgs" name="resource" type="file" multiple class="file-loading">
                    </div>
                    <div class="advice">为了展示效果，建议上传图片的规格为380×240。</div>
                </div>
            </div>
            <div class="outDiv clearfix">
                <div class="form-group yhy-form-group">
                    <label class="leftTitle" style="margin-left:32px!important"><span class="starColor_white">*</span>设施服务：</label>
                    <div class="service" id="service_group">
                        <label class="checkbox-inline service-item">
                            <input class="form-control yhy-form-ctrl" type="checkbox" value="" name="hotel.serviceAmenities">
                        </label>
                    </div>
                </div>
            </div>
            <div class="outDiv clearfix" style="margin-top: 25px">
                <div class="form-group yhy-form-group">
                    <label id="hotelPolicyLab" for="hotelPolicy" class="leftTitle"><span class="starColor_white">*</span>政策：</label>
                    <textarea id="hotelPolicy" class="form-control yhy-form-ctrl hotel-policy" placeholder="请填写政策" name="hotel.policy"></textarea>
                    <span class="writLimit">0/500</span>
                </div>
                <div class="remind_policy" style="left:770px">
                    <p>1.优惠政策</p>
                    <p>2.会员政策</p>
                    <p>3.其他政策</p>
                </div>
            </div>
            <div class="outDiv clearfix">
                <div class="mineBtn">
                    <a class="btn btn-default btn1" id="" href="/yhy/yhyMain/toHomestay.jhtml">取消</a>
                    <button type="submit" class="btn btn-default btn2" id="saveHotelInfoBtn" data-loading-text="保存中...">提交</button>
                </div>
            </div>
        </form>
    </div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</div>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/lib/bootstrap-fileinput4.3.6/js/fileinput.js" ></script>
<script type="text/javascript" src="/lib/bootstrap-fileinput4.3.6/js/locales/zh.js"></script>
<script type="text/javascript" src="/lib/bootstrap-fileinput4.3.6/js/fileinput_util.js"></script>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay.js"></script>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay_info.js"></script>
</html>
