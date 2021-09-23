var Line = {
	initNav: function(){
		$(".mainnav").find("a").removeClass("curr");
		$("#navLine").addClass("curr");
	}
}

$(function() {
	MallLine.init();
	MallLine.initSelect();
//	MallLine.initSearch();
//	MallLine.searchBtn();
})
