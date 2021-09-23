/**
 * Created by HMLY on 2016-12-20.
 */

var SailboatOrderWrite = {
    fullCalendar: null,
    init: function () {
        SailboatOrderWrite.initJsp();
        SailboatOrderWrite.getCustomerList();
        SailboatOrderWrite.initEvt();
        SailboatOrderWrite.submitOrder();
    },
    initJsp: function () {
        if (!$("#play-date").val()) {
            $("#play-date").val(moment().add(1, 'd').format("YYYY-MM-DD"));
        }

        //if (!SailboatOrderWrite.fullCalendar) {
        //    SailboatOrderWrite.fullCalendar = SailboatOrderWrite.initPriceCalendar();
        //}
        //$("#price-calendar").fullCalendar('select', $("#play-date").val());

        $("#play-date").click(function (e) {
            $(".calendar-shadom").show();
            $("#price-calendar").show();
            if (!SailboatOrderWrite.fullCalendar) {
                SailboatOrderWrite.fullCalendar = SailboatOrderWrite.initPriceCalendar();
            }
            //$("#price-calendar").fullCalendar('select', $("#play-date").val());
        });

        $(".calendar-shadom").click(function(){
            $(this).hide();
            $('#price-calendar').fadeOut();
        });
    },

    addTourist: function() {    //新增游客
        Tourist.clearTouristInfoForm();
        Popup.openAddTourist();
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
                                    SailboatOrderWrite.getCustomerList();  //重新获取游客列表信息
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

    getCustomerList: function() {
        $.post("/yhypc/order/getCustomerList.jhtml",
            function (data) {
                $(".customer-name-check").empty();
                if (data.success) {
                    var touristList = data.touristList;
                    $.each(touristList, function (i, customer) {
                        template.config("escape", false);
                        var result = $(template("tpl-order-sailboat-wirte-customer", customer));
                        $(".customer-name-check").append(result);
                        result.data("customer", customer);
                    });
                }
            }
            , "json");
    },


    initPriceCalendar: function () {
        SailboatOrderWrite.fullCalendar = $('#price-calendar').fullCalendar({
            theme: true,
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            defaultDate: moment().format("YYYY-MM-DD"),
            lang: 'zh-cn',
            editable: false,
            eventLimit: false, // allow "more" link when too many events
            weekNumbers: false,
            fixedWeekCount: false,
            selectable: true,
            droppable: false,
            dayClick: SailboatOrderWrite.dayClick,
            eventClick: SailboatOrderWrite.eventClick
        });
        SailboatOrderWrite.setDateSource();
    },


    setDateSource: function () { //设置价格日历表
        var url = "/yhypc/order/getTicketDatePriceList.jhtml";
        var postData = {
            ticketPriceId: $('#ticketPriceId').val()
        };
        if ($('#ticketPriceId').val()) {
            $.post(url,
                postData,
                function (result) {
                    if (result.success) {
                        var calendarData = [];
                        $.each(result.ticketDatepriceList, function (i, datePrice) {
                            calendarData.push({
                                id: datePrice.huiDate.substring(0, 10),
                                ticketDatePriceId: datePrice.id,
                                price: (datePrice.priPrice),
                                date: datePrice.huiDate.substring(0, 10),
                                title: '¥' + (datePrice.priPrice)
                            });
                        });
                    }
                    $('#price-calendar').fullCalendar('removeEvents');
                    $('#price-calendar').fullCalendar('addEventSource', calendarData);
                }
            );
        }
    },
    dayClick: function (date, allDay, jsEvent, view) {
        var calEvent = $("#price-calendar").fullCalendar('clientEvents', date)[0];
        console.log("1");
        console.log(calEvent);
    },
    eventClick: function (event, jsEvent, view) {
        $("#price-calendar").fullCalendar('select', moment(event.date));
        $('#play-date').val(event.date);
        $(".calendar-shadom").hide();
        $('#price-calendar').fadeOut();
        $("#ticket-per-price").val(event.price);
        $("#ticketDatePriceId").val(event.ticketDatePriceId);
        SailboatOrderWrite.statisticOrder();
        //console.log(event);
    },
    statisticOrder: function () {
        var perPrice = $("#ticket-per-price").val();
        if (!perPrice) {
            perPrice = 0;
        }
        var checkedCustomerList = $(".customer-name-check").find(":input[name='customer']:checked");
        var count = checkedCustomerList.length;
        $("#ticket-count-span").html(count);

        var m=0,s1=perPrice.toString(),s2=count.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        var totalPrice = Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);

        $("#ticket-price-span").html('<sub>￥</sub>' + totalPrice);
        $("#ticket-total-price-span").html('<sup>￥</sup>' + totalPrice);

    },

    submitOrder: function () {
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
                var contactName = $("#contactName").val();
                var contactTel = $("#contactTel").val();
                if (contactName == null || contactName == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写联系人姓名"
                    });
                    return;
                }
                if (!contactName.match(Reg.nameReg) || contactName.length > 10) {
                    $.message.alert({
                        title: "提示",
                        info: "联系人姓名有误"
                    });
                    return;
                }

                if (contactTel == null || contactTel == "") {
                    $.message.alert({
                        title: "提示",
                        info: "请填写联系人手机"
                    });
                    return;
                }
                if (!contactTel.match(Reg.telephoneReg)) {
                    $.message.alert({
                        title: "提示",
                        info: "联系人手机有误"
                    });
                    return;
                }
                var checkedCustomerList = $(".customer-name-check").find(":input[name='customer']:checked");
                if (checkedCustomerList.length < 1) {
                    $.message.alert({
                        title: "提示",
                        info: "请选择游客"
                    });
                    return;
                }
                var ticketDatePriceId = $("#ticketDatePriceId").val();

                $this.addClass("nowLoading");
                $.ajax({
                    url: "/yhypc/order/checkTicketOrder.jhtml",
                    data: {
                        ticketDatePriceId: ticketDatePriceId,
                        num: checkedCustomerList.length
                    },
                    progress: true,
                    success: function (data) {
                        if (data.success) {
                            var tourists = [];
                            $.each(checkedCustomerList, function (i, ele) {
                                var tourist = $(ele).parents(".checkbox-group").data("customer");
                                tourists.push({
                                    name: tourist.name,
                                    phone: tourist.tel,
                                    peopleType: tourist.peopleType,
                                    idType: tourist.idType,
                                    idNum: tourist.idNumber
                                });
                            });
                            var playDate = $('#play-date').val();
                            var jsonObj = {
                                id: 0,
                                name: "<" + $("#priceName").val() + ">" + $("#datepriceName").val(),
                                playDate: playDate,
                                contact: {
                                    name: contactName,
                                    telephone: contactTel
                                },
                                orderType: $("#ticketType").val()
                            };
                            var details = [];
                            var detail = {
                                id: $("#ticketId").val(),
                                priceId: $("#ticketPriceId").val(),
                                price: $("#ticket-per-price").val(),
                                startTime: playDate,
                                endTime: playDate,
                                num: checkedCustomerList.length,
                                type: "scenic",
                                seatType: $("#datepriceName").val(),
                                tourist: tourists
                            };
                            details.push(detail);
                            jsonObj.details = details;
                            $.ajax({
                                url: "/yhypc/order/save.jhtml",
                                data: {
                                    json: JSON.stringify(jsonObj)
                                },
                                progress: true,
                                success: function (data) {
                                    if (data.success) {
                                        location.href = "/yhypc/order/orderSailboatPay.jhtml?orderId=" + data.order.id;
                                    }
                                    $this.removeClass("nowLoading");
                                },
                                dataType: "json"
                            });
                        } else {
                            $this.removeClass("nowLoading");
                            $.message.alert({
                                title: "提示",
                                info: "门票库存不足"
                            });
                        }
                    },
                    dataType: "json"
                });
            }
        });
    }
};

$(function () {
    SailboatOrderWrite.init();
    inputTooltips($(".customer-name-input"), $(".fill-order-name-tooltips"));
    inputTooltips($(".customer-tel-input"), $(".fill-order-tel-tooltips"));
});
/*input  输入框提示*/
function inputTooltips(obj, target) {
    target.hide();
    obj.focus(function () {
        $(this).attr("style", "border-color:#f80");
        target.show();
    });
    obj.blur(function () {
        $(this).removeAttr('style');
        target.hide();
    })
}