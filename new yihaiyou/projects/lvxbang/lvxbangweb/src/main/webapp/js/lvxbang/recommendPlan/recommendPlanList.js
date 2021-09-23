/**
 * Created by zzl on 2015/12/31.
 */
var pager;
$(document).ready(function () {
    // 初始化分页
    createPager();
    //更多
    //$(".searchBox .more").click(function () {
    //    var myStaute = $(this).attr("data-staute");
    //    if (!myStaute) {
    //        $("span", this).text("收起更多");
    //        var el = $(this).next('.select'),
    //            curHeight = el.height(),
    //            autoHeight = el.css('height', 'auto').height();
    //        el.height(curHeight).animate({height: autoHeight}, 1000);
    //        $(this).addClass("checked");
    //        $(this).attr("data-staute", "1");
    //    } else {
    //        $("span", this).text("更多查询条件");
    //        $(this).next(".select").animate({"height": "52px"}, 1000);
    //        $(this).removeClass("checked");
    //        $(this).removeAttr("data-staute");
    //    }
    //});

    //收藏
    $(".collect2").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).text("已收藏").addClass("checked");
            $(this).attr("data-staute", "1");
        } else {
            $(this).text("+收藏").removeClass("checked");
            $(this).removeAttr("data-staute");
        }
    });

    //checkbox
    $("#destination").delegate(".checkbox .checkbox_i", "click", function () {
        if ($(this).parent("div").hasClass("checked")) {
            $(this).parent("div").removeClass("checked");
        }
        else {
            $(this).parent("div").addClass("checked");
        }
        getRecommendPlanList();
    });

    CitySelector.withOption({
        selectedFn: function () {
            getRecommendPlanList();
            //FloatEditor.renderCity();
        }
    });
    //初始化查询条件
    initQueryCondition();
    //发起列表检索请求
    getRecommendPlanList();
});

function initQueryCondition() {
    $.ajax({
        url: "/lvxbang/labels/getRecplanListLabels.jhtml?parentId=31",
        type: "get",
        dataType: "json",
        success: function (data) {
            $("#tag_t10").html("");
            $("#tag_t10").append("<li class='whole'><a href=\"#\" name=\"tag\" class=\"checked\" data-staute=\"1\">全部</a></li>");
            $.each(data, function (i, item) {
                $("#tag_t10").append("<li><a href='#' name='tag' lid=" + item.id + ">" + item.name + "</a></li>");
            });
            $("#tag_t10 li a").click(function () {
                $("#tag_t10 li a").removeClass("checked");
                $("#tag_t10 li a").removeAttr("data-staute");
                $(this).addClass("checked");
                $(this).attr("data-staute", "1");
                getRecommendPlanList();
            });
        }
    });



    //$.ajax({
    //    url: "/lvxbang/labels/getRecplanListLabels.jhtml?parentId=43",
    //    type: "get",
    //    dataType: "json",
    //    success: function (data) {
    //        $("#theme_tag").html("");
    //        $("#theme_tag").append("<li><a href=\"#\" name=\"tag\" class=\"checked\" data-staute=\"1\">全部</a></li>");
    //        $.each(data, function (i, item) {
    //            $("#theme_tag").append("<li><a href='#' name='monthRange' lid=" + item.id + ">" + item.name + "</a></li>");
    //        });
    //        $("#theme_tag li a").click(function () {
    //            $("#theme_tag li a").removeClass("checked");
    //            $("#theme_tag li a").removeAttr("data-staute")
    //            $(this).addClass("checked");
    //            $(this).attr("data-staute", "1");
    //            getRecommendPlanList();
    //        });
    //    }
    //});

    //$.ajax({
    //    url: "/lvxbang/labels/getRecplanListLabels.jhtml?parentId=44",
    //    type: "get",
    //    dataType: "json",
    //    success: function (data) {
    //        $("#user_tag").html("");
    //        $("#user_tag").append("<li><a href=\"#\" name=\"tag\" class=\"checked\" data-staute=\"1\">全部</a></li>");
    //        $.each(data, function (i, item) {
    //            $("#user_tag").append("<li><a href='#' name='tag' lid=" + item.id + ">" + item.name + "</a></li>");
    //        });
    //        $("#user_tag li a").click(function () {
    //            $("#user_tag li a").removeClass("checked");
    //            $("#user_tag li a").removeAttr("data-staute")
    //            $(this).addClass("checked");
    //            $(this).attr("data-staute", "1");
    //            getRecommendPlanList();
    //        });
    //    }
    //});

    //绑定搜索条件点击事件

    $("#day_range li a").click(function () {
        $("#day_range li a").removeClass("checked");
        $("#day_range li a").removeAttr("data-staute");
        $(this).addClass("checked");
        $(this).attr("data-staute", "1");
        getRecommendPlanList();
    });

    $("#month_range li a").click(function () {
        $("#month_range li a").removeClass("checked");
        $("#month_range li a").removeAttr("data-staute");
        $(this).addClass("checked");
        $(this).attr("data-staute", "1");
        getRecommendPlanList();
    });

    $("#cost_range li a").click(function () {
        $("#cost_range li a").removeClass("checked");
        $("#cost_range li a").removeAttr("data-staute")
        $(this).addClass("checked");
        $(this).attr("data-staute", "1");
        getRecommendPlanList();
    });

    $("#orderType li").click(function () {
        orderClick(this);
        getRecommendPlanList(1);
    });

    //$("#destination p input").click(function () {
    //    if ($(this).attr("checked") == "checked") {
    //        $(this).removeClass("checked");
    //        $(this).removeAttr("checked");
    //        $(this).parent().removeClass("checked");
    //    } else {
    //        $(this).addClass("checked");
    //        $(this).attr("checked", "checked");
    //        $(this).parent().addClass("checked");
    //    }
    //    getRecommendPlanList();
    //});

    $("#destination a[data-staute = 'related']").click(function () {
        $(this).remove();
        getRecommendPlanList();
    });
}

