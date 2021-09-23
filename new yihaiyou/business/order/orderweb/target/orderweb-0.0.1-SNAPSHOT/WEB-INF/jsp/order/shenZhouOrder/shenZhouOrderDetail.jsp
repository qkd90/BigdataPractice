<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/9/14
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/szdetail.css"/>
<html>
<head>
    <title></title>
</head>
<body>
  <div class="detail-panel">
    <strong class="title">订单信息</strong>
    <table style="padding: 3px;">
      <tr>
        <td class="label-td">下单时间：</td>
        <td class="order-time">
          <s:if test="shenzhouOrder.createTime != null">
            <s:date name="shenzhouOrder.createTime" format="yyyy-MM-dd HH:mm:ss"/>
          </s:if>
          <s:else>
            无
          </s:else>
        </td>
      </tr>
      <tr>
        <td class="label-td">下单帐号：</td>
        <td class="name">${shenzhouOrder.user.account}</td>
      </tr>
      <tr>
        <td class="label-td">服务类型：</td>
        <td class="mobile">
          <s:if test="shenzhouOrder.serviceId == 7">
            接机
          </s:if>
          <s:if test="shenzhouOrder.serviceId == 8">
            送机
          </s:if>
          <s:if test="shenzhouOrder.serviceId == 11">
            半日租
          </s:if>
          <s:if test="shenzhouOrder.serviceId == 12">
            日租
          </s:if>
          <s:if test="shenzhouOrder.serviceId == 13">
            预约用车
          </s:if>
          <s:if test="shenzhouOrder.serviceId == 14">
            立即叫车
          </s:if>
       </td>
      </tr>
      <tr>
        <td class="label-td">车组：</td>
        <td class="mobile">
          <s:if test="shenzhouOrder.carGroupId == 2">
            公务轿车
          </s:if>
          <s:if test="shenzhouOrder.carGroupId == 3">
            商务
          </s:if>
          <s:if test="shenzhouOrder.carGroupId == 4">
            豪华轿车
          </s:if>
        </td>
      </tr>
    </table>
  </div>
  <div class="detail-panel">
    <strong class="title">状态信息</strong>
    <table style="padding: 3px;">
      <tr>
        <td class="label-td">订单状态：</td>
        <td class="order-time" style="width: 300px;">
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@created">
            新建
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@deleted">
            已删除
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@invalid">
            无效
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@dispatched">
            已派单
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@arriving">
            已出发
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@arrived">
            已到达
          </s:if>


          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@canceled">
            已取消
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@serviceStarted">
            已开始服务
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@serviceFinished">
            已结束服务
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@feeSubmitted">
            已提交费用
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@paid">
            已支付待评价
          </s:if>
          <s:if test="shenzhouOrder.status == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus@completed">
            已完成
          </s:if>
        </td>
        <td class="label-td">订单支付状态：</td>
        <td class="name">
          <s:if test="shenzhouOrder.paymentStatus == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus@unpaid">
            未支付
          </s:if>
          <s:if test="shenzhouOrder.paymentStatus == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus@paying'">
            支付处理中
          </s:if>
          <s:if test="shenzhouOrder.paymentStatus == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus@paymentFailure">
            支付失败
          </s:if>
          <s:if test="shenzhouOrder.paymentStatus == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus@partPayment">
            部分支付
          </s:if>
          <s:if test="shenzhouOrder.paymentStatus == @com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus@paid">
            已支付
          </s:if>
        </td>
      </tr>
    </table>
  </div>
  <div class="detail-panel">
    <strong class="title">路程相关</strong>
    <table style="margin: 3px; border:solid #CDD0CE; border-collapse:collapse; border-width:1px 0px 0px 1px;">
      <tr style="background-color: #eae8e8">
        <td class="lucheng-td">出发地名称</td>
        <td class="lucheng-td">到达地名称</td>
        <td class="lucheng-td">本单里程（米）</td>
        <td class="lucheng-td">本单时长（分）</td>
        <td class="lucheng-td">用车时间</td>
        <td class="lucheng-td">派单时间</td>
        <td class="lucheng-td">订单完成时间</td>
      </tr>
      <tr>
        <td class="lucheng-td">${shenzhouOrder.startName}</td>
        <td class="lucheng-td">${shenzhouOrder.endName}</td>
        <td class="lucheng-td">${shenzhouOrder.distance}</td>
        <td class="lucheng-td">${shenzhouOrder.duration}</td>
        <td class="lucheng-td">
          <s:if test="shenzhouOrder.departureTime != null">
            <s:date name="shenzhouOrder.departureTime" format="yyyy-MM-dd HH:mm:ss"/>
          </s:if>
          <s:else>
            无
          </s:else>

        </td>
        <td class="lucheng-td">
          <s:if test="shenzhouOrder.dispatchedTime != null">
            <s:date name="shenzhouOrder.dispatchedTime" format="yyyy-MM-dd HH:mm:ss"/>
          </s:if>
          <s:else>
            无
          </s:else>
        </td>
        <td class="lucheng-td">
          <s:if test="shenzhouOrder.finishedTime != null">
            <s:date name="shenzhouOrder.finishedTime" format="yyyy-MM-dd HH:mm:ss"/>
          </s:if>
          <s:else>
            无
          </s:else>
        </td>
      </tr>
    </table>
  </div>
  <div class="detail-panel">
    <strong class="title">费用信息</strong>
    <table style="margin: 3px; border:solid #CDD0CE; border-collapse:collapse; border-width:1px 0px 0px 1px;">
      <tr style="background-color: #eae8e8">
        <td class="lucheng-td">起租价</td>
        <td class="lucheng-td">里程费</td>
        <td class="lucheng-td">时长费</td>
        <td class="lucheng-td">远途费</td>
        <td class="lucheng-td">本单欠费金额</td>
        <td class="lucheng-td">总费用</td>
      </tr>
      <tr>
        <td class="lucheng-td">${shenzhouOrder.startPrice}</td>
        <td class="lucheng-td">${shenzhouOrder.kilometrePrice}</td>
        <td class="lucheng-td">${shenzhouOrder.timePrice}</td>
        <td class="lucheng-td">${shenzhouOrder.longDistancePrice}</td>
        <td class="lucheng-td">${shenzhouOrder.arrearsPrice}</td>
        <td class="lucheng-td">${shenzhouOrder.totalPrice}</td>
      </tr>
    </table>
  </div>
  <div class="detail-panel">
    <strong class="title">司机信息</strong>
    <table style="margin: 3px; border:solid #CDD0CE; border-collapse:collapse; border-width:1px 0px 0px 1px;">
      <tr style="background-color: #eae8e8">
        <td class="lucheng-td">司机姓名</td>
        <td class="lucheng-td">车牌号</td>
        <td class="lucheng-td">司机手机号（订）</td>
        <td class="lucheng-td">司机手机号（乘）</td>
        <td class="lucheng-td">车型名称</td>
        <td class="lucheng-td">司机评分</td>
      </tr>
      <tr>
        <td class="lucheng-td">${shenzhouOrder.driverName}</td>
        <td class="lucheng-td">${shenzhouOrder.vehicleNo}</td>
        <td class="lucheng-td">${shenzhouOrder.virtualPhone4Purchaser}</td>
        <td class="lucheng-td">${shenzhouOrder.virtualPhone4Passenger}</td>
        <td class="lucheng-td">${shenzhouOrder.vehicleModel}</td>
        <td class="lucheng-td">${shenzhouOrder.driverScore}</td>
      </tr>
    </table>
  </div>
  <div class="detail-panel">
    <strong class="title">乘客信息</strong>
    <table style="margin: 3px; border:solid #CDD0CE; border-collapse:collapse; border-width:1px 0px 0px 1px;">
      <tr style="background-color: #eae8e8">
        <td class="lucheng-td">乘客姓名</td>
        <td class="lucheng-td">乘客手机号</td>
      </tr>
      <tr>
        <td class="lucheng-td">${shenzhouOrder.passengerName}</td>
        <td class="lucheng-td">${shenzhouOrder.passengerMobile}</td>
      </tr>
    </table>
  </div>
</body>
</html>
