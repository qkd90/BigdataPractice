/**
 * Created by vacuity on 16/1/19.
 */


/**
 * Created by vacuity on 16/1/13.
 */


var CommentList = {
    pager: null,
    data: {},
    categoryNum: 0,
    init: function () {
        CommentList.pager = new Pager(
            {
                pageSize: 5,
                countUrl: "/lvxbang/user/myCommentCount.jhtml",
                searchData: {},
                pageRenderFn: function (pageNo, pageSize) {
                    CommentList.data.pageNo = pageNo;
                    CommentList.data.pageSize = pageSize;
                    $.post("/lvxbang/user/getMyComments.jhtml", CommentList.data, function (result) {
                        var html = "";
                        $.each(result, function (i, data) {
                            html += template("tpl-comment-list-item", data);
                        });
                        $("#mycomment").html(html);
                        $(".comment_hover").hover(function () {
                            if($(this).find('p:eq(0)').hasClass('comment_cut')){
                                $(this).find('p').hide();
                                $(this).find(".comment_span").css("display","block");
                                $(this).next('a').hide();
                            }
                        }, function () {
                            if($(this).find('p:eq(0)').hasClass('comment_cut')) {
                                $(this).find(".comment_span").css("display", "none");
                                $(this).find('p').show();
                                $(this).next('a').show();
                            }
                        });
                    }, "json");
                }
            }
        );
        CommentList.refreshList();
    },

    refreshList: function () {
        CommentList.data = {};

        var commentType = "";
        switch (CommentList.categoryNum) {
            case 0:
                break;
            case 1:
                commentType = "scenic";
                break;
            case 2:
                commentType = "hotel";
                break;
            case 3:
                commentType = "delicacy";
                break;
            case 4:
                commentType = "recplan";
                break;
            default:
                break;
        }
        if (commentType != "") {
            CommentList.data["commentType"] = commentType;
        }

        CommentList.pager.init(CommentList.data);
    },

    changeCategory: function (num) {
        $(".comment-category").removeClass("checked");
        $("#comment-" + num).addClass("checked");
        CommentList.categoryNum = num;

        CommentList.refreshList();
    },

    deleteByIds: function (ids) {
        deleteWarn("确定删除该点评？", function () {
            $.post("/lvxbang/user/delComments.jhtml",
                {
                    delIds: ids
                },
                function (result) {
                    if (result.success) {
                        //
                        CommentList.refreshList();
                    } else {
                        promptWarn("删除失败");
                    }
                }, "json");
        });
    },

    delSelected: function () {
        var ids = "";
        var checkList = $(".comment-check");
        for (var i = 0; i < checkList.size(); i++) {
            var check = checkList.eq(i);
            if (check.hasClass("checked")) {
                ids += check.children(".commentId").val() + ",";
            }
        }
        if (ids == "") {
            promptWarn("请至少选择一项进行删除");
            return;
        }
        CommentList.deleteByIds(ids);
    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#comment-panel").addClass("checked");
    }

}


$(function () {
    CommentList.initPanel();
    CommentList.init();
});
