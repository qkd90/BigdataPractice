<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-13,0013
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" type="text/css" href="/css/order/planOrder.css">
  <title>DIY已选产品</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
  <%@include file="../../yhypc/public/order_header.jsp" %>
  <div class="body_order clearfix">
    <div class="progress">
      <span class="active">选择产品</span><span>在线支付</span><span>订单完成</span>
    </div>
    <div class="product_left">
      <div class="a_contain">
        <p class="pro_title g_blue" id="boatTicket">船票</p>
        <div id="ferry"></div>
      </div>
      <div class="a_contain">
        <p class="pro_title g_blue" id="hotel">酒店</p>
        <div id="hotelList"></div>
      </div>
      <div class="a_contain">
        <p class="pro_title g_blue" id="scenic">门票</p>
        <div id="scenicList"></div>
      </div>
    </div>
    <div class="product_right">
      <p class="fare_title">费用清单</p>
      <div class="totallist">
        <p class="one_head">船票</p>
        <div id="simpleFerry"></div>
        <p class="one_head one_head_orange">酒店</p>
        <div id="simpleHotelList"></div>
        <p class="one_head one_head_green">景点</p>
        <div id="simpleScenicList"></div>
        <div class="totalprice">
          订单总价 <span class="count"><label>¥</label><span id="totalPrice"></span></span>
        </div>
        <span class="submit"><a id="submitOrder">提交订单</a></span>
      </div>
    </div>
  </div>
  <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/order/planOrder.js"></script>
<script type="text/html" id="ferry_item">
  <div class="pro_contain clearfix g_blue">
    <div class="sel_box"></div>
    <div class="picture"><img src="/image/chuanpiao.png"></div>
    <div class="message">
      <p><span class="p_title">航线：</span>{{line.name}}</p>
      <p><span class="p_title">航班：</span>{{number}}</p>
      <p><span class="p_title">时间：</span>{{departTime}}</p>
      <div class="tour clearfix">
        <span class="p_title">游客：</span>
        <div class="tourlist clearfix">
          {{each touristList as tourist}}
          <span class="takeit" data-name="{{tourist.name}}" data-mobile="{{tourist.tel}}" data-id-type="{{tourist.idType}}" data-idnumber="{{tourist.idNumber}}">{{tourist.name}}</span>
          <div class="tickettype">
            <label data-id="{{tourist.ticket.id}}" data-price="{{tourist.ticket.price}}" data-number="{{tourist.ticket.number}}" data-ticket-name="{{tourist.ticket.name}}">{{tourist.ticket.name}}</label>
            <div class="typelist" style="display: none;">
              {{each ticketLst.Ticket as ticket}}<p data-id="{{ticket.id}}" data-price="{{ticket.price}}" data-number="{{ticket.number}}" data-ticket-name="{{ticket.name}}">{{ticket.name}}</p>{{/each}}
            </div>
          </div>
          {{/each}}
        </div>
      </div>
    </div>
    <div class="total_price"><span>¥</span><span class="ferryPrice">{{totalPrice}}</span></div>
  </div>
</script>
<script type="text/html" id="hotel_list_item">
  <div class="pro_contain clearfix g_blue" data-id="hotel_{{id}}">
    <div class="sel_box"></div>
    <div class="picture"><img src="{{cover}}"></div>
    <div class="message">
      <p><span class="p_title">酒店：</span>{{name}}</p>
      <p><span class="p_title">时间：</span>{{startDate}}至{{endDate}}（{{hotelPrices[0].day}}晚）</p>
      <div class="tour clearfix">
        <span class="p_title">房型：</span>
        <div class="p_proadd">
          {{each hotelPrices as price}}
          <span class="p_produ">{{price.priceName}} <span class="p_price">¥{{price.price}}</span></span>
          <span class="numbox">
            <span class="sub {{if price.selectedNum == 1}}readOlny{{/if}}">－</span>
			<span class="num" data-num="{{price.selectedNum}}" data-price="{{price.price}}" data-max-num="{{price.selectedNum}}" data-simple-id="hotelPrice_{{price.priceId}}"
                    data-hotel-id="{{id}}" data-price-id="{{price.priceId}}" data-start-time="{{price.startDate}}" data-end-time="{{price.endDate}}" data-price-name="{{price.priceName}}">{{price.selectedNum}}</span>
			<span class="add readOlny">＋</span>
          </span>
          {{/each}}
        </div>
      </div>
      <div class="tour clearfix">
        <span class="p_title">游客：</span>
        <div class="tourlist clearfix">
          {{each touristList as tourist}}<span class="takeit" data-name="{{tourist.name}}" data-phone="{{tourist.tel}}" data-people-type="{{tourist.peopleType}}" data-id-type="{{tourist.idType}}" data-id-num="{{tourist.idNumber}}">{{tourist.name}}</span>{{/each}}
        </div>
      </div>
    </div>
    <div class="total_price"><span>¥</span><span class="hotelPrice">{{totalPrice}}</span></div>
  </div>
