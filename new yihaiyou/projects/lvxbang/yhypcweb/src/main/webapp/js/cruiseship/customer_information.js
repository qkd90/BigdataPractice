/**
 * Created by Administrator on 2017/6/7.
 */
var customerInformation = {
    init:function(){
        customerInformation.radioFn();
        customerInformation.checkboxFn();
        //customerInformation.accountPrice();
    },
    /*//计算价格
    accountPrice: function(){
        var totalPrice = 0;
        var price = parseInt($("#price").val());
        totalPrice += parseInt(price * ($("#adult").val() + $("#children").val()));
        $(".pay-price").html("总计：<span><i>¥</i>" + totalPrice + "</span>");
    },*/
    //提交订单
    saveCustomerInfo: function () {
        var telephone = $("#phone").val();
        if (telephone == null || telephone == "") {
            $.message.alert({
                title: "提示",
                info: "请输入手机号"
            });
            return;
        }
        if (!telephone.match(Reg.telephoneReg)) {
            $.message.alert({
                title: "提示",
                info: "手机号格式错误"
            });
            return;
        }
        var details = [];
        $(".orderDetails").each(function () {

            var touristList = [];
            $(this).find(".person-group").each(function() {

                touristList.push({
                    name: $(this).find(".peopleName").val(),
                    peopleType: $(this).find(".person-group").attr("name")
                });
            });
            var detail = {
                id: $("#dateId").val(),
                priceId: $(this).attr("roomId"),
                price: $(this).attr("price"),
                seatType: $(this).attr("roomName"),
                startTime: $(".playDate").attr("name"),
                endTime: $(".playDate").attr("endDate"),
                num: touristList.length,
                type: "cruiseship",
                tourist: touristList
            };
            details.push(detail);
        });

        var jsonObj = {
            id: 0,
            name: $(".shipName").attr("name"),
            playDate: $(".playDate").attr("name"),
            contact: {
                name: $("#userName").val(),
                telephone: $("#phone").val()
            },

            orderType: "cruiseship"
        };
        jsonObj.details= details;
        $.ajax({
            url: "/yhypc/order/save.jhtml",
            data: {
                json: JSON.stringify(jsonObj)
            },
            progress: true,
            success: function (data) {
                if (data.success) {
                    location.href = "/yhypc/order/orderCruiseshipPay.jhtml?orderId=" + data.order.id;
                }
            },
            dataType: "json"
        });

    },


    /*单选*/
    radioFn:function () {
        $(".input-radio").find("label").click(function(){
            if($(this).find("input[type=radio]").checked){
               $(this).removeClass("active").siblings().addClass("active");
            }else{
                $(this).addClass("active").siblings().removeClass("active");
            }
        });
    },
    /*多选*/
    checkboxFn:function(){
        $(".contract-agree").click(function(){
            if($(this).find("input[type=checkbox]")[0].checked){
                $(this).addClass("active");
            }else{
                $(this).removeClass("active");
            }
        });
    },
};
$(function(){
    customerInformation.init();
});