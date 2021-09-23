<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2016-06-28,0028
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>${city.name}高端定制旅游_${city.name}包团定制旅游_${city.name}精品定制旅游-旅行帮</title>
  <meta name="keywords" content="${city.name}高端定制,${city.name}高端旅游,${city.name}包团旅游,${city.name}小众包团,${city.name}精品旅游线路"/>
  <meta name="description" content="旅行帮提供${city.name}高端定制,${city.name}高端旅游,${city.name}包团旅游,${city.name}小众包团等定制旅游线路，${city.name}高端旅游定制就找旅行帮"/>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/list.css" rel="stylesheet" type="text/css">

  <link href="/css/line/prd_2in1.css" rel="stylesheet" type="text/css">
  <link href="/css/line/style_v3.css" rel="stylesheet" type="text/css">
  <link href="/css/line/widget.css" rel="stylesheet" type="text/css">
</head>
<body myname="customMadeList" class="lines_List index1000">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/listheader.jsp"></jsp:include>
<div class="contentcontainer clearfix">
  <input type="hidden" id="blockCityCode" value="602">
  <div class="main fl">
    <div class="crumbs">
      <%--<p>--%>
        <%--<a href="/domestic/">--%>
          <%--国内旅游</a>--%>
        <%--<i>&gt;</i>--%>
        <%--<a href="http://www.tuniu.com/guide/d-hainan-900/">--%>
          <%--海南旅游</a>--%>
        <%--<i>&gt;</i>--%>
        <%--<a href="http://www.tuniu.com/guide/d-sanya-906/">--%>
          <%--三亚旅游</a>--%>
        <%--&gt;&nbsp;广州到三亚自助游    </p>--%>
    </div>
    <style>
    .wohide{
      display : none;
    }
  </style>
    <!--start cate_nav-->

    <div class="cate_nav mb_10 an_mo" liwithhan="category_筛选项">
      <!--start prop_list-->
      <div class="prop_list prop-list-new clearfix">
        <!-- 线路玩法 -->

        <!-- 出发城市 -->
        <div class="clearfix prop_item needed_filter mult_select hasMoreChoice">
          <dl class="clearfix poi_selecters">
            <dt class="startdt"><span class="list_gaiban_icon x_city"></span>出发城市</dt>
            <dd class="oneLine this_height startdd">
              <ul class="list_3 clearfix" id="startCity">
                <li>
                  <a class="input_hide cur_city" href="javascript:void(0);" data-id="">全部</a>
                </li>
              </ul>
              <span id="moreCity" style="display: none;">更多</span>
            </dd>
          </dl>
          <%--<div class="multioption">--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="multipleselect"><span class="addicon tn_fontface">+</span>多选</a>--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="moreselector" style="display: none;">更多</a>--%>
          <%--</div>--%>
          <%--<p class="confAndCancel">--%>
          <%--<input type="button" value="确定" class="confirm mulAndMore">--%>
          <%--<a href="javascript:void(0)" class="cancel mulAndMore" defaultchecked="">取消</a>--%>
          <%--</p>--%>
        </div>
        <!-- 行程天数 -->
        <div class="prop_item needed_filter mult_select hasMoreChoice " id="lineDay">
          <dl class="clearfix poi_day_selecter xc_days">
            <dt><span class="list_gaiban_icon x_day"></span>行程天数</dt>
            <dd class="oneLine">
              <ul class="list_3 clearfix duration_select">
                <li>
                  <a class="input_hide cur_city" href="javascript:void(0);" data-day="">全部 </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="1">
                    1                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="2">
                    2                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="3">
                    3                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="4">
                    4                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="5">
                    5                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="6">
                    6                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="7">
                    7                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="8">
                    8                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="9">
                    9                                    天                                </a>
                </li>
                <li class="filter_input ">
                  <a href="javascript:void(0);" data-day="[10 TO *]">
                    10天以上                                </a>
                </li>
              </ul>
            </dd>
          </dl>
          <%--<div class="multioption">--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="multipleselect"><span class="addicon tn_fontface">+</span>多选</a>--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="moreselector" style="display: none;">更多</a>--%>
          <%--</div>--%>
          <%--<p class="confAndCancel">--%>
          <%--<input type="button" value="确定" class="confirm mulAndMore">--%>
          <%--<a href="javascript:void(0)" class="cancel mulAndMore" defaultchecked="">取消</a>--%>
          <%--</p>--%>
        </div>
        <!-- 出游时间 -->
        <div class="clearfix prop_item " id="startDay">
          <dl>
            <dt><span class="list_gaiban_icon x_time"></span>出游时间</dt>
            <dd class="oneLine">
              <div class="calendar">
                <div>
                  <input id="begin_date" type="text" value="" readonly style="color: rgb(153, 153, 153);" onfocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change()}})">                                        <input type="hidden" id="hd_begin_date" name="hd_begin_date" value="">
                </div>
                <div>—</div>
                <div>
                  <input id="end_date" type="text" value="" readonly style="color: rgb(153, 153, 153);" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'begin_date\',{d:1})}',onpicked:function(){$(this).change()}})">                                        <input type="hidden" id="hd_end_date" name="hd_end_date" value="">
                </div>
                <%--<a href="javascript:void(0);">中秋节</a>--%>
                <%--<a href="javascript:void(0);">国庆节</a>--%>
              </div>
            </dd>
          </dl>
        </div>
        <!-- 成团地点start -->
        <!-- 成团地点end -->
        <!-- 自定义筛选条件 -->
        <div class="clearfix prop_item needed_filter mult_select hasMoreChoice" id="traffic">
          <dl class="clearfix poi_day_selecter xc_days">
            <dt><span class="list_gaiban_icon x_jt_style"></span>交通类型</dt>
            <dd class="oneLine">
              <ul class="list_3 clearfix duration_select" id="adv_selcts_7">
                <li class="filter_input">
                  <a class="input_hide cur_city" href="javascript:void(0);" data-traffic="">全部 </a>
                </li>
                <li class="filter_input">
                  <a href="javascript:void(0);" data-traffic="汽车往返">汽车往返</a>
                </li>
                <li class="filter_input">
                  <a href="javascript:void(0);" data-traffic="火车往返">火车往返</a>
                </li>
                <li class="filter_input">
                  <a href="javascript:void(0);" data-traffic="飞机往返">飞机往返</a>
                </li>
                <li class="filter_input">
                  <a href="javascript:void(0);" data-traffic="飞机-火车">飞机-火车</a>
                </li>
              </ul>
            </dd>
          </dl>
          <%--<div class="multioption">--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="multipleselect"><span class="addicon tn_fontface">+</span>多选</a>--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="moreselector" style="display: none;">更多</a>--%>
          <%--</div>--%>
          <%--<p class="confAndCancel">--%>
          <%--<input type="button" value="确定" class="confirm mulAndMore">--%>
          <%--<a href="javascript:void(0)" class="cancel mulAndMore" defaultchecked="">取消</a>--%>
          <%--</p>--%>
        </div>
        <!-- 自定义筛选条件 -->
        <div class="clearfix prop_item needed_filter mult_select hasMoreChoice">
          <dl class="clearfix poi_day_selecter xc_days">
            <dt><span class="list_gaiban_icon x_cp_tese"></span>线路特色</dt>
            <dd class="oneLine">
              <ul class="list_3 clearfix duration_select" id="tese">
                <li class="filter_input">
                  <a class="input_hide cur_city" href="javascript:void(0);">全部</a>
                </li>
              </ul>
            </dd>
          </dl>
          <%--<div class="multioption">--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="multipleselect"><span class="addicon tn_fontface">+</span>多选</a>--%>
          <%--<a href="javascript:void(0)" rel="nofollow" class="moreselector" style="display: none;">更多</a>--%>
          <%--</div>--%>
          <%--<p class="confAndCancel">--%>
          <%--<input type="button" value="确定" class="confirm mulAndMore">--%>
          <%--<a href="javascript:void(0)" class="cancel mulAndMore" defaultchecked="">取消</a>--%>
          <%--</p>--%>
        </div>
      </div>
      <!--end prop_list-->
    </div>
    <div class="filter">
      <div class="sorting">
        <ul class="sorting_btns" id="sort">
          <li class="default">
            <a class="sort_option" href="javascript:void(0)" title="产品推荐">
              <span>推荐</span>
            </a>
          </li>
          <li class="cur">
            <a class="crt" href="javascript:void(0)" title="按综合排序">
              <span>综合</span>
            </a>
          </li>
          <li>
            <a class="sort_option" href="javascript:void(0)" title="按30天销量排序">
              <span>销量</span>
            </a>
          </li>
          <li>
            <a class="dbsort sort_option" href="javascript:void(0)" title="按价格排序">
              <span>价格</span>
            </a>
          </li>
        </ul>
        <div class="rank_priceform">
          <!--focus显示边框-->
          <div class="fm_price">
            <p class="mr3">价格区间</p>
            <p>
              <input id="first_price" type="text" value="">
              <span> - </span>
              <input id="second_price" type="text" value="">
            </p>
            <div class="btns">
              <span class="reset clearprice">重置价格</span>
					<span class="btn">
						<button id="confirm_btn" type="submit" onclick="poiResetPrice()">确定</button>
					</span>
            </div>
          </div>
        </div>
        <div class="specil_filter">
          <p>
          </p>
        </div>
      </div>
    </div>
    <link rel="stylesheet" href="/css/line/entrance-classification.css">
    <div class="thelist an_mo" liwithhan="category_列表页详情区域">
      <!-- 不同的产品类型对应不同的class -->
      <ul class="thebox clearfix zizhubox" id="lineList"></ul>

    </div>
    <!--end pro_list-->
    <!--start 分页（除推荐页）-->
    <div class="m-pager st cl">
    </div>
    <!--end 分页-->
    <!-- 普通楼层开始 -->
    <div class="galleryblock local" id="block_floor_0">
      <div class="top clearfix">
        <div class="productname fl"><span class="cityname">${city.name}</span>热门跟团游</div>
        <div class="moredetail fr">
          <a href="${GENTUAN_PATH}/group_tour_${city.id}.html" target="_blank">更多跟团游 &gt;</a>
        </div>
      </div>
      <div class="gallery-lists">
        <ul class="current gallery-list clearfix" id="hotLocal">
        </ul>
      </div>
    </div>
    <!-- 普通楼层结束-->
    <!-- 普通楼层开始 -->
    <div class="galleryblock ticket" id="block_floor_1">
      <div class="top clearfix">
        <div class="productname fl"><span class="cityname">${city.name}</span>热门景点</div>
        <div class="moredetail fr">
          <a target="_blank" href="${SCENIC_PATH}/scenic_list.html?cityCode=${city.id}">更多景点 &gt;</a>
        </div>
      </div>
      <div class="gallery-lists">
        <ul class="current gallery-list clearfix" id="hotScenic">
        </ul>
      </div>
    </div>
    <!-- 普通楼层结束-->
    <!-- 普通楼层开始 -->
    <div class="galleryblock hotel" id="block_floor_2">
      <div class="top clearfix">
        <div class="productname fl"><span class="cityname">${city.name}</span>热门酒店</div>
        <div class="moredetail fr">
          <a target="_blank" href="${HOTEL_PATH}/hotel_list.html?hotelCityId=${city.id}">更多酒店 &gt;</a>
        </div>
      </div>
      <div class="gallery-lists">
        <ul class="current gallery-list clearfix" id="hotHotel">
        </ul>
      </div>
    </div>
    <!-- 普通楼层结束-->
  </div>
  <!-- start 右侧 -->
  <div class="side fr">
    <input type="hidden" id="ga" value="国内">
    <!-- 列表页右侧区块 -->
    <div class="statistics sideblock">
      <div class="clearfix">
        <div class="satisfactionrateblock fl">
          <span class="satisfactionrateicon"></span>
          <div class="satisfactionrate">
            <div class="text">满意度</div>
            <div class="rate"><span class="number">93</span>%</div>
          </div>
        </div>
        <div class="infoblock">
          <div><span>已关注人数：</span><span class="numberunit">883745人次</span></div>
        </div>
      </div>
    </div>
    <%--<div class="list sideblock">--%>
    <%--<div class="carouselblock">--%>
    <%--<div class="carouselheader">清仓特卖</div>--%>
    <%--<div class="carousel">--%>
    <%--<ul class="temai">--%>
    <%--<li>--%>
    <%--<div class="tm_prod">--%>
    <%--<a href="http://temai.tuniu.com/tours/212064525" class="tm_img" target="_blank">--%>
    <%--<img src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/7F/Cii9EVaqaBOIdkXAAACL7gPBfnAAABq8APJPowAAIwG057_w300_h200_c1_t0.jpg" width="160" height="90">--%>
    <%--</a>--%>
    <%--<p class="prod_lab spe_icon_wei">--%>
    <%--<span>尾货</span>--%>
    <%--</p>--%>
    <%--<p class="time_bg"></p>--%>
    <%--<p end-data="" start-data="2016-07-14 09:00:00" id="timeDes" class="time_des">剩余:<span class="tnIndexDay">31</span>天<span class="tnIndexH">21</span>时<span class="tnIndexM">45</span>分<span class="tnIndexS">14</span>秒</p>--%>
    <%--</div>--%>
    <%--<dl class="tm_line">--%>
    <%--<dt class="">--%>
    <%--<a href="http://temai.tuniu.com/tours/212064525" class="" target="_blank">[蜜月优选]&lt;三亚之恋深度4日自由行&gt;共度甜蜜二人世界，让爱在缤纷海岸上升华</a>--%>
    <%--</dt>--%>
    <%--<dd class="tm_price">--%>
    <%--<p><span class="tn_price">¥<em>1297</em>起</span><s class="cx_price">¥1999</s><span class="tm_zk">6.5折</span></p>--%>
    <%--</dd>--%>
    <%--</dl>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<div class="tm_prod">--%>
    <%--<a href="http://temai.tuniu.com/tours/212064737" class="tm_img" target="_blank">--%>
    <%--<img src="http://m.tuniucdn.com/filebroker/cdn/olv/30/33/3033df41725e9cbf788f5e6a5e737004_w300_h200_c1_t0.jpg" width="160" height="90">--%>
    <%--</a>--%>
    <%--<p class="prod_lab spe_icon_wei">--%>
    <%--<span>尾货</span>--%>
    <%--</p>--%>
    <%--<p class="time_bg"></p>--%>
    <%--<p end-data="" start-data="2016-06-30 09:00:00" id="timeDes1" class="time_des">剩余:<span class="tnIndexDay">17</span>天<span class="tnIndexH">21</span>时<span class="tnIndexM">45</span>分<span class="tnIndexS">15</span>秒</p>--%>
    <%--</div>--%>
    <%--<dl class="tm_line">--%>
    <%--<dt class="">--%>
    <%--<a href="http://temai.tuniu.com/tours/212064737" class="" target="_blank">&lt;海南三亚-南山5日游&gt;暑期亲子大促，畅游蜈支洲，4晚5钻维景海景</a>--%>
    <%--</dt>--%>
    <%--<dd class="tm_price">--%>
    <%--<p><span class="tn_price">¥<em>1870</em>起</span><s class="cx_price">¥2529</s><span class="tm_zk">7.4折</span></p>--%>
    <%--</dd>--%>
    <%--</dl>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>

    <%--</div>--%>
    <%--</div>--%>
    <!-- 小帮订制 -->
    <a target="_blank" href="${REQUIRE_PATH}/lvxbang/customRequire/fill.jhtml" rel="nofollow" title="小帮定制" class="niuren_ding">
      <img src="/images/line/bangmei_1000.jpg" alt="小帮定制">
    </a>

    <%--<div class="latestactivity sideblock an_mo" liwithhan="POS-9">--%>
    <%--<textarea style="display:none" class="TomJerry">{"system":"WAC","adlist":[1105,63,89,943]}</textarea>--%>
    <%--<div class="carouselblock">--%>
    <%--<div class="carouselheader fl">最新活动</div>--%>
    <%--<div class="carousel">--%>
    <%--<div class="carousel-top clearfix">--%>
    <%--<ul class="carousel-indicatorls fr">--%>
    <%--<li class="carousel-indicator fl active" data-slide-to="0"></li>--%>
    <%--<li class="carousel-indicator fl" data-slide-to="1"></li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--<ul class="carousel-slidels clearfix" style="left: 0%;">--%>
    <%--<li class="carousel-slide fl active">--%>
    <%--<div class="carousel-item first">--%>
    <%--<div class="carousel-imgbox">--%>
    <%--<a href="http://www.tuniu.com/gt/guangfacxqq/" target="_blank">--%>
    <%--<img src="http://img2.tuniucdn.com/u/mainpic/liebiao/2016/fenqi.jpg">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="carousel-item ">--%>
    <%--<div class="carousel-imgbox">--%>
    <%--<a href="http://www.tuniu.com/theme/haidao/" target="_blank">--%>
    <%--<img src="http://img2.tuniucdn.com/u/mainpic/liebiao/201504/island.jpg">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<li class="carousel-slide fl ">--%>
    <%--<div class="carousel-item first">--%>
    <%--<div class="carousel-imgbox">--%>
    <%--<a href="http://www.tuniu.com/gt/sanya/" target="_blank">--%>
    <%--<img src="http://img2.tuniucdn.com/u/mainpic/liebiao/201504/glsy.jpg">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="carousel-item ">--%>
    <%--<div class="carousel-imgbox">--%>
    <%--<a href="http://www.tuniu.com/niuren/" target="_blank">--%>
    <%--<img src="http://img2.tuniucdn.com/u/mainpic/liebiao/225125/nrzx.jpg">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <!-- 机+酒 -->

    <div class="aside_tab order_tab">
      <ul class="tabs clearfix">
        <%--<li class="order_tab_pkg current">自由行</li>--%>
        <li class="order_tab_flight current">机票</li>
        <li class="order_tab_hotel">酒店</li>
        <li class="order_tab_train">火车票</li>
      </ul>
      <ul class="tab_contents">
        <%--<li class="current">--%>
          <%--<div id="diyTypeField" class="diy_type_field" style="display:none">--%>
            <%--<label>--%>
              <%--<input type="radio" name="diy_type" value="flightHotel" checked="">--%>
              <%--<span>机票+酒店</span>--%>
            <%--</label>--%>
            <%--<label>--%>
              <%--<input type="radio" name="diy_type" value="trainHotel">--%>
              <%--<span>火车票+酒店</span>--%>
            <%--</label>--%>
          <%--</div>--%>
          <%--<div class="J_flightHotel">--%>
            <%--<form method="GET" id="flight_hotel_form" target="_blank" onsubmit="return false;">--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">出发地：</label>--%>
                <%--<input id="airplane_origin" class="field_input" placeholder="选择出发地" type="text" autocomplete="off" code="602" value="广州">--%>
              <%--</div>--%>


              <%--<div class="form_field">--%>
                <%--<label class="field_name">目的地：</label>--%>
                <%--<input id="airplane_destination" class="field_input" placeholder="选择目的地" type="text" autocomplete="off" code="906" value="三亚">--%>
              <%--</div>--%>


              <%--<div class="form_field">--%>
                <%--<label class="field_name">出发时间：</label>--%>
                <%--<input class="field_input" id="d11" type="text">--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">返程时间：</label>--%>
                <%--<input class="field_input" id="d12" type="text">--%>
              <%--</div>--%>
              <%--<div class="form_field adult_num_field">--%>
                <%--<label class="field_name">成人：</label>--%>
                <%--<div class="field_select_2 w_93">--%>
                  <%--<input type="text" readonly="" value="2">--%>
                  <%--<i class="header_icon icon_arrow"></i>--%>
                  <%--<ul class="dropdown_list" style="display: none;">--%>
                    <%--<li>1</li>--%>
                    <%--<li>2</li>--%>
                    <%--<li>3</li>--%>
                    <%--<li>4</li>--%>
                    <%--<li>5</li>--%>
                    <%--<li>6</li>--%>
                    <%--<li>7</li>--%>
                    <%--<li>8</li>--%>
                    <%--<li>9</li>--%>
                    <%--<li>10</li>--%>
                    <%--<li>11</li>--%>
                    <%--<li>12</li>--%>
                    <%--<li>13</li>--%>
                    <%--<li>14</li>--%>
                    <%--<li>15</li>--%>
                    <%--<li>16</li>--%>
                    <%--<li>17</li>--%>
                    <%--<li>18</li>--%>
                    <%--<li>19</li>--%>
                    <%--<li>20</li>--%>
                    <%--<li>21</li>--%>
                    <%--<li>22</li>--%>
                    <%--<li>23</li>--%>
                    <%--<li>24</li>--%>
                    <%--<li>25</li>--%>
                    <%--<li>26</li>--%>
                    <%--<li>27</li>--%>
                    <%--<li>28</li>--%>
                    <%--<li>29</li>--%>
                    <%--<li>30</li>--%>
                    <%--<li>31</li>--%>
                    <%--<li>32</li>--%>
                    <%--<li>33</li>--%>
                    <%--<li>34</li>--%>
                    <%--<li>35</li>--%>
                    <%--<li>36</li>--%>
                    <%--<li>37</li>--%>
                    <%--<li>38</li>--%>
                    <%--<li>39</li>--%>
                    <%--<li>40</li>--%>
                    <%--<li>41</li>--%>
                    <%--<li>42</li>--%>
                    <%--<li>43</li>--%>
                    <%--<li>44</li>--%>
                    <%--<li>45</li>--%>
                    <%--<li>46</li>--%>
                    <%--<li>47</li>--%>
                    <%--<li>48</li>--%>
                    <%--<li>49</li>--%>
                    <%--<li>50</li>--%>
                  <%--</ul>--%>
                <%--</div>--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">儿童：</label>--%>
                <%--<div class="field_select_2 w_93">--%>
                  <%--<i class="header_icon icon_arrow"></i>--%>
                  <%--<input type="text" readonly="" value="0">--%>
                  <%--<ul class="dropdown_list" style="display: none;">--%>
                    <%--<li>0</li>--%>
                    <%--<li>1</li>--%>
                    <%--<li>2</li>--%>
                    <%--<li>3</li>--%>
                    <%--<li>4</li>--%>
                    <%--<li>5</li>--%>
                    <%--<li>6</li>--%>
                    <%--<li>7</li>--%>
                    <%--<li>8</li>--%>
                    <%--<li>9</li>--%>
                    <%--<li>10</li>--%>
                    <%--<li>11</li>--%>
                    <%--<li>12</li>--%>
                    <%--<li>13</li>--%>
                    <%--<li>14</li>--%>
                    <%--<li>15</li>--%>
                    <%--<li>16</li>--%>
                    <%--<li>17</li>--%>
                    <%--<li>18</li>--%>
                    <%--<li>19</li>--%>
                    <%--<li>20</li>--%>
                    <%--<li>21</li>--%>
                    <%--<li>22</li>--%>
                    <%--<li>23</li>--%>
                    <%--<li>24</li>--%>
                    <%--<li>25</li>--%>
                    <%--<li>26</li>--%>
                    <%--<li>27</li>--%>
                    <%--<li>28</li>--%>
                    <%--<li>29</li>--%>
                    <%--<li>30</li>--%>
                    <%--<li>31</li>--%>
                    <%--<li>32</li>--%>
                    <%--<li>33</li>--%>
                    <%--<li>34</li>--%>
                    <%--<li>35</li>--%>
                    <%--<li>36</li>--%>
                    <%--<li>37</li>--%>
                    <%--<li>38</li>--%>
                    <%--<li>39</li>--%>
                    <%--<li>40</li>--%>
                    <%--<li>41</li>--%>
                    <%--<li>42</li>--%>
                    <%--<li>43</li>--%>
                    <%--<li>44</li>--%>
                    <%--<li>45</li>--%>
                    <%--<li>46</li>--%>
                    <%--<li>47</li>--%>
                    <%--<li>48</li>--%>
                    <%--<li>49</li>--%>
                    <%--<li>50</li>--%>
                  <%--</ul>--%>
                <%--</div>--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">&nbsp;</label>--%>
                <%--<input class="field_btn" type="submit" value="开始定制" onclick="_gaq.push(['_trackEvent','common_gz','query','开始定制']);">--%>
              <%--</div>--%>

            <%--</form>--%>
          <%--</div>--%>
        <%--</li>--%>
        <li class="current">
          <form method="POST" id="flightSearchForm" action="${TRAFFIC_PATH}/lvxbang/traffic/toFlight.jhtml" target="_blank">
            <div class="switch_btn">
              <img src="http://img1.tuniucdn.com/img/201501041115/index_v3/switch_btn.gif">
            </div>
            <%--<div class="form_field ticket_category">--%>
              <%--<input id="flight_ticket_domestic" type="radio" class="ml10 mr10" checked="" name="ticket_category">--%>
              <%--国内机票--%>
              <!-- <input id="flight_ticket_abroad" type="radio" class="ml10 mr10" name="ticket_category">国际机票 -->
            <%--</div>--%>
            <input type="hidden" name="leaveCity" value="">
            <input type="hidden" name="leavePort" value="">
            <input type="hidden" name="arriveCity" value="">
            <input type="hidden" name="arrivePort" value="">
            <div class="form_field sho">
              <label class="field_name">出发地：</label>
              <input id="flight_origin" class="field_input portinput clickinput" placeholder="选择出发地" type="text" autocomplete="off" value="" name="leaveCityName">
              <div class="posiA Addmore Addmore2 categories_Addmore2"></div>
            </div>
            <div class="form_field sho">
              <label class="field_name">目的地：</label>
              <input id="flight_destination" class="field_input portinput clickinput" placeholder="选择目的地" type="text" autocomplete="off" value="${city.name}" name="arriveCityName">
              <div class="posiA Addmore Addmore2 categories_Addmore2" style="top: 89px;"></div>
            </div>
            <div class="form_field">
              <label class="field_name">出发时间：</label>
              <input id="flight_departure_date" class="field_input" type="text" name="leaveDate" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})">
            </div>
            <div class="form_field">
              <label class="field_name">&nbsp;</label>
              <input class="field_btn" type="button" value="搜索" onclick="SearcherBtn.btnFlightSeach()">
            </div>
          </form>
        </li>
        <li class="">
          <%--<div id="hotelTypeField" class="diy_type_field">--%>
            <%--<label><input type="radio" name="hotel_type" value="internalHotel" checked=""><span> 国内酒店</span></label>--%>
            <%--<label><input type="radio" name="hotel_type" value="externalHotel"><span> 国际酒店</span></label>--%>
          <%--</div>--%>
          <div class="J_internalHotel">
            <form method="post" id="hotelSearchForm" target="_blank" action="${HOTEL_PATH}/hotel_list.html">
              <div class="form_field">
                <label class="field_name">入住城市：</label>
                <input id="hotel_origin" value="${city.name}" class="field_input input clickinput" placeholder="城市名称" type="text" autocomplete="off" name="cities">
                <div class="posiA Addmore Addmore2 categories_Addmore2" style="top: 100px;"></div>
              </div>
              <div class="form_field clickinput">
                <label class="field_name">关键词：</label>
                <input class="field_input input input01 clickinput" placeholder="酒店名/位置/品牌" type="text" name="name" data-url="/lvxbang/hotel/suggest.jhtml">
                <!--关键字提示 clickinput input input01-->
                <div class="posiA categories_div  KeywordTips" style="top: 131px;">
                  <ul>

                  </ul>
                </div>

                <!--错误-->
                <div class="posiA categories_div cuowu textL" style="top: 131px;">
                  <p class="cl">抱歉未找到相关的结果！</p>
                </div>
              </div>
              <div class="form_field">
                <label class="field_name">入住时间：</label>
                <input class="field_input" type="text" id="d21" name="startDate" onfocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change()}});">
              </div>
              <div class="form_field">
                <label class="field_name">离店时间：</label>
                <input class="field_input" type="text" id="d22" name="endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'d21\',{d:1})}',maxDate:'#F{$dp.$D(\'d21\',{d:30})}',onpicked:function(){$(this).change()}});">
              </div>

              <div class="form_field">
                <label class="field_name">&nbsp;</label>
                <input class="field_btn" type="button" value="查询" onclick="SearcherBtn.btnHotelSeach();">
              </div>
              <div class="hide">
                <input type="hidden" name="checkindate">
                <input type="hidden" name="checkoutdate">
                <input type="hidden" name="keyword">
              </div>
            </form>
          </div>
          <%--<div class="J_externalHotel" style="display: none;">--%>
            <%--<form method="GET" id="external_hotel_form" target="_blank">--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">入住城市：</label>--%>
                <%--<input class="field_input J_departCity" placeholder="城市名称" type="text" autocomplete="off">--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">入住时间：</label>--%>
                <%--<input class="field_input J_checkinDate" type="text" id="d91">--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">离店时间：</label>--%>
                <%--<input class="field_input J_checkoutDate" type="text" id="d92">--%>
              <%--</div>--%>
              <%--<div class="form_field">--%>
                <%--<label class="field_name">&nbsp;</label>--%>
                <%--<input class="field_btn J_submitBtn" type="submit" value="查询" onclick="_gaq.push(['_trackEvent','common_gz','query','查询']);">--%>
              <%--</div>--%>
            <%--</form>--%>
          <%--</div>--%>

        </li>
        <li class="">
          <form id="trainSearchForm" method="POST" action="${TRAFFIC_PATH}/lvxbang/traffic/toTrain.jhtml" target="_blank">
            <div class="switch_btn">
              <img src="http://img1.tuniucdn.com/img/201501041115/index_v3/switch_btn.gif">
            </div>
            <!--div class="form_field">
            <input type="radio" class="ml20" name="ticket_type" checked=""><label class="w_auto mr20 ml5">单程</label>
            <input type="radio" name="ticket_type"><label class="w_auto ml5">往返</label>
        </div-->
            <input type="hidden" name="leaveCity" value="">
            <input type="hidden" name="leavePort" value="">
            <input type="hidden" name="arriveCity" value="">
            <input type="hidden" name="arrivePort" value="">
            <div class="form_field sho">
              <label class="field_name">出发站：</label>
              <input id="train_origin" class="field_input clickinput portinput" type="text" autocomplete="off" value="" name="leaveCityName">
              <div class="posiA Addmore Addmore2 categories_Addmore2" style="top: 27px;"></div>
            </div>
            <div class="form_field sho">
              <label class="field_name">到达站：</label>
              <input id="train_destination" class="field_input clickinput portinput" type="text" autocomplete="off" value="${city.name}" name="arriveCityName">
              <div class="posiA Addmore Addmore2 categories_Addmore2" style="top: 63px;"></div>
            </div>
            <div class="form_field">
              <label class="field_name">出发时间：</label>
              <input class="field_input" type="text" id="d31" name="leaveDate" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})">
            </div>
            <div class="form_field">
              <label class="field_name">&nbsp;</label>
              <input type="checkbox">
              <label class="w_auto">仅高铁和动车</label>
            </div>
            <div class="form_field">
              <label class="field_name">&nbsp;</label>
              <input class="field_btn" type="button" value="搜索列车" onclick="SearcherBtn.btnTrainSeach();">
            </div>
          </form>
        </li>
      </ul>
    </div><!-- 热卖线路排行start -->
    <div class="hot_line sideblock an_mo">
      <div class="carouselblock">
        <div class="carouselheader">热卖线路产品排行</div>
        <div class="carousel">
          <ul id="hotLineList">
          </ul>
        </div>
      </div>
    </div>
    <!-- 热卖线路排行end -->
    <!-- 最新点评start -->
    <!-- 最新点评end -->

    <div id="guessyoulike" class="guessyoulike an_mo" liwithhan="category_最近访问"><div class="guesslike_history" id="guesslike_history">
      <ul class="tabs clearfix">
        <li class="current">猜你喜欢</li>
        <li class="">浏览历史</li>
      </ul>
      <div class="tab_contents datalazyload no-action" data-lazyload-type="data" data-lazyload-from="textarea">
        <div class="tab_contents_list guessyoulike">
          <div class="tab_contents_item">
            <ul id="interest">
            </ul>
          </div>
        </div>
      </div>
      <div class="tab_contents datalazyload no-action" data-lazyload-type="data" data-lazyload-from="textarea">
        <div class="tab_contents_list search_history hide">
          <div class="tab_contents_item">
            <ul id="history">
            </ul>
          </div>
        </div>
      </div>
    </div>
    </div>
    <div class="travelraider sideblock">

    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
