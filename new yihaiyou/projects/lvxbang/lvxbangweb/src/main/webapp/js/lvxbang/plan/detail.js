//var mapModule = new MapModule();
$(function () {
    $(".buy-plan").click(function () {
        if($('#completeFlag').val() == 'false'){
           deleteWarn2("您还未添加交通和酒店,立即去添加?", fn1, null, fn2,null);
        }else{
            var startDate = new Date(Date.parse($('#startDate').val().replace("-", "/")));
            var now = new Date();
            if (now.getTime() - startDate.getTime() > 24 * 60 * 60 * 1000) {
                deleteWarn("出行日期已过，是否重新安排？", fn1, null, 1);
            }else{
                $("#booking-order").submit();
            }
        }
       return;
    });
    collect(".d_collect", true, ".collectNum");

    $(".d_stroke").on("click", function () {
        if (has_no_User(quotePlan)) {
            return;
        }
        quotePlan();
    });

    function quotePlan() {
        $.getJSON("/lvxbang/plan/quoteFromPlan.jhtml", {planId: $(".d_stroke").data("id")}, function (result) {
            if (result.success) {
                $.each(result.data.data, function (index, data) {
                    data.city.id = parseInt(data.city.id / 100) * 100;
                });
                FloatEditor.optimizeResult = result.data;
                FloatEditor.renderPage("/lvxbang/plan/edit.jhtml");
                FloatEditor.saveCookie();
                promptMessage("复制成功");
                $(this).addClass("checked");
            }
            else {
                promptWarn(result.errorMsg);
                $(this).removeClass("checked");
            }
        });
    }

    $(".detail_top .name_div").css("width", $(".detail_top .name").width());
    $(".detail_top .change_name").css("left", parseInt($(".detail_top .name").width()) + 60 + "px");
    $(".detail_top .name_button").css("left", parseInt($(".detail_top .name").width()) + 65 + "px");

    $(".detail_top .change_name").on("click", function () {
        $(this).hide();
        $(".detail_top .name").hide();
        $(".detail_top .name_input").val($(".detail_top .name").text());
        $(".detail_top .name_div").css("width", $(".detail_top .name").width()).show();
        $(".detail_top .name_button").css("left", parseInt($(".detail_top .name").width()) + 65 + "px").show();
    });

    $(".detail_top .name_button_ok").on("click", function () {
        $.post("/lvxbang/plan/updateName.jhtml", {planId: $(".d_stroke").data("id"), planName:$(".detail_top .name_input").val()});
        $(".detail_top .name_div").hide();
        $(".detail_top .name_button").hide();
        $(".detail_top .name").text($(".detail_top .name_input").val()).show();
        $(".detail_top .change_name").css("left", parseInt($(".detail_top .name").width()) + 60 + "px").show();
    });

    $(".detail_top .name_button_cancle").on("click", function () {
        $(".detail_top .name_div").hide();
        $(".detail_top .name_button").hide();
        $(".detail_top .name").show();
        $(".detail_top .change_name").show();
    });

    $(".d_stroke").hover(function () {
        $(this).find("div").show();
    }, function () {
        $(this).find("div").hide();
    });

    $(".general_dl dd:gt(2)").hide();

    $(".general .more").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).html("<i></i>收起");
            $(".general_dl dd:gt(2)").show(500);
            $(this).attr("data-staute", "1");
        } else {
            $(this).html("<i></i>展开");
            $(".general_dl dd:gt(2)").hide(500);
            $(this).removeAttr("data-staute");
        }
    });

    //字数
    $(".textarea").keyup(function () {
        var numx = $(this).attr("mname");
        var len = $(this).val().length;
        if (len > numx - 1) {
            $(this).val($(this).val().substring(0, numx));
        }
        var num = numx - len;
        $(this).next().find(".word").text(num);
    });

    //栏目滚动
    $(".d_select_d dd").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        var height = $("#id" + ($(this).index() + 1)).offset().top - 55;
        $('html,body').animate({"scrollTop": height}, 500);
    });

    //天数
    $(".day_dl dd").click(function () {
        if ($(this).hasClass('first')) {
            $(".general_dl dd:gt(2)").show(500);
            $(".general .more").html("<i></i>收起").attr("data-staute", "1");
        }
        $(this).addClass("checked").siblings().removeClass("checked");
        var heightx = $("#idx" + ($(this).index() + 1)).offset().top - 75;
        $('html,body').animate({"scrollTop": heightx}, 500);
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
            $('.d_select_d dd.checked').removeClass('checked')
            $span.addClass("checked")
        }

        var totalx = $('.day_dl').children().length;
        var $label;
        for (var y = totalx - 1; y > 0; y--) {
            if (scrollTop >= $('#idx' + y).offset().top - 140) {
                $label = $('.label' + y);
                break;
            }
        }

        //如果找不到,选第一个
        if (!$label) {
            $label = $('.label1');
        }

        if (!$label.hasClass('checked')) {
            $('.day_dl dd.checked').removeClass('checked')
            $label.addClass("checked")
        }

    };


    $(window).bind("scroll", scrollHandler);

    $(".scenic-li").each(function () {
        if ($.trim($(this).text()).length > 100) {
            toggleText(this)
        }
    });

    //$('.scenic-li').delegate('.more', 'click', function () {
    //    var p = $(this).parent();
    //    $(this).remove();
    //    toggleText(p)
    //
    //});
    $('.scenic-li').hover(function () {
        toggleText($(this));
    }, function () {
        toggleText($(this));
    });


