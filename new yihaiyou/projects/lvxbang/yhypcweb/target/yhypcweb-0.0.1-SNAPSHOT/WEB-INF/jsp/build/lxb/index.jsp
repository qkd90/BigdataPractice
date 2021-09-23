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
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/build.jhtml" data-title="全部生成">全部生成</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildIndex.jhtml" data-title="首页">首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildCommon.jhtml" data-title="城市选择面板">城市选择面板</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildPlanIndex.jhtml" data-title="生成行程首页">生成行程首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildDestinationIndex.jhtml" data-title="目的地首页">目的地首页</a></div>
        <div class="panel" id="destination-detail">
            <a class="btn btn-lg build btn-primary destinationDetail" data-url="/build/lxb/buildDestinationDetail.jhtml"
               data-title="目的地详情">目的地详情</a>
            <input class="form-control startId" id="destination-start-id" type="text" placeholder="起始id"/>
            <input class="form-control endId" id="destination-end-id" type="text" placeholder="结束id"/>
            <span class="status"></span>
            当前id：<span class="id"></span>
            已处理数量：<span class="count">0</span>
            平均速度：<span class="speed"></span>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildHotelIndex.jhtml" data-title="酒店首页">酒店首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildHotelDetail.jhtml"
                              data-title="全部酒店详情">全部酒店详情</a></div>
        <div class="panel">
            <a class="btn btn-lg build btn-primary hotelDetail" data-url="/build/lxb/buildOneHotelDetail.jhtml"
               data-title="酒店详情">酒店详情</a>
            <input class="form-control" type="text" id="hotelId" name="hotelId" placeholder="请输入单个酒店Id"/>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildRecommendPlanIndex.jhtml" data-title="游记首页">游记首页</a></div>
        <div class="panel recommend">
            <a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildRecommendPlanDetail.jhtml" data-title="游记详情">生成全部游记详情</a>
            <a class="btn btn-lg build btn-primary build-a-recommend" data-url="/build/lxb/buildRecommendPlanDetail.jhtml" data-title="游记详情">生成游记详情</a>
            <input class="form-control" type="text" id="recplanId" name="recplanId" placeholder="请输入单个游记Id"/>
            <input class="form-control" type="text" id="recplanStartId" name="recplanStartId" placeholder="请输入游记起始Id"/>
            <input class="form-control" type="text" id="recplanEndId" name="recplanEndId" placeholder="请输入游记结束Id"/>
            <input class="form-control" type="text" id="cityIds" name="cityIds" placeholder="或输入城市Id"/>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildTrafficIndex.jhtml" data-title="生成交通首页">生成交通首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildScenicIndex.jhtml" data-title="景点首页">景点首页</a></div>
        <div class="panel " id="scenic-detail">
            <a class="btn btn-lg build btn-primary scenicDetail" data-url="/build/lxb/buildScenicDetail.jhtml" data-title="景点详情">景点详情</a>
            <input class="form-control startId" id="scenic-start-id" type="text"  placeholder="起始id"/>
            <input class="form-control endId" id="scenic-end-id" type="text"  placeholder="结束id"/>
            <span class="status"></span>
            当前id：<span class="id"></span>
            已处理数量：<span class="count">0</span>
            平均速度：<span class="speed"></span>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildDelicacyIndex.jhtml" data-title="美食首页">美食首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildDelicacyDetail.jhtml" data-title="美食详情">美食详情</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildLineCustomTourIndex.jhtml" data-title="定制精品首页">定制精品首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildLineGroupTourIndex.jhtml" data-title="跟团游首页">跟团游首页</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/buildLineSelfTourIndex.jhtml" data-title="自助自驾游首页">自助自驾游首页</a></div>
        <div class="panel " id="line-detail">
            <a class="btn btn-lg build btn-primary lineDetail" data-url="/build/lxb/buildLineDetail.jhtml" data-title="线路详情">线路详情</a>
            <a class="btn btn-lg build btn-primary cloneLine" data-url="/build/lxb/cloneLine.jhtml" data-title="复制线路">复制线路</a>
            <input class="form-control" type="text" id="lineId" name="lineId" placeholder="请输入单个线路Id"/>
            <input class="form-control" type="text" id="cloneNum" name="cloneNum" placeholder="请输入复制数量"/>
        </div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexLine.jhtml"
                              data-title="线路索引">线路索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexPlan.jhtml"
                              data-title="行程索引">行程索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexScenic.jhtml"
                              data-title="景点索引">景点索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexHotel.jhtml"
                              data-title="酒店索引">酒店索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexHotelRoom.jhtml"
                              data-title="酒店索引">酒店房型索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexRecommendPlan.jhtml"
                              data-title="游记索引">游记索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexDelicacy.jhtml"
                              data-title="游记索引">美食索引</a></div>
        <div class="panel"><a class="btn btn-lg build btn-primary" data-url="/build/lxb/indexArea.jhtml"
                              data-title="游记索引">城市索引</a></div>
    </div>
</div>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/assets/lib/js/build.lxb.index.js"></script>
</body>
</html>
