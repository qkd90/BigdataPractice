<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-10,0010
  Time: 16:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" href="/css/scenic/scenicDetail.css">
    <link rel="stylesheet" href="/css/public/pager.css">
    <title>${scenicInfo.name}-景点详情-一海游</title>
</head>
<body class="hotel scenic">
<div class="hotelIndex">
    <%@include file="../../yhypc/public/nav_header.jsp" %>
    <input type="hidden" id="scenicId" value="${scenicInfo.id}">
    <input type="hidden" value="${scenicInfo.id}" id="productId">
    <input type="hidden" value="scenic" id="proType">
    <input type="hidden" id="name" value="${scenicInfo.name}">
    <input type="hidden" id="cover" value="${QINIU_BUCKET_URL}${scenicInfo.cover}">
    <input type="hidden" id="score" value="${scenicInfo.score / 20}">
    <input type="hidden" id="adviceTime" value="${scenicInfo.scenicOther.adviceTimeDesc}">
    <input type="hidden" id="lng" value="${scenicInfo.scenicGeoinfo.baiduLng}">
    <input type="hidden" id="lat" value="${scenicInfo.scenicGeoinfo.baiduLat}">
    <div class="detailTop">
        <div class="outrangestate">
            <div class="rangestate">
                您在这里：<a href="/yhypc/index/index.jhtml">首页</a> &gt; <a href="/yhypc/scenic/index.jhtml">景点门票</a> &gt;<a href="/yhypc/scenic/list.jhtml">厦门景点</a> &gt; ${scenicInfo.name}
            </div>
        </div>
        <div class="hotelbox clearfix">
            <div class="pictrue clearfix">
                <div class="pic_max">
                    <c:if test="${scenicInfo.scenicGalleryList.size() <= 0}">
                        <img src="/image/default-lg670.png">
                    </c:if>
                    <c:forEach items="${scenicInfo.scenicGalleryList}" var="gallery">
                        <img src="${QINIU_BUCKET_URL}${gallery.imgUrl}?imageView2/2/w/670/h/366">
                    </c:forEach>
                </div>
                <div class="pic_min">
                    <label class="t_left"><</label>
                    <ul class="clearfix">
                        <c:if test="${scenicInfo.scenicGalleryList.size() <= 0}">
                            <li><img src="/image/default-xm112.png"></li>
                        </c:if>
                        <c:forEach items="${scenicInfo.scenicGalleryList}" var="gallery">
                            <li><img src="${QINIU_BUCKET_URL}${gallery.imgUrl}?imageView2/2/w/112/h/62"></li>
                        </c:forEach>
                    </ul>
                    <label class="t_right">&gt;</label>
                </div>
            </div>
            <div class="describ">
                <div class="address">
                    <p class="name">${scenicInfo.name}</p>
                    <p class="scenic_level">
                        <c:if test="${scenicInfo.level != null && scenicInfo.level != ''}">国家${scenicInfo.level}级旅游景区</c:if></p>
                    <p class="gps"><span class="de_le">区域：</span><c:forEach items="${scenicInfo.scenicAreas}" var="area">【${area.areaName}】</c:forEach></p>
                    <p class="de_address">
                        <span class="addword"><span class="de_le">地址：</span>${scenicInfo.scenicOther.address}</span><span class="de_map" id="jumpToMap">地图</span>
                    </p>
                    <p class="de_address">
                        <span class="de_le">开放时间：</span>${scenicInfo.scenicOther.openTime}
                    </p>
                    <p class="de_address">
                        <span class="de_le">建议游玩时间：</span><span
                            class="de_adtime">${scenicInfo.scenicOther.adviceTimeDesc}</span>
                    </p>
                </div>
                <div class="commend">
                    <div class="score">
                        <c:choose>
                            <c:when test="${commentCount <= 0}">
                                <span class="score_gold"><span class="now">5</span> / 5分</span>
                                <span class="starlevel starlevel_5"></span>
                                <span class="score_com">（<span class="num"></span>暂无评论）</span>
                            </c:when>
                            <c:otherwise>
                                <span class="score_gold"><span class="now">${scenicInfo.score / 20}</span> / 5分</span>
                                <span class="starlevel starlevel_<fmt:formatNumber type="number" value="${scenicInfo.score / 20}" maxFractionDigits="1"/>"></span>
                                <span class="score_com">（<span class="num">${commentCount}</span>条评论）</span>
                            </c:otherwise>
                        </c:choose>
                        <span class="love" id="collection">.</span>
                    </div>
                    <div class="desc">
                        <p class="super">.</p>
                        <c:choose>
                            <c:when test="${commentCount <= 0}">
                                <p class="word">暂无评论</p>
                            </c:when>
                            <c:otherwise>
                                <p class="word"></p>
                            </c:otherwise>
                        </c:choose>
                        <p class="sub">.</p>
                    </div>
                </div>
                <div class="price_book">
                    <c:choose>
                        <c:when test="${scenicInfo.minPrice > 0}"><span class="rmb">¥</span><span class="num">${scenicInfo.minPrice}</span><span class="qi">起</span></c:when>
                        <c:otherwise><span class="rmb"></span><span class="num fs22 lh50">暂无可售门票</span><span class="qi"></span></c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="ticketlist">
        <div class="list_head">
            <span class="ticket li_he_active">景点门票</span><span class="scenic_intro">景点介绍</span><span class="scenic_comment">评价(${commentCount})</span><span class="scenic_geo">地图</span>
        </div>
        <div class="ticketbox">
            <p class="ticket_title"><span class="s1">产品名称</span><span class="s2">销售价</span></p>
            <ul class="ticketul" id="ticketList"></ul>
        </div>
    </div>
    <div class="detailList clearfix">
        <div class="listLeft">
            <div class="hotelMessage">
                <h3 class="clearfix"><i class="l_lo"></i><span>景点介绍</span></h3>
                <div class="scenicContain">${scenicInfo.scenicOther.description}</div>
            </div>
            <div class="commendMessage" id="comment_area">
                <h3 class="clearfix"><i class="l_lo"></i><span>评价详情</span></h3>
                <div class="commend_score clearfix">
                    <c:choose>
                        <c:when test="${commentCount <= 0}">
                            <div class="commend_star starNum5"><span>5</span> / 5分</div>
                            <div class="commend_count">（<span></span>暂无评论）</div>
                        </c:when>
                        <c:otherwise>
                            <div class="commend_star starNum<fmt:formatNumber type="number" value="${(hotel.score / 100) * 5}" maxFractionDigits="0"/>">
                                <span><fmt:formatNumber type="number" value="${(hotel.score / 100) * 5}" maxFractionDigits="1"/></span> / 5分</div>
                            <div class="commend_count">（<span>${commentCount}</span>条评论）</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="commendBox" id="pro_commentBox"></div>
                <div class="paging m-pager"></div>
            </div>
            <div class="traffic">
                <h3 class="clearfix"><i class="l_lo"></i><span>周边交通</span></h3>
                <div class="trafficMap"><div id="bot_map" style="height: 400px;border:1px solid #c9c9c9;;"></div></div>
            </div>
        </div>
        <div class="listRight">
            <div class="around_head">
                <span>周边景点</span>
            </div>
            <div class="aroundList"><ul id="nearby_scenic_content"></ul></div>
        </div>
    </div>
    <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/scenic/scenicDetail.js"></script>
