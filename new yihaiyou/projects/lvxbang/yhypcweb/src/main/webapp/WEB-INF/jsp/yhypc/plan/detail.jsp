<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-12,0012
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" type="text/css" href="/css/plan/planDetail.css">
  <title>DIY行程详情</title>
</head>
<body class="DIY">
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp" %>
<div class="detailTop">
  <div class="outrangestate">
    <div class="outrangestatein">
    <div class="rangestate">
      您在这里：
      <a href="/yhypc/index/index.jhtml">首页</a> &gt;
      <a href="/yhypc/plan/demand.jhtml"> DIY行程</a> &gt;
      <a href="/yhypc/plan/scenicList.jhtml"> 厦门景点</a> &gt;
      行程详情
    </div>
    <div class="finalbox clearfix">
      <div class="suggestman">
        <span class="face"><img src="<c:if test="${!fn:startsWith(user.head, 'http')}">${QINIU_BUCKET_URL}</c:if>${user.head}"></span>
        <p class="yours"><span class="namebox" id="planName"></span><input type="text" class="nameinput"/><i>.</i></p>
        <p class="suggester">规划师：${user.nickName}</p>
      </div>
      <div class="fare">
        行程共 <span id="totalDay"></span>天， <span id="totalScenic"></span>个景点，大约花费 <span><label class="rmb">¥</label><label class="num" id="totalPrice"></label></span>
      </div>
      <div class="buy">
        <span class="buying" id="buy">立即购买</span>
        <span class="save" id="savePlanBtn">保存行程</span>
      </div>
    </div>
  </div>
  </div>
</div>
<div class="marchdetail clearfix">
  <span class="t_title">行程详情</span>
  <div class="march_left" id="planDayList">
  </div>
  <div class="march_right">
    <div class="map" id="map"></div>
    <p class="line_title">行程概览</p>
    <div class="dayline" id="simplePlanList">
    </div>
  </div>
</div>
<div class="shadowbox"></div>
<div class="tourbox">
  <div class="title_close">选择游客<span id="buyshut"></span></div>
  <div class="edited">
    <%--<c:forEach items="${touristList}" var="tourist" varStatus="status">--%>
    <%--<p data-id="${tourist.id}" data-tourist="${tourist}">游客${status.index + 1}：${tourist.name} <span class="adult">${tourist.peopleTypeStr}</span> ${tourist.hiddenTel}</p>--%>
    <%--</c:forEach>--%>
  </div>
  <span class="savebtn"><a id="nextOrder">确定</a></span>
  <div class="title_close">添加游客</div>
  <div class="addtour">
    <ul class="clearfix">
      <li>
        <label>姓名</label><input type="text" id="addTourName">
      </li>
      <li>
        <label>手机号</label><input type="text" id="addTourTel">
      </li>
      <li class="card">
        <label>证件类型</label><input type="text" value="身份证" data-type="IDCARD" readonly id="addTourIdType">
        <div class="cardlist">
          <p data-type="IDCARD">身份证</p>
          <p data-type="PASSPORT">护照</p>
        </div>
      </li>
      <li>
        <label>证件号</label><input type="text" id="addTourIdNo">
      </li>
    </ul>
  </div>
  <span class="savebtn" onclick="PlanDetail.saveTourist()">保存</span>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/plan/planDetail.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js"></script>
