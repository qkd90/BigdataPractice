var Line = {
    weekString: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
    detailString: ["cpts", "xcjs", "fysm", "ydxz", "xgcp"],
    init: function () {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            weekMode: "fixed",
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
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"],
            today: ["今天"],
            dayClick: function (date, allDay, jsEvent, view) {
                Line.changeDate(date);
            },
            eventClick: function (calEvent, jsEvent, view) {
                Line.eventClick(calEvent);
            }
        });

        $(".price_type span").click(function () {
            if ($(this).hasClass("checked")) {
                return;
            }
            $(".fysm_div").hide();
            $("#fysm" + $(this).data("id")).show();
            $(".price_type").find(".checked").removeClass("checked");
            $(this).addClass("checked");
            $(".select_result").text("请选择出发日期").addClass("grey");
            $(".supportChild, .child_describe").show();
            Line.lineDatePrice();
        });

        Line.detailNav();
        Line.lineDatePrice();
        Line.history();
        Line.bindEvent();
        collect(".line_collect", true, "#collectNum");
    },
    changeDate: function(date) {
        date = date.format();
        $("#selectDepartDate .select_list li[data-date=" + date + "]").click();
    },
    eventClick: function (calEvent) {
        $("#selectDepartDate .select_list li[data-date=" + calEvent.date + "]").click();
    },
    lineDatePrice: function () {
        var typeId = $(".price_type span.checked").data("id");
        $("#selectDepartDate .select_list").empty();
        $.post("/lvxbang/order/getLineDatePriceList.jhtml", {
            linetypepriceId: typeId
        }, function (result) {
            var data = [];
            var deadLine = "";
            //var min = 1000000;
            for (var i = 0; i < result.length; i++) {
                //
                var datePrice = result[i];
                var priceDate = datePrice.day.substring(0, 10);
                data.push({
                    discountPrice: datePrice.discountPrice + datePrice.rebate,
                    marketPrice: datePrice.marketPrice,
                    date: priceDate,
                    title: '¥' + (datePrice.discountPrice + datePrice.rebate) + "起",
                    hasChild: datePrice.childPrice > 0
                });
                var date = new Date(datePrice.day);
                datePrice.showDate = date.format("MM-dd");
                datePrice.showWeek = Line.weekString[date.getDay()];
                datePrice.day = date.format("yyyy-MM-dd");
                var html = template("tpl-select-date-item", datePrice);
                $("#selectDepartDate .select_list").append(html);
                var preOrderDay = Number($("#preOrderDay").val());
                date.setDate(date.getDate() - preOrderDay);
                if (i > 0 && i % 2 == 0) {
                    deadLine += "<br/>";
                }
                deadLine += priceDate + "出发团期，" + date.format("yyyy-MM-dd") + " " + $("#suggestOrderHour").val() +":00:00截止报名； ";
                //if (min > datePrice.discountPrice) {
                //    min = datePrice.discountPrice;
                //}
            }
            $("#deadlineDate").html(deadLine);
            if (result.length > 4) {
                $("#cdate_more").removeClass("hide");
            } else {
                $("#cdate_more").addClass("hide");
            }
            //Line.changePrice(data[0]);
            $('#calendar').fullCalendar('removeEvents');
            $('#calendar').fullCalendar('addEventSource', data);
        },"json");
    },
    bindEvent: function () {
        //图片
        $(".mailTab2 li").click(function () {
            $(this).addClass("checked").siblings().removeClass("checked");
            $(this).closest("div").find(".mailTablePlan2").eq($(this).index()).show().siblings(".mailTablePlan2").hide();
        });

        //儿童标准
        $(".acerbity").hover(function () {
            $(".adjutant_hover").show();
        }, function() {
            $(".adjutant_hover").hide();
        });

        //产品经理推荐
        var recomboxHeight = $("#recombox").height();
        if ($("#recomInnerBox").height() > recomboxHeight) {
            $(".showallbtn").show();
            $("#recombox").hover(function () {
                $(this).css("height", "auto");
            }, function () {
                $(this).css("height", recomboxHeight + "px");
            });
        }

        //出游日期
        $("#selectDepartDate .select_list").delegate("li", "click", function () {
            $("#selectDepartDate .select_result").text($(this).text()).data("date", $(this).data("date")).removeClass("grey");
            $("#selectDepartDate").removeClass("select_hover");
            if (Number($(this).data("child-price")) > 0) {
                $(".supportChild, .child_describe").show();
            } else {
                $(".supportChild, .child_describe").hide();
            }
        });
        $("#selectDepartDate").hover(function () {
            $(this).addClass("select_hover");
        }, function () {
            $(this).removeClass("select_hover");
        });

        $("#selectDepartCity").hover(function () {
            $(this).addClass("select_hover");
        }, function () {
            $(this).removeClass("select_hover");
        });

        //出游人数
        $("#adA-le").click(function () {
            var num = Number($("#adultN").val());
            num--;
            if (num <= 1) {
                $("#adultN").val(1);
                $("#adA-le").removeClass("on");
            } else {
                $("#adultN").val(num);
            }
        });

        $("#adA-ri").click(function () {
            var num = Number($("#adultN").val());
            num++;
            if (num < 2) {
                $("#adultN").val(2);
            } else {
                $("#adultN").val(num);
            }
            $("#adA-le").addClass("on");
        });

        $("#adC-le").click(function () {
            var num = Number($("#childN").val());
            num--;
            if (num <= 0) {
                $("#childN").val(0);
                $("#adC-le").removeClass("on");
            } else {
                $("#childN").val(num);
            }
        });

        $("#adC-ri").click(function () {
            var num = Number($("#childN").val());
            num++;
            if (num < 1) {
                $("#childN").val(1);
            } else {
                $("#childN").val(num);
            }
            $("#adC-le").addClass("on");
        });

        //预订
        $("#yuding, #now_yuding").click(function () {
            if (!has_no_User(toOrder)) {
                toOrder();
            }
        });

        function toOrder() {
            if ($("#selectDepartDate .select_result").text() == "请选择出发日期") {
                promptWarn("请选择出游日期！");
                return;
            }
            var form = $("#toOrder");
            var linetypepriceId = $(".price_type span.checked").data("id");
            var lineStartDate = $("#selectDepartDate .select_result").data("date");
            var adultNum = $("#adultN").val();
            var childNum = $("#childN").val();
            if (adultNum < 1) {
                promptWarn("请选择人数");
                return;
            }
            $.post("/lvxbang/order/checkLineOrder.jhtml", {
                linetypepriceId: linetypepriceId,
                lineStartDate: lineStartDate,
                adultNum: adultNum
            }, function (data) {
                if (data.success) {
                    form.find("[name=linetypepriceId]").val(linetypepriceId);
                    form.find("[name=lineStartDate]").val(lineStartDate);
                    form.find("[name=adultNum]").val(adultNum);
                    form.find("[name=childNum]").val(childNum);
                    form.submit();
                } else {
                    promptWarn("线路库存不足");
                }
            });
        }

        //行程天数
        $("#tripall .daybox").click(function () {
            $(this).addClass("current").siblings().removeClass("current");
            var height = $("#" + $(this).attr("rel")).offset().top - 50;
            $('html,body').animate({"scrollTop": height}, 1000);
        });

        //报名截止日期
        $("#cdate_more").click(function () {
            if ($(this).hasClass("open")) {
                $(this).removeClass("open").text("查看更多");
                $("#deadlineDate").css("height", "50px");
            } else {
                $(this).addClass("open").text("收起");
                $("#deadlineDate").css("height", "auto");
            }
        });
    },
    history: function () {
        $.post("/lvxbang/line/history.jhtml", function (data) {
            $.each(data.data, function (i, line) {
                line.index = i + 1;
                var html = template("tpl-history-item", line);
                $("#routeViewHistory ul").append(html);
            });
            if (data.data.length > 0) {
                $("#routeViewHistory").css("display", "block");
            }
        });
    },
    detailNav: function () {
        $(window).bind("scroll", function () {
            var scrollTop = $(window).scrollTop();
            var navTop = $("#general_infor").offset().top;
            if (scrollTop > navTop) {
                $("#pkg-detail-wrap").css("position", "fixed");
            } else {
                $("#pkg-detail-wrap").css("position", "static");
            }
            for (var i = Line.detailString.length - 1; i >= 0; i--) {
                if ($("#" + Line.detailString[i]).length > 0 && scrollTop > $("#" + Line.detailString[i]).offset().top - 100) {
                    $("#pkg-detail-tab-bd .current").removeClass("current");
                    $("#pkg-detail-tab-bd li[rel=" + Line.detailString[i] + "]").addClass("current");
                    break;
                }
            }
            var dayTop = $("#day1").offset().top - 100;
            var dayBottom = $("#fysm").offset().top - 230;
            if (scrollTop > dayTop && scrollTop < dayBottom) {
                var dayNum = $("#tripall .daybox").length;
                for (var i = dayNum; i > 0; i--) {
                    if (scrollTop >= $("#day" + i).offset().top - 100) {
                        $("#tripall .daybox[rel=day" + i + "]").addClass("current").siblings().removeClass("current");
                        break;
                    }
                }
                $("#tripall .sidebarPrv").removeClass("outfix").addClass("infix");
            } else {
                $("#tripall .sidebarPrv").removeClass("infix").addClass("outfix");
            }
        });
        $("#pkg-detail-tab-bd li").click(function () {
            $(this).addClass("current").siblings().removeClass("current");
            var height = $("#" + $(this).attr("rel")).offset().top - 50;
            $('html,body').animate({"scrollTop": height}, 1000);
        });
    }
};

$(document).ready(function () {
   Line.init();
});