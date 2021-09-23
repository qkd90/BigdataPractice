/**
 * Created by Administrator on 2017/6/1.
 */
var ProductDetails = {
        init: function () {
            ProductDetails.getRoomsInfo();
            ProductDetails.getServiceInfo();
            ProductDetails.getFoodInfo();
            ProductDetails.getEntainmentInfo();
            ProductDetails.swiper();
            ProductDetails.detailsSwiper();
            ProductDetails.popupFn();
            ProductDetails.cruisesAboutNav();
            ProductDetails.anchorFn();
            ProductDetails.routeAchor();
            ProductDetails.showHide();
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
        /*细节介绍轮播*/
        detailsSwiper: function () {
            var cabinSwiper = new Swiper(".cabin .content-swiper", {
                slidesPerView: 4
            });
            $(".cabin .about-content-prev").click(function () {
                cabinSwiper.swipePrev();
            });
            $(".cabin .about-content-next").click(function () {
                cabinSwiper.swipeNext();
            });
            var onseaSwiper = new Swiper(".onsea .content-swiper", {
                slidesPerView: 4
            });
            $(".onsea .about-content-prev").click(function () {
                onseaSwiper.swipePrev();
            });
            $(".onsea .about-content-next").click(function () {
                onseaSwiper.swipeNext();
            });
            var playSwiper = new Swiper(".cruise-play .content-swiper", {
                slidesPerView: 4
            });
            $(".cruise-play .about-content-prev").click(function () {
                playSwiper.swipePrev();
            });
            $(".cruise-play .about-content-next").click(function () {
                playSwiper.swipeNext();
            });
            var serverSwiper = new Swiper(".cruise-server .content-swiper", {
                slidesPerView: 4
            });
            $(".cruise-server .about-content-prev").click(function () {
                serverSwiper.swipePrev();
            });
            $(".cruise-server .about-content-next").click(function () {
                serverSwiper.swipeNext();
            });
        },
        /*弹窗生成*/
        popupFn: function () {
            $(".content-swiper").on('click','.swiper-slide',function(){
                $('#popupWrap').html("");
                var popHtml = $(this).find(".popup-wrap").html();
                $('#popupWrap').html(popHtml);
                /*大图轮播*/
                var viewSwiper = new Swiper("#popupWrap .views", {
                    onSlideChangeStart: function () {
                        updateNavPosition();
                    }
                });
                var previewSwiper = new Swiper("#popupWrap .preview", {
                    visibilityFullFit: true,
                    slidesPerView: 5,
                    onlyExternal: true,
                    onSlideClick: function () {
                        viewSwiper.swipeTo(previewSwiper.clickedSlideIndex)
                    }
                });
                $("#popupWrap").on("click",".btn-prev",function (e) {
                    var e = e || event;
                    e.preventDefault();
                    if (viewSwiper.activeIndex == 0) {
                        viewSwiper.swipeTo(viewSwiper.slides.length - 1, 1000);
                        return;
                    }
                    viewSwiper.swipePrev()
                });
                $("#popupWrap").on("click",".btn-next",function () {
                    var e = e || event;
                    e.preventDefault();
                    if (viewSwiper.activeIndex == viewSwiper.slides.length - 1) {
                        viewSwiper.swipeTo(0, 1000);
                        return;
                    }
                    viewSwiper.swipeNext()
                });
                function updateNavPosition() {
                    $('#popupWrap .preview .active').removeClass('active');
                    var activeNav = $('#popupWrap .preview .swiper-slide').eq(viewSwiper.activeIndex).addClass('active');
                    if (!activeNav.hasClass('swiper-slide-visible')) {
                        if (activeNav.index() > previewSwiper.activeIndex) {
                            var thumbsPerNav = Math.floor(previewSwiper.width / activeNav.outerWidth(true)) - 1;
                            previewSwiper.swipeTo(activeNav.index() - thumbsPerNav);
                        } else {
                            previewSwiper.swipeTo(activeNav.index());
                        }
                    }
                };
                $("#popupWrap").on('click','.popup-close',function(){
                    $("#popupWrap").html(" ");
                });
            });
        },
        /*邮轮介绍  导航切换*/
        cruisesAboutNav:function(){
            $(".about-nav").find("li").click(function(){
                $(this).addClass('active').siblings().removeClass('active');
                $(".about-content-group").eq($(this).index()).show().siblings().hide();
            });
        },
        /*锚点*/
        anchorFn:function(){
            /*定义一个空数组，存储各个锚点的距离*/
            var anchor = [];
            /*获取导航的高度*/
            var navHeight = $(".product-nav").height();
            /*获取导航的站位高度*/
            var navOuterHeight = $(".product-nav").outerHeight(true);
            /*获取导航距离顶部的距离*/
            var navScrollTop = $(".product-nav").offset().top;
            /*获取锚点距离*/
            for(var i=0;i<$(".product-details-group").length;i++){
                anchor.push($(".product-details-group").eq(i).offset().top - navHeight );
            }
            /*监听页面滚动事件*/
            $(window).scroll(function(){
                /*获取页面的滚动距离*/
                var windowScroll = $(window).scrollTop() + 3;
                /*判断滚动距离*/
                if(windowScroll < navScrollTop){
                    $(".product-nav").removeClass("fixed");
                    $(".product-order").css("margin-bottom",0);
                }else{
                    $(".product-nav").addClass("fixed");
                    $(".product-order").css("margin-bottom",navOuterHeight);
                }
                /*导航样式  随动*/
                for(var i=0;i<anchor.length;i++){
                    if(windowScroll > anchor[i]){
                        $(".product-nav").find("li").eq(i).addClass("active").siblings().removeClass('active');
                    }
                }
            });
            /*导航点击事件*/
            $(".product-nav").find("li").click(function(){
                $("body,html").animate({scrollTop:anchor[$(this).index()]},100);
                $(this).addClass("active").siblings().removeClass("active");
            });
        },
        /*航线锚点*/
        routeAchor:function(){
            /*获取导航距离顶部的距离  最小距离*/
            var minHeight = $(".everyday-group").first().offset().top;
            console.log(minHeight);
            /*最大距离*/
            var maxHeight = $(".everyday-group").last().offset().top;
            console.log(maxHeight);
            /*获取导航的高度*/
            var navHeight = $(".product-nav").outerHeight(true);
            /*定义锚点数组*/
            var achorArray = [];
            for(var i=0;i<$(".everyday-group").length;i++){
                achorArray.push($(".everyday-group").eq(i).offset().top);
            }
            achorArray.push(Number.POSITIVE_INFINITY);
            console.log(achorArray);
            $(".everyday-nav").find("li").click(function(){
                $("body,html").animate({scrollTop:achorArray[$(this).index()] - navHeight},100);
                $(".everyday-nav").css({"margin-top":achorArray[$(this).index()] - minHeight});
                $(this).addClass("active").siblings().removeClass("active");
            });

            $(window).scroll(function(){
                var windowScroll = $(window).scrollTop();
                if(windowScroll <= minHeight - navHeight){
                    $(".everyday-nav").find("li").first().addClass("active").siblings().removeClass('active');
                    $(".everyday-nav").css("margin-top",0);
                }else if(windowScroll >= maxHeight -navHeight){
                    $(".everyday-nav").find('li').last().addClass("active").siblings().removeClass('active');
                    $(".everyday-nav").css("margin-top",maxHeight - minHeight);
                }else if(windowScroll > minHeight - navHeight && windowScroll < maxHeight -navHeight){
                    for(var i = 0 ;i < achorArray.length-1;i++){
                        if(windowScroll >= achorArray[i] - navHeight){
                            $(".everyday-nav").find("li").eq(i).addClass("active").siblings().removeClass('active');
                            $(".everyday-nav").css("margin-top",windowScroll - minHeight + navHeight);
                        }
                    }
                }
            });
        },
        /*显示影藏事件*/
        showHide:function(){
            $(".order-nav").find("li").click(function(){
                $(this).addClass("active").siblings().removeClass("active");
                $(".order-txt-group").eq($(this).index()).show().siblings().hide();
            });
        },
        //获取舱房信息
        getRoomsInfo:function(){
            $.ajax({
                url:'/yhypc/cruiseShip/getRoomList.jhtml',
                dataType: 'json',
                data: {
                    'dateId' : $('#dateId').val()
                },

                success: function(result){
                    $.each(result,function(i,item){
                        var html = template("room", item);
                        $("#partOfRoom").append(html)

                    });

                    $(".roomNum").html("舱房介绍（" + result.length + "）");
                }
            });

        },

        //获取服务信息
        getServiceInfo:function() {
            $.ajax({
                url:'/yhypc/cruiseShip/getCruiseShipProjectList.jhtml?parentId=1',
                dataType: 'json',
                type:'post',
                data: {
                   'cruiseShipId' : $('#cruiseShipId').val()

                },
                success: function(result){
                    $.each(result,function(i,item){
                        var serviceHtml = template("service",item);
                        $("#partOfService").append(serviceHtml);

                    });
                    $(".serviceNum").html("邮轮服务（" + result.length + "）");
                }
            });
        },
        //获取美食信息
        getFoodInfo:function(){$.ajax({
            url:'/yhypc/cruiseShip/getCruiseShipProjectList.jhtml?parentId=2',
            dataType: 'json',
            type:'post',
            data: {
               'cruiseShipId' : $('#cruiseShipId').val()

            },
            success: function(result){
                $.each(result,function(i,item){
                    var foodHtml = template("food", item);
                    $("#partOfFood").append(foodHtml);

                });
                $(".foodNum").html("海上美食（" + result.length + "）");
            }
        });},
        //获取娱乐信息
        getEntainmentInfo:function(){
            $.ajax({
            url:'/yhypc/cruiseShip/getCruiseShipProjectList.jhtml?parentId=3',
            dataType: 'json',
            type:'post',
            data: {
                'cruiseShipId' : $('#cruiseShipId').val()

            },
            success: function(result){
                $.each(result,function(i,item){
                    var entainmentHtml = template("entainment",item);
                    $("#partOfEntainment").append(entainmentHtml);

                });
                $(".entainmentNum").html("邮轮玩乐（" + result.length + "）");
            }
        });}
    };
$(function(){
    ProductDetails.init();
});