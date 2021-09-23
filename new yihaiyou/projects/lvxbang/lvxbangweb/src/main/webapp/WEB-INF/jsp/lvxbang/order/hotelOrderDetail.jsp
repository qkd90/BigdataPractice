<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/12
  Time: 16:03
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
    <title>酒店订单详情</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

</head>
<body class="order_details_jd order_details"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
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
            <%--行程订单详情 --%>
        </p>

        <!--左侧-->
        <div class="free_e_fl fl">
            <!--标题-->
            <div class="order_details_title hotel_detail_title">
                <p class="type">订单状态：<span>
                <%--<c:if test="${orderDetailResponse.printStatus != '等待支付' || orderDetailResponse.source != 'ELONG'}">--%>
                    <%--${orderDetailResponse.printStatus}--%>
                <%--</c:if>--%>
                    <%--<c:if test="${orderDetailResponse.status == 'PAYED'}">--%>
                        <%--预订完成--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${orderDetailResponse.status == 'PROCESSED'}">--%>
                        <%--预订成功--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${orderDetailResponse.status != 'PROCESSED' && orderDetailResponse.status != 'PAYED'}">--%>
                        <%--预订失败--%>
                    <%--</c:if>--%>

                    <c:if test="${orderDetailResponse.detailStatus == 'WAITING'}">
                        确认中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'PAYED'}">
                        确认中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'SUCCESS'}">
                        预订成功
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'BOOKING'}">
                        确认中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'CANCELED'}">
                        已取消
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'CANCELING'}">
                        取消中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'FAILED'}">
                        预订失败
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'RETRY'}">
                        重试
                    </c:if>

                </span>
                    <c:if test="${orderDetailResponse.printStatus == '支付成功' && orderDetailResponse.source == 'ELONG'}">
                        <span style="color:#f66000;">(前台支付)</span>
                    </c:if>
                    <%--<c:if test="${orderDetailResponse.source == 'ELONG'}">

                    </c:if>--%>
                </p>
                <dl>
                    <dt>
                    <div class="w1 fl">订单编号</div>
                    <div class="w2 fl" style="margin-left: 30px;">预订日期</div>
                    <div class="w3 fl" style="margin-left: 30px;">订单总额</div>
                    <%--<div class="w4 fl">确认号</div>--%>
                    </dt>
                    <dd>
                        <div class="w1 fl">${orderDetailResponse.orderNo}</div>
                        <div class="w2 fl" style="margin-left: 30px;">
                            <fmt:formatDate value="${orderDetailResponse.orderDate}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w3 fl" style="margin-left: 30px;">¥
                            <%--${orderDetailResponse.cost}--%>
                            ${orderDetailResponse.singleCost}

                        </div>
                        <%--<div class="w4 fl">${orderDetailResponse.confirmNo}</div>--%>
                    </dd>
                </dl>
            </div>

            <div class="order_details_div">
                <p class="title">酒店信息</p>
                <ul class="free_e_fl_c_ut textC">
                    <li class="w1" style="width: 180px;">酒店名称</li>
                    <li class="w2" style="width: 60px;margin-left: 93px;">使用日期</li>
                    <li class="w3" style="width: 70px;margin-left: 79px;">房间类型</li>
                    <li class="w4" style="width: 80px;margin-left: 5px;">单价(元)</li>
                    <li class="w5">预订数量</li>
                    <li class="w6" style="width: 80px;">小计(元)</li>
                </ul>
                <div class="order_details_center order_detail_hotel">
                    <dl>
                        <dt class="textC">
                        <div class="w1 fl disn">&nbsp;</div>
                        <div class="w2 fl" style="margin-left: 65px;width: 197px;text-align: left;<c:if test="${fn:length(orderDetailResponse.name) > 16}">line-height:16px; margin-top: 16px;</c:if>">
                        ${orderDetailResponse.name}</div>

                        <div class="w4 fl" style="margin-top: 15px;margin-left: 6px;">
                            入住: <fmt:formatDate value="${orderDetailResponse.date}" pattern="yyyy-MM-dd"/><br/>
                            退房: <fmt:formatDate value="${orderDetailResponse.leaveDate}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w5 fl" style="margin-left: 11px;">
                            <p style="margin-top: 24px;"
                                    <c:if test="${fn:length(orderDetailResponse.type) > 6}">class="p_hover"</c:if>>${orderDetailResponse.type}
                                <%--${orderDetailResponse.breakfast}--%>
                            </p>
                            &nbsp;
                            <%--<div class="mt10"></div>--%>
                        </div>
                        <div class="w6 fl" style="width: 45px;margin-left: 5px;">${orderDetailResponse.price}</div>
                        <div class="w7 fl" style="width: 112px;">${orderDetailResponse.num}</div>
                        <div class="w8 fl" style="width: 50px;">${orderDetailResponse.singleCost}</div>
                        </dt>
                    </dl>
                    <ul class="textL">
                        <c:forEach items="${orderDetailResponse.touristList}" var="tourist" varStatus="status">
                            <li>
                                <div class="w1 fl">
                                    <%--旅客信息${status.index + 1}:--%>
                                </div>
                                <div class="w2 fl" style="margin-left: -12px;">姓名: ${tourist.name}</div>
                                <div class="w3 fl" style="margin-left: 25px;">身份证: ${tourist.idNumber}</div>
                                <div class="w4 fl">电话: ${tourist.tel}</div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="order_details_bottom">
                    <p class="title">联系人</p>
                    <dl>
                        <dd style="margin-left: -12px;">
                            <div class="w1 fl" style="margin-left: 0px;width:auto;">姓名:${orderDetailResponse.contactsName}</div>
                            <%--<div class="w2 fl">身份证：1301301988********</div>--%>
                            <div class="w3 fl" style="margin-left: 25px;">电话:${orderDetailResponse.tel}</div>
                        </dd>
                    </dl>
                </div>
            </div>
            <p class="cl"></p>
        </div>

        <!--右侧-->
        <div class="free_e_fr fr order_details_fr">
            <div class="nav">
                <div class="free_e_fr_div cl">
                    <c:if test="${orderDetailResponse.status == 'WAIT' }">
                        <a href="${INDEX_PATH}/lvxbang/order/orderHotel.jhtml?orderId=${orderDetailResponse.id}&priceId=${orderDetailResponse.priceId}"
                           class="oval4">修改订单</a>
                        <c:if test="${orderDetailResponse.source != 'ELONG'}">
                            <a href="${INDEX_PATH}/lvxbang/lxbPay/request.jhtml?orderId=${orderDetailResponse.id}"
                               class="oval4 checked">去支付</a>
                        </c:if>
                    </c:if>
                    <a href="${HOTEL_PATH}/hotel_detail_${orderDetailResponse.productId}.html" class="oval4">再次预订</a>
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
<!--foot end--><!-- #EndLibraryItem -->
<input type="hidden" id="source" value="${orderDetailResponse.source}"/>
</body>
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
</script>


