<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<!DOCTYPE html>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>私人定制_定制旅游_自由行-旅行帮 </title>
    <meta name="keywords" content="私人定制，自助游，自由行，旅游线路，定制旅游，行程安排"/>
    <meta name="description"
          content="旅行帮是中国领先的C2B旅游电商平台。提供全网最智能、最方便的定制旅游工具，一键定制自由行行程方案。交通，住宿，景点，美食，游记，旅游地图应有尽有，无需查攻略、跨平台比价购买，旅行帮一键帮您搞定。"/>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement_v2.css" rel="stylesheet" type="text/css">
    <link href="/css/homepage_banner.css" rel="stylesheet" type="text/css">
    <%--<link href="/css/hongbao.css" rel="stylesheet" type="text/css">--%>
    <link href="/css/datePicker.css" rel="stylesheet" type="text/css">
</head>
<body myname="home">
<%--提示下来状态判断--%>
<input type="hidden" id="keyStatus" value="13"/>
<%--目的地搜索框跳到景点使用--%>
<form id="destinationScenicSearch" method="post" action="${SCENIC_PATH}/scenic_list.html">
    <input id="destinationScenicName" name="scenicName" type="hidden" placeholder="输入景点" value="" class="input" autocomplete="off">
</form>

<div class="nextpage ff_yh fs36 textC">
    <a href="javaScript:;" class="close"></a>
    <img src="/images/g_ico.jpg" alt="如何定制旅游线路"/>
</div><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!-- #EndLibraryItem -->
<!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--banner end-->
<jx:include fileAttr="${LVXBANG_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildIndex"></jx:include>
<div class="lxb_main">
    <!--内链：大家在看-->
    <div class="links-wrp">
        <div class="links-box">
            <div class="links-list">
                <dl class="links-item clearfix">
                    <dt>大家都在看</dt>
                    <dd><a href="javascript:;" target="_blank">美国定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">法国定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">北欧定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">新西兰定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">海岛度假别墅</a></dd>
                    <dd><a href="javascript:;" target="_blank">南极旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">瑞士定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">意大利定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">马尔代夫定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">泰国定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">加拿大定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">澳洲定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">印度定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">马来西亚定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">缅甸定制旅游</a></dd>
                    <dd><a href="javascript:;" target="_blank">斯里兰卡定制旅游</a></dd>
                </dl>
            </div>
        </div>
    </div>
    <!--外链：合作单位-->
    <div class="main_bottom">
        <div class="main_inner">
            <div class="tourism_box">
                <div class="tourism_tit clearfix">
                    <h3>合作单位</h3>
                    <div class="more_lyj lxb_none"><a>更多合作单位 &gt;</a></div>
                </div>
                <div class="tourism_wrap">
                    <div id="tourism_wrap_inner" class="tourism_wrap_inner">
                        <ul class="tourism_list clearfix datalazyload" id="tourismLists" data-lazyload-type="data" data-lazyload-from="textarea" >
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img3.tuniucdn.com/u/v1/common/footer/img/lvyouju/HongKong.jpg" alt="香港旅游发展局" style="">
                                </a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">香港旅游发展局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/img/20141215/footer/xi_adly.png" alt="西澳大利亚旅游局" style="">
                                </a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">西澳大利亚旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/img/20150108/footer/dibailogo.jpg" alt="迪拜政府商业及旅游局推广局" style="">
                                </a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">迪拜政府商业及旅游局推广局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/img/20150108/footer/fujian_logo.jpg" alt="福建旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">福建旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/u/v1/common/footer/img/lvyouju/Portugal.jpg" alt="葡萄牙旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">葡萄牙旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/img/20150723/footer/Disney.jpg" alt="香港迪士尼乐园" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">香港迪士尼乐园</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img2.tuniucdn.com/site/file/zt/public/images/hainan.jpg" alt="海南省旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">海南省旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img.tuniucdn.com/u/mainpic/lvyouju/Singapore.jpg" alt="新加坡旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">新加坡旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img.tuniucdn.com/u/mainpic/lvyouju/adlybld.jpg" alt="澳大利亚北领地" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">澳大利亚北领地</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img.tuniucdn.com/u/mainpic/lvyouju/NewZealand.jpg" alt="新西兰旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">新西兰旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img.tuniucdn.com/u/mainpic/lvyouju/Norway.jpg" alt="挪威旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">挪威旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://m.tuniucdn.com/fb2/t1/G1/M00/EB/51/Cii9EVdNIOOIQtqFAAAVf9Mqc-0AAGQSwP_6AYAABWX72.jpeg" alt="加拿大旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">加拿大旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img2.tuniucdn.com/u/v1/common/footer/img/lvyouju/Hawaiian.JPG" alt="夏威夷旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">夏威夷旅游局</a></p>
                            </li>
                            <li>
                                <div><a rel="nofollow" href="javascript:;" target="_blank">
                                    <img width="100" height="40" src="http://img4.tuniucdn.com/img/20150108/footer/jp_jiuzhou.jpg" alt="日本国家旅游局" style=""></a></div>
                                <p><a class="inner_a" target="_blank" href="javascript:;" rel="nofollow">日本国家旅游局</a></p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>

<!--foot begin-->
<!-- <script src="/js/lib/jquery.js" type="text/javascript"></script> -->
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/index/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/hash.js" type="text/javascript"></script>
<%--<script src="/js/lvxbang/index/hongbao.js" type="text/javascript"></script>--%>
<script type="text/html" id="tpl-interest-item-scenic">
<li class="posiR">
    <a href="${SCENIC_PATH}/scenic_detail_{{resObjectId}}.html" target="_blank"><i class="posiA"></i>
    <p class="img"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}?imageView2/1/w/155/h/155/q/75" alt="{{title}}" /></p>
    <div class="nr mt5">
        <label class="name">{{title}}</label>
        <span class="jb">{{introduction}}</span>
        {{if price != null && price > 0}}
        <p class="price b ff_yh">￥<b class="fs16">{{price}}</b><span style="color: #999">起</span></p>
        {{/if}}
    </div></a>
</li>
</script>
<script type="text/html" id="tpl-history-item-scenic">
<li class="posiR">
    <a href="${SCENIC_PATH}/scenic_detail_{{resObjectId}}.html" target="_blank"><i class="posiA"></i>
    <p class="img"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}?imageView2/1/w/155/h/155/q/75" alt="{{title}}" class="his"/></p>
    <div class="nr mt5">
        <label class="name">{{title}}</label>
        <span class="jb">{{introduction}}</span>
        {{if price != null && price > 0}}
        <p class="price b ff_yh">￥<b class="fs16">{{price}}</b><span style="color: #999">起</span></p>
        {{/if}}
    </div></a>
</li>
</script>
</body>
</html>
