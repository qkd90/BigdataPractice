/**
 * Created by vacuity on 16/1/14.
 */

var WechatPay = {
    makeQrcode: function (wechatCode) {
        $("#qrcode").qrcode({
            render: "canvas", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: wechatCode //任意内容
        });
    }
}

$(function () {
    //LxbPay.wechatPay();
    var code = $("#code").val();
    WechatPay.makeQrcode(code);
});