</script>
<script type="text/html" id="scenic_list_item">
  <div class="pro_contain clearfix g_blue" data-id="scenic_{{id}}">
    <div class="sel_box"></div>
    <div class="picture"><img src="/image/hotelModle3.png"></div>
    <div class="message">
      <p><span class="p_title">景点：</span>{{scenicName}}</p>
      <p><span class="p_title">时间：</span>{{playDate}}</p>
      <div class="tour clearfix">
        <span class="p_title">票型：</span>
        <div class="p_proadd">
          {{each tickets as ticket}}
          <span class="p_produ">{{ticket.ticketName}} <span class="p_price">¥{{ticket.price}}</span></span>
          <span class="numbox">
			<span class="sub {{if ticket.selectedNum == 1}}readOlny{{/if}}">－</span>
			<span class="num" data-num="{{ticket.selectedNum}}" data-price="{{ticket.price}}" data-max-num="{{ticket.selectedNum}}" data-simple-id="scenicTicket_{{ticket.priceId}}"
                    data-ticket-id="{{ticket.ticketId}}" data-price-id="{{ticket.priceId}}" data-play-date="{{playDate}}" data-ticket-name="{{ticket.ticketName}}">{{ticket.selectedNum}}</span>
			<span class="add readOlny">＋</span>
          </span>
          {{/each}}
        </div>
      </div>
      <div class="tour clearfix">
        <span class="p_title">游客：</span>
        <div class="tourlist clearfix">
          {{each touristList as tourist}}<span class="takeit" data-name="{{tourist.name}}" data-phone="{{tourist.tel}}" data-people-type="{{tourist.peopleType}}" data-id-type="{{tourist.idType}}" data-id-num="{{tourist.idNumber}}">{{tourist.name}}</span>{{/each}}
        </div>
      </div>
    </div>
    <div class="total_price"><span>¥</span><span class="scenicPrice">{{totalPrice}}</span></div>
  </div>
</script>
<script type="text/html" id="simple_ferry_item">
  <div class="bottom">
    {{each ticketLst.Ticket as ticket}}
    <p class="thir clearfix {{if ticket.num == 0}}hidden{{/if}}" id="ferryTicket_{{ticket.id}}">
      <span class="s_left">{{ticket.name}}</span>
      <span class="s_center">¥{{ticket.price}}</span>
      <span class="s_right"><label class="selectedNum">{{ticket.num}}</label>/张</span>
    </p>
    {{/each}}
  </div>
</script>
<script type="text/html" id="simple_hotel_item">
  <div class="bottom" id="hotel_{{id}}">
    <p class="fourth">{{name}}</p>
    {{each hotelPrices as price}}
    <p class="thir clearfix" id="hotelPrice_{{price.priceId}}">
      <span class="s_left">{{price.priceName}}</span>
      <span class="s_center">¥{{price.price}}</span>
      <span class="s_right">{{price.day}}/晚 <label class="selectedNum">{{price.selectedNum}}</label>/间</span>
    </p>
    {{/each}}
  </div>
</script>
<script type="text/html" id="simple_scenic_item">
  <div class="bottom" id="scenic_{{id}}">
    <p class="fourth">{{scenicName}}</p>
    {{each tickets as ticket}}
    <p class="thir clearfix" id="scenicTicket_{{ticket.priceId}}">
      <span class="s_left">{{ticket.ticketName}}</span>
      <span class="s_center">¥{{ticket.price}}</span>
      <span class="s_right"><label class="selectedNum">{{ticket.selectedNum}}</label>/张</span>
    </p>
    {{/each}}
  </div>
</script>
</html>