<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="../../common/yhyheader.jsp" %>
    <title>评价管理</title>
    <link href="/css/yhy/yhySailboat/normalize.css" rel="stylesheet">
    <link href="/css/yhy/yhySailboat/sailling_comment_replay.css" rel="stylesheet">

</head>
<body  class="sailComment includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<div class="roomset container">
    <div class="search clearfix">
        <input type="hidden" id="hipt_priceTypeId" value="${priceTypeId}">
            <div class="form-group">
                <label for="dropdown">状态</label>
                <button class="btn btn-default" id="dropDown" type="button">
                    全部
                </button>
                <div class="selectBtn" id="selectBtn">
                    <ul>
                        <li class="active">全部</li>
                        <li>已回复</li>
                        <li>未回复</li>
                    </ul>
                </div>
                <input id="replyStatus" type="hidden" name="condition">
            </div>
            <div class="form-group">
                <input type="text" name="content" id="content" value="" placeholder="评价内容/订单号">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default" id="checkBtn">查询</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default" id="returnBtn">返回</button>
            </div>
    </div>
    <div class="table-list row messageList_header">
        <table class="table table-bordered" id="table">
        </table>
    </div>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_comment_reply.js"></script>
</html>