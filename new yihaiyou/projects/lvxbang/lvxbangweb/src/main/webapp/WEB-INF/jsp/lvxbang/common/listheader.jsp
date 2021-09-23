<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/newcss/announcement_v2.css" rel="stylesheet" type="text/css">
<link href="/newcss/homepage_banner.css" rel="stylesheet" type="text/css">
<link href="/newcss/all_link.css" rel="stylesheet" type="text/css">
<link href="/css/destination.css" rel="stylesheet" type="text/css"/>
<!--head-->
<%--<link href="/css/tBase.css" rel="stylesheet" type="text/css">--%>
<%--<link href="/css/announcement.css" rel="stylesheet" type="text/css">--%>
<%--<link href="/css/index.css" rel="stylesheet" type="text/css">--%>
<%-- 百度统计 --%>
<script>
  //add support for ie

  if (!Array.prototype.forEach) {
    Array.prototype.forEach = function(callback, thisArg) {
      var T, k;
      if (this == null) {
        throw new TypeError(" this is null or not defined");
      }
      var O = Object(this);
      var len = O.length >>> 0; // Hack to convert O.length to a UInt32
      if ({}.toString.call(callback) != "[object Function]") {
        throw new TypeError(callback + " is not a function");
      }
      if (thisArg) {
        T = thisArg;
      }
      k = 0;
      while (k < len) {
        var kValue;
        if (k in O) {
          kValue = O[k];
          callback.call(T, kValue, k, O);
        }
        k++;
      }
    };
  }

  if (!window.console) console = {};
  console.log = console.log || function(){};
  console.warn = console.warn || function(){};
  console.error = console.error || function(){};
  console.info = console.info || function(){};
  //add support for ie end
  var _hmt = _hmt || [];
  (function() {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?127abc95631d19c3a9465860d72f828a";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
  })();
</script>

<!--logoRow_top start-->
<input type="hidden" id="scenic_path" value="${SCENIC_PATH}">
<div class="logoRow"  >
  <div class="w1000">
    <!--lxb_logo_Top start-->
    <a href="${INDEX_PATH}" target="_blank" class="topLogo"></a>
    <!--lxb_logo_top end-->
    <!--search_top start-->
    <div class="topSearch">
      <div class="searchDiv ">
        <div class="posiR categories" data-url="/lvxbang/index/suggestAll.jhtml">
          <input type="text" name="unUsedHiddenValue" value="" style="display:none" autocomplete="off">
          <input type="text" id="txtSearch" data-headsearch="true" placeholder="输入游记/景点/美食/酒店" name="keyword" value="" class="input" autocomplete="off">
          <div class="posiA categories_div KeywordTips" style="display: none;">
            <ul>

            </ul>
          </div>
          <!--错误-->
          <div class="posiA categories_div cuowu textL" style="display: none;">
            <p class="cl">抱歉未找到相关的结果！</p>
          </div>
        </div>
        <a href="javaScript:headerSearch();" class="searchDivA">搜 索</a>
      </div>
    </div>
    <!--search_top end-->
    <!--logoin_top start-->
    <c:if test="${CURRENT_LOGIN_USER_MEMBER!=null}">
      <div class="Haslogged fr" id="loginStatus">
        <a class="type  fl posiR" href="${INDEX_PATH}/lvxbang/message/system.jhtml" id="myMessage">消息</a>
        <a href="${INDEX_PATH}/lvxbang/user/plan.jhtml" class="name fl" id="userMessage"
           value="${userName}">个人中心：${userName}</a>
        <a href="javascript:exitDelete();"  class="fr but exit">退出</a>
      </div>
    </c:if>
    <c:if test="${CURRENT_LOGIN_USER_MEMBER==null}">
      <ul class="nav_top_div fr" id="logoutStatus">
          <%--/lvxbang/register/register.jhtml
             javaScript:register_popup();
             /lvxbang/login/login.jhtml
             javaScript:login_popup();--%>
          <%--<li class="bl_bor"><a href="javaScript:register_popup();" target="_blank">注册</a></li>--%>
          <%--<li><a href="javaScript:login_popup();" target="_blank">登陆</a></li>--%>
        <li class="bl_bor"><a href="${INDEX_PATH}/lvxbang/register/registerr.jhtml" target="_blank">注册</a></li>
        <li><a href="${INDEX_PATH}/lvxbang/login/login.jhtml" target="_blank" onclick="return login_According_url();">登陆</a></li>
        <li><a href="javascript:void 0" target="_blank" class="qq"></a></li>
        <li><a href="javascript:void 0" target="_blank" class="wx"></a></li>
      </ul>
    </c:if>
    <!-- logoin_top end -->
    <!--favorite start-->
    <!--<a href="http://www.lvxbang.com/lvxbang/user/favorite.jhtml" target="_blank" onclick="return has_no_User2(&#39;http://www.lvxbang.com/lvxbang/user/favorite.jhtml&#39;);" class="fr collect disB posiR mt30"><i></i>收藏夹&nbsp;&gt;
    </a>-->
    <!--favorite end-->
  </div>
  <!--nav_logo end-->
