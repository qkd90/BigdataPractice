/**
 * Created by vacuity on 15/11/13.
 */

var SupplierLine = {
    initNav: function(){
        $(".mainnav").find("a").removeClass("curr");
        $(".mainnav").find(".supplier-line").addClass("curr");
    }
}

$(function() {

    SupplierLine.initNav();
    MallLine.init();
    MallLine.initSelect();
//	MallLine.initSearch();
//	MallLine.searchBtn();
})
