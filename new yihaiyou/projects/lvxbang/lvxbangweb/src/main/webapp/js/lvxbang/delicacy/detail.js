var i = 0;

$(function () {

    //var map = new BMap.Map("dituContent", {maxZoom: 14});//在百度地图容器中创建一个地图
    //window.map = map;//将map变量存储在全局
    //$("div.guide_div").hide();
    $(".d_add_a").click(function () {
        createMap(this);
    });
    collect(".d_collect", true, ".collectNum");

    $(".ft_list li:gt(2)").hide();
    if ($(".ft_list li").size() < 4) {
        $("#more_restaurant").hide();
    }
    $(".f_more").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).html("收起<i></i>").addClass("checked");
            $(".ft_list li:gt(2)").fadeToggle(1000);
            $(this).attr("data-staute", "1");
        } else {
            $(this).html("查看更多餐厅<i></i>").removeClass("checked");
            $(".ft_list li:gt(2)").hide();
            $(this).removeAttr("data-staute");
        }
    });
    //栏目滚动
    $(".d_select_d dd").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        var height = $("#id" + ($(this).index() + 1)).offset().top - 55;
        $('html,body').animate({"scrollTop": height}, 1000);
    });

    //自动切换
    var scrollHandler = function () {
        var scrollTop = $(window).scrollTop();

        var total = $('.d_select_d').children().length;
        var $span;
        for (var i = total; i > 0; i--) {
            if (scrollTop >= $('#id' + i).offset().top - 55) {
                $span = $('.span' + i);
                break;
            }
        }

        //如果找不到,选第一个
        if (!$span) {
            $span = $('.span1');
        }

        if (!$span.hasClass('checked')) {
            $('dd.checked').removeClass('checked');
            $span.addClass("checked")
        }
    };

    $(".is_hover").hover(function () {
        $(this).find("span").hide();
    }, function () {
        $(this).find("span").show();
    });

    $(window).bind("scroll", scrollHandler);

    LvxbangMap.init({scenicMark:"tpl-map-delicacy"});

});

function toDelicacyCity() {
    $('#formid').submit();
}

function createMap(restaurant) {
    var delicacy = {};
    delicacy.lng = $(restaurant).attr("data-restaurant-baiduLng");
    delicacy.lat = $(restaurant).attr("data-restaurant-baiduLat");
    delicacy.name = $(restaurant).attr("data-restaurant-name");
    var dom = $(restaurant).closest("li");
    delicacy.cover = dom.find("img").attr("src");
    delicacy.tel = dom.find(".js").eq(1).find("span").text();
    delicacy.address =  dom.find(".js").eq(0).find("span").text();
    delicacy.comment =  dom.find(".food_p").text();
    LvxbangMap.clear();
    LvxbangMap.addAScenic(delicacy);
    LvxbangMap.toBigger();
    LvxbangMap.location(delicacy.lng, delicacy.lat, 13);
    //var point = new BMap.Point($(restaurant).attr("data-restaurant-baiduLng"),
    // $(restaurant).attr("data-restaurant-baiduLat"));//定义一个中心点坐标 map.centerAndZoom(point,
    // 17);//设定地图的中心点和坐标并将地图显示在地图容器中 var local = new BMap.LocalSearch(map, { renderOptions: {map: map}, pageCapacity:
    // 1, onMarkersSet: function (pois) { if (pois.length > 0) { var icon = pois[0].marker.getIcon();
    // icon.setImageOffset(new BMap.Size(0, 0 - i * 25)); pois[0].marker.setIcon(icon); i++; $(restaurant).on("click",
    // function () { pois[0].marker.V.click(); pois[0].marker.setTop(true); map.centerAndZoom(point, 17); var search =
    // new BMap.LocalSearch(map, { onSearchComplete: function (result) { if (local.getStatus() == BMAP_STATUS_SUCCESS
    // && result.getPoi(0) != null) { var str = ""; str += "乘坐" + result.getPoi(0).address + "到\"" +
    // result.getPoi(0).title + "\"站。"; $("#bus").text(str); $("div.guide_div").show(); } } });
    // search.searchNearby("公交站", pois[0]); var height = $("#dituContent").offset().top - 55;
    // $('html,body').animate({"scrollTop": height}, 1000); }); } } });
    // local.search($(restaurant).attr("data-restaurant-name"));
}

