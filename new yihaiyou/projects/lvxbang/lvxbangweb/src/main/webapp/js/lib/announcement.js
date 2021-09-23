$(document).ready(function () {
    //栏目选中
    var nowPage = $("body").attr("myname");
    var navigation = $(".header_nav .menu_panel .menu_list li");
    navigation.removeClass("selected");
    if (nowPage) {
        navigation.filter('[data-tab=' + nowPage + ']').addClass("selected");
    }

    //图片延迟加载
    $("img").lazyload({
        effect: "fadeIn"
    });

    //默认值
    $('.input').placeholder({
        isUseSpan: true
    });
    $(".mailTablePlan").find("input").placeholder({
        placeholderColor: "#666",
        isUseSpan: true
    });

    //搜索框
    $(".categories").each(function () {
        var category = $(this);
        category.find(".input").keyup(function (e) {
            // 阻止冒泡
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            if (e.keyCode == 38 || e.keyCode == 40) {	// 38向上，40向下
                return;
            }
            var $input = $(".categories_div.checked li.checked").closest(".categories_div").siblings(".input");
            var keyword = $.trim($(this).val());
            if ($("body").hasClass("Attractions_List")) {
                var thia = $('#scenicName');
                if($.trim(thia.val()).length > 0){
                    thia.next().css("display",'block');
                }else{
                    thia.next().css("display",'none');
                }
            }
            if (keyword.length == 0) {

                return;
            }
            //var regex = /[a-zA-Z]+/;
            //if (regex.test(keyword)) {
            //    return;
            //}
            if (e.keyCode == 13) {	// 回车时判断是否是头部搜索框，是直接跳转
                var headsearch = $(this).attr('data-headsearch');
                if (headsearch) {
                    var label = category.find('.categories_div.checked li.checked label').text();
                    if (!label) {
                        label = $.trim($(this).val());
                    }
                    $(this).val(label);
                    $('#headerSearch-form').submit();
                    return;
                } else {
                    return;
                }
            }
            var da = {};
            da['name'] = keyword;
            if ($("body").hasClass("Attractions_List")) {
                var regex = /[a-zA-Z]+/;
                if (!regex.test(keyword)) {
                    var cityIds = new Array();
                    var n = 0;
                    $("#destination .checkbox").each(function () {
                        if ($(this).hasClass("checked")) {
                            cityIds[n] = $(this).attr("data-id");
                            n++;
                        }
                    });

                    for (var i = 0; i < cityIds.length; i++) {
                        da['scenicRequest.cityIds[' + i + ']'] = cityIds[i];
                    }
                }


            }

            if ($("body").hasClass("food_List")) {
                var regex = /[a-zA-Z]+/;
                if (!regex.test(keyword)) {
                    var cityIds = [];
                    var i=0;
                    $("#destination").find(".checkbox").each(function( e) {
                        if($(this).hasClass("checked")) {
                            da['delicacyRequest.cityIds['+i+']'] = Number($(this).data("id"));
                            i++;
                        }
                    });
                }
            }

            $.post(category.attr("data-url"), da, function (result) {
                category.find("ul").html("");
                if (result.length > 0) {
                    var html = "";
                    if(window.console && console.log) {
                        window.console.log(result);
                    }
                    $.each(result, function (i, data) {
                        data.key = data.name.replace(keyword, "<strong>" + keyword + "</strong>");
                        html += template("tpl-suggest-item", data);
                    });
                    //result.forEach(function (data) {
                    //
                    //});
                    category.find("ul").html("").append(html);
                    category.find(".suggest-item").click(function () {
                        category.find(".input").val($(this).attr("data-text"));
                        category.find(".input").focus();
                    });
                    category.find(".cuowu").hide();
                    category.find(".cuowu").removeClass('checked');
                    category.find(".KeywordTips").addClass('checked').show();
                } else {
                    category.find(".KeywordTips").hide();
                    category.find(".KeywordTips").removeClass('checked');
                    category.find(".cuowu").addClass('checked').show();
                }
            }, "json");

        });
    });


    //首页回车键进行搜索
    $(function () {
        $('#txtSearch').bind('keypress', function (event) {
            if (event.keyCode == "13") {
                if (!isNull($('#txtSearch').val())) {
                    headerSearch();
                    $('#txtSearch').next().hide();
                }
            }
        });
    });


    $(document).on("click", function () {
        $(".categories_div").hide();
    });

    //以下内容暂时没有使用，将在确认后删除，若确认有用，就移到上面

    //选项卡
    $(".mailTab li").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).closest("div").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
    });

    //选中
    //$(".choose").click(function(){
    //  var choose = $(this).attr("data-staute");
    //  if(!choose){
    //	  $(this).addClass("checked").attr("data-staute","1");
    //  }else{
    //	  $(this).removeClass("checked").removeAttr("data-staute");
    //    }
    //});


    //栏目加浮动
    if ($('#nav').length > 0) {
        var navbar = function () {
            var navbar_top = $(window).scrollTop();
            var height = $("#nav").offset().top;
            if (navbar_top >= height) {
                $(".nav").addClass("fixed");
            }
            if (navbar_top <= height) {
                $(".nav").removeClass("fixed");
            }
        }
        $(window).bind("scroll", navbar);
    }

    /*20160107*/
    function hidex() {
        $(".categories_Addmore2,.time_xc,.cuowu,.categories_div,.KeywordTips,.hotellevel_div").hide();
    }

    //选项卡
    $(".mailTab li").click(function () {
        hidex();
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).closest("div").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
    })

    //首页目的地选项卡
    $(".Addmore_dl dt li").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).closest(".Addmore_dl").find("dd").eq($(this).index()).show().siblings("dd").hide();
    })

    //关闭
    $(".Addmore .close").click(function () {
        $(this).parent().hide();
    })


    //搜索框
    $(".categories .input").click(function () {
        $(".categories_div").removeClass("checked");
        hidex();
    }).keydown(function () {
        $(".categories_div").hide();
        $(this).next(".categories_div").addClass("checked").show();
    });

    //点击clickinput
    $(".clickinput").click(function () {
        $(".categories_div").removeClass("checked");
        hidex();
        $(this).siblings(".categories_Addmore2").show();
    }).keyup(function (e) {
        // 查询后台
        if (e.keyCode == 13) {
            if ($("#scenic-form").find(".categories_div").hasClass("checked")) {
                $("#scenic-form").find(".categories_div li").eq(0).click();
            }
        }
        if (e.keyCode == 13 || e.keyCode == 38 || e.keyCode == 40) {	// 38向上，40向下
            return;
        }
        var $thiz = $(this);
        $thiz.attr("data-areaId", "");  // 先清空data-areaId值
        $thiz.attr("data-portId", "");  // 先清空data-portId值
        var keyword = $.trim($thiz.val());
        if (keyword.length == 0) {
            return;
        }
        var multi = $thiz.attr('data-multi');  // 检查是否是多选框，取分号后的字符串
        if (multi) {
            keyword = getSemicolonAfterStr(keyword);
            if (!keyword) {
                return;
            }
        }
        //var regex = /[a-zA-Z]+/;
        //if (regex.test(keyword)) {
        //    return;
        //}
        var da = {};
        da['name'] = keyword;

        var regex = /[a-zA-Z]+/;
        if ($("body").hasClass("hotels_List") || $("body").find('.togetherCityName')) {
            if (!regex.test(keyword)) {
                if(!isNull($('.togetherCityName').val())){
                    var city = $(".togetherCityName").val();
                    var cityId = getCityId(city);
                    if (cityId != 0) {
                        da['cityId'] = getCityId($('.togetherCityName').val());
                    }
                }
            }
        }



        $.post($thiz.attr("data-url"), da, function (result) {
            var ul = $thiz.siblings(".KeywordTips").find("ul");
            ul.html("");
            if (result.length > 0) {
                var html = "";
                $.each(result, function (i, data) {
                    data.key = data.name.replace(keyword, "<strong>" + keyword + "</strong>");
                    html += '<li data-id="' + data.id + '"><label class="fl">' + data.key + '</label></li>';
                });
                ul.append(html);
                $(".categories_div.checked li").hover(function () {
                    $(".categories_div.checked li").removeClass("checked");
                    $(this).addClass("checked");
                });
                ul.find("li").click(function (e) {
                    var areaId = $(this).data("id");
                    var label = $(this).text();
                    // 定制需求目的控件需要，参见页面：WEB-INF/jsp/lvxbang/customRequire/fill.jsp
                    var customRequire = $thiz.attr('data-custom-require');
                    if (customRequire) {
                        addDestion(areaId, label);   // 添加目的地
                        $thiz.val('');
                    } else {
                        if($thiz.attr("id")=="input_planId") {
                            if ($('#keyStatus').val()=='13') {
                                $('#keyStatus').val('click');
                            } else {
                                $thiz.val(addSemicolon($thiz.val(), $(this).text()));
                            }
                        } else {
                            $thiz.val(label);
                        }
                        //$thiz.attr("data-areaid", areaId);
                        if ($thiz.parents(".hotels_List").length > 0 && $("#city").length > 0) {
                            $("#city").val($(this).data("id"));
                        }
                    }
                    $thiz.attr("data-areaid", areaId);
                    $thiz.change();
                    $thiz.focus();


                });
                $thiz.siblings(".categories_Addmore2").hide();
                $thiz.siblings(".cuowu").hide();
                $thiz.siblings(".Addmore").hide();
                $thiz.siblings(".KeywordTips").addClass("checked").show();
                //thiz.siblings(".KeywordTips").find(".suggest-item").click(function () {
                //   $(this).siblings(".KeywordTips").find(".input").val($(this).attr("data-text"));
                //});
            } else {
                $thiz.siblings(".categories_Addmore2").hide();
                $thiz.siblings(".KeywordTips").addClass("checked").hide();
                $thiz.siblings(".cuowu").show();
            }
            $thiz.siblings(".des_main").hide();
        },"json");

    });

    // 搜索提示下拉框点击事件
    $(".clickinput .categories_div").delegate("li", "click", function () {
        var label = $("label", this).text();
        var $input = $(this).parents(".categories_div").siblings(".input");
        var multi = $input.attr('data-multi');  // 检查是否是多选框，如果是分号拼接
        if (multi) {
            var str = addSemicolon($input.val(), label);
            $input.val(str);
        } else {
            $input.val(label);
        }
        var areaId = $(this).attr("data-id");
        $input.attr("data-areaId", areaId);  // 赋值data-areaId
        $input.attr("data-portId", "");  // 赋值data-areaId
        if ($input.attr("id") == "transitCityName") {
            $("#transitCityName-2").val($input.val());
        }
        $input.focus();
        hidex();
    });

    //点击portinput
    $(".portinput").click(function () {
        $(".categories_div").removeClass("checked");
        hidex();
        $(this).siblings(".categories_Addmore2").show();
    }).keyup(function (e) {
        // 查询后台
        if (e.keyCode == 13) {
            if ($("#scenic-form").find(".categories_div").hasClass("checked")) {
                $("#scenic-form").find(".categories_div li").eq(0).click();
            }
        }
        if (e.keyCode == 13 || e.keyCode == 38 || e.keyCode == 40) {	// 38向上，40向下
            return;
        }
        var $thiz = $(this);
        $thiz.attr("data-portId", "");  // 先清空data-portId值
        $thiz.attr("data-areaId", "");  // 先清空data-areaId值
        var keyword = $.trim($thiz.val());
        if (keyword.length == 0) {
            return;
        }
        var multi = $thiz.attr('data-multi');  // 检查是否是多选框，取分号后的字符串
        if (multi) {
            keyword = getSemicolonAfterStr(keyword);
            if (!keyword) {
                return;
            }
        }
        $.post($thiz.attr("data-url"), {name: keyword}, function (result) {
            var ul = $thiz.siblings(".KeywordTips").find("ul");
            ul.html("");
            // console.info("result.length=" + result.length);
            var existsCityName = "";
            if (result.length > 0) {
                var html = "";
                $.each(result, function (i, data) {
                    var siteName = data.name;
                    if (data.type == 1) {
                        siteName = siteName + "站";
                    }
                    if (data.type == 2) {
                        if (data.cityName.indexOf(keyword) != -1) {
                            if (existsCityName.indexOf(data.cityName + ",") != -1)
                                return;
                            existsCityName += data.cityName + ",";
                            siteName = data.cityName;
                        } else {
                            siteName = data.cityName + "&nbsp;" + siteName;
                        }
                    }
                    siteName = siteName.replace(keyword, "<strong>" + keyword + "</strong>");
                    html += '<li class="portTip" data-areaId="' + data.cityId + '" data-portId="' + data.code + '"><label class="fl">' + siteName + '</label></li>';
                });
                ul.append(html);
                $(".categories_div.checked li").hover(function () {
                    $(".categories_div.checked li").removeClass("checked");
                    $(this).addClass("checked");
                });
                $thiz.siblings(".categories_Addmore2").hide();
                $thiz.siblings(".cuowu").hide();
                $thiz.siblings(".KeywordTips").addClass("checked").show();
            } else {
                $thiz.siblings(".categories_Addmore2").hide();
                $thiz.siblings(".KeywordTips").addClass("checked").hide();
                $thiz.siblings(".cuowu").show();
                // $.post(
                //     "/lvxbang/destination/getAreaIds.jhtml",
                //     {"nameStr": keyword},
                //     function (result) {
                //         var html = "";
                //         result.forEach(function (data) {
                //             var siteName = data.name.replace(keyword, "<strong>" + keyword + "</strong>");
                //             html += '<li class="portTip" data-areaId="' + data.id + '" data-portId=""><label class="fl">' + siteName + '</label></li>';
                //         });
                //         ul.append(html);
                //         $(".categories_div.checked li").hover(function () {
                //             $(".categories_div.checked li").removeClass("checked");
                //             $(this).addClass("checked");
                //         });
                //         $thiz.siblings(".categories_Addmore2").hide();
                //         $thiz.siblings(".cuowu").hide();
                //         $thiz.siblings(".KeywordTips").addClass("checked").show();
                //     }
                // );
            }

            // 搜索提示下拉框点击事件
            $(".portTip").click(function () {
                var label = $("label", this).text();
                var $input = $(this).parents(".categories_div").siblings(".input");
                var multi = $input.attr('data-multi');  // 检查是否是多选框，如果是分号拼接
                if (multi) {
                    var str = addSemicolon($input.val(), label);
                    $input.val(str);
                } else {
                    $input.val(label);
                }
                $input.attr("data-areaId", $(this).attr("data-areaId"));
                $input.attr("data-portId", $(this).attr("data-portId"));  // 赋值data-areaId
                if ($input.attr("id") == "transitCityName") {
                    $("#transitCityName-2").val($input.val());
                }
                $input.focus();
                hidex();
            });
        },"json");

    });


    //酒店级别
    $(".hotellevel .input").click(function () {
        hidex();
        $(this).siblings(".hotellevel_div").show();
    });

    $(".hotellevel_div li").click(function () {
        var label = $("label", this).text();
        $(this).parents(".hotellevel_div").siblings(".input").val(label);
        var hotelStar = $(this).attr('value');
        $(this).parents(".hotellevel_div").siblings(".hotelStar").val(hotelStar);
        $(".hotellevel_div").hide();
    });

    // 点击城市面板点击事件
    $(".Addmore_dl dd .Addmore_nr li").click(function () {
        var $input = $(this).parents(".Addmore").hide().prev("input");
        var areaId = $(this).attr("data-id");
        var areaName = $(this).children('a').text();
        // 定制需求目的控件需要，参见页面：WEB-INF/jsp/lvxbang/customRequire/fill.jsp
        var customRequire = $input.attr('data-custom-require');
        if (customRequire) {
            addDestion(areaId, areaName);   // 添加目的地
            $input.val('');
        } else {
            var multi = $input.attr('data-multi');  // 检查是否是多选框，如果是分号拼接
            if (multi) {
                //if ($(this).hasClass("checked")) {
                //    return;
                //}
                var tempValue = $input.val();
                tempValue = SearcherBtn.replaceChar(tempValue);
                var tempArr = tempValue.split("；");
                var newArr = SearcherBtn.unique(SearcherBtn.trimArr(tempArr));
                var span = $("a", this).text();
                var flag = false;
                $.each(newArr, function (i, data) {
                    if (data == span) {
                        flag = true;
                        return false;
                    }
                });
                if (flag) {
                    $(this).addClass("checked");
                    return;
                }
                var str = addSemicolon($input.val(), $("a", this).text());
                $input.val(str);
            } else {
                $(this).parents(".Addmore_dl dd").find(".Addmore_nr li").removeClass("checked")
                $input.val($("a", this).text());
            }
            $(this).addClass("checked");
        }
        $input.attr("data-areaId", areaId);    // 赋值data-areaId
        $input.attr("data-portId", "");    // 赋值data-areaId
        $input.data("areaid", $(this).data("id"));    // 赋值data-areaId
        if ($input.attr("id") == "transitCityName") {
            $("#transitCityName-2").val($input.val());
        }
        $input.change();
        $input.focus();
    });


    //in_time
    $(".in_time").click(function () {
        hidex();
        $(this).siblings(".time_xc").show();
    });


    $('.categories  .input,.in_time,.time_xc,.Order_hc_ss .calendar,.o_select_fl .o_select_input,.hotellevel').on('click', function (event) {
        // 阻止冒泡
        if (event.stopPropagation) {    // standard
            event.stopPropagation();
        } else {
            // IE6-8
            event.cancelBubble = true;
        }
    });

    $(document).on("click", function () {
        $(".categories_div").removeClass("checked");
        $(".categories_div,.time_xc,.Order_hc_ss .calendar .cdar,.o_select_fl .o_select_input .categories_div,.hotellevel_div").hide();
    });

    //点击clickinput
    $(".o_select_fl .categories").click(function () {
        $(".categories_div", this).show();
    })
    $(".o_select_fl .categories .input").click(function () {
        $(this).next(".categories_div").show();
    })


    //头部小图标
    $(".nav_top_p a").hover(function () {
        $(this).prev("s").addClass("checked");
    }, function () {
        $(this).prev("s").removeClass("checked");
    });


    //上下回车键
    document.onkeydown = nextpage;
    function nextpage(event) {

        $(".categories_div.checked li,.hotellevel_div li").hover(function () {
                $(".categories_div.checked li,.hotellevel_div li").removeClass("checked");
                $(this).addClass("checked");
        });

        var $cate = $(".categories_div");
        if($('body').hasClass('write_recommendPlan')){
            $(".categories_div").each(function(){
                if($(this).hasClass('checked')){
                    $cate = $(this);
                }
            });
        }
        event = event ? event : (window.event ? window.event : null);
        if (event.keyCode == 13) { // 搜索提示下拉框回车事件
            if ($cate.hasClass("checked")) {
                var $input = $(".categories_div.checked li.checked").closest(".categories_div").siblings(".input");
                var label = $(".categories_div.checked li.checked label").text();
                var areaId = $(".categories_div.checked li.checked").attr("data-id");
                // 定制需求目的控件需要，参见页面：WEB-INF/jsp/lvxbang/customRequire/fill.jsp
                var customRequire = $input.attr('data-custom-require');
                if (customRequire) {
                    addDestion(areaId, label);   // 添加目的地
                    $input.val('');
                    $input.attr("data-areaId", areaId);
                } else {
                    var multi = $input.attr('data-multi');  // 检查是否是多选框，如果是分号拼接
                    if (multi) {
                        var str = addSemicolon($input.val(), label);
                        $input.val(str);
                        $("#keyStatus").val('13');

                    } else {
                        if($('body').hasClass('Attractions')){
                            $('#scenicName').val(label);
                        }else if($('body').hasClass('Food')){
                            $('#delicacyName').val(label);
                        }else if($('body').hasClass('write_recommendPlan')){
                            $(".categories_div.checked li.checked").closest(".categories_div").siblings("p").find('.input').val(label);
                        } else{
                            $input.val(label);
                        }
                    }
                    if (!isNull($input.attr('class')) && $input.attr('class').indexOf('port') > 0) {
                        $input.attr("data-portId", areaId);
                        $input.attr("data-areaId", 0);
                    }
                    else {
                        $input.attr("data-areaId", areaId);
                        if ($input.parents(".hotels_List").length > 0 && $("#city").length > 0) {
                            $("#city").val(areaId);
                        }
                        $input.attr("data-portId", "");
                    }
                    if ($input.attr("id") == "transitCityName") {
                        $("#transitCityName-2").val($input.val());
                    }
                }
                $input.change();
                $(".categories_div").hide();

            }

        }

        if (event.keyCode == 38) {//向上
            if ($cate.hasClass("checked")) {
                $(".categories_div.checked").show();
                if ($(".categories_div.checked li").hasClass("checked")) {
                    if (!$(".categories_div.checked li:first").hasClass("checked")) {
                        $(".categories_div.checked li.checked").removeClass('checked').prev("li").addClass("checked");
                    }
                } else {
                    $(".categories_div.checked li:first").addClass("checked");
                }
            }
        }

        if (event.keyCode == 40) {//向下
            if ($cate.hasClass("checked")) {
                $(".categories_div.checked").show();
                if ($(".categories_div.checked li").hasClass("checked")) {
                    if (!$(".categories_div.checked li:last").hasClass("checked")) {
                        $(".categories_div.checked li.checked").removeClass('checked').next("li").addClass("checked");
                    }
                } else {
                    $(".categories_div.checked li:first").addClass("checked");
                }
            }
        }
    }

    // 导航栏事件
    $('.header_nav .menu_panel .menu_list li.hasSubNav').hover(
        function() {
            $(this).addClass('cui_nav_o');
            $('#subnav_wrap_bg').show();
        },
        function() {
            $(this).removeClass('cui_nav_o');
            $('#subnav_wrap_bg').hide();
        }
    );

    relocationFoot();
    setInterval(function () {
        relocationFoot();
    }, 100);

    //列表导航栏
    $("#topContainer .list_top_menu .moreTopnav").hover(function () {
        $(this).addClass("cur");
    }, function () {
        $(this).removeClass("cur");
    });
});