function getRecommendPlanList(type) {
    var labelIds = [];
    var cityIds = [];
    var condition = {};
    $("#tag_t10 li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("lid") > 0) {
            labelIds.push($(this).attr("lid"));
        }
    });

    $("#month_range li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("monthRange") != null
            && $(this).attr("monthRange").split("-").length == 2) {
            condition['recommendPlanSearchRequest.monthRange[0]'] = Number($(this).attr("monthRange").split("-")[0]);
            condition['recommendPlanSearchRequest.monthRange[1]'] = Number($(this).attr("monthRange").split("-")[1]);
        }
    });

    $("#day_range li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("dayRange") != null) {
            if ($(this).attr("dayRange").split("-")[0] > 14) {
                condition['recommendPlanSearchRequest.dayRange[0]'] = Number($(this).attr("dayRange").split("-")[0]);
            } else {
                condition['recommendPlanSearchRequest.dayRange[0]'] = Number($(this).attr("dayRange").split("-")[0]);
                condition['recommendPlanSearchRequest.dayRange[1]'] = Number($(this).attr("dayRange").split("-")[1]);
            }
        }
    });
    $("#theme_tag li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("lid") > 0) {
            labelIds.push($(this).attr("lid"));
        }
    });

    $("#cost_range li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("costRange") != null) {
            if ($(this).attr("data-staute") == 1 && $(this).attr("costRange") != null) {
                if ($(this).attr("costRange").split("-")[0] > 5000) {
                    condition['recommendPlanSearchRequest.costRange[0]'] = Number($(this).attr("costRange").split("-")[0]);
                } else {
                    condition['recommendPlanSearchRequest.costRange[0]'] = Number($(this).attr("costRange").split("-")[0]);
                    condition['recommendPlanSearchRequest.costRange[1]'] = Number($(this).attr("costRange").split("-")[1]);
                }
            }
        }
    });

    $("#user_tag li a").each(function (i, e) {
        if ($(this).attr("data-staute") == 1 && $(this).attr("lid") > 0) {
            labelIds.push($(this).attr("lid"));
        }
    });

    $("#orderType li").each(function (i, e) {
        if ($(this).hasClass("checked")) {
            if(isNull($('#r_name').val()) || type==1){
                condition['recommendPlanSearchRequest.orderColumn'] = $(this).attr("orderBy");
                condition['recommendPlanSearchRequest.orderType'] = $(this).attr("orderType");
            }else{
                $(this).removeClass('checked');
            }
        }
    });

    if ($("#destination div.checkbox").length > 0) {
        //console.log($("#destination_item p input").length);
        $("#destination div").each(function (i, e) {
            if ($(this).hasClass("checked")) {
                //console.log($(this).val());
                cityIds.push(Number($(this).attr("data-id")));
            }
        });
        if (cityIds.length == 0) {
            $("#recommendPlan_item").html('');
            $("#loading").hide();
            $(".m-pager").html('<label class="total">共0页/0条</label>');
            return;
        }
    }

    if ($("#destination a.related_item").length > 0) {
        var relatedType = $("#destination a.related_item").attr("relatedType");
        var relatedId = $("#destination a.related_item").attr("relatedId");
        if (relatedType == "scenic") {
            condition['recommendPlanSearchRequest.scenicId'] = relatedId;
        } else if (relatedType == "delicacy") {
            condition['recommendPlanSearchRequest.delicacyId'] = relatedId;
        } else if (relatedType == "hotel") {
            condition['recommendPlanSearchRequest.hotelId'] = relatedId;
        } else if (relatedType == "traffic") {
            condition['recommendPlanSearchRequest.trafficId'] = relatedId;
        }
    }

    if ($("#r_name").val() != null && $("#r_name").val() != '') {
        condition['recommendPlanSearchRequest.name'] = $("#r_name").val();
    }

    $.each(cityIds, function (i, item) {
        condition['recommendPlanSearchRequest.cityIds[' + i + ']'] = item;
    });

    $.each(labelIds, function (i, item) {
        condition['recommendPlanSearchRequest.labelIds[' + i + ']'] = item;
    });

    //console.log(labelIds);
    //console.log(condition);

    pager.init(condition);
}

