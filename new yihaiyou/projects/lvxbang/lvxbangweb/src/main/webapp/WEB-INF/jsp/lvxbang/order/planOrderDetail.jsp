<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/13
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>线路订单详情</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

</head>
<body class="order_details_xc order_details"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <div class="free_exercise_div cl">
        <p class="n_title">
            <%--您在这里：&nbsp;<a href="${INDEX_PATH}/lvxbang/user/plan.jhtml">个人中心</a>&nbsp;&gt;&nbsp;--%>
            <%--<a href="${INDEX_PATH}/lvxbang/user/order.jhtml">我的订单</a>&nbsp;&gt;&nbsp;--%>
            <%--线路订单详情 --%>
        </p>

        <!--左侧-->
        <div class="free_e_fl fl" id="dingdantou">
            <!--标题-->
            <div class="order_details_title">
                <p class="type">订单状态：<span>${orderDetailResponse.printStatus} (酒店费用前台支付)</span></p>
                <dl>
                    <dt>
                    <div class="w1 fl">订单编号</div>
                    <div class="w2 fl">预订日期</div>
                    <div class="w3 fl">订单总额</div>
                    <div class="w4 fl">确认号</div>
                    </dt>
                    <dd>
                        <div class="w1 fl">${orderDetailResponse.id}</div>
                        <div class="w2 fl">
                            <fmt:formatDate value="${orderDetailResponse.orderDate}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w3 fl">¥${orderDetailResponse.cost}</div>
                        <div class="w4 fl">33344555452</div>
                    </dd>
                </dl>
            </div>

            <div class="order_details_div">
                <p class="title">套餐信息</p>
                <ul class="free_e_fl_c_ut textC">
                    <li class="w1" style="width:220px;">名称</li>
                    <li class="w2">日期</li>
                    <li class="w3" style="width:50px;margin-left: 90px;">类型</li>
                    <li class="w4" style="margin-left: 26px;width:52px;">单价(元)</li>
                    <li class="w5">数量</li>
                    <li class="w6">小计(元)</li>
                </ul>

                <!--飞机-->
                <c:if test="${planeDetailList.size() > 0}">
                    <p class="od_title">飞机</p>
                    <div class="order_details_center">
                        <c:forEach items="${planeDetailList}" var="plane" varStatus="status">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl" style="width:52px;">
                                    &nbsp;
                                        <%--去程--%>
                                </div>
                                <div class="w2 fl">${plane.company}</div>
                                <div class="w3 fl">${plane.trafficCode}</div>
                                <div class="w4 fl " style="margin-left: 18px;width: 200px;text-align: left;">
                                    <p style="line-height: 25px;margin-top: 8px;"><fmt:formatDate value="${plane.date}"
                                                       pattern="yyyy-MM-dd HH:mm"/> ${plane.leavePort} </p>
                                    <p style="line-height: 16px;"><fmt:formatDate value="${plane.leaveDate}"
                                        pattern="yyyy-MM-dd HH:mm"/> ${plane.arrivePort}</p>
                 <%--class="hover_in"<div style="display: none;border:1px solid #dddddd;--%>
                                    <%--width:200px;background-color: #f7f8fc;line-height: 20px;" class="posiA">--%>
                                        <%--<p class="fl" style="margin-left: 5px;"><fmt:formatDate value="${plane.date}"--%>
                                                            <%--pattern="yyyy-MM-dd HH:mm"/> ${plane.leavePort}</p>--%>
                                        <%--<p class="fl" style="margin-left: 5px;"><fmt:formatDate value="${plane.leaveDate}"--%>
                                                           <%--pattern="yyyy-MM-dd HH:mm"/> ${plane.arrivePort}</p>--%>
                                    <%--</div>--%>
                                </div>
                                <div class="w5 fl" style="width:75px;margin-right: 10px;">${plane.type}</div>
                                <div class="w6 fl" style="width:50px;">${plane.price}</div>
                                <div class="w7 fl">${plane.num}</div>
                                <div class="w8 fl">${plane.singleCost}</div>
                                </dt>
                            </dl>
                            <ul class="textL" style="padding-left: 68px;">
                                <c:forEach items="${plane.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                            <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 63px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 63px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </div>
                </c:if>

                <!--火车-->
                <c:if test="${trainDetailList.size() > 0}">
                    <p class="od_title">火车</p>
                    <div class="order_details_center">
                        <c:forEach items="${trainDetailList}" var="train" varStatus="status">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl">
                                    &nbsp;
                                        <%--去程--%>
                                </div>
                                <div class="w2 fl">${train.company}</div>
                                <div class="w3 fl" style="margin-left:0px;">${train.trafficCode}</div>
                                <div class="w4 fl " style="margin-left: 83px;width: 175px;text-align: left;">
                                    <p style="line-height: 25px;margin-top: 8px"><fmt:formatDate value="${train.date}"
                                                       pattern="yyyy-MM-dd HH:mm"/> ${train.leavePort}</p>
                                    <p style="line-height: 25px;"><fmt:formatDate value="${train.leaveDate}"
                                                                                  pattern="yyyy-MM-dd HH:mm"/> ${train.arrivePort}</p>
                                    <%--hover_in--%>
                                    <%--<div style="display: none;border:1px solid #dddddd;--%>
                                    <%--width:160px;background-color: #f7f8fc;line-height: 20px;" class="posiA">--%>
                                        <%--<p class="fl" style="margin-left: 5px;"><fmt:formatDate value="${train.date}"--%>
                                                           <%--pattern="yyyy-MM-dd HH:mm"/> ${train.leavePort}</p>--%>
                                        <%--<p class="fl" style="margin-left: 5px;"><fmt:formatDate value="${train.leaveDate}"--%>
                                                           <%--pattern="yyyy-MM-dd HH:mm"/> ${train.arrivePort}</p>--%>
                                    <%--</div>--%>
                                </div>
                                <div class="w5 fl" style="margin-left: 8px;margin-right: 0px;">${train.type}</div>
                                <div class="w6 fl" style="width:50px;">${train.price}</div>
                                <div class="w7 fl">${train.num}</div>
                                <div class="w8 fl">${train.singleCost}</div>
                                </dt>
                            </dl>
                            <ul class="textL" style="padding-left: 68px;">
                                <c:forEach items="${train.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                            <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 65px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 65px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </div>
                </c:if>

                <!--酒店-->
                <c:if test="${hotelDetailList.size() > 0}">
                    <p class="od_title">酒店</p>
                    <div class="order_details_center jiudian">
                        <c:forEach items="${hotelDetailList}" var="hotel" varStatus="status">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl">&nbsp;

                                <c:if test="${hotel.priceStatus == 'UP'}"><span style="font-size: 10px;
                                                                                        color: white;
                                                                                        background-color: #EEDC82;
                                                                                        border-radius: 3px;
                                                                                        padding: 1px;
                                                                                        vertical-align: -webkit-baseline-middle;
                                                                                    }"
                                        >到付</span></c:if>
                                <c:if test="${hotel.priceStatus != 'UP'}"><span style="font-size: 10px;
                                                                                        color: white;
                                                                                        background-color: #69A7EB;
                                                                                        border-radius: 3px;
                                                                                        padding: 1px;
                                                                                        vertical-align: -webkit-baseline-middle;
                                ">担保</span></c:if>
                                </div>
                                <div class="w2 fl" style="width: 140px;margin-top: 15px; <c:if test="${fn:length(hotel.name) > 11}">line-height:16px; </c:if>"   >${hotel.name}</div>

                                <div class="w4 fl">
                                    <p style="line-height: 23px;margin-top: 10px;"><fmt:formatDate value="${hotel.date}" pattern="yyyy-MM-dd"/>
                                    入住</p>
                                    <p style="line-height: 23px;"><fmt:formatDate value="${hotel.leaveDate}" pattern="yyyy-MM-dd"/> 退房</p>
                                </div>
                                <div class="w5 fl hover_in" style="margin-left: 77px;margin-right: 0px;margin-top: 11px;">
                                        <p>${fn:substring(hotel.type,0,6)}</p>

                                    <%--<div style="display: none;border:1px solid #dddddd;--%>
                                        <%--width:160px;background-color: #f7f8fc;line-height: 20px;" class="posiA">--%>
                                        <%--<p class="fl" style="margin-left: 5px;">${hotel.type}</p>--%>
                                        <%--<p class="fl" style="margin-left: 10px;">${hotel.breakfast}</p>--%>
                                    <%--</div>--%>
                                </div>
                                <div class="w6 fl" style="width:50px;">${hotel.price}</div>
                                <div class="w7 fl">${hotel.num}</div>
                                <div class="w8 fl">${hotel.singleCost}</div>
                                </dt>
                            </dl>
                            <ul class="textL" style="padding-left: 68px;">
                                <c:forEach items="${hotel.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                            <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 67px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 67px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </div>
                </c:if>

                <!--门票-->
                <c:if test="${ticketDetailList.size() > 0}">
                    <p class="od_title">门票</p>
                    <div class="order_details_center jingdian">
                        <c:forEach items="${ticketDetailList}" var="ticket" varStatus="status">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl">&nbsp;</div>
                                <div class="w2 fl">${ticket.name}</div>
                                <div class="w4 fl" style="line-height: 32px;width:75px;margin-top: 14px;">
                                    <fmt:formatDate value="${ticket.date}" pattern="yyyy-MM-dd"/>
                                </div>
                                <div class="w5 fl" style="margin-left: 110px;margin-right: 0px;">${ticket.type}</div>
                                <div class="w6 fl" style="width:50px;">${ticket.price}</div>
                                <div class="w7 fl">${ticket.num}</div>
                                <div class="w8 fl">${ticket.singleCost}</div>
                                </dt>
                            </dl>
                            <ul class="textL" style="padding-left: 68px;">
                                <c:forEach items="${ticket.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                            <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 69px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 69px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </div>
                </c:if>

                <div class="order_details_bottom">
                    <p class="title">联系人</p>
                    <dl>
                        <dd>
                            <div class="w1 fl" style="margin-left:-10px;width:auto;">姓名:${orderDetailResponse.contactsName}</div>
                            <%--<div class="w2 fl">身份证：1301301988********</div>--%>
                            <div class="w3 fl" style="margin-left: 55px;">电话:${orderDetailResponse.tel}</div>
                        </dd>
                    </dl>
                </div>

                <c:if test="${couponName != null && couponName != ''}">
                    <div class="order_details_bottom">
                        <p class="title">已享受优惠</p>
                        <dl>
                            <dd>
                                <div class="fl"><span style="border:solid 1px red;padding: 2px;margin-right: 2px;color:red;">立减</span><span style="color:red;">¥${couponValue}</span> 已使用${couponName}红包，优惠¥${couponValue}</div>
                            </dd>
                        </dl>
                    </div>
                </c:if>
            </div>
            <p class="cl"></p>
        </div>

        <!--右侧-->
        <div class="free_e_fr fr order_details_fr" id="zhifu">
            <div class="nav">
                <div class="free_e_fr_div cl">
                    <c:if test="${orderDetailResponse.status != 'PAYED'}">
                        <a href="${INDEX_PATH}/lvxbang/order/editPlanOrder.jhtml?orderId=${orderDetailResponse.id}" class="oval4">修改订单</a>
                    </c:if>
                    <c:if test="${orderDetailResponse.status == 'WAIT'}">
                        <a href="${INDEX_PATH}/lvxbang/lxbPay/request.jhtml?orderId=${orderDetailResponse.id}" class="oval4 checked">去支付</a>
                    </c:if>
                    <a href="${SCENIC_PATH}" class="oval4">继续预订</a>
                    <p class="cl"></p>
                </div>

                <div class="fill_fr_div posiR">
                    <div class="name posiR fs16"><i></i>注意事项</div>
                    <p class="nr cl" style="margin-right: 15px;">
                        1> 付款成功后如需修改、取消、退订，将按您所订资源的退变更条款收取费用。<br/>
                        2> 烦请注意填写预订人与实际出行人的姓名及联系方式保持一致，以免信息错误导致无法正常使用，如因此给您带来的损失我司不给予承担，给您带来不便请谅解。<br/>
                        3> 如需帮助，请致电旅行帮客服，电话：400-0131-770 。<br/>
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
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
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
        $(".sfz .name,sfz i").click(function () {
            $(this).siblings(".sfzp").show();
        });

        $(".sfzp a").click(function () {
            var label = $(this).text();
            $(this).parent(".sfzp").hide();
            $(this).parent(".sfzp").siblings(".name").text(label);
        });

    });
       //过多文字隐藏
     $('.hover_in').hover(function(){
//         $(this).find('.p').css('display','none');
         $(this).find('div').css('display','block');
     },function(){
//         $(this).find('.p').css('display','block');
         $(this).find('div').css('display','none');
     });

    //支付浮动
    var navbar = function () {
        var navbar_top = $(window).scrollTop();
//        var height = $("#nav").height();
        var listheight = $("#dingdantou").offset().top;

        if (navbar_top >= listheight) {

            $('#zhifu').css("position","fixed");
            $('#zhifu').css("top",listheight/2 + "px");
            $('#zhifu').css("margin-left","12px");
        } else {

            $('#zhifu').css("position","");
            $('#zhifu').css("top","");
            $('#zhifu').css("margin-left","");
        }
    };
    $(window).bind("scroll", navbar);
</script>


