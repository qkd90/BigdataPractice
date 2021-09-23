$(function () {
    //点击浏览历史后图片加载出来
    $('#history').click(function () {
        $('.his').lazyload({
            effect: "fadeIn"
        });
    });

    $.ajax({
        url:"/lvxbang/destination/getIpCity.jhtml",
        type:"post",
        dataType:"json",
        data:{
        },
        success:function(data){
            var name = $('#flightLeaveCity').val();
            if(isNull(name)){
                $('#flightLeaveCity').val(data.name);
                $('#flightLeaveCity').attr("data-areaid",data.id);
            }
            var date = new Date();
            if(isNull($('#startDate').val())){
                //$('#fjcf').val(date.format("yyyy-MM-dd"));
                $('#startDate').val(date.format("yyyy-MM-dd"));
            }
            if(isNull($('#first_flight_date').val())){
                //$('#fjcf').val(date.format("yyyy-MM-dd"));
                $('#first_flight_date').val(date.format("yyyy-MM-dd"));
            }
            name = $('#trainLeaveCity').val();
            if(isNull(name)){
                $('#trainLeaveCity').val(data.name);
                $('#trainLeaveCity').attr("data-areaid",data.id);
            }

            if(isNull($('#trainLeaveDate').val())){
                //$('#hccf').val(date.format("yyyy-MM-dd"));
                $('#trainLeaveDate').val(date.format("yyyy-MM-dd"));
            }

            if(isNull($("#hotelLeaveCity").val())){
                $("#hotelLeaveCity").val(data.name);
                $('#hotelLeaveCity').attr("data-areaid",data.id);
                date.setDate(date.getDate()+1);
                $("#hotelStartDate").val(date.format("yyyy-MM-dd"));
                date.setDate(date.getDate()+1);
                $("#hotelEndDate").val(date.format("yyyy-MM-dd"));
            }
        }
    });

    $('.mailTablePlan.disn input').bind('keypress',function(event) {
        if (event.keyCode == "13") {
            $(this).parents(".mailTablePlan").find(".toSearch").click();
        }
    });

    //面板切换
    $(".J_SearchTab").click(function () {
        $(".J_panel").addClass("lxb_none");
        $(this).addClass("c_f_selected").siblings().removeClass("c_f_selected");
        $("#" + $(this).data("rel")).removeClass("lxb_none").parents(".J_CatalogRegular").removeClass("lxb_none");
    });

    //私人订制热门目的地
    $("#srdzHot a").click(function () {
        var city = $(this).text();
        var select = $("#input_planId").val();
        if (select.indexOf(city) < 0) {
            select += city + "；";
            $("#input_planId").val(select);
        }
    });

    $(".nav_tabs li").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
        var index = $(this).index();
        $(this).parent().next(".tab_infos").find(".linebox").eq(index).addClass("now").siblings().removeClass("now");
    });

    $("#mbdzjp .J_CatalogSecond, #gty .J_CatalogSecond").hover(function () {
        $(this).addClass("c_s_hover");
    }, function () {
        $(this).removeClass("c_s_hover");
    });

    //面板关闭按钮
    $(".J_catalogClose").click(function () {
        $(".J_SearchTab[data-rel=mbdzjp]").click();
    });

    $("#toSelfTour").click(function () {
        var cityId = getCityId($("#hotelLeaveCity").val());
        if (cityId == 0) {
            cityId = 350200;
        }
        var href = $(this).data("href");
        href = href.replace("cityId", cityId);
        location.href = href;
    });

    //广告
    //initSlide();
    //tagEvg();
    setInterval(function () {
        var ads = $("#focus .activity_images li");
        var now = ads.has(":visible");
        var next = now.next();
        if (now.index() == ads.length - 1) {
            next = ads.eq(0);
        }
        now.fadeOut(200, function () {
            next.show();
        });
    }, 5000);
});

function initSlide() {
    var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
    if ($("#focus .activity_images li:eq(0)").width() > $("#focus").width()) {
        sWidth = $("#focus .activity_images li:eq(0)").width();
    }
    var lis = $("#focus ul li");
    var len = lis.length; //获取焦点图个数
    var index = 0;
    var picTimer;

    //以下代码添加数字按钮和按钮后的半透明长条
    var btn = "<div class='btnBg'></div><div class='btn'>";
    for (var i = 0; i < len; i++) {
        btn += "<span>" + lis.eq(i).data("title") + "</span>";
    }
    btn += "</div>"
    $("#focus").append(btn);
    $("#focus .btnBg").css("opacity", 0.3);

    //为数字按钮添加鼠标滑入事件，以显示相应的内容
    $("#focus .btn span").mouseenter(function () {
        index = $("#focus .btn span").index(this);
        showPics(index);
    }).eq(0).trigger("mouseenter");

    //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
    $("#focus ul").css("width", sWidth * (len + 1));

    //鼠标滑入某li中的某div里，调整其同辈div元素的透明度，由于li的背景为黑色，所以会有变暗的效果
    $("#focus ul li div").hover(function () {
        $(this).siblings().css("opacity", 0.7);
    }, function () {
        $("#focus ul li div").css("opacity", 1);
    });

    //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
    $("#focus").hover(function () {
        clearInterval(picTimer);
    }, function () {
        if (!isAutoSlide()) {
            return;
        }
        picTimer = setInterval(function () {
            if (index == len) { //如果索引值等于li元素个数，说明最后一张图播放完毕，接下来要显示第一张图，即调用showFirPic()，然后将索引值清零
                showFirPic();
                index = 0;
            } else { //如果索引值不等于li元素个数，按普通状态切换，调用showPics()
                showPics(index);
            }
            index++;
        }, 3000); //此3000代表自动播放的间隔，单位：毫秒
    }).trigger("mouseleave");

    // 当只有一张图片的时候, 不自动滚动
    function isAutoSlide() {
        var slideList = $("#focus").find('li');
        return slideList.length > 1;
    }
    //显示图片函数，根据接收的index值显示相应的内容
    function showPics(index) {  //普通切换
        var nowLeft = -index * sWidth; //根据index值计算ul元素的left值
        $("#focus ul").stop(true, false).animate({"left": nowLeft}, 500); //通过animate()调整ul元素滚动到计算出的position
        $("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
    }

    //最后一张图自动切换到第一张图时专用
    function showFirPic() {
        $("#focus ul").append($("#focus ul li:first").clone());
        var nowLeft = -len * sWidth; //通过li元素个数计算ul元素的left值，也就是最后一个li元素的右边
        $("#focus ul").stop(true, false).animate({"left": nowLeft}, 500, function () {
            //通过callback，在动画结束后把ul元素重新定位到起点，然后删除最后一个复制过去的元素
            $("#focus ul").css("left", "0");
            $("#focus ul li:last").remove();
        });
        $("#focus .btn span").removeClass("on").eq(0).addClass("on"); //为第一个按钮添加选中的效果
    }
}
function tagEvg() {
    $('.horizontal').each(function () {
        var tab = $(this);
        var list = tab.find('ul').eq(0).addClass('mytab');
        var li = list.find('li');
        list.on('mouseover', 'li', function () {
            var now_li = $(this);
            var index = now_li.index();
            if (now_li.hasClass('on')) return;
            li.removeClass('on').eq(index).addClass('on');
            var now_box = now_li.parents('.horizontal').find('.linebox').removeClass('now').eq(index).addClass('now');
            now_box.find('img').lazyload({
                effect: "fadeIn"
            });
        });
    });
}
//点击非城市选择框范围时隐藏该框
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
});