function relocationFoot() {
    if ($('.nextpage').height() > 0) {
        if (($("body").height() - $('.nextpage').height()) < $(window).height() - 80) {
            $(".foot").addClass("stay-bottom");
        } else {
            $(".foot").removeClass("stay-bottom");
        }
    } else {

        if ($("body").height() < $(window).height() - 80) {
            $(".foot").addClass("stay-bottom");
        } else {
            $(".foot").removeClass("stay-bottom");
        }
    }
}

//判断用户有没有登录
function has_no_User(fn) {
    if (checkLoginbyajax()) {
        //promptMessage("用户未登陆");
        //setTimeout(function () {
        login_popup(fn);
        //}, 1500);//延迟1秒
        return true;
    }
    return false;

}
function has_no_User2(url) {
    var fn = function () {
        window.location.href = url;
    }
    if (checkLoginbyajax()) {
        //promptMessage("用户未登陆");
        //setTimeout(function () {
        login_popup(fn);
        //}, 1500);//延迟1秒
        return false;
    }
    return true;

}
//从后台判断用户是否登录
function checkLoginbyajax() {
    var islogin;
    $.ajax({
        url: "/lvxbang/user/checkedLogin.jhtml",
        type: "post",
        async: false,
        dataType: 'json',
        success: function (data) {
            if (!data[0].success) {
                islogin = true;
                return islogin;
            } else {

                //$('#logoutStatus').remove();
                //var re = '<div class="Haslogged fr" id="loginStatus">'+
                //	'<a class="type  fl posiR" href="/lvxbang/message/system.jhtml" id="myMessage">我的消息</a>'+
                //	'<a href="/lvxbang/user/index.jhtml" class="name fl" id="userMessage"'+
                //	'value="'+data[0].userName+'">'+data[0].userName+' </a>'+
                //	'<a href="/lvxbang/login/exitLogin.jhtml" class="fr but">退出</a></div>'
                //var res = template("tpl-has-user-item", data[0].userName);
                //$('#head_p').after(re);

                islogin = !data[0].success;
                return islogin;
            }

        },
        error: function () {
            //window.console.log("error");
        }
    });
    return islogin;
}
//根据页面判断以跳转形式登录还是弹窗式登录
function login_According_url() {
    var url = document.URL.split("/")[document.URL.split("/").length - 1];
    if ("booking.jhtml" == url.toString()) {
        login_popup();
        return false;
    } else {
        return true;
    }
}

