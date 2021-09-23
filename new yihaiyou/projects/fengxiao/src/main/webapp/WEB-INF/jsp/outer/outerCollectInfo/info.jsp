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
  <title>资料填写</title>
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
            width:80%;
            margin:0 10%;
        }
        .questionContain {
            padding: 0 10px;
            font-family: "方正卡通简体";
        }
        .questionContain h3 {
            font-size:22px;
            font-weight:600;
            color: #000;
            margin-bottom:15px;
        }
        .questionContain p.exp {
            margin:15px 0;
        }
        .questionContain p {
            font-size:16px;
            color: #000;
            font-weight:500;
        }
        .questionContain button.goNext{
            color:#4b2b72;
            text-decoration: underline;
            font-weight:600;
            font-size:16px;
        }
        .questionContain p.agreeSel span {
            width:50px;
            height:30px;
            text-align:right;
            display: block;
            float:left;
            line-height:30px;
            margin-left:10px;
            font-family: "方正卡通简体";
            background:url(/images/outer/icon-NO1.png) no-repeat left center;
        }
        .questionContain p.agreeSel span.agree {
            background:url(/images/outer/icon-YES1.png) no-repeat left center;
        }
        .questionContain p.atention {
            margin:10px 0;
        }
        .modal-header{ padding: 7px; font-family: "方正卡通简体";}
        .modal-title{font-size: 14px; font-weight: bold; font-family: "方正卡通简体";}
        .modal-body{font-size: 14px; font-family: "方正卡通简体";}
    </style>
</head>
<body>
<div class="personal_question">
    <div class="logopic">
        <img src="/images/outer/quetion2.png">
    </div>
    <div class="questionContain">
        <form action="/outer/outerCollectInfo/question.jhtml" method="post">
        <h3>个人资料</h3>
        <div class="form-group">
            <input type="text" name="outerCollectInfo.companyName" value="${outerCollectInfo.companyName}" maxlength="50" class="form-control" placeholder="公司名称(可选)" autofocus>
        </div>
        <div class="form-group">
            <input type="text" name="outerCollectInfo.participator" value="${outerCollectInfo.participator}" maxlength="5" class="form-control" placeholder="姓名(必填)" required>
        </div>
        <div class="form-group">
            <input id="phone" type="text" name="outerCollectInfo.phone" value="${outerCollectInfo.phone}" maxlength="11" class="form-control" placeholder="电话(必填)" required>
        </div>
            <input id="agree" type="hidden" name="outerCollectInfo.agree" value="true">
        <p class="exp">游戏结束后会以您填写的资料作为获奖者凭证，请认真填写。</p>
        <p class="agreeSel clearfix">
            <span class="agree" data-agree="true">同意</span>
            <span class="" data-agree="false">拒绝</span>
        </p>
        <p class="atention">注：如果拒绝提供个人资料，将未能获奖</p>
        <button class="btn btn-lg btn-link next" type="submit">提交</button>
        </form>
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
        $('.agreeSel span').click(function() {
            $('.agreeSel span').removeClass('agree');
            $(this).addClass('agree');
            $('#agree').val($(this).attr('data-agree'));
        });
        // 电话输入框校验
        $('#phone').keyup(function() {
            if (this.value) {
                this.value = this.value.replace(/\D/g,'');
            }
        });
        // 是否显示提示信息
        var checkValue = '${checkValue}';
        if (checkValue) {
            $('.modal-body').html(checkValue);
            $('#myModal').modal('show');
        }
    });
</script>
</html>
