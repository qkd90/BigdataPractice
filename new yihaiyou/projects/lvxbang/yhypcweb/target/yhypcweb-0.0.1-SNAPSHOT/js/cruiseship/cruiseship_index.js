/**
 * Created by HMLY on 2016-12-19.
 */

var CruiseshipIndex = {
    init: function() {
        CruiseshipIndex.IECss();
        CruiseshipIndex.placeholderFn($('.brandPlaceholder'));
        CruiseshipIndex.placeholderFn($('.routPlaceholder'));
        CruiseshipIndex.datalist($("#location"), $("#search-cruiseship-line"), $("#location-list"), $("#category"), $("#departureDate"));
        CruiseshipIndex.datalist($("#category"), $("#search-cruiseship-brand"), $("#category-list"), $("#location"), $("#departureDate"));

        CruiseshipIndex.initJsp();
        //CruiseshipIndex.datalist($("#departureDate"),$("#departureDate-list"),$("#location"),$("#category"));
        CruiseshipIndex.initSwiper();
    },


    initJsp: function() {

        //设置初始化日期
        var date = new Date();
        var fullYear = date.getFullYear();
        var month = date.getMonth()+1 < 10? "0"+(date.getMonth()+1): date.getMonth()+1;
        var day = date.getDate();

        $("#departureDate").val(fullYear + "-" + month + "-" + day);

        /*$('#departureDate').datetimepicker({
            closeOnDateSelect: true,
            scrollInput: false,
            todayButton: false,
            timepicker: false,
            format: "YYYY-MM-DD",
            formatDate: "YYYY-MM-DD",
            value: new Date(),
            minDate: 0
        });*/
    },

    initSwiper: function() {
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal',
            loop: true,
            autoplay:3000,
            // 如果需要分页器
            pagination: '.swiper-pagination',
            paginationClickable :true,
            autoplayDisableOnInteraction : false
        })
    },

    /*轮播中   下拉菜单*/
    datalist: function (obj, formObj, tagObj, silbings1, slibings2){
    tagObj.hide();
    var onOff = true;
    obj.click(function(ev){
        var ev = ev || event;
        ev.stopPropagation();
        if(tagObj[0].style.display == 'none'){
            tagObj.slideDown('2000');
        }else{
            tagObj.slideUp('2000');
        }

        tagObj.find('li').hover(
            function(){
                $(this).addClass('active');
            },function(){
                $(this).removeClass('active');
            }
        );
        tagObj.find('li').click(function(){
            tagObj.slideUp('2000');
            var inputVal = $(this).html();
            var dataId = $(this).attr("data-id");
            formObj.val(dataId);
            obj.val(inputVal);
            obj.next('span').hide();
            //obj.attr("data-id", dataId);
        });
    });
    silbings1.click(function(){
        tagObj.slideUp('2000');
    });
    slibings2.click(function(){
        tagObj.slideUp('2000');
    });
    $("body").click(function(){
        tagObj.slideUp('2000');
    });
},
    IECss:function(){
        $('.ie8-placeholder').hide();
        var explorer = window.navigator.userAgent ;
        if (explorer.indexOf("MSIE") >= 0) {
            var aLi1 = $('.recommend-content-right:first ul li');
            aLi1.eq(0).css({
                float:'left',
                marginBottom:'18px',
                width:'248px'
            });
            aLi1.eq(1).css({
                float:'right',
                marginBottom:'18px',
                width:'248px'
            });
            aLi1.eq(2).css({
                float:'left',
                width:'248px'
            });
            aLi1.eq(3).css({
                float:'right',
                width:'248px'
            });
            var aLi2 = $('.recommend-content-right:last ul li');
            aLi2.eq(0).css({
                float:'left',
                marginBottom:'18px',
                width:'248px'
            });
            aLi2.eq(1).css({
                float:'right',
                marginBottom:'18px',
                width:'248px'
            });
            aLi2.eq(2).css({
                float:'left',
                width:'248px'
            });
            aLi2.eq(3).css({
                float:'right',
                width:'248px'
            });
        }
    },
    /*输入框placeholder 兼容IE8*/
    placeholderFn:function(obj){
        var explorer = window.navigator.userAgent ;
        var inputObj = obj.prev('input');
        if (explorer.indexOf("MSIE") >= 0) {
            obj.show();
        }else{
            obj.hide();
        }
    }
};
$(function(){
    CruiseshipIndex.init();

});


