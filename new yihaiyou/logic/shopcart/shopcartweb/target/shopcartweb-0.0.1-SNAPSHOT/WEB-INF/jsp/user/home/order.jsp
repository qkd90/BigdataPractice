<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人中心</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/geren.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <!--日期控件css-->
    <link href="${mallConfig.resourcePath}/css/date/default.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="container-bg clearfix">
    <div class="container">
        <div class="row">
<jsp:include page="left-nav.jsp"></jsp:include>
            <div class="col-xs-10">
                <div class="gr-order-box clearfix">
                    <!------------------->
                    <div class="order-tab"> <a href="#" class="curr">所有订单</a><a href="#">线路订单</a><a href="#">门票订单</a> </div>
                    <!------------------->
                    <div class="order-form">
                        <form class="form-horizontal">
                            <div class="form-group">
                <span class="col-xs-6">
                <label for="time">下单时间：</label>
                <input id="datepicker1" type="text" data-zdp_direction="1" value="2015-10-24">
                -
                <input id="datepicker2" type="text" data-zdp_direction="1" value="2015-10-24">
                </span>
                            </div>
                            <div class="form-group">
                <span class="col-xs-2">
                <select class="form-control">
                    <option>客人姓名</option>
                    <option>订单号</option>
                </select>
                </span>
                <span class="col-xs-2">
                <input type="text" class="form-control" id="exampleInputName2">
                </span>
                                <label class="col-xs-2 text-right">按订单状态搜索：</label>
                                <div class="col-xs-2"><input type="text" class="form-control" id="exampleInputName2"></div>
                <span class="col-xs-2">
                <select class="form-control">
                    <option>所有</option>
                    <option>已支付</option>
                </select>
                </span>
                                <span class="col-xs-2"><input class="btn btn-danger" value="查找" type="button"></span>
                            </div>
                        </form>
                    </div>
                    <!------------------->
                    <div class="clearfix orderlist">
                        <input type="hidden" id="page-index" value="${page.pageIndex}"/>
                        <table class="table table-hover text-center">
                            <thead >
                            <tr class="active">
                                <th>订单号</th>
                                <th>客人姓名</th>
                                <th>联系电话</th>
                                <th>类型</th>
                                <th>预定产品名称</th>
                                <th>下单时间</th>
                                <th>数量</th>
                                <th>订单状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="order-list-wrap">

                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix text-right">
                        <ul class="pagination" id="page-list">
                            <%-- <li class="disabled"><span>共有 8 条记录</span></li> --%>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/x-jsrender" id="order-template">
    <tr>
        <td>{{:id}}</td>
        <td>{{:recName}}</td>
        <td>{{:mobile}}</td>
        <td>{{:type}}</td>
        <td>{{:orderName}}</td>
        <td>{{:createTime}}</td>
        <td>{{:count}}</td>
        <td>{{:statusStr}}</td>
        <td>
			<a href="/user/home/detail.jhtml?id={{:id}}">查看</a>
			{{if status == 'WAIT'}}<a href="/pay/pay/confirmOrder.jhtml?orderId={{:id}}">支付</a>{{/if}}
		</td>
    </tr>
</script>
<jsp:include page="footer.jsp"></jsp:include>
<script src="${mallConfig.resourcePath}/js/jquery.min.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/zebra_datepicker.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/plugs/jquery.pagination.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jsrender.js" type=""></script>
<script src="${mallConfig.resourcePath}/js/user/home/order.js?${mallConfig.resourceVersion}"></script>
</body>
</html>

