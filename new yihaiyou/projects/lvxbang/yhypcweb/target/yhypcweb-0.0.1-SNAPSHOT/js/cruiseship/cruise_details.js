/**
 * Created by Administrator on 2017/6/7.
 */
var cruiseDetails = {
    init:function(){
        cruiseDetails.bannerSwiper();
        cruiseDetails.foodSwiper();
        cruiseDetails.playSwiper();
        cruiseDetails.bannerSlider();
        cruiseDetails.cabinSlider();
        cruiseDetails.sectionNav($(".cabin-nav-wrap"),$(".cabin-group"),'.cabin-group');
        cruiseDetails.sectionNav($(".food-nav-wrap"),$(".restaurant-group"),'.restaurant-group');
        cruiseDetails.sectionNav($(".play-nav-wrap"),$(".play-group"),".play-group");
        cruiseDetails.navAchor();
    },
    bannerSwiper:function(){
        var bannerSwiper = new Swiper(".banner-swiper",{
            loop:true,
            autoplay:3000,
            pagination : '.pagination',
            paginationClickable :true,
            onlyExternal: true
        });
    },
    /*美食   轮播*/
    foodSwiper:function(){
        /*主餐厅*/
        var mainFood = new Swiper('.main-restaurant',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".main-prev").click(function(){
            mainFood.swipePrev();
        });
        $(".main-next").click(function(){
            mainFood.swipeNext();
        });
        /*自助餐厅*/
        var cafeteria = new Swiper('.cafeteria',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".cafeteria-prev").click(function(){
            cafeteria.swipePrev();
        });
        $(".cafeteria-next").click(function(){
            cafeteria.swipeNext();
        });
        /*特色餐厅*/
        var feature = new Swiper('.feature-restaurant',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".feature-prev").click(function(){
            feature.swipePrev();
        });
        $(".feature-next").click(function(){
            feature.swipeNext();
        });
        /*酒吧*/
        var wineBar = new Swiper('.wine-bar',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".bar-prev").click(function(){
            wineBar.swipePrev();
        });
        $(".bar-next").click(function(){
            wineBar.swipeNext();
        });

    },
    /*邮轮娱乐  轮播*/
    playSwiper:function(){
        /*船上娱乐*/
        var playBoat = new Swiper('.play-boat',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".boat-prev").click(function(){
            playBoat.swipePrev();
        });
        $(".boat-next").click(function(){
            playBoat.swipeNext();
        });
        /*运动健身*/
        var fitness = new Swiper('.fitness',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".fitness-prev").click(function(){
            fitness.swipePrev();
        });
        $(".fitness-next").click(function(){
            fitness.swipeNext();
        });
        /*海上休闲*/
        var playOnsea = new Swiper('.play-onsea',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".onsea-prev").click(function(){
            playOnsea.swipePrev();
        });
        $(".onsea-next").click(function(){
            playOnsea.swipeNext();
        });
        /*其他*/
        var playElse = new Swiper('.play-else',{
            onlyExternal: true,
            slidesPerView : 3
        });
        $(".else-prev").click(function(){
            playElse.swipePrev();
        });
        $(".else-next").click(function(){
            playElse.swipeNext();
        });
    },
    /*banner中  简介展开收缩*/
    bannerSlider:function(){
        $(".details-btn").click(function(){
            if($(this).hasClass('active')){
                $(".txt-content").stop().slideUp(600);
                $(this).removeClass("active");
            }else{
                $(".txt-content").stop().slideDown(600);
                $(this).addClass("active");
            }
        });
    },
    /*舱房  详情展开*/
    cabinSlider:function(){
        $(".cabin-operation").click(function(){
            if($(this).hasClass("active")){
                $(this).closest(".body-group").siblings(".cabin-txt").css("display","none");;
                $(this).removeClass("active");
            }else{
                $(".cabin-txt").css("display","none");
                $(".cabin-operation").removeClass("active");
                $(this).closest(".body-group").siblings(".cabin-txt").css("display","inline-block");
                $(this).addClass("active");
            }
        });
    },
    /*各模块点击导航内容切换*/
    sectionNav:function(obj,target,className){
        obj.find(".nav-li").click(function(){
            var index = $(this).index();
            $(this).addClass("active").siblings().removeClass("active");
            target.eq(index).show().siblings(className).hide();
        });
    },
    /*锚点  页面滚动*/
    navAchor:function(){
        /*导航浮动的最小页面滚动距离*/
        var minHeight = $(".cruise-nav").offset().top;
        console.log(minHeight);
        /*导航的高度*/
        var navHeight = $(".cruise-nav").outerHeight(true);
        /*导航实际高度*/
        var navActualHeight = $(".cruise-nav").height();
        /*鼠标滚动事件*/
        $(window).scroll(function(){
            var windowScroll = $(window).scrollTop();
            if(windowScroll >= minHeight){
                $(".cruise-nav").addClass("fixed");
                $(".banner-wrap").css("margin-bottom",navHeight);
            }else{
                $(".cruise-nav").removeClass("fixed");
                $(".banner-wrap").css("margin-bottom",0);
            };
            /*页面滚动时候   导航条样式变化*/
            for(var i = 0;i<$(".content-group").length;i++){
                if(windowScroll <= $(".content-group").eq(i).offset().top - navActualHeight){
                    $(".cruise-nav").find("li").eq(i).addClass("active").siblings().removeClass("active");
                    return;
                }
            }
        });
        /*导航点击事件*/
        $(".cruise-nav").find("li").click(function(){
            var index = $(this).index();
            $(this).addClass("active").siblings().removeClass("active");
            $("body,html").animate({scrollTop:$(".content-group").eq(index).offset().top - navActualHeight},600);
        });
    }
};
$(function(){
    cruiseDetails.init();
});