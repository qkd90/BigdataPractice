<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" href="/css/order/cruiseshipOrderRoom.css">
<head>
  <title>游轮选择仓房</title>
</head>
<body>
<div class="hotelIndex">
<%@include file="../../yhypc/public/order_header.jsp" %>
<%--<div class="nav">
  <ul class="clearfix">
    <li>
      <a href="#">
        选择仓房
        <span class="triangle1"></span>
      </a>
    </li>
    <li>
      <a href="#">
        <span class="triangle2"></span>
        完善订单信息
        <span class="triangle3"></span>
      </a>
    </li>
    <li>
      <a href="#">
        <span class="triangle4"></span>
        预定成功
      </a>
    </li>
  </ul>
</div>--%>
<div class="cabin-wrap">
  <div class="cabin-header">
    <h3>${cruiseShip.name}</h3>
    <p>编号：${cruiseShip.id}</p>
    <input type="hidden" value="${cruiseShip.dateId}" id="hid_dateId">
    <input type="hidden" value="${cruiseShip.id}" id="hid_productId">
    <div class="cabin-header-date clearfix">
      <div class="cabin-date-group clearfix">
        <i>已选航期：</i>
        <span><s:date name="cruiseShip.startDate" format="yyyy年MM月dd日"/></span>
      </div>
      <%--<div class="cabin-date-group clearfix">
        <a href="#">更改日期</a>
      </div>--%>
      <div class="cabin-date-group clearfix">
        <i>返回时间：</i>
        <span><s:date name="cruiseShip.endDate" format="yyyy年MM月dd日"/></span>
      </div>
    </div>
  </div>
  <div class="tel-contact-tips-wrap">
    <p class="tel-contact-tips">如需预订，请咨询客服。电话：<mark>0597-5829849</mark></p>
  </div>
  <div class="cabin-nav">
    <div class="bg-line"></div>
    <ul class="clearfix">
      <s:iterator value="cruiseShipRoomList" var="cruiseShipRoom" status="L">
        <input type="hidden" name="roomType" value="<s:property value="#cruiseShipRoom.roomTypeStr"/>">
        <s:if test="#L.index == 0">
          <li class="active" id="<s:property value="#cruiseShipRoom.roomTypeStr"/>Btn">
            <a><s:property value="#cruiseShipRoom.roomTypeName"/> </a>
            <i class="dot"></i>
            <span><sub>￥</sub><s:property value="#cruiseShipRoom.minPrice"/><sub>起</sub></span>
          </li>
        </s:if>
        <s:else>
          <li id="<s:property value="#cruiseShipRoom.roomTypeStr"/>Btn">
            <a><s:property value="#cruiseShipRoom.roomTypeName"/> </a>
            <i class="dot"></i>
            <span><sub>￥</sub><s:property value="#cruiseShipRoom.minPrice"/><sub>起</sub></span>
          </li>
        </s:else>

      </s:iterator>
    </ul>
  </div>
