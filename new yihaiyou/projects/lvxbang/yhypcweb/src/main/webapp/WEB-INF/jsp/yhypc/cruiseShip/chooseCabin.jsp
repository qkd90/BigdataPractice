<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>舱房详情</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/choose_cabin.css">
</head>
<%@include file="../../yhypc/public/nav_header.jsp"%>

<body>
<input type="hidden" value="${dateId}" id="hid_dateId">
<input type="hidden" value="${cruiseShip.id}" id="hid_productId">
<div class="wrap-box">
    <div class="wrap">
        <div class="wrap-header clearfix">
            <div class="header-group active pull-left">
                <div class="header-content">
                    <i></i>
                    <span>选择舱房</span>
                </div>
            </div>
            <div class="header-group pull-left">
                <div class="header-content">
                    <i></i>
                    <span>填写信息</span>
                </div>
            </div>
            <div class="header-group pull-left">
                <div class="header-content">
                    <i></i>
                    <span>支付并提交</span>
                </div>
            </div>
        </div>
        <div class="product clearfix">
            <div class="product-img pull-left">
                <img src="${QINIU_BUCKET_URL}${cruiseShip.coverImage}">
            </div>
            <div class="product-intro pull-left">
                <h5>${cruiseShip.routeName}${cruiseShip.name}</h5>
                <div class="product-star clearfix">
                    <i class="star star04 pull-left"></i>
                    <span class="pull-left">${cruiseShip.avgScore}<sub>分</sub></span>
                    <sub class="custom-recommend pull-left">来自8名游客的真实点评</sub>
                </div>
                <div class="boat-time clearfix">
                    <div class="time-group pull-left">
                        <label>上船时间：</label>
                        <div class="time clearfix">
                            <span class="pull-left"><fmt:formatDate value="${cruiseShip.startDate}" pattern="E"></fmt:formatDate></span>
                            <i class="pull-left week">

                            </i>
                            <a href="#" class="pull-left">更改出发时间<i></i></a>
                        </div>
                    </div>
                    <div class="time-group pull-left">
                        <label>下船时间：</label>
                        <div class="time clearfix">
                            <span class="pull-left"><fmt:formatDate value="${cruiseShip.endDate}" pattern="E"></fmt:formatDate></span>
                            <i class="pull-left week">

                            </i>
                        </div>
                    </div>
                </div>
            </div>
            <a href="/yhypc/cruiseShip/detail.jhtml?dateId=${dateId}" class="product-link pull-right">查看产品详情</a>
        </div>
        <div class="product-select">
            <div class="select-wrap clearfix">
                <ul class="select-nav pull-left clearfix">
                    <li class="active pull-left">
                        <span>内舱房</span>
                        <i>¥${cruiseShipRoomInside[0].minPrice}起</i>
                    </li>
                    <li class="pull-left">
                        <span>海景房</span>
                        <i>¥${cruiseShipRoomSeascape[0].minPrice}起</i>
                    </li>
                    <li class="pull-left">
                        <span>海景阳台房</span>
                        <i>¥${cruiseShipRoomBalcony[0].minPrice}起</i>
                    </li>
                    <li class="pull-left">
                        <span>套房</span>
                        <i>¥${cruiseShipRoomSuite[0].minPrice}起</i>
                    </li>
                </ul>
                <div class="select-tips pull-left">
                    <!--没有订单数据的情况下显示-->
                    <div class="select-nodata" style="display: none;">
                        <span>请在下方选择入住人数进行预订</span>
                            <span class="tips-icon">
                            入住提示<i></i>
                            <div class="tips-details" style="display: none;">
                                1.  除套房以外的房型面积有限，如四位成人入住一间，空间会相当拥挤，望您谅解。<br>
                                2.  儿童和成人都必须占床，儿童是否能享受优惠价请参见您所选的舱房说明。
                            </div>
                        </span>
                    </div>
                    <!--有订单数据的情况下显示-->
                    <div class="select-data clearfix ">
                        <div class="data-left pull-left ">
                            <p class="totalPerson">入住人数： 人</p>
                            <p id="roomNum">房间：间</p>
                        </div>
                        <div class="data-right pull-left">
                            <div class="price-all clearfix">
                                <div class="all-left pull-left" id="totalPrice">
                                    总价：<span><sub>¥</sub></span>
                                </div>
                                <a href="javascript:;" class="all-details-btn pull-left">
                                    <span>明细</span>
                                    <i></i>
                                </a>
                            </div>
                            <div class="price-average">
                                人均：¥
                            </div>
                            <div class="price-details" style="display: none;">

                                <table class="orderDetails">
                                    <tbody >
                                    <tr >

                                    </tr>
                                    </tbody>
                                </table>
                                <div class="tips">
                                    以上价格包括：<br>
                                    船票、港务费及税费
                                </div>
                                <div class="warning">
                                    温馨提示：部分与年龄有关的优惠，将在填写旅客出生日期之后计入总价
                                </div>
                            </div>
                        </div>
                        <form id="sel-room-form" method="post" action="/yhypc/order/orderCruiseshipWrite.jhtml">
                            <input type="hidden" name="cruiseshiId" id="hid-form-product-id">
                            <input type="hidden" name="dateId" id="hid-form-date-id">
                            <input type="hidden" name="totalPrice" id="hid-form-totalprice"> </form>
                        <div class="select-submit pull-left">
                            <a class="pull-left" href="#" onclick="chooseCabin.doSubmit()">
                                <i>下一步</i>
                                <span>填写相关信息&gt;</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="select-input clearfix">
                <label class="pull-left">按每间可入住人数筛选：</label>
                <div class="person pull-left">
                    <div class="person-show clearfix">
                        <span class="pull-left">不限</span>
                        <i class="pull-right"></i>
                    </div>
                    <ul class="person-list clearfix">
                        <li value="0">不限</li>
                        <li value="1">可住1人</li>
                        <li value="2">可住2人</li>
                        <li value="3">可住3人</li>
                        <li value="4">可住4人</li>
                    </ul>
                </div>
                <%--<div class="person-favorable pull-left clearfix">
                    <div class="favorable-group pull-left clearfix">
                        <i class="pull-left"></i>
                        <span class="pull-left">舱房礼遇</span>
                    </div>
                    <div class="favorable-group pull-left clearfix">
                        <i class="pull-left"></i>
                        <span class="pull-left">立减优惠</span>
                    </div>
                    <div class="favorable-group pull-left clearfix">
                        <i class="pull-left"></i>
                        <span class="pull-left">套房特惠</span>
                    </div>
                </div>--%>
                <a href="javascript:;" class="person-favorable-clear pull-left" style="display: none;">
                    清除
                </a>
            </div>
        </div>

        <c:if test="${cruiseShipRoomInside.size() > 0}">
        <div class="cabin-category">
            <h5>内舱房</h5>
            <c:forEach items="${cruiseShipRoomInside}" var="cruiseShipRoomInsideItem">
                <div class="cabin-group clearfix" price="${cruiseShipRoomInsideItem.minPrice}" peopleNum="${cruiseShipRoomInsideItem.peopleNum}" totalprice="" data-room-id="${cruiseShipRoomInsideItem.id}">
                    <div class="group-left pull-left clearfix allOfPrice"  totalPrice="">
                        <img src="${QINIU_BUCKET_URL}${cruiseShipRoomInsideItem.coverImage}" class="pull-left">
                        <div class="cabin-txt pull-left">
                            <h5 class="roomName" name="${cruiseShipRoomInsideItem.name}" >${cruiseShipRoomInsideItem.name}</h5>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomInsideItem.area}</span>
                                <span class="pull-left">必须入住${cruiseShipRoomInsideItem.peopleNum}人</span>
                            </div>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomInsideItem.deck}层</span>
                            </div>
                        </div>
                    </div>
                    <div class="group-center pull-left">
                        <!--有优惠政策-->
                        <%--<div class="sales" style="display: none">
                            <span class="price"><i>优惠</i><sub>¥</sub>2000<sub>起/人</sub></span>
                            &lt;%&ndash;<span class="price"><i>优惠</i><sub>¥</sub>2000<sub>起/人</sub></span>
                            <span class="favorable">立减优惠减¥400/人<mark>（已减）</mark></span>&ndash;%&gt;
                        </div>--%>
                        <!--没有优惠政策的-->
                        <div class="no-sales">
                            价格：<span><sub>¥</sub>${cruiseShipRoomInsideItem.minPrice}<sub>起/人</sub></span>
                        </div>
                    </div>
                    <div class="group-right pull-right clearfix">
                        <div class="room pull-left">
                            <label>成人</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left adult">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>儿童</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left children">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                            <div class="room-tips">
                                6个月-18岁
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>房间数</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left roomNum" >
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="price pull-left">
                            <!--价格初始状态-->
                            <div class="price-init">
                                请先在左侧选择入住人数
                            </div>
                            <!--数量错误提示状态-->
                            <div class="price-error" style="display: none;">
                                每间舱房必须入住${cruiseShipRoomInsideItem.peopleNum}人，请修改
                                <a href="#" class="clear">清除</a>
                            </div>
                            <!--正常显示价格状态-->
                            <div class="price-normal" style="display: none;">
                                <span class="original-price">¥5678</span>
                                <div class="last-price">
                                    <div class="last-price-previews">
                                        <sub>¥</sub>6789<i class="last-price-icon"></i>
                                        <div class="average-price">
                                            人均：¥2789
                                        </div>
                                        <a href="#" class="clear">清除</a>
                                    </div>
                                </div>
                                <div class="last-price-views" style="display: none">
                                    <div class="views-title">
                                        总计：<span><sup>¥</sup>8998<sub>人均：¥4499</sub></span>
                                    </div>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>人均价格（¥4499）</td>
                                            <td>x2</td>
                                            <td>¥8998</td>
                                        </tr>
                                        <tr>
                                            <td>单人房差（¥3799）</td>
                                            <td>x2</td>
                                            <td>¥7598</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="views-txt">
                                        以上价格包含：<br>
                                        船票，港务票及税费，鹿儿岛+佐世保世岸上观光A线，日本免签船舶观光上陆许可证（适合邮轮，上海送签）
                                    </div>
                                    <div class="views-tips">
                                        单人房差：当一间房入住人数不足2人时，需补足房费¥3799
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        </c:if>
        <c:if test="${cruiseShipRoomSeascape.size() > 0}">
            <div class="cabin-category">
            <h5>海景房</h5>
            <c:forEach items="${cruiseShipRoomSeascape}" var="cruiseShipRoomSeascapeItem">
                <div class="cabin-group clearfix" price="${cruiseShipRoomSeascapeItem.minPrice}" peopleNum="${cruiseShipRoomSeascapeItem.peopleNum}" totalprice="" data-room-id="${cruiseShipRoomSeascapeItem.id}">
                    <div class="group-left pull-left clearfix allOfPrice" totalPrice="">
                        <img src="${QINIU_BUCKET_URL}${cruiseShipRoomSeascapeItem.coverImage}" class="pull-left">
                        <div class="cabin-txt pull-left">
                            <h5 class="roomName" name="${cruiseShipRoomSeascapeItem.name}" >${cruiseShipRoomSeascapeItem.name}</h5>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomSeascapeItem.area}</span>
                                <span class="pull-left">必须入住${cruiseShipRoomSeascapeItem.peopleNum}人</span>
                            </div>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomSeascapeItem.deck}层</span>
                            </div>
                        </div>
                    </div>
                    <div class="group-center pull-left">
                       <%-- <!--有优惠政策-->
                        <div class="sales" style="display: none">
                            <span class="price"><i>优惠</i><sub>¥</sub>2000<sub>起/人</sub></span>
                            <span class="favorable">立减优惠减¥400/人<mark>（已减）</mark></span>
                        </div>--%>
                        <!--没有优惠政策的-->
                        <div class="no-sales">
                            价格：<span><sub>¥</sub>${cruiseShipRoomSeascapeItem.minPrice}<sub>起/人</sub></span>
                        </div>
                    </div>
                    <div class="group-right pull-right clearfix">
                        <div class="room pull-left">
                            <label>成人</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left adult">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>儿童</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left children">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                            <div class="room-tips">
                                6个月-18岁
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>房间数</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left roomNum">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="price pull-left">
                            <!--价格初始状态-->
                            <div class="price-init">
                                请先在左侧选择入住人数
                            </div>
                            <!--数量错误提示状态-->
                            <div class="price-error" style="display: none;">
                                每间舱房必须入住${cruiseShipRoomSeascapeItem.peopleNum}人，请修改
                                <a href="#" class="clear">清除</a>
                            </div>
                            <!--正常显示价格状态-->
                            <div class="price-normal" style="display: none;">
                                <span class="original-price">¥5678</span>
                                <div class="last-price">
                                    <div class="last-price-previews">
                                        <sub>¥</sub>6789<i class="last-price-icon"></i>
                                        <div class="average-price">
                                            人均：¥2789
                                        </div>
                                        <a href="#" class="clear">清除</a>
                                    </div>
                                </div>
                                <div class="last-price-views" style="display: none">
                                    <div class="views-title">
                                        总计：<span><sup>¥</sup>8998<sub>人均：¥4499</sub></span>
                                    </div>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>人均价格（¥4499）</td>
                                            <td>x2</td>
                                            <td>¥8998</td>
                                        </tr>
                                        <tr>
                                            <td>单人房差（¥3799）</td>
                                            <td>x2</td>
                                            <td>¥7598</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="views-txt">
                                        以上价格包含：<br>
                                        船票，港务票及税费，鹿儿岛+佐世保世岸上观光A线，日本免签船舶观光上陆许可证（适合邮轮，上海送签）
                                    </div>
                                    <div class="views-tips">
                                        单人房差：当一间房入住人数不足2人时，需补足房费¥3799
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        </c:if>
        <c:if test="${cruiseShipRoomBalcony.size() > 0}">
        <div class="cabin-category">
            <h5>海景阳台房</h5>
            <c:forEach items="${cruiseShipRoomBalcony}" var="cruiseShipRoomBalconyItem">
                <div class="cabin-group clearfix" price="${cruiseShipRoomBalconyItem.minPrice}" peopleNum="${cruiseShipRoomBalconyItem.peopleNum}" totalprice="" data-room-id="${cruiseShipRoomBalconyItem.id}">
                    <div class="group-left pull-left clearfix allOfPrice"  totalPrice="">
                        <img src="${QINIU_BUCKET_URL}${cruiseShipRoomBalconyItem.coverImage}" class="pull-left">
                        <div class="cabin-txt pull-left">
                            <h5 class="roomName" name="${cruiseShipRoomBalconyItem.name}" >${cruiseShipRoomBalconyItem.name}</h5>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomBalconyItem.area}</span>
                                <span class="pull-left">必须入住${cruiseShipRoomBalconyItem.peopleNum}人</span>
                            </div>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomBalconyItem.deck}层</span>
                            </div>
                        </div>
                    </div>
                    <div class="group-center pull-left">
                        <%--<!--有优惠政策-->
                        <div class="sales" style="display: none">
                            <span class="price"><i>优惠</i><sub>¥</sub>2000<sub>起/人</sub></span>
                            <span class="favorable">立减优惠减¥400/人<mark>（已减）</mark></span>
                        </div>--%>
                        <!--没有优惠政策的-->
                        <div class="no-sales">
                            价格：<span><sub>¥</sub>${cruiseShipRoomBalconyItem.minPrice}<sub>起/人</sub></span>
                        </div>
                    </div>
                    <div class="group-right pull-right clearfix">
                        <div class="room pull-left">
                            <label>成人</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left adult">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>儿童</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left children">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                            <div class="room-tips">
                                6个月-18岁
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>房间数</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left roomNum">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="price pull-left">
                            <!--价格初始状态-->
                            <div class="price-init">
                                请先在左侧选择入住人数
                            </div>
                            <!--数量错误提示状态-->
                            <div class="price-error" style="display: none;">
                                每间舱房必须入住4${cruiseShipRoomBalconyItem.peopleNum}人，请修改
                                <a href="#" class="clear">清除</a>
                            </div>
                            <!--正常显示价格状态-->
                            <div class="price-normal" style="display: none;">
                                <span class="original-price">¥5678</span>
                                <div class="last-price">
                                    <div class="last-price-previews">
                                        <sub>¥</sub>6789<i class="last-price-icon"></i>
                                        <div class="average-price">
                                            人均：¥2789
                                        </div>
                                        <a href="#" class="clear">清除</a>
                                    </div>
                                </div>
                                <div class="last-price-views" style="display: none">
                                    <div class="views-title">
                                        总计：<span><sup>¥</sup>8998<sub>人均：¥4499</sub></span>
                                    </div>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>人均价格（¥4499）</td>
                                            <td>x2</td>
                                            <td>¥8998</td>
                                        </tr>
                                        <tr>
                                            <td>单人房差（¥3799）</td>
                                            <td>x2</td>
                                            <td>¥7598</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="views-txt">
                                        以上价格包含：<br>
                                        船票，港务票及税费，鹿儿岛+佐世保世岸上观光A线，日本免签船舶观光上陆许可证（适合邮轮，上海送签）
                                    </div>
                                    <div class="views-tips">
                                        单人房差：当一间房入住人数不足2人时，需补足房费¥3799
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        </c:if>
        <c:if test="${cruiseShipRoomSuite.size() > 0}">
        <div class="cabin-category">
            <h5>套房</h5>
            <c:forEach items="${cruiseShipRoomSuite}" var="cruiseShipRoomSuiteItem">
                <div class="cabin-group clearfix" price="${cruiseShipRoomSuiteItem.minPrice}"  peopleNum="${cruiseShipRoomSuiteItem.peopleNum}" totalprice="" data-room-id="${cruiseShipRoomSuiteItem.id}">
                    <div class="group-left pull-left clearfix allOfPrice"  totalPrice="">
                        <img src="${QINIU_BUCKET_URL}${cruiseShipRoomSuiteItem.coverImage}" class="pull-left">
                        <div class="cabin-txt pull-left">
                            <h5 class="roomName" name="${cruiseShipRoomSuiteItem.name}" >${cruiseShipRoomSuiteItem.name}</h5>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomSuiteItem.area}</span>
                                <span class="pull-left">必须入住${cruiseShipRoomSuiteItem.peopleNum}人</span>
                            </div>
                            <div class="txt-group clearfix">
                                <span class="pull-left">${cruiseShipRoomSuiteItem.deck}层</span>
                            </div>
                        </div>
                    </div>
                    <div class="group-center pull-left">
                        <%--<!--有优惠政策-->
                        <div class="sales" style="display: none">
                            <span class="price"><i>优惠</i><sub>¥</sub>2000<sub>起/人</sub></span>
                            <span class="favorable">立减优惠减¥400/人<mark>（已减）</mark></span>
                        </div>--%>
                        <!--没有优惠政策的-->
                        <div class="no-sales">
                            价格：<span><sub>¥</sub>${cruiseShipRoomSuiteItem.minPrice}<sub>起/人</sub></span>
                        </div>
                    </div>
                    <div class="group-right pull-right clearfix">
                        <div class="room pull-left">
                            <label>成人</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left adult">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>儿童</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left children">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                            <div class="room-tips">
                                6个月-18岁
                            </div>
                        </div>
                        <div class="room pull-left">
                            <label>房间数</label>
                            <div class="count clearfix">
                                <a href="javascript:;" class="minus pull-left"></a>
                                <input type="text" value="0" class="value pull-left roomNum">
                                <a href="javascript:;" class="plus pull-left"></a>
                            </div>
                        </div>
                        <div class="price pull-left">
                            <!--价格初始状态-->
                            <div class="price-init">
                                请先在左侧选择入住人数
                            </div>
                            <!--数量错误提示状态-->
                            <div class="price-error" style="display: none;">
                                每间舱房必须入住${cruiseShipRoomSuiteItem.peopleNum}人，请修改
                                <a href="#" class="clear">清除</a>
                            </div>
                            <!--正常显示价格状态-->
                            <div class="price-normal" style="display: none;">
                                <span class="original-price">¥5678</span>
                                <div class="last-price">
                                    <div class="last-price-previews">
                                        <sub>¥</sub>6789<i class="last-price-icon"></i>
                                        <div class="average-price">
                                            人均：¥2789
                                        </div>
                                        <a href="#" class="clear">清除</a>
                                    </div>
                                </div>
                                <div class="last-price-views" style="display: none">
                                    <div class="views-title">
                                        总计：<span><sup>¥</sup>8998<sub>人均：¥4499</sub></span>
                                    </div>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>人均价格（¥4499）</td>
                                            <td>x2</td>
                                            <td>¥8998</td>
                                        </tr>
                                        <tr>
                                            <td>单人房差（¥3799）</td>
                                            <td>x2</td>
                                            <td>¥7598</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="views-txt">
                                        以上价格包含：<br>
                                        船票，港务票及税费，鹿儿岛+佐世保世岸上观光A线，日本免签船舶观光上陆许可证（适合邮轮，上海送签）
                                    </div>
                                    <div class="views-tips">
                                        单人房差：当一间房入住人数不足2人时，需补足房费¥3799
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>

        </div>
        </c:if>
    </div>
</div>

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/choose_cabin.js"></script>
</body></html>
<%@include file="../../yhypc/public/nav_footer.jsp"%>