</body>
</html>

<script type="text/html" id="tpl-line-list-item">
  <li>
    <div class="theinfo clearfix">
      <div class="imgbox">
        <a class="img" href="/line_detail_{{id}}.html" target="_blank">
          <span class="icon dzjp-icon"></span>

          <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{productImg}}" alt="<{{name}}>{{appendTitle}}" style="display: block;">
        </a>
        <p class="sat">


          满意度：<i>96%</i>
        </p><div>
        <a class="comments" href="/line_detail_{{id}}.html" target="_blank">{{orderNum}}人出游</a>
        <a class="comments cent">|</a>
        <a class="comments" href="/line_detail_{{id}}.html" target="_blank">7536人点评</a>
      </div>


        <p></p>



        <div>

        </div>
      </div>
      <dl class="detail">
        <dt>
        <p class="title">
          <a href="/line_detail_{{id}}.html" target="_blank" title="<{{name}}>{{appendTitle}}">
            <span class="f_f00"></span><span class="f_0053aa">&lt;{{name}}&gt;</span>{{appendTitle}}					</a>


          <!-- niu-star -->
          <!-- niu-star -->
        </p>
        <p class="label">

        </p>
        <p class="subtitle">
          <span>{{muiltCityStr}} | </span>
          {{shortComment}}				</p>
        </dt>

        <dd class="tqs">
          <span>团　　期：<a href="/line_detail_{{id}}.html" target="_blank">{{startDays}}</a></span>
          {{if more}}
          <a target="_blank" href="/line_detail_{{id}}.html">更多<span class="inlineblock"></span>
          </a>
          {{/if}}
        </dd>

      </dl>
      <div class="priceinfo">

			<span class="tnPrice">
			    <i>¥</i>{{if price > 0}}<em>{{price}}</em>起	{{else}}<em>来电咨询</em>{{/if}}		</span>
        <a class="sbtn sbtn-24 sbtn-orange" href="/line_detail_{{id}}.html" target="_blank"><span>预订</span></a>
        <a class="favorite" data-favorite-id="{{id}}" data-favorite-type="line"><div class="tn_fontface"></div><span>关注</span></a>
      </div>
    </div>
  </li>
