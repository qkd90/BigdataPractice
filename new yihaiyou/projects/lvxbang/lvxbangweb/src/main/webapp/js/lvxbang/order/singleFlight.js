/**
 * Created by vacuity on 16/1/4.
 */


var SingleFlight = {
    changePrice: function(num) {
        // 人数改变时改变总金额和游客信息输入框的数量
        Order.changePrice(num, "singleFlightPassengerNum", "singleFlightPrice", "singleFlightSumCost","singleFlightContacts", "singleFlightTourist", "rightPanel", "sf");
    },

    totalCost: function(sumCost) {
        //
        sumCost = Number(sumCost);
        var additionalFuelTaxPrice = Number($(".s_additionalFuelTaxPrice").html());
        var airportBuildFeePrice = Number($('.s_airportBuildFeePrice').html());
        if (isNaN(additionalFuelTaxPrice)) {
            additionalFuelTaxPrice = 0;
        }
        if (isNaN(airportBuildFeePrice)) {
            airportBuildFeePrice = 0;
        }
        var sum_Cost = (sumCost + additionalFuelTaxPrice + airportBuildFeePrice);
        //sumCost = sum_Cost.toFixed(1);
        //计算优惠券价格
        if($('.redPacketLi .checked').length>0){
            //cost = cost.toFixed(1);
            var li = $('.redPacketLi .checked').parents('li');
            var useCondition = Number(li.attr('useCondition'));
            if(sum_Cost >= useCondition){
                sum_Cost = sum_Cost - Number(li.attr("faceValue"));
            }else{
                $('.redPacketLi .checked').removeClass('checked');
            }
        }

        $("#orderSumCost").text("¥" + sum_Cost);
        $("#rightSumCost").text("¥" + sum_Cost);
    },

    fillTourist: function() {
        Order.fillTourist("singleFlightContacts", "singleFlightTourist");
    },

    createOrderData: function() {
        //
        var type = $("#trafficType").val();
        var orderType = "";
        if (type == "AIRPLANE") {
            orderType = "flight";
        } else {
            orderType = "train";
        }

        var orderId = $("#orderId").val();

        var data = {};
        if (orderId != null && orderId != "") {
            data["orderId"] = orderId;
        }
        data["contactsName"] = $("#contactsName").val();
        data["contactsTelphone"] = $("#contactsTel").val();
        data["orderType"] = orderType;
        var detailList = new Array();
        var detail = {};
        detail["id"] = $("#productId").val();
        detail["priceId"] = $("#priceId").val();
        detail["startTime"] = $("#flightDate").val();
        detail["endTime"] = $("#flightDate").val();
        detail["num"] = $("#singleFlightPassengerNum").val();
        detail["type"] = orderType;
        detail["seatType"] = $("#singleSeatType").val();
        var touristList = Order.getTourist("singleFlightTourist");
        detail["tourist"] = touristList;
        detailList.push(detail);
        data["detail"] = detailList;

        var json = JSON.stringify(data);
        return json;
    },

    submitOrder: function() {
        var validateMsg = Order.checkValidate();
        if (validateMsg == "ok") {
            //
            var json = SingleFlight.createOrderData();
            var userCouponId = $('.redPacketLi .checked').parents('li').attr('couponid');
            $("#submitFlag").val("invalid");
            loadingBegin();
            $.post("/lvxbang/order/createOrder.jhtml",
                {
                    data: json,
                    userCouponId:userCouponId

                }, function (result) {
                    loadingEnd();
                    if (result.success == true) {
                        //Order.popMsg("下单成功");
                        promptMessage("下单成功",3000);
                        var random = parseInt(Math.random() * 10000);
                        window.location.href = "/lvxbang/lxbPay/request.jhtml?orderId=" + result.orderId + "&random=" + random;
                    } else {
                        $("#submitFlag").val("ok");
                        promptWarn("下单失败");
                    }
                }
                ,"json");
        } else {
            //
            promptWarn(validateMsg);
        }
    },

    initEdit: function (orderDetailId) {
        $.post("/lvxbang/order/getOrderInfo.jhtml",
            {
                orderDetailId: orderDetailId

            }, function (result) {
                if (result.success == true) {
                    //
                    $("#singleFlightPassengerNum").val(result.num);
                    SingleFlight.changePrice(0);
                    var html = "";
                    $.each(result.tourist, function (i, data) {
                        html += template("tpl-tourist-list-item", data);
                    });
                    $("#singleFlightTourist").html(html);
                    $('.shiyong').click().removeClass('shiyong');
                    // 下拉事件
                    $(".sfz .name,sfz i").click(function () {
                        $(this).siblings(".sfzp").show();
                    });
                    $(".sfzp a").click(function () {
                        var label = $(this).text();
                        $(this).parent(".sfzp").hide();
                        $(this).parent(".sfzp").siblings(".name").text(label);
                    });
                } else {
                    promptWarn("获取订单信息失败");
                }
            }
            ,"json");
    }
}

$(function () {
    //SingleFlight.changePrice();
    SingleFlight.fillTourist();
    var orderDetailId = $("#orderDetailId").val();
    if (orderDetailId != null && orderDetailId != "") {
        SingleFlight.initEdit(orderDetailId);
    }
    bindEvent();
});
//事件绑定方法
function bindEvent(){
    $('.redPacketLi .checkbox').click(function(){

        if($(this).hasClass('checked')){
            $(this).removeClass('checked');
            SingleFlight.changePrice(0);
            return;
        }
        var li = $(this).parents('li');
        var checkedLi = $(this).parents('ul').find('.checked').parents('li');

        var useCondition = Number(li.attr('useCondition'));
        var faceValue = Number(checkedLi.attr('faceValue'));
        if(isNaN(faceValue)){
            faceValue = 0;
        }
        var rightTotalCost = Number($.trim($('#rightSumCost').html().replace("¥","")));
        if ((faceValue + rightTotalCost) >= useCondition) {
            $(this).parents('ul').find('.checked').removeClass('checked');
            $(this).addClass('checked');
            SingleFlight.changePrice(0);
        }else{
            promptWarn("金额必须满"+useCondition+"元才能抵用",1500);
        }

    });

    //如果是修改订单则判断是否有使用优惠券，作出相应处理
    //if(!isNull($('#userCouponId').val())){
    //    $('.redPacketLi').each(function(){
    //        if($(this).attr("couponid") == $('#userCouponId').val()){
    //           $(this).find(".checkbox").click();
    //        }
    //    });
    //
    //}

}