/**
 * Created by vacuity on 16/1/7.
 */

var TicketOrder = {
    //
    changePrice: function (num) {
        // 人数改变时改变总金额和游客信息输入框的数量
        Order.changePrice(num, "passengerNum", "price", "singleSumCost","contacts", "touristList", "rightPanel", "ticket");
    },

    fillTourist: function() {
        Order.fillTourist("contacts", "touristList");
    },

    totalCost: function(cost) {
        cost = Number(cost);
        $("#rightCost").text("¥" + cost);
        if($('.redPacketLi .checked').length>0){
            //cost = cost.toFixed(1);
            var li = $('.redPacketLi .checked').parents('li');
            var useCondition = Number(li.attr('useCondition'));
            if(cost >= useCondition){
                cost = cost - Number(li.attr("faceValue"));
            }else{
                $('.redPacketLi .checked').removeClass('checked');
            }
        }
        $("#sumCost").text("¥"+cost);
        $("#rightTotalCost").text("¥" + cost);
    },

    changeCost: function() {
        var num = Number($("#passengerNum").val());
        var price = Number($("#price").val());
        var cost = num * price;
        $("#rightCost").text("¥" + cost);
        $("#singleSumCost").text("¥"+cost);

        if($('.redPacketLi .checked').length>0){
            //cost = cost.toFixed(1);
            var li = $('.redPacketLi .checked').parents('li');
            var useCondition = Number(li.attr('useCondition'));
            if(cost >= useCondition){
                cost = cost - Number(li.attr("faceValue"));
            }else{
                $('.redPacketLi .checked').removeClass('checked');
            }
        }
        $("#rightTotalCost").text("¥" + cost);
        $("#sumCost").text("¥"+cost);
    },


    changeDate: function() {
        var ticketPriceId = $("#ticketPriceId").val();
        var date = $("#ticketDate").val();


        //
        $.post("/lvxbang/order/getTicketDatePrice.jhtml",
            {
                ticketDate: date,
                ticketPriceId: ticketPriceId

            }, function (result) {
                if (result.success == true) {
                    $("#singlePrice").text(result.price);
                    $("#price").val(result.price);
                    TicketOrder.changeCost();

                    $('.shiyong').click().removeClass("shiyong");
                } else {
                    $("#price").val(0);
                    promptWarn("查询价格失败");
                }
            }
            ,"json");
    },

    initDate: function() {
        var dateString = $("#default-date").val();
        $("#ticketDate").val(dateString);
    },

    checkTicket: function () {
        var ticketDate = $("#ticketDate").val();

        if (ticketDate == "") {
            return "请选择游玩日期";
        }

        if ($("#price").val() == 0) {
            return "所选日期内门票价格不存在，请重新选择日期";
        }
        return "ok";
    },

    createOrderData: function() {
        //
        var orderType = "ticket";

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
        detail["id"] = $("#ticketId").val();
        detail["priceId"] = $("#ticketPriceId").val();
        detail["price"] = $("#price").val();
        detail["startTime"] = $("#ticketDate").val();
        detail["endTime"] = $("#ticketDate").val();
        detail["num"] = $("#passengerNum").val();
        detail["type"] = "scenic";
        detail["seatType"] = "成人票";
        var touristList = Order.getTourist("touristList");
        detail["tourist"] = touristList;
        detailList.push(detail);
        data["detail"] = detailList;

        var json = JSON.stringify(data);
        return json;
    },

    submitOrder: function() {
        var validateMsg = Order.checkValidate();
        if (validateMsg == "ok") {
            var ticketMsg = TicketOrder.checkTicket();
            if (ticketMsg == "ok") {
                loadingBegin();
                $.post("/lvxbang/order/checkTicketOrder.jhtml", {
                    ticketPriceId: $("#ticketPriceId").val(),
                    num: $("#passengerNum").val(),
                    ticketDate: $("#ticketDate").val()
                }, function (data) {
                    if (data.success) {
                        var json = TicketOrder.createOrderData();
                        var userCouponId = $('.redPacketLi .checked').parents('li').attr('couponid');
                        $("#submitFlag").val("invalid");
                        $.post("/lvxbang/order/createOrder.jhtml",
                            {
                                data: json,
                                userCouponId: userCouponId
                            }, function (result) {
                                loadingEnd();
                                if (result.success == true) {
                                    //Order.popMsg("下单成功");
                                    promptMessage("下单成功", 3000);
                                    var random = parseInt(Math.random() * 10000);
                                    window.location.href = "/lvxbang/lxbPay/request.jhtml?orderId=" + result.orderId + "&random=" + random;
                                } else {
                                    $("#submitFlag").val("ok");
                                    promptWarn("下单失败");
                                }
                            }
                            , "json");
                    } else {
                        loadingEnd();
                        promptWarn("门票库存不足");
                    }
                });
            } else {
                promptWarn(ticketMsg);
            }
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
                    $("#passengerNum").val(result.num);
                    $("#ticketDate").val(result.date);
                    TicketOrder.changePrice(0);
                    TicketOrder.changeDate();
                    var html = "";
                    var ticket_num = 1;
                    $.each(result.tourist, function (i, data) {
                        data.ticket_num = ticket_num;
                        html += template("tpl-tourist-list-item", data);
                        ticket_num++;
                    });
                    $("#touristList").html(html);

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
    TicketOrder.fillTourist();

    var orderDetailId = $("#orderDetailId").val();
    if (orderDetailId != null && orderDetailId != "") {
        TicketOrder.initEdit(orderDetailId);
    } else {
        TicketOrder.initDate();
    }
    testCalendar();
    bindEvent();
});

function changeDate(){
    TicketOrder.changeDate();
};

function testCalendar() {

    var defaultDate = new Date();
    // 日历
    $('#priceCalendar').fullCalendar({
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        theme: true,
        defaultDate: defaultDate,
        lang: 'zh-cn',
        buttonIcons: false, // show the prev/next text
        weekNumbers: false,
        fixedWeekCount: false,
        editable: true,
        //selectable: true,
        eventLimit: true, // allow "more" link when too many events
        //events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1'

        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"],
        today: ["今天"],
        //height: 225,
        //contentHeight: 300,
        aspectRatio: 1.0,
        eventClick: function (calEvent, jsEvent, view) {
            eventClick(calEvent);
        },
        dayClick: function (date, allDay, jsEvent, view) {
            changeDate(date);
        }
    });
    //
    var data = [];
    $.post("/lvxbang/order/getTicketPriceList.jhtml", {
        ticketPriceId: $("#ticketPriceId").val()
    }, function (result) {
        //
        if (result.length < 1) {
            promptWarn("门票价格信息不存在");
            disalbeCalendar();
            return;
        }
        for (var i = 0; i < result.length; i++) {
            //
            var datePrice = result[i];
            var priceDate = datePrice.huiDate.substring(0, 10);
            data.push({
                id: datePrice.id,
                price: datePrice.priPrice + datePrice.rebate,
                date: priceDate,
                title: '¥' + (datePrice.priPrice + datePrice.rebate),
                start: priceDate
            });
        }
        //data.push({id:11,discountPrice:1,rebate:2,title:'成人'+1+"("+2+")",start:'2016-03-22'});

        var filter = function (event) {
            return tempId.indexOf(event._id) > -1;
        };
        $('#priceCalendar').fullCalendar('removeEvents', filter);
        $('#priceCalendar').fullCalendar('addEventSource', data);
        //disalbeCalendar();
    },"json");

}

// 点击有价格的日历
function changeDate(date) {

    date = date.format();
    $('#priceCalendar').fullCalendar('clientEvents', function (event) {
        if (event.start.format() == date) {
            var price = Number(event.price);
            var eventDate = event.date;
            $("#ticketDate").val(eventDate);

            //price = price.toFixed(1);
            $("#singlePrice").text(price);
            $("#price").val(price);
            TicketOrder.changeCost();
            disalbeCalendar();
        }
    });


}

function eventClick(calEvent) {
    var price = Number(calEvent.price);
    var date = calEvent.date;
    $("#ticketDate").val(date);

    //price = price.toFixed(1);
    $("#singlePrice").text(price);
    $("#price").val(price);
    TicketOrder.changeCost();
    disalbeCalendar();
}

// 显示日历
function enalbeCalendar() {
    $("#priceCalendar").removeClass("visibility-hidden");
}

// 隐藏日历
function disalbeCalendar() {
    $("#priceCalendar").addClass("visibility-hidden");
}

// 当时间框和日历失去焦点后隐藏日历
function changeFocus() {
    var focusE = document.activeElement;
    if (focusE.id != "ticketDate" && focusE.className.indexOf("ui-state-default") < 0) {
        disalbeCalendar();
    }
}
//事件绑定方法
function bindEvent(){
    $('.redPacketLi .checkbox').click(function(){

        if($(this).hasClass('checked')){
            $(this).removeClass('checked');
            TicketOrder.changePrice(0);
            return;
        }
        var li = $(this).parents('li');
        var checkedLi = $(this).parents('ul').find('.checked').parents('li');

        var useCondition = Number(li.attr('useCondition'));
        var faceValue = Number(checkedLi.attr('faceValue'));
        if(isNaN(faceValue)){
            faceValue = 0;
        }
        var rightTotalCost = Number($.trim($('#rightTotalCost').html().replace("¥","")));
        if ((faceValue + rightTotalCost) >= useCondition) {
            $(this).parents('ul').find('.checked').removeClass('checked');
            $(this).addClass('checked');
            TicketOrder.changePrice(0);
        }else{
            promptWarn("金额必须满"+useCondition+"元才能抵用",1500);
        }


    });
}