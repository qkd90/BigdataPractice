/**
 * Created by HMLY on 2016-12-19.
 */
$(function(){
    SailboatIndex.init();
});

var SailboatIndex = {
    init: function() {
        SailboatIndex.placeholderFn($('.locationPlaceholder'));
        SailboatIndex.placeholderFn($('.categoryPlaceholder'));
        SailboatIndex.datalist($("#location"), $("#location_scenicId"), $("#location-list"), $("#category"));
        SailboatIndex.datalist($("#category"), $("#location_ticketType"), $("#category-list"), $("#location"));
        SailboatIndex.initSwiper();
    },
    initCom: function() {
        $(".btn-search").click(function() {
            var scenicId = $("#location").attr("data-id");
            var ticketType = $("#category").attr("data-id");
            SailboatIndex.toList(scenicId, ticketType)
        });
    },
    toList: function(scenicId, ticketType) {
        window.location.href = '';
    },
    initSwiper: function() {
        /*轮播组件swiper*/
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
    datalist: function(obj, hidObj, tagObj, silbings) {
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
                hidObj.val(dataId);
                obj.val(inputVal);
                obj.next('span').hide();
            });
        });
        silbings.click(function(){
            tagObj.slideUp('2000');
        });
        $("body").click(function(){
            tagObj.slideUp('2000');
        });
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
