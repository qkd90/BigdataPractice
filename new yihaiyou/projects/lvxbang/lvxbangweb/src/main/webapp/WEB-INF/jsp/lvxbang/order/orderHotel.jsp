<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/6
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>预订酒店</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

</head>
<body class="fill_orders Fill_orders_jd" onload="pageReload();">
<jsp:useBean id="now" class="java.util.Date" scope="session"/>
<%--用来刷新用的隐藏域--%>
<input type="hidden" value="0" id="page_reload"/>
<!-- #BeginLibraryItem "/lbi/head.lbi" -->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <div class="free_exercise_div cl">
        <p class="n_title">
            <%--您在这里：&nbsp;<a href="${INDEX_PATH}/lvxbang/user/order.jhtml">订单</a>&nbsp;&gt;&nbsp;填写酒店订单 --%>
        </p>

        <input id="orderId" type="hidden" value="${order.id}"/>
        <input id="orderDetailId" type="hidden" value="${orderDetailId}"/>
        <input id="hotelId" type="hidden" value="${hotel.id}"/>
        <input id="price" type="hidden" value="${price}">
        <input id="totalSinglePrice" type="hidden" value="${price}"/>
        <input id="ratePlanCode" type="hidden" value="${hotelPrice.ratePlanCode}"/>
        <input id="days" type="hidden" value="1"/>
        <input id="priceIds" type="hidden" value="${hotelPrice.id}"/>
        <input id="source" type="hidden" value="${hotel.source}">
        <!--左侧-->
        <div class="free_e_fl fl">
            <!--标题-->
            <div class="fill_title">
                <p class="name ff_yh fs20">${hotel.name}</p>

                <p class="js cl"><b class="fs16">订单信息</b></p>
            </div>

            <div class="free_e_fl_c fill_div">
                <ul class="free_e_fl_c_ut b">
                    <li class="w1">&nbsp; </li>
                    <li class="w2">商品</li>
                    <li class="w3">日期</li>
                    <li class="w4">单价(元)</li>
                    <li class="w5">数量</li>
                    <li class="w6">小计(元)</li>
                </ul>
                <div class="free_e_fl_lm posiR">
                    <!--酒店-->
                    <div class="free_e_fl_lm_div">
                        <ul class="free_e_fl_lm_ul">
                            <li>
                                <div class="free_e_fl_lm_ul_div">
                                    <div class="w1 fl" style="width:33px">
                                        &nbsp;
                                    </div>
                                    <div class="w2 fl hc_name textC">
                                        ${hotel.name}
                                    </div>
                                    <div class="w3 fl" style="margin-left: 26px;">
                                        <div class="time">
                                            <p class="jd_time">入住</p>

                                            <div class="time_p"><i class="ico rili"></i><input type="text"
                                                                                          placeholder="" readonly="true"
                                                                                          value="${priceStartDate}" class="input"
                                                                                          onfocus="WdatePicker({onpicked:changeDate, startDate:'${priceStartDate}',alwaysUseStartDate:false ,minDate: '%y-%M-{%d}',onpicked:function(){$(this).change()}
                                                                                                  <%--maxDate:${fn:substring(hotelPrice.end, 0, 10)}--%>
                                                                                                  });"
                                                                                          id="startDate"
                                                                                          onchange="HotelOrder.changeDate();">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="w4 fl" style="width:33px;">&nbsp;</div>
                                    <div class="w5 fl">
                                        <div class="time3">
                                            <p class="jd_time">退房</p>

                                            <div class="time_p"><i class="ico rili"></i>
                                            <input type="text" placeholder="" readonly="true"  value="${priceEndDate}" class="input"
                                            onfocus="WdatePicker({onpicked:function(){$(this).change(),HotelOrder.changeDate(1)},startDate:'${priceEndDate}',alwaysUseStartDate:false, minDate:'#F{$dp.$D(\'startDate\',{d:1})}',maxDate:'#F{$dp.$D(\'startDate\',{d:30})}'})" id="leaveDate">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="w6 fl jd_fs">
                                        <input id="seatType" type="hidden" value="${hotelPrice.roomName}"/>
                                        <p style="margin-top:9px;">${hotelPrice.roomName}</p>
                                        <%--<c:if test="${hotelPrice.breakfast == false}">--%>
                                            <%--不含早餐--%>
                                        <%--</c:if>--%>
                                        <%--<c:if test="${hotelPrice.breakfast == true}">--%>
                                            <%--含早餐--%>
                                        <%--</c:if>--%>
                                    </div>
                                    <p class="w7 fl" id="singlePrice"><fmt:formatNumber type="number"
                                                                                        pattern="###.#">${price}</fmt:formatNumber></p>

                                    <div class="w8 fl" style="width: 118px;">
                                        <div class="opera_num">
                                            <a href="javascript:;" class="minus"
                                               onclick="HotelOrder.changePrice(-1)">-</a>

                                            <p class="posiR"><input class="quantity" type="text"
                                                                    onkeyup="value=value.replace(/[^\d]/g,'')" value="1"
                                                                    maxlength="3" id="passengerNum"
                                                                    onchange="HotelOrder.changePrice(0)"></p>
                                            <a href="javascript:;" class="plus"
                                               onclick="HotelOrder.changePrice(1)">+</a>
                                        </div>
                                    </div>
                                    <p class="w9 fl b" id="firstCost"><fmt:formatNumber type="number"
                                                                                        pattern="###.#">${price}</fmt:formatNumber></p>

                                    <p class="cl"></p>
                                </div>

                                <div class="fill_div_nr">
                                    <p class="title b fs14">常用出行人:</p>

                                    <div class="fill_name" id="contacts">
                                        <c:forEach items="${touristList}" var="tourist">
                                        <span class="oval4" style="margin-bottom:5px;">
                                            ${tourist.name}
                                            <input type="hidden" class="id" value="${tourist.id}"/>
                                            <input type="hidden" class="name" value="${tourist.name}"/>
                                            <input type="hidden" class="peopleType" value="${tourist.peopleType}"/>
                                            <input type="hidden" class="idType" value="${tourist.idType}"/>
                                            <input type="hidden" class="idNumber" value="${tourist.idNumber}"/>
                                            <input type="hidden" class="tel" value="${tourist.tel}"/>
                                        </span>
                                        </c:forEach>
                                        <p class="cl"></p>
                                    </div>
                                    <div id="touristList">
                                        <div class="nr cl touristClass">
                                            <input type="hidden" class="touristId"/>
                                            <b class="fl num disB fs16">1</b>

                                            <div class="fl fill_div_nr_fr">
                                                <div class="fill_w1 cl">
                                                    <div class="DebitCard_d2 fl oval4">
                                                        <input type="text" placeholder="姓名(请确保与证件保持一致)" value=""
                                                               class="input touristName">
                                                    </div>
                                                    <%--<div class="sfz fl posiR oval4">--%>
                                                        <%--<p class="name touristPeopleType">成人</p><i></i>--%>

                                                        <%--<p class="sfzp disn">--%>
                                                            <%--<a href="javaScript:;">成人</a>--%>
                                                            <%--<a href="javaScript:;">儿童</a>--%>
                                                        <%--</p>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="fill_w2 cl oval4">
                                                    <div class="sfz fl posiR">
                                                        <p class="name touristIdType">身份证</p><i></i>

                                                        <p class="sfzp disn">
                                                            <a href="javaScript:;">身份证</a>
                                                            <a href="javaScript:;">护照</a>
                                                        </p>
                                                    </div>
                                                    <div class="DebitCard_d2 fl">
                                                        <input type="text" placeholder="请输入您的证件号" value=""
                                                               class="input touristIdNum">
                                                    </div>
                                                </div>
                                                <div class="fill_w3 cl oval4">
                                                    <div class="DebitCard_d2 ">
                                                        <input type="text" placeholder="请输入手机号" value=""
                                                               class="input touristTel">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                    <p class="cl"></p>

                                </div>

                            </li>
                        </ul>
                        <p class="cl"></p>
                    </div>

                    <p class="cl"></p>
                </div>
            </div>


            <input type="hidden" id="hotelPriceStatus" value="${hotelPrice.status}"/>
            <div class="fill_top_div" <c:if test="${hotelPrice.status != 'GUARANTEE'}">style="display: none;"</c:if>>
                <div class="name"><b class="fs16">酒店担保信息</b>(<span class="red">*</span>为必填)</div>
                <div class="fill_top_div_js">
                    <p class="top"></p>

                    <div class="nr" style="padding:0px 0px 0px 0px;">
                        <ul style="margin-top: 20px;">
                            <li style="margin-left: 60px;margin-bottom: 10px;">
                                <p class="fl"><span class="Orange mr5">*</span>信用卡卡号</p>
                                <div class="oval4 fl " style="padding-top: 0px;"><input id="cardNum" type="text" style="margin-top:5px;" placeholder="请输入信用卡卡号" value="" class="input" />
                                </div>

                                <p class="fl" style="margin-left: 85px;width: 80px;"><span class="Orange mr5">*</span>持卡人姓名</p>
                                <div class="oval4 fl" style="padding-top: 0px;width:257px;"><input id="holderName" type="text" style="margin-top:5px;" placeholder="请输入持卡人姓名" value="" class="input" />
                                </div>
                            </li>
                            <li style="margin-left: 60px;margin-bottom: 10px;">
                                <p class="fl"><span class="Orange mr5">*</span>信用卡有效期至</p>
                                <%--<div class="oval4 fl" style="padding-top: 0px;"><input type="text" style="margin-top:5px;" placeholder="请输入联系人手机号" value="${order.mobile==null?user.telephone:order.mobile}" class="input" id="contactsTel">--%>
                                <%--</div>--%>
                                <div class="sfz fl posiR" style="width:70px;padding-top: 0px;">
                                    <p class="name " id="expirationYear">年</p>

                                    <i   style="width: 9px;
                                                height: 6px;
                                                background: url(/images/ico.png) 0 -582px no-repeat;
                                                display: block;
                                                position: absolute;
                                                top: 12px;
                                                right: 8px;"></i>

                                    <p class="sfzp disn xinyongka" style="position: absolute;
                                                                background: #fff;
                                                                top: 28px;
                                                                border: 1px solid #ccc;
                                                                border-top: 0px;
                                                                width: 100%;
                                                                left: -1px;
                                                                z-index: 9;">

                                        <c:forEach begin="0" end="9" var="y">
                                            <a href="javaScript:;" style="display: block;line-height: 30px;
                                                                        clear: both;
                                                                        color: #666;
                                                                        padding-left: 10px;">
                                                    ${sessionScope.now.year + 1900 + y}年
                                            </a>
                                        </c:forEach>

                                    </p>
                                </div>

                                <div class="sfz fl posiR" style="width:70px;padding-top: 0px;margin-left: 52px;">
                                    <p class="name " id="expirationMonth">月</p>

                                    <i   style="width: 9px;
                                                height: 6px;
                                                background: url(/images/ico.png) 0 -582px no-repeat;
                                                display: block;
                                                position: absolute;
                                                top: 12px;
                                                right: 8px;"></i>

                                    <p class="sfzp disn xinyongka" style="position: absolute;
                                                                background: #fff;
                                                                top: 28px;
                                                                border: 1px solid #ccc;
                                                                border-top: 0px;
                                                                width: 100%;
                                                                left: -1px;
                                                                z-index: 9;">
                                        <c:forEach begin="1" end="12" var="yue">
                                            <a href="javaScript:;" style="display: block;line-height: 30px;
                                                                        clear: both;
                                                                        color: #666;
                                                                        padding-left: 10px;">
                                                ${yue}月</a>
                                        </c:forEach>

                                    </p>
                                </div>
                                <p class="fl" style="margin-left: 85px;width:80px;"><span class="Orange mr5">*</span>证件</p>
                                <div class="sfz fl posiR" style="width:50px;padding-top: 0px;">
                                    <p class="name " id="creditCardIdType">身份证</p>

                                    <i   style="width: 9px;
                                                height: 6px;
                                                background: url(/images/ico.png) 0 -582px no-repeat;
                                                display: block;
                                                position: absolute;
                                                top: 12px;
                                                right: 8px;"></i>

                                    <p class="sfzp disn xinyongka" style="position: absolute;
                                                                background: #fff;
                                                                top: 28px;
                                                                border: 1px solid #ccc;
                                                                border-top: 0px;
                                                                width: 100%;
                                                                left: -1px;
                                                                z-index: 9;">
                                        <a href="javaScript:;" style="display: block;
                                                                        clear: both;
                                                                        color: #666;
                                                                        padding-left: 10px;">
                                            身份证</a>
                                        <a href="javaScript:;" style="display: block;
                                                                        clear: both;
                                                                        color: #666;
                                                                        padding-left: 10px;">
                                            护照</a>
                                    </p>
                                </div>
                                <div class="oval4 fl" style="padding-top: 0px;width:181px;"><input id="idNo" type="text" style="margin-top:5px;" placeholder="请输入联系人证件号" value="" class="input" />
                                </div>
                            </li>
                            <li style="margin-left: 60px;margin-bottom: 10px;">
                                <p class="fl"><span class="Orange mr5">*</span>信用卡验证码</p>
                                <div class="oval4 fl" style="padding-top: 0px;"><input id="cvv" type="text" style="margin-top:5px;" placeholder="卡背后末三位数" value="" class="input" />
                                </div>

                            </li>
                        </ul>


                    </div>

                </div>
            </div>

            <c:if test="${hotelPrice.status == 'GUARANTEE'}">
            <div class="fill_top_div">
                <div class="name"><b class="fs16">预订须知</b></div>
                <div class="fill_top_div_js" >
                    <p class="top"></p>

                    <div class="nr" style="padding: 25px 60px 25px;font-size: 13px;">

                        <span style="line-height: 20px;">1>房量紧张，订单提交后需在线支付<span style="color: #f52">¥${price}</span>担保金，如未预订成功，担保金全部原路退还;</span><br/>
                            <span style="line-height: 20px;">2>入住结账，艺龙向酒店确认后，担保金全额原路退还;</span><br/>
                            <span style="line-height: 20px;">3>订单一经预订成功不可变更/取消，如未入住酒店将扣除全部担保金作为违约金;</span><br/>
                            <span style="line-height: 20px;">4>退还金额境内卡5个工作日内到账，境外卡20个工作日内到账。</span>

                    </div>
                </div>
            </div>
            </c:if>

            <div class="fill_top_div">
                <div class="name"><b class="fs16">联系人信息</b>(<span class="red">*</span>为必填)</div>
                <div class="fill_top_div_js">
                    <p class="top"></p>

                    <div class="nr" style="padding: 30px 60px 0px;">
                        <ul>
                            <li>
                                <p class="fl"><span class="red mr5">*</span>联系人姓名</p>

                                <div class="oval4 fl"><input type="text" placeholder="请输入联系人姓名" value="${order.recName==null?user.userName:order.recName}" class="input" id="contactsName">
                                </div>

                                <p class="fl" style="margin-left: 115px;"><span class="red mr5">*</span>联系人手机号</p>

                                <div class="oval4 fl"><input type="text" placeholder="请输入联系人手机号" value="${order.mobile==null?user.telephone:order.mobile}" class="input" id="contactsTel">
                                </div>
                            </li>
                            <%--<li>--%>
                                <%--<p class="fl"><span class="red mr5">*</span>联系人手机号</p>--%>

                                <%--<div class="oval4 fl"><input type="text" placeholder="请输入联系人手机号" value="${order.mobile==null?user.telephone:order.mobile}" class="input" id="contactsTel">--%>
                                <%--</div>--%>
                            <%--</li>--%>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="fill_bottom_div">
                <div class="top fs13" style="display:none;">
                    <span class="fl checkbox checked" input="options" id="provisionCheck"><i></i></span>

                    <a href="javascript:;" class="protocol" style="color:#aaa;" onmouseover="this.style.cssText='color:#01af63'" onmouseout="this.style.cssText='color:#aaa'">
                        <p class="fl">我已阅读并接受协议条款、补充条款及其他所有内容</p>
                    </a>
                </div>

                <div class="bottom">
                    <a href="javaScript:;" style="background-color:#f66000;"class="oval4 b fs14 textC fl" onclick="javascript:history.go(-1);">上一步</a>
                    <input id="submitFlag" type="hidden" value="ok"/>
                    <c:if test="${hotel.source == 'ELONG'}">
                        <a href="javascript:;" class="b fs20 ff_yh textC  posiA " onclick="HotelOrder.submitOrder()">
                            <c:if test="${hotelPrice.status != 'GUARANTEE'}">完成预订</c:if>
                            <c:if test="${hotelPrice.status == 'GUARANTEE'}">支付担保金</c:if>
                        </a>

                    </c:if>
                    <c:if test="${hotel.source != 'ELONG'}">
                    <a href="javascript:;" class="b fs20 ff_yh textC  posiA " onclick="HotelOrder.submitOrder()">去支付</a>
                    </c:if>
                    <p class="fr b ff_yh fs16">
                        <c:if test="${hotelPrice.status != 'GUARANTEE'}">应付总额:</c:if>
                        <c:if test="${hotelPrice.status == 'GUARANTEE'}">应付担保金:</c:if>
                        <span class="fs20 red" id="lastCost">¥<fmt:formatNumber type="number"
                                                                                pattern="###.#">${price}</fmt:formatNumber>元
                        </span>
                    </p>
                </div>
            </div>

            <p class="cl"></p>
        </div>

        <!--右侧-->
        <div class="free_e_fr fr fill_fr fill_on_bot" id="nav">
            <div class="nav">
                <p class="title">费用明细</p>

                <div class="free_e_fr_div cl">
                    <div class="free_e_fr_t" style="font-size: 14px;">
                        <ul class="free_e_fr_ul">

                            <li>
                                <b class="name">酒店</b>

                                <div class="cl nr" id="rightPanel">
                                    <label class="fl" style="line-height: 14px;">${hotel.name}</label>
                                    <span class="fl cw" id="hotelDays" style="line-height: 14px;">1晚</span>
                                    <span class="fl num rightPanelTouristNum" id="hotelNums" style="line-height: 14px;">1x</span>
                                    <b class="price fr" id="rightCost"
                                       style="line-height: 14px;font-weight: bold;">¥<fmt:formatNumber type="number"
                                                                                                       pattern="###.#">${price}</fmt:formatNumber></b>
                                </div>
                            </li>

                        </ul>
                        <p class="cl"></p>
                    </div>
                    <div class="free_e_fr_c">
                        <div class="cl free_e_fr_c_p" style="font-size: 14px;">
                            <c:if test="${hotelPrice.status != 'GUARANTEE'}"><label class="fl">应付总额:</label></c:if>
                            <c:if test="${hotelPrice.status == 'GUARANTEE'}"><label class="fl">应付担保金:</label></c:if>
                            <%--<label class="fl">应付总额:</label>--%>
                            <b class="fr fs16 totalCost" id="rightTotalCost">¥<fmt:formatNumber type="number"
                                                                                                pattern="###.#">${price}</fmt:formatNumber></b>
                        </div>
                        <%--<div class="cl free_e_fr_c_p free_e_fr_c_p2">--%>
                        <%--<label class="fl">小帮价:</label>--%>
                        <%--<b class="fr fs20 ff_yh">1988</b>--%>
                        <%--<span class="fr mr5">7.5折</span>--%>
                        <%--</div>--%>
                        <c:if test="${hotel.source == 'ELONG'}">
                         <a href="javascript:;" class="free_e_fr_but ffhover b oval4" onclick="HotelOrder.submitOrder()">
                             <c:if test="${hotelPrice.status != 'GUARANTEE'}">完成预订</c:if>
                             <c:if test="${hotelPrice.status == 'GUARANTEE'}">支付担保金</c:if>
                         </a>
                            <br/>
                            <span style="color:red;margin-left: 12%;">

                                <c:if test="${hotelPrice.status != 'GUARANTEE'}">免费预订，入住酒店后前台付款</c:if>
                                <c:if test="${hotelPrice.status == 'GUARANTEE'}">担保预订，入住酒店后前台付款</c:if>
                            </span>
                        </c:if>
                        <c:if test="${hotel.source != 'ELONG'}">
                           <a href="javascript:;" class="free_e_fr_but ffhover b oval4" onclick="HotelOrder.submitOrder()">下一步去支付</a>
                        </c:if>
                    </div>


                    <p class="cl"></p>
                </div>

                <div class="fill_fr_div posiR">
                    <div class="name posiR fs16"><i></i>注意事项</div>
                    <p class="nr cl">

                        1> 旅行帮酒店供应商为艺龙旅行网<br/>
                        2> 现在预订，5分钟内供应商确认订单。<br/>
                        3> 订单提交后可拨打客服电话400-0131<br/>
                        &nbsp; -770免费取消/变更。<br/>
                        4> 房费请于酒店前台支付。<br/>
                        5> 如需发票请到酒店前台索取。<br/>
                        <%--<c:if test="${hotelPrice.status == 'GUARANTEE'}">--%>
                        <%--6>房量紧张，订单提交后需在线支付<span style="color: #f52">¥${price}</span>担保金--%>
                            <%--如未预订成功，担保金全部原路退还；入住结账，旅行帮向酒店确认后，担保金全额原路退还--%>
                            <%--订单一经预订成功不可变更/取消，如未入住酒店将扣除全部担保金作为违约金--%>
                            <%--退还金额境内卡5个工作日内到账，境外卡20个工作日内到账--%>
                        <%--</c:if>--%>
                    </p>
                    <div class="flot">
                        <b>咨询电话</b>

                        <p>400-0131-770</p>
                        <%--<p>4006587799转<br/>1420#</p>--%>
                    </div>
                </div>

            </div>

        </div>

        <p class="cl"></p>
    </div>
