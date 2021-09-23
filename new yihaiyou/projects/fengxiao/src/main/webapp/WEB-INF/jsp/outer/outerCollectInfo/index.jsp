<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zuipin.util.DateUtils" %>
<%@page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 在此配置活动起始时间，方便部署修改
    String startTimeStr = "20161101 00:00:00";
    String endTimeStr = "20181101 00:00:00";
    Date now = new Date();
    Date startTime = DateUtils.getDate(startTimeStr, "yyyyMMdd HH:mm:ss");
    Date endTime = DateUtils.getDate(endTimeStr, "yyyyMMdd HH:mm:ss");
    String msg = null;
    if (now.compareTo(startTime) < 0) {
        msg = "感谢您的关注，活动还未开始，请耐心等候！";
    } else if (now.compareTo(endTime) > 0) {
        msg = "感谢您的关注，活动已经结束！";
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta content="" name="Keywords"/>
  <meta content="" name="Description"/>
  <title>欢迎，尊敬的贵宾！</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color:#fff;
    }
    @font-face {
      font-family: "方正卡通简体";
      src: url("/css/yhy/yhyCommon/cuton.ttf");
      src: url("/css/yhy/yhyCommon/cuton.ttf") format("embedded-opentype"), url("/css/yhy/yhyCommon/cuton.ttf") format("truetype"), url("/css/yhy/yhyCommon/cuton.ttf") format("svg");
      font-style: normal;
      font-weight: normal
    }
    .logopic {
      width:100%;
    }
    .logopic img {
      width:55%;
      float:right;
    }
    .questionContain {
      padding: 25px 10px 10px 10px;
      clear: both;
      font-family: "方正卡通简体";
    }
    .questionContain h3 {
      font-size:22px;
      font-weight:600;
      color: #000;
      margin-top:10px;
    }
    .questionContain p {
      font-size:14px;
      color: #000;
      font-weight:500;
    }
    .questionContain p.exp {
      margin:15px 0;
    }
    .questionContain p.goNext {
      color:#4b2b72;
      text-decoration: underline;;
      font-weight:600;
    }
  </style>
</head>
<body>
<div class="personal_question">
  <div class="logopic">
    <img src="/images/outer/quetionIndex.png">
  </div>
  <div class="questionContain">
    <h3>欢迎，尊敬的贵宾！</h3>
    <% if (msg == null) { %>
    <p class="exp">欢迎了解一海游旅游电商平台，现在邀您来一场小测试，回答五个问题，开启您的海上之旅。</p>
    <button class="btn btn-lg btn-link next" type="button">开始吧 ！></button>
    <% } else { %>
    <p class="exp"><%=msg%></p>
    <% } %>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('.next').click(function() {
            location.href = '/outer/outerCollectInfo/info.jhtml';
        });
    });
</script>
</html>
