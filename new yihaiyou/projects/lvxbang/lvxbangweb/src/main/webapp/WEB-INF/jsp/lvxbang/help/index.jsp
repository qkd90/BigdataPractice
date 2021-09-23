<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>帮助中心</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <meta http-equiv=”x-ua-compatible” content=”ie=7″/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <%--<link href="/css/common.css" rel="stylesheet" type="text/css">--%>
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/help.css" rel="stylesheet" type="text/css">
    <link href="/css/pagination.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="top-wrap">
    <div class="intro">
        <p class="big">你只需决定出发</p>
        <p>— 旅行帮为你定制最适合的旅程 —</p>
        <div class="bg"></div>
    </div>
</div>
<div class="middle-wrap" id="J_help-tab">
    <input type="hidden" value="${ page }" id="J_page">
    <ul class="help-nav cf">
        <li class="nav nav-1 <c:if test="${ page < 3 || empty page  || page > 11}">active</c:if>">
            <p>加盟合作</p>
            <ul class="sec-tab">
                <li id="1" <c:if test="${ page == 1 || empty page  || page > 11}">class="active"</c:if>>商家合作</li>
                <li id="2" <c:if test="${ page == 2}">class="active"</c:if>>广告业务</li>
            </ul>
        </li>
        <li class="nav nav-2 <c:if test="${ page < 8 &&  page > 2 }">active</c:if>">
            <p>用户帮助</p>
            <ul class="sec-tab">
                <li id="3" <c:if test="${ page == 3}">class="active"</c:if>>旅行帮功能介绍</li>
                <li id="4" <c:if test="${ page == 4}">class="active"</c:if>>FAQ</li>
                <li id="5" <c:if test="${ page == 5}">class="active"</c:if>>旅行帮用户协议</li>
                <li id="6" <c:if test="${ page == 6}">class="active"</c:if>>免责声明</li>
                <li id="7" class="advice-btn <c:if test="${ page == 7}">active</c:if>">意见反馈</li>
            </ul>
        </li>
        <li class="nav nav-3 <c:if test="${ page < 12 &&  page > 7 }">active</c:if>">
            <p>关于旅行帮</p>
            <ul class="sec-tab">
                <li id="8" <c:if test="${ page == 8}">class="active"</c:if>>关于旅行帮</li>
                <li id="8" <c:if test="${ page == 9}">class="active"</c:if>>联系旅行帮</li>
                <li id="9" <c:if test="${ page == 10}">class="active"</c:if>>诚聘英才</li>
                <li id="10" <c:if test="${ page == 11}">class="active"</c:if>>友情链接</li>
            </ul>
        </li>
    </ul>
    <div class="help-cont">
        <c:if test="${ page == 1 || empty page || page > 11}"><h1 >商家合作</h1></c:if>
        <c:if test="${ page == 2}"><h1>广告业务</h1></c:if>
        <c:if test="${ page == 3}"><h1>旅行帮功能介绍</h1></c:if>
        <c:if test="${ page == 4}"><h1>FAQ</h1></c:if>
        <c:if test="${ page == 5}"><h1>旅行帮用户协议</h1></c:if>
        <c:if test="${ page == 6}"><h1>免责声明</h1></c:if>
        <c:if test="${ page == 7}"><h1>意见反馈</h1></c:if>
        <c:if test="${ page == 8}"><h1>关于旅行帮</h1></c:if>
        <c:if test="${ page == 9}"><h1>联系旅行帮</h1></c:if>
        <c:if test="${ page == 10}"><h1>诚聘英才</h1></c:if>
        <c:if test="${ page == 11}"><h1>友情链接</h1></c:if>
        <!-- 商家合作流程 -->
        <div id="content" >
        </div>

        <!-- 意见反馈 -->
        <div class="tab-cont tab-cont-7 <c:if test="${ page != 7}">hide</c:if>">
            <div class="panel">
                    <textarea id="advice_content" name="content" class="w70 fs16" placeholder="狠狠地吐槽吧" ></textarea>
                    <input  id="advice-submit" class="btn-green btn fs14" type="button" value="提交">
                    <div class = countword>您已经输入<span id="word_count">0</span>/2000个文字</div>
                <ul id="advice-list" class="advice-wrap">

                </ul>
                <div class="pagelist-wrapper">
                    <div class="pagelist userpagelist" id="advice-page-list">
                        <div class="pagelist-center"></div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 -->
    </div>
</div>
<input type="hidden" value="${empty _CURRENT_MEMBER ? '0' : '1' }" id="J_userInfo"/>
<div class="ticket-footer">
    <%--<jsp:include page="../common/footerV2.jsp"></jsp:include>--%>
</div>
<script type="text/html" id="tpl_advice-list">
    <li class="list-item">
        <div class="advice cf">
            <div class="fl head" >
                {{if userHead}}
                <img src="{{userHead}}">
                {{else}}
                <img src="/images/d_avatar.jpg">
                {{/if}}
            </div>

            <div class="fl content">
                <p class="mb5">
                    {{if userName}}
                    <a class="fs16 name black">{{userName}}</a>
                    {{else}}
                    <a class="fs16 name black">匿名驴友</a>
                    {{/if}}
                    <span class="fs12 grey-1">{{createTime | ldf:'yyyy-MM-dd hh:mm:ss', ''}}</span>
                </p>
                <p class="fs14 grey-1">{{content}}</p>
                {{if reply}}
                <div class="reply mt5">
                    <p>
                        <span class="fs14">帮主回复：</span>
                        <span class="fs14 reply-content">{{reply}}</span>
                    </p>
                </div>
                {{/if}}
            </div>
        </div>
    </li>
</script>

<%@ include file="/WEB-INF/jsp/lvxbang/common/footer.jsp" %>
<script src="/js/lib/soutuu.pagenation.js" type="text/javascript"></script>
<script src="/js/lvxbang/help/index.js" type="text/javascript"></script>
</body>
</html>