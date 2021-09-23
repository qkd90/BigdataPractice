/**
 * Created by zzl on 2016/12/27.
 */
var HotelIndex = {
    init: function() {
        HotelIndex.initComp();
        HotelIndex.initEvt();
    },
    initComp: function() {

        //设置初始化日期
        var date = new Date();
        $("#startDate").val($.addDate(date, 0));
        $("#endDate").val($.addDate(date, 1));

        // hotel index banner
        var mySwiper = new Swiper ('#hotel_index_top_banner', {
            direction: 'horizontal',
            loop: false,
            autoplay: 5000,
            pagination: '#hotel_index_top_page'
        });

    },
    onFoucsEndTime: function() {
        var startTime = $("#startDate").val();
        if (startTime.length <= 0) {
            return;
        }
        var date = moment(startTime);
        $("#endDate").val($.addDate(date, 1));
    },

    addDate: function(a, dadd){
        a = a.valueOf();
        a = a + dadd * 24 * 60 * 60 * 1000;
        a = new Date(a);
        var fullYear = a.getFullYear();
        var month = a.getMonth()+1 < 10? "0"+(a.getMonth()+1): a.getMonth()+1;
        var day = a.getDate() < 10? "0" +  a.getDate():  a.getDate();
        return fullYear + "-" + month + "-" + day;
    },

    initEvt: function() {
        // go hotel list btn
        $('#goHotelListBtn').on('click', function(event) {
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            var searchWord = $('#searchWord').val();
            var url = '/yhypc/hotel/list.jhtml';
            url += "?startDate=" + startDate;
            url += "&endDate=" + endDate;
            url += "&searchWord=" + encodeURI(encodeURI(searchWord));
            console.log(encodeURI(encodeURI(searchWord)));
            window.location.href = url;
        });
    }
};
$(function() {
    HotelIndex.init();
});