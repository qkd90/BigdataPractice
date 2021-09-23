<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/1/13
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <%@include file="../../yhypc/public/header.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>游记攻略列表</title>
  <link rel="stylesheet" href="/css/public/pager.css">
  <link rel="stylesheet" href="/css/recommendPlan/recommendPlan_list.css">

</head>
<body  class="travel">
<%@include file="../../yhypc/public/nav_header.jsp"%>
<div class="list_content">
  <div class="container-box">
    <p class="dahang">您在这里：&nbsp;<a class="shouye" href="/yhypc/index/index.jhtml">首页</a> >  <a class="shouye" href="/yhypc/recommendPlan/index.jhtml">游记攻略首页</a> > <span class="menu2">游记攻略列表</span>      </p>
    <div class="form-container">
      <div class="form-group list_box">
        <i>出发日期：</i>
        <label class="checkbox-mask">
          <input type="radio" name="startDate" value="" checked>
          <div class="mask">
            <span class="mask-bg"></span>不限
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="startDate" data-start-month="1" data-end-month="3" value="1-3月">
          <div class="mask">
            <span class="mask-bg"></span>1-3月
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="startDate" data-start-month="4" data-end-month="6" value="4-6月">
          <div class="mask">
            <span class="mask-bg"></span>4-6月
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="startDate" data-start-month="7" data-end-month="9" value="7-9月">
          <div class="mask">
            <span class="mask-bg"></span>7-9月
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="startDate" data-start-month="10" data-end-month="12" value="10-12月">
          <div class="mask">
            <span class="mask-bg"></span>10-12月
          </div>
        </label>
      </div>

      <div class="form-group list_box">
        <i>天数：</i>
        <label class="checkbox-mask">
          <input type="radio" name="dayRange" value="" checked>
          <div class="mask">
            <span class="mask-bg"></span>不限
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="dayRange" data-start-day="0" data-end-day="2"  value="3天以内">
          <div class="mask">
            <span class="mask-bg"></span>3天以内
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="dayRange" data-start-day="3" data-end-day="5" value="3-5天">
          <div class="mask">
            <span class="mask-bg"></span>3-5天
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="dayRange" data-start-day="6" data-end-day="9" value="6-9天">
          <div class="mask">
            <span class="mask-bg"></span>6-9天
          </div>
        </label>
        <label class="radio-mask">
          <input type="radio" name="dayRange" data-start-day="10" data-end-day="1000" value="10天以上">
          <div class="mask">
            <span class="mask-bg"></span>10天以上
          </div>
        </label>
      </div>
      <div class="result">
        <p>
          为您找到<span id="totalProduct">0</span>条游记
          <input id="keyword" type="hidden" value="<c:out escapeXml='true' value='${recommendPlanSearchRequest.name}'/>">
          <span id="form-sum-condition" class="form-sum-condition">
          </span>
          <a class="clear-all-sel-label" style="display: none;">清除条件</a>
        </p>
      </div>
    </div>
  </div>
  <div class="bianji_tuijian">
    <div class="tuijian_jd">
      <div class="tuijina_header">
        <div>
          <ul class="tuijianhaoping">
            <li class="xuanzhong" style="width: 88px;" data-order-column="collectNum" data-order-type="desc">编辑推荐</li>
            <li class="li2" data-order-column="viewNum" data-order-type="desc">热门游记<span class="desc"></span></li>
            <li class="li3" data-order-column="createTime" data-order-type="desc">最新发布<span class="desc"></span></li>
          </ul>
          <div style="clear: both;"></div>
        </div>
      </div>
      <div class="allyouji">
      </div>
      <div class="paging m-pager">
      </div>
      <div style="clear: both;"></div>
      <div style="clear: both;"></div>
    </div>
    <div style="clear: both;"></div>
  </div>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

<script type="text/html" id="tpl-recommendPlan-list-item">
  <div class="youjizhuti">
    <a href="/recommendplan_detail_{{id}}.html">
      <div class="youjileft">
        <div class="yjl_pic">
            <img src="{{coverPath}}">
        </div>
        <div class="yjl_wenzi">
          <p class="yjl_title">{{name}}</p>
          <p class="yjl_suolve">{{description}}</p>
        </div>
      </div>
      <div class="youjiright">
        <div class="liulansoucang">
          <ul>
            <li>
              <div class="liulan"></div>
              <p>浏览：<span class="llnum">{{viewNum}}</span></p>
            </li>
            <li>
              <div class="shoucang"></div>
              <p>收藏：<span class="scnum">{{collectNum}}</span></p>
            </li>
          </ul>

          <div style="clear: both;"></div>
        </div>
      </div>
      <div style="clear: both;"></div>
    </a>
  </div>

</script>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/recommendPlan/recommendPlan_list.js"></script>
</html>
