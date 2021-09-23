<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" href="/css/ferry/ferry-list.css">
    <title>轮渡船票</title>
</head>
<body class="boatTicket">
<div class="hotelIndex">
    <%@include file="../../yhypc/public/nav_header.jsp" %>
    <!--内容-->
    <div class="wrap">
        <div class="container-box">
            <h3>您在这里：<a href="/yhypc/index/index.jhtml">首页</a>><span>轮渡船票</span></h3>
            <input type="hidden" id="status" value="${status}">
            <div class="search-wrap clearfix">
                <div class="search-group clearfix">
                    <label>航线：</label>
                    <input class="route-input" type="text" value="邮轮中心-三丘田码头（热门航线）" readonly id="routeInputBtn">
                    <input type="hidden" id="flight-number" name="fligth-number"
                           value="9F3A7072E74142D7A41D999318818922"
                           form-data-flight-line-id="<c:out escapeXml='true' value='${flightLineId}'/>">
                    <ul class="route-datalist" id="routeDatalist">
                        <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="三丘田码头"
                            data-number="9F3A7072E74142D7A41D999318818922">邮轮中心-三丘田码头（热门航线）
                        </li>
                        <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头"
                            data-number="4A3BA22E8D6A4CD3B54DCD52A7E1E258">邮轮中心-内厝澳码头(普通)
                        </li>
                        <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头"
                            data-number="167B6542C83A4F94871B54DADCE049E9">邮轮中心-内厝澳码头（豪华）
                        </li>
                        <li data-departPort="嵩鼓码头（嵩屿）" data-arrivePort="内厝澳码头"
                            data-number="8ADC5A482FE04AE1A9B8613179005090">嵩鼓码头-内厝澳码头（海沧出发）
                        </li>
                        <li data-departPort="厦门轮渡码头" data-arrivePort="三丘田码头"
                            data-number="2BB589AD46C04BCE88B74AEBC387F1D3">厦门轮渡码头-三丘田码头（夜航）
                        </li>
                    </ul>
                </div>
                <div class="search-group clearfix">
                    <label>出发日期：</label>
                    <input class="date-input" type="text" name="date" placeholder="2016-12-18"
                           value="<c:out escapeXml='true' value='${date}'/>"
                           onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})" readonly>
                    <input type="hidden" id="sel-date">
                </div>
                <div class="search-group">
                    <a class="search-btn"><i></i>搜索</a>
                </div>
            </div>
            <div class="date-wrap clearfix">
                <a class="date-btn date-prev" onclick="FerryList.subDateArr()"></a>
                <ul class="date-content clearfix">
                </ul>
                <a onclick="FerryList.addDateArr()" class="date-btn date-next"></a>
            </div>
            <div id="elseTicketTable">
                <table>
                    <thead>
                    <tr>
                        <th class="else-one">类型</th>
                        <th class="else-two">名称</th>
                        <th class="else-three"></th>
                        <th class="else-four">票价</th>
                        <th class="else-five"></th>
                        <th class="else-six"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="else-one">其他票</td>
                        <td class="else-two">鼓浪屿景区-手机语音导游</td>
                        <td class="else-three">全天候均预定，7天内可随时退</td>
                        <td class="else-four">￥16</td>
                        <td class="else-five">
                            <a class="else-details" href="javascript:;">详情></a>
                            <div class="detail-pop">
                                <div class="detail-popWrap">
                                    <div class="pop-header">
                                        详情
                                        <i class="pop-close"></i>
                                    </div>
                                    <div class="pop-body">
                                        预定时间<br>
                                        全天候均可预定<br>
                                        费用包含<br>
                                        厦门鼓浪屿景区手机语音导游讲解票（不含船票）1张。<br>
                                        产品亮点：<br>
                                        111<br>
                                        11<br>
                                        1<br>
                                        111<br>
                                        1111<br>
                                        使用方法：<br>
                                        无需取票<br>
                                        请妥善保管<br>
                                        退改规则：<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="else-six"><a href="/yhypc/ferry/glyfillOrder.jhtml?productId=3819">立即预订</a></td>
                    </tr>
                    <tr>
                        <td class="else-one">其他票</td>
                        <td class="else-two">胡里山炮台景区-手机语音导游</td>
                        <td class="else-three">全天候均预定，7天内可随时退</td>
                        <td class="else-four">￥2</td>
                        <td class="else-five">
                            <a class="else-details" href="javascript:;">详情></a>
                            <div class="detail-pop">
                                <div class="detail-popWrap">
                                    <div class="pop-header">
                                        详情
                                        <i class="pop-close"></i>
                                    </div>
                                    <div class="pop-body">
                                        22222预定时间<br>
                                        全天候均可预定<br>
                                        费用包含<br>
                                        厦门鼓浪屿景区手机语音导游讲解票（不含船票）1张。<br>
                                        产品亮点：<br>
                                        111<br>
                                        11<br>
                                        1<br>
                                        111<br>
                                        1111<br>
                                        使用方法：<br>
                                        无需取票<br>
                                        请妥善保管<br>
                                        退改规则：<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="else-six"><a href="/yhypc/ferry/hlsfillOrder.jhtml?productId=3820">立即预订</a></td>
                    </tr>
                    <tr>
                        <td class="else-one">其他票</td>
                        <td class="else-two">嘉庚公园景区-手机语音导游</td>
                        <td class="else-three">全天候均预定，7天内可随时退</td>
                        <td class="else-four">￥2</td>
                        <td class="else-five">
                            <a class="else-details" href="javascript:;">详情></a>
                            <div class="detail-pop">
                                <div class="detail-popWrap">
                                    <div class="pop-header">
                                        详情
                                        <i class="pop-close"></i>
                                    </div>
                                    <div class="pop-body">
                                        22222预定时间<br>
                                        全天候均可预定<br>
                                        费用包含<br>
                                        厦门鼓浪屿景区手机语音导游讲解票（不含船票）1张。<br>
                                        产品亮点：<br>
                                        111<br>
                                        11<br>
                                        1<br>
                                        111<br>
                                        1111<br>
                                        使用方法：<br>
                                        无需取票<br>
                                        请妥善保管<br>
                                        退改规则：<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                        1.<br>
                                        2.<br>
                                        3.<br>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="else-six"><a href="/yhypc/ferry/jgfillOrder.jhtml?productId=3821">立即预订</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="table-wrap">
                <div class="mask-left"></div>
                <div class="mask-right"></div>
                <div class="line"></div>
                <table>
                    <thead>
                    <tr>
                        <th class="one">序号</th>
                        <th class="two">出发码头</th>
                        <th class="three">抵达码头（鼓浪屿）</th>
                        <th class="four">航班号</th>
                        <th class="five">开航日期</th>
                        <th class="six">票价</th>
                        <th class="seven">余票</th>
                        <th class="eight"></th>
                    </tr>
                    </thead>
                    <tbody class="tablelist">
                    </tbody>
                </table>
            </div>
            <div class="none-data">
                <div class="nonedata-img"></div>
                <h2 class="nonedata-tips">
                    很抱歉，暂时没有数据！
                </h2>
            </div>


            <%--<ul class="paging">
                <li><a href="#" class="paging-left">&nbsp;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#" class="active">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#" class="paging-right">&nbsp;</a></li>
                <li>
                    到<input type="text" class="paging-num" value="1">页
                    <button type="submit" class="paging-jump">确定</button>
                </li>
            </ul>--%>
        </div>
    </div>
    <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>

