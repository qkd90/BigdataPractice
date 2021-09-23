/**
 * Created by HMLY on 2016-12-20.
 */

var SailboatDetail = {
    pager: null,
    init: function() {
        SailboatDetail.initJsp();
        SailboatDetail.getTicketPriceList(1, 6);
        SailboatDetail.getCommentInfo();
        SailboatDetail.createMap();
        SailboatDetail.createPager();
        SailboatDetail.commentList();
        SailboatDetail.ulmin();
        SailboatDetail.photo();
    },

    initJsp: function() {
        $(".check-more-item").click(function() {
            var flag = $(this).attr("data-flag");
            $("div[id^='ticket-category-']").slideUp('2000');
            if (flag == 0) {
                SailboatDetail.getTicketPriceList(1, 100);
                $(this).attr("data-flag", "1");
                $("#html-content").html("隐藏部分票型");
                $(this).find("i").removeClass("down");
                $(this).find("i").addClass("up");
            } else {
                SailboatDetail.getTicketPriceList(1, 6);
                $(this).attr("data-flag", "0");
                $("#html-content").html("展开全部票型");
                $(this).find("i").removeClass("up");
                $(this).find("i").addClass("down");
            }

        });
    },

    createMap: function () {
        var map = new BMap.Map("traffic-map", {enableMapClick: false}); //在百度地图容器中创建一个地图
        var point = new BMap.Point($("#traffic-map").attr("data-lng"), $("#traffic-map").attr("data-lat")); //定义一个中心点坐标
        map.centerAndZoom(point, 17); //设定地图的中心点和坐标并将地图显示在地图容器中
        map.enableScrollWheelZoom(); //启用滚轮放大缩小
        window.map = map; //将map变量存储在全局
        var local = new BMap.LocalSearch(map, {
            renderOptions: {map: map}, pageCapacity: 1
        });
        var marker = new BMap.Marker(point);        // 创建标注
        map.addOverlay(marker);                     // 将标注添加到地图中
    },

    commentList: function () {
        var search = {};
        search['comment.targetId'] = $("#commentList").attr("data-target-id");
        SailboatDetail.pager.init(search);
    },

    ulmin: function () {
        var li = $('.pic_min li');
        var ul_width = li.width() * li.length + 10 * li.length;
        var left = $('.t_left');
        var right = $('.t_right');
        $('.pic_min ul').css({'width': ul_width});
        right.on("click", function () {
            var pos_left = $('.pic_min ul').css('left');//第一个小图片距离左部的距离
            var pos_x = -(parseInt(pos_left) - 25) / li.width() + 1;//小图当前第一个的index
            var pos_group = -(parseInt(pos_left) - 25) / ((li.width() + 10) * 5) + 1;//组数
            var onepage = -(li.width() + 10) * 5 * pos_group + 25;
            if (li.length - pos_x >= 5) {
                $('.pic_min ul').animate({'left': onepage}, 100);
            }
        });
        left.on("click", function () {
            var pos_left = $('.pic_min ul').css('left');
            var pos_x = -(parseInt(pos_left) - 25) / (li.width() + 10) + 1;//小图当前第一个的index
            var onepage = parseInt(pos_left) + (li.width() + 10) * 5;
            if (pos_x - 5 != 1 && pos_x - 5 > 0) {
                $('.pic_min ul').animate({'left': onepage}, 100);
            } else {
                $('.pic_min ul').animate({'left': '25px'}, 100);
            }
        });
    },

//相册
    photo: function () {
        var timer;
        var i = -1;
        var offset = 5000;
        $(function () {
            roll();
            stopmax();
            stopmin();
        });
        function slider(i) {
            $('.pic_max img').eq(i).fadeIn().siblings().hide();
            $('.pic_min ul li').eq(i).siblings().removeClass('on');
            $('.pic_min ul li').eq(i).addClass('on');
            var groupId = Math.floor(i / 5);
            var ulleft = - groupId * 610 +25;
            if(i % 5 == 0){
                $('.pic_min ul').animate({'left': ulleft}, 100);
            }
        }

        function roll() {
            var long = $('.pic_min li').length - 1;
            i++;
            if (i > long) {
                i = 0;
            }
            slider(i);
            timer = setTimeout(roll, offset);
        }

        function stopmax() {
            $('.pic_max img').hover(function () {
                clearTimeout(timer);
            }, function () {
                timer = setTimeout(roll, offset);
            });
        }

        function stopmin() {
            $('.pic_min ul li').hover(function () {
                clearTimeout(timer);
                i = $(this).index();
                slider(i);
            }, function () {
                timer = setTimeout(roll, offset);
            });
        }
    },


    createPager: function() {
        var options = {
            countUrl: "/yhypc/sailboat/getTotalCommentPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                $("#commentList").empty();
                //$("#loading").show();
                scroll(0, 0);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.post("/yhypc/sailboat/getCommentList.jhtml",
                    data,
                    function (data) {
                        $("#commentList").empty();
                        var commentList = data.commentList;
                        if (commentList.length > 0) {
                            for (var i = 0; i < commentList.length; i++) {
                                var s = commentList[i];
                                if(parseInt(s.id) < 2000433){
                                    s.shortComment = formatString(s.shortComment);
                                }
                                template.config("escape", false);
                                var result = $(template("tpl-sailboat-detail-comment-list", s));
                                $("#commentList").append(result);
                                result.data("comment", s);
                            }
                        } else {
                            $(".m-pager").hide();
                            //$("#commentList").after();
                        }

                    }
                    ,"json");
            }
        };
        SailboatDetail.pager = new Pager(options);
    },
    getCommentInfo: function() {
        $.post("/yhypc/sailboat/getCommentInfo.jhtml",
            {
                ticketId: $("#ticketPriceList").attr("data-id")
            },
            function (data) {
                if (data.success) {
                    $("#nav-comment-span").html(data.commentCount);

                    $(".grade").empty();
                    template.config("escape", false);
                    var result1 = $(template("tpl-sailboat-detail-comment-info-1", data));
                    $(".grade").append(result1);
                    result1.data("commentInfo", data);

                    $(".comment-details-header").empty();
                    template.config("escape", false);
                    var result2 = $(template("tpl-sailboat-detail-comment-info-2", data));
                    $(".comment-details-header").append(result2);
                    result2.data("commentInfo", data);
                    SailboatDetail.initFavorite();
                }
            }
            ,"json");
    },

    getTicketPriceList: function(pageIndex, pageSize) {
        $.post("/yhypc/sailboat/getTicketPriceList.jhtml",
            {
                ticketId: $("#ticketPriceList").attr("data-id"),
                pageIndex: pageIndex,
                pageSize: pageSize
            },
            function (data) {
                $("#ticketPriceList").empty();
                if (data.success) {
                    var ticketPrices = data.ticketPrices;
                    $.each(ticketPrices, function(i, ticketPrice) {
                        template.config("escape", false);
                        var result = $(template("tpl-sailboat-list-type-item", ticketPrice));
                        $("#ticketPriceList").append(result);
                        result.data("ticketPriceInfo", ticketPrice);
                    });
                    if (ticketPrices.length <= 6) {
                        $(".ticket-details-unflod").hide();
                    }
                    if (ticketPrices.length <= 0) {
                        $(".ticket_title").show();
                        $(".ticket-details-header").hide();
                    }
                }
            }
            ,"json");
    },

    priceInfo: function(targetObjt, id) {
        $.post("/yhypc/sailboat/getPriceTypeInfo.jhtml",
            {ticketPriceId: id},
            function (data) {
                targetObjt.empty();
                if (data.success) {
                    var priceTypeExtendList = data.priceTypeExtendList;
                    template.config("escape", false);
                    var result = $(template("tpl-sailboat-list-type-extend-item", data));
                    targetObjt.append(result);
                    result.data("ticketPriceInfo", data);
                }
            }
            ,"json");
    },
    /*收藏   心形icon*/
    switchClassName: function(target){

        YhyUser.checkLogin(function (result) {
            if (result.success) {
                doFavorite();
            } else {
                YhyUser.goLogin(doFavorite);
            }
        });

        function doFavorite() {
            var url = "/yhypc/favorite/favorite.jhtml";
            var data = {
                favoriteId: $("#sailboat-id").val(),
                favoriteType: 'sailboat'
            }

            $.ajax({
                url: url,
                data: data,
                progress: true,
                success: function(data) {

                    if (data.favorite) {

                        $(target).addClass("active");
                        $(target).attr("data-flag", 1);
                    } else {

                        $(target).removeClass("active");
                        $(target).attr("data-flag", 0);
                    }
                },
                error: function() {}
            });
        }


    },
    initFavorite: function() {
        var url = "/yhypc/favorite/doCheckFavorite.jhtml";
        var data = {
            favoriteId: $("#sailboat-id").val(),
            favoriteType: 'sailboat'
        }

        $.ajax({
            url: url,
            data: data,
            progress: false,
            success: function(data) {
                if (data.favorite) {
                    $("#collect").addClass("active");
                    $("#collect").attr("data-flag", 1);
                } else {
                    $("#collect").removeClass("active");
                    $("#collect").attr("data-flag", 0);
                }
            },
            error: function() {}
        });


    },
    datalist: function(id) {
        var onOff = $("#ticket-category-"+ id +"").attr("data-flag");
        if (onOff == 0) {
            SailboatDetail.priceInfo($("#ticket-category-"+ id +""), id);
            $("#ticket-category-"+ id +"").slideDown('2000');
            $("#ticket-category-"+ id +"").attr("data-flag", "1");
            $("#ticket-category-"+ id +"").parent().find('.collapse i').addClass('turnup');
        } else {
            $("#ticket-category-"+ id +"").slideUp('2000');
            $("#ticket-category-"+ id +"").attr("data-flag", "0");
            $("#ticket-category-"+ id +"").parent().find('.collapse i').removeClass('turnup');
        }
    }
};

