/**
 * Created by zzl on 2017/1/3.
 */
var ChangeHotelList = {
    pager: null,
    map: null,
    mapIcon: null,
    init: function () {
        ChangeHotelList.initComp();
        ChangeHotelList.initEvt();
        ChangeHotelList.createPager();
        ChangeHotelList.initData();
    },
    initComp: function () {
        // init BMap
        ChangeHotelList.map = new BMap.Map("r_map");
        ChangeHotelList.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 11);
        ChangeHotelList.mapIcon = new BMap.Icon("/image/places-16.png", new BMap.Size(20, 25));
    },
    initEvt: function () {
        // customize price btn
        $('#custom_price_btn').on('click', function (event) {
            event.stopPropagation();
            var minPrice = $('#custom_min_price').val();
            var maxPrice = $('#custom_max_price').val();
            if (!minPrice || minPrice == "" || !maxPrice || maxPrice == "") {
                $.message.alert({
                    title: "提示",
                    info: "请填写价格范围"
                });
                return;
            }
            ChangeHotelList.getHotelListData();
        });
        // order btn
        $('div.searchingList div.listLeft div.list_head span').on('click', function (event) {
            event.stopPropagation();
            if (!$(this).hasClass('to-order') && $(this).index() == 0) {
                return;
            }
            var name = $('#searchWord').val();
            if (name && name != "") {
                return;
            }
            var orderType = $(this).attr('data-order-type');
            if (orderType) {
                if (orderType == "asc") {
                    $(this).attr('data-order-type', 'desc');
                } else if (orderType == "desc") {
                    $(this).attr('data-order-type', 'asc');
                }
            }
            ChangeHotelList.getHotelListData();
        });
        // search btn
        $('#searchBtn').on('click', function (event) {
            event.stopPropagation();
            ChangeHotelList.getHotelListData();
        });
        // clear all filter btn
        $('#clear_filter_btn').on('click', function (event) {
            event.stopPropagation();
            ChangeHotelList.resetSearchData();
        });
        // clear single filter btn
        $(document).on('click', '#filter_dis span.forone label', function (event) {
            event.stopPropagation();
            var mode = $(this).parent('span.forone').attr('data-filter-mode');
            var id = $(this).parent('span.forone').attr('data-filter-id');
            if (mode == "startDate" || mode == "endDate") {
                return;
            } else if (mode == "searchWord") {
                $('#' + mode).val(null);
                ChangeHotelList.getHotelListData();
            } else if (mode == "service") {
                $('#' + mode + '_ul li[data-service-id=' + id + ']').click();
            } else {
                $('div.dirbox div.' + mode + ' div.limit span.limited').click();
            }
        });
        // filter li btn
        $(document).on('click', '.dirbox li.radio, .dirbox li.checkbox', function (event) {
            event.stopPropagation();
            if ($(this).hasClass('custom-price')) {
                return;
            } else {
                if ($(this).hasClass('price')) {
                    $('#custom_min_price').val(null);
                    $('#custom_max_price').val(null);
                }
                ChangeHotelList.getHotelListData();
            }
        });
        // unlimited btn
        $('span.limited').on('click', function (event) {
            event.stopPropagation();
            if ($(this).hasClass('price')) {
                $('#custom_min_price').val(null);
                $('#custom_max_price').val(null);
            }
            ChangeHotelList.getHotelListData();
        });

    },
    initData: function () {
        // get region data
        $.ajax({
            url: '/yhypc/hotel/getListFilterData.jhtml',
            progress: true,
            success: function (result) {
                $.each(result.hotelRegions, function (i, r) {
                    var region_item = template('pos_tpl', r);
                    $('#position_ul').append(region_item);
                });
                $.each(result.hotelCityBrands, function (i, r) {
                    var brand_item = template('brand_tpl', r);
                    $('#brand_ul').append(brand_item);
                });
                $.each(result.hotelCityServices, function (i, r) {
                    var service_item = template('service_tpl', r);
                    $('#service_ul').append(service_item);
                });
                // url param filter
                var selRegionId = $('#regionId').val();
                var selStar = $('#star').val();
                if (selRegionId && selRegionId != "") {
                    var $posSel = $('#position_ul li[data-region-id = ' + selRegionId + ']')
                    $posSel.click();
                    if ($posSel.index() > 6) {
                        $('#posMore').click();
                    }
                }
                if (selStar && selStar != "") {
                    $('#star_ul li[data-star=' + selStar + ']').click();
                }
                // get hotel data finally
                ChangeHotelList.getHotelListData();
            }
        });
    },
    getHotelListData: function () {
        var searchRequest = ChangeHotelList.buildSearchData();
        ChangeHotelList.pager.init(searchRequest);
    },
    createPager: function () {
        var page = {
            countUrl: '/yhypc/hotel/countList.jhtml',
            resultCountFn: function (result) {
                $('#totalCount').html(result[0]);
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                scroll(0, 180);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/hotel/getHotelList.jhtml',
                    data: data,
                    progress: true,
                    success: function (result) {
                        $('#list_content').empty();
                        // clear map overlay
                        ChangeHotelList.map.clearOverlays();
                        $.each(result, function (i, h) {
                            h.index = i + 1;
                            var p = new BMap.Point(h.lng, h.lat);
                            var marker = new BMap.Marker(p);
                            //var marker = new BMap.Marker(p);
                            ChangeHotelList.map.addOverlay(marker);
                            var hotel_item = template('hotel_item', h);
                            $('#list_content').append(hotel_item);
                        });
                    }
                })
            }
        };
        ChangeHotelList.pager = new Pager(page);
    },
    buildSearchData: function () {
        var searchRequest = {};
        // clear filter display
        $('#filter_dis').empty();
        // date
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        if (startDate && startDate != "") {
            searchRequest['searchRequest.startDate'] = startDate;
            ChangeHotelList.displayFilter(null, "startDate", "入住:" + startDate);
        }
        if (endDate && endDate != null) {
            searchRequest['searchRequest.endDate'] = endDate;
            ChangeHotelList.displayFilter(null, "endDate", "退房:" + endDate);
        }
        // name
        var name = $('#searchWord').val();
        var orderList = $('div.searchingList div.listLeft div.list_head');
        if (name && name != "") {
            searchRequest['searchRequest.name'] = name;
            ChangeHotelList.displayFilter(null, "searchWord", name);
            orderList.find("span").removeClass("li_he_active");
        } else if (orderList.find('span.li_he_active').length == 0) {
            orderList.find("span:eq(0)").addClass("li_he_active");
        }
        // order
        var $orderSel = orderList.find('span.li_he_active');
        if ($orderSel.length > 0) {
            var orderColumn = $orderSel.attr('data-order-column');
            var orderType = $orderSel.attr('data-order-type');
            if (orderColumn && orderType) {
                searchRequest['searchRequest.orderColumn'] = orderColumn;
                searchRequest['searchRequest.orderType'] = orderType;
            }
        }
        // position
        var $posSel = $('#position_ul').find('li.radioCheck');
        if ($posSel.length > 0) {
            searchRequest['searchRequest.region'] = $posSel.attr('data-region-id');
            ChangeHotelList.displayFilter(null, "position", $.trim($posSel.html()));
        }
        // star
        var $starSel = $('#star_ul').find('li.radioCheck');
        if ($starSel.length > 0) {
            searchRequest['searchRequest.star'] = $starSel.attr('data-star');
            ChangeHotelList.displayFilter(null, "level", $.trim($starSel.html()));
        }
        // price
        var $priceSel = $('#price_ul').find('li.radioCheck');
        if ($priceSel.length > 0) {
            if ($priceSel.attr('data-price-mode') == "range") {
                var minPrice = $priceSel.attr('data-min-price');
                var maxPrice = $priceSel.attr('data-max-price');
                searchRequest['searchRequest.priceRange[0]'] = minPrice;
                if (maxPrice && maxPrice != "") {
                    searchRequest['searchRequest.priceRange[1]'] = maxPrice;
                }
                ChangeHotelList.displayFilter(null, "price", $.trim($priceSel.html()));
            } else if ($priceSel.attr('data-price-mode') == "customize") {
                var minPrice = $('#custom_min_price').val();
                var maxPrice = $('#custom_max_price').val();
                searchRequest['searchRequest.priceRange[0]'] = minPrice;
                searchRequest['searchRequest.priceRange[1]'] = maxPrice;
                ChangeHotelList.displayFilter(null, "price", minPrice + "-" + maxPrice + "元");
            }
        }
        // brand
        var $brandSel = $('#brand_ul').find('li.radioCheck');
        if ($brandSel.length > 0) {
            searchRequest['searchRequest.brands[0]'] = $brandSel.attr('data-brand-id');
            ChangeHotelList.displayFilter(null, 'brand', $.trim($brandSel.html()));
        }
        // service
        var $serviceSel = $('#service_ul').find('li.checktrue');
        $.each($serviceSel, function (i, s) {
            searchRequest['searchRequest.serviceAmenities[' + i + ']'] = $(s).attr('data-service-id');
            ChangeHotelList.displayFilter($(s).attr('data-service-id'), "service", $.trim($(s).html()));
        });
        if ($('#filter_dis').children().length > 0) {
            $('#clear_filter_btn').show();
        } else {
            $('#clear_filter_btn').hide();
        }
        return searchRequest;
    },
    resetSearchData: function () {
        // reset name
        $('#searchWord').val(null);
        // reset position
        $('#position_ul').find('li').removeClass('radioCheck');
        // reset star
        $('#star_ul').find('li').removeClass('radioCheck');
        // reset price
        $('#price_ul').find('li').removeClass('radioCheck');
        $('#custom_min_price').val(null);
        $('#custom_max_price').val(null);
        // reset brand
        $('#brand_ul').find('li').removeClass('radioCheck');
        // reset service
        $('#service_ul').find('li').removeClass('checktrue');
        // select all
        $('span.limited').removeClass('unlimited');
        // clear filter display
        $('#filter_dis').empty();
        $('#clear_filter_btn').hide();
        ChangeHotelList.getHotelListData();
    },
    displayFilter: function (id, mode, text) {
        var data = {};
        data['id'] = id;
        data['mode'] = mode;
        data['text'] = text;
        var $f = template('filter_dis_tpl', data);
        $('#filter_dis').append($f);
    }
};
$(function () {
    ChangeHotelList.init();
});