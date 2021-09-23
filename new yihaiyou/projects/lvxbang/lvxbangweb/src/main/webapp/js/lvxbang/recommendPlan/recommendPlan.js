/**
 * Created by zzl on 2015/12/31.
 */
var TravelNote = {
    init: function () {
        TravelNote.view();
        TravelNote.bindQuoteEvent();
        TravelNote.bindScrollEvent();
    },

    view: function () {
        var resObjectId = $("#recplanId").val();
        $.ajax({
            url: "/lvxbang/recommendPlan/addViewNum.jhtml",
            type: "post",
            dataType: "json",
            data: {
                'otherVisitHistory.path': '/lvxbang/recommendPlan/detail.jhtml',
                'otherVisitHistory.resObjectId': resObjectId,
                'otherVisitHistory.resType': 'recplan'
            },
            success: function (result) {
                $("#view_num").html('').html(result.viewNum);
                $("#quote_num").html('').html(result.quoteNum);
                $("#top_quote_num").html('').html(result.quoteNum);
            },
            error: function (result) {
                //console.log("error");
            }
        });
    },

    bindQuoteEvent: function () {
        $(".d_stroke").click(function () {
            var href = $('#plan_path').val();
            if (has_no_User(function () {
                    $.getJSON("/lvxbang/plan/quoteFromRecommend.jhtml", {planId: $("#recplanId").val()}, function (result) {
                        if (result.success) {
                            $.each(result.data.data, function (index, data) {
                                data.city.id = parseInt(data.city.id / 100) * 100;
                            });
                            FloatEditor.optimizeResult = result.data;
                            FloatEditor.failedScenic = result.failedScenic;
                            FloatEditor.renderPage(href + "/lvxbang/plan/edit.jhtml");
                            FloatEditor.saveCookie();
                            promptMessage("复制成功");
                            $(this).addClass("checked");
                        }
                        else {
                            promptWarn(result.errorMsg);
                            $(this).removeClass("checked");
                        }
                    })
                })) {
                return;
            }

            $.getJSON("/lvxbang/plan/quoteFromRecommend.jhtml", {planId: $("#recplanId").val()}, function (result) {
                if (result.success) {
                    $.each(result.data.data, function (index, data) {
                        data.city.id = parseInt(data.city.id / 100) * 100;
                    });
                    FloatEditor.optimizeResult = result.data;
                    FloatEditor.failedScenic = result.failedScenic;
                    FloatEditor.renderPage(href + "/lvxbang/plan/edit.jhtml");
                    FloatEditor.saveCookie();
                    promptMessage("复制成功");
                    $(this).addClass("checked");
                }
                else {
                    promptWarn(result.errorMsg);
                    $(this).removeClass("checked");
                }
            })
        });
    },

    bindScrollEvent: function () {
        var scrolloff = false;
        var rightNav = $(".travel_d_r_div");
        //右侧点击DT

        rightNav.delegate(".travel_d_r_dl dt", "click", function () {
            //scrolloff = true;
            $(this).closest('.travel_d_r_div').find('.travel_d_r_dl_d').removeClass("checked").removeAttr("data-staute"); //清空DD选择效果
            //var height = $(".travel_d_l_yj").eq($(this).index()).offset().top - 50;  //获取距离左侧高度
            //$('html,body').animate({scrollTop: height}, 1000, function () {//动画开始
            //    scrolloff = false
            //});

            var choose = $(this).parent(".travel_d_r_dl").hasClass("checked");
            if (!choose) {  //是否选中
                $(".travel_d_r_dl dd").slideUp();
                $(this).next().slideDown();
                $(this).parent(".travel_d_r_dl").addClass("checked").attr("data-staute", "1").siblings().removeClass('checked').removeAttr("data-staute");
            } else {
                $(this).next().slideUp(function () {
                    $(this).parent(".travel_d_r_dl").removeClass("checked").removeAttr("data-staute");
                });

            }

        });

        //右侧点击travel_d_r_dl_d
        rightNav.find(".travel_d_r_dl").each(function () {
            var day = $(this).index();
            $(this).delegate(".travel_d_r_dl_d", "click", function () {
                scrolloff = true;
                var index = $(this).index();
                var height = $(".travel_d_l_yj").eq(day).find(".travel_t").eq(index).offset().top - 55;
                $('body').animate({scrollTop: height}, 1000, function () {//动画开始
                    scrolloff = false
                });
                var choose = $(this).attr("data-staute");
                if (!choose) {
                    $(this).addClass("checked").attr("data-staute", "1").siblings().removeClass('checked').removeAttr("data-staute");
                } else {
                    $(this).removeClass("checked").removeAttr("data-staute");
                }

            });
        });

        //右侧滑动
        //$(".travel_d_r_dl dt").click(function () {
        //    $(".travel_d_r_dl").removeClass("checked");
        //    $(".travel_d_r_dl dd").slideUp();
        //    $(this).parent(".travel_d_r_dl").addClass("checked");
        //    $(this).next().slideToggle("hide");
        //});

        //栏目滚动
        $(".d_select_d dd").click(function () {
            $(this).addClass("checked").siblings().removeClass("checked");
            var height = $("#id" + ($(this).index() + 1)).offset().top - 55;
            $('html,body').animate({"scrollTop": height}, 1000);
        });

        $(".to_recplan_detail").click(function () {
            var height = $("#recplan_detail").offset().top - 55;
            $('html,body').animate({"scrollTop": height}, 1000);
        });
        //自动切换
        var scrollHandler = function () {
            var scrollTop = $(window).scrollTop();
            var topNav = $('.d_select_d');
            if (topNav.length > 0) {
                topNav.children().removeClass("checked");
                // 判断页面是否有评论区
                var $id2 = $('#id2');
                if ($id2.length > 0 && scrollTop >= $('#id2').offset().top - 55) {
                    topNav.children().eq(1).addClass("checked");
                } else {
                    topNav.children().eq(0).addClass("checked");
                }
            }

            //右侧大类
            var rightNavList = $(".travel_d_r_div").find(".travel_d_r_dl");
            var travelNoteList = $(".travel_d_l").find(".travel_d_l_yj");
            var located = false;
            travelNoteList.each(function (day) {
                $(this).find(".travel_t").each(function (i) {
                    var top = $(this).offset().top; //获取每个travel_t离顶部距离
                    if (scrollTop <= top) {
                        var navList = $(".travel_d_r_dl").eq(day).find(".travel_d_r_dl_d");
                        navList.removeClass("checked");
                        navList.eq(i).addClass("checked");
                        if (!rightNavList.eq(day).hasClass("checked")) {
                            rightNavList.each(function (i) {
                                if (i != day) {
                                    rightNavList.eq(i).removeClass('checked').removeAttr("data-staute");
                                    rightNavList.eq(i).find("dd").slideUp();
                                }
                            });
                            rightNavList.eq(day).addClass("checked").attr("data-staute", "1");
                            rightNavList.eq(day).find("dd").slideDown();

                        }
                        located = true;
                        return false;
                    }
                });
                return !located;
            });
        };

        $(window).bind("scroll", function () {
            scrollHandler();
        });
    }
};
