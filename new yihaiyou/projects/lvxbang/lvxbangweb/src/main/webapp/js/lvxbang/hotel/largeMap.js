/**
 * Created by Sane on 16/1/12.
 */

var map;
function initMap() {
    // 百度地图API功能
    map = new BMap.Map("map_canvas", {enableMapClick: false});
    map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
    map.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
    map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
    map.enableScrollWheelZoom();                            //启用滚轮放大缩小
}
function cleanMap() {
    $('#hotelDetail').hide();
    map.clearOverlays();//清空原来的标注
}

function panTo() {
    var position = map.pointToOverlayPixel(new BMap.Point($(this).attr('lng'), $(this).attr('lat')));
    var p_point = map.overlayPixelToPoint({x: position.x, y: (position.y - 100)});
    // 根据所点击的景点，地图移至中心点
    map.panTo(p_point);
}
function detailClick() {
    panTo.call(this);
    showHotelDetail($(this).attr('id'));
}

//function focus() {
//    $('#hotelDetail').hide();
//    var lat = $(this).attr('lat');
//    var lng = $(this).attr('lng');
//    map.centerAndZoom(new BMap.Point(lng, lat), 14);
//    $('#' + $(this).attr('id') + ' div div').addClass('checked');
//}
//function unfocus() {
//    var detailId = $(this).attr('id');
//    $('#' + detailId + ' div div').removeClass('checked');
//}
function addHotelMarker(data, sum) {
    var p = sum;
    var htm1 =
        "<div class='posiA' >" +
        "<div class='posiR Pop_ups_posiR' id='" + data.id + "'  lng='" + data.lng + "' lat='" + data.lat + "'>" +
        "<div class=\"location location_" + p + "\">\n" +
        "  <label class=\"fl location_l\">\n" +
        "   <b class=\"ff_yh fs20 disB\" style='margin-top: 6px;width:34px'>" + sum + "</b><i class=\"round disB\"></i>\n" +
        "  </label>\n" +
        "  <div class=\"fl\">"
        + data.name + "<span class=\"ml15 ff_yh \">￥</span><b class=\"Orange\">" + data.price + "</b></div>\n" +
        //"  <i class=\"fl fl2\"></i>\n" +
        "</div>" +
        "</div>" +
        "</div>";
    var myRichMarker1 = new BMapLib.RichMarker(htm1, new BMap.Point(data.lng, data.lat), {
        "anchor": new BMap.Size(-15, -35)
    });
    map.addOverlay(myRichMarker1);


}
function getHTML(result, detailId) {
    var detail = result[0];
    var name = detail.name;
    var star = detail.star * 10;
    var addr = detail.address;
    var commentNum = detail.commentsNum;
    var score = Number((detail.score / 20).toFixed(1));
    ;
    var imagesLis = '';
    $.each(detail.images, function (i, image) {
        imagesLis += '<li><p class="img"><img src="' + image + '" /></p></li>\n';
    });
    //console.info(name + '\t' + addr + '\t' + commentNum + '\t' + score + '\n' + imagesLis);
    var html = "<div class=\"posiR Pop_ups_div\">\n" +
        "                \t<a href=\"javaScript:;\" class=\"close\"></a>\n" +
        "                    <a href=\"javaScript:;\" class=\"left\" style='display: none'></a>\n" +
        "                    <a href=\"javaScript:;\" class=\"right\" style='display: none'></a>\n" +
        "                \t<ul class=\"Pop_ups_ul\" num=\"1\" style='width: 1050px;'>\n" +
        imagesLis +
        "                     </ul>  \n" +
        "                     <div class=\"Pop_ups_nr\"> \n" +
        "                     \t<div class=\"Pop_ups_nr_div\">\n" +
        "                            <div class=\"Pop_ups_fl fl\">\n" +
        "                                <p class=\"name fs14 b\"><a href=\"/hotel_detail_" + detailId + ".html\" target=\"_blank\"><b>" + name + "</b></a></p>\n" +
        "                                <p class=\"hstar cl mb15\"><i style=\"width: " + star + "px\"></i></p>\n" +
        "                                <p class=\"add\">地址：" + addr + "</p>\n" +
        "                            </div>\n" +
        "                            <div class=\"Pop_ups_fr fr textR\" id='scoreView'>\n" +
        "                                <p class=\"fs\"><b class=\"ff_yh\">" + score + "</b>/5分 </p>\n" +
        //"                                <a class=\"dp\" href=\"/hotel_detail_" + detailId + ".html#comment\" target=\"_blank\">" + commentNum + "人点评</a>\n" +
        "                            </div>\n" +
        "                            <p class=\"cl\"></p>\n" +
        "                        </div>\n" +
        "                        <div id='hotelPrices'></div>\n" +
        "                        <a href=\"/hotel_detail_" + detailId + ".html\" target=\"_blank\"" +
        "                           class=\"but b oval4\">查看更多房型</a>\n" +
        "                    </divdiv> \n" +
        "                </div>";
    return html;
}
function showHotelDetail(detailId) {
    var data = {
        hotelId: detailId
    };
    $.post("/lvxbang/hotel/detailForMap.jhtml", data, function (result) {
        var html = getHTML(result, detailId);
        $('#hotelDetail').html(html);
        //左右滚动
        $(function () {
            var Pop_ups_ul = $(".Pop_ups_ul li").length;
            var width2 = Pop_ups_ul * 350;
            $(".Pop_ups_ul").css("width", width2);
            var ta_list_div_d2 = $(".ta_list_div_d").width();
            var nowPage2 = $(".Pop_ups_ul").attr("num");
            $('.Pop_ups_div').mouseover(function () {
                $('.Pop_ups_div .left').show()
                $('.Pop_ups_div .right').show()
            });
            $('.Pop_ups_div').mouseout(function () {
                $('.Pop_ups_div .left').hide();
                $('.Pop_ups_div .right').hide();
            });
            $('.Pop_ups_div .left').click(function () {
                if (nowPage2 > 1) {
                    $(".Pop_ups_ul").animate({"margin-left": "+=350px"}, 500);
                    nowPage2--;
                }
            });
            $('.Pop_ups_div .right').click(function () {
                var zong2 = 350 * nowPage2;
                if (width2 > ta_list_div_d2 && zong2 < width2) {
                    $(".Pop_ups_ul").animate({"margin-left": "-=350px"}, 500);
                    nowPage2++;
                }
                $(".Pop_ups_ul").attr("num", nowPage2);
            });
        });
        $(".Pop_ups_div .close").click(function () {
            $(".Pop_ups").hide(500);
        });
        $('#hotelDetail').show();
        var startDate = new Date();
        var endDate = new Date(startDate / 1 + 86400000);
        var priceData = {
            hotelId: detailId,
            priceStartDate: startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + startDate.getDate(),
            priceEndDate: endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate(),
        };
        $.post("/lvxbang/hotel/listPrice.jhtml", priceData, function (result) {
            if (result.success) {
                var price = result.hotelPrices[0];
                var name = price.name;
                if (name.length > 8) {
                    name = name.substring(0, 8);
                }
                //console.info(price);
                //<a href=\"/lvxbang/hotel/detail.jhtml?hotelId=" + detailId + "\">
                var priveHtml = "<div class=\"pript\">\n" +
                    "<span class=\"fl\">" + name + "</span>\n" +
                    "<a href=\"/hotel_detail_" + detailId + ".html\" target=\"_blank\" class=\"pript_a fr oval4 b\">预订</a>\n" +
                    "<p class=\"fr b\"><b class=\"ff_yh\">￥</b><span class=\"fs14\">" + price.price + "</span></p>\n" +
                    "</div>\n";
                $('#hotelPrices').append(priveHtml);
            }
        },"json");
    },"json");
}
function HotelList() {
    var hotelList = this;
    this.pager;
    this.data = {};
    this.init = function () {
        hotelList.pager = new Pager(
            {
                pageSize: 8,
                container: ".pager",
                countUrl: "/lvxbang/hotel/countHotel.jhtml",
                searchData: {},
                pageText: '',
                prevButton: '<a class="prev-page" href="javascript:void(0)">&lt;-</a>',
                nextButton: '<a class="next-page" href="javascript:void(0)">-&gt;</a>',
                lastButton: '',
                firstButton: '',
                button: '<a class="numerated-page {class}" href="javascript:void(0)">{page}</a>',
                pageRenderFn: function (pageNo, pageSize) {
                    cleanMap();
                    hotelList.data.pageNo = pageNo;
                    hotelList.data.pageSize = pageSize;
                    $.post("/lvxbang/hotel/listHotel.jhtml", hotelList.data, function (result) {
                        var date = {};
                        date.startDate = $("#startDate").val();
                        date.endDate = $('#endDate').val();
                        setUnCodedCookie("date", JSON.stringify(date));

                        var html = "";
                        var sum = 1;
                        if (result.length == 0) {
                            $(".hotels_list").html(html);
                            return;
                        }
                        map.centerAndZoom(new BMap.Point(result[0].lng, result[0].lat), 14);
                        $.each(result, function (i, data) {
                            data.sum = sum;
                            if (sum == 1)
                                data.num = ' one';
                            else if (sum == 2)
                                data.num = ' two';
                            data.score0 = Number((data.score / 20).toFixed(1));
                            html += template("tpl-hotel-list-item", data);
                            addHotelMarker(data, sum++);
                        });
                        $(".Pop_ups_posiR").click(function () {
                            panTo.call(this);
                            var detailId = $(this).attr('id');
                            showHotelDetail(detailId);
                        });
                        $(".hotels_list").html(html).find("img").lazyload({
                            effect: "fadeIn"
                        });
                        $(".hotels_list li").click(detailClick);
                        $(".location").hover(function () {
                            $(this).find(".location_l").css("background-position-y", "0px");
                            $(this).find("div").css("background-color", "#e7eaf1");
                        }, function () {
                            if ($(this).hasClass("location_1")) {
                                $(this).find(".location_l").css("background-position-y", "-165px");
                            } else if ($(this).hasClass("location_2")) {
                                $(this).find(".location_l").css("background-position-y", "-112px");
                            } else {
                                $(this).find(".location_l").css("background-position-y", "-223px");
                            }
                            $(this).find("div").css("background-color", "#fff");
                        });
                        //$(".hotels_list li").mouseover(focus);
                        //$(".hotels_list li").mouseout(unfocus);

                        //列表移入样式修改
                        $('.largeMapLi').hover(function(){
                            $(this).css("background",'#e7eaf1');
                        },function(){
                            $(this).css("background",'');
                        });
                    },"json");
                    //hotelList.refreshList();
                }
            }
        );
        hotelList.refreshList();
    };
    this.refreshList = function (type) {
        //如果从机+酒过来的酒店
        var city = $("#destination").val();
        var cityId = getCityId(city);
        if (cityId == 0) {
            return;
        }
        $("#destination").attr('data-areaid', cityId);
        cleanMap();
        if (cityId != undefined) {
            var searchHistoryTxt = $("#searchHistoryTxt");
            var searchHistory = JSON.parse(getCookie("hotel_map_history"));
            if (searchHistoryTxt.html().indexOf(cityId) == -1) {
                var data = {
                    id: cityId,
                    name: city
                };
                var as = new Array(data);
                searchHistory = as.concat(searchHistory);
                if (searchHistory.length > 5) {
                    $("#searchHistoryTxt a:first").remove();
                    searchHistory = searchHistory.slice(0, 5);
                }
                setCookie("hotel_map_history", JSON.stringify(searchHistory));
                var span = "<a id='" + cityId + "' name='" + city + "' href='#'><span>" + city + "</span></a> ";
                searchHistoryTxt.append(span);
                $('#' + data.id).click(function () {
                    $('.categories_Addmore2').hide();
                    $('#destination').val($(this).attr('name'));
                    $('#destination').attr('data-areaid', $(this).attr('id'));
                });
            }
        }

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
        hotelList.data['searchRequest.name'] = name;
        if (!isNull(name) && type != 1) {
            $("#order").find(".checked").removeClass("checked");
        } else {
            if ($("#order").find(".checked").length < 1) {
                $("#order").find("li").eq(0).addClass("checked");
            }
            hotelList.data['searchRequest.orderColumn'] = $("#order").find(".checked").attr("orderColumn");
            hotelList.data['searchRequest.orderType'] = $("#order").find(".checked").attr("orderType");
        }
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        hotelList.data['searchRequest.startDate'] = startDate;
        hotelList.data['searchRequest.endDate'] = endDate;
        hotelList.data['searchRequest.star'] = $(".select_div .star").find(".checked").data("value");
        var price = $(".select_div .price").find(".checked").data("price");
        if (price != undefined) {
            price = price.toString().split(",");
            if (price.length == 1) {
                if (parseInt(price[0]) != 0) {
                    hotelList.data['searchRequest.priceRange[0]'] = price[0];
                }
            }
            if (price.length == 2) {
                hotelList.data['searchRequest.priceRange[0]'] = price[0];
                hotelList.data['searchRequest.priceRange[1]'] = price[1];
            }
        }

        hotelList.pager.init(hotelList.data);
    };
}


