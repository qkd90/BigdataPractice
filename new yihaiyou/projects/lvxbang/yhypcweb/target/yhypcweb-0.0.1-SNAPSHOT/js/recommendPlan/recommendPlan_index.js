/**
 * Created by dy on 2017/1/13.
 */
var RecommendPlanIndex = {
    init: function() {
        RecommendPlanIndex.initSwiper();
    },

    initSwiper: function() {
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal',
            loop: true,
            autoplay:5000
            //pagination: '.swiper-pagination'
        })
    }
};

$(function() {
    RecommendPlanIndex.init();
})