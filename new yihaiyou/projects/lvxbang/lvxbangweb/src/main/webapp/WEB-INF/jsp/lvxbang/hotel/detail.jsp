<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Expires" CONTENT="0">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <jx:include fileAttr="${LVXBANG_HOTEL_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildHotelDetail" objs="${param.hotelId}" validDay="60"></jx:include>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Hotels_Detail"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl mt10" >
    <input type="hidden" id="hotelId" value="${hotelId}"/>

    <jx:include fileAttr="${LVXBANG_HOTEL_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildHotelDetail" objs="${param.hotelId}" validDay="60"></jx:include>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
    <script type="text/html" id="tpl-hotel-room">
        <ul class="cl">
            <li class="w1 roomDescription" >{{name}}
             <span style="display: none;border: 1px dashed #34bf82;float:right; background-color:#fff;width:300px;" class="posiA">{{roomDescription}}</span>
            </li>
            <li class="w2 breakfast {{if hasBreakfast}}hasbreakfast{{else}}nobreakfast{{/if}}"
                style="text-align: right;">{{if hasBreakfast}}含早{{else}}不含早{{/if}}
            </li>
            <li class="w3">
                <%--&nbsp;{{if canCancel}}可取消{{else}}不可取消{{/if}}--%>
                    <%--免费取消--%>
                {{if changeRule}}
                    {{changeRule}}
                    {{/if}}
                {{if !changeRule}}不可取消{{/if}}
            </li>
            <li class="w4 fs13" style="color:#666666;">&nbsp;¥<b class="Orange fs16">{{price}}</b></li>
            <li class="w5"><a href="javaScript:; "
                              url="${INDEX_PATH}/lvxbang/order/orderHotel.jhtml?priceId={{id}}&hotelId={{hotelId}}&ratePlanCode={{ratePlanCode}}&priceStartDate={{priceStartDate}}&priceEndDate={{priceEndDate}}"
                              class="o_line disT oval4" onclick="toOrderPage(this)">预订</a>
                        <span class="statusContent"
                              style="
                            font-size: 10px;
                            color: white;
                            background-color: {{color}};
                            border-radius: 3px;
                            padding: 1px;
                            vertical-align:-webkit-baseline-middle;
                        ">{{status}}</span>
                {{if status=='担保'}}
                    <span style="line-height:25px;display: none;border: 1px dashed #34bf82;float:right; background-color:#fff;width:300px;margin-left: 2%;padding-left: 15px;padding-right: 15px;text-align: left;"
                          class="posiA">
                        担保：房型太抢手了，酒店需要您提供的信用卡或支付宝担保预订。
                        什么叫“担保”？
                        我们会预先冻结或扣除相应担保金，实际入住后，担保金会在您离
                        店3-5个工作日解冻或退款。退还金额境内卡5个工作日内到账，境外
                        卡20个工作日内到账。
                    </span>
                {{/if}}
                {{if status != '担保'}}
                <span style="line-height:47px;display: none;border: 1px dashed #34bf82;float:right; background-color:#fff;width:120px;margin-left: 2%;padding-left: 15px;padding-right: 15px;text-align: left;"
                      class="posiA">
                    房费于酒店前台支付。
                </span>
                {{/if}}
            </li>
        </ul>
    </script>
    <script type="text/html" id="tpl-hotel-more">
        <p class="cl"></p>
        <p class="more textC posiA"><a href="javaScript:;">查看更多房型<i></i></a></p>
    </script>
    <script type="text/html" id="tpl-line">
        <div class="w1000 Hotels_ss" style="margin-top: 50px;">
            <div class="Hotels_lb cl">
                <div class="Hotels_lb_top">
                    <p class="w1">关联线路</p>

                    <p class="w2 posiR zaocan" style="text-align: right;">
                    </p>

                    <p class="w3"></p>

                    <p class="w4"></p>
                </div>
                <div class="Hotels_lb_cen" id="line_list">
                    {{each lineResponses as line}}
                    <ul class="cl">
                        <li class="w1 roomDescription"
                            style="text-overflow: ellipsis;white-space: nowrap;width: 570px;overflow: hidden;">&lt;{{line.name}}&gt;{{line.appendTitle}}
                        </li>
                        <li class="w4 fs13" style="color:#666666;">&nbsp;¥<b class="Orange fs16">{{line.price}}</b>起
                        </li>
                        <li class="w5"><a href="/line_detail_{{line.id}}.html" target="_blank"
                                          class="o_line disT oval4">预订</a>
                        </li>
                    </ul>
                    {{/each}}
                    <p class="more textC" style="display: none;"><a href="javaScript:;">查看更多线路<i></i></a></p>
                </div>
            </div>
        </div>
    </script>
    <!--comment-->
    <jsp:include page="/WEB-INF/jsp/lvxbang/comment/comment.jsp"></jsp:include>
</body>
</html>


<script src="/js/lib/Time/WdatePicker.js" type="text/javascript" ></script>
<script src="/js/lvxbang/detail.js" type="text/javascript" ></script>

<script src="/js/lvxbang/hotel/detail.js" type="text/javascript" ></script>

<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>
