<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/12/14
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>静态页面生成</title>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/lib/css/build.lxb.index.css"/>
</head>
<body>
<div class="page">

    <div class="container">
        <%--<div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/build.jhtml" data-title="全部生成">全部生成</a></div>--%>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/yhyIndex.jhtml" data-title="首页">首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/sailboatIndex.jhtml" data-title="游艇帆船首页">游艇帆船首页</a></div>
        <div class="panel " id="scenic-detail">
            <a class="btn btn-lg build btn-primary scenicDetail" data-url="/build/yhy/sailboatDetail.jhtml" data-title="游艇帆船详情">游艇帆船详情</a>
            <input class="form-control startId" id="scenic-start-id" type="text"  placeholder="起始id"/>
            <input class="form-control endId" id="scenic-end-id" type="text"  placeholder="结束id"/>
            <span class="status"></span>
            当前id：<span class="id"></span>
            已处理数量：<span class="count">0</span>
            平均速度：<span class="speed"></span>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/yhyHotelIndex.jhtml" data-title="民宿首页">民宿首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/yhyScenicIndex.jhtml" data-title="景点首页">景点首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/cruiseshipIndex.jhtml" data-title="游轮首页">游轮首页</a></div>
        <div class="panel " id="cruiseship-detail">
            <a class="btn btn-lg build btn-primary cruiseshipDetail" data-url="/build/yhy/cruiseshipDetail.jhtml" data-title="游轮详情">游轮详情</a>
            <input class="form-control startId" id="cruiseship-start-id" type="text"  placeholder="起始id"/>
            <input class="form-control endId" id="cruiseship-end-id" type="text"  placeholder="结束id"/>
            <span class="status"></span>
            当前id：<span class="id"></span>
            已处理数量：<span class="count">0</span>
            平均速度：<span class="speed"></span>
        </div>

        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/yhy/recommendPlanIndex.jhtml" data-title="游记首页">游记首页</a></div>

        <div class="panel " id="recommendPlan-detail">
            <a class="btn btn-lg build btn-primary recommendPlanDetail" data-url="/build/yhy/recommendPlanDetail.jhtml" data-title="游记详情">游记详情</a>
            <input class="form-control startId" id="recommendPlan-start-id" type="text"  placeholder="起始id"/>
            <input class="form-control endId" id="recommendPlan-end-id" type="text"  placeholder="结束id"/>
            <span class="status"></span>
            当前id：<span class="id"></span>
            已处理数量：<span class="count">0</span>
            平均速度：<span class="speed"></span>
        </div>
    </div>
</div>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/assets/lib/js/build.yhy.index.js"></script>
</body>
</html>
