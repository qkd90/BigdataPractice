<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyMain/pictureSorting.css">
    <title>民宿图片-一海游运维平台</title>
</head>
<body class="homestayIndex includeTable">
<div class="roomset pictureBox" style="display:block">
    <%--<p class="productTitle">民宿图片</p>--%>
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
</body>
<%--<%@include file="../../common/yhyfooter.jsp" %>--%>
<script src="/lib/jquery-1.11.1/js/jquery.sortable.js"></script>
<%--<script src="/js/yhy/yhyHotel/homestay.js"></script>--%>
<script src="/js/sailboat/sailboatImages.js"></script>
<script>

</script>
</html>
