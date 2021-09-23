/**
 * Created by zzl on 2017/1/9.
 */
var HotelDetail = {
    map: null,
    mapIcon: null,
    RoomNumberLimit: 8,
    NearByDistance: 5,
    init: function() {
        HotelDetail.initComp();
        HotelDetail.initEvt();
        HotelDetail.initData();
    },
    initComp: function() {

        var date = new Date();
        if (!$("#startDate").val()) {
            $("#startDate").val($.addDate(date, 0));
        }
        if (!$("#endDate").val()) {
            $("#endDate").val($.addDate(date, 1));
        }


        // init BMap
        HotelDetail.map = new BMap.Map("bot_map");
        //HotelDetail.mapIcon = new BMap.Icon("/image/places-16.png", new BMap.Size(20, 25));
        HotelDetail.mapIcon = new BMap.Icon("http://api0.map.bdimg.com/images/marker_red_sprite.png", new BMap.Size(25, 16));
        var lng = $('#lng').val();
        var lat = $('#lat').val();
        if (lng != null && lat != null && lng != "" && lat != "") {
            var p = new BMap.Point(lng, lat);
            var marker = new BMap.Marker(p, {icon: HotelDetail.mapIcon});
            HotelDetail.map.addOverlay(marker);
            HotelDetail.map.centerAndZoom(p, 17);
        } else {
            HotelDetail.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 11);
        }
    },
    onFoucsEndTime: function() {
        var startTime = $("#startDate").val();
        if (startTime.length <= 0) {
            return;
        }
        var date = moment(startTime);
        $("#endDate").val($.addDate(date, 1));
    },


    initEvt: function() {

        // change date btn
        $('#change_date_btn').on('click', function (event) {
            event.stopPropagation();
            HotelDetail.getHotelPrice();
        });
        // room number opt
        $(document).on('click', 'p.room_num_opt span.sub', function(event) {
            event.stopPropagation();
            var num = Number($(this).siblings('span.num').html());
            if (num > 1) {
                num--;
                $(this).siblings('span.num').html(num);
                $(this).siblings('span.add').removeClass('disa').addClass('ena');
                if (num == 1) {
                    $(this).removeClass('ena').addClass('disa');
                }
            } else{
                $(this).removeClass('ena').addClass('disa');
            }
        });
        $(document).on('click', 'p.room_num_opt span.add', function(event) {
            event.stopPropagation();
            var num = Number($(this).siblings('span.num').html());
            if (num < HotelDetail.RoomNumberLimit) {
                num++;
                $(this).siblings('span.num').html(num);
                $(this).siblings('span.sub').removeClass('disa').addClass('ena');
                if (num == HotelDetail.RoomNumberLimit) {
                    $(this).removeClass('ena').addClass('disa');
                }
            } else {
                $(this).removeClass('ena').addClass('disa');
            }
        });
        // go room type btn
        $('.tackdetail a').on('click', function(event) {
            event.stopPropagation();
            $('.detailList .listLeft .list_head span.room-type').click();
        });
        // go booking btn
        $(document).on('click', 'div.this_book div.bookThis', function(event) {
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            var priceId = $(this).attr('data-price-id');
            var num = Number($.trim($('p.room_num_opt span[data-price-id=' + priceId + ']').html()));
            var url = '/yhypc/order/orderHotelWrite.jhtml?hotelPriceId=' + priceId;
            url += "&startDate=" + startDate + "&endDate=" + endDate + "&num=" + num;
            var code = Math.random().toString(36).substring(2);
            url += "&code=" + code;
            window.location.href = url;
        });
        // fav evt
        $('#collection').on('click', function(event) {
            event.stopPropagation();
            YhyUser.checkLogin(function(result) {
                if (result && result.success) {
                    HotelDetail.doFav();
                } else {YhyUser.goLogin(function() {ScenicDetail.doFav();})}
            });
        });
    },
    initData: function() {
        HotelDetail.getHotelPrice();
        HotelDetail.getNearByHotel();
        HotelDetail.initFav();
    },
    initFav: function() {
        $("#collect").removeClass("collection");
        $.ajax({
            url: '/yhypc/favorite/doCheckFavorite.jhtml',
            data: {favoriteId: $("#hotelId").val(), favoriteType: 'hotel'},
            success: function(result) {
                if (result.favorite) {$("#collection").addClass("collection");}
            },
            error: function() {}
        });
    },
    doFav: function() {
        $.ajax({
            url: '/yhypc/favorite/favorite.jhtml',
            data: {favoriteId: $("#hotelId").val(), favoriteType: 'hotel'},
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
    getHotelPrice: function() {
        $.ajax({
            url: '/yhypc/hotel/getHotelPrice.jhtml',
            progress: true,
            data: {
                hotelId: $('#hotelId').val(),
                startDate: $('#startDate').val(),
                endDate: $('#endDate').val()
            },
            success: function(result) {
                $('#hotel_price_content').empty();
                if (result.data) {
                    $.each(result.data, function(i, p) {
                        var price_item = template('hotel_price_item', p);
                        $('#hotel_price_content').append(price_item);
                    });
                }
            }
        })
    },
    getNearByHotel: function() {
        var lng = $('#lng').val();
        var lat = $('#lat').val();
        if (lng && lat && lng != "" && lat != "") {
            $.ajax({
                url: '/yhypc/hotel/getNearByHotel.jhtml',
                data: {
                    lng: lng,
                    lat: lat,
                    dis: HotelDetail.NearByDistance
                },
                success: function(result) {
                    if (result.data) {
                        $.each(result.data, function(i,h) {
                            var nearby_hotel_item = template('nearby_hotel_item', h);
                            $('#nearby_hotel_content').append(nearby_hotel_item);
                        });
                    } else {
                        $('#nearby_hotel_content').append("<li class='none'>暂无推荐</li>");
                    }
                },
                error: function() {
                    $('#nearby_hotel_content').append("<li class='none'>暂无推荐</li>");
                }
            })
        } else {
            $('#nearby_hotel_content').append("<li class='none'>暂无推荐</li>");
        }
    }
};

$(function () {
    HotelDetail.init();
});