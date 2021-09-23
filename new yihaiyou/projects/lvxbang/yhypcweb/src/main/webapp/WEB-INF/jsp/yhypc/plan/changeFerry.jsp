<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/ferry/ferry-list.css">
    <title>DIY更换船票</title>
</head>
<body class="DIY">
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp"%>
<!--内容-->
<div class="wrap">
    <div class="container-box">
        <h3>您在这里：<a href="/yhypc/index/index.jhtml">首页</a>><span>轮渡船票</span></h3>
        <div class="search-wrap clearfix">
            <div class="search-group clearfix">
                <label>航线：</label>
                <input class="route-input" type="text" placeholder="邮轮中心-三丘田码头（热门航线）" readonly  id="routeInputBtn">
                <input type="hidden" id="flight-number" name="fligth-number" value="9F3A7072E74142D7A41D999318818922" form-data-flight-line-id="${flightLineId}">
                <ul class="route-datalist" id="routeDatalist" style="display: none;">
                    <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="三丘田码头" data-number="9F3A7072E74142D7A41D999318818922">邮轮中心-三丘田码头（热门航线）</li>
                    <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头" data-number="4A3BA22E8D6A4CD3B54DCD52A7E1E258">邮轮中心-内厝澳码头(普通)</li>
                    <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头" data-number="167B6542C83A4F94871B54DADCE049E9">邮轮中心-内厝澳码头（豪华）</li>
                    <li data-departPort="嵩鼓码头（嵩屿）" data-arrivePort="内厝澳码头" data-number="8ADC5A482FE04AE1A9B8613179005090">嵩鼓码头-内厝澳码头（海沧出发）</li>
                    <li data-departPort="厦门轮渡码头" data-arrivePort="三丘田码头" data-number="2BB589AD46C04BCE88B74AEBC387F1D3">厦门轮渡码头-三丘田码头（夜航）</li>
                </ul>
            </div>
        </div>
        <div class="table-wrap">
            <div class="mask-left"></div>
            <div class="mask-right"></div>
            <div class="line"></div>
            <table>
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>出发码头</th>
                        <th>抵达码头（鼓浪屿）</th>
                        <th>航班号</th>
                        <th>开行日期</th>
                        <th>票价</th>
                        <th>余票</th>
                        <th></th>
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
    </div>
</div>

<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

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
            <a class="gobuy" href="javascript:;" onclick="ChangeFerry.changeFerry('{{json}}')">更换船票</a>
        </td>
    </tr>
</script>

<script type="text/javascript" src="/lib/util/pager.js"></script>
<script src="/js/plan/changeFerry.js"></script>
</html>