/**
 * Created by vacuity on 15/11/18.
 */

var Wechatpay = {
    // 判断微信浏览器方法
    isWeiXin: function () {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        }
        else {
            return false;
        }
    },

    payOrder: function (cost) {

        if (this.isWeiXin()) {

            $.post("/pay/pay/getPrepayId.jhtml", {cost: cost}, function (result) {

                if (result.success) {
                    Wechatpay.wxPay(result.prepayId, result.orderId, Wechatpay.paySuccess);
                } else {
                    alert("出错了:" + result.errMsg);
                }
            });
        }
        else {
            alert("请在微信浏览器下使用支付功能");
        }
    },
    payOrderWithBack: function (cost, callback) {

        if (this.isWeiXin()) {

            $.post("/pay/pay/getPrepayId.jhtml", {cost: cost}, function (result) {

                if (result.success) {
                    Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                } else {
                    alert("出错了:" + result.errMsg);
                }
            });
        }
        else {
            alert("请在微信浏览器下使用支付功能");
        }
    },


    wxPay: function (prepayId, orderId, callback) {
        var ctimestamp;
        var cnonceStr;
        var cpaysign;
        var cappId;

        $.post("/pay/pay/getPayConfig.jhtml", {prepayId: prepayId}, function (result) {
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
                        callback(orderId);
                    } else {
                        Order.stopCheck();
                    }
                });
        });
    },

    paySuccess: function (orderId) {
        window.setTimeout(function () {
            Order.startCheck(orderId);
        }, 200);
    },

    alipay : function(){

    },

    makeQrcode: function () {
        var url = $("#code-url").val();
        $("#code").qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: url //任意内容
        });
    }
};
