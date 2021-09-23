$(function () {

    $(".sec-tab").find("li").click(function () {
        $(".help-nav").find("li").removeClass("active");
        $(this).addClass("active");
        $(this).parents(".nav").addClass("active");

        $(".help-cont").find("h1").html($(this).text());

        var nav_index = $(this).parents(".nav").index();
        var sec_index = $(this).index();
        var prev_nav_count = 0;
        for (var i = 0; i < nav_index; i++) {
            prev_nav_count = prev_nav_count + $(".nav").eq(i).find(".sec-tab").find("li").length;
        }
        var index = prev_nav_count + sec_index + 1;

        $(".help-cont").find(".tab-cont").addClass("hide");
        $(".help-cont").find(".tab-cont-" + index).removeClass("hide");

        var browser = navigator.appName;
        var b_version = navigator.appVersion;
        var version = b_version.split(";");
        if (browser != "Microsoft Internet Explorer" || version.length < 2 || version[1].replace(/[ ]/g, "") != "MSIE8.0") {
            window.history.replaceState(null, null, "?page=" + $(this).prop("id"));//IE8不支持
        }

        //$("body").animate({scrollTop: 500}, 600);
        var title = $(this).text();
        if (title != "意见反馈") {
            $('#content').show();
            $.ajax({
                url: "/lvxbang/help/dataListBykeyword.jhtml",
                type: "post",
                dataType: "json",
                data: {
                    'keyword': title
                },
                success: function (data) {
                    $('#content').html(data.content);
                    window.console.log("success:" + data.status);
                },
                error: function (data) {
                    $('#content').html("暂无内容");
                    window.console.log("error:" + data.status);
                }
            });
        } else {
            $('#content').hide();
        }
    });

    //如果page在1-13中
//	if( 0 < $("#J_page").val() < 14)
//	{
//		var page = "#" + $("#J_page").val();
//		$(page).click();
//	}

    renderAdvicePage(0);

    $("#advice-submit").click(function () {
        var content = $("#advice_content").val();
        if (content.length < 10) {
            promptWarn('请至少输入10个字');
            return false;
        }
        if (content.length > 2000) {
            promptWarn('太长了受不了啦(╯`□′)╯┻━┻');
            return false;
        }

        submitAdvice(content);
    });

    $("#advice_content").keyup(function () {
        var maxChars = 2000;//最多字符数
        var content = $("#advice_content").val();
        if (content.length < maxChars) $("#word_count").css("color", "#999999");
        if (content.length > maxChars) $("#word_count").css("color", "#FF0000");
        $("#word_count").text(content.length);
    });

    $(".sec-tab").find("li.active").click();
});

function submitAdvice(content) {
    $.ajax({
        url: "/lvxbang/feedback/saveMyFeedbak.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'feedback.content': content
        },
        success: function (result) {
            if (result.success) {
                promptWarn("意见反馈成功", 1000);
                $("textarea[name='content']").val("");
                $("#word_count").text("0");
            } else {
                promptWarn("反馈失败, 请稍候重试!", 1000);
            }
        },
        error: function(result) {
            promptWarn("反馈失败, 请稍候重试!", 1000);
        }
    });
    //$.post("/lvxbang/advice/save.jhtml?random=" + new Date().getTime(), {content: content}, function (result) {
    //    if (result == "success") {
    //        promptWarn("反馈成功", 1000);
    //        $("textarea[name='content']").val("");
    //        $("#word_count").text("0");
    //
    //        renderAdvicePage(0);
    //
    //    } else {
    //        promptWarn("意见反馈失败，请稍后再试");
    //    }
    //});
    //return false;
}

function renderAdvicePage(page) {
    $.getJSON("/lvxbang/advice/countAdvice.jhtml?random=" + new Date().getTime(), function (result) {
        if (!result.success) {
            return;
        }
        // 收藏评论分页
        $("#advice-page-list").pagination(result.count,
            {
                link_to: "javascript:;",
                items_per_page: 6,
                current_page: page,
                num_display_entries: 5,
                callback: loadAdvicePage
            });

    });
}

function loadAdvicePage(page) {
    $.getJSON("/lvxbang/advice/getAdvice.jhtml?random=" + new Date().getTime(),
        {
            "pageSize": 6,
            "pageNo": (page + 1)
        },
        function (result) {
            var adviceList = $("#advice-list");
            if (result.length == 0) {
                adviceList.find("li").remove();
                return;
            }
            adviceList.find("li").remove();
            for (var i = 0; i < result.length; i++) {
                var myLi = $(template("tpl_advice-list", result[i]));
                adviceList.append(myLi);
            }
        });
}

//$(".sec-tab").find("li").click(function(){
//
//});
