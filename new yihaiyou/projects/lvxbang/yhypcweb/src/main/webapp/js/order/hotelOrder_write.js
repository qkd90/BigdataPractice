var OrderHotelWrite = {
    weekdays: ["", "星期一(Mon)", "星期二(Tue)", "星期三(Wed)", "星期四(Thu)", "星期五(Fri)", "星期六(Sat)", "星期日(Sun)"],
    day: 0,
    num: 1,
    price: 0,
    hotelId: 0,
    hotelName: "",
    hotelPriceId: 0,
    hotelPriceName: "",
    startDate: "",
    endDate: "",
    touristList: [],

    init: function () {
        OrderHotelWrite.bindEvent();
        OrderHotelWrite.initDate();
    },

    initDate: function () {
        OrderHotelWrite.hotelId = $("#hotelId").val();
        OrderHotelWrite.hotelName = $("#hotelName").val();
        OrderHotelWrite.hotelPriceId = $("#hotelPriceId").val();
        OrderHotelWrite.hotelPriceName = $("#hotelPriceName").val();
        OrderHotelWrite.startDate = $("#startDate").val();
        OrderHotelWrite.endDate = $("#endDate").val();
        OrderHotelWrite.num = $("#num").val();
        OrderHotelWrite.initRoomPeople();
        OrderHotelWrite.initTouristList();
        OrderHotelWrite.hotelPriceCalendar();
        delCookie("hotelOrder");
    },

    onFoucsEndTime: function() {
        var startTime = $("#startDateSel").val();
        if (startTime.length <= 0) {
            return;
        }
        var date = moment(startTime);
        $("#endDateSel").val($.addDate(date, 1));
    },

    initTouristList: function () {
        YhyUser.checkLogin(function (result) {
            if (result.success) {
                getTouristList();
            } else {
                YhyUser.goLogin(getTouristList);
            }
        });

        function getTouristList() {
            $.post("/yhypc/user/touristList.jhtml", {}, function (data) {
                if (data.success) {
                    OrderHotelWrite.touristList = data.touristList;
                    var touristList = $(".edited");
                    touristList.empty();
                    var html = "";
                    $.each(OrderHotelWrite.touristList, function (i, tourist) {
                        tourist.index = i + 1;
                        html += template("tourist_list_item", tourist);
                    });
                    touristList.append(html);
                    OrderHotelWrite.selectTour();
                }
            }, "json");
        }
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

    hotelPriceCalendar: function () {
        $.post("/yhypc/order/hotelPriceCalendar.jhtml", {
            hotelPriceId: OrderHotelWrite.hotelPriceId,
            startDate: OrderHotelWrite.startDate,
            endDate: OrderHotelWrite.endDate
        }, function (data) {
            if (data.success) {
                var totalPrice = 0;
                $.each(data.calendarList, function (i, calander) {
                    totalPrice += calander.member;
                });
                totalPrice = Math.round(totalPrice * 100) / 100;
                OrderHotelWrite.price = totalPrice;
            } else {
                OrderHotelWrite.price = 0;
                $.message.alert({
                    title: "提示",
                    info: "酒店库存不足"
                });
            }
            $(".num").text(OrderHotelWrite.price * OrderHotelWrite.num);

            $(".startDateStr").text(OrderHotelWrite.startDate);
            $("#startDateSel").val(OrderHotelWrite.startDate);
            var start = moment(OrderHotelWrite.startDate);
            $("#startWeekday").text(OrderHotelWrite.weekdays[start.format("E")]);

            $(".endDateStr").text(OrderHotelWrite.endDate);
            $("#endDateSel").val(OrderHotelWrite.endDate);
            var end = moment(OrderHotelWrite.endDate);
            $("#endWeekday").text(OrderHotelWrite.weekdays[end.format("E")]);

            OrderHotelWrite.day = end.diff(start, "d");
            $("#day").text(OrderHotelWrite.day);
        }, "json");
    },

    bindEvent: function () {
        OrderHotelWrite.phoneList();
        OrderHotelWrite.agreement();
        OrderHotelWrite.roomNum();
        OrderHotelWrite.centerBox($('.handInbox'));
        OrderHotelWrite.centerBox($('.dateChange'));
        OrderHotelWrite.centerBox($('.tourbox'));
        OrderHotelWrite.modify();
        OrderHotelWrite.takeOrder();
        OrderHotelWrite.selTour();
    },

    //电话号码
    phoneList: function () {
        var downBtn = $('.down');
        var upBtn = $('.hotelOrder');
        downBtn.on("click", function (event) {
            event.stopPropagation();
            $('.phoneNum').slideDown(100);
        });
        upBtn.on("click", function () {
            $('.phoneNum').slideUp(100);
        })
    },

    //协议
    agreement: function () {
        var agreeBox = $('.payNum .p2');
        var k = 0;
        agreeBox.on("click", function () {
            if (k == 0) {
                $(this).addClass('p3');
                k = 1;
            } else {
                $(this).removeClass('p3');
                k = 0;
            }
        })
    },

    //房间数量
    roomNum: function () {
        var sub = $('.sub'),
            add = $('.add'),
            num = $('.r_num');
        add.on("click", function () {
            if (OrderHotelWrite.num < 10) {
                OrderHotelWrite.num++;
                num.text(OrderHotelWrite.num);
                $(".num").text(OrderHotelWrite.price * OrderHotelWrite.num);
                OrderHotelWrite.initRoomPeople();
            }
        });
        sub.on("click", function () {
            if (OrderHotelWrite.num > 1) {
                OrderHotelWrite.num--;
                num.text(OrderHotelWrite.num);
                $(".num").text(OrderHotelWrite.price * OrderHotelWrite.num);
                OrderHotelWrite.initRoomPeople();
            }
        });
    },

    initRoomPeople: function () {
        var html = "";
        for (var i = 1; i <= OrderHotelWrite.num; i++) {
            html += template("room_people_item", {index: i});
        }
        $("#roomPeople").html(html);
    },

    //居中
    centerBox: function (tag) {
        var left = (window.screen.availWidth - tag.width()) / 2;
        var top = (window.screen.availHeight - tag.height()) / 2 - 40;
        tag.css({'left': left, 'top': top});
    },

    selectTourist: function () {
        var peopleName = $("#roomPeople .roomPeopleName");
        var selectedTourist = $('.edited p.checked');
        if (peopleName.length != selectedTourist.length) {
            $.message.alert({
                title: "提示",
                info: "游客数量错误"
            });
            return;
        }
        var index = 0;
        $(selectedTourist).each(function () {
            var id = $(this).data("id");
            $.each(OrderHotelWrite.touristList, function (i, tourist) {
                if (tourist.id == id) {
                    peopleName.eq(index).val(tourist.name).data("tourist", tourist);
                    index++;
                    return false;
                }
            });
        });
        $('.shadow').hide();
        $('.tourbox').hide();
    },

    //提交
    takeOrder: function () {
        $(".handin .pay .payBtn,.handin .pay .guaranteeBtn").on("click", function () {
            var $this = $(this);
            if ($this.hasClass("nowLoading")) {
                return;
            }
            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    submit();
                } else {
                    YhyUser.goLogin(submit);
                }
            });

            function submit() {
                var source = $this.data("source");
                var isReturn = false;
                var touristList = [];
                $("#roomPeople .roomPeopleName").each(function () {
                    var tourist = $(this).data("tourist");
                    if ($.isEmptyObject(tourist)) {
                        isReturn = true;
                        return false;
                    }
                    touristList.push({
                        name: tourist.name,
                        phone: tourist.tel,
                        peopleType: tourist.peopleType,
                        idType: tourist.idType,
                        idNum: tourist.idNumber
                    });
                });
                if (isReturn) {
                    $.message.alert({
                        title: "提示",
                        info: "入住人姓名错误"
                    });
                    return;
                }
                var telephone = $("#contactTelephone").val();
                var code = $("#checkCode").val();
                if ($this.hasClass("payBtn")) {
                    if (code == null || code == "") {
                        $.message.alert({
                            title: "提示",
                            info: "请输入验证码"
                        });
                        return;
                    }
                }
                if (telephone == null || telephone == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请输入手机号"
                    });
                    return;
                }
                if (!telephone.match(Reg.telephoneReg)) {
                    $.message.alert({
                        title: "提示",
                        info: "手机号格式错误"
                    });
                    return;
                }

                $this.addClass("nowLoading");
                $.post("/yhypc/order/checkHotelOrder.jhtml", {
                    hotelPriceId: OrderHotelWrite.hotelPriceId,
                    startDate: OrderHotelWrite.startDate,
                    endDate: OrderHotelWrite.endDate,
                    num: OrderHotelWrite.num
                }, function (data) {
                    if (data.success) {
                        var jsonObj = {
                            id: 0,
                            name: OrderHotelWrite.hotelName,
                            playDate: OrderHotelWrite.startDate,
                            contact: {
                                telephone: telephone
                            },
                            orderType: "hotel"
                        };
                        var details = [];
                        var detail = {
                            id: OrderHotelWrite.hotelId,
                            priceId: OrderHotelWrite.hotelPriceId,
                            price: OrderHotelWrite.price / OrderHotelWrite.day,
                            startTime: OrderHotelWrite.startDate,
                            endTime: OrderHotelWrite.endDate,
                            num: OrderHotelWrite.num,
                            type: "hotel",
                            seatType: OrderHotelWrite.hotelPriceName,
                            tourist: touristList
                        };
                        details.push(detail);
                        jsonObj.details = details;
                        if ($this.hasClass("payBtn")) {
                            $.post("/yhypc/order/save.jhtml", {
                                json: JSON.stringify(jsonObj),
                                checkCode: code
                            }, function (data) {
                                if (data.success) {
                                    if (source == "LXB") {
                                        location.href = "/yhypc/order/orderHotelPay.jhtml?orderId=" + data.order.id;
                                    } else {
                                        location.href = "/yhypc/personal/hotelOrderDetail.jhtml?id=" + data.order.id + "&type=hotel";
                                    }
                                } else {
                                    $this.removeClass("nowLoading");
                                    $.message.alert({
                                        title: "提示",
                                        info: data.errMsg
                                    });
                                }
                            }, "json");
                        } else {
                            jsonObj.cover = $("#hotelCover").val();
                            setUnCodedCookie("hotelOrder", JSON.stringify(jsonObj));
                            location.href = "/yhypc/order/orderHotelGuarantee.jhtml";
                        }
                    } else {
                        $this.removeClass("nowLoading");
                        $.message.alert({
                            title: "提示",
                            info: "房型库存不足"
                        });
                    }
                }, "json");
            }
        });
    },

    //修改日期
    modify: function () {
        var modifyBtn = $('.modify');
        var changeBtm = $('.dateChange .change_btn');
        var closeBtn = $('.dateChange .closeBtn');
        var shadow = $('.shadow');
        var startDate = $('#startDateSel');
        var endDate = $('#endDateSel');

        modifyBtn.on("click", function () {
            $('.shadow').show();
            $('.dateChange').show();
            $('body').addClass('stopScroll');
        });
        changeBtm.on("click", ok);
        closeBtn.on("click", cancel);
        //shadow.on("click", cancel);

        function hide() {
            $('.shadow').hide();
            $('.dateChange').hide();
            $('body').removeClass('stopScroll');
        }

        function ok() {
            hide();
            OrderHotelWrite.startDate = startDate.val();
            OrderHotelWrite.endDate = endDate.val();
            OrderHotelWrite.hotelPriceCalendar();
        }

        function cancel() {
            hide();
            startDate.val(OrderHotelWrite.startDate);
            endDate.val(OrderHotelWrite.endDate);
        }


        //startDate.datetimepicker({
        //    closeOnDateSelect: true,
        //    scrollInput: false,
        //    todayButton: false,
        //    timepicker: false,
        //    format: "YYYY-MM-DD",
        //    formatDate: "YYYY-MM-DD",
        //    value: new Date(),
        //    minDate: 0,
        //    onSelectDate: function (date) {
        //        var start = moment(date);
        //        startDate.val(start.format("YYYY-MM-DD"));
        //        var end = moment(endDate.val());
        //        if (end.diff(start, "d") <= 0) {
        //            var newEnd = start.add(1, "d");
        //            endDate.val(newEnd.format("YYYY-MM-DD"));
        //        }
        //    }
        //});

        //endDate.datetimepicker({
        //    closeOnDateSelect: true,
        //    scrollInput: false,
        //    todayButton: false,
        //    timepicker: false,
        //    format: "YYYY-MM-DD",
        //    formatDate: "YYYY-MM-DD",
        //    value: new Date(),
        //    minDate: 0,
        //    onShow: function () {
        //        if (!startDate.val()) {
        //            this.setOptions({
        //                minDate: false
        //            });
        //        } else {
        //            var min = moment(startDate.val()).add(1, "d");
        //            this.setOptions({
        //                minDate: min.format("YYYY-MM-DD")
        //            });
        //        }
        //    },
        //    onSelectDate: function (date) {
        //        endDate.val(moment(date).format("YYYY-MM-DD"));
        //    }
        //});
        //
        //$('div.dateChange div.date_left').on('click', function (event) {
        //    event.stopPropagation();
        //    startDate.datetimepicker('show');
        //});
        //$('div.dateChange div.date_right').on('click', function (event) {
        //    event.stopPropagation();
        //    endDate.datetimepicker('show');
        //});
    },
    selTour: function () {
        var sel = $('.hostMess .addtourbtn');
        var close = $('.tourbox .title_close span');
        $("#roomPeople").on("click", ".roomPeopleName", function () {
            $('.shadow').show();
            $('.tourbox').show();
        });
        sel.on('click', function () {
            $('.shadow').show();
            $('.tourbox').show();
        });
        close.on('click', function () {
            $('.shadow').hide();
            $('.tourbox').hide();
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
                OrderHotelWrite.initTouristList();
                $("#addTourName").val("");
                $("#addTourTel").val("");
                $("#addTourIdType").val("身份证").data("type", "IDCARD");
                $("#addTourIdNo").val("");
            }
        });
    }
};

$(document).ready(function () {
    OrderHotelWrite.init();
});