var planId;
$(function () {
    initUI();

    planId = $("#planId").val();

    destinationInfo();
    initHdMap();
    initRecommendScenic();
    initRecommendPanel();
    citySearch();

    var scrollTop = $(document).height();
    $(".main").height(scrollTop - 80);

    $(".Pop_ups_div .but").click(function () {
        $(this).addClass("checked").html("<i></i>已加入线路");
    });
    //搜索框
    $(".categories .input").click(function () {
        $(".categories_dl").hide();
        $(this).next(".categories_div").show();
    });

    $(".categories_div li").click(function () {
        var label = $("label", this).text();
        $(this).closest(".categories").children(".input").val(label);
    });

    $('.categories  .input,.HandDrawing_ul li').on('click', function (event) {
        // 阻止冒泡
        if (event.stopPropagation) {    // standard
            event.stopPropagation();
        } else {
            // IE6-8
            event.cancelBubble = true;
        }
    });
    $(document).on("click", function () {
        $(".categories_div,.categories_dl").hide();
    });


    //搜索框
    $(".HandDrawing_ul li").click(function () {
        $(".categories_div,.categories_dl").hide();
        $(this).children(".categories_dl").show();
    });


    //搜索历史
    var searchHistoryTxt = $("#searchHistoryTxt");
    var searchHistory = JSON.parse(getCookie("hd_map_history"));
    //window.console.info('searchHistory:' + searchHistory);
    if (searchHistory != null && searchHistory.length > 0) {
        //window.console.info('searchHistoryTxt:' + searchHistoryTxt);
        for (var i = 0; i < searchHistory.length; i++) {
            var data = searchHistory[i];
            if (data != null) {
                // console.info(data);
                var span = "<a href='map_" + data.id + ".html'><span>" + data.name + "</span></a> ";
                searchHistoryTxt.append(span);
            }
        }
    }

    $(".Addmore_nr a").click(function () {
        var id = $(this).attr('data-id');
        if (id == undefined)
            return;
        if (searchHistoryTxt.html().indexOf(id) > -1)
            return;
        var data = {
            id: id,
            name: $(this).attr('data-name')
        };
        var as = new Array(data);
        //window.console.info('as:' + as)
        searchHistory = as.concat(searchHistory);
        if (searchHistory.length > 5) {
            searchHistory = searchHistory.slice(0, 5);
        }
        setCookie("hd_map_history", JSON.stringify(searchHistory));
        //window.console.info('set cookie:' + JSON.stringify(searchHistory))
    });

    //点击打开
    $(".top_attractions").click(function () {
        $(this).hide();
        $(".ta_list").animate({"width": "100%"}, 500);
    });

    //点击关闭
    $(".ta_list .close").click(function () {

        $(".ta_list").animate({"width": "0px"}, 500, function () {
            $(".top_attractions").show();
        });
    });

    $(".Pop_ups_div .close").click(function () {

        $(".Pop_ups").hide(500);
    });

    //判断浏览器版本
    var userAgent = navigator.userAgent.toLowerCase();
    jQuery.browser = {
        version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
        safari: /webkit/.test( userAgent ),
        opera: /opera/.test( userAgent ),
        msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
        mozilla: /mozilla/.test(userAgent)&&!/(compatible|webkit)/.test(userAgent)
    };

    if(jQuery.browser.msie){
        $(".top_attractions").css("bottom", "80px");
        $(".ta_list").css("bottom", "80px");
    }

});

function drawScenicCount() {
    $(".trips-wrap").find("i").html(planPanel.scenicCount());
}

function destinationInfo() {
    $.ajax({
        url: "/lvxbang/handDraw/getMaps.jhtml?t=" + new Date().getTime(),
        data: {cityId: editCfg.cityCode()},
        async: false,
        dataType: "json",
        success: function (result) {
            // 初始化地图

            $("#J_hd-tips-img").hide();
            hdConfig.bounds = result;

            hdConfig.hdmin = 19;
            hdConfig.hdmax = 1;
            for (var i = 0; i < result.length; i++) {
                if (result[i].level < hdConfig.hdmin) {
                    hdConfig.hdmin = result[i].level;
                }
                if (result[i].level > hdConfig.hdmax) {
                    hdConfig.hdmax = result[i].level;
                }
            }
            $("#map-min-level").val(hdConfig.hdmin);
            initHdScenics();
        }
    });
}

