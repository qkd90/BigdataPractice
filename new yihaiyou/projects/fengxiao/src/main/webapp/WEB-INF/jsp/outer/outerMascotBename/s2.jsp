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
  <title>一海游寓意</title>
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
    <img src="/images/outer/mascot/s2.png">
  </div>
  <div class="questionContain">
    <p class="exp">因为我们需要为它找一个清新脱俗、雅俗共赏、如雷贯耳、响彻天际的名字，毕竟出来混的，最起码要有一个响亮的名字~对，没错，我们现在就是要以千金，换亲萌一“名”惊人 。</p>
      <p class="exp">你要了如指掌，才能一“名”惊人</p>
    </p>
    <button class="btn btn-lg btn-link next" type="button">嘿嘿~ 继续往下戳 ></button>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('.next').click(function() {
            location.href = '/outer/outerMascotBename/s3.jhtml';
        });
    });
</script>
</html>
