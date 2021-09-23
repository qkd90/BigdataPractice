<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/12
  Time: 14:35
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
    <title>景点门票订单详情</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

</head>
<body class="order_details_mp order_details"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
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
            <%--门票订单详情 --%>
        </p>

        <!--左侧-->
        <div class="free_e_fl fl">
            <!--标题-->
            <div class="order_details_title ticket_detail_title">
                <p class="type">订单状态: <span>
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
                <%--${orderDetailResponse.printStatus}--%>

                </span></p>
                <dl>
                    <dt>
                    <div class="w1 fl">订单编号</div>
                    <div class="w2 fl" style="margin-left: 35px;">订单日期</div>
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

            <div class="order_details_div">
                <p class="title">门票信息</p>
                <ul class="free_e_fl_c_ut textC">
                    <li class="w1" style="width: 207px;">门票名称</li>
                    <li class="w2" style="margin-left: 25px;">使用日期</li>
                    <li class="w3">门票类型</li>
                    <li class="w4">单价(元)</li>
                    <li class="w5">门票数量</li>
                    <li class="w6">小计(元)</li>
                </ul>
                <div class="order_details_center">
                    <dl>
                        <dt class="textC" style="padding-left:75px">
                        <div class="w1 fl disn">&nbsp;</div>
                        <c:set var="string2" value="${fn:trim(orderDetailResponse.name)}" />
                        <%--<c:set var="string2" value="凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城凤凰古城" />--%>

                        <div class="w2 fl" style="text-align: left;width: 155px;<c:if test="${fn:length(orderDetailResponse.name) > 12}">line-height:16px; margin-top: 16px;</c:if>">
                        <%--<div class="w2 fl" style="text-align: left;margin-left: 78px;width: 155px;<c:if test="${fn:length(string2) > 12}">line-height:16px; margin-top: 16px;</c:if>">--%>
                        <%--${orderDetailResponse.name}--%>
                            ${fn:substring(string2, 0, 20)}
                        </div>
                        <div class="w4 fl">
                            <fmt:formatDate value="${orderDetailResponse.date}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w5 fl">${orderDetailResponse.type}</div>
                        <div class="w6 fl">${orderDetailResponse.price}</div>
                        <div class="w7 fl">${orderDetailResponse.num}</div>
                        <div class="w8 fl">${orderDetailResponse.singleCost}</div>
                        </dt>
                    </dl>
                    <ul class="textL">
                        <c:forEach items="${orderDetailResponse.touristList}" var="tourist" varStatus="status">
                            <li>
                                <div class="w1 fl">
                                    <%--旅客信息 ${status.index + 1}:--%>
                                </div>
                                <div class="w2 fl">姓名: ${tourist.name}</div>
                                <div class="w3 fl" style="margin-left: 25px;">
                                    <c:if test="${tourist.idType == 'IDCARD'}">身份证: </c:if>
                                    <c:if test="${tourist.idType == 'PASSPORT'}">护照:&nbsp; </c:if>
                                    ${tourist.idNumber}</div>
                                <div class="w4 fl">电话: ${tourist.tel}</div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="order_details_bottom">
                    <p class="title">订单联系人</p>
                    <dl>
                        <dd>
                            <div class="w1 fl" style="margin-left: 0px;width:auto;">姓名:${orderDetailResponse.contactsName}</div>
                            <%--<div class="w2 fl">身份证：1301301988********</div>--%>
                            <div class="w3 fl" style="margin-left: 35px;">电话:${orderDetailResponse.tel}</div>
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
                        <a href="${INDEX_PATH}/lvxbang/order/orderTicket.jhtml?orderId=${orderDetailResponse.id}"
                           class="oval4">修改订单</a>
                    </c:if>
                    <c:if test="${orderDetailResponse.status == 'WAIT'}">
                        <a href="${INDEX_PATH}/lvxbang/lxbPay/request.jhtml?orderId=${orderDetailResponse.id}" class="oval4 checked">去支付</a>
                    </c:if>
                    <a href="${SCENIC_PATH}/scenic_detail_${orderDetailResponse.productId}.html" class="oval4">再次预订</a>
                    <p class="cl"></p>
                </div>

                <div class="fill_fr_div posiR">
                    <div class="name posiR fs16"><i></i>注意事项</div>
                    <p class="nr cl">
                        1> 旅行帮景点门票供应商为携程旅行网。
                        2> 加各景点每张票的预订须知
                    </p>

                    <div class="flot">
                        <b>咨询电话</b>
                        <p>400-0131-770</p>
                        <%--<p>4006587799<br/>转1420#</p>--%>
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


