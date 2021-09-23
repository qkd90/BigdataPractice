var RecommendPlanDetail = {
    pager: null,
    init: function() {
        $(".content_right .moreyouji ul li:last-child").css({"border":"none"});
        RecommendPlanDetail.getCommentInfo();
        RecommendPlanDetail.createPager();
        RecommendPlanDetail.commentList();
        RecommendPlanDetail.initSubmitComment();
        RecommendPlanDetail.initFavorite();

    },


    initFavorite: function() {
        var url = "/yhypc/favorite/doCheckFavorite.jhtml";
        var data = {
            favoriteId: $("#recommendPlanId").val(),
            favoriteType: 'recplan'
        }

        $.ajax({
            url: url,
            data: data,
            progress: false,
            success: function(data) {
                if (data.favorite) {
                    $("#collection").addClass("collection");
                } else {
                    $("#collection").removeClass("collection");
                }
            },
            error: function() {}
        });

        $("#collection").on("click", function() { /*收藏   心形icon*/
            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    doFavorite();
                } else {
                    YhyUser.goLogin(doFavorite);
                }
            });

            function doFavorite() {
                var url = "/yhypc/favorite/favorite.jhtml";
                var data = {
                    favoriteId: $("#recommendPlanId").val(),
                    favoriteType: 'recplan'
                }
                $.ajax({
                    url: url,
                    data: data,
                    progress: true,
                    success: function(data) {
                        var scnum = Number($(".scnum").html());
                        if (data.favorite) {
                            $(".scnum").html(scnum + 1);
                            $("#collection").addClass("collection");
                        } else {
                            $(".scnum").html(scnum - 1);
                            $("#collection").removeClass("collection");
                        }
                    },
                    error: function() {}
                });
            }

        });
    },

    initSubmitComment: function() {

        $(".tijiaopl").on("click", function(){

            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    doSubmitComment();
                } else {
                    YhyUser.goLogin(doSubmitComment);
                }
            });

            function doSubmitComment() {
                if($(".tianxiepl").val()) {
                    var url = "/yhypc/comment/saveRePlanComment.jhtml";
                    var data = {
                        'comment.targetId':$("#hid-recommendPlan-id").val(),
                        'comment.type':'recplan',
                        'comment.content':$(".tianxiepl").val()
                    }
                    $.post(url, data, function(data) {
                        if (data.success) {
                            RecommendPlanDetail.getCommentInfo();
                            RecommendPlanDetail.commentList();
                            $(".tianxiepl").val("");
                        }
                        $.message.alert({
                            title: "提示",
                            info: data.msg
                        });
                    });
                } else {
                    $.message.alert({
                        title: "提示",
                        info: "评论不能为空"
                    });
                }
            }
        });
    },
    getCommentInfo: function() {
        $.post("/yhypc/recommendPlan/getCommentInfo.jhtml",
            {
                recommendPlanId: $("#hid-recommendPlan-id").val()
            },
            function (data) {
                if (data.success) {
                    //$("#nav-comment-span").html(data.commentCount);

                    $(".plnum").html(data.commentCount);
                    $(".comment-details-header").empty();
                    template.config("escape", false);
                    var result2 = $(template("tpl-recommendPlan-detail-comment-info", data));
                    $(".comment-details-header").append(result2);
                    result2.data("commentInfo", data);

                }
            }
            ,"json");
    },
    commentList: function () {
        var search = {};
        search['recommendPlanId'] = $("#hid-recommendPlan-id").val();
        RecommendPlanDetail.pager.init(search);
    },


    createPager: function() {
        var options = {
            countUrl: "/yhypc/recommendPlan/getTotalCommentPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                $(".comment-details-body").empty();
                //$("#loading").show();
                //scroll(0, 0);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.post("/yhypc/recommendPlan/getCommentList.jhtml",
                    data,
                    function (data) {
                        $(".comment-details-body").empty();
                        var commentList = data.commentList;
                        if (commentList.length > 0) {
                            for (var i = 0; i < commentList.length; i++) {
                                var s = commentList[i];
                                if(parseInt(s.id) < 2000433){
                                    s.shortComment = formatString(s.shortComment);
                                }
                                template.config("escape", false);
                                var result = $(template("tpl-recommendPlan-detail-comment-list", s));
                                $(".comment-details-body").append(result);
                                result.data("comment", s);
                            }
                        } else {
                            $(".m-pager").hide();
                            //$("#commentList").after();
                        }
                    }
                    ,"json");
            }
        };
        RecommendPlanDetail.pager = new Pager(options);
    }



};

$(document).ready(function(){
    RecommendPlanDetail.init();
});

function setShowLength(obj, maxlength, id)
{
    var rem = maxlength - obj.value.length;
    var ree=obj.value.length;
    var wid = id;
    if (rem < 0){
        rem = 0;
    }
    document.getElementById(wid).innerHTML = ree +"&nbsp;/&nbsp;" +  rem ;
}
	