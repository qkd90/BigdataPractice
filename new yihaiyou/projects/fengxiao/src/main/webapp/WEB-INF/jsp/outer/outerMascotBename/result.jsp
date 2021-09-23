<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>吉祥物候选名称清单</title>
  <style>
    * {
      margin: 0;
      padding: 0;
    }
    .clear {
      clear: both;
    }
    .clearfix:after {
      content:"\200B"; display:block; height:0; clear:both;
    }
    .clearfix {
      *zoom:1;
    }
    .background {
      padding-top:1px;
      background:url(/images/outer/winnerbg.jpg) no-repeat center center;
      background-size:100% 100%;
      font-family: "微软雅黑"
    }
    h3 {
      text-align:center;
      font-size:34px;
      margin-bottom:40px;

    }
    .page {
      width: 980px;
      position: absolute;

    }
    .page ul li {
      width:20%;
      height: 45px;float: left;
      list-style-type: none;
      text-align: center;
      line-height:45px;
      font-weight: 600;
      font-size:20px;
      color: #333
    }
  </style>
</head>
<body>
<div class="background">
  <div class="page">
    <h3>吉祥物候选名称清单如下：</h3>
    <ul class="clearfix">
        <c:forEach items="${mascots}" var="m" varStatus="status">
        <li title="${m.participator},${m.phone}">${m.mascotName}</li>
        </c:forEach>
    </ul>
  </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script>
  var width = $(window).width();
  var height = $(window).height();
  var xwidth = $('.page').width();
  var xheight = $('.page').height();
  var left = (width - xwidth)/2;
//  var topr = (height - xheight)/2;
  var topr = 60;
  $('.background').css({'width':width,'height':height-2});
  $('.page').css({'left':left,'top':topr})
</script>
</html>
