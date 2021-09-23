$(function(){
    commentList();
    uploadPhoto();
});

$(function () {
    $(".add_comment").click(function () {
        var height = $("#add_comment").offset().top - 55;
        $('html,body').animate({"scrollTop": height}, 1000);
    });
});
//获取评论列表
function commentList(){
    var targetId = $('#targetId').val();
    var type = $('#targetId').attr('seftType');
    var options={
        countUrl: "/lvxbang/comment/count.jhtml",
        resultCountFn: function (result) {
            if (result[0] > 0) {
                $('#cc').parent().show();
                $('#cc').html(result[0]);
            }
            return parseInt(result[0])
        },
        pageRenderFn: function(pageNo, pageSize, data) {

            $.ajax({
                url:"/lvxbang/comment/list.jhtml",
                type:"post",
                dataType:"json",
                data:{
                    'comment.targetId':targetId,
                    'comment.type':type,
                    'pageNo': pageNo,
                    'pageSize': pageSize
                },
                success: function (data) {
                    //for(var i=0; i<data.length ; i++){
                    //    console.log(data[i]);
                    //}

                    $('#comment_ul').empty();

                    if (data.length == 0) {
                        $('.m-pager').prev().hide();
                        $('.m-pager').hide();
                        var result = template("tpl-no-comment-item", data.length);
                        $('#comment_ul').append(result);

                    } else {
                        if (data.length < 11) {
                            $('.m-pager').prev().hide();
                        }
                        if (data.length > 10) {
                            $('.m-pager').prev().show();
                        }
                        $('.m-pager').show();
                        for (var i = 0; i < data.length; i++) {
                        var s = data[i];
                        //console.log(s.createTime);
                        //s.createTime = new Date(s.createTime.time).format("yyyy-MM-dd HH:mm:ss");
                        //console.log(s.createTime);
                        var result = template("tpl-comment-item", s);
                        $('#comment_ul').append(result);
                        //alert($(".reply_list:eq("+i+") li").size());
                        if ($(".reply_list:eq(" + i + ") li").size() < 3) {
                            $(".reply_list:eq(" + i + ")").next("a").hide();
                        }
                    }
                    }
                    //隐藏回复
                    //$(".reply_list li:gt(1)").hide();
                    $(".reply_more").prev().find('li:gt(1)').hide();
                    $(".reply_more").click(function () {
                        var myStaute = $(this).attr("data-staute");
                        if (!myStaute) {
                            $(this).html("收起<i></i>").addClass("checked");
                            //$(".reply_list li:gt(1)").fadeToggle(1000);
                            $(this).prev().find('li:gt(1)').fadeToggle(1000);
                            $(this).attr("data-staute", "1");
                        } else {
                            $(this).html("查看更多回复<i></i>").removeClass("checked");
                            //$(".reply_list li:gt(1)").hide();
                            $(this).prev().find('li:gt(1)').hide();
                            $(this).removeAttr("data-staute");
                        }
                    });

                    //留言
                    //$(".reply").unbind("click").click(function(){
                    //    var myStaute = $(this).attr("data-staute");
                    //    if(!myStaute){
                    //        $(this).text("收起");
                    //        $(this).parent(".synopsis").siblings(".message").animate({"height":"130px"},600);
                    //        $(this).attr("data-staute","1");
                    //    }else{
                    //        $(this).text("回复");
                    //        $(this).parent(".synopsis").siblings(".message").animate({"height":"0px"},600);
                    //        $(this).removeAttr("data-staute");
                    //    }
                    //});

                    //留言
                    $(".reply").click(function(){
                        $('.message .oval4').removeClass("oval4");/*IE8*/
                        var myStaute = $(this).attr("data-staute");
                        var parentx=$(this).parent();
                        if(!myStaute){
                            $(this).text("收起").attr("data-staute","1");
                            if(parentx.is(".synopsis")){
                                parentx.parent('li').find(".message:eq(0)").animate({"height":"130px"},600).css("visibility","visible");
                            }else{
                                parentx.next(".message").animate({"height":"130px"},600).css("visibility","visible");
                            }

                        }else{
                            $(this).text("回复").removeAttr("data-staute");
                            if(parentx.is(".synopsis")){
                                parentx.parent('li').find(".message:eq(0)").animate({"height":"0px"},600).css("visibility","hidden");
                            }else{
                                parentx.next(".message").animate({"height":"0px"},600).css("visibility","hidden");
                            }
                        }
                        $('.message a').addClass("oval4");/*IE8*/
                    });

                    $(".message .but2").click(function(){
                        if($(this).parent('.message').prev('div').hasClass('color6')){
                            $(this).parent('.message').animate({"height":"0px"},600).css("visibility","hidden").prevAll('.synopsis').find(".reply").text("回复").removeAttr("data-staute");
                        }else{
                            $(this).parent('.message').animate({"height":"0px"},600).css("visibility","hidden").prev('div').find(".reply").text("回复").removeAttr("data-staute");
                        }
                    });

                    var scrolloff=false;

                    //图片懒加载
                    $("img").lazyload({
                        effect: "fadeIn"
                    });

                    //字数
                    $(".textarea").keyup(function () {
                        var numx = $(this).attr("mname");
                        //var regR = /\r/g;
                        var regN = /\n/g;
                        var len = $(this).val().replace(regN,"11").length;
                        var num = numx - len;
                        if (len > numx) {
                            $(this).val($(this).val().substring(0, numx));
                            num = 0;
                        }

                        $(this).next().find(".word").text(num);
                    }).keydown(function(e) {
                        if ( Number($(this).next().find(".word").text())==0) {
                            event.returnValue=false;
                            // IE6-8
                            //e.cancelBubble = true;
                        }
                    });

                },
                error:function(data){
                    //console.log(data);
                    //console.log("error");
                }
            });
        }
    };
    var pager = new Pager(options);
    pager.init({'comment.targetId':targetId,'comment.type':type});
}

