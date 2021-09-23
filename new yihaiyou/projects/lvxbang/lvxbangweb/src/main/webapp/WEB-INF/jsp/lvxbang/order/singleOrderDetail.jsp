<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/12
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>交通订单详情</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

</head>
<body class="order_details_fj_dc order_details"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
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
            <%--<c:choose>--%>
            <%--<c:when test="${orderDetailResponse.trafficType == '火车票'}">--%>
             <%--火车--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--飞机--%>
        <%--</c:otherwise>--%>
        <%--</c:choose>订单详情 --%>
        </p>

        <!--左侧-->
        <div class="free_e_fl fl">
            <!--标题-->
            <div class="order_details_title single_traffic_title">
                <p class="type">订单状态：<span>
                <%--${orderDetailResponse.printStatus}--%>
                    <c:if test="${orderDetailResponse.detailStatus == 'WAITING'}">
                        等待支付
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'PAYED'}">
                        已支付
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'SUCCESS'}">
                        交易完成
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'BOOKING'}">
                        预订中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'CANCELED'}">
                        已取消
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'CANCELING'}">
                        取消中
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'FAILED'}">
                        交易失败
                    </c:if>
                    <c:if test="${orderDetailResponse.detailStatus == 'RETRY'}">
                        重试
                    </c:if>
                </span></p>
                <dl>
                    <dt>
                    <div class="w1 fl">订单编号</div>
                    <div class="w2 fl" style="margin-left: 35px;">预订日期</div>
                    <div class="w3 fl" style="margin-left: 35px;">订单总额</div>
                    <%--<div class="w4 fl">确认号</div>--%>
                    </dt>
                    <dd>
                        <div class="w1 fl">${orderDetailResponse.orderNo}</div>
                        <div class="w2 fl" style="margin-left: 35px;">
                            <fmt:formatDate value="${orderDetailResponse.orderDate}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w3 fl" style="margin-left: 35px;">¥ ${orderDetailResponse.cost}</div>
                        <%--<div class="w4 fl">33344555452</div>--%>
                    </dd>
                </dl>
            </div>

            <div class="order_details_div order_detail_traffic">
                <p class="title">${orderDetailResponse.trafficType}信息</p>
                <ul class="free_e_fl_c_ut textC">
                    <c:choose>
                        <c:when test="${orderDetailResponse.trafficType == '火车票'}">
                            <li class="w1">车次</li>
                            <li class="w2">出发/到达日期</li>
                            <li class="w3">席位</li>
                            <li class="w4">单价(元)</li>
                            <li class="w5">数量</li>
                            <li class="w6">小计(元)</li>
                        </c:when>
                        <c:otherwise>
                            <li class="w1">航班</li>
                            <li class="w2">起飞/落地日期</li>
                            <li class="w3">舱位</li>
                            <li class="w4">单价(元)</li>
                            <li class="w5">数量</li>
                            <li class="w6">小计(元)</li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <div class="order_details_center">
                    <c:choose>
                        <c:when test="${orderDetailResponse.trafficType == '火车票'}">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl disn"></div>
                                <div class="w2 fl disn"></div>
                                <div class="w3 fl" style="text-align: left;margin-left: 88px;width: 112px;">
                                    <c:if test="${orderDetailResponse.company != null}">${orderDetailResponse.company}/</c:if>${orderDetailResponse.trafficCode}/单程</div>
                                <div class="w4 fl"style="    text-align: left;margin-left: 40px;width: 210px;"<c:if test="${orderDetailResponse.company != null}"> style="width:250px"</c:if>>
                                    <p style="margin-top: 10px;line-height: 10px;">出发: <fmt:formatDate value="${orderDetailResponse.date}" pattern="yyyy-MM-dd HH:mm"/> ${orderDetailResponse.leavePort}</p>
                                        <br/>
                                    <p style="line-height: 10px;">到达: <fmt:formatDate value="${orderDetailResponse.leaveDate}" pattern="yyyy-MM-dd HH:mm"/> ${orderDetailResponse.arrivePort}</p>
                                </div>
                                <div class="w5 fl">${orderDetailResponse.type}</div>
                                <div class="w6 fl">${orderDetailResponse.price}</div>
                                <div class="w7 fl">${orderDetailResponse.num}</div>
                                <div class="w8 fl">${orderDetailResponse.singleCost}</div>
                                </dt>
                            </dl>
                        </c:when>
                        <c:otherwise>
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl disn"></div>
                                <div class="w2 fl disn"></div>
                                <div class="w3 fl" >

                                  <%--<c:if test="${orderDetailResponse.company != null}">${orderDetailResponse.company}/</c:if>--%>
                                  ${orderDetailResponse.trafficCode}/单程

                                </div>
                                <div class="w4 fl"<c:if test="${empty returnOrderDetailResponse.company}"> style="width:250px"</c:if>>
                                    <p style="margin-top: 10px;line-height: 10px;">起飞: <fmt:formatDate value="${orderDetailResponse.date}" pattern="yyyy-MM-dd HH:mm"/> ${orderDetailResponse.leavePort}</p>
                                        <br/>
                                    <p style="line-height:10px;">落地: <fmt:formatDate value="${orderDetailResponse.leaveDate}" pattern="yyyy-MM-dd HH:mm"/> ${orderDetailResponse.arrivePort}</p>
                                </div>
                                <div class="w5 fl">${orderDetailResponse.type}</div>
                                <div class="w6 fl">${orderDetailResponse.price}</div>
                                <div class="w7 fl">${orderDetailResponse.num}</div>
                                <div class="w8 fl">${orderDetailResponse.singleCost}</div>
                                </dt>
                            </dl>
                        </c:otherwise>
                    </c:choose>
                    <ul class="textL">
                        <c:choose>
                            <c:when test="${orderDetailResponse.trafficType == '火车票'}">
                                <c:forEach items="${orderDetailResponse.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                                <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl" style="margin-left: 10px;">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 45px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 45px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${orderDetailResponse.touristList}" var="tourist" varStatus="status">
                                    <li>
                                        <div class="w1 fl">
                                                <%--旅客信息${status.index + 1}:--%>
                                        </div>
                                        <div class="w2 fl" style="margin-left: -11px;">姓名: ${tourist.name}</div>
                                        <div class="w3 fl" style="margin-left: 55px;">身份证: ${tourist.idNumber}</div>
                                        <div class="w4 fl" style="margin-left: 55px;">电话: ${tourist.tel}</div>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </ul>
                </div>

                <div class="order_details_bottom">
                    <p class="title">联系人</p>
                    <dl>
                        <dd>
                            <c:choose>
                                <c:when test="${orderDetailResponse.trafficType == '火车票'}">
                                    <div class="w1 fl" style="margin-left: 10px;width: auto;">姓名:${orderDetailResponse.contactsName}</div>
                                    <%--<div class="w2 fl">身份证：1301301988********</div>--%>
                                    <div class="w3 fl" style="margin-left: 25px;">电话:${orderDetailResponse.tel}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="w1 fl" style="margin-left: -11px;width: auto;">姓名:${orderDetailResponse.contactsName}</div>
                                    <%--<div class="w2 fl">身份证：1301301988********</div>--%>
                                    <div class="w3 fl" style="margin-left: 25px;">电话:${orderDetailResponse.tel}</div>
                                </c:otherwise>
                            </c:choose>


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
        <div class="free_e_fr fr order_details_fr">
            <div class="nav">
                <div class="free_e_fr_div cl">
                    <c:if test="${orderDetailResponse.status != 'PAYED'}">
                        <a href="${INDEX_PATH}/lvxbang/order/orderSingleFlight.jhtml?orderId=${orderDetailResponse.id}"
                           class="oval4">修改订单</a>
                    </c:if>
                    <c:if test="${orderDetailResponse.status == 'WAIT'}">
                        <a href="${INDEX_PATH}/lvxbang/lxbPay/request.jhtml?orderId=${orderDetailResponse.id}" class="oval4 checked">去支付</a>
                    </c:if>
                    <a href="${TRAFFIC_PATH}" class="oval4">继续预订</a>
                    <p class="cl"></p>
                </div>

                <div class="fill_fr_div posiR">
                    <div class="name posiR fs16"><i></i>注意事项</div>
                    <p class="nr cl" style="margin-right: 15px;">
                        <%--1> 退款手续费：¥15/张<br/>--%>
                        <%--2> 使用日期截止后当天20:00可申请退款<br/>--%>
                        <%--3> 不支持部分退款<br/>--%>
                        <%--4> 其他规定，请见<span>退款说明</span><br/>--%>
                            <c:choose>
                            <c:when test="${orderDetailResponse.trafficType == '火车票'}">
                                1> 因受全国各铁路局的不同规定与要求，无法承诺百分百出票。<br/>
                                2> 暂不提供学生票、团体票、纸质票等火车票购买功能。<br/>
                                3> 退票说明：<br/>
                                3.1 代购成功，未取票且发车前时间大于2小时，可致电旅行帮客服400-0131-770申请退票。<br/>
                                3.2 代购成功，已取票或发车前时间小于2小时，需您自行携带购票时所使用的乘车人有效证件原件和火车票在发车前去火车站退票窗口办理退票。<br/>
                                4> 改签说明：<br/>
                                4.1 改签需您自行携带购票时所使用的乘车人有效证件原件和火车票在发车前去火车站退票窗口办理改签。<br/>
                                5> 用户通过本平台委托购买火车票的相关服务均由代理方提供，本平台仅为技术服务提供方。<br/>
                            </c:when>
                            <c:otherwise>
                                1> 乘坐飞机需要报销凭证,请致电旅行帮客服：400-0131-770。<br/>
                                2> 飞机退订和更改规则以产品实际显示为准。<br/>
                                3> 用户通过本平台委托购买飞机票的相关服务均由代理方提供，本平台仅为技术服务提供方。<br/>
                            </c:otherwise>
                            </c:choose>

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
</script>


