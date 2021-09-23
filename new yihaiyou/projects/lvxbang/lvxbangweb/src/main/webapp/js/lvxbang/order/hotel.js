/**
 * Created by vacuity on 16/1/6.
 */


var HotelOrder = {
    changePrice: function(num) {
        // 人数改变时改变总金额和游客信息输入框的数量
        Order.changePrice(num, "passengerNum", "totalSinglePrice", "singleFlightSumCost","contacts", "touristList", "rightPanel", "hotel");
    },

    changeDate: function(type) {
        if (type != 1) {
        var lDate = new Date(Date.parse($('#startDate').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#leaveDate').val().replace("-", "/")));

            if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
                lDate.setDate(lDate.getDate() + 1);
                $('#leaveDate').val(lDate.format("yyyy-MM-dd"));
            }

            $('#leaveDate').click();
            $('#leaveDate').focus();
        }

        var startDate = $("#startDate").val();
        var leaveDate = $("#leaveDate").val();
        if(isNull(leaveDate)){
            //$("#leaveDate").focus();
            return;
        }
        var sDate = new Date(startDate.replace(/-/g,   "/"));
        var eDate = new Date(leaveDate.replace(/-/g,   "/"));
        var time = eDate.getTime() - sDate.getTime();
        var days = parseInt(time / (1000 * 60 * 60 * 24));


        var nowDay = parseInt((sDate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));

        if (nowDay < 0) {
            promptWarn("预订日期已过期", 1000);
            return;
        }
        if ( days < 1) {
            promptWarn("开时间必须大于入住时间");
            return;
        }
        $("#hotelDays").text(days + "晚");
        $("#hotelDays").val(days);
        $("#days").val(days);
        var num = Number($("#passengerNum").val());
        //
        $.post("/lvxbang/order/getHotelPriceList.jhtml",
            {
                hotelId: $("#hotelId").val(),
                startDate: startDate,
                leaveDate: leaveDate,
                ratePlanCode: $("#ratePlanCode").val(),
                priceId: $('#priceIds').val(),
                days: days

            }, function (result) {
                if (result.success == true) {
                    $("#singlePrice").text(result.min);
                    $("#totalSinglePrice").val(result.sum);
                    //$("#priceIds").val(result.ids);
                    HotelOrder.changeCost();
                    $("#date1").val(startDate);
                    $("#date2").val(leaveDate);

                } else {
                    //$("#priceIds").val("");
                    promptWarn("查询价格失败");
                    $("#startDate").val($("#date1").val());
                    $("#leaveDate").val($("#date2").val());
                    HotelOrder.changeDate(1);
                }
            }
            ,"json");
    },

    changeCost: function() {
        var price = Number($("#totalSinglePrice").val());
        var num = Number($("#passengerNum").val());
        var days = Number($("#days").val());
        var cost = price * num * days;
        $("#firstCost").text(cost);
        $("#lastCost").text("¥"+cost);
        $("#rightCost").text("¥" + cost);
        $("#rightTotalCost").text("¥" + cost);
    },

    changeByNum: function(cost) {
        //
        cost = Number(cost);
        //cost = cost.toFixed(1);
        $("#firstCost").text(cost);
        $("#lastCost").text("¥"+cost);
        $("#rightCost").text("¥"+cost);
        $("#rightTotalCost").text("¥"+cost);
    },

    fillTourist: function() {
        Order.fillTourist("contacts", "touristList");
    },

    checkHotel: function () {
        var startDate = $("#startDate").val();
        var leaveDate = $("#leaveDate").val();

        if (startDate == "" || leaveDate == "") {
            return "请选择入住日期和离开日期";
        }
        var sDate = new Date(startDate.replace(/-/g,   "/"));
        var eDate = new Date(leaveDate.replace(/-/g,   "/"));
        var time = eDate.getTime() - sDate.getTime();
        var days = parseInt(time / (1000 * 60 * 60 * 24));


        var nowDay = parseInt((sDate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));

        if (nowDay < 0 || days < 1) {
            return "请选择入住日期和离开日期, 不能提交";
        }
        if ($("#priceIds").val() == null && $("#priceIds").val() == "") {
            return "制定日期内存在无法预订酒店，请重新选择日期"
        }
        return "ok";
    },


    createOrderData: function() {
        //
        var orderType = "hotel";
        var priceIds = $("#priceIds").val();
        var orderId = $("#orderId").val();

        var data = {};
        if (orderId != null && orderId != "") {
            data["orderId"] = orderId;
        }
        data["contactsName"] = $("#contactsName").val();
        data["contactsTelphone"] = $("#contactsTel").val();
        data["orderType"] = orderType;
        data["source"] = $('#source').val();
        var detailList = new Array();

        var detail = {};
        detail["id"] = $("#hotelId").val();
        detail["price"] = $("#totalSinglePrice").val();
        detail["priceId"] = $("#priceIds").val();
        detail["startTime"] = $("#startDate").val();
        detail["endTime"] = $("#leaveDate").val();
        detail["num"] = $("#passengerNum").val();
        detail["ratePlanCode"] = $("#ratePlanCode").val();
        detail["type"] = orderType;
        detail["seatType"] = $("#seatType").val();

        var touristList = Order.getTourist("touristList");
        detail["tourist"] = touristList;


        //酒店担保时信用卡信息
        var creditCard = {};
        if($("#hotelPriceStatus").val() == 'GUARANTEE'){
            creditCard['status'] = true;
            creditCard['cardNum'] = $.trim($('#cardNum').val());
            creditCard['cvv'] = $.trim($('#cvv').val());
            creditCard['expirationYear'] = $.trim($('#expirationYear').html().replace(/[^0-9]/ig,""));
            creditCard['expirationMonth'] = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig,""));
            creditCard['holderName'] = $.trim($('#holderName').val());
            var creditCardIdType = $.trim($('#creditCardIdType').html());
            if(creditCardIdType == '身份证'){
                creditCardIdType = 'IdentityCard';
            }else {
                creditCardIdType = 'Passport';
            }
            creditCard['creditCardIdType'] = creditCardIdType;
            creditCard['idNo'] = $.trim($('#idNo').val());

        }else {
            creditCard['status'] = false;
        }
        detail['creditCard'] = creditCard;
        detailList.push(detail);
        data["detail"] = detailList;

        var json = JSON.stringify(data);
        return json;
    },

    submitOrder: function() {

        var str = $.trim($('#contactsName').val());
        var reg =  /^[\u4e00-\u9fa5]+$/;
        if(!str.match(reg)){
            promptWarn("姓名必须是全汉字",1000);
            return;
        }
        var validateMsg = Order.checkValidate();

        //酒店担保时验证信用卡号及填写内容
        var flag = true;
        if($("#hotelPriceStatus").val() == 'GUARANTEE') {

            var cardNum = $.trim($('#cardNum').val());
            var cvv = $.trim($('#cvv').val());
            var holderName = $.trim($('#holderName').val());
            var idNo = $.trim($('#idNo').val());
            var expirationYear = $.trim($('#expirationYear').html().replace(/[^0-9]/ig,""));
            var expirationMonth = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig,""));


            if(isNull(cardNum) || isNull(cvv) || isNull(holderName) || isNull(idNo) ){
                promptWarn("酒店担保信息未填完整",1000);
                return;
            }
            var reg1 = /^[0-9]*$/;
            if (!cardNum.match(reg1)) {
                promptWarn("信用卡号有误",1000);
                return;
            }
            if(isNull(expirationYear) || isNull(expirationMonth)){
                promptWarn("信用卡有效至期未选",1000);
                return;
            }
            if (!idNo.match(reg1)) {
                promptWarn("身份证卡号有误",1000);
                return;
            }
            var reg2 = /^[0-9]{3}$/;
            if(!cvv.match(reg2)){
                promptWarn("信用卡验证码有误",1000);
                return;
            }
            var reg3 = /^[\u4e00-\u9fa5]+$/;
            if(!holderName.match(reg3)){
                promptWarn("持卡人姓名必须是全汉字",1000);
                return;
            }


            $.ajax({
                url: "/lvxbang/order/validateCreditCart.jhtml",
                type: "post",
                async: false,
                dataType: 'json',
                data: {'cardNum': $('#cardNum').val()},
                success: function (data) {
                    if (!data.success) {
                        promptWarn("信用卡号不支持",1000);
                        $('#cardNum').click().focus();
                        flag = false;
                    }else{
                        window.console.log("可以创建订单信息了");
                    }
                },
                error: function () {
                    window.console.log("error");
                }
            });

        }

        if(!flag){
            return;
        }


        if (validateMsg == "ok") {
            var hotelMsg = HotelOrder.checkHotel();
            if (hotelMsg == "ok") {
                //loadingBegin();
                $.post("/lvxbang/order/checkHotelOrder.jhtml", {
                    priceStartDate: $("#startDate").val(),
                    priceEndDate: $("#leaveDate").val(),
                    priceId: $("#priceIds").val(),
                    num: $("#passengerNum").val()
                }, function (data) {
                    if (data.success) {
                        var json = HotelOrder.createOrderData();
                        window.console.log(json);
                        $("#submitFlag").val("invalid");
                        $.post("/lvxbang/order/createOrder.jhtml",
                            {
                                data: json

                            }, function (result) {
                                loadingEnd();
                                if (result.success == true) {
                                    //Order.popMsg("下单成功");
                                    promptMessage("下单成功", 3000);
                                    if ($('#source').val() == 'ELONG') {
                                        promptMessage("预订完成，请查收短信", 1500);
                                        setTimeout(function () {
                                            window.location.href = '/lvxbang/order/hotelOrderDetail.jhtml?orderId=' + result.orderId;
                                        }, 3000);
                                    } else {
                                        var random = parseInt(Math.random() * 10000);
                                        window.location.href = "/lvxbang/lxbPay/request.jhtml?orderId=" + result.orderId + "&random=" + random;
                                    }
                                } else {
                                    $("#submitFlag").val("ok");
                                    promptWarn("下单失败");
                                }
                            }
                            , "json");
                    } else {
                        promptWarn("酒店库存不足");
                    }
                });
            } else {
                promptWarn(hotelMsg);
            }
        } else {
            //
            promptWarn(validateMsg);
        }
    },

    initDate: function () {
        var date = new Date().format("yyyy-MM-dd");
        if (isNull($("#startDate").val())) {
            $("#startDate").val(date);
        }
        var leaveDate = new Date();
        leaveDate.setDate(leaveDate.getDate() + 1);
        var dateString = leaveDate.format("yyyy-MM-dd");
        if(isNull($('#leaveDate').val())) {
            $("#leaveDate").val(dateString);
        }
        HotelOrder.changeDate(1);
    },

    initEdit: function (orderDetailId) {
        $.post("/lvxbang/order/getOrderInfo.jhtml",
            {
                orderDetailId: orderDetailId

            }, function (result) {
                if (result.success == true) {
                    //
                    $("#startDate").val(result.date);
                    $("#leaveDate").val(result.leaveDate);
                    $("#passengerNum").val(result.num);
                    HotelOrder.changeDate();
                    HotelOrder.changePrice(0);
                    var html = "";
                    var hotel_num=1;
                    $.each(result.tourist, function (i, data) {
                        data.hotel_num = hotel_num;
                        html += template("tpl-tourist-list-item", data);
                        hotel_num++;
                    });
                    $("#touristList").html(html);

                    ////下拉事件
                    //$(".sfz .name,.sfz i").click(function () {
                    //    $(this).siblings(".sfzp").show();
                    //});
                    //$(".sfzp a").click(function () {
                    //    var label = $(this).text();
                    //    $(this).parent(".sfzp").hide();
                    //    $(this).parent(".sfzp").siblings(".name").text(label);
                    //});
                } else {
                    //promptMessage("获取订单信息失败");
                }
            }
            ,"json");
    }

}

