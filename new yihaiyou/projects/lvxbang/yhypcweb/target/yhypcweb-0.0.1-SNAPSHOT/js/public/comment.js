/**
 * Created by zzl on 2017/2/5.
 */
var Comment = {
    init: function() {
        Comment.initComp();
        Comment.initEvt();
    },
    initComp: function() {
        var scrolltop = 0;
        Comment.centerbox('.commentContain',scrolltop);
        Comment.centerbox('.comsuccess',scrolltop);
        $(window).scroll(function(){
            var scrolltop = $(this).scrollTop();
            Comment.centerbox('.commentContain',scrolltop);
            Comment.centerbox('.comsuccess',scrolltop);
        })
    },
    initEvt: function() {
        Comment.comment();   //评价弹窗
        Comment.lookComment();   //评价弹窗
        Comment.sussbox();
        Comment.niming();
        Comment.starLevel();
        Comment.closeCommentBox('.com_close', '.commentContain', '.shadow');    //评价弹窗关闭
        Comment.closeCommentBox('#suss_close', '.comsuccess', '.shadow');    //评价成功弹窗关闭
    },
    centerbox: function (tag,s_top) {
        var left = (window.screen.availWidth - $(tag).width()) / 2;
        var top = (window.screen.availHeight - $(tag).height()) / 2 + s_top;
        $(tag).css({'left': left, 'top': top})
    },
    comment: function () {
        $('.commitBtn.commitComment').on('click', function () {
            var commitComment = $('#commitComment');
            var comment = {
                targetId: $(this).data("proId"),
                priceId: $(this).data("priceId"),
                type: $(this).data("proType"),
                orderDetailId: $(this).data("detailId")
            };
            commitComment.find(".com_submit").data("comment", comment);
            commitComment.find(".mess_name").text($(this).data("name"));
            var type = "";
            switch (comment.type) {
                case "scenic":
                    type = "景点门票";
                    break;
                case "sailboat":
                    type = "海上休闲";
                    break;
                case "hotel":
                    type = "酒店";
                    break;
            }
            commitComment.find(".commentType").text(type);
            commitComment.find(".commentCover").attr("src", $(this).data("cover"));
            commitComment.find(".mess_score .starnum").removeClass(function () {
                var str = "";
                for (var i = 1; i <= 5; i++) {
                    str += "mess_score0" + i + " ";
                }
                return str;
            }).data("star", 0);
            commitComment.find(".commentContent").val("");
            commitComment.show();
            $('.shadow').show();
            $('body').css({'overflow': 'hidden'});
        })
    },

    lookComment: function () {
        $(".commitBtn.lookComment").on("click", function () {
            $.post("/yhypc/comment/findComment.jhtml", {
                orderDetailId: $(this).data("detailId")
            }, function (data) {
                if (data.success) {
                    var lookComment = $("#lookComment");
                    lookComment.find(".commentName").text(data.comment.targetName);
                    var type = "";
                    switch (data.comment.productType) {
                        case "scenic":
                            type = "景点门票";
                            break;
                        case "sailboat":
                            type = "海上休闲";
                            break;
                        case "hotel":
                            type = "酒店";
                            break;
                    }
                    lookComment.find(".commentType").text(type);
                    lookComment.find(".commentCover").attr("src", data.comment.targetCover);
                    lookComment.find(".mess_score .starnum").addClass('mess_score0' + Math.round(data.comment.score / 20));
                    lookComment.find(".commentContent").val(data.comment.content);
                    lookComment.show();
                    $('.shadow').show();
                    $('body').css({'overflow': 'hidden'});
                } else {
                    $.message.alert({
                        title: "提示",
                        info: data.errMsg
                    });
                }
            });
        });
    },

    closeCommentBox: function (btn, tagBox, shadow) {
        $(document).on('click', btn, function () {
            $(shadow).hide();
            $(tagBox).hide();
            $('body').css({'overflow': 'auto'});
        });
    },

    sussbox: function () {
        $(document).on('click', '.com_submit', function () {
            var commitComment = $("#commitComment");
            var content = commitComment.find(".commentContent").val().trim();
            if (content == null || content == "") {
                $.message.alert({
                    title: "提示",
                    info: "请输入评价内容"
                });
                return;
            }
            var star = commitComment.find(".mess_score .starnum").data("star");
            if (star == 0) {
                $.message.alert({
                    title: "提示",
                    info: "请选择评分"
                });
                return;
            }
            var comment = $(this).data("comment");
            $.ajax({
                url: "/yhypc/comment/saveComment.jhtml",
                data: {
                    'comment.targetId': comment.targetId,
                    'comment.priceId': comment.priceId,
                    'comment.type': comment.type,
                    'comment.content': content,
                    orderDetailId: comment.orderDetailId,
                    score: star
                },
                progress: true,
                success: function (data) {
                    if (data.success) {
                        $(".commitComment.commitBtn[data-detail-id=" + comment.orderDetailId + "]").hide();
                        $(".lookComment.commitBtn[data-detail-id=" + comment.orderDetailId + "]").show();
                        $('.commentContain').hide();
                        $('.comsuccess').show();
                    }
                },
                dataType: "json"
            });
        });
    },

    niming: function () {
        $(document).on('click', '.maincontain .niming', function () {
            var state = $(this).hasClass('no_niming');
            if (state == false) {
                $(this).addClass('no_niming');
            } else {
                $(this).removeClass('no_niming');
            }
        });
    },

    starLevel: function () {
        $('#commitComment .mess_score .starnum span').on('click', function () {
            $(this).parent().removeClass(function () {
                var str = "";
                for (var i = 1; i <= 5; i++) {
                    str += "mess_score0" + i + " ";
                }
                return str;
                // .removeClass('mess_score01 mess_score02 mess_score03 mess_score04 mess_score05')
            });
            $(this).parent().addClass('mess_score0' + ($(this).index() + 1)).data("star", $(this).index() + 1);
        })
    }
};

$(document).ready(function () {
    Comment.init();
});