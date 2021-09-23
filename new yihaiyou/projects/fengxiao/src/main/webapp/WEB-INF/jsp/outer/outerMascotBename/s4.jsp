<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta content="" name="Keywords"/>
  <meta content="" name="Description"/>
  <title>我以千金，换你一名惊人</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color:#fff;
    }
    .logopic {
      width:100%;
    }
    .logopic img {
      width:100%;
    }
    .questionContain {
      padding: 25px 10px 10px 10px;
      clear: both;
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
      margin:5px 0;
      text-indent: 28px;
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
    <img src="/images/outer/mascot/s4.png">
  </div>
  <div class="questionContain">
    <h3>奖品设置</h3>
    <p class="exp">巅峰大奖（10名）：海上旅游产品免费体验</p>
    <p class="exp">神秘大奖（1名）：10个入围好名字将挑选一个绝世好名，可领取我们的神秘大奖哦</p>
    <button class="btn btn-lg btn-link next" type="button">继续 ></button>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('.next').click(function() {
            location.href = '/outer/outerMascotBename/s5.jhtml';
        });
    });
</script>
</html>
