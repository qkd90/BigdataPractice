<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/12/9
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>加入旅行帮人人分销</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link href="${mallConfig.resourcePath}/css/mobile/user/share.css?${mallConfig.resourceVersion}" rel="stylesheet">
	<script type="text/javascript">
	var WEIXIN_DOMAIN = '<s:property value="weixinDomain"/>';
	</script> 
    <script src="${mallConfig.resourcePath}/js/jquery.min.js?${mallConfig.resourceVersion}"></script>
    <script src="${mallConfig.resourcePath}/js/mobile/share/share_util.js?${mallConfig.resourceVersion}"></script>
    <script src="${mallConfig.resourcePath}/js/mobile/share/share.js?${mallConfig.resourceVersion}"></script>
</head>
<body>

<div>
    <%--用户信息--%>
    <input id="parentId" type="hidden" value="${parentId}">

    <div id="notSubscribe" class="display-none">
        <div>
            <span>您还没有关注我们的公众号无法参加活动，请长按下方二维码关注之后重新点击链接</span>
        </div>
        <div>
            <img src="${qrcode}">
        </div>
    </div>

    <div id="errPage" class="display-none">
        <span>出错了，请稍后再试</span>
    </div>

    <div id="subscribed" class="display-none">
        <span>
            欢迎加入旅行帮人人分销，请
            <a class="success-account-name" href="/mobile/user/home.jhtml?accountId=${accountId}">点击</a>
            查看详情
        </span>
    </div>

    <div id="cantBeInvited" class="display-none">
        <span>
             您已经被邀请或者您已经邀请了别人，请
            <a class="success-account-name" href="/mobile/user/home.jhtml?accountId=${accountId}">点击</a>
            查看详情
        </span>
    </div>
</div>

</body>
</html>