</div>
<!--logoRow_top end-->

<!-- 二期tab start -->
<div style="height: 46px">
  <input type="hidden" id="cityId" value="${city.id}"/>
  <input type="hidden" id="cityName" value="${city.name}"/>
  <div id="topContainer" class="an_mo" liwithhan="category_横向导航" style="">
    <div class="topcontainer">
      <div class="toparea clearfix">
        <div class="box gohome">
          <a href="${INDEX_PATH}" target="_blank">首页</a>
        </div>
        <div class="box citybox categorys">
          <span class="cityname add_more_city_button" style="cursor: pointer;">${city.name}</span>
					<span class="moreplace add_more_city_button" style="cursor: pointer;">
						<span class="text">更改</span>
						<span class="down-arrow"></span>
					</span>
          <div style="left: -219px;top: 16px;position: absolute;">
          <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>
          </div>
        </div>
        <div class="box clearfix topnav common">
          <%--<a href="http://www.tuniu.com/g906/whole-gz-0/#cat_67" class="type product_recommend">--%>
            <%--产品推荐							<span class="poptip3"></span>--%>
          <%--</a>--%>
          <%--<span class="separator"></span>--%>
          <a href="${CUSTOM_PATH}/custom_made_${city.id}.html" class="type custom_made">
            定制精品							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${GENTUAN_PATH}/group_tour_${city.id}.html" class="type group_tour">
            跟团游							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${ZIZHU_PATH}/self_tour_${city.id}.html" class="type self_tour">
            自助游							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${ZIZHU_PATH}/self_driver_${city.id}.html" class="type self_drive">
            自驾							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${SCENIC_PATH}" target="_blank" class="type">
            门票					<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${TRAFFIC_PATH}?type=flight" target="_blank" class="type">
            机票							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${TRAFFIC_PATH}?type=train" target="_blank" class="type">
            火车票							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
          <a href="${HOTEL_PATH}" target="_blank" class="type">
            酒店							<span class="poptip3"></span>
          </a>
          <span class="separator"></span>
        </div>
        <div class="box topnav lesscommon list_top_menu">
          <ul>
            <li class="moreTopnav">
              <a class="type" href="javascript:;">攻略</a>
              <span class="list_top_menu_icon1"></span>
              <span class="list_top_menu_icon2"></span>
              <div class="list_top_menu_con">
                <a href="${DESTINATION_PATH}" target="_blank">目的地</a>
                <a href="${RECOMMENDPLAN_PATH}" target="_blank">游记</a>
                <a href="${DELICACY_PATH}" target="_blank">美食</a>
                <a href="${HANDDRAW_PATH}/map_${city.id}.html" target="_blank">旅游地图</a>
              </div>
            </li>
            <li>
              <span class="separator"></span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div><!-- 二期tab end -->

<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/login_register_popup.jsp"></jsp:include>--%>
<script type="text/html" id="tpl-suggest-item">
  <li data-text="{{name}}" class="suggest-item" data-id="{{id}}" data-type="{{type}}">
    <label title="{{name}}" class="fl">{{#key}}</label>
    <span class="fr"></span>
  </li>
</script>
<script type="text/html" id="tpl-no-user-item">
  <ul class="nav_top_div fr" >
    <li class="bl_bor"><a href="${INDEX_PATH}/lvxbang/register/registerr.jhtml" target="_blank">注册</a></li>
    <li><a href="${INDEX_PATH}/lvxbang/login/login.jhtml" target="_blank">登陆</a></li>
    <li><a href="#" target="_blank" class="qq"></a></li>
    <li><a href="#" target="_blank" class="wx"></a></li>
  </ul>
</script>


<!--head  end-->