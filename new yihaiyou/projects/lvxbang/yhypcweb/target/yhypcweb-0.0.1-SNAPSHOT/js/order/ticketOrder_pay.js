/**
 * Created by huangpeijie on 2017-02-09,0009.
 */
var OrderSailBoatPay = {
    init: function () {
        OrderPay.detailUrl = "/yhypc/personal/ticketOrderDetail.jhtml?id=" + OrderPay.orderId + "&type=" + OrderPay.orderType;
        OrderSailBoatPay.initDate();
    },

    initDate: function () {
        var startDate = moment($("#startDate").val());
        $("#startDateStr").text(startDate.format("YYYY-MM-DD"));
        $("#startWeekday").text(OrderPay.weekdays[startDate.format("E")]);
    }
};

$(window).ready(function () {
    OrderSailBoatPay.init();
});