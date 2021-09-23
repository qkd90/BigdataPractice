<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/newcss/index_nav_menu.css" rel="stylesheet" type="text/css">
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
<input type="hidden" id="plan_path" value="${PLAN_PATH}">
<input type="hidden" id="traffic_path" value="${TRAFFIC_PATH}">
<input type="hidden" id="index_path" value="${INDEX_PATH}">
<input type="hidden" id="handdraw_path" value="${HANDDRAW_PATH}">
<input type="hidden" id="recommendplan_path" value="${RECOMMENDPLAN_PATH}">
<input type="hidden" id="scenic_path" value="${SCENIC_PATH}">
<input type="hidden" id="destination_path" value="${DESTINATION_PATH}">
<input type="hidden" id="hotel_path" value="${HOTEL_PATH}">
<input type="hidden" id="delicacy_path" value="${DELICACY_PATH}">
<input type="hidden" id="custom_path" value="${CUSTOM_PATH}">
<input type="hidden" id="gentuan_path" value="${GENTUAN_PATH}">
<input type="hidden" id="zizhu_path" value="${ZIZHU_PATH}">
<input type="hidden" id="require_path" value="${REQUIRE_PATH}">
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

<!--navigation-->
<div class="header_nav">
    <div class="index_nav_menu">
        <div class="menu_panel">
            <div class="inner clearfix">
                <ul class="menu_list clearfix">
                    <!--用类cui_nav_o开启二级导航栏-->
                    <li  class="menu_first " name="nav-tab" data-tab="home">
                        <a class="cur_nav" href="${INDEX_PATH}" title="首页">首&nbsp;页</a>
                    </li>
                    <li  class="nav-tab" name="nav-tab" data-tab="TripPlanning">
                        <a href="${PLAN_PATH}">私人定制</a>
                    </li>
                    <li  class="nav-tab" name="nav-tab" data-tab="">
                        <a href="${CUSTOM_PATH}">定制精品</a>
                    </li>

                    <li class="hasSubNav " name="nav-tab" data-tab="customRequire">
                        <a >定制需求<i class="cui_ico_triangle"></i>
                        </a>
                        <div class="top_subnav_wrap" style="left:-290px">
                            <div class="top_sub_nav" style="left:140px">
                                <a href="${REQUIRE_PATH}?customType=company">公司定制</a>
                                <a href="${REQUIRE_PATH}?customType=personal">个人定制</a>
                                <a href="${REQUIRE_PATH}?customType=home">家庭定制</a>
                            </div>
                            <i class="header_icon icon_arrowUp" style="left: 332px;"></i>
                        </div>
                    </li>
                    <li  class="nav-tab" name="nav-tab" data-tab="">
                        <a href="${ZIZHU_PATH}">自助自驾<%--<i class="cui_ico_triangle"></i>--%></a>
                        <%--<div class="top_subnav_wrap" style="left:-388px">--%>
                            <%--<div class="top_sub_nav" style="left:150px">--%>
                                <%--<a class="highLight" >国内自助</a>--%>
                                <%--<a >周边自驾</a>--%>
                                <%--<a >出境自助</a>--%>
                                <%--<a >门票+酒店</a>--%>
                                <%--<a >机票+酒店</a>--%>
                            <%--</div>--%>
                            <%--<i class="header_icon icon_arrowUp" style="left: 432px;"></i>--%>
                        <%--</div>--%>
                    </li>
                    <li  class="nav-tab" name="nav-tab" data-tab="">
                        <a href="${GENTUAN_PATH}">跟团游<%--<i class="cui_ico_triangle"></i>--%></a>
                        <%--<div class="top_subnav_wrap" style="left:-488px">--%>
                            <%--<div class="top_sub_nav" style="left:390px">--%>
                                <%--<a class="highLight">周边跟团</a>--%>
                                <%--<a >国内跟团</a>--%>
                                <%--<a >出境跟团</a>--%>
                            <%--</div>--%>
                            <%--<i class="header_icon icon_arrowUp" style="left: 522px;"></i>--%>
                        <%--</div>--%>
                    </li>
                    <li   class="hasSubNav "  name="nav-tab" data-tab="mall">
                        <a >商城<i class="cui_ico_triangle"></i></a>
                        <div class="top_subnav_wrap" style="left:-572px;">
                            <div class="top_sub_nav " style="left:400px ">
                                <!--<a class="highLight">邮轮</a>-->
                                <a href="${TRAFFIC_PATH}?type=flight" class="" >机票</a>
                                <a href="${TRAFFIC_PATH}?type=train" class="" >火车票</a>
                                <a href="${HOTEL_PATH}" class="" >酒店</a>
                                <a href="${SCENIC_PATH}" class="" >门票</a>
                                <!--<a class="highLight">特卖</a>-->
                                <!--<a class=" ">旅游卡</a>-->
                            </div>
                            <i class="header_icon icon_arrowUp" style="left: 600px;"></i>
                        </div>
                    </li>
                    <li  class="hasSubNav " name="nav-tab" data-tab="strategy">
                        <a >攻略<i class="cui_ico_triangle"></i></a>
                        <div class="top_subnav_wrap" style="left:-637px">
                            <div class="top_sub_nav" style="left:537px">
                                <a href="${DESTINATION_PATH}" class="highLight" >目的地</a>
                                <a href="${RECOMMENDPLAN_PATH}" >游记</a>
                                <a href="${DELICACY_PATH}" >美食</a>
                                <a target="_blank" href="${HANDDRAW_PATH}" >旅游地图</a>
                            </div>
                            <i class="header_icon icon_arrowUp" style="left: 662px;"></i>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="subnav_wrap_bg" id="subnav_wrap_bg" style="display: none;border-bottom: 1px solid #999;"></div>
</div>
<!--navigation end-->

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