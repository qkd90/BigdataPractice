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
  <title>恭喜，尊敬的贵宾！</title>
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
      padding: 5px 10px 10px 10px;
      clear: both;
      font-family: "方正卡通简体";
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
      font-family: "方正卡通简体";
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
    <h3>恭喜，尊敬的贵宾！</h3>
    <p class="exp">您已回答完所有问题，请耐心等待获奖公布，获奖者可能就是您喔！</p>
    <button id="closeWindow" class="btn btn-lg btn-link" type="button">关闭</button>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<%--<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>--%>
<script type="text/javascript">
  $(function() {
      <%--wx.config({--%>
          <%--debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。--%>
          <%--appId: '${resultMap.appId}', // 必填，公众号的唯一标识--%>
          <%--timestamp: ${resultMap.timeStamp}, // 必填，生成签名的时间戳--%>
          <%--nonceStr: '${resultMap.nonceStr}', // 必填，生成签名的随机串--%>
          <%--signature: '${resultMap.signature}',// 必填，签名，见附录1--%>
          <%--jsApiList: ['closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2--%>
      <%--});--%>
      <%--// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。--%>
      <%--wx.ready(function(){--%>
          <%--$('#closeWindow').show();--%>
      <%--});--%>
      <%--// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。--%>
      <%--wx.error(function(res){--%>
          <%--alert(res.toString);--%>
      <%--});--%>
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
