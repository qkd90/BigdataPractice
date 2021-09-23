var PlanOrder = {
    order: {},
    ferryTicket: [],
    ferry: {},

    init: function () {
        PlanOrder.initDate();
    },

    initDate: function () {
        var planDemand = RightBar.getPlanDemand();
        var daysRequest = RightBar.getDaysRequest();
        var selectedTourist = RightBar.getSelectedTourist();
        PlanOrder.ferry = RightBar.getSelectedFerry();
        if (!$.isEmptyObject(PlanOrder.ferry)) {
            PlanOrder.ferry.touristList = [];
            var totalPrice = 0;
            $.each(selectedTourist, function (j, tourist) {
                var tou = {
                    name: tourist.name,
                    idType: tourist.idType,
                    peopleType: tourist.peopleType,
                    idNumber: tourist.idNumber,
                    tel: tourist.tel
                };
                var ticNum;
                if (tou.peopleType == "KID") {
                    ticNum = "E";
                } else {
                    ticNum = "Q";
                }
                $.each(PlanOrder.ferry.ticketLst.Ticket, function (i, ticket) {
                    if ($.inArray(ticket.id, PlanOrder.ferryTicket) == -1) {
                        PlanOrder.ferryTicket.push(ticket.id);
                    }
                    if (ticket.num == null || isNaN(ticket.num)) {
                        ticket.num = 0;
                    }
                    if (ticket.number.indexOf(ticNum) > -1) {
                        tou.ticket = ticket;
                        ticket.num++;
                    }
                });
                totalPrice += parseFloat(tou.ticket.price);
                PlanOrder.ferry.touristList.push(tou);
            });
            PlanOrder.ferry.totalPrice = totalPrice;
            var ferryHtml = template("ferry_item", PlanOrder.ferry);
            var simpleFerryHtml = template("simple_ferry_item", PlanOrder.ferry);
            $("#ferry").append(ferryHtml);
            $("#simpleFerry").append(simpleFerryHtml);
        } else {
            $("#ferry").parents(".a_contain").hide();
            $("#simpleFerry").hide().prev().hide();
        }
        $.ajax({
            url: "/yhypc/plan/orderNoPlan.jhtml",
            data: {
                json: JSON.stringify({
                    startDate: planDemand.playDate,
                    days: daysRequest
                })
            },
            progress: true,
            success: function (data) {
                if (data.success) {
                    var orderPrice = 0;
                    var scenicHtml = "";
                    var simpleScenicHtml = "";
                    $.each(data.order.scenics, function (i, scenic) {
                        scenic.touristList = selectedTourist;
                        var totalPrice = 0;
                        $.each(scenic.tickets, function (j, ticket) {
                            ticket.selectedNum = scenic.touristList.length;
                            totalPrice += ticket.price * ticket.selectedNum;
                        });
                        scenic.totalPrice = totalPrice;
                        orderPrice += scenic.totalPrice;
                        scenicHtml += template("scenic_list_item", scenic);
                        simpleScenicHtml += template("simple_scenic_item", scenic);
                    });
                    if (scenicHtml == "") {
                        $("#scenicList").parents(".a_contain").hide();
                        $("#simpleScenicList").hide().prev().hide();
                    } else {
                        $("#scenicList").append(scenicHtml);
                        $("#simpleScenicList").append(simpleScenicHtml);
                    }
                    var hotelHtml = "";
                    var simpleHotelHtml = "";
                    $.each(data.order.hotels, function (i, hotel) {
                        hotel.touristList = selectedTourist;
                        var totalPrice = 0;
                        $.each(hotel.hotelPrices, function (j, price) {
                            price.selectedNum = hotel.touristList.length;
                            totalPrice += price.price * price.selectedNum;
                        });
                        hotel.totalPrice = totalPrice;
                        orderPrice += hotel.totalPrice;
                        hotelHtml += template("hotel_list_item", hotel);
                        simpleHotelHtml += template("simple_hotel_item", hotel);
                    });
                    if (hotelHtml == "") {
                        $("#hotelList").parents(".a_contain").hide();
                        $("#simpleHotelList").hide().prev().hide();
                    } else {
                        $("#hotelList").append(hotelHtml);
                        $("#simpleHotelList").append(simpleHotelHtml);
                    }
                    PlanOrder.order = data.order;
                    if (!$.isEmptyObject(PlanOrder.ferry)) {
                        PlanOrder.order.ferry = PlanOrder.ferry;
                        orderPrice += PlanOrder.order.ferry.totalPrice;
                    }
                    PlanOrder.order.totalPrice = orderPrice;
                    $("#totalPrice").text(PlanOrder.order.totalPrice);
                    PlanOrder.bindEvent();
                }
            },
            dataType: "json"
        });
    },

    changeCost: function () {
        var orderPrice = 0;
        var ferryPrice = 0;
        var ferryPriceEle = $("#ferry .pro_contain .ferryPrice");
        $("#ferry .pro_contain .takeit").each(function () {
            ferryPrice += parseFloat($(this).next(".tickettype").children("label").data("price"));
        });
        ferryPriceEle.text(ferryPrice).data("price", ferryPrice);
        if ($("#ferry .pro_contain").hasClass("g_blue")) {
            orderPrice += ferryPrice;
        }

        var hotelPrice = 0;
        $("#hotelList .pro_contain").each(function () {
            var thisPrice = 0;
            $(this).find(".numbox .num").each(function () {
                thisPrice += parseFloat($(this).data("price")) * parseFloat($(this).data("num"));
            });
            $(this).find(".hotelPrice").text(thisPrice).data("price", thisPrice);
            if ($(this).hasClass("g_blue")) {
                hotelPrice += thisPrice;
            }
        });

        var scenicPrice = 0;
        $("#scenicList .pro_contain").each(function () {
            var thisPrice = 0;
            $(this).find(".numbox .num").each(function () {
                thisPrice += parseFloat($(this).data("price")) * parseFloat($(this).data("num"));
            });
            $(this).find(".scenicPrice").text(thisPrice).data("price", thisPrice);
            if ($(this).hasClass("g_blue")) {
                scenicPrice += thisPrice;
            }
        });

        orderPrice += hotelPrice + scenicPrice;
        $("#totalPrice").text(orderPrice).data("price", orderPrice);
    },

    simpleList: function () {
        var ferryNum = {};
        $.each(PlanOrder.ferryTicket, function (i, id) {
            ferryNum[id] = 0;
        });
        $("#ferry .pro_contain.g_blue .takeit").each(function () {
            var id = $(this).next(".tickettype").children("label").data("id");
            var num = ferryNum[id];
            num++;
            ferryNum[id] = num;
        });
        for (var id in ferryNum) {
            var num = ferryNum[id];
            var label = $("#ferryTicket_" + id + " .selectedNum");
            label.text(num);
            var parent = label.parents(".thir");
            if (num == 0) {
                parent.addClass("hidden");
            } else {
                parent.removeClass("hidden");
            }
        }
        var simpleFerry = $("#simpleFerry");
        if (simpleFerry.find(".thir:not(.hidden)").length > 0) {
            simpleFerry.removeClass("hidden").prev().removeClass("hidden");
        } else {
            simpleFerry.addClass("hidden").prev().addClass("hidden");
        }

        $("#hotelList .pro_contain, #scenicList .pro_contain").each(function () {
            var simple = $("#" + $(this).data("id"));
            if ($(this).hasClass("g_blue")) {
                simple.removeClass("hidden");
            } else {
                simple.addClass("hidden");
            }
        });
        $("#simpleHotelList, #simpleScenicList").each(function () {
            if ($(this).children(".bottom:not(.hidden)").length > 0) {
                $(this).removeClass("hidden").prev().removeClass("hidden");
            } else {
                $(this).addClass("hidden").prev().addClass("hidden");
            }
        });
    },

    bindEvent: function () {
        PlanOrder.selected();
        PlanOrder.typeList();
        PlanOrder.seltour('.tourlist span');
        PlanOrder.count();
        PlanOrder.submitOrder();
    },

    selected: function () {
        var apro = $('.pro_contain .sel_box');
        apro.on("click", function (event) {
            event.stopPropagation();
            if ($(this).parent().hasClass('g_blue')) {
                $(this).parent().removeClass('g_blue');
            } else {
                $(this).parent().addClass('g_blue');
            }
            PlanOrder.changeCost();
            PlanOrder.simpleList();
        });
        var total = $(".product_left .pro_title");
        total.on("click", function () {
            if ($(this).hasClass('g_blue')) {
                $(this).removeClass('g_blue');
                $(this).parent().find('.pro_contain').removeClass('g_blue');
            } else {
                $(this).addClass('g_blue');
                $(this).parent().find('.pro_contain').addClass('g_blue');
            }
            PlanOrder.changeCost();
            PlanOrder.simpleList();
        });
    },

    seltour: function (tourType) {
        var tour = $(tourType);
        tour.on('click', function () {
            if ($(this).hasClass('takeit')) {
                $(this).removeClass('takeit');
            } else {
                $(this).addClass('takeit');
            }
            PlanOrder.changeCost();
            if ($(this).parents("#ferry").length > 0) {
                PlanOrder.simpleList();
            }
        });
    },

    count: function () {
        var countbox = $('.numbox');
        countbox.each(function (id, element) {
            var add = $(element).find('.add');
            var num = $(element).find('.num');
            var sub = $(element).find('.sub');
            var nowNum = parseInt(num.data("num"));
            var maxNum = parseInt(num.data("max-num"));
            add.on("click", function () {
                nowNum++;
                if (nowNum >= maxNum) {
                    nowNum = maxNum;
                    add.addClass("readOlny");
                    sub.removeClass("readOlny");
                }
                num.text(nowNum).data("num", nowNum);
                $("#" + num.data("simple-id") + " .selectedNum").text(nowNum);
                PlanOrder.changeCost();
            });
            sub.on("click", function () {
                nowNum--;
                if (nowNum <= 1) {
                    nowNum = 1;
                    sub.addClass("readOlny");
                    add.removeClass("readOlny");
                }
                num.text(nowNum).data("num", nowNum);
                $("#" + num.data("simple-id") + " .selectedNum").text(nowNum);
                PlanOrder.changeCost();
            });
        })
    },

    typeList: function () {
        var typelist = $('.tickettype');
        typelist.each(function () {
            $(this).on("click", function (event) {
                event.stopPropagation();
                $(this).find('.typelist').slideDown(50);
                $(this).addClass('orangeborder');
                $(this).siblings().find('.typelist').slideUp(10);
            });
            $(this).find('p').on("click", function (event) {
                event.stopPropagation();
                var label = $(this).parent().prev('label');
                label.text($(this).text()).data("id", $(this).data("id")).data("price", $(this).data("price")).data("number", $(this).data("number")).data("ticketName", $(this).data("ticketName"));
                $(this).parent().slideUp(10);
                typelist.removeClass('orangeborder');
                PlanOrder.changeCost();
                PlanOrder.simpleList();
            });
            $('body').on("click", function () {
                typelist.find('.typelist').slideUp(10);
                typelist.removeClass('orangeborder');
            });
        })
    },

    submitOrder: function () {
        $("#submitOrder").on("click", function () {
            var $this = $(this);
            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    submit();
                } else {
                    YhyUser.goLogin(submit);
                }
            });

            function submit() {
                var totalPrice = $("#totalPrice").data("price");
                if (totalPrice <= 0) {
                    $.message.alert({
                        title: "提示",
                        info: "未选择购买项目"
                    });
                    return;
                }
                var planDemand = RightBar.getPlanDemand();
                var jsonObj = {
                    id: 0,
                    name: planDemand.planName,
                    day: planDemand.planDay,
                    playDate: planDemand.playDate,
                    orderType: "plan"
                };
                var details = [];
                $("#scenicList .pro_contain.g_blue").each(function () {
                    var tourists = [];
                    $(this).find(".tourlist .takeit").each(function () {
                        var tourist = {
                            name: $(this).data("name"),
                            phone: $(this).data("phone"),
                            peopleType: $(this).data("peopleType"),
                            idType: $(this).data("idType"),
                            idNum: $(this).data("idNum")
                        };
                        tourists.push(tourist);
                    });
                    $(this).find(".numbox .num").each(function () {
                        var detail = {
                            id: $(this).data("ticketId"),
                            priceId: $(this).data("priceId"),
                            price: $(this).data("price"),
                            startTime: $(this).data("playDate"),
                            endTime: $(this).data("playDate"),
                            num: $(this).data("num"),
                            type: "scenic",
                            seatType: $(this).data("ticketName")
                        };
                        detail.tourist = tourists;
                        details.push(detail);
                    });
                });
                $("#hotelList .pro_contain.g_blue").each(function () {
                    var tourists = [];
                    $(this).find(".tourlist .takeit").each(function () {
                        var tourist = {
                            name: $(this).data("name"),
                            phone: $(this).data("phone"),
                            peopleType: $(this).data("peopleType"),
                            idType: $(this).data("idType"),
                            idNum: $(this).data("idNum")
                        };
                        tourists.push(tourist);
                    });
                    $(this).find(".numbox .num").each(function () {
                        var detail = {
                            id: $(this).data("hotelId"),
                            priceId: $(this).data("priceId"),
                            price: $(this).data("price"),
                            startTime: $(this).data("startTime"),
                            endTime: $(this).data("endTime"),
                            num: $(this).data("num"),
                            type: "hotel",
                            seatType: $(this).data("priceName")
                        };
                        detail.tourist = tourists;
                        details.push(detail);
                    });
                });
                jsonObj.details = details;
                var params = {
                    json: JSON.stringify(jsonObj)
                };
                var ferryTicketList = [];
                var ferryTotal = 0;
                $("#ferry .pro_contain.g_blue .tourlist .takeit").each(function () {
                    var idType = $(this).data("idType");
                    if (idType == "IDCARD") {
                        idType = "ID_CARD";
                    } else {
                        idType = "OTHER";
                    }
                    var label = $(this).next().children("label");
                    var price = Number(label.data("price"));
                    ferryTicketList.push({
                        ticketId: label.data("id"),
                        ticketName: label.data("ticketName"),
                        price: label.data("price"),
                        number: price,
                        idType: idType,
                        name: $(this).data("name"),
                        idnumber: $(this).data("idnumber"),
                        mobile: $(this).data("mobile")
                    });
                    ferryTotal += price;
                });
                if (ferryTotal > 0) {
                    var ferry = {
                        dailyFlightGid: PlanOrder.ferry.dailyFlightGid,
                        flightNumber: PlanOrder.ferry.number,
                        flightLineName: PlanOrder.ferry.line.name,
                        departTime: PlanOrder.ferry.departTime,
                        amount: Math.round(ferryTotal * 100) / 100,
                        seat: ferryTicketList.length,
                        ferryOrderItemList: ferryTicketList
                    };
                    params.ferryJson = JSON.stringify(ferry);
                }
                saveOrder();

                function saveOrder() {
                    if ($this.hasClass("nowLoading")) {
                        return;
                    }
                    $this.addClass("nowLoading");
                    $.post("/yhypc/order/save.jhtml",
                        params,
                        function (data) {
                            if (data.success) {
                                location.href = "/yhypc/order/orderPlanPay.jhtml?orderId=" + data.order.id;
                            } else {
                                $this.removeClass("nowLoading");
                                if (!data.noMember && !data.noReal) {
                                    $.message.alert({
                                        title: "提示",
                                        info: data.errMsg
                                    });
                                    return;
                                }
                                if (data.noMember) {
                                    Popup.openRealname(saveOrder);
                                }
                                if (data.noReal) {
                                    Popup.openDoRealname(saveOrder);
                                }
                            }
                        }
                    )
                }
            }
        });
    }
};

$(window).ready(function () {
    PlanOrder.init();
});

