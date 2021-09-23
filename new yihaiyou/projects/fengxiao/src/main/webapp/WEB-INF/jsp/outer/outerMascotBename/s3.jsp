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
  <title>吉祥物介绍</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color:#fff;
    }
    .logopic {
      width:100%;
    }
    .logopic img {
      width:50%;
      float:left;
    }
    .questionContain {
      padding: 10px 10px 10px 10px;
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
  <div class="questionContain">
    <p class="exp">它是一只活泼可爱的小海豚，任何人见了它都萌萌哒；</p>
    <p class="exp">它爱自拍卖萌，能萌你一脸血；</p>
    <p class="exp">它是个文青，简直就是萌宠界中的一股清流啊；</p>
    <p class="exp">它还是个十足的大吃货，在减肥的路上挣扎着，从未停止。</p>
    <h3>它实际是这个样子滴：</h3>
      <div class="logopic">
          <img src="/images/outer/mascot/mascot01.jpg">
          <img src="/images/outer/mascot/mascot02.jpg">
          <img src="/images/outer/mascot/mascot03.jpg">
          <img src="/images/outer/mascot/mascot04.jpg">
      </div>
    <button class="btn btn-lg btn-link next" type="button">继续 ></button>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('.next').click(function() {
            location.href = '/outer/outerMascotBename/s4.jhtml';
        });
    });
</script>
</html>
