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
    <title>验票</title>
    <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/vticket/checkTicket/clogin.css" rel="stylesheet">
    <style type="text/css">
        p.bg-success, p.bg-danger {padding: 8px;}
        .table>tbody>tr>td {padding: 1px;border: none;}
    </style>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
            // 票数整数验证
            $('#validateCount').blur(function() {
                var val = $(this).val();
                if (val) {  // 强制转为大于0的整数
                    val = parseInt(val);
                    if (val <= 0) {
                        $(this).val(1);
                    } else {
                        $(this).val(val);
                    }
                }
            });

            // 验票按钮
            $('#checkTicket').click(function() {
                var validateCode = $('#validateCode').val();
                var validateCount = $('#validateCount').val();
                if (!validateCode) {
                    $('.bg-danger').html('验证码不能为空');
                    $(".bg-danger").show().fadeOut(3000);
                    return ;
                }
                // 获取产品信息
                $.ajax({
                    url:"/vticket/checkTicket/doGetTicket.jhtml",
                    type:"post",
                    dataType:"json",
                    data:{
                        validateCode: validateCode, validateCount: validateCount
                    },
                    success: function(result) {
                        if (result) {
                            if (result.success) {
//                                $('.bg-info').html('已匹配到验证码');
//                                $(".bg-info").show().fadeOut(3000);

                                var validateCount = result.validateCount;
                                var html = '<table class="table table-hover">';
                                html += '<tbody>';
                                if (result.orderNo) {
                                    var url = "/vticket/checkTicket/getVcheckProTicketList.jhtml";
                                    var data = {
                                        orderNO:result.orderNo,
                                        productId:result.productId
                                    }
                                    $.post(url, data,
                                            function(resultData) {
                                                if (resultData.success) {
                                                    var detail = resultData.detail;
                                                    html += '<tr>';
                                                    html += '<td>'+detail.ticketName +'</td>';
                                                    html += '<td>';
                                                    if (detail.type == "adult") {
                                                        html +=  '成人票';
                                                    }
                                                    if (detail.type == "student") {
                                                        html +=  '学生票';
                                                    }
                                                    if (detail.type == "child") {
                                                        html +=  '儿童票';
                                                    }
                                                    if (detail.type == "oldman") {
                                                        html +=  '老年票';
                                                    }
                                                    if (detail.type == "taopiao") {
                                                        html +=  '套票';
                                                    }
                                                    if (detail.type == "team") {
                                                        html +=  '团体票';
                                                    }
                                                    if (detail.type == "other") {
                                                        html +=  '其他';
                                                    }
                                                    html += 'x';
                                                    html +=  detail.count - detail.refundCount;
                                                    html += '</td><td>';
                                                    html +=  '验票x<strong style="color: orange;">';
                                                    html +=  validateCount;
                                                    html += '</strong></td>';
                                                    html += '</tr>';
                                                    html += '</tbody>';
                                                    html += '</table>';
                                                    $('.modal-body').html(html);
                                                }
                                            }
                                    );
                                } else {
                                    html += '<tr>';
                                    html += '<td>'+result.productName +'</td>';
                                    html += '<td>x</td>';
                                    html += '<td>'+result.orderCount+'</td>';
                                    html += '</tr>';
                                    html += '</tbody>';
                                    html += '</table>';
                                    $('.modal-body').html(html);
                                }
                                $('#codeId').val(result.codeId);
                                $('#myModal').modal('show');
                                $('#cfmCheck').removeAttr('disabled');
                            } else {
                                $('.bg-danger').html(result.errorMsg);
                                $(".bg-danger").show().fadeOut(3000);
                            }
                        } else {
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                    }
                });
            });

            // 确认验票按钮
            $('#cfmCheck').click(function() {
                var codeId = $('#codeId').val();
                if (!codeId) {
                    alert("无效参数");
                    return ;
                }
                // 验票
                var validateCount = $('#validateCount').val();
                $('#cfmCheck').attr('disabled', 'disabled');
                $.ajax({
                    url:"/vticket/checkTicket/doCheck.jhtml",
                    type:"post",
                    dataType:"json",
                    data:{
                        codeId: codeId, validateCount: validateCount
                    },
                    success: function(result) {
                        $('#myModal').modal('hide');
                        if (result) {
                            if (result.success) {
                                $('.bg-success').html('验证成功');
                                $(".bg-success").show().fadeOut(3000);
                                $('.modal-body').html('');
                                $('#codeId').val('');
                                $('#validateCode').val('');
                                $('#validateCount').val('');
                            } else {
                                $('.bg-danger').html(result.errorMsg);
                                $(".bg-danger").show().fadeOut(3000);
                            }
                        } else {
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                    }
                });
            });
            // 验票记录
            $('#checkRecord').click(function() {
                location.href = '/vticket/checkTicket/checkRecord.jhtml';
            });
        });
    </script>
</head>
<body>
<div class="container">
    <form class="form-signin">
        <h3 class="form-signin-heading">验证输入</h3>
        <div>
            <p class="bg-success" style="display:none"></p>
            <p class="bg-danger" style="display:none"></p>
        </div>

        <div class="row">
            <div class="col-xs-6" style="text-align: right; padding-right: 0;">
                <div class="form-group">
                    <label for="validateCode" class="sr-only">验证码</label>
                    <input type="text" id="validateCode" name="validateCode" maxlength="5" class="form-control" placeholder="五位验证码" required autofocus>
                </div>
            </div>
            <div class="col-xs-6" style="text-align: left; padding-left: 0;">
                <div class="form-group">
                    <label for="validateCount" class="sr-only">票数</label>
                    <input type="number" id="validateCount" name="validateCount" class="form-control" placeholder="票数">
                </div>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-xs-12" style="text-align: center;">
            <button id="checkTicket" class="btn btn-info" type="button">验票</button>
            <button id="checkRecord" class="btn btn-info" type="button">验票记录</button>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h3 class="modal-title" id="myModalLabel">产品信息</h3>
            </div>
            <div class="modal-body">

            </div>
            <input id="codeId" type="hidden"/>
            <div class="modal-footer">
                <button id="cfmCheck" type="button" class="btn btn-primary">确认验票</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
