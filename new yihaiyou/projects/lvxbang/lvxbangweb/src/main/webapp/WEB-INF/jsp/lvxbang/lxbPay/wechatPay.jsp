<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/14
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<!DOCTYPE html >--%>
<%--<html>--%>
<%--<head>--%>
<%--<title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div>--%>
<%--<input id="code" type="hidden" value="${payForm}">--%>
<%--<div id="qrcode"></div>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>旅行帮微信支付</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/newcss/all_link.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
    <script type="text/JavaScript" src="html5shiv.js"></script>
    <script type="text/javascript" src="canvas.text.js"></script>
    <script type="text/javascript" src="excanvas.js"></script>
    <![endif]-->
</head>
<body>
<input type="hidden" value="0" id="page_reload"/>
<!--head-->
<div class="head">
    <!--nav_top-->
    <div class="nav_top">
        <div class="w1000 posiR">
            <p id="head_p" class="nav_top_p fl posiR"><s class="home posiA disB"></s><a
                    href="${INDEX_PATH}">旅行帮首页</a></p>
            <c:if test="${CURRENT_LOGIN_USER_MEMBER!=null}">
                <div class="Haslogged fr" id="loginStatus">
                    <a class="type  fl posiR" href="${INDEX_PATH}/lvxbang/message/system.jhtml" id="myMessage">我的消息</a>
                    <a href="${INDEX_PATH}/lvxbang/user/plan.jhtml" class="name fl" id="userMessage"
                       value="${userName}">${userName}</a>
                    <a href="${INDEX_PATH}/lvxbang/login/exitLogin.jhtml" class="fr but">退出</a>
                </div>
            </c:if>
            <%--<ul class="nav_top_div fr">--%>
            <%--<li class="bl_bor"><a href="FY_password.html" target="_blank">注册</a></li>--%>
            <%--<li><a href="login.html" target="_blank">登陆</a></li>--%>
            <%--<li><a href="javaScript:;" target="_blank" class="wx"></a></li>--%>
            <%--<li><a href="javaScript:;" target="_blank" class="qq"></a></li>--%>
            <%--</ul>--%>
            <c:if test="${CURRENT_LOGIN_USER_MEMBER==null}">
                <ul class="nav_top_div fr" id="logoutStatus">
                        <%--/lvxbang/register/register.jhtml
                           javaScript:register_popup();
                           /lvxbang/login/login.jhtml
                           javaScript:login_popup();--%>
                        <%--<li class="bl_bor"><a href="javaScript:register_popup();" target="_blank">注册</a></li>--%>
                        <%--<li><a href="javaScript:login_popup();" target="_blank">登陆</a></li>--%>
                    <li class="bl_bor"><a href="${INDEX_PATH}/lvxbang/register/registerr.jhtml" target="_blank">注册</a></li>
                    <li><a href="${INDEX_PATH}/lvxbang/login/login.jhtml" target="_blank">登陆</a></li>
                    <li><a href="javascript:void 0" target="_blank" class="qq"></a></li>
                    <li><a href="javascript:void 0" target="_blank" class="wx"></a></li>
                </ul>
            </c:if>
        </div>
    </div>
    <!--nav_top end-->
    <p class="cl"></p>
</div>
<!--head  end-->

<div class="pay_wx_t">
    <div class="w1000">
				<span class="fl disB">
					<img src="/images/Pay_wx1.png"/>
				</span>
        <div class="fl">
            <h1 class="ff_yh">微信支付</h1>
            扫一扫安全支付
        </div>
    </div>
</div>

<div class="main cl">
    <div class="w1000">
        <div class="pay_wx_c cl">
            <div class="pay_wx_c_l fl">
                <p class="name">${order.name}</p>
                <p class="orderform">支付订单号：
                    <a style="" href="${detailUrl}">${order.orderNo}
                    </a>

                </p>
                <div class="introduction">
                    <p class="fl">请及时完成付款，否则订单将会自动取消</p>
                    <%--<p class="Orange fl">支付订单剩余时间：<span>18分16秒</span></p>--%>
                </div>
            </div>

            <div class="pay_wx_c_r fr">
                支付<b class="ff_yh Orange">${order.price}</b>元
            </div>
            <p class="cl"></p>
        </div>

        <input id="code" type="hidden" value="${payForm}">
        <div class="pay_wx_b cl">
            <div class="pay_wx_b_l fl">
                <p class="ff_yh name">微信支付</p>
                <%--<p class="img">--%>
                <%--<img src="/images/Pay_wx1.jpg" />--%>
                <%--</p>--%>
                <p class="img" id="qrcode">
                </p>
                <div class="introduction">
                    <p class="fl"><img src="/images/Pay_wx2.png"/></p>
                    <div class="fl">请使用微信扫一扫<br/>扫描二维码支付</div>
                </div>
            </div>
            <div class="pay_wx_b_r fr"><p class="img"><img src="/images/Pay_wx2.jpg"/></p></div>
            <p class="cl"></p>
        </div>
    </div>

</div><!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem -->
   <input type="hidden" id="orderId" value="${order.id}"/>
</body>
</html>
<%--多引入这个js会导致图片懒加载报错，不知道会不会影响暂时先去掉--%>
<%--<script src="/js/lib/jquery.js" type="text/javascript"></script>--%>
<script>
    $(document).ready(function () {
        //头部小图标
        $(".nav_top_p a").hover(function () {
            $(this).prev("s").addClass("checked");
        }, function () {
            $(this).prev("s").removeClass("checked");
        });
    });
</script>
<script src="/js/lib/excanvas.js" type="text/javascript"></script>
<script src="/js/lib/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="/js/lvxbang/pay/pay.js" type="text/javascript"></script>
<script src="/js/lvxbang/pay/wechatPay.js" type="text/javascript"></script>

