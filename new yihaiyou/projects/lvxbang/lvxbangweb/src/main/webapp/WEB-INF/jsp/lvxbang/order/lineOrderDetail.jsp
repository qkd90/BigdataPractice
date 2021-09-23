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
    <title>路线订单详情</title>
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
                    ${order.status.description}
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
                        <div class="w3 fl" style="margin-left: 35px;">¥<fmt:formatNumber value="${orderDetailResponse.cost}" pattern="#,##0"/></div>
                        <%--<div class="w4 fl">33344555452</div>--%>
                    </dd>
                    <div style="line-height: 20px;">
                        <p>相关附件：<a href="#" style="color: rgb(51,153,0);">628346734合同.pdf</a></p>
                        <p style="color: rgb(51,153,0);">注：如果您无法阅读以上文件，<a href="#" style="color: rgb(51,153,0);">请下载免费PDF阅览器</a></p>
                    </div>
                </dl>
            </div>

            <div class="order_details_div">
                <p class="title">线路信息</p>
                <ul class="free_e_fl_c_ut textC">
                    <li class="w1" style="width: 220px;">线路名称</li>
                    <li class="w2" style="margin-left:2px;">使用日期</li>
                    <li class="w3" style="width: 80px;">人数*单价</li>
                    <li class="w4" style="width: 280px;">保险费用</li>
                </ul>
                <div class="order_details_center">
                    <dl>
                        <dt class="textC">
                        <c:set var="string2" value="${fn:trim(orderDetailResponse.name)}" />
                        <div class="w2 fl" style="text-align: left;width: 220px;margin-left: 17px;<c:if test="${fn:length(orderDetailResponse.name) > 12}">line-height:16px; margin-top: 16px;</c:if>">
                            ${fn:substring(string2, 0, 20)}
                        </div>
                        <div class="w4 fl" style="width: 110px;">
                            <fmt:formatDate value="${orderDetailResponse.date}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="w5 fl" style="text-align: left;<c:if test='${returnOrderDetailResponse != null && returnOrderDetailResponse.num > 0}'>line-height: 20px;padding-top: 10px;</c:if>">
                            ${orderDetailResponse.num}${orderDetailResponse.type}*¥<fmt:formatNumber value="${orderDetailResponse.price}" pattern="#,##0"/>
                            <c:if test="${returnOrderDetailResponse != null && returnOrderDetailResponse.num > 0}">
                                <br/>
                                ${returnOrderDetailResponse.num}${returnOrderDetailResponse.type}*¥<fmt:formatNumber value="${returnOrderDetailResponse.price}" pattern="#,##0"/>
                            </c:if>
                        </div>
                        <div class="w6 fl" style="line-height: 20px;width: 280px;padding-top: 10px;text-align: left;">
                            <c:forEach items="${order.orderInsurances}" var="orderInsurance">
                            ${orderInsurance.insurance.name}：¥${orderInsurance.insurance.price}*${totalNum}=¥${orderInsurance.insurance.price * totalNum}<br/>
                            </c:forEach>
                        </div>
                        <div class="w7 fl"></div>
                        </dt>
                    </dl>
                </div>

                <div class="order_details_bottom">
                    <p class="title">全程单房差</p>
                    <dl style="padding-left: 0px;">
                        <dd>
                            <div class="w1 fl" style="margin-left: 20px;">预付单房差</div>
                            <div class="w2 fl" style="margin-left: 15px; width: auto;">成人价：¥<fmt:formatNumber value="${orderDetailResponse.singleRoomPrice}" pattern="#,##0"/></div>
                            <div class="w3 fl" style="margin-left: 8px; width: auto;">
                                <c:if test='${returnOrderDetailResponse != null && returnOrderDetailResponse.singleRoomPrice > 0}'>
                                    儿童价：¥<fmt:formatNumber value="${returnOrderDetailResponse.singleRoomPrice}" pattern="#,##0"/>
                                </c:if>
                                <c:if test='${returnOrderDetailResponse == null || returnOrderDetailResponse.singleRoomPrice le 0}'>
                                    儿童价：不加价
                                </c:if>
                            </div>
                        </dd>
                    </dl>
                </div>

                <c:if test="${order.orderInsurances != null && fn:length(order.orderInsurances) > 0}">
                <div class="order_details_bottom">
                    <p class="title">保险方案</p>
                    <dl style="padding-left: 0px;">
                        <c:forEach items="${order.orderInsurances}" var="orderInsurance">
                            <dd class="cl">
                                <div class="w1 fl" style="margin-left: 20px; width: 200px;">${orderInsurance.insurance.name}</div>
                                <div class="w2 fl" style="margin-left: 10px; width: 30px;">${totalNum}份</div>
                                <div class="w3 fl" style="margin-left: 10px; width: 90px;">小计：¥${orderInsurance.insurance.price * totalNum}</div>
                            </dd>
                        </c:forEach>
                    </dl>
                </div>
                </c:if>

                <div class="order_details_bottom">
                    <p class="title">联系人信息</p>
                    <dl style="padding-left: 0px;">
                        <dd>
                            <div class="fl" style="margin-left: 20px; width: 150px;">联系人姓名：${orderDetailResponse.contactsName}</div>
                            <div class="fl" style="margin-left: 10px; width: 150px;">手机：${orderDetailResponse.tel}</div>
                            <div class="fl" style="margin-left: 10px; width: 150px;">固定电话：</div>
                            <div class="fl" style="margin-left: 10px; width: 150px;">E-mail：</div>
                        </dd>
                    </dl>
                </div>

                <div class="order_details_div">
                    <p class="title">出游人信息</p>
                    <ul class="free_e_fl_c_ut textC">
                        <li class="w1" style="width: 120px;">姓名</li>
                        <li class="w2" style="width: 120px;">类型</li>
                        <li class="w3" style="width: 120px;">证件类型</li>
                        <li class="w4" style="width: 180px;">证件号码</li>
                        <li class="w5" style="width: 150px;">手机</li>
                    </ul>
                    <div class="order_details_center">
                        <dl>
                            <c:forEach items="${orderDetailResponse.touristList}" var="tourist" varStatus="status">
                                <dt class="textC" style="<c:if test='${status.index != 0}'>border-top: 1px dashed rgb(107,206,164);</c:if>">
                                <div class="w1 fl" style="width: 120px;">
                                        ${tourist.name}&nbsp;
                                </div>
                                <div class="w2 fl" style="width: 120px;">
                                        ${tourist.peopleType.description}&nbsp;
                                </div>
                                <div class="w3 fl" style="width: 120px;">
                                        ${tourist.idType.description}&nbsp;
                                </div>
                                <div class="w4 fl" style="width: 180px;">
                                        ${tourist.idNumber}&nbsp;
                                </div>
                                <div class="w5 fl" style="width: 150px;">
                                        ${tourist.tel}&nbsp;
                                </div>
                                </dt>
                            </c:forEach>
                            <c:if test="${returnOrderDetailResponse != null}">
                            <c:forEach items="${returnOrderDetailResponse.touristList}" var="tourist" varStatus="status">
                                <dt class="textC"style="border-top: 1px dashed rgb(107,206,164);">
                                <div class="w1 fl" style="width: 120px;">
                                        ${tourist.name}&nbsp;
                                </div>
                                <div class="w2 fl" style="width: 120px;">
                                        ${tourist.peopleType.description}&nbsp;
                                </div>
                                <div class="w3 fl" style="width: 120px;">
                                        ${tourist.idType.description}&nbsp;
                                </div>
                                <div class="w4 fl" style="width: 180px;">
                                        ${tourist.idNumber}&nbsp;
                                </div>
                                <div class="w5 fl" style="width: 150px;">
                                        ${tourist.tel}&nbsp;
                                </div>
                                </dt>
                            </c:forEach>
                            </c:if>
                        </dl>
                    </div>
                </div>

                <c:if test="${order.invoice != null}">
                <div class="order_details_div" style="margin-top: 36px;">
                    <p class="title">发票信息</p>
                    <ul class="free_e_fl_c_ut textC">
                        <li class="w1" style="width: 160px;">发票抬头</li>
                        <li class="w2" style="width: 160px;">发票明细</li>
                        <li class="w3" style="width: 120px;">金额</li>
                    </ul>
                    <div class="order_details_center">
                            <dl>
                                <dt class="textC">
                                <div class="w1 fl" style="width: 160px;">
                                        ${order.invoice.title}
                                </div>
                                <div class="w2 fl" style="width: 160px;">
                                    旅游费
                                </div>
                                <div class="w2 fl" style="width: 120px;">
                                    ¥<fmt:formatNumber value="${orderDetailResponse.cost - order.insurancePrice}" pattern="#,##0"/>
                                </div>
                                </dt>
                            </dl>
                            <div style="margin-left: 10px;line-height:25px;">
                                <strong style="font-weight: bold;">配送地址：</strong> ${order.invoice.address}（${order.invoice.name}，${order.invoice.telephone}）
                            </div>
                        <div style="margin-left: 10px;color: rgb(153,153,153);line-height:20px;">
                            <div class="fl">注：</div>
                            <div class="fl">
                            1.旅游费用和保险费用的发票分开，添加旅游费用发票自动生成保险费用发票；<br/>
                            2.出游前填写的发票将于出游归来后5个工作日内开具，请注意查收；<br/>
                            3.发票开具有效期为出游归来后两个月内，如有疑问请电询；<br/>
                            4.发票开具后，若办理退款，需先退还原发票，并保持发票兑奖联完好（如有兑奖联）。
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>

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
                    <c:if test="${order.status == 'UNCONFIRMED'}">
                        <a href="${INDEX_PATH}/lvxbang/order/orderLine.jhtml?orderId=${orderDetailResponse.id}"
                           class="oval4">修改订单</a>
                    </c:if>
                    <c:if test="${orderDetailResponse.status == 'WAIT'}">
                        <a href="${INDEX_PATH}/lvxbang/lxbPay/request.jhtml?orderId=${orderDetailResponse.id}" class="oval4 checked">去支付</a>
                    </c:if>
                    <%--<a href="${SCENIC_PATH}/lvxbang/scenic/detail.jhtml?scenicId=${orderDetailResponse.productId}" class="oval4">再次预订</a>--%>
                    <p class="cl"></p>
                </div>

                <div class="fill_fr_div posiR">
                    <div class="name posiR fs16"><i></i>注意事项</div>
                    <p class="nr cl">
                        1> 退款手续费：¥15/张<br/>
                        2> 使用日期截止后当天20:00可申请退款<br/>
                        3> 不支持部分退款<br/>
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


