function go2Prices(leaveCity2, leavePort2, arriveCity2, arrivePort2, leaveDate2) {

    $('#Order_hc_list_ul_back').html('');
    $.ajax({
        url: "/lvxbang/traffic/listTrafficPrice.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'leaveCity': leaveCity2,
            'leavePort': leavePort2,
            'arriveCity': arriveCity2,
            'arrivePort': arrivePort2,
            'leaveDate': leaveDate2,
            'trafficType': 'AIRPLANE',
        },
        success: function (data) {
            if (data.length > 0) {
                addBackDates(3);
                var ports = '', companys = '';
                $('#flight_return_count').html(data.length);
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    processFlight(s);
                    s.totalTime = Math.floor(s.flightTime / 60) + '小时' + s.flightTime % 60 + '分';
                    ports = addReturnPort(ports, s.leaveTransportation.name);
                    companys = addReturnCompany(companys, s.company);
                    var result = template("tpl-traffic-flight-item", s);
                    $('#Order_fj_list_ul_back').append(result);
                    leaveCity = s.leaveCity.id;
                    arriveCity = s.arriveCity.id;
                    addPrices(s.hashCode, s.prices, s.trafficCode);

                }
                Sort.changeSort('Order_fj_list_ul_back', 'wf-sort-leave-right', 'sortLeave', 'wf-sort-type-right', 0);
                //返程radio
                returnRaioClick();
                extractKey('returnTrafficKey', leaveCity2, arriveCity2, "AIRPLANE", leaveDate2);
                searchSuccess(3, 2);

                bindTishi3();
            } else {
                searchFaild(3, 2);
            }
        },
        error: function () {
            searchFaild(3, 2);
        }
    });
}

function go1Prices(leaveCity, leavePort, arriveCity, arrivePort, leaveDate) {

    $('#Order_hc_list_ul').html('');

    $.ajax({
        url: "/lvxbang/traffic/listTrafficPrice.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'leaveCity': leaveCity,
            'leavePort': leavePort,
            'arriveCity': arriveCity,
            'arrivePort': arrivePort,
            'leaveDate': leaveDate,
            'trafficType': 'AIRPLANE',
        },
        success: function (data) {
            if (data.length > 0) {
                addDates(3);
                var ports = '', companys = '';
                $('#flight_count').html(data.length);
                for (var i = 0; i < data.length; i++) {
                    var s = data[i];
                    processFlight(s);
                    s.totalTime = Math.floor(s.flightTime / 60) + '小时' + s.flightTime % 60 + '分';
                    ports = addPort(ports, s.leaveTransportation.name);
                    companys = addCompany(companys, s.company);
                    var result = template("tpl-traffic-flight-item", s);
                    $('#Order_fj_list_ul').append(result);
                    leaveCity = s.leaveCity.id;
                    arriveCity = s.arriveCity.id;
                    addPrices(s.hashCode, s.prices, s.trafficCode);


                }
                Sort.changeSort('Order_fj_list_ul', 'wf-sort-leave-left', 'sortLeave', 'wf-sort-type-left', 0);
                //去程radio
                leaveRadioClick();
                extractKey('trafficKey', leaveCity, arriveCity, "AIRPLANE", leaveDate);
                searchSuccess(3, 1);
                bindTishi3();
            } else {
                searchFaild(3, 1);
            }
        },
        error: function () {
            searchFaild(3, 1);
        }
    });
}
function findTrafficPrice(go1, go2) {
    searchingWithReturn(go1, go2);
    var leaveCity = $('#leaveCity-3').val();
    if ($('#leavePort-3').val() == '' && leaveCity == '') {
        promptWarn('请选择第一程出发城市');
        searchFaild();
        return;
    }
    var arriveCity = $('#transitCity').val();
    if ($('#transPort').val() == '' && arriveCity == '') {
        promptWarn('请选择第一程到达城市');
        searchFaild();
        return;
    }
    var leaveDate1 = $('#leaveDate-3').val();
    if (leaveDate1 == undefined || leaveDate1 == '') {
        promptWarn('请选择第一程出发日期');
        searchFaild();
        return;
    }
    var leaveCity2 = $('#transitCity').val();
    if (leaveCity2 == '') {
        promptWarn('请选择第二程出发城市');
        searchFaild();
        return;
    }
    var arriveCity2 = $('#arriveCity-3').val();
    if ($('#arrivePort-3').val() == '' && arriveCity == '') {
        promptWarn('请选择第二程到达城市');
        searchFaild();
        return;
    }
    var leaveDate2 = $('#returnDate-3').val();
    if (leaveDate1 == undefined || leaveDate1 == '') {
        promptWarn('请选择第二程出发日期');
        searchFaild();
        return;
    }
    var leavePort = $('#leavePort-3').val();
    var arrivePort = $('#transitPort').val();
    var leavePort2 = $('#transitPort').val();
    var arrivePort2 = $('#arrivePort-3').val();
    if (go1)
        go1Prices(leaveCity, leavePort, arriveCity, arrivePort, leaveDate1);
    if (go2)
        go2Prices(leaveCity2, leavePort2, arriveCity2, arrivePort2, leaveDate2);
}

