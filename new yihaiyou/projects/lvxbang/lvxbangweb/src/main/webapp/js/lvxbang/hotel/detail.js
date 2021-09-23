$(document).ready(function () {
    collect(".d_collect", true, ".collectNum");
    getDate();
});

function HotelDetail() {
    this.refreshRoomList = function () {
      hotelPrices();
    }
}
function hotelPrices(){
    var date = $("#check-in-date").val();

    if (!date) {
        var date1 = new Date();
        var newDate = new Date();
        date1.setDate(date1.getDate() + 1);
        //date = newDate.format("yyyy-MM-dd");
        $("#check-in-date").val(date1.format("yyyy-MM-dd"));
        newDate.setDate(newDate.getDate() + 2);
        $("#check-out-date").val(newDate.format("yyyy-MM-dd"));
    }
    date = $("#check-in-date").val();
    var date2 = $("#check-out-date").val();
    if(isNull(date2)){
        promptWarn("退房日期不能为空",1000);
    }
    var data = {
        hotelId: $("#hotelId").val(),
        priceStartDate: date,
        priceEndDate: $("#check-out-date").val(),
        status: "UP"
    };
    $.getJSON("/lvxbang/hotel/listPrice.jhtml", data, function (result) {
        if (!result.success) {
            //return alert("查询的日期没有房间");
            $("#hotel_house").next().remove();
            $("#hotel_house").remove();
            return promptWarn("查询的日期没有房间");
        }

        var html = "";

        $.each(result.hotelPrices, function (i, data) {
            data.hotelId = result.hotelId;
            if(data.status == "GUARANTEE"){
                data.color = "#EEDC82";
                data.status = "担保";
            }else{
                data.color = "#69A7EB";
                data.status = "到付";
            }
            html += template("tpl-hotel-room", data);
        });


        //html = html + template("tpl-hotel-more",null);
        //$(".Hotels_lb_cen").html(html);
        $("#hotel_house").siblings("ul").remove();
        $("#hotel_house").before(html);
        $(".statusContent").hover(function(){
            $(this).next().show();
        },function(){
            $(this).next().hide();
        });
        $('.roomDescription').mouseover(function(){
            $(this).find('span').css("display","block");
        }).mouseout(function(){
            $(this).find('span').css("display","none");
        });
        if (result.hotelPrices.length<=3){
            $("#hotel_house").next().hide();
            $("#hotel_house").hide();
        }
        var thiz = $(".Hotels_lb_cen .more");
        $(".Hotels_lb_cen ul:gt(2)").slideUp();
        $("a", thiz).html("查看更多房价<i></i>").removeClass("checked");
        $(thiz).removeAttr("data-staute");
        //查看更多票价
        $(".Hotels_lb_cen .more").off("click");
        $(".Hotels_lb_cen .more").on("click", function () {
            var myStaute = $(this).attr("data-staute");
            var uls;
            if ($(".Hotels_lb_top .checkbox").hasClass("checked")) {
                uls = $(".hasbreakfast:gt(2)").parent();
            } else {
                uls = $(".breakfast:gt(2)").parent();
            }
            if (!myStaute) {
                uls.slideDown();
                $("a", this).html("收起<i></i>").addClass("checked");
                $(this).attr("data-staute", "1");
            } else {
                uls.slideUp();
                $("a", this).html("查看更多房价<i></i>").removeClass("checked");

                $(this).removeAttr("data-staute");
            }
        });

        if (result.lineResponses.length > 0) {
            var lineHtml = template("tpl-line", result);
            $("#id1").append(lineHtml);
            if ($("#line_list ul").length > 2) {
                var uls = $("#line_list ul:gt(2)");
                uls.hide();
                $("#line_list .more").show().click(function () {
                    var myStaute = $(this).attr("data-staute");
                    if (!myStaute) {
                        uls.slideDown();
                        $("a", this).html("收起<i></i>").addClass("checked");
                        $(this).attr("data-staute", "1");
                    } else {
                        uls.slideUp();
                        $("a", this).html("查看更多线路<i></i>").removeClass("checked");
                        $(this).removeAttr("data-staute");
                    }
                });
            }
        }
    });
}
function selectCities(a) {
    var city = $(a).children("span").text();
    $("#cities").val(city);
    $("#toList").submit();
}