</div>
<input type="hidden" value="" id="date1"/>
<input type="hidden" value="" id="date2"/>
<%--<div class="mask"></div>--%>
<%--<!--添加成功-->--%>
<%--<div class="cg_prompt b fs16">--%>
<%--<img src="/images/tanc1.png" align="absmiddle" class="mr15"/>--%>
<%--<span></span>--%>
<%--</div>--%>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/order/order_util.js" type="text/javascript"></script>
<script src="/js/lvxbang/order/hotel.js" type="text/javascript"></script>
<script src="/js/lib/common_util.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        //日期下移
//        $('#startDate').change(function(){
//            console.log(123);
////            $('#leaveDate').val('');
//            if(isNull($('#leaveDate').val())){
//                $('#leaveDate').click();
//                $('#leaveDate').focus();
//            }
//        });


        $("img").lazyload({
            effect: "fadeIn"
        });

        //搜索框
        $(".categories .input").click(function () {
            $(".categories_div").hide();
            $(this).next(".categories_div").show();
        });

        $(".categories_div li").click(function () {
            var label = $("label", this).text();
            $(this).closest(".categories").children(".input").val(label);
        });

        $('.categories  .input').on('click', function (event) {
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
        $(document).on("click", function () {
            $(".categories_div").hide();
        });

        $("img").lazyload({
            effect: "fadeIn"
        });


        //删除按钮
        $(".Free_exe_d_d .close").click(function () {
            $(this).parent('li').fadeOut(500, function () {
                $(this).parent('li').remove();
            });
        });


        //checkbox
        $(".free_exercise_div .checkbox").click(function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").addClass("checked").attr("data-staute", "1");
                }
                else if (input == "selectall_group") {
                    $(this).addClass("checked").attr("data-staute", "1").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").addClass("checked").attr("data-staute", "1");
                }
                else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }
            }
            else {
                if (input == "selectall") {
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").removeClass("checked").removeAttr("data-staute");
                } else if (input == "selectall_group") {
                    $(this).removeClass("checked").removeAttr("data-staute").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");

                }
                $(this).removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_lm_div').prev('.free_e_fl_c_title').find(".checkbox").removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");
            }
        });


        //radio

        $(".tong .radio").click(function () {
            var myStaute = $(this).parent(".tong").attr("data-staute");
            if (!myStaute) {
                $(this).parent(".tong").addClass("checked").attr("data-staute", "1");
            } else {
                $(this).parent(".tong").removeClass("checked").removeAttr("data-staute");
            }
        });


        //下拉框
