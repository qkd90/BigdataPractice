/**
 * Created by Sane on 16/1/3.
 */

function seatFilter(seatName) {//座位舱位
    $('.Order_hc_c_fl dd').hide();
    $('.Order_hc_c_fl dd:contains(' + seatName + ')').show();
}

function companyFilter(companyName) {//航空公司
    $('.Order_hc_c_fl').hide();
    $('.Order_hc_c_fl:contains(' + companyName + ')').show();
}

function portFilter(portName) {//起飞机场
    $('.Order_hc_c_fl').hide();
    $('.Order_hc_c_fl:contains(' + portName + ')').show();
}

function leaveTimeFilter(time) {//时段
    $('.Order_hc_c_fl').hide();
    $('.Order_hc_c_fl:contains(' + time + ')').show();
}
function arriveTimeFilter(time) {//时段
    $('.Order_hc_c_fl').hide();
    $('.Order_hc_c_fl:contains(' + time + ')').show();
}
function typeFilter(type) {//车型 高铁 动车
    $('.Order_hc_c_fl').hide();
    $('.Order_hc_c_fl:contains(' + type + ')').show();
}

function addPrices(hashCode, prices, code) {
    var hasTicket = false;
    for (var j = 0; j < prices.length; j++) {
        var p = prices[j];
        p.code = code;
        p.trafficHashCode = hashCode;
        p.leaveTime = new Date(p.leaveTime.time).format("MM月dd日 HH:mm ");
        p.arriveTime = new Date(p.arriveTime.time).format("MM月dd日 HH:mm ");
        if (p.price != '0' && p.seatNum != '0') {
            if (!hasTicket)
                hasTicket = true;
            if (p.seatNum > '9' || p.seatNum == '>9') {
                p.seatNum = '票量充足';
            } else {
                p.seatNum = '仅剩' + p.seatNum + '张';
            }
            var price = template("tpl-traffic-price-item", p);
            $('#traffic_price_' + hashCode).append(price);
        }
    }
    if (!hasTicket) {
        var p = {
            code: '',
            trafficHashCode: '',
            leaveTime: '',
            arriveTime: '',
            seatNum: '无票',
            seatName: '',
            price: 0,
        }
        var price = template("tpl-traffic-price-item", p);
        $('#traffic_price_' + hashCode).append(price);
        $('#traffic_price_' + hashCode + ' a').remove();
    }
}
function getLeaveAndArrive(s, code) {
    var guo = '<div class="guo cl"><span class="disB textC fl">过</span> ';
    var leaveAndArrive = (s.startPort == s.leaveTransportation.name ? '<div class="kais cl"><span class="disB textC fl">始</span> ' : guo) +
        '<p class="fl">' + s.leaveTransportation.name + '</p>' +
        (s.endPort == s.arriveTransportation.name ? '</div> <div class="jies cl"><span class="disB textC fl">终</span> ' : guo) +
        '<p class="fl">' + s.arriveTransportation.name + '</p></div>';
    $('#traffic_leaveAndArrive_' + code).html(leaveAndArrive);
}
function addPort(ports, port) {
    if (ports.indexOf(port) == -1) {
        $('#conditions_leavePort').append('<li><a title="' + port + '" href="javaScript:;">' + port + '</a></li>\n');
        ports += port + ",";
    }
    return ports;
}
function addCompany(companys, company) {
    if (companys.indexOf(company) == -1) {
        $('#conditions_company').append('<li><a title="' + company + '" href="javaScript:;">' + company + '</a></li>\n');
        companys += company + ",";
    }
    return companys;
}
function addReturnCompany(companys, company) {

    if (companys.indexOf(company) == -1) {
        $('#conditions_return_company').append('<li><a title="' + company + '" href="javaScript:;">' + company + '</a></li>\n');
        companys += company + ",";
    }
    return companys;
}
function addReturnPort(ports, port) {

    if (ports.indexOf(port) == -1) {
        $('#conditions_return_leavePort').append('<li><a title="' + port + '" href="javaScript:;">' + port + '</a></li>\n');
        ports += port + ",";
    }
    return ports;
}
function getPrice() {
    return parseFloat($(this).children('.vPrice').val()) + parseFloat($(this).children('.vFuelTax').val()) + parseFloat($(this).children('.vBuildFee').val());
}
function returnRaioClick() {
    $("#return .radio").unbind('click');
    $("#return .radio").click(function () {
        $("#return").find(".radio").removeClass('checked');
        $(this).addClass('checked');
        $('#return_flight_code').html($(this).children('.vCode').val());
        $('#return_time').html($(this).children('.vDate').val());
        $('#return_seat i').html($(this).children('.vSeat').val());
        $('#return_price').html(getPrice.call(this));

        count();

        $('input[name="returnTrafficId"]').val($(this).children('.vTrafficId').val());
        $('input[name="returnTrafficPriceId"]').val($(this).children('.vPriceId').val());
    });
}
function count() {
    //console.info('count ');
    if ($('#return_time').html() == ' --:-- 出发') {
        return;
    }
    if ($('#go_arrive_time').html() > $('#return_time').html()) {
        promptWarn('返程时间应晚于去程时间');
        //propromptWarn二程时间应当小于第一程:)\n返程发车时间与去程到达时间须相差2小时以上');
        $('#submitButton').removeClass('checked');
        $('#submitButton').attr("disabled", true);
        return;
    }
    var rPrice = $('#return_price').html();
    var gPrice = $('#go_price').html();
    if (rPrice != '' && gPrice != '') {
        var total = parseFloat(rPrice) + parseFloat(gPrice);
        $('#total_price').html(total);
        if (total > 0) {
            $('#submitButton').addClass('checked');
            $('#submitButton').attr("disabled", false);
        }
    }
}

