var OrderHotelPay = {
    init: function () {
        OrderPay.detailUrl = "/yhypc/personal/hotelOrderDetail.jhtml?id=" + OrderPay.orderId + "&type=" + OrderPay.orderType;
        OrderHotelPay.initDate();
    },

    initDate: function () {
        var startDate = moment($("#startDate").val());
        var endDate = moment($("#endDate").val());
        $("#startDateStr").text(startDate.format("YYYY-MM-DD"));
        $("#startWeekday").text(OrderPay.weekdays[startDate.format("E")]);
        $("#endDateStr").text(endDate.format("YYYY-MM-DD"));
        $("#endWeekday").text(OrderPay.weekdays[endDate.format("E")]);
    }
};

$(window).ready(function () {
    OrderHotelPay.init();
});