</script>
<script type="text/html" id="tpl-hot-groupTour-item">
  <li class="gallery-list-item fl first ">
    <div class="imgblock">
      <a title="<{{name}}>{{appendTitle}}" href="${GENTUAN_PATH}/line_detail_{{id}}.html" target="_blank">
        <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{cover}}" style="display: inline;">
      </a>
      <div class="dess_bg"></div>
      <div class="dess">&lt;{{name}}&gt;{{appendTitle}}</div>
    </div>
    <div class="exter_mess">
      <div class="box p1 clearfix">
        <div class="satisfactionrate fl">
          <span class="text">满意度</span>
          <span class="rate">{{satisfaction}}%</span>
        </div>
        <div class="comment fr">30条评论</div>
      </div>
      <div class="box clearfix">
        <div class="price fr">
          <span class="unit">¥</span>
          {{if price > 0}}
          <span class="num">{{price}}</span>
          <span class="text">起</span>
          {{else}}
          <span class="num">来电咨询</span>
          {{/if}}
        </div>
      </div>
    </div>
  </li>
</script>
<script type="text/html" id="tpl-hot-scenic-item">
  <li class="gallery-list-item fl first ">
    <div class="imgblock">
      <a title="{{name}}" href="${SCENIC_PATH}/scenic_detail_{{id}}.html" target="_blank">
        {{if cover != null && cover != ""}}
        <img alt="{{name}}" style="display: inline;"
             data-original="{{cover | recommendPlanTripListImg}}">
        {{else}}
        <img alt="{{name}}" style="display: inline;"
             data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/170/h/100/q/75"/>
        {{/if}}
      </a>
      <div class="dess_bg"></div>
      <div class="dess">{{name}}</div>
    </div>
    <div class="exter_mess">
      <div class="box p1 clearfix">
      </div>
      <div class="box clearfix">
        <div class="name fl"><i class="ticket_icon"></i>门票</div>
        {{if price > 0}}
        <div class="price fr">
          <span class="unit">¥</span>
          <span class="num">{{price}}</span>
          <span class="text">起</span>
        </div>
        {{/if}}
      </div>
    </div>
  </li>