//回复评论
function replyComment(id){
    //if ($('#userMessage').val() == undefined) {
    //    promptMessage("用户未登陆");
    //    return;
    //}
    //if (has_no_User()) {
    //    return;
    //}
    var targetId = $('#targetId').val();
    var type = $('#targetId').attr('seftType');
    var repliedId = id;
    var content = $.trim($('#reply_comment_' + repliedId).val());
    if (content == "") {
        promptWarn("回复不能为空", 1000);
        return;
    }
    $.ajax({
        url:"/lvxbang/comment/replyComment.jhtml",
        type:"post",
		dataType:"json",
        data:{
            'comment.content':content,
            'comment.targetId':targetId,
            'comment.type':type,
            'comment.repliedId':repliedId
        },
        success:function(data){
            //if(data.result =="nologin"){
            //    promptMessage("用户未登陆");
            //}
            if(data.msg =="回复成功") {
                promptMessage(data.msg, 1000);
                $('#reply_comment_' + repliedId).val('');
                commentList();
            }
        },
        error:function(data){
            //console.log(data);
        }
    });
}

//发表评论
function saveComment(){
    //if ($('#userMessage').val() == undefined) {
    //    //promptMessage("用户未登陆");
    //    login_popup();
    //   // return;
    //}
    //if (has_no_User()) {
    //    return;
    //}
    var data = {};
    var scores = new Array();
    scores[0] = $('#commentScoretypeId1').width()/35*20;
    scores[1] = $('#commentScoretypeId2').width();
    scores[2] = $('#commentScoretypeId3').width();
    scores[3] = $('#commentScoretypeId4').width();

    var ids = new Array();
    ids[0] = $('#commentScoretypeId1').attr('commentscoretypeid');
    ids[1] = $('#commentScoretypeId2').attr('commentscoretypeid');
    ids[2] = $('#commentScoretypeId3').attr('commentscoretypeid');
    ids[3] = $('#commentScoretypeId4').attr('commentscoretypeid');


    var targetId = $('#targetId').val();
    var type = $('#targetId').attr('seftType');
    var content = $.trim($('#comment').val());
        data['comment.targetId'] = targetId;
        data['comment.type'] = type;
        data['comment.content'] = content;
    if(content==""){
        promptWarn("评论不能为空", 1000);
        return;
    }

    for (var i = 0; i < scores.length; i++) {
        if (scores[i] == 0) {
            promptWarn("要给星星点赞哦！", 1000);
            return;
        }
        if (scores[i] != null) {
            data['comment.commentScores[' + i + '].score'] = scores[i];
            data['comment.commentScores[' + i + '].commentScoreType.id'] = ids[i];
        }
    }
    var i = 0;
    var imagePhotos = new Array();
    $(".image_photo").each(function (e) {
        //if($(this).hasClass("checked")) {
        //    search['delicacyRequest.cityIds['+i+']'] = Number($(this).data("id"));
        //    i++;
        //}
        // console.log($(this).attr('imagephoto'));
        imagePhotos[i] = $(this).attr('imagephoto');
        i++;
    });

    for (var j = 0; j < imagePhotos.length; j++) {
        // console.log(imagePhotos[j]);
        data['comment.commentPhotos[' + j + '].imagePath'] = imagePhotos[j];
    }

    if (imagePhotos.length > 4) {
        promptWarn("请选择四张最精美照片哈", 1000);
        return;
    }
    $.ajax({
        url:"/lvxbang/comment/saveComment.jhtml",
        type:"post",
        data:data,
        dataType:"json",
        success:function(data){
            //if(data.result =="nologin"){
            //    promptMessage("用户未登陆");
            //    setTimeout(function () {
            //        login_popup();
            //    }, 1000);//延迟1秒
            //
            //}
            if(data.msg == "评论成功") {
                promptMessage(data.msg, 1000);
                $('.word').html("1000");
                $('.img_div').remove();
                $('#comment').val('');
                for (var i = 0; i < scores.length; i++) {
                    if (scores[i] != null) {
                        var j = i + 1;
                        $('#commentScoretypeId' + j).width(0);
                    }
                }
                commentList();
                var height = $("#comment_ul").offset().top - 125;
                $('html,body').animate({"scrollTop": height}, 1000);

            }

        },
        error:function(data){
            window.console.log(data.msg);
        }
    });

}