function siteMore() {
    $(".Order_hc_list_ul_div .more").unbind('click');
    //火车经停站
    $(".Order_hc_list_ul_div .more").click(function () {
        ////console.info('show more Order_hc_list_ul_hide = ' +
        // $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").html()); //console.info('show more
        // trafficCode = ' +
        // $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children(".vTCode").val());
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").fadeIn();
            $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children('dd').remove();
            var id = new Date().getTime();
            $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children(".textC").attr('id', id);
            $.ajax({
                url: "/lvxbang/traffic/listTrainPassBy.jhtml",
                type: "post",
                dataType: "json",
                data: {
                    'trafficCode': $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children(".vTCode").val(),
                    'leavePortName': $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children(".vTSName").val(),
                    'arrivePortName': $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").children(".vTEName").val(),
                    'leaveDate': $('input[name="leaveDate"]').val()
                },
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var s = data[i];
                        //var day = s.day == "第一天"?"":"<i color=''";
                        var sname = i == 0 ? "始发站" : s.arrive;
                        var ename = i == data.length - 1 ? "终点站" : s.leave;
                        var stime = s.time.replace("-", "---");
                        var result = "<dd>\n" +
                            "                <div class=\"w1 fl\" ><span class=\"num\">" + (i + 1) + "</span></div>\n" +
                            "                    <div class=\"w2 fl\">" + s.siteName + "</div>\n" +
                            "                    <div class=\"w3 fl\">" + sname + "</div>\n" +
                            "                <div class=\"w4 fl\">" + ename + "</div>\n" +
                            "                <div class=\"w5 fl\">" + stime + "</div>\n" +
                            "                    </dd>";
                        $("#" + id).append(result);
                    }
                },
                error: function () {
                }
            });
            $(this).addClass("checked").attr("data-staute", "1");
        } else {
            $(this).closest(".Order_hc_list_ul_div").next(".Order_hc_list_ul_hide").fadeOut();
            $(this).removeClass("checked").removeAttr("data-staute");
        }
    });

    //收起
    $(".Order_hc_list_ul_hide .shouqi").click(function () {
        $(this).closest(".Order_hc_list_ul_hide").prev(".Order_hc_list_ul_div").find("div span.more").removeClass("checked").removeAttr("data-staute");
        $(this).closest(".Order_hc_list_ul_hide").fadeOut();
    });
}

function leaveRadioClick() {
    $("#leave .radio").unbind('click');
    $("#leave .radio").bind('click', function () {
        $("#leave").find(".radio").removeClass('checked');
        $(this).addClass('checked');
        //$('#go_date').html($(this).children('.w1').children('.name').html());
        $('#go_flight_code').html($(this).children('.vCode').val());
        $('#go_time').html($(this).children('.vDate').val());
        $('#go_arrive_time').html($(this).children('.vrDate').val());
        $('#go_seat i').html($(this).children('.vSeat').val());
        $('#go_price').html(getPrice.call(this));
        count();

        $('input[name="singleTrafficId"]').val($(this).children('.vTrafficId').val());
        $('input[name="singleTrafficPriceId"]').val($(this).children('.vPriceId').val());
    });
}