$(document).ready(function () {
    initMap();

    var priceIndex = parseInt($("#hotelPrice").val());
    $(".select_div .price a").each(function () {
        if ($(this).parent().index() - 1 == priceIndex) {
            $(this).parents(".price").find(".checked").removeClass("checked");
            $(this).addClass("checked");
            $(this).css("color",'red');
            $(this).parents('li').find('.leixing').html($(this).html());
        }
    });

    var star = parseInt($("#f_star").val());
    $(".select_div .star a").each(function () {
        if ($(this).data("value") == star) {
            $(this).parents(".star").find(".checked").removeClass("checked");
            $(this).addClass("checked");
            $(this).css("color",'red');
            $(this).parents('li').find('.leixing').html($(this).html());
        }
    });

    if (!isNull($("#coreScenic").val())) {
        $("#order").find(".checked").removeClass("checked");
        $("#distance").addClass("checked").show();
        $("#order li").each(function () {
            $(this).css("width", "33%");
        })
    }

    $("img").lazyload({
        effect: "fadeIn"
    });
    $(".mailTab li").click(function () {
        //重复点击时,更换排序,并刷新结果;切换点击时,切换页面,不更换排序;
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).closest("div").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
    })
    //搜索框
    $(".categories .input").click(function () {
        $(this).next(".categories_div").show();
        $(".categories_dl").hide();
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
    //$(".HandDrawing_ul li").click(function () {
    //    $(".categories_dl").hide();
    //    $(this).children(".categories_dl").show();
    //    $(".categories_div").hide();
    //});
    var scrollTop = $(document).height();
    $(".main").height(scrollTop - 80);

    $(".Pop_ups_div .but").click(function () {
        $(this).addClass("checked").html("<i></i>已加入线路");
    });


    $(".categories_dl").each(function () {
        var panel = $(this);
        panel.find("a").click(function () {
            panel.find("a").removeClass("checked");
            panel.find("a").css('color','#666');
            $(this).addClass("checked");
            $(this).css("color",'red');
        })
    });
    var hotelList = new HotelList();
    hotelList.init();

    $(".categories_dl dd a").click(function () {
        //$(".categories_dl").hide();
        $(this).parents('li').find('.leixing').html($(this).html());
        hotelList.refreshList();
    });

    $("#search-button").click(function () {
        hotelList.refreshList();
    });

    CitySelector.withOption({
        selectFn: function () {
            $("#destination").val($.trim($(this).text()));
            $(".Addmore").hide();
        }
    });

    $("#order li").unbind("click");
    $("#order").on("click", "li", function () {
        if (!$(this).hasClass("checked")) {
            $("#order").find(".checked").removeClass("checked");
            $(this).addClass("checked");
        } else {
            var orderType = $(this).attr('orderType');
            if ("desc" == orderType) {
                $(this).attr('orderType', 'asc');
                $(this).addClass('OrderAsc');
            } else {
                $(this).attr('orderType', 'desc');
                $(this).removeClass('OrderAsc');
            }
        }
        hotelList.refreshList(1);
    });

    $("#goToList").click(function () {
        $('#f_cityId').val($("#destination").attr('data-areaid'));
        $('#f_cities').val($('#destination').val());
        $('#f_startDate').val($('#startDate').val());
        $('#f_star').val($(".select_div .star").find(".checked").data("value"));
        $('#f_endDate').val($('#endDate').val());
        $('#f_name').val($('#keyword').val());
        $("#hotelPrice").val($(".select_div .price").find(".checked").parent().index() - 1);
        $('#goToListForm').submit();
    })

    //搜索历史
    var searchHistoryTxt = $("#searchHistoryTxt");
    var searchHistory = JSON.parse(getCookie("hotel_map_history"));
    //window.console.info('searchHistory:' + searchHistory);
    if (searchHistory != null && searchHistory.length > 0) {
        //window.console.info('searchHistoryTxt:' + searchHistoryTxt);
        for (var i = 0; i < searchHistory.length; i++) {
            var data = searchHistory[i];
            if (data == null)
                continue;
            if (searchHistoryTxt.html().indexOf(data.id) == -1) {
                var span = "<a id='" + data.id + "' name='" + data.name + "' href='#'><span>" + data.name + "</span></a> ";
                searchHistoryTxt.append(span);
                $('#' + data.id).click(function () {
                    $('.categories_Addmore2').hide();
                    $('#destination').val($(this).attr('name'));
                    $('#destination').attr('data-areaid', $(this).attr('id'));
                    //$('#search-button').click();
                });
            }
        }
    }

    //搜索条件和下拉事件
    $('.HandDrawing_ul li ').click(function(){
        if($(this).find('.select-i').next().css('display')=='block'){
            $(this).find('.select-i').css('background-position-y','-582px');
            $(this).find('.select-i').next().css('display','none');
        }else{
            $(this).find('.select-i').css('background-position-y','-596px');
            $(this).find('.select-i').next().css('display','block');
        }
    });
    //图标点击后事件
    $('.mudidi').click(function(){
        $(this).next().focus().click();
    });
    $('.rili').click(function(){
        $(this).next().click();
        $(this).next().focus();
    });
    //$('.HandDrawing_ul li').click(function(){
    //    $(this).find("i").click();
    //});



    $("#startDate").change(function(){
        var lDate = new Date(Date.parse($('#startDate').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#endDate').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#endDate').val(lDate.format("yyyy-MM-dd"));
        }
        $('#endDate').click().focus();
    });

    $('.n_select_d input').bind('keypress',function(event) {
        if (event.keyCode == "13") {
            hotelList.refreshList();
        }
    });

    window.onbeforeunload = function() {
        delCookie("date");
    };

    //$(".Addmore_nr a").click(function () {
    //    var id = $(this).attr('data-id');
    //    if (id == undefined)
    //        return;
    //    if (searchHistoryTxt.html().indexOf(id) > -1)
    //        return;
    //    var data = {
    //        id: id,
    //        name: $(this).attr('data-name')
    //    };
    //    var span = "<a id='" + data.id + "' href='#'><span>" + data.name + "</span></a> ";
    //    searchHistoryTxt.append(span);
    //    $('#' + data.id).click(function () {
    //        $('.categories_Addmore2').hide();
    //        $('#destination').val($(this).attr('name'));
    //        $('#destination').attr('data-areaid', $(this).attr('id'));
    //        //$('#search-button').click();
    //    });
    //    var as = new Array(data);
    //    //console.info('as:' + as)
    //    searchHistory = as.concat(searchHistory);
    //    if (searchHistory.length > 5) {
    //        searchHistory = searchHistory.slice(0, 5);
    //    }
    //    setCookie("hotel_map_history", JSON.stringify(searchHistory));
    //    //console.info('set cookie:' + JSON.stringify(searchHistory))
    //});

});
