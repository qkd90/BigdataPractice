var FerryOrderDetail = {

    init: function () {
        FerryOrderDetail.initComp();
        FerryOrderDetail.initEvt();
    },
    initComp: function () {

    },
    initEvt: function () {
        $(".ferrycanclebtn").on("click", function () {
            var orderId = $(this).data("orderId");
            $.message.confirm({
                title: "提示",
                info: "确定取消订单",
                yes: function () {
                    $.post("/yhypc/ferry/cancelOrder.jhtml", {
                        orderId: orderId
                    }, function (data) {
                        if (data.success) {
                            $.message.alert({
                                title: "提示",
                                info: "订单取消成功"
                            });
                            location.reload();
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
    },
};

$(function () {
    FerryOrderDetail.init();
});