<script type="text/html" id="tpl-ferry-list-item">

    <tr>
        <td>{{index}}</td>
        <td>{{departProt}}</td>
        <td>{{arrivePort}}</td>
        <td>{{number}}</td>
        <td>{{departTime}}</td>
        <td class="price"><sub>￥</sub>{{price}}</td>
        <td>{{freeCount}}</td>
        <td>
            <form id="ferry-tr-{{index}}" action="/yhypc/ferry/fillOrder.jhtml" method="post">
                <input type="hidden" name="ferryInfo.dailyFlightGid" value="{{dailyFlightGid}}">
                <input type="hidden" name="ferryInfo.number" value="{{number}}">
                <input type="hidden" name="ferryInfo.departTime" value="{{departTime}}">
                <input type="hidden" name="ferryInfo.price" value="{{price}}">
                <input type="hidden" name="ferryInfo.freeCount" value="{{freeCount}}">
                <input type="hidden" name="ferryInfo.departProt" value="{{departProt}}">
                <input type="hidden" name="ferryInfo.arrivePort" value="{{arrivePort}}">
                <input type="hidden" name="ferryInfo.flightLineName" value="{{flightLineName}}">
                {{each ticketLst.Ticket as ticket i}}
                <input type="hidden" name="ferryInfo.ticketVoList[{{i}}].id" value="{{ticket.id}}">
                <input type="hidden" name="ferryInfo.ticketVoList[{{i}}].price" value="{{ticket.price}}">
                <input type="hidden" name="ferryInfo.ticketVoList[{{i}}].name" value="{{ticket.name}}">
                <input type="hidden" name="ferryInfo.ticketVoList[{{i}}].number" value="{{ticket.number}}">
                {{/each}}
                <a class="gobuy" href="javascript:void(0);" onclick="FerryList.nextOrder('ferry-tr-{{index}}')">立即预订</a>
            </form>
        </td>
    </tr>
</script>

<script type="text/javascript" src="/lib/util/pager.js"></script>
<script src="/js/ferry/ferry_list.js"></script>
</html>