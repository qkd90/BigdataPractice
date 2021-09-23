var PlanDetail = {
    plan: {},
    touristList: [],
    map: null,

    init: function () {
        PlanDetail.initMap();
        PlanDetail.bindEvent();
        PlanDetail.initData();
    },

    initData: function () {
        PlanDetail.plan = RightBar.getPlanDetail();
        if (!$.isEmptyObject(PlanDetail.plan)) {
            PlanDetail.initDetailHtml();
        } else {
            var planDemand = RightBar.getPlanDemand();
            var selectedScenic = RightBar.getSelectedScenic();
            var selectList = [];
            for (var id in selectedScenic) {
                selectList.push({id: id, type: 1});
            }
            $.ajax({
                url: "/yhypc/plan/optimize.jhtml",
                data: {
                    planOptimize: JSON.stringify({
                        type: 2,
                        day: planDemand.playDay,
                        hour: planDemand.hour,
                        cityDays: {3502: planDemand.playDay},
                        scenicList: selectList
                    }),
                    startDate: planDemand.playDate,
                    needHotel: planDemand.needHotel,
                    hotelSearch: JSON.stringify(planDemand.hotelSearch)
                },
                progress: true,
                success: function (data) {
                    if (data.success) {
                        PlanDetail.plan = data.plan;
                        $.each(PlanDetail.plan.days, function (i, day) {
                            if (day.needHotel && !$.isEmptyObject(day.hotel)) {
                                day.hotel.priceIds = [day.hotel.priceId];
                            }
                        });
                        PlanDetail.initDetailHtml();
                    }
                },
                dataType: "json"
            });
        }
        PlanDetail.initTouristList();
    },

    initTouristList: function () {
        $.post("/yhypc/user/touristList.jhtml", {}, function (data) {
            if (data.success) {
                PlanDetail.touristList = data.touristList;
                var touristList = $(".edited");
                touristList.empty();
                var html = "";
                $.each(PlanDetail.touristList, function (i, tourist) {
                    tourist.index = i + 1;
                    html += template("tourist_list_item", tourist);
                });
                touristList.append(html);
                PlanDetail.selectTour();
            }
        }, "json");
    },

    initDetailHtml: function () {
        $("#planName").text(PlanDetail.plan.name);
        $("#totalDay").text(PlanDetail.plan.days.length);
        $("#totalScenic").text(PlanDetail.plan.scenicNum);
        $("#totalPrice").text(PlanDetail.plan.price);

        var planDayList = $("#planDayList");
        var simplePlanList = $("#simplePlanList");
        planDayList.empty();
        simplePlanList.empty();
        var planDayListHtml = "";
        var simplePlanListHtml = "";
        $.each(PlanDetail.plan.days, function (i, day) {
            planDayListHtml += template("plan_day_item", day);
            simplePlanListHtml += template("simple_plan_item", day);
            if (day.hotel != null) {
                PlanDetail.addMarker({
                    id: day.hotel.id,
                    name: day.hotel.name,
                    lat: day.hotel.lat,
                    lng: day.hotel.lng
                });
            }
            $.each(day.scenics, function (j, scenic) {
                PlanDetail.addMarker({
                    id: scenic.id,
                    name: scenic.name,
                    lat: scenic.latitude,
                    lng: scenic.longitude
                });
            });
        });
        planDayList.append(planDayListHtml);
        simplePlanList.append(simplePlanListHtml);
    },

    bindEvent: function () {
        PlanDetail.centerBox();
        PlanDetail.tourboxShow();
        PlanDetail.selectCard();
        PlanDetail.selectOne();
        PlanDetail.nextOrder();
        PlanDetail.savePlanEvt();
        PlanDetail.editoPlan();
        PlanDetail.floatBar();
    },

    centerBox: function () {
        var left = (window.screen.availWidth - $('.tourbox').width()) / 2;
        var top = (window.screen.availHeight - $('.tourbox').height()) / 2;
        $('.tourbox').css({'left': left, 'top': top});
    },

    tourboxShow: function () {
        $('#buy').on("click", function () {
            $('.shadowbox').show();
            $('.tourbox').show();
            $('body').css({'overflow': 'hidden'});
        });
        $('#buyshut').on("click", function () {
            $('.shadowbox').hide();
            $('.tourbox').hide();
            $('body').css({'overflow': 'auto'});
        });
    },

    selectTour: function () {
        var this_p = $('.edited p');
        this_p.on("click", function () {
            if ($(this).hasClass('checked')) {
                $(this).removeClass('checked');
            } else {
                $(this).addClass('checked');
            }
        });
    },

    selectCard: function () {
        var cardbox = $('li.card .cardlist');
        $("#addTourIdType").on("click", function (event) {
            event.stopPropagation();
            cardbox.slideDown(100);
        });
        $('.shadowbox').on("click", function () {
            cardbox.slideUp(100);
        });
        $('.tourbox').on("click", function () {
            cardbox.slideUp(100);
        });
    },

    selectOne: function () {
        var this_p = $('.addtour li.card .cardlist p');
        this_p.on("click", function () {
            $('.addtour li.card input').val($(this).text()).data("type", $(this).data("type"));
            $('.addtour li.card .cardlist').slideUp(100);
        });
    },

    nextOrder: function () {
        $("#nextOrder").on("click", function () {
            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    var selectedTourist = [];
                    $('.edited p.checked').each(function () {
                        var id = $(this).data("id");
                        $.each(PlanDetail.touristList, function (i, tourist) {
                            if (tourist.id == id) {
                                selectedTourist.push(tourist);
                                return false;
                            }
                        });
                    });
                    if (selectedTourist.length == 0) {
                        $.message.alert({
                            title: "提示",
                            info: "请选择游客"
                        });
                        return;
                    }
                    RightBar.setSelectedTourist(selectedTourist);
                    var days = [];
                    var ferry = {};
                    var selectedScenic = RightBar.getSelectedScenic();
                    $.each(PlanDetail.plan.days, function (i, day) {
                        if (day.needShip && !$.isEmptyObject(day.ferry)) {
                            ferry = day.ferry;
                        }
                        var scenics = {};
                        $.each(day.scenics, function (j, scenic) {
                            var select = selectedScenic[scenic.id];
                            if (!$.isEmptyObject(select)) {
                                scenics[scenic.id] = select.prices;
                            }
                        });
                        var obj = {
                            day: day.day,
                            scenics: scenics
                        };
                        if (!$.isEmptyObject(day.hotel)) {
                            obj.hotels = day.hotel.priceIds;
                        }
                        days.push(obj);
                    });
                    RightBar.setSelectedFerry(ferry);
                    RightBar.setDaysRequest(days);
                    var planDemand = RightBar.getPlanDemand();
                    planDemand.planName = PlanDetail.plan.name;
                    RightBar.setPlanDemand(planDemand);
                    location.href = "/yhypc/order/orderPlan.jhtml";
                } else {
                    YhyUser.goLogin(function () {
                        location.reload();
                    });
                }
            });
        });
    },
    savePlanEvt: function () {
        $('#savePlanBtn').one('click', function (event) {
            event.stopPropagation();
            $(this).html(null).html("请稍候...");
            PlanDetail.doSavePlan();
        });
    },
    editoPlan: function () {
        $('.suggestman p.yours i').on('click', function (event) {
            event.stopPropagation();
            if ($(this).hasClass('save')) {
                var val = $(this).parent().find('input').val();
                PlanDetail.plan.name = val;
                $(this).parent().find('span').html(val);
                $(this).parent().find('input').hide();
                $(this).parent().find('span').show();
                $(this).removeClass('save');
            } else {
                $(this).addClass('save');
                $(this).parent().find('span').hide();
                $(this).parent().find('input').val(PlanDetail.plan.name).show();
            }
        });
        $('.DIY').on('click', function () {
            $('.DIY .nameinput').hide();
            $('.DIY .namebox').show();
            $('.suggestman p.yours i').removeClass('save');
        });
        $('.suggestman p.yours input').on('click', function (event) {
            event.stopPropagation();
        })
    },
    floatBar: function () {
        var posiTop = $('.finalbox ').position().top;
        var left = $('.finalbox ').position().left - 9;
        $(window).scroll(function () {
            var scrollTop = $(window).scrollTop();
            if (scrollTop > posiTop) {
                $('.finalbox ').css({'position': 'fixed', 'left': left}).addClass('finashadow');
            } else {
                $('.finalbox ').css({'position': 'static',}).removeClass('finashadow');
            }
        });
    },

    doSavePlan: function () {
        $.ajax({
            url: '/yhypc/plan/doSavePlan.jhtml',
            data: {planJson: JSON.stringify(PlanDetail.plan)},
            success: function (result) {
                if (result.success) {
                    $('#savePlanBtn').html(null).html("保存成功!");
                } else {
                    $('#savePlanBtn').html(null).html("请重试!");
                    PlanDetail.savePlanEvt();
                }
            },
            error: function () {
                $('#savePlanBtn').html(null).html("请重试!");
                PlanDetail.savePlanEvt();
            }
        });
    },

    saveTourist: function () {
        var name = $("#addTourName").val();
        var tel = $("#addTourTel").val();
        var idType = $("#addTourIdType").data("type");
        var idNumber = $("#addTourIdNo").val();
        if (name == null || name == "") {
            $.message.alert({
                title: "提示",
                info: "请输入游客姓名"
            });
            return;
        }
        if (!name.match(Reg.nameReg)) {
            $.message.alert({
                title: "提示",
                info: "游客姓名格式错误"
            });
            return;
        }
        if (tel == null || tel == "") {
            $.message.alert({
                title: "提示",
                info: "请输入游客电话"
            });
            return;
        }
        if (!tel.match(Reg.telephoneReg)) {
            $.message.alert({
                title: "提示",
                info: "游客电话格式错误"
            });
            return;
        }
        if (idNumber == null || idNumber == "") {
            $.message.alert({
                title: "提示",
                info: "请输入游客证件号"
            });
            return;
        }
        if (!idNumber.match(Reg.idCardReg)) {
            $.message.alert({
                title: "提示",
                info: "游客证件号格式错误"
            });
            return;
        }
        $.post("/yhypc/personal/doSaveTourist.jhtml", {
            "tourist.name": name,
            "tourist.tel": tel,
            "tourist.idType": idType,
            "tourist.idNumber": idNumber,
            "tourist.peopleType": "ADULT",
            "tourist.gender": "male"
        }, function (data) {
            if (data.success) {
                PlanDetail.initTouristList();
                $("#addTourName").val("");
                $("#addTourTel").val("");
                $("#addTourIdType").val("身份证").data("type", "IDCARD");
                $("#addTourIdNo").val("");
            }
        });
    },

    changeFerry: function (num) {
        PlanDetail.plan.changingFerry = num;
        RightBar.setPlanDetail(PlanDetail.plan);
        location.href = "/yhypc/plan/changeFerry.jhtml";
    },

    changeHotel: function (num) {
        PlanDetail.plan.changingHotel = num;
        RightBar.setPlanDetail(PlanDetail.plan);
        var changingDay = PlanDetail.plan.days[num - 1];
        location.href = "/yhypc/plan/changeHotelList.jhtml?startDate=" + changingDay.startDate + "&endDate=" + changingDay.endDate;
    },

    jumpTo: function (id) {
        $("html,body").animate({scrollTop: $("#" + id).offset().top - 70}, 1000);
    },

    initMap: function () {
        PlanDetail.map = new BMap.Map("map", {enableMapClick: false});
        PlanDetail.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 13);
    },

    addMarker: function (data) {
        var marker = new BMap.Marker(new BMap.Point(data.lng, data.lat));
        PlanDetail.map.addOverlay(marker);
    }
};

$(window).ready(function () {
    PlanDetail.init();
});

