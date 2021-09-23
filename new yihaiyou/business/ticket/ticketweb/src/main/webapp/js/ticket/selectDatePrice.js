/**
 * Created by dy on 2016/3/15.
 */

var DatePrice= {

    init: function() {

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
    ///proManage/productManage/getTicketPriceList.jhtml
    $.post("/ticket/ticket/getTicketPriceList.jhtml", {
        ticketPriceId: $("#ticketPriceId").val(),
        ticketId: $("#ticketId").val()
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

//function clickEvent(calEvent) {
//    var price = calEvent.price;
//    var date = calEvent.date;
//    var rebate = calEvent.rebate;
//
//    var ticketPriceId = $("#ticketPriceId").val();
//    var index = $("#ticketIndex").val();
//
//
//    window.parent.$("#startTime_"+ticketPriceId+"").val(date);
//    window.parent.$("#price_"+ticketPriceId+"").html(price);
//    window.parent.$("#rebate_"+ticketPriceId+"").val(rebate);
//
//
//    var inputValue  = window.parent.$("#count_i"+ticketPriceId+"").attr("temp-value");
//    inputValue = parseFloat(inputValue);
//    //inputValue = Number(inputValue);
//    var totalPrice  = window.parent.$("#ipt_totalPrice").numberbox("getValue");
//
//    window.parent.$("#count_i"+ticketPriceId+"").val("");
//
//
//    if (inputValue) {
//        var resultPrice = totalPrice - (inputValue * price);
//        window.parent.$("#count_i"+ticketPriceId+"").attr("temp-value", 0);
//        window.parent.$("#ipt_totalPrice").numberbox("setValue", resultPrice);
//    }
//    window.parent.$("#sel_startTime").dialog("close");
//    //$(editStartTimeEditor).textbox("setValue", date);
//
//}

//function clickDay(date) {
//    date = date.format();
//    $('#priceCalendar').fullCalendar('clientEvents', function (event) {
//        var ticketPriceId = $("#ticketPriceId").val();
//       var index = $("#ticketIndex").val();
//       if (event.start.format() == date) {
//            var price = event.price;
//            var eventDate = event.date;
//            var rebate = event.rebate;
//
//           window.parent.$("#startTime_"+ticketPriceId+"").val(eventDate);
//           window.parent.$("#price_"+ticketPriceId+"").html(price);
//           window.parent.$("#rebate_"+ticketPriceId+"").val(rebate);
//
//           var inputValue  = window.parent.$("#count_i"+ticketPriceId+"").attr("temp-value");
//           inputValue = parseFloat(inputValue);
//           //inputValue = Number(inputValue);
//           var totalPrice  = window.parent.$("#ipt_totalPrice").numberbox("getValue");
//           window.parent.$("#count_i"+ticketPriceId+"").val("");
//           if (inputValue) {
//               var resultPrice = totalPrice - (inputValue * price);
//               window.parent.$("#count_i"+ticketPriceId+"").attr("temp-value", 0);
//               window.parent.$("#ipt_totalPrice").numberbox("setValue", resultPrice);
//           }
//           window.parent.$("#sel_startTime").dialog("close");
//       }
//
//    });
//}


