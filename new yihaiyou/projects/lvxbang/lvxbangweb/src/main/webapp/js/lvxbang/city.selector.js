var CitySelector = {

    panelId: '#destination',

    history: [],

    init: function () {
        CitySelector.initSelector();
        CitySelector.initSearcher();
        CitySelector.readCookie();
        //CitySelector.initFloatCity();
    },

    initFloatCity: function () {
        try {
            if (FloatEditor.getPlanData() && FloatEditor.getPlanData().city) {
                $("#des_place").find(".city-selector-button").each(function () {
                    if (FloatEditor.getPlanData().city[$(this).data("id")]) {
                        $(this).click();
                    }
                })
            }
        } catch(e) {
        }

    },

    initSelector: function () {
        $(".add_more_city_button").click(function () {
            var url = window.location.pathname;
            if(url.indexOf("line/list.jhtml") >=0){
                $('#citySearch').parent().next().hide();
                $('#citySearch').parent().hide();
            }
            $(".Addmore").show();
        });
        $(".addMore_input").keyup(function () {
            if ($.trim($(this).val()).length > 0) {
                $("#dest_addmore_dl").hide();
            } else {
                $("#dest_addmore_dl").show();
            }
        });
        //首页目的地选项卡冲突事件解绑
        $(".Addmore_dl dt li").unbind("click").click(function () {
            $(this).addClass("checked").siblings().removeClass("checked");
        });

        $(".add_more_tab").find("a").click(function () {
            $(".add_more_tab").find("a").removeClass("checked");
            $(this).addClass("checked");
            var panels = $("#des_place").find("dd");
            var current = $(this).data("label");
            panels.hide();
            if (current == "hot") {
                panels.each(function () {
                    if ($(this).data("label") == "hot") {
                        $(this).show();
                    }
                })
            } else {
                var regex = new RegExp("[" + current + "]{1}", "i");
                panels.each(function () {
                    if ($(this).data("label").length == 1 && regex.test($(this).data("label"))) {
                        $(this).show();
                    }
                })
            }
        });
        $("#des_place").find(".city-selector-button").click(function () {
            if (CitySelector.selectFn == null) {
                CitySelector.defaultSelectFn.call(this);
            } else {
                CitySelector.selectFn.call(this);
            }
            if (CitySelector.selectedFn != null) {
                CitySelector.selectedFn.call(this);
            }
            CitySelector.renderCity();
            $(".Addmore").hide();
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
        $(".Addmore_shadow").click(function () {
            $(".Addmore").hide();
        });
        CitySelector.renderCity();
        $(".add_more_tab").find("a").eq(0).click();

    },

    renderCity: function () {
        $(CitySelector.panelId).find(".checkbox").each(function () {
            var cityId = $(this).data("id");
            $("#des_place").find(".city-selector-button").each(function () {
                if ($(this).data("id") == cityId) {
                    $(this).parent().addClass("checked");
                }
            })
        })
    },

    defaultSelectFn: function () {
        var cityId = $(this).data("id");
        var added = false;
        $(CitySelector.panelId).find(".checkbox").each(function () {
            if ($(this).data("id") == cityId) {
                added = true;
            }
        });
        if (added) {
            return;
        }
        var data = {};
        data.id = cityId;
        data.name = $.trim($(this).text());
        var url = window.location.pathname;
        if(url.indexOf("line/list.jhtml") >=0){
            $('#destination').val(data.name);
            $('#destination').attr('data-cityId',cityId);
        }else{
          $(".add_more_city_button").before(template("tpl-city-radio", data));
        }
    },

    initSearcher: function () {
        var category = $(".Addmore");
        var cityList = $("#des_place").find(".city-selector-button");
        $("#citySearch").keyup(function (e) {
            if (e.keyCode == 38 || e.keyCode == 40) {	// 38向上，40向下
                return;
            }
            var keyword = $.trim($(this).val());
            if (keyword.length == 0) {
                return;
            }
            var regex = /[a-zA-Z]+/;
            if (regex.test(keyword)) {
                return;
            }
            if (e.keyCode == 13) {	// 回车时判断是否是头部搜索框，是直接跳转
                if (category.find(".suggest-item.checked").length>0) {
                    category.find(".suggest-item.checked").eq(0).click();
                    return;
                }
                if (category.find(".suggest-item").length>0) {
                    category.find(".suggest-item").eq(0).click();
                }
            }
            $.post("/lvxbang/destination/getSeachAreaList.jhtml", {name: keyword}, function (result) {
                if (result.length > 0) {
                    var html = "";
                    $.each(result, function (i, data) {
                        cityList.each(function () {
                            if ($(this).data("id") == data.id) {
                                data.key = data.name.replace(keyword, "<strong>"+keyword+"</strong>");
                                html += template("tpl-suggest-item", data);
                            }
                        });
                    });
                    category.find(".categories_div ul").html("").append(html);
                    category.find(".categories_div").addClass("checked");
                    category.find(".suggest-item").click(function () {
                        var cityId = $(this).data("id");
                        var cityName = $(this).attr("data-text");
                        cityList.each(function () {
                            if ($(this).data("id") != cityId) {
                                return;
                            }
                            $(this).click();
                            CitySelector.saveCookie(cityId, cityName);
                            $("#citySearch").val("");
                        });
                    })
                }
            },"json");
            category.find(".categories_div").show();
        });
    },

    saveCookie: function (cityId, name) {
        var existIndex = null;
        $.each(CitySelector.history, function (index, history) {
            if (history.id == cityId) {
                existIndex = index;
                return false;
            }
        });
        if (existIndex != null) {
            CitySelector.history.splice(existIndex, 1);
        } else if (CitySelector.history.length>=5) {
            CitySelector.history.splice(0, 1);
        }
        CitySelector.history.push({id: cityId, name: name});
        setCookie("city-selector-history", encodeURI(JSON.stringify(CitySelector.history)));
        CitySelector.renderHistory();
    },

    readCookie: function () {
        var history = getCookie("city-selector-history");
        if (history) {
            CitySelector.history = JSON.parse(decodeURI(history));
        }
        CitySelector.renderHistory();
    },

    renderHistory: function () {
        $(".search-history").html("");
        $.each(CitySelector.history, function (index, history) {
            $(".search-history").append(template("tpl-city-history", history));
        });
        $(".Addmore_d").find(".city-selector-button").click(function () {
            if (CitySelector.selectFn == null) {
                CitySelector.defaultSelectFn.call(this);
            } else {
                CitySelector.selectFn.call(this);
            }
            if (CitySelector.selectedFn != null) {
                CitySelector.selectedFn.call(this);
            }
            CitySelector.renderCity();
            $(".Addmore").hide();
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
    },

    selectFn: null,

    selectedFn: null,

    searchFn: null,

    withOption: function (option) {
        CitySelector = $.extend(CitySelector, option);
    }


};
$(function () {
    if($("#tpl-city-history").length>0 || $("body").hasClass("HandDrawing")) {
        CitySelector.init();
    }
});
