var MyRecharge = {
    payType: "wechat",
    wechatId: 0,
    zhifubaoId: 0,
    init: function () {
        YhyUser.checkLogin(function(result) {
            if (result.success) {
                MyRecharge.initComp();
                MyRecharge.initEvt();
                MyRecharge.timers();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function () {
        // scroll to input
        scroll(0, 480);
        // focus input
        $('#rechargeIpt').focus();
    },
    initEvt: function () {
        // switch pay way
        $('.payList ul li').click(function () {
            if ($(this).hasClass('blank')) {
            } else {
                $(this).siblings().removeClass('pay_active').removeClass('zhifubaoActive').removeClass('wechatActive').removeClass('bankActive');
                $(this).addClass('pay_active');
                $('.payContain').removeClass('active');
                if ($(this).hasClass('wechat')) {
                    MyRecharge.payType = "wechat";
                    $(this).addClass('wechatActive');
                    $(".payContain.wechatPay").addClass("active");
                } else if ($(this).hasClass('zhifubao')) {
                    MyRecharge.payType = "zhifubao";
                    $(this).addClass('zhifubaoActive');
                    $(".payContain.zhifubaopay").addClass("active");
                }
            }
        });

        $(".payContain .next").on("click", function () {
            var $this = $(this);
            if ($this.hasClass("nowLoading")) {
                return;
            }
            var price = Number($("#rechargeIpt").val());
            if (!(price > 0)) {
                $.message.alert({
                    title: "提示",
                    info: "请输入充值金额"
                });
                return;
            }
            var jsonObj = {
                id: 0,
                orderType: "recharge"
            };
            var details = [];
            var detail = {
                price: price
            };
            details.push(detail);
            jsonObj.details = details;
            $this.addClass("nowLoading");
            $.post("/yhypc/order/saveBalanceOrder.jhtml", {
                json: JSON.stringify(jsonObj)
            }, function (data) {
                if (data.success) {
                    if (MyRecharge.payType == "wechat") {
                        MyRecharge.wechatId = data.order.id;
                        $.ajax({
                            url: "/yhypc/pay/wechatPay.jhtml",
                            data: {
                                orderId: MyRecharge.wechatId
                            },
                            progress: true,
                            success: function (data) {
                                if (data.success) {
                                    MyRecharge.makeQrcode("wechat_qrcode", data.payForm);
                                    $('.wechatPay').addClass("payNext");
                                } else {
                                    $this.removeClass("nowLoading");
                                    $.message.alert({
                                        title: "提示",
                                        info: data.errMsg
                                    });
                                }
                            },
                            dataType: "json"
                        });
                    } else if (MyRecharge.payType == "zhifubao") {
                        MyRecharge.zhifubaoId = data.order.id;
                        $.ajax({
                            url: "/yhypc/pay/aliPay.jhtml",
                            data: {
                                orderId: MyRecharge.zhifubaoId
                            },
                            progress: true,
                            success: function (data) {
                                if (data.success) {
                                    MyRecharge.makeQrcode("zhifubao_qrcode", data.payForm);
                                    $('.zhifubaopay').addClass("payNext");
                                } else {
                                    $this.removeClass("nowLoading");
                                    $.message.alert({
                                        title: "提示",
                                        info: data.errMsg
                                    });
                                }
                            },
                            dataType: "json"
                        });
                    }
                }
            });
        });
    },

    makeQrcode: function (id, wechatCode) {
        $("#" + id).qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: wechatCode //任意内容
        });
    },

    timers: function () {
        setInterval(function () {
            if (MyRecharge.wechatId > 0) {
                $.post("/yhypc/order/orderStatus.jhtml", {
                    orderId: MyRecharge.wechatId
                }, function (data) {
                    if (data.success && data.status == "SUCCESS") {
                        MyRecharge.wechatId = 0;
                        $.message.alert({
                            title: "提示",
                            info: "充值成功"
                        });
                    }
                }, "json");
            }
            if (MyRecharge.zhifubaoId > 0) {
                $.post("/yhypc/order/orderStatus.jhtml", {
                    orderId: MyRecharge.zhifubaoId
                }, function (data) {
                    if (data.success && data.status == "SUCCESS") {
                        MyRecharge.zhifubaoId = 0;
                        $.message.alert({
                            title: "提示",
                            info: "充值成功"
                        });
                    }
                }, "json");
            }
        }, 2000);
    }
};

$(function () {
    MyRecharge.init();
});