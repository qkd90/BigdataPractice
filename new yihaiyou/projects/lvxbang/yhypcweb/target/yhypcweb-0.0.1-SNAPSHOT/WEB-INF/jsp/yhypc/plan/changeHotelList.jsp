<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <%@include file="../../yhypc/public/header.jsp"%>
  <link rel="stylesheet" href="/css/hotel/hotelSearchList.css">
  <link rel="stylesheet" href="/css/public/pager.css">
  <title>酒店民宿列表-一海游</title>
</head>
<body class="DIY hotelist">
<div class="hotelIndex">
  <%@include file="../../yhypc/public/nav_header.jsp"%>
  <div class="directories">
    <div class="rangestate">
      您在这里：<span class="history" >首页 </span>&gt; <span class="history">酒店预订 </span>&gt; 厦门酒店
    </div>
    <div class="dirbox">
      <input type="hidden" id="regionId" value="${regionId}">
      <input type="hidden" id="star" value="${star}">
      <div class="directorHead">
        <ul class="clearfix">
          <li>
            <label>入住日期：</label>
            <div class="date_d" id="startDateDiv">
              <input type="text" id="startDate" placeholder="入住日期" autocomplete="off" value="${startDate}" readonly></div>
          </li>
          <li>
            <label>离店日期：</label>
            <div class="date_d" id="endDateDiv">
              <input type="text" id="endDate" placeholder="退房日期" autocomplete="off" value="${endDate}" readonly></div>
          </li>
          <li>
            <label>关键词：</label>
            <div class="date_d date_key">
              <input type="text" id="searchWord" placeholder="关键字" value="${searchWord}"></div>
            <div class="search"><a id="searchBtn">搜索</a></div>
          </li>
        </ul>
      </div>
      <div class="dirbody">
        <div class="line position clearfix">
          <div class="limit">
            <label>位置：</label><span class="limited pos">不限</span>
          </div>
          <div class="secondList clearfix">
            <ul class="seccontai positoncontain" id="position_ul"></ul>
            <div class="openMore" id="posMore"><span>展开</span><i class="down"></i></div>
          </div>
        </div>
        <div class="line level clearfix">
          <div class="limit"><label>星级：</label><span class="limited lev">不限</span></div>
          <div class="limitlist">
            <div class="levelbox">
              <ul class="levelcontain" id="star_ul">
                <li class="radio" data-star="0">客栈/经济</li>
                <li class="radio" data-star="1">一星/普通</li>
                <li class="radio" data-star="2">二星/标准</li>
                <li class="radio" data-star="3">三星/舒适</li>
                <li class="radio" data-star="4">四星/高档</li>
                <li class="radio" data-star="5">五星/豪华</li>
              </ul>
            </div>
          </div>
        </div>
        <div class="line price clearfix">
          <div class="limit"><label>房价：</label><span class="limited price pri">不限</span></div>
          <div class="limitlist">
            <div class="pricebox" id="price_div">
              <ul class="pricecontain" id="price_ul">
                <li class="radio price" data-min-price="0" data-max-price="150" data-price-mode="range">150元以下</li>
                <li class="radio price" data-min-price="150" data-max-price="300" data-price-mode="range">150-300元</li>
                <li class="radio price" data-min-price="301" data-max-price="600" data-price-mode="range">301-600元</li>
                <li class="radio price" data-min-price="601" data-price-mode="range">600元以上</li>
                <li class="radio custom-price" data-min-price="-1" data-price-mode="customize" style="width: 280px">
                                        <span class="price_mine">自定义
									    <span class="price_in">¥<input type="text" id="custom_min_price" onkeyup="value=value.replace(/[^\d]/g,'')"></span>
                                            <span class="price_line">-</span>
                                            <span class="price_in">¥<input type="text" id="custom_max_price" onkeyup="value=value.replace(/[^\d]/g,'')"></span>
									        <label id="custom_price_btn">确定</label>
								        </span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="line brand clearfix">
          <div class="limit"><label>品牌：</label><span class="limited bra">不限</span></div>
          <div class="secondList clearfix">
            <ul class="seccontai brandtain" id="brand_ul"></ul>
            <div class="openMore" id="braMore"><span>展开</span><i class="down"></i></div>
          </div>
        </div>
        <div class="line serve clearfix">
          <div class="limit"><label>服务：</label><span class="limited ser">不限</span></div>
          <div class="limitlist">
            <div class="servebox">
              <ul class="servecontain" id="service_ul"></ul>
              <div class="takeMore" id="serMore"><span>展开</span><i class="down"></i></div>
            </div>
          </div>
        </div>
        <div class="dirfoot clearfix">
          <div class="numLeft"><span class="riNum" id="totalCount"></span><span class="riWord">家满足条件</span></div>
          <div id="filter_dis" class="filter-dis discontain"></div>
          <span class="clearall" id="clear_filter_btn" style="display: none">清空条件</span>
        </div>
      </div>
    </div>
  </div>
  <div class="searchingList clearfix">
    <div class="listLeft">
      <div class="list_head">
        <span class="li_he_active">编辑推荐</span><span class="ace" data-order-column="productScore" data-order-type="asc">好评</span><span class="dace" data-order-column="price" data-order-type="desc">价格</span>
      </div>
      <div id="list_content"></div>
      <div class="paging m-pager"></div>
    </div>
    <div class="listRight">
      <div id="r_map" style="height: 500px;"></div>
    </div>
  </div>
  <%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/hotel/hotelSearchList.js"></script>
<script type="text/javascript" src="/js/plan/changeHotelList.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script type="text/html" id="pos_tpl">
  <li class="radio" data-region-id="{{id}}">{{name}}</li>
</script>
<script type="text/html" id="brand_tpl">
  <li class="radio" data-brand-id="{{brandId}}">{{brandName}}</li>
</script>
<script type="text/html" id="service_tpl">
  <li class="checkbox" data-service-id="{{serviceId}}">{{serviceName}}</li>
</script>
<script type="text/html" id="filter_dis_tpl">
  <span class="forone" data-filter-id="{{id}}" data-filter-mode="{{mode}}">{{text}}{{if mode != 'startDate' && mode != 'endDate'}}<label>×</label>{{/if}}</span>
</script>
<script type="text/html" id="hotel_item">
  <div class="list_body">
    <div class="body_detail clearfix">
      <div class="body_pic"><img src="{{cover | imageResize:225,135}}"></div>
      <div class="body_describ clearfix">
        <div class="address">
          <a href="/yhypc/plan/changeHotelDetail.jhtml?hotelId={{id}}">
            <p class="name"><span class="nametitle">{{index}}</span>{{name}}</p></a>
          {{each regionNameList as regionName}}<p class="gps">【{{regionName}}】</p>{{/each}}
          <p class="de_address" title="{{address}}">{{address | briefText:28}}</p>
          <div class="commend">
            <span class="sc_blue"><span class="score">{{productScore}}</span>/5分</span>
            <span class="level_star"></span>
            <span class="sc_orage"><span class="comnum">{{commentCount}}</span>条评论</span>
          </div>
        </div>
        <div class="price">
          <div class="price_count">
            <span class="rmb">¥</span><span class="num">{{price}}</span><span class="qi">起</span>
          </div>
          <a class="tackdetail" href="/yhypc/plan/changeHotelDetail.jhtml?hotelId={{id}}">查看详情</a>
        </div>
      </div>
    </div>
  </div>
</script>
</html>