var SailboatList = {
    pager: null,
    activeObj: $(".li-active").attr("data-order-column"),
    init: function() {
        SailboatList.IE8Css();
        SailboatList.initJsp();
        SailboatList.createPager();
        SailboatList.sailboatList();     //查询游艇帆船列表数据
    },

    checkMore: function(target, id) {
        var flag = $(target).attr("data-flag");
        $("div[id^='ticket-category-']").slideUp('2000');
        if (flag == 0) {
            SailboatList.getTicketPriceList(1, 100, id);
            $(target).attr("data-flag", "1");
            $("#html-content-"+ id +"").html("隐藏部分票型");
            $(target).find("i").removeClass("down");
            $(target).find("i").addClass("up");
        } else {
            SailboatList.getTicketPriceList(1, 3, id);
            $(target).attr("data-flag", "0");
            $("#html-content-"+ id +"").html("展开全部票型");
            $(target).find("i").removeClass("up");
            $(target).find("i").addClass("down");
        }
    },

    getTicketPriceList: function(pageIndex, pageSize, id) {
        $.post("/yhypc/sailboat/getTicketPriceList.jhtml",
            {
                ticketId: id,
                pageIndex: pageIndex,
                pageSize: pageSize
            },
            function (data) {
                $("#ticket-list-"+ id +"").empty();
                if (data.success) {
                    var ticketPrices = data.ticketPrices;
                    $.each(ticketPrices, function(i, ticketPrice) {
                        template.config("escape", false);
                        ticketPrice['i'] = i;
                        var result = $(template("tpl-sailboat-list-pricetype-item", ticketPrice));
                        $("#ticket-list-"+ id +"").append(result);
                        result.data("ticketPriceInfo", ticketPrice);
                    });
                    if (Number($("#ticket-list-"+ id +"").attr("data-price-length")) <= 3) {
                        $("#ticket-list-unflod-"+ id +"").hide();
                    } else {
                        $("#ticket-list-unflod-"+ id +"").show();
                    }
                }
            }
            ,"json");
    },

    initJsp: function() {

        $(".select-sort").find("li").click(function() {
            if (SailboatList.activeObj == $(this).attr("data-order-column")) {
                if ($(this).attr("data-order-column") != "showOrder") {
                    if ($(this).attr("data-order-type") == "desc") {
                        $(this).attr("data-order-type", "asc");
                        $(this).find("span").removeClass("desc");
                        $(this).find("span").addClass("asc");
                    } else {
                        $(this).attr("data-order-type", "desc");
                        $(this).find("span").removeClass("asc");
                        $(this).find("span").addClass("desc");
                    }
                }
            }
            $(".select-sort").children().removeClass("li-active");
            $(this).addClass("li-active");
            SailboatList.activeObj = $(this).attr("data-order-column");
            SailboatList.sailboatList();    //查询游艇帆船列表数据
        });
        $(".sel-label").click(function(){
            $(this).addClass("hide");
            if ($(this).attr("data-name") == "location") {
                $(":input[name='location']").first().prop("checked", "checked");
            } else if ($(this).attr("data-name") == "category") {
                $(":input[name='category']").first().prop("checked", "checked");
            } else {
                $(":input[name='price']").first().prop("checked", "checked");
            }
            if ($(".sel-label.hide").length == 3) {
                $(".clear-all-sel-label").addClass("hide");
            }
            SailboatList.sailboatList();     //查询游艇帆船列表数据
        });
        $(".clear-all-sel-label").click(function() {
            $(":input[name='location']").first().prop("checked", "checked");
            $(":input[name='category']").first().prop("checked", "checked");
            $(":input[name='price']").first().prop("checked", "checked");
            $(".sel-label").addClass("hide");
            $(".clear-all-sel-label").addClass("hide");
            SailboatList.sailboatList();     //查询游艇帆船列表数据
        });


        $(":input[name='price']").click(function() {
            if (!$(this).attr("min") && !$(this).attr("max")) {
                $(".sel-price").addClass("hide");
                if ($(".sel-label.hide").length == 3) {
                    $(".clear-all-sel-label").addClass("hide");
                }
            } else {
                $(".sel-price").removeClass("hide");
                $(".clear-all-sel-label").removeClass("hide");
                $(".sel-price").html("￥" + $(this).attr("min") + "-" + $(this).attr("max"));
            }
            SailboatList.sailboatList();     //查询游艇帆船列表数据
        });

        if ($(":input[name='location']:checked").attr("data-name")) {
            $(".sel-scenic").removeClass("hide");
            $(".clear-all-sel-label").removeClass("hide");
            $(".sel-scenic").html($(":input[name='location']:checked").attr("data-name"));
        }

        $(":input[name='location']").click(function() {
            if (!$(this).attr("data-name")) {
                $(".sel-scenic").addClass("hide");
                if ($(".sel-label.hide").length == 3) {
                    $(".clear-all-sel-label").addClass("hide");
                }
            } else {
                $(".sel-scenic").removeClass("hide");
                $(".clear-all-sel-label").removeClass("hide");
                $(".sel-scenic").html($(this).attr("data-name"));
            }
            SailboatList.sailboatList();     //查询游艇帆船列表数据
        });

        if ($(":input[name='category']:checked").attr("data-name")) {
            $(".sel-ticketType").removeClass("hide");
            $(".clear-all-sel-label").removeClass("hide");
            $(".sel-ticketType").html($(":input[name='category']:checked").attr("data-name"));
        }

        $(":input[name='category']").click(function() {
            if (!$(this).attr("data-name")) {
                $(".sel-ticketType").addClass("hide");
                if ($(".sel-label.hide").length == 3) {
                    $(".clear-all-sel-label").addClass("hide");
                }
            } else {
                $(".sel-ticketType").removeClass("hide");
                $(".clear-all-sel-label").removeClass("hide");
                $(".sel-ticketType").html($(this).attr("data-name"));
            }
            SailboatList.sailboatList();     //查询游艇帆船列表数据
        });
    },

    createPager: function() {
        var options = {
            countUrl: "/yhypc/sailboat/getTotalPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                $('#sailboatList').empty();
                //$("#loading").show();
                scroll(0, 0);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/sailboat/getScenicList.jhtml',
                    data: data,
                    progress: true,
                    success: function(data) {
                        $('#sailboatList').empty();
                        $("#loading").hide();
                        $("#totalProduct").html(data.page.totalCount);
                        for (var i = 0; i < data.ticketResponses.length; i++) {
                            var s = data.ticketResponses[i];
                            if(parseInt(s.id) < 2000433){
                                s.shortComment = formatString(s.shortComment);
                            }
                            var result = $(template("tpl-sailboat-list-item", s));
                            $('#sailboatList').append(result);
                            result.data("scenic", s);
                            SailboatList.getTicketPriceList(1, 3, s.id);
                        }
                        $("div[id^='ticket-category-']").hide();

                    },
                    error: function() {}
                });
            }
        };
        SailboatList.pager = new Pager(options);
    },

    sailboatList: function () {
        var search = {};

        if ($(":input[name='category']:checked").val()) {
            search['ticketSearchRequest.ticketTypes[0]'] = $(":input[name='category']:checked").val();
        } else {
            search['ticketSearchRequest.ticketTypes[0]'] = 'sailboat';
            search['ticketSearchRequest.ticketTypes[1]'] = 'yacht';
            search['ticketSearchRequest.ticketTypes[2]'] = 'huanguyou';
        }

        if ($(":input[name='location']:checked").val()) {
            search['ticketSearchRequest.scenicId'] = $(":input[name='location']:checked").val();
        }

        var priceRange = [];
        if ($(":input[name='price']:checked").attr("min") && $(":input[name='price']:checked").attr("min") != "undefined") {
            search['ticketSearchRequest.priceRange[0]'] = $(":input[name='price']:checked").attr("min");
        }
        if ($(":input[name='price']:checked").attr("max") && $(":input[name='price']:checked").attr("max") != "undefined") {
            search['ticketSearchRequest.priceRange[1]'] = $(":input[name='price']:checked").attr("max");
        }
        search['ticketSearchRequest.orderColumn'] = $(".content-nav .li-active").attr("data-order-column");
        search['ticketSearchRequest.orderType'] = $(".content-nav .li-active").attr("data-order-type");
        SailboatList.pager.init(search);
    },

    priceInfo: function(targetObjt, id) {
        $.post("/yhypc/sailboat/getPriceTypeInfo.jhtml",
            {ticketPriceId: id},
            function (data) {
                targetObjt.empty();
                if (data.success) {
                    var priceTypeExtendList = data.priceTypeExtendList;
                    template.config("escape", false);
                    var result = $(template("tpl-sailboat-list-type-item", data));
                    targetObjt.append(result);
                    result.data("ticketPriceInfo", data);
                }
            }
            ,"json");
    },
    datalist: function(id, index, event) {
        var onOff = $("#ticket-category-"+ id +"-"+ index +"").attr("data-flag");
        if (onOff == 0) {
            SailboatList.priceInfo($("#ticket-category-"+ id +"-"+ index +""), id);
            $("#ticket-category-"+ id +"-"+ index +"").slideDown('2000');
            $("#ticket-category-"+ id +"-"+ index +"").attr("data-flag", "1");
        } else {
            $("#ticket-category-"+ id +"-"+ index +"").slideUp('2000');
            $("#ticket-category-"+ id +"-"+ index +"").attr("data-flag", "0");
        }
    },
    IE8Css:function(){
        var explorer = window.navigator.userAgent ;
        if (explorer.indexOf("MSIE 8.0") >= 0) {
            $('.mask-bg').css({'filter':'Mask(Color=#f3f8fe)'});
        }
    }
};
$(function(){
    SailboatList.init();
});
