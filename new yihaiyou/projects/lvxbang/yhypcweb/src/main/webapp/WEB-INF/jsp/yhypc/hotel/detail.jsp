<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/hotel/hotelDetail.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>${hotel.name}-一海游酒店民宿详情</title>
</head>
<body class="hotel">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div class="detailTop">
			<div class="rangestate">您在这里：
				<span class="history">&nbsp;<a href="/yhypc/index/index.jhtml">首页</a> &gt; <a href="/yhypc/hotel/index.jhtml">酒店预订</a> &gt; <a href="/yhypc/hotel/list.jhtml">厦门酒店</a> &gt; </span>${hotel.name}
			</div>
			<div class="hotelbox clearfix">
                <input type="hidden" value="${hotel.id}" id="hotelId">
                <input type="hidden" value="${hotel.id}" id="productId">
                <input type="hidden" value="${hotel.proType}" id="proType">
                <input type="hidden" value="${hotel.extend.longitude}" id="lng">
                <input type="hidden" value="${hotel.extend.latitude}" id="lat">
				<div class="pictrue clearfix">
					<div class="pic_max">
						<%--<div class="pre turn"><div class="premid"></div></div>--%>
						<%--<div class="next turn"><div class="premid"></div></div>--%>
						<div class="image">
                            <c:if test="${hotel.productimages.size() <= 0}"><img src="/image/default-lg.png"></c:if>
                            <c:forEach items="${hotel.productimages}" var="proImage" begin="0" end="0">
                                <c:choose>
                                    <c:when test="${fn:startsWith(proImage.path, 'http://')}"><img src="${proImage.path}"></c:when>
                                    <c:otherwise><img src="${QINIU_BUCKET_URL}${proImage.path}?imageView2/2/w/540/h/412"></c:otherwise>
                                </c:choose>
                            </c:forEach>
						</div>
					</div>
					<div class="pic_min">
                        <c:choose>
                            <c:when test="${hotel.productimages.size() <= 0}">
                                <div class="pic_min_top"><img src="/image/default-xm.png"></div>
                                <div class="pic_min_top"><img src="/image/default-xm.png"></div>
                                <div class="takeall">
                                    <div class="count"><p style="line-height: 38px;">暂无图片</p></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${hotel.productimages}" var="proImage" begin="1" end="2">
                                    <c:choose>
                                        <c:when test="${fn:startsWith(proImage.path, 'http://')}"><div class="pic_min_top"><img src="${proImage.path}"></div></c:when>
                                        <c:otherwise><div class="pic_min_top"><img src="${QINIU_BUCKET_URL}${proImage.path}?imageView2/2/w/212/h/163"></div></c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <div class="takeall">
                                    <div class="count"><p>查看所有</p><p>${hotel.productimages.size()} 张照片</p></div>
                                </div>
                            </c:otherwise>
                        </c:choose>
					</div>
				</div>
				<div class="describ">
					<div class="address">
						<p class="name">${hotel.name}<span class="level_x_star_${hotel.star}"></span></p>
                        <c:forEach items="${hotel.hotelAreas}" var="area"><p class="gps">【${area.areaName}】</p></c:forEach>
						<p class="de_address">地址：${hotel.extend.address}</p>
					</div>
					<div class="commend">
						<div class="score">
                            <c:choose>
                                <c:when test="${commentCount <= 0}">
                                    <span class="score_gold"><span class="now">5</span> / 5分</span>
                                    <span class="score_com">（<span class="num"></span>暂无评论）</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="score_gold"><span class="now"><fmt:formatNumber type="number" value="${(hotel.score / 100) * 5}" maxFractionDigits="1"/></span> / 5分</span>
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
                                    <p class="word" id="fst_comment"></p>
                                </c:otherwise>
                            </c:choose>
							<p class="sub">.</p>
						</div>
					</div>
                    <div class="price_book">
                        <c:choose>
                            <c:when test="${hotel.minPrice != null}">
                                <span class="rmb">¥</span><span class="num">${hotel.minPrice}</span><span class="qi">起</span>
                                <div class="tackdetail"><a>立即预订</a></div>
                            </c:when>
                            <c:otherwise><div class="no-price">暂无价格</div></c:otherwise>
                        </c:choose>
                    </div>
				</div>
			</div>
		</div>
		<div class="detailList clearfix">
			<div class="listLeft">
				<div class="list_head">
					<span class="li_he_active room-type">房型</span><span class="hotel-info">酒店信息</span><span class="hotel_comment">评价(${commentCount})</span><span class="hotel-geo">交通</span>
				</div>
				<div class="bookDate clearfix">
					<div class="book_star brr1">
						<span class="book_state">入住 |</span><input class="book_state_ip" id="startDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, onpicked:HotelDetail.onFoucsEndTime(), minDate:'%y-%M-{%d}'})"><span class="book_btn" id="start_date_btn"></span>
					</div>
					<div class="book_star">
						<span class="book_state">离店 |</span><input class="book_state_ip" id="endDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})">
					</div>
					<%--<span class="changeDate" id="change_date_btn">修改日期</span>--%>
				</div>
				<div class="thisHotel" id="booking">
					<ul id="hotel_price_content">

					</ul>
					<%--<div class="allproduct"><span>展开全部房型（xx）</span></div>--%>
				</div>
				<div class="hotelMessage">
					<h3 class="clearfix"><i class="l_lo"></i><span>酒店信息</span></h3>
					<div class="briefing">
						<h4>酒店简介</h4>
						<div class="contain">
							<c:choose>
                                <c:when test="${hotel.extend.description != null}">${hotel.extend.description}</c:when>
                                <c:otherwise>${hotel.shortDesc}</c:otherwise>
                            </c:choose>
						</div>
						<h4>设施服务</h4>
                        <div class="ser_contain" id="serve">
                            <c:if test="${hotel.serviceNames == null || hotel.serviceNames == ''}"><img src="/image/no-serve.png" style="display:none"></c:if>
                            ${hotel.serviceNames}
                        </div>
						<h4>酒店政策</h4>
                        <div class="contain policy" id="policy">
                            <c:if test="${hotel.policy == null || hotel.policy == ''}"><img src="/image/no-policy.png" style="display:none"></c:if>
                            ${hotel.policy}
                        </div>
					</div>
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
					<div class="trafficMap"><div id="bot_map" style="height: 400px;"></div></div>
				</div>
			</div>
			<div class="listRight">
				<div class="around_head"><span>周边住宿</span></div>
				<div class="aroundList"><ul id="nearby_hotel_content"></ul></div>
			</div>
		</div>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
    <div class="popup_shadow opcity9"></div>
    <div class="out_picbox">
        <span class="closeArea"></span>
        <div class="com_pictrue clearfix">
            <div class="pic_max">
                <c:forEach items="${hotel.productimages}" var="proImage">
                    <c:choose>
                        <c:when test="${fn:startsWith(proImage.path, 'http://')}"><img src="${proImage.path}"></c:when>
                        <c:otherwise><img src="${QINIU_BUCKET_URL}${proImage.path}?imageView2/2/w/670/h/366"></c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="pic_min">
                <label class="t_left"><</label>
                <ul class="clearfix">
                    <c:forEach items="${hotel.productimages}" var="proImage">
                        <c:choose>
                            <c:when test="${fn:startsWith(proImage.path, 'http://')}"><li><img src="${proImage.path}"></li></c:when>
                            <c:otherwise><li><img src="${QINIU_BUCKET_URL}${proImage.path}?imageView2/2/w/112/h/62"></li></c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <label class="t_right">></label>
            </div>
        </div>
    </div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/hotel/hotelDetail.js"></script>
