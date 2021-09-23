<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/18
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="m_banner" style="background:url(/images/gerenzhongxin.png) center center no-repeat;">
    <div class="w1000 posiR">
        <p class="name fs16 b posiA">${user.nickName}</p>
        <p class="img posiA">
            <c:choose>
                <c:when test="${user.head != null && user.head != ''}">
                    <c:choose>
                        <c:when test="${fn: startsWith(user.head, 'http')}">
                            <img src="${user.head}" alt="" id="head-pic" style="width: 100%; height: 100%"/>
                        </c:when>
                        <c:otherwise>
                            <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${user.head}" id="head-pic"
                                 style="width: 100%; height: 100%"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <img src="/images/toux.PNG" id="head-pic" style="width: 100%; height: 100%"/>
                </c:otherwise>
            </c:choose>
        </p>
    </div>
</div>

<div class="m_nav cl">
    <div class="w1000">
        <p class="m_nav_p">
            <a href="${INDEX_PATH}/lvxbang/user/plan.jhtml" class="personal checked" id="plan-panel">我的线路</a>
            <a href="${INDEX_PATH}/lvxbang/user/recplan.jhtml" class="personal" id="recplan-panel">我的游记</a>
            <a href="${INDEX_PATH}/lvxbang/user/favorite.jhtml" class="personal" id="favorite-panel">我的收藏</a>
            <a href="${INDEX_PATH}/lvxbang/user/comment.jhtml" class="personal" id="comment-panel">我的点评</a>
            <a href="${INDEX_PATH}/lvxbang/user/order.jhtml" class="personal" id="order-panel">我的订单</a>
            <a href="${INDEX_PATH}/lvxbang/user/tourist.jhtml" class="personal" id="tourist-panel">常用出行人</a>
            <a href="${INDEX_PATH}/lvxbang/user/index.jhtml" class="personal" id="info-panel">个人信息</a>
            <a href="${INDEX_PATH}/lvxbang/user/coupon.jhtml" class="personal" id="coupon-panel">我的红包</a>
        </p>
        <p class="cl"></p>
    </div>
</div>