</script>
<script type="text/html" id="tpl-hot-hotel-item">
  <li class="gallery-list-item fl first ">
    <div class="imgblock">
      <a title="{{name}}" href="${HOTEL_PATH}/hotel_detail_{{id}}.html" target="_blank">
        {{if cover != null && cover != ""}}
        <img data-original="{{cover}}" alt="{{name}}" style="display: inline;"/>
        {{else}}
        <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/240/h/175/q/75"
             alt="{{name}}" style="display: inline;"/>
        {{/if}}
      </a>
      {{if star > 0}}<span class="gallery_icon">{{star | numToWord}}星级</span>{{/if}}
      <div class="dess_bg"></div>
      <div class="dess">{{name}}</div>
    </div>
    <div class="exter_mess">
      <div class="box p1 clearfix">
      </div>
      <div class="box clearfix">
        <div class="price fr">
          <span class="unit">¥</span>
          <span class="num">{{price}}</span>
          <span class="text">起</span>
        </div>
      </div>
    </div>
  </li>
</script>
<script type="text/html" id="tpl-hot-line-item">
  <li class="clearfix">
    <div class="pic">
      <a href="/line_detail_{{id}}.html" target="_blank">
        <img style="display: inline;" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{productImg}}">
      </a>
    </div>
    <div class="des">
      <p class="name"><a href="/line_detail_{{id}}.html" target="_blank">&lt;{{name}}&gt;{{appendTitle}}</a></p>
      <p class="price"><em>¥{{if price > 0}}{{price}}</em>起{{else}}来电咨询</em>{{/if}}</p>
    </div>
    <div class="hot_num">{{index}}</div>
  </li>
