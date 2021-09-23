<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="lxbApp">
<head>
    <meta charset="UTF-8">
    <title>旅行帮</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%--<meta name="viewport" content="width=640, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi" />--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <%--<meta name="viewport" content="width=device-width, initial-scale=0.5, maximum-scale=0.5, user-scalable=no, target-densitydpi=device-dpi" />--%>
    <%--<meta name="viewport" content="width=640, user-scalable=no" />--%>
    <%--<meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">--%>
    <meta name="description" content="厦门旅游, 网上订购, 景点推荐, 路线规划">
    <meta name="keywords" content="旅游, 厦门, 网上订票">
    <link rel="stylesheet" href="libs/bootstrap/css/bootstrap.min.css">
    <%--<link rel="stylesheet" href="libs/jquery/jquery-ui.css">--%>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/icheck/skins/flat/green.css"><link rel="stylesheet" href="css/icheck/skins/square/green.css">
    <link rel="stylesheet" href="css/dropload.css">
    <link rel="stylesheet" href="css/icheck/skins/square/blue.css">
    <link href="css/owl.carousel.css" rel="stylesheet">
    <link href="css/owl.theme.css" rel="stylesheet">
</head>
<body>
<ajaxloading></ajaxloading>
<div>
    <div ui-view></div>
    <%--<div>fffff</div>--%>
</div>
</body>
<!-- loading js -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="libs/jquery/jquery.min.js"></script>
<script src="libs/bootstrap/js/bootstrap.min.js"></script>
<script src="libs/bootstrap/js/bootbox.min.js"></script>
<script src="libs/jquery/jquery.spinner.js"></script>
<script src="libs/jquery/icheck.min.js"></script>
<%--<script src="js/my.js"></script>--%>
<script src="libs/angular/angular.min.js"></script>
<script src="libs/angular/angular-animate.js"></script>
<script src="libs/angular/angular-ui-router.js"></script>
<script src="libs/angular/angular-cookies.min.js"></script>
<script src="libs/angular/angularLocalStorage.min.js"></script>
<script src="libs/angular/ng-infinite-scroll.min.js"></script>
<script type="text/javascript" src="config.js"></script>
<script type="text/javascript" src="tpls/demo.template.html.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript" src="js/directive.js"></script>
<script type="text/javascript" src="js/controllers.js"></script>
<script type="text/javascript" src="js/mycontrollers.js"></script>
<script type="text/javascript" src="js/services.js"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/getscript?v=2.0&ak=GKjVLKFYCjItmN8Ubcs22zea&services=&t=20151113040005"
        class="ng-scope"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js"></script>

<script src="libs/data/js/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
<script src="libs/data/js/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
<script src="libs/jquery/jquery.spinner.js"></script>
<link href="libs/data/css/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css"/>
<link href="libs/data/css/mobiscroll.animation-2.5.2.css" rel="stylesheet" type="text/css"/>
<script src="libs/data/js/mobiscroll.datetime-2.5.1.js" type="text/javascript"></script>
<script src="libs/data/js/mobiscroll.datetime-2.5.1-zh.js" type="text/javascript"></script>
<!-- S 可根据自己喜好引入样式风格文件 -->
<script src="libs/data/js/mobiscroll.android-ics-2.5.2.js" type="text/javascript"></script>
<link href="libs/data/css/mobiscroll.android-ics-2.5.2.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="libs/jquery/Sortable.min.js"></script>
<script src="js/owl.carousel.min.js"></script>

<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?e0b07e2b5234f26c938df7f89692a960";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
</html>