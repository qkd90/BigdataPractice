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
    ///proManage/productManage/getLineDatePriceList.jhtml


    var url = "/hotel/hotel/getDatePriceList.jhtml";

    $.post(url, {
        typePriceId: $("#ticketPriceId").val()
    }, function (result) {
        //
        if (result.length < 1) {
            show_msg("房型价格信息不存在");
            return;
        }
        for (var i = 0; i < result.length; i++) {
            //
            var datePrice = result[i];
            var priceDate = datePrice.date.substring(0, 10);
            data.push({
                id: datePrice.id,
                date: priceDate,
                title: '¥' + datePrice.cost
            });
        }
        $('#priceCalendar').fullCalendar('removeEvents');
        $('#priceCalendar').fullCalendar('addEventSource', data);
    });
});








