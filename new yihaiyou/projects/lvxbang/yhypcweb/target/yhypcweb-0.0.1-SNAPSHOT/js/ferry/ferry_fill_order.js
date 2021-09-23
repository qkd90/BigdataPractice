/**
 * Created by HMLY on 2016-12-20.
 */

var ticketFillOrder = {
    init: function () {
        ticketFillOrder.getCustomerList();
        ticketFillOrder.initEvt();
    },

    initEvt: function() {
        $('#saveTbTN').on('click', function(event) {
            event.stopPropagation();
            if (Tourist.doCheckTouristInfoForm()) {
                $.form.commit({
                    formId: '#tourist_form',
                    url: '/yhypc/personal/doSaveTourist.jhtml',
                    success: function(result) {
                        if (result.success) {
                            $.message.alert({
                                title: "提示",
                                info: "游客添加成功",
                                afterClosed: function() {
                                    Popup.closeDialog();    //关闭弹窗
                                    ticketFillOrder.getCustomerList();  //重新获取游客列表信息
                                }
                            });

                        } else {
                            $('#save_msg').html(null).html(result.msg);
                        }
                    },
                    error: function() {
                        $('#save_msg').html(null).html("操作失败, 请重试");
                    }
                });
            }
        });
    },
    checkCustomer: function (target) {

        if (!$("#ticket-type-count").val()) {
            return;
        }

        if (!$(target).is(':checked')) {
            $("#ticket-customer-" + $(target).attr("data-id") + "").remove();
            ticketFillOrder.calculateTotalPrice();
            return;
        }

        var ticketList = [];
        for (var i = 0; i < Number($("#ticket-type-count").val()); i++) {
            var ticketData = {};
            $.each($("input[name='ticket_" + i + "']"), function (j, perData) {
                ticketData['' + $(perData).attr("data-name") + ''] = $(perData).val();
            });
            ticketList.push(ticketData);
        }
        $.post("/yhypc/order/getCustomerInfo.jhtml",
            {touristId: $(target).attr("data-id")},
            function (data) {
                if (data.success) {
                    var tourist = data.tourist;
                    tourist['ticketList'] = ticketList;
                    template.config("escape", false);
                    var ticNum;
                    if (tourist.peopleType == "KID") {
                        ticNum = "E";
                    } else {
                        ticNum = "Q";
                    }
                    $.each(ticketList, function (i, ticket) {
                        if (ticket.number.indexOf(ticNum) > -1) {
                            tourist.selectedTicket = ticket;
                        }
                    });
                    var result = $(template("tpl-order-ferry-ticket-info", tourist));
                    $(".customer-ticket").append(result);
                    result.data("tourist", tourist);
                    /*datalist*/
                    ticketFillOrder.dataList($(".ticketDatalistBtn-" + $(target).attr("data-id") + ""), $(target).attr("data-id"));
                    ticketFillOrder.calculateTotalPrice();
                }
            }
            , "json");

    },

    getCustomerList: function () {
        $.post("/yhypc/order/getCustomerList.jhtml",
            function (data) {
                $(".customer-name-check").empty();
                if (data.success) {
                    var touristList = data.touristList;
                    $.each(touristList, function (i, customer) {
                        template.config("escape", false);
                        var result = $(template("tpl-order-ferry-wirte-customer", customer));
                        $(".customer-name-check").append(result);
                        result.data("customer", customer);
                    });
                }
            }
            , "json");
    },

    calculateTotalPrice: function () {
        var totalPrice = 0;
        $.each($("input[name^='perPrice-']"), function (i, perInput) {
            totalPrice += Number($(perInput).val());
        });
        $("#total-span").html('<sup>￥</sup>' + totalPrice);
    },

    /*getFlightInfo: function() {
     $.post("/yhypc/ferry/getFlightInfo.jhtml",
     {dailyFlightGid:$("#dailyFlightGid").val(), departTime: $("#departTime").val()},
     function (data) {
     $(".fill-order-content-part1").empty();
     if (data.success) {
     template.config("escape", false);
     var info = data.flightInfo;
     var result = $(template("tpl-order-ferry-info", info));
     $(".fill-order-content-part1").append(result);
     result.data("customer", info);
     }
     }
     ,"json");
     },*/


    inputTooltips: function (obj, target) {
        target.hide();
        obj.focus(function () {
            $(this).attr("style", "border-color:#f80");
            target.show();
        });

        obj.blur(function () {
            $(this).removeAttr('style');
            target.hide();
        });
    },
    dataList: function (obj, id) {
        obj.click(function (ev) {
            var ev = ev || event;
            ev.stopPropagation();
            $(".ticket-datalist").slideUp("800");
            if ($(this).hasClass('active')) {
                $(this).siblings(".ticket-datalist").slideUp("800");
                $(this).removeClass("active");
            } else {
                $(this).siblings(".ticket-datalist").slideDown("800");
                $(this).addClass("active");
            }

        });
        $("html body").click(function () {
            obj.siblings(".ticket-datalist").slideUp("800");
            obj.removeClass('active');
        });
        obj.siblings(".ticket-datalist").find("dt").click(function () {
            var touristEle = $("#ticket-customer-" + id);
            var tourist = touristEle.data("tourist");
            var ticketId = $(this).data("id");
            $.each(tourist.ticketList, function (i, ticket) {
                if (ticket.id == ticketId) {
                    tourist.selectedTicket = ticket;
                    return false;
                }
            });
            touristEle.data("tourist", tourist);
            $(":input[name='perPrice-" + id + "']").val($(this).attr("data-price"));
            $(this).closest(".ticket-datalist").siblings(".ticketDatalistBtn-" + id + "").val($(this).html());
            ticketFillOrder.calculateTotalPrice();
        });
    },

    addTourist: function() {
        Popup.openAddTourist();
    },

    submitOrder: function (thiz) {
        YhyUser.checkLogin(function (result) {
            if (result.success) {
                submit();
            } else {
                YhyUser.goLogin(submit);
            }
        });



        function submit() {
            var ticketList = [];
            var totalPrice = 0;
            var flag = true;
            var failTourstNameList = [];
            $(".ticket-body").each(function () {
                var tourist = $(this).data("tourist");
                var ticket = {
                    ticketId: tourist.selectedTicket.id,
                    ticketName: tourist.selectedTicket.name,
                    price: tourist.selectedTicket.price,
                    number: tourist.selectedTicket.number,
                    name: tourist.name,
                    idnumber: tourist.idNumber,
                    mobile: tourist.tel
                };

                if (tourist.idType == "IDCARD" && tourist.selectedTicket.number == "C1") {
                    failTourstNameList.push(tourist.name);
                }

                if (tourist.idType == "IDCARD") {
                    ticket.idType = "ID_CARD";
                } else if (tourist.idType == "REMNANTSOLDIER") {
                    ticket.idType = "REMNANT_SOLDIER";
                } else {
                    ticket.idType = "OTHER";
                }
                totalPrice += Number(ticket.price);
                ticketList.push(ticket);

            });

            if (failTourstNameList.length > 0) {
                $.message.alert({
                    title: "提示",
                    info: "当前游客《"+ failTourstNameList.join(",") +"》的证件类型不符合，请重新选择票型！"
                });
                return;
            }

            if (ticketList.length == 0) {
                $.message.alert({
                    title: "提示",
                    info: "请选择游客！"
                });
                return;
            }

            var jsonObj = {
                dailyFlightGid: $("#ferryDailyFlightGid").val(),
                flightNumber: $("#ferryNumber").val(),
                flightLineName: $("#ferryFlightLineName").val(),
                departTime: $("#ferryDepartTime").val(),
                amount: totalPrice,
                seat: ticketList.length,
                ferryOrderItemList: ticketList
            };
            saveOrder();

            function saveOrder() {
                var $this = $(thiz);
                if ($this.hasClass("nowLoading")) {
                    return;
                }
                $this.addClass("nowLoading");
                $.post("/yhypc/ferry/saveOrder.jhtml", {
                    json: JSON.stringify(jsonObj)
                }, function (data) {
                    if (data.success) {
                        location.href = "/yhypc/ferry/orderPay.jhtml?orderId=" + data.orderId;
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
                    }
                });
            }
        }
    }
};
$(function () {
    ticketFillOrder.init();
});


