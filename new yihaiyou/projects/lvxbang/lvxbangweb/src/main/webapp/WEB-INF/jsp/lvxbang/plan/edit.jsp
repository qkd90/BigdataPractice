<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%--<meta http-equiv=“X-UA-Compatible” content=“chrome=1″/>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>旅行帮线路编辑页</title>
    <meta name="keywords" content="自由行，自主编辑，旅游必备"/>
    <meta name="description" content="自主设计行程，让旅途尽在掌中"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
    <link href="/css/plan.edit.css" rel="stylesheet" type="text/css">
    <link href="/css/plan.map.css" rel="stylesheet" type="text/css">

</head>
<body class="Travel_editor"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl">
    <div id="plan-booking-loading-cover" class=" cl disB textC">
        <img src="/images/loadingx.gif">
        <p class="mt20">
            正在根据您的线路推荐交通与酒店信息...
        </p>
    </div>
    <input type="hidden" value="${planId}" id="planId"/>
    <div class="Travel_editor_div w1000 cl">
        <p class="n_title">您在这里：&nbsp;<a href="${PLAN_PATH}">私人定制</a>&nbsp;&gt;&nbsp;线路编辑 </p>
        <div class="Travel_e_t">
            <span class="fl ff_yh">线路编辑</span>
            <a href="javascript:;" class="b fr oval4 but2 booking-plan" style="display: none;">快速选择交通和酒店</a>
            <a href="javascript:;" class="b fr oval4 but1 mr20 save-plan" style="display: none;">保存线路至个人中心</a>
        </div>

        <div class="Travel_e_c" style="overflow: hidden; min-height: 95px;">

        </div>

        <div class="Travel_e_b" id="added-scenic-panel" style="overflow: hidden; min-height: 95px;">
            <table style="width: 100%; text-align: center; vertical-align: middle">
                <tr>
                    <td class="title">
                        <span class="disB">添加的景点</span>
                        精准算法基于线路合理性
                    </td>
                    <td class="Travel_e_b_nr fl" style="margin-bottom: 30px;padding-left: 30px;">
                        <p class="Travel_e_b_nr_p">
                        </p>
                    </td>
                </tr>
            </table>
            <p class="cl"></p>
        </div>

        <div class="Travel_e_b checked" id="removed-scenic-panel" style="overflow: hidden; min-height: 95px;">
            <table style="width: 100%; text-align: center; vertical-align: middle">
                <tr>
                    <td class="title">
                        <span class="disB">删除的景点</span>
                        精准算法基于线路合理性
                    </td>
                    <td class="Travel_e_b_nr fl" style="margin-bottom: 30px;padding-left: 30px;">
                        <p class="Travel_e_b_nr_p">
                        </p>
                    </td>
                </tr>
            </table>
            <p class="cl"></p>
        </div>

        <div class="Travel_e_f" id="failed-scenic-panel">
            <p class="title"><img src="/images/tishix.png">
            <label></label></p>
            <p class="Travel_e_f_nr"></p>
        </div>

        <div id="edit-tip" style="height: 20px; margin-bottom: 10px; line-height: 20px; color: #ff6000; display: none; text-align: center; letter-spacing: 1pt; font-weight: bold;">
            <p>拖动景点，可调整顺序</p>
        </div>
        <div class="Travel_exercise_div_b">

        </div>

        <div class="Travel_exercise_div_bottom cl">
            <a href="javascript:;" class="b fl save-plan" style="display: none;">保存线路至个人中心</a>
            <a href="javascript:;" class="b fr booking-plan" style="display: none;"><i></i>快速选择交通和酒店</a>
            <p class="cl"></p>
        </div>
    </div>
</div>
<form action="${PLAN_PATH}/lvxbang/plan/booking.jhtml" method="post" id="booking-form">
    <input type="hidden" name="newOne" value="true"/>
    <input type="hidden" name="json" value="" id="booking-form-json"/>
</form>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<div class="map-container fr whole-map">
    <a href="#" class="close"><i></i></a>
    <div class="fl" style="width:0;height:0;" id="baidu-map-main"></div>
    <div class="hqxqdtx_fr fl">
        <p class="title fs16">线路地图</p>
        <p class="alltravel checked"><i></i>全部线路</p>
        <ul id="edit-map-day-panel">

        </ul>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/map.jsp"></jsp:include>
