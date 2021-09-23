/**
 * Created by dy on 2016/3/10.
 */
var Request = {
    init: function() {

    },
    toCreateOrder: function() {

        var url = "/balance/balance/createOrder.jhtml?orderId=" + $("#hipt_accountLogId").val();
        var ifr = window.parent.$("#editPanel").children()[0];
        $(ifr).attr("src", url);

    },
    toWechatPay: function() {
        var orderId = $("#hipt_orderId").val();
        //var productId = $("#hipt_productId").val();
        var random = parseInt(Math.random() * 10000);
        var url = "/balance/balance/wechatPay.jhtml?orderId=" + orderId + "&random=" + random;
        var ifr = window.parent.$("#editPanel").children()[0];
        $(ifr).attr("src", url);
    },
    toZhaohPay: function() {
        // TODO 待删除代码
        show_msg("功能完善中，请选择其他支付方式");
        return ;

        var orderId = $("#hipt_orderId").val();
        $.post("/balance/balance/findZhaohCfg.jhtml",
            {orderId: orderId},
            function(data){
                if (data && data.success) {
                    // 打开招行支付页面，页面加载没法自动提交"_blank"的表单，只能手动提交
                    $('#forwardZhFm').attr('action', data.PayUrl);
                    $('#zhaoh_BranchID').val(data.BranchID);
                    $('#zhaoh_CoNo').val(data.CoNo);
                    $('#zhaoh_Date').val(data.Date);
                    $('#zhaoh_BillNo').val(data.BillNo);
                    $('#zhaoh_MerchantURL').val(data.MerchantURL);
                    $('#forwardZhFm').submit();
                    // 延迟跳转，否则表单不能完成提交
                    setTimeout(function() {
                        var orderId = $("#hipt_orderId").val();
                        var random = parseInt(Math.random() * 10000);
                        var url = "/balance/balance/zhaohPay.jhtml?orderId=" + orderId + "&payDate=" + data.Date + "&random=" + random;
                        var ifr = window.parent.$("#editPanel").children()[0];
                        $(ifr).attr("src", url);
                    }, 1000);
                } else {
                    if (data && data.errorMsg) {
                        show_msg(data.errorMsg);
                    } else {
                        show_msg("操作失败");
                    }
                }
            },
            'json'
        );
    },
    toManage: function() {
        window.parent.$("#editPanel").dialog("close");
    }
}

$(function() {
   Request.init();
});