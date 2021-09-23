/**
 * Created by huangpeijie on 2017-02-09,0009.
 */
var OrderPay = {
    weekdays: ["", "星期一（Mon）", "星期二（Tue）", "星期三（Wed）", "星期四（Thu）", "星期五（Fri）", "星期六（Sat）", "星期日（Sun）"],
    payType: "wechat",
    orderId: "",
    orderType: "",
    detailUrl: "",

    init: function () {
        OrderPay.bindEvent();
        OrderPay.initDate();
        OrderPay.timers();
    },


    initDate: function () {
        OrderPay.orderId = $("#orderId").val();
        OrderPay.orderType = $("#orderType").val();
    },

    bindEvent: function () {
        OrderPay.payway();
        OrderPay.balancepay();
        OrderPay.topay();
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
                    OrderPay.payType = "wechat";
                    $(this).addClass('wechatActive');
                    $(".payContain.wechatPay").addClass("active");
                } else if ($(this).hasClass('zhifubao')) {
                    OrderPay.payType = "zhifubao";
                    $(this).addClass('zhifubaoActive');
                    $(".payContain.zhifubaopay").addClass("active");
                } else if ($(this).hasClass("bank")) {
                    OrderPay.payType = "bank";
                    $(this).addClass('bankActive');
                    $(".payContain.bankpay").addClass("active");
                }
            }
        });

        $(".payContain .next").on("click", function () {
            var $this = $(this);
            if ($this.hasClass("nowLoading")) {
                return;
            }
            $this.addClass("nowLoading");
            if (OrderPay.payType == "wechat") {
                $.ajax({
                    url: "/yhypc/pay/wechatPay.jhtml",
                    data: {
                        orderId: OrderPay.orderId
                    },
                    progress: true,
                    success: function (data) {
                        if (data.success) {
                            OrderPay.makeQrcode("wechat_qrcode", data.payForm);
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
            } else if (OrderPay.payType == "zhifubao") {
                $.ajax({
                    url: "/yhypc/pay/aliPay.jhtml",
                    data: {
                        orderId: OrderPay.orderId
                    },
                    progress: true,
                    success: function (data) {
                        if (data.success) {
                            OrderPay.makeQrcode("zhifubao_qrcode", data.payForm);
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
            } else if (OrderPay.payType == "bank") {
                location.href = "/yhypc/pay/cmbPay.jhtml?orderId=" + OrderPay.orderId;
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

    makeQrcode: function (id, wechatCode) {
        $("#" + id).qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: wechatCode //任意内容
        });
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
                        OrderPay.paykey(scrolltop);
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
                    orderId: OrderPay.orderId,
                    payPassword: password
                },
                progress: true,
                success: function (data) {
                    if (data.success) {
                        location.href = OrderPay.detailUrl;
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

    timers: function () {
        var seconds = parseInt($("#waitSeconds").val());
        setInterval(function () {
            if (seconds <= 0) {
                location.href = OrderPay.detailUrl;
            }
            seconds--;
            var str = parseInt(seconds / 60) + "分" + seconds % 60 + "秒";
            $("#waitTimeStr").text(str);
        }, 1000);
        setInterval(function () {
            $.post("/yhypc/order/orderStatus.jhtml", {
                orderId: OrderPay.orderId
            }, function (data) {
                if (data.success) {
                    if (data.status != "WAIT") {
                        location.href = OrderPay.detailUrl;
                    }
                }
            }, "json");
        }, 2000);
    }
};

$(window).ready(function () {
    OrderPay.init();
});