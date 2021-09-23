/**
 * Created by dy on 2016/3/10.
 */
var PaySuccess = {

    backManage: function() {
        window.parent.location.reload();
        window.parent.$("#editPanel").dialog("close");
        window.parent.$("#tabs").tabs("select", 0);
    },

    toCreateOrder: function() {
        var random = parseInt(Math.random() * 10000);
        var url = "/balance/balance/createOrder.jhtml?random=" + random;
        var ifr = window.parent.$("#editPanel").children()[0];
        $(ifr).attr("src", url);
    }
}
$(function() {

});
