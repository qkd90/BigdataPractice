var PlanScenicDetail = {
    scenicId: 0,
    name: "",
    cover: "",
    score: 0,
    adviceTime: "",
    NearByDistance: 5,
    map: null,
    mapIcon: null,
    init: function () {
        PlanScenicDetail.initComp();
        PlanScenicDetail.initEvent();
        PlanScenicDetail.initData();
    },
    initComp: function() {
        // init BMap
        PlanScenicDetail.map = new BMap.Map("bot_map");
        PlanScenicDetail.map.disableDoubleClickZoom();
        PlanScenicDetail.mapIcon = new BMap.Icon("http://api0.map.bdimg.com/images/marker_red_sprite.png", new BMap.Size(25, 16));
        var lng = $('#lng').val();
        var lat = $('#lat').val();
        if (lng != null && lat != null && lng != "" && lat != "") {
            var p = new BMap.Point(lng, lat);
            var marker = new BMap.Marker(p, {icon: PlanScenicDetail.mapIcon});
            PlanScenicDetail.map.addOverlay(marker);
            PlanScenicDetail.map.centerAndZoom(p, 17);
        } else {
            PlanScenicDetail.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 11);
        }
    },
    initData: function () {
        PlanScenicDetail.scenicId = parseInt($("#scenicId").val());
        PlanScenicDetail.name = $("#name").val();
        PlanScenicDetail.cover = $("#cover").val();
        PlanScenicDetail.score = parseFloat($("#score").val());
        PlanScenicDetail.adviceTime = $("#adviceTime").val();
        PlanScenicDetail.getTicketList();
        PlanScenicDetail.getNearByScenic();
        PlanScenicDetail.abc();
        PlanScenicDetail.initFav();
    },
    favEvt: function() {
        $('#collection').on('click', function(event) {
            event.stopPropagation();
            YhyUser.checkLogin(function(result) {
                if (result && result.success) {
                    PlanScenicDetail.doFav();
                } else {YhyUser.goLogin(function() {PlanScenicDetail.doFav();})}
            });
        });
    },
    initFav: function() {
        $("#collect").removeClass("collection");
        $.ajax({
            url: '/yhypc/favorite/doCheckFavorite.jhtml',
            data: {favoriteId: $("#scenicId").val(), favoriteType: 'scenic'},
            success: function(result) {
                if (result.favorite) {$("#collection").addClass("collection");}
            },
            error: function() {}
        });
    },
    doFav: function() {
        $.ajax({
            url: '/yhypc/favorite/favorite.jhtml',
            data: {favoriteId: $("#scenicId").val(), favoriteType: 'scenic'},
            progress: true,
            success: function(result) {
                if (result.favorite) {
                    $('#collection').addClass("collection");
                } else {
                    $('#collection').removeClass("collection");
                }
            },
            error: function() {}
        });
    },
    getTicketList: function () {
        $.ajax({
            url: '/yhypc/scenic/getTicketList.jhtml',
            data: {
                scenicId: PlanScenicDetail.scenicId
            },
            success: function(result) {
                if (result.success) {
                    var ticketList = $("#ticketList");
                    ticketList.empty();
                    var html = "";
                    $.each(result.ticketList, function (i, ticket) {
                        html += template("ticket_list_item", ticket);
                    });
                    ticketList.append(html);
                    if (html.length <= 0) {
                        $('.ticketbox').empty().append("<p class='ticket_title'>暂无可售门票.</p>");
                    }
                    PlanScenicDetail.ticketmessage();
                    PlanScenicDetail.colection();
                    PlanScenicDetail.initSelected();
                }
            },
            error: function () {}
        });
    },
    getNearByScenic: function() {
        var lng = $('#lng').val();
        var lat = $('#lat').val();
        if (lng && lat && lng != "" && lat != "") {
            $.ajax({
                url: '/yhypc/scenic/getNearByScenic.jhtml',
                data: {
                    lng: lng,
                    lat: lat,
                    dis: PlanScenicDetail.NearByDistance
                },
                success: function(result) {
                    if (result.data) {
                        $.each(result.data, function(i, s) {
                            var nearby_scenic_item = template('nearby_scenic_item', s);
                            $('#nearby_scenic_content').append(nearby_scenic_item);
                        });
                    } else {
                        $('#nearby_scenic_content').append("<li class='none'>暂无推荐</li>");
                    }
                },
                error: function() {
                    $('#nearby_scenic_content').append("<li class='none'>暂无推荐</li>");
                }
            })
        } else {
            $('#nearby_scenic_content').append("<li class='none'>暂无推荐</li>");
        }
    },
    initSelected: function () {
        var selectedScenic = RightBar.getSelectedScenic();
        var scenic = selectedScenic[PlanScenicDetail.scenicId];
        if (!$.isEmptyObject(scenic)) {
            $('#tack_detail').addClass('dislike').html('取消行程');
            $.each(scenic.prices, function (i, price) {
                $('.ticketul .pri_selected').each(function () {
                    if ($(this).data("id") == price) {
                        $(this).addClass('pri_discolect').addClass('orange').html('取消行程');
                        return false;
                    }
                });
            });
        } else {
            $('#tack_detail').removeClass('dislike').html('加入行程');
            $('.ticketul .pri_selected').each(function () {
                $(this).removeClass('pri_discolect').removeClass('orange').html('加入行程');
            });
        }
    },

    initEvent: function () {
        PlanScenicDetail.ulmin();
        PlanScenicDetail.photo();
        PlanScenicDetail.searchSelect();
        PlanScenicDetail.tack_detail();
        PlanScenicDetail.favEvt();
        $('#jumpToMap').on('click', function(event) {
            event.stopPropagation();
            var top = $('div.traffic').offset().top - 20;
            $('body').animate({scrollTop: top}, 100);
        });
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

// 门票，景点介绍，评价，交通
    searchSelect: function () {
        var Btn = $('.ticketlist .list_head span');
        Btn.on("click", function () {
            $(this).addClass('li_he_active');
            $(this).siblings().removeClass('li_he_active');
            var top = 0;
            if ($(this).hasClass('ticket')) {
                top = $('.ticketbox').offset().top - 20;
            } else if($(this).hasClass('scenic_intro')) {
                top = $('.hotelMessage').offset().top - 20;
            } else if($(this).hasClass('scenic_comment')) {
                top = $('.commendMessage').offset().top - 20;
            } else if($(this).hasClass('scenic_geo')) {
                top = $('.traffic').offset().top - 20;
            }
            $('body,html').animate({scrollTop: top}, 100);
        });
    },


//套票信息
    ticketmessage: function () {
        var li = $('.ticketul .intro');
        li.each(function () {
            $(this).on("click", function () {
                if ($(this).parent().hasClass('auto')) {
                    $(this).parent().removeClass('auto');
                    $(this).find('i').removeClass('forup');
                } else {
                    $(this).parent().addClass('auto');
                    $(this).find('i').addClass('forup');
                }
            });
        });
    },

    tack_detail: function () {
        var take = $('#tack_detail');
        take.on("click", function () {
            var selectedScenic = RightBar.getSelectedScenic();
            var scenic = selectedScenic[PlanScenicDetail.scenicId];
            if ($(this).hasClass('dislike')) {
                $(this).removeClass('dislike').html('加入行程');
                if (!$.isEmptyObject(scenic) && scenic.prices.length == 0) {
                    delete selectedScenic[PlanScenicDetail.scenicId];
                }
            } else {
                $(this).addClass('dislike').html('取消行程');
                if ($.isEmptyObject(scenic)) {
                    scenic = {
                        id: PlanScenicDetail.scenicId,
                        name: PlanScenicDetail.name,
                        cover: PlanScenicDetail.cover,
                        score: PlanScenicDetail.score,
                        adviceTime: PlanScenicDetail.adviceTime,
                        prices: []
                    };
                    selectedScenic[PlanScenicDetail.scenicId] = scenic;
                }
                RightBar.showRightBar(true);
            }
            RightBar.setSelectedScenic(selectedScenic);
            RightBar.initScenicList();
        });
    },

    colection: function () {
        var colect = $('.ticketul .pri_selected');
        colect.each(function (id, element) {
            $(this).hover(function () {
                if ($(this).hasClass('pri_discolect')) {
                    $(this).addClass('orange');
                } else {
                    $(this).addClass('gray');
                }
            }, function () {
                $(this).removeClass('gray').removeClass('orange');
            });
            $(this).on("click", function () {
                var priceId = parseInt($(this).data("id"));
                var selectedScenic = RightBar.getSelectedScenic();
                var scenic = selectedScenic[PlanScenicDetail.scenicId];
                if ($.isEmptyObject(scenic)) {
                    scenic = {
                        id: PlanScenicDetail.scenicId,
                        name: PlanScenicDetail.name,
                        cover: PlanScenicDetail.cover,
                        score: PlanScenicDetail.score,
                        adviceTime: PlanScenicDetail.adviceTime,
                        prices: []
                    };
                }
                var index = -1;
                $.each(scenic.prices, function (i, price) {
                    if (price == priceId) {
                        index = i;
                        return false;
                    }
                });
                if ($(this).hasClass('pri_discolect')) {
                    $(this).removeClass('pri_discolect').removeClass('orange').html('加入行程');
                    if (index > -1) {
                        scenic.prices.splice(index, 1);
                        if ($('#tack_detail').hasClass('dislike')) {
                            selectedScenic[PlanScenicDetail.scenicId] = scenic;
                        } else {
                            delete selectedScenic[PlanScenicDetail.scenicId];
                        }
                    }
                } else {
                    $(this).addClass('pri_discolect').addClass('orange').html('取消行程');
                    if (index == -1) {
                        scenic.prices.push(priceId);
                        selectedScenic[PlanScenicDetail.scenicId] = scenic;
                    }
                    RightBar.showRightBar(true);
                }
                RightBar.setSelectedScenic(selectedScenic);
                RightBar.initScenicList();
            });
        });
    },
    abc:function(){
        $(".listRight .aroundList ul li:last-child").css({"border":"none"});
    }


};
$(window).ready(function () {
    PlanScenicDetail.init();
    //$(".listRight .aroundList ul li:last-child").css({"border":"none"});
});

