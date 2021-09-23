/**
 * Created by vacuity on 16/1/5.
 */


var ReturnFlight = {
    initSumCost: function () {
        //
        var singleCost = Number($("#singleFlightSumCost").text());
        var returnCost = Number($("#returnFlightSumCost").text());
        var s_additionalFuelTaxPrice = Number($(".s_additionalFuelTaxPrice").attr("additionalFuelTax"));
        var s_airportBuildFeePrice = Number($('.s_airportBuildFeePrice').attr("airportBuildFee"));

        if(isNaN(s_additionalFuelTaxPrice)){
            s_additionalFuelTaxPrice=0;
        }
        if(isNaN(s_airportBuildFeePrice)){
            s_airportBuildFeePrice=0;
        }
        var s_sum_Cost = singleCost + s_additionalFuelTaxPrice + s_airportBuildFeePrice;
        var r_additionalFuelTaxPrice = Number($(".r_additionalFuelTaxPrice").attr("additionalFuelTax"));
        var r_airportBuildFeePrice = Number($('.r_airportBuildFeePrice').attr("airportBuildFee"));
        if(isNaN(r_additionalFuelTaxPrice)){
            r_additionalFuelTaxPrice=0;
        }
        if(isNaN(r_airportBuildFeePrice)){
            r_airportBuildFeePrice=0;
        }
        var r_sum_Cost = returnCost + r_additionalFuelTaxPrice + r_airportBuildFeePrice;
        var sum = s_sum_Cost + r_sum_Cost;
        var sum1 = returnCost + singleCost;
        //sum = sum.toFixed(1);
        $("#rightSumCost").text("¥" + sum1);
        $("#orderSumCost").text("¥" + sum1);
    },
    changePrice: function(num, type) {

        if (type == "go") {
            //
            Order.changePrice(num, "singleFlightPassengerNum", "singleFlightPrice", "singleFlightSumCost", "singleFlightContacts", "singleFlightTourist", "singleRightPanel", "rf");
        } else {
            //
            Order.changePrice(num, "returnFlightPassengerNum", "returnFlightPrice", "returnFlightSumCost", "returnFlightContacts", "returnFlightTourist", "returnRightPanel", "rf");
        }

    },

    totalCost: function() {
        //
        var sumCost = 0;
        if ($("#firstCheck").hasClass("checked")) {
            //var s_additionalFuelTaxPrice = Number($(".s_additionalFuelTaxPrice").text());
            //var s_airportBuildFeePrice = Number($('.s_airportBuildFeePrice').text());
            var singleCost = Number($("#singleFlightSumCost").text());
             //singleCost = singleCost + s_additionalFuelTaxPrice + s_airportBuildFeePrice;
            sumCost += singleCost;
        }
        if ($("#lastCheck").hasClass("checked")) {
            var returnCost = Number($("#returnFlightSumCost").text());
            //var r_additionalFuelTaxPrice = Number($(".r_additionalFuelTaxPrice").text());
            //var r_airportBuildFeePrice = Number($('.r_airportBuildFeePrice').text());
            //var returnCost = returnCost + r_additionalFuelTaxPrice + r_airportBuildFeePrice;
            sumCost += returnCost;
        }
        //sumCost = sumCost.toFixed(1);

        //计算优惠券价格
        if($('.redPacketLi .checked').length>0){
            //cost = cost.toFixed(1);
            var li = $('.redPacketLi .checked').parents('li');
            var useCondition = Number(li.attr('useCondition'));
            if(sumCost >= useCondition){
                sumCost = sumCost - Number(li.attr("faceValue"));
            }else{
                $('.redPacketLi .checked').removeClass('checked');
            }
        }


        $("#orderSumCost").text("¥" + sumCost);
        $("#rightSumCost").text("¥" + sumCost);
    },

    provisionCheck: function () {
        //
        if ($("#provisionCheck").hasClass("checked")) {
            $("#provisionCheck").removeClass("checked")
        } else {
            $("#provisionCheck").addClass("checked")
        }
    },

    changeCheck: function(location) {
        //
        if (location == "all") {
            //
            if ($("#allCheck").hasClass("checked")) {
                $("#allCheck").removeClass("checked");
                $("#firstCheck").removeClass("checked");
                $("#lastCheck").removeClass("checked");
                //
                $("#rightSumCost").text("0");
                $("#orderSumCost").text(0);
                $("#singleRightPanel").addClass("display-none");
                $("#returnRightPanel").addClass("display-none");
            } else {
                $("#allCheck").addClass("checked");
                $("#firstCheck").addClass("checked");
                $("#lastCheck").addClass("checked");
                //
                $("#singleRightPanel").removeClass("display-none");
                $("#returnRightPanel").removeClass("display-none");

                var singleCost = Number($("#singleFlightSumCost").text());
                var returnCost = Number($("#returnFlightSumCost").text());
                var sum = singleCost + returnCost;
                //sum = sum.toFixed(1);
                $("#rightSumCost").text("" + sum);
                $("#orderSumCost").text(sum);
            }
        } else if (location == "first") {
            //
            if ($("#firstCheck").hasClass("checked")) {
                $("#firstCheck").removeClass("checked");
                $("#allCheck").removeClass("checked");
                //
                $("#singleRightPanel").addClass("display-none");
                if ($("#lastCheck").hasClass("checked")) {
                    var returnCost = Number($("#returnFlightSumCost").text());
                    $("#rightSumCost").text("" + returnCost);
                    $("#orderSumCost").text(returnCost);
                } else {
                    $("#rightSumCost").text("0");
                    $("#orderSumCost").text(0);
                }
            } else {
                $("#firstCheck").addClass("checked");
                $("#singleRightPanel").removeClass("display-none");
                if ($("#lastCheck").hasClass("checked")) {
                    // 两个都勾选后选中全部也应该是勾选的
                    $("#allCheck").addClass("checked");
                    var singleCost = Number($("#singleFlightSumCost").text());
                    var returnCost = Number($("#returnFlightSumCost").text());
                    var sum = singleCost + returnCost;
                    //sum = sum.toFixed(1);
                    $("#rightSumCost").text("" + sum);
                    $("#orderSumCost").text(sum);
                } else {
                    var singleCost = Number($("#singleFlightSumCost").text());
                    $("#rightSumCost").text("" + singleCost);
                    $("#orderSumCost").text(singleCost);
                }
            }
        } else {
            //
            if ($("#lastCheck").hasClass("checked")) {
                $("#lastCheck").removeClass("checked");
                $("#allCheck").removeClass("checked");
                //
                $("#returnRightPanel").addClass("display-none");
                if ($("#firstCheck").hasClass("checked")) {
                    var singleCost = Number($("#singleFlightSumCost").text());
                    $("#rightSumCost").text("" + singleCost);
                    $("#orderSumCost").text(singleCost);
                } else {
                    $("#rightSumCost").text("0");
                    $("#orderSumCost").text(0);
                }
            } else {
                $("#lastCheck").addClass("checked");
                $("#returnRightPanel").removeClass("display-none");
                if ($("#firstCheck").hasClass("checked")) {
                    // 两个都勾选后选中全部也应该是勾选的
                    $("#allCheck").addClass("checked");
                    var singleCost = Number($("#singleFlightSumCost").text());
                    var returnCost = Number($("#returnFlightSumCost").text());
                    var sum = singleCost + returnCost;
                    //sum = sum.toFixed(1);
                    $("#rightSumCost").text("" + sum);
                    $("#orderSumCost").text(sum);
                } else {
                    var returnCost = Number($("#returnFlightSumCost").text());
                    $("#rightSumCost").text("" + returnCost);
                    $("#orderSumCost").text(returnCost);
                }
            }
        }
    },

    fillTourist: function() {
        Order.fillTourist("singleFlightContacts", "singleFlightTourist");
        Order.fillTourist("returnFlightContacts", "returnFlightTourist");
    },

    copyTourist: function () {
        //
        if (!$("#copyFlag").hasClass("checked")) {
            Order.copyTourist("singleFlightContacts", "singleFlightTourist", "returnFlightContacts", "returnFlightTourist", "returnFlightPassengerNum", "rf");
        }
    },

    createOrderData: function() {
        // 订单类型
        var type = $("#trafficType").val();
        var orderType = "";
        if (type == "AIRPLANE") {
            orderType = "flight";
        } else {
            orderType = "train";
        }
        //
        var orderId = $("#orderId").val();

        var data = {};
        if (orderId != null && orderId != "") {
            data["orderId"] = orderId;
        }
        data["contactsName"] = $("#contactsName").val();
        data["contactsTelphone"] = $("#contactsTel").val();
        data["orderType"] = orderType;
        var detailList = new Array();
        if ($("#firstCheck").hasClass("checked")) {
            // 第一个航班
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
        }
        if ($("#lastCheck").hasClass("checked")) {
            // 第二个航班
            var returnDetail = {};
            returnDetail["id"] = $("#returnProductId").val();
            returnDetail["priceId"] = $("#returnPriceId").val();
            returnDetail["startTime"] = $("#returnFlightDate").val();
            returnDetail["endTime"] = $("#returnFlightDate").val();
            returnDetail["num"] = $("#returnFlightPassengerNum").val();
            returnDetail["type"] = orderType;
            returnDetail["seatType"] = $("#returnSeatType").val();
            var returnTouristList = Order.getTourist("returnFlightTourist");
            returnDetail["tourist"] = returnTouristList;
            detailList.push(returnDetail);
        }

        data["detail"] = detailList;

        var json = JSON.stringify(data);
        return json;
    },

    submitOrder: function() {
        together();
        var validateMsg = Order.checkValidate();
        if (validateMsg == "ok") {
            //
            if ($("#firstCheck").hasClass("checked") || $("#lastCheck").hasClass("checked")) {
                var json = ReturnFlight.createOrderData();
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
                promptWarn("请选择至少一件商品");
            }
        } else {
            //
            promptWarn(validateMsg);
        }
    },

    initEdit: function (orderDetailId, returnOrderDetailId) {
        $.post("/lvxbang/order/getOrderInfo.jhtml",
            {
                orderDetailId: orderDetailId,
                returnOrderDetailId: returnOrderDetailId

            }, function (result) {
                if (result.success == true) {
                    //
                    $("#singleFlightPassengerNum").val(result.num);
                    ReturnFlight.changePrice(0, "go");
                    var html = "";
                    var piao_num=1;
                    $.each(result.tourist, function (i, data) {
                        data.piao_num = piao_num;
                        html += template("tpl-tourist-list-item", data);
                        piao_num++;
                    });
                    $("#singleFlightTourist").html(html);
                    $('.shiyong').click().removeClass('shiyong');
                    $("#returnFlightPassengerNum").val(result.returnNum);
                    ReturnFlight.changePrice(0);
                    var rhtml = "";
                    piao_num=1;
                    $.each(result.returnTourist, function (i, data) {
                        data.piao_num = piao_num;
                        rhtml += template("tpl-tourist-list-item", data);
                        piao_num++;
                    });
                    $("#returnFlightTourist").html(rhtml);
                    var edit_num = $('.editorder').size();
                    if(edit_num>0){
                        for(var i=0;i<edit_num/2;i++){
                            if($('.editorder').eq(i).val()!=$('.editorder').eq(edit_num/2 + i).val()){
                                $(".tong .radio").parent(".tong").removeClass("checked").removeAttr("data-staute");
                                $('#returnFlightPassengerNum').attr('disabled',false);
                                $('#div_together').css('display','block');
                            }
                        }

                    }
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
    ReturnFlight.fillTourist();

    var orderDetailId = $("#orderDetailId").val();
    var returnOrderDetailId = $("#returnOrderDetailId").val();
    if (orderDetailId != null && orderDetailId != "") {
        ReturnFlight.initEdit(orderDetailId, returnOrderDetailId);
    }
    ReturnFlight.initSumCost();

    bindEvent();
});


//默认旅客信息同上
function together(){
    var c = $('#radio_together').parent().hasClass('checked');
    if(c) {
        Order.copyTourist("singleFlightContacts", "singleFlightTourist", "returnFlightContacts", "returnFlightTourist", "returnFlightPassengerNum", "rf");
    }
}
//事件绑定方法
function bindEvent(){
    $('.redPacketLi .checkbox').click(function(){

        if($(this).hasClass('checked')){
            $(this).removeClass('checked');
            ReturnFlight.changePrice(0, 'go');together();
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
            ReturnFlight.changePrice(0, 'go');together();
        }else{
            promptWarn("金额必须满"+useCondition+"元才能抵用",1500);
        }

    });

    //如果是修改订单则判断是否有使用优惠券，作出相应处理
    //if(!isNull($('#userCouponId').val())){
    //    $('.redPacketLi').each(function(){
    //        if($(this).attr("couponid") == $('#userCouponId').val()){
    //            $(this).find(".checkbox").click();
    //        }
    //    });
    //
    //}

}

