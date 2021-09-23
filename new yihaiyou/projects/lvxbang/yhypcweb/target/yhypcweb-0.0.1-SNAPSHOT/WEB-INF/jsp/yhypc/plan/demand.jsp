<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-09,0009
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp"%>
  <link rel="stylesheet" type="text/css" href="/css/plan/DIYmarch.css">
  <title>DIY首页</title>
</head>
<body class="DIY">
<div class="hotelIndex">
  <%@include file="../../yhypc/public/nav_header.jsp"%>
  <div class="rightBar">
    <ul>
      <li class="myOrder"><span>我的订单</span></li>
      <li class="mySelected"><span>我的收藏</span></li>
      <li class="myEditor"><span>我的编辑</span></li>
      <li class="service"><span>联系客服</span></li>
      <li class="toTop"><span>回到顶部</span></li>
    </ul>
  </div>
  <div class="total">
    <div class="axis"><span></span></div>
    <div class="demandBox">
      <div class="ademand">
        <h3>出游需求</h3>
        <div class="contain">
          <div class="dateline clearfix">
            <label>出发日期：</label>
            <div class="datebox" onclick="WdatePicker({el:'startDate', doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d', onpicked:PlanDemand.startDate()})">
              <input type="text" id="startDate" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d', onpicked:PlanDemand.startDate()})"> <span></span>
              <span class="datebtn" onclick="WdatePicker({el:'startDate', doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d', onpicked:PlanDemand.startDate()})"></span>
            </div>
          </div>
          <div class="dateline clearfix">
            <label>游玩天数：</label>
            <div class="numCount" id="playNum">
              <span class="sub">－</span><span class="num">1</span><span class="add">＋</span>
            </div>
            <span class="simsun">天</span>
          </div>
          <%--<div class="dateline clearfix">--%>
            <%--<label>出游人数：</label>--%>
            <%--<span class="simsun left">成人</span>--%>
            <%--<div class="numCount secnum" id="adultNum">--%>
              <%--<span class="sub">－</span><span class="num">1</span><span class="add">＋</span>--%>
            <%--</div>--%>
            <%--<span class="simsun left">儿童</span>--%>
            <%--<div class="numCount secnum" id="childNum">--%>
              <%--<span class="sub">－</span><span class="num">0</span><span class="add">＋</span>--%>
            <%--</div>--%>
          <%--</div>--%>
        </div>
      </div>
      <div class="ademand">
        <h3>住宿需求</h3>
        <div class="contain contain_sleep">
          <div class="needHotel">
            <ul class="clearfix">
              <label>是否住宿：</label>
              <li data-need-hotel="1">是</li>
              <li data-need-hotel="0" class="li_selected">否</li>
            </ul>
          </div>
          <div class="price">
            <ul class="clearfix">
              <label>价格：</label>
              <li class="firstli li_selected" data-price-min="0" data-price-max="9999999"><span>不限</span></li>
              <li data-price-min="0" data-price-max="150">150元以下</li>
              <li data-price-min="150" data-price-max="300元">150-300元</li>
              <li data-price-min="300" data-price-max="450元">301-450元</li>
              <li data-price-min="450" data-price-max="600元">451-600元</li>
              <li data-price-min="600" data-price-max="9999999">600元以上</li>
            </ul>
          </div>
          <div class="level">
            <ul class="clearfix">
              <label>星级：</label>
              <li class="firstli li_selected" data-star="0"><span>不限</span></li>
              <li data-star="2">经济/客栈</li>
              <li data-star="3">三星/舒适</li>
              <li data-star="4">四星/高档</li>
              <li data-star="5">五星/豪华</li>
              <li data-star="1">公寓</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="ademand">
        <h3>行程安排</h3>
        <div class="contain contain_progress">
          <div class="hour">
            <ul class="clearfix">
              <li data-hour="1">宽松安排</li>
              <li data-hour="2" class="li_selected">适中安排</li>
              <li data-hour="3">紧凑安排</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="nextstep">
        <a id="next">下一步，选择景点</a>
      </div>
    </div>

  </div>
  <%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
</html>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/plan/planDemand.js"></script>