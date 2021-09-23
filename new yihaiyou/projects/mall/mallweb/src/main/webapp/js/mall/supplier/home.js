/**
 * Created by vacuity on 15/11/17.
 */

var HomeSearch = {
    productSearch: function(){
        $("#search-btn").click(function () {
            HomeSearch.searchByName();
        });
    },

    searchByName: function(){
        var productType = $("#product-type").val();
        var name = $("#product-name").val();
        var unitId = $("#unit-id").val();
        var url = "";
        if (productType == "线路") {
            url = "/mall/supplier/lines.jhtml?id=" + unitId + "&lineName=" + name;
        } else if (productType == "门票") {
            url = "/mall/ticket/gysTicket.jhtml?supplierId=" + unitId + "&ticketName=" + name;
        }
        window.location.href = url;
    },

    AgentBtn: function(){
        refreshTable();
    }
}


$(function() {
    HomeSearch.productSearch();
    HomeSearch.AgentBtn();
})