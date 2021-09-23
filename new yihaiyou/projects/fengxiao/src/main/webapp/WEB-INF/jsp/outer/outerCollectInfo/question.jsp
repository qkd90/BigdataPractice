<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta content="" name="Keywords"/>
  <meta content="" name="Description"/>
  <title>题目问答</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color:#fff;
        }
        @font-face {
            font-family: "方正卡通简体";
            src: url("/css/yhy/yhyCommon/cuton.ttf");
            src: url("/css/yhy/yhyCommon/cuton.ttf") format("embedded-opentype"), url("/css/yhy/yhyCommon/cuton.ttf") format("truetype"), url("/css/yhy/yhyCommon/cuton.ttf") format("svg");
            font-style: normal;
            font-weight: normal
        }
        .logopic {
            width:100%;
        }
        .logopic img {
            width:77%;
            margin:0 10%;
            height:240px;
        }
        .questionContain {
            padding: 0 10px;
            display:inline-block;
            height:300px;
        }
        .questionContain h3 {
            font-size:28px;
            font-weight:600;
            color: #000;
            margin-bottom:15px;
        }
        .questionContain h4 {
            font-size:21px;
            color: #333;
        }
        .questionContain ul {
            /*padding:10px 10px 10px 15px;*/
            list-style-type: none;
            padding-left:10px;
            margin-bottom:0;
        }
        .questionContain ul li {
            font-size:18px;
            padding-left:25px;
            line-height:25px;
            background:url(/images/outer/icon-czx.png) no-repeat left center;
            background-size:17px;
        }
        .questionContain ul li.forsure {
            background:url(/images/outer/icon-gou.png) no-repeat left center;
            background-size:17px;
            color: #35bf83;
        }

        .questionContain ul li.forfalse {
            background:url(/images/outer/icon-false.png) no-repeat left center;
            background-size:17px;
            color: #f51212;
        }
        #complete,.next {
            margin-left:60%;
            width: 100px;
            height: 30px;
            line-height: 30px;
            padding: 0;
        }
        .questionBox {
            width:100%;
            height:300px;
            overflow:hidden;
            position:relative;
            margin-bottom:40px;
        }
        .slider {
            position:absolute;
            top:0;
            left:0;
            z-index: 10;
            font-family: '方正卡通简体';
        }
        .whiteBox {
            opacity:0.5;
            background: linear-gradient(to bottom, #fff 0%,#f0f0f0 100%);
            position:absolute;
            top:0;
            left:0;
            width:100%;
            height:35px;
            z-index:11;
        }
        .questionContain p.goNext {
            color:#4b2b72;
            text-decoration: underline;
            font-weight:600;
        }
        .modal-header{ padding: 7px; font-family: "方正卡通简体";}
        .modal-title{font-size: 14px; font-weight: bold; font-family: "方正卡通简体";}
        .modal-body{font-size: 14px; font-family: "方正卡通简体";}
    </style>
</head>
<body>
<div class="personal_question">
    <div class="logopic">
        <img src="/images/outer/quetion1.png">
    </div>
    <div class="questionBox">
        <div class="slider">
            <c:forEach items="${questions}" var="q" varStatus="status">
                <div class="questionContain" data-index="${status.index + 1}">
                    <h3>${q.title}</h3>
                    <h4>${q.desc}</h4>
                    <ul>
                        <c:forEach items="${q.questionCandidates}" var="c">
                            <li class="" data-candidateid="${c.id}" data-answerflag="${c.answerFlag}">${c.desc}</li>
                        </c:forEach>
                    </ul>
                    <c:if test="${!status.last}">
                        <button class="btn btn-lg btn-link next" type="button" disabled="disabled">下一题</button>
                    </c:if>
                    <c:if test="${status.last}">
                        <button id="complete" class="btn btn-lg btn-link" type="button" disabled="disabled">完成</button>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="whiteBox" style="display: none;"></div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade bs-example-modal-sm" id="myModal" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h3 class="modal-title" id="myModalLabel">温馨提示</h3>
            </div>
            <div class="modal-body">

            </div>
        </div>
    </div>
</div>
</body>
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script src="/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function() {
        // 单选框事件
        $('.questionContain li').click(function() {
//            $(this).siblings().removeClass('forsure');
            $(this).addClass('answer'); // 参与者回答的答案
            var answerflag = $(this).attr('data-answerflag');
            if (answerflag == 'true') { // 选择正确
                $(this).addClass('forsure');
            } else {    // 选择错误
                $(this).addClass('forfalse');
                $(this).siblings('li[data-answerflag="true"]').addClass('forsure');
            }
            // 解绑事件
            $(this).parent().children('li').unbind('click');
            // 激活按钮
            $(this).parent().next().removeAttr('disabled');
//            var index = $(this).parent().parent().attr('data-index');
//            $('.whiteBox').show();
//            $('.slider').animate({top:-300*index},500,function(){
//                $('.whiteBox').hide();
//            });
        });
        $('.next').click(function(){
            var index = $(this).parent().attr('data-index');
            $('.whiteBox').show();
            $('.slider').animate({top:-300*index},500,function(){
                $('.whiteBox').hide();
            });
        });
        // 单选框事件
        $('#complete').click(function() {
            var selectedLi = $('.questionContain li.answer');
            if (!selectedLi && selectedLi.length <= 0) {
                $('.modal-body').html('您还没有回答问题！');
                $('#myModal').modal('show');
                return;
            }
            var candidateIdArray = [];
            for (var i = 0; i < selectedLi.length; i++) {
                candidateIdArray.push($(selectedLi[i]).attr('data-candidateid'));
            }
            // 显示数据加载框
            $('.modal-header').hide();
            $('.modal-body').html('<div><img src="/images/outer/loading-sm.gif" style="width:12px;height:12px;margin-right:3px;">数据处理中...</div>');
            $('#myModal').modal('show');
            $.ajax({
                url:"/outer/outerCollectInfo/complete.jhtml",
                type:"post",
                dataType:"json",
                data:{
                    candidateIds: candidateIdArray.join(',')
                },
                success: function(result) {
                    // 隐藏数据加载框
                    $('#myModal').modal('hide');
                    $('.modal-body').html('');
//                    $('.modal-header').show();
                    if (result) {
                        if (result.success) {
                            $('#complete').hide();
                            location.href = '/outer/outerCollectInfo/success.jhtml';
                        } else {
                            $('.modal-body').html(result.errorMsg);
                            $('#myModal').modal('show');
                        }
                    } else {
                        $('.modal-body').html('操作失败，请稍后再试！');
                        $('#myModal').modal('show');
                    }
                },
                error:function(){
                    // 隐藏数据加载框
                    $('#myModal').modal('hide');
                    $('.modal-body').html('');
//                    $('.modal-header').show();
                    $('.modal-body').html('操作失败，请稍后再试！');
                    $('#myModal').modal('show');
                }
            });
        });
    });
</script>
</html>