var bmap;
function initBMap(lng, lat) {
    bmap = new BMap.Map("J_planmap-panel", {enableMapClick: false});
    bmap.centerAndZoom(new BMap.Point(lng, lat), 11);
}

function initUI() {
    //$("#set-start-date").placeholder({tip_class: "set-time-label"});
    adjustHeight();
    $(window).resize(function () {
        adjustHeight();
    });
    $("body").click(function () {
        $(".day-list-ul").hide();
    });
}

function adjustHeight() {
    var h = $(window).height() - $(".header-top-wrapper").outerHeight();
    $(".auto-height-panel").height(h);
    $(".auto-height-panel1").height(h - 122);
    var height1 = h - 48;
    $(".auto-height-panel4").height(height1 - 38);
    $(".auto-height-panel5").height(height1 - 124);
    var width1 = $(window).width() - 320;
    $(".auto-width-panel").width(width1);
    $(".right-btn").removeClass("disable");
}

function initHdMap() {
    createHdMap(editCfg.cityCode(), hdConfig);
    loadHdScenic(editCfg.cityCode(), hdConfig);
}

function initHdScenics() {
    $.getJSON("/lvxbang/handDraw/getScenic.jhtml?t=" + new Date().getTime(), {
        cityId: editCfg.cityCode(),
        level: parseInt($("#map-min-level").val()) + 1,
        page: 1,
        pageSize: 20,
        rankFlag: true
    }, function (result) {
        if (result.success) {
            $("#J_hmap-scenic").html("");
            for (var i = 0; i < result.data.length; i++) {
                // 查询景点数据
                $.ajax({
                    url: "/scenic/ajax/outline?t=" + new Date().getTime(),
                    data: {scenicId: result.data[i].scenicId},
                    async: false,
                    success: function (infores) {
                        if (infores.success) {
                            infores.data.lat = result.data[i].lat;
                            infores.data.lng = result.data[i].lng;
                            var myli = $(template("tpl-map-scenic", infores.data));
                            myli.find(".nslog").nslog();
                            $("#J_hmap-scenic").append(myli);
                        }
                    }
                });
            }
        }
        ;
    });
}

function initCitys() {
    $("#J_hmap-scenic").append($("#tpl-map-city").html());
    $("#J_hmap-scenic").find(".nslog").nslog();
}

// 城市选择
function citySearch() {
    //$("#J_city").placeholder({tip_class: "index-label"});
    $("#J_city").search({tplid: "tpl-search-result"});
}

function showCityList() {
    try {
        stopEvent(event);
    }
    catch (e) {
        if ($.browser.mozilla) {
            var $E = function () {
                var c = $E.caller;
                while (c.caller)c = c.caller;
                return c.arguments[0];
            };
            __defineGetter__("event", $E);
            event.stopPropagation();
        }
        ;
    }

    $("#J_popup_city").show();
}

function goontrip() {
    if (isNull($("#J_city").val())) {
        $("#J_city").focus();
        try {
            event.stopPropagation();
        }
        catch (e) {
        }
        return false;
    }
    else {
        $.post("/destination/ajax/search", {term: $("#J_city").val()}, function (result) {
            if (result.success && result.data.length > 0) {
                window.location.href = "/hdmap/" + result.data[0].cityCode + ".html";
            }
            else {
                $("#J_city").focus();
            }
        },"json");
    }
}

// 在地图上显示
function openMap() {
    var myli = getEvtTgt().parents("li:first");
    var lng = myli.attr("lng");
    var lat = myli.attr("lat");
    var scenicId = myli.attr("scenicId");
    showMap(scenicId, lng, lat);
}

function showMap(scenicId, lng, lat) {
    map.setZoom(parseInt($("#map-min-level").val()) + 1);
    window.setTimeout(function () {
        hdInfWin.open(new BMap.Point(lng, lat), scenicId);
    }, 500);
}

// 加入行程
function addHpToTrip(scenicId) {
    var mydiv = getEvtTgt().parents(".sceInfobox:first");
    var top = mydiv.css("top");
    var left = mydiv.css("left");
    var src = mydiv.find("img").attr("src");
    var node = $(template("tpl-move-img", {imgsrc: src}));
    node.find(".small-img").css({"top": top, "left": left});
    $("body").append(node);
    $(".small-img").animate({top: "30%", left: "98%", marginTop: "-20px"}, "slow");
    var scount = $(".trips-wrap").find("i").text();
    window.setTimeout(function () {
        $(".small-img").remove();
        $(".trips-wrap").find("i").html(parseInt(scount) + 1);
    }, 700);

    if (parseInt(scount) == 0) {
        $(".smart-sort-ball").removeClass("disable");
        $("#J_scenic-list").html("");
    }

    ETaddToTirp(scenicId, drawScenicCount);
}