</script>
<script type="text/html" id="tpl-interest-history-item">
  <li class="seller_item clearfix">
    <a href="/line_detail_{{resObjectId}}.html" target="_blank">
      <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}" style="display: block;">
    </a>
    <a href="/line_detail_{{resObjectId}}.html" target="_blank">
      <h6>&lt;{{title}}&gt;</h6>
      <p>{{introduction}}</p>
    </a>
    <p>
       <span class="fl">
         {{if price > 0}}<i>¥ {{price}}</i>起{{else}}<i>¥ 来电咨询</span>{{/if}}
       </span>
    </p>
  </li>
</script>
<script type="text/html" id="tpl-travel-raider-item">
  <div class="travelblock">
    <div class="travelheader">{{name}}旅游攻略</div>
    <div class="travel">
      <div class="travel-top clearfix">
        <div class="bookpage toppage"></div>
        <div class="bookpage bottompage"></div>
        <div class="imgblock fl">
          <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/{{tbAreaExtend.cover}}?imageView2/1/w/90/h/127">
        </div>
        <div class="detailblock fr">
          <div class="textblock"> {{tbAreaExtend.abs}}</div>

        </div>
      </div>
      <ul class="travel-list clearfix">
        <li class="friendlylink"><a href="${SCENIC_PATH}/scenic_list.html?cityCode={{cityCode}}" title="{{name}}景点大全" target="_blank">{{name}}景点大全</a></li>
        <li class="friendlylink"><a href="${RECOMMENDPLAN_PATH}/guide_list.html?cityIds={{cityCode}}" title="{{name}}游玩攻略" target="_blank">{{name}}游玩攻略</a></li>
        <li class="friendlylink"><a href="${DELICACY_PATH}/food_list.html?cityCode={{cityCode}}" title="{{name}}特色美食" target="_blank">{{name}}特色美食</a></li>
        <li class="friendlylink"><a href="${PLAN_PATH}/plan_list.html?cityIdStr={{cityCode}}" title="{{name}}推荐行程" target="_blank">{{name}}推荐行程</a></li>
        <%--<li class="friendlylink"><a href="http://www.tuniu.com/g906/specialty-0-0/" title="{{name}}自助游游记" target="_blank">{{name}}自助游游记</a></li>--%>
        <%--<li class="friendlylink"><a href="${HANDDRAW_PATH}/map_{{cityCode}}.html" title="{{name}}旅游地图" target="_blank">{{name}}旅游地图</a></li>--%>
      </ul>
    </div>
  </div>
