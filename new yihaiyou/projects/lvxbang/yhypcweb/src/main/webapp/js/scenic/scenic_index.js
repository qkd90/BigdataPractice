/**
 * Created by zzl on 2017/1/12.
 */
var ScenicIndex = {
    init: function () {
        ScenicIndex.initComp();
        ScenicIndex.initEvt();
    },
    initComp: function () {
        var bleft = $('.selectBar').position().left;
        $('.outadvertBox').css({'left':bleft});
        window.onresize=function(){
            var bleft = $('.selectBar').position().left;
            $('.outadvertBox').css({'left':bleft});
        }
        // scenic index banner
        var mySwiper = new Swiper ('#scenic_index_top_banner', {
            direction: 'horizontal',
            loop: false,
            autoplay: 5000,
            pagination: '#scenic_index_top_page'
        });
    },
    initEvt: function() {
        $('#searchBtn').on('click', function(event) {
            var searchWord = $('#searchWord').val();
            searchWord = encodeURI(encodeURI(searchWord));
            var url = "/yhypc/scenic/list.jhtml";
            url += "?searchWord=" + searchWord;
            window.location.href = url;
        });
    }
};
$(function () {
    ScenicIndex.init();
});