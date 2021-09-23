/**
 * Created by Administrator on 2017/6/1.
 */
var ProductDetails = {
        init: function () {
            ProductDetails.swiper();
            ProductDetails.txtslide();
        },
        swiper: function () {
            /*大图轮播*/
            var viewSwiper = new Swiper(".order-swiper .views", {
                onSlideChangeStart: function () {
                    updateNavPosition();
                }
            });
            var previewSwiper = new Swiper(".order-swiper .preview", {
                visibilityFullFit: true,
                slidesPerView: 5,
                onlyExternal: true,
                onSlideClick: function () {
                    viewSwiper.swipeTo(previewSwiper.clickedSlideIndex)
                }
            });
            $(".btn-prev").click(function (e) {
                var e = e || event;
                e.preventDefault();
                if (viewSwiper.activeIndex == 0) {
                    viewSwiper.swipeTo(viewSwiper.slides.length - 1, 1000);
                    return;
                }
                viewSwiper.swipePrev()
            });

            $(".btn-next").click(function () {
                var e = e || event;
                e.preventDefault();
                if (viewSwiper.activeIndex == viewSwiper.slides.length - 1) {
                    viewSwiper.swipeTo(0, 1000);
                    return;
                }
                viewSwiper.swipeNext()
            });
            function updateNavPosition() {
                $('.preview .active').removeClass('active');
                var activeNav = $('.preview .swiper-slide').eq(viewSwiper.activeIndex).addClass('active');
                if (!activeNav.hasClass('swiper-slide-visible')) {
                    if (activeNav.index() > previewSwiper.activeIndex) {
                        var thumbsPerNav = Math.floor(previewSwiper.width / activeNav.outerWidth(true)) - 1;
                        previewSwiper.swipeTo(activeNav.index() - thumbsPerNav);
                    } else {
                        previewSwiper.swipeTo(activeNav.index());
                    }
                }
            };
        },
        /*文本显示隐藏*/
        txtslide:function(){
            $(".txt-slide").click(function(){
                var height = $(this).siblings(".txt-mask").find(".txt").outerHeight(true);
                if($(this).closest(".txt-wrap").hasClass("active")){
                    $(this).closest(".txt-wrap").removeClass("active");
                    $(this).siblings(".txt-mask").animate({"height":"128px"},400);
                }else{
                    $(this).closest(".txt-wrap").addClass("active");
                    $(this).siblings(".txt-mask").animate({"height":height},400);
                }
            });
        }
    };
$(function(){
    ProductDetails.init();
});