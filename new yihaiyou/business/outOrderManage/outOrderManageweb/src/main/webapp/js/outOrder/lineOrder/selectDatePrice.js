/**
 * Created by dy on 2016/3/15.
 */

var DatePrice= {

    init: function() {

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
                    //TicketOrder.changeCost();
                } else {
                    $("#price").val(0);
                    promptMessage("查询价格失败");
                }
            }
        );
    }

}

$(function() {
    DatePrice.init();

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
        eventLimit: true, // allow "more" link when too many events

        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"],
        today: ["今天"],
        aspectRatio: 1.0,

        eventClick: function (calEvent, jsEvent, view) {
            clickEvent(calEvent);
        },
        dayClick: function (date, allDay, jsEvent, view) {
            clickDay(date);
        }
    });
    //
    var data = [];
    ///proManage/productManage/getLineDatePriceList.jhtml

    var typePrice = $("#priceType").val();

    var url = "";

    if (typePrice) {
        url = "/proManage/productManage/getLineDatePriceList.jhtml?type=" + typePrice;
    }

    $.post(url, {
        ticketPriceId: $("#ticketPriceId").val(),
        lineId: $("#lineId").val()
    }, function (result) {
        //
        if (result.length < 1) {
            show_msg("门票价格信息不存在");
            return;
        }
        for (var i = 0; i < result.length; i++) {
            //
            var datePrice = result[i];
            var priceDate = datePrice.huiDate.substring(0, 10);
            data.push({
                id: datePrice.id,
                price: datePrice.priPrice,
                date: priceDate,
                title: '¥' + datePrice.priPrice,
                start: priceDate,
                rebate:datePrice.rebate
            });
        }
        $('#priceCalendar').fullCalendar('removeEvents');
        $('#priceCalendar').fullCalendar('addEventSource', data);
    });



})



function clickEvent(calEvent) {
    var price = calEvent.price;
    var rebate = calEvent.rebate;
    var date = calEvent.date;

    var ticketPriceId = $("#ticketPriceId").val();
    var index = $("#ticketIndex").val();
    var typePrice = $("#priceType").val();
    window.parent.$("#startTime_"+typePrice+"_"+ticketPriceId+"").val(date);
    window.parent.$("#price_"+typePrice+"_"+ticketPriceId+"").html(price);
    window.parent.$("#rebate_"+typePrice+"_"+ticketPriceId+"").val(rebate);

    var inputValue  = window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").attr("temp-value");
    var totalPrice  = window.parent.$("#ipt_totalPrice").numberbox("getValue");

    window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").val("");
    if (inputValue) {
        var resultPrice = totalPrice - (inputValue * price);
        window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").attr("temp-value", 0);
        window.parent.$("#ipt_totalPrice").numberbox("setValue", resultPrice);
    }
    window.parent.$("#sel_startTime").dialog("close");
    //$(editStartTimeEditor).textbox("setValue", date);

}

function clickDay(date) {
    date = date.format();
    $('#priceCalendar').fullCalendar('clientEvents', function (event) {
        var ticketPriceId = $("#ticketPriceId").val();
       var index = $("#ticketIndex").val();
        var typePrice = $("#priceType").val();
       if (event.start.format() == date) {
            var price = event.price;
            var eventDate = event.date;
           var rebate = event.rebate;

           window.parent.$("#startTime_"+typePrice+"_"+ticketPriceId+"").val(eventDate);
           window.parent.$("#price_"+typePrice+"_"+ticketPriceId+"").html(price);
           window.parent.$("#rebate_"+typePrice+"_"+ticketPriceId+"").val(rebate);

           var inputValue  = window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").attr("temp-value");
           var totalPrice  = window.parent.$("#ipt_totalPrice").numberbox("getValue");

           window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").val("");

           if (inputValue) {
               var resultPrice = totalPrice - (inputValue * price);
               window.parent.$("#count_"+typePrice+"_"+ticketPriceId+"").attr("temp-value", 0);
               window.parent.$("#ipt_totalPrice").numberbox("setValue", resultPrice);
           }
           window.parent.$("#sel_startTime").dialog("close");
       }

    });
}


