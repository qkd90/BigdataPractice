var MyWallet = {
    pager: null,
    bank: {},
    init: function () {
        YhyUser.checkLogin(function (result) {
            if (result && result.success) {
                MyWallet.initComp();
                MyWallet.initEvt();
                MyWallet.createPager();
                MyWallet.initData();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function () {
        // center box
        var left = (window.screen.width - $('.withdrawBox').width()) / 2;
        var top = (window.screen.height - $('.withdrawBox').height()) / 2.5;
        $('.withdrawBox').css({'left': left, 'top': top});
        left = (window.screen.width - $('.setpswordBox').width()) / 2;
        top = (window.screen.height - $('.setpswordBox').height()) / 2.5;
        $('.setpswordBox').css({'left': left, 'top': top});
        var fnS = $('#fn').val();
        if (fnS && fnS != null) {
            var fn = window["MyWallet"][fnS];
            if (fn && typeof fn === "function") {
                fn.apply(this, null);
            }
        }
    },
    initEvt: function () {
        //bankcar
        var allcontain = $('.bindBank');
        $('.bindBank .line .inputbox').on('click', function (event) {
            event.stopPropagation();
            if ($(this).hasClass("city") && $('.bindBank .line .inputbox.province .contain').text() == "") {
                return;
            }
            $('.allist').slideUp(100);
            $(this).children("ul.allist").slideDown(100);
        });
        $('.bindBank .line .allist').on('click', "li", function (event) {
            event.stopPropagation();
            var text = $(this).html();
            $(this).parent().prev().html(text);
            $(this).parent().slideUp(100);
        });
        $('.bindBank .line .provincelist').on("click", "li", function (event) {
            event.stopPropagation();
            MyWallet.getCity($(this).data("id"));
        });
        allcontain.on('click', function () {
            $('.bindBank .line .allist').slideUp(100);
        });
        $('.withdraw').on('click', function (event) {
            event.stopPropagation();
            $.post("/yhypc/pay/lastCmbLog.jhtml", function (data) {
                if (data.success) {
                    var historyCard = $(".bindBank .historyCard");
                    historyCard.find(".carname").text(data.bank.bankName + "：");
                    historyCard.find(".carnum").text(data.bank.bankNo);
                    historyCard.find(".carid").data("id", data.bank.id);
                    historyCard.show();
                }
                allcontain.show();
                $('.shadow').show();
                $('body').css({'overflow': 'hidden'});
                MyWallet.clearBank();
                MyWallet.bank = {};
            });
        });
        $('.bindBank .closebtn').on('click', function () {
            allcontain.hide();
            $('.shadow').hide();
            MyWallet.clearBank();
            MyWallet.bank = {};
            $('body').css({'overflow': 'auto'});
        });
        // withdraw box
        $('.bindBank .bottom').on('click', function (event) {
            event.stopPropagation();
            var historyCard = $(".bindBank .historyCard .carid");
            if (historyCard.hasClass("carcolor")) {
                MyWallet.bank = {
                    id: historyCard.data("id")
                };
            } else {
                var bankName = $(".bindBank .bank .contain").text();
                var bankNo = $(".bindBank .bankNo .reinput").val();
                var holderName = $(".bindBank .holderName .reinput").val();
                var province = $(".bindBank .province .contain").text();
                var city = $(".bindBank .city .contain").text();
                if (bankName == null || bankName == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请选择银行"
                    });
                    return;
                }
                if (bankNo == null || bankNo == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请输入银行卡号"
                    });
                    return;
                }
                if (!bankNo.match(Reg.bankNoReg)) {
                    $.message.alert({
                        title: "提示",
                        info: "银行卡格式错误"
                    });
                    return;
                }
                if (holderName == null || holderName == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请输入持卡人姓名"
                    });
                    return;
                }
                if (!holderName.match(Reg.nameReg)) {
                    $.message.alert({
                        title: "提示",
                        info: "持卡人姓名格式错误"
                    });
                    return;
                }
                if (province == null || province == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请选择省份"
                    });
                    return;
                }
                if (city == null || city == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请选择城市"
                    });
                    return;
                }
                MyWallet.bank = {
                    bankName: bankName,
                    bankNo: bankNo,
                    holderName: holderName,
                    province: province,
                    city: city
                };
            }
            allcontain.hide();
            $('.withdrawBox').show();
            MyWallet.clearBank();
        });
        $(".withdrawBox .submit").on("click", function () {
            var $this = $(this);
            if ($this.hasClass("nowLoading")) {
                return;
            }
            var withdrawPrice = Number($(".withdrawBox .withdrawPrice").val());
            var payPassword = $(".withdrawBox .payPassword").val();
            if (!(withdrawPrice > 0)) {
                return;
            }
            if (payPassword == null || payPassword == "") {
                return;
            }
            if (!payPassword.match(Reg.passwordReg)) {
                return;
            }
            var jsonObj = {
                id: 0,
                orderType: "withdraw"
            };
            var details = [];
            var detail = {
                price: withdrawPrice
            };
            details.push(detail);
            jsonObj.details = details;
            $this.addClass("nowLoading");
            $.ajax({
                url: "/yhypc/order/saveBalanceOrder.jhtml",
                data: {
                    json: JSON.stringify(jsonObj),
                    payPassword: payPassword
                },
                progress: true,
                success: function (data) {
                    if (data.success) {
                        var bankId = MyWallet.bank.id;
                        delete MyWallet.bank.id;
                        $.ajax({
                            url: "/yhypc/pay/cmbWithdraw.jhtml",
                            data: {
                                json: JSON.stringify(MyWallet.bank),
                                bankId: bankId,
                                orderId: data.order.id
                            },
                            progress: true,
                            success: function (result) {
                                $this.removeClass("nowLoading");
                                if (result.success) {
                                    $.message.alert({
                                        title: "提示",
                                        info: "提现请求成功"
                                    });
                                    MyWallet.closeWithdrawBox();
                                } else {
                                    $.message.alert({
                                        title: "提示",
                                        info: result.errMsg
                                    });
                                }
                            },
                            dataType: "json"
                        });
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
        });
        // pay pwd box
        $('.setpsword').on('click', function (event) {
            event.stopPropagation();
            MyWallet.openSetPayPwdBox();
        });
        //close withdraw box
        $('#with_close').on('click', function (event) {
            event.stopPropagation();
            MyWallet.closeWithdrawBox();
        });
        // close pay pwd box
        $('#psword_close').on('click', function (event) {
            event.stopPropagation();
            MyWallet.closeSetPayPwdBox();
        });
        // focus
        $('.with_body input').on('focus', function (event) {
            event.stopPropagation();
            $(this).addClass('outline');
        });
        // blur
        $('.with_body input').on('blur', function (event) {
            event.stopPropagation();
            $(this).removeClass('outline')
            if ($(this).val().length == 0) {
                $(this).next().find('.hidden').show();
            } else {
                $(this).next().find('.hidden').hide();
            }
        });
        // do set pay pwd btn
        $('#doSetPayPwdBtn').on('click', function (event) {
            event.stopPropagation();
            MyWallet.doSetPayPwd();
        });
        $('.bindBank .leftcheck').on('click',function(event){
            event.stopPropagation();
            var check = $(this).hasClass('leftcheckin');
            if(check == false){
                $(this).addClass('leftcheckin');
                $(this).next().addClass('carcolor');
            }else{
                $(this).removeClass('leftcheckin');
                $(this).next().removeClass('carcolor');
            }
        })
    },
    openSetPayPwdBox: function () {
        $('.shadow').show();
        $('.setpswordBox').show();
        $('body').css({'overflow': 'hidden'});
    },
    closeSetPayPwdBox: function () {
        $('.shadow').hide();
        $('.setpswordBox').hide();
        $('body').css({'overflow': 'auto'});
        $.form.reset({formId: '#setPayPwdForm'});
        $('#setPayPwdForm').find('input[name = "cfmPayPwd"]').next('span.attention').html(null);
    },
    closeWithdrawBox: function () {
        $('.shadow').hide();
        $('.withdrawBox').hide();
        $('body').css({'overflow': 'auto'});
    },
    initData: function () {
        MyWallet.getProvince();
        MyWallet.getConsumeRecord();
    },
    getProvince: function () {
        $.post("/yhypc/city/getProvinceList.jhtml", function (data) {
            var html = "";
            $.each(data, function (i, province) {
                html += template("city_item", province);
            });
            $(".bindBank .provincelist").html(html);
        });
    },
    getCity: function (provinceId) {
        $(".bindBank .city .contain").text("");
        $.post("/yhypc/city/getCityList.jhtml", {
            provinceId: provinceId
        }, function (data) {
            var html = "";
            $.each(data, function (i, city) {
                html += template("city_item", city);
            });
            $(".bindBank .citylist").html(html);
        });
    },
    clearBank: function () {
        $(".bindBank .contain").text("");
        $(".bindBank .citylist").html("");
        $(".bindBank input").val("");
    },
    getConsumeRecord: function () {
        var searchRequest = {};
        MyWallet.pager.init(searchRequest);
    },
    createPager: function () {
        var page = {
            countUrl: '/yhypc/personal/countConsumeRecord.jhtml',
            resultCountFn: function (result) {
                if (result.success) {
                    return parseInt(result.count);
                } else {
                    if (result.code == "req_login") {
                        YhyUser.goLogin();
                    }
                    return 0;
                }
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: "/yhypc/personal/getConsumeRecord.jhtml",
                    data: data,
                    progress: true,
                    success: function (result) {
                        if (result.success) {
                            $('#consume_content').empty();
                            $.each(result.data, function (i, c) {
                                var consume__item = template("consume_item", c);
                                $('#consume_content').append(consume__item);
                            });
                            scroll(0, 480);
                        }
                    },
                    error: function () {
                    }
                });
            }
        };
        MyWallet.pager = new Pager(page);
    },
    doSetPayPwd: function () {
        var $cfmPwdIpt = $('#setPayPwdForm').find('input[name = "cfmPayPwd"]');
        var payPwd = $('#setPayPwdForm').find('input[name = "payPwd"]').val();
        var cfmPayPwd = $('#setPayPwdForm').find('input[name = "cfmPayPwd"]').val();
        if (!payPwd || payPwd == "") {
            $cfmPwdIpt.next('span.attention').html(null).html("支付密码不正确!");
            return;
        }
        if (!cfmPayPwd || cfmPayPwd == "") {
            $cfmPwdIpt.next('span.attention').html(null).html("确认支付密码不正确!");
            return;
        }
        if (cfmPayPwd != payPwd) {
            $cfmPwdIpt.next('span.attention').html(null).html("两次密码不一致!");
            return;
        }
        $.form.commit({
            formId: '#setPayPwdForm',
            url: '/yhypc/user/doSetPayPwd.jhtml',
            success: function (result) {
                if (result.success) {
                    MyWallet.closeSetPayPwdBox();
                    Popup.openDialog("commonSuccess");
                } else {
                    $cfmPwdIpt.next('span.attention').html(null).html(result.msg);
                }
            },
            error: function (result) {
                $cfmPwdIpt.next('span.attention').html(null).html("操作失败, 稍后重试!");
            }
        });
    }
};
$(function () {
    MyWallet.init();
});
