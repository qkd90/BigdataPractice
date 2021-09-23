<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-04,0004
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" type="text/css" href="/css/order/hotelOrder.css">
  <title>酒店民宿支付</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
  <%@include file="../../yhypc/public/order_header.jsp" %>
  <div class="body_order">
    <div class="progress progress2">
      <span>填写订单</span><span class="active">在线支付</span><span>订单完成</span>
    </div>
    <div class="orderPay clearfix ">
      <div class="payTop">
        <div class="picture"><img src="" id="hotelCover"></div>
        <div class="order_mess">
          <h3 id="orderName"></h3>
          <p>入住日期：
            <span class="date" id="startDateStr"></span>
            <span class="week" id="startWeekday"></span>
            <span class="roomNum">房间数量：<span id="roomNum"></span> </span>
							<span class="total">订单总额：
								<span class="rmb">¥</span><span class="num" id="totalPrice"></span>
							</span>
          </p>
          <p>离店日期：
            <span class="date" id="endDateStr"></span>
            <span class="week" id="endWeekday"></span>
            <span class="roomNum">联系方式：<span id="recMobile"></span></span>
          </p>
        </div>
      </div>
    </div>
    <div class="write">
      <div class="center_box">
        <div class="wr_left">
          <p><span>信用卡卡号</span><input class="bankCode" type="text" id="cardNum"></p>
          <p><span>持卡人姓名</span><input class="bankCode" type="text" placeholder="持卡人姓名" id="holderName"></p>
          <p>
            <span>信用卡有效期</span><select class="bank" id="expirationMonth"></select>
            <select class="bank" id="expirationYear"></select>
          </p>
          <p><span>信用卡预留证件</span><input class="bankCode" type="text" placeholder="身份证号码" id="idNo"></p>
          <p><span>信用卡验证码</span><input class="bankCode" type="text" placeholder="卡背后末三位数" id="cvv"></p>
          <p class="atention">温馨提示：请确保您提供的信息与发卡行记录的信息一致。</p>
          <p class="codeP clearfix"><span>安全验证</span><input class="bankCode" type="text" id="checkCode"><img src="/image/checkNum.jsp" id="imgyz_ADMIN" class="save_code"/>
            <a class="save_change" href="javascript:;" onclick="imgClick('imgyz_ADMIN');">换一张</a></p>
          <span class="pay"><a id="submitOrder">确认支付</a></span>
        </div>
        <div class="wr_right">
          <h3>信用卡安全保障</h3>
          <ul>
            <li>NASDAQ上市公司诚信保证</li>
            <li>全球著名在线旅游公司Exliedia长期经验分享</li>
            <li>众多银行及信用卡的守信，实名消费，安全消费</li>
            <li>完备的风险控制体系，遵从信用卡支付国际标准</li>
            <li>现金的专线连接及加密方法，从技术上确保安全</li>
            <li>完善的管理，专业的分工，标准化的流程，严格的监控体</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/order/hotelOrder_guarantee.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.qrcode.min.js"></script>
</html>