function bindTishi3() {
    $(".w3").hover(function () {
        $(this).find(".tishi3").show();
    }, function () {
        $(this).find(".tishi3").hide();
    });
}
function setCitysClick() {
    //机票火车中的目的地搜索框
    $(".select_area").each(function () {
        var category = $(this);
        category.find(".displayName").keyup(function (e) {
            category.find(".hideValue").attr('value', '');
            if (e.keyCode == 13) {
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
            $.post(category.attr("data-url"), {name: keyword}, function (result) {
                if (result.length > 0) {
                    var html = "";
                    $.each(result, function (i, data) {
                        data.key = data.name.replace(keyword, "<strong style='color:#39F;'>" + keyword + "</strong>");
                        html += template("tpl-suggest-item", data);
                    });
                    category.find("ul").html("").append(html);
                    category.find("li").css("height", "25px");
                    category.find("li").css("width", "100%");
                    category.find("li").css("line-height", "25px");
                    // set default value
                    var defaultValue = result[0];
                    category.find(".hideValue").attr('value', defaultValue.id);
                    // set values once click
                    category.find(".suggest-item").click(function () {
                        category.find(".displayName").attr('value', $(this).attr("data-text"));
                        category.find(".displayName").val($(this).attr("data-text"));
                        category.find(".hideValue").attr('value', $(this).attr("data-id"));
                    })
                }
            },"json");
            category.find(".list_citys_div").show();
        });
    });
    $(document).on("click", function () {
        $(".list_citys_div").hide();
    });
}
//增加快速选择天
function addDay(date, id) {

    var weekday = new Array(7);
    weekday[0] = "周日";
    weekday[1] = "周一";
    weekday[2] = "周二";
    weekday[3] = "周三";
    weekday[4] = "周四";
    weekday[5] = "周五";
    weekday[6] = "周六";
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var dateStr = '' + date.getFullYear() + '-' + (month > 9 ? month : ('0' + month)) + '-' + (day > 9 ? day : ('0' + day));
    var flightDay = {
        dayOfWeek: weekday[date.getDay()],
        dateStr: dateStr,
        day: day,
        month: month
    };
    var result = template("tpl-traffic-flight-date", flightDay);
    //console.info((date + 86400000) < new Date() + "," + (date + 86400000) + "," + new Date())
    if (new Date() - 86400000 > date) {
        result = result.replace('<li>', '<li style="display:none">');
    }
    $('#' + id).append(result);

}
function addDates(module) {
    $('#Order_fj_date_ul').html('');
    var leaveDate = new Date();
    var str = $('#leaveDate-' + module).val();
    if (str != undefined && str != '') {
        leaveDate = NewDate(str);
    }
    for (var i = -3; i < 13; i++) {
        addDay(new Date(leaveDate / 1 + 86400000 * i), "Order_fj_date_ul");
    }
    $('#Order_fj_date_ul li:eq(3)').addClass('checked');
    //去程日期
    $("#Order_fj_date_ul li").click(function () {
        var dateStr = $(this).children('input[name="dateStr"]').val();
        if (module > 1) {
            var returnDate = $('#returnDate-' + module).val();
            if (returnDate != undefined && new Date(returnDate) < new Date(dateStr)) {
                promptWarn('请选择正确的日期');
                return;
            }
        }
        $(this).parents(".Order_hc_ss_d_ul").find("li.checked").removeClass('checked');
        $(this).addClass('checked');
        $('#leaveDate-' + module).val(dateStr);
        $("#span-leaveDate").text(dateStr);
        $('#go_date').html(dateStr);
        $("#Order_fj_list_ul li").remove();
        findTrafficPrice(true, false);
    });
}
//解决ie下new Date带参数问题
function NewDate(str) {
    str = str.split('-');
    var date = new Date();
    date.setUTCFullYear(str[0], str[1] - 1, str[2]);
    date.setUTCHours(0, 0, 0, 0);
    return date;
}

function addBackDates(module) {
    $('#Order_fj_date_ul_back').html('');
    var returnDate = new Date();
    var str = $('#returnDate-' + module).val();
    if (str != undefined && str != '') {
        returnDate = NewDate(str);
    }
    for (var i = -3; i < 13; i++) {
        addDay(new Date(returnDate / 1 + 86400000 * i), "Order_fj_date_ul_back");
    }
    $('#Order_fj_date_ul_back li:eq(3)').addClass('checked');
    //console.info("addBackDates:" + module);
    //返程日期
    $("#Order_fj_date_ul_back li").click(function () {
        var dateStr = $(this).children('input[name="dateStr"]').val();
        if (module > 1) {
            var leaveDate = $('#leaveDate-' + module).val();
            if (leaveDate != undefined && new Date(dateStr) < new Date(leaveDate)) {
                promptWarn('请选择正确的日期');
                return;
            }
        }
        $(this).parents(".Order_hc_ss_d_ul").find("li.checked").removeClass('checked');
        $(this).addClass('checked');
        $('#returnDate-' + module).val(dateStr);
        $("#span-returnDate").text(dateStr);
        $('#return_date').html(dateStr);
        $("#Order_fj_list_ul_back li").remove();
        findTrafficPrice(false, true);
    });
}

function setConditionClick() {
    $("#conditions_type li a").click(typeFilter($(this).html()));//火车车型
    $("#conditions_company li a").click(companyFilter($(this).html()));//飞机航空公司
    $("#conditions_seat li a").click(seatFilter($(this).html()));//座位舱位
    $("#conditions_leavePort li a").click(portFilter($(this).html()));//起飞站点/机场
    $("#conditions_arrivePort li a").click(portFilter($(this).html()));//火车到达站点
    $("#conditions_time li a").click(leaveTimeFilter($(this).html()));//飞机起飞时间
    $("#conditions_leaveTime li a").click(leaveTimeFilter($(this).html()));//火车出发时间
    $("#conditions_arriveTime li a").click(arriveTimeFilter($(this).html()));//火车到达时间

}
function commonInit() {
    setCitysClick();
    setHuanClick();
}

function setHuanClick() {
    $(".huan").click(
        function () {
            for (var i = 1; i < 4; i++) {
                var leaveCity = $("#leaveCityName-" + i).attr("data-areaId");
                var leavePort = $("#leaveCityName-" + i).attr("data-portId");
                var leaveCityName = $("#leaveCityName-" + i).val();
                var arriveCity = $("#arriveCityName-" + i).attr("data-areaId");
                var arrivePort = $("#arriveCityName-" + i).attr("data-portId");
                var arriveCityName = $("#arriveCityName-" + i).val();

                $("#leaveCityName-" + i).val(arriveCityName);
                $("#leaveCityName-" + i).attr("data-areaId", arriveCity);
                $("#leaveCityName-" + i).attr("data-portId", arrivePort);
                $("#arriveCityName-" + i).val(leaveCityName);
                $("#arriveCityName-" + i).attr("data-areaId", leaveCity);
                $("#arriveCityName-" + i).attr("data-portId", leavePort);
            }
        }
    );
}

function searching(go, back) {
    //console.info('searching:' + go + ',' + back);
    var cond = '<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>';
    $('#conditions_leavePort').html(cond);
    $('#conditions_company').html(cond);
    loading(go, back);
}

function searchingWithReturn(go, back) {
    var cond = '<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>';
    $('#conditions_leavePort').html(cond);
    $('#conditions_company').html(cond);
    $('#conditions_return_company').html(cond);
    $('#conditions_return_leavePort').html(cond);
    loading(go, back);
}
function loading(go, back) {
    //console.info('loading');
    $('#flightSearch').hide();
    //$('#nav').hide();
    $('#bottom').hide();
    $('#history').hide();
    //console.info(go + ',' + back);
    if (go) {
        $('#leave').hide();
        $('#noResultLeave').hide();
        $('#loadingLeave').show();
    }
    if (back) {
        $('#return').hide();
        $('#noResultReturn').hide();
        $('#loadingReturn').show();
    }

}
function searchSuccess(module, category) {
    $('#flightSearch').show();
    $('#nav').show();
    $('#bottom').show();
    $('#history').show();
    //console.info('search success' + category)
    //var moveXXX = function () {
    //    var myTop = $(document).height() - $(window).scrollTop();
    //    if (myTop <= 0) {
    //        $(".Order_hc_bottom").removeClass("Order_hc_fix");
    //    } else {
    //        $(".Order_hc_bottom").addClass("Order_hc_fix");
    //    }
    //}
    $(".Order_hc_bottom").addClass("Order_hc_fix");
    if (category == 2) {
        $('#loadingReturn').hide();
        $('#noResultReturn').hide();
        $('#return').show();
    } else {
        $('#loadingLeave').hide();
        $('#noResultLeave').hide();
        $('#leave').show();
    }
    selectTicket('available-check', 'Order_hc_list_ul');
    //$(window).bind("scroll", moveXXX);
    return;
}

function searchFaild(module, category) {
    //if (module == 1) {
    //    Common.failCount = 2;
    //    processResult();
    //} else {
    //    Common.completeCount++;
    //    Common.failCount++;
    //    if (Common.completeCount == 2) {
    //        processResult();
    //    }
    //}
    //console.info('search fail' + category)
    $('#flightSearch').show();
    //$('#nav').hide();
    $('#bottom').hide();
    $('#history').hide();
    if (category == 2) {
        $('#loadingReturn').hide();
        $('#return').hide();
        $('#noResultReturn').show();
    } else {
        $('#loadingLeave').hide();
        $('#leave').hide();
        $('#noResultLeave').show();
    }
    return;
}

//function processResult() {
//    if (Common.failCount == 2) {
//        $('#flightSearch').show();
//        $('#loadingLeave').hide();
//        $('#nav').hide();
//        $('#bottom').hide();
//        $('#noResulst').show();
//        return;
//    } else {
//        //console.info('success');
//        $('#flightSearch').show();
//        $('#loadingLeave').hide();
//        $('#nav').show();
//        $('#bottom').show();
//        $('#noResulst').hide();
//        var moveXXX = function () {
//            var myTop = $(document).height() - $(window).scrollTop();
//            if (myTop <= 0) {
//                $(".Order_hc_bottom").removeClass("Order_hc_fix");
//            } else {
//                $(".Order_hc_bottom").addClass("Order_hc_fix");
//            }
//        }
//        $(window).bind("scroll", moveXXX);
//        return;
//    }
//
//}
$(commonInit());

function changeModule(module) {
    //
    $(".flightForm").hide();
    $(".trainForm").hide();
    $("#searchForm-" + module).show();
}

var Common = {
    completeCount: 0,
    failCount: 0,
    successCount: 0,
}

function submitTrafficForm(module) {
    var type;
    if ($("#searchForm-" + module).hasClass("flightForm")) {
        type = 2;
    }
    if ($("#searchForm-" + module).hasClass("trainForm")) {
        type = 1;
    }
    var leaveCity = getCityAndPortId($("#leaveCityName-" + module).val(), type);
    if (leaveCity[0] > 0) {
        $("#leaveCity-" + module).val(leaveCity[0]);
        $("#leavePort-" + module).val(leaveCity[1]);
    }
    var arriveCity = getCityAndPortId($("#arriveCityName-" + module).val(), type);
    if (arriveCity[0] > 0) {
        $("#arriveCity-" + module).val(arriveCity[0]);
        $("#arrivePort-" + module).val(arriveCity[1]);
    }
    if (module == 3) {
        var transitCity = getCityAndPortId($("#transitCityName").val(), type);
        if (transitCity[0] > 0) {
            $("#transitCity").val(transitCity[0]);
            $("#transitPort").val(transitCity[1]);
        }
    }
    if (checkValidate(module)) {
        $("#searchForm-" + module).submit();
    }
}

function checkValidate(module) {
    var leaveCity = $("#leaveCity-" + module).val();
    if ($('#leavePort-' + module).val() == '' && leaveCity == "") {
        $("#leaveCityName-" + module + "").next(".categories_Addmore2").show();
        promptMessage('请选择出发城市');
        return false;
    }
    var arriveCity = $("#arriveCity-" + module).val();
    if ($('#arrivePort-' + module).val() == '' && arriveCity == "") {
        $("#arriveCityName-" + module + "").next(".categories_Addmore2").show();
        promptMessage('请选择到达城市');
        return false;
    }
    var leaveDate = $("#leaveDate-" + module).val();
    if (leaveDate == null || leaveDate == "") {
        $("#leaveDate-" + module).focus();
        promptMessage('请选择出发日期');
        return false;
    }

    if (module > 1) {
        var returnDate = $("#returnDate-" + module).val();
        if (returnDate == null || returnDate == "") {
            $("#returnDate-" + module).focus();
            promptWarn('请选择返回或第二程出发日期');
            return false;
        }
        if (module == 3) {
            var transitCity = $("#transitCity").val();
            if (transitCity == null || transitCity == "") {
                $("#transitCityName").next(".categories_Addmore2").show();
                promptWarn('请选择中转城市');
                return false;
            }
        }
    }
    return true;
}

function moreFlight() {
    $(".Order_fj_list_ul .shouqi").unbind('click');
    $(".Order_hc_list_ul_hide .shouqi").unbind('click');
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
            $(this).removeClass("c hecked").removeAttr("data-staute");
        }
    });


    //收起
    $(".Order_hc_list_ul_hide .shouqi").click(function () {

        $(this).closest(".Order_hc_list_ul_hide").prev(".Order_hc_list_ul_div").find("div span.more").removeClass("checked").removeAttr("data-staute");
        $(this).closest(".Order_hc_list_ul_hide").fadeOut();
    });
}