$(document).ready(function () {
    //日历控件补充
    $('.ico.ico2').click(function(){
        $(this).next().click().focus();

    });
    //入住日期变化时自动
    $('#check-in-date').change(function(){
        //$('#check-out-date').val('');
        //if(isNull($('#check-out-date').val())){
        //    $('#check-out-date').focus();
        //}
        var lDate = new Date(Date.parse($('#check-in-date').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#check-out-date').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#check-out-date').val(lDate.format("yyyy-MM-dd"));
        }
        $('#check-out-date').focus().click();
    });
    //留言
    $(".reply").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).text("收起");
            $(this).parent(".synopsis").siblings(".message").animate({"height": "130px"}, 600);
            $(this).attr("data-staute", "1");
        } else {
            $(this).text("回复");
            $(this).parent(".synopsis").siblings(".message").animate({"height": "0px"}, 600);
            $(this).removeAttr("data-staute");
        }
    });


    //栏目滚动
    $(".d_select_d dd").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        var height = $("#id" + ($(this).index() + 1)).offset().top - 50;
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
            $('dd.checked').removeClass('checked')
            $span.addClass("checked")
        }
    };
    $(window).bind("scroll", scrollHandler);

    //创建和初始化地图函数：
    function initMap() {
        $("div.guide_div").hide();
        var mapContainer = $("#mapContainer");
        var map = new BMap.Map("mapContainer",{enableMapClick:false});//在百度地图容器中创建一个地图
        var point = new BMap.Point(mapContainer.attr("lng"), mapContainer.attr("lat"));//定义一个中心点坐标
        map.enableScrollWheelZoom();//启用滚轮放大缩小
        map.centerAndZoom(point, 17);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
        var local = new BMap.LocalSearch(map, {
            renderOptions: {map: map}, pageCapacity: 1,
            onSearchComplete: function (result) {
                var search = new BMap.LocalSearch(map, {
                    onSearchComplete: function (result) {
                        if (local.getStatus() == BMAP_STATUS_SUCCESS && result.getPoi(0) != null) {
                            var str = "";
                            str += "乘坐" + result.getPoi(0).address + "到\"" + result.getPoi(0).title + "\"站。";
                            $("#bus").text(str);
                        }
                    }
                });
                search.searchNearby("公交站", result.getPoi(0));
            }
        });
        local.search(mapContainer.attr("scenic-name"));
    }

    initMap();//创建和初始化地图

    var hotelDetail = new HotelDetail();
    hotelDetail.refreshRoomList();
    $("#hotel-searcher").click(function () {
        hotelDetail.refreshRoomList()
    });

    $(".add_a").click(function () {
        var height = $("#mapContainer").offset().top - 50;
        $('html,body').animate({"scrollTop": height}, 1000);
    })


    //是否含早餐筛选按钮
    //checkbox
    $(".Hotels_lb_top .checkbox").click(function () {
        if ($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            $(".breakfast").parent().slideDown();
        }
        else {
            $(this).addClass("checked");
            $(".hasbreakfast").parent().slideDown();
            $(".nobreakfast").parent().hide();
        }
        var thiz = $(".Hotels_lb_cen .more");

        if ($(".Hotels_lb_cen ul:visible").size() <= 3) {
            $("#hotel_house").next().hide();
            $("#hotel_house").hide();
        } else {
            $("#hotel_house").next().show();
            $("#hotel_house").show();
            $(".Hotels_lb_cen ul:visible:gt(2)").slideUp();
            $("a", thiz).html("查看更多房价<i></i>").removeClass("checked");
            $(thiz).removeAttr("data-staute");
    }
    });

    $(".is_hover").hover(function () {
        $(this).find("span").hide();
    }, function () {
        $(this).find("span").show();
    });
});
//当入住日期改变时，清空退房日期
//function cleanCheckoutdate(){
//    $('#check-out-date').val('');
//}
//function changedate(){
//    //入住日期变化时自动
//    $('#check-in-date').change(function(){
//        if(isNull($('#check-out-date').val())){
//            $('#check-out-date').focus();
//        }
//    });
//}
//读取从列表页传过来的时间
function getDate(){
    var date = JSON.parse(getUnCodedCookie("date"));
    //console.log(document.referrer);
    if(!isNull(date)) {
        $('#check-in-date').val(date.startDate);
        $('#check-out-date').val(date.endDate);
    }
}