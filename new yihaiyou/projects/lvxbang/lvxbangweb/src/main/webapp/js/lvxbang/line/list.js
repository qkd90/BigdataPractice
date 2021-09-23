var LineList = {
    pager: null,
    cityId: Number($("#cityId").val()),
    productAttr: null,
    type: null,
    orderColumn: ["shareNum", "orderNum", "price"],
    init: function () {
        var options={
            pageSize: 20,
            countUrl: "/lvxbang/line/count.jhtml",
            resultCountFn: function(result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                $('#lineList').empty();
                //$("#loading").show();
                //scroll(0, 0);
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url:"/lvxbang/line/getLineList.jhtml",
                    type:"post",
                    dataType:"json",
                    data:data,
                    success:function(data){
                        $('#lineList').empty();
                        //$("#loading").hide();
                        $.each(data, function(i, line) {
                            var length = 6;
                            var more = true;
                            if (line.startDays.length < 6) {
                                length = line.startDays.length;
                                more = false;
                            }
                            var startDays = "";
                            for (var j = 0; j < length; j++) {
                                if (j > 0) {
                                    startDays += ",";
                                }
                                var date = new Date(line.startDays[j]);
                                startDays += date.format("MM月dd日");
                            }
                            if (line.muiltStartCity[0] > 1) {
                                line.muiltCityStr = "多城市出发";
                            } else {
                                line.muiltCityStr = line.startCity + "出发";
                            }
                            line.startDays = startDays;
                            line.more = more;
                            var result = template("tpl-line-list-item", line);
                            $('#lineList').append(result);
                        });
                        collect(".favorite", false);
                        //图片懒加载
                        $("img").lazyload({
                            effect: "fadeIn"
                        });
                    },
                    error:function(){
                        window.console.log("error");
                    }
                });
            }
        };
        LineList.pager = new Pager(options);
        LineList.initStartCity();
        LineList.initPage();
        LineList.initTese();
        LineList.changeCity();
        LineList.citySelector();
        LineList.ipCity();
        LineList.history();
        LineList.interest();
        LineList.bindEvent();
        CitySelector.withOption({
            selectFn: function () {
                var searchHistoryCookie;
                if ($(this).parent().hasClass("follower_city")) {
                    searchHistoryCookie = "internal_search_history";
                } else {
                    searchHistoryCookie = "abroad_search_history";
                }
                var searchHistory = JSON.parse(getCookie(searchHistoryCookie));
                searchHistory = SearcherBtn.unique([$(this).text()].concat(searchHistory));
                if (searchHistory.length > 5) {
                    searchHistory = searchHistory.slice(0, 5);
                }
                setCookie(searchHistoryCookie, JSON.stringify(searchHistory));
                location.href = "/" + LineList.type + "_" + $(this).data("id") + ".html";
            }
        });
        LineList.initDate();
    },
    lineList: function () {
        var startCityId = $("#startCity a.cur_city").data("id");
        var priceRange = new Array();
        priceRange[0] = isNaN($("#first_price").val()) ? 0 : Number($("#first_price").val());
        priceRange[1] = isNaN($("#second_price").val()) ? 0 : Number($("#second_price").val());
        var lineDay = $('#lineDay .oneLine a.cur_city').data('day');
        var startDate = $("#begin_date").val();
        var endDate = $("#end_date").val();
        var traffic = $("#traffic .oneLine a.cur_city").data("traffic");
        var theme = $("#tese a.cur_city").text();
        if (theme == "全部") {
            theme = "";
        }
        var sort = $("#sort li.cur");
        var orderColumn = LineList.orderColumn[sort.index() - 1];
        var orderType = sort.find("a").hasClass("dbsort_asc") ? "asc" : "desc";
        var search = {
            'lineSearchRequest.startCityId': startCityId,
            'lineSearchRequest.productAttr': LineList.productAttr,
            'lineSearchRequest.priceRange[0]':priceRange[0],
            'lineSearchRequest.priceRange[1]':priceRange[1],
            'lineSearchRequest.orderColumn':orderColumn,
            'lineSearchRequest.orderType':orderType,
            'lineSearchRequest.lineDay':lineDay,
            'lineSearchRequest.trafficType': traffic,
            'lineSearchRequest.cityId': LineList.cityId,
            'lineSearchRequest.startDate': startDate,
            'lineSearchRequest.endDate': endDate,
            'lineSearchRequest.theme': theme
        };
        LineList.pager.init(search);
    },
    hotCustomMade: function () {
        $.post("/lvxbang/line/hotCustomMade.jhtml", {
            cityId: LineList.cityId
        }, function (data) {
            $("#hotLocal").empty();
            $.each(data, function (i, local) {
                var html = template("tpl-hot-customMade-item", local);
                $("#hotLocal").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    hotGroupTour: function () {
        $.post("/lvxbang/line/hotGroupTour.jhtml", {
            cityId: LineList.cityId
        }, function (data) {
            $("#hotLocal").empty();
            $.each(data, function (i, local) {
                var html = template("tpl-hot-groupTour-item", local);
                $("#hotLocal").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    hotScenic: function () {
        $.post("/lvxbang/line/hotScenic.jhtml", {
            cityId: LineList.cityId
        }, function (data) {
            $("#hotScenic").empty();
            $.each(data, function (i, scenic) {
                var html = template("tpl-hot-scenic-item", scenic);
                $("#hotScenic").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    hotHotel: function () {
        $.post("/lvxbang/line/hotHotel.jhtml", {
            cityId: LineList.cityId
        }, function (data) {
            $("#hotHotel").empty();
            $.each(data, function (i, hotel) {
                var html = template("tpl-hot-hotel-item", hotel);
                $("#hotHotel").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    bindEvent: function () {
        //搜索历史点击事件
        $("#topContainer .des_main").delegate(".history_city", "click", function () {
            $.post("/lvxbang/destination/getAreaIdsWithAbroad.jhtml", {"nameStr": $(this).text()}, function (data) {
                if (data.success) {
                    var cityId;
                    if (data.internal[0].length > 0) {
                        cityId = data.internal[0];
                    }
                    if (data.abroad[0].length > 0) {
                        cityId = data.abroad[0];
                    }
                    if (isNaN(cityId)) {
                        return;
                    }
                    $("#des_place .city-selector-button[data-id=" + cityId + "]").click();
                }
            });
        });
        //搜索条件行程天数
        $("#lineDay .oneLine a").click(function () {
            $("#lineDay .oneLine a.cur_city").removeClass("cur_city");
            $(this).addClass("cur_city");
            LineList.lineList();
        });
        //搜索条件出游时间
        $("#begin_date").change(function () {
            $("#end_date").focus();
        });
        $("#end_date").change(function () {
            LineList.lineList();
        });
        //搜索条件交通类型
        $("#traffic .oneLine a").click(function () {
            $("#traffic .oneLine a.cur_city").removeClass("cur_city");
            $(this).addClass("cur_city");
            LineList.lineList();
        });
        //排序
        $("#sort li[class!=default]").click(function () {
            $(this).addClass("cur").siblings().removeClass("cur");
            $("#sort li[class!=default] a").removeClass("crt").addClass("sort_option");
            $(this).find("a").removeClass("sort_option").addClass("crt");
            var a = $("#sort li[class!=default] a").eq(2);
            if (a.hasClass("crt")) {
                if (a.hasClass("dbsort")) {
                    a.removeClass("dbsort").addClass("dbsort_asc");
                } else if (a.hasClass("dbsort_asc")) {
                    a.removeClass("dbsort_asc").addClass("dbsort_desc");
                } else if (a.hasClass("dbsort_desc")) {
                    a.removeClass("dbsort_desc").addClass("dbsort_asc");
                }
            } else {
                a.attr("class", "sort_option dbsort");
            }
            LineList.lineList();
        });
        //价格区间
        $("#first_price, #second_price").click(function (e) {
            // 阻止冒泡
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            $(".rank_priceform").addClass("focus");
        });
        $(".rank_priceform .clearprice").click(function () {
            $("#first_price, #second_price").val("");
        });
        $("#confirm_btn").click(function () {
            LineList.lineList();
        });
        //机+酒面板
        $(".aside_tab .tabs li").click(function () {
            $(".aside_tab .tabs li.current, .aside_tab .tab_contents>li.current").removeClass("current");
            $(this).addClass("current");
            var index = $(this).index();
            $(".aside_tab .tab_contents>li").eq(index).addClass("current");
        });
        //交换出发到达城市
        $(".aside_tab .tab_contents .switch_btn").click(function () {
            var input = $(this).siblings(".sho").find(".portinput");
            var val = input.eq(0).val();
            input.eq(0).val(input.eq(1).val());
            input.eq(1).val(val);
        });
        //猜你喜欢面板
        $("#guesslike_history .tabs li").click(function () {
            $("#guesslike_history .tabs li.current").removeClass("current");
            $(this).addClass("current");
            var index = $(this).index();
            $("#guesslike_history .tab_contents_list").addClass("hide").eq(index).removeClass("hide");
        });

        $(document).click(function(e){
            var i=0;
            $(".categories_Addmore2").each(function(e){
                if($(this).css('display')=='block'){
                    i=e;
                }
            });

            e = window.event || e;
            var obj = e.srcElement || e.target;

            if(!$(obj).is(".categories_Addmore2:eq("+i+")") && !$(obj).is("input") && !$(obj).is("a")
                && !$(obj).is("div.Addmore_nr")) {
                //$("#keywords-area").hide();
                $('.categories_Addmore2').css('display','none')
            }
            $(".rank_priceform").removeClass("focus");
        });
    },
    hotLine: function () {
        $.post("/lvxbang/line/getLineList.jhtml", {
            "lineSearchRequest.cityId": LineList.cityId,
            "pageNo": 1,
            "pageSize": 5
        }, function (data) {
            $("#hotLineList").empty();
            $.each(data, function (i, line) {
                line.index = i + 1;
                var html = template("tpl-hot-line-item", line);
                $("#hotLineList").append(html);
            });
        });
    },
    history: function () {
        $.post("/lvxbang/line/history.jhtml", function (data) {
            $.each(data.data, function (i, line) {
                var html = template("tpl-interest-history-item", line);
                $("#history").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    interest: function () {
        $.post("/lvxbang/line/interest.jhtml", function (data) {
            $.each(data.data, function (i, line) {
                var html = template("tpl-interest-history-item", line);
                $("#interest").append(html);
            });
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
        });
    },
    changeCity: function () {
        LineList.lineList();
        LineList.hotLine();
        if ($("body").attr("myname") == "customMadeList") {
            LineList.hotGroupTour();
        } else {
            LineList.hotCustomMade();
        }
        LineList.hotScenic();
        LineList.hotHotel();

        $.post("/lvxbang/destination/areaInfo.jhtml", {
            id: LineList.cityId
        }, function (data) {
            data.city.tbAreaExtend = data.extend;
            var html = template("tpl-travel-raider-item", data.city);
            $(".travelraider").empty().append(html);
        });
    },
    citySelector: function () {
        $.post("/lvxbang/destination/citySelector.jhtml", function (data) {
            var html = template("tpl-city-selector-item", data);
            $(".categories_Addmore2").html(html);
            //首页目的地选项卡
            $(".Addmore_dl2 dt li").click(function () {
                $(this).addClass("checked").siblings().removeClass("checked");
                $(this).closest(".Addmore_dl").find("dd").eq($(this).index()).show().siblings("dd").hide();
            });
            //关闭
            $(".Addmore2 .close").click(function () {
                $(this).parent().hide();
            });
            // 点击城市面板点击事件
            $(".Addmore_dl2 dd .Addmore_nr").delegate("li", "click", function () {
                var $input = $(this).parents(".Addmore").hide().prev("input");
                $(this).parents(".Addmore_dl dd").find(".Addmore_nr li").removeClass("checked")
                $input.val($("a", this).text());
                $(this).addClass("checked");
                $input.attr("data-areaId", $(this).attr("data-id"));    // 赋值data-areaId
                $input.attr("data-portId", "");    // 赋值data-areaId
                $input.data("areaid", $(this).data("id"));    // 赋值data-areaId
                $input.change();
                if ($input.attr("id") == "transitCityName") {
                    $("#transitCityName-2").val($input.val());
                }
                $input.focus();
            });
        });
    },
    initDate: function () {
        $("#flight_departure_date, #d21, #d31, #begin_date").each(function () {
            if (isNull($(this).val())) {
                var date = new Date();
                date.setDate(date.getDate() + 1);
                $(this).val(date.format("yyyy-MM-dd"));
            }
        });
        if (isNull($("#d22").val())) {
            var date = new Date($("#d21").val());
            date.setDate(date.getDate() + 1);
            $("#d22").val(date.format("yyyy-MM-dd"));
        }
    },
    initPage: function () {
        var name = $("body").attr("myname");
        if (name == "groupTourList") {
            $("#topContainer .topnav .group_tour").addClass("cur");
            LineList.productAttr = "跟团游";
            LineList.type = "group_tour";
        } else if (name == "selfTourList") {
            $("#topContainer .topnav .self_tour").addClass("cur");
            LineList.productAttr = "自助游";
            LineList.type = "self_tour";
        } else if (name == "selfDriverList") {
            $("#topContainer .topnav .self_drive").addClass("cur");
            LineList.productAttr = "自驾游";
            LineList.type = "self_driver";
        } else if (name == "customMadeList") {
            $("#topContainer .topnav .custom_made").addClass("cur");
            LineList.productAttr = "精品定制";
            LineList.type = "custom_made";
        }
    },
    initTese: function () {
        $.post("/lvxbang/line/getLineLabelName.jhtml", function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#tese").append("<li class='filter_input'>" +
                    "<a href='javascript:void(0);'>" + data[i] + "</a>" +
                    "</li>");
            }
            //搜索条件线路特色
            $("#tese>li>a").click(function () {
                $("#tese>li>a").removeClass("cur_city");
                $(this).addClass("cur_city");
                LineList.lineList();
            });
        });
    },
    initStartCity: function () {
        $.post("/lvxbang/line/getLineStartCity.jhtml", function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#startCity").append("<li class='filter_input'>" +
                    "<a href='javascript:void(0);' data-id='" + data[i].id + "'>" + data[i].name + "</a>" +
                    "</li>");
            }
            if (data.length > 10) {
                $("#moreCity").show();
            }
            //搜索条件出发城市
            $("#startCity a").click(function() {
                $("#startCity a.cur_city").removeClass("cur_city");
                $(this).addClass("cur_city");
                LineList.lineList();
            });
            LineList.moreCity();
        });
    },
    ipCity: function () {
        $.post("/lvxbang/destination/getIpCity.jhtml", function (data) {
            $('#flight_origin, #train_origin').val(data.name);
        });
    },
    moreCity: function () {
        var startcity_text = $('#moreCity').html();
        var k = 0;
        $('#moreCity').click(function () {
            if (k == 0) {
                $('.startdd').removeClass('oneLine');
                $('.startdd').addClass('most_height');
                var dd_height = $('.startdd').height();
                $('.startdt').css('height', dd_height + 'px');
                $('#moreCity').html('收起');
                k = 1;
            } else {
                $('.startdd').removeClass('most_height');
                $('.startdd').addClass('oneLine');
                $('.startdt').css('height', '32px');
                $('#moreCity').html('更多');
                k = 0;
            }
        });
    }
};

$(document).ready(function () {
   LineList.init();
});