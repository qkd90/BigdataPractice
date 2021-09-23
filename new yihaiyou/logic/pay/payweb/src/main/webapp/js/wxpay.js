/**
 * Created by vacuity on 15/11/18.
 */

var Wechatpay={
    // 判断微信浏览器方法
    isWeiXin: function() {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        }
        else {
            return false;
        }
    },

    payOrder: function (orderId) {

        if (this.isWeiXin()) {

            $.post("/pay/payMobile/getPrepayId.jhtml", {orderId: orderId}, function (result) {

                if (result.success) {
                    Wechatpay.wxPay(result.prepayId, Wechatpay.paySuccess);
                } else {
                    alert("出错了:" + result.errMsg);

                }
            });
        }
        else {
        	alert("请在微信浏览器下使用支付功能");
        }
    },


    wxPay: function (prepayId, callback) {
        var ctimestamp;
        var cnonceStr;
        var cpaysign;
        var cappId;

        $.post("/pay/payMobile/getPayConfig.jhtml", {prepayId: prepayId}, function (result) {
            //$(".weixin-loading").hide();
            //if (!result.success) {
            //    alert("无法获取配置信息");
            //}


            ctimestamp = result.timeStamp;
            cnonceStr = result.nonceStr;
            cpaysign = result.paySign;
            cappId = result.appId;

            WeixinJSBridge.invoke('getBrandWCPayRequest',
                {
                    appId: cappId,
                    timeStamp: ctimestamp + '',
                    nonceStr: cnonceStr,
                    package: 'prepay_id=' + prepayId,
                    signType: 'MD5',
                    paySign: cpaysign,
                },
                function (res) {

                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        callback();
                    }
                });
        });
    },

    paySuccess: function () {
        window.setTimeout(function () {
            window.location.href = "/pay/payMobile/paySuccess.jhtml?orderId=" + $("#order-id").val();
        }, 200);
    },

    makeQrcode: function () {
        var url = $("#code-url").val();
        $("#code").qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height:200, //高度
            text: url //任意内容
        });
    }
}
