/**
 * Created by zzl on 2017/3/9.
 */
var CommentData = {
    CommentPager: null,
    LoadNum: 0,
    init: function () {
        CommentData.initComment();
        CommentData.initEvt();
        CommentData.initComment();
    },
    initComp: function() {
    },
    initEvt: function () {
    },
    initComment: function() {
        var productId = $('#productId').val();
        var proType = $('#proType').val();
        if (productId == null || productId == "" || proType == null || proType == "") {
            return;
        }
        var pager = {
            countUrl: '/yhypc/comment/countDetailComment.jhtml?productId=' + productId + '&proType=' + proType,
            resultCountFn: function(result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/comment/getDetailComment.jhtml',
                    data: data,
                    progress: true,
                    success: function(result) {
                        if (CommentData.LoadNum > 1) {
                            $('body,html').animate({scrollTop: $('#comment_area').offset().top - 20}, 100);
                        }
                        if (result.success) {
                            $('#pro_commentBox').empty();
                            $.each(result.commentVos, function(i, c) {
                                if (i == 0) {
                                    $('div.commend div.desc p.word').html(null).html(c.content);
                                }
                                var comment_item = template('pro_comment_item', c);
                                var $comment_item = $(comment_item);
                                if (c.replyCommentVos != null) {
                                    $.each(c.replyCommentVos, function(j, rc) {
                                        var reply_comment_item = template('pro_reply_comment_item', rc);
                                        $comment_item.find('div.com_theWord').append(reply_comment_item);
                                    })
                                }
                                $('#pro_commentBox').append($comment_item);
                            });
                        }
                        CommentData.LoadNum++;
                    }
                });
            }
        };
        CommentData.CommentPager = new Pager(pager);
        var commentReq = {productId: productId, proType: proType};
        CommentData.CommentPager.init(commentReq);
    }
};
$(function () {
    CommentData.init();
});