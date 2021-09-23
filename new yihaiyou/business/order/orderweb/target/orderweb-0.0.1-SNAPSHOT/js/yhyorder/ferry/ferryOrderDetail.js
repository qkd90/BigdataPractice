/**
 * Created by dy on 2016/2/23.
 */
var ferryOrderDetail = {
    init: function() {
    },
    // 订单退款（用户已支付，订单是成功状态） - 轮渡船票
    doDefundFerry: function(id) {
        $.messager.confirm('温馨提示', '确认提交轮渡退款申请？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/yhyorder/yhyFerryOrder/defundOrder.jhtml",
                    {ferryOrderId : id},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            show_msg("退款成功");
                            location.reload(true);
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
            }
        });
    },
    // 订单退款（用户已支付，订单不是成功状态） - 轮渡船票
    doFailOrderRefundFerry: function(id) {
        $.messager.confirm('温馨提示', '确认执行退款操作？', function(r) {
            if (r) {
                var requestData = {
                    'orderId': id
                };
                $.messager.progress();
                $.post("/balance/balance/doFailOrderRefundFerry.jhtml", requestData,
                    function (data) {
                        $.messager.progress('close');
                        if (data.flag) {
                            show_msg("退款成功");
                            location.reload(true);
                        } else {
                            if (data && data.reMsg) {
                                show_msg(data.reMsg);
                            } else {
                                show_msg("操作失败");
                            }
                        }
                    }
                );
            }
        });
    }

};

$(function(){
    ferryOrderDetail.init();
});