$(function () {
    //SingleFlight.changePrice();
    HotelOrder.fillTourist();


    var orderDetailId = $("#orderDetailId").val();
    if (orderDetailId != null && orderDetailId != "") {
        HotelOrder.initEdit(orderDetailId);
    } else {
        HotelOrder.initDate();
    }

    //$('#startDate').change(function(){
    //    var lDate = new Date(Date.parse($('#startDate').val().replace("-", "/")));
    //    var rDate = new Date(Date.parse($('#leaveDate').val().replace("-", "/")));
    //    var days = (rDate.getTime() - lDate.getTime()) / (24 * 60 * 60 * 1000)
    //        lDate.setDate(lDate.getDate() + days);
    //        $('#check-out-date').val(lDate.format("yyyy-MM-dd"));
    //    $('#leaveDate').click();
    //    $('#leaveDate').focus();
    //
    //});

    //日历提示按钮
    $('.rili').click(function(){
        $(this).next().focus().click();
    });

    // 下拉事件
    $(".sfz .name,.sfz i").click(function (event) {

        if($(this).siblings(".sfzp").css("display") =='none'){
            $(this).siblings(".sfzp").show();
        }else{
            $(this).siblings(".sfzp").hide();
        }

        // 阻止冒泡
        if (event.stopPropagation) {    // standard
            event.stopPropagation();
        } else {
            // IE6-8
            event.cancelBubble = true;
        }
    });
    $(".sfzp a").click(function () {
        var label = $(this).text();
        $(this).parent(".sfzp").hide();
        $(this).parent(".sfzp").siblings(".name").text(label);
    });

    $("body").click(function(){
       $(".sfz .name").siblings(".sfzp").hide();
    });
});

function changeDate(){
    HotelOrder.changeDate();
};
