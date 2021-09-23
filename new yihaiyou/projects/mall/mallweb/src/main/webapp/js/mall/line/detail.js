var LineDetail = {
    lineId: null,
    init: function () {
        this.lineId = $("#lineId").val();
        $('#calendar').fullCalendar({
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
                $("#startDate").val(date._d.getFullYear() + "-" + LineDetail.lpad(date._d.getMonth()+1) + "-" + LineDetail.lpad(date._d.getDate()));
            },
            eventClick: function (calEvent, jsEvent, view) {
                $("#startDate").val(calEvent.start._i);
                $('#calendar').fullCalendar('select', calEvent.start._i, calEvent.start._i, true)
            }
        });
        this.refreshPriceList();
        $(".info-lx-list").find("a").click(function () {
            $(".info-lx-list").find("a").removeClass("curr");
            $(this).addClass("curr");
            $("#priceId").val( $(this).attr("data-id"));
            LineDetail.refreshPriceList();
        });

        $(".order-count").each(function () {
            var panel = $(this);
            var counter = panel.find(".num");
            panel.find(".minus").click(function () {
                if (parseInt(counter.val()) > 0) {
                    counter.val(parseInt(counter.val()) - 1);
                }
            });
            panel.find(".plus").click(function () {
                counter.val(parseInt(counter.val()) + 1);
            });
        });

    },
    refreshPriceList: function () {
        $.getJSON("/mall/line/getPriceList.jhtml", {"priceDateCondition.id": $(".info-lx-list").find(".curr").attr("data-id")}, function (result) {
            if (result.success) {
                var priceList = result.priceList;
                var html = $("#typePriceItem").render({"price": priceList[0]});
                var zebraEnableDates = [];
                var day = null;
                var month = null;
                var year = null;
                var dateStr = "";
                var fullCalendarDates = [];
                for (var i = 0; i < priceList.length; i++) {
                    var date = new Date(priceList[i].day);
                    if (year != date.getFullYear()) {
                        if (dateStr != "") {
                            dateStr += "-" + day + " " + month + " " + year;
                            zebraEnableDates.push(dateStr);
                        }
                        year = date.getFullYear();
                        month = date.getMonth() + 1;
                        day = date.getDate();
                        dateStr = day;
                    } else if (month != date.getMonth() + 1) {
                        dateStr += "-" + day + " " + month + " " + year;
                        zebraEnableDates.push(dateStr);
                        month = date.getMonth() + 1;
                        day = date.getDate();
                        dateStr = day;
                    } else if (day != date.getDate() - 1) {
                        dateStr += "-" + day + " " + month + " " + year;
                        zebraEnableDates.push(dateStr);
                        day = date.getDate();
                        dateStr = day;
                    } else {
                        day = date.getDate();
                    }
                    fullCalendarDates.push({
                        title: '￥' + priceList[i].discountPrice,
                        discountPrice: priceList[i].discountPrice,
                        start: date.getFullYear() + "-" + LineDetail.lpad((date.getMonth() + 1)) + "-" + LineDetail.lpad(date.getDate())
                    });
                    fullCalendarDates.push({
                        title: '￥' + priceList[i].childPrice,
                        childPrice: priceList[i].childPrice,
                        start: date.getFullYear() + "-" + LineDetail.lpad((date.getMonth() + 1)) + "-" + LineDetail.lpad(date.getDate())
                    });
                }

                $("#price-panel").html(html);
                $("#startDate").val(result.startDate);
                $('#startDate').Zebra_DatePicker({
                    enabled_dates: zebraEnableDates,
                    disabled_dates: ['* * * *'],
                    direction: [true, true],
                    onSelect: function (date, sDate, calendar) {
                        $('#calendar').fullCalendar('gotoDate', calendar.getFullYear()+'-'+(parseInt(calendar.getMonth())+1));
                        $('#calendar').fullCalendar('select', date);
                    }
                });
                $('#calendar').fullCalendar('removeEvents');
                $('#calendar').fullCalendar('addEventSource', fullCalendarDates);
                refreshTable();

            }

        })
    },
    lpad: function (num) {
        if (num < 10) {
            return '0' + num;
        } else {
            return '' + num;
        }
    }

};

LineDetail.init();