<s:iterator value="cruiseShipRoomList" var="cruiseShipRoom" status="L">
  <div class="cabin-inside" id="<s:property value="#cruiseShipRoom.roomTypeStr"/>">
    <h3><i></i><s:property value="#cruiseShipRoom.roomTypeName"/></h3>
    <div class="table-wrap">
      <ul class="table-header clearfix">
        <li>房型</li>
        <li>面积</li>
        <li>可住人数</li>
        <li>价格</li>
        <%--<li>成人</li>
        <li>儿童</li>
        <li>房间</li>--%>
      </ul>
      <div id="<s:property value="#cruiseShipRoom.roomTypeStr"/>_roomList">
        <div class="table-body">
          <ul class="clearfix">
            <li><a class="collapse-btn" href="javascript:;">标准内舱双人房间标准</a></li>
            <li>12.1-20.6m<sup>2</sup></li>
            <li>2</li>
            <li style="color: #f80;">￥4404</li>
            <%--<li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>
            <li>
              <div class="room-num-btn children clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
              <p class="children-yo">1岁-12岁</p>
            </li>
            <li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>--%>
          </ul>
          <div class="collapse clearfix">
            <i class="triangle"></i>
            <div class="room-img">
              <img src="/image/cruises-choose-cabin-room-img.png">
            </div>
            <div class="room-info">
              <h4>标准内舱双人房DD-DA内侧双人间</h4>
              <p>甲板楼层：随机</p>
              <p>舱房详情：张单人床（可合并双人大床）</p>
            </div>
          </div>
        </div>
        <div class="table-body">
          <ul class="clearfix">
            <li><a class="collapse-btn" href="javascript:;">标准内舱双人房间标准</a></li>
            <li>12.1-20.6m<sup>2</sup></li>
            <li>2</li>
            <li style="color: #f80;">￥4404</li>
            <%--<li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>
            <li>
              <div class="room-num-btn children clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
              <p class="children-yo">1岁-12岁</p>
            </li>
            <li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>--%>
          </ul>
          <div class="collapse clearfix">
            <i class="triangle"></i>
            <div class="room-img">
              <img src="/image/cruises-choose-cabin-room-img.png">
            </div>
            <div class="room-info">
              <h4>标准内舱双人房DD-DA内侧双人间</h4>
              <p>甲板楼层：随机</p>
              <p>舱房详情：张单人床（可合并双人大床）</p>
            </div>
          </div>
        </div>
        <div class="table-body">
          <ul class="clearfix">
            <li><a class="collapse-btn" href="javascript:;">标准内舱双人房间标准</a></li>
            <li>12.1-20.6m<sup>2</sup></li>
            <li>2</li>
            <li style="color: #f80;">￥4404</li>
            <%--<li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>
            <li>
              <div class="room-num-btn children clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
              <p class="children-yo">1岁-12岁</p>
            </li>
            <li>
              <div class="room-num-btn clearfix">
                <i class="subtract"></i>
                <span>0</span>
                <i class="plus"></i>
              </div>
            </li>--%>
          </ul>
          <div class="collapse clearfix">
            <i class="triangle"></i>
            <div class="room-img">
              <img src="/image/cruises-choose-cabin-room-img.png">
            </div>
            <div class="room-info">
              <h4>标准内舱双人房DD-DA内侧双人间</h4>
              <p>甲板楼层：随机</p>
              <p>舱房详情：张单人床（可合并双人大床）</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</s:iterator>
  <%--<div class="cabin-footer clearfix">
    <div class="cabin-footer-left clearfix">
      <div class="cabin-footer-group clearfix">
        <i>入住：</i>
        <ul id="total-people-num-ul">
          <li><em>0</em>人</li>
          &lt;%&ndash;<li>成人<em>2</em>人</li>
          <li>儿童<em>2</em>人</li>&ndash;%&gt;
        </ul>
      </div>
      <div class="cabin-footer-group clearfix">
        <i>房间：</i>
        <span id="total-roomnum-span">0间</span>
      </div>
    </div>
    <div class="cabin-footer-right clearfix">
      <div class="cabin-footer-group clearfix">
        <i>总价：</i>
        <span id="total-price-span"><sub>￥</sub>0</span>
      </div>
      <div class="cabin-footer-group">
        <a class="details" href="#">明细</a>
        <div class="details-tips-wrap">
          <ul class="details-content">
            &lt;%&ndash;<li class="clearfix">
              <span>露台客房双人间（1间，2成人）</span>
              <em>￥7098</em>
            </li>
            <li class="clearfix">
              <span>露台客房双人间（1间，2成人）</span>
              <em>￥7098</em>
            </li>
            <li class="clearfix">
              <span>露台客房双人间（1间，2成人）</span>
              <em>￥7098</em>
            </li>&ndash;%&gt;
          </ul>
          <i class="triangle"></i>
        </div>
      </div>
      <div class="cabin-footer-group">
        <form id="sel-room-form" method="post" action="/yhypc/order/orderCruiseshipWrite.jhtml">
          <input type="hidden" name="cruiseshiId" id="hid-form-product-id">
          <input type="hidden" name="dateId" id="hid-form-date-id">
          <input type="hidden" name="totalPrice" id="hid-form-totalprice">
          <a class="next" href="javascript:void(0);" onclick="CruiseshipOrderRoom.doSubmit()">下一步</a>
        </form>
      </div>
    </div>


  </div>--%>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>

<script type="text/html" id="tpl-order-cruiseship-room-item">
  <div class="table-body">
    <ul class="clearfix">
      <li><a class="collapse-btn" href="javascript:void(0);" onclick="CruiseshipOrderRoom.collapse(this)">{{name}}</a></li>
      <li>{{area}}<%--m<sup>2</sup>--%></li>
      <li id="peopleNum_{{id}}">{{peopleNum}}</li>
      <li style="color: #f80;">￥{{minPrice}}<input type="hidden" value="{{minPrice}}" id="perPrice_{{id}}"></li>
      <%--<li>
        <div class="room-num-btn clearfix">
          <i class="subtract" onclick="CruiseshipOrderRoom.subtract('{{id}}', 'adult')"></i>
          <span id="adult_{{id}}" data-room-id="{{id}}">0</span>
          <i class="plus" onclick="CruiseshipOrderRoom.plus('{{id}}', 'adult')"></i>
        </div>
      </li>
      <li>
        <div class="room-num-btn children clearfix">
          <i class="subtract" onclick="CruiseshipOrderRoom.subtract('{{id}}', 'child')"></i>
          <span id="child_{{id}}" data-room-id="{{id}}">0</span>
          <i class="plus" onclick="CruiseshipOrderRoom.plus('{{id}}', 'child')"></i>
        </div>
        <p class="children-yo">1岁-12岁</p>
      </li>
      <li>
        <div class="room-num-btn clearfix">
          <i class="subtract"></i>
          <span id="roomnum_{{id}}" data-room-name="{{name}}" data-room-id="{{id}}">0</span>
          <i class="plus"></i>
        </div>
      </li>--%>
    </ul>
    <div class="collapse clearfix">
      <i class="triangle"></i>
      <div class="room-img">
        {{if (coverImage.length > 0)}}
        <img src="<%=com.zuipin.util.QiniuUtil.URL%>{{coverImage}}?imageView2/1/w/163/h/135/q/95">
        {{else}}
        <img src="/image/cruises-choose-cabin-room-img.png">
        {{/if}}
      </div>
      <div class="room-info">
        <h4>{{name}}</h4>
        <p>甲板楼层：{{deck}}</p>
        <p>舱房详情：{{facilities}}</p>
      </div>
    </div>
  </div>
</script>
<script src="/js/order/cruisehispOrder_room.js"></script>
</html>