function createPager() {
    var page = {
        countUrl: "/lvxbang/recommendPlan/getTotalPage.jhtml",
        resultCountFn: function (result) {
            return parseInt(result[0]);
        },
        pageRenderFn: function (pageNo, pageSize, data) {
            $("#recommendPlan_item").empty();
            $("#loading").show();
            scroll(0, 0);
            data.pageIndex = pageNo;
            data.pageSize = pageSize;
            $.ajax({
                url: "/lvxbang/recommendPlan/getRecplanList.jhtml",
                type: "post",
                dataType: "json",
                data: data,
                success: function (data) {
                    $("#recommendPlan_item").empty();
                    $("#loading").hide();
                    $.each(data, function (i, item) {
                        //if (item.startTime != null) {
                        //    item.startTime = new Date(item.startTime.time).format("yyyy-MM-dd");
                        //} else {
                        //    item.startTime = '';
                        //}
                        if (item.createTime != null) {
                            item.createTime = new Date(item.createTime.time).format("yyyy-MM-dd");
                        } else {
                            item.createTime = '';
                        }
                        item.description = formatString(item.description);
                        var recommendPlan_item = template("recommendPlan_item_template", item);
                        //console.log(item.createTime);
                        $("#recommendPlan_item").append(recommendPlan_item);
                    });
                    collect(".collect2", false, ".collectNum");
                    $(".is_hover, .title_hover").hover(function () {
                        $(this).find("span").hide();
                    }, function () {
                        $(this).find("span").show();
                    });
                    //图片懒加载
                    $("img").lazyload({
                        effect: "fadeIn",
                        load: resizePic
                    });
                }
            });
        }
    };
    pager = new Pager(page);
}

//回车键进行搜索
$(function(){
    $('#r_name').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            if(!isNull($('#r_name').val())){
                getRecommendPlanList();
                $('#r_name').next().hide();
            }
        }
    });
});