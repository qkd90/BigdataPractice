<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2017/1/23
  Time: 11:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" name="personalCenterFlag" value="true">
<div class="advert"><img src="/image/personal_banner.png"></div>
<div class="personal_nav">
  <div class="nav_center">
    <div class="personal_face" id="user_info">
      <c:choose>
          <c:when test="${LOGIN_USER != NULL}">
              <span class="face">
              <c:choose>
                  <c:when test="${LOGIN_USER.head != null && LOGIN_USER.head != ''}">
                      <c:choose>
                          <c:when test="${fn:startsWith(LOGIN_USER.head, 'http://')}"><img src="${LOGIN_USER.head}"></c:when>
                          <c:otherwise><img src="${QINIU_BUCKET_URL}${LOGIN_USER.head}?imageView2/2/w/104/h/104"></c:otherwise>
                      </c:choose>
                  </c:when>
                  <c:otherwise><img src="/image/user_default104.png"></c:otherwise>
              </c:choose>
              </span>
              <span class="name">${LOGIN_USER.nickName}</span>
          </c:when>
          <c:otherwise>
              <span class="face"><img class="req-login" src="/image/user_login104.png"></span>
              <span class="name req-login">请登录</span>
          </c:otherwise>
      </c:choose>
    </div>
    <ul class="clearfix" id="user_nav_ul">
      <li class="pc-myorder per_active"><a href="/yhypc/personal/index.jhtml">我的订单</a></li>
      <li class="pc-myplan"><a href="/yhypc/personal/myPlan.jhtml">我的行程</a></li>
      <li class="pc-mycollection"><a href="/yhypc/personal/myCollection.jhtml">我的收藏</a></li>
      <li class="pc-mywallet"><a href="/yhypc/personal/myWallet.jhtml">我的钱包</a></li>
      <li class="pc-myinfo"><a href="/yhypc/personal/myInfo.jhtml">个人信息</a></li>
      <li class="pc-mycontact"><a href="/yhypc/personal/myContact.jhtml">常用联系人</a></li>
    </ul>
  </div>
</div>
<script type="text/html" id="login_loading_tpl">
    <span class="face"><img src="/image/user_login_loading104.png"></span>
    <span class="name">登录中, 请稍候...</span>
</script>
<script type="text/html" id="logged_tpl">
    <span class="face"><img src="{{head | imageResize:104,104}}"></span>
    <span class="name">{{nickName}}</span>
</script>
<script type="text/html" id="logout_tpl">
    <span class="face"><img class="req-login" src="/image/user_login104.png"></span>
    <span class="name req-login">请登录</span>
</script>