//收起
//    $('.scenic-li').delegate('.collapse', 'click', function () {
//        var p = $(this).parent();
//        $(this).remove();
//        toggleText(p)
//    });

    //var tripNode = $(".trip-node");

    //mapModule.initMap("dituContent", tripNode.eq(0).attr("data-lng"), tripNode.eq(0).attr("data-lat"), 13);
    //activeDayMap(0);

    initMap();

    //当鼠标移动到简介时显示全部
    //$('.plan_is_hover').mouseover(function(){
    //    $(this).find('div').eq(0).css('display','none');
    //    $(this).find('.description').css('display','block');
    //});
    //
    //$('.plan_is_hover').mouseout(function(){
    //    $(this).find('.description').css('display','none');
    //    $(this).find('div').eq(0).css('display','block');
    //});
    var code = $("#code").val();
    qrcode(code);
});

function initMap() {
    LvxbangMap.init();
    $(".day-node").each(function (day) {
        var index = 1;
        $(this).find(".trip-node").each(function () {
            var scenic = {};
            scenic.day = day + 1;
            scenic.index = index;
            scenic.lng = $(this).attr("lng");
            scenic.lat = $(this).attr("lat");
            scenic.id = $(this).attr("tid");
            scenic.name = $(this).attr("tname");
            scenic.address = $(this).attr("taddress");
            scenic.price = $(this).attr("tprice");
            scenic.comment = $(this).attr("tcomment");
            scenic.cover = $(this).attr("tcover");
            scenic.star = $(this).attr("tstar");
            LvxbangMap.addAScenic(scenic, day);
            if ($(this).index() == 0) {
                LvxbangMap.location(scenic.lng, scenic.lat, 13)
            }
            index += 1;
        });
    });
    LvxbangMap.drawLines();
    LvxbangMap.showAllLine();
    var dayPanel = $(".hqxqdtx_fr");
    dayPanel.find(".alltravel").click(function () {
        dayPanel.find("li").removeClass("checked");
        dayPanel.find(".alltravel").addClass("checked");
        LvxbangMap.showAllLine();
    });
    dayPanel.find("li").click(function () {
        dayPanel.find(".alltravel").removeClass("checked");
        dayPanel.find("li").removeClass("checked");
        $(this).addClass("checked");
        LvxbangMap.showDay($(this).index() + 1);
    });
    dayPanel.find(".plan-map-scenic").click(function () {
        var id = $(this).data("id");
        LvxbangMap.showAScenic(id);
    });
    $(".xcxq").find(".today-map").click(function () {
        LvxbangMap.toBigger();
        dayPanel.find("li").eq($(this).data("day") - 1).click();
    });
    LvxbangMap.bindEvent();
}

function toggleText(it) {
    var $this = $(it);
    //如果数据被保存起来了,则表示要显示所有文字
    if ($this.data('text')) {
        $this.html($this.data('text'));
        //删除完整文字
        $this.removeData('text')
    } else {
        var text = $.trim($this.text());
        $this.html(text.substring(0, 100) + "…");
        //把完整文字保存起来
        $this.data('text', text);
    }
}

//function activeDayMap(day)
//{
//    var dayNode = $(".day-node").eq(day);
//    var layer = {};
//    layer.points = [];
//    layer.lines = [];
//
//    dayNode.find(".trip-node").each(function () {
//        var point = new Point($(this));
//        layer.points.push(point);
//    });
//    // 前一天取消高亮
//    if (day > 0)
//    {
//        mapModule.unactiveDay(parseInt(day) - 1);
//    }
//
//    // 绘制当天景点、和直线
//    mapModule.updateDay(layer, day);
//    mapModule.drawDayLine(day);
//    // 高亮当天
//    mapModule.activeDay(day);
//}

function fn1(){

    $.getJSON("/lvxbang/plan/detailJson.jhtml", {planId: $("#planId").val()}, function (result) {
        var plan = {};
        plan.planDayList = [];
        var cityCount = {};
        $.each(result.data, function (index, day) {
            if (day.tripList.length == 0) {
                return;
            }
            if (cityCount[day.city.id] == null){
                cityCount[day.city.id] = 0;
            }
            cityCount[day.city.id]++;
        });
        $.each(result.data, function (index, day) {
            if (day.tripList.length==0) {
                return;
            }
            var planDay = {};
            planDay.city = {
                id: day.city.id,
                count: cityCount[day.city.id],
                name: day.city.name
            };
            planDay.planTripList = [];
            $.each(day.tripList, function (tripIndex, trip) {
                var planTrip = {
                    tripType: trip.type,
                    scenicInfo: {
                        id: trip.id,
                        ranking: trip.ranking,
                        score: trip.score,
                        name: trip.name
                    }
                };
                planDay.planTripList.push(planTrip);
            });
            plan.planDayList.push(planDay);
        });
        $("#booking-form-json").val(JSON.stringify(plan));
        $("#booking-form").submit();
    });

    //window.location.href = "/lvxbang/plan/booking.jhtml?planId="+$('#planId').val()+"&newOne=true";
}
function fn2(){
    $("#booking-order").submit();
}

function qrcode(url) {
    $("#toMobile").qrcode({
        render: "canvas", //table方式
        width: 183, //宽度
        height: 183, //高度
        text: url //任意内容
    });
}