function initPage() {
    $("#searchForm-3").show();
    $("#searchForm-2").hide();
    $("#searchForm-1").hide();
    $(".n_title").html($(".n_title").html() + "（联程）");
    TrafficCookie.saveTrafficCookie(3, "flight");
    findTrafficPrice(true, true);


    $(".o_select_fl .huan").click(function () {
        var Departure2 = $('[name=Departure2]').val();
        var Reach2 = $('[name=Reach2]').val();
        $('[name=Departure2]').val(Reach2);
        $('[name=Reach2]').val(Departure2);
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
        if (width > ta_list_div_d && width - zong > 603) {
            //parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul li.checked").removeClass("checked").next("li").addClass("checked");
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").animate({"margin-left": "-=87px"}, 500);
            nowPage++;
            parentx.find(".Order_hc_ss_d .Order_hc_ss_d_ul").attr("num", nowPage);
        }

    });

    //更多
    $(".Order_fj_list_ul .shouqi").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $("span", this).html("收起<i></i>");
            var el = $(this).prev('.Order_fl_list_dl'),
                curHeight = el.height(),
                autoHeight = el.css('height', 'auto').height();
            el.height(curHeight).animate({height: autoHeight}, 500);
            $(this).addClass("checked").attr("data-staute", "1");
        } else {
            $("span", this).html("更多舱位<i></i>");
            $(this).prev(".Order_fl_list_dl").animate({"height": "174px"}, 500);
            $(this).removeClass("checked").removeAttr("data-staute");
        }
    });


    //收起
    $(".Order_hc_list_ul_hide .shouqi").click(function () {

        $(this).closest(".Order_hc_list_ul_hide").prev(".Order_hc_list_ul_div").find("div span.more").removeClass("checked").removeAttr("data-staute");
        $(this).closest(".Order_hc_list_ul_hide").fadeOut();
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

function checkReturnFlight(thiz){

    var trafficKey = $(thiz).find("input[name='trafficKey']").val();
    var singleTrafficId = $(thiz).find("input[name='singleTrafficId']").val();
    var singleTrafficPriceId = $(thiz).find("input[name='singleTrafficPriceId']").val();
    var returnTrafficKey = $(thiz).find("input[name='returnTrafficKey']").val();
    var returnTrafficId = $(thiz).find("input[name='returnTrafficId']").val();
    var returnTrafficPriceId = $(thiz).find("input[name='returnTrafficPriceId']").val();
    var off = true;
    $.ajax({
        url: "/lvxbang/traffic/orderReturnFlight.jhtml",
        type: "post",
        dataType: "json",
        async: false,
        data: {
            'trafficKey': trafficKey,
            'singleTrafficId': singleTrafficId,
            'singleTrafficPriceId': singleTrafficPriceId,
            'returnTrafficKey': returnTrafficKey,
            'returnTrafficId': returnTrafficId,
            'returnTrafficPriceId': returnTrafficPriceId
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