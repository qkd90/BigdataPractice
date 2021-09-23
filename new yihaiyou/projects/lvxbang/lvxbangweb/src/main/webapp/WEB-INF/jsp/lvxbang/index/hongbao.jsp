<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2016-05-18,0018
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>百万红包任性送</title>
  <link href="/css/hongbao.css" rel="stylesheet" type="text/css">
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
</head>
<body myname="home">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="hbbanner"></div>
<div class="hb-info">
  <div class="w1000">
    <h4>一、活动规则</h4>
    <p>1.即日起，新用户关注旅行帮微信公众号（lvxbang2014）即可获得500元红包，红包数量有限，先到先得。</p>
    <p>2.本次活动仅限新用户且仅限参与活动一次。</p>
    <p>3.红包详细使用规则可至旅行帮个人中心"我的红包"中查看。</p>
    <p>4.红包有效期为领取之日起2个月内有效。</p>
    <p>5.对于以不正当方式参与活动的用户，包括但不限于恶意套现，恶意下单，恶意注册，利用程序漏洞等，旅行帮有权在不事先通知的情况下取
      消其参与活动的资格并收回相应奖品。</p>
    <p>6.活动最终解释权归厦门浩茫连宇信息技术有限公司所有。</p>
    <br />
    <h4>二、旅行帮是WHO？</h4>
    </p>
    旅行帮是中国领先的C2B旅游电商平台。提供全网最智能、最方便的定制旅游工具，一键定制自由行线路。无需查攻略、跨平台比价，旅行帮
    一键帮您搞定！
    </p>
    <br />
    <h4>三、旅行帮特色</h4>
    <p>1.私人定制：可原创一条属于自己的线路；也可复制参考线路调整成自己的特色线路。</p>
    <p>2.旅游地图：卡通版旅游地图，景点一目了然，可直接点击心仪景点添加至线路，轻松帮旅行！</p>
    <p>3.一键支付：通过智能搜索比价，推荐性价比最优的酒店、交通、景点等资源，并可一键打包支付。</p>
    <br />
  </div>
</div>
<div class="jdtj">
  <div class="w1000">
    <a href="${SCENIC_PATH}/scenic_list.html?cityCode=350200" class="more" target="_blank"></a>
    <a href="${SCENIC_PATH}/scenic_detail_1059640.html" class="jd1" target="_blank">
      <img class="thumb" src="/images/huodong/jd1.jpg" />
      <div class="text">
        <h2>鼓浪屿</h2>
        <p class="price">¥90<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
    </a>
    <a href="${SCENIC_PATH}/scenic_detail_1042941.html" class="jd2" target="_blank">
      <div class="text">
        <h2>鼓浪屿贝壳梦工厂</h2>
        <p class="price">¥48<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
      <img class="thumb" src="/images/huodong/jd2.jpg" />
    </a>
    <a href="${SCENIC_PATH}/scenic_detail_1059691.html" class="jd3" target="_blank">
      <img class="thumb" src="/images/huodong/jd3.jpg" />
      <div class="text">
        <h2>观音山梦幻海岸</h2>
        <p class="price">¥80<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
    </a>
    <a href="${SCENIC_PATH}/scenic_detail_1059682.html" class="jd4" target="_blank">
      <img class="thumb" src="/images/huodong/jd4.jpg" />
      <div class="text">
        <h2>方特梦幻王国</h2>
        <p class="price">¥150<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
    </a>
    <a href="${SCENIC_PATH}/scenic_detail_1059646.html" class="jd5" target="_blank">
      <img class="thumb" src="/images/huodong/jd5.jpg" />
      <div class="text">
        <h2>胡里山炮台</h2>
        <p class="price">¥23<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
    </a>
    <a href="${SCENIC_PATH}/scenic_detail_1059756.html" class="jd6" target="_blank">
      <div class="text">
        <h2>天沐温泉度假村</h2>
        <p class="price">¥98<small>起</small></p>
        <p class="enter"><img src="/images/huodong/enter.png" /></p>
      </div>
      <img class="thumb" src="/images/huodong/jd6.jpg" />
    </a>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
</body>
</html>