function myPlan() {
    $(".right-plan-panle-wrap").animate({right: 0});
}

function closePlanPanel() {
    $(".right-plan-panle-wrap").animate({right: "-100%"});
    drawScenicCount();
}

function showGuessList() {
    if (getEvtTgt().hasClass("active")) {
        closeGuessList();
    }
    else {
        $(".hdmap-footer").animate({left: 0});
        $(".guess-like-btn").addClass("active");
    }
}

function closeGuessList() {
    $(".guess-like-btn").removeClass("active");
    $(".hdmap-footer").animate({left: "-100%"});
}

function leftScroll() {
    if ($(".left-btn").hasClass("disable")) {
        return;
    }
    else {
        $(".right-btn").removeClass("disable");
        var w = Math.floor($(".scenic-list-panel").width() / 170) * 170;
        var tgt = $(".scenic-list-panel").scrollLeft() - w;
        $(".scenic-list-panel").animate({scrollLeft: tgt}, 500, function () {
            if ($(".scenic-list-panel").scrollLeft() == 0) {
                $(".left-btn").addClass("disable");
            }
        });
    }
}

function rightScroll() {
    if ($(".right-btn").hasClass("disable")) {
        return;
    }
    else {
        $(".left-btn").removeClass("disable");
        var w = Math.floor($(".scenic-list-panel").width() / 170) * 170;
        var tgt = $(".scenic-list-panel").scrollLeft() + w;
        $(".scenic-list-panel").animate({scrollLeft: tgt}, 500, function () {
            var sleft = $(".scenic-list-panel").scrollLeft();
            var sw = $(".scenic-list-panel").width();
            if (sleft >= (3400 - sw)) {
                $(".right-btn").addClass("disable");
                return;
            }
        });
    }
}

var editCfg =
{
    cityCode: function () {
        var cityId = $("#city_code").val();
        return cityId;
    },
    cityName: function () {
        var cityName = $(".city-name").text();
        return cityName;
    },
    scenicId: function () {
        var scenicId = $("#city_scenicId").val();
        return scenicId;
    }
};

// 防止报错
function updateScenicPage() {

}

function closeOne() {

}

function drawAllMarker() {

}

function initRecommendScenic() {
    $.getJSON("/lvxbang/handDraw/getRecommendScenic.jhtml?t=" + new Date().getTime(), {cityId: $("#city_code").val()}, function (result) {
        var scenicList = result;

        $.each(scenicList, function (index, scenic) {
            var scenicPanel = $(template("tpl-recommend-scenic-item", {scenic: scenic}));
            $(".ta_list_ul").append(scenicPanel);
            scenicPanel.click(function () {
                var tempScenic = hdScenic[scenic.id + "_" + map.getZoom()];
                if (tempScenic) {
                    hdInfWin.open(new BMap.Point(tempScenic.lng, tempScenic.lat), scenic.id);
                } else {
                    hdInfWin.open(new BMap.Point(scenic.lng, scenic.lat), scenic.id, {
                        top: 90,
                        left: $(this).position().left
                    });
                }
            });
        });

        initRecommendPanel();
    });
}

//左右滚动
function initRecommendPanel() {
    var ta_list_ul = $(".ta_list_ul li").length;
    var width = ta_list_ul * 162;
    $(".ta_list_ul").css("width", width);
    var nowPage = $(".ta_list_ul").attr("num");
    $('.ta_list_div .left').click(function () {
        var ta_list_div_d = $(".ta_list_div_d").width();
        if (nowPage > 1) {
            $(".ta_list_ul").animate({"margin-left": "+=" + ta_list_div_d}, 500);
            nowPage--;
        }
    });
    $('.ta_list_div .right').click(function () {
        var ta_list_div_d = $(".ta_list_div_d").width();
        var zong = ta_list_div_d * nowPage;
        if (width > ta_list_div_d && zong < width) {
            $(".ta_list_ul").animate({"margin-left": "-=" + ta_list_div_d}, 500);
            nowPage++;
        }
        $(".ta_list_ul").attr("num", nowPage);
    });

};