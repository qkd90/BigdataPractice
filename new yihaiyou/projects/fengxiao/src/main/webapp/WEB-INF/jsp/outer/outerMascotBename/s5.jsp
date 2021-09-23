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
  <title>联系方式</title>
  <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color:#fff;
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
        .questionContain p.atention {
            margin:10px 0;
        }
        .modal-header{ padding: 7px; }
        .modal-title{font-size: 14px; font-weight: bold; }
        .modal-body{font-size: 14px; }
    </style>
</head>
<body>
<div class="personal_question">
    <div class="questionContain">
        <form action="/outer/outerMascotBename/save.jhtml" method="post">
        <h3>联系方式：</h3>
        <div class="form-group">
            <input type="text" name="outerMascotBename.mascotName" value="${outerMascotBename.mascotName}" maxlength="50" class="form-control" placeholder="萌宠名字(必填)" required autofocus>
        </div>
        <div class="form-group">
            <input type="text" name="outerMascotBename.participator" value="${outerMascotBename.participator}" maxlength="5" class="form-control" placeholder="您的姓名(必填)" required>
        </div>
        <div class="form-group">
            <input id="phone" type="text" name="outerMascotBename.phone" value="${outerMascotBename.phone}" maxlength="11" class="form-control" placeholder="联系方式(必填)" required>
        </div>
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
        // 电话输入框校验
        $('#phone').keyup(function() {
            if (this.value) {
                this.value = this.value.replace(/\D/g,'');
            }
        });
        // 是否显示提示信息
        var errorMsg = '${errorMsg}';
        if (errorMsg) {
            $('.modal-body').html(errorMsg);
            $('#myModal').modal('show');
        }
    });
</script>
</html>