// 用分号拼接字符串，如果前面有字符且不为空直接替换为“xxx;”，如果有分号直接加上
function addSemicolon(str, label) {
    if (!str || $.trim(str).length <= 0) {
        return label + '；';
    }
    str = str.substr(0, lastSemicolonIndex(str) + 1);
    return str + label + '；';
}

// 获取分号后的字符串
function getSemicolonAfterStr(str) {
    if (!str || $.trim(str).length <= 0) {
        return null;
    }
    str = str.substr(lastSemicolonIndex(str) + 1);
    return str;
}

// 获取最后分号的位置
function lastSemicolonIndex(str) {
    if (!str || $.trim(str).length <= 0) {
        return -1;
    }
    var index1 = str.lastIndexOf(';');
    var index2 = str.lastIndexOf('；');
    if (index1 < 0 && index1 == index2) {
        return -1;
    }
    if (index1 > index2) {
        return index1;
    } else {
        return index2;
    }
}

//懒加载后重新计算图片宽高
function resizePic(count, settings) {
    var img = new Image();
    img.src = $(this).data("original");
    var width = img.width;
    var height = img.height;
    if (width / height > this.width / this.height) {
        this.style.width = "auto";
        this.style.height = "100%";
    } else if (width / height < this.width / this.height) {
        this.style.width = "100%";
        this.style.height = "auto";
    } else {
        this.style.height = "100%";
        this.style.width = "100%";
    }
    img = null;

}

//日历显示
$(document).ready(function () {
    $(".time_ico.in_time").click(function () {
        $(this).next().focus();
        $(this).next().click();
    });
});

//首页酒店回车事件
$(function () {
    $('#hotelLeaveCity,#sygjc').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            SearcherBtn.btnHotelSeach();
            //if(!isNull($('#scenicName').val())){
            //    scenicList();
            //    $('#scenicName').next().hide();
            //}
        }
    });
});