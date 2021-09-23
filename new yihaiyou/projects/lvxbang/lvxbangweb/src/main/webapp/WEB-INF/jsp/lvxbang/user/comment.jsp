<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/18
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的评论</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_dp" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>

    <div class=" cl mc_nr">
        <div class="w1000 mc_nr_bg">
            <div class="m_left fl">
                <a href="javaScript:;" class="checked comment-category" id="comment-0"
                   onclick="CommentList.changeCategory(0)"><i></i>全部</a>
                <a href="javaScript:;" class="comment-category" id="comment-1"
                   onclick="CommentList.changeCategory(1)"><i></i>景点点评</a>
                <a href="javaScript:;" class="comment-category" id="comment-2"
                   onclick="CommentList.changeCategory(2)"><i></i>酒店点评</a>
                <a href="javaScript:;" class="comment-category" id="comment-3"
                   onclick="CommentList.changeCategory(3)"><i></i>美食点评</a>
                <a href="javaScript:;" class="comment-category" id="comment-4"
                   onclick="CommentList.changeCategory(4)"><i></i>游记点评</a>
            </div>

            <div class="m_right fr m_dp_line">
                <div class="top mb10"><span class="checkbox disB fl mr10" input="selectall"
                                            id="select-all"><i></i></span>
                    <span class="fl mr30">全选</span>
                    <a href="javaScript:;" class="del fl" onclick="CommentList.delSelected()"></a>
                </div>
                <div class="center">
                    <ul id="mycomment">

                    </ul>
                </div><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
                <!--pager-->
                <p class="cl"></p>
                <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                <div class="m-pager st cl">

                </div>
                <!--pager end-->
                <p class="cl h30"></p><!-- #EndLibraryItem --></div>
            <p class="cl"></p>
        </div>
    </div>

</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/myComment.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //checkbox
        $('.m_dp_line').delegate('.checkbox', 'click', function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").closest('.m_dp_line').find(".checkbox").addClass("checked").attr("data-staute", "1");
                } else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }

            }
            else {
                if (input == "selectall") {
                    $(this).removeClass("checked").removeAttr("data-staute").closest('.m_dp_line').find(".checkbox").removeClass("checked").removeAttr("data-staute");
                } else {
                    $(this).removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.m_dp_line').find(".top .checkbox").removeClass("checked").removeAttr("data-staute");
                }

            }
            var off=false;
            $('.checkbox').each(function(){
                if($(this).hasClass('checked')){
                    off=true;
                }
                if(off){
                    $('.del').css('background-position','0 -156px');
                }else{
                    $('.del').css('background-position','0 -134px');
                }
            });
        });

    });
</script>

<script type="text/html" id="tpl-comment-list-item">

    {{if productType == "scenic"}}
    <li class="jingdian">
        {{else if productType == "hotel"}}
    <li class="jiudian">
        {{else if productType == "delicacy"}}
    <li class="meishi">
        {{else}}
    <li class="xingcheng">
        {{/if}}

        <span class="checkbox disB fl mr10 comment-check" input="options" style="margin-top: 54px;">
            <input class="commentId" type="hidden" value="{{id}}"/>
            <i></i>
        </span>
        <div class="m_dp_line_nr fl">
            <p class="img fl">
                {{if productType == "hotel"}}
                <img src="{{targetCover}}"/>
                {{else}}
                <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/{{targetCover}}"/>
                {{/if}}

            </p>
            <div class="m_dp_line_js fr posiR">
                <p class="name fs14 b">{{targetName}}</p>
                <p class="dianp fs13"><span>我的点评</span>{{time}}</p>
                <div class="comment_hover">
                <p class="synopsis posiR {{if content.length>50}}comment_cut{{/if}}">
                    {{formatFavoritesCom content 50}}
                </p>
                <span class="posiA comment_span" style="display: none; border: 1px solid rgb(229, 229, 229);
                 float: right; width: 550px;color:#aaaaaa;padding:5px;
                  background-color: rgb(255, 255, 255);margin-left: 10px;">{{content}}</span>
                </div>
                {{if detail}}
                <a href="{{detail}}" class="but posiA">查看详情</a>
                {{/if}}
                <i class="m_del posiA" onclick="CommentList.deleteByIds({{id}})"></i>
                <p class="cl"></p>
            </div>
        </div>
        <p class="cl"></p>
    </li>


</script>

