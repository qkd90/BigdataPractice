var PlanDemand = {
    weekdays: ["", "星期一(Mon)", "星期二(Tue)", "星期三(Wed)", "星期四(Thu)", "星期五(Fri)", "星期六(Sat)", "星期日(Sun)"],

    init: function () {
        PlanDemand.initDate();
        PlanDemand.bindEvent();
    },

    initDate: function () {
        var startDate = $("#startDate");
        var now = moment(new Date());
        startDate.val(now.format("YYYY-MM-DD"));
        startDate.next().text(PlanDemand.weekdays[now.format("E")]);
    },

    bindEvent: function () {
        PlanDemand.startDate();
        PlanDemand.numCount('#playNum .sub', '#playNum .add', '#playNum .num', 1, 15);//游玩天数
        PlanDemand.numCount('#adultNum .sub', '#adultNum .add', '#adultNum .num', 1, 15);//出游人数/成人
        PlanDemand.numCount('#childNum .sub', '#childNum .add', '#childNum .num', 0, 15);//出游人数/儿童
        PlanDemand.needHotel();
        PlanDemand.condition('.contain_sleep .price li');//价格限制
        PlanDemand.condition('.contain_sleep .level li');//星级限制
        PlanDemand.condition('.contain_progress .hour li');//星级限制
        PlanDemand.planStyle();
        PlanDemand.next();
    },

    startDate: function () {
        var startDate = $("#startDate");
        if (!startDate.val()) {
            return;
        }
        var start = moment(startDate.val());
        startDate.val(start.format("YYYY-MM-DD"));
        startDate.next().text(PlanDemand.weekdays[start.format("E")]);
    },

    numCount: function (subx, addx, numx, min, max) {
        var sub = $(subx);
        var add = $(addx);
        var num = $(numx);
        var text = parseInt(num.html());
        add.on("click", function () {
            if (text == max - 1) {
                text++;
                num.html(text);
            }
            if (text >= max) {
                num.removeClass('num_gray');
            } else {
                text++;
                num.html(text);
                num.addClass('num_gray');
            }
        });
        sub.on("click", function () {
            if (text == min + 1) {
                text--;
                num.html(text);
            }
            if (text <= min) {
                num.removeClass('num_gray');
            } else {
                text--;
                num.html(text);
                num.addClass('num_gray');
            }
        });
    },

    needHotel: function () {
        $('.contain_sleep .needHotel li').on("click", function () {
            $(this).addClass('li_selected').siblings().removeClass('li_selected');
            var div = $(".contain_sleep .price, .contain_sleep .level");
            if ($(this).data("need-hotel") == 1) {
                div.slideDown();
                $(".ademand .contain_sleep").animate({height: 115});
            } else {
                div.slideUp();
                $(".ademand .contain_sleep").animate({height: 20});
            }
        });
    },

    condition: function (li) {
        var Li = $(li);
        Li.on("click", function () {
            $(this).addClass('li_selected').siblings().removeClass('li_selected');
        })
    },

    //行程安排
    planStyle: function () {
        var Li = $('.contain_progress .price li');
        Li.on("click", function () {
            $(this).addClass('li_selected');
            $(this).siblings().removeClass('li_selected');
        })
    },

    next: function () {
        $("#next").on("click", function () {
            var startDate = $("#startDate").val();
            var playDay = parseInt($("#playNum .num").text());
            var adultNum = parseInt($("#adultNum .num").text());
            var childNum = parseInt($("#childNum .num").text());
            var needHotel = parseInt($(".contain_sleep .needHotel li.li_selected").data("need-hotel"));
            var minPrice = parseInt($(".contain_sleep .price li.li_selected").data("price-min"));
            var maxPrice = parseInt($(".contain_sleep .price li.li_selected").data("price-max"));
            var star = parseInt($(".level li.li_selected").data("star"));
            var hour = parseInt($(".contain_progress .hour li.li_selected").data("hour"));
            var planDemand = {
                playDate: startDate,
                playDay: playDay,
                adultNum: adultNum,
                childNum: childNum,
                needHotel: needHotel == 1,
                hour: hour,
                hotelSearch: {
                    priceRange: [minPrice, maxPrice],
                    star: star
                }
            };
            RightBar.setPlanDemand(planDemand);
            RightBar.delPlanDetail();
            RightBar.delSelectedScenic();
            RightBar.delSelectedFerry();
            RightBar.delSelectedTourist();
            RightBar.delDaysRequest();
            location.href = "/yhypc/plan/scenicList.jhtml";
        });
    }
};

$(window).ready(function () {
    PlanDemand.init();
});


