<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-12,0012
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="numFmt" uri="/NumToCNFmtTag" %>
</!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" type="text/css" href="/css/plan/planDetail.css">
  <title>${planVo.name}-DIY行程详情</title>
</head>
<body class="DIY">
<%@include file="../../yhypc/public/nav_header.jsp" %>
<div class="detailTop">
  <div class="outrangestate">
      <div class="outrangestatein">
        <div class="rangestate">您在这里：<span class="history" >首页 </span> >行程详情</div>
        <div class="finalbox clearfix">
          <div class="suggestman">
            <span class="face">
                <c:choose>
                    <c:when test="${fn:startsWith(user.head, 'http://')}">
                        <img src="${user.head}">
                    </c:when>
                    <c:otherwise>
                        <img src="${QINIU_BUCKET_URL}${user.head}?imageView2/2/w/56/h/56">
                    </c:otherwise>
                </c:choose>
            </span>
            <p class="yours"><span id="planName">${planVo.name}</span></p>
            <p class="suggester">规划师：${user.nickName}</p>
          </div>
          <div class="fare">
            行程共 <span id="totalDay">${planVo.planDays}</span>天，
              <span id="totalScenic">${planVo.scenicNum}</span>个景点，
              大约花费 <span><label class="rmb">¥</label><label class="num" id="totalPrice"><fmt:formatNumber value="${planVo.price}" type="number" pattern="0"/></label></span>
          </div>
        </div>
     </div>
  </div>
</div>
<div class="marchdetail clearfix">
  <span class="t_title">行程详情</span>
  <div class="march_left" id="planDayList">
      <c:forEach items="${planVo.days}" var="day">
          <div class="aday">
              <div class="final_aday">第<numFmt:NumToCNFmt numStr="${day.day}" />天
                  游玩 <span>${day.playTime}</span>个小时，<span>${day.scenics.size()}</span>个景点。
                  大约花费 <span> <label class="rmb">¥</label><label class="num"><fmt:formatNumber value="${day.price}" type="number" pattern="0"/></label></span>
              </div>
              <c:forEach items="${day.scenics}" var="scenic" varStatus="s_status">
                  <div class="boat_hotel scenic">
                      <div class="scen_name"><span><fmt:formatNumber value="${s_status.index + 1}" type="number" pattern="00"/></span>${scenic.name}</div>
                      <div class="pic"><img src="${QINIU_BUCKET_URL}${scenic.cover}"></div>
                      <div class="mess">
                          <p><span class="lef clock">参考用时：</span><span class="ad_time">建议<span><fmt:formatNumber value="${scenic.adviceMinute / 60}" type="number" pattern="0"/></span>小时</span></p>
                          <p><span class="lef line">地址：</span><span class="time">${scenic.address}</span></p>
                          <p><span class="lef price">价格：</span><span class="pri"><label class="rmb">¥</label><label class="num"><fmt:formatNumber value="${scenic.price}" type="number" pattern="0"/></label></span></p>
                          <div class="clearfix">
                              <span class="lef intro">简介：</span>
                              <span class="introbox">${scenic.shortComment}</span>
                          </div>
                      </div>
                  </div>
              </c:forEach>
          </div>
      </c:forEach>
  </div>
  <div class="march_right">
    <div class="map"><img src="/image/map.png"></div>
    <p class="line_title">行程概览</p>
    <div class="dayline" id="simplePlanList">
        <ul>
            <c:forEach items="${planVo.days}" var="day">
                <li class="daynum">第<numFmt:NumToCNFmt numStr="${day.day}" />天<i></i></li>
                <li class="sec scen">景点<i></i>
                    <c:forEach items="${day.scenics}" var="scenic">
                        <p>${scenic.name}</p>
                    </c:forEach>
                </li>
            </c:forEach>
        </ul>
    </div>
  </div>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp" %>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/plan/myPlanDetail.js"></script>
</html>