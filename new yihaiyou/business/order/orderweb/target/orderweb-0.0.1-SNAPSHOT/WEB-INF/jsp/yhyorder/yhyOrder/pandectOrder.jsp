<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/3/3
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp"%>
    <title>订单总览</title>
  <style>
      p.clr_float{clear: both;}
      h3 {line-height: 30px;}
      h3.category {font-size: 18px;color: rgb(0,153,255);}
      .rowdiv{margin:10px 20px 10px 20px;}
      .hsxx_title, .hsxx_sub_title, .jdms_title, .xcgh_title{width:100%;border-bottom: 1px solid rgb(221,221,221);}
     .hsxx_title h3, .hsxx_sub_title h3, .jdms_title h3, .xcgh_title h3 {float: left;}
      .jdms_title span.fl_center, .xcgh_title span.fl_center{ float: left; margin-left:30%;margin-top: 6px;}
      .hsxx_title span, .hsxx_sub_title span, .jdms_title span.fl_right, .xcgh_title span.fl_right{float: right; margin-right:8px;margin-top: 6px;}
     .hsxx_content .hsxx_sub {float: left; width: 32%; margin-right:1.3%}
     .jdms, .xcgh{float: left;width:49%;}
      .jdms {margin-right: 1%;}
      .hsxx_sub_content, .jdms_content, .xcgh_content{height:260px;}
      .no_detail{text-align: center; padding-top:80px; color: #706f6e}
      .detail{margin:5px;border-bottom: 1px dashed rgb(221,221,221);}
      .detail_info1, .detail_info2 {width: 100%;}
      .detail_info2 {height: 30px;}
      .order_no, .order_name {float: left;}
      .order_date, .order_status {float: right;}
      .order_status{color: #d08a1d}
      .order_name{overflow: hidden; text-overflow: ellipsis; width: 75%; white-space: nowrap;}
  </style>
  <script type="text/javascript" src="/js/yhyorder/pandectOrder.js"></script>
</head>
<body style="overflow-y: scroll;">
<div>
  <div class="rowdiv">
    <div class="hsxx_title">
      <h3 class="category">海上休闲</h3>
      <span><a style="color:blue;" href="javascript:void(0)" onclick="PandectOrder.goSailboatOrderList()">全部订单&gt;&gt;</a></span>
        <p class="clr_float"></p>
    </div>
    <div class="hsxx_content">
        <div class="hsxx_sub">
            <div class="hsxx_sub_title">
                <h3>帆船订单</h3>
                <span>最新订单数:<strong id="sailboatCount">0</strong></span>
                <p class="clr_float"></p>
            </div>
            <div class="hsxx_sub_content" id="sailboatOrders">
                <!-- js异步加载数据 -->
            </div>
        </div>
        <div class="hsxx_sub">
            <div class="hsxx_sub_title">
                <h3>游艇订单</h3>
                <span>最新订单数:<strong id="yachtCount">0</strong></span>
                <p class="clr_float"></p>
            </div>
            <div class="hsxx_sub_content" id="yachtOrders">
                <!-- js异步加载数据 -->
            </div>
        </div>
        <div class="hsxx_sub">
            <div class="hsxx_sub_title">
                <h3>鹭岛游订单</h3>
                <span>最新订单数:<strong id="huanguyouCount">0</strong></span>
                <p class="clr_float"></p>
            </div>
            <div class="hsxx_sub_content" id="huanguyouOrders">
                <!-- js异步加载数据 -->
            </div>
        </div>
        <p class="clr_float"></p>
    </div>
  </div>
  <div class="rowdiv">
      <div class="jdms">
          <div class="jdms_title">
              <h3 class="category">酒店民宿</h3>
              <span class="fl_center">最新订单数:<strong id="hotelCount">0</strong></span>
              <span class="fl_right"><a style="color:blue;" href="javascript:void(0)" onclick="PandectOrder.goHotelOrderList()">全部订单&gt;&gt;</a></span>
              <p class="clr_float"></p>
          </div>
          <div class="jdms_content" id="hotelOrders">
              <!-- js异步加载数据 -->
          </div>
      </div>
      <div class="xcgh">
          <div class="xcgh_title">
              <h3 class="category">行程规划</h3>
              <span class="fl_center">最新订单数:<strong id="planCount">0</strong></span>
              <span class="fl_right"><a style="color:blue;" href="javascript:void(0)" onclick="PandectOrder.goPlanOrderList()">全部订单&gt;&gt;</a></span>
              <p class="clr_float"></p>
          </div>
          <div class="xcgh_content" id="planOrders">
              <!-- js异步加载数据 -->
          </div>
      </div>
      <p class="clr_float"></p>
  </div>
</div>
</body>
</html>
