var zhaohPay = {

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
    // 打开招行支付页面，页面加载没法自动提交"_blank"的表单，只能手动提交
    openZhaohPage: function () {
        //$('#forwardZhFm').submit();
        // TODO 启动定时查询结果
    },
    toManage: function() {
        window.parent.$("#editPanel").dialog("close");
    }

}
$(function() {
    //zhaohPay.openZhaohPage();
    zhaohPay.checkPayResult();
});
