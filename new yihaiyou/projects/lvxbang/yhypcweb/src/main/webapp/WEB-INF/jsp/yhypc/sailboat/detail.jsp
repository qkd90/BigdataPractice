<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/public/pager.css">
	<link rel="stylesheet" type="text/css" href="/css/sailboat/sailboat_details.css">
    <jx:include fileAttr="${YHY_SAILBOAT_HEAD}" targetObject="yhyBuildService" targetMethod="buildSailboatDetail" objs="${param.ticketId}" validDay="1"></jx:include>
	<title>游艇帆船详情</title>
</head>
<body class="sailBoat">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
        <jx:include fileAttr="${YHY_SAILBOAT_DETAIL}" targetObject="yhyBuildService" targetMethod="buildSailboatDetail" objs="${param.ticketId}" validDay="1"></jx:include>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

<script type="text/html" id="tpl-sailboat-list-type-extend-item">
    <div class="point"></div>
    <div class="ticket-discription">
        {{each priceTypeExtendList as typeExtend i}}
        {{if typeExtend.secondTitle.length > 0}}
             <h5>{{typeExtend.secondTitle}}：</h5>
        {{/if}}
        <div class="ticket-discription-details">{{typeExtend.content}}</div>
        {{/each}}
    </div>
</script>

<script type="text/html" id="tpl-sailboat-list-type-item">
    <li class="clearfix">
        <a class="collapse" href="javascript:;" id="ticket-categoryBtn1" onclick="SailboatDetail.datalist('{{id}}')">{{name}}<i></i></a>
        <span class="price-span"><sub>￥</sub>{{discountPrice}}</span>
        <a href="/yhypc/order/orderSailboatWrite.jhtml?ticketPriceId={{id}}" class="buynow">立即预订</a>
        <div id="ticket-category-{{id}}" class="ticket-discription-wrap" data-flag="0" style="display: none;">
        </div>
    </li>
</script>

<script type="text/html" id="tpl-sailboat-detail-comment-info-2">
    <div class="comment-details-star">
        <h4>{{commentAvgScore}}<sub>/&nbsp;5分</sub></h4>
        {{if commentAvgScore > 0 && commentAvgScore <= 1}}
            <i class="icon-star-1"></i>
        {{/if}}
        {{if commentAvgScore > 1 && commentAvgScore <= 2}}
            <i class="icon-star-2"></i>
        {{/if}}
        {{if commentAvgScore > 2 && commentAvgScore <= 3}}
            <i class="icon-star-3"></i>
        {{/if}}
        {{if commentAvgScore > 3 && commentAvgScore <= 4}}
            <i class="icon-star-4"></i>
        {{/if}}
        {{if commentAvgScore > 4 && commentAvgScore <= 5}}
            <i class="icon-star-5"></i>
        {{/if}}
        {{if commentAvgScore == 0}}
            <i class="icon-star-0"></i>
        {{/if}}

    </div>
    <div class="comment-details-num">
        <i class="icon-hand"></i>
        （{{if commentCount != null && commentCount > 0}}<mark>{{commentCount}}</mark>条评论{{else}}暂无评论{{/if}}）
    </div>
</script>
<script type="text/html" id="tpl-sailboat-detail-comment-info-1">
    <em>{{commentAvgScore}}</em>
    <sub>&nbsp;/&nbsp;5分</sub>
    （<i>{{commentCount}}</i>条好评）
    <a id="collect" href="javascript:;" onclick="SailboatDetail.switchClassName(this)"></a>
</script>

<script type="text/html" id="tpl-sailboat-detail-comment-list">
    <li class="clearfix">
        <div class="comment-details-aside">
            {{if head.length > 0}}
                <img src="{{head}}" class="comment-person-img">
            {{else}}
                <img src="/image/2.png" class="comment-person-img">
            {{/if}}
            <p class="comment-customer-name">
                {{userName}}
            </p>
        </div>
        <div class="comment-details-article">
            <h5 class="clearfix">
                <span class="pull-left">发表时间：{{createTime}}</span>
                {{if avgScore > 0 && avgScore <= 1}}
                    <i class="comment-customer star-01 pull-right"></i>
                {{/if}}
                {{if avgScore > 1 && avgScore <= 2}}
                    <i class="comment-customer star-02 pull-right"></i>
                {{/if}}
                {{if avgScore > 2 && avgScore <= 3}}
                    <i class="comment-customer star-03 pull-right"></i>
                {{/if}}
                {{if avgScore > 3 && avgScore <= 4}}
                    <i class="comment-customer star-04 pull-right"></i>
                {{/if}}
                {{if avgScore > 4 && avgScore <= 5}}
                    <i class="comment-customer star-05 pull-right"></i>
                {{/if}}
                {{if avgScore == 0}}
                    <i class="comment-customer star-00 pull-right"></i>
                {{/if}}
            </h5>
            <p class="comment-aritcle">{{content}}</p>
            {{if comments != null && comments.length > 0}}
                <div class="busiReply">
                    <p class="replylineone"><span>商家回复：</span>{{comments[0].createTime}}</p>
                    <p class="replylineone replylinetwo">{{comments[0].content}}</p>
                </div>
            {{/if}}
        </div>

    </li>
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/sailboat/sailboat_details.js"></script>
</html>