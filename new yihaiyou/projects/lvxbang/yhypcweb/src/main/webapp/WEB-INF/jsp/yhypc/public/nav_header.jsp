<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/12/27
  Time: 15:26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header">
    <div class="headBar clearfix">
        <div class="headBarLeft">
            <ul id="login_info_ul">
                <li class="slogan">一海游，玩转厦门！</li>
                <c:choose>
                    <c:when test="${LOGIN_USER != null}">
                        <li class="login login_scs">欢迎您: ${LOGIN_USER.nickName}<span class="split">|</span><span class="logout">退出</span><input type="hidden" name="uid" value="${LOGIN_USER.id}"></li>
                    </c:when>
                    <c:otherwise>
                        <li class="login"><span class="loginbt">吴锦锋</span><span class="register">退出登录</span><span class="split">|<span class="register">免费注册</span><span class="split">|</span><a target="_blank" href="/yhypc/company/doRegister.jhtml">商户注册</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div class="headBarRight">
            <ul>
                <li class="listdown" id="myyihaiyou">我的一海游
                    <ul class="myyihaiyou clearfix">
                        <li><a target="_blank" href="/yhypc/personal/index.jhtml">我的订单</a></li>
                        <li><a target="_blank" href="/yhypc/personal/myWallet.jhtml">我的钱包</a></li>
                        <li><a target="_blank" href="/yhypc/personal/myInfo.jhtml">个人信息</a></li>
                        <li><a target="_blank" href="/yhypc/personal/myContact.jhtml">常用联系人</a></li>
                    </ul>
                </li>
                <li>
                    <span class="mys_l"><a target="_blank" href="/yhypc/personal/myCollection.jhtml">我的收藏</a></span>
                    <span class="mys_l"><a target="_blank" href="/yhypc/personal/myPlan.jhtml">我的行程</a></span>
                </li>
                <li class="listdown" id="customService">客服服务
                    <ul class="customService clearfix" style="width:97px;left:-1px">
                        <li><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=86198148&site=qq&menu=yes">联系客服</a></li>
                    </ul>
                </li>
                <%--<li class="headlastli">网站导航</li>--%>
            </ul>
        </div>
    </div>
    <div class="selectBar">
        <div class="logoIndex"><a href="/yhypc/index/index.jhtml"></a></div>
        <div class="nav">
            <ul class="clearfix">
                <li><a href="/yhypc/index/index.jhtml">首页</a></li>
                <li><a href="/yhypc/plan/demand.jhtml">DIY行程</a></li>
                <li><a href="/yhypc/hotel/index.jhtml">酒店民宿</a></li>
                <li><a href="/yhypc/ferry/list.jhtml">船票预订</a></li>
                <li><a href="/yhypc/sailboat/index.jhtml">海上休闲</a></li>
                <li><a href="/yhypc/scenic/index.jhtml">景点门票</a></li>
                <li><a href="/yhypc/cruiseShip/index.jhtml">邮轮旅游</a></li>
                <%--<li><a href="#">美食礼品</a></li>--%>
                <%--<li><a href="#">导游预约</a></li>--%>
                <li><a href="/yhypc/recommendPlan/index.jhtml">游记攻略</a></li>
            </ul>
        </div>
    </div>
</div>
<script type="text/html" id="login_info_item">
    <li class="login login_scs">欢迎您: {{nickName}}<span class="split">|</span><span class="logout">退出</span><input type="hidden" name="uid" value="{{id}}"></li>
</script>
<script type="text/html" id="login_out_item">
    <li class="login"><span class="loginbt">请登录</span><span class="register">免费注册</span><span class="split">|</span><a target="_blank" href="/yhypc/company/doRegister.jhtml">商户注册</a></li>
</script>