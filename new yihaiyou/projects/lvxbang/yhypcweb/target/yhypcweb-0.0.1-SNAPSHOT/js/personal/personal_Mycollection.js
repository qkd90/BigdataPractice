var MyCollection = {
    pager: null,
    init: function() {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                MyCollection.initComp();
                MyCollection.initEvt();
                MyCollection.createPager();
                MyCollection.initData();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function() {},
    initEvt: function() {
        $('.score_comment .comNum i').on('click', function () {
            var state = $(this).hasClass('dislike');
            if (state == false) {
                $(this).addClass('dislike');
            } else {
                $(this).removeClass('dislike');
            }
        });
        $('#favTypeSel span').on('click', function(event) {
            event.stopPropagation();
            $(this).addClass('s_active').siblings().removeClass('s_active');
            MyCollection.getMyFav();
        })
    },
    createPager: function() {
        var page = {
            countUrl: '/yhypc/favorite/countMyFav.jhtml',
            resultCountFn: function(result) {
                if (result.success) {
                    return parseInt(result.count);
                } else {
                    if (result.code == "req_login") {YhyUser.goLogin();}
                    return 0;
                }
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/favorite/getMyFav.jhtml',
                    data: data,
                    progress: true,
                    success: function(result) {
                        if (result.success) {
                            $('#fav_content').empty();
                            $.each(result.data, function(i, o) {
                                o.cover = o.imgPath;
                                var fav_item = template('fav_item', o);
                                $('#fav_content').append(fav_item);
                            });
                            scroll(0, 480);
                        }
                    }
                });
            }
        };
        MyCollection.pager = new Pager(page);
    },
    initData: function() {
        MyCollection.getMyFav();
    },
    getMyFav: function() {
        var searchRequest = {};
        var favoriteType = $('#favTypeSel span.s_active').attr('data-fav-type');
        searchRequest['favoriteType'] = favoriteType;
        MyCollection.pager.init(searchRequest);
    }
};

$(function(){
    MyCollection.init();
});