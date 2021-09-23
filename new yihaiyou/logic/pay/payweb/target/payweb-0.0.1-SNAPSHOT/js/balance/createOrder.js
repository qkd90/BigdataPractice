/**
 * Created by dy on 2016/3/10.
 */


var CreateOrder = {

    init: function() {

        var money = $("#hipt_rechargeMoney").val();
        if (money) {
            $("#ipt_money").numberbox("setValue", money);
        }

    },

    createOrder: function() {
        var url = "/balance/balance/subPreRecharge.jhtml";

        $("#perRechargeForm").form();

        $('#perRechargeForm').form('submit', {
            url:url,
            onSubmit: function(){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.show("请填写充值金额！");
                }
                return isValid;	// 返回false终止表单提交

            },
            success:function(result){
                var result = eval('(' + result + ')');

                if (result.success) {
                    url = "/balance/balance/request.jhtml?orderId=" + result.orderId;
                    var ifr = window.parent.$("#editPanel").children()[0];
                    $(ifr).attr("src", url);
                } else {
                    show_msg(result.errorMsg);
                }

            }
        });
    },
    toManage: function() {
        window.parent.$("#editPanel").dialog("close");
    }

}

$(function() {
    CreateOrder.init();
});