function selectTicket(checkId, listId) {
    //
    if ($("#" + checkId).hasClass("checked")) {
        //
        allTicket(listId);
    } else {
        //
        available(listId);
    }
}
function available(listId) {
    var list = $("#" + listId).find("li");
    for (var i = 0; i < list.size(); i++) {
        var item = list.eq(i);
        if (item.text().indexOf("无票") > 0) {
            item.addClass("display-none");
        }
        if (item.find("dd").size() == 0) {
            item.addClass("display-none");
        }

    }
}
function allTicket(listId) {
    var list = $("#" + listId).find("li").removeClass("display-none");
}


function processFlight(s) {
    var priceList = s.prices;
    var minPrice = 99999;
    var sortLeave = "";
    var sortArrive = "";
    for (var j = 0; j < priceList.length; j++) {
        var singlePrice = priceList[j];
        if (minPrice > singlePrice.price) {
            minPrice = singlePrice.price;
        }
        sortLeave = new Date(singlePrice.leaveTime.time).format("yyyy-MM-dd HH:mm");
        sortArrive = new Date(singlePrice.arriveTime.time).format("yyyy-MM-dd HH:mm");
    }
    s.sortLeave = sortLeave;
    s.sortArrive = sortArrive;
    s.sortPrice = minPrice;
    s.sortTime = s.flightTime;
    if (priceList != null && priceList != undefined && priceList.length > 0) {
        s.additionalFuelTax = priceList[0].additionalFuelTax;
        s.airportBuildFee = priceList[0].airportBuildFee;
    }
}

