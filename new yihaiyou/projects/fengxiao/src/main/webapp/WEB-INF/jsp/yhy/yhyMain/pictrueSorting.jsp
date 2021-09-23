<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyMain/pictureSorting.css">
    <title>产品图片-一海游商户平台</title>
</head>
<body class="homestayIndex includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<!-- 民宿信息/基础信息 -->
<div class="secnav">
    <div class="secnav_list">
        <ul class="clearfix">
            <s:if test="type==@com.data.data.hmly.service.common.entity.enums.ProductType@hotel">
                <li data-href="/yhy/yhyMain/toHomestay.jhtml" class="HSsec_active">我的民宿</li>
                <li data-href="/yhy/yhyMain/toHomeStayPriceList.jhtml">房型设置</li>
            </s:if>
            <s:if test="type==@com.data.data.hmly.service.common.entity.enums.ProductType@sailboat">
                <li data-href="/yhy/yhyMain/toSailing.jhtml" class="HSsec_active">我的产品</li>
                <li data-href="/yhy/yhyMain/toSailboatPriceList.jhtml">票型设置</li>
            </s:if>
        </ul>
    </div>
</div>
<div class="roomset pictureBox" style="display:block">
    <s:if test="type==@com.data.data.hmly.service.common.entity.enums.ProductType@hotel">
        <p class="productTitle">民宿图片（拖动图片可对图片进行排序）</p>
    </s:if>
    <s:if test="type==@com.data.data.hmly.service.common.entity.enums.ProductType@sailboat">
        <p class="productTitle">海上休闲图片（拖动图片可对图片进行排序）</p>
    </s:if>
    <div class="pictureContain">
        <form id="image-form" method="post">
            <ul class="clearfix" id="sortable">
                <s:if test="productimages.size() > 0">
                    <s:iterator value="productimages" var="productimage">
                        <li>
                            <input type="hidden" name="ids" value="<s:property value="#productimage.id"/>">
                            <img src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#productimage.path"/>">
                        </li>
                    </s:iterator>
                </s:if>
            </ul>
        </form>
    </div>
    <div class="optbox">
        <span class="concle">取消</span>
        <span class="save">保存</span>
    </div>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/lib/jquery-1.11.1/js/jquery.sortable.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhyMain/pictureSorting.js"></script>
</html>
