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
  <title>静候大奖，走上人生巅峰</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color:#fff;
    }
    .logopic {
      width:100%;
        margin-top:30px;
    }
    .logopic img {
      width:100%;
    }
    .questionContain {
      padding: 5px 10px 10px 10px;
      clear: both;
    }
    .questionContain h3 {
      font-size:22px;
      font-weight:600;
      color: #000;
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
    <img src="/images/outer/mascot/s6.png">
  </div>
  <div class="questionContain">
    <button id="closeWindow" class="btn btn-lg btn-link" type="button">关闭</button>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<%--<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>--%>
<script type="text/javascript">
  $(function() {
      if (isWeiXin()) {
          $('#closeWindow').click(function() {
              WeixinJSBridge.call('closeWindow');
          });
      } else {
          $('#closeWindow').hide();
      }
  });

  function isWeiXin(){
      var ua = window.navigator.userAgent.toLowerCase();
      if(ua.match(/MicroMessenger/i) == 'micromessenger'){
          return true;
      }else{
          return false;
      }
  }
</script>
</html>
