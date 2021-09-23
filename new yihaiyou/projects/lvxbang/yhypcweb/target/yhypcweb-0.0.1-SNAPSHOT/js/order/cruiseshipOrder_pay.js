/**
 * Created by huangpeijie on 2017-02-09,0009.
 */
var OrderCruiseShipPay = {
    init: function () {
        OrderCruiseShipPay.initDate();
    },

    initDate: function () {
        var startDate = moment($("#startDate").val());
        $("#startDateStr").text(startDate.format("YYYY-MM-DD"));
        $("#startWeekday").text(OrderPay.weekdays[startDate.format("E")]);

        var num=0;
        $(".roomNum").each(function(){
            num += parseInt($(this).attr("num"));
        })
        $(".numOfRoom").html("<label>购买数量：" + num + "</label>");
        
    }
};

$(window).ready(function () {
    OrderCruiseShipPay.init();
});