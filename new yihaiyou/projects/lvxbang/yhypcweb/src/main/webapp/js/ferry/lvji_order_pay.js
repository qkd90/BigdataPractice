/**
 * Created by huangpeijie on 2017-02-09,0009.
 */
var LvjiOrderPay = {
    weekdays: ["", "星期一(Mon)", "星期二(Tue)", "星期三(Wed)", "星期四(Thu)", "星期五(Fri)", "星期六(Sat)", "星期日(Sun)"],
    payType: "wechat",
    orderId: "",
    detailUrl: "",

    init: function () {
        LvjiOrderPay.bindEvent();
        LvjiOrderPay.initDate();
        LvjiOrderPay.timers();
    },


    initDate: function () {
        LvjiOrderPay.orderId = $("#orderId").val();
        LvjiOrderPay.detailUrl = "/yhypc/personal/ljOrderDetail.jhtml?orderId=" + LvjiOrderPay.orderId;
    },

    bindEvent: function () {
        LvjiOrderPay.payway();
        LvjiOrderPay.balancepay();
        LvjiOrderPay.topay();
        //LvjiOrderPay.paykey();
    },

    payway: function () {
        var li = $('.payList ul li');
        li.on("click", function () {
            if ($(this).hasClass('blank')) {

            } else {
                $(this).siblings().removeClass('pay_active').removeClass('zhifubaoActive').removeClass('wechatActive').removeClass('bankActive');
                $(this).addClass('pay_active');
                $('.payContain').removeClass('active');
                if ($(this).hasClass('wechat')) {
                    LvjiOrderPay.payType = "wechat";
                    $(this).addClass('wechatActive');
                    $(".payContain.wechatPay").addClass("active");
                } else if ($(this).hasClass('zhifubao')) {
                    LvjiOrderPay.payType = "zhifubao";
                    $(this).addClass('zhifubaoActive');
                    $(".payContain.zhifubaopay").addClass("active");
                } else if ($(this).hasClass("bank")) {
                    LvjiOrderPay.payType = "bank";
                    $(this).addClass('bankActive');
                    $(".payContain.bankpay").addClass("active");
                }
            }
        });

        $(".payContain .prev").on("click", function () {
            $.message.confirm({
                title: "提示",
                info: "确定取消订单",
                yes: function () {
                    $.post("/yhypc/ferry/cancelLjOrder.jhtml", {
                        orderId: LvjiOrderPay.orderId
                    }, function (data) {
                        if (data.success) {
                            $.message.alert({
                                title: "提示",
                                info: "订单取消成功"
                            });
                            location.href = LvjiOrderPay.detailUrl;
                        } else {
                            $.message.alert({
                                title: "提示",
                                info: data.errMsg
                            });
                        }
                    }, "json");
                }
            });
        });

        $(".payContain .next").on("click", function () {
            if (LvjiOrderPay.payType == "wechat") {
                $.post("/yhypc/pay/wechatPay.jhtml", {
                    orderId: LvjiOrderPay.orderId,
                    orderType: "Lvji"
                }, function (data) {
                    if (data.success) {
                        LvjiOrderPay.makeQrcode("wechat_qrcode", data.payForm);
                        $('.wechatPay').addClass("payNext");
                    } else {
                        $.message.alert({
                            title: "提示",
                            info: data.errMsg
                        });
                    }
                }, "json");
            } else if (LvjiOrderPay.payType == "zhifubao") {
                $.post("/yhypc/pay/aliPay.jhtml", {
                    orderId: LvjiOrderPay.orderId,
                    orderType: "Lvji"
                }, function (data) {
                    if (data.success) {
                        LvjiOrderPay.makeQrcode("zhifubao_qrcode", data.payForm);
                        $('.zhifubaopay').addClass("payNext");
                    } else {
                        $.message.alert({
                            title: "提示",
                            info: data.errMsg
                        });
                    }
                }, "json");
            } else if (OrderPay.payType == "bank") {
                location.href = "/yhypc/pay/cmbPay.jhtml?orderId=" + LvjiOrderPay.orderId;
            }
        });
    },

    balancepay: function () {
        var balance = $('.recharge .total');
        var k = 0;
        balance.on("click", function () {
            if (k == 0) {
                $(this).addClass('balancepay');
                $('.balance_disable').addClass('balance');
                k = 1;
            } else {
                $(this).removeClass('balancepay');
                $('.balance_disable').removeClass('balance');
                k = 0;
            }
        })
    },

    paykey: function (s_top) {
        var payPassBox = $('.payPasswordBox');
        var left = (window.screen.availWidth - payPassBox.width()) / 2;
        var top = (window.screen.availHeight - payPassBox.height()) / 2 + s_top;
        payPassBox.css({'left': left, 'top': top});
        var payPassConfirm = $('.payPassConfirm');
        left = (window.screen.availWidth - payPassConfirm.width()) / 2;
        top = (window.screen.availHeight - payPassConfirm.height()) / 2 + s_top;
        payPassConfirm.css({'left': left, 'top': top});
    },

    topay: function () {
        var paybtn = $('.recharge span.balance_disable');
        var closebtn = $('.payPasswordBox .closebtn,.payPassConfirm .closebtn,.payPassConfirm .yes,.payPassConfirm .no');
        var commitbtn = $('.payPasswordBox .btn');
        paybtn.on('click', function () {
            if ($(this).hasClass('balance')) {
                $.post("/yhypc/user/userInfo.jhtml", {}, function (data) {
                    if (data.success) {
                        var scrolltop = $(window).scrollTop();
                        $('.windowPayShadow').show();
                        $('body').css({'overflow': 'hidden'});
                        LvjiOrderPay.paykey(scrolltop);
                        if (data.user.hasPayPassword) {
                            $('.payPasswordBox').show();
                            $(".payPasswordBox .paykey").val("");
                        } else {
                            $('.payPassConfirm').show();
                        }
                    }
                });
            }
        });
        closebtn.on('click', function () {
            $('.windowPayShadow').hide();
            $('.payPasswordBox').hide();
            $('.payPassConfirm').hide();
            $('body').css({'overflow': 'auto'})
        });
        commitbtn.on("click", function () {
            var password = $(".payPasswordBox .paykey").val();
            if (password == null || password == "") {
                $.message.alert({
                    title: "提示",
                    info: "请输入支付密码"
                });
                return;
            }
            if (!password.match(Reg.passwordReg)) {
                $.message.alert({
                    title: "提示",
                    info: "支付密码格式错误"
                });
                return;
            }
            $.ajax({
                url: "/yhypc/pay/payBalance.jhtml",
                data: {
                    orderId: LvjiOrderPay.orderId,
                    orderType: "lvji",
                    payPassword: password
                },
                progress: true,
                success: function (data) {
                    if (data.success) {
                        location.href = LvjiOrderPay.detailUrl;
                    } else {
                        $.message.alert({
                            title: "提示",
                            info: data.errMsg
                        });
                    }
                },
                dataType: "json"
            })
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
        var seconds = parseInt($("#waitSeconds").val());
        setInterval(function () {
            if (seconds <= 0) {
                location.href = LvjiOrderPay.detailUrl;
            }
            seconds--;
            var str = parseInt(seconds / 60) + "分" + seconds % 60 + "秒";
            $("#waitTimeStr").text(str);
        }, 1000);
        setInterval(function () {
            $.post("/yhypc/ferry/ljOrderStatus.jhtml", {
                orderId: LvjiOrderPay.orderId
            }, function (data) {
                if (data.success) {
                    if (data.status != "WAIT") {
                        location.href = LvjiOrderPay.detailUrl;
                    }
                }
            }, "json");
        }, 2000);
    }
};

$(window).ready(function () {
    LvjiOrderPay.init();
});