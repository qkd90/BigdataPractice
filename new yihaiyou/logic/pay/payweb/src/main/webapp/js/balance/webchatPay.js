/**
 * Created by dy on 2016/3/10.
 */
var WebchatPay = {

    checkPayResult: function() {
        window.setInterval(function () {
            var url = "/balance/balance/checkPayBackResult.jhtml";
            var data = {
                orderId:$("#hipt_orderId").val()
            };
            $.post(url, data, function(result) {
                if (result.success) {
                    if (result.orderStatus == 'SUCCESS') {
                        var surl = "/balance/balance/paySuccess.jhtml?orderId=" + result.orderId;
                        var ifr = window.parent.$("#editPanel").children()[0];
                        $(ifr).attr("src", surl);
                    } else if (result.orderStatus == 'FAILED') {
                        var furl = "/balance/balance/payFail.jhtml?orderId=" + result.orderId;
                        var ifr = window.parent.$("#editPanel").children()[0];
                        $(ifr).attr("src", furl);
                    }
                }
            });
        }, 500);
    },

    makeQrcode: function (wechatCode) {
        $("#qrcode").qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: wechatCode //任意内容
        });
    }
    //paySuccess: function () {
    //
    //
    //
    //
    //    //window.setTimeout(function () {
    //    //
    //    //    //window.location.href = "/pay/payMobile/paySuccess.jhtml?orderId=" + $("#order-id").val();
    //    //}, 1000);
    //}

}
$(function() {
    var code = $("#code").val();
    WebchatPay.makeQrcode(code);
    WebchatPay.checkPayResult();
    //WebchatPay.paySuccess();



});
//var time = setInterval("checkPayResult()",1000);//1000为1秒钟