function extractKey(inputName, city1, city2, type, date) {
    if (date.indexOf('-') != -1) {
        date = date.replace(/-/g, '');
    }
    $('input[name="' + inputName + '"]').val(city1 + "##" + city2 + "##" + type + "##" + date);
}


function formToPlanPage(order) {
    var form = $(order).parents(".traffic_flight_train");
    var checked = JSON.parse(getUnCodedCookie("booking-checked"));
    if (isNull(checked)) {
        checked = [];
    }
    checked.push(form.find("[name=code]").val());
    setUnCodedCookie("booking-checked", JSON.stringify(checked));
    var jsonHidden = "<input name='json' type='hidden' value='" + $("#json").val() + "'/>";
    form.append(jsonHidden);
    var typeHidden = "<input name='type' type='hidden' value='" + $("#trafficType").val() + "'/>";
    form.append(typeHidden);
    var cityHidden = "<input name='city' type='hidden' value='" + $("#city").val() + "'/>";
    form.append(cityHidden);
    var firstLeave = "<input type='hidden' name='firstLeave' value='" + $("#firstLeave").val() + "'/>"
    form.append(firstLeave);
    var secondLeave = "<input type='hidden' name='secondLeave' value='" + $("#secondLeave").val() + "'/>"
    form.append(secondLeave);
    var leaveDate = $(order).parents("li").find("[name='sortLeave']").eq(0).val();
    var newLeave = "<input type='hidden' name='newLeave' value='" + leaveDate + "'/>"
    form.append(newLeave);
    form.submit();
    loadingBegin();
}

$(document).ready(function () {
   $(".o_select input").bind('keypress',function(event) {
       if (event.keyCode == "13") {
           $(this).parents("form").find(".submitSearch").click();
       }
   });
});



