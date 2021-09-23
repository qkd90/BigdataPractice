/**
 * Created by huangpeijie on 2017-02-09,0009.
 */
var OrderTicketPay = {
    init: function () {
        OrderPay.detailUrl = "/yhypc/personal/sailboatOrderDetail.jhtml?id=" + OrderPay.orderId + "&type=" + OrderPay.orderType;
        OrderTicketPay.initDate();
    },

    initDate: function () {
        var startDate = moment($("#startDate").val());
        $("#startDateStr").text(startDate.format("YYYY-MM-DD"));
        $("#startWeekday").text(OrderPay.weekdays[startDate.format("E")]);
    }
};

$(window).ready(function () {
    OrderTicketPay.init();
});