$(function(){
    SailboatDetail.init();
    switchNav($("#nav-ticket"),$("#ticket-target"),'active');
    switchNav($("#nav-sightport"),$("#sightport-target"),'active');
    switchNav($("#nav-comment"),$("#comment-target"),'active');
    switchNav($("#nav-traffic"),$("#traffic-target"),'active');
    switchNav($("#map-id"),$("#traffic-target"),'active');

    /*轮播*/
    var viewSwiper = new Swiper('.view .swiper-container', {
        autoplay:3000,
        loop:true,
        slidesPerView : 1,
        centeredSlides : false,
        loopAdditionalSlides : 4,
        autoplayDisableOnInteraction:true
    });

    var previewSwiper = new Swiper('.preview .swiper-container', {
        loop:true,
        prevButton:'.arrow-left',
        nextButton:'.arrow-right',
        slidesPerView : 5,
        slideToClickedSlide:true,
        centeredSlides : false,
        spaceBetween : 9,
        autoplayDisableOnInteraction:true
    });

    viewSwiper.params.control = previewSwiper;
    previewSwiper.params.control = viewSwiper;
    hoverPlay($(".view"));
    hoverPlay($(".preview"));

    function hoverPlay(obj){
        obj.hover(function(){
            viewSwiper.stopAutoplay();
            previewSwiper.stopAutoplay();
        },function(){
            viewSwiper.startAutoplay();
            previewSwiper.startAutoplay();
        });
    }


/*
    *//*限制精彩留言框中文本字数*//*
    limitTxtCount($("#product-comment"),90);
    *//*手风琴*//*
    datalist($("#ticket-categoryBtn1"),$("#ticket-category1"));
    datalist($("#ticket-categoryBtn2"),$("#ticket-category2"));
    datalist($("#ticket-categoryBtn3"),$("#ticket-category3"));

    *//*收藏   心形icon*//*
    switchClassName($("#collect"),'active');
    *//*导航切换  及  页面滚动*//*
    * */

});
/*限制文本字数*/
function limitTxtCount(obj,num){
    var txt = obj.html().substring(0,num);
    obj.html(txt + '...更多');
}



/*导航切换  及  页面滚动*/
function switchNav(obj,targetObj,className){
    obj.click(function(ev){
        obj.addClass(className).siblings().removeClass(className);
        var ev = ev || event;
        var scrolltop = targetObj.offset().top;
        $('body,html').animate({scrollTop:scrolltop},800);
    });
}
/*手风琴*/
function datalist(obj,tagObj){
    tagObj.hide();
    var onOff = true;
    obj.click(function(ev){
        var ev = ev || event;
        ev.stopPropagation();
        if(onOff){
            tagObj.slideDown('2000');
            onOff = !onOff;
        }else{
            tagObj.slideUp('2000');
            onOff = !onOff;
        }
    });
    $("body").click(function(){
        tagObj.slideUp('2000');
        onOff = true;
    });
}
