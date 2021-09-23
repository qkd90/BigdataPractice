<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2015/12/28
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <jx:include fileAttr="${LVXBANG_SCENIC_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildOneScenic" objs="${param.scenicId}" validDay="60"></jx:include>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/detail.css" rel="stylesheet" type="text/css">
</head>

<body myname="mall" class="Attractions_Detail">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<jx:include fileAttr="${LVXBANG_SCENIC_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildOneScenic" objs="${param.scenicId}" validDay="60"></jx:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>

<!--comment-->
<jsp:include page="/WEB-INF/jsp/lvxbang/comment/comment.jsp"></jsp:include>

</body>
</html>
<script type="text/html" id="tpl-ticket-list-item">
  <ul class="cl">
    <li class="pt20 w1">{{type}}</li>
    <li class="Attractions_dl">
      {{each priceList as price i}}
        <dl >
          <dd class="w2">
            <p class="cl">
              <span class="green">{{price.name}}</span>
            </p>
            {{if price.addInfoList.length > 0}}
              <div class="posiA tishi3">
                {{each price.addInfoList as addInfo j}}
                  <b>{{addInfo.subTitle}}：</b>
                  {{each addInfo.descDetails as detail k}}
                    {{#detail}}<br>
                  {{/each}}
                {{/each}}
              </div>
            {{/if}}
          </dd>
          <dd class="w3">{{if price.formatBooking != null && price.formatBooking.length > 0}}{{price.formatBooking}}{{else}}无需提前{{/if}}</dd>
          <dd class="w4 fs14"><s>¥{{if price.maketPrice != null && price.maketPrice > 0}}{{price.maketPrice}}{{else}}-{{/if}}</s></dd>
          <dd class="w5 fs14 Orange b">¥{{price.discountPrice}}</dd>
          <dd class="w6">
            <a href="javaScript:;"
               url="/lvxbang/order/orderTicket.jhtml?ticketId={{price.ticketId}}&ticketPriceId={{price.id}}"
               class="o_line disT oval4" onclick="toOrderPage(this);">预订</a>
          </dd>
        </dl>
      {{/each}}
      <p class="cl"></p>
    </li>
    <li class="cl disB"></li>
  </ul>
</script>
<script type="text/html" id="tpl-line">
  <ul class="cl">
    <li class="pt20 w1">关联线路</li>
    <li class="Attractions_dl">
      {{each lineList as line}}
      <dl>
        <dd class="w2" style="text-overflow: ellipsis; white-space: nowrap; width: 673px; overflow: hidden;">
          <p class="cl">
            <span class="green">&lt;{{line.name}}&gt;{{line.appendTitle}}</span>
          </p>
        </dd>
        <dd class="w5 fs14">&nbsp;¥<b class="Orange">{{line.price}}</b>起</dd>
        <dd class="w6">
          <a href="/line_detail_{{line.id}}.html" target="_blank"
             class="o_line disT oval4">预订</a>
        </dd>
      </dl>
      {{/each}}
      <p class="cl"></p>
    </li>
    <li class="cl disB"></li>
  </ul>
</script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
<script src="/js/lvxbang/scenic/detail.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="application/javascript"></script>