//        $(".sfz .name,sfz i").click(function () {
//            $(this).siblings(".sfzp").show();
//        });
//
//        $(".sfzp a").click(function () {
//            var label = $(this).text();
//            $(this).parent(".sfzp").hide();
//            $(this).parent(".sfzp").siblings(".name").text(label);
//        });


//        $('#startDate').change(function(){
//            var lDate = new Date(Date.parse($('#startDate').val().replace("-", "/")));
//            var rDate = new Date(Date.parse($('#leaveDate').val().replace("-", "/")));
//            if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
//                lDate.setDate(lDate.getDate() + 1);
//                $('#check-out-date').val(lDate.format("yyyy-MM-dd"));
//            }
//
////        $('#leaveDate').val('');
//        });

    });
    //当入住日期改变时，清空退房日期
//    function cleanCheckoutdate(){
//        $('#leaveDate').val('');
//    }



    //后退是刷新页面
    function pageReload(){
        if($('#page_reload').val()==0){
            $('#page_reload').val('1')
        }else{
            location.reload();
        }
    }

</script>

<script type="text/html" id="tpl-tourist-list-item">
    <div class="nr cl touristClass">
        <input type="hidden" class="touristId"/>
        <b class="fl num disB fs16">{{hotel_num}}</b>

        <div class="fl fill_div_nr_fr">
            <div class="fill_w1 cl">
                <div class="DebitCard_d2 fl oval4">
                    <input type="text" placeholder="姓名(请确保与证件保持一致)" value="{{name}}"
                           class="input touristName">
                </div>
                <%--<div class="sfz fl posiR oval4">--%>
                    <%--{{if peopleType == "ADULT"}}--%>
                    <%--<p class="name touristPeopleType">成人</p><i></i>--%>
                    <%--{{/if}}--%>
                    <%--{{if peopleType == "KID"}}--%>
                    <%--<p class="name touristPeopleType">儿童</p><i></i>--%>
                    <%--{{/if}}--%>


                    <%--<p class="sfzp disn">--%>
                        <%--<a href="javaScript:;">成人</a>--%>
                        <%--<a href="javaScript:;">儿童</a>--%>
                    <%--</p>--%>
                <%--</div>--%>
            </div>
            <div class="fill_w2 cl oval4">
                <div class="sfz fl posiR">
                    {{if idType == "IDCARD"}}
                    <p class="name touristIdType">身份证</p><i></i>
                    {{/if}}
                    {{if idType == "PASSPORT"}}
                    <p class="name touristIdType">护照</p><i></i>
                    {{/if}}

                    <p class="sfzp disn">
                        <a href="javaScript:;">身份证</a>
                        <a href="javaScript:;">护照</a>
                    </p>
                </div>
                <div class="DebitCard_d2 fl">
                    <input type="text" placeholder="请输入您的证件号" value="{{idNumber}}"
                           class="input touristIdNum">
                </div>
            </div>
            <div class="fill_w3 cl oval4">
                <div class="DebitCard_d2 ">
                    <input type="text" placeholder="请输入手机号" value="{{tel}}"
                           class="input touristTel">
                </div>
            </div>
        </div>
    </div>
</script>