<script src="/js/lib/jquery.sortable.js" type="text/javascript"></script>
<script src="/js/lvxbang/plan/edit.js" type="text/javascript"></script>
<script type="text/html" id="tpl-plan-map-day">
    <li style="padding-left: 15px;">
        <div class="jieshao posiR">
            <i></i>
            <p class="name"><span>D{{day}}</span>{{cityName}}</p>
            <p class="nr">
                {{each trips as trip index}}{{if index>0}}--{{/if}}<span class="plan-map-scenic" data-id="{{trip.id}}">{{trip.name}}</span>{{/each}}
            </p>
        </div>
    </li>
</script>
<script type="text/html" id="tpl-optimized-city-panel">

    <table style="width: 100%; text-align: center; vertical-align: middle">
        <tr>
            <td style="width:192px;background-color:white;font-size: 16px;line-height: 24px;" id="allTravel">
                <a href="javaScript:;" style="color:#666">
                    <div id="cityNames">
                        <i class="Travel_e_c_i"></i>
                        {{each cityList as city index}}
                        {{if index>0}}、{{/if}}{{city.name}}
                        {{/each}}
                    </div>
                    <p><span id="total">{{total}}</span>日游</p>
                </a>
            </td>
            <td>
                <ul class="Travel_e_c_ul fl">
                    {{each cityList as city index}}
                    <li data-id="{{city.id}}">
                        <label class="name">{{city.name}}</label>
                        <div class="opera_num">
                            <a href="javascript:;" class="minus">-</a>
                            <p class="posiR"><input class="quantity" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" value="{{city.count}}" maxlength="3">天</p>
                            <a href="javascript:;" class="plus">+</a>
                        </div>
                    </li>
                    {{/each}}
                </ul>
            </td>
            <td><a href="javascript:;" data-url="${SCENIC_PATH}/scenic_list.html?planId={{planId}}&&cityIdStr=" class="Travel_e_b_nr_a b fr">添加更多景点&gt;</a></td>
        </tr>
    </table>
</script>
<script type="text/html" id="tpl-optimized-added-item">
    <a href="javascript:;" data-id="{{id}}"><i class="close"></i>{{name}}</a>
</script>
<script type="text/html" id="tpl-optimized-removed-item">
    <a href="javascript:;" data-id="{{id}}" class="auto_removed checked" data-staute="1"><i class="close"></i>{{name}}</a>
</script>
<script type="text/html" id="tpl-optimized-result-day">
    <div class="Travel_exe_d" data-city-name="{{city.name}}" data-city-id="{{city.id}}">
        <p class="fl Travel_exe_d_f" data-day="{{day}}">
            <a href="javaScript:;">
                <i></i>第<span>{{day}}</span>天<br>
            </a>
            <span>{{city.name}}</span>
            <i class="Travel_exe_d_f_up"></i>
            <i class="Travel_exe_d_f_down"></i>
        </p>
        <div class="fl Travel_exe_d_d">
            <ul>
                {{each tripList as trip index}}
                <li draggable="true"
                    lng="{{trip.lng}}"
                    lat="{{trip.lat}}"
                    tid="{{trip.id}}"
                    tname="{{trip.name}}"
                    taddress="{{trip.address}}"
                    tcomment="{{trip.shortIntro}}"
                    tcover="{{trip.cover}}"
                    tstar="{{trip.star}}"
                    >
                    <p class="img">
                        <img data-original="{{trip.cover}}?imageView2/1/w/180/h/180" >
                    </p>
                    <p class="name" data-url="${SCENIC_PATH}/scenic_detail_{{trip.id}}.html">{{trip.name}}</p>
                    <i></i>
                    <p class="close oval4">删除</p>
                </li>
                {{/each}}
            </ul>
        </div>
        <p class="cl"></p>
    </div></script>
<script type="text/html" id="tpl-optimized-result-trip">
    <li draggable="true"
        lng="{{lng}}"
        lat="{{lat}}"
        tid="{{id}}"
        tname="{{name}}"
        taddress="{{address}}"
        tcomment="{{shortIntro}}"
        tcover="{{cover}}"
        tstar="{{star}}"
            >
        <p class="img">
            <a href="javaScript:;"><img src="{{cover}}?imageView2/1/w/180/h/180" ></a>
        </p>
        <a href="${SCENIC_PATH}/scenic_detail_{{id}}.html" target="_blank">
            <p class="name">{{name}}</p>
        </a>
        <i></i>
        <a href="javaScript:;" class="close oval4">删除</a>
    </li>
</script>
<!-- #EndLibraryItem --></body>
</html>