<script type="text/javascript" src="/js/hotel/hotel_detail.js"></script>
<script type="text/javascript" src="/js/public/comment.data.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script type="text/html" id="hotel_price_item">
    <li class="clearfix">
        <div class="this_div this_pic"><img src="{{cover | imageResize:80,80}}"></div>
        <div class="this_div this_detail">
            <p class="product">{{roomName}}</p>
            <p class="clude">
                {{#roomDescription}}</br><%--<span class="apart">|</span> --%>最多可住{{capacity}}人
                <span class="apart">|</span> {{if breakfast == true}}含早{{else}}不含早{{/if}}
            </p>
        </div>
        <div class="this_div room_num">
            <p class="room_numtitle">房间数量</p>
            <p class="room_num_opt"><span class="sub disa">－</span><span data-price-id="{{id}}" class="num">1</span><span class="add ena">＋</span></p>
        </div>
        <div class="room_state">
            <span class="paytype">{{statusStr}}</span>
            <span class="at_word yufu">为了保障您的预订，需要您预先在网上支付房费，到店后直接办理入住~</span>
            <span class="at_word daofu">到店支付房费</span>
            <span class="at_word danbao"><p><span>担保</span>：房型太抢手了，酒店需要您提供信用卡，担保预订。</p><p>什么叫“<span>担保</span>”？</p><p>我们会预先冻结或扣除相应担保金，实际入住后，担保金会在3-5个工作日内解冻或退款。退还金额境内卡5个工作日内到账，境外卡20个工作日内到账。</p></span>
        </div>
        <div class="this_div this_book">
            <span class="rmb">¥</span><span class="num">{{price}}</span><span class="qi">起</span>
            <div class="bookThis" data-price-id="{{id}}">立即预订</div>
        </div>
    </li>
</script>
<script type="text/html" id="nearby_hotel_item">
    <li class="clearfix">
        <div class="picture"><a target="_blank" href="/hotel_detail_{{id}}.html"><img src="{{cover | imageResize:82,86}}"></a></div>
        <div class="distance"><a target="_blank" href="/hotel_detail_{{id}}.html"><p class="name">{{name}}</p></a>
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