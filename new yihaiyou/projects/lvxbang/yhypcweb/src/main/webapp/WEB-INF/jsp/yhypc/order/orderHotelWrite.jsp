<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-03,0003
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/order/hotelOrder.css">
    <title>酒店民宿填写订单</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
    <%@include file="../../yhypc/public/order_header.jsp" %>
    <input type="hidden" id="hotelPriceId" value="${hotelPrice.id}">
    <input type="hidden" id="hotelPriceName" value="${hotelPrice.roomName}">
    <input type="hidden" id="startDate" value="${startDate}">
    <input type="hidden" id="endDate" value="${endDate}">
    <input type="hidden" id="hotelName" value="${hotel.name}">
    <input type="hidden" id="hotelId" value="${hotel.id}">
    <input type="hidden" id="num" value="${num}">
    <input type="hidden" id="hotelCover" value="${hotel.cover}">

    <div class="body_order">
        <div class="progress">
            <span class="active">填写订单</span><span>在线支付</span><span>订单完成</span>
        </div>
        <div class="orderMessage clearfix ">
            <div class="messageLeft">
                <div class="messBox roomMess clearfix">
                    <div class="messTitle">预订信息</div>
                    <div class="messContain">
                        <p>
                            <span class="data_in">入住日期</span>
                            <span class="date_during startDateStr"></span>
                            <span class="date_during date_bold" id="startWeekday"></span>
                            <span class="data_in"> 到 </span>
                            <span class="date_during endDateStr"></span>
                            <span class="date_during date_bold" id="endWeekday"></span>
                            <span class="data_in">共<span id="day"></span> 晚</span>
								<a class="modify">修改日期</a>
                        </p>

                        <p>
                            <span class="data_in">房间数量</span>
								<span class="dataNum">
									<span class="sub">－</span><span class="r_num">${num}</span><span class="add">＋</span>
								</span>
                            <span class="data_in">间</span>
                        </p>

                        <p>
                            <span class="data_in">房费总计</span>
								<span class="price">
									<span class="rmb">¥</span><span class="num"></span><span class="qi">元</span>
								</span>
                        </p>
                    </div>
                </div>
                <div class="messBox hostMess clearfix">
                    <div class="messTitle">入住信息</div>
                    <div class="messContain">
                        <div id="roomPeople"></div>
                        <div class="addtourbtn"></div>
                        <div class="p" style="position:relative">
                            <span class="host_lefttitle">联系电话<label>*</label></span>
                            <%--<span class="phoNum">中国大陆（+86）<span class="down"></span></span>--%>
                            <input class="host_smessage" type="text" placeholder="联系电话" id="contactTelephone">

                            <%--<div class="phoneNum">--%>
                                <%--<ul>--%>
                                    <%--<li>12345678</li>--%>
                                    <%--<li>45646545</li>--%>
                                    <%--<li>87784545</li>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                        </div>
                        <div class="p" style="margin-top:5px">
                            <span class="host_lefttitle">Email</span>
                            <input class="host_email" type="text">
                        </div>
                        <c:if test="${hotelPrice.status != 'GUARANTEE'}">
                        <div class="p" style="margin-top:5px">
                            <span class="host_lefttitle yzs">安全验证<label>*</label></span>
                            <input class="host_room yzs ml" type="text" id="checkCode">
                            <img src="/image/checkNum.jsp" id="imgyz_ADMIN" class="save_code"/>
                            <a class="save_change" href="javascript:;" onclick="imgClick('imgyz_ADMIN');">换一张</a>
                        </div>
                        </c:if>
                    </div>
                </div>
                <div class="handin clearfix">
                    <div class="return" onclick="history.back()">
                        &lt;重新选择房型
                    </div>
                    <div class="pay">
                        <div class="payNum">
                            <p class="p1">
                                <span class="yuan">需支付：</span>
                                <span class="rmb">¥</span><span class="num"></span>
                            </p>

                            <p class="p2">我接受《酒店预订须知》</p>
                        </div>
                        <c:choose>
                            <c:when test="${hotelPrice.status == 'GUARANTEE'}">
                                <div class="guaranteeBtn">支付担保金</div>
                            </c:when>
                            <c:otherwise>
                                <div class="payBtn" data-source="${hotel.source}">提交订单</div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
            <div class="messageRight">
                <div class="mess_right_top">
                    <div class="pictrue"><img src="${hotel.cover}"></div>
                    <div class="address">
                        <p class="h_name">${hotel.name}</p>

                        <p class="h_room">${hotelPrice.roomName}</p>

                        <p class="h_address">地址：${hotel.extend.address}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="shadow"></div>
    <div class="dateChange">
        <h3>修改日期 <span class="closeBtn"></span></h3>

        <div class="date_during date_left">
            <span class="state">入住时间</span>
            <input type="text" class="time" id="startDateSel" onclick="WdatePicker({doubleCalendar:true, readOnly:true, onpicked:OrderHotelWrite.onFoucsEndTime(), minDate:'%y-%M-{%d}'})">
            <span class="change" onclick="WdatePicker({el:'startDateSel', doubleCalendar:true, readOnly:true, onpicked:OrderHotelWrite.onFoucsEndTime(), minDate:'%y-%M-{%d}'})"></span>
        </div>
        <span class="zhi">至</span>

        <div class="date_during date_right">
            <span class="state">离店时间</span>
            <input type="text" class="time" id="endDateSel" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDateSel\',{d:1})}'})">
            <span class="change" onclick="WdatePicker({el:'endDateSel', doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDateSel\',{d:1})}'})"></span>
        </div>
        <span class="change_btn">确认</span>
    </div>

    <div class="tourbox">
        <div class="title_close">选择游客<span id="buyshut"></span></div>
        <div class="edited">
        </div>
        <span class="savebtn"><a onclick="OrderHotelWrite.selectTourist()">确定</a></span>
        <div class="title_close">添加游客</div>
        <div class="addtour">
            <ul class="clearfix">
                <li>
                    <label>姓名</label><input type="text" id="addTourName">
                </li>
                <li>
                    <label>手机号</label><input type="text" id="addTourTel">
                </li>
                <li class="card">
                    <label>证件类型</label><input type="text" value="身份证" data-type="IDCARD" readonly id="addTourIdType">
                    <div class="cardlist">
                        <p data-type="IDCARD">身份证</p>
                        <p data-type="PASSPORT">护照</p>
                    </div>
                </li>
                <li>
                    <label>证件号</label><input type="text" id="addTourIdNo">
                </li>
            </ul>
        </div>
        <span class="savebtn" onclick="OrderHotelWrite.saveTourist()">保存</span>
    </div>
    <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/order/hotelOrder_write.js"></script>
<script type="text/html" id="room_people_item">
    <div class="p">
        <span class="host_lefttitle">房间{{index}}<label>*</label></span>
        <input class="host_room roomPeopleName" type="text" placeholder="选择入住人" readonly style="cursor: pointer;">
    </div>
</script>
<script type="text/html" id="tourist_list_item">
    <p data-id="{{id}}">游客{{index}}：{{name}} <span class="adult">{{peopleTypeStr}}</span> {{hiddenTel}}</p>
</script>
</html>