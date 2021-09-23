$(function () {
    $("#calendar").fullCalendar({
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        selectHelper: function () {
        },
        lang: 'zh-cn',
        buttonIcons: true, // show the prev/next text
        buttonText: {prev: '<', next: '>'},
        weekNumbers: false,
        fixedWeekCount: false,
        //editable: true,
        selectable: true,
        eventLimit: true, // allow "more" link when too many events
        select: function () {
        },
        dayClick: function (date, allDay, jsEvent, view) {
            //window.location.href="/mobile/line/detail.jhtml?priceDateId="+
            console.log(view);
        },
        eventClick: function (calEvent, jsEvent, view, date) {
            if (calEvent.id == undefined || calEvent.id == null || calEvent.id == -1 || calEvent.id == "") {
                return false;
            }
            window.location.href = "/mobile/line/detail.jhtml?lineId=" + $("#lineId").val() + "&priceTypeId=" + $("#priceTypeId").val() + "&priceDateId=" + calEvent.id;

        }
    });

    $(".price-type-content").each(function () {
        if ($(this).attr("data-id") == $("#priceTypeId").val()) {
            $(this).addClass("active");
        }
    }).click(function () {
        $(".price-type-content").removeClass("active");
        $(this).addClass("active");
        $("#priceTypeId").val($(this).attr("data-id"));
        changePrice($(this).attr("data-id"));
    });

    changePrice($("#priceTypeId").val());
});

function changePrice(priceTypeId) {
    $.getJSON("/mobile/line/getDate.jhtml", {priceTypeId: priceTypeId}, function (result) {
        var priceList = result;
        var fullCalendarDates = [];
        for (var i = 0; i < priceList.length; i++) {
            var date = new Date(priceList[i].day);
            if (date.getTime() < new Date().getTime()) {
                continue;
            }
            fullCalendarDates.push({
                title: '￥' + priceList[i].discountPrice,
                discountPrice: priceList[i].discountPrice,
                start: date.getFullYear() + "-" + lpad((date.getMonth() + 1)) + "-" + lpad(date.getDate()),
                id: priceList[i].id
            });
            fullCalendarDates.push({
                title: '￥' + priceList[i].childPrice,
                childPrice: priceList[i].childPrice,
                start: date.getFullYear() + "-" + lpad((date.getMonth() + 1)) + "-" + lpad(date.getDate()),
                id: priceList[i].id
            });
        }

        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('addEventSource', fullCalendarDates);

    })
}

function lpad(num) {
    if (num < 10) {
        return '0' + num;
    } else {
        return '' + num;
    }
}