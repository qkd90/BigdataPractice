<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta content="" name="Keywords"/>
    <meta content="" name="Description"/>
    <title>验票记录</title>
    <link href="/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        table th { text-align:center; background-color:rgb(217,237,247);}
        .table-striped>tbody>tr:nth-of-type(odd) {background-color:rgba(217,237,247,0.5);}
        .text-ellipsis {
            overflow: hidden; /*自动隐藏文字*/
            text-overflow: ellipsis;/*文字隐藏后添加省略号*/
            white-space: nowrap;/*强制不换行*/
        }
        .namecol { width:60%;}
        .phonecol { width:20%;}
        .timecol { width:20%;}
    </style>
</head>
<body>
<div class="container">
    <!-- 查询条件开始 -->
    <div class="row" style=" margin-top:5px; margin-bottom:5px;">
        <div class="col-xs-8">
            <div class="input-group">
                <input id="validateDate" type="date" class="form-control" value="${validateDate}">
				<span class="input-group-btn">
					<button id="search" class="btn btn-default" type="button"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
				</span>
            </div>
            <input id="page" type="hidden" value="${page}">
        </div>
        <div class="col-xs-4">
            <button id="backCheck" class="btn btn-info" type="button">返回</button>
        </div>
    </div>
    <!-- 查询条件结束 -->

    <!-- 列表开始 -->
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-bordered table-striped table-condensed">
                <thead>
                <tr>
                    <th class="namecol">门票x数量</th>
                    <th class="phonecol">手机</th>
                    <th class="timecol">时间</th>
                </tr>
                </thead>
                <c:forEach items="${validateRecords}" var="record">
                    <tr data-id="${record.id}">
                        <td>${record.nameAndCount}</td>
                        <td>${record.buyerMobile}</td>
                        <td>${record.validateTimeStr}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <!-- 列表结束 -->

    <div class="row">
        <div class="col-xs-12" style="text-align: center;">
            <c:if test="${fn:length(validateRecords)>=10}">
            <button id="loadMore" class="btn btn-info" type="button">加载更多</button>
            </c:if>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function() {

        // 查询
        $('#search').click(function() {
            var validateDate = $('#validateDate').val();
            location.href = '/vticket/checkTicket/checkRecord.jhtml?validateDate='+validateDate;
        });

        // 返回验票
        $('#backCheck').click(function() {
            location.href = '/vticket/checkTicket/check.jhtml';
        });

        // 加载更多
        $('#loadMore').click(function() {
            var validateTime = '${validateTime}';
            var page = parseInt($('#page').val()) + 1;
            $.ajax({
                url:"/vticket/checkTicket/loadMoreRecord.jhtml",
                type:"post",
                dataType:"json",
                data:{
                    validateTime: validateTime,
                    page:page
                },
                success: function(result) {
                    if (result) {
                        if (result.success) {
                            $('#page').val(page);
                            if (result.data && result.data.length > 0) {
                                if (result.data.length < 10) {// 删除加载更多按钮
                                    $('#loadMore').remove();
                                }
                                var html = "";
                                for (var i = 0; i < result.data.length; i++) {
                                    var obj = result.data[i];
                                    html = html + '<tr data-id="' + obj.id + '"><td>' + obj.nameAndCount + '</td><td>'
                                            + obj.buyerMobile + '</td><td>' + obj.validateTimeStr + '</td></tr>';
                                }
                                $('table').append(html);
                            } else {    // 删除加载更多按钮
                                $('#loadMore').remove();
                            }
                        } else {
                            alert(result.errorMsg);
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

    });
</script>
</body>
</html>