</script>
<script type="text/html" id="tpl-city-selector-item">
  <i class="close"></i>
  <dl class="Addmore_dl Addmore_dl2">
    <dt>
    <div class="Addmore_nr">
      <ul>
        <li class="checked"><a href="javaScript:;">热门</a></li>
        <li><a href="javaScript:;">A-E</a></li>
        <li><a href="javaScript:;">F-J</a></li>
        <li><a href="javaScript:;">K-P</a></li>
        <li><a href="javaScript:;">Q-W</a></li>
        <li><a href="javaScript:;">X-Z</a></li>
      </ul>
    </div>
    </dt>
    <dd>
      <label></label>
      <div class="Addmore_nr">
        <ul>
          {{each hot as aArea}}
          <li data-id="{{aArea.id}}">
            <a href="javaScript:;" title="{{aArea.name}}">{{aArea.name}}</a>
          </li>
          {{/each}}
        </ul>
      </div>
    </dd>
    {{each letterSortAreas as letterSortArea}}
    <dd class="disn">
      {{each letterSortArea.letterRange as lrArea}}
      <label>{{lrArea.name}}</label>
      <div class="Addmore_nr">
        <ul>
          {{each lrArea.list as aArea}}
          <li data-id="{{aArea.id}}">
            <a href="javaScript:;" title="{{aArea.name}}">{{aArea.name}}</a>
          </li>
          {{/each}}
        </ul>
      </div>
      {{/each}}
    </dd>
    {{/each}}
  </dl>
  <p class="cl"></p>
</script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
<script src="/js/lvxbang/line/list.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/lvxbang/collect.js"></script>