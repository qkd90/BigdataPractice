<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/4/1
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>异常订单详情列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/order.index.css"/>
</head>
<body>
<div class="main-wrap">
    <div title="异常订单详情列表" data-options="fit:true,border:false" style="width:100%;height:100%;"
         class="easyui-layout order-table" id="total-order-table-wrap">
        <div id="abnormal-orderDaetail-searcher" style="padding:3px">
            <span>
                <select id="search-type">
                    <option value="orderDetail.order.orderNo">订单号</option>
                    <%--<option value="orderDetail.order.id">订单ID</option>--%>
                    <%--<option value="orderDetail.id">订单详情ID</option>--%>
                    <option value="orderDetail.order.recName">联系人</option>
                    <option value="orderDetail.order.mobile">联系电话</option>
                </select>
            </span>
            <input id="search-content" placeholder="输入查询内容"
                   style="line-height:26px;border:1px solid #ccc; padding: 0 5px;">
            <%--<span>按订单状态搜索：</span>--%>
             <span>
                <select id="search-status">
                    <option value="0">所有状态</option>
                    <option value="1" selected>异常</option>
                    <%--<option value="PAYED">已支付</option>--%>
                    <%--<option value="SUCCESS">交易完成</option>--%>
                    <%--<option value="BOOKING">预订中</option>--%>
                    <%--<option value="CANCELED">已取消</option>--%>
                    <%--<option value="CANCELING">取消中</option>--%>
                    <%--<option value="FAILED">交易失败</option>--%>
                    <%--<option value="RETRY">重试</option>--%>
                </select>
            </span>
             <span>
                <select id="search-productType">
                    <option value="">所有类型</option>
                    <option value="scenic">景点门票</option>
                    <option value="train">火车票</option>
                    <option value="flight">机票</option>
                    <option value="hotel">酒店</option>
                </select>
            </span>
            <span>
                <select id="search-sort-property">
                    <option value="order.createTime">下单时间</option>
                    <%--<option value="modifyTime">更新时间</option>--%>
                    <option value="playDate">出发/入住/起飞时间</option>
                </select>
            </span>
            <span>
                <select id="search-sort-type">
                    <option value="desc">倒序</option>
                    <option value="asc">升序</option>
                    <%--<option></option>--%>
                    <%--<option></option>--%>
                </select>
            </span>
            <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="OrderDetail.doSearch()">查询</a>
        </div>
        <div data-options="region:'center', border:false, title:'订单详情列表'">
            <table id="abnormal-orderDetail-table">
            </table>
        </div>
    </div>
    <div class="easyui-dialog easyui-layout panel-scroll" id="log-panel" closed="true"
         style="width:620px;height: 500px;overflow-y: auto;">
        <div data-options="region:'center',border:false">
            <table id="logDg"></table>
        </div>
    </div>
</div>



<script src="/js/order/order/order.js" type="text/javascript"></script>
<script src="/js/order/order/orderdetail.js" type="text/javascript"></script>
</body>
</html>
