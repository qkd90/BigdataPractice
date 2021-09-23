var map;//小地图
function initMap() {
    // 百度地图API功能
    map = new BMap.Map("dituContent", {enableMapClick: false});//小地图
    //map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
    //map.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
    //map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
    //map.enableScrollWheelZoom();                            //启用滚轮放大缩小
}


function cleanMap() {
    map.clearOverlays();//小地图清空原来的标注
}


function addHotelMarker(data) {
    var icon = new BMap.Icon("/images/map/location.png", new BMap.Size(18, 22));
    var marker = new BMap.Marker(new BMap.Point(data.lng, data.lat), {icon: icon});  // 创建标注，为要查询的地方对应的经纬度
    map.addOverlay(marker);

}
function HotelList() {
    var hotelList = this;
    this.pager;
    this.data = {};
    this.init = function () {
        hotelList.pager = new Pager(
            {
                pageSize: 10,
                countUrl: "/lvxbang/hotel/countHotel.jhtml",
                searchData: {},
                pageRenderFn: function (pageNo, pageSize) {
                    $(".hotels_list").empty();
                    $("#loading").show();
                    scroll(0, 0);
                    hotelList.data.pageNo = pageNo;
                    hotelList.data.pageSize = pageSize;

                    $.post("/lvxbang/hotel/listHotel.jhtml", hotelList.data, function (result) {
                        var date = {};
                        date.startDate = $("#enterDate").val();
                        date.endDate = $('#leaveDate').val();
                        //var startDate = $("#enterDate").val();
                        //var endDate = $('#leaveDate').val();
                        setUnCodedCookie("date", JSON.stringify(date));
                        //setUnCodedCookie("endDate", JSON.stringify(endDate));
                        var html = "";
                        $(".hotels_list").empty();
                        recommendHotel(hotelList.data);
                        $("#loading").hide();
                        var change = false;
                        if ($("#pre-json").val().length > 0) {
                            change = true;
                            //$("#enterDate").attr({"disabled":true});
                            //$("#leaveDate").attr({"disabled":true});
                            $("#destination").unbind("click");
                        }
                        try {
                            map.centerAndZoom(new BMap.Point(result[0].lng, result[0].lat), 11);//小地图定位第一个酒店
                            map.clearOverlays();
                        } catch (e) {
                            setTimeout(function () {
                                map.centerAndZoom(new BMap.Point(result[0].lng, result[0].lat), 11);//小地图定位第一个酒店
                                map.clearOverlays();
                            }, 1000);
                        }

                        $.each(result, function (i, data) {
                            data.change = change;
                            data.price = Math.ceil(data.price);
                            html += template("tpl-hotel-list-item", data);
                            addHotelMarker(data);//小地图标记
                        });
                        $(".hotels_list").html(html).find("img").lazyload({
                            effect: "fadeIn"
                        });
                        $(".is_hover").hover(function () {
                            $(this).find("span").hide();
                        }, function () {
                            $(this).find("span").show();
                        });
                        lookMap();//地图弹窗
                    }, "json");
                }
            }
        );
        hotelList.refreshList();
    };
    this.refreshList = function (type) {
        cleanMap();
        var city = $("#destination").val();
        var cityId = getCityId(city);
        if (cityId == 0) {
            return;
        }
        $('.city').val(cityId);
        $('#destination_name2').html(city);
        $('#destination_name1').html(city);
        var des_path = $('#destination_path').val();
        $('#destination_name1').attr('href', des_path + "/city_" + cityId + ".html");
        hotelList.data = {};
        if ($("#distance").hasClass("checked")) {
            var coreScenic = $("#coreScenic").val();
            hotelList.data["coreScenic"] = coreScenic;
        }
        if (!isNull(city)) {
            hotelList.data['searchRequest.cities[0]'] = city;
        }
        if (!isNull(cityId)) {
            hotelList.data['searchRequest.cityIds[0]'] = cityId;
        }
        var name = $("#keyword").val();
        if (!isNull(name)) {
            hotelList.data['searchRequest.name'] = name;
        }

        var startDate = $("#enterDate").val();
        var endDate = $("#leaveDate").val();
        hotelList.data['searchRequest.startDate'] = startDate;
        hotelList.data['searchRequest.endDate'] = endDate;

        hotelList.data['searchRequest.star'] = $(".select_div.star").find(".checked").data("value");
        var price = ($(".select_div.price").find(".checked").data("price") + "").split(",");
        if (price.length == 1) {
            if (parseInt(price[0]) != 1) {
                hotelList.data['searchRequest.priceRange[0]'] = price[0];
            }
            else if ($("#price-min").val() != "" || $("#price-max").val() != "") {
                var priceMin = Number($('#price-min').val());
                var priceMax = Number($('#price-max').val());
                if (isNaN(priceMin) || isNaN(priceMax)) {
                    promptWarn("价格输入不正确", 1000);
                    return;
                }
                if (priceMax <= priceMin) {
                    $('#price-min').val(priceMin);
                    $('#price-max').val(priceMax);
                    promptWarn("起始价格应该小于结束价格", 1000);
                    return;
                }
                hotelList.data['searchRequest.priceRange[0]'] = $("#price-min").val() == "" ? 1 : $("#price-min").val();
                hotelList.data['searchRequest.priceRange[1]'] = $("#price-max").val() == "" ? 100000 : $("#price-max").val();
            }
        }
        if (price.length == 2) {
            hotelList.data['searchRequest.priceRange[0]'] = price[0];
            hotelList.data['searchRequest.priceRange[1]'] = price[1];
        }

        if (isNull(name) || type == 1) {
            if ($("#order").find(".checked").length < 1) {
                $("#order").find("li").eq(0).addClass("checked");
            }
            hotelList.data['searchRequest.orderColumn'] = $("#order").find(".checked").attr("orderColumn");
            hotelList.data['searchRequest.orderType'] = $("#order").find(".checked").attr("orderType");
        } else {
            $("#order").find(".checked").removeClass("checked");
        }
        var region = $("#position").find(".checked").attr("data-region");
        if (!isNull(region)) {
            hotelList.data['searchRequest.region'] = region;
        }
        var brand = $("#brand").find(".checked").attr("data-brand");
        if (!isNull(brand)) {
            hotelList.data['searchRequest.brands'] = brand;
        }
        var service = $("#service").find(".checked").attr("data-service");
        if (!isNull(service)) {
            hotelList.data['searchRequest.serviceAmenities'] = service;
        }

        hotelList.pager.init(hotelList.data);
    };

    this.getRegions = function () {
        $.ajax({
            url: "/lvxbang/hotel/listHotelRegion.jhtml",
            type: "post",
            dataType: "json",
            data: {
                'cityId': $("#city").val()
            },
            success: function (data) {
                if (data.length == 0) {
                    $("#position").hide();
                    return;
                }
                $("#position").show();
                $("#regions_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    var sname = s.name;
                    if (sname.length > 6) {
                        sname = sname.substr(0, 5) + "...";
                    }
                    var result = "<li><a title='" + s.name + "' href='javaScript:;' data-region='" + s.id + "'>" + sname + "</a></li>";
                    if(i>6){
                        result = "<li style='display: none;' class='li_hide'><a title='" + s.name + "' href='javaScript:;' data-region='" + s.id + "'>" + sname + "</a></li>";
                   }

                    $("#regions_ul").append(result);
                }
                if(data.length < 8){
                    $('.reg_more').hide();
                }else{
                    $('.reg_more').show();
                    $('.reg_more').click(function(){
                        if($.trim($(this).html()) == '更多'){
                            $("#regions_ul").find('.li_hide').show();
                            $(this).html('收起');
                        }else{
                            $("#regions_ul").find('.li_hide').hide();
                            $(this).html('更多');
                        }
                    });
                }

                $("#position").find("a").click(function () {
                    $("#position").find("a").removeClass("checked");
                    $(this).addClass("checked");
                    hotelList.refreshList();
                });
            },
            error: function () {
                $("#regions_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
            }
        });
    }
    this.getServices = function () {
        $.ajax({
            url: "/lvxbang/hotel/listHotelService.jhtml",
            type: "post",
            dataType: "json",
            data: {
                'cityId': $("#city").val()
            },
            success: function (data) {
                if (data.length == 0) {
                    $("#service").hide();
                    return;
                }
                $("#service").show();
                $("#service_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    var sname = s.serviceName;
                    if (sname.length > 6) {
                        sname = sname.substr(0, 5) + "...";
                    }
                    var result = "<li><a title='" + s.serviceName + "' href='javaScript:;' data-service='" + s.serviceId + "'>" + sname + "</a></li>";
                    if(i>6){
                        result = "<li style='display: none;' class='li_hide'><a title='" + s.serviceName + "' href='javaScript:;' data-service='" + s.serviceId + "'>" + sname + "</a></li>";
                    }

                    $("#service_ul").append(result);
                }

                if(data.length < 8){
                    $('.ser_more').hide();
                }else{
                    $('.ser_more').show();
                    $('.ser_more').click(function(){
                        if($.trim($(this).html()) == '更多'){
                            $("#service_ul").find('.li_hide').show();
                            $(this).html('收起');
                        }else{
                            $("#service_ul").find('.li_hide').hide();
                            $(this).html('更多');
                        }
                    });
                }

                $("#service").find("a").click(function () {
                    $("#service").find("a").removeClass("checked");
                    $(this).addClass("checked");
                    hotelList.refreshList();
                });
            },
            error: function () {
                $("#service_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
            }
        });

    }
    this.getBrands = function () {
        $.ajax({
            url: "/lvxbang/hotel/listHotelBrand.jhtml",
            type: "post",
            dataType: "json",
            data: {
                'cityId': $("#city").val()
            },
            success: function (data) {
                if (data.length == 0) {
                    $("#brand").hide();
                    return;
                }
                $("#brand").show();
                $("#brand_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    var sname = s.brandName;
                    if (sname.length > 6) {
                        sname = sname.substr(0, 5) + "...";
                    }
                    var result = "<li><a title='" + s.brandName + "' href='javaScript:;' data-brand='" + s.brandId + "'>" + sname + "</a></li>";

                    if(i>6){
                        result = "<li style='display: none;' class='li_hide'><a title='" + s.brandName + "' href='javaScript:;' data-brand='" + s.brandId + "'>" + sname + "</a></li>";
                    }

                    $("#brand_ul").append(result);
                }

                if(data.length < 8){
                    $('.bra_more').hide();
                }else{
                    $('.bra_more').show();
                    $('.bra_more').click(function(){
                        if($.trim($(this).html()) == '更多'){
                            $("#brand_ul").find('.li_hide').show();
                            $(this).html('收起');
                        }else{
                            $("#brand_ul").find('.li_hide').hide();
                            $(this).html('更多');
                        }
                    });
                }


                $("#brand").find("a").click(function () {
                    $("#brand").find("a").removeClass("checked");
                    $(this).addClass("checked");
                    hotelList.refreshList();
                });
            },
            error: function () {
                $("#brand_ul").html(' <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>');
            }
        });

    }
    this.bindChange = function () {
        $(".hotels_list").delegate(".change-hotel", "click", function () {
            $("#change-hotel-form").find(".code").val($(this).data("id") + "," + $("#enterDate").val() + "," + $("#leaveDate").val());
            $("#change-hotel-form").submit();
            $("#list").empty();
            loadingBegin();
        })
    }

}
$(document).ready(function () {
    var priceIndex = parseInt($("#hotelPrice").val());
    $(".select_div.price li").each(function () {
        if ($(this).index() == priceIndex) {
            $(this).parent().find(".checked").removeClass("checked");
            $(this).find("a").addClass("checked");
        }
    });

    if (!isNull($("#coreScenic").val())) {
        $("#order").find(".checked").removeClass("checked");
        $("#distance").addClass("checked").show();
    }

    //确定按钮
    $(".custom .input").click(function () {
        $(this).nextAll(".fix").fadeIn(500);
    });

    // //位    置
    // $(".opt").click(function () {
    //     var myStaute = $(this).attr("data-staute");
    //     if (!myStaute) {
    //         $(".opt").removeAttr("data-staute");
    //         $(".opt").removeClass("checked").height("30px");
    //         $(".opt_p").hide();
    //         $(".opt_p", this).show();
    //         $(this).addClass("checked").height("73px");
    //         $(this).attr("data-staute", "1");
    //     } else {
    //         $(".opt_p", this).hide();
    //         $(this).removeClass("checked").height("30px");
    //         $(this).removeAttr("data-staute");
    //     }
    // });

    //左右滚动
    $(function () {
        var $con = $('#bacSlideBox2');
        $con.css({width: 10000000});
        $('.recommend .prev2').click(function () {
            var $last = $('>:last-child', $con);
            $con.prepend($last);
            $last.css({marginLeft: -$last.width()});
            var $it = $('>:first-child', $con);
            $it.animate({marginLeft: 0}, 600);
        });
        $('.recommend .next2').click(function () {
            var $it = $('>:first-child', $con);
            $it.animate({marginLeft: -$it.width()}, 600, function () {
                $con.append($it);
                $it.css({marginLeft: 0});
            });
        });
    });

    var star = $("#star").val();
    $(".select_div.star a").each(function () {
        if ($(this).attr("data-value") == star) {
            $(this).closest("ul").find(".checked").removeClass("checked");
            $(this).addClass("checked");
        }
    });
    initMap();

    var hotelList = new HotelList();

    $(".select_div").each(function () {
        var panel = $(this);
        panel.find("a").click(function () {
            panel.find("a").removeClass("checked");
            $(this).addClass("checked");
            hotelList.refreshList();
        })
    });

    hotelList.init();
    hotelList.bindChange();
    hotelList.getRegions();
    hotelList.getBrands();
    hotelList.getServices();
    $("#search-button").click(function () {

        var dest = $.trim($("#destination").val());

        if (!dest) {
            $(".hotel_city_panel").children(".Addmore").show();
            return;
        }

        var enterDate = $.trim($("#enterDate").val());
        if (!enterDate) {
            $("#enterDate").focus();
            return;
        }
        var leaveDate = $.trim($("#leaveDate").val());
        if (!leaveDate) {
            $("#leaveDate").focus();
            return;
        }

        hotelList.getRegions();
        hotelList.getBrands();
        hotelList.getServices();
        hotelList.refreshList();
    });

    $(".search-button").click(function () {


        var dest = $.trim($("#destination").val());

        if (!dest) {
            $(".hotel_city_panel").children(".Addmore").show();
            return;
        }

        var enterDate = $.trim($("#enterDate").val());
        if (!enterDate) {
            $("#enterDate").focus();
            return;
        }
        var leaveDate = $.trim($("#leaveDate").val());
        if (!leaveDate) {
            $("#leaveDate").focus();
            return;
        }
        var priceMin = Number($('#price-min').val());
        var priceMax = Number($('#price-max').val());
        if (isNaN(priceMin) || isNaN(priceMax)) {
            promptWarn("价格输入不正确", 1000);
            return;
        }
        if (priceMax <= priceMin) {
            $('#price-min').val(priceMin);
            $('#price-max').val(priceMax);
            promptWarn("起始价格应该小于结束价格", 1000);
            return;
        }
        $(".select_div.price").find("li a.checked").removeClass('checked');
        $(".select_div.price").find("li a").eq(0).addClass('checked');
        hotelList.getRegions();
        hotelList.getBrands();
        hotelList.getServices();
        hotelList.refreshList();
    });

    CitySelector.withOption({
        selectFn: function () {
            $("#destination").val($.trim($(this).text()));
            $("#change-hotel-form").find(".city").val($(this).data("id"));
            $(".Addmore").hide();
        }
    });

    $("#order").on("click", "li", function () {
        orderClick(this);
        hotelList.refreshList(1);
    });


    $('#dituContent').click(function () {

    });
});

$(function () {


    //推介酒店换页时间
    $('.prev2').click(function () {
        var n = Number($('#count').html());
        if (n == 1) {
            n = 3;
        } else {
            n = n - 1;
        }
        $('#count').html(n);
    });

    $('.next2').click(function () {
        var n = Number($('#count').html());
        if (n == 3) {
            n = 1;
        } else {
            n = n + 1;
        }
        $('#count').html(n);
    });


    //栏目加浮动
    var navbar = function () {
        var navbar_top = $(window).scrollTop();
        var pager_top = $(".m-pager").offset().top;
        var height = $("#nav").height();
        var listheight = $("#list").offset().top;
        var dituHeight = $("#di_tu").height();
        var fixMax = navbar_top + height + dituHeight;
        var height2 = pager_top - dituHeight - 50;
        if (navbar_top >= listheight) {
            if (fixMax < pager_top) {
                $('#di_tu').css("position", "fixed").css("top", height + "px").css("margin-left", "22px");
            } else {
                $('#di_tu').css("position", "absolute").css("top", height2 + "px").css("margin-left", "22px");
            }
        } else {
            $('#di_tu').css("position", "").css("top", "").css("margin-left", "");
        }
    };
    $(window).bind("scroll", navbar);

    //小图标点击事件添加（例如日历控件）
    $('.ico').click(
        function () {
            $(this).next().focus();
            $(this).next().click();
        }
    );

    $(".enterDate").change(function () {
        var lDate = new Date(Date.parse($('#enterDate').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#leaveDate').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#leaveDate').val(lDate.format("yyyy-MM-dd"));
        }
        $('#leaveDate').focus().click();
    });

    $(".Addmore_p,.Addmore_d").remove();

    var hotelList = new HotelList();
    $('.n_select_d input').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            hotelList.refreshList();
        }
    });

    window.onbeforeunload = function () {
        delCookie("date");
    };
});
function toLargeMap() {
    $('#areaName').val($('#destination').val());
    $('#cityId').val($('#city').val());
    $('#startDate').val($('#enterDate').val());
    $('#endDate').val($('#leaveDate').val());
    $("#name").val($("#keyword").val());
    $("#hotelStar").val($(".select_div.star").find(".checked").data("value"));
    $("#hotelPrice").val($(".select_div.price").find(".checked").parent().index());
    $('#toLargeMap').submit();
}

//查询不同城市时同时推介该城市的酒店
function recommendHotel(datas) {
    datas.pageSize = 9;
    datas['searchRequest.orderColumn'] = "productScore";

    $.post("/lvxbang/hotel/listHotel.jhtml", datas, function (result) {
        //console.log(result);
        $('.recommendHotel1').empty();
        $('.recommendHotel2').empty();
        $('.recommendHotel3').empty();
        var i = 1;
        $.each(result, function (index, data) {
            data.price = Math.floor(data.price);
            var result = template("tpl-recommend-hotel-item", data);
            if (i <= 3) {
                $('.recommendHotel1').append(result);
            }
            if (i > 3 && i <= 6) {
                $('.recommendHotel2').append(result);
            }
            if (i > 6 && i <= 9) {
                $('.recommendHotel3').append(result);
            }
            i++;
        });
    }, "json");
}