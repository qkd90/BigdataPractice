/**
 * Created by vacuity on 16/1/4.
 */

var LxbPay = {
    payOrder: function (orderId) {

        var orderId = $("#orderId").val();
        var random = parseInt(Math.random() * 10000);
        if ($("#wechat-pay").hasClass("checked")) {
            // wechat
            window.location.href = "/lvxbang/lxbPay/wechatPay.jhtml?orderId=" + orderId + "&random=" + random;
        } else if ($("#ali-pay").hasClass("checked")) {
            // ali
            window.location.href = "/lvxbang/lxbPay/aliPay.jhtml?orderId=" + orderId + "&random=" + random;
        } else {
            // card
            window.location.href = "/lvxbang/lxbPay/cmbPay.jhtml?orderId=" + orderId + "&random=" + random;
        }

    }

}

$(function(){
    var d = {};
    d['orderId'] = $('#orderId').val();
    setInterval(function () {
        $.ajax({
            url:"/lvxbang/order/orderStatus.jhtml",
            type:"post",
            data:d,
            dataType:"json",
            success:function(data){
                if(data[0] == "PAYED") {
                    if (data[1] == "train1" || data[1]=="flight1") {
                        window.location.href = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + $('#orderId').val();
                    }
                    if (data[1] == "train2" || data[1] == "flight2") {
                        window.location.href = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + $('#orderId').val();
                    }
                    if (data[1] == "ticket" ) {
                        window.location.href = "/lvxbang/order/ticketOrderDetail.jhtml?orderId=" + $('#orderId').val();
                    }
                    if (data[1] == "hotel" ) {
                        window.location.href = "/lvxbang/order/hotelOrderDetail.jhtml?orderId=" + $('#orderId').val();
                    }
                    if (data[1] == "plan" ) {
                        window.location.href = "/lvxbang/order/planOrderDetail.jhtml?orderId=" + $('#orderId').val();
                    }

                }
                //console.log(data);
            },
            error:function(data){
                //console.log(data);
            }
        });
    }, 2000);

    $(".Pay .more_bold").click(function () {
        $(this).parents(".price").next().slideToggle();
    });

    //$.post("/lvxbang/help/dataListBykeyword.jhtml",
    //    {
    //        'keyword': "线路下单合同条款"
    //    },
    //    function (data) {
    //        $(".Pay .contract").html(data.content);
    //    }
    //);
});