<script type="text/javascript" src="/js/public/comment.data.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script type="text/html" id="ticket_list_item">
    <li class="clearfix">
        <div class="intro">
            <span class="tp">[{{formatType}}]</span>{{name}}<i></i>
        </div>
        <div class="pri">
            <span class="pri_rmb">¥</span><span class="pri_num">{{minDiscountPrice}}</span>
            <span class="yuding" data-price-id="{{id}}">立即预订</span>
        </div>
        <div class="ticketMessage clearfix">
            {{each addInfoList as addInfo}}
                <label class="mes_title">{{addInfo.subTitle}}：</label>
                <span>{{each addInfo.descDetails as detail}}<p>{{detail}}</p>{{/each}}</span><br>
            {{/each}}
        </div>
    </li>
</script>
<script type="text/html" id="nearby_scenic_item">
    <li class="clearfix">
        <div class="picture"><a target="_blank" href="/scenic_detail_{{id}}.html"><img src="{{cover | imageResize:82,86}}"></a></div>
        <div class="distance"><a target="_blank" href="/scenic_detail_{{id}}.html"><p class="name">{{name}}</p></a>
            <%--<p class="p_distance">距离该酒店<span class="dis_num">136.5</span>米</p>--%>
        </div>
        {{if price > 0}}
        <div class="around_price"><span class="rmb">¥</span><span class="num">{{price}}</span><span class="qi">起</span></div>
        {{else}}
        <div class="around_price" style="display: none;"><span class="rmb">¥</span><span class="num">{{price}}</span><span class="qi">起</span></div>
        {{/if}}
    </li>
</script>
<script type="text/html" id="pro_comment_item">
    <div class="oneCommend clearfix">
        <div class="face">
            <div class="userface">
                <img src="{{avatar | imageResize:74,74}}">
            </div>
            <p>{{nickName}}</p>
        </div>
        <div class="thecommend">
            <div class="com_time sta_lev_{{score}}">发表时间：{{createTime}}</div>
            <div class="com_theWord">{{content}}</div>
        </div>
    </div>
</script>
<script type="text/html" id="pro_reply_comment_item">
    <div class="busiReply">
        <p class="replylineone"><span>商家回复：</span>{{createTime}}</p>
        <p class="replylineone replylinetwo">{{content}}</p>
    </div>
</script>
</html>