<script type="text/html" id="plan_day_item">
  <div class="aday" id="plan_day_{{day}}">
    <div class="final_aday">
      第{{day | numToWord}}天
      游玩 <span>{{playTime}}</span>个小时，
      <span>{{scenics.length}}</span>个景点。
      大约花费<span> <label class="rmb"> ¥</label><label class="num">{{price}}</label></span>
    </div>
    {{if needShip && ferry != null}}
    <div class="boat_hotel" id="ferry_{{day}}_{{ferry.dailyFlightGid}}">
      <div class="pic"><img src="/image/chuanpiao.png"></div>
      <div class="mess">
        <p><span class="lef line">航线：</span><span class="product">{{ferry.line.name}}</span></p>
        <p><span class="lef date">日期：</span><span class="time">{{ferry.departTime}}</span></p>
        <p><span class="lef price">价格：</span><span class="pri">往返船票 <label class="rmb"> ¥</label><label class="num">{{ferry.price}}</label></span></p>
      </div>
      <div class="change">
        <span class="changebox"><a onclick="PlanDetail.changeFerry({{day}})">更换船票</a></span>
      </div>
    </div>
    {{/if}}
    {{if needHotel}}
      {{if hotel == null}}
        <div class="boat_hotel noHotel">
          <div class="noHotelInfo">暂无您想要的酒店，您可以点击右下角更换酒店</div>
          <div class="change">
            <span class="changebox"><a onclick="PlanDetail.changeHotel({{day}})">更换酒店</a></span>
          </div>
        </div>
      {{else}}
        <div class="boat_hotel" id="hotel_{{day}}_{{hotel.id}}">
          <div class="pic"><img src="{{hotel.cover}}"></div>
          <div class="mess">
            <p><span class="lef hotel">酒店：</span><span class="product">{{hotel.name}}</span></p>
            <p><span class="lef line">地址：</span><span class="time">{{hotel.address}}</span></p>
            <p><span class="lef price">价格：</span><span class="pri"><label class="rmb"> ¥</label><label class="num">{{hotel.price}}</label></span><span class="qi">起</span></p>
            <div class="clearfix">
              <span class="lef intro">简介：</span>
              <span class="introbox">{{#hotel.description}}</span>
            </div>
          </div>
          <div class="change">
            <span class="changebox"><a onclick="PlanDetail.changeHotel({{day}})">更换酒店</a></span>
          </div>
        </div>
      {{/if}}
    {{/if}}
    {{each scenics as scenic i}}
    <div class="boat_hotel scenic" id="scenic_{{day}}_{{scenic.id}}">
      <div class="scen_name"><span>{{if i < 10}}0{{/if}}{{i + 1}}</span>{{scenic.name}}</div>
      <div class="pic"><img src="{{scenic.cover}}"></div>
      <div class="mess">
        <p><span class="lef clock">参考用时：</span><span class="ad_time">建议<span>{{scenic.adviceMinute / 60}}</span>小时</span></p>
        <p><span class="lef line">地址：</span><span class="time">{{scenic.address}}</span></p>
        <p><span class="lef price">价格：</span><span class="pri"><label class="rmb"> ¥</label><label class="num">{{scenic.price}}</label></span></p>
        <div class="clearfix">
          <span class="lef intro">简介：</span>
          <span class="introbox">{{scenic.shortComment}}</span>
        </div>
      </div>
    </div>
    {{/each}}
  </div>
</script>
<script type="text/html" id="simple_plan_item">
  <ul>
    <li class="daynum"><span class="pointer" onclick="PlanDetail.jumpTo('plan_day_{{day}}')">第{{day | numToWord}}天</span><i></i></li>
    {{if needShip && ferry != null}}
    <li class="sec boat"><span class="pointer" onclick="PlanDetail.jumpTo('ferry_{{day}}_{{ferry.dailyFlightGid}}')">船票</span><i></i>
      <p><span class="pointer">{{ferry.line.name}}</span></p>
    </li>
    {{/if}}
    {{if needHotel && hotel != null}}
    <li class="sec hotel"><span class="pointer" onclick="PlanDetail.jumpTo('hotel_{{day}}_{{hotel.id}}')">酒店</span><i></i>
      <p><span class="pointer">{{hotel.name}}</span></p>
    </li>
    {{/if}}
    <li class="sec scen"><span class="pointer" onclick="PlanDetail.jumpTo('scenic_{{day}}_{{scenics[0].id}}')">景点</span><i></i>
      {{each scenics as scenic i}}
      <p><span class="pointer" onclick="PlanDetail.jumpTo('scenic_{{day}}_{{scenic.id}}')">{{scenic.name}}</span></p>
      {{/each}}
    </li>
  </ul>
</script>
<script type="text/html" id="tourist_list_item">
  <p data-id="{{id}}">游客{{index}}：{{name}} <span class="adult">{{peopleTypeStr}}</span> {{hiddenTel}}</p>
</script>
</html>