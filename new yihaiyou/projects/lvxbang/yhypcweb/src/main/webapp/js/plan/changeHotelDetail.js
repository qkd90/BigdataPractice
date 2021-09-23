/**
 * Created by zzl on 2017/1/9.
 */
var ChangeHotelDetail = {
    map: null,
    mapIcon: null,
    RoomNumberLimit: 8,
    NearByDistance: 5,
    hotel: {},

    init: function() {
        ChangeHotelDetail.initComp();
        ChangeHotelDetail.initEvt();
        ChangeHotelDetail.initData();
    },
    initComp: function() {
        // init BMap
        ChangeHotelDetail.map = new BMap.Map("bot_map");
        //ChangeHotelDetail.mapIcon = new BMap.Icon("/image/places-16.png", new BMap.Size(20, 25));
        ChangeHotelDetail.mapIcon = new BMap.Icon("http://api0.map.bdimg.com/images/marker_red_sprite.png", new BMap.Size(25, 16));
        var lng = $('#lng').val();
        var lat = $('#lat').val();
        if (lng != null && lat != null && lng != "" && lat != "") {
            var p = new BMap.Point(lng, lat);
            var marker = new BMap.Marker(p, {icon: ChangeHotelDetail.mapIcon});
            ChangeHotelDetail.map.addOverlay(marker);
            ChangeHotelDetail.map.centerAndZoom(p, 17);
        } else {
            ChangeHotelDetail.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 11);
        }

        var planDetail = RightBar.getPlanDetail();
        var changingDay = planDetail.days[planDetail.changingHotel - 1];
        var hotelId = $("#hotelId").val();
        if (changingDay.hotel != null && changingDay.hotel.id == hotelId) {
            ChangeHotelDetail.hotel = changingDay.hotel;
        } else {
            ChangeHotelDetail.hotel = {
                id: hotelId,
                startDate: changingDay.startDate,
                endDate: changingDay.endDate,
                name: $("#name").val(),
                address: $("#address").val(),
                cover: $("#cover").val(),
                description: $("#description").val(),
                lat: lat,
                lng: lng,
                price: 0,
                priceIds: []
            };
        }
        $("#startDate").val(ChangeHotelDetail.hotel.startDate);
        $("#endDate").val(ChangeHotelDetail.hotel.endDate);
    },
    initEvt: function() {
        $(".listLeft .saveChange").on("click", function () {
            if (ChangeHotelDetail.hotel.priceIds.length == 0) {
                $.message.alert({
                    title: "提示",
                    info: "请选择更改的房型"
                });
                return;
            }
            var planDetail = RightBar.getPlanDetail();
            planDetail.days[planDetail.changingHotel - 1].hotel = ChangeHotelDetail.hotel;
            RightBar.setPlanDetail(planDetail);
            location.href = "/yhypc/plan/detail.jhtml";
        });
        // go booking btn
        $(document).on('click', 'div.this_book div.bookThis', function(event) {
            var priceId = $(this).data('price-id');
            var price = $(this).data('price-price');
            var index = $.inArray(priceId, ChangeHotelDetail.hotel.priceIds);
            if ($(this).hasClass("bkActive")) {
                if (index > -1) {
                    ChangeHotelDetail.hotel.priceIds.splice(index, 1);
                    ChangeHotelDetail.hotel.price -= price;
                }
                $(this).removeClass("bkActive");
            } else {
                if (index < 0) {
                    ChangeHotelDetail.hotel.priceIds.push(priceId);
                    ChangeHotelDetail.hotel.price += price;
                }
                $(this).addClass("bkActive");
            }
        });
        // fav evt
        $('#collection').on('click', function(event) {
            event.stopPropagation();
            YhyUser.checkLogin(function(result) {
                if (result && result.success) {
                    ChangeHotelDetail.doFav();
                } else {YhyUser.goLogin(function() {ScenicDetail.doFav();})}
            });
        });
    },
    initData: function() {
        ChangeHotelDetail.getHotelPrice();
        ChangeHotelDetail.getNearByHotel();
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
                startDate: ChangeHotelDetail.hotel.startDate,
                endDate: ChangeHotelDetail.hotel.endDate
            },
            success: function(result) {
                $('#hotel_price_content').empty();
                if (result.data) {
                    $.each(result.data, function(i, p) {
                        p.selected = $.inArray(p.id, ChangeHotelDetail.hotel.priceIds) > -1;
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
                    dis: ChangeHotelDetail.NearByDistance
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
    ChangeHotelDetail.init();
});