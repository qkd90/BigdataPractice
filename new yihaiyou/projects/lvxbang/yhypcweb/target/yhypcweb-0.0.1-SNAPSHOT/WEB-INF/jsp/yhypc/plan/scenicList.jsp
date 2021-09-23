<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-09,0009
  Time: 16:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" href="/css/scenic/scenicList.css">
    <link rel="stylesheet" href="/css/public/pager.css">
    <title>景点列表-一海游</title>
</head>
<body class="DIY">
<div class="hotelIndex">
    <%@include file="../../yhypc/public/nav_header.jsp" %>
    <div class="directories">
        <div class="rangestate">
            您在这里：<a href="/yhypc/index/index.jhtml">首页</a> &gt; <a href="/yhypc/plan/demand.jhtml"> DIY行程</a> </span> &gt; 厦门景点
        </div>
        <div class="dirbox">
            <input type="hidden" id="labelId" value="${labelId}">
            <input type="hidden" id="cityId" value="${cityId}">
            <div class="dirbody">
                <div class="position keyword clearfix">
                    <label>关键字：</label>
                    <ul class="clearfix">
                        <li>
                            <input class="word" type="text" id="searchWord" placeholder="关键字" value="${searchWord}">
                            <div class="search"><a id="searchBtn">搜索</a></div>
                        </li>
                    </ul>
                </div>
                <div class="position clearfix" id="area">
                    <label>区域：</label>
                    <ul class="clearfix">
                        <li class="firstli takeit">不限</li>
                        <li data-city-id="350203">思明区</li>
                        <li data-city-id="350206">湖里区</li>
                        <li data-city-id="350205">海沧区</li>
                        <li data-city-id="350211">集美区</li>
                        <li data-city-id="350212">同安区</li>
                        <li data-city-id="350213">翔安区</li>
                    </ul>
                    <div class="openMore" style="display: none;"><span class='listopen'>展开</span></div>
                </div>
                <div class="position clearfix" id="theme">
                    <label>主题：</label>
                    <ul class="clearfix">
                        <li class="firstli takeit">不限</li>
                    </ul>
                    <div class="openMore" style="display: none;"><span class='listopen'>展开</span></div>
                </div>
                <div class="position clearfix" id="price">
                    <label>价格：</label>
                    <ul class="clearfix">
                        <li data-min-price="0" data-max-price="9999999" data-price-mode="range" class="price firstli takeit">不限</li>
                        <li data-min-price="0" data-max-price="150" data-price-mode="range" class="price">150元以下</li>
                        <li data-min-price="150" data-max-price="300" data-price-mode="range" class="price">150-300元</li>
                        <li data-min-price="300" data-max-price="600" data-price-mode="range" class="price">301-600元</li>
                        <li data-min-price="600" data-price-mode="range" class="price">600元以上</li>
                        <li class="customized" data-min-price="-1" data-price-mode="customize">
                            <label>自定义</label>
                            <span>¥<input type="text" id="custom_min_price"></span>
                            <span class="none">-</span>
                            <span>¥<input type="text" id="custom_max_price"></span>
                            <label class="yes" id="custom_price_btn">确定</label>
                        </li>
                    </ul>
                </div>
                <div class="dirfoot">
                    <span class="riWord">为您找到&nbsp; </span><span class="riNum" id="totalCount"></span><span class="riWord"> &nbsp;个景点</span>
                    <div id="filter_dis" class="filter-dis"></div>
                    <span class="clearall" id="clear_filter_btn" style="display: none">清空条件</span>
                </div>
            </div>
        </div>
    </div>
    <div class="searchingList clearfix">
        <div class="listLeft">
            <div class="list_head">
                <span class="li_he_active" data-order-column="ranking" data-order-type="asc">编辑推荐</span><span class="ace" data-order-column="productScore" data-order-type="asc">好评</span><span class="dace" data-order-column="price" data-order-type="desc">价格</span>
            </div>
            <div id="scenicList"></div>
            <div class="paging m-pager"></div>
        </div>
        <div class="listRight">
            <div id="r_map" style="height: 500px;"></div>
        </div>
    </div>
    <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/plan/planScenicList.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>s
<script type="text/html" id="theme_list_item">
    <li data-label-id="{{id}}">{{alias}}</li>
</script>
<script type="text/html" id="filter_dis_tpl">
    <span class="forone" data-filter-id="{{id}}" data-filter-mode="{{mode}}">{{text}}<label>×</label></span>
</script>
<script type="text/html" id="scenic_item">
    <div class="list_body">
        <div class="body_detail clearfix">
            <div class="body_pic"><img src="{{cover | imageResize:225,135}}"></div>
            <div class="body_describ clearfix">
                <div class="address">
                    <a target="_blank" href="/yhypc/plan/scenicDetail.jhtml?scenicId={{id}}">
                    <p class="name">{{name}}</p></a>
                    {{each region as regionName}}<p class="gps">【{{regionName}}】</p>{{/each}}
                    <p class="de_address">地址：{{address}}</p>
                    <div class="commend">
                        <span class="sc_blue"><span class="score">{{productScore / 20}}</span> / 5分</span>
                        <span class="level_star level_star_{{productScore / 20}}"></span>
                        <span class="sc_orage"><span class="comnum">{{commentCount}}</span>条评论</span>
                    </div>
                </div>
                <div class="price">
                {{if price <= 0}}<div class="price_count" style="visibility: hidden">{{else}}<div class="price_count">{{/if}}
                        <span class="rmb">¥</span><span class="num">{{price}}</span><span class="qi">起</span>
                    </div>
                    <div class="tackdetail"><a target="_blank" href="/yhypc/plan/scenicDetail.jhtml?scenicId={{id}}">查看详情</a></div>
                </div>
            </div>
        </div>
    </div>
</script>
</html>