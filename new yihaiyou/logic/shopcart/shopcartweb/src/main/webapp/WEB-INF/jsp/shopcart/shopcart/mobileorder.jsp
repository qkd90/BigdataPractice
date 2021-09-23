<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/20
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>下单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="/css/mobile/common.css">
    <link rel="stylesheet" type="text/css" href="/css/mobile/order.css">
</head>
<body>
<div id="order-panel">


<div class="header-wrap">
    <a class="left-btn" href="javascript:history.go(-1)"></a>

    <h1 class="pageTitle">${productInfo.product.name}订单</h1>
</div>
<div class="main-wrap cf">
    <div class="box-order cf" id="qd">
        <div class="buylist row cf hide" id="price-detail-${productInfo.priceInfo.priceId}">
            <c:if test="${productInfo.priceInfo.childPrice!=null}">
                <span class="col-xs-4">成人价</span>
                <span class="col-xs-4 text-center">￥<span class="adult-price">${productInfo.priceInfo.discountPrice}</span>x<span class="adult-count">1</span></span>
                <span class="col-xs-4 text-center price">￥<span class="adult-total-price">${productInfo.priceInfo.discountPrice}</span></span>
                <span class="col-xs-4">儿童价</span>
                <span class="col-xs-4 text-center">￥<span class="child-price">${productInfo.priceInfo.childPrice}</span>x<span class="child-count">0</span></span>
                <span class="col-xs-4 text-center price">￥<span class="child-total-price">0</span></span>
            </c:if>
            <c:if test="${productInfo.priceInfo.childPrice==null}">
                <span class="col-xs-4">单价</span>
                <span class="col-xs-4 text-center">￥<span class="adult-price">${productInfo.priceInfo.discountPrice}</span>x<span class="adult-count">1</span></span>
                <span class="col-xs-4 text-center price">￥<span class="adult-total-price">${productInfo.priceInfo.discountPrice}</span></span>

            </c:if>
        </div>

        <div class="row text-right">
            <div class="total cf">
                <span class="all fl">订单总额：￥<span id="total-price">${productInfo.priceInfo.discountPrice}</span></span>
                <%--<span class="buytotal">应付总额：<b>￥<span id="final-price">${productInfo.priceInfo.discountPrice}</span></b></span>--%>
                <span class="arrow fr"></span>
            </div>
        </div>

    </div>
    <div class="clearfix" id="orderlist">
        <input type="hidden" value="${productInfo.product.id}" id="productId"/>
        <input type="hidden" value="${productInfo.product.proType}" id="productType"/>

        <div class="info clearfix">
            <span>发团日期：<span id="start-date">${productInfo.startDay}</span></span>
            <c:if test="${productInfo.endDay != null}">
                <span>返程日期：${productInfo.endDay}</span>
            </c:if>
            <span>出发地：${productInfo.startCity.name}</span>
            <span>供应商：${productInfo.product.user.sysUnit.name}</span>
        </div>

        <div class="box-order clearfix price-list-panel">
            <h4>报名信息</h4>

            <div class="price-list clearfix box-order-content order-panel cf">
                <input type="hidden" value="${productInfo.priceInfo.priceId}" class="price-id"/>
                <input type="hidden" value="${productInfo.priceInfo.dateId}" class="price-date-id"/>
                <input type="hidden" value="${productInfo.priceInfo.costType}" class="price-cost-type"/>
                <span class="col-xs-12 price-name">${productInfo.priceInfo.costName}</span>
                <span class="col-xs-12">
                    <span class="clearfix order-count-panel cf">
                        <span class="col-xs-3 price-name">成人价</span>
                        <span class="price col-xs-3 text-center price-amount adult-price">${productInfo.priceInfo.discountPrice}</span>
                        <span class="col-xs-6 text-center">
                            <input name="" class="minus col-xs-3" type="button" value="-"/>
                            <input name="" class="num adult-num col-xs-6" type="text" value="${adultCount>1?adultCount:1}"/>
                            <input name="" class="plus col-xs-3" type="button" value="+"/>
                            <input type="hidden" value="ADULT" class="price-type" }/>
                        </span>
                        <%--<span class="col-xs-3 text-center">300（没处理）</span>--%>
                    </span>
                    <c:if test="${productInfo.priceInfo.childPrice!=null}">
                    <span class="clearfix order-count-panel">
                       <span class="col-xs-3 price-name">儿童价</span>
                        <span class="price col-xs-3 text-center price-amount child-price">${productInfo.priceInfo.childPrice}</span>
                        <span class="col-xs-6 text-center">
                            <input name="" class="minus col-xs-3" type="button" value="-"/>
                            <input name="" class="num child-num col-xs-6" type="text" value="${childCount}"/>
                            <input name="" class="plus col-xs-3" type="button" value="+"/>
                            <input type="hidden" value="CHILD" class="price-type"/>
                        </span>
                        <%--<span class="col-xs-3 text-center">300（没处理）</span>--%>
                    </span>
                    </c:if>
                </span>
            </div>

        </div>
        <div class="box-order clearfix" id="tourist-list-panel">
            <h4>游客信息
                <small>（供应商确认订单后方可看到游客联系方式，请放心填写） <!--<a href="#">批量导入</a>--></small>
            </h4>
            <div class="price-list box-order-content cf">
                <form class="form-inline" id="kehu">
                    <div class="form-group cf">
                        <label class="col-xs-4"><i>*</i>您的姓名：</label>
                        <input type="text" class="form-control tourist-name col-xs-6">
                    </div>
                    <div class="form-group cf">
                        <label class="col-xs-4"><i>*</i>联系电话：</label>
                        <input type="text" class="form-control tourist-tel col-xs-6">
                    </div>
                    <div class="form-group cf">
                        <label class="col-xs-4"><i></i>证件类型：</label>
                        <select class="form-control tourist-idType col-xs-6">
                            <option value="id">身份证</option>
                            <option value="driver">驾驶证</option>
                        </select>
                        <input type="text" class="form-control tourist-idNumber col-xs-6 col-xs-offset-4">
                    </div>
                    <div class="form-group cf">
                        <label class="col-xs-4"><i></i>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
                        <input type="text" class="form-control tourist-remark col-xs-6">
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!--温馨提醒-->
    <div class="box-order clearfix">
        <h4>温馨提示</h4>

        <div class="tixing box-order-content">
            1. 平台对交易资金进行担保，在线支付后款项托管到平台，交易完成后再付款至供应商帐号，全面保障您的资金安全；<br>
            2. 预定即时确认产品，订单提交后即可直接在线支付；预定二次确认后方可支付；<br>
            3. 分销商下单后，在订单确认前，分销商可以取消订单。<br>
        </div>
    </div>
    <!--温馨提醒-->

</div>
<div class="clearfix tijiao text-center">
    <a id="form-submit" name="" class="btn btn-warning form-submit" >提交订单</a>
        <span>（若价格变动，请在提交订单后联系供应商改价）</span>
</div>
</div>
<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/shopcart/shopcart/mobile.order.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