// 评论区图片上传
function uploadPhoto() {
    var options = {
        url: '/lvxbang/upload/imageUpload.jhtml',
        picker: '#image_div',
        fileNumLimit: 4,
        formData: {
            section: "comment"
        },
        singleSuccessHandler: function (response) {
            var result = template("tpl-image-item", response);
            $('#image_div').before(result);
            return true;
        },
        allSuccessHandler: function () {
            $(".Upload_xp_r").find("i.close").click();
            promptMessage("上传成功", 1000);
            return true;
        }
    }
    //初始化上传器
    initCommentImgUploader(options);
}

function deleteImage(i) {
    $(i).parent().remove();
}

//取消回复按钮
function cancel_reply(reply) {
    var rep = $(reply).parents('li').find('div:eq(0)').find('.reply');
    rep.text("回复");
    rep.parent(".synopsis").siblings(".message").animate({"height": "0px"}, 600);
    rep.removeAttr("data-staute");
    $(reply).parent().children('textarea').val("");
    $(reply).parent().find('.word').text(200);
}

//来源star.js
$(document).ready(function(){

    $(".star").each(function(){
        var star=this;
        var stepW = $(this).attr("mname");
        if (stepW == 30) {
            stepW=35;
        }
        var description = new Array("很差不推荐","一般不推荐","推荐","较推荐","强烈推荐");
        var stars = $("> li",star);
        $("~ .current-rating",star).css("width",0);
        stars.each(function(i){
            $(stars[i]).click(function(e){
                var n = i+1;
                $("~ .current-rating",star).css({"width":stepW*n});
                var descriptionTemp = description[i];
                $(this).find('a').blur();
                $(star).data("description",descriptionTemp);
                return stopDefault(e);
            });
        });
        stars.each(function(i){
            $(this).hover(
                function(){
                    $("~.description",$(star).parent()).text(description[i]);
                },
                function(){
                    var descriptionTemp=$(star).data("description");
                    if(descriptionTemp != null)
                        $("~.description",$(star).parent()).text(descriptionTemp);
                    else
                        $("~.description",$(star).parent()).text(" ");
                }
            );
        });
    });

});
function stopDefault(e){
    if(e && e.preventDefault)
        e.preventDefault();
    else
        window.event.returnValue = false;
    return false;
};