/**
 * Created by vacuity on 15/11/18.
 */

$(function() {
    var url = $("#code-url").val();
    if (url != null) {
        Wxpay.makeQrcode();
    }
});
