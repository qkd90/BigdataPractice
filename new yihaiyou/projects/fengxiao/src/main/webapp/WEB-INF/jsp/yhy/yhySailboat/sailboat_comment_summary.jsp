<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="../../common/yhyheader.jsp" %>
    <title>评价管理</title>
    <link href="/css/yhy/yhySailboat/normalize.css" rel="stylesheet">
    <link href="/css/yhy/yhySailboat/sailling_comment_evaluation.css" rel="stylesheet">
</head>
<body class="sailComment includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
<div class="roomset container">
    <div class="search clearfix">
        <div class="form-group">
            <input type="text" name="category" id="ipt_targetName" value="" placeholder="票型名称" autocomplete="off">
        </div>
        <div class="form-group">
            <button class="btn btn-default" id="checkBtn">查询</button>
        </div>
    </div>
    <div class="table-list row messageList_header">
        <table class="table table-bordered">
        </table>
    </div>
</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_comment_summary.js"></script>
</html>