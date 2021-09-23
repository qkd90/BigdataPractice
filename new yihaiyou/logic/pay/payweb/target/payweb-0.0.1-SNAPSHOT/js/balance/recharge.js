/**
 * Created by dy on 2016/3/8.
 */

var Recharge = {

    init: function() {

    },









    toRechargeSuccess: function() {
        var url = "/balance/balance/rechargeSuccess.jhtml";

        var ifr = window.parent.$("#editPanel").children()[0];
        $(ifr).attr("src", url);
    },






};

$(function() {
   Recharge.init();
})
