var ScenicList = {
    pager: null,
    map: null,
    mapIcon: null,
    init: function () {
        ScenicList.initComp();
        ScenicList.initEvt();
        ScenicList.createPager();
        ScenicList.initData();
    },
    initComp: function() {
        // init BMap
        ScenicList.map = new BMap.Map("r_map");
        ScenicList.map.centerAndZoom("%E5%8E%A6%E9%97%A8", 11);
        ScenicList.mapIcon = new BMap.Icon("/image/places-16.png", new BMap.Size(20, 25));
    },
    initEvt: function () {
        ScenicList.scenicSelect('#area li');//区域选择
        ScenicList.scenicSelect('#theme li');//主题选择
        ScenicList.scenicSelect('#price li');//价格选择
        ScenicList.openlist();
        ScenicList.searchSelect();
        ScenicList.mapstick();
        // customize price btn
        $('#custom_price_btn').on('click', function(event) {
            event.stopPropagation();
            var minPrice = $('#custom_min_price').val();
            var maxPrice = $('#custom_max_price').val();
            if (!minPrice || minPrice == "" || !maxPrice || maxPrice == "") {
                alert("请填写价格范围");
                return;
            }
            ScenicList.getScenicListData();
        });
        // order btn
        $('div.searchingList div.listLeft div.list_head span').on('click', function(event) {
            event.stopPropagation();
            if (!$(this).hasClass('to-order') && $(this).index() == 0) {
                return;
            }
            var searchWord = $('#searchWord').val();
            if (searchWord && searchWord != "") {
                return;
            }
            if ($(this).index() != 0) {
                var orderType = $(this).attr('data-order-type');
                if (orderType) {
                    if (orderType == "asc") {
                        $(this).attr('data-order-type', 'desc');
                    } else if (orderType == "desc") {
                        $(this).attr('data-order-type', 'asc');
                    }
                }
            }
            ScenicList.getScenicListData();
        });
        // search btn
        $('#searchBtn').on('click', function(event) {
            event.stopPropagation();
            ScenicList.getScenicListData();
        });
        // clear all filter btn
        $('#clear_filter_btn').on('click', function(event) {
            event.stopPropagation();
            ScenicList.resetSearchData();
        });
        // clear single filter btn
        $(document).on('click', '#filter_dis span.forone label', function(event) {
            event.stopPropagation();
            var mode = $(this).parent('span.forone').attr('data-filter-mode');
            if (mode == "searchWord") {
                $('#' + mode).val(null);
                ScenicList.getScenicListData();
            } else {
                $('#' + mode + ' ul li:eq(0)').click();
            }
        });
        // filter li btn
        $(document).on('click', '#area ul li, #theme ul li, #price ul li', function(event) {
            event.stopPropagation();
            if($(this).index() == 0) {
                // unlimited btn
                event.stopPropagation();
                if ($(this).hasClass('price')) {
                    $('#custom_min_price').val(null);
                    $('#custom_max_price').val(null);
                }
                ScenicList.getScenicListData();
            } else {
                if ($(this).hasClass('customized')) {
                    return;
                } else {
                    if ($(this).hasClass('price')) {
                        $('#custom_min_price').val(null);
                        $('#custom_max_price').val(null);
                    }
                    ScenicList.getScenicListData();
                }
            }
        });
    },
    initData: function () {
        $.ajax({
            url: '/yhypc/scenic/getThemeList.jhtml',
            progress: true,
            success: function(result) {
                if (result.success) {
                    var html = "";
                    $.each(result.theme, function (i, theme) {
                        html += template("theme_list_item", theme);
                    });
                    if (result.theme.length > 7) {
                        $('#theme .openMore').show();
                    } else {
                        $('#theme .openMore').hide();
                    }
                    $("#theme ul").append(html);
                    // url param filter
                    var labelId = $('#labelId').val();
                    if (labelId && labelId != "") {
                        var $selTheme =  $('#theme ul li[data-label-id=' + labelId + ']');
                        if ($selTheme && $selTheme.length > 0) {
                            $selTheme.siblings().removeClass('takeit');
                            $selTheme.addClass('takeit');
                            // auto open
                            if ($selTheme.index() > 7) {
                                $('#theme .listopen').click();
                            }
                        }
                    }
                    var cityId = $('#cityId').val();
                    if (cityId && cityId != "") {
                        var $selCity =  $('#area ul li[data-city-id=' + cityId + ']');
                        if ($selCity && $selCity.length > 0) {
                            $selCity.siblings().removeClass('takeit');
                            $selCity.addClass('takeit');
                        }
                    }
                    // get scenic data finally
                    ScenicList.getScenicListData();
                }
            },
            error: function() {}
        });
    },

    createPager: function () {
        var options = {
            countUrl: "/yhypc/scenic/getTotalPage.jhtml",
            resultCountFn: function (result) {
                $("#totalCount").html(null).html(result[0]);
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                var scenicList = $("#scenicList");
                scenicList.empty();
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/scenic/getScenicList.jhtml',
                    progress: true,
                    data: data,
                    success: function(result) {
                        var html = "";
                        // clear map overlay
                        ScenicList.map.clearOverlays();
                        $.each(result.data, function (i, scenic) {
                            var p = new BMap.Point(scenic.longitude, scenic.latitude);
                            var marker = new BMap.Marker(p);
                            //var marker = new BMap.Marker(p);
                            ScenicList.map.addOverlay(marker);
                            html += template("scenic_item", scenic);
                        });
                        scenicList.append(html);
                        scroll(0, 180);
                    },
                    error: function() {

                    }
                });
            }
        };
        ScenicList.pager = new Pager(options);
    },

    getScenicListData: function () {
        var searchRequest = ScenicList.buildSearchData();
        ScenicList.pager.init(searchRequest);
    },

    buildSearchData: function() {
        var searchRequest = {};
        // clear filter display
        $('#filter_dis').empty();
        // search word
        var searchWord = $('#searchWord').val();
        var orderList = $('div.searchingList div.listLeft div.list_head');
        if (searchWord && searchWord != "") {
            searchRequest['searchRequest.name'] = searchWord;
            ScenicList.displayFilter(null, 'searchWord', searchWord);
            orderList.find("span").removeClass("li_he_active");
        } else if (orderList.find('span.li_he_active').length == 0){
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
        // area
        var $areaSel = $('#area ul li:gt(0).takeit');
        $.each($areaSel, function(i, a) {
            searchRequest['searchRequest.cityIds[' + i + ']']= $(a).attr('data-city-id');
            ScenicList.displayFilter(null, 'area', $.trim($(a).html()));
        });
        // theme labels
        var $themeSel = $('#theme ul li:gt(0).takeit');
        $.each($themeSel, function(i, t) {
            var lid = $(t).attr('data-label-id');
            searchRequest['searchRequest.labelIds[' + i + ']'] = lid;
            ScenicList.displayFilter(lid, 'theme', $.trim($(t).html()));
        });
        // price
        var $priceSel = $('#price ul li:gt(0).takeit');
        if ($priceSel.length > 0) {
            if ($priceSel.attr('data-price-mode') == "range") {
                var minPrice = $priceSel.attr('data-min-price');
                var maxPrice = $priceSel.attr('data-max-price');
                searchRequest['searchRequest.priceRange[0]'] = minPrice;
                if (maxPrice && maxPrice != "") {
                    searchRequest['searchRequest.priceRange[1]'] = maxPrice;
                }
                ScenicList.displayFilter(null, "price", $.trim($priceSel.html()));
            } else if ($priceSel.attr('data-price-mode') == "customize") {
                var minPrice = $('#custom_min_price').val();
                var maxPrice = $('#custom_max_price').val();
                searchRequest['searchRequest.priceRange[0]'] = minPrice;
                searchRequest['searchRequest.priceRange[1]'] = maxPrice;
                ScenicList.displayFilter(null, "price", minPrice + "-" + maxPrice + "元");
            }
        }
        if ($('#filter_dis').children().length > 0) {
            $('#clear_filter_btn').show();
        } else {
            $('#clear_filter_btn').hide();
        }
        return searchRequest;
    },
    resetSearchData: function () {
        // reset searchWord
        $('#searchWord').val(null);
        // reset area
        $('#area ul li').removeClass('takeit');
        $('#area ul li:eq(0)').addClass('takeit');
        // reset theme label
        $('#theme ul li').removeClass('takeit');
        $('#theme ul li:eq(0)').addClass('takeit');
        // reset price
        $('#price ul li').removeClass('takeit');
        $('#custom_min_price').val(null);
        $('#custom_max_price').val(null);
        $('#price ul li:eq(0)').addClass('takeit');
        ScenicList.getScenicListData();
    },
    displayFilter: function(id, mode, text) {
        var data = {};
        data['id'] = id;
        data['mode'] = mode;
        data['text'] = text;
        var $f = template('filter_dis_tpl', data);
        $('#filter_dis').append($f);
    },





    scenicSelect: function (li) {
        var Li = $(li);
        $(document).on("click", li, function () {
            $(this).addClass('takeit').siblings().removeClass('takeit');
        });
    },

    openlist: function () {
        var k = 0;
        $(document).on("click", '#theme .listopen', function () {
            if (k == 0) {
                $('#theme ul').css({'height': 'auto'});
                $('#theme .listopen').addClass('listshut');
                $(this).html('收起');
                k = 1;
            } else {
                $('#theme ul').css({'height': '45px'});
                $('#theme .listopen').removeClass('listshut');
                $(this).html('展开');
                k = 0;
            }
        });
    },

    // 排序: 编辑推荐，好评，价格
    searchSelect: function () {
        var Btn = $('.searchingList .list_head span');
        Btn.on("click", function () {
            var searchWord = $('#searchWord').val();
            if (searchWord && searchWord != "") {
                return;
            }
            if ($(this).hasClass('li_he_active')) {
                if ($(this).index() == 0) {
                    $(this).removeClass('to-order');
                    return;
                }
                if ($(this).hasClass('ace')) {
                    $(this).removeClass('ace').addClass('dace');
                } else if ($(this).hasClass('dace')) {
                    $(this).removeClass('dace').addClass('ace');
                }
            } else {
                if ($(this).index() == 0) {
                    $(this).addClass('to-order');
                }
                $(this).addClass('li_he_active').addClass('white');
                $(this).siblings().removeClass('li_he_active');
                $(this).siblings().removeClass('white');
            }
        });
    },

    //地图固定
    /*mapstick: function () {
        var mapTop = $('.searchingList .listRight').position().top;
        var left = ($(window).width() - 1200) / 2 + 780;
        $(window).scroll(function () {
            if ($('body').scrollTop() > mapTop) {
                if (($('body').height() - $('body').scrollTop()) > 800) {
                    $('.searchingList .listRight').css({'position': 'fixed', 'top': 0, 'left': left,'margin-top': 15});
                } else {
                    $('.searchingList .listRight').css({'position': 'fixed', 'top': -230, 'left': left,'margin-top': 15});
                }
            } else {
                $('.searchingList .listRight').css({'position': 'static', 'margin-top': 0});
            }
        });
    }*/
    mapstick: function () {
        /*var mapTop = $('.searchingList .listRight').position().top;*/
        /*var left = ($(window).width() - 1200) / 2 + 780;*/
        var minTop = $('.searchingList').offset().top;
        $(window).scroll(function () {
            var scrollTop = $(document).scrollTop();
            if (scrollTop > minTop && scrollTop < 1622) {
                /*$('.searchingList .listRight').css({'margin': 'fixed', 'top': 0, 'left': left,'margin-top': 15});*/
                $('.searchingList .listRight').css({marginTop:scrollTop-minTop + 20});
            } else if(scrollTop < minTop){
                $('.searchingList .listRight').css({marginTop:0});
            }else if(scrollTop > 1622){
                $('.searchingList .listRight').css({marginTop:1181});
            }
        });
    }
};

$(function () {
    ScenicList.init();
    $(".list_box3 .checked input:first").attr("checked",true);
    $(".list_box .checked input:first").attr("checked",true);
});


