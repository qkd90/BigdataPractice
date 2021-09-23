<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <%@include file="../../yhypc/public/header.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>游轮旅游列表</title>
  <link rel="stylesheet" href="/css/public/pager.css">
  <link rel="stylesheet" href="/css/cruiseship/cruiseship_list.css">
</head>
<body class="cruise">
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp"%>
  <!--内容-->
  <div class="wrap">
    <div class="container-box">
      <h3>您在这里：<a href="/yhypc/index/index.jhtml">首页</a>><a href="/yhypc/cruiseShip/index.jhtml">邮轮旅游首页</a>><span>邮轮旅游</span></h3>
      <div class="form-container">
        <form>
          <div class="form-group clearfix">
            <i class="pull-left">航线：</i>
            <label class="checkbox-mask pull-left">
              <input type="radio" name="route" value="" <s:if test="cruiseShipSearchRequest.route == null">checked</s:if> id="routeAll">
                    <span class="mask">
                        <span class="mask-bg"></span>不限
                    </span>
            </label>
            <div class="radio-wrap clearfix">
              <s:iterator value="cruiseshipLines" var="cruiseshipLine">
                <label class="radio-mask">
                  <input type="radio" name="route" data-name="<s:property value="#cruiseshipLine.name"/>" value="<s:property value="#cruiseshipLine.id"/>" <s:if test="#cruiseshipLine.id == cruiseShipSearchRequest.route">checked</s:if>>
                  <div class="mask">
                    <span class="mask-bg"></span><s:property value="#cruiseshipLine.name"/>
                  </div>
                </label>
              </s:iterator>
            </div>
          </div>
          <div class="form-group clearfix">
            <i>品牌：</i>
            <label class="checkbox-mask">
              <input type="radio" name="brand" value="" <s:if test="cruiseShipSearchRequest.brand == null">checked</s:if>>
              <div class="mask">
                <span class="mask-bg"></span>不限
              </div>
            </label>
            <div class="radio-wrap">
              <s:iterator value="cruiseshipBrands" var="cruiseshipBrand">
                <label class="radio-mask">
                  <input type="radio" name="brand" data-name="<s:property value="#cruiseshipBrand.name"/>" value="<s:property value="#cruiseshipBrand.id"/>" <s:if test="#cruiseshipBrand.id == cruiseShipSearchRequest.brand">checked</s:if>>
                  <div class="mask">
                    <span class="mask-bg"></span><s:property value="#cruiseshipBrand.name"/>
                  </div>
                </label>
              </s:iterator>
            </div>
          </div>
          <%--<div class="form-group clearfix">--%>
            <%--<i>开航日期：</i>--%>
            <%--<label class="checkbox-mask">--%>
              <input type="hidden" name="date" value="<c:out escapeXml='true' value='${cruiseShipSearchRequest.date}'/>" checked>
              <%--<div class="mask">--%>
                <%--<span class="mask-bg"></span>不限--%>
              <%--</div>--%>
            <%--</label>--%>
            <%--<div class="radio-wrap form-month-range">--%>

            <%--</div>--%>
          <%--</div>--%>
          <div class="form-group newform-group clearfix">
            <i class="pull-left">出游信息：</i>
            <label class="checkbox-mask pull-left">
              <input type="radio" name="duration" value="" checked>
              <div class="mask">
                <span class="mask-bg"></span>不限
              </div>
            </label>

            <div class="collapse-group pull-left">
                          <span class="triangle-down">
                              行程天数
                              <div class="border-mask"></div>
                          </span>
              <div class="collapse-wrap radio-wrap duration-wrap clearfix">
                <label style="margin-bottom: 0px" class="radio-mask">
                  <input type="radio" name="duration" data-name="1-5天" data-min-day="1" data-max-day="5">

                  <div class="mask">
                    <span class="mask-bg"></span>1-5天
                  </div>
                </label>
                <label style="margin-bottom: 0px" class="radio-mask">
                  <input type="radio" name="duration" data-name="6-10天" data-min-day="6" data-max-day="10">

                  <div class="mask">
                    <span class="mask-bg"></span>6-10天
                  </div>
                </label>
                <label style="margin-bottom: 0px" class="radio-mask">
                  <input type="radio" name="duration" data-name="11-15天" data-min-day="11" data-max-day="15">
                  <div class="mask">
                    <span class="mask-bg"></span>11-15天
                  </div>
                </label>

              </div>
            </div>


          </div>
        </form>
        <div class="result">
          <p>
            为您找到<span id="totalProduct">0</span>个产品
            <span id="form-sum-condition" class="form-sum-condition">
            </span>
            <a href="javascript:void(0);" class="clear-all-sel-label hide">清除条件</a>
          </p>
        </div>
      </div>
    </div>
    <div class="content-container clearfix">
      <%--<h3>编辑推荐</h3>--%>
      <ul class="content-nav clearfix select-sort">
        <li class="li-active" data-order-column="satisfaction" data-order-type="desc">编辑推荐</li>
        <li data-order-column="price" data-order-type="asc">价格<span class="desc-up"></span></li>
        <li data-order-column="startDate" data-order-type="desc">航期<span class="desc-down"></span></li>
      </ul>
    </div>
    <ul class="tablelist">




    </ul>
    <div class="paging m-pager">

    </div>
  </div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

<script type="text/html" id="tpl-cruiseship-list-item">
  <li class="tablelist-every">
    <div class="product clearfix">
      <div class="product-info clearfix">
        <div class="product-img">
          <img src="<%=com.zuipin.util.QiniuUtil.URL%>{{cover}}?imageView2/1/w/211/h/135/q/75" alt="尚波游艇出海体验">
        </div>
        <div class="product-txt">
          <h3><a href="/cruiseship_detail_{{id}}.html">{{name}}</a></h3>
          <p class="p-class"><a href="javascript:void(0);">{{startCity}}出发</a>{{if (routes.length > 0)}}<i>|</i>{{routes}}{{/if}}</p>
          <em><span>{{satisfaction/20}}分</span>/5分</em>
          <p style="margin-top: 11px; margin-left: 5px;"><span style="font-size: 14px;">出发日期：{{startDate}}</span></p>
        </div>
      </div>
      <div class="product-price">
        <h3><sup>￥</sup>{{price}}<sub>起</sub></h3>
        <a href="/cruiseship_detail_{{id}}.html">查看详情</a>
      </div>
    </div>
  </li>
</script>



<script type="text/javascript" src="/lib/util/pager.js"></script>
<script src="/js/cruiseship/cruiseship_list.js"></script>
</html>
