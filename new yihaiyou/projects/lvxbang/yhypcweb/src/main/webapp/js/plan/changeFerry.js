/**
 * Created by HMLY on 2016-12-20.
 */

var ChangeFerry = {
    pager: null,
    date: "",

    init: function () {
        ChangeFerry.initDate();
        ChangeFerry.bindEvent();
    },

    initDate: function () {
        var planDetail = RightBar.getPlanDetail();
        if ($.isEmptyObject(planDetail)) {
            location.href = "/yhypc/plan/detail.jhtml";
        }
        var changingDay = planDetail.days[planDetail.changingFerry - 1];
        ChangeFerry.date = changingDay.startDate;
        var flightNumber = $("#flight-number");
        flightNumber.val(changingDay.ferry.line.number);
        $("#routeDatalist li").each(function (i, perRoute) {
            if ($(perRoute).data("number") == changingDay.ferry.line.number) {
                $("#routeInputBtn").val($(perRoute).text()).data("line", {
                    number: $(perRoute).data("number"),
                    name: $(perRoute).text(),
                    departProt: $(perRoute).data("departPort"),
                    arrivePort: $(perRoute).data("arrivePort")
                });
            }
        });

        ChangeFerry.createPager();
        ChangeFerry.getFerryList();
    },

    bindEvent: function () {
        ChangeFerry.changeLine();
    },

    changeLine: function () {
        var routeDatalist = $("#routeDatalist");
        $("#routeInputBtn").click(function (ev) {
            var ev = ev || event;
            ev.stopPropagation();
            if (routeDatalist.is(":hidden")) {
                routeDatalist.slideDown("800");
            } else {
                routeDatalist.slideUp("800");
            }
        });
        routeDatalist.find("li").click(function () {
            var inputVal = $(this).html();
            var flightLineId = $(this).data("number");
            $("#routeInputBtn").val(inputVal).data("line", {
                number: flightLineId,
                name: inputVal,
                departProt: $(this).data("departPort"),
                arrivePort: $(this).data("arrivePort")
            });
            $("#flight-number").val(flightLineId);
            routeDatalist.slideUp();
            ChangeFerry.getFerryList();
        });
        $("body").click(function () {
            routeDatalist.slideUp();
        });
    },

    createPager: function () {
        var options = {
            countUrl: "/yhypc/ferry/getTotalFerryPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                $('.tablelist').empty();
                //$("#loading").show();
                scroll(0, 0);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.post("/yhypc/ferry/ferryList.jhtml",
                    data,
                    function (data) {
                        $('.tablelist').empty();
                        //$("#loading").hide();
                        //$("#totalProduct").html(data.page.totalCount);
                        if (data.success) {
                            if (data.flightList.length > 0) {
                                $(".none-data").addClass("hidden");
                            } else {
                                $(".none-data").removeClass("hidden");
                                return;
                            }
                            for (var i = 0; i < data.flightList.length; i++) {
                                var s = data.flightList[i];
                                s.index = i + 1;
                                s.json = JSON.stringify(s);
                                var result = $(template("tpl-ferry-list-item", s));
                                $('.tablelist').append(result);
                                result.data("ferry", s);
                            }
                        }
                    }
                    , "json");
            }
        };
        ChangeFerry.pager = new Pager(options);
    },

    getFerryList: function () {
        var search = {};
        search['date'] = ChangeFerry.date;
        var flightNumber = $("#flight-number").val();
        if (flightNumber) {
            search['flightLineId'] = flightNumber;
        }
        ChangeFerry.pager.init(search);
    },

    changeFerry: function (json) {
        var ferry = JSON.parse(json);
        ferry.line = $("#routeInputBtn").data("line");
        var planDetail = RightBar.getPlanDetail();
        planDetail.days[planDetail.changingFerry - 1].ferry = ferry;
        delete planDetail.changingFerry;
        RightBar.setPlanDetail(planDetail);
        location.href = "/yhypc/plan/detail.jhtml";
    }
};

$(function () {
    ChangeFerry.init();
});