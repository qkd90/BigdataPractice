$(document).ready(function () {
    createMap();
    collect(".d_collect", true, ".collectNum");
    bindScenic();
});

function bindScenic() {
    var id = [];
    var scenicId = parseInt($("#scenicId").val());
    id.push(scenicId);
    $.getJSON("/lvxbang/scenic/getScenicBriefData.jhtml", {json: JSON.stringify(id)}, function (result) {
        $(".scenic-node").data("scenic", result[scenicId]);
    })
}

function createMap() {
    var map = new BMap.Map("mapContent", {enableMapClick: false});//在百度地图容器中创建一个地图
    var point = new BMap.Point($("#baiduLng").val(), $("#baiduLat").val());//定义一个中心点坐标
    map.centerAndZoom(point, 17);//设定地图的中心点和坐标并将地图显示在地图容器中
    map.enableScrollWheelZoom();//启用滚轮放大缩小
    window.map = map;//将map变量存储在全局
    var local = new BMap.LocalSearch(map, {
        renderOptions: {map: map}, pageCapacity: 1
    });
    local.search($("#scenicName").val());
}

$(document).ready(function () {

    $.post("/lvxbang/scenic/getTicketList.jhtml", {"scenicInfo.id": $("#scenicId").val()}, function (data) {
        var ticketList = data.ticketList;
        if (ticketList.length < 1) {
            $(".Attractions_ss").html("<span style='margin-left: 2em;font-size: 14px;color: #666'>未知</span>");
            $(".Attractions_ss").removeClass("Hotels_ss");
        }
        for (var i = 0; i < ticketList.length; i++) {
            var html = $(template("tpl-ticket-list-item", ticketList[i]));
            $("#ticketList").append(html);
        }
        if (data.lineList.length > 0) {
            var lineHtml = template("tpl-line", data);
            $("#ticketList").append(lineHtml);
        }

        $(".d_select_d").find(".checked").removeClass("checked");
        $(".d_select_d .span1").addClass("checked").show();
        $("#id1").show();

        if ($(".Attractions_dl dl").length > 3) {
            $("#ticketList").append('<p class="more textC posiA"><a href="javaScript:;">查看更多票价<i></i></a></p>');
        }
        $('.Attractions_dl dl p').each(function(){
            var h =  $(this).height();
            if(h == '30'){
                $(this).parents('dl').css("padding-top","5px");
                $(this).parents('dl').css("padding-bottom","5px");
            }
            if(h == '60'){
                $(this).parents('dl').find('.w3').css("margin-top","15px");
                $(this).parents('dl').find('.w4').css("margin-top","15px");
                $(this).parents('dl').find('.w5').css("margin-top","15px");
                $(this).parents('dl').find('a').css("margin-top","15px");
            }
        });
        $(".ft_list li:gt(2),.Attractions_dl dl:gt(2),.Attractions_Detail .Attractions_ss ul").hide();
        $(".Attractions_dl dl:lt(3)").parents("ul").show();
        $(".ft_list li:gt(2)").hide();

        //查看更多票价
        $(".Hotels_lb_cen .more").click(function () {
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                $(".Attractions_dl dl:gt(2)").slideToggle("hide");
                $(".Attractions_dl dl:gt(2)").parents("ul").show();
                $("a", this).html("收起<i></i>").addClass("checked");
                $(this).attr("data-staute", "1");
            } else {
                $(".Attractions_dl dl:gt(2)").slideUp();
                $(".Attractions_dl").parents("ul").hide();
                $(".Attractions_dl dl:lt(3)").parents("ul").show();
                $("a", this).html("查看更多票价<i></i>").removeClass("checked");

                $(this).removeAttr("data-staute");
            }
        });

        $(".Attractions_ss .Hotels_lb_cen .w2").hover(function () {
            $(this).find(".tishi3").show();
        }, function () {
            $(this).find(".tishi3").hide();
        });
    },"json");

    //选项卡
    $(".mailTab2 li").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).closest("div").find(".mailTablePlan2").eq($(this).index()).show().siblings(".mailTablePlan2").hide();
    });

    //图片遮罩层
    $(".mailTab2 li").hover(function () {
            if ($(this).hasClass("checked")) {
                $("p.posiA", this).hide();
            } else {
                $(this).css("padding-top", "8px");
                $("p.posiA", this).show();
            }
        },
        function () {
            $(this).css("padding-top", "9px");
            $("p.posiA", this).hide();
        });

    //右侧滑动
    $(".travel_d_r_dl dt").click(function () {
        $(".travel_d_r_dl").removeClass("checked");
        $(".travel_d_r_dl dd").slideUp();
        $(this).parent(".travel_d_r_dl").addClass("checked");
        $(this).next().slideToggle("hide");
    });


    //栏目滚动
    $(".d_select_d dd").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        var height = $("#id" + ($(this).index() + 1)).offset().top - 50;
        $('html,body').animate({"scrollTop": height}, 1000);
    })


    //自动切换
    var scrollHandler = function () {
        var scrollTop = $(window).scrollTop();

        var total = $('.d_select_d').children().length;
        var $span;
        for (var i = total; i > 0; i--) {
            if (scrollTop >= $('#id' + i).offset().top - 55) {
                $span = $('.span' + i);
                if (i == 1 && !$span.is(":visible")) {
                    $span = $(".span2");
                }
                break;
            }
        }

        //如果找不到,选第一个
        if (!$span) {
            $span = $('.span1');
        }

        if (!$span.hasClass('checked')) {
            $('dd.checked').removeClass('checked')
            $span.addClass("checked")
        }
    }
    $(window).bind("scroll", scrollHandler);


    $(".add_a").click(function () {
        var height = $("#mapContent").offset().top - 50;
        $('html,body').animate({"scrollTop": height}, 1000);
    });

    $(".is_hover").hover(function () {
        $(this).find("span").hide();
    }, function () {
        $(this).find("span").show();
    });

    //添加至路线
    //$('.add_line').click(function(){
    //
    //});
    $('.add_line').click(function () {
        var thiz = $(".stroke_open");
        if ($(thiz).hasClass("stroke_handDraw")) {
            return;
        }
        var stroke = $(thiz).attr("data-staute");
        if (!stroke) {

            $(".Itineraryfk").hide();
            if (FloatEditor.plan.scenicList.length == 0) {
                $(".Float_w_r").animate({"width": "240px"}, 300).attr("open_k", "1"); 	//右侧打开
            }
            $(".Add_attractions").show();//添加景点
            $(".Myitinerary").addClass("checked").attr("data-staute", "1");  //我的行程打开

            //判断浏览器版本
            var userAgent = navigator.userAgent.toLowerCase();
            jQuery.browser = {
                version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
                safari: /webkit/.test( userAgent ),
                opera: /opera/.test( userAgent ),
                msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
                mozilla: /mozilla/.test(userAgent)&&!/(compatible|webkit)/.test(userAgent)
            };

            //IE的话就不执行抛物线
            if(!jQuery.browser.msie){
                //alert(jQuery.browser.version);
                //alert(123);
                FloatEditor.flyIntoCart.apply(this, [event]);  //运行抛物线
            }

            $(thiz).addClass("checked").attr("data-staute", "1");  //加选中样式
            var spa = $(thiz).find("spa");
            if (!isNull(spa.html()) && spa.html().indexOf("+") == 0) {
                spa.html(spa.html().substring(1, spa.html().length));
            }
            var scenic = $(thiz).parents(".scenic-node").data("scenic");
            FloatEditor.addIntoCart(scenic);
            $(this).text("取消添加");
        } else {
            if(window.console && console.log) {
                window.console.log($(this).text());
            }
            var id = $(thiz).parents(".scenic-node").data("id");
            $(".Add_attractions_u .close").each(function () {
                if ($(this).data("id") == id) {
                    $(this).click();
                    return false;
                }
            });
            $(this).text("添加至线路");
        }

    });

    if ($(".stroke_open").hasClass("checked")) {
        $(".add_line").text("取消添加");
    }
});