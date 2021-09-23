<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

  <%@include file="../../yhypc/public/header.jsp"%>
  <link rel="stylesheet" href="/css/public/pager.css">
  <input id="dateId" name="dateId" type="hidden" value="${dateId}"/>

  <jx:include fileAttr="${YHY_CRUISESHIP_HEAD}" targetObject="yhyBuildService" targetMethod="buildOneCruiseship" objs="${param.dateId}" validDay="1"></jx:include>
  <title>游轮详情</title>
  <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
  <link rel="stylesheet" href="/css/cruiseship/new-public.css">
  <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
  <link rel="stylesheet" href="/css/cruiseship/product_details.css">
</head>
<body class="cruise">
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp"%>
  <input id="cruiseShipId" type="hidden" value="${cruiseShip.id}"/>
<jx:include fileAttr="${YHY_CRUISESHIP_DETAIL}" targetObject="yhyBuildService" targetMethod="buildOneCruiseship" objs="${param.dateId}" validDay="1"></jx:include>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
<!--邮轮介绍弹窗外壳-->
<div id="popupWrap">

</div>
<!--常见问题 弹窗效果-->
<!--主体部分  ending-->
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

<script type="text/html" id="room">
        <div class="swiper-slide" style="width: 273.5px; height: 251px;">
          <div class="content-box">
            <img src="${QINIU_BUCKET_URL}{{coverImage}}">
            <h6>{{roomTypeStr}}</h6>
            <p>{{facilities}}</p>
          </div>
          <div class="popup-wrap" style="display: none">
            <div class="popup-box">
              <div class="popup">
                <span class="popup-close"><i></i></span>
                <h6>{{name}}</h6>
                <div class="popup-swiper clearfix">
                  <div class="views">
                    <div class="swiper-wrapper">
                      {{each productimages as roomImage}}
                      <div class="swiper-slide">
                        <img src="${QINIU_BUCKET_URL}{{roomImage.path}}">
                      </div>
                      {{/each}}
                    </div>
                  </div>
                  <div class="previews-wrap">
                    <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
                    <div class="preview">
                      <div class="swiper-wrapper">
                        {{each productimages as roomImage}}
                        <div class="swiper-slide">
                          <img src="${QINIU_BUCKET_URL}{{roomImage.path}}">
                        </div>
                        {{/each}}
                      </div>
                    </div>
                    <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
                  </div>
                </div>
                <div class="txt">{{facilities}}</div>
              </div>
            </div>
          </div>
        </div>
</script>
<script type="text/html" id="food">
  <div class="swiper-slide"  style="width: 273.5px; height: 251px;">
    <div class="content-box">
      <img src="${QINIU_BUCKET_URL}{{coverImage}}">
      <h6>{{cruiseShipProjectClassify.classifyName}}</h6>
      <p>{{introduction}}</p>
    </div>
    <div class="popup-wrap" style="display: none">
      <div class="popup-box">
        <div class="popup">
          <span class="popup-close"><i></i></span>
          <h6>{{name}}</h6>
          <div class="popup-swiper clearfix">
            <div class="views">
              <div class="swiper-wrapper">
                {{each projectImages as foodImage}}
                <div class="swiper-slide">
                  <img src="${QINIU_BUCKET_URL}{{foodImage.path}}">
                </div>
                {{/each}}
              </div>
            </div>
            <div class="previews-wrap">
              <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
              <div class="preview">
                <div class="swiper-wrapper">
                  {{each projectImages as foodImage}}
                  <div class="swiper-slide">
                    <img src="${QINIU_BUCKET_URL}{{foodImage.path}}">
                  </div>
                  {{/each}}
                </div>
              </div>
              <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
            </div>
          </div>
          <div class="txt">{{introduction}}</div>
        </div>
      </div>
    </div>
  </div>
</script>
<script type="text/html" id="entainment">
  <div class="swiper-slide" style="width: 273.5px; height: 251px;">
    <div class="content-box">
      <img src="${QINIU_BUCKET_URL}{{coverImage}}">
      <h6>{{cruiseShipProjectClassify.classifyName}}</h6>
      <p>{{introduction}}</p>
    </div>
    <div class="popup-wrap" style="display: none">
      <div class="popup-box">
        <div class="popup">
          <span class="popup-close"><i></i></span>
          <h6>{{name}}</h6>
          <div class="popup-swiper clearfix">
            <div class="views">
              <div class="swiper-wrapper">
                {{each projectImages as entainmentImage}}
                <div class="swiper-slide">
                  <img src="${QINIU_BUCKET_URL}{{entainmentImage.path}}">
                </div>
                {{/each}}
              </div>
            </div>
            <div class="previews-wrap">
              <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
              <div class="preview">
                <div class="swiper-wrapper">
                  <div class="swiper-slide">
                    {{each projectImages as entainmentImage}}
                    <img src="${QINIU_BUCKET_URL}{{entainmentImage.path}}">
                  </div>
                  {{/each}}
                </div>
              </div>
              <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
            </div>
          </div>
          <div class="txt">{{introduction}}</div>
        </div>
      </div>
    </div>
  </div>
</script>
<script type="text/html" id="service">
  <div class="swiper-slide" style="width: 273.5px; height: 251px;">
    <div class="content-box">
      <img src="${QINIU_BUCKET_URL}{{coverImage}}">
      <h6>{{cruiseShipProjectClassify.classifyName}}</h6>
      <p>{{introduction}}</p>
    </div>
    <div class="popup-wrap" style="display: none">
      <div class="popup-box">
        <div class="popup">
          <span class="popup-close"><i></i></span>
          <h6>{{name}}</h6>
          <div class="popup-swiper clearfix">
            <div class="views">
              <div class="swiper-wrapper">
                {{each projectImages as serviceImage}}
                <div class="swiper-slide">
                  <img src="${QINIU_BUCKET_URL}{{serviceImage.path}}">
                </div>
                {{/each}}
              </div>
            </div>
            <div class="previews-wrap">
              <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
              <div class="preview">
                <div class="swiper-wrapper">
                  {{each projectImages as serviceImage}}
                  <div class="swiper-slide">
                    <img src="${QINIU_BUCKET_URL}{{serviceImage.path}}">
                  </div>
                  {{/each}}
                </div>
              </div>
              <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
            </div>
          </div>
          <div class="txt">{{introduction}}</div>
        </div>
      </div>
    </div>
  </div>
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>

<script type="text/javascript" src="/lib/util/pager.js"></script>
<script src="/js/cruiseship/jquery.min.js"></script>
<script src="/js/cruiseship/idangerous.swiper2.7.6.min.js"></script>
<script src="/js/cruiseship/product_details.js"></script>
</html>
