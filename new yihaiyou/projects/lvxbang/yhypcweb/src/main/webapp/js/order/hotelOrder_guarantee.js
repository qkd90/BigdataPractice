var OrderHotelGuarantee = {
    weekdays: ["", "星期一(Mon)", "星期二(Tue)", "星期三(Wed)", "星期四(Thu)", "星期五(Fri)", "星期六(Sat)", "星期日(Sun)"],
    hotelOrder: {},

    init: function () {
        OrderHotelGuarantee.bindEvent();
        OrderHotelGuarantee.initDate();
    },

    initDate: function () {
        var hotelOrderStr = getUnCodedCookie("hotelOrder");
        if (hotelOrderStr == null || hotelOrderStr == "" || hotelOrderStr == "null" || hotelOrderStr == "undefined") {
            history.back();
        }
        OrderHotelGuarantee.hotelOrder = JSON.parse(hotelOrderStr);
        if ($.isEmptyObject(OrderHotelGuarantee.hotelOrder)) {
            history.back();
        }
        $("#orderName").text(OrderHotelGuarantee.hotelOrder.name);
        var detail = OrderHotelGuarantee.hotelOrder.details[0];
        var startDate = moment(detail.startTime);
        var endDate = moment(detail.endTime);
        $("#startDateStr").text(startDate.format("YYYY-MM-DD"));
        $("#startWeekday").text(OrderHotelGuarantee.weekdays[startDate.format("E")]);
        $("#endDateStr").text(endDate.format("YYYY-MM-DD"));
        $("#endWeekday").text(OrderHotelGuarantee.weekdays[endDate.format("E")]);
        $("#roomNum").text(detail.num);
        $("#recMobile").text(OrderHotelGuarantee.hotelOrder.contact.telephone);
        $("#totalPrice").text(Math.round(detail.price * detail.num * endDate.diff(startDate, "d") * 100) / 100);
        $("#hotelCover").attr("src", OrderHotelGuarantee.hotelOrder.cover);
        delete OrderHotelGuarantee.hotelOrder.cover;

        var monthHtml = "";
        for (var i = 1; i < 13; i++) {
            monthHtml += "<option>" + i + "月</option>";
        }
        $("#expirationMonth").append(monthHtml);

        var yearHtml = "";
        var now = new Date().getYear() + 1900;
        for (var j = 0; j < 10; j++) {
            yearHtml += "<option>" + (now + j) + "年</option>";
        }
        $("#expirationYear").append(yearHtml);
    },

    bindEvent: function () {
        OrderHotelGuarantee.takeOrder();
    },

    //提交
    takeOrder: function () {
        $("#submitOrder").on("click", function () {
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
                var cardNum = $("#cardNum").val();
                var expirationMonth = $("#expirationMonth").val().replace("月", "");
                var expirationYear = $("#expirationYear").val().replace("年", "");
                var cvv = $("#cvv").val();
                var holderName = $("#holderName").val();
                var idNo = $("#idNo").val();
                if (cardNum == null || cardNum == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写银行卡号"
                    });
                    return;
                }
                if (cvv == null || cvv == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写cvv"
                    });
                    return;
                }
                if (!cvv.match(/^[0-9]{3}$/)) {
                    $.message.alert({
                        title: "提示",
                        info: "cvv"
                    });
                    return;
                }
                if (holderName == null || holderName == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写联系人姓名"
                    });
                    return;
                }
                if (!holderName.match(Reg.nameReg) || holderName.length > 10) {
                    $.message.alert({
                        title: "提示",
                        info: "联系人姓名有误"
                    });
                    return;
                }
                if (idNo == null || idNo == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写身份证"
                    });
                    return;
                }
                if (!idNo.match(Reg.idCardReg)) {
                    $.message.alert({
                        title: "提示",
                        info: "身份证格式错误"
                    });
                    return;
                }
                var code = $("#checkCode").val();
                if (code == null || code == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请输入验证码"
                    });
                    return;
                }

                OrderHotelGuarantee.hotelOrder.details[0].creditCard = {
                    status: true,
                    cardNum: cardNum,
                    cvv: cvv,
                    expirationYear: expirationYear,
                    expirationMonth: expirationMonth,
                    holderName: holderName,
                    creditCardIdType: "IdentityCard",
                    idNo: idNo
                };

                $this.addClass("nowLoading");
                var detail = OrderHotelGuarantee.hotelOrder.details[0];
                $.post("/yhypc/order/checkHotelOrder.jhtml", {
                    hotelPriceId: detail.priceId,
                    startDate: detail.startTime,
                    endDate: detail.endTime,
                    num: detail.num
                }, function (data) {
                    if (data.success) {
                        $.ajax({
                            url: "/yhypc/order/save.jhtml",
                            data: {
                                json: JSON.stringify(OrderHotelGuarantee.hotelOrder),
                                checkCode: code
                            },
                            progress: true,
                            success: function (data) {
                                if (data.success) {
                                    location.href = "/yhypc/personal/hotelOrderDetail.jhtml?id=" + data.order.id + "&type=" + OrderPay.orderType;
                                } else {
                                    $this.removeClass("nowLoading");
                                    $.message.alert({
                                        title: "提示",
                                        info: data.errMsg
                                    });
                                }
                            }
                        });
                    } else {
                        $this.removeClass("nowLoading");
                        $.message.alert({
                            title: "提示",
                            info: "房型库存不足"
                        });
                    }
                });
            }
        });
    },
};

$(document).ready(function () {
    OrderHotelGuarantee.init();
});