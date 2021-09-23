/**
 * Created by Sane on 16/1/3.
 */
function findTrafficPrice() {
    searching(true, false);
    var leaveCity = $('#leaveCity-1').val();
    if ($('#leavePort-1').val() == '' && leaveCity == '') {
        promptWarn('请选择出发城市');
        searchFaild(1, 1);
        return;
    }
    var arriveCity = $('#arriveCity-1').val();
    if ($('#arrivePort-1').val() == '' && arriveCity == '') {
        promptWarn('请选择到达城市');
        searchFaild(1, 1);
        return;
    }
    var leaveDate = $('#leaveDate-1').val();
    if (leaveDate == undefined || leaveDate == '') {
        promptWarn('请选择出发日期');
        searchFaild(1, 1);
        return;
    }

    $('#Order_hc_list_ul').html('');
    $.ajax({
        url: "/lvxbang/traffic/listTrafficPrice.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'leaveCity': leaveCity,
            'leavePort': $('#leavePort-1').val(),
            'arriveCity': arriveCity,
            'arrivePort': $('#arrivePort-1').val(),
            'leaveDate': leaveDate,
            'trafficType': 'TRAIN'

        },
        success: function (data) {
            if (data.length > 0) {
                addDates(1);
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    processFlight(s);
                    s.totalTime = Math.floor(s.flightTime / 60) + 'h' + s.flightTime % 60 + 'm';
                    s.days = parseInt((s.flightTime / 60 + parseInt(s.leaveTime.split(":")[0])) / 24);
                    //s.leaveAndArrive = leaveAndArrive;
                    if (s.prices[0] != undefined)
                        s.arriveDate = new Date(s.prices[0].arriveTime.time).format("MM月dd日 HH:mm ");
                    s.totalTime = s.totalTime.replace("h", "小时").replace("m", "分");
                    var result = template("tpl-traffic-train-item", s);
                    $('#Order_hc_list_ul').append(result);
                    getLeaveAndArrive(s, s.hashCode);
                    leaveCity = s.leaveCity.id;
                    arriveCity = s.arriveCity.id;
                    addPrices(s.hashCode, s.prices, s.trafficCode);
                }
                Sort.changeSort('Order_hc_list_ul', 'dc-sort-leave', 'sortLeave', 'dc-sort-type', 0);
                extractKey('trafficKey', leaveCity, arriveCity, "TRAIN", leaveDate);
                siteMore();
                searchSuccess(1, 1);
                bindTishi3();
            } else {
                searchFaild(1, 1);
            }

        },
        error: function () {
            searchFaild(1, 1);
        }
    });

}
function initPage() {
    $("#searchForm-1").show();
    $("#searchForm-2").hide();
    $("#searchForm-3").hide();
    $(".n_title").html($(".n_title").html() + "（单程）");
    TrafficCookie.saveTrafficCookie(1, "train");
    TrafficCookie.getTrafficCookie("train", "search-history");
    findTrafficPrice();
    // $('#flightSearch').bind('click', findTrafficPrice);
    $(".o_select_fl .huan").click(function () {
        var Departure2 = $('[name=Departure2]').val();
        var Reach2 = $('[name=Reach2]').val();
        $('[name=Departure2]').val(Reach2);
        $('[name=Reach2]').val(Departure2);
    });

    $(".Order_hc_ss_d_ul li").click(function () {
        $(".Order_hc_ss_d_ul li.checked").removeClass("checked");
        $(this).addClass("checked");
        var dateStr = $(this).children('input[name="dateStr"]').val();
        $('#leaveDate-1').val(dateStr);
        findTrafficPrice();
    });

    $('.Order_hc_ss .Order_left').click(function () {
        var parentx = $(this).parent(".Order_hc_ss");
        var nowPage = parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").attr("num");
        var zong = nowPage * 87;

        if (nowPage > 1) {
            //parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul li.checked").removeClass("checked").prev("li").addClass("checked");
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").animate({"margin-left": "+=87px"}, 500);
            nowPage--;
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").attr("num", nowPage);
        }

    });

    $('.Order_hc_ss .Order_right').click(function () {
        var parentx = $(this).parent(".Order_hc_ss");
        var ta_list_ul = parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul li").length;
        var width = ta_list_ul * 87;
        parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").css("width", width);
        var nowPage = parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").attr("num");
        var ta_list_div_d = parentx.find(".Order_hc_ss_d").width();
        var zong = nowPage * 87;
        if (width > ta_list_div_d && width - zong > 780) {
            //parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul li.checked").removeClass("checked").next("li").addClass("checked");
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").animate({"margin-left": "-=87px"}, 500);
            nowPage++;
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").attr("num", nowPage);
        }

    });

    //更多
    $(".searchBox .more").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $("span", this).text("收起");
            var el = $(this).parent('.select'),
                curHeight = el.height(),
                autoHeight = el.css('height', 'auto').height();
            el.height(curHeight).animate({height: autoHeight}, 1000);
            $(this).addClass("checked").attr("data-staute", "1");
        } else {
            $("span", this).text("更多查询条件");
            $(this).parent(".select").animate({"height": "90px"}, 1000);
            $(this).removeClass("checked").removeAttr("data-staute");
        }
    });


    //checkbox
    $(".Order_hc_list_t .checkbox").click(function () {
        var myStaute = $(this).parent().attr("data-staute");
        if (!myStaute) {
            $(this).parent().addClass("checked").attr("data-staute", "1");
        } else {
            $(this).parent().removeClass("checked").removeAttr("data-staute");
        }
    });
    //添加
    $(".searchBox .select_div li a").click(function () {
        $(this).closest(".select_div").find("a").removeClass("checked");
        $(this).addClass("checked")
        var parentsx = $(this).parents(".searchBox"); //最上一级
        var text = $(this).text();
        var num = $(this).closest(".select_div").attr("num");
        var Order_hc_jg_div_ul = parentsx.next(".Order_hc_jg_div").find(".Order_hc_jg_div_d .Order_hc_jg_div_ul");
        var maybeexists = Order_hc_jg_div_ul.find('li[num=' + num + '] span');
        if (text != "不限") {
            parentsx.next(".Order_hc_jg_div").fadeIn(); //选择显示
            if (maybeexists.length > 0) {
                maybeexists.text(text);
            } else {
                Order_hc_jg_div_ul.append("<li num=" + num + "><span>" + text + "</span><i></i></li>");
            }
        } else {
            $(this).addClass("checked")
            maybeexists.parent().remove();
        }

        if (Order_hc_jg_div_ul.find('li[num]').length == 0) {
            parentsx.next(".Order_hc_jg_div").fadeOut();
        }
    });


    //删除
    $(".Order_hc_jg_div").delegate('.Order_hc_jg_div_ul i', 'click', function () {
        var num = $(this).parent().attr("num");
        $(this).parents(".Order_hc_jg_div").prev('.searchBox').find('div[num=' + num + '] .checked').removeClass('checked')
        $(this).parents(".Order_hc_jg_div").prev('.searchBox').find('div[num=' + num + '] li:first-child a').addClass('checked')
        $(this).parent("li").remove();
    });

    //清空
    $(".Order_hc_jg_div").delegate('.close', 'click', function () {
        $(this).prev(".Order_hc_jg_div_ul").find("li").remove();
        $(this).closest(".Order_hc_jg_div").fadeOut();
        $(this).parents(".Order_hc_jg_div").prev('.searchBox').find('.checked').removeClass('checked')
        $(this).parents(".Order_hc_jg_div").prev('.searchBox').find('li:first-child a').addClass('checked')
    });

};
$(initPage());

function checkSingleFlight(thiz){

     var trafficKey = $(thiz).find("input[name='trafficKey']").val();
    var singleTrafficId = $(thiz).find("input[name='singleTrafficId']").val();
    var singleTrafficPriceId = $(thiz).find("input[name='singleTrafficPriceId']").val();
    var off = true;
    $.ajax({
        url: "/lvxbang/traffic/orderSingleFlight.jhtml",
        type: "post",
        dataType: "json",
        async: false,
        data: {
            'trafficKey': trafficKey,
            'singleTrafficId': singleTrafficId,
            'singleTrafficPriceId': singleTrafficPriceId
        },
        success: function (data) {
           if(!data.success){
               promptWarn(data.errMsg, 2000);
               off = false;
           }
        },
        error: function